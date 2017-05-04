package com.deppon.foss.module.settlement.agency.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.agency.api.server.dao.IBillLandPayableAgencyDao;
import com.deppon.foss.module.settlement.agency.api.server.service.IBillLandPayableAgencyService;
import com.deppon.foss.module.settlement.agency.api.server.service.ICheckLdpExternalBillClient;
import com.deppon.foss.module.settlement.agency.api.shared.dto.BillPayableAgencyDto;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IOperatingLogService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.OperatingLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountUpdateDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillService;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillService;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 查询_审核_作废快递代理其它应付service
 * 
 * @author foss-guxinhua
 * @date 2012-11-6 下午3:42:19
 * @since
 * @version
 */
public class BillLandPayableAgencyService implements IBillLandPayableAgencyService {

	/**
	 * 注入应付的dao
	 */
	private IBillLandPayableAgencyDao billLandPayableAgencyDao;

	/**
	 * 注入公共的应付接口
	 */
	private IBillPayableService billPayableService;

	/**
	 * 注入验证正单号接口
	 */
	private IAirWaybillService airWaybillService;

	/**
	 * 注入自动生成单号接口
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 注入验证对账单接口
	 */
	private IStatementOfAccountService statementOfAccountService;

	/**
	 * 注入日志接口
	 */
	private IOperatingLogService operatingLogService;
	
	/**
	 * 注入获取公司名称的公共接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 注入获取配置参数接口
	 */
	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * 获取收货部门的名称
	 */
	private IWaybillManagerService waybillManagerService;
	
	/**
	 * 快递代理service接口
	 */
	private ILdpExternalBillService ldpExternalBillService;
	
	/**
	 * 校验运单号和快递代理在外发单中是否存在接口
	 */
	private ICheckLdpExternalBillClient checkLdpExternalBillClient;
	
