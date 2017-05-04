package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 零担服务范围信息
 * 
 * @ClassName: LtlServiceScopeDto
 * @author 200664-yangjinheng
 * @date 2014年9月30日 下午12:41:24
 */
public class LtlServiceScopeDto extends BaseEntity {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 行政区域编码.
	 */
	private String code;

	/**
	 * 区域全称.
	 */
	private String name;

	/**
	 * 简称.
	 */
	private String simpleName;

	/**
	 * 可用名称.
	 */
	private String availableName;

	/**
	 * 上级行政区域编码.
	 */
	private String parentDistrictCode;

	/**
	 * 上级行政区域名称.
	 */
	private String parentDistrictName;

	/**
	 * 虚拟行政区域.
	 */
	private String virtualDistrictId;

	/**
	 * 行政区域级别.
	 */
	private String degree;

	/**
	 * 是否启用.
	 */
	private String active;

	/**
	 * 拼音.
	 */
	private String pinYin;

	/**
	 * 拼音简码.
	 */
	private String pinYinAbbr;

	/**
	 * Tree展开的全路径，由行政区域的code拼接起来，分隔符是.
	 */
	private String fullCodePath;

	/**
	 * 传给GIS的Name路径，由行政区域的name拼接起来，分隔符是-
	 */
	private String fullNamePath;

	/**
	 * 部门服务区坐标编号
	 */
	private String depCoordinate;

	/**
	 * 派送区坐标编号
	 */
	private String deliveryCoordinate;

	public String getDepCoordinate() {
		return depCoordinate;
	}

	public void setDepCoordinate(String depCoordinate) {
		this.depCoordinate = depCoordinate;
	}

	public String getDeliveryCoordinate() {
		return deliveryCoordinate;
	}

	public void setDeliveryCoordinate(String deliveryCoordinate) {
		this.deliveryCoordinate = deliveryCoordinate;
	}

	public String getFullCodePath() {
		return fullCodePath;
	}

	public void setFullCodePath(String fullCodePath) {
		this.fullCodePath = fullCodePath;
	}

	public String getFullNamePath() {
		return fullNamePath;
	}

	public void setFullNamePath(String fullNamePath) {
		this.fullNamePath = fullNamePath;
	}

	/**
	 * 获取 行政区域编码.
	 * 
	 * @return the 行政区域编码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置 行政区域编码.
	 * 
	 * @param code
	 *            the new 行政区域编码
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取 区域全称.
	 * 
	 * @return the 区域全称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置 区域全称.
	 * 
	 * @param name
	 *            the new 区域全称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取 简称.
	 * 
	 * @return the 简称
	 */
	public String getSimpleName() {
		return simpleName;
	}

	/**
	 * 设置 简称.
	 * 
	 * @param simpleName
	 *            the new 简称
	 */
	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	/**
	 * 获取 可用名称.
	 * 
	 * @return the 可用名称
	 */
	public String getAvailableName() {
		return availableName;
	}

	/**
	 * 设置 可用名称.
	 * 
	 * @param availableName
	 *            the new 可用名称
	 */
	public void setAvailableName(String availableName) {
		this.availableName = availableName;
	}

	/**
	 * 获取 上级行政区域编码.
	 * 
	 * @return the 上级行政区域编码
	 */
	public String getParentDistrictCode() {
		return parentDistrictCode;
	}

	/**
	 * 设置 上级行政区域编码.
	 * 
	 * @param parentDistrictCode
	 *            the new 上级行政区域编码
	 */
	public void setParentDistrictCode(String parentDistrictCode) {
		this.parentDistrictCode = parentDistrictCode;
	}

	/**
	 * 获取 上级行政区域名称.
	 * 
	 * @return the 上级行政区域名称
	 */
	public String getParentDistrictName() {
		return parentDistrictName;
	}

	/**
	 * 设置 上级行政区域名称.
	 * 
	 * @param parentDistrictName
	 *            the new 上级行政区域名称
	 */
	public void setParentDistrictName(String parentDistrictName) {
		this.parentDistrictName = parentDistrictName;
	}

	/**
	 * 获取 虚拟行政区域.
	 * 
	 * @return the 虚拟行政区域
	 */
	public String getVirtualDistrictId() {
		return virtualDistrictId;
	}

	/**
	 * 设置 虚拟行政区域.
	 * 
	 * @param virtualDistrictId
	 *            the new 虚拟行政区域
	 */
	public void setVirtualDistrictId(String virtualDistrictId) {
		this.virtualDistrictId = virtualDistrictId;
	}

	/**
	 * 获取 行政区域级别.
	 * 
	 * @return the 行政区域级别
	 */
	public String getDegree() {
		return degree;
	}

	/**
	 * 设置 行政区域级别.
	 * 
	 * @param degree
	 *            the new 行政区域级别
	 */
	public void setDegree(String degree) {
		this.degree = degree;
	}

	/**
	 * 获取 是否启用.
	 * 
	 * @return the 是否启用
	 */
	public String getActive() {
		return active;
	}

	/**
	 * 设置 是否启用.
	 * 
	 * @param active
	 *            the new 是否启用
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * 获取 拼音.
	 * 
	 * @return pinYin
	 */
	public String getPinYin() {
		return pinYin;
	}

	/**
	 * 设置 拼音.
	 * 
	 * @param pinYin
	 *            the new 拼音
	 */
	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}

	/**
	 * 获取 拼音简码.
	 * 
	 * @return pinYinAbbr
	 */
	public String getPinYinAbbr() {
		return pinYinAbbr;
	}

	/**
	 * 设置 拼音简码.
	 * 
	 * @param pinYinAbbr
	 *            the new 拼音简码
	 */
	public void setPinYinAbbr(String pinYinAbbr) {
		this.pinYinAbbr = pinYinAbbr;
	}

}
