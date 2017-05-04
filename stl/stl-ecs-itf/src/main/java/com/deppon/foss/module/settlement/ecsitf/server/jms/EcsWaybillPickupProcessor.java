package com.deppon.foss.module.settlement.ecsitf.server.jms;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.ecs.EcsFossWaybillBillingRequest;
import com.deppon.esb.inteface.domain.ecs.EcsFossWaybillBillingResponse;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.settlement.closing.api.server.service.ILogEcsFossService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCEntity;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillPickupService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.util.define.FossConstants;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;

public class EcsWaybillPickupProcessor implements IProcess {

	//记录日志
	private static final Logger LOGGER = LoggerFactory.getLogger(EcsWaybillPickupProcessor.class);

	//成功返回1
	private static final String SUCCESS = "1";

	//失败返回0
	private static final String FAILURE = "0";
	
    //待刷卡运单类型新增为 1
    private static final String NEWWAYBILL = "1";
    //待刷卡运单类型更改为 2
    public static final String WAYBILLCHANGE = "2";
	
	//运单开单服务
	private IWaybillPickupService waybillPickupService;
	
	//运单管理接口
	//private IWaybillManagerService waybillManagerService;
    //代刷service
    //private IWSCManageService wscManageService;
	/**
	 * 日志
	 */
    private ILogEcsFossService logEcsFossService;
	/**
	 * 快递新增运单，生成财务单据接口
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-19 11:51
	 */
//    @Log(esbCode="FOSS_ESB2FOSS_ECS_WAYBILL_BILLING")
	@Override
	public Object process(Object request) throws ESBBusinessException {
		LOGGER.info("ECS-FOSS开单生成财务单据--start");
		Date date = new Date();
		//请求参数
		EcsFossWaybillBillingRequest req = (EcsFossWaybillBillingRequest)request;
		//响应参数
		EcsFossWaybillBillingResponse res = null;
		//响应状态1代表成功，0代表失败
		Boolean flag = null;
		//请求参数为空
		if(req == null){
			LOGGER.info("ECS-FOSS开单生成财务单据--end");
			res = this.getResponse(FAILURE, "结算生成财务单据失败：生成财务单据的运单信息为空！",null);
			flag = false;
		} else {
			try{
				if(StringUtils.isBlank(req.getEmpCode())
						|| StringUtils.isBlank(req.getEmpName())
						|| StringUtils.isBlank(req.getCurrentDeptCode())
						|| StringUtils.isBlank(req.getCurrentDeptName())){
					LOGGER.info(req.getWaybillNo() + "ECS-FOSS开单生成财务单据--end");
					res = this.getResponse(FAILURE, "结算生成财务单据失败：开单员信息为空！",StringUtils.isNotBlank(req.getNewWaybillNo())?req.getNewWaybillNo()+","+ req.getWaybillNo():req.getWaybillNo());
					flag = false;
				}else if(WaybillConstants.DEPPON_CUSTOMER.equals(req.getDeliveryCustomerCode())){
					//发货客户为内部带货时，不生成财务单据
					res = this.getResponse(SUCCESS, "该运单为内部带货，不生成财务单据！",StringUtils.isNotBlank(req.getNewWaybillNo())?req.getNewWaybillNo()+","+ req.getWaybillNo():req.getWaybillNo());
					flag = true;
				} else {
					// 设置运单开单信息DTO
					WaybillPickupInfoDto waybillDto = this.getNewWaybillDto(req);
					CurrentInfo currentInfo = SettlementUtil.getECSCurrentInfo(req.getEmpCode(), req.getEmpName(), req.getCurrentDeptCode(), req.getCurrentDeptName());
					// begin add by 309603 yangqiang 生成待刷卡单据 2016-06-24暂时先注释
                    WSCEntity wscEntity =null;
					if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(waybillDto.getPaidMethod())) {
                        // 开单银行卡
                        wscEntity = this.addWscEntityInfo(req, NEWWAYBILL);
                        wscEntity.setCreateUserCode(req.getEmpCode());
                        wscEntity.setCreateUserName(req.getEmpName());
                        wscEntity.setSerialNo(req.getBatchNo());
                    }
                    // end add by 309603 yangqiang
					if (FossConstants.YES.equals(waybillDto.getIsRedirect())) {
                        // 转寄时
                        waybillPickupService.ecsToChangeTermsPayment(wscEntity,waybillDto, null, currentInfo, true);
					} else {
						// 非转寄时，走原逻辑
						waybillPickupService.ecsAddWaybill(wscEntity,waybillDto, currentInfo);
					}
					res = this.getResponse(SUCCESS, "结算财务单据生成成功！", StringUtils.isNotBlank(req.getNewWaybillNo()) ? req.getNewWaybillNo() + "," + req.getWaybillNo() : req.getWaybillNo());
					flag = true;
				}
			}catch (BusinessException e){
				LOGGER.info(req.getWaybillNo() + "ECS-FOSS开单生成财务单据--end");
				res = this.getResponse(FAILURE, "结算生成财务单据失败：" +e.getErrorCode() != null ? e.getErrorCode() : e.getMessage(),StringUtils.isNotBlank(req.getNewWaybillNo())?req.getNewWaybillNo()+","+ req.getWaybillNo():req.getWaybillNo());
				flag = false;
			}catch (Exception e){
				LOGGER.info(req.getWaybillNo() + "ECS-FOSS开单生成财务单据--end");
				//取错误信息
				String tmp = e.getMessage();
				int index = tmp.toUpperCase().indexOf("ORA-");
				if (index != -1) {
					tmp = tmp.substring(index + SettlementReportNumber.FOUR, index + SettlementReportNumber.NINE);
				}
				//当错误编码为ORA-00001时，为oracle 主键重复异常 可能表示pod表已有数据，即该单已生成财务单据
				if("00001".equals(tmp)){
					res = this.getResponse(FAILURE, "结算生成财务单据失败:该运单已生成财务单据，不能重复生成！", StringUtils.isNotBlank(req.getNewWaybillNo())?req.getNewWaybillNo()+","+ req.getWaybillNo():req.getWaybillNo());
				}else{
					res = this.getResponse(FAILURE, "结算生成财务单据失败：系统异常，请重新操作，以生成财务单据！："+e,StringUtils.isNotBlank(req.getNewWaybillNo())?req.getNewWaybillNo()+","+ req.getWaybillNo():req.getWaybillNo());
				}
				flag = false;
			}
		}
		//记录异步接口日志
		try{
			logEcsFossService.setLog(req, res, SettlementDictionaryConstants.FOSS_ESB2FOSS_ECS_WAYBILL_BILLING, req.getWaybillNo(), flag, date);
		}catch(Exception e){
			LOGGER.info("记录开单异步接口日志失败："+e);
		}
		return res;
	}

    /**
     * 设置待刷卡单据参数
     * @param req
     * @param wayBillSource
     * @return
     */
    private WSCEntity addWscEntityInfo(EcsFossWaybillBillingRequest req,String wayBillSource) {
        WSCEntity wsc = new WSCEntity();
        wsc.setWayBillNo(req.getWaybillNo());
        wsc.setWayBillSource(wayBillSource);
        wsc.setSendCustomerCode(req.getDeliveryCustomerCode());
        wsc.setSendCustomerName(req.getDeliveryCustomerName());
        wsc.setCreateBillOrgCode(req.getCreateOrgCode());
        wsc.setCreateBillOrgName(req.getCreateUserDeptName());
        wsc.setCreateBillTime(req.getBillTime());
        wsc.setWayBillAmount(new Double(req.getTotalFee().toString()));
        wsc.setWaitSwipeAmount(new Double(req.getPrePayAmount().toString()));
        return wsc;
    }
	/**
	 * 获取运单开单信息dto
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-19 17:48
	 */
	private WaybillPickupInfoDto getNewWaybillDto(EcsFossWaybillBillingRequest req){
		//查询运单，若能查到，则用运单表的id，否则用悟空传的id
//		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(req.getWaybillNo());
//		
//		if(waybillEntity != null){
//			req.setId(waybillEntity.getId());
//		}
		WaybillPickupInfoDto waybillDto = new WaybillPickupInfoDto();
		//运单id
		waybillDto.setId(req.getId());
		//运单号
		waybillDto.setWaybillNo(req.getWaybillNo());
		//产品ID
		waybillDto.setProductId(req.getProductId());
		//运输性质
		waybillDto.setProductCode(req.getProductCode());
		//发票标记(悟空系统如果没有发票标记，就默认INVOICE_TYPE_02)
		waybillDto.setInvoiceMark(StringUtils.isBlank(req.getInvoiceMark())
									? SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO
									: req.getInvoiceMark());
		//开单部门
		waybillDto.setCreateOrgCode(req.getCreateOrgCode());
		//运单提交人所在部门名称
		waybillDto.setCreateUserDeptName(req.getCreateUserDeptName());
		//收货部门(出发部门)
		waybillDto.setReceiveOrgCode(req.getReceiveOrgCode());
		//出发部门名称
		waybillDto.setReceiveOrgName(req.getReceiveOrgName());
		//最终配载部门
		waybillDto.setLastLoadOrgCode(req.getLastLoadOrgCode());
		//到达部门名称
		waybillDto.setLastLoadOrgName(req.getLastLoadOrgName());
		//提货网点
		waybillDto.setCustomerPickupOrgCode(req.getCustomerPickupOrgCode());
		//目的站
		waybillDto.setTargetOrgCode(req.getTargetOrgCode());
		//更新组织
		waybillDto.setModifyOrgCode(req.getModifyOrgCode());
		//发货客户编码
		waybillDto.setDeliveryCustomerCode(req.getDeliveryCustomerCode());
		//发货客户名称
		waybillDto.setDeliveryCustomerName(req.getDeliveryCustomerName());
		//发货客户手机
		waybillDto.setDeliveryCustomerMobilephone(req.getDeliveryCustomerMobilephone());
		//发货客户电话
		waybillDto.setDeliveryCustomerPhone(req.getDeliveryCustomerPhone());
		//发货客户联系人
		waybillDto.setDeliveryCustomerContact(req.getDeliveryCustomerContact());
		//收货客户编码
		waybillDto.setReceiveCustomerCode(req.getReceiveCustomerCode());
		//收货客户联系人
		waybillDto.setReceiveCustomerContact(req.getReceiveCustomerContact());
		//收货客户手机
		waybillDto.setReceiveCustomerMobilephone(req.getReceiveCustomerMobilephone());
		//提货方式
		waybillDto.setReceiveMethod(req.getReceiveMethod());
		//货物名称
		waybillDto.setGoodsName(req.getGoodsName());
		//货物总件数
		waybillDto.setGoodsQtyTotal(req.getGoodsQtyTotal());
		//货物总体积
		waybillDto.setGoodsVolumeTotal(req.getGoodsVolumeTotal());
		//计费重量
		waybillDto.setBillWeight(req.getBillWeight());
		//货物总重量
		waybillDto.setGoodsWeightTotal(req.getGoodsWeightTotal());
		//开单付款方式
		waybillDto.setPaidMethod(req.getPaidMethod());
		//退款类型
		waybillDto.setRefundType(req.getRefundType());
		//总费用
		waybillDto.setTotalFee(req.getTotalFee());
		//预付金额
		waybillDto.setPrePayAmount(req.getPrePayAmount());
		//到付金额
		waybillDto.setToPayAmount(req.getToPayAmount());
		//代收货款
		waybillDto.setCodAmount(req.getCodAmount());
		//公布价运费(加上转寄费)
		waybillDto.setTransportFee(req.getTransportFee().add(req.getIsRedirectFee() == null ? new BigDecimal(0) : req.getIsRedirectFee()));
		//接货费
		waybillDto.setPickupFee(req.getPickupFee());
		//送货费
		waybillDto.setDeliveryGoodsFee(req.getDeliveryGoodsFee());
		//包装手续费
		waybillDto.setPackageFee(req.getPackageFee());
		//代收货款手续费
		waybillDto.setCodFee(req.getCodFee());
		//保价费
		waybillDto.setInsuranceFee(req.getInsuranceFee());
		//其他费用
		waybillDto.setOtherFee(req.getOtherFee());
		//增值费用
		waybillDto.setValueAddFee(req.getValueAddFee());
		//优惠费用
		waybillDto.setPromotionsFee(req.getPromotionsFee());
		//装卸费
		waybillDto.setServiceFee(req.getServiceFee());
		//币种
		waybillDto.setCurrencyCode(req.getCurrencyCode());
		//是否集中接货(Y:是)
		waybillDto.setPickupCentralized(req.getPickupCentralized());
		//开单时间
		waybillDto.setBillTime(req.getBillTime());
		//返款帐户开户账户
		waybillDto.setAccountCode(req.getAccountCode());
		//返款帐户开户名称
		waybillDto.setAccountName(req.getAccountName());
		//返款帐户开户银行
		waybillDto.setAccountBank(req.getAccountBank());
		//始发统一结算
		waybillDto.setOrigUnifiedSettlement(req.getOrigUnifiedSettlement());
		//到达统一结算
		waybillDto.setDestUnifiedSettlement(req.getDestUnifiedSettlement());
		//出发统一结算合同部门标杆编码
		waybillDto.setOrigContractUnifiedCode(req.getOrigContractUnifiedCode());
		//到达统一结算合同部门标杆编码
		waybillDto.setDestContractUnifiedCode(req.getDestContractUnifiedCode());
		//出发催款部门标杆编码
		waybillDto.setOrigUnifiedDuningCode(req.getOrigUnifiedDuningCode());
		//到达催款部门标杆编码
		waybillDto.setDestUnifiedDuningCode(req.getDestUnifiedDuningCode());
		//开户行编码
		waybillDto.setBankHQCode(req.getBankHQCode());
		//开户行名称
		waybillDto.setBankHQName(req.getBankHQName());
		//省份编码
		waybillDto.setProvinceCode(req.getProvinceCode());
		//省份名称
		waybillDto.setProvinceName(req.getProvinceName());
		//城市编码
		waybillDto.setCityCode(req.getCityCode());
		//城市名称
		waybillDto.setCityName(req.getCityName());
		//支行编码（行号）
		waybillDto.setBankBranchCode(req.getBankBranchCode());
		//支行名称
		waybillDto.setBankBranchName(req.getBankBranchName());
		//对公对私标志 对公:PUBLIC_ACCOUNT 对私:PRIVATE_ACCOUNT
		waybillDto.setPublicPrivateFlag(req.getPublicPrivateFlag());
		//收款人与发货人关系
		waybillDto.setPayeeRelationship(req.getPayeeRelationship());
		//收款人手机号码
		waybillDto.setPayeePhone(req.getPayeePhone());
		//结算专用：是否需要查询快递点部（自有的、自有的非外场） Y:代理网点，需要查询	N:自有网点，无需查询
		waybillDto.setIsSelfStation(req.getIsSelfStation());
		//POS串号
		waybillDto.setPosSerialNum(req.getPosSerialNum());
		//银行交易流水号
		waybillDto.setBatchNo(req.getBatchNo());
		//快递员工号
		waybillDto.setExpressEmpCode(req.getExpressEmpCode());
		//快递员姓名
		waybillDto.setExpressEmpName(req.getExpressEmpName());
		//pda开单时间
		waybillDto.setPdaBillTime(req.getPdaBillTime());
		//是否转寄
		waybillDto.setIsRedirect(req.getIsRedirect());
		//中转费（转寄费）
		waybillDto.setIsRedirectFee(req.getIsRedirectFee());
		//预付现金(始发现金)
		waybillDto.setPrePayAmountCH(req.getPrePayAmountCH());
		//预付月结(始发月结)
		waybillDto.setPrePayAmountCT(req.getPrePayAmountCT());
		//是否快递(快递员卡需求，需要该字段)
		waybillDto.setIsExpress(FossConstants.YES);
		//来源系统(快递系统)
		waybillDto.setSourceSystem(SettlementDictionaryConstants.SOURCE_SYSTEM_ECS);
		//转寄新单付款方式
		waybillDto.setRedirectPaidMethod(req.getRedirectPaidMethod());
		//是否裹裹运单
		waybillDto.setIsWrap(req.getIsWrap());
		//到达部门是否PTP
		waybillDto.setIsPtp(req.getIsPtp());
		return waybillDto;
	}
	
	/**
	 * 获取响应信息
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-20 17:27
	 */
	private EcsFossWaybillBillingResponse getResponse(String result,String message,String waybillNo){
		EcsFossWaybillBillingResponse res = new EcsFossWaybillBillingResponse();
		
		res.setResult(result);
		res.setMessage(message);
		res.setData(waybillNo);
		LOGGER.info(waybillNo + "ECS-FOSS开单生成财务单据--end");
		return res;
	}
	
	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		LOGGER.info("开单异常");
		return null;
	}

	/*public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}*/

	public void setWaybillPickupService(IWaybillPickupService waybillPickupService) {
		this.waybillPickupService = waybillPickupService;
	}

	public void setLogEcsFossService(ILogEcsFossService logEcsFossService) {
		this.logEcsFossService = logEcsFossService;
	}

    /*public void setWscManageService(IWSCManageService wscManageService) {
        this.wscManageService = wscManageService;
    }*/
}
