package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * @author 092020-lipengfei
 * @version V1.0
 * @Description 待叉区实体
 * @Time 2014-4-25
 */
public class WaitForkAreaEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2806002434719109991L;
	
	/**
	 * 组织编码
	 */
	private String organizationCode;
	/**
	 * 组织名称
	 */
	private String organizationName;
	/**
	 * 外场编码
	 */
	private String transferCode;
	/**
	 * 待叉区编码
	 */
	private String waitForkAreaCode;
	/**
	 * 虚拟编码
	 */
	private String virtualCode;
	/**
	 * 横坐标
	 */
	private String abscissa;
	/**
	 * 纵坐标
	 */
	private String ordinate;
	/**
	 * 待叉区长度
	 */
	private String waitForkAreaLength;
	/**
	 * 待叉区宽度
	 */
	private String waitForkAreaWidth;
	/**
	 * 待叉区高度
	 */
	private String waitForkAreaHeight;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 是否有效
	 */
	private String active;
	/**
	 * 与库区的距离
	 */
	private List<WaitForkAreaDistanceEntity> distanceBetweenGoodsArea;
	/**
	 * 与月台的距离
	 */
	private List<WaitForkAreaDistanceEntity> distanceBetweenPlatform;
	
	
	/**
	 * 目标编码
	 */
	private String targetCode;
	
	/**
	 *操作类型
	 */
	private String isUpdate;
	
	
	
	
	public String getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}
	public String getTargetCode() {
		return targetCode;
	}
	public void setTargetCode(String targetCode) {
		this.targetCode = targetCode;
	}
	public String getOrganizationCode() {
		return organizationCode;
	}
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getTransferCode() {
		return transferCode;
	}
	public void setTransferCode(String transferCode) {
		this.transferCode = transferCode;
	}
	public String getWaitForkAreaCode() {
		return waitForkAreaCode;
	}
	public void setWaitForkAreaCode(String waitForkAreaCode) {
		this.waitForkAreaCode = waitForkAreaCode;
	}
	public String getVirtualCode() {
		return virtualCode;
	}
	public void setVirtualCode(String virtualCode) {
		this.virtualCode = virtualCode;
	}
	public String getAbscissa() {
		return abscissa;
	}
	public void setAbscissa(String abscissa) {
		this.abscissa = abscissa;
	}
	public String getOrdinate() {
		return ordinate;
	}
	public void setOrdinate(String ordinate) {
		this.ordinate = ordinate;
	}
	public String getWaitForkAreaLength() {
		return waitForkAreaLength;
	}
	public void setWaitForkAreaLength(String waitForkAreaLength) {
		this.waitForkAreaLength = waitForkAreaLength;
	}
	public String getWaitForkAreaWidth() {
		return waitForkAreaWidth;
	}
	public void setWaitForkAreaWidth(String waitForkAreaWidth) {
		this.waitForkAreaWidth = waitForkAreaWidth;
	}
	public String getWaitForkAreaHeight() {
		return waitForkAreaHeight;
	}
	public void setWaitForkAreaHeight(String waitForkAreaHeight) {
		this.waitForkAreaHeight = waitForkAreaHeight;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public List<WaitForkAreaDistanceEntity> getDistanceBetweenGoodsArea() {
		return distanceBetweenGoodsArea;
	}
	public void setDistanceBetweenGoodsArea(
			List<WaitForkAreaDistanceEntity> distanceBetweenGoodsArea) {
		this.distanceBetweenGoodsArea = distanceBetweenGoodsArea;
	}
	public List<WaitForkAreaDistanceEntity> getDistanceBetweenPlatform() {
		return distanceBetweenPlatform;
	}
	public void setDistanceBetweenPlatform(
			List<WaitForkAreaDistanceEntity> distanceBetweenPlatform) {
		this.distanceBetweenPlatform = distanceBetweenPlatform;
	}
	

}
