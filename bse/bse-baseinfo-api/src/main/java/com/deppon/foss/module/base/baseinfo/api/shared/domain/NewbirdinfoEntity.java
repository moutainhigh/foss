package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

public class NewbirdinfoEntity {

    private String id;


    private String transport;


    private String name;


    private String receiver;


    private String telphone;


    private String phone;


    private String operater;


    private Date operateTime;


    private String active;


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getTransport() {
        return transport;
    }


    public void setTransport(String transport) {
        this.transport = transport;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getReceiver() {
        return receiver;
    }


    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }


    public String getTelphone() {
        return telphone;
    }


    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }


    public String getPhone() {
        return phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getOperater() {
        return operater;
    }


    public void setOperater(String operater) {
        this.operater = operater;
    }


    public Date getOperateTime() {
        return operateTime;
    }


    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }


    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}