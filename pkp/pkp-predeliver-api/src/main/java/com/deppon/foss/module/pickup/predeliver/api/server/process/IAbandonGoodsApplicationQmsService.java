package com.deppon.foss.module.pickup.predeliver.api.server.process;

/**
 * FOSS对接QMS，弃货应用服务（弃货运单信息查询、弃货签收）
 * @author 231434-FOSS-bieyexiong
 * @date 2015-5-18 上午08:59:35
 */
public interface IAbandonGoodsApplicationQmsService {
	
	/**
	 * QMS系统对接FOSS弃货签收
	 * @author 231434-FOSS-bieyexiong
	 * @date 2015-5-18 上午09:26:51
	 */
	public String signAbandonGoods(String requestJson);
	
	/**
	 * QMS对接FOSS，根据运单号，获取异常货运单信息
 	 * @author 231434-FOSS-bieyexiong
 	 * @date 2015-5-22 下午15:02:15
	 */
	public String queryUnnormalWaybill(String waybillNo);

}
