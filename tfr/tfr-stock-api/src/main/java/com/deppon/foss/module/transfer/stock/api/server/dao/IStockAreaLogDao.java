package com.deppon.foss.module.transfer.stock.api.server.dao;

import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;

/**
* @description 本接口主要是对库位的操作(已经在库存中的数据)。
* @version 1.0
* @author 140022-foss-songjie
* @update 2013-7-3 下午1:45:33
*/

public interface IStockAreaLogDao {
	/**
	* @param inOutStockEntity
	* @return 保存库位记录(跟入库记录一样的表结构，加了库位属性)
	* @description 
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-3 下午2:34:21
	*/
	public int insertStockAreaLog(InOutStockEntity inOutStockEntity);
	
	/**
	* @param inOutStockEntity  运单号、流水号、货区编码、部门编号、库位 不能为空
	* @return 
	* @description 修改库存表的库位  
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-3 下午2:38:07
	*/
	public int updateStockStockPosition(InOutStockEntity inOutStockEntity);
	
	
	/**
	* @param inOutStockEntity 运单号、货区编码、部门编号不能为空
	* @return
	* @description 修改运单库存表的库位件数
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-3 下午2:40:02
	*/
	public int updateWaybillStockOutStockPosition(InOutStockEntity inOutStockEntity);
}
