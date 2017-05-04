package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.ILTLEWaybillPdaScanService;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillProcessLogDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILTLEWaybillHandlePdaService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillProcessLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabelInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PdaAppInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WoodenRequirePdaDto;
import com.deppon.foss.module.trackings.api.server.service.IPushTrackForCaiNiaoService;
import com.deppon.foss.module.trackings.api.shared.domain.SynTrackingEntity;
import com.deppon.foss.util.UUIDUtils;


/**
 * @description 零担电子运单PDA接口 
 * @author 297064
 * 2016-05-30
 */
public class LTLEWaybillPdaScanService implements ILTLEWaybillPdaScanService {
	/**
	 * 记录日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LTLEWaybillPdaScanService.class);
	
	private static final String OPERATION_TYPE_APP = "APP";
	
	private ILTLEWaybillHandlePdaService lTLEWaybillHandlePdaService;
	
	private IWaybillProcessLogDao waybillProcessLogDao;
	
	private IPushTrackForCaiNiaoService pushTrackForCaiNiaoService;
	public void setPushTrackForCaiNiaoService(
			IPushTrackForCaiNiaoService pushTrackForCaiNiaoService) {
		this.pushTrackForCaiNiaoService = pushTrackForCaiNiaoService;
	}
	
	public void setlTLEWaybillHandlePdaService(
			ILTLEWaybillHandlePdaService lTLEWaybillHandlePdaService) {
		this.lTLEWaybillHandlePdaService = lTLEWaybillHandlePdaService;
	}

	public void setWaybillProcessLogDao(IWaybillProcessLogDao waybillProcessLogDao) {
		this.waybillProcessLogDao = waybillProcessLogDao;
	}

	/**
	 * @description APP/DOP扫描后返回标签信息
	 * @param waybill 运单号
	 * @param originateOrgCode 始发部门
	 * @return
	 */
	@Override
	public LabelInfoDto appScan(String waybillNo, String originateOrgCode)throws Exception{
		return lTLEWaybillHandlePdaService.appScan(waybillNo, originateOrgCode);
	}

	
	/**
	 * @description PDA和APP完成接货任务时候调用此方法
	 * @param overPickupInfo
	 */
	public void appOverTask(List<PdaAppInfoDto> pdaAppInfos){
		for (PdaAppInfoDto pdaAppInfo : pdaAppInfos) {
			/**
			 * 初始化日志
			 */
			WaybillProcessLogEntity entity = new WaybillProcessLogEntity();
			Date date = new Date();
			
			if(pdaAppInfo==null){
				entity.setId(UUIDUtils.getUUID());
				entity.setOperateResult(WaybillConstants.LTLEWAYBILL_PROCESS_LOG_FAILURE);
				entity.setLogType(WaybillConstants.LTLEWAYBILL_OPERATION_TYPE_APP_LOG);
				entity.setCreateTime(date);
				entity.setModifyTime(date);
			}else{
				try{
					String appContent = ReflectionToStringBuilder.toString(pdaAppInfo);
					entity.setId(UUIDUtils.getUUID());
					entity.setWaybillNo(pdaAppInfo.getWaybillNo());
					entity.setOperateResult(WaybillConstants.LTLEWAYBILL_PROCESS_LOG_SUCCESS);
					entity.setLogType(WaybillConstants.LTLEWAYBILL_OPERATION_TYPE_APP_LOG);
					entity.setCreateTime(date);
					entity.setModifyTime(date);
					entity.setContent(appContent);
					entity.setJobId("notJobId");
					//完成接货时添加揽收轨迹
					SynTrackingEntity track = new SynTrackingEntity();
					track.setId(UUIDUtils.getUUID());
					//运单
					track.setWayBillNo(pdaAppInfo.getWaybillNo());
					//发生时间
					track.setOperateTime(pdaAppInfo.getModifyTime());
					//创建时间
					track.setCreateDate(new Date());
					//跟踪信息描述
					track.setTrackInfo("您的订单已揽收。");
					//事件
					track.setEventType(String.valueOf("GOT"));
					pushTrackForCaiNiaoService.addSynLpsTrack(track);
					lTLEWaybillHandlePdaService.handleAppInfo(pdaAppInfo,OPERATION_TYPE_APP,null);
				}catch(BusinessException be){
					entity.setOperateResult(WaybillConstants.LTLEWAYBILL_PROCESS_LOG_FAILURE);
					String failureMessage = ExceptionUtils.getFullStackTrace(be);
					entity.setFailResion(failureMessage);
					LOGGER.error(be.getMessage(),be);
				}catch(Exception se){
					entity.setOperateResult(WaybillConstants.LTLEWAYBILL_PROCESS_LOG_FAILURE);
					String failureMessage = ExceptionUtils.getFullStackTrace(se);
					entity.setFailResion(failureMessage);
					LOGGER.error(se.getMessage(),se);
				}
			}
			/**
			 * 记录日志
			 */
			waybillProcessLogDao.addWaybillProcessLogEntity(entity);
		}
	}
	
	
	
	/**
	 * @description 提供给PDA扫描的接口不区分集中接货和非集中接货
	 * @param woodenPdaDto PDA扫描信息
	 */
	@Override
	public void pdaScan(WoodenRequirePdaDto woodenPdaDto)
			throws BusinessException {
		/**
		 * 初始化日志
		 */
		WaybillProcessLogEntity entity = new WaybillProcessLogEntity();
		Date date = new Date();
		if(woodenPdaDto==null){
			entity.setId(UUIDUtils.getUUID());
			entity.setOperateResult(WaybillConstants.LTLEWAYBILL_PROCESS_LOG_FAILURE);
			entity.setId(UUIDUtils.getUUID());
			entity.setLogType(WaybillConstants.LTLEWAYBILL_OPERATION_TYPE_PDA_LOG);
			entity.setCreateTime(date);
			entity.setModifyTime(date);
		}else{
			try{
				String content = ReflectionToStringBuilder.toString(woodenPdaDto);
				entity.setId(UUIDUtils.getUUID());
				entity.setOperateResult(WaybillConstants.LTLEWAYBILL_PROCESS_LOG_SUCCESS);
				entity.setLogType(WaybillConstants.LTLEWAYBILL_OPERATION_TYPE_PDA_LOG);
				entity.setCreateTime(date);
				entity.setModifyTime(date);
				entity.setWaybillNo(woodenPdaDto.getWaybillNo());
				entity.setContent(content);
				lTLEWaybillHandlePdaService.handlePdaInfo(woodenPdaDto);
			}catch(BusinessException be){
				entity.setOperateResult(WaybillConstants.LTLEWAYBILL_PROCESS_LOG_FAILURE);
				String failureMessage = ExceptionUtils.getFullStackTrace(be);
				entity.setFailResion(failureMessage);
				LOGGER.error(be.getMessage(),be);
			}catch(Exception se){
				entity.setOperateResult(WaybillConstants.LTLEWAYBILL_PROCESS_LOG_FAILURE);
				String failureMessage = ExceptionUtils.getFullStackTrace(se);
				entity.setFailResion(failureMessage);
				LOGGER.error(se.getMessage(),se);
			}
		}
		/**
		 * 记录日志
		 */
		waybillProcessLogDao.addWaybillProcessLogEntity(entity);
	}
	
}
