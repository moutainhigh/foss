package com.deppon.foss.module.settlement.financeitf.server.esb;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.foss.inteface.domain.courier.SendCourierProcessReult;
import com.deppon.foss.inteface.domain.courier.SendCourierResponse;

/**
 * 返回电子发票发送接口
 * 
 * @date 2014-10-31 下午3:14:23
 */
public class ElectronicInvoiceSendCallBack implements ICallBackProcess {

	/**
	 * 返回电子发票发送接口日志
	 */
	protected static final Logger LOGGER = LoggerFactory.getLogger(ElectronicInvoiceSendCallBack.class);

	/**
	 * 返回电子发票发送接口日志
	 * 
	 * @see com.deppon.esb.core.process.ICallBackProcess#callback(java.lang.Object)
	 */
	@Override
	public void callback(Object obj) throws ESBException {
		//记录日志
		LOGGER.info("开始电子发票发送接口返回...");
		
		// 返回工作流
		SendCourierResponse response = (SendCourierResponse) obj;
		//记录日志
		LOGGER.info("成功条数："+response.getSuccessCount());
		LOGGER.info("失败条数："+response.getFailedCount());
		
		List<SendCourierProcessReult> list =  response.getProcessResult();
		for (SendCourierProcessReult sendCourierProcessReult : list) {
			LOGGER.info("运单号："+sendCourierProcessReult.getWayBillNo());
			LOGGER.info("发票号码："+sendCourierProcessReult.getInvoiceNo());
			LOGGER.info("发票代码："+sendCourierProcessReult.getInvoiceCode());
			LOGGER.info("开票时间："+sendCourierProcessReult.getDeliveryTime());
			LOGGER.info("处理结果："+sendCourierProcessReult.getReason());
			LOGGER.info("--------------------------------------------");
		}
		
		//记录日志
		LOGGER.info("结束电子发票发送接口返回...");
	}

	/**
	 * 异常处理
	 * 
	 * @see com.deppon.esb.core.process.ICallBackProcess#errorHandler(java.lang.Object)
	 */
	@Override
	public void errorHandler(Object arg0) throws ESBException {
		LOGGER.error("返回电子发票发送接口错误！");
	}

}
