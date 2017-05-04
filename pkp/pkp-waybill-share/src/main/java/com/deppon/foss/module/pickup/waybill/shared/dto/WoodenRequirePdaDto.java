package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequireEntity;
public class WoodenRequirePdaDto {

	// 运单编号
	private String waybillNo;
	//操作员工号
	private String operatorCode;
	//操作员所属外场
	private String outerCode;
	// 货物总体积
	private BigDecimal goodsVolumeTotal;
	// 货物总重量
    private BigDecimal goodsWeightTotal;
	// 是否大件上楼加收
	private String isBigUp;
	// 500KG到1000KG超重件数
	private Integer fhToOtOverQty;
    //1000KG到2000KG超重件数
	private Integer otToTtOverQty;
	//打木托件数
	private Integer woodenStockNum;
	private List<WoodenRequireEntity> woodenRequireEntityLis ;
	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}


	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	public String getOuterCode() {
		return outerCode;
	}

	public void setOuterCode(String outerCode) {
		this.outerCode = outerCode;
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

	public List<WoodenRequireEntity> getWoodenRequireEntityLis() {
		return woodenRequireEntityLis;
	}

	public void setWoodenRequireEntityLis(
			List<WoodenRequireEntity> woodenRequireEntityLis) {
		this.woodenRequireEntityLis = woodenRequireEntityLis;
	}

	public Integer getWoodenStockNum() {
		return woodenStockNum;
	}

	public void setWoodenStockNum(Integer woodenStockNum) {
		this.woodenStockNum = woodenStockNum;
	}
    
	
	
	
}
