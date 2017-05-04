package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 快递代理代理网点
 * 
 * @author WangPeng
 * @date   2013-07-16 12:42PM
 *
 */
public class OuterBranchExpressEntity extends BaseEntity {

	/**
	 * Serial VersionUID
	 */
	private static final long serialVersionUID = 741540695985118216L;
	
	/**
	 * ID
	 */
	private String id;
	
	 /**
     * 代理网点编码.
     */
    private String agentDeptCode;

    /**
     * 代理网点名称.
     */
    private String agentDeptName;

    /**
     * 代理公司编码.
     */
    private String agentCompany;
    
    /**
     * 代理公司名称（扩展）.
     */
    private String agentCompanyName;
    
    /**
     * 管理部门编码.
     */
    private String management;
    
    /**
     * 管理部门名称（扩展）.
     */
    private String managementName;
    
    /**
     * 代理网点简称.
     */
    private String simplename;
    
    /**
     * 国家地区.
     */	
    private String countryRegion;
    
    /**
     * 国家地区名称.
     */	
     private String countryRegionName; 
     
     /**
      * 提货网点编码，为4位数字,用户打标签使用.
      */
     private String stationNumber;

    /**
     * 省份编码.
     */
    private String provCode;
    
    /**
     * 省份名称（扩展）.
     */
    private String provName;

    /**
     * 正单联系电话.
     */
    private String airWaybillTel;

    /**
     * 正单开单名称.
     */
    private String airWaybillName;

    /**
     * 城市编码.
     */
    private String cityCode;
    
    /**
     * 城市名称（扩展）.
     */
    private String cityName;

    /**
     * 联系人.
     */
    private String contact;

    /**
     * 区县编码.
     */
    private String countyCode;
    
    /**
     * 区县名称（扩展）.
     */
    private String countyName;

    /**
     * 网点地址.
     */
    private String address;

    /**
     * 是否可自提.
     */
    private String pickupSelf;

    /**
     * 是否送货上门.
     */
    private String pickupToDoor;

    /**
     * 是否支持返回签单.
     */
    private String returnBill;

    /**
     * 是否支持货到付款.
     */
    private String arriveCharge;

    /**
     * 是否支持代收货款.
     */
    private String helpCharge;

    /**
     * 自提区域.
     */
    private String pickupArea;

    /**
     * 派送区域.
     */
    private String deliverArea;

    /**
     * 可出发.
     */
    private String leave;

    /**
     * 可中转.
     */
    private String transfer;

    /**
     * 可到达.
     */
    private String arrive;

    /**
     * 联系电话.
     */
    private String contactPhone;

    /**
     * 联系人电话.
     */
    private String mobilePhone;

    /**
     * 备注.
     */
    private String notes;

    /**
     * 是否启用.
     */
    private String active;
    
    /**
     * 虚拟编码.
     */
    private String virtualCode;
    
    /**
     * 网点类型：空运代理：KY 偏线代理：PX.
     */
    private String branchtype;
    
    /**
     * 拼音.
     */
    private String pinyin;
    
    /**
     * 版本号.
     */
    private Long versionNo;
    
    /**
     * 是否机场
     */
    private String isAirport;
    
    /**
     * 是否保价
     */
    private String insured;
    
    /**
     * 网点服务坐标
     */
    private String deptCoordinate;
    
    /**
     * 派送区域服务坐标
     */
    private String deliveryCoordinate;
    
