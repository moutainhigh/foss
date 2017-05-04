
package com.deppon.foss.module.transfer.airfreight.api.shared.vo;

import java.util.List;

/**
 * 状态信息和货物轨迹
* @author 200942
 * @date 2016-5-10 下午8:37:39
 */
public class StatusAndTrackResponse implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4319108731101460017L;
	//状态信息
	private List<GeneralInformation> generalInformations;
	
	//货物轨迹
	private List<WaybillTrack> waybillTracks;

	//提示信息
	private String returnInfo;
	
	//标示
	private String state;
	
	

	public String getReturnInfo() {
		return returnInfo;
	}

	public void setReturnInfo(String returnInfo) {
		this.returnInfo = returnInfo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the generalInformations
	 */
	public List<GeneralInformation> getGeneralInformations() {
		return generalInformations;
	}

	/**
	 * @param generalInformations the generalInformations to set
	 */
	public void setGeneralInformations(List<GeneralInformation> generalInformations) {
		this.generalInformations = generalInformations;
	}

	/**
	 * @return the waybillTracks
	 */
	public List<WaybillTrack> getWaybillTracks() {
		return waybillTracks;
	}

	/**
	 * @param waybillTracks the waybillTracks to set
	 */
	public void setWaybillTracks(List<WaybillTrack> waybillTracks) {
		this.waybillTracks = waybillTracks;
	}

	
	
}