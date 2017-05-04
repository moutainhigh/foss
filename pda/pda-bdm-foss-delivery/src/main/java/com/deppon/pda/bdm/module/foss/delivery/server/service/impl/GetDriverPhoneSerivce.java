package com.deppon.pda.bdm.module.foss.delivery.server.service.impl;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.DeliveryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.DeliverbillSMSEntiy;

/**
 * 根据前端传入的司机编号查询司机电话号把电话号返回给前台
 * @author 354682
 * 
 */
public class GetDriverPhoneSerivce implements
		IBusinessService<String, DeliverbillSMSEntiy> {
	private IEmployeeService employeeService;

	/**
	 * 解析包体
	 */
	@Override
	public DeliverbillSMSEntiy parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		DeliverbillSMSEntiy deliverbillSMSEntiy = JsonUtil.parseJsonToObject(
				DeliverbillSMSEntiy.class, asyncMsg.getContent());
		return deliverbillSMSEntiy;
	}

	/**
	 * 服务方法
	 */
	@Override
	public String service(AsyncMsg asyncMsg,
			DeliverbillSMSEntiy deliverbillSMSEntiy) throws PdaBusiException {

		EmployeeEntity employee = null;
		try {
			// 验证数据的有效性
			this.validate(deliverbillSMSEntiy);
				// 根据司机编码查询司机姓名和电话
				employee = employeeService.queryEmployeeByEmpCode(deliverbillSMSEntiy.getDriverCode());
				//判断实体是否为空
				if(employee==null){
					throw new FossInterfaceException(null, "foss没有返回到信息");
				}
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		} catch (Exception e) {
			throw new FossInterfaceException(null, "未知异常"+e.getMessage());
		}
		
		return employee.getMobilePhone();

	}

	/**
	 * 验证数据的有效性
	 */
	private void validate(
			DeliverbillSMSEntiy deliverbillSMSEntiy) {
		Argument.notNull(deliverbillSMSEntiy, "deliverbillSMSEntiy");
		// 司机工号
		Argument.hasText(deliverbillSMSEntiy.getDriverCode(),
				"DeliverbillSMSEntiy.driverCode");
	}

	/**
	 * 服务类型
	 */
	@Override
	public String getOperType() {
		// TODO Auto-generated method stub
		return DeliveryConstant.OPER_TYPE_DRIVERPHONE_GET.VERSION;
	}

	/**
	 * 是否同步
	 */
	@Override
	public boolean isAsync() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;

	}

}
