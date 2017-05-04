package com.deppon.foss.module.transfer.unload.api.shared.dto;

public class UnloadTaskbindTrayDetailseriaNo extends UnloadbindTrayDetailDto {

	/**
	 * 卸车任务绑定托盘流水号明细
	 */
	private static final long serialVersionUID = 1333895345467652786L;

	//流水号
	private String seriaNo;

	public String getSeriaNo() {
		return seriaNo;
	}

	public void setSeriaNo(String seriaNo) {
		this.seriaNo = seriaNo;
	}
	
	
}
