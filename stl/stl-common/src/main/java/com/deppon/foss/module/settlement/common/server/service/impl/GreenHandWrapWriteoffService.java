package com.deppon.foss.module.settlement.common.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.dao.IGreenHandWrapWriteoffDao;
import com.deppon.foss.module.settlement.common.api.server.service.IBillRepaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.IGreenHandWrapWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.GreenHandWrapWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.RequestGreenHandWrapEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillRepaymentConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountUpdateDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class GreenHandWrapWriteoffService implements IGreenHandWrapWriteoffService  {
	/**
	 * 根据传入的dop实体进行查询
	 * @author 310970
	 * @date 2016-2-16
	 */
	
	/**
	 * 注入
	 * @param billReceivableEntityDao
	 */
	private IGreenHandWrapWriteoffDao greenHandWrapWriteoffDao;
	
	/**
	 * 业务互斥锁服务
	 */
	private IBusinessLockService businessLockService;
	
	/**
	 * 注入生成序列Service
	 */
	private ISettlementCommonService settlementCommonService;
	
	/**
	 * 注入子公司SERVICE
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 注入还款单IBillRepaymentService
	 */
	private IBillRepaymentService billRepaymentService;
	
	/**
	 * 注入核销单IBillWriteoffService
	 */
	private IBillWriteoffService billWriteoffService;
	
	/**
	 * 注入对账单IStatementOfAccountService
	 */
	private IStatementOfAccountService statementOfAccountService;
	
	/**
	 * 注入裹裹网上支付对接FINS客户端接口IGreenHandWrapToFinanceClient
	 */
	//private IGreenHandWrapToFinanceClient greenHandWrapToFinanceClient;

	private static final Logger logger = LogManager.getLogger(GreenHandWrapWriteoffService.class);
	
	@Override
	public List<BillReceivableEntity> queryReceivableBill(
			RequestGreenHandWrapEntity request) {
		logger.info("查询始发应收单并且未核销金额大于0的开始...");	
		//网上支付应收单查询dto
		//获取dop传入的运单号
		if(request != null){
			if("Y".equals(request.getIsDr())){
				//查询到付的单子
				//查询无效应收单
				String waybillNo= request.getWaybillNo();
				logger.info("运单号为：" + waybillNo);
				List<BillReceivableEntity> writeoffInformationDtoList= greenHandWrapWriteoffDao.queryDrReceiveByWaybillNos(waybillNo);
				return writeoffInformationDtoList;
			}else if("Y".equals(request.getIsException())){
				//查询无效应收单
				String waybillNo= request.getWaybillNo();
				logger.info("运单号为：" + waybillNo);
				List<BillReceivableEntity> writeoffInformationDtoList= greenHandWrapWriteoffDao.queryReceiveByWaybillNos(waybillNo);
				return writeoffInformationDtoList;
			}else{
				String waybillNo= request.getWaybillNo();
				logger.info("运单号为：" + waybillNo);
				List<BillReceivableEntity> writeoffInformationDtoList= greenHandWrapWriteoffDao.queryByWaybillNOs(waybillNo);
				return writeoffInformationDtoList;
			}
		}else{
			logger.info("查询始发应收单并且未核销金额大于0的实体为空");
			throw new SettlementException("查询始发应收单并且未核销金额大于0的实体为空");
		}
	}

	/**
     * @author 231434-bieyexiong
     * @date 2016-06-25 8:22
     * 根据单号查询未核销暂存表的信息
     */
	@Override
	public List<GreenHandWrapWriteoffEntity> queryGreenHandWrapByWaybillNo(String waybillNo) {
		
		return greenHandWrapWriteoffDao.queryGreenHandWrapByWaybillNo(waybillNo);
	}
	
	/**
	 * @author 218392 张永雪
	 * @date 2016-02-19 09:44:50
	 * 裹裹项目核销始发应收单
	 */
	@Transactional
	@Override
	public void writeoffByDoprequest(BillReceivableEntity entity,RequestGreenHandWrapEntity request) {
		//记录日志
		logger.info("裹裹客户付款应收单号：" + entity.getReceivableNo());
		//判空
		if (request.getDopAmount().compareTo(BigDecimal.ZERO) <= 0) {
			//记录日志
			logger.error("裹裹客户还款金额小于等于0");
			throw new SettlementException("还款金额小于等于0！", "");
		}
		logger.error("裹裹客户部分还款,还款金额为"+request.getDopAmount()+";未还款金额为"+entity.getUnverifyAmount());
		
		// 判断在线支付编号是否已经使用
		BillRepaymentConditionDto dto = new BillRepaymentConditionDto();
		//设置属性值
		dto.setOnlinePaymentNo("GG"+request.getWaybillNo());
		//调用接口进行查询
		List<BillRepaymentEntity> list = billRepaymentService.queryBillRepaymentByCondition(dto);
		//判断列表集合
		if (list.size() > 0) {
			//记录日志
			logger.error("在线支付编号已被使用过，请确认是否重复付款");
			throw new SettlementException("在线支付编号已被使用过，请确认是否重复付款！", "");
		}
		
		// 如果应收单存在运单号，生成互斥对象
		MutexElement mutex = null;
		//判空
		if (StringUtils.isNotEmpty(entity.getWaybillNo())&&!SettlementConstants.DEFAULT_BILL_NO.equals(entity.getWaybillNo())) {
			//互斥锁
			mutex = new MutexElement(entity.getWaybillNo(),"网上支付按应收单还款", MutexElementType.WAYBILL_NO);
		}
		// 如果互斥对象不为空,锁定该应收单对应的运单
		if (mutex != null) {
			// 锁定运单
			businessLockService.lock(mutex,SettlementConstants.BUSINESS_LOCK_BATCH);
		}
		
		// 生成还款单信息
		BillRepaymentEntity repaymentEntity = new BillRepaymentEntity();
		//设置 裹裹网上支付编码：GG+运单号 ，和FINS那边保持一致
		repaymentEntity.setOnlinePaymentNo(dto.getOnlinePaymentNo());
		//设置ID
		repaymentEntity.setId(UUIDUtils.getUUID());
		//还款单号
		repaymentEntity.setRepaymentNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HK1));
		//设置客户编码
		repaymentEntity.setCustomerCode(entity.getCustomerCode());
		//设置客户名称
		repaymentEntity.setCustomerName(entity.getCustomerName());
		//设置运单号
		repaymentEntity.setWaybillNo(entity.getWaybillNo());

		if(request.getDopAmount().compareTo(entity.getUnverifyAmount()) < 0){
			// 还款金额
			repaymentEntity.setAmount(request.getDopAmount());
			//设置实际还款金额
			repaymentEntity.setTrueAmount(request.getDopAmount());
			//fossVerifyAmount = request.getDopAmount();
		}else if(request.getDopAmount().compareTo(entity.getUnverifyAmount()) >= 0){
			// 还款金额
			repaymentEntity.setAmount(entity.getUnverifyAmount());
			//设置实际还款金额
			repaymentEntity.setTrueAmount(entity.getUnverifyAmount());
			//fossVerifyAmount = entity.getUnverifyAmount();
		}
		
		//设置反核销金额
		repaymentEntity.setBverifyAmount(BigDecimal.ZERO);
		// 审核状态、生成方式
		repaymentEntity.setAuditStatus(SettlementDictionaryConstants.BILL_REPAYMENT__AUDIT_STATUS__NOT_AUDIT);
		//设置属性值
		repaymentEntity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
		// 收款部门编码、名称
		repaymentEntity.setCollectionOrgCode(entity.getReceivableOrgCode());
		repaymentEntity.setCollectionOrgName(entity.getReceivableOrgName());
		// 根据部门编码获取子公司信息
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getReceivableOrgCode());
		//判空
		if (orgEntity != null) {
			// 收款部门公司编码、名称
			repaymentEntity.setCollectionCompanyCode(orgEntity.getSubsidiaryCode());
			//设置属性值
			repaymentEntity.setCollectionCompanyName(orgEntity.getSubsidiaryName());
		}
		// 单据类型、是否有效、是否红单、支付方式
		repaymentEntity.setBillType(SettlementDictionaryConstants.BILL_REPAYMENT__BILL_TYPE__REPAYMENT);
		//设置属性值
		repaymentEntity.setActive(FossConstants.ACTIVE);
		//设置属性值
		repaymentEntity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		//设置属性值
		repaymentEntity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE);
		// 业务日期、记账日期、是否初始化、版本号
		repaymentEntity.setBusinessDate(new Date());
		//设置属性值
		repaymentEntity.setAccountDate(new Date());
		//设置属性值
		repaymentEntity.setStatus(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__STATUS__SUBMIT);
		//设置属性值
		repaymentEntity.setIsInit(FossConstants.NO);
		//设置属性值
		repaymentEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
		// 创建当前登录信息
		EmployeeEntity employee = new EmployeeEntity();
		//设置属性值  裹裹 GG
		employee.setEmpCode(SettlementConstants.WRAP_CODE);
		//设置属性值 裹裹
		employee.setEmpName(SettlementConstants.WRAP_NAME);
		//实例化
		UserEntity user = new UserEntity();
		//设置属性值
		user.setEmployee(employee);
		//设置属性值
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		//设置属性值
		dept.setCode(entity.getReceivableOrgCode());
		//设置属性值
		dept.setName(entity.getReceivableOrgName());
		//获取当前登录人
		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		// 新增还款单信息
		repaymentEntity.setCreateOrgCode(currentInfo.getCurrentDeptCode());
		repaymentEntity.setCreateOrgName(currentInfo.getCurrentDeptName());
		billRepaymentService.addBillRepayment(repaymentEntity, currentInfo);
		logger.info("裹裹生成还款单...");
		logger.info("调用还款冲应收...");
		// 调用还款冲应收服务
		BillWriteoffOperationDto billWriteoffOperationDto = new BillWriteoffOperationDto();
		//设置属性值
		billWriteoffOperationDto.setBillRepaymentEntity(repaymentEntity);
		//声明
		List<BillReceivableEntity> billReceivableEntitys = new ArrayList<BillReceivableEntity>();
		//设置属性值
		billReceivableEntitys.add(entity);
		//设置属性值
		billWriteoffOperationDto.setBillReceivableEntitys(billReceivableEntitys);
		// 核销批次号
		billWriteoffOperationDto.setWriteoffBatchNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HX_BN));
		// 核销單生成方式（手工輸入）
		billWriteoffOperationDto.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
		
		/**
		 *  核销网点编码和名称
		 */
		BillWriteoffOperationDto resultDto = billWriteoffService.writeoffRepaymentAndReceibable(billWriteoffOperationDto,currentInfo);
		
		// 核销完毕调用通知对账单接口
		StatementOfAccountUpdateDto updateDto = new StatementOfAccountUpdateDto();
		//设置属性值
		updateDto.setReceivableEntityList(resultDto.getBillReceivableEntitys());
		
		/**
		 * 调用接口更更新
		 */
		statementOfAccountService.updateStatementAndDetailForWriteOff(updateDto, currentInfo);
		
		// 如果互斥对象不为空,解锁该应收单对应的运单
		if (mutex != null) {
			// 解锁运单
			businessLockService.unlock(mutex);
		}
		//记录日志
		logger.info("裹裹网上支付还款sucessful,应收单号："+ entity.getReceivableNo());
		
		/**
		 * 还款成功之后
		 * 1.首先将更新暂存表:将是否核销 N-->Y
		 * 2.将暂存表这条数据移到转储表
		 */
		GreenHandWrapWriteoffEntity  greenEntity = new GreenHandWrapWriteoffEntity();
		greenEntity.setWaybillNo(request.getWaybillNo());
		greenEntity.setModifyTime(new Date());
		greenEntity.setModifyUser("System");
		//根据单号跟新暂存表中是否核销 N --> Y
		greenHandWrapWriteoffDao.updateGreenHandWrapInfo(greenEntity);
		logger.info("根据单号跟新暂存表中是否核销 N-->Y 成功!");
		
		//将暂存表的数据 转移到 转储表
		greenHandWrapWriteoffDao.addDumpGreenHandFromWrap(greenEntity);		
		logger.info("将暂存表的数据 转移到 转储表成功!");
		
		//将暂存表的数据删除
		greenHandWrapWriteoffDao.deleteWrapGreenHandInfo(greenEntity);
		logger.info("将暂存表的数据删除表成功!");
		
		//催款部门编码
		/*String dunningCode = entity.getDunningOrgCode();
		logger.info("催款部门编码为：" + dunningCode);
		//根据部门编码查询标杆编码（这里是根据应收单里的催款部门编码查询标杆编码）
		String dunningCodeDP = orgAdministrativeInfoService.queryUnifiedCodeByCode(dunningCode);
		logger.info("催款部门对应标杆编码为：" + dunningCodeDP);*/
		
		/*//调用财务自助接口
		logger.info("裹裹网上支付successful之后,begin调用财务自助接口...");
		WriteoffInformationDto writeoffInformationDto = new WriteoffInformationDto();
		writeoffInformationDto.setBillNum("GG" + request.getWaybillNo());//设置裹裹网上支付编码 ： GG+运单号
		writeoffInformationDto.setRemitDate(request.getDoptime());//设置Dop传过来的时间
		writeoffInformationDto.setRemitDept(dunningCodeDP);//汇款部门	(应收单中的催款部门对应的标杆编码DP开头的)
		writeoffInformationDto.setFundDept(dunningCodeDP);//款项所属部门(应收单中的催款部门对应的标杆编码DP开头的)
		writeoffInformationDto.setRemitTance(request.getDopAmount());//汇款金额(对应DOP传给我的金额)
		writeoffInformationDto.setAlreadyamount(fossVerifyAmount);//设置FOSS已核销金额
		writeoffInformationDto.setFundType("1");//款项类别   1：运费，2：补贴  (我传过去的是运费)
		
		//调用FINS接口
		greenHandWrapToFinanceClient.sendWriteoffWrapToFinance(writeoffInformationDto);*/
		//logger.info("调用财务自助接口成功Success...!");
	}

	/**
	 * @author 310970 caopeng
	 * @date 2016
	 */
	@Override
	public void addDopInfo(RequestGreenHandWrapEntity entity) {
		Date time =new Date();
		GreenHandWrapWriteoffEntity  greenHandWrapWriteoffEntity= new GreenHandWrapWriteoffEntity();
		//设置ID
		greenHandWrapWriteoffEntity.setId(UUIDUtils.getUUID());
		//设置运单号
		greenHandWrapWriteoffEntity.setWaybillNo(entity.getWaybillNo());
		//设置金额
		greenHandWrapWriteoffEntity.setAmount(entity.getDopAmount());
		//设置费用类型
		greenHandWrapWriteoffEntity.setCostType(entity.getCostType());
		//设置dop传数据过来的时间
		greenHandWrapWriteoffEntity.setDopTime(time);
		//设置创建人
		greenHandWrapWriteoffEntity.setCreateUser("System");
		//设置是否有效状态
		greenHandWrapWriteoffEntity.setActive(FossConstants.ACTIVE);
		//设置核销状态
		greenHandWrapWriteoffEntity.setWriteoffStatus("N");
		//设置创建时间
		greenHandWrapWriteoffEntity.setCreateTime(time);
		//设设置修改时间
		greenHandWrapWriteoffEntity.setModifyTime(null);
		//设置修改人
		greenHandWrapWriteoffEntity.setModifyUser(null);
		//设置备注
		greenHandWrapWriteoffEntity.setNote(null);
		int num=greenHandWrapWriteoffDao.addDopInfo(greenHandWrapWriteoffEntity);
		logger.info("插表结果为"+num);	
	}

	public void setGreenHandWrapWriteoffDao(
			IGreenHandWrapWriteoffDao greenHandWrapWriteoffDao) {
		this.greenHandWrapWriteoffDao = greenHandWrapWriteoffDao;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setBillRepaymentService(IBillRepaymentService billRepaymentService) {
		this.billRepaymentService = billRepaymentService;
	}

	public void setBillWriteoffService(IBillWriteoffService billWriteoffService) {
		this.billWriteoffService = billWriteoffService;
	}

	public void setStatementOfAccountService(
			IStatementOfAccountService statementOfAccountService) {
		this.statementOfAccountService = statementOfAccountService;
	}

	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	/*public void setGreenHandWrapToFinanceClient(
			IGreenHandWrapToFinanceClient greenHandWrapToFinanceClient) {
		this.greenHandWrapToFinanceClient = greenHandWrapToFinanceClient;
	}*/
	
}
