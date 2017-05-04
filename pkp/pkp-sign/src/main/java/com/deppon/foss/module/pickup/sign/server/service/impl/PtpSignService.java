package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.sign.server.util.GrayForCUBCUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.deppon.esb.inteface.domain.foss2ptp.PartnerUpdateTakeEffectTimeRequest;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPartnerRelationService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.sign.api.server.dao.IRepaymentDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IPtpSignPartnerService;
import com.deppon.foss.module.pickup.sign.api.server.service.IPtpSignService;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.RepaymentConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.PtpCodDeductReqEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.PtpCodDeductRespEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.exception.RepaymentException;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.sign.api.shared.vo.PtpSignVo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IFossConfigEntityService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.FossConfigEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCResponseDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayRequestEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayResponseEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossSearchTradeRequestDO;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossSearchTradeResponseDO;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossToCubcRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PaymentSettlementDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.TradeDO;
import com.deppon.foss.module.settlement.common.api.shared.dto.VestBatchResult;
import com.deppon.foss.module.settlement.common.api.shared.exception.CUBCGrayException;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.HttpClientUtils;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICubcSettlementService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IPaymentSettlementService;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author 239284
 * @description 合伙人零担签收业务接口实现类
 * @date 2016-01-13
 *
 */
public class PtpSignService implements IPtpSignService {
	
	private String queryTradeListUrl;
	public void setQueryTradeListUrl(String queryTradeListUrl) {
		this.queryTradeListUrl = queryTradeListUrl;
	}
	
