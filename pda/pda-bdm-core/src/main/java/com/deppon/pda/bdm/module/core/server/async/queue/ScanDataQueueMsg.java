package com.deppon.pda.bdm.module.core.server.async.queue;

/**
 * 扫描数据队列消息
 * @author wanghongling
 * @date 2012-10-18
 * @version 1.0
 *
 */
public class ScanDataQueueMsg extends QueueMessage {
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
