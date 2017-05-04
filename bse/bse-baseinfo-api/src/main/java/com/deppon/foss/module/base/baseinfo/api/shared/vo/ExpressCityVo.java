package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressCityQueryDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressCityResultDto;

/**
 * 试点城市vo
 * @author foss-qiaolifeng
 * @date 2013-7-16 下午4:20:05
 */
public class ExpressCityVo implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3505298116387962308L;

	/**
	 * 试点城市查询dto
	 */
	private ExpressCityQueryDto expressCityQueryDto;
	
	/**
	 * 试点城市结果集dto
	 */
	private List<ExpressCityResultDto> expressCityResultDtoList;
	
	/**
	 * 试点城市结果dto
	 * 
	 */
	private ExpressCityResultDto expressCityResultDto;

	public ExpressCityQueryDto getExpressCityQueryDto() {
		return expressCityQueryDto;
	}

	public void setExpressCityQueryDto(ExpressCityQueryDto expressCityQueryDto) {
		this.expressCityQueryDto = expressCityQueryDto;
	}

	public List<ExpressCityResultDto> getExpressCityResultDtoList() {
		return expressCityResultDtoList;
	}

	public void setExpressCityResultDtoList(
			List<ExpressCityResultDto> expressCityResultDtoList) {
		this.expressCityResultDtoList = expressCityResultDtoList;
	}

	public ExpressCityResultDto getExpressCityResultDto() {
		return expressCityResultDto;
	}

	public void setExpressCityResultDto(ExpressCityResultDto expressCityResultDto) {
		this.expressCityResultDto = expressCityResultDto;
	}


	
}
