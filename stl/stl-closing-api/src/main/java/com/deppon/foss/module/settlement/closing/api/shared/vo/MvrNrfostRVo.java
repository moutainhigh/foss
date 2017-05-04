package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfostREntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfostRDto;

/**
 * 02特殊业务重分类报表
 * @author 340778 foss
 * @createTime 2016年8月17日 下午7:58:17
 */
public class MvrNrfostRVo extends MvrNrfostRDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MvrNrfostRDto mvrNrfostRDto;
	private List<MvrNrfostREntity> mvrNrfostRList;


	public List<MvrNrfostREntity> getMvrNrfostRList() {
		return mvrNrfostRList;
	}

	public void setMvrNrfostRList(List<MvrNrfostREntity> mvrNrfostRList) {
		this.mvrNrfostRList = mvrNrfostRList;
	}

	public MvrNrfostRDto getMvrNrfostRDto() {
		return mvrNrfostRDto;
	}

	public void setMvrNrfostRDto(MvrNrfostRDto mvrNrfostRDto) {
		this.mvrNrfostRDto = mvrNrfostRDto;
	}
}
