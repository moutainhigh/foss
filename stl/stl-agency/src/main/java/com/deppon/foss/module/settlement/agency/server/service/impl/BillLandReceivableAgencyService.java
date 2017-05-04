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
import com.deppon.foss.module.settlement.agency.api.server.dao.IBillLandReceivableAgencyDao;
import com.deppon.foss.module.settlement.agency.api.server.service.IBillLandReceivableAgencyService;
import com.deppon.foss.module.settlement.agency.api.server.service.ICheckLdpExternalBillClient;
import com.deppon.foss.module.settlement.agency.api.shared.dto.BillReceivableAgencyDto;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IOperatingLogService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.OperatingLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountUpdateDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirWaybillService;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillService;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 查询_审核_作废快递代理其它应收Service
 * @author foss-guxinhua
 * @date 2012-10-24 下午4:34:23
 * @since
 * @version
 */
public class BillLandReceivableAgencyService implements IBillLandReceivableAgencyService {
	
	/**
	 * 注入service
	 */
	private IBillLandReceivableAgencyDao billLandReceivableAgencyDao;
	
	/**
	 * 注入公共的应收接口
	 */
	private IBillReceivableService billReceivableService;
	
	/**
	 * 注入自动生成单号接口
	 */
	private ISettlementCommonService settlementCommonService;
	
	/**
	 * 注入验证正单号接口
	 */
	private IAirWaybillService airWaybillService;
	
	/**
	 * 注入日志接口
	 */
	private IOperatingLogService operatingLogService;
	
	/**
	 * 注入验证对账单接口
	 */
	private IStatementOfAccountService statementOfAccountService;
	
