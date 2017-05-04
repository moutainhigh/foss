package com.deppon.foss.module.transfer.dubbo.api.define;

import java.util.Date;
import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 运单库存
 * @author 097457-foss-wangqiang
 * @date 2012-10-18 下午4:18:27
 */
public class WaybillStockEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 运单号
	 */
	private String waybillNO;
	/**
	 * 库存件数
	 */
	private Integer stockGoodsCount;
	/**
	 * 入库时间
	 */
	private Date inStockTime;
	/**
	 * 货区编号
	 */
	private String goodsAreaCode;
	/**
	 * 部门编号
	 */
	private String orgCode;
	/**
	 * 该票最后一件货物入库时间
	 */
	private Date lastInStockTime;
	/**
	 * 计划出发时间
	 */
	private Date planStartTime;
	/**
	 * 下一部门编号
	 */
	private String nextOrgCode;
	
	/** 散货、整车货区*/
	private String otherGoodsAreaGode;
	/** 产品*/
	private String productCode;
	/**
	* 库位件数
	*/
	private Integer positionCount;
	/**
	* 库位(库存管理页面是否按库存查询)
	*/
	private String position;
	/**
	* 提货方式
	*/
	private String receiveMethod;
	
	
	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNO() {
		return waybillNO;
	}
	
	/**
	 * 设置 运单号.
	 *
	 * @param waybillNO the new 运单号
	 */
	public void setWaybillNO(String waybillNO) {
		this.waybillNO = waybillNO;
	}
	
	/**
	 * 获取 入库时间.
	 *
	 * @return the 入库时间
	 */
	public Date getInStockTime() {
		return inStockTime;
	}
	
	/**
	 * 设置 入库时间.
	 *
	 * @param inStockTime the new 入库时间
	 */
	public void setInStockTime(Date inStockTime) {
		this.inStockTime = inStockTime;
	}
	
	/**
	 * 获取 货区编号.
	 *
	 * @return the 货区编号
	 */
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	
	/**
	 * 设置 货区编号.
	 *
	 * @param goodsAreaCode the new 货区编号
	 */
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}
	
	/**
	 * 获取 部门编号.
	 *
	 * @return the 部门编号
	 */
	public String getOrgCode() {
		return orgCode;
	}
	
	/**
	 * 设置 部门编号.
	 *
	 * @param orgCode the new 部门编号
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	/**
	 * 获取 库存件数.
	 *
	 * @return the 库存件数
	 */
	public Integer getStockGoodsCount() {
		return stockGoodsCount;
	}
	
	/**
	 * 设置 库存件数.
	 *
	 * @param stockGoodsCount the new 库存件数
	 */
	public void setStockGoodsCount(Integer stockGoodsCount) {
		this.stockGoodsCount = stockGoodsCount;
	}
	
	/**
	 * 获取 该票最后一件货物入库时间.
	 *
	 * @return the 该票最后一件货物入库时间
	 */
	public Date getLastInStockTime() {
		return lastInStockTime;
	}
	
	/**
	 * 设置 该票最后一件货物入库时间.
	 *
	 * @param lastInStockTime the new 该票最后一件货物入库时间
	 */
	public void setLastInStockTime(Date lastInStockTime) {
		this.lastInStockTime = lastInStockTime;
	}
	
	/**
	 * 获取 计划出发时间.
	 *
	 * @return the 计划出发时间
	 */
	public Date getPlanStartTime() {
		return planStartTime;
	}
	
	/**
	 * 设置 计划出发时间.
	 *
	 * @param planStartTime the new 计划出发时间
	 */
	public void setPlanStartTime(Date planStartTime) {
		this.planStartTime = planStartTime;
	}
	
	/**
	 * 获取 下一部门编号.
	 *
	 * @return the 下一部门编号
	 */
	public String getNextOrgCode() {
		return nextOrgCode;
	}
	
	/**
	 * 设置 下一部门编号.
	 *
	 * @param nextOrgCode the new 下一部门编号
	 */
	public void setNextOrgCode(String nextOrgCode) {
		this.nextOrgCode = nextOrgCode;
	}

	public String getOtherGoodsAreaGode() {
		return otherGoodsAreaGode;
	}

	public void setOtherGoodsAreaGode(String otherGoodsAreaGode) {
		this.otherGoodsAreaGode = otherGoodsAreaGode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	* @return
	* @description 获取库位件数
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-3 上午10:27:14
	*/
	public Integer getPositionCount() {
		return positionCount;
	}

	/**
	* @param positionCount 设置库位件数
	* @description 
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-3 上午10:27:31
	*/
	public void setPositionCount(Integer positionCount) {
		this.positionCount = positionCount;
	}

	/**
	* @return
	* @description  获取库位(库存管理页面是否按库存查询)
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-13 上午8:54:59
	*/
	public String getPosition() {
		return position;
	}

	/**
	* @param position
	* @description 设置库位(库存管理页面是否按库存查询)
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-13 上午8:55:17
	*/
	public void setPosition(String position) {
		this.position = position;
	}
	
	
	/**
	* @return
	* @description 获取提货方式
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-23 上午9:06:00
	*/
	public String getReceiveMethod() {
		return receiveMethod;
	}

	/**
	* @param receiveMethod 设置提货方式
	* @description 
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-23 上午9:06:56
	*/
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}
}