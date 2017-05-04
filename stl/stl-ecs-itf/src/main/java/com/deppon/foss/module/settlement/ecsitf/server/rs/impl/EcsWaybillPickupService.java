package com.deppon.foss.module.settlement.ecsitf.server.rs.impl;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.WebApplicationContextHolder;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.settlement.closing.api.server.service.ILogEcsFossService;
import com.deppon.foss.module.settlement.closing.api.shared.inteface.domain.EcsFossWaybillPickupRequest;
import com.deppon.foss.module.settlement.common.api.server.service.IGreenHandWrapWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.IWSCManageService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.GreenHandResponseEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.GreenHandWrapWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCEntity;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillPickupEcsService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillPickupService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.module.settlement.ecsitf.server.rs.IEcsWaybillPickupService;
import com.deppon.foss.util.define.FossConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 快递新增、更改、作废、校验运单，财务相关接口
 * @author foss-231434-bieyexiong
 * @date 2016-4-19 11:51
 */
public class EcsWaybillPickupService implements IEcsWaybillPickupService{

	//记录日志
	private static final Logger LOGGER = LoggerFactory.getLogger(EcsWaybillPickupService.class);
	
	//成功返回1
	private static final String SUCCESS = "1";

	//失败返回0
	private static final String FAILURE = "0";

	//待刷卡运单类型新增为 1
	private static final String NEWWAYBILL = "1";
	//待刷卡运单类型更改为 2
	private static final String WAYBILLCHANGE = "2";
	@Context
	HttpServletResponse res;
	
	//运单开单服务
	private IWaybillPickupService waybillPickupService;
	
	//快递新增、更改、作废、校验运单，财务相关接口
	private IEcsWaybillPickupService ecsWaybillPickupService;
	
	//private IWaybillManagerService waybillManagerService;
    //代刷service
    private IWSCManageService wscManageService;
    
    //裹裹订单服物
    private IGreenHandWrapWriteoffService greenHandWrapWriteoffService;
    
    //日志 
    private ILogEcsFossService logEcsFossService;
    
    //悟空开单、更改服务
    private IWaybillPickupEcsService waybillPickupEcsService;
	
    /**
	 * 悟空裹裹订单，补录时，查询裹裹暂存信息
	 * @author foss-231434-bieyexiong
	 * @date 2016-6-25 8:05
	 */
	@Override
	public @ResponseBody String queryGreenHandWrapByWaybillNo(@RequestBody String jsonReq) {
		// TODO Auto-generated method stub
		try{
			res.setHeader("ESB-ResultCode" , "1");
			JSONObject jsonObj = JSONObject.parseObject(jsonReq);
			if(jsonObj == null){
				return this.getResponse(FAILURE,"结算查询裹裹暂存信息失败：请求参数为空！",null);
			}

			String waybillNo = (String)jsonObj.get("waybillNo");
			if(StringUtils.isBlank(waybillNo)){
				return this.getResponse(FAILURE,"结算查询裹裹暂存信息失败：运单号为空！",null);
			}

			List<GreenHandWrapWriteoffEntity> greenHandList = greenHandWrapWriteoffService.queryGreenHandWrapByWaybillNo(waybillNo);

			if(CollectionUtils.isNotEmpty(greenHandList)){
				GreenHandWrapWriteoffEntity entity = greenHandList.get(0);
				return this.getGreenHandResponse(SUCCESS, "查询成功！", entity);
			}
			//查不到信息，返回金额0
			GreenHandWrapWriteoffEntity en = new GreenHandWrapWriteoffEntity();
			en.setAmount(new BigDecimal(0));
			return this.getGreenHandResponse(SUCCESS,"无裹裹暂存信息,金额为0！",en);
		}catch(BusinessException e){
			return this.getResponse(FAILURE, "结算查询裹裹暂存信息失败：" + (e.getErrorCode() != null ? e.getErrorCode() : e.getMessage()),null);
		}catch(Exception e){
			return this.getResponse(FAILURE, "结算查询裹裹暂存信息失败：系统异常，请重新操作，以查询裹裹信息！："+e,null);
		}
	}

