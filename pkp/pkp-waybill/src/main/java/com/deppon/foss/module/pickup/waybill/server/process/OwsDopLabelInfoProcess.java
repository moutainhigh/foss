package com.deppon.foss.module.pickup.waybill.server.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ErrorResponse;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.pojo.domain.foss2dop.LabelInfoJmsResponse;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabelPushProcessService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;

/**
 * 
 * OWS/DOP标签信息异步推送JMS远端服务请求回调处理
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-zhangfan,date:20160527,
 * </p>
 * 
 * @author dp-zhangfan
 * @date 20160527
 * @since
 * @version
 */
public class OwsDopLabelInfoProcess implements ICallBackProcess {
	protected final static Logger logger = LoggerFactory.getLogger(OwsDopLabelInfoProcess.class.getName());
	
	private transient ILabelPushProcessService labelPushProcessService;
	
	public OwsDopLabelInfoProcess(){
		
	}
	
	@Override
	public void callback(final Object response) throws ESBException {
		LabelInfoJmsResponse resp = (LabelInfoJmsResponse) response;
		if("1".equals(resp.getCode())){
			if(logger.isErrorEnabled()){
				logger.error("OwsDopLabelInfoProcess标签信息推送成功,运单号:"+resp.getWaybillNo());
			}
			labelPushProcessService.updateAfterJmsResponse(resp.getWaybillNo()
					, WaybillConstants.LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_SUCCESS, null);
		}else{
			if(logger.isErrorEnabled()){
				logger.error("OwsDopLabelInfoProcess标签信息推送失败,运单号:"+resp.getWaybillNo()
						+", 失败原因描述："+resp.getMessage());		
			}
			labelPushProcessService.updateAfterJmsResponse(resp.getWaybillNo()
					, WaybillConstants.LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_FAIL
					, resp.getMessage());
		}
		if(logger.isInfoEnabled()){
			logger.info("OwsDopLabelInfoProcess标签信息响应处理完成,运单号:"+resp.getWaybillNo());
		}
	}

	@Override
	public void errorHandler(final Object errorResponse) throws ESBException {
		ErrorResponse response = (ErrorResponse)errorResponse;
		if(response == null){
			response = new ErrorResponse();
		}
		StringBuilder sb =new StringBuilder();
		sb.append("业务编号:").append(response.getBusinessId()).append(",描述1：")
		.append(response.getBusinessDesc1()).append(",描述2：")
		.append(response.getBusinessDesc2()).append(",描述3：")
		.append(response.getBusinessDesc3());
		
		if(logger.isErrorEnabled()){
			logger.error("OwsDopLabelInfoProcess标签信息推送失败,业务编号:"+response.getBusinessId()
						+", 失败原因描述："+"ESB处理错误,", sb.toString());
		}
		String waybillNo = null;
		if(StringUtil.isNotEmpty(response.getBusinessId())){
			waybillNo = (response.getBusinessId().split("#")[0]);
		}
		if(StringUtil.isNotEmpty(waybillNo)){
			labelPushProcessService.updateAfterJmsResponse(waybillNo
					, WaybillConstants.LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_FAIL
					, "ESB处理错误,"+sb.toString());
		}
	}

	public void setLabelPushProcessService(
			ILabelPushProcessService labelPushProcessService) {
		this.labelPushProcessService = labelPushProcessService;
	}
	
}
