package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ICODEntityDao;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.AirBillPaidCODQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODPayConfirmDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODPayFailedQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODPayableQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODStartApplyQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODWaybillNoQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODMergeDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODPayableDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODQueryByWaybillDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.LandBillPaidCODQueryDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 代收货款实体数据库层
 * 
 * @author dp-zengbinwen
 * @date 2012-10-13 下午2:43:21
 */
public class CODEntityDao extends iBatis3DaoImpl implements ICODEntityDao {

	public static final String NAMESPACE = "foss.stl.CodEntityDao.";

	/**
	 * 新增代收货款实体
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-13 下午2:44:31
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.ICODEntityDao#addCod(com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity)
	 */
	@Override
	public int addCod(CODEntity entity) {
		if (entity != null) {
			return getSqlSession().insert(NAMESPACE + "addCod", entity);
		} else {
			throw new SettlementException("内部错误，参数Entity为空!");
		}
	}

	/**
	 * @author 218392 zhangyongxue
	 * @date 2016-07-08 09:21:00
	 * 查询已签收但长期未支付代收货款-短期冻结
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryShortNonRefundCodLock(String billType) {
		logger.info("process 在CodEntityDao queryShortNonRefundCod长期未退款代收货款冻结job开始!");
		return getSqlSession().selectList(NAMESPACE + "queryShortNonRefundCodLock", billType);
	}
	
	/**
	 * @author 218392 zhangyongxue
	 * @date 2016-07-08 09:21:00
	 * 查询已签收但长期未支付代收货款-长期冻结
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryLongNonRefundCodLock(String billType) {
		logger.info("process 在CodEntityDao queryLongNonRefundCodLock长期未退款代收货款冻结job开始!");
		return getSqlSession().selectList(NAMESPACE + "queryLongNonRefundCodLock", billType);
	}
	
	/**
	 * 更新银行帐号信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-26 下午3:29:07
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.ICODEntityDao#updateBankAccounts(com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity)
	 */
	@Override
	public int updateBankAccounts(CODEntity entity) {
		if (entity != null) {
			return this.getSqlSession().update(
					NAMESPACE + "updateBankAccounts", entity);
		} else {
			throw new SettlementException("内部错误，参数为空！");
		}
	}

	/**
	 * 资金部冻结
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-26 下午4:00:35
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.ICODEntityDao#updateFreezeState(com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity)
	 */
	@Override
	public int updateFreezeState(CODEntity entity) {
		if (entity != null) {
			return this.getSqlSession().update(NAMESPACE + "updateFreezeState",
					entity);
		} else {
			throw new SettlementException("内部错误，参数为空！");
		}
	}

	/**
	 * 更新付款状态
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-26 下午4:02:14
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.ICODEntityDao#updatePaymentState(com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity)
	 */
	@Override
	public int updatePaymentState(CODEntity entity) {
		if (entity != null) {
			return this.getSqlSession().update(
					NAMESPACE + "updatePaymentState", entity);
		} else {
			throw new SettlementException("内部错误，参数为空！");
		}
	}

	/**
	 * @author 218392 ZHANGYONGXUE 2015-12-25 16:12:50
	 * 代收货款支付界面：按单号查询总数
	 */
	@Override
	public CODDto queryByWaybillNoListSum(BillCODWaybillNoQueryDto queryDto){
		if (queryDto != null) {
			return (CODDto) getSqlSession().selectOne(
					NAMESPACE + "queryBillCODListByWaybillNoSum", queryDto);
		} else {
			throw new SettlementException("内部错误，queryDto为空");
		}
	}	
	
	
	/**
	 * 根据运单号查询代收货款总条件总金额，冻结总条件总金额
	 * 
	 * @author foss-guxinhua
	 * @date 2013-5-6 下午3:41:07
	 * @param queryDto
	 * @return
	 */
	@Override
	public CODDto queryByWaybillNoSum(BillCODWaybillNoQueryDto queryDto){
		if (queryDto != null) {
			return (CODDto) getSqlSession().selectOne(
					NAMESPACE + "queryBillCODByWaybillNoSum", queryDto);
		} else {
			throw new SettlementException("内部错误，queryDto为空");
		}
	}
	