	/**
	 * 查询快递代理其他管理应付单
	 * 
	 * 快递代理其他应付管理的分页查询
	 * 
	 * 开始时间和结束时间相差不能大于一个月
	 * 
	 * 用户只能查询到当前登录用户下属部门信息
	 * 
	 * 输入单号的个数不能为空
	 * 
	 * 输入的单号的个数不能超过10个
	 * 
	 * 当登陆用户只能查询所属部门的快递代理其它应付单信息
	 * 
	 * 快递代理其它应付单必须为有效、非红单的数据
	 * 
	 * @author foss-guxinhua
	 * @date 2012-12-10 上午10:32:41
	 * @param billPayableAgencyDto
	 * @param start
	 * @param limit
	 * @param currentInfo
	 * @return
	 * @see com.deppon.foss.module.settlement.agency.api.server.service.IBillPayableAgencyService#queryLandPayablePage(com.deppon.foss.module.settlement.agency.api.shared.dto.BillPayableAgencyDto,
	 *      int, int,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public BillPayableAgencyDto queryLandPayablePage(
			BillPayableAgencyDto billPayableAgencyDto, int start, int limit,
			CurrentInfo currentInfo) {
		//判断传入的参数是否为空
		if (billPayableAgencyDto == null) {
			throw new SettlementException("内部错误，应付代理成本对象为空!");
		}

		// 1,当前登陆用户只能查询所属部门的快递代理其它应付单信息
		//应付部门编码
		billPayableAgencyDto
				.setPayableOrgCode(currentInfo.getCurrentDeptCode());
		//应付部门名称
		billPayableAgencyDto
				.setPayableOrgName(currentInfo.getCurrentDeptName());
		// 2,快递代理其它应付单必须为有效的数据
		billPayableAgencyDto.setActive(FossConstants.YES);
		//快递代理其它应付单必须为非红单的数据
		billPayableAgencyDto.setIsRedBack(FossConstants.NO);
		//单据类型为快递代理
		billPayableAgencyDto
				.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE_OTHER);
		//声明应付单集合对象
		List<BillPayableEntity> billPayableEntityList = null;
		//声明数字对象
		Long totalNum = null;
		// 日期不为空，则以日期为条件查询所有的数据
		if (StringUtils.equals(billPayableAgencyDto.getQueryByTab(), 
				SettlementConstants.TAB_QUERY_BY_DATE)) {
			//根据传入获取一到多条应付单总数
			totalNum = billLandPayableAgencyDao
					.queryBillPayableEntityParamsCount(billPayableAgencyDto);
			//当数据条数大于0
			if (totalNum > 0) {
				//根据传入获取一到多条应付单信息
				billPayableEntityList = billLandPayableAgencyDao
						.queryBillPayableEntityParams(billPayableAgencyDto,
								start, limit);
				//设置总条数
				billPayableAgencyDto.setTotalNum(totalNum);
			}
		} else if (StringUtils.equals(billPayableAgencyDto.getQueryByTab()
				, SettlementConstants.TAB_QUERY_BY_PAYABLE_NO)) {
			//根据传入获取一到多条应付单总数
			totalNum = billLandPayableAgencyDao
					.queryByPayableNOsCount(billPayableAgencyDto);
			if (totalNum > 0) {
				// 应付单号不为空，则按应付单号查询
				billPayableEntityList = billLandPayableAgencyDao.queryByPayableNOs(
						billPayableAgencyDto, start, limit);
				//设置总条数
				billPayableAgencyDto.setTotalNum(totalNum);
			}
		} else if (StringUtils.equals(billPayableAgencyDto.getQueryByTab()
				, SettlementConstants.TAB_QUERY_BY_SOURCE_BILL_NO)) {
			totalNum = billLandPayableAgencyDao
					.queryBySourceBillNOsCount(billPayableAgencyDto);
			if (totalNum > 0) {
				// 航空正单号不为空，则按航空正单单号查询
				billPayableEntityList = billLandPayableAgencyDao
						.queryBySourceBillNOs(billPayableAgencyDto, start,
								limit);
				//设置总条数
				billPayableAgencyDto.setTotalNum(totalNum);
			}
		}
		// 用于返回的DTO
		if (CollectionUtils.isNotEmpty(billPayableEntityList)) {
			billPayableAgencyDto
					.setBillPayableEntityList(billPayableEntityList);
			// 设置查询到的应付单的总金额
			BigDecimal totalAmount = BigDecimal.ZERO;
			//设置查询到的应付单的未核销金额
			BigDecimal unverifyTotalAmount = BigDecimal.ZERO;
			//设置查询到的应付单已核销金额
			BigDecimal verifyTotalAmount = BigDecimal.ZERO;
			for (BillPayableEntity entity : billPayableEntityList) {
				totalAmount = totalAmount.add(entity.getAmount());
				unverifyTotalAmount = unverifyTotalAmount.add(entity
						.getUnverifyAmount());
				verifyTotalAmount = verifyTotalAmount.add(entity
						.getVerifyAmount());
			}
			//设置返回dto
			//设置应付单的总金额
			billPayableAgencyDto.setTotalAmount(totalAmount);
			//设置应付单的未核销金额
			billPayableAgencyDto.setUnverifyTotalAmount(unverifyTotalAmount);
			//设置应付单已核销金额
			billPayableAgencyDto.setVerifyTotalAmount(verifyTotalAmount);
		}

		return billPayableAgencyDto;
	}

	/**
	 * 无分页查询
	 * @author foss-guxinhua
	 * @date 2013-1-23 上午9:41:26
	 * @param billPayableAgencyDto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	private List<BillPayableEntity> queryLandPayablePage(BillPayableAgencyDto billPayableAgencyDto,
			CurrentInfo currentInfo){
		//声明一个应付单集合
		List<BillPayableEntity> billPayableEntities = null;
		//设置应付单的部门code
		billPayableAgencyDto.setPayableOrgCode(currentInfo.getCurrentDeptCode());
		//设置应付单的部门name
    	billPayableAgencyDto.setPayableOrgName(currentInfo.getCurrentDeptName());
    	//查询的数据是快递代理的
    	billPayableAgencyDto.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE_OTHER);
    	//查询的数据是有效的
    	billPayableAgencyDto.setIsRedBack(FossConstants.NO);
    	//查询的数据是有效的，非红单
    	String active = FossConstants.ACTIVE;
		billPayableAgencyDto.setActive(active);
		//如果是日期页签，则按日期查询
		if(StringUtils.equals(billPayableAgencyDto.getQueryByTab(), 
				SettlementConstants.TAB_QUERY_BY_DATE)){
			// 业务结束时间
			Date dateTemp = DateUtils.addDayToDate(billPayableAgencyDto.getEndBusinessDate(), 1);
			billPayableAgencyDto.setEndBusinessDate(dateTemp);
			//调用应付单dao层接口，获取后台数据信息
			billPayableEntities = billLandPayableAgencyDao.queryBillPayableEntityParams(billPayableAgencyDto);
		}
		
		if(StringUtils.equals(billPayableAgencyDto.getQueryByTab()
				, SettlementConstants.TAB_QUERY_BY_PAYABLE_NO)){
			//处理单号
			String[] payableNo = billPayableAgencyDto.getPayableNo().split(",");
			//收集单号，放入单号集合
			List<String> payableNos = Arrays.asList(payableNo);
			//调用应付单dao层接口，获取后台数据信息
			billPayableEntities = billPayableService.queryByPayableNOs(payableNos, active);
		}
		
		if(StringUtils.equals(billPayableAgencyDto.getQueryByTab()
				, SettlementConstants.TAB_QUERY_BY_SOURCE_BILL_NO)){
			String[] sourceBillNo = billPayableAgencyDto.getSourceBillNo().split(",");
			List<String> sourceBillNos = Arrays.asList(sourceBillNo);
			billPayableAgencyDto.setSourceBillNos(sourceBillNos);
    		//航空正单号不为空，则按航空正单单号查询
			billPayableEntities = billLandPayableAgencyDao.queryBySourceBillNOs(billPayableAgencyDto);
		}
		return billPayableEntities;
	}
	/**
	 * 快递代理其他应付单导出
	 * @author foss-guxinhua 
	 * @date 2012-12-27 上午9:46:15
	 * @param billReceivableAgencyDto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	public HSSFWorkbook landPayableListExport(BillPayableAgencyDto billPayableAgencyDto,
			CurrentInfo currentInfo){
		//声明一个对象
		HSSFWorkbook work = null;
		//判断传入参数是否为空
		if(billPayableAgencyDto == null){
			throw new SettlementException("内部错误，传入参数为空！");
		}
		//无分页查询快递代理其他应付单
		List<BillPayableEntity> billPayableEntities = this.queryLandPayablePage(billPayableAgencyDto, currentInfo);
		
		//判断加载后的数据是否为空
		if(CollectionUtils.isEmpty(billPayableEntities)){
			throw new SettlementException("库中数据不存在，不能进行导出操作！");
		}
		
		// 查询到的结果集不为空时，执行导出操作
		if (CollectionUtils.isNotEmpty(billPayableEntities)) {
			//导出Excel的最大条数
			if(billPayableEntities.size()>SettlementConstants.EXPORT_EXCEL_MAX_COUNTS){
				throw new SettlementException("每次最多只能导出"+ 
						SettlementConstants.EXPORT_EXCEL_MAX_COUNTS +"条数据,请重新查询并导出");
			}
			ExcelExport export = new ExcelExport();
			billPayableAgencyDto.setBillPayableEntityList(billPayableEntities);
			// 设置每次最多导出10万条数据
			//生成快递代理其他应付单的excel
			work = export.exportExcel(
					fillSheetDataByBillPayable(billPayableAgencyDto), "sheet", SettlementConstants.EXPORT_MAX_COUNT);
		} else {
			throw new SettlementException("导出数据为空！");
		}
		return work;
	}
	
	/**
	 * 生成快递代理其他应付单的excel
	 * @author foss-guxinhua
	 * @date 2012-12-6 上午11:13:38
	 */
	private SheetData fillSheetDataByBillPayable(
			BillPayableAgencyDto  dto) {
		SheetData sheetData = new SheetData();
		sheetData.setRowHeads(new String[] { "应付单编号", "来源单号", "快递代理编码", "快递代理名称",
				"收货部门编码","收货部门名称","审核状态","创建人编码","创建人名称","审核人编码","审核人名称",
				"总金额", "已核销金额", "未核销金额", "业务日期","记账日期", "审核时间", "备注" });

		List<BillPayableEntity> lists = dto.getBillPayableEntityList();
		
		//声明字典集合
		List<String> termCodes = new ArrayList<String>();
		termCodes.add(DictionaryConstants.BILL_PAYABLE__APPROVE_STATUS);
		//后台获取数据字典对应的数据
		Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(termCodes);
		
		if(CollectionUtils.isNotEmpty(lists)){
			List<List<String>> rowList = new ArrayList<List<String>>();
			for (BillPayableEntity entity : lists) {
				List<String> cellList = new ArrayList<String>();
				// 应付单编号
				cellList.add(entity.getPayableNo());
				// 航空正单号
				cellList.add(entity.getSourceBillNo());
				//航空公司编码
				cellList.add(entity.getCustomerCode());
				//航空公司名称
				cellList.add(entity.getCustomerName());
				//部门编码
				cellList.add(entity.getOrigOrgCode());
				//部门名称
				cellList.add(entity.getOrigOrgName());
				cellList.add(SettlementUtil.getDataDictionaryByTermsName(map
						, DictionaryConstants.BILL_PAYABLE__APPROVE_STATUS, 
						entity.getApproveStatus()));//审核状态
				//创建人编码
				cellList.add(entity.getCreateOrgCode());
				//创建人名称
				cellList.add(entity.getCreateUserName());
				//审核人编码
				cellList.add(entity.getAuditUserCode());
				//审核人名称
				cellList.add(entity.getAuditUserName());
				//金额
				cellList.add(entity.getAmount() != null ? entity.getAmount() + "" : "");
				//已核销金额
				cellList.add(entity.getVerifyAmount() != null ? entity.getVerifyAmount() + "" : "");
				//未核销金额
				cellList.add(entity.getUnverifyAmount() != null ? entity.getUnverifyAmount() + "" : "");
				//创建时间
				cellList.add(DateUtils.convert(entity.getBusinessDate(),DateUtils.DATE_TIME_FORMAT));
				//记账日期
				cellList.add(DateUtils.convert(entity.getAccountDate(),DateUtils.DATE_TIME_FORMAT));
				//审核时间
				cellList.add(DateUtils.convert(entity.getAuditDate(),DateUtils.DATE_TIME_FORMAT));
				//备注
				cellList.add(entity.getNotes());
				rowList.add(cellList);
			}
			sheetData.setRowList(rowList);
		}
		return sheetData;
	}	
	
