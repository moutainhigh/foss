package com.deppon.foss.module.pickup.order.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class AddressCollectionEntity  extends BaseEntity{ 

	private static final long serialVersionUID = 1L;

	private String id;

    private String billNo;

    private String driverCode;

    private String driverDept;

    private String addressType;

    private String gpsLongitude;

    private String gpsLatitude;

    private Date collectionTime;

    private String scopeoordinatesId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getDriverCode() {
        return driverCode;
    }

    public void setDriverCode(String driverCode) {
        this.driverCode = driverCode;
    }

    public String getDriverDept() {
        return driverDept;
    }

    public void setDriverDept(String driverDept) {
        this.driverDept = driverDept;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getGpsLongitude() {
        return gpsLongitude;
    }

    public void setGpsLongitude(String gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    public String getGpsLatitude() {
        return gpsLatitude;
    }

    public void setGpsLatitude(String gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public Date getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(Date collectionTime) {
        this.collectionTime = collectionTime;
    }

    public String getScopeoordinatesId() {
        return scopeoordinatesId;
    }

    public void setScopeoordinatesId(String scopeoordinatesId) {
        this.scopeoordinatesId = scopeoordinatesId;
    }
}