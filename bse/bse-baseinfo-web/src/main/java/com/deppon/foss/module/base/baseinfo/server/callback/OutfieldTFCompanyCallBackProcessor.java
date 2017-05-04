package com.deppon.foss.module.base.baseinfo.server.callback;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.ecs.inteface.domain.SyncOutfieldTFCompanyResponse;
import com.deppon.ecs.inteface.domain.failinfo.FailInfo;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.pojo.domain.foss2ecs.SyncOutfieldTFCompanyResponseTrans;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EsbCallBackLog;
/**
 * 用来存储发送外请车合同主体变更到ECS验证后的结果回调同步信息到数据库：无SUC
 * @author 313353-qiupeng
 * @date 2016-4-21 上午8:48:43
 * @since
 * @version
 */
public class OutfieldTFCompanyCallBackProcessor implements
	ICallBackProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(OutfieldTFCompanyCallBackProcessor.class);
    
    @Override
    public void callback(Object response) throws ESBException {
	LOGGER.info(" ***************************** Begin to record data ***************************** ");
	
	if (null != response) {
		SyncOutfieldTFCompanyResponse outfieldTFCompanyresponse = (SyncOutfieldTFCompanyResponse) response;
	    
	    LOGGER.info("send OutfieldTFCompany info,then receive callback info.外请车合同主体变更，收到回调信息：\n"
			    + new SyncOutfieldTFCompanyResponseTrans()
				    .fromMessage(outfieldTFCompanyresponse));
	    List<FailInfo> failInfoList = outfieldTFCompanyresponse.getFailInfos();
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
