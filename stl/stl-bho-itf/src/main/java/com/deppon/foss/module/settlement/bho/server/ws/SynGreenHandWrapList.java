package com.deppon.foss.module.settlement.bho.server.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.settlement.bho.server.inter.ISynGreenHandWrapList;
import com.deppon.foss.module.settlement.common.api.server.service.IBillCashCollectionService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillRepaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IFossToFinsRemitCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.IGreenHandWrapWriteoffService;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.RequestGreenHandWrapEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.ReturnGreenHandWrapEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillRepaymentConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.define.ESBHeaderConstant;
import com.deppon.foss.util.define.FossConstants;

public class SynGreenHandWrapList implements ISynGreenHandWrapList {
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SynGreenHandWrapList.class);
	
	/**
	 * 注入裹裹核销应收单的接口service
	 */
	private IGreenHandWrapWriteoffService greenHandWrapWriteoffService;
	
	/**
	 * FOSS到财务第三方支付
	 */
	private IFossToFinsRemitCommonService fossToFinsRemitCommonService;
	
	/**
	 * 注入现金收款单接口
	 */
	private IBillCashCollectionService billCashCollectionService;
	/**
	 * 注入还款单服务
	 */
	private IBillRepaymentService billRepaymentService;

    @Context
    protected HttpServletRequest req;
	@Context
	protected HttpServletResponse resp;
	
	private static final String GREEN = "GG";

	
	/**
	 * 裹裹
	 * 
	 * @Title: addGatewayPayment
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-7-1 上午9:59:04
	 */
	@SuppressWarnings("finally")
	@Override
	public String addGreenHandWrapList(String greenHandWrapList) {
		resp.setHeader(ESBHeaderConstant.VERSION, req.getHeader(ESBHeaderConstant.VERSION));
		resp.setHeader(ESBHeaderConstant.ESBSERVICECODE, "FOSS_ESB2FOSS_AUTO_VERIFICATION");
		resp.setHeader(ESBHeaderConstant.REQUESTID, req.getHeader(ESBHeaderConstant.REQUESTID));
		resp.setHeader(ESBHeaderConstant.BUSINESSID, req.getHeader(ESBHeaderConstant.BUSINESSID));
		resp.setHeader(ESBHeaderConstant.MESSAGEFORMAT, req.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
		resp.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, req.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
		resp.setHeader(ESBHeaderConstant.BACKSERVICECODE, req.getHeader(ESBHeaderConstant.BACKSERVICECODE));
		resp.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
		resp.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
		
		ReturnGreenHandWrapEntity response = new ReturnGreenHandWrapEntity();
		JSONObject object = JSONObject.fromObject(greenHandWrapList);
		RequestGreenHandWrapEntity request = (RequestGreenHandWrapEntity) JSONObject.toBean(object, RequestGreenHandWrapEntity.class);
		/**
		 * 封装另一个请求实体
		 */
		RequestGreenHandWrapEntity param = new RequestGreenHandWrapEntity();
		param = request;
		LOGGER.info("开始获取dop传过来的信息...");
		String waybillNo = "";
		if(request == null){
			throw new SettlementException("DOP推送来的数据为空...");
		}
		waybillNo = request.getWaybillNo();
		/**
		 * 调用service业务层，根据request里的单号查询始发应收单，若有，处理；没有，插入到暂存表里；
		 */	
		try{	
				/**
				 * 1.首先往暂存表中插入数据,是否核销状态为N
				 */
				greenHandWrapWriteoffService.addDopInfo(request);
				/**
				 * 2.根据DOP传入的参数，运单号查询满足本次项目需求条件的应收单
				 * （条件是：始发应收单，未核销金额大于0，有效的，来源单据类型为 W-开单）
				 */
				List<BillReceivableEntity> billReceivableList = greenHandWrapWriteoffService.queryReceivableBill(request);
				
				//校验
				if(billReceivableList != null && billReceivableList.size() == 0){
					/**
					 * 不存在单据，则有三种可能
					 * 1、PDA开单需要补录的时候
					 * 2、运单作废，不存在有效应收单
					 * 3、运单已结清，对接财务自助失败
					 * 
					 * 需求优化：
					 * 	 补录或者开单为现金，都需要推送到财务自助公布
					 */
					/**
					 * 针对上面三种情况判断到底是哪种情况
					 */
					/**
					 * 1、运单结清,对接财务自助失败，DOP重推
					 */
					validaBillRepayment(request, param, waybillNo);
				}else if(billReceivableList != null && billReceivableList.size() > 1){
					/**
					 * 单子存在多条始发应收单，则财务单据已经生成,则需要将数据推送到报账平台
					 */
					param.setIsPush("true");//是否需要推送
					param.setResource(GREEN);//来源
					param.setIsException(FossConstants.YES);//是否异常
					throw new SettlementException("单号："+ waybillNo + "存在多条始发应收单");
				}else if(billReceivableList != null && billReceivableList.size() == 1){
					/**
					 * 3.如果查询应收单有的话，那么直接核销始发应收单;
					 *   核销完之后,调用财务自助接口,将数据推送到财务自助那边
					 */
					try{
						BillReceivableEntity billReceivableEntity = billReceivableList.get(0);
						greenHandWrapWriteoffService.writeoffByDoprequest(billReceivableEntity,request);
					}catch(Exception e){
						//核销失败之后,将异常数据推送
						param.setIsPush("true");//是否需要推送
						param.setResource(GREEN);//来源
						param.setIsException(FossConstants.YES);//是否异常
						throw new SettlementException(waybillNo+"单子核销失败！");
					}
					// 假如系统核销成功,则正常推送到财务自助
					param.setIsPush("true");//是否需要推送
					param.setResource(GREEN);//来源
					param.setIsException(FossConstants.NO);//正常推送
					response.setIfSuccess(true);
				}
		}catch(SettlementException e){
			LOGGER.info("打印捕捉异常信息为："+e.getErrorCode());
			response.setIfSuccess(true);
			response.setErrorMsg("数据异常:"+e.getErrorCode());
		}catch(Exception e1){
			LOGGER.info("打印捕捉异常信息为："+e1.getMessage());
			response.setIfSuccess(true);
			response.setErrorMsg("数据异常:"+e1.getMessage());
		}finally{
			/**
			 * 判断是否需要推送数据到报账平台，存在多条单子的时候
			 */
			try{
				if("true".equals(request.getIsPush())){
					//将DOP推送过来的数据，对接到报账平台
					fossToFinsRemitCommonService.pushRemittanceMessToFins(param);
					//推送数据到财务自助成功，true
					response.setIfSuccess(true);
				}else{
					//PDA开单，不需要推送到财务，以防数据丢失，让DOP重推
					response.setIfSuccess(false);
				}
			}catch(SettlementException e2){
				/**
				 * 对于DOP来说，只有FOSS对接财务自助失败，才会重新推送
				 */
				response.setIfSuccess(false);
				response.setErrorMsg("数据异常:"+e2.getErrorCode());
			}catch(Exception e3){
				/**
				 * 对于DOP来说，只有FOSS对接财务自助失败，才会重新推送
				 */
				response.setIfSuccess(false);
				response.setErrorMsg("数据异常:"+e3.getMessage());
			}
			resp.setHeader("ESB-ResultCode", "1");
			String responseJson = JSONObject.fromObject(response).toString();
			return responseJson;
		}
	}


	private void validaBillRepayment(RequestGreenHandWrapEntity request,
			RequestGreenHandWrapEntity param, String waybillNo) {
		BillRepaymentConditionDto condition = new BillRepaymentConditionDto();
		String onlinePaymentNo = GREEN+param.getWaybillNo();
		condition.setOnlinePaymentNo(onlinePaymentNo);
		condition.setActive(FossConstants.YES);
		List<BillRepaymentEntity> lists = billRepaymentService.queryBillRepaymentByCondition(condition);
		//存在一条有效的还款单数据,运单结清
		if(lists != null && lists.size() == 1){
			param.setIsPush("true");//是否需要推送
			param.setResource(GREEN);//来源
			param.setIsException(FossConstants.NO);//是否异常
			throw new SettlementException("单号："+ waybillNo + "已结清过");
		}else if(lists != null && lists.size() > 1){
			param.setIsPush("false");//不需要推送
			throw new SettlementException("单号："+ waybillNo + "存在多条有效的还款单");
		}
		
		/**
		 * 判断单据是否补录为了现金，找现金收款单
		 */
		List<String> waybillNos = new ArrayList<String>();
		waybillNos.add(waybillNo);
		List<BillCashCollectionEntity> cashList = billCashCollectionService.queryBySourceBillNOs(waybillNos, null, FossConstants.YES);
		// 补录为现金
		if(cashList != null && cashList.size()>0){
			param.setIsPush("true");
			param.setResource(GREEN);
			param.setIsException(FossConstants.YES);
			param.setDunningOrgCode(cashList.get(0).getCollectionOrgCode());//收款部门
			throw new SettlementException("单号："+ waybillNo + "补录为现金！");
		}
		
		// 判断是否补录为到付
		request.setIsDr(FossConstants.YES);//为了查询，放置一个变量
		List<BillReceivableEntity> dRlist = greenHandWrapWriteoffService.queryReceivableBill(request);
		//补录为到付
		if(dRlist != null && dRlist.size()>0){
			param.setIsPush("true");
			param.setResource(GREEN);
			param.setIsException(FossConstants.YES);
			param.setIsDr(FossConstants.YES);
			param.setDunningOrgCode(dRlist.get(0).getOrigOrgCode());//公布到出发部门
			throw new SettlementException("单号："+ waybillNo + "补录为到付！");
		}
		/**
		 * 单据是否作废，或者待补录状态
		 */
		//判断查询条件
		request.setIsException(FossConstants.YES);
		
		//查询无效应收单，判断当前单号是否生成过财务单据
		List<BillReceivableEntity> list = greenHandWrapWriteoffService.queryReceivableBill(request);
		if(list != null && list.size() > 0){
			//运单作废，需要推送到财务自助
			param.setIsPush("true");//是否需要推送
			param.setResource(GREEN);//来源
			param.setIsException(FossConstants.YES);//是否异常
			String dunningOrgCode = list.get(0).getDunningOrgCode();
			param.setDunningOrgCode(dunningOrgCode);
			throw new SettlementException("单号："+ waybillNo + "已被作废");
		}else{
			//PDA开单
			param.setIsPush("false");//不需要推送，但是得让DOP重新将数据推送过来，以防数据丢失
			throw new SettlementException("单号："+ waybillNo + "待补录状态！");
		}
	}

	
	/*********setter*********/
	
	public void setGreenHandWrapWriteoffService(
			IGreenHandWrapWriteoffService greenHandWrapWriteoffService) {
		this.greenHandWrapWriteoffService = greenHandWrapWriteoffService;
	}

	public HttpServletRequest getReq() {
		return req;
	}

	public void setReq(HttpServletRequest req) {
		this.req = req;
	}

	public HttpServletResponse getResp() {
		return resp;
	}

	public void setResp(HttpServletResponse resp) {
		this.resp = resp;
	}

	public void setFossToFinsRemitCommonService(
			IFossToFinsRemitCommonService fossToFinsRemitCommonService) {
		this.fossToFinsRemitCommonService = fossToFinsRemitCommonService;
	}

	public void setBillRepaymentService(IBillRepaymentService billRepaymentService) {
		this.billRepaymentService = billRepaymentService;
	}


	public void setBillCashCollectionService(
			IBillCashCollectionService billCashCollectionService) {
		this.billCashCollectionService = billCashCollectionService;
	}
	
	
	
}
