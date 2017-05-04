/**  
 * Project Name:tfr-trackings  
 * File Name:PushForWaybillTrackingsService.java  
 * Package Name:com.deppon.foss.module.trackings.server.service.impl  
 * Date:2016年2月19日下午7:59:14  
 *  
 */  
  
package com.deppon.foss.module.trackings.server.service.impl;  
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import org.apache.commons.collections.CollectionUtils;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.inteface.domain.foss2ptp.SyncWayBillNosRequest;
import com.deppon.esb.inteface.domain.foss2ptp.ToPtpBatchDeductItem;
import com.deppon.esb.pojo.transformer.json.PushWaybillToPTPRequestTrans;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SynchronousExternalSystemException;
import com.deppon.foss.module.trackings.api.server.dao.IPushForWaybillTrackingsDao;
import com.deppon.foss.module.trackings.api.server.service.IPushForWaybillToPTPService;
import com.deppon.foss.module.trackings.api.shared.dto.WaybillTrackingsDto;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessGoalContants;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessSceneConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobTodoEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.TfrJobTodoQueryDto;
import com.deppon.foss.module.transfer.common.server.utils.JSONUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**  
 * ClassName: PushForWaybillToPTPService <br/>  
 * Function: 供job调用，车辆到达的操作后，将车载运单推送到PTP合伙人供扣款. <br/>  
 * date: 2016年2月19日 下午7:59:14 <br/>  
 *  
 * @author 189284-foss-zhangxu  
 * @version   
 * @since JDK 1.6  
 */
public class PushForWaybillToPTPService implements IPushForWaybillToPTPService{
	
	/**
	 * 定义每次处理的车辆任务明细个数
	 */
	private static final int count = 20;
	
	/**
	 * 用于查询待处理的任务车辆明细ID
	 */
	private ITfrJobTodoService tfrJobTodoService;
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private IEmployeeService employeeService;
	/**
	 * @param employeeService the employeeService to set
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/**
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 * 根据任务车辆明细ID拉取运单
	 */
	private IPushForWaybillTrackingsDao pushForWaybillTrackingsDao;
	private static final Logger LOGGER = LoggerFactory.getLogger(PushForWaybillToPTPService.class);
	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_BATCH_BOND_WITHHOLD";
	
	private static final String EXCEPTION_REMARK = "任务执行失败";
	/**
	 * 异常信息的最大程度
	 */
	private static final int EXCINFO_MAX_LENGTH = 2000;
	
	/**
	 * 用于存储异常日志
	 */
	private ITfrCommonService tfrCommonService;

