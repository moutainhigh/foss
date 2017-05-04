package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.order.api.server.dao.IPdaSignEntityDao;
import com.deppon.foss.module.pickup.pda.api.shared.exception.PdaProcessException;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IArrivesheetDao;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.ArriveSheetMannerException;
import com.deppon.foss.module.pickup.predeliver.api.shared.util.BigDecimalOperationUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.sign.api.server.dao.IRepaymentDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.server.service.IzfbCodeRepaymentService;
import com.deppon.foss.module.pickup.sign.api.shared.define.RepaymentConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirAgencyQueryDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.FinancialDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RepaymentDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.RepaymentException;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRelateDetailEntityService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillReceivableEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IFossConfigEntityService;
import com.deppon.foss.module.settlement.common.api.server.service.IFossToFinsRemitCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.IPdaPosManageService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.FossConfigEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.InterfaceLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.RequestGreenHandWrapEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VerificationEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCResponseDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CommonQueryParamDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayRequestEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayResponseEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossSearchTradeRequestDO;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossSearchTradeResponseDO;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossToCubcRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PaymentSettlementDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PdaRepaymentDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PosCardManageDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.TradeDO;
import com.deppon.foss.module.settlement.common.api.shared.dto.VestBatchResult;
import com.deppon.foss.module.settlement.common.api.shared.exception.CUBCGrayException;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.HttpClientUtils;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICubcSettlementService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IPaymentSettlementService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ITakingService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IVtsValidateAndSettlementService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.SettlementPayToVtsDto;
import com.deppon.foss.module.settlement.consumer.api.shared.exception.CubcSettleException;
import com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;



/**日期：2017年3月18日
 * 作者：378391 zhanglipeng
 * 包名：com.deppon.foss.module.pickup.sign.server.service.impl
 *工程名：pkp-sign
 *类说明：支付宝结清货款条码支付
 */
public class ZfbCodeRepaymentService implements IzfbCodeRepaymentService {
        
        private String queryTradeListUrl;
        public void setQueryTradeListUrl(String queryTradeListUrl) {
                this.queryTradeListUrl = queryTradeListUrl;
        }
        /** 结算应收单服务. */
        private IBillReceivableService billReceivableService;
        public void setBillReceivableService(
                        IBillReceivableService billReceivableService) {
                this.billReceivableService = billReceivableService;
        }

