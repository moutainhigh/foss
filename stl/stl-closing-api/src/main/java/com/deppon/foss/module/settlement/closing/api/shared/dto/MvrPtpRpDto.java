/**
 * 
 */
package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPtpRpEntity;

/**
 * @author 231438
 *
 */
public class MvrPtpRpDto extends MvrPtpRpEntity implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -8640206631949001984L;
	/** 业务类型集合. */
	private List<String> productCodeList;
	/** 用户编码-设置用户数据查询权限. */
	private String userCode;
	/** 总条数. */
	private Long count;
	
	
	public List<String> getProductCodeList() {
		return productCodeList;
	}
	public void setProductCodeList(List<String> productCodeList) {
		this.productCodeList = productCodeList;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}

	
}
