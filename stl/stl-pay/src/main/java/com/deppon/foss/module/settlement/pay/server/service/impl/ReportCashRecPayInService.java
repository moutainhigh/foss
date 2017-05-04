package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.pay.api.server.dao.ICashCollectionIncomeDao;
import com.deppon.foss.module.settlement.pay.api.server.dao.ICashCollectionRptDEntityDao;
import com.deppon.foss.module.settlement.pay.api.server.dao.ICashCollectionRptEntityDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IReportCashRecPayInService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.CashCollectionIncomeEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.CashCollectionRptDEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.CashCollectionRptEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInDDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInDResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.UpdateCashIncomerptDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.UUIDUtils;

/**
 * 缴款报表服务
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-16 下午2:13:56
 */
public class ReportCashRecPayInService implements IReportCashRecPayInService {

	/**
	 * Logger 现金收入缴款报表服务
	 */
	private static final Logger LOGGER = LogManager
			.getLogger(ReportCashRecPayInService.class);
	
	/**
	 * 现金收入缴款报表
	 */
	private ICashCollectionRptEntityDao cashCollectionRptEntityDao;

	/**
	 * 现金收入缴款明细报表
	 */
	private ICashCollectionRptDEntityDao cashCollectionRptDEntityDao;

	/**
	 * 根据部门编码获取标杆编码
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	
	/**
	 * 缴款记录
	 */
	private ICashCollectionIncomeDao cashCollectionIncomeDao;
	
	
	/**
	 * 互斥锁接口
	 */
	private IBusinessLockService businessLockService;

	/**
	 * 查询FOSS（缴款）报表明细
	 * FOSS缴款报表使用
	 * MODIFY DATE 2013-09-02 092036-FOSS-BOCHENLONG
	 * HAS CHECK
	 * @author 095793-foss-LiQin
	 * @date 2012-12-12 下午5:24:31
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IReportCashRecPayInService#queryReportCashRecPayInD(com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInDDto,
	 *      int, int)
	 */
	@Override
	public BillCashRecPayInDResultDto queryReportCashRecPayInD(
			BillCashRecPayInDDto billCashRecDDto, CurrentInfo cInfo,int start,int limit) {
		
		// 根据用户选择的现金收入缴款报表单，查询现金收入报表明
		LOGGER.info("查询FOSS缴款报表明细.... into");
		
		//现金收入缴款明细汇总		
		List<CashCollectionRptDEntity> cashColRptDQListByPage = null;
		//现金缴款报表明细返回		
		BillCashRecPayInDResultDto cashRecDResultDto = null;
		
		// 查询当前时间+1天
		if (null != billCashRecDDto.getBusinessEndDate()) {
			billCashRecDDto.setBusinessEndDate(DateUtils.addDayToDate(billCashRecDDto.getBusinessEndDate(), 1));
		}

		// 当前部门code为收入报表收款部门改为界面传递
		//billCashRecDDto.setCollectionOrgCode(cInfo.getCurrentDeptCode());
		
		/**
		 * 现金缴款报表要求查询实时的数据，所以每次查询前先调用生成现金缴款报表的存储过程
		 * add 2013-03-28   dp-liqin
		 */
		LOGGER.info("开始调用存储过程，按部门编码："+billCashRecDDto.getCollectionOrgCode()+"生成或更新缴款明细");
		
		
		//添加互斥锁收集数据
		List<MutexElement> mutexElements = new ArrayList<MutexElement>();
		// 业务互锁运单号
		MutexElement mutexElement = new MutexElement(billCashRecDDto.getCollectionOrgCode(),"按部门生成缴款报表时,锁定部门", MutexElementType.CASH_IN_COME_LOCK_DEPT_CODE);
		//加入互斥对象集合
		mutexElements.add(mutexElement);
		//添加互斥锁
		if(CollectionUtils.isNotEmpty(mutexElements)){
			//锁定
			if(!businessLockService.lock(mutexElements, SettlementConstants.BUSINESS_LOCK_BATCH)) {
				throw new SettlementException("资源已被占用，请稍后再操作！");
			}
		}
		
		//获取当前时间		
		Date now = new Date();
		//存储过程，生成当前时间和当前时间前一天的数据		
		createOneReportCashRecPayIn(SettlementConstants.CASH_BEGIN_DATE, now,
				billCashRecDDto.getCollectionOrgCode(), now);
		LOGGER.info("结束存储过程调用----------------");
		
		
		//去除互斥锁
		if(CollectionUtils.isNotEmpty(mutexElements)){
			//解锁
			businessLockService.unlock(mutexElements);
		}
		
		//按输入参数查询现金缴款报表的条数		
		cashRecDResultDto=cashCollectionRptDEntityDao.queryCountByCashRecPayDIn(billCashRecDDto);
		LOGGER.info("明细条数：" + cashRecDResultDto.getTotalCount());
		
		//查询现金缴款明细报表的总数		
		if(cashRecDResultDto.getTotalCount()>0){
			cashColRptDQListByPage=cashCollectionRptDEntityDao.queryByCashRecPayInD(billCashRecDDto,start,limit);
		}
		cashRecDResultDto.setCashRpDList(cashColRptDQListByPage);
		LOGGER.info("查询FOSS缴款报表明细....   exit");
		return cashRecDResultDto;
	}
	
	

