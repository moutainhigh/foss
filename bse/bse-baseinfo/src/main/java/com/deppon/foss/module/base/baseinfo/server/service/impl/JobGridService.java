/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.LoggerFactory;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.jobgrid.JobGridNode;
import com.deppon.foss.framework.server.components.jobgrid.dao.jdbc.JDBCContants;
import com.deppon.foss.framework.server.components.jobgrid.domain.JobSchedule;
import com.deppon.foss.framework.server.components.jobgrid.impl.GridJobUtils;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IJobGridDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IJobGridService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobGridClusterEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobGridLoggingsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.JobGridSchedulesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.JobGridException;
import com.deppon.foss.util.UUIDUtils;

/**
 * 任务管理Service接口实现
 * @author DPAP
 * @date 2013-03-23
 */
public class JobGridService implements IJobGridService  {
	/**
	 * 日志打印对象声明
	 */
	private static final Logger log = Logger
			.getLogger(JobGridService.class);
	
	/**
	 * 打印异常信息日志
	 */
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(JobGridService.class);
	
    private IJobGridDao jobGridDao;
	private JobGridNode jobGridNode;

	public void setJobGridNode(JobGridNode jobGridNode) {
		this.jobGridNode = jobGridNode;
	}

	public void setJobGridDao(IJobGridDao jobGridDao) {
		this.jobGridDao = jobGridDao;
	}
	
	public boolean hasCoreInfoOfJob(JobGridSchedulesEntity entity) {
		if(StringUtils.isBlank(entity.getBlongModule())||StringUtils.isBlank(entity.getJobGroup()) || StringUtils.isBlank(entity.getJobName()) || StringUtils.isBlank(entity.getTriggerName())
				|| StringUtils.isBlank(entity.getTriggerGroup()) || StringUtils.isBlank(entity.getTriggerExpression()) ||
			  StringUtils.isBlank(entity.getJobClass())) {
			return false;
		} else {
			return true;
		}
	}
	
    /**
     * 添加作业对象 信息
     * @author 曾宪涛
     * @date 2013-03-23
     * @param entity
     * @return
     */
	@Override
	@Transactional
	public int addJobGridSchedule(JobGridSchedulesEntity entity) throws JobGridException {
		if(entity == null) {
			throw new JobGridException(JobGridException.JOB_ENTITY_IS_NULL);
		}
		if(!hasCoreInfoOfJob(entity)) {
			throw new JobGridException(JobGridException.LANK_OF_JOB_CORE_INFO);
		}
		entity.setId(UUIDUtils.getUUID());
		entity.setUpdateTime(new Date());
		entity.setActive(1);
		int flag = jobGridDao.insertJobGridSchedule(entity);
		if(flag==0){
			throw new JobGridException("数据更新失败");
		}
		 if(entity.getActive()==1){
        	 this.startJob(entity.getId(), entity.getBlongModule());
         }
        //停止任务
        //if(entity.getActive() ==0){
        	//this.pauseJob(entity.getId(),entity.getBlongModule());
       // }
		return flag;
	}

    /**
     * 修改一笔作业对象 信息
     * @author 曾宪涛
     * @date 2013-03-23
     * @param entity
     * @return
     */
	@Override
	@Transactional
	public int modifyJobGridSchedule(JobGridSchedulesEntity entity) throws JobGridException {
		if(entity == null) {
			throw new JobGridException(JobGridException.JOB_ENTITY_IS_NULL);
		}
		if(!hasCoreInfoOfJob(entity)) {
			throw new JobGridException(JobGridException.LANK_OF_JOB_CORE_INFO);
		}
		JobGridSchedulesEntity dbEntity = jobGridDao.getJobGridScheduleById(entity.getId(),entity.getBlongModule());
		if(dbEntity!= null && (!dbEntity.getTriggerExpression().equals(entity.getTriggerExpression()) || 
				!dbEntity.getJobClass().equals(entity.getJobClass()))) {
			entity.setUpdateTime(new Date());
		}
		int flag = jobGridDao.updateJobGridSchedule(entity);
		if(flag==0){
			throw new JobGridException("数据更新失败");
		}
		//313353 sonar
		String jobName = StringUtils.EMPTY;
		String jobGroup = StringUtils.EMPTY;
		if(null != dbEntity){
			jobName = dbEntity.getJobName();
			jobGroup = dbEntity.getJobGroup();
		}
		//重启定时任务，使修改的信息生效
		try {
			if(jobGridNode==null){
				throw new JobGridException("spring初始化jobGridNode对象失败");
			}
			jobGridNode.getScheduler().deleteJob(jobName, jobGroup);
			if(entity.getActive()==1){
				this.startJob(entity.getId(), entity.getBlongModule());
			}
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			throw new JobGridException("定时任务重启失败");
		}
		return flag;
	}

