package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.deppon.esb.inteface.domain.ecs.EcsFossComplementRequest;
import com.deppon.esb.inteface.domain.ecs.EcsFossWaybillBillingRequest;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.settlement.agency.api.server.service.IComplementFunctionService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.SettlementComplementBillDto;
import com.deppon.foss.module.settlement.closing.api.server.dao.IWaybillCommonDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IEcsFossExecuteErrorLogService;
import com.deppon.foss.module.settlement.closing.api.server.service.IEcsSendWaybillService;
import com.deppon.foss.module.settlement.closing.api.server.service.ILogEcsFossService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MqLogEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.WayBillRequest;
import com.deppon.foss.module.settlement.closing.api.shared.inteface.domain.EcsFossWaybillPickupRequest;
import com.deppon.foss.module.settlement.common.api.server.service.IWSCManageService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillPickupService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 异常任务查询表接口
 * @author 326181
 *
 */
public class EcsFossExecuteErrorLogService implements IEcsFossExecuteErrorLogService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EcsFossExecuteErrorLogService.class);
	
	//待刷卡运单类型新增为 1
    private static final String NEWWAYBILL = "1";
    //待刷卡运单类型更改为 2
  	private static final String WAYBILLCHANGE = "2";
	
	private IEcsSendWaybillService ecsSendWaybillService;
	
	private IComplementFunctionService complementFunctionService;
	
	private IWaybillPickupService waybillPickupService;
	
	private ILogEcsFossService logEcsFossService;
	
	private IWaybillCommonDao waybillCommonDao;
	
	 //代刷service
    private IWSCManageService wscManageService;
    
	@Transactional
	@Override
	public void doExecuteLogReqMsg(MqLogEntity log) {
		String esbCode = log.getEsbCode();
		String reqMsg = log.getRequestMsg();
		if (SettlementDictionaryConstants.FOSS_ESB2FOSS_ECS_WAYBILL_BILLING
				.equals(esbCode)) {// 开单
			ecsBilling(reqMsg);
		} else if (SettlementDictionaryConstants.FOSS_ESB2FOSS_COMPLEMENT_FUNCTION
				.equals(esbCode)) {// 补码
			ecsComplementFunction(reqMsg);
		} else if (SettlementDictionaryConstants.FOSS_ESB2FOSS_ECS_SYNC_WAYBILL
				.equals(esbCode)) {// 同步运单
			this.doExecuteLogRequestMsg(log, true);
		} else if (SettlementDictionaryConstants.FOSS_ESB2FOSS_ECS_SYNC_SIGNRESULT
				.equals(esbCode)) {// 同步签收
			this.doExecuteLogRequestMsg(log, false);
		} else {//更改
			this.modifiAndCancle(reqMsg);
		}
		this.logEcsFossService.doUpdateLogEntity(log);
	}
	
	/**
	 * 重发异常日志请求参数
	 * @param logList
	 */
	@Transactional
	public void doExecuteLogRequestMsg(MqLogEntity log, boolean flag) {
		String resMsg = log.getRequestMsg();
		if (StringUtils.isBlank(resMsg)) {
			throw new SettlementException("请求参数不能为空！");
		}
		WayBillRequest request = null;
		if (flag) {
			request = JSONObject.parseObject(resMsg, WayBillRequest.class);
			this.ecsSendWaybillService.doSynchroWaybill(request, true);
		} else {
			this.updateOrInsertSignFor(resMsg);
		}
	}
	
	public void updateOrInsertSignFor(String resMsg) {
		WaybillSignResultEntity waybillSignResultEntity=JSONObject.parseObject(resMsg,WaybillSignResultEntity.class);
		// 签收件数设置为固定值
		waybillSignResultEntity.setSignGoodsQty(1);
		int count = 0;
		// 判断数据库中有没有这条信息
		count = waybillCommonDao.selectWaybillSignResult(waybillSignResultEntity);
		if (count == 2) {
			throw new SettlementException(waybillSignResultEntity.getWaybillNo() + ":数据库中有多条重复的信息");
		}
		// 有则更新无则插入
		if (count == 1) {
			waybillCommonDao.updateWaybill(waybillSignResultEntity);
		} else if (count == 0) {	
			//传过来的对象主键不能用
			waybillSignResultEntity.setId(UUIDUtils.getUUID());
			//生效时间必填，不能为空
			if(waybillSignResultEntity.getCreateTime()==null){
				waybillSignResultEntity.setCreateTime(new Date());
			}
			waybillCommonDao.addWaybillSignResult(waybillSignResultEntity);
		}
	}
	
	/**
	 * 执行补码异常请求参数
	 * @author 326181
	 * @param reqMsg
	 */
	private void ecsComplementFunction(String reqMsg) {
		EcsFossComplementRequest req = JSONObject.parseObject(reqMsg, EcsFossComplementRequest.class);
		if(req == null){
			LOGGER.info("ECS-FOSS补码--endt");
			throw new SettlementException("结算补码失败：补码参数为空！");
		} else {
			if(StringUtils.isBlank(req.getEmpCode())
					|| StringUtils.isBlank(req.getEmpName())
					|| StringUtils.isBlank(req.getCurrentDeptCode())
					|| StringUtils.isBlank(req.getCurrentDeptName())){
				LOGGER.info(req.getWaybillNo() + "ECS-FOSS补码--end");
				throw new SettlementException("结算补码失败：补码员信息为空！");
			} else {
				//设置补码信息
				SettlementComplementBillDto dto = this.getComplementDto(req);
				CurrentInfo currentInfo = SettlementUtil.getECSCurrentInfo(req.getEmpCode(), req.getEmpName(), req.getCurrentDeptCode(), req.getCurrentDeptName());
				try{
					//补码
					complementFunctionService.ecsComplementFunctionWriteBackAndCreateReceivable(dto, currentInfo);
					LOGGER.info(req.getWaybillNo() + "ECS-FOSS补码--end");
				}catch(BusinessException e){
					LOGGER.info(req.getWaybillNo() + "ECS-FOSS补码--end");
					throw new SettlementException("结算补码失败：" +e.getErrorCode() != null ? e.getErrorCode() : e.getMessage());
				}catch(Exception e){
					LOGGER.info(req.getWaybillNo() + "ECS-FOSS补码--end");
					throw new SettlementException("结算补码失败：系统异常，请重新操作，以校验财务单据！：" + e.getMessage());
				}
			}
		}
		//修改运单到达部门信息
		ecsSendWaybillService.updateWaybillByComplement(req);
	}

	/**
	 * 执行开单异常请求参数
	 * @author 326181
	 * @param reqMsg
	 */
	private void ecsBilling(String reqMsg) {
		EcsFossWaybillBillingRequest req = JSONObject.parseObject(reqMsg, EcsFossWaybillBillingRequest.class);
		//请求参数为空
		if(req == null){
			throw new SettlementException("结算生成财务单据失败：生成财务单据的运单信息为空！");
		} else {
			try{
				if(StringUtils.isBlank(req.getEmpCode())
						|| StringUtils.isBlank(req.getEmpName())
						|| StringUtils.isBlank(req.getCurrentDeptCode())
						|| StringUtils.isBlank(req.getCurrentDeptName())){
					LOGGER.info(req.getWaybillNo() + "ECS-FOSS开单生成财务单据--end");
					throw new SettlementException("开单员信息为空！");

				}else if(WaybillConstants.DEPPON_CUSTOMER.equals(req.getDeliveryCustomerCode())){
					//发货客户为内部带货时，不生成财务单据
					throw new SettlementException("该运单为内部带货，不生成财务单据！");
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
				}
			}catch (BusinessException e){
				LOGGER.info(req.getWaybillNo() + "ECS-FOSS开单生成财务单据--end");
				throw new SettlementException("结算生成财务单据失败：" +e.getErrorCode() != null ? e.getErrorCode() : e.getMessage(),StringUtils.isNotBlank(req.getNewWaybillNo())?req.getNewWaybillNo()+","+ req.getWaybillNo():req.getWaybillNo());
			}catch (Exception e){
				LOGGER.info(req.getWaybillNo() + "ECS-FOSS开单生成财务单据--end");
				throw new SettlementException("结算生成财务单据失败：系统异常，请重新操作，以生成财务单据！："+e,StringUtils.isNotBlank(req.getNewWaybillNo())?req.getNewWaybillNo()+","+ req.getWaybillNo():req.getWaybillNo());
			}
		}
	}

	/**
	 * 获取补码参数
	 * 
	 * @author foss-231434-bieyexiong
	 * @date 2016-5-31 10:30
	 */
	private SettlementComplementBillDto getComplementDto(
			EcsFossComplementRequest req) {
		SettlementComplementBillDto dto = new SettlementComplementBillDto();

		// 运单号
		dto.setWaybillNo(req.getWaybillNo());
		// 最新提货网点的到达部门编码
		dto.setDestOrgCode(req.getDestOrgCode());
		// 最新提货网点的到达部门名称
		dto.setDestOrgName(req.getDestOrgName());
		// 用于区分自由网点补码，还是快递代理补码 自由网点 Y 快递代理 N
		dto.setIsFreeSite(req.getIsFreeSite());
		// 开单时间
		dto.setBillTime(req.getBillTime());
		// 收货部门
		dto.setReceiveOrgCode(req.getReceiveOrgCode());
		return dto;
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
		waybillDto.setTransportFee(req.getTransportFee().add(req.getIsRedirectFee() == null ? BigDecimal.ZERO : req.getIsRedirectFee()));
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
		
		return waybillDto;
	}
	
	/**
	 * 更改
	 * @param jsonReq
	 */
	public void modifiAndCancle(String jsonReq) {
		//将请求参数转换成实体对象
		EcsFossWaybillPickupRequest req = JSONObject.parseObject(jsonReq, EcsFossWaybillPickupRequest.class);
		try{
			//当请求参数为null时
			if(req == null){
				throw new SettlementException("运单信息为空！");
			}else if(StringUtils.isBlank(req.getEmpCode())
					|| StringUtils.isBlank(req.getEmpName())
					|| StringUtils.isBlank(req.getCurrentDeptCode())
					|| StringUtils.isBlank(req.getCurrentDeptName())){
				throw new SettlementException("操作人信息为空！");
			}else {
                //设置运单开单信息DTO
                WaybillPickupInfoDto newWaybill = this.getNewWaybillDto(req);
                //设置运单更改单信息
                WaybillPickupInfoDto oldWaybill = this.getOldWaybillDto(req);
                CurrentInfo currentInfo = SettlementUtil.getECSCurrentInfo(req.getEmpCode(), req.getEmpName(), req.getCurrentDeptCode(), req.getCurrentDeptName());
                //begin add by  309603 yangqiang 暂时先注释
                String isChange = req.getIsChange();
                try {
                    boolean b = FossConstants.YES.equals(isChange);
                    //boolean oldChange = SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(req.getOldPaidMethod());
                    boolean newChange = SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(req.getPaidMethod());
                    //非银行卡改银行卡
                    WSCEntity entity = this.addWscEntityInfo(req, WAYBILLCHANGE);
                    entity.setModifyTime(new Date());
                    entity.setModifyUserCode(req.getEmpCode());
                    entity.setModifyUserName(req.getEmpName());
                    //银行卡改非银行卡
                    if (!newChange||!b) {
                        entity.setWaitSwipeAmount(new Double(0.0));
                    }
                    wscManageService.changeWayBill(entity);
                } catch (Exception e) {
                    try {
                        logEcsFossService.setLog(e.getMessage(), e.getStackTrace(), "CD错误", newWaybill.getWaybillNo(), false, null);
                    } catch (Exception e1) {
                    }
                }
                //end  add by  309603 yangqiang
                if (FossConstants.YES.equals(newWaybill.getIsRedirect())) {
                    //转寄时(转寄的更改和作废 等同于转寄开新单，将原单据红冲，生成新单据；转寄作废只能作废新单)
                  //  ecsWaybillPickupService.toChangeTermsPayment(newWaybill, oldWaybill, currentInfo, false);
                } else {
                    //非转寄时，走原逻辑
                    //this.modifiAndCancle(, oldWaybill, newWaybill, currentInfo);
                    if(FossConstants.YES.equals(isChange)){
            			waybillPickupService.modifyWaybill(oldWaybill, newWaybill, currentInfo);
            			//return "结算运单更改成功！";
            		}else{
            			waybillPickupService.cancelWaybill(oldWaybill.getWaybillNo(), currentInfo);
            			//return "结算运单作废成功！";
            		}
                }
            }
        }catch(BusinessException e){
			throw new SettlementException("结算运单更改或作废失败：" + (e.getErrorCode() != null ? e.getErrorCode() : e.getMessage()));
		}catch(Exception e){
			throw new SettlementException("结算运单更改或作废失败：系统异常，请重新操作，以校验财务单据！："+e);
		}
		
	}
	
	/**
	 * 获取运单开单信息dto
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-19 17:48
	 */
	private WaybillPickupInfoDto getNewWaybillDto(EcsFossWaybillPickupRequest req){
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
		
		return waybillDto;
	}

	/**
	 * 获取运单更改单信息dto
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-20 9:11
	 */
	private WaybillPickupInfoDto getOldWaybillDto(EcsFossWaybillPickupRequest req){
		WaybillPickupInfoDto waybillDto = new WaybillPickupInfoDto();
		
		//运单号
		waybillDto.setWaybillNo(req.getOldWaybillNo());
		//更改/作废前运输性质
		waybillDto.setProductCode(req.getOldProductCode());
		//更改/作废前开单部门
		waybillDto.setCreateOrgCode(req.getOldCreateOrgCode());
		//更改/作废前收货部门(出发部门)
		waybillDto.setReceiveOrgCode(req.getOldReceiveOrgCode());
		//更改/作废前开单付款方式
		waybillDto.setPaidMethod(req.getOldPaidMethod());
		//更改/作废前预付金额
		waybillDto.setPrePayAmount(req.getOldPrePayAmount());
		
		return waybillDto;
	}
	
	/**
     * 设置待刷卡单据参数
     * @param req
     * @param wayBillSource
     * @return
     */
    private WSCEntity addWscEntityInfo(EcsFossWaybillPickupRequest req,String wayBillSource) {
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
	
	public void setEcsSendWaybillService(
			IEcsSendWaybillService ecsSendWaybillService) {
		this.ecsSendWaybillService = ecsSendWaybillService;
	}
	
	public void setComplementFunctionService(
			IComplementFunctionService complementFunctionService) {
		this.complementFunctionService = complementFunctionService;
	}

	public void setWaybillPickupService(IWaybillPickupService waybillPickupService) {
		this.waybillPickupService = waybillPickupService;
	}
	
	public void setLogEcsFossService(ILogEcsFossService logEcsFossService) {
		this.logEcsFossService = logEcsFossService;
	}
	
	public void setWaybillCommonDao(IWaybillCommonDao waybillCommonDao) {
		this.waybillCommonDao = waybillCommonDao;
	}
	
	public void setWscManageService(IWSCManageService wscManageService) {
	    this.wscManageService = wscManageService;
	}
	 
}
