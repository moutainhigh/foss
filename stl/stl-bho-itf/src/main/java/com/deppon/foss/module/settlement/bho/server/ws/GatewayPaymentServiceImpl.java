package com.deppon.foss.module.settlement.bho.server.ws;

import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.settlement.bho.server.inter.GatewayPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillRepaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IFossToFinsRemitCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.ISendStatusToPtpService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.ReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.RequestGreenHandWrapEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.ReturnGreenHandWrapEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableOnlineQueryDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillRepaymentConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.WithholdStatusDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.pay.api.server.service.IGatewayPaymentService;
import com.deppon.foss.util.define.FossConstants;
import net.sf.json.JSONObject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by youkun on 2016/5/6. 实现支付宝网关支付
 */
public class GatewayPaymentServiceImpl implements GatewayPaymentService {

	/**
	 * 日志属性
	 */
	private static final Logger logger = LogManager
			.getLogger(GatewayPaymentServiceImpl.class);

	/**
	 * 注入更改单SERVICE
	 */
	private IWaybillRfcService waybillRfcService;

	/**
	 * 注入应收单SERVICE
	 */
	private IBillReceivableService billReceivableService;

	/**
	 * 网上支付：查询未核销的应收单服务
	 */
	private IGatewayPaymentService gatewayPaymentService;

	/**
	 * 注入运单IWaybillManagerService
	 */
	private IWaybillManagerService waybillManagerService;

	/**
	 * FOSS到财务第三方支付
	 */
	private IFossToFinsRemitCommonService fossToFinsRemitCommonService;
	
	/**
	 * 注入还款单服务
	 */
	private IBillRepaymentService billRepaymentService;
	
	/**
	 * 更新PTP扣款状态
	 */
	 private ISendStatusToPtpService sendStatusToPtpService;

	@Context
	HttpServletResponse response;
	@Context
	HttpServletRequest request;

