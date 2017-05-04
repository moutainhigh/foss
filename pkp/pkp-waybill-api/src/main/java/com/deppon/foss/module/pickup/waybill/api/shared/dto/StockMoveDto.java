package com.deppon.foss.module.pickup.waybill.api.shared.dto;

/**
 * Created by 230532 on 2015/1/12.
 */
public class StockMoveDto {
    /*
    原库存部门编码
     */
    private String  OldFinalDeptCode;
    /*
    原库区编码
     */
    private String  OldAccessCode;
    /*
    原库存部门编码
     */
    private String  NewFinalDeptCode;
    /*
    原库区编码
     */
    private String  NewAccessCode;
    /*
    判断零担还是快递
     */
    private String  LTLorExpress;

    public String getOldFinalDeptCode() {
        return OldFinalDeptCode;
    }

    public void setOldFinalDeptCode(String oldFinalDeptCode) {
        OldFinalDeptCode = oldFinalDeptCode;
    }

    public String getOldAccessCode() {
        return OldAccessCode;
    }

    public void setOldAccessCode(String oldAccessCode) {
        OldAccessCode = oldAccessCode;
    }

    public String getNewFinalDeptCode() {
        return NewFinalDeptCode;
    }

    public void setNewFinalDeptCode(String newFinalDeptCode) {
        NewFinalDeptCode = newFinalDeptCode;
    }

    public String getNewAccessCode() {
        return NewAccessCode;
    }

    public void setNewAccessCode(String newAccessCode) {
        NewAccessCode = newAccessCode;
    }

    public String getLTLorExpress() {
        return LTLorExpress;
    }

    public void setLTLorExpress(String lTLorExpress) {
        this.LTLorExpress = lTLorExpress;
    }
}
