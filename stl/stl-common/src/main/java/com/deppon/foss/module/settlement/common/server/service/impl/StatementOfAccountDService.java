package com.deppon.foss.module.settlement.common.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.settlement.common.api.server.dao.IStatementOfAccountDEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.IBillAdvancedPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillDepositReceivedService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountDService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountDetailCountDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.util.define.FossConstants;

/**
 * 对账单明细公共Service接口实现类
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-10-25 下午12:33:26
 */
public class StatementOfAccountDService implements IStatementOfAccountDService {

	/**
	 * 注入statementOfAccountDEntityDao
	 */
	private IStatementOfAccountDEntityDao statementOfAccountDEntityDao;
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(StatementOfAccountDService.class);
	/**
	 * 注入应收单公共SERVICE
	 */
	private IBillReceivableService billReceivableService;

	/**
	 * 注入应付单公共SERVICE
	 */
	private IBillPayableService billPayableService;

	/**
	 * 注入预收单公共SERVICE
	 */
	private IBillDepositReceivedService billDepositReceivedService;

	/**
	 * 注入预付单公共SERVICE
	 */
	private IBillAdvancedPaymentService billAdvancedPaymentService;

	/**
	 * 保存对账单明细
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-25 上午11:43:22
	 */
	@Override
	public int add(StatementOfAccountDEntity entity) {
		logger.info("新增对账单明细，enter service ,对账单号："
				+ entity.getStatementBillNo());
		// 校验对账单明细ID不能为空
		if (StringUtil.isEmpty(entity.getId())) {
			throw new SettlementException("对账单明细ID为空！");
		}
		// 校验对账单明细单号不能为空
		if (StringUtil.isEmpty(entity.getStatementBillNo())) {
			throw new SettlementException("对账单明细单号为空！");
		}
		// 校验对账单明细来源单号不能为空
		if (StringUtil.isEmpty(entity.getSourceBillNo())) {
			throw new SettlementException("对账单明细来源为空！");
		}
		// 校验对账单明细单据父类型不能为空
		if (StringUtil.isEmpty(entity.getBillParentType())) {
			throw new SettlementException("对账单明细单据父类型为空！");
		}
		// 校验对账单明细单据类型不能为空
		if (StringUtil.isEmpty(entity.getBillType())) {
			throw new SettlementException("对账单明细单据类型为空！");
		}
		// 校验对账单明细金额不能为空
		if (entity.getAmount() == null) {
			throw new SettlementException("对账单明细金额为空");
		}
		// 校验对账单明细已核销金额不能为空
		if (entity.getVerifyAmount() == null) {
			throw new SettlementException("对账单明细已核销金额为空");
		}
		// 校验对账单明细未核销金额不能为空
		if (entity.getUnverifyAmount() == null) {
			throw new SettlementException("对账单明细未核销金额为空");
		}
		// 校验对账单明细部门编码不能为空
		if (StringUtil.isEmpty(entity.getOrgCode())) {
			throw new SettlementException("对账单明细部门编码为空！");
		}
		// 校验对账单明细客户编码不能为空
		if (StringUtil.isEmpty(entity.getCustomerCode())) {
			throw new SettlementException("对账单明细客户编码为空！");
		}
		// 校验对账单业务日期不能为空
		if (entity.getBusinessDate() == null) {
			throw new SettlementException("对账单账单业务日期为空！");
		}
		// 校验对账单创建时间不能为空
		if (entity.getCreateTime() == null) {
			throw new SettlementException("对账单创建时间为空！");
		}
		// 校验对账单明细记账日期不能为空
		if (entity.getAccountDate() == null) {
			throw new SettlementException("对账单账单记账日期为空！");
		}
		logger.info("新增对账单明细，succesfully exit service ,对账单号："
				+ entity.getStatementBillNo());
		return statementOfAccountDEntityDao.add(entity);
	}

