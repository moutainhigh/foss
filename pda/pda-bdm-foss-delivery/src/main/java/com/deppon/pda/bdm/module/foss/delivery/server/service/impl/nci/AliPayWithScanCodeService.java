package com.deppon.pda.bdm.module.foss.delivery.server.service.impl.nci;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.builder.AlipayTradePayRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPayResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.sign.api.server.service.IzfbCodeRepaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IzfbStatementManageService;
import com.deppon.foss.module.settlement.common.api.shared.domain.VerificationEntity;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.DeliveryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.delivery.server.IPdaToCubcService;
import com.deppon.pda.bdm.module.foss.delivery.server.ISettlementPdaToCubcService;
import com.deppon.pda.bdm.module.foss.delivery.server.dao.IDeliveryPdaDao;
import com.deppon.pda.bdm.module.foss.delivery.shared.constants.DeliveryExceptionConstant;
import com.deppon.pda.bdm.module.foss.delivery.shared.constants.DeryConstant;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.AccountStatementEntitys;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.GetPaymentDeatilSuccessEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.GetPaymentSuccessEntitys;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PayInfoDO;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.RequestDO;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.SettlementOfPaymentSuccessEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.VestBatchResult;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.VestResponse;
import com.deppon.pda.bdm.module.foss.delivery.shared.exception.AliPayWithScanCodeException;

/**
 * TODO(德邦扫码支付PDA后台服务)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:ganxiaojian,2016-10-12 上午9:45:14,content:TODO
 * </p>
 * 
 * @author ganxiaojian-pda开发组
 * @date 2016-10-12 上午9:45:14
 * @since
 * @version
 */
public class AliPayWithScanCodeService implements IBusinessService<Void, SettlementOfPaymentSuccessEntity> {
	//线程池
	ExecutorService fixedThreadPool = Executors.newFixedThreadPool(DeliveryExceptionConstant.AlipayThreadNum);
	
    // 日志
    private Logger log = Logger.getLogger(AliPayWithScanCodeService.class);

    // 支付宝当面付2.0服务
    private static AlipayTradeService tradeService;

    // 支付类型
    private static final String alipayWithScanCode = "支付宝条码支付";

    // 结清货款模块标识
    private static final String settleCredit = "settleCredit";

    // 对账单模块标识
    private static final String accountStatement = "accountStatement";

    // 支付超时，线下扫码交易定义为5分钟
    private static final String timeoutExpress = "5m";

    // 对账接口
    private IzfbStatementManageService zfbStatementManageService;

    // 结清货款接口
    private IzfbCodeRepaymentService zfbCodeRepaymentService;
    
    //DeliveryPdaDao保存异常数据（by:218371-foss-zhaoyanjun）
    IDeliveryPdaDao deliveryPdaDao;
    //调用CUBC接口
  	private IPdaToCubcService pdaToCubcService;
  	//封装参数
  	private ISettlementPdaToCubcService settlementPdaToCubcService;

    public void setZfbStatementManageService(IzfbStatementManageService zfbStatementManageService) {
        this.zfbStatementManageService = zfbStatementManageService;
    }

    public void setZfbCodeRepaymentService(IzfbCodeRepaymentService zfbCodeRepaymentService) {
        this.zfbCodeRepaymentService = zfbCodeRepaymentService;
    }
    
    public void setPdaToCubcService(IPdaToCubcService pdaToCubcService) {
		this.pdaToCubcService = pdaToCubcService;
	}

	public void setSettlementPdaToCubcService(
			ISettlementPdaToCubcService settlementPdaToCubcService) {
		this.settlementPdaToCubcService = settlementPdaToCubcService;
	}

    // 初始化
    static {
        // 加载配置文件
        Configs.init("zfbinfo.properties");
        // 支付服务类
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
    }

