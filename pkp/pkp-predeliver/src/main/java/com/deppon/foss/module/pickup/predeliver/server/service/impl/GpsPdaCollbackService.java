package com.deppon.foss.module.pickup.predeliver.server.service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.foss.inteface.domain.pdatime.SyncPdaTimeResponse;
/**
 * FOSS系统同步PDA作业时间
 * @author 159231-foss-meiying
 * @date 2014-3-6 下午3:33:23
 */
public class GpsPdaCollbackService  implements ICallBackProcess
{
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(GpsPdaCollbackService.class.getName());
	private static final int  ONE= 1;
	@Override
	public void callback(Object response) throws ESBException {
		SyncPdaTimeResponse syncPdaTimeResponse =(SyncPdaTimeResponse)response;
		if(syncPdaTimeResponse!= null){
			if(ONE==syncPdaTimeResponse.getResult()){
				LOGGER.info("FOSS系统同步PDA作业时间 返回结果成功 返回内容:"+
						syncPdaTimeResponse.getOrderNo()+syncPdaTimeResponse.getWaybillNo()
						+syncPdaTimeResponse.getReason());//
			}else {
				LOGGER.error("FOSS系统同步PDA作业时间 返回结果失败 返回内容:"+
						syncPdaTimeResponse.getOrderNo()+syncPdaTimeResponse.getWaybillNo()
						+syncPdaTimeResponse.getReason());//MANA-1137 FOSS系统同步PDA作业时间
			}
		}else {
			LOGGER.error("FOSS系统同步PDA作业时间 返回空");//MANA-1137 FOSS系统同步PDA作业时间
		}
	}
	@Override
	public void errorHandler(Object errorResponse) throws ESBException {
		SyncPdaTimeResponse syncPdaTimeResponse =(SyncPdaTimeResponse)errorResponse;
		LOGGER.error("FOSS系统同步PDA作业时间 调用接口失败"+syncPdaTimeResponse.toString());
	}

}
