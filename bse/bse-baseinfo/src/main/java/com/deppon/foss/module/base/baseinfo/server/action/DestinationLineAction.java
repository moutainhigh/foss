/**
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
 *
 *
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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.define.ColumnConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDepartureStandardService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.LineUserOrgDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LineException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.OriginatingLineVo;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.base.util.define.NumberConstants;

/**
 * 到达线路控制类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-30 上午10:46:26 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-30 上午10:46:26
 * @since
 * @version
 */
public class DestinationLineAction extends AbstractAction {
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DestinationLineAction.class);

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -7284101423203887931L;

    /**
     * 线路服务类.
     */
    private ILineService lineService;
    
    /**
     * 发车标准service.
     */
    private IDepartureStandardService departureStandardService;

    /**
     * 前台注入参数.
     */
    private OriginatingLineVo lineVo = new OriginatingLineVo();
    
    /**
     * 导出Excel 文件名.
     */
    private String downloadFileName;
    
    /**
     * 导出Excel 文件流.
     */
    private InputStream inputStream;
    /**
     * 人员接口Service
     */
    private IEmployeeService employeeService;
    /**
     * 组织接口Service
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    /**
     * 组织外围接口Service
     */
    private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
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
     *<p>Title: queryCurrentUserOrgResource</p>
     *<p>查询当前用户数据权限</p>
     *@author 130566-ZengJunfan
     *@date 2014-5-23下午2:09:04
     * @return
     */
    @JSON
    public String queryCurrentUserOrgResource(){
    	try {
    		CurrentInfo currentInfo =FossUserContext.getCurrentInfo();
    		//获取当前登录人的所属部门
    		EmployeeEntity employeeEntity =employeeService.queryEmployeeByEmpCode(currentInfo.getEmpCode());
    		LineUserOrgDto dto =new LineUserOrgDto();
    		//非空校验
    		if(null!= employeeEntity &&StringUtils.isNotBlank(employeeEntity.getOrgCode())){
    			OrgAdministrativeInfoEntity org =orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(employeeEntity.getOrgCode());
    			if(null!=org){
    				//校验是否是个营业部
    				if(StringUtils.isNotBlank(org.getSalesDepartment())&&StringUtils.equals(org.getSalesDepartment(), FossConstants.YES)){
    					dto.setSalesOrgs(org.getCode());
    					dto.setSalesOrgsName(org.getName());
    				//校验是否是个外场	
    				}else if(StringUtils.isNotBlank(org.getTransferCenter())&&StringUtils.equals(org.getTransferCenter(), FossConstants.YES)){
    					dto.setTransferOrg(org.getCode());
    					dto.setTransferOrgName(org.getName());
    				}else{
    					List<String> bizTypes =new ArrayList<String>();
    					bizTypes.add("TRANSFER_CENTER");
    					//查询所属上级部门是否是个外场
    					OrgAdministrativeInfoEntity superOrg =orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(org.getCode(), bizTypes);
    					//若上级外场存在， 则返回上级外场，若不存在，则返回用户数据权限
    					if(null !=superOrg){
    						dto.setTransferOrg(superOrg.getCode());
    						dto.setTransferOrgName(superOrg.getName());
    					}else{
    						//默认为专业部门
    						List<String> orgs = FossUserContext.getCurrentUserManagerDeptCodes();
    						dto.setOrgList(orgs);
    					}
    				}
    			}
    		}
    		lineVo.setLineUserOrgDto(dto);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
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
	    LineEntity entity = lineVo.getEntity();
	    entity.setCreateUser(userCode);
	    entity.setModifyUser(userCode);
	    //设置到达线路默认为汽运
	    entity.setTransType(DictionaryValueConstants.BSE_LINE_TRANSTYPE_QIYUN);
	    // 设置线路类型为：到达线路
	    entity.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_TARGET);

	    //添加线路  保存成功返回一个对象
	    entity = lineService.addLine(entity);
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
	    lineService.deleteLineList(lineVo.getCodeList(), userCode);
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
	    LineEntity entity = lineVo.getEntity();
	    entity.setModifyUser(userCode);
	    //设置到达线路默认为汽运
	    entity.setTransType(DictionaryValueConstants.BSE_LINE_TRANSTYPE_QIYUN);
	    // 设置线路类型为：到达线路
	    entity.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_TARGET);

	    // 更新成功返回一个对象
	    entity = lineService.updateLine(entity);
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
	    LineEntity lineEntity = lineVo.getEntity();
	    // 设置线路类型为：到达线路
	    lineEntity
		    .setLineSort(DictionaryValueConstants.BSE_LINE_SORT_TARGET);
	    List<LineEntity> lineList = lineService.queryLineListByCondition(
		    lineEntity, this.getStart(), this.getLimit());
	    lineVo.setLineEntities(lineList);

	    // 查询总记录数
	    Long totalCount = lineService.countLineListByCondition(lineVo
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
	    LineEntity lineEntity = lineService.queryLineByVirtualCode(lineVirtualCode);
	    //发车标准
	    List<DepartureStandardEntity> departureStandardEntityList = departureStandardService
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
		  downloadFileName =new String(ColumnConstants.EXPORT_DESTINATION_LINE_NAME.getBytes("UTF-8"),"iso-8859-1");
		//获取查询参数
	    LineEntity entity = lineVo.getEntity();
	    if(null == entity){
		entity = new LineEntity();
	    }
	    //设置线路类型为到达线路
	    entity.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_TARGET);
	    //导出到达线路
	    ExportResource exportResource =lineService.exportLineList(entity);
	    //创建导出表头对象
	    ExportSetting exportSetting = new ExportSetting();
	    //设置名称
	    exportSetting.setSheetName(ColumnConstants.EXPORT_DESTINATION_LINE_NAME);
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
    private List<DepartureStandardEntity> convertList(List<DepartureStandardEntity> list){
	if(!CollectionUtils.isEmpty(list)){
	    List<DepartureStandardEntity> entityList = new ArrayList<DepartureStandardEntity>();
	    for(DepartureStandardEntity entity : list){
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
    private DepartureStandardEntity convertInfos(DepartureStandardEntity entity){
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
    public OriginatingLineVo getLineVo() {
	return lineVo;
    }

    /**
     * 设置 前台注入参数.
     *
     * @param lineVo the new 前台注入参数
     */
    public void setLineVo(OriginatingLineVo lineVo) {
	this.lineVo = lineVo;
    }

    /**
     * 设置 线路服务类.
     *
     * @param lineService the new 线路服务类
     */
    public void setLineService(ILineService lineService) {
	this.lineService = lineService;
    }
    
    /**
     * 设置 发车标准service.
     *
     * @param departureStandardService the new 发车标准service
     */
    public void setDepartureStandardService(
    	IDepartureStandardService departureStandardService) {
        this.departureStandardService = departureStandardService;
    }

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
}
