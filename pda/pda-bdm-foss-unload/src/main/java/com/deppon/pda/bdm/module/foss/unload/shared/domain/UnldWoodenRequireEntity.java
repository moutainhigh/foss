package com.deppon.pda.bdm.module.foss.unload.shared.domain;

import java.util.Date;


public class UnldWoodenRequireEntity {
	private  String id;
	
	private double goodsWeightTotal;
	private double goodsVolumeTotal;
	private String wblCode;	
	private String userCode;
	//操作员所属外场
	private String outerCode;
	// 件数
	private Integer goodsNum;
	//长
	private double length ;
	
	//宽
	private double  width;
	
	//高
	private double  height ;
	
	//包装类型    stand=打木箱; box=打木架； noPack=没打木箱木架
	private String packType;
	
	// 进入界面时间	 
	private Date scanTime;
	
	//上传时间	 
	private Date uploadTime;
	
	// 是否大件上楼加收
	private String isBigUp;
	
	// 500KG到1000KG超重件数
	private Integer fhToOtOverQty;
	
    //1000KG到2000KG超重件数
	private Integer otToTtOverQty;
	
	//打木托个数
	private Integer woodenStockNum;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getPackType() {
		return packType;
	}

	public void setPackType(String packType) {
		this.packType = packType;
	}

	public double getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	public void setGoodsWeightTotal(double goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	public String getWblCode() {
		return wblCode;
	}

	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getOuterCode() {
		return outerCode;
	}

	public void setOuterCode(String outerCode) {
		this.outerCode = outerCode;
	}

	public double getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	public void setGoodsVolumeTotal(double goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	public Date getScanTime() {
		return scanTime;
	}

	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getIsBigUp() {
		return isBigUp;
	}

	public void setIsBigUp(String isBigUp) {
		this.isBigUp = isBigUp;
	}

	public Integer getFhToOtOverQty() {
		return fhToOtOverQty;
	}

	public void setFhToOtOverQty(Integer fhToOtOverQty) {
		this.fhToOtOverQty = fhToOtOverQty;
	}

	public Integer getOtToTtOverQty() {
		return otToTtOverQty;
	}

	public void setOtToTtOverQty(Integer otToTtOverQty) {
		this.otToTtOverQty = otToTtOverQty;
	}

	public Integer getWoodenStockNum() {
		return woodenStockNum;
	}

	public void setWoodenStockNum(Integer woodenStockNum) {
		this.woodenStockNum = woodenStockNum;
	}






}