        private String grayByWaybillNoUrl;
        public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
                this.grayByWaybillNoUrl = grayByWaybillNoUrl;
        }
        
        private ICubcSettlementService cubcSettlementService;
        
        public ICubcSettlementService getCubcSettlementService() {
                return cubcSettlementService;
        }
        public void setCubcSettlementService(
                        ICubcSettlementService cubcSettlementService) {
                this.cubcSettlementService = cubcSettlementService;
        }
        private static final String SERVICE_CODE = "com.deppon.foss.module.pickup.sign.server.service.impl.ZfbCodeRepaymentService";

        public void setSaleDepartmentService(
                        ISaleDepartmentService saleDepartmentService) {
                this.saleDepartmentService = saleDepartmentService;
        }

        public static void setESB_SERVICE_CODE(String eSB_SERVICE_CODE) {
                ESB_SERVICE_CODE = eSB_SERVICE_CODE;
        }

        public static void setESB_CLIENT_CODE(String eSB_CLIENT_CODE) {
                ESB_CLIENT_CODE = eSB_CLIENT_CODE;
        }

        public void setIsDriver(String isDriver) {
                this.isDriver = isDriver;
        }
        // 支付宝条码支付
    private static final String alipayWithScanCode = "支付宝条码支付";

    /** 日志服务. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZfbCodeRepaymentService.class);

    /** 付款信息dao. */
    private IRepaymentDao repaymentDao;

    /** 到达联服务. */
    private IArriveSheetManngerService arriveSheetManngerService;

    /** 签收结算货款服务. */
    private IPaymentSettlementService paymentSettlementService;

    /** 实际货物服务. */
    private IActualFreightService actualFreightService;

    /** 更改单service. */
    private IWaybillRfcService waybillRfcService;

    /** 运单接口service类 */
    private IWaybillExpressService waybillExpressService;

    /**
     * 张新 快递代理服务接口
     */
    private ILdpAgencyDeptService ldpAgencyDeptService;

    /**
     * 业务互斥锁服务
     */
    private IBusinessLockService businessLockService;

    /**
     * 零
     */
    private static final int ZERO = 0;

    private IRepaymentService repaymentService;

    /**
     * 运单service
     */
    private IWaybillManagerService waybillManagerService;

    private ITakingService takingService;

    /**
     * 子母件服务接口层
     */
    private IWaybillRelateDetailEntityService waybillRelateDetailEntityService;

    /**
     * 运单签收结果
     */
    private IWaybillSignResultService waybillSignResultService;

    /**
     * T+0报表
     * 
     * @author 309603 yangqiang
     * @date 2016-02-23
     */
    private IPdaPosManageService pdaPosManageService;

    /**
     * PDA处理DAO
     * 
     */

    private IPdaSignEntityDao pdaSignEntityDao;

    // 支付宝条码支付结清货款
    private IzfbCodeRepaymentService zfbCodeRepaymentService;

    public void setZfbCodeRepaymentService(IzfbCodeRepaymentService zfbCodeRepaymentService) {
        this.zfbCodeRepaymentService = zfbCodeRepaymentService;
    }

    /**
     * 应收单Dao
     */
    private IBillReceivableEntityDao billReceivableEntityDao;

    /**
     * 注入整车web结清货款Service
     */
    private IVtsValidateAndSettlementService vtsValidateAndSettlementService;

    /**
     * 查询ESB地址信息的接口
     */
    private IFossConfigEntityService fossConfigEntityService;

    /**
     * 综合服务接口 部门信息 Service接口
     */
    @Autowired
    private ISaleDepartmentService saleDepartmentService;

    /**
         * 
         */
    private IActualFreightDao actualFreightDao;

    private IArrivesheetDao arrivesheetDao;

    private INotifyCustomerService notifyCustomerService;

    private IHandleQueryOutfieldService handleQueryOutfieldService;

    private static final String dateStr = "2013-08-01";

    private static final String format = "yyyy-MM-dd";

    private IVehicleAssembleBillService vehicleAssembleBillService;

    private static final String TOTAL = "ALL";

    private static final int WAYBILLNOLENGTH = 10;

    private static final String CREATEUSER = "FOSS";

    private static final String ISSUCCESS_Y = "Y";

    private static final String ISSUCCESS_N = "N";

    /**
     * ESB提供的服务端编码
     */

    public static String ESB_SERVICE_CODE = "TPS_ESB2TPS_VERIFICATIONREPORT_FOR_VTS";

    /**
     * ESB提供的客户端编码
     */

    public static String ESB_CLIENT_CODE = "ESB_FOSS2ESB_VERIFICATIONREPORT_FOR_VTS";

    /**
     * 支付宝条码支付结清货款
     * 
     * @author 378391 zhanglipeng
     * @date 2016-3-1
     * @param CommonQueryParamDto dto empCode 员工工号 empName 员工姓名 orgCode 登录部门 isDriver 是否为司机
     *        tradeSerialNo 交易流水号 List<String> waybillNo 运单号
     * @return List<PdaRepaymentDto> 未核销运单
     * 
     */
    // 登录员工默认为司机
    private String isDriver = "YES";

    // foss 推送数据到第三方财务自助接口
    private IFossToFinsRemitCommonService fossToFinsRemitCommonService;

    // set 方法
    public void setFossToFinsRemitCommonService(IFossToFinsRemitCommonService fossToFinsRemitCommonService) {
        this.fossToFinsRemitCommonService = fossToFinsRemitCommonService;
    }

    @Override
    public void zfbCodeWrietoff(VerificationEntity verificationEntity) {
        CommonQueryParamDto commonQueryParamDto = new CommonQueryParamDto();
        List<CommonQueryParamDto> commonQueryParamDtos = new ArrayList<CommonQueryParamDto>();
        commonQueryParamDto.setEmpCode(verificationEntity.getEmpCode());// 登录员工工号
        commonQueryParamDto.setEmpName(verificationEntity.getEmpName());// 登录员工姓名
        commonQueryParamDto.setOrgCode(verificationEntity.getOrgCode());// 登录员工部门
        commonQueryParamDto.setOrgName(verificationEntity.getOrgName());// 登录员工部门名称
        commonQueryParamDto.setIsDriver(isDriver);// 是否为司机默认为是
        commonQueryParamDto.setTradeSerialNo("ZFBCODE" + verificationEntity.getOutTradeNo());// 交易流水号（与推送财务自助编码一致）
        commonQueryParamDto.setSerialAmount(verificationEntity.getTotalAmount());// 交易金额
        commonQueryParamDto.setZfbPayType(verificationEntity.getPayType());// 设置付款方式
        commonQueryParamDto.setRecivibleNusString(verificationEntity.getCollectionAccount());// 设置收款账号
        commonQueryParamDto.setRepaymentType("PDA");
        // 运单号单据类型操作时间
        List<String> list = new ArrayList<String>();
        list.add(verificationEntity.getOutTradeNo());
        LOGGER.info("运单号：" + list.get(0));
        commonQueryParamDto.setWaybillNo(list);
        LOGGER.info("运单号集合：" + commonQueryParamDto.getWaybillNo().size());
        LOGGER.info("结清货款dto封装信息" + commonQueryParamDto.toString());
        commonQueryParamDtos.add(commonQueryParamDto);
        List<PdaRepaymentDto> addPaymentInfo = null;
        try {
            addPaymentInfo = addPaymentInfo(commonQueryParamDtos);
        } catch (Exception e) {
            LOGGER.info("运单号：" + verificationEntity.getOutTradeNo() + "异常");
        } finally {
            if (addPaymentInfo.size() > 0) {
                // 推送财务自助
                RequestGreenHandWrapEntity paramEntity = new RequestGreenHandWrapEntity();
                paramEntity.setWaybillNo(verificationEntity.getOutTradeNo());// 封装运单号
                paramEntity.setDopAmount(verificationEntity.getTotalAmount());// 封装金额
                LOGGER.info("核销后返回数据:" + addPaymentInfo.size());
                for (PdaRepaymentDto pdaRepaymentDto : addPaymentInfo) {
                    String flag = pdaRepaymentDto.getIsSuccess();
                    // 正常推送
                    if (ISSUCCESS_Y.equals(flag)) {
                        paramEntity.setIsPush("true");// 是否需要推送
                        paramEntity.setIsException("N");// 是否异常推送
                        paramEntity.setResource("ZFBCODE");// 来源
                        paramEntity.setCostType("4");// 设置费用类型支付宝条码支付
                        paramEntity.setDoptime(new Date());
                        paramEntity.setReceivableNo(verificationEntity.getCollectionAccount());// 封装汇款账号
                        paramEntity.setZfbBillNum("ZFBCODE" + verificationEntity.getOutTradeNo());// 推送财务自助编号
                        paramEntity.setIsDr(FossConstants.YES);//设置为到付
                        paramEntity.setDunningOrgCode(verificationEntity.getOrgCode());//设置催款部门编码
                    } else {
                        // 异常推送
                        paramEntity.setIsPush("true");// 是否需要推送
                        paramEntity.setIsException("Y");// 是否异常推送
                        paramEntity.setResource("ZFBCODE");// 来源
                        paramEntity.setCostType("4");// 设置费用类型支付宝条码支付
                        paramEntity.setDoptime(new Date());
                        paramEntity.setReceivableNo(verificationEntity.getCollectionAccount());// 封装汇款账号
                        paramEntity.setZfbBillNum("ZFBCODE" + verificationEntity.getOutTradeNo());// 推送财务自助编号
                        paramEntity.setIsDr(FossConstants.YES);//设置为到付
                        paramEntity.setDunningOrgCode(verificationEntity.getOrgCode());//设置催款部门编码
                    }
                    if ("true".equals(paramEntity.getIsPush())) {
                        fossToFinsRemitCommonService.pushRemittanceMessToFins(paramEntity);
                        LOGGER.info("推送数据成功！");
                    } else {
                        LOGGER.info("推送财务自助失败。。。。。");
                        throw new SettlementException("推送到财务自助数据异常！");
                    }

                }

            }
        }
    }

    @Override
    public List<PdaRepaymentDto> addPaymentInfo(List<CommonQueryParamDto> list) {
        // 记录日志
        LOGGER.info("支付宝条码支付自动结清货款开始。。。。");
        // 异常数据存储List
        List<PdaRepaymentDto> pdaRepaymentDtoList = new ArrayList<PdaRepaymentDto>();
        // 声明异常数据存储实体
        PdaRepaymentDto pdaRepaymentDto = null;
        // 错误单号
        List<String> wayBillNoList = null;
        // 错误单号对应的详细信息
        Map<String, String> msgMap = null;
        // 部分结清运单实体List
        List<CommonQueryParamDto> commonQueryParamDtoList = null;
        // 306579--guoxinru 声明整车结清货款dto
        SettlementPayToVtsDto vtsDto = null;
        // 306579--guoxinru 初始化接口日志实体；
        InterfaceLogEntity interfaceEntity = null;
        if (list == null || list.isEmpty()) {
            LOGGER.info("传入数据有误，请检查。自动结清货款结束。。。。");

            throw new RepaymentException("传入数据有误，请检查。");
        }

        for (CommonQueryParamDto dto : list) {
            // 返回付款信息实例化
            // 实例化未结清的数据
            commonQueryParamDtoList = new ArrayList<CommonQueryParamDto>();
            // 实例化返回数据存储实体
            pdaRepaymentDto = new PdaRepaymentDto();
            // 默认为成功
            pdaRepaymentDto.setIsSuccess("Y");
            // 交易流水号
            pdaRepaymentDto.setTradeSerialNo(dto.getTradeSerialNo());
            // 实例化返回运单集合
            wayBillNoList = new ArrayList<String>();
            pdaRepaymentDto.setWayBillNoList(wayBillNoList);
            // 实例化信息
            msgMap = new HashMap<String, String>();
            // 放入错误Map
            pdaRepaymentDto.setMsgMap(msgMap);
            // 放入错误List
            pdaRepaymentDtoList.add(pdaRepaymentDto);
            // 运单实体集合
            ArrayList<RepaymentDto> repaymentDtolist = new ArrayList<RepaymentDto>();
            // 获取运单号
            List<String> waybillNoList = dto.getWaybillNo();
            // 定义执行开关
            boolean flag = true;
            // 306579--guoxinru 实例化整车结清货款dto
            vtsDto = new SettlementPayToVtsDto();
            // 306579--guoxinru 实例化日志实体
            interfaceEntity = new InterfaceLogEntity();
            try {
                // 校验运单是否存在
                checkWayBillNo(repaymentDtolist, waybillNoList, dto.getOrgCode());
            } catch (BusinessException e) {

                // 设为不成功
                pdaRepaymentDto.setIsSuccess("N");
                // 错误的运单号+信息
                wayBillNoList.addAll(waybillNoList);
                for (int i = 0; i < wayBillNoList.size(); i++) {
                    if (i == 0) {
                        msgMap.put(wayBillNoList.get(i), e.getErrorCode());
                    } else {
                        msgMap.put(wayBillNoList.get(i), "");
                    }
                }
                e.printStackTrace();
                // 下面代码不执行
                flag = false;
            }
            if (flag) {
                // 对付款对象进行排序
                // 按时间排序，运单核销结清时按业务时间先后排序
                Collections.sort(repaymentDtolist, new Comparator<RepaymentDto>() {
                    // 按照业务发生时间排序
                    @Override
                    public int compare(RepaymentDto o1, RepaymentDto o2) {
                        if (o1.getWaybillDto().getCreateTime().before(o2.getWaybillDto().getCreateTime())) {
                            return -1;
                        }
                        return 1;
                    }
                });
                // 准备查询数据 TODO
                if (!alipayWithScanCode.equals(dto.getZfbPayType())) {
                    PosCardManageDto posCardManageDto = new PosCardManageDto();
                    posCardManageDto.setTradeSerialNo(dto.getTradeSerialNo());
                    PosCardEntity posCardEntity = null;
                    // posCardManageDto.setBelongMoudleCode(SettlementDictionaryConstants.NCI_SETTLE);
                    // 查询T+0报表数据
                    //根据交流水号和部门编码查询POS刷卡数据   灰度  353654---start
                    String vestSystemCode = null;
                    List<PosCardEntity> posCardEntitys = null;
                    try {
                        CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(waybillNoList,
                                        SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".addPaymentInfo",
                                        SettlementConstants.TYPE_FOSS);
                        CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
                        List<VestBatchResult> list1 = response.getVestBatchResult();
                        vestSystemCode = list1.get(0).getVestSystemCode();      
                                } catch (Exception e) {
                                        LOGGER.info("灰度分流失败,"+"错误方法位置："+SERVICE_CODE+".addPaymentInfo");
                                                throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
                                }
                                if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
                                        posCardEntitys = pdaPosManageService.queryPosCardData(posCardManageDto).getPosCardEntitys();
                                        // 是否存在
                                        if (posCardEntitys == null || posCardEntitys.isEmpty()) {
                                                // 设为不成功
                                                pdaRepaymentDto.setIsSuccess("N");
                                                wayBillNoList.add(dto.getTradeSerialNo() + "流水号");
                                                msgMap.put(dto.getTradeSerialNo() + "流水号", "该交易流水号不存在！");
                                                break;
                                        } else {
                                                posCardEntity = posCardEntitys.get(0);
                                        }
                                        if(posCardEntity != null){
                                                @SuppressWarnings("unused")
                                                BigDecimal amounts = posCardEntity.getUnUsedAmount();
                                        }
                                }
                                if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
                                        //TODO  待定
                                }
                                //根据交流水号和部门编码查询POS刷卡数据   灰度  353654---end
                }
                BigDecimal amount = dto.getSerialAmount();
                // 循环结清账单
                for (int i = 0; i < repaymentDtolist.size(); i++) {

                    RepaymentDto repaymentDto = repaymentDtolist.get(i);
                    // 金额大于0可以进入结清货款
                    if (amount.compareTo(BigDecimal.ZERO) == 1) {
                        MutexElement mutexElement = new MutexElement(repaymentDto.getWaybillDto()
                                .getWaybillNo(), "结清货款", MutexElementType.REPAYMENT_NO);
                        // 互斥锁定
                        boolean isLocked = businessLockService.lock(mutexElement, ZERO);
                        // 如果没有上锁
                        if (!isLocked) {

                            pdaRepaymentDto.setIsSuccess("N"); // 设为不成功
                            for (int j = i; j < repaymentDtolist.size(); j++) {
                                wayBillNoList.add(repaymentDtolist.get(j).getWaybillDto().getWaybillNo());
                                if (j == i) {
                                    msgMap.put(repaymentDtolist.get(j).getWaybillDto().getWaybillNo(),
                                            "此单据正在操作中，请稍后再试！");
                                } else {
                                    msgMap.put(repaymentDtolist.get(j).getWaybillDto().getWaybillNo(),
                                            "请稍后再试！");
                                }
                            }
                            break;
                        }
                        // 获取还需要支付的代收货款
                        BigDecimal receiveableAmount = repaymentDto.getFinancialDto().getReceiveableAmount() == null ? BigDecimal.ZERO
                                : repaymentDto.getFinancialDto().getReceiveableAmount();
                        // 获取还需支付的运费
                        BigDecimal receiveablePayAmoout = repaymentDto.getFinancialDto()
                                .getReceiveablePayAmoout() == null ? BigDecimal.ZERO : repaymentDto
                                .getFinancialDto().getReceiveablePayAmoout();
                        // 准备user 数据
                        UserEntity user = new UserEntity();
                        OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
                        // 获取应收单实体
                        BillReceivableEntity entity = queryBillReceivable(repaymentDto.getWaybillDto().getWaybillNo());
                        // 当前部门编码
                        dept.setCode(entity.getReceivableOrgCode());
                        // 当前部门名称
                        dept.setName(entity.getReceivableOrgName());
                        // 司机姓名
                        user.setEmpName(dto.getEmpName());
                        EmployeeEntity employee = new EmployeeEntity();
                        employee.setEmpName(dto.getEmpName());
                        // 司机编码
                        user.setEmpCode(dto.getEmpCode());
                        employee.setEmpCode(dto.getEmpCode());
                        user.setEmployee(employee);
                        // 设置到UserContext
                        UserContext.setCurrentUser(user);
                        /*
                         * try { Class c =
                         * Class.forName("com.deppon.foss.framework.server.context.SessionContext"
                         * ); Constructor c0 = c.getDeclaredConstructor();
                         * 
                         * c0.setAccessible(true); SessionContext po = (SessionContext)
                         * c0.newInstance(); } catch (Exception e1) { e1.printStackTrace(); }
                         */
                        CurrentInfo currentInfo = new CurrentInfo(user, dept);
                        // 当流水号金额大于等于代收货款和运费之和，按照运费和代收货款结清
                        if (amount.compareTo(receiveableAmount.add(receiveablePayAmoout)) != -1) {
                            RepaymentEntity repaymentEntity = increaseRepaymentEntity(dto, repaymentDto,
                                    receiveableAmount, receiveablePayAmoout);
                            // 新增付款信息
                            try {
                                // 结清货款--判断是否为整车 306579 guoxinru
                                if (isVehicle(repaymentEntity.getWaybillNo())) {
                                    org.apache.commons.beanutils.BeanUtils.copyProperties(vtsDto,
                                            repaymentEntity);
                                    // 调用整车web结清货款service
                                    // 封装日志实体
                                    addLog(vtsDto, interfaceEntity, repaymentEntity);
                                    LOGGER.info("整车PDA结清货款service，运单号：" + repaymentEntity.getWaybillNo());
                                    vtsValidateAndSettlementService
                                            .ValidateAndSettlement(vtsDto, currentInfo);
                                    // foss同步结清状态至vts
                                    LOGGER.info("foss同步结清状态至vts，运单号：" + repaymentEntity.getWaybillNo());
                                    sendSettleStatusToVts(repaymentEntity.getWaybillNo());
                                    // 接口日志添加成功信息
                                    interfaceEntity.setIsSuccess(ISSUCCESS_Y);
                                    interfaceEntity.setCorrectLog("vts调用foss银行卡结清货款成功!");
                                } else {
                                    zfbCodeRepaymentService.addPaymentInfo(repaymentEntity, currentInfo,
                                            repaymentDto.getWaybillDto());

                                    LOGGER.info("对账单结清货款无异常。。。。。");
                                }
                            } catch (BusinessException e) {
                                errMsgByBusiness(pdaRepaymentDto, wayBillNoList, msgMap, repaymentDtolist, i,
                                        e.getErrorCode());
                                e.printStackTrace();
                                StringWriter writer = new StringWriter();
                                e.printStackTrace(new PrintWriter(writer));
                                // 接口日志添加失败信息
                                interfaceEntity.setIsSuccess(ISSUCCESS_N);
                                interfaceEntity.setCorrectLog("vts调用foss银行卡结清货款失败！：" + writer.toString());
                                break;
                            } catch (Exception e) {
                                errMsgByBusiness(pdaRepaymentDto, wayBillNoList, msgMap, repaymentDtolist, i,
                                        "线路繁忙，请稍后重试");
                                e.printStackTrace();
                                StringWriter writer = new StringWriter();
                                e.printStackTrace(new PrintWriter(writer));
                                // 接口日志添加失败信息
                                interfaceEntity.setIsSuccess(ISSUCCESS_N);
                                interfaceEntity.setCorrectLog("vts调用foss银行卡结清货款失败！：" + writer.toString());
                                break;
                            } 
                            // 可用金额减少
                            amount = amount.subtract(receiveableAmount.add(receiveablePayAmoout));
                            if ((repaymentEntity.getStorageFee() == null ? BigDecimal.ZERO : repaymentEntity
                                    .getStorageFee()).compareTo(BigDecimal.ZERO) == 1) {// 保管费不为0；
                                wayBillNoList.add(repaymentDto.getWaybillDto().getWaybillNo()); // 运单

                                msgMap.put(repaymentDto.getWaybillDto().getWaybillNo(), "该运单存在保管费，不能结清。");
                            }

                        } else {
                            // 可用余额大于运费小于代收货款，优先结清运费
                            if (amount.compareTo(receiveablePayAmoout) != -1) {// 可用余额大于等于运费
                                // 准备结清数据
                                RepaymentEntity repaymentEntity = increaseRepaymentEntity(dto, repaymentDto,
                                        amount.subtract(receiveablePayAmoout), receiveablePayAmoout);
                                // 调用结清方法去结清数据
                                try {
                                    // 结清货款--判断是否为整车 306579 guoxinru
                                    validaSettlementPayToVtsDto(vtsDto,
                                                                                        interfaceEntity, repaymentDto,
                                                                                        currentInfo, repaymentEntity);
                                } catch (BusinessException e) {
                                    errMsgByBusiness(pdaRepaymentDto, wayBillNoList, msgMap,
                                            repaymentDtolist, i, e.getErrorCode());
                                    e.printStackTrace();
                                    StringWriter writer = new StringWriter();
                                    e.printStackTrace(new PrintWriter(writer));
                                    // 接口日志添加失败信息
                                    interfaceEntity.setIsSuccess(ISSUCCESS_N);
                                    interfaceEntity.setCorrectLog("vts调用foss银行卡结清货款失败！：" + writer.toString());
                                    break;
                                } catch (Exception e) {
                                    errMsgByBusiness(pdaRepaymentDto, wayBillNoList, msgMap,
                                            repaymentDtolist, i, "线路繁忙，请稍后重试");
                                    e.printStackTrace();
                                    StringWriter writer = new StringWriter();
                                    e.printStackTrace(new PrintWriter(writer));
                                    // 接口日志添加失败信息
                                    interfaceEntity.setIsSuccess(ISSUCCESS_N);
                                    interfaceEntity.setCorrectLog("vts调用foss银行卡结清货款失败！：" + writer.toString());
                                    break;
                                } 
                                // 可用金额减去使用的金额
                                amount = amount.subtract(receiveableAmount.add(receiveablePayAmoout));
                                // 获取付款信息
                                // repaymentDto =
                                // repaymentService.queryPaymentByWaybillNo(repaymentEntity.getWaybillNo());
                                this.unsettled(pdaRepaymentDto, commonQueryParamDtoList, repaymentDto);
                            } else {// 可用余额小于运费
                                RepaymentEntity repaymentEntity = null;
                                if (amount.compareTo(BigDecimal.ZERO) == 1) {// 可用余额大于0
                                    // 可用余额小于运费，可用余额结运费
                                    repaymentEntity = increaseRepaymentEntity(dto, repaymentDto,
                                            BigDecimal.ZERO, amount);
                                    try {
                                        validaSettlementPayToVtsDto(vtsDto,
                                                                                                interfaceEntity, repaymentDto,
                                                                                                currentInfo, repaymentEntity);
                                    } catch (BusinessException e) {
                                        errMsgByBusiness(pdaRepaymentDto, wayBillNoList, msgMap,
                                                repaymentDtolist, i, e.getErrorCode());
                                        e.printStackTrace();
                                        StringWriter writer = new StringWriter();
                                        e.printStackTrace(new PrintWriter(writer));
                                        // 接口日志添加失败信息
                                        interfaceEntity.setIsSuccess(ISSUCCESS_N);
                                        interfaceEntity.setCorrectLog("vts调用foss银行卡结清货款失败！："
                                                + writer.toString());
                                        break;
                                    } catch (Exception e) {
                                        errMsgByBusiness(pdaRepaymentDto, wayBillNoList, msgMap,
                                                repaymentDtolist, i, "线路繁忙，请稍后重试");
                                        e.printStackTrace();
                                        StringWriter writer = new StringWriter();
                                        e.printStackTrace(new PrintWriter(writer));
                                        // 接口日志添加失败信息
                                        interfaceEntity.setIsSuccess(ISSUCCESS_N);
                                        interfaceEntity.setCorrectLog("vts调用foss银行卡结清货款失败！："
                                                + writer.toString());
                                        break;
                                    } 
                                }
                                amount = amount.subtract(receiveableAmount.add(receiveablePayAmoout));
                                // 获取付款信息
                                // repaymentDto =
                                // repaymentService.queryPaymentByWaybillNo(repaymentEntity.getWaybillNo());
                                this.unsettled(pdaRepaymentDto, commonQueryParamDtoList, repaymentDto);
                            }
                        }
                        // 解锁
                        businessLockService.unlock(mutexElement);
                    } else {
                        this.unsettled(pdaRepaymentDto, commonQueryParamDtoList, repaymentDto);

                    }

                }
            }
        }
        LOGGER.info("自动结清货款结束。。。。");
        return pdaRepaymentDtoList;
    }

        private void validaSettlementPayToVtsDto(SettlementPayToVtsDto vtsDto,
                        InterfaceLogEntity interfaceEntity, RepaymentDto repaymentDto,
                        CurrentInfo currentInfo, RepaymentEntity repaymentEntity)
                        throws IllegalAccessException, InvocationTargetException {
                if (isVehicle(repaymentEntity.getWaybillNo())) {
                    org.apache.commons.beanutils.BeanUtils.copyProperties(vtsDto,
                            repaymentEntity);
                    // 调用整车web结清货款service
                    // 封装日志实体
                    addLog(vtsDto, interfaceEntity, repaymentEntity);
                    LOGGER.info("整车PDA结清货款service，运单号：" + repaymentEntity.getWaybillNo());
                    vtsValidateAndSettlementService.ValidateAndSettlement(vtsDto,
                            currentInfo);
                    // foss同步结清状态至vts
                    LOGGER.info("foss同步结清状态至vts，运单号：" + repaymentEntity.getWaybillNo());
                    sendSettleStatusToVts(repaymentEntity.getWaybillNo());
                    // 接口日志添加成功信息
                    interfaceEntity.setIsSuccess(ISSUCCESS_Y);
                    interfaceEntity.setCorrectLog("vts调用foss银行卡结清货款成功!");
                } else {
                    zfbCodeRepaymentService.addPaymentInfo(repaymentEntity, currentInfo,
                            repaymentDto.getWaybillDto());
                }
        }

    private void addLog(SettlementPayToVtsDto vtsDto, InterfaceLogEntity interfaceEntity,
            RepaymentEntity repaymentEntity) {
        /**
         * 封装接口日志信息实体
         */
        interfaceEntity.setId(UUIDUtils.getUUID());
        interfaceEntity.setWaybillNo(repaymentEntity.getWaybillNo());
        interfaceEntity.setEsbCode(ESB_SERVICE_CODE);
        interfaceEntity.setSendContent(JSONObject.toJSONString(vtsDto));
        interfaceEntity.setCreateUser(CREATEUSER);
        interfaceEntity.setCreateTime(new Date());
    }

    private void errMsgByBusiness(PdaRepaymentDto pdaRepaymentDto, List<String> wayBillNoList,
            Map<String, String> msgMap, ArrayList<RepaymentDto> repaymentDtolist, int i, String e) {
        pdaRepaymentDto.setIsSuccess("N"); // 运单结清失败
        for (int j = i; j < repaymentDtolist.size(); j++) {
            wayBillNoList.add(repaymentDtolist.get(j).getWaybillDto().getWaybillNo());
            msgMap.put(repaymentDtolist.get(j).getWaybillDto().getWaybillNo(), e);
        }
    }

    /**
     * 未结清数据封装
     * 
     * @param pdaRepaymentDto
     * @param commonQueryParamDtoList
     * @param repaymentDto
     */
    private void unsettled(PdaRepaymentDto pdaRepaymentDto,
            List<CommonQueryParamDto> commonQueryParamDtoList, RepaymentDto repaymentDto) {
        // 准备返回数据
        CommonQueryParamDto commonQueryParamDto = new CommonQueryParamDto();
        pdaRepaymentDto.setIsSuccess("N"); // 设为失败
        addCommon(repaymentDto.getWaybillDto().getWaybillNo(), commonQueryParamDto);
        commonQueryParamDtoList.add(commonQueryParamDto);
        pdaRepaymentDto.setCommonQueryParamDtoList(commonQueryParamDtoList);
    }

    /**
     * 
     * 校验运单是否存在
     * 
     * @param repaymentDtolist
     * @param waybillNoList
     */

    private void checkWayBillNo(ArrayList<RepaymentDto> repaymentDtolist, List<String> waybillNoList,
            String orgCode) {
        // 判空
        if (waybillNoList == null || waybillNoList.isEmpty()) {
            throw new BusinessException("获取运单号失败。");
        }
        // 循环遍历运单号
        for (int i = 0; i < waybillNoList.size(); i++) {
            String waybillNo = waybillNoList.get(i);
            // 根据运单号查询要付款详细信息
            RepaymentDto repaymentDto = repaymentService.queryPaymentByWaybillNo(waybillNo);
            if (repaymentDto.getWaybillDto() == null) {
                throw new RepaymentException("获取运单详细信息失败。运单号：");
            }
            // 是否上报差错
            boolean isLack = repaymentService.isLack(waybillNo);

            // 过滤数据 如果运单有上报差错 派送方式非自提 整车、偏线、空运及快递货物免收保管费 add by yangkang
            if (isLack
                    || repaymentDto.getWaybillDto().getReceiveMethod().indexOf(SignConstants.RECEIVE_METHOD) == -1
                    || WaybillConstants.MONTH_PAYMENT.equals(repaymentDto.getWaybillDto().getPaidMethod())
                    || ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(repaymentDto.getWaybillDto()
                            .getProductCode())
                    || ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(repaymentDto
                            .getWaybillDto().getProductCode())
                    || ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(repaymentDto
                            .getWaybillDto().getProductCode())
                    || WaybillConstants.directDetermineIsExpressByProductCode(repaymentDto.getWaybillDto()
                            .getProductCode())) {
                repaymentDto.getWaybillDto().setStorageCharge(BigDecimal.ZERO);
            } else {
                // 判断该运单是否已经有保管费小票记录产生
                BigDecimal storageChargeSum = repaymentService.queryStorageChargeWithOtherRevenue(waybillNo);
                // 如果该运单已经产生了小票记录，则应付的保管费为运单现有保管费与已付保管费的差值
                if (storageChargeSum.compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal storageChargeTemp = repaymentDto.getWaybillDto().getStorageCharge()
                            .subtract(storageChargeSum);
                    if (storageChargeTemp.compareTo(BigDecimal.ZERO) > 0) {
                        repaymentDto.getWaybillDto().setStorageCharge(storageChargeTemp);
                    } else {
                        repaymentDto.getWaybillDto().setStorageCharge(BigDecimal.ZERO);
                    }
                }
            }
            if (repaymentDto.getWaybillDto().getStorageCharge() != null) {
                // 对保管费取整数
                repaymentDto.getWaybillDto()
                        .setStorageCharge(
                                repaymentDto.getWaybillDto().getStorageCharge()
                                        .setScale(0, BigDecimal.ROUND_HALF_UP));
            }
            repaymentDtolist.add(repaymentDto); // 成功添加到付款DtoList
            waybillNoList.remove(i--); // 成功一个从运单中移除一个
        }
    }

    /**
     * 新增付款信息(内含财务接口调用). 供PDA使用
     * 
     * @author 309603 yangqiang
     * @date 2016-3-3
     */
    @Transactional
    @Override
    public void addPaymentInfo(RepaymentEntity repaymentEntity, CurrentInfo currentInfo, WaybillDto waybilldto) {
        // 运单
        WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(repaymentEntity
                .getWaybillNo());
        // 子母件查询通用接口
        TwoInOneWaybillDto twoInOneWaybillDto = null;
        // 结清前验证
        verification(repaymentEntity, currentInfo, waybillEntity);
        // 查询实际货物信息
        ActualFreightEntity actentity = actualFreightService.queryByWaybillNo(repaymentEntity.getWaybillNo());

        // 判断运单 是否已终止或已作废
        if (actentity != null
                && (WaybillConstants.ABORTED.equals(actentity.getStatus()) || WaybillConstants.OBSOLETE
                        .equals(actentity.getStatus()))) {
            // 抛出业务异常
            throw new RepaymentException("改运单已终止或已作废");
        }

        // 得到结算应收单信息
        FinancialDto financialDto = repaymentService.queryFinanceSign(repaymentEntity.getWaybillNo());
        // 应收代收款
        BigDecimal receiveableAmount = financialDto.getReceiveableAmount() == null ? BigDecimal.ZERO
                : financialDto.getReceiveableAmount();
        // 应收到付款
        BigDecimal receiveablePayAmoout = financialDto.getReceiveablePayAmoout() == null ? BigDecimal.ZERO
                : financialDto.getReceiveablePayAmoout();

        // 是否返货单标识
        boolean isReturnOrders = false;
        // 判断是否快递
        if (waybillEntity != null
                && WaybillConstants.directDetermineIsExpressByProductCode(waybillEntity.getProductCode())) {
            // 运单快递
            WaybillExpressEntity waybillExpress = waybillExpressService
                    .queryWaybillExpressByNo(repaymentEntity.getWaybillNo());
            // 判断是否返货单
            if (waybillExpress != null && StringUtils.isNotBlank(waybillExpress.getReturnType())
                    && StringUtils.isNotBlank(waybillExpress.getOriginalWaybillNo())) {
                isReturnOrders = true;
                // 定义一个结算应收单信息
                FinancialDto financialDtoOriginal = null;
                // 创建Map集合存放查询条件
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("waybillNo", waybillExpress.getOriginalWaybillNo());
                params.put("active", FossConstants.YES);
                // 根据运单号、订单号判定是否子母件
                twoInOneWaybillDto = waybillRelateDetailEntityService
                        .queryWaybillRelateByWaybillOrOrderNo(params);
                // 判断是否子母件
                if (twoInOneWaybillDto != null
                        && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 是子母件
                    // 得到原单 结算应收单信息
                    financialDtoOriginal = repaymentService.queryFinanceSign(twoInOneWaybillDto
                            .getMainWaybillNo());
                } else {
                    // 得到原单 结算应收单信息
                    financialDtoOriginal = repaymentService.queryFinanceSign(waybillExpress
                            .getOriginalWaybillNo());
                }
                // 应收代收款
                BigDecimal receiveableAmountOriginal = financialDtoOriginal.getReceiveableAmount() == null ? BigDecimal.ZERO
                        : financialDtoOriginal.getReceiveableAmount();
                // 应收到付款
                BigDecimal receiveablePayAmooutOriginal = financialDtoOriginal.getReceiveablePayAmoout() == null ? BigDecimal.ZERO
                        : financialDtoOriginal.getReceiveablePayAmoout();
                // 创建付款信息对象存储原单付款信息
                RepaymentEntity originalreWaybillPayment = new RepaymentEntity();
                BeanUtils.copyProperties(repaymentEntity, originalreWaybillPayment);
                // 判断新单是否一次性结清
                if ((!SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT.equals(repaymentEntity
                        .getPaymentType()))
                        && (!SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT
                                .equals(repaymentEntity.getPaymentType()))
                        && (!repaymentEntity.getCodAmount().equals(receiveableAmount) || !repaymentEntity
                                .getActualFreight().equals(
                                        receiveablePayAmoout.add(receiveablePayAmooutOriginal)))) {
                    // 不是 抛出业务异常
                    throw new RepaymentException("该运单为原单，需一次结清");
                }
                // 设置运单信息为原单运单号
                originalreWaybillPayment.setWaybillNo(waybillExpress.getOriginalWaybillNo());
                // 按票返
                if (WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(waybillExpress.getReturnType())) {
                    originalreWaybillPayment.setActualFreight(receiveablePayAmooutOriginal);
                    originalreWaybillPayment.setCodAmount(receiveableAmountOriginal);
                    // 原单结清
                    this.addOriginalPaymentInfo(originalreWaybillPayment, currentInfo,
                            WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO, waybillExpress.getWaybillNo(),
                            twoInOneWaybillDto);
                } else { // 按件返
                    // 原单结清
                    this.addOriginalPaymentInfo(originalreWaybillPayment, currentInfo,
                            WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_PIECE, waybillExpress.getWaybillNo(),
                            twoInOneWaybillDto);
                }
                if (repaymentEntity.getActualFreight() != null
                        && repaymentEntity.getActualFreight().compareTo(BigDecimal.ZERO) > 0) {
                    repaymentEntity.setActualFreight(repaymentEntity.getActualFreight().subtract(
                            receiveablePayAmooutOriginal));
                }
                twoInOneWaybillDto = null;
            } else {
                // 通过原单编号、开单类型查询运单快递
                waybillExpress = waybillExpressService.queryWaybillByOriginalWaybillNo(
                        repaymentEntity.getWaybillNo(), WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
                // 判断是否是 返货单的原单
                if (waybillExpress != null) { // 是
                    // 抛出业务异常 此时原单不能结清货款
                    throw new RepaymentException("返货原单，不能结清");
                }
                // 创建Map集合存放查询条件
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("waybillNo", repaymentEntity.getWaybillNo());
                params.put("active", FossConstants.YES);
                // 根据运单号、订单号判定是否子母件
                twoInOneWaybillDto = waybillRelateDetailEntityService
                        .queryWaybillRelateByWaybillOrOrderNo(params);
                // 判断是否是子母件
                if (twoInOneWaybillDto != null
                        && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 为子母件
                    // 判断 是母件 还是子件
                    validateRepaymentEntity(repaymentEntity, currentInfo,
                                                        waybilldto, twoInOneWaybillDto);
                }
            }
        }
        // 创建 付款 对象 充作查询条件
        RepaymentEntity queryParam = new RepaymentEntity();
        queryParam.setActive(FossConstants.ACTIVE);
        queryParam.setWaybillNo(repaymentEntity.getWaybillNo());
        // 获得付款LIST--通过运单号
        List<RepaymentEntity> repaymentList = repaymentDao.searchRepaymentList(queryParam);
        // 实付运费
        BigDecimal actualFreight = repaymentEntity.getActualFreight() == null ? BigDecimal.ZERO
                : repaymentEntity.getActualFreight();
        // 代收货款
        BigDecimal codAmount = repaymentEntity.getCodAmount() == null ? BigDecimal.ZERO : repaymentEntity
                .getCodAmount();
        // 遍历付款LIST
        for (RepaymentEntity repayment : repaymentList) {
            // 财务单据未生成 或 财务单据生成中
            if (RepaymentConstants.STLBILL_NOGENERATE.equals(repayment.getStlbillGeneratedStatus())
                    || RepaymentConstants.STLBILL_GENERATEING.equals(repayment.getStlbillGeneratedStatus())) {
                codAmount = codAmount.add(repayment.getCodAmount());
                actualFreight = actualFreight.add(repayment.getActualFreight());
            }
        }

        // 判断实付运费大于应收到付款
        if (actualFreight.compareTo(receiveablePayAmoout) == 1) {
            // 抛出业务异常
            throw new RepaymentException("实付运费大于应收到付款,请检查最新运单");
        }

        // 判断代收货款大于应收代收款
        if (codAmount.compareTo(receiveableAmount) == 1) {
            // 抛出业务异常
            throw new RepaymentException("代收货款大于应收代收款,请检查最新运单");
        }

        // 判断是否统一结算
        if (FossConstants.ACTIVE.equals(actentity.getArriveCentralizedSettlement()) && !isReturnOrders) {
            // 判断 实付运费 大于 0 并且 代收货款等于 0
            if (repaymentEntity.getActualFreight().compareTo(BigDecimal.ZERO) > 0
                    && repaymentEntity.getCodAmount().compareTo(BigDecimal.ZERO) == 0) {
                // 判断是否是快递
                if (waybillEntity != null
                        && WaybillConstants.directDetermineIsExpressByProductCode(waybillEntity
                                .getProductCode())) {
                    if (!Arrays.asList("CT", "TT").contains(repaymentEntity.getPaymentType())) {
                        throw new RepaymentException("统一结算限制:运单产品类型为快递，实付运费>0，代收=0时。结清货款时付款方式只能为“月结”或“电汇”");
                    }
                } else { // 零担
                    if (!Arrays.asList("DT", "CT", "TT").contains(repaymentEntity.getPaymentType())) {
                        throw new RepaymentException(
                                "统一结算限制:运单产品类型为零担，实付运费>0，代收=0时。结清货款时付款方式只能为“临时欠款”、“月结”或“电汇”");
                    }
                }
                // 判断实付运费等于 0 并且 代收货款大于 0
            } else if (repaymentEntity.getActualFreight().compareTo(BigDecimal.ZERO) == 0
                    && repaymentEntity.getCodAmount().compareTo(BigDecimal.ZERO) > 0) {
                if (Arrays.asList("DT", "CT").contains(repaymentEntity.getPaymentType())) {
                    throw new RepaymentException("统一结算限制:实付运费=0，代收>0时，以代收货款标准限制付款方式（不可为临欠、月结）");
                }
                // 判断实付运费大于0 并且代收货款大于0
            } else if (repaymentEntity.getActualFreight().compareTo(BigDecimal.ZERO) > 0
                    && repaymentEntity.getCodAmount().compareTo(BigDecimal.ZERO) > 0) {
                if (!"TT".equals(repaymentEntity.getPaymentType())) {
                    throw new RepaymentException("统一结算限制:实付运费>0，代收>0时，付款方式限制（只可为电汇）");
                }
            }
        }
        // 调用 根据不同的付款方式 结清货款方法
        settleUpRepayment(repaymentEntity, currentInfo, receiveableAmount, receiveablePayAmoout,
                twoInOneWaybillDto, waybilldto);

    }

        private void validateRepaymentEntity(RepaymentEntity repaymentEntity,
                        CurrentInfo currentInfo, WaybillDto waybilldto,
                        TwoInOneWaybillDto twoInOneWaybillDto) {
                if (!repaymentEntity.getWaybillNo().equals(twoInOneWaybillDto.getMainWaybillNo())) { // 为子件
                    // 判断母件是否 已结清
                    if (!repaymentService.isPayment(twoInOneWaybillDto.getMainWaybillNo())) { // 没结清
                        // 得到原单 结算应收单信息
                        FinancialDto financialMain = repaymentService.queryFinanceSign(twoInOneWaybillDto
                                .getMainWaybillNo());
                        // 应收代收款
                        BigDecimal receiveableAmountMain = financialMain.getReceiveableAmount() == null ? BigDecimal.ZERO
                                : financialMain.getReceiveableAmount();
                        // 应收到付款
                        BigDecimal receiveablePayAmooutMain = financialMain.getReceiveablePayAmoout() == null ? BigDecimal.ZERO
                                : financialMain.getReceiveablePayAmoout();
                        // 创建付款信息对象存储母件付款信息
                        RepaymentEntity mainPayment = new RepaymentEntity();
                        BeanUtils.copyProperties(repaymentEntity, mainPayment);
                        // 设置运单信息为原单运单号
                        mainPayment.setWaybillNo(twoInOneWaybillDto.getMainWaybillNo());
                        mainPayment.setActualFreight(receiveableAmountMain);
                        mainPayment.setCodAmount(receiveablePayAmooutMain);
                        // 调用 根据不同的付款方式 结清货款方法
                        settleUpRepayment(mainPayment, currentInfo, receiveableAmountMain,
                                receiveablePayAmooutMain, twoInOneWaybillDto, waybilldto);
                        if (repaymentEntity.getActualFreight() != null
                                && repaymentEntity.getActualFreight().compareTo(BigDecimal.ZERO) > 0) {
                            repaymentEntity.setActualFreight(repaymentEntity.getActualFreight().subtract(
                                    receiveablePayAmooutMain));
                        }
                    }
                }
        }

    /**
     * 根据不同的付款方式 结清货款
     * 
     * @param repaymentEntity 付款 对象
     * @param currentInfo 当前信息对象
     * @param receiveableAmount 应收代收款
     * @param receiveablePayAmout 应收到付款
     * @param twoInOneWaybillDto 子母件通用接口
     * @author fangwenjun 237982
     * @date 2015年9月14日 上午8:57:10
     */
    @Transactional
    private void settleUpRepayment(RepaymentEntity repaymentEntity, CurrentInfo currentInfo,
            BigDecimal receiveableAmount, BigDecimal receiveablePayAmout,
            TwoInOneWaybillDto twoInOneWaybillDto, WaybillDto waybilldto) {
        // 生成付款编号
        StringBuffer dateStr = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        // 拼接付款编号
        dateStr = dateStr.append(repaymentEntity.getWaybillNo());
        // 付款方式为现金时，暂不调用结算接口，暂存到付款表中，30分钟后job再调用实收货款接口
        if ((SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH).equals(repaymentEntity
                .getPaymentType())) {
            // 付款编号
            repaymentEntity.setRepaymentNo(dateStr.toString());
            // 付款时间
            repaymentEntity.setPaymentTime(new Date());
            // 操作人
            repaymentEntity.setOperator(currentInfo.getEmpName());
            // 操作人编码
            repaymentEntity.setOperatorCode(currentInfo.getEmpCode());
            // 操作部门
            repaymentEntity.setOperateOrgName(currentInfo.getCurrentDeptName());
            // 操作部门编码
            repaymentEntity.setOperateOrgCode(currentInfo.getCurrentDeptCode());
            // 币种
            repaymentEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
            // 当实付运费和代收货款同时为0时 设置为财务单据无需生成
            if (BigDecimalOperationUtil.compare(receiveableAmount, BigDecimal.ZERO)
                    && BigDecimalOperationUtil.compare(receiveablePayAmout, BigDecimal.ZERO)) {
                // 更新ActualFreight表中的结清状态为已结清
                ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
                // 运单号
                actualFreightEntity.setWaybillNo(repaymentEntity.getWaybillNo());
                // 结清状态-已结清
                actualFreightEntity.setSettleStatus(FossConstants.YES);
                // 结款日期
                actualFreightEntity.setSettleTime(new Date());
                // 收货人
                actualFreightEntity.setDeliverymanName(repaymentEntity.getDeliverymanName());
                // 证件类型
                actualFreightEntity.setIdentifyType(repaymentEntity.getIdentifyType());
                // 证件号
                actualFreightEntity.setIdentifyCode(repaymentEntity.getIdentifyCode());
                if (StringUtil.isNotBlank(repaymentEntity.getCodIdentifyCode())) {
                    // 证件类型(代收人)
                    actualFreightEntity.setCodIdentifyType(repaymentEntity.getCodIdentifyType());
                    // 证件号码（代收）
                    actualFreightEntity.setCodIdentifyCode(repaymentEntity.getCodIdentifyCode());
                } else {
                    // 证件类型(代收人)
                    actualFreightEntity.setCodIdentifyType("");
                    // 证件号码（代收）
                    actualFreightEntity.setCodIdentifyCode("");
                }
                // 更新actualFreight表
                actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
                // 无需结清 将付款信息置0
                repaymentEntity.setActualFreight(BigDecimal.ZERO);
                // 无需结清 将付款信息置0
                repaymentEntity.setCodAmount(BigDecimal.ZERO);
                // 设置财务单据无需生成
                repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOTREQUIRE);
            } else {
                // 更新ActualFreight表中的提货人、身份证
                ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
                // 运单号
                actualFreightEntity.setWaybillNo(repaymentEntity.getWaybillNo());
                // 收货人
                actualFreightEntity.setDeliverymanName(repaymentEntity.getDeliverymanName());
                // 证件类型
                actualFreightEntity.setIdentifyType(repaymentEntity.getIdentifyType());
                // 证件号
                actualFreightEntity.setIdentifyCode(repaymentEntity.getIdentifyCode());
                if (StringUtil.isNotBlank(repaymentEntity.getCodIdentifyCode())) {
                    // 证件类型(代收人)
                    actualFreightEntity.setCodIdentifyType(repaymentEntity.getCodIdentifyType());
                    // 证件号码（代收人）
                    actualFreightEntity.setCodIdentifyCode(repaymentEntity.getCodIdentifyCode());
                } else {
                    // 证件类型(代收人)
                    actualFreightEntity.setCodIdentifyType("");
                    // 证件号码（代收）
                    actualFreightEntity.setCodIdentifyCode("");
                }
                // 更新actualFreight表
                actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
                // 判断是否是子母件
                if (twoInOneWaybillDto != null
                        && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 是子母件
                    if (repaymentEntity.getWaybillNo().equals(twoInOneWaybillDto.getMainWaybillNo())) { // 为子母件的母件
                        // 设置财务单据未生成
                        repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOGENERATE);
                    } else { // 为子母件的子件
                        // 设置财务单据无需生成
                        repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOTREQUIRE);
                    }
                } else { // 不是子母件
                    // 设置财务单据未生成
                    repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOGENERATE);
                }
            }
            // 生成付款信息
            repaymentDao.addPaymentInfo(repaymentEntity);
            // 生成到达联信息
            ArriveSheetEntity entity = new ArriveSheetEntity();
            // 运单号
            entity.setWaybillNo(repaymentEntity.getWaybillNo());
            // 收货人
            entity.setDeliverymanName(repaymentEntity.getDeliverymanName());
            // 证件类型
            entity.setIdentifyType(repaymentEntity.getIdentifyType());
            // 证件号
            entity.setIdentifyCode(repaymentEntity.getIdentifyCode());
            // 调用到达联接口
            checkGenerateArriveSheet(entity, currentInfo);
            // 更新到达联,通过运单号
            arriveSheetManngerService.updateArriveSheetEntityByWaybillNo(repaymentEntity.getWaybillNo(),
                    repaymentEntity.getDeliverymanName(), repaymentEntity.getIdentifyType(),
                    repaymentEntity.getIdentifyCode());
        } else {
            // 结算传入
            PaymentSettlementDto dto = new PaymentSettlementDto();
            // 如果付款方式为临欠或月结时 调用结算接口-到付运费结转临欠月结
            if ((SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT).equals(repaymentEntity
                    .getPaymentType())
                    || (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT)
                            .equals(repaymentEntity.getPaymentType())) {
                // 运单号
                dto.setWaybillNo(repaymentEntity.getWaybillNo());
                // 付款类型
                dto.setPaymentType(repaymentEntity.getPaymentType());
                // 部门cide
                dto.setDestOrgCode(currentInfo.getCurrentDeptCode());
                // 部门名称
                dto.setDestOrgName(currentInfo.getCurrentDeptName());
                // 客户code
                dto.setCustomerCode(repaymentEntity.getConsigneeCode());
                // 客户名称
                dto.setCustomerName(repaymentEntity.getConsigneeName());
                // 时间
                dto.setBusinessDate(new Date());
                // 实收代收货款费用
                dto.setCodFee(repaymentEntity.getCodAmount());
                // 实收到付运费
                dto.setToPayFee(repaymentEntity.getActualFreight());

                // 判断是否是子母件
                if (twoInOneWaybillDto != null
                        && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 是子母件
                    if (repaymentEntity.getWaybillNo().equals(twoInOneWaybillDto.getMainWaybillNo())) { // 为子母件的母件
                        //------CUBC灰度结清服务开始--------add by 353654
                                                String vestSystemCode = null;
                                    try {
                                        ArrayList<String> arrayList = new ArrayList<String>();
                                arrayList.add(repaymentEntity.getWaybillNo());
                                CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
                                                SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".settleUpRepayment",
                                                SettlementConstants.TYPE_FOSS);
                                CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
                                        List<VestBatchResult> list = response.getVestBatchResult();
                                        vestSystemCode = list.get(0).getVestSystemCode();               
                                                } catch (Exception e) {
                                                        LOGGER.info("灰度分流失败,"+"运单号："+repaymentEntity.getWaybillNo());
                                                        throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
                                                }
                                                if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
                                                        LOGGER.info("调用结算接口开始"+"运单号："+repaymentEntity.getWaybillNo());//记录日志
                                                        paymentSettlementService.confirmToBillReceivable(dto, currentInfo);
                                                        LOGGER.info("调用结算接口结束");//记录日志                  
                                                }
                                                if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
                                                        FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
                                                        fossDto.setDto(dto);
                                                        fossDto.setCurrentInfo(currentInfo);
                                                        CUBCResponseDto resultDto1 = cubcSettlementService.settleReqToCUBU(fossDto);
                                                        if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
                                                                if(StringUtils.isNotBlank(resultDto1.getMessage())){
                                                                        LOGGER.info("调用CUBC结清接口异常信息如下:"+resultDto1.getMessage());
                                                                        throw new CubcSettleException(resultDto1.getMessage());
                                                                }
                                                        }
                                                }
                                                //------CUBC灰度结清服务结束--------add by 353654
                    }
                } else { // 不是子母件
                        //------CUBC灰度结清服务开始--------add by 353654
                                        String vestSystemCode = null;
                            try {
                                ArrayList<String> arrayList = new ArrayList<String>();
                        arrayList.add(dto.getWaybillNo());
                        CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
                                        SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".settleUpRepayment",
                                        SettlementConstants.TYPE_FOSS);
                        CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
                                List<VestBatchResult> list = response.getVestBatchResult();
                                vestSystemCode = list.get(0).getVestSystemCode();               
                                        } catch (Exception e) {
                                                LOGGER.info("灰度分流失败,"+"运单号："+dto.getWaybillNo());
                                                throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
                                        }
                                        if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
                                                LOGGER.info("调用结算接口开始"+"运单号："+repaymentEntity.getWaybillNo());//记录日志
                                                paymentSettlementService.confirmToBillReceivable(dto, currentInfo);
                                                LOGGER.info("调用结算接口结束");//记录日志
                                                // 更新ActualFreight表中的结清状态为已结清
                                updateActualFreightEntity(repaymentEntity);
                                        }
                                        if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
                                                FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
                                                fossDto.setDto(dto);
                                                fossDto.setCurrentInfo(currentInfo);
                                                CUBCResponseDto resultDto1 = cubcSettlementService.settleReqToCUBU(fossDto);
                                                if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
                                                        if(StringUtils.isNotBlank(resultDto1.getMessage())){
                                                                LOGGER.info("调用CUBC结清接口异常信息如下:"+resultDto1.getMessage());
                                                                throw new CubcSettleException(resultDto1.getMessage());
                                                        }
                                                }
                                                if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.YES) && StringUtils.equals(resultDto1.getSettleStatus(), FossConstants.YES)){
                                                        // 更新ActualFreight表中的结清状态为已结清
                                        updateActualFreightEntity(repaymentEntity);
                                                }
                                        }
                                        //------CUBC灰度结清服务结束--------add by 353654
                }
                // 付款方式为其他方式时,调用结算接口-实收货款
            } else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(repaymentEntity
                    .getPaymentType())
                    || SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE.equals(repaymentEntity
                            .getPaymentType())
                    || SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
                            .equals(repaymentEntity.getPaymentType())
                    || SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(repaymentEntity
                            .getPaymentType())) {
                // 付款方式是‘银行卡’时，款项确认编号必须输入数字。
                if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(repaymentEntity
                        .getPaymentType())
                        && (StringUtil.isBlank(repaymentEntity.getClaimNo()) || !repaymentEntity.getClaimNo()
                                .matches("[0-9]+"))) {
                    throw new RepaymentException("付款方式为银行卡时，款项确认编号必须输入数字。");
                }
                // 款项认领编号
                dto.setPaymentNo(repaymentEntity.getClaimNo());
                // 运单号
                dto.setWaybillNo(repaymentEntity.getWaybillNo());
                // 付款类型
                dto.setPaymentType(repaymentEntity.getPaymentType());
                // 部门cide
                dto.setDestOrgCode(currentInfo.getCurrentDeptCode());
                // 部门名称
                dto.setDestOrgName(currentInfo.getCurrentDeptName());
                // 客户code
                dto.setCustomerCode(repaymentEntity.getConsigneeCode());
                // 客户名称
                dto.setCustomerName(repaymentEntity.getConsigneeName());
                // 时间
                dto.setBusinessDate(new Date());
                // 付款编号
                dto.setSourceBillNo(dateStr.toString());
                // 实收代收货款费用
                dto.setCodFee(repaymentEntity.getCodAmount());
                // 实收到付运费
                dto.setToPayFee(repaymentEntity.getActualFreight());
                // 结清类型
                dto.setRepaymentType(repaymentEntity.getRepaymentType());

                // 当实付运费和代收货款同时为0时 更改为已结清
                if (BigDecimalOperationUtil.compare(receiveableAmount, BigDecimal.ZERO)
                        && BigDecimalOperationUtil.compare(receiveablePayAmout, BigDecimal.ZERO)
                        && BigDecimalOperationUtil.compare(
                                repaymentEntity.getStorageFee() == null ? BigDecimal.ZERO : repaymentEntity
                                        .getStorageFee(), BigDecimal.ZERO)) {
                    // 更新ActualFreight表中的结清状态为已结清
                    updateActualFreightEntity(repaymentEntity);
                } else {
                    // 判断是否是子母件
                    if (twoInOneWaybillDto != null
                            && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 是子母件
                        validaEntity(repaymentEntity, currentInfo,
                                                                twoInOneWaybillDto, dto);
                    } else { // 不是子母件
                        //------CUBC灰度结清服务开始--------add by 353654
                                String message = null;
                                String vestSystemCode = null;
                                try {
                                        ArrayList<String> arrayList = new ArrayList<String>();
                                        arrayList.add(repaymentEntity.getWaybillNo());
                                        CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
                                                        SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".settleUpRepayment",
                                                        SettlementConstants.TYPE_FOSS);
                                        CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
                                        List<VestBatchResult> list = response.getVestBatchResult();
                                        vestSystemCode = list.get(0).getVestSystemCode();       
                                } catch (Exception e) {
                                        LOGGER.info("灰度分流失败,"+"运单号："+repaymentEntity.getWaybillNo());
                                                throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
                                }
                                if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
                                        LOGGER.info("调用结算接口开始" + "运单号：" + repaymentEntity.getWaybillNo());// 记录日志
                            message = paymentSettlementService.confirmToPayment(dto, currentInfo);
                            LOGGER.info("调用结算接口结束");// 记录日志                     
                                }
                                if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
                                        FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
                                                fossDto.setDto(dto);
                                                fossDto.setCurrentInfo(currentInfo);
                                                CUBCResponseDto resultDto1 = cubcSettlementService.settleReqToCUBU(fossDto);
                                                if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
                                                        if(StringUtils.isNotBlank(resultDto1.getMessage())){
                                                                LOGGER.info("调用CUBC结清接口异常信息如下:"+resultDto1.getMessage());
                                                                throw new CubcSettleException(resultDto1.getMessage());
                                                        }
                                                }
                                                if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.YES) && StringUtils.equals(resultDto1.getSettleStatus(), FossConstants.YES)){
                                                        message = SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE;
                                                }
                                }
                                //------CUBC灰度结清服务结束--------add by 353654
                        if (SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE.equals(message)
                                && BigDecimalOperationUtil.compare(
                                        repaymentEntity.getStorageFee() == null ? BigDecimal.ZERO
                                                : repaymentEntity.getStorageFee(), BigDecimal.ZERO)) {
                            // 更新ActualFreight表中的结清状态为已结清
                            updateActualFreightEntity(repaymentEntity);
                        }
                    }
                }
                /**
                 * 更新T+0数据 调用更新数据
                 * 
                 * @author yangqiang 309603
                 * @date 2016-2-23
                 */
                if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(repaymentEntity
                        .getPaymentType())
                        && (!StringUtil.isBlank(repaymentEntity.getClaimNo()) && repaymentEntity.getClaimNo()
                                .matches("[0-9]+"))) {
                    // 获取流水号内总金额
                    PosCardManageDto posCardManageDto = new PosCardManageDto();
                    posCardManageDto.setTradeSerialNo(repaymentEntity.getClaimNo());
                    // 查询T+0报表数据
                    //根据交流水号和部门编码查询POS刷卡数据   灰度  353654---start
                    String vestSystemCode = null;
                    PosCardEntity posCardEntity = null;
                    try {
                        List<String> wayBillNoList = new ArrayList<String>();
                        wayBillNoList.add(dto.getWaybillNo());
                        CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(wayBillNoList,
                                        SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".settleUpRepayment",
                                        SettlementConstants.TYPE_FOSS);
                        CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
                        List<VestBatchResult> list1 = response.getVestBatchResult();
                        vestSystemCode = list1.get(0).getVestSystemCode();      
                                } catch (Exception e) {
                                        LOGGER.info("灰度分流失败,"+"运单号："+dto.getWaybillNo());
                                                throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
                                }
                                if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
                                        posCardEntity = pdaPosManageService.queryPosCardData(posCardManageDto).getPosCardEntitys().get(0);
                                        // 可用余额
                        BigDecimal amount = posCardEntity.getUnUsedAmount();
                        // 更新POS刷卡信息
                        // 准备数据
                        BigDecimal actualFreight = repaymentEntity.getActualFreight();
                        BigDecimal codAmount = repaymentEntity.getCodAmount();
                        // 使用总金额
                        BigDecimal payAmount = actualFreight.add(codAmount);
                        // 更新人名称
                        posCardEntity.setModifyUser(currentInfo.getEmpName());
                        // 更新人编码
                        posCardEntity.setModifyUserCode(currentInfo.getEmpCode());
                        // 已使用金额
                        posCardEntity.setUsedAmount(posCardEntity.getUsedAmount().add(payAmount));
                        // 未使用金额
                        posCardEntity.setUnUsedAmount(amount.subtract(payAmount));
                        // 更新T+0报表
                                pdaPosManageService.updatePosCardMessageAmount(posCardEntity);
                                //根据交流水号去更新POS刷卡已使用流水号金额，未使用金额   灰度  353654---end
                        // 更新POS刷卡明细
                        // 准备数据
                        PosCardDetailEntity posCardDetailEntity = new PosCardDetailEntity();
                        // 交易流水号
                        posCardDetailEntity.setTradeSerialNo(posCardEntity.getTradeSerialNo());
                        // 运单
                        posCardDetailEntity.setInvoiceType("W2");
                        // 运单号
                        posCardDetailEntity.setInvoiceNo(repaymentEntity.getWaybillNo());
                        // 发生金额
                        posCardDetailEntity.setAmount(waybilldto.getToPayAmount());
                        // 已占用金额
                        posCardDetailEntity.setOccupateAmount(payAmount);
                        BigDecimal unVerifyAmount = (receiveableAmount.add(receiveablePayAmout))
                                .subtract(payAmount);
                        // 未核销金额
                        posCardDetailEntity.setUnVerifyAmount(unVerifyAmount);
                        // 创建人名称
                        posCardDetailEntity.setModifyUser(currentInfo.getEmpName());
                        // 创建人编码
                        posCardDetailEntity.setModifyUserCode(currentInfo.getEmpCode());
                                        pdaPosManageService.insertOrUpdateDetail(posCardDetailEntity);//调用接口插入数据
                                }
                                if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
                                        //TODO  待定
                                }
                                //根据交流水号和部门编码查询POS刷卡数据   灰度  353654---end
                    
                }
            }
            // 付款编号
            repaymentEntity.setRepaymentNo(dateStr.toString());
            // 付款时间
            repaymentEntity.setPaymentTime(new Date());
            // 操作人
            repaymentEntity.setOperator(currentInfo.getEmpName());
            // 操作人编码
            repaymentEntity.setOperatorCode(currentInfo.getEmpCode());
            // 操作部门
            repaymentEntity.setOperateOrgName(currentInfo.getCurrentDeptName());
            // 操作部门编码
            repaymentEntity.setOperateOrgCode(currentInfo.getCurrentDeptCode());
            // 当实付运费和代收货款同时为0时 更改为无需生成
            if (BigDecimalOperationUtil.compare(receiveableAmount, BigDecimal.ZERO)
                    && BigDecimalOperationUtil.compare(receiveablePayAmout, BigDecimal.ZERO)) {
                // 无需结清 将付款信息置0
                repaymentEntity.setActualFreight(BigDecimal.ZERO);
                // 无需结清 将付款信息置0
                repaymentEntity.setCodAmount(BigDecimal.ZERO);
                // 财务单据无需生成
                repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOTREQUIRE);
            } else {
                // 判断是否是子母件
                if (twoInOneWaybillDto != null
                        && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 是子母件
                    if (repaymentEntity.getWaybillNo().equals(twoInOneWaybillDto.getMainWaybillNo())) { // 为子母件的母件
                        // 财务单据已生成
                        repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_GENERATED);
                    } else { // 为子母件的子件
                        // 设置财务单据无需生成
                        repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOTREQUIRE);
                    }
                } else { // 不是子母件
                    // 财务单据已生成
                    repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_GENERATED);
                }
            }
            // 币种
            repaymentEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
            // job id
            repaymentEntity.setJobId(UUIDUtils.getUUID());
            // 生成付款信息
            repaymentDao.addPaymentInfo(repaymentEntity);
            // 生成到达联信息
            ArriveSheetEntity entity = new ArriveSheetEntity();
            // 运单号
            entity.setWaybillNo(repaymentEntity.getWaybillNo());
            // 收货人
            entity.setDeliverymanName(repaymentEntity.getDeliverymanName());
            // 证件类型
            entity.setIdentifyType(repaymentEntity.getIdentifyType());
            // 证件号
            entity.setIdentifyCode(repaymentEntity.getIdentifyCode());
            // 调用到达联接口
            checkGenerateArriveSheet(entity, currentInfo);
            // 更新到达联,通过运单号
            arriveSheetManngerService.updateArriveSheetEntityByWaybillNo(repaymentEntity.getWaybillNo(),
                    repaymentEntity.getDeliverymanName(), repaymentEntity.getIdentifyType(),
                    repaymentEntity.getIdentifyCode());
        }

        // 判断是否是子母件
        if (twoInOneWaybillDto != null && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 是子母件
            if (repaymentEntity.getWaybillNo().equals(twoInOneWaybillDto.getMainWaybillNo())) { // 为子母件的母件
                // 如果小票号不为空，则调用结算接口添加小票号信息
                if (StringUtils.isNotBlank(repaymentEntity.getOtherRevenueNo())) {
                    this.repaymentService.addOtherRevenueInfo(repaymentEntity, waybilldto, currentInfo);
                }
            }
        } else { // 不是子母件
            // 如果小票号不为空，则调用结算接口添加小票号信息
            if (StringUtils.isNotBlank(repaymentEntity.getOtherRevenueNo())) {
                this.repaymentService.addOtherRevenueInfo(repaymentEntity, waybilldto, currentInfo);
            }
        }
    }

        private void validaEntity(RepaymentEntity repaymentEntity,
                        CurrentInfo currentInfo, TwoInOneWaybillDto twoInOneWaybillDto,
                        PaymentSettlementDto dto) {
                if (repaymentEntity.getWaybillNo().equals(twoInOneWaybillDto.getMainWaybillNo())) { // 为子母件的母件
                        //------CUBC灰度结清服务开始--------add by 353654
                        String message = null;
                        String vestSystemCode = null;
                        try {
                                ArrayList<String> arrayList = new ArrayList<String>();
                                arrayList.add(repaymentEntity.getWaybillNo());
                                CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
                                                SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".validaRepayment",
                                                SettlementConstants.TYPE_FOSS);
                                CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
                                List<VestBatchResult> list = response.getVestBatchResult();
                                vestSystemCode = list.get(0).getVestSystemCode();       
                        } catch (Exception e) {
                                LOGGER.info("灰度分流失败,"+"运单号："+repaymentEntity.getWaybillNo());
                                throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
                        }
                        if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
                                LOGGER.info("调用结算接口开始" + "运单号：" + repaymentEntity.getWaybillNo());// 记录日志
                            message = paymentSettlementService.confirmToPayment(dto, currentInfo);
                            LOGGER.info("调用结算接口结束");// 记录日志
                        }
                        if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
                                FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
                                fossDto.setDto(dto);
                                fossDto.setCurrentInfo(currentInfo);
                                CUBCResponseDto resultDto1 = cubcSettlementService.settleReqToCUBU(fossDto);
                                if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
                                        if(StringUtils.isNotBlank(resultDto1.getMessage())){
                                                LOGGER.info("调用CUBC结清接口异常信息如下:"+resultDto1.getMessage());
                                                throw new CubcSettleException(resultDto1.getMessage());
                                        }
                                }
                                if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.YES) && StringUtils.equals(resultDto1.getSettleStatus(), FossConstants.YES)){
                                        message = SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE;
                                }
                        }
                        //------CUBC灰度结清服务结束--------add by 353654
                    if (SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE
                            .equals(message)
                            && BigDecimalOperationUtil.compare(
                                    repaymentEntity.getStorageFee() == null ? BigDecimal.ZERO
                                            : repaymentEntity.getStorageFee(), BigDecimal.ZERO)) {
                        // 更新ActualFreight表中的结清状态为已结清
                        updateActualFreightEntity(repaymentEntity);
                    }
                } else {
                    // 更新ActualFreight表中的结清状态为已结清
                    updateActualFreightEntity(repaymentEntity);
                }
        }

    /**
     * 准备付款RepaymentEntity
     * 
     * @author 309603 yangqiang
     * @date 2016-03-04
     * @param dto
     * @param repaymentDto 运单详情
     * @param receiveableAmount 实付代收货款
     * @param receiveablePayAmoout 实付运费
     * @return
     */
    private RepaymentEntity increaseRepaymentEntity(CommonQueryParamDto dto, RepaymentDto repaymentDto,
            BigDecimal receiveableAmount, BigDecimal receiveablePayAmoout) {
        RepaymentEntity repaymentEntity = new RepaymentEntity();
        // 实付运费
        repaymentEntity.setActualFreight(receiveablePayAmoout);
        // 交易流水号(款项认领编号)
        //TODO
        
        repaymentEntity.setClaimNo(dto.getTradeSerialNo());
        LOGGER.info("款项认领编号："+repaymentEntity.getClaimNo());
        // 实付代收货款
        repaymentEntity.setCodAmount(receiveableAmount);
        // 代收人证件号
        repaymentEntity.setCodIdentifyCode("");
        // 代收人证件类型
        repaymentEntity.setCodIdentifyType("");
        repaymentEntity.setConsigneeCode(""); // 客户code
        repaymentEntity.setConsigneeName(""); // 客户名称
        repaymentEntity.setCodIdentityIsScan(""); // 是否扫描
        repaymentEntity.setDeliverymanName(repaymentDto.getWaybillDto().getReceiveCustomerName());// 提货人
        repaymentEntity.setIdentifyCode(""); // 提货人证件号
        repaymentEntity.setIdentifyType(""); // 提货人证件类型
        repaymentEntity.setIdentityIsScan(""); // 提货人证件是否扫描
        repaymentEntity.setPassWord(""); // 密码
        repaymentEntity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE); // 付款类型
        repaymentEntity.setRevenueClaimNo(""); // 小票确认编号
        repaymentEntity.setRevenuePaymentType("CH"); // 语言类型
        repaymentEntity.setStorageFee(repaymentDto.getWaybillDto().getStorageCharge());// 保管费
        repaymentEntity.setWaybillNo(repaymentDto.getWaybillDto().getWaybillNo()); // 运单号
        repaymentEntity.setRepaymentType(dto.getRepaymentType());
        return repaymentEntity;
    }

    /**
     * 更新实际承运表的结清状态
     * 
     * @author 309603 yangqiang
     * @date 2016-3-3
     */
    private void updateActualFreightEntity(RepaymentEntity repaymentEntity) {
        // 更新ActualFreight表中的结清状态为已结清
        ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
        // 运单号
        actualFreightEntity.setWaybillNo(repaymentEntity.getWaybillNo());
        // 结清状态-已结清
        actualFreightEntity.setSettleStatus(FossConstants.YES);
        // 结款日期
        actualFreightEntity.setSettleTime(new Date());
        // 收货人
        actualFreightEntity.setDeliverymanName(repaymentEntity.getDeliverymanName());
        // 证件类型
        actualFreightEntity.setIdentifyType(repaymentEntity.getIdentifyType());
        // 证件号
        actualFreightEntity.setIdentifyCode(repaymentEntity.getIdentifyCode());
        if (StringUtil.isNotBlank(repaymentEntity.getCodIdentifyCode())) {
            // 证件类型(代收人)
            actualFreightEntity.setCodIdentifyType(repaymentEntity.getCodIdentifyType());
            // 证件号码（代收）
            actualFreightEntity.setCodIdentifyCode(repaymentEntity.getCodIdentifyCode());
        } else {
            // 证件类型(代收人)
            actualFreightEntity.setCodIdentifyType("");
            // 证件号码（代收）
            actualFreightEntity.setCodIdentifyCode("");
        }
        // 更新actualFreight表
        actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
    }

    /**
     * 结清货款前验证
     * 
     * @param repaymentEntity 付款 对象
     * @param currentInfo 当前信息对象
     * @param waybillEntity 运单
     * @author yangqiang 309603
     * @date 2016-3-3 下午2:33:29
     */
    private void verification(RepaymentEntity repaymentEntity, CurrentInfo currentInfo,
            WaybillEntity waybillEntity) {

        // 校验未受理的更改单
        if (waybillRfcService.isExsitsWayBillRfc(repaymentEntity.getWaybillNo())) {
            // 抛出业务异常
            throw new RepaymentException("未受理的更改单");
        }

        // 判断是否 已结清
        if (repaymentService.isPayment(repaymentEntity.getWaybillNo())) {
            // 抛出业务异常
            throw new RepaymentException("改运单已结清");
        }

        // 校验更改单或者更改单申请未处理
        if (waybillEntity != null
                && WaybillConstants.directDetermineIsExpressByProductCode(waybillEntity.getProductCode())) {
            List<String> waybillRfcList = new ArrayList<String>();
            waybillRfcList.add(repaymentEntity.getWaybillNo());
            List<String> notWaybillRfc = waybillRfcService.queryUnActiveRfcWaybillNo(waybillRfcList); // 调用接口
            if (CollectionUtils.isNotEmpty(notWaybillRfc) && notWaybillRfc.size() > 0) {
                // 抛出业务异常
                throw new RepaymentException("有更改单申请未处理，请受理后再次操作！");
            }
        }
        /*
         * // 判断如果当前运单是整车 if (waybillEntity != null &&
         * FossConstants.YES.equals(waybillEntity.getIsWholeVehicle())) { boolean arrival =
         * FossConstants.YES.equals(waybillEntity.getIsPassOwnDepartment()); if (!arrival) {
         * //如果当前部门==最终配载部门 if (currentInfo.getCurrentDeptCode().equals
         * (waybillEntity.getLastLoadOrgCode())) { arrival=true; } } //update by foss 231434
         * 2015-6-2 //对整车(三级)产品，若开单时间在2013-08-01之前(不包含2013-08-01)，不做校验 Date date =
         * DateUtils.convert(dateStr, format); if (date.before(waybillEntity.getBillTime())) { //
         * 校验运单是否配载是否到达 String message =
         * arrivalService.validateWaybillNo(repaymentEntity.getWaybillNo(), arrival); if(message !=
         * null){ throw new RepaymentException(message); } } } else { // 判断当前部门是否是最终到达部门
         * if(!currentInfo.getCurrentDeptCode().equals (waybillEntity.getLastLoadOrgCode())){ throw
         * new RepaymentException("当前部门与最终到达部门不一致，不能结清货款！"); } }
         */

        // 判断是否网上支付
        if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(waybillEntity
                .getPaidMethod())) {
            // ISSUE-4379调用结算接口判断 如果网上支付未完成时 给出相应提示
            List<String> waybillNos = new ArrayList<String>();
            waybillNos.add(repaymentEntity.getWaybillNo());
            List<String> notPayWaybillNo = null;
          //开单网上支付但是尚未支付的单据，灰度改造   353654 ---------------------------start 
            String vestSystemCode = null;
            try {
                CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(waybillNos,
                                SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".verification",
                                SettlementConstants.TYPE_FOSS);
                CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
                List<VestBatchResult> list = response.getVestBatchResult();
                vestSystemCode = list.get(0).getVestSystemCode();               
                        } catch (Exception e) {
                                LOGGER.info("灰度分流失败,"+"错误方法位置："+SERVICE_CODE+".verification");
                                throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
                        }
                        if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
                    notPayWaybillNo = takingService.unpaidOnlinePay(waybillNos);
                    if (CollectionUtils.isNotEmpty(notPayWaybillNo)) {
                        throw new RepaymentException("该运单为网上支付运单，网上支付未完成，无法进行结清货款操作");
                    }
                        }
                        if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
                                //调用CUBC查询物流交易单  应收信息校验  353654
                                        FossSearchTradeRequestDO requestDto1 = new FossSearchTradeRequestDO();
                                        requestDto1.setWaybillNos(waybillNos);
                                        FossSearchTradeResponseDO responseDto1 = null;
                                        try {
                                                responseDto1 = (FossSearchTradeResponseDO)HttpClientUtils.postMethod(requestDto1, new FossSearchTradeResponseDO(), queryTradeListUrl);
                                        } catch (Exception e) {
                                                LOGGER.error("调用CUBC接口出现异常,异常信息为："+e);
                                                throw new SettlementException("服务器正忙,CUBC查询物流交易单异常,请稍候重试");
                                        }
                                        if(responseDto1 != null){
                                                if(StringUtils.isNotBlank(responseDto1.getMsg()) && StringUtils.equals("N", responseDto1.getActive())){
                                                        LOGGER.error("调用CUBC查询物流交易单接口失败，异常信息：" + responseDto1.getMsg());
                                                        throw new SettlementException(responseDto1.getMsg());
                                                }
                                                Map<String, List<TradeDO>> dataMap = responseDto1.getDataMap();
                                                List<TradeDO> tradeslist = dataMap.get(repaymentEntity.getWaybillNo());
                                                if(!CollectionUtils.isEmpty(tradeslist)){
                                                        for (TradeDO tradeDO : tradeslist) {
                                                                if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE.
                                                                           equals(tradeDO.getOrderSubType())&&
                                                                           SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.
                                                                           equals(tradeDO.getOrderSubType())
                                                                           &&BigDecimal.ZERO.compareTo(tradeDO.getUnverifyAmount())<0){
                                                                   //添加数据
                                                                   notPayWaybillNo.add(repaymentEntity.getWaybillNo());
                                                                   if(CollectionUtils.isNotEmpty(notPayWaybillNo)){
                                                                           throw new RepaymentException("该运单为网上支付运单，网上支付未完成，无法进行结清货款操作");
                                                               }
                                                         }
                                                        }
                                                }
                                        }
                        }
                        //开单网上支付但是尚未支付的单据，灰度改造   353654 ---------------------------end
        }
    }

    /**
     * 判断 是 司机还是营业员登陆
     * 
     * @author 309603 yangqiang
     * @date 2016-3-3
     * @param vehicleNo 车牌号 waybill 运单号
     * @return
     */
    @Override
    public CommonQueryParamDto isDriver(CommonQueryParamDto dto) {
        if (dto == null) {
            throw new PdaProcessException("参数不正确，请重试 ");
        }
        // 判断是否为vts整车，整车只有非司机登录
        if (isVehicle(dto.getWaybillNo().get(0))) {
            return checkWaybillForAss(dto.getOrgCode(), dto.getWaybillNo().get(0));
        } else {
            // 获取是否为司机
            String isDriver = dto.getIsDriver() == null ? "" : dto.getIsDriver();
            if (isDriver.isEmpty()) {
                throw new PdaProcessException("登陆人类型有误，请重试");
            } else if ("Y".equals(isDriver)) {
                return checkWaybill(dto.getEmpCode(), dto.getWaybillNo().get(0));
            } else {
                return checkWaybillForAss(dto.getOrgCode(), dto.getWaybillNo().get(0));
            }
        }
    }

    /**
     * 校验运单是否已核销
     * 
     * @author 309603 yangqiang
     * @date 2016-3-31
     * @param waybill 运单号
     */
    @Override
    public String checkVerification(String waybill) {
        BillReceivableConditionDto dto = new BillReceivableConditionDto();
        // 设置运单号
        dto.setWaybillNo(waybill);
        dto.setActive(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES);
        dto.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
        dto.setBillTypes(new String[] {
                SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
                SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE,
                SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE });
        // 应收单信息
        String vestSystemCode = null;
        List<BillReceivableEntity> list = null; 
        try {
                ArrayList<String> arrayList = new ArrayList<String>();
                arrayList.add(waybill);
                CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
                                SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".checkVerification",
                                SettlementConstants.TYPE_FOSS);
                CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
                List<VestBatchResult> list1 = response.getVestBatchResult();
                vestSystemCode = list1.get(0).getVestSystemCode();
                } catch (Exception e) {
                        LOGGER.info("灰度分流失败,"+"运单号："+waybill);
                        throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
                }
        String result = null;
        BigDecimal unverifyAmount = BigDecimal.ZERO;
        if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
                        list = billReceivableEntityDao.queryBillReceivableByCondition(dto);
                        if (list == null || list.isEmpty()) {
                    throw new PdaProcessException("该运单的应收单不存在，请检查后输入。");
                }
                // 获取应收单实体
                for (BillReceivableEntity billReceivableEntity : list) {
                    if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT.equals(billReceivableEntity.getPaymentType())) {
                        unverifyAmount = unverifyAmount.add(billReceivableEntity.getUnverifyAmount() == null ? BigDecimal.ZERO
                                        : billReceivableEntity.getUnverifyAmount());
                    }
                }
                result = unverifyAmount.compareTo(BigDecimal.ZERO) == 1 ? "N" : "Y";
                }
                if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
                        List<String> waybillNos = new ArrayList<String>();
                        waybillNos.add(waybill);
                        FossSearchTradeRequestDO requestDto1 = new FossSearchTradeRequestDO();
                        requestDto1.setWaybillNos(waybillNos);
                        FossSearchTradeResponseDO responseDto1 = null;
                        try {
                                responseDto1 = (FossSearchTradeResponseDO)HttpClientUtils.postMethod(requestDto1, new FossSearchTradeResponseDO(), queryTradeListUrl);
                        } catch (Exception e) {
                                LOGGER.error("调用CUBC接口出现异常,异常信息为："+e);
                                throw new SettlementException("服务器正忙,CUBC查询物流交易单异常,请稍候重试");
                        }
                        if(responseDto1 != null){
                                if(StringUtils.isNotBlank(responseDto1.getMsg()) && StringUtils.equals("N", responseDto1.getActive())){
                                        LOGGER.error("调用CUBC查询物流交易单接口失败，异常信息：" + responseDto1.getMsg());
                                        throw new SettlementException(responseDto1.getMsg());
                                }
                                Map<String, List<TradeDO>> dataMap = responseDto1.getDataMap();
                                List<TradeDO> tradeslist = dataMap.get(waybill);
                                if(!CollectionUtils.isEmpty(tradeslist)){
                                        for (TradeDO tradeDO : tradeslist) {
                                                if(SettlementDictionaryConstants.SETTLEMENT__BILL_PARENT_TYPE__YS.equals(tradeDO.getOrderType())){//应收
                                                        if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT.equals(tradeDO.getPaymentType())){
                                                                unverifyAmount = unverifyAmount.add(tradeDO.getUnverifyAmount()==null? BigDecimal.ZERO:tradeDO.getUnverifyAmount());
                                                                break;
                                                        }
                                                }
                                        }
                                }else{
                                        throw new PdaProcessException("该运单的物流交易单不存在，请检查后输入");
                                }
                                result = unverifyAmount.compareTo(BigDecimal.ZERO)==1? "N":"Y";
                        }
                }
        return result;

    }

    /**
     * 根据司机工号、运单号查询并判是否在派送任务中
     * 
     * @author 309603 yangqiang
     * @date 2016-3-3
     * @param vehicleNo 车牌号 waybill 运单号
     * @return
     */
    private CommonQueryParamDto checkWaybill(String driverCode, String waybill) {
        // 根据工号去查签到表获取车牌号
        if (driverCode == null || driverCode.isEmpty()) {
            throw new PdaProcessException("员工工号不正确，请确认。");
        }
        if (waybill == null || waybill.isEmpty()) {
            throw new PdaProcessException("运单号不正确，请确认。");
        }
        String vehicleNo = pdaSignEntityDao.queryVehicleNoByContidion(driverCode); // 车牌号
        if (vehicleNo == null || vehicleNo.isEmpty()) {
            throw new PdaProcessException("请先在ocb登陆。");
        }
        // 准备查询数据
        Map<String, String> map = new HashMap<String, String>();
        map.put("vehicleNo", vehicleNo);
        map.put("driverCode", driverCode);
        map.put("waybill_no", waybill);
        String bill = repaymentDao.checkWaybill(map);
        if (bill == null || bill.isEmpty()) {
            throw new PdaProcessException("该运单号不正确，或者该运单没有在OCB下拉中");
        }
        // 准备数据
        CommonQueryParamDto dto = new CommonQueryParamDto();
        addCommon(waybill, dto);
        return dto;
    }

    /**
     * 根据营业员工号、所在部门验证运单号是否可以结清
     * 
     * @author 309603 yangqiang
     * @date 2016-3-3
     * @param vehicleNo 车牌号 waybill 运单号
     * @return
     */
    private CommonQueryParamDto checkWaybillForAss(String orgCode, String waybill) {
        if (orgCode == null || orgCode.isEmpty()) {
            throw new PdaProcessException("员工所在部门不正确，请确认。");
        }
        if (waybill == null || waybill.isEmpty()) {
            throw new PdaProcessException("该运单输入有误。。");
        }
        // vts整车无配载、无库存
        /*
         * AirAgencyQueryDto airAgencyQueryDto = new AirAgencyQueryDto();
         * airAgencyQueryDto.setWaybillNo(waybill); airAgencyQueryDto.setProductCode("ALL");
         * RepaymentDto repaymentDto = repaymentService.queryPaymentByWaybillNo(waybill);
         * if(repaymentDto==null||repaymentDto.getWaybillDto()==null) { throw new
         * PdaProcessException("查询不到付款信息。。。"); }
         * airAgencyQueryDto.setIsExpress(repaymentDto.getWaybillDto().getIsExpress());
         * List<AirAgencyQueryDto> list =
         * this.queryAirAgencyQueryDtoList(airAgencyQueryDto,orgCode); if (null ==list ||
         * list.isEmpty()) { throw new PdaProcessException("该运单，请先入库。。。"); }
         */
        BillReceivableEntity entity = queryBillReceivable(waybill);
        if (!orgCode.equals(entity.getReceivableOrgCode())) { // 应收单应收部门
            throw new PdaProcessException("当前部门与最终到达部门不一致！");
        }
        // 准备返回数据
        CommonQueryParamDto comDto = new CommonQueryParamDto();
        addCommon(waybill, comDto);
        return comDto;
    }

    @Override
    public BillReceivableEntity queryBillReceivable(String waybill) {
        BillReceivableConditionDto dto = new BillReceivableConditionDto();
        // 设置运单号
        dto.setWaybillNo(waybill);
        dto.setActive(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES);
        dto.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
        dto.setBillTypes(new String[] {
                SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
                SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE,
                SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE });
        // 应收单信息
        String vestSystemCode = null;
        List<BillReceivableEntity> list = null; 
        try {
                ArrayList<String> arrayList = new ArrayList<String>();
                arrayList.add(waybill);
                CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
                                SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".queryBillReceivable",
                                SettlementConstants.TYPE_FOSS);
                CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
                List<VestBatchResult> list1 = response.getVestBatchResult();
                vestSystemCode = list1.get(0).getVestSystemCode();
                } catch (Exception e) {
                        LOGGER.info("灰度分流失败,"+"运单号："+waybill);
                        throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
                }
        BillReceivableEntity entity = null;
        if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
                list = billReceivableEntityDao.queryBillReceivableByCondition(dto);
                if (list == null || list.isEmpty()) {
                        throw new PdaProcessException("该运单的应收单不存在，请检查后输入。");
                }
                // 获取应收单实体
                for (BillReceivableEntity billReceivableEntity : list) {
                        if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT
                                        .equals(billReceivableEntity.getPaymentType())) {
                                entity = billReceivableEntity;
                                break;
                        }
                }
                if (entity == null) {
                        throw new PdaProcessException("该单据无到达应收！！");
                }
                }
                if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
                        List<String> waybillNos = new ArrayList<String>();
                        waybillNos.add(waybill);
                        FossSearchTradeRequestDO requestDto1 = new FossSearchTradeRequestDO();
                        requestDto1.setWaybillNos(waybillNos);
                        FossSearchTradeResponseDO responseDto1 = null;
                        try {
                                responseDto1 = (FossSearchTradeResponseDO)HttpClientUtils.postMethod(requestDto1, new FossSearchTradeResponseDO(), queryTradeListUrl);
                        } catch (Exception e) {
                                LOGGER.error("调用CUBC接口出现异常,异常信息为："+e);
                                throw new SettlementException("服务器正忙,CUBC查询物流交易单异常,请稍候重试");
                        }
                        if(responseDto1 != null){
                                if(StringUtils.isNotBlank(responseDto1.getMsg()) && StringUtils.equals("N", responseDto1.getActive())){
                                        LOGGER.error("调用CUBC查询物流交易单接口失败，异常信息：" + responseDto1.getMsg());
                                        throw new SettlementException(responseDto1.getMsg());
                                }
                                Map<String, List<TradeDO>> dataMap = responseDto1.getDataMap();
                                List<TradeDO> tradeslist = dataMap.get(waybill);
                                if(!CollectionUtils.isEmpty(tradeslist)){
                                        for (TradeDO tradeDO : tradeslist) {
                                                if(SettlementDictionaryConstants.SETTLEMENT__BILL_PARENT_TYPE__YS.equals(tradeDO.getOrderType())){//应收
                                                        if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT.equals(tradeDO.getPaymentType())){
                                                                //TODO 将CUBC物流交易单的数据封装到FOSS应收单实体
                                                                entity = new BillReceivableEntity();
                                                                entity.setReceivableOrgCode(tradeDO.getActionOrgCode());
                                                                entity.setReceivableOrgName(tradeDO.getActionOrgName());
                                                                break;
                                                        }
                                                }
                                        }
                                }else{
                                        throw new PdaProcessException("该运单的物流交易单不存在，请检查后输入");
                                }       
                        }
                        if (entity==null) {
                        throw new PdaProcessException("CUBC,该单据无到达应收！！");
                }
                }
        return entity;
    }

    /**
     * 填充返回数据
     * 
     * @param waybill
     * @param dto
     */
    private void addCommon(String waybill, CommonQueryParamDto dto) {
        RepaymentDto repaymentDto = null;
        // 306579--guoxinru 调用私有方法，根据运单号判断是否为整车，调用查询service
        if (isVehicle(waybill)) {
            repaymentDto = repaymentService.vtsQueryPaymentByWaybillNo(waybill);
        } else {
            // 根据运单号查询数据
            repaymentDto = repaymentService.queryPaymentByWaybillNo(waybill);
        }
        if (repaymentDto == null) {
            throw new PdaProcessException("该运单号不存在，或者该运单有误");
        }
        /**
         * 2016/11/24 优化 运单号的到达部门(提货网点)在【行政组织业务属性-营业部信息】中的是否加盟网点 357637 yuanhuijun
         */
        checkCustomerPickupOrgCode(repaymentDto.getWaybillDto().getCustomerpickuporgcode());

        BigDecimal receiveableAmount = repaymentDto.getFinancialDto().getReceiveableAmount() == null ? BigDecimal.ZERO
                : repaymentDto.getFinancialDto().getReceiveableAmount();
        BigDecimal receiveablePayAmoout = repaymentDto.getFinancialDto().getReceiveablePayAmoout() == null ? BigDecimal.ZERO
                : repaymentDto.getFinancialDto().getReceiveablePayAmoout();
        BigDecimal receivedAmount = repaymentDto.getFinancialDto().getReceivedAmount() == null ? BigDecimal.ZERO
                : repaymentDto.getFinancialDto().getReceivedAmount();
        BigDecimal receivedPayAmount = repaymentDto.getFinancialDto().getReceivedPayAmount() == null ? BigDecimal.ZERO
                : repaymentDto.getFinancialDto().getReceivedPayAmount();
        if (!(receiveableAmount.add(receiveablePayAmoout).compareTo(BigDecimal.ZERO) == 1)) {
            throw new PdaProcessException("该运单已经结清，无需再结清。。。");
        }
        // 准备数据
        ArrayList<String> list = new ArrayList<String>();
        list.add(waybill);
        dto.setWaybillNo(list);
        // 收货人
        dto.setReceiveCustomerName(repaymentDto.getWaybillDto().getReceiveCustomerName());
        // 业务日期
        dto.setCreateTime(repaymentDto.getWaybillDto().getCreateTime());
        // 总运费
        // dto.setToPayAmount(repaymentDto.getWaybillDto().getToPayAmount());
        BigDecimal totalAmount = repaymentDao.getTotalAmount(waybill);
        if (totalAmount == null) {
            throw new PdaProcessException("该运单有误...");
        }
        dto.setToPayAmount(totalAmount);
        // 未核销
        if (repaymentDto.getFinancialDto() == null) {
            throw new PdaProcessException("该运单号的应收单不存在。");
        }
        dto.setUnverifyAmount(receiveableAmount.add(receiveablePayAmoout));
        dto.setUnPayAmount(receiveableAmount.add(receiveablePayAmoout).add(receivedAmount)
                .add(receivedPayAmount));
    }

    /**
     * 2016/11/24 优化 运单号的到达部门(提货网点)在【行政组织业务属性-营业部信息】中的是否加盟网点 357637 yuanhuijun
     */
    private void checkCustomerPickupOrgCode(String customerpickuporgcode) {
        if (StringUtils.isBlank(customerpickuporgcode)) {
            throw new SettlementException("运单的提货部门编码为空!!!");
        }
        // 查询到达部门,判断达到部门是否为合伙人部门
        SaleDepartmentEntity saleDept = saleDepartmentService
                .querySaleDepartmentInfoByCode(customerpickuporgcode);
        if (null == saleDept) {
            throw new SettlementException("运单对应的系统维护的提货部门信息为空!!!");
        }
        if (FossConstants.YES.equals(saleDept.getIsLeagueSaleDept())
                || isPTP(customerpickuporgcode, saleDept.getName())) {
            throw new SettlementException("运单的提货部门是加盟网点!!!");
        }

    }

    /**
     * 判断是否为合伙人(部门名称以H,J,D开头的) 357637 yuanhuijun
     * 
     * @param string
     */
    private boolean isPTP(String customerpickuporgcode, String name) {

        LOGGER.info("**********查询出的运单信息中的到达部门的名称:" + name + "**********");
        if (name.length() < FossConstants.MIN_INDEX) {
            throw new SettlementException("运单提货部门编码:" + customerpickuporgcode + "对应的系统维护的到提货门名称:" + name
                    + "异常!!!");
        }
        // 截取到达部门的前三位
        String startName = name.substring(FossConstants.START_INDEX, FossConstants.END_INDEX);
        // 判断是否为合伙人部门
        if (FossConstants.START_WHITH_H.equals(startName) || FossConstants.START_WHITH_J.equals(startName)
                || FossConstants.START_WHITH_D.equals(startName)) {
            return true;
        }
        return false;
    }

    public void setPdaSignEntityDao(IPdaSignEntityDao pdaSignEntityDao) {
        this.pdaSignEntityDao = pdaSignEntityDao;
    }

    /**
     * Sets the repayment dao.
     * 
     * @param repaymentDao the new repayment dao
     */
    public void setRepaymentDao(IRepaymentDao repaymentDao) {
        this.repaymentDao = repaymentDao;
    }

    /**
     * Sets the arrive sheet mannger service.
     * 
     * @param arriveSheetManngerService the new arrive sheet mannger service
     */
    public void setArriveSheetManngerService(IArriveSheetManngerService arriveSheetManngerService) {
        this.arriveSheetManngerService = arriveSheetManngerService;
    }

    /**
     * Sets the payment settlement service.
     * 
     * @param paymentSettlementService the new payment settlement service
     */
    public void setPaymentSettlementService(IPaymentSettlementService paymentSettlementService) {
        this.paymentSettlementService = paymentSettlementService;
    }

    /**
     * Sets the actual freight service.
     * 
     * @param actualFreightService the new actual freight service
     */
    public void setActualFreightService(IActualFreightService actualFreightService) {
        this.actualFreightService = actualFreightService;
    }

    /**
     * Sets the waybill rfc service.
     * 
     * @param waybillRfcService the new waybill rfc service
     */
    public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
        this.waybillRfcService = waybillRfcService;
    }

    /**
     * @author 309603 yangqiang
     * @date 2016-3-3
     * 
     *       原单结清货款
     * 
     * 
     */
    @Override
    public void addOriginalPaymentInfo(RepaymentEntity repaymentEntity, CurrentInfo currentInfo,
            String returnType, String waybillNo, TwoInOneWaybillDto twoInOneWaybillDto) {
        try {
            // 得到付款方式
            if (repaymentEntity != null) {
                // 查询原单运单信息
                WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(repaymentEntity
                        .getWaybillNo());
                if (waybill == null) {
                    // 原单运单为空
                    return;
                }
                // 1.判断是不是快递代理
                OuterBranchExpressEntity companyDto = ldpAgencyDeptService.queryLdpAgencyDeptByCode(
                        waybill.getCustomerPickupOrgCode(), FossConstants.ACTIVE);
                if (companyDto != null) {
                    // 快递代理
                    return;
                }
                // 根据通过返单号查询对应原单号
                List<WaybillExpressEntity> waybillLists = waybillExpressService
                        .queryWaybillListByWaybillNo(waybillNo);
                if (CollectionUtils.isNotEmpty(waybillLists)) {
                    int paymentCount = 0;
                    for (int i = 0, len = waybillLists.size(); i < len; i++) {
                        WaybillExpressEntity waybillExpress = waybillLists.get(i);
                        if (repaymentService.isPayment(waybillExpress.getOriginalWaybillNo())) {
                            paymentCount++;
                        }
                    }

                    // 原单号是否结清
                    if (waybillLists.size() == paymentCount) {
                        return;
                    }
                }

                // 判断是否子母件
                if (twoInOneWaybillDto != null
                        && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 是子母件
                    // 按票返
                    if (WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(returnType)) {
                        // 判断母件是否结清
                        if (!repaymentService.isPayment(twoInOneWaybillDto.getMainWaybillNo())) {
                            // 得到结算应收单信息
                            FinancialDto financialDto = repaymentService.queryFinanceSign(repaymentEntity
                                    .getWaybillNo());
                            // 应收代收款
                            BigDecimal receiveableAmount = financialDto.getReceiveableAmount() == null ? BigDecimal.ZERO
                                    : financialDto.getReceiveableAmount();
                            // 应收到付款
                            BigDecimal receiveablePayAmoout = financialDto.getReceiveablePayAmoout() == null ? BigDecimal.ZERO
                                    : financialDto.getReceiveablePayAmoout();
                            // 设置 实付运费
                            repaymentEntity.setActualFreight(receiveablePayAmoout);
                            // 设置 代收货款
                            repaymentEntity.setCodAmount(receiveableAmount);
                            // 设置运单号
                            repaymentEntity.setWaybillNo(twoInOneWaybillDto.getMainWaybillNo());
                            // 原单结清货款
                            settleUpOriginalRepayment(repaymentEntity, currentInfo, twoInOneWaybillDto);
                        }
                        // 判断子件集合是否为空
                        validaRepayment(repaymentEntity, currentInfo,
                                                                twoInOneWaybillDto);
                    } else {
                        if (CollectionUtils.isNotEmpty(waybillLists)) {
                            // 判断母件是否结清
                            if (!repaymentService.isPayment(twoInOneWaybillDto.getMainWaybillNo())) {
                                // 得到结算应收单信息
                                FinancialDto financialDto = repaymentService.queryFinanceSign(repaymentEntity
                                        .getWaybillNo());
                                // 应收代收款
                                BigDecimal receiveableAmount = financialDto.getReceiveableAmount() == null ? BigDecimal.ZERO
                                        : financialDto.getReceiveableAmount();
                                // 应收到付款
                                BigDecimal receiveablePayAmoout = financialDto.getReceiveablePayAmoout() == null ? BigDecimal.ZERO
                                        : financialDto.getReceiveablePayAmoout();
                                // 设置 实付运费
                                repaymentEntity.setActualFreight(receiveablePayAmoout);
                                // 设置 代收货款
                                repaymentEntity.setCodAmount(receiveableAmount);
                                // 设置运单号
                                repaymentEntity.setWaybillNo(twoInOneWaybillDto.getMainWaybillNo());
                                // 原单结清货款
                                settleUpOriginalRepayment(repaymentEntity, currentInfo, twoInOneWaybillDto);
                            }
                            validaRepaymentEntity(repaymentEntity, currentInfo,
                                                                        twoInOneWaybillDto, waybillLists);
                        }
                    }
                } else { // 不是子母件
                    // 张新 原单应收款大于零时抛出异常$$
                    if (repaymentEntity.getCodAmount().compareTo(BigDecimal.ZERO) > 0) {
                        // 抛出业务异常
                        throw new RepaymentException("结清原单时，原单应收代收款不为零");
                    }
                    settleUpOriginalRepayment(repaymentEntity, currentInfo, twoInOneWaybillDto);
                }

            }
        } catch (SettlementException se) {
            // 抛出异常
            throw new RepaymentException(se.getErrorCode(), se);
        }
    }

        private void validaRepayment(RepaymentEntity repaymentEntity,
                        CurrentInfo currentInfo, TwoInOneWaybillDto twoInOneWaybillDto) {
                if (CollectionUtils.isNotEmpty(twoInOneWaybillDto.getWaybillNoList())) {
                    List<String> waybillNos = twoInOneWaybillDto.getWaybillNoList();
                    // 遍历子件集合
                    for (int i = 0, len = waybillNos.size(); i < len; i++) {
                        if (!repaymentService.isPayment(waybillNos.get(i))) {
                            // 得到结算应收单信息
                            FinancialDto financialDto = repaymentService.queryFinanceSign(waybillNos
                                    .get(i));
                            // 应收代收款
                            BigDecimal receiveableAmount = financialDto.getReceiveableAmount() == null ? BigDecimal.ZERO
                                    : financialDto.getReceiveableAmount();
                            // 应收到付款
                            BigDecimal receiveablePayAmoout = financialDto.getReceiveablePayAmoout() == null ? BigDecimal.ZERO
                                    : financialDto.getReceiveablePayAmoout();
                            // 设置 实付运费
                            repaymentEntity.setActualFreight(receiveablePayAmoout);
                            // 设置 代收货款
                            repaymentEntity.setCodAmount(receiveableAmount);
                            // 设置运单号
                            repaymentEntity.setWaybillNo(waybillNos.get(i));
                            // 原单结清货款
                            settleUpOriginalRepayment(repaymentEntity, currentInfo,
                                    twoInOneWaybillDto);
                        }
                    }
                }
        }

        private void validaRepaymentEntity(RepaymentEntity repaymentEntity,
                        CurrentInfo currentInfo, TwoInOneWaybillDto twoInOneWaybillDto,
                        List<WaybillExpressEntity> waybillLists) {
                for (int i = 0, len = waybillLists.size(); i < len; i++) {
                    WaybillExpressEntity waybillExpress = waybillLists.get(i);
                    if (!waybillExpress.getOriginalWaybillNo().equals(
                            twoInOneWaybillDto.getMainWaybillNo())) {
                        // 得到结算应收单信息
                        FinancialDto financialDto = repaymentService
                                .queryFinanceSign(waybillExpress.getOriginalWaybillNo());
                        // 应收代收款
                        BigDecimal receiveableAmount = financialDto.getReceiveableAmount() == null ? BigDecimal.ZERO
                                : financialDto.getReceiveableAmount();
                        // 应收到付款
                        BigDecimal receiveablePayAmoout = financialDto.getReceiveablePayAmoout() == null ? BigDecimal.ZERO
                                : financialDto.getReceiveablePayAmoout();
                        // 设置 实付运费
                        repaymentEntity.setActualFreight(receiveablePayAmoout);
                        // 设置 代收货款
                        repaymentEntity.setCodAmount(receiveableAmount);
                        // 设置运单号
                        repaymentEntity.setWaybillNo(waybillExpress.getOriginalWaybillNo());
                        // 原单结清货款
                        settleUpOriginalRepayment(repaymentEntity, currentInfo,
                                twoInOneWaybillDto);
                    }
                }
        }

    /**
     * 根据不同的付款方式 结清货款(原单)
     * 
     * @param repaymentEntity 付款对象
     * @param currentInfo 当前信息对此昂
     * @param paymentType 付款方式
     * @param dateStr2 付款编号
     * @author yangqiang 309603
     * @date 2016-3-3
     */
    private void settleUpOriginalRepayment(RepaymentEntity repaymentEntity, CurrentInfo currentInfo,
            TwoInOneWaybillDto twoInOneWaybillDto) {
        // 校验未受理的更改单
        if (waybillRfcService.isExsitsWayBillRfc(repaymentEntity.getWaybillNo())) {
            // 抛出业务异常
            throw new RepaymentException("该运单更改未受理");
        }

        ActualFreightEntity actentity = actualFreightService.queryByWaybillNo(repaymentEntity.getWaybillNo());
        // 判断运单是否已中止或已作废
        if (actentity != null
                && (WaybillConstants.ABORTED.equals(actentity.getStatus()) || WaybillConstants.OBSOLETE
                        .equals(actentity.getStatus()))) {
            // 抛出业务异常
            throw new RepaymentException("运单状态异常！");
        }

        // 获取付款方式
        String paymentType = repaymentEntity.getPaymentType();
        // 生成付款编号
        StringBuffer dateStr = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        // 拼接付款编号
        dateStr = dateStr.append(repaymentEntity.getWaybillNo());

        // 张新 当付款方式为临欠或者月结 且到付运费为零的时候 付款方式修改为现金
        if (((SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT).equals(paymentType) || (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT)
                .equals(paymentType)) && (repaymentEntity.getActualFreight().compareTo(BigDecimal.ZERO) == 0)) {
            repaymentEntity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
            paymentType = SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH;
        }

        // 子母件母件或不是子母件标识
        boolean isMainWaybillNo = true;
        if (twoInOneWaybillDto != null && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 是子母件

            if (!repaymentEntity.getWaybillNo().equals(twoInOneWaybillDto.getMainWaybillNo())) {
                isMainWaybillNo = false;
            }
        }

        // 结算传入
        PaymentSettlementDto psldto = new PaymentSettlementDto();
        // 如果付款方式为临欠或月结时 调用结算接口-到付运费结转临欠月结
        if ((SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT).equals(paymentType)
                || (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT).equals(paymentType)) {
            // 运单号
            psldto.setWaybillNo(repaymentEntity.getWaybillNo());
            // 付款类型
            psldto.setPaymentType(repaymentEntity.getPaymentType());
            // 部门cide
            psldto.setDestOrgCode(currentInfo.getCurrentDeptCode());
            // 部门名称
            psldto.setDestOrgName(currentInfo.getCurrentDeptName());
            // 客户code
            psldto.setCustomerCode(repaymentEntity.getConsigneeCode());
            // 客户名称
            psldto.setCustomerName(repaymentEntity.getConsigneeName());
            // 时间
            psldto.setBusinessDate(new Date());
            // 实收代收货款费用
            psldto.setCodFee(repaymentEntity.getCodAmount());
            // 实收到付运费
            psldto.setToPayFee(repaymentEntity.getActualFreight());
            // 当实收货款和到付运费不都为0时
            if (isMainWaybillNo) {
                //------CUBC灰度结清服务开始--------add by 353654
                                String vestSystemCode = null;
                    try {
                        ArrayList<String> arrayList = new ArrayList<String>();
                        arrayList.add(psldto.getWaybillNo());
                        CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
                                        SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".settleUpRepayment",
                                        SettlementConstants.TYPE_FOSS);
                        CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
                        List<VestBatchResult> list = response.getVestBatchResult();
                        vestSystemCode = list.get(0).getVestSystemCode();               
                                } catch (Exception e) {
                                        LOGGER.info("灰度分流失败,"+"运单号："+psldto.getWaybillNo());
                                        throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
                                }
                                if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
                                        LOGGER.info("调用结算接口开始"+"运单号："+repaymentEntity.getWaybillNo());//记录日志
                                        paymentSettlementService.confirmToBillReceivable(psldto, currentInfo);
                                        LOGGER.info("调用结算接口结束");//记录日志
                                        // 更新ActualFreight表中的结清状态为已结清
                            ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
                            // 运单号
                            actualFreightEntity.setWaybillNo(repaymentEntity.getWaybillNo());
                            // 结清状态-已结清
                            actualFreightEntity.setSettleStatus(FossConstants.YES);
                            // 结款日期
                            actualFreightEntity.setSettleTime(new Date());
                            // 收货人
                            actualFreightEntity.setDeliverymanName(repaymentEntity.getDeliverymanName());
                            // 证件类型
                            actualFreightEntity.setIdentifyType(repaymentEntity.getIdentifyType());
                            // 证件号
                            actualFreightEntity.setIdentifyCode(repaymentEntity.getIdentifyCode());
                            if (StringUtil.isNotBlank(repaymentEntity.getCodIdentifyCode())) {
                                // 证件类型(代收人)
                                actualFreightEntity.setCodIdentifyType(repaymentEntity.getCodIdentifyType());
                                // 证件号码（代收）
                                actualFreightEntity.setCodIdentifyCode(repaymentEntity.getCodIdentifyCode());
                            } else {
                                // 证件类型(代收人)
                                actualFreightEntity.setCodIdentifyType("");
                                // 证件号码（代收）
                                actualFreightEntity.setCodIdentifyCode("");
                            }
                            // 更新actualFreight表
                            actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
                                }
                                if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
                                        FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
                                        fossDto.setDto(psldto);
                                        fossDto.setCurrentInfo(currentInfo);
                                        CUBCResponseDto resultDto1 = cubcSettlementService.settleReqToCUBU(fossDto);
                                        if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
                                                if(StringUtils.isNotBlank(resultDto1.getMessage())){
                                                        LOGGER.info("调用CUBC结清接口异常信息如下:"+resultDto1.getMessage());
                                                        throw new CubcSettleException(resultDto1.getMessage());
                                                }
                                        }
                                        if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.YES) && StringUtils.equals(resultDto1.getSettleStatus(), FossConstants.YES)){
                                                // 更新ActualFreight表中的结清状态为已结清
                                    ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
                                    // 运单号
                                    actualFreightEntity.setWaybillNo(repaymentEntity.getWaybillNo());
                                    // 结清状态-已结清
                                    actualFreightEntity.setSettleStatus(FossConstants.YES);
                                    // 结款日期
                                    actualFreightEntity.setSettleTime(new Date());
                                    // 收货人
                                    actualFreightEntity.setDeliverymanName(repaymentEntity.getDeliverymanName());
                                    // 证件类型
                                    actualFreightEntity.setIdentifyType(repaymentEntity.getIdentifyType());
                                    // 证件号
                                    actualFreightEntity.setIdentifyCode(repaymentEntity.getIdentifyCode());
                                    if (StringUtil.isNotBlank(repaymentEntity.getCodIdentifyCode())) {
                                        // 证件类型(代收人)
                                        actualFreightEntity.setCodIdentifyType(repaymentEntity.getCodIdentifyType());
                                        // 证件号码（代收）
                                        actualFreightEntity.setCodIdentifyCode(repaymentEntity.getCodIdentifyCode());
                                    } else {
                                        // 证件类型(代收人)
                                        actualFreightEntity.setCodIdentifyType("");
                                        // 证件号码（代收）
                                        actualFreightEntity.setCodIdentifyCode("");
                                    }
                                    // 更新actualFreight表
                                    actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
                                        }
                                }
                                //------CUBC灰度结清服务结束--------add by 353654
            }
            // 付款方式为其他方式时,调用结算接口-实收货款
        } else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(paymentType)
                || SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE.equals(paymentType)
                || SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
                        .equals(paymentType)
                || SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(paymentType)
                || (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH).equals(paymentType)) {
            // 运单号
            psldto.setWaybillNo(repaymentEntity.getWaybillNo());
            // 付款类型
            psldto.setPaymentType(repaymentEntity.getPaymentType());
            // 部门cide
            psldto.setDestOrgCode(currentInfo.getCurrentDeptCode());
            // 部门名称
            psldto.setDestOrgName(currentInfo.getCurrentDeptName());
            // 客户code
            psldto.setCustomerCode(repaymentEntity.getConsigneeCode());
            // 客户名称
            psldto.setCustomerName(repaymentEntity.getConsigneeName());
            // 时间
            psldto.setBusinessDate(new Date());
            // 付款编号
            psldto.setSourceBillNo(dateStr.toString());
            // 实收代收货款费用
            psldto.setCodFee(repaymentEntity.getCodAmount());
            // 实收到付运费
            psldto.setToPayFee(repaymentEntity.getActualFreight());
            // 款项认领编号
            psldto.setPaymentNo(repaymentEntity.getClaimNo());

            // 当实付运费和代收货款同时为0时 更改为已结清
            if (BigDecimalOperationUtil.compare(repaymentEntity.getCodAmount(), BigDecimal.ZERO)
                    && BigDecimalOperationUtil.compare(repaymentEntity.getActualFreight(), BigDecimal.ZERO)) {
                // 更新ActualFreight表中的结清状态为已结清
                ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
                // 运单号
                actualFreightEntity.setWaybillNo(repaymentEntity.getWaybillNo());
                // 结清状态-已结清
                actualFreightEntity.setSettleStatus(FossConstants.YES);
                // 结款日期
                actualFreightEntity.setSettleTime(new Date());
                // 收货人
                actualFreightEntity.setDeliverymanName(repaymentEntity.getDeliverymanName());
                // 证件类型
                actualFreightEntity.setIdentifyType(repaymentEntity.getIdentifyType());
                // 证件号
                actualFreightEntity.setIdentifyCode(repaymentEntity.getIdentifyCode());
                if (StringUtil.isNotBlank(repaymentEntity.getCodIdentifyCode())) {
                    // 证件类型(代收人)
                    actualFreightEntity.setCodIdentifyType(repaymentEntity.getCodIdentifyType());
                    // 证件号码（代收）
                    actualFreightEntity.setCodIdentifyCode(repaymentEntity.getCodIdentifyCode());
                } else {
                    // 证件类型(代收人)
                    actualFreightEntity.setCodIdentifyType("");
                    // 证件号码（代收）
                    actualFreightEntity.setCodIdentifyCode("");
                }
                // 更新actualFreight表
                actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
            } else {
                if (isMainWaybillNo) {
                        //------CUBC灰度结清服务开始--------add by 353654
                                String message = null;
                                String vestSystemCode = null;
                                try {
                                        ArrayList<String> arrayList = new ArrayList<String>();
                                        arrayList.add(repaymentEntity.getWaybillNo());
                                        CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
                                                        SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".validaRepayment",
                                                        SettlementConstants.TYPE_FOSS);
                                        CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
                                        List<VestBatchResult> list = response.getVestBatchResult();
                                        vestSystemCode = list.get(0).getVestSystemCode();       
                                } catch (Exception e) {
                                        LOGGER.info("灰度分流失败,"+"运单号："+repaymentEntity.getWaybillNo());
                                                throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
                                }
                                if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
                                        LOGGER.info("调用结算接口开始" + "运单号：" + repaymentEntity.getWaybillNo());// 记录日志
                        message = paymentSettlementService.confirmToPayment(psldto, currentInfo);
                        LOGGER.info("调用结算接口结束");// 记录日志
                                }
                                if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
                                        FossToCubcRequestDto fossDto = new FossToCubcRequestDto();
                                        fossDto.setDto(psldto);
                                        fossDto.setCurrentInfo(currentInfo);
                                        CUBCResponseDto resultDto1 = cubcSettlementService.settleReqToCUBU(fossDto);
                                        if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
                                                if(StringUtils.isNotBlank(resultDto1.getMessage())){
                                                        LOGGER.info("调用CUBC结清接口异常信息如下:"+resultDto1.getMessage());
                                                        throw new CubcSettleException(resultDto1.getMessage());
                                                }
                                        }
                                        if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.YES) && StringUtils.equals(resultDto1.getSettleStatus(), FossConstants.YES)){
                                                        message = SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE;
                                                }
                                }
                                //------CUBC灰度结清服务结束--------add by 353654
                    if ((SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE).equals(message)) {
                        // 更新ActualFreight表中的结清状态为已结清
                        ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
                        // 运单号
                        actualFreightEntity.setWaybillNo(repaymentEntity.getWaybillNo());
                        // 结清状态-已结清
                        actualFreightEntity.setSettleStatus(FossConstants.YES);
                        // 结款日期
                        actualFreightEntity.setSettleTime(new Date());
                        // 收货人
                        actualFreightEntity.setDeliverymanName(repaymentEntity.getDeliverymanName());
                        // 证件类型
                        actualFreightEntity.setIdentifyType(repaymentEntity.getIdentifyType());
                        // 证件号
                        actualFreightEntity.setIdentifyCode(repaymentEntity.getIdentifyCode());
                        if (StringUtil.isNotBlank(repaymentEntity.getCodIdentifyCode())) {
                            // 证件类型(代收人)
                            actualFreightEntity.setCodIdentifyType(repaymentEntity.getCodIdentifyType());
                            // 证件号码（代收）
                            actualFreightEntity.setCodIdentifyCode(repaymentEntity.getCodIdentifyCode());
                        } else {
                            // 证件类型(代收人)
                            actualFreightEntity.setCodIdentifyType("");
                            // 证件号码（代收）
                            actualFreightEntity.setCodIdentifyCode("");
                        }
                        // 更新actualFreight表
                        actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
                    }
                } else {
                    // 更新ActualFreight表中的结清状态为已结清
                    ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
                    // 运单号
                    actualFreightEntity.setWaybillNo(repaymentEntity.getWaybillNo());
                    // 结清状态-已结清
                    actualFreightEntity.setSettleStatus(FossConstants.YES);
                    // 结款日期
                    actualFreightEntity.setSettleTime(new Date());
                    // 收货人
                    actualFreightEntity.setDeliverymanName(repaymentEntity.getDeliverymanName());
                    // 证件类型
                    actualFreightEntity.setIdentifyType(repaymentEntity.getIdentifyType());
                    // 证件号
                    actualFreightEntity.setIdentifyCode(repaymentEntity.getIdentifyCode());
                    if (StringUtil.isNotBlank(repaymentEntity.getCodIdentifyCode())) {
                        // 证件类型(代收人)
                        actualFreightEntity.setCodIdentifyType(repaymentEntity.getCodIdentifyType());
                        // 证件号码（代收）
                        actualFreightEntity.setCodIdentifyCode(repaymentEntity.getCodIdentifyCode());
                    } else {
                        // 证件类型(代收人)
                        actualFreightEntity.setCodIdentifyType("");
                        // 证件号码（代收）
                        actualFreightEntity.setCodIdentifyCode("");
                    }
                    // 更新actualFreight表
                    actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
                }
            }
        }
        // 付款编号
        repaymentEntity.setRepaymentNo(dateStr.toString());
        // 付款时间
        repaymentEntity.setPaymentTime(new Date());
        // 操作人
        repaymentEntity.setOperator(currentInfo.getEmpName());
        // 操作人编码
        repaymentEntity.setOperatorCode(currentInfo.getEmpCode());
        // 操作部门
        repaymentEntity.setOperateOrgName(currentInfo.getCurrentDeptName());
        // 操作部门编码
        repaymentEntity.setOperateOrgCode(currentInfo.getCurrentDeptCode());
        // 当实付运费和代收货款同时为0时 更改为无需生成
        if (BigDecimalOperationUtil.compare(repaymentEntity.getCodAmount(), BigDecimal.ZERO)
                && BigDecimalOperationUtil.compare(repaymentEntity.getActualFreight(), BigDecimal.ZERO)) {
            // 无需结清 将付款信息置0
            repaymentEntity.setActualFreight(BigDecimal.ZERO);
            // 无需结清 将付款信息置0
            repaymentEntity.setCodAmount(BigDecimal.ZERO);
            // 财务单据无需生成
            repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOTREQUIRE);
        } else {
            // 判断是否是子母件
            if (twoInOneWaybillDto != null && FossConstants.YES.equals(twoInOneWaybillDto.getIsTwoInOne())) { // 是子母件
                if (repaymentEntity.getWaybillNo().equals(twoInOneWaybillDto.getMainWaybillNo())) { // 为子母件的母件
                    // 财务单据已生成
                    repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_GENERATED);
                } else { // 为子母件的子件
                    // 设置财务单据无需生成
                    repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOTREQUIRE);
                }
            } else { // 不是子母件
                // 财务单据已生成
                repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_GENERATED);
            }
        }
        // 币种
        repaymentEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
        // job id
        repaymentEntity.setJobId(UUIDUtils.getUUID());
        // 生成付款信息
        repaymentDao.addPaymentInfo(repaymentEntity);
        // 生成到达联信息
        ArriveSheetEntity entity = new ArriveSheetEntity();
        // 运单号
        entity.setWaybillNo(repaymentEntity.getWaybillNo());
        // 收货人
        entity.setDeliverymanName(repaymentEntity.getDeliverymanName());
        // 证件类型
        entity.setIdentifyType(repaymentEntity.getIdentifyType());
        // 证件号
        entity.setIdentifyCode(repaymentEntity.getIdentifyCode());
        // 调用到达联接口
        checkGenerateArriveSheet(entity, currentInfo);
        // 更新到达联,通过运单号
        arriveSheetManngerService.updateArriveSheetEntityByWaybillNo(repaymentEntity.getWaybillNo(),
                repaymentEntity.getDeliverymanName(), repaymentEntity.getIdentifyType(),
                repaymentEntity.getIdentifyCode());
    }

    public int checkGenerateArriveSheet(ArriveSheetEntity entity, CurrentInfo cinfo) {
        MutexElement mutexElement = new MutexElement(entity.getWaybillNo(), "运单号",
                MutexElementType.ARRIVE_SHEET_CREATE_WAYBILLNO);
        // 互斥锁定
        boolean isLocked = businessLockService.lock(mutexElement, NumberConstants.ZERO);
        // 若未上锁
        if (!isLocked) {
            // 抛出派送单异常
            throw new ArriveSheetMannerException(ArriveSheetMannerException.ARRIVE_SHEET_CREATING);
        }
        // 计算可打印件数
        Integer printNum = 0; // 可打印件数
        ActualFreightEntity actualFreightEntity = actualFreightDao.queryByWaybillNo(entity.getWaybillNo());
        if (actualFreightEntity != null && actualFreightEntity.getArriveGoodsQty() != null
                && actualFreightEntity.getGenerateGoodsQty() != null) {
            printNum = actualFreightEntity.getArriveGoodsQty() - actualFreightEntity.getGenerateGoodsQty();
        }
        // 校验是否有效且为“生成”状态的到达联
        List<ArriveSheetEntity> list = arrivesheetDao.queryArriveSheetByWaybillNo(new ArriveSheetEntity(
                entity.getWaybillNo(), ArriveSheetConstants.STATUS_GENERATE, FossConstants.YES,
                FossConstants.NO));
        // 是否有生成状态的到达联
        ArriveSheetEntity existedArriveSheetEntity = null;
        // 生成状态下的到达联件数
        // 根据到达联编号 判断已有“生成”状态的到达联
        if (CollectionUtils.isNotEmpty(list)) {
            existedArriveSheetEntity = list.get(0);
        }
        // 可打印件数 大于0
        if (printNum > 0) {
            // 有 有效且为“生成”状态的到达联
            // 计算已生成件数
            int generateGoodsQty = actualFreightEntity.getGenerateGoodsQty() + printNum;
            if (generateGoodsQty > actualFreightEntity.getGoodsQty()) {
                generateGoodsQty = actualFreightEntity.getGoodsQty();
            }
            if (existedArriveSheetEntity != null) {
                // 存在“生成”状态的到达联 则作废之前到达联并生成新的到达联
                // 如果通知客户值不为空 则重新设置
                if (entity.getCreateDate() != null) {
                    existedArriveSheetEntity.setCreateDate(entity.getCreateDate());
                }
                if (StringUtils.isNotEmpty(entity.getCreateUserName())) {
                    existedArriveSheetEntity.setCreateUserName(entity.getCreateUserName());
                }
                if (StringUtils.isNotEmpty(entity.getCreateUserCode())) {
                    existedArriveSheetEntity.setCreateUserCode(entity.getCreateUserCode());
                }
                if (StringUtils.isNotEmpty(entity.getCreateOrgName())) {
                    existedArriveSheetEntity.setCreateOrgName(entity.getCreateOrgName());
                }
                if (StringUtils.isNotEmpty(entity.getCreateOrgCode())) {
                    existedArriveSheetEntity.setCreateOrgCode(entity.getCreateOrgCode());
                }
                // 是否必送货
                if (StringUtils.isNotEmpty(entity.getIsSentRequired())) {
                    existedArriveSheetEntity.setIsSentRequired(entity.getIsSentRequired());
                }
                // 是否需要发票
                if (StringUtils.isNotEmpty(entity.getIsNeedInvoice())) {
                    existedArriveSheetEntity.setIsNeedInvoice(entity.getIsNeedInvoice());
                }
                // 提前通知内容
                if (StringUtils.isNotEmpty(entity.getPreNoticeContent())) {
                    existedArriveSheetEntity.setPreNoticeContent(entity.getPreNoticeContent());
                }
                // 送货要求
                if (StringUtils.isNotEmpty(entity.getDeliverRequire())) {
                    existedArriveSheetEntity.setDeliverRequire(entity.getDeliverRequire());
                }
                // 此时生成到达联的件数 = 可打印件数 + 作废的件数
                if ((printNum + list.get(0).getArriveSheetGoodsQty()) > actualFreightEntity.getGoodsQty()) {
                    existedArriveSheetEntity.setArriveSheetGoodsQty(actualFreightEntity.getGoodsQty());
                } else {
                    existedArriveSheetEntity.setArriveSheetGoodsQty(printNum
                            + list.get(0).getArriveSheetGoodsQty());
                }
                this.copyAndUpdateArriveSheet(existedArriveSheetEntity);

                // 更新 actualFreight 表的 "生成到达联件数"字段
                ActualFreightEntity actualFreight = new ActualFreightEntity();
                actualFreight.setWaybillNo(entity.getWaybillNo());
                actualFreight.setGenerateGoodsQty(generateGoodsQty);
                actualFreightDao.updateGenerateGoodsQtyByWaybillNo(actualFreight);
            } else {
                // 否则直接 生成到达联
                addNewArriveSheet(printNum, actualFreightEntity, entity, generateGoodsQty, cinfo);
            }
        } else if (StringUtil.equals(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY, entity.getSource())
                && existedArriveSheetEntity == null) {
            // 是通知客户打印 并且没有生成状态的到达联
            // 可打印件数= 开单件数 - 已生成件数
            addNewArriveSheet(actualFreightEntity.getGoodsQty() - actualFreightEntity.getGenerateGoodsQty(),
                    actualFreightEntity, entity, actualFreightEntity.getGoodsQty(), cinfo);
        }
        businessLockService.unlock(mutexElement);
        return 0;
    }

    private String copyAndUpdateArriveSheet(ArriveSheetEntity entity) {
        if (entity != null) {
            // 将原有到达联 置废 将有效日期改为 此时
            ArriveSheetEntity updateArriveSheet = new ArriveSheetEntity();
            Date now = new Date();
            updateArriveSheet.setArrivesheetNo(entity.getArrivesheetNo());
            updateArriveSheet.setActive(FossConstants.NO);
            updateArriveSheet.setModifyTime(now);
            updateArriveSheet.setId(entity.getId());
            arrivesheetDao.updateByPrimaryKeySelective(updateArriveSheet);
            // copy原有到达联 并生成新的到达联。
            entity.setCreateTime(now);
            entity.setActive(FossConstants.YES);
            entity.setDestroyed(FossConstants.NO);
            entity.setModifyDate(null);
            if (!ArriveSheetConstants.STATUS_DELIVER.equals(entity.getStatus())) {
                entity.setStatus(ArriveSheetConstants.STATUS_GENERATE);
            }
            // 添加通知信息
            notifyCustomerService.setArriveSheetNotifyInfo(entity);
            arrivesheetDao.addArriveSheetData(entity);
            return entity.getArrivesheetNo();
        } else {
            return null;
        }
    }

    private void addNewArriveSheet(Integer printNum, ActualFreightEntity actualFreightEntity,
            ArriveSheetEntity entity, int generateGoodsQty, CurrentInfo cinfo) {
        // 对重复到达联，再次添加验证
        // 校验是否有效且为“生成”状态的到达联
        List<ArriveSheetEntity> list = arrivesheetDao.queryArriveSheetByWaybillNo(new ArriveSheetEntity(
                entity.getWaybillNo(), ArriveSheetConstants.STATUS_GENERATE, FossConstants.YES,
                FossConstants.NO));
        if (CollectionUtils.isNotEmpty(list)) {
            return;
        }

        entity.setArriveSheetGoodsQty(printNum);
        // 到达提货人
        entity.setDeliverymanName(actualFreightEntity.getDeliverymanName());
        // 证件类型
        entity.setIdentifyType(actualFreightEntity.getIdentifyType());
        // 证件号码
        entity.setIdentifyCode(actualFreightEntity.getIdentifyCode());
        // 生成到达联
        String arrivesheetNo = this.generateArriveSheet(entity, cinfo);
        if (StringUtil.isEmpty(arrivesheetNo)) {
            LOGGER.info(entity.getWaybillNo() + "无法生成0件的到达联。");
            return;
        }
        // 更新 actualFreight 表的 "生成到达联件数"字段
        ActualFreightEntity actualFreight = new ActualFreightEntity();
        actualFreight.setWaybillNo(entity.getWaybillNo());
        actualFreight.setGenerateGoodsQty(generateGoodsQty);
        actualFreightDao.updateGenerateGoodsQtyByWaybillNo(actualFreight);
    }

    /**
     * 
     * @param entity
     * @return
     */
    private String generateArriveSheet(ArriveSheetEntity entity, CurrentInfo cinfo) {
        if (entity.getArriveSheetGoodsQty() == 0) {
            return "";
        }
        UserEntity user = cinfo.getUser();
        OrgAdministrativeInfoEntity orgEntity = cinfo.getDept();
        // 产生到达联编号
        entity.setArrivesheetNo(arriveSheetManngerService.generateArriveSheetId(entity.getWaybillNo()));
        entity.setId(UUIDUtils.getUUID());
        entity.setActive(FossConstants.YES);
        if (!ArriveSheetConstants.STATUS_DELIVER.equals(entity.getStatus())) {
            entity.setStatus(ArriveSheetConstants.STATUS_GENERATE);
        }
        // 将状态作为有效
        entity.setDestroyed(FossConstants.NO);
        entity.setCreateTime(new Date());
        entity.setCreateOrgCode(orgEntity.getCode());
        entity.setCreateOrgName(orgEntity.getName());
        entity.setCreateUserCode(user.getEmployee().getEmpCode());
        entity.setCreateUserName(user.getEmployee().getEmpName());
        entity.setIsPrinted(FossConstants.NO);
        entity.setPrinttimes(0);
        // 添加通知信息
        notifyCustomerService.setArriveSheetNotifyInfo(entity);
        arrivesheetDao.addArriveSheetData(entity);
        return entity.getArrivesheetNo();
    }

    @SuppressWarnings("unused")
        private List<AirAgencyQueryDto> queryAirAgencyQueryDtoList(AirAgencyQueryDto airAgencyQueryDto,
            String orgCode) {
        // 如果airAgencyQueryDto不为空
        if (airAgencyQueryDto != null) {
            // 获取当前部门
            String currOrgCode = orgCode;// FossUserContextHelper.getOrgCode();
            // 如果单号不为空
            if (StringUtil.isNotEmpty(airAgencyQueryDto.getWaybillNo())) {
                boolean flag = repaymentService.isPayment(airAgencyQueryDto.getWaybillNo());
                if (flag) {
                    // 抛出业务异常
                    throw new RepaymentException("该运单已结清");
                }
                WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(airAgencyQueryDto
                        .getWaybillNo());
                if (waybillEntity != null && FossConstants.YES.equals(waybillEntity.getIsWholeVehicle())
                        && currOrgCode.equals(waybillEntity.getReceiveOrgCode())
                        && FossConstants.NO.equals(waybillEntity.getIsPassOwnDepartment()))// 判断如果当前部门是整车，当前登录部门是收货部门，且不经过到达部门
                {
                    // update by foss 231434 2015-6-2
                    // 对整车(三级)产品，若开单时间在2013-08-01之前(不包含2013-08-01)，不做配载校验
                    Date date = DateUtils.convert(dateStr, format);
                    // 中转接口校验通过
                    if (!date.before(waybillEntity.getBillTime())
                            || vehicleAssembleBillService
                                    .queryVehicleAssemblyBillByWaybillNo(airAgencyQueryDto.getWaybillNo())) {
                        // 传入当前部门
                        airAgencyQueryDto.setReceiveOrgCode(currOrgCode);
                        // 设置状态为生效
                        airAgencyQueryDto.setActive(FossConstants.YES);
                        // 设置为未结清
                        airAgencyQueryDto.setSettleStatus(FossConstants.NO);
                        // 设置运单处理状态：已开单PC_ACTIVE，已经补录PDA_PENDING
                        List<String> checkStatus = new ArrayList<String>();
                        checkStatus.add("PDA_ACTIVE");
                        checkStatus.add("PC_ACTIVE");
                        airAgencyQueryDto.setCheckStatus(checkStatus);
                        // 查询待处理列表
                        return repaymentDao.queryAirAgencyQueryDtoListByReceiveOrg(airAgencyQueryDto);
                    } else {
                        // 抛出业务异常
                        throw new RepaymentException("整车配载");
                    }
                }
            }
            // 如果运输性质为全部
            if (airAgencyQueryDto.getProductCode().equals(TOTAL)) {
                // 运输性质传空
                airAgencyQueryDto.setProductCode("");
            }
            // 如果收货人手机不为空
            if (StringUtil.isNotEmpty(airAgencyQueryDto.getReceiveCustomerMobilephone())) {
                // 收货客户电话传空
                airAgencyQueryDto.setReceiveCustomerPhone(null);
                // 收货客户名称传空
                airAgencyQueryDto.setReceiveCustomerName(null);
            }
            // 如果收货人电话不为空
            else if (StringUtil.isNotEmpty(airAgencyQueryDto.getReceiveCustomerPhone())) {
                // 收货客户名称传空
                airAgencyQueryDto.setReceiveCustomerName(null);
            }

            // 获取当前部门
            airAgencyQueryDto.setLastLoadOrgCode(currOrgCode);
            // 设置状态为生效
            airAgencyQueryDto.setActive(FossConstants.YES);
            // 设置为未结清
            airAgencyQueryDto.setSettleStatus(FossConstants.NO);
            // 设置运单处理状态：已开单PC_ACTIVE，已经补录PDA_PENDING
            List<String> checkStatus = new ArrayList<String>();
            checkStatus.add("PDA_ACTIVE");
            checkStatus.add("PC_ACTIVE");
            airAgencyQueryDto.setCheckStatus(checkStatus);
            // 添加库存外场、库区默认查询条件
            List<String> list = handleQueryOutfieldService.getEndStockCodeAndExpressAreaCode(currOrgCode);
            if (CollectionUtils.isNotEmpty(list)) {
                List<String> ld = new ArrayList<String>();
                ld.add(list.get(1));
                ld.add(list.get(2));
                // 传入最终库存部门code
                airAgencyQueryDto.setEndStockOrgCode(list.get(0));
                // 传入库区code
                airAgencyQueryDto.setGoodsAreaCodes(ld);
            }

            // 根据是否快递字段判断 add by yangkang
            if (airAgencyQueryDto.getIsExpress().equals(FossConstants.YES)) {
                // 查询快递待处理列表
                return repaymentDao.queryExpressAirAgencyQueryDtoList(airAgencyQueryDto);
            } else {
                // 查询待处理列表
                return repaymentDao.queryAirAgencyQueryDtoList(airAgencyQueryDto);
            }
        }
        // 返回空
        return null;
    }

    /**
     * 校验是否整车
     * 
     * @param waybillNoList
     * @return
     */
    private boolean isVehicle(String waybillNo) {
        boolean flag = false;
        if (StringUtils.isBlank(waybillNo)) {
            throw new SettlementException("PDA自动结清货款传入运单号为空！");
        }
        if (waybillNo.length() == WAYBILLNOLENGTH && waybillNo.startsWith("1")) {
            flag = true;
        }
        return flag;
    }

    /**
     * 返回vts结清状态同步接口
     * 
     * @param waybillNo
     * @return
     */
    private void sendSettleStatusToVts(String waybillNo) {
        try {
            SettlementPayToVtsDto vtsPdaDto = new SettlementPayToVtsDto();
            // 根据运单号查询实际承运表结清状态
            String status = queryPdaSettleStatus(waybillNo);
            if (StringUtils.isEmpty(status)) {
                throw new SettlementException("查询结清状态失败，运单号：" + waybillNo);
            }
            LOGGER.info("运单号：" + waybillNo + "，返回vts结清状态:" + status);
            LOGGER.info("FOSS推送pda结清状态至VTS开始..." + "运单号：" + waybillNo);
            // 封装推送vts结清状态同步接口参数
            vtsPdaDto.setSettleStatus(status);
            vtsPdaDto.setWaybillNo(waybillNo);
            Map<String, Object> obj = new HashMap<String, Object>();
            obj.put("type", "updateBillStatusByfoss");
            obj.put("requestEntity", vtsPdaDto);
            // String js = JSONObject.fromObject(obj).toString();
            String js = JSONObject.toJSON(obj).toString();
            LOGGER.info("运单号：" + waybillNo + "，foss同步至vts结清状态同步接口参数为：" + js);
            RequestEntity entity = new StringRequestEntity(js, "application/json", "UTF-8");

            // 根据ESB_CODE查到ESB地址信息
            FossConfigEntity fossConfigEntity = fossConfigEntityService
                    .queryFossConfigEntityByServerCode(ESB_SERVICE_CODE);
            String esbBTPSAddr = fossConfigEntity.getEsbAddr();
            LOGGER.info("查询到esb地址为" + esbBTPSAddr);
            LOGGER.info("VTS从FOSS请求的ESB地址为" + esbBTPSAddr + ESB_CLIENT_CODE);
            // 构造postMethod实例
            PostMethod postMethod = new PostMethod(esbBTPSAddr + ESB_CLIENT_CODE);

            // PostMethod postMethod = new PostMethod(VTS_SERVICE_CODE);//
            // 和VTS本地联调用的

            postMethod.setRequestEntity(entity);
            postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
            HttpClient httpClient = new HttpClient();
            // 设置编码格式
            httpClient.getParams().setContentCharset("UTF-8");
            // 执行postMethod
            int statusCode = httpClient.executeMethod(postMethod);

            LOGGER.info("客户端方法执行的结果状态:" + statusCode);
            String responseBody = "";
            // 获取返回值
            responseBody = postMethod.getResponseBodyAsString();
            LOGGER.info("返回值信息为：" + responseBody);

            // 解析传入json，封装实体类
            JSONObject object = JSONObject.parseObject(responseBody);
            SettlementPayToVtsDto dto = object.getObject("responseEntity", SettlementPayToVtsDto.class);
            LOGGER.info("是否成功：" + dto.getIsSuccess());
            LOGGER.info("失败原因为: " + dto.getMsg());
        } catch (Exception e) {
            throw new SettlementException("FOSS同步结清状态至VTS失败,异常信息为: " + e.getMessage());
        }
    }

    /**
     * 根据运单号查询实际承运表结清状态
     * 
     * @param waybillNo
     * @return
     */
    private String queryPdaSettleStatus(String waybillNo) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("waybillNo", waybillNo);
        return pdaSignEntityDao.querySettleStatus(map);
    }

    /**
     * 根据 交易流水号，部门编码，查询T+0数据返回，并判断余额大小
     * 
     * @param seriaNo 交易流水号
     * @param deptCode 部门编码
     * @param codAmount 实际支付代收货款
     * @param actualFreight 实际支付运费
     * @return
     */
    @Override
    public PosCardEntity queryPosCard(String seriaNo, String deptCode, BigDecimal codAmount,
            BigDecimal actualFreight) {
        // 查询T+0报表获取未使用金额
        // 准备查询数据
        PosCardEntity posCardEntity = null;
        PosCardManageDto posCardManageDto = new PosCardManageDto();
        posCardManageDto.setTradeSerialNo(seriaNo);
        posCardManageDto.setOrgCode(deptCode);
        // posCardManageDto.setBelongMoudleCode(SettlementDictionaryConstants.NCI_SETTLE);
        // 查询T+0报表数据
        //根据交流水号和部门编码查询POS刷卡数据   灰度  353654---start
        List<PosCardEntity> posCardEntitys = null;
//        String vestSystemCode = null;
//        try {
//              List<String> wayBillNoList = new ArrayList<String>();
//              wayBillNoList.add(seriaNo);//根据交易流水号能否灰度
//              CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(wayBillNoList,
//                              SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".queryPosCard",
//                              SettlementConstants.TYPE_FOSS);
//              CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
//              List<VestBatchResult> list1 = response.getVestBatchResult();
//              vestSystemCode = list1.get(0).getVestSystemCode();      
//              } catch (Exception e) {
//                      LOGGER.info("灰度分流失败,"+"交易流水号："+seriaNo);
//                      throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
//              }
//              if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
                        posCardEntitys = pdaPosManageService.queryPosCardData(posCardManageDto).getPosCardEntitys();
                        // 是否存在
                        if (posCardEntitys == null || posCardEntitys.isEmpty()) {
                                throw new RepaymentException("输入流水号不存在或者输入流水号有误或者流水所属部门不正确。");
                        } else {
                                posCardEntity = posCardEntitys.get(0);
                        }
                        // 判断所属模块，结清货款，
                        if (SettlementDictionaryConstants.NCI_DEPOSIT.equals(posCardEntity.getBelongModule())) {
                                throw new RepaymentException("该交易流水号仅能做预收或做小票！");
                        }
                        // 获取未使用金额
                        BigDecimal amount = posCardEntity.getUnUsedAmount();
                        // 比较
                        if (amount.compareTo(codAmount.add(actualFreight)) < 0) {// 可用金额小于实收代收货款+实收到付运费
                                throw new RepaymentException("可用余额不足。");
                        }
//              }
//              if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
//                      //TODO  待定
//              }
                //根据交流水号和部门编码查询POS刷卡数据   灰度  353654---end
        return posCardEntity;
    }

    /**
     * 使用PosCard金额，以及PosCardDetail中金额
     * 
     * @param waybillNo 运单号
     * @param codAmount 实际支付代收货款
     * @param actualFreight 实际支付运费
     * @param totalAmount 运单总金额
     * @param currentInfo 用户
     * @param posCardEntity 查询出PosCard实体
     */
    @Override
    public void applyPosCardAndDetail(String waybillNo, BigDecimal codAmount, BigDecimal actualFreight,
            CurrentInfo currentInfo, PosCardEntity posCardEntity, String invoiceType) {
        // 更新POS刷卡信息
        // 准备数据
        BigDecimal payAmount = codAmount.add(actualFreight); // 使用总金额
        posCardEntity.setModifyUser(currentInfo.getEmpName()); // 更新人名称
        posCardEntity.setModifyUserCode(currentInfo.getEmpCode()); // 更新人编码
        posCardEntity.setUsedAmount(posCardEntity.getUsedAmount().add(payAmount)); // 已使用金额
        posCardEntity.setUnUsedAmount(posCardEntity.getUnUsedAmount().subtract(payAmount)); // 未使用金额
        //根据交流水号去更新POS刷卡已使用流水号金额，未使用金额   灰度  353654---start
        String vestSystemCode1 = null;
        try {
                List<String> arrayList = new ArrayList<String>();
                arrayList.add(waybillNo);
                CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
                                SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".applyPosCardAndDetail",
                                SettlementConstants.TYPE_FOSS);
                CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
                List<VestBatchResult> list = response.getVestBatchResult();
                vestSystemCode1 = list.get(0).getVestSystemCode();      
                } catch (Exception e) {
                        LOGGER.info("灰度分流失败,"+"运单号："+waybillNo);
                        throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
                }
                if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode1)){
                        pdaPosManageService.updatePosCardMessageAmount(posCardEntity);
                        // 插入新的POS刷卡明细
                // 准备数据
                PosCardDetailEntity posCardDetailEntity = new PosCardDetailEntity();
                posCardDetailEntity.setTradeSerialNo(posCardEntity.getTradeSerialNo()); // 交易流水号
                posCardDetailEntity.setInvoiceType(invoiceType); // 运单
                posCardDetailEntity.setInvoiceNo(waybillNo); // 运单号
                // posCardDetailEntity.setAmount(waybilldto.getToPayAmount()); //发生金额
                BigDecimal totalAmount = repaymentDao.getTotalAmount(waybillNo);
                posCardDetailEntity.setAmount(totalAmount); // 运单总金额
                posCardDetailEntity.setOccupateAmount(payAmount); // 已占用金额
                posCardDetailEntity.setUnVerifyAmount(BigDecimal.ZERO); // 未核销金额
                posCardDetailEntity.setCreateUser(currentInfo.getEmpName()); // 创建人名称
                posCardDetailEntity.setCreateUserCode(currentInfo.getEmpCode()); // 创建人编码
                        pdaPosManageService.insertOrUpdateDetail(posCardDetailEntity);//调用接口插入数据
                }
                if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode1)){
                        //TODO  自行处理
                }
                //根据交流水号去更新POS刷卡已使用流水号金额，未使用金额   灰度  353654---end
    }

    public void setBusinessLockService(IBusinessLockService businessLockService) {
        this.businessLockService = businessLockService;
    }

    public void setRepaymentService(IRepaymentService repaymentService) {
        this.repaymentService = repaymentService;
    }

    public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
        this.waybillManagerService = waybillManagerService;
    }

    public void setTakingService(ITakingService takingService) {
        this.takingService = takingService;
    }

    public void setWaybillExpressService(IWaybillExpressService waybillExpressService) {
        this.waybillExpressService = waybillExpressService;
    }

    public void setLdpAgencyDeptService(ILdpAgencyDeptService ldpAgencyDeptService) {
        this.ldpAgencyDeptService = ldpAgencyDeptService;
    }

    public IWaybillRelateDetailEntityService getWaybillRelateDetailEntityService() {
        return waybillRelateDetailEntityService;
    }

    public void setWaybillRelateDetailEntityService(
            IWaybillRelateDetailEntityService waybillRelateDetailEntityService) {
        this.waybillRelateDetailEntityService = waybillRelateDetailEntityService;
    }

    public IWaybillSignResultService getWaybillSignResultService() {
        return waybillSignResultService;
    }

    public void setWaybillSignResultService(IWaybillSignResultService waybillSignResultService) {
        this.waybillSignResultService = waybillSignResultService;
    }

    public void setPdaPosManageService(IPdaPosManageService pdaPosManageService) {
        this.pdaPosManageService = pdaPosManageService;
    }

    public void setBillReceivableEntityDao(IBillReceivableEntityDao billReceivableEntityDao) {
        this.billReceivableEntityDao = billReceivableEntityDao;
    }

    public void setActualFreightDao(IActualFreightDao actualFreightDao) {
        this.actualFreightDao = actualFreightDao;
    }

    public void setArrivesheetDao(IArrivesheetDao arrivesheetDao) {
        this.arrivesheetDao = arrivesheetDao;
    }

    public void setNotifyCustomerService(INotifyCustomerService notifyCustomerService) {
        this.notifyCustomerService = notifyCustomerService;
    }

    public void setHandleQueryOutfieldService(IHandleQueryOutfieldService handleQueryOutfieldService) {
        this.handleQueryOutfieldService = handleQueryOutfieldService;
    }

    public void setVehicleAssembleBillService(IVehicleAssembleBillService vehicleAssembleBillService) {
        this.vehicleAssembleBillService = vehicleAssembleBillService;
    }

    public void setVtsValidateAndSettlementService(
            IVtsValidateAndSettlementService vtsValidateAndSettlementService) {
        this.vtsValidateAndSettlementService = vtsValidateAndSettlementService;
    }

    public void setFossConfigEntityService(IFossConfigEntityService fossConfigEntityService) {
        this.fossConfigEntityService = fossConfigEntityService;
    }

}
