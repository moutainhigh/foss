package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description 修改卸车任务Dto
 * @version 1.0
 * @author 328768-foss-gaojianfu
 * @update 2016年4月28日 下午5:47:45
 */
public class ExpressUpdateUnloadTaskDto  implements Serializable{

	
	
	
	private static final long serialVersionUID = 7541433479669215649L;

	/** 卸车任务编号 */
	private String unloadTaskNo;

	/** 月台号 */
	private String platformNo;
	
	/** 修改时间 */
	private Date updateTime;
	
	/**更新人工号 */
	private String updateNo;
	
	/**更新组织编号 */
	private String updateOrgCode;
	
	
	/** 删除理货员信息 */
	private List<ExpressLoaderParticipateDto> deleteOldLoader;

	/** 新增理货员信息 */
	private List<ExpressLoaderParticipateDto> addNewLoader;
	
	/** 删除卸车任务交接单信息 */
	private List<ExpressUnloadTaskDetailDto> deleteOldUnloadDetail;
	
	/** 新增卸车任务交接单信息 */
	private List<ExpressUnloadTaskDetailDto> addNewUnloadDetail;
	
	/**
	 * @description 获取 卸车任务编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月28日 下午5:53:15
	 */
	public String getUnloadTaskNo() {
		return unloadTaskNo;
	}

	/**
	 * @description 设置 卸车任务编号
	 * @param unloadTaskNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月28日 下午6:00:27
	 */
	public void setUnloadTaskNo(String unloadTaskNo) {
		this.unloadTaskNo = unloadTaskNo;
	}

	
	/**
	 * @description 获取 月台号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月28日 下午6:00:10
	 */
	public String getPlatformNo() {
		return platformNo;
	}

	/**
	 * @description 设置 月台号
	 * @param platformNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月28日 下午5:59:54
	 */
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}

	/**
	 * @description 获取 修改时间
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月28日 下午5:59:39
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @description 设置 修改时间
	 * @param updateTime
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月28日 下午5:59:21
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	
	/**
	 * @description 获取 删除理货员信息
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月28日 下午5:59:04
	 */
	public List<ExpressLoaderParticipateDto> getDeleteOldLoader() {
		return deleteOldLoader;
	}
	
	/**
	 * @description 设置 删除理货员信息
	 * @param deleteOldLoader
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月28日 下午5:56:46
	 */
	public void setDeleteOldLoader(List<ExpressLoaderParticipateDto> deleteOldLoader) {
		this.deleteOldLoader = deleteOldLoader;
	}

	/**
	 * @description 获取  新增理货员信息 
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月28日 下午5:56:29
	 */
	public List<ExpressLoaderParticipateDto> getAddNewLoader() {
		return addNewLoader;
	}

	/**
	 * @description 设置  新增理货员信息
	 * @param addNewLoader
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月28日 下午5:56:10
	 */
	public void setAddNewLoader(List<ExpressLoaderParticipateDto> addNewLoader) {
		this.addNewLoader = addNewLoader;
	}
	
	/**
	 * @description 获取 更新人编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月16日 上午8:49:26
	 */

	public String getUpdateNo() {
		return updateNo;
	}
	
	/**
	 * @description 设置 更新人编号
	 * @param updateNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月16日 上午8:50:10
	 */

	public void setUpdateNo(String updateNo) {
		this.updateNo = updateNo;
	}

	/**
	 * @description 设置 更新组织编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月16日 上午8:50:35
	 */
	public String getUpdateOrgCode() {
		return updateOrgCode;
	}

	/**
	 * @description 获取  更新组织编号
	 * @param updateOrgCode
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月16日 上午8:51:05
	 */
	public void setUpdateOrgCode(String updateOrgCode) {
		this.updateOrgCode = updateOrgCode;
	}

	/**
	 * @description 获取 删除卸车任务交接单信息
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月28日 下午5:55:55
	 */
	public List<ExpressUnloadTaskDetailDto> getDeleteOldUnloadDetail() {
		return deleteOldUnloadDetail;
	}

	/**
	 * @description 设置 删除卸车任务交接单信息
	 * @param deleteOldUnloadDetail
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月28日 下午5:55:31
	 */
	public void setDeleteOldUnloadDetail(List<ExpressUnloadTaskDetailDto> deleteOldUnloadDetail) {
		this.deleteOldUnloadDetail = deleteOldUnloadDetail;
	}

	/**
	 * @description 获取 新增卸车任务交接单信息
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月28日 下午5:55:11
	 */
	public List<ExpressUnloadTaskDetailDto> getAddNewUnloadDetail() {
		return addNewUnloadDetail;
	}

	/**
	 * @description 设置 新增卸车任务交接单信息
	 * @param addNewUnloadDetail
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月28日 下午5:54:50
	 */
	public void setAddNewUnloadDetail(List<ExpressUnloadTaskDetailDto> addNewUnloadDetail) {
		this.addNewUnloadDetail = addNewUnloadDetail;
	}
	
}
