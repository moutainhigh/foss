package com.deppon.pda.bdm.module.foss.clear.server.service.impl;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.deppon.ar.bamp.client.listener.RequestContainer;
import com.deppon.dpap.rocketmq.Action;
import com.deppon.dpap.rocketmq.annotation.Service;
import com.deppon.pda.bdm.module.core.server.httpService.OperationFactory;
import com.deppon.pda.bdm.module.core.server.service.IAsyncMsgUploadService;
import com.deppon.pda.bdm.module.core.server.service.impl.ValidateService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.OperationConstant;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoginConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.domain.PdaInfo;
import com.deppon.pda.bdm.module.core.shared.domain.ReqData;
import com.deppon.pda.bdm.module.core.shared.domain.ReqJsonData;
import com.deppon.pda.bdm.module.core.shared.domain.Result;
import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgTaskEntity;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.JsonNotNullException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.BeanFactory;
import com.deppon.pda.bdm.module.core.shared.util.CodeParseUtil;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.StringUtils;

@Service(serviceCode = "PDA_TO_FOSS_LOGIN", requestClass = String.class)
public class PdaRockteMqService implements Action {
	private static Logger log = Logger.getLogger(PdaRockteMqService.class);

	@Override
	public void action(Object obj) throws MQClientException {
		// 获得包头和包体的值
		String reqJson = (String) obj;
		System.out.println("reqJson======" + reqJson);
		if (StringUtils.isEmpty(reqJson)) {
			throw new JsonNotNullException();
		}
		// Argument.hasText(reqJson, "jsonStr");
		try {
			reqJson = new String(reqJson.getBytes("iso-8859-1"), "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		log.info("MQ接收到的【清仓扫描】数据:" + reqJson);       
		// 1.解析jsonStr 获取N个请求json
		ReqData reqData = JsonUtil.parseJsonToObject(ReqData.class, reqJson);
		if(reqData.getReqData()!=null){
		for (ReqJsonData reqJsonData : reqData.getReqData()) {
			// 1.获取PdaInfo
			String pdaInfoStr = reqJsonData.getPdaInfo();
			AsyncMsg asyncMsg = JsonUtil.parseJsonToObject(AsyncMsg.class, pdaInfoStr);
			// 获取Body
			asyncMsg.setContent(reqJsonData.getBody());
			// 校验是否是外请车司机登录
			if (asyncMsg.getUserCode() != null && Constant.EXTERNAL_DRIVER.equals(asyncMsg.getUserCode())) {
				ScanMsgTaskEntity entity = null;
				try {
					entity = JsonUtil.parseJsonToObject(ScanMsgTaskEntity.class, reqJsonData.getBody());
				} catch (Exception e) {
					log.info("com.deppon.pda.bdm.module.core.server.httpService.PdaHttpService:ScanMsgTaskEntity实体解析异常"
							+ e);
				}
				if (entity != null) {
					asyncMsg.setId(entity.getId());
					asyncMsg.setTaskCode(entity.getTaskCode());
				}
				// 验证合法性
				ValidateService validateService = (ValidateService) BeanFactory.getBean("validateService");
				PdaInfo pdaInfo = JsonUtil.parseJsonToObject(PdaInfo.class, pdaInfoStr);
				// 1、OCB_08为设备注册接口，此时pdaInfo肯定不存在
				// 2、如果操作类型是OCB_08 则不做校验
				if (!"OCB_08".equals(asyncMsg.getOperType())) {
					validateService.check(pdaInfo);
				}
				/*
				 * =====================插入异步表================
				 * ========================
				 */
				IAsyncMsgUploadService service = (IAsyncMsgUploadService) BeanFactory
						.getBean(OperationConstant.SERVICE_ASYNMSG_UPLOAD);

				// 异步接入BAM监控
				RequestContainer.setMethodName(new StringBuffer()
						.append("com.deppon.pda.bdm.module.core.server.service.impl.AsyncMsgUploadService.saveAsyncMsg")
						.toString());
				RequestContainer.setNamespace("/pkp-pda-itf");
				RequestContainer.setEmpCode(asyncMsg.getUserCode());
				// 插入异步表
				String saveAsyncMsg = service.saveAsyncMsg(asyncMsg);
				log.info("MQ消息记录到异步表成功返回id：" + saveAsyncMsg);
				/*
				 * =====================插入异步表================
				 * ========================
				 */
			} else {
				if (checkDept(asyncMsg.getOperType())) {
					// 根据员工工号查询该工号对应的部门ID
					asyncMsg.setDeptCode(CodeParseUtil.getDeptId(asyncMsg.getUserCode()));
					Argument.hasText(asyncMsg.getDeptCode(), "asyncMsg.deptCode");
				}
				if (checkDept(asyncMsg.getOperType())) {
					// 查询部门编码，如果是快递员，则返回快递员所在的营业部的编码
					asyncMsg.setDeptCode(CodeParseUtil.getDeptCode(asyncMsg.getDeptCode(), // 部门ID
							asyncMsg.getUserCode(), asyncMsg.getUserType()));
					Argument.hasText(asyncMsg.getDeptCode(), "asyncMsg.deptCode");
				}

				ScanMsgTaskEntity entity = null;
				try {
					entity = JsonUtil.parseJsonToObject(ScanMsgTaskEntity.class, reqJsonData.getBody());
				} catch (Exception e) {
					log.info("com.deppon.pda.bdm.module.core.server.httpService.PdaHttpService:ScanMsgTaskEntity实体解析异常"
							+ e);
				}

				if (entity != null) {
					asyncMsg.setId(entity.getId());
					asyncMsg.setTaskCode(entity.getTaskCode());
				}
				// 验证合法性
				ValidateService validateService = (ValidateService) BeanFactory.getBean("validateService");
				PdaInfo pdaInfo = JsonUtil.parseJsonToObject(PdaInfo.class, pdaInfoStr);
				// 1、OCB_08为设备注册接口，此时pdaInfo肯定不存在
				// 2、如果操作类型是OCB_08 则不做校验
				if (!"OCB_08".equals(asyncMsg.getOperType())) {
					validateService.check(pdaInfo);
				}

				/*
				 * =====================插入异步表================
				 * ========================
				 */
				IAsyncMsgUploadService service = (IAsyncMsgUploadService) BeanFactory
						.getBean(OperationConstant.SERVICE_ASYNMSG_UPLOAD);

				// 异步接入BAM监控
				RequestContainer.setMethodName(new StringBuffer()
						.append("com.deppon.pda.bdm.module.core.server.service.impl.AsyncMsgUploadService.saveAsyncMsg")
						.toString());
				RequestContainer.setNamespace("/pkp-pda-itf");
				RequestContainer.setEmpCode(asyncMsg.getUserCode());
				// 插入异步表
				String saveAsyncMsg = service.saveAsyncMsg(asyncMsg);
				log.info("MQ消息记录到异步表成功返回id：" + saveAsyncMsg);
				/*
				 * =====================插入异步表================
				 * ========================
				 */
			}   

		}
	}
	}

	/**
	 * 
	 * @Description: TODO
	 * @param operType
	 * @return
	 * @return boolean
	 * @date 2013-9-23 下午2:28:46
	 */
	private boolean checkDept(String operType) {
		// 程序版本更新\PDA登录\程序模块版本更新 \NCI登录 不需要验证部门
		if (!operType.equals(LoginConstant.OPER_TYPE_SYS_VER_DNLD.VERSION)
				&& !operType.equals(LoginConstant.OPER_TYPE_SYS_LOGIN.VERSION)
				&& !operType.equals(LoginConstant.OPER_TYPE_SYS_MODULE_VER_DNLD.VERSION)
				&& !operType.equals(LoginConstant.OPER_TYPE_NCI_LOGIN.VERSION)) {
			return true;
		} else {
			return false;
		}
	}

}
