/**
 *  initial comments.
 */

package com.deppon.foss.module.transfer.dubbo.api.define;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;
/**
 * 分单合票DTO
 * @author 099197-foss-zhoudejun
 * @date 2012-10-19 下午5:15:13
 */
public class AirWaybillDetailDto implements Serializable {

    

	private static final long serialVersionUID = 733035901981856301L;

	/**
	 * 合票号
	 */
	private String jointTicketNo;
	/**
	 * 运单流水明细ID
	 */
	private String serialId;
	/**
	 * 操作类型
	 */
	private String operatingType;
	/**
	 * 单一运单号
	 */
	private String waybillNo;
	/**
	 * 运单号waybillNo
	 */
	private String[] waybillNos;
	/**
	 * 开单方式
	 */
	private String makeWaybillWay;
	/**
	 * 库存状态
	 */
	private String stockStatus;
	/**
	 * 开始时间 
	 */
	private Date beginInTime;
	/**
	 * 截止时间
	 */
	private Date endInTime;
	/**
	 * 目的站名称
	 */
	private String arrvRegionName;
	/**
	 * 正单ID
	 */
	private String aiyWayBillId;
	/**
	 * 运单明细ID数组
	 */
	private String[] waybillIds;
	/**
	 * 当前操作人所在城市
	 */
	private String cityName;
	/**
	 * 制单人
	 */
	private String createUserName;
	/**
	 * 制单人所在部门代码
	 */
	private String orgCode;
	/**
	 * 制单人所在部门
	 */
	private String orgName;
	/**
	 * 制单部门电话zwd
	 */
	private String shipperContactPhone;
	
	/**
	 * 航空震正单明细id
	 */
	private String airWaybillDetailId;
	
	/**
	 * 需要新增的流水号list
	 */
	private List<AirSerialNoDetail> addAirSerialNoDetailList;
	/**
	 * 需要删除的流水号list
	 */
	private List<AirSerialNoDetail> delAirSerialNoDetailList;
	
	/**
	 * 需要显示的流水号list
	 */
	private List<AirSerialNoDetail> airSerialNoViewDetailList;
	
	/**
	 * 库存状态list
	 */
	private List<String> stockStatusList;
	
	/**
	 * 开单方式
	 * */
	private List<String> freightMethodList;
	
	/**
	 * 库存状态
	 */
	private List<String> handoverbillStateList;
	
	/**
	 * 库区编码
	 */
	private String goodsAreaCode;
	
	/**
	 * 空运总调部门
	 */
	private String destOrgCode;
	
	/**
	 * 运单流水号list
	 */
	private List<String> waybillNoList;
	
	/**
	 * 交接单code
	 */
	private String handoverbillOrgCode;
	
	/**
	 * 交接到空运总调对应外场
	 * 如，广州空运总调对应的外场为‘花都运作部’
	 * */
	private String handOverbillTFROrgCode;
    /**
     * 快递列表
     */
    private List<String> packageProductList;

    /**
     *下一个到达网点
     */
    private String nextDestOrg;
    /**
     * 运输性质
     */
    private String transportType;
    
    /**
	 * 入库开始时间 
	 */
	private Date storageBeginInTime;
	/**
	 * 入库截止时间
	 */
	private Date storageEndInTime;

	/**
     * 入库开始时间 
     * @return
     */
    public Date getStorageBeginInTime() {
		return storageBeginInTime;
	}
    /**
     * 入库开始时间 
     * @param storageBeginInTime
     */
	public void setStorageBeginInTime(Date storageBeginInTime) {
		this.storageBeginInTime = storageBeginInTime;
	}
	/**
     * 入库截止时间
     * @return
     */
	public Date getStorageEndInTime() {
		return storageEndInTime;
	}
	/**
     * 入库截止时间
     * @param storageEndInTime
     */
	public void setStorageEndInTime(Date storageEndInTime) {
		this.storageEndInTime = storageEndInTime;
	}

	/**
     * 运输性质
     * @return
     */
    public String getTransportType() {
        return transportType;
    }

    /**
     * 运输性质
     * @param transportType
     */
    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    /**
     * 下一个到达网点
     * @return
     */
    public String getNextDestOrg() {
        return nextDestOrg;
    }

    /**
     * 下一个到达网点
     * @param nextDestOrg
     */
    public void setNextDestOrg(String nextDestOrg) {
        this.nextDestOrg = nextDestOrg;
    }

