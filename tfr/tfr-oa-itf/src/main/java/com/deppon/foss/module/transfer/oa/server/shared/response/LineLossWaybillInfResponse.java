package com.deppon.foss.module.transfer.oa.server.shared.response;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.LineLossWaybillInfDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadSerialDetaiDto;

public class LineLossWaybillInfResponse implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**运单详情*/
	private LineLossWaybillInfDto lineLossWaybillInfDto;
	/**走货路径*/
	private String pathDeatailUnion;
	/**走货路径集*/
	private List<PathDetailEntity> pathDetails;
	/**卸车扫描信息*/
	private List<UnloadSerialDetaiDto> unloadScanList;
	
	/**返回编码 0 成功 1 失败*/
	private String returnCode;
	/**返回信息*/
	private String returnMsg;
	
	public LineLossWaybillInfDto getLineLossWaybillInfDto() {
		return lineLossWaybillInfDto;
	}
	public void setLineLossWaybillInfDto(LineLossWaybillInfDto lineLossWaybillInfDto) {
		this.lineLossWaybillInfDto = lineLossWaybillInfDto;
	}
	public String getPathDeatailUnion() {
		return pathDeatailUnion;
	}
	public void setPathDeatailUnion(String pathDeatailUnion) {
		this.pathDeatailUnion = pathDeatailUnion;
	}
	public List<PathDetailEntity> getPathDetails() {
		return pathDetails;
	}
	public void setPathDetails(List<PathDetailEntity> pathDetails) {
		this.pathDetails = pathDetails;
	}
	public List<UnloadSerialDetaiDto> getUnloadScanList() {
		return unloadScanList;
	}
	public void setUnloadScanList(List<UnloadSerialDetaiDto> unloadScanList) {
		this.unloadScanList = unloadScanList;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	
}
