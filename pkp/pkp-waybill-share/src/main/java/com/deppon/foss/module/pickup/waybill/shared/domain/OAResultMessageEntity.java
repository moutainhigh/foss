package com.deppon.foss.module.pickup.waybill.shared.domain;


public class OAResultMessageEntity {
	
	 //消息代码｛成功为“10000”，失败为“00000”｝
	private String message_code;
	
	//消息代码说明
	private String message_detail;
	
	//返回记录的条数
	private int count;
	
	//处理明细
	private RewardFineDetailEntity detail;

	public String getMessage_code() {
		return message_code;
	}

	public void setMessage_code(String messageCode) {
		this.message_code = messageCode;
	}

	public String getMessage_detail() {
		return message_detail;
	}

	public void setMessage_detail(String messageDetail) {
		this.message_detail = messageDetail;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public RewardFineDetailEntity getDetail() {
		return detail;
	}

	public void setDetail(RewardFineDetailEntity detail) {
		this.detail = detail;
	}

}
