package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.pay.api.server.dao.IDopStatementPaymentDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IDopStatementPaymentService;
import com.deppon.foss.module.settlement.pay.api.server.service.IPayToFSSCSendService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentAddDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.PayToCostcontrolDto;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IDopStatementService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.HomeStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.HomeStatementEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 家装
 * 
 * @ClassName: HomeStatementPaymentService
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-12-10 下午6:15:12
 */

public class DopStatementPaymentService implements IDopStatementPaymentService {
	private static final Logger logger = LogManager
			.getLogger(DopStatementPaymentService.class);

	/**
	 * 定义的接口描述
	 */
	private static final String PAYDESC = "申请付款工作流";

	/**
	 * 注入结算单据编号生成service
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 注入付款单公共服务接口
	 */
	private IBillPaymentService billPaymentService;

	/**
	 * 注入应付单服务接口
	 */
	private IBillPayableService billPayableService;

	/**
	 * 注入应收单SERVICE
	 */
	private IBillReceivableService billReceivableService;

	/**
	 * 注入财务共享接口
	 */
	private IPayToFSSCSendService payToFSSCSendService;

	/**
	 * 注入核销单SERVICE
	 */
	private IBillWriteoffService billWriteoffService;

	/**
	 * 注入家装Service
	 */
	private IDopStatementService dopStatementService;
	
	/**
	 * 注入部门service
	 */
	private IOrgAdministrativeInfoService  orgAdministrativeInfoService;
	
	public void setDopStatementService(IDopStatementService dopStatementService) {
		this.dopStatementService = dopStatementService;
	}

	/**
	 * 家装付款Dao
	 */
	private IDopStatementPaymentDao dopStatementPaymentDao;

	public void setDopStatementPaymentDao(
			IDopStatementPaymentDao dopStatementPaymentDao) {
		this.dopStatementPaymentDao = dopStatementPaymentDao;
	}

	/**
	 * 对账单付款
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional
	public String dopToPayment(BillPaymentEntity paymentEntity,String statementBillNo, CurrentInfo currentInfo) {
		
		logger.info("进入录入付款单service: 金额 " + paymentEntity.getAmount());
		// 获取付款金额
		BigDecimal amount = paymentEntity.getAmount();
		logger.info(paymentEntity);
		// 如果对账单制作参数dto为空
		if (null == statementBillNo || "".equals(statementBillNo)) {
			// 提示传入参数为空，无法核销
			throw new SettlementException("传入参数为空，无法核销");
		}
		/**
		 * 1、按对账单号去查询对账单，返回对账单实体。
		 */
		// 按对账单单号查询对账单
		HomeStatementEntity homeStatementEntity = dopStatementService.queryByStatementNo(statementBillNo);
		
