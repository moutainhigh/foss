/**
 * 
 */
package com.deppon.foss.module.transfer.management.api.shared.dto;

import com.deppon.foss.module.transfer.management.api.shared.domain.FuelStandardEntity;


/**
 * @author niuly
 * @function 油耗标准导入DTO
 * @date 2013-11-15 19:07:23
 */
public class FuelStandardExcelDto extends FuelStandardEntity{

	private static final long serialVersionUID = -7304995300740096716L;
	//行号
	private int rowNum;

	/**
	 * @return the rowNum
	 */
	public int getRowNum() {
		return rowNum;
	}

	/**
	 * @param rowNum the rowNum to set
	 */
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

}