	@Override
	public int modifyActiveOfJobGridSchedule(String id,String blongModule, Integer active) {
		if(id == null || id.equals("") || active == null) {
			throw new JobGridException(JobGridException.JOB_ENTITY_ID_IS_NULL);
		}
		if(StringUtils.isEmpty(blongModule)) {
			throw new JobGridException("任务所属模块为空");
		}
		//更新数据库记录
		int i =jobGridDao.updateActiveOfJobGridSchedule(id,blongModule, active);
		if(i==0){
			throw new JobGridException("数据更新失败");
		}
		//启动任务
        if(active==1){
        	 this.startJob(id, blongModule);
         }
        //停止任务
        if(active ==0){
        	this.pauseJob(id, blongModule);
        }
		return  1;
	}
	private void startJob(String id,String blongModule){
		  if(jobGridNode==null){
			  throw new JobGridException("jobGridNode对象未初始化");
		  }
		  Scheduler  scheduler = jobGridNode.getScheduler();
		  if(scheduler==null){
			  throw new JobGridException("scheduler对象未初始化");
		  }
		  JobGridSchedulesEntity  queryEntity = new JobGridSchedulesEntity();
		  queryEntity.setActive(1);
		  queryEntity.setBlongModule(blongModule);
		  queryEntity.setId(id);
		   try {
	   		  List<JobGridSchedulesEntity> jobGridSchedulesEntitylist= this.getJobGridSchedulesRecords(queryEntity,0,1);
			 if(jobGridSchedulesEntitylist!=null&&jobGridSchedulesEntitylist.size()==1){
				 JobGridSchedulesEntity entity =jobGridSchedulesEntitylist.get(0);
				 JobSchedule jobSchedule = new JobSchedule();
				 PropertyUtils.copyProperties(jobSchedule, entity);
				 log.info("启动任务，任务名："+jobSchedule.getJobName()+"任务分组:"+jobSchedule.getJobGroup());
				 //添加任务
				 addJob(scheduler,jobSchedule);
			 }else{
				 throw new JobGridException("启动失败，数据库中未找到对应的记录");
			 }
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			throw new JobGridException(e.getMessage());
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			throw new JobGridException(e.getMessage());
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			throw new JobGridException(e.getMessage());
		}
	  }	
	/**
	  * 添加作业   
	  * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	  * @author 268984 
	  * @date 2016-4-7 下午4:55:19
	  * @param scheduler
	  * @param js
	  * @see
	  */
	private  void addJob(Scheduler  scheduler,JobSchedule js){
	        // 更新scheduler中的任务数据
	        JobDetail jd = fromJobSchedule(js);
	        if (null == jd) {
				 throw new JobGridException("启动失败,jobClass未找到，创建jobDetail失败");
	        }
	        try {
	        	 //scheduler.deleteJob(js.getJobName(), js.getJobGroup());
	            Trigger oldTrigger = scheduler.getTrigger(js.getTriggerName(), js.getTriggerGroup());                
	               //若任务处于启动状态，则作废之前的执行计划
	            if (null != oldTrigger) {
	            	scheduler.addJob(jd, true);
	                scheduler.unscheduleJob(oldTrigger.getName(), oldTrigger.getGroup());
	            }
	           if (js.getActive() == 0) {
	            	throw new JobGridException("启动失败，数据库记录未更新");
				}
	            // 如果任务没有运行加入到计划中去
	            Trigger newTrigger = createTrigger(js);
	            if (null != newTrigger) {
	                scheduler.scheduleJob(jd, newTrigger);
	            }
	        } catch (SchedulerException e) {
	        	throw new JobGridException(e.getMessage());
	        }
	    
		}
	 /**
	 * 
	 * <p>构建作业详细信息</p> 
	 */
	private JobDetail fromJobSchedule(JobSchedule js) {
	        try {
	            JobDetail jd = new JobDetail();
	            jd.setJobClass(Class.forName(js.getJobClass()));
	            jd.setDescription(js.getDescription());
	            jd.setGroup(js.getJobGroup());
	            jd.setName(js.getJobName());
	            jd.setJobDataMap(GridJobUtils.fromString(js.getJobData()));
	            return jd;
	        } catch (ClassNotFoundException e) {
	        	throw new JobGridException("jobClass未找到，创建jobDetail失败");
	        }
	    }
	 /**
	 * 
	 * <p>创建触发器</p> 
	 *
	 */
	    private Trigger createTrigger(JobSchedule js) {
	        try {
	            Trigger trigger = null;
	            if (JDBCContants.TRIGGER_TYPE_SIMPLE == js.getTriggerType()) {
	                String expr = js.getTriggerExpression();
	                int splitIndex = expr.indexOf(":");
	                int repeatCount = Integer.valueOf(expr.substring(0, splitIndex));
	                long repeatInterval = Long.valueOf(expr.substring(splitIndex + 1));
	                trigger = new SimpleTrigger(js.getTriggerName(), js.getTriggerGroup(), repeatCount, repeatInterval);
	            } else if (JDBCContants.TRIGGER_TYPE_CRON == js.getTriggerType()) {
	                trigger = new CronTrigger(js.getTriggerName(), js.getTriggerGroup(), js.getTriggerExpression());
	                //对没有处理的job不做任何处理
	                trigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
	            }
	            return trigger;
	        } catch (NumberFormatException e) {
	        	LOGGER.error("表达式错误:"+e.getMessage(), e);
	        } catch (ParseException e) {
	        	throw new JobGridException("定时任务表达式错误");
	        }
	        return null;
	    }
    /**
     * 根据ID获取作业对象信息
     * @author 曾宪涛
     * @date 2013-03-23
     * @param id
     * @return
     */
	@Override
	public JobGridSchedulesEntity getJobGridScheduleById(String id,String blongModule) throws JobGridException {
		if(id == null || id.equals("")) {
			throw new JobGridException(JobGridException.JOB_ENTITY_ID_IS_NULL);
		}
		if(blongModule == null || blongModule.equals("")) {
			throw new JobGridException("任务所属模块为空");
		}
		return jobGridDao.getJobGridScheduleById(id,blongModule);
	}

