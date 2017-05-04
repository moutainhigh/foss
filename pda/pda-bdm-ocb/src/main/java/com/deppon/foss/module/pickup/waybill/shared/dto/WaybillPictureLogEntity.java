package com.deppon.foss.module.pickup.waybill.shared.dto;


/**
 * 集中开单，运单在线撤销功能--日志实体类
 * @author 245955
 *
 */
public class WaybillPictureLogEntity {
	private String id;
	//调用FOSS接口开始时间
    private String startFossTime;
    //调用FOSS接口结束时间
    private String endFossTime;
    //查询运单目录的开始时间
    private String queryFilePathStartTime;
    //查询运单目录的结束时间
    private String queryFilePathEndTime;
    //删除运单目录的开始时间
    private String deleteFilePathStartTime;
    //删除运单目录的结束时间
    private String deleteFilePathEndTime;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStartFossTime() {
		return startFossTime;
	}
	public void setStartFossTime(String startFossTime) {
		this.startFossTime = startFossTime;
	}
	public String getEndFossTime() {
		return endFossTime;
	}
	public void setEndFossTime(String endFossTime) {
		this.endFossTime = endFossTime;
	}
	public String getQueryFilePathStartTime() {
		return queryFilePathStartTime;
	}
	public void setQueryFilePathStartTime(String queryFilePathStartTime) {
		this.queryFilePathStartTime = queryFilePathStartTime;
	}
	public String getQueryFilePathEndTime() {
		return queryFilePathEndTime;
	}
	public void setQueryFilePathEndTime(String queryFilePathEndTime) {
		this.queryFilePathEndTime = queryFilePathEndTime;
	}
	public String getDeleteFilePathStartTime() {
		return deleteFilePathStartTime;
	}
	public void setDeleteFilePathStartTime(String deleteFilePathStartTime) {
		this.deleteFilePathStartTime = deleteFilePathStartTime;
	}
	public String getDeleteFilePathEndTime() {
		return deleteFilePathEndTime;
	}
	public void setDeleteFilePathEndTime(String deleteFilePathEndTime) {
		this.deleteFilePathEndTime = deleteFilePathEndTime;
	}
	
}
