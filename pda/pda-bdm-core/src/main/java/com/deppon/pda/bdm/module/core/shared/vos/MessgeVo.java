package com.deppon.pda.bdm.module.core.shared.vos;
/**
 * 
  * @ClassName MessgeVo 
  * @Description TODO 消息类型
  * @author mt hyssmt@vip.qq.com
  * @date 2013-10-2 上午11:02:12
 */
public class MessgeVo {
	/**
	 * 业务类型
	 */
	private String operType;
	/**
	 * 异常消息总数
	 */
	private int errTotal = 0;
	/**
	 * 正常消息总数
	 */
	private int normalTotal = 0;
	/**
	 * 消息类型，异常/正常
	 */
	private String msgType;
	
	/**
	 * 消息时间HHmm
	 */
	private String msgTime;
	
	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public int getErrTotal() {
		return errTotal;
	}

	public void setErrTotal(int errTotal) {
		this.errTotal = errTotal;
	}

	public int getNormalTotal() {
		return normalTotal;
	}

	public void setNormalTotal(int normalTotal) {
		this.normalTotal = normalTotal;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMsgTime() {
		return msgTime;
	}

	public void setMsgTime(String msgTime) {
		this.msgTime = msgTime;
	}
}
