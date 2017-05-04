package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IGradientDiscountDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGradientDiscountService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GradientDiscItemEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GradientDiscountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.GradientDiscountItemDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.GradientDiscountException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 零担梯度折扣需求 服务端 restful
 * @author 218392 张永雪
 * @date 2015-06-05 14:48:19
 */
public class GradientDiscountService implements IGradientDiscountService {
	
	/**
	 * 注入gradientDiscountService
	 */
	private IGradientDiscountService gradientDiscountService;
	
	/**
	 * 注入IGradientDiscountDao
	 */
	private IGradientDiscountDao gradientDiscountDao;
	
	/**
	 * set注入gradientDiscountService
	 * @param gradientDiscountService
	 */
	public void setGradientDiscountService(
			IGradientDiscountService gradientDiscountService) {
		this.gradientDiscountService = gradientDiscountService;
	}
	
	/**
	 * set注入gradientDiscountDao
	 * @param gradientDiscountDao
	 */
	public void setGradientDiscountDao(IGradientDiscountDao gradientDiscountDao) {
		this.gradientDiscountDao = gradientDiscountDao;
	}
	

	/**
	 * 查询数目操作
	 * 验证从CRM同步过来的数据在FOSS表中的存在，查询参数必须是客户编码+方案类型(数据字典形式)+是否有效active
	 * 因为一个客户只能对应一种方案类型
	 * 也有可能改客户对应的信息之前已经有，只是已经作废了，现在又给该客户开通了相当于新增吗，之前的记录保存而不再原数据上进行修改覆盖
	 * 
	 */
	@Override
	public Long queryGradientDiscountNum(GradientDiscountEntity entity) {
		if(entity != null){
			entity.setActive(FossConstants.ACTIVE);
			return gradientDiscountDao.queryGradientDiscountNum(entity);
		}else{
			throw new GradientDiscountException("传入的参数为空...");
		}
	}
	

