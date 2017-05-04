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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/FreightRouteEntity.java
 * 
 * FILE NAME        	: FreightRouteEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.util.define.FossConstants;


/**
 * 走货路径实体
 * @author foss-zhujunyong
 * @date Oct 18, 2012 6:50:58 PM
 * @version 1.0
 */
public class FreightRouteEntity extends BaseEntity {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 9002587728985548682L;

    // 虚拟编码
    private String virtualCode;

    // 出发部门编码
    private String orginalOrganizationCode;
    // 出发部门名称（扩展）
    private String orginalOrganizationName;

    // 到达部门编码
    private String destinationOrganizationCode;
    // 到达部门名称（扩展）
    private String destinationOrganizationName;

    // 出发部门类型(德邦部门，偏线部门，空运代理部门)
    private String orginalType;

    // 到达部门类型(德邦部门，偏线部门，空运代理部门)
    private String destinationType;
    
    // 打木架外场部门编码
    private String packingOrganizationCode;
    
    // 打木架外场部门名称（冗余）
    private String packingOrganizationName;

    // 运输性质（产品代码）
    private String transType;
    
    // 产品名称（扩展）
    private String productName;
    
    // 时效 (冗余)
    private Long aging;
    
    // 是否默认走货路径
    private String defaultRoute;

    // 是否可以打木架
    private String doPacking;
    
    // 是否有效
    private String active;
    
    // 备注
    private String notes;
    
    // 走货路径线路（扩展）
    private List<FreightRouteLineEntity> freightRouteLineList;

    // 版本号
    private Long version;
    
    // 是否生效（走货路径和走货路径线路添加完成后，校验成功则生效）
    private String valid;
    
    // 所使用的线路虚拟编码，查询用(扩展)
    private String lineVirtualCode;
    
    /**
     * 用于离线开单下载数据，限制只能找指定出发外场的走货路径用
     */
    private List<String> transferCodeList;
    
    /**
     * 
     * @author foss-zhujunyong
     * @date Mar 18, 2013 11:53:06 AM
     * @return
     * @see
     */
    public List<String> getTransferCodeList() {
        return transferCodeList;
    }

    /**
     * 
     * @author foss-zhujunyong
     * @date Mar 18, 2013 11:53:10 AM
     * @param transferCodeList
     * @see
     */
    public void setTransferCodeList(List<String> transferCodeList) {
        this.transferCodeList = transferCodeList;
    }
    
    /**
     * 
     * <p>检查是否可以打木架</p> 
     * @author foss-zhujunyong
     * @date Jun 24, 2013 10:49:14 AM
     * @return
     * @see
     */
    public boolean checkDoPacking(){
	return StringUtils.equals(doPacking, FossConstants.YES);
    }
    

    /**
     * 
     * <p>验证该走货路径是否生效</p> 
     * @author foss-zhujunyong
     * @date Jan 11, 2013 12:13:45 PM
     * @return
     * @see
     */
    public boolean checkValid(){
	return StringUtils.equals(valid, FossConstants.YES);
    }

    /**
     * 
     * <p>检查出发站和到达站是否相同</p> 
     * @author foss-zhujunyong
     * @date Jan 11, 2013 3:18:43 PM
     * @return
     * @see
     */
    public boolean checkSameSite() {
	return StringUtils.equals(orginalOrganizationCode, destinationOrganizationCode);
    }
    
    /**
     * @return  the valid
     */
    public String getValid() {
        return valid;
    }

    
    /**
     * @param valid the valid to set
     */
    public void setValid(String valid) {
        this.valid = valid;
    }

    /**
     * 
     * @author foss-zhujunyong
     * @date Mar 18, 2013 11:50:30 AM
     * @return
     * @see
     */
    public String getOrginalOrganizationName() {
        return orginalOrganizationName;
    }


    /**
     * 
     * @author foss-zhujunyong
     * @date Mar 18, 2013 11:50:35 AM
     * @param orginalOrganizationName
     * @see
     */
    public void setOrginalOrganizationName(String orginalOrganizationName) {
        this.orginalOrganizationName = orginalOrganizationName;
    }


    /**
     * 
     * @author foss-zhujunyong
     * @date Mar 18, 2013 11:50:39 AM
     * @return
     * @see
     */
    public String getDestinationOrganizationName() {
        return destinationOrganizationName;
    }


    /**
     * 
     * @author foss-zhujunyong
     * @date Mar 18, 2013 11:50:43 AM
     * @param destinationOrganizationName
     * @see
     */
    public void setDestinationOrganizationName(String destinationOrganizationName) {
        this.destinationOrganizationName = destinationOrganizationName;
    }

