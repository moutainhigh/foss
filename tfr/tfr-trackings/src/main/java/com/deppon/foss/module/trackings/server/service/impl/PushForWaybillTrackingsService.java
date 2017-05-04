/**  
 * Project Name:tfr-trackings  
 * File Name:PushForWaybillTrackingsService.java  
 * Package Name:com.deppon.foss.module.trackings.server.service.impl  
 * Date:2015年4月16日下午7:59:14  
 *  
 */  
  
package com.deppon.foss.module.trackings.server.service.impl;  

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.trackings.api.server.dao.IPushForWaybillTrackingsDao;
import com.deppon.foss.module.trackings.api.server.service.IPushForWaybillTrackingsService;
import com.deppon.foss.module.trackings.api.server.service.IWaybillTrackingsService;
import com.deppon.foss.module.trackings.api.shared.define.WaybillTrackingsConstants;
import com.deppon.foss.module.trackings.api.shared.dto.WaybillTrackingsDto;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessGoalContants;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessSceneConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobTodoEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.TfrJobTodoQueryDto;
import com.deppon.foss.util.define.FossConstants;

/**  
 * ClassName: PushForWaybillTrackingsService <br/>  
 * Function: 供job调用，车辆出发到达的正反操作后，将车载运单存入到临时表. <br/>  
 * date: 2015年4月16日 下午7:59:14 <br/>  
 *  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
public class PushForWaybillTrackingsService implements
		IPushForWaybillTrackingsService {
	
	/**
	 * 定义每次处理的车辆任务明细个数
	 */
	private static final int count = 20;
	
	/**
	 * 用于查询待处理的任务车辆明细ID
	 */
	private ITfrJobTodoService tfrJobTodoService;
	
	/**
	 * 根据任务车辆明细ID拉取运单
	 */
	private IPushForWaybillTrackingsDao pushForWaybillTrackingsDao;
	
	/**
	 * 根据工号获取姓名和电话
	 */
	private IEmployeeService employeeService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PushForWaybillTrackingsService.class);
	
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
	 * 调用接口，存入轨迹表
	 */
	private IWaybillTrackingsService  waybillTrackingsService;
	
	public void setWaybillTrackingsService(
				IWaybillTrackingsService waybillTrackingsService) {
			this.waybillTrackingsService = waybillTrackingsService;
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
	 * employeeService.  
	 *  
	 * @param   employeeService    the employeeService to set  
	 * @since   JDK 1.6  
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
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
	 * 将车载运单存入轨迹表.  
	 * @see com.deppon.foss.module.trackings.api.server.service.IPushForWaybillTrackingsService#pushForWaybillTrackings()  
	 */
	@Override
	public int pushForWaybillTrackings() {
		//构造查询条件
		TfrJobTodoQueryDto queryDto = new TfrJobTodoQueryDto();
		queryDto.setCount(count);
		//业务场景，出发、取消出发、到达、取消到达
		String[] sceneList = new String[]{BusinessSceneConstants.BUSINESS_SCENE_TRUCK_DEPARTURE,
				BusinessSceneConstants.BUSINESS_SCENE_CANCEL_TRUCK_DEPARTURE,
				BusinessSceneConstants.BUSINESS_SCENE_TRUCK_ARRIVAL,
				BusinessSceneConstants.BUSINESS_SCENE_CANCEL_TRUCK_ARRIVAL};
		queryDto.setBusinessSceneList(sceneList);
		//业务目标，推送运单
		queryDto.setBusinessGoalList(new String[]{BusinessGoalContants.BUSINESS_GOAL_EXPRESS100});
		List<TfrJobTodoEntity> jobTodoList = tfrJobTodoService.queryJobTodo(queryDto);
		
		/**
		 * 循环处理车辆任务明细
		 */
		//记录出错日志
		TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
		for(TfrJobTodoEntity entity : jobTodoList){
			try{
				pushForWaybillTrackingsByTruckTaskDetailID(entity);
			}catch(Exception e){
				LOGGER.error("车载运单存入轨迹表遇到异常，truckTaskDetailID：" + entity.getBusinessID());
				// 记录出错日志
				jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.PUSH_FOR_WAYBILL_TRACKINGS_JOB.getBizName());
				jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.PUSH_FOR_WAYBILL_TRACKINGS_JOB.getBizCode());
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
	 * setProperties4WaybillDetail:(私有方法，为待插入运单设置属性). <br/>  
	 *  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param queriedDto 根据truckTaskDetailID获取的车载运单信息
	 * @param trkDto 待插入的运单信息
	 * @param bizScene 业务场景，四种，出发到达及其反操作
	 * @param businessTime 操作时间
	 * @param operatorCode  操作人工号
	 * @since JDK 1.6
	 */
	private void setProperties4WaybillDetail(WaybillTrackingsDto queriedDto,WaybillTrackingsDto trkDto,String bizScene,Date businessTime,String operatorCode){
		/**
		 * 这一坨为操作部门、下一部门、操作类别
		 */
		//如果为出发、取消出发，则操作部门为出发部门
		if(StringUtils.equals(bizScene, BusinessSceneConstants.BUSINESS_SCENE_TRUCK_DEPARTURE)
				|| StringUtils.equals(bizScene, BusinessSceneConstants.BUSINESS_SCENE_CANCEL_TRUCK_DEPARTURE)){
			trkDto.setOperateDeptCode(queriedDto.getOperateDeptCode());
			trkDto.setOperateDeptName(queriedDto.getOperateDeptName());
			//如果为出发，则传入下一部门
			if(StringUtils.equals(bizScene, BusinessSceneConstants.BUSINESS_SCENE_TRUCK_DEPARTURE)){
				trkDto.setOperateType(WaybillTrackingsConstants.OPERATE_TYPE_DEPART);
				trkDto.setNextDeptCode(queriedDto.getNextDeptCode());
				trkDto.setNextDeptName(queriedDto.getNextDeptName());
			}else{
				trkDto.setOperateType(WaybillTrackingsConstants.OPERATE_TYPE_CANCEL_DEPART);
			}
			//如果为到达、取消到达，则操作部门为到达部门
		}else if(StringUtils.equals(bizScene, BusinessSceneConstants.BUSINESS_SCENE_TRUCK_ARRIVAL)
				|| StringUtils.equals(bizScene, BusinessSceneConstants.BUSINESS_SCENE_CANCEL_TRUCK_ARRIVAL)){
			trkDto.setOperateDeptCode(queriedDto.getNextDeptCode());
			trkDto.setOperateDeptName(queriedDto.getNextDeptName());
			if(StringUtils.equals(bizScene, BusinessSceneConstants.BUSINESS_SCENE_TRUCK_ARRIVAL)){
				trkDto.setOperateType(WaybillTrackingsConstants.OPERATE_TYPE_ARRIVE);
			}else{
				trkDto.setOperateType(WaybillTrackingsConstants.OPERATE_TYPE_CANCEL_ARRIVE);
			}
		}
		
		/**
		 * other properties
		 */
		//运单号
		trkDto.setWaybillNo(queriedDto.getWaybillNo());
		//操作时间
		trkDto.setOperateTime(businessTime);
		//获取员工信息
		EmployeeEntity employee = employeeService.queryEmployeeByEmpCodeNoCache(operatorCode);
		if(employee != null){
			//操作人姓名及电话
			trkDto.setOperatorName(employee.getEmpName());
			trkDto.setOperatorPhone(employee.getMobilePhone());
		}
	}
	
	/**
	 * 
	 * 根据任务车辆明细ID将车载运单存入临时表，控制事务，被pushForWaybillTrackings方法调用.  
	 * @see com.deppon.foss.module.trackings.api.server.service.IPushForWaybillTrackingsService#pushForWaybillTrackingsByTruckTaskDetailID(java.lang.String)
	 */
	@Override
	@Transactional
	public int pushForWaybillTrackingsByTruckTaskDetailID(TfrJobTodoEntity entity) {
		//获取待办job信息
		String id = entity.getId();
		//车辆任务明细ID
		String detailID = entity.getBusinessID();
		//业务场景
		String bizScene = entity.getBusinessScene();
		//业务发生时间
		Date businessTime = entity.getBusinessTime();
		//操作人工号
		String operatorCode = entity.getOperatorCode();
		//查询车载运单
		List<WaybillTrackingsDto> waybillList = pushForWaybillTrackingsDao.queryWaybillDetailByTruckTaskDetailID(detailID);
		//定义待推送的车载运单明细
		List<WaybillTrackingsDto> waybillDtoList = new ArrayList<WaybillTrackingsDto>();
		for(WaybillTrackingsDto queriedDto : waybillList){
			WaybillTrackingsDto trkDto = new WaybillTrackingsDto();
			//设置属性
			this.setProperties4WaybillDetail(queriedDto, trkDto, bizScene, businessTime, operatorCode);
			waybillDtoList.add(trkDto);
		}
		waybillTrackingsService.addWaybillTracks(waybillDtoList);
		//更新待办job信息
		tfrJobTodoService.updateJobTodoByID(id);
		return FossConstants.SUCCESS;
	}

}
  