	/**
	 * @author 218392 张永雪 2015-12-25 15:25:26
	 * 代收货款支付界面：按单号查询
	 */
	@SuppressWarnings("unchecked")
	public List<CODDto> queryCodListByWaybillNo(BillCODWaybillNoQueryDto queryDto) {
		if (queryDto != null) {
			return getSqlSession().selectList(
					NAMESPACE + "queryBillCODListByWaybillNo", queryDto);
		} else {
			throw new SettlementException("内部错误，queryDto为空");
		}
	}
	
	
	/**
	 * 
	 * 根据运单号查询代收货款信息，代收货款状态、应付部门可选
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-30 上午8:43:02
	 */
	@SuppressWarnings("unchecked")
	public List<CODDto> queryByWaybillNo(BillCODWaybillNoQueryDto queryDto) {
		if (queryDto != null) {
			return getSqlSession().selectList(
					NAMESPACE + "queryBillCODByWaybillNo", queryDto);
		} else {
			throw new SettlementException("内部错误，queryDto为空");
		}
	}

	/**
	 * 
	 * 出发申请查询
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-30 上午9:19:04
	 */
	@SuppressWarnings("unchecked")
	public List<CODDto> queryStartApplyBillCOD(
			BillCODStartApplyQueryDto queryDto) {
		if (queryDto != null) {
			RowBounds rb = new RowBounds(queryDto.getOffset(),
					queryDto.getLimit());

			return getSqlSession().selectList(
					NAMESPACE + "queryStartApplyBillCOD", queryDto, rb);
		} else {
			throw new SettlementException("内部错误，queryDto为空");
		}
	}

	/**
	 * 
	 * 出发申请查询大小
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-30 上午9:20:35
	 */
	public int queryStartApplyBillCODSize(BillCODStartApplyQueryDto queryDto) {
		if (queryDto != null) {
			return (Integer) getSqlSession().selectOne(
					NAMESPACE + "queryStartApplyBillCODSize", queryDto);
		} else {
			throw new SettlementException("内部错误，queryDto为空");
		}
	}

	/**
	 * 
	 * 代收货款付款失败查询
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-30 上午9:56:17
	 */
	@SuppressWarnings("unchecked")
	public List<CODDto> queryBillCODPayFailed(BillCODPayFailedQueryDto queryDto) {
		if (queryDto != null) {
			RowBounds rb = new RowBounds(queryDto.getOffset(),
					queryDto.getLimit());

			return getSqlSession().selectList(
					NAMESPACE + "queryBillCODPayFailed", queryDto, rb);
		} else {
			throw new SettlementException("内部错误，queryDto为空");
		}
	}

	/**
	 * 
	 * 代收货款付款失败查询大小，合计金额
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-30 上午9:57:26
	 */
	public CODDto queryBillCODPayFailedSize(BillCODPayFailedQueryDto queryDto) {
		if (queryDto != null) {
			return (CODDto) getSqlSession().selectOne(
					NAMESPACE + "queryBillCODPayFailedSize", queryDto);
		} else {
			throw new SettlementException("内部错误，queryDto为空");
		}
	}

	/**
	 * 
	 * 代收货款汇款查询,合计总条数总金额，冻结总条数总金额
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-30 上午11:08:18
	 */
	public CODDto queryBillCODPayableSum(BillCODPayableQueryDto queryDto){
		if (queryDto != null) {
			return (CODDto) getSqlSession().selectOne(
					NAMESPACE + "queryBillCODPayableSum", queryDto);
		} else {
			throw new SettlementException("内部错误，queryDto为空");
		}
	}
	
