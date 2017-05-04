package com.deppon.foss.module.settlement.common.server.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.inteface.domain.fins.FossToFinsRemitCommonRequest;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillRepaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IFossConfigEntityService;
import com.deppon.foss.module.settlement.common.api.server.service.IFossToFinsRemitCommonService;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.FossConfigEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.RequestGreenHandWrapEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillRepaymentConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.ResponseFinsWrapDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.WriteoffInformationDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 提供FOSS到财务第三方付款公共接口
 * 
 * @ClassName: FossToFinsCommonService
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2016-7-3 上午11:30:02
 */
public class FossToFinsRemitCommonService implements IFossToFinsRemitCommonService {
    // 日志
    private static final Logger logger = LogManager.getLogger(FossToFinsRemitCommonService.class);

    /**
     * 注入还款单服务
     */
    private IBillRepaymentService billRepaymentService;

    /**
     * 支付宝ESB提供的服务端编码（ESB同事提供的）
     */
    public static String ZFB_ESB_SERVICE_CODE = "/FINS_ESB2FINS_INCOME_REPORTED";

    /**
     * 支付宝ESB提供的客户端编码（ESB同事提供的）
     */
    public static String ZFB_ESB_CLIENT_CODE = "/ESB_FOSS2ESB_INCOME_REPORTED";

    /**
     * 果果ESB提供的服务端编码（ESB同事提供的）
     */
    public static String GG_ESB_SERVICE_CODE = "/FINS_ESB2FINS_PUSH_RESULT_INF2FINS";

    /**
     * 果果ESB提供的客户端编码（ESB同事提供的）
     */
    public static String GG_ESB_CLIENT_CODE = "/ESB_FOSS2ESB_PUSH_RESULT_INF2FINS";

    /**
     * 注入子公司SERVICE
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;

    /**
     * 注入应收单IBillReceivableService
     */
    private IBillReceivableService billReceivableService;

    /**
     * 查询esb地址信息的接口
     */
    private IFossConfigEntityService fossConfigEntityService;

    private static final String version = "1.0";

    private static final String serviceCode = "ESB_FOSS2ESB_FOSS_ONLINEPAY_FINS";

    /**
     * 推送第三方付款数据到财务自助
     * 
     * @Title: pushRemittanceMessageToFince
     * @author： 269052 |zhouyuan008@deppon.com
     * @date 2016-7-3 上午11:28:21
     */
    @Override
    public void pushRemittanceMessToFins(RequestGreenHandWrapEntity param) {
        logger.info("推送数据到财务自助开始");
        if (param == null) {
            throw new SettlementException("数据错误，传递参数为空！");
        }
        /**
         * 封装参数
         */
        WriteoffInformationDto dto = buildRequestParam(param);

        if (dto == null) {
            throw new SettlementException("数据错误:传递参数为空！");
        }
        if (StringUtils.isEmpty(dto.getRemitDept())) {
            throw new SettlementException("数据错误：汇款部门编码为空！");
        }
        if (StringUtils.isEmpty(dto.getFundDept())) {
            throw new SettlementException("数据错误：款项所属部门编码为空！");
        }
        if (dto.getRemitTance() == null) {
            throw new SettlementException("数据错误:汇款金额不能为null！");
        }
        if (dto.getRemitTance().compareTo(BigDecimal.ZERO) == 0) {
            throw new SettlementException("数据错误：汇款金额不能小于0！");
        }
        if (dto.getAlreadyamount() == null) {
            throw new SettlementException("数据错误：已使用金额不能为null！");
        }
        if (dto.getUnuseamount() == null) {
            throw new SettlementException("数据错误：未使用金额不能为null！");
        }
        try {
            validaGreenHandWrap(param, dto);

        } catch (Exception e) {
            throw new SettlementException("FOSS同步到FINS财务自助失败,异常信息为: " + e.getMessage());
        }
        logger.info("推送数据到财务自助结束");
    }

