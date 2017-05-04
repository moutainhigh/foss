/**
 * 
 */
package com.deppon.foss.module.transfer.dubbo.api.service;

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
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillInfoByWaybillNoReultEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillStockEntity;

/**
 * 原来的接口MatchTaskOrgService引用了太多接口，这里把那些接口方法集成起来放在这个接口：<br>
 * IWaybillQueryService、IStockService、ILabeledGoodService、IAirWaybillService、
 * IExternalBillService、ILdpExternalBillService、ISignDetailService
 * 
 * @author 335284
 * @since 2016.11.23
 */
public interface ITransfer2CrmIntegratedService4dubbo {

	/**
	 * 根据运单号查询运单信息
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param waybillNO
	 * @return WaybillInfoByWaybillNoReultDto
	 */
	WaybillInfoByWaybillNoReultEntity queryWaybillInfoByWaybillNo(String waybillNO);

	/**
	 * 根据运单号查询货件库存
	 * 
	 * @param waybillStockEntity
	 * @return
	 */
	List<StockEntity> queryStockByWaybillNo(WaybillStockEntity waybillStockEntity);

	/**
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
	 * @author zwd 2009687
	 * @date 2015-04-24
	 * @function 根据运单号查询偏线外发信息
	 * @param dto
	 * @return
	 */
	List<ExternalBillDto> selectExternalByWayBillNo(ExternalBillDto dto);

	/**
	 * 通过运单号查询外发单信息列表
	 * 
	 * @author zwd 200968
	 * @date 2015-04-24
	 * @param waybillNo
	 * @return
	 */
	List<LdpExternalBillDto> queryExternalBillListByWaybillNo(String waybillNo);

	/**
	 * 给综合查询提供 已签收的流水号接口，参数（运单号，有效，是否作废，到达联状态是签收）
	 * 
	 * @author foss-meiying
	 * @date 2013-8-6 上午16:12:15
	 * @param waybillNo
	 *            运单号
	 * @param serialNo
	 *            流水号
	 * @return String
	 * @see
	 */
	List<String> querySerialNoByWaybillNo(StockDto dto);

	/**
	 * @param waybillNo
	 * @param serialNo
	 * @param orgCode
	 * @param createBillTime
	 * @return
	 * @description 查询小于入库时间的入库动作记录
	 * @version 1.0
	 * @author 140022-foss-songjie
	 * @update 2013-7-27 下午1:52:19
	 */
	List<InOutStockEntity> queryInStockInfoSmall(String waybillNo, String serialNo, String orgCode,
			Date createBillTime);

	List<StockEntity> queryStockByWaybillNoInStockTime(WaybillStockEntity waybillStockEntity);
}
