package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.math.BigDecimal;
import java.util.List;

/**
 * 德邦家装接口实体
 * 
 * @author WangQianJin
 * 
 */
public class WaybillMessageEntity {
	/**
	 * 增值服务
	 */
	private String specialValueAddedServiceType;
	/**
	 * 货物总件数
	 */
	private Integer goodsQtyTotal;
	/**
	 * 提货方式
	 */
	private String receiveMethod;
	/**
	 * 货物名称
	 */
	private String goodsName;
	/**
	 * 货物总重量
	 */
	private BigDecimal goodsWeightTotal;

	/**
	 * 货物总体积
	 */
	private BigDecimal goodsVolumeTotal;
	/**
	 * 货物类型
	 */
	private String goodsTypeCode;
	/**
	 * 安装费
	 * 
	 * @return
	 */
	private List<InstallationEntity> installList;
	/**
	 * 发货部门
	 * @return
	 */
	private String receiveOrgCode;

	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	public String getSpecialValueAddedServiceType() {
		return specialValueAddedServiceType;
	}

	public List<InstallationEntity> getInstallList() {
		return installList;
	}

	public void setInstallList(List<InstallationEntity> installList) {
		this.installList = installList;
	}

	public void setSpecialValueAddedServiceType(
			String specialValueAddedServiceType) {
		this.specialValueAddedServiceType = specialValueAddedServiceType;
	}

	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	public String getGoodsTypeCode() {
		return goodsTypeCode;
	}

	public void setGoodsTypeCode(String goodsTypeCode) {
		this.goodsTypeCode = goodsTypeCode;
	}

}
