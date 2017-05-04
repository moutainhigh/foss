package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;

public class PushMessageEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String pushType;
	
	private Object pushMessage;

	public String getPushType() {
		return pushType;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
	}

	public Object getPushMessage() {
		return pushMessage;
	}

	public void setPushMessage(Object pushMessage) {
		this.pushMessage = pushMessage;
	}
}
