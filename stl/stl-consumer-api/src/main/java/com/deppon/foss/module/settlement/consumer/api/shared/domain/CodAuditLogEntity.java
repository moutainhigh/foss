package com.deppon.foss.module.settlement.consumer.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

import java.util.Date;

/**
 * 代收货款审核操作日志
 */
public class CodAuditLogEntity extends BaseEntity{
    private String id;
    /**
     *代收货款审核日志ID 
     */
    private String auditCodId;
    /**
     *运单号
     */
    private String waybillNo;
    /**
     *操作内容
     */
    private String operateContent;
    /**
     *操作时间
     */
    private Date operateTime;
    /**
     *操作用户编码
     */
    private String operateUserCode;
    /**
     *操作用户姓名
     */
    private String operateUserName;
    /**
     *操作用户部门编码
     */
    private String operateDeptcode;
    /**
     *操作用户部门名称
     */
    private String operateDeptname;
    /**
     *创建时间
     */
    private Date createDate;
    /**
     *修改时间
     */
    private Date modifyDate;
    /**
     *创建人
     */
    private String createUser;
    /**
     *修改人
     */
    private String modifyUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuditCodId() {
        return auditCodId;
    }

    public void setAuditCodId(String auditCodId) {
        this.auditCodId = auditCodId;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public String getOperateContent() {
        return operateContent;
    }

    public void setOperateContent(String operateContent) {
        this.operateContent = operateContent;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateUserCode() {
        return operateUserCode;
    }

    public void setOperateUserCode(String operateUserCode) {
        this.operateUserCode = operateUserCode;
    }

    public String getOperateUserName() {
        return operateUserName;
    }

    public void setOperateUserName(String operateUserName) {
        this.operateUserName = operateUserName;
    }

    public String getOperateDeptcode() {
        return operateDeptcode;
    }

    public void setOperateDeptcode(String operateDeptcode) {
        this.operateDeptcode = operateDeptcode;
    }

    public String getOperateDeptname() {
        return operateDeptname;
    }

    public void setOperateDeptname(String operateDeptname) {
        this.operateDeptname = operateDeptname;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }
}