	/**
	 * 支付宝网关支付
	 * 
	 * @Title: addGatewayPayment
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-7-1 上午9:59:04
	 */
	@SuppressWarnings({ "finally" })
	@Override
	public String addGatewayPayment(String receivableBill) {
		logger.info("DOP核销应收单开始");
		// 运单号结合
		List<String> waybillNos = new ArrayList<String>();
		// 网上支付应收单查询dto
		BillReceivableOnlineQueryDto queryDto = new BillReceivableOnlineQueryDto();
		// 返回DOP应收集合
		List<ReceivableEntity> receivableEntityList = new ArrayList<ReceivableEntity>();
		ReturnGreenHandWrapEntity res = new ReturnGreenHandWrapEntity();
		//更新PTP扣款状态DTO
		WithholdStatusDto withholdStatusDto = new WithholdStatusDto();
		//更新PTP扣款状态DTO集合
		List<WithholdStatusDto> withholdStatusDtoList = new ArrayList<WithholdStatusDto>();
		/**
		 * DOP推送数据到结算之后，结算必须将数据推送到财务自助
		 */
		// 如果系统无异常，则推送数据到财务的时候为已使用，否则为未使用用。默认为N
		RequestGreenHandWrapEntity paramEntity = new RequestGreenHandWrapEntity();
		try {
			// 将接收到的字符串转换成JSONObject
			JSONObject object = JSONObject.fromObject(receivableBill);

			// 将reJsonArray转换成应收实体
			RequestGreenHandWrapEntity req = (RequestGreenHandWrapEntity) JSONObject
					.toBean(object, RequestGreenHandWrapEntity.class);
			paramEntity = req;
			/**
			 * 默认无异常
			 */
			paramEntity.setIsException("N");
			// 数据来源
			paramEntity.setResource("ZFB");
			// 运单号
			String waybillNo = req.getWaybillNo();
			// 运单号加入list
			waybillNos.add(waybillNo);
			// 打印DOP参数
			logger.info("DOP核销运单单号：" + req.getWaybillNo());
			logger.info("DOP核销应收金额：" + req.getDopAmount());
			logger.info("DOP核销汇款编码：" + req.getWaybillNo());
			logger.info("DOP核销汇款账号：" + req.getReceivableNo());
			/**
			 * 限制一下，如果单子已经结清了，但是对接财务自助失败了，
			 * DOP重推，这个时候按正常数据推送，判断在线支付编码是否已经使用了
			 */
			/**
			 * 1、运单结清,对接财务自助失败，DOP重推
			 */
			BillRepaymentConditionDto condition = new BillRepaymentConditionDto();
			String onlinePaymentNo = paramEntity.getWaybillNo();
			condition.setOnlinePaymentNo(onlinePaymentNo);
			condition.setActive("Y");
			List<BillRepaymentEntity> lists = billRepaymentService.queryBillRepaymentByCondition(condition);
			//存在一条有效的还款单数据,运单结清
			if(lists != null && lists.size() == 1){
				paramEntity.setIsPush("true");
				paramEntity.setIsException("N");//是否异常
				throw new SettlementException("单号："+ waybillNo + "已被使用过");
			}/*else if(lists != null && lists.size() > 1){
				paramEntity.setIsPush("false");
				throw new SettlementException("单号："+ waybillNo + "存在多条有效的还款单");
			}*/
			// 查询未受理的更改单
			List<String> waybillList = waybillRfcService
					.isExsitsWayBillRfcs(waybillNos);
			// 快递开单到付，限制补码前使用支付宝支付
			this.noPayNotAddCode(waybillNos);
			// 通过运单号查询应收单
			receivableEntityList = billReceivableService.queryByWaybillNOs(
					waybillNos, waybillList);
			// 系统当前时间
			Date date = new Date();
			// 循环处理应收单进行核销
			for (int i = 0; i < receivableEntityList.size(); i++) {
				// 获取应收单实体
				ReceivableEntity receivableEntity = receivableEntityList.get(i);
				// 在线支付编号
				queryDto.setOnlineNo(req.getWaybillNo());
				// 金额
				queryDto.setAmount(req.getDopAmount());
				// 在线支付编号
				receivableEntity.setOnlineNo(req.getWaybillNo());
				// 设置应收单单号
				queryDto.setReceivableNo(receivableEntity.getReceivableNo());
				// 记账日期
				queryDto.setAccountDate(date);
				// 定义的费用类型
				queryDto.setCostType(req.getCostType());
				// 汇款账号
				queryDto.setRemitAccount(req.getReceivableNo());
				// 调用网上支付接口进行核销
				gatewayPaymentService.paymentReceiveBillInfo(queryDto);
				// 核销成功
				res.setIfSuccess(true);
				
				//设置运单号
				withholdStatusDto.setWaybillNo(req.getWaybillNo());
				//设置单据子类型
				withholdStatusDto.setBillType(receivableEntity.getBillType());
				//设置到达部门编码
				withholdStatusDto.setDestOrgCode(receivableEntity.getDestOrgCode());
				//设置到达部门名称
				withholdStatusDto.setDestOrgName(receivableEntity.getDestOrgName());
				//设置场景
				withholdStatusDto.setScene(SettlementDictionaryConstants.FOSS_PTP_SEND_WITHHOLD_STATUS_ONLINE);
				//添加到集合中
				withholdStatusDtoList.add(withholdStatusDto);
			}
			//发送到ptp
			sendStatusToPtpService.sendStatusToPtp(withholdStatusDtoList);
			
		} catch (SettlementException e) {
			/**
			 * 异常处理，foss结算处理的任何异常信息,将异常数据推送到财务自助
			 */
			paramEntity.setIsException("Y");
			// 清除集合
			res.setIfSuccess(true);
			// res.setErrorMsg("异常信息："+e.getErrorCode());
			logger.info("DOP核销异常：" + e.getErrorCode());
		} catch (Exception e1) {
			/**
			 * 异常处理，foss结算处理的任何异常信息,将异常数据推送到财务自助
			 */
			paramEntity.setIsException("Y");
			// 清除集合
			res.setIfSuccess(true);
			// res.setErrorMsg("异常信息："+e1.getMessage());
			logger.info("DOP核销异常：" + e1);
		} finally {
			/**
			 * 数据处理完成之后推送信息到财务自助,对于DOP来说，只有对接财务失败了，才算是异常，DOP重推
			 */
			try {
				/**
				 * 调用财务自助推送数据
				 */
				if("true".equals(paramEntity.getIsPush())){
					//非异常处理
					paramEntity.setIsException("N");
				}
				//调用财务自助接口
				fossToFinsRemitCommonService
							.pushRemittanceMessToFins(paramEntity);
				//对接财务自助成功，返回true
				res.setIfSuccess(true);
				logger.info("推送数据成功！");
			} catch (SettlementException e) {
				// 对接财务失败，DOP数据重推
				res.setIfSuccess(false);
				res.setErrorMsg("异常信息：" + e.getErrorCode());
			}catch (Exception e1) {
				// 对接财务失败，DOP数据重推
				res.setIfSuccess(false);
				res.setErrorMsg("异常信息：" + e1.getMessage());
			}
			// 将异常信息返回给调用放
			String responseJson = JSONObject.fromObject(res).toString();
			response.addHeader("ESB-ResultCode", "1");
			logger.info("DOP核销应收单结束");
			return responseJson;
		}
	}

