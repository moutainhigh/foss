package com.deppon.foss.module.transfer.unload.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.module.transfer.common.server.utils.PropertiesUtil;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.unload.api.server.service.IAsyncPdaUnloadService;
import com.deppon.foss.module.transfer.unload.api.server.service.IPdaUnloadOptimizeService;
import com.deppon.foss.module.transfer.unload.api.shared.domain.VehicleSyncPdaEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PdaUnloadSerialDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadBillDto;
import com.deppon.foss.util.define.FossConstants;

public class AsyncPdaUnloadService implements IAsyncPdaUnloadService, InitializingBean, DisposableBean {

	private static final Log LOGGER = LogFactory.getLog(AsyncPdaUnloadService.class);

	private static final Long CONNECTION_TIMEOUT = 2000L;

	private IPdaUnloadOptimizeService pdaUnloadOptimizeService;

	public void setPdaUnloadOptimizeService(IPdaUnloadOptimizeService pdaUnloadOptimizeService) {
		this.pdaUnloadOptimizeService = pdaUnloadOptimizeService;
	}

	/**
	 * @desc
	 * @date 2016年9月20日 下午9:56:45
	 */
	@Override
	public void pushPdaUnloadSerial2Pda() {
		List<Future<?>> futures = new ArrayList<Future<?>>();

		boolean allDone = true;

		while (true) {
			try {
				TimeUnit.MILLISECONDS.sleep(ConstantsNumberSonar.SONAR_NUMBER_1000);
			} catch (InterruptedException e) {
				LOGGER.error("PdaUnloadOptimizeService.pushPdaUnloadSerial2Pda sleep exception", e);
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

			List<VehicleSyncPdaEntity> infos = pdaUnloadOptimizeService.queryVehicleSyncPda();
			if (CollectionUtils.isEmpty(infos)) {
				return;
			}

			futures.clear();

			for (VehicleSyncPdaEntity info : infos) {
				Future<?> ft = threadPool.submit(new Task(info));
				futures.add(ft);
			}
		}
	}

	/**
	 * @desc 调用pda提供的接口，将一个车辆任务明细id对应的流水推给pda
	 * @param info
	 * @throws Exception
	 * @date 2016年9月20日 下午10:15:13
	 */
	private void pushPdaUnloadSerialDto(Map<String, List<PdaUnloadSerialDto>> param) throws Exception {
		LOGGER.info("AsyncPdaUnloadService.pushPdaUnloadSerialDto begin");
		String jsonStr = JSON.toJSONString(param);
		LOGGER.info("AsyncPdaUnloadService.pushPdaUnloadSerialDto(Map<String, List<PdaUnloadSerialDto>>).jsonStr = "
				+ jsonStr);
		LOGGER.info("AsyncPdaUnloadService.pushPdaUnloadSerialDto JSON.toJSONString end");

		String url = PropertiesUtil.getKeyValue("SYNC_UNLOAD_SERIAL_2_PDA");
		LOGGER.info("AsyncPdaUnloadService.pushPdaUnloadSerialDto(Map<String, List<PdaUnloadSerialDto>>).url = " + url);

		HttpClient httpClient = new HttpClient();
		HttpClientParams params = httpClient.getParams();
		params.setContentCharset("gbk");
		params.setParameter("http.connection-manager.timeout", CONNECTION_TIMEOUT);
		// params.setSoTimeout(SO_TIMEOUT);

		PostMethod postMethod = new PostMethod(url);
		try {
			postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=gbk");

			NameValuePair nameValuePair = new NameValuePair("jsonStr", jsonStr);

			NameValuePair[] pair = { nameValuePair };
			postMethod.setRequestBody(pair);
			httpClient.executeMethod(postMethod);
		} finally {
			LOGGER.info("AsyncPdaUnloadService.pushPdaUnloadSerialDto end");
			postMethod.releaseConnection();
		}
	}

	/**
	 * @desc 根据车辆任务明细id，查询对应的流水
	 * @param truckTaskDetailId
	 * @return
	 * @date 2016年9月21日 下午3:00:56
	 */
	private Map<String, List<PdaUnloadSerialDto>> queryUnloadSerial(String truckTaskDetailId) {
		// 返回结果

		Map<String, List<PdaUnloadSerialDto>> result = new HashMap<String, List<PdaUnloadSerialDto>>();

		// 根据车辆任务明细id查询有效的车辆任务单据
		List<UnloadBillDto> unloadBillList = pdaUnloadOptimizeService
				.queryUnloadBillByTruckTaskDetailId(truckTaskDetailId);

		for (UnloadBillDto bill : unloadBillList) {

			String key = bill.getBillNo();
			List<PdaUnloadSerialDto> value = pdaUnloadOptimizeService.queryPdaUnloadSerialDto(bill);

			result.put(key, value);
		}

		return result;
	}

	private static final int N_THREADS = 10;

	private ExecutorService threadPool = null;

	@Override
	public void afterPropertiesSet() throws Exception {
		threadPool = Executors.newFixedThreadPool(N_THREADS);
	}

	@Override
	public void destroy() throws Exception {
		threadPool.shutdown();
	}

	private class Task implements Runnable {

		private VehicleSyncPdaEntity info;

		public Task(VehicleSyncPdaEntity info) {
			this.info = info;
		}

		@Override
		public void run() {
			String failed = FossConstants.NO;
			String failedInfo = null;
			Date modifyTime = new Date();

			try {
				Map<String, List<PdaUnloadSerialDto>> map = queryUnloadSerial(info.getTruckTaskDetailId());
				LOGGER.info("AsyncPdaUnloadService.pushPdaUnloadSerialDto begin");
				pushPdaUnloadSerialDto(map);
				LOGGER.info("AsyncPdaUnloadService.pushPdaUnloadSerialDto end");
			} catch (Exception e) {
				failed = FossConstants.YES;
				LOGGER.error("AsyncPdaUnloadService.Task run error", e);
				failedInfo = StringUtils.left(ExceptionUtils.getFullStackTrace(e), ConstantsNumberSonar.SONAR_NUMBER_1000);
			}

			if (FossConstants.YES.equals(failed)) {
				info.setFailed(failed);
				info.setFailedInfo(failedInfo);
				info.setModifyTime(modifyTime);
				pdaUnloadOptimizeService.updateVehicleSyncPda(info);
			} else {
				pdaUnloadOptimizeService.deleteVehicleSyncPda(info);
			}
		}
	}

}