	private void validaGreenHandWrap(RequestGreenHandWrapEntity param,
			WriteoffInformationDto dto) throws Exception {
		/**
		 * 封装调用财务自助参数
		 */
		/**
		 * 判断是支付宝还是裹裹
		 */
		String ESB_SERVICE_CODE = "";
		String ESB_CLIENT_CODE = "";
		if ("ZFB".equals(param.getResource()) || "ZFBCODE".equals(param.getResource())) {
		    ESB_SERVICE_CODE = ZFB_ESB_SERVICE_CODE;
		    ESB_CLIENT_CODE = ZFB_ESB_CLIENT_CODE;
		} else if ("GG".equals(param.getResource())) {
		    ESB_SERVICE_CODE = GG_ESB_SERVICE_CODE;
		    ESB_CLIENT_CODE = GG_ESB_CLIENT_CODE;
		} else {
		    throw new SettlementException("数据错误：数据来源不明确！");
		}
		FossConfigEntity configEntity = fossConfigEntityService
		        .queryFossConfigEntityByServerCode(ESB_SERVICE_CODE);
		if (configEntity == null) {
		    throw new SettlementException("数据错误：接口地址未配置！接口编码：" + ESB_SERVICE_CODE.replace("/", ""));
		}
		// 获取ESB请求的地址
		String esbAddr = configEntity.getEsbAddr();
		// http://192.168.67.12:8180/esb2/rs/ESB_FOSS2ESB_INCOME_REPORTED
		logger.info("查询到的ESB地址为：" + esbAddr);
		// 请求完整路径
		String url = esbAddr + ESB_CLIENT_CODE;
		logger.info("FOSS到FINS请求的ESB地址为：" + url);
		/**
		 * 封装RequestEntity
		 */
		JSONObject jb = JSONObject.fromObject(dto);
		String content = jb.toString();
		RequestEntity requestEntity = null;
		try {
		    requestEntity = new StringRequestEntity(content, "application/json", "UTF-8");
		} catch (UnsupportedEncodingException e) {
		    throw new SettlementException("数据错误:封装RequestEntity错误！");
		}
		/**
		 * 请求财务自助地址,封装URL和RequestEntity
		 */
		try {
		    String responseBody = HttpClientUtil.postRequest(url, requestEntity);
		    JSONObject jsonOb = JSONObject.fromObject(responseBody);
		    if (jsonOb != null) {
		        Object object = JSONObject.toBean(jsonOb, ResponseFinsWrapDto.class);
		        ResponseFinsWrapDto responseFinsWrapDto = (ResponseFinsWrapDto) object;
		        logger.info("调用FINS服务端接口后响应的结果...");
		        logger.info("是否成功：" + responseFinsWrapDto.getIsSuccess());
		        logger.info("失败原因为: " + responseFinsWrapDto.getFailReason());
		        if ("0".equals(responseFinsWrapDto.getIsSuccess())) {
		            throw new SettlementException("支付编码已存在！");
		        }
		    }
		} catch (Exception e) {
		    if ("ZFB".equals(param.getResource()) || "ZFBCODE".equals(param.getResource())) {
		        this.sendRemittanceMessToFins(dto);
		    } else {
		        throw e;
		    }
		}
	}

    /**
     * @Title: sendRemittanceMessToFins
     * @Description:
     * @author 321603
     * @date 2016-10-13
     * @throws
     */
    private void sendRemittanceMessToFins(WriteoffInformationDto param) {
        try {
            // 设置服务编码
            AccessHeader accessHeader = new AccessHeader();
            FossToFinsRemitCommonRequest requestInfo = new FossToFinsRemitCommonRequest();
            BeanUtils.copyProperties(param, requestInfo);
            // 设置唯一标示
            accessHeader.setBusinessId(UUIDUtils.getUUID());
            // 设置服务编码
            accessHeader.setEsbServiceCode(serviceCode);
            // 设置版本号
            accessHeader.setVersion(version);
            accessHeader.setBusinessDesc1("重新推送第三方付款数据到财务自助");
            // 发送消息
            logger.info("sendRemittanceMessToFins:send info start.............");
            ESBJMSAccessor.asynReqeust(accessHeader, requestInfo);
            logger.info("sendRemittanceMessToFins:send info end.............");
        } catch (Exception e) {
            throw new SettlementException("重新推送第三方付款数据到财务自助失败:" + e.getMessage());
        }
    }

