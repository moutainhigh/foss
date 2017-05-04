package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IOverdueSFPaymentApplyDao;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.OverdueSFPaymentApplyEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OverdueSFPaymentApplyDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OverdueSFPaymentApplyQueryDto;

public class OverdueSFPaymentApplyDao extends iBatis3DaoImpl implements IOverdueSFPaymentApplyDao {

	private final static String NAMESPACE = "foss.stl.OverdueSFPaymentApplyDao.";

	/**
	 * 按记账日期查询
	 * @author 105762
	 * @date 2014-4-23 下午3:30:29
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<OverdueSFPaymentApplyDto> queryByAccountDate(OverdueSFPaymentApplyQueryDto dto) {
		return (List<OverdueSFPaymentApplyDto>) this.getSqlSession().selectList(NAMESPACE + "queryByAccountDate", dto,
				new RowBounds(dto.getStart(), dto.getLimit()));
	}

	/**
	 * 按记账日期查询总数
	 * @author 105762
	 * @date 2014-4-23 下午3:30:29
	 */
	@Override
	public int queryCountByAccountDate(OverdueSFPaymentApplyQueryDto dto) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE + "queryCountByAccountDate", dto);
	}

	/**
	 * 按来源单号查询
	 * @author 105762
	 * @date 2014-4-23 下午3:31:03
	 * @return
	 * @see
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<OverdueSFPaymentApplyDto> queryBySourceBillNo(OverdueSFPaymentApplyQueryDto dto) {
		return (List<OverdueSFPaymentApplyDto>) this.getSqlSession().selectList(NAMESPACE + "queryByWaybillNos", dto);
	}

	/**
	 * 按应付单号查询
	 * @author 105762
	 * @date 2014-4-23 下午3:31:03
	 * @see
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<OverdueSFPaymentApplyDto> queryByPayableNo(OverdueSFPaymentApplyQueryDto dto) {
		return (List<OverdueSFPaymentApplyDto>) this.getSqlSession().selectList(NAMESPACE + "queryByPayableNos", dto);
	}

	/**
	 * 插入或更新超时装卸费付款申请单
	 * @author 105762
	 * @date 2014-5-7 下午5:23:02
	 */
	@Override
	public int insertOrUpdate(OverdueSFPaymentApplyEntity en) {
		return this.getSqlSession().insert(NAMESPACE + "insertOrUpdate", en);
	}
	
	/**
	 * <p>审核</p> 
	 * @author 105762
	 * @date 2014-5-14 上午11:05:02
	 */
	@Override
	public int updateForAudit(OverdueSFPaymentApplyEntity en) {
		return this.getSqlSession().insert(NAMESPACE + "updateForAudit", en);
	}
}
