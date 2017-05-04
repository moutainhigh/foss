package com.deppon.pda.bdm.module.core.shared.domain.adapter;

import java.io.Serializable;

public class ResponseDeptEntity implements Serializable{
	
	
	/**
	 * @fields serialVersionUID
	 * @author 314587-ECS-LiuLiPeng
	 * @update 2016年8月10日 下午6:01:40
	 * @version V1.0
	 */
	
	private static final long serialVersionUID = 1L;

	/**
	 * 营业部
	 */
	private boolean salesDepartment;
	
	/**
	 * 外场
	 */
	private boolean transferCenter;

	public boolean isSalesDepartment() {
		return salesDepartment;
	}

	public void setSalesDepartment(boolean salesDepartment) {
		this.salesDepartment = salesDepartment;
	}

	public boolean isTransferCenter() {
		return transferCenter;
	}

	public void setTransferCenter(boolean transferCenter) {
		this.transferCenter = transferCenter;
	}
}