    /**
     * 封装FOSS到财务自助参数
     * 
     * @Title: buildRequestParam
     * @author： 269052 |zhouyuan008@deppon.com
     * @date 2016-7-3 下午4:52:30
     */
    private WriteoffInformationDto buildRequestParam(RequestGreenHandWrapEntity param) {
        logger.info("封装第三方支付数据到财务自助开始");
        if (param == null) {
            throw new SettlementException("推送异常数据，参数为空！");
        }
        /*
         * if (StringUtil.isEmpty(param.getIsException())) { throw new
         * SettlementException("是否异常为空！", ""); }
         */
        // 判断应收单号、在线支付编号、还款金额、记账日期是否为空
        /*
         * if (StringUtil.isEmpty(param.getReceivableNo())) { throw new
         * SettlementException("应收单号为空！", ""); }
         */
        // 判空
        if (param.getDoptime() == null) {
            // 记录日志
            logger.error("记账日期为空");
            throw new SettlementException("记账日期为空！", "");
        }
        // 判空
        if (param.getDopAmount() == null) {
            // 记录日志
            logger.error("还款金额为空");
            throw new SettlementException("还款金额为空！", "");
        }
        // 判空
        if (param.getDopAmount().compareTo(BigDecimal.ZERO) <= 0) {
            // 记录日志
            logger.error("还款金额小于等于0");
            throw new SettlementException("还款金额小于等于0！", "");
        }
        /**
         * 封装传递参数
         */
        BillReceivableConditionDto dto = new BillReceivableConditionDto();
        WriteoffInformationDto writeoffInformationDto = new WriteoffInformationDto();
        // 裹裹和支付宝的编号不一致
        if ("ZFB".equals(param.getResource())) {
            /**
             * 编号(运单号)
             */
            writeoffInformationDto.setBillNum(param.getWaybillNo());
            /**
             * 款项类别 1：运费，2：补贴 (我传过去的是运费),3:支付宝支付
             */
            writeoffInformationDto.setFundType("3");            
        } else if ("GG".equals(param.getResource())) {
            /**
             * 编号(运单号)
             */
            writeoffInformationDto.setBillNum("GG" + param.getWaybillNo());
            /**
             * 款项类别 1：运费，2：补贴 (我传过去的是运费),3:支付宝支付
             */
            writeoffInformationDto.setFundType("1");
        } else if ("ZFBCODE".equals(param.getResource())) {
            /**
             * 编号(运单号)
             */
            writeoffInformationDto.setBillNum(param.getZfbBillNum());
            /**
             * 款项类别 1：运费，2：补贴 (我传过去的是运费),3:支付宝支付,4:支付宝条码支付
             */
            writeoffInformationDto.setFundType("4");
            logger.info("款项类别：" + writeoffInformationDto.getFundType());
        } else {
            throw new SettlementException("数据错误:数据来源不明确！" + param.getResource());
        }
        /**
         * 汇款时间(对应DOP传给我的时间)
         */
        writeoffInformationDto.setRemitDate(param.getDoptime());

        /**
         * 根据运单号查询应收单信息 根据支付宝 支付和条码支付使用不同的查询条件进行查询
         */
        validaHandWrapEntity(param, dto, writeoffInformationDto);
        logger.info("封装第三方支付数据到财务自助结束");
        return writeoffInformationDto;
    }