    /**
     * 查询作业对象信息的总数量
     * @author 曾宪涛
     * @date 2013-03-23
     * @param entity
     * @return
     */
	@Override
	public long getJobGridSchedulesCount(JobGridSchedulesEntity entity) throws JobGridException {
		if(entity == null) {
			throw new JobGridException(JobGridException.JOB_ENTITY_IS_NULL);
		}
		String blongModule = entity.getBlongModule();
		if(StringUtils.isEmpty(blongModule)||blongModule.equals("ALL")){
		JobGridSchedulesEntity en  = new JobGridSchedulesEntity();
		en.setBlongModule("BSE");
   		 long  bse =  jobGridDao.getJobGridSchedulesCount(en);
   		en.setBlongModule("TFR");
   		 long tfr = jobGridDao.getJobGridSchedulesCount(entity);
   		en.setBlongModule("STL");
   		 long stl = jobGridDao.getJobGridSchedulesCount(entity);
   		 en.setBlongModule("PKP");
   		 long pkp = jobGridDao.getJobGridSchedulesCount(entity);
   		 return bse+tfr+stl+pkp;
   	 }
		return jobGridDao.getJobGridSchedulesCount(entity);
	}
	
    /**
     * 查询作业对象信息（分页）
     * @author 曾宪涛
     * @date 2013-03-23
     * @param entity
     * @return
     */
	@Override
	public List<JobGridSchedulesEntity> getJobGridSchedulesRecords(JobGridSchedulesEntity entity, 
			int offset, int limit) throws JobGridException {
		if(entity == null) {
			throw new JobGridException(JobGridException.JOB_ENTITY_IS_NULL);
		}
		if(StringUtils.isEmpty( entity.getBlongModule())){
			return null;
		}
		List<JobGridSchedulesEntity> jobGridEntityList = new ArrayList<JobGridSchedulesEntity>();
		//entity.setActive(1);
		//如果设置所属模块为all，则查询所有表空间下的数据
		//涉及到多表的查询
	  if( entity.getBlongModule().equals("ALL")){
		  List<JobGridSchedulesEntity> allData = new ArrayList<JobGridSchedulesEntity>();
			entity.setBlongModule("BSE");
			List<JobGridSchedulesEntity> bseJobGridSchedules =	jobGridDao.getJobGridSchedulesRecords(entity);
			attachJobModuleInfo(bseJobGridSchedules,"BSE");
			entity.setBlongModule("TFR");
			List<JobGridSchedulesEntity> tfrJobGridSchedules =	jobGridDao.getJobGridSchedulesRecords(entity);
			attachJobModuleInfo(tfrJobGridSchedules,"TFR");
			entity.setBlongModule("PKP");
			List<JobGridSchedulesEntity> pkpJobGridSchedules =	jobGridDao.getJobGridSchedulesRecords(entity);
			attachJobModuleInfo(pkpJobGridSchedules,"PKP");
			entity.setBlongModule("STL");
			List<JobGridSchedulesEntity> stlJobGridSchedules =	jobGridDao.getJobGridSchedulesRecords(entity);
			attachJobModuleInfo(stlJobGridSchedules,"STL");
			allData.addAll(stlJobGridSchedules);
			allData.addAll(pkpJobGridSchedules);
			allData.addAll(tfrJobGridSchedules);
			allData.addAll(bseJobGridSchedules);
			entity.setBlongModule("ALL");
			int end  = offset+limit;
			int max = allData.size();
			jobGridEntityList = allData.subList(offset,max>end?end:max);
		}else{
			jobGridEntityList= jobGridDao.getJobGridSchedulesRecords(entity, offset, limit);
			for(JobGridSchedulesEntity en:jobGridEntityList){
				en.setBlongModule(entity.getBlongModule());
			}
		}
		return jobGridEntityList;
	}
	private void attachJobModuleInfo(List<JobGridSchedulesEntity>jobGridEntityList,String moduleName ){
		 for(JobGridSchedulesEntity en:jobGridEntityList){
			 en.setBlongModule(moduleName);
		 }
	}
    /**
     * 查询任务的历史记录信息的总数量
     * @author 曾宪涛
     * @date 2013-05-14
     * @param entity
     * @return
     */
	@Override
	public long getJobGridLoggingsCount(JobGridLoggingsEntity entity) {		
		if(entity == null) {
			throw new JobGridException(JobGridException.JOB_ENTITY_IS_NULL);
		}
		if(StringUtils.isBlank(entity.getBlongModule())||StringUtils.isBlank(entity.getJobName()) || StringUtils.isBlank(entity.getJobGroup())) {
			throw new JobGridException(JobGridException.LANK_OF_JOB_CORE_INFO);
		}
		return jobGridDao.getJobGridLoggingsCount(entity);
	}

