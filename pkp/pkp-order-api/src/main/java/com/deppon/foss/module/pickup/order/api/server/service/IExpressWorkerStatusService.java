package com.deppon.foss.module.pickup.order.api.server.service;

import java.util.List;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.order.api.shared.domain.ExpressWorkerStatusEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.ExpressWorkerCompleteDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.ExpressWorkerStatusDto;
import com.deppon.foss.module.pickup.order.api.shared.vo.ExpressWorerStatusConditionVo;

public interface IExpressWorkerStatusService extends IService{
	/**
	 * 开启快递员
	 * */
	public int openExpressWorker(ExpressWorkerStatusDto dto);
   	
	/**
	 * 暂停快递员
	 * liding mod for NCI 2016-05-11 
	 * 添加用户类型参数
	 * */
	public int stopExrpessWorker(ExpressWorkerStatusDto dto,String userType);
	/**
	 * 查询快递员完成情况(根据小区和快递员)
	 * */
	public List<ExpressWorkerCompleteDto> queryExpressWorkerComplete(ExpressWorerStatusConditionVo vo);
	/**
	 *根据工号查看快递员状态
	 * @param emp_Code
	 * @return ExpressWorkerStatusEntity
	 */
	public ExpressWorkerStatusEntity queryOneByEmpcode(String empCode);
	/**
	 * 查询车辆当前状态 14.7.24 gcl
	 * @param vehicleNo
	 * @return
	 */
	public String queryVehicleNoStatus(String vehicleNo);

}
