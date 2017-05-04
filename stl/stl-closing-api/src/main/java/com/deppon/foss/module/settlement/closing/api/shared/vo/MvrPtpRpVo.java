/**
 * 
 */
package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPtpRpEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPtpRpDto;

/**
 * @author 231438
 *
 */
public class MvrPtpRpVo extends MvrPtpRpDto implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -5449973668939228949L;
	/**
	 * 合伙人奖罚月报表列表
	 * */
	private List<MvrPtpRpEntity> mvrPtpRpList;
	
	/** 合计合伙人奖罚月报表 */
	private MvrPtpRpDto mvrPtpRpDto;

	
	public MvrPtpRpDto getMvrPtpRpDto() {
		return mvrPtpRpDto;
	}
	public void setMvrPtpRpDto(MvrPtpRpDto mvrPtpRpDto) {
		this.mvrPtpRpDto = mvrPtpRpDto;
	}
	public List<MvrPtpRpEntity> getMvrPtpRpList() {
		return mvrPtpRpList;
	}
	public void setMvrPtpRpList(List<MvrPtpRpEntity> mvrPtpRpList) {
		this.mvrPtpRpList = mvrPtpRpList;
	}

}
