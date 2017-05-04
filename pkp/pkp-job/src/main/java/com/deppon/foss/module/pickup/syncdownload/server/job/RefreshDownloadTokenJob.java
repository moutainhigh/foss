package com.deppon.foss.module.pickup.syncdownload.server.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.login.server.downloadtoken.DownloadTokenManager;
import com.deppon.foss.module.login.server.downloadtoken.DownloadTokenManagerFactory;

/**
 * 刷新下载令牌状态 job
 * zxy 20140408 MANA-2018
 * 
 * @author 157229-zxy
 * 
 */
public class RefreshDownloadTokenJob extends GridJob implements StatefulJob{
	/**
	 * 日志
	 */
	protected final static Logger LOG = LoggerFactory.getLogger(RefreshDownloadTokenJob.class);
	
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		LOG.info("刷新下载令牌[RefreshDownloadTokenJob start...]");
		DownloadTokenManager downloadTokenManager = DownloadTokenManagerFactory.getInstance().getDownloadTokenManager();
		downloadTokenManager.refresh();
		LOG.info("刷新下载令牌[RefreshDownloadTokenJob end.]");
	}

}
