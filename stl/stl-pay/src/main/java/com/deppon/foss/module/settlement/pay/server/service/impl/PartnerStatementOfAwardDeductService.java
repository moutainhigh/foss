package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivablePartnerService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.FossConfigEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.service.impl.FossConfigEntityService;
import com.deppon.foss.module.settlement.pay.api.server.service.IPartnerStatementOfAwardDeductService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.PartnerStatementOfAwardDeductDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.PartnerStatementOfAwardDeductResultDto;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IPartnerStatementOfAwardDao;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IBillDepWriteoffBillRecService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IPartnerStatementOfAwardMService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerStatementOfAwardDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerStatementOfAwardEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillDepWriteoffBillRecDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillDepositReceivedDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerStatementOfAwardDto;
import com.deppon.foss.util.define.FossConstants;

/** 
 * 合伙人奖罚对账单扣款SERVICE
 * @author foss结算-306579-guoxinru 
 * @date 2016-3-5  
 */
public class PartnerStatementOfAwardDeductService implements
		IPartnerStatementOfAwardDeductService {
	//声明日志
	private static final Logger logger = LogManager.getLogger(PartnerStatementOfAwardDeductService.class);
	
	/**
	 * 注入结算单据编号生成service
	 */
	private ISettlementCommonService settlementCommonService;
	
	/**
	 * esb地址配置Service
	 */
	private FossConfigEntityService fossConfigEntityService;
	
	/**
	 * 注入部门service
	 */
	private IOrgAdministrativeInfoService  orgAdministrativeInfoService;
	
	/**
	 * 注入应付单服务接口
	 */
	private IBillPayableService billPayableService;
	
	/**
	 * 注入应收单SERVICE
	 */
	private IBillReceivableService billReceivableService;
	
	/**
	 * 注入应收单SERVICE
	 */
	private IBillReceivablePartnerService billReceivablePartnerService;
	
	/**
	 * 注入核销单SERVICE
	 */
	private IBillWriteoffService billWriteoffService;
	
	/**
	 * 待核销预收单、应收单进行查询、核销等操作service
	 */
	private IBillDepWriteoffBillRecService billDepWriteoffBillRecService;
	
	/**
	 * 注入合伙人奖罚对账单管理SERVICE
	 */
	private IPartnerStatementOfAwardMService partnerStatementOfAwardMService;
	
	
	/**
	 * 注入对账单新增DAO
	 */
	private IPartnerStatementOfAwardDao partnerStatementOfAwardDao; 
	
	/**
	 * 合伙人奖罚扣款接口编码
	 */
	private final static String PTP_WITHDRAW_SERVER_CODE = "/PTP_ESB2PTP_DEDUCT_BY_WAYBILL_LIST";
	
	/**
	 * 合伙人奖罚扣款类型
	 */
	private final static String DEDUCT_TYPE_PARTNER_AWARD = "JFKK";

	/**
	 * 对账单扣款
	 * @author foss结算-306579-guoxinru 
     * @date 2016-3-5 
	 */
	@Override
	@Transactional
	public void partnerStatementOfAwardToDeduct(List<String> statementNoList){
		logger.info("进入奖罚对账单扣service ");
		//获取当前登录用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();	
		//判断传入对账单号集合是否为空
		if(CollectionUtils.isEmpty(statementNoList)){
			throw new SettlementException("对账单号为空，无法核销！");
		}
	
		//根据对账单号集合查询对账单
		List<PartnerStatementOfAwardEntity> pAwardEntityList = partnerStatementOfAwardMService.queryPStatementsByStaNoList(statementNoList);
		if(CollectionUtils.isEmpty(pAwardEntityList)){
			throw new SettlementException("根据对账单号查询对账单集合为空！");
		}else{
			//遍历对账单list，进行扣款判断
/*			for (PartnerStatementOfAwardEntity entity : pAwardEntityList){
				if("N".equals(entity.getConfirmStatus())){
					throw new SettlementException("对账单状态未确认，不能扣款！");
				}else if (entity.getPeriodUnverifyRecAmount().compareTo(entity.getPeriodUnverifyPayAmount())<0) {
					throw new SettlementException("对账单单据类型是应付对账单不可以扣款！");
				}
			}*/
			if(pAwardEntityList.size()==1){
				if("N".equals(pAwardEntityList.get(0).getConfirmStatus())){
					throw new SettlementException("对账单状态未确认，不能扣款！");
				}else if (pAwardEntityList.get(0).getPeriodUnverifyRecAmount().compareTo(pAwardEntityList.get(0).getPeriodUnverifyPayAmount())<0) {
					throw new SettlementException("对账单单据类型是应付对账单不可以扣款！");
				}else if((pAwardEntityList.get(0).getUnpaidAmount().compareTo(BigDecimal.ZERO))==0){
					throw new SettlementException("金额已全部核销，不能扣款！");
				}
			}else{
				PartnerStatementOfAwardEntity entity = null;
				for (int i = 0; i < pAwardEntityList.size(); i++) {
					entity = pAwardEntityList.get(i);
					if("N".equals(entity.getConfirmStatus())){
						throw new SettlementException("对账单状态未确认，不能扣款！");
					}else if (entity.getPeriodUnverifyRecAmount().compareTo(entity.getPeriodUnverifyPayAmount())<0) {
						throw new SettlementException("对账单单据类型是应付对账单不可以扣款！");
					}else if(pAwardEntityList.size()-i!=1){
						if(!pAwardEntityList.get(i).getCustomerCode().equals(pAwardEntityList.get(i+1).getCustomerCode())){
							throw new SettlementException("客户不一致，不能批量扣款！");
						}
					}else if((pAwardEntityList.get(i).getUnpaidAmount().compareTo(BigDecimal.ZERO))==0){
						throw new SettlementException("金额已全部核销，不能扣款！");
					}
					
				}
			}
		}
		
		//根据对账单号集合查询对账单明细
		List<PartnerStatementOfAwardDEntity> pAwardDEntityList = partnerStatementOfAwardMService.queryPStatementDByStaNoList(statementNoList);
		if(CollectionUtils.isEmpty(pAwardDEntityList)){
			throw new SettlementException("根据对账单号查询对账单明细集合为空！");
		}
		//从对账单及对账单明细获取数据封装扣款实体集合
		List<PartnerStatementOfAwardDeductDto> deductDtoList = new ArrayList<PartnerStatementOfAwardDeductDto>();
		//循环遍历对账单集合
		for (PartnerStatementOfAwardEntity pAwardEntity : pAwardEntityList) {
			//创建扣款实体
			PartnerStatementOfAwardDeductDto dto = new PartnerStatementOfAwardDeductDto();
			//封装来源单号--对账单号
			dto.setSourceBillNo(pAwardEntity.getStatementBillNo());
			deductDtoList.add(dto);
		}
		
		
		//定义核销dto
		BillWriteoffOperationDto writeoffResultDto;
		
		//定义应收单集合
		List<BillReceivableEntity> recList = new ArrayList<BillReceivableEntity>();
		//循环对账单List
		for (PartnerStatementOfAwardEntity partnerStatementOfAwardEntity : pAwardEntityList) {
			//对账单确认状态判断
			if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM.equals(
					partnerStatementOfAwardEntity.getConfirmStatus())) {
				throw new SettlementException("对账单确认状态为未确认不可以进行扣款!");
			}
			
			//对账单核销操作--应收冲应付
			writeoffResultDto = writeoffStatement(partnerStatementOfAwardEntity, currentInfo);
			if(null!=writeoffResultDto.getBillReceivableEntitys()&&writeoffResultDto.getBillReceivableEntitys().size()>0 ){
				//ddw--begin
				for(BillReceivableEntity entity : writeoffResultDto.getBillReceivableEntitys()){
//					if(entity.getUnverifyAmount().compareTo(BigDecimal.valueOf(0)) > 0){
						recList.add(entity);
//					}
				}
			}else{
				throw new SettlementException("应收单为空！");
			}
		}
		try {
			final int numberOfOneHundred = 100;
			//封装实体，调用PTP接口，通知合伙人扣款
			notifyPtpToDeduct(deductDtoList,currentInfo);
			//预收冲应收
			BillWriteoffOperationDto writeoffDto = new BillWriteoffOperationDto();
			BillDepositReceivedDto billDepositReceivedDto = new BillDepositReceivedDto();
			//设置客户编码
			billDepositReceivedDto.setCustomerCode(pAwardEntityList.get(0).getCustomerCode());
			//设置部门编码
			billDepositReceivedDto.setCollectionOrgCode(pAwardEntityList.get(0).getCreateOrgCode());
			//按时间与客户查询
			billDepositReceivedDto.setQueryType(SettlementConstants.TAB_QUERY_BY_DATE);
			//设置预收单最多显示条数
			billDepositReceivedDto.setMaxShowNum(numberOfOneHundred);
			//按照客户编码和部门编码查询预收单
			BillDepWriteoffBillRecDto billDepWriteoffBillRecDto = billDepWriteoffBillRecService.queryBillDep(billDepositReceivedDto);
			if(null==billDepWriteoffBillRecDto.getBillDepositreceivedEntityList()&&billDepWriteoffBillRecDto.getBillDepositreceivedEntityList().size()==0){
				throw new SettlementException("该合伙人预收单为空！");
			}
			//核销操作Dto加入预收单List
			writeoffDto.setBillDepositReceivedEntitys(billDepWriteoffBillRecDto.getBillDepositreceivedEntityList());
	
			writeoffDto.setBillReceivableEntitys(recList);
			
			//获取预收冲应收核销批次号
		    String writeoffBatchNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HX_BN);
		    writeoffDto.setWriteoffBatchNo(writeoffBatchNo);
		    //设置核销方式--手工核销
		    writeoffDto.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
			//预收冲应收操作
			billWriteoffService.writeoffDepositAndReceivable(writeoffDto, currentInfo);
			//ddw--end		
			//遍历对账单集合
			for (PartnerStatementOfAwardEntity partnerStatementOfAwardEntity : pAwardEntityList) {
				//设置对账单未核销金额
				partnerStatementOfAwardEntity.setUnpaidAmount(BigDecimal.ZERO);
				//设置对账单核销金额--发生金额
				partnerStatementOfAwardEntity.setPaidAmount(partnerStatementOfAwardEntity.getPeriodAmount());
				//修改对账单金额
				this.partnerStatementOfAwardMService.updateStatementForWriteOff(partnerStatementOfAwardEntity, currentInfo);
			}
			
		} catch (SettlementException e) {
			logger.error("扣款失败！"+e.getErrorCode(), e);
			throw new SettlementException("扣款失败！"+e.getErrorCode(), e.getErrorCode());
		}
		logger.info("合伙人奖罚扣款end");
	}

	/**
	 * 封装实体，调用PTP接口，通知合伙人扣款
	 * @author guoxinru--306579
	 * @date 2016-03-05
	 */
	@SuppressWarnings("unchecked")
	private void notifyPtpToDeduct(List<PartnerStatementOfAwardDeductDto> deductDtoList,CurrentInfo currentInfo) {
		logger.info("合伙人奖罚扣款通知PTP开始...");
		//循环扣款dto
		logger.info("合伙人奖罚扣款金额校验通过，开始扣款...");
		String url = "";
		//校验地址是否在ESB注册
		FossConfigEntity configEntity = fossConfigEntityService.queryFossConfigEntityByServerCode(PTP_WITHDRAW_SERVER_CODE);
		if (null != configEntity && !StringUtil.isEmpty(configEntity.getEsbAddr())) {
			url = configEntity.getEsbAddr();
		} else {
			logger.error("合伙人奖罚扣款-读取esb地址有误");
			throw new SettlementException("读取esb地址有误!");
		}
//		String url = "http://10.224.166.154:8081/ptp-syncfoss-itf/v1/ptp/saleflow/saleFlowDeductService/deductByList";
		//发送请求
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("UTF-8");
		PostMethod postMethod = new PostMethod(url);
		//请求参数
		List<PartnerStatementOfAwardDeductDto> req = new ArrayList<PartnerStatementOfAwardDeductDto>();
		//响应参数
		List<PartnerStatementOfAwardDeductResultDto>res  = new ArrayList<PartnerStatementOfAwardDeductResultDto>();
		//组装请求实体
		req = buildPtpDeductReqEntityList(deductDtoList,currentInfo);
		//将请求list转为json数组
		String json = JSONArray.fromObject(req).toString();
		try {
			logger.info("合伙人奖罚扣款-请求参数:" + json);
			RequestEntity requestEntity = new StringRequestEntity(json, "application/json", "UTF-8");
			postMethod.setRequestEntity(requestEntity);
			postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
			String responseBody = "";
			// 执行postMethod
			httpClient.executeMethod(postMethod);
			// 获取返回值
			responseBody = postMethod.getResponseBodyAsString();
			if(null==responseBody||"".equals(responseBody)){
				logger.error("合伙人奖罚扣款响应为空");
				throw new Exception("合伙人奖罚扣款响应为空");
			}
			logger.info("合伙人奖罚扣款成功！" + responseBody);
			// 将返回值转换成list
			JSONArray ja = JSONArray.fromObject(responseBody);
			Collection<PartnerStatementOfAwardDeductResultDto> coll = JSONArray.toCollection(ja,PartnerStatementOfAwardDeductResultDto.class);
			res = new ArrayList<PartnerStatementOfAwardDeductResultDto>(coll);
			//遍历返回list，校验返回值
			for (PartnerStatementOfAwardDeductResultDto resultDto : res) {
				//异常信息
				if (!resultDto.getResult()) {
					logger.error("合伙人奖罚扣款返回标识校验失败:" +resultDto.getMessage() );
					throw new Exception("合伙人系统校验失败，"+resultDto.getMessage());
				}
			}
		}catch (Exception e) {
			throw new SettlementException(e.getMessage());
		}  finally {
			if (null != postMethod) {
				postMethod.releaseConnection();
			}
		}
	logger.info("合伙人奖罚扣款通知PTP-end....");
}


	/**
	 * 组装请求实体List
	 * @author guoxinru--306579
	 * @date 2016-03-05
	 */
	private List<PartnerStatementOfAwardDeductDto> buildPtpDeductReqEntityList(List<PartnerStatementOfAwardDeductDto> deductDtoList,CurrentInfo currentInfo) {
		//ddw
		List<String> statementBillNoList = new ArrayList<String>();
		List<PartnerStatementOfAwardDeductDto> dtoList = new ArrayList<PartnerStatementOfAwardDeductDto>();
		for(int i = 0; i < deductDtoList.size(); i++){
			statementBillNoList.add(deductDtoList.get(i).getSourceBillNo());
		}
		List<BillReceivableEntity> receivableList = billReceivableService.queryReceivableByStatementBillNo(statementBillNoList);
		for(BillReceivableEntity entity : receivableList){
			PartnerStatementOfAwardDeductDto dto = new PartnerStatementOfAwardDeductDto();
			//根据合伙人部门编码，查询部门的标杆编码
			OrgAdministrativeInfoEntity origEntity = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCode(entity.getCustomerCode());//合伙人部门编码
			if(null == origEntity){
				logger.error("查询合伙人部门标杆编码失败，合伙人部门信息为空！");
				throw new SettlementException("查询合伙人部门标杆编码失败，合伙人部门信息为空！");
			}
			logger.info("合伙人部门标杆编码是："+origEntity.getUnifiedCode());
			//封装扣款金额--应收未核销
			dto.setAmount(entity.getUnverifyAmount());
			//封装流水类型--应收子类型
			dto.setFlowType(entity.getBillType());
			//封装来扣款类型--奖罚扣款
			dto.setSourceBillType(DEDUCT_TYPE_PARTNER_AWARD);
			//封装合伙人部门编码--标杆编码
			dto.setPartnerOrgCode(origEntity.getUnifiedCode());
//			dto.setPartnerOrgCode("DP10856");//测试数据
			//封装合伙人名称
			dto.setPartnerOrgName(entity.getCustomerName());
			//封装来源单号--应收单号
			dto.setSourceBillNo(entity.getSourceBillNo());
			//封装对接营业部编码
			dto.setContractOrgCode(entity.getCreateOrgCode());
			//封装对接营业部名称
			dto.setContractOrgName(entity.getCreateOrgName());
			//封装创建用户编码
			dto.setOperatorCode(currentInfo.getEmpCode());
			//封装创建用户名称
			dto.setOperatorName(currentInfo.getEmpName());
			//封装业务日期
			dto.setBizDate(new Date());
			dtoList.add(dto);
		}
		
		return dtoList;
	}		


	/**
	 * 对账单核销
	 * @author ddw
	 * @date 2014-06-17
	 */
	private BillWriteoffOperationDto writeoffStatement(PartnerStatementOfAwardEntity partnerStatementOfAwardEntity,CurrentInfo currentInfo) {

		// 获取核销批次号
		String writeoffBatchNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HX_BN);

		/*
		 * 3 获取应收单、应付单列表记录
		 */
		BillWriteoffOperationDto writeoffResultDto = writeoffStatementDetail(partnerStatementOfAwardEntity, writeoffBatchNo, currentInfo);
		
		//返回核销结果dto
		return writeoffResultDto;
	}
	
	/**
	 * 对账单核销
	 * @author ddw
	 * @date 2014-06-17
	 */
	private BillWriteoffOperationDto writeoffStatementDetail(PartnerStatementOfAwardEntity partnerStatementOfAwardEntity, String writeoffBatchNo,CurrentInfo currentInfo) {

		//封装查询DTO
		PartnerStatementOfAwardDto queryDto = new PartnerStatementOfAwardDto();
		queryDto.setStatementBillNo(partnerStatementOfAwardEntity.getStatementBillNo());
		//根据对账单号查询对账单明细信息
		List<PartnerStatementOfAwardDEntity> statementDetailEntitys = partnerStatementOfAwardDao.querypartnerDByStatementBillNo(queryDto);
		//如果对账单明细列表为空
		if (CollectionUtils.isEmpty(statementDetailEntitys)) {
			//提示对账单明细记录为空，无法核销
			throw new SettlementException("对账单明细记录为空，无法核销");
		}

		// 核销操作Dto
		BillWriteoffOperationDto writeoffDto = new BillWriteoffOperationDto();

		// 从对账单明细中获取应收单号、应付单号
		List<String> recNos = new ArrayList<String>();//应收单号
		List<String> payNos = new ArrayList<String>();//应付单号
		//循环处理对账单明细
		for (PartnerStatementOfAwardDEntity statmentDetailEntity : statementDetailEntitys) {
			// 应收单号
			if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE.equals(statmentDetailEntity.getBillParentType())) {
				//加入到应收单号列表
				recNos.add(statmentDetailEntity.getSourceBillNo());
			}
			// 应付单号
			else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE.equals(statmentDetailEntity.getBillParentType())) {
				//加入到应付单号列表
				payNos.add(statmentDetailEntity.getSourceBillNo());
			}
		}

		// 根据来源单号查询应收单
		//如果应收单号不为空
		if (CollectionUtils.isNotEmpty(recNos)) {
			//查询应收单
			//ddw
			List<BillReceivableEntity> recs = billReceivablePartnerService.queryByReceivableNOs(recNos, FossConstants.ACTIVE);
			// 校应收单原始单据数量和对账单明细数量是否匹配
			if (CollectionUtils.isEmpty(recs)||recs.size() != recNos.size()) {
				//提示对账单明细记录中的应收单和原始记录信息不一致，无法核销，请联系管理员
				logger.error("对账单明细应收单数量:"+recNos.size()+"");
				logger.error("原始应收单数量:"+recs.size()+"");
				throw new SettlementException("对账单明细记录中的应收单和原始记录信息不一致，无法核销，请联系管理员");
			}
			// 设置核销参数中的核销方列表：应收单列表
			writeoffDto.setBillReceivableEntitys(recs);
		}
		// 根据来源单号查询应付单
		//如果应付单号不为空
		if (CollectionUtils.isNotEmpty(payNos)) {
			//查询应付单
			List<BillPayableEntity> pays = billPayableService.queryByPayableNOs(payNos, FossConstants.ACTIVE);
			// 校应付单原始单据数量和对账单明细数量是否匹配
			if ((CollectionUtils.isNotEmpty(pays) && pays.size() != payNos.size()) || CollectionUtils.isEmpty(pays)) {
				//提示对账单明细记录中的应付单和原始记录信息不一致，无法核销，请联系管理员
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
		writeoffDto.setStatementBillNo(partnerStatementOfAwardEntity.getStatementBillNo());

		// 核销对账单明细
		BillWriteoffOperationDto writeoffResultDto = writeoffStatementDetailByBillList(writeoffDto, currentInfo);

		//返回核销结果dto
		return writeoffResultDto;
	}
	
	/**
	 * 对账单核销
	 * @author ddw
	 * @date 2014-06-17
	 */
	private BillWriteoffOperationDto writeoffStatementDetailByBillList(BillWriteoffOperationDto writeoffDto, CurrentInfo currentInfo) {
		// 初始化返回结果
		BillWriteoffOperationDto writeoffResultDto = new BillWriteoffOperationDto();
		//设置应付单
		writeoffResultDto.setBillPayableEntitys(writeoffDto.getBillPayableEntitys());
		
		writeoffResultDto.setBillReceivableEntitys(writeoffDto.getBillReceivableEntitys());

		// 应收冲应付
		//如果应收单和应付单不为空
		if (CollectionUtils.isNotEmpty(writeoffDto.getBillReceivableEntitys())
				&& CollectionUtils.isNotEmpty(writeoffDto.getBillPayableEntitys())) {
			//调用统一核销方法
			writeoffResultDto = billWriteoffService.writeoffReceibableAndPayable(writeoffDto, currentInfo);
			//设置应收单核销结果
			writeoffDto.setBillReceivableEntitys(writeoffResultDto.getBillReceivableEntitys());
			//设置应付单核销结果
			writeoffDto.setBillPayableEntitys(writeoffResultDto.getBillPayableEntitys());
		}

		//更新应收单扣款状态
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("deductStatus","WHS");
		map.put("billReceivableList",writeoffDto.getBillReceivableEntitys());
		int updateNo = this.partnerStatementOfAwardDao.updateDeductStatus(map);
		if(updateNo==0){
			throw new SettlementException("更新应收单扣款状态失败！");
		}
		//返回核销结果
		return writeoffResultDto;
	}


	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}


	public void setFossConfigEntityService(
			FossConfigEntityService fossConfigEntityService) {
		this.fossConfigEntityService = fossConfigEntityService;
	}


	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}


	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}


	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}


	public void setBillWriteoffService(IBillWriteoffService billWriteoffService) {
		this.billWriteoffService = billWriteoffService;
	}


	public void setBillDepWriteoffBillRecService(
			IBillDepWriteoffBillRecService billDepWriteoffBillRecService) {
		this.billDepWriteoffBillRecService = billDepWriteoffBillRecService;
	}


	public void setPartnerStatementOfAwardMService(
			IPartnerStatementOfAwardMService partnerStatementOfAwardMService) {
		this.partnerStatementOfAwardMService = partnerStatementOfAwardMService;
	}

	public void setPartnerStatementOfAwardDao(
			IPartnerStatementOfAwardDao partnerStatementOfAwardDao) {
		this.partnerStatementOfAwardDao = partnerStatementOfAwardDao;
	}

	public void setBillReceivablePartnerService(
			IBillReceivablePartnerService billReceivablePartnerService) {
		this.billReceivablePartnerService = billReceivablePartnerService;
	}
	
}
