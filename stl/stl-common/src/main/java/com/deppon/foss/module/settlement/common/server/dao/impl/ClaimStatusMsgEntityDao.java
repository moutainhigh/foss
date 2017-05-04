package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IClaimStatusMsgEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.ClaimStatusMsgEntity;

/**
 * 理赔支付状态消息DAO
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-12-3 上午11:38:32
 */
public class ClaimStatusMsgEntityDao extends iBatis3DaoImpl implements IClaimStatusMsgEntityDao {

	private static final String NAMESPACES = "foss.stl.ClaimStatusMsgEntityDao.";// 命名空间路径

	/** 
	 * 新加支付消息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-12-3 上午10:33:43
	 * @param entity 理赔支付状态消息
	 * @return
	 */
	@Override
	public int addClaimStatusMsg(ClaimStatusMsgEntity entity) {
		return this.getSqlSession().insert(NAMESPACES + "insert", entity);
	}

	/** 
	 * 删除支付消息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-12-3 上午10:34:11
	 * @param id
	 * @return
	 */
	@Override
	public int deleteClaimStatusMsg(String id) {
		return this.getSqlSession().insert(NAMESPACES + "delete", id);
	}

	/** 
	 * 更新消息状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-5 上午10:43:58
	 * @param entity 理赔支付状态消息
	 * @return
	 */
	public int updateMsgStatus(ClaimStatusMsgEntity entity) {
		return this.getSqlSession().update(NAMESPACES + "updateMsgStatus",entity);
	}

	/** 
	 * 查询所有未处理的付款消息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-12-3 上午10:35:17
	 * @param msgStatus 消息状态
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ClaimStatusMsgEntity> queryNotSend(String msgStatus) {
		return this.getSqlSession().selectList(NAMESPACES + "queryNotSend",msgStatus);
	}

	/** 
	 * 根据运单号查询新加理赔支付消息
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-5 上午10:43:58
	 * @param waybillNo 运单号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ClaimStatusMsgEntity> queryClaimStatusMsgByWaybillNO(String waybillNo) {
		return this.getSqlSession().selectList(NAMESPACES + "selectClaimStatusMsgByWaybillNo", waybillNo);
	}

}
