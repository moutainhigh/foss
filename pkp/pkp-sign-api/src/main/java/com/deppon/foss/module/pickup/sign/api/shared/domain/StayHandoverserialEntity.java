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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/domain/StayHandoverserialEntity.java
 * 
 * FILE NAME        	: StayHandoverserialEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 *交接流水号信息
 * @author foss-meiying
 * @date 2013-1-18 下午4:10:50
 * @since
 * @version
 */
public class StayHandoverserialEntity extends BaseEntity{
	private static final long serialVersionUID = -3561140565405887562L;
	/**
     * 交接明细id
     */
    private String tSrvStayHandoverdetailId;
    /**
     * 流水号 
     */
    private String serailno;

    /**
     * get方法
     * @author foss-meiying
     * @date 2013-3-14 上午9:26:17
     * @return
     * @see
     */
    public String gettSrvStayHandoverdetailId() {
        return tSrvStayHandoverdetailId;
    }
    /**
     * set方法
     * @author foss-meiying
     * @date 2013-3-14 上午9:26:31
     * @param tSrvStayHandoverdetailId
     * @see
     */
    public void settSrvStayHandoverdetailId(String tSrvStayHandoverdetailId) {
        this.tSrvStayHandoverdetailId = tSrvStayHandoverdetailId;
    }

    /**
     * Gets the 流水号.
     *
     * @return the 流水号
     */
    public String getSerailno() {
        return serailno;
    }

    /**
     * Sets the 流水号.
     *
     * @param serailno the new 流水号
     */
    public void setSerailno(String serailno) {
        this.serailno = serailno;
    }
}