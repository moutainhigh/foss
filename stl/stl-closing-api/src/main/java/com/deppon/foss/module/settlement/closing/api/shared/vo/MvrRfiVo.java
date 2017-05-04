package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrRfiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrRfiDto;

/**
 * 出发到达往来报表vo.
 *
 * @author 045738-foss-maojianqiang
 * @date 2013-3-7 下午2:19:27
 */
public class MvrRfiVo implements Serializable{

	/** 序列哈. */
	private static final long serialVersionUID = -4377951400654715462L;
	
	/** 注入dto. */
	private MvrRfiDto dto;
	
	/** 注入结果list. */
	private List<MvrRfiEntity> list;
	
	/**
	 * Gets the dto.
	 *
	 * @return dto
	 */
	public MvrRfiDto getDto() {
		return dto;
	}
	
	/**
	 * Sets the dto.
	 *
	 * @param dto the new dto
	 */
	public void setDto(MvrRfiDto dto) {
		this.dto = dto;
	}
	
	/**
	 * Gets the list.
	 *
	 * @return list
	 */
	public List<MvrRfiEntity> getList() {
		return list;
	}
	
	/**
	 * Sets the list.
	 *
	 * @param list the new list
	 */
	public void setList(List<MvrRfiEntity> list) {
		this.list = list;
	}
		
	
	
}
