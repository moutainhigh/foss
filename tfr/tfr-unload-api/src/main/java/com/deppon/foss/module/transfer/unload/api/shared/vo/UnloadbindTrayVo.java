package com.deppon.foss.module.transfer.unload.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadbindTrayEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadbindTrayExpressEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadbindTrayDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadbindTrayQueryConditionDto;
public class UnloadbindTrayVo implements Serializable{

	/*
	 * @author wqh
	 * @date  2013-12-27
	 * @desc  卸车绑定托盘
	 * */

	/**
	 * UnloadbindTrayVo
	 */
	private static final long serialVersionUID = -777989797997879L;

	//卸车绑定托盘查询条件
    private	UnloadbindTrayQueryConditionDto unloadbindTrayQueryConditionDto;
    
	//卸车绑定托盘实体 零担
    private List<UnloadbindTrayEntity> unloadbindTrayList;
    
    //卸车绑定托盘实体 快递
    private List<UnloadbindTrayExpressEntity> unloadbindTrayListExpress;
    
    //卸车任务绑定托盘明细
    private List<UnloadbindTrayDetailDto> unloadBindTrayDetailList;
    
   //托盘任务编号
    private String taskNo;
    //运单号
	private String waybillNo;
    
	public UnloadbindTrayQueryConditionDto getUnloadbindTrayQueryConditionDto() {
		return unloadbindTrayQueryConditionDto;
	}
	public void setUnloadbindTrayQueryConditionDto(
			UnloadbindTrayQueryConditionDto unloadbindTrayQueryConditionDto) {
		this.unloadbindTrayQueryConditionDto = unloadbindTrayQueryConditionDto;
	}
	public List<UnloadbindTrayEntity> getUnloadbindTrayList() {
		return unloadbindTrayList;
	}
	public void setUnloadbindTrayList(List<UnloadbindTrayEntity> unloadbindTrayList) {
		this.unloadbindTrayList = unloadbindTrayList;
	}
	public List<UnloadbindTrayDetailDto> getUnloadBindTrayDetailList() {
		return unloadBindTrayDetailList;
	}
	public void setUnloadBindTrayDetailList(
			List<UnloadbindTrayDetailDto> unloadBindTrayDetailList) {
		this.unloadBindTrayDetailList = unloadBindTrayDetailList;
	}
	public String getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public List<UnloadbindTrayExpressEntity> getUnloadbindTrayListExpress() {
		return unloadbindTrayListExpress;
	}
	public void setUnloadbindTrayListExpress(
			List<UnloadbindTrayExpressEntity> unloadbindTrayListExpress) {
		this.unloadbindTrayListExpress = unloadbindTrayListExpress;
	}
	
	
	
	
    
    
}
