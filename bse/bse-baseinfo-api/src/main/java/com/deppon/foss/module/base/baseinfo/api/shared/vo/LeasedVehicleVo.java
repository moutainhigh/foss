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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/LeasedVehicleVo.java
 * 
 * FILE NAME        	: LeasedVehicleVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity;
/**
 * 用来响应“外请车辆（厢式车、挂车、拖头）”的Action类的封装对象VO：SUC-104、SUC-608、SUC-44、SUC-103  
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-26 下午8:48:32</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-26 下午8:48:32
 * @since
 * @version
 */
public class LeasedVehicleVo implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 8618840066482293788L;
    
    /**
     * "外请车"对象
     */
    private LeasedTruckEntity leasedVehicle;
    
    /**
     * "外请车"对象列表集合
     */
    private List<LeasedTruckEntity> leasedVehicleList;

	/**
     * 批量操作ID集合
     */
    private List<String> batchIds;

    /**
     * 绑定车队，部门编码
     */
    private List<String> bindingOgrCodes;
    
    private String empCode;
    private String empNum;
    private String returnYorN;
    
    
    public String getReturnYorN() {
		return returnYorN;
	}

	public void setReturnYorN(String returnYorN) {
		this.returnYorN = returnYorN;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpNum() {
		return empNum;
	}

	public void setEmpNum(String empNum) {
		this.empNum = empNum;
	}

	/**
     * @return  the leasedVehicle
     */
    public LeasedTruckEntity getLeasedVehicle() {
        return leasedVehicle;
    }
    
    /**
     * @param leasedVehicle the leasedVehicle to set
     */
    public void setLeasedVehicle(LeasedTruckEntity leasedVehicle) {
        this.leasedVehicle = leasedVehicle;
    }
    
    /**
     * @return  the leasedVehicleList
     */
    public List<LeasedTruckEntity> getLeasedVehicleList() {
        return leasedVehicleList;
    }
    
    /**
     * @param leasedVehicleList the leasedVehicleList to set
     */
    public void setLeasedVehicleList(List<LeasedTruckEntity> leasedVehicleList) {
        this.leasedVehicleList = leasedVehicleList;
    }
    
    /**
     * @return  the batchIds
     */
    public List<String> getBatchIds() {
        return batchIds;
    }
    
    /**
     * @param batchIds the batchIds to set
     */
    public void setBatchIds(List<String> batchIds) {
        this.batchIds = batchIds;
    }
    
    public List<String> getBindingOgrCodes() {
		return bindingOgrCodes;
	}

	public void setBindingOgrCodes(List<String> bindingOgrCodes) {
		this.bindingOgrCodes = bindingOgrCodes;
	}
}
