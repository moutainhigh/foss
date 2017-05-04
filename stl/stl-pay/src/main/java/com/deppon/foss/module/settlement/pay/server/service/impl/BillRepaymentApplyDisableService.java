package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillWriteoffEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.pay.api.server.dao.IBillRepaymentManageDao;
import com.deppon.foss.module.settlement.pay.api.server.dao.IRepaymentDisableApplicationEntityDao;
import com.deppon.foss.module.settlement.pay.api.server.dao.IRepaymentDisableDetailEntityDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillRepaymentApplyDisableService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.RepaymentDisableApplicationEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.RepaymentDisableDetailEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentDisableDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentDisableResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentManageDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class BillRepaymentApplyDisableService implements IBillRepaymentApplyDisableService {
	/**综合接口，查询大小区*/
	private IOrgAdministrativeInfoComplexService  orgAdministrativeInfoComplexService;
	/**申请作废DAO*/
	private IRepaymentDisableApplicationEntityDao repaymentDisableApplicationEntityDao;
	/**申请作废明细DAO*/
	private IRepaymentDisableDetailEntityDao repaymentDisableDetailEntityDao;
	/**还款单DAO*/
	private IBillRepaymentManageDao billRepaymentManageDao;
	/** 核销/反核销公共Dao*/
	private IBillWriteoffEntityDao billWriteoffEntityDao;
	//经理审批
	public final static String JLSP = "00";
	//会计审批
	public final static String KJSP = "01";

	
	@Override
	public BillRepaymentDisableResultDto queryRepayment(
			BillRepaymentDisableDto dto) {
		BillRepaymentDisableResultDto resultDto = new BillRepaymentDisableResultDto();
		BillRepaymentDisableDto billRepaymentDisableDto = new BillRepaymentDisableDto();
		//校验参数不能为空
		if(StringUtils.isEmpty(dto.getRepaymentNo())) {
			throw new SettlementException("申请作废的还款单号不能为空！");
		}
		
		//查询申请作废，不能存在全单作废的申请
		//List<RepaymentDisableApplicationEntity> repaymentDisableApplicationEntities = new ArrayList<RepaymentDisableApplicationEntity>();
		List<RepaymentDisableApplicationEntity> repaymentDisableApplicationEntities = queryDisableApplicationByDto(dto);
		if(CollectionUtils.isEmpty(repaymentDisableApplicationEntities)) {
			for(RepaymentDisableApplicationEntity entity:repaymentDisableApplicationEntities) {
				if(entity.getIsAllDisable().equals('Y')) {
					throw new SettlementException("此单已经申请全单作废！");
				} 
			}
		}
		//查询有效的还款单
		List<BillRepaymentManageDto> repayments = queryBillRepaymentByNos(dto);
		BillRepaymentManageDto repayment = repayments.get(0);
		//校验还款单参数
		validateRepayment(repayment);
		//设置返回参数 还款单号
		billRepaymentDisableDto.setRepaymentNo(dto.getRepaymentNo());
		//还款单金额
		billRepaymentDisableDto.setRepaymentAmount(repayment.getAmount());
		//付款方式
		billRepaymentDisableDto.setPaymentType(repayment.getPaymentType());
		//客户编码
		billRepaymentDisableDto.setCustomerCode(repayment.getCustomerCode());
		//客户名称
		billRepaymentDisableDto.setCustomerName(repayment.getCustomerName());
		//dto
		resultDto.setBillRepaymentDisableDto(billRepaymentDisableDto);
		
		return resultDto;
	}
	/**
	 *查询有效的还款单信息
	 */
	private List<BillRepaymentManageDto> queryBillRepaymentByNos(BillRepaymentDisableDto dto) {
		//使用原有方法，转换DTO
		BillRepaymentManageDto billRepaymentManageDto = new BillRepaymentManageDto();
		List<String> repaymentnos = new ArrayList<String>();
		repaymentnos.add(dto.getRepaymentNo());
		billRepaymentManageDto.setRepaymentNos(repaymentnos);
		billRepaymentManageDto.setEmpCode(dto.getEmpCode());
		//查询有效的还款单
		billRepaymentManageDto.setActive(FossConstants.ACTIVE);
		List<BillRepaymentManageDto> repayment = billRepaymentManageDao.queryBillRepaymentByNos(billRepaymentManageDto);
		return repayment;
	}
	
	/**
	 * 校验还款单信息
	 */
	private void validateRepayment(BillRepaymentManageDto dto) {
		//只有审核状态为未审核、有效的、非红单的还款单才能进行还款单作废申请操作
		if(!dto.getAuditStatus().equals(SettlementDictionaryConstants.BILL_ADVANCED_PAYMENT__AUDIT_STATUS__NOT_AUDIT)){
			throw new SettlementException("还款单单据审核状态必须为未审核！");
		} 
		if(dto.getIsRedBack().equals(FossConstants.YES)) {
			throw new SettlementException("还款单单据不能为红单！");
		}
		if(!dto.getActive().equals(FossConstants.YES)) {
			throw new SettlementException("还款单单据不能为无效单据！");
		}
		//增加校验 如果为自动生成的还款单不能作废
		if(dto.getCreateType().equals(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO)) {
			throw new SettlementException("还款单单据生成方式不能为系统生成！");
		}
	}


	@Override
	public BillRepaymentDisableResultDto queryRepaymentWriteoff(
			BillRepaymentDisableDto dto, int start, int limit) {
		BillRepaymentDisableResultDto resultDto = new BillRepaymentDisableResultDto();
		BillRepaymentDisableDto billRepaymentDisableDto = new BillRepaymentDisableDto();
		if(StringUtils.isEmpty(dto.getRepaymentNo())) {
			throw new SettlementException("申请作废的还款单号不能为空！");
		}

		//查询有效的核销明细(非已经申请)
		List<BillWriteoffEntity>  billWriteoffEntities= billWriteoffEntityDao
				.queryByBeginNoR(
						dto.getRepaymentNo(),
						FossConstants.ACTIVE,
						null,
						SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE,
					  start, limit);
		if (CollectionUtils.isNotEmpty(billWriteoffEntities)) {
			// 转换实体 如果明细已经申请过，则不再显示
			for (BillWriteoffEntity billWriteoffEntity : billWriteoffEntities) {
				RepaymentDisableDetailEntity detaiEntity = new RepaymentDisableDetailEntity();
				detaiEntity.setId(billWriteoffEntity.getId());
				detaiEntity.setAmount(billWriteoffEntity.getAmount());
				detaiEntity.setCustomerCode(billWriteoffEntity.getCustomerCode());
				detaiEntity.setCustomerName(billWriteoffEntity.getCustomerName());
				detaiEntity.setOrgCode(billWriteoffEntity.getOrgCode());
				detaiEntity.setOrgName(billWriteoffEntity.getOrgName());
				detaiEntity.setReceviceNo(billWriteoffEntity.getEndNo());
				detaiEntity.setReceviceWaybillNo(billWriteoffEntity.getEndWaybillNo());
				detaiEntity.setRepaymentNo(billWriteoffEntity.getBeginNo());
				detaiEntity.setStatementNo(billWriteoffEntity.getStatementBillNo());
				detaiEntity.setWriteOffBillNo(billWriteoffEntity.getWriteoffBillNo());
				detaiEntity.setWriteoffTime(billWriteoffEntity.getWriteoffTime());
				detaiEntity.setWriteoffType(billWriteoffEntity.getWriteoffType());
				billRepaymentDisableDto.getDetails().add(detaiEntity);
			}
		}
		List<Map<String,Object>> lsmap = billWriteoffEntityDao.queryByBeginNoRepay(dto.getRepaymentNo(),
						FossConstants.ACTIVE,
						null,
						SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE);
		//核销金额
		resultDto.setTotalVerifyAmount((BigDecimal) lsmap.get(0).get("AMOUNT"));
		//明细总数
		resultDto.setTotalCount(((BigDecimal)lsmap.get(0).get("COUNT")).intValue());
		//dto
		resultDto.setBillRepaymentDisableDto(billRepaymentDisableDto);
				
		return resultDto;
	}
	
	@Transactional
	@Override
	public void addDisableApplication(BillRepaymentDisableDto dto) {
	
		//校验是否可以申请作废
		validateApplication(dto);
		
		Date now = new Date();
		
		//声明DTO查询
		BillRepaymentDisableDto disDto = new BillRepaymentDisableDto();
		//还款单号查询
		disDto.setRepaymentNo(dto.getRepaymentNo());
		disDto.setActive(FossConstants.ACTIVE);
		//获取作废申请明细
		List<RepaymentDisableDetailEntity> detailEntities = queryDisableDetailByDto(disDto);
		List<BillWriteoffEntity> billWriteoffEntities = billWriteoffEntityDao.queryByPrimaryKeys(dto.getDetailIds());
		//异常单据
		List<String> msgString = new ArrayList<String>();
		//如果不为空则判断是否已经申请作废
		if(CollectionUtils.isNotEmpty(detailEntities) && CollectionUtils.isNotEmpty(billWriteoffEntities)) {
			//循环，如果核销单已经申请，则筛选出来
			for(RepaymentDisableDetailEntity detailEntity : detailEntities) {
				for(BillWriteoffEntity writeOffEntity : billWriteoffEntities) {
					if(detailEntity.getWriteOffBillNo().equals(writeOffEntity.getWriteoffBillNo())) {
						msgString.add(writeOffEntity.getWriteoffBillNo());
					}
				}
			}
		}
		
		// 如果存在已经申请的明细，抛出异常
		if (CollectionUtils.isNotEmpty(msgString)) {
				throw new SettlementException(msgString + "已经申请作废！");
		}
		
		//获取申请实体
		RepaymentDisableApplicationEntity entity = getRepaymentDisableApplicationEntity(dto ,now);
		List<RepaymentDisableDetailEntity> entityDetails = getRepaymentDisablDetailEntity(dto ,now ,entity,billWriteoffEntities);
		
		//插入明细
		for(int i=0;i<entityDetails.size();i++) {
			repaymentDisableDetailEntityDao.addDisableDetail(entityDetails.get(i));
		}
		
		
		
		//插入申请尸体
		repaymentDisableApplicationEntityDao.addDisableApplication(entity);
		
	}
	
	/**
	 * 校验是否可以申请
	 *
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-11-19 下午8:10:40 
	 * @param dto
	 */
	private void validateApplication(BillRepaymentDisableDto dto) {
		//dto参数校验
		if(dto == null) {
			throw new SettlementException("DTO参数不能为空！");
		}
		if(StringUtils.isEmpty(dto.getRepaymentNo())) {
			throw new SettlementException("还款单号不能为空！");
		}
		if(CollectionUtils.isEmpty(dto.getDetailIds())) {
			throw new SettlementException("申请作废明细不能为空！");
		}
		if(StringUtils.isEmpty(dto.getDisableReason())) {
			throw new SettlementException("申请作废原因不能为空！");
		}
		if(StringUtils.isEmpty(dto.getDisableNote())) {
			throw new SettlementException("申请作废备注不能为空！");
		} else if(dto.getDisableNote().length() > SettlementConstants.MAX_LIST_SIZE){
			throw new SettlementException("申请备注请限制在1000字数以内！");
		}
		if(dto.getAmount()==null || dto.getAmount().compareTo(BigDecimal.ZERO) <=0) {
			throw new SettlementException("申请作废金额为空或者小于等于0！");
		}
	}
	
	/**
	 * 获取申请实体
	 */
	private RepaymentDisableApplicationEntity getRepaymentDisableApplicationEntity(
			BillRepaymentDisableDto dto ,Date now) {
		RepaymentDisableApplicationEntity entity = new RepaymentDisableApplicationEntity();
		//金額
		entity.setAmount(dto.getAmount());
		//申请大区
		List<OrgAdministrativeInfoEntity> orgAdministrativeInfoEntities = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoUpByBizType(dto.getApplyOrgCode(),BizTypeConstants.ORG_BIG_REGION);
		if(CollectionUtils.isNotEmpty(orgAdministrativeInfoEntities)) {
			entity.setApplyAreaCode(orgAdministrativeInfoEntities.get(0).getCode());
			entity.setApplyAreaName(orgAdministrativeInfoEntities.get(0).getName());
		}
		//申请部门
		entity.setApplyOrgCode(dto.getApplyOrgCode());
		entity.setApplyOrgName(dto.getApplyOrgName());
		//申请小区
		orgAdministrativeInfoEntities = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoUpByBizType(dto.getApplyOrgCode(), BizTypeConstants.ORG_SMALL_REGION);
		if(CollectionUtils.isNotEmpty(orgAdministrativeInfoEntities)) {
			entity.setApplyParentOrgCode(orgAdministrativeInfoEntities.get(0).getCode());
			entity.setApplyParentOrgName(orgAdministrativeInfoEntities.get(0).getName());
		}		
		//申請時間
		entity.setApplyTime(now);
		//申請人編碼
		entity.setApplyUserCode(dto.getApplyUserCode());
		//申請人名稱
		entity.setApplyUserName(dto.getApplyUserName());
		//創建時間
		entity.setCreateTime(now);
		//还款单号
		entity.setRepaymentNo(dto.getRepaymentNo());
		//作废原因
		entity.setDisableReason(dto.getDisableReason());
		//作废备注
		entity.setDisableNote(dto.getDisableNote());
		entity.setApproveStatus(SettlementDictionaryConstants.BILL_REPAYMENT__AUDIT_STATUS__NOT_AUDIT);
		//修改时间
		entity.setModifyTime(now);
		//还款单号
		entity.setRepaymentNo(dto.getRepaymentNo());
		//ID
		entity.setId(UUIDUtils.getUUID());
		
		//是否全单作废
		List<BillWriteoffEntity> slmap = billWriteoffEntityDao.queryByBeginNo(dto.getRepaymentNo(),FossConstants.ACTIVE,null,
				SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE); 
		entity.setIsAllDisable(slmap.size() == dto.getDisableNum()?"Y":"N");
		//作废条数
		entity.setDisableNum(dto.getDisableNum());
		
		//查询有效的还款单
		List<BillRepaymentManageDto> repayments = queryBillRepaymentByNos(dto);
		BillRepaymentManageDto repayment = repayments.get(0);
		//校验还款单参数
		validateRepayment(repayment);
		//还款单金额
		entity.setRepaymentAmount(repayment.getAmount());
		//客户编码
		entity.setCustomerCode(repayment.getCustomerCode());
		//客户名称
		entity.setCustomerName(repayment.getCustomerName());
		
		entity.setPaymentType(repayment.getPaymentType());
		entity.setApproveStatus(SettlementDictionaryConstants.BILL_REPAYMENT__AUDIT_STATUS__NOT_AUDIT);
		
		//汇款编号
		entity.setTrueAmount(repayment.getTrueAmount());
		//实际回款金额
		entity.setOaPaymentNo(repayment.getOaPaymentNo());
		return entity;
	}
	
	/**
	 *获取明细实体
	 */
	private List<RepaymentDisableDetailEntity> getRepaymentDisablDetailEntity(
			BillRepaymentDisableDto dto, Date now ,RepaymentDisableApplicationEntity repaymentDisableApplicationEntity,
			List<BillWriteoffEntity> billWriteoffEntities) {
		List<RepaymentDisableDetailEntity> detailEntities = new ArrayList<RepaymentDisableDetailEntity>();
		//封装申请明细实体
		for(BillWriteoffEntity entity : billWriteoffEntities) {
			if(!entity.getActive().equals(FossConstants.ACTIVE)) {
				throw new SettlementException(entity.getWriteoffBillNo() + "核销单已经失效，请重新申请！");
			}
			RepaymentDisableDetailEntity detailEntity = new RepaymentDisableDetailEntity();
			detailEntity.setId(UUIDUtils.getUUID());
			//设置表头ID
			detailEntity.setRepaymentApplicationId(repaymentDisableApplicationEntity.getId());
			detailEntity.setActive(FossConstants.ACTIVE);
			detailEntity.setAmount(entity.getAmount());
			detailEntity.setCreateTime(now);
			detailEntity.setCustomerCode(entity.getCustomerCode());
			detailEntity.setCustomerName(entity.getCustomerName());
			detailEntity.setModifyTime(now);
			detailEntity.setOrgCode(entity.getOrgCode());
			detailEntity.setOrgName(entity.getOrgName());
			detailEntity.setReceviceNo(entity.getEndNo());
			detailEntity.setReceviceWaybillNo(entity.getEndWaybillNo());
			detailEntity.setRepaymentNo(entity.getBeginNo());
			detailEntity.setStatementNo(entity.getStatementBillNo());
			detailEntity.setWriteOffBillNo(entity.getWriteoffBillNo());
			detailEntity.setWriteoffTime(now);
			detailEntity.setWriteoffType(entity.getWriteoffType());
			detailEntities.add(detailEntity);
		}
		
		return detailEntities;
	}
	
	@Transactional
	@Override
	public void approveApplication(BillRepaymentDisableDto dto) {
		// 校验审批的ID是否为空
		if (CollectionUtils.isEmpty(dto.getApplyIDs())) {
			throw new SettlementException("审批的申请不能为空！");
		}
		List<RepaymentDisableApplicationEntity> applicationEntities = queryDisableApplicationByDto(dto);
		if (CollectionUtils.isNotEmpty(applicationEntities)) {
			//如果為經理審批
			if (dto.getRole().equals(BillRepaymentApplyDisableService.JLSP)) {
				for (RepaymentDisableApplicationEntity entity : applicationEntities) {
					if (!entity.getApproveStatus()
							.equals(SettlementDictionaryConstants.BILL_REPAYMENT__AUDIT_STATUS__NOT_AUDIT)) {
						throw new SettlementException("单据状态为未审批才能审批！");
					}
				}
				// 设置审批状态参数调用DAO
				if(dto.getOperateType().equals(SettlementDictionaryConstants.BILL_REPAYMENT_AUDIT_OPERATE_TYPE_TG)) {
					dto.setApproveStatus(SettlementDictionaryConstants.BILL_REPAYMENT_AUDIT_STATUS_AUDITING);
				}
				
			}
			
	
			//如果為會計審批
			if (dto.getRole().equals(BillRepaymentApplyDisableService.KJSP)) {
				for (RepaymentDisableApplicationEntity entity : applicationEntities) {
					if (!entity.getApproveStatus()
							.equals(SettlementDictionaryConstants.BILL_REPAYMENT_AUDIT_STATUS_AUDITING)) {
						throw new SettlementException("单据状态为审批中才能审批！");
					}
				}
				// 设置审批状态参数调用DAO
				// 判断操作类型 会计审批完状态为审批通过
				if(dto.getOperateType().equals(SettlementDictionaryConstants.BILL_REPAYMENT_AUDIT_OPERATE_TYPE_TG)) {
					dto.setApproveStatus(SettlementDictionaryConstants.BILL_REPAYMENT__AUDIT_STATUS__AUDIT_AGREE);
				}  
			}
			
			// 退回状态为退回
			if(dto.getOperateType().equals(SettlementDictionaryConstants.BILL_REPAYMENT_AUDIT_OPERATE_TYPE_TH)) {
				dto.setApproveStatus(SettlementDictionaryConstants.BILL_REPAYMENT_AUDIT_STATUS_DISAGREE);
				/*如果退回，将明细更新成无效状态，方便下次申请查询
				 */
				dto.setActive(FossConstants.NO);
				repaymentDisableDetailEntityDao.updateDisableDetailByDto(dto);
			}
			// 设置其他参数
			Date now = new Date();
			dto.setApproveTime(now);
			dto.setModifyTime(now);
			repaymentDisableApplicationEntityDao.approveApply(dto);
			
		}
			
			

	}
	
	@Override
	public List<RepaymentDisableApplicationEntity> queryDisableApplicationByDto(BillRepaymentDisableDto dto) {
		
		return repaymentDisableApplicationEntityDao.queryDisableApplicationByDto(dto);
	}
	
	@Override
	public BillRepaymentDisableResultDto queryDisableApplicationByDto(BillRepaymentDisableDto dto ,int start ,int limit) {
		BillRepaymentDisableResultDto resultDto = new BillRepaymentDisableResultDto();
		List<RepaymentDisableApplicationEntity> repaymentDisableApplicationEntities = new ArrayList<RepaymentDisableApplicationEntity>();
		//如果为经理审批，则只能 查询当前部门的未审批单据按日期查询时
		if(BillRepaymentApplyDisableService.JLSP.equals(dto.getRole())) {
			if (SettlementConstants.TAB_QUERY_BY_DATE.equals(dto.getQueryType())) {
				if (StringUtils.isEmpty(dto.getApplyOrgCode())) {
					throw new SettlementException("部门为空，不能查询！");
				}
			}
			//设置审批状态为未审批
			dto.setApproveStatus(SettlementDictionaryConstants.BILL_REPAYMENT__AUDIT_STATUS__NOT_AUDIT);
			//大区和小区为空
			dto.setApplyParentOrgCode(null);
			dto.setApplyAreaCode(null);
			resultDto = repaymentDisableApplicationEntityDao.queryResultDto(dto);
			repaymentDisableApplicationEntities = repaymentDisableApplicationEntityDao.queryDisableApplicationByDto(dto, start, limit);
			resultDto.setApplys(repaymentDisableApplicationEntities);
		}
		//如果为会计审批，可以切换查询部门、大区、小区
		if(BillRepaymentApplyDisableService.KJSP.equals(dto.getRole())) {
			//设置审批状态为审批中
			dto.setApproveStatus(SettlementDictionaryConstants.BILL_REPAYMENT_AUDIT_STATUS_AUDITING);
			//大区、小区、部门不能同时为空按日期查询时
			if(SettlementConstants.TAB_QUERY_BY_DATE.equals(dto.getQueryType())) {
				if(StringUtils.isEmpty(dto.getApplyAreaCode()) && StringUtils.isEmpty(dto.getApplyParentOrgCode())
						&& StringUtils.isEmpty(dto.getApplyOrgCode())) {
					throw new SettlementException("查询大区、小区、部门不能同时为空！");
				}
			}
			resultDto = repaymentDisableApplicationEntityDao.queryResultDto(dto);
			repaymentDisableApplicationEntities = repaymentDisableApplicationEntityDao.queryDisableApplicationByDto(dto, start, limit);
			resultDto.setApplys(repaymentDisableApplicationEntities);
		}
		//如果dto不含role值，则为综合查询
		if(StringUtils.isEmpty(dto.getRole())) {
			if(SettlementConstants.TAB_QUERY_BY_DATE.equals(dto.getQueryType())) {
				//大区、小区、部门不能同时为空
				if(dto.getApplyAreaCode() == null && dto.getApplyParentOrgCode()==null && dto.getApplyOrgCode() == null) {
					throw new SettlementException("查询大区、小区、部门不能同时为空！");
				}
			}
			resultDto = repaymentDisableApplicationEntityDao.queryResultDto(dto);
			repaymentDisableApplicationEntities =repaymentDisableApplicationEntityDao.queryDisableApplicationByDto(dto, start, limit);
			resultDto.setApplys(repaymentDisableApplicationEntities);
		}
		return resultDto;
	}
	
	@Override
	public HSSFWorkbook queryExportBillRepaymentApply(
			BillRepaymentDisableDto billRepayDto, CurrentInfo cInfo) {
		/**
		 * 如果导出列头名称不存在，则抛出异常提示
		 */
		if (billRepayDto.getArrayColumnNames() == null
				|| billRepayDto.getArrayColumnNames().length == 0) {
			throw new SettlementException("导出Excel的列头名称不存在，请至少存在一列!");
		}
		/**
		 * 如果导出列头名称不存在，则抛出异常提示
		 */
		if (billRepayDto.getArrayColumns() == null
				|| billRepayDto.getArrayColumns().length == 0) {
			throw new SettlementException("导出Excel的列头不存在，请至少存在一列!");
		}
		// 调用执行方法，获取结果集
		BillRepaymentDisableResultDto resultDto = queryDisableApplicationByDto(billRepayDto, 0, Integer.MAX_VALUE);
		// 判断要导出数据是否存在，若不存在，则抛出异常提示
		if (resultDto == null || resultDto.getApplys() == null
				|| resultDto.getApplys().size() == 0) {
			throw new SettlementException("没有要导出的数据!");
		}
		// 将要导出东西封装转化成Excel导出要用的List<List<String>> 格式
		List<List<String>> rowList = convertListFormEntity(
				resultDto.getApplys(),
				billRepayDto.getArrayColumns());

		// 获取导出数据
		SheetData data = new SheetData();
		// 设置导出列头
		data.setRowHeads(billRepayDto.getArrayColumnNames());
		data.setRowList(rowList);
		// 获取平台提供导出函数
		ExcelExport export = new ExcelExport();
		// 返回wookbook
		HSSFWorkbook wookbook = export.exportExcel(data, "sheet",SettlementConstants.EXPORT_MAX_COUNT);
		return wookbook;
	}

	private List<List<String>> convertListFormEntity(
			List<RepaymentDisableApplicationEntity> list, String[] header) {
		// 声明sheetList
		List<List<String>> sheetList = new ArrayList<List<String>>();
		//声明字典集合
		List<String> termCodes = new ArrayList<String>();
		termCodes.add(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE);
		termCodes.add(DictionaryConstants.FOSS_BOOLEAN);
	
		//后台获取数据字典对应的数据
		Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(termCodes);
		
		// 循环进行封装
		for (RepaymentDisableApplicationEntity entity : list) {
			// 声明一行的rowList
			List<String> rowList = new ArrayList<String>();
			for (String columnName : header) {
				// 通过名称获取field
				Field field = ReflectionUtils.findField(
						RepaymentDisableApplicationEntity.class, columnName);
				if (field != null) {
					// 通过传入字段获取值
					ReflectionUtils.makeAccessible(field);
					Object fieldValue = ReflectionUtils.getField(field, entity);
					// //如果为日期，需要进行格式化
					if (Date.class.equals(field.getType())
							&& fieldValue != null) {
						fieldValue = com.deppon.foss.util.DateUtils.convert(
								(Date) fieldValue, "yyyy-MM-dd HH:mm:ss");
					}
					// 将字段封装到list中
					if (fieldValue != null) {
						
						// 还款方式
						if (columnName.equals("paymentType")) {
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
									DictionaryConstants.SETTLEMENT__PAYMENT_TYPE,
									fieldValue.toString());
						}
						if (columnName.equals("approveStatus")) {
							if(fieldValue.toString().equals(SettlementDictionaryConstants.BILL_REPAYMENT__AUDIT_STATUS__NOT_AUDIT)) {
								fieldValue="未审批";
							}
							if(fieldValue.toString().equals(SettlementDictionaryConstants.BILL_REPAYMENT_AUDIT_STATUS_AUDITING)) {
								fieldValue="审批中";
							}
							if(fieldValue.toString().equals(SettlementDictionaryConstants.BILL_REPAYMENT__AUDIT_STATUS__AUDIT_AGREE)) {
								fieldValue="已审批";
							}
							if(fieldValue.toString().equals(SettlementDictionaryConstants.BILL_REPAYMENT_AUDIT_STATUS_DISAGREE)) {
								fieldValue="退回";
							}
						}
						if (columnName.equals("isAllDisable")) {
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
									DictionaryConstants.FOSS_BOOLEAN,
									fieldValue.toString());
						}
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
	
	@Override
	public List<RepaymentDisableDetailEntity> queryDisableDetailByDto(BillRepaymentDisableDto dto) {
		return repaymentDisableDetailEntityDao.queryDisableDetailByDto(dto);
	}
	/**
	 * @return the orgAdministrativeInfoComplexService
	 */
	public IOrgAdministrativeInfoComplexService getOrgAdministrativeInfoComplexService() {
		return orgAdministrativeInfoComplexService;
	}
	/**
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	/**
	 * @return the repaymentDisableApplicationEntityDao
	 */
	public IRepaymentDisableApplicationEntityDao getRepaymentDisableApplicationEntityDao() {
		return repaymentDisableApplicationEntityDao;
	}
	/**
	 * @param repaymentDisableApplicationEntityDao the repaymentDisableApplicationEntityDao to set
	 */
	public void setRepaymentDisableApplicationEntityDao(
			IRepaymentDisableApplicationEntityDao repaymentDisableApplicationEntityDao) {
		this.repaymentDisableApplicationEntityDao = repaymentDisableApplicationEntityDao;
	}
	/**
	 * @return the repaymentDisableDetailEntityDao
	 */
	public IRepaymentDisableDetailEntityDao getRepaymentDisableDetailEntityDao() {
		return repaymentDisableDetailEntityDao;
	}
	/**
	 * @param repaymentDisableDetailEntityDao the repaymentDisableDetailEntityDao to set
	 */
	public void setRepaymentDisableDetailEntityDao(
			IRepaymentDisableDetailEntityDao repaymentDisableDetailEntityDao) {
		this.repaymentDisableDetailEntityDao = repaymentDisableDetailEntityDao;
	}
	/**
	 * @return the billRepaymentManageDao
	 */
	public IBillRepaymentManageDao getBillRepaymentManageDao() {
		return billRepaymentManageDao;
	}
	/**
	 * @param billRepaymentManageDao the billRepaymentManageDao to set
	 */
	public void setBillRepaymentManageDao(
			IBillRepaymentManageDao billRepaymentManageDao) {
		this.billRepaymentManageDao = billRepaymentManageDao;
	}
	/**
	 * @return the billWriteoffEntityDao
	 */
	public IBillWriteoffEntityDao getBillWriteoffEntityDao() {
		return billWriteoffEntityDao;
	}
	/**
	 * @param billWriteoffEntityDao the billWriteoffEntityDao to set
	 */
	public void setBillWriteoffEntityDao(
			IBillWriteoffEntityDao billWriteoffEntityDao) {
		this.billWriteoffEntityDao = billWriteoffEntityDao;
	}


}
