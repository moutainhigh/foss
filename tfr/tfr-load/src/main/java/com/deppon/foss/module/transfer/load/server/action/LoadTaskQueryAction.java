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
 *  PROJECT NAME  : tfr-load
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/action/LoadTaskQueryAction.java
 *  
 *  FILE NAME          :LoadTaskQueryAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadTaskQueryService;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportWayBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadTaskDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadWayBillDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.LoadTaskVo;

/**
 * 查询装车任务
 * @author ibm-zhangyixin
 * @date 2012-11-12 上午11:10:50
 */
/**
 * @author 352203
 *
 */
public class LoadTaskQueryAction extends AbstractAction {
	
	private static final long serialVersionUID = -1951433063704715005L;
	/**装车任务Vo**/
	private LoadTaskVo loadTaskVo = new LoadTaskVo();
	/**查询装车任务Service**/
	private ILoadTaskQueryService loadTaskQueryService;
	/** 导出Excel 文件流*/
	private InputStream excelStream;
	/** 导出Excel 文件名*/
	private String fileName;
	
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * 设置 查询装车任务Service*.
	 *
	 * @param loadTaskQueryService the new 查询装车任务Service*
	 */
	public void setLoadTaskQueryService(ILoadTaskQueryService loadTaskQueryService) {
		this.loadTaskQueryService = loadTaskQueryService;
	}

	/**
	 * 设置 装车任务Vo*.
	 *
	 * @param loadTaskVo the new 装车任务Vo*
	 */
	public void setLoadTaskVo(LoadTaskVo loadTaskVo) {
		this.loadTaskVo = loadTaskVo;
	}

	/**
	 * 获取 装车任务Vo*.
	 *
	 * @return the 装车任务Vo*
	 */
	public LoadTaskVo getLoadTaskVo() {
		return loadTaskVo;
	}

	/**
	 * 查询装车任务
	 * @author ibm-zhangyixin
	 * @date 2012-12-5 上午10:18:34
	 */
	@JSON
	public String queryLoadTask() {
		try {
			Long totCount = loadTaskQueryService.getTotCount(loadTaskVo.getLoadTaskDto());
			this.setTotalCount(totCount);
			loadTaskVo.setLoadTaskList(loadTaskQueryService.queryLoadTask(loadTaskVo.getLoadTaskDto(), this.getLimit(), this.getStart()));
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 查询出装车明细中的数据
	 * @author ibm-zhangyixin
	 * @date 2012-12-4 下午12:01:18
	 */
	@JSON
	public String queryLoadWayBillByTaskNo() {
		try {
			String taskNo = loadTaskVo.getLoadTaskDto().getTaskNo();
			String loadSource = loadTaskVo.getLoadTaskDto().getSource();
			//装车明细上方Form中的数据
			LoadTaskDto loadTaskDto = loadTaskQueryService.getLoadTaskByTaskNo(taskNo);
			loadTaskVo.setLoadTaskDto(loadTaskDto);
			//装车明细中表格中需要的数据
			List<LoadWayBillDetailDto> loadWaybillDetailList = loadTaskQueryService.queryLoadWayBillByTaskNo(taskNo, loadSource);
			loadTaskVo.setLoadWaybillDetailList(loadWaybillDetailList);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 
	 * @Title: exportLoadWayBillByTaskNo 
	 * @Description: 根据任务号导出装车明细数据 
	 * @return    
	 * @return String    返回类型 
	 * exportLoadWayBillByTaskNo
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-24下午3:07:52
	 */
	public String exportLoadWayBillByTaskNo() {
		try {
			String taskNo = loadTaskVo.getLoadTaskDto().getTaskNo();
			String loadSource = loadTaskVo.getLoadTaskDto().getSource();
			fileName = encodeFileName("装车明细");
			excelStream = loadTaskQueryService.exportLoadWayBillByTaskNo(taskNo, loadSource);
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
	 * @Title: exportLoadTask 
	 * @Description: 根据查询参数导出装车任务
	 * @return    
	 * @return String    返回类型 
	 * exportLoadTask
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-29上午9:55:45
	 */
	public String exportLoadTask() {
		try {
			fileName = encodeFileName("装车任务");
			excelStream = loadTaskQueryService.exportLoadTask(loadTaskVo.getLoadTaskDto());
		} catch (TfrBusinessException e) {
			return returnError(e);
		} catch (UnsupportedEncodingException e) {
			return returnError(new TfrBusinessException("转换字符串编码时出错!"));
		}
		return returnSuccess();
	}

	/**
	 * 根据装车任务ID查询出所有装车人员
	 * @author ibm-zhangyixin
	 * @date 2012-12-5 上午10:18:58
	 */
	@JSON
	public String queryLoaderByTaskId() {
		try {
			List<LoaderParticipationEntity> loaderParticipationList = loadTaskQueryService.queryLoaderByTaskId(loadTaskVo.getLoaderParticipation().getTaskId());
			loadTaskVo.setLoaderParticipationList(loaderParticipationList);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 根据装车任务ID 查询出所有理货员
	 * @author ibm-zhangyixin
	 * @date 2012-12-17 下午6:43:28
	 */
	@JSON
	public String queryLoadWayBillDetail() {
		try {
			List<LoaderParticipationEntity> loaderParticipationList = loadTaskQueryService.queryLoaderByTaskId(loadTaskVo.getLoaderParticipation().getTaskId());
			loadTaskVo.setLoaderParticipationList(loaderParticipationList);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 根据loadWaybillDetailId得到所有流水号
	 * @author ibm-zhangyixin
	 * @date 2012-12-17 下午6:43:44
	 */
	@JSON
	public String queryloadSerialNoByLoadWaybillDetailId() {
		try {
			List<LoadSerialNoEntity> loadSerialNoList = loadTaskQueryService.queryloadSerialNoByLoadWaybillDetailId(loadTaskVo);
			loadTaskVo.setLoadSerialNoList(loadSerialNoList);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 根据配送装车No获取配送装车Id
	 * @author ibm-zhangyixin
	 * @date 2012-12-17 下午6:44:03
	 */
	@JSON
	public String queryLoadGaprepIdByGaprepNo() {
		try {
			String loadGaprepId = loadTaskQueryService.queryLoadGaprepIdByGaprepNo(loadTaskVo.getGaprepNo());
			loadTaskVo.setLoadGaprepId(loadGaprepId);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 根据派送装车差异报告ID查询出派送装车数据
	 * @author ibm-zhangyixin
	 * @date 2012-12-17 下午6:44:16
	 */
	@JSON
	public String queryLoadGaprepReport() {
		try {
			DeliverLoadGapReportEntity loadGapReport = loadTaskQueryService.queryLoadGaprepReport(loadTaskVo.getLoadGaprepId());
			List<DeliverLoadGapReportWayBillEntity> reportWayBills = loadTaskQueryService.queryDeliverLoadGapReportWayBills(loadTaskVo.getLoadGaprepId());
			loadTaskVo.setLoadGapReport(loadGapReport);
			loadTaskVo.setReportWayBills(reportWayBills);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
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
		loadTaskVo.setOutfieldCode(orgEntity.getCode());
		return returnSuccess();
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
	 * Date:2013-4-24下午3:13:24
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
	 * Date:2013-4-24下午3:13:24
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}