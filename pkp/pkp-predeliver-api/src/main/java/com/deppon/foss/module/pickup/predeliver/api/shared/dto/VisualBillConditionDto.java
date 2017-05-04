package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

/**
 * 可视化排单查询条件DTO
 * @author 239284
 *
 */
public class VisualBillConditionDto {

	private String waybillNo; // 运单号
	private String[] waybillNos;
	private String deliverDate; // 送货日期
	private String productCode; // 运输性质
	private String[] productCodes; //运输性质数组
	private String goodsStatus; // 货物状态
	private String vehicleType; // 所属车队组
	private String deliverGrandArea; // 送货大区
	private String[] deliverGrandAreas;
	private String deliverSmallArea; // 送货小区
	private String[] deliverSmallAreas;
	private String specialNoType; // 特殊运单
	private String[]  specialAddressType; //特殊运单特殊地址类型
	private String lateNo; // 晚交运单
	private String tallymanReturnReason; // 理货员退回
	
	//新加的五个复选框
	private String deliverInga;//进仓货
	private String noDeliverInga;//非进仓货
	private String uitraLongDelivery;//超远派送
	private String isExhibition;//会展货
	private String pieceInspection;//逐件验货
	
	
	private String 	active; //交单状态
	
	//聚合用到的查询条件
	private String provCode; //省
	private String cityCode; //市
	private String distCode; //区
	
	private String queryType;// 查询类型
  
	/**
     * 库区
     */
    private String goodsAreaCode;
    
    /**
     * 部门角色类型
     */
    private int OrgRoleType;
    /**
     * 最终配载部门
     */
    private String lastLoadOrgCode;
    
    /**
     * 最后库存code
     */
    private String endStockOrgCode;
    
	
	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	public String[] getWaybillNos() {
		return waybillNos;
	}

	public void setWaybillNos(String[] waybillNos) {
		this.waybillNos = waybillNos;
	}

	public String getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(String deliverDate) {
		this.deliverDate = deliverDate;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getDeliverGrandArea() {
		return deliverGrandArea;
	}

	public void setDeliverGrandArea(String deliverGrandArea) {
		this.deliverGrandArea = deliverGrandArea;
	}

	public String getDeliverSmallArea() {
		return deliverSmallArea;
	}

	public void setDeliverSmallArea(String deliverSmallArea) {
		this.deliverSmallArea = deliverSmallArea;
	}

	public String getSpecialNoType() {
		return specialNoType;
	}

	public void setSpecialNoType(String specialNoType) {
		this.specialNoType = specialNoType;
	}

	public String getLateNo() {
		return lateNo;
	}

	public void setLateNo(String lateNo) {
		this.lateNo = lateNo;
	}

	public String getTallymanReturnReason() {
		return tallymanReturnReason;
	}

	public void setTallymanReturnReason(String tallymanReturnReason) {
		this.tallymanReturnReason = tallymanReturnReason;
	}

	public String[] getDeliverGrandAreas() {
		return deliverGrandAreas;
	}

	public void setDeliverGrandAreas(String[] deliverGrandAreas) {
		this.deliverGrandAreas = deliverGrandAreas;
	}

	public String[] getDeliverSmallAreas() {
		return deliverSmallAreas;
	}

	public void setDeliverSmallAreas(String[] deliverSmallAreas) {
		this.deliverSmallAreas = deliverSmallAreas;
	}

	public String getProvCode() {
		return provCode;
	}

	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getDistCode() {
		return distCode;
	}

	public void setDistCode(String distCode) {
		this.distCode = distCode;
	}

	public String[] getSpecialAddressType() {
		return specialAddressType;
	}

	public void setSpecialAddressType(String[] specialAddressType) {
		this.specialAddressType = specialAddressType;
	}

	public String[] getProductCodes() {
		return productCodes;
	}

	public void setProductCodes(String[] productCodes) {
		this.productCodes = productCodes;
	}

	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}

	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}

	public int getOrgRoleType() {
		return OrgRoleType;
	}

	public void setOrgRoleType(int orgRoleType) {
		OrgRoleType = orgRoleType;
	}

	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	public String getEndStockOrgCode() {
		return endStockOrgCode;
	}

	public void setEndStockOrgCode(String endStockOrgCode) {
		this.endStockOrgCode = endStockOrgCode;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getDeliverInga() {
		return deliverInga;
	}

	public void setDeliverInga(String deliverInga) {
		this.deliverInga = deliverInga;
	}

	public String getNoDeliverInga() {
		return noDeliverInga;
	}

	public void setNoDeliverInga(String noDeliverInga) {
		this.noDeliverInga = noDeliverInga;
	}

	public String getUitraLongDelivery() {
		return uitraLongDelivery;
	}

	public void setUitraLongDelivery(String uitraLongDelivery) {
		this.uitraLongDelivery = uitraLongDelivery;
	}

	public String getIsExhibition() {
		return isExhibition;
	}

	public void setIsExhibition(String isExhibition) {
		this.isExhibition = isExhibition;
	}

	public String getPieceInspection() {
		return pieceInspection;
	}

	public void setPieceInspection(String pieceInspection) {
		this.pieceInspection = pieceInspection;
	}
	
}