	/**  
	 * tfrCommonService.  
	 *  
	 * @param   tfrCommonService    the tfrCommonService to set  
	 * @since   JDK 1.6  
	 */
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}
	
	/**  
	 * tfrJobTodoService.  
	 *  
	 * @return  the tfrJobTodoService  
	 * @since   JDK 1.6  
	 */
	public ITfrJobTodoService getTfrJobTodoService() {
		return tfrJobTodoService;
	}

	/**  
	 * tfrJobTodoService.  
	 *  
	 * @param   tfrJobTodoService    the tfrJobTodoService to set  
	 * @since   JDK 1.6  
	 */
	public void setTfrJobTodoService(ITfrJobTodoService tfrJobTodoService) {
		this.tfrJobTodoService = tfrJobTodoService;
	}
	/**  
	 * pushForWaybillTrackingsDao.  
	 *  
	 * @return  the pushForWaybillTrackingsDao  
	 * @since   JDK 1.6  
	 */
	public IPushForWaybillTrackingsDao getPushForWaybillTrackingsDao() {
		return pushForWaybillTrackingsDao;
	}

	/**  
	 * pushForWaybillTrackingsDao.  
	 *  
	 * @param   pushForWaybillTrackingsDao    the pushForWaybillTrackingsDao to set  
	 * @since   JDK 1.6  
	 */
	public void setPushForWaybillTrackingsDao(
			IPushForWaybillTrackingsDao pushForWaybillTrackingsDao) {
		this.pushForWaybillTrackingsDao = pushForWaybillTrackingsDao;
	}

	/**  
	 * 将车载运单推送到PTP合伙人 
	 * @see com.deppon.foss.module.trackings.api.server.service.IPushForWaybillTrackingsService#pushForWaybillTrackings()  
	 */
	@Override
	public int pushForWaybillTrackings() {
		//构造查询条件
		TfrJobTodoQueryDto queryDto = new TfrJobTodoQueryDto();
		queryDto.setCount(count);
		//业务场景，出发、取消出发、到达、取消到达
		String[] sceneList = new String[]{
				BusinessSceneConstants.BUSINESS_SCENE_TRUCK_ARRIVAL,
				BusinessSceneConstants.BUSINESS_SCENE_TRUCK_UNLOAD
				};
		queryDto.setBusinessSceneList(sceneList);
		//业务目标，推送运单
		queryDto.setBusinessGoalList(new String[]{BusinessGoalContants.BUSINESS_GOAL_TO_PTP});
		List<TfrJobTodoEntity> jobTodoList = tfrJobTodoService.queryJobTodo(queryDto);
		
		/**
		 * 循环处理车辆任务明细
		 */
		//记录出错日志
		TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
		for(TfrJobTodoEntity entity : jobTodoList){
			 String businessScene = entity.getBusinessScene();
			try{
				//到达确认  扣款运单明细
				if(BusinessSceneConstants.BUSINESS_SCENE_TRUCK_ARRIVAL.equals(businessScene)){
					pushForWaybillToPTPByTruckTaskDetailID(entity);
				}else if(BusinessSceneConstants.BUSINESS_SCENE_TRUCK_UNLOAD.equals(businessScene)){
					pushForWaybillToPTPByUnloadTaskID(entity);
				}				
			}catch(Exception e){
				LOGGER.error("车载运单推送到PTP合伙人遇到异常，truckTaskDetailID：" + entity.getBusinessID());
				// 记录出错日志
				jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.PUSH_FOR_WAYBILL_PTP_JOB.getBizName());
				jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.PUSH_FOR_WAYBILL_PTP_JOB.getBizCode());
				jobProcessLogEntity.setRemark(EXCEPTION_REMARK);
				jobProcessLogEntity.setExceptionInfo(StringUtils.substring(ExceptionUtils.getFullStackTrace(e), 0, EXCINFO_MAX_LENGTH));
				jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
				tfrCommonService.addJobProcessLog(jobProcessLogEntity);
				continue;
			}
		}
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 
	 * 根据任务车辆明细ID将车载运单推送给PTP被pushForWaybillTrackings方法调用.  
	 * @see com.deppon.foss.module.trackings.api.server.service.IPushForWaybillTrackingsService#pushForWaybillTrackingsByTruckTaskDetailID(java.lang.String)
	 */
	@Transactional
	private int pushForWaybillToPTPByTruckTaskDetailID(TfrJobTodoEntity entity) {
		//获取待办job信息
		String id = entity.getId();
		//车辆任务明细ID
		String detailID = entity.getBusinessID();
		String operatorCode=entity.getOperatorCode();
		String operatorName=null;
		if(StringUtils.isNotEmpty(entity.getOperatorCode())){
			operatorName=employeeService.queryEmpNameByEmpCode(entity.getOperatorCode());
		}
		/*else{
			throw new SynchronousExternalSystemException("同步操作人工号，名称为空");
		}*/
		//查询车载运单
		List<WaybillTrackingsDto> waybillList = pushForWaybillTrackingsDao.queryWaybillDetailByTruckTaskDetailID(detailID);
	    pushWaybillToPTP(waybillList,operatorCode,operatorName,"到达确认扣款");
		//更新待办job信息
		tfrJobTodoService.updateJobTodoByID(id);
		return FossConstants.SUCCESS;
	}
	
	private int pushForWaybillToPTPByUnloadTaskID(TfrJobTodoEntity entity) {
		// 获取待办job信息
		String id = entity.getId();
		// 车辆任务明细ID
		String detailID = entity.getBusinessID();
		String operatorCode = entity.getOperatorCode();
		String operatorName = null;
		if (StringUtils.isNotEmpty(entity.getOperatorCode())) {
			operatorName = employeeService.queryEmpNameByEmpCode(entity
					.getOperatorCode());
		}
		/*
		 * else{ throw new SynchronousExternalSystemException("同步操作人工号，名称为空"); }
		 */
		// 查询车载运单
		List<WaybillTrackingsDto> waybillList = pushForWaybillTrackingsDao
				.queryWaybillDetailByUnloadTaskID(detailID);
		pushWaybillToPTP(waybillList, operatorCode, operatorName, "卸车 确认扣款");
		// 更新待办job信息
		tfrJobTodoService.updateJobTodoByID(id);
		return FossConstants.SUCCESS;
	}
	/**
	 * 设置 json 数据格式
	 * 
	 * @author 189284-zhangxu 
	 * @date 2013-08-01 上午9:21:30
	 */
	public static ObjectMapper obtainJSONObjectMapper() {
		// 获取objectMapper
		ObjectMapper objectMapper = JSONUtils.obtainObjectMapper();
		// 设置时间转换格式
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
		// 设置到objectMapper
		objectMapper.setDateFormat(dateFormat);
		
		return objectMapper;
	}
	/**
	 * @author 189284-foss-zhangxu
	 * @date 2016年2月19日 下午5:08:14
	 * @function 推送运单号给PTP合伙人
	 * @param requestDto
	 * @param operatorCode 操作人编码
	 * @param operatorName 操作人名称
	 * @param  businessDesc2场景描述
	 * @return
	 */
	@Override
	public void pushWaybillToPTP(List<WaybillTrackingsDto> waybillList,String operatorCode,String operatorName,String businessDesc2 ) {
		try{
			if(CollectionUtils.isNotEmpty(waybillList)){
				SyncWayBillNosRequest wayBillNoRequest = new SyncWayBillNosRequest();
				List<ToPtpBatchDeductItem> wayBillNoList = new ArrayList<ToPtpBatchDeductItem>();
				for (WaybillTrackingsDto waybillTrackingsDto : waybillList) {
					if(StringUtils.isNotEmpty(waybillTrackingsDto.getWaybillNo())){
						ToPtpBatchDeductItem wayBillNos=new ToPtpBatchDeductItem();
						wayBillNos.setWaybillNo(waybillTrackingsDto.getWaybillNo());
						/**
						 * 根据code返回部门标杆编码
						 */
						String partnerOrgCode=orgAdministrativeInfoService.queryUnifiedCodeByCode(waybillTrackingsDto.getNextDeptCode());
						wayBillNos.setPartnerOrgCode(partnerOrgCode);
						//运单到达时间
						Date instockTime = waybillTrackingsDto.getInStockTime();
						if(instockTime != null){
							wayBillNos.setInStockTime(instockTime);
						}
						wayBillNos.setOperatorCode(operatorCode);
						wayBillNos.setOperatorName(operatorName);
						wayBillNoList.add(wayBillNos);
					}else{
						continue;
					}
				}
				wayBillNoRequest.getDetail().addAll(wayBillNoList);
				
				AccessHeader accessHeader = new AccessHeader();
				accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
				accessHeader.setBusinessId(UUIDUtils.getUUID());
				accessHeader.setBusinessDesc1("合伙人同运步 单号  到PTP平台");
				accessHeader.setBusinessDesc2(businessDesc2);
				accessHeader.setVersion("0.1");
				LOGGER.info("start 合伙人到达确认同运单号接口 到PTP平台：\n"
					+ new PushWaybillToPTPRequestTrans().fromMessage(wayBillNoRequest));
				ESBJMSAccessor.asynReqeust(accessHeader, wayBillNoRequest);
				LOGGER.info("end 合伙人到达确认同运单号接口 到PTP平台：\n"
					+ new PushWaybillToPTPRequestTrans()
						.fromMessage(wayBillNoRequest));
			    }
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
  				e.printStackTrace();
			    throw new SynchronousExternalSystemException("合伙人到达确认同运单号接口 到PTP平台", "合伙人到达确认同运单号接口 到PTP平台 发送数据到ESB错误");
			}
		
	}
}
  
