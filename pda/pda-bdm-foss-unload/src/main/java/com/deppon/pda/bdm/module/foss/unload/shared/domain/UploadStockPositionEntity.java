package com.deppon.pda.bdm.module.foss.unload.shared.domain;

import java.util.List;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

public class UploadStockPositionEntity extends ScanMsgEntity{

	
	private  List<StockPositionList>  stockList;
	
	/**  
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 1L;

	public List<StockPositionList> getStockList() {
		return stockList;
	}

	public void setStockList(List<StockPositionList> stockList) {
		this.stockList = stockList;
	}
	
	

	
}
