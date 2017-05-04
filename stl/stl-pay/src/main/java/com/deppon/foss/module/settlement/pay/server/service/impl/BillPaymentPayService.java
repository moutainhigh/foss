package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.deppon.esb.inteface.domain.payment.ExpenseDetail;
import com.deppon.esb.inteface.domain.payment.StowageEntity;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptResultDto;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillDepositReceivedService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IClientSendVtsPayableCheck;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.FossConfigEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.RequestPayableCheckEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.ResponsePayableCheckEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillInfoEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPaymentDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.service.impl.FossConfigEntityService;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillDepositReceivedPayService;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillPayableQueryManageservice;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillPaymentPayService;
import com.deppon.foss.module.settlement.pay.api.server.service.IPayToCostcontrolSendService;
import com.deppon.foss.module.settlement.pay.api.server.service.IPayToFSSCSendService;
import com.deppon.foss.module.settlement.pay.api.server.service.IPaymentStatusToVTSClient;
import com.deppon.foss.module.settlement.pay.api.shared.domain.PtpWithdrawReqEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.PtpWithdrawRespEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillDepositReceivedPayDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPayableManageDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPayableManageResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentAddDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.PayToCostcontrolDto;
import com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService;
import com.deppon.foss.module.transfer.load.api.shared.define.OutsideVehicleChargeConstants;
import com.deppon.foss.module.transfer.load.api.shared.vo.OutVehicleAssembleBillAndFeeVo;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.fssc.inteface.domain.payment.StowageDetail;

/**
 * 录入付款单服务类
 *
 * @author 045738-foss-maojianqiang
 * @date 2012-11-27 下午7:49:35
 */
public class BillPaymentPayService implements IBillPaymentPayService {
	/**
	 * 定义的接口描述
	 */
	private static final String PAYDESC = "申请付款工作流";

	/**
	 * esb地址配置Service
	 */
	private FossConfigEntityService fossConfigEntityService;

	/**
     * 是否月结标志
	 */
	private static final String ISMONTHFLAG = "1";

	/**
     * 数字比较，前者大
	 */
	private static final int GREATER = 1;

//	/**
//	 * 当前登录部门快递代理点部
//	 */
//	private String expressOrgUniCode = "";

	/**
	 * 获取日志
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(BillPaymentPayService.class);

	/**
	 * 注入付款单公共服务接口
	 */
	private IBillPaymentService billPaymentService;

	/**
	 * 注入应付单服务接口
	 */
	private IBillPayableService billPayableService;

	/**
	 * 注入应付单查询服务
	 */
	private IBillPayableQueryManageservice billPayableQueryManageservice;

	/**
	 * 注入预收单服务接口
	 */
	private IBillDepositReceivedService billDepositReceivedService;

	/**
	 * 注入预收单退预收服务
	 */
	private IBillDepositReceivedPayService billDepositReceivedPayService;

	/**
	 * 注入结算单据编号生成service
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 注入对费控接口
	 */
	private IPayToCostcontrolSendService payToCostcontrolSendService;

	/**
	 * 注入财务共享接口
	 */
	private IPayToFSSCSendService payToFSSCSendService;

	/**
	 * 注入锁服务
	 */
	private IBusinessLockService businessLockService;

	/**
	 * 注入配载单信息，在付款时如果为外请车，需要将明细传递过去
	 */
	private IOutsideVehicleChargeService outsideVehicleChargeService;

	/**
	 * 注入组织service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	/**
	 * 注入部门service
	 */
	private IOrgAdministrativeInfoService  orgAdministrativeInfoService;

	/**
	 * 快递代理点部映射
	 */
	private IExpressPartSalesDeptService expressPartSalesDeptService;

	/**
	 * 配置参数
	 */
	private IConfigurationParamsService configurationParamsService;

    /**
     * 营业部service
     */
    private ISaleDepartmentService saleDepartmentService;

    /**
     * 注入校验VTS单号+合同编码
     * @218392 zhangyongxue
     * @date 2016-06-22 13:30:15
     */
    private IClientSendVtsPayableCheck clientSendVtsPayableCheck;

	/**
	 * 合伙人-预收单退款接口编码
	 */
	private final static String PTP_WITHDRAW_SERVER_CODE = "/PTP_ESB2PTP_DEPOSIT_REFUND";
	/**
	 * 395982 zhengyating
	 */
    private IPaymentStatusToVTSClient paymentStatusToVTSClient;

	/**
	 * 录入付款单服务
	 * @param 付款单头
	 * @param 付款单明细列表
	 * @param 当前登录用户信息
	 * @author 045738-foss-maojianqiang
	 * @date 2012-11-27 下午7:48:17
	 */
    @SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	@Transactional
    public String addBillPaymentInfo(BillPaymentEntity paymentEntity, List<BillPaymentAddDto> addDtoList, CurrentInfo currentInfo) {

		logger.info("进入录入付款单service: 金额 " + paymentEntity.getAmount()
				+ " 明细数量：" + addDtoList.size());
		//获取付款单部门
		String paymentOrgCode = paymentEntity.getPaymentOrgCode();
		String paymentUnifiedCode = null;//声明付款部门标杆编码
		//付款部门编码
        if (StringUtils.isNotEmpty(paymentOrgCode)) {
			//查询付款部门编码
			OrgAdministrativeInfoEntity entity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(paymentOrgCode);
            if (entity == null || StringUtils.isEmpty(entity.getUnifiedCode())) {
				throw new SettlementException("付款部门不存在或找不到对应的标杆编码！");
			}
			paymentUnifiedCode = entity.getUnifiedCode();
		}
		// 声明当前日期
		Date now = new Date();
		// 获取单据编号 （此处单号是手动生成付款单）
		String paymentNum = settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.FK1);
		if (StringUtils.isBlank(paymentNum)) {
			throw new SettlementException("获取付款单号有误！");
		}

		//判断是否是合伙人提现
		String currDept = currentInfo.getCurrentDeptCode();//当前用户所在部门
        logger.info("预收单提现，用户所在部门是：" + currDept);
		SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentInfoByCode(currDept);
        if (null != saleDepartmentEntity && "Y".equals(saleDepartmentEntity.getIsLeagueSaleDept())) {//合伙人所在部门
            logger.info("合伙人部门下人员提现，合伙人部门编码是：" + currDept);
			//合伙人预付单提现
			String billType = addDtoList.get(0).getBillType();
			//合伙人提现只能使用电汇付款
			String paymentType = paymentEntity.getPaymentType();
            logger.info("\n\n预付单据子类型是：" + billType + "\n\n付款方式是：" + paymentType + "\n\n");
            if (!SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER.equals(paymentType)) {
				throw new SettlementException("合伙人预付单提现，付款方式只能电汇！");
			}
            if (SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__BILL_TYPE__DEPOSIT_PARTNER.equals(billType)) {
				//通知合伙人系统提现
                notifyPtp(paymentEntity, addDtoList, currentInfo, paymentNum);
			}
        } else {//非合伙人所在部门

		}


        //付款对接系统  Y--财务共享，N--代表费控
        String payToSystem = getPayToSystem();
		// 校验传入参数  -- -包含付款单实体的工作流类型也在里面进行封装
		Map map = null;
        if (SettlementDictionaryConstants.SETTLEMENT__PAYTOSYSTEM_TYPE_CONSCONTROL.equals(payToSystem)) {
            map = validateAddPayment(paymentEntity, addDtoList, paymentNum, currentInfo);
        } else if (SettlementDictionaryConstants.SETTLEMENT__PAYTOSYSTEM_TYPE_FSSC.equals(payToSystem)) {
            map = validateAddPaymentForFSSC(paymentEntity, addDtoList, paymentNum, currentInfo);
        } else {
			throw new SettlementException("付款工作流对接系统数据字典配置有误，请重新配置！");
		}
		//从map中获取应付单预收单集合
		List<BillPayableDto> payableList = (List<BillPayableDto>) map.get("payableList");
		List<BillDepositReceivedEntity> depositList = (List<BillDepositReceivedEntity>) map.get("depositList");
		//获取锁定运单集合
		List<MutexElement> mutexesList = (List<MutexElement>) map.get("mutexesList");
		//付款单明细列表
		List<BillPaymentDEntity> payDetailList = (List<BillPaymentDEntity>) map.get("payDetailList");

