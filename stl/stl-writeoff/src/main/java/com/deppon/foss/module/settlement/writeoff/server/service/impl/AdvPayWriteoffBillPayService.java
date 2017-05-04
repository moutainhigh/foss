/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillAdvancedPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountUpdateDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IAdvPayWriteoffBillPayQueryDao;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IBillPayWriteoffAdvPayQueryDao;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IAdvPayWriteoffBillPayService;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.AdvPayWriteoffBillPayDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 空运预付冲应付service
 * @author foss-pengzhen
 * @date 2012-10-22 下午1:39:07
 * @since
 * @version
 */
public class AdvPayWriteoffBillPayService implements IAdvPayWriteoffBillPayService {

	/**
	 * 注入预付单查询 dao
	 */
	private IAdvPayWriteoffBillPayQueryDao advPayWriteoffBillPayQueryDao;
	
	/**
	 * 注入应付查询dao
	 */
	private IBillPayWriteoffAdvPayQueryDao billPayWriteoffAdvPayQueryDao;

	/**
	 * 注入预付冲应付 核销service
	 */
	private IBillWriteoffService billWriteoffService;

	/**
	 * 注入获取批次号公共接口
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 注入付款公共接口
	 */
	private IBillPaymentService billPaymentService;

	/**
	 * 注入预付公共接口
	 */
	private IBillAdvancedPaymentService billAdvancedPaymentService;

	/**
	 * 注入应付公共接口
	 */
	private IBillPayableService billPayableService;

	/**
	 * 对账单公共service
	 */
	private IStatementOfAccountService statementOfAccountService;
	
	/**
	 * 校验更改单
	 */
	private IWaybillRfcService waybillRfcService;
	
	/**
	 * 互斥锁接口
	 */
	private IBusinessLockService businessLockService;

	/**
	 * 根据传入的一到多个预付单号，获取一到多条预付单信息
	 * 
	 * @author foss-pengzhen
	 * @date 2012-10-17 下午3:58:43
	 * @param billAdvancedPaymentDto
	 * @return
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IAdvPayWriteoffBillPayService#queryBillAdvancedPaymentNos(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillAdvancedPaymentDto)
	 */
	@Override
	public List<BillAdvancedPaymentEntity> queryBillAdvancedPaymentNos(AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto,CurrentInfo currentInfo) {
		// 设置部门编码
		advPayWriteoffBillPayDto.setPaymentOrgCode(currentInfo.getCurrentDeptCode());
		// 设置部门名称
		advPayWriteoffBillPayDto.setPaymentOrgName(currentInfo.getCurrentDeptName());
		// 预付单应付单的必须是有效
		advPayWriteoffBillPayDto.setActive(FossConstants.YES);
		// 预付单应付单的必须是非红单；
		advPayWriteoffBillPayDto.setIsRedBack(FossConstants.NO);
		// 预付单的审批状态必须为汇款成功。审批状态为审批中、退回的，复选框为灰色不可选；
		advPayWriteoffBillPayDto.setAuditStatus(SettlementDictionaryConstants.BILL_ADVANCED_PAYMENT__AUDIT_STATUS__AUDIT_AGREE);
		//预付单的单据子类型必须是空运
		advPayWriteoffBillPayDto.setBillType(SettlementDictionaryConstants.BILL_ADVANCED_PAYMENT__BILL_TYPE__AIR);
		//根据传入的一到多个预付单号，获取一到多条预付单信息
		return advPayWriteoffBillPayQueryDao.queryBillAdvancedPaymentNos(advPayWriteoffBillPayDto);
	}

	/**
	 * 根据传入获取一到多条预付单信息
	 * 
	 * @author foss-pengzhen
	 * @date 2012-10-17 下午4:00:02
	 * @param billAdvancedPaymentDto
	 * @return
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IAdvPayWriteoffBillPayService#queryBillAdvancedPaymentParams(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillAdvancedPaymentDto)
	 */
	@Override
	public List<BillAdvancedPaymentEntity> queryBillAdvancedPaymentParams(AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto,CurrentInfo currentInfo) {
		//业务结束日期加1天
		Date dateTemp = DateUtils.addDayToDate(advPayWriteoffBillPayDto.getEndBusinessDate(), 1);
		//设置查询dto业务结束日期参数
		advPayWriteoffBillPayDto.setEndBusinessDate(dateTemp);
		// 设置部门编码
		advPayWriteoffBillPayDto.setPaymentOrgCode(currentInfo.getCurrentDeptCode());
		// 设置部门名称
		advPayWriteoffBillPayDto.setPaymentOrgName(currentInfo.getCurrentDeptName());
		// 预付单应付单的必须是有效；
		advPayWriteoffBillPayDto.setActive(FossConstants.YES);
		// 预付单应付单的必须是非红单；
		advPayWriteoffBillPayDto.setIsRedBack(FossConstants.NO);
		// 预付单的审批状态必须为已审批。审批状态为审批中、退回的，复选框为灰色不可选；
		advPayWriteoffBillPayDto.setAuditStatus(SettlementDictionaryConstants.BILL_ADVANCED_PAYMENT__AUDIT_STATUS__AUDIT_AGREE);
		//预付单的单据子类型必须是空运
		advPayWriteoffBillPayDto.setBillType(SettlementDictionaryConstants.BILL_ADVANCED_PAYMENT__BILL_TYPE__AIR);
		//根据传入获取一到多条预付单信息
		return advPayWriteoffBillPayQueryDao.queryBillAdvancedPaymentParams(advPayWriteoffBillPayDto);
	}

