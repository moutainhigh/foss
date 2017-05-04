package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.server.service.impl.complex.OrgAdministrativeInfoComplexService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IStatementCreatDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.IStatementCreatService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.InvoiceManagementAddDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.InvoiceManagementAddListDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

public class StatementCreatService implements IStatementCreatService {
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(StatementCreatService.class);
	
	/**
	 * 注入生成序列Service
	 */
	private ISettlementCommonService settlementCommonService;
	
	/**
	 * 查询应收单与对账单的Dao
	 */
	private IStatementCreatDao statementCreatDao;
	
	/**
	 * 查询部门标杆编码的service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 查询部门编码
	 */
	private OrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/*
	 * 查询部门
	 * */
	
	private ISaleDepartmentService saleDepartmentService;
	


	@Override
	public InvoiceManagementAddDto queryForInvoiceStatementAddStatementMake(
			InvoiceManagementAddDto queryDto,
			CurrentInfo info) {
		// TODO Auto-generated method stub
		// 参数dto不能为空
		if (queryDto == null) {
			// 提示查询DTO为空
			throw new SettlementException("查询DTO为空！", "");
		}
		// 记录日志
		logger.info("制作对账单，enter service ,客户编码：" + queryDto.getCustomerCode());
		// 当前登录用户不能为空
		if (info == null) {
			// 提示当前登录用户信息为空
			throw new SettlementException("当前登录用户信息为空！", "");
		}
		List<String> subsidiaryDept=null;
		if (queryDto.getCurrentDeptNo() == null||"".equals(queryDto.getCurrentDeptNo())) {
			// 提示当前登录用户信息为空
			throw new SettlementException("当前部门信息为空！", "");
		}else{
			//判断当前登录是不是事业合伙人业务培训小组
			if(!("事业合伙人业务培训小组").equals(info.getCurrentDeptName()) && info.getCurrentDeptName()!="事业合伙人业务培训小组"){
				//查询子部门所有编码
				subsidiaryDept=new ArrayList<String>();
				subsidiaryDept.add(queryDto.getCurrentDeptNo());
				List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(queryDto.getCurrentDeptNo());
			if(CollectionUtils.isNotEmpty(orgList)){
					for(OrgAdministrativeInfoEntity entity:orgList){
						subsidiaryDept.add(entity.getCode());
					}
				}
			}
		}
		//查询出的应收单集合
		List<InvoiceManagementAddListDto> receivables=new ArrayList<InvoiceManagementAddListDto>();
		List<InvoiceManagementAddListDto> tempByWaybillNumber;
		// 存放返回的对账单明细及对账单
		InvoiceManagementAddDto resultDto=new InvoiceManagementAddDto();
		
		//boolean unRepeat=true;
		/**
		 * 首先进行判断，用户是按照客户制作对账单还是按单号制作对账单
		 */
		// 如果是按单号制作，则需要对前台传入的单号集合进行识别转换，转换成按 财务单据编号查询和来源单号查询集合
		if (SettlementConstants.TAB_QUERY_BY_BILL_NO.equals(queryDto
				.getQueryPage())) {
			//校验信息是否完整
			if (queryDto.getBillDetailNos() == null
					|| queryDto.getBillDetailNos().length == 0) {
				throw new SettlementException("按单制作对账单时，输入的单号不能为空");
			}
			List<String> waybills=new ArrayList<String>();
			String[] waybillNos=queryDto.getBillDetailNos();
			for (int i = 0; i < waybillNos.length; i++) {
				if(waybillNos[i]!=null&&!"".equals(waybillNos[i])){
					waybills.add(waybillNos[i]);
				}
			}
			tempByWaybillNumber=statementCreatDao.queryYSMakeSOAByWaybillsNumber(waybills,subsidiaryDept,queryDto.getManycheck());
			//过滤出查询不出的其它单号再次查询
			if(tempByWaybillNumber!=null){
				for(int i=0;i<tempByWaybillNumber.size();i++){
					String imadl=tempByWaybillNumber.get(i).getWaybillNumber();
					for(int j=0;j<waybills.size();j++){
						if(waybills.get(j).equals(imadl)){
							waybills.remove(j);
							j--;
						}
					}
				}
			}
			if(waybills.size()!=0){
				List<InvoiceManagementAddListDto> tempByReceivableNumber=statementCreatDao.queryYSMakeSOAByReceivableNumber(waybills,subsidiaryDept,queryDto.getManycheck());
				//将重复单号去除
				for(int i=0;i<tempByWaybillNumber.size();i++){
					String waybillNumberByI=tempByWaybillNumber.get(i).getWaybillNumber();
					//培训会务没有运单号
					if(waybillNumberByI!=null){
					String receivableNumberByI=tempByWaybillNumber.get(i).getReceivableNumber();
					for(int j=0;j<tempByReceivableNumber.size();j++){
						if(waybillNumberByI.equals(tempByReceivableNumber.get(j).getWaybillNumber())
									&&receivableNumberByI.equals(tempByReceivableNumber.get(j).getReceivableNumber())){
								tempByReceivableNumber.remove(j);
								j--;
								break;
							}
						}
					}
				}
				//讲部门不对的去掉
				//生成完成应收单
				tempByWaybillNumber.addAll(tempByReceivableNumber);
			}
			receivables=tempByWaybillNumber;
		}
		// 否则为按客户日期制作
		else {
			//校验信息是否完整
			if(queryDto.getCustomerCode()==null||"".equals(queryDto.getCustomerCode())){
				throw new SettlementException("按单制作对账单时，输入的客户信息不能为空");
			}
			if(queryDto.getPeriodBeginDate()==null){
				throw new SettlementException("按单制作对账单时，输入的起始日期不能为空");
			}
			if(queryDto.getPeriodEndDate()==null){
				throw new SettlementException("按单制作对账单时，输入的结束日期不能为空");
			}
			if(queryDto.getManycheck()==null){
				throw new SettlementException("按单制作对账单时，输入的单据类型不能为空");
			}
			// 调用私有方法获取期初和本期明细数据
			Map<String, Object> queryCondition = new HashMap<String, Object>();
			queryCondition.put("customerCode",queryDto.getCustomerCode());
			queryCondition.put("beginTime",queryDto.getPeriodBeginDate());
			queryCondition.put("endTime",queryDto.getPeriodEndDate());
			queryCondition.put("list",subsidiaryDept);
			queryCondition.put("Manycheck",queryDto.getManycheck());
			//查询应收单并将结果返还给resultDto
			receivables = statementCreatDao.queryYSMakeSOAByCustomer(queryCondition);
		}
		//判断单子类型

		for(int i=0;i<receivables.size();i++){
			SaleDepartmentEntity saleDept = saleDepartmentService.querySaleDepartmentInfoByCode(receivables.get(i).getDestOrgCode());
			if (null != saleDept) {
				//判断是不是合伙人
				if (FossConstants.YES.equals(saleDept.getIsLeagueSaleDept())) {
					//判断单子类型是否为空
					if(receivables.get(i).getSubTypesOfDocuments()!=null){
						if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_RECEIVABLE.equals(receivables.get(i).getSubTypesOfDocuments())||SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__ORIGINAL_RECEIVABLE.equals(receivables.get(i).getSubTypesOfDocuments())){
							receivables.remove(i);
							i--;
						}		
					}
				}
			}
		}
	