    /**
	 * 代理公司简称
	 */
	private String agentCompanyAbbreviation;
	/**
	 * 代理公司简称Code
	 */
	private String agentCompanyAbbreviationCode;
	/**
	 * agentCollectedUpperLimit
	 * @return
	 */
	private String agentCollectedUpperLimit;
	
	
    public String getAgentCollectedUpperLimit() {
		return agentCollectedUpperLimit;
	}
	public void setAgentCollectedUpperLimit(String agentCollectedUpperLimit) {
		this.agentCollectedUpperLimit = agentCollectedUpperLimit;
	}
	public String getAgentCompanyAbbreviation() {
		return agentCompanyAbbreviation;
	}
	public void setAgentCompanyAbbreviation(String agentCompanyAbbreviation) {
		this.agentCompanyAbbreviation = agentCompanyAbbreviation;
	}
	public String getAgentCompanyAbbreviationCode() {
		return agentCompanyAbbreviationCode;
	}
	public void setAgentCompanyAbbreviationCode(String agentCompanyAbbreviationCode) {
		this.agentCompanyAbbreviationCode = agentCompanyAbbreviationCode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
    
    /**
     * 是否机场
     *
     * @return the 是否机场
     */
    public String getIsAirport() {
		return isAirport;
	}
    /**
     * 是否机场
     *
     * @param pinyin the new 是否机场
     */
	public void setIsAirport(String isAirport) {
		this.isAirport = isAirport;
	}

	/**
     * 获取 拼音.
     *
     * @return the 拼音
     */
    public String getPinyin() {
        return pinyin;
    }
    
    /**
     * 设置 拼音.
     *
     * @param pinyin the new 拼音
     */
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
    
    /**
     * 获取 版本号.
     *
     * @return the 版本号
     */
    public Long getVersionNo() {
        return versionNo;
    }
    
    /**
     * 获取 提货网点编码，为4位数字,用户打标签使用.
     *
     * @return  the stationNumber
     */
    public String getStationNumber() {
        return stationNumber;
    }
    
    /**
     * 设置 提货网点编码，为4位数字,用户打标签使用.
     *
     * @param stationNumber the stationNumber to set
     */
    public void setStationNumber(String stationNumber) {
        this.stationNumber = stationNumber;
    }

    /**
     * 设置 版本号.
     *
     * @param versionNo the new 版本号
     */
    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }

    /**
     * 获取 省份名称（扩展）.
     *
     * @return the 省份名称（扩展）
     */
    public String getProvName() {
        return provName;
    }

    /**
     * 设置 省份名称（扩展）.
     *
     * @param provName the new 省份名称（扩展）
     */
    public void setProvName(String provName) {
        this.provName = provName;
    }
    
    /**
     * 获取 城市名称（扩展）.
     *
     * @return the 城市名称（扩展）
     */
    public String getCityName() {
        return cityName;
    }
    
    /**
     * 设置 城市名称（扩展）.
     *
     * @param cityName the new 城市名称（扩展）
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    
    /**
     * 获取 区县名称（扩展）.
     *
     * @return the 区县名称（扩展）
     */
    public String getCountyName() {
        return countyName;
    }
    
    /**
     * 设置 区县名称（扩展）.
     *
     * @param countyName the new 区县名称（扩展）
     */
    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    /**
     * 获取 网点类型：空运代理：KY 偏线代理：PX.
     *
     * @return the 网点类型：空运代理：KY 偏线代理：PX
     */
    public String getBranchtype() {
        return branchtype;
    }
    
    /**
     * 设置 网点类型：空运代理：KY 偏线代理：PX.
     *
     * @param branchtype the new 网点类型：空运代理：KY 偏线代理：PX
     */
    public void setBranchtype(String branchtype) {
        this.branchtype = branchtype;
    }

    /**
     * 获取 虚拟编码.
     *
     * @return the 虚拟编码
     */
    public String getVirtualCode() {
        return virtualCode;
    }

    /**
     * 设置 虚拟编码.
     *
     * @param virtualCode the new 虚拟编码
     */
    public void setVirtualCode(String virtualCode) {
        this.virtualCode = virtualCode;
    }

    /**
     * 获取 代理网点编码.
     *
     * @return  the agentDeptCode
     */
    public String getAgentDeptCode() {
        return agentDeptCode;
    }

    /**
     * 设置 代理网点编码.
     *
     * @param agentDeptCode the agentDeptCode to set
     */
    public void setAgentDeptCode(String agentDeptCode) {
        this.agentDeptCode = agentDeptCode;
    }
    
    /**
     * 获取 代理网点名称.
     *
     * @return  the agentDeptName
     */
    public String getAgentDeptName() {
        return agentDeptName;
    }
    
    /**
     * 设置 代理网点名称.
     *
     * @param agentDeptName the agentDeptName to set
     */
    public void setAgentDeptName(String agentDeptName) {
        this.agentDeptName = agentDeptName;
    }
    
    /**
     * 获取 代理公司虚拟编码.
     *
     * @return  the agentCompany
     */
    public String getAgentCompany() {
        return agentCompany;
    }
    
    /**
     * 设置 代理公司虚拟编码.
     *
     * @param agentCompany the agentCompany to set
     */
    public void setAgentCompany(String agentCompany) {
        this.agentCompany = agentCompany;
    }
    
