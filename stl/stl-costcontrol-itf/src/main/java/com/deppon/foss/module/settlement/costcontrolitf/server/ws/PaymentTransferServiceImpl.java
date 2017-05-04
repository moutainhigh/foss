package com.deppon.foss.module.settlement.costcontrolitf.server.ws;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import net.sf.json.JSONObject;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentService;
import com.deppon.foss.module.settlement.common.api.shared.domain.PaymentTransferEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.costcontrolitf.server.inter.PaymentTransferService;

public class PaymentTransferServiceImpl implements PaymentTransferService {

	/**
	 * 日志属性
	 */
	private static final Logger logger = LogManager.getLogger(PaymentTransferServiceImpl.class);
	
	@Context
	HttpServletResponse response;
	@Context
	HttpServletRequest request;
	
	private IBillPaymentService billPaymentService;
	
	@SuppressWarnings("finally")
	@Override
	public String updatePaymentTransfer(String paymentTransfer) {
		//打印日志
		logger.info("报账更新付款单开始");
		//创建map对象
		Map<Object,String> map = new HashMap<Object,String>();
		//创建转报销实体
		PaymentTransferEntity paymentEntity  = new PaymentTransferEntity();
		try{
			//判断参数是否为空
			if(StringUtil.isNotBlank(paymentTransfer)){
				//转换paymentTransfer参数类型
				JSONObject paymentObject = JSONObject.fromObject(paymentTransfer);
				//转换paymentObject变量类型
				Object object = JSONObject.toBean(paymentObject, PaymentTransferEntity.class);
				//转换object变量类型
				PaymentTransferEntity paymentTransferEntity  = (PaymentTransferEntity)object;
				//转报销工作流号
				String workflowNo = paymentTransferEntity.getWorkflowNo();
				//批次号
				String batchNo = paymentTransferEntity.getBatchNo();
				//打印转报销工作流号
				logger.info("转报销工作流号" + workflowNo);
				//打印批次号
				logger.info("批次号" + batchNo);
				//判断批次号和工作流号是否为空
				if(null != paymentTransferEntity.getWorkflowNo() && null != paymentTransferEntity.getBatchNo()){
					//转报销工作流号
					map.put("workflowNo", workflowNo);
					//批次号
					map.put("batchNo", batchNo);
					//更新付款单转报销工作流号
					int rownom = billPaymentService.updatePaymentByBatchNo(map);
					//判断更改行数
					if(rownom == 0){
						//转报销工作流号
						paymentEntity.setWorkflowNo(workflowNo);
						//批次号
						paymentEntity.setBatchNo(batchNo);
						//是否成功
						paymentEntity.setIsSuccess("0");
						//原因
						paymentEntity.setReason("更新付款单失败，条数为" + rownom);
					}else{
						//转报销工作流号
						paymentEntity.setWorkflowNo(workflowNo);
						//批次号
						paymentEntity.setBatchNo(batchNo);
						//是否成功
						paymentEntity.setIsSuccess("1");
						//原因
						paymentEntity.setReason("更新付款单成功，条数为" + rownom);
					}
				}else{
					throw new SettlementException("数据参数错误，workflowNo为空或batchNo为空！");
				}
			}else{
				throw new Exception("数据参数错误，paymentTransfer为空！");
			}
		}catch(SettlementException e){
			//是否成功
			paymentEntity.setIsSuccess("0");
			//原因
			paymentEntity.setReason(e.getMessage());
		}catch(Exception e){
			//是否成功
			paymentEntity.setIsSuccess("0");
			//原因
			paymentEntity.setReason(e.getMessage());
		}finally{
			//转换paymentEntity类型
			JSONObject jsonObj = JSONObject.fromObject(paymentEntity);
			//转换jsonObj类型
			String js = jsonObj.toString();
			//赋值esb消息头
			response.addHeader("ESB-ResultCode", "1");
			//打印日志
			logger.info("报账更新付款单结束");
			//返回
			return js;
		}
	}

	public void setBillPaymentService(IBillPaymentService billPaymentService) {
		this.billPaymentService = billPaymentService;
	}

}
