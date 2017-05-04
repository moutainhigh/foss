/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-common
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/server/service/impl/TfrCommonService.java
 *  
 *  FILE NAME          :TfrCommonService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.common.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.common.api.server.dao.ISerialNumberRuleDao;
import com.deppon.foss.module.transfer.common.api.server.dao.ITfrJobProcessDao;
import com.deppon.foss.module.transfer.common.api.server.dao.ITfrJobProcessLogDao;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferDictionaryConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.CreateErrorLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.SerialNumberRuleEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.api.shared.vo.TfrCommonVO;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

public class TfrCommonService implements ITfrCommonService {
	
	private static final Logger logger = LogManager.getLogger(TfrCommonService.class);
	/**
	 * serialNumberRuleDao
	 */
	private ISerialNumberRuleDao serialNumberRuleDao;
	private ITfrJobProcessDao tfrJobProcessDao;
	private ITfrJobProcessLogDao tfrJobProcessLogDao;
	
	private IDataDictionaryValueService dataDictionaryValueService;
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	private IOutfieldService outfieldService;
	private IWaybillManagerService waybillManagerService;
	
	/**
	 * 
	 * 线路service
	 */
	private ILineService lineService;
	
	/**
	 * 快递线路service
	 */
	private IExpressLineService expresslineService;

	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}

	public void setExpresslineService(IExpressLineService expresslineService) {
		this.expresslineService = expresslineService;
	}

	public void setSerialNumberRuleDao(ISerialNumberRuleDao serialNumberRuleDao) {
		this.serialNumberRuleDao = serialNumberRuleDao;
	}
	
	public void setDataDictionaryValueService(IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}
	
	public void setTfrJobProcessDao(ITfrJobProcessDao tfrJobProcessDao) {
		this.tfrJobProcessDao = tfrJobProcessDao;
	}

	public void setTfrJobProcessLogDao(ITfrJobProcessLogDao tfrJobProcessLogDao) {
		this.tfrJobProcessLogDao = tfrJobProcessLogDao;
	}
	
	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}
	
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	
	@Override
	public String generateSerialNumber(TfrSerialNumberRuleEnum serialNumberRule, String... params) {
		//通过rule_code尝试获取计数，并锁定计数表记录
		SerialNumberRuleEntity snEntity = new SerialNumberRuleEntity();
		Map<String, Object> result = new HashMap<String, Object>();
		
		snEntity = serialNumberRuleDao.selectSnRuleForUpdate(serialNumberRule.getCode());
		//如果从数据库中未取得计数，则按照code新增一条，若数据重复，外围捕获异常
		if(snEntity == null){
			snEntity = new SerialNumberRuleEntity();
			snEntity.setCode(serialNumberRule.getCode());
			snEntity.setName(serialNumberRule.getName());
			snEntity.setCurrentNum(0L);
			snEntity.setCurrentTime(Calendar.getInstance().getTime());
			//新增一条记录并锁定此记录
			serialNumberRuleDao.addSnRule(snEntity);
			snEntity = serialNumberRuleDao.selectSnRuleForUpdate(serialNumberRule.getCode());
		}
		try{
//			按照编号规则拼接编号
			result = spliceSN(serialNumberRule, snEntity, true, params);
			//按rule_code更新计数表
			snEntity.setCurrentTime(Calendar.getInstance().getTime());
			snEntity.setCurrentNum((Long) result.get("newSeq"));
		}catch(Exception e){
			logger.error("生成编号出现异常", e);
			throw new BusinessException("", e);
		}finally{
			serialNumberRuleDao.updateSnRule(snEntity);
		}
		return (String) result.get("newCode");
	}
	
	@Override
	public String generateSerialNumberNoLock(TfrSerialNumberRuleEnum serialNumberRule, String... params) {
//		如果非使用sequence的，直接抛出异常
		if(!serialNumberRule.isNeedSequence()){
			throw new BusinessException("调用生成编号接口错误，此接口只支持使用sequence的编号生成规则");
		}
		//通过rule_code尝试获取计数
		SerialNumberRuleEntity snEntity = new SerialNumberRuleEntity();
		Map<String, Object> result = new HashMap<String, Object>();
		
		snEntity = serialNumberRuleDao.selectSnRule(serialNumberRule.getCode());
		//如果从数据库中未取得计数，则按照code新增一条，若数据重复，外围捕获异常
		if(snEntity == null){
			snEntity = new SerialNumberRuleEntity();
			snEntity.setCode(serialNumberRule.getCode());
			snEntity.setName(serialNumberRule.getName());
			snEntity.setCurrentNum(0L);
			snEntity.setCurrentTime(Calendar.getInstance().getTime());
			//新增一条记录
			serialNumberRuleDao.addSnRule(snEntity);
			snEntity = serialNumberRuleDao.selectSnRule(serialNumberRule.getCode());
		}
		try{
//			按照编号规则拼接编号
			result = spliceSN(serialNumberRule, snEntity, true, params);
			//按rule_code更新计数表
			snEntity.setCurrentTime(Calendar.getInstance().getTime());
			snEntity.setCurrentNum((Long) result.get("newSeq"));
		}catch(Exception e){
			logger.error("生成编号出现异常", e);
			throw new BusinessException("", e);
		}finally{
			serialNumberRuleDao.updateSnRule(snEntity);
		}
		return (String) result.get("newCode");
	}
	
	@Override
	public String showSerialNumber(TfrSerialNumberRuleEnum serialNumberRule, String... params) {
//		通过rule_code尝试获取计数，并读取计数表记录
		SerialNumberRuleEntity snEntity = new SerialNumberRuleEntity();
		snEntity = serialNumberRuleDao.selectSnRule(serialNumberRule.getCode());
//		如果从数据库中未取得计数，则按照code新增一条，若数据重复，外围捕获异常
		if(snEntity == null){
			snEntity = new SerialNumberRuleEntity();
			snEntity.setCode(serialNumberRule.getCode());
			snEntity.setName(serialNumberRule.getName());
			snEntity.setCurrentNum(0L);
			snEntity.setCurrentTime(Calendar.getInstance().getTime());
			serialNumberRuleDao.addSnRule(snEntity);
//			新增一条记录并读取此记录
			snEntity = serialNumberRuleDao.selectSnRule(serialNumberRule.getCode());
		}
//		按照编号规则拼接编号
		Map<String, Object> result = spliceSN(serialNumberRule, snEntity, false, params);

		return (String) result.get("newCode");
	}
	
	
	/**
	 * 按照业务规则拼接新的编号
	 * @author foss-wuyingjie
	 * @param snEntity 
	 * @param formal 是否是正式生成编号
	 * @param params 
	 * @date 2012-11-5 下午5:34:29
	 */
	private Map<String, Object> spliceSN(TfrSerialNumberRuleEnum serialNumberRule, SerialNumberRuleEntity snEntity, boolean formal, String... params) {
		Map<String, Object> mapResult = new HashMap<String, Object>();
		String newCode = "";
		long newSeq = 0;
		boolean resetNo = false;
//		通过返回的最新计数，以及计数规则，创建新的编号
//		如果需要参数，按code及参数拼接
		if(serialNumberRule.isNeedParams()){
//			清仓差异报告 ：清仓任务 D + 清仓任务编号（params1）
			if(StringUtils.equals(TfrSerialNumberRuleEnum.CY.getCode(), serialNumberRule.getCode())){
				newCode += params[0];
//			清仓任务编号、装车任务编号、卸车任务编号、部门编号(params0，用此参数判断当前部门是否为外场[ISSUE-868]) + yyyymmdd
			}else if(StringUtils.equals(TfrSerialNumberRuleEnum.QC.getCode(), serialNumberRule.getCode())
				  || StringUtils.equals(TfrSerialNumberRuleEnum.ZC.getCode(), serialNumberRule.getCode())
				  || StringUtils.equals(TfrSerialNumberRuleEnum.XC.getCode(), serialNumberRule.getCode())){
				List<String> bizTypes = new ArrayList<String>();
				bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
				OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(params[0], bizTypes);
				String prefix = "";
				if(StringUtils.equals(TfrSerialNumberRuleEnum.PSZC.getCode(), serialNumberRule.getCode())){
					prefix = serialNumberRule.getLetterPrefix();
				}else{
//					如果此部门为外场，则取前缀的前部分作为字母前缀，否则取后部分
					String[] prefixArray = serialNumberRule.getLetterPrefix().split(TransferConstants.DEFAULT_SPLIT_STRING);
					if(null == org){
						prefix = prefixArray[1];
					}else{
						prefix = prefixArray[0];
					}
				}
				
			    newCode += prefix;
//			卸车差异报告编号、派送装车差异报告编号  (params[0] + 任务编号)
			}else if(StringUtils.equals(TfrSerialNumberRuleEnum.PSZCCY.getCode(), serialNumberRule.getCode())
				  || StringUtils.equals(TfrSerialNumberRuleEnum.XCCY.getCode(), serialNumberRule.getCode())){
				newCode += params[0];
//			配载车次号 始发外场简称(params[0])+到达外场简称(params[1])+出发日期yymmdd(params[2])+是否为整车(params[3])) 特殊处理
			}else if(StringUtils.equals(TfrSerialNumberRuleEnum.PZCC.getCode(), serialNumberRule.getCode())){
				//获取到达组织code的list
				List<String> destOrgCodeList = queryDestOrgCodeListForVehicleAssembleNo(params[1]);
				//按照传入参数，查询对应配载车次记录的最后单号
				String oldSn = serialNumberRuleDao.queryVehicleAssembleBillNo(params[0], destOrgCodeList, params[2]);
				//若未返回记录，则单号为01
				if(StringUtils.isBlank(oldSn)){
					newSeq = 1;
				}else{
				//若返回了1条记录，则在已有单号基础上+1
					String interceptedNo = StringUtils.right(oldSn, 2);
					try{
						newSeq = Long.valueOf(interceptedNo).longValue() + 1;
					}catch(NumberFormatException e){
						throw new TfrBusinessException(TfrBusinessException.ILLEGAL_ARGUMENT_EXCEPTION, e.getMessage(), e);
					}
				}
				
				String newSn = String.format("%0"+ serialNumberRule.getNumLen() +"d", newSeq);
				
				
				
				OutfieldEntity startOrg = outfieldService.queryOutfieldByOrgCode(params[0]);
				OutfieldEntity endOrg = outfieldService.queryOutfieldByOrgCode(params[1]);
				if(null == startOrg || null == endOrg){
					throw new TfrBusinessException(TfrBusinessException.SN_PZCC_ERROR_ORG, TfrBusinessException.SN_PZCC_ERROR_ORG);
				}
//				如果是整车类型，则需要加入整车参数前缀
				if(TransferDictionaryConstants.YES.equals(params[TransferConstants.SONAR_NUMBER_3])){
					newCode = startOrg.getSimpleCode() + endOrg.getSimpleCode() + StringUtils.right(params[2], TransferConstants.SONAR_NUMBER_6) + TransferDictionaryConstants.TOTAL_TRUCK_PREFIX + newSn;
				}else{
					newCode = startOrg.getSimpleCode() + endOrg.getSimpleCode() + StringUtils.right(params[2], TransferConstants.SONAR_NUMBER_6) + newSn;
				}
			}else if (StringUtils.equals(TfrSerialNumberRuleEnum.ZCBJ.getCode(), serialNumberRule.getCode())){
				newCode+=params[0];
			}
		}
		
//		编号中包含当前日期格式类型的处理  可能需要重置对应的sequence
		if(serialNumberRule.isNeedTime()){
//			如果对于上一个已生成的编号已跨天，需重置对应的sequence
			String currentDate = DateUtils.convert(snEntity.getCurrentTime(), DateUtils.DATE_FORMAT);
			String nowDate = DateUtils.convert(Calendar.getInstance().getTime(), DateUtils.DATE_FORMAT);
			Long differDayNum = DateUtils.getTimeDiff(currentDate, nowDate);
			if(differDayNum > 0){
				resetNo = true;
			}
		}
		
//		字母前缀
		if(serialNumberRule.isNeedLetterPrefix()){
			newCode = serialNumberRule.getLetterPrefix() + newCode;
		}
//		时间前缀
		if(serialNumberRule.isNeedTime()){
			newCode += DateUtils.convert(Calendar.getInstance().getTime(), serialNumberRule.getTimeFormat()); 
		}
//		分隔符
		if(serialNumberRule.isNeedDelimiter()){
			newCode += serialNumberRule.getDelimiter();
		}
//		数字项
		if(serialNumberRule.isNeedNumber()){
//			如果为正式生成编号的情况，并且需要sequence的直接取nextval
			if(formal && serialNumberRule.isNeedSequence()){
				newSeq = serialNumberRuleDao.getNextSequenceValue(serialNumberRule.getSequence()); 
			}else{
//			非正式生成编号的情况，使用数据库当前值累加1
//				针对日期+数字情况，如果跨天，则重置为1
				if(resetNo){
					newSeq = 1;
//				针对无需重置的情况，使当前值累加1
				}else{
					newSeq = snEntity.getCurrentNum() + 1;
				}
			}
//			数字项是否固定位数
			if(serialNumberRule.isFixedNumLen()){
				newCode += String.format("%0"+ serialNumberRule.getNumLen() +"d", newSeq);
			}else{
				newCode += String.valueOf(newSeq);
			}
		}
		//后缀
		if(serialNumberRule.isNeedLetterSuffix()){
			newCode += serialNumberRule.getLetterSuffix();
		}
		
		mapResult.put("newCode", newCode);
		mapResult.put("newSeq", Long.valueOf(newSeq));
		
		return mapResult;
	}

	@Transactional(readOnly = true)
	@Override
	public List<BaseDataDictDto> loadDictDataCombox(String termsCode) {
		List<BaseDataDictDto> list = new ArrayList<BaseDataDictDto>();
		
		BaseDataDictDto baseDataDictDto = new BaseDataDictDto();
		baseDataDictDto.setValueName(TransferDictionaryConstants.DEFAULT_COMBO_TEXT);
		baseDataDictDto.setValueCode(TransferDictionaryConstants.DEFAULT_COMBO_VALUE);
		list.add(baseDataDictDto);

		// 调用综合接口获取指定 termsCode对应的数据字典列表
		List<DataDictionaryValueEntity> dataDictList = dataDictionaryValueService.queryDataDictionaryValueByTermsCode(termsCode);
		for(DataDictionaryValueEntity entity: dataDictList){
			BaseDataDictDto dict = new BaseDataDictDto();
			dict.setValueName(entity.getValueName());
			dict.setValueCode(entity.getValueCode());
			
			list.add(dict);
		}
		
		return list;
	}

	@Override
	public TfrJobProcessEntity queryLastExecutedJobInfo(TfrJobBusinessTypeEnum typeEnum, Date scheduledFireTime, int threadNo, int threadCount) {
//		获取某一个业务对应的job执行情况信息
		TfrJobProcessEntity jobProcess = tfrJobProcessDao.queryJobProcessByIndex(typeEnum.getBizCode(), threadNo, threadCount);
//		若未获取到，则初始化一条相对应的job执行记录
		if(StringUtils.isBlank(jobProcess.getId())){
			jobProcess.setBizName(typeEnum.getBizName());
			jobProcess.setBizCode(typeEnum.getBizCode());
			Date initBizStartTime = org.apache.commons.lang.time.DateUtils.addMinutes(scheduledFireTime, typeEnum.getInitMins() + typeEnum.getInitMins());
			Date initBizEndTime = org.apache.commons.lang.time.DateUtils.addMinutes(scheduledFireTime, typeEnum.getInitMins());
			jobProcess.setBizStartTime(initBizStartTime);
			jobProcess.setBizEndTime(initBizEndTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
			jobProcess.setThreadNo(threadNo);
			jobProcess.setThreadCount(threadCount);
			jobProcess.setJobStartTime(initBizStartTime);
			jobProcess.setJobEndTime(initBizEndTime);
			
			tfrJobProcessDao.addJobProcessEntity(jobProcess);
			
			jobProcess = tfrJobProcessDao.queryJobProcessByIndex(typeEnum.getBizCode(), threadNo, threadCount);
		}
		
		return jobProcess;
	}
	
	@Override
	public TfrJobProcessEntity queryJobInfo(TfrJobBusinessTypeEnum typeEnum, Date scheduledFireTime, int threadNo, int threadCount){
//		获取某一个业务对应的job执行情况信息
		TfrJobProcessEntity jobProcess = tfrJobProcessDao.queryJobProcessByIndexNoLock(typeEnum.getBizCode(), threadNo, threadCount);
//		若未获取到，则初始化一条相对应的job执行记录
		if(StringUtils.isBlank(jobProcess.getId())){
			jobProcess.setBizName(typeEnum.getBizName());
			jobProcess.setBizCode(typeEnum.getBizCode());
			Date initBizStartTime = org.apache.commons.lang.time.DateUtils.addMinutes(scheduledFireTime, typeEnum.getInitMins() + typeEnum.getInitMins());
			Date initBizEndTime = org.apache.commons.lang.time.DateUtils.addMinutes(scheduledFireTime, typeEnum.getInitMins());
			jobProcess.setBizStartTime(initBizStartTime);
			jobProcess.setBizEndTime(initBizEndTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
			jobProcess.setThreadNo(threadNo);
			jobProcess.setThreadCount(threadCount);
			jobProcess.setJobStartTime(initBizStartTime);
			jobProcess.setJobEndTime(initBizEndTime);
			
			tfrJobProcessDao.addJobProcessEntity(jobProcess);
			
			jobProcess = tfrJobProcessDao.queryJobProcessByIndexNoLock(typeEnum.getBizCode(), threadNo, threadCount);
		}
		
		return jobProcess;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void updateExecutedJob(TfrJobProcessEntity jobProcess) {
		tfrJobProcessDao.updateByPrimaryKeySelective(jobProcess);
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@Override
	public void addJobProcessLog(TfrJobProcessLogEntity jobProcessLogEntity) {
		try{
			jobProcessLogEntity.setExceptionInfo(StringUtils.left(jobProcessLogEntity.getExceptionInfo(), TransferConstants.SONAR_NUMBER_4000));
			tfrJobProcessLogDao.addJobProcessLog(jobProcessLogEntity);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
	}
	/**
	 * 通过传入的数据字典编码，获取下拉框信息，没有默认项
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-3 下午8:23:18
	 * @see com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService#loadDictDataComboxNoDefault(java.lang.String)
	 */
	@Transactional(readOnly = true)
	@Override
	public List<BaseDataDictDto> loadDictDataComboxNoDefault(String termsCode) {
		List<BaseDataDictDto> list = new ArrayList<BaseDataDictDto>();
		// 调用综合接口获取指定 termsCode对应的数据字典列表
		List<DataDictionaryValueEntity> dataDictList = dataDictionaryValueService.queryDataDictionaryValueByTermsCode(termsCode);
		for(DataDictionaryValueEntity entity: dataDictList){
			BaseDataDictDto dict = new BaseDataDictDto();
			dict.setValueName(entity.getValueName());
			dict.setValueCode(entity.getValueCode());
			list.add(dict);
		}
		return list;
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2013-1-14 下午6:27:08
	 * @see com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService#validateWaybillNoExist(java.lang.String, java.lang.String)
	 */
	@Override
	public TfrCommonVO validateWaybillNoExist(String waybillNo, String serialNo) {
		TfrCommonVO vo = new TfrCommonVO();
		String resultMessage = "";
		
		vo.setResultMessage(resultMessage);
		vo.setResultSuccessful(true);
		
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		
		if(waybillEntity != null){
			//非迁移数据
			if(StringUtils.equals(waybillEntity.getIsInit(), "N")){
				//运单号存在的基础上，继续查看是否流水号是否存在
				String[] serialNoArray = serialNo.split(",");
				if(null != serialNoArray && serialNoArray.length > 0){
					for(String sn: serialNoArray){
						boolean serialNoExists = waybillManagerService.isSerialNoExsits(waybillNo, sn);
						if(!serialNoExists){
							resultMessage += "流水号：" + sn + " 不存在！";
							vo.setResultMessage(resultMessage);
							vo.setResultSuccessful(false);
							break;
						}
					}
				}
			}else{
				//迁移数据不做流水号校验
			}
		}else{
			resultMessage += "运单号：" + waybillNo + " 不存在！";
			vo.setResultMessage(resultMessage);
			vo.setResultSuccessful(false);
		}

		return vo;
	}

	@Override
	public String queryDictNameByCode(String code, String value) {
		DataDictionaryValueEntity entity = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(code, value);
		
		if(null == entity){
			return "";
		}else{
			return entity.getValueName();
		}
	}
	
	/**
	 * 私有方法，获取到达外场下所有的营业部code，如果非整车，则直接返回到达外场code
	 * @author 045923-foss-shiwei
	 * @date 2013-5-17 上午10:20:59
	 */
	private List<String> queryDestOrgCodeListForVehicleAssembleNo(String destOutfieldCode){
		List<String> list = new ArrayList<String>();
		list.add(destOutfieldCode);
		LineEntity con = new LineEntity();
		con.setActive(FossConstants.ACTIVE);
		//修改配載單車次號重複問題
		con.setIsDefault(FossConstants.YES);
		con.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_TARGET);
		con.setOrginalOrganizationCode(destOutfieldCode);
		List<LineEntity> lineList = lineService.querySimpleLineListByCondition(con);
		/**
		 * 2014-09-10  200978
		 * 分离快递和零担的线路服务接口
		 */
		if(!CollectionUtils.isEmpty(lineList)){
			for (LineEntity entity : lineList) {
				list.add(entity.getDestinationOrganizationCode());
			}
		}else{
			ExpressLineEntity con2 = new ExpressLineEntity();
			con2.setActive(FossConstants.ACTIVE);
			//修改配載單車次號重複問題
			con2.setIsDefault(FossConstants.YES);
			con2.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_TARGET);
			con2.setOrginalOrganizationCode(destOutfieldCode);
			List<ExpressLineEntity> expressLineList = expresslineService.querySimpleLineListByCondition(con2);
			for (ExpressLineEntity entity : expressLineList) {
				list.add(entity.getDestinationOrganizationCode());
			}
		}
		
		return list;
	}

	/**
	 * 新事务生成编码
	 * @author alfred
	 * @date 2013-9-4 上午8:27:54
	 * @param serialNumberRule
	 * @param params
	 * @return 
	 * @see com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService#generateSerialNumberRequireNew(com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum, java.lang.String[])
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public String generateSerialNumberRequireNew(TfrSerialNumberRuleEnum serialNumberRule, String... params) {
		return this.generateSerialNumber(serialNumberRule, params);
	}

	/**
	 * 插入错误记录
	 * @author alfred
	 * @date 2016-10-19 16:26:34
	 * @param entity
	 * @return
	 * @see
	 */
	@Override
	public void createErrorLog(CreateErrorLogEntity entity) {
		try {
			serialNumberRuleDao.createErrorLog(entity);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		
	}
	
}