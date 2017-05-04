package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ICODCompositeQueryDao;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODCompositeGridDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODCompositeQueryDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 代收货款综合查询
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-30 下午4:08:11
 */
public class CODCompositeQueryDao extends iBatis3DaoImpl implements
		ICODCompositeQueryDao {

	private static final String NAMESPACE = "foss.stl.CODCompositeQueryDao.";

	/**
	 * 按单号进行(分页查询)
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 下午4:17:21
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.ICODCompositeQueryDao#queryByWaybillNO(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CODCompositeGridDto> queryByWaybillNo(CODCompositeQueryDto dto) {
		if (!CollectionUtils.isEmpty(dto.getWaybillNos())) {
			// 返回查询结果
			return this.getSqlSession().selectList(
					NAMESPACE + "queryCODByWaybillNo", dto);
		} else {
			throw new SettlementException("内部错误，参数为空");
		}
	}
	
	/**
	 * 按合并编号进行(分页查询)
	 * 
	 * @date 2012-10-30 下午4:17:21
	 */
	@Override
	public List<CODCompositeGridDto> queryByMergeCode(CODCompositeQueryDto dto) {
		if (StringUtils.isBlank(dto.getMergeCode())) {
			throw new SettlementException("内部错误，参数为空");
		} else {
			// 返回查询结果
			@SuppressWarnings("unchecked")
			List<CODCompositeGridDto> list = this.getSqlSession().selectList(
					NAMESPACE + "queryCODByMergeCode", dto);
			return list;
		}
	}

	/**
	 * 按批次号查询
	 * @author foss-guxinhua
	 * @date 2013-5-7 下午6:23:17
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CODCompositeGridDto> queryCompositeByBatchNo(CODCompositeQueryDto dto) {
		// 判断参数是否为空
		if (dto != null && StringUtils.isNotBlank(dto.getBatchNumber())) {
			
			addDefaultParams(dto);
	
			return this.getSqlSession().selectList(
					NAMESPACE + "queryCompositeByBatchNo", dto);
		} else {
			throw new SettlementException("内部错误，参数为空");
		}
	}
	
	/**
	 * 按业务查询总行数
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-3 上午11:40:12
	 */
	@Override
	public int queryTotalRowsByBizDate(CODCompositeQueryDto dto) {
		// 判断参数是否为空
		if (dto != null && dto.getInceptBizDate() != null
				&& dto.getEndBizDate() != null) {
			addDefaultParams(dto);
			return (Integer) this.getSqlSession().selectOne(
					NAMESPACE + "queryTotalRowsByBizDate", dto);
		} else {
			throw new SettlementException("内部错误，参数为空");
		}
	}

	/**
	 * 
	 * 按业务日期查询记录
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-3 上午11:51:28
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.ICODCompositeQueryDao#queryByBizDate(com.deppon.foss.module.settlement.consumer.api.shared.dto.CODCompositeGridDto,
	 *      int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CODCompositeGridDto> queryByBizDate(CODCompositeQueryDto dto,
			int offset, int limit) {
		// 判断参数是否为空
		if (dto != null && dto.getInceptBizDate() != null
				&& dto.getEndBizDate() != null) {
			
			addDefaultParams(dto);

			// 初始分页器
			RowBounds rowBounds = new RowBounds(offset, limit);
			return this.getSqlSession().selectList(
					NAMESPACE + "queryByBizDate", dto, rowBounds);
		} else {
			throw new SettlementException("内部错误，参数为空");
		}
	}

	/**
	 * 添加默认参数
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-9 下午7:35:45
	 */
	private void addDefaultParams(CODCompositeQueryDto dto) {
		// 只查询有效的代收货款记录
		dto.setActive(FossConstants.ACTIVE);

		// 应付单类型为代收货款应付但
		dto.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
	}

	/**
	 * 按付款时间查询行数
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-3 下午3:29:07
	 */
	@Override
	public int queryTotalRowsByPayDate(CODCompositeQueryDto dto) {
		// 判断参数是否为空
		if (dto != null && dto.getInceptPaymentDate() != null
				&& dto.getEndPaymentDate() != null) {
			addDefaultParams(dto);
			return (Integer) this.getSqlSession().selectOne(
					NAMESPACE + "queryTotalRowsByPayDate", dto);
		} else {
			throw new SettlementException("内部错误，参数为空");
		}
	}

	/**
	 * 按付款时间进行查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-3 下午3:28:37
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.ICODCompositeQueryDao#queryByPayDate(com.deppon.foss.module.settlement.consumer.api.shared.dto.CODCompositeQueryDto,
	 *      int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CODCompositeGridDto> queryByPayDate(CODCompositeQueryDto dto,
			int offset, int limit) {
		// 判断参数是否为空
		if (dto != null && dto.getInceptPaymentDate() != null
				&& dto.getEndPaymentDate() != null) {
			addDefaultParams(dto);
			RowBounds rowBounds = new RowBounds(offset, limit);
			return this.getSqlSession().selectList(
					NAMESPACE + "queryByPayDate", dto, rowBounds);
		} else {
			throw new SettlementException("内部错误，参数为空");
		}
	}
	
	/**
	 * 按汇款成功时间查询行数
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-3 下午3:29:07
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.ICODCompositeQueryDao#queryTotalRowsByPayDate(com.deppon.foss.module.settlement.consumer.api.shared.dto.CODCompositeQueryDto)
	 */
	@Override
	public int queryTotalRowsByRefundSuccessDate(CODCompositeQueryDto dto) {
		// 判断参数是否为空
		if (dto != null && dto.getInceptRefundSuccessDate() != null
				&& dto.getEndRefundSuccessDate() != null) {
			addDefaultParams(dto);
			return (Integer) this.getSqlSession().selectOne(
					NAMESPACE + "queryTotalRowsByRefundSuccessDate", dto);
		} else {
			throw new SettlementException("内部错误，参数为空");
		}
	}

	/**
	 * 按汇款成功时间进行查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-3 下午3:28:37
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.ICODCompositeQueryDao#queryByPayDate(com.deppon.foss.module.settlement.consumer.api.shared.dto.CODCompositeQueryDto,
	 *      int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CODCompositeGridDto> queryByRefundSuccessDate(CODCompositeQueryDto dto,
			int offset, int limit) {
		// 判断参数是否为空
		if (dto != null && dto.getInceptRefundSuccessDate() != null
				&& dto.getEndRefundSuccessDate() != null) {
			addDefaultParams(dto);
			RowBounds rowBounds = new RowBounds(offset, limit);
			return this.getSqlSession().selectList(
					NAMESPACE + "queryByRefundSuccessDate", dto, rowBounds);
		} else {
			throw new SettlementException("内部错误，参数为空");
		}
	}

	/**
	 * 按签收日期查询总行数
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-6 上午11:05:22
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.ICODCompositeQueryDao#queryTotalRowsSignDate(com.deppon.foss.module.settlement.consumer.api.shared.dto.CODCompositeQueryDto)
	 */
	@Override
	public int queryTotalRowsSignDate(CODCompositeQueryDto dto) {
		// 判断参数是否为空
		if (dto != null && dto.getInceptSignDate() != null
				&& dto.getEndSignDate() != null) {
			addDefaultParams(dto);
			return (Integer) this.getSqlSession().selectOne(
					NAMESPACE + "queryTotalRowsBySignDate", dto);
		} else {
			throw new SettlementException("内部错误，参数为空");
		}
	}

	/**
	 * 按签收日期分页查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-6 上午11:01:02
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.ICODCompositeQueryDao#queryBySignDate(com.deppon.foss.module.settlement.consumer.api.shared.dto.CODCompositeQueryDto,
	 *      int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CODCompositeGridDto> queryBySignDate(CODCompositeQueryDto dto,
			int offset, int limit) {
		// 判断参数是否为空
		if (dto != null && dto.getInceptSignDate() != null
				&& dto.getEndSignDate() != null) {
			addDefaultParams(dto);
			RowBounds rowBounds = new RowBounds(offset, limit);
			return this.getSqlSession().selectList(
					NAMESPACE + "queryBySignDate", dto, rowBounds);
		} else {
			throw new SettlementException("内部错误，参数为空");
		}
	}

}