	/**
	 * 获取组织信息
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 获取配置参数信息
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
	 * 快递代理其他管理分页查询
	 * @author foss-guxinhua
	 * @date 2012-11-1 下午2:05:54
	 * @param billReceivableAgencyDto
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	@Override
	public BillReceivableAgencyDto queryLandReceivablePage(BillReceivableAgencyDto billReceivableAgencyDto,int start, int limit,CurrentInfo currentInfo){
		//1,当前登陆用户只能查询所属部门的快递代理其它应收单信息
		//设置应收部门编码
		billReceivableAgencyDto.setReceivableOrgCode(currentInfo.getCurrentDeptCode());
		//设置应收部门名称
		billReceivableAgencyDto.setReceivableOrgName(currentInfo.getCurrentDeptName());
		//快递代理其它应收单必须为有效的数据
		billReceivableAgencyDto.setActive(FossConstants.YES);
		//快递代理其它应收单必须为非红单的数据
		billReceivableAgencyDto.setIsRedBack(FossConstants.NO);
		//快递代理其他应收单类型必须为快递代理
		billReceivableAgencyDto.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_RECEIVABLE);
		//声明一个应收单集合对象
		List<BillReceivableEntity> billReceivableEntityList = null;
		//声明一个对象
		Long totalNum = null;
		// 日期不为空，则以日期为条件查询所有的数据
		if(StringUtils.equals(billReceivableAgencyDto.getQueryByTab(),SettlementConstants.TAB_QUERY_BY_DATE)){
			// 业务结束时间
			Date dateEndBusiness = DateUtils.addDayToDate(billReceivableAgencyDto.getEndBusinessDate(), 1);
			//设置时间
			billReceivableAgencyDto.setEndBusinessDate(dateEndBusiness);
			//获取总条数
			totalNum = billLandReceivableAgencyDao.queryBillReceivableEntityParamsCount(billReceivableAgencyDto);
			//当条数大于0
			if(totalNum > 0){
				//获取应收单的数据
    			billReceivableEntityList =billLandReceivableAgencyDao
    					.queryBillReceivableEntityParams(billReceivableAgencyDto,start,limit);
    			//设置总条数
    			billReceivableAgencyDto.setTotalNumRec(totalNum);
			}
		}else if(StringUtils.equals(billReceivableAgencyDto.getQueryByTab(),SettlementConstants.TAB_QUERY_BY_RECEIVABLE_NO)){
			// 获取预收单号并分割处理
			String[] receivableNos = billReceivableAgencyDto.getReceivableNo().split(",|;");
			List<String> receivableNoList = Arrays.asList(receivableNos);
			billReceivableAgencyDto.setReceivableNos(receivableNoList);
			//获取总条数
			totalNum = billLandReceivableAgencyDao.queryByReceivableNOsCount(billReceivableAgencyDto);
			if(totalNum > 0){
    			//应收单号不为空，则按应收单号查询
				//根据传入的一到多个应收单号，获取一到多条应收单信息
    			billReceivableEntityList = billLandReceivableAgencyDao.queryByReceivableNOs(billReceivableAgencyDto,start,limit);
    			//设置总条数
    			billReceivableAgencyDto.setTotalNumRec(totalNum);
			}
		}else if(StringUtils.equals(billReceivableAgencyDto.getQueryByTab(),SettlementConstants.TAB_QUERY_BY_SOURCE_BILL_NO)){
			// 获取航空正单号并分割处理
			String[] sourceBillNos = billReceivableAgencyDto.getSourceBillNo().split(",|;");
			List<String> sourceBillNoList = Arrays.asList(sourceBillNos);
			//设置单号集合到对象中
			billReceivableAgencyDto.setSourceBillNos(sourceBillNoList);
			//获取总条数
			totalNum = billLandReceivableAgencyDao.queryBySourceBillNOsCount(billReceivableAgencyDto);
			if(totalNum > 0){
    			//航空正单号不为空，则按航空正单单号查询
				//根据传入的一到多个航空正单号，获取一到多条应收单信息
    			billReceivableEntityList = billLandReceivableAgencyDao
    					.queryBySourceBillNOs(billReceivableAgencyDto,start,limit);
    			//设置总条数
    			billReceivableAgencyDto.setTotalNumRec(totalNum);
			}
		}
		//用于返回的DTO
		if (CollectionUtils.isNotEmpty(billReceivableEntityList)) {
			billReceivableAgencyDto
					.setBillReceivableEntityList(billReceivableEntityList);
			// 设置查询到的应收单的总条数、总金额、未核销金额、已核销金额
			BigDecimal totalAmount = BigDecimal.ZERO;
			BigDecimal unverifyTotalAmount = BigDecimal.ZERO;
			BigDecimal verifyTotalAmount = BigDecimal.ZERO;
			for(BillReceivableEntity entity : billReceivableEntityList){
				// 设置查询到的应收单的总金额
				totalAmount = totalAmount.add(entity.getAmount());
				// 设置查询到的应收单的核销金额
				unverifyTotalAmount = unverifyTotalAmount.add(entity.getUnverifyAmount());
				// 设置查询到的应收单已核销金额
				verifyTotalAmount = verifyTotalAmount.add(entity.getVerifyAmount());
			}
			// 设置返回dto
			// 设置查询到的应收单的总金额
			billReceivableAgencyDto.setTotalAmountRec(totalAmount);
			// 设置查询到的应收单的未核销金额
			billReceivableAgencyDto.setUnverifyTotalAmountRec(unverifyTotalAmount);
			// 设置查询到的应收单的已核销金额
			billReceivableAgencyDto.setVerifyTotalAmountRec(verifyTotalAmount);
		}
		return billReceivableAgencyDto;
	}

	/**
	 * 快递代理其他应收单导出
	 * @author foss-guxinhua 
	 * @date 2012-12-27 上午9:46:15
	 * @param billReceivableAgencyDto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	public HSSFWorkbook landReceivableListExport(BillReceivableAgencyDto billReceivableAgencyDto,
			CurrentInfo currentInfo){
		HSSFWorkbook work = null;
		if(billReceivableAgencyDto != null){
			//获取库中最新数据
			List<BillReceivableEntity> billReceivableEntities = this.queryLandReceivablePage(billReceivableAgencyDto, currentInfo);
			
			// 查询到的结果集不为空时，执行导出操作
			if (CollectionUtils.isNotEmpty(billReceivableEntities)) {
				if(billReceivableEntities.size()>SettlementConstants.EXPORT_EXCEL_MAX_COUNTS){
					throw new SettlementException("每次最多只能导出"+
							SettlementConstants.EXPORT_EXCEL_MAX_COUNTS +"条数据,请重新查询并导出");
				}
				//声明实例对象
				ExcelExport export = new ExcelExport();
				billReceivableAgencyDto.setBillReceivableEntityList(billReceivableEntities);
				// 设置每次最多导出10万条数据
				work = export.exportExcel(
						fillSheetDataByBillReceivable(billReceivableAgencyDto), "sheet", SettlementConstants.EXPORT_MAX_COUNT);
			} else {
				throw new SettlementException("导出数据为空！");
			}
		}
		return work;
	}
	
	/**
	 * 生成快递代理其他应收单的excel
	 * @author foss-guxinhua
	 * @date 2012-12-6 上午11:13:38
	 */
	private SheetData fillSheetDataByBillReceivable(
			BillReceivableAgencyDto  dto) {
		//声明实例对象
		SheetData sheetData = new SheetData();
		sheetData.setRowHeads(new String[] { "应收单号", "来源单号", "快递代理编码", "快递代理名称",
				"收货部门编码","收货部门名称","审核状态","创建人编码","创建人名称","审核人编码","审核人名称",
				"总金额", "已核销金额", "未核销金额", "业务日期","记账日期", "审核时间", "备注" });
		//获取参数中的结合
		List<BillReceivableEntity> lists = dto.getBillReceivableEntityList();
		
		//声明字典集合
		List<String> termCodes = new ArrayList<String>();
		termCodes.add(DictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS);
		//后台获取数据字典对应的数据
		Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(termCodes);
		
		//循环遍历集合对象
		if(CollectionUtils.isNotEmpty(lists)){
			//声明实例集合对象
			List<List<String>> rowList = new ArrayList<List<String>>();
			for (BillReceivableEntity entity : lists) {
				List<String> cellList = new ArrayList<String>();
				// 应收单号
				cellList.add(entity.getReceivableNo());
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
						, DictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS, 
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
	 * 快递代理其他管理查询
	 * @author foss-guxinhua
	 * @date 2013-1-18 上午10:23:19
	 * @param billReceivableAgencyDto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	private List<BillReceivableEntity> queryLandReceivablePage(BillReceivableAgencyDto billReceivableAgencyDto,CurrentInfo currentInfo){
		//当前登陆用户只能查询所属部门的快递代理其它应收单信息
		billReceivableAgencyDto.setReceivableOrgCode(currentInfo.getCurrentDeptCode());
		//部门名称
		billReceivableAgencyDto.setReceivableOrgName(currentInfo.getCurrentDeptName());
		//查询类型是快递代理的单据
		billReceivableAgencyDto.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_RECEIVABLE);
		//查询非红单的单据
		billReceivableAgencyDto.setIsRedBack(FossConstants.NO);
		//查询有效的单据
		String active = FossConstants.ACTIVE;
		billReceivableAgencyDto.setActive(active);
		//声明一个对象
		List<BillReceivableEntity> billReceivableEntities = null;
		if(StringUtils.equals(billReceivableAgencyDto.getQueryByTab(),SettlementConstants.TAB_QUERY_BY_DATE)){
			// 业务结束时间
			Date dateTemp = DateUtils.addDayToDate(billReceivableAgencyDto.getEndBusinessDate(), 1);
			billReceivableAgencyDto.setEndBusinessDate(dateTemp);
			//根据传入多条件参数获取一到多条应收单信息(不分页)
			billReceivableEntities = billLandReceivableAgencyDao
	    				.queryBillReceivableEntityParams(billReceivableAgencyDto);
		}
		
		if(StringUtils.equals(billReceivableAgencyDto.getQueryByTab(),SettlementConstants.TAB_QUERY_BY_RECEIVABLE_NO)){
			//调用公共方发处理应收单号
			String[] receivableNo = billReceivableAgencyDto.getReceivableNo().split(",");
			List<String> receivableNos = Arrays.asList(receivableNo);
			//根据传入的一到多个应收单号，获取一到多条应收单信息
			billReceivableEntities = billReceivableService
					.queryByReceivableNOs(receivableNos, active);
		}
		
		if(StringUtils.equals(billReceivableAgencyDto.getQueryByTab(),SettlementConstants.TAB_QUERY_BY_SOURCE_BILL_NO)){
			String[] sourceBillNo = billReceivableAgencyDto.getSourceBillNo().split(",");
			List<String> sourceBillNos = Arrays.asList(sourceBillNo);
			billReceivableAgencyDto.setSourceBillNos(sourceBillNos);
    		//航空正单号不为空，则按航空正单单号查询
			billReceivableEntities = billLandReceivableAgencyDao.queryBySourceBillNOs(billReceivableAgencyDto);
		}
		return billReceivableEntities;
	}
	
	
	/**
	 * 快递代理其他应收管理作废
	 * @author foss-guxinhua
	 * @date 2012-11-8 上午10:22:31
	 * @param billReceivableAgencyDto
	 * @return
	 * @see
	 */
	@Transactional
	public BillReceivableAgencyDto writeBackLandOtherBillReceivable (List<BillReceivableAgencyDto> billReceivableAgencyDtos,
			BillReceivableAgencyDto billReceivableAgencyDto, CurrentInfo currentInfo){
		//判断传入的参数是否为空
		if(CollectionUtils.isEmpty(billReceivableAgencyDtos)){
			throw new SettlementException("内部错误，应收代理成本对象为空!");
		}
		//循环拿应收单收单号放入应收单号集合
		List<String> receivableNos = new ArrayList<String>();
		for(BillReceivableAgencyDto receivableAgencyDto : billReceivableAgencyDtos){
			receivableNos.add(receivableAgencyDto.getReceivableNo());
		}
		//根据应收单号集合，load库中最新数据
		List<BillReceivableEntity> billReceivableEntities = billReceivableService
				.queryByReceivableNOs(receivableNos, FossConstants.ACTIVE);
		
		if (CollectionUtils.isEmpty(billReceivableEntities)) {
			throw new SettlementException("库中数据已改变，不能进行审核操作！");
		}
		
		//循环验证数据
		for(BillReceivableEntity billReceivableEntity : billReceivableEntities){
			// 1、验证客户编码是否为空 
			if (StringUtils.isEmpty(billReceivableEntity.getCustomerCode())) {
				throw new SettlementException("待作废数据的客户编码为空!");
			} 
		 
			//2,已审核的单据不能作废
			String appStatus = billReceivableEntity.getApproveStatus();
			if (StringUtils.equals(appStatus,SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE)) {
				throw new SettlementException("已审核的单据不能作废!");
			}
			
		}
		//循环作废传入数据
		for(BillReceivableEntity billReceivableEntity : billReceivableEntities){
			//修改为已作废
	    	billReceivableEntity.setActive(FossConstants.NO);
	    	//调用common下面的作废接口 
	    	billReceivableService.writeBackBillReceivable(billReceivableEntity,currentInfo);
		}
    		
    	//声明实例对账单dto
		StatementOfAccountUpdateDto statementOfAccountUpdateDto = new StatementOfAccountUpdateDto();
		//设置传入参数
		statementOfAccountUpdateDto.setReceivableEntityList(billReceivableEntities);
		//作废完成时，通知修改对账单及对账单明细信息
//		statementOfAccountService.updateStatementAndDetailForRedBack(statementOfAccountUpdateDto,currentInfo);
		
		//将单据信息、作废人、作废时间保存到操作日志表中
		OperatingLogEntity operatingLogEntity = new OperatingLogEntity();
		//循环插入日志
		for(BillReceivableEntity billReceivableEntity : billReceivableEntities){
			//设置日志单号
			operatingLogEntity.setOperateBillNo(billReceivableEntity.getReceivableNo());
			//设置操作日志类型
			operatingLogEntity
					.setOperateBillType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__LAND_STOWAGE_RECEIVABLE);
			//设置操作日志类型
			operatingLogEntity
					.setOperateType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__DISABLE);
			//插入日志接口
	    	operatingLogService.addOperatingLog(operatingLogEntity,currentInfo);
		}
		
		//加载库中数据进行返回
		billReceivableEntities = this.queryLandReceivablePage(billReceivableAgencyDto, currentInfo);
		billReceivableAgencyDto.setBillReceivableEntityList(billReceivableEntities);
		
		return billReceivableAgencyDto;
	}
	
