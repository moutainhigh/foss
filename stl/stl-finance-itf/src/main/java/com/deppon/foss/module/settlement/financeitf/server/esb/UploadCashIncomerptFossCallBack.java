package com.deppon.foss.module.settlement.financeitf.server.esb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ICallBackProcess;
//import com.deppon.esb.inteface.domain.finincomecash.ResultInfo;
import com.deppon.esb.inteface.domain.finincomecash.UploadCashIncomeResponse;

/**
 * 返回上传现金收入报表（缴款）工作流
 * 
 * @author 095793-foss-LiQin
 * @date 2012-11-26 下午3:14:23
 */
public class UploadCashIncomerptFossCallBack implements ICallBackProcess {

	/**
	 * 返回上传现金收入报表（缴款）工作流日志
	 */
	protected static final Logger LOGGER = LoggerFactory.getLogger(UploadCashIncomerptFossCallBack.class);

	/**
	 * 返回上传现金收入报表（缴款）报表
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 29, 2012 3:53:34 PM
	 * @see com.deppon.esb.core.process.ICallBackProcess#callback(java.lang.Object)
	 */
	@Override
	public void callback(Object obj) throws ESBException {
		//记录日志
		LOGGER.info("开始处理上传现金缴款报表返回...");
		
		// 返回工作流
		UploadCashIncomeResponse response = (UploadCashIncomeResponse) obj;
		//记录日志
		LOGGER.info("paymentDate："+String.valueOf(response.getPaymentDate()));
		LOGGER.info("结束处理上传现金缴款报表返回...");
	}

	/**
	 * 异常处理
	 * 
	 * @author foss-wangxuemin
	 * @date Nov 29, 2012 3:46:57 PM
	 * @see com.deppon.esb.core.process.ICallBackProcess#errorHandler(java.lang.Object)
	 */
	@Override
	public void errorHandler(Object arg0) throws ESBException {
		LOGGER.error("返回上传现金收入缴款工作流错误！");
	}

}