	/**
	 * 导出预付单
	 * @author foss-pengzhen
	 * @date 2012-12-11 下午4:16:38
	 * @param advPayWriteoffBillPayDto
	 * @param currentInfo
	 * @see
	 */
	public HSSFWorkbook advancedListExport(AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto,CurrentInfo currentInfo){
		//excel工作区
		HSSFWorkbook work = null;
		//如果预付单查询dto不为空
		if(advPayWriteoffBillPayDto != null){
			//生成预付单实体列表
			List<BillAdvancedPaymentEntity> billAdvancedPaymentEntities = null;
			//按时间查询
			if(StringUtils.equals(advPayWriteoffBillPayDto.getQueryByTab(),SettlementConstants.TAB_QUERY_BY_DATE)){
				// 业务结束时间+1天
				Date dateTemp = DateUtils.addDayToDate(advPayWriteoffBillPayDto.getEndBusinessDate(), 1);
				//设置查询dto业务结算时间
				advPayWriteoffBillPayDto.setEndBusinessDate(dateTemp);
				//查询预付单
				billAdvancedPaymentEntities = this.queryBillAdvancedPaymentParams(advPayWriteoffBillPayDto, currentInfo);
			}
			//按预收单号查询
			if(StringUtils.equals(advPayWriteoffBillPayDto.getQueryByTab(),SettlementConstants.TAB_QUERY_BY_DEPOIST_RECEIVED_NO)){
				//获取预付单号并分割
				String[] advancesNo = advPayWriteoffBillPayDto.getAdvancesNo().split(",");
				//将预付单号转化为list
				List<String> advancesNos = Arrays.asList(advancesNo);
				//设置查询dto预付单号参数
				advPayWriteoffBillPayDto.setAdvancesNos(advancesNos);
				//查询预付单
				billAdvancedPaymentEntities = this.queryBillAdvancedPaymentNos(advPayWriteoffBillPayDto, currentInfo);
			}
			// 查询到的结果集不为空时，执行导出操作
			if (CollectionUtils.isNotEmpty(billAdvancedPaymentEntities)) {
				//导出结果不能超过限制条数
				if(billAdvancedPaymentEntities.size()>SettlementConstants.EXPORT_EXCEL_MAX_COUNTS){
					//提示每次最多只能导出XXX条数据,请重新查询并导出
					throw new SettlementException("每次最多只能导出"+SettlementConstants.EXPORT_EXCEL_MAX_COUNTS +"条数据,请重新查询并导出");
				}
				//生成excel导出实体
				ExcelExport export = new ExcelExport();
				//设置导出list
				advPayWriteoffBillPayDto.setBillAdvancedPaymentEntityList(billAdvancedPaymentEntities);
				// 设置每次最多导出10万条数据
				work = export.exportExcel(fillSheetDataByAdvanced(advPayWriteoffBillPayDto), "sheet", SettlementConstants.EXPORT_MAX_COUNT);
			} else {
				//提示导出数据为空
				throw new SettlementException("导出数据为空！");
			}
		}
		return work;
	}
	
	/**
	 * 导出应付单
	 * @author foss-pengzhen
	 * @date 2012-12-11 下午4:16:38
	 * @param advPayWriteoffBillPayDto
	 * @param currentInfo
	 * @see
	 */
	public HSSFWorkbook payableListExport(AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto,CurrentInfo currentInfo){
		//excel工作区
		HSSFWorkbook work = null;
		//如果应付单查询dto不为空
		if(advPayWriteoffBillPayDto != null){
			//生成应付单实体列表
			List<BillPayableEntity> billPayableEntities = null;
			//按时间查询
			if(StringUtils.equals(advPayWriteoffBillPayDto.getQueryByTab(),SettlementConstants.TAB_QUERY_BY_DATE)){
				// 业务结束时间+1天
				Date dateTemp = DateUtils.addDayToDate(advPayWriteoffBillPayDto.getEndBusinessDate(), 1);
				//设置查询dto业务结算时间
				advPayWriteoffBillPayDto.setEndBusinessDate(dateTemp);
				//查询应付单
				billPayableEntities = this.queryPayableParams(advPayWriteoffBillPayDto, currentInfo);
			}
			//按预收单号查询
			if(StringUtils.equals(advPayWriteoffBillPayDto.getQueryByTab(),SettlementConstants.TAB_QUERY_BY_DEPOIST_RECEIVED_NO)){
				//获取应付单号并分割
				String[] payableNo = advPayWriteoffBillPayDto.getPayableNo().split(",");
				//将应付单号转化为list
				List<String> payableNos = Arrays.asList(payableNo);
				//设置查询dto应付单号参数
				advPayWriteoffBillPayDto.setAdvancesNos(payableNos);
				//查询应付单
				billPayableEntities = this.queryPayableNOs(advPayWriteoffBillPayDto, currentInfo);
			}
			// 查询到的结果集不为空时，执行导出操作
			if (CollectionUtils.isNotEmpty(billPayableEntities)) {
				//导出结果不能超过限制条数
				if(billPayableEntities.size()>SettlementConstants.EXPORT_EXCEL_MAX_COUNTS){
					//提示每次最多只能导出XXX条数据,请重新查询并导出
					throw new SettlementException("每次最多只能导出"+SettlementConstants.EXPORT_EXCEL_MAX_COUNTS +"条数据,请重新查询并导出");
				}
				//生成excel导出实体
				ExcelExport export = new ExcelExport();
				//设置导出list
				advPayWriteoffBillPayDto.setBillPayableEntityList(billPayableEntities);
				// 设置每次最多导出10万条数据
				work = export.exportExcel(fillSheetDataByPayable(advPayWriteoffBillPayDto), "sheet", SettlementConstants.EXPORT_MAX_COUNT);
			} else {
				//提示导出数据为空
				throw new SettlementException("导出数据为空！");
			}
		}
		return work;
	}
	
