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
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/AirUnshippedNoticeEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 */
public class AirUnshippedNoticeEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7438105903749204852L;

	/**
	 * id
	 */
	private String id;

	/**
	 * 通知时间
	 */
    private Date noticeTime;

    /**
	 * 通知人code
	 */
    private String createdUserCode;

    /**
	 * 通知人名称
	 */
    private String createdUserName;

    /**
	 * 通知信息
	 */
    private String message;

    /**
	 * 通知部门编码
	 */
    private String createOrgCode;

    /**
	 * 通知部门名称
	 */
    private String createOrgName;

    /**
	 * 拉货ID
	 */
    private String unshippedGoodsId;

    /**
     * 获取 id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置 id.
     *
     * @param id the new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取 通知时间.
     *
     * @return the 通知时间
     */
    public Date getNoticeTime() {
        return noticeTime;
    }

    /**
     * 设置 通知时间.
     *
     * @param noticeTime the new 通知时间
     */
    public void setNoticeTime(Date noticeTime) {
        this.noticeTime = noticeTime;
    }

    /**
     * 获取 通知人code.
     *
     * @return the 通知人code
     */
    public String getCreatedUserCode() {
        return createdUserCode;
    }

    /**
     * 设置 通知人code.
     *
     * @param createdUserCode the new 通知人code
     */
    public void setCreatedUserCode(String createdUserCode) {
        this.createdUserCode = createdUserCode;
    }

    /**
     * 获取 通知人名称.
     *
     * @return the 通知人名称
     */
    public String getCreatedUserName() {
        return createdUserName;
    }

    /**
     * 设置 通知人名称.
     *
     * @param createdUserName the new 通知人名称
     */
    public void setCreatedUserName(String createdUserName) {
        this.createdUserName = createdUserName;
    }

    /**
     * 获取 通知信息.
     *
     * @return the 通知信息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置 通知信息.
     *
     * @param message the new 通知信息
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取 通知部门编码.
     *
     * @return the 通知部门编码
     */
    public String getCreateOrgCode() {
        return createOrgCode;
    }

    /**
     * 设置 通知部门编码.
     *
     * @param createOrgCode the new 通知部门编码
     */
    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode;
    }

    /**
     * 获取 通知部门名称.
     *
     * @return the 通知部门名称
     */
    public String getCreateOrgName() {
        return createOrgName;
    }

    /**
     * 设置 通知部门名称.
     *
     * @param createOrgName the new 通知部门名称
     */
    public void setCreateOrgName(String createOrgName) {
        this.createOrgName = createOrgName;
    }

    /**
     * 获取 拉货ID.
     *
     * @return the 拉货ID
     */
    public String getUnshippedGoodsId() {
        return unshippedGoodsId;
    }

    /**
     * 设置 拉货ID.
     *
     * @param unshippedGoodsId the new 拉货ID
     */
    public void setUnshippedGoodsId(String unshippedGoodsId) {
        this.unshippedGoodsId = unshippedGoodsId;
    }
	
}