	/**
	 * 导出FOSS现金收入（缴款）报表
	 * 
	 * MODIFY DATE 2013-09-02 092036-FOSS-BOCHENLONG
	 * HAS CHECK
	 * @author 095793-foss-LiQin
	 * @date 2012-12-13 上午10:32:33
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IReportCashRecPayInService#exportCashRecPayIn(com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public HSSFWorkbook exportCashRecPayIn(BillCashRecPayInDDto cashRecPayDIn,
			CurrentInfo cInfo) {
		/**
		 * 如果导出现金收入缴款报表列头名称不存在，则抛出异常提示
		 */
		if (cashRecPayDIn.getArrayColumnNames() == null
				|| cashRecPayDIn.getArrayColumnNames().length == 0) {
			throw new SettlementException("导出Excel的列头名称不存在，请至少存在一列!");
		}
		/**
		 * 如果导出现金收入缴款报表列头名称不存在，则抛出异常提示
		 */
		if (cashRecPayDIn.getArrayColumns() == null
				|| cashRecPayDIn.getArrayColumns().length == 0) {
			throw new SettlementException("导出Excel的列头不存在，请至少存在一列!");
		}
		// 调用执行方法，获取结果集
		BillCashRecPayInDResultDto resultDto = queryReportCashRecPayInD(
				cashRecPayDIn,cInfo,0, Integer.MAX_VALUE);
		// 判断要导出数据是否存在，若不存在，则抛出异常提示
		if (resultDto == null || resultDto.getCashRpDList() == null
				|| resultDto.getCashRpDList().size() == 0) {
			throw new SettlementException("没有要导出的数据!");
		}
		// 将要导出东西封装转化成Excel导出要用的List<List<String>> 格式
		List<List<String>> rowList = convertListFormEntity(
				resultDto.getCashRpDList(), cashRecPayDIn.getArrayColumns());

