package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import org.apache.commons.lang.StringUtils;
import org.jgroups.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.cc.module.foss.server.service.CommException;
import com.deppon.cc.module.foss.server.service.IFossToCCService;
import com.deppon.cc.module.foss.shared.domain.AnswerReminderRequest;
import com.deppon.cc.module.foss.shared.domain.AnswerReminderResponse;
import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISyncBackCallCenterInfoToCCService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CallCenterWaybillInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.CallCenterWaybillInfoException;

/**
 * 
 * 同步催运单反馈信息给CC
 * Web Service客户端服务接口实现
 * @author 132599-foss-shenweihua
 * @date 2014-7-22 上午9:57:32
 */
public class SyncBackCallCenterInfoToCCService  implements ISyncBackCallCenterInfoToCCService{
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SyncBackCallCenterInfoToCCService.class);
	
	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_FEEDBACK_REMINDER";

	private IFossToCCService fossToCCService;
	
	public IFossToCCService getFossToCCService() {
		return fossToCCService;
	}

	public void setFossToCCService(IFossToCCService fossToCCService) {
		this.fossToCCService = fossToCCService;
	}

	/**
	 * 
	 * 同步催运单反馈信息给CC
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2014-7-22 上午9:57:32
	 */
	@Override
	public boolean syncBackCallCenterInfoToCC(CallCenterWaybillInfoEntity entity) {
		LOGGER.info("syncExpressTrainProgramInfo:send info start.............");

		ESBHeader header = new ESBHeader();
		// 设置服务编码
		header.setEsbServiceCode(ESB_SERVICE_CODE);
		header.setMessageFormat("SOAP");
		header.setSourceSystem("FOSS");
		header.setExchangePattern(1);
		header.setVersion("1.0");
		header.setRequestId(UUID.randomUUID().toString());
		
		Holder<ESBHeader> holder = new Holder<ESBHeader>(header);
		if(null != entity){
			//方案名称作为唯一标识
			header.setBusinessId("businessId"+entity.getPressWaybillNo());
			AnswerReminderRequest answerReminderRequest  = new AnswerReminderRequest();
			answerReminderRequest = transFossToEsb(answerReminderRequest,entity);
			boolean flag =false;
			try {
				AnswerReminderResponse response = fossToCCService.answerReminder(holder, answerReminderRequest);
				LOGGER.info("syncBackCallCenterInfo:send info end.............");
				LOGGER.debug("syncBackCallCenterInfo:result: "+response.getIfSuccess());
				if(("Y").equals(response.getIfSuccess())){
					flag = true;
				}else{
					if("N".equals(response.getIfSuccess())){
						throw new CallCenterWaybillInfoException(response.getErrMsg());
					}
					flag = false;
				}
				return flag;
			    } catch (CommException e) {
			    	return false;
				}
		}
		return false;
	}
	
	/**
	 * 
	 * 将催运单反馈信息set给esb实体信息
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2014-7-22 上午9:57:32
	 */
	private AnswerReminderRequest transFossToEsb(AnswerReminderRequest request,CallCenterWaybillInfoEntity entity){
		if(null==entity){
			return null;
		}
		//校验关键字段信息不能为空
		if(StringUtils.isEmpty(entity.getDealDept())){
			 throw new BusinessException("", "同步催运单反馈信息时，处理部门为空！");
		}
		if(StringUtils.isEmpty(entity.getPressWaybillNo())){
			 throw new BusinessException("", "同步催运单反馈信息时，催运单凭证号为空！");		
		}
		if(StringUtils.isEmpty(entity.getWaybillNo())){
			 throw new BusinessException("", "同步催运单反馈信息时，运单号为空！");
		}
		if(StringUtils.isEmpty(entity.getDealUserName())){
			 throw new BusinessException("", "同步催运单反馈信息时，处理人为空！");
		}
		if(entity.getDealTime()==null){
			 throw new BusinessException("", "同步催运单反馈信息时，处理时间为空！");
		}
		//催单凭证号
		request.setId(entity.getPressWaybillNo());
		//运单号
		request.setCode(entity.getWaybillNo());
		//是否已解决（Y是 /N否）
		request.setHasDone(entity.getHasDone());
		//处理时间
		request.setDealTime(convertToXMLGregorianCalendar(entity.getDealTime()));
		//反馈信息
		request.setCallbackMsg(entity.getCallBackMsg());
		//受理部门
		request.setAcceptDepartment(entity.getDealDept());
		//处理人
		request.setDealUser(entity.getDealUserName());
		return request;
	}
	
	/**
	 * 转化date类型时间方法
	 * @param cal
	 * @return
	 */
	private XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {
             e.printStackTrace();
        }
        return gc;
    }
	
}
