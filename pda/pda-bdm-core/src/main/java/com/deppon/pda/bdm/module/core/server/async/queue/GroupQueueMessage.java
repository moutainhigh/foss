package com.deppon.pda.bdm.module.core.server.async.queue;

public class GroupQueueMessage extends QueueMessage {
	private String groupId;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