		// 获取导出数据
		SheetData data = new SheetData();
		// 设置导出列头
		data.setRowHeads(cashRecPayDIn.getArrayColumnNames());
		data.setRowList(rowList);
		// 获取平台提供导出函数
		ExcelExport export = new ExcelExport();
		// 返回 wookbook
		HSSFWorkbook wookbook = export.exportExcel(data, "sheet",
				SettlementConstants.EXPORT_MAX_COUNT);
		return wookbook;
	}

	/**
	 * 查询现金(缴款)报表转化的实体
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-5 下午8:01:16
	 */
	private List<List<String>> convertListFormEntity(
			List<CashCollectionRptDEntity> list, String[] header) {
		// 声明sheetList
		List<List<String>> sheetList = new ArrayList<List<String>>();
		
		//声明字典集合
		List<String> termCodes = new ArrayList<String>();
		termCodes.add(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE);
		termCodes.add(DictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE);
		termCodes.add(DictionaryConstants.BILL_DEPOSIT_RECEIVED__STATUS);
		//后台获取数据字典对应的数据
		Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(termCodes);
		
		// 循环进行封装
		for (CashCollectionRptDEntity entity : list) {
			// 声明一行的rowList
			List<String> rowList = new ArrayList<String>();
			for (String columnName : header) {
				// 通过名称获取field
				Field field = ReflectionUtils.findField(
						CashCollectionRptDEntity.class, columnName);
				if (field != null) {
					// 通过传入字段获取值
					ReflectionUtils.makeAccessible(field);
					
					Object fieldValue = ReflectionUtils.getField(field, entity);
					// 如果为日期，需要进行格式化
					if (Date.class.equals(field.getType())
							&& fieldValue != null) {
						fieldValue = DateUtils.convert((Date) fieldValue,
								DateUtils.DATE_TIME_FORMAT);
					}
					 
					// 如果为审核状态，则需要转化
					if (columnName.equals("paymentType") && fieldValue != null) {
						fieldValue = SettlementUtil.getDataDictionaryByTermsName(map, 
								DictionaryConstants.SETTLEMENT__PAYMENT_TYPE, fieldValue.toString());
					}
					
					
					// 如果为审核状态，则需要转化
					if (columnName.equals("sourceBillType") && fieldValue != null) {
						fieldValue = SettlementUtil.getDataDictionaryByTermsName(map, 
								DictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE, fieldValue.toString());
					}
					
					// 将字段封装到list中
					if (fieldValue != null) {
						rowList.add(fieldValue.toString());
					} else {
						rowList.add(null);
					}
				}
			}
			sheetList.add(rowList);
		}
		return sheetList;
	}

	/**
	 * 更新现金盘点缴款
	 * MODIFY DATE 2013-09-02 092036-FOSS-BOCHENLONG
	 * HAS CHECK
	 * 财务自助根据部门传递需更新的盘点现金汇总金额 （标杆编码和付款总金额）， Foss根据部门（调用综合标杆部门编码对应的部门编码）和总金额，
	 * 更新Foss现金缴款和现金明细
	 * 
	 * 1、生成现金缴款报表 2、调用更新现金盘点应缴款服务处理 （1.先查现金收入汇总 2、根据汇总查明细 3、遍历明细，更新金额）
	 * 
	 * @author 045738-foss-LiQin
	 * @date 2013-1-8 下午12:07:05
	 * @param dto
	 * @return
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IReportCashRecPayInService#updateCashIncomerptForProcessor(com.deppon.foss.module.settlement.pay.api.shared.dto.UpdateCashIncomerptDto)
	 */
	@Transactional
	@Override
	public Map<String ,Object> updateCashIncomerptForProcessor(UpdateCashIncomerptDto dto){
		
		LOGGER.info("更新缴款信息缴款接口开始调用 service into......");
		// 缴款金额
		BigDecimal payAmount = dto.getPayAmount();	
		// 核销方式 -1为反核销，1为核销。。
		int verifyType = 1;
		if(payAmount.compareTo(BigDecimal.ZERO) == -1) {
			verifyType = -1;
			// 取绝对值进行计算
			payAmount = payAmount.abs();
		} 
		
		// 封装参数返回Map
		Map<String ,Object> map = new HashMap<String ,Object>();
		
		// 添加互斥锁收集数据
		List<MutexElement> mutexElements = new ArrayList<MutexElement>();
		// 业务互锁运单号
		MutexElement mutexElement = new MutexElement(dto.getDeptCd(), "按部门生成缴款报表时,锁定部门", MutexElementType.CASH_IN_COME_LOCK_DEPT_CODE);
		// 加入互斥对象集合
		mutexElements.add(mutexElement);
		// 添加互斥锁
		if(CollectionUtils.isNotEmpty(mutexElements)){
			//锁定
			final int numberOfTen = 10;
			if(!businessLockService.lock(mutexElements, SettlementConstants.BUSINESS_LOCK_BATCH*numberOfTen)) {
				throw new SettlementException("资源已被占用，请稍后再操作！");
			}
		}
		
		// 如果发生异常捕获抛出，除去互斥锁
		try {
			
			Date now = new Date();
			// 如果为核销，先加载最新数据
			if(verifyType == 1) {
				LOGGER.info("调用存储过程，更新或者创建部门" + dto.getDeptCd() + "缴款信息。");
				LOGGER.info("开始更新部门" + dto.getDeptCd() + "缴款明细。");
				createOneReportCashRecPayIn(SettlementConstants.CASH_BEGIN_DATE,
						now, dto.getDeptCd(), now);

			}
			
			// 更新明细
			Map<String, Object> m = updateIncomerptD(payAmount,dto.getDeptCd(), dto.getUnifiedCode(), dto.getPayMethod(), verifyType);
			BigDecimal amountUse = payAmount.subtract((BigDecimal) m.get("paytotalAmount"));

			int num = (Integer) m.get("num");

			LOGGER.info("更新缴款信息缴款接口结束调用 service exit......");

			// 返回NUM说明蓝单明细有更新记录
			// 如果使用金额为0，则说明红单金额=蓝单金额。无须核销（反核销paytotalAmount一定为0）
			if (num > 0 && amountUse.compareTo(BigDecimal.ZERO) > 0) {
				// 添加缴款记录
				CashCollectionIncomeEntity entity = new CashCollectionIncomeEntity();
				entity.setAmount(verifyType == -1 ? amountUse.multiply(new BigDecimal(-1)) : amountUse);
				entity.setBusinessDate(now);
				entity.setModifyTime(now);
				entity.setCreateTime(now);
				entity.setId(UUIDUtils.getUUID());
				entity.setOverdueAmount(BigDecimal.ZERO);
				entity.setPaidAmount(BigDecimal.ZERO);
				entity.setOrgCode(dto.getDeptCd());
				entity.setSerialno(dto.getSerialNO());
				entity.setUnifiedCode(dto.getUnifiedCode());
				entity.setVersionNo((short) 1);
				entity.setPaymentType(dto.getPayMethod());
				addCashIncome(entity);
			} else {
				throw new SettlementException("未有更新的明细记录：" + num + "或者使用金额为0或负：" + amountUse);
			}
			// 如果为反核销，返回负数
			map.put("amountUse", verifyType == -1 ? amountUse.multiply(new BigDecimal(-1)) : amountUse);
			map.put("num", 1);
			
		} catch (SettlementException e) {
			
			LOGGER.info(e.getErrorCode());
			throw new SettlementException(e.getErrorCode());
			
		} finally {
			// 去除互斥锁
			if (CollectionUtils.isNotEmpty(mutexElements)) {
				// 解锁
				businessLockService.unlock(mutexElements);
			}
		}
		
		return map;
	}

	/**
	 * 更新现金缴款报表明细
	 * MODIFY DATE 2013-09-02 092036-FOSS-BOCHENLONG
	 * HAS CHECK
	 * 修改 不再使用报表参数
	 * @author 045738-foss-LiQin
	 * @date 2013-1-8 下午6:26:05
	 * @param cashMap
	 */
	private Map<String ,Object> updateIncomerptD(BigDecimal paytotalAmount ,String deptCode ,String unifiedCode ,String payMethod ,int verifyType) {
		Map<String ,Object> map = new HashMap<String ,Object>();	
		
		// 按照付款方式查询明细
		// 核销方式就查询明细未核销金额不为0；反核销范式就查询核销金额>0，且满足核销金额>=paytotalAmount的业务时间内的明细
		List<CashCollectionRptDEntity> cashDList = queryUpdateCashincomerptD(deptCode ,unifiedCode ,payMethod ,verifyType ,paytotalAmount);
		// 业务日期进行排序
		//cashDList = sortCashCollection(cashDList);
		
		// 区分红单，蓝单；计算总金额
		// 红单List
		List<CashCollectionRptDEntity> redDList = new ArrayList<CashCollectionRptDEntity>();
		// 蓝单List
		List<CashCollectionRptDEntity> blueDList = new ArrayList<CashCollectionRptDEntity>();
		// 红单总金额
		BigDecimal underZeroAmountTotal = BigDecimal.ZERO;
		// 核销区分红蓝单
		if(verifyType == 1) {
			for (CashCollectionRptDEntity entity : cashDList) {
				// 如果未缴金额小于0，则为红单。放入红单List,加上金额
				if (BigDecimal.ZERO.compareTo(entity.getOverdueAmount()) > 0) {
					redDList.add(entity);
					underZeroAmountTotal = underZeroAmountTotal.add(entity.getOverdueAmount());
				} else {
					blueDList.add(entity);
				}
			}
		} else if(verifyType == -1) {
			// 反核销全部为蓝单直接复制
			blueDList = cashDList;
		}
				
		
		// 处理红单
		if (CollectionUtils.isNotEmpty(redDList)) {
			updateWriteOffCashincomerpt(redDList, paytotalAmount,
					Integer.valueOf(0), verifyType);
		}

		// 计算和冲销总金额 （加红单金额）
		paytotalAmount = paytotalAmount.subtract(underZeroAmountTotal);

		// 处理蓝单
		if (CollectionUtils.isNotEmpty(blueDList)) {
			map = updateWriteOffCashincomerpt(blueDList, paytotalAmount,
					new Integer(1), verifyType);
		} else {
			throw new SettlementException("只含有红单不能进行核销操作！");
		}
		
		return map;
	}



	/**
	 * 查询缴款明细
	 * MODIFY DATE 2013-09-02 092036-FOSS-BOCHENLONG
	 * CHECK
	 * 修改 不再使用报表参数
	 * @author 095793-foss-LiQin
	 * @date 2012-12-14 下午4:17:16
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IReportCashRecPayInService#queryUpdateCashincomerptD(java.lang.String,
	 *      java.lang.String)
	 */
	private List<CashCollectionRptDEntity> queryUpdateCashincomerptD(String deptCd,String unifiedCode ,String payMethod ,int verifyType ,BigDecimal paytotalAmount) {

		// 财务自助标杆编码、报表编码
		BillCashRecPayInDDto billCashDDto = new BillCashRecPayInDDto();
		billCashDDto.setCollectionOrgCode(deptCd);
		// 财务要求，财务自助银行卡、电汇、支票核销FOSS对应3种单据
		String[] paymentTypes = new String[]{
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD,
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER,
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE
		};
		// 如果为其中一种，则查询上三种数据
		if(Arrays.asList(paymentTypes).contains(payMethod)) {
			billCashDDto.setPaymentTypes(Arrays.asList(paymentTypes));
		} else {
			billCashDDto.getPaymentTypes().add(payMethod);
		}
		
		// 根据核销方式查询不同的单据 核销-未交款金额不等于0 反核销-已交款金额不等于0
		billCashDDto.setVerifyType(verifyType);
		
		// 反核销
		if (verifyType == -1) {
			// 获取31天前的时间
			final int numberOfThirtyOne = -31;
			Date businessDate = DateUtils.addDayToDate(new Date(), numberOfThirtyOne);
			billCashDDto.setBusinessDate(businessDate);
			
			BigDecimal paidAmount = cashCollectionRptDEntityDao.queryPaiAmount(billCashDDto);
			
			// 默认查询1个月，如果不够则循环往前查询
			if(paidAmount.compareTo(paytotalAmount) == -1) {
				businessDate = DateUtils.addDayToDate(businessDate, numberOfThirtyOne);
				
				Date earliestDate = cashCollectionRptDEntityDao.queryEarLiestDate(billCashDDto);
				
				if (earliestDate == null) {
					throw new SettlementException("没有缴款数据_businessdate取数为空！");
				}
				
				// 是否全额核销
				boolean flag = false;
				
				// 如果不能全额核销就查询更久以前
				while (businessDate.after(earliestDate)) {
					billCashDDto.setBusinessDate(businessDate);
					paidAmount = (BigDecimal) cashCollectionRptDEntityDao.queryPaiAmount(billCashDDto);
					// 如果缴款金额大于等于反核销金额结束循环
					if(paidAmount.compareTo(paytotalAmount) >= 0) {
						flag = true;
						break;
					}
					businessDate = DateUtils.addDayToDate(businessDate, numberOfThirtyOne);
				}
				
				// 如果不能全额核销
				if(!flag) {
					billCashDDto.setBusinessDate(earliestDate);
					paidAmount = (BigDecimal) cashCollectionRptDEntityDao.queryPaiAmount(billCashDDto);
					// 如果缴款金额小于反核销金额结束循环
					if(paidAmount.compareTo(paytotalAmount) == -1) {
						throw new SettlementException("反核销金额不能全额反核销！");
					}
				}
			}
		}
		
		// 根据部门和现金收入缴款汇总报表报表编码查询明细
		List<CashCollectionRptDEntity> cashDList = cashCollectionRptDEntityDao.queryUpdateCashinDComerpt(billCashDDto);
		
		if (CollectionUtils.isEmpty(cashDList)) {
			throw new SettlementException("部门编码：" + unifiedCode + "付款方式：" + payMethod + ",未找到需更新的现金收入明细!");
		}
		
		return cashDList;
	}
	

	/**
	 * 每天定时生成所有网点的现金收入报表及明细信息
	 * MODIFY DATE 2013-09-02 092036-FOSS-BOCHENLONG
	 * CHECK
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-12-15 下午5:18:07
	 */
	@Override
	public void createAllReportCashRecPayIn(Date beginDate, Date endDate) {
		// 设置查询结束日期加一天
		if (beginDate != null && endDate != null) {
			cashCollectionRptEntityDao.createAllReportCashRecPayIn(beginDate,endDate);
		} else {
			throw new SettlementException("开始日期或结束日期为空！", "");
		}
	}

	/**
	 * 实时生成单个网点的现金收入报表及明细信息
	 * MODIFY DATE 2013-09-02 092036-FOSS-BOCHENLONG
	 * CHECK
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-12-15 下午5:18:07
	 */
	@Override
	public void createOneReportCashRecPayIn(Date beginDate, Date endDateTime,
			String orgCode, 
			Date currentTime) {
		Date endDate=endDateTime;
		
		if (beginDate == null || endDate == null) {
			throw new SettlementException("开始日期或结束日期为空！");
		}
		if (StringUtil.isEmpty(orgCode)) {
			throw new SettlementException("网点编码为空！");
		}
		if (currentTime == null) {
			throw new SettlementException("当前时间为空！");
		}
		
		//int day=-30 / 60 / 60 / 24;
		// 将结束日期提前30秒，防止由于长事务引起的数据遗漏问题
		//endDate = DateUtils.addDayToDate(endDate,day);
		// 将结束日期提前10分钟，防止由于长事务引起的数据遗漏问题
		final int numberOfTten = -10;
		Calendar c = Calendar.getInstance();
		c.setTime(endDate);
		c.add(Calendar.MINUTE, numberOfTten);
		cashCollectionRptEntityDao.createOneReportCashRecPayIn(beginDate,c.getTime(), 
				orgCode, currentTime);

	}

	/**
	 * 
	 * 用于每日上传财务自助部门现金、非现金缴款金额
	 * MODIFY BY 092036-FOSS-BOCHENLONG 2013-09-03
	 * HAS CHECKED
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-12-19 上午10:04:57
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IReportCashRecPayInService#queryUploadCashAllAmount(java.util.Date,
	 *      java.util.Date)
	 */
	@Override
	public List<CashCollectionRptEntity> queryUploadCashAllAmount(
			Date beginDate, Date endDate) {
		LOGGER.info("每天定时查询网点前一天的现金、非现金缴款金额 service begin............");
		if (beginDate == null || endDate == null) {
			throw new SettlementException("开始日期或结束日期为空！");
		}
		// 付款方式-现金
		List<String>paymentList=new ArrayList<String>();
		paymentList.add(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);

		// 查询部门的现金、非现金缴款金额		
		List<CashCollectionRptEntity> list = cashCollectionRptEntityDao.queryUploadCashAllAmount(beginDate,endDate,paymentList);
		
		// 存放转换成标杆编码的集合
		List<CashCollectionRptEntity> cashList = new ArrayList<CashCollectionRptEntity>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (CashCollectionRptEntity cashEntity : list) {
				// 根据部门获取部门标杆编码
				if (StringUtil.isNotEmpty(cashEntity.getOrgCode())) {
					OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService
							.queryOrgAdministrativeInfoByCode(cashEntity.getOrgCode());
					if (orgEntity != null&& StringUtil.isNotEmpty(orgEntity.getUnifiedCode())) {
						
						cashEntity.setOrgCode(orgEntity.getUnifiedCode());

					} else {
						LOGGER.error("网点编码" + cashEntity.getOrgCode()+ "的标杆编码不存在！");
						throw new SettlementException("网点编码"+ cashEntity.getOrgCode() + "的标杆编码不存在！");
					}
				}
				cashList.add(cashEntity);
			}
		}
		LOGGER.info("每天定时查询网点前一天的现金、非现金缴款金额 service exit..............");
		
		return cashList;

	}

	/**
	 * 查询部门，未交账金额
	 * MODIFY BY 092036-FOSS-BOCHENLONG 2013-09-03
	 * HAS CHECKED
	 * @author 095793-foss-LiQin
	 * @date 2012-12-19 下午3:57:15
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IReportCashRecPayInService#queryCashinComerptDebtAmount(com.deppon.foss.module.settlement.pay.api.shared.domain.CashCollectionRptEntity)
	 */
	@Override
	public BillCashRecPayInResultDto queryCashinComerptDebtAmount(
			CashCollectionRptEntity entity) {
		LOGGER.info("部门编码" + entity.getOrgCode() + "info");
		BillCashRecPayInResultDto resultDto = new BillCashRecPayInResultDto();
		//查询部门现金未缴款金额
		List<CashCollectionRptDEntity> resultList = cashCollectionRptDEntityDao.
				queryDeptUncollectedAmount(entity.getOrgCode(),
						SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		//判空
		if (CollectionUtils.isEmpty(resultList)) {
			resultDto=new BillCashRecPayInResultDto();
			resultDto.setTotalClerksAmount(BigDecimal.ZERO);
			resultDto.setTotalPrecollectedAmount(BigDecimal.ZERO);
		}else{
			//预收总金额
			BigDecimal preAmount = BigDecimal.ZERO;
			//营业款总金额
			BigDecimal clerksAmount = BigDecimal.ZERO;
			//循环计算
			for(CashCollectionRptDEntity d:resultList){
				//如果为预收类型，则加到预收总金额里，反之都算营业款
			
				if(SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__DEPOSIT_RECEIVED.equals(d.getSourceBillType())){
					preAmount = preAmount.add(d.getOverdueAmount());
				} else {
					//还款，现金收款单金额总和
					clerksAmount = clerksAmount.add(d.getOverdueAmount());
				}
				
			}
			//设置营业款
			resultDto.setTotalClerksAmount(clerksAmount);
			//设置预收款
			resultDto.setTotalPrecollectedAmount(preAmount);
			LOGGER.info("部门编码" + "营业款："+clerksAmount+"预收款："+ preAmount+ "info");
		}
		LOGGER.info("success exit....");
		return resultDto;
	}
	
	
	/**
	 * 更新缴款明细
	 * MODIFY DATE 2013-09-02 092036-FOSS-BOCHENLONG
	 * CHECK
	 * @author 092036-foss-bochenlong
	 * @date 2013-9-4 下午3:03:11 
	 * @param cashDList
	 * @param paytotalAmount
	 * @return
	 */
	private Map<String ,Object> updateWriteOffCashincomerpt(List<CashCollectionRptDEntity> cashDList,
			BigDecimal paytotalAmount ,Integer n ,int verifyType) {
		// 封装参数MAP
		HashMap<String ,Object> map = new HashMap<String ,Object>();
		// 每1000处理的LIST
		List<CashCollectionRptDEntity> perList = new ArrayList<CashCollectionRptDEntity>();
		// 蓝单全部核销LIST
		List<CashCollectionRptDEntity> blueAllList = new ArrayList<CashCollectionRptDEntity>();
		// 蓝单部分核销LIST
		List<CashCollectionRptDEntity> bluePartList = new ArrayList<CashCollectionRptDEntity>();
		// 更新条数
		int num = 0;
		
		// DTO 
		BillCashRecPayInDDto billCashRecDDto = new BillCashRecPayInDDto();
		billCashRecDDto.setModifyTime(new Date());
		// 设置核销类型 如果为1 则为核销 -1则为反核销 此核销状态由金额决定 上面饮用方法传递而来
		billCashRecDDto.setVerifyType(verifyType);
		
		// 如果为0,为红单
		// 处理红单，与核销类型无关
		if(n.equals(Integer.valueOf(0))) {
			int i = 0;
			
			for(i=0 ;i<(cashDList.size()/SettlementConstants.MAX_LIST_SIZE) ;i++) {
				perList = cashDList.subList(i*SettlementConstants.MAX_LIST_SIZE, i*SettlementConstants.MAX_LIST_SIZE+SettlementConstants.MAX_LIST_SIZE);
				// 核销红单
				updateCashRptDEntity(perList ,billCashRecDDto ,SettlementConstants.MAX_LIST_SIZE);
			}
			
			perList = cashDList.subList(i*SettlementConstants.MAX_LIST_SIZE, cashDList.size());
			
			if(CollectionUtils.isNotEmpty(perList)) {
				// 核销红单
				updateCashRptDEntity(perList ,billCashRecDDto ,perList.size());
			}
			
		}
		
		// 如果为1，为蓝单
		// 处理蓝单，增加判断如果为核销则判断未交款金额，如果为反核销判断交款金额
		if(n.equals(new Integer(1))) {
			// 核销
			if(verifyType == 1) {
				// 筛选全部核销和部分核销分别一次性提交
				for (CashCollectionRptDEntity cashDEntity : cashDList) {
					// 如果总金额大于明细未缴款金额，明细全部缴款，总金额变化
					if (paytotalAmount.compareTo(cashDEntity.getOverdueAmount()) > 0) {
						// 总金额减少
						paytotalAmount = paytotalAmount.subtract(cashDEntity.getOverdueAmount());// 总金额=参数中传入的总金额-原未缴款金额
						// 全部核销
						blueAllList.add(cashDEntity);
						// 跳出本次循环
						continue;
					} else {	
						// 部分核销
						bluePartList.add(cashDEntity);
						// 结束循环
						break;
					}
				}
			}
			// 反核销
			if(verifyType == -1) {
				// 筛选全部核销和部分核销分别一次性提交
				for (CashCollectionRptDEntity cashDEntity : cashDList) {
					// 如果总金额大于明细未缴款金额，明细全部缴款，总金额变化
					if (paytotalAmount.compareTo(cashDEntity.getPaidAmount()) > 0) {
						// 总金额减少
						paytotalAmount = paytotalAmount.subtract(cashDEntity.getPaidAmount());// 总金额=参数中传入的总金额-原未缴款金额
						// 全部核销
						blueAllList.add(cashDEntity);
						// 跳出本次循环
						continue;
					} else {	
						// 部分核销
						bluePartList.add(cashDEntity);
						// 结束循环
						break;
					}
				}
			}
			
			
			
			// 分组完毕处理
			int i = 0;
			
			// 全部核销部分
			for(i=0 ;i<(blueAllList.size()/SettlementConstants.MAX_LIST_SIZE) ;i++) {
				perList = blueAllList.subList(i*SettlementConstants.MAX_LIST_SIZE, i*SettlementConstants.MAX_LIST_SIZE+SettlementConstants.MAX_LIST_SIZE);
				// 核销蓝单
				num = num + updateCashRptDEntity(perList ,billCashRecDDto ,SettlementConstants.MAX_LIST_SIZE);
			}
			
			perList = blueAllList.subList(i*SettlementConstants.MAX_LIST_SIZE, blueAllList.size());
			
			if(CollectionUtils.isNotEmpty(perList)) {
				// 核销蓝单
				num = num + updateCashRptDEntity(perList ,billCashRecDDto ,perList.size());
			}
			
			// 部分核销部分
			if(CollectionUtils.isNotEmpty(bluePartList)) {
				// 核销
				if(verifyType == 1) {
					billCashRecDDto.setOverdueAmount(bluePartList.get(0).getOverdueAmount().subtract(paytotalAmount));
					billCashRecDDto.setPaidAmount(NumberUtils.add(bluePartList.get(0).getPaidAmount(), paytotalAmount));
				}
				if(verifyType == -1) {
					billCashRecDDto.setPaidAmount(bluePartList.get(0).getPaidAmount().subtract(paytotalAmount));
					billCashRecDDto.setOverdueAmount(NumberUtils.add(bluePartList.get(0).getOverdueAmount(), paytotalAmount));
				}
				// 反核销
				num = num + updateCashRptDEntity(bluePartList ,billCashRecDDto ,1);
				// 总金额为0 全部核销完毕
				paytotalAmount = BigDecimal.ZERO;
			}
			
		}
			
		map.put("num", num);
		map.put("paytotalAmount", paytotalAmount);
		return map;
		
	}
	
	private int updateCashRptDEntity(List<CashCollectionRptDEntity>  perList,BillCashRecPayInDDto billCashRecDDto ,int n) {
		int num = cashCollectionRptDEntityDao.updateCashincomerptD(perList ,billCashRecDDto);
		if(num != n) {
			throw new SettlementException("数据已经发生变化，不能进行核销操作！");
		}
		return num;
		
	}



	/**
	 * 公共集合排序方法
	 * MODIFY DATE 2013-09-02 092036-FOSS-BOCHENLONG
	 * HAS CHECK
	 * @author 095793-foss-LiQin
	 * @date 2013-5-22 下午3:58:35
	 * @param list
	 * @return
	 */
//	private List<CashCollectionRptDEntity> sortCashCollection(List<CashCollectionRptDEntity> list){
//		ListComparator orderComparator = new ListComparator("businessDate");
//		//按业务日期排序		
//		if (CollectionUtils.isNotEmpty(list)) {
//			Collections.sort(list, orderComparator);
//		}
//		return list;
//	}
	
	
	
	/**
	 * 流水号是否存在
	 * MODIFY DATE 2013-09-02 092036-FOSS-BOCHENLONG
	 * CHECK
	 * @author 095793-foss-LiQin
	 * @date 2013-5-23 下午6:23:19
	 * @param SerialNo
	 * @return
	 */
	public boolean isExistSerino(String serilNO){
		CashCollectionIncomeEntity entity=new CashCollectionIncomeEntity();
		entity.setSerialno(serilNO);
		List<CashCollectionIncomeEntity> list= cashCollectionIncomeDao.queryCashCollectionIncome(entity);
		if(list.size()>0){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 新增缴款记录
	 * MODIFY DATE 2013-09-02 092036-FOSS-BOCHENLONG
	 * CHECK
	 * @author 095793-foss-LiQin
	 * @date 2013-5-23 下午6:26:42
	 */
	private void addCashIncome(CashCollectionIncomeEntity entity){
		
		cashCollectionIncomeDao.addCashCollectionIncome(entity);
	}
	
	
	/**
	 * @return cashCollectionRptEntityDao
	 */
	public ICashCollectionRptEntityDao getCashCollectionRptEntityDao() {
		return cashCollectionRptEntityDao;
	}

	/**
	 * @param cashCollectionRptEntityDao
	 */
	public void setCashCollectionRptEntityDao(
			ICashCollectionRptEntityDao cashCollectionRptEntityDao) {
		this.cashCollectionRptEntityDao = cashCollectionRptEntityDao;
	}

	/**
	 * @return cashCollectionRptDEntityDao
	 */
	public ICashCollectionRptDEntityDao getCashCollectionRptDEntityDao() {
		return cashCollectionRptDEntityDao;
	}

	/**
	 * @param cashCollectionRptDEntityDao
	 */
	public void setCashCollectionRptDEntityDao(
			ICashCollectionRptDEntityDao cashCollectionRptDEntityDao) {
		this.cashCollectionRptDEntityDao = cashCollectionRptDEntityDao;
	}

	/**
	 * @param orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * @return cashCollectionIncomeDao
	 */
	public ICashCollectionIncomeDao getCashCollectionIncomeDao() {
		return cashCollectionIncomeDao;
	}

	/**
	 * @param cashCollectionIncomeDao
	 */
	public void setCashCollectionIncomeDao(
			ICashCollectionIncomeDao cashCollectionIncomeDao) {
		this.cashCollectionIncomeDao = cashCollectionIncomeDao;
	}

	/**
	 * @return businessLockService
	 */
	public IBusinessLockService getBusinessLockService() {
		return businessLockService;
	}

	/**
	 * @param businessLockService
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	
}