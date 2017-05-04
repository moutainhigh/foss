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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/vo/AbandonGoodsApplicationVo.java
 * 
 * FILE NAME        	: AbandonGoodsApplicationVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.server.web.upload.AttachementEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.AbandonGoodsApplicationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonGoodsApplicationDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ResultDto;

/**
 * @ClassName: AbandonGoodsApplicationDto
 * @Description: 弃货dto
 * @author
 * @date 2012-10-25 下午1:37:50
 */
public class AbandonGoodsApplicationVo implements Serializable {

	// 序列化版本号
	private static final long serialVersionUID = 1L;

	// 申请数据--页面显示查看
	private AbandonGoodsApplicationDto abandonGoodsApplicationDto = new AbandonGoodsApplicationDto();

	// 保存的数据
	private AbandonGoodsApplicationEntity abandonGoodsApplicationEntity = new AbandonGoodsApplicationEntity();
	
	//上传的附近
	private List<AttachementEntity> attachementFiles = new ArrayList<AttachementEntity>();

	// 选择需要导入的waybill no 序列
	private String waybillNos;

	// id
	private String id;

	// 启动工作流接口调用结果
	private ResultDto resultDto;
	/**
	 * 是否通过在线提醒加载的
	 */
	private int isLoadByMsg=0;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to see
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the resultDto
	 */
	public ResultDto getResultDto() {
		return resultDto;
	}

	/**
	 * @param resultDto the resultDto to see
	 */
	public void setResultDto(ResultDto resultDto) {
		this.resultDto = resultDto;
	}

	/**
	 * @return the abandonGoodsApplicationDto
	 */
	public AbandonGoodsApplicationDto getAbandonGoodsApplicationDto() {
		return abandonGoodsApplicationDto;
	}

	/**
	 * @param abandonGoodsApplicationDto the abandonGoodsApplicationDto to see
	 */
	public void setAbandonGoodsApplicationDto(AbandonGoodsApplicationDto abandonGoodsApplicationDto) {
		this.abandonGoodsApplicationDto = abandonGoodsApplicationDto;
	}

	/**
	 * @return the abandonGoodsApplicationEntity
	 */
	public AbandonGoodsApplicationEntity getAbandonGoodsApplicationEntity() {
		return abandonGoodsApplicationEntity;
	}

	/**
	 * @param abandonGoodsApplicationEntity the abandonGoodsApplicationEntity to
	 *            see
	 */
	public void setAbandonGoodsApplicationEntity(AbandonGoodsApplicationEntity abandonGoodsApplicationEntity) {
		this.abandonGoodsApplicationEntity = abandonGoodsApplicationEntity;
	}

	/**
	 * @return the waybillNos
	 */
	public String getWaybillNos() {
		return waybillNos;
	}

	/**
	 * @param waybillNos the waybillNos to see
	 */
	public void setWaybillNos(String waybillNos) {
		this.waybillNos = waybillNos;
	}

	/**
	 * @return attachementFiles : return the property attachementFiles.
	 */
	public List<AttachementEntity> getAttachementFiles() {
		return attachementFiles;
	}

	/**
	 * @param attachementFiles : set the property attachementFiles.
	 */
	public void setAttachementFiles(List<AttachementEntity> attachementFiles) {
		this.attachementFiles = attachementFiles;
	}

	public int getIsLoadByMsg() {
		return isLoadByMsg;
	}

	public void setIsLoadByMsg(int isLoadByMsg) {
		this.isLoadByMsg = isLoadByMsg;
	}


}