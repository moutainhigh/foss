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
 *  FILE PATH          :/SaleBookingSpaceEntity.java
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


import java.math.BigDecimal;
import java.util.Date;
import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;
/**
 * 营业部订舱entity
 * @author 038300-foss-pengzhen
 * @date 2012-12-17 下午7:02:54
 */
public class SaleBookingSpaceEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6643920099897074750L;
	/**ID*/
	private String id;
	/**订舱编号*/
    private String bookingNo; 
    /**受理部门名称*/
    private String acceptOrgName;
    /**受理 部门编码*/
    private String acceptOrgCode;
    /**走货日期*/
    private Date takeOffDate;
    /**始发站名称*/
    private String deptRegionName;
    /**始发站编码*/
    private String deptRegionCode;
    /**目的站编码*/
    private String arrvRegionCode;
    /**目的站名称*/
    private String arrvRegionName;
    /**航班类型*/
    private String flightType;
    /**航班号*/
    private String flightNo;
    /**货物名称*/
    private String goodsName;
    /**货物体积*/
    private BigDecimal volume;
    /**货物重量*/
    private BigDecimal weight;
    /**备注*/
    private String notes;
    /**申请部门编码*/
    private String applyOrgCode;
    /**申请部门名称*/
    private String applyOrgName;
    /**订舱人编码*/
    private String createUserCode;
    /**订舱人名称*/
    private String createUserName;
    /**订舱日期  或 申请时间*/
    private Date createTime;
    /**修改人编码*/
    private String modifyUserCode;
    /**修改人名称*/
    private String modifyUserName;
    /**修改日期*/
    private Date modifyTime;
    /**受理人编码*/
    private String acceptUserCode;
    /**受理人名称*/
    private String acceptUserName;
    /**受理日期*/
    private Date acceptTime;
    /**受理状态*/
    private String acceptState;
    /**受理备注*/
    private String acceptNotes;
    /**
     * 运单号
     */
    private String waybillNo;

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
     * TODO（方法详细描述说明、方法参数的具体涵义）
     * @author 038300-foss-pengzhen
     * @date 2012-12-25 下午6:42:57
     * @see com.deppon.foss.framework.entity.BaseEntity#getId()
     */
    public String getId() {
        return id;
    }

    /** 
     * TODO（方法详细描述说明、方法参数的具体涵义）
     * @author 038300-foss-pengzhen
     * @date 2012-12-25 下午6:42:57
     * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取 订舱编号.
     *
     * @return the 订舱编号
     */
    public String getBookingNo() {
        return bookingNo;
    }

    /**
     * 设置 订舱编号.
     *
     * @param bookingNo the new 订舱编号
     */
    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    /**
     * 获取 受理部门名称.
     *
     * @return the 受理部门名称
     */
    public String getAcceptOrgName() {
        return acceptOrgName;
    }

    /**
     * 设置 受理部门名称.
     *
     * @param acceptOrgName the new 受理部门名称
     */
    public void setAcceptOrgName(String acceptOrgName) {
        this.acceptOrgName = acceptOrgName;
    }

    /**
     * 获取 受理 部门编码.
     *
     * @return the 受理 部门编码
     */
    public String getAcceptOrgCode() {
        return acceptOrgCode;
    }

    /**
     * 设置 受理 部门编码.
     *
     * @param acceptOrgCode the new 受理 部门编码
     */
    public void setAcceptOrgCode(String acceptOrgCode) {
        this.acceptOrgCode = acceptOrgCode;
    }
    
    /**
     * 获取 走货日期.
     *
     * @return the 走货日期
     */
    @DateFormat(formate="yyyy-MM-dd")
    public Date getTakeOffDate() {
        return takeOffDate;
    }
    
    /**
     * 设置 走货日期.
     *
     * @param takeOffDate the new 走货日期
     */
    @DateFormat(formate="yyyy-MM-dd")
    public void setTakeOffDate(Date takeOffDate) {
        this.takeOffDate = takeOffDate;
    }

    /**
     * 获取 始发站名称.
     *
     * @return the 始发站名称
     */
    public String getDeptRegionName() {
        return deptRegionName;
    }

    /**
     * 设置 始发站名称.
     *
     * @param deptRegionName the new 始发站名称
     */
    public void setDeptRegionName(String deptRegionName) {
        this.deptRegionName = deptRegionName;
    }

    /**
     * 获取 始发站编码.
     *
     * @return the 始发站编码
     */
    public String getDeptRegionCode() {
        return deptRegionCode;
    }

    /**
     * 设置 始发站编码.
     *
     * @param deptRegionCode the new 始发站编码
     */
    public void setDeptRegionCode(String deptRegionCode) {
        this.deptRegionCode = deptRegionCode;
    }

    /**
     * 获取 目的站编码.
     *
     * @return the 目的站编码
     */
    public String getArrvRegionCode() {
        return arrvRegionCode;
    }

    /**
     * 设置 目的站编码.
     *
     * @param arrvRegionCode the new 目的站编码
     */
    public void setArrvRegionCode(String arrvRegionCode) {
        this.arrvRegionCode = arrvRegionCode;
    }

    /**
     * 获取 目的站名称.
     *
     * @return the 目的站名称
     */
    public String getArrvRegionName() {
        return arrvRegionName;
    }

    /**
     * 设置 目的站名称.
     *
     * @param arrvRegionName the new 目的站名称
     */
    public void setArrvRegionName(String arrvRegionName) {
        this.arrvRegionName = arrvRegionName;
    }

    /**
     * 获取 航班类型.
     *
     * @return the 航班类型
     */
    public String getFlightType() {
        return flightType;
    }

    /**
     * 设置 航班类型.
     *
     * @param flightType the new 航班类型
     */
    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }

    /**
     * 获取 航班号.
     *
     * @return the 航班号
     */
    public String getFlightNo() {
        return flightNo;
    }

    /**
     * 设置 航班号.
     *
     * @param flightNo the new 航班号
     */
    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    /**
     * 获取 货物名称.
     *
     * @return the 货物名称
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * 设置 货物名称.
     *
     * @param goodsName the new 货物名称
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * 获取 货物体积.
     *
     * @return the 货物体积
     */
    public BigDecimal getVolume() {
        return volume;
    }

    /**
     * 设置 货物体积.
     *
     * @param volume the new 货物体积
     */
    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    /**
     * 获取 货物重量.
     *
     * @return the 货物重量
     */
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * 设置 货物重量.
     *
     * @param weight the new 货物重量
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * 获取 备注.
     *
     * @return the 备注
     */
    public String getNotes() {
        return notes;
    }

    /**
     * 设置 备注.
     *
     * @param notes the new 备注
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * 获取 申请部门编码.
     *
     * @return the 申请部门编码
     */
    public String getApplyOrgCode() {
        return applyOrgCode;
    }

    /**
     * 设置 申请部门编码.
     *
     * @param applyOrgCode the new 申请部门编码
     */
    public void setApplyOrgCode(String applyOrgCode) {
        this.applyOrgCode = applyOrgCode;
    }

    /**
     * 获取 申请部门名称.
     *
     * @return the 申请部门名称
     */
    public String getApplyOrgName() {
        return applyOrgName;
    }

    /**
     * 设置 申请部门名称.
     *
     * @param applyOrgName the new 申请部门名称
     */
    public void setApplyOrgName(String applyOrgName) {
        this.applyOrgName = applyOrgName;
    }

    /**
     * 获取 订舱人编码.
     *
     * @return the 订舱人编码
     */
    public String getCreateUserCode() {
        return createUserCode;
    }

    /**
     * 设置 订舱人编码.
     *
     * @param createUserCode the new 订舱人编码
     */
    public void setCreateUserCode(String createUserCode) {
        this.createUserCode = createUserCode;
    }

    /**
     * 获取 订舱人名称.
     *
     * @return the 订舱人名称
     */
    public String getCreateUserName() {
        return createUserName;
    }

    /**
     * 设置 订舱人名称.
     *
     * @param createUserName the new 订舱人名称
     */
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
    
    /**
     * 获取 订舱日期  或 申请时间.
     *
     * @return the 订舱日期  或 申请时间
     */
    @DateFormat(formate="yyyy-MM-dd hh24:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }
    
    /**
     * 设置 订舱日期  或 申请时间.
     *
     * @param createTime the new 订舱日期  或 申请时间
     */
    @DateFormat(formate="yyyy-MM-dd hh24:mm:ss")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取 修改人编码.
     *
     * @return the 修改人编码
     */
    public String getModifyUserCode() {
        return modifyUserCode;
    }

    /**
     * 设置 修改人编码.
     *
     * @param modifyUserCode the new 修改人编码
     */
    public void setModifyUserCode(String modifyUserCode) {
        this.modifyUserCode = modifyUserCode;
    }

    /**
     * 获取 修改人名称.
     *
     * @return the 修改人名称
     */
    public String getModifyUserName() {
        return modifyUserName;
    }

    /**
     * 设置 修改人名称.
     *
     * @param modifyUserName the new 修改人名称
     */
    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName;
    }

    /**
     * 获取 修改日期.
     *
     * @return the 修改日期
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置 修改日期.
     *
     * @param modifyTime the new 修改日期
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 获取 受理人编码.
     *
     * @return the 受理人编码
     */
    public String getAcceptUserCode() {
        return acceptUserCode;
    }

    /**
     * 设置 受理人编码.
     *
     * @param acceptUserCode the new 受理人编码
     */
    public void setAcceptUserCode(String acceptUserCode) {
        this.acceptUserCode = acceptUserCode;
    }

    /**
     * 获取 受理人名称.
     *
     * @return the 受理人名称
     */
    public String getAcceptUserName() {
        return acceptUserName;
    }

    /**
     * 设置 受理人名称.
     *
     * @param acceptUserName the new 受理人名称
     */
    public void setAcceptUserName(String acceptUserName) {
        this.acceptUserName = acceptUserName;
    }
    
    /**
     * 获取 受理日期.
     *
     * @return the 受理日期
     */
    @DateFormat(formate="yyyy-MM-dd hh24:mm:ss")
    public Date getAcceptTime() {
        return acceptTime;
    }
    
    /**
     * 设置 受理日期.
     *
     * @param acceptTime the new 受理日期
     */
    @DateFormat(formate="yyyy-MM-dd hh24:mm:ss")
    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    /**
     * 获取 受理状态.
     *
     * @return the 受理状态
     */
    public String getAcceptState() {
        return acceptState;
    }

    /**
     * 设置 受理状态.
     *
     * @param acceptState the new 受理状态
     */
    public void setAcceptState(String acceptState) {
        this.acceptState = acceptState;
    }

    /**
     * 获取 受理备注.
     *
     * @return the 受理备注
     */
    public String getAcceptNotes() {
        return acceptNotes;
    }

    /**
     * 设置 受理备注.
     *
     * @param acceptNotes the new 受理备注
     */
    public void setAcceptNotes(String acceptNotes) {
        this.acceptNotes = acceptNotes;
    }
}