    /**
     * 查询任务的历史记录信息
     * @author 曾宪涛
     * @date 2013-03-23
     * @param entity
     * @return
     */
	@Override
	public List<JobGridLoggingsEntity> getJobGridLoggingsRecords(JobGridLoggingsEntity entity, 
			int offset, int limit) throws JobGridException {
		if(entity == null) {
			throw new JobGridException(JobGridException.JOB_ENTITY_IS_NULL);
		}
		if(StringUtils.isBlank(entity.getBlongModule())||StringUtils.isBlank(entity.getJobName()) || StringUtils.isBlank(entity.getJobGroup())) {
			throw new JobGridException(JobGridException.LANK_OF_JOB_CORE_INFO);
		}
		DateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(StringUtils.isBlank(entity.getStartTime())) {
			entity.setStartTime(dft.format(new Date()));
		}
		if(StringUtils.isBlank(entity.getStartTime())) {
			entity.setStartTime(dft.format(new Date()));
		}
		return jobGridDao.getJobGridLoggingsRecords(entity, offset, limit);
	}
	/**
	 * 查询任务分组
	 * @param paginationParam
	 * @return
	 */
    @SuppressWarnings("unchecked")
	public List<JobGridClusterEntity> queryJobGridCluSelector(String queryStr,JobGridClusterEntity entity,int start,int limit)
    {
        if(queryStr == null){
            throw new JobGridException("查询条件为空");
        }
        List<JobGridClusterEntity> jobGridClusters = new ArrayList<JobGridClusterEntity>();
        //如没有直接job所属模块则查询所有命名空间下的数据
        
        if(entity==null||entity.getBlongModule()==null||entity.getBlongModule().equals("ALL")){
        
        	entity = new JobGridClusterEntity();
        entity.setBlongModule("BSE");
        List<JobGridClusterEntity> bseClusters = jobGridDao.queryJobGridCluSelector(queryStr, entity, start, limit);
        addBlongModuleOnJobGridClusters(bseClusters ,"BSE");
        entity.setBlongModule("TFR");
        List<JobGridClusterEntity> tfrCluster = jobGridDao.queryJobGridCluSelector(queryStr, entity, start, limit);
        addBlongModuleOnJobGridClusters(tfrCluster ,"TFR");
        entity.setBlongModule("STL");
        List<JobGridClusterEntity> stlClusters = jobGridDao.queryJobGridCluSelector(queryStr, entity, start, limit);
        addBlongModuleOnJobGridClusters(stlClusters ,"STL");
        entity.setBlongModule("PKP");
        List<JobGridClusterEntity> pkpClusters = jobGridDao.queryJobGridCluSelector(queryStr, entity, start, limit);
        addBlongModuleOnJobGridClusters(pkpClusters ,"PKP");
        jobGridClusters.addAll(bseClusters);
        jobGridClusters.addAll(tfrCluster);
        jobGridClusters.addAll(pkpClusters);
        jobGridClusters.addAll(stlClusters);
        }else{
        	jobGridClusters =jobGridDao.queryJobGridCluSelector(queryStr, entity, start, limit);
        	for(JobGridClusterEntity en:jobGridClusters){
            	en.setBlongModule(entity.getBlongModule());
            }
        }
        
        for(int i = 0; i < jobGridClusters.size(); i++)
        {
            JobGridClusterEntity jobGridCluster = (JobGridClusterEntity)jobGridClusters.get(i);
            if(jobGridCluster.getScopeType() == 1)
                jobGridCluster.setScopeTypeName("任务组");
            else
                jobGridCluster.setScopeTypeName("任务名称");
        }
        return jobGridClusters;
    }
    private void addBlongModuleOnJobGridClusters(List<JobGridClusterEntity> jobGridClusters ,String blongModule){
            for(JobGridClusterEntity en:jobGridClusters){
            	en.setBlongModule(blongModule);
            }
    }
    /**
     * 统计任务分组总数
     * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
     * @author 268984 
     * @date 2016-3-19 下午3:54:42
     * @param queryStr
     * @return
     * @see
     */
    @Override
    public long queryJobGridCluSelectorCount(String queryStr,String blongModule){
    	 //未设置所属模块信息，则查询所有表空间下的任务分组
    	if(StringUtils.isEmpty(blongModule)){
    		 long  bse =  jobGridDao.queryJobGridCluSelectorCount(queryStr,"BSE");
    		 long tfr = jobGridDao.queryJobGridCluSelectorCount(queryStr,"TFR");
    		 long pkp = jobGridDao.queryJobGridCluSelectorCount(queryStr,"PKP");
    		 long stl = jobGridDao.queryJobGridCluSelectorCount(queryStr,"STL");
    		 return bse+tfr+pkp+stl;
    	 }
    	 return jobGridDao.queryJobGridCluSelectorCount(queryStr,blongModule);
    }
    /**
     * 新增任务分组
     * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
     * @author 268984 
     * @date 2016-3-19 下午4:00:38
     * @param entity
     * @return
     * @see
     */
    @Override
    public int addJobGridCluSchedule(JobGridClusterEntity entity)
    {
        if(entity == null)
            throw new JobGridException("查询条件为空");
        String instanceId = entity.getInstanceId();
        String scopeName = entity.getScopeName();
        if(entity.getBlongModule() ==null){
            throw new JobGridException("分组所属模块不能为空");
        }
        if(instanceId == null)
            throw new JobGridException("分组名不能为空");
        if(scopeName == null)
            throw new JobGridException("分组名不能为空");
        JobGridClusterEntity validateJob = new JobGridClusterEntity();
        validateJob.setScopeName(entity.getScopeName());
        validateJob.setBlongModule(entity.getBlongModule());
        List jobGridClusterList = queryJobGridCluInfos(validateJob, 0, NumberConstants.NUMBER_2147483647);
        if(jobGridClusterList != null && jobGridClusterList.size() > 0)
            throw new JobGridException("任务组或任务名称已经存在");
        int n = jobGridDao.addJobGridCluSchedule(entity);
        if(n == 0)
            throw new JobGridException("保存失败");
        else
            return 1;
    }
     /**
      * 查询任务分组（精确） 
      * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
      * @author 268984 
      * @date 2016-3-19 下午4:06:17
      * @param entity
      * @param start
      * @param limit
      * @return
      * @see
      */
    @Override
    public List queryJobGridCluInfos(JobGridClusterEntity entity, int start, int limit){
        if(entity == null){
            throw new JobGridException("参数为空");
            }
        if(StringUtils.isEmpty(entity.getBlongModule())){
        	 throw new JobGridException("分组所属模块为空");
        }
        List<JobGridClusterEntity> ls =  jobGridDao.queryJobGridCluInfos(entity, start, limit);
        for(JobGridClusterEntity en:ls){
        	en.setBlongModule(entity.getBlongModule());
        }
        return ls;
              
    }
   