    /**
     * 快递产品列表
     * @return
     */
    public List<String> getPackageProductList() {
        return packageProductList;
    }

    /**
     * 快递产品列表
     * @param packageProductList
     */
    public void setPackageProductList(List<String> packageProductList) {
        this.packageProductList = packageProductList;
    }

    /**
	 * 获取 运单明细ID数组.
	 *
	 * @return the 运单明细ID数组
	 */
	public String[] getWaybillIds() {
		return waybillIds;
	}

	/**
	 * 设置 运单明细ID数组.
	 *
	 * @param waybillIds the new 运单明细ID数组
	 */
	public void setWaybillIds(String[] waybillIds) {
		this.waybillIds = waybillIds;
	}

	public List<String> getWaybillNoList() {
		return waybillNoList;
	}

	public void setWaybillNoList(List<String> waybillNoList) {
		this.waybillNoList = waybillNoList;
	}

	/**
	 * 获取 合票号.
	 *
	 * @return the 合票号
	 */
	public String getJointTicketNo() {
		return jointTicketNo;
	}

	/**
	 * 设置 合票号.
	 *
	 * @param jointTicketNo the new 合票号
	 */
	public void setJointTicketNo(String jointTicketNo) {
		this.jointTicketNo = jointTicketNo;
	}

	/**
	 * 获取 运单流水明细ID.
	 *
	 * @return the 运单流水明细ID
	 */
	public String getSerialId() {
		return serialId;
	}

	/**
	 * 设置 运单流水明细ID.
	 *
	 * @param serialId the new 运单流水明细ID
	 */
	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	/**
	 * 获取 操作类型.
	 *
	 * @return the 操作类型
	 */
	public String getOperatingType() {
		return operatingType;
	}

	/**
	 * 设置 操作类型.
	 *
	 * @param operatingType the new 操作类型
	 */
	public void setOperatingType(String operatingType) {
		this.operatingType = operatingType;
	}

	/**
	 * 获取 单一运单号.
	 *
	 * @return the 单一运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 设置 单一运单号.
	 *
	 * @param waybillNo the new 单一运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 获取 开单方式.
	 *
	 * @return the 开单方式
	 */
	public String getMakeWaybillWay() {
		return makeWaybillWay;
	}

	/**
	 * 设置 开单方式.
	 *
	 * @param makeWaybillWay the new 开单方式
	 */
	public void setMakeWaybillWay(String makeWaybillWay) {
		this.makeWaybillWay = makeWaybillWay;
	}

	/**
	 * 获取 库存状态.
	 *
	 * @return the 库存状态
	 */
	public String getStockStatus() {
		return stockStatus;
	}

