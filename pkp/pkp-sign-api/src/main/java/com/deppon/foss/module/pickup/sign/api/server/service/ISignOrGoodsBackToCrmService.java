package com.deppon.foss.module.pickup.sign.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.sign.api.shared.dto.ExceptionDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;

/**
 * 菜鸟项目--物流服务预警产品 
 * 1.在派送拉回原因中增加“节假日延迟”、“客户自提”、“无法进入单位派送”三个选项；
 * 2.营业员在FOSS选择派送拉回原因或者快递员在PDA选择派送拉回原因时，选择拉回原因为以下几项中一项时：
 * “开单地址有误/电话录入错误（公司）”、“节假日延迟”、“客户自提”、“无法进入单位派送”，推送运单号和异常类型至CRM，异常类型分别对应：
 * “开单地址有误/电话错误”、“节假日延迟”、“客户自提”、“无法进入单位派送”；
 * 3.营业员在FOSS选择签收类型为‘异常签收-破损’或者快递员在PDA选择签收类型为‘异常签收-破损’，推送运单号和异常类型至CRM，
 *   异常类型为‘派送前破损’；
 *4 .CRM将对应更新信息推送至DOP；
 *5，只推送订单来源为‘TAOBAO’且运输性质为‘标准快递、3.60特惠件’的运单；
 * @author 159231
 *
 */
public interface ISignOrGoodsBackToCrmService extends IService {
	/**
	 * 异常操作(货物拉回、异常签收)
	 *  * 1.在派送拉回原因中增加“节假日延迟”、“客户自提”、“无法进入单位派送”三个选项；
	 * 2.营业员在FOSS选择派送拉回原因或者快递员在PDA选择派送拉回原因时，选择拉回原因为以下几项中一项时：
	 * “开单地址有误/电话录入错误（公司）”、“节假日延迟”、“客户自提”、“无法进入单位派送”，推送运单号和异常类型至CRM，异常类型分别对应：
	 * “开单地址有误/电话错误”、“节假日延迟”、“客户自提”、“无法进入单位派送”；
	 * 3.营业员在FOSS选择签收类型为‘异常签收-破损’或者快递员在PDA选择签收类型为‘异常签收-破损’，推送运单号和异常类型至CRM，
	 *   异常类型为‘派送前破损’；
	 *4 .CRM将对应更新信息推送至DOP；
	 *5，只推送订单来源为‘TAOBAO’且运输性质为‘标准快递、3.60特惠件’的运单；
	 */
	 void exceptionOperatToCrm(ExceptionDto dto,WaybillEntity entity);
	
}