		/**
		 *  将查询出的应收单的共同信息保存在resultDto中
		 */
		if(receivables!=null&&receivables.size()!=0){
			//定义委托派费应收
			
			//合伙人委托派费应收去掉到达部门非直营的应收单
			for(int i=0;i<receivables.size();i++){
				InvoiceManagementAddListDto dto=receivables.get(i);
				if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_RECEIVABLE.equals(dto.getSubTypesOfDocuments())
						&&orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(receivables.get(i).getDestOrgCode())==null){
					receivables.remove(i);
					i--;
				}
			}
			InvoiceManagementAddListDto dto=receivables.get(0);
			//制单时间
			resultDto.setCreateTime(new Date());
			//客户编码
			resultDto.setCustomerCode(dto.getCustomerCode());
			//计算账期最大时间，最小时间，总金额
			dealWithResultDto(resultDto,receivables);
			//客户名称
			resultDto.setCustomerName(dto.getCustomerName());
			//部门编码
			resultDto.setDepartmentCode(queryDto.getCurrentDeptNo());
			//客户标杆编码
			OrgAdministrativeInfoEntity org=orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(queryDto.getCurrentDeptNo());
			resultDto.setSectorBenchmarkingEncoding(org==null?null:org.getUnifiedCode());
			//所属部门
			resultDto.setCreateOrgName(org==null?null:org.getName());
			//所属公司赋值
			resultDto.setCompanyName(org==null?null:org.getSubsidiaryName());
			resultDto.setList(receivables);
		}
		// 记录日志
		if(resultDto.getList()!=null){
			logger.debug("制作对账单时，查询对账单明细，本期明细条数："
					+ resultDto.getList().size());
		}else{
			logger.debug("制作对账单时，查询对账单明细，本期明细条数："
					+ 0);
		}
		// 返回对账单制作dto
		return resultDto;
	}
	
	//计算账期最大时间，最小时间，总金额
	private void dealWithResultDto(InvoiceManagementAddDto resultDto,
			List<InvoiceManagementAddListDto> receivables) {
		// TODO Auto-generated method stub
		//最早时间
		Date beginTime=new Date();
		//最晚时间
		Date endTime=new Date();
		//总金额
		Double totleFee=0d;
		//比较的业务时间
		Date tempTime;
		for(InvoiceManagementAddListDto dto:receivables){
			//重新赋值最早时间
			tempTime=dto.getBusinessDate();
			if(beginTime==null){
				beginTime=tempTime;
			}else{
				if(beginTime.compareTo(tempTime)>0){
					beginTime=tempTime;
				}
			}
			//重新赋值最晚时间
			if(endTime==null){
				endTime=tempTime;
			}else{
				if(endTime.compareTo(tempTime)<0){
					endTime=tempTime;
				}
			}
			//各运单总金额保留两位小数
			double tempTotle=dto.getTotleFee();
			BigDecimal bg = new BigDecimal(tempTotle);
	        tempTotle = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			dto.setTotleFee(tempTotle);
			//计算总金额
			totleFee+=dto.getTotleFee();
		}
		resultDto.setPeriodBeginDate(beginTime);
		resultDto.setPeriodEndDate(endTime);
		resultDto.setTotleFee(totleFee);
	}
	
	/**
	 * 制作对账单时，保存对账单及对账单明细单据
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-17 下午3:46:35
	 */
	@Transactional
	@Override
	public InvoiceManagementAddDto addStatement(InvoiceManagementAddDto resultDto, CurrentInfo info) {
		//参数dto不能为空
		if (resultDto == null) {
			//提示制作对账单时,传入的对账单实体为空
			throw new SettlementException("制作对账单时,传入的对账单实体为空！", "");
		}
		logger.info("制作发票对账单开始。。。。");
		List<String> ids=new ArrayList<String>();
		String[] billIds=resultDto.getBillDetailNos();
		for(int i=0;i<billIds.length;i++){
			String tempId=billIds[i];
			if(tempId!=null&&!"".equals(tempId)){
				ids.add(tempId);
			}
		}
		if(CollectionUtils.isNotEmpty(ids)){
			// 批量更新，大于1000条，拆分多组执行
			List<List<String>> splitList = com.deppon.foss.util.CollectionUtils
					.splitListBySize(ids,
							FossConstants.ORACLE_MAX_IN_SIZE);
			for (List<String> list : splitList) {
				//按ID查询应收单是否已经开对账单，若开，则抛出对应已开出对账单的应收单单号的异常并显示在前端界面
				List<String> waybillNos=statementCreatDao.queryYSByIds(list);
				if(CollectionUtils.isNotEmpty(waybillNos)){
					String errorMessage = StringUtils.collectionToCommaDelimitedString(waybillNos);
					throw new SettlementException("当前运单中，有运单已开对账单，单号为："+errorMessage);
				}
			}
			//查找当前对账单的最大单号
			String statementNo=String.valueOf(statementCreatDao.queryMaxHhStatementNo()+1);
			int currentLength=statementNo.length();
			for(int i=currentLength;statementNo.length()<9;i++){
				statementNo="0"+statementNo;
			}
			//生成没有HH的对账单号
			resultDto.setStatementNumberWithoutHh(Integer.valueOf(statementNo));
			//生成对账单号
			statementNo="HH"+statementNo;
			resultDto.setStatementBillNo(statementNo);
			//生成制单时间
			resultDto.setCreateTime(new Date());
			//生成发票标记（写死的）
			resultDto.setInvoiceMark("02");
			//生成运输性质（写死的）
			resultDto.setTransportProperties("02");
			//发票状态
			resultDto.setInvoiceStatus("notApply");
			//生成对账单
			statementCreatDao.makeHhStatement(resultDto);
			//修改应收单中相应的对账单号为新的单号，waybills经修改后指定的是应收单的ID，需注意
			Map map=new HashMap();
			map.put("statementNumberUpdate", statementNo);
			for (List<String> list : splitList) {
				map.put("list", list);
				statementCreatDao.updateForDealWithIs(map);
			}
			logger.info("新增对账单 enter service；客户编码："+ resultDto.getCustomerCode()+ "本期明细条数"+ resultDto.getBillDetailNos().length);
		}
		return resultDto;
	}

	private List<InvoiceManagementAddListDto> validateForSaveDetail(
			List<String> list,
			String statementNo, CurrentInfo info) {
		// TODO Auto-generated method stub
		// 记录日志
		logger.info("操作未确认的对账单时校验明细单据是否发生了变化,enter service..");
		List<InvoiceManagementAddListDto> soadList = new ArrayList<InvoiceManagementAddListDto>();
		// 如果明细单据发生更改变为无效状态或发生核销则保存单号进入该集合
		List<String> hasChangedNosList = new ArrayList<String>();
		// 记录日志
		logger.info("发生异常的单号集合条数为" + hasChangedNosList.size());
		if (StringUtil.isNotEmpty(statementNo)) {
			// 将对账单号批量更新到原始单据上(需要修改)
//			updateDetailBillStatementBillNo(list,statementNo, info);
		}
		// 记录日志
		logger.info("保存对账单时校验明细单据是否发生了变化,successfully exit service..");
		return soadList;
	}

	//查询对账单集合
	@Override
	public List<InvoiceManagementAddDto> queryStatements(
			InvoiceManagementAddDto queryDto) {
		// TODO Auto-generated method stub
		//查询当前允许查看的部门对账单
		List<OrgAdministrativeInfoEntity> allowOrgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(queryDto.getCurrentDeptNo());
		List<String> allowDepts=new ArrayList<String>();
		if(allowOrgList!=null && allowOrgList.size()!=0){
			for(OrgAdministrativeInfoEntity entity:allowOrgList){
				allowDepts.add(entity.getCode());
			}
		}
		//返回的对账单实体
		List<InvoiceManagementAddDto> list=new ArrayList<InvoiceManagementAddDto>();
		//按客户查询
		if (SettlementConstants.TAB_QUERY_BY_DATE.equals(queryDto.getQueryPage())) {
			Map<String,Object> map=new HashMap<String, Object>();
			List<String> departmentCodes=new ArrayList<String>();
			if(queryDto.getDepartmentCode()!=null){
				departmentCodes.add(queryDto.getDepartmentCode());
				List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(queryDto.getDepartmentCode());
				if(CollectionUtils.isNotEmpty(orgList)){
					for(OrgAdministrativeInfoEntity entity:orgList){
						departmentCodes.add(entity.getCode());
					}
				}
			}else if(queryDto.getSmallRegion()!=null){
				List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(queryDto.getSmallRegion());
				if(CollectionUtils.isNotEmpty(orgList)){
					for(OrgAdministrativeInfoEntity entity:orgList){
						departmentCodes.add(entity.getCode());
					}
				}
			}else if(queryDto.getBigRegion()!=null){
				List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(queryDto.getBigRegion());
				if(CollectionUtils.isNotEmpty(orgList)){
					for(OrgAdministrativeInfoEntity entity:orgList){
						departmentCodes.add(entity.getCode());
					}
				}
			}
			//过滤出不允许查询的地址
			for(int i=0;i<departmentCodes.size();i++){
				String tempCode=departmentCodes.get(i);
				boolean flag=true;
				for(int j=0;j<allowDepts.size();j++){
					if(tempCode.equals(allowDepts.get(j))){
						flag=false;
						break;
					}
				}
				if(flag){
					departmentCodes.remove(i);
					i--;
				}
			}
			if(departmentCodes==null||departmentCodes.size()==0){
				list=null;
			}else{
				map.put("regionList", departmentCodes);
				map.put("beginTime", queryDto.getPeriodBeginDate());
				map.put("endTime", queryDto.getPeriodEndDate());
				map.put("customerCode", queryDto.getCustomerCode());
				String invoiceStatus=null;
				if("all".equals(queryDto.getInvoiceStatus())||"".equals(queryDto.getInvoiceStatus())){
					invoiceStatus=null;
				}else{
					invoiceStatus=queryDto.getInvoiceStatus();
				}
				map.put("invoiceStatus", invoiceStatus);
				list=statementCreatDao.queryhhStatementForCustomer(map);
			}
		//按单号查询
		} else if(SettlementConstants.TAB_QUERY_BY_BILL_NO.equals(queryDto.getQueryPage())){
			List<String> waybills=new ArrayList<String>();
			String[] waybillNos=queryDto.getBillDetailNos();
			for(int i=0;i<waybillNos.length;i++){
				if(waybillNos[i]!=null&&!"".equals(waybillNos[i])){
					waybills.add(waybillNos[i]);
				}
			}			
			list=statementCreatDao.queryhhStatementForWaybillNo(waybills,allowDepts);
		//按对账单号查询	
		} else if(SettlementConstants.TAB_QUERY_BY_DZ_BILL_NO.equals(queryDto.getQueryPage())){
			List<String> waybills=new ArrayList<String>();
			String[] waybillNos=queryDto.getStatementNos();
			for(int i=0;i<waybillNos.length;i++){
				if(waybillNos[i]!=null&&!"".equals(waybillNos[i])){
					waybills.add(waybillNos[i]);
				}
			}
			list=statementCreatDao.queryhhStatementForStatementNo(waybills,allowDepts);
		}
		return list;
	}
	
	//删除对账单
	@Override
	public void deleteStatementNo(String[] statementNos) {
		// TODO Auto-generated method stub
		//修改应收单的所有合伙人对账单号为空
		for(String statementNo:statementNos){
			if(statementNo!=null&&!"".equals(statementNo)){
				statementCreatDao.deleteStatementNoForReceivable(statementNo);
				//删除对账单号
				statementCreatDao.deleteStatement(statementNo);
			}
		}
	}
	
	public ISettlementCommonService getSettlementCommonService() {
		return settlementCommonService;
	}

	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	public IStatementCreatDao getStatementCreatDao() {
		return statementCreatDao;
	}

	public void setStatementCreatDao(IStatementCreatDao statementCreatDao) {
		this.statementCreatDao = statementCreatDao;
	}

	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public OrgAdministrativeInfoComplexService getOrgAdministrativeInfoComplexService() {
		return orgAdministrativeInfoComplexService;
	}

	public void setOrgAdministrativeInfoComplexService(
			OrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	//修改合伙人对账单状态
	@Override
	public int updateInvoiceState(Map<String, Object> receiveMap) {
		// TODO Auto-generated method stub
		int flag=0;
		flag = statementCreatDao.updateInvoiceState(receiveMap);
		return flag;
	}

	@Override
	public InvoiceManagementAddDto queryReceivablesByStatementBillNo(
			String statementBillNo) {
		// TODO Auto-generated method stub
		InvoiceManagementAddDto dto=new InvoiceManagementAddDto();
		dto.setList(statementCreatDao.queryReceivablesByStatementBillNo(statementBillNo));
		return dto;
	}
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
}
