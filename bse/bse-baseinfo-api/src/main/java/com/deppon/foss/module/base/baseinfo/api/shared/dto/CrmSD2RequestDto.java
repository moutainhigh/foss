package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;


//@XmlAccessorType(XmlAccessType.FIELD)
public class CrmSD2RequestDto  implements Serializable{

    
    /**
	 * 
	 */
	private static final long serialVersionUID = -45646546546541L;
	
	private SpecialdiscountDto specialdiscountDto;

	public SpecialdiscountDto getSpecialdiscountDto() {
		return specialdiscountDto;
	}

	public void setSpecialdiscountDto(SpecialdiscountDto specialdiscountDto) {
		this.specialdiscountDto = specialdiscountDto;
	}
	
}