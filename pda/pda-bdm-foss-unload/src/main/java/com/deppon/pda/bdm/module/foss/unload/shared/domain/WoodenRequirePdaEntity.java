package com.deppon.pda.bdm.module.foss.unload.shared.domain;

import java.util.List;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;



public class WoodenRequirePdaEntity extends ScanMsgEntity{

	/**  
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 1L;

	
	//操作员所属外场
	private String outerCode;
	// 货物总体积
	private double goodsVolumeTotal;
	// 货物总重量
    private double goodsWeightTotal;
	// 是否大件上楼加收
	private String isBigUp;
	// 500KG到1000KG超重件数
	private Integer fhToOtOverQty;
    //1000KG到2000KG超重件数
	private Integer otToTtOverQty;
	/**打木托个数*/
	private Integer woodenStockNum;
	//实体明细
	private List<UnldWoodenRequireEntity> woodenRequireEntityLis ;
	
	
	public String getOuterCode() {
		return outerCode;
	}
	public void setOuterCode(String outerCode) {
		this.outerCode = outerCode;
	}
	public double getGoodsWeightTotal() {
		return goodsWeightTotal;
	}
	public void setGoodsWeightTotal(double goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
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
	public List<UnldWoodenRequireEntity> getWoodenRequireEntityLis() {
		return woodenRequireEntityLis;
	}
	public void setWoodenRequireEntityLis(
			List<UnldWoodenRequireEntity> woodenRequireEntityLis) {
		this.woodenRequireEntityLis = woodenRequireEntityLis;
	}
	public double getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}
	public void setGoodsVolumeTotal(double goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}
	
	/**
	 * @作者：xiaolongwu
	 * @描述：打木托
	 * @返回值：打木托个数
	 * @时间：2014-12-26 上午11:41:09
	 */
	public Integer getWoodenStockNum() {
		return woodenStockNum;
	}
	public void setWoodenStockNum(Integer woodenStockNum) {
		this.woodenStockNum = woodenStockNum;
	}
	
	

    
    
	
}
