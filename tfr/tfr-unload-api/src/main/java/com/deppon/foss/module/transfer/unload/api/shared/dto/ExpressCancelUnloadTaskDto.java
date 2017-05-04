package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 快递修改卸车任务Dto
 * @version 1.0
 * @author 328768-foss-gaojianfu
 * @update 2016年4月29日 上午11:46:03
 */
public class ExpressCancelUnloadTaskDto implements Serializable {

	
	
	
	private static final long serialVersionUID = 8504973360383699577L;

	//卸车任务编号
	private String unloadTaskNo;
	
	//当前操作人编号
	private String updateNo;
	
	//当前操作人部门编号
	private String updateOrgCode;
	
	//修改时间
	private Date updateTime;

	/**
	 * @description 获取 卸车任务编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午11:46:58
	 */
	public String getUnloadTaskNo() {
		return unloadTaskNo;
	}

	/**
	 * @description 设置 卸车任务编号
	 * @param unloadTaskNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午11:47:39
	 */
	public void setUnloadTaskNo(String unloadTaskNo) {
		this.unloadTaskNo = unloadTaskNo;
	}
	
	/**
	 * @description 获取  当前操作人编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月16日 上午9:00:50
	 */
    public String getUpdateNo() {
		return updateNo;
	}

    /**
     * @description 设置 当前操作人编号
     * @param updateNo
     * @version 1.0
     * @author 328768-foss-gaojianfu
     * @update 2016年5月16日 上午9:01:02
     */
	public void setUpdateNo(String updateNo) {
		this.updateNo = updateNo;
	}

	/**
	 * @description 获取  当前操作人部门编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月16日 上午9:01:08
	 */
	public String getUpdateOrgCode() {
		return updateOrgCode;
	}

	/**
	 * @description 设置  当前操作人部门编号
	 * @param updateOrgCode
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月16日 上午9:01:12
	 */
	public void setUpdateOrgCode(String updateOrgCode) {
		this.updateOrgCode = updateOrgCode;
	}

	/**
     * @description 获取  修改时间
     * @return
     * @version 1.0
     * @author 328768-foss-gaojianfu
     * @update 2016年4月29日 上午11:48:33
     */
	public Date getUpdateTime() {
		return updateTime;
	}
    /**
     * @description 设置  修改时间
     * @param updateTime
     * @version 1.0
     * @author 328768-foss-gaojianfu
     * @update 2016年4月29日 上午11:49:00
     */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