	/**
	 * 查询代收货款及应付单
	 * @author foss-guxinhua
	 * @date 2013-5-8 上午11:05:11
	 * @param queryDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CODPayableDto> queryAllCODAndBillPayable(BillCODPayableQueryDto queryDto) {
		if (queryDto != null) {
			return getSqlSession().selectList(
					NAMESPACE + "queryAllCODAndBillPayable", queryDto);
		} else {
			throw new SettlementException("内部错误，queryDto为空");
		}
	}
	
	/**
	 * 查询可退款的并可合并付款的代收货款及应付单明细
	 * @author foss-guxinhua
	 * @date 2014-10-14 上午11:05:11
	 * @param queryDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CODMergeDto> queryAllMergeCODAndBillPayable(BillCODPayableQueryDto queryDto) {
		if (queryDto != null) {
			return getSqlSession().selectList(
					NAMESPACE + "queryAllMergeCODAndBillPayable", queryDto);
		} else {
			throw new SettlementException("内部错误，queryDto为空");
		}
	}
	
	/**
	 * 
	 * 代收货款汇款查询
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-30 上午11:08:18
	 */
	@SuppressWarnings("unchecked")
	public List<CODDto> queryBillCODPayable(BillCODPayableQueryDto queryDto) {
		if (queryDto != null) {
			RowBounds rb = new RowBounds(queryDto.getOffset(), queryDto.getLimit());
			return getSqlSession().selectList(
					NAMESPACE + "queryBillCODPayable", queryDto,rb);
		} else {
			throw new SettlementException("内部错误，queryDto为空");
		}
	}

	/**
	 * 
	 * 查询代收货款汇款确认
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-6 上午9:41:12
	 */
	@SuppressWarnings("unchecked")
	public List<CODDto> queryPayConfirm(BillCODPayConfirmDto dto, int start,
			int offset) {
		if (dto != null) {
			RowBounds rb = new RowBounds(start, offset);
			return getSqlSession().selectList(NAMESPACE + "queryPayConfirm",
					dto, rb);
		} else {
			throw new SettlementException("内部错误，queryDto为空");
		}

	}

	/**
	 * 
	 * 查询代收货款汇款确认大小,合计金额
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-6 上午9:41:40
	 */
	public CODDto queryPayConfirmSize(BillCODPayConfirmDto dto) {
		if (dto != null) {
			return (CODDto) getSqlSession().selectOne(
					NAMESPACE + "queryPayConfirmSize", dto);
		} else {
			throw new SettlementException("内部错误，queryDto为空");
		}

	}

	/**
	 * 
	 * 更新代收货款作废状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 上午10:59:37
	 */
	public int updateCODCancelStatus(CODEntity entity) {
		if (entity != null) {
			return getSqlSession().update(NAMESPACE + "updateCODCancelStatus",
					entity);
		} else {
			throw new SettlementException("内部错误，entity为空");
		}
	}

	/**
	 * 
	 * 更新代收货款营业部冻结状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 上午11:00:57
	 */
	public int updateOrgFreezeStatus(CODEntity entity) {
		if (entity != null) {
			return getSqlSession().update(NAMESPACE + "updateOrgFreezeStatus",
					entity);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}

	}

