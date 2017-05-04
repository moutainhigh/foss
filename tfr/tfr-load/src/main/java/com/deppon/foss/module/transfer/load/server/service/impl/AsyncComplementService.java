package com.deppon.foss.module.transfer.load.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.message.IMessageBundle;
import com.deppon.foss.module.transfer.load.api.server.service.IAsyncComplementService;
import com.deppon.foss.module.transfer.load.api.server.service.IComplementService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.AsyncComplementEntity;
import com.deppon.foss.util.define.FossConstants;

public class AsyncComplementService implements IAsyncComplementService, InitializingBean, DisposableBean {
	private static final Log LOGGER = LogFactory.getLog(AsyncComplementService.class);

	private static final int N_THREADS = 10;

	private IMessageBundle messageBundle;

	private IComplementService complementService;

	public void setMessageBundle(IMessageBundle messageBundle) {
		this.messageBundle = messageBundle;
	}

	public void setComplementService(IComplementService complementService) {
		this.complementService = complementService;
	}

	/**
	 * @desc job调用方法
	 * @date 2015年12月1日 上午11:14:23
	 */
	@Override
	public void complementAsync4Job() {
		List<Future<?>> futures = new ArrayList<Future<?>>();

		boolean allDone = true;

		while (true) {
			try {
				TimeUnit.MILLISECONDS.sleep(LoadConstants.SONAR_NUMBER_300);
			} catch (InterruptedException e) {
				LOGGER.error("AsyncComplementService.complementAsync4Job sleep异常", e);
			}

			for (Future<?> index : futures) {
				if (!index.isDone()) {
					allDone = false;
					break;
				}
			}

			if (!allDone) {
				allDone = true;
				continue;
			}

			// 查询待处理的运单，row_number() over(partition by waybill_no order by create_time desc),取row_number为1的，
			//一次取2k，sql中有控制
			List<AsyncComplementEntity> infos = complementService.findAsyncComplement();
			if (CollectionUtils.isEmpty(infos)) {
				return;
			}

			futures.clear();

			for (AsyncComplementEntity info : infos) {
				Future<?> ft = threadPool.submit(new Task(info));
				futures.add(ft);
			}
		}
	}

	private ExecutorService threadPool = null;

	@Override
	public void destroy() throws Exception {
		threadPool.shutdown();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		threadPool = Executors.newFixedThreadPool(N_THREADS);
	}

	class Task implements Runnable {

		private AsyncComplementEntity info;

		public Task(AsyncComplementEntity info) {
			this.info = info;
		}

		@Override
		public void run() {
			try {
				String failed = FossConstants.NO;
				String failInfo = null;
				try {
					complementService.complementAsync(info);
				} catch (BusinessException be) {
					failed = FossConstants.YES;
					failInfo = messageBundle.getMessage(be.getErrorCode(), be.getErrorArguments());
				} catch (Exception e) {
					failed = FossConstants.YES;
					failInfo = StringUtils.left(ExceptionUtils.getFullStackTrace(e), LoadConstants.SONAR_NUMBER_1000);
				}
				
				if(FossConstants.NO.equals(failed)){
					int rownum = complementService.deleteAsyncComplement(info);
					if(rownum > 0){
						complementService.createComplementLog(info);
					}
				}else{
					info.setFailed(FossConstants.YES);
					info.setFailInfo(failInfo);
					info.setModifyTime(new Date());
					complementService.updateAsyncFailed(info);
					
					complementService.deleteAsyncComplement(info);
				}
			} catch (Exception e) {
				LOGGER.error("AsyncComplementService.Task.run error", e);
			}
		}
	}

}