		//锁定运单
        if (CollectionUtils.isNotEmpty(mutexesList)) {
			businessLockService.lock(mutexesList, SettlementConstants.BUSINESS_LOCK_BATCH);
		}
		// 封装付款单实体
		dealPaymentEntity(paymentEntity, paymentNum, now);
		// 如果为电汇，生成汇款工作流,那么付款单上需要批次号的  --新增时候需要有批次号
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
                .equals(paymentEntity.getPaymentType())) {
			//获取付款批次号
			String batchNo = null;
            if (null != paymentEntity.getBatchNo() && !"".equals(paymentEntity.getBatchNo())) {
				batchNo = paymentEntity.getBatchNo();
            } else {
				batchNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.FK_BN);
			}
			//封装批次号
			paymentEntity.setBatchNo(batchNo);
		}

		// 新增付款单
        billPaymentService.addBillPaymentAndDetails(paymentEntity, payDetailList, currentInfo);

		// 更新应付单 --支付状态 、付款单号、付款金额、付款备注
		for (BillPayableDto payableDto : payableList) {
			billPayableService.payForBillPayable(payableDto, currentInfo);
		}
        //更新预收单
        for (BillDepositReceivedEntity en : depositList) {
			billDepositReceivedService.payForBillDepositReceived(en, currentInfo);
		}

        final int numberOfThreeHundred = 300;
        final int numberOfTwoNinetyNnine = 299;
		//需要重置下付款部门编码为标杆编码  ---2014-08 付款部门修改为应付单部门，不获取当前登录部门
		paymentEntity.setPaymentOrgCode(paymentUnifiedCode);
		// 如果为电汇，则需要调用费控接口，生成汇款工作流
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
				.equals(paymentEntity.getPaymentType())) {
			//对接费控
            if (SettlementDictionaryConstants.SETTLEMENT__PAYTOSYSTEM_TYPE_CONSCONTROL.equals(payToSystem)) {
				// 获取传递给费控参数
                PayToCostcontrolDto dto = buildPayToCostcontrolDto(paymentEntity, payableList, depositList,
						currentInfo);
                //往费控传递部门编码 --当前登录部门所对应的财务编码	 add by liqin
				try {
					//设置事由说明
                    if (StringUtils.isNotBlank(paymentEntity.getNotes())) {
						//备注
						String notes = paymentEntity.getNotes();
                        if (paymentEntity.getNotes().length() > numberOfThreeHundred) {
							//截取299个字
                            notes = paymentEntity.getNotes().substring(0, numberOfTwoNinetyNnine);
						}
						dto.setPayBillDiscription(notes);
					}

					// 调用接口传递数据
					payToCostcontrolSendService.payToCostcontrol(dto);
				} catch (Exception e) {
                    logger.error("申请付款工作流失败！" + e.getMessage(), e);
                    throw new SettlementException("申请付款工作流失败！" + e.getMessage(), e.getMessage());
				}
            } else if (SettlementDictionaryConstants.SETTLEMENT__PAYTOSYSTEM_TYPE_FSSC.equals(payToSystem)) {
				// 获取传递给财务自助参数
                PayToCostcontrolDto dto = buildPayToFSSCDto(paymentEntity, payableList, depositList, currentInfo);
				try {
					//设置事由说明
                    if (StringUtils.isNotBlank(paymentEntity.getNotes())) {
						//备注
						String notes = paymentEntity.getNotes();
                        if (paymentEntity.getNotes().length() > numberOfThreeHundred) {
							//截取299个字
                            notes = paymentEntity.getNotes().substring(0, numberOfTwoNinetyNnine);
						}
						dto.setPayBillDiscription(notes);
					}

					// 调用接口传递数据
					payToFSSCSendService.payToCostcontrol(dto);
				} catch (SettlementException e) {
                    logger.error("申请付款工作流失败！" + e.getErrorCode(), e);
					throw new SettlementException(e.getErrorCode());
				}
            } else {
				throw new SettlementException("付款工作流对接系统数据字典配置有误，请重新配置！");
			}
		}
		//解锁运单
        if (CollectionUtils.isNotEmpty(mutexesList)) {
			businessLockService.unlock(mutexesList);
		}
		logger.info("录入付款单end");
		return paymentNum;
	}

	/**
	 * 通知合伙人提现
	 * @author 302346-foss-jiangxun
	 * @date 2016-02-26 上午8:13:00
	 */
    public void notifyPtp(BillPaymentEntity paymentEntity, List<BillPaymentAddDto> addDtoList, CurrentInfo currentInfo, String paymentNum) {
		logger.info("\n\n合伙人预付单提现...\n\n");
		//提现金额小于1000元，抛出异常
        if (SettlementDictionaryConstants.WITHDRAW_MIN_PARTNER.compareTo(paymentEntity.getAmount()) == GREATER) {
            throw new SettlementException("提现金额小于" + SettlementDictionaryConstants.WITHDRAW_MIN_PARTNER + ",不能提现！");
        } else {
			//通知ptp生成提现明细
			logger.info("\n\n合伙人提现-合伙人校验并付款-start....\n\n");
			String url = "";
			//校验地址是否在ESB注册
			FossConfigEntity configEntity = fossConfigEntityService.queryFossConfigEntityByServerCode(PTP_WITHDRAW_SERVER_CODE);
			if (null != configEntity && !StringUtil.isEmpty(configEntity.getEsbAddr())) {
				url = configEntity.getEsbAddr();
			} else {
				logger.error("\n\n合伙人提现-合伙人校验并付款-读取esb地址有误:\n\n");
				throw new SettlementException("读取esb地址有误!");
			}
//			url = "http://10.224.165.113:8081/ptp-syncfoss-itf/v1/ptp/saleflow/fossDepositRefundService/refund";

			//发送请求
			HttpClient httpClient = new HttpClient();
			httpClient.getParams().setContentCharset("UTF-8");
			PostMethod postMethod = new PostMethod(url);
			//请求参数
			PtpWithdrawReqEntity req = new PtpWithdrawReqEntity();
			//响应参数
			PtpWithdrawRespEntity result = new PtpWithdrawRespEntity();
			//组装请求实体
            req = buildPtpWithdrawReqEntity(paymentEntity, addDtoList, currentInfo, paymentNum);
			Object obj = JSONObject.toJSON(req);
			String json = (null == obj) ? "" : obj.toString();
			try {
				logger.info("\n\n合伙人提现-合伙人校验并付款-请求参数:\n\n" + json);
				RequestEntity requestEntity = new StringRequestEntity(json, "application/json", "UTF-8");
				postMethod.setRequestEntity(requestEntity);
				postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
				String responseBody = "";
				// 执行postMethod
				httpClient.executeMethod(postMethod);
				// 获取返回值
				responseBody = postMethod.getResponseBodyAsString();
                if (responseBody == null || "".equals(responseBody)) {
					logger.error("\n\n合伙人提现-合伙人校验并付款-响应为空:\n\n");
					throw new Exception("\n\n合伙人提现-合伙人校验并付款-响应为空:\n\n");
				}
				logger.info("\n\n合伙人提现-合伙人校验并付款-响应信息:\n\n" + responseBody);

				// 将返回值转换成对象
				JSONObject returnJSON = JSONObject.parseObject(responseBody);
				result = JSONObject.toJavaObject(returnJSON, PtpWithdrawRespEntity.class);
				//异常信息
				if (!result.isResult()) {
                    logger.error("\n\n合伙人提现-合伙人校验并付款-合伙人系统校验失败:" + result.getMessage());
                    throw new Exception("合伙人系统校验失败，" + result.getMessage());
				}
            } catch (Exception e) {
                throw new SettlementException(e.getMessage(), e);
			}  finally {
				if (null != postMethod) {
					postMethod.releaseConnection();
				}
			}
			logger.info("\n\n合伙人提现-合伙人校验并付款-end....\n\n");
		}
	}

	/**
	 * 组装合伙人提现请求实体
	 * @author 302346-foss-蒋迅
	 * @date 2016-02-26 上午8:30:00
	 */
	public PtpWithdrawReqEntity buildPtpWithdrawReqEntity(BillPaymentEntity paymentEntity,
                                                          List<BillPaymentAddDto> addDtoList, CurrentInfo currentInfo, String paymentNum) {
		PtpWithdrawReqEntity ptpWithdrawReqEntity = new PtpWithdrawReqEntity();
		ptpWithdrawReqEntity.setAmount(paymentEntity.getAmount());//提现总金额
		ptpWithdrawReqEntity.setBizDate(new Date());//业务日期
		ptpWithdrawReqEntity.setCreateUserCode(currentInfo.getEmpCode());//创建人编码
		ptpWithdrawReqEntity.setCreateUserName(currentInfo.getEmpName());//创建人名称

		//根据合伙人部门编码，查询部门的标杆编码
		OrgAdministrativeInfoEntity origEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(currentInfo.getCurrentDeptCode());//合伙人部门编码
        if (null == origEntity) {
			logger.error("查询合伙人部门标杆编码失败，合伙人部门信息为空！");
			throw new SettlementException("查询合伙人部门标杆编码失败，合伙人部门信息为空！");
		}
        logger.info("合伙人部门标杆编码是：" + origEntity.getUnifiedCode());
		ptpWithdrawReqEntity.setPartnerOrgCode(origEntity.getUnifiedCode());//合伙人部门编码（标杆编码）

		ptpWithdrawReqEntity.setRefundType(SettlementDictionaryConstants.BILL_PAYMENT__BILL_TYPE__PAYMENT);//退款类别
		ptpWithdrawReqEntity.setRemark(paymentEntity.getNotes());//备注信息

		String sourceBillNos = "";
        for (int i = 0; i < addDtoList.size(); i++) {
            if (i == addDtoList.size() - 1) {
                sourceBillNos = sourceBillNos + addDtoList.get(i).getPayableNo();
            } else {
                sourceBillNos = sourceBillNos + addDtoList.get(i).getPayableNo() + ",";
			}
		}

		ptpWithdrawReqEntity.setSourceBillNo(paymentNum);//来源单号(付款单号)
		ptpWithdrawReqEntity.setSourceBillType(paymentEntity.getSourceBillType());//业务类别
		ptpWithdrawReqEntity.setSourceSystem(SettlementESBDictionaryConstants.ESB_HEADER__SOURCE_SYSTEM);//来源系统
		return ptpWithdrawReqEntity;
	}

	/**
	 * 更新付款工作流号
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-1 上午8:55:27
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillPaymentPayService#updateWorkFlow(java.util.List,
	 *      java.lang.String)
	 */
	@Override
	@Transactional
	public void updateWorkFlow(String paymentNos, String workflowNo) {
		logger.info("费控返回结果,更新备用金工作流号start: 付款编号" + paymentNos
				+ ",工作流号:" + workflowNo);
		// 校验传入更新付款单id集合
		if (StringUtils.isNotBlank(paymentNos)) {
			// 校验工作流号
			if (StringUtils.isNotBlank(workflowNo)) {
				// 声明dto
				BillPaymentDto dto = new BillPaymentDto();
				// 根据id查询对应的付款单实体
				List<BillPaymentEntity> billPayments = billPaymentService
						.queryPaymentByBatchNos(paymentNos,
								FossConstants.ACTIVE);
				dto.setBillPayments(billPayments);// 设置更新付款单集合
				dto.setWorkflowNo(workflowNo);// 设置工作流号
				// 反写付款单工作流号
                try {
					billPaymentService.batchReverseWorkFlowNoBillPayment(dto,
							SettlementUtil.getFKCurrentInfo());
					logger.info("更新付款工作流号end");
					/**
					 * FOSS向VTS回传字段信息需求
					 * @author 395982
					 * @date 2017-01-12
					 */
					BillPayableConditionDto payableDtos = new BillPayableConditionDto();
					//定义待传输的实体
					BillInfoEntity billInfoEntity = new BillInfoEntity();
					List<String> billIds = new ArrayList<String>();
					for(BillPaymentEntity paymentEntity : billPayments){
						logger.info("paymentEntity" + paymentEntity.toString());
						// 查询应付单
						payableDtos.setPaymentNo(paymentEntity.getPaymentNo());
						// 获取应付单列表
						List<BillPayableEntity> payableList = billPayableService
								.queryBillPayableByCondition(payableDtos);							
						//循环判断，将符合条件的应付单中的运单号放到一个list里面
						for(BillPayableEntity payable : payableList){
							logger.info("payable" + payable.toString());
							//判断应付单据的来源单据类型是否为TC
							if(null != payable && SettlementDictionaryConstants
									.BILL_PAYABLE__SOURCE_BILL_TYPE__TRANSPORT_CONTRACTS.equals(payable.getSourceBillType())){
								billIds.add(payable.getWaybillNo());
								}
							}
						}
					//设置运单号集合
					billInfoEntity.setBillIds(billIds);
					//工作流号
					billInfoEntity.setPaymentNumber(workflowNo);
					//汇款状态
					billInfoEntity.setPaymentStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRING);
					//调用VTS同步接口
					paymentStatusToVTSClient.sendPaymentStatusToVTS(billInfoEntity);
					logger.info("调用VTS同步接口成功");
                } catch (BusinessException e) {
                    logger.error(e.getMessage(), e);
					throw e;
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
					throw new SettlementException("更新付款工作流号失败！");
				}
			} else {
				logger.error("传入工作流号为空！");
				throw new SettlementException("传入工作流号为空！");
			}
		} else {
			logger.error("传入付款编号为空!");
			throw new SettlementException("传入付款编号为空！");
		}
	}

    /**
     * 对账单界面付款功能
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-11 下午7:47:31
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillPaymentPayService#payFormStatement(java.util.List, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
    public BillPayableManageResultDto payFormStatement(String[] billNos, CurrentInfo currentInfo) {
        logger.info("对账单查询界面付款功能开始：单号个数" + billNos.length);
		//单号集合
        if (billNos.length == 0) {
			throw new SettlementException("单号集合不能为空！");
		}
		//声明预收单集合
		List<BillDepositReceivedEntity> billDepositReceivedEntityList = new ArrayList<BillDepositReceivedEntity>();
		//声明应付单号集合
		String[] payableBillNos = {};
		//声明付款总金额
		BigDecimal totalAmount = BigDecimal.ZERO;
		//对传入单据进行分类，获取应付单和预收单集合列表
        for (int i = 0; i < billNos.length; i++) {
			//如果为应付单，则放到应付单数组中
            if (SettlementUtil.isPayable(billNos[i])) {
				//将数据添加到应付单号数组中
                payableBillNos = (String[]) ArrayUtils.add(payableBillNos, billNos[i]);
                //如果为预收单，则放到预收单集合中
            } else if (SettlementUtil.isDepositReceived(billNos[i])) {
				//声明预收单
				BillDepositReceivedEntity entity = new BillDepositReceivedEntity();
				//设置预收单实体
				entity.setDepositReceivedNo(billNos[i]);
				billDepositReceivedEntityList.add(entity);
                //如果不为应付单，也不是预收单，则抛出异常提示
            } else {
                throw new SettlementException("单号：" + billNos[i] + "不是应付单也不是预收单！");
			}
		}
		//如果应付单和预收单都为空，则提示
        if (CollectionUtils.isEmpty(billDepositReceivedEntityList) && payableBillNos.length == 0) {
			throw new SettlementException("传入单号集合中，没有要支付的应付单和预收单");
		}
		//声明跳转到付款界面的dto实体
		BillPayableManageResultDto resultDto = new BillPayableManageResultDto();
		//声明付款单实体
		BillPaymentEntity paymentEntity = new BillPaymentEntity();
		//声明付款单明细列表
		List<BillPaymentAddDto> addDtoList = new ArrayList<BillPaymentAddDto>();

		//如果存在应付单
        if (payableBillNos.length > 0) {
			//声明应付单付款服务要用的dto
			BillPayableManageDto dto = new BillPayableManageDto();
			dto.setBillNos(payableBillNos);
			//调用应付单付款服务进行校验
			BillPayableManageResultDto payableDto = billPayableQueryManageservice.payForBillPayable(dto);
			//如果应付单明细不为空，则返回
            if (CollectionUtils.isNotEmpty(payableDto.getAddDtoList())) {
				//如果应付单返回dto的付款单实体不为空，则取其付款单实体
                if (StringUtils.isNotEmpty(payableDto.getPaymentEntity().getCustomerCode())) {
					//获取付款单实体
					paymentEntity = payableDto.getPaymentEntity();
					//累加总金额
					totalAmount = totalAmount.add(paymentEntity.getAmount());
				}
				//将应付单明细添加到付款单明细中
				addDtoList.addAll(payableDto.getAddDtoList());
			}
		}
		//如果预收单实体集合大于0，则调用预收单退预收服务进行校验
        if (billDepositReceivedEntityList.size() > 0) {
			//获取一条应付单明细
            if (addDtoList.size() > 0) {
				BillPaymentAddDto dto = addDtoList.get(0);
				//获取应付单所属的付款工作流类型
                String workFlowType = getWorkFlowTypeByBillType(dto.getPayableNo(), dto.getBillType(), SettlementESBDictionaryConstants.COST_CONTROL_PAYMENT_PAY, null);

				//判断是否应付单的单据明细所属工作流类型都为foss日常，因为预收单是foss日常
                if (!StringUtils.equals(SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_DAY_PAY, workFlowType)
                        && !StringUtils.equals(SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_DAY_APPLY, workFlowType)) {
					throw new SettlementException("您所选择的应付单与预收单属于不同的付款工作流类型，不能进行付款操作！");
				}
			}

			//声明退预收dto
			BillDepositReceivedPayDto billDepositReceivedPayDto = new BillDepositReceivedPayDto();
			billDepositReceivedPayDto.setBillDepositReceivedEntityList(billDepositReceivedEntityList);
            //调用退预收服务，进行校验和封装
            BillDepositReceivedPayDto depositDto = billDepositReceivedPayService.backDepositReceived(billDepositReceivedPayDto, currentInfo, SettlementConstants.OPERATETYPE_DEPOSITRECEIVED);
			//如果预收单集合为空，则不添加
            if (CollectionUtils.isNotEmpty(depositDto.getAddDtoList())) {
				//如果上面应付单没数据，则付款单表头是要从预收单来的
                if (StringUtils.isBlank(paymentEntity.getCustomerCode())) {
					paymentEntity = depositDto.getPaymentEntity();
				//判断应付单和预收单客户一致，才能付款
                } else if (!paymentEntity.getCustomerCode().equals(depositDto.getPaymentEntity().getCustomerCode())) {
					throw new SettlementException("所选应付单和预收单的不是一个客户，不能一起进行付款！");
				}
				//累加总金额
				totalAmount = totalAmount.add(paymentEntity.getAmount());
				//将预收单明细添加到付款单明细中
				addDtoList.addAll(depositDto.getAddDtoList());
			}
		}
		//重新设置总金额
		paymentEntity.setAmount(totalAmount);

		//判断最终付款单明细是否为空
        if (addDtoList.size() == 0) {
			throw new SettlementException("没有查询到要支付的应付单和预收单");
		}
		//包装传出数据
		resultDto.setAddDtoList(addDtoList);
		resultDto.setPaymentEntity(paymentEntity);
		logger.info("对账单查询界面付款功能结束");
		return resultDto;
	}

	/**
	 * 封装给费控的付款接口参数
     *
	 * @author 045738-foss-maojianqiang
	 * @date 2012-11-30 下午1:47:19
	 */
    private PayToCostcontrolDto buildPayToCostcontrolDto(BillPaymentEntity bill, List<BillPayableDto> payableList
            , List<BillDepositReceivedEntity> depositList, CurrentInfo currentInfo) {
		// 声明传递给费控的dto
		PayToCostcontrolDto dto = new PayToCostcontrolDto();
		//如果应付单和预收单集合都为空，则抛出异常
        if (CollectionUtils.isEmpty(payableList) && CollectionUtils.isEmpty(depositList)) {
			throw new SettlementException("费用明细不存在，不能进行付款！");
		}
		//声明付款明细
		List<ExpenseDetail> detail = new ArrayList<ExpenseDetail>();
		//因为业务规定，电汇只可能有一种工作流。故而如果存在预收单列表，肯定只能是FOSS日常工作流
        if (CollectionUtils.isNotEmpty(depositList)) {
			//循环预收单
            for (BillDepositReceivedEntity deposit : depositList) {
				ExpenseDetail d = new ExpenseDetail();
				d.setBillNumber(deposit.getDepositReceivedNo());//明细单号
				d.setExpenseExplain(deposit.getDepositReceivedNo());//费用说明  --ISSUE-3009
				d.setExpensesMoney(deposit.getPaymentAmount());//明细金额
				d.setExpensesType(SettlementESBDictionaryConstants.COST_CONTROL_PAY_DETAIL_DEPOSIT_RECEIVED);//退预收
				d.setPayBillExpDate(deposit.getBusinessDate());//业务发生日期
				d.setExpenseCostCenter(currentInfo.getDept().getUnifiedCode());//部门标杆编码
				detail.add(d);
			}
		}
		//非空判断
        if (CollectionUtils.isNotEmpty(payableList)) {
			//申请配载车次号集合
			List<String> trunkNos = new ArrayList<String>();
			//是否押回单到达  --针对费控需要押回单到达单独付款
            boolean isReturnBackBalance = false;
			//是否外请车--外请车是单独付款故而只需要判断一条是即可
            List<String> driverList = Arrays.asList(SettlementESBDictionaryConstants.WORKFLOW_DETAIL_DRIVER);
			boolean isTrunckFlag = (driverList.contains(payableList.get(0).getBillType()));
 			//循环应付单
            for (BillPayableDto payable : payableList) {
				//如果是理赔则需要去分摊部门做点事情
                if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM.equals(payable.getBillType())) {
					//声明接口传入参数
					BillPayableEntity payableEntity = new BillPayableEntity();
					payableEntity.setPayableNo(payable.getPayableNo());
					payableEntity.setBillType(payable.getBillType());
					payableEntity.setBusinessDate(payable.getBusinessDate());
                    payableEntity.setPayableOrgCode(currentInfo.getCurrentDeptCode());//直接传入当前部门编码
 					payableEntity.setPaymentAmount(payable.getPaymentAmount());
 					payableEntity.setWaybillNo(payable.getWaybillNo());//理赔增加运单号
					//进行理赔应付单费用分摊
					payToCostcontrolSendService.sharePayableForBad(detail, payableEntity, PAYDESC);
					continue;
					//如果为外请车，则需要获取配载单信息
                } else if (isTrunckFlag) {
					trunkNos.add(payable.getSourceBillNo());
				}
				ExpenseDetail d = new ExpenseDetail();
				d.setBillNumber(payable.getPayableNo());//明细单号
				//ISSUE-3009 修改费用说明 对理赔、退运费、服务补救、装卸费传递运单号在费用说明中
                if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__REFUND.equals(payable.getBillType())
                        || SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION.equals(payable.getBillType())
                        || SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE.equals(payable.getBillType())) {
					d.setExpenseExplain(payable.getWaybillNo());//费用说明
                } else {
					d.setExpenseExplain(PAYDESC);//费用说明
				}

				d.setExpensesMoney(payable.getPaymentAmount());//明细金额
				//转化给对应费控那边费用明细
                String payType = convertBillType(payable.getPayableNo(), payable.getBillType());
				d.setExpensesType(payType);
				d.setPayBillExpDate(payable.getBusinessDate());//业务发生日期
				d.setExpenseCostCenter(currentInfo.getDept().getUnifiedCode());//部门标杆编码
				detail.add(d);
			}
            //如果不为空，则封装配载单信息 --调用中转接口
            if (CollectionUtils.isNotEmpty(trunkNos)) {
				BillPayableDto payable = payableList.get(0);
				//判断是否为押回单到达付款
                if (StringUtils.isEmpty(payable.getPayableType())) {
                    throw new SettlementException("应付单：" + payable.getPayableNo() + "的付款类型为空！没法判断是否为押回单到达！");
				//如果付款为押回单到达，则设置值
                } else if (SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__LAST_BACK.equals(payable.getPayableType())) {
                    isReturnBackBalance = true;
				}
				List<OutVehicleAssembleBillAndFeeVo> vehicleAssembleList = null;
				//调用中转接口获取配载单信息
                try {
					vehicleAssembleList = outsideVehicleChargeService.queryOutVehicleAssembleBillAndFeeVoList(trunkNos);
                } catch (BusinessException e) {
                    throw new SettlementException("调用中转接口获取配载单信息异常：" + e.getMessage());
				}
				//声明配载单信息
				List<StowageEntity> stowageDetail = new ArrayList<StowageEntity>();
				//进行数据转化
                getStowageEntityList(stowageDetail, vehicleAssembleList, trunkNos, isReturnBackBalance, currentInfo);
				//设置配载单信息
				dto.setStowageEntityList(stowageDetail);
			}

        }
		// 声明付款集合
		dto.setPaymentBillNo(bill.getBatchNo());//获取付款编号
		dto.setPayBillDeptNo(bill.getPaymentOrgCode());// 获取部门标杆编码
		dto.setPayBillDeptName(bill.getPaymentOrgName());// 获取部门名称
		dto.setPayBillAmount(bill.getAmount());// 获取总金额
		dto.setPayBillComNo(bill.getInvoiceHeadCode());// 获取公司编码
		dto.setPayBillBankNo(bill.getAccountNo());// 获取银行账号
		dto.setAccountType(bill.getAccountType());//账户类型
		dto.setBankHqCode(bill.getBankHqCode());//开户行编码
		dto.setBankBranchCode(bill.getBankBranchCode());//开户行支行编码
		dto.setProvinceCode(bill.getProvinceCode());//省份编码
		dto.setCityCode(bill.getCityCode());//城市编码
		dto.setPayBillPayeeName(bill.getPayeeName());// 获取收款人名称
		dto.setPayBillPayeeCode(bill.getPayeeCode());//收款人编码
		dto.setPayBillCelephone(bill.getMobilePhone());// 获取联系方式
		dto.setPayBillAppNo(currentInfo.getEmpCode());// 获取员工编号
		dto.setPayBillLastTime(bill.getCreateDate());// 获取最迟汇款日期 --前台封装到createDate上
		dto.setPayBillAppType(bill.getWorkFlowType());//工作流类型
		dto.setPayType(SettlementESBDictionaryConstants.COST_CONTROL_PAYMENT_PAY);//支付类型  --付款
		dto.setExpenseDetail(detail);
		dto.setBatchNo(bill.getBatchNo());//工作流号
		return dto;
	}

	/**
	 * 新增校验,并将更新应付单需要参数进行封装到dto中
     *
	 * @author 045738-foss-maojianqiang
	 * @date 2012-11-28 下午6:49:33
	 */
    @SuppressWarnings({"rawtypes", "unchecked"})
	private Map validateAddPayment(
			BillPaymentEntity paymentEntity,
            List<BillPaymentAddDto> addDtoList, String paymentNum, CurrentInfo currentInfo) {
		logger.info("付款单新增时校验开始");
		//声明工作流类型    --每次付款只能付同一种工作流类型的明细数据
		String workFlowType = null;
		//声明押回单到达标志  --给后面生成付款用
		boolean isReturnBackbalance = false;
		//是否月结
		boolean isMonth = false;
		//声明付款类型 报销还是付款
		String payType = null;
		//如果付款方式为电汇，表示为付款，反之为报销
        if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
                .equals(paymentEntity.getPaymentType())) {
			payType = SettlementESBDictionaryConstants.COST_CONTROL_PAYMENT_PAY;//付款
        } else {
			payType = SettlementESBDictionaryConstants.COST_CONTROL_PAYMENT_APPLY;//报销
		}

		//校验界面传入参数
        validateParams(paymentEntity, addDtoList);

		// 声明本次付款金额总和
		BigDecimal payableUnVerifyAmount = BigDecimal.ZERO;
		BigDecimal depositUnVerifyAmount = BigDecimal.ZERO;

		// 声明要更新的应付单集合
		List<BillPayableDto> billPayables = new ArrayList<BillPayableDto>();
		//声明要更新的预收单集合
		List<BillDepositReceivedEntity> depositList = new ArrayList<BillDepositReceivedEntity>();
		//声明锁对象集合
		List<MutexElement> mutexesList = new ArrayList<MutexElement>();
		//声明付款单明细
		List<BillPaymentDEntity> payDetailList = new ArrayList<BillPaymentDEntity>();

		//声明应付单单号列表
		List<String> payableNos = new ArrayList<String>();
		//声明预收单单号列表
		List<String> depositReceivedNos = new ArrayList<String>();
		//声明付款单明细单号对应的支付金额map
        Map<String, BigDecimal> amountMapByNo = new HashMap<String, BigDecimal>();

		// 循环获取dto中本次付款金额
		for (BillPaymentAddDto dto : addDtoList) {
			//校验界面传入id
            if (StringUtils.isBlank(dto.getId())) {
				throw new SettlementException("付款单明细中应付单号："
						+ dto.getPayableNo() + "的明细id为空！");
			}

			//如果为应付单
            if (SettlementUtil.isPayable(dto.getPayableNo())) {
				payableNos.add(dto.getPayableNo());
				amountMapByNo.put(dto.getPayableNo(), dto.getCurrentPaymentAmount());
				//封装应付单要用的dtolist
				BillPayableDto entity = new BillPayableDto();
				entity.setId(dto.getId());// 封装应付单id
				entity.setPayableNo(dto.getPayableNo());//应付单号
				entity.setAccountDate(dto.getAccountDate());// 封装记账日期
				entity.setBusinessDate(dto.getBusinessDate());//封装业务日期
				entity.setVersionNo(dto.getVersionNo());// 封装版本号
				entity.setPaymentAmount(dto.getCurrentPaymentAmount());// 封装本次付款金额
				entity.setPaymentNo(paymentNum);// 封装付款单号
				entity.setPaymentNotes(dto.getNotes());// 封装本次付款明细备注
				entity.setTaxAmount(dto.getTaxAmount());//设置税金
				entity.setTax(dto.getTax());//设置税金
				entity.setBusinessOfDate(dto.getBusinessOfDate());//设置业务日期
				//如果为装卸费、服务补救或理赔应付单，必须全额支付
                if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE.equals(dto.getBillType())
                        || SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION.equals(dto.getBillType())
                        || SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM.equals(dto.getBillType())) {
					//如果付款金额与未核销金额不相等，则抛出异常
                    if (dto.getCurrentPaymentAmount().compareTo(dto.getUnverifyAmount()) != 0) {
                        throw new SettlementException("应付单：" + dto.getPayableNo() + "为装卸费或服务补救或理赔应付单，必须全额付款！");
					}
				}
				//将数据封装到应付单dto的集合中
				billPayables.add(entity);
				//累加应付单金额
				payableUnVerifyAmount = payableUnVerifyAmount.add(dto.getCurrentPaymentAmount());

			//声明预收单
            } else if (SettlementUtil.isDepositReceived(dto.getPayableNo())) {
				depositReceivedNos.add(dto.getPayableNo());
				amountMapByNo.put(dto.getPayableNo(), dto.getCurrentPaymentAmount());
				//声明与预付单集合
				BillDepositReceivedEntity entity = new BillDepositReceivedEntity();
				entity.setId(dto.getId());// 封装预收单id
				entity.setDepositReceivedNo(dto.getPayableNo());// 封装预收单号
				entity.setVersionNo(dto.getVersionNo());// 封装版本号
				entity.setPaymentAmount(dto.getCurrentPaymentAmount());// 封装本次付款金额
				entity.setPaymentNo(paymentNum);// 封装付款单号
				entity.setPaymentNotes(dto.getNotes());// 封装本次付款明细备注
				entity.setBusinessDate(dto.getBusinessDate());
				entity.setAccountDate(dto.getAccountDate());
				//将前台数据封装到预收单dto集合中
				depositList.add(entity);
				//累加应付单金额
				depositUnVerifyAmount = depositUnVerifyAmount.add(dto.getCurrentPaymentAmount());
            } else {
				throw new SettlementException("付款单明细中应付单号："
						+ dto.getPayableNo() + "的单据类型不是应付或预收单！");
			}
        }
		//查询预收单
        if (depositReceivedNos.size() > 0) {
			//调用预收单接口查询应付单信息
			List<BillDepositReceivedEntity> entityList = billDepositReceivedService.queryByDepositReceivedNOs(depositReceivedNos, FossConstants.ACTIVE);
			//如果查询预收单条数与传入条数不等，则弹出提示
            if (entityList.size() != depositReceivedNos.size()) {
				throw new SettlementException("查询预收单明细与传入明细不等，请关闭界面，重新制作！");
			}
			//校验新查询数据与界面传入的dto是否一致
            for (int i = 0; i < entityList.size(); i++) {
				//获取预收单entity
				BillDepositReceivedEntity entity = entityList.get(i);
				//循环界面列表集合，与查询出来数据进行比较
                for (int j = 0; j < depositList.size(); j++) {
					//获取dto
                    BillDepositReceivedEntity viewEntity = depositList.get(j);
					//比较新查询出数据与界面数据是否一致，根据同一单号
                    if (entity.getDepositReceivedNo().equals(viewEntity.getDepositReceivedNo())) {
						//判断版本号是否一致
                        if (!entity.getVersionNo().equals(viewEntity.getVersionNo())) {
                            throw new SettlementException("付款单明细中预收单号：" + entity.getDepositReceivedNo() + "的数据已经发生变化，请刷新预收单或对账单页面，重新进行付款");
						}
						//给depositList中明细封装businessDate和acctdate,单据类型、给后面费控明细要用到
						viewEntity.setBusinessDate(entity.getBusinessDate());
						viewEntity.setAccountDate(entity.getAccountDate());
						viewEntity.setBillType(entity.getBillType());
						break;
					}
				}
				//声明付款单明细
				BillPaymentDEntity dEntity = new BillPaymentDEntity();
				//设置值
				dEntity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYMENT_D_SOURCE_BILL_TYPE_DEPOSIT_RECEIVED);
				//设置值
				dEntity.setSourceBillNo(entity.getDepositReceivedNo());
				//设置值
				dEntity.setSourceAccountDate(entity.getAccountDate());
				//设置支付金额
				dEntity.setPayAmount(amountMapByNo.get(entity.getDepositReceivedNo()));
				//添加到明细列表中
				payDetailList.add(dEntity);
			}
			//声明校验dto
			BillDepositReceivedPayDto billDepositReceivedPayDto = new BillDepositReceivedPayDto();
			//包装要校验的预收单集合
			billDepositReceivedPayDto.setBillDepositReceivedEntityList(entityList);
            billDepositReceivedPayService.backDepositReceived(billDepositReceivedPayDto, currentInfo, SettlementConstants.OPERATETYPE_PAYMENT);
			//声明工作流类型为foss日常
            if (SettlementESBDictionaryConstants.COST_CONTROL_PAYMENT_PAY.equals(payType)) {
				//FOSS日常--付款
				workFlowType = SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_DAY_PAY;
            } else {
				//FOSS日常--报销
				workFlowType = SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_DAY_APPLY;
			}
		}

		//查询应付单
        if (payableNos.size() > 0) {
			//调用应付单接口查询应付单信息
			List<BillPayableEntity> payableList = billPayableService.queryByPayableNOs(payableNos, FossConstants.ACTIVE);
			//如果查询应付单条数与传入条数不等，则弹出提示
            if (payableList.size() != payableNos.size()) {
				throw new SettlementException("查询付款单明细与传入明细不等，请关闭界面，重新制作！");
			}
			//校验新查询数据与界面传入的dto是否一致
            for (int i = 0; i < payableList.size(); i++) {
				//获取应付单entity
				BillPayableEntity entity = payableList.get(i);

				//如果是第一次循环，则要获取
                if (i == 0 && StringUtils.isBlank(workFlowType)) {
                    workFlowType = getWorkFlowTypeByBillType(entity.getPayableNo(), entity.getBillType(), payType, paymentEntity.getCostType());
                } else {
					//获取下一次的类型与第一次工作流类型进行比较
                    String workFlowTypeNew = getWorkFlowTypeByBillType(entity.getPayableNo(), entity.getBillType(), payType, paymentEntity.getCostType());
					//如果两次工作流类型不等，则抛出异常
                    if (!StringUtils.equals(workFlowType, workFlowTypeNew)) {
						throw new SettlementException("该付款单中存在不同付款工作流类型的单据，不能进行付款！");
					}
				}
				//理赔工作流类型      268217
                if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM.equals(entity.getBillType()) &&
						SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
                                .equals(paymentEntity.getPaymentType())) {
                    workFlowType = SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PAY_APPLY_CLAIM;
				}
				BillPayableDto dto = null;
				//循环界面列表集合，与查询出来数据进行比较
                for (int j = 0; j < billPayables.size(); j++) {
					//获取dto
					dto = billPayables.get(j);
					//比较新查询出数据与界面数据是否一致，根据同一单号
                    if (entity.getPayableNo().equals(dto.getPayableNo())) {
						//判断版本号是否一致
                        validaExtracted(paymentEntity, entity, dto);
						//给billPayables中明细封装businessDate和acctdate,单据类型、给后面费控明细要用到
						dto.setBusinessDate(entity.getBusinessDate());
						dto.setAccountDate(entity.getAccountDate());
						dto.setBillType(entity.getBillType());
						dto.setSourceBillNo(entity.getSourceBillNo());//封装来源单据编号 --给外请车付款用来查询配载单数据
						dto.setPayableType(entity.getPayableType());//获取支付类型
						dto.setWaybillNo(entity.getWaybillNo());
						//跳出循环
						break;
					}
				}
				//声明锁定运单对象
                if (StringUtils.isNotBlank(entity.getWaybillNo())) {
					MutexElement mtx = new MutexElement(entity.getWaybillNo(), "录入付款单锁定运单", MutexElementType.WAYBILL_NO);
					mutexesList.add(mtx);
				}
				//声明付款单明细
				BillPaymentDEntity dEntity = new BillPaymentDEntity();
				//设置值
				dEntity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYMENT_D_SOURCE_BILL_TYPE_PAYABLE);
				//设置值
				dEntity.setSourceBillNo(entity.getPayableNo());
				//设置值
				dEntity.setWaybillNo(entity.getWaybillNo());
				//设置值
				dEntity.setSrcSourceBillNo(entity.getSourceBillNo());
				//设置值
				dEntity.setSourceAccountDate(entity.getAccountDate());
				//设置支付金额
				dEntity.setPayAmount(amountMapByNo.get(entity.getPayableNo()));
				//设置税金
				dEntity.setTaxAmount(dto.getTaxAmount());
				//设置税后金额
				dEntity.setTax(dto.getTax());
				//设置业务发生日期
				dEntity.setBusinessOfDate(dto.getBusinessOfDate());
				//添加到明细列表中
				payDetailList.add(dEntity);

			}
			//获取应付单entity
			BillPayableEntity entity = payableList.get(0);
			//如果一条为外请车尾款押回单，则该付款单都为押回单到达
            if (StringUtils.isNotEmpty(entity.getPayableType())
                    && SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__LAST_BACK.equals(entity.getPayableType())) {
				isReturnBackbalance = true;
            } else if (StringUtils.isNotEmpty(entity.getPayableType())
                    && SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MONTH.equals(entity.getPayableType())) {
				isMonth = true;
			}
			//校验应付单数据是否可以支付
            billPayableQueryManageservice.validateCanPay(payableList, paymentEntity.getPaymentType());
		}

		// 校验明细本次付款金额总和和付款单头的金额是否相等
		if (paymentEntity.getAmount() != null
				&& paymentEntity.getAmount().compareTo(payableUnVerifyAmount.add(depositUnVerifyAmount)) != 0) {
			throw new SettlementException("本次付款金额明细之和与付款单金额不等！");
		}

		//在此处来确认付款单的工作流类型
        if (StringUtils.isNotBlank(workFlowType)) {
			paymentEntity.setWorkFlowType(workFlowType);
        } else {
			throw new SettlementException("付款工作流类型为不能为空！");
		}
		//在此处封装押回单到达到付款单的审核意见上。 --只有该字段没用，可以暂时封装给用户看
        if (isReturnBackbalance) {
			paymentEntity.setAuditOpinion(SettlementConstants.RETURN_BACK_BALANCE);
		}
		//如果为月结，则设置月结标志
        if (isMonth) {
			paymentEntity.setAuditOpinion(SettlementConstants.PAYABLE_TYPE__MONTH);
		}

		//校验付款单明细
        if (CollectionUtils.isEmpty(payDetailList)) {
			throw new SettlementException("付款单明细不能为空！");
		}
        //声明传出map
        Map map = new HashMap();
		//封装付款单列表
		map.put("payableList", billPayables);
		//封装付款单列表
		map.put("depositList", depositList);
		//封装所列表
		map.put("mutexesList", mutexesList);
		//封装付款单明细列表
		map.put("payDetailList", payDetailList);
		logger.info("付款单新增时校验结束");
		return map;
	}

	private void validaExtracted(BillPaymentEntity paymentEntity,
			BillPayableEntity entity, BillPayableDto dto) {
		if (!entity.getVersionNo().equals(dto.getVersionNo())) {
		    throw new SettlementException("付款单明细中应付单号：" + dto.getPayableNo() + "的数据已经发生变化，请刷新应付单或对账单页面，重新进行付款");
		}
		//如果付款为现金时，需要校验装卸费
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH.equals(paymentEntity.getPaymentType())
		        && SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE.equals(entity.getBillType())) {
			//如果单笔装卸费小于1000元，且现金付款，则抛出异常
		    if (SettlementConstants.SERVICE_FEE_PAY_CASH__MAX.compareTo(dto.getPaymentAmount()) < 0) {
		        throw new SettlementException("应付单：" + entity.getPayableNo() + "为装卸费，其单笔支付金额大于1000元，只能进行电汇付款！");
			}
		}
	}

	/**
	 * 根据传入单据类型确认是那种工作流类型
     * @param billType
     * @return
	 * @author 045738-foss-maojianqiang
	 * @date 2013-1-14 上午11:48:44
	 */
    public static String getWorkFlowTypeByBillType(String number, String billType, String payType, String costType) {
				//声明工作流类型
				String workFlowType = null;
				//获取日常工作流明细类型
				List<String> dayList = Arrays.asList(SettlementESBDictionaryConstants.WORKFLOW_DETAIL_DAY);
				//获取成本工作流明细类型
				List<String> costList = Arrays.asList(SettlementESBDictionaryConstants.WORKFLOW_DETAIL_COST);
				//获取外请车工作流类型  --财务共享用
        List<String> driverList = Arrays.asList(SettlementESBDictionaryConstants.WORKFLOW_DETAIL_DRIVER);
				//报账工作流
				List<String> packList = Arrays.asList(SettlementESBDictionaryConstants.WORKFLOW_DETAIL_PACK);
				//合伙人工作流 @218392 备注有问题，本次修改
				List<String> partnerList = Arrays.asList(SettlementESBDictionaryConstants.PARTNER_DETAIL_PACK);
				//单据类型不能为空
        if (StringUtils.isBlank(billType)) {
            throw new SettlementException("应付单号：" + number + "的单据类型为空，不能确认是那种工作流类型！");
				}
        logger.info("\n\n结算请求fssc，判断哪种工作流。单据子类型:" + billType + "\n\n");
				//如果为日常工作流明细
        if (dayList.contains(billType)) {
					//根据传入付款类型 - 付款/报销，判断那种类型付款类型
            if (SettlementESBDictionaryConstants.COST_CONTROL_PAYMENT_PAY.equals(payType)) {
						workFlowType = SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_DAY_PAY;//日常工作流 --付款
            } else {
						workFlowType = SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_DAY_APPLY;//日常工作流 --报销
					}
					//理赔应付工作流类型    268217
            if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM.equals(billType) &&
                    SettlementESBDictionaryConstants.COST_CONTROL_PAYMENT_PAY.equals(payType)) {
                workFlowType = SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PAY_APPLY_CLAIM;
					}
				//如果为成本工作流明细
        } else if (costList.contains(billType)) {
            //根据传入付款类型 - 付款/报销，判断那种类型成本类型
            if (SettlementESBDictionaryConstants.COST_CONTROL_PAYMENT_PAY.equals(payType)) {
						//如果系统对接财务共享且为外请车，则声称外请车工作流
                if (driverList.contains(billType)) {
							workFlowType = SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_DRIVER_PAY;//外请车付款
                } else {
							workFlowType =  SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_PAY;//成本工作流 --付款
						}
            } else {
						//如果系统对接财务共享且为外请车，则声称外请车工作流
                if (driverList.contains(billType)) {
							workFlowType = SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_DRIVER_APPLY;//外请车报销
                } else {
							workFlowType = SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_APPLY;//成本工作流--报销
						}
					}
        } else if (packList.contains(billType)) {
					workFlowType = SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PACK_WOODEN_PAY;
            //临时租车
        } else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__RENT_CAR.equals(billType)) {
            if (StringUtils.isBlank(costType)) {
                throw new SettlementException("应付单：" + number + "的费用类型不能为空！");
					}
					//根据传入付款类型 - 付款/报销，判断那种类型付款类型
            if (SettlementESBDictionaryConstants.COST_CONTROL_PAYMENT_PAY.equals(payType)) {
                if (SettlementDictionaryConstants.RENTCAR_COST_TYPE_TRAFFIC_FEE.equals(costType)) {
							workFlowType = SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_RENT_CAR_TRAFFIC_PAY;//临时租车-付款(交通费)
                } else {
							workFlowType = SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_RENT_CAR_PAY;//临时租车-交通费付款
						}
            } else {
                if (SettlementDictionaryConstants.RENTCAR_COST_TYPE_TRAFFIC_FEE.equals(costType)) {
							workFlowType = SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_RENT_CAR_TRAFFIC_APPLY;//日常工作流 --报销
                } else {
							workFlowType = SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_RENT_CAR_APPLY;//日常工作流 --报销
						}
					}
				//合伙人
        } else if (partnerList.contains(billType)) {
					workFlowType = SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PARTNER_PAY;
				//合伙人预收提现
        } else if (SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__BILL_TYPE__DEPOSIT_PARTNER.equals(billType)) {
					//“合伙人提现”工作流
					//根据传入付款类型 - 付款/报销，判断那种类型付款类型
            if (SettlementESBDictionaryConstants.COST_CONTROL_PAYMENT_PAY.equals(payType)) {
						//支付类型——付款（电汇）
						workFlowType = SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PARTNER_WITHDRAW_PAY;
            } else {
						//支付类型——报销（现金）
						throw new SettlementException("\n\n合伙人提现，不能申请非电汇（付款）的工作流！\n\n");
					}
            logger.info("\n\n结算请求fssc，生成合伙人提现工作流。工作流编码:" + workFlowType + "\n\n");
        } else {
            throw new SettlementException("应付单：" + number + "的单据子类型不在付款工作流明细类型中，不能判断是何种工作流！");
				}
        logger.info("\n\n结算请求fssc。工作流编码:" + workFlowType + "\n\n");
				return workFlowType;
	}

	/**
	 * 校验界面传入参数
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-4 上午9:46:28
	 */
    private void validateParams(BillPaymentEntity paymentEntity, List<BillPaymentAddDto> addDtoList) {
		// 如果明细size小于0，则弹出提示
		if (CollectionUtils.isEmpty(addDtoList)) {
			throw new SettlementException("录入付款单，至少有一条明细数据！");
		}
		// 校验前台必填项
		if (paymentEntity != null) {
//			// 如果客户编码为空，则抛出异常
//			if (StringUtils.isBlank(paymentEntity.getCustomerCode())) {
//				throw new SettlementException("客户编码不能为空！");
//			}
//			// 如果部门编码为空，则抛出异常
//			if (StringUtils.isBlank(paymentEntity.getPaymentOrgCode())) {
//				throw new SettlementException("部门编码不能为空！");
//			}
			// 判断部门名称
			if (StringUtils.isBlank(paymentEntity.getPaymentOrgName())) {
				throw new SettlementException("部门名称不能为空！");
			}
			// 如果子公司编码为空，则抛出异常
			if (StringUtils.isBlank(paymentEntity.getPaymentCompanyCode())) {
				throw new SettlementException("子公司编码不能为空！");
			}
			// 付款方式校验
			if (StringUtils.isBlank(paymentEntity.getPaymentType())) {
				throw new SettlementException("付款方式不能为空！");
			}

			// 付款金额校验
			if (paymentEntity.getAmount() == null
					|| paymentEntity.getAmount().compareTo(BigDecimal.ZERO) < 1) {
				throw new SettlementException("金额不能为空且必须大于0！");
			}
			//来源单据类型校验  （作废要用到该字段）
			if (StringUtils.isBlank(paymentEntity.getSourceBillType())) {
				throw new SettlementException("来源单据类型不能为空！");
			}

			// 如果代打木架应付，付款方式必须为电汇
            if (StringUtils.equals(addDtoList.get(0).getBillType(), SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__WOODEN_PAYABLE)
                    && !StringUtils.equals(paymentEntity.getPaymentType(), SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER)) {
				throw new SettlementException("代打木架付款时，付款方式必须为电汇！");
			}

			// 如果折扣应付，不允许付款
            if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__DISCOUNT.equals(addDtoList.get(0).getBillType())) {
				throw new SettlementException("折扣应付不允许付款！");
			}

			// 如果付款方式不能为空，且为电汇，则需要判断银行等信息
			if (StringUtils.isNotBlank(paymentEntity.getPaymentType())
					&& SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
							.equals(paymentEntity.getPaymentType())) {
				// 如果银行账号为空，则抛出异常
				if (StringUtils.isBlank(paymentEntity.getAccountNo())) {
					throw new SettlementException("付款方式为电汇时银行账号不能为空！");
				}
				// 省份编码不能为空
				if (StringUtils.isBlank(paymentEntity.getProvinceCode())) {
					throw new SettlementException("省份编码不能为空！");
				}
				// 城市编码不能为空
				if (StringUtils.isBlank(paymentEntity.getCityCode())) {
					throw new SettlementException("城市编码不能为空！");
				}
				// 开户银行不能为空
				if (StringUtils.isBlank(paymentEntity.getBankHqCode())) {
					throw new SettlementException("开户银行编码不能为空！");
				}
				// 行号不能为空
				if (StringUtils.isBlank(paymentEntity.getBankBranchCode())) {
					throw new SettlementException("行号不能为空！");
				}
				// 账户类型
				if (StringUtils.isBlank(paymentEntity.getAccountType())) {
					throw new SettlementException("账户类型不能为空！");
				}
				// 收款人不能为空
				if (StringUtils.isBlank(paymentEntity.getPayeeName())) {
					throw new SettlementException("收款人不能为空！");
				}
				// 收款人手机号不能为空
				if (StringUtils.isBlank(paymentEntity.getMobilePhone())) {
					throw new SettlementException("收款人手机号不能为空！");
				}
			}

			// 如果代打木架应付，付款方式必须为电汇
            if (StringUtils.equals(addDtoList.get(0).getBillType(), SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__RENT_CAR)
                    && (StringUtils.isBlank(paymentEntity.getCostDeptCode()) || StringUtils.isBlank(paymentEntity.getCostType()))) {
				throw new SettlementException("临时租车付款，费用承担部门和费用类型不能为空！");
			}
			//校验增值税发票号是否输入，如果勾选了是增值税发票，则必输
            if (paymentEntity.getIsTaxinvoice()
                    && StringUtils.isEmpty(paymentEntity.getVatInvoice())) {
				throw new SettlementException("勾选是否增值税发票后，发票号必输！");
			}

		}
	}

	/**
	 * 新增时，处理付款单实体，保存
     * @param 付款单实体 付款单号 当前日期
	 * @author 045738-foss-maojianqiang
	 * @date 2012-11-28 下午4:18:37
	 */
	private BillPaymentEntity dealPaymentEntity(
			BillPaymentEntity paymentEntity, String paymentNum, Date nowDate) {
		paymentEntity.setId(UUIDUtils.getUUID());// 获取id
		paymentEntity.setPaymentNo(paymentNum);// 设置单号
		paymentEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);// 设置币种
		// 如果为电汇，则更新付款单状态为付款中，现金为未付款
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
				.equals(paymentEntity.getPaymentType())) {
			paymentEntity
					.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRING);// 设置汇款状态为”汇款中“
		} else {
			paymentEntity
					.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER);// 设置汇款状态为”未汇款“
		}
		paymentEntity
				.setAuditStatus(SettlementDictionaryConstants.BILL_PAYMENT__AUDIT_STATUS__NOT_AUDIT);// 设置审核状态为”未审核“
		paymentEntity.setSourceBillNo(SettlementConstants.DEFAULT_BILL_NO);// 来源单据编号 'N/A'
		paymentEntity.setActive(FossConstants.ACTIVE);// 是否有效
		paymentEntity.setIsRedBack(FossConstants.NO);// 是否红单
		paymentEntity.setBusinessDate(nowDate);// 设置业务日期
		paymentEntity.setAccountDate(nowDate);// 设置记账日期
		paymentEntity.setIsInit(FossConstants.NO);// 是否初始化
		paymentEntity
				.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);// 手动录入
																							  // 生成方式
		paymentEntity.setVersionNo(FossConstants.INIT_VERSION_NO);// 版本号
		return paymentEntity;
	}

	/**
	 * 根据单据子类型转化为对应费控明细类型  --仅限于应付单子类型
     *
     * @return
	 * @author 045738-foss-maojianqiang
	 * @date 2013-1-12 下午5:35:18
	 */
    public static String convertBillType(String number, String billType) {
        if (StringUtils.isBlank(billType)) {
            throw new SettlementException("应付单：" + number + "的单据子类型为空！");
		}
		//装卸费
        if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE.equals(billType)) {
			return SettlementESBDictionaryConstants.COST_CONTROL_PAY_DETAIL_SERVICE_FEE;
		//理赔应付
        } else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM.equals(billType)) {
			return SettlementESBDictionaryConstants.COST_CONTROL_PAY_DETAIL_CLAIM;
		//退运费应付
        } else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__REFUND.equals(billType)) {
			return SettlementESBDictionaryConstants.COST_CONTROL_PAY_DETAIL_REFUND;
		//减免运费
        } else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION.equals(billType)) {
			return SettlementESBDictionaryConstants.COST_CONTROL_PAY_DETAIL_COMPENSATION;
		//偏线成本
        } else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTIAL_LINE.equals(billType)
                || SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTIAL_LINE_OTHER.equals(billType)) {
			return SettlementESBDictionaryConstants.COST_CONTROL_PAY_DETAIL_PARTIAL_LINE;
		//空运代理成本
        } else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR.equals(billType)
                || SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_AGENCY_ORIGINAL.equals(billType)
                || SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_AGENCY_DELIVERY.equals(billType)
                || SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_OTHER.equals(billType)
                || SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTIAL_LINE.equals(billType)) {
			return SettlementESBDictionaryConstants.COST_CONTROL_PAY_DETAIL_AIR;
		//外请车整车首款
        } else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_FIRST.equals(billType)
                || SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST.equals(billType)) {
			return SettlementESBDictionaryConstants.COST_CONTROL_PAY_DETAIL_TRUCK_FIRST;
		//外请车整车尾款
        } else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_LAST.equals(billType)
                || SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST.equals(billType)) {
			return SettlementESBDictionaryConstants.COST_CONTROL_PAY_DETAIL_TRUCK_LAST;
		//快递代理外发成本
        } else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE.equals(billType)
                || SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE_OTHER.equals(billType)) {
			return SettlementESBDictionaryConstants.COST_CONTROL_PAY_DETAIL_LAND_STOWAGE;
		//代打木架
        } else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__WOODEN_OTEHR_PAYABLE.equals(billType)
                || SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__WOODEN_PAYABLE.equals(billType)) {
			return SettlementESBDictionaryConstants.COST_CONTROL_PAY_DETAIL_WOODEN;
		//合伙人到达提成应付
        } else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE.equals(billType)) {
			return SettlementESBDictionaryConstants.COST_CONTROL_PAY_DETAIL_DELIVERY;
		//合伙人委托派费应付
        } else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELEGATION_DELIVERY_FEE.equals(billType)) {
			return SettlementESBDictionaryConstants.COST_CONTROL_PAY_DETAIL_DELEGATION;
		//合伙人到付运费应付
        } else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_PAYABLE.equals(billType)) {
			return SettlementESBDictionaryConstants.COST_CONTROL_PAY_DETAIL_FREIGHT;
		} else {
			return null;
		}
	}

	/**
	 * 封装给财务共享的付款接口参数
     *
	 * @author 045738-foss-maojianqiang
	 * @date 2013-4-13 下午1:47:19
	 */
    private PayToCostcontrolDto buildPayToFSSCDto(BillPaymentEntity bill, List<BillPayableDto> payableList
            , List<BillDepositReceivedEntity> depositList, CurrentInfo currentInfo) {
		// 声明传递给费控的dto
		PayToCostcontrolDto dto = new PayToCostcontrolDto();
		//如果应付单和预收单集合都为空，则抛出异常
        if (CollectionUtils.isEmpty(payableList) && CollectionUtils.isEmpty(depositList)) {
			throw new SettlementException("费用明细不存在，不能进行付款！");
		}

		//声明付款明细
		List<com.deppon.fssc.inteface.domain.payment.ExpenseDetail> detail = new ArrayList<com.deppon.fssc.inteface.domain.payment.ExpenseDetail>();

		//因为业务规定，电汇只可能有一种工作流。故而如果存在预收单列表，肯定只能是FOSS日常工作流
        if (CollectionUtils.isNotEmpty(depositList)) {
			//循环预收单
            for (BillDepositReceivedEntity deposit : depositList) {
				com.deppon.fssc.inteface.domain.payment.ExpenseDetail d = new com.deppon.fssc.inteface.domain.payment.ExpenseDetail();
				d.setBillNumber(deposit.getDepositReceivedNo());//明细单号
				d.setExpenseExplain(deposit.getDepositReceivedNo());//费用说明
				d.setExpensesMoney(deposit.getPaymentAmount());//明细金额

				//如果是合伙人预收单提现
                if (SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__BILL_TYPE__DEPOSIT_PARTNER.equals(deposit.getBillType())) {
                    logger.info("\n\n合伙人预收单提现。应付费用类型是:" + SettlementESBDictionaryConstants.COST_CONTROL_PAY_DETAIL_WITHDRAW);
//					ddw
					BillDepositReceivedEntity depositEntity = billDepositReceivedService.queryByDepositReceivedNo(deposit.getDepositReceivedNo(), FossConstants.YES);
					OrgAdministrativeInfoEntity entity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(depositEntity.getGeneratingOrgCode());
					d.setExpensesType(SettlementESBDictionaryConstants.COST_CONTROL_PAY_DETAIL_WITHDRAW);
					d.setExpenseCostCenter(entity.getUnifiedCode());//部门标杆编码
                } else {
					d.setExpensesType(SettlementESBDictionaryConstants.COST_CONTROL_PAY_DETAIL_DEPOSIT_RECEIVED);//退预收
					d.setExpenseCostCenter(currentInfo.getDept().getUnifiedCode());//部门标杆编码
				}

				d.setPayBillExpDate(deposit.getBusinessDate());//业务发生日期
				d.setRemittanceNumber(deposit.getRemitNo());//预收单汇款编号
				detail.add(d);
			}
		}
		//非空判断
        if (CollectionUtils.isNotEmpty(payableList)) {
			//申请配载车次号集合
			List<String> trunkNos = new ArrayList<String>();
			/**
			 * @218392 张永雪 VTS整车 批量付款的时候，去校验 VTS
			 * 1.单号是否作废
			 * 2.单号+合同编码是否唯一
			 * 3.合同编码是否存在
			 */
			List<RequestPayableCheckEntity> requestPayableCheckEntity = new ArrayList<RequestPayableCheckEntity>();
			RequestPayableCheckEntity checkEntity = new RequestPayableCheckEntity();
			//是否外请车--外请车是单独付款故而只需要判断一条是即可
            List<String> driverList = Arrays.asList(SettlementESBDictionaryConstants.WORKFLOW_DETAIL_DRIVER);
			boolean isTrunckFlag = (driverList.contains(payableList.get(0).getBillType()));
			//循环应付单
            for (BillPayableDto payable : payableList) {
				//如果是理赔则需要去分摊部门做点事情
                if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM.equals(payable.getBillType())) {
					//声明接口传入参数
					BillPayableEntity payableEntity = new BillPayableEntity();
					payableEntity.setPayableNo(payable.getPayableNo());
					payableEntity.setBillType(payable.getBillType());
					payableEntity.setBusinessDate(payable.getBusinessDate());
                    payableEntity.setPayableOrgCode(currentInfo.getCurrentDeptCode());//直接传入当前部门编码
 					payableEntity.setPaymentAmount(payable.getPaymentAmount());
 					payableEntity.setWaybillNo(payable.getWaybillNo());//理赔增加运单号
 					payableEntity.setProductCode(payable.getProductCode());//设置产品类型
 					payableEntity.setAccountDate(payable.getAccountDate());//记账日期
					//进行理赔应付单费用分摊
					payToFSSCSendService.sharePayableForBad(detail, payableEntity, PAYDESC);
					continue;
				//如果为外请车，则需要获取配载单信息
                } else if (isTrunckFlag) {
					/**
					 * @author 218392 张永雪
					 * @date 2016-06-20 15:21:00
					 * 如果应付单来源是VTS系统，那么就跨过这个校验
					 */
					//定义是否 VTS的来源单号
					String vtsSourceBillNo = payable.getSourceBillNo();
					//判断是否ZC开头合同编码
					boolean isVtsPayable = SettlementUtil.isPayableZc(vtsSourceBillNo);
					//根据来源单号查询是否是ZC开头+当前时间+自增1，如果是的话VTS系统自己开单的校验，不是走原来配载单的校验
                    if (isVtsPayable) {
						checkEntity.setWaybillNo(payable.getWaybillNo());//set运单号
						checkEntity.setContractCode(vtsSourceBillNo);//set合同编号
						requestPayableCheckEntity.add(checkEntity);
                    } else {
						trunkNos.add(payable.getSourceBillNo());
					}

				}
				com.deppon.fssc.inteface.domain.payment.ExpenseDetail d = new com.deppon.fssc.inteface.domain.payment.ExpenseDetail();
				d.setBillNumber(payable.getPayableNo());//明细单号
				//ISSUE-3009 修改费用说明 对理赔、退运费、服务补救、装卸费传递运单号在费用说明中
                if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__REFUND.equals(payable.getBillType())
                        || SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION.equals(payable.getBillType())
                        || SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE.equals(payable.getBillType())) {
					d.setExpenseExplain(payable.getWaybillNo());//费用说明
                } else {
					d.setExpenseExplain(PAYDESC);//费用说明
				}
				d.setExpensesMoney(payable.getPaymentAmount());//明细金额
				//转化给对应费控那边费用明细  临时租车取付款单表头
                if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__RENT_CAR.equals(payable.getBillType())) {
					d.setExpensesType(bill.getCostType());
					d.setInvatNum(bill.getVatInvoice());//增值税发票号码
                    if (StringUtils.isBlank(bill.getVatInvoice())) {
						d.setIsvat(FossConstants.NO);//是否增值税专用发票
                    } else {
						d.setIsvat(FossConstants.YES);//是否增值税专用发票
					}
					d.setTaxfreePrice(payable.getTaxAmount());//不含税金额
					d.setTaxPrice(payable.getTax());//税额
					d.setCarPurpose(getRentCarUseType(payable.getRentCarUseType()));//租车用途
					d.setCarNumber(payable.getVehicleNo());//车牌号
					d.setDriverName(payable.getDriverName());//司机姓名
					//司机联系方式
                    final int numberOfEleven = 11;
                    if (StringUtils.isNotBlank(payable.getDriverPhone()) && payable.getDriverPhone().length() > numberOfEleven) {
                        d.setDriverTel(payable.getDriverPhone().substring(0, numberOfEleven));//司机联系方式
                    } else {
						d.setDriverTel(payable.getDriverPhone());//司机联系方式
					}
					//重复标记
                    if (SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__RENTCAR_REPET.equals(payable.getPayableType())) {
						d.setRepeatTag(FossConstants.YES);
                    } else {
						d.setRepeatTag(FossConstants.NO);
					}
					d.setWithHoldClaimNo(payable.getWorkFlowNo());//报销对应的预提工作流号
					d.setPayBillExpDate(payable.getBusinessOfDate());//重置业务发生日期为界面选取的日期
                } else {
                    String payType = convertBillType(payable.getPayableNo(), payable.getBillType());
					d.setExpensesType(payType);
					d.setPayBillExpDate(payable.getBusinessDate());//业务发生日期
				}

				//如果为包裹，且类型为服务补救和退运费，则去综合获取对应的快递代理点部
                if (SettlementUtil.isPackageProductCode(payable.getProductCode())
						&& (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__REFUND.equals(payable.getBillType())
                        || SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION.equals(payable.getBillType()))) {
					//判断如果当前登录部门快递代理点部为空，则去获取
					//调用综合接口去查询快递代理点部
                    ExpressPartSalesDeptResultDto shareExpressOrg = expressPartSalesDeptService.queryExpressPartSalesDeptBySalesCodeAndTime(currentInfo.getDept().getCode(), payable.getAccountDate());
					//判断是否为空
                    if (shareExpressOrg == null) {
                        throw new SettlementException("应付单：" + payable.getPayableNo() + "产品类型为快递代理包裹，其应付部门对应的快递代理点部为空，不能进行付款操作！");
                    } else if (StringUtils.isNotBlank(shareExpressOrg.getUnifiedCode())) {
						//设置快递代理点部
						d.setExpenseCostCenter(shareExpressOrg.getUnifiedCode());
                    } else {
						//设置快递代理点部
						d.setExpenseCostCenter(currentInfo.getDept().getUnifiedCode());
					}
                } else {
					//转化给对应费控那边费用明细  临时租车取付款单表头
                    if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__RENT_CAR.equals(payable.getBillType())) {
						d.setExpenseCostCenter(bill.getCostDeptCode());//部门标杆编码
					//判断合伙人应付单，如果是合伙人应付单费用承担部门为合伙人标杆编码，ddw
                    } else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_PAYABLE.equals(payable.getBillType())
                            || SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE.equals(payable.getBillType())
                            || SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELEGATION_DELIVERY_FEE.equals(payable.getBillType())) {
						d.setExpenseCostCenter(payable.getExpenseBearCode());//部门标杆编码
						d.setExpenseExplain(payable.getWaybillNo());
                    } else {
						d.setExpenseCostCenter(currentInfo.getDept().getUnifiedCode());//部门标杆编码
					}
				}
				detail.add(d);
			}

            //如果不为空，则封装配载单信息 --调用中转接口
            if (CollectionUtils.isNotEmpty(trunkNos)) {
				List<OutVehicleAssembleBillAndFeeVo> vehicleAssembleList = null;
				List<OutVehicleAssembleBillAndFeeVo> vehicleList = new ArrayList<OutVehicleAssembleBillAndFeeVo>();
				List<String> list = new ArrayList<String>();
				//调用中转接口获取配载单信息
                try {
					OutVehicleAssembleBillAndFeeVo vo  = null;
                    for (int i = 0; i < trunkNos.size(); i++) {
						list.add(trunkNos.get(i));
						List<OutVehicleAssembleBillAndFeeVo> outList = outsideVehicleChargeService.queryOutVehicleAssembleBillAndFeeVoList(list);
                        if (outList.size() > 0) {
							vo = outList.get(0);
                            OutVehicleAssembleBillAndFeeVo vo1 = new OutVehicleAssembleBillAndFeeVo();
							vo.setPaybillNo(detail.get(i).getBillNumber());
							BeanUtils.copyProperties(vo, vo1);
							vehicleList.add(vo1);
						}
						list.clear();
					}
					vehicleAssembleList = vehicleList;
                } catch (BusinessException e) {
                    throw new SettlementException("调用中转接口获取配载单信息异常：" + e.getMessage());
				}
				//声明配载单信息
				List<StowageDetail> stowageDetail = new ArrayList<StowageDetail>();

				//进行数据转化
                getStowageDetailForFSSC(stowageDetail, vehicleAssembleList, trunkNos);
				//设置配载单信息
				dto.setStowageDetail(stowageDetail);
			}

			/**
			 * @218392 zhangyongxue
			 * @date 2016-06-22 11:03:10
			 * 调用VTS接口校验
			 */
            if (CollectionUtils.isNotEmpty(requestPayableCheckEntity)) {
				//调用VTS服务端接口
				ResponsePayableCheckEntity responsePayableCheckEntity = clientSendVtsPayableCheck.sendVtsPayableCheck(requestPayableCheckEntity);
                if (responsePayableCheckEntity.isResultFlag()) {
					logger.info("校验通过没问题！");
                } else {
					throw new SettlementException("付款失败：" + responsePayableCheckEntity.getFailureReason());
				}

				//声明配载单信息：现在改成VTS整车项目的
				List<StowageDetail> stowageDetailList = new ArrayList<StowageDetail>();
                for (BillPayableDto dtoPay : payableList) {
					//定义是否 VTS的来源单号
					String vtsSourceBillNo = dtoPay.getSourceBillNo();
					//判断是否ZC开头合同编码
					boolean isVtsPayable = SettlementUtil.isPayableZc(vtsSourceBillNo);
					//根据来源单号查询是否是ZC开头+当前时间+自增1，如果是的话VTS系统自己开单的校验，不是走原来配载单
                    if (isVtsPayable) {
                        BillPayableEntity payableBillEntity =
								billPayableService.queryByPayableNO(dtoPay.getPayableNo(), FossConstants.YES);
						OrgAdministrativeInfoEntity destOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(payableBillEntity.getDestOrgCode());
                        OrgAdministrativeInfoEntity origOrg =
								orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(payableBillEntity.getOrigOrgCode());//始发部门=应付部门

						StowageDetail stowageDetail = new StowageDetail();
						stowageDetail.setStartDeptNumber(origOrg.getUnifiedCode());//设置起始部门编码
						stowageDetail.setArriveDeptNumber(destOrg.getUnifiedCode());//设置到达部门编码
						stowageDetail.setPlateNumber("VTS");//设置车牌号
						stowageDetail.setCarNumber(payableBillEntity.getSourceBillNo());//设置车次号
						stowageDetail.setDriverName(payableBillEntity.getCustomerName());//设置司机编号
						stowageDetail.setAmountMoney(payableBillEntity.getAmount());//总金额
						stowageDetail.setStartMoney(payableBillEntity.getAmount());//出发金额
						stowageDetail.setArriveMoney(new BigDecimal(0));//到达金额
						stowageDetail.setPayableNo(payableBillEntity.getPayableNo());//应付单号
						stowageDetail.setRegulationChange(new BigDecimal(0));//增减金额
						stowageDetail.setRewardWithhold(null);//奖惩类型：奖励、惩罚、null
						stowageDetail.setBackBillArrive("N");//是否押回单
						stowageDetail.setIsMonthly("N");//是否月结

						stowageDetailList.add(stowageDetail);
					}
				}
				//设置配载单信息
				dto.setStowageDetail(stowageDetailList);
			}
        }
        //保理
        if (null != bill.getFactoring() && StringUtils.isNotEmpty(bill.getFactoring())) {
            dto.setFactoring(bill.getFactoring());//获取是否保理
            if (bill.getFactoring().equals(FossConstants.YES)) {
                dto.setCusCode(bill.getCusCode());//贷款客户编码
                dto.setCustomerCode(bill.getCustomerCode());//获取付款单中的客户编码
                dto.setCustomerName(bill.getCustomerName());//获取付款单中的客户名称
            }
        } else {
            dto.setFactoring(FossConstants.NO);
        }
		// 声明付款集合
		dto.setPaymentBillNo(bill.getBatchNo());//获取付款编号
		dto.setPayBillDeptNo(bill.getPaymentOrgCode());// 获取部门标杆编码
		dto.setPayBillDeptName(bill.getPaymentOrgName());// 获取部门名称
		dto.setPayBillAmount(bill.getAmount());// 获取总金额
		dto.setPayBillComNo(bill.getInvoiceHeadCode());// 获取公司编码
		dto.setPayBillBankNo(bill.getAccountNo());// 获取银行账号
		dto.setAccountType(bill.getAccountType());//账户类型
		dto.setBankHqCode(bill.getBankHqCode());//开户行编码
		dto.setBankBranchCode(bill.getBankBranchCode());//开户行支行编码
		dto.setProvinceCode(bill.getProvinceCode());//省份编码
		dto.setCityCode(bill.getCityCode());//城市编码
		dto.setPayBillPayeeName(bill.getPayeeName());// 获取收款人名称
		dto.setPayBillPayeeCode(bill.getPayeeCode());//收款人编码
		dto.setPayBillCelephone(bill.getMobilePhone());// 获取联系方式
		dto.setPayBillAppNo(currentInfo.getEmpCode());// 获取员工编号
        //最迟汇款日期 ---前台未获取到先写死  add by liqin 2013-5-3    nowDate（）
		dto.setPayBillLastTime(new Date());//获取最迟汇款日期 --now time

		dto.setPayBillAppType(bill.getWorkFlowType());//工作流类型
		dto.setPayType(SettlementESBDictionaryConstants.COST_CONTROL_PAYMENT_PAY);//支付类型  --付款
		dto.setCurrency(SettlementESBDictionaryConstants.CURRENCY_CODE_RMB);//币种--人民币
		dto.setIsAutoAbatementLoan(SettlementESBDictionaryConstants.AUTOABATEMENTLOAN_N);//电汇默认为N,只有申请备用金才可能自动冲借支
		dto.setExchangeRate(null);//汇率默认为空， foss暂时没有汇率
		dto.setExpenseDetailToFSSC(detail);
		dto.setBatchNo(bill.getBatchNo());//批次号
		return dto;
	}

	/**
	 * 新增校验,并将更新应付单需要参数进行封装到dto中
     *
	 * @author 045738-foss-maojianqiang
	 * @date 2014-4-17 下午6:49:33
	 */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private Map validateAddPaymentForFSSC(BillPaymentEntity paymentEntity, List<BillPaymentAddDto> addDtoList, String paymentNum, CurrentInfo currentInfo) {
		logger.info("付款单新增时校验开始");
		//声明工作流类型    --每次付款只能付同一种工作流类型的明细数据
		String workFlowType = null;
		//声明付款类型 报销还是付款
		String payType = null;
		//是否临时租车
		boolean isRentCarFlag = false;
		//如果付款方式为电汇，表示为付款，反之为报销
        if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
                .equals(paymentEntity.getPaymentType())) {
			payType = SettlementESBDictionaryConstants.COST_CONTROL_PAYMENT_PAY;//付款
        } else {
			payType = SettlementESBDictionaryConstants.COST_CONTROL_PAYMENT_APPLY;//报销
		}

		//校验界面传入参数
        validateParams(paymentEntity, addDtoList);
		//如果为临时租车
        if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__RENT_CAR.equals(addDtoList.get(0).getBillType())) {
			isRentCarFlag = true;
		}

		// 声明本次付款金额总和
		BigDecimal payableUnVerifyAmount = BigDecimal.ZERO;
		BigDecimal depositUnVerifyAmount = BigDecimal.ZERO;

		// 声明要更新的应付单集合
		List<BillPayableDto> billPayables = new ArrayList<BillPayableDto>();
		//声明要更新的预收单集合
		List<BillDepositReceivedEntity> depositList = new ArrayList<BillDepositReceivedEntity>();
		//声明锁对象集合
		List<MutexElement> mutexesList = new ArrayList<MutexElement>();
		//声明付款单明细
		List<BillPaymentDEntity> payDetailList = new ArrayList<BillPaymentDEntity>();

		//声明应付单单号列表
		List<String> payableNos = new ArrayList<String>();
		//声明预收单单号列表
		List<String> depositReceivedNos = new ArrayList<String>();
		//声明付款单明细单号对应的支付金额map
        Map<String, BigDecimal> amountMapByNo = new HashMap<String, BigDecimal>();

		//ddw
        if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_PAYABLE.equals(addDtoList.get(0).getBillType())) {
			BigDecimal num = new BigDecimal("0");
//					BigDecimal.valueOf(0);
			String[] codes = new String[1];
            codes[0] = ConfigurationParamsConstants.STL_PAYABLE_PAY_AMOUNT;
			List<ConfigurationParamsEntity> configurationParamsEntitys = this.configurationParamsService.queryConfigurationParamsBatchByCode(codes);
            if (null != configurationParamsEntitys && configurationParamsEntitys.size() > 0) {
				String value = configurationParamsEntitys.get(0).getConfValue();
                if (value != null) {
					num = new BigDecimal(value);
				}
			}
            if (num.compareTo(paymentEntity.getAmount()) > 0) {
                throw new SettlementException("应付单：" + addDtoList.get(0).getPayableNo() + "为到付运费应付单，付款金额必须大于" + num);
			}
		}

		// 循环获取dto中本次付款金额
		for (BillPaymentAddDto dto : addDtoList) {
			//校验界面传入id
            if (StringUtils.isBlank(dto.getId())) {
				throw new SettlementException("付款单明细中应付单号："
						+ dto.getPayableNo() + "的明细id为空！");
			}

			//如果为应付单
            if (SettlementUtil.isPayable(dto.getPayableNo())) {
				payableNos.add(dto.getPayableNo());
				amountMapByNo.put(dto.getPayableNo(), dto.getCurrentPaymentAmount());
				//封装应付单要用的dtolist
				BillPayableDto entity = new BillPayableDto();
				entity.setId(dto.getId());// 封装应付单id
				entity.setBillType(dto.getBillType());//获取单据子类型
				entity.setPayableNo(dto.getPayableNo());//应付单号
				entity.setAccountDate(dto.getAccountDate());// 封装记账日期
				entity.setBusinessDate(dto.getBusinessDate());//封装业务日期
				entity.setVersionNo(dto.getVersionNo());// 封装版本号
				entity.setPaymentAmount(dto.getCurrentPaymentAmount());// 封装本次付款金额
				entity.setPaymentNo(paymentNum);// 封装付款单号
				entity.setPaymentNotes(dto.getNotes());// 封装本次付款明细备注
				entity.setTax(dto.getTax());
				entity.setTaxAmount(dto.getTaxAmount());
				entity.setBusinessOfDate(dto.getBusinessOfDate());
				//如果为装卸费、服务补救或理赔应付单，必须全额支付
                if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE.equals(dto.getBillType())
                        || SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION.equals(dto.getBillType())
                        || SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM.equals(dto.getBillType())) {
					//如果付款金额与未核销金额不相等，则抛出异常
                    if (dto.getCurrentPaymentAmount().compareTo(dto.getUnverifyAmount()) != 0) {
                        throw new SettlementException("应付单：" + dto.getPayableNo() + "为装卸费或服务补救或理赔应付单，必须全额付款！");
					}
				}
				//将数据封装到应付单dto的集合中
				billPayables.add(entity);
				//累加应付单金额
				payableUnVerifyAmount = payableUnVerifyAmount.add(dto.getCurrentPaymentAmount());

			//声明预收单
            } else if (SettlementUtil.isDepositReceived(dto.getPayableNo())) {
				depositReceivedNos.add(dto.getPayableNo());
				amountMapByNo.put(dto.getPayableNo(), dto.getCurrentPaymentAmount());
				//声明与预付单集合
				BillDepositReceivedEntity entity = new BillDepositReceivedEntity();
				entity.setId(dto.getId());// 封装预收单id
				entity.setDepositReceivedNo(dto.getPayableNo());// 封装预收单号
				entity.setVersionNo(dto.getVersionNo());// 封装版本号
				entity.setPaymentAmount(dto.getCurrentPaymentAmount());// 封装本次付款金额
				entity.setPaymentNo(paymentNum);// 封装付款单号
				entity.setPaymentNotes(dto.getNotes());// 封装本次付款明细备注
				entity.setBusinessDate(dto.getBusinessDate());
				entity.setAccountDate(dto.getAccountDate());
				//将前台数据封装到预收单dto集合中
				depositList.add(entity);
				//累加应付单金额
				depositUnVerifyAmount = depositUnVerifyAmount.add(dto.getCurrentPaymentAmount());
            } else {
				throw new SettlementException("付款单明细中应付单号："
						+ dto.getPayableNo() + "的单据类型不是应付或预收单！");
			}
        }
		//查询预收单
        if (depositReceivedNos.size() > 0) {
			//调用预收单接口查询应付单信息
			List<BillDepositReceivedEntity> entityList = billDepositReceivedService.queryByDepositReceivedNOs(depositReceivedNos, FossConstants.ACTIVE);
			//如果查询预收单条数与传入条数不等，则弹出提示
            if (entityList.size() != depositReceivedNos.size()) {
				throw new SettlementException("查询预收单明细与传入明细不等，请关闭界面，重新制作！");
			}
			//校验新查询数据与界面传入的dto是否一致
            for (int i = 0; i < entityList.size(); i++) {
				//获取预收单entity
				BillDepositReceivedEntity entity = entityList.get(i);
				//循环界面列表集合，与查询出来数据进行比较
                for (int j = 0; j < depositList.size(); j++) {
					//获取dto
                    BillDepositReceivedEntity viewEntity = depositList.get(j);
					//比较新查询出数据与界面数据是否一致，根据同一单号
                    if (entity.getDepositReceivedNo().equals(viewEntity.getDepositReceivedNo())) {
						//判断版本号是否一致
                        if (!entity.getVersionNo().equals(viewEntity.getVersionNo())) {
                            throw new SettlementException("付款单明细中预收单号：" + entity.getDepositReceivedNo() + "的数据已经发生变化，请刷新预收单或对账单页面，重新进行付款");
						}
						//给depositList中明细封装businessDate和acctdate,单据类型、给后面费控明细要用到
						viewEntity.setBusinessDate(entity.getBusinessDate());
						viewEntity.setAccountDate(entity.getAccountDate());
						viewEntity.setBillType(entity.getBillType());
						viewEntity.setRemitNo(entity.getRemitNo());
						break;
					}
				}
				//声明付款单明细
				BillPaymentDEntity dEntity = new BillPaymentDEntity();
				//设置值
				dEntity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYMENT_D_SOURCE_BILL_TYPE_DEPOSIT_RECEIVED);
				//设置值
				dEntity.setSourceBillNo(entity.getDepositReceivedNo());
				//设置值
				dEntity.setSourceAccountDate(entity.getAccountDate());
				//设置支付金额
				dEntity.setPayAmount(amountMapByNo.get(entity.getDepositReceivedNo()));
				//添加到明细列表中
				payDetailList.add(dEntity);
			}
			//声明校验dto
			BillDepositReceivedPayDto billDepositReceivedPayDto = new BillDepositReceivedPayDto();
			//包装要校验的预收单集合
			billDepositReceivedPayDto.setBillDepositReceivedEntityList(entityList);
            billDepositReceivedPayService.backDepositReceived(billDepositReceivedPayDto, currentInfo, SettlementConstants.OPERATETYPE_PAYMENT);
			//声明工作流类型为foss日常
            if (SettlementESBDictionaryConstants.COST_CONTROL_PAYMENT_PAY.equals(payType)) {
				//FOSS日常--付款
				workFlowType = SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_DAY_PAY;
				//如果是合伙人预收单提现
                if (SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__BILL_TYPE__DEPOSIT_PARTNER.equals(entityList.get(0).getBillType())) {
					workFlowType = SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PARTNER_WITHDRAW_PAY;
                    logger.info("\n\n结算请求fssc，生成合伙人提现工作流。工作流编码:" + workFlowType + "\n\n");
				}
            } else {
				//FOSS日常--报销
				workFlowType = SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_DAY_APPLY;
			}
		}

		//查询应付单
        if (payableNos.size() > 0) {
			//调用应付单接口查询应付单信息
			List<BillPayableEntity> payableList = null;
            if (isRentCarFlag) {
				payableList = billPayableService.queryByPayableNOsForRentCar(payableNos);
            } else {
				//调用应付单接口查询应付单信息
				payableList = billPayableService.queryByPayableNOs(payableNos, FossConstants.ACTIVE);
			}

			//如果查询应付单条数与传入条数不等，则弹出提示
            if (payableList.size() != payableNos.size()) {
				throw new SettlementException("查询付款单明细与传入明细不等，请关闭界面，重新制作！");
			}
			//校验新查询数据与界面传入的dto是否一致
            for (int i = 0; i < payableList.size(); i++) {
				//获取应付单entity
				BillPayableEntity entity = payableList.get(i);
				//获取预提状态
				String withholdingStatus = entity.getWithholdingStatus();
				//如果为临时租车预提中，且付款为电汇，则需要抛出提示
                if (StringUtils.isNotBlank(withholdingStatus) && SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
                        .equals(paymentEntity.getPaymentType())) {
                    if (SettlementDictionaryConstants.WITHHOLDING_STATUS_TRANSFERRING.equals(withholdingStatus)) {
                        throw new SettlementException("应付单：" + entity.getPayableNo() + "预提工作流还在审批中，为“预提中”状态，不能进行电汇付款！");
					}
				}


				//如果是第一次循环，则要获取
                if (i == 0 && StringUtils.isBlank(workFlowType)) {
                    workFlowType = getWorkFlowTypeByBillType(entity.getPayableNo(), entity.getBillType(), payType, paymentEntity.getCostType());
                } else {
					//获取下一次的类型与第一次工作流类型进行比较
                    String workFlowTypeNew = getWorkFlowTypeByBillType(entity.getPayableNo(), entity.getBillType(), payType, paymentEntity.getCostType());
					//如果两次工作流类型不等，则抛出异常
                    if (!StringUtils.equals(workFlowType, workFlowTypeNew)) {
						throw new SettlementException("该付款单中存在不同付款工作流类型的单据，不能进行付款！");
					}
				}
				//理赔工作流类型       268217
                if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM.equals(entity.getBillType()) &&
						SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
                                .equals(paymentEntity.getPaymentType())) {
                    workFlowType = SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_PAY_APPLY_CLAIM;
				}
				BillPayableDto dto = null;
				//循环界面列表集合，与查询出来数据进行比较
                for (int j = 0; j < billPayables.size(); j++) {
					//获取dto
					dto = billPayables.get(j);
					//比较新查询出数据与界面数据是否一致，根据同一单号
                    if (entity.getPayableNo().equals(dto.getPayableNo())) {
						validaExtracted(paymentEntity, entity, dto);
						//给billPayables中明细封装businessDate和acctdate,单据类型、给后面费控明细要用到
						dto.setBusinessDate(entity.getBusinessDate());
						dto.setAccountDate(entity.getAccountDate());
						dto.setBillType(entity.getBillType());
						dto.setSourceBillNo(entity.getSourceBillNo());//封装来源单据编号 --给外请车付款用来查询配载单数据
						dto.setWaybillNo(entity.getWaybillNo());//封装运单号
						dto.setProductCode(entity.getProductCode());//获取产品类型
						dto.setPayableType(entity.getPayableType());//封装支付类型
						dto.setWorkFlowNo(entity.getWorkflowNo());
						dto.setExpenseBearCode(entity.getExpenseBearCode());
						//临时租车设置司机、车牌、类型等
                        if (isRentCarFlag) {
							dto.setDriverName(entity.getDriverName());
							dto.setDriverPhone(entity.getDriverPhone());
							dto.setVehicleNo(entity.getVehicleNo());
							dto.setRentCarUseType(entity.getRentCarUseType());
						}
						//跳出循环
						break;
					}
				}
				//声明锁定运单对象
                if (StringUtils.isNotBlank(entity.getWaybillNo())) {
					MutexElement mtx = new MutexElement(entity.getWaybillNo(), "录入付款单锁定运单", MutexElementType.WAYBILL_NO);
					mutexesList.add(mtx);
				}
				//声明付款单明细
				BillPaymentDEntity dEntity = new BillPaymentDEntity();
				//设置值
				dEntity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYMENT_D_SOURCE_BILL_TYPE_PAYABLE);
				//设置值
				dEntity.setSourceBillNo(entity.getPayableNo());
				//设置值
				dEntity.setWaybillNo(entity.getWaybillNo());
				//设置值
				dEntity.setSrcSourceBillNo(entity.getSourceBillNo());
				//设置值
				dEntity.setSourceAccountDate(entity.getAccountDate());
				//设置支付金额
				dEntity.setPayAmount(amountMapByNo.get(entity.getPayableNo()));
				//设置税金
				dEntity.setTaxAmount(dto.getTaxAmount());
				//设置税后金额
				dEntity.setTax(dto.getTax());
				//设置业务发生日期
				dEntity.setBusinessOfDate(dto.getBusinessOfDate());
				//添加到明细列表中
				payDetailList.add(dEntity);

				/*
                 * modify by 269044-zhurongrong 2016-11-24
				 * DEFECT-16990:DN201608120070-FOSS系统自动带出理赔方式需求
				 */
                //如果是在线理赔
                if (SettlementDictionaryConstants.CLAIMSWAY_ONLINE.equals(entity.getClaimWay())) {
					//付款单的申请事由加上 在线理赔 这四个字
					paymentEntity.setNotes("【在线理赔】" + paymentEntity.getNotes());
				}
            }
			//校验应付单数据是否可以支付
            billPayableQueryManageservice.validateCanPay(payableList, paymentEntity.getPaymentType());
		}

		// 校验明细本次付款金额总和和付款单头的金额是否相等
		if (paymentEntity.getAmount() != null
				&& paymentEntity.getAmount().compareTo(payableUnVerifyAmount.add(depositUnVerifyAmount)) != 0) {
			throw new SettlementException("本次付款金额明细之和与付款单金额不等！");
		}

		//在此处来确认付款单的工作流类型
        if (StringUtils.isNotBlank(workFlowType)) {
			paymentEntity.setWorkFlowType(workFlowType);
        } else {
			throw new SettlementException("付款工作流类型为不能为空！");
		}
		//校验付款单明细
        if (CollectionUtils.isEmpty(payDetailList)) {
			throw new SettlementException("付款单明细不能为空！");
		}
        //声明传出map
        Map map = new HashMap();
		//封装付款单列表
		map.put("payableList", billPayables);
		//封装付款单列表
		map.put("depositList", depositList);
		//封装所列表
		map.put("mutexesList", mutexesList);
		//封装付款单明细列表
		map.put("payDetailList", payDetailList);
		logger.info("付款单新增时校验结束");
		return map;
	}

	/**
	 * 获取付款对接系统
     * @return
	 * @author 045738-foss-maojianqiang
	 * @date 2013-4-16 上午10:09:49
	 */
    public static String getPayToSystem() {
		//获取对接系统参数
		DataDictionaryEntity dictEntity = DictUtil.getDataByTermsCode("SETTLEMENT__PAYTOSYSTEM_TYPE");
		List<DataDictionaryValueEntity> dataList = dictEntity.getDataDictionaryValueEntityList();
		//如果对接系统数据字典没配置，则抛出异常
        if (CollectionUtils.isEmpty(dataList)) {
			throw new SettlementException("FOSS付款的付款工作流对接系统类型数据字典没配置，请去数据字典进行配置！");
		}
		//对接系统必须配置，且必须是1条 Y--财务共享，N--代表费控
        if (dataList.size() != 1) {
			throw new SettlementException("付款工作流对接系统类型数据字典配置有误，必须只有1条。其中值为Y代表财务共享，N代表费控！");
		}

        //付款对接系统  Y--财务共享，N--代表费控
		String payToSystem = dataList.get(0).getValueCode();
		//判空
        if (StringUtils.isEmpty(payToSystem)) {
			throw new SettlementException("付款工作流对接系统类型数据字典配置有误，不能为空！值必须为Y或N。其中值为Y代表财务共享，N代表费控！");
		}
		return payToSystem;
	}

	/**
	 * 封装财务共享的外请车付款配载单信息
     * @return
	 * @author 045738-foss-maojianqiang
	 * @date 2013-4-20 下午3:04:25
	 */
    public static void getStowageDetailForFSSC(List<StowageDetail> stowageDetail, List<OutVehicleAssembleBillAndFeeVo> vehicleAssembleList, List<String> trunkNos) {
		//判空
        if (CollectionUtils.isEmpty(vehicleAssembleList)) {
			throw new SettlementException("外请车付款，查询相关配载单信息为空，不能进行付款！");
        } else if (vehicleAssembleList.size() != trunkNos.size()) {
			throw new SettlementException("查询配载单信息条数与传入查询配载单个数不相等，不能进行付款！");
		}
		//循环封装配载单信息
        for (OutVehicleAssembleBillAndFeeVo vo : vehicleAssembleList) {
			//校验配载单信息
			validateStowageDetail(vo);
			//声明配载单信息 接口类
			StowageDetail detail = new StowageDetail();
			detail.setStartDeptNumber(vo.getOrigOrgCode());//设置起始部门编码
			detail.setArriveDeptNumber(vo.getDestOrgCode());//设置到达部门编码
			detail.setPlateNumber(vo.getVehicleNo());//设置车牌号
			detail.setCarNumber(vo.getVehicleAssembleNo());//设置车次号
			detail.setDriverName(vo.getDriverName());//设置司机编号
			detail.setAmountMoney(vo.getFeeTotal());//总金额
			detail.setStartMoney(vo.getPrepaidFeeTotal());//出发金额
			detail.setArriveMoney(vo.getArriveFeeTotal());//到达金额
			detail.setPayableNo(vo.getPaybillNo());
            //调整金额
            if (vo.getAdjustFee() != null) {
				detail.setRegulationChange(vo.getAdjustFee());
            } else {
				detail.setRegulationChange(null);
			}

			//奖励/扣款 类型转化
            if (StringUtils.isNotBlank(vo.getAwardType())) {
                if (OutsideVehicleChargeConstants.REWARD.equals(vo.getAwardType())) {
					detail.setRewardWithhold(SettlementESBDictionaryConstants.AWARDTYPE_REWARD);//奖励
                } else if (OutsideVehicleChargeConstants.FINE.equals(vo.getAwardType())) {
					detail.setRewardWithhold(SettlementESBDictionaryConstants.AWARDTYPE__FINE);//惩罚
                } else {
					detail.setRewardWithhold(null);//为空
				}
			}
			//是否押回单
            if (FossConstants.ACTIVE.equals(vo.getBeMidwayLoad())) {
				detail.setBackBillArrive(SettlementESBDictionaryConstants.BEMIDWAYLOAD_Y);//是否押回单
            } else {
				detail.setBackBillArrive(SettlementESBDictionaryConstants.BEMIDWAYLOAD_N);//是否押回单
			}
			//是否月结
            if (ISMONTHFLAG.equals(vo.getIsMonthPay())) {
				detail.setIsMonthly(SettlementESBDictionaryConstants.ISMONTH_Y);
            } else {
				detail.setIsMonthly(SettlementESBDictionaryConstants.ISMONTH_N);
			}
			stowageDetail.add(detail);
		}
	}


	/**
	 * 封装财务共享的外请车付款配载单信息
     * @return
	 * @author 045738-foss-maojianqiang
	 * @date 2013-4-20 下午3:04:25
	 */
    public void getStowageEntityList(List<StowageEntity> stowageDetail, List<OutVehicleAssembleBillAndFeeVo> vehicleAssembleList, List<String> trunkNos, boolean isReturnBackBalance, CurrentInfo currentInfo) {
		//判空
        if (CollectionUtils.isEmpty(vehicleAssembleList)) {
			throw new SettlementException("外请车付款，查询相关配载单信息为空，不能进行付款！");
        } else if (vehicleAssembleList.size() != trunkNos.size()) {
			throw new SettlementException("查询配载单信息条数与传入查询配载单个数不相等，不能进行付款！");
		}
		//获取部门对象
        Map<String, String> tranceCetnerMap = new HashMap<String, String>();

		//循环封装配载单信息
        for (OutVehicleAssembleBillAndFeeVo vo : vehicleAssembleList) {
			//校验配载单信息
			validateStowageDetail(vo);
			//声明配载单信息 接口类
			StowageEntity detail = new StowageEntity();
			detail.setStartDeptNumber(vo.getOrigOrgCode());//设置起始部门编码
			detail.setArriveDeptNumber(vo.getDestOrgCode());//设置到达部门编码
			detail.setPlateNumber(vo.getVehicleNo());//设置车牌号
			detail.setCarNumber(vo.getVehicleAssembleNo());//设置车次号
			detail.setDriverName(vo.getDriverName());//设置司机编号
			detail.setAmountMoney(vo.getFeeTotal());//总金额
			detail.setStartMoney(vo.getPrepaidFeeTotal());//出发金额
			detail.setArriveMoney(vo.getArriveFeeTotal());//到达金额
            //调整金额
            if (vo.getAdjustFee() != null) {
				detail.setRegulationChange(vo.getAdjustFee());
            } else {
				detail.setRegulationChange(null);
			}
			//奖励/扣款 类型转化
            if (StringUtils.isNotBlank(vo.getAwardType())) {
                if (OutsideVehicleChargeConstants.REWARD.equals(vo.getAwardType())) {
					detail.setRewardWithhold(SettlementESBDictionaryConstants.AWARDTYPE_REWARD);//奖励
                } else if (OutsideVehicleChargeConstants.FINE.equals(vo.getAwardType())) {
					detail.setRewardWithhold(SettlementESBDictionaryConstants.AWARDTYPE__FINE);//惩罚
                } else {
					detail.setRewardWithhold(null);//为空
				}
			}
            //是否押回单
            if (!ISMONTHFLAG.equals(vo.getIsMonthPay()) && isReturnBackBalance) {
				detail.setBackBillArrive(SettlementESBDictionaryConstants.BEMIDWAYLOAD_Y);//是否押回单
            } else {
				detail.setBackBillArrive(SettlementESBDictionaryConstants.BEMIDWAYLOAD_N);//是否押回单
			}
			//是否月结
            if (ISMONTHFLAG.equals(vo.getIsMonthPay())) {
				detail.setIsMonthly(SettlementESBDictionaryConstants.ISMONTH_Y);
            } else {
				detail.setIsMonthly(SettlementESBDictionaryConstants.ISMONTH_N);
			}
			//获取付款部门编码
			String deptCode = currentInfo.getDept().getUnifiedCode();//标杆
			String code = currentInfo.getDept().getCode();//普通编码
			//判断付款方式 --如果当前申请人所在部门等于配载单出发部门，则为出发，反之如果等于配载单到达部门，则为到达，反之抛异常
            if (deptCode.equals(vo.getOrigOrgCode())) {
				//付款方式
				detail.setPaymentType(SettlementESBDictionaryConstants.PAY_TYPE_START);
            } else if (deptCode.equals(vo.getDestOrgCode())) {
				//付款方式
				detail.setPaymentType(SettlementESBDictionaryConstants.PAY_TYPE_ARRIVE);
            } else {
				//声明外场编码
				String tranceCenterCode = null;
				//先去map中获取
                if (tranceCetnerMap.get(deptCode) != null) {
					tranceCenterCode = tranceCetnerMap.get(deptCode);
                } else {
					List<String> bizTypes = new ArrayList<String>();
					//声明 --查询外场
					bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
					//获取外场
					OrgAdministrativeInfoEntity centerOrg = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(code, bizTypes);
					//出发部门
                    if (centerOrg != null) {
						//设置标杆编码
						tranceCenterCode = centerOrg.getUnifiedCode();
						//将其放到map中
						tranceCetnerMap.put(deptCode, centerOrg.getUnifiedCode());
					}
				}
				//出发部门
                if (tranceCenterCode != null && tranceCenterCode.equals(vo.getOrigOrgCode())) {
					//付款方式
					detail.setPaymentType(SettlementESBDictionaryConstants.PAY_TYPE_START);
				//到达部门
                } else if (tranceCenterCode != null && tranceCenterCode.equals(vo.getDestOrgCode())) {
					//付款方式
					detail.setPaymentType(SettlementESBDictionaryConstants.PAY_TYPE_ARRIVE);
                } else {
					throw new SettlementException("申请人部门（或所属外场）与配载单出发和到达部门编码都不相等！");
				}
			}


			//电汇/月结--则全部尾款paymentType
            if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER.equals(vo.getPaymentType())
                    || SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(vo.getPaymentType())) {
                //支付类型
				detail.setPaymentproperty(SettlementESBDictionaryConstants.PAYMENT_TYPE_ARRIV_EALL);
            } else {
				//出发金额大于0
                if (BigDecimal.ZERO.compareTo(vo.getPrepaidFeeTotal()) == -1) {
					//小于总金额 则必然是出发部门付款
                    if (vo.getFeeTotal().compareTo(vo.getPrepaidFeeTotal()) == 1) {
						detail.setPaymentproperty(SettlementESBDictionaryConstants.PAYMENT_TYPE_START_PART);
					//如果出发金额等于总金额，则全部为出发全额付
                    } else if (vo.getFeeTotal().compareTo(vo.getPrepaidFeeTotal()) == 0) {
						detail.setPaymentproperty(SettlementESBDictionaryConstants.PAYMENT_TYPE_START_ALL);
					}
                    //到达金额大于0
                } else if (BigDecimal.ZERO.compareTo(vo.getArriveFeeTotal()) == -1) {
					//如果到达金额小于总金额，则为出发部门付款
                    if (vo.getFeeTotal().compareTo(vo.getArriveFeeTotal()) == 1) {
						detail.setPaymentproperty(SettlementESBDictionaryConstants.PAYMENT_TYPE_START_PART);
					//到达金额等于总金额，则为到达全部付款
                    } else if (vo.getFeeTotal().compareTo(vo.getArriveFeeTotal()) == 0) {
						detail.setPaymentproperty(SettlementESBDictionaryConstants.PAY_TYPE_ARRIVE);
					}
				}
			}
			stowageDetail.add(detail);
		}
	}

	/**
     * 校验配载单信息
     * @param vo
	 * @author 045738-foss-maojianqiang
	 * @date 2013-4-20 下午3:16:33
	 */
    public static void validateStowageDetail(OutVehicleAssembleBillAndFeeVo vo) {
		//校验出发部门编码
        if (StringUtils.isBlank(vo.getOrigOrgCode())) {
            throw new SettlementException("配载单：" + vo.getVehicleNo() + "出发部门编码为空！");
		}

		//校验到达部门编码
        if (StringUtils.isBlank(vo.getDestOrgCode())) {
            throw new SettlementException("配载单：" + vo.getVehicleNo() + "到达部门编码为空！");
		}

		//校验车牌号
        if (StringUtils.isBlank(vo.getVehicleNo())) {
            throw new SettlementException("配载单：" + vo.getVehicleNo() + "车牌号为空！");
		}

		//校验配载车次号
        if (StringUtils.isBlank(vo.getVehicleAssembleNo())) {
            throw new SettlementException("配载单：" + vo.getVehicleNo() + "配载车次号为空！");
		}

		//校验司机姓名
        if (StringUtils.isBlank(vo.getDriverName())) {
            throw new SettlementException("配载单：" + vo.getVehicleNo() + "司机姓名为空！");
		}
		//校验总金额
        if (vo.getFeeTotal() == null) {
            throw new SettlementException("配载单：" + vo.getVehicleNo() + "金额为空");
		}
//
//		//校验预付金额
//		if(vo.getPrepaidFeeTotal()==null){
//			throw new SettlementException("配载单："+vo.getVehicleNo()+"预付金额为空");
//		}
//
//		//校验到付金额
//		if(vo.getArriveFeeTotal()==null){
//			throw new SettlementException("配载单："+vo.getVehicleNo()+"到付金额为空！");
//		}

		//押回单到达
        if (StringUtils.isBlank(vo.getBeMidwayLoad())) {
            throw new SettlementException("配载单：" + vo.getVehicleNo() + "是否押回单标志为空！");
		}

		//是否月结
        if (StringUtils.isBlank(vo.getIsMonthPay())) {
            throw new SettlementException("配载单：" + vo.getVehicleNo() + "是否月结标志为空！");
		}

		//支付方式
        if (StringUtils.isBlank(vo.getPaymentType())) {
            throw new SettlementException("配载单：" + vo.getVehicleNo() + "支付方式为空！");
		}
	}

	/**
	 * 功能：租车用途转话
     * @return
	 * @author 045738-foss-maojianqiang
	 * @date 2014-8-9
	 */
    public static String getRentCarUseType(String rentCarUseType) {
        if (StringUtils.isBlank(rentCarUseType)) {
			return null;
		}
        if (SettlementDictionaryConstants.RENTCAR_USE_TYPE_RECEIVE.equals(rentCarUseType)) {
			return "接货";
        } else if (SettlementDictionaryConstants.RENTCAR_USE_TYPE_DELIVER.equals(rentCarUseType)) {
			return "送货";
        } else if (SettlementDictionaryConstants.RENTCAR_USE_TYPE_TRANSLATE.equals(rentCarUseType)) {
			return "转货";
        } else if (SettlementDictionaryConstants.RENTCAR_USE_TYPE_DELIVER_RECEIVE.equals(rentCarUseType)) {
			return "接送货";
		}
		return null;
	}

	public void setBillPaymentService(IBillPaymentService billPaymentService) {
		this.billPaymentService = billPaymentService;
	}

	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	public void setPayToCostcontrolSendService(
			IPayToCostcontrolSendService payToCostcontrolSendService) {
		this.payToCostcontrolSendService = payToCostcontrolSendService;
	}

	public void setBillDepositReceivedService(
			IBillDepositReceivedService billDepositReceivedService) {
		this.billDepositReceivedService = billDepositReceivedService;
	}

	public void setBillPayableQueryManageservice(
			IBillPayableQueryManageservice billPayableQueryManageservice) {
		this.billPayableQueryManageservice = billPayableQueryManageservice;
	}


	public void setBillDepositReceivedPayService(
			IBillDepositReceivedPayService billDepositReceivedPayService) {
		this.billDepositReceivedPayService = billDepositReceivedPayService;
	}

	public IBusinessLockService getBusinessLockService() {
		return businessLockService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	/**
     * @param payToFSSCSendService
	 * @SET
	 */
	public void setPayToFSSCSendService(IPayToFSSCSendService payToFSSCSendService) {
		/*
		 *@set
		 *@this.payToFSSCSendService = payToFSSCSendService
		 */
		this.payToFSSCSendService = payToFSSCSendService;
	}

	/**
     * @param outsideVehicleChargeService
	 * @SET
	 */
	public void setOutsideVehicleChargeService(
			IOutsideVehicleChargeService outsideVehicleChargeService) {
		/*
		 *@set
		 *@this.outsideVehicleChargeService = outsideVehicleChargeService
		 */
		this.outsideVehicleChargeService = outsideVehicleChargeService;
	}

	/**
     * @return orgAdministrativeInfoComplexService
	 * @GET
	 */
	public IOrgAdministrativeInfoComplexService getOrgAdministrativeInfoComplexService() {
		/*
		 *@get
		 *@ return orgAdministrativeInfoComplexService
		 */
		return orgAdministrativeInfoComplexService;
	}

	/**
     * @param orgAdministrativeInfoComplexService
	 * @SET
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		/*
		 *@set
		 *@this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService
		 */
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
     * @return expressPartSalesDeptService
	 * @GET
	 */
	public IExpressPartSalesDeptService getExpressPartSalesDeptService() {
		/*
		 *@get
		 *@ return expressPartSalesDeptService
		 */
		return expressPartSalesDeptService;
	}

	/**
     * @param expressPartSalesDeptService
	 * @SET
	 */
	public void setExpressPartSalesDeptService(
			IExpressPartSalesDeptService expressPartSalesDeptService) {
		/*
		 *@set
		 *@this.expressPartSalesDeptService = expressPartSalesDeptService
		 */
		this.expressPartSalesDeptService = expressPartSalesDeptService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setFossConfigEntityService(
			FossConfigEntityService fossConfigEntityService) {
		this.fossConfigEntityService = fossConfigEntityService;
	}

	public ISaleDepartmentService getSaleDepartmentService() {
		return saleDepartmentService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setClientSendVtsPayableCheck(
			IClientSendVtsPayableCheck clientSendVtsPayableCheck) {
		this.clientSendVtsPayableCheck = clientSendVtsPayableCheck;
	}

	public void setPaymentStatusToVTSClient(
			IPaymentStatusToVTSClient paymentStatusToVTSClient) {
		this.paymentStatusToVTSClient = paymentStatusToVTSClient;
	}
	

}
