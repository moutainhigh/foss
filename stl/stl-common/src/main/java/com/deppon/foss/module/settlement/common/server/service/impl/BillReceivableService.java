package com.deppon.foss.module.settlement.common.server.service.impl;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillReceivableEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.*;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.CreditMsgEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.ReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillChangeMsgEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.*;
import com.deppon.foss.module.settlement.common.api.shared.exception.CUBCGrayException;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.HttpClientUtils;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 应收单Service服务 应收单新增、修改、红冲、查询、审核等公共Service
 * 
 * 
 * 
 * 
 * @author huangxb
 * @date 2012-10-10 下午3:23:57
 * @since
 * @version
 */
public class BillReceivableService implements IBillReceivableService {
	
	private String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		this.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}
	
	private static final String SERVICE_CODE = "com.deppon.foss.module.settlement.common.server.service.impl.BillReceivableService";

	/**
	 * 日志属性
	 */
	private static final Logger logger = LogManager
			.getLogger(BillReceivableService.class);

	/**
	 * 应收单Dao
	 */
	private IBillReceivableEntityDao billReceivableEntityDao;

	/**
	 * 扣减客户信用额度Service
	 */
	private ICustomerBargainService customerBargainService;

	/**
	 * 对账单Service
	 */
	private IStatementOfAccountService statementOfAccountService;

	/**
	 * 财务消息变更Service
	 */
	private IWaybillChangeMsgService waybillChangeMsgService;

	/**
	 * 财务收支平衡消息 Service
	 */
	private ICreditMsgService creditMsgService;
	
	/**
	 * 注入部门service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 注入合伙人部门service
	 */
	private ISaleDepartmentService saleDepartmentService;
	// -------------------- write methods --------------------
	
	private ICUBCQueryReceivableAmountService cubcQueryReceivableAmountService;
	
	/**
	 * 生成应收单
	 * @author foss-wangxuemin
	 * @date Apr 19, 2013 4:04:40 PM
	 */
	private void add(BillReceivableEntity entity){
		// FOSS生成的所有单据的初始化状态都为"N"
		entity.setIsInit(FossConstants.NO);
		// 如果返回记录数不等于1，则新增异常
		int i = billReceivableEntityDao.add(entity);
		if (i != 1) {
			throw new SettlementException("生成应收单失败");
		}
	}

	/**
	 * 新增应收单 客户欠款、或代理欠款生成新增应收单方法 1.司机接货后，客户在营业部、派送部、接货开单组进行开单提交运单后，
	 * 调用此接口，根据客户选择的支付运费的欠款方式（临欠、月结） 和客户是否在公司办理代收货款业务， 而生成相应财务单据的应收单
	 * 
	 * 2、因业务原因需要开具小票单据，如用户派送改自提、客户开通会员收取的会员卡费，
	 * 自己自提该派送增加的费用、仓储费、加收送货费、包装费、放空费、卖废品和其他费用等，
	 * 如果客户有权限（可能月结或者临欠）非现金业务的小票时,调用此接口生成非现金小票应收单
	 * 
	 * 3、中转外发反馈录入后，调用此接口，需先红冲开单时生成的应收单 ， 再生成一条新的客户为偏线代理的应收单。
	 * 
	 * 4、在合大票或空运对账员在中转清单中对合票清单增加到达代理时 （1）红冲到付运费和代收货款的应收单（挂在合票时的代理），
	 * 同时生成挂在到达代理上的到付运费和代收货款应收单 （2）同时生成新的一条应付给到达代理的送货费应付单
	 * 
	 * 5、当存在有无法在正单中体现的应收费用款项时，会计可以通过输入正单号、 航空公司名称、金额、备注信息新增一条空运其他应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-25 上午10:09:25
	 * @param entity
	 *            应收单实体
	 * @param currentInfo
	 *            当前操作者
	 */
	@Override
	public void addBillReceivable(BillReceivableEntity entity,
			CurrentInfo currentInfo) {

		// 输入参数校验
		if (entity == null) {
			throw new SettlementException("传入的应收单实体不能为空");
		}

		logger.info("entering service, sourceBillNo: "
				+ entity.getSourceBillNo());

		// 单据信息校验
		// 应收单的来源单据号不能为空
		if (StringUtils.isEmpty(entity.getSourceBillNo())) {
			throw new SettlementException("来源单据号为空，不能进行新增应收单操作！");
		}
		if (StringUtils.isEmpty(entity.getSourceBillType())) {
			throw new SettlementException("来源单据类型为空，不能进行新增应收单操作！");
		}
		if (StringUtils.isEmpty(entity.getBillType())) {
			throw new SettlementException("应收单类型为空，不能进行新增应收单操作！");
		}

		// 业务信息校验1
		if (StringUtils.isEmpty(entity.getReceivableOrgCode())) {
			throw new SettlementException("应收部门编码为空，不能进行新增应收单操作");
		}
		if (StringUtils.isEmpty(entity.getPaymentType())) {
			throw new SettlementException("付款方式为空，不能进行新增应收单操作");
		}

		// 金额校验
		validaBillRece(entity);

		// 各种单据类型校验，单据类型为：
		validaInfo(entity, currentInfo);

		logger.info("successfully exit service, sourceBillNo: "
				+ entity.getSourceBillNo());
	}

	private void validaBillRece(BillReceivableEntity entity) {
		if (entity.getAmount() == null) {
			throw new SettlementException("应收金额为空，不能进行新增应收单操作");
		}
		if (entity.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new SettlementException("应收金额小于等于0，不能进行新增应收单操作");
		}
		if (entity.getVerifyAmount() == null
				|| entity.getUnverifyAmount() == null) {
			throw new SettlementException("核销金额为空，不能进行新增应收单操作");
		}
		if (entity.getUnverifyAmount().compareTo(BigDecimal.ZERO) < 0) {
			throw new SettlementException("未核销金额为负数，不能进行新增应收单操作");
		}
		if (entity.getVerifyAmount().add(entity.getUnverifyAmount())
				.compareTo(entity.getAmount()) != 0) {
			throw new SettlementException("未核销与核销金额之和不等于总金额，不能进行新增应收单操作");
		}

		// 日期校验
		if (entity.getBusinessDate() == null) {
			throw new SettlementException("业务日期为空，不能进行新增应收单操作");
		}
		if (entity.getAccountDate() == null) {
			throw new SettlementException("记账日期为空，不能进行新增应收单操作");
		}

		// 业务日期在记账日期之后 ，提示不能保存
		Date bussinessDate = entity.getBusinessDate();
		Date accountDate = entity.getAccountDate();
		//modify by 354658-duyijun  将业务日期 秒 置为0，解决开单跨系统的时间差问题。
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(bussinessDate);
	    cal.set(Calendar.SECOND, SettlementReportNumber.ZERO);
	    cal.set(Calendar.MILLISECOND, 000);
	    bussinessDate=cal.getTime();
		if (bussinessDate.after(accountDate)) {
			throw new SettlementException("记账日期小于业务日期，不能进行新增应收单操作！");
		}
	}

	private void validaInfo(BillReceivableEntity entity, CurrentInfo currentInfo) {
		// 1)始发/到达 应收单
		if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE
				.equals(entity.getBillType())
				|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
						.equals(entity.getBillType())) {
			if (StringUtils.isEmpty(entity.getWaybillNo())
					|| StringUtils.isEmpty(entity.getWaybillId())) {
				throw new SettlementException("运单号或运单ID为空，不能进行新增应收单操作！");
			}
			if (StringUtils.isEmpty(entity.getProductCode())) {
				throw new SettlementException("运输性质为空，不能进行新增应收单操作");
			}

			// 明细费用之和等于增值业务费
			BigDecimal valueAddFee = NumberUtils.sum(entity.getPickupFee(),
					entity.getDeliveryGoodsFee(), entity.getPackagingFee(),
					entity.getCodFee(), entity.getInsuranceFee(),
					entity.getOtherFee());
			if (valueAddFee.compareTo(entity.getValueAddFee()) != 0) {
				throw new SettlementException("明细之和不等于增值业务费，不能进行新增应收单操作");
			}
			// 明细费用之和等于总运费
			BigDecimal sum = entity.getTransportFee().add(valueAddFee);
			if (sum.compareTo(entity.getAmount()) != 0) {
				throw new SettlementException("明细之和不等于总运费，不能进行新增应收单操作");
			}
		}
		// 2)代收货款
		else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE
				.equals(entity.getBillType())
				|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD
						.equals(entity.getBillType())// 新增加：空运代收货款
		) {
			if (StringUtils.isEmpty(entity.getWaybillNo())
					|| StringUtils.isEmpty(entity.getWaybillId())) {
				throw new SettlementException("运单号或运单ID为空，不能进行新增应收单操作！");
			}
			if (StringUtils.isEmpty(entity.getProductCode())) {
				throw new SettlementException("运输性质为空，不能进行新增应收单操作！");
			}
		}
		// 3)小票
		else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__OTHER_REVENUE_RECEIVABLE
				.equals(entity.getBillType())) {
			if (StringUtils.isEmpty(entity.getCollectionType())) {
				throw new SettlementException("收款类别为空，不能进行新增应收单操作！");
			}
		}
		// 4)空运其他应收时
		else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__OTHER_REVENUE_RECEIVABLE
				.equals(entity.getBillType())
				|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_RECEIVABLE
						.equals(entity.getBillType())) {
		}

		entity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO); // 对账单设置为默认值

		// 设置操作者信息
		Date date = new Date();
		entity.setCreateTime(date);
		entity.setModifyTime(date);
		if(null!= currentInfo){
			if(StringUtils.isNotBlank(currentInfo.getEmpCode())){
				entity.setCreateUserCode(currentInfo.getEmpCode());
				entity.setModifyUserCode(currentInfo.getEmpCode());
			}else{
				logger.info("当前用户编码为空");
			}
			if(StringUtils.isNotBlank(currentInfo.getEmpName())){
				entity.setCreateUserName(currentInfo.getEmpName());
				entity.setModifyUserName(currentInfo.getEmpName());
			}else{
				logger.info("当前用户名称为空");
			}
			if(StringUtils.isNotBlank(currentInfo.getCurrentDeptCode())){
				entity.setCreateOrgCode(currentInfo.getCurrentDeptCode());
			}else{
				logger.info("当前部门编码为空");
			}
			if(StringUtils.isNotBlank(currentInfo.getCurrentDeptName())){
				entity.setCreateOrgName(currentInfo.getCurrentDeptName());
			}else{
				logger.info("当前部门名称为空");
			}
		}
		this.add(entity);

		// 单据的运单号和来源单号相同,新增应收单时插入财务变更消息表 +1
		if (StringUtils.isNotEmpty(entity.getWaybillNo())
				&& (SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL
						.equals(entity.getSourceBillType()) || SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__EXCEPTION
						.equals(entity.getSourceBillType()))){

			this.addChangeMsg(
					entity.getWaybillNo(),
					entity.getReceivableNo(),
					SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESSING // 新增
																								// +1
					, date);
		}
	}

	/**
	 * 新增财务变更消息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-5 上午10:46:37
	 * @param waybillNo
	 * @param receivableNo
	 *            //应收单号
	 * @param msgAction
	 * @param date
	 */
	private void addChangeMsg(String waybillNo, String receivableNo,
			String msgAction, Date date) {
		WaybillChangeMsgEntity entity = new WaybillChangeMsgEntity();
		entity.setId(UUIDUtils.getUUID());// ID
		entity.setWaybillNo(waybillNo);// 运单号
		entity.setMsgDate(date);// 消息发生日期
		entity.setMsgAction(msgAction);// 消息动作
		entity.setSourceBillType(SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__SOURCE_BILL_TYPE__RECEIVABLE);// 来源单据类型-应收单
		entity.setSourceBillNo(receivableNo);// 来源单号-应收单号
		this.waybillChangeMsgService.addChangeMsg(entity);
	}

	/**
	 * 红冲应收单服务 因为运单变更、或小票做废，有欠款的红冲原有的财务单据， 1、把原单据是否有效状态设置为无效 2、新生成的红单数据，是否有效：无效，
	 * 是否红单：是 金额为负数
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-16 上午10:42:28
	 * @param entity
	 *            原应收单实体
	 * @param currentInfo
	 *            操作者
	 * @return
	 */
	@Override
	public void writeBackBillReceivable(BillReceivableEntity entity,
			CurrentInfo currentInfo) {

		// 输入参数校验
		//
		if (entity == null || StringUtils.isEmpty(entity.getId())
				|| StringUtils.isEmpty(entity.getReceivableNo())
				|| entity.getAccountDate() == null) {
			throw new SettlementException("红冲应收单参数不能为空！");
		}

		logger.info("entering service, sourceBillNo: "
				+ entity.getSourceBillNo());

		Date now = Calendar.getInstance().getTime();

		// 当前系统时间，小于应收单的解锁时间时不能被红冲
		if (entity.getUnlockDateTime() != null
				&& now.before(entity.getUnlockDateTime())) {
			throw new SettlementException("应收单已被锁定，不能进行红冲应收单操作");
		}

		// 已核销,不能红冲
		if (entity.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
			throw new SettlementException("应收单已核销，不能进行红冲应收单操作");
		}
		// 校验对账单
		if (StringUtils.isEmpty(entity.getStatementBillNo())
				|| SettlementConstants.DEFAULT_BILL_NO.equals(entity
						.getStatementBillNo())) {
			// do nothing
		} else {
			List<String> list = statementOfAccountService
					.queryConfirmStatmentOfAccount(Arrays.asList(entity
							.getStatementBillNo()));
			if (CollectionUtils.isEmpty(list)) { // 对账单未确认,则更新对账相关信息
				StatementOfAccountUpdateDto dto = new StatementOfAccountUpdateDto();
				dto.setReceivableEntityList(Arrays.asList(entity));
				statementOfAccountService.updateStatementAndDetailForRedBack(
						dto, currentInfo);
			} else { // 对账单已确认则不允许红冲单据
				throw new SettlementException(
						"该单存在相应的客户对账单已确认、核销、付款、还款，不能进行红冲应收单操作");
			}
		}

		BillReceivableEntity updateEntity = new BillReceivableEntity();

		// 作废原应收单
		updateEntity.setId(entity.getId()); // ID
		updateEntity.setAccountDate(entity.getAccountDate()); // 分区键
		updateEntity.setVersionNo(entity.getVersionNo()); // 版本号
		updateEntity.setActive(FossConstants.INACTIVE);
		updateEntity.setModifyTime(now);

		// 操作者信息
		updateEntity.setModifyUserCode(currentInfo.getEmpCode());
		updateEntity.setModifyUserName(currentInfo.getEmpName());

		int i = this.billReceivableEntityDao
				.updateBillReceivableByWriteBack(updateEntity);
		if (i != 1) {
			throw new SettlementException("红冲应收单出错");
		}

		// 生成红冲单
		validaCurrentInfo(entity, currentInfo, now);

		logger.info("successfully exit service, sourceBillNo: "
				+ entity.getSourceBillNo());

	}

	private void validaCurrentInfo(BillReceivableEntity entity,
			CurrentInfo currentInfo, Date now) {
		BillReceivableEntity redBackEntity = new BillReceivableEntity();
		BeanUtils.copyProperties(entity, redBackEntity);

		// 保存一条和原单据相同的新红单实体
		redBackEntity.setId(UUIDUtils.getUUID());
		redBackEntity.setActive(FossConstants.INACTIVE); // 设置为无效状态
		redBackEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
		redBackEntity
				.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__YES); // 红单
		redBackEntity.setAccountDate(now); // 设置记账日期
		redBackEntity.setCreateTime(now);
		redBackEntity.setModifyTime(now);

		redBackEntity.setAmount(NumberUtils.multiply(redBackEntity.getAmount(),
				-1));
		redBackEntity.setUnverifyAmount(NumberUtils.multiply(
				redBackEntity.getUnverifyAmount(), -1));
		redBackEntity.setVerifyAmount(NumberUtils.multiply(
				redBackEntity.getVerifyAmount(), -1));

		//运费
		redBackEntity.setTransportFee(NumberUtils.multiply(
				redBackEntity.getTransportFee(), -1));
		
		//接货费
		redBackEntity.setPickupFee(NumberUtils.multiply(
				redBackEntity.getPickupFee(), -1));
		
		//送货费
		redBackEntity.setDeliveryGoodsFee(NumberUtils.multiply(
				redBackEntity.getDeliveryGoodsFee(), -1));
		//包装费
		redBackEntity.setPackagingFee(NumberUtils.multiply(
				redBackEntity.getPackagingFee(), -1));
		//代收货款手续费
		redBackEntity.setCodFee(NumberUtils.multiply(redBackEntity.getCodFee(),
				-1));
		//保价费
		redBackEntity.setInsuranceFee(NumberUtils.multiply(
				redBackEntity.getInsuranceFee(), -1));
		//其它费
		redBackEntity.setOtherFee(NumberUtils.multiply(
				redBackEntity.getOtherFee(), -1));
		//增值费
		redBackEntity.setValueAddFee(NumberUtils.multiply(
				redBackEntity.getValueAddFee(), -1));
		//优惠费
		redBackEntity.setPromotionsFee(NumberUtils.multiply(
				redBackEntity.getPromotionsFee(), -1));

		// 操作者信息
		redBackEntity.setModifyUserCode(currentInfo.getEmpCode());
		redBackEntity.setModifyUserName(currentInfo.getEmpName());

		this.add(redBackEntity);

		// 单据的运单号和来源单号相同,红冲应收单时插入财务变更消息表 -1
		if (StringUtils.isNotEmpty(entity.getWaybillNo())
				&& (SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL
						.equals(entity.getSourceBillType()) || SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__EXCEPTION
						.equals(entity.getSourceBillType()))){
			this.addChangeMsg(
					entity.getWaybillNo(),
					entity.getReceivableNo(),
					SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESS_DONE // 红冲应收单
																								// 完结
																								// -1
					, now);
		}
		
		// 存放应收单类型
		List<String> billTypes = new ArrayList<String>();

		// 始发应收单
		billTypes
				.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE);

		// 到达应收单
		billTypes
				.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE);

		// 小票应收单
		billTypes
				.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__OTHER_REVENUE_RECEIVABLE);

		// 插入财务收支平衡消息---type:红冲
		if (billTypes.contains(entity.getBillType())
				&& (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT
						.equals(entity.getPaymentType())// 临欠或
				|| SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT
						.equals(entity.getPaymentType()))// 月结
		) {
			this.addCreditMsgEntity(
					SettlementDictionaryConstants.CREDIT_MSG_TYPE__WRITEBACK,
					redBackEntity, entity.getAmount());
		}
	}

	/**
	 * 新增财务收支平衡消息方法 供（核销/反核销/红冲应收单方法调用）
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-18 下午3:30:19
	 * @param type
	 * @param receEntity
	 * @param amount
	 *            核销：核销金额（正整数）；反核销：反核销金额（负数）；红冲：应收金额（正整数）
	 */
	private void addCreditMsgEntity(String type,
			BillReceivableEntity receEntity, BigDecimal amount) {

		String code = "";// 客户/部门编码
		String name = "";// 客户/部门名称
		String creditType = "";// 收支平衡类型

		// 应收单的付款方式：临欠，月结的
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT
				.equals(receEntity.getPaymentType())) {// 临欠

			// 部门信息
			code = receEntity.getReceivableOrgCode();
			name = receEntity.getReceivableOrgName();
			creditType = SettlementDictionaryConstants.CREDIT_MSG_CREDIT_TYPE__ORG;
		} else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT
				.equals(receEntity.getPaymentType())) {// 月结

			// 客户信息
			code = receEntity.getCustomerCode();
			name = receEntity.getCustomerName();
			creditType = SettlementDictionaryConstants.CREDIT_MSG_CREDIT_TYPE__CUSTOMER;
		}
		CreditMsgEntity entity = new CreditMsgEntity();
		entity.setId(UUIDUtils.getUUID());// ID
		entity.setType(type);// 核销
		entity.setSourceBillNo(receEntity.getReceivableNo());
		entity.setCode(code);
		entity.setName(name);
		entity.setCreditType(creditType);// 收支平衡类型
		entity.setAmount(amount);
		entity.setCreateTime(new Date());
		entity.setStatus(SettlementDictionaryConstants.CREDIT_MSG_STATUS_NOT_EXECUTE);// 状态未执行

		this.creditMsgService.add(entity);// 保存财务收支平衡消息表
	}

	/**
	 * 应收单核销
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-10-19 上午11:31:24
	 * @param entity
	 *            应收单实体
	 * @param writeoffAmount
	 *            核销/反核销金额
	 * @param currentInfo
	 *            操作者
	 */
	@Override
	public void writeoffBillReceivable(BillReceivableEntity entity,
			BigDecimal writeoffAmount, CurrentInfo currentInfo) {

		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getId())
				|| entity.getAccountDate() == null
				|| entity.getVersionNo() == null) {
			throw new SettlementException("核销应收单参数不能为空！");
		}
		if (writeoffAmount == null || BigDecimal.ZERO.equals(writeoffAmount)) {
			throw new SettlementException("核销金额不能为空！");
		}

		logger.info("entering service, id: " + entity.getId());
		Date now = Calendar.getInstance().getTime();
		BillWriteoffAmountDto dto = new BillWriteoffAmountDto();
		BeanUtils.copyProperties(entity, dto);
		dto.setModifyTime(now);
		dto.setWriteoffAmount(writeoffAmount);

		// 操作者信息
		entity.setModifyUserCode(currentInfo.getEmpCode());
		entity.setModifyUserName(currentInfo.getEmpName());

		// 核销应收单时，如果应收单的付款方式为临欠的，要实时调用客户还原信用额度的接口 2012-11-12日
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT
				.equals(entity.getPaymentType())) {
			customerBargainService.updateUsedAmount(
					// 要么成功，要么updateUsedAmount（）抛出异常
					entity.getCustomerCode(), entity.getReceivableOrgCode(),
					entity.getPaymentType(),
					NumberUtils.multiply(writeoffAmount, -1), currentInfo);
		}

		int i = billReceivableEntityDao.updateBillReceivableByWriteoff(dto);
		if (i != 1) {
			throw new SettlementException("核销应收单出错");
		}

		// 核销之后，其他地方还需要使用，为了保持java方法中的版本号和数据库中的版本号统一，在这里+1
		validaBillReceivableEntity(entity, writeoffAmount, now);

		logger.info("successfully exit service, id: " + entity.getId());
	}

	private void validaBillReceivableEntity(BillReceivableEntity entity,
			BigDecimal writeoffAmount, Date now) {
		entity.setVersionNo((short) (entity.getVersionNo() + 1));

		// 发送财务变更消息
		if (StringUtils.isNotEmpty(entity.getWaybillNo())
				&& (SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL
						.equals(entity.getSourceBillType()) || SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__EXCEPTION
						.equals(entity.getSourceBillType()))){

			validaBillReceivable(entity, writeoffAmount, now);
		}
		List<String> billTypes = new ArrayList<String>();

		// 始发应收单
		billTypes
				.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE);

		// 到达应收单
		billTypes
				.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE);

		// 小票应收单
		billTypes
				.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__OTHER_REVENUE_RECEIVABLE);

		// 插入财务收支平衡消息---type:红冲
		if (billTypes.contains(entity.getBillType())
				&& (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT
						.equals(entity.getPaymentType())// 临欠或
				|| SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT
						.equals(entity.getPaymentType()))// 月结
		) {
			// 默认为核销
			String type = SettlementDictionaryConstants.CREDIT_MSG_TYPE__WRITEOFF;

			// 反核销时，金额为负数
			if (writeoffAmount.compareTo(BigDecimal.ZERO) < 0) {
				// 反核销
				type = SettlementDictionaryConstants.CREDIT_MSG_TYPE_REVERS__WRITEOFF;
			}
			this.addCreditMsgEntity(type, entity, writeoffAmount);
		}
	}

	private void validaBillReceivable(BillReceivableEntity entity,
			BigDecimal writeoffAmount, Date now) {
		// 单据完成核销时：未核销金额等于0，发送财务完结变更消息-1
		if (entity.getUnverifyAmount() != null
				&& entity.getUnverifyAmount().compareTo(BigDecimal.ZERO) == 0) {
			this.addChangeMsg(
					entity.getWaybillNo(),
					entity.getReceivableNo(),
					SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESS_DONE,
					now);// 财务完结变更消息 -1

		} else if (writeoffAmount.compareTo(BigDecimal.ZERO) < 0) {
			// 反核销时：未核销金额等于正的反核销的金额writeoffAmount

			// posWriteoffAmount 是writeoffAmount乘以-1得到正数
			BigDecimal posWriteoffAmount = writeoffAmount
					.multiply(new BigDecimal("-1"));

			// 或可以用writeoffAmount和未核销金额相加等于0
			if (entity.getUnverifyAmount() != null
					&& entity.getUnverifyAmount().compareTo(
							posWriteoffAmount) == 0) {// 反核销+1

				this.addChangeMsg(
						entity.getWaybillNo(),
						entity.getReceivableNo(),
						SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESSING,
						now);// 财务反核销 反操作+1
			}
		}
	}

	/**
	 * 签收时 确认收入日期
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-22 上午11:12:09
	 * @param entity
	 * @param currentInfo
	 * @return
	 */
	@Override
	public void updateBillReceivableByConfirmIncome(
			BillReceivableEntity entity, CurrentInfo currentInfo) {

		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getId())
				|| entity.getAccountDate() == null
				|| entity.getVersionNo() == null) {
			throw new SettlementException("确认应收单收入参数不能为空！");
		}

		logger.info("entering service, id: " + entity.getId());

		if (entity.getConrevenDate() == null) {
			throw new SettlementException("确认收入日期不能为空！");
		}
		// 操作者信息
		entity.setModifyTime(new Date());
		entity.setModifyUserCode(currentInfo.getEmpCode());
		entity.setModifyUserName(currentInfo.getEmpName());

		int i = this.billReceivableEntityDao
				.updateBillReceivableByConfirmIncome(entity);
		if (i != 1) {
			throw new SettlementException("确认应收单收入操作失败。");
		}

		logger.info("successfully exit service, id: " + entity.getId());
	}

	/**
	 * 反签收时取消确认收入日期
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-22 上午11:12:43
	 * @param entity
	 * @param currentInfo
	 * @return
	 */
	@Override
	public void updateBillReceivableByReverseConfirmIncome(
			BillReceivableEntity entity, CurrentInfo currentInfo) {

		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getId())
				|| entity.getAccountDate() == null
				|| entity.getVersionNo() == null) {
			throw new SettlementException("反确认应收单收入参数不能为空！");
		}

		logger.info("entering service, id: " + entity.getId());

		// 签收日期设置为空
		entity.setConrevenDate(null);

		// 操作者信息
		entity.setModifyTime(new Date());
		entity.setModifyUserCode(currentInfo.getEmpCode());
		entity.setModifyUserName(currentInfo.getEmpName());
		int i = this.billReceivableEntityDao
				.updateBillReceivableByConfirmIncome(entity);
		if (i != 1) {
			throw new SettlementException("反确认收入操作失败。");
		}

		logger.info("successfully exit service, id: " + entity.getId());
	}

	/**
	 * 批量审核空运其他应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-31 上午8:17:40
	 * @param dto
	 * @param currentInfo
	 * @return
	 */
	@Override
	public void auditAirOtherBillReceivable(BillReceivableDto dto,
			CurrentInfo currentInfo) {

		if (dto == null || CollectionUtils.isEmpty(dto.getBillReceivables())) {
			throw new SettlementException("批量审核空运其他应收单输入的参数不能为空");
		}

		logger.info(" 批量审核空运其他应收单 Start ");

		if (dto.getBillReceivables().size() > SettlementConstants.MAX_LIST_SIZE) {
			throw new SettlementException("审核空运其他应收单数量大于"
					+ SettlementConstants.MAX_LIST_SIZE + "不能进行反审核操作。");
		}

		// 设置修改类型为空运其他应收单
		dto.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_RECEIVABLE);
		dto.setActive(FossConstants.ACTIVE);
		dto.setConApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__NOT_AUDIT);

		// 操作者信息
		dto.setModifyTime(new Date());
		dto.setModifyUserCode(currentInfo.getEmpCode());
		dto.setModifyUserName(currentInfo.getEmpName());

		int i = this.billReceivableEntityDao
				.updateBillReceivableByAirAudit(dto);

		if (i != dto.getBillReceivables().size()) {
			throw new SettlementException("批量审核空运其他应收单失败");
		}

		logger.info(" 批量审核空运其他应收单 end ");
	}

	/**
	 * 反审核空运其他应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-31 上午8:08:23
	 * @param dto
	 * @param currentInfo
	 * @return
	 */
	@Override
	public void reverseAuditAirOtherBillReceivable(BillReceivableDto dto,
			CurrentInfo currentInfo) {

		if (dto == null || CollectionUtils.isEmpty(dto.getBillReceivables())) {
			throw new SettlementException("批量反审核空运其他应收单输入的参数不能为空");
		}

		logger.info(" 批量反审核空运其他应收单 Start ");

		if (dto.getBillReceivables().size() > SettlementConstants.MAX_LIST_SIZE) {
			throw new SettlementException("反审核空运其他应收单数量大于"
					+ SettlementConstants.MAX_LIST_SIZE + "不能进行反审核操作。");
		}

		// 设置修改类型为空运其他应收单
		dto.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_RECEIVABLE);
		dto.setActive(FossConstants.ACTIVE);
		dto.setConApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE);

		// 操作者信息
		dto.setModifyTime(new Date());
		dto.setModifyUserCode(currentInfo.getEmpCode());
		dto.setModifyUserName(currentInfo.getEmpName());

		int i = this.billReceivableEntityDao
				.updateBillReceivableByAirAudit(dto);

		if (i != dto.getBillReceivables().size()) {
			throw new SettlementException("批量反审核空运其他应收单失败");
		}

		logger.info(" 批量反审核空运其他应收单 end ");
	}
	
	/**
	 * 批量审核快递代理其他应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-31 上午8:17:40
	 * @param dto
	 * @param currentInfo
	 * @return
	 */
	@Override
	public void auditLandOtherBillReceivable(BillReceivableDto dto,
			CurrentInfo currentInfo) {

		if (dto == null || CollectionUtils.isEmpty(dto.getBillReceivables())) {
			throw new SettlementException("批量审核快递代理其他应收单输入的参数不能为空");
		}

		logger.info(" 批量审核快递代理其他应收单 Start ");

		if (dto.getBillReceivables().size() > SettlementConstants.MAX_LIST_SIZE) {
			throw new SettlementException("审核快递代理其他应收单数量大于"
					+ SettlementConstants.MAX_LIST_SIZE + "不能进行反审核操作。");
		}

		// 设置修改类型为空运其他应收单
		dto.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_RECEIVABLE);
		dto.setActive(FossConstants.ACTIVE);
		dto.setConApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__NOT_AUDIT);

		// 操作者信息
		dto.setModifyTime(new Date());
		dto.setModifyUserCode(currentInfo.getEmpCode());
		dto.setModifyUserName(currentInfo.getEmpName());

		int i = this.billReceivableEntityDao
				.updateBillReceivableByAirAudit(dto);

		if (i != dto.getBillReceivables().size()) {
			throw new SettlementException("批量审核快递代理其他应收单失败");
		}

		logger.info(" 批量审核快递代理其他应收单 end ");
	}

	/**
	 * 反审核快递代理其他应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-31 上午8:08:23
	 * @param dto
	 * @param currentInfo
	 * @return
	 */
	@Override
	public void reverseAuditLandOtherBillReceivable(BillReceivableDto dto,
			CurrentInfo currentInfo) {

		if (dto == null || CollectionUtils.isEmpty(dto.getBillReceivables())) {
			throw new SettlementException("批量反审核快递代理其他应收单输入的参数不能为空");
		}

		logger.info(" 批量反审核快递代理其他应收单 Start ");

		if (dto.getBillReceivables().size() > SettlementConstants.MAX_LIST_SIZE) {
			throw new SettlementException("反审核空运快递代理应收单数量大于"
					+ SettlementConstants.MAX_LIST_SIZE + "不能进行反审核操作。");
		}

		// 设置修改类型为空运其他应收单
		dto.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_RECEIVABLE);
		dto.setActive(FossConstants.ACTIVE);
		dto.setConApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE);

		// 操作者信息
		dto.setModifyTime(new Date());
		dto.setModifyUserCode(currentInfo.getEmpCode());
		dto.setModifyUserName(currentInfo.getEmpName());

		int i = this.billReceivableEntityDao
				.updateBillReceivableByAirAudit(dto);

		if (i != dto.getBillReceivables().size()) {
			throw new SettlementException("批量反审核快递代理其他应收单失败");
		}

		logger.info(" 批量反审核快递代理其他应收单 end ");
	}


	/** 
	 * <p> 审核偏线其他应收单</p> 
	 * @author 105762 Yang Shushuo
	 * @date 2014-2-21 上午11:37:35
	 * @param dto
	 * @param currentInfo 
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService#auditOtherBillReceivable(com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableDto, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void auditPAOtherBillReceivable(BillReceivableDto dto, CurrentInfo currentInfo) {

		if (dto == null || CollectionUtils.isEmpty(dto.getBillReceivables())) {
			throw new SettlementException("批量审核偏线其他应收单输入的参数不能为空");
		}

		logger.info(" 批量审核偏线其他应收单 Start ");

		if (dto.getBillReceivables().size() > SettlementConstants.MAX_LIST_SIZE) {
			throw new SettlementException("审核偏线其他应收单数量大于"
					+ SettlementConstants.MAX_LIST_SIZE + "不能进行反审核操作。");
		}

		// 设置修改类型为偏线其他应收单
		dto.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTIAL_LINE_RECEIVABLE);
		dto.setActive(FossConstants.ACTIVE);
		dto.setConApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__NOT_AUDIT);

		// 操作者信息
		dto.setModifyTime(new Date());
		dto.setModifyUserCode(currentInfo.getEmpCode());
		dto.setModifyUserName(currentInfo.getEmpName());

		int i = this.billReceivableEntityDao
				.updateBillReceivableByAirAudit(dto);

		if (i != dto.getBillReceivables().size()) {
			throw new SettlementException("批量审核偏线其他应收单失败");
		}

		logger.info(" 批量审核偏线其他应收单 end ");		
	}

	/** 
	 * <p> 反审核偏线其他应收单</p> 
	 * @author 105762 Yang Shushuo
	 * @date 2014-2-21 上午11:37:35
	 * @param dto
	 * @param currentInfo 
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService#reverseAuditOtherBillReceivable(com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableDto, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void reversePAAuditOtherBillReceivable(BillReceivableDto dto, CurrentInfo currentInfo) {

		if (dto == null || CollectionUtils.isEmpty(dto.getBillReceivables())) {
			throw new SettlementException("批量反审核偏线其他应收单输入的参数不能为空");
		}

		logger.info(" 批量反审核偏线其他应收单 Start ");

		if (dto.getBillReceivables().size() > SettlementConstants.MAX_LIST_SIZE) {
			throw new SettlementException("反审核偏线应收单数量大于"
					+ SettlementConstants.MAX_LIST_SIZE + "不能进行反审核操作。");
		}

		// 设置修改类型为偏线其他应收单
		dto.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTIAL_LINE_RECEIVABLE);
		dto.setActive(FossConstants.ACTIVE);
		dto.setConApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE);

		// 操作者信息
		dto.setModifyTime(new Date());
		dto.setModifyUserCode(currentInfo.getEmpCode());
		dto.setModifyUserName(currentInfo.getEmpName());

		int i = this.billReceivableEntityDao
				.updateBillReceivableByAirAudit(dto);

		if (i != dto.getBillReceivables().size()) {
			throw new SettlementException("批量反审核偏线其他应收单失败");
		}

		logger.info(" 批量反审核偏线其他应收单 end ");		
	}

	/** 
	 * <p>审核包装其他应收</p> 
	 * @author 105762
	 * @date 2014-6-11 上午9:13:02
	 * @param dto
	 * @param currentInfo 
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService#auditPackingOtherBillReceivable(com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableDto, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void auditPackingOtherBillReceivable(BillReceivableDto dto, CurrentInfo currentInfo) {
		if (dto == null || CollectionUtils.isEmpty(dto.getBillReceivables())) {
			throw new SettlementException("批量审核包装其他应收单输入的参数不能为空");
		}

		logger.info(" 批量审核包装其他应收单 Start ");

		if (dto.getBillReceivables().size() > SettlementConstants.MAX_LIST_SIZE) {
			throw new SettlementException("审核包装其他应收单数量大于"
					+ SettlementConstants.MAX_LIST_SIZE + "不能进行反审核操作。");
		}

		// 设置修改类型为包装其他应收单
		dto.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__WOODEN_OTEHR_RECEIVABLE);
		dto.setActive(FossConstants.ACTIVE);
		dto.setConApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__NOT_AUDIT);

		// 操作者信息
		dto.setModifyTime(new Date());
		dto.setModifyUserCode(currentInfo.getEmpCode());
		dto.setModifyUserName(currentInfo.getEmpName());

		int i = this.billReceivableEntityDao
				.updateBillReceivableByAirAudit(dto);

		if (i != dto.getBillReceivables().size()) {
			throw new SettlementException("批量审核包装其他应收单失败");
		}

		logger.info(" 批量审核包装其他应收单 end ");	
	}

	/**
	 * <p>
	 * 反审核包装其他应收
	 * </p>
	 * @author 105762
	 * @date 2014-6-11 上午9:13:02
	 * @param dto
	 * @param currentInfo
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService#reverseAuditPackingOtherBillReceivable(com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void reverseAuditPackingOtherBillReceivable(BillReceivableDto dto, CurrentInfo currentInfo) {
		if (dto == null || CollectionUtils.isEmpty(dto.getBillReceivables())) {
			throw new SettlementException("批量反审核包装其他应收单输入的参数不能为空");
		}

		logger.info(" 批量反审核包装其他应收单 Start ");

		if (dto.getBillReceivables().size() > SettlementConstants.MAX_LIST_SIZE) {
			throw new SettlementException("反审核包装应收单数量大于"
					+ SettlementConstants.MAX_LIST_SIZE + "不能进行反审核操作。");
		}

		// 设置修改类型为包装其他应收单
		dto.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__WOODEN_OTEHR_RECEIVABLE);
		dto.setActive(FossConstants.ACTIVE);
		dto.setConApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE);

		// 操作者信息
		dto.setModifyTime(new Date());
		dto.setModifyUserCode(currentInfo.getEmpCode());
		dto.setModifyUserName(currentInfo.getEmpName());

		int i = this.billReceivableEntityDao
				.updateBillReceivableByAirAudit(dto);

		if (i != dto.getBillReceivables().size()) {
			throw new SettlementException("批量反审核包装其他应收单失败");
		}

		logger.info(" 批量反审核包装其他应收单 end ");	
	}

	/**
	 * <p>
	 * 红冲包装其他应收
	 * </p>
	 * @author 105762
	 * @date 2014-6-11 上午9:13:02
	 * @param dto
	 * @param currentInfo
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService#writeBackPackingOtherBillReceivable(com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void writeBackPackingOtherBillReceivable(BillReceivableDto dto, CurrentInfo currentInfo) {
		
	}

	/**
	 * 批量锁定应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-7 上午10:23:05
	 * @param dto
	 * @param currentInfo
	 */
	@Override
	public void lockBillReceivable(BillReceivableDto dto,
			CurrentInfo currentInfo) {
		logger.info(" 批量锁定应收单 Start ");

		if (dto == null || CollectionUtils.isEmpty(dto.getBillReceivables())) {
			throw new SettlementException("批量锁定应收单参数不能为空");
		}

		if (dto.getBillReceivables().size() > SettlementConstants.MAX_LIST_SIZE) {
			throw new SettlementException("批量锁定应收单数量大于"
					+ SettlementConstants.MAX_LIST_SIZE + "。");
		}

		dto.setActive(FossConstants.ACTIVE);

		// 操作者信息
		dto.setModifyTime(new Date());
		dto.setModifyUserCode(currentInfo.getEmpCode());
		dto.setModifyUserName(currentInfo.getEmpName());

		int i = this.billReceivableEntityDao.updateBillReceivableByLock(dto);
		if (i != dto.getBillReceivables().size()) {
			throw new SettlementException("批量锁定应收单失败");
		}

		logger.info(" 批量锁定应收单 end ");
	}

	/**
	 * 批量解锁应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-7 上午10:23:20
	 * @param dto
	 * @param currentInfo
	 */
	@Override
	public void unlockBillReceivable(BillReceivableDto dto,
			CurrentInfo currentInfo) {

		logger.info(" 批量解锁应收单 Start ");

		if (dto == null || CollectionUtils.isEmpty(dto.getBillReceivables())) {
			throw new SettlementException("批量解锁应收单参数不能为空");
		}
		if (dto.getBillReceivables().size() > SettlementConstants.MAX_LIST_SIZE) {
			throw new SettlementException("批量解锁应收单数量大于"
					+ SettlementConstants.MAX_LIST_SIZE + "。");
		}

		dto.setActive(FossConstants.ACTIVE);
		dto.setUnlockDateTime(null);// 设置解锁时间为空即可

		// 操作者信息
		dto.setModifyTime(new Date());
		dto.setModifyUserCode(currentInfo.getEmpCode());
		dto.setModifyUserName(currentInfo.getEmpName());

		int i = this.billReceivableEntityDao.updateBillReceivableByLock(dto);
		if (i != dto.getBillReceivables().size()) {
			throw new SettlementException("批量解锁应收单失败");
		}

		logger.info(" 批量解锁应收单 end ");
	}

	/**
	 * 批量修改应收单的对账单号
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-4 下午6:42:32
	 * @param dto
	 * @param currentInfo
	 */
	@Override
	public void batchUpdateByMakeStatement(BillReceivableDto dto,
			CurrentInfo currentInfo) {

		if (dto == null || StringUtils.isEmpty(dto.getStatementBillNo())
				|| CollectionUtils.isEmpty(dto.getBillReceivables())) {
			throw new SettlementException("批量修改应收单对账单号参数不能为空！");
		}
		if (dto.getBillReceivables().size() > SettlementConstants.MAX_LIST_SIZE) {
			throw new SettlementException("应收单数量大于"
					+ SettlementConstants.MAX_LIST_SIZE + "，不能进行修改操作");
		}

		logger.info("---Start 批量修改应收单的对账单号");

		dto.setActive(FossConstants.ACTIVE);// 是否有效设置有效
		dto.setModifyTime(new Date());
		dto.setModifyUserCode(currentInfo.getEmpCode());
		dto.setModifyUserName(currentInfo.getEmpName());

		int i = this.billReceivableEntityDao.updateBatchByMakeStatement(dto);
		if (i != dto.getBillReceivables().size()) {
			throw new SettlementException("批量修改应收单的对账单号服务失败");
		}

		logger.info("---End 批量修改应收单的对账单号");
	}

	// -------------------- valid methods --------------------

	/**
	 * 验证一个运单是否存在相同类型的多条应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-1 下午7:35:23
	 * @param list
	 */
	@Override
	public void validateWaybillForBillReceivable(List<BillReceivableEntity> list) {
		if (CollectionUtils.isNotEmpty(list)) {
			int orig = 0;// 始发标记
			int dest = 0;// 到达标记
			int cod = 0;// 代收货款标记

			for (BillReceivableEntity entity : list) {
				if (entity != null) {
					if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE
							.equals(entity.getBillType())) {
						orig += 1;
						if (orig > 1) {
							throw new SettlementException("同一个运单，存在多条始发应收单");
						}
					} else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
							.equals(entity.getBillType())// 专线到达运费应收单
							|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE
									.equals(entity.getBillType())// 偏线到达运费应收单
							|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY
									.equals(entity.getBillType())// 空运到达运费应收单
							|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_LAND_STOWAGE
							.equals(entity.getBillType()) // 到达快递代理应收 ISSUE-3389 小件业务
					          ) {
						dest += 1;
						if (dest > 1) {
							throw new SettlementException("同一个运单，存在多条到达运费应收单");
						}
					} else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE
							.equals(entity.getBillType())
							|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD
									.equals(entity.getBillType())
							|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD
									.equals(entity.getBillType()) // 快递代理代收货款应收单 ISSUE-3389 小件业务
							  ) {
						cod += 1;
						if (cod > 1) {
							throw new SettlementException("同一个运单，存在多条代收货款应收单");
						}
					}
				}
			}
		}

	}

	// -------------------- read methods --------------------

	/**
	 * 
	 * 根据传入的一到多个应收单号，获取一到多条应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-12 下午6:28:48
	 * @param receivableNos
	 *            应收单号集合
	 * @param active
	 *            是否有效
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<BillReceivableEntity> queryByReceivableNOs(
			List<String> receivableNos, String active) {
		if (CollectionUtils.isEmpty(receivableNos)) {
			throw new SettlementException("查询应收单，输入的应收单号不能为空！");
		}
		// 应收单号个数小于等于1000个时，直接查询返回
		if (receivableNos.size() <= SettlementConstants.MAX_LIST_SIZE) {
			return this.billReceivableEntityDao.queryByReceivableNOs(
					receivableNos, active);
		} 
		// 应收单号个数大于1000个时，分批查询
		else {
			List<BillReceivableEntity> recEntitys = new ArrayList<BillReceivableEntity>();
			List<String> queryRecNos = new ArrayList<String>();
			for (int index = 0; index < receivableNos.size(); index++) {
				queryRecNos.add(receivableNos.get(index));
				if (queryRecNos.size() == SettlementConstants.MAX_LIST_SIZE) {
					recEntitys = (List<BillReceivableEntity>) CollectionUtils
							.union(recEntitys, billReceivableEntityDao
									.queryByReceivableNOs(queryRecNos, active));
					queryRecNos.clear();
				}
			}
			if (CollectionUtils.isNotEmpty(queryRecNos)) {
				recEntitys = (List<BillReceivableEntity>) CollectionUtils
						.union(recEntitys, billReceivableEntityDao
								.queryByReceivableNOs(queryRecNos, active));
			}
			return recEntitys;
		}
	}

	/**
	 * 
	 * 根据传入的一个应收单号，获取一条应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-12 上午11:58:36
	 * @param receivableNo
	 *            应收单号
	 * @param active
	 *            是否有效
	 * @return
	 * @see
	 */
	public BillReceivableEntity queryByReceivableNO(String receivableNo,
			String active) {
		if (StringUtils.isEmpty(receivableNo)) {
			throw new SettlementException("查询应收单，输入的应收单号不能为空！");
		}
		List<String> receivableNos = new ArrayList<String>();
		receivableNos.add(receivableNo);
		List<BillReceivableEntity> list = this.billReceivableEntityDao
				.queryByReceivableNOs(receivableNos, active);

		// 判断是否为空
		if (CollectionUtils.isNotEmpty(list)) {

			// 判断是否有多个有效应收单
			if (list.size() != 1 && FossConstants.ACTIVE.equals(active)) {
				throw new SettlementException(String.format(
						"应收单号为:%s的有效应收单有多个，请检查数据是否正确", receivableNo));
			}

			return (BillReceivableEntity) list.get(0);
			
		}

		return null;
	}

	/**
	 * 
	 * 根据传入的一到多个来源单号，获取一到多条应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-15 下午5:26:27
	 * @param sourceBillNos
	 * @param sourceBillType
	 * @param active
	 * @return
	 */
	@Override
	public List<BillReceivableEntity> queryBySourceBillNOs(
			List<String> sourceBillNos, String sourceBillType, String active) {
		if (CollectionUtils.isEmpty(sourceBillNos)) {
			throw new SettlementException("查询应收单，输入的来源单号不能为空！");
		}
		return this.billReceivableEntityDao.queryBySourceBillNOs(sourceBillNos,
				sourceBillType, active);
	}

	/**
	 * 根据传入的运单号和单据类型等参数，查询[到付运费/始发/代收货款]有效应收单信息可公用
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-16 上午10:41:37
	 * @param dto
	 * @return
	 */
	@Override
	public List<BillReceivableEntity> queryBillReceivableByCondition(
			BillReceivableConditionDto dto) {

		// 满足以下(运单号和来源单据类型不能为空)条件才能进入，此查询方法
		if (dto != null
				&& (StringUtils.isNotEmpty(dto.getReceivableNo())
						|| StringUtils.isNotEmpty(dto.getWaybillNo()) || StringUtils
							.isNotEmpty(dto.getSourceBillNo()))) {
			// 有效单据
			dto.setActive(FossConstants.ACTIVE);

			// 非红单
			dto.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
			//应收单查询  灰度 ---- 353654
			List<BillReceivableEntity> list = null;
			String vestSystemCode = null;
	        try {
	        	ArrayList<String> arrayList = new ArrayList<String>();
	        	arrayList.add(dto.getWaybillNo());
	        	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
	        			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".queryBillReceivableByCondition",
	        			SettlementConstants.TYPE_FOSS);
	        	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
	        	List<VestBatchResult> list1 = response.getVestBatchResult();
	        	vestSystemCode = list1.get(0).getVestSystemCode();
			} catch (Exception e) {
				logger.info("灰度分流失败,"+"运单号："+dto.getWaybillNo());
				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			}
	        if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
	        	list = billReceivableEntityDao.queryBillReceivableByCondition(dto);
			}
			if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
				//TODO  查询应收单
			}
			//应收单查询  灰度 ---- 353654
			return list;
		} else {
			throw new SettlementException("查询应收单，输入的单据号码不能为空！");
		}
	}
	
	
	/**
	 * 查询始发月结
	 * ECS-327090
	 * @date 2016-5-24 
	 * 
	 */
	@Override
	public List<BillReceivableEntity> queryBillReceivableMonthlyStatement(
			BillReceivableConditionDto dto) {
		// 满足以下(运单号和来源单据类型不能为空)条件才能进入，此查询方法
		if (dto != null && StringUtils.isNotEmpty(dto.getWaybillNo())) {
			// 有效单据
			dto.setActive(FossConstants.ACTIVE);

			// 非红单
			dto.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);

			return billReceivableEntityDao.queryBillReceivableMonthlyStatement(dto);
		} else {
			throw new SettlementException("查询应收单，输入的单据号码不能为空！");
		}
	}

	/**
	 * 根据运单号查询客户的应收单到付金额和应收代收货款金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-2 上午8:49:02
	 * @param dto
	 * @return
	 */
	@Override
	public List<BillReceivableEntity> queryReceivableAmountByCondition(
			BillReceivableConditionDto dto) {
		if (dto == null || StringUtils.isEmpty(dto.getWaybillNo())) {
			throw new SettlementException("查询应收单，输入的运单号不能为空！");
		}
		dto.setActive(FossConstants.ACTIVE);
		dto.setIsRedBack(FossConstants.NO);
		dto.setBillTypes(new String[] {
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,// 到达应收单
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY,// 空运到达应收单
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE,// 代收货款应收单
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD, // 空运代收货款应收单
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE, // 到达偏线代理应收单
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE //合伙人到付运费应收
		});
		dto.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL);
		List<BillReceivableEntity> list  = this.billReceivableEntityDao.queryReceivableAmountByCondition(dto);
		return list;
	}

	/**
	 * 根据运单号和外发单号、客户编码、判断是否已存在有效的偏线到达应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-19 下午3:34:51
	 * @param dto
	 * @return
	 */
	@Override
	public int queryReceivableByExternalBillNo(BillReceivableConditionDto dto) {
		if (dto != null && StringUtils.isNotEmpty(dto.getWaybillNo())
				&& StringUtils.isNotEmpty(dto.getExternalBillNo())
				&& StringUtils.isNotEmpty(dto.getPartailLineAgentCode())) {
			dto.setActive(FossConstants.ACTIVE);
			dto.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
			return this.billReceivableEntityDao
					.queryReceivableByExternalBillNo(dto);
		} else {
			throw new SettlementException("查询偏线到达运费应收单，参数不能为空");
		}
	}

	/**
	 * 根据ID（或应收单）与分区键查询应收单 根据【id,accountDate】或者【receiveNos,accountDate】批量查询
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-11-29 下午2:34:58
	 * @param dtos
	 */
	@Override
	public List<BillReceivableEntity> queryPartitionsByConditions(
			BillReceivableDto dtos) {
		if (dtos == null || CollectionUtils.isEmpty(dtos.getBillReceivables())) {
			throw new SettlementException("查询参数不能为空！");
		}
		List<BillReceivableEntity> list = dtos.getBillReceivables();

		int ids = 0;
		int receiveNos = 0;
		int dates = 0;

		for (BillReceivableEntity item : list) {
			if (StringUtils.isNotEmpty(item.getId())) {
				ids += 1;
			}
			if (StringUtils.isNotEmpty(item.getReceivableNo())) {
				receiveNos += 1;
			}
			if (item.getAccountDate() != null) {
				dates += 1;
			}
		}

		if (ids == list.size() && dates == list.size()) {
			return billReceivableEntityDao.queryPartitionsByIds(dtos);
		} else if (receiveNos == list.size() && dates == list.size()) {
			return billReceivableEntityDao.queryPartitionsByReceivableNos(dtos);
		} else {
			throw new SettlementException("查询参数错误");
		}

	}

	/**
	 * 按照运单号和应付部门编码集合查询应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-28 下午3:25:57
	 * @param waybillNos
	 * @param orgCodeList
	 * @param active
	 * @param currentInfo
	 * @return
	 */
	@Override
	public List<BillReceivableEntity> queryByWaybillNosAndOrgCodes(
			List<String> waybillNos, List<String> orgCodeList, String active,
			CurrentInfo currentInfo) {
		if (CollectionUtils.isEmpty(waybillNos)) {
			throw new SettlementException(" 按照运单号和应付部门编码集合查询应收单信息参数不能为空");
		}
		return this.billReceivableEntityDao.queryByWaybillNosAndOrgCodes(
				waybillNos, orgCodeList, active, currentInfo);
	}

	/**
	 * 根据运单号，查询开单付款方式为网上支付的应收单的未核销金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-15 下午5:31:40
	 * @param waybillNo
	 * @return
	 */
	@Override
	public WaybillReceivableDto queryReceivableAmountByWaybillNO(
			String waybillNo) {
		if (StringUtils.isEmpty(waybillNo)) {
			throw new SettlementException("按照运单号查询应收单信息，运单号不能为空");
		}
		return this.billReceivableEntityDao
				.queryReceivableAmountByWaybillNO(waybillNo);
	}

	/**
	 * 根据运单号集合或来源单号结合，单据类型集合查询应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-16 下午3:06:56
	 * @param waybillNos
	 * @param sourceBillNos
	 * @param sourceBillType
	 * @param billTypes
	 * @return
	 */
	@Override
	public List<BillReceivableEntity> queryByWaybillNosOrSourceBillNosAndBillTypes(
			List<String> waybillNos, List<String> sourceBillNos,
			String sourceBillType, List<String> billTypes, String active,
			String isRedBack) {
		if (CollectionUtils.isEmpty(waybillNos)
				&& CollectionUtils.isEmpty(sourceBillNos)) {
			throw new SettlementException("查询应收单传入的参数不能为空！");
		}
		if (CollectionUtils.isEmpty(billTypes)) {
			throw new SettlementException("单据类型不能为空！");
		}
		active = StringUtils.isEmpty(active) ? FossConstants.ACTIVE : active;
		isRedBack = StringUtils.isEmpty(isRedBack) ? SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO
				: isRedBack;
		return this.billReceivableEntityDao
				.queryByWaybillNosOrSourceBillNosAndBillTypes(waybillNos,
						sourceBillNos, sourceBillType, billTypes, active,
						isRedBack);
	}

	/**
	 * 根据客户编码，获取客户是否存在应收未核销金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-16 下午8:36:19
	 * @param customerCode
	 * @return
	 */
	@Override
	public boolean queryIsExistsReceivableAmountByCustomerCode(
			String customerCode) {
		List<BillReceivableEntity> list = this.billReceivableEntityDao
				.queryReceivableAmountByCustomerCode(customerCode);
		if (CollectionUtils.isNotEmpty(list)) {
			return true;
		}
		return false;
	}

	/**
	 * 根据客户编码，获取客户存在的应收未核销金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-16 下午8:36:19
	 * @param customerCode
	 * @return
	 */
	@Override
	public List<BillReceivableEntity> queryReceivableAmountByCustomerCode(
			String customerCode) {
		List<BillReceivableEntity> list = this.billReceivableEntityDao
				.queryReceivableAmountByCustomerCode(customerCode);
		return list;
	}
	
	/**
	 * 根据来源单号集合和应收部门编码集合，查询应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-22 下午4:38:08
	 * @param sourceBillNos
	 * @param orgCodes
	 * @param sourceBillType
	 * @param active
	 * @return
	 */
	@Override
	public List<BillReceivableEntity> queryBySourceBillNOsAndOrgCodes(
			List<String> sourceBillNos, List<String> orgCodes,
			String sourceBillType, String active, CurrentInfo currentInfo) {
		if (CollectionUtils.isEmpty(sourceBillNos)) {
			throw new SettlementException("按照来源单号集合和应收部门编码集合，查询应收单传入的参数不能为空！");
		}
		return this.billReceivableEntityDao.queryBySourceBillNOsAndOrgCodes(
				sourceBillNos, orgCodes, sourceBillType, active, currentInfo);
	}
	
 	/**
 	 * 锁定应收单信息
 	 * @author 088933-foss-zhangjiheng
 	 * @date 2012-11-23 上午9:05:22
 	 */
	@Override
 	public int updateReceiveBillInfoForLock(BillReceivableOnlineQueryDto queryDto){
		return billReceivableEntityDao.updateReceiveBillInfoForLock(queryDto);
	}

	/**
	 * 根据应收单号集合批量标记和反标记状态
	 * @author 045738-foss-maojianqiang
	 * @date 2013-5-21 下午6:43:52
	 * @param numbers
	 * @param stampFlag
	 * @param active
	 * @param currentInfo
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService#updateStampByNumbers(java.util.List, java.lang.String, java.lang.String, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void updateStampByNumbers(BillReceivableDto dto,CurrentInfo currentInfo) {

		logger.info(" 批量标记/反标记应收单 Start ");
		//校验参数
		if (dto == null || CollectionUtils.isEmpty(dto.getBillReceivables())) {
			throw new SettlementException("标记/反标记应收单集合不能为空");
		}
		//校验操作状态
		if (StringUtils.isBlank(dto.getStamp())) {
			throw new SettlementException("操作状态不能为空！");
		}
		//校验有效标志
		if (StringUtils.isBlank(dto.getActive())) {
			throw new SettlementException("有效标志不能为空！");
		}
		//设置最后更新时间等值
		dto.setModifyTime(new Date());
		dto.setModifyUserCode(currentInfo.getEmpCode());
		dto.setModifyUserName(currentInfo.getEmpName());
		int i = this.billReceivableEntityDao.updateStampByNumbers(dto);
		if (i != dto.getBillReceivables().size()) {
			throw new SettlementException("批量标记/反标记应收单失败");
		}
		
		logger.info(" 批量标记/反标记应收单  end ");
		
	}


	/**
     * 按应收单号和数据权限查询应收单
     * @author 045738-foss-maojianqiang
     * @date 2013-6-12 下午6:36:24
     * @param receivableNos
     * @param active
     * @param currentInfo
     * @return
     */
	public List<BillReceivableEntity> queryByReceivableNosAndOrgCodes(
			List<String> receivableNos, String active, CurrentInfo currentInfo) {
		//用手段号不能为空
		if (CollectionUtils.isEmpty(receivableNos)) {
			throw new SettlementException(" 按照应收单号和数据权限查询应收单信息参数不能为空");
		}
		return this.billReceivableEntityDao.queryByReceivableNosAndOrgCodes(receivableNos, active, currentInfo);
	}
	
	/**
     * 根据客户，部门查询最小欠款时间
     * @author lianghaisheng
     * @date 2013-6-12 下午6:36:24
     * @param dto 客户 部门信息
     * @return 最小欠款时间
     */
	@Override
	public Date queryMinDebitTime(BillReceivableConditionDto dto) {
		if(StringUtils.isEmpty(dto.getCustomerCode())){
			throw new SettlementException("客户编码为空");	
		}else if (StringUtils.isEmpty(dto.getOrgCode())){
			throw new SettlementException("部门编码为空");
		}
		//设置为有效
		dto.setActive(FossConstants.YES);
		
		Date minDate = billReceivableEntityDao.queryMinTimebyCondition(dto);
		return minDate;
	}
	
	/**
	 * 根据传入的一到多个运单单号，获取一到多条应收单信息
     * @author 邓大伟
     * @date 2014-12-12
     * @param wayBillNos  运单单号集合
	 */
	@Override
	public List<ReceivableEntity> queryByWaybillNOs(List<String> waybillNos,List<String> waybillList) {
		//返回DOP应收集合
		List<ReceivableEntity> receivableEntityList = new ArrayList<ReceivableEntity>();
		//查询应收单
		List<BillReceivableEntity> billReceivablelist = billReceivableEntityDao.queryByWaybillNOs(waybillNos);
		String billTypeDR = "";
		String billTypeOR = "";
		//判断查询应收单是否为空
		if(CollectionUtils.isEmpty(billReceivablelist)){
			throw new SettlementException("没有查询到数据。");
		}else{
			//判断是否存在未受理的更改单
			if(CollectionUtils.isNotEmpty(waybillList)){
				throw new SettlementException("存在更改单未受理，请联系发货部门。");
			}else{
				//循环处理应收单
				for(int i = 0;i < billReceivablelist.size();i++){
					BillReceivableEntity billReceivableEntity = billReceivablelist.get(i);
					SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentInfoByCode(billReceivableEntity.getOrigOrgCode());
					
					/*
					 * modify by 269044-zhurongrong 2016-10-28
					 * 新增校验快递到付应收单据挂在非虚拟部门才可第三方支付,补码后且到达部门非虚拟部门才可进行支付。
					 */
					if(StringUtils.isNotEmpty(billReceivableEntity.getDestOrgCode())) {
						//根据到达部门编码查询部门信息
						OrgAdministrativeInfoEntity destOrgEntity = orgAdministrativeInfoService
								.queryOrgAdministrativeInfoByCodeClean(billReceivableEntity.getDestOrgCode());
						//部门不为空，并且不是虚拟部门
						if(destOrgEntity == null || FossConstants.YES.equals(destOrgEntity.getExpressSalesDepartment())) {
							throw new SettlementException("到达部门" + billReceivableEntity.getDestOrgCode() + "还是虚拟营业部，请先补码再进行支付");
						}
					}//end
					//判断是否存在多条应收单
					if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE.equals(billReceivableEntity.getBillType())){
						billTypeDR = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE;
					}
					if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE.equals(billReceivableEntity.getBillType())){
						billTypeOR = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE;
					}
					validaEntity(receivableEntityList, billTypeDR, billTypeOR,
							billReceivableEntity, saleDepartmentEntity);
				}
			}
		}
		return receivableEntityList;
	}

	private void validaEntity(List<ReceivableEntity> receivableEntityList,
			String billTypeDR, String billTypeOR,
			BillReceivableEntity billReceivableEntity,
			SaleDepartmentEntity saleDepartmentEntity) {
		if(StringUtil.isNotBlank(billTypeDR) && StringUtil.isNotBlank(billTypeOR)){
			throw new SettlementException("运单同时存在到达和始发付款金额，请联系提货或发货部门。");
		//判断应收单是否做了对账单
		}else if(StringUtil.isNotBlank(billReceivableEntity.getStatementBillNo())
			&& !SettlementConstants.DEFAULT_BILL_NO.equals(billReceivableEntity.getStatementBillNo())){
			throw new SettlementException("已被制作对账单，请联系发货部门。");
		//判断应收单是否已经全部核销
		}else if(billReceivableEntity.getUnverifyAmount().compareTo(BigDecimal.valueOf(0)) == 0){
			throw new SettlementException("运单已结清。");
		}else if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE.equals(billReceivableEntity.getBillType())
				|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD.equals(billReceivableEntity.getBillType())
				|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD.equals(billReceivableEntity.getBillType())){
			throw new SettlementException("运单存在代收货款。");
		//判断是否合伙人开单
		}else if(null != saleDepartmentEntity && FossConstants.ACTIVE.equals(saleDepartmentEntity.getIsLeagueSaleDept())){
				throw new SettlementException("始发部门为合伙人不能网上支付。");
		}else if(SettlementDictionaryConstants.STL__WITHHOLD_STATUS__WITHHOLD_FAILED.equals(billReceivableEntity.getWithholdStatus())
				|| SettlementDictionaryConstants.STL__WITHHOLD_STATUS__WITHHOLD_SUCCESS.equals(billReceivableEntity.getWithholdStatus())){
			throw new SettlementException("应收单扣款状态为扣款成功或失败不能进行网上支付。");
		}else{
			validaReceivable(receivableEntityList,
					billReceivableEntity);
		}
	}

	private void validaReceivable(List<ReceivableEntity> receivableEntityList,
			BillReceivableEntity billReceivableEntity) {
		ReceivableEntity receivableEntity = new ReceivableEntity();
		//应收单单号
		receivableEntity.setReceivableNo(billReceivableEntity.getReceivableNo());
		//未核销金额
		receivableEntity.setUnverifyAmount(billReceivableEntity.getUnverifyAmount());
		//运单号
		receivableEntity.setWaybillNo(billReceivableEntity.getWaybillNo());
		//客户编码
		receivableEntity.setCustomerCode(billReceivableEntity.getCustomerCode());
		//应收部门名称
		receivableEntity.setReceivableOrgName(billReceivableEntity.getReceivableOrgName());
		//是否错误
		receivableEntity.setIsError(FossConstants.NO);
		//错误消息
		receivableEntity.setErrorMsg(null);
		//根据部门编码查询部门信息
		OrgAdministrativeInfoEntity orgReceiveEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(billReceivableEntity.getReceivableOrgCode());
		//判断部门实体是否为空
		if (orgReceiveEntity != null) {
			//标杆编码
			receivableEntity.setUnifiedCode(orgReceiveEntity.getUnifiedCode());
		}
		//到达部门编码
		receivableEntity.setDestOrgCode(billReceivableEntity.getDestOrgCode());
		//到达部门名称
		receivableEntity.setDestOrgName(billReceivableEntity.getDestOrgName());
		//单据子类型
		receivableEntity.setBillType(billReceivableEntity.getBillType());
		//将应收实体添加到返回给DOP集合中
		receivableEntityList.add(receivableEntity);
	}
	

	@Override
	public List<BillReceivableEntity> queryReceivableByWaybillNOs(List<String> waybillNos) {
		//查询应收单
		List<BillReceivableEntity> billReceivablelist = billReceivableEntityDao.queryByWaybillNOs(waybillNos);
		return billReceivablelist;
	}
	/**
	 * 根据运单号查询有效的应收单（单据子类型为单一的一种）
	 * 设想是根据运单号、一种单据子类型查这个单据子类型的应收单，返回结果只取一条
	 * @author 231438
	 * @return
	 */
	@Override
	public BillReceivableEntity queryReceivableByWaybillNoAndBillType(BillReceivableConditionDto dto){
		if(dto == null || StringUtils.isEmpty(dto.getWaybillNo())){
			throw new SettlementException("查询应收单，输入的单据号码不能为空！");
		}
		//根据条件查应收单
		List<BillReceivableEntity> billReceives = this.queryBillReceivableByCondition(dto);
		// 校验应收单合法性(代收货款应收单)
		validateWaybillForBillReceivable(billReceives);
		//validateWaybillForBillReceivable中有校验一种单据子类型只能有一条有效的应收单
		return CollectionUtils.isNotEmpty(billReceives) ? billReceives.get(0) : null ;
	}
	/**
	 * 合伙人应收单红冲
	 * @author 231438
	 */
	@Override
	public void writeBackPartnerBillReceivable(BillReceivableEntity entity, CurrentInfo currentInfo){
		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getId())
				|| StringUtils.isEmpty(entity.getReceivableNo())
				|| entity.getAccountDate() == null) {
			throw new SettlementException("红冲应收单参数不能为空！");
		}

		logger.info("entering service, sourceBillNo: "
				+ entity.getSourceBillNo());

		Date now = Calendar.getInstance().getTime();

		// 当前系统时间，小于应收单的解锁时间时不能被红冲
		if (entity.getUnlockDateTime() != null
				&& now.before(entity.getUnlockDateTime())) {
			throw new SettlementException("应收单已被锁定，不能进行红冲应收单操作");
		}

		// 已核销,不能红冲
		if (entity.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
			throw new SettlementException("应收单已核销，不能进行红冲应收单操作");
		}
		// 校验对账单
		if (StringUtils.isEmpty(entity.getStatementBillNo())
				|| SettlementConstants.DEFAULT_BILL_NO.equals(entity
						.getStatementBillNo())) {
			// do nothing
		} else {
			List<String> list = statementOfAccountService
					.queryConfirmStatmentOfAccount(Arrays.asList(entity
							.getStatementBillNo()));
			if (CollectionUtils.isEmpty(list)) { // 对账单未确认,则更新对账相关信息
				StatementOfAccountUpdateDto dto = new StatementOfAccountUpdateDto();
				dto.setReceivableEntityList(Arrays.asList(entity));
				statementOfAccountService.updateStatementAndDetailForRedBack(
						dto, currentInfo);
			} else { // 对账单已确认则不允许红冲单据
				logger.error("合伙人应收单红冲。该单存在相应的客户对账单已确认、核销、付款、还款，不能进行红冲应收单操作");
				throw new SettlementException(
						"该单存在相应的客户对账单已确认、核销、付款、还款，不能进行红冲应收单操作");
			}
		}

		BillReceivableEntity updateEntity = new BillReceivableEntity();

		// 作废原应收单
		updateEntity.setId(entity.getId()); // ID
		updateEntity.setAccountDate(entity.getAccountDate()); // 分区键
		updateEntity.setVersionNo(entity.getVersionNo()); // 版本号
		updateEntity.setActive(FossConstants.INACTIVE);
		updateEntity.setModifyTime(now);

		// 操作者信息
		updateEntity.setModifyUserCode(currentInfo.getEmpCode());
		updateEntity.setModifyUserName(currentInfo.getEmpName());
		updateEntity.setNotes(new Date()+"失效合伙人应收单  ");

		int i = this.billReceivableEntityDao
				.updateBillReceivableByWriteBack(updateEntity);
		if (i != 1) {
			logger.error("合伙人应收单红冲。红冲应收单出错，失效原单数量不为1。");
			throw new SettlementException("红冲应收单出错");
		}

		// 生成红冲单
		BillReceivableEntity redBackEntity = new BillReceivableEntity();
		BeanUtils.copyProperties(entity, redBackEntity);

		// 保存一条和原单据相同的新红单实体
		redBackEntity.setId(UUIDUtils.getUUID());
		redBackEntity.setActive(FossConstants.INACTIVE); // 设置为无效状态
		redBackEntity.setVersionNo(FossConstants.INIT_VERSION_NO); //版本号，用于版本控制
		redBackEntity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__YES); // 红单
		redBackEntity.setAccountDate(now); // 设置记账日期
		redBackEntity.setCreateTime(now); // 创建时间
		redBackEntity.setModifyTime(now); // 修改时间
		//总费用
		redBackEntity.setAmount(NumberUtils.multiply(redBackEntity.getAmount(),
				-1));
		//未核销金额
		redBackEntity.setUnverifyAmount(NumberUtils.multiply(
				redBackEntity.getUnverifyAmount(), -1));
		//已核销金额
		redBackEntity.setVerifyAmount(NumberUtils.multiply(
				redBackEntity.getVerifyAmount(), -1));

		//运费
		redBackEntity.setTransportFee(NumberUtils.multiply(
				redBackEntity.getTransportFee(), -1));
		
		//接货费
		redBackEntity.setPickupFee(NumberUtils.multiply(
				redBackEntity.getPickupFee(), -1));
		
		//送货费
		redBackEntity.setDeliveryGoodsFee(NumberUtils.multiply(
				redBackEntity.getDeliveryGoodsFee(), -1));
		//包装费
		redBackEntity.setPackagingFee(NumberUtils.multiply(
				redBackEntity.getPackagingFee(), -1));
		//代收货款手续费
		redBackEntity.setCodFee(NumberUtils.multiply(redBackEntity.getCodFee(),
				-1));
		//保价费
		redBackEntity.setInsuranceFee(NumberUtils.multiply(
				redBackEntity.getInsuranceFee(), -1));
		//其它费
		redBackEntity.setOtherFee(NumberUtils.multiply(
				redBackEntity.getOtherFee(), -1));
		//增值费
		redBackEntity.setValueAddFee(NumberUtils.multiply(
				redBackEntity.getValueAddFee(), -1));
		//优惠费
		redBackEntity.setPromotionsFee(NumberUtils.multiply(
				redBackEntity.getPromotionsFee(), -1));

		// 操作者信息
		redBackEntity.setModifyUserCode(currentInfo.getEmpCode());
		redBackEntity.setModifyUserName(currentInfo.getEmpName());
		redBackEntity.setNotes(new Date()+"生成合伙人红单  ");

		this.add(redBackEntity);
		logger.info("successfully exit service, sourceBillNo: " + entity.getSourceBillNo());
	}


    /**
     * 修改应收单扣款状态
     *
     *@author 099995-foss-hemingyu
     * @date 2016-01-08 上午10:59:38
     * @param entity
     *            应收单实体
     *
     */
    public void updateBillReceivableWithholdStatus(BillReceivableEntity entity) {
        // 输入参数校验主要校验id和应收单号ReceivableNo
        if (entity == null || StringUtils.isEmpty(entity.getId())
                || StringUtils.isEmpty(entity.getReceivableNo())) {
            throw new SettlementException("确认应收单的应收单号和运单号不能为空！");
        }

        logger.info("entering service, id: " + entity.getId());

        //修改应收单扣款状态并返回信息，每次修改一条
        int i = this.billReceivableEntityDao
                .updateBillReceivableWithholdStatus(entity);
        if (i != 1) {
            throw new SettlementException("确认应收单修改扣款状态操作失败。");
        }

        logger.info("successfully exit service, id: " + entity.getId());
    }

    /*
    *@author 099995-foss-hemingyu
     * @date 2016-01-14 上午15:47:38
     * @param wayBillNo
     *            运单号
     * @param billType
     *            应收类型
     */
    public BillReceivableEntity selectByWayBillNoAndBillType(String wayBillNo,String billType) {
        if (StringUtils.isEmpty(wayBillNo) || StringUtils.isEmpty(billType)) {
            throw new SettlementException("查询应收单，输入的运单号和应收单类型不能为空！");
        }

        List<BillReceivableEntity>  billReceivableEntityList= this.billReceivableEntityDao
                .selectByWayBillNoAndBillType(wayBillNo, billType);

        // 判断是否为空
        if (CollectionUtils.isEmpty(billReceivableEntityList)) {
            return null;
        }else if(billReceivableEntityList.size() != 1){
            throw new SettlementException(String.format(
                    "运单号为:%s的有效应收单有多条数据，请检查数据是否正确", wayBillNo));
        }else{
            BillReceivableEntity billReceivableEntity = billReceivableEntityList.get(0);
            return billReceivableEntity;
        }
    }


	/**
	 * 根据应收单号更新对账单
	 * @author 尤坤
	 * @param map
	 * @return
	 */
	public void updateByReceivableNo(Map<String,Object> map) {
		try {
			this.billReceivableEntityDao.updateByReceivableNo(map);
		}catch (SettlementException e){
			logger.error("根据应收单号更新对账单操作失败!"+e.getMessage());
			throw new SettlementException("根据应收单号更新对账单操作失败!");
		}

	}

	/**
	 * @author ddw
	 * 根据对账单单号查询应收单
	 * @param statementBillNoList
	 * @return
	 */
	@Override
	public List<BillReceivableEntity> queryReceivableByStatementBillNo(List<String> statementBillNoList) {
		if(CollectionUtils.isEmpty(statementBillNoList)){
			throw new SettlementException("对账单单号为空！");
		}
		List<BillReceivableEntity> list = billReceivableEntityDao.queryReceivableByStatementBillNo(statementBillNoList);
		return list;
	}
	
	/**
	 * @author zlp
	 * 根据对账单单号查询应收单
	 * @param statementBillNoList
	 * @return
	 */
	@Override
	public List<BillReceivableEntity> queryReceivableBySBNO(List<String> statementBillNoList) {
		if(CollectionUtils.isEmpty(statementBillNoList)){
			throw new SettlementException("对账单单号为空！");
		}
		List<BillReceivableEntity> list = billReceivableEntityDao.queryReceivableBySBNO(statementBillNoList);
		return list;
	}
	
	@Override
	public BillReceivableEntity queryBillReceivableByReceivableNO(String receivableNo, String active) {
		BillReceivableEntity entity = billReceivableEntityDao.queryBillReceivableByReceivableNO(receivableNo,active);
		return entity;
	}
	
	@Override
	public List<BillReceivableEntity> queryReceivableByStatementBillNoAuto(String statementBillNo) {
		if(StringUtil.isEmpty(statementBillNo)){
			throw new SettlementException("对账单单号为空！");
		}
		List<BillReceivableEntity> list = billReceivableEntityDao.queryReceivableByStatementBillNoAuto(statementBillNo);
		return list;
	}
	
	/*
	 * 根据查询条件查询是否存在重复的应收单
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService#checkBillRepeated(com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity)
	 */
	@Override
	public List<BillReceivableEntity> checkReceivableBillRepeated(
			BillReceivableEntity queryEntity) {
		logger.info("校验合伙人应收单是否重复校验条件："
				+ ReflectionToStringBuilder.toString(queryEntity));
		if(queryEntity == null){
			logger.error("校验合伙人应收单是否重复查询条件为空");
			throw new SettlementException("校验合伙人应收单是否重复查询条件为空");
		}
		if(StringUtils.isBlank(queryEntity.getSourceBillNo())){
			logger.error("校验合伙人应收单是否重复来源单据号为空");
			throw new SettlementException("校验合伙人应收单是否重复来源单据号为空");
		}
		if(StringUtils.isBlank(queryEntity.getBillType())){
			logger.error("校验合伙人应收单是否重复单据子类型为空");
			throw new SettlementException("校验合伙人应收单是否重复单据子类型为空");
		}
		if (StringUtils.isBlank(queryEntity.getCustomerCode())) {
			logger.error("校验合伙人应收单是否重复合伙人编码为空");
			throw new SettlementException("校验合伙人应收单是否重复合伙人编码为空");
		}
		if (queryEntity.getAmount() == null
				|| BigDecimal.ZERO.compareTo(queryEntity.getAmount()) >= 0) {
			logger.error("校验合伙人应收单是否重复应收单总金额为空");
			throw new SettlementException("校验合伙人应收单是否重复应收单总金额为空");
		}
		
		queryEntity.setActive(FossConstants.ACTIVE);//有效
		queryEntity.setIsRedBack(FossConstants.NO);//非红单
		return billReceivableEntityDao.checkReceivableBillRepeated(queryEntity);
	}


	/**
	 * 根据传入运单号查询应收单已核销金额之和
	 * @author 379106
	 * @param waybills
	 * @return
	 */
	public Map<String,BigDecimal> queryReceivableVeryfyAmountsByWaybill(List<String> waybills){
		if(null==waybills||waybills.isEmpty())
			throw new SettlementException("未传入运单号,请核实数据");
		Map<String,BigDecimal> map=new HashMap<String, BigDecimal>();
		if(waybills.size()>FossConstants.ORACLE_MAX_IN_SIZE){
			List<List<String>> idsLists = com.deppon.foss.util.CollectionUtils.splitListBySize(waybills, FossConstants.ORACLE_MAX_IN_SIZE);
			for (List<String> strings : idsLists){
				List<BillReceivableEntity> list = billReceivableEntityDao.queryReceivableVeryfyAmountsByWaybill(strings);
				if (null != list && list.size() > 0) {
					for (BillReceivableEntity billReceivableEntity : list) {
						map.put(billReceivableEntity.getWaybillNo(), billReceivableEntity.getVerifyAmount());
					}
				}
			}
		}else {
			List<BillReceivableEntity> list = billReceivableEntityDao.queryReceivableVeryfyAmountsByWaybill(waybills);
			if (null != list && list.size() > 0) {
				for (BillReceivableEntity billReceivableEntity : list) {
					map.put(billReceivableEntity.getWaybillNo(), billReceivableEntity.getVerifyAmount());
				}
			}
		}
		if(null!=map&&map.size()>0){
			return map;
		}
		return null;
	}

	/**
	 * @param creditMsgService
	 *            the creditMsgService to set
	 */
	public void setCreditMsgService(ICreditMsgService creditMsgService) {
		this.creditMsgService = creditMsgService;
	}

	/**
	 * @param billReceivableEntityDao
	 *            the billReceivableEntityDao to set
	 */
	public void setBillReceivableEntityDao(IBillReceivableEntityDao billReceivableEntityDao) {
		this.billReceivableEntityDao = billReceivableEntityDao;
	}

	/**
	 * @param customerBargainService
	 *            the customerBargainService to set
	 */
	public void setCustomerBargainService(ICustomerBargainService customerBargainService) {
		this.customerBargainService = customerBargainService;
	}

	/**
	 * @param statementOfAccountService
	 *            the statementOfAccountService to set
	 */
	public void setStatementOfAccountService(IStatementOfAccountService statementOfAccountService) {
		this.statementOfAccountService = statementOfAccountService;
	}

	/**
	 * @param waybillChangeMsgService
	 *            the waybillChangeMsgService to set
	 */
	public void setWaybillChangeMsgService(IWaybillChangeMsgService waybillChangeMsgService) {
		this.waybillChangeMsgService = waybillChangeMsgService;
	}

	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	@Override
	public int queryIsOrPayByBillNo(String[] billTypes, String wayBillNo, String rePaymentNo, String sourceNo,  String actualPayType) {
		return billReceivableEntityDao.queryIsOrPayByBillNo(billTypes, wayBillNo, rePaymentNo, sourceNo, actualPayType);
	}

	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	public void setCubcQueryReceivableAmountService(
			CUBCQueryReceivableAmountService cubcQueryReceivableAmountService) {
		this.cubcQueryReceivableAmountService = cubcQueryReceivableAmountService;
	}

}
