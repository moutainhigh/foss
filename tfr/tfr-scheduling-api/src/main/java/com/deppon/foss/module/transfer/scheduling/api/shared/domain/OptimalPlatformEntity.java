package com.deppon.foss.module.transfer.scheduling.api.shared.domain;

public class OptimalPlatformEntity {
	//主键ID
    private String optimalPlatformId;
    //任务车辆明细ID
    private String truckTaskDetailId;
    //月台号
    private String platformNo;
    //月台虚拟编码
    private String platformVirtualCode;
    //是否有升降台
    private String ifHasLift;
    //车辆类型
    private String vehicleTypeName;

    public String getOptimalPlatformId() {
        return optimalPlatformId;
    }

    public void setOptimalPlatformId(String optimalPlatformId) {
        this.optimalPlatformId = optimalPlatformId;
    }

    public String getTruckTaskDetailId() {
        return truckTaskDetailId;
    }

    public void setTruckTaskDetailId(String truckTaskDetailId) {
        this.truckTaskDetailId = truckTaskDetailId;
    }

    public String getPlatformNo() {
        return platformNo;
    }

    public void setPlatformNo(String platformNo) {
        this.platformNo = platformNo;
    }

    public String getPlatformVirtualCode() {
        return platformVirtualCode;
    }

    public void setPlatformVirtualCode(String platformVirtualCode) {
        this.platformVirtualCode = platformVirtualCode;
    }

    public String getIfHasLift() {
        return ifHasLift;
    }

    public void setIfHasLift(String ifHasLift) {
        this.ifHasLift = ifHasLift;
    }

    public String getVehicleTypeName() {
        return vehicleTypeName;
    }

    public void setVehicleTypeName(String vehicleTypeName) {
        this.vehicleTypeName = vehicleTypeName;
    }
}