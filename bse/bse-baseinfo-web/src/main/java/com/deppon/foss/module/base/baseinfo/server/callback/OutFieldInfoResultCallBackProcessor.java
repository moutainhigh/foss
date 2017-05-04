package com.deppon.foss.module.base.baseinfo.server.callback;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.pojo.transformer.jaxb.OutFieldInfoResponseTrans;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EsbCallBackLog;
import com.deppon.oa.ows.inteface.domain.SyncExternalPropertyResponse;
import com.deppon.oa.ows.inteface.domain.failinfo.FailInfo;
/**
 * 用来存储发送外场信息到OA验证后的结果回调同步信息到数据库：无SUC
 * @author 187862-dujunhui
 * @date 2015-1-5 下午6:48:43
 * @since
 * @version
 */
public class OutFieldInfoResultCallBackProcessor implements
	ICallBackProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(OutFieldInfoResultCallBackProcessor.class);
    
    @Override
    public void callback(Object response) throws ESBException {
	LOGGER.info(" ***************************** Begin to record data ***************************** ");
	
	if (null != response) {
	    SyncExternalPropertyResponse externalPropertyInfoResponse = (SyncExternalPropertyResponse) response;
	    
	    LOGGER.info("send OutField info,then receive callback info.同步外场信息，收到回调信息：\n"
			    + new OutFieldInfoResponseTrans()
				    .fromMessage(externalPropertyInfoResponse));
		    List<FailInfo> failInfoList = externalPropertyInfoResponse.getFailInfos();
		    for (FailInfo info : failInfoList) {
			EsbCallBackLog callBackLog = new EsbCallBackLog();//记录回调函数返回的信息
			callBackLog.setId(info.getId());
			callBackLog.setReason(info.getReason());
			callBackLog.setCreateTime(new Date());
	    }
	}
	
	LOGGER.info(" ***************************** End to record data ***************************** ");
    }

    @Override
    public void errorHandler(Object errorResponse) throws ESBException {
	LOGGER.error("ESB处理错误");
    }
}
