package com.deppon.foss.module.settlement.consumer.api.shared.vo;

import java.io.Serializable;
import java.util.List;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.NoteStockInEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteApplyDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteStockInDto;

/**
 * 小票单据与ACTION交互VO
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-10-23 上午8:14:35
 */
public class NoteVo implements Serializable {

	private static final long serialVersionUID = -8368576667915195072L;

	/**
	 * 查询页类型
	 */
	private String queryPage;

	/**
	 * 小票单据申请DTO
	 */
	private NoteApplyDto noteApplyDto;

	/**
	 * 小票单据入库DTO
	 */
	private NoteStockInDto noteStockInDto;

	/**
	 * 小票单据明细DTO
	 */
	private NoteQueryDto noteQueryDto;

	/**
	 * 小票单据申请数据列表
	 */
	private List<NoteApplyDto> noteApplyEntityList;

	/**
	 * 小票单据明细数据列表
	 */
	private List<NoteQueryDto> noteQueryDtoList;

	/**
	 * 小票单据入库数据列表
	 */
	private List<NoteStockInEntity> noteStockInEntityList;

	/**
	 * @return queryPage
	 */
	public String getQueryPage() {
		return queryPage;
	}

	/**
	 * @param queryPage
	 */
	public void setQueryPage(String queryPage) {
		this.queryPage = queryPage;
	}

	/**
	 * @return noteApplyDto
	 */
	public NoteApplyDto getNoteApplyDto() {
		return noteApplyDto;
	}

	/**
	 * @param noteApplyDto
	 */
	public void setNoteApplyDto(NoteApplyDto noteApplyDto) {
		this.noteApplyDto = noteApplyDto;
	}

	/**
	 * @return noteStockInDto
	 */
	public NoteStockInDto getNoteStockInDto() {
		return noteStockInDto;
	}

	/**
	 * @param noteStockInDto
	 */
	public void setNoteStockInDto(NoteStockInDto noteStockInDto) {
		this.noteStockInDto = noteStockInDto;
	}

	/**
	 * @return noteQueryDto
	 */
	public NoteQueryDto getNoteQueryDto() {
		return noteQueryDto;
	}

	/**
	 * @param noteQueryDto
	 */
	public void setNoteQueryDto(NoteQueryDto noteQueryDto) {
		this.noteQueryDto = noteQueryDto;
	}

	/**
	 * @return noteQueryDtoList
	 */
	public List<NoteQueryDto> getNoteQueryDtoList() {
		return noteQueryDtoList;
	}

	/**
	 * @param noteQueryDtoList
	 */
	public void setNoteQueryDtoList(List<NoteQueryDto> noteQueryDtoList) {
		this.noteQueryDtoList = noteQueryDtoList;
	}

	/**
	 * @return noteStockInEntityList
	 */
	public List<NoteStockInEntity> getNoteStockInEntityList() {
		return noteStockInEntityList;
	}

	/**
	 * @param noteStockInEntityList
	 */
	public void setNoteStockInEntityList(
			List<NoteStockInEntity> noteStockInEntityList) {
		this.noteStockInEntityList = noteStockInEntityList;
	}

	/**
	 * @return noteApplyEntityList
	 */
	public List<NoteApplyDto> getNoteApplyEntityList() {
		return noteApplyEntityList;
	}

	/**
	 * @param noteApplyEntityList
	 */
	public void setNoteApplyEntityList(List<NoteApplyDto> noteApplyEntityList) {
		this.noteApplyEntityList = noteApplyEntityList;
	}

}