    /**
     * 获取 管理部门编码.
     *
     * @return  the management
     */
    public String getManagement() {
        return management;
    }

    /**
     * 设置 管理部门编码.
     *
     * @param management the management to set
     */
    public void setManagement(String management) {
        this.management = management;
    }

    /**
     * 获取 代理网点简称.
     *
     * @return  the simplename
     */
    public String getSimplename() {
        return simplename;
    }

    /**
     * 设置 代理网点简称.
     *
     * @param simplename the simplename to set
     */
    public void setSimplename(String simplename) {
        this.simplename = simplename;
    }
    
    /**
     * 获取 省份编码.
     *
     * @return  the provCode
     */
    public String getProvCode() {
        return provCode;
    }
    
    /**
     * 设置 省份编码.
     *
     * @param provCode the provCode to set
     */
    public void setProvCode(String provCode) {
        this.provCode = provCode;
    }

    /**
     * 获取 正单联系电话.
     *
     * @return  the airWaybillTel
     */
    public String getAirWaybillTel() {
        return airWaybillTel;
    }
    
    /**
     * 设置 正单联系电话.
     *
     * @param airWaybillTel the airWaybillTel to set
     */
    public void setAirWaybillTel(String airWaybillTel) {
        this.airWaybillTel = airWaybillTel;
    }

    /**
     * 获取 正单开单名称.
     *
     * @return  the airWaybillName
     */
    public String getAirWaybillName() {
        return airWaybillName;
    }

    /**
     * 设置 正单开单名称.
     *
     * @param airWaybillName the airWaybillName to set
     */
    public void setAirWaybillName(String airWaybillName) {
        this.airWaybillName = airWaybillName;
    }
    
    /**
     * 获取 城市编码.
     *
     * @return  the cityCode
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * 设置 城市编码.
     *
     * @param cityCode the cityCode to set
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    
    /**
     * 获取 联系人.
     *
     * @return  the contact
     */
    public String getContact() {
        return contact;
    }
    
    /**
     * 设置 联系人.
     *
     * @param contact the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * 获取 区县编码.
     *
     * @return  the countyCode
     */
    public String getCountyCode() {
        return countyCode;
    }
    
    /**
     * 设置 区县编码.
     *
     * @param countyCode the countyCode to set
     */
    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    /**
     * 获取 网点地址.
     *
     * @return  the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置 网点地址.
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取 是否可自提.
     *
     * @return  the pickupSelf
     */
    public String getPickupSelf() {
        return pickupSelf;
    }
    
    /**
     * 设置 是否可自提.
     *
     * @param pickupSelf the pickupSelf to set
     */
    public void setPickupSelf(String pickupSelf) {
        this.pickupSelf = pickupSelf;
    }
    
    /**
     * 获取 是否送货上门.
     *
     * @return  the pickupToDoor
     */
    public String getPickupToDoor() {
        return pickupToDoor;
    }

    /**
     * 设置 是否送货上门.
     *
     * @param pickupToDoor the pickupToDoor to set
     */
    public void setPickupToDoor(String pickupToDoor) {
        this.pickupToDoor = pickupToDoor;
    }

    /**
     * 获取 是否支持返回签单.
     *
     * @return  the returnBill
     */
    public String getReturnBill() {
        return returnBill;
    }
    
    /**
     * 设置 是否支持返回签单.
     *
     * @param returnBill the returnBill to set
     */
    public void setReturnBill(String returnBill) {
        this.returnBill = returnBill;
    }

    /**
     * 获取 是否支持货到付款.
     *
     * @return  the arriveCharge
     */
    public String getArriveCharge() {
        return arriveCharge;
    }
    
    /**
     * 设置 是否支持货到付款.
     *
     * @param arriveCharge the arriveCharge to set
     */
    public void setArriveCharge(String arriveCharge) {
        this.arriveCharge = arriveCharge;
    }
    
    /**
     * 获取 是否支持代收货款.
     *
     * @return  the helpCharge
     */
    public String getHelpCharge() {
        return helpCharge;
    }
    
    /**
     * 设置 是否支持代收货款.
     *
     * @param helpCharge the helpCharge to set
     */
    public void setHelpCharge(String helpCharge) {
        this.helpCharge = helpCharge;
    }

