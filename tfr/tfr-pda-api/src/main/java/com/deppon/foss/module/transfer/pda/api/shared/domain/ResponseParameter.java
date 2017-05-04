package com.deppon.foss.module.transfer.pda.api.shared.domain;

public class ResponseParameter {
	//返回是否成功的标志  true ,false
	private boolean beSuccess ;
	//失败原因 
	private String failureReason;
	//返回类型，接口类型 目前三个接口类型 ：makePacakgeNo，createPacakge，queryWaybillPath
	private String returnType;
	//返回结果实体，不同接口返回不同的实体。
	//1、标签打印获取包号接口（makePacakgeNo）：返回的是List<String>
	//2、建包接口 （createPacakge）：返回没有实体，只有成功与否的标志 beSuccess
	//3、拉取走货路由（queryWaybillPath）：返回的是 List<WaybillPathDetailEntity>
	private Object returnEntity;
	
	public boolean isBeSuccess() {
		return beSuccess;
	}
	public void setBeSuccess(boolean beSuccess) {
		this.beSuccess = beSuccess;
	}
	public String getFailureReason() {
		return failureReason;
	}
	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}
	public Object getReturnEntity() {
		return returnEntity;
	}
	public void setReturnEntity(Object returnEntity) {
		this.returnEntity = returnEntity;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	
	
}
