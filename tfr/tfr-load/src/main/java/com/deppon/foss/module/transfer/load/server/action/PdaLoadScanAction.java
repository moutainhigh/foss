package com.deppon.foss.module.transfer.load.server.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.deppon.dpap.rocketmq.Action;
import com.deppon.dpap.rocketmq.annotation.Service;
import com.deppon.dpap.rocketmq.core.send.DefaultMQProducerTemplate;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.IPDALoadService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadScanReceipt;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadScanDetailDto;
import com.deppon.foss.util.define.FossConstants;

@Service(serviceCode="PDA2FOSS_TFR_LOAD_SCAN",requestClass = LoadScanDetailDto.class)
public class PdaLoadScanAction implements Action {

	private static final String FOSS_TO_PFD_LOAD_SCAN = "FOSS2PDA_TFR_LOAD_SCAN";

	/**
	 * 日志信息记录
	 */
	static final Logger logger = LoggerFactory.getLogger(PdaLoadScanAction.class);
	
	public IBusinessLockService businessLockService;
	
	public IPDALoadService pdaLoadService;
	

	public DefaultMQProducerTemplate template;
	

	/**
	 * 处理扫描信息Action
	 */
	public void action(Object obj) throws MQClientException {
		
		LoadScanDetailDto scanRecord = (LoadScanDetailDto)obj;

		// 业务锁
		MutexElement mutex = new MutexElement(scanRecord.getWayBillNo(), "LOAD_SCAN", MutexElementType.PDA_WAYBILL_NO);
		logger.error("load scan begin:" + scanRecord.getTaskNo() + " " + scanRecord.getWayBillNo() + " "
				+ scanRecord.getSerialNo());
		businessLockService.lock(mutex, LoadConstants.PDA_SCAN_OUTTIME);
		LoadScanReceipt loadScanReceipt = new LoadScanReceipt();
		try {
			if (FossConstants.YES.equals(scanRecord.getIsPackageScan())) {
				/** 装车包扫描新方法 zwd 200968 */
				pdaLoadService.unlockPackageLoadScanNew(scanRecord);
			} else {
				pdaLoadService.unlockLoadScan(scanRecord);
			}
			
			if (LoadConstants.LOAD_TASK_STATE_CANCELED.equals(scanRecord.getType())) {
				loadScanReceipt.setId(scanRecord.getPdaScanId());
				loadScanReceipt.setTaskCode(scanRecord.getTaskNo());
				loadScanReceipt.setWayBillNo(scanRecord.getWayBillNo());
				loadScanReceipt.setSerialNo(scanRecord.getSerialNo());
				loadScanReceipt.setIsSCan(0);
				loadScanReceipt.setIsFlag(1);
				template.send(FOSS_TO_PFD_LOAD_SCAN,loadScanReceipt);
			}
		} catch (TfrBusinessException e) {
			// 释放锁
			businessLockService.unlock(mutex);
			// 记录日志
			logger.error("load scan exception:" + scanRecord.getTaskNo() + " " + scanRecord.getWayBillNo() + " "
					+ scanRecord.getSerialNo(), e);
			
			if (LoadConstants.LOAD_TASK_STATE_CANCELED.equals(scanRecord.getType())) {
				loadScanReceipt.setId(scanRecord.getPdaScanId());
				loadScanReceipt.setTaskCode(scanRecord.getTaskNo());
				loadScanReceipt.setWayBillNo(scanRecord.getWayBillNo());
				loadScanReceipt.setSerialNo(scanRecord.getSerialNo());
				loadScanReceipt.setIsSCan(0);
				loadScanReceipt.setIsFlag(0);
				template.send(FOSS_TO_PFD_LOAD_SCAN,loadScanReceipt);
			} else {
				loadScanReceipt.setId(scanRecord.getPdaScanId());
				loadScanReceipt.setTaskCode(scanRecord.getTaskNo());
				loadScanReceipt.setWayBillNo(scanRecord.getWayBillNo());
				loadScanReceipt.setSerialNo(scanRecord.getSerialNo());
				loadScanReceipt.setIsSCan(1);
				loadScanReceipt.setIsFlag(0);
				template.send(FOSS_TO_PFD_LOAD_SCAN,loadScanReceipt);
			}
			throw (e);
		}

		// 释放锁
		businessLockService.unlock(mutex);
		// 记录日志
		logger.error("load scan end:" + scanRecord.getTaskNo() + " " + scanRecord.getWayBillNo() + " "
				+ scanRecord.getSerialNo());
	}

	@Autowired
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	@Autowired
	public void setPdaLoadService(IPDALoadService pdaLoadService) {
		this.pdaLoadService = pdaLoadService;
	}

	public void setTemplate(DefaultMQProducerTemplate template) {
		this.template = template;
	}
}
