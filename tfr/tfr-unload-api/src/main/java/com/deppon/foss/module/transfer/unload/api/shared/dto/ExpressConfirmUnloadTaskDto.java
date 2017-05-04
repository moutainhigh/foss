package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description 快递确认卸车任务Dto
 * @version 1.0
 * @author 328768-foss-gaojianfu
 * @update 2016年4月29日 下午12:49:22
 */
public class ExpressConfirmUnloadTaskDto implements Serializable {

	
	
	private static final long serialVersionUID = -8258444947414586027L;

	// 卸车任务编号
	private String unloadTaskNo;

	// 卸车开始时间
	private Date unloadStartTime;

	// 卸车结束时间
	private Date unloadEndTime;

	//车牌号
	private String vehicleNo;
	
	//工号
	private String updateNo;
	
	//当前操作部门编码
	private String updateOrgCode;

	// 少货件号
	private List<ExpressUnloadWaybillDetailDto> lackWayBillList;
	// 多货件号
	private List<ExpressUnloadWaybillDetailDto> moreWayBillList;
	
	// 少货交接单（整个交接单 丢失）
	private List<ExpressUnloadWaybillDetailDto> lackHandOverBillList;
	
	/**
	 * @description 获取 卸车任务编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午5:05:01
	 */
	public String getUnloadTaskNo() {
		return unloadTaskNo;
	}
	
	/**
	 * @description 设置 卸车任务编号
	 * @param unloadTaskNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午5:05:13
	 */
	public void setUnloadTaskNo(String unloadTaskNo) {
		this.unloadTaskNo = unloadTaskNo;
	}
	
	/**
	 * @description 获取 卸车开始时间
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午5:05:41
	 */
	public Date getUnloadStartTime() {
		return unloadStartTime;
	}
	
	/**
	 * @description 设置 卸车开始时间
	 * @param unloadStartTime
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午5:05:52
	 */
	public void setUnloadStartTime(Date unloadStartTime) {
		this.unloadStartTime = unloadStartTime;
	}
	
	/**
	 * @description 获取 卸车结束时间
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午5:06:01
	 */
	public Date getUnloadEndTime() {
		return unloadEndTime;
	}
	
	/**
	 * @description 设置 卸车结束时间
	 * @param unloadEndTime
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午5:06:10
	 */
	public void setUnloadEndTime(Date unloadEndTime) {
		this.unloadEndTime = unloadEndTime;
	}
	
	/**
	 * @description 获取 车牌号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午5:06:19
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * @description 设置 车牌号
	 * @param vehicleNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午5:06:29
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	/**
	 * @description 获取 工号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午5:06:37
	 */
	public String getUpdateNo() {
		return updateNo;
	}
	
	/**
	 * @description 设置 工号
	 * @param updateNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午5:06:47
	 */
	public void setUpdateNo(String updateNo) {
		this.updateNo = updateNo;
	}
	

	/**
	 * @description 获取  当前操作部门编码
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月16日 上午9:39:03
	 */
	public String getUpdateOrgCode() {
		return updateOrgCode;
	}

	/**
	 * @description 设置  当前操作部门编码
	 * @param updateOrgCode
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月16日 上午9:39:12
	 */
	public void setUpdateOrgCode(String updateOrgCode) {
		this.updateOrgCode = updateOrgCode;
	}

	/**
	 * @description 获取 少货件号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午5:06:58
	 */
	public List<ExpressUnloadWaybillDetailDto> getLackWayBillList() {
		return lackWayBillList;
	}
	
	/**
	 * @description 设置 少货件号
	 * @param lackWayBillList
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午5:07:06
	 */
	public void setLackWayBillList(List<ExpressUnloadWaybillDetailDto> lackWayBillList) {
		this.lackWayBillList = lackWayBillList;
	}
	
	/**
	 * @description 获取   多货件号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午5:07:16
	 */
	public List<ExpressUnloadWaybillDetailDto> getMoreWayBillList() {
		return moreWayBillList;
	}
	
	/**
	 * @description 设置   多货件号
	 * @param moreWayBillList
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午5:07:23
	 */
	public void setMoreWayBillList(List<ExpressUnloadWaybillDetailDto> moreWayBillList) {
		this.moreWayBillList = moreWayBillList;
	}

	/**
	 * @description 获取 少货交接单编号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月17日 下午4:34:38
	 */
	public List<ExpressUnloadWaybillDetailDto> getLackHandOverBillList() {
		return lackHandOverBillList;
	}

	/**
	 * @description 设置  少货交接单编号
	 * @param lackHandOverBillList
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月17日 下午4:34:46
	 */
	public void setLackHandOverBillList(List<ExpressUnloadWaybillDetailDto> lackHandOverBillList) {
		this.lackHandOverBillList = lackHandOverBillList;
	}
	
}