	/**
	 * 根据对账单明细来源单号查询对账单明细信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-26 上午10:56:09
	 */
	@Override
	public List<StatementOfAccountDEntity> queryByResourceNos(List<String> list) {
		if (CollectionUtils.isEmpty(list)) {
			throw new SettlementException("执行按对账单明细来源单号查询对账单明细信息,来源单号集合为空！", "");
		}
		return statementOfAccountDEntityDao.queryByResourceNos(list,
				FossConstants.NO);
	}

	/**
	 * 删除对账单明细时修改明细信息（逻辑删除）
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-3 下午4:37:56
	 */
	@Override
	public void updateStatementDetailForDeleteDetail(
			StatementOfAccountDEntity entity) {
		if (entity == null) {
			throw new SettlementException("执行删除对账单明细操作时，对账单明细实体为空！", "");
		}
		if (StringUtil.isEmpty(entity.getId())) {
			throw new SettlementException("执行删除对账单明细时，对账单明细ID为空！", "");
		}
		if (entity.getCreateTime() == null) {
			throw new SettlementException("执行删除对账单明细时，对账单明细创建日期为空！", "");
		}
		if (StringUtil.isEmpty(entity.getStatementBillNo())) {
			throw new SettlementException("执行删除对账单明细时，对账单明细对账单号为空！", "");
		}
		int row = statementOfAccountDEntityDao
				.updateStatementDetailByDeleteFlag(entity);
		if (row != 1) {
			throw new SettlementException("数据库更新信息发生错误，更新条数不等于1！");
		}
	}

	/**
	 * 根据对账单号查询对账单明细信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-6 下午1:02:48
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountDService#queryByStatementBillNo(java.lang.String)
	 */
	@Override
	public List<StatementOfAccountDEntity> queryByStatementBillNo(
			String statementBillNo) {
		if (StringUtil.isEmpty(statementBillNo)) {
			throw new SettlementException("执行按对账单号查询对账单明细操作时，对账单号为空！", "");
		}
		return (List<StatementOfAccountDEntity>) statementOfAccountDEntityDao
				.queryByStatementBillNo(statementBillNo);
	}

	/**
	 * 根据对账单号查询对账单明细信息(分页)
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-6 下午1:02:48
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountDService#queryByStatementBillNo(java.lang.String)
	 */
	@Override
	public List<StatementOfAccountDEntity> queryByStatementBillNo(
			String statementBillNo, int pageNo, int pageSize) {
		if (StringUtil.isEmpty(statementBillNo)) {
			throw new SettlementException("执行按对账单号查询对账单明细操作时，对账单号为空！", "");
		}
		return statementOfAccountDEntityDao.queryByStatementBillNo(
				statementBillNo, pageNo, pageSize);
	}

	/**
	 * 根据对账单号查询对账单明细信息（求总条数）
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-6 下午1:02:48
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountDService#queryByStatementBillNo(java.lang.String)
	 */
	@Override
	public StatementOfAccountDetailCountDto queryCountByStatementBillNo(
			String statementBillNo) {
		if (StringUtil.isEmpty(statementBillNo)) {
			throw new SettlementException("执行按对账单号查询对账单明细操作时，对账单号为空！", "");
		}
		return (StatementOfAccountDetailCountDto) statementOfAccountDEntityDao
				.queryCountByStatementBillNo(statementBillNo);
	}

	/**
	 * 由于对账单明细金额发生变法修改对账单明细信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-7 上午11:30:33
	 */
	@Override
	public void updateStatementDetailByAmount(StatementOfAccountDEntity entity) {
		if (entity == null) {
			throw new SettlementException("执行修改对账单金额信息时，对账单明细实体为空！", "");
		}
		if (StringUtil.isEmpty(entity.getId())) {
			throw new SettlementException("执行修改对账单信息时，对账单明细ID为空！", "");
		}
		if (entity.getCreateTime() == null) {
			throw new SettlementException("执行修改对账单信息时，对账单明细创建日期为空！", "");
		}
		if (StringUtil.isEmpty(entity.getStatementBillNo())) {
			throw new SettlementException("执行修改对账单信息时，对账单明细对账单号为空！", "");
		}
		int row = statementOfAccountDEntityDao
				.updateStatementDetailByAmount(entity);
		if (row != 1) {
			throw new SettlementException("数据库更新信息发生错误，更新条数不等于1！");
		}
	}

