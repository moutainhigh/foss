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
 * PROJECT NAME	: bse-dict
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/server/action/DataDictionaryAction.java
 * 
 * FILE NAME        	: DataDictionaryAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.server.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;


import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryService;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.api.shared.exception.DataDictionaryValueException;
import com.deppon.foss.module.base.dict.api.shared.exception.MessageType;
import com.deppon.foss.module.base.dict.api.shared.vo.DataDictionaryVo;
import com.deppon.foss.module.base.dict.api.util.DataDictUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;

public class DataDictionaryAction extends AbstractAction {

	private static final long serialVersionUID = -4387687988772020011L;
	/**
	 * 用于注入业务字典服务实现类
	 */
	private IDataDictionaryService dataDictionaryService;

	public void setDataDictionaryService(
			IDataDictionaryService dataDictionaryService) {
		this.dataDictionaryService = dataDictionaryService;
	}

	private IDataDictionaryValueService dataDictionaryValueService;

	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	/**
	 * Vo对象，用于接收前台的数据及返回数据到前台：
	 */
	private DataDictionaryVo dataDictionaryVo = new DataDictionaryVo();

	public DataDictionaryVo getDataDictionaryVo() {
		return dataDictionaryVo;
	}

	public void setDataDictionaryVo(DataDictionaryVo dataDictionaryVo) {
		this.dataDictionaryVo = dataDictionaryVo;
	}
	
	private long lastModifyTime;
	
