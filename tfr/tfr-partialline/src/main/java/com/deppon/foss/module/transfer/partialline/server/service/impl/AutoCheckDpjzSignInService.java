package com.deppon.foss.module.transfer.partialline.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.module.base.common.api.server.dao.IMsgOnlineDao;
import com.deppon.foss.module.base.common.api.shared.domain.MsgOnlineEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.partialline.api.server.dao.IAutoCheckDpjzSignInDao;
import com.deppon.foss.module.transfer.partialline.api.server.service.IAutoCheckDpjzSignInService;
import com.deppon.foss.module.transfer.partialline.api.server.service.IDpjzSignInMsgService;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.DpjzSignInDetailDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.define.FossConstants;

public class AutoCheckDpjzSignInService implements IAutoCheckDpjzSignInService{
	private static final Logger LOGGER = LoggerFactory.getLogger(AutoCheckDpjzSignInService.class);

	private IAutoCheckDpjzSignInDao autoCheckDpjzSignInDao;
	
	private ITfrCommonService tfrCommonService;
	/**
	 * 获取配置参数
	 */
	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * 在线通知
	 */
	private IMsgOnlineDao msgOnlineDao;
	
	/**
	 * 审核service
	 */
	private IDpjzSignInMsgService dpjzSignInMsgService;

	/**
	 * 自动审核德邦家装送装签收信息
	 */
	@Override
	public void doAutoCheckSignInInfo() {
		LOGGER.info("---调用自动审核doAutoCheckSignInInfo()方法开始---");
		LOGGER.error("---调用自动审核doAutoCheckSignInInfo()方法开始---");
		//设置默认值24小时
		int dopCheckHourRule = ConstantsNumberSonar.SONAR_NUMBER_24;
		//查询需处理的运单信息
		List<DpjzSignInDetailDto> entityList = new ArrayList<DpjzSignInDetailDto>();
	
		//获取配制参数
		ConfigurationParamsEntity defaultBizHourSlice = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , ConfigurationParamsConstants.TFR_AUTO_CHECKDPJZ_SIGNINMSG, FossConstants.ROOT_ORG_CODE);
		dopCheckHourRule = Integer.valueOf(defaultBizHourSlice.getConfValue()).intValue();
	
		Map<String,Object> paramMap=new HashMap<String,Object>();
		//配置参数时间限制
		paramMap.put("dopCheckHourRule", dopCheckHourRule);
		//获取需要审核的数据
		entityList = autoCheckDpjzSignInDao.queryDpjzSignInMsgWaybill(paramMap);
		//若不存在则退出
		if(entityList.size() == 0) {
			return;
		}
		//在线通知使用
		MsgOnlineEntity msgOnlineEntity=new MsgOnlineEntity();
		//0代表界面新增数据
		int status = 0;
		//循环处理各个运单
		for(DpjzSignInDetailDto entity:entityList){
			LOGGER.info("审核处理德邦家装送装信息开始,运单号："+entity.getWaybillNo());
			//更新德邦家装送装信息
			try{
				dpjzSignInMsgService.updateDpjzSignInMsgWaybill(entity);
				LOGGER.info("------------更新德邦家装送装信息成功-----------------");
				LOGGER.error("------------更新德邦家装送装信息成功-----------------");
				
				LOGGER.error("------------对应收货部门自动发在线通知开始-----------------");
				//自动审核以后，发送在线短信通知
				//对应收货部门自动发在线通知，在线通知内容“家装运单：****运单号****，因超过**（根据FOSS配置参数）小时未核对供应商送装明细，系统默认同意供应商送装明细。”
				//运单号
				msgOnlineEntity.setWaybillNo(entity.getWaybillNo());
				StringBuffer msg=new StringBuffer();
				//消息内容==“家装运单：****运单号:****，因超过**（根据FOSS配置参数）小时未核对供应商送装明细，系统默认同意供应商送装明细。”
				msg.append("家装运单：****运单号: ")
					.append(entity.getWaybillNo())
					.append("****，因超过**")
					.append(dopCheckHourRule)
					.append("**（根据FOSS配置参数）小时未核对供应商送装明细，系统默认同意供应商送装明细。");
				//消息内容
				msgOnlineEntity.setContext(msg.toString());
				//收货部门code
				msgOnlineEntity.setReceiveOrgCode(entity.getReceiveOrgCode());
				//收货部门name
				msgOnlineEntity.setReceiveOrgName(entity.getReceiveOrgName());
				//受理部门code
				msgOnlineEntity.setSendOrgCode(entity.getReceiveOrgCode());
				//受理部门name
				msgOnlineEntity.setSendOrgName(entity.getReceiveOrgName());
				//起草人
				msgOnlineEntity.setCreateUser("job自动发在线通知");
				msgOnlineEntity.setAcceptStatus(MessageConstants.MSG__STATUS__PROCESSING);
				
				//0 表示界面新增
				msgOnlineDao.addOnlineMsg(msgOnlineEntity,status);
				LOGGER.error("------------对应收货部门自动发在线通知结束-----------------");
				
			}catch(Exception e){
				String remark = "审核处理德邦家装送装信息,运单号："+entity.getWaybillNo();
				TfrJobProcessLogEntity jobProcessLogEntity = this.getLogEntity(remark);
				jobProcessLogEntity.setExecBizId(entity.getId());
				jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
				tfrCommonService.addJobProcessLog(jobProcessLogEntity);
			}
		}
	}
	
	/**
	 * @author lln
	 * @date 2015-12-12下午3:26:47
	 * @function 获取job日志实体，用于记录错误日志 
	 * @param remark
	 * @return TfrJobProcessLogEntity
	 */
	private TfrJobProcessLogEntity getLogEntity(String remark) {
		LOGGER.error(remark);
		
		TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
		jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.AUTOCHECK_DPJZ_JOB.getBizName());
		jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.AUTOCHECK_DPJZ_JOB.getBizCode());
		jobProcessLogEntity.setExecTableName("TFR.T_OPT_DPJZ_SIGNIN_MSG");
		jobProcessLogEntity.setRemark(remark);
		jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
		
		return jobProcessLogEntity;
	}

	/**
	 * @param autoCheckDpjzSignInDao the autoCheckDpjzSignInDao to set
	 */
	public void setAutoCheckDpjzSignInDao(
			IAutoCheckDpjzSignInDao autoCheckDpjzSignInDao) {
		this.autoCheckDpjzSignInDao = autoCheckDpjzSignInDao;
	}

	/**
	 * @param tfrCommonService the tfrCommonService to set
	 */
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}

	/**
	 * @param configurationParamsService the configurationParamsService to set
	 */
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * @param dpjzSignInMsgService the dpjzSignInMsgService to set
	 */
	public void setDpjzSignInMsgService(IDpjzSignInMsgService dpjzSignInMsgService) {
		this.dpjzSignInMsgService = dpjzSignInMsgService;
	}

	public void setMsgOnlineDao(IMsgOnlineDao msgOnlineDao) {
		this.msgOnlineDao = msgOnlineDao;
	}

}