	/**
	 * 梯度折扣基础资料 --插入操作
	 * 把CRM同步过来的“梯度折扣基础资料”数据增加到FOSS梯度折扣基础资料表中
	 */
	@Override
	public void addGradientDiscountInfo(GradientDiscountEntity entity) {
		
		/** 
		 * 获取梯度折基础资料实体中梯度折基础资料详情实体GradientDiscItemEntity(CRM那边是把详情放在梯度折基础资料实体里面然后传过来的)
		 */
		List<GradientDiscItemEntity> gradientDiscItemList = entity.getDealItems();
		
		//对传过来的方案状态进行判断，0-有效将Active设置成Y，  2-无效讲Active设置成N
		String isZero = entity.getStatus();
		if("0".equals(isZero)){
			entity.setActive(FossConstants.ACTIVE);
			for(GradientDiscItemEntity entityList : gradientDiscItemList){
				entityList.setActive(FossConstants.ACTIVE);
			}
		}else{
			entity.setActive(FossConstants.INACTIVE);
			for(GradientDiscItemEntity entityList : gradientDiscItemList){
				entityList.setActive(FossConstants.INACTIVE);
			}
		}
		//对梯度折基础资料进行添加
		gradientDiscountDao.addGradientDiscountInfo(entity);
		//获取对详情表中active设置Y或N之后的List
		List<GradientDiscItemEntity> gradientDiscItem = entity.getDealItems();
		for(GradientDiscItemEntity entityList:gradientDiscItem){
			entityList.setDealId(entity.getFid());
			//对详情信息进行增加
			gradientDiscountDao.addGradientDiscItemsInfo(entityList);
		}
	}
	
	
	/**
	 * 同步操作操作
	 */
	@Override
	public void updateGradientDiscountInfo(GradientDiscountEntity entity) {
		//首先根据客户编码和方案类型和active='Y'去查询是否纯在
		Long num = gradientDiscountService.queryGradientDiscountNum(entity);
	//	List<GradientDiscountItemDto> dtos = gradientDiscountService.queryGradientDiscountByCondition(entity);
		if(num > 0){//如果存在，先进行逻辑作废active='N'；再新增
			/** 
			 * 首先根据客户编码,方案类型和active='Y'查询出在梯度折基础资料表ID和梯度基础资料详情表中主键ID ITEM_ID，
			 * 再根据ID修改梯度基础资料信息active='N'，
			 * 再根据ITEM_ID基础资料修改详细信息active='N'(因为详细信息表中的“优惠方案ID”DISCOUNT_CRM_ID这个字段是关联这个CRM_ID)，
			 * 再新增一条
			 */
			List<GradientDiscountItemDto> gradientItemDto  = gradientDiscountService.queryGradientDiscountByCode(entity);
			
			//获取梯度折基础资料实体中梯度折基础资料详情实体GradientDiscItemEntity(CRM那边是把详情放在梯度折基础资料实体里面然后传过来的)
			List<GradientDiscItemEntity> gradientDiscItemList = entity.getDealItems();
			//对传过来的方案状态进行判断，0-有效对应active=Y,2-无效对应active=N
//			String isZero = entity.getStatus();
//			if("0".equals(isZero)){
//				entity.setActive(FossConstants.ACTIVE);
//				for(GradientDiscItemEntity discList:gradientDiscItemList){
//					discList.setActive(FossConstants.ACTIVE);
//				}
//			}else{
//				entity.setActive(FossConstants.INACTIVE);
//				for(GradientDiscItemEntity discList:gradientDiscItemList){
//					discList.setActive(FossConstants.INACTIVE);
//				}
//			}
			
			/**
			 * 对梯度折基础资料数据进行逻辑作废
			 */
			entity.setId(gradientItemDto.get(0).getId());
			gradientDiscountService.updateGradientDiscount(entity);//逻辑作废
			/**
			 * 对梯度折基础资料详情数据进行逻辑作废
			 */
			for(int i=0;i<gradientDiscItemList.size();i++){
				 // 利用for循环将“根据code，方案类型，active=‘Y’ 查询出梯度折扣表ID，梯度折扣详情表ITEM_ID”,
				 // 获取详情表数据中主键ITEM_ID，然后再set到实体(传到后台的实体)中，方便后台Mapper文件中修改时的条件以ID为条件
				gradientDiscItemList.get(i).setItemId(gradientItemDto.get(i).getItemId());				
				gradientDiscountService.updateGradientDiscItem(gradientDiscItemList.get(i));//逻辑作废
			}
			//再新增一条
			gradientDiscountService.addGradientDiscountInfo(entity);//新增
		}else{	//如果不存在，则进行增加操作
			gradientDiscountService.addGradientDiscountInfo(entity);
		}
	}
	
	/**
	 * 修改“梯度折基础资料”
	 * 根据 ID进行修改，这样保证修改的是同一条数据
	 */
	@Override
	public void updateGradientDiscount(GradientDiscountEntity entity) {
		gradientDiscountDao.updateGradientDiscount(entity);
	}
	

	/**
	 * 修改“梯度折基础资料详情”
	 * 根据详情表中主键ITEM_ID进行修改
	 */
	@Override
	public void updateGradientDiscItem(GradientDiscItemEntity entity) {

		gradientDiscountDao.updateGradientDiscItem(entity);
		
	}

	
	/**
	 * 提供给接送货开发组
	 * 查询操作--根据客户编码+（方案类型）查询出该客户的梯度折扣信息，以及合同的开始结束时间、合同的标杆编码、以及
	 * 详情表数据的修改时间
	 * 根据接送货传入的条件查询
	 * 
	 */
	@Override
	public List<GradientDiscountItemDto> queryGradientDiscountByCondition(GradientDiscountEntity entity) {
		if(entity != null){
			return gradientDiscountDao.queryGradientDiscountByCondition(entity);
		}else{
			throw new GradientDiscountException("传入的参数为空...");
		}
	}

	/**
	 * 根据客户编码CODE查询梯度折扣基础资料表中的ID和CRM_ID
	 * @param entity
	 * @return
	 */
	@Override
	public List<GradientDiscountItemDto> queryGradientDiscountByCode(
			GradientDiscountEntity entity) {
		if(entity != null){
			return gradientDiscountDao.queryGradientDiscountByCode(entity);
		}else{
			throw new GradientDiscountException("传入的参数为空...");
		}
	}
	

}
