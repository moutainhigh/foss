package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.util.Date;

/**
 * 裹裹  PDA推送数据至DOP(同步)   
 * @author 245955
 *
 */
public class CainiaoVerifyCodeInfo  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Long id;
	
	/**
	 * 渠道单号
	 */
	private String logisticNo;
	
	/**
	 * 验证码
	 */
	private String verifyCode;
	
	/**
	 * 状态标识：0为待推送，1为推送成功，2,为推送失败
	 */
	private Integer status;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 预留字段
	 */
	private String demo;
	
	/**
	 * 本地IP
	 */
	private String ipAddress;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogisticNo() {
		return logisticNo;
	}

	public void setLogisticNo(String logisticNo) {
		this.logisticNo = logisticNo;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDemo() {
		return demo;
	}

	public void setDemo(String demo) {
		this.demo = demo;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
}
