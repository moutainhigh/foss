package com.deppon.pda.bdm.module.foss.test.server.service.delivery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.module.pickup.pda.api.server.service.IPdaSignService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaSignDto;

public class PdaSignService implements IPdaSignService {
	private static final Log LOG = LogFactory.getLog(PdaSignService.class);
	@Override
	public String pdaSign(PdaSignDto dto) {
		LOG.info("到达联:"+dto.getArrivesheetNo());
		LOG.info("操作人:"+dto.getOperatorCode());
		LOG.info("流水号列表:"+dto.getSerialNos());
		LOG.info("签收部门:"+dto.getSignDeptCode());
		LOG.info("签收货物数量:"+dto.getSignGoodsQty());
		LOG.info("签收备注:"+dto.getSignNote());
		LOG.info("签收时间:"+dto.getSignTime());
		LOG.info("签收情况:"+dto.getSituation());
		LOG.info("运单号:"+dto.getWaybillNo());
		System.out.println("签收成功");
		return "success";
	}
//	@Override
//	public String pdaExpressSign(PdaSignDto arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	@Override
	public String pdaExpressSign(PdaSignDto arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
