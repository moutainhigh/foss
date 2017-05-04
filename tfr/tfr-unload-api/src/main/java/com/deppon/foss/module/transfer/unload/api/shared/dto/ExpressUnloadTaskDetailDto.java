package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description 快递-卸车任务明细
 * @version 1.0
 * @author 328768-foss-gaojianfu
 * @update 2016年4月27日 下午2:07:06
 */
public class ExpressUnloadTaskDetailDto implements Serializable{

	
	/**
	 * @fields serialVersionUID
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月27日 下午2:10:27
	 * @version V1.0
	 */
	
	private static final long serialVersionUID = 1L;

	/**卸车任务编号*/
	private String unloadTaskNo;
	
	/**交接单编号*/
	private String handoverBillNo;
	
	/** 件数 */
	private Long goodsQty;
	
	/** 重量 */
	private BigDecimal weight;

	/** 体积 */
	private BigDecimal volume;
	
	/** 产品类型 */
	private String productType;
	
	/** 到达部门编号 */
	private String destOrgCode2;

	/**
	 * @description 获取 卸车任务编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午8:11:18
	 */
	public String getUnloadTaskNo() {
		return unloadTaskNo;
	}

	/**
	 * @description 设置 卸车任务编号
	 * @param unloadTaskNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午8:12:00
	 */
	public void setUnloadTaskNo(String unloadTaskNo) {
		this.unloadTaskNo = unloadTaskNo;
	}

	/**
	 * @description 获取 交接单编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午8:11:18
	 */
	public String getHandoverBillNo() {
		return handoverBillNo;
	}

	/**
	 * @description 设置 交接单编号
	 * @param handoverBillNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午8:12:00
	 */
	public void setHandoverBillNo(String handoverBillNo) {
		this.handoverBillNo = handoverBillNo;
	}

	/**
 	 * @description 获取 件数
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午8:11:18
	 */
	public Long getGoodsQty() {
		return goodsQty;
	}

	/**
	 * @description 设置 件数
	 * @param goodsQty
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午8:12:00
	 */
	public void setGoodsQty(Long goodsQty) {
		this.goodsQty = goodsQty;
	}

	/**
	 * @description 获取 重量
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午8:11:18
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * @description 设置 重量
	 * @param weight
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午8:12:00
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * @description 获取 体积
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午8:11:18
	 */
	public BigDecimal getVolume() {
		return volume;
	}

	/**
	 * @description 设置 体积
	 * @param volume
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午8:12:00
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	
	/**
	 * @description 获取 产品类型
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午8:11:18
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * @description 设置 产品类型
	 * @param productType
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午8:12:00
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**
	 * @description 获取 到达部门编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午8:11:18
	 */
	public String getDestOrgCode2() {
		return destOrgCode2;
	}

	/**
	 * @description 设置 到达部门编号
	 * @param destOrgCode2
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午8:12:00
	 */
	public void setDestOrgCode2(String destOrgCode2) {
		this.destOrgCode2 = destOrgCode2;
	}

}
