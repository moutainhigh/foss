package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.common.api.shared.domain.ClaimStatusMsgEntity;

/**
 * 
 * 理赔支付消息service
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-12-3 上午10:50:23
 */
public interface IClaimStatusMsgService extends IService {

	/**
	 * 
	 * 新加理赔支付消息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-12-3 上午10:50:50
	 */
	void addClaimStatusMsg(ClaimStatusMsgEntity entity);

	/**
	 * 删除理赔支付消息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-12-3 上午10:51:19
	 */
	void deleteClaimStatusMsg(String id);

	/**
	 * 查询所有理赔支付消息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-12-3 上午10:52:18
	 */
	List<ClaimStatusMsgEntity> queryNotSendMsg();
	
	/**
	 * 根据运单号查询新加理赔支付消息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-5 下午3:46:14
	 * @param waybillNo
	 * @return
	 */
	List<ClaimStatusMsgEntity> queryClaimStatusMsgByWaybillNO(
			String waybillNo) ;

	/**
	 * 
	 * 更新已发送消息状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-5 上午10:51:20
	 */
	void updateMsgStatusSended(String entityId);
}
