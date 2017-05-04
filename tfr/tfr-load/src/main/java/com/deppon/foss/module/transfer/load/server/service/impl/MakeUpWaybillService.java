/**
 * 
 */
package com.deppon.foss.module.transfer.load.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.load.api.server.dao.IMakeUpWaybillDao;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.server.service.ILoaderWorkloadService;
import com.deppon.foss.module.transfer.load.api.server.service.IMakeUpWaybillService;
import com.deppon.foss.module.transfer.load.api.server.service.IPDACommonService;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.module.transfer.load.api.shared.domain.MakeUpWaybillEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * @author niuly
 *
 */
public class MakeUpWaybillService implements IMakeUpWaybillService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MakeUpWaybillService.class);
	//dao
	private IMakeUpWaybillDao makeUpWaybillDao;
	
	private ITfrCommonService tfrCommonService;
	
	private IHandOverBillService handOverBillService;
	
	private ILoaderWorkloadService loaderWorkloadService;
	
	private IVehicleAssembleBillService vehicleAssembleBillService;
	
	private IPDACommonService pdaCommonService;
	
	
	/**
	 * @param handOverBillService the handOverBillService to set
	 */
	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}

	/**
	 * @param loaderWorkloadService the loaderWorkloadService to set
	 */
	public void setLoaderWorkloadService(
			ILoaderWorkloadService loaderWorkloadService) {
		this.loaderWorkloadService = loaderWorkloadService;
	}

	/**
	 * @param vehicleAssembleBillService the vehicleAssembleBillService to set
	 */
	public void setVehicleAssembleBillService(
			IVehicleAssembleBillService vehicleAssembleBillService) {
		this.vehicleAssembleBillService = vehicleAssembleBillService;
	}

	/**
	 * @param pdaCommonService the pdaCommonService to set
	 */
	public void setPdaCommonService(IPDACommonService pdaCommonService) {
		this.pdaCommonService = pdaCommonService;
	}

	/**
	 * @param makeUpWaybillDao the makeUpWaybillDao to set
	 */
	public void setMakeUpWaybillDao(IMakeUpWaybillDao makeUpWaybillDao) {
		this.makeUpWaybillDao = makeUpWaybillDao;
	}

	/**
	 * @param tfrCommonService the tfrCommonService to set
	 */
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}

	/**
	 * @author niuly
	 * @date 2014-02-12 09:39:20
	 * @function 运单补录重量或体积发生变化或PDA更新重量体积时调用此方法
	 * @param entity
	 */
	@Override
	public void addWaybillInfoForTfr(MakeUpWaybillEntity entity) {
		entity.setId(UUIDUtils.getUUID());
		makeUpWaybillDao.addWaybillInfo(entity);
	}

	/**
	 * @author niuly
	 * @date 2014-2-12下午2:35:36
	 * @function 处理运单补录或PDA更新重量体积数据
	 * @param threadNo
	 * @param threadCount
	 */
	@Override
	public void doMakeUpWaybillInfo(int threadNo, int threadCount) {
		//查询需处理的运单信息
		List<MakeUpWaybillEntity> entityList = new ArrayList<MakeUpWaybillEntity>();
		entityList = makeUpWaybillDao.queryMakeUpWaybill();
		//若不存在则退出
		if(entityList.size() == 0) {
			return;
		}
		
		//过滤重复运单信息
		List<MakeUpWaybillEntity> waybillList = new ArrayList<MakeUpWaybillEntity>();
		//存储运单号重复记录的id
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		
		for(MakeUpWaybillEntity entity:entityList) {
			String waybillNo = entity.getWaybillNo();
			//若不存在，则添加至waybillList中
			if(!map.containsKey(waybillNo)) {
				waybillList.add(entity);
				List<String> list = new ArrayList<String>();
				list.add(entity.getId());
				map.put(waybillNo, list);
			} else {
				map.get(waybillNo).add(entity.getId());
			}
		}
		
		//循环处理各个运单
		for(MakeUpWaybillEntity waybill:waybillList) {
			LOGGER.info("运单补录开始,运单号："+waybill.getWaybillNo()+",重量：" + waybill.getWeight()+",体积：" + waybill.getVolume()+",件数：" + waybill.getQuantity());
			//更新交接单
			try{
				handOverBillService.updateHandOverbillforMakeUpWaybill(waybill);
				LOGGER.info("------------更新交接单成功-----------------");
			}catch(Exception e){
				String remark = "运单补录更新交接单信息失败,运单号："+waybill.getWaybillNo()+",重量：" + waybill.getWeight()+",体积：" + waybill.getVolume()+",件数：" + waybill.getQuantity();
				
				TfrJobProcessLogEntity jobProcessLogEntity = this.getLogEntity(remark);
				jobProcessLogEntity.setExecBizId(waybill.getId());
				jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
				
				tfrCommonService.addJobProcessLog(jobProcessLogEntity);
			}
			//更新配载单
			try{
				vehicleAssembleBillService.updateVehAssemForMakeUpWaybill(waybill);
				LOGGER.info("------------更新配载单成功-----------------");
			}catch(Exception e){
				String remark = "运单补录更新配载单信息失败,运单号："+waybill.getWaybillNo()+",重量：" + waybill.getWeight()+",体积：" + waybill.getVolume()+",件数：" + waybill.getQuantity();

				TfrJobProcessLogEntity jobProcessLogEntity = this.getLogEntity(remark);
				jobProcessLogEntity.setExecBizId(waybill.getId());
				jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
				
				tfrCommonService.addJobProcessLog(jobProcessLogEntity);
			}
			//更新装卸车工作量
			try{
				pdaCommonService.makeUpPDAloadAndUnload(waybill);
				
				loaderWorkloadService.reCalculateWorkLoad(waybill);

				LOGGER.info("------------更新装卸车工作量成功-----------------");
			}catch(Exception e){
				String remark = "运单补录更新装卸车工作量信息失败,运单号："+waybill.getWaybillNo()+",重量：" + waybill.getWeight()+",体积：" + waybill.getVolume()+",件数：" + waybill.getQuantity();
				
				TfrJobProcessLogEntity jobProcessLogEntity = this.getLogEntity(remark);
				jobProcessLogEntity.setExecBizId(waybill.getId());
				jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
				
				tfrCommonService.addJobProcessLog(jobProcessLogEntity);
			}
			//更新标志位和处理时间
			//一个运单对应多条记录时，使用同一个处理时间
			Date doneTime = new Date();
			//运单号对应的ids
			List<String> waybillIdList = map.get(waybill.getWaybillNo());
			for(String waybillId:waybillIdList) {
				makeUpWaybillDao.updateMakeUpWaybill(waybillId,doneTime);
			}
			
			LOGGER.info("运单补录结束,运单号："+waybill.getWaybillNo()+",重量：" + waybill.getWeight()+",体积：" + waybill.getVolume()+",件数：" + waybill.getQuantity());
		}
	}
	
	/**
	 * @author niuly
	 * @date 2014-2-12下午3:26:47
	 * @function 获取job日志实体，用于记录错误日志 
	 * @param remark
	 * @return TfrJobProcessLogEntity
	 */
	private TfrJobProcessLogEntity getLogEntity(String remark) {
		LOGGER.error(remark);
		
		TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
		jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.MAKEUP_WAYBILL_JOB.getBizName());
		jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.MAKEUP_WAYBILL_JOB.getBizCode());
		jobProcessLogEntity.setExecTableName("TFR.T_OPT_MAKEUP_WAYBILL");
		jobProcessLogEntity.setRemark(remark);
		jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
		
		return jobProcessLogEntity;
	}

}
