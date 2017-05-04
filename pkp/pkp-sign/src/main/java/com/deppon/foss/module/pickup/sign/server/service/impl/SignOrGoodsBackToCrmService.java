package com.deppon.foss.module.pickup.sign.server.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.inteface.domain.crm.WarnMessageRequest;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignOrGoodsBackToCrmService;
import com.deppon.foss.module.pickup.sign.api.shared.define.ExceptionConstants;
import com.deppon.foss.module.pickup.sign.api.shared.dto.ExceptionDto;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
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
public class SignOrGoodsBackToCrmService implements ISignOrGoodsBackToCrmService {
	/**
	 * 记录
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SignOrGoodsBackToCrmService.class);
	/**
	 * 菜鸟项目 FOSS系统货物拉回 异常破损签收同步CRM异常类型的版本号
	 */
	public static final String ESB_FOSS2ESB_EXCEPTION_OPEART_VERSION = "0.1";
	/**
	 * 菜鸟项目 FOSS系统货物拉回 异常破损签收同步CRM异常类型的标识符  
	 */
	public static final String ESB_FOSS2ESB_EARLY_WARNING_INFO_CODE = "ESB_FOSS2ESB_EARLY_WARNING_INFO";
	
	private IWaybillManagerService waybillManagerService;
	
	/**
	 * 异常操作(货物拉回、异常签收)
	 */
	@Override
	public void exceptionOperatToCrm(ExceptionDto dto,WaybillEntity entity) {
		//如果运单号、签收情况不为空
		if(dto!= null && StringUtils.isNotBlank(dto.getWaybillNo())&& 
				 StringUtils.isNotBlank(dto.getSituation())){
			//如果签收情况为货物拉回
			if(ArriveSheetConstants.SITUATION_GOODS_BACK.equals(dto.getSituation())){
				if(StringUtils.isNotBlank(dto.getExceptionType())){
					//如果拉回原因---节假日延迟
					if(ExceptionConstants.HOLIDAY_DELAY.equals(dto.getExceptionType())){
						dto.setExceptionTypeDesc(ExceptionConstants.HOLIDAY_DELAY_DESC);
					}
					//拉回原因---客户自提
					else if(ExceptionConstants.CUSTOMER_PICKUP.equals(dto.getExceptionType())){
						dto.setExceptionTypeDesc(ExceptionConstants.CUSTOMER_PICKUP_DESC);
					}
					//如果拉回原因---开单地址有误/电话录入错误
					else if(ExceptionConstants.PHONE_WRONG.equals(dto.getExceptionType())){
						dto.setExceptionTypeDesc(ExceptionConstants.PHONE_WRONG_DESC);
					}
					//如果拉回原因---无法进入单位派送
					else if(ExceptionConstants.NOT_IN_UNIT_DELIVER.equals(dto.getExceptionType())){
						dto.setExceptionTypeDesc(ExceptionConstants.NOT_IN_UNIT_DELIVER_DESC);
					}
				}
			//如果签收情况为异常-破损
			}else if(ArriveSheetConstants.SITUATION_UNNORMAL_BREAK.equals(dto.getSituation())){
				dto.setExceptionTypeDesc(ExceptionConstants.DELIVER_BEFORE_BREAK_DESC);
			}
			if(StringUtils.isNotBlank(dto.getExceptionTypeDesc())){
				if(entity == null){
					entity = waybillManagerService.queryWaybillBasicByNo(dto.getWaybillNo());
				}
				if(entity!= null && StringUtils.isNotBlank(entity.getProductCode())
						&& (WaybillConstants.directDetermineIsExpressByProductCode(entity.getProductCode()))
						&& StringUtils.isNotBlank(entity.getOrderChannel()) 
						&& WaybillConstants.CRM_ORDER_CHANNEL_TAOBAO.equals(entity.getOrderChannel())){
					// 调用CRM接口
					//准备消息头信息
					AccessHeader header = createAccessHeader(dto.getWaybillNo());
					//创建具体消息
					WarnMessageRequest request = new WarnMessageRequest();
					request.setBillNumber(dto.getWaybillNo());
					request.setExceptionType(dto.getExceptionTypeDesc());
					// 发送请求
					try {
						ESBJMSAccessor.asynReqeust(header, request);
					} catch (Exception e) {
						LOGGER.error("菜鸟项目 FOSS系统货物拉回 异常破损签收同步CRM异常类型 通过ESB发送请求报错:"+dto.getWaybillNo()+e.getMessage());
					}
					LOGGER.info("菜鸟项目 FOSS系统货物拉回 异常破损签收同步CRM异常类型 完成"+dto.toString());
				}
			}
		}
	}
	private AccessHeader createAccessHeader(String waybillNo) {
		AccessHeader header = new AccessHeader();
		//business code 根据esb提供
		header.setEsbServiceCode(ESB_FOSS2ESB_EARLY_WARNING_INFO_CODE);
		//版本随意
		header.setVersion(ESB_FOSS2ESB_EXCEPTION_OPEART_VERSION);
		//business id 随意
		header.setBusinessId(waybillNo);
		//运单号放在消息头中传给 waybillNo
		header.setBusinessDesc1(waybillNo);
		return header;
	}
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	
	
}