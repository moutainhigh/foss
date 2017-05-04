package com.deppon.pda.bdm.module.foss.test.server.service.delivery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.module.pickup.pda.api.server.service.IPdaDeliverSignService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverSignDto;

public class PdaDeliverSignService implements IPdaDeliverSignService {

	private static final Log LOG = LogFactory.getLog(PdaDeliverSignService.class); 
	@Override
	public String pdaSign(PdaDeliverSignDto dto) {
		LOG.info("到达联编号："+dto.getArrivesheetNo());
		LOG.info("司机工号:"+dto.getDriverCode());
		LOG.info("付款方式："+dto.getPaymentType());
		LOG.info("签收流水号列表："+dto.getPdaSignDetailDtos());
		LOG.info("签收部门："+dto.getSignDeptCode());
		LOG.info("签收件数："+dto.getSignGoodsQty());
		LOG.info("签收备注："+dto.getSignNote());
		LOG.info("签收时间:"+dto.getSignTime());
		LOG.info("签收情况:"+dto.getSituation());
		LOG.info("车牌号"+dto.getVehicleNo());
		LOG.info("运单号："+dto.getWaybillNo());
		System.out.println("派送签收成功！");
		return "success";
	}
//	@Override
//	public String pdaExpressSign(PdaDeliverSignDto arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	@Override
	public String pdaExpressSign(PdaDeliverSignDto arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}

