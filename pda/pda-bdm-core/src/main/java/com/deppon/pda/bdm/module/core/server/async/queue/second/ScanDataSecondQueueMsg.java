package com.deppon.pda.bdm.module.core.server.async.queue.second;

import com.deppon.pda.bdm.module.core.server.async.queue.QueueMessage;

/**
 * 扫描数据队列消息
 * @author 245955
 * @date 2015-10-18
 * @version 1.0
 *
 */
public class ScanDataSecondQueueMsg extends QueueMessage {
	/**
	 * 扫描数据ID
	 */
//	private String scanMsgId;
	/**
	 * 扫描类型
	 */
	private String scanType;
	
//	public String getScanMsgId() {
//		return scanMsgId;
//	}
//	public void setScanMsgId(String scanMsgId) {
//		this.scanMsgId = scanMsgId;
//	}
	public String getScanType() {
		return scanType;
	}
	public void setScanType(String scanType) {
		this.scanType = scanType;
	}
}
