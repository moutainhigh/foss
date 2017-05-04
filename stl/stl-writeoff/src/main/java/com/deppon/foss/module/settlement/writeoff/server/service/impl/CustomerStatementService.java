package com.deppon.foss.module.settlement.writeoff.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPublicBankAccountService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PublicBankAccountEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.ICustomerStatementDao;
import com.deppon.foss.module.settlement.writeoff.api.server.service.ICustomerStatementService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.CustomerStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.CustomerStatementEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.CustomerStatementDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

public class CustomerStatementService implements ICustomerStatementService {

	//日志
	private static final Logger logger = LoggerFactory.getLogger(CustomerStatementService.class);
	/*
	 * 客户对账单DAO
	 */
	private ICustomerStatementDao customerStatementDao;
	/*
	 * 获取结算单据编号服务
	 */
	private ISettlementCommonService settlementCommonService;
	/**
	 * 子公司银行信息
	 */
	private IPublicBankAccountService publicBankAccountService;
	/**
	 * 注入更改单SERVICE
	 */
	private IWaybillRfcService waybillRfcService;
	/**
	 * 注入应收单service
	 */
	private IBillReceivableService billReceivableService;
	/**
	 * 互斥锁接口
	 */
	private IBusinessLockService businessLockService;
	/**
	 * 客户对账单新增查询应收单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public CustomerStatementDto queryCustomerStatementD(CustomerStatementDto dto,int start,int limit) {
		//对账单明细列表
		List<CustomerStatementDEntity> customerStatementDList = new ArrayList<CustomerStatementDEntity>();
		//对账单DTO
		CustomerStatementDto customerStatementDto = new CustomerStatementDto();
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户所在部门
		dto.setOrgCode(currentInfo.getCurrentDeptCode());
		//设置总行数
		int count = 0;
		//判断查询页签，按客户查询，否则按来源单号查询
		if(SettlementConstants.TAB_QUERY_BY_DATE.equals(dto.getQueryTabType())){
			//根据时间客户查询应收应付单据
			customerStatementDList = customerStatementDao.queryCustomerStatementDByCustomer(dto,start,limit);
			//根据时间客户查询应收应付总行数
			count = customerStatementDao.countCustomerStatementDByCustomer(dto);
		} else {
			//按单号查询不需要分页，设置总行数为单号个数
			count = dto.getNumbers().size();
			//按应收应付单号查询应收应付单
			customerStatementDList = customerStatementDao.queryCustomerStatementDByNumber(dto);
		}
		//设置对账单明细返回列表
		customerStatementDto.setCustomerStatementDList(customerStatementDList);
		//设置对账单明细返回总行数
		customerStatementDto.setCount(count);
		//返回参数
		return customerStatementDto;
	}
	
	/**
	 * 生成客户对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Transactional
	@Override
	public CustomerStatementDto customerStatementSave(CustomerStatementDto dto) {
		//记录日志
		logger.info("生成对账单，enter service...");
		//获取所有运单号集合
		List<String> waybillNos = dto.getNumbers();
		//生成对账单
		if (CollectionUtils.isNotEmpty(waybillNos)) {
			//添加互斥锁收集数据
			List<MutexElement> mutexElements = new ArrayList<MutexElement>();
			//循环处理运单号
			for (String waybill : waybillNos) {
				// 业务互锁运单号
				MutexElement mutexElement = new MutexElement(waybill, "对账单确认操作", MutexElementType.WAYBILL_NO);
				//加入互斥对象集合
				mutexElements.add(mutexElement);
			}
			// 校验该对账单明细中的单据所关联的运单是否存在有未受理的更改单
			// 运单号个数小于等于1000个时，直接查询返回
			if (waybillNos.size() <= SettlementConstants.MAX_BILL_NO_SIZE) {
				// 传入运单号或者运单号数组集合
				// 判断传入的运单号是否存在未处理的更改单
				List<String> changeWaybillNoList = waybillRfcService.isExsitsWayBillRfcs(waybillNos);
				// 如果有则抛出异常,并抛出对账单号和运单号
				//如果存在未受理的更改单
				if (CollectionUtils.isNotEmpty(changeWaybillNoList)) {
					//生成储存运单buffer
					StringBuffer buffer = new StringBuffer();
					//循环处理运单列表
					for (int i = 0; i < changeWaybillNoList.size(); i++) {
						//如果第一次循环
						if (i != 0) {
							//拼接,
							buffer.append(",");
						}
						//拼接运单号
						buffer.append(changeWaybillNoList.get(i));
					}
					//对账单XXX存在有未受理的更改单，不能进行确认对账单操作！
					//存在更改的运单号为XXX
					throw new SettlementException("运单存在有未受理的更改单，不能进行生成对账单操作！存在更改的运单号为"+ buffer, "");
				}
			}// 运单号个数大于1000个时，分批查询
			else {
				List<String> queryWaybillNos = new ArrayList<String>();
				for (int index = 0; index < waybillNos.size(); index++) {
					queryWaybillNos.add(waybillNos.get(index));
					if (queryWaybillNos.size() == SettlementConstants.MAX_BILL_NO_SIZE) {
						// 传入运单号或者运单号数组集合
						// 判断传入的运单号是否存在未处理的更改单
						List<String> changeWaybillNoList = waybillRfcService.isExsitsWayBillRfcs(queryWaybillNos);

						// 如果有则抛出异常,并抛出对账单号和运单号
						//如果存在未受理的更改单
						validaWaybillNoList(changeWaybillNoList);
						queryWaybillNos.clear();
					}
				}
				validaWaybillNos(queryWaybillNos);
			}
			//根据传入的一到多个应收单号，获取一到多条应收单信息
			List<BillReceivableEntity> receiveableEntityList = billReceivableService.queryBySourceBillNOs(waybillNos,SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,FossConstants.ACTIVE);
			// 校验应收单是否被网厅锁定
			//如果应收单号不为空
			if (CollectionUtils.isNotEmpty(receiveableEntityList)) {
				//生成应收单锁定列表
				List<String> lockBillList = new ArrayList<String>();
				//循环处理应收单
				for (BillReceivableEntity receiveEntity : receiveableEntityList) {
					//如果应收单被锁定
					if (receiveEntity.getUnlockDateTime() != null&& receiveEntity.getUnlockDateTime().after(new Date())) {
						//加入到锁定应收单列表
						lockBillList.add(receiveEntity.getReceivableNo());
					}
				}
				// 如果有则抛出异常,并抛出对账单号和运单号
				//如果锁定应收单不为空
				if (CollectionUtils.isNotEmpty(lockBillList)) {
					//生成应收单号buffer
					StringBuffer receiveNos = new StringBuffer();
					//循环处理应收单号列表
					for (int i = 0; i < lockBillList.size(); i++) {
						//循环第一次
						if (i != 0) {
							//拼接,
							receiveNos.append(",");
						}
						//拼接应收单号
						receiveNos.append(lockBillList.get(i));
					}
					//提示对账单XXXX存在被网厅锁定的应收单，不能进行确认对账单操作！应收单号为XXXXX
					throw new SettlementException("存在应收单被网厅锁定的应收单，不能进行生成对账单操作！应收单号为" + receiveNos);
				}
			}
			//添加互斥锁
			//如果互斥对象集合不为空
			if(CollectionUtils.isNotEmpty(mutexElements)){
				//锁定
				businessLockService.lock(mutexElements, SettlementConstants.BUSINESS_LOCK_BATCH);
			}		
			//进行性对账单新增操作
			// 获取当前登录信息
			CurrentInfo info = FossUserContext.getCurrentInfo();
			//生成对账单单号
			String statementNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.DZ);
			//设置对账单单号
			dto.setStatementBillNo(statementNo);
			//获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//获取当前用户工号
			dto.setEmpCode(currentInfo.getEmpCode());
			//获取当前用户姓名
			dto.setEmpName(currentInfo.getEmpName());
			//获取当前用户所在部门编码
			dto.setOrgCode(currentInfo.getCurrentDeptCode());
			//获取当前用户所在部门名称
			dto.setOrgName(currentInfo.getCurrentDeptName());
			//获取当前用户所在部门标杆编码
			dto.setUnifiedCode(currentInfo.getDept().getUnifiedCode());
			// 调用综合管理接口获取公司的对公银行账号信息
			PublicBankAccountEntity publicBankAccountEntity = new PublicBankAccountEntity();
			// 部门标杆编码信息
			publicBankAccountEntity.setDeptCd(info.getDept().getUnifiedCode());
			//查询部门所属银行信息
			List<PublicBankAccountEntity> bankList = publicBankAccountService.queryPublicBankAccountExactByEntity(publicBankAccountEntity,0, Integer.MAX_VALUE);
			//如果银行账号信息为空则抛出异常
			if (CollectionUtils.isEmpty(bankList)) {
				//提示当前操作用户所属部门没有银行账号信息 
				throw new SettlementException("当前操作用户所属部门没有银行账号信息！", "");
			}else if (bankList.size() > 1) {
				//提示当前操作用户所属部门存在多条银行账号信息
				throw new SettlementException("当前操作用户所属部门存在多条银行账号信息！", "");
			}else{
				//获取第一条银行账号信息（业务上只允许一条，但综合提供的方法返回参数是集合）
				PublicBankAccountEntity bankAccountEntity = bankList.get(0);
				// 获取支行名称
				dto.setBankBranchName(bankAccountEntity.getSubbranchName()); 
				// 获取开户名
				dto.setAccountUserName(bankAccountEntity.getBankAccName());
				// 获取子公司账号
				dto.setCompanyAccountBankNo(bankAccountEntity.getBankAcc());
			}
			//插入行数
			int insertDRows = 0;
			if(SettlementConstants.TAB_QUERY_BY_DATE.equals(dto.getQueryTabType())){
				//判断客户编码是否为空
				if(null == dto.getCustomerCode() || "".equals(dto.getCustomerCode())){
					throw new SettlementException("客户编码为空不可以生成对账单！");
				}
				//按时间客户生成对账单明细
				insertDRows = customerStatementDao.customerStatementDSaveByCustomer(dto);
			}else{
				//按单号生成对账单明细
				insertDRows = customerStatementDao.customerStatementDSaveByNumber(dto);
			}
			//生成对账单
			//此刻参数中的numbers的值为运单号，即用户输入的运单号，为应收单中的来源单号
			int insertRows = customerStatementDao.customerStatementSaveByStatementBillNo(dto);
			//判断是否生成一条对账单
			if(insertRows != 1){
				throw new SettlementException("生成对账单失败，插入条数大于1，请确认应收单客户是否一致");
			}
			//更新应收单
			int updateRecRows = customerStatementDao.customerRecUpdateByStatementBillNo(dto);
			//判断更新的应收应付单据总条数是否和生成的对账单明细条数是否相等
			if(updateRecRows != insertDRows){
				throw new SettlementException("对账单明细生成条数和应收单生成条数不一致，请重新查询！");
			}
			//去除互斥锁
			//如果互斥对象集合不为空
			if(CollectionUtils.isNotEmpty(mutexElements)){
				//解锁
				businessLockService.unlock(mutexElements);
			}
		} else {
			throw new SettlementException("运单号为空，不能进行生成对账单的操作");
		}
		//记录日志
		logger.info("生成对账单，successfully exit service...");
		//返回参数
		return dto;
	}

	private void validaWaybillNoList(List<String> changeWaybillNoList) {
		if (CollectionUtils.isNotEmpty(changeWaybillNoList)) {
			//生成储存运单buffer
			StringBuffer buffer = new StringBuffer();
			//循环处理运单列表
			for (int i = 0; i < changeWaybillNoList.size(); i++) {
				//如果第一次循环
				if (i != 0) {
					//拼接,
					buffer.append(",");
				}
				//拼接运单号
				buffer.append(changeWaybillNoList.get(i));
			}
			//对账单XXX存在有未受理的更改单，不能进行确认对账单操作！
			//存在更改的运单号为XXX
			throw new SettlementException("运单存在有未受理的更改单，不能进行新增对账单操作！存在更改的运单号为"+ buffer, "");
		}
	}

	private void validaWaybillNos(List<String> queryWaybillNos) {
		if (CollectionUtils.isNotEmpty(queryWaybillNos)) {
			// 传入运单号或者运单号数组集合
			// 判断传入的运单号是否存在未处理的更改单
			List<String> changeWaybillNoList = waybillRfcService.isExsitsWayBillRfcs(queryWaybillNos);

			validaWaybillNoList(changeWaybillNoList);
		}
	}
	
	/**
	 * 查询客户对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public CustomerStatementDto queryCustomerStatement(CustomerStatementDto dto, int start, int limit) {
		//对账单列表
		List<CustomerStatementEntity> customerStatementList = new ArrayList<CustomerStatementEntity>();
		//对账单DTO
		CustomerStatementDto customerStatementDto = new CustomerStatementDto();
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户的工号
		dto.setEmpCode(currentInfo.getEmpCode());
		//获取当前用户的姓名
		dto.setEmpName(currentInfo.getEmpName());
		FossUserContext.getCurrentUserManagerDeptCodes();
		//获取当前用户所在部门
		dto.setOrgCode(currentInfo.getCurrentDeptCode());
		//设置总行数
		int count = 0;
		//判断查询页签，按客户查询，否则按对账单单号查询
		if(SettlementConstants.TAB_QUERY_BY_DATE.equals(dto.getQueryTabType())){
			//根据时间客户查询对账单
			customerStatementList = customerStatementDao.queryCustomerStatementByCustomer(dto,start,limit);
			//根据时间客户查询对账单总行数
			count = customerStatementDao.countCustomerStatementByCustomer(dto);
		}else{
			//按单号查询不需要分页，设置总行数为单号个数
			count = dto.getNumbers().size();
			//按对账单单号查询对账单
			customerStatementList = customerStatementDao.queryCustomerStatementByNumber(dto);
		}
		//设置对账单返回列表
		customerStatementDto.setCustomerStatementList(customerStatementList);
		//设置对账单返回总行数
		customerStatementDto.setCount(count);
		//返回参数
		return customerStatementDto;
	}
	
	/**
	 * 双击对账单记录查看明细，按对账单单号查询
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public CustomerStatementDto queryCustomerDByStatementBillNo(CustomerStatementDto dto, int start, int limit) {
		//对账单DTO
		CustomerStatementDto customerStatementDto = new CustomerStatementDto();
		//按对账单单号分页查询对账单明细
		List<CustomerStatementDEntity> customerStatementDList = customerStatementDao.queryCustomerDByStatementBillNo(dto,start,limit);
		//按对账单单号查询对账单明细总行数
		int count = customerStatementDao.countCustomerDByStatementBillNo(dto);
		//设置对账单明细返回列表
		customerStatementDto.setCustomerStatementDList(customerStatementDList);
		//设置对账单明细返回总行数
		customerStatementDto.setCount(count);
		//返回参数
		return customerStatementDto;
	}
	
	/**
	 * 确认货反确认大客户对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Transactional
	@Override
	public void confirmOrUnConfirmStatement(CustomerStatementDto dto) {
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户工号
		dto.setEmpCode(currentInfo.getEmpCode());
		//获取当前用户姓名
		dto.setEmpName(currentInfo.getEmpName());
		//获取当前用户部门
		dto.setOrgCode(currentInfo.getCurrentDeptCode());
		//修改行数
		int updateRows = 0;
		//定义list列表
		List<String> list = new ArrayList<String>();
		//获取对账单单号
		list.add(dto.getStatementBillNo());
		//设置单号
		dto.setNumbers(list);
		//按单号集合查询对账单
		List<CustomerStatementEntity> customerStatementList = customerStatementDao.queryCustomerStatementByNumber(dto);
		if(null != customerStatementList && customerStatementList.size() > 0){
			//获取对账单
			CustomerStatementEntity customerStatementEntity = customerStatementList.get(0);
			if(customerStatementEntity.getPeriodAmount().compareTo(customerStatementEntity.getUnpaidAmount()) != 0){
				throw new SettlementException("对账单已经核销不可以进行确认或反确认操作！");
			}
			//判断对账单是否存在有效的核销单
			int writeoffBillCount = customerStatementDao.queryWriteoffBillByStatementBillNo(customerStatementEntity.getStatementBillNo());
			if(writeoffBillCount > 0){
				throw new SettlementException("对账单已经核销不可以进行确认或反确认操作！");
			}
			//判断确认状态
			if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM.equals(
					dto.getConfirmStatus())){
				//判断对账单是否为确认状态
				if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM.equals(
						customerStatementEntity.getConfirmStatus())){
					throw new SettlementException("对账单已经为确认状态不可以再次确认！");
				}
				//插入确认时间
				dto.setConfirmTime(new Date());
				//插入确认人工号
				dto.setConfirmUserCode(currentInfo.getEmpCode());
				//插入确认人名称
				dto.setConfirmUserName(currentInfo.getEmpName());
			}else if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM.equals(
					dto.getConfirmStatus())){
				//判断对账单是否为未确认状态
				if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM.equals(
						customerStatementEntity.getConfirmStatus())){
					throw new SettlementException("对账单已经为未确认状态不可以再次未确认！");
				}
				//清空确认时间
				dto.setConfirmTime(null);
				//清空确认人工号
				dto.setConfirmUserCode(null);
				//清空确认人名称
				dto.setConfirmUserName(null);
			}else{
				throw new SettlementException("对账单确认状态错误！");
			}
			//更新对账单确认状态
			updateRows = customerStatementDao.confirmOrUnConfirmStatement(dto);
		}else{
			throw new SettlementException("没有找到对账单数据，请确认对账单是否存在！");
		}
		//判断更新行数
		if(updateRows == 0){
			throw new SettlementException("对账单不存在，请重新查询对账单！");
		}
	}
	/**
	 * 按对账单单号查询对账单信息
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public CustomerStatementEntity queryByStatementNo(String statementNo) {
		CustomerStatementEntity customerStatementEntity = null;
		//对账单DTO
		CustomerStatementDto customerStatementDto = new CustomerStatementDto();
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户所在部门
		customerStatementDto.setOrgCode(currentInfo.getCurrentDeptCode());
		//单号列表
		List<String> numbers = new ArrayList<String>();
		//添加对账单单号
		numbers.add(statementNo);
		//设置对账单单号
		customerStatementDto.setNumbers(numbers);
		//按对账单单号查询对账单
		List<CustomerStatementEntity> list = customerStatementDao.queryCustomerStatementByNumber(customerStatementDto);
		
		if(CollectionUtils.isNotEmpty(list)){
			customerStatementEntity = list.get(0);
		}
		
		//返回对账单实体
		return customerStatementEntity;
	}
	/**
	 * 按对账单单号查询对账单明细信息
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public List<CustomerStatementDEntity> queryByStatementBillNo(String statementNo) {
		//对账单DTO
		CustomerStatementDto customerStatementDto = new CustomerStatementDto();
		//设置对账单单号
		customerStatementDto.setStatementBillNo(statementNo);
		//按对账单单号查询对账单明细
		List<CustomerStatementDEntity> list = customerStatementDao.queryCustomerDByStatementBillNo(customerStatementDto);
		//返回对账单明细列表
		return list;
	}
	/**
	 * 更新大客户对账单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public void updateStatementForWriteOff(CustomerStatementEntity entity,CurrentInfo cInfo) {
		//对账单DTO
		CustomerStatementDto customerStatementDto = new CustomerStatementDto();
		//发生金额
		customerStatementDto.setPeriodAmount(entity.getPeriodAmount());
		//未核销金额
		customerStatementDto.setUnpaidAmount(entity.getUnpaidAmount());
		//单据类型
		customerStatementDto.setBillType(entity.getBillType());
		//当前用户工号
		customerStatementDto.setEmpCode(cInfo.getEmpCode());
		//当前用户姓名
		customerStatementDto.setEmpName(cInfo.getEmpName());
		//对账单单号
		customerStatementDto.setStatementBillNo(entity.getStatementBillNo());
		//更新对账单
		customerStatementDao.customerStatementUpdateByStatementBillNo(customerStatementDto);
	}
	/**
	 * 大客户对账单添加明细查询应收单
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public CustomerStatementDto queryAddCustomerStatementD(CustomerStatementDto dto, int start, int limit) {
		//对账单DTO
		CustomerStatementDto customerStatementDto = new CustomerStatementDto();
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户所在部门
		dto.setOrgCode(currentInfo.getCurrentDeptCode());
		//对账单明细列表
		List<CustomerStatementDEntity> customerStatementDList = customerStatementDao.queryAddCustomerStatementD(dto);
		//设置对账单明细返回列表
		customerStatementDto.setCustomerStatementDList(customerStatementDList);
		//返回参数
		return customerStatementDto;
	}
	/**
	 * 大客户对账单添加明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Transactional
	@Override
	public CustomerStatementDto addCustomerStatementD(CustomerStatementDto customerStatementDto) {
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户工号
		customerStatementDto.setEmpCode(currentInfo.getEmpCode());
		//获取当前用户姓名
		customerStatementDto.setEmpName(currentInfo.getEmpName());
		//获取当前用户所在部门
		customerStatementDto.setOrgCode(currentInfo.getCurrentDeptCode());
		//插入行数
		int insertRows = 0;
		//更新行数
		int updateRows = 0;
		//按应收单号查询对账单明细
		//此时参数中的numbers的值为运单号，即对应收单中的来源单号
		List<CustomerStatementDEntity> customerStatementDList = customerStatementDao.queryDelCustomerStatementD(customerStatementDto);
		//判断对账单是否存在同一个对账单
		if(CollectionUtils.isNotEmpty(customerStatementDList)){
			throw new SettlementException("应收单据已经在此对账单，不可以重复新增！");
		}
		//判断插入页签
		if(SettlementConstants.TAB_QUERY_BY_DATE.equals(customerStatementDto.getQueryTabType())){
			//判断客户编码是否为空
			if(null == customerStatementDto.getCustomerCode() || "".equals(customerStatementDto.getCustomerCode())){
				throw new SettlementException("客户编码为空不可以生成对账单！");
			}
			//按时间客户添加对账单明细
			insertRows = customerStatementDao.addCustomerStatementDByCustomer(customerStatementDto);
		}else{
			//按来源单号添加对账单明细
			insertRows = customerStatementDao.addCustomerStatementDByNumber(customerStatementDto);
		}
		//判断插入行数
		if(insertRows == 0){
			throw new SettlementException("对账单明细插入失败，新增条数为零！");
		}
		//查询最新对账单明细
		List<CustomerStatementDEntity> list = customerStatementDao.queryCustomerDByStatementBillNo(customerStatementDto);
		//设置对账单金额
		sumRecAndPayAmount(customerStatementDto,list);
		//按对账单单号更新对账单金额
		updateRows = customerStatementDao.customerStatementUpdateByStatementBillNo(customerStatementDto);
		//判断更新行数
		if(updateRows != 1){
			throw new SettlementException("对账单单号存在重复！");
		}
		//更新应收单
		int updateRecRows = customerStatementDao.customerRecUpdateByStatementBillNo(customerStatementDto);
		//判断更新行数
		if(updateRecRows == 0){
			throw new SettlementException("对账单明细插入失败，应收应付单据更新条数同时为零！");
		}
		//返回参数
		return customerStatementDto;
	}
	/**
	 * 设置对账单金额，单据类型
	 * @author ddw
	 * @date 2014-06-16
	 */
	private void sumRecAndPayAmount(CustomerStatementDto customerStatementDto,List<CustomerStatementDEntity> list){
		// 本期发生金额
		BigDecimal periodAmount = BigDecimal.ZERO; 
		// 本期应收金额
		BigDecimal periodRecAmount = BigDecimal.ZERO; 
		//循环对账单明细计算应收应付金额
		for(CustomerStatementDEntity dEntity : list){
			//计算应收金额
			if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE.equals(dEntity.getBillParentType())){
				periodRecAmount = periodRecAmount.add(dEntity.getUnverifyAmount());
			}
		}
		//计算本期发生金额，应收金额减去应付金额
		periodAmount = periodRecAmount;
		//设置对账单本期应收金额
		customerStatementDto.setPeriodRecAmount(periodRecAmount);
		//设置发生金额
		customerStatementDto.setPeriodAmount(periodAmount);
		//设置未核销金额
		customerStatementDto.setUnpaidAmount(periodAmount);
	}
	/**
	 * 大客户对账单删除明细查询应收明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Override
	public CustomerStatementDto queryDelCustomerStatementD(CustomerStatementDto dto){
		//对账单DTO
		CustomerStatementDto customerStatementDto = new CustomerStatementDto();
		//按单号查询对账单明细
		List<CustomerStatementDEntity> customerStatementDList = customerStatementDao.queryDelCustomerStatementD(dto);
		//设置对账单明细返回列表
		customerStatementDto.setCustomerStatementDList(customerStatementDList);
		//返回参数
		return customerStatementDto;
	}
	/**
	 * 大客户对账单删除明细
	 * @author ddw
	 * @date 2014-06-16
	 */
	@Transactional
	@Override
	public CustomerStatementDto delCustomerStatementD(CustomerStatementDto customerStatementDto) {
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户工号
		customerStatementDto.setEmpCode(currentInfo.getEmpCode());
		//获取当前用户姓名
		customerStatementDto.setEmpName(currentInfo.getEmpName());
		//获取当前用户所在部门
		customerStatementDto.setOrgCode(currentInfo.getCurrentDeptCode());
		//按单号删除对账单明细--根据应收单号删除
		int updateRows = customerStatementDao.delCustomerStatementD(customerStatementDto);
		//查询最新对账单明细
		List<CustomerStatementDEntity> list = customerStatementDao.queryCustomerDByStatementBillNo(customerStatementDto);
		//设置对账单金额
		sumRecAndPayAmount(customerStatementDto,list);
		//按对账单单号更新对账单金额
		updateRows = customerStatementDao.customerStatementUpdateByStatementBillNo(customerStatementDto);
		//判断更新行数
		if(updateRows != 1){
			throw new SettlementException("对账单单号存在重复！");
		}
		//更新应收单--根据应收单号更新
		int updateRecRows = customerStatementDao.updateRecStatementBillNo(customerStatementDto);
		//判断更新行数
		if(updateRecRows == 0){
			throw new SettlementException("对账单明细删除失败，应收单据更新条数为零！");
		}
		//返回参数
		return customerStatementDto;
	}
	
	public void setSettlementCommonService(ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	public void setCustomerStatementDao(ICustomerStatementDao customerStatementDao) {
		this.customerStatementDao = customerStatementDao;
	}

	public void setPublicBankAccountService(IPublicBankAccountService publicBankAccountService) {
		this.publicBankAccountService = publicBankAccountService;
	}
	
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}
	/**
	 * @param billReceivableService
	 */
	public void setBillReceivableService(IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}
	/**
	 * @param businessLockService
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

}
