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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/vo/SealOrigDetailVo.java
 *  
 *  FILE NAME          :SealOrigDetailVo.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.vo;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealOrigDetailEntity;

/**
 * 绑定封签明细vo
 * @author ibm-zhangyixin
 * @date 2012-12-25 下午6:49:53
 */
public class SealOrigDetailVo extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 8627339379205994347L;
	
	/**后门id**/
	private String backId;
	/**后门封签号码**/
    private String backSealNo;				//后门封签号码
    /**后门SealEntity外键**/
    private String backSealId;				//后门SealEntity外键
    /**后门封签类型,BACK	后门, SIDE 侧门**/
    private String backSealType;			//后门封签类型,BACK	后门, SIDE 侧门
    /**后门操作时间**/
    private Date backOperateTime;			//后门操作时间
    /**后门虚拟字段,是否已经校验过, 封签校验时用到.**/
    private Boolean backInspected = false;	//后门虚拟字段,是否已经校验过, 封签校验时用到.
    /**后门虚拟字段,是否有差异, 封签校验时用到. true为有差异**/
    private Boolean backIsdiff = true;		//后门虚拟字段,是否有差异, 封签校验时用到. true为有差异
    
    /**侧门id**/
    private String sideId;
    /**侧门封签号码**/
    private String sideSealNo;				//侧门封签号码
    /**侧门SealEntity外键**/
    private String sideSealId;				//侧门SealEntity外键
    /**侧门封签类型,BACK	后门, SIDE 侧门**/
    private String sideSealType;			//侧门封签类型,BACK	后门, SIDE 侧门
    /**侧门操作时间**/
    private Date sideOperateTime;			//侧门操作时间
    /**侧门虚拟字段,是否已经校验过, 封签校验时用到.**/
    private Boolean sideInspected = false;	//侧门虚拟字段,是否已经校验过, 封签校验时用到.
    /**侧门虚拟字段,是否有差异, 封签校验时用到. true为有差异**/
    private Boolean sideIsdiff = true;		//侧门虚拟字段,是否有差异, 封签校验时用到. true为有差异
    
    /**出发封签明细-后门**/
    private SealOrigDetailEntity backSeal;
    /**出发封签明细-侧门**/
    private SealOrigDetailEntity sideSeal;
    
	/**
	 * 获取 出发封签明细-后门*.
	 *
	 * @return the 出发封签明细-后门*
	 */
	public SealOrigDetailEntity getBackSeal() {
		return backSeal;
	}

	/**
	 * 设置 出发封签明细-后门*.
	 *
	 * @param backSeal the new 出发封签明细-后门*
	 */
	public void setBackSeal(SealOrigDetailEntity backSeal) {
		this.backId = backSeal.getId();
		this.backSealId = backSeal.getSealId();
		this.backSealNo = backSeal.getSealNo();
		this.backSealType = backSeal.getSealType();
		this.backOperateTime = backSeal.getOperateTime();
		this.backInspected = backSeal.getInspected();
		this.backIsdiff = backSeal.getIsdiff();
		this.backSeal = backSeal;
	}
	
	/**
	 * 获取 出发封签明细-侧门*.
	 *
	 * @return the 出发封签明细-侧门*
	 */
	public SealOrigDetailEntity getSideSeal() {
		return sideSeal;
	}
	
	/**
	 * 设置 出发封签明细-侧门*.
	 *
	 * @param sideSeal the new 出发封签明细-侧门*
	 */
	public void setSideSeal(SealOrigDetailEntity sideSeal) {
		this.sideId = sideSeal.getId();
		this.sideSealId = sideSeal.getSealId();
		this.sideSealNo = sideSeal.getSealNo();
		this.sideSealType = sideSeal.getSealType();
		this.sideOperateTime = sideSeal.getOperateTime();
		this.sideInspected = sideSeal.getInspected();
		this.sideIsdiff = sideSeal.getIsdiff();
		this.sideSeal = sideSeal;
	}
	
	/**
	 * 获取 后门id*.
	 *
	 * @return the 后门id*
	 */
	public String getBackId() {
		return backId;
	}
	
	/**
	 * 设置 后门id*.
	 *
	 * @param backId the new 后门id*
	 */
	public void setBackId(String backId) {
		this.backId = backId;
	}
	
	/**
	 * 获取 后门封签号码*.
	 *
	 * @return the 后门封签号码*
	 */
	public String getBackSealNo() {
		return backSealNo;
	}
	
	/**
	 * 设置 后门封签号码*.
	 *
	 * @param backSealNo the new 后门封签号码*
	 */
	public void setBackSealNo(String backSealNo) {
		this.backSealNo = backSealNo;
	}
	
	/**
	 * 获取 后门SealEntity外键*.
	 *
	 * @return the 后门SealEntity外键*
	 */
	public String getBackSealId() {
		return backSealId;
	}
	
	/**
	 * 设置 后门SealEntity外键*.
	 *
	 * @param backSealId the new 后门SealEntity外键*
	 */
	public void setBackSealId(String backSealId) {
		this.backSealId = backSealId;
	}
	
	/**
	 * 获取 后门封签类型,BACK	后门, SIDE 侧门*.
	 *
	 * @return the 后门封签类型,BACK	后门, SIDE 侧门*
	 */
	public String getBackSealType() {
		return backSealType;
	}
	
	/**
	 * 设置 后门封签类型,BACK	后门, SIDE 侧门*.
	 *
	 * @param backSealType the new 后门封签类型,BACK	后门, SIDE 侧门*
	 */
	public void setBackSealType(String backSealType) {
		this.backSealType = backSealType;
	}
	
	/**
	 * 获取 后门操作时间*.
	 *
	 * @return the 后门操作时间*
	 */
	public Date getBackOperateTime() {
		return backOperateTime;
	}
	
	/**
	 * 设置 后门操作时间*.
	 *
	 * @param backOperateTime the new 后门操作时间*
	 */
	public void setBackOperateTime(Date backOperateTime) {
		this.backOperateTime = backOperateTime;
	}
	
	/**
	 * 获取 后门虚拟字段,是否已经校验过, 封签校验时用到.
	 *
	 * @return the 后门虚拟字段,是否已经校验过, 封签校验时用到
	 */
	public Boolean getBackInspected() {
		return backInspected;
	}
	
	/**
	 * 设置 后门虚拟字段,是否已经校验过, 封签校验时用到.
	 *
	 * @param backInspected the new 后门虚拟字段,是否已经校验过, 封签校验时用到
	 */
	public void setBackInspected(Boolean backInspected) {
		this.backInspected = backInspected;
	}
	
	/**
	 * 获取 后门虚拟字段,是否有差异, 封签校验时用到.
	 *
	 * @return the 后门虚拟字段,是否有差异, 封签校验时用到
	 */
	public Boolean getBackIsdiff() {
		return backIsdiff;
	}
	
	/**
	 * 设置 后门虚拟字段,是否有差异, 封签校验时用到.
	 *
	 * @param backIsdiff the new 后门虚拟字段,是否有差异, 封签校验时用到
	 */
	public void setBackIsdiff(Boolean backIsdiff) {
		this.backIsdiff = backIsdiff;
	}
	
	/**
	 * 获取 侧门id*.
	 *
	 * @return the 侧门id*
	 */
	public String getSideId() {
		return sideId;
	}
	
	/**
	 * 设置 侧门id*.
	 *
	 * @param sideId the new 侧门id*
	 */
	public void setSideId(String sideId) {
		this.sideId = sideId;
	}
	
	/**
	 * 获取 侧门封签号码*.
	 *
	 * @return the 侧门封签号码*
	 */
	public String getSideSealNo() {
		return sideSealNo;
	}
	
	/**
	 * 设置 侧门封签号码*.
	 *
	 * @param sideSealNo the new 侧门封签号码*
	 */
	public void setSideSealNo(String sideSealNo) {
		this.sideSealNo = sideSealNo;
	}
	
	/**
	 * 获取 侧门SealEntity外键*.
	 *
	 * @return the 侧门SealEntity外键*
	 */
	public String getSideSealId() {
		return sideSealId;
	}
	
	/**
	 * 设置 侧门SealEntity外键*.
	 *
	 * @param sideSealId the new 侧门SealEntity外键*
	 */
	public void setSideSealId(String sideSealId) {
		this.sideSealId = sideSealId;
	}
	
	/**
	 * 获取 侧门封签类型,BACK	后门, SIDE 侧门*.
	 *
	 * @return the 侧门封签类型,BACK	后门, SIDE 侧门*
	 */
	public String getSideSealType() {
		return sideSealType;
	}
	
	/**
	 * 设置 侧门封签类型,BACK	后门, SIDE 侧门*.
	 *
	 * @param sideSealType the new 侧门封签类型,BACK	后门, SIDE 侧门*
	 */
	public void setSideSealType(String sideSealType) {
		this.sideSealType = sideSealType;
	}
	
	/**
	 * 获取 侧门操作时间*.
	 *
	 * @return the 侧门操作时间*
	 */
	public Date getSideOperateTime() {
		return sideOperateTime;
	}
	
	/**
	 * 设置 侧门操作时间*.
	 *
	 * @param sideOperateTime the new 侧门操作时间*
	 */
	public void setSideOperateTime(Date sideOperateTime) {
		this.sideOperateTime = sideOperateTime;
	}
	
	/**
	 * 获取 侧门虚拟字段,是否已经校验过, 封签校验时用到.
	 *
	 * @return the 侧门虚拟字段,是否已经校验过, 封签校验时用到
	 */
	public Boolean getSideInspected() {
		return sideInspected;
	}
	
	/**
	 * 设置 侧门虚拟字段,是否已经校验过, 封签校验时用到.
	 *
	 * @param sideInspected the new 侧门虚拟字段,是否已经校验过, 封签校验时用到
	 */
	public void setSideInspected(Boolean sideInspected) {
		this.sideInspected = sideInspected;
	}
	
	/**
	 * 获取 侧门虚拟字段,是否有差异, 封签校验时用到.
	 *
	 * @return the 侧门虚拟字段,是否有差异, 封签校验时用到
	 */
	public Boolean getSideIsdiff() {
		return sideIsdiff;
	}
	
	/**
	 * 设置 侧门虚拟字段,是否有差异, 封签校验时用到.
	 *
	 * @param sideIsdiff the new 侧门虚拟字段,是否有差异, 封签校验时用到
	 */
	public void setSideIsdiff(Boolean sideIsdiff) {
		this.sideIsdiff = sideIsdiff;
	}
    
}