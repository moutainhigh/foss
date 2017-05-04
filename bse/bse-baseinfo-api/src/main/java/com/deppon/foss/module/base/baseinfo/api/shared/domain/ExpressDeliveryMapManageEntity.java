package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 快递派送电子地图管理的实体类
 * 
 * @author 187862-dujunhui
 * @date 2014-12-18 下午4:40:52
 * @since
 * @version v1.0
 */
public class ExpressDeliveryMapManageEntity extends BaseEntity {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -318195069178756282L;
	// ID
	private String id;
	// 营业部编码
	private String salesDepartmentCode;
	// 营业部名称
	private String salesDepartmentName;
	// 行政区域编码
	private String districtCode;
	// 行政区域名称
	private String districtName;
	// 省份编码
	private String provCode;
	// 省份名称
	private String provName;
	// 城市编码
	private String cityCode;
	// 城市名称
	private String cityName;
	// 区县编码
	private String countyCode;
	// 区县名称
	private String countyName;
	// 审核状态
	private String verifyState;
	// 申请开始时间
	private Date applyTimeBegin;
	// 申请结束时间
	private Date applyTimeEnd;
	// 快递员人数
	private int expressManNum;
	// 营业部服务面积
	private BigDecimal departServiceArea;
	// 申请人姓名
	private String applyManName;
	// 申请人工号
	private String applyManCode;
	// 申请时间
	private Date applyTime;
	// 审核人姓名
	private String verifyManName;
	// 审核人工号
	private String verifyManCode;
	// 审核时间
	private Date verifyTime;
	// 快递派送区域坐标
	private String expressDeliveryCoordinate;
	// 部门区域坐标
	private String deptCoordinate;
	// 是否有效
	private String active;
	// 版本号
	private Long versionNo;

	private String[] codeList;
	
	private String status;//用户区分查询未编辑状态的数据
	
	private String oldCoordinate;
	
	/**
	 * 备注
	 */
	private String note;
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSalesDepartmentCode() {
		return salesDepartmentCode;
	}

	public void setSalesDepartmentCode(String salesDepartmentCode) {
		this.salesDepartmentCode = salesDepartmentCode;
	}

	public String getSalesDepartmentName() {
		return salesDepartmentName;
	}

	public void setSalesDepartmentName(String salesDepartmentName) {
		this.salesDepartmentName = salesDepartmentName;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getProvCode() {
		return provCode;
	}

	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}

	public String getProvName() {
		return provName;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getVerifyState() {
		return verifyState;
	}

	public void setVerifyState(String verifyState) {
		this.verifyState = verifyState;
	}

	public Date getApplyTimeBegin() {
		return applyTimeBegin;
	}

	public void setApplyTimeBegin(Date applyTimeBegin) {
		this.applyTimeBegin = applyTimeBegin;
	}

	public Date getApplyTimeEnd() {
		return applyTimeEnd;
	}

	public void setApplyTimeEnd(Date applyTimeEnd) {
		this.applyTimeEnd = applyTimeEnd;
	}

	public int getExpressManNum() {
		return expressManNum;
	}

	public void setExpressManNum(int expressManNum) {
		this.expressManNum = expressManNum;
	}

	public BigDecimal getDepartServiceArea() {
		return departServiceArea;
	}

	public void setDepartServiceArea(BigDecimal departServiceArea) {
		this.departServiceArea = departServiceArea;
	}

	public String getApplyManName() {
		return applyManName;
	}

	public void setApplyManName(String applyManName) {
		this.applyManName = applyManName;
	}

	public String getApplyManCode() {
		return applyManCode;
	}

	public void setApplyManCode(String applyManCode) {
		this.applyManCode = applyManCode;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getVerifyManName() {
		return verifyManName;
	}

	public void setVerifyManName(String verifyManName) {
		this.verifyManName = verifyManName;
	}

	public String getVerifyManCode() {
		return verifyManCode;
	}

	public void setVerifyManCode(String verifyManCode) {
		this.verifyManCode = verifyManCode;
	}

	public Long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	public Date getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}

	public String getExpressDeliveryCoordinate() {
		return expressDeliveryCoordinate;
	}

	public void setExpressDeliveryCoordinate(String expressDeliveryCoordinate) {
		this.expressDeliveryCoordinate = expressDeliveryCoordinate;
	}

	public String getDeptCoordinate() {
		return deptCoordinate;
	}

	public void setDeptCoordinate(String deptCoordinate) {
		this.deptCoordinate = deptCoordinate;
	}

	public String[] getCodeList() {
		return codeList;
	}

	public void setCodeList(String[] codeList) {
		this.codeList = codeList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOldCoordinate() {
		return oldCoordinate;
	}

	public void setOldCoordinate(String oldCoordinate) {
		this.oldCoordinate = oldCoordinate;
	}

}
