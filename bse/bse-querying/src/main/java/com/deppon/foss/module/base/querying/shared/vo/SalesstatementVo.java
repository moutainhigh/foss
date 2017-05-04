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
 * PROJECT NAME	: bse-querying
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/querying/shared/vo/SalesstatementVo.java
 * 
 * FILE NAME        	: SalesstatementVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.querying.shared.vo;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.CalcTotalFeeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.SalesStatementByComplexCondationResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillListByComplexCondationDto;

public class SalesstatementVo {

    /**
     * 查询条件
     */
    private WaybillListByComplexCondationDto condition = new WaybillListByComplexCondationDto();

    /**
     * 查询结果
     */
    private List<SalesStatementByComplexCondationResultDto> dtoList = new ArrayList<SalesStatementByComplexCondationResultDto>();
    /**
     * 查询结果 总计
     */
    private CalcTotalFeeDto totalFeeDto = new CalcTotalFeeDto();

    /**
     * 营业部
     */
    private SaleDepartmentEntity saleDepartment;

    /**
     * action 中涉及到的String操作【通过当前登录用户部门code查询营业部】
     */
    private String codeStr;

    /**
     * action 中涉及到的String操作数组
     */
    private List<String> codeArr;
    /**
     * @return the condition
     */
    public WaybillListByComplexCondationDto getCondition() {
	return condition;
    }

    /**
     * @param condition
     *            the condition to set
     */
    public void setCondition(WaybillListByComplexCondationDto condition) {
	this.condition = condition;
    }

    /**
     * @return the dtoList
     */
    public List<SalesStatementByComplexCondationResultDto> getDtoList() {
	return dtoList;
    }

    /**
     * @param dtoList
     *            the dtoList to set
     */
    public void setDtoList(
	    List<SalesStatementByComplexCondationResultDto> dtoList) {
	this.dtoList = dtoList;
    }

    /**
     * @return the codeStr
     */
    public String getCodeStr() {
	return codeStr;
    }

    /**
     * @param codeStr
     *            the codeStr to set
     */
    public void setCodeStr(String codeStr) {
	this.codeStr = codeStr;
    }

    /**
     * @return the saleDepartment
     */
    public SaleDepartmentEntity getSaleDepartment() {
	return saleDepartment;
    }

    /**
     * @param saleDepartment
     *            the saleDepartment to set
     */
    public void setSaleDepartment(SaleDepartmentEntity saleDepartment) {
	this.saleDepartment = saleDepartment;
    }

    
    /**
     * @return  the totalFeeDto
     */
    public CalcTotalFeeDto getTotalFeeDto() {
        return totalFeeDto;
    }

    
    /**
     * @param totalFeeDto the totalFeeDto to set
     */
    public void setTotalFeeDto(CalcTotalFeeDto totalFeeDto) {
        this.totalFeeDto = totalFeeDto;
    }

    
    /**
     * @return  the codeArr
     */
    public List<String> getCodeArr() {
        return codeArr;
    }

    
    /**
     * @param codeArr the codeArr to set
     */
    public void setCodeArr(List<String> codeArr) {
        this.codeArr = codeArr;
    }
}
