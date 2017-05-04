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
 * PROJECT NAME	: bse-common-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/vo/SMSSloganVo.java
 * 
 * FILE NAME        	: SMSSloganVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.common.api.shared.domain.SMSSloganEntity;
import com.deppon.foss.module.base.common.api.shared.domain.SloganAppOrgEntity;

/**
 * 短信广告语VO
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-11
 * 下午6:15:07
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-12 上午9:23:18
 * @since
 * @version
 */
public class SMSSloganVo implements Serializable {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 6882170662829537437L;
	
	/**
	 * 短信广告语实体类.
	 */
	private SMSSloganEntity smssloganEntity;
	
	/**
	 * 短信广告语信息集合.
	 */
	private List<SMSSloganEntity> smssloganEntityList;
	
	/**
	 * 短信广告语实体类.
	 */
	private SloganAppOrgEntity sloganAppOrgEntity;
	
	/**
	 * 短信广告语信息集合.
	 */
	private List<SloganAppOrgEntity> sloganAppOrgEntityList;
	
	/**
	 * 实体中对应virtualCode字段，字段间以逗号分隔[支持批量删除].
	 */
	private String codeStr;
	
	/**
	 * 返回前台的INT类型属性.
	 */
	private int returnInt;
	
	/**
	 * 获取 短信广告语实体类.
	 *
	 * @return  the smssloganEntity
	 */
	public SMSSloganEntity getSmssloganEntity() {
	    return smssloganEntity;
	}
	
	/**
	 * 设置 短信广告语实体类.
	 *
	 * @param smssloganEntity the smssloganEntity to set
	 */
	public void setSmssloganEntity(SMSSloganEntity smssloganEntity) {
	    this.smssloganEntity = smssloganEntity;
	}
	
	/**
	 * 获取 短信广告语信息集合.
	 *
	 * @return  the smssloganEntityList
	 */
	public List<SMSSloganEntity> getSmssloganEntityList() {
	    return smssloganEntityList;
	}
	
	/**
	 * 设置 短信广告语信息集合.
	 *
	 * @param smssloganEntityList the smssloganEntityList to set
	 */
	public void setSmssloganEntityList(List<SMSSloganEntity> smssloganEntityList) {
	    this.smssloganEntityList = smssloganEntityList;
	}
	
	/**
	 * 获取 短信广告语实体类.
	 *
	 * @return  the sloganAppOrgEntity
	 */
	public SloganAppOrgEntity getSloganAppOrgEntity() {
	    return sloganAppOrgEntity;
	}
	
	/**
	 * 设置 短信广告语实体类.
	 *
	 * @param sloganAppOrgEntity the sloganAppOrgEntity to set
	 */
	public void setSloganAppOrgEntity(SloganAppOrgEntity sloganAppOrgEntity) {
	    this.sloganAppOrgEntity = sloganAppOrgEntity;
	}
	
	/**
	 * 获取 短信广告语信息集合.
	 *
	 * @return  the sloganAppOrgEntityList
	 */
	public List<SloganAppOrgEntity> getSloganAppOrgEntityList() {
	    return sloganAppOrgEntityList;
	}
	
	/**
	 * 设置 短信广告语信息集合.
	 *
	 * @param sloganAppOrgEntityList the sloganAppOrgEntityList to set
	 */
	public void setSloganAppOrgEntityList(
		List<SloganAppOrgEntity> sloganAppOrgEntityList) {
	    this.sloganAppOrgEntityList = sloganAppOrgEntityList;
	}
	
	/**
	 * 获取 实体中对应virtualCode字段，字段间以逗号分隔[支持批量删除].
	 *
	 * @return  the codeStr
	 */
	public String getCodeStr() {
	    return codeStr;
	}
	
	/**
	 * 设置 实体中对应virtualCode字段，字段间以逗号分隔[支持批量删除].
	 *
	 * @param codeStr the codeStr to set
	 */
	public void setCodeStr(String codeStr) {
	    this.codeStr = codeStr;
	}
	
	/**
	 * 获取 返回前台的INT类型属性.
	 *
	 * @return  the returnInt
	 */
	public int getReturnInt() {
	    return returnInt;
	}
	
	/**
	 * 设置 返回前台的INT类型属性.
	 *
	 * @param returnInt the returnInt to set
	 */
	public void setReturnInt(int returnInt) {
	    this.returnInt = returnInt;
	}


}
