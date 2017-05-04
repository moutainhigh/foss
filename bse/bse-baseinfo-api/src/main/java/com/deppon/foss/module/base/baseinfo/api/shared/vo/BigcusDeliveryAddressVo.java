package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigcusDeliveryAddressEntity;


/**
 *  零担大客户派送地址库
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:232607,date:2016-3-25 下午4:54:36,content:TODO </p>
 * @author 232607 
 * @date 2016-3-25 下午4:54:36
 * @since
 * @version
 */
public class BigcusDeliveryAddressVo {
	/**
	 * 区域实体（用于前台查询条件）
	 */
	private BigcusDeliveryAddressEntity bigcusDeliveryAddressEntity;
	/**
	 * 区域实体列表（用于分页查询结果返回给前台）
	 */
	private List<BigcusDeliveryAddressEntity> bigcusDeliveryAddressList;
	/**
	 * 用来批量作废的code数组
	 */
	private String[] codes;
	
	/**
	 * 上级区域编码
	 */
	private String parentDistrictCode;
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 以下为set get 方法
	 */
	public BigcusDeliveryAddressEntity getBigcusDeliveryAddressEntity() {
		return bigcusDeliveryAddressEntity;
	}
	public void setBigcusDeliveryAddressEntity(
			BigcusDeliveryAddressEntity bigcusDeliveryAddressEntity) {
		this.bigcusDeliveryAddressEntity = bigcusDeliveryAddressEntity;
	}
	public List<BigcusDeliveryAddressEntity> getBigcusDeliveryAddressList() {
		return bigcusDeliveryAddressList;
	}
	public void setBigcusDeliveryAddressList(
			List<BigcusDeliveryAddressEntity> bigcusDeliveryAddressList) {
		this.bigcusDeliveryAddressList = bigcusDeliveryAddressList;
	}
	public String[] getCodes() {
		return codes;
	}
	public void setCodes(String[] codes) {
		this.codes = codes;
	}
	public String getParentDistrictCode() {
		return parentDistrictCode;
	}
	public void setParentDistrictCode(String parentDistrictCode) {
		this.parentDistrictCode = parentDistrictCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
