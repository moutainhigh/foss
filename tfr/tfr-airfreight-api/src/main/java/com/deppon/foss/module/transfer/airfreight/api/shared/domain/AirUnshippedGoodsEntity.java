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
 *  FILE PATH          :/AirUnshippedGoodsEntity.java
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
import java.math.BigDecimal;
import java.util.Date;
/**
 * 拉货实体
 * @author 038300-foss-pengzhen
 * @date 2012-12-25 下午6:47:52
 */
public class AirUnshippedGoodsEntity implements Serializable {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -7773520069478775141L;

	/**
	 * 
	 */
	private String id;  
	/**
	 * 拉货类型
	 */
    private String unshippedType;  
    /**
     * 正单号/代单号
     */
    private String billNo;  
    /**
     * 目的站名称
     */
    private String arrvRegionName; 
    /**
     * 目的站编码
     */
    private String arrvRegionCode;  
    /**
     * 配载类型
     */
    private String airAssembleType; 
    /**
     * 收货人名称
     */
    private String receiverName; 
    /**
     * 收货人编码
     */
    private String receiverCode; 
    /**
     * 航班号
     */
    private String flightNo; 
    /**
     * 总重量
     */
    private BigDecimal weightTotal;
    /**
     * 总件数
     */
    private Integer goodsQtyTotal; 
    /**
     * 改配航班
     */
    private String reassignFlightNo;
    /**
     * 拉货重量
     */
    private BigDecimal unshippedWeight; 
    /**
     * 拉货件数
     */
    private Integer unshippedGoodsQty; 
    /**
     * 拉货时间
     */
    private Date unshippedTime; 
    /**
     * 拉货备注
     */
    private String notes;  
    /**
     * 创建时间
     */
    private Date createTime;  
    /**
     * 修改时间
     */
    private Date modifyTime; 
    /**
     * 创建人编码
     */
    private String createUserCode;
    /**
     * 创建人名称
     */
    private String createUserName; 
    /**
     * 修改人编码
     */
    private String updateUserCode;
    /**
     * 修改人名称
     */
    private String updateUserName;
    /**
     * 配载部门编码
     */
    private String airAssembleOrgCode; 
    /**
     * 配载部门名称
     */
    private String airAssembleOrgName;
    /**
     * 代理编码
     */
    private String airAgencyCode;
    /**
     * 代理名称
     */
    private String airAgencyName;
    //拓展字段
    /**
     * 航空公司
     */
    private String airLine;
    /**
     * 通知人
     */
    private String createdUserName;
    /**
     * 通知时间
     */
    private Date noticeTime;

    /**
     * @return 
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取 拉货类型.
     *
     * @return the 拉货类型
     */
    public String getUnshippedType() {
        return unshippedType;
    }

    /**
     * 设置 拉货类型.
     *
     * @param unshippedType the new 拉货类型
     */
    public void setUnshippedType(String unshippedType) {
        this.unshippedType = unshippedType;
    }

    /**
     * 获取 正单号/代单号.
     *
     * @return the 正单号/代单号
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 设置 正单号/代单号.
     *
     * @param billNo the new 正单号/代单号
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
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
     * 获取 配载类型.
     *
     * @return the 配载类型
     */
    public String getAirAssembleType() {
        return airAssembleType;
    }

    /**
     * 设置 配载类型.
     *
     * @param airAssembleType the new 配载类型
     */
    public void setAirAssembleType(String airAssembleType) {
        this.airAssembleType = airAssembleType;
    }

    /**
     * 获取 收货人名称.
     *
     * @return the 收货人名称
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
     * 设置 收货人名称.
     *
     * @param receiverName the new 收货人名称
     */
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    /**
     * 获取 收货人编码.
     *
     * @return the 收货人编码
     */
    public String getReceiverCode() {
        return receiverCode;
    }

