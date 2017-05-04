package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 子母件Dto：查询子母件
 * @author 231438-chenjunying
 * @date 2015-08-20 下午14:03:31
 * @since
 * @version
 */
public class CzmSignDto implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 查询使用的单号
	 */
	private String queryWaybillNo;
	/**
	 * 子母件单号列表
	 */
	private List<String> czmWaybilllist;
	/**
	 * 在库子母件单号
	 */
	private List<String> stockInWaybillNoList;
	/**
	 * 未在库子母件单号
	 */
	private List<String> stockNotInWaybillNoList;
	/**
	 * 子母件总票数
	 */
	private Integer czmGoodsQtyTotal;
	/**
	 * 子母件在库票数
	 */
	private Integer czmGoodsQtyInStock;
	/**
	 * 子母件签收限制 配置参数code
	 */
	private String czmSignDataCode;
	/**
	 * 子母件签收限制 配置参数值
	 */
	private int czmSignLimit ;

	public String getQueryWaybillNo() {
		return queryWaybillNo;
	}
	public void setQueryWaybillNo(String queryWaybillNo) {
		this.queryWaybillNo = queryWaybillNo;
	}
	public List<String> getStockInWaybillNoList() {
		return stockInWaybillNoList;
	}
	public void setStockInWaybillNoList(List<String> stockInWaybillNoList) {
		this.stockInWaybillNoList = stockInWaybillNoList;
	}
	public List<String> getStockNotInWaybillNoList() {
		return stockNotInWaybillNoList;
	}
	public void setStockNotInWaybillNoList(List<String> stockNotInWaybillNoList) {
		this.stockNotInWaybillNoList = stockNotInWaybillNoList;
	}
	public List<String> getCzmWaybilllist() {
		return czmWaybilllist;
	}
	public void setCzmWaybilllist(List<String> czmWaybilllist) {
		this.czmWaybilllist = czmWaybilllist;
	}
	public Integer getCzmGoodsQtyTotal() {
		return czmGoodsQtyTotal;
	}
	public void setCzmGoodsQtyTotal(Integer czmGoodsQtyTotal) {
		this.czmGoodsQtyTotal = czmGoodsQtyTotal;
	}
	public Integer getCzmGoodsQtyInStock() {
		return czmGoodsQtyInStock;
	}
	public void setCzmGoodsQtyInStock(Integer czmGoodsQtyInStock) {
		this.czmGoodsQtyInStock = czmGoodsQtyInStock;
	}
	public String getCzmSignDataCode() {
		return czmSignDataCode;
	}
	public void setCzmSignDataCode(String czmSignDataCode) {
		this.czmSignDataCode = czmSignDataCode;
	}
	public int getCzmSignLimit() {
		return czmSignLimit;
	}
	public void setCzmSignLimit(int czmSignLimit) {
		this.czmSignLimit = czmSignLimit;
	}
}
