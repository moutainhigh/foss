package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.DptAreaDensityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForkAreaDensityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrAreaDensityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrDensityPeakEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.DensityQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.DensityTrendDto;

public class DensityVo implements Serializable {

	private static final long serialVersionUID = 6572999603716073467L;

	/**
	 * 经营本部编码
	 */
	private String hqCode;

	/**
	 * 经营本部名称
	 */
	private String hqName;

	/**
	 * 外场编码
	 */
	private String tfrCtrCode;

	/**
	 * 外场名称
	 */
	private String tfrCtrName;

	/**
	 * 查询条件
	 */
	private DensityQcDto densityQcDto;

	/**
	 * 外场密度峰值
	 */
	private List<TfrCtrDensityPeakEntity> tfrCtrDensityPeakEntities;

	/**
	 * 待叉区密度
	 */
	private List<ForkAreaDensityEntity> forkAreaDensityEntities;

	/**
	 * 派送库区密度
	 */
	private List<DptAreaDensityEntity> dptAreaDensityEntities;

	/**
	 * 中转库区密度
	 */
	private List<TfrAreaDensityEntity> tfrAreaDensityEntities;

	/**
	 * 各种日，月密度趋势
	 */
	private List<DensityTrendDto> trendDtos;

	public String getHqCode() {
		return hqCode;
	}

	public void setHqCode(String hqCode) {
		this.hqCode = hqCode;
	}

	public String getHqName() {
		return hqName;
	}

	public void setHqName(String hqName) {
		this.hqName = hqName;
	}

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

	public DensityQcDto getDensityQcDto() {
		return densityQcDto;
	}

	public void setDensityQcDto(DensityQcDto densityQcDto) {
		this.densityQcDto = densityQcDto;
	}

	public List<TfrCtrDensityPeakEntity> getTfrCtrDensityPeakEntities() {
		return tfrCtrDensityPeakEntities;
	}

	public void setTfrCtrDensityPeakEntities(
			List<TfrCtrDensityPeakEntity> tfrCtrDensityPeakEntities) {
		this.tfrCtrDensityPeakEntities = tfrCtrDensityPeakEntities;
	}

	public List<ForkAreaDensityEntity> getForkAreaDensityEntities() {
		return forkAreaDensityEntities;
	}

	public void setForkAreaDensityEntities(
			List<ForkAreaDensityEntity> forkAreaDensityEntities) {
		this.forkAreaDensityEntities = forkAreaDensityEntities;
	}

	public List<DptAreaDensityEntity> getDptAreaDensityEntities() {
		return dptAreaDensityEntities;
	}

	public void setDptAreaDensityEntities(
			List<DptAreaDensityEntity> dptAreaDensityEntities) {
		this.dptAreaDensityEntities = dptAreaDensityEntities;
	}

	public List<TfrAreaDensityEntity> getTfrAreaDensityEntities() {
		return tfrAreaDensityEntities;
	}

	public void setTfrAreaDensityEntities(
			List<TfrAreaDensityEntity> tfrAreaDensityEntities) {
		this.tfrAreaDensityEntities = tfrAreaDensityEntities;
	}

	public List<DensityTrendDto> getTrendDtos() {
		return trendDtos;
	}

	public void setTrendDtos(List<DensityTrendDto> trendDtos) {
		this.trendDtos = trendDtos;
	}

}
