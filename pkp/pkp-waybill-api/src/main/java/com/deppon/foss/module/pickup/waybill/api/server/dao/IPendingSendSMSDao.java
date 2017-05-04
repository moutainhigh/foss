package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.PendingSendSMSEntity;
import com.deppon.foss.module.pickup.waybill.shared.vo.PendingSendSMSVo;

/**
 * 待处理发送短信接口
 * @author WangQianJin
 * @date 2013-4-11 上午10:27:43
 */
public interface IPendingSendSMSDao {

	/**
	 * 添加待处理发送短信
	 * @author WangQianJin
	 * @date 2013-4-11 上午10:29:11
	 */
	int addPendingSendmailEntity(PendingSendSMSEntity record);
	
	/**
	 * 查询待处理发送短信
	 * @author WangQianJin
	 * @date 2013-4-11 上午10:29:11
	 */
	List<PendingSendSMSEntity>  queryPendingSendmailEntity();
	
	/**
	 * 根据ID更新操作类型
	 * @author WangQianJin
	 * @date 2013-4-11 上午10:29:11
	 */
	int updateOperateTypeByID(PendingSendSMSEntity record);	
	
	/**
	 * 更新待处理发送短信信息
	 * @author WangQianJin
	 * @date 2013-4-11 上午10:29:11
	 */
	int updateJobIDByID(PendingSendSMSEntity record);
	
	/**
	 * 更新一定数量的待处理发送短信JobId
	 * @author WangQianJin
	 * @date 2013-4-11 上午10:29:11
	 */
	int updateJobIDTopByRowNum(PendingSendSMSVo vo);	
	
	/**
	 * 根据jobId查询待处理发送短信信息
	 * @author WangQianJin
	 * @date 2013-4-11 上午10:29:11
	 */
	List<PendingSendSMSEntity>  queryPendingSendmailEntityByJobID(String jobId);	
	
	/**
	 * 根据JobId删除待处理发送短信信息
	 * @author WangQianJin
	 * @date 2013-4-11 上午10:29:11
	 */
	void  deleteByJobId(String jobId);
	
	/**
	 * 根据ID删除待处理发送短信信息
	 * @author WangQianJin
	 * @date 2013-4-11 上午10:29:11
	 */
	void  deleteById(String id);	
	
	/**
	 * 根据运单号查询待处理发送信息
	 * @author WangQianJin
	 * @date 2013-4-11 上午10:29:11
	 */
	List<PendingSendSMSEntity> queryPendingSendmailEntityByWaybillNo(String waybillNo);
}
