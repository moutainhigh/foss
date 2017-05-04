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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/ContactEntity.java
 * 
 * FILE NAME        	: ContactEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;

/**
 * 联系人实体类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-16 下午4:39:09 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-16 下午4:39:09
 * @since
 * @version
 */
public class ContactEntity extends BaseEntity{
   
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -8309380431114160567L;
    
    /**
     * 性别.
     */
    private String gender;
    
    /**
     * 办公电话.
     */
    private String contactPhone;
    
    /**
     * 移动电话.
     */
    private String mobilePhone;
    
    /**
     * 传真.
     */
    private String faxNo;
    
    /**
     * 联系人地址.
     */
    private String address;
    
    /**
     * 电子邮箱.
     */
    private String email;
    
    /**
     * 邮编.
     */
    private String zipCode;
    
    /**
     * 生日.
     */
    private Date birthday;
    
    /**
     * 身份证号.
     */
    private String idCard;
    
    /**
     * 个人爱好.
     */
    private String hobby;
    
    /**
     * 是否接收邮件.
     */
    private String receiveEmail;
    
    /**
     * 是否接收短信.
     */
    private String receiveMessage;
    
    /**
     * 是否接收信件.
     */
    private String receiveLetter;
    
    /**
     * 获知公司途径.
     */
    private String way;
    
    /**
     * 民族.
     */
    private String nation;
    
    /**
     * 籍贯.
     */
    private String hometown;
    
    /**
     * 职务.
     */
    private String title;
    
    /**
     * 任职部门.
     */
    private String workingDept;
    
    /**
     * 联系人姓名.
     */
    private String contactName;
    
    /**
     * 联系人编码.
     */
    private String code;
    
    /**
     * 备注.
     */
    private String notes;
    
    /**
     * 联系人类型.
     */
    private String contactType;
    
    /**
     * 是否主联系人.
     */
    private String mainContract;
    
    /**
     * 是否启用.
     */
    private String active;
    
    /**
     * 所属客户ID.
     */
    private BigDecimal customerCode;
    
    /**
     * 虚拟编码.
     */
    private String virtualCode;
    
    /**
     * 与客户信息是多对一关系.
     */
    private CustomerDto customerDto;
    
    /**
     * 在CRM中fid.
     */
    private BigDecimal crmId; 
    
    /**
     * 一个客户联系人对应多个联系人—地址（偏好地址）.
     */
    private List<ContactAddressEntity> contactAddrList;
    
    //078816_wp_2014-03-20
    /**
     * 所属客户的fossId
     */
    private String ownCustId;
    
    /**
     * 客户性质
     */
    private String customserNature;
    
    /**
     * 获取 性别.
     *
     * @return  the gender
     */
    public String getGender() {
        return gender;
    }
    
    /**
     * 获取 联系人编码.
     *
     * @return  the code
     */
    public String getCode() {
        return code;
    }

    
    /**
     * 设置 联系人编码.
     *
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 设置 性别.
     *
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    /**
     * 获取 办公电话.
     *
     * @return  the contactPhone
     */
    public String getContactPhone() {
        return contactPhone;
    }
    
    /**
     * 设置 办公电话.
     *
     * @param contactPhone the contactPhone to set
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    
    /**
     * 获取 移动电话.
     *
     * @return  the mobilePhone
     */
    public String getMobilePhone() {
        return mobilePhone;
    }
    
    /**
     * 设置 移动电话.
     *
     * @param mobilePhone the mobilePhone to set
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    
    /**
     * 获取 传真.
     *
     * @return  the faxNo
     */
    public String getFaxNo() {
        return faxNo;
    }
    
