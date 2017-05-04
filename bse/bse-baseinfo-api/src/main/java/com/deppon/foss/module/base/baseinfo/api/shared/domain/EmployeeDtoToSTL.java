package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.List;
import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 查询实体
 * 
 * @author wangyuanyuan
 * @date 2016年4月26日
 */
public class EmployeeDtoToSTL extends BaseEntity {

    private static final long serialVersionUID = -1;

    /**
     * 收银员集合
     */
    private List<EmployeeEntity> cashierList;

    /**
     * 部门电话
     */
    private String depTelephone;

	public List<EmployeeEntity> getCashierList() {
		return cashierList;
	}

	public void setCashierList(List<EmployeeEntity> cashierList) {
		this.cashierList = cashierList;
	}

	public String getDepTelephone() {
		return depTelephone;
	}

	public void setDepTelephone(String depTelephone) {
		this.depTelephone = depTelephone;
	}

	@Override
	public String toString() {
		return "EmployeeDtoToSTL [cashierList=" + cashierList
				+ ", depTelephone=" + depTelephone + "]";
	}

}
