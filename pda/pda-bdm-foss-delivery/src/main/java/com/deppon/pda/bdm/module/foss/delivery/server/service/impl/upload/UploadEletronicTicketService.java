package com.deppon.pda.bdm.module.foss.delivery.server.service.impl.upload;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ElectronicTicketEntity;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaImageService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;
import com.deppon.pda.bdm.module.core.shared.constants.version.DeliveryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.domain.Result;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PDAElectronicTicketEntity;

public class UploadEletronicTicketService implements IBusinessService<Result, PDAElectronicTicketEntity> {

	private IPdaImageService pdaImageSerice;
	
	public void setPdaImageSerice(IPdaImageService pdaImageSerice) {
		this.pdaImageSerice = pdaImageSerice;
	}


	@Override
	public PDAElectronicTicketEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		PDAElectronicTicketEntity eleTikcet = JsonUtil.parseJsonToObject(PDAElectronicTicketEntity.class, asyncMsg.getContent());
		return eleTikcet;
	}

	@Override
	public Result service(AsyncMsg asyncMsg,
			PDAElectronicTicketEntity param) throws PdaBusiException {
			Result result = new Result();
			//校验不包裹在异常，直接抛出去
			this.validate(param);
			//foss接口接收参数
			List<ElectronicTicketEntity> addToFoss = new ArrayList<ElectronicTicketEntity>();
			//多运单
			List<String> waybills = param.getWayBillNo();
			try {
				for(String waybill : waybills){
					ElectronicTicketEntity passFoss = new ElectronicTicketEntity();
					passFoss.setCardMoney(param.getCardMoney());
					passFoss.setCardTime(param.getCardTime());
					passFoss.setWayBillNo(waybill);
					passFoss.setSerialNo(param.getSerialNo());
					passFoss.setImageUrl(param.getImageUrl());
					addToFoss.add(passFoss);
				}
				//调用foss接口
				pdaImageSerice.pdaAddImage(addToFoss);
				result.setRetStatus(Result.SUCCESS);
			} catch (BusinessException e) {
				throw new FossInterfaceException(e.getCause(),e.getErrorCode());
			} catch (Exception e){
				throw new FossInterfaceException(e.getCause(),ExceptionConstant.ERRCODE_BASE);
			}
			
		return result;
	}

	@Override
	public String getOperType() {
		return DeliveryConstant.OPER_TYPE_UPLOAD_ELECTRONIC_TICKET.VERSION;
	}

	private void validate(PDAElectronicTicketEntity param){
		Argument.notNull(param, "PDAElectronicTicketEntity");
		//刷钱金额
		Argument.notNull(param.getCardMoney(), "PDAElectronicTicketEntity.cardMoney");
		//支付单号
		Argument.hasText(param.getSerialNo(), "PDAElectronicTicketEntity.serialNo");
		//运单号
//		Argument.hasText(param.getWayBillNo(), "PDAElectronicTicketEntity.wayBillNo");
		//图片地址
		Argument.hasText(param.getImageUrl(), "PDAElectronicTicketEntity.imageUrl");
		//刷卡时间
		Argument.notNull(param.getCardTime(), "PDAElectronicTicketEntity.cardTime");
		
		
	}
	
	@Override
	public boolean isAsync() {
		return false;
	}

}