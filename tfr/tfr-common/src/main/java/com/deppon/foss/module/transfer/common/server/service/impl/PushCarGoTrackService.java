package com.deppon.foss.module.transfer.common.server.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
//import com.deppon.foss.module.trackings.api.shared.dto.TaobaoTrackingsRequestDto;
import com.deppon.foss.module.transfer.common.api.server.dao.IPushCarGoTrackDao;
import com.deppon.foss.module.transfer.common.api.server.multithreading.TheadPool;
import com.deppon.foss.module.transfer.common.api.server.service.IPushCarGoTrackService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessGoalContants;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessSceneConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobTodoEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.TfrJobTodoQueryDto;
import com.deppon.foss.util.define.FossConstants;


/***
 * 货物轨迹推送接口
 *货物轨迹信息存储到异步表，以便后续推送
 *用于不同 业务场景的货物轨迹存储service
 * @author 205109-zenghaibin-foss
 * @date 2015-04-27 上午11:13:20
 ****/
public class PushCarGoTrackService extends TheadPool implements IPushCarGoTrackService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PushCarGoTrackService.class);

	/**待处理job数据service**/
	private ITfrJobTodoService tfrJobTodoService;
	/**通用service**/
	private ITfrCommonService tfrCommonService;
	/**货物轨迹推送dao**/
	private IPushCarGoTrackDao pushCarGoTrackDao;
	
	private IConfigurationParamsService configurationParamsService;
	
	
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	

	public void setTfrJobTodoService(ITfrJobTodoService tfrJobTodoService) {
		this.tfrJobTodoService = tfrJobTodoService;
	}


	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}


	public void setPushCarGoTrackDao(IPushCarGoTrackDao pushCarGoTrackDao) {
		this.pushCarGoTrackDao = pushCarGoTrackDao;
	}

	
	/**
	* @description 给DOP推送货物轨迹信息
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.service.IPushCarGoTrackService#pushTrackForDop(java.util.Date, java.util.Date, int, int)
	* @author 218381-foss-lijie
	* @update 2015年5月21日 下午2:05:04
	* @version V1.0
	*/
	@Override
	public void pushTrackForDop(Date bizJobStartTime, Date bizJobEndTime,
			int threadNo, int threadCount) {
		TfrJobTodoQueryDto queryDto=new TfrJobTodoQueryDto();
		queryDto.setBizEndTime(bizJobEndTime);
		queryDto.setBizStartTime(bizJobStartTime);
		queryDto.setCount(TransferConstants.SONAR_NUMBER_500);
		List<TfrJobTodoEntity> tfrJobToDoEntityList= tfrJobTodoService.queryJobTodoToComm(queryDto);
		
		if(tfrJobToDoEntityList!=null&&!tfrJobToDoEntityList.isEmpty()){
			
			for( TfrJobTodoEntity entity:tfrJobToDoEntityList){
			
					//把  到达  货物轨迹存储到轨迹表
					if(entity !=null && entity.getBusinessScene().equals(BusinessSceneConstants.BUSINESS_SCENE_TRUCK_ARRIVAL)){
						try{
							pushCarGoTrackDao.pushArrivalTrack(entity.getBusinessID(),"Y");
						}catch(Exception e){
							LOGGER.error(ExceptionUtils.getFullStackTrace(e));
							
							TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
							jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.PUSH_SENTCITY_CARGO_TRACK_JOB.getBizName());
							jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.PUSH_SENTCITY_CARGO_TRACK_JOB.getBizCode());
							jobProcessLogEntity.setExecBizId(entity.getId());
							jobProcessLogEntity.setExecTableName("tfr.t_opt_expresstrack_external");
							jobProcessLogEntity.setRemark("处理到达城市货物轨迹插入轨迹表");
							jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
							jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
							
							tfrCommonService.addJobProcessLog(jobProcessLogEntity);
						}
					}
						//把  出发 货物轨迹存储到轨迹表	
					else if(entity !=null && entity.getBusinessScene().equals(BusinessSceneConstants.BUSINESS_SCENE_TRUCK_DEPARTURE)){
						try{
							pushCarGoTrackDao.pushDepartureTrack(entity.getBusinessID(),"Y");
						}catch(Exception e){
							LOGGER.error(ExceptionUtils.getFullStackTrace(e));
							
							TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
							jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.PUSH_SENTCITY_CARGO_TRACK_JOB.getBizName());
							jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.PUSH_SENTCITY_CARGO_TRACK_JOB.getBizCode());
							jobProcessLogEntity.setExecBizId(entity.getId());
							jobProcessLogEntity.setExecTableName("tfr.t_opt_expresstrack_external");
							jobProcessLogEntity.setRemark("处理到达城市货物轨迹插入轨迹表");
							jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
							jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
							
							tfrCommonService.addJobProcessLog(jobProcessLogEntity);
						}
					}
						//把  派送扫描  货物轨迹存储到轨迹表
					else if(entity !=null && entity.getBusinessScene().equals(BusinessSceneConstants.BUSINESS_SCENE_SENT_SCAN)){
						try{
							pushCarGoTrackDao.pushSentScanTrack(entity.getBusinessID(),"Y");
						}catch(Exception e){
							LOGGER.error(ExceptionUtils.getFullStackTrace(e));
							
							TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
							jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.PUSH_SENTCITY_CARGO_TRACK_JOB.getBizName());
							jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.PUSH_SENTCITY_CARGO_TRACK_JOB.getBizCode());
							jobProcessLogEntity.setExecBizId(entity.getId());
							jobProcessLogEntity.setExecTableName("tfr.t_opt_expresstrack_external");
							jobProcessLogEntity.setRemark("处理到达城市货物轨迹插入轨迹表");
							jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
							jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
							
							tfrCommonService.addJobProcessLog(jobProcessLogEntity);
						
						}
					}
					//把  签收  货物轨迹存储到轨迹表
