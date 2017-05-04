package com.deppon.foss.module.pickup.crm.server.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.failworkflow.FailWaybillRequest;
import com.deppon.esb.inteface.domain.failworkflow.FailWaybillResponse;
import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.base.common.api.shared.exception.MessageException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPendingService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.UUIDUtils;

/**
 * @项目：快递自动补录项目
 * @功能：录单中心退回供应商消息推送接口
 * @author 268974 wangzhili
 * @date：2015-08-10
 */
public class FossFailInfoToWicsService implements IProcess{
	
	public static final String MSG_TYPE__SUPPLIER_FAIL_PATCH = "SUPPLIER_FAIL_PATCH";
	
	/**
	 * 日志消息
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FossFailInfoToWicsService.class);
	/**
	 * 站内消息明细Dao
	 */
	private IMessageService messageService;
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}
	private IWaybillPendingService waybillPendingService;
	public void setWaybillPendingService(
			IWaybillPendingService waybillPendingService) {
		this.waybillPendingService = waybillPendingService;
	}
	/**
	 * @项目：快递自动补录项目
	 * @功能：录单中心退回供应商信息推送
	 * @author:268974  wangzhili
	 * @date：2015-08-10 
	 */
	@Override
	public Object process(Object obj) throws ESBBusinessException {
		// TODO Auto-generated method stub
		FailWaybillRequest failWaybillRequest;
		FailWaybillResponse failWaybillResponse = new FailWaybillResponse();
		// 成功继续执行，失败返回结果
		try {
			failWaybillRequest = (FailWaybillRequest) obj;
		} catch (Exception e) {
			failWaybillResponse.setState("1");
			return null;
		}
		if (failWaybillRequest != null) {
			// 获得请求参数
			String waybillNo = failWaybillRequest.getWaybillNo();
			String failMsg = failWaybillRequest.getFailMsg();
			String context="";
			if (waybillNo != null) {
				StringBuffer stringBuffter = new StringBuffer();
				// 运单号与失败消息的拼接
				if(null!=failMsg&&StringUtil.isNotBlank(failMsg)){
					 context = stringBuffter.append(waybillNo).append(",")
							.append(failMsg).toString();
				}else{
					context = "供应商未备注退回原因";
				}
				// 接口调用
				//sonar 优化 start -------------
				/*ResultDto result = sendInnerMess(
						waybillNo, context);
				
				if (result == null) {
					// 插入信息失败，则返回失败
					failWaybillResponse.setState("1");
				} else {
					// 插入成功
					failWaybillResponse.setState("1");
				}*/
				
				sendInnerMess(waybillNo, context);
				failWaybillResponse.setState("1");
				//sonar 优化 end -------------
			} else {
				return null;
			}
		} else {
			//请求没有数据，不做处理
			failWaybillResponse.setState("1");
		}
		return null;
	}

	@Override
	public Object errorHandler(Object obj) throws ESBBusinessException {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 图片退回消息保存到消息表
	 */
	private ResultDto sendInnerMess(String waybillNo,String content) {
	 ResultDto dto = new ResultDto();
	 //结果返回对象
	 try{
	   WaybillPendingEntity pendingEntity = waybillPendingService.queryPendingByNo(waybillNo);
		  if(null!=pendingEntity){
		  //平台内部发给营业员的消息 保留
		  InstationJobMsgEntity entity = new InstationJobMsgEntity();
		 //发送人员编码
		 entity.setSenderCode("N/A");
		 //发送人员
		 entity.setSenderName("供应商");
		 //发送组织编码
		 entity.setSenderOrgCode("N/A");
		 //发送组织
		 entity.setSenderOrgName("/");
		 //接收方组织编码
		 if(null!=pendingEntity.getReceiveOrgCode()){
		    entity.setReceiveOrgCode(pendingEntity.getReceiveOrgCode());
		 }else{
		   entity.setReceiveOrgCode(pendingEntity.getCreateOrgCode());
		 }
		 //补录失败
		 entity.setMsgType(MSG_TYPE__SUPPLIER_FAIL_PATCH);
		 //content
		 entity.setContext(content);
		 //接收方类型 MSG__RECEIVE_TYPE__ORG  组织 
		 entity.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
		 //id
		 entity.setId(UUIDUtils.getUUID());
		 // 未处理
		 entity.setStatus(MessageConstants.MSG__STATUS__PROCESSING);
		 //发送时间
		 entity.setPostDate(new Date());
		 //创建时间
		 messageService.createBatchInstationMsg(entity);
		 //成功标志
		 dto.setCode(ResultDto.SUCCESS);
		 dto.setMsg("发送成功");
		   return dto;
		}else{
	       return null;
	     }
	}catch(MessageException e){
	//出现异常
	LOGGER.error("error", e);
	//发送失败
	throw new WaybillValidateException("发送消息失败！");
	}
  }	
}