		// 对账单确认状态判断
		if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM.equals(homeStatementEntity.getConfirmStatus())) {
			throw new SettlementException("对账单确认状态为未确认不可以进行付款!");
		}
		
		
		/**
		 * 2、对账单应收冲应付 
		 * 		a)获取应收单号,应付单号 应收单和应付单并没有改变
		 */

		// 应收冲应付
		List<BillWriteoffOperationDto> writeoffResult = writeoffStatement(homeStatementEntity, currentInfo);
		/**
		 * 将所有的应付单号实体封装在一起
		 */
		
		// 声明当前日期
		Date now = new Date();
		// 获取单据编号 （此处单号是手动生成付款单）
		//String paymentNum = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.FK1);
		/**
		 * 设置付款单号
		 */
		//payNo = paymentNum;
		/*if (StringUtils.isBlank(paymentNum)) {
			throw new SettlementException("获取付款单号有误！");
		}*/
		List<BillPaymentAddDto> addDtoList = new ArrayList<BillPaymentAddDto>();
		
		// 获取应付单号
		for(BillWriteoffOperationDto billWriteoffOperationResult:writeoffResult){
			List<BillPayableEntity> list = billWriteoffOperationResult.getBillPayableEntitys();
			if(list == null) break;//数据为空，则退出循环
			for (BillPayableEntity pay : list) {
				// 判断应付单是否已经核销
				if (pay.getUnverifyAmount().compareTo(BigDecimal.ZERO) > 0) {
					// 将属性copy到付款单明细dto中
					BillPaymentAddDto addDto = new BillPaymentAddDto();
					// 属性copy
					BeanUtils.copyProperties(pay, addDto);
					// 设置属性值
					addDto.setCurrentPaymentAmount(pay.getUnverifyAmount());
					addDtoList.add(addDto);
				}
			}
		}
		
		// 校验传入参数 -- -包含付款单实体的工作流类型也在里面进行封装
		Map map = validateAddPaymentForFSSC(paymentEntity, addDtoList,
				"", currentInfo);
		
		// 从map中获取应付单集合
		List<BillPayableDto> payableList = (List<BillPayableDto>) map.get("payableList");
		// 付款单明细列表
		//List<BillPaymentDEntity> payDetailList = (List<BillPaymentDEntity>) map.get("payDetailList");
		
		/**
		 * 将剩余的应付单号按部门分组，查询当前部门的总金额
		 */
		List<BillPaymentEntity> billEntitys = dopStatementPaymentDao.queryOrgAndAmountbyNo(addDtoList);
		//封装付款部门
		StringBuffer payOrgName = new StringBuffer();
		//付款部门的标杆编码
		StringBuffer payDeptNo = new StringBuffer();
		/**
		 * 设置返回的付款单的值
		 */
		String paymentNum = null;
		//重新设置付款单
		for(BillPaymentEntity entity:billEntitys){
			// 获取单据编号 （此处单号是手动生成付款单）
			paymentNum = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.FK1);
			String deptNo = "";
			//获取当前部门的部门标杆编码
			//付款部门编码
			if(StringUtils.isNotEmpty(entity.getPaymentOrgCode())){
				logger.info("付款部门编码："+entity.getPaymentOrgCode());
				//查询付款部门编码
				OrgAdministrativeInfoEntity entityInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getPaymentOrgCode());
				System.out.println("标杆编码"+entityInfo.getUnifiedCode());
				if(entityInfo==null || StringUtils.isEmpty(entityInfo.getUnifiedCode())){
					throw new SettlementException("付款部门不存在或找不到对应的标杆编码！");
				}
				deptNo = entityInfo.getUnifiedCode();
			}
			//付款部门
			payOrgName = payOrgName.append(entity.getPaymentOrgName()).append(":").append(entity.getAmount()).append(",");
			//付款部门标杆编码
			payDeptNo = payDeptNo.append(deptNo).append(",");
			//付款部门
			paymentEntity.setPaymentOrgCode(entity.getPaymentOrgCode());
			paymentEntity.setPaymentOrgName(entity.getPaymentOrgName());
			//付款金额
			paymentEntity.setAmount(entity.getAmount());
			//封装付款单实体
			dealPaymentEntity(paymentEntity, paymentNum, now);
		
			// 如果为电汇，生成汇款工作流,那么付款单上需要批次号的 --新增时候需要有批次号
			if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
					.equals(paymentEntity.getPaymentType())) {
				// 获取付款批次号
				String batchNo = null;
				if (null != paymentEntity.getBatchNo()&& !"".equals(paymentEntity.getBatchNo())) {
					batchNo = paymentEntity.getBatchNo();
				} else {
					batchNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.FK_BN);
				}
				// 封装批次号
				paymentEntity.setBatchNo(batchNo);
			}
			/**
			 * 重新设置付款单号
			 */
			/*if(num != 0){
				payNo = payNo + "-" +num;
				paymentEntity.setPaymentNo(payNo);
			}*/
			// 新增付款单
			addBillPayment(paymentEntity, currentInfo);
			
			/**
			 * 获取当前部门下的所有应付单号
			 */
			List<BillPaymentEntity> entitys = dopStatementPaymentDao.queryPayableByOrgCode(entity.getPaymentOrgCode(),statementBillNo);
			/**
			 * 封装应付单号
			 */
			List<String> payableNos = new ArrayList<String>();
			for(BillPaymentEntity detail : entitys){
				//应付单号的值封装到了付款单里面
				String payableNo = detail.getPaymentNo();
				payableNos.add(payableNo);
			}
			// 保存付款单明细
			dopStatementPaymentDao.addBillPaymentDetails(paymentEntity.getPaymentNo(), payableNos);
			
			// 封装应付单数据
			Map payableMap = new HashMap();
			//payableMap.put("statementBillNo", statementBillNo);
			payableMap.put("payableNos", payableNos);
			payableMap.put("paymentNum", paymentNum);
			payableMap.put("modifyUserCode", currentInfo.getEmpCode());
			payableMap.put("modifyUserName", currentInfo.getEmpName());
			payableMap.put("paymentNotes", payableList.get(0).getPaymentNotes());
			// 更新应付单
			dopStatementPaymentDao.payForBillPayable(payableMap);
		}
		/**
		 * 重新设置付款单号
		 */
		/*if(num != 0){
			//paymentEntity.setPaymentNo(payNo+"-"+num);
			//新增付款单明细
			addBillPaymentDetails(payDetailList,payNo);
		}else{
			addBillPaymentDetails(payDetailList,paymentNum);
		}*/
		/**
		 * 封装应付单号
		 */
		/*List<String> payableNos = new ArrayList<String>();
		for(BillPayableDto detail : payableList){
			String payableNo = detail.getPayableNo();
			payableNos.add(payableNo);
		}
		// 保存付款单明细
		dopStatementPaymentDao.addBillPaymentDetails(paymentNum, payableNos);*/
		/*Map payableMap = new HashMap();
		payableMap.put("statementBillNo", statementBillNo);
		payableMap.put("paymentNum", paymentNum);
		payableMap.put("modifyUserCode", currentInfo.getEmpCode());
		payableMap.put("modifyUserName", currentInfo.getEmpName());
		payableMap.put("paymentNotes", payableList.get(0).getPaymentNotes());
		// 更新应付单
		dopStatementPaymentDao.payForBillPayable(payableMap);*/
		// 设置对账单未核销金额:为0证明已经核销过了
		homeStatementEntity.setUnverifyAmount(BigDecimal.ZERO);
		
		// 调用修改对账单接口
		dopStatementService.updateStatementForWriteOff(homeStatementEntity,currentInfo);
		/**
		 * 封装付款部门,付款部门标杆
		 */
		int len = payOrgName.toString().length();
		paymentEntity.setPaymentOrgName(payOrgName.toString().substring(0,len-1));
		paymentEntity.setPaymentOrgCode(payDeptNo.toString().substring(0,payDeptNo.length()-1));
		paymentEntity.setAmount(amount);
		// 获取传递给财务自助参数
		PayToCostcontrolDto dto = buildPayToFSSCDto(paymentEntity, payableList,
				homeStatementEntity, currentInfo);
		logger.info(dto);
		try {
			// 设置事由说明
			if (StringUtils.isNotBlank(paymentEntity.getNotes())) {
				// 备注
				String notes = paymentEntity.getNotes();
				final int numberOfThreeHundred = 300;
				final int numberOfTwoNinetyNine = 299;
				if (paymentEntity.getNotes().length() > numberOfThreeHundred) {
					// 截取299个字
					notes = paymentEntity.getNotes().substring(0, numberOfTwoNinetyNine);
				}
				dto.setPayBillDiscription(notes);
			}

			// 调用接口传递数据
			payToFSSCSendService.payToCostcontrol(dto);
		} catch (Exception e) {
			logger.error("申请付款工作流失败！" + e.getMessage(), e);
			throw new SettlementException("申请付款工作流失败！" + e.getMessage(),e.getMessage());
		}
		logger.info("录入付款单end");
		return paymentNum;
	}
	
	/**
	 * 封装付款单明细
	 * @param payDetailList
	 * @param paymentNum
	 */
	/*private void addBillPaymentDetails(List<BillPaymentDEntity> payDetailList,
			String paymentNum) {
		logger.info("***************封装付款单明细*************");
		for(BillPaymentDEntity detail:payDetailList){
			//设置付款单号
			detail.setPaymentNo(paymentNum);
		}
		*//**
		 * 新增付款单明细
		 *//*
		//dopStatementPaymentDao.addBillPaymentD(payDetailList);
	}*/

	/**
	 * 新增付款单
	 * 
	 * @author zya
	 * @date 2015-12-17
	 */
	private void addBillPayment(BillPaymentEntity entity,
			CurrentInfo currentInfo) {
		// 实体不能为空或id不能为空，付款单号不能为空，业务日期和记账日期都不能为空
		if (entity == null || StringUtils.isEmpty(entity.getId())
				|| StringUtils.isEmpty(entity.getPaymentNo())
				|| entity.getBusinessDate() == null
				|| entity.getAccountDate() == null) {
			throw new SettlementException("新增付款单传入的参数不能为空！");
		}
		logger.info("新增付款单Start, 付款单号：" + entity.getPaymentNo());
		// 设置操作者信息
		Date date = new Date();
		// 设置创建时间
		entity.setCreateTime(date);
		// 设置创建人编码
		entity.setCreateUserCode(currentInfo.getEmpCode());
		// 创建人名称
		entity.setCreateUserName(currentInfo.getEmpName());
		// 设置修改时间
		entity.setModifyTime(date);
		// 设置修改人编码
		entity.setModifyUserCode(currentInfo.getEmpCode());
		// 设置修改人名称
		entity.setModifyUserName(currentInfo.getEmpName());
		// 业务日期在记账日期之后 ，提示不能保存
		Date bussinessDate = entity.getBusinessDate();
		// 记账日期格式化到秒，之后进行比较
		Date accountDate = entity.getAccountDate();
		// 业务日期如果在记账日期之后，提示异常信息
		if (bussinessDate.after(accountDate)) {
			throw new SettlementException("记账日期小于业务日期，不能进行新增付款单操作！");
		}
		// FOSS生成的所有单据的初始化状态都为"N"
		entity.setIsInit(FossConstants.NO);
		// FOSS生成的所有单据的初始化状态都为"N"
		entity.setIsInit(FossConstants.NO);
		// 保存数据后返回值
		logger.info("保存之前"+entity);
		billPaymentService.addBillDopPayment(entity);
		logger.info("新增付款单End, 付款单号：" + entity.getPaymentNo());
	}

	/**
	 * 新增校验,并将更新应付单需要参数进行封装到dto中
	 * 
	 * @author zya
	 * @date 2015-12-17
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map validateAddPaymentForFSSC(BillPaymentEntity paymentEntity,
			List<BillPaymentAddDto> addDtoList, String paymentNum,CurrentInfo currentInfo) {
		logger.info("付款单新增时校验开始");
		// 声明工作流类型 --每次付款只能付同一种工作流类型的明细数据
		String workFlowType = null;
		// 声明付款类型 报销还是付款
		String payType = null;
		// 如果付款方式为电汇，表示为付款，反之为报销
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER.equals(paymentEntity.getPaymentType())) {
			payType = SettlementESBDictionaryConstants.COST_CONTROL_PAYMENT_PAY;// 付款
		} else {
			payType = SettlementESBDictionaryConstants.COST_CONTROL_PAYMENT_APPLY;// 报销
		}

		// 校验界面传入参数
		validateParams(paymentEntity, addDtoList);
		
		// 声明本次付款金额总和
		BigDecimal payableUnVerifyAmount = BigDecimal.ZERO;
		
		// 声明要更新的应付单集合	
		List<BillPayableDto> billPayables = new ArrayList<BillPayableDto>();
		// 声明付款单明细
		List<BillPaymentDEntity> payDetailList = new ArrayList<BillPaymentDEntity>();
		// 声明应付单单号列表
		List<String> payableNos = new ArrayList<String>();
		// 声明付款单明细单号对应的支付金额map
		Map<String, BigDecimal> amountMapByNo = new HashMap<String, BigDecimal>();
		
		// 循环获取dto中本次付款金额
		for (BillPaymentAddDto dto : addDtoList) {
			// 校验界面传入id
			if (StringUtils.isBlank(dto.getId())) {
				throw new SettlementException("付款单明细中应付单号："
						+ dto.getPayableNo() + "的明细id为空！");
			}

			// 如果为应付单
			if (SettlementUtil.isPayable(dto.getPayableNo())) {
				payableNos.add(dto.getPayableNo());
				amountMapByNo.put(dto.getPayableNo(),dto.getCurrentPaymentAmount());
				// 封装应付单要用的dtolist
				BillPayableDto entity = new BillPayableDto();
				entity.setId(dto.getId());// 封装应付单id
				entity.setBillType(dto.getBillType());// 获取单据子类型
				entity.setPayableNo(dto.getPayableNo());// 应付单号
				entity.setAccountDate(dto.getAccountDate());// 封装记账日期
				entity.setBusinessDate(dto.getBusinessDate());// 封装业务日期
				entity.setVersionNo(dto.getVersionNo());// 封装版本号
				entity.setPaymentAmount(dto.getCurrentPaymentAmount());// 封装本次付款金额
				entity.setPaymentNo(paymentNum);// 封装付款单号
				entity.setPaymentNotes(dto.getNotes());// 封装本次付款明细备注
				// 将数据封装到应付单dto的集合中
				billPayables.add(entity);
				// 累加应付单金额
				payableUnVerifyAmount = payableUnVerifyAmount.add(dto
						.getCurrentPaymentAmount());
			} else {
				throw new SettlementException("付款单明细中应付单号："
						+ dto.getPayableNo() + "的单据类型不是应付单！");
			}
		}
		
		// 查询应付单
		if (payableNos.size() > 0) {
			// 调用应付单接口查询应付单信息
			List<BillPayableEntity> payableList = billPayableService
					.queryByPayableNOs(payableNos, FossConstants.ACTIVE);
			// 如果查询应付单条数与传入条数不等，则弹出提示
			if (payableList.size() != payableNos.size()) {
				throw new SettlementException("查询付款单明细与传入明细不等，请关闭界面，重新制作！");
			}
			// 校验新查询数据与界面传入的dto是否一致
			for (int i = 0; i < payableList.size(); i++) {
				// 获取应付单entity
				BillPayableEntity entity = payableList.get(i);

				// 如果是第一次循环，则要获取
				if (i == 0 && StringUtils.isBlank(workFlowType)) {
					workFlowType = getWorkFlowTypeByBillType(
							entity.getPayableNo(), entity.getBillType(),
							payType);
				} else {
					// 获取下一次的类型与第一次工作流类型进行比较
					String workFlowTypeNew = getWorkFlowTypeByBillType(
							entity.getPayableNo(), entity.getBillType(),
							payType);
					// 如果两次工作流类型不等，则抛出异常
					if (!StringUtils.equals(workFlowType, workFlowTypeNew)) {
						throw new SettlementException(
								"该付款单中存在不同付款工作流类型的单据，不能进行付款！");
					}
				}

				// 循环界面列表集合，与查询出来数据进行比较
				for (int j = 0; j < billPayables.size(); j++) {
					// 获取dto
					BillPayableDto dto = billPayables.get(j);
					// 比较新查询出数据与界面数据是否一致，根据同一单号
					if (entity.getPayableNo().equals(dto.getPayableNo())) {
						// 判断版本号是否一致
						if (!entity.getVersionNo().equals(dto.getVersionNo())) {
							throw new SettlementException("付款单明细中应付单号："
									+ dto.getPayableNo()
									+ "的数据已经发生变化，请刷新应付单或对账单页面，重新进行付款");
						}
						dto.setBusinessDate(entity.getBusinessDate());
						dto.setAccountDate(entity.getAccountDate());
						dto.setBillType(entity.getBillType());
						dto.setSourceBillNo(entity.getSourceBillNo());// 封装来源单据编号
																		// --给外请车付款用来查询配载单数据
						dto.setWaybillNo(entity.getWaybillNo());// 封装运单号
						dto.setProductCode(entity.getProductCode());// 获取产品类型
						// 跳出循环
						break;
					}
				}
				// 声明付款单明细
				BillPaymentDEntity dEntity = new BillPaymentDEntity();
				// 设置值
				dEntity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYMENT_D_SOURCE_BILL_TYPE_PAYABLE);
				// 设置值
				dEntity.setSourceBillNo(entity.getPayableNo());
				// 设置值
				dEntity.setWaybillNo(entity.getWaybillNo());
				// 设置值
				dEntity.setSrcSourceBillNo(entity.getSourceBillNo());
				// 设置值
				dEntity.setSourceAccountDate(entity.getAccountDate());
				// 设置支付金额
				dEntity.setPayAmount(amountMapByNo.get(entity.getPayableNo()));
				// 添加到明细列表中
				payDetailList.add(dEntity);

			}
			// 校验应付单数据是否可以支付
			validateCanPay(payableList, paymentEntity.getPaymentType());
		}

		// 校验明细本次付款金额总和和付款单头的金额是否相等
		if (paymentEntity.getAmount() != null
				&& paymentEntity.getAmount().compareTo(payableUnVerifyAmount) != 0) {
			throw new SettlementException("本次付款金额明细之和与付款单金额不等！");
		}

		// 在此处来确认付款单的工作流类型
		if (StringUtils.isNotBlank(workFlowType)) {
			paymentEntity.setWorkFlowType(workFlowType);
		} else {
			throw new SettlementException("付款工作流类型为不能为空！");
		}
		// 校验付款单明细
		if (CollectionUtils.isEmpty(payDetailList)) {
			throw new SettlementException("付款单明细不能为空！");
		}
		// 声明传出map
		Map map = new HashMap();
		// 封装付款单列表
		map.put("payableList", billPayables);
		// 封装付款单明细列表
		map.put("payDetailList", payDetailList);
		logger.info("付款单新增时校验结束");
		return map;
	}

	/**
	 * 新增时，处理付款单实体，保存
	 * 
	 * @author zya
	 * @date 2015-12-17
	 */
	private BillPaymentEntity dealPaymentEntity(
			BillPaymentEntity paymentEntity, String paymentNum, Date nowDate) {
		paymentEntity.setId(UUIDUtils.getUUID());// 获取id
		paymentEntity.setPaymentNo(paymentNum);// 设置单号
		paymentEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);// 设置币种
		paymentEntity.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRING);// 设置汇款状态为”汇款中“
		paymentEntity.setAuditStatus(SettlementDictionaryConstants.BILL_PAYMENT__AUDIT_STATUS__NOT_AUDIT);// 设置审核状态为”未审核“
		paymentEntity.setSourceBillNo(SettlementConstants.DEFAULT_BILL_NO);// 来源单据编号
																			// 'N/A'
		paymentEntity.setActive(FossConstants.ACTIVE);// 是否有效
		paymentEntity.setIsRedBack(FossConstants.NO);// 是否红单
		paymentEntity.setBusinessDate(nowDate);// 设置业务日期
		paymentEntity.setAccountDate(nowDate);// 设置记账日期
		paymentEntity.setIsInit(FossConstants.NO);// 是否初始化
		paymentEntity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);// 手动录入
		paymentEntity.setVersionNo(FossConstants.INIT_VERSION_NO);// 版本号
		return paymentEntity;
	}

	/**
	 * 封装给财务共享的付款接口参数
	 * 
	 * @author zya
	 * @date 2015-12-17
	 */
	private PayToCostcontrolDto buildPayToFSSCDto(BillPaymentEntity bill,
			List<BillPayableDto> payableList,
			HomeStatementEntity homeStatementEntity, CurrentInfo currentInfo) {
		// 声明传递给共享的dto
		PayToCostcontrolDto dto = new PayToCostcontrolDto();
		BillPayableDto payable = payableList.get(0);
		//解析拼接的数据
		String codeStr = bill.getPaymentOrgCode();
		String[] orgCode = codeStr.split(",");
		String nameStr = bill.getPaymentOrgName();
		String[] orgName = nameStr.split(",");
		
		// zya
		// 声明付款明细
		List<com.deppon.fssc.inteface.domain.payment.ExpenseDetail> detail = new ArrayList<com.deppon.fssc.inteface.domain.payment.ExpenseDetail>();
		StringBuffer sb = new StringBuffer();
		// 初始化一个bigdecml类型叠加每个部门税额
		BigDecimal dec = new BigDecimal("0");
		for (int i = 0; i < orgCode.length; i++) {
			com.deppon.fssc.inteface.domain.payment.ExpenseDetail d = new com.deppon.fssc.inteface.domain.payment.ExpenseDetail();
			d.setBillNumber(homeStatementEntity.getStatementBillNo());// 明细单号
			d.setExpenseExplain(PAYDESC);// 费用说明
			String s = orgName[i].split(":")[1].toString();
			sb.append(orgName[i].split(":")[0]).append(",");
			d.setExpensesMoney(BigDecimal.valueOf(Double.parseDouble(s)));// 明细金额
			// 转化给对应共享那边费用明细
			String payType = convertBillType(payable.getPayableNo(),payable.getBillType());
			d.setExpensesType(payType);
			d.setPayBillExpDate(homeStatementEntity.getCreateTime());// 业务发生日期
			d.setExpenseCostCenter(orgCode[i]);// 部门标杆编码
			// 判断金额是否为空，即是否增值税专用发票
			if (bill.getTaxPrice() != null
					&& bill.getTaxPrice().compareTo(new BigDecimal(0)) != 0) {
				//设置增值税专用发票标识
				d.setIsvat("Y");
				//设置纳税人识别号
				d.setTaxpayerId(bill.getTaxpayerNumber());
				//设置增值税专用发票号码
				d.setInvatNum(bill.getVatInvoice());
				
				// 计算营业部对应的税额
				if (i < orgCode.length - 1) {
					BigDecimal de = bill.getTaxPrice().divide(bill.getAmount(),
							2, RoundingMode.HALF_UP);
					d.setTaxPrice(bill
							.getTaxPrice()
							.divide(bill.getAmount(), 2, RoundingMode.HALF_UP)
							.multiply(BigDecimal.valueOf(Double.parseDouble(s))));
					dec = dec.add(d.getTaxPrice());
				} else {
					d.setTaxPrice(bill.getTaxPrice().subtract(dec));
				}
				//设置不含税金额
				d.setTaxfreePrice(d.getExpensesMoney()
						.subtract(d.getTaxPrice()));
			}else{
				//设置增值税专用发票标识
				d.setIsvat("N");
			}
			detail.add(d);
		}
		
		// 声明付款集合
		dto.setPaymentBillNo(bill.getBatchNo());// 获取付款编号
		//dto.setPayBillDeptName(bill.getPaymentOrgName());// 获取部门名称
		dto.setPayBillDeptName(currentInfo.getCurrentDeptName());// 获取部门名称
		dto.setPayBillDeptNo(currentInfo.getDept().getUnifiedCode());// 获取部门标杆编码
		
		dto.setPayBillAmount(bill.getAmount());// 获取总金额
		dto.setPayBillComNo(bill.getInvoiceHeadCode());// 获取公司编码
		dto.setPayBillBankNo(bill.getAccountNo());// 获取银行账号
		dto.setAccountType(bill.getAccountType());// 账户类型
		dto.setBankHqCode(bill.getBankHqCode());// 开户行编码
		dto.setBankBranchCode(bill.getBankBranchCode());// 开户行支行编码
		dto.setProvinceCode(bill.getProvinceCode());// 省份编码
		dto.setCityCode(bill.getCityCode());// 城市编码
		dto.setPayBillPayeeName(bill.getPayeeName());// 获取收款人名称
		dto.setPayBillPayeeCode(bill.getPayeeCode());// 收款人编码
		dto.setPayBillCelephone(bill.getMobilePhone());// 获取联系方式
		dto.setPayBillAppNo(currentInfo.getEmpCode());// 获取员工编号
		dto.setPayBillLastTime(new Date());// 获取最迟汇款日期 --now time
		dto.setPayBillAppType(bill.getWorkFlowType());// 工作流类型
		dto.setPayType(SettlementESBDictionaryConstants.COST_CONTROL_PAYMENT_PAY);// 支付类型
																					// --付款
		dto.setCurrency(SettlementESBDictionaryConstants.CURRENCY_CODE_RMB);// 币种--人民币
		dto.setIsAutoAbatementLoan(SettlementESBDictionaryConstants.AUTOABATEMENTLOAN_N);// 电汇默认为N,只有申请备用金才可能自动冲借支
		dto.setExchangeRate(null);// 汇率默认为空， foss暂时没有汇率
		dto.setExpenseDetailToFSSC(detail);
		dto.setBatchNo(bill.getBatchNo());// 工作流号
		return dto;
	}

	/**
	 * 根据单据子类型转化为对应报账明细类型 --仅限于应付单子类型
	 * 
	 * @author zya
	 * @date 2015-12-17
	 */
	public static String convertBillType(String number, String billType) {
		// 生命费用明细类型
		String payType = SettlementESBDictionaryConstants.COST_CONTROL_PAY_DETAIL_HOME;
		return payType;
	}

	/**
	 * 根据传入单据类型确认是那种工作流类型
	 * 
	 * @author zya
	 * 2015-12-17
	 */
	public static String getWorkFlowTypeByBillType(String number,
			String billType, String payType) {
		// 声明工作流类型
		String workFlowType = SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PACK_HOME_PAY;// 家装--付款
		return workFlowType;
	}

	/**
	 * 校验界面传入参数
	 * 
	 * @author zya
	 * @date 2014-06-17
	 */
	private void validateParams(BillPaymentEntity paymentEntity,List<BillPaymentAddDto> addDtoList) {
		// 如果明细size小于0，则弹出提示
		if (CollectionUtils.isEmpty(addDtoList)) {
			throw new SettlementException("录入付款单，至少有一条明细数据！");
		}
		// 校验前台必填项
		if (paymentEntity != null) {
			// 判断部门名称
			if (StringUtils.isBlank(paymentEntity.getPaymentOrgName())) {
				throw new SettlementException("部门名称不能为空！");
			}
			// 如果子公司编码为空，则抛出异常
			if (StringUtils.isBlank(paymentEntity.getPaymentCompanyCode())) {
				throw new SettlementException("子公司编码不能为空！");
			}
			// 付款方式校验
			if (StringUtils.isBlank(paymentEntity.getPaymentType())) {
				throw new SettlementException("付款方式不能为空！");
			}

			// 付款金额校验
			if (paymentEntity.getAmount() == null
					|| paymentEntity.getAmount().compareTo(BigDecimal.ZERO) < 1) {
				throw new SettlementException("金额不能为空且必须大于0！");
			}
			// 来源单据类型校验 （作废要用到该字段）
			if (StringUtils.isBlank(paymentEntity.getSourceBillType())) {
				throw new SettlementException("来源单据类型不能为空！");
			}

			// 如果付款方式不能为空，且为电汇，则需要判断银行等信息
			if (StringUtils.isNotBlank(paymentEntity.getPaymentType())
					&& SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
							.equals(paymentEntity.getPaymentType())) {
				// 如果银行账号为空，则抛出异常
				if (StringUtils.isBlank(paymentEntity.getAccountNo())) {
					throw new SettlementException("付款方式为电汇时银行账号不能为空！");
				}
				// 省份编码不能为空
				if (StringUtils.isBlank(paymentEntity.getProvinceCode())) {
					throw new SettlementException("省份编码不能为空！");
				}
				// 城市编码不能为空
				if (StringUtils.isBlank(paymentEntity.getCityCode())) {
					throw new SettlementException("城市编码不能为空！");
				}
				// 开户银行不能为空
				if (StringUtils.isBlank(paymentEntity.getBankHqCode())) {
					throw new SettlementException("开户银行编码不能为空！");
				}
				// 行号不能为空
				if (StringUtils.isBlank(paymentEntity.getBankBranchCode())) {
					throw new SettlementException("行号不能为空！");
				}
				// 账户类型
				if (StringUtils.isBlank(paymentEntity.getAccountType())) {
					throw new SettlementException("账户类型不能为空！");
				}
				// 收款人不能为空
				if (StringUtils.isBlank(paymentEntity.getPayeeName())) {
					throw new SettlementException("收款人不能为空！");
				}
				// 收款人手机号不能为空
				if (StringUtils.isBlank(paymentEntity.getMobilePhone())) {
					throw new SettlementException("收款人手机号不能为空！");
				}
			}
		}
	}

	/**
	 * 对账单核销
	 * 
	 * @Title: writeoffStatement
	 * @author： 269052 |zhouyuan008@deppon.com
	 */

	private List<BillWriteoffOperationDto> writeoffStatement(
			HomeStatementEntity homeStatementEntity, CurrentInfo currentInfo) {
		// 获取核销批次号
		String writeoffBatchNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HX_BN);
		/*
		 * 3 获取应收单、应付单列表记录
		 */
		List<BillWriteoffOperationDto> writeoffResultDto = writeoffStatementDetail(
				homeStatementEntity, writeoffBatchNo, currentInfo);
		// 返回核销结果dto
		return writeoffResultDto;
	}

	/**
	 * 核销对账单明细
	 * @author zya
	 */
	private List<BillWriteoffOperationDto> writeoffStatementDetail(
			HomeStatementEntity sWriteoffEntity, String writeoffBatchNo,
			CurrentInfo currentInfo) {
		/**
		 * 根据对账单号去查询不同部门
		 */
		List<HomeStatementDEntity> orgEntitys = dopStatementService.getOrgNameByStatementBillNo(sWriteoffEntity.getStatementBillNo());
		
		//返回结果
		List<BillWriteoffOperationDto> billWriteoffResult = new ArrayList<BillWriteoffOperationDto>();
		
		for(HomeStatementDEntity orgEntity:orgEntitys){
			// 根据对账单号和部门查询对账单明细信息
			List<HomeStatementDEntity> statementDetailEntitys = dopStatementService.queryByStatementBillNo(sWriteoffEntity.getStatementBillNo(),orgEntity.getOrgCode());
				
			// 如果对账单明细列表为空
			if (CollectionUtils.isEmpty(statementDetailEntitys)) {
				// 提示 对账单明细记录为空，无法核销
				throw new SettlementException("对账单明细记录为空，无法核销");
			}
			/**
			 * 
			 */
			// 核销操作Dto
			BillWriteoffOperationDto writeoffDto = new BillWriteoffOperationDto();

			// 从对账单明细中获取应收单号、应付单号
			List<String> recNos = new ArrayList<String>();// 应收单号
			List<String> payNos = new ArrayList<String>();// 应付单号
			// 循环处理对账单明细
			for (HomeStatementDEntity statmentDetailEntity : statementDetailEntitys) {
				// 应收单号
				/*
				 * if (SettlementDictionaryConstants.
				 * STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE
				 * .equals(statmentDetailEntity.getBillParentType())) { //加入到应收单号列表
				 * recNos.add(statmentDetailEntity.getPayableNo()); } // 应付单号 else
				 * if (SettlementDictionaryConstants.
				 * STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE
				 * .equals(statmentDetailEntity.getBillParentType())) { //加入到应付单号列表
				 * payNos.add(statmentDetailEntity.getPayableNo()); } // 其他 else {
				 * //提示对账单明细单据大类型异常，无法核销 throw new
				 * SettlementException("对账单明细单据大类型异常，无法核销"); }
				 */
				String str = statmentDetailEntity.getPayableNo().substring(0, 2);
				if ("YF".equals(str)) {
					// 加入到应付单号列表
					payNos.add(statmentDetailEntity.getPayableNo());
				} else if ("YS".equals(str)) {
					// 加入到应收单号列表
					recNos.add(statmentDetailEntity.getPayableNo());
				}// 其他
				else {
					// 提示对账单明细单据大类型异常，无法核销
					throw new SettlementException("对账单明细单据大类型异常，无法核销");
				}
			}

			// 根据来源单号查询应收单
			// 如果应收单号不为空
			if (CollectionUtils.isNotEmpty(recNos)) {
				// 查询应收单
				List<BillReceivableEntity> recs = billReceivableService.queryByReceivableNOs(recNos, FossConstants.ACTIVE);
				// 校应收单原始单据数量和对账单明细数量是否匹配
				if ((CollectionUtils.isNotEmpty(recs) && recs.size() != recNos.size()) || CollectionUtils.isEmpty(recs)) {
					// 提示对账单明细记录中的应收单和原始记录信息不一致，无法核销，请联系管理员
					throw new SettlementException("对账单明细记录中的应收单和原始记录信息不一致，无法核销，请联系管理员");
				}
				// 设置核销参数中的核销方列表：应收单列表
				writeoffDto.setBillReceivableEntitys(recs);
			}
			// 根据来源单号查询应付单
			// 如果应付单号不为空
			if (CollectionUtils.isNotEmpty(payNos)) {
				// 查询应付单
				List<BillPayableEntity> pays = billPayableService.queryByPayableNOs(payNos, FossConstants.ACTIVE);
				// 校应付单原始单据数量和对账单明细数量是否匹配
				if ((CollectionUtils.isNotEmpty(pays) && pays.size() != payNos.size()) || CollectionUtils.isEmpty(pays)) {
					// 提示对账单明细记录中的应付单和原始记录信息不一致，无法核销，请联系管理员
					throw new SettlementException("对账单明细记录中的应付单和原始记录信息不一致，无法核销，请联系管理员");
				}
				// 设置核销参数中的核销方列表：应付单列表
				writeoffDto.setBillPayableEntitys(pays);
			}

			// 设置核销批次号
			writeoffDto.setWriteoffBatchNo(writeoffBatchNo);

			// 核销类型为手工核销
			writeoffDto.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);

			// 核销对账单编号取对账单上的单据编号
			writeoffDto.setStatementBillNo(sWriteoffEntity.getStatementBillNo());

			// 核销对账单明细
			BillWriteoffOperationDto writeoffResultDtoDetail = writeoffStatementDetailByBillList(writeoffDto, currentInfo);
			
			//设置应收单应付单
			billWriteoffResult.add(writeoffResultDtoDetail);
		}
		
		// 返回核销结果dto
		return billWriteoffResult;
	}

	/**
	 * 对账单明细核销，包括：应收冲应付
	 * @author zya
	 */
	private BillWriteoffOperationDto writeoffStatementDetailByBillList(
			BillWriteoffOperationDto writeoffDto, CurrentInfo currentInfo) {
		// 初始化返回结果
		BillWriteoffOperationDto writeoffResultDto = new BillWriteoffOperationDto();
		
		// 设置应付单
		writeoffResultDto.setBillPayableEntitys(writeoffDto.getBillPayableEntitys());

		// 应收冲应付
		// 如果应收单和应付单不为空
		if (CollectionUtils.isNotEmpty(writeoffDto.getBillReceivableEntitys())
				&& CollectionUtils.isNotEmpty(writeoffDto
						.getBillPayableEntitys())) {
			// 调用统一核销方法
			writeoffResultDto = billWriteoffService.writeoffReceibableAndPayable(writeoffDto, currentInfo);
			// 设置应收单核销结果
			writeoffDto.setBillReceivableEntitys(writeoffResultDto.getBillReceivableEntitys());
			// 设置应付单核销结果
			writeoffDto.setBillPayableEntitys(writeoffResultDto.getBillPayableEntitys());
		}

		// 返回核销结果
		return writeoffResultDto;
	}

	/**
	 * 校验是否可以支付
	 * @param payableList --应付单列表 ，payType--付款单的付款类型（此参数是在录入付款单调用该方法时传入）
	 * @author zya
	 * @date 2014-06-17
	 */
	private void validateCanPay(List<BillPayableEntity> payableList,
			String payType) {
		// 声明应付单银行账号
		String accountNo = null;
		// 声明循环次数
		int cycleTimes = 0;
		// 理赔单的应付单号
		String calimNo = null;

		// 循环校验
		for (BillPayableEntity bill : payableList) {
			// 循环次数
			cycleTimes = cycleTimes + 1;

			// 判断是否有效
			if (!FossConstants.ACTIVE.equals(bill.getActive())) {
				throw new SettlementException("应付单号：" + bill.getPayableNo()
						+ "为无效应付单，不能进行付款操作！");
			}
			// 审核状态必须是已审核
			if (bill.getApproveStatus() == null
					|| !SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE
							.equals(bill.getApproveStatus())) {
				throw new SettlementException("应付单号：" + bill.getPayableNo()
						+ "还未审核，不能进行付款！");
			}
			// 完全核销的应付单不能付款
			if (bill.getUnverifyAmount() == null
					|| BigDecimal.ZERO.compareTo(bill.getUnverifyAmount()) == 0) {
				throw new SettlementException("应付单号：" + bill.getPayableNo()
						+ "已经核销完毕，不能付款！");
			}
			// 如果没有生效，则不能付款
			if (SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__NO
					.equals(bill.getEffectiveStatus())) {
				throw new SettlementException("应付单号：" + bill.getPayableNo()
						+ "还未生效，不能进行付款操作！");
			}
			// 如果存在付款单号，则表示已经付过款，不能再次付款
			if (!SettlementConstants.DEFAULT_BILL_NO
					.equals(bill.getPaymentNo())
					&& StringUtils.isNotBlank(bill.getPaymentNo())) {
				throw new SettlementException("应付单号：" + bill.getPayableNo()
						+ "已经付过一次款，一个付款单只能付款一次！");
			}
			// 初始化银行账号字段
			if (cycleTimes == 1) {
				// 初始化应付单号
				calimNo = bill.getPayableNo();
				accountNo = bill.getAccountNo();
				// 如果银行账号不相等，则抛出提示
			} else if (!StringUtils.equals(accountNo, bill.getAccountNo())) {
				throw new SettlementException("应付单号：" + bill.getPayableNo()
						+ "的银行账号与" + calimNo + "的银行账号不相同，不能一起支付");
			}
		}
	}

	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	public void setBillPaymentService(IBillPaymentService billPaymentService) {
		this.billPaymentService = billPaymentService;
	}

	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	public void setPayToFSSCSendService(
			IPayToFSSCSendService payToFSSCSendService) {
		this.payToFSSCSendService = payToFSSCSendService;
	}

	public void setBillWriteoffService(IBillWriteoffService billWriteoffService) {
		this.billWriteoffService = billWriteoffService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
}