	private String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		this.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}
	private static final String SERVICE_CODE = "com.deppon.foss.module.pickup.sign.server.service.impl.PtpSignService";
	/**
	 * 记录日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PtpSignService.class);
	
	/**
	 *  运单状态服务接口
	 */
	private IActualFreightService actualFreightService;
	/**
	 *结清货款, 自提签收时，结清 
	 */
	private IRepaymentDao repaymentDao;
	/**
	 * 合伙人零担签收service
	 */
	private IPtpSignPartnerService ptpSignPartnerService;
	/**
	 * 签收出库service
	 */
	private ISignService signService;
	/**
	 * 付款记录service
	 */
	private IRepaymentService repaymentService;
	/**
	 * 签收结算货款服务
	 */
	private IPaymentSettlementService paymentSettlementService;
	/**
	 * esb地址配置Service
	 */
	private IFossConfigEntityService fossConfigEntityService;

	/**
	 * 运单service
	 */
	private IWaybillManagerService waybillManagerService;
	
	/**
	 * 综合服务接口 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 *  应收单服务
	 */
	public IBillReceivableService billReceivableService;
	
	/**
	 *  营业部 Service
	 */
	private ISaleDepartmentService saleDepartmentService;
	
	/**
	 * 一级网点和二级网点映射管理service
	 */
	private IPartnerRelationService partnerRelationService;
	
	/**
	 * 运单签收结果信息
	 */
	private IWaybillSignResultService waybillSignResultService;
	
	/**
	 * CUBC结清中转接口
	 */
	private ICubcSettlementService cubcSettlementService;
    	
	/**
	 *  合伙人代收货款扣款操作(调用PTP同步接口)
	 *  @author 239284 
	 *  @param ptpVo 页面传参数
	 *  @param currentInfo 当前用户信息
	 */
	public void ptpCodDeduct (PtpSignVo ptpVo, CurrentInfo currentInfo, String typeName) {
		LOGGER.info("合伙人" + typeName + "-检验代收货款状态并扣款-start....");
//		String url = "http://10.224.165.19:8081/ptp-syncfoss-itf/v1/ptp/saleflow/saleFlowDeductService/deductBySourceBill";
		String url = "";
		FossConfigEntity configEntity = fossConfigEntityService.queryFossConfigEntityByServerCode(SignConstants.PTP_DEDUCT_ESB_SYN_SERVER_CODE);
		if (null != configEntity && !StringUtil.isEmpty(configEntity.getEsbAddr())) {
			url = configEntity.getEsbAddr();
		} else {
			LOGGER.error("合伙人签收，调用PTP代收货款扣款。读取esb地址有误!接口编码:"+SignConstants.PTP_DEDUCT_ESB_SYN_SERVER_CODE);
			throw new SignException("合伙人签收，调用PTP代收货款扣款。读取esb地址有误!");
		}
		HttpClient httpClient = new HttpClient();
		// 设置编码格式
		httpClient.getParams().setContentCharset("UTF-8");
		// 构造PostMethod的实例
		PostMethod postMethod = new PostMethod(url);
		//请求参数
		PtpCodDeductReqEntity req = new PtpCodDeductReqEntity();
		//响应参数
		PtpCodDeductRespEntity result = new PtpCodDeductRespEntity();
		try {

			// 来源单号-不能为空
			if (StringUtil.isNotBlank(ptpVo.getWaybillNo())) {
				req.setSourceBillNo(ptpVo.getWaybillNo());
			} else {
				LOGGER.error("合伙人签收，调用PTP代收货款扣款。运单号为空");
				throw new Exception("运单号为空");
			}
			
			WaybillEntity wayBillEntity = waybillManagerService.queryWaybillBasicByNo(ptpVo.getWaybillNo());
			if (null != wayBillEntity) {
				// 判断合伙人信息-不能为空-传标杆编码
				if (StringUtil.isNotEmpty(wayBillEntity.getLastLoadOrgCode())) {
					OrgAdministrativeInfoEntity orgAdminEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(wayBillEntity.getLastLoadOrgCode());
					if (null != orgAdminEntity && StringUtil.isNotEmpty(orgAdminEntity.getUnifiedCode() )) {
						req.setPartnerOrgCode(orgAdminEntity.getUnifiedCode());
					} else {
						LOGGER.error("合伙人签收，调用PTP代收货款扣款。合伙人到达部门标杆编码为空!");
						throw new Exception("合伙人到达部门标杆编码为空!");
					}
				} else {
					LOGGER.error("合伙人签收，调用PTP代收货款扣款。"+"该运单号：" + ptpVo.getWaybillNo() + ", 的到达部门不存在!");
					throw new Exception("该运单号：" + ptpVo.getWaybillNo() + ", 的到达部门不存在!");
				}
			} else {
				LOGGER.error("合伙人签收，调用PTP代收货款扣款。"+"该运单号：" + ptpVo.getWaybillNo() + ", 的到达部门不存在!");
				throw new Exception("该运单号：" + ptpVo.getWaybillNo() + ", 不存在!");
			}

			// 检查扣款类别-不能为空
			req.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE);

			//代收货款金额-不能为空
			req.setAmount(ptpVo.getCodAmount());
			
			//操作人code
			req.setOperatorCode(currentInfo.getEmpCode());
			//操作人name
			req.setOperatorName(currentInfo.getEmpName());
			//签收时间
			req.setSignDate(new Date());
			
			//设置开单日期
			if (null != wayBillEntity.getBillTime()) {
				req.setBizDate(wayBillEntity.getBillTime());
			} else {
				LOGGER.error("合伙人签收，调用PTP代收货款扣款。"+"该运单号：" + ptpVo.getWaybillNo() + ", 开单日期为空!");
				throw new Exception("该运单号：" + ptpVo.getWaybillNo() + ", 开单日期为空!");
			}
			
			//系统来源单号
			req.setSourceSystem("FOSS");

			Object obj = JSONObject.toJSON(req);
			String json = null == obj ? "" : obj.toString();
			LOGGER.info("合伙人" + typeName + "-代收货款校验、扣款-请求参数:" + json);
			RequestEntity requestEntity = new StringRequestEntity(json, "application/json", "UTF-8");
			postMethod.setRequestEntity(requestEntity);
			postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
			String responseBody = "";
	
			// 执行postMethod
			int count = httpClient.executeMethod(postMethod);
			if(count != SettlementReportNumber.TWO_HUNDRED){
				LOGGER.error("合伙人签收通知ptp扣代收货款接口返回失败！响应结果"+result.getMessage());
				throw new Exception("代收扣款失败:"+result.getMessage());
			}
			// 获取返回值
			responseBody = postMethod.getResponseBodyAsString();
			LOGGER.info("合伙人" + typeName + "-代收货款校验、扣款-响应信息" + responseBody);
	
			// 将返回值转换成对象
			JSONObject returnJSON = JSONObject.parseObject(responseBody);
	
			result = JSONObject.toJavaObject(returnJSON, PtpCodDeductRespEntity.class);
			
			//异常信息
			if (null != result && !result.getResult()) {
				LOGGER.error("合伙人签收，调用PTP代收货款扣款。PTP校验失败！返回响应:"+result.getMessage());
				throw new Exception(result.getMessage());
			}
			
			
		} catch (Exception e) {
			LOGGER.error("合伙人签收，调用PTP代收货款扣款。PTP校验失败！错误原因:"+e.getMessage());
			throw new SignException(e.getMessage(), e);
		} finally {
			if (null != postMethod) {
				postMethod.releaseConnection();
			}
		}
		LOGGER.info("合伙人"+ typeName + "-检验代收货款状态并扣款-end....");
	}
	
	/**
	 * 代收货款校验与扣款
	 * @author 239284 
	 * @date 2016-01-13
	 * @param waybillNo 		运单号
	 * @param deptCode	合伙人部门编码
	 * @param deptName	合伙人部门名称
	 * @param codAmount	代收货款金额
	 * @return 是否成功
	 */
	@Transactional
	public boolean validatePayCOD(PtpSignVo ptpVo, CurrentInfo currentInfo) {
		try {
			String vestSystemCode = null;
			try {
              	List<String> arrayList = new ArrayList<String>();
              	arrayList.add(ptpVo.getWaybillNo());
              	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
              			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".validatePayCOD",
              			SettlementConstants.TYPE_FOSS);
              	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
              	List<VestBatchResult> list1 = response.getVestBatchResult();
              	vestSystemCode = list1.get(0).getVestSystemCode();	
	  			} catch (Exception e) {
	  				LOGGER.info("灰度分流失败,"+"运单号："+ptpVo.getWaybillNo());
					throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
	  			}
			if(vestSystemCode == null){
				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			}
			if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
				// 更新代收货款应收单扣款状态为已扣款
				BillReceivableEntity entity = billReceivableService.selectByWayBillNoAndBillType(ptpVo.getWaybillNo(),SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE);
				if (null != entity) {
					//判断扣款状态,如果已经扣款成功则不调用ptp接口扣代收货款
					if (!SettlementDictionaryConstants.STL__WITHHOLD_STATUS__WITHHOLD_SUCCESS
							.equals(entity.getWithholdStatus())) {
						//合伙人代收货款金额为0或小于0时，不调用合伙人扣款操作
						int result =  BigDecimal.ZERO.compareTo(ptpVo.getCodAmount());
						if (result < 0) {
							this.ptpCodDeduct(ptpVo, currentInfo, "PTP-FOSS零担签收");
						}
						//扣款状态为扣款成功
						entity.setWithholdStatus(SettlementDictionaryConstants.STL__WITHHOLD_STATUS__WITHHOLD_SUCCESS);
						//更新代收货款应收单扣款状态为扣款成功
						billReceivableService.updateBillReceivableWithholdStatus(entity);
					}
				}
			}
			if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
				//合伙人代收货款金额为0或小于0时，不调用合伙人扣款操作
				int result =  BigDecimal.ZERO.compareTo(ptpVo.getCodAmount());
				if (result < 0) {
					this.ptpCodDeduct(ptpVo, currentInfo, "PTP-CUBC零担签收");
				}
			}
		} catch (CUBCGrayException e) {
			LOGGER.error("系统繁忙，灰度分流失败，请稍候重试" + e.getErrorCode(), e);
			throw new SignException("系统繁忙，灰度分流失败，请稍候重试");
		} catch (BusinessException e) {
			LOGGER.error("--合伙人签收代收货款校验与扣款异常--" + e.getErrorCode(), e);
			throw new SignException(e.getErrorCode());
		} 
		
		return true;
	}
	

	
	/**
	 * 生成结清记录，同时更新结清状态
	 * 	 @param	vo  运单号
	 *  @param	currentInfo  当前登录信息
	 */
	public void paymentInfoOperation(PtpSignVo ptpVo, CurrentInfo currentInfo) {
		// 结清实体
		RepaymentEntity payEntity = new RepaymentEntity();
		// 运单号
		payEntity.setWaybillNo(ptpVo.getWaybillNo());
		// 结清方式
		payEntity.setSettleApproach(SettlementDictionaryConstants.SETTLE_APPROACH_PC);
		// 生成付款编号
		StringBuffer dateStr = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		// 拼接付款编号
		dateStr = dateStr.append(ptpVo.getWaybillNo());
		payEntity.setRepaymentNo(dateStr.toString());
		// 操作人
		payEntity.setOperator(currentInfo.getEmpName());
		// 操作人编码
		payEntity.setOperatorCode(currentInfo.getEmpCode());
		// 操作部门
		payEntity.setOperateOrgName(currentInfo.getCurrentDeptName());
		// 操作部门编码
		payEntity.setOperateOrgCode(currentInfo.getCurrentDeptCode());
		// 付款方式--合伙人签收后台默认都是现金
		payEntity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		// 客户编码
		payEntity.setConsigneeCode(ptpVo.getConsigneeCode());
		// 客户名称
		payEntity.setConsigneeName(ptpVo.getConsigneeName());
		// 实付运费
		BigDecimal toPayFee = (null == ptpVo.getToPayFee() ? BigDecimal.ZERO  : ptpVo.getToPayFee());
		payEntity.setActualFreight(toPayFee);
		// 代收货款
		BigDecimal codAmount = (null == ptpVo.getCodAmount() ? BigDecimal.ZERO : ptpVo.getCodAmount());
		payEntity.setCodAmount(codAmount);
		// 财务单据无需生成
		payEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOTREQUIRE);
		// 结清是否有效
		payEntity.setActive(FossConstants.YES);
		// 付款时间
		payEntity.setPaymentTime(new Date());
		// 币种
		payEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);

		// 更新ActualFreight表中的结清状态为已结清
		ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
		// 运单号
		actualFreightEntity.setWaybillNo(payEntity.getWaybillNo());
		// 结清状态-已结清
		actualFreightEntity.setSettleStatus(FossConstants.YES);
		// 结款日期
		actualFreightEntity.setSettleTime(new Date());
		// 收货人
		actualFreightEntity.setDeliverymanName(payEntity.getDeliverymanName());
		// 证件类型
		actualFreightEntity.setIdentifyType(payEntity.getIdentifyType());
		// 证件号
		actualFreightEntity.setIdentifyCode(payEntity.getIdentifyCode());
		if (StringUtil.isNotBlank(payEntity.getCodIdentifyCode())) {
			// 证件类型(代收人)
			actualFreightEntity.setCodIdentifyType(payEntity.getCodIdentifyType());
			// 证件号码（代收）
			actualFreightEntity.setCodIdentifyCode(payEntity.getCodIdentifyCode());
		} else {
			// 证件类型(代收人)
			actualFreightEntity.setCodIdentifyType("");
			// 证件号码（代收）
			actualFreightEntity.setCodIdentifyCode("");
		}
		// 更新actualFreight表-更新结清相关信息
		actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);

		try {
			// 只添加金额为0.0的结清记录, 不调结算的接口
			repaymentDao.addPaymentInfo(payEntity);
		} catch (RepaymentException e) {// 捕获异常
			LOGGER.error("--合伙人零担-调用结清货款抛出异常:" + e.getErrorCode());// 记录异常信息
			throw new SignException(e.getErrorCode(), e.getMessage());// 抛出捕获的异常
		}

		LOGGER.info("合伙人零担-调用结清货款完成");// 记录日志
	}

	/**
	 * 核销应收单，同时签收
	 * @param list
	 * @param entity
	 * @param dto
	 * @param currentInfo
	 * @param orderNo
	 */
	@Transactional
	public String  wrieOffAndSign(PtpSignVo ptpVo , List<SignDetailEntity> list,  ArriveSheetEntity entity, LineSignDto dto, CurrentInfo currentInfo, String orderNo) {

		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(ptpVo.getWaybillNo());
		//灰度分流
		String vestSystemCode = GrayForCUBCUtil.getGray(arrayList, grayByWaybillNoUrl);
		//1.校验同一单号运单表的“到付金额“=合伙人代收货款应收+到付运费应收+到达应收
		checkToPayEqualReceivable(ptpVo.getWaybillNo(),vestSystemCode);
		//2.当合伙人点击签收按钮时，增加校验规则 ---add by gpz 2016-8-15
		validateCodAndPaidmethod(ptpVo,vestSystemCode);
		//二级网点历史单据签收校验
		this.checkTwoLevelNetworkBill(ptpVo.getWaybillNo(),vestSystemCode);
		// 根据 付款对象 查询有效付款信息 如果查询出结果 说明此单号没有反结清
		RepaymentEntity conPayEntity = new RepaymentEntity();
		conPayEntity.setWaybillNo(ptpVo.getWaybillNo());
		RepaymentEntity payEntity = repaymentService.queryOneRepaymentInfo(conPayEntity);
		//3.ptp合伙人冲销
		PaymentSettlementDto paymentDto = new PaymentSettlementDto();
		paymentDto.setWaybillNo(ptpVo.getWaybillNo());
		if (null != payEntity) {
			paymentDto.setSourceBillNo(payEntity.getRepaymentNo());
		}
		//付款方式
		paymentDto.setPaymentType(ptpVo.getPaymentType());
		//到达部门
		paymentDto.setDestOrgCode(currentInfo.getDept().getCode());
		//实收到付运费
		paymentDto.setToPayFee(ptpVo.getToPayFee());
		//------CUBC灰度结清服务开始--------add by 378375
		if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
			paymentSettlementService.confirmPTPPaymentAndWriteOff(paymentDto, currentInfo);
			//4.判断你是否已结清，未结清则生成结清记录，同时更新结清状态
			if(!repaymentService.isPayment(ptpVo.getWaybillNo())){
				this.paymentInfoOperation(ptpVo, currentInfo);
			}
		}
		if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
			FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
            //实收代收货款费用    add by 378375
			paymentDto.setCodFee(ptpVo.getCodAmount());
			//判断是否是合伙人    add by 378375
			fossDto.setIsPtp(FossConstants.YES);
			fossDto.setDto(paymentDto);
			fossDto.setCurrentInfo(currentInfo);
			CUBCResponseDto resultDto1 = cubcSettlementService.settleReqToCUBU(fossDto);
			if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
				if(StringUtils.isNotBlank(resultDto1.getMessage())){
					LOGGER.info("调用CUBC结清接口异常信息如下:"+resultDto1.getMessage());
					throw new SettlementException("调用CUBC结清接口异常信息:"+resultDto1.getMessage());
				}
			}
			if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.YES) && StringUtils.equals(resultDto1.getSettleStatus(), FossConstants.YES)){
				this.paymentInfoOperation(ptpVo, currentInfo);
			}
		}
		//------CUBC灰度结清服务结束--------add by 378375
		
		//5.签收
		String resultMsg = signService.addSign(list, entity, dto, currentInfo, orderNo);
		LOGGER.info("--合伙人零担签收结果信息：" + resultMsg);

		if (StringUtil.isNotBlank(resultMsg)) {
			return resultMsg;
		}
		return null;
	}
	
	/**
	 * @author 367638 caodajun
	 * FOSS设置定时任务将签收结果表同步到PTP，每天 05:00,23:00 点进行签收时间同步,
	 * 同步的为前一天签收的数据， PTP保存并更新最新的签收时间.
	 */
	public void processptpSignjob(){
		Date today = new Date();
		// 开始时间
		Date signTimeStart = DateUtils.getStartDatetime(today, -1);

		// 截止时间
		Date signTimeEnd = DateUtils.getEndDatetime(today, -1);

		// 根据起止时间时间查询前一天的签收结果表
		List<PartnerUpdateTakeEffectTimeRequest> result = waybillSignResultService
				.queryWaybillSignResultBySignTime(signTimeStart, signTimeEnd);
		if (CollectionUtils.isNotEmpty(result)) {
			// 向ptp分批推送签收时间和运单号
			if (!ptpSignPartnerService.validBillSaleFlowByAsynESB(result)) {
				throw new SignException("合伙人签收时间发送ESB失败!");
			}
		} else {
			LOGGER.info("--合伙人签收结果表无签收结果记录,不推送签收时间到PTP");
		}
	}
	
	/**
	 * <p>若运单到达部门为H2，到付运费应收单/到达应收单和代收货款应收单的CREATE_TIME晚于2016年10月22日12：00，
	 * FOSS结算调用到达部门映射关系进行匹配，
	 * 如果到付运费应收单和代收货款应收单中到达部门与该二级网点所属的一级网点部门相同，
	 * 则校验通过，可以签收，否则校验不通过。</p>
	 * @author gpz
	 * @date 2016年10月10日
	 * @param waybillNo 运单号
	 */
	public void checkTwoLevelNetworkBill(String waybillNo,String vestSystemCode) {
		LOGGER.info("---二级网点历史单据签收校验   开始---");
		// 通过运单号获取运单信息
		WaybillEntity wayBillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		if (null == wayBillEntity) {
			LOGGER.error("运单号" + waybillNo + "不存在");
			throw new SettlementException("运单号" + waybillNo + "不存在");
		}
		// 查询到达部门是否是合伙人二级网点H2
		SaleDepartmentEntity destDept = saleDepartmentService.querySaleDepartmentInfoByCode(wayBillEntity.getLastLoadOrgCode());
		if (null != destDept) {
			// 如果是到达部门不是二级网点，则不做校验；如果到达部门是二级网点则执行校验
			if (!FossConstants.YES.equals(destDept.getIsTwoLevelNetwork())){
				return;
			}
		}
		//2016年10月22日12：00之前的单据，FOSS结算校验时不匹配映射关系
		Date date = DateUtils.convert(SignConstants.PTP_TWOLEVELNETWORK_BILL_DATE,DateUtils.DATE_TIME_FORMAT);
		//查询物流交易单灰度开始                    353654
		List<BillReceivableEntity> receivableList = null ;
		//FOSS
        if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
			// 查询到付运费应收单(PFCR)/到达应收单(DR)和代收货款应收单(CR)
			BillReceivableConditionDto receivableDto = new BillReceivableConditionDto();
			receivableDto.setWaybillNo(waybillNo);
			// 查询出以下3种应收单:
			receivableDto.setBillTypes(new String[] {
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE,// 合伙人代收货款应收
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE,// 合伙人到付运费应收
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE // 到达应收
			});
        	// 查询合伙人应收单信息
    		receivableList = billReceivableService.queryBillReceivableByCondition(receivableDto);
    		//应收单集合不为空
    		if(CollectionUtils.isNotEmpty(receivableList)){
    			for(BillReceivableEntity entity : receivableList){
    				//CREATE_TIME晚于2016年10月22日12：00，调用到达部门映射关系进行校验
    				if(entity.getCreateTime() != null && entity.getCreateTime().after(date)){
    					//根据二级网点编码查询一级网点
    					SaleDepartmentEntity deptEntity = partnerRelationService.oneEntityByTwoCode(wayBillEntity.getLastLoadOrgCode());
    					if (deptEntity == null) {
    						throw new SettlementException("当前到达部门"+ wayBillEntity.getLastLoadOrgCode()+ "没有对应的一级网点部门");
    					}
    					//判断一级网点的 部门是否和应收单的到达部门是否一致，一致则能签收，不一致，则不能签收
    					if (!deptEntity.getCode().equals(entity.getDestOrgCode())) {
    						LOGGER.error("当前应收单到达部门：" + entity.getDestOrgCode()+ "，一级网点部门：" + deptEntity.getCode());
    						throw new SettlementException("二级网点签收，到达部门不一致，不能签收");
    					}
    				}
    			}
    		}
        }
		//CUBC
        if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
			List<String> waybillNos = new ArrayList<String>();
			waybillNos.add(waybillNo);
			List<String> billTypes = new ArrayList<String>();
			billTypes.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE);// 合伙人代收货款应收
			billTypes.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE);// 合伙人到付运费应收
			billTypes.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE);// 到达应收
			//调用CUBC查询物流交易单  应收信息校验  243921
			FossSearchTradeRequestDO requestDto1 = new FossSearchTradeRequestDO();
			requestDto1.setWaybillNos(waybillNos); //运单号
			requestDto1.setOrderSubType(billTypes);//单据类型
			FossSearchTradeResponseDO responseDto1 = null;
			try {
				responseDto1 = (FossSearchTradeResponseDO) HttpClientUtils.postMethod(requestDto1, new FossSearchTradeResponseDO(), queryTradeListUrl);
			} catch (Exception e) {
				LOGGER.error("调用CUBC接口出现异常,异常信息为：" + e);
				throw new SettlementException("服务器正忙,CUBC查询物流交易单异常,请稍候重试");
			}
			if (responseDto1 != null) {
				if (StringUtils.isNotBlank(responseDto1.getMsg()) && StringUtils.equals("N", responseDto1.getActive())) {
					LOGGER.error("调用CUBC查询物流交易单接口失败，异常信息：" + responseDto1.getMsg());
					throw new SettlementException(responseDto1.getMsg());
				}
				Map<String, List<TradeDO>> dataMap = responseDto1.getDataMap();
				List<TradeDO> tradeslist = dataMap.get(waybillNo);
				if (CollectionUtils.isNotEmpty(tradeslist)) {
					for (TradeDO tradeDO : tradeslist){
						//CREATE_TIME晚于2016年10月22日12：00，调用到达部门映射关系进行校验
						if(tradeDO.getCreateDate() != null && tradeDO.getCreateDate().after(date)){
							//根据二级网点编码查询一级网点
							SaleDepartmentEntity deptEntity = partnerRelationService.oneEntityByTwoCode(wayBillEntity.getLastLoadOrgCode());
							if (deptEntity == null) {
								throw new SettlementException("当前到达部门"+ wayBillEntity.getLastLoadOrgCode()+ "没有对应的一级网点部门");
							}
							//判断一级网点的 部门是否和应收单的到达部门是否一致，一致则能签收，不一致，则不能签收
							if (!deptEntity.getCode().equals(tradeDO.getDestOrgComCode())) {
								LOGGER.error("当前应收单到达部门：" + tradeDO.getDestOrgComCode()+ "，一级网点部门：" + deptEntity.getCode());
								throw new SettlementException("二级网点签收，到达部门不一致，不能签收");
							}
						}
					}
				}
			}
        }
		LOGGER.info("---二级网点历史单据签收校验   结束---");
	}

	/**
	 * D-H ：合伙人零担签收校验优化
	 * <p>1) 识别当前（签收时）运单下的开单付款方式若为网上支付，且代收货款字段为0时，进行始发应收单校验
	 * 		<ol>①   若始发应收单未核销金额为0时，则可正常操作出库</ol>
	 * 		<ol>②   若始发应收单未核销>0时，则无法签收出库，并弹出提示：网上支付未付，不可签收</ol>
	 * </p>
	 * <p>2） 识别当前（签收时）运单下的开单付款方式若为网上支付，且代收货款字段大于0时，进行以下校验.
	 * 		<ol>①   若代收货款应收单扣款成功且始发应收单未核销金额为0时，二者同时满足方可签收出库.</ol>
	 * </p>
	 * @author gpz
	 * @date 2016年8月15日
	 * @param ptpVo 合伙人签收VO
	 */
	private void validateCodAndPaidmethod(PtpSignVo ptpVo,String vestSystemCode) {
		LOGGER.info("---合伙人点击签收按钮时，增加校验规则开始---");
		//运单号
		String waybillNo = ptpVo.getWaybillNo();
		//通过运单号获取运单信息 
		WaybillEntity wayBillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		if (null == wayBillEntity) {
			LOGGER.error("运单号" + waybillNo + "不存在");
			throw new SettlementException("运单号" + waybillNo + "不存在");
		}
		LOGGER.info("运单号：" + waybillNo + "，开单付款方式："+ wayBillEntity.getPaidMethod() + "，代收货款：" + wayBillEntity.getCodAmount());
		//出发部门是直营部门并且到达部门是合伙人部门，才进行签收校验
		SaleDepartmentEntity origDept = saleDepartmentService.querySaleDepartmentInfoByCode(wayBillEntity.getReceiveOrgCode());
		if (null != origDept) {
			//如果是出发部门是合伙人部门，则跳过以下校验
			if (FossConstants.YES.equals(origDept.getIsLeagueSaleDept())){
				return;
			}
		}
		//校验到达部门是合伙人部门
		SaleDepartmentEntity destDept = saleDepartmentService.querySaleDepartmentInfoByCode(wayBillEntity.getLastLoadOrgCode());
		if (null != destDept) {
			//如果是到达部门是直营部门，则跳过以下校验
			if (!FossConstants.YES.equals(destDept.getIsLeagueSaleDept())){
				return;
			}
		}
		List<String> waybillNos = new ArrayList<String>();
		waybillNos.add(waybillNo);
		//开单付款方式若为网上支付
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(wayBillEntity.getPaidMethod())){
			//FOSS
			if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)) {
				if (wayBillEntity.getCodAmount().compareTo(BigDecimal.ZERO) == 1) {//代收货款字段大于0时
					//查询代收货款应收单
					BillReceivableEntity billReceivableEntity = billReceivableService.selectByWayBillNoAndBillType(waybillNo
							, SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE);
					if (null == billReceivableEntity) {
						LOGGER.error("运单号" + waybillNo + "不存在代收货款应收单");
						throw new SettlementException("运单号" + waybillNo + "不存在代收货款应收单");
					}
					//代收货款应收单中扣款状态如果不是扣款成功，则不能签收出库
					if (!SettlementDictionaryConstants.STL__WITHHOLD_STATUS__WITHHOLD_SUCCESS.equals(billReceivableEntity.getWithholdStatus())) {
						LOGGER.error("运单号" + waybillNo + "代收货款应收扣款未成功，扣款状态：" + billReceivableEntity.getWithholdStatus());
						throw new SettlementException("代收货款未扣款或扣款失败");
					}

				}
				//根据运单号和单据子类型，查询始发应收单
				BillReceivableEntity origReceivable = billReceivableService.selectByWayBillNoAndBillType(waybillNo
						, SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE);
				if (null == origReceivable) {
					LOGGER.error("运单号" + waybillNo + "不存在始发应收单");
					throw new SettlementException("运单号" + waybillNo + "不存在始发应收单");
				}
				//始发应收单未核销>0则无法签收出库,未核销金额为0时，则可正常操作出库
				if (origReceivable.getUnverifyAmount().compareTo(BigDecimal.ZERO) > 0) {
					LOGGER.error("运单号" + waybillNo + "的始发应收单未核销");
					throw new SettlementException("网上支付未付，不可签收");
				}
			}
			//CUBC
			if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
				if (wayBillEntity.getCodAmount().compareTo(BigDecimal.ZERO) == 1) {//代收货款字段大于0时
					//查询代收货款应收单
					List<String> billType1s = new ArrayList<String>();
					billType1s.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE);
					//调用CUBC查询物流交易单  应收信息校验  243921
					FossSearchTradeRequestDO requestDto = new FossSearchTradeRequestDO();
					requestDto.setWaybillNos(waybillNos); //运单号
					requestDto.setOrderSubType(billType1s);//单据类型
					FossSearchTradeResponseDO responseDto = null;
					try {
						responseDto = (FossSearchTradeResponseDO) HttpClientUtils.postMethod(requestDto, new FossSearchTradeResponseDO(), queryTradeListUrl);
					} catch (Exception e) {
						LOGGER.error("调用CUBC接口出现异常,异常信息为：" + e);
						throw new SettlementException("服务器正忙,CUBC查询物流交易单异常,请稍候重试");
					}
					if (responseDto != null) {
						if (StringUtils.isNotBlank(responseDto.getMsg()) && StringUtils.equals("N", responseDto.getActive())) {
							LOGGER.error("调用CUBC查询物流交易单接口失败，异常信息：" + responseDto.getMsg());
							throw new SettlementException(responseDto.getMsg());
						}
						Map<String, List<TradeDO>> dataMap = responseDto.getDataMap();
						List<TradeDO> tradeslist = dataMap.get(waybillNo);
						if(CollectionUtils.isNotEmpty(tradeslist)){
							TradeDO recBill = tradeslist.get(0);
							if(null == recBill){
								LOGGER.error("运单号"+waybillNo+"不存在代收货款应收流水");
								throw new SettlementException("运单号" + waybillNo + "不存在代收货款应收流水");
							}
							//代收货款应收单中扣款状态如果不是扣款成功，则不能签收出库
							if (!RepaymentConstants.DONE_STATE.equals(recBill.getPayStatus())){
								LOGGER.error("运单号" + waybillNo + "代收货款应收扣款未成功，扣款状态："+ recBill.getPayStatus());
								throw new SettlementException("代收货款未扣款或扣款失败");
							}
						}
					}
				}
				//查询始发应收单
				List<String> billTypes = new ArrayList<String>();
				billTypes.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE);
				//调用CUBC查询物流交易单  应收信息校验  243921
				FossSearchTradeRequestDO requestDto1 = new FossSearchTradeRequestDO();
				requestDto1.setWaybillNos(waybillNos); //运单号
				requestDto1.setOrderSubType(billTypes);//单据类型
				FossSearchTradeResponseDO responseDto1 = null;
				try {
					responseDto1 = (FossSearchTradeResponseDO) HttpClientUtils.postMethod(requestDto1, new FossSearchTradeResponseDO(), queryTradeListUrl);
				} catch (Exception e) {
					LOGGER.error("调用CUBC接口出现异常,异常信息为：" + e);
					throw new SettlementException("服务器正忙,CUBC查询物流交易单异常,请稍候重试");
				}
				if (responseDto1 != null) {
					if (StringUtils.isNotBlank(responseDto1.getMsg()) && StringUtils.equals("N", responseDto1.getActive())) {
						LOGGER.error("调用CUBC查询物流交易单接口失败，异常信息：" + responseDto1.getMsg());
						throw new SettlementException(responseDto1.getMsg());
					}
					Map<String, List<TradeDO>> dataMap = responseDto1.getDataMap();
					List<TradeDO> tradeslist = dataMap.get(waybillNo);
					if(CollectionUtils.isNotEmpty(tradeslist)){
						TradeDO recBill = tradeslist.get(0);
						if (null == recBill) {
							LOGGER.error("运单号" + waybillNo + "不存在始发应收流水");
							throw new SettlementException("运单号" + waybillNo + "不存在始发应收流水");
						}
						//始发应收单未核销>0则无法签收出库,未核销金额为0时，则可正常操作出库
						if(recBill.getUnverifyAmount().compareTo(BigDecimal.ZERO) > 0){
							LOGGER.error("运单号" + waybillNo + "网上支付未付，不可签收");
							throw new SettlementException("网上支付未付，不可签收");
						}
					}
				}
			}
		}
		LOGGER.info("---合伙人点击签收按钮时，增加校验规则结束---");
	}
	
	
	/**
	 * 新增合伙人签收的校验规则：校验同一单号运单表的“到付金额“=合伙人代收货款应收+到付运费应收/到达应收/快递代理代收货款应收单
	 * /到达快递代理应收/到达偏线代理应收单/空运代理代收货款应收/空运到达代理应收<br/>
	 * 包括（H-H，D-H）
	 * @author gpz
	 * @date 2016年8月17日
	 * @param waybillNo 运单号
	 */
	public void checkToPayEqualReceivable(String waybillNo,String vestSystemCode){
		// 通过运单号获取运单信息
		WaybillEntity wayBillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		if (null == wayBillEntity) {
			LOGGER.error("运单号" + waybillNo + "不存在");
			throw new SettlementException("运单号" + waybillNo + "不存在");
		}
		LOGGER.info("---运单号：" + waybillNo + "，到付金额：" + wayBillEntity.getToPayAmount());
		//查询到达部门是否是合伙人部门
		SaleDepartmentEntity destDept = saleDepartmentService.querySaleDepartmentInfoByCode(wayBillEntity.getLastLoadOrgCode());
		if (null != destDept) {
			// 如果是到达部门是直营部门，则不做校验；如果到达部门合伙人部门则执行校验
			if (!FossConstants.YES.equals(destDept.getIsLeagueSaleDept())){
				return;
			}
		}
		BigDecimal totalAmount = BigDecimal.ZERO;
		//FOSS
        if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
			//存放应收单查询过滤条件 dto
			BillReceivableConditionDto receivableDto = new BillReceivableConditionDto();
			receivableDto.setWaybillNo(waybillNo);
			//查询出以下8种应收单:
			receivableDto.setBillTypes(new String[] {
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE,// 合伙人代收货款应收
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE,// 合伙人到付运费应收
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,// 到达应收
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD,//快递代理代收货款应收单
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_LAND_STOWAGE,//到达快递代理应收
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE,//到达偏线代理应收单
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD,//空运代理代收货款应收
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY	//空运到达代理应收
			});

			//应收单查询灰度。查询CUBC物流交易单   353654
			List<BillReceivableEntity> receivableList = null;
        	// 查询合伙人应收单信息
        	receivableList = billReceivableService.queryBillReceivableByCondition(receivableDto);
        	//计算查询出来的应收单的总金额
        	if(CollectionUtils.isNotEmpty(receivableList)){
        		for(BillReceivableEntity entity : receivableList){
        			totalAmount = entity.getAmount().add(totalAmount);
        			LOGGER.info("--合伙人校验到付金额：应收单号：" + entity.getReceivableNo()
        					+ ",应收单据子类型：" + entity.getBillType() + ",金额："
        					+ entity.getAmount());
        		}
        	}
        	//比较到付金额和应收单的总金额是否相等
        	if(wayBillEntity.getToPayAmount().compareTo(totalAmount) != 0){
        		LOGGER.error("--到付金额与应收金额不一致！到付金额：" + wayBillEntity.getToPayAmount()
        				+ "应收总金额：" + totalAmount);
        		throw new SettlementException("此单号不允许签收，到付金额与应收金额不一致！");
        	}	
        }
		//CUBC
        if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
			//运单号
			List<String> waybillNos = new ArrayList<String>();
			waybillNos.add(waybillNo);
			//应收单类型
			List<String> billTypes = new ArrayList<String>();
			billTypes.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE);// 合伙人代收货款应收
			billTypes.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE);// 合伙人到付运费应收
			billTypes.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE);// 到达应收
			billTypes.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD);//快递代理代收货款应收单
			billTypes.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_LAND_STOWAGE);//到达快递代理应收
			billTypes.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE);//到达偏线代理应收单
			billTypes.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD);//空运代理代收货款应收
			billTypes.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY);//空运到达代理应收

			FossSearchTradeRequestDO requestDto1 = new FossSearchTradeRequestDO();
			requestDto1.setWaybillNos(waybillNos);
			requestDto1.setOrderSubType(billTypes);

			FossSearchTradeResponseDO responseDto1 = null;
			try {
				responseDto1 = (FossSearchTradeResponseDO)HttpClientUtils.postMethod(requestDto1, new FossSearchTradeResponseDO(), queryTradeListUrl);
			} catch (Exception e) {
				LOGGER.error("CUBC查询物流交易单异常,异常信息为："+e.getMessage());
				throw new SettlementException("服务器正忙,CUBC查询物流交易单异常,请稍候重试");
			}
			if(responseDto1 != null){
				if(StringUtils.isNotBlank(responseDto1.getMsg()) && StringUtils.equals("N", responseDto1.getActive())){
					LOGGER.error("调用CUBC查询物流交易单接口失败，异常信息：" + responseDto1.getMsg());
					throw new SettlementException(responseDto1.getMsg());
				}
				Map<String, List<TradeDO>> dataMap = responseDto1.getDataMap();
				List<TradeDO> tradeslist = dataMap.get(waybillNo);
				//计算查询出来的应收单的总金额
				if(CollectionUtils.isNotEmpty(tradeslist)){
					for(TradeDO entity : tradeslist){
						totalAmount = entity.getAmount().add(totalAmount);
						LOGGER.info("--合伙人校验到付金额：物流交易号：" + entity.getOrderNo()
								+ ",应收流水子类型：" + entity.getOrderSubType() + ",金额："
								+ entity.getAmount());
					}
				}
				//比较到付金额和应收单的总金额是否相等
				if(wayBillEntity.getToPayAmount().compareTo(totalAmount) != 0){
					LOGGER.error("--到付金额与应收金额不一致！到付金额：" + wayBillEntity.getToPayAmount()
							+ "应收总金额：" + totalAmount);
					throw new SettlementException("此单号不允许签收，到付金额与应收金额不一致！");
				}
			}
        }
	}
	
	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}

	public void setRepaymentDao(IRepaymentDao repaymentDao) {
		this.repaymentDao = repaymentDao;
	}

	public void setPaymentSettlementService(
			IPaymentSettlementService paymentSettlementService) {
		this.paymentSettlementService = paymentSettlementService;
	}

	public void setSignService(ISignService signService) {
		this.signService = signService;
	}

	public void setRepaymentService(IRepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}

	public void setFossConfigEntityService(
			IFossConfigEntityService fossConfigEntityService) {
		this.fossConfigEntityService = fossConfigEntityService;
	}
	
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	public void setCubcSettlementService(
			ICubcSettlementService cubcSettlementService) {
		this.cubcSettlementService = cubcSettlementService;
	}
	
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	/**
	 * @param partnerRelationService the partnerRelationService to set
	 */
	public void setPartnerRelationService(
			IPartnerRelationService partnerRelationService) {
		this.partnerRelationService = partnerRelationService;
	}

	/**
	 * @param waybillSignResultService the waybillSignResultService to set
	 */
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	public void setPtpSignPartnerService(
			IPtpSignPartnerService ptpSignPartnerService) {
		this.ptpSignPartnerService = ptpSignPartnerService;
	}

	@Override
	public void checkToPayEqualReceivable(String waybillNo) {
			// 通过运单号获取运单信息
			WaybillEntity wayBillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
			if (null == wayBillEntity) {
				LOGGER.error("运单号" + waybillNo + "不存在");
				throw new SettlementException("运单号" + waybillNo + "不存在");
			}
			LOGGER.info("---运单号：" + waybillNo + "，到付金额：" + wayBillEntity.getToPayAmount());
			//查询到达部门是否是合伙人部门
			SaleDepartmentEntity destDept = saleDepartmentService.querySaleDepartmentInfoByCode(wayBillEntity.getLastLoadOrgCode());
			if (null != destDept) {
				// 如果是到达部门是直营部门，则不做校验；如果到达部门合伙人部门则执行校验
				if (!FossConstants.YES.equals(destDept.getIsLeagueSaleDept())){
					return;
				}
			}
			//存放应收单查询过滤条件 dto
			BillReceivableConditionDto receivableDto = new BillReceivableConditionDto();
			receivableDto.setWaybillNo(waybillNo);
			//查询出以下8种应收单:
			receivableDto.setBillTypes(new String[] {
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE,// 合伙人代收货款应收
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE,// 合伙人到付运费应收
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,// 到达应收
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD,//快递代理代收货款应收单
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_LAND_STOWAGE,//到达快递代理应收
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE,//到达偏线代理应收单
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD,//空运代理代收货款应收
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY	//空运到达代理应收
							});
			BigDecimal totalAmount = BigDecimal.ZERO;
	    	// 查询合伙人应收单信息
			List<BillReceivableEntity> receivableList = billReceivableService.queryBillReceivableByCondition(receivableDto);
	    	//计算查询出来的应收单的总金额
	    	if(CollectionUtils.isNotEmpty(receivableList)){
	    		for(BillReceivableEntity entity : receivableList){
	    			totalAmount = entity.getAmount().add(totalAmount);
	    			LOGGER.info("--合伙人校验到付金额：应收单号：" + entity.getReceivableNo()
	    					+ ",应收单据子类型：" + entity.getBillType() + ",金额："
	    					+ entity.getAmount());
	    		}
	    	}
	    	//比较到付金额和应收单的总金额是否相等
	    	if(wayBillEntity.getToPayAmount().compareTo(totalAmount) != 0){
	    		LOGGER.error("--到付金额与应收金额不一致！到付金额：" + wayBillEntity.getToPayAmount()
	    				+ "应收总金额：" + totalAmount);
	    		throw new SettlementException("此单号不允许签收，到付金额与应收金额不一致！");
	    	}	
	}

	@Override
	public void checkTwoLevelNetworkBill(String waybillNo) {
			LOGGER.info("---二级网点历史单据签收校验   开始---");
			// 通过运单号获取运单信息
			WaybillEntity wayBillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
			if (null == wayBillEntity) {
				LOGGER.error("运单号" + waybillNo + "不存在");
				throw new SettlementException("运单号" + waybillNo + "不存在");
			}
			// 查询到达部门是否是合伙人二级网点H2
			SaleDepartmentEntity destDept = saleDepartmentService.querySaleDepartmentInfoByCode(wayBillEntity.getLastLoadOrgCode());
			if (null != destDept) {
				// 如果是到达部门不是二级网点，则不做校验；如果到达部门是二级网点则执行校验
				if (!FossConstants.YES.equals(destDept.getIsTwoLevelNetwork())){
					return;
				}
			}
			// 查询到付运费应收单(PFCR)/到达应收单(DR)和代收货款应收单(CR)
			BillReceivableConditionDto receivableDto = new BillReceivableConditionDto();
			receivableDto.setWaybillNo(waybillNo);
			// 查询出以下3种应收单:
			receivableDto.setBillTypes(new String[] {
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE,// 合伙人代收货款应收
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE,// 合伙人到付运费应收
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE // 到达应收
					});
			//2016年10月22日12：00之前的单据，FOSS结算校验时不匹配映射关系
			Date date = DateUtils.convert(SignConstants.PTP_TWOLEVELNETWORK_BILL_DATE,DateUtils.DATE_TIME_FORMAT);
	    	// 查询合伙人应收单信息
			List<BillReceivableEntity> receivableList = billReceivableService.queryBillReceivableByCondition(receivableDto);
			//应收单集合不为空
			if(CollectionUtils.isNotEmpty(receivableList)){
				for(BillReceivableEntity entity : receivableList){
					//CREATE_TIME晚于2016年10月22日12：00，调用到达部门映射关系进行校验
					if(entity.getCreateTime() != null && entity.getCreateTime().after(date)){
						//根据二级网点编码查询一级网点
						SaleDepartmentEntity deptEntity = partnerRelationService.oneEntityByTwoCode(wayBillEntity.getLastLoadOrgCode());
						if (deptEntity == null) {
							throw new SettlementException("当前到达部门"+ wayBillEntity.getLastLoadOrgCode()+ "没有对应的一级网点部门");
						}
						//判断一级网点的 部门是否和应收单的到达部门是否一致，一致则能签收，不一致，则不能签收
						if (!deptEntity.getCode().equals(entity.getDestOrgCode())) {
							LOGGER.error("当前应收单到达部门：" + entity.getDestOrgCode()+ "，一级网点部门：" + deptEntity.getCode());
							throw new SettlementException("二级网点签收，到达部门不一致，不能签收");
						}
					}
				}
			}
			LOGGER.info("---二级网点历史单据签收校验   结束---");
	}
	
}
