package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.StockDuration;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockDurationDispatch;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockDurationTransfer;
import com.deppon.foss.module.transfer.platform.api.shared.dto.KeyValueDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.StockDurationQcDto;

public class StockDurationVo implements Serializable {

	private static final long serialVersionUID = -915852401028955463L;

	/**
	 * 外场编码
	 */
	private String tfrCtrCode;

	/**
	 * 外场名称
	 */
	private String tfrCtrName;

	/**
	 * 提货方式
	 */
	private List<KeyValueDto> receiveMethods;

	/**
	 * 查询条件
	 */
	private StockDurationQcDto stockDurationQcDto;

	/**
	 * 快递日数据汇总
	 */
	private List<StockDuration> expDays;

	/**
	 * 快递月数据汇总
	 */
	private List<StockDuration> expMonths;

	/**
	 * 零担日数据汇总
	 */
	private List<StockDuration> ltcDays;

	/**
	 * 零担月数据汇总
	 */
	private List<StockDuration> ltcMonths;

	/**
	 * 快递中转数据
	 */
	private List<StockDurationTransfer> tfrExps;

	/**
	 * 零担中转数据
	 */
	private List<StockDurationTransfer> tfrLtcs;

	/**
	 * 快递派送数据
	 */
	private List<StockDurationDispatch> dptExps;

	/**
	 * 零担派送数据
	 */
	private List<StockDurationDispatch> dptLtcs;

	public String getTfrCtrCode() {
		return tfrCtrCode;
	}

	public void setTfrCtrCode(String tfrCtrCode) {
		this.tfrCtrCode = tfrCtrCode;
	}

	public String getTfrCtrName() {
		return tfrCtrName;
	}

	public void setTfrCtrName(String tfrCtrName) {
		this.tfrCtrName = tfrCtrName;
	}

	public StockDurationQcDto getStockDurationQcDto() {
		return stockDurationQcDto;
	}

	public void setStockDurationQcDto(StockDurationQcDto stockDurationQcDto) {
		this.stockDurationQcDto = stockDurationQcDto;
	}

	public List<StockDuration> getExpDays() {
		return expDays;
	}

	public void setExpDays(List<StockDuration> expDays) {
		this.expDays = expDays;
	}

	public List<StockDuration> getExpMonths() {
		return expMonths;
	}

	public void setExpMonths(List<StockDuration> expMonths) {
		this.expMonths = expMonths;
	}

	public List<StockDuration> getLtcDays() {
		return ltcDays;
	}

	public void setLtcDays(List<StockDuration> ltcDays) {
		this.ltcDays = ltcDays;
	}

	public List<StockDuration> getLtcMonths() {
		return ltcMonths;
	}

	public void setLtcMonths(List<StockDuration> ltcMonths) {
		this.ltcMonths = ltcMonths;
	}

	public List<StockDurationTransfer> getTfrExps() {
		return tfrExps;
	}

	public void setTfrExps(List<StockDurationTransfer> tfrExps) {
		this.tfrExps = tfrExps;
	}

	public List<StockDurationTransfer> getTfrLtcs() {
		return tfrLtcs;
	}

	public void setTfrLtcs(List<StockDurationTransfer> tfrLtcs) {
		this.tfrLtcs = tfrLtcs;
	}

	public List<StockDurationDispatch> getDptExps() {
		return dptExps;
	}

	public void setDptExps(List<StockDurationDispatch> dptExps) {
		this.dptExps = dptExps;
	}

	public List<StockDurationDispatch> getDptLtcs() {
		return dptLtcs;
	}

	public void setDptLtcs(List<StockDurationDispatch> dptLtcs) {
		this.dptLtcs = dptLtcs;
	}

	public List<KeyValueDto> getReceiveMethods() {
		return receiveMethods;
	}

	public void setReceiveMethods(List<KeyValueDto> receiveMethods) {
		this.receiveMethods = receiveMethods;
	}

}
