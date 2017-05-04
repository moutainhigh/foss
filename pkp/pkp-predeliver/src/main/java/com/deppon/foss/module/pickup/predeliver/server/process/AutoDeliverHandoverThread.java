package com.deppon.foss.module.pickup.predeliver.server.process;

import java.util.List;

import javax.annotation.Resource;

import com.deppon.foss.module.pickup.predeliver.api.server.service.IHandoverBillVoService;
import com.deppon.foss.module.pickup.predeliver.api.shared.tools.DeliverThreadPoolCaller;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.HandoverBillVo;
import com.deppon.foss.module.pickup.predeliver.server.service.impl.AutoHBMatchCommunityService;

public class AutoDeliverHandoverThread extends DeliverThreadPoolCaller {

	/**
	 * 交单
	 */
	private IHandoverBillVoService handoverBillVoService;
	
	/**
	 * 交单自动匹配小区和车辆
	 */
	private AutoHBMatchCommunityService autoHBMatchCommunityService;

	@Override
	public int serviceCaller() {
		
		// 获取没有跑过Gis的有效交单
		List<HandoverBillVo> handoverBillList = handoverBillVoService.selectHBMatchCommunityList();
		
		if (handoverBillList != null && handoverBillList.size() > 0) {
			autoHBMatchCommunityService.matchCommunity(handoverBillList);
			return handoverBillList.size();
		}
		return 0;
	}

	/**
	 * 设置handoverBillVoService
	 * 
	 * @param handoverBillVoService
	 *            要设置的handoverBillVoService
	 */
	@Resource
	public void setHandoverBillVoService(
			IHandoverBillVoService handoverBillVoService) {
		this.handoverBillVoService = handoverBillVoService;
	}

	/**
	 * 设置autoHBMatchCommunityService
	 * @param autoHBMatchCommunityService 要设置的autoHBMatchCommunityService
	 */
	@Resource
	public void setAutoHBMatchCommunityService(
			AutoHBMatchCommunityService autoHBMatchCommunityService) {
		this.autoHBMatchCommunityService = autoHBMatchCommunityService;
	}
	
	

}