	/**
	 * 
	 * 更新代收货款营业部审核状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 上午11:01:45
	 */
	public int updateOrgAuditStatus(CODEntity entity) {
		if (entity != null) {
			return getSqlSession().update(NAMESPACE + "updateOrgAuditStatus",
					entity);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 
	 * 更新代收货款营业部经理审核状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 上午11:02:27
	 */
	public int updateManagerAuditStatus(CODEntity entity) {
		if (entity != null) {
			return getSqlSession().update(
					NAMESPACE + "updateManagerAuditStatus", entity);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 
	 * 更新代收货款合并编号状态
	 * 
	 * @author 163576
	 * @date 2014-11-16 上午11:03:19
	 */
	public int updateCodMergeCode(CODMergeDto mergeDto) {
		if (mergeDto != null) {
			return getSqlSession().update(
					NAMESPACE + "updateCodMergeCode", mergeDto);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}
	
	/**
	 * 
	 * 更新代收货款资金部冻结状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 上午11:03:19
	 */
	public int updateTusyorgFreezeStatus(CODEntity entity) {
		if (entity != null) {
			return getSqlSession().update(
					NAMESPACE + "updateTusyorgFreezeStatus", entity);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}
	
	/**
	 * 批量更新代收货款资金部冻结状态
	 * 
	 * @author foss-guxinhua
	 * @date 2013-5-3 下午3:46:13
	 * @param cod
	 * @return
	 */
	public int updateTusyorgFreezeStatusByBatch(CODDto cod) {
		if (cod != null && CollectionUtils.isNotEmpty(cod.getCodList())) {
			
			return getSqlSession().update(
					NAMESPACE + "updateTusyorgFreezeStatusByBatch", cod);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 
	 * 更新代收货款资金部反冻结状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 下午1:43:49
	 */
	public int updateTusyorgClfreezeStatus(CODEntity entity) {
		if (entity != null) {
			return getSqlSession().update(
					NAMESPACE + "updateTusyorgClfreezeStatus", entity);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}

	}
	
	/**
	 * 
	 * 批量更新代收货款资金部反冻结状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 下午1:43:49
	 */
	public int updateTusyorgClfreezeStatusByBatch(CODDto cod) {
		if (cod != null && CollectionUtils.isNotEmpty(cod.getCodList())) {
			return getSqlSession().update(
					NAMESPACE + "updateTusyorgClfreezeStatusByBatch", cod);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}

	}

	/**
	 * 
	 * 更新代收货款付款失败申请审核状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 下午1:44:55
	 */
	public int updatePaymentFailedAuditStatus(CODEntity entity) {
		if (entity != null) {
			return getSqlSession().update(
					NAMESPACE + "updatePaymentFailedAuditStatus", entity);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 
	 * 批量更新代收货款线下汇款导出状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 下午1:48:30
	 */
	public int updatePayCODOfflineStatusByBatch(CODDto cod) {
		if (cod != null && CollectionUtils.isNotEmpty(cod.getCodList())) {
			return getSqlSession().update(
					NAMESPACE + "updatePayCODOfflineStatusByBatch", cod);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}
	
	/**
	 * 
	 * 更新代收货款线下汇款导出状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 下午1:48:30
	 */
	public int updatePayCODOfflineStatus(CODEntity entity) {
		if (entity != null) {
			return getSqlSession().update(
					NAMESPACE + "updatePayCODOfflineStatus", entity);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 
	 * 批量更新代收货款线上汇款状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 下午1:49:25
	 */
	public int updatePayCODOnlineStatusByBatch(CODDto cod) {
		if (cod != null && CollectionUtils.isNotEmpty(cod.getCodList())) {
			return getSqlSession().update(
					NAMESPACE + "updatePayCODOnlineStatusByBatch", cod);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}
	
	/**
	 * 
	 * 更新代收货款线上汇款状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-5 下午1:49:25
	 */
	public int updatePayCODOnlineStatus(CODEntity entity) {
		if (entity != null) {
			return getSqlSession().update(
					NAMESPACE + "updatePayCODOnlineStatus", entity);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 
	 * 更新代收货款汇款成功状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-7 上午9:48:54
	 */
	public int updatePayCODSuccess(CODEntity entity) {
		if (entity != null) {
			return getSqlSession().update(NAMESPACE + "updatePayCODSuccess",
					entity);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}
	
	/**
	 * 批量更新代收货款汇款成功状态
	 * 
	 * @author foss-guxinhua
	 * @date 2014-9-27 下午3:46:13
	 * @param cod
	 * @return
	 */
	public int updatePayCODSuccessByBatch(CODDto cod) {
		if (cod != null && CollectionUtils.isNotEmpty(cod.getCodEntityList())) {
			
			return getSqlSession().update(
					NAMESPACE + "updatePayCODSuccessByBatch", cod);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 
	 * 更新代收货款汇款失败状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-7 上午9:49:39
	 */
	public int updatePayCODFailure(CODEntity entity) {
		if (entity != null) {
			return getSqlSession().update(NAMESPACE + "updatePayCODFailure",
					entity);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 
	 * 更新代收货款反汇款成功状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-7 上午9:51:08
	 */
	public int updatePayCODAntiRemittance(CODEntity entity) {
		if (entity != null) {
			return getSqlSession().update(
					NAMESPACE + "updatePayCODAntiRemittance", entity);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 空运代收货款记录状态
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-7 下午5:39:03
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.IAirBillPaidCODQueryDao#updateAirPaidCodStatus(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int updateAirPaidCodStatus(CODEntity codEntity, String newAirStatus,
			String oldAirStatus) {
		if (codEntity != null && StringUtils.isNotEmpty(oldAirStatus)
				&& StringUtils.isNotEmpty(newAirStatus)) {
			// 获得默认查询条件
			AirBillPaidCODQueryDto dto = new AirBillPaidCODQueryDto();
			// 默认值
			// 未核销的应付单
			dto.setVerifyAmount(BigDecimal.ZERO);
			// 状态为有效
			dto.setActive(FossConstants.ACTIVE);
			// 设置参数
			dto.setId(codEntity.getId());
			dto.setNewAirStatus(newAirStatus);
			dto.setOldAirStatus(oldAirStatus);
			
			// 用户信息
			dto.setUserCode(codEntity.getModifyUserCode());
			dto.setUserName(codEntity.getModifyUserName());
			
			// 审核状态
			dto.setStatus(codEntity.getStatus());

			// 审核时间
			dto.setAirAuditDate(codEntity.getAirOrgAuditTime());
			
			//空运审核人编码
			dto.setAirOrgAuditUserCode(codEntity.getAirOrgAuditUserCode());
			
			//空运审核人名称
			dto.setAirOrgAuditUserName(codEntity.getAirOrgAuditUserName());
			
			// 返回更新结果
			return (Integer) this.getSqlSession().update(
					NAMESPACE + "updateAirPaidCodStatus", dto);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}

	}
	
	/**
	 * 快递代理代收货款记录状态
	 * ISSUE-3389 小件业务 		
	 * @author foss-guxinhua
	 * @date 2013-07-22 下午5:39:03
	 */
	@Override
	public int updateLandPaidCodStatus(CODEntity codEntity, String newLandStatus,
			String oldLandStatus) {
		if (codEntity != null && StringUtils.isNotEmpty(oldLandStatus)
				&& StringUtils.isNotEmpty(newLandStatus)) {
			// 获得默认查询条件
			LandBillPaidCODQueryDto dto = new LandBillPaidCODQueryDto();
			// 默认值
			// 未核销的应付单
			dto.setVerifyAmount(BigDecimal.ZERO);
			// 状态为有效
			dto.setActive(FossConstants.ACTIVE);
			// 设置参数
			dto.setId(codEntity.getId());
			dto.setNewLandStatus(newLandStatus);
			dto.setOldLandStatus(oldLandStatus);
			
			// 用户信息
			dto.setUserCode(codEntity.getModifyUserCode());
			dto.setUserName(codEntity.getModifyUserName());
			
			// 审核状态
			dto.setStatus(codEntity.getStatus());

			// 审核时间
			dto.setLandAuditDate(codEntity.getLandOrgAuditTime());
			
			//空运审核人编码
			dto.setLandOrgAuditUserCode(codEntity.getLandOrgAuditUserCode());
			
			//空运审核人名称
			dto.setLandOrgAuditUserName(codEntity.getLandOrgAuditUserName());
			
			// 返回更新结果
			return (Integer) this.getSqlSession().update(
					NAMESPACE + "updateLandPaidCodStatus", dto);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}

	}

	/**
	 * 根据主键查询代收货款实体
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-13 下午2:49:20
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.ICODEntityDao#queryById(java.lang.String)
	 */
	@Override
	public CODEntity queryById(String id) {
		if (StringUtils.isNotEmpty(id)) {
			return (CODEntity) getSqlSession().selectOne(
					NAMESPACE + "queryById", id);
		} else {
			throw new SettlementException("内部错误，参数Id为空!");
		}
	}

	/**
	 * 按Id集合进行查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-16 上午10:04:12
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.ICODEntityDao#queryByIds(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CODEntity> queryByIds(List<String> ids) {
		if (!CollectionUtils.isEmpty(ids)) {
			return getSqlSession().selectList(NAMESPACE + "queryByIds", ids);
		} else {
			throw new SettlementException("内部错误，参数Ids个数为空!");
		}
	}
	
	/**
	 * 按合并编号进行查询
	 * 
	 * @date 2014-11-16 上午10:04:12
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CODEntity> queryListByMergeCode(CODEntity cod) {
		if (cod!=null && StringUtils.isNotBlank(cod.getMergeCode())) {
			return getSqlSession().selectList(NAMESPACE + "queryListByMergeCode", cod);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}
	
	/**
	 * 按Id集合进行查询
	 * 
	 * @date 2014-11-16 上午10:04:12
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CODMergeDto> queryMergeCodByIds(List<String> ids) {
		if (!CollectionUtils.isEmpty(ids)) {
			return getSqlSession().selectList(NAMESPACE + "queryMergeCodByIds", ids);
		} else {
			throw new SettlementException("内部错误，参数Ids个数为空!");
		}
	}

	/**
	 * 按运单号进行查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-26 下午5:38:02
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.ICODEntityDao#queryByWaybill(java.lang.String)
	 */
	@Override
	public CODEntity queryByWaybill(CODQueryByWaybillDto dto) {
		if (dto != null) {
			return (CODEntity) getSqlSession().selectOne(
					NAMESPACE + "queryByWaybill", dto);
		} else {
			throw new SettlementException("内部错误，参数查询条件DTO为空!");
		}
	}

	/**
	 * 
	 * 根据批次号查询代收货款
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-23 上午9:50:01
	 */
	@SuppressWarnings("unchecked")
	public List<CODEntity> queryByBatchNumber(String batchNumber) {
		if (StringUtils.isNotEmpty(batchNumber)) {
			List<CODEntity> codList = getSqlSession().selectList(
					NAMESPACE + "queryByBatchNumber", batchNumber);
			return codList;
		} else {
			throw new SettlementException("内部错误，参数waybillNumber为空!");
		}
	}

	/**
	 * 
	 * 更新银企整批通回代收货款状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-26 上午9:53:30
	 */
	public int updateCODBankBatchBackStatus(CODEntity entity) {
		if (entity != null) {
			return getSqlSession().update(
					NAMESPACE + "updateCODBankBatchBackStatus", entity);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 
	 * 按运单号集合查询（官网代收货款查询）
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-5 下午1:58:19
	 */
	@SuppressWarnings("unchecked")
	public List<CODDto> queryCODByWaybillNums(BillCODWaybillNoQueryDto dto,
			int offset, int limit) {

		RowBounds rowBounds = new RowBounds(offset, limit);

		return getSqlSession().selectList(NAMESPACE + "queryCODByWaybillNums",
				dto, rowBounds);
	}
	
	/**
	 * 
	 * 按运单号集合查询（CC代收货款查询）
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-5 下午1:58:19
	 */
	@SuppressWarnings("unchecked")
	public List<CODDto> queryCODByWaybillNumsForCC(BillCODWaybillNoQueryDto dto,
			int offset, int limit) {

		RowBounds rowBounds = new RowBounds(offset, limit);

		return getSqlSession().selectList(NAMESPACE + "queryCODByWaybillNumsForCC",
				dto, rowBounds);
	}

	/**
	 * 
	 * 按运单号集合查询大小（官网代收货款查询）
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-5 下午1:58:22
	 */
	public int queryCODByWaybillNumsSize(BillCODWaybillNoQueryDto dto) {

		int size = (Integer) getSqlSession().selectOne(
				NAMESPACE + "queryCODByWaybillNumsSize", dto);
		return size;
	}
	
	/**
	 * 根据应付单条件及代收货款批次号查询应付单集合
	 * @author foss-guxinhua
	 * @date 2013-5-29 上午10:06:14
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BillPayableEntity> queryBillPayableByCodBatchNumber(CODPayableDto dto){
		if (dto != null && StringUtil.isNotBlank(dto.getBatchNumber())) {
			return getSqlSession().selectList(NAMESPACE + "queryBillPayableByCodBatchNumber",dto);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}


}
