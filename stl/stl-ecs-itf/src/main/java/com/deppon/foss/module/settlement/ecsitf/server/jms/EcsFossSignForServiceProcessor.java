package com.deppon.foss.module.settlement.ecsitf.server.jms;

import java.util.Date;

import javax.annotation.Resource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.deppon.dpap.esb.mqc.core.exception.ESBBusinessException;
import com.deppon.dpap.esb.mqc.core.process.IProcess;
import com.deppon.ecs.inteface.domain.SyncOutfieldSignForRequest;
import com.deppon.ecs.inteface.domain.SyncOutfieldSignForResponse;
/*import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;*/
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.settlement.closing.api.server.service.ILogEcsFossService;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.ecsitf.server.rs.ISignForService;

public class EcsFossSignForServiceProcessor implements IProcess {
	
	/**
	 * 日志属性
	 */
	private static final Logger logger = LogManager.getLogger(EcsFossSignForServiceProcessor.class);

	@Resource
	private ISignForService signForService;
	

	@Resource
	private ILogEcsFossService logEcsFossService;
	
	//@Log(esbCode="FOSS_ESB2FOSS_ECS_SYNC_SIGNRESULT")
	@SuppressWarnings("finally")
	@Override
	public Object process(Object req) throws ESBBusinessException {
		Date date = new Date();
		// 实例化该变量
		SyncOutfieldSignForResponse response = new SyncOutfieldSignForResponse();
		SyncOutfieldSignForRequest request = (SyncOutfieldSignForRequest) req;
		//响应状态1代表成功，0代表失败
		// 判空
		if (req == null) {
			response.setMessage(new ESBBusinessException("SyncOutfieldSignForRequest 为空！").toString());
			try{
				logEcsFossService.setLog(request, response, "FOSS_ESB2FOSS_ECS_SYNC_SIGNRESULT", "null", false, date);
				logger.info("FOSS_ESB2FOSS_ECS_SYNC_SIGNRESULT日志记录成功");
			}catch(Exception t){
				logger.info("FOSS_ESB2FOSS_ECS_SYNC_SIGNRESULT日志记录失败");
			}
			return response;
		}		
				
		if (request.getWaybillNo() == null || request.getWaybillNo().equals("")) {
			response.setMessage("运单号不能为空");
		}
		
		WaybillSignResultEntity waybillSignResultEntity=null;
		//生效时间必填，不能为空
		/*if(waybillSignResultEntity.getCreateTime()==null){
			waybillSignResultEntity.setCreateTime(new Date());
		}*/		
		
		try {
			// 直接把传过来的值通过json转换为目标实体
			waybillSignResultEntity=JSONObject.parseObject(JSONObject.toJSONString(request),WaybillSignResultEntity.class);
			
			// 签收件数设置为固定值
			waybillSignResultEntity.setSignGoodsQty(1);
			logger.info("查询" + waybillSignResultEntity.getWaybillNo() + "有没有");
			
			int count = signForService.updateOrInsertSignFor(waybillSignResultEntity);
			if (count == 0) {
				logger.info("新增：" + waybillSignResultEntity.getWaybillNo());
				response.setResult(request.getWaybillNo() + ":新增成功");
			} else if (count == 1) {
				logger.info("更新：" + waybillSignResultEntity.getWaybillNo());
				response.setResult(request.getWaybillNo() + ":更新成功");
			} else{
				response.setMessage(request.getWaybillNo() + ":数据库中有多条重复的信息");
				throw new SettlementException(request.getWaybillNo() + ":数据库中有多条重复的信息");
			}			

		} catch (Exception e) {
			logger.info("FOSS_ESB2FOSS_ECS_SYNC_SIGNRESULT异常：" + e.getMessage());
			response.setMessage(request.getWaybillNo() + e.getMessage());
			try{
				logEcsFossService.setLog(request, response, "FOSS_ESB2FOSS_ECS_SYNC_SIGNRESULT", waybillSignResultEntity.getWaybillNo(), false, date);
				logger.info("FOSS_ESB2FOSS_ECS_SYNC_SIGNRESULT日志记录成功");
			}catch(Exception t){
				logger.info("FOSS_ESB2FOSS_ECS_SYNC_SIGNRESULT日志记录失败");
			}
		}finally{
			logger.info("FOSS_ESB2FOSS_ECS_SYNC_SIGNRESULT接口调用结束！");
			return response;
		}
	}

	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		/**
		 * 打印日志
		 */
		logger.error("签收出错:" + req);
		return null;
	}
	

}
