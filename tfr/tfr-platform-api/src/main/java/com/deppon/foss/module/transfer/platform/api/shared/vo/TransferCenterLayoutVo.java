/**
 * Project Name:tfr-platform-api
 * File Name:TransferCenterLayoutVo.java
 * Package Name:com.deppon.foss.module.transfer.platform.api.shared.vo
 * Date:2014年12月30日上午8:59:33
 * Copyright (c) 2014, shiwei@outlook.com All Rights Reserved.
 *
*/

package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.dto.TransferCenterUnitDto;

/**
 * ClassName:TransferCenterLayoutVo <br/>
 * Reason:	 场内布局图Vo. <br/>
 * Date:     2014年12月30日 上午8:59:33 <br/>
 * @author   045923
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class TransferCenterLayoutVo {
	
	/**
	 * 组织是否为外场
	 */
	private String beTransferCenter;
	
	/**
	 * 外场code
	 */
	private String orgCode;
	
	/**
	 * 库区list
	 */
	private List<TransferCenterUnitDto> goodsAreaList;
	
	/**
	 * 月台list
	 */
	private List<TransferCenterUnitDto> platformList;
	
	/**
	 * 待叉区list
	 */
	private List<TransferCenterUnitDto> transferAreaList;
	
	/**
	 * 转运场所有单位list
	 */
	private List<TransferCenterUnitDto> unitsList;

	public List<TransferCenterUnitDto> getUnitsList() {
		return unitsList;
	}

	public void setUnitsList(List<TransferCenterUnitDto> unitsList) {
		this.unitsList = unitsList;
	}

	public String getBeTransferCenter() {
		return beTransferCenter;
	}

	public void setBeTransferCenter(String beTransferCenter) {
		this.beTransferCenter = beTransferCenter;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public List<TransferCenterUnitDto> getGoodsAreaList() {
		return goodsAreaList;
	}

	public void setGoodsAreaList(List<TransferCenterUnitDto> goodsAreaList) {
		this.goodsAreaList = goodsAreaList;
	}

	public List<TransferCenterUnitDto> getPlatformList() {
		return platformList;
	}

	public void setPlatformList(List<TransferCenterUnitDto> platformList) {
		this.platformList = platformList;
	}

	public List<TransferCenterUnitDto> getTransferAreaList() {
		return transferAreaList;
	}

	public void setTransferAreaList(List<TransferCenterUnitDto> transferAreaList) {
		this.transferAreaList = transferAreaList;
	}
	
}

