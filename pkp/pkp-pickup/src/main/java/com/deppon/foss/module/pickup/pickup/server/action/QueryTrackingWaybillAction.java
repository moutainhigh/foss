/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-pickup
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/server/action/QueryTrackingWaybillAction.java
 * 
 * FILE NAME        	: QueryTrackingWaybillAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.pickup.api.server.service.IQueryTrackingWaybillService;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.TrackingWaybillConditionDto;
import com.deppon.foss.module.pickup.pickup.api.shared.exception.QueryTrackingWaybillException;
import com.deppon.foss.module.pickup.pickup.api.shared.vo.QueryTrackingWaybillVo;

/**
 * 
 *运单追踪Action
 * @author 
 *		ibm-wangfei
 * @date 
 *      Dec 29, 2012 5:30:03 PM
 * @since
 * @version
 */
public class QueryTrackingWaybillAction extends AbstractAction {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Log 对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryTrackingWaybillAction.class);

	/**
	 * vo
	 */
	private QueryTrackingWaybillVo vo = new QueryTrackingWaybillVo();

	/**
	 * 运单追踪service
	 */
	private IQueryTrackingWaybillService queryTrackingWaybillService;
	 /**
		 * 导出文件名
		 */
		private String fileName;
		
		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		/**
		 * 这个输入流对应上面struts.xml中配置的那个excelStream，两者必须一致
		 */
		private InputStream excelStream;  
		public InputStream getExcelStream() {
			return excelStream;
		}

		public void setExcelStream(InputStream excelStream) {
			this.excelStream = excelStream;
		}
	/**
	 * 
	 * 运单追踪查询
	 * 
	 * @return
	 * @author ibm-wangfei
	 * @date Dec 29, 2012 5:29:57 PM
	 */
	@JSON
	public String queryTrackingWaybill() {
		try {
			// 查询符合条件的记录数
			Long totalCount = this.queryTrackingWaybillService.queryTrackingWaybillCount(vo.getConditionDto());
			if (totalCount != null && totalCount.intValue() > 0) {
				vo.setTrackingWaybillDtoList(this.queryTrackingWaybillService.queryTrackingWaybillDtoLit(vo.getConditionDto(), this.getStart(), this.getLimit()));
			} else {
				vo.setTrackingWaybillDtoList(null);
			}
			this.setTotalCount(totalCount);
		} catch (QueryTrackingWaybillException e) {//捕获异常
			return returnError(e);//返回失败
		}
		return returnSuccess();//返回成功
	}

	/**
	 * 
	 * 查询运单追踪信息明细
	 * 
	 * @return
	 * @author ibm-wangfei
	 * @date Dec 29, 2012 5:31:38 PM
	 */
	@JSON
	public String queryTrackingWaybillInfo() {
		try {
			LOGGER.info(ReflectionToStringBuilder.toString(vo));
			// 设置 跟踪运单弹出显示DTO.
			vo.setQueryTrackingWaybillDto(this.queryTrackingWaybillService.queryByWaybillNo(vo.getConditionDto().getWaybillNo()));
		} catch (QueryTrackingWaybillException e) {//捕获异常
			// GO ERROR
			LOGGER.error("error", e);//记录日志
			return returnError(e);
		}
		// GO SUCCESS
		return returnSuccess();//返回成功
	}

	/**
	 * 
	 * 添加运单追踪信息
	 * 
	 * @return
	 * @author ibm-wangfei
	 * @date Dec 29, 2012 5:32:51 PM
	 */
	@JSON
	public String addTrackWaybill() {
		try {
			if (vo != null) {
				// 新增运单追踪记录
				this.queryTrackingWaybillService.addTrackingWaybillEntity(vo.getWaybillTrackInfoEntity());
			} else {
				LOGGER.info("没有追踪记录");//记录日志
			}
		} catch (QueryTrackingWaybillException e) {//捕获异常
			// GO ERROR
			LOGGER.error("", e);
			return returnError(e);
		}
		// GO SUCCESS
		return returnSuccess("跟踪完成！");//返回成功
	}
	//RFOSS2015041738跟踪运单增加导出功能需求  065340 liutao
	/**
	 * 
	 * 转换导出文件的文件名
	 * @author 065340-foss-liutao
	 * @date 2015-06-25
	 * @see
	 */
    public String encodeFileName(String name) throws UnsupportedEncodingException {
    	String returnStr;
    	String agent = (String)ServletActionContext.getRequest().getHeader("USER-AGENT");
    	if(agent != null && agent.indexOf("MSIE") == -1) {
    		returnStr = new String(name.getBytes("UTF-8"), "iso-8859-1");
    	} else {
    		returnStr = URLEncoder.encode(name, "UTF-8");
    	}
    	return returnStr;
    }
	/**
	 * 
	 * 转换导出文件的文件名
	 * @author 065340-foss-liutao
	 * @date 2015-06-25
	 * @see
	 */
	public String export(){
		try {
			//设置文件名
			fileName = encodeFileName("跟踪运单导出");
		} catch (UnsupportedEncodingException se) {
			//记录错误信息
			LOGGER.error("error", se);
		}
		TrackingWaybillConditionDto conditionDto=null;
		if(vo != null){
			conditionDto=vo.getConditionDto();
			excelStream = queryTrackingWaybillService.queryExport(conditionDto);
		}
		//返回成功
		return returnSuccess();
	}
	/**
	 * 获取 vo
	 * 
	 * @return the vo
	 */
	public QueryTrackingWaybillVo getVo() {
		return vo;
	}

	/**
	 * 设置 vo.
	 * 
	 * @param vo 
	 * the new vo
	 */
	public void setVo(QueryTrackingWaybillVo vo) {
		this.vo = vo;
	}


	/**
	 * 设置 运单追踪service.
	 * 
	 * @param queryTrackingWaybillService 
	 * the new 运单追踪service
	 */
	public void setQueryTrackingWaybillService(IQueryTrackingWaybillService queryTrackingWaybillService) {
		this.queryTrackingWaybillService = queryTrackingWaybillService;
	}
}