	/**
	 * 快递新增运单，生成财务单据接口(该同步接口未被使用，上线是为了备用)
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-19 11:51
	 */
	@Override
	public @ResponseBody String addWaybill(@RequestBody String jsonReq) {
		Date date = new Date();
		res.setHeader("ESB-ResultCode" , "1");
		String response = "", waybillNo = "null";
		//是否开单成功
		boolean flag = false;
		//将请求参数转换成实体对象
		EcsFossWaybillPickupRequest req = JSONObject.parseObject(jsonReq, EcsFossWaybillPickupRequest.class);
		//当请求参数为null时
		if(req == null){
			response = this.getResponse(FAILURE, "结算生成财务单据失败：生成财务单据的运单信息为空！",null);
		}
		try{

			if(StringUtils.isBlank(req.getEmpCode())
					|| StringUtils.isBlank(req.getEmpName())
					|| StringUtils.isBlank(req.getCurrentDeptCode())
					|| StringUtils.isBlank(req.getCurrentDeptName())){
				response = this.getResponse(FAILURE, "结算生成财务单据失败：开单员信息为空！",req.getWaybillNo());
			}else if(WaybillConstants.DEPPON_CUSTOMER.equals(req.getDeliveryCustomerCode())){
				flag = true;
				//发货客户为内部带货时，不生成财务单据
				response = this.getResponse(SUCCESS, "该运单为内部带货，不生成财务单据！",req.getWaybillNo());
			}
			waybillNo = req.getWaybillNo();
			//响应为空 说明校验通过
			if (StringUtils.isBlank(response)) {
				//设置运单开单信息DTO
				WaybillPickupInfoDto waybillDto = this.getNewWaybillDto(req);
				CurrentInfo currentInfo = SettlementUtil.getECSCurrentInfo(req.getEmpCode(),req.getEmpName(),req.getCurrentDeptCode(),req.getCurrentDeptName());
	            //  begin add   by    309603  yangqiang 生成待刷卡单据 暂时先注释
	            WSCEntity wscEntity = null;
	            if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(waybillDto.getPaidMethod())) {
	                //开单银行卡
	                wscEntity = this.addWscEntityInfo(req, NEWWAYBILL);
	                wscEntity.setCreateUserCode(req.getEmpCode());
	                wscEntity.setCreateUserName(req.getEmpName());
	            }
	            //  end  add   by    309603  yangqiang
				if(FossConstants.YES.equals(waybillDto.getIsRedirect())){
					//转寄时
					ecsWaybillPickupService.toChangeTermsPayment(wscEntity,waybillDto,null, currentInfo, true);
				}else{
					//非转寄时，走原逻辑
					ecsWaybillPickupService.addWaybill(wscEntity,waybillDto, currentInfo);
				}
				response = this.getResponse(SUCCESS, "结算财务单据生成成功！",req.getWaybillNo());
				flag = true;
			}
		}catch(BusinessException e){
			response = this.getResponse(FAILURE, "结算生成财务单据失败：" + (e.getErrorCode() != null ? e.getErrorCode() : e.getMessage()),req.getWaybillNo());
		}catch(Exception e){
			//取错误信息
			String tmp = e.getMessage();
			int index = tmp.toUpperCase().indexOf("ORA-");
			if (index != -1) {
				tmp = tmp.substring(index + 4, index + 9);
			}
			//当错误编码为ORA-00001时，为oracle 主键重复异常 可能表示pod表已有数据，即该单已生成财务单据
			if("00001".equals(tmp)){
				response = this.getResponse(FAILURE, "结算生成财务单据失败:该运单已生成财务单据，不能重复生成！", waybillNo);
			}else{
				response = this.getResponse(FAILURE, "结算生成财务单据失败：系统异常，请重新操作，以生成财务单据！："+e,req.getWaybillNo());
			}
		}
		try{
			//保存日志
			logEcsFossService.setLog(jsonReq, response, 
					SettlementDictionaryConstants.FOSS_ESB2FOSS_ECS_WAYBILL_BILLING, waybillNo, flag, date);
		}catch(Exception e1){
			LOGGER.info("记录开单接口日志插入失败："+e1.toString());
		}
		return response;
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

    /**
	 * 快递起草更改运单，校验财务是否可以更改接口
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-19 11:51
	 */
	@Override
	public @ResponseBody String canChange(@RequestBody String jsonReq) {
		try{
			res.setHeader("ESB-ResultCode" , "1");

			JSONObject jsonObj = JSONObject.parseObject(jsonReq);
			if(jsonObj == null){
				return this.getResponse(FAILURE,"结算校验起草更改未通过：请求参数为空！",null);
			}

			String waybillNo = (String)jsonObj.get("waybillNo");
			if(StringUtils.isBlank(waybillNo)){
				return this.getResponse(FAILURE,"结算校验起草更改未通过：运单号为空！",null);
			}
			//校验
			waybillPickupEcsService.canChange(waybillNo);
			return this.getResponse(SUCCESS, "结算校验起草更改通过！",null);
		}catch(BusinessException e){
			return this.getResponse(FAILURE, "结算校验起草更改未通过：" + (e.getErrorCode() != null ? e.getErrorCode() : e.getMessage()),null);
		}catch(Exception e){
			return this.getResponse(FAILURE, "结算校验起草更改未通过：系统异常，请重新操作，以校验财务单据！："+e,null);
		}
	}

