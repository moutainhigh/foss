package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrSummaryREntity;

/**
 * 02业务重分类汇总报表Dto
 * @author 340778 foss
 * @createTime 2016年8月18日 下午4:40:28
 */
public class MvrSummaryRDto extends MvrSummaryREntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<MvrSummaryREntity> mvrSummaryRList;
	/** 用户编码-设置用户数据查询权限. */
	private String userCode;
	
	/**
	 * 总数量
	 */
	private Long count;


	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public List<MvrSummaryREntity> getMvrSummaryRList() {
		return mvrSummaryRList;
	}

	public void setMvrSummaryRList(List<MvrSummaryREntity> mvrSummaryRList) {
		this.mvrSummaryRList = mvrSummaryRList;
	}
}
