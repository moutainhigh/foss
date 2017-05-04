package com.deppon.foss.module.transfer.load.api.shared.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.TrayScanDetaiEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TrayScanExpressEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryTrayScanConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.TrayScanDto;
/**
 * @description:托盘任务模块之vo
 * @author 105869-foss-heyongdong
 * @date 2013-07-25 
 */
public class TrayScanVo implements Serializable {
	private static final long serialVersionUID = -7230331376983013020L;
	
	/**
	 * 查询叉车扫描任务时，接收前台传入的查询条件
	 */
	private QueryTrayScanConditionDto queryTrayScanConditionDto;
	
	/**
	 * 查询绑定托盘的运单、流水号前台传入的查询条件
	 * */
	private String traytaskCode;
	
	/**
	 * 查询叉车扫描任务时，返回前台的运单、流水号list
	 * */
	private List<TrayScanDetaiEntity> serialNoList;
	
	/**  零担
	 * 查询叉车扫描任务时，返回前台的List
	 * */
	private List<TrayScanDto>   trayScanList;
	
	/**  快递
	 * 查询叉车扫描任务时，返回前台的List<TrayScanExpressEntity>
	 * */
	private List<TrayScanExpressEntity>   trayScanListExpress;
	
	/**  快递
	 * 查询叉车扫描任务时，返回前台的List<Map>
	 * */
	private List<HashMap<String,String>>   trayScanListExpressMap;
	
	/**
	 * 当前大部门code
	 */
	private String superOrgCode;
	


	/**
	 * 后台返回的错误信息
	 * */
	private String errorMsg;

	public String getErrorMsg() {
		return errorMsg;
	}


	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}


	public QueryTrayScanConditionDto getQueryTrayScanConditionDto() {
		return queryTrayScanConditionDto;
	}


	public void setQueryTrayScanConditionDto(
			QueryTrayScanConditionDto queryTrayScanConditionDto) {
		this.queryTrayScanConditionDto = queryTrayScanConditionDto;
	}



	public String getTraytaskCode() {
		return traytaskCode;
	}


	public void setTraytaskCode(String traytaskCode) {
		this.traytaskCode = traytaskCode;
	}


	public List<TrayScanDetaiEntity> getSerialNoList() {
		return serialNoList;
	}


	public void setSerialNoList(List<TrayScanDetaiEntity> serialNoList) {
		this.serialNoList = serialNoList;
	}


	public List<TrayScanDto> getTrayScanList() {
		return trayScanList;
	}


	public void setTrayScanList(List<TrayScanDto> trayScanList) {
		this.trayScanList = trayScanList;
	}


	public String getSuperOrgCode() {
		return superOrgCode;
	}


	public void setSuperOrgCode(String superOrgCode) {
		this.superOrgCode = superOrgCode;
	}


	public List<TrayScanExpressEntity> getTrayScanListExpress() {
		return trayScanListExpress;
	}


	public void setTrayScanListExpress(
			List<TrayScanExpressEntity> trayScanListExpress) {
		this.trayScanListExpress = trayScanListExpress;
	}


	public List<HashMap<String, String>> getTrayScanListExpressMap() {
		return trayScanListExpressMap;
	}


	public void setTrayScanListExpressMap(
			List<HashMap<String, String>> trayScanListExpressMap) {
		this.trayScanListExpressMap = trayScanListExpressMap;
	}

	

	

	

	
	
	

}