	/**
     * 快递代理应收其他管理的审核 操作
     * @author foss-guxinhua
     * @date 2012-10-29 下午2:53:07
     * @param billReceivableEntityListFrom
     * @param billReceivableEntityListAll
     * @return
     * @see
     */
	@Transactional
	public BillReceivableAgencyDto auditLandOtherBillReceivable(List<BillReceivableAgencyDto> billReceivableAgencyDtos,
			BillReceivableAgencyDto billReceivableAgencyDto,CurrentInfo currentInfo){
		//判断传入的参数是否为空
		if(CollectionUtils.isEmpty(billReceivableAgencyDtos)){
			throw new SettlementException("内部错误，应收代理成本对象为空!");
		}
		//声明实例应收单号集合
		List<String> receivableNos = new ArrayList<String>();
		//循环拿应收单收单号
		for(BillReceivableAgencyDto receivableAgencyDto : billReceivableAgencyDtos){
			//把应收单号放入应收单号集合
			receivableNos.add(receivableAgencyDto.getReceivableNo());
		}
		//根据应收单号集合，load库中最新数据
		List<BillReceivableEntity> billReceivableEntities = billReceivableService
				.queryByReceivableNOs(receivableNos, FossConstants.ACTIVE);
		
		//判断库中最新数据
		if (CollectionUtils.isEmpty(billReceivableEntities)) {
			throw new SettlementException("库中数据已改变，不能进行审核操作！");
		}
		
		//循环校验数据
		for(BillReceivableEntity billReceivableEntity : billReceivableEntities){
			// 1、验证客户编码是否为空 
			if (StringUtils.isEmpty(billReceivableEntity.getCustomerCode())) {
				throw new SettlementException("待审核数据的客户编码为空!");
			} 
			//2,已审核的单据不能审核
			String appStatus = billReceivableEntity.getApproveStatus();
			if (StringUtils.equals(appStatus,SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE)) {
				throw new SettlementException("已审核的单据不能审核!");
			}
		}
		
		//调用审核接口
    	BillReceivableDto billReceivableDto = new BillReceivableDto();
    	
    	//批量传入list实体
    	billReceivableDto.setBillReceivables(billReceivableEntities);
    	
    	//审核时间
    	Date now = new Date();
    	billReceivableDto.setAuditDate(now);
    	
    	//修改审核状态为已审核
    	billReceivableDto.setApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE);
    	
