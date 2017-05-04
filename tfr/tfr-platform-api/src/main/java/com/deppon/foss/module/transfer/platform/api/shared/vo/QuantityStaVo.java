/**   
* @Title: QuantityStaVo.java 
* @Package com.deppon.foss.module.transfer.platform.api.shared.vo 
* @author shiwei-045923-shiwei@outlook.com
* @date 2014年8月29日 上午11:01:37 
* @version V1.0   
*/
package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaQcDto;

/** 
 * @ClassName: QuantityStaVo 
 * @Description: 货量统计Vo
 * @author shiwei-045923-shiwei@outlook.com
 * @date 2014年8月29日 上午11:01:37 
 *  
 */
public class QuantityStaVo {
	
	/**
	 * 对应转运场code
	 */
	private String transferCenterCode;
	
	/**
	 * 对应转运场name
	 */
	private String transferCenterName;
	
	/**
	 * 货量统计起始时间
	 */
	private String staStartTime;
	
	/**
	 * dto
	 */
	private QuantityStaDto quantityStaDto;
	
	/**
	 * 查询结果集
	 */
	private List<QuantityStaDto> quantityStaDtoList;
	
	/**
	 * 查询条件
	 */
	private QuantityStaQcDto quantityStaQcDto;
	
	/**
	 * 导出无结果时返回的字符串
	 */
	private String exportError;

	public String getExportError() {
		return exportError;
	}

	public void setExportError(String exportError) {
		this.exportError = exportError;
	}

	public String getTransferCenterName() {
		return transferCenterName;
	}

	public void setTransferCenterName(String transferCenterName) {
		this.transferCenterName = transferCenterName;
	}

	public QuantityStaQcDto getQuantityStaQcDto() {
		return quantityStaQcDto;
	}

	public void setQuantityStaQcDto(QuantityStaQcDto quantityStaQcDto) {
		this.quantityStaQcDto = quantityStaQcDto;
	}

	/** 
	 * @return quantityStaDtoList 
	 */
	public List<QuantityStaDto> getQuantityStaDtoList() {
		return quantityStaDtoList;
	}

	/** 
	 * @param quantityStaDtoList 要设置的 quantityStaDtoList 
	 */
	public void setQuantityStaDtoList(List<QuantityStaDto> quantityStaDtoList) {
		this.quantityStaDtoList = quantityStaDtoList;
	}

	/** 
	 * @return quantityStaDto 
	 */
	public QuantityStaDto getQuantityStaDto() {
		return quantityStaDto;
	}

	/** 
	 * @param quantityStaDto 要设置的 quantityStaDto 
	 */
	public void setQuantityStaDto(QuantityStaDto quantityStaDto) {
		this.quantityStaDto = quantityStaDto;
	}

	/** 
	 * @return transferCenterCode 
	 */
	public String getTransferCenterCode() {
		return transferCenterCode;
	}

	/** 
	 * @param transferCenterCode 要设置的 transferCenterCode 
	 */
	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}

	public String getStaStartTime() {
		return staStartTime;
	}

	public void setStaStartTime(String staStartTime) {
		this.staStartTime = staStartTime;
	}

}
