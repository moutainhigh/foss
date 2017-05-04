package com.deppon.foss.module.settlement.common.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.dao.IStatementOfAccountEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.IOperatingLogService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountDService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.OperatingLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountQueryResultDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountUpdateDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 对账单公共service接口实现类
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-10-25 下午12:34:59
 */
public class StatementOfAccountService implements IStatementOfAccountService {

	/**
	 * 注入statementOfAccountEntityDao
	 */
	private IStatementOfAccountEntityDao statementOfAccountEntityDao;

	/**
	 * 注入IStatementOfAccountDService
	 */
	private IStatementOfAccountDService statementOfAccountDService;
	
	/**
	 * 注入IBillWriteoffService
	 */
	private IBillWriteoffService billWriteoffService;

	/**
	 * 注入日志service
	 */
	private IOperatingLogService operatingLogService;

	private static final Logger logger = LogManager
			.getLogger(StatementOfAccountService.class);

	/**
	 * 保存对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-25 上午11:41:48
	 */
	@Override
	public int add(StatementOfAccountEntity entity) {
		logger.info("新增对账单，enter service ,对账单号：" + entity.getStatementBillNo());
		//校验对账单ID不能为空
		if(StringUtil.isEmpty(entity.getId())){
			throw new SettlementException("对账单ID为空！");
		}
		//校验对账单号不能为空
		if(StringUtil.isEmpty(entity.getStatementBillNo())){
			throw new SettlementException("对账单号为空！");
		}
		//校验创建部门编码不能为空
		if(StringUtil.isEmpty(entity.getCreateOrgCode())){
			throw new SettlementException("对账单创建部门编码为空！");
		}
		//校验创建部门名称不能为空
		if(StringUtil.isEmpty(entity.getCreateOrgName())){
			throw new SettlementException("对账单创建部门名称为空！");
		}
		//校验标杆编码不能为空
		if(StringUtil.isEmpty(entity.getUnifiedCode())){
			throw new SettlementException("对账单标杆编码为空！");
		}
		//校验客户编码不能为空
		if(StringUtil.isEmpty(entity.getCustomerCode())){
			throw new SettlementException("对账单客户编码为空！");
		}
		//校验对账单类型不能为空
		if(StringUtil.isEmpty(entity.getBillType())){
			throw new SettlementException("对账单类型为空！");
		}
		//校验对账单期初发生金额不能为空
		if(entity.getBeginPeriodAmount()==null){
			throw new SettlementException("对账单期初发生金额为空！");
		}
		//校验对账单期初应收发生金额不能为空
		if(entity.getBeginPeriodRecAmount()==null){
			throw new SettlementException("对账单期初应收发生金额为空！");
		}
		//校验对账单期初应付发生金额不能为空
		if(entity.getBeginPeriodPayAmount()==null){
			throw new SettlementException("对账单期初应付发生金额为空！");
		}
		//校验对账单期初预收发生金额不能为空
		if(entity.getBeginPeriodPreAmount()==null){
			throw new SettlementException("对账单期初预收发生金额为空！");
		}
		//校验对账单期初预付发生金额不能为空
		if(entity.getBeginPeriodAdvAmount()==null){
			throw new SettlementException("对账单期初预付发生金额为空！");
		}
		//校验对账单本期发生金额不能为空
		if(entity.getPeriodAmount()==null){
			throw new SettlementException("对账单本期发生金额为空！");
		}
		//校验对账单本期应收发生金额不能为空
		if(entity.getPeriodRecAmount()==null){
			throw new SettlementException("对账单本期应收发生金额为空！");
		}
		//校验对账单本期应付发生金额不能为空
		if(entity.getPeriodPayAmount()==null){
			throw new SettlementException("对账单本期应付发生金额为空！");
		}
		//校验对账单本期预收发生金额不能为空
		if(entity.getPeriodPreAmount()==null){
			throw new SettlementException("对账单对账单本期预收发生金额为空！");
		}
		//校验对账单本期预付发生金额不能为空
		if(entity.getPeriodAdvAmount()==null){
			throw new SettlementException("对账单本期预付发生金额为空！");
		}
		//校验对账单本期剩余应收金额不能为空
		if(entity.getPeriodUnverifyRecAmount()==null){
			throw new SettlementException("对账单本期剩余应收金额为空！");
		}
		//校验对账单本期剩余应付金额不能为空
		if(entity.getPeriodUnverifyPayAmount()==null){
			throw new SettlementException("对账单本期剩余应付金额为空！");
		}
		//校验对账单本期剩余预收金额不能为空
		if(entity.getPeriodUnverifyPreAmount()==null){
			throw new SettlementException("对账单本期剩余预收金额为空！");
		}
		//校验对账单本期剩余预付金额不能为空
		if(entity.getPeriodUnverifyAdvAmount()==null){
			throw new SettlementException("对账单本期剩余预付金额为空！");
		}
		//校验对账单业务日期不能为空
		if(entity.getBusinessDate()==null){
			throw new SettlementException("对账单账单业务日期为空！");
		}
		//校验对账单创建时间不能为空
		if(entity.getCreateTime()==null){
			throw new SettlementException("对账单创建时间为空！");
		}
		//校验对账单版本号不能为空
		if(entity.getVersionNo()==null){
			throw new SettlementException("对账单版本号为空！");
		}
		logger.info("新增对账单，successfully exit service ,对账单号：" + entity.getStatementBillNo());
		return statementOfAccountEntityDao.add(entity);
	}

	/**
	 * 根据对账单号查询对账单是否确认，并返回已确认对账单单号（传入）
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-26 上午10:26:47
	 */
	@Override
	public List<String> queryConfirmStatmentOfAccount(
			List<String> sourcesStatementNos) {

		// 保存返回的对账单号集合
		List<String> list = new ArrayList<String>();
		// 如果传入的单号不为空，则查询对账单明细信息
		if (CollectionUtils.isNotEmpty(sourcesStatementNos)) {
			if (sourcesStatementNos.size() > SettlementConstants.MAX_BILL_NO_SIZE) {
				throw new SettlementException("传入的单号个数超过"
						+ SettlementConstants.MAX_BILL_NO_SIZE + "个");
			}
			List<StatementOfAccountEntity> statementOfAccountList = statementOfAccountEntityDao
					.queryByStatementNos(
							sourcesStatementNos,
							SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM);
			// 如果获取的对账单明细信息不为空，则根据对账单明细的对账单号查询对账单信息
			if (CollectionUtils.isNotEmpty(statementOfAccountList)) {
				Iterator<StatementOfAccountEntity> it = statementOfAccountList
						.iterator();
				while (it.hasNext()) {
					StatementOfAccountEntity soaEntity = it.next();
					// 根据明细单号获取对账单信息
					list.add(soaEntity.getStatementBillNo());
				}
			}
		}
		return list;
	}

	/**
	 * 新增对账单明细时修改对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-3 下午4:37:56
	 */
	@Override
	public void updateStatementForAddDetail(StatementOfAccountEntity entity,
			CurrentInfo info) {
		if(info==null){
			throw new SettlementException("当前登陆信息为空，请退出重新登陆！");
		}
		//设置修改时间、修改人编码、名称
		entity.setModifyTime(new Date());
		entity.setModifyUserCode(info.getEmpCode());
		entity.setModifyUserName(info.getEmpName());
		//执行修改明细操作
		int row = statementOfAccountEntityDao
				.updateStatementForAddDetail(entity);
		//校验修改结果
		if (row != 1) {
			throw new SettlementException("修改条数错误，请重新操作！");
		}
	}

	/**
	 * 删除对账单明细时修改对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-3 下午4:37:56
	 */
	@Override
	public void updateStatementForDeleteDetail(StatementOfAccountEntity entity,
			CurrentInfo info) {
		if(info==null){
			throw new SettlementException("当前登陆信息为空，请退出重新登陆！");
		}
		//设置修改时间、修改人编码、名称
		entity.setModifyTime(new Date());
		entity.setModifyUserCode(info.getEmpCode());
		entity.setModifyUserName(info.getEmpName());
		//执行修改明细操作
		int row = statementOfAccountEntityDao
				.updateStatementForDeleteDetail(entity);
		//校验修改结果
		if (row == 0) {
			throw new SettlementException("修改条数为0，对账单已被更新，请重新操作！");
		}
		if (row != 1) {
			throw new SettlementException("修改条数不等于1，修改条数错误，请重新操作！");
		}
	}