	/**
	 * 生成预付单的excel
	 * @author foss-pengzhen
	 * @date 2012-12-6 上午11:13:38
	 */
	private SheetData fillSheetDataByAdvanced(AdvPayWriteoffBillPayDto  dto) {
		SheetData sheetData = new SheetData();
		sheetData.setRowHeads(new String[] { "预付单号", "部门名称", "部门编码", "客户名称",
				"客户编码", "审核状态", "金额", "已核销金额", "未核销金额", "业务日期", "制单时间", "会计日期",
				"制单人", "制单人编号" });
		//,"核销状态", "核销单号", "核销单明细","核销单生成方式" 
		List<BillAdvancedPaymentEntity> lists = dto.getBillAdvancedPaymentEntityList(); 
		//如果预付单不为空
		if(CollectionUtils.isNotEmpty(lists)){
			//生成导出行数据列表
			List<List<String>> rowList = new ArrayList<List<String>>();
			//声明字典集合
			List<String> termCodes = new ArrayList<String>();
			//添加单据类型数据字典到集合中
			termCodes.add(DictionaryConstants.BILL_ADVANCED_PAYMENT__AUDIT_STATUS);
			//后台获取数据字典对应的数据
			Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(termCodes);
			//循环处理
			for (BillAdvancedPaymentEntity entity : lists) {
				//生成单元格数据列表
				List<String> cellList = new ArrayList<String>();
				cellList.add(entity.getAdvancesNo());// 预付单号
				cellList.add(entity.getPaymentOrgName());//部门名称
				cellList.add(entity.getPaymentOrgCode());//部门编码
				cellList.add(entity.getCustomerName());//客户名称
				cellList.add(entity.getCustomerCode());//客户编码
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map,DictionaryConstants.BILL_ADVANCED_PAYMENT__AUDIT_STATUS, entity.getAuditStatus())); //审核状态
				cellList.add(entity.getAmount() != null ? entity.getAmount() + "" : "");//金额
				cellList.add(entity.getAmount() != null ? entity.getVerifyAmount() + "" : "");//已核销金额
				cellList.add(entity.getAmount() != null ? entity.getUnverifyAmount() + "" : "");//未核销金额
				cellList.add(DateUtils.convert(entity.getBusinessDate(),DateUtils.DATE_TIME_FORMAT)); //业务日期
				cellList.add(DateUtils.convert(entity.getCreateTime(),DateUtils.DATE_TIME_FORMAT));//制单之间
				cellList.add(DateUtils.convert(entity.getAccountDate(),DateUtils.DATE_TIME_FORMAT));//记账日期
				cellList.add(entity.getCreateUserName());//制单人
				cellList.add(entity.getCreateUserCode());//制单人编号
				
				//加入行数据list
				rowList.add(cellList);
			}
			//设置excel表单数据
			sheetData.setRowList(rowList);
		}
		//返回数据
		return sheetData;
	}	
	/**
	 * 生成应付单的excel
	 * @author foss-pengzhen
	 * @date 2012-12-6 上午11:13:38
	 */
	private SheetData fillSheetDataByPayable(AdvPayWriteoffBillPayDto  dto) {
		SheetData sheetData = new SheetData();
		sheetData.setRowHeads(new String[] { "应付单号", "部门名称", "部门编码", "客户名称",
				"客户编码", "审核状态", "金额", "已核销金额", "未核销金额", "业务日期", "制单时间", "会计日期",
				"制单人", "制单人编号" });
		//,"核销状态", "核销单号", "核销单明细","核销单生成方式" 
		List<BillPayableEntity> lists = dto.getBillPayableEntityList();
		//声明字典集合
		List<String> termCodes = new ArrayList<String>();
		//添加单据类型数据字典到集合中
		termCodes.add(DictionaryConstants.BILL_PAYABLE__APPROVE_STATUS);
		//后台获取数据字典对应的数据
		Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(termCodes);
		
		//如果应付单不为空
		if(CollectionUtils.isNotEmpty(lists)){
			//生成导出行数据列表
			List<List<String>> rowList = new ArrayList<List<String>>();
			//循环处理
			for (BillPayableEntity entity : lists) {
				//生成导出单元格数据列表
				List<String> cellList = new ArrayList<String>();
				cellList.add(entity.getPayableNo());// 预付单号
				cellList.add(entity.getPayableOrgName());//部门名称
				cellList.add(entity.getPayableOrgCode());//部门编码
				cellList.add(entity.getCustomerName());//客户名称
				cellList.add(entity.getCustomerCode());//客户编码
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map,DictionaryConstants.BILL_PAYABLE__APPROVE_STATUS, entity.getApproveStatus())); //审核状态
				cellList.add(entity.getAmount() != null ? entity.getAmount() + "" : "");//金额
				cellList.add(entity.getAmount() != null ? entity.getVerifyAmount() + "" : "");//已核销金额
				cellList.add(entity.getAmount() != null ? entity.getUnverifyAmount() + "" : "");//未核销金额
				cellList.add(DateUtils.convert(entity.getBusinessDate(),DateUtils.DATE_TIME_FORMAT)); //业务日期
				cellList.add(DateUtils.convert(entity.getCreateTime(),DateUtils.DATE_TIME_FORMAT));//制单时间
				cellList.add(DateUtils.convert(entity.getAccountDate(),DateUtils.DATE_TIME_FORMAT));//记账日期
				cellList.add(entity.getCreateUserName());//制单人
				cellList.add(entity.getCreateUserCode());//制单人编号
				//加入导出行数据列表
				rowList.add(cellList);
			}
			//加入导出excel表单数据
			sheetData.setRowList(rowList);
		}
		//返回数据
		return sheetData;
	}	
	
	/**
	 * 过滤存在更改单的应付单
	 */
	public List<BillPayableEntity>  filterPayable(List<BillPayableEntity> billPayableEntities){
		//校验更改单，如有应付单存在对应的更改单，不能直接核销
		if(CollectionUtils.isNotEmpty(billPayableEntities)){
			// 应付单和预付单对应对账单状态是否为客户已确认，客户已确认情况下不允许核销
			// 1、调用对账单接口过滤应付单，根据单号过滤掉已经生成对账单的应付单
			// 1.1 、获取对账单号列表
			List<String> statementBillNos = null;
			//生成待处理应付单列表
			List<BillPayableEntity> toDelList = new ArrayList<BillPayableEntity>();
			//循环处理
			//生成对账单号列表
			statementBillNos = new ArrayList<String>();
			//循环处理
			for (BillPayableEntity entity : billPayableEntities) {
				//如果对账单相等
				if (entity.getStatementBillNo() != null&& !SettlementConstants.DEFAULT_BILL_NO.equals(entity.getStatementBillNo())) {
					//加入到对账单号列表
					statementBillNos.add(entity.getStatementBillNo());
				}
			}
			// 1.2、调用对账单验证接口，返回已经做过确认对账单的单号
			//如果对账单号列表不为空
			if (CollectionUtils.isNotEmpty(statementBillNos)) {
				//检测已经确认对账单的单号
				statementBillNos = statementOfAccountService.queryConfirmStatmentOfAccount(statementBillNos);
			}

			// 1.3、从应付单列表中除去已经确认对账单的数据
			//如果已确认对账单单号列表不为空
			if (CollectionUtils.isNotEmpty(statementBillNos)) {
				//两级循环处理
				for (String statementBillNo : statementBillNos) { 
					for (BillPayableEntity entity : billPayableEntities) {
						//如果对账单号相等
						if (statementBillNo.equals(entity.getStatementBillNo())) {
							//将应付单加入到待处理应付单列表
							toDelList.add(entity);
						}
					}
				}
			}
			//如果待处理应付单列表不为空
			if(CollectionUtils.isNotEmpty(toDelList)){
				//从load全部数据中出去已确认对账单的应付单数据
				billPayableEntities.removeAll(toDelList);
				//清空待处理应付单列表
				toDelList.clear();
			}
			//生成运单号list
    		List<String> waybillNos = new ArrayList<String>();
    		//循环处理
    		for(BillPayableEntity billPayableEntity : billPayableEntities){
    			//取运单集合加入运单号list
    			waybillNos.add(billPayableEntity.getWaybillNo());
    		}
    		//取到存在更改单的运单号集合
    		validaBillPayableEntity(billPayableEntities, waybillNos);
		}
		//返回应付单实体列表
		return billPayableEntities;
	}

	private void validaBillPayableEntity(
			List<BillPayableEntity> billPayableEntities, List<String> waybillNos) {
		if(CollectionUtils.isNotEmpty(waybillNos)){
			//获取存在未处理更改单号的运单号
			List<String> waybillNosRfc = waybillRfcService.isExsitsWayBillRfcs(waybillNos);
			//,不为空则调用接口进行过滤
			if(CollectionUtils.isNotEmpty(waybillNosRfc)){
				//循环存在未处理更改单号的运单号
				for(String waybillNo : waybillNosRfc){
					//循环应付列表
					for(BillPayableEntity billPayableEntity : billPayableEntities){
						//如果两者运单号相等
						if(StringUtils.equals(waybillNo,billPayableEntity.getWaybillNo())){
							//去掉存在更改单的应付单据
							billPayableEntities.remove(billPayableEntity);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 根据传入的一到多个应付单号，获取一到多条应付单信息
	 * 
	 * @author foss-pengzhen
	 * @date 2012-10-17 下午4:02:06
	 * @param billPayableDto
	 * @return
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IAdvPayWriteoffBillPayService#queryPayableNOs(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillPayableDto)
	 */
	@Override
	public List<BillPayableEntity> queryPayableNOs(AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto,CurrentInfo currentInfo) {
		// 设置部门编码
		// 只能查询当前登录用户所属部门的单据信息
		advPayWriteoffBillPayDto.setPayableOrgCode(currentInfo.getCurrentDeptCode());
		// 设置部门名称
		advPayWriteoffBillPayDto.setPayableOrgName(currentInfo.getCurrentDeptName());
		// 预付单应付单的必须是有效；
		advPayWriteoffBillPayDto.setActive(FossConstants.YES);
		// 预付单应付单的必须是非红单；
		advPayWriteoffBillPayDto.setIsRedBack(FossConstants.NO);
		// 能够核销的应付单的单据子类型必须为航空公司；
		List<String> billTypes = new ArrayList<String>();
		// 能够核销的应付单的单据子类型必须为空运其他应付
		billTypes.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_OTHER);
		// 能够核销的应付单的单据子类型必须为航空公司运费
		billTypes.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR);
		//设置查询dto的单据类型参数
		advPayWriteoffBillPayDto.setBillTypes(billTypes);
		//设置付款单号为默认单号
		advPayWriteoffBillPayDto.setPaymentNo(SettlementConstants.DEFAULT_BILL_NO);
		//设置汇款状态为已汇款
		advPayWriteoffBillPayDto.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRED);
		// 审核状态为已审核
		advPayWriteoffBillPayDto.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);
 
		//查询应付单
		List<BillPayableEntity> billPayableEntities = billPayWriteoffAdvPayQueryDao.queryPayableNOs(advPayWriteoffBillPayDto);
		//调用过滤更改单的方法，
		this.filterPayable(billPayableEntities);
		//返回应付单信息
		return billPayableEntities;
	}

	/**
	 * 根据传入获取一到多条应付单信息
	 * 
	 * @author foss-pengzhen
	 * @date 2012-10-17 下午4:01:48
	 * @param billPayableDto
	 * @return
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IAdvPayWriteoffBillPayService#queryPayableParams(com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillPayableDto)
	 */
	@Override
	public List<BillPayableEntity> queryPayableParams(AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto,CurrentInfo currentInfo) {

		//业务结束日期+1天
		Date dateTemp = DateUtils.addDayToDate(advPayWriteoffBillPayDto.getEndBusinessDate(), 1);
		//设置查询dto的业务结束日期参数
		advPayWriteoffBillPayDto.setEndBusinessDate(dateTemp);
		// 只能查询当前登录用户所属部门的单据信息
		// 设置部门编码
		advPayWriteoffBillPayDto.setPayableOrgCode(currentInfo.getCurrentDeptCode());
		// 设置部门名称
		advPayWriteoffBillPayDto.setPayableOrgName(currentInfo.getCurrentDeptName());
		// 预付单应付单的必须是有效地；
		advPayWriteoffBillPayDto.setActive(FossConstants.YES);
		// 预付单应付单的必须是非红单；
		advPayWriteoffBillPayDto.setIsRedBack(FossConstants.NO);
		// 能够核销的应付单的单据子类型必须为航空公司；
		List<String> billTypes = new ArrayList<String>();
		// 能够核销的应付单的单据子类型必须为空运其他应付
		billTypes.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_OTHER);
		// 能够核销的应付单的单据子类型必须为航空公司运费
		billTypes.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR);
		//设置查询dto的单据类型参数
		advPayWriteoffBillPayDto.setBillTypes(billTypes);
		//设置付款单号为默认单号
		advPayWriteoffBillPayDto.setPaymentNo(SettlementConstants.DEFAULT_BILL_NO);
		//设置汇款状态为已汇款
		advPayWriteoffBillPayDto.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRED);
		// 审核状态为已审核
		advPayWriteoffBillPayDto.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);
		//查询应付单
		List<BillPayableEntity> billPayableEntities = billPayWriteoffAdvPayQueryDao.queryPayableParams(advPayWriteoffBillPayDto);
		//调用过滤更改单的方法，
		this.filterPayable(billPayableEntities);
		//返回应付单
		return billPayableEntities;
	}

	/**
	 * 预付冲应付
	 * 
	 * @author foss-pengzhen
	 * @date 2012-10-19 下午5:41:25
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IAdvPayWriteoffBillPayService#writeoffAdvancedAndPayable()
	 */
	public AdvPayWriteoffBillPayDto writeoffAdvancedAndPayable(AdvPayWriteoffBillPayDto advPayWriteoffBillPayDto,CurrentInfo currentInfo) {
		//查询dto不能为空
		if(advPayWriteoffBillPayDto == null){
			//提示内部异常，传入参数为空，不能进行核销操作
			throw new SettlementException("内部异常，传入参数为空，不能进行核销操作！");
		}
		// 取界面预付单grid中的数据
		List<BillAdvancedPaymentEntity> billAdvancedPaymentEntityListFrom = advPayWriteoffBillPayDto.getBillAdvancedPaymentEntityList();
		//选择的预付单不能为空
		if(CollectionUtils.isEmpty(billAdvancedPaymentEntityListFrom)){
			//内部异常，传入参数为空，不能进行核销操作
			throw new SettlementException("内部异常，传入参数为空，不能进行核销操作！");
		}
		
		// 取界面应付单grid中的数据
		List<BillPayableEntity> billPayableEntityListFrom = advPayWriteoffBillPayDto.getBillPayableEntityList();
		//选择的预付单不能为空
		if(CollectionUtils.isEmpty(billPayableEntityListFrom)){
			//内部异常，传入参数为空，不能进行核销操作
			throw new SettlementException("内部异常，传入参数为空，不能进行核销操作！");
		}
		
		// 循环拿grid中的预付单号
		List<String> advancesNoList = new ArrayList<String>();
		//循环处理
		for (BillAdvancedPaymentEntity entity : billAdvancedPaymentEntityListFrom) {
			//放到单号集中
			advancesNoList.add(entity.getAdvancesNo());
		}
		// load选择核销的数据
		List<BillAdvancedPaymentEntity> billAdvancedPaymentEntityListAll = billAdvancedPaymentService.queryBillAdvancedPaymentNos(advancesNoList,FossConstants.ACTIVE);

		//load选择核销的数据不能为空
		if(CollectionUtils.isEmpty(billAdvancedPaymentEntityListAll)){
			//库中数据已修改，不能进行核销操作
			throw new SettlementException("库中数据已修改，不能进行核销操作！");
		}
		
		// 循环拿grid中的应付单号
		List<String> payableNoList = new ArrayList<String>();
		//循环处理
		for (BillPayableEntity entity : billPayableEntityListFrom) {
			//放到单号集中
			payableNoList.add(entity.getPayableNo());
		}
		// load选择核销的数据
		List<BillPayableEntity> billPayableEntityListAll = billPayableService.queryByPayableNOs(payableNoList, FossConstants.ACTIVE);
		//load选择核销的数据不能为空
		if(CollectionUtils.isEmpty(billPayableEntityListAll)){
			//库中数据已修改，不能进行核销操作
			throw new SettlementException("库中数据已修改，不能进行核销操作！");
		}
		
		
		// 获取客户编码
		String costomerCode = billAdvancedPaymentEntityListFrom.get(0).getCustomerCode();
		//客户编码不能为空
		if (StringUtils.isEmpty(costomerCode)) {
			//提示只有同一客户才能进行核销操作
			throw new SettlementException("只有同一客户才能进行核销操作！");
		}

		List<BillAdvancedPaymentEntity> advancedPaymentEntities = new ArrayList<BillAdvancedPaymentEntity>();
		// 判断均通过，将最新的数据赋值给待核销的实体
		for(BillAdvancedPaymentEntity billAdvancedPaymentEntity : billAdvancedPaymentEntityListAll){
			advancedPaymentEntities.add(billAdvancedPaymentEntity);
		}
		//循环处理
		for (BillAdvancedPaymentEntity entityFrom : billAdvancedPaymentEntityListFrom) {
			// 选中预付单是否在最新数据库查询结果只中
			boolean existsFlag = false;
			//循环处理
			for (BillAdvancedPaymentEntity entity : advancedPaymentEntities) {
				// 判断客户编码必须相同,只有同一客户（客户编号相同）的未完全核销预付单和未完全核销应付单可以进行核销操作。
				if (!costomerCode.equals(entity.getCustomerCode())) {
					//提示只有同一客户才能进行核销操作
					throw new SettlementException("只有同一客户才能进行核销操作！");
				}
				if(StringUtils.equals(entityFrom.getId(), entity.getId())){
    				// 选中预付单已被被别人部分核销的须重新选择核销
    				if (entityFrom.getUnverifyAmount().compareTo(entity.getUnverifyAmount()) != 0) {
    					//提示已被其他用户部分核销的预付单请重新选择并核销
    					throw new SettlementException("已被其他用户部分核销的预付单请重新选择并核销!");
    				}
    				// 判断均通过，将最新的数据赋值给待核销的实体
    				BeanUtils.copyProperties(entity, entityFrom);
    
    				// 验证完成，去除本条待核销数据数据
    				advancedPaymentEntities.remove(entity);
				}
				//设置标志为ture
				existsFlag = true;
				break;
			}
			// 如果选中预付单是否在最新数据库查询结果只中，返回界面最新结果
			if (!existsFlag) {
				//提示已被其他用户完全核销的预付单不能进行核销操作,请重新核销
				throw new SettlementException("已被其他用户完全核销的预付单不能进行核销操作,请重新核销!");
			}
		}

		List<BillPayableEntity> billPayableEntities = new ArrayList<BillPayableEntity>();
		// 判断均通过，将最新的数据赋值给待核销的实体
		for(BillPayableEntity billPayableEntity : billPayableEntityListAll){
			billPayableEntities.add(billPayableEntity);
		}
		//循环处理
		for (BillPayableEntity entityFrom : billPayableEntityListFrom) {
			// 选中应付单是否在最新数据库查询结果只中
			boolean existsFlag = false;
			//循环处理
			for (BillPayableEntity entity : billPayableEntities) {

				// 判断客户编码必须相同,只有同一客户（客户编号相同）的未完全核销预付单和未完全核销应付单可以进行核销操作。
				if (!costomerCode.equals(entity.getCustomerCode())) {
					//提示只有同一客户才能进行核销操作
					throw new SettlementException("只有同一客户才能进行核销操作！");
				}
				if(StringUtils.equals(entityFrom.getId(), entity.getId())){
    				// 选中预付单已被被别人部分核销的须重新选择核销
    				if (entityFrom.getUnverifyAmount().compareTo(entity.getUnverifyAmount()) != 0) {
    					//提示已被其他用户部分核销的预付单请重新选择并核销
    					throw new SettlementException("已被其他用户部分核销的应付单请重新选择并核销!");
    				}
    				// 判断均通过，将最新的数据赋值给待核销的实体
    				BeanUtils.copyProperties(entity, entityFrom);
    
    				// 验证完成，去除本条待核销数据数据
    				billPayableEntities.remove(entity);
				}
				//设置标志为ture
				existsFlag = true;
				break;
			}
			// 如果选中预付单是否在最新数据库查询结果只中，返回界面最新结果
			if (!existsFlag) {
				//提示已被其他用户完全核销的应付单不能进行核销操作,请重新核销
				throw new SettlementException("已被其他用户完全核销的应付单不能进行核销操作,请重新核销!");
			}
		}

		// 应付单和预付单对应对账单状态是否为客户已确认，客户已确认情况下不允许核销
		// 1、调用对账单接口过滤应付单，根据单号过滤掉已经生成对账单的应付单
		// 1.1 、获取对账单号列表
		List<String> statementBillNoList = null;
		//生成待处理应付单列表
		List<BillPayableEntity> toDelList = new ArrayList<BillPayableEntity>();
		//循环处理
		if (CollectionUtils.isNotEmpty(billPayableEntityListAll)) {
			//生成对账单号列表
			statementBillNoList = new ArrayList<String>();
			//循环处理
			for (BillPayableEntity entity : billPayableEntityListAll) {
				//如果对账单相等
				if (entity.getStatementBillNo() != null&& !SettlementConstants.DEFAULT_BILL_NO.equals(entity.getStatementBillNo())) {
					//加入到对账单号列表
					statementBillNoList.add(entity.getStatementBillNo());
				}
			}
		}

		// 1.2、调用对账单验证接口，返回已经做过确认对账单的单号
		//如果对账单号列表不为空
		if (CollectionUtils.isNotEmpty(statementBillNoList)) {
			//检测已经确认对账单的单号
			statementBillNoList = statementOfAccountService.queryConfirmStatmentOfAccount(statementBillNoList);
		}

		// 1.3、从应付单列表中除去已经确认对账单的数据
		//如果已确认对账单单号列表不为空
		if (CollectionUtils.isNotEmpty(statementBillNoList)) {
			//两级循环处理
			for (String statementBillNo : statementBillNoList) { 
				for (BillPayableEntity entity : billPayableEntityListAll) {
					//如果对账单号相等
					if (statementBillNo.equals(entity.getStatementBillNo())) {
						//将应付单加入到待处理应付单列表
						toDelList.add(entity);
					}
				}
			}
		}
		//如果待处理应付单列表不为空
		if(CollectionUtils.isNotEmpty(toDelList)){
			//从load全部数据中出去已确认对账单的应付单数据
			billPayableEntityListAll.removeAll(toDelList);
			//清空待处理应付单列表
			toDelList.clear();
		}

		//校验更改单，如有应付单存在对应的更改单，不能直接核销
		List<String> waybillNos = new ArrayList<String>();
		//循环处理
		for(BillPayableEntity billPayableEntity : billPayableEntityListAll){
			//依次获取运单
			waybillNos.add(billPayableEntity.getWaybillNo());
		}
		//生成存在更改单的运单号列表
		List<String> waybillNosReturn = null;
		//如果运单列表不为空
		if(CollectionUtils.isNotEmpty(waybillNos)){
			//检测是否存在更改单，并返回存在更改单的运单号
			waybillNosReturn = waybillRfcService.isExsitsWayBillRfcs(waybillNos);
		}
		//如果存在更改单的运单号不为空
		if(CollectionUtils.isNotEmpty(waybillNosReturn)){
			//提示应付单XXX存在对应的更改单，不能直接核销
			throw new SettlementException("应付单"+ waybillNosReturn.get(0) +"存在对应的更改单，不能直接核销");
		}
		// 获取核销批次号
		String writeoffBillBatchNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HX_BN);

		// 调用common统一的预付冲应付service
		BillWriteoffOperationDto billWriteoffOperationDto = new BillWriteoffOperationDto();//
		//设置load预付单集合
		billWriteoffOperationDto.setBillAdvancedPaymentEntitys(billAdvancedPaymentEntityListAll);
		//设置load应付单集合
		billWriteoffOperationDto.setBillPayableEntitys(billPayableEntityListAll);
		//设置核销批次号
		billWriteoffOperationDto.setWriteoffBatchNo(writeoffBillBatchNo);
		//设置核销生成方式
		billWriteoffOperationDto.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
		
		//添加互斥锁收集数据
		List<MutexElement> mutexElements = new ArrayList<MutexElement>();
		//生成互斥对象
		MutexElement mutexElement = null;
		//循环处理
		for(BillPayableEntity billPayableEntity : billPayableEntityListAll){
			//如果运单号不为空
			if(StringUtils.isNotEmpty(billPayableEntity.getWaybillNo())&& StringUtils.equals(SettlementConstants.DEFAULT_BILL_NO, billPayableEntity.getWaybillNo())){
    			//生成互斥对象
				mutexElement = new MutexElement(billPayableEntity.getWaybillNo(), "预付冲应付单核销操作", MutexElementType.WAYBILL_NO);
    			//加入批量互斥对象列表
				mutexElements.add(mutexElement);
			}
		}
		//添加互斥锁
		if(CollectionUtils.isNotEmpty(mutexElements)){
			//锁定
			businessLockService.lock(mutexElements, SettlementConstants.BUSINESS_LOCK_BATCH);
		}
		// 调用通用的预付冲应付核销接口
		billWriteoffService.writeoffAdvancedAndPayable(billWriteoffOperationDto, currentInfo);
		//去除互斥锁
		if(CollectionUtils.isNotEmpty(mutexElements)){
			//解锁
			businessLockService.unlock(mutexElements);
		}
		
		// 设置返回Vo
		//预付单返回的已核销金额和未核销金额
		BigDecimal advPayVerifyAmount = BigDecimal.ZERO;
		//循环处理
		for(BillAdvancedPaymentEntity advancedPaymentEntity: billAdvancedPaymentEntityListAll){
			//累加已核销金额
			advPayVerifyAmount = advPayVerifyAmount.add(advancedPaymentEntity.getVerifyAmount());
		}
		
		//应付单的已核销金额相对增加
		BigDecimal advVerifyAmount = advPayWriteoffBillPayDto.getAdvPayVerifyAmount().add(advPayVerifyAmount);
		//设置预付单已核销金额
		advPayWriteoffBillPayDto.setAdvPayVerifyAmount(advVerifyAmount);
		//预付单的未核销金额相对较少
		BigDecimal advUnVerifyAmount = advPayWriteoffBillPayDto.getAdvPayUnVerifyAmount().subtract(advPayVerifyAmount);
		//设置预付单未核销金额
		advPayWriteoffBillPayDto.setAdvPayUnVerifyAmount(advUnVerifyAmount);
		//预付单的已核销金额相对增加
		BigDecimal billVerifyAmount = advPayWriteoffBillPayDto.getBillPayVerifyAmount().add(advPayVerifyAmount);
		//设置应付单已核销金额
		advPayWriteoffBillPayDto.setBillPayVerifyAmount(billVerifyAmount);
		//应付单的未核销金额相对较少
		BigDecimal billUnVerifyAmount = advPayWriteoffBillPayDto.getBillPayUnVerifyAmount().subtract(advPayVerifyAmount);
		//设置应付单未核销金额
		advPayWriteoffBillPayDto.setBillPayUnVerifyAmount(billUnVerifyAmount);
		//设置预付单列表
		advPayWriteoffBillPayDto.setBillAdvancedPaymentEntityList(billAdvancedPaymentEntityListAll);
		//设置应付单列表
		advPayWriteoffBillPayDto.setBillPayableEntityList(billPayableEntityListAll);

		// 核销完成，通知修改对账单及对账单明细信息
		//生成对账单修改dto
		StatementOfAccountUpdateDto statementOfAccountUpdateDto = new StatementOfAccountUpdateDto();
		//设置对账单修改dto的预付单列表参数
		statementOfAccountUpdateDto.setAdvancePaymentEntityList(advPayWriteoffBillPayDto.getBillAdvancedPaymentEntityList());
		//设置对账单修改dto的应付单列表参数
		statementOfAccountUpdateDto.setPayableEntityList(advPayWriteoffBillPayDto.getBillPayableEntityList());
		//修改对账单及对账单明细信息
		statementOfAccountService.updateStatementAndDetailForWriteOff(statementOfAccountUpdateDto, currentInfo);

		//返回预付冲应付dto
		return advPayWriteoffBillPayDto;

	}

	
	/**
	 * @get
	 * @return advPayWriteoffBillPayQueryDao
	 */
	public IAdvPayWriteoffBillPayQueryDao getAdvPayWriteoffBillPayQueryDao() {
		/*
		 * @get
		 * @return advPayWriteoffBillPayQueryDao
		 */
		return advPayWriteoffBillPayQueryDao;
	}

	
	/**
	 * @set
	 * @param advPayWriteoffBillPayQueryDao
	 */
	public void setAdvPayWriteoffBillPayQueryDao(
			IAdvPayWriteoffBillPayQueryDao advPayWriteoffBillPayQueryDao) {
		/*
		 *@set
		 *@this.advPayWriteoffBillPayQueryDao = advPayWriteoffBillPayQueryDao
		 */
		this.advPayWriteoffBillPayQueryDao = advPayWriteoffBillPayQueryDao;
	}

	
	/**
	 * @get
	 * @return billPayWriteoffAdvPayQueryDao
	 */
	public IBillPayWriteoffAdvPayQueryDao getBillPayWriteoffAdvPayQueryDao() {
		/*
		 * @get
		 * @return billPayWriteoffAdvPayQueryDao
		 */
		return billPayWriteoffAdvPayQueryDao;
	}

	
	/**
	 * @set
	 * @param billPayWriteoffAdvPayQueryDao
	 */
	public void setBillPayWriteoffAdvPayQueryDao(
			IBillPayWriteoffAdvPayQueryDao billPayWriteoffAdvPayQueryDao) {
		/*
		 *@set
		 *@this.billPayWriteoffAdvPayQueryDao = billPayWriteoffAdvPayQueryDao
		 */
		this.billPayWriteoffAdvPayQueryDao = billPayWriteoffAdvPayQueryDao;
	}

	
	/**
	 * @get
	 * @return billWriteoffService
	 */
	public IBillWriteoffService getBillWriteoffService() {
		/*
		 * @get
		 * @return billWriteoffService
		 */
		return billWriteoffService;
	}

	
	/**
	 * @set
	 * @param billWriteoffService
	 */
	public void setBillWriteoffService(IBillWriteoffService billWriteoffService) {
		/*
		 *@set
		 *@this.billWriteoffService = billWriteoffService
		 */
		this.billWriteoffService = billWriteoffService;
	}

	
	/**
	 * @get
	 * @return settlementCommonService
	 */
	public ISettlementCommonService getSettlementCommonService() {
		/*
		 * @get
		 * @return settlementCommonService
		 */
		return settlementCommonService;
	}

	
	/**
	 * @set
	 * @param settlementCommonService
	 */
	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		/*
		 *@set
		 *@this.settlementCommonService = settlementCommonService
		 */
		this.settlementCommonService = settlementCommonService;
	}

	
	/**
	 * @get
	 * @return billPaymentService
	 */
	public IBillPaymentService getBillPaymentService() {
		/*
		 * @get
		 * @return billPaymentService
		 */
		return billPaymentService;
	}

	
	/**
	 * @set
	 * @param billPaymentService
	 */
	public void setBillPaymentService(IBillPaymentService billPaymentService) {
		/*
		 *@set
		 *@this.billPaymentService = billPaymentService
		 */
		this.billPaymentService = billPaymentService;
	}

	
	/**
	 * @get
	 * @return billAdvancedPaymentService
	 */
	public IBillAdvancedPaymentService getBillAdvancedPaymentService() {
		/*
		 * @get
		 * @return billAdvancedPaymentService
		 */
		return billAdvancedPaymentService;
	}

	
	/**
	 * @set
	 * @param billAdvancedPaymentService
	 */
	public void setBillAdvancedPaymentService(
			IBillAdvancedPaymentService billAdvancedPaymentService) {
		/*
		 *@set
		 *@this.billAdvancedPaymentService = billAdvancedPaymentService
		 */
		this.billAdvancedPaymentService = billAdvancedPaymentService;
	}

	
	/**
	 * @get
	 * @return billPayableService
	 */
	public IBillPayableService getBillPayableService() {
		/*
		 * @get
		 * @return billPayableService
		 */
		return billPayableService;
	}

	
	/**
	 * @set
	 * @param billPayableService
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		/*
		 *@set
		 *@this.billPayableService = billPayableService
		 */
		this.billPayableService = billPayableService;
	}

	
	/**
	 * @get
	 * @return statementOfAccountService
	 */
	public IStatementOfAccountService getStatementOfAccountService() {
		/*
		 * @get
		 * @return statementOfAccountService
		 */
		return statementOfAccountService;
	}

	
	/**
	 * @set
	 * @param statementOfAccountService
	 */
	public void setStatementOfAccountService(
			IStatementOfAccountService statementOfAccountService) {
		/*
		 *@set
		 *@this.statementOfAccountService = statementOfAccountService
		 */
		this.statementOfAccountService = statementOfAccountService;
	}

	
	/**
	 * @get
	 * @return waybillRfcService
	 */
	public IWaybillRfcService getWaybillRfcService() {
		/*
		 * @get
		 * @return waybillRfcService
		 */
		return waybillRfcService;
	}

	
	/**
	 * @set
	 * @param waybillRfcService
	 */
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		/*
		 *@set
		 *@this.waybillRfcService = waybillRfcService
		 */
		this.waybillRfcService = waybillRfcService;
	}

	
	/**
	 * @get
	 * @return businessLockService
	 */
	public IBusinessLockService getBusinessLockService() {
		/*
		 * @get
		 * @return businessLockService
		 */
		return businessLockService;
	}

	
	/**
	 * @set
	 * @param businessLockService
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		/*
		 *@set
		 *@this.businessLockService = businessLockService
		 */
		this.businessLockService = businessLockService;
	}

	
	
	
	
}