//					else if(entity !=null && entity.getBusinessScene().equals(BusinessSceneConstants.BUSINESS_SCENE_SENT_SCAN)){
//						pushCarGoTrackDao.pushArrivalTrack(entity.getBusinessID(),"Y");
//					}
					//把待办job数据迁移，然后删除
					tfrJobTodoService.updateJobTodoByID(entity.getId());
				
				
			}
			
		}
		
	
		
	}

	/**
	* @description 给DOP推送货物轨迹信息 线程调用
	* 查询出job1todo表的数据,根据业务id分析出轨迹信息 添加到轨迹表中,之后添加job1todo_Migration,最后删除job1todo表中的该记录
	* @param count
	* @param orderChannel
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年5月28日 上午10:47:16
	*/
	@Override
	public int pushTrackForDop(int count,List<String> list) {
		int backInt=0;
//		TfrJobTodoQueryDto queryDto=new TfrJobTodoQueryDto();
//		queryDto.setCount(count); 
		String jobId = tfrJobTodoService.updateAndGetJobId(count, list);
		
	//	List<TfrJobTodoEntity> tfrJobToDoEntityList= tfrJobTodoService.queryJobTodoToComm(queryDto);
		//通过jobid查询代办job信息
		List<TfrJobTodoEntity> tfrJobToDoEntityList= tfrJobTodoService.queryJobTodoToCommByJobId(count,jobId,list);
		if(tfrJobToDoEntityList!=null&&!tfrJobToDoEntityList.isEmpty()){ 
			backInt = tfrJobToDoEntityList.size();
			for(TfrJobTodoEntity entity:tfrJobToDoEntityList){
				pushThreadsPool(entity);
			}
		}
		
		return backInt;
	}

	@Override
	@Transactional
	public void businessExecutor(Object obj) {
		TfrJobTodoEntity entity  = (TfrJobTodoEntity) obj;
		//查询已处理的业务id
		int checkNum = 0;
		if(entity != null && entity.getBusinessGoal()!=null){
			//todo开关和dop开关是键值对的关系,以后要做优化,目前只有天猫家装
			if(StringUtils.equals("BUSINESS_GOAL_TO_TAOBAOJZ", entity.getBusinessGoal())){
				checkNum = tfrJobTodoService.selectJobTodoMigrationByBusinessId(entity.getBusinessID(),
						entity.getBusinessGoal(),entity.getBusinessScene());
				entity.setBusinessGoal("TAOBAOJZ");
			}else if(StringUtils.equals("BUSINESS_GOAL_TO_TAOBAO", entity.getBusinessGoal())){
				checkNum = tfrJobTodoService.selectJobTodoMigrationByBusinessId(entity.getBusinessID(),
						entity.getBusinessGoal(),entity.getBusinessScene());
				entity.setBusinessGoal("TAOBAO");
			}else if(StringUtils.equals("BUSINESS_GOAL_TO_JIAWAYUN", entity.getBusinessGoal())){
				checkNum = tfrJobTodoService.selectJobTodoMigrationByBusinessId(entity.getBusinessID(),
						entity.getBusinessGoal(),entity.getBusinessScene());
				entity.setBusinessGoal("JIAWAYUN");
			}else{
				//没有匹配到键值对的 空处理
				entity.setBusinessGoal("");
			}
			//获取业务类型：Taobao或者天猫家庄或者家洼云（查询历史记录是否有记录 如果没有记录 进行轨迹表插入 否则直接移动到todo的日志表中）
			String orderChannel=entity.getBusinessGoal();
			if(checkNum == 0){
				//往轨迹表插值
				this.doPush(entity,orderChannel);
			}else{
				tfrJobTodoService.updateJobTodoByID(entity.getId());
			}
		}
		
	}

	
	/**
	 * 往轨迹表里插值,根据不同的业务节点进行插入
	 *@author 205109-zenghaibin 
	 *@date 2015-07-10 14:50:00 
	 * @param entity待处理的job
	 * @param orderChannel 订单来源
	 ***/
	private void doPush(TfrJobTodoEntity entity,String orderChannel){
		
		Log.info("往轨迹表里插值：bussId->"+entity.getBusinessID()+"；业务场景->"+entity.getBusinessScene());
		if(StringUtils.equals(orderChannel, "TAOBAOJZ")){
			//如果是天猫家庄
			//把“到达”货物轨迹存储到轨迹表
			if(entity !=null && entity.getBusinessScene().equals(BusinessSceneConstants.BUSINESS_SCENE_TRUCK_ARRIVAL)){
				try{
					//把到达的数据写入tfr.t_opt_expresstrack_external(提供给第三方的货物轨迹)表中
					pushCarGoTrackDao.pushArrivalTrack(entity.getBusinessID(),orderChannel);
					//把待办job数据迁移，然后删除
					tfrJobTodoService.updateJobTodoByID(entity.getId());
				}catch(Exception e){
					Log.error("往job异常信息表插入日志:"+e.getMessage());
					//往job异常信息表插入日志
					this.dealException(entity.getId(),TfrJobBusinessTypeEnum.PUSH_TRUCK_ARRIVAL_TRACK_JOB.getBizName(),
							TfrJobBusinessTypeEnum.PUSH_TRUCK_ARRIVAL_TRACK_JOB.getBizCode(),e);
				}
			}else if(entity !=null && entity.getBusinessScene().equals(BusinessSceneConstants.BUSINESS_SCENE_TRUCK_DEPARTURE)){
				//把  出发 货物轨迹存储到轨迹表
				try{
					pushCarGoTrackDao.pushDepartureTrack(entity.getBusinessID(),entity.getBusinessGoal());
					//把待办job数据迁移，然后删除
					tfrJobTodoService.updateJobTodoByID(entity.getId());
				}catch(Exception e){
					Log.error("往job异常信息表插入日志:"+e.getMessage());
					//往job异常信息表插入日志
					this.dealException(entity.getId(),TfrJobBusinessTypeEnum.PUSH_TRUCK_DEPARTURE_TRACK_JOB.getBizName(),
							TfrJobBusinessTypeEnum.PUSH_TRUCK_DEPARTURE_TRACK_JOB.getBizCode(),e);
				}
			}else if(entity !=null && entity.getBusinessScene().equals(BusinessSceneConstants.BUSINESS_SCENE_SENT_SCAN)){
				//把  派送扫描  货物轨迹存储到轨迹表
				try{
					pushCarGoTrackDao.pushSentScanTrack(entity.getBusinessID(),entity.getBusinessGoal());
					//把待办job数据迁移，然后删除
					tfrJobTodoService.updateJobTodoByID(entity.getId());
				}catch(Exception e){
					Log.error("往job异常信息表插入日志:"+e.getMessage());
					//往job异常信息表插入日志
					this.dealException(entity.getId(),TfrJobBusinessTypeEnum.PUSH_SENT_SCAN_TRACK_JOB.getBizName(),
							TfrJobBusinessTypeEnum.PUSH_SENT_SCAN_TRACK_JOB.getBizCode(),e);
				}
			}else {
				//把jobid设置为"N/A"
				tfrJobTodoService.updateJobIdToNA(entity.getId());
			}
		}else if(StringUtils.equals(orderChannel, "JIAWAYUN")){

			//如果是家洼云
			//把“到达”货物轨迹存储到轨迹表
			if(entity !=null && entity.getBusinessScene().equals(BusinessSceneConstants.BUSINESS_SCENE_TRUCK_ARRIVAL)){
				try{
					//把到达的数据写入tfr.t_opt_expresstrack_external(提供给第三方的货物轨迹)表中
					pushCarGoTrackDao.pushArrivalTrack(entity.getBusinessID(),orderChannel);
					//把待办job数据迁移，然后删除
					tfrJobTodoService.updateJobTodoByID(entity.getId());
				}catch(Exception e){
					Log.error("往job异常信息表插入日志:"+e.getMessage());
					//往job异常信息表插入日志
					this.dealException(entity.getId(),TfrJobBusinessTypeEnum.PUSH_TRUCK_ARRIVAL_TRACK_JOB.getBizName(),
							TfrJobBusinessTypeEnum.PUSH_TRUCK_ARRIVAL_TRACK_JOB.getBizCode(),e);
				}
			}else if(entity !=null && entity.getBusinessScene().equals(BusinessSceneConstants.BUSINESS_SCENE_TRUCK_DEPARTURE)){
				//把  出发 货物轨迹存储到轨迹表
				try{
					pushCarGoTrackDao.pushDepartureTrack(entity.getBusinessID(),entity.getBusinessGoal());
					//把待办job数据迁移，然后删除
					tfrJobTodoService.updateJobTodoByID(entity.getId());
				}catch(Exception e){
					Log.error("往job异常信息表插入日志:"+e.getMessage());
					//往job异常信息表插入日志
					this.dealException(entity.getId(),TfrJobBusinessTypeEnum.PUSH_TRUCK_DEPARTURE_TRACK_JOB.getBizName(),
							TfrJobBusinessTypeEnum.PUSH_TRUCK_DEPARTURE_TRACK_JOB.getBizCode(),e);
				}
			}else if(entity !=null && entity.getBusinessScene().equals(BusinessSceneConstants.BUSINESS_SCENE_SENT_SCAN)){
				//把  派送扫描  货物轨迹存储到轨迹表
				try{
					pushCarGoTrackDao.pushSentScanTrack(entity.getBusinessID(),entity.getBusinessGoal());
					//把待办job数据迁移，然后删除
					tfrJobTodoService.updateJobTodoByID(entity.getId());
				}catch(Exception e){
					Log.error("往job异常信息表插入日志:"+e.getMessage());
					//往job异常信息表插入日志
					this.dealException(entity.getId(),TfrJobBusinessTypeEnum.PUSH_SENT_SCAN_TRACK_JOB.getBizName(),
							TfrJobBusinessTypeEnum.PUSH_SENT_SCAN_TRACK_JOB.getBizCode(),e);
				}
			}else {
				//把jobid设置为"N/A"
				tfrJobTodoService.updateJobIdToNA(entity.getId());
			}
		
		}else{
			//如果是淘宝
			//把“到达”货物轨迹存储到轨迹表
			if(entity !=null && entity.getBusinessScene().equals(BusinessSceneConstants.BUSINESS_SCENE_TRUCK_ARRIVAL)){
				try{
					//到达轨迹
					pushCarGoTrackDao.pushArrivalTrackNew(entity.getBusinessID(),BusinessGoalContants.BUSINESS_GOAL_TO_TAOBAO);//pushArrivalTrackNew(entity.getBusinessID(),orderChannel)
					//到达城市
					pushCarGoTrackDao.pushSentCityTrackNew(entity.getBusinessID(),BusinessGoalContants.BUSINESS_GOAL_TO_TAOBAO);//pushSentCityTrackNew(entity.getBusinessID(),null)
					//把待办job数据迁移，然后删除
					tfrJobTodoService.updateJobTodoByID(entity.getId());
				}catch(Exception e){
					Log.error("往job异常信息表插入日志:"+e.getMessage());
					//往job异常信息表插入日志
					this.dealException(entity.getId(),TfrJobBusinessTypeEnum.PUSH_TRUCK_ARRIVAL_TRACK_JOB.getBizName(),
							TfrJobBusinessTypeEnum.PUSH_TRUCK_ARRIVAL_TRACK_JOB.getBizCode(),e);
				}
			}else if(entity !=null && entity.getBusinessScene().equals(BusinessSceneConstants.BUSINESS_SCENE_TRUCK_DEPARTURE)){
				//把  出发 货物轨迹存储到轨迹表
				try{
					pushCarGoTrackDao.pushDepartureTrackNew(entity.getBusinessID(),BusinessGoalContants.BUSINESS_GOAL_TO_TAOBAO);
					//把待办job数据迁移，然后删除
					tfrJobTodoService.updateJobTodoByID(entity.getId());
				}catch(Exception e){
					Log.error("往job异常信息表插入日志:"+e.getMessage());
					//往job异常信息表插入日志
					this.dealException(entity.getId(),TfrJobBusinessTypeEnum.PUSH_TRUCK_DEPARTURE_TRACK_JOB.getBizName(),
							TfrJobBusinessTypeEnum.PUSH_TRUCK_DEPARTURE_TRACK_JOB.getBizCode(),e);
				}
			}else if(entity !=null && entity.getBusinessScene().equals(BusinessSceneConstants.BUSINESS_SCENE_SENT_SCAN)){
				//把  派送扫描  货物轨迹存储到轨迹表
				try{
					pushCarGoTrackDao.pushSentScanTrackNew(entity.getBusinessID(),null);
					//把待办job数据迁移，然后删除
					tfrJobTodoService.updateJobTodoByID(entity.getId());
				}catch(Exception e){
					Log.error("往job异常信息表插入日志:"+e.getMessage());
					//往job异常信息表插入日志
					this.dealException(entity.getId(),TfrJobBusinessTypeEnum.PUSH_SENT_SCAN_TRACK_JOB.getBizName(),
							TfrJobBusinessTypeEnum.PUSH_SENT_SCAN_TRACK_JOB.getBizCode(),e);
				}
			}
			//直发中转场提交任务时生成出站轨迹 duhao-276198-20151023
			else if(entity != null && entity.getBusinessScene().equals(BusinessSceneConstants.BUSINESS_SCENE_TRUCK_STRAIGHT_DEPARTURE)){
				//把  直发中转场提交任务时生成出站轨迹  货物轨迹存储到轨迹表
				try{
					pushCarGoTrackDao.pushStraightDepartureTrack(entity.getBusinessID(), BusinessGoalContants.BUSINESS_GOAL_TO_TAOBAO);
					//把待办job数据迁移，然后删除
					tfrJobTodoService.updateJobTodoByID(entity.getId());
				}catch(Exception e){
					Log.error("往job异常信息表插入日志:"+e.getMessage());
					//往job异常信息表插入日志
					this.dealException(entity.getId(),TfrJobBusinessTypeEnum.PUSH_TRUCK_STRAIGHT_DEPARTURE_TRACK_JOB.getBizName(),
							TfrJobBusinessTypeEnum.PUSH_TRUCK_STRAIGHT_DEPARTURE_TRACK_JOB.getBizCode(),e);
				}
			}
			//外场分配快递集中接货卸车任务时候生成  duhao-276198-20151023			
			else if(entity != null && entity.getBusinessScene().equals(BusinessSceneConstants.BUSINESS_SCENE_TRUCK_STRAIGHT_ARRIVAL)){
				//把  直发中转场提交任务时生成出站轨迹  货物轨迹存储到轨迹表
				try{
					pushCarGoTrackDao.pushStraightArrivalTrack(entity.getBusinessID(), BusinessGoalContants.BUSINESS_GOAL_TO_TAOBAO);
					//把待办job数据迁移，然后删除
					tfrJobTodoService.updateJobTodoByID(entity.getId());
				}catch(Exception e){
					Log.error("往job异常信息表插入日志:"+e.getMessage());
					//往job异常信息表插入日志
					this.dealException(entity.getId(),TfrJobBusinessTypeEnum.PUSH_TRUCK_STRAIGHT_ARRIVAL_TRACK_JOB.getBizName(),
							TfrJobBusinessTypeEnum.PUSH_TRUCK_STRAIGHT_ARRIVAL_TRACK_JOB.getBizCode(),e);
				}
			}
			else {
				//把jobid设置为"N/A"
				tfrJobTodoService.updateJobIdToNA(entity.getId());
			}
	 }
		
		
		
 }
	/**
	 *异常处理
	 *@author 205109-zenghaibin 
	 *@date 2015-07-10 14:50:00 
	 * @param businessId待处理的jobid
	 * @param orderChannel 订单来源
	 ***/
	private void dealException(String businessId,String bizName,String bizCode ,Exception e){
		LOGGER.error(ExceptionUtils.getFullStackTrace(e));
		TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
		jobProcessLogEntity.setBizName(bizName);
		jobProcessLogEntity.setBizCode(bizCode);
		jobProcessLogEntity.setExecBizId(businessId);
		jobProcessLogEntity.setExecTableName("TFR.T_OPT_TRACK_TODOSYN");
		jobProcessLogEntity.setRemark("处理"+bizName+"货物轨迹插入轨迹表");
		jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
		jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
		
		tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		//把jobid设置为"N/A"
		tfrJobTodoService.updateJobIdToNA(businessId);
	}
	
	
	
	@Override
	public void outOfUnloadPool(Object obj) {
		LOGGER.info("【线程池满异常处理开始。。。。。。】");
		//插入日志
		@SuppressWarnings("unchecked")
		List<TfrJobTodoEntity> tfrJobToDoEntityList = (List<TfrJobTodoEntity>) obj;
		if(CollectionUtils.isNotEmpty(tfrJobToDoEntityList) && tfrJobToDoEntityList.size()>0){
//			for (TfrJobTodoEntity tfrJobTodoEntity : tfrJobToDoEntityList) {
				//业务id
				String businessID = tfrJobToDoEntityList.get(0).getBusinessID();
				//业务目标
				String businessGoal = tfrJobToDoEntityList.get(0).getBusinessGoal();
				LOGGER.info("业务id："+businessID+"业务目标:"+businessGoal+"推送失败！线程池已满");
				//异常插入线程日志中
				TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
				jobProcessLogEntity.setBizName("推动轨迹线程:"+Thread.currentThread().getName());
				jobProcessLogEntity.setBizCode("推动轨迹线程类:"+Thread.class.getClass().getName());
				jobProcessLogEntity.setRemark("业务id："+businessID+"业务目标:"+businessGoal+"推送失败！线程池已满");
				jobProcessLogEntity.setExceptionInfo("线程池满");
				jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
				tfrCommonService.addJobProcessLog(jobProcessLogEntity);
//			}
			
		}
		LOGGER.info("【线程池满异常处理结束。。。。。。】");
		
	}

	@Override
	public int getActiveThreads() {
		ConfigurationParamsEntity paramEntity;
		int threadCount =TransferConstants.SONAR_NUMBER_50;
		//获取最大线程数
		try {
			paramEntity = configurationParamsService
					.queryConfigurationParamsByOrgCode(
							DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
							ConfigurationParamsConstants.TFR_PARM_PDA_UNLOAD_INSTOCK_JOB_COUNT,
							FossConstants.ROOT_ORG_CODE);
			if(paramEntity!= null){
				String value = paramEntity.getConfValue();
				//从数据字典获取线程数
				 threadCount = Integer.parseInt(value);
			}
			
		} catch (Exception e) {
			LOGGER.info("从数据字典获取卸车入库线程数异常！"+e.toString());
		}
		return threadCount;
	}

	
	/**
	* @description 重置jobtodo表中的异常数据将jobid设置为"N/A"
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.common.api.server.service.IPushCarGoTrackService#reSetJobMsgbyJobId()
	* @author 218381-foss-lijie
	* @update 2015年5月29日 上午8:40:40
	* @version V1.0
	*/
	@Override
	public void reSetJobMsgbyJobId() {
		pushCarGoTrackDao.reSetJobMsgbyJobId();
		
	}
	
}
