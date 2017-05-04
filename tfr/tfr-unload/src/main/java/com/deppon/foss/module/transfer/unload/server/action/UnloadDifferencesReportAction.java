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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/server/action/UnloadDifferencesReportAction.java
 *  
 *  FILE NAME          :UnloadDifferencesReportAction.java
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
package com.deppon.foss.module.transfer.unload.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.jfree.util.Log;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToWkService;
import com.deppon.foss.module.transfer.common.api.shared.domain.FossToWKResponseAndPageEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadDiffReportService;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryUnloadDiffReportConditionDto;
import com.deppon.foss.module.transfer.unload.api.shared.vo.UnloadDiffReportVo;

/** 
 * @className: UnloadDifferencesReportAction
 * @author: ShiWei shiwei@outlook.com
 * @description: 卸车差异报告action
 * @date: 2012-12-7 上午10:56:17
 * 
 */
public class UnloadDifferencesReportAction extends AbstractAction {

	private static final long serialVersionUID = -4399008474292976094L;

	/** 导出Excel 文件流*/
	private InputStream excelStream;
	/** 导出Excel 文件名*/
	private String fileName;
	
	/**
	 * 本模块service
	 */
	private IUnloadDiffReportService unloadDiffReportService;
	
	/**
	 * 本模块Vo
	 */
	private UnloadDiffReportVo unloadDiffReportVo = new UnloadDiffReportVo();
	
	/**卸车service**/
	private IUnloadTaskService unloadTaskService;
	
	private IFOSSToWkService fossToWkService;
	
	public void setFossToWkService(IFOSSToWkService fossToWkService) {
		this.fossToWkService = fossToWkService;
	}

	/**
	 * getter for unloadDiffReportVo
	 * @author 045923-foss-shiwei
	 * @date 2013-3-16 下午3:03:27
	 */
	public UnloadDiffReportVo getUnloadDiffReportVo() {
		return unloadDiffReportVo;
	}

	/**
	 * setter for unloadDiffReportVo
	 * @author 045923-foss-shiwei
	 * @date 2013-3-16 下午3:03:45
	 */
	public void setUnloadDiffReportVo(UnloadDiffReportVo unloadDiffReportVo) {
		this.unloadDiffReportVo = unloadDiffReportVo;
	}
	
	/**   
	 * @param unloadTaskService the unloadTaskService to set
	 * Date:2013-8-2下午3:30:14
	 */
	public void setUnloadTaskService(IUnloadTaskService unloadTaskService) {
		this.unloadTaskService = unloadTaskService;
	}

	/**
	 * setter for unloadDiffReportService
	 * @author 045923-foss-shiwei
	 * @date 2013-3-16 下午3:03:45
	 */
	public void setUnloadDiffReportService(
			IUnloadDiffReportService unloadDiffReportService) {
		this.unloadDiffReportService = unloadDiffReportService;
	}
	