	/**
	 * 根据对账单号、来源单号、及删除标记查询对账单明细单据
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-7 下午3:43:27
	 */
	@Override
	public StatementOfAccountDEntity queryByResourceAndStatementNo(
			String sourceBillNo, String statementBillNo, String isDelete) {
		if (StringUtil.isEmpty(sourceBillNo)) {
			throw new SettlementException("查询对账单明细数据时，来源单号为空！");
		}
		if (StringUtil.isEmpty(statementBillNo)) {
			throw new SettlementException("查询对账单明细数据时，对账单号为空！");
		}
		if (StringUtil.isEmpty(isDelete)) {
			throw new SettlementException("查询对账单明细数据时，删除标记为空！");
		}
		return statementOfAccountDEntityDao.queryByResourceAndStatementNo(
				sourceBillNo, statementBillNo, isDelete);
	}

	/**
	 * 根据运单号集合查询对账单明细
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-9 下午4:32:47
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountDService#queryByWaybillNos(java.util.List)
	 */
	@Override
	public List<StatementOfAccountDEntity> queryByWaybillNos(
			List<String> waybillNos) {
		if (CollectionUtils.isEmpty(waybillNos)) {
			throw new SettlementException("执行按运单号集合查询对账单明细时，运单号集合为空！");
		}
		return statementOfAccountDEntityDao.queryByWaybillNos(waybillNos,
				FossConstants.NO);
	}
	
	/**
	 * 根据原始来源单号集合查询对账单明细
	 * @author foss-wangxuemin
	 * @date Apr 19, 2013 11:18:29 AM
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountDService#queryByOrigSourceNos(java.util.List)
	 */
	@Override
	public List<StatementOfAccountDEntity> queryByOrigSourceBillNos(List<String> origSourceBillNos){
		if (CollectionUtils.isEmpty(origSourceBillNos)) {
			throw new SettlementException("执行按原始来源单号集合查询对账单明细时，原始来源单号集合为空！");
		}
		return statementOfAccountDEntityDao.queryByOrigSourceNos(origSourceBillNos,
				FossConstants.NO);
	}