	/**
	 * 快递受理更改运单，相关财务单据更改接口
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-19 11:51
	 */
	@Override
	public @ResponseBody String modifiAndCancle(@RequestBody String jsonReq) {
		Date date = new Date();
		res.setHeader("ESB-ResultCode" , "1");
		//将请求参数转换成实体对象
		EcsFossWaybillPickupRequest req = JSONObject.parseObject(jsonReq, EcsFossWaybillPickupRequest.class);
		//返回参数
		String response = "";
		//响应状态1代表成功，0代表失败
		Boolean flag=null; 
		try{
			//当请求参数为null时
			if(req == null){
				response = this.getResponse(FAILURE, "结算运单更改或作废失败：运单信息为空！",null);
				flag = false;
			}else if(StringUtils.isBlank(req.getEmpCode())
					|| StringUtils.isBlank(req.getEmpName())
					|| StringUtils.isBlank(req.getCurrentDeptCode())
					|| StringUtils.isBlank(req.getCurrentDeptName())){
				response = this.getResponse(FAILURE, "结算运单更改或作废失败：操作人信息为空！",null);
				flag = false;
			}else {
                //设置运单开单信息DTO
                WaybillPickupInfoDto newWaybill = this.getNewWaybillDto(req);
                //设置运单更改单信息
                WaybillPickupInfoDto oldWaybill = this.getOldWaybillDto(req);
                CurrentInfo currentInfo = SettlementUtil.getECSCurrentInfo(req.getEmpCode(), req.getEmpName(), req.getCurrentDeptCode(), req.getCurrentDeptName());
                String message = "";
                if (FossConstants.YES.equals(newWaybill.getIsRedirect())) {
                    ecsWaybillPickupService.toChangeTermsPayment(req,newWaybill, oldWaybill, currentInfo, false);

                } else {
                    //非转寄时，走原逻辑
                    message = ecsWaybillPickupService.modifiAndCancle(req,req.getIsChange(), oldWaybill, newWaybill, currentInfo);
                }
                flag = true;
                response = this.getResponse(SUCCESS, message, null);
            }
        }catch(BusinessException e){
        	flag = false;
			response = this.getResponse(FAILURE, "结算运单更改或作废失败：" + (e.getErrorCode() != null ? e.getErrorCode() : e.getMessage()),null);
		}catch(Exception e){
			flag = false;
			response = this.getResponse(FAILURE, "结算运单更改或作废失败：系统异常，请重新操作，以校验财务单据！："+e,null);
		}
		//记录异步接口日志
		try{
			logEcsFossService.setLog(jsonReq, response, "FOSS_ESB2FOSS_CHANGE_FINALNCIAL_BILL", req.getWaybillNo(), flag, date);
		}catch(Exception e){
			LOGGER.info("记录更改运单接口日志失败："+e);
		}
		return response;
	}
    //运单更改，生成银行卡信息方法
    private void addWscInfo(EcsFossWaybillPickupRequest req) {
            String isChange = req.getIsChange();
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
    }

    /**
	 * 获取响应信息
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-19 17:30
	 */
	private String getResponse(String result,String message,String waybillNo){
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("result", result);
		map.put("message", message);
//		if(StringUtils.isNotBlank(waybillNo)){
//			map.put("data", waybillNo);
//		}
		String response = JSONObject.toJSONString(map);
		return response;
	}
	
	/**
	 * 获取响应信息
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-19 17:30
	 */
	private String getGreenHandResponse(String result,String message,GreenHandWrapWriteoffEntity entity){
		GreenHandResponseEntity res = new GreenHandResponseEntity();
		if(entity != null){
			//运单号
			res.setWaybillNo(entity.getWaybillNo());
			//金额
			res.setAmount(entity.getAmount() == null?new BigDecimal(0) : entity.getAmount());
			//费用类型
			res.setCostType(entity.getCostType());
			//dop传数据过来的时间
			res.setDopTime(entity.getDopTime());
			//创建人
			res.setCreateUser(entity.getCreateUser());
			//创建时间
			res.setCreateTime(entity.getCreateTime());
			//备注 
			res.setNote(entity.getNote());
		}
		//成功失败标识
		res.setResult(result);
		//提示信息
		res.setMessage(message);
		String response = JSONObject.toJSONString(res);
		return response;
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
	 * 快递开单(为了加事务控制，所以提出来)
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-19 21:10
	 */
    @Override
    @Transactional
    public void addWaybill(WSCEntity wscEntity, WaybillPickupInfoDto waybillDto, CurrentInfo currentInfo) {
        //开单银行卡加入事务
        if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(waybillDto.getPaidMethod())) {
            wscManageService.payWscEntityForESC(wscEntity);
        }
        waybillPickupService.addWaybill(waybillDto, currentInfo);
    }

