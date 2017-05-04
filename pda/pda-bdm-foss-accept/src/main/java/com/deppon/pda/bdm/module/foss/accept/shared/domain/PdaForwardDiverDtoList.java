package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;
import java.util.List;

public class PdaForwardDiverDtoList implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * PDA可转发快递员实体list
	 */
	List<PdaForwardDiverDto> pdadriverlist;

	/**
	 * 
	 * @return PDA可转发快递员实体list
	 */
	public List<PdaForwardDiverDto> getPdadriverlist() {
		return pdadriverlist;
	}

	/**
	 * 
	 * @param pdadriverlist
	 */
	public void setPdadriverlist(List<PdaForwardDiverDto> pdadriverlist) {
		this.pdadriverlist = pdadriverlist;
	}
}
