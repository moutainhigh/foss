package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.dto.DvrReturnCodDto;

/**
 * 退代收货款参数Vo.
 *
 * @author foss-pengzhen
 * @date 2013-3-6 上午10:57:16
 * @since
 * @version
 */
public class DvrReturnCodVo implements Serializable {

	/** 序列号. */
	private static final long serialVersionUID = -5329344548543419597L;
	
	/** 退代收货款dto集合. */
	private List<DvrReturnCodDto> dvrReturnCodDtos;
	
	/** 退代收货款dto. */
	private DvrReturnCodDto dvrReturnCodDto;

	
	/**
	 * @return  the dvrReturnCodDtos
	 */
	public List<DvrReturnCodDto> getDvrReturnCodDtos() {
		return dvrReturnCodDtos;
	}

	
	/**
	 * @param dvrReturnCodDtos the dvrReturnCodDtos to set
	 */
	public void setDvrReturnCodDtos(List<DvrReturnCodDto> dvrReturnCodDtos) {
		this.dvrReturnCodDtos = dvrReturnCodDtos;
	}

	
	/**
	 * @return  the dvrReturnCodDto
	 */
	public DvrReturnCodDto getDvrReturnCodDto() {
		return dvrReturnCodDto;
	}

	
	/**
	 * @param dvrReturnCodDto the dvrReturnCodDto to set
	 */
	public void setDvrReturnCodDto(DvrReturnCodDto dvrReturnCodDto) {
		this.dvrReturnCodDto = dvrReturnCodDto;
	}
	
	
}
