package com.deppon.foss.module.transfer.dubbo.api.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.dubbo.api.define.AirWaybillDetailDto;
import com.deppon.foss.module.transfer.dubbo.api.define.AirWaybillEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.ExternalBillDto;
import com.deppon.foss.module.transfer.dubbo.api.define.InOutStockEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.LabeledGoodEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.LdpExternalBillDto;
import com.deppon.foss.module.transfer.dubbo.api.define.StockDto;
import com.deppon.foss.module.transfer.dubbo.api.define.StockEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillInfoByWaybillNoReultEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillRelateDetailEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillStockEntity;

public interface ITransfer2CrmIntegratedDao4dubbo {
	/**
	 * 根据运单号查询运单信息
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param waybillNO
	 * @return WaybillInfoByWaybillNoReultEntity
	 */
	WaybillInfoByWaybillNoReultEntity queryWaybillInfoByWaybillNo(String waybillNO);

	/**
	 * 通过运单号查询提货网点名称
	 * 
	 * @author panguoyang
	 * @date 2013-7-30下午9:10:10
	 */
	String queryCustomerPickupOrgNameByWayno(String waybillNo, String productCode);

	/**
	 * 通过运单号查询出发部门名称
	 * 
	 * @author panguoyang
	 * @date 2013-7-30下午9:10:10
	 */
	String queryReceiveOrgNameByWayno(String waybillNO);

	/**
	 * 通过运单编号查询运单
	 * 
	 * @param waybill
	 */
	WaybillEntity queryWaybillByNo(String waybillNo);

	/**
	 * 根据运单号查询货件库存
	 * 
	 * @param waybillStockEntity
	 * @return
	 */
	List<StockEntity> queryStockByWaybillNo(WaybillStockEntity waybillStockEntity);

	/**
	 * 
	 * 通过运单号查询所有流水号
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-30 下午4:55:34
	 * @param waybillNo
	 * @return
	 * @see
	 */
	List<LabeledGoodEntity> queryAllSerialByWaybillNo(String waybillNo);

	/**
	 * 根据运单号查询所在航空正单的配载类型
	 * 
	 * @param AirWaybillDetailDto
	 * @return the list
	 * @author 200968 zwd
	 * @date 2015-04-24 上午15:36:32
	 */
	List<AirWaybillEntity> queryAirWayBillListByWayBill(AirWaybillDetailDto airWaybillDetailDto);

	/**
	 * @author zwd 200968
	 * @date 2015-04-24
	 * @function 根据运单号查询偏线外发信息
	 * @param dto
	 * @return
	 */
	public List<ExternalBillDto> selectExternalByWayBillNo(ExternalBillDto dto);

	List<LdpExternalBillDto> queryExternalBillListByWaybillNo(String waybillNo);

	List<String> querySerialNoByWaybillNo(StockDto dto);

	List<InOutStockEntity> queryInStockInfoSmall(String waybillNo, String serialNo, String orgCode,
			Date createBillTime);

	List<StockEntity> queryStockByWaybillNoInStockTime(WaybillStockEntity waybillStockEntity);
}
