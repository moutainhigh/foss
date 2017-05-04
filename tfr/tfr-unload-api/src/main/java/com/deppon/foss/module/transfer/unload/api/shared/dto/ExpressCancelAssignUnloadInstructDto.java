package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @description FOSS同步取消分配车辆任务到悟空
 * @version 1.0
 * @author 328768-foss-gaojianfu
 * @update 2016年4月29日 下午3:42:41
 */
public class ExpressCancelAssignUnloadInstructDto implements Serializable {

	

	private static final long serialVersionUID = 4531196008580954362L;

	/** 分配卸车指令编号 */
	private String commandNo;
	
	/** 当前操作人编号 */
	private String updateNo;
	
	/** 当前操作人部门编号 */
	private String updateOrgCode;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	/**
	 * @description 获取 分配卸车指令编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午3:44:55
	 */
	public String getCommandNo() {
		return commandNo;
	}

	/**
	 * @description 设置 分配卸车指令编号
	 * @param commandNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午3:45:37
	 */
	public void setCommandNo(String commandNo) {
		this.commandNo = commandNo;
	}

	/**
	 * @description 获取 当前操作人编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月16日 上午9:56:52
	 */
	
	public String getUpdateNo() {
		return updateNo;
	}

	/**
	 * @description 设置 当前操作人编号
	 * @param updateNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月16日 上午9:56:57
	 */
	public void setUpdateNo(String updateNo) {
		this.updateNo = updateNo;
	}

	/**
	 * @description 获取 当前操作人部门编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月16日 上午9:57:01
	 */
	public String getUpdateOrgCode() {
		return updateOrgCode;
	}

	/**
	 * @description 设置 当前操作人部门编号
	 * @param updateOrgCode
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月16日 上午9:57:06
	 */
	public void setUpdateOrgCode(String updateOrgCode) {
		this.updateOrgCode = updateOrgCode;
	}

	/**
	 * @description 获取 修改时间
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午3:45:57
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @description 设置 修改时间
	 * @param updateTime
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午3:46:16
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
