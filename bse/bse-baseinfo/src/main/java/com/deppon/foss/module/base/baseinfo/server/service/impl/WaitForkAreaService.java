package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPlatformDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IWaitForkAreaDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IWaitForkAreaService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WaitForkAreaDistanceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WaitForkAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.WaitForkAreaException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * @author 092020-lipengfei
 * @version V1.0
 * @Description 待叉区service
 * @Time 2014-4-25
 */
public class WaitForkAreaService implements IWaitForkAreaService {
	/**
	 * 货区service
	 */
	private IGoodsAreaService goodsAreaService;
	
	/**
	 * 月台
	 */
	private IPlatformDao platformDao;
	/**
	 * 待叉区service
	 */
	private IWaitForkAreaDao waitForkAreaDao;
	private static final Logger LOGGER = LoggerFactory.getLogger(WaitForkAreaService.class);
	
	
	public void setPlatformDao(IPlatformDao platformDao) {
		this.platformDao = platformDao;
	}
	public void setWaitForkAreaDao(IWaitForkAreaDao waitForkAreaDao) {
		this.waitForkAreaDao = waitForkAreaDao;
	}
	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}
	/**
	 * @author 李鹏飞
	 * @Description 根据外场编码查询待叉区
	 * @Time 2014-4-25
	 * @param transferCode
	 * @return
	 */
	@Override
	public List<WaitForkAreaEntity> queryWaitForkAreaByParams(
			WaitForkAreaEntity waitForkAreaEntity,int limit, int start) {
		String organizationCode=waitForkAreaEntity.getOrganizationCode();
		List<WaitForkAreaEntity> entityList=waitForkAreaDao.queryWaitForkAreaByTransferCode(organizationCode, limit, start);
		return entityList;
	}
	/**
	 * @author 李鹏飞
	 * @Description 查询符合条件待叉区总量
	 * @Time 2014-4-25
	 * @param entity
	 * @return
	 */
	@Override
	public long queryWaitForkAreaCount(WaitForkAreaEntity waitForkAreaEntity) {
		String organizationCode=waitForkAreaEntity.getOrganizationCode();
		return waitForkAreaDao.countWaitForkAreaByTransferCode(organizationCode);
	}
	/**
	 * @author 李鹏飞
	 * @Description 删除待叉区
	 * @Time 2014-4-25
	 * @param entity
	 * @return
	 */
	@Transactional
	@Override
	public int deleteWaitForkAreaByVirtualCode(WaitForkAreaEntity entity) {
		int flag=waitForkAreaDao.deleteWaitForkAreaByVirtualCode(entity);//删除待叉区
		waitForkAreaDao.deleteWaitForkAreaDistanceByCode(entity);//删除改待叉区到目标的距离
		return flag;
	}
	/**
	 * @author 李鹏飞
	 * @Description 新增待叉区
	 * @Time 2014-4-25
	 * @param entity
	 * @return
	 */
	@Transactional
	@Override
	public int addWaitForkArea(WaitForkAreaEntity entity) throws WaitForkAreaException{
		WaitForkAreaEntity existsEntity=waitForkAreaDao.queryWaitForkAreaByTransferCodeAndWaitForkAreaCode(entity.getWaitForkAreaCode(),entity.getTransferCode());
		if(null!=existsEntity){
			throw new WaitForkAreaException("同一外场下已有相同的待叉区编号！","同一外场下已有相同的待叉区编号！");
		}
		int flag=waitForkAreaDao.addWaitForkArea(entity);
		if(flag!=0){
			List<WaitForkAreaDistanceEntity> distanceEntityList=new ArrayList<WaitForkAreaDistanceEntity>();
			getDistanceBetweenGoodsArea(distanceEntityList, entity);//获取待叉区到货区的距离
			getDistanceBetweenPlatform(distanceEntityList, entity);//获取待叉区到月台的距离
			if(distanceEntityList.size()>0){
				waitForkAreaDao.addWaitForkAreaDistance(distanceEntityList);
			}
		}
		return flag;
	}
	
	/**
	 * @author 李鹏飞
	 * @Description 修改待叉区
	 * @Time 2014-4-25
	 * @param entity
	 * @return
	 */
	@Transactional
	@Override
	public int updateWaitForkArea(WaitForkAreaEntity entity) {
		//修改时，首先查询本条待叉区，若横纵坐标修改，则重新计算到货区距离，若没修改，直接使用表单中内容
		WaitForkAreaEntity existsEntity=waitForkAreaDao.queryWaitForkAreaByCode(entity.getWaitForkAreaCode(),entity.getVirtualCode());
		if(null==existsEntity){
			throw new WaitForkAreaException("修改失败！","修改失败！");
		}
		waitForkAreaDao.deleteWaitForkAreaByVirtualCode(entity);//删除原待叉区
		 //操作类型设置为修改状态（用来方便算月台）
		entity.setIsUpdate("Y");
		waitForkAreaDao.deleteWaitForkAreaDistanceByCode(entity);//删除原改待叉区到目标的距离
		//当没有修改横纵坐标是，到货区的距离以用户输入为准，当修改了横纵坐标，到货区的距离以重新计算为准
		if(!StringUtil.equals(entity.getAbscissa(), existsEntity.getAbscissa())||!StringUtil.equals(entity.getOrdinate(), existsEntity.getOrdinate())){
			entity.setDistanceBetweenGoodsArea(null);
		}
		int flag=waitForkAreaDao.addWaitForkArea(entity);
		
		if(flag!=0){
			List<WaitForkAreaDistanceEntity> distanceEntityList=new ArrayList<WaitForkAreaDistanceEntity>();
			getDistanceBetweenGoodsArea(distanceEntityList, entity);//获取待叉区到货区的距离
			getDistanceBetweenPlatform(distanceEntityList, entity);//获取待叉区到月台的距离
			if(distanceEntityList.size()>0){
				waitForkAreaDao.addWaitForkAreaDistance(distanceEntityList);
			}
		}
		return flag;
	}
	/**
	 * @author 李鹏飞
	 * @Description 根据外场编码、待叉区编码查询待叉区
	 * @Time 2014-4-28
	 * @param waitForkAreaCode
	 * @return
	 */
	@Override
	public WaitForkAreaEntity queryWaitForkAreaByCode(String waitForkAreaCode,String virtuaCode) {
		//查询待叉区
		WaitForkAreaEntity entity=waitForkAreaDao.queryWaitForkAreaByCode(waitForkAreaCode,virtuaCode);
		//获取待叉区与月台、库区的距离
		getWaitForkAreaDistance(entity);
		return entity;
	}
	/**
	 * @author 李鹏飞
	 * @Description 根据外场查询待叉区，计算待叉区到指定月台的距离(提供给其他模块调用)
	 * @Time 2014-4-28
	 * @param transferCode
	 * @param platformCode
	 * @return List<WaitForkAreaDistanceEntity>
	 */
	@Override
	public List<WaitForkAreaDistanceEntity> queryDistanceBetweenPlatform(
			String transferCode, String platformCode) {
		List<WaitForkAreaDistanceEntity> entityList=null;
		if(StringUtil.isNotBlank(transferCode) && StringUtil.isNotBlank(platformCode)){
			entityList=waitForkAreaDao.queryDistanceBetweenPlatform(transferCode, platformCode);
		}
		return entityList;
	}
	/**
	 * @author 李鹏飞
	 * @Description 计算待叉区与库区距离
	 * @Time 2014-4-28
	 * @param transferCode
	 * @param goodsAreaCode
	 * @param waitForkAreaCode
	 * @return WaitForkAreaDistanceEntity
	 */
	@Override
	public WaitForkAreaDistanceEntity queryDistanceBetweenGoodsArea(
			String transferCode, String goodsAreaCode, String waitForkAreaCode) {
		WaitForkAreaDistanceEntity entity=null;
		if(StringUtil.isNotBlank(transferCode) && StringUtil.isNotBlank(goodsAreaCode) && StringUtil.isNotBlank(waitForkAreaCode)){
			entity=waitForkAreaDao.queryDistanceBetweenGoodsArea(transferCode, goodsAreaCode, waitForkAreaCode);
		}
		return entity;
	}
	/**
	 * @author 李鹏飞
	 * @Description 获取待叉区与月台的距离
	 * @Time 2014-4-25
	 * @param distanceEntityList
	 * @param entity
	 */
	private void getDistanceBetweenPlatform(List<WaitForkAreaDistanceEntity> distanceEntityList,WaitForkAreaEntity entity){
		if(null!=entity.getDistanceBetweenPlatform()&&entity.getDistanceBetweenPlatform().size()>0){
			for(WaitForkAreaDistanceEntity distanceEntity: entity.getDistanceBetweenPlatform()){
				//如果是修改状态先删除原来的待叉区到月台的距离
//				if(StringUtil.isBlank(entity.getIsUpdate())){
//					WaitForkAreaEntity daleteEntity = new WaitForkAreaEntity();
//					daleteEntity.setWaitForkAreaCode(distanceEntity.getWaitForkAreaCode());
//					daleteEntity.setOrganizationCode(entity.getOrganizationCode());
//					daleteEntity.setTargetCode(distanceEntity.getTargetCode());
//					daleteEntity.setModifyUser(entity.getModifyUser());
//					waitForkAreaDao.deleteWaitForkAreaDistanceByCode(daleteEntity);//删除原改待叉区到目标的距离
//				}
				//根据外场和月台编号获取月台信息
				PlatformEntity platformEntity = platformDao.queryPlatformByCode(entity.getOrganizationCode(),distanceEntity.getTargetCode());
				//根据待叉区，月台坐标算距离
				String distance = countDist(entity.getAbscissa(),entity.getOrdinate(),platformEntity.getAbscissa(),platformEntity.getOrdinate());
				distanceEntity.setDistance(distance);
				
				distanceEntity.setTargetType(DictionaryValueConstants.DISTANCE_BETWEEN_WAITFORKAREA_PLATFORM);
				distanceEntity.setId(UUIDUtils.getUUID());
				distanceEntity.setWaitForkAreaCode(entity.getWaitForkAreaCode());
				distanceEntity.setTransferCode(entity.getOrganizationCode());
				distanceEntity.setActive(FossConstants.ACTIVE);
				distanceEntity.setModifyUser(entity.getModifyUser());
				distanceEntity.setModifyDate(new Date());
				distanceEntityList.add(distanceEntity);
			}
		}
	}
	/**
	 * @author 李鹏飞
	 * @Description 获取待叉区与货区的距离
	 * @Time 2014-4-25
	 * @param distanceEntityList
	 * @param entity
	 */
	private void getDistanceBetweenGoodsArea(List<WaitForkAreaDistanceEntity> distanceEntityList,WaitForkAreaEntity entity){
		//当到货区距离不为空时
		if(null!=entity.getDistanceBetweenGoodsArea()&&entity.getDistanceBetweenGoodsArea().size()>0){
			for(WaitForkAreaDistanceEntity distanceEntity: entity.getDistanceBetweenGoodsArea()){
				WaitForkAreaEntity daleteEntity = new WaitForkAreaEntity();
				daleteEntity.setWaitForkAreaCode(distanceEntity.getWaitForkAreaCode());
				daleteEntity.setOrganizationCode(entity.getOrganizationCode());
				daleteEntity.setTargetCode(distanceEntity.getTargetCode());
				daleteEntity.setModifyUser(entity.getModifyUser());
				waitForkAreaDao.deleteWaitForkAreaDistanceByCode(daleteEntity);//删除原改待叉区到目标的距离
				
				distanceEntity.setTargetType(DictionaryValueConstants.DISTANCE_BETWEEN_WAITFORKAREA_GOODSAREA);
				distanceEntity.setId(UUIDUtils.getUUID());
				distanceEntity.setTransferCode(entity.getOrganizationCode());
				distanceEntity.setWaitForkAreaCode(entity.getWaitForkAreaCode());
				distanceEntity.setActive(FossConstants.ACTIVE);
				distanceEntity.setModifyUser(entity.getModifyUser());
				distanceEntity.setModifyDate(new Date());
				distanceEntityList.add(distanceEntity);
			}
		}else{//当到货区距离为空时，根据坐标计算
			//查询外场所有的货区
			List<GoodsAreaEntity> goodsAreaEntityList=goodsAreaService.queryGoodsAreaListByOrganizationCode(entity.getOrganizationCode());
			if(null!=goodsAreaEntityList&&goodsAreaEntityList.size()>0){
				for(GoodsAreaEntity goodsAreaEntity:goodsAreaEntityList){
					WaitForkAreaDistanceEntity distanceEntity=new WaitForkAreaDistanceEntity();
					distanceEntity.setWaitForkAreaCode(entity.getWaitForkAreaCode());
					distanceEntity.setTransferCode(entity.getOrganizationCode());
					distanceEntity.setTargetType(DictionaryValueConstants.DISTANCE_BETWEEN_WAITFORKAREA_GOODSAREA);
					distanceEntity.setId(UUIDUtils.getUUID());
					distanceEntity.setActive(FossConstants.ACTIVE);
					distanceEntity.setModifyUser(entity.getModifyUser());
					distanceEntity.setModifyDate(new Date());
					distanceEntity.setTargetCode(goodsAreaEntity.getGoodsAreaCode());
					//计算距离
					distanceEntity.setDistance(countDist(entity.getAbscissa(),entity.getOrdinate(),goodsAreaEntity.getAbscissa(),goodsAreaEntity.getOrdinate()));
					distanceEntityList.add(distanceEntity);
				}
			}
		}
	}
	/**
	 * @author 李鹏飞
	 * @Description 获取当前待叉区与所有目标的距离
	 * @Time 2014-4-28
	 * @param entity
	 */
	private void getWaitForkAreaDistance(WaitForkAreaEntity entity){
		List<WaitForkAreaDistanceEntity> distanceEntityList=null;
		if(null!=entity&&StringUtil.isNotBlank(entity.getWaitForkAreaCode())){
			//根据待叉区编码查询距离表，获得待叉区到各目标的距离
			distanceEntityList=waitForkAreaDao.queryWaitForkAreaDistanceByCode(entity.getWaitForkAreaCode(),entity.getOrganizationCode());
		}
		List<WaitForkAreaDistanceEntity> distanceBetweenGoodsArea = new ArrayList();
		List<WaitForkAreaDistanceEntity> distanceBetweenPlatform = new ArrayList();
		if(null!=distanceEntityList&&distanceEntityList.size()>0){
			for(WaitForkAreaDistanceEntity distanceEntity:distanceEntityList){
				//当为待叉区到库区的距离时
				if(StringUtil.equals(DictionaryValueConstants.DISTANCE_BETWEEN_WAITFORKAREA_GOODSAREA, distanceEntity.getTargetType())){
					distanceBetweenGoodsArea.add(distanceEntity);
					entity.setDistanceBetweenGoodsArea(distanceBetweenGoodsArea);
				}else if(StringUtil.equals(DictionaryValueConstants.DISTANCE_BETWEEN_WAITFORKAREA_PLATFORM, distanceEntity.getTargetType())){
					//当为待叉区到月台距离时
					distanceBetweenPlatform.add(distanceEntity);
					entity.setDistanceBetweenPlatform(distanceBetweenPlatform);
//					entity.getDistanceBetweenPlatform().add(distanceEntity);
				}
			}
		}
	}

	/**
	 * @author 李鹏飞
	 * @Description 根据横纵坐标计算距离
	 * @Time 2014-4-25
	 * @return
	 */
	private String countDist(String x1,String y1,String x2,String y2){
		double a1=0,b1=0,a2=0,b2=0;
		try {
			a1=StringUtil.isBlank(x1)?0:Double.parseDouble(x1);
			b1=StringUtil.isBlank(y1)?0:Double.parseDouble(y1);
			a2=StringUtil.isBlank(x2)?0:Double.parseDouble(x2);
			b2=StringUtil.isBlank(y2)?0:Double.parseDouble(y2);
		} catch (NumberFormatException e) {
			LOGGER.debug(e.getMessage(), e);
			return "0.00";
		}
		double result=Math.abs(a1-a2)+Math.abs(b1-b2);
		DecimalFormat df=new DecimalFormat("0.00");
		return df.format(result);
	}
	/**
	 * @author lifanghong
	 * @Description 根据虚拟Code查询待叉区
	 * @Time 2014-4-28
	 * @return WaitForkAreaDistanceEntity
	 */
	@Override
	public WaitForkAreaEntity queryWaitForkAreaByVirtualCode(
			String virtualCode) {
		return waitForkAreaDao.queryWaitForkAreaByCode(null, virtualCode);
	}
}
