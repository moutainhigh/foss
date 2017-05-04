package com.deppon.foss.module.pickup.predeliver.api.shared.util;

/** 
 * @ClassName: PropertyFactory 
 * @Description: Gis配置信息
 * @author 237982-foss-fangwenjun 
 * @date 2015-5-8 下午4:01:40 
 *  
 */
public class PropertyValues {

	/**
	 * gis匹配小区Code
	 */
	private String gisMatchCommunityUrl;
	
	/**
	 * gis自动排序接口
	 */
	private String gisAutoSortUrl;
	
	/**
	 * gis保存作废更新地址坐标信息
	 */
	private String gisSaveDelUpdateCroodUrl;
	
	/**
	 * 可视化自动排序
	 */
	private String gisWaybillAutoSortUrl;

	/**
	 * 获取gisMatchCommunityUrl
	 * @return the gisMatchCommunityUrl
	 */
	public String getGisMatchCommunityUrl() {
		return gisMatchCommunityUrl;
	}

	/**
	 * 设置gisMatchCommunityUrl
	 * @param gisMatchCommunityUrl 要设置的gisMatchCommunityUrl
	 */
	public void setGisMatchCommunityUrl(String gisMatchCommunityUrl) {
		this.gisMatchCommunityUrl = gisMatchCommunityUrl;
	}

	public String getGisAutoSortUrl() {
		return gisAutoSortUrl;
	}

	public void setGisAutoSortUrl(String gisAutoSortUrl) {
		this.gisAutoSortUrl = gisAutoSortUrl;
	}

	public String getGisSaveDelUpdateCroodUrl() {
		return gisSaveDelUpdateCroodUrl;
	}

	public void setGisSaveDelUpdateCroodUrl(String gisSaveDelUpdateCroodUrl) {
		this.gisSaveDelUpdateCroodUrl = gisSaveDelUpdateCroodUrl;
	}

	public String getGisWaybillAutoSortUrl() {
		return gisWaybillAutoSortUrl;
	}

	public void setGisWaybillAutoSortUrl(String gisWaybillAutoSortUrl) {
		this.gisWaybillAutoSortUrl = gisWaybillAutoSortUrl;
	}
	
}
