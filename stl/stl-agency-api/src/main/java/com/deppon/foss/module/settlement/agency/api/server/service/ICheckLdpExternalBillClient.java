package com.deppon.foss.module.settlement.agency.api.server.service;

/**
 * 
 * @author 325369
 * @date  2016-07-05 下午19:36:42
 * 校验运单号和快递代理在外发单中是否存在接口
 */
public interface ICheckLdpExternalBillClient {
	
	public boolean sendLdpExternalBillMsgToWK(String waybillNo, String agencyCompanyCode);

}
