package com.deppon.foss.module.settlement.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.ClaimStatusMsgEntity;

/**
 * 理赔支付状态消息DAO
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-12-3 上午11:38:32
 * @since
 * @version
 */
public interface IClaimStatusMsgEntityDao {

	/**
	 * 新加支付消息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-12-3 上午10:33:43
	 * @param entity 理赔支付状态消息
	 * @return
	 */
	int addClaimStatusMsg(ClaimStatusMsgEntity entity);

	/**
	 * 删除支付消息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-12-3 上午10:34:11
	 * @param id
	 * @return
	 */
	int deleteClaimStatusMsg(String id);

	/**
	 * 更新消息状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-5 上午10:43:58
	 * @param entity 理赔支付状态消息
	 * @return
	 */
	int updateMsgStatus(ClaimStatusMsgEntity entity);
	
	/**
	 * 查询所有未处理的付款消息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-12-3 上午10:35:17
	 * @param msgStatus 消息状态
	 * @return
	 */
	List<ClaimStatusMsgEntity> queryNotSend(String msgStatus);

	/**
	 * 根据运单号查询新加理赔支付消息
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-5 上午10:43:58
	 * @param waybillNo 运单号
	 * @return
	 */
	List<ClaimStatusMsgEntity> queryClaimStatusMsgByWaybillNO(String waybillNo);

}