    	//设置审核人code
    	billReceivableDto.setAuditUserCode(currentInfo.getEmpCode());
    	//设置审核人name
    	billReceivableDto.setAuditUserName(currentInfo.getEmpName());
    	
    	//快递代理其它应收单必须为有效的数据
    	billReceivableDto.setActive(FossConstants.YES);
    	//快递代理其它应收单必须为非红单的数据
    	billReceivableDto.setIsRedBack(FossConstants.NO);
    	//快递代理其它应收单类型必须为快递代理
    	billReceivableDto.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_RECEIVABLE);
    	
    	//调用common下面的审核接口
    	billReceivableService.auditLandOtherBillReceivable(billReceivableDto,currentInfo);

    	//将单据信息、反审核人、反审核时间保存到操作日志表中
    	//声明实例日志对象
		OperatingLogEntity operatingLogEntity = new OperatingLogEntity();
		//循环插入日志
		for(BillReceivableEntity billReceivableEntity : billReceivableEntities){
			//设置操作日志单号
			operatingLogEntity.setOperateBillNo(billReceivableEntity.getReceivableNo());
			//设置操作日志类型
			operatingLogEntity
					.setOperateBillType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__LAND_STOWAGE_RECEIVABLE);
			//设置操作类型
			operatingLogEntity
					.setOperateType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__AUDIT);
			//调用插入日志接口
			operatingLogService.addOperatingLog(operatingLogEntity,currentInfo);
		}
		
		//加载库中数据进行返回
		billReceivableEntities = this.queryLandReceivablePage(billReceivableAgencyDto, currentInfo);
		billReceivableAgencyDto.setBillReceivableEntityList(billReceivableEntities);
		
		return billReceivableAgencyDto;
    }
	/**
	 * 反审核快递代理其他应收单
	 * @author foss-guxinhua
	 * @date 2012-11-6 上午9:49:41
	 * @param billReceivableAgencyDto
	 * @return
	 * @see
	 */
	@Transactional
	public BillReceivableAgencyDto reverseAuditLandOtherBillReceivable(List<BillReceivableAgencyDto> billReceivableAgencyDtos,
			BillReceivableAgencyDto billReceivableAgencyDto,CurrentInfo currentInfo){
		//判断传入的参数是否为空
		if(CollectionUtils.isEmpty(billReceivableAgencyDtos)){
			throw new SettlementException("内部错误，应收代理成本对象为空!");
		}
		
		//声明实例应收单集合
		List<String> receivableNos = new ArrayList<String>();
		//循环拿应收单收单号
		for(BillReceivableAgencyDto receivableAgencyDto : billReceivableAgencyDtos){
			//放入应收单号集合
			receivableNos.add(receivableAgencyDto.getReceivableNo());
		}
		//根据应收单号集合，load库中最新数据
		List<BillReceivableEntity> billReceivableEntities = billReceivableService
				.queryByReceivableNOs(receivableNos, FossConstants.ACTIVE);
		
		//判断库中最新数据是否为空
		if (CollectionUtils.isEmpty(billReceivableEntities)) {
			throw new SettlementException("库中数据已改变，不能进行审核操作！");
		}
		
		//循环验证应收单据
		for(BillReceivableEntity billReceivableEntity : billReceivableEntities){
			// 1、验证客户编码是否为空 
			if (StringUtils.isEmpty(billReceivableEntity.getCustomerCode())) {
				throw new SettlementException("待审核数据的客户编码为空!");
			} 
			//2,未审核的单据不能反审核
			String appStatus = billReceivableEntity.getApproveStatus();
			if (StringUtils.equals(appStatus,SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__NOT_AUDIT)) {
				throw new SettlementException("未审核的单据不能反审核!");
			}
			//3,已核销金额大于0的单据不能被反审核 
			if(billReceivableEntity.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0){
				throw new SettlementException("已参与核销的单据不能反审核!");
			}
		}
		//调用审核接口
    	BillReceivableDto billReceivableDto = new BillReceivableDto();
    	
    	//批量传入list实体
    	billReceivableDto.setBillReceivables(billReceivableEntities);
    	
    	//反审核时间 
    	billReceivableDto.setAuditDate(null);

    	//修改审核状态为未审核
    	billReceivableDto.setApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__NOT_AUDIT);
    	//设置审核人code为空
    	billReceivableDto.setAuditUserCode("");
    	//设置审核人name为空
    	billReceivableDto.setAuditUserName("");
    	
    	billReceivableDto.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
    	//2,快递代理其它应收单必须为有效数据
    	billReceivableDto.setActive(FossConstants.YES);
    	//2,快递代理其它应收单必须为非红单的数据
    	billReceivableDto.setIsRedBack(FossConstants.NO);
    	billReceivableDto.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_RECEIVABLE);
    	//调用common下面的反审核接口
    	billReceivableService.reverseAuditLandOtherBillReceivable(billReceivableDto,currentInfo);
    	
    	//声明实例对账单dto
		StatementOfAccountUpdateDto statementOfAccountUpdateDto = new StatementOfAccountUpdateDto();
		//设置传入参数
		statementOfAccountUpdateDto.setReceivableEntityList(billReceivableEntities);
		//反审核完成时，通知修改对账单及对账单明细信息
		statementOfAccountService.updateStatementAndDetailForRedBack(statementOfAccountUpdateDto,currentInfo);
		

    	//将单据信息、反审核人、反审核时间保存到操作日志表中
		//声明实例日志对象
		OperatingLogEntity operatingLogEntity = new OperatingLogEntity();
		//循环插入日志
		for(BillReceivableEntity billReceivableEntity : billReceivableEntities){
			//设置日志单号
			operatingLogEntity.setOperateBillNo(billReceivableEntity.getReceivableNo());
			//设置操作日志类型
			operatingLogEntity
					.setOperateBillType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__LAND_STOWAGE_RECEIVABLE);
			//设置操作类型
			operatingLogEntity
					.setOperateType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__REVERSE_AUDIT);
			//调用插入日志接口
			operatingLogService.addOperatingLog(operatingLogEntity,currentInfo);
		}
		
		//加载库中数据进行返回
		billReceivableEntities = this.queryLandReceivablePage(billReceivableAgencyDto, currentInfo);
		billReceivableAgencyDto.setBillReceivableEntityList(billReceivableEntities);
				
		return billReceivableAgencyDto;
    }
    /**
     * 新增快递代理其它应收款
     * @author foss-guxinhua
     * @date 2012-10-29 下午5:44:42
     * @see
     */
	@Transactional
	public void addBillReceivable(BillReceivableAgencyDto billReceivableAgencyDto,CurrentInfo currentInfo){
		BillReceivableEntity billReceivableEntity = new BillReceivableEntity();
		//判断传入的参数是否为空
		if(billReceivableAgencyDto == null){
			throw new SettlementException("内部异常，传入参数为空，不能新增快递代理其他应收信息!");
    	}
		
		//获取参数中的客户编码
		String customerCode = billReceivableAgencyDto.getCustomerCode();
		//判断参数中的航空正单号是否为空
		//如果运单号不为空，则获取界面单号，为空则获取默认单号
		if(StringUtils.isNotEmpty(billReceivableAgencyDto.getWaybillNo())){
			
			//如果需要填入 “运单号”，则需检验运单号和快递代理在外发单中存在
//			if(!ldpExternalBillService.existHasAuditedLdpExternalBillForStl( billReceivableAgencyDto.getWaybillNo(), customerCode)){
//				throw new SettlementException("运单号和快递代理在已审核外发单中不存在，不能新增快递代理其他应收信息！");
//			}
			/**
			 * @author 325369
			 * @date  2016-07-11 上午08:36:42
			 * 校验运单号和快递代理在外发单中是否存在，增加请求悟空系统接口判断
			 */
			if(!ldpExternalBillService.existHasAuditedLdpExternalBillForStl( billReceivableAgencyDto.getWaybillNo(), customerCode)){
				/*
				 * 添加条件，结算代码在悟空系统之前上线，对调悟空系统的接口，添加开关 2016-08-09
				 * true:发送请求  false：不发送
				 */
				boolean flag = configurationParamsService.queryStlSwitch4Ecs();
				if (!flag) {
					throw new SettlementException("综合模块配合快递接口开关未打开或者未配置--不能新增应收");
				}	
				if(!checkLdpExternalBillClient.sendLdpExternalBillMsgToWK( billReceivableAgencyDto.getWaybillNo(), customerCode)){
					throw new SettlementException("运单号和快递代理在已审核外发单中不存在，不能新增快递代理其他应收信息！");
				}
			}
			
			billReceivableEntity.setWaybillNo(billReceivableAgencyDto.getWaybillNo());
			billReceivableEntity.setSourceBillNo(billReceivableAgencyDto.getWaybillNo());
			//根据运单号在接送货查询对应的运单信息，拿到收货部门编码
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(billReceivableAgencyDto.getWaybillNo());
			if(waybillEntity != null){
				/*
				 * 3.60特惠件 快递代理新增加的一种三级产品
				 * 
				 * 10562 2014年8月6日 上午10:25:36
				 */
		    	//运输性质 包裹
		    	billReceivableEntity.setProductCode(waybillEntity.getProductCode());
    			//根据收货部门编码调用综合的接口，获取相应的信息取到收货部门名称
    			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getCreateOrgCode());
    			//设置运单的收货部门编码对应的应收单的出发部门编码
				billReceivableEntity.setOrigOrgCode(waybillEntity.getCreateOrgCode());
				//设置运单的收货部门名称对应的应收单的出发部门名称
				billReceivableEntity.setOrigOrgName(orgAdministrativeInfoEntity.getName());
				
				//设置快递代理其他应收管理的来源单据类型   运单
		    	billReceivableEntity.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__MANUAL);
			}else {
				throw new SettlementException("运单号不合法，请修改确认后再操作！");
			}
		}else{
			throw new SettlementException("运单号不能为空，请修改确认后再操作！");
		}
		
		//取dto中的金额
		BigDecimal amount = billReceivableAgencyDto.getAmount();
		final int NumberThree = 300;
		//判断金额是否为空
		if(amount == null){
			throw new SettlementException("内部异常，传入应收金额参数为空，不能新增快递代理其他应收信息!");
		}
		
		//声明金额字符串
		//获取应收金额最大配置参数
		String amountStr = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.STL_LDP_ADD_MAX_AMOUNT);
		BigDecimal amountBig = new BigDecimal(amountStr);
		
		//判断应收的最大最小值
		if(amount.compareTo(BigDecimal.ZERO) != 1
				|| amount.compareTo(amountBig) != -1  ){
			throw new SettlementException("应收金额必须输入大于0且小于"+ amountStr +"的金额!");
			
		}
		//备注长度小于300个汉字
		if(billReceivableAgencyDto.getNotes().length() >= NumberThree){
			throw new SettlementException("备注信息长度小于300个汉字（包含空格和标点符号）!");
		}
		
    	getBillReceivable(billReceivableAgencyDto, currentInfo,
				billReceivableEntity, customerCode);

    	//调用新增日志接口
    	billReceivableService.addBillReceivable(billReceivableEntity,currentInfo);
    }

	private void getBillReceivable(
			BillReceivableAgencyDto billReceivableAgencyDto,
			CurrentInfo currentInfo, BillReceivableEntity billReceivableEntity,
			String customerCode) {
		//设置快递代理其他应收管理的代理、航空公司名称、代理信息为界面输入信息
		billReceivableEntity.setCustomerName(billReceivableAgencyDto.getCustomerName());
		//设置快递代理其他应收管理的代理、航空公司编码、代理信息为界面输入信息
    	billReceivableEntity.setCustomerCode(customerCode);
    	
    	//设置id
    	billReceivableEntity.setId(UUIDUtils.getUUID());
    	//设置快递代理其他应收管理的应收单号,应收单单号系统自动生成，生成规则“YS93+7位流水号” 
    	billReceivableEntity.setReceivableNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YS93));
    	
		// 支付方式        月结
    	billReceivableEntity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);
    	
    	//设置快递代理其他应收管理的系统生成方式
    	billReceivableEntity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
    	
    	//设置快递代理其他应收管理的单据子类型
    	billReceivableEntity.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_RECEIVABLE);
    	
    	//设置快递代理其他应收管理的代理、航空公司名称、代理信息为界面输入信息
    	billReceivableEntity.setCustomerName(billReceivableAgencyDto.getCustomerName());
    	//代理编码
    	billReceivableEntity.setCustomerCode(billReceivableAgencyDto.getCustomerCode());
		
		//催款部门编码
		billReceivableEntity.setDunningOrgCode(currentInfo.getCurrentDeptCode());
		//催款部门名称
		billReceivableEntity.setDunningOrgName(currentInfo.getCurrentDeptName());
		
		billReceivableEntity.setDestOrgCode(currentInfo.getCurrentDeptCode());
		billReceivableEntity.setDestOrgName(currentInfo.getCurrentDeptName());
		
		//设置应收部门编码
		billReceivableEntity.setReceivableOrgCode(currentInfo.getCurrentDeptCode());
		//设置应收部门名称
    	billReceivableEntity.setReceivableOrgName(currentInfo.getCurrentDeptName());
    	
    	// 获取当前选择的应收部门组织的的实体信息
		OrgAdministrativeInfoEntity generatOrgEntity = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCode(currentInfo.getCurrentDeptCode());
		if(generatOrgEntity == null){
			throw new SettlementException("当前选择的应收部门组织信息为空！");
		}
		//判断，当前登陆用户不属于外场部门，
		if(!StringUtils.equals(generatOrgEntity.getTransferCenter(), FossConstants.YES)){
			throw new SettlementException("当前部门不是顶级外场，不能新增快递代理其他应收信息!");
		}
		
		//设置部门收入部门编码
		billReceivableEntity.setGeneratingOrgCode(currentInfo.getCurrentDeptCode());
		//设置部门收入部门名称
		billReceivableEntity.setGeneratingOrgName(currentInfo.getCurrentDeptName());
		// 当前登录用户操作公司code
		billReceivableEntity.setGeneratingComCode(generatOrgEntity.getSubsidiaryCode());
		// 当前登录用户操作公司name
		billReceivableEntity.setGeneratingComName(generatOrgEntity.getSubsidiaryName());
		
    	//设置快递代理其他应收管理的金额
    	billReceivableEntity.setAmount(billReceivableAgencyDto.getAmount());
    	
    	//设置快递代理其他应收管理的未核销金额
    	billReceivableEntity.setUnverifyAmount(billReceivableAgencyDto.getAmount());
    	
    	//设置快递代理其他应收管理的核销金额
    	billReceivableEntity.setVerifyAmount(BigDecimal.ZERO);
    	
    	//设置审核状态
    	billReceivableEntity.setApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__NOT_AUDIT);
    	
    	//设置快递代理其他应收管理的币种
    	billReceivableEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
    	
    	//业务日期、记账日期为系统当前时间；创建时间默认为系统当前时间
    	Date now = new Date();
		//设置快递代理其他应收管理的业务时间
    	billReceivableEntity.setBusinessDate(now);
    	
		//设置快递代理其他应收管理的记账时间
    	billReceivableEntity.setAccountDate(now);
    	
    	// 变更
    	billReceivableEntity.setConrevenDate(now);
    	
    	//新增的快递代理其他应收单单据默认为有效的单据
    	billReceivableEntity.setActive(FossConstants.ACTIVE);
    	//新增的快递代理其他应收单单据默认为非红单的单据
    	billReceivableEntity.setIsRedBack(FossConstants.NO);
    	//新增的快递代理其他应收单单据默认为非初始化的单据
    	billReceivableEntity.setIsInit(FossConstants.NO);
    	//版本号
    	billReceivableEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
    	//默认单号
    	billReceivableEntity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
    	
    	//备注
    	billReceivableEntity.setNotes(billReceivableAgencyDto.getNotes());
    	
    	billReceivableEntity.setExpressOrigOrgCode(currentInfo.getCurrentDeptCode());
		billReceivableEntity.setExpressOrigOrgName(currentInfo.getCurrentDeptName());
		billReceivableEntity.setExpressDestOrgCode(currentInfo.getCurrentDeptCode());
		billReceivableEntity.setExpressDestOrgName(currentInfo.getCurrentDeptName());

		/*
		 * 税务改造需求
		 * 
		 * 快递代理其他应收发票标记为 02-非运输专票
		 * 
		 * 杨书硕 2013-11-5 上午9:58:56
		 */
		
		billReceivableEntity.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);
	}

	
	/**
	 * @return  the billReceivableAgencyDao
	 */
	public IBillLandReceivableAgencyDao getBillLandReceivableAgencyDao() {
		return billLandReceivableAgencyDao;
	}

	
	/**
	 * @param billReceivableAgencyDao the billReceivableAgencyDao to set
	 */
	public void setBillLandReceivableAgencyDao(
			IBillLandReceivableAgencyDao billLandReceivableAgencyDao) {
		this.billLandReceivableAgencyDao = billLandReceivableAgencyDao;
	}

	
	/**
	 * @return  the billReceivableService
	 */
	public IBillReceivableService getBillReceivableService() {
		return billReceivableService;
	}

	
	/**
	 * @param billReceivableService the billReceivableService to set
	 */
	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	
	/**
	 * @return  the settlementCommonService
	 */
	public ISettlementCommonService getSettlementCommonService() {
		return settlementCommonService;
	}

	
	/**
	 * @param settlementCommonService the settlementCommonService to set
	 */
	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	
	/**
	 * @return  the airWaybillService
	 */
	public IAirWaybillService getAirWaybillService() {
		return airWaybillService;
	}

	
	/**
	 * @param airWaybillService the airWaybillService to set
	 */
	public void setAirWaybillService(IAirWaybillService airWaybillService) {
		this.airWaybillService = airWaybillService;
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
	 * @return  the statementOfAccountService
	 */
	public IStatementOfAccountService getStatementOfAccountService() {
		return statementOfAccountService;
	}

	
	/**
	 * @param statementOfAccountService the statementOfAccountService to set
	 */
	public void setStatementOfAccountService(
			IStatementOfAccountService statementOfAccountService) {
		this.statementOfAccountService = statementOfAccountService;
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
