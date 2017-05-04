/**
 * @author foss 257200
 * 2015-8-15
 * 257220
 */
package com.deppon.foss.module.transfer.partialline.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintCZMTipsEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.PrintCZMTipsDto;

/**
 * @author 257220
 *
 */
public class PrintTipsVo implements Serializable{


	

	private static final long serialVersionUID = 1L;

	private String waybillNos; //
	
	private String handOverBillNos;
	
	private String tips;//界面提示信息
	
	private List<PrintCZMTipsEntity> printTipsList;
	
	private List<PrintCZMTipsDto> list;

	private PrintCZMTipsEntity printCZMTipsEntity;
	
	private PrintCZMTipsDto printCZMTipsDto;
	/**
	 * @return the handOverBillNos
	 */
	public String getHandOverBillNos() {
		return handOverBillNos;
	}

	/**
	 * @param handOverBillNos the handOverBillNos to set
	 */
	public void setHandOverBillNos(String handOverBillNos) {
		this.handOverBillNos = handOverBillNos;
	}

	/**
	 * @return the waybillNos
	 */
	public String getWaybillNos() {
		return waybillNos;
	}

	/**
	 * @param waybillNos the waybillNos to set
	 */
	public void setWaybillNos(String waybillNos) {
		this.waybillNos = waybillNos;
	}

	/**
	 * @return the printTipsList
	 */
	public List<PrintCZMTipsEntity> getPrintTipsList() {
		return printTipsList;
	}

	/**
	 * @param printTipsList the printTipsList to set
	 */
	public void setPrintTipsList(List<PrintCZMTipsEntity> printTipsList) {
		this.printTipsList = printTipsList;
	}



	public List<PrintCZMTipsDto> getList() {
		return list;
	}

	public void setList(List<PrintCZMTipsDto> list) {
		this.list = list;
	}

	public PrintCZMTipsEntity getPrintCZMTipsEntity() {
		return printCZMTipsEntity;
	}

	public void setPrintCZMTipsEntity(PrintCZMTipsEntity printCZMTipsEntity) {
		this.printCZMTipsEntity = printCZMTipsEntity;
	}

	public PrintCZMTipsDto getPrintCZMTipsDto() {
		return printCZMTipsDto;
	}

	public void setPrintCZMTipsDto(PrintCZMTipsDto printCZMTipsDto) {
		this.printCZMTipsDto = printCZMTipsDto;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}
}
