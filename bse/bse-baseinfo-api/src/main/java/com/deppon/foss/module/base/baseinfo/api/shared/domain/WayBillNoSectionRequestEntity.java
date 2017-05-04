package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.io.Serializable;
/**
 * 运单号请求接口实体类
 * @author 262036 HuangWei
 *
 * @date 2015-7-21 上午9:14:08
 */
public class WayBillNoSectionRequestEntity implements Serializable{

	private static final long serialVersionUID = 10593343954L;

	private Integer id;
	/**
	 * 系统名称
	 */
	private String systemName;
	
	/**
	 * 渠道来源
	 */
	private String channelSourceCode;
	
	/**
	 * 客户编码
	 */
	private String customerCode;
	
	/**
	 * 申请数量
	 */
	private Long applyCount;

	public String getSystemName() {
		return (systemName == null ? "" : systemName);
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getChannelSourceCode() {
		return (channelSourceCode == null ? "" : channelSourceCode);
	}

	public void setChannelSourceCode(String channelSourceCode) {
		this.channelSourceCode = channelSourceCode;
	}

	public String getCustomerCode() {
		return (customerCode == null ? "" : customerCode);
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Long getApplyCount() {
		return applyCount;
	}

	public void setApplyCount(Long applyCount) {
		this.applyCount = applyCount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
