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
 *  PROJECT NAME  : tfr-package
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/server/action/QueryUnpackAction.java
 *  
 *  FILE NAME          :QueryUnpackAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 */
package com.deppon.foss.module.transfer.packaging.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.transfer.packaging.api.server.service.IQueryUnpackService;
import com.deppon.foss.module.transfer.packaging.api.shared.define.PackagingConstants;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryUnpackDetailsEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryUnpackResultEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.dto.CurrentDeptDto;
import com.deppon.foss.module.transfer.packaging.api.shared.exception.PackagingException;
import com.deppon.foss.module.transfer.packaging.api.shared.vo.QueryUnpackVo;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;


/**
 * 
 * 查询营业部代打包装的ACTION层
 * @author 046130-foss-xuduowei
 * @date 2012-10-22 上午8:39:53
 */
public class QueryUnpackAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9033212011249680628L;
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryUnpackAction.class);

	/**
	 * 业务层对象
	 */
	private IQueryUnpackService queryUnpackService;
	

	/**
	 * 数据字典
	 */
	private IDataDictionaryValueService dataDictionaryValueService;
	
	/**
	 * vo对象
	 */
	private QueryUnpackVo queryUnpackVo = new QueryUnpackVo();
	/**
	 * 导出Excel 文件流
	 */
	transient InputStream excelStream;
	/**
	 * 导出Excel 文件名
	 */
	private String fileName;
	
	/**
	 * 
	 * action层，打开查询营业部代打包装界面时，默认代打包装部门为当前操作人所在部门
	 *      本方法即是返回当前部门名称
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-12 下午4:13:29
	 */
	@JSON
	public String queryPackDept(){
		//获取当前组织信息
		try {
			CurrentDeptDto deptDto = queryUnpackService.queryCurrentDept();
			queryUnpackVo.setCurrentDeptDto(deptDto);
			queryUnpackVo.setPackDept(deptDto.getDeptName());
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * 页面加载时获取货物状态
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-1 上午10:23:17
	 */
	@JSON
	public String queryGoodsStatus(){
		//获取下拉框数据，参数从常量里取
		List<DataDictionaryValueEntity> goodsStatusList = 
				dataDictionaryValueService.queryDataDictionaryValueByTermsCode(
						DictionaryConstants.PACKAGING_GOODS_STATUS);
		queryUnpackVo.setGoodsStatusList(goodsStatusList);
		return returnSuccess();
	}
	
	
	
	/**
	 * 
	 * action层，点击查询营业部代打包装界面查询按钮，
	 *      此方法返回符合条件的营业部需要包装的，且需要当前部门包装的货物信息
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-12 下午4:14:43
	 */
	@JSON
	public String queryUnpackAll(){
		
		try {
			//查询结果集
			List<QueryUnpackResultEntity> queryUnpackResultList = queryUnpackService.queryUnpackALL(queryUnpackVo.getQueryUnpackConditionEntity(), this.getLimit(), this.getStart());
			//总数
			//取消分页
			//Long totalCount = queryUnpackService.queryTotalCount(queryUnpackVo.getQueryUnpackConditionEntity());
			//this.setTotalCount(totalCount);
			queryUnpackVo.setQueryUnpackResultList(queryUnpackResultList);
			return returnSuccess();
		} catch (PackagingException e) {			
			return returnError(e);
		}		
		
	}
	
	/**
	 * 
	 * action层，查询每票货物的库存及包装状态信息
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-12 下午4:16:43
	 */
	@JSON
	public String queryUnpackDetails(){
		try {
			
			List<QueryUnpackDetailsEntity> queryUnpackDetailsList = 
					queryUnpackService.queryUnpackDetails(queryUnpackVo.getQueryUnpackConditionEntity());
			queryUnpackVo.setQueryUnpackDetailsList(queryUnpackDetailsList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2013-03-22 下午4:16:43
	 */
	public String exportUnpackExcel(){
		try {
			// 导出文件名
			fileName = this.encodeFileName(PackagingConstants.PACKAGING_EXPORT_FILE_NAME_UNPACK);
			// 导出文件流
			excelStream = queryUnpackService.exportExcelStream(queryUnpackVo.getQueryUnpackConditionEntity());
		} catch (BusinessException e) {
			LOGGER.error(e.getErrorCode());
			return returnError(e);
		}catch (Exception e) {
			//LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			return super.returnError(e.toString(), "");
		}
		return returnSuccess();
	}
	
	/**
	 * 已下是get，set方法
	 */

	public void setQueryUnpackService(IQueryUnpackService queryUnpackService) {
		this.queryUnpackService = queryUnpackService;
	}

	/**
	 * 获取 vo对象.
	 *
	 * @return the vo对象
	 */
	public QueryUnpackVo getQueryUnpackVo() {
		return queryUnpackVo;
	}

	/**
	 * 设置 vo对象.
	 *
	 * @param queryUnpackVo the new vo对象
	 */
	public void setQueryUnpackVo(QueryUnpackVo queryUnpackVo) {
		this.queryUnpackVo = queryUnpackVo;
	}

	/**
	 * 设置 数据字典.
	 *
	 * @param dataDictionaryValueService the new 数据字典
	 */
	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public String getFileName() {
		return fileName;
	}
	
	/**
	 * 生成导出文件名称
	 * @author yuyongxiang
	 * @date 2013年10月11日 11:38:30
	 */
	public String encodeFileName(String fileName) throws BusinessException {
		try {
			String returnStr;
			String agent = (String) ServletActionContext.getRequest().getHeader("USER-AGENT");
			if (agent != null && agent.indexOf("MSIE") == -1) {
				returnStr = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			} else {
				returnStr = URLEncoder.encode(fileName, "UTF-8");
			}
			return returnStr;
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("转换文件名编码失败", e);
			throw new BusinessException(StockException.EXPORT_FILE_ERROR_CODE, "");
		}
	}
	
	
	
	
}