    /**
     * 
     * <p>
     * TODO(方法详细描述说明、方法参数的具体涵义)
     * </p>
     * 
     * @author ganxiaojian
     * @date 2016-10-12 上午9:45:14
     * @param asyncMsg
     * @return
     * @throws PdaBusiException
     * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
     */
    @Override
    public SettlementOfPaymentSuccessEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
        String content = asyncMsg.getContent();
        SettlementOfPaymentSuccessEntity entity = JsonUtil.parseJsonToObject(
                SettlementOfPaymentSuccessEntity.class, content);
        return entity;
    }

    /**
     * 
     * <p>
     * TODO(方法详细描述说明、方法参数的具体涵义)
     * </p>
     * 
     * @author ganxiaojian
     * @date 2016-10-12 上午9:45:14
     * @param asyncMsg
     * @param param pda 前端传来的参数
     * @return
     * @throws PdaBusiException
     * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg,
     *      java.lang.Object)
     */
    @Transactional
    @Override
    public Void service(AsyncMsg asyncMsg, SettlementOfPaymentSuccessEntity param) throws PdaBusiException {
        log.info("BDM == 支付宝扫码支付  ==> 开始  < ");

        // 校验PDA前端传来的参数
        this.validate(asyncMsg, param);

        // 记录日志
        String pdaParamStr = JSONObject.toJSONString(param);
        log.info("PDA前端传参：" + pdaParamStr);

        // 封装支付数据
        AlipayTradePayRequestBuilder builder = this.getBuilder(param);

        // 记录日志
        String builderStr = JSONObject.toJSONString(builder);
        log.info("BDM调用支付宝扫码支付接口传参：" + builderStr);

        // 调用支付宝当面付2.0接口(单例)、获取当面付应答(轮询查询)
        AlipayF2FPayResult result = tradeService.tradePay(builder);

        // 记录日志
        String responseStr = JSONObject.toJSONString(result.getResponse());
        log.info("支付宝扫码支付返回信息：" + responseStr);

        // 返回支付结果给PDA前端
        switch (result.getTradeStatus()) {
        case SUCCESS:
            break;
        case FAILED:
            String subMsg = result.getResponse().getSubMsg();
            log.error("支付宝支付失败:" + subMsg);
            throw new AliPayWithScanCodeException(null, "支付宝返回失败信息:" + subMsg);
        case UNKNOWN:
            log.error("系统异常，订单状态未知");
            throw new AliPayWithScanCodeException(null, "支付宝返回失败信息:系统异常，订单状态未知");
        default:
            log.error("不支持的交易状态，交易返回异常");
            throw new AliPayWithScanCodeException(null, "支付宝返回失败信息:不支持的交易状态，交易返回异常");
        }

        // try {
        // 若成功,封装推送给FOSS进行核销的数据,并根据模块判断是对账还是结清货款
        VerificationEntity verificationEntity = this.wrapResultMsg2Foss(result.getResponse(), param);

        // 订单所属模块（用于判断是对账，还是结清货款）
        String belongModule = param.getGetPaymentSuccessEntitys().get(0).getBelongModule();

        // 记录日志
        String verificationStr = JSONObject.toJSONString(verificationEntity);
        log.info("模块：" + belongModule + " == BDM推送支付结果给FOSS进行核销传参：" + verificationStr);

        // 推送数据到FOSS进行核销
        fixedThreadPool.execute(new ThreadForPush2Foss4BoaOrSettlement(verificationEntity,belongModule,pdaParamStr));
       
        /*
         * } catch (Exception e) { log.error("支付成功,核销出现未知异常：" + e.getClass().toString()); throw new
         * FossInterfaceException(null, "支付成功,核销出现未知异常"); }
         */

        log.info("BDM == 支付宝扫码支付  ==> 结束  < ");
        return null;
    }

    private AlipayTradePayRequestBuilder getBuilder(SettlementOfPaymentSuccessEntity param) {

        GetPaymentSuccessEntitys getPaymentSuccessEntitys = param.getGetPaymentSuccessEntitys().get(0);
        GetPaymentDeatilSuccessEntity getPaymentDeatilSuccessEntity = getPaymentSuccessEntitys
                .getGetPaymentDeatilSuccessEntity().get(0);

        // our_trade_no——>POS机各模块发起请求是生成的商户订单号
        String outTradeNo = getPaymentDeatilSuccessEntity.getInvoiceNo();

        // 订单标题 ——>德邦 +单据号
        String subject = "德邦" + outTradeNo;

        // 订单总金额
        String totalAmount = String.valueOf(getPaymentSuccessEntitys.getSerialAmount());

        // 付款条码——>用户支付宝钱包手机app点击“付款”产生的付款条码
        String authCode = getPaymentSuccessEntitys.getTradeSerialNo();

        // 订单描述——>德邦+单据号+部门名称
        String body = subject + getPaymentSuccessEntitys.getCardDeptName();

        // 商户操作员编号 ——>pos机登录人工号
        String operatorId = getPaymentSuccessEntitys.getCreateUserCode();

        // 商户门店编号 ——>foss系统部门标杆编码
        String storeId = getPaymentSuccessEntitys.getCardDeptCode();

        // 德邦PID
        String sellerId = Configs.getPid();

        // 创建条码支付请求builder，设置请求参数
        AlipayTradePayRequestBuilder builder = new AlipayTradePayRequestBuilder().setAppAuthToken(null)
                .setOutTradeNo(outTradeNo).setSubject(subject).setAuthCode(authCode)
                .setTotalAmount(totalAmount).setStoreId(storeId).setBody(body).setOperatorId(operatorId)
                .setSellerId(sellerId).setTimeoutExpress(timeoutExpress);
        return builder;
    }

    /**
     * 推送支付宝扫码支付结果到FOSS，进行对账或结清货款
     * 
     * @param belongModule
     * @param verificationEntity
     * @throws BusinessException
     */
    private void push2Foss4BoaOrSettlement(String belongModule, VerificationEntity verificationEntity,String pdaParamStr)
            throws BusinessException {
        try {
            Boolean isSettleCredit = settleCredit.equals(belongModule);
            Boolean isAccountStatement = accountStatement.equals(belongModule);
            long startTime = System.currentTimeMillis();
            //CUBC灰度实体
            RequestDO requestDo=new RequestDO();
            String[] versionNos = new String[1];
			versionNos[0]=verificationEntity.getOutTradeNo();
			//运单号
			requestDo.setSourceBillNos(versionNos);
			//结清货款传运单
			if(isSettleCredit){
			   //来源单据类型 --运单号
			   requestDo.setSourceBillType("W");
			}else if(isAccountStatement){  //对账单传对账单号
			   //来源单据类型 --对账单号
			   requestDo.setSourceBillType("DZ");
			}
			//请求ID--时间戳
			requestDo.setRequestId(System.currentTimeMillis());
			//服务编码--报名+类名+方法名
			requestDo.setServiceCode("com.deppon.pda.bdm.module.foss.delivery.server.service.impl.nci.AliPayWithScanCodeService.push2Foss4BoaOrSettlement");
			//来源系统
			requestDo.setOrigin("PDA");
			//根据运单号调用归属服务
			String gsService=pdaToCubcService.cubcHuiduQuery(requestDo);
			if(!StringUtils.isBlank(gsService)){
				   VestResponse vestResponse=JSON.parseObject(gsService,VestResponse.class);
				   //由于返回的是个list,需要用到循环
				   List<VestBatchResult> vestBatchResult=vestResponse.getVestBatchResult();
				   for (int i = 0; i < vestBatchResult.size(); i++) {
						// 归属系统编码
						String cubcOrFoss = vestBatchResult.get(i).getVestSystemCode();
						if (cubcOrFoss.equals(DeryConstant.GS_CUBC)) {
							 // 结清货款
				            if (isSettleCredit) {
				            	//zfbCodeRepaymentService.zfbCodeWrietoff(verificationEntity);
				            	String type="isSettleCredit";
				            	//封装支付宝条码信息
				            	PayInfoDO dto=settlementPdaToCubcService.setVerificationPosCard(verificationEntity,type);
				            	//支付宝数据传给CUBC
				            	String responseStr=pdaToCubcService.getAccountStatementSuccess(dto);
				            	PayInfoDO payInfo = JSON.parseObject(responseStr,PayInfoDO.class);
								String isSuccess=payInfo.getIsSuccess();
								if(isSuccess!=null && isSuccess.equals("flase")){
						          //CUBC返回false,保存到异常表
								  //savePosCardDataCubc(getBushCardEntitys,payInfo.getErrorMessage(),asyncMsg.getContent());
						          throw new FossInterfaceException(null,"支付宝结清货款到CUBC失败!");
								}
				            }
				
				            // 对账单
				            else if (isAccountStatement) {
				            	//对账单调用CUBC接口
				                //zfbStatementManageService.statementRepaymentwriteoff(verificationEntity);
				            	String type="isAccountStatement";
				            	//封装支付宝条码信息
				            	PayInfoDO dto=settlementPdaToCubcService.setVerificationPosCard(verificationEntity,type);
				            	//支付宝数据传给CUBC
				            	String responseStr=pdaToCubcService.getAccountStatementSuccess(dto);
				            	PayInfoDO payInfo = JSON.parseObject(responseStr,PayInfoDO.class);
								String isSuccess=payInfo.getIsSuccess();
								if(isSuccess!=null && isSuccess.equals("flase")){
						          //CUBC返回false,保存到异常表
								  //savePosCardDataCubc(getBushCardEntitys,payInfo.getErrorMessage(),asyncMsg.getContent());
						          throw new FossInterfaceException(null,"支付宝对账单到CUBC失败!");
								}
				            }
						}else{
							 // 结清货款
				            if (isSettleCredit) {
				            	zfbCodeRepaymentService.zfbCodeWrietoff(verificationEntity);
				            }
				
				            // 对账单
				            else if (isAccountStatement) {
				                zfbStatementManageService.statementRepaymentwriteoff(verificationEntity);
				            }
				
				            // 非法业务类型
				            else {
				                throw new BusinessException(null, "非法业务类型:" + belongModule);
				            }
						}
				   }
						
			}
				   
            long endTime = System.currentTimeMillis();
            QueueMonitorInfo.addTotalFossTime(endTime - startTime);

            log.info("PDA后台推送扫码支付结果到FOSS核销接口消耗时间:" + (endTime - startTime) + "ms");
        } catch (BusinessException e) {
            // throw new FossInterfaceException(e.getCause(), e.getErrorCode());
        	//将报错信息保存（by:218371-foss-zhaoyanjun）
        	if(verificationEntity!=null){
        		AccountStatementEntitys accountStatementEntitys=new AccountStatementEntitys();
            	accountStatementEntitys.setBelongModule(belongModule);
            	accountStatementEntitys.setTradeSerialNo(verificationEntity.getTradeNo());
            	accountStatementEntitys.setSerialAmount(verificationEntity.getTotalAmount());
            	accountStatementEntitys.setOperateTime(new Date());
            	accountStatementEntitys.setCardDeptCode(verificationEntity.getOrgCode());
            	accountStatementEntitys.setErrorCause(e.getMessage());
            	accountStatementEntitys.setContent(pdaParamStr);
            	deliveryPdaDao.saveNCIPaymentCard(accountStatementEntitys);
        	}
        }
    }

    private VerificationEntity wrapResultMsg2Foss(AlipayTradePayResponse response,
            SettlementOfPaymentSuccessEntity param) {
        VerificationEntity entity = new VerificationEntity();
        GetPaymentSuccessEntitys getPaymentSuccessEntitys = param.getGetPaymentSuccessEntitys().get(0);

        entity.setEmpCode(getPaymentSuccessEntitys.getCreateUserCode());// 员工工号
        entity.setEmpName(getPaymentSuccessEntitys.getCreateUserName());// 员工姓名
        entity.setOrgCode(getPaymentSuccessEntitys.getCardDeptCode());// 登录部门编码
        entity.setOrgName(getPaymentSuccessEntitys.getCardDeptName());// 登录部门名称

        entity.setTradeNo(response.getTradeNo());// 支付宝交易账号
        entity.setTotalAmount(new BigDecimal(response.getTotalAmount()));// 交易金额
        entity.setOutTradeNo(response.getOutTradeNo());// 运单号或对账单号
        entity.setBuyerUserId(response.getBuyerUserId());// 买家在支付宝的用户id
        entity.setPayType(alipayWithScanCode);// 支付类型(条码支付)
        entity.setCollectionAccount(response.getTradeNo());// 收款账户

        return entity;
    }

    /**
     * 
     * <p>
     * TODO(方法详细描述说明、方法参数的具体涵义)
     * </p>
     * 
     * @author ganxiaojian
     * @date 2016-10-12 上午9:45:14
     * @param entity
     * @see
     */
    private void validate(AsyncMsg asyncMsg, SettlementOfPaymentSuccessEntity entity) {
        Argument.notNull(asyncMsg, "AsyncMsg");
        Argument.notNull(entity, "GetBushCardSuccessEntity");
        Argument.notEmpty(entity.getGetPaymentSuccessEntitys(), "entity.getPaymentSuccessEntitys");
    }

    @Override
    public String getOperType() {
        return DeliveryConstant.OPER_TYPE_ALIPAY_SCAN.VERSION;
    }

    @Override
    public boolean isAsync() {
        return false;
    }
    
    //为支付宝传参(By:218371-foss-zhaoyanjun)
    class ThreadForPush2Foss4BoaOrSettlement extends Thread{
    	VerificationEntity verificationEntity;
    	String belongModule;
    	String pdaParamStr;
    	
    	public ThreadForPush2Foss4BoaOrSettlement(VerificationEntity verificationEntity,String belongModule,String pdaParamStr){
    		this.verificationEntity=verificationEntity;
    		this.belongModule=belongModule;
    		this.pdaParamStr=pdaParamStr;
    	}
    	
    	@Override
    	public void run() {
    		// TODO Auto-generated method stub
    		super.run();
    		push2Foss4BoaOrSettlement(belongModule,verificationEntity,pdaParamStr);
    	}
    }

	public IDeliveryPdaDao getDeliveryPdaDao() {
		return deliveryPdaDao;
	}

	public void setDeliveryPdaDao(IDeliveryPdaDao deliveryPdaDao) {
		this.deliveryPdaDao = deliveryPdaDao;
	}
}