	/**
	 * 设置 库存状态.
	 *
	 * @param stockStatus the new 库存状态
	 */
	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}

	/**
	 * 获取 开始时间.
	 *
	 * @return the 开始时间
	 */
	public Date getBeginInTime() {
		return beginInTime;
	}

	/**
	 * 设置 开始时间.
	 *
	 * @param beginInTime the new 开始时间
	 */
	@DateFormat
	public void setBeginInTime(Date beginInTime) {
		this.beginInTime = beginInTime;
	}

	/**
	 * 获取 截止时间.
	 *
	 * @return the 截止时间
	 */
	public Date getEndInTime() {
		return endInTime;
	}
	
	/**
	 * 设置 截止时间.
	 *
	 * @param endInTime the new 截止时间
	 */
	@DateFormat
	public void setEndInTime(Date endInTime) {
		this.endInTime = endInTime;
	}

	/**
	 * 获取 目的站名称.
	 *
	 * @return the 目的站名称
	 */
	public String getArrvRegionName() {
		return arrvRegionName;
	}

	/**
	 * 获取 运单号waybillNo.
	 *
	 * @return the 运单号waybillNo
	 */
	public String[] getWaybillNos() {
		return waybillNos;
	}

	/**
	 * 设置 运单号waybillNo.
	 *
	 * @param waybillNos the new 运单号waybillNo
	 */
	public void setWaybillNos(String[] waybillNos) {
		this.waybillNos = waybillNos;
	}

	/**
	 * 设置 目的站名称.
	 *
	 * @param arrvRegionName the new 目的站名称
	 */
	public void setArrvRegionName(String arrvRegionName) {
		this.arrvRegionName = arrvRegionName;
	}

	/**
	 * 获取 正单ID.
	 *
	 * @return the 正单ID
	 */
	public String getAiyWayBillId() {
		return aiyWayBillId;
	}

	/**
	 * 设置 正单ID.
	 *
	 * @param aiyWayBillId the new 正单ID
	 */
	public void setAiyWayBillId(String aiyWayBillId) {
		this.aiyWayBillId = aiyWayBillId;
	}

	/**
	 * 获取 当前操作人所在城市.
	 *
	 * @return the 当前操作人所在城市
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * 设置 当前操作人所在城市.
	 *
	 * @param cityName the new 当前操作人所在城市
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * 获取 制单人.
	 *
	 * @return the 制单人
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * 设置 制单人.
	 *
	 * @param createUserName the new 制单人
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * 获取 制单人所在部门.
	 *
	 * @return the 制单人所在部门
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * 设置 制单人所在部门.
	 *
	 * @param orgName the new 制单人所在部门
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * 获取 航空震正单明细id.
	 *
	 * @return the 航空震正单明细id
	 */
	public String getAirWaybillDetailId() {
		return airWaybillDetailId;
	}

	/**
	 * 设置 航空震正单明细id.
	 *
	 * @param airWaybillDetailId the new 航空震正单明细id
	 */
	public void setAirWaybillDetailId(String airWaybillDetailId) {
		this.airWaybillDetailId = airWaybillDetailId;
	}

	/**
	 * 获取 需要新增的流水号list.
	 *
	 * @return the 需要新增的流水号list
	 */
	public List<AirSerialNoDetail> getAddAirSerialNoDetailList() {
		return addAirSerialNoDetailList;
	}

	/**
	 * 设置 需要新增的流水号list.
	 *
	 * @param addAirSerialNoDetailList the new 需要新增的流水号list
	 */
	public void setAddAirSerialNoDetailList(
			List<AirSerialNoDetail> addAirSerialNoDetailList) {
		this.addAirSerialNoDetailList = addAirSerialNoDetailList;
	}

	/**
	 * 获取 需要删除的流水号list.
	 *
	 * @return the 需要删除的流水号list
	 */
	public List<AirSerialNoDetail> getDelAirSerialNoDetailList() {
		return delAirSerialNoDetailList;
	}

	/**
	 * 设置 需要删除的流水号list.
	 *
	 * @param delAirSerialNoDetailList the new 需要删除的流水号list
	 */
	public void setDelAirSerialNoDetailList(
			List<AirSerialNoDetail> delAirSerialNoDetailList) {
		this.delAirSerialNoDetailList = delAirSerialNoDetailList;
	}

	public List<AirSerialNoDetail> getAirSerialNoViewDetailList() {
		return airSerialNoViewDetailList;
	}

	public void setAirSerialNoViewDetailList(
			List<AirSerialNoDetail> airSerialNoViewDetailList) {
		this.airSerialNoViewDetailList = airSerialNoViewDetailList;
	}

	public List<String> getStockStatusList() {
		return stockStatusList;
	}

	public void setStockStatusList(List<String> stockStatusList) {
		this.stockStatusList = stockStatusList;
	}

	public List<String> getFreightMethodList() {
		return freightMethodList;
	}

	public void setFreightMethodList(List<String> freightMethodList) {
		this.freightMethodList = freightMethodList;
	}

	public List<String> getHandoverbillStateList() {
		return handoverbillStateList;
	}

	public void setHandoverbillStateList(List<String> handoverbillStateList) {
		this.handoverbillStateList = handoverbillStateList;
	}

	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}

	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}

	public String getDestOrgCode() {
		return destOrgCode;
	}

	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	public String getHandoverbillOrgCode() {
		return handoverbillOrgCode;
	}

	public void setHandoverbillOrgCode(String handoverbillOrgCode) {
		this.handoverbillOrgCode = handoverbillOrgCode;
	}

	/**
	 * 交接到空运总调对应外场
	 * 如，广州空运总调对应的外场为‘花都运作部’
	 * */
	public String getHandOverbillTFROrgCode() {
		return handOverbillTFROrgCode;
	}

	public void setHandOverbillTFROrgCode(String handOverbillTFROrgCode) {
		this.handOverbillTFROrgCode = handOverbillTFROrgCode;
	}

	/**
	 * 获取制单部门电话
	 * @return
	 */
	public String getShipperContactPhone() {
		return shipperContactPhone;
	}
    /**
     * 设置制单部门电话
     * @param shipperContactPhone
     */
	public void setShipperContactPhone(String shipperContactPhone) {
		this.shipperContactPhone = shipperContactPhone;
	}
   
	
	
}