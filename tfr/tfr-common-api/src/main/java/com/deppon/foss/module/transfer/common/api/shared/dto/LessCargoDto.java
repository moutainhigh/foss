package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.io.Serializable;
/**
 * 卸车差异列表(多货少货)
 * @author 313352 gyy
 * 
 */
public class LessCargoDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 多货件数
	private int moreGoodsPieces;
	// 少货件数
	private int lackGoodsPieces;
    //卸车差异报告编号
    private String diffReportNo;

    public String getDiffReportNo() {
        return diffReportNo;
    }

    public void setDiffReportNo(String diffReportNo) {
        this.diffReportNo = diffReportNo;
    }

    public int getMoreGoodsPieces() {
		return moreGoodsPieces;
	}

	public void setMoreGoodsPieces(int moreGoodsPieces) {
		this.moreGoodsPieces = moreGoodsPieces;
	}

	public int getLackGoodsPieces() {
		return lackGoodsPieces;
	}

	public void setLackGoodsPieces(int lackGoodsPieces) {
		this.lackGoodsPieces = lackGoodsPieces;
	}
}
