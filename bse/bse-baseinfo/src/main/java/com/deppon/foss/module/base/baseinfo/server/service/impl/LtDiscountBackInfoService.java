package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ILtDiscountBackInfoDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILtDiscountBackInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusLtdiscountafterEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusLtdiscountafterItemEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CusLtDiscountItemDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LtDiscountBackInfoException;
import com.deppon.foss.util.define.FossConstants;

public class LtDiscountBackInfoService implements ILtDiscountBackInfoService {

	/**
	 * 零担事后折详细信息dao
	 */
	private ILtDiscountBackInfoDao ltDiscountBackInfoDao;
	
	public void setLtDiscountBackInfoDao(
			ILtDiscountBackInfoDao ltDiscountBackInfoDao) {
		this.ltDiscountBackInfoDao = ltDiscountBackInfoDao;
	}

	@Override
	public void addCusLtdiscountafter(CusLtdiscountafterEntity entity) {
		/** 
		 * 获取梯度折基础资料实体中梯度折基础资料详情实体GradientDiscItemEntity(CRM那边是把详情放在梯度折基础资料实体里面然后传过来的)
		 */
		List<CusLtdiscountafterItemEntity> cusLtdiscountafterItemList = entity.getDealItems();
		
		//对传过来的方案状态进行判断，0-有效将Active设置成Y，  2-无效讲Active设置成N
		String isZero = entity.getStatus();
		if("0".equals(isZero)){
			entity.setActive(FossConstants.ACTIVE);
			for(CusLtdiscountafterItemEntity entityList : cusLtdiscountafterItemList){
				entityList.setActive(FossConstants.ACTIVE);
			}
		}else{
			entity.setActive(FossConstants.INACTIVE);
			for(CusLtdiscountafterItemEntity entityList : cusLtdiscountafterItemList){
				entityList.setActive(FossConstants.INACTIVE);
			}
		}
		//对梯度折基础资料进行添加
		ltDiscountBackInfoDao.addCusLtdiscountafter(entity);
		//获取对详情表中active设置Y或N之后的List
		List<CusLtdiscountafterItemEntity> cusLtdiscountafterItem = entity.getDealItems();
		for(CusLtdiscountafterItemEntity entityList:cusLtdiscountafterItem){
			entityList.setDealId(entity.getFid());
			//对详情信息进行增加
			ltDiscountBackInfoDao.addCusLtdiscountafterItem(entityList);
		}
	}
	
	@Override
	public void updateCusLtdiscountafterInfo(CusLtdiscountafterEntity entity) {
		//首先根据客户编码和方案类型和active='Y'去查询是否纯在
				Long num = this.queryCusLtdiscountafterNum(entity);
		//		List<CusLtDiscountItemDto> dtos = this.queryCusLtdiscountafterByCondition(entity);
				if(num > 0){//如果存在，进行更新操作
					/** 
					 * 首先根据客户编码,方案类型和active=‘Y’查询出在梯度折基础资料表ID和梯度基础资料详情表中主键ID ITEM_ID，
					 * 再根据ID修改梯度基础资料信息，
					 * 再根据ITEM_ID基础资料修改详细信息(因为详细信息表中的“优惠方案ID”DISCOUNT_CRM_ID这个字段是关联这个CRM_ID)，
					 * 所以基础资料表中为N，那么详情表中与此关联的所有 记录也为N
					 */
					List<CusLtDiscountItemDto> cusLtDiscountItemDto  = this.queryCusLtdiscountafterByCode(entity);
					
					//获取梯度折基础资料实体中梯度折基础资料详情实体GradientDiscItemEntity(CRM那边是把详情放在梯度折基础资料实体里面然后传过来的)
					List<CusLtdiscountafterItemEntity> cusLtdiscountafterItemEntityList = entity.getDealItems();
					//对传过来的方案状态进行判断，0-有效对应active=Y,2-无效对应active=N
//					String isZero = entity.getStatus();
//					if("0".equals(isZero)){
//						entity.setActive(FossConstants.ACTIVE);
//						for(CusLtdiscountafterItemEntity discList:cusLtdiscountafterItemEntityList){
//							discList.setActive(FossConstants.ACTIVE);
//						}
//					}else{
//						entity.setActive(FossConstants.INACTIVE);
//						for(CusLtdiscountafterItemEntity discList:cusLtdiscountafterItemEntityList){
//							discList.setActive(FossConstants.INACTIVE);
//						}
//					}
					
					/**
					 * 对梯度折基础资料数据进行修改
					 */
					entity.setId(cusLtDiscountItemDto.get(0).getId());
					this.updateCusLtdiscountafter(entity);
					
					String status = entity.getStatus();
					
					/**
					 * 对梯度折基础资料详情数据进行修改
					 */
					for(int i=0;i<cusLtdiscountafterItemEntityList.size();i++){
						 // 利用for循环将“根据code，方案类型，active=‘Y’ 查询出梯度折扣表ID，梯度折扣详情表ITEM_ID”,
						 // 获取详情表数据中主键ITEM_ID，然后再set到实体(传到后台的实体)中，方便后台Mapper文件中修改时的条件以ID为条件
						cusLtdiscountafterItemEntityList.get(i).setItemId(cusLtDiscountItemDto.get(i).getItemId());
						this.updateCusLtdiscountafterItem(cusLtdiscountafterItemEntityList.get(i));
					}					
					if (!"2".equals(status)) {	
						entity.setEndTime(null);
						this.addCusLtdiscountafter(entity);
					}
				}else{	//如果不存在，则进行增加操作
					this.addCusLtdiscountafter(entity);
				}
		
	}

	@Override
	public void updateCusLtdiscountafter(CusLtdiscountafterEntity entity) {
		ltDiscountBackInfoDao.updateCusLtdiscountafter(entity);
	}

	@Override
	public void updateCusLtdiscountafterItem(CusLtdiscountafterItemEntity entity) {
		ltDiscountBackInfoDao.updateCusLtdiscountafterItem(entity);		
	}

	@Override
	public List<CusLtDiscountItemDto> queryCusLtdiscountafterByCondition(
			CusLtdiscountafterEntity entity) {
		if(entity != null){
			return ltDiscountBackInfoDao.queryCusLtdiscountafterByCondition(entity);
		}else{
			throw new LtDiscountBackInfoException("传入的参数为空...");
		}
	}

	@Override
	public List<CusLtDiscountItemDto> queryCusLtdiscountafterByCode(
			CusLtdiscountafterEntity entity) {
		if(entity != null){
			return ltDiscountBackInfoDao.queryCusLtdiscountafterByCode(entity);
		}else{
			throw new LtDiscountBackInfoException("传入的参数为空...");
		}
	}

	@Override
	public Long queryCusLtdiscountafterNum(CusLtdiscountafterEntity entity) {
		if(entity != null){
			entity.setActive(FossConstants.ACTIVE);
			return ltDiscountBackInfoDao.queryCusLtdiscountafterNum(entity);
		}else{
			throw new LtDiscountBackInfoException("传入的参数为空...");
		}
	}


}