	private IConfigurationParamsService configurationParamsService;
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * 查询卸车差异报告
	 * @author 045923-foss-shiwei
	 * @date 2012-12-10 上午11:06:25
	 */
	@SuppressWarnings("unchecked")
	public String queryUnloadDiffReport(){
		//获取查询条件
		QueryUnloadDiffReportConditionDto queryCondition = unloadDiffReportVo.getQueryUnloadDiffReportConditionDto();
		//获取查询结果
		List<UnloadDiffReportEntity> list = unloadDiffReportService.queryUnloadDiffReportList(queryCondition, this.getLimit(), this.getStart());
		List<UnloadDiffReportEntity> resList = null;
		if (configurationParamsService.queryTfrSwitch4Ecs()) {
			String [] reqMsg = new String[UnloadConstants.SONAR_NUMBER_7];
			reqMsg[0]=queryCondition.getOrgCode();
			reqMsg[1]=queryCondition.getVehicleNo();
			reqMsg[2]=queryCondition.getUnloadTaskNo();
			reqMsg[UnloadConstants.SONAR_NUMBER_3]=queryCondition.getLoadManCode();
			reqMsg[UnloadConstants.SONAR_NUMBER_4]=queryCondition.getHandleStatus();
			reqMsg[UnloadConstants.SONAR_NUMBER_5]=queryCondition.getDiffReportNo();
			reqMsg[UnloadConstants.SONAR_NUMBER_6]=queryCondition.getWaybillNo();
			resList = (List<UnloadDiffReportEntity>) fossToWkService.getExpressLessCargoList(reqMsg, queryCondition.getBeginCreateTime(), queryCondition.getEndCreateTime());
		}
        int totleCountMore = 0;
        int totleCountLack = 0;
       //218427 sonar 为空判断
        if(resList==null){
        	throw new TfrBusinessException("resList为空");
        }
        if (CollectionUtils.isEmpty(list)) {
            list = resList;
        } else {
            if(!CollectionUtils.isEmpty(resList)){
                for(int i = 0; i < list.size(); i++ ){
                    for (int j = 0; j < resList.size(); j++ ) {
                        if (list.get(i).getDiffReportNo().equals(resList.get(j).getDiffReportNo())) {
                            totleCountMore = list.get(i).getMoreGoodsPieces() + resList.get(j).getMoreGoodsPieces();   // 多货件数
                            totleCountLack = list.get(i).getLackGoodsPieces()+ resList.get(j).getLackGoodsPieces();     // 少货件数
                            if((list.get(i).getHandleStatus().equals(resList.get(j).getHandleStatus()))){
                            	list.get(i).setHandleStatus(resList.get(j).getHandleStatus());
                            }else{
                            	list.get(i).setHandleStatus("RESOLVING");
                            }
                            list.get(i).setMoreGoodsPieces(totleCountMore);
                            list.get(i).setLackGoodsPieces(totleCountLack);
                            resList.remove(j);
                            --j;
                        }
                    }
                }
                
                list.addAll(resList);
            }
        }
		//获取总条数
		//Long totalCount = unloadDiffReportService.queryUnloadDiffReportCount(queryCondition);
		//分页
		//this.setTotalCount(totalCount);
		//查询结果返回前台
		unloadDiffReportVo.setUnloadDiffReportEntityList(list);
		//返回success
		return returnSuccess();
	}

