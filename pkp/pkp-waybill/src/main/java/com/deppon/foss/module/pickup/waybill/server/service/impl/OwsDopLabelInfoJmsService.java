package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.pojo.domain.foss2dop.LabelInfoJmsRequest;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabelPushService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IOwsDopLabelInfoJmsService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabelInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.util.UUIDUtils;
import com.primeton.das.entity.impl.hibernate.exception.ExceptionUtils;


/**
 * 
 * JMS远端服务请求回调处理
 * OWS、DOP标签信息推送
 * 
 * @author dp-zhangfan 
 * @date 2016-05-27 下午5:07:42
 * @since
 * @version
 */
public class OwsDopLabelInfoJmsService implements IOwsDopLabelInfoJmsService {

	protected final static Logger logger = LoggerFactory.getLogger(OwsDopLabelInfoJmsService.class);
	
	private static final String TARGET_SYSTEM_OWS = "0";
	private static final String TARGET_SYSTEM_DOP = "1";
	
	private static final String ESB_SVC_CODE_DOP = "ESB_FOSS2ESB_RECEIVE_LTLLABEL";
	private static final String ESB_SVC_CODE_OWS = "ESB_FOSS2ESB_RECEIVE_LTL_ELEORDERINFO";
	
	private static final String ESB_HEADER_VER = "0.1";
	
	private transient ILabelPushService labelPushService;
	private transient IWaybillDao waybillDao;
	
	public OwsDopLabelInfoJmsService(){
		
	}

	@Override
	public ResultDto sendLabelInfo(String waybillNo, String labelProcessEntityId) {
		if(logger.isInfoEnabled()){
			logger.info("OWS、DOP标签信息推送接口, start : waybillNo = "+waybillNo);
		}
		
		if(StringUtils.isEmpty(waybillNo)){
			if(logger.isErrorEnabled()){
				logger.error("标签信息推送失败！ 运单号为空！");
			}
			ResultDto res = new ResultDto();
			res.setCode("0");
			res.setMsg("标签信息推送失败！ 运单号为空！");
			if(logger.isInfoEnabled()){
				logger.info("OWS、DOP标签信息推送 OwsDopLabelInfoJmsService.sendLabelInfo end : waybillNo = "+waybillNo);
			}
			return res;
		}
		
		// 准备消息头信息
		AccessHeader header = new AccessHeader();

		header.setVersion(ESB_HEADER_VER);
		//设置业务id为运单号#uuid
		header.setBusinessId(waybillNo+"#"+UUIDUtils.getUUID());
		
		LabelInfoJmsRequest request = new LabelInfoJmsRequest(); 
		
		request.setUuid(labelProcessEntityId);
		
		try{
			WaybillEntity waybill = waybillDao.queryWaybillByNo(waybillNo);//waybillPendingService.queryPendingByNo(waybillNo);
			if(waybill == null){
				if(logger.isErrorEnabled()){
					logger.error("标签信息推送失败！没有查询到运单实体！运单号："+waybillNo+", 目标系统值为："+request.getTargetSystem());
				}
				ResultDto res = new ResultDto();
				res.setCode("0");
				res.setMsg("标签信息推送失败！没有查询到运单实体！运单号："+waybillNo+", 目标系统值为："+request.getTargetSystem());
				if(logger.isInfoEnabled()){
					logger.info("OWS、DOP标签信息推送 OwsDopLabelInfoJmsService.sendLabelInfo end : waybillNo = "+waybillNo);
				}
				return res;
			}
			LabelInfoDto labelInfo = labelPushService.queryLabelInfos(waybill);
			BeanUtils.copyProperties(labelInfo, request);
		}catch(Exception e){
			if(logger.isErrorEnabled()){
				logger.error("OWS、DOP标签推送服务，获取标签信息失败，运单编号为"+waybillNo);
				logger.error("获取标签信息失败，异常信息为：",ExceptionUtils.getFullStackTrace(e));
			}
	
			ResultDto res = new ResultDto();
			res.setCode("0");
			res.setMsg("获取标签信息失败：" + getExceptoinMsg(e));
			if(logger.isInfoEnabled()){
				logger.info("OWS、DOP标签信息推送 OwsDopLabelInfoJmsService.sendLabelInfo end : waybillNo = "+waybillNo);
			}
			return res;
		}
		
		if(TARGET_SYSTEM_DOP.equals(request.getTargetSystem())){
			header.setEsbServiceCode(ESB_SVC_CODE_DOP);
		}else if(TARGET_SYSTEM_OWS.equals(request.getTargetSystem())){
			header.setEsbServiceCode(ESB_SVC_CODE_OWS);
		}else{
			if(logger.isErrorEnabled()){
				logger.error("标签信息推送失败！标签信息推送时，零担电子运单对应的订单的目标系统值不正确！运单号："+waybillNo+", 目标系统值为："+request.getTargetSystem());
			}
			ResultDto res = new ResultDto();
			res.setCode("0");
			res.setMsg("标签信息推送失败！标签信息推送时，零担电子运单对应的订单的目标系统值不正确！运单号："+waybillNo+", 目标系统值为："+request.getTargetSystem());
			if(logger.isInfoEnabled()){
				logger.info("OWS、DOP标签信息推送 OwsDopLabelInfoJmsService.sendLabelInfo end : waybillNo = "+waybillNo);
			}
			return res;
		}
		if(logger.isInfoEnabled()){
			logger.info("OWS、DOP标签信息推送接口, 目标系统："+request.getTargetSystem()+",  start : waybillNo = "+waybillNo+", 开始发送请求");
		}
		
		// 标识更新状态为成功
		String code = "1";
		// 初始化消息
		String msg = "标签信息推送成功！";
				
		// 发送请求
		try {
			ESBJMSAccessor.asynReqeust(header, request);
		} catch (Exception e) {
			// 对异常进行处理
			String expMessage =  getExceptoinMsg(e);
			if(logger.isErrorEnabled()){
				logger.error("获取标签信息失败，运单号为"+waybillNo+"，异常信息为：",e);
			}
			code = "0";
			msg = "JMS请求发送失败：" + expMessage;
		}

		//封装返回的结果信息
		ResultDto res = new ResultDto();
		res.setCode(code);
		res.setMsg(msg);
		if(logger.isInfoEnabled()){
			logger.info("标签信息推送服务OwsDopLabelInfoJmsService.sendLabelInfo end : " + ReflectionToStringBuilder.toString(res));
		}
		return res;
	}
	
	private String getExceptoinMsg(Throwable t){
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		t.printStackTrace(new PrintWriter(buf, true));
		String  expMessage = buf.toString();
		try {
			buf.close();
		} catch (IOException e1) {
			if(logger.isErrorEnabled()){
				logger.error(ExceptionUtils.getFullStackTrace(e1));
			}
		}
		
		return expMessage;
	}

	public void setLabelPushService(ILabelPushService labelPushService) {
		this.labelPushService = labelPushService;
	}

	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}

}
