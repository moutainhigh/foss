/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/DestinationLineAction.java
 * 
 * FILE NAME        	: DestinationLineAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.base.baseinfo.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.define.ColumnConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDepartureStandardService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDepartureStandardEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LineException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExpressOriginatingLineVo;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * 快递到达线路控制类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-30 上午10:46:26 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-30 上午10:46:26
 * @since
 * @version
 */
public class ExpressDestinationLineAction extends AbstractAction {
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpressDestinationLineAction.class);

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -7284101423203887931L;

    /**
     * 线路服务类.
     */
    private IExpressLineService expresslineService;
    
    /**
     * 发车标准service.
     */
    private IExpressDepartureStandardService expressdepartureStandardService;

    /**
     * 前台注入参数.
     */
    private ExpressOriginatingLineVo lineVo = new ExpressOriginatingLineVo();
    
    /**
     * 导出Excel 文件名.
     */
    private String downloadFileName;
    
    /**
     * 导出Excel 文件流.
     */
    private InputStream inputStream;

    /**
     * 获取 导出Excel 文件名.
     *
     * @return the 导出Excel 文件名
     */
    public String getDownloadFileName() {
        return downloadFileName;
    }
    
    /**
     * 设置 导出Excel 文件名.
     *
     * @param downloadFileName the new 导出Excel 文件名
     */
    public void setDownloadFileName(String downloadFileName) {
        this.downloadFileName = downloadFileName;
    }
    
    /**
     * 获取 导出Excel 文件流.
     *
     * @return the 导出Excel 文件流
     */
    public InputStream getInputStream() {
        return inputStream;
    }
    
    /**
     * 设置 导出Excel 文件流.
     *
     * @param inputStream the new 导出Excel 文件流
     */
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    
    /**
     * <p>
     * 新增到达线路
     * </p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-9 下午6:12:14
     * @see
     */
    @JSON
    public String addDestinationLine() {
	try {
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    // 获取Form信息
	    ExpressLineEntity entity = lineVo.getEntity();
	    entity.setCreateUser(userCode);
	    entity.setModifyUser(userCode);
	    //设置到达线路默认为汽运
	    entity.setTransType(DictionaryValueConstants.BSE_LINE_TRANSTYPE_QIYUN);
	    // 设置线路类型为：到达线路
	    entity.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_TARGET);

	    //添加线路  保存成功返回一个对象
	    entity = expresslineService.addLine(entity);
	    //保存到VO
	    lineVo.setEntity(entity);

	    return returnSuccess(MessageType.SAVE_SUCCESS);
	} catch (LineException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * <p>
     * 作废到达线路
     * </p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-9 下午6:12:14
     * @see
     */
    @JSON
    public String deleteDestinationLine() {
	try {
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    //批量作废线路
	    expresslineService.deleteLineList(lineVo.getCodeList(), userCode);
	    //返回消息
	    return returnSuccess(MessageType.DELETE_SUCCESS);
	} catch (LineException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * <p>
     * 修改到达线路
     * </p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-9 下午6:12:14
     * @see
     */
    @JSON
    public String updateDestinationLine() {

	try {
	    UserEntity user =  FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    // 获取Form信息
	    ExpressLineEntity entity = lineVo.getEntity();
	    entity.setModifyUser(userCode);
	    //设置到达线路默认为汽运
	    entity.setTransType(DictionaryValueConstants.BSE_LINE_TRANSTYPE_QIYUN);
	    // 设置线路类型为：到达线路
	    entity.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_TARGET);

	    // 更新成功返回一个对象
	    entity = expresslineService.updateLine(entity);
	    //设置 始发线路实体类.
	    lineVo.setEntity(entity);

	    return returnSuccess(MessageType.SAVE_SUCCESS);
	} catch (LineException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}

    }

    /**
     * <p>
     * 分页查询到达线路所有信息
     * </p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-8 下午1:49:44
     * @see
     */
    @JSON
    public String queryDestinationLine() {
	try {
		ExpressLineEntity lineEntity = lineVo.getEntity();
	    // 设置线路类型为：到达线路
	    lineEntity
		    .setLineSort(DictionaryValueConstants.BSE_LINE_SORT_TARGET);
	    List<ExpressLineEntity> lineList = expresslineService.queryLineListByCondition(
		    lineEntity, this.getStart(), this.getLimit());
	    lineVo.setLineEntities(lineList);

	    // 查询总记录数
	    Long totalCount = expresslineService.countLineListByCondition(lineVo
		    .getEntity());
	    // 设置totalCount
	    this.setTotalCount(totalCount);

	    return returnSuccess();
	} catch (LineException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * <p>根据虚拟编码查询线路信息和发车标准</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-13 上午11:25:38
     * @see
     */
    public String queryLineAndStandardByCode(){
	try {
	    String lineVirtualCode = lineVo.getLineVirtualCode();
	    //通过虚拟编码查询线路信息
	    ExpressLineEntity lineEntity = expresslineService.queryLineByVirtualCode(lineVirtualCode);
	    //发车标准
	    List<ExpressDepartureStandardEntity> departureStandardEntityList = expressdepartureStandardService
			.queryDepartureStandardListByLineVirtualCode(lineVirtualCode);
	    //设置 始发线路实体类.
	    lineVo.setEntity(lineEntity);
	    //设置 发车标准实体List.
	    lineVo.setDepartureStandardEntityList(convertList(departureStandardEntityList));
	    return returnSuccess();
	} catch (LineException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * <p>导出到达线路数据至EXCEl</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-12-21 下午3:43:31
     * @see
     */
    public String exportDestinationLines(){
	try {
	    //导出文件名称
	    //downloadFileName = URLEncoder.encode(ColumnConstants.EXPORT_DESTINATION_LINE_NAME, "UTF-8");
		  downloadFileName =new String(ColumnConstants.EXPORT_EXPRESS_DESTINATION_LINE_NAME.getBytes("UTF-8"),"iso-8859-1");
		//获取查询参数
	    ExpressLineEntity entity = lineVo.getEntity();
	    if(null == entity){
		entity = new ExpressLineEntity();
	    }
	    //设置线路类型为到达线路
	    entity.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_TARGET);
	    //导出到达线路
	    ExportResource exportResource =expresslineService.exportLineList(entity);
	    //创建导出表头对象
	    ExportSetting exportSetting = new ExportSetting();
	    //设置名称
	    exportSetting.setSheetName(ColumnConstants.EXPORT_EXPRESS_DESTINATION_LINE_NAME);
	    //创建导出工具类
	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	    // 导出成文件
	    inputStream =  objExporterExecutor.exportSync(exportResource, exportSetting);
	    
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	} catch (UnsupportedEncodingException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError("UnsupportedEncodingException", e);
	}
    }
    
    /**
     * <p>批量为准点发车时间、准点到达时间添加冒号</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-3-16 下午3:17:03
     * @param list
     * @return
     * @see
     */
    private List<ExpressDepartureStandardEntity> convertList(List<ExpressDepartureStandardEntity> list){
	if(!CollectionUtils.isEmpty(list)){
	    List<ExpressDepartureStandardEntity> entityList = new ArrayList<ExpressDepartureStandardEntity>();
	    for(ExpressDepartureStandardEntity entity : list){
		entityList.add(convertInfos(entity));
	    }
	    
	    return entityList;
	}else {
	    return list;
	}
    }
    
    /**
     * <p>为准点发车时间、准点到达时间添加冒号</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-3-16 下午2:59:56
     * @param entity
     * @return
     * @see
     */
    private ExpressDepartureStandardEntity convertInfos(ExpressDepartureStandardEntity entity){
	if(null != entity){
	    //准点发车时间
	    entity.setLeaveTime(addColon(entity.getLeaveTime()));
	    // 准点到达时间
	    entity.setArriveTime(addColon(entity.getArriveTime()));
	    
	    return entity;
	}else {
	    return entity;
	}
    }
    
    /**
     * <p>添加冒号字符串</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-3-16 下午2:55:40
     * @param colonStr 
     * @return
     * @see
     */
    private String addColon(String colonStr){
	if(StringUtil.isNotBlank(colonStr)){
	    StringBuffer buffer = new StringBuffer();
	    //添加冒号
	    return buffer.append(colonStr.substring(NumberConstants.STRING_LOCATION_0, NumberConstants.STRING_LOCATION_2)).append(":").append(colonStr.substring(NumberConstants.STRING_LOCATION_2, NumberConstants.STRING_LOCATION_4)).toString();
	}else {
	    return colonStr;
	}
    }
    
    /**
     * 获取 前台注入参数.
     *
     * @return the 前台注入参数
     */
    public ExpressOriginatingLineVo getLineVo() {
	return lineVo;
    }

    /**
     * 设置 前台注入参数.
     *
     * @param lineVo the new 前台注入参数
     */
    public void setLineVo(ExpressOriginatingLineVo lineVo) {
	this.lineVo = lineVo;
    }

    /**
     * 设置 线路服务类.
     *
     * @param lineService the new 线路服务类
     */
    public void setExpresslineService(IExpressLineService expresslineService) {
	this.expresslineService = expresslineService;
    }
    
    /**
     * 设置 发车标准service.
     *
     * @param departureStandardService the new 发车标准service
     */
    public void setExpressdepartureStandardService(
    	IExpressDepartureStandardService expressdepartureStandardService) {
        this.expressdepartureStandardService = expressdepartureStandardService;
    }
    
    

}
