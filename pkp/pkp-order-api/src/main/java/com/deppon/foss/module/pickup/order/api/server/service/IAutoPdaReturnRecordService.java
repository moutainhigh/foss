package com.deppon.foss.module.pickup.order.api.server.service;

import com.deppon.foss.module.pickup.order.api.shared.dto.PdaReturnDto;


public interface IAutoPdaReturnRecordService {
	/**
	 * PDA退回重新匹配
	 * @param entity
	 * @return
	 * 	返回值为
	 * 1、退单原因为“客户取消或重复下单、客户要求延迟揽货、联系不上客户、接货地址不准/不详、违禁品”的订单将回退CRM系统，由营业部处理；
	 * 2、退单原因类型为“车辆空间不足”或“来不及接货”的，将进入系统二次车辆匹配环节：上一级车退单，系统自动将该单按车辆匹配顺序分给下一级车，下一级车不存在时，由调度人工处理；若存在下一级车辆且该订单继续是上述原因退回，则依次类推至最后一级车辆，最后一级车辆非规则1原因的退单或不存在最后一级车，则由调度进行人工处理；
	 * 3、除以上描述的退单原因的订单，都将由调度手工处理
	 */
	public int pdaReturnProcess(String orderNo, String returnType,PdaReturnDto pdaReturnDto);
}
