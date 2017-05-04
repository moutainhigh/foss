package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.EmployeeVo;

/**
 * 根据条件查询快递员信息--公共选择器
 * 
 * @author WangPeng
 * @date   2013-08-28 9:30 AM
 *
 */
public class CommonExpressEmployeeAction extends AbstractAction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1013286827535614774L;
	//存放数据的Vo对象
	EmployeeVo employeeVo = new EmployeeVo();
	

	public EmployeeVo getEmployeeVo() {
		return employeeVo;
	}


	public void setEmployeeVo(EmployeeVo employeeVo) {
		this.employeeVo = employeeVo;
	}

	//查询service接口
	ICommonExpressEmployeeService commonExpressEmployeeService;
	
	public void setCommonExpressEmployeeService(
			ICommonExpressEmployeeService commonExpressEmployeeService) {
		this.commonExpressEmployeeService = commonExpressEmployeeService;
	}


	/**
	 * 根据条件查询快递员信息
	 * 
	 * @author WangPeng
	 * @Date   2013-8-28 上午9:36:24
	 * @param  entity
	 * @param  start
	 * @param  limit
	 * @return List<EmployeeEntity>
	 * 
	 *
	 */
	@JSON
	 public String queryExpressEmp(){
		employeeVo.setEmployeeList(commonExpressEmployeeService
				.queryExpressEmployeeByCondition(employeeVo.getEmployeeDetail(), start, limit));
		this.setTotalCount(commonExpressEmployeeService.recordQueryCount(employeeVo.getEmployeeDetail()));
     return returnSuccess();
	}
}