    /**
     * 设置 传真.
     *
     * @param faxNo the faxNo to set
     */
    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }
    
    /**
     * 获取 联系人地址.
     *
     * @return  the address
     */
    public String getAddress() {
        return address;
    }
    
    /**
     * 设置 联系人地址.
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * 获取 电子邮箱.
     *
     * @return  the email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * 设置 电子邮箱.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * 获取 邮编.
     *
     * @return  the zipCode
     */
    public String getZipCode() {
        return zipCode;
    }
    
    /**
     * 设置 邮编.
     *
     * @param zipCode the zipCode to set
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    /**
     * 获取 生日.
     *
     * @return  the birthday
     */
    public Date getBirthday() {
        return birthday;
    }
    
    /**
     * 设置 生日.
     *
     * @param birthday the birthday to set
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    /**
     * 获取 身份证号.
     *
     * @return  the idCard
     */
    public String getIdCard() {
        return idCard;
    }
    
    /**
     * 设置 身份证号.
     *
     * @param idCard the idCard to set
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    
    /**
     * 获取 个人爱好.
     *
     * @return  the hobby
     */
    public String getHobby() {
        return hobby;
    }
    
    /**
     * 设置 个人爱好.
     *
     * @param hobby the hobby to set
     */
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
    
    /**
     * 获取 是否接收邮件.
     *
     * @return  the receiveEmail
     */
    public String getReceiveEmail() {
        return receiveEmail;
    }
    
    /**
     * 设置 是否接收邮件.
     *
     * @param receiveEmail the receiveEmail to set
     */
    public void setReceiveEmail(String receiveEmail) {
        this.receiveEmail = receiveEmail;
    }
    
    /**
     * 获取 是否接收短信.
     *
     * @return  the receiveMessage
     */
    public String getReceiveMessage() {
        return receiveMessage;
    }
    
    /**
     * 设置 是否接收短信.
     *
     * @param receiveMessage the receiveMessage to set
     */
    public void setReceiveMessage(String receiveMessage) {
        this.receiveMessage = receiveMessage;
    }
    
    /**
     * 获取 是否接收信件.
     *
     * @return  the receiveLetter
     */
    public String getReceiveLetter() {
        return receiveLetter;
    }
    
    /**
     * 设置 是否接收信件.
     *
     * @param receiveLetter the receiveLetter to set
     */
    public void setReceiveLetter(String receiveLetter) {
        this.receiveLetter = receiveLetter;
    }
    
    /**
     * 获取 获知公司途径.
     *
     * @return  the way
     */
    public String getWay() {
        return way;
    }
    
    /**
     * 设置 获知公司途径.
     *
     * @param way the way to set
     */
    public void setWay(String way) {
        this.way = way;
    }
    
    /**
     * 获取 民族.
     *
     * @return  the nation
     */
    public String getNation() {
        return nation;
    }
    
    /**
     * 设置 民族.
     *
     * @param nation the nation to set
     */
    public void setNation(String nation) {
        this.nation = nation;
    }
    
    /**
     * 获取 籍贯.
     *
     * @return  the hometown
     */
    public String getHometown() {
        return hometown;
    }
    
    /**
     * 设置 籍贯.
     *
     * @param hometown the hometown to set
     */
    public void setHometown(String hometown) {
        this.hometown = hometown;
    }
    
    /**
     * 获取 职务.
     *
     * @return  the title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * 设置 职务.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * 获取 任职部门.
     *
     * @return  the workingDept
     */
    public String getWorkingDept() {
        return workingDept;
    }
    
    /**
     * 设置 任职部门.
     *
     * @param workingDept the workingDept to set
     */
    public void setWorkingDept(String workingDept) {
        this.workingDept = workingDept;
    }
    
    /**
     * 获取 联系人姓名.
     *
     * @return  the contactName
     */
    public String getContactName() {
        return contactName;
    }
    
    /**
     * 设置 联系人姓名.
     *
     * @param contactName the contactName to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    
    /**
     * 获取 备注.
     *
     * @return  the notes
     */
    public String getNotes() {
        return notes;
    }
    
    /**
     * 设置 备注.
     *
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    /**
     * 获取 联系人类型.
     *
     * @return  the contactType
     */
    public String getContactType() {
        return contactType;
    }
    
    /**
     * 设置 联系人类型.
     *
     * @param contactType the contactType to set
     */
    public void setContactType(String contactType) {
        this.contactType = contactType;
    }
    
    /**
     * 获取 是否主联系人.
     *
     * @return  the mainContract
     */
    public String getMainContract() {
        return mainContract;
    }
    
    /**
     * 设置 是否主联系人.
     *
     * @param mainContract the mainContract to set
     */
    public void setMainContract(String mainContract) {
        this.mainContract = mainContract;
    }
    
    /**
     * 获取 是否启用.
     *
     * @return  the active
     */
    public String getActive() {
        return active;
    }
    
    /**
     * 设置 是否启用.
     *
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }
    
    /**
     * 获取 所属客户ID.
     *
     * @return  the customerCode
     */
    public BigDecimal getCustomerCode() {
        return customerCode;
    }
    
    /**
     * 设置 所属客户ID.
     *
     * @param customerCode the customerCode to set
     */
    public void setCustomerCode(BigDecimal customerCode) {
        this.customerCode = customerCode;
    }
    
    /**
     * 获取 虚拟编码.
     *
     * @return  the virtualCode
     */
    public String getVirtualCode() {
        return virtualCode;
    }
    
    /**
     * 设置 虚拟编码.
     *
     * @param virtualCode the virtualCode to set
     */
    public void setVirtualCode(String virtualCode) {
        this.virtualCode = virtualCode;
    }
    
    /**
     * 获取 与客户信息是多对一关系.
     *
     * @return  the customerDto
     */
    public CustomerDto getCustomerDto() {
        return customerDto;
    }
    
    /**
     * 设置 与客户信息是多对一关系.
     *
     * @param customerDto the customerDto to set
     */
    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }
    
    /**
     * 获取 在CRM中fid.
     *
     * @return  the crmId
     */
    public BigDecimal getCrmId() {
        return crmId;
    }
    
    /**
     * 设置 在CRM中fid.
     *
     * @param crmId the crmId to set
     */
    public void setCrmId(BigDecimal crmId) {
        this.crmId = crmId;
    }
    
    /**
     * 获取 一个客户联系人对应多个联系人—地址（偏好地址）.
     *
     * @return  the contactAddrList
     */
    public List<ContactAddressEntity> getContactAddrList() {
        return contactAddrList;
    }
    
    /**
     * 设置 一个客户联系人对应多个联系人—地址（偏好地址）.
     *
     * @param contactAddrList the contactAddrList to set
     */
    public void setContactAddrList(List<ContactAddressEntity> contactAddrList) {
        this.contactAddrList = contactAddrList;
    }

	public String getCustomserNature() {
		return customserNature;
	}

	public void setCustomserNature(String customserNature) {
		this.customserNature = customserNature;
	}

	/**
	 * @return the ownCustId
	 */
	public String getOwnCustId() {
		return ownCustId;
	}

	/**
	 * @param ownCustId the ownCustId to set
	 */
	public void setOwnCustId(String ownCustId) {
		this.ownCustId = ownCustId;
	}
    
    
    
}
