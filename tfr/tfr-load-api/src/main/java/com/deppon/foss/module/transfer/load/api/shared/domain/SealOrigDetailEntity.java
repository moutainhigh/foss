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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/SealOrigDetailEntity.java
 *  
 *  FILE NAME          :SealOrigDetailEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 出发封签明细
 * @author ibm-zhangyixin
 * @date 2012-12-25 下午6:29:26
 */
public class SealOrigDetailEntity extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -4566634092216766704L;
	
	/**ID**/
	private String id;

	/**封签号码**/
    private String sealNo;		

    /**SealEntity外键**/
    private String sealId;		

    /**封签类型,back	后门, side 侧门**/
    private String sealType;
    
    /**封签录入方式SCANED扫描, BY_HAND手输**/
    private String bindType;

    /**操作时间**/
    private Date operateTime;	
    
    /**虚拟字段,是否已经校验过, 封签校验时用到.**/
    private Boolean inspected = false;	

    /**虚拟字段,是否有差异, 封签校验时用到. true为有差异**/
    private Boolean isdiff = true;	
    
    /** 
     * ID
     * @author ibm-zhangyixin
     * @date 2012-12-25 下午6:30:19
     * @see com.deppon.foss.framework.entity.BaseEntity#getId()
     */
    public String getId() {
        return id;
    }

    /** 
     * ID
     * @author ibm-zhangyixin
     * @date 2012-12-25 下午6:30:19
     * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取 封签号码*.
     *
     * @return the 封签号码*
     */
    public String getSealNo() {
        return sealNo;
    }

    /**
     * 设置 封签号码*.
     *
     * @param sealNo the new 封签号码*
     */
    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
    }

    /**
     * 获取 sealEntity外键*.
     *
     * @return the sealEntity外键*
     */
    public String getSealId() {
        return sealId;
    }

    /**
     * 设置 sealEntity外键*.
     *
     * @param sealId the new sealEntity外键*
     */
    public void setSealId(String sealId) {
        this.sealId = sealId;
    }

    /**
     * 获取 操作时间*.
     *
     * @return the 操作时间*
     */
    public Date getOperateTime() {
        return operateTime;
    }

    /**
     * 设置 操作时间*.
     *
     * @param operateTime the new 操作时间*
     */
    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

	/**
	 * 获取 封签类型,back	后门, side 侧门*.
	 *
	 * @return the 封签类型,back	后门, side 侧门*
	 */
	public String getSealType() {
		return sealType;
	}

	/**
	 * 设置 封签类型,back	后门, side 侧门*.
	 *
	 * @param sealType the new 封签类型,back	后门, side 侧门*
	 */
	public void setSealType(String sealType) {
		this.sealType = sealType;
	}
	
	/**
	 * 获取 虚拟字段,是否已经校验过, 封签校验时用到.
	 *
	 * @return the 虚拟字段,是否已经校验过, 封签校验时用到
	 */
	public Boolean getInspected() {
		return inspected;
	}
	
	/**
	 * 设置 虚拟字段,是否已经校验过, 封签校验时用到.
	 *
	 * @param inspected the new 虚拟字段,是否已经校验过, 封签校验时用到
	 */
	public void setInspected(Boolean inspected) {
		this.inspected = inspected;
	}
	
	/**
	 * 获取 虚拟字段,是否有差异, 封签校验时用到.
	 *
	 * @return the 虚拟字段,是否有差异, 封签校验时用到
	 */
	public Boolean getIsdiff() {
		return isdiff;
	}
	
	/**
	 * 设置 虚拟字段,是否有差异, 封签校验时用到.
	 *
	 * @param isdiff the new 虚拟字段,是否有差异, 封签校验时用到
	 */
	public void setIsdiff(Boolean isdiff) {
		this.isdiff = isdiff;
	}

	public String getBindType() {
		return bindType;
	}

	public void setBindType(String bindType) {
		this.bindType = bindType;
	}
}