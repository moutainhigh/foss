package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.settlement.common.api.server.service.IFossToFinsRemitCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.domain.RequestGreenHandWrapEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IEcsFossErrorLogJobDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.IEcsFossErrorLogJobService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.EcsFossErrorLogJobEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
  
/**
 * 快递对接FOSS,JOB定时执行service  
 * @author 326181
 * @date 2016-9-20
 */
public class EcsFossErrorLogJobService implements IEcsFossErrorLogJobService{
	
	/**
	 * 日志
	 */
	private final Logger logger = LogManager.getLogger(getClass());
	
	/**
	 * job执行快递对接FOSS异常日志Dao
	 * 
	 */
	private IEcsFossErrorLogJobDao ecsFossErrorLogJobDao;
	
	/**
	 * FOSS到财务第三方支付
	 */
	private IFossToFinsRemitCommonService fossToFinsRemitCommonService;
	
	@Override
	public void addEcsFossErrorLogJob(String codeType, String codeName, Object obj, String errorMsg) {
		EcsFossErrorLogJobEntity errorLogJobEntity = new EcsFossErrorLogJobEntity();
		errorLogJobEntity.setId(UUIDUtils.getUUID());
		errorLogJobEntity.setCodeType(codeType);
		errorLogJobEntity.setCodeName(codeName);
		errorLogJobEntity.setRequestMsg(obj != null ? JSONObject.toJSONString(obj) : null);
		errorLogJobEntity.setErrorMsg(subStringParams(errorMsg, errorMsg));
		errorLogJobEntity.setActive(FossConstants.YES);
		errorLogJobEntity.setCreateDate(new Date());
		errorLogJobEntity.setModifyDate(errorLogJobEntity.getCreateDate());
		this.ecsFossErrorLogJobDao.insertEcsFossErrorLogJob(errorLogJobEntity);
	}

	@Override
	public void updateEcsFossErrorLogJobByLogId(String logId, String jobErrorMsg) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", logId);
		if (StringUtils.isNotBlank(jobErrorMsg)) {
			jobErrorMsg = subStringParams(jobErrorMsg, jobErrorMsg);
		} else {
			params.put("active", FossConstants.NO);
		}
		params.put("jobErrorMsg", jobErrorMsg);
		params.put("modifyDate", new Date());
		this.ecsFossErrorLogJobDao.updateEcsFossErrorLogJob(params);
	}

	@Override
	public List<EcsFossErrorLogJobEntity> queryEcsFossErrorLogJob(String codeType) {
		return this.ecsFossErrorLogJobDao.findEcsFossErrorLogJob(codeType);
	}
	
	/**
	 * 每隔30分钟执行此方法。推送第三方付款数据到财务自助
	 * @author 326181
	 * 
	 */
	public void doExecuteFossToFinsRemittanceJob() {
		List<EcsFossErrorLogJobEntity> resultList = this.queryEcsFossErrorLogJob("ESB_FOSS2ESB_INCOME_REPORTED");
		if (CollectionUtils.isEmpty(resultList)) {
			return;
		}
		//快递补码修改财务单据请求实体 
		RequestGreenHandWrapEntity wrapEntity = null;
		//请求参数
		String requestMsg = null, waybillNo = null;
		//JOB异常信息
		String jobErrorMsg = null;
		for (EcsFossErrorLogJobEntity errorLogJobEntity : resultList) {
			try {
				jobErrorMsg = null;
				requestMsg = errorLogJobEntity.getRequestMsg();
				wrapEntity = JSONObject.parseObject(requestMsg, RequestGreenHandWrapEntity.class);
				waybillNo = wrapEntity.getWaybillNo();
				//使用JOB推送第三方付款数据到财务自助
				fossToFinsRemitCommonService.pushRemittanceMessToFins(wrapEntity);
				logger.error("运单号："+waybillNo+"调用推送到财务自助JOB成功");
			} catch(SettlementException e){
				jobErrorMsg = e.getErrorCode();
				logger.error("运单号："+waybillNo+"调用推送到财务自助JOB失败："+jobErrorMsg);
			} catch (Exception e) {
				logger.error("运单号："+waybillNo+"调用推送到财务自助JOB失败："+e);
				jobErrorMsg = e.toString();
			} finally {
				try {
					this.updateEcsFossErrorLogJobByLogId(errorLogJobEntity.getId(), jobErrorMsg);
				} catch (Exception e1) {
					logger.error("运单号："+waybillNo+"****修改推送到财务自助JOB失败："+e1.toString());
				}
			}
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

	public void setEcsFossErrorLogJobDao(
			IEcsFossErrorLogJobDao ecsFossErrorLogJobDao) {
		this.ecsFossErrorLogJobDao = ecsFossErrorLogJobDao;
	}

	public void setFossToFinsRemitCommonService(
			IFossToFinsRemitCommonService fossToFinsRemitCommonService) {
		this.fossToFinsRemitCommonService = fossToFinsRemitCommonService;
	}
	
} 