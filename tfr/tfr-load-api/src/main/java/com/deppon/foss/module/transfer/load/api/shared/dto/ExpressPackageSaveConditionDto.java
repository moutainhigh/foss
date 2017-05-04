package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageEntity;

/**
 * 新增快递包条件Dto，在service中进行组装成新的Dto：ExpressPackageDetailSaveDto
 * @author 200978  xiaobingcheng
 * 2014-8-23
 */
public class ExpressPackageSaveConditionDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5846695635344652436L;
	
	/**包实体*/
	private ExpressPackageEntity packageEntity;
	
	/**运单list*/
	private List<ExpressPackageWayBillDetailDto> waybillStockList;
	
	/**库存流水号*/
	private List<ExpressPackageSerialNoStockDto> serialNoStockList;
	
	/**是否包号已经存在*/
	private boolean isExistPackageNo;
	
	public boolean getIsExistPackageNo() {
		return isExistPackageNo;
	}

	public void setIsExistPackageNo(boolean isExistPackageNo) {
		this.isExistPackageNo = isExistPackageNo;
	}

	public ExpressPackageEntity getPackageEntity() {
		return packageEntity;
	}

	public void setPackageEntity(ExpressPackageEntity packageEntity) {
		this.packageEntity = packageEntity;
	}

	public List<ExpressPackageWayBillDetailDto> getWaybillStockList() {
		return waybillStockList;
	}

	public void setWaybillStockList(
			List<ExpressPackageWayBillDetailDto> waybillStockList) {
		this.waybillStockList = waybillStockList;
	}

	public List<ExpressPackageSerialNoStockDto> getSerialNoStockList() {
		return serialNoStockList;
	}

	public void setSerialNoStockList(List<ExpressPackageSerialNoStockDto> serialNoStockList) {
		this.serialNoStockList = serialNoStockList;
	}


}