	public long getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(long lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	/**
	 * .
	 * <p>
	 * 根据entity的属性查询，当属性值为空，或者空白，则这个属性所关联的查询条件无效 <br/>
	 * 方法名：queryDataDictionaryValueByEntity
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-11-07
	 * @since JDK1.6
	 */
	@JSON
	public String queryDataDictionaryValueByEntity() {
		try {
			List<DataDictionaryValueEntity> dataDictionaryValueEntityList = dataDictionaryValueService.queryDataDictionaryValueForView(dataDictionaryVo.getDataDictionaryValueEntity(), start,limit );
			dataDictionaryVo.setDataDictionaryValueEntityList(dataDictionaryValueEntityList);
			Long count = dataDictionaryValueService.queryDataDictionaryValueForViewCount(dataDictionaryVo.getDataDictionaryValueEntity());
			this.setTotalCount(count);// 设置totalCount
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}


	/**
	 * .
	 * <p>
	 * 作废值列表 <br/>
	 * 方法名：deleteValue
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-11-07
	 * @since JDK1.6
	 */
	@JSON
	public String deleteValue() {
		try {
			if(CollectionUtils.isNotEmpty(dataDictionaryVo.getDataDictionaryValueEntityList())){
				for(DataDictionaryValueEntity model:dataDictionaryVo.getDataDictionaryValueEntityList()){
					checkedValue(model);
				}
			}
			dataDictionaryValueService.deleteDataDictionaryValueMore(dataDictionaryVo.getDataDictionaryValueEntityList());
			return returnSuccess(MessageType.DELETE_DATADICTIONARYVALUE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}

	/**
	 * .
	 * <p>
	 * 新增值 <br/>
	 * 方法名：addValue
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-11-07
	 * @since JDK1.6
	 */
	@JSON
	public String addValue() {
		try {
			DataDictionaryValueEntity dataDictionaryValueEntityAdd = dataDictionaryVo.getDataDictionaryValueEntity();
			//如果上级词条为空则设置词条为VALUABLE_GOODS 即贵重物品 @132599 2013-04-23
			if(StringUtils.isBlank(dataDictionaryValueEntityAdd.getTermsCode())){
				dataDictionaryValueEntityAdd.setTermsCode(DictionaryConstants.VALUABLE_GOODS);
			}
			//如果值代码为空自动生成一个uuid @132599 2013-04-23
			if(StringUtils.isBlank(dataDictionaryValueEntityAdd.getValueCode())){
				dataDictionaryValueEntityAdd.setValueCode(UUIDUtils.getUUID());
			}
			//如果序号为空，则取贵重物品最大序号加1 @132599 2013-04-23
			if(dataDictionaryValueEntityAdd.getValueSeq().isEmpty()){
				long valuablesValueSEQ = dataDictionaryValueService.queryMaxValue(DictionaryConstants.VALUABLE_GOODS);
				dataDictionaryValueEntityAdd.setValueSeq(String.valueOf(valuablesValueSEQ + NumberConstants.NUMERAL_ONE));
			}
			checkedValue(dataDictionaryValueEntityAdd);
			//获取当前用户对象
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//设置entity的创建人
			dataDictionaryValueEntityAdd.setCreateUser(currentInfo.getEmpCode());
			
			DataDictionaryValueEntity dataDictionaryValueEntity = dataDictionaryValueService
					.addDataDictionaryValue(dataDictionaryValueEntityAdd);//新增值
			dataDictionaryVo
					.setDataDictionaryValueEntity(dataDictionaryValueEntity);//并给前台返回新增的值
			return returnSuccess(MessageType.SAVE_DATADICTIONARYVALUE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}
	
	/**
	 * .
	 * <p>
	 * 判断该操作的数据字典是否是后勤系统同步的数据，如果是则抛出异常，不是则return <br/>
	 * 方法名：checkedValue
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2013-1-17
	 * @since JDK1.6
	 */
	private void checkedValue(DataDictionaryValueEntity entity) throws BusinessException{
		if(entity==null){
			return;
		}else{
			if(DictionaryConstants.VEHICLE_STOP_REASON_TERMSCODE.equals(entity.getTermsCode())||DictionaryConstants.LMS_TRAILER_TYPE_TERMSCODE.equals(entity.getTermsCode())){
				throw new DataDictionaryValueException("后勤系统的数据字典不可编辑！",null); 
			}/*else if(DictionaryConstants.LMS_TRAILER_TYPE_TERMSCODE.equals(entity.getTermsCode())){
				throw new DataDictionaryValueException("后勤系统的数据字典不可编辑！",null); 
			}*/else{
				return;
			}
		}
		
	}
	/**
	 * .
	 * <p>
	 * 查询值详情 <br/>
	 * 方法名：queryDataDictionaryValueByTermsCodeValueCode
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-11-07
	 * @since JDK1.6
	 */
	@JSON
	public String queryDataDictionaryValueByTermsCodeValueCode() {
		try {
			DataDictionaryValueEntity dataDictionaryValueEntity = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(dataDictionaryVo.getDataDictionaryValueEntity().getTermsCode(), dataDictionaryVo.getDataDictionaryValueEntity().getValueCode());
			dataDictionaryVo.setDataDictionaryValueEntity(dataDictionaryValueEntity);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}
	
	/**
	 * 根据贵重物品的词条代码来查询它下面的值信息
	 * @author 132599-foss-shenweihua
	 * @时间 2013-04-24
	 * @since JDK1.6
	 */
	@JSON
	public String queryValuablesValueByTermsCode() {
		try {
			//获取数据字典entity
			DataDictionaryValueEntity entity = dataDictionaryVo.getDataDictionaryValueEntity();
			//在数据字典entity里设置上级词条代码为贵重物品的词条代码
			entity.setTermsCode(DictionaryConstants.VALUABLE_GOODS);
			List<DataDictionaryValueEntity> dataDictionaryValueEntityList = dataDictionaryValueService.queryDataDictionaryValueForView(entity,start,limit );
			dataDictionaryVo.setDataDictionaryValueEntityList(dataDictionaryValueEntityList);
			Long count = dataDictionaryValueService.queryDataDictionaryValueForViewCount(dataDictionaryVo.getDataDictionaryValueEntity());
			this.setTotalCount(count);// 设置totalCount
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}

	/**
	 * .
	 * <p>
	 * 修改值 <br/>
	 * 方法名：updateValue
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-11-07
	 * @since JDK1.6
	 */
	@JSON
	public String updateValue() {
		try {
			DataDictionaryValueEntity dataDictionaryValueEntityUpdate = dataDictionaryVo.getDataDictionaryValueEntity();
			checkedValue(dataDictionaryValueEntityUpdate);
			//获取当前用户对象
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//设置entity的更新人
			dataDictionaryValueEntityUpdate.setModifyUser(currentInfo.getEmpCode());
			
			DataDictionaryValueEntity dataDictionaryValueEntity = dataDictionaryValueService
					.updateDataDictionaryValue(dataDictionaryValueEntityUpdate);// 修改数据
			dataDictionaryVo
					.setDataDictionaryValueEntity(dataDictionaryValueEntity);// 返回前台修改后的数据
			return returnSuccess(MessageType.UPDATE_DATADICTIONARYVALUE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}

	
	/**
	 * .
	 * <p>
	 * 查询所有业务字典数据 <br/>
	 * 方法名：searchAllDataDictionary
	 * </p>
	 * 
	 * @author 053990-foss-zhongtingjie
	 * @时间 2012-11-13
	 * @since JDK1.6
	 */
	@JSON
	public String searchAllDataDictionary() {
		try {
			dataDictionaryVo.setDataDictionaryEntitys(
				dataDictionaryService.queryDataDictionarys()
			);
			long lLastChangeVersionNo = dataDictionaryValueService.getLastChangeVersionNo();
			clientVersionNo = lLastChangeVersionNo;
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}

	/**
	 * .
	 * <p>
	 * 查询所有业务字典词<br/>
	 * 方法名：getPropList
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-11-13
	 * @since JDK1.6
	 */
	@JSON
	public String getPropList() {
		try {
			List<DataDictionaryEntity> dataDictionaryEntityList = DataDictUtil.getPropList();
			dataDictionaryVo.setDataDictionaryEntitys(dataDictionaryEntityList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}
	
	private long clientVersionNo;
	
	public long getClientVersionNo() {
		return clientVersionNo;
	}

	public void setClientVersionNo(long clientVersionNo) {
		this.clientVersionNo = clientVersionNo;
	}

	/**
	 * .
	 * <p>
	 * 判断数据字典内容是否有更新 <br/>
	 * 方法名：isDictionaryHasChanged
	 * </p>
	 * 
	 * @author 107046-foss-zengxiantao
	 * @时间 2013-05-06
	 * @since JDK1.6
	 */
	@JSON
	public String isDictionaryHasChanged() {
		try {
			long lLastChangeVersionNo = dataDictionaryValueService.getLastChangeVersionNo();
			if(clientVersionNo == lLastChangeVersionNo) {
				return returnSuccess("keep");
			} else {
				clientVersionNo = lLastChangeVersionNo;
				return returnSuccess("refresh");
			}
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}

}
