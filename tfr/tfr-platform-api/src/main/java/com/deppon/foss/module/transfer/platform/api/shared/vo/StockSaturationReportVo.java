package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.StockSaturationReportEntity;

/**
* @description 仓库饱和度数据监控报表VO
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年5月13日 下午4:55:34
*/
public class StockSaturationReportVo implements java.io.Serializable {

	
	/**
	* @fields serialVersionUID
	* @author 14022-foss-songjie
	* @update 2014年5月13日 下午4:55:31
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 实体对象
	* @fields stockSaturationReportDto
	* @author 14022-foss-songjie
	* @update 2014年5月13日 下午4:57:02
	* @version V1.0
	*/
	private StockSaturationReportEntity stockSaturationReportDto;
	
	/**
	 * 实体对象的集合
	* @fields stockSaturationReportList
	* @author 14022-foss-songjie
	* @update 2014年5月13日 下午4:57:05
	* @version V1.0
	*/
	private List<StockSaturationReportEntity> stockSaturationReportList;

	
	/**
	* @description 实体对象
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月13日 下午4:58:22
	*/
	public StockSaturationReportEntity getStockSaturationReportDto() {
		return stockSaturationReportDto;
	}

	
	/**
	* @description 实体对象
	* @param stockSaturationReportDto
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月13日 下午4:58:27
	*/
	public void setStockSaturationReportDto(
			StockSaturationReportEntity stockSaturationReportDto) {
		this.stockSaturationReportDto = stockSaturationReportDto;
	}

	
	/**
	* @description 实体对象的集合
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月13日 下午4:58:36
	*/
	public List<StockSaturationReportEntity> getStockSaturationReportList() {
		return stockSaturationReportList;
	}

	
	/**
	* @description 实体对象的集合
	* @param stockSaturationReportList
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月13日 下午4:58:40
	*/
	public void setStockSaturationReportList(
			List<StockSaturationReportEntity> stockSaturationReportList) {
		this.stockSaturationReportList = stockSaturationReportList;
	}
	
	
	


}
