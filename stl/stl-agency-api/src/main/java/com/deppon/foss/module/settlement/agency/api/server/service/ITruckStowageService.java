package com.deppon.foss.module.settlement.agency.api.server.service;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.agency.api.shared.dto.StlVehicleAssembleBillDto;

/**
 * 整车/外请车配载服务
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-11-28 上午10:06:28
 * @since
 * @version
 */
public interface ITruckStowageService {
	
	/**
	 * 录入配载单
	 *【
	 * 配载车次号：vehicleAssembleNo
	 * 运单号：waybillNo(整车时需要)，
	 * 配载类型：assembleType；
	 * 出发部门编码:origOrgCode；
	 * 出发部门名称：origOrgName；
	 * 到达部门编码：destOrgCode；
	 * 到达部门名称：destOrgName；
	 * 车牌号:vehicleNo；
	 * 车辆所有权类别：vehicleOwnerShip；
	 * 司机姓名：driverName；
	 * 司机编码：driverCode
	 * 付款方式 ：paymentType
	 * 总运费：feeTotal
	 * 预付运费总额：prePaidFeeTotal
	 * 到付运费总额：arriveFeeTotal；
	 * 是否押回单：beReturnReceipt；
	 * 币种：currencyCode；
	 * 】
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-28 下午2:28:04
	 * @param dto
	 * @param currentInfo
	 */
	void addTruckStowage(StlVehicleAssembleBillDto dto,CurrentInfo currentInfo);
	
	/**
	 * 修改配载单
	 * 【
	 * 配载车次号：vehicleAssembleNo
	 * 运单号：waybillNo(整车时需要)，
	 * 配载类型：assembleType；
	 * 出发部门编码:origOrgCode；
	 * 出发部门名称：origOrgName；
	 * 到达部门编码：destOrgCode；
	 * 到达部门名称：destOrgName；
	 * 车牌号:vehicleNo；
	 * 车辆所有权类别：vehicleOwnerShip；
	 * 司机姓名：driverName；
	 * 司机编码：driverCode
	 * 付款方式 ：paymentType
	 * 总运费：feeTotal
	 * 预付运费总额：prePaidFeeTotal
	 * 到付运费总额：arriveFeeTotal；
	 * 是否押回单：beReturnReceipt；
	 * 币种：currencyCode；
	 * 车辆出发日期：leaveTime；
	 * 】
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-28 下午2:34:52
	 * @param dto
	 * @param currentInfo
	 */
	void modifyTruckStowage(StlVehicleAssembleBillDto dto,CurrentInfo currentInfo);
	
	/**
	 * 调整整车/外请车费用信息
	 * 【配载车次号 vehicleAssembleNo， 配载类型：assembleType， 出发部门编码：origOrgCode
	 * 到达部门编码：destOrgCode ，总费用：feeTotal
	 * 奖罚类型：awardType 调整费用：adjustFee
	 *  部门经理审核状态：auditState 】
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-30 下午5:03:47
	 * @param dto
	 * @param currentInfo
	 */
	void adjustOutVehicleFee(StlVehicleAssembleBillDto dto,
			CurrentInfo currentInfo);
	
	/**
	 * 作废配置单
	 * 【配载车次号 vehicleAssembleNo 运单号 waybillNo 配载类型 assembleType 出发部门编码
	 * origOrgCode 到达部门编码 destOrgCode 车辆所有权类别 vehicleOwnerShip 】
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-28 下午2:35:33
	 * @param dto
	 * @param currentInfo
	 */
	void disableTruckStowage(StlVehicleAssembleBillDto dto,CurrentInfo currentInfo);
}
