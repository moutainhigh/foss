package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfontREntity;

/**
 * 02普通业务重分类报表Dto
 * @author 340778 foss
 * @createTime 2016年8月17日 上午10:39:08
 */
public class MvrNrfontRDto extends MvrNrfontREntity implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 02普通业务重分类实体集合
	 */
	private List<MvrNrfontREntity> mvrNrfontRList;
	/**
	 * 当前登录用户编码
	 */
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

	public List<MvrNrfontREntity> getMvrNrfontRList() {
		return mvrNrfontRList;
	}

	public void setMvrNrfontRList(List<MvrNrfontREntity> mvrNrfontRList) {
		this.mvrNrfontRList = mvrNrfontRList;
	}
}