	/**
	 * 反确认对账单
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-5 下午5:21:47
	 */
	@Override
	public void unConfirmStatement(List<StatementOfAccountEntity> list,
			CurrentInfo info) {
		if(info==null){
			throw new SettlementException("当前登陆信息为空，请退出重新登陆！");
		}
		if (CollectionUtils.isNotEmpty(list)) {
			Date nowTime = new Date();
			for (StatementOfAccountEntity entity : list) {
				// 判断对账单是否存在生效的核销单
				List<BillWriteoffEntity> billWriteoffEntitys = billWriteoffService
						.queryByStatementBillNo(entity.getStatementBillNo(),
								null, null, null, FossConstants.ACTIVE);
				if (CollectionUtils.isNotEmpty(billWriteoffEntitys)) {
					throw new SettlementException("对账单"
							+ entity.getStatementBillNo()
							+ "存在生效的核销单！不能进行反确认操作！");
				}
				// 判断该对账单是否被网厅锁定
				if (entity.getUnlockTime() != null
						&& entity.getUnlockTime().after(nowTime)) {
					throw new SettlementException("对账单"
							+ entity.getStatementBillNo()
							+ "已被网上营业厅锁定！不能进行反确认操作！");
				}
				// 判断该对账单是否已发生改变
				StatementOfAccountEntity newEntity = statementOfAccountEntityDao
						.queryByPrimaryKey(entity.getId());
				if (!newEntity.getVersionNo().equals(entity.getVersionNo())) {
					throw new SettlementException("对账单"
							+ entity.getStatementBillNo() + "已被修改，请重新刷新后在操作！");
				}
				//设置确认状态
				entity.setConfirmStatus(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM);
				//设置确认时间、确认人编码、确认人名称
				entity.setConfirmTime(null);
				entity.setConfirmUserCode(null);
				entity.setConfirmUserName(null);
				//设置修改时间、修改人编码、修改人名称
				entity.setModifyTime(nowTime);
				entity.setModifyUserCode(info.getEmpCode());
				entity.setModifyUserName(info.getEmpName());
				//执行修改对账单操作
				int row = statementOfAccountEntityDao
						.confirmOrUnConfirmStatement(entity);
				//校验修改结果信息
				if (row != 1) {
					throw new SettlementException("数据库更新条数发生错误！");
				}
				// 插入操作日志
				OperatingLogEntity logEntity = new OperatingLogEntity();
				//操作单据编号
				logEntity.setOperateBillNo(entity.getStatementBillNo());
				//操作单据类型
				logEntity.setOperateBillType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__STATEMENT_OF_ACCOUNT);
				//操作类型
				logEntity.setOperateType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__UNCONFIRM);
				operatingLogService.addOperatingLog(logEntity, info);
			}
		}
	}

	/**
	 * 确认对账单
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-6 上午11:16:01
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService#confirmStatement(java.util.List,
	 *      com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity)
	 */
	@Override
	public void confirmStatement(List<StatementOfAccountEntity> list,
			CurrentInfo info) {
		if(info==null){
			throw new SettlementException("当前登陆信息为空，请退出重新登陆！");
		}
		//判断list集合是否为空
		if (CollectionUtils.isNotEmpty(list)) {
			Date nowTime = new Date();
			for (StatementOfAccountEntity entity : list) {
				// 判断该对账单是否已发生改变
				StatementOfAccountEntity newEntity = statementOfAccountEntityDao
						.queryByPrimaryKey(entity.getId());
				if (!newEntity.getVersionNo().equals(entity.getVersionNo())) {
					throw new SettlementException("对账单已被修改，请重新查询后再操作！");
				}
				if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM
						.equals(entity.getConfirmStatus())) {
					throw new SettlementException("对账单已被确认！");
				}
				//修改确认状态、确认时间、确认人编码、确认人名称
				entity.setConfirmStatus(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM);
				entity.setConfirmTime(nowTime);
				entity.setConfirmUserCode(info.getEmpCode());
				entity.setConfirmUserName(info.getEmpName());
				//设置修改时间、修改人编码、修改人名称
				entity.setModifyTime(nowTime);
				entity.setModifyUserCode(info.getEmpCode());
				entity.setModifyUserName(info.getEmpName());
				//执行修改明细操作
				int row = statementOfAccountEntityDao
						.confirmOrUnConfirmStatement(entity);
				if (row != 1) {
					throw new SettlementException("数据库更新条数发生错误！");
				}
				// 插入操作日志
				OperatingLogEntity logEntity = new OperatingLogEntity();
				//设置操作单据编号
				logEntity.setOperateBillNo(entity.getStatementBillNo());
				//设置操作类型
				logEntity.setOperateBillType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__STATEMENT_OF_ACCOUNT);
				//设置操作类型
				logEntity.setOperateType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__CONFIRM);
				operatingLogService.addOperatingLog(logEntity, info);
			}
		}

	}

	/**
	 * 根据对账单ID查询
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-7 上午9:09:38
	 */
	@Override
	public StatementOfAccountEntity queryByPrimaryKey(String id) {
		return statementOfAccountEntityDao.queryByPrimaryKey(id);
	}
	
	/**
	 * 根据对账单号查询
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-7 上午9:09:38
	 */
	@Override
	public StatementOfAccountEntity queryByStatementNo(String statementNo) {
		//校验对账单号是否为空
		if(StringUtil.isEmpty(statementNo)){
			throw new SettlementException("按对账单号查询对账单时，对账单号为空！", "");
		}
		return statementOfAccountEntityDao.queryByStatementNo(statementNo);
	}

	/**
	 * 核销时，修改对账单及对账单明细信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-7 上午8:44:43
	 */
	@Override
	public void updateStatementAndDetailForWriteOff(
			StatementOfAccountUpdateDto dto, CurrentInfo info) {
		logger.info("核销时，修改对账单及对账单明细信息 enter service..");
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取参与核销的应收单集合
		if (CollectionUtils.isNotEmpty(dto.getReceivableEntityList())) {
			map = updatePeriodAmountByYSList(dto, map,
					SettlementConstants.WRITE_OFF, info);
		}
		// 获取参与核销的应付单集合
		if (CollectionUtils.isNotEmpty(dto.getPayableEntityList())) {
			map = updatePeriodAmountByYFList(dto, map,
					SettlementConstants.WRITE_OFF, info);
		}
		// 获取参与核销的预收单集合
		if (CollectionUtils.isNotEmpty(dto.getDepositReceivedEntityList())) {
			map = updatePeriodAmountByUSList(dto, map,
					SettlementConstants.WRITE_OFF, info);
		}
		// 获取参与核销的预付单
		if (CollectionUtils.isNotEmpty(dto.getAdvancePaymentEntityList())) {
			map = updatePeriodAmountByUFList(dto, map,
					SettlementConstants.WRITE_OFF, info);
		}
		// 修改对账单信息，更新数据库
		if (!map.isEmpty()) {
			Date now = new Date();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				StatementOfAccountEntity entity = (StatementOfAccountEntity) entry
						.getValue();
				//如果对账单处于已确认状态，则不允许操作
				if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM.
						equals(entity.getConfirmStatus())){
					throw new SettlementException("对账单号："+entity.getStatementBillNo()+
							"已经处于确认状态，不能操作该对账单下面的明细信息");
				}
				//设置修改时间、修改人编码、修改人名称
				entity.setModifyTime(now);
				entity.setModifyUserCode(info.getEmpCode());
				entity.setModifyUserName(info.getEmpName());
				//修改明细单据信息
				int row = statementOfAccountEntityDao
						.updateStatementForChangeDetail(entity);
				//校验修改结果信息条数
				if (row != 1) {
					throw new SettlementException("核销对账单明细时，修改对账单信息，数据库更新条数错误，不等于1条！");
				}
				//插入操作日志
				OperatingLogEntity logEntity = new OperatingLogEntity();
				//设置操作单据编号
				logEntity.setOperateBillNo(entity.getStatementBillNo());
				//设置操作类型
				logEntity.setOperateType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__MODIFY);
				//设置操作单据类型
				logEntity.setOperateBillType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__STATEMENT_OF_ACCOUNT);
				operatingLogService.addOperatingLog(logEntity, info);
			}
		}
		logger.info("核销时，修改对账单及对账单明细信息 successfully exit service..");
	}

	
	
	
	
	
	
	
	
	
	/**
	 * 反核销时，修改对账单及对账单明细信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-7 上午8:44:43
	 */
	@Override
	public void updateStatementAndDetailForBackWriteOff(
			StatementOfAccountUpdateDto dto, CurrentInfo info) {
		logger.info("反核销时，修改对账单及对账单明细信息!enter service..");
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取参与反核销的应收单集合
		if (CollectionUtils.isNotEmpty(dto.getReceivableEntityList())) {
			map = updatePeriodAmountByYSList(dto, map,
					SettlementConstants.BACK_WRITE_OFF, info);
		}
		// 获取参与反核销的应付单集合
		if (CollectionUtils.isNotEmpty(dto.getPayableEntityList())) {
			map = updatePeriodAmountByYFList(dto, map,
					SettlementConstants.BACK_WRITE_OFF, info);
		}
		// 获取参与反核销的预收单集合
		if (CollectionUtils.isNotEmpty(dto.getDepositReceivedEntityList())) {
			map = updatePeriodAmountByUSList(dto, map,
					SettlementConstants.BACK_WRITE_OFF, info);
		}
		// 获取参与反核销的预付单
		if (CollectionUtils.isNotEmpty(dto.getAdvancePaymentEntityList())) {
			map = updatePeriodAmountByUFList(dto, map,
					SettlementConstants.BACK_WRITE_OFF, info);
		}
		// 修改对账单信息，更新数据库
		if (!map.isEmpty()) {
			Date now = new Date();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				StatementOfAccountEntity entity = (StatementOfAccountEntity) entry
						.getValue();
				//如果对账单处于已确认状态，则不允许操作
				if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM.
						equals(entity.getConfirmStatus())){
					throw new SettlementException("对账单号："+entity.getStatementBillNo()+
							"已经处于确认状态，不能操作该对账单下面的明细信息");
				}
				//设置对账单的修改时间、修改人编码、修改人名称
				entity.setModifyTime(now);
				entity.setModifyUserCode(info.getEmpCode());
				entity.setModifyUserName(info.getEmpName());
				//执行修改对账单明细操作
				int row = statementOfAccountEntityDao
						.updateStatementForChangeDetail(entity);
				if (row != 1) {
					throw new SettlementException("数据库更新条数错误！");
				}
				// 插入操作日志
				OperatingLogEntity logEntity = new OperatingLogEntity();
				//设置操作单据编号
				logEntity.setOperateBillNo(entity.getStatementBillNo());
				//设置操作类型
				logEntity.setOperateType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__MODIFY);
				//设置操作单据类型
				logEntity.setOperateBillType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__STATEMENT_OF_ACCOUNT);
				operatingLogService.addOperatingLog(logEntity, info);
			}
		}
		logger.info("反核销时，修改对账单及对账单明细信息 successfully exit service..");
	}

	/**
	 * 红冲或作废时，修改对账单及对账单明细信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-7 上午8:51:13
	 */
	@Override
	public void updateStatementAndDetailForRedBack(
			StatementOfAccountUpdateDto dto, CurrentInfo info) {
		logger.info("红冲时，修改对账单及对账单明细信息!enter service..");
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取参与反核销的应收单集合
		if (CollectionUtils.isNotEmpty(dto.getReceivableEntityList())) {
			map = updatePeriodAmountByYSList(dto, map,
					SettlementConstants.RED_BACK, info);
		}
		// 获取参与反核销的应付单集合
		if (CollectionUtils.isNotEmpty(dto.getPayableEntityList())) {
			map = updatePeriodAmountByYFList(dto, map,
					SettlementConstants.RED_BACK, info);
		}
		// 获取参与反核销的预收单集合
		if (CollectionUtils.isNotEmpty(dto.getDepositReceivedEntityList())) {
			map = updatePeriodAmountByUSList(dto, map,
					SettlementConstants.RED_BACK, info);
		}
		// 获取参与反核销的预付单
		if (CollectionUtils.isNotEmpty(dto.getAdvancePaymentEntityList())) {
			map = updatePeriodAmountByUFList(dto, map,
					SettlementConstants.RED_BACK, info);
		}
		// 修改对账单信息，更新数据库
		if (!map.isEmpty()) {
			Date now = new Date();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				StatementOfAccountEntity entity = (StatementOfAccountEntity) entry
						.getValue();
				//如果对账单处于已确认状态，则不允许操作
				if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM.
						equals(entity.getConfirmStatus())){
					throw new SettlementException("对账单号："+entity.getStatementBillNo()+
							"已经处于确认状态，不能操作该对账单下面的明细信息");
				}
				//设置修改时间、修改人编码和名称
				entity.setModifyTime(now);
				entity.setModifyUserCode(info.getEmpCode());
				entity.setModifyUserName(info.getEmpName());
				int row = statementOfAccountEntityDao
						.updateStatementForChangeDetail(entity);
				if (row != 1) {
					throw new SettlementException("数据库更新条数错误！");
				}
				// 插入操作日志
				OperatingLogEntity logEntity = new OperatingLogEntity();
				//设置操作单号
				logEntity.setOperateBillNo(entity.getStatementBillNo());
				//设置操作类型
				logEntity.setOperateType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__MODIFY);
				//设置操作单据类型
				logEntity.setOperateBillType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__STATEMENT_OF_ACCOUNT);
				operatingLogService.addOperatingLog(logEntity, info);
			}
		}
		logger.info("红冲作废时，修改对账单及对账单明细信息 successfully exit service..");
	}

	/**
	 * 
	 * 根据对账单的本期发生金额自动更新对账单的未核销发生金额
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-13 下午12:36:01
	 */
	public StatementOfAccountEntity updatePeriodUnverifyAmountCommon(
			StatementOfAccountEntity entity) {
		return updatePeriodUnverifyAmount(entity);

	}

	/**
	 * 当对账单金额信息发生变化时自动更新对账单的本期剩余未还金额
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-7 下午6:40:31
	 */
	private StatementOfAccountEntity updateUnPaidAmount(
			StatementOfAccountEntity entity, CurrentInfo info) {
		// 获取本期发生金额
		BigDecimal periodAmount = entity.getPeriodAmount();
		// 设置本期未还金额
		entity.setUnpaidAmount(periodAmount);
		return entity;
	}

	/**
	 * 核销、反核销、红冲时，根据应收单集合更新对账单及对账单明细信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-7 下午4:56:00
	 */
	private Map<String, Object> updatePeriodAmountByYSList(
			StatementOfAccountUpdateDto dto, Map<String, Object> map,
			String flag, CurrentInfo info) {
		// 循环应收单集合
		for (BillReceivableEntity rsEntity : dto.getReceivableEntityList()) {
			// 如果明细单据有对账单号，则根据对账单号获取对账单信息
			if (StringUtil.isEmpty(rsEntity.getStatementBillNo())) {
				throw new SettlementException("对账单号为空！程序出现异常！没有传入有效的N/A空对账单号！");
			}
			if (!SettlementConstants.DEFAULT_BILL_NO.equals(rsEntity
					.getStatementBillNo())) {
				// 判断map集合中是否已存在该对账单信息，不存在则从数据库中读取
				StatementOfAccountEntity soaEntity = null;
				soaEntity = (StatementOfAccountEntity) map.get(rsEntity
						.getStatementBillNo());
				//校验改对账单是否已经存在，不存在则执行按对账单号查询对账单信息
				if (soaEntity == null) {
					soaEntity = statementOfAccountEntityDao
							.queryByStatementNo(rsEntity.getStatementBillNo());
				}
				//校验对账单实体是否为空
				if (soaEntity == null) {
					throw new SettlementException("请确认应收单的对账单号字段值是否合法！");
				}
				//校验对账单的确认状态
				if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM
						.equals(soaEntity.getConfirmStatus())) {
					throw new SettlementException("对账单已被确认不能更新");
				}
				// 更新对账单信息
				// 获取对账单明细
				StatementOfAccountDEntity soadEntity = statementOfAccountDService
						.queryByResourceAndStatementNo(
								rsEntity.getReceivableNo(),
								rsEntity.getStatementBillNo(), FossConstants.NO);
				if (soadEntity == null) {
					throw new SettlementException("该应收单在对账单"
							+ rsEntity.getStatementBillNo() + "明细中不存在！");
				}
				// 修改对账单信息
				BigDecimal periodAmount = BigDecimal.ZERO; // 本期发生金额
				BigDecimal periodRecAmount = BigDecimal.ZERO; // 本期应收金额
				// 如果是核销操作
				if (SettlementConstants.WRITE_OFF.equals(flag)) {
					// 变化金额
					BigDecimal changeAmount = soadEntity.getUnverifyAmount()
							.subtract(rsEntity.getUnverifyAmount());
					// 减少本期发生金额
					periodAmount = soaEntity.getPeriodAmount().subtract(
							changeAmount);
					// 减少本期应收金额
					periodRecAmount = soaEntity.getPeriodRecAmount().subtract(
							changeAmount);
				}
				// 如果是反核销操作
				else if (SettlementConstants.BACK_WRITE_OFF.equals(flag)) {
					// 变化金额
					BigDecimal changeAmount = rsEntity.getUnverifyAmount()
							.subtract(soadEntity.getUnverifyAmount());
					// 增加本期发生金额
					periodAmount = soaEntity.getPeriodAmount()
							.add(changeAmount);
					// 增加本期应收金额
					periodRecAmount = soaEntity.getPeriodRecAmount().add(
							changeAmount);
				}
				// 如果是红冲操作
				else if (SettlementConstants.RED_BACK.equals(flag)) {
					// 变化金额
					BigDecimal changeAmount = rsEntity.getUnverifyAmount();
					// 减少本期发生金额
					periodAmount = soaEntity.getPeriodAmount().subtract(
							changeAmount);
					// 减少本期应收金额
					periodRecAmount = soaEntity.getPeriodRecAmount().subtract(
							changeAmount);
				}
				soaEntity.setPeriodRecAmount(periodRecAmount);
				soaEntity.setPeriodAmount(periodAmount);

				// 调用私有方法，更新本期剩余发生金额信息
				soaEntity = updatePeriodUnverifyAmount(soaEntity);
				// 调用私有方法,更新剩余未还金额
				soaEntity = updateUnPaidAmount(soaEntity, info);
				// 如果是红冲操作，则删除对账单明细
				if (SettlementConstants.RED_BACK.equals(flag)) {
					// 删除时间
					soadEntity.setDisableTime(new Date());
					// 删除标识
					soadEntity.setIsDelete(FossConstants.YES);
					statementOfAccountDService
							.updateStatementDetailForDeleteDetail(soadEntity);
				}
				// 如果是核销和反核销，则修改对账单明细
				else {
					// 修改对账单明细信息（已核销和未核销金额）
					soadEntity.setVerifyAmount(rsEntity.getVerifyAmount());
					soadEntity.setUnverifyAmount(rsEntity.getUnverifyAmount());
					statementOfAccountDService
							.updateStatementDetailByAmount(soadEntity);
				}
				// 将对账单信息保存到MAP集合中，如果存在则删除原来的对账单信息
				if (map.get(soaEntity.getStatementBillNo()) != null) {
					map.remove(soaEntity.getStatementBillNo());
					map.put(soaEntity.getStatementBillNo(), soaEntity);
				}
				else{
					map.put(soaEntity.getStatementBillNo(), soaEntity);
				}
			}
		}
		return map;
	}

	/**
	 * 核销、反核销、红冲时，根据应付单集合更新对账单及对账单明细信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-7 下午4:56:00
	 */
	private Map<String, Object> updatePeriodAmountByYFList(
			StatementOfAccountUpdateDto dto, Map<String, Object> map,
			String flag, CurrentInfo info) {
		// 循环应付单集合
		for (BillPayableEntity yfEntity : dto.getPayableEntityList()) {
			// 如果明细单据有对账单号，则根据对账单号获取对账单信息
			if (!SettlementConstants.DEFAULT_BILL_NO.equals(yfEntity
					.getStatementBillNo())) {
				// 判断map集合中是否已存在该对账单信息，不存在则从数据库中读取
				StatementOfAccountEntity soaEntity = null;
				soaEntity = (StatementOfAccountEntity) map.get(yfEntity
						.getStatementBillNo());
				//校验改对账单是否已经存在，不存在则执行按对账单号查询对账单信息
				if (soaEntity == null) {
					soaEntity = statementOfAccountEntityDao
							.queryByStatementNo(yfEntity.getStatementBillNo());
				}
				//校验对账单实体是否为空
				if (soaEntity == null) {
					throw new SettlementException("请确认应付单的对账单号字段值是否合法！");
				}
				//校验对账单的确认状态
				if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM
						.equals(soaEntity.getConfirmStatus())) {
					throw new SettlementException("对账单已被确认不能更新");
				}
				// 更新对账单信息
				// 根据应付单号获取对账单明细（一条应付单只能在一个对账单中存在一条记录）
				// 获取对账单明细
				StatementOfAccountDEntity soadEntity = statementOfAccountDService
						.queryByResourceAndStatementNo(yfEntity.getPayableNo(),
								yfEntity.getStatementBillNo(), FossConstants.NO);
				if (soadEntity == null) {
					throw new SettlementException("该应付单在对账单"
							+ yfEntity.getStatementBillNo() + "明细中不存在！");
				}
				// 修改对账单信息
				BigDecimal periodAmount = BigDecimal.ZERO; // 本期发生金额
				BigDecimal periodPayAmount = BigDecimal.ZERO; // 本期应付金额
				// 核销操作
				if (SettlementConstants.WRITE_OFF.equals(flag)) {
					// 变化金额
					BigDecimal changeAmount = soadEntity.getUnverifyAmount()
							.subtract(yfEntity.getUnverifyAmount());
					// 增加本期发生金额
					periodAmount = soaEntity.getPeriodAmount()
							.add(changeAmount);
					// 减少本期应付金额
					periodPayAmount = soaEntity.getPeriodPayAmount().subtract(
							changeAmount);
				}
				// 反核销操作
				else if (SettlementConstants.BACK_WRITE_OFF.equals(flag)) {
					// 变化金额
					BigDecimal changeAmount = yfEntity.getUnverifyAmount()
							.subtract(soadEntity.getUnverifyAmount());
					// 减少本期发生金额
					periodAmount = soaEntity.getPeriodAmount().subtract(
							changeAmount);
					// 增加本期应付金额
					periodPayAmount = soaEntity.getPeriodPayAmount().add(
							changeAmount);
				} else if (SettlementConstants.RED_BACK.equals(flag)) {
					// 变化金额
					BigDecimal changeAmount = soadEntity.getUnverifyAmount();
					// 增加本期发生金额
					periodAmount = soaEntity.getPeriodAmount()
							.add(changeAmount);
					// 减少本期应付金额
					periodPayAmount = soaEntity.getPeriodPayAmount().subtract(
							changeAmount);
				}
				soaEntity.setPeriodAmount(periodAmount);
				soaEntity.setPeriodPayAmount(periodPayAmount);
				// 调用私有方法，更新本期剩余发生金额信息
				soaEntity = updatePeriodUnverifyAmount(soaEntity);
				// 调用私有方法,更新剩余未还金额
				soaEntity = updateUnPaidAmount(soaEntity, info);
				// 如果是红冲操作，则删除对账单明细
				if (SettlementConstants.RED_BACK.equals(flag)) {
					// 删除时间
					soadEntity.setDisableTime(new Date());
					// 删除标识
					soadEntity.setIsDelete(FossConstants.YES);
					statementOfAccountDService
							.updateStatementDetailForDeleteDetail(soadEntity);
				}
				// 如果是核销和反核销，则修改对账单明细
				else {
					// 修改对账单明细信息
					soadEntity.setVerifyAmount(yfEntity.getVerifyAmount());
					soadEntity.setUnverifyAmount(yfEntity.getUnverifyAmount());
					statementOfAccountDService
							.updateStatementDetailByAmount(soadEntity);
				}
				// 将对账单信息保存到MAP集合中
				if (map.get(soaEntity.getStatementBillNo()) != null) {
					map.remove(soaEntity.getStatementBillNo());
					map.put(soaEntity.getStatementBillNo(), soaEntity);
				}else{
					map.put(soaEntity.getStatementBillNo(), soaEntity);
				}
			}
		}
		return map;
	}

	/**
	 * 核销、反核销、红冲时，根据预收单集合更新对账单及对账单明细信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-7 下午4:56:00
	 */
	private Map<String, Object> updatePeriodAmountByUSList(
			StatementOfAccountUpdateDto dto, Map<String, Object> map,
			String flag, CurrentInfo info) {
		// 循环预收单集合
		for (BillDepositReceivedEntity usEntity : dto
				.getDepositReceivedEntityList()) {
			// 如果明细单据有对账单号，则根据对账单号获取对账单信息
			if (!SettlementConstants.DEFAULT_BILL_NO.equals(usEntity
					.getStatementBillNo())) {
				// 判断map集合中是否已存在该对账单信息，不存在则从数据库中读取
				StatementOfAccountEntity soaEntity = null;
				soaEntity = (StatementOfAccountEntity) map.get(usEntity
						.getStatementBillNo());
				//校验改对账单是否已经存在，不存在则执行按对账单号查询对账单信息
				if (soaEntity == null) {
					soaEntity = statementOfAccountEntityDao
							.queryByStatementNo(usEntity.getStatementBillNo());
				}
				//校验对账单信息是否为空
				if (soaEntity == null) {
					throw new SettlementException("请确认预收单的对账单号字段值是否合法！");
				}
				//校验对账单的确认状态
				if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM
						.equals(soaEntity.getConfirmStatus())) {
					throw new SettlementException("对账单已被确认不能更新");
				}
				// 更新对账单信息
				// 根据预付单号获取对账单明细（一条预付单只能在一个对账单中存在一条记录）
				// 获取对账单明细
				StatementOfAccountDEntity soadEntity = statementOfAccountDService
						.queryByResourceAndStatementNo(
								usEntity.getDepositReceivedNo(),
								usEntity.getStatementBillNo(), FossConstants.NO);
				if (soadEntity == null) {
					throw new SettlementException("该预收单在对账单"
							+ usEntity.getStatementBillNo() + "明细中不存在！");
				}
				// 修改对账单信息
				BigDecimal periodAmount = BigDecimal.ZERO; // 本期发生金额
				BigDecimal periodPreAmount = BigDecimal.ZERO; // 本期预收金额
				// 核销操作
				if (SettlementConstants.WRITE_OFF.equals(flag)) {
					// 变化金额
					BigDecimal changeAmount = soadEntity.getUnverifyAmount()
							.subtract(usEntity.getUnverifyAmount());
					// 增加本期发生金额
					periodAmount = soaEntity.getPeriodAmount()
							.add(changeAmount);
					// 减少本期预收金额
					periodPreAmount = soaEntity.getPeriodPreAmount().subtract(
							changeAmount);
				} else if (SettlementConstants.BACK_WRITE_OFF.equals(flag)) {
					// 变化金额
					BigDecimal changeAmount = usEntity.getUnverifyAmount()
							.subtract(soadEntity.getUnverifyAmount());
					// 减少本期发生金额
					periodAmount = soaEntity.getPeriodAmount().subtract(
							changeAmount);
					// 增加本期预收金额
					periodPreAmount = soaEntity.getPeriodPreAmount().add(
							changeAmount);
				} else if (SettlementConstants.RED_BACK.equals(flag)) {
					// 变化金额
					BigDecimal changeAmount = soadEntity.getUnverifyAmount();
					// 增加本期发生金额
					periodAmount = soaEntity.getPeriodAmount()
							.add(changeAmount);
					// 减少本期预收金额
					periodPreAmount = soaEntity.getPeriodPreAmount().subtract(
							changeAmount);
				}
				soaEntity.setPeriodAmount(periodAmount);
				soaEntity.setPeriodPreAmount(periodPreAmount);
				// 调用私有方法，更新本期剩余发生金额信息
				soaEntity = updatePeriodUnverifyAmount(soaEntity);
				// 调用私有方法,更新剩余未还金额
				soaEntity = updateUnPaidAmount(soaEntity, info);
				// 如果是红冲操作，则删除对账单明细
				if (SettlementConstants.RED_BACK.equals(flag)) {
					// 删除时间
					soadEntity.setDisableTime(new Date());
					// 删除标识
					soadEntity.setIsDelete(FossConstants.YES);
					statementOfAccountDService
							.updateStatementDetailForDeleteDetail(soadEntity);
				}
				// 如果是核销和反核销，则修改对账单明细
				else {
					// 修改对账单明细信息(已核销和未核销金额)
					soadEntity.setVerifyAmount(usEntity.getVerifyAmount());
					soadEntity.setUnverifyAmount(usEntity.getUnverifyAmount());
					statementOfAccountDService
							.updateStatementDetailByAmount(soadEntity);
				}
				// 将对账单信息保存到MAP集合中，如果存在则先删除原来的对账单信息
				if (map.get(soaEntity.getStatementBillNo()) != null) {
					map.remove(soaEntity.getStatementBillNo());
					map.put(soaEntity.getStatementBillNo(), soaEntity);
				}else{
					map.put(soaEntity.getStatementBillNo(), soaEntity);
				}
			}
		}
		return map;
	}

	/**
	 * 核销、反核销、红冲时，根据预付单集合更新对账单及对账单明细信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-7 下午4:56:00
	 */
	private Map<String, Object> updatePeriodAmountByUFList(
			StatementOfAccountUpdateDto dto, Map<String, Object> map,
			String flag, CurrentInfo info) {
		// 循环预付单集合
		for (BillAdvancedPaymentEntity ufEntity : dto
				.getAdvancePaymentEntityList()) {
			// 如果明细单据有对账单号，则根据对账单号获取对账单信息
			if (!SettlementConstants.DEFAULT_BILL_NO.equals(ufEntity
					.getStatementBillNo())) {
				// 判断map集合中是否已存在该对账单信息，不存在则从数据库中读取
				StatementOfAccountEntity soaEntity = null;
				soaEntity = (StatementOfAccountEntity) map.get(ufEntity
						.getStatementBillNo());
				//校验改对账单是否已经存在，不存在则执行按对账单号查询对账单信息
				if (soaEntity == null) {
					soaEntity = statementOfAccountEntityDao
							.queryByStatementNo(ufEntity.getStatementBillNo());
				}
				//校验查询出的对账单是否为空
				if (soaEntity == null) {
					throw new SettlementException("请确认预付单的对账单号字段值是否合法！");
				}
				//校验对账单的确认状态
				if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM
						.equals(soaEntity.getConfirmStatus())) {
					throw new SettlementException("对账单已被确认不能更新");
				}
				// 更新对账单信息
				// 根据预付单号获取对账单明细（一条预付单只能在一个对账单中存在一条记录）
				// 获取对账单明细
				StatementOfAccountDEntity soadEntity = statementOfAccountDService
						.queryByResourceAndStatementNo(
								ufEntity.getAdvancesNo(),
								ufEntity.getStatementBillNo(), FossConstants.NO);
				//校验对账单明细是否为空
				if (soadEntity == null) {
					throw new SettlementException("该预付单在对账单"
							+ ufEntity.getStatementBillNo() + "明细中不存在！");
				}
				
				// 修改对账单信息
				BigDecimal periodAmount = BigDecimal.ZERO; // 本期发生金额
				BigDecimal periodAdvAmount = BigDecimal.ZERO; // 本期预付金额
				// 核销操作时
				if (SettlementConstants.WRITE_OFF.equals(flag)) {
					// 变化金额
					BigDecimal changeAmount = soadEntity.getUnverifyAmount()
							.subtract(ufEntity.getUnverifyAmount());
					// 减少本期发生金额
					periodAmount = soaEntity.getPeriodAmount().subtract(
							changeAmount);
					// 减少本期预付金额
					periodAdvAmount = soaEntity.getPeriodAdvAmount().subtract(
							changeAmount);
				} else if (SettlementConstants.BACK_WRITE_OFF.equals(flag)) {
					// 变化金额
					BigDecimal changeAmount = ufEntity.getUnverifyAmount()
							.subtract(soadEntity.getUnverifyAmount());
					// 增加本期发生金额
					periodAmount = soaEntity.getPeriodAmount()
							.add(changeAmount);
					// 增加本期预付金额
					periodAdvAmount = soaEntity.getPeriodAdvAmount().add(
							changeAmount);
				} else if (SettlementConstants.RED_BACK.equals(flag)) {
					// 变化金额
					BigDecimal changeAmount = soadEntity.getUnverifyAmount();
					// 减少本期发生金额
					periodAmount = soaEntity.getPeriodAmount().subtract(
							changeAmount);
					// 减少本期预付金额
					periodAdvAmount = soaEntity.getPeriodAdvAmount().subtract(
							changeAmount);
				}
				soaEntity.setPeriodAdvAmount(periodAdvAmount);
				soaEntity.setPeriodAmount(periodAmount);
				// 调用私有方法，更新本期剩余发生金额信息
				soaEntity = updatePeriodUnverifyAmount(soaEntity);
				// 调用私有方法,更新剩余未还金额
				soaEntity = updateUnPaidAmount(soaEntity, info);
				// 如果是红冲操作，则删除对账单明细
				if (SettlementConstants.RED_BACK.equals(flag)) {
					// 删除时间
					soadEntity.setDisableTime(new Date());
					// 删除标识
					soadEntity.setIsDelete(FossConstants.YES);
					statementOfAccountDService
							.updateStatementDetailForDeleteDetail(soadEntity);
				}
				// 如果是核销和反核销，则修改对账单明细
				else {
					// 修改对账单明细信息
					soadEntity.setVerifyAmount(ufEntity.getVerifyAmount());
					soadEntity.setUnverifyAmount(ufEntity.getUnverifyAmount());
					statementOfAccountDService
							.updateStatementDetailByAmount(soadEntity);
				}
				// 将对账单信息保存到MAP集合中
				if (map.get(soaEntity.getStatementBillNo()) != null) {
					map.remove(soaEntity.getStatementBillNo());
					map.put(soaEntity.getStatementBillNo(), soaEntity);
				}else{
					map.put(soaEntity.getStatementBillNo(), soaEntity);
				}

			}
		}
		return map;
	}

	/**
	 * 根据对账单的本期发生金额更新对账单的本期剩余发生金额信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-7 下午4:47:41
	 */
	private StatementOfAccountEntity updatePeriodUnverifyAmount(
			StatementOfAccountEntity soaEntity) {
		/*
		 * 计算本期剩余发生金额明细信息，由于下面代码逻辑比较负责，所以将部分代码逻辑分析过程作为注释进行了添加
		 * 1、对账单明细的核销顺序为预收冲应收、应收冲应付、还款冲应收、预付冲应付 2、本期发生金额=本期应收+本期预付-本期应付-本期预收
		 * 3、如果本期发生金额大于0，且应收单的核销优先于预付单，所以结果可能由本期剩余应收金额+本期剩余预付金额 或者完全由本期剩余预付金额组成；
		 * 当本期发生金额大于本期预付金额，则本期发生金额=本期剩余应收金额+本期剩余预付金额，且本期剩余应收金额=
		 * 本期发生金额-本期预付金额，本期剩余预付金额=本期预付金额； 当本期发生金额小于等于本期预付金额，则本期发生金额=本期剩余预付金额；
		 * 4、如果本期发生金额小于0，且本期预收单的核销优先于应付单，所以结果可能由预收单+应付单或者完全由应付单组成
		 * 当本期发生金额的绝对值大于本期应付金额，则本期发生金额=本期剩余应付金额=本期剩余预收金额，且本期剩余应
		 * 付金额=本期应付金额，本期剩余预收金额=本期发生金额绝对值-本期应付金额；
		 * 当本期发生金额绝对值小于本期应付金额，则本期发生金额绝对值=本期剩余应付金额
		 */
		// 本期剩余应收金额
		BigDecimal periodUnverifyRecAmount = BigDecimal.ZERO; 
		// 本期剩余应付金额
		BigDecimal periodUnverifyPayAmount = BigDecimal.ZERO; 
		// 本期剩余预收金额
		BigDecimal periodUnverifyPreAmount = BigDecimal.ZERO; 
		// 本期剩余预付金额
		BigDecimal periodUnverifyAdvAmount = BigDecimal.ZERO; 
		//如果本期发生金额大于0
		if (soaEntity.getPeriodAmount().compareTo(BigDecimal.ZERO) > 0) {
			//如果本期发生金额大于本期预付金额
			if (soaEntity.getPeriodAmount().compareTo(
					soaEntity.getPeriodAdvAmount()) > 0) {
				//本期未核销应收金额=本期发生金额减去本期预付金额
				periodUnverifyRecAmount = soaEntity.getPeriodAmount().subtract(
						soaEntity.getPeriodAdvAmount());
				//本期未核销预付金额=本期发生金额减去奔去未核销应收金额
				periodUnverifyAdvAmount = soaEntity.getPeriodAmount().subtract(
						periodUnverifyRecAmount);

			} else {
				//本期未核销预付金额=本期发生金额
				periodUnverifyAdvAmount = soaEntity.getPeriodAmount();
			}
			//如果本期发生金额小于0
		} else if (soaEntity.getPeriodAmount().compareTo(BigDecimal.ZERO) < 0) {
			//如果本期发生金额的绝对值大于本期应付金额
			if (soaEntity.getPeriodAmount().negate()
					.compareTo(soaEntity.getPeriodPayAmount()) > 0) {
				//本期未核销应付金额=本期应付金额
				periodUnverifyPayAmount = soaEntity.getPeriodPayAmount();
				//本期未核销预收金额=本期发生金额的绝对值加上本期应付金额
				periodUnverifyPreAmount = soaEntity.getPeriodAmount().negate()
						.subtract(soaEntity.getPeriodPayAmount());
			} else {
				//本期未核销应付金额=本期发生金额绝对值
				periodUnverifyPayAmount = soaEntity.getPeriodAmount().negate();
			}
		}
		//设置本期未核销应收金额
		soaEntity.setPeriodUnverifyRecAmount(periodUnverifyRecAmount);
		//应付金额
		soaEntity.setPeriodUnverifyPayAmount(periodUnverifyPayAmount);
		//预收金额
		soaEntity.setPeriodUnverifyPreAmount(periodUnverifyPreAmount);
		//预付金额
		soaEntity.setPeriodUnverifyAdvAmount(periodUnverifyAdvAmount);
		return soaEntity;
	}

	/**
	 * 根据日期查询对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-9 下午5:45:11
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService#queryStatementByCreateDate(com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountUpdateDto)
	 */
	@Override
	public List<StatementOfAccountEntity> queryStatementByCreateDate(
			StatementOfAccountUpdateDto updateDto, int start, int limit) {
		//校验开始日期和结束日期是否为空
		if (updateDto.getPeriodBeginDate() == null
				|| updateDto.getPeriodEndDate() == null){
			throw new SettlementException("日期为空！不能执行按日期查询对账单信息！", "");
		}
		//校验当前登录部门信息不能为空
	    if (StringUtil.isEmpty(updateDto.getOrgCode())){
	    	throw new SettlementException("按日期查询时，当前登录部门信息不能为空！", "");
	    }
		return statementOfAccountEntityDao
				.queryStatementByCreateDate(updateDto,start,limit);
	}
	
	@Override
	public StatementOfAccountQueryResultDto countStatementByCreateDate(StatementOfAccountUpdateDto updateDto) {
		return statementOfAccountEntityDao.countStatementByCreateDate(updateDto);
	}
	
	/**
	 * 根据对账单单号集合查询对账单(只查询本部门的对账单信息，供按对账单单号、明细单号查询查询对账单使用)
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-1-8 下午2:23:39
	 * @param list
	 *            对账单号集合
	 * @param orgCode
	 *            当前操作网点编码
	 * @return
	 */
    public List<StatementOfAccountEntity> queryCurrentStatementNos(
			List<String> statementNos, String orgCode){
    	//判断对账单号是否为空
    	if(CollectionUtils.isEmpty(statementNos)){
			throw new SettlementException("执行按对账单号集合查询对账单信息时，对账单号集合为空！","");
		}
    	//校验网点编码是否为空
    	if(StringUtil.isEmpty(orgCode)){
    		throw new SettlementException("当前登录用户的操作部门编码为空", "");
    	}
    	return statementOfAccountEntityDao.queryCurrentStatementNos(statementNos, orgCode);
    }
	/**
	 * 根据对账单号集合查询对账单信息(未被官网锁定的对账单信息)
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-9 下午6:37:44
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService#queryByStatementNos(java.util.List)
	 */
	@Override
	public List<StatementOfAccountEntity> queryByStatementNos(
			List<String> statementNos) {
		if(CollectionUtils.isEmpty(statementNos)){
			throw new SettlementException("执行按对账单号集合查询对账单信息时，对账单号集合为空！","");
		}
		return statementOfAccountEntityDao.queryByStatementNos(statementNos,
				null);
	}

	/**
	 * 对账单核销(核销、还款)时修改对账单的本期未还金额
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-15 上午11:53:57
	 */
	@Override
	public void updateStatementForWriteOff(StatementOfAccountEntity entity,
			CurrentInfo info) {
		//设置修改日期、修改人编码和名称
		entity.setModifyTime(new Date());
		entity.setModifyUserCode(info.getEmpCode());
		entity.setModifyUserName(info.getEmpName());
		// 结账次数
		short num =  (short) (entity.getSettleNum() + 1);
		entity.setSettleNum(num);
		//修改本期未还金额
		int row = statementOfAccountEntityDao
				.updateStatementForWriteoff(entity);
		if (row != 1) {
			throw new SettlementException("数据库更新异常！");
		}
		// 插入操作日志
		OperatingLogEntity logEntity = new OperatingLogEntity();
		//操作对账单号
		logEntity.setOperateBillNo(entity.getStatementBillNo());
		//操作类型
		logEntity.setOperateType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__MODIFY);
		//操作单据类型
		logEntity.setOperateBillType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__STATEMENT_OF_ACCOUNT);
		//增加操作日志信息
		operatingLogService.addOperatingLog(logEntity, info);
	}
	
	/**
	 * 
	 * 按明细反核销时，更新对账单金额，并释放应收单
	 * 
	 * @author foss-wangxuemin
	 * @date Apr 2, 2013 10:15:52 AM
	 */
	@Override
	public void updateStatementForReverseBillWriteoff(
			BillWriteoffEntity writeoffEntity, StatementOfAccountEntity soaEntity, CurrentInfo info) {
		// 应收单号List
		List<String> recNos = new ArrayList<String>();
		// 查询核销单，校验同一应收同一对账单是否存在有效的核销单（应收冲应付）
		List<BillWriteoffEntity> beginEntitys = new ArrayList<BillWriteoffEntity>();
		if (SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE
				.equals(writeoffEntity.getWriteoffType())
				|| SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__DEPOSIT_RECEIVABLE
						.equals(writeoffEntity.getWriteoffType())) {
			beginEntitys = billWriteoffService.queryByStatementBillNo(
					soaEntity.getStatementBillNo(),
					writeoffEntity.getBeginNo(), null, null,
					FossConstants.ACTIVE);
			// 应收单号为核销单的开始单号
			recNos.add(writeoffEntity.getBeginNo());
		}
		// 查询核销单，校验同一应收同一对账单是否存在有效的核销单（还款冲应收、预收冲应收）
		List<BillWriteoffEntity> endEntitys = new ArrayList<BillWriteoffEntity>();
		if (SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__RECEIVABLE_PAYABLE
				.equals(writeoffEntity.getWriteoffType())) {
			endEntitys = billWriteoffService.queryByStatementBillNo(
					soaEntity.getStatementBillNo(), null,
					writeoffEntity.getEndNo(), null, FossConstants.ACTIVE);
			// 应收单号为核销单的目的单号
			recNos.add(writeoffEntity.getEndNo());
		}
		
		// 设置修改日期、修改人编码和名称
		soaEntity.setModifyTime(new Date());
		soaEntity.setModifyUserCode(info.getEmpCode());
		soaEntity.setModifyUserName(info.getEmpName());
		// 结账次数
		short num = (short) (soaEntity.getSettleNum() + 1);
		soaEntity.setSettleNum(num);
		
		// 存在同一应收单同一对账单的有效核销记录，释放应收单
		if (CollectionUtils.isEmpty(beginEntitys)
				&& CollectionUtils.isEmpty(endEntitys)) {
			//查询待删除的对账单明细
			List<StatementOfAccountDEntity> soaDetailEntitys = new ArrayList<StatementOfAccountDEntity>();
			soaDetailEntitys = statementOfAccountDService
					.queryByResourceNos(recNos);
			StatementOfAccountDEntity soaDetailEntity = soaDetailEntitys.get(0);
			if (soaDetailEntitys.size() == 1) {
				statementOfAccountDService
						.updateStatementDetailForDeleteDetail(soaDetailEntity);
			} else {
				throw new SettlementException("核销记录中应收单号对应的对账单明细记录为空，无法更新");
			}
			// 如果本期发生金额大于零，并且核销单的核销类型为还款冲应收
			if (soaEntity.getPeriodAmount().compareTo(BigDecimal.ZERO) > 0){
				if (SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE
						.equals(writeoffEntity.getWriteoffType())) {
					// 修改对账单未还款金额，对账单未还款金额=对账单未还款金额+核销单核销金额
					soaEntity.setUnpaidAmount(soaEntity.getUnpaidAmount().add(
							writeoffEntity.getAmount()));
				}
				soaEntity.setUnpaidAmount(soaEntity.getUnpaidAmount().subtract(
						soaDetailEntity.getUnverifyAmount()));
			}
			// 本期金额修改为本期金额减去本期应收金额
			soaEntity.setPeriodAmount(soaEntity.getPeriodAmount().subtract(
					soaDetailEntity.getUnverifyAmount()));
			// 本期应收修改为零
			soaEntity.setPeriodRecAmount(soaEntity.getPeriodRecAmount()
					.subtract(soaDetailEntity.getUnverifyAmount()));
			// 更新对账单的本期发生金额、本期应收发生金额、本期未还金额
			int row = statementOfAccountEntityDao
					.updateStatementForReverseWriteoffRelease(soaEntity);
			if (row != 1) {
				throw new SettlementException("数据库更新异常！");
			}
		}
		// 不存在同一应收单同一对账单的有效核销记录，只更新为还款金额
		else {

			// 如果对账单的本期发生金额大于0，修改本期未还金额
			// 且其核销类型为还款冲应收才执行对账单未还款金额=对账单未还款金额+核销单核销金额操作
			if (soaEntity.getPeriodAmount().compareTo(BigDecimal.ZERO) > 0
					&& SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE
							.equals(writeoffEntity.getWriteoffType())) {
				// 修改对账单未还款金额，对账单未还款金额=对账单未还款金额+核销单核销金额
				soaEntity.setUnpaidAmount(soaEntity.getUnpaidAmount().add(
						writeoffEntity.getAmount()));
			}

			// 修改本期未还金额
			int row = statementOfAccountEntityDao
					.updateStatementForWriteoff(soaEntity);
			if (row != 1) {
				throw new SettlementException("数据库更新异常！");
			}
		}

		// 插入操作日志
		OperatingLogEntity logEntity = new OperatingLogEntity();
		// 操作对账单号
		logEntity.setOperateBillNo(soaEntity.getStatementBillNo());
		// 操作类型
		logEntity
				.setOperateType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__MODIFY);
		// 操作单据类型
		logEntity
				.setOperateBillType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__STATEMENT_OF_ACCOUNT);
		// 增加操作日志信息
		operatingLogService.addOperatingLog(logEntity, info);
	}
	
	/**
	 * 
	 * 根据对账单号和对账单版本号查询对账单信息
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-12-6 下午7:05:19
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService#queryByStatementNoAndVersion(java.lang.String, short)
	 */
	@Override
	public StatementOfAccountEntity queryByStatementNoAndVersion(
			String statementNo, short versionNo) {
		//校验对账单号是否为空
		if(StringUtil.isEmpty(statementNo)){
			throw new SettlementException("对账单号为空！");
		}
		//校验版本号是否为空
		if(StringUtil.isEmpty(String.valueOf(versionNo))){
			throw new SettlementException("版本号为空！");
		}
		//执行按对账单号和版本号查询对账单信息
		return statementOfAccountEntityDao.queryByStatementNoAndVersion(statementNo, versionNo);
	}



	/**
	 * 坏账核销时，修改对账单及对账单明细信息
	 * @author 095793-foss-LiQin
	 * @date 2013-7-26 上午9:07:28
	 * @param dto
	 * @param info
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService#updateBadAccountStatementAndDetailForWriteOff(com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountUpdateDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void updateBadAccountStatementAndDetailForWriteOff(
			StatementOfAccountUpdateDto dto, CurrentInfo info) {
		logger.info("核销时，修改对账单及对账单明细信息 enter service..");
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取参与核销的应收单集合
		if (CollectionUtils.isNotEmpty(dto.getReceivableEntityList())) {
			map = updateBadAccountPeriodAmountByYSList(dto, map,
					SettlementConstants.WRITE_OFF, info);
		}

		// 修改对账单信息，更新数据库
		if (!map.isEmpty()) {
			Date now = new Date();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				StatementOfAccountEntity entity = (StatementOfAccountEntity) entry
						.getValue();
				//设置修改时间、修改人编码、修改人名称
				entity.setModifyTime(now);
				entity.setModifyUserCode(info.getEmpCode());
				entity.setModifyUserName(info.getEmpName());
				//修改明细单据信息
				int row = statementOfAccountEntityDao.updateStatementForChangeDetail(entity);
				//校验修改结果信息条数
				if (row != 1) {
					throw new SettlementException("核销对账单明细时，修改对账单信息，数据库更新条数错误，不等于1条！");
				}
				//插入操作日志
				OperatingLogEntity logEntity = new OperatingLogEntity();
				//设置操作单据编号
				logEntity.setOperateBillNo(entity.getStatementBillNo());
				//设置操作类型
				logEntity.setOperateType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__MODIFY);
				//设置操作单据类型
				logEntity.setOperateBillType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__STATEMENT_OF_ACCOUNT);
				operatingLogService.addOperatingLog(logEntity, info);
			}
		}
		logger.info("核销时，修改对账单及对账单明细信息 successfully exit service..");
	}

	
	/**
	 * 坏账核销、反核销、红冲时，根据应收单集合更新对账单及对账单明细信息
	 * @author 095793-foss-LiQin
	 * @date 2013-7-26 上午9:07:43
	 * @param dto
	 * @param map
	 * @param flag
	 * @param info
	 * @return
	 */
	private Map<String, Object> updateBadAccountPeriodAmountByYSList(
			StatementOfAccountUpdateDto dto, Map<String, Object> map,
			String flag, CurrentInfo info) {
		// 循环应收单集合
		for (BillReceivableEntity rsEntity : dto.getReceivableEntityList()) {
			// 如果明细单据有对账单号，则根据对账单号获取对账单信息
			if (StringUtil.isEmpty(rsEntity.getStatementBillNo())) {
				throw new SettlementException("对账单号为空！程序出现异常！没有传入有效的N/A空对账单号！");
			}
			if (!SettlementConstants.DEFAULT_BILL_NO.equals(rsEntity
					.getStatementBillNo())) {
				// 判断map集合中是否已存在该对账单信息，不存在则从数据库中读取
				StatementOfAccountEntity soaEntity = null;
				soaEntity = (StatementOfAccountEntity) map.get(rsEntity.getStatementBillNo());
				//校验改对账单是否已经存在，不存在则执行按对账单号查询对账单信息
				if (soaEntity == null) {
					soaEntity = statementOfAccountEntityDao.queryByStatementNo(rsEntity.getStatementBillNo());
				}
				//校验对账单实体是否为空
				if (soaEntity == null) {
					throw new SettlementException("请确认应收单的对账单号字段值是否合法！");
				}
			
				// 更新对账单信息
				// 获取对账单明细
				StatementOfAccountDEntity soadEntity = statementOfAccountDService.queryByResourceAndStatementNo(rsEntity.getReceivableNo(),rsEntity.getStatementBillNo(), FossConstants.NO);
				if (soadEntity == null) {
					throw new SettlementException("该应收单在对账单"
							+ rsEntity.getStatementBillNo() + "明细中不存在！");
				}
				// 修改对账单信息
				BigDecimal periodAmount = BigDecimal.ZERO; // 本期发生金额
				BigDecimal periodRecAmount = BigDecimal.ZERO; // 本期应收金额
				// 如果是核销操作
				if (SettlementConstants.WRITE_OFF.equals(flag)) {
					// 变化金额
					BigDecimal changeAmount = soadEntity.getUnverifyAmount()
							.subtract(rsEntity.getUnverifyAmount());
					// 减少本期发生金额
					periodAmount = soaEntity.getPeriodAmount().subtract(
							changeAmount);
					// 减少本期应收金额
					periodRecAmount = soaEntity.getPeriodRecAmount().subtract(
							changeAmount);
				}
				// 如果是反核销操作
				else if (SettlementConstants.BACK_WRITE_OFF.equals(flag)) {
					// 变化金额
					BigDecimal changeAmount = rsEntity.getUnverifyAmount()
							.subtract(soadEntity.getUnverifyAmount());
					// 增加本期发生金额
					periodAmount = soaEntity.getPeriodAmount()
							.add(changeAmount);
					// 增加本期应收金额
					periodRecAmount = soaEntity.getPeriodRecAmount().add(
							changeAmount);
				}
				// 如果是红冲操作
				else if (SettlementConstants.RED_BACK.equals(flag)) {
					// 变化金额
					BigDecimal changeAmount = rsEntity.getUnverifyAmount();
					// 减少本期发生金额
					periodAmount = soaEntity.getPeriodAmount().subtract(
							changeAmount);
					// 减少本期应收金额
					periodRecAmount = soaEntity.getPeriodRecAmount().subtract(
							changeAmount);
				}
				soaEntity.setPeriodRecAmount(periodRecAmount);
				soaEntity.setPeriodAmount(periodAmount);

				// 调用私有方法，更新本期剩余发生金额信息
				soaEntity = updatePeriodUnverifyAmount(soaEntity);
				// 调用私有方法,更新剩余未还金额
				soaEntity = updateUnPaidAmount(soaEntity, info);
				// 如果是红冲操作，则删除对账单明细
				if (SettlementConstants.RED_BACK.equals(flag)) {
					// 删除时间
					soadEntity.setDisableTime(new Date());
					// 删除标识
					soadEntity.setIsDelete(FossConstants.YES);
					statementOfAccountDService
							.updateStatementDetailForDeleteDetail(soadEntity);
				}
				// 如果是核销和反核销，则修改对账单明细
				else {
					// 修改对账单明细信息（已核销和未核销金额）
					soadEntity.setVerifyAmount(rsEntity.getVerifyAmount());
					soadEntity.setUnverifyAmount(rsEntity.getUnverifyAmount());
					statementOfAccountDService
							.updateStatementDetailByAmount(soadEntity);
				}
				// 将对账单信息保存到MAP集合中，如果存在则删除原来的对账单信息
				if (map.get(soaEntity.getStatementBillNo()) != null) {
					map.remove(soaEntity.getStatementBillNo());
					map.put(soaEntity.getStatementBillNo(), soaEntity);
				}
				else{
					map.put(soaEntity.getStatementBillNo(), soaEntity);
				}
			}
		}
		return map;
	}
	
	
	/**
	 * @return  the statementOfAccountEntityDao
	 */
	public IStatementOfAccountEntityDao getStatementOfAccountEntityDao() {
		return statementOfAccountEntityDao;
	}

	
	/**
	 * @param statementOfAccountEntityDao the statementOfAccountEntityDao to set
	 */
	public void setStatementOfAccountEntityDao(
			IStatementOfAccountEntityDao statementOfAccountEntityDao) {
		this.statementOfAccountEntityDao = statementOfAccountEntityDao;
	}

	
	/**
	 * @return  the statementOfAccountDService
	 */
	public IStatementOfAccountDService getStatementOfAccountDService() {
		return statementOfAccountDService;
	}

	
	/**
	 * @param statementOfAccountDService the statementOfAccountDService to set
	 */
	public void setStatementOfAccountDService(
			IStatementOfAccountDService statementOfAccountDService) {
		this.statementOfAccountDService = statementOfAccountDService;
	}

	
	/**
	 * @return  the operatingLogService
	 */
	public IOperatingLogService getOperatingLogService() {
		return operatingLogService;
	}

	
	/**
	 * @param operatingLogService the operatingLogService to set
	 */
	public void setOperatingLogService(IOperatingLogService operatingLogService) {
		this.operatingLogService = operatingLogService;
	}

	/**
	 * @param billWriteoffService the billWriteoffService to set
	 */
	public void setBillWriteoffService(IBillWriteoffService billWriteoffService) {
		this.billWriteoffService = billWriteoffService;
	}
}
