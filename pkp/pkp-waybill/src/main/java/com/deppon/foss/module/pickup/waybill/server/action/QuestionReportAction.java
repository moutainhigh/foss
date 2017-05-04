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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/action/TodoAction.java
 * 
 * FILE NAME        	: TodoAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/* * PROJECT NAME: pkp-waybill
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.server.action
 * FILE    NAME: TodoAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.waybill.server.action;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.waybill.api.server.service.IUploadITService;
import com.deppon.foss.module.pickup.waybill.shared.vo.ITServiceVo;


/**
 * 问题上报Action
 * @author WangQianJin
 *
 */
public class QuestionReportAction extends AbstractAction{  

	private static final long serialVersionUID = 1L;
	
	/**
	 * 问题上报VO
	 */
	private ITServiceVo vo;
	
	/**
	 * 问题上报Service
	 */
	private IUploadITService uploadITService;

	/**
	 * 问题上报.
	 * 
	 * @return the string
	 * @author WangQianJin
	 */
	@JSON
	public String questionReport(){
		//判断IT服务台一键上报是否启用
		if(!uploadITService.isStartItServiceUpload()){
			returnError(new BusinessException("IT服务台一键上报未启用"));
		}
		try{
			List<ITServiceVo> itList = new ArrayList<ITServiceVo>();
			itList.add(vo);
			//调用接口一键上报
			uploadITService.uploadItService(itList);
		} catch (BusinessException e){
			returnError(e);
		}
		return returnSuccess();		
	}

	public void setVo(ITServiceVo vo) {
		this.vo = vo;
	}

	public ITServiceVo getVo() {
		return vo;
	}

	public void setUploadITService(IUploadITService uploadITService) {
		this.uploadITService = uploadITService;
	}
}