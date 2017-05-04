package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * @description  分配卸车指令明细 Dto
 * @version 1.0
 * @author 328768-foss-gaojianfu
 * @update 2016年4月29日 下午1:53:34
 */
public class ExpressUnloadcmdAssignDetailDto implements Serializable {
	
	
	
	private static final long serialVersionUID = -5119415310935213490L;

	/** 分配卸车指令编号 */
	private String commandNo;

	/** 交接单号 */
	private String handoverBillNo;

	/** 理货员编号 */
	private String tallymanNo;

	/** 理货员名称 */
	private String tallymanName;

	/** 重量 */
	private BigDecimal weight;

	/** 体积 */
	private BigDecimal volume;

	/** 件数 */
	private Long goodsQty;

	/** 单据出发部门编码 */
	private String handoverBillDepartCode;

	/** 单据出发部门名称 */
	private String handoverBillDepartName;

	/** 单据到达部门名称 */
	private String handoverBillDestName;

	/** 单据到达部门编码 */
	private String handoverBillDestCode;


	/**
	 * @description 获取 分配卸车指令编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:54:16
	 */
	public String getCommandNo() {
		return commandNo;
	}

	/**
	 * @description 设置 分配卸车指令编号
	 * @param commandNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:54:32
	 */
	public void setCommandNo(String commandNo) {
		this.commandNo = commandNo;
	}

	/**
	 * @description 获取 交接单号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:54:45
	 */
	public String getHandoverBillNo() {
		return handoverBillNo;
	}

	/**
	 * @description 设置 交接单号
	 * @param handoverBillNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:55:00
	 */
	public void setHandoverBillNo(String handoverBillNo) {
		this.handoverBillNo = handoverBillNo;
	}

	/**
	 * @description 获取 理货员编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:55:19
	 */
	public String getTallymanNo() {
		return tallymanNo;
	}

	/**
	 * @description 设置 理货员编号
	 * @param tallymanNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:55:35
	 */
	public void setTallymanNo(String tallymanNo) {
		this.tallymanNo = tallymanNo;
	}

	/**
	 * @description 获取 理货员名称
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:55:51
	 */
	public String getTallymanName() {
		return tallymanName;
	}

	/**
	 * @description 设置 理货员名称
	 * @param tallymanName
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:56:04
	 */
	public void setTallymanName(String tallymanName) {
		this.tallymanName = tallymanName;
	}

	/**
	 * @description 获取 重量
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:56:20
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * @description 设置 重量
	 * @param weight
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:56:34
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * @description  获取 体积
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:56:55
	 */
	public BigDecimal getVolume() {
		return volume;
	}

	/**
	 * @description 设置 体积
	 * @param volume
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:57:09
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	/**
	 * @description 获取 件数
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:57:30
	 */
	public Long getGoodsQty() {
		return goodsQty;
	}

	/**
	 * @description 设置 件数
	 * @param goodsQty
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:57:52
	 */
	public void setGoodsQty(Long goodsQty) {
		this.goodsQty = goodsQty;
	}

	/**
	 * @description 获取 单据出发部门编码
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:58:13
	 */
	public String getHandoverBillDepartCode() {
		return handoverBillDepartCode;
	}

	/**
	 * @description 设置 单据出发部门编码
	 * @param handoverBillDepartCode
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:58:26
	 */
	public void setHandoverBillDepartCode(String handoverBillDepartCode) {
		this.handoverBillDepartCode = handoverBillDepartCode;
	}

	/**
	 * @description 获取 单据出发部门名称
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:58:41
	 */
	public String getHandoverBillDepartName() {
		return handoverBillDepartName;
	}

	/**
	 * @description 设置 单据出发部门名称
	 * @param handoverBillDepartName
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:58:55
	 */
	public void setHandoverBillDepartName(String handoverBillDepartName) {
		this.handoverBillDepartName = handoverBillDepartName;
	}

	/**
	 * @description 获取 单据到达部门编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:59:14
	 */
	public String getHandoverBillDestName() {
		return handoverBillDestName;
	}

	/**
	 * @description 设置 单据到达部门编号
	 * @param handoverBillDestName
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:59:29
	 */
	public void setHandoverBillDestName(String handoverBillDestName) {
		this.handoverBillDestName = handoverBillDestName;
	}

	/**
	 * @description 获取 单据到达部门名称
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:59:42
	 */
	public String getHandoverBillDestCode() {
		return handoverBillDestCode;
	}

	/**
	 * @description 设置 单据到达部门名称
	 * @param handoverBillDestCode
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午2:00:06
	 */
	public void setHandoverBillDestCode(String handoverBillDestCode) {
		this.handoverBillDestCode = handoverBillDestCode;
	}

}
