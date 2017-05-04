package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfostREntity;

/**
 * 02特殊业务重分类报表Dto
 * @author 340778 foss
 * @createTime 2016年8月17日 下午7:31:02
 */
public class MvrNrfostRDto extends MvrNrfostREntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<MvrNrfostREntity> mvrNrfostRList;
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

	public List<MvrNrfostREntity> getMvrNrfostRList() {
		return mvrNrfostRList;
	}

	public void setMvrNrfostRList(List<MvrNrfostREntity> mvrNrfostRList) {
		this.mvrNrfostRList = mvrNrfostRList;
	}
}