	/**
	 * 快递代理其他应付管理作废
	 * 
	 * @author foss-guxinhua
	 * @date 2012-11-8 上午9:45:15
	 * @param billPayableAgencyDto
	 * @return
	 * @see
	 */
	@Transactional
	public BillPayableAgencyDto writeBackLandOtherBillPayable(
			List<BillPayableAgencyDto> billPayableAgencyDtos,
			BillPayableAgencyDto billPayableAgencyDto,CurrentInfo currentInfo) {
		//判断传入参数是否为空
		if (CollectionUtils.isEmpty(billPayableAgencyDtos)) {
			throw new SettlementException("内部错误，应付代理成本为空！");
		}
		
		//实例一个应付单号集合
		List<String> payableNos = new ArrayList<String>();
		//收集应付单
		for (BillPayableAgencyDto payableAgencyDto : billPayableAgencyDtos) {
			//放入应付单集合中
			payableNos.add(payableAgencyDto.getPayableNo());
		}
		//根据传入的一到多个应付单号，获取一到多条应付单信息
		List<BillPayableEntity> billPayableEntities = billPayableService
					.queryByPayableNOs(payableNos,FossConstants.ACTIVE);
		//判断加载后的数据是否为空
		if (CollectionUtils.isEmpty(billPayableEntities)) {
			//库中数据已改变，不能进行审核操作！异常
			throw new SettlementException("库中数据已改变，不能进行审核操作！");
		}
		
		for(BillPayableEntity billPayableEntity : billPayableEntities){
			// 1、验证客户编码是否为空
			if (StringUtils.isEmpty(billPayableEntity.getCustomerCode())) {
				//待作废数据的客户编码为空!
				throw new SettlementException("待作废数据的客户编码为空!");
			}
			// 2,已审核的单据不能作废
			String appStatus = billPayableEntity.getApproveStatus();
			if (StringUtils.equals(appStatus,
							SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE)) {
				throw new SettlementException("已审核的单据不能作废!");
			}
		}
		
		//循环调用作废公共接口
		for(BillPayableEntity billPayableEntity : billPayableEntities){
    		// 修改为已作废
    		billPayableEntity.setActive(FossConstants.NO);
    		// 调用common下面的作废接口
    		billPayableService.writeBackBillPayable(billPayableEntity,
    				currentInfo);
		}

		//声明实例对账单dto
		StatementOfAccountUpdateDto statementOfAccountUpdateDto = new StatementOfAccountUpdateDto();
		//把相应的数据设置到对账单中
		statementOfAccountUpdateDto.setPayableEntityList(billPayableEntities);
		// 作废完成时，通知修改对账单及对账单明细信息
//		statementOfAccountService.updateStatementAndDetailForRedBack(statementOfAccountUpdateDto, currentInfo);

		// 将单据信息、作废人、作废时间保存到操作日志表中
		OperatingLogEntity operatingLogEntity = new OperatingLogEntity();
		//循环加入日志
		for(BillPayableEntity payableEntity : billPayableEntities){
			//设置应付单单号到日志单号
			operatingLogEntity.setOperateBillNo(payableEntity.getPayableNo());
			//操作日志类型
			operatingLogEntity
					.setOperateBillType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__LAND_STOWAGE_PAYABLE);
			//操作日志类型
			operatingLogEntity
					.setOperateType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__DISABLE);
			//新加操作日志
			operatingLogService.addOperatingLog(operatingLogEntity,
					currentInfo);
		}
		
		//返回库中最新数据到界面
		billPayableEntities = this.queryLandPayablePage(billPayableAgencyDto, currentInfo);
		billPayableAgencyDto.setBillPayableEntityList(billPayableEntities);
		return billPayableAgencyDto;
	}

	/**
	 * 快递代理其他应付管理审核
	 * 
	 * @author foss-guxinhua
	 * @date 2012-11-7 下午2:55:27
	 * @param billPayableAgencyDto
	 * @return
	 * @see com.deppon.foss.module.settlement.agency.api.server.service.IBillPayableAgencyService#auditLandOtherBillPayable(com.deppon.foss.module.settlement.agency.api.shared.dto.BillPayableAgencyDto)
	 */
	@Transactional
	@Override
	public BillPayableAgencyDto auditLandOtherBillPayable(
			List<BillPayableAgencyDto> billPayableAgencyDtos,
			BillPayableAgencyDto billPayableAgencyDto,CurrentInfo currentInfo) {
		//判断传入参数是否为空
		if(CollectionUtils.isEmpty(billPayableAgencyDtos)){
			throw new SettlementException("内部错误，应付代理成本对象为空!");
		}

		//声明实例一个应付单集合
		List<String> payableNos = new ArrayList<String>();
		// 循环拿取应付单号
		for (BillPayableAgencyDto payableAgencyDto : billPayableAgencyDtos) {
			//放到应付单号集合中
			payableNos.add(payableAgencyDto.getPayableNo());
		}

		// load库中对应的数据
		List<BillPayableEntity> billPayableEntities = billPayableService
				.queryByPayableNOs(payableNos, FossConstants.ACTIVE);
		//判断加载后的数据是否为空
		if (CollectionUtils.isEmpty(billPayableEntities)) {
			throw new SettlementException("库中数据已改变，不能进行审核操作！");
		}

		// 循环对每条数据进行校验
		for (BillPayableEntity billPayableEntity : billPayableEntities) {
			// 1、验证客户编码是否为空
			if (StringUtils.isEmpty(billPayableEntity.getCustomerCode())) {
				throw new SettlementException("待审核数据的客户编码为空!");
			}
			// 2,已审核的单据不能审核
			String appStatus = billPayableEntity.getApproveStatus();
			if (StringUtils.equals(appStatus,
							SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE)) {
				//已审核的单据不能审核!
				throw new SettlementException("已审核的单据不能审核!");
			}
		}
		// 调用审核接口
		BillPayableDto billPayableDto = new BillPayableDto();
		// 传list实体
		billPayableDto.setBillPayables(billPayableEntities);

		Date now = new Date();
		// 审核时间
		billPayableDto.setAuditDate(now);
		// 修改审核状态为已审核
		billPayableDto.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);
		// 2,快递代理其它应付单必须为有效、非红单的数据
		billPayableDto.setActive(FossConstants.YES);
		billPayableDto.setIsRedBack(FossConstants.NO);
		billPayableDto
				.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE_OTHER);
		// 调用common下面的审核接口
		billPayableService.batchAuditBillPayable(billPayableDto, currentInfo);

		// 将单据信息、审核人、审核时间保存到操作日志表中
		OperatingLogEntity operatingLogEntity = new OperatingLogEntity();
		//循环插入日志
		for (BillPayableEntity billPayableEntity : billPayableEntities) {
			//插入日志编号
			operatingLogEntity.setOperateBillNo(billPayableEntity
					.getPayableNo());
			// 插入日志类型
			operatingLogEntity
					.setOperateBillType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__LAND_STOWAGE_PAYABLE);
			// 插入日志类型
			operatingLogEntity
					.setOperateType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__AUDIT);
			//插入日志
			operatingLogService
					.addOperatingLog(operatingLogEntity, currentInfo);
		}
		
		//返回库中最新数据到界面
		billPayableEntities = this.queryLandPayablePage(billPayableAgencyDto, currentInfo);
		billPayableAgencyDto.setBillPayableEntityList(billPayableEntities);
		return billPayableAgencyDto;
	}

	/**
	 * 快递代理其他应付管理反审核
	 * 
	 * @author foss-guxinhua
	 * @date 2012-11-7 下午2:55:45
	 * @param billPayableAgencyDto
	 * @return
	 * @see com.deppon.foss.module.settlement.agency.api.server.service.IBillPayableAgencyService#reverseAuditLandOtherBillPayable(com.deppon.foss.module.settlement.agency.api.shared.dto.BillPayableAgencyDto)
	 */
	@Transactional
	@Override
	public BillPayableAgencyDto reverseAuditLandOtherBillPayable(
			List<BillPayableAgencyDto> billPayableAgencyDtos,
			BillPayableAgencyDto billPayableAgencyDto,CurrentInfo currentInfo) {
		//判断传入参数是否为空
		if(CollectionUtils.isEmpty(billPayableAgencyDtos)){
			throw new SettlementException("内部错误，应付代理成本对象为空!");
		}

		//声明实例一个应付单集合
		List<String> payableNos = new ArrayList<String>();
		// 循环拿取应付单号
		for (BillPayableAgencyDto payableAgencyDto : billPayableAgencyDtos) {
			//放到应付单号集合中
			payableNos.add(payableAgencyDto.getPayableNo());
		}

		// load库中对应的数据
		List<BillPayableEntity> billPayableEntities = billPayableService
				.queryByPayableNOs(payableNos, FossConstants.ACTIVE);

		if (CollectionUtils.isEmpty(billPayableEntities)) {
			throw new SettlementException("库中数据已改变，不能进行审核操作！");
		}

		// 循环对每条数据进行校验
		for (BillPayableEntity billPayableEntity : billPayableEntities) {
			// 1、验证客户编码是否为空
			if (StringUtils.isEmpty(billPayableEntity.getCustomerCode())) {
				throw new SettlementException("待反审核数据的客户编码为空!");
			}
			// 2,未审核的单据不能反审核
			String appStatus = billPayableEntity.getApproveStatus();
			if (StringUtils.equals(appStatus,
							SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT)) {
				throw new SettlementException("未审核单据不能进行反审核操作！");
			}
			// 3,已核销金额大于0的单据不能被反审核
			if (billPayableEntity.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
				throw new SettlementException("已参与核销的单据不能反审核!");
			}
			
			//正在申请付款的单据不能反审核
			if (StringUtils.equals(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES
					,billPayableEntity.getPayStatus())) {
						throw new SettlementException(
	    						"已申请付款的单据不能反审核！");
			}
		}
		
		// 调用反审核接口
		BillPayableDto billPayableDto = new BillPayableDto();
		// 传list实体
		billPayableDto.setBillPayables(billPayableEntities);

		// 反审核时间
		billPayableDto.setAuditDate(null);
		// 修改已审核状态为未审核
		billPayableDto
				.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT);
		// 快递代理其它应付单必须为有效的数据
		billPayableDto.setActive(FossConstants.YES);
		// 快递代理其它应付单必须为非红单的数据
		billPayableDto.setIsRedBack(FossConstants.NO);
		// 快递代理其他应付单必须为快递代理
		billPayableDto.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE_OTHER);
		// 调用common下面的反审核接口
		billPayableService.batchReverseAuditBillPayable(billPayableDto,
				currentInfo);

		//声明实例对账单dto
		StatementOfAccountUpdateDto statementOfAccountUpdateDto = new StatementOfAccountUpdateDto();
		//设置参数
		statementOfAccountUpdateDto.setPayableEntityList(billPayableEntities);
		// 作废完成时，通知修改对账单及对账单明细信息
