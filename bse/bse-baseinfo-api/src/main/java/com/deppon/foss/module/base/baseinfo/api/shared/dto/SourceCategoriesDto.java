package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SourceCategoriesEntity;

/**
 * 行业货源类别基础资料Dto
 * @author 198771
 *
 */
public class SourceCategoriesDto extends SourceCategoriesEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -364646464646449L;
	/**
	 * 创建人编码
	 */
	private String createUserCode;
	/**
	 * 修改人编码
	 */
	private String modifyUserCode;

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	
	

}
