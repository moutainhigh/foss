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
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;


/**
 * 营业部和集中开单组关系DTO
 * @author foss-zhujunyong
 * @date Apr 26, 2013 2:48:39 PM
 * @version 1.0
 */
public class SalesBillingGroupDto implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 7166055801946743012L;

    // 营业部部门编码
    private String salesDeptCode;
    
    // 营业部部门名称
    private String salesDeptName;
    
    // 营业部对应的集中开单组列表
    private List<MapDto> billingGroupList;
    
    // 操作人员工号
    private String modifyUserCode;

    
    // 验证该实体是否合法,并适当容忍（对集中开单组列表去重去空）
    public boolean validate(){
	// 营业部编码，名称不能为null，操作人员工号也不能为null
	if (StringUtils.isBlank(salesDeptCode) || StringUtils.isBlank(salesDeptName) || StringUtils.isBlank(modifyUserCode)) {
	    return false;
	}
	
	if (billingGroupList == null) {
	    billingGroupList = new ArrayList<MapDto>();
	}
	
	List<MapDto> tempList = new ArrayList<MapDto> ();
	// 对集中开单组去重去空
	for (MapDto dto : billingGroupList) {
	    // 集中开单组的部门编码，名称不能为null
	    if (dto == null || StringUtils.isBlank(dto.getCode()) || StringUtils.isBlank(dto.getName())) {
		continue;
	    }
	    // 添加不重复的集中开单组到列表中
	    if (!tempList.contains(dto)) {
		tempList.add(dto);
	    }
	}
	billingGroupList = tempList;
	return true;
    }
    
    
    /**
     * @return  the salesDeptCode
     */
    public String getSalesDeptCode() {
        return salesDeptCode;
    }

    
    /**
     * @param salesDeptCode the salesDeptCode to set
     */
    public void setSalesDeptCode(String salesDeptCode) {
        this.salesDeptCode = salesDeptCode;
    }

    
    /**
     * @return  the salesDeptName
     */
    public String getSalesDeptName() {
        return salesDeptName;
    }

    
    /**
     * @param salesDeptName the salesDeptName to set
     */
    public void setSalesDeptName(String salesDeptName) {
        this.salesDeptName = salesDeptName;
    }

    
    /**
     * @return  the billingGroupList
     */
    public List<MapDto> getBillingGroupList() {
        return billingGroupList;
    }

    
    /**
     * @param billingGroupList the billingGroupList to set
     */
    public void setBillingGroupList(List<MapDto> billingGroupList) {
        this.billingGroupList = billingGroupList;
    }


    
    /**
     * @return  the modifyUserCode
     */
    public String getModifyUserCode() {
        return modifyUserCode;
    }


    
    /**
     * @param modifyUserCode the modifyUserCode to set
     */
    public void setModifyUserCode(String modifyUserCode) {
        this.modifyUserCode = modifyUserCode;
    }
    
 
    
}
