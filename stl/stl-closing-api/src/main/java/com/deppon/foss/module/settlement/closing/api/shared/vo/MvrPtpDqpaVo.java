package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPtpDqpaEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpDqpaQueryDto;

/**
 * 合伙人德启代付月报表Vo
 * 
 * @author gpz
 * @date 2016年3月21日
 */
public class MvrPtpDqpaVo implements Serializable {

	/**
	 * 序列号ID
	 */
	private static final long serialVersionUID = -3550740155599619200L;

	/**
	 * 合伙人德启代付月报表参数dto
	 */
	private MvrPtpDqpaQueryDto queryDto;
	
	/**
	 * 合伙人德启代付月报表数据集合
	 */
	private List<MvrPtpDqpaEntity> mvrPtpDqpaEntityList;

	/**
	 * @return the queryDto
	 */
	public MvrPtpDqpaQueryDto getQueryDto() {
		return queryDto;
	}

	/**
	 * @param queryDto the queryDto to set
	 */
	public void setQueryDto(MvrPtpDqpaQueryDto queryDto) {
		this.queryDto = queryDto;
	}

	/**
	 * @return the mvrPtpDqpaEntityList
	 */
	public List<MvrPtpDqpaEntity> getMvrPtpDqpaEntityList() {
		return mvrPtpDqpaEntityList;
	}

	/**
	 * @param mvrPtpDqpaEntityList the mvrPtpDqpaEntityList to set
	 */
	public void setMvrPtpDqpaEntityList(List<MvrPtpDqpaEntity> mvrPtpDqpaEntityList) {
		this.mvrPtpDqpaEntityList = mvrPtpDqpaEntityList;
	}
	
}
