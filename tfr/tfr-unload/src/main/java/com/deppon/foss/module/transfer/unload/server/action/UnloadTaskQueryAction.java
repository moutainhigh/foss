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
 *  PROJECT NAME  : tfr-unload
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/server/action/UnloadTaskQueryAction.java
 *  
 *  FILE NAME          :UnloadTaskQueryAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskQueryService;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadSerialNoDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressQueryUnloadWaybillDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadWaybillDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.vo.UnloadTaskVo;
import com.deppon.foss.util.DateUtils;


/**
 * 查询卸车任务Action
 * @author ibm-zhangyixin
 * @date 2012-12-6 下午3:05:30
 */
public class UnloadTaskQueryAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8876041407631454177L;
	
	/****
	 * 卸车任务vo
	 */
	private UnloadTaskVo unloadTaskVo = new UnloadTaskVo();
	/**
	 * 查询卸车任务sealvice
	 */
	private IUnloadTaskQueryService unloadTaskQueryService;
	
    private IFOSSToWkService fossToWkService;
    
    
    private IConfigurationParamsService configurationParamsService;
    
private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	
	/** 导出Excel 文件流*/
	private InputStream excelStream;
	/** 导出Excel 文件名*/
	private String fileName;

	/**
	 * 查询卸车任务
	 * @author ibm-zhangyixin
	 * @date 2012-12-6 下午3:06:36
	 */
	@JSON
	public String queryUnloadTask() {
		try {
			//查询卸车任务(分页)
			List<UnloadTaskDto> unloadTaskDtoList = unloadTaskQueryService.queryUnloadTask(unloadTaskVo.getUnloadTaskDto(), this.getLimit(), this.getStart());
			
			UnloadTaskDto unloadTaskDto =unloadTaskVo.getUnloadTaskDto();
			//2015/9/10 272681 查询商务专递卸车任务时获得卸车路线
			for(UnloadTaskDto list:unloadTaskDtoList){
				boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
				LOG.error("查询卸车任务是否有快递差异开关" + tfrSwitch4Ecs);
				if (tfrSwitch4Ecs) {
					if (UnloadConstants.ASSIGN_UNLOAD_TASK_STATE_FINISHED.equals(list.getTaskState())
							&& StringUtils.isEmpty(list.getGaprepNo())) {
						List<String> paramList = new ArrayList<String>();
						paramList.add(FossUserContext.getCurrentDeptCode());
						paramList.add(unloadTaskDto.getVehicleNo());
						paramList.add(unloadTaskDto.getUnloadTaskNo());
						paramList.add(unloadTaskDto.getLoaderName());
						paramList.add(null);
						paramList.add(list.getUnloadTaskNo() + "_c");
						paramList.add(null);
						@SuppressWarnings("unchecked")
						List<UnloadDiffReportEntity> resList = (List<UnloadDiffReportEntity>) fossToWkService
								.getExpressLessCargoList(paramList.toArray(new String[paramList.size()]),
										DateUtils.convert(unloadTaskDto.getBeginDate(), DateUtils.DATE_TIME_FORMAT),
										DateUtils.convert(unloadTaskDto.getEndDate(), DateUtils.DATE_TIME_FORMAT));
						if (!CollectionUtils.isEmpty(resList)) {
							list.setGaprepNo(list.getUnloadTaskNo() + "_c");
						}
					}
				}
				
				if(list.getUnloadType().equals(UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS)){
				String unloadTaskNo = list.getUnloadTaskNo();
				String line = unloadTaskQueryService.queryBusinessLine(unloadTaskNo);
				list.setLine(line);
				}
			}
			
			//查询结果的总记录数
			Long totCount = unloadTaskQueryService.getTotCount(unloadTaskVo.getUnloadTaskDto());
			this.setTotalCount(totCount);
			unloadTaskVo.setUnloadTaskDtoList(unloadTaskDtoList);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	    return returnSuccess();
	}

	
	/**
	 * 查询卸车任务明细
	 * 
	 * unloadWaybillDetailDtoList
	 * 根据卸车任务编号获取卸车明细</br>
	 * 	交接件数:</br>
			如果单据类型为交接单, 取该单据下 交接单明细表中sum(已配件数)</br>
			如果为配载单, 根据配载单找到该配载单下所有交接单, 再根据交接单找交接明细下sum(已配件数)</br>
			如果为接货任务, 转货交接单, 派送拉回货 则取交接表(pkp.t_srv_stay_handover) 下所有的件数</br>
		件数扫描率:</br>
			已扫描件数/交接件数</br>
		交接重量</br>
			如果单据类型为交接单, 取该单据下交接单明细表中sum(已配重量)</br>
			如果为配载单, 根据该配载单好找到该配置单下所有交接单, 再根据交接单找交接单明细下sum(已配重量)</br>
			如果为派送拉回货, 交接单开单重量*交接件数(交接件数获取方式同上)/开单件数</br>
			如果为转货交接单, 取该单据下交接单明细表中sum(已配重量)</br>
			如果为接回货, 取交接单中开单重量</br>
		交接体积</br>
			如果单据类型为交接单, 取该单据下交接单明细表中sum(已配体积)</br>
			如果为配载单, 根据该配载单好找到该配置单下所有交接单, 再根据交接单找交接单明细下sum(已配体积)</br>
			如果为派送拉回货, 交接单开单体积*交接件数(交接件数获取方式同上)/开单件数</br>
			如果为转货交接单, 取该单据下交接单明细表中sum(已配体积)</br>
			如果为接回货, 取交接单中开单体积</br>
	 * @author ibm-zhangyixin
	 * @date 2012-12-6 下午3:06:36
	 */
	@JSON
	public String queryUnloadTaskDetail() {
		try {
			//根据卸车任务编号获取单条卸车任务
			UnloadTaskDto unloadTaskDto = unloadTaskQueryService.getUnloadTaskByUnloadTaskNo(unloadTaskVo.getUnloadTaskDto().getUnloadTaskNo());
			//2015/9/11 272681 商务专递卸车查看卸车明细时获得路线
			if(unloadTaskDto.getUnloadType().equals(UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS)){
				String unloadTaskNo = unloadTaskDto.getUnloadTaskNo();
				String line = unloadTaskQueryService.queryBusinessLine(unloadTaskNo);
				unloadTaskDto.setLine(line);
			}
			List<UnloadWaybillDetailDto> unloadWaybillDetailDtoList = unloadTaskQueryService.queryUnloadTaskDetailByUnloadTaskNo(unloadTaskVo.getUnloadTaskDto().getUnloadTaskNo());
			boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
			LOG.error("根据卸车任务编号获取快递单条卸车任务开关" + tfrSwitch4Ecs);
			if(tfrSwitch4Ecs){
				
				String state=unloadTaskVo.getUnloadTaskDto().getState();
				if (StringUtils.equals(state, UnloadConstants.ASSIGN_UNLOAD_TASK_STATE_FINISHED)
						|| (UnloadConstants.UNLOAD_TASK_WAY_PDA.equals(unloadTaskDto.getUnloadWay())
								&& StringUtils.equals(state, UnloadConstants.UNLOAD_TASK_STATE_UNLOADING))){
					//根据卸车任务编号查询快递卸车明细将返回结果与零担明细合并
					List<UnloadWaybillDetailDto> unloadWaybillDetailDtoExpressList=queryExpressUnloadTaskDetailByUnloadTaskNo(unloadTaskVo.getUnloadTaskDto().getUnloadTaskNo());
					if(unloadWaybillDetailDtoExpressList!=null){
						for(UnloadWaybillDetailDto UnloadWaybillDetailDto:unloadWaybillDetailDtoExpressList){
							if(UnloadWaybillDetailDto.isHasDifferences()
									&&StringUtils.isEmpty(unloadTaskDto.getGaprepNo())){
								unloadTaskDto.setGaprepNo(unloadTaskDto.getUnloadTaskNo()+"_c");
							}
						}
						unloadWaybillDetailDtoList.addAll(unloadWaybillDetailDtoExpressList);
				   }
				}
			}
			
			unloadTaskVo.setUnloadTaskDto(unloadTaskDto);
			unloadTaskVo.setUnloadWaybillDetailDtoList(unloadWaybillDetailDtoList);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 
	* @description 根据卸车任务编号获取快递卸车明细
	* @param args
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年4月22日 下午4:37:30
	 */
	private List<UnloadWaybillDetailDto> queryExpressUnloadTaskDetailByUnloadTaskNo(String unloadTaskNo){
		
		List<UnloadWaybillDetailDto> expressDetailList=null;
		try {
			//根据卸车任务编号从WK系统获取快递卸车明细
			expressDetailList = unloadTaskQueryService.queryUnloadExpressTaskDetailByUnloadTaskNo(unloadTaskNo);
		}catch(TfrBusinessException e){
			LOG.error(e.getMessage(),"");
		}catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(),"");
		}
	   return expressDetailList;
	}
	
	/**
	 * 
	 * @Title: exportLoadWayBillByTaskNo 
	 * @Description: 根据任务号导出卸车明细 
	 * @return    
	 * @return String    返回类型 
	 * exportLoadWayBillByTaskNo
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-24下午4:11:59
	 */
	public String exportUnloadWayBillByTaskNo() {
		try {
			String taskNo = unloadTaskVo.getUnloadTaskDto().getUnloadTaskNo();
			
//			fileName = unloadTaskQueryService.encodeFileName("卸车明细");
			fileName = encodeFileName("卸车明细");
			excelStream = unloadTaskQueryService.exportLoadWayBillByTaskNo(taskNo);
		} catch (TfrBusinessException e) {
			return returnError(e);
		} catch (UnsupportedEncodingException e) {
			return returnError(new TfrBusinessException("转换字符串编码时出错!"));
		}
		return returnSuccess();
	}
	
	public String encodeFileName(String fileName)
			throws UnsupportedEncodingException {
		String returnStr;
		String agent = (String) ServletActionContext.getRequest().getHeader(
				"USER-AGENT");
		if (agent != null && agent.indexOf("MSIE") == -1) {
			returnStr = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
		} else {
			returnStr = URLEncoder.encode(fileName, "UTF-8");
		}
		return returnStr;
	}

	/**
	 * 
	 * @Title: exportUnloadWayBillByTaskNo 
	 * @Description: 根据查询条件导出卸车任务 
	 * @return    
	 * @return String    返回类型 
	 * exportUnloadWayBillByTaskNo
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-29上午11:02:46
	 */
	public String exportUnloadTask() {
		try {
//			fileName = unloadTaskQueryService.encodeFileName("卸车任务");
			fileName = encodeFileName("卸车任务");
			excelStream = unloadTaskQueryService.exportUnloadTask(unloadTaskVo.getUnloadTaskDto());
		} catch (TfrBusinessException e) {
			return returnError(e);
		} catch (UnsupportedEncodingException e) {
			return returnError(new TfrBusinessException("转换字符串编码时出错!"));
		}
		return returnSuccess();
	}
	
	/**
	 * 根据unloadWaybillDetailId得到所有流水号
	 * @author ibm-zhangyixin
	 * @date 2012-12-17 下午6:43:44
	 */
	@JSON
	public String queryUnloadSerialNoByUnloadWaybillDetailId() {
		try {
			//express快递标识
 			if(StringUtils.equals(UnloadConstants.EXPRESS, unloadTaskVo.getExpressOrLingdan())){
				List<UnloadSerialNoDetailEntity> expressUnloadSerialNoDetailList=queryUnloadSerialNoByExpressUnloadWaybillDetailId(unloadTaskVo);
				unloadTaskVo.setUnloadSerialNoDetailList(expressUnloadSerialNoDetailList);
			}else{
				//根据unloadWaybillDetailId得到所有流水号
				List<UnloadSerialNoDetailEntity> unloadSerialNoDetailList = unloadTaskQueryService
						.queryUnloadSerialNoByUnloadWaybillDetailId(unloadTaskVo.getUnloadWaybillDetailId());
				unloadTaskVo.setUnloadSerialNoDetailList(unloadSerialNoDetailList);
			} 
			
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
   /**
    * @description 根据快递卸车运单ID得到所有流水号
    * @param unloadTaskVo
    * @return
    * @version 1.0
    * @author 328768-foss-gaojianfu
    * @update 2016年4月27日 上午8:00:56
    */
	private List<UnloadSerialNoDetailEntity> queryUnloadSerialNoByExpressUnloadWaybillDetailId(UnloadTaskVo unloadTaskVo){
		
		ExpressQueryUnloadWaybillDetailDto expressQueryUnloadWaybillDetailDto=new ExpressQueryUnloadWaybillDetailDto();
		//设置 卸车任务编号
		expressQueryUnloadWaybillDetailDto.setUnloadTaskNo(unloadTaskVo.getUnloadTaskNo());
		//设置 件号
		expressQueryUnloadWaybillDetailDto.setCargoNo(unloadTaskVo.getCargoNo());
		//设置 件类型
		expressQueryUnloadWaybillDetailDto.setCargoType(unloadTaskVo.getCargoType());
		List<UnloadSerialNoDetailEntity> expressUnloadSerialNoDetailList=null;
		try {
			//根据快递unloadWaybillDetailId得到所有流水号
			expressUnloadSerialNoDetailList = unloadTaskQueryService
						.queryUnloadSerialNoByExpressUnloadWaybillDetailId(expressQueryUnloadWaybillDetailDto);
			if(null==expressUnloadSerialNoDetailList){
				 throw new TfrBusinessException("根据快递卸车运单编号获取流水号失败 ！");  
			 }
		    }catch (Exception e) {
				throw new TfrBusinessException(e.getMessage());
			}
		   return expressUnloadSerialNoDetailList;
	}
	

	/**
	 * 根据taskID得到所有理货员
	 * 如果是手工卸车，卸车创建人不需要显示。
	 * 如果是PDA卸车，需要显示。
	 * T_OPT_LOADER_PARTICIPATION这个表中有be_creator字段，Y为创建者，N为参与者
	 * @author ibm-zhangyixin
	 * @date 2012-12-17 下午6:43:44
	 */
	@JSON
	public String queryLoaderByTaskId() {
		try {
			//根据taskID得到所有理货员 
			//如果是手工卸车，卸车创建人不需要显示。 
			//如果是PDA卸车，需要显示。
			//T_OPT_LOADER_PARTICIPATION这个表中有be_creator字段，Y为创建者，N为参与者
			List<LoaderParticipationEntity> loaderParticipationList = unloadTaskQueryService
					.queryLoaderByTaskId(unloadTaskVo.getUnloadTaskId(), unloadTaskVo.getUnloadWay());
			unloadTaskVo.setLoaderParticipationList(loaderParticipationList);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 设置 查询卸车任务sealvice.
	 *
	 * @param unloadTaskQueryService the new 查询卸车任务sealvice
	 */
	public void setUnloadTaskQueryService(
			IUnloadTaskQueryService unloadTaskQueryService) {
		this.unloadTaskQueryService = unloadTaskQueryService;
	}
	
	public void setFossToWkService(IFOSSToWkService fossToWkService) {
		this.fossToWkService = fossToWkService;
	}
	
	/**
	 * 根据登陆部门code查询所属外场code
	 * @author foss-wangxin
	 * @date 2016-09-03 下午6:44:16
	 */
	@JSON
	public String queryOutfieldCode(){
		//当前登录部门code
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		List<String> bizTypesList=new ArrayList<String>();
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);

		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoIncludeSelfByCode(currentDeptCode, bizTypesList);
		if(orgEntity==null){
			return returnSuccess();
		}
		unloadTaskVo.setOutfieldCode(orgEntity.getCode());
		return returnSuccess();
	}

	/**
	 * 获取 ** 卸车任务vo.
	 *
	 * @return the ** 卸车任务vo
	 */
	public UnloadTaskVo getUnloadTaskVo() {
		return unloadTaskVo;
	}

	/**
	 * 设置 ** 卸车任务vo.
	 *
	 * @param unloadTaskVo the new ** 卸车任务vo
	 */
	public void setUnloadTaskVo(UnloadTaskVo unloadTaskVo) {
		this.unloadTaskVo = unloadTaskVo;
	}

	/**   
	 * excelStream   
	 *   
	 * @return  the excelStream   
	 */
	
	public InputStream getExcelStream() {
		return excelStream;
	}

	/**   
	 * @param excelStream the excelStream to set
	 * Date:2013-4-24下午4:11:13
	 */
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	/**   
	 * fileName   
	 *   
	 * @return  the fileName   
	 */
	
	public String getFileName() {
		return fileName;
	}

	/**   
	 * @param fileName the fileName to set
	 * Date:2013-4-24下午4:11:13
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	
	
	
}