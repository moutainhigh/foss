package com.deppon.pda.bdm.module.foss.accept.server.service.impl;

import org.jgroups.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.inteface.domain.pdadop.SyncVerifyCodeInfoRequest;
import com.deppon.esb.pojo.transformer.json.SyncVerifyCodeInfoRequestTrans;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SynchronousExternalSystemException;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.GougouPdaJmsEntity;

/**
 * PDA推送数据至DOP的消费发送Service
 * @author 245955
 *
 */
public class PdaEsbProducerServiceImpl {
	private static final Logger log = LoggerFactory.getLogger(PdaEsbProducerServiceImpl.class);
	//服务编码
	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_ORDER_PUSH_MESSAGE";
	/**
	 * 同步渠道单号和验证码到DOP
	 * 
	 * @author 245955
	 * @Date 2015-12-21
	 * @param dto
	 * 
	 */
	public void syncWaybillCodeToDop(GougouPdaJmsEntity gougouPdaJmsEntrity) {
		// 判断传入的集合是否为空
		if (gougouPdaJmsEntrity != null) {
			// 发送请求消息类
			SyncVerifyCodeInfoRequest request=new SyncVerifyCodeInfoRequest();
			// 设置验证码
			request.setVerifyCode(gougouPdaJmsEntrity.getVerifyCode());
			// 设置渠道单号
			request.setLogisticNo(gougouPdaJmsEntrity.getLogisticNo());
			// 备注
			request.setRemark("");
			AccessHeader accessHeader = new AccessHeader();
			// 服务编码
			accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
			// 版本
			accessHeader.setVersion("0.1");
			accessHeader.setBusinessDesc1("send SyncWaybillCode To DOP System ：同步裹裹数据到DOP! \n");
		
			if(gougouPdaJmsEntrity.getVerifyCode()!=null && gougouPdaJmsEntrity.getLogisticNo()!=null){
			  accessHeader.setBusinessId(gougouPdaJmsEntrity.getLogisticNo()+"--"+gougouPdaJmsEntrity.getVerifyCode()+"--"+UUID.randomUUID().toString());
			}else{
			  accessHeader.setBusinessId(UUID.randomUUID().toString());
			}
			accessHeader.setBusinessDesc2("推送PDA信息至DOP1!");
			accessHeader.setBusinessDesc3("推送PDA信息至DOP2!");
			
			try {
				log.info("开始调用裹裹信息：\n"
						+ new SyncVerifyCodeInfoRequestTrans().fromMessage(request));

				ESBJMSAccessor.asynReqeust(accessHeader, request);

			   log.info("结束调用裹裹信息：\n"
						+ new SyncVerifyCodeInfoRequestTrans().fromMessage(request));

			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new SynchronousExternalSystemException(" 同步裹裹信息",
						" 同步裹裹信息 发送数据到ESB错误");
			}
		}
	}
} 
 