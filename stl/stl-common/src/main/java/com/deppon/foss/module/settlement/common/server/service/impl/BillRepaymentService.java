package com.deppon.foss.module.settlement.common.server.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillRepaymentEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.IBillRepaymentService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillRepaymentConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillRepaymentDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffAmountDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 还款单服务
 * @author ibm-zhuwei
 * @date 2012-11-13 下午3:58:46
 */
public class BillRepaymentService implements IBillRepaymentService {
	
	private static final Logger logger = LogManager.getLogger(BillRepaymentService.class);
	
	private IBillRepaymentEntityDao billRepaymentEntityDao;
	
	// -------------------- getter/setter --------------------
	
	public void setBillRepaymentEntityDao(
			IBillRepaymentEntityDao billRepaymentEntityDao) {
		this.billRepaymentEntityDao = billRepaymentEntityDao;
	}

	// -------------------- write methods --------------------
	
	/**
	 * 生成还款单
	 * @author foss-wangxuemin
	 * @date Apr 19, 2013 4:08:15 PM
	 */
	private void add(BillRepaymentEntity entity){
		// FOSS生成的所有单据的初始化状态都为"N"
		entity.setIsInit(FossConstants.NO);
		// 如果返回记录数不等于1，则生成还款单异常
		int i = this.billRepaymentEntityDao.add(entity);
		if (i != 1) {
			throw new SettlementException("生成还款单出错");
		}
	}
	/** 
	 * 新增还款单
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-11-13 下午4:01:21
	 * @param entity
	 * @param currentInfo
	 */
	@Override
	public void addBillRepayment(BillRepaymentEntity entity, CurrentInfo currentInfo) {

		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getRepaymentNo())) {
			throw new SettlementException("新增还款单参数不能为空！");
		}
		
		logger.info("entering service, repaymentNo: " + entity.getRepaymentNo());
		
		//设置操作者信息
		Date date=new Date();
		entity.setCreateTime(date);
		entity.setCreateUserCode(currentInfo.getEmpCode());
		entity.setCreateUserName(currentInfo.getEmpName());