    /**
     * 获取 自提区域.
     *
     * @return  the pickupArea
     */
    public String getPickupArea() {
        return pickupArea;
    }
    
    /**
     * 设置 自提区域.
     *
     * @param pickupArea the pickupArea to set
     */
    public void setPickupArea(String pickupArea) {
        this.pickupArea = pickupArea;
    }
    
    /**
     * 获取 派送区域.
     *
     * @return  the deliverArea
     */
    public String getDeliverArea() {
        return deliverArea;
    }
    
    /**
     * 设置 派送区域.
     *
     * @param deliverArea the deliverArea to set
     */
    public void setDeliverArea(String deliverArea) {
        this.deliverArea = deliverArea;
    }

    /**
     * 获取 可出发.
     *
     * @return  the leave
     */
    public String getLeave() {
        return leave;
    }

    /**
     * 设置 可出发.
     *
     * @param leave the leave to set
     */
    public void setLeave(String leave) {
        this.leave = leave;
    }

    /**
     * 获取 可中转.
     *
     * @return  the transfer
     */
    public String getTransfer() {
        return transfer;
    }
    
    /**
     * 设置 可中转.
     *
     * @param transfer the transfer to set
     */
    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }
    
    /**
     * 获取 可到达.
     *
     * @return  the arrive
     */
    public String getArrive() {
        return arrive;
    }

    /**
     * 设置 可到达.
     *
     * @param arrive the arrive to set
     */
    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    /**
     * 获取 联系电话.
     *
     * @return  the contactPhone
     */
    public String getContactPhone() {
        return contactPhone;
    }
    
    /**
     * 设置 联系电话.
     *
     * @param contactPhone the contactPhone to set
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    
    /**
     * 获取 联系人电话.
     *
     * @return  the mobilePhone
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * 设置 联系人电话.
     *
     * @param mobilePhone the mobilePhone to set
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
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
     * 获取 代理公司名称（扩展）.
     *
     * @return the 代理公司名称（扩展）
     */
    public String getAgentCompanyName() {
        return agentCompanyName;
    }
    
    /**
     * 设置 代理公司名称（扩展）.
     *
     * @param agentCompanyName the new 代理公司名称（扩展）
     */
    public void setAgentCompanyName(String agentCompanyName) {
        this.agentCompanyName = agentCompanyName;
    }
    
    /**
     * 获取 管理部门名称（扩展）.
     *
     * @return the 管理部门名称（扩展）
     */
    public String getManagementName() {
        return managementName;
    }
    
    /**
     * 设置 管理部门名称（扩展）.
     *
     * @param managementName the new 管理部门名称（扩展）
     */
    public void setManagementName(String managementName) {
        this.managementName = managementName;
    }
    
    /**
     * 获取 国家地区.
     *
     * @return the 国家地区
     */
    public String getCountryRegion() {
        return countryRegion;
    }
    
    /**
     * 设置 国家地区.
     *
     * @param countryRegion the new 国家地区
     */
    public void setCountryRegion(String countryRegion) {
        this.countryRegion = countryRegion;
    }
    
    /**
     * 获取 国家地区名称.
     *
     * @return the 国家地区名称
     */
    public String getCountryRegionName() {
        return countryRegionName;
    }
    
    /**
     * 设置 国家地区名称.
     *
     * @param countryRegionName the new 国家地区名称
     */
    public void setCountryRegionName(String countryRegionName) {
        this.countryRegionName = countryRegionName;
    }
	/**
	 * @return 保价
	 */
	public String getInsured() {
		return insured;
	}
	/**
	 * @param 设置保价
	 */
	public void setInsured(String insured) {
		this.insured = insured;
	}
	/**
	 * @return  the deptCoordinate
	 */
	public String getDeptCoordinate() {
		return deptCoordinate;
	}
	/**
	 * @param deptCoordinate the deptCoordinate to set
	 */
	public void setDeptCoordinate(String deptCoordinate) {
		this.deptCoordinate = deptCoordinate;
	}
	/**
	 * @return  the deliveryCoordinate
	 */
	public String getDeliveryCoordinate() {
		return deliveryCoordinate;
	}
	/**
	 * @param deliveryCoordinate the deliveryCoordinate to set
	 */
	public void setDeliveryCoordinate(String deliveryCoordinate) {
		this.deliveryCoordinate = deliveryCoordinate;
	}

}
