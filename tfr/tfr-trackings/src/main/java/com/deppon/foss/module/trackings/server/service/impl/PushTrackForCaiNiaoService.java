package com.deppon.foss.module.trackings.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.trackings.api.server.dao.IPushTrackForCaiNiaoDao;
import com.deppon.foss.module.trackings.api.server.service.IPushTrackForCaiNiaoService;
import com.deppon.foss.module.trackings.api.shared.domain.ExpressTrackExternalEntity;
import com.deppon.foss.module.trackings.api.shared.domain.SynTrackingEntity;
import com.deppon.foss.module.trackings.api.shared.domain.TrackExternalLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.partialline.api.shared.util.RegexpUtil;
import com.deppon.foss.util.UUIDUtils;

/***
 * 货物轨迹推送接口
 *货物轨迹信息存储到异步表，以便后续推送
 *用于不同 业务场景的货物轨迹存储service
 * @author 205109-zenghaibin-foss
 * @date 2015-04-27 上午11:13:20
 ****/
public class PushTrackForCaiNiaoService implements IPushTrackForCaiNiaoService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PushTrackForCaiNiaoService.class);

	/**货物轨迹推送dao**/
	private IPushTrackForCaiNiaoDao pushTrackForCaiNiaoDao;
	/**
	 * 运单service
	 */
	private IWaybillManagerService waybillManagerService;
	
	/**
	 * 部门service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	public void setPushTrackForCaiNiaoDao(
			IPushTrackForCaiNiaoDao pushTrackForCaiNiaoDao) {
		this.pushTrackForCaiNiaoDao = pushTrackForCaiNiaoDao;
	}
	
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	/**
	 * 获取运单信息
	 */
	private IWaybillDao waybillDao;
	
	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}

	/***
	 *插入快递相关轨迹到货物表
	 * @author 205109-zenghaibin-foss
	 * @date 2015-04-29上午10:11:20
	 * @param entity 快递轨迹实体
	 * @return
	 ****/
	@Override
	public void addCarGoTrack(ExpressTrackExternalEntity entity){
		if(entity==null){
			throw new TfrBusinessException("快递轨迹参数为空");
		}
		if(StringUtils.isBlank(entity.getId())){
			throw new TfrBusinessException("快递轨迹id不能为空");
		}
		if(StringUtils.isBlank(entity.getWayBillNo())){
			throw new TfrBusinessException("快递轨迹运单号不能为空");
		}
		
		pushTrackForCaiNiaoDao.addCarGoTrack(entity);
	}

	/***
	 *插入快递相关轨迹到货物表
	 * @author 205109-zenghaibin-foss
	 * @date 2015-04-29上午10:11:20
	 * @param entityList 快递轨迹实体list
	 * @return
	 ****/
	public void addBatchCarGoTrack(List<ExpressTrackExternalEntity> entityList){
		pushTrackForCaiNiaoDao.addBatchCarGoTrack(entityList);
	}

	

	/***
	 *插入快递相关轨迹到货物表
	 * @author 205109-zenghaibin-foss
	 * @date 2015-04-29上午10:11:20
	 * @param entity 快递轨迹实体
	 * @return
	 ****/
	@Override
	public void addSynTrack(SynTrackingEntity entity){
		if(entity==null){
			throw new TfrBusinessException("快递轨迹参数为空");
		}
		if(StringUtils.isBlank(entity.getId())){
			throw new TfrBusinessException("快递轨迹id不能为空");
		}
		if(StringUtils.isBlank(entity.getWayBillNo())){
			throw new TfrBusinessException("快递轨迹运单号不能为空");
		}
		
		pushTrackForCaiNiaoDao.addSynTrack(entity);
	}
	
	/**
	 *插入快递相关轨迹到货物表(揽派收)
	 * @author 311396-wwb
	 * @date 2016年9月26日14:26:26
	 * @param entity 快递轨迹实体
	 * @return
	 */
	@Override
	public void addSynLpsTrack(SynTrackingEntity entity){
		if(entity==null){
			throw new TfrBusinessException("快递轨迹参数为空");
		}
		if(StringUtils.isBlank(entity.getId())){
			throw new TfrBusinessException("快递轨迹id不能为空");
		}
		if(StringUtils.isBlank(entity.getWayBillNo())){
			throw new TfrBusinessException("快递轨迹运单号不能为空");
		}
		
		pushTrackForCaiNiaoDao.addSynLpsTrack(entity);
	}

	/***
	 *在绑定快递代理单号的时候将信息插入到相关轨迹的表中
	 * @author 268084
	 * @date 2016-03-17上午10:11:20
	 * @param entity 快递轨迹实体
	 * @return
	 ****/
	@Override
	public void addSynTrackToWQS(SynTrackingEntity entity){
		try {
			LOGGER.info("开始查询运单信息..............");
			WaybillEntity waybillEntity = waybillDao.queryWaybillByNo(entity
					.getWayBillNo());//获取运单信息
			if (waybillEntity != null) {
				entity.setProductCode(waybillEntity.getProductCode());
				entity.setOrderChannel(waybillEntity.getOrderChannel());
				entity.setDestinationDeptName(waybillEntity.getCustomerPickupOrgName());
				//2016年8月30日14:44:31 311396 增加目的国名称 line 152
				//【G】上海转日本【LD】/M上海-新加坡KM【G】针对此种类型
				String regexp = "[转|-]([^ ]+?)[a-zA-Z|【]";
				String destCountryName = RegexpUtil.getMatchString(waybillEntity.getCustomerPickupOrgName(), regexp);
				entity.setDestCountryName(destCountryName);//目的国名称
			}
			LOGGER.info("查询运单信息并赋值结束");
			LOGGER.info("开始将相关信息插入到同步轨迹表......");
			pushTrackForCaiNiaoDao.addSynTrackToWQS(entity);
			LOGGER.info("开始将相关信息插入到同步轨迹表结束......");
		} catch (Exception e) {
			LOGGER.info("绑定外发单号同步至WQS报错"+e.toString());
		}
	}
	
	/***
	  *插入快递相关轨迹到货物表(揽派收)
	 * @author 311396-wwb
	 * @date 2016年9月26日14:26:26
	 * @param entityList 快递轨迹实体list
	 * @return
	 ****/
	public void addBatchSynLpsTrack(List<SynTrackingEntity> entityList){
		pushTrackForCaiNiaoDao.addBatchSynLpsTrack(entityList);
	}

	/***
	 *插入快递相关轨迹到货物表
	 * @author 205109-zenghaibin-foss
	 * @date 2015-04-29上午10:11:20
	 * @param entityList 快递轨迹实体list
	 * @return
	 ****/
	public void addBatchSynTrack(List<SynTrackingEntity> entityList){
		pushTrackForCaiNiaoDao.addBatchSynTrack(entityList);
	}

	
	
	
	

	/**
	 * 
	 * <p>提供方法给异常库存，判定是否推动轨迹</p> 
	 * @author alfred
	 * @date 2015年7月11日 下午2:33:35
	 * @param waybillNos 
	 */
	@Override
	public void judgeTrakcForExpctionStock(List<String> waybillNos) {
		if(CollectionUtils.isNotEmpty(waybillNos)){
			for(String waybillNo:waybillNos){
				//判定轨迹是否存在
				TrackExternalLogEntity logEntity = new TrackExternalLogEntity();				
				logEntity.setWaybillNo(waybillNo);
				logEntity.setMsgType("FAILED");
				boolean isExits = pushTrackForCaiNiaoDao.checkExistsTrack(logEntity);
				if(!isExits){
					//运单信息
					WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
					//提货网点
					OrgAdministrativeInfoEntity entity = orgAdministrativeInfoService.
							queryOrgAdministrativeInfoByCode(waybillEntity.getCustomerPickupOrgCode());
					//轨迹信息填充
					SynTrackingEntity trackEntity = new SynTrackingEntity();
					trackEntity.setWayBillNo(waybillNo);
					trackEntity.setOrderNo(waybillEntity.getOrderNo());
					trackEntity.setCreateDate(new Date());
					trackEntity.setEventType("FAILED");
					trackEntity.setId(UUIDUtils.getUUID());
					trackEntity.setModifyDate(new Date());
					trackEntity.setOperateCity(entity.getCityName());
					trackEntity.setOperateTime(new Date());
					trackEntity.setOrgCode(waybillEntity.getCustomerPickupOrgCode());
					trackEntity.setOrgName(waybillEntity.getCustomerPickupOrgName());
					trackEntity.setOrgType("1");
					trackEntity.setTrackInfo("客户拒签");
					this.addSynTrack(trackEntity);
				}
			}
		}
	}

	
}
