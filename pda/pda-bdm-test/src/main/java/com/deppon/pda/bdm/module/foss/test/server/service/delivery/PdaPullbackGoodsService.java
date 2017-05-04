package com.deppon.pda.bdm.module.foss.test.server.service.delivery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.module.pickup.pda.api.server.service.IPdaPullbackGoodsService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaPullbackgoodsDto;

public class PdaPullbackGoodsService implements IPdaPullbackGoodsService {
	private static final Log LOG = LogFactory.getLog(PdaSignService.class);
	@Override
	public String addPullbackGoodsSign(PdaPullbackgoodsDto pullbackgoodsDto) {
		System.out.println("success..");
		LOG.info("到达联:"+pullbackgoodsDto.getArrivesheetNo());
		LOG.info("司机:"+pullbackgoodsDto.getDriverCode());
		LOG.info("拉回原因:"+pullbackgoodsDto.getPullbackReason());
		LOG.info("签收备注:"+pullbackgoodsDto.getSignNote());
		LOG.info("车牌号:"+pullbackgoodsDto.getVehicleNo());
		LOG.info("运单号:"+pullbackgoodsDto.getWaybillNo());
		LOG.info("拉回件数:"+pullbackgoodsDto.getPullbackQty());
		LOG.info("拉回时间:"+pullbackgoodsDto.getPullbackTime());
		return "success";
	}

}