	/**
	 * 快递开单到付，限制补码前使用支付宝支付
	 * 
	 * @author 尤坤
	 * @date 2016-05-06
	 */
	public void noPayNotAddCode(List<String> waybillNos) {
		// 循环校验每一个运单
		for (int i = 0; i < waybillNos.size(); i++) {
			// 调用接口进行查询运单实体
			WaybillDto waybillDto = waybillManagerService
					.queryWaybillByNo(waybillNos.get(i));
			// 判断运单实体DTO是否为空
			if (waybillDto == null) {
				throw new SettlementException("运单 " + waybillNos.get(i)
						+ "没有对应的运单实体信息！");
			}
			// 判断运单基础是否为空
			if (waybillDto.getWaybillEntity() == null) {
				throw new SettlementException("运单 " + waybillNos.get(i)
						+ "没有对应的运单基础信息!");
			}
			// 如果是快递
			if (SettlementUtil.isPackageProductCode(waybillDto
					.getWaybillEntity().getProductCode())) {
				// 到付
				if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT
						.equals(waybillDto.getWaybillEntity().getPaidMethod())) {
					// 判断该运单是否有对应的快递信息
					if (waybillDto.getWaybillExpressEntity() == null) {
						throw new SettlementException("运单 " + waybillNos.get(i)
								+ "没有对应的快递信息!");
					}
					// 未补码
					if (!FossConstants.ACTIVE.equals(waybillDto
							.getWaybillExpressEntity().getIsAddCode())) {
						throw new SettlementException("请在运单 "
								+ waybillNos.get(i) + "补码后再进行支付!");
					}
				}
			}
		}
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}

	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	public void setGatewayPaymentService(
			IGatewayPaymentService gatewayPaymentService) {
		this.gatewayPaymentService = gatewayPaymentService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setFossToFinsRemitCommonService(
			IFossToFinsRemitCommonService fossToFinsRemitCommonService) {
		this.fossToFinsRemitCommonService = fossToFinsRemitCommonService;
	}

	public void setBillRepaymentService(IBillRepaymentService billRepaymentService) {
		this.billRepaymentService = billRepaymentService;
	}

	public void setSendStatusToPtpService(
			ISendStatusToPtpService sendStatusToPtpService) {
		this.sendStatusToPtpService = sendStatusToPtpService;
	}
	
	
}
