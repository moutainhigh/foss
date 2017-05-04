package com.deppon.pda.bdm.module.foss.accept.server.service.impl;

import org.apache.log4j.Logger;

import com.deppon.foss.module.pickup.pda.api.server.service.IPdaDispatchOrderService;
import com.deppon.foss.module.pickup.waybill.shared.domain.ReturnBillingEWaybillEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.ReturnBillingPrintEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.ReturnBillingPrintResult;

/**
 * 获取转寄件退回打印信息
 * @ClassName ReturnBillingPrintService.java
 * @Description 
 * @author 245955
 * @date 2016-03-15
 */
public class ReturnBillingPrintService implements IBusinessService<ReturnBillingPrintResult,ReturnBillingPrintEntity> {
	private Logger log = Logger.getLogger(getClass());
	private IPdaDispatchOrderService pdaDispatchOrderService;
	/**
	 * 获取包头参数信息
	 */
	@Override
	public ReturnBillingPrintEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		ReturnBillingPrintEntity returnBillingPrintEntity=JsonUtil.parseJsonToObject(ReturnBillingPrintEntity.class,asyncMsg.getContent());
		return returnBillingPrintEntity;
	}

	/**
	 * 方法入口Service
	 */
	@Override
	public ReturnBillingPrintResult service(AsyncMsg asyncMsg, ReturnBillingPrintEntity param)
			throws PdaBusiException {
		//参数非空校验
		this.validate(asyncMsg, param);
		ReturnBillingPrintResult result=null;
		log.debug("---调用FOSS获取转寄件退回打印信息 接口开始---");
		String waiblllCode=param.getWaybillCode();
		String changCode=param.getChangCode();
		log.debug("运单号为:"+waiblllCode+";标示位为:"+changCode);
		ReturnBillingEWaybillEntity entity=pdaDispatchOrderService.getReturnWaybillEntity(waiblllCode,changCode);
		if(entity!=null){
			result=new ReturnBillingPrintResult();
			//目的站
			result.setDestination(entity.getDestination());
			//件数
			result.setPieces(entity.getPieces());
			 //收货人地址
		    result.setReciveAddress(entity.getReturnGoodsInfo());
		    //报价金额
		    result.setInsuranceAmount(entity.getInsuranceAmount());
		    //代收货款
		    result.setCodAmount(entity.getCodAmount());
		    //签收单
		    result.setReturnBillType(entity.getReturnBillType());
		    //包装费
		    result.setPackageFee(entity.getPackageFee());
		    //运费
		    result.setTransportFee(entity.getTransportFee());
		    //到付款合计
		    result.setToPayAmount(entity.getToPayAmount());
		    //返货类型
		    result.setReturnType(entity.getReturnType());
		    //到达目的站城市外场的简称
		    result.setTransportCityName(entity.getTransportCityName());
		    //到达目的站虚拟快递营业部
		    result.setCutsetrPickuRgname(entity.getCutsetrPickuRgname());
		}
		return result;
	}
	private void validate(AsyncMsg asyncMsg, ReturnBillingPrintEntity returnBillingPrintEntity) throws PdaBusiException {
		// 运单号不能为空
		Argument.hasText(returnBillingPrintEntity.getWaybillCode(), "waybillCode");
	}
	
	/**
	 * 操作类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_EXP_RCV_RETURN_PRINT.VERSION;
	}

	/**
	 * @description 下拉接口，同步
	 * @return false
	 * @author 245955
	 * @date 2016-03-15
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaDispatchOrderService(
			IPdaDispatchOrderService pdaDispatchOrderService) {
		this.pdaDispatchOrderService = pdaDispatchOrderService;
	}
 
}
