package com.deppon.foss.module.pickup.crm.server.service.impl;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.crm.ALiChangeOrderRequest;
import com.deppon.esb.inteface.domain.crm.ALiChangeOrderResponse;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcForAccountServiceDto;

public class ALiChangeJMSProcessor implements IProcess {
	 private static final Logger LOGGER = LoggerFactory.getLogger(ALiChangeJMSProcessor.class);
	 private IWaybillManagerService waybillManagerService;
	 private IWaybillRfcService waybillRfcService;
	@Override
	public Object process(Object req) throws ESBBusinessException {
		// TODO Auto-generated method stub
		LOGGER.info(" ***************************** Begin to AliChangeOrder data ***************************** ");
		ALiChangeOrderResponse res = new ALiChangeOrderResponse();
		res.setResult(false);
		if(req!=null){
			ALiChangeOrderRequest request = (ALiChangeOrderRequest) req;
			WaybillRfcForAccountServiceDto dto = new WaybillRfcForAccountServiceDto();
			res.setOrderNo(request.getOrderNo());
			if(request.getUserName()==null || "".equals(request.getUserName())){
				res.setFailureReason("申请人不能为空;userName is Not NULL");
				return res;
			}
			dto.setApplyPerson(request.getUserName());
			dto.setApplyTime(new Date());
			dto.setChangeContent(request.getChangeContent());
			dto.setContact(request.getLinkMan());
			dto.setContactHandy(request.getLinkManMobile());
			//获取部门标杆编码
			String departureDeptNumber = waybillManagerService.queryDepartureDeptNumber(request.getWaybillNum());
			if(departureDeptNumber!=null){
				dto.setUnifieldCode(departureDeptNumber);
			} 
			
			dto.setWaybillNumber(request.getWaybillNum());
			if(dto.getUnifieldCode()==null){
				res.setFailureReason("运单号："+dto.getWaybillNumber()+"申请更改失败，改运单号对应的部门标杆编码为空");
//				throw new ESBConvertException("运单号："+dto.getWaybillNumber()+"申请更改失败，部门标杆编码为空");
				LOGGER.info("运单号："+dto.getWaybillNumber()+"申请更改失败，改运单号对应的部门标杆编码为空");
			}
			// TODO
			Map<String,Object> map = waybillRfcService.aLiChangeOrder(dto);
			res.setResult((Boolean)map.get("result"));
			if(res.getFailureReason()==null && map.get("failureReason")!=null){
				res.setFailureReason(map.get("failureReason").toString());
			}
			return res;
		}
		res.setFailureReason("没有获取到请求内容");
		return res;
	}

	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}

}
