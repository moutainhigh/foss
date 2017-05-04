package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrSummaryREntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrSummaryRDto;

/**
 * 02业务重分类汇总报表Vo
 * @author 340778 foss
 * @createTime 2016年8月18日 下午4:42:49
 */
public class MvrSummaryRVo extends MvrSummaryRDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MvrSummaryRDto mvrSummaryRDto;
	private List<MvrSummaryREntity> mvrSummaryRList;


	public List<MvrSummaryREntity> getMvrSummaryRList() {
		return mvrSummaryRList;
	}

	public void setMvrSummaryRList(List<MvrSummaryREntity> mvrSummaryRList) {
		this.mvrSummaryRList = mvrSummaryRList;
	}

	public MvrSummaryRDto getMvrSummaryRDto() {
		return mvrSummaryRDto;
	}

	public void setMvrSummaryRDto(MvrSummaryRDto mvrSummaryRDto) {
		this.mvrSummaryRDto = mvrSummaryRDto;
	}
}