	/**
	 * @Title: querySuperiorOrgByOrgCode 
	 * @Description: 查询当前部门顶级外场 
	 * @return    
	 * @return String    返回类型 
	 * querySuperiorOrgByOrgCode
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-8-2下午2:46:40
	 */
	public String querySuperiorOrgByOrgCode(){
		try{
			//当前部门Code
			String currentDeptCode = FossUserContext.getCurrentDeptCode();
			//当前部门顶级组织
			OrgAdministrativeInfoEntity administrativeInfo = unloadTaskService.querySuperOrgByOrgCode(currentDeptCode);
			//当前部门顶级组织code
			String orgCode = administrativeInfo.getCode();
			unloadDiffReportVo.setSuperOrgCode(orgCode);
		}catch(TfrBusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 根据卸车差异报告获取基本信息及详情列表(运单)
	 * @author 045923-foss-shiwei
	 * @date 2012-12-11 上午11:12:27
	 */
	@SuppressWarnings("unchecked")
	public String showUnloadDiffReportAndDetail(){
		//获取卸车差异编号
		String diffReportNo = unloadDiffReportVo.getDiffReportNo();
		//获取卸车差异报告基本信息
		UnloadDiffReportEntity baseEntity = unloadDiffReportService.queryUnloadDiffReportBasicInfo(diffReportNo);
		String diffReportId="";
		//获取卸车差异报告ID
		if(baseEntity!=null){
			diffReportId = baseEntity.getId();
		}else{
			baseEntity = new UnloadDiffReportEntity();
			String [] reqMsg = new String[UnloadConstants.SONAR_NUMBER_7];
			reqMsg[0]=FossUserContext.getCurrentDeptCode();
			reqMsg[1]="";
			reqMsg[2]="";
			reqMsg[UnloadConstants.SONAR_NUMBER_3]="";
			reqMsg[UnloadConstants.SONAR_NUMBER_4]="";
			reqMsg[UnloadConstants.SONAR_NUMBER_5]=diffReportNo;
			reqMsg[UnloadConstants.SONAR_NUMBER_6]="";
			List<UnloadDiffReportEntity> resList =(List<UnloadDiffReportEntity>)fossToWkService.getExpressLessCargoList(reqMsg,null,null);
		     if(!CollectionUtils.isEmpty(resList)){
		    	 UnloadDiffReportEntity unloadDiffReportEntity=resList.get(0);
		    	 baseEntity.setUnloadTaskNo(unloadDiffReportEntity.getUnloadTaskNo());
		    	 baseEntity.setVehicleNo(unloadDiffReportEntity.getVehicleNo());
		    	 baseEntity.setLoadManName(unloadDiffReportEntity.getLoadManName());
		    	 baseEntity.setDiffReportNo(unloadDiffReportEntity.getDiffReportNo());
		    	 baseEntity.setReportCreateTime(unloadDiffReportEntity.getReportCreateTime());
		    	 baseEntity.setUnloadType(unloadDiffReportEntity.getUnloadType());
		     } 
		}
		//根据id获取差异报告详情列表
		List<UnloadDiffReportDetailEntity> unloadDiffReportDetailEntityList = unloadDiffReportService.queryUnloadDiffReportDetail(diffReportId);
		//将基本信息及详情列表置于vo中
		unloadDiffReportVo.setBaseEntity(baseEntity);
		//返回明细列表
		unloadDiffReportVo.setUnloadDiffReportDetailEntityList(unloadDiffReportDetailEntityList);
		this.setTotalCount(new Long(unloadDiffReportDetailEntityList.size()));
		//返回success
		return returnSuccess();
	}
	
	/**
	 * 根据卸车差异报告获取基本信息及详情列表(快递)
	 * @author 313352  gouyangyang  2016-05-28
	 * @return 
	 */
	public String showUnloadDiffReportMarkDetail(){
		//获取卸车差异编号
		String diffReportNo = unloadDiffReportVo.getDiffReportNo();
		this.querySuperiorOrgByOrgCode();
		// 顶级外场
		String orgCode = unloadDiffReportVo.getSuperOrgCode();
		// 根据卸车差异编号获取快递差异报告详情列表
		List<UnloadDiffReportDetailEntity> expressList = getExpressDiffInfo(diffReportNo,orgCode);
		unloadDiffReportVo.setExpressEntityList(expressList);
		//返回success
		return returnSuccess();
	}
	
	@SuppressWarnings("unchecked")
	private List<UnloadDiffReportDetailEntity> getExpressDiffInfo(String diffReportNo,String orgCode) {
		List<UnloadDiffReportDetailEntity> returnList = null;
		try {
			FossToWKResponseAndPageEntity returnEntity = fossToWkService.getExpressDiffList(diffReportNo,orgCode, this.limit, this.start);
			if (returnEntity != null) {
				if ("1".equals(returnEntity.getStatus())) {
					returnList = (List<UnloadDiffReportDetailEntity>) returnEntity.getData();
					this.setTotalCount(returnEntity.getTotalRows());
				}
			}
		} catch (TfrBusinessException e) {
			Log.error("获取快递卸车差异异常", e);
			throw new TfrBusinessException("系统异常", e);
		}
		return returnList;
	}
	
	/**
	 * 手动处理卸车少货差异
	 * @author 045923-foss-shiwei
	 * @date 2012-12-26 下午3:17:38
	 */
	public String handleUnloadLackGoodsException(){
		//获取前台传入的差异报告ID、运单号、流水号
		String diffReportId = unloadDiffReportVo.getDiffReportId();
		//获取前台传入的流水号
		String waybillNo = unloadDiffReportVo.getWaybillNo();
		//获取传入的流水号
		String serialNo = unloadDiffReportVo.getSerialNo();
		//获取传入的备注
		String note = unloadDiffReportVo.getNote();
		try{
			//根据传入的信息处理卸车少货差异
			unloadDiffReportService.handleUnloadLackGoodsException(diffReportId, waybillNo, serialNo,note);
		}catch(BusinessException e){
			//返回业务异常信息
			return returnError(e);
		}
		//返回success
		return returnSuccess();
	}
	
	/**
	 * @Title: exportUnloadWayBillByTaskNo 
	 * @Description: 卸车差异报告明细导出 
	 * @return    
	 * @return String    返回类型 
	 * exportUnloadDifferencesReportDetail
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-7-8下午5:37:02
	 */
	public String exportUnloadDifferencesReportDetail() {
		try {
			//获取卸车差异编号
			String diffReportNo = unloadDiffReportVo.getDiffReportNo();
			
			fileName = encodeFileName("卸车差异报告明细");
			excelStream = unloadDiffReportService.exportUnloadDifferencesReportDetail(diffReportNo);
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
	 * excelStream   
	 *   
	 * @return  the excelStream   
	 */
	
	public InputStream getExcelStream() {
		return excelStream;
	}

	/**   
	 * @param excelStream the excelStream to set
	 * Date:2013-7-8下午5:36:21
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
	 * Date:2013-7-8下午5:36:21
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}