	/**
	 * 根据明细单号查询应收单、应付单、预收单、预付单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-1-18 下午2:41:49
	 */
	@Override
	public Map<Object, Object> queryBySourceBillNos(List<String> sourceBillNos) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		// 存放各种明细单据集合(应收、应付、预收、预付)
		List<BillReceivableEntity> receivableEntityList = new ArrayList<BillReceivableEntity>();
		List<BillPayableEntity> payableEntityList = new ArrayList<BillPayableEntity>();
		List<BillDepositReceivedEntity> depositReceivedEntityList = new ArrayList<BillDepositReceivedEntity>();
		List<BillAdvancedPaymentEntity> advancedPaymentEntityList = new ArrayList<BillAdvancedPaymentEntity>();
		if (CollectionUtils.isNotEmpty(sourceBillNos)) {
			for (int i = 0; i < sourceBillNos.size(); i++) {
				if (SettlementUtil.isReceiveable(sourceBillNos.get(i))) {
					// 获取应收单信息
					BillReceivableEntity entity = billReceivableService
							.queryByReceivableNO(sourceBillNos.get(i),
									FossConstants.YES);
					if (entity == null) {
						throw new SettlementException("数据发生异常！"
								+ sourceBillNos.get(i) + "该应收单已处于无效状态", "");
					}
					receivableEntityList.add(entity);
				} else if (SettlementUtil.isPayable(sourceBillNos.get(i))) {
					// 获取应付单信息
					BillPayableEntity entity = billPayableService
							.queryByPayableNO(sourceBillNos.get(i),
									FossConstants.YES);
					payableEntityList.add(entity);
					if (entity == null) {
						throw new SettlementException("数据发生异常！"
								+ sourceBillNos.get(i) + "该应付单已处于无效状态", "");
					}

				} else if (SettlementUtil.isDepositReceived(sourceBillNos
						.get(i))) {
					// 获取预收单信息
					BillDepositReceivedEntity entity = billDepositReceivedService
							.queryByDepositReceivedNo(sourceBillNos.get(i),
									FossConstants.YES);
					depositReceivedEntityList.add(entity);
					if (entity == null) {
						throw new SettlementException("数据发生异常！"
								+ sourceBillNos.get(i) + "该预收单已处于无效状态", "");
					}

				} else if (SettlementUtil.isAdvancedPayment(sourceBillNos
						.get(i))) {
					// 获取预付单信息
					BillAdvancedPaymentEntity entity = billAdvancedPaymentService
							.queryBillAdvancedPaymentNo(sourceBillNos.get(i),
									FossConstants.YES);
					advancedPaymentEntityList.add(entity);
					if (entity == null) {
						throw new SettlementException("数据发生异常！"
								+ sourceBillNos.get(i) + "该预付单已处于无效状态", "");
					}
				}
			}
		}
		map.put("receivableEntityList", receivableEntityList);
		map.put("payableEntityList", payableEntityList);
		map.put("depositReceivedEntityList", depositReceivedEntityList);
		map.put("advancedPaymentEntityList", advancedPaymentEntityList);
		return map;
	}

	/**
	 * 查询单号List中是否至少一个单存在于<b>代打木架对账单</b>中
	 * 单号需小于1000个
	 * @author 105762-Yang Shushuo
	 * @date 2014-07-16
	 */
	@Override
	public boolean queryIfAtLeastOneBillExistsInStatement(List<String> billNoList) {
		SettlementUtil.valideIsNull(billNoList, "单号列表为空");
		if(billNoList.size() > SettlementConstants.MAX_LIST_SIZE){
			throw new SettlementException("单号不能超过1000个");
		}
		return statementOfAccountDEntityDao.queryIfAtLeastOneBillExistsInStatement(billNoList) > 0 ? true : false;
	}	
	/**
	 * @return  the statementOfAccountDEntityDao
	 */
	public IStatementOfAccountDEntityDao getStatementOfAccountDEntityDao() {
		return statementOfAccountDEntityDao;
	}

	
	/**
	 * @param statementOfAccountDEntityDao the statementOfAccountDEntityDao to set
	 */
	public void setStatementOfAccountDEntityDao(
			IStatementOfAccountDEntityDao statementOfAccountDEntityDao) {
		this.statementOfAccountDEntityDao = statementOfAccountDEntityDao;
	}

	
	/**
	 * @return  the billReceivableService
	 */
	public IBillReceivableService getBillReceivableService() {
		return billReceivableService;
	}

	
	/**
	 * @param billReceivableService the billReceivableService to set
	 */
	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	
	/**
	 * @return  the billPayableService
	 */
	public IBillPayableService getBillPayableService() {
		return billPayableService;
	}

	
	/**
	 * @param billPayableService the billPayableService to set
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	
	/**
	 * @return  the billDepositReceivedService
	 */
	public IBillDepositReceivedService getBillDepositReceivedService() {
		return billDepositReceivedService;
	}

	
	/**
	 * @param billDepositReceivedService the billDepositReceivedService to set
	 */
	public void setBillDepositReceivedService(
			IBillDepositReceivedService billDepositReceivedService) {
		this.billDepositReceivedService = billDepositReceivedService;
	}

	
	/**
	 * @return  the billAdvancedPaymentService
	 */
	public IBillAdvancedPaymentService getBillAdvancedPaymentService() {
		return billAdvancedPaymentService;
	}

	
	/**
	 * @param billAdvancedPaymentService the billAdvancedPaymentService to set
	 */
	public void setBillAdvancedPaymentService(
			IBillAdvancedPaymentService billAdvancedPaymentService) {
		this.billAdvancedPaymentService = billAdvancedPaymentService;
	}


}
