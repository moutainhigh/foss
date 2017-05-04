package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPendingSendSMSDao;
import com.deppon.foss.module.pickup.waybill.server.service.impl.PendingSendSMSService;
import com.deppon.foss.module.pickup.waybill.shared.domain.PendingSendSMSEntity;
import com.deppon.foss.module.pickup.waybill.shared.vo.PendingSendSMSVo;
import com.deppon.foss.util.UUIDUtils;

/**
 * 待处理发送短信实现类
 * @author WangQianJin
 * @date 2013-4-11 上午10:27:43
 */
public class PendingSendSMSDao extends iBatis3DaoImpl implements
		IPendingSendSMSDao {

	private static final String NAMESPACE = "foss.pkp.PendingSendSMSEntityMapper.";
	
	/**
	 * 添加待处理发送短信
	 * @author WangQianJin
	 * @date 2013-4-11 上午10:29:11
	 */
	@Override
	public int addPendingSendmailEntity(PendingSendSMSEntity record) {
		//主键
		record.setId(UUIDUtils.getUUID());
		//JobId默认为'N/A'
		record.setJobId(PendingSendSMSService.queryJobId);
		return getSqlSession().insert(NAMESPACE+"insert", record);
	}
	
	/**
	 * 查询待处理发送短信
	 * @author WangQianJin
	 * @date 2013-4-11 上午10:29:11
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PendingSendSMSEntity>  queryPendingSendmailEntity(){
		return getSqlSession().selectList(NAMESPACE+"selectPendingSendmail");
	}
	
	/**
	 * 根据ID更新操作类型
	 * @author WangQianJin
	 * @date 2013-4-11 上午10:29:11
	 */
	@Override
	public int updateOperateTypeByID(PendingSendSMSEntity record){
		return getSqlSession().update(NAMESPACE+"updateOperateTypeByID", record);
	}
	
	/**
	 * 更新待处理发送短信信息
	 * @author WangQianJin
	 * @date 2013-4-11 上午10:29:11
	 */
	@Override
	public int updateJobIDByID(PendingSendSMSEntity record){
		return getSqlSession().update(NAMESPACE+"updateJobIDByID", record);
	}	
	
	/**
	 * 更新一定数量的待处理发送短信JobId
	 * @author WangQianJin
	 * @date 2013-4-11 上午10:29:11
	 */
	@Override
	public int updateJobIDTopByRowNum(PendingSendSMSVo vo){
		return getSqlSession().update(NAMESPACE+"updateJobIDTopByRowNum", vo);
	}
	
	/**
	 * 根据jobId查询待处理发送短信信息
	 * @author WangQianJin
	 * @date 2013-4-11 上午10:29:11
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PendingSendSMSEntity>  queryPendingSendmailEntityByJobID(String jobId){
		return getSqlSession().selectList(NAMESPACE+"selectPendingSendmailEntityByJobID",jobId);
	}
	
	/**
	 * 根据JobId删除待处理发送短信信息
	 * @author WangQianJin
	 * @date 2013-4-11 上午10:29:11
	 */
	@Override
	public void deleteByJobId(String jobId){
		getSqlSession().delete(NAMESPACE+"deleteByJobId",jobId);
	}
	
	/**
	 * 根据ID删除待处理发送短信信息
	 * @author WangQianJin
	 * @date 2013-4-11 上午10:29:11
	 */
	@Override
	public void deleteById(String id){
		getSqlSession().delete(NAMESPACE+"deleteById",id);
	}
	
	/**
	 * 根据运单号查询待处理发送信息
	 * @author WangQianJin
	 * @date 2013-4-11 上午10:29:11
	 */
	@Override
	public List<PendingSendSMSEntity> queryPendingSendmailEntityByWaybillNo(String waybillNo){
		return getSqlSession().selectList(NAMESPACE+"selectPendingSendmailEntityByWaybillNo",waybillNo);
	}

}
