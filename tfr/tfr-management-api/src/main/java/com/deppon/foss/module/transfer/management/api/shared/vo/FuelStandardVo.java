/**
 * 
 */
package com.deppon.foss.module.transfer.management.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.management.api.shared.dto.FuelStandardExcelDto;

/**
 * @author Administrator
 *
 */
public class FuelStandardVo implements Serializable {

	private static final long serialVersionUID = -5411726271875920754L;
	//油耗实体
	private FuelStandardExcelDto fuelStandardExcelDto;
	//油耗标准dtolist
	private List<FuelStandardExcelDto> fuelStandardDtoList;
	//插入总条数
	private int importTotalCount;
	
	/**
	 * @return the fuelStandardExcelDto
	 */
	public FuelStandardExcelDto getFuelStandardExcelDto() {
		return fuelStandardExcelDto;
	}
	/**
	 * @param fuelStandardExcelDto the fuelStandardExcelDto to set
	 */
	public void setFuelStandardExcelDto(FuelStandardExcelDto fuelStandardExcelDto) {
		this.fuelStandardExcelDto = fuelStandardExcelDto;
	}
	/**
	 * @return the fuelStandardDtoList
	 */
	public List<FuelStandardExcelDto> getFuelStandardDtoList() {
		return fuelStandardDtoList;
	}
	/**
	 * @param fuelStandardDtoList the fuelStandardDtoList to set
	 */
	public void setFuelStandardDtoList(
			List<FuelStandardExcelDto> fuelStandardDtoList) {
		this.fuelStandardDtoList = fuelStandardDtoList;
	}
	/**
	 * @return the importTotalCount
	 */
	public int getImportTotalCount() {
		return importTotalCount;
	}
	/**
	 * @param importTotalCount the importTotalCount to set
	 */
	public void setImportTotalCount(int importTotalCount) {
		this.importTotalCount = importTotalCount;
	}
	
}
