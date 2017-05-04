package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.pay.api.server.dao.IPaymentQueryDao;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillDepositReceivedPayDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPayableManageDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentAddDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentQueryDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentResultDto;

/**
 * 付款单查询接口实现
 * 
 * @author foss-qiaolifeng
 * @date 2012-11-19 上午11:16:55
 */
public class PaymentQueryDao extends iBatis3DaoImpl implements IPaymentQueryDao {

	private static final String NAMESPACES = "foss.stl.BillPaymentEntityDao.";

	/**
	 * 按输入参数查询分页付款单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-19 上午11:15:58
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPaymentEntity> queryPaymentByPageAndParams(
			BillPaymentQueryDto dto, int start, int limit) {

		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				NAMESPACES + "selectPaymentListByParams", dto, rowBounds);
	}

	/**
	 * 按输入参数查询付款单总条数、总金额
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-29 上午11:33:48
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IPaymentQueryDao#queryPaymentTotalByParams(com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentQueryDto)
	 */
	@Override
	public BillPaymentResultDto queryPaymentTotalByParams(
			BillPaymentQueryDto dto) {
		return (BillPaymentResultDto)this.getSqlSession().selectOne(
				NAMESPACES + "selectPaymentTotalByParams", dto);
	}

	/** 
	 * 按付款单号查询付款单
	 * @author foss-qiaolifeng
	 * @date 2012-11-29 上午11:34:22
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IPaymentQueryDao#queryPaymentByPaymentNos(com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPaymentEntity> queryPaymentByPaymentNos(
			BillPaymentQueryDto dto) {
		return this.getSqlSession().selectList(
				NAMESPACES + "selectPaymentListByPaymentNos", dto);
	}

	/** 
	 * 按运单号查询付款单
	 * @author foss-qiaolifeng
	 * @date 2012-11-29 上午11:34:32
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IPaymentQueryDao#queryPaymentByWaybillNos(com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPaymentEntity> queryPaymentByWaybillNos(
			BillPaymentQueryDto dto) {
		return this.getSqlSession().selectList(
				NAMESPACES + "selectPaymentListByWaybillNos", dto);
	}

	/** 
	 * 按输入参数查询付款单
	 * @author foss-qiaolifeng
	 * @date 2012-11-29 上午11:34:57
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IPaymentQueryDao#queryPaymentByParams(com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPaymentEntity> queryPaymentByParams(BillPaymentQueryDto dto) {
		return this.getSqlSession().selectList(
				NAMESPACES + "selectPaymentListByParams", dto);
	}

	/** 
	 *按来源 单号查询付款单
	 * @author foss-qiaolifeng
	 * @date 2013-3-11 上午10:56:24
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IPaymentQueryDao#queryPaymentBysourceBillNos(com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPaymentEntity> queryPaymentBysourceBillNos(
			BillPaymentQueryDto dto) {
		return this.getSqlSession().selectList(
				NAMESPACES + "selectPaymentListBySourceBillNos", dto);
	}
	
	/** 
	 *按来源 单号查询付款单
	 * @author foss-qiaolifeng
	 * @date 2013-3-11 上午10:56:24
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPaymentEntity> queryPaymentBysourceNoAsPayable(
			BillPaymentQueryDto dto) {
		return this.getSqlSession().selectList(
				NAMESPACES + "queryPaymentListBySourceNoAsPayable", dto);
	}

	/** 
	 * 按工作流号查询付款单
	 * @author foss-qiaolifeng
	 * @date 2013-6-17 下午6:33:28
	 * @param dto
	 * @return
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IPaymentQueryDao#queryPaymentByWorkFlowNos(com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPaymentEntity> queryPaymentByWorkFlowNos(
			BillPaymentQueryDto dto) {
		return this.getSqlSession().selectList(
				NAMESPACES + "selectPaymentListByWorkFlowNos", dto);
	}
	
	/**
	 * 按输入参数查询分页付款单明细
	 * 
	 * @author foss-guxinhua
	 * @date 2014-06-24 上午11:15:58
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPaymentAddDto> queryPaymentDetialByPageAndParams(BillPaymentQueryDto dto,
			BillPayableManageDto queryPayableDto,BillDepositReceivedPayDto queryDepositReceivedDto, int start, int limit) {

		RowBounds rowBounds = new RowBounds(start, limit);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dto", dto);
		map.put("queryPayableDto", queryPayableDto);
		map.put("queryDepositReceivedDto", queryDepositReceivedDto);
		return this.getSqlSession().selectList(
				NAMESPACES + "selectPaymentDetialListByParams", map, rowBounds);
	}
	
	/**
	 * 按输入参数查询分页付款单明细总条数
	 * 
	 * @author foss-guxinhua
	 * @date 2014-06-24 上午11:15:58
	 */
	@Override
	public long queryPaymentDetialByPageAndParamsSize(BillPaymentQueryDto dto,
			BillPayableManageDto queryPayableDto,BillDepositReceivedPayDto queryDepositReceivedDto) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dto", dto);
		map.put("queryPayableDto", queryPayableDto);
		map.put("queryDepositReceivedDto", queryDepositReceivedDto);
		return (Long) this.getSqlSession().selectOne(
				NAMESPACES + "selectPaymentDetialListByParamsSize", map);
	}
}
