package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.util.Date;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

public class GpsAddressCollectEntity extends ScanMsgEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
   
    /**
     * 订单号/运单号
     * */
    private String billNo;

    /**
     * 地址类型（LD_PK:零担接货,LD_DE：零担送货,KD_PK：快递接货,KD_DE：快递送货）
     * */
    private String addressType;

    /**
     * GPS经度
     * */
    private String gpsLongitude;

    /**
     * GPS纬度
     * */
    private String gpsLatitude;

    /**
     * 采集时间
     * */
    private Date collectTime;
    

    

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
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

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }
   
    
}