//		entity.setCreateOrgCode(currentInfo.getCurrentDeptCode());
//		entity.setCreateOrgName(currentInfo.getCurrentDeptName());
		entity.setModifyTime(date);
		entity.setModifyUserCode(currentInfo.getEmpCode());
		entity.setModifyUserName(currentInfo.getEmpName());	
		entity.setBillType(SettlementDictionaryConstants.BILL_REPAYMENT__BILL_TYPE__REPAYMENT);//单据类型
		
		//业务日期在记账日期之后 ，提示不能保存
		Date bussinessDate= entity.getBusinessDate();
		Date accountDate= entity.getAccountDate();
		if(bussinessDate.after(accountDate)){
			throw new SettlementException("记账日期小于业务日期，不能进行还款单操作！");
		}
		
		this.add(entity);
		
		logger.info("successfully exit service, repaymentNo: " + entity.getRepaymentNo());
	}
	
	/** 
	 * 红冲还款单
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-11-13 下午4:39:41
	 * @param entity
	 * @param currentInfo
	 */
	@Override
	public void writeBackBillRepayment(BillRepaymentEntity entity, CurrentInfo currentInfo) {

		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getRepaymentNo())) {
			throw new SettlementException("红冲还款单参数不能为空！");
		}
		
		logger.info("entering service, repaymentNo: " + entity.getRepaymentNo());

		//获取格式化到秒的时间
		Date now = new Date();
		
		BillRepaymentEntity updateEntity = new BillRepaymentEntity();
		
		// 作废旧单据
		updateEntity.setId(entity.getId());	// ID
		updateEntity.setAccountDate(entity.getAccountDate());	// 分区键
		updateEntity.setVersionNo(entity.getVersionNo());	// 版本号
		updateEntity.setActive(FossConstants.INACTIVE);
		updateEntity.setModifyTime(now);
		// 操作者信息
		updateEntity.setModifyUserCode(currentInfo.getEmpCode());
	    updateEntity.setModifyUserName(currentInfo.getEmpName());
		
		int i = billRepaymentEntityDao.updateByWriteBack(updateEntity);
		if (i != 1) {
			throw new SettlementException("红冲还款单出错");
		}
		
		// 生成红冲单
		BillRepaymentEntity newEntity = new BillRepaymentEntity();
		BeanUtils.copyProperties(entity, newEntity);
		
		newEntity.setId(UUIDUtils.getUUID());
		newEntity.setActive(FossConstants.INACTIVE);	// 设置还款单状态为无效
		newEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
		newEntity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__YES);
		newEntity.setAccountDate(now);
		newEntity.setCreateTime(now);
		newEntity.setModifyTime(now);
		newEntity.setCashConfirmTime(null);
		
		newEntity.setAmount(NumberUtils.multiply(newEntity.getAmount(), -1));
		newEntity.setTrueAmount(NumberUtils.multiply(newEntity.getTrueAmount(), -1));
		newEntity.setBverifyAmount(NumberUtils.multiply(newEntity.getBverifyAmount(), -1));
		
		// 操作者信息
		newEntity.setModifyUserCode(currentInfo.getEmpCode());
		newEntity.setModifyUserName(currentInfo.getEmpName());
		
		//收银状态改为提交
		newEntity.setStatus(SettlementDictionaryConstants.BILL_REPAYMENT__STATUS__SUBMIT);
		newEntity.setCashConfirmTime(null);
		newEntity.setCashConfirmUserCode(null);
		newEntity.setCashConfirmUserName(null);
		
		this.add(newEntity);
		
		logger.info("successfully exit service, repaymentNo: " + entity.getRepaymentNo());
	}
	
	/**
	 * 反核销-修改还款单的金额信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-29 下午2:44:39
	 * @param entity
	 * @param bverifyAmount
	 * @param currentInfo
	 * @return 
	 */
	@Override
	public void reverseWriteoffBillRepayment(BillRepaymentEntity entity, BigDecimal bverifyAmount, CurrentInfo currentInfo) {

		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getId())
				|| entity.getAccountDate() == null
				|| entity.getVersionNo() == null) {
			throw new SettlementException("反核销还款单参数不能为空！");
		}
		if (bverifyAmount == null
				|| bverifyAmount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new SettlementException("反核销还款单金额必须大于0");
		}
		
		logger.info("entering service, id: " + entity.getId());

		Date now = Calendar.getInstance().getTime();

		BillWriteoffAmountDto dto = new BillWriteoffAmountDto();
		BeanUtils.copyProperties(entity, dto);
		dto.setModifyTime(now);
		dto.setWriteoffAmount(bverifyAmount);

		entity.setModifyTime(now);
		
		// 操作者信息
		dto.setModifyUserCode(currentInfo.getEmpCode());
		dto.setModifyUserName(currentInfo.getEmpName());
		
		int i = this.billRepaymentEntityDao.updateByReverseWriteoff(dto);
		if (i != 1) {
			throw new SettlementException("反核销还款单出错");
		}

		entity.setVersionNo((short) (entity.getVersionNo() + 1));

		logger.info("successfully exit service, id: " + entity.getId());
	}


	/**
	 * 批量审核还款单 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-12 上午11:32:21
	 * @param  dto
	 * @param  currentInfo
	 */
	@Override
	public void auditBillRepayments(BillRepaymentDto dto, CurrentInfo currentInfo) {
		
		// 输入参数校验
		if (dto == null || CollectionUtils.isEmpty(dto.getBillRepayments())) {
			throw new SettlementException("批量审核还款单参数不能为空！");
		}
		
		logger.info("entering service, id: " + dto.getBillRepayments());
		
		dto.setActive(FossConstants.ACTIVE);
		dto.setAuditStatus(SettlementDictionaryConstants.BILL_REPAYMENT__AUDIT_STATUS__AUDIT_AGREE);
		dto.setAuditUserCode(currentInfo.getEmpCode());
		dto.setAuditUserName(currentInfo.getEmpName());
		
		// 操作者信息
		dto.setModifyTime(new Date());
		dto.setModifyUserCode(currentInfo.getEmpCode());
		dto.setModifyUserName(currentInfo.getEmpName());
		
		int i = this.billRepaymentEntityDao.updateByBatchAudit(dto);
		if (i != dto.getBillRepayments().size()) {
			throw new SettlementException("批量审核还款单失败");
		}
		
		logger.info("successfully exit service, id: " + dto.getBillRepayments());
	}

	/**
	 * 批量反审核还款单
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-12 上午11:32:38
	 * @param dto 
	 * @param currentInfo
	*/
	@Override
	public void reverseAuditBillRepayments(BillRepaymentDto dto, CurrentInfo currentInfo) {
		
		// 输入参数校验
		if (dto == null || CollectionUtils.isEmpty(dto.getBillRepayments())) {
			throw new SettlementException("批量反审核还款单参数不能为空！");
		}
		
		logger.info("entering service, id: " + dto.getBillRepayments());
		
		dto.setAuditStatus(SettlementDictionaryConstants.BILL_REPAYMENT__AUDIT_STATUS__NOT_AUDIT);
		dto.setActive(FossConstants.ACTIVE);
		
		// 操作者信息
		dto.setModifyTime(new Date());
		dto.setModifyUserCode(currentInfo.getEmpCode());
		dto.setModifyUserName(currentInfo.getEmpName());
		
		int i = this.billRepaymentEntityDao.updateByBatchAudit(dto);
		if (i != dto.getBillRepayments().size()) {
			throw new SettlementException("批量反审核还款单失败");
		}

		logger.info("successfully exit service, id: " + dto.getBillRepayments());
	}

	/** 
	 * 批量确认收银还款单
	 * @author ibm-zhuwei
	 * @date 2012-12-18 下午2:07:55
	 * @param dto
	 * @param currentInfo
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillRepaymentService#confirmCashierBillRepayments(com.deppon.foss.module.settlement.common.api.shared.dto.BillRepaymentDto, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void confirmCashierBillRepayments(BillRepaymentDto dto,
			CurrentInfo currentInfo) {
		
		// 输入参数校验
		if (dto == null || CollectionUtils.isEmpty(dto.getBillRepayments())) {
			throw new SettlementException("确认收银还款单参数不能为空！");
		}
		if (dto.getBillRepayments().size() > SettlementConstants.MAX_LIST_SIZE) {
			throw new SettlementException("确认收银还款单数量大于"
					+ SettlementConstants.MAX_LIST_SIZE + "不能进行操作。");
		}
		
		logger.info("entering service, ids: " + dto.getBillRepayments());
		
		Date now = new Date();

		// 操作者信息
		dto.setModifyTime(now);
		dto.setModifyUserCode(currentInfo.getEmpCode());
		dto.setModifyUserName(currentInfo.getEmpName());

		// 收银人信息
		dto.setStatus(SettlementDictionaryConstants.BILL_CASH_COLLECTION__STATUS__CONFIRM);
		dto.setCashConfirmTime(now);
		dto.setCashConfirmUserCode(currentInfo.getEmpCode());
		dto.setCashConfirmUserName(currentInfo.getEmpName());
		
		int i = this.billRepaymentEntityDao.updateByConfirmCashier(dto);
		
		if (i != dto.getBillRepayments().size()) {
			throw new SettlementException("确认收银还款单失败");
		}
		
		logger.info("successfully exit service, ids: " + dto.getBillRepayments());
		
	}

	// -------------------- read methods --------------------
	
	/**
     * 根据传入的一到多个还款单ID号，获取一到多条还款单信息
     * 
     * @author foss-pengzhen
     * @date 2012-10-12 下午6:55:54
     * @param repaymentNos   还款单号ID集合
     * @param active         是否有效
     * @return
     * @see
     */
	@Override
	public List<BillRepaymentEntity> queryByRepaymentIds(
			List<String> repaymentIds, String active) {
		// 输入参数校验
		if (CollectionUtils.isEmpty(repaymentIds)) {
			throw new SettlementException("查询还款单，输入的还款单Id号不能为空！");
		}
		
		logger.debug("entering service, repaymentIds: " + repaymentIds);
		
		return this.billRepaymentEntityDao.queryByRepaymentIds(repaymentIds,active);
	}
	
	/**
     * 根据传入的一到多个还款单号，获取一到多条还款单信息
     * 
     * @author dp-wujiangtao
     * @date 2012-10-12 下午6:55:54
     * @param repaymentNos   还款单号集合
     * @param active         是否有效
     * @return
     * @see
     */
	@Override
	public List<BillRepaymentEntity> queryByRepaymentNOs(
			List<String> repaymentNos, String active) {
		// 输入参数校验
		if (CollectionUtils.isEmpty(repaymentNos)) {
			throw new SettlementException("查询还款单，输入的还款单号不能为空！");
		}
		
		logger.debug("entering service, repaymentNos: " + repaymentNos);
		
		return this.billRepaymentEntityDao.queryByRepaymentNOs(repaymentNos,active);
	}

	/**
     * 根据传入的一个还款单号，获取一条还款单信息
     * 
     * @author dp-wujiangtao
     * @date 2012-10-12 下午6:55:54
     * @param repaymentNos   还款单号
     * @param active         是否有效
     * @return
     * @see
     */
	@Override
	public BillRepaymentEntity queryByRepaymentNO(String repaymentNo,String active) {
		// 输入参数校验
		if (StringUtils.isEmpty(repaymentNo)) {
			throw new SettlementException("查询还款单，输入的还款单号不能为空！");
		}
		
		logger.debug("entering service, repaymentNo: " + repaymentNo);
		
		List<BillRepaymentEntity> list = this.billRepaymentEntityDao
				.queryByRepaymentNOs(Arrays.asList(repaymentNo), active);
		
		BillRepaymentEntity entity = null;
		
		if (CollectionUtils.isNotEmpty(list)) {
			if (FossConstants.ACTIVE.equals(active)){
				for (BillRepaymentEntity repEntity : list){
					if(repEntity.getAmount().compareTo(BigDecimal.ZERO) > 0){
						entity = repEntity;
						break;
					}
				}
			} else {
				entity = list.get(0);
			}
		}
		
		return entity;
	}
	
	/**
	 * 根据传入的一到多个来源单号，获取一到多条还款单信息
	 * 
	 * @author dp-wujiangtao
	 * @date 2012-10-17 下午8:03:04
	 * @param sourceBillNos
	 * @param active
	 * @return 
	 */
	@Override
	public List<BillRepaymentEntity> queryBySourceBillNOs(
			List<String> sourceBillNos, String active) {

		// 输入参数校验
		if (CollectionUtils.isEmpty(sourceBillNos)){
			throw new SettlementException("查询还款单，输入的来源单号不能为空！");
		}
		
		logger.debug("entering service, sourceBillNos: " + sourceBillNos);
		
		return this.billRepaymentEntityDao.queryBySourceBillNOs(sourceBillNos, active);
	}
	
	/**
	 * 
     * 根据传入的到达实收单编号，查询是否存在有效的还款单
     * 
     * [到达实收单编号存储在还款单的来源单号属性]
	 * @author dp-wujiangtao
	 * @date 2012-10-17 下午8:44:08
	 * @param sourceBillNo
	 * @param sourceBillType
	 * @return 
	 */
	@Override
	public String queryBySourceBillNO(String sourceBillNo, String sourceBillType) {

		// 输入参数校验
		if(StringUtils.isEmpty(sourceBillNo)){
			throw new SettlementException("查询还款单，输入的来源单号不能为空！");
		}
		
		logger.debug("entering service, sourceBillNo: " + sourceBillNo);
		
		return this.billRepaymentEntityDao.queryBySourceBillNO(sourceBillNo,sourceBillType);
	}

	/**
	 * 根据外发单号和外发代理编码是否已存在有效的还款单记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-19 下午7:12:23
	 * @param dto
	 * @return
	 */
	@Override
	public int queryBillRepaymentByExternalBillNo(BillRepaymentConditionDto dto) {
		
		//来源单号或外发单号，其一不能为空
		if(dto!=null&&(StringUtils.isNotEmpty(dto.getSourceBillNo())||StringUtils.isNotEmpty(dto.getExternalBillNo()))&&StringUtils.isNotEmpty(dto.getPartailLineAgentCode())){
			dto.setActive(FossConstants.ACTIVE);
			dto.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
			return this.billRepaymentEntityDao.queryBillRepaymentByExternalBillNo(dto);
		}else{
			throw new SettlementException("查询偏线外发还款单参数不能为空！");
		}
	}

	/**
	 * 查询符合条件的还款单信息
	 *  
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-21 上午8:27:13
	 * @param dto
	 * @return
	 */
	@Override
	public List<BillRepaymentEntity> queryBillRepaymentByCondition(
			BillRepaymentConditionDto dto) {
		
		// 输入参数校验
		if (dto == null) {
			throw new SettlementException("查询还款单，输入的参数不能为空！");
		}
		if (StringUtils.isEmpty(dto.getSourceBillNo())
				&& StringUtils.isEmpty(dto.getRepaymentNo())
				&& StringUtils.isEmpty(dto.getOnlinePaymentNo())
				&& StringUtils.isEmpty(dto.getWaybillNo())
				) {
			throw new SettlementException("查询还款单输入的单据号不能为空！");
		}
		logger.debug("entering service");
		
		return this.billRepaymentEntityDao.queryBillRepaymentByCondition(dto);
	}

	/**
	 * 根据来源单号集合和部门编码集合，查询还款单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-22 上午9:35:54
	 * @param sourceBillNos
	 * @param orgCodes
	 * @param active
	 * @return
	 */
	@Override
	public List<BillRepaymentEntity> queryBySourceBillNOsAndOrgCodes(
			List<String> sourceBillNos, List<String> orgCodes, String active,CurrentInfo currentInfo) {
		if(CollectionUtils.isEmpty(sourceBillNos)){
			throw new SettlementException("根据来源单号集合和部门编码集合，查询还款单信息参数不能为空！");
		}
		return this.billRepaymentEntityDao.queryBySourceBillNOsAndOrgCodes(sourceBillNos, orgCodes, active,currentInfo);
	}

	/** 
	 * 当还款单全部金额都反核消后，将原还款单作废
	 * @author foss-qiaolifeng
	 * @date 2013-5-27 上午10:08:34
	 * @param entity
	 * @param currentInfo
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillRepaymentService#revereWriteoffBillRepayment(com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void revereWriteoffBillRepayment(BillRepaymentEntity entity,
			CurrentInfo currentInfo) {
		
		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getRepaymentNo())) {
			throw new SettlementException("反核消时作废原还款单参数不能为空！");
		}

		logger.info("entering service, repaymentNo: " + entity.getRepaymentNo());

		// 获取格式化到秒的时间
		Date now = new Date();

		BillRepaymentEntity updateEntity = new BillRepaymentEntity();

		// 作废旧单据
		updateEntity.setId(entity.getId()); // ID
		updateEntity.setAccountDate(entity.getAccountDate()); // 分区键
		updateEntity.setVersionNo(entity.getVersionNo()); // 版本号
		updateEntity.setActive(FossConstants.INACTIVE);
		updateEntity.setModifyTime(now);
		// 操作者信息
		updateEntity.setModifyUserCode(currentInfo.getEmpCode());
		updateEntity.setModifyUserName(currentInfo.getEmpName());

		int i = billRepaymentEntityDao.updateByWriteBack(updateEntity);
		if (i != 1) {
			throw new SettlementException("反核消时作废原还款出错");
		}

	}
	
	/**
	 * 根据应收单号查询还款单信息
	 * @author 231434-foss-bieyexiong
	 * @date 2016-10-03 
	 */
	public List<BillRepaymentEntity> queryRepaymentByReceivableNo(String receivableNo,String active,String writeoffType){
		
		return billRepaymentEntityDao.queryRepaymentByReceivableNo(receivableNo, active, writeoffType);
	}

}