    @Override
	@Transactional
	public void addWaybill(WaybillPickupInfoDto waybillDto,CurrentInfo currentInfo){
		waybillPickupService.addWaybill(waybillDto, currentInfo);
	}
	
	/**
	 * 快递更改单(为了加事务控制，所以重写一个方法)
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-19 21:34
	 */
    @Override
    @Transactional
    public String modifiAndCancle(EcsFossWaybillPickupRequest req,String isChange,WaybillPickupInfoDto oldWaybill, WaybillPickupInfoDto newWaybill, CurrentInfo currentInfo) {
        ////309603   加入事务
        this.addWscInfo(req);
        return ecsWaybillPickupService.modifiAndCancle(req.getIsChange(), oldWaybill, newWaybill, currentInfo);
    }

    @Override
    @Transactional
    public String modifiAndCancle(String isChange,WaybillPickupInfoDto oldWaybill, WaybillPickupInfoDto newWaybill, CurrentInfo currentInfo){
        if(FossConstants.YES.equals(isChange)){
            waybillPickupService.modifyWaybill(oldWaybill, newWaybill, currentInfo);
            return "结算运单更改成功！";
        }else{
            waybillPickupService.handleCanceledWaybill(oldWaybill.getWaybillNo(), currentInfo);
            return "结算运单作废成功！";
        }
    }

	/**
	 * 快递转寄开单(为了加事务控制，所以重写一个方法)
	 * @author foss-231434-bieyexiong
	 * @date 2016-5-10 10:56
	 */
    @Override
    @Transactional
    public void toChangeTermsPayment(WSCEntity wscEntity,WaybillPickupInfoDto waybillDto,WaybillPickupInfoDto oldWaybill,CurrentInfo currentInfo,boolean isAddBill) {
        //开单银行卡加入事务 309603
        if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(waybillDto.getPaidMethod())) {
            wscManageService.payWscEntityForESC(wscEntity);
        }
        //转寄时(转寄的更改和作废 等同于转寄开新单，将原单据红冲，生成新单据；转寄作废只能作废新单)
        ecsWaybillPickupService.toChangeTermsPayment(waybillDto, oldWaybill, currentInfo, false);
    }
    @Override
    @Transactional
    public void toChangeTermsPayment(EcsFossWaybillPickupRequest req ,WaybillPickupInfoDto waybillDto,WaybillPickupInfoDto oldWaybill,CurrentInfo currentInfo,boolean isAddBill) {
        //309603   加入事务 更改
        this.addWscInfo(req);
        //转寄时(转寄的更改和作废 等同于转寄开新单，将原单据红冲，生成新单据；转寄作废只能作废新单)
        ecsWaybillPickupService.toChangeTermsPayment(waybillDto, oldWaybill, currentInfo, false);
    }
	@Override
	@Transactional
	public void toChangeTermsPayment(WaybillPickupInfoDto waybillDto,WaybillPickupInfoDto oldWaybill,CurrentInfo currentInfo,boolean isAddBill) {
		waybillPickupService.toChangeTermsPayment(waybillDto,oldWaybill,currentInfo,isAddBill);
	}
	
	public void setWaybillPickupService(IWaybillPickupService waybillPickupService) {
		this.waybillPickupService = waybillPickupService;
	}

	public void setEcsWaybillPickupService(IEcsWaybillPickupService ecsWaybillPickupService) {
		this.ecsWaybillPickupService = WebApplicationContextHolder.getWebApplicationContext().
				getBean("ecsWaybillPickupService",IEcsWaybillPickupService.class);
	}

	/*public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}*/

    public void setWscManageService(IWSCManageService wscManageService) {
        this.wscManageService = wscManageService;
    }

	public void setGreenHandWrapWriteoffService(
			IGreenHandWrapWriteoffService greenHandWrapWriteoffService) {
		this.greenHandWrapWriteoffService = greenHandWrapWriteoffService;
	}

	public void setLogEcsFossService(ILogEcsFossService logEcsFossService) {
		this.logEcsFossService = logEcsFossService;
	}

	public void setWaybillPickupEcsService(
			IWaybillPickupEcsService waybillPickupEcsService) {
		this.waybillPickupEcsService = waybillPickupEcsService;
	}

}