//		statementOfAccountService.updateStatementAndDetailForRedBack(
//				statementOfAccountUpdateDto, currentInfo);

		// 将单据信息、反审核人、反审核时间保存到操作日志表中
		OperatingLogEntity operatingLogEntity = new OperatingLogEntity();
		//循环插入日志
		for (BillPayableEntity billPayableEntity : billPayableEntities) {
			//插入日志单号
			operatingLogEntity.setOperateBillNo(billPayableEntity.getPayableNo());
			// 插入单据类型
			operatingLogEntity
					.setOperateBillType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__LAND_STOWAGE_PAYABLE);
			// 插入操作类型
			operatingLogEntity
					.setOperateType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__REVERSE_AUDIT);
			//插入日志
			operatingLogService
					.addOperatingLog(operatingLogEntity, currentInfo);
		}
		//返回库中最新数据到界面
		billPayableEntities = this.queryLandPayablePage(billPayableAgencyDto, currentInfo);
		billPayableAgencyDto.setBillPayableEntityList(billPayableEntities);
		return billPayableAgencyDto;
	}

	/**
	 * 新增快递代理其他应付
	 * 
	 * @author foss-guxinhua
	 * @date 2012-11-7 上午11:44:47
	 * @param billPayableAgencyDto
	 * @see com.deppon.foss.module.settlement.agency.api.server.service.IBillPayableAgencyService#addBillPayable(com.deppon.foss.module.settlement.agency.api.shared.dto.BillPayableAgencyDto)
	 */
	@Transactional
	@Override
	public void addBillPayable(BillPayableAgencyDto billPayableAgencyDto,
			CurrentInfo currentInfo) {
		BillPayableEntity billPayableEntity = new BillPayableEntity();
		final int NumberThree = 300;
		//判断传入参数是否为空
		if (billPayableAgencyDto == null) {
			throw new SettlementException("内部异常，传入参数为空，不能新增快递代理其他应付信息!");
		}
		
		//获取客户编码
		String customerCode = billPayableAgencyDto.getCustomerCode();
		//如果运单号不为空，则获取界面单号，为空则获取默认单号
		getNotNullWaybillNo(billPayableAgencyDto, billPayableEntity,
				customerCode);
		
		//获取dto中的金额
		BigDecimal amount = billPayableAgencyDto.getAmount();
		//判断金额是否为空
		if(amount == null){
			throw new SettlementException("内部异常，传入应付金额参数为空，不能新增快递代理其他应付信息!");
		}
		
		//获取应付金额最大配置参数
		String amountStr  = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.STL_LDP_ADD_MAX_AMOUNT);
		BigDecimal amountBig = new BigDecimal(amountStr);
		
		//判断应付的最大最小值
		if(amount.compareTo(BigDecimal.ZERO) != 1
				|| amount.compareTo(amountBig) != -1  ){
			throw new SettlementException("应付金额必须输入大于0且小于"+ amountStr +"的金额!");
		}
		//备注长度小于300个汉字
		if(billPayableAgencyDto.getNotes().length() >= NumberThree){
			throw new SettlementException("备注信息长度小于300个汉字（包含空格和标点符号）!");
		}
		/**
		 * @author 218392 zhangyongxue 2015-07-27 16:37:39
		 * 设置 其他应付类型
		 */
		billPayableEntity.setPayableType(billPayableAgencyDto.getOtherPayType());
		
		// 设置快递代理其他应付管理的代理、航空公司名称、代理信息为界面输入信息
		billPayableEntity.setCustomerName(billPayableAgencyDto.getCustomerName());
		// 设置快递代理其他应付管理的代理、航空公司编码、代理信息为界面输入信息
		billPayableEntity.setCustomerCode(customerCode);

		// 设置快递代理其他应付管理的金额
		billPayableEntity.setAmount(billPayableAgencyDto.getAmount());

		// 设置快递代理其他应付管理的未核销金额
		billPayableEntity.setUnverifyAmount(billPayableAgencyDto.getAmount());

		// 设置快递代理其他应付管理的核销金额
		billPayableEntity.setVerifyAmount(BigDecimal.ZERO);
		// 设置id
		billPayableEntity.setId(UUIDUtils.getUUID());

		// 设置快递代理其他应付管理的系统生成方式
		billPayableEntity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);

		// 设置快递代理其他应付管理的单据子类型
		billPayableEntity.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE_OTHER);

		// 设置审核状态为未审核
		billPayableEntity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT);

		// 设置快递代理其他应付管理的代理、航空公司code
		billPayableEntity.setPayableOrgCode(currentInfo.getCurrentDeptCode());
		//设置快递代理其他应付管理的代理、航空公司名称
		billPayableEntity.setPayableOrgName(currentInfo.getCurrentDeptName());
		
		//设置到达名称、编码
		billPayableEntity.setDestOrgCode(currentInfo.getCurrentDeptCode());
		billPayableEntity.setDestOrgName(currentInfo.getCurrentDeptName());
		
		// 获取当前选择的应付部门组织的的实体信息
		OrgAdministrativeInfoEntity payOrgEntity = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCode(currentInfo.getCurrentDeptCode());
		//判断组织信息是否为空
		if(payOrgEntity == null){
			//当前选择的应付部门组织信息为空异常
			throw new SettlementException("当前选择的应付部门组织信息为空！");
		}
		
		//判断，当前登陆用户不属于外场部门，
		if(!StringUtils.equals(payOrgEntity.getTransferCenter(), FossConstants.YES)){
			throw new SettlementException("当前部门不是顶级外场，不能新增快递代理其他应付信息!");
		}
		
		//设置应付部门code
		billPayableEntity.setPayableComCode(payOrgEntity.getSubsidiaryCode());
		//设置应付部门name
		billPayableEntity.setPayableComName(payOrgEntity.getSubsidiaryName());
		
		// 生效状态不能为空
		billPayableEntity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES);

		// 支付状态不能为空
		billPayableEntity.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);

		// 冻结状态不能为空
		billPayableEntity.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);

		// 设置快递代理其他应付管理的币种
		billPayableEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);

		// 业务日期、记账日期为系统当前时间；创建时间默认为系统当前时间
		Date now = new Date();
		// 设置快递代理其他应付管理的业务时间
		billPayableEntity.setBusinessDate(now);

		// 设置快递代理其他应付管理的记账时间
		billPayableEntity.setAccountDate(now);

		// 设置快递代理其他应付管理的确认付入日期,暂未空
		
		// 变更
		billPayableEntity.setSignDate(now);

		// 新增的快递代理其他应付单单据默认为有效的单据
		billPayableEntity.setActive(FossConstants.ACTIVE);
		//新增的快递代理其他应付单单据默认为非红单
		billPayableEntity.setIsRedBack(FossConstants.NO);
		//新增的快递代理其他应付单单据默认为非初始化
		billPayableEntity.setIsInit(FossConstants.NO);
		// 版本号
		billPayableEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
		// 默认单号
		billPayableEntity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
		billPayableEntity.setPaymentNo(SettlementConstants.DEFAULT_BILL_NO);

		billPayableEntity.setNotes(billPayableAgencyDto.getNotes());

		// 设置快递代理其他应付管理的应付单号,应付单单号系统自动生成，生成规则“YF93+7位流水号”
		billPayableEntity.setPayableNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YF93));

		// 设置快递代理其他应付管理的代理、航空公司code
		billPayableEntity.setExpressOrigOrgCode(currentInfo.getCurrentDeptCode());
		//设置快递代理其他应付管理的代理、航空公司名称
		billPayableEntity.setExpressOrigOrgName(currentInfo.getCurrentDeptName());
		
		//设置到达名称、编码
		billPayableEntity.setExpressDestOrgCode(currentInfo.getCurrentDeptCode());
		billPayableEntity.setExpressDestOrgName(currentInfo.getCurrentDeptName());
		
		/*
		 * 税务改造需求
		 * 
		 * 快递代理其他应付发票标记为 02-非运输专票
		 * 
		 * 杨书硕 2013-11-5 上午9:58:56
		 */
		
		billPayableEntity.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);
		//新增应付单接口
		billPayableService.addBillPayable(billPayableEntity, currentInfo);
	}

	private void getNotNullWaybillNo(BillPayableAgencyDto billPayableAgencyDto,
			BillPayableEntity billPayableEntity, String customerCode) {
		if(StringUtils.isNotEmpty(billPayableAgencyDto.getWaybillNo())){
			
			//如果需要填入 “运单号”，则需检验运单号和快递代理在外发单中存在
//			if(!ldpExternalBillService.existHasAuditedLdpExternalBillForStl( billPayableAgencyDto.getWaybillNo(), customerCode)){
//				throw new SettlementException("运单号和快递代理在已审核外发单中不存在，不能新增快递代理其他应付信息！");
//			}
			/**
			 * @author 325369
			 * @date  2016-07-11 上午08:36:42
			 * 校验运单号和快递代理在外发单中是否存在，增加请求悟空系统接口判断
			 */
			if(!ldpExternalBillService.existHasAuditedLdpExternalBillForStl( billPayableAgencyDto.getWaybillNo(), customerCode)){
				/*
				 * 添加条件，结算代码在悟空系统之前上线，对调悟空系统的接口，添加开关 2016-08-09
				 * true:发送请求  false：不发送
				 */
				boolean flag = configurationParamsService.queryStlSwitch4Ecs();
				if (!flag) {
					throw new SettlementException("综合模块配合快递接口开关未打开或者未配置--不能新增应付");
				}	
				if(!checkLdpExternalBillClient.sendLdpExternalBillMsgToWK( billPayableAgencyDto.getWaybillNo(), customerCode)){
					throw new SettlementException("运单号和快递代理在已审核外发单中不存在，不能新增快递代理其他应付信息！");
				}
			}
			
			
			billPayableEntity.setWaybillNo(billPayableAgencyDto.getWaybillNo());
			billPayableEntity.setSourceBillNo(billPayableAgencyDto.getWaybillNo());
			//根据运单号在接送货查询对应的运单信息，拿到收货部门编码
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(billPayableAgencyDto.getWaybillNo());
			if(waybillEntity != null){
    			//根据收货部门编码调用综合的接口，获取相应的信息取到收货部门名称
    			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getReceiveOrgCode());
    			//设置运单的收货部门编码对应的应付单的出发部门编码
    			billPayableEntity.setOrigOrgCode(waybillEntity.getReceiveOrgCode());
    			//设置运单的收货部门名称对应的应付单的出发部门名称
    			billPayableEntity.setOrigOrgName(orgAdministrativeInfoEntity.getName());
    			// 设置快递代理其他应付管理的来源单据类型 运单
    			billPayableEntity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__WAYBILL);
    			
    			/*
    			 * 添加360特惠件快递代理产品类型
				 * 
				 * 10562 2014年8月6日 上午8:27:24
				 */

    			// 运输性质
    			billPayableEntity.setProductCode(waybillEntity.getProductCode());
    			
			}else {
				throw new SettlementException("运单号不合法，请修改确认后再操作！");
			}
		}else{
			throw new SettlementException("运单号不能为空，请修改确认后再操作！");
		}
	}

	/**
	 * @return the billPayableAgencyDao
	 */
	public IBillLandPayableAgencyDao getBillPayableAgencyDao() {
		return billLandPayableAgencyDao;
	}

	/**
	 * @param billPayableAgencyDao
	 *            the billPayableAgencyDao to set
	 */
	public void setBillLandPayableAgencyDao(
			IBillLandPayableAgencyDao billLandPayableAgencyDao) {
		this.billLandPayableAgencyDao = billLandPayableAgencyDao;
	}

	/**
	 * @return the billPayableService
	 */
	public IBillPayableService getBillPayableService() {
		return billPayableService;
	}

	/**
	 * @param billPayableService
	 *            the billPayableService to set
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	/**
	 * @return the airWaybillService
	 */
	public IAirWaybillService getAirWaybillService() {
		return airWaybillService;
	}

	/**
	 * @param airWaybillService
	 *            the airWaybillService to set
	 */
	public void setAirWaybillService(IAirWaybillService airWaybillService) {
		this.airWaybillService = airWaybillService;
	}

	/**
	 * @return the settlementCommonService
	 */
	public ISettlementCommonService getSettlementCommonService() {
		return settlementCommonService;
	}

	/**
	 * @param settlementCommonService
	 *            the settlementCommonService to set
	 */
	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	/**
	 * @return the statementOfAccountService
	 */
	public IStatementOfAccountService getStatementOfAccountService() {
		return statementOfAccountService;
	}

	/**
	 * @param statementOfAccountService
	 *            the statementOfAccountService to set
	 */
	public void setStatementOfAccountService(
			IStatementOfAccountService statementOfAccountService) {
		this.statementOfAccountService = statementOfAccountService;
	}

	/**
	 * @return the operatingLogService
	 */
	public IOperatingLogService getOperatingLogService() {
		return operatingLogService;
	}

	/**
	 * @param operatingLogService
	 *            the operatingLogService to set
	 */
	public void setOperatingLogService(IOperatingLogService operatingLogService) {
		this.operatingLogService = operatingLogService;
	}

	
	/**
	 * @return  the orgAdministrativeInfoService
	 */
	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	
	/**
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	
	/**
	 * @return  the configurationParamsService
	 */
	public IConfigurationParamsService getConfigurationParamsService() {
		return configurationParamsService;
	}

	
	/**
	 * @param configurationParamsService the configurationParamsService to set
	 */
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	
	/**
	 * @return  the waybillManagerService
	 */
	public IWaybillManagerService getWaybillManagerService() {
		return waybillManagerService;
	}

	
	/**
	 * @param waybillManagerService the waybillManagerService to set
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * @return ldpExternalBillService
	 */
	public ILdpExternalBillService getLdpExternalBillService() {
		return ldpExternalBillService;
	}

	/**
	 * @param ldpExternalBillService
	 */
	public void setLdpExternalBillService(
			ILdpExternalBillService ldpExternalBillService) {
		this.ldpExternalBillService = ldpExternalBillService;
	}
	
	public ICheckLdpExternalBillClient getCheckLdpExternalBillClient() {
		return checkLdpExternalBillClient;
	}

	public void setCheckLdpExternalBillClient(
			ICheckLdpExternalBillClient checkLdpExternalBillClient) {
		this.checkLdpExternalBillClient = checkLdpExternalBillClient;
	}
}
