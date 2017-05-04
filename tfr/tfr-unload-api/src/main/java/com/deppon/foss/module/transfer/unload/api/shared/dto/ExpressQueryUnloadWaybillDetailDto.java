package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;

/**
 * @description 这里用一句话描述这个类的作用
 * @version 1.0
 * @author 328768-foss-gaojianfu
 * @update 2016年5月3日 上午9:02:09
 */
public class ExpressQueryUnloadWaybillDetailDto implements Serializable {

	
	
	private static final long serialVersionUID = 437740353235541668L;

	/** 卸车任务编号 */
	private String unloadTaskNo;
	
	/**
	 * 件号
	 */
	private String cargoNo;
	
	/**
	 * 件类型
	 */
	private String cargoType;
	
	/**
	 * 部门编号
	 */
	private String orgCode;
	

	/**
	 * @description 获取 卸车任务编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月3日 上午9:02:42
	 */
	public String getUnloadTaskNo() {
		return unloadTaskNo;
	}

	/**
	 * @description 设置 卸车任务编号
	 * @param unloadTaskNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月3日 上午9:02:52
	 */
	public void setUnloadTaskNo(String unloadTaskNo) {
		this.unloadTaskNo = unloadTaskNo;
	}

	/**
	 * @description 获取  件号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月3日 上午9:03:03
	 */
	public String getCargoNo() {
		return cargoNo;
	}

	/**
	 * @description 设置  件号
	 * @param cargoNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月3日 上午9:03:11
	 */
	public void setCargoNo(String cargoNo) {
		this.cargoNo = cargoNo;
	}

	/**
	 * @description 获取  件类型
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月3日 上午9:03:20
	 */
	public String getCargoType() {
		return cargoType;
	}

	/**
	 * @description 设置  件类型
	 * @param cargoType
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月3日 上午9:03:29
	 */
	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}

	/**
	 * @description 获取 部门编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月16日 上午10:44:45
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @description 设置  部门编号
	 * @param orgCode
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月16日 上午10:44:49
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	
}
