package com.deppon.foss.module.settlement.pay.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.dto.RentCarReportDetailDto;
/**
 * 
 * @author maojianqiang
 * @date 2014-8-7 下午2:27:06
 */
public class RentCarReportDetailVo {
    
	/**
      * 租车明细返回的Dto 
      */
     private RentCarReportDetailDto rentCarReportDetailDto;
     
     /**
      * 租车明细返回的Dto集合
      */
     private List<RentCarReportDetailDto> rentCarReportDetailDtoList;
     
 	/**
 	 * 导出对账单列头英文名称
 	 */
 	private String[] arrayColumns;
 	/**
 	 * 导出对账单列头中文名称
 	 */
 	private String[] arrayColumnNames;

	public RentCarReportDetailDto getRentCarReportDetailDto() {
		return rentCarReportDetailDto;
	}

	public void setRentCarReportDetailDto(
			RentCarReportDetailDto rentCarReportDetailDto) {
		this.rentCarReportDetailDto = rentCarReportDetailDto;
	}

	public List<RentCarReportDetailDto> getRentCarReportDetailDtoList() {
		return rentCarReportDetailDtoList;
	}

	public void setRentCarReportDetailDtoList(
			List<RentCarReportDetailDto> rentCarReportDetailDtoList) {
		this.rentCarReportDetailDtoList = rentCarReportDetailDtoList;
	}

	public String[] getArrayColumnNames() {
		return arrayColumnNames;
	}

	public void setArrayColumnNames(String[] arrayColumnNames) {
		this.arrayColumnNames = arrayColumnNames;
	}

	public String[] getArrayColumns() {
		return arrayColumns;
	}

	public void setArrayColumns(String[] arrayColumns) {
		this.arrayColumns = arrayColumns;
	}
     
     
}
