package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.settlement.closing.api.server.dao.ILogDao;
import com.deppon.foss.module.settlement.closing.api.server.service.ILogEcsFossService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MqLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.util.UUIDUtils;
  
/** 
 * 日志记录业务逻辑接口实现类 
 * @author 326181 
 */  
public class LogEcsFossService implements ILogEcsFossService{

	/**
	 * 日志
	 */
	private final Logger logger = LogManager.getLogger(getClass());
	
	private ILogDao logDao;
	
	public ILogDao getLogDao() {
		return logDao;
	}

	public void setLogDao(ILogDao logDao) {
		this.logDao = logDao;
	}  
	
	/**
     * 设置日志实体参数
     * @param request
     * @param response
     * @param esbCode
     * @param waybillNo
     */
	@Override
	public void setLog(Object request, Object response, String esbCode,String waybillNo,Boolean flag, Date date) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		String startTime = date != null  ? "接口起始时间：" + sdf.format(date) : "";
		MqLogEntity mqLogEntity= new MqLogEntity();
		String requestMsg = request instanceof String ? request+"" : JSONObject.toJSONString(request);
		String responseMsg = response instanceof String ? response+"" : JSONObject.toJSONString(response);
		
		logger.info("运单号:"+waybillNo+",esbCode:"+esbCode);
		logger.info("运单号:"+waybillNo+",request:"+requestMsg);
		logger.info("运单号:"+waybillNo+",response:"+responseMsg);
		responseMsg = startTime + responseMsg;
		//requestMsg = subStringParams(requestMsg, request);
		responseMsg = subStringParams(responseMsg, response);
		mqLogEntity.setEsbCode(esbCode);
		mqLogEntity.setId(UUIDUtils.getUUID());
		mqLogEntity.setCreateTime(new Date());
		mqLogEntity.setWaybillNo(waybillNo);
		mqLogEntity.setRequestMsg(requestMsg);
		mqLogEntity.setResponseMsg(responseMsg);
		if(flag){
			this.addSuccessLog(mqLogEntity);
		} else {
			this.addFailLog(mqLogEntity);
		}
	}
	
	/**
	 * 截取参数
	 * @param params
	 * @param obj
	 * @return
	 */
	private String subStringParams(String params, Object obj) {
		String regex = "[\u4e00-\u9fff]";
		//判断有多少个中文
		int count = (" " + obj + " ").split (regex).length - 1;
		if (params.length()+count>SettlementReportNumber.TWO_THOUSAND) {
			params = params.substring(0, SettlementReportNumber.ONE_THOUSAND_AND_EIGHT_HUNDRED);
		} 
		return params;
	}

	@Override
	public void doUpdateLogEntity(MqLogEntity log) {
		//运单同步、同步签收不添加成功日志
		if (!SettlementDictionaryConstants.FOSS_ESB2FOSS_ECS_SYNC_WAYBILL.equals(log.getEsbCode()) 
				&& !SettlementDictionaryConstants.FOSS_ESB2FOSS_ECS_SYNC_SIGNRESULT.equals(log.getEsbCode())) {
			log.setResponseMsg("异常任务查询页面重新执行成功");
			log.setCreateTime(new Date());
			this.addSuccessLog(log);
		}
		//删除执行成功的日志记录
		this.logDao.doDelFailLogById(log.getId());
	}
	
	@Override
	public List<MqLogEntity> queryMqLogEntity(Map<String, Object> params, int start, int limit) {
		List<MqLogEntity> logList = new ArrayList<MqLogEntity>();
		//开单
		logList = this.logDao.queryFailLogEntity(params, start, limit);
		return logList;
	}

	@Override
	public void addSuccessLog(MqLogEntity log) {
		this.logDao.insertSuccessLog(log);
	}

	@Override
	public void addFailLog(MqLogEntity log) {
		this.logDao.insertFailLog(log);
	}
	
	@Override
	public Long queryLogTotalCount(Map<String, Object> params) {
		return this.logDao.queryLogTotalCount(params);
	}      
} 