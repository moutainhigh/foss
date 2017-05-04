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
 *  FILE PATH          :/AirUnshippedSerialNoEntity.java
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

/**
 * 拉货流水号实体
 * @author foss-liuxue(for IBM)
 * @date 2013-3-13 上午11:35:55
 */
public class AirUnshippedSerialNoEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7106743686443326128L;

	/**
	 * id
	 */
	private String id;

    /**
     * 运单号
     */
    private String waybillNo;

    /**
     * 流水号
     */
    private String serialNo;

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
     * 获取 运单号.
     *
     * @return the 运单号
     */
    public String getWaybillNo() {
        return waybillNo;
    }

    /**
     * 设置 运单号.
     *
     * @param waybillNo the new 运单号
     */
    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    /**
     * 获取 流水号.
     *
     * @return the 流水号
     */
    public String getSerialNo() {
        return serialNo;
    }

    /**
     * 设置 流水号.
     *
     * @param serialNo the new 流水号
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
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