  public void pauseJob(String id,String blongModule){
	  if(jobGridNode==null){
		  throw new JobGridException("jobGridNode对象未初始化");
	  }
	  Scheduler  scheduler = jobGridNode.getScheduler();
	  if(scheduler==null){
		  throw new JobGridException("scheduler对象未初始化");
	  }
	  JobGridSchedulesEntity  queryEntity = new JobGridSchedulesEntity();
	  queryEntity.setActive(0);
	  queryEntity.setId(id);
	  queryEntity.setBlongModule(blongModule);
	 List<JobGridSchedulesEntity> jobGridSchedulesEntitylist= this.getJobGridSchedulesRecords(queryEntity,0,1);
	 if(jobGridSchedulesEntitylist!=null&&jobGridSchedulesEntitylist.size()==1){
		 JobGridSchedulesEntity entity =jobGridSchedulesEntitylist.get(0);
		 log.info("暂停任务，任务名："+entity.getJobName()+"任务分组:"+entity.getJobGroup());
		 //任务
			try {
				JobDetail  detail = scheduler.getJobDetail(entity.getJobName(), entity.getJobGroup());
				//Trigger trig =	jobGridNode.getTrigger("qrtz-jobEarlyWarn", "bse-group");
			   if(detail==null){
				   throw new JobGridException("任务已处于停止状态");
				 }
			   jobGridNode.pauseJob(entity.getJobName(), entity.getJobGroup());
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				log.error("schedule job error,while process pauseJob<"+entity.getJobName()+","+entity.getJobGroup()+">", e);
				throw new JobGridException("停止任务失败");
			}
	 }else{
		 throw new JobGridException("暂停失败，数据库中未找到对应的记录");
	 }
	}
}
