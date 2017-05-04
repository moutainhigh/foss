package com.deppon.foss.module.settlement.common.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillPayableEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IClaimStatusMsgService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.server.service.IWaybillChangeMsgService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.ClaimStatusMsgEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillChangeMsgEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffAmountDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountUpdateDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 应付单服务
 *
 * @author ibm-zhuwei
 * @date 2012-12-25 下午4:04:12
 */
public class BillPayableService implements IBillPayableService {

	//打印日志使用
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BillPayableService.class);

	/**
	 * 应付单Dao
	 */
	private IBillPayableEntityDao billPayableEntityDao;

	/**
	 * 对账单Service
	 */
	private IStatementOfAccountService statementOfAccountService;

	/**
	 * 理赔状态消息Service
	 */
	private IClaimStatusMsgService claimStatusMsgService;

	/**
	 * 财务变更消息实体Service
	 */
	private IWaybillChangeMsgService waybillChangeMsgService;
    /**
     * 在线通知
     */
    private IMessageService messageService;

    private static final int NUM_THOUSAND = 1000;

	public IMessageService getMessageService() {
		return messageService;
	}


	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	// -------------------- getter/setter --------------------

	/**
	 * @param billPayableEntityDao the billPayableEntityDao to set
	 */
	public void setBillPayableEntityDao(IBillPayableEntityDao billPayableEntityDao) {
		this.billPayableEntityDao = billPayableEntityDao;
	}

	/**
	 * @param statementOfAccountService the statementOfAccountService to set
	 */
	public void setStatementOfAccountService(
			IStatementOfAccountService statementOfAccountService) {
		this.statementOfAccountService = statementOfAccountService;
	}

	/**
	 * @param claimStatusMsgService the claimStatusMsgService to set
	 */
	public void setClaimStatusMsgService(
			IClaimStatusMsgService claimStatusMsgService) {
		this.claimStatusMsgService = claimStatusMsgService;
	}

	/**
	 * @param waybillChangeMsgService the waybillChangeMsgService to set
	 */
	public void setWaybillChangeMsgService(
			IWaybillChangeMsgService waybillChangeMsgService) {
		this.waybillChangeMsgService = waybillChangeMsgService;
	}

	// -------------------- write methods --------------------

	/**
	 * 生成应付单
     *
	 * @author foss-wangxuemin
	 * @date Apr 19, 2013 4:00:31 PM
	 */
	private void add(BillPayableEntity entity) {
		// FOSS生成的所有单据的初始化状态都为"N"
		entity.setIsInit(FossConstants.NO);
		// 如果返回不等于1，则新增异常
		int i = billPayableEntityDao.add(entity);
		if (i != 1) {
			throw new SettlementException("生成应付单出错");
		}
	}

	/**
	 * 新增应付单
     *
     * @param entity      应付单实体
     * @param currentInfo 操作者
	 * @author ibm-zhuwei
	 * @date 2012-10-25 下午4:38:10
	 */
	@Override
	public void addBillPayable(BillPayableEntity entity, CurrentInfo currentInfo) {

		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getSourceBillNo())) {
			throw new SettlementException("新增应付单参数不能为空！");
		}

		LOGGER.info("entering service, sourceBillNo: "
				+ entity.getSourceBillNo());

		// 单据信息校验

		//来源单号信息不能为空
		validaBillPayableEntity(entity);

		// 业务日期在记账日期之后 ，提示不能保存
		Date bussinessDate = entity.getBusinessDate();
		Date accountDate = entity.getAccountDate();
		
		//业务日期在记账日期之后，提示不能进行操作
		if (bussinessDate.after(accountDate)) {
			throw new SettlementException("记账日期小于业务日期，不能进行新增应付单操作！");
		}
		
		// 对账单设置为默认值
		entity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO); 
		
		//付款单号为空时，设置为N/A
		if(StringUtils.isEmpty(entity.getPaymentNo())){
			// 付款单号设置为默认值
			entity.setPaymentNo(SettlementConstants.DEFAULT_BILL_NO); 
		}
		
		// 设置操作者信息
		validaPayableEntity(entity, currentInfo);
		
		LOGGER.info("successfully exit service, sourceBillNo: "
				+ entity.getSourceBillNo());
	}


	private void validaBillPayableEntity(BillPayableEntity entity) {
		if (StringUtils.isEmpty(entity.getSourceBillNo())) {
			throw new SettlementException("来源单据号为空，不能进行新增应付单操作！");
		}

		//来源单据类型
		if (StringUtils.isEmpty(entity.getSourceBillType())) {
			throw new SettlementException("来源单据类型为空，不能进行新增应付单操作！");
		}

		//应付单的单据子类型不能为空
		if (StringUtils.isEmpty(entity.getBillType())) {
			throw new SettlementException("应付单类型为空，不能进行新增应付单操作！");
		}

		//应付单的审核状态不能为空
		if (StringUtils.isEmpty(entity.getApproveStatus())) {
			throw new SettlementException("应付单审核状态为空，不能进行新增应付单操作！");
		}

		// 金额校验
		if (entity.getAmount() == null) {
			throw new SettlementException("应付单金额为空，不能进行新增应付单操作！");
		}

		//金额不能为空，且金额必须大于0
		if (entity.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new SettlementException("应付金额小于等于0，不能进行新增应付单操作");
		}

		//应付单的核销金额或未核销金额都不能为空，且金额不能为负数
		if (entity.getVerifyAmount() == null || entity.getUnverifyAmount() == null) {
			throw new SettlementException("核销金额为空，不能进行新增应付单操作");
		}
		if (entity.getUnverifyAmount().compareTo(BigDecimal.ZERO) < 0) {
			throw new SettlementException("未核销金额为负数，不能进行新增应付单操作");
		}
		if (entity.getVerifyAmount().add(entity.getUnverifyAmount()).compareTo(entity.getAmount()) != 0) {
			throw new SettlementException("未核销与核销金额之和不等于总金额，不能进行新增应付单操作");
		}

		// 业务信息校验
		if (StringUtils.isEmpty(entity.getPayableOrgCode())) {
			throw new SettlementException("应付部门编码为空，不能进行新增应付单操作");
		}

		//应付单生效状态不能为空
		if (StringUtils.isEmpty(entity.getEffectiveStatus())) {
			throw new SettlementException("生效状态为空，不能进行新增应付单操作");
		}

		//应付单支付状态不能为空
		if (StringUtils.isEmpty(entity.getPayStatus())) {
			throw new SettlementException("支付状态为空，不能进行新增应付单操作");
		}

		//应付单冻结状态不能为空
		if (StringUtils.isEmpty(entity.getFrozenStatus())) {
			throw new SettlementException("冻结状态为空，不能进行新增应付单操作");
		}

		// 业务日期不能为空
		if (entity.getBusinessDate() == null) {
			throw new SettlementException("业务日期为空，不能进行新增应付单操作");
		}

		//记账日期不能为空
		if (entity.getAccountDate() == null) {
			throw new SettlementException("记账日期为空，不能进行新增应付单操作");
		}
	}


	private void validaPayableEntity(BillPayableEntity entity,
			CurrentInfo currentInfo) {
		Date date = new Date();

		//创建时间
		entity.setCreateTime(date);

		//操作者编码
		entity.setCreateUserCode(currentInfo.getEmpCode());

		//操作者名称
		entity.setCreateUserName(currentInfo.getEmpName());

		//创建部门编码
		entity.setCreateOrgCode(currentInfo.getCurrentDeptCode());

		//创建部门名称
		entity.setCreateOrgName(currentInfo.getCurrentDeptName());

		//修改时间
		entity.setModifyTime(date);

		//修改用户编码
		entity.setModifyUserCode(currentInfo.getEmpCode());

		//修改用户名称
		entity.setModifyUserName(currentInfo.getEmpName());
        //判断是否有值 无值默认设置为N
        if (null == entity.getFactoring() || StringUtils.isEmpty(entity.getFactoring())) {
            entity.setFactoring(FossConstants.NO);
        }

		// FOSS生成的所有单据的初始化状态都为"N"
		entity.setIsInit(FossConstants.NO);

		this.add(entity);

		// 单据的运单号和来源单号相同,新增应付单时插入财务变更消息表 +1
		if (StringUtils.isNotEmpty(entity.getWaybillNo())
				&& (SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__WAYBILL
						.equals(entity.getSourceBillType()) || SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__EXCEPTION
                .equals(entity.getSourceBillType()))) {
			// 添加财务变更消息方法
			this.addChangeMsg(
					entity.getWaybillNo(),
					entity.getPayableNo(),
					SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESSING, // 消息类型：新增
					date);
		}
	}

	/**
	 * 生效应付单
     *
     * @param entity      应付单实体
     * @param signDate    签收时间
     * @param currentInfo 操作者信息
	 * @author ibm-zhuwei
	 * @date 2012-10-22 下午2:15:20
	 */
	@Override
	public void enableBillPayable(BillPayableEntity entity, Date signDate,
			CurrentInfo currentInfo) {

		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getId())
				|| entity.getAccountDate() == null
				|| entity.getVersionNo() == null) {
			throw new SettlementException("生效应付单参数不为空！");
		}

		LOGGER.info("entering service, id: " + entity.getId());

        //初始化日期格式化到秒
        Date now = Calendar.getInstance().getTime();

        //签收时间
        entity.setSignDate(signDate);

        //生效状态-设置为有效
        entity.setEffectiveStatus(FossConstants.YES);

        //生效日期
        entity.setEffectiveDate(now);

        //生效人编码
        entity.setEffectiveUserCode(currentInfo.getEmpCode());

        //生效人名称
        entity.setEffectiveUserName(currentInfo.getEmpName());

        //修改时间
        entity.setModifyTime(now);

        // 操作者信息
        entity.setModifyUserCode(currentInfo.getEmpCode());

        //修改人名称
        entity.setModifyUserName(currentInfo.getEmpName());

        int i = billPayableEntityDao.updateByTakeEffect(entity);

		//dao层返回值不等于1时，提示保存异常信息
		if (i != 1) {
			throw new SettlementException("生效应付单出错");
		}
		//如果审核退代收货款生效，则通知始发部门及时审核
        if (SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE
                .equals(entity.getCodType())) {
		//发送站点消息
            sendMassage(entity, currentInfo);
		}
		LOGGER.info("successfully exit service, id: " + entity.getId());
	}


	/**
	 * 失效应付单
     *
     * @param entity      应付单实体
     * @param currentInfo 操作者信息
	 * @author ibm-zhuwei
	 * @date 2012-10-22 下午2:15:20
	 */
	@Override
	public void disableBillPayable(BillPayableEntity entity,
			CurrentInfo currentInfo) {

		// 输入参数校验
		//实体ID和记账日期和版本号不能为空
		if (entity == null || StringUtils.isEmpty(entity.getId())
				|| entity.getAccountDate() == null
				|| entity.getVersionNo() == null) {
			throw new SettlementException("失效应付单参数不能为空！");
		}

		LOGGER.info("entering service, id: " + entity.getId());

		// 以下应付单审核后才能核销
		// 长途外请车/整车成本、航空公司成本、空运出发代理成本、空运中转代理成本、空运到达代理成本、空运其他应付、偏线成本
		if (Arrays.asList(SettlementConstants.AUDIT_OR_UNAUDIT_TYPES).contains(entity.getBillType()) &&
				SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE.equals(entity.getApproveStatus())) {
			throw new SettlementException("应付单必须先反审核才能进行修改");
		}

		//获取一个格式化到秒的时间对象
		Date now = Calendar.getInstance().getTime();

		//生效状态：未生效
		entity.setEffectiveStatus(FossConstants.NO);

		//失效时时间设置为空
		entity.setEffectiveDate(null);

		//生效人编码设置为空
		entity.setEffectiveUserCode(null);

		//生效人名称设置为空
		entity.setEffectiveUserName(null);

		//修改时间
		entity.setModifyTime(now);

		// 操作者信息
		entity.setModifyUserCode(currentInfo.getEmpCode());

		//修改人名称
		entity.setModifyUserName(currentInfo.getEmpName());

		int i = billPayableEntityDao.updateByTakeEffect(entity);
		if (i != 1) {
			throw new SettlementException("失效应付单出错");
		}

		LOGGER.info("successfully exit service, id: " + entity.getId());
	}

	/**
	 * 红冲应付单
     *
     * @param entity
     * @param currentInfo
	 * @author ibm-zhuwei
	 * @date 2012-10-23 下午3:22:16
	 */
	@Override
	public void writeBackBillPayable(BillPayableEntity entity,
			CurrentInfo currentInfo) {

		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getSourceBillNo())) {
			throw new SettlementException("红冲应付单参数不能为空！");
		}

		LOGGER.info("entering service, sourceBillNo: "
				+ entity.getSourceBillNo());

		// 已核销,不能红冲
		if (entity.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
			throw new SettlementException("应付单已核销,不能红冲应付单");
		}

		// 以下应付单审核后才能核销
		// 长途外请车/整车成本、航空公司成本、空运出发代理成本、空运中转代理成本、空运到达代理成本、空运其他应付、偏线成本
		if (Arrays.asList(SettlementConstants.AUDIT_OR_UNAUDIT_TYPES).contains(entity.getBillType()) &&
				SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE.equals(entity.getApproveStatus())) {
			throw new SettlementException("应付单必须先反审核才能进行红冲操作");
		}

		// 校验对账单
		if (StringUtils.isEmpty(entity.getStatementBillNo())
				|| SettlementConstants.DEFAULT_BILL_NO.equals(entity
						.getStatementBillNo())) {
			// do nothing
		} else {
			//对账单号不为空时，需要查询对账单记录，
			//看对账单记录是否已经被确认了。
			List<String> list = statementOfAccountService
					.queryConfirmStatmentOfAccount(Arrays.asList(entity
							.getStatementBillNo()));
            if (CollectionUtils.isEmpty(list)) {
				// 对账单未确认,则更新对账相关信息
				StatementOfAccountUpdateDto dto = new StatementOfAccountUpdateDto();
				dto.setPayableEntityList(Arrays.asList(entity));
				statementOfAccountService.updateStatementAndDetailForRedBack(
						dto, currentInfo);
            } else {
				// 对账单已确认则不允许红冲单据
				throw new SettlementException("该单存在相应的客户对账单已确认、核销、付款、还款，不能进行红冲");
			}
		}

		//获取一个时间
		Date now = Calendar.getInstance().getTime();

		BillPayableEntity updateEntity = new BillPayableEntity();

		// 作废旧单据 // ID
        updateEntity.setId(entity.getId());

		// 分区键
        updateEntity.setAccountDate(entity.getAccountDate());

		// 版本号
        updateEntity.setVersionNo(entity.getVersionNo());

		//是否有效-无效
		updateEntity.setActive(FossConstants.INACTIVE);

		//修改时间
		updateEntity.setModifyTime(now);

		// 修改人编码
		updateEntity.setModifyUserCode(currentInfo.getEmpCode());

		//修改人名称
		updateEntity.setModifyUserName(currentInfo.getEmpName());

		int i = billPayableEntityDao.updateByWriteBack(updateEntity);
		if (i != 1) {
			throw new SettlementException("红冲应付单出错");
		}

		// 生成红冲单
		BillPayableEntity newEntity = new BillPayableEntity();

		//对象之间进行拷贝
		BeanUtils.copyProperties(entity, newEntity);

		//ID
		newEntity.setId(UUIDUtils.getUUID());

	    //是否有效
		newEntity.setActive(FossConstants.INACTIVE);

		//版本号
		newEntity.setVersionNo(FossConstants.INIT_VERSION_NO);

		//是否红单
		newEntity
				.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__YES);

		//设置记账日期
		newEntity.setAccountDate(now);

		//创建时间
		newEntity.setCreateTime(now);

		//修改时间
		newEntity.setModifyTime(now);

		//金额-.negate()是和原来金额相反
		newEntity.setAmount(newEntity.getAmount().negate());

		//已核销金额负数
		newEntity.setVerifyAmount(newEntity.getVerifyAmount().negate());

		//未核销金额设置为负数
		newEntity.setUnverifyAmount(newEntity.getUnverifyAmount().negate());

        // 修改人编码
		newEntity.setModifyUserCode(currentInfo.getEmpCode());

		//修改人名称
		newEntity.setModifyUserName(currentInfo.getEmpName());

		this.add(newEntity);

		// 单据的运单号和来源单号相同,红冲应付单时插入财务变更消息表 -1
		if (StringUtils.isNotEmpty(entity.getWaybillNo())
				&& (SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__WAYBILL
						.equals(entity.getSourceBillType()) || SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__EXCEPTION
                .equals(entity.getSourceBillType()))) {

			// 调用插入财务变更消息方法
			this.addChangeMsg(
					entity.getWaybillNo(),
					entity.getPayableNo(),
					SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESS_DONE, // 红冲代表业务完结-1
					now);
		}

		LOGGER.info("successfully exit service, sourceBillNo: "
				+ entity.getSourceBillNo());

	}

	/**
	 * 应付单核销/反核销
     *
     * @param entity         应付单实体
     * @param writeoffAmount 核销金额
     * @param currentInfo    操作者
	 * @author ibm-zhuwei
	 * @date 2012-10-19 上午11:13:37
	 */
	@Override
	public void writeoffBillPayable(BillPayableEntity entity,
			BigDecimal writeoffAmount, CurrentInfo currentInfo) {

		//核销时writeoffAmount里面的金额大于0；反核销时writeoffAmount小于0为负数

		//应付单实体和记账日期和版本号不能为空，修改时需要
		if (entity == null || StringUtils.isEmpty(entity.getId())
				|| entity.getAccountDate() == null
				|| entity.getVersionNo() == null) {
			throw new SettlementException("核销应付单参数不能为空！");
		}

		// 输入参数校验
		if (writeoffAmount == null || BigDecimal.ZERO.equals(writeoffAmount)) {
			throw new SettlementException("核销金额为空");
		}

		LOGGER.info("entering service, id: " + entity.getId());

		// 应付单审核后才能核销
		if (!SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE.equals(entity.getApproveStatus())) {
			throw new SettlementException("应付单审核后才能进行核销操作");
		}

		//获取一个格式化到秒的时间
		validaPayable(entity, writeoffAmount, currentInfo);
		
		LOGGER.info("successfully exit service, id: " + entity.getId());
	}


	private void validaPayable(BillPayableEntity entity,
			BigDecimal writeoffAmount, CurrentInfo currentInfo) {
		Date now = Calendar.getInstance().getTime();

		//定义核销dto
		BillWriteoffAmountDto dto = new BillWriteoffAmountDto();
		BeanUtils.copyProperties(entity, dto);

		//设置修改时间
		dto.setModifyTime(now);

		//设置核销金额
		dto.setWriteoffAmount(writeoffAmount);

		// 操作者信息
		dto.setModifyUserCode(currentInfo.getEmpCode());

		//设置修改人名称
		dto.setModifyUserName(currentInfo.getEmpName());

		int i = billPayableEntityDao.updateByWriteoff(dto);
		if (i != 1) {
            throw new SettlementException(entity.getPayableNo() + ":应付单金额已发生变化，请刷新后再试");
		}

		// 核销之后，其他地方还需要使用，为了保持java方法中的版本号和数据库中的版本号统一，在这里+1
		entity.setVersionNo((short) (entity.getVersionNo() + 1));


		// 运单号和来源单号相同、生成财务变更消息和理赔支付状态消息代码
		if (StringUtils.isNotEmpty(entity.getWaybillNo())
				&& (SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__WAYBILL
						.equals(entity.getSourceBillType()) || SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__EXCEPTION
                .equals(entity.getSourceBillType()))) {

			validaBill(entity, writeoffAmount, now);
		}
	}


	private void validaBill(BillPayableEntity entity,
			BigDecimal writeoffAmount, Date now) {
		// 单据完成核销时：未核销金额等于0
		if (entity.getUnverifyAmount() != null
				&& entity.getUnverifyAmount().compareTo(BigDecimal.ZERO) == 0) {
			
			//核销成功应付单之后，如果是理赔应付单，生成理赔状态消息数据
			if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM
					.equals(entity.getBillType())) {
				this.addClaimStatusMsg(entity.getWaybillNo(), now);
			}
			
			//财务变更消息---核销完结
			this.addChangeMsg(entity.getWaybillNo(), entity.getPayableNo(),
					SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESS_DONE//完结-1
					, now);
		} else if (writeoffAmount.compareTo(BigDecimal.ZERO) < 0) {
			// 反核销时：未核销金额等于正的反核销的金额writeoffAmount
			
			//posWriteoffAmount  是writeoffAmount乘以-1得到正数
			BigDecimal posWriteoffAmount = writeoffAmount.negate();
			
			//或可以用writeoffAmount和未核销金额相加等于0
			if (entity.getUnverifyAmount() != null
					&& entity.getUnverifyAmount().compareTo(
							posWriteoffAmount) == 0) {
			
				//财务变更消息---反核销操作
				this.addChangeMsg(entity.getWaybillNo(), entity.getPayableNo(),
						SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESSING//反操作+1
						, now);
			}
		}
	}

	/**
	 * 新增理赔状态消息
     *
     * @param waybillNo
     * @param date
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-4 下午5:09:20
	 */
    private void addClaimStatusMsg(String waybillNo, Date date) {
        ClaimStatusMsgEntity clMsg = new ClaimStatusMsgEntity();

		//ID
		clMsg.setId(UUIDUtils.getUUID());

		//运单号
		clMsg.setWaybillNo(waybillNo);

		//消息发生日期
		clMsg.setMsgDate(date);

		//消息动作--核销成功
		clMsg.setMsgAction(SettlementDictionaryConstants.
				CLAIM_STATUS_MSG__MSG_ACTION__PASS);

		clMsg.setMsgContent("消息正文内容");//消息正文

		//消息状态---新增
		clMsg.setMsgStatus(SettlementDictionaryConstants.
				CLAIM_STATUS_MSG__MSG_STATUS__NEW);
		this.claimStatusMsgService.addClaimStatusMsg(clMsg);
	}

	/**
	 * 新增财务变更消息
     *
	 * @param waybillNo
	 * @param payableNo //应付单号
	 * @param msgAction
	 * @param date
     * @author 099995-foss-wujiangtao
     * @date 2012-12-5 上午10:46:37
	 */
    private void addChangeMsg(String waybillNo, String payableNo, String msgAction, Date date) {
        WaybillChangeMsgEntity entity = new WaybillChangeMsgEntity();

		//ID
		entity.setId(UUIDUtils.getUUID());

		//运单号
		entity.setWaybillNo(waybillNo);

		//消息发生日期
		entity.setMsgDate(date);

		//消息动作
		entity.setMsgAction(msgAction);

		//来源单据类型-应付单
		entity.setSourceBillType(SettlementDictionaryConstants.
				WAYBILL_CHANGE_MSG__SOURCE_BILL_TYPE__PAYABLE);

		//来源单号-应付单号
		entity.setSourceBillNo(payableNo);
		this.waybillChangeMsgService.addChangeMsg(entity);
	}

	/**
	 * 批量审核应付单
     *
	 * @param dto
	 * @param currentInfo
	 * @return
     * @author 099995-foss-wujiangtao
     * @date 2012-11-1 上午8:36:19
	 */
	@Override
	public void batchAuditBillPayable(BillPayableDto dto,
			CurrentInfo currentInfo) {

		// 输入参数校验
		if (dto == null || CollectionUtils.isEmpty(dto.getBillPayables())) {
			throw new SettlementException("批量审核应付单参数不能为空！");
		}
		if (dto.getBillPayables().size() > SettlementConstants.MAX_LIST_SIZE) {
			throw new SettlementException("批量审核的应付单数量大于"
					+ SettlementConstants.MAX_LIST_SIZE + "不能进行审核操作。");
		}

		LOGGER.info("entering service, ids: " + dto.getBillPayables());

		//是否有效：有效
		dto.setActive(FossConstants.ACTIVE);

		//限制条件状态
		dto.setConApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT);

		// 设置审核状态为已审核
		dto.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);

		//审核时间
		dto.setAuditDate(new Date());

		//审核人编码
		dto.setAuditUserCode(currentInfo.getEmpCode());

		//审核人名称
		dto.setAuditUserName(currentInfo.getEmpName());


		// 操作者信息
		dto.setModifyTime(new Date());

		//修改人编码
		dto.setModifyUserCode(currentInfo.getEmpCode());

		//修改人名称
		dto.setModifyUserName(currentInfo.getEmpName());

		int i = this.billPayableEntityDao.updateByBatchAudit(dto);

		// 批量操作返回的不一定是1
		if (i != dto.getBillPayables().size()) {
			throw new SettlementException("批量审核应付单失败");
		}

		LOGGER.info("successfully exit service, ids: " + dto.getBillPayables());

	}

	/**
	 * 批量反审核应付单
     *
	 * @param dto
	 * @param currentInfo
	 * @return
     * @author 099995-foss-wujiangtao
     * @date 2012-11-1 上午8:36:50
	 */
	@Override
	public void batchReverseAuditBillPayable(BillPayableDto dto,
			CurrentInfo currentInfo) {

		// 输入参数校验
		if (dto == null || CollectionUtils.isEmpty(dto.getBillPayables())) {
			throw new SettlementException("批量反审核的应付单参数不能为空");
		}

		//一次性操作的数据不能大于1000条记录
		if (dto.getBillPayables().size() > SettlementConstants.MAX_LIST_SIZE) {
			throw new SettlementException("批量反审核的应付单数量大于"
					+ SettlementConstants.MAX_LIST_SIZE + "不能进行反审核操作。");
		}

		LOGGER.info("entering service, ids: " + dto.getBillPayables());

		//是否有效
		dto.setActive(FossConstants.ACTIVE);

		//支付状态：反审核时设置为已审核
		dto.setConApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);

		// 设置审核状态为未审核
		dto.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT);

		//审核时间
		dto.setAuditDate(null);

		//审核人code
		dto.setAuditUserCode("");

		//审核人名称
		dto.setAuditUserName("");

		// 操作者信息
		dto.setModifyTime(new Date());

		//修改人编码
		dto.setModifyUserCode(currentInfo.getEmpCode());

		//修改人名称
		dto.setModifyUserName(currentInfo.getEmpName());

		int i = this.billPayableEntityDao.updateByBatchAudit(dto);

		if (i != dto.getBillPayables().size()) {
			throw new SettlementException("批量反审核应付单失败");
		}

		LOGGER.info("successfully exit service, ids: " + dto.getBillPayables());
	}

	/**
	 * 冻结应付单
     *
     * @param dto
     * @param currentInfo
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-2 下午6:04:37
	 */
	@Override
	public void frozenBillPayable(BillPayableEntity entity,
			CurrentInfo currentInfo) {

		// 输入参数校验，ID和记账日期和版本号都不能为空
		if (entity == null || StringUtils.isEmpty(entity.getId())
				|| entity.getAccountDate() == null
				|| entity.getVersionNo() == null) {
			throw new SettlementException("冻结应付单参数不能为空！");
		}

		LOGGER.info("entering service, id: " + entity.getId());

		//初始化一个格式化到秒的时间
		Date now = Calendar.getInstance().getTime();

		//设置修改时间
		entity.setModifyTime(now);

		// 设置冻结状态-已冻结
		entity.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__FROZEN);

		//修改人编码 操作者信息
		entity.setModifyUserCode(currentInfo.getEmpCode());

		//修改人名称
		entity.setModifyUserName(currentInfo.getEmpName());

		int i = this.billPayableEntityDao.updateByFrozen(entity);
		if (i != 1) {
			throw new SettlementException("冻结应付单失败。");
		}

		LOGGER.info("successfully exit service, id: " + entity.getId());
	}

	/**
	 * 批量冻结应付单
	 *
     * @param entity
     * @param currentInfo
	 * @author foss-guxinhua
	 * @date 2013-5-3 下午5:43:50
	 */
	@Override
    public void frozenBillPayableByBatch(BillPayableDto dto, CurrentInfo currentInfo) {
		LOGGER.info("批量冻结应付单开始");
        if (dto != null && CollectionUtils.isNotEmpty(dto.getBillPayables())) {

			if (dto.getBillPayables().size() > SettlementConstants.MAX_LIST_SIZE) {
				throw new SettlementException("批量冻结应付单,批量冻结数量大于"
						+ SettlementConstants.MAX_LIST_SIZE + "，不能进行操作");
			}

			int updateRows = billPayableEntityDao.updateByFrozenByBatch(dto);
			if (updateRows != dto.getBillPayables().size()) {
				LOGGER.error("更新失败，更新行数不唯一!");
				throw new SettlementException("更新失败，更新行数不唯一!");
			}

		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
		LOGGER.info("批量冻结应付单结束");
	}

	/**
	 * 取消冻结应付单
	 * 和设置应付单的状态为：未支付
     *
     * @param dto
     * @param currentInfo
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-2 下午6:04:51
	 */
	@Override
	public void cancelFrozenBillPayable(BillPayableEntity entity,
			CurrentInfo currentInfo) {

		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getId())
				|| entity.getAccountDate() == null
				|| entity.getVersionNo() == null) {
			throw new SettlementException("取消冻结应付单参数不能为空！");
		}

		LOGGER.info("entering service, id: " + entity.getId());

		//初始化一个格式化到秒的时间
		Date now = Calendar.getInstance().getTime();

		// 设置冻结状态为-未冻结
		entity.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);

		// 修改操作
		entity.setModifyTime(now);

		//修改人编码
		entity.setModifyUserCode(currentInfo.getEmpCode());

		//修改人名称
		entity.setModifyUserName(currentInfo.getEmpName());

		// 设定支付状态为未支付
        entity.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);

		// 设置付款金额为空
        entity.setPaymentAmount(null);

		// 付款备注为空
		entity.setPaymentNotes(null);

		// 设置付款单号为N/A
        entity.setPaymentNo(SettlementConstants.DEFAULT_BILL_NO);

		int i = this.billPayableEntityDao.updateByCancelFrozen(entity);

		//i 返回值不能于1抛出异常信息
		if (i != 1) {
			throw new SettlementException("取消冻结应付单失败");
		}

		LOGGER.info("successfully exit service, id: " + entity.getId());
	}

	/**
	 * 批量取消冻结应付单
	 * 和设置应付单的状态为：未支付
     *
     * @param dto
     * @param currentInfo
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-2 下午6:04:51
	 */
	@Override
	public void cancelFrozenBillPayableByBatch(BillPayableDto dto, CurrentInfo currentInfo) {
		LOGGER.info("批量取消冻结应付单开始");
        if (dto != null && CollectionUtils.isNotEmpty(dto.getBillPayables())) {

			if (dto.getBillPayables().size() > SettlementConstants.MAX_LIST_SIZE) {
				throw new SettlementException("批量取消冻结应付单,批量取消冻结数量大于"
						+ SettlementConstants.MAX_LIST_SIZE + "，不能进行操作");
			}

			int updateRows = billPayableEntityDao.updateByCancelFrozenByBatch(dto);
			if (updateRows != dto.getBillPayables().size()) {
				LOGGER.error("更新失败，更新行数不唯一!");
				throw new SettlementException("更新失败，更新行数不唯一!");
			}

		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
		LOGGER.info("批量取消冻结应付单结束");

	}

	/**
	 * 修改应付单的支付状态、付款单号、付款金额等信息
     *
	 * @param dto
	 * @param currentInfo
	 * @return
     * @author 099995-foss-wujiangtao
     * @date 2012-11-6 下午2:05:16
	 */
	@Override
	public void payForBillPayable(BillPayableDto dto, CurrentInfo currentInfo) {
		// 输入参数校验
		if (dto == null || StringUtils.isEmpty(dto.getId())
				|| dto.getAccountDate() == null || dto.getVersionNo() == null) {
			throw new SettlementException("修改应付单的支付状态参数不能为空！");
		}
		if (StringUtils.isEmpty(dto.getPaymentNo())) {
			throw new SettlementException("付款单号为空，不能进行修改应付单的支付信息操作！");
		}
		LOGGER.info(" 修改应付单的支付状态、付款单号、付款金额等信息 id：" + dto.getId());

		dto.setActive(FossConstants.ACTIVE);

		// 设定支付状态为已支付
		dto.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES);// 设置已支付

		// 限定条件中的状态为未支付
		dto.setConPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);// 设置为未支付

		// 操作者信息
		dto.setModifyTime(new Date());

		//修改人编码
		dto.setModifyUserCode(currentInfo.getEmpCode());

		//修改人名称
		dto.setModifyUserName(currentInfo.getEmpName());

		int i = this.billPayableEntityDao.updateByBatchPay(dto);

		//i 返回值不能于1抛出异常信息
		if (i != 1) {
			throw new SettlementException("修改应付单的支付状态失败");
		}

		LOGGER.info("successfully exit service, id: " + dto.getId());

	}

	/**
	 * 取消应付单的支付状态和付款单号、付款金额等信息
     *
	 * @param dto
	 * @param currentInfo
	 * @return
     * @author 099995-foss-wujiangtao
     * @date 2012-11-6 下午2:13:40
	 */
	@Override
	public void cancelPayForBillPayable(BillPayableDto dto,
			CurrentInfo currentInfo) {

		// 输入参数校验
		if (dto == null || StringUtils.isEmpty(dto.getId())
				|| dto.getAccountDate() == null || dto.getVersionNo() == null) {
			throw new SettlementException("修改应付单的支付状态参数不能为空！");
		}
		LOGGER.info(" 取消应付单的支付状态、付款单号、付款金额等信息 id：" + dto.getId());

		dto.setActive(FossConstants.ACTIVE);

		// 设定支付状态为未支付
		dto.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);// 设置为未支付

		// 限定条件中的状态为已支付
		dto.setConPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES);// 设置已支付

		// 设置付款金额为空
		dto.setPaymentAmount(null);

		// 付款备注为空
		dto.setPaymentNotes(null);

		// 设置付款单号为N/A
		dto.setPaymentNo(SettlementConstants.DEFAULT_BILL_NO);

		// 操作者信息
		dto.setModifyTime(new Date());

		//设置修改人编码
		dto.setModifyUserCode(currentInfo.getEmpCode());

		//设置修改人名称
		dto.setModifyUserName(currentInfo.getEmpName());

		int i = this.billPayableEntityDao.updateByBatchPay(dto);

		//i 返回值不能于1抛出异常信息
		if (i != 1) {
			throw new SettlementException("取消应付单的支付状态 失败");
		}
		LOGGER.info("successfully exit service, id: " + dto.getId());
	}

	/**
	 * 签收时，不能生效应付单的情况下，修改代收货款应付单的签收日期
     *
     * @param entity
     * @param currentInfo
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-15 下午7:02:57
	 */
	@Override
	public void updateBillPayableSignDateByConfirmIncome(
			BillPayableEntity entity, CurrentInfo currentInfo) {

		//校验应付实体属性信息
		if (entity == null || entity.getSignDate() == null //签收日期
                || entity.getVersionNo() == null//版本号
                || entity.getId() == null //ID
                || entity.getAccountDate() == null //记账日期
				) {
			throw new SettlementException("签收时，修改应付单参数信息不能为空");
		}
		LOGGER.info("---Start 签收时设置应付单的签收日期");

		//修改时间
		entity.setModifyTime(new Date());

		//修改人编码
		entity.setModifyUserCode(currentInfo.getEmpCode());

		//修改人名称
		entity.setModifyUserName(currentInfo.getEmpName());

		//修改应付单的记账日期
		int i = this.billPayableEntityDao.updateBySignDate(entity);

		//i 返回值不能于1抛出异常信息
		if (i != 1) {
			throw new SettlementException("签收时，修改应付单的签收日期失败");
		}
		LOGGER.info("---End 签收时设置应付单的签收日期");

	}

	/**
	 * 反签收时，不能失效代收货款应付单的情况下，设置代收货款应付单的签收日期为空
     *
     * @param entity
     * @param currentInfo
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-15 下午7:29:26
	 */
	@Override
	public void updateBillPayableSignDateByReverseConfirmIncome(
			BillPayableEntity entity, CurrentInfo currentInfo) {

		//校验参数信息不能为空
        if (entity == null || entity.getVersionNo() == null
                || entity.getId() == null
                || entity.getAccountDate() == null
				) {
			throw new SettlementException("反签收时，修改应付单参数信息不能为空");
		}
		LOGGER.info(" start --- 反签收置空应付单的签收日期 ");

		//设置签收日期为空
		entity.setSignDate(null);

		//修改时间：为当前时间
		entity.setModifyTime(new Date());

		//修改人编码
		entity.setModifyUserCode(currentInfo.getEmpCode());

		//修改人名称
		entity.setModifyUserName(currentInfo.getEmpName());
		int i = this.billPayableEntityDao.updateBySignDate(entity);

		//i 返回值不能于1抛出异常信息
		if (i != 1) {
			throw new SettlementException("反签收时，修改应付单的签收日期失败");
		}
		LOGGER.info(" end ---反签收置空应付单的签收日期 ");
	}

	/**
	 * 批量修改应付单的对账单单号
     *
     * @param dto
     * @param currentInfo
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-4 下午6:34:13
	 */
	@Override
	public void batchUpdateByMakeStatement(BillPayableDto dto, CurrentInfo currentInfo) {

		//校验参数信息不能为空
		if (dto == null || StringUtils.isEmpty(dto.getStatementBillNo())
				|| CollectionUtils.isEmpty(dto.getBillPayables())) {
			throw new SettlementException("批量修改应付单对账单号传入参数不能为空！");
		}
		//一次性修改数据不能大于1000条记录
		if (dto.getBillPayables().size() > SettlementConstants.MAX_LIST_SIZE) {
			throw new SettlementException("批量修改应付单数量大于"
					+ SettlementConstants.MAX_LIST_SIZE + "，不能进行修改操作");
		}

		LOGGER.info("---Start 批量修改应付单的对账单单号");

		//是否有效设置有效
		dto.setActive(FossConstants.ACTIVE);

		//修改时间
		dto.setModifyTime(new Date());

		//修改人编码
		dto.setModifyUserCode(currentInfo.getEmpCode());

		//修改人名称
		dto.setModifyUserName(currentInfo.getEmpName());

		int i = this.billPayableEntityDao.updateBatchByMakeStatement(dto);

		//返回值和需要修改的总条数不一致，返回异常提示
		if (i != dto.getBillPayables().size()) {
			throw new SettlementException("批量修改应付单的对账单单号服务失败");
		}

		LOGGER.info("---End 批量修改应付单的对账单单号");
	}

	/**
	 * 批量生效应付单信息
     * <p/>
	 * （收银确认地方，只需要批量生效代收货款应付单）
     *
     * @param dto
     * @param currentInfo
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-17 上午9:18:58
	 */
	@Override
	public void batchUpdateByTakeEffect(BillPayableDto dto,
			CurrentInfo currentInfo) {
        if (dto == null || CollectionUtils.isEmpty(dto.getBillPayables())) {
			throw new SettlementException("批量生效应付单参数不能为空！");
		}
		LOGGER.info(" 开始批量生效应付单操作！ ");

        Date date = new Date();

		//生效状态为已生效
		dto.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES);//生效状态

		//生效人编码
		dto.setEffectiveUserCode(currentInfo.getEmpCode());

		//生效人名称
		dto.setEffectiveUserName(currentInfo.getEmpName());

		//生效时间
		dto.setEffectiveDate(date);

		//修改时间
		dto.setModifyTime(date);

		//修改人编码
		dto.setModifyUserCode(currentInfo.getEmpCode());

		//修改人名称
		dto.setModifyUserName(currentInfo.getEmpName());

		//已生效
		dto.setActive(FossConstants.ACTIVE);

		//是否红单-否
		dto.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);

		//增加在线通知内容 ISSUE-3250
		 List<BillPayableEntity>  payables = dto.getBillPayables();
        if (CollectionUtils.isNotEmpty(payables)) {
            for (BillPayableEntity payable : payables) {
				//如果审核退代收货款生效，则通知始发部门及时审核
                if (SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE
                        .equals(payable.getCodType())) {
					//发送站点消息
                    sendMassage(payable, currentInfo);
					}
			 }
		 }
        int i = this.billPayableEntityDao.updateByBatchTakeEffect(dto);
        if (i != dto.getBillPayables().size()) {
				throw new SettlementException("批量生效应付单失败！");
			}
		LOGGER.info(" 批量生效应付单操作！--end ");
	}

	// -------------------- valid methods --------------------

	/**
	 * 验证同一个运单号相同类型的应付单是否存在多条记录
	 * 前提传进来的list，是按照一个运单号进行查询应付单数据的
     *
     * @param list
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-2 上午10:57:19
	 */
	@Override
	public void validateWaybillForBillPayable(List<BillPayableEntity> list) {


		//BUG-26904:整车尾款应付单可以存在多条

		if (CollectionUtils.isNotEmpty(list)) {

			//cod代表代收货款应付单个数
			int cod = 0;

			//sic代表装卸费应付单个数
			int sic = 0;

			for (BillPayableEntity entity : list) {

				//应付单实体不为空时
				if (entity != null) {
					if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD
							.equals(entity.getBillType())) {
						cod += 1;
						if (cod > 1) {
							//大于1，说明同一个运单号的超过两条记录
							throw new SettlementException("同一个运单存在多条代收货款应付单");
						}
					} else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE
							.equals(entity.getBillType())) {
						sic += 1;
						if (sic > 1) {
							//大于1，说明同一个运单号的超过两条记录
							throw new SettlementException("同一个运单存在多条装卸费应付单");
						}
				}
			}
		}
	}
    }

	// -------------------- read methods --------------------

	/**
	 * 根据传入的一到多个应付单号，获取一到多条应付单信息
     *
     * @param payableNos 应付单号集合
     * @param active     是否有效
     * @return
	 * @author dp-wujiangtao
	 * @date 2012-10-12 下午3:42:34
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<BillPayableEntity> queryByPayableNOs(List<String> payableNos,
			String active) {
		//应付单单号集合不能为空
		if (CollectionUtils.isEmpty(payableNos)) {
			throw new SettlementException("查询应付单，输入的应付单号集合不能为空！");
		}
		// 应付单号个数小于等于1000个时，直接查询返回
		if (payableNos.size() <= SettlementConstants.MAX_LIST_SIZE) {
			return this.billPayableEntityDao.queryByPayableNOs(
					payableNos, active);
        }
		// 应付单号个数大于1000个时，分批查询
		else {
			List<BillPayableEntity> payEntitys = new ArrayList<BillPayableEntity>();
			List<String> queryPayNos = new ArrayList<String>();
			for (int index = 0; index < payableNos.size(); index++) {
				queryPayNos.add(payableNos.get(index));
				if (queryPayNos.size() == SettlementConstants.MAX_LIST_SIZE) {
					payEntitys = (List<BillPayableEntity>) CollectionUtils
							.union(payEntitys, billPayableEntityDao
									.queryByPayableNOs(queryPayNos, active));
					queryPayNos.clear();
				}
			}
			if (CollectionUtils.isNotEmpty(queryPayNos)) {
				payEntitys = (List<BillPayableEntity>) CollectionUtils
						.union(payEntitys, billPayableEntityDao
								.queryByPayableNOs(queryPayNos, active));
			}
			return payEntitys;
		}
	}

	/**
	 * 根据传入的一个应付单号，获取一条应付单信息
     *
     * @param payableNo 应付单号
     * @param active    是否有效
     * @return
	 * @author dp-wujiangtao
	 * @date 2012-10-12 下午3:42:55
	 */
	@Override
	public BillPayableEntity queryByPayableNO(String payableNo, String active) {

		//应付单号
		if (StringUtils.isEmpty(payableNo)) {
			throw new SettlementException("查询应付单，输入的应付单号不能为空！");
		}
		List<BillPayableEntity> list = this.billPayableEntityDao
				.queryByPayableNOs(Arrays.asList(payableNo), active);
		if (CollectionUtils.isNotEmpty(list)) {//有效数据只有一条，无效的也只有一条记录
			return (BillPayableEntity) list.get(0);
		}
		return null;
	}

	/**
	 * 根据传入的一到多个来源单号，获取一到多条应付单信息
     *
	 * @param sourceBillNos 来源单号集合
	 * @param  sourceBillType 来源单据类型
	 * @param active  是否有效
	 * @return List<BillPayableEntity>
     * @author dp-wujiangtao
     * @date 2012-10-15 下午6:43:05
	 */
	@Override
	public List<BillPayableEntity> queryBySourceBillNOs(
			List<String> sourceBillNos, String sourceBillType, String active) {
		//来源单号集合不能为空
		if (CollectionUtils.isEmpty(sourceBillNos)) {
			throw new SettlementException("查询应付单，输入的来源单号不能为空！");
		}
		return billPayableEntityDao.queryBySourceBillNOs(sourceBillNos,
				sourceBillType, active);
	}

	/**
	 * 根据运单号和应付单类型集合，获取一到多条应付单信息
     *
     * @param dto
     * @return
	 * @author dp-wujiangtao
	 * @date 2012-10-19 上午10:49:24
	 */
	@Override
	public List<BillPayableEntity> queryBillPayableByCondition(
			BillPayableConditionDto dto) {

		// 满足以下(运单号和来源单据类型不能为空)条件才能进入，此查询方法
        if (dto != null
                && (StringUtils.isNotEmpty(dto.getPayableNo()) || StringUtils.isNotBlank(dto.getPaymentNo())
						|| StringUtils.isNotEmpty(dto.getWaybillNo()) || StringUtils
							.isNotEmpty(dto.getSourceBillNo()))) {
			// 有效单据
			dto.setActive(FossConstants.ACTIVE);

			// 非红单
			dto.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);

			return billPayableEntityDao.queryBillPayableByCondition(dto);
		} else {
			throw new SettlementException("查询应付单，输入的单据编号不能为空！");
		}

	}

	/**
	 * 根据运单号和外发单号、代理编码、判断是否已存在有效的偏线成本应付单
     *
     * @param dto
     * @return
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-19 下午4:02:24
	 */
	@Override
	public int queryBillPayableByExternalBillNo(BillPayableConditionDto dto) {
		//运单号和外发单号、外发代码编码不能为空
		if (dto != null && StringUtils.isNotEmpty(dto.getWaybillNo())
				&& StringUtils.isNotEmpty(dto.getExternalBillNo())
				&& StringUtils.isNotEmpty(dto.getPartailLineAgentCode())) {
			dto.setActive(FossConstants.ACTIVE);
			dto.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
			return this.billPayableEntityDao
					.queryBillPayableByExternalBillNo(dto);
		} else {
			throw new SettlementException("查询偏线成本应付单，输入的参数信息不能为空");
		}
	}

	/**
	 * 根据传入的一到多个应付单号，传入的部门范围，获取一到多条应付单信息
     *
	 * @param payableNos
	 * @param deptCodeList
	 * @param active
     * @author 045738-foss-maojianqiang
     * @date 2012-11-22 下午3:35:24
	 */
	@Override
	public List<BillPayableEntity> queryByPayableNosAndDeptCodes(
            List<String> payableNos, List<String> deptCodeList, String active, CurrentInfo currentInfo, String isPartner) {
		//应付单号集合，不能为空
		if (CollectionUtils.isEmpty(payableNos)) {
			throw new SettlementException("查询应付单，输入的应付单号集合不能为空！");
		}
		return billPayableEntityDao.queryByPayableNosAndDeptCodes(
                payableNos, deptCodeList, active, currentInfo, isPartner);
	}

	/**
	 * 根据传入的一到多个来源单号，传入的部门范围,获取一到多条应付单信息
     *
	 * @param sourceBillNos
	 * @param orgCodes
	 * @param sourceBillType
	 * @param active
     * @author 045738-foss-maojianqiang
     * @date 2012-11-22 下午3:35:24
	 */
	@Override
	public List<BillPayableEntity> queryBySourceBillNosAndOrgCodes(
			List<String> sourceBillNos, List<String> orgCodes,
            String sourceBillType, String active, CurrentInfo currentInfo, String isPartner) {
		//来源单号集合，不能为空
		if (CollectionUtils.isEmpty(sourceBillNos)) {
			throw new SettlementException("查询应付单，输入的来源单号集合不能为空！");
		}
		return billPayableEntityDao.queryBySourceBillNosAndOrgCodes(
                sourceBillNos, orgCodes, sourceBillType, active, currentInfo, isPartner);
	}

	/**
	 * 根据运单号集合和单据类型集合查询应付单信息
     *
     * @param waybillNos 运单号集合
     * @param billTypes  单据类型集合
     * @return
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-22 下午5:19:32
	 */
	@Override
	public List<BillPayableEntity> queryByWaybillNosAndByBillTypes(
			List<String> waybillNos, List<String> billTypes) {
		// 运单号集合或单据类型集合都不能为空
        if (CollectionUtils.isEmpty(waybillNos) || CollectionUtils.isEmpty(billTypes)
				) {
			throw new SettlementException("查询应付单，输入的运单号集合不能为空！");
		}
		return this.billPayableEntityDao.queryByWaybillNosAndByBillTypes(
				waybillNos, billTypes, FossConstants.ACTIVE);
	}
	
	/**
	 * 根据运单号集合和单据类型集合以及来源单号查询应付单信息
     *
     * @param waybillNos 运单号集合
     * @param billTypes  单据类型集合
     * @param sourceBillNo 来源单号集合
     * @return
	 * @author 367638-foss-caodajun
	 * @date 2016-12-13 下午3:49:32
	 */
	@Override
	public List<BillPayableEntity> queryBysourceBillNoAndByBillTypes(
			List<String> waybillNos, List<String> billTypes,List<String>sourceBillNo) {
		// 运单号集合或单据类型集合都不能为空
        if (CollectionUtils.isEmpty(waybillNos) || CollectionUtils.isEmpty(billTypes)||CollectionUtils.isEmpty(sourceBillNo)
				) {
			throw new SettlementException("查询应付单，输入的运单号集合不能为空！");
		}
		return this.billPayableEntityDao.queryBysourceBillNoAndByBillTypes(
				waybillNos, billTypes,sourceBillNo, FossConstants.ACTIVE);
	}
	
	/**
	 * 根据运单号集合和单据类型集合以及到达部门集合查询应付单信息
	 * 
	 * @author 367638-foss-caodajun
	 * @date 2016-10-24 下午5:19:32
	 * @param waybillNos
	 *             运单号集合
	 * @param billTypes
	 *            单据类型集合
	 * @param  destOrgCode
	 *            到达部门集合          
	 * @return
	 */
	@Override
	
	public List<BillPayableEntity> queryDByWaybillNosAndByBillTypes(
			List<String> waybillNos, List<String> billTypes,List<String> destOrgCode) {
		// 运单号集合或单据类型集合或到达部门编码都不能为空
		if (CollectionUtils.isEmpty(waybillNos)||CollectionUtils.isEmpty(billTypes)||CollectionUtils.isEmpty(destOrgCode)
				) {
			throw new SettlementException("查询应付单，输入的运单号集合、单据类型、到达部门不能为空！");
		}
		return this.billPayableEntityDao.queryByWaybillNosAndByBillDTypes(
				waybillNos, billTypes, FossConstants.ACTIVE,destOrgCode);
	}

	/**
	 * 根据来源单号查询应付单是否已经核销/已审核/已付款【供中转使用】
     *
     * @param sourceBillNo 来源单号
     * @param isAdjust
     * @return
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-28 下午6:30:38
	 */
	@Override
	public boolean queryBillPayableIsWriteOff(String sourceBillNo, String isAdjust) {
		//来源单号集合不能为空
		if (StringUtils.isEmpty(sourceBillNo)) {
			throw new SettlementException("查询应付单参数不能为空！");
        }
		List<BillPayableEntity> list = this.billPayableEntityDao
				.queryBillPayableIsWriteOff(
						sourceBillNo,

						//来源单据类型：汽运配载单
						SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__ROAD_FREIGHT_STOWAGE,
						FossConstants.ACTIVE);

		//查询不到应付单数据，直接返回为空
		if (CollectionUtils.isEmpty(list)) {
			return false;
        }
        boolean bl = false;
		for (BillPayableEntity entity : list) {
			if (entity != null) {
				// 调整费用接口调用，只验证尾款数据
				if (FossConstants.YES.equals(isAdjust)) {

					//为yes，只验证尾款应付单数据，此处if不能合并
					// 外请车尾款
                    if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_LAST
							.equals(entity.getBillType())
					  // 整车尾款
					   || SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST
                            .equals(entity.getBillType())) {
						//判断应付单是否已(部分)核销，或已支付/已审核
                        bl = validateBillPayableIsWriteOff(entity, isAdjust);
					}
				} else {
                    bl = validateBillPayableIsWriteOff(entity, null);
                    if (bl) {//如果有为true，说明不能进行修改，直接返回
						return bl;
					}
				}
			}
		}
		return bl;
	}

	/**
	 * 判断应付单是否已(部分)核销，或已支付/已审核
     *
	 * @param entity
	 * @param isAdjust 等于Y是调整费用需要，需要提示Exception信息
	 * @return
     * @author 099995-foss-wujiangtao
     * @date 2013-2-26 上午11:28:39
	 */
    private boolean validateBillPayableIsWriteOff(BillPayableEntity entity, String isAdjust) {
		if (entity == null) {
			return false;
		}
		//应付单已经核销，不能进行后续操作
        if (entity.getVerifyAmount() != null && entity.getVerifyAmount()
                .compareTo(BigDecimal.ZERO) > 0) {
            if (FossConstants.YES.equals(isAdjust)) {
				throw new SettlementException("尾款应付单已核销，不能进行后续操作！");
			}
			return true;
		}

		//应付单已经支付，不能进行后续反操作
        if (SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES
                .equals(entity.getPayStatus())) {
            if (FossConstants.YES.equals(isAdjust)) {
				throw new SettlementException("尾款应付单已支付，不能进行后续操作！");
			}
			return true;
		}

		//应付单状态为已冻结不能进行后续操作
        if (SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__FROZEN
                .equals(entity.getFrozenStatus())) {
            if (FossConstants.YES.equals(isAdjust)) {
				throw new SettlementException("尾款应付单已冻结，不能进行后续操作！");
			}
			return true;
		}

//		//应付单已审核，不能进行后续操作  --此处需要取消，因为整车\外请车默认生成就为已审核    2013-5-23  毛建强
//		if( SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE
//				.equals(entity.getApproveStatus())){
//			if(FossConstants.YES.equals(isAdjust)){
//				throw new SettlementException("尾款应付单已审核，不能进行后续操作！");
//			}
//			return true;
//		}
		return false;
	}

	/**
	 * 生效应付单Service实现--批处理更新符合条件的记录
     *
     * @param billPayableList
	 * @author foss-zhangxiaohui
	 * @date Nov 22, 2012 2:05:49 PM
	 */
	@Override
    public void updatePayableBillLaborFeeStatus(List<BillPayableEntity> billPayableList) {
		// 判断取出来的List是否为空
		if (CollectionUtils.isEmpty(billPayableList)) {
			//打印日志
			LOGGER.info("批处理生效装卸费--updatePayableBillLaborFeeStatus服务");
			//抛出异常
			throw new SettlementException("生效应付单的参数不能为空！");
		}
		//打印日志
		LOGGER.info("进入批处理生效装卸费--updatePayableBillLaborFeeStatus服务");
		//每次更新的个数设置为1
		int i = billPayableEntityDao.updatePayableBillLaborFee(billPayableList);

		//判断Update的个数是否为1
		if (i != billPayableList.size()) {
			throw new SettlementException("生效应付单出错");
		}
		//打印日志
		LOGGER.info("退出批处理生效装卸费--updatePayableBillLaborFeeStatus服务");
	}

	/**
	 * 按照运单号和应付部门编码集合查询应付单信息
     *
	 * @param waybillNos 运单号
	 * @param orgCodeList 应付部门编码集合
	 * @param active  是否有效
	 * @return
     * @author 099995-foss-wujiangtao
     * @date 2012-12-28 下午3:25:04
	 */
	@Override
	public List<BillPayableEntity> queryByWaybillNosAndOrgCodes(
            List<String> waybillNos, List<String> orgCodeList, String active, CurrentInfo currentInfo, String isPartner) {
		//运单号集合不能为空
        if (CollectionUtils.isEmpty(waybillNos)) {
			throw new SettlementException(" 按照运单号和应付部门编码集合查询应付单信息参数不能为空");
		}
        return this.billPayableEntityDao.queryByWaybillNosAndOrgCodes(waybillNos, orgCodeList, active, currentInfo, isPartner);
	}

    /**
	 * 根据付款单号集合查询应付单
     *
	 * @param paymentNos
	 * @param active
	 * @param isRedBack
	 * @return
     * @author 045738-foss-maojianqiang
     * @date 2013-1-14 下午3:26:59
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService#queryByPaymentNos(java.util.List, java.lang.String, java.lang.String)
	 */
	@Override
	public List<BillPayableEntity> queryByPaymentNos(List<String> paymentNos,
			String active, String isRedBack) {
		//付款单号集合不能为空
        if (CollectionUtils.isEmpty(paymentNos)) {
			throw new SettlementException("付款单号集合不能为空!");
		}
		return this.billPayableEntityDao.queryByPaymentNos(paymentNos, active, isRedBack);
	}

	/**
	 * 根据运单号集合和单据类型，查询已签收且未生效（且运输性质不为空运的）应付单信息
     *
	 * @param waybillNos
	 * @param billType
	 * @return
     * @author 099995-foss-wujiangtao
     * @date 2013-1-18 上午9:42:15
	 */
	@Override
	public List<BillPayableEntity> queryByWaybillNosAndBillType(
			List<String> waybillNos, String billType) {
		//运单号集合不能为空
        if (CollectionUtils.isEmpty(waybillNos)) {
			throw new SettlementException("按运单号集合和单据类型查询应付单参数不能为空");
		}
		return this.billPayableEntityDao.queryByWaybillNosAndBillType(waybillNos, billType,
				SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__NO,
				PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT //精准空运
				);
	}


	/**
     * @author 218392 zhangyongxue
	 * @date 2015-12-31 18:27:30
	 */
	@Override
	public List<BillPayableEntity> queryByWaybillNosByBillType(
			List<String> waybillNos, String billType) {
		//运单号集合不能为空
        if (CollectionUtils.isEmpty(waybillNos)) {
			throw new SettlementException("按运单号集合和单据类型查询应付单参数不能为空");
		}
		String billTypes = billType;
		return this.billPayableEntityDao.queryByWaybillNosByBillType(waybillNos, billTypes,
				SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__NO,
				PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT //精准空运
				);
	}

	/**
	 * 根据来源单号集合和客户（代理）编码，查询应付单信息
     *
	 * @param sourceBillNos 来源单号集合
	 * @param customerCode  客户编码
	 * @param sourceBillType 来源单据类型
	 * @param active  是否有效
	 * @return List<BillPayableEntity>
     * @author 099995-foss-wujiangtao
     * @date 2013-1-21 下午2:51:17
	 */
	@Override
	public List<BillPayableEntity> queryBySourceBillNOsAndCustomerCode(
			List<String> sourceBillNos, String customerCode,
			String sourceBillType, String active) {
        if (CollectionUtils.isEmpty(sourceBillNos)
                || StringUtils.isEmpty(customerCode)
                ) {
			throw new SettlementException("按来源单号集合和客户（代理）编码，查询应付单参数不能为空");
		}
		return this.billPayableEntityDao.queryBySourceBillNOsAndCustomerCode
				(sourceBillNos, customerCode, sourceBillType, active);
	}


	 /**
     * 根据来源单号、客户编码、来源单据类型批量查询应付单  供空运变更清单服务调用
     *
	  * @param list
	  * @param active
	  * @return
     * @author 045738-foss-maojianqiang
     * @date 2013-4-12 上午9:39:56
	  */
	public List<BillPayableEntity> selectBySourceBillNOsAndCustomerCodeAndBillType(
			List<BillPayableEntity> list, String active) {
        if (CollectionUtils.isEmpty(list)) {
			throw new SettlementException("按来源单号、客户编码、来源单据类型批量查询时，查询条件参数不能为空！");
		}
        if (list.size() > SettlementConstants.MAX_BILL_NO_SIZE) {
            throw new SettlementException("查询条数不能超过" + SettlementConstants.MAX_BILL_NO_SIZE + "条");
		}
		return this.billPayableEntityDao.selectBySourceBillNOsAndCustomerCodeAndBillType(list, active);
	}

	/**
	 * ISSUE-3250 如果为审核退代收货款，则需要发出在线通知，通知始发部门及时审核
     *
	 * @author lianghaisheng
	 * @date 2013-08-23
     */
    private void sendMassage(BillPayableEntity entity, CurrentInfo currentInfo) {

		InstationJobMsgEntity entiy = new InstationJobMsgEntity();
		//发送人和发送部门信息
		entiy.setSenderCode(currentInfo.getEmpCode());
		entiy.setSenderName(currentInfo.getEmpName());
		entiy.setSenderOrgCode(currentInfo.getCurrentDeptCode());
		entiy.setSenderOrgName(currentInfo.getCurrentDeptName());
		//设置为代收货款消息
        entiy.setMsgType(MessageConstants.MSG_TYPE__COLLECTION);
		//接受方式为组织
		entiy.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
		//设置接收部门信息
		entiy.setReceiveOrgCode(entity.getPayableOrgCode());
		entiy.setReceiveOrgName(entity.getPayableOrgName());
		//设置
        entiy.setContext("你部门含有审核退代收货款的单据：" + entity.getWaybillNo() + "已生效,请及时审核");
        messageService.createBatchInstationMsg(entiy);

	}

    /**
	  * 根据来源单号，来源单据类型查询对应的应付单 供空运中转提货调用
	  * 	查询中转提货清单运单对应的送货费应付单集合
     *
     * @param sourceBillNos   来源单号集合
	  * @param sourceBillTypes 来源单据类型集合
	  * @param active 是否有效
	  * @return List<BillPayableEntity>
     * @author 092036-foss-bochenlong
     * @date 2013-8-20 下午14:50:55
	  */
	@Override
	public List<BillPayableEntity> selectBySourceBillNosTypes(
			List<String> sourceBillNos, List<String> sourceBillTypes,
			String active) {
        if (CollectionUtils.isEmpty(sourceBillNos)) {
			throw new SettlementException("来源单号不能为空！");
        }
        if (CollectionUtils.isEmpty(sourceBillTypes)) {
			throw new SettlementException("来源单据类型不能为空！");
		}
        if (StringUtils.isEmpty(active)) {
			throw new SettlementException("有效类型不能为空！");
		}
        return this.billPayableEntityDao.selectBySourceBillNosTypes(sourceBillNos, sourceBillTypes, active);
	}

	/**
	 * 功能：给临时租车使用，更新应付单上的工作流号
     *
     * @return
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-24
	 */
    public void updateWorkFlowNoByPayNo(List<BillPayableEntity> list, String workFlowNo, CurrentInfo cInfo) {
		//判断传入更新集合
        if (CollectionUtils.isEmpty(list)) {
			throw new SettlementException("传入集合参数不能为空！");
		}
		//判断更新条数
        if (list.size() > SettlementConstants.MAX_LIST_SIZE) {
			throw new SettlementException("一次最大更新条数不能超过1000！");
		}
		//每次更新的个数设置为1
        int i = billPayableEntityDao.updateWorkFlowNoByPayNo(list, workFlowNo, cInfo);

		//判断Update的个数是否为1
		if (i != list.size()) {
			throw new SettlementException("应付单更新条数和传入条数不一致，数据可能发生变化，请刷新界面重新预提！");
		}
	}

	/**
	 * 功能：给临时租车使用，查询应付单数据，顺带关联查询出临时租车预提数据
     *
     * @return
	 * @author 045738-foss-maojianqiang
	 * @date 2014-8-05
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<BillPayableEntity> queryByPayableNOsForRentCar(List<String> payableNos) {
		//应付单单号集合不能为空
		if (CollectionUtils.isEmpty(payableNos)) {
			throw new SettlementException("查询应付单，输入的应付单号集合不能为空！");
		}
		// 应付单号个数小于等于1000个时，直接查询返回
        if (payableNos.size() <= NUM_THOUSAND) {
            return this.billPayableEntityDao.queryByPayableNOsForRentCar(payableNos);
        }
        // 应付单号个数大于1000个时，分批查询
		else {
			List<BillPayableEntity> payEntitys = new ArrayList<BillPayableEntity>();
			List<String> queryPayNos = new ArrayList<String>();
			for (int index = 0; index < payableNos.size(); index++) {
				queryPayNos.add(payableNos.get(index));
				if (queryPayNos.size() == SettlementConstants.MAX_LIST_SIZE) {
					payEntitys = (List<BillPayableEntity>) CollectionUtils
							.union(payEntitys, billPayableEntityDao
									.queryByPayableNOsForRentCar(queryPayNos));
					queryPayNos.clear();
				}
			}
			if (CollectionUtils.isNotEmpty(queryPayNos)) {
				payEntitys = (List<BillPayableEntity>) CollectionUtils
						.union(payEntitys, billPayableEntityDao
								.queryByPayableNOsForRentCar(queryPayNos));
			}
			return payEntitys;
		}
	}

	/**
	 * 功能：给临时租车使用，查询应付单数据，顺带关联查询出临时租车预提数据
     *
     * @return
	 * @author 045738-foss-maojianqiang
	 * @date 2014-8-05
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<BillPayableEntity> queryByPaymentNosForRentCar(List<String> paymentNos) {
		//应付单单号集合不能为空
		if (CollectionUtils.isEmpty(paymentNos)) {
			throw new SettlementException("查询应付单，输入的应付单号集合不能为空！");
		}
		// 应付单号个数小于等于1000个时，直接查询返回
		if (paymentNos.size() <= SettlementConstants.MAX_LIST_SIZE) {
			return this.billPayableEntityDao.queryByPaymentNosForRentCar(paymentNos);
        }
		// 应付单号个数大于1000个时，分批查询
		else {
			List<BillPayableEntity> payEntitys = new ArrayList<BillPayableEntity>();
			List<String> queryPayNos = new ArrayList<String>();
			for (int index = 0; index < paymentNos.size(); index++) {
				queryPayNos.add(paymentNos.get(index));
				if (queryPayNos.size() == SettlementConstants.MAX_LIST_SIZE) {
					payEntitys = (List<BillPayableEntity>) CollectionUtils
							.union(payEntitys, billPayableEntityDao
									.queryByPaymentNosForRentCar(queryPayNos));
					queryPayNos.clear();
				}
			}
			if (CollectionUtils.isNotEmpty(queryPayNos)) {
				payEntitys = (List<BillPayableEntity>) CollectionUtils
						.union(payEntitys, billPayableEntityDao
								.queryByPayableNOsForRentCar(queryPayNos));
			}
			return payEntitys;
		}
	}

	/**
	 * 功能：当更新报账预提接口不成功时，版本号-1
     *
     * @return
	 * @author 045738-foss-maojianqiang
	 * @date 2014-11-4
	*/
    public void updatePayableVersion(List<BillPayableEntity> list, CurrentInfo cInfo) {
    	//判断传入更新集合
        if (CollectionUtils.isEmpty(list)) {
			throw new SettlementException("传入集合参数不能为空！");
		}
		//判断更新条数
        if (list.size() > SettlementConstants.MAX_LIST_SIZE) {
			throw new SettlementException("一次最大更新条数不能超过1000！");
		}
		//每次更新的个数设置为1
        billPayableEntityDao.updatePayableVersion(list, cInfo);
    }

    /**
     * 生成生效时间使应付单生效
     *
     * @param entity      应付单实体
     * @param currentInfo 操作用户
     * @author foss-hemingyu
     * @date 2016-01-21 下午18:33:33
     */
    public void updateBillPayableEffective(BillPayableEntity entity, CurrentInfo currentInfo) {
        // 输入参数校验主要校验id和应收单号ReceivableNo
        if (entity == null || StringUtils.isEmpty(entity.getId())
                || StringUtils.isEmpty(entity.getPayableNo())) {
            throw new SettlementException("确认应收单的应收单号和运单号不能为空！");
        }
        LOGGER.info("entering service, id: " + entity.getId());

        //初始化日期格式化到秒
        Date now = Calendar.getInstance().getTime();

        //生效状态-设置为有效
        entity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES);

        //生效日期
        entity.setEffectiveDate(now);

        //生效人编码
        entity.setEffectiveUserCode(currentInfo.getEmpCode());

        //生效人名称
        entity.setEffectiveUserName(currentInfo.getEmpName());

        //修改时间
        entity.setModifyTime(now);

        // 操作者信息
        entity.setModifyUserCode(currentInfo.getEmpCode());

        //修改人名称
        entity.setModifyUserName(currentInfo.getEmpName());

        //修改应收单扣款状态并返回信息，每次修改一条
        int i = this.billPayableEntityDao.updateBillPayableEffective(entity);
        if (i != 1) {
            throw new SettlementException("确认应收单修改扣款状态操作失败。");
        }
    }

    /**
     * 根据运单号集合查询应付单信息
     *
     * @param waybillNo 运单号集合
     * @return List<BillPayableEntity>
     * @author foss-hemingyu
     * @date 2016-01-28 下午18:33:33
     */
    public List<BillPayableEntity> selectByWaybillNo(String waybillNo) {
        if (StringUtils.isEmpty(waybillNo)) {
            throw new SettlementException("来源单号不能为空！");
        }
        return this.billPayableEntityDao.queryByWaybillNo(waybillNo);
    }

	@Override
	public String queryDiscountPayable(String waybillNo) {
		String status = billPayableEntityDao.queryDiscountPayable(waybillNo);
		return status;
	}

	/**
	 *  根据付款单号查询应付单
     *
     * @param List<BillPayableEntity> payableList
     * @return List<BillPayableEntity> payableList    
	 * @author foss-269044
	 * @date 2015-10-26
	 */
	public List<BillPayableEntity> queryPaymentListByPaymentNo(List<BillPayableEntity> payableList) {
		return billPayableEntityDao.queryPaymentListByPaymentNo(payableList);
	}

	/**
     * 根据运单号集合查询应付单信息
     *
     * @param waybillNo 运单号集合
     * @return List<BillPayableEntity>
     * @author 331556 fanjingwei
     * @date 2016-05-28
     */
	public List<BillPayableEntity> selectByWaybillNos(String waybillNo) {
		// TODO Auto-generated method stub
		return this.billPayableEntityDao.queryByWaybillNos(waybillNo);
	}
	
	/**
     * 长期未支付有效应付单自动限制付款
     * @author 340778-foss-zf
     * @date 2016-7-22 下午2:59:35
     * @description
     */
    public void updateBillPayableAutoLimit(){
    	this.billPayableEntityDao.updateBillPayableAutoLimit();
    }
    
    /**
     * 长期未支付有效应付单自动限制付款解除
     * @author 340778-foss-zf
     * @date 2016-7-22 下午3:02:01
     * @description
     * @param payableNo 应付单号
     * @param currentInfo 当前用户信息
     */
    public void updateBillPayableAutoLimitRestore(List<String> payableNos,CurrentInfo currentInfo){
    	//service层参数效验
    	if (payableNos==null || payableNos.size()==0) {
    		 throw new SettlementException("传入参数不能为空");
		}
    	//service层map参数装配
    	Map<String,Object> map=new HashMap<String, Object>();
    	map.put("payableNos", payableNos);
    	map.put("currentInfo", currentInfo);
    	
    	this.billPayableEntityDao.updateBillPayableAutoLimitRestore(map);    	
    }
	/**
     * 根据运单号查询第一条代收退款应付单金额
     *
     * @param waybillNo 运单号
     * @return amount
     * @author 326181
     */
	public BigDecimal selectFirstPayableAmountByWaybillNo(String waybillNo) {
		return this.billPayableEntityDao.queryFirstPayableAmountByWaybillNo(waybillNo);
	}


	@Override
	public void updateBillPayableEffectiveStatus(BillPayableEntity entity,
			Date signDate, String effectiveStatus, CurrentInfo currentInfo) {
		// TODO Auto-generated method stub
		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getId())
				|| entity.getAccountDate() == null
				|| entity.getVersionNo() == null) {
			throw new SettlementException("生效应付单参数不为空！");
		}

		//获取系统当前时间
		 Date now = Calendar.getInstance().getTime();
		//设置生效时间
		entity.setEffectiveDate(signDate);
		//设置生效状态
		entity.setEffectiveStatus(effectiveStatus);
		//设置修改时间
		entity.setModifyTime(now);
		// 操作者信息
        entity.setModifyUserCode(currentInfo.getEmpCode());
        //修改人名称
        entity.setModifyUserName(currentInfo.getEmpName());

        //调用dao层更新方法
        int i = this.billPayableEntityDao.updateByTakeEffect(entity);
		// dao层返回值不等于1时，提示保存异常信息
		if (i != 1) {
			throw new SettlementException("更新应付单出错");
		}
	}


	@Override
	public List<BillPayableEntity> checkPayableBillRepeated(
			BillPayableEntity queryEntity) {
		LOGGER.info("校验合伙人奖罚应付单是否重复校验条件："
				+ ReflectionToStringBuilder.toString(queryEntity));
		if(queryEntity == null){
			LOGGER.error("校验合伙人应付单是否重复查询条件为空");
			throw new SettlementException("校验合伙人应付单是否重复查询条件为空");
		}
		if(StringUtils.isBlank(queryEntity.getSourceBillNo())){
			LOGGER.error("校验合伙人应付单是否重复来源单据号为空");
			throw new SettlementException("校验合伙人应付单是否重复来源单据号为空");
		}
		if(StringUtils.isBlank(queryEntity.getBillType())){
			LOGGER.error("校验合伙人应付单是否重复单据子类型为空");
			throw new SettlementException("校验合伙人应付单是否重复单据子类型为空");
		}
		if (StringUtils.isBlank(queryEntity.getCustomerCode())) {
			LOGGER.error("校验合伙人应付单是否重复合伙人编码为空");
			throw new SettlementException("校验合伙人应付单是否重复合伙人编码为空");
		}
		if (queryEntity.getAmount() == null
				|| BigDecimal.ZERO.compareTo(queryEntity.getAmount()) >= 0) {
			LOGGER.error("校验合伙人应付单是否重复总金额为空,总金额是：" + queryEntity.getAmount());
			throw new SettlementException("校验合伙人应付单是否重复总金额为空");
		}
		
		queryEntity.setActive(FossConstants.ACTIVE);//有效
		queryEntity.setIsRedBack(FossConstants.NO);//非红单
		return billPayableEntityDao.checkPayableBillRepeated(queryEntity);
	}
	
	
	
}