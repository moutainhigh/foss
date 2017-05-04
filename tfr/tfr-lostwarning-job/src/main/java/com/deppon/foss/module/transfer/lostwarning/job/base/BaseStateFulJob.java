package com.deppon.foss.module.transfer.lostwarning.job.base;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;


public abstract class BaseStateFulJob implements StatefulJob{
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	private ApplicationContext appContext;

	public void execute(JobExecutionContext context) throws JobExecutionException {
	    init(context);
	    doExecute(context);
	 }

	protected void init(JobExecutionContext context) {
	    Object cxt = context.get("SPRING_CONTEXT");
	    if ((null != cxt) && (ApplicationContext.class.isInstance(cxt)))
	      this.appContext = ((ApplicationContext)cxt);
	}

	protected Object getProperty(JobExecutionContext context, String key) {
	    JobDataMap dataMap = context.getMergedJobDataMap();
	    return dataMap.get(key);
	}

	protected <T> T getBean(String name, Class<T> clazz) {
	    return getAppContext().getBean(name, clazz);
	}

    protected ApplicationContext getAppContext() {
	    return this.appContext;
	}

	protected abstract void doExecute(JobExecutionContext paramJobExecutionContext)  throws JobExecutionException;
}
