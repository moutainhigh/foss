package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrOnDutyEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrOnDutyQcDto;

public class TfrCtrOnDutyVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 查询参数
	 */
	private TfrCtrOnDutyQcDto queryParam;

	/**
	 * 查询结果或用与新增
	 */
	private List<TfrCtrOnDutyEntity> tfrCtrOnDutys;

	/**
	 * 用户修改或新增特殊人员信息
	 */
	private TfrCtrOnDutyEntity tfrCtrOnDuty;

	/**
	 * 用于删除
	 */
	private String id;

	/**
	 * 外场编码
	 */
	private String tfrCtrCode;

	/**
	 * 外场名称
	 */
	private String tfrCtrName;

	public TfrCtrOnDutyQcDto getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(TfrCtrOnDutyQcDto queryParam) {
		this.queryParam = queryParam;
	}

	public List<TfrCtrOnDutyEntity> getTfrCtrOnDutys() {
		return tfrCtrOnDutys;
	}

	public void setTfrCtrOnDutys(List<TfrCtrOnDutyEntity> tfrCtrOnDutys) {
		this.tfrCtrOnDutys = tfrCtrOnDutys;
	}

	public TfrCtrOnDutyEntity getTfrCtrOnDuty() {
		return tfrCtrOnDuty;
	}

	public void setTfrCtrOnDuty(TfrCtrOnDutyEntity tfrCtrOnDuty) {
		this.tfrCtrOnDuty = tfrCtrOnDuty;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTfrCtrCode() {
		return tfrCtrCode;
	}

	public void setTfrCtrCode(String tfrCtrCode) {
		this.tfrCtrCode = tfrCtrCode;
	}

	public String getTfrCtrName() {
		return tfrCtrName;
	}

	public void setTfrCtrName(String tfrCtrName) {
		this.tfrCtrName = tfrCtrName;
	}

}