	private void validaHandWrapEntity(RequestGreenHandWrapEntity param,
			BillReceivableConditionDto dto,
			WriteoffInformationDto writeoffInformationDto) {
		List<BillReceivableEntity> list = new ArrayList<BillReceivableEntity>();        
        if ("ZFBCODE".equals(param.getResource()) && param.getStatementBillNos() != null
                && param.getStatementBillNos().size() != 0) {
            // 通过对账单号查询应收单信息
            list = billReceivableService.queryReceivableBySBNO(param.getStatementBillNos());
        } else {
            dto.setWaybillNo(param.getWaybillNo());
            list = billReceivableService.queryBillReceivableByCondition(dto);
        }
        // 根据对账单号查询应收单信息 先判断来源条码支付走对账单号查询 非支付宝条码支付走 根据运单号查询 queryReceivableByStatementBillNo

        /**
         * 催款部门编码，果果作废了，单子就不能重推到财务自助，在之前获取标杆编码
         */
        String dunningCode = "";
        // 校验应收单情况
        if (list.size() == 0 && StringUtils.isEmpty(param.getDunningOrgCode())) {
            throw new SettlementException("数据异常：应收单信息不存在！");
        } else if (list != null && list.size() > 0) {
            dunningCode = list.get(0).getDunningOrgCode();
        } else {
            dunningCode = param.getDunningOrgCode();
        }

        /**
         * 假如是到付的情况，重新设置公布部门
         */
        if (FossConstants.YES.equals(param.getIsDr())) {
            dunningCode = param.getDunningOrgCode();
        }

        logger.info("催款部门编码为：" + dunningCode);
        /**
         * 根据部门编码查询标杆编码（这里是根据应收单里的催款部门编码查询标杆编码）
         */
        String dunningCodeDP = orgAdministrativeInfoService.queryUnifiedCodeByCode(dunningCode);

        if (StringUtils.isEmpty(dunningCodeDP)) {
            throw new SettlementException("催款部门编码为空！");
        }
        logger.info("催款部门对应标杆编码为：" + dunningCodeDP);

        /**
         * 汇款部门(应收单中的催款部门对应的标杆编码DP开头的)
         */
        writeoffInformationDto.setRemitDept(dunningCodeDP);
        /**
         * 款项所属部门(应收单中的催款部门对应的标杆编码DP开头的)
         */
        writeoffInformationDto.setFundDept(dunningCodeDP);
        /**
         * 汇款金额(对应DOP传给我的金额)
         */
        writeoffInformationDto.setRemitTance(param.getDopAmount());
        /**
         * 汇入账号(我FOSS这是为空,FINS那边有默认的)
         */
        writeoffInformationDto.setRemitAccount(param.getReceivableNo());
        /**
         * 判断是否为异常数据推送，异常数据推送的金额对于财务自助设置不一样
         */
        if ("Y".equals(param.getIsException())) {
            /**
             * 已使用金额0
             */
            writeoffInformationDto.setAlreadyamount(BigDecimal.ZERO);
            /**
             * 未使用金额(DOP传递过来的金额)
             */
            writeoffInformationDto.setUnuseamount(param.getDopAmount());
        } else {
            validaRequestGreen(param, writeoffInformationDto);

        }
	}

	private void validaRequestGreen(RequestGreenHandWrapEntity param,
			WriteoffInformationDto writeoffInformationDto) {
		// FOSS核销成功，推送数据到财务为已使用
		if ("ZFB".equals(param.getResource()) || "ZFBCODE".equals(param.getResource())) {
		    /**
		     * 已使用金额(FOSS核销金额)
		     */
		    writeoffInformationDto.setAlreadyamount(param.getDopAmount());
		    /**
		     * 未使用金额0
		     */
		    writeoffInformationDto.setUnuseamount(BigDecimal.ZERO);
		} else if ("GG".equals(param.getResource())) {
		    /**
		     * 已使用金额(FOSS核销金额)
		     */
		    /**
		     * 裹裹对应的钱对于结算来说，有可能存在多出的情况，则公布数据的时候一部分已占用，一部分未占用 根据裹裹在线支付编号去查询核销的金额
		     */
		    /**
		     * 根据支付编号去查询已还款金额
		     */
		    BillRepaymentConditionDto condition = new BillRepaymentConditionDto();
		    String onlinePaymentNo = "GG" + param.getWaybillNo();
		    condition.setOnlinePaymentNo(onlinePaymentNo);
		    condition.setActive("Y");
		    List<BillRepaymentEntity> lists = billRepaymentService
		            .queryBillRepaymentByCondition(condition);
		    if (lists.size() == 0) {
		        throw new SettlementException("数据错误:不存在有效还款单信息！还款单号：" + onlinePaymentNo);
		    }
		    // 已核销金额为还款单金额
		    BigDecimal verifyAmount = lists.get(0).getAmount();
		    writeoffInformationDto.setAlreadyamount(verifyAmount);
		    /**
		     * 未使用金额DOP传递的金额减去已核销金额
		     */
		    writeoffInformationDto.setUnuseamount(param.getDopAmount().subtract(verifyAmount));
		} else {
		    throw new SettlementException("数据错误：数据来源不明确！");
		}
	}

    /*********** setter *************/
    public void setFossConfigEntityService(IFossConfigEntityService fossConfigEntityService) {
        this.fossConfigEntityService = fossConfigEntityService;
    }

    public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

    public void setBillReceivableService(IBillReceivableService billReceivableService) {
        this.billReceivableService = billReceivableService;
    }

    public void setBillRepaymentService(IBillRepaymentService billRepaymentService) {
        this.billRepaymentService = billRepaymentService;
    }

}
