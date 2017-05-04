package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 
* @ClassName: BigCustmerLableResEntity 
* @Description: TODO(大客户标签打印返回实体) 
* @author &245960 |yangdeming@deppon.com
* @date 2015-9-10 下午1:38:55 
*
 */
public class BigCustmerLableReqEntity implements Serializable{

	//不明原因导致eclipse无法生成对应的uuid，只能为1L了
	private static final long serialVersionUID = 1L;
	
	//用户工号
	private String userCode;
	
	//客户条形码
	private List<String> customerLableNum;

	/**
	 * @return the userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param userCode the userCode to set
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * @return the customerLableNum
	 */
	public List<String> getCustomerLableNum() {
		return customerLableNum;
	}

	/**
	 * @param customerLableNum the customerLableNum to set
	 */
	public void setCustomerLableNum(List<String> customerLableNum) {
		this.customerLableNum = customerLableNum;
	}
	
}
