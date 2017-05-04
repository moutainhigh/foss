package com.deppon.pda.bdm.module.foss.test.server.service.acct;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.pda.api.server.service.IPdaDispatchOrderService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDispatchOrderDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaForwardDiverDto;
import com.deppon.foss.module.pickup.pda.api.shared.exception.PdaProcessException;
import com.deppon.foss.module.pickup.waybill.shared.domain.ReturnBillingEWaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillConditionDto;
import com.deppon.pda.bdm.module.foss.test.server.service.temp.DispatchEWaybillDto;
import com.deppon.pda.bdm.module.foss.test.server.service.temp.EWaybillCustomerDto;

public class PdaDispatchOrderService implements IPdaDispatchOrderService {

	@Override
	public List<PdaDispatchOrderDto> queryDispatchOrderByVehicle(String driverCode, String vehicleNo) throws PdaProcessException {
		List<PdaDispatchOrderDto> lists = new ArrayList<PdaDispatchOrderDto>();
		PdaDispatchOrderDto dto1 = new PdaDispatchOrderDto();
		dto1.setOrderNo("10000000003");// 订单编号
		dto1.setSalesDepartmentCode("W060002070701");// 营业部编号
		dto1.setReceiveMethod("自提");// 提货方式
		dto1.setProductCode("精准卡航");// 运输性质
		dto1.setCustomerPickupOrgCode("W0600307060803");// 提货网点编号
		dto1.setPickupProvince("a7c86a5e-afad-4f2a-9f40-49574b48ba75");// 接货省
		dto1.setPickupCity("XZQY-ID-0000002");// 接货市
		dto1.setPickupCounty("87e790ae-bbed-43a2-ae87-4d1b9bb9a927");// 接货区县
		dto1.setPickupElseAddress("上海市青浦区");// 接货区县
		dto1.setTel("5465465465");// 客户电话
		dto1.setCustomerName("张三");// 客户姓名
		dto1.setEarliestPickupTime(new Date());// 最早接货时间
		dto1.setLatestPickupTime(new Date());// 最晚接货时间
		dto1.setVolume(new BigDecimal(100));// 体积
		dto1.setWeight(new BigDecimal(1000));// 重量
		dto1.setGoodsQty(10);// 件数
		dto1.setGoodsPackage("木架");// 包装类型
		dto1.setSalesDepartmentTel("1236549887");// 营业部联系电话
		dto1.setOrderTime(new Date());// 下单时间
		dto1.setOrderType("PICKUP_ORDER");// 订单类型
		dto1.setGoodsType("A货");// 货物类型
		dto1.setVehicleType("大车");// 车型需求
		dto1.setCreateUserName("李四");// 请车专员姓名
		dto1.setOperator("调度");// 受理人
		dto1.setOrderNotes("备注");
		dto1.setConsigneeAddress("上海市青浦区明珠路");//收获地址
		// 营业部联系人
		
		PdaDispatchOrderDto dto2 = new PdaDispatchOrderDto();
		dto2.setOrderNo("10000000004");// 订单编号
		dto2.setSalesDepartmentCode("W060002070701");// 营业部编号
		dto2.setReceiveMethod("自提");// 提货方式
		dto2.setProductCode("精准卡航");// 运输性质
		dto2.setCustomerPickupOrgCode("W01130603");// 提货网点编号
		dto2.setPickupProvince("a7c86a5e-afad-4f2a-9f40-49574b48ba75");// 接货省
		dto2.setPickupCity("XZQY-ID-0000002");// 接货市
		dto2.setPickupCounty("87e790ae-bbed-43a2-ae87-4d1b9bb9a927");// 接货区县
		dto2.setPickupElseAddress("上海市青浦区");// 接货区县
		dto2.setTel("13212313221");// 客户电话
		dto2.setCustomerName("张三");// 客户姓名
		dto2.setEarliestPickupTime(new Date());// 最早接货时间
		dto2.setLatestPickupTime(new Date());// 最晚接货时间
		dto2.setVolume(new BigDecimal(100));// 体积
		dto2.setWeight(new BigDecimal(1000));// 重量
		dto2.setGoodsQty(10);// 件数
		dto2.setGoodsPackage("木架");// 包装类型
		dto2.setSalesDepartmentTel("1236549887");// 营业部联系电话
		dto2.setOrderTime(new Date());// 下单时间
		dto2.setOrderType("TRANSFER_ORDER");// 订单类型
		dto2.setGoodsType("A货");// 货物类型
		dto2.setVehicleType("大车");// 车型需求
		dto2.setCreateUserName("李四");// 请车专员姓名
		dto2.setOperator("调度");// 受理人
		dto2.setOrderNotes("备注");
		dto2.setConsigneeAddress("上海市青浦区徐祥路");//收获地址
		
		lists.add(dto1);
		lists.add(dto2);
		return lists;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.pda.api.server.service.IPdaDispatchOrderService#queryForwardListByDriverCode(java.lang.String)
	 */
	@Override
	public List<PdaForwardDiverDto> queryForwardListByDriverCode(
			String driverCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillCustomerDto> queryEWaybillOrderBigCust(
			EWaybillConditionDto eWaybillConditionDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<com.deppon.foss.module.pickup.waybill.shared.dto.DispatchEWaybillDto> queryEWaybillOrderByCust(
			EWaybillConditionDto eWaybillConditionDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<com.deppon.foss.module.pickup.waybill.shared.dto.DispatchEWaybillDto> queryIndividualCustEwaybill(
			EWaybillConditionDto eWaybillConditionDto) {
		// TODO Auto-generated method stub
		return null;
	}


	
}
