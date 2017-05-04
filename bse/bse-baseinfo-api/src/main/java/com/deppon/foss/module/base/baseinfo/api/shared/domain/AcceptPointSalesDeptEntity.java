package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 接驳点对应营业部映射关系主干实体
 * @author 132599-ShenWeiHua
 *
 * @date 2015-4-14 上午10:06:57
 */
public class AcceptPointSalesDeptEntity extends BaseEntity{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 接驳点编码
	 */
	private String acceptPointCode;
	
	/**
	 * 大区编码
	 */
	private String bigRegion;
	
	/**
	 * 营业区编码
	 */
	private String smallRegion;
	
	/**
	 * 中转场编码
	 */
	private String transferCode;
	
	/**
	 * 是否启用
	 */
	private String status;
	
	/**
	 * 是否删除
	 */
	private String active;
	
	/**
	 * 创建人名称
	 */
	private String createUserName;
	
	/**
	 * 修改人名称
	 */
	private String modifyUserName;
	
	/**
	 * 接驳点名称
	 */
	private String acceptPointName;
	
	/**
	 * 大区名称
	 */
	private String bigRegionName;
	
	/**
	 * 营业区名称
	 */
	private String smallRegionName;
	
	/**
	 * 中转场的名称
	 */
	private String transferName;
	
	/**
	 * 接驳点映射子类列表
	 */
	private List<AcceptPointSalesChildrenDeptEntity> childrenSalesDeptEntitys;

	public String getAcceptPointCode() {
		return acceptPointCode;
	}

	public void setAcceptPointCode(String acceptPointCode) {
		this.acceptPointCode = acceptPointCode;
	}

	public String getBigRegion() {
		return bigRegion;
	}

	public void setBigRegion(String bigRegion) {
		this.bigRegion = bigRegion;
	}

	public String getSmallRegion() {
		return smallRegion;
	}

	public void setSmallRegion(String smallRegion) {
		this.smallRegion = smallRegion;
	}

	public String getTransferCode() {
		return transferCode;
	}

	public void setTransferCode(String transferCode) {
		this.transferCode = transferCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getAcceptPointName() {
		return acceptPointName;
	}

	public void setAcceptPointName(String acceptPointName) {
		this.acceptPointName = acceptPointName;
	}

	public String getBigRegionName() {
		return bigRegionName;
	}

	public void setBigRegionName(String bigRegionName) {
		this.bigRegionName = bigRegionName;
	}

	public String getSmallRegionName() {
		return smallRegionName;
	}

	public void setSmallRegionName(String smallRegionName) {
		this.smallRegionName = smallRegionName;
	}

	public String getTransferName() {
		return transferName;
	}

	public void setTransferName(String transferName) {
		this.transferName = transferName;
	}

	public List<AcceptPointSalesChildrenDeptEntity> getChildrenSalesDeptEntitys() {
		return childrenSalesDeptEntitys;
	}

	public void setChildrenSalesDeptEntitys(
			List<AcceptPointSalesChildrenDeptEntity> childrenSalesDeptEntitys) {
		this.childrenSalesDeptEntitys = childrenSalesDeptEntitys;
	}
	
	
	
}