    /**
     * 
     * @author foss-zhujunyong
     * @date Mar 18, 2013 11:50:54 AM
     * @return
     * @see
     */
    public Long getVersion() {
        return version;
    }

    /**
     * 
     * @author foss-zhujunyong
     * @date Mar 18, 2013 11:50:58 AM
     * @param version
     * @see
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * 
     * @author foss-zhujunyong
     * @date Mar 18, 2013 11:51:03 AM
     * @return
     * @see
     */
    public boolean checkDefaultRoute() {
	return StringUtils.equals(defaultRoute, FossConstants.YES);
    }
    
    /**
     * @return  the virtualCode
     */
    public String getVirtualCode() {
        return virtualCode;
    }

    
    /**
     * @param virtualCode the virtualCode to set
     */
    public void setVirtualCode(String virtualCode) {
        this.virtualCode = virtualCode;
    }

    
    /**
     * @return  the orginalOrganizationCode
     */
    public String getOrginalOrganizationCode() {
        return orginalOrganizationCode;
    }

    
    /**
     * @param orginalOrganizationCode the orginalOrganizationCode to set
     */
    public void setOrginalOrganizationCode(String orginalOrganizationCode) {
        this.orginalOrganizationCode = orginalOrganizationCode;
    }

    
    /**
     * @return  the destinationOrganizationCode
     */
    public String getDestinationOrganizationCode() {
        return destinationOrganizationCode;
    }

    
    /**
     * @param destinationOrganizationCode the destinationOrganizationCode to set
     */
    public void setDestinationOrganizationCode(String destinationOrganizationCode) {
        this.destinationOrganizationCode = destinationOrganizationCode;
    }

    
    /**
     * @return  the transType
     */
    public String getTransType() {
        return transType;
    }

    
    /**
     * @param transType the transType to set
     */
    public void setTransType(String transType) {
        this.transType = transType;
    }

    
    /**
     * @return  the aging
     */
    public Long getAging() {
        return aging;
    }

    
    /**
     * @param aging the aging to set
     */
    public void setAging(Long aging) {
        this.aging = aging;
    }

    
    /**
     * @return  the defaultRoute
     */
    public String getDefaultRoute() {
        return defaultRoute;
    }

    
    /**
     * @param defaultRoute the defaultRoute to set
     */
    public void setDefaultRoute(String defaultRoute) {
        this.defaultRoute = defaultRoute;
    }

    
    /**
     * @return  the doPacking
     */
    public String getDoPacking() {
        return doPacking;
    }

    
    /**
     * @param doPacking the doPacking to set
     */
    public void setDoPacking(String doPacking) {
        this.doPacking = doPacking;
    }

    
    /**
     * @return  the active
     */
    public String getActive() {
        return active;
    }

    
    /**
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }

    
    /**
     * @return  the notes
     */
    public String getNotes() {
        return notes;
    }

    
    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }


    
    /**
     * @return  the packingOrganizationCode
     */
    public String getPackingOrganizationCode() {
        return packingOrganizationCode;
    }


    
    /**
     * @param packingOrganizationCode the packingOrganizationCode to set
     */
    public void setPackingOrganizationCode(String packingOrganizationCode) {
        this.packingOrganizationCode = packingOrganizationCode;
    }


    
    /**
     * @return  the freightRouteLineList
     */
    public List<FreightRouteLineEntity> getFreightRouteLineList() {
        return freightRouteLineList;
    }


    
    /**
     * @param freightRouteLineList the freightRouteLineList to set
     */
    public void setFreightRouteLineList(
    	List<FreightRouteLineEntity> freightRouteLineList) {
        this.freightRouteLineList = freightRouteLineList;
    }

    public String getOrginalType() {
        return orginalType;
    }


    
    public void setOrginalType(String orginalType) {
        this.orginalType = orginalType;
    }


    
    public String getDestinationType() {
        return destinationType;
    }


    
    public void setDestinationType(String destinationType) {
        this.destinationType = destinationType;
    }


    
    public String getPackingOrganizationName() {
        return packingOrganizationName;
    }


    
    public void setPackingOrganizationName(String packingOrganizationName) {
        this.packingOrganizationName = packingOrganizationName;
    }



    
    public String getProductName() {
        return productName;
    }



    
    public void setProductName(String productName) {
        this.productName = productName;
    }

    
    /**
     * @return  the lineVirtualCode
     */
    public String getLineVirtualCode() {
        return lineVirtualCode;
    }

    
    /**
     * @param lineVirtualCode the lineVirtualCode to set
     */
    public void setLineVirtualCode(String lineVirtualCode) {
        this.lineVirtualCode = lineVirtualCode;
    }
    
    
}