    /**
     * 设置 收货人编码.
     *
     * @param receiverCode the new 收货人编码
     */
    public void setReceiverCode(String receiverCode) {
        this.receiverCode = receiverCode;
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
     * 获取 总重量.
     *
     * @return the 总重量
     */
    public BigDecimal getWeightTotal() {
        return weightTotal;
    }

    /**
     * 设置 总重量.
     *
     * @param weightTotal the new 总重量
     */
    public void setWeightTotal(BigDecimal weightTotal) {
        this.weightTotal = weightTotal;
    }

    /**
     * 获取 总件数.
     *
     * @return the 总件数
     */
    public Integer getGoodsQtyTotal() {
        return goodsQtyTotal;
    }

    /**
     * 设置 总件数.
     *
     * @param goodsQtyTotal the new 总件数
     */
    public void setGoodsQtyTotal(Integer goodsQtyTotal) {
        this.goodsQtyTotal = goodsQtyTotal;
    }

    /**
     * 获取 改配航班.
     *
     * @return the 改配航班
     */
    public String getReassignFlightNo() {
        return reassignFlightNo;
    }

    /**
     * 设置 改配航班.
     *
     * @param reassignFlightNo the new 改配航班
     */
    public void setReassignFlightNo(String reassignFlightNo) {
        this.reassignFlightNo = reassignFlightNo;
    }

    /**
     * 获取 拉货重量.
     *
     * @return the 拉货重量
     */
    public BigDecimal getUnshippedWeight() {
        return unshippedWeight;
    }

    /**
     * 设置 拉货重量.
     *
     * @param unshippedWeight the new 拉货重量
     */
    public void setUnshippedWeight(BigDecimal unshippedWeight) {
        this.unshippedWeight = unshippedWeight;
    }

    /**
     * 获取 拉货件数.
     *
     * @return the 拉货件数
     */
    public Integer getUnshippedGoodsQty() {
        return unshippedGoodsQty;
    }

    /**
     * 设置 拉货件数.
     *
     * @param unshippedGoodsQty the new 拉货件数
     */
    public void setUnshippedGoodsQty(Integer unshippedGoodsQty) {
        this.unshippedGoodsQty = unshippedGoodsQty;
    }

    /**
     * 获取 拉货时间.
     *
     * @return the 拉货时间
     */
    public Date getUnshippedTime() {
        return unshippedTime;
    }

    /**
     * 设置 拉货时间.
     *
     * @param unshippedTime the new 拉货时间
     */
    public void setUnshippedTime(Date unshippedTime) {
        this.unshippedTime = unshippedTime;
    }

    /**
     * 获取 拉货备注.
     *
     * @return the 拉货备注
     */
    public String getNotes() {
        return notes;
    }

    /**
     * 设置 拉货备注.
     *
     * @param notes the new 拉货备注
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * 获取 创建时间.
     *
     * @return the 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置 创建时间.
     *
     * @param createTime the new 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取 修改时间.
     *
     * @return the 修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置 修改时间.
     *
     * @param modifyTime the new 修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 获取 创建人编码.
     *
     * @return the 创建人编码
     */
    public String getCreateUserCode() {
        return createUserCode;
    }

    /**
     * 设置 创建人编码.
     *
     * @param createUserCode the new 创建人编码
     */
    public void setCreateUserCode(String createUserCode) {
        this.createUserCode = createUserCode;
    }

    /**
     * 获取 创建人名称.
     *
     * @return the 创建人名称
     */
    public String getCreateUserName() {
        return createUserName;
    }

    /**
     * 设置 创建人名称.
     *
     * @param createUserName the new 创建人名称
     */
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    /**
     * 获取 修改人编码.
     *
     * @return the 修改人编码
     */
    public String getUpdateUserCode() {
        return updateUserCode;
    }

    /**
     * 设置 修改人编码.
     *
     * @param updateUserCode the new 修改人编码
     */
    public void setUpdateUserCode(String updateUserCode) {
        this.updateUserCode = updateUserCode;
    }

    /**
     * 获取 修改人名称.
     *
     * @return the 修改人名称
     */
    public String getUpdateUserName() {
        return updateUserName;
    }

    /**
     * 设置 修改人名称.
     *
     * @param updateUserName the new 修改人名称
     */
    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    /**
     * 获取 配载部门编码.
     *
     * @return the 配载部门编码
     */
    public String getAirAssembleOrgCode() {
        return airAssembleOrgCode;
    }

    /**
     * 设置 配载部门编码.
     *
     * @param airAssembleOrgCode the new 配载部门编码
     */
    public void setAirAssembleOrgCode(String airAssembleOrgCode) {
        this.airAssembleOrgCode = airAssembleOrgCode;
    }

    /**
     * 获取 配载部门名称.
     *
     * @return the 配载部门名称
     */
    public String getAirAssembleOrgName() {
        return airAssembleOrgName;
    }

    /**
     * 设置 配载部门名称.
     *
     * @param airAssembleOrgName the new 配载部门名称
     */
    public void setAirAssembleOrgName(String airAssembleOrgName) {
        this.airAssembleOrgName = airAssembleOrgName;
    }

    /**
     * 获取 代理编码.
     *
     * @return the 代理编码
     */
    public String getAirAgencyCode() {
        return airAgencyCode;
    }

    /**
     * 设置 代理编码.
     *
     * @param airAgencyCode the new 代理编码
     */
    public void setAirAgencyCode(String airAgencyCode) {
        this.airAgencyCode = airAgencyCode;
    }

    /**
     * 获取 代理名称.
     *
     * @return the 代理名称
     */
    public String getAirAgencyName() {
        return airAgencyName;
    }

    /**
     * 设置 代理名称.
     *
     * @param airAgencyName the new 代理名称
     */
    public void setAirAgencyName(String airAgencyName) {
        this.airAgencyName = airAgencyName;
    }

	/**
	 * 获取 航空公司.
	 *
	 * @return the 航空公司
	 */
	public String getAirLine() {
		return airLine;
	}

	/**
	 * 设置 航空公司.
	 *
	 * @param airLine the new 航空公司
	 */
	public void setAirLine(String airLine) {
		this.airLine = airLine;
	}

	/**
	 * 获取 通知人.
	 *
	 * @return the 通知人
	 */
	public String getCreatedUserName() {
		return createdUserName;
	}

	/**
	 * 设置 通知人.
	 *
	 * @param createdUserName the new 通知人
	 */
	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
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
    
}