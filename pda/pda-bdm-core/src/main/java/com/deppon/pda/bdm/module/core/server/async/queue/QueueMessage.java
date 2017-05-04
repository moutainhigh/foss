package com.deppon.pda.bdm.module.core.server.async.queue;

import java.util.Date;

/**
 * 队列消息
 * @author wanghongling
 * @date 2012-10-18
 * @version 1.0
 *
 */
public class QueueMessage {
	private String id;
	private Date readTime;

	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
