package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.NoteDetailsEntity;

/**
 * 小票单据明细DTO
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-10-23 上午8:12:54
 */
public class NoteDetailsDto extends NoteDetailsEntity {

	private static final long serialVersionUID = 606281779711483251L;

	/**
	 * 小票单据编号
	 */
	private String detailNo;

	/**
	 * 起始编号
	 */
	private int beginNo;

	/**
	 * 终止编号
	 */
	private int endNo;

	/**
	 * 下发起止号
	 */
	private String beginWithEndNo;

	/**
	 * 要更新的AppID号
	 */
	private List<String> noteAppIds;

	/**
	 * @return detailNo
	 */
	public String getDetailNo() {
		return detailNo;
	}

	/**
	 * @param detailNo
	 */
	public void setDetailNo(String detailNo) {
		this.detailNo = detailNo;
	}

	/**
	 * @return beginNo
	 */
	public int getBeginNo() {
		return beginNo;
	}

	/**
	 * @param beginNo
	 */
	public void setBeginNo(int beginNo) {
		this.beginNo = beginNo;
	}

	/**
	 * @return endNo
	 */
	public int getEndNo() {
		return endNo;
	}

	/**
	 * @param endNo
	 */
	public void setEndNo(int endNo) {
		this.endNo = endNo;
	}

	/**
	 * @return beginWithEndNo
	 */
	public String getBeginWithEndNo() {
		return beginWithEndNo;
	}

	/**
	 * @param beginWithEndNo
	 */
	public void setBeginWithEndNo(String beginWithEndNo) {
		this.beginWithEndNo = beginWithEndNo;
	}

	/**
	 * @return noteAppIds
	 */
	public List<String> getNoteAppIds() {
		return noteAppIds;
	}

	/**
	 * @param noteAppIds
	 */
	public void setNoteAppIds(List<String> noteAppIds) {
		this.noteAppIds = noteAppIds;
	}

}
