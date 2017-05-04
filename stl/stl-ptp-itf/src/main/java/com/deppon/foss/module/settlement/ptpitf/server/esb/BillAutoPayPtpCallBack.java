package com.deppon.foss.module.settlement.ptpitf.server.esb;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ErrorResponse;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.exception.CommonExceptionInfo;
import com.deppon.esb.inteface.domain.payment.AutoPayResponse;
import com.deppon.esb.inteface.domain.payment.AutoPayResult;
import com.deppon.esb.pojo.transformer.jaxb.AutoPayResponseTrans;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillAutoPayPtpService;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 返回自动付款PTP充值结果回调
 * @author 302346-foss-Jiang Xun
 * @date 2016-05-26 上午10:04:00
 */
public class BillAutoPayPtpCallBack implements ICallBackProcess {

	/**
	 * FOSS到PTP自动付款service
	 */
	private IBillAutoPayPtpService billAutoPayPtpService;

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LogManager
			.getLogger(BillAutoPayPtpCallBack.class);
	
	/**
	 * 处理自动付款结果（付款冲应付、更新付款单汇款状态）
	 * 
	 * @author 302346-foss-Jiang Xun
	 * @date 2016-05-26 上午10:06:00
	 * @see com.deppon.esb.core.process.ICallBackProcess#callback(java.lang.Object)
	 */
	@Override
	public void callback(Object obj) throws ESBException {
		LOGGER.info("回调自动付款PTP结果开始。");
		if(obj==null){
			LOGGER.error("回调自动付款PTP结果。回调参数是：参数为空");
			throw new SettlementException("回调自动付款PTP结果。回调参数是：参数为空");
		}
		// 返回响应信息
		AutoPayResponse response = (AutoPayResponse) obj;
		LOGGER.info("回调自动付款PTP结果。回调参数是：\n"+ new AutoPayResponseTrans().fromMessage(response));
		//超过1000分割成多个list
		List<List<AutoPayResult>> splitList = CollectionUtils.splitListBySize(response.getAutoPayResult(), FossConstants.ORACLE_MAX_IN_SIZE);
		if(CollectionUtils.isNotEmpty(splitList)){
			for(List<AutoPayResult> resultList : splitList){
				billAutoPayPtpService.dealAutoPayResult(resultList);
			}
		}
		//记录日志
		LOGGER.info("回调自动付款PTP结果结束。");
	}

	/**
	 * 财务共享异常处理
	 * 
	 * @author 302346-foss-Jiang Xun
	 * @date 2016-06-01 下午3:59:00
	 * @see com .deppon.esb.core.process.ICallBackProcess#errorHandler(java.lang.Object)
	 */
	@Override
	@Transactional
	// 此处一定要打事物标记，因为下面service没有事物
	public void errorHandler(Object arg0) throws ESBException {
		//记录日志
		LOGGER.info("回调自动付款PTP结果,错误处理结果开始。错误内容是："+arg0.toString());
		//获取异常参数
		ErrorResponse response = (ErrorResponse) arg0;
		// 获取esb异常信息
		CommonExceptionInfo info = response.getCommonExceptionInfo();
		//记录日志
		LOGGER.info("业务编号:" + info.getExceptioncode());
		//记录日志
		LOGGER.info("错误信息：" + info.getMessage());
		//记录日志
		LOGGER.info("错误信息：" + info.getDetailedInfo());
		// 如果异常头信息存在，则进行回滚操作。 此处异常exceptionCode 为 付款编号,付款类型
		if (StringUtils.isNotEmpty(info.getExceptioncode())) {
			
		}
		//记录日志
		LOGGER.error("回调自动付款PTP结果,错误处理结果结束。");
	}

	public void setBillAutoPayPtpService(
			IBillAutoPayPtpService billAutoPayPtpService) {
		this.billAutoPayPtpService = billAutoPayPtpService;
	}

}
