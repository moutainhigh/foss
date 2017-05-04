package com.deppon.pda.bdm.module.core.shared.domain.adapter;

import java.io.Serializable;
import java.util.List;

public class AdapterDeptVo implements Serializable {

	/**
	 * @fields serialVersionUID
	 * @author 314587-ECS-LiuLiPeng
	 * @update 2016年8月10日 下午12:15:16
	 * @version V1.0
	 */

	private static final long serialVersionUID = 1L;

	/**
	 * 部门编码
	 */
	private String orgCode;
	/**
	 * 查询类型 ：查询车队类型？ 请看BizTypeConstants类，目前包括：
	 * ORG_TRANS_DEPARTMENT="TRANS_DEPARTMENT";
	 * ORG_TRANSFER_CENTER="TRANSFER_CENTER"; ORG_AIR_DISPATCH="AIR_DISPATCH";
	 * ORG_DIVISION="DIVISION"; ORG_BIG_REGION="BIG_REGION";
	 * ORG_SMALL_REGION="SMALL_REGION";
	 * ORG_IS_DELIVER_SCHEDULE="IS_DELIVER_SCHEDULE";
	 */
	private List<String> bizTypes;

	/**
	 * 部门编码
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * 部门编码
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * 查询类型 ：查询车队类型？ 请看BizTypeConstants类，目前包括：
	 * ORG_TRANS_DEPARTMENT="TRANS_DEPARTMENT";
	 * ORG_TRANSFER_CENTER="TRANSFER_CENTER"; ORG_AIR_DISPATCH="AIR_DISPATCH";
	 * ORG_DIVISION="DIVISION"; ORG_BIG_REGION="BIG_REGION";
	 * ORG_SMALL_REGION="SMALL_REGION";
	 * ORG_IS_DELIVER_SCHEDULE="IS_DELIVER_SCHEDULE";
	 */
	public List<String> getBizTypes() {
		return bizTypes;
	}
	/**
	 * 查询类型 ：查询车队类型？ 请看BizTypeConstants类，目前包括：
	 * ORG_TRANS_DEPARTMENT="TRANS_DEPARTMENT";
	 * ORG_TRANSFER_CENTER="TRANSFER_CENTER"; ORG_AIR_DISPATCH="AIR_DISPATCH";
	 * ORG_DIVISION="DIVISION"; ORG_BIG_REGION="BIG_REGION";
	 * ORG_SMALL_REGION="SMALL_REGION";
	 * ORG_IS_DELIVER_SCHEDULE="IS_DELIVER_SCHEDULE";
	 */
	public void setBizTypes(List<String> bizTypes) {
		this.bizTypes = bizTypes;
	}

}
