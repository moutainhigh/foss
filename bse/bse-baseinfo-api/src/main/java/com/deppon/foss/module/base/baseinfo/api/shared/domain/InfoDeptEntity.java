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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/InfoDeptEntity.java
 * 
 * FILE NAME        	: InfoDeptEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * 用来存储交互“信息部”的数据库对应实体：SUC-222
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-11 下午5:12:12</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-11 下午5:12:12
 * @since
 * @version
 */
public class InfoDeptEntity extends BaseEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -8697984366385622548L;

    //信息部名称
    private String name;
  //信息部ID
    private String code;

    //业务联系人
    private String contact;

    //联系电话
    private String contactPhone;

    //手机号码
    private String mobilePhone;

    //传真号码
    private String faxNo;

    //信息部性质
    private String property;

    //注册资本
    private BigDecimal registFunds;

    //法人代表
    private String legalPerson;

    //营业部执照编号
    private String operateLicense;

    //联系地址
    private String contactAddress;

    //标准总得分
    private BigDecimal totalScore;

    //采用意见
    private String opinion;

    //信息部老板身份证正面
    private String idCardFrontPic;

    //信息部老板身份证反面
    private String idCardBackPic;

    //营业执照复印件
    private String operateLicCopy;

    //信息部照片
    private String infoDeptPic;

    //是否启用
    private String active;
    
    //信息部得分集合
    private List<InfoDeptScoresEntity> infoDeptScoresList;
    
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
     * @return  the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return  the contact
     */
    public String getContact() {
        return contact;
    }
    
    /**
     * @param contact the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * @return  the contactPhone
     */
    public String getContactPhone() {
        return contactPhone;
    }
    
    /**
     * @param contactPhone the contactPhone to set
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    
    /**
     * @return  the mobilePhone
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * @param mobilePhone the mobilePhone to set
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * @return  the faxNo
     */
    public String getFaxNo() {
        return faxNo;
    }
    
    /**
     * @param faxNo the faxNo to set
     */
    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    /**
     * @return  the property
     */
    public String getProperty() {
        return property;
    }

    /**
     * @param property the property to set
     */
    public void setProperty(String property) {
        this.property = property;
    }
    
    /**
     * @return  the registFunds
     */
    public BigDecimal getRegistFunds() {
        return registFunds;
    }

    /**
     * @param registFunds the registFunds to set
     */
    public void setRegistFunds(BigDecimal registFunds) {
        this.registFunds = registFunds;
    }

    /**
     * @return  the legalPerson
     */
    public String getLegalPerson() {
        return legalPerson;
    }

    /**
     * @param legalPerson the legalPerson to set
     */
    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    /**
     * @return  the operateLicense
     */
    public String getOperateLicense() {
        return operateLicense;
    }

    /**
     * @param operateLicense the operateLicense to set
     */
    public void setOperateLicense(String operateLicense) {
        this.operateLicense = operateLicense;
    }
    
    /**
     * @return  the contactAddress
     */
    public String getContactAddress() {
        return contactAddress;
    }

    /**
     * @param contactAddress the contactAddress to set
     */
    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }
    
    /**
     * @return  the totalScore
     */
    public BigDecimal getTotalScore() {
        return totalScore;
    }

    /**
     * @param totalScore the totalScore to set
     */
    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * @return  the opinion
     */
    public String getOpinion() {
        return opinion;
    }
    
    /**
     * @param opinion the opinion to set
     */
    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }
    
    /**
     * @return  the idCardFrontPic
     */
    public String getIdCardFrontPic() {
        return idCardFrontPic;
    }

    /**
     * @param idCardFrontPic the idCardFrontPic to set
     */
    public void setIdCardFrontPic(String idCardFrontPic) {
        this.idCardFrontPic = idCardFrontPic;
    }
    
    /**
     * @return  the idCardBackPic
     */
    public String getIdCardBackPic() {
        return idCardBackPic;
    }

    /**
     * @param idCardBackPic the idCardBackPic to set
     */
    public void setIdCardBackPic(String idCardBackPic) {
        this.idCardBackPic = idCardBackPic;
    }

    /**
     * @return  the operateLicCopy
     */
    public String getOperateLicCopy() {
        return operateLicCopy;
    }
    
    /**
     * @param operateLicCopy the operateLicCopy to set
     */
    public void setOperateLicCopy(String operateLicCopy) {
        this.operateLicCopy = operateLicCopy;
    }

    /**
     * @return  the infoDeptPic
     */
    public String getInfoDeptPic() {
        return infoDeptPic;
    }
    
    /**
     * @param infoDeptPic the infoDeptPic to set
     */
    public void setInfoDeptPic(String infoDeptPic) {
        this.infoDeptPic = infoDeptPic;
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
     * @return  the infoDeptScoresList
     */
    public List<InfoDeptScoresEntity> getInfoDeptScoresList() {
        return infoDeptScoresList;
    }
    
    /**
     * @param infoDeptScoresList the infoDeptScoresList to set
     */
    public void setInfoDeptScoresList(List<InfoDeptScoresEntity> infoDeptScoresList) {
        this.infoDeptScoresList = infoDeptScoresList;
    }
}
