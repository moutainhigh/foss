/**
 * @remark 
 * @author YANGBIN
 * @date 2014-3-10 上午8:44:18
 */
package com.deppon.foss.module.pickup.order.server.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.foss.inteface.domain.collectiontask.SyncCollectionTaskResponse;

/** 
 * @ClassName: GPSOrderTaskBackPrcess 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author YANGBIN
 * @date 2014-3-10 上午8:44:18 
 *  
 */
public class GPSOrderTaskBackPrcess implements ICallBackProcess {
	protected final Logger logger = LoggerFactory.getLogger(GPSOrderTaskBackPrcess.class.getName());
	/* (非 Javadoc) 
	 * <p>Title: callback</p> 
	 * <p>Description: </p> 
	 * @param response
	 * @throws ESBException 
	 * @see com.deppon.esb.core.process.ICallBackProcess#callback(java.lang.Object) 
	 */
	@Override
	public void callback(Object response) throws ESBException {
		// TODO Auto-generated method stub
		SyncCollectionTaskResponse res = (SyncCollectionTaskResponse) response;
		int result = res.getResult();
		String orderNo = res.getOrderNo();
		if(result == 1){		
			logger.info("成功将"+orderNo+"传给GPS");
		}else{
			logger.info(orderNo+"传给GPS失败");
		}
	}

	/* (非 Javadoc) 
	 * <p>Title: errorHandler</p> 
	 * <p>Description: </p> 
	 * @param errorResponse
	 * @throws ESBException 
	 * @see com.deppon.esb.core.process.ICallBackProcess#errorHandler(java.lang.Object) 
	 */
	@Override
	public void errorHandler(Object errorResponse) throws ESBException {
		SyncCollectionTaskResponse res = (SyncCollectionTaskResponse) errorResponse;
		int result = res.getResult();
		String reason = res.getReason();
		String orderNo = res.getOrderNo();
		if(result == 0){		
			logger.info(orderNo+"传给GPS失败,失败原因："+reason);
		}
	}

}
