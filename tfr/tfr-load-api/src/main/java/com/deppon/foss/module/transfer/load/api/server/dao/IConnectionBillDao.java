
package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.ConnectionBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ConnectionBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SerialNoStockEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.HandOverBillDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryArrivalConnectionBillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryConnectionBillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillNoForUnloadTaskLackGoodsDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QuerySerialNoListForWaybillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryWayBillForConnectionBillDto;

/** 
 * @className: IConnectionBillDao
 * @author: zenghaibin-foss-205109 
 * @description:接驳交接单dao接口
 * @date: 2015-04-09 下午03:29:21
 */
public interface IConnectionBillDao {
	
	/**
	 *用于查询接驳交接单信息
	 *@author 205109-foss-zenghaibin
	 * @date 2015-04-09
	 * @param connectionBillVo 页面传递的查询条件参数（交接单号，部门信息，时间等）
	 * @param limit 分页参数
	 * @param start 分页参数
	 * @return List<ConnectionBillEntity>  返回交接单基本信息实体List
	 ****/
	public List<ConnectionBillEntity> queryConnectionBillList(QueryConnectionBillConditionDto queryConnectionBillConditionDto,int limit,int start);
	
	/**
	 *用于查询接驳交接单信息
	 * @author 218427-foss-hongwy
	 * @date 2015-10-029
	 * @param arrivalConnectionBillVo 页面传递的查询条件参数（交接单号，部门信息，时间等）
	 * @param limit 分页参数
	 * @param start 分页参数
	 * @return List<ConnectionBillEntity>  返回交接单基本信息实体List
	 ****/
	public List<ConnectionBillEntity> queryArrivalConnectionBillList(
			QueryArrivalConnectionBillConditionDto queryArrivalConnectionBillConditionDto, int limit, int start);
	/**
	 *用于查询接驳交接单信息总条数
	 *@author 205109-foss-zenghaibin
	 * @date 2015-04-09
	 * @param queryConnectionBillConditionDto 页面传递的查询条件参数（交接单号，部门信息，时间等）
	 ****/
	public Long queryConnectionBillListCount(QueryConnectionBillConditionDto queryConnectionBillConditionDto);
	
	/**
	 *用于查询接驳交接单信息总条数
	 *@author 218427-foss-hongwy
	 * @date 2015-10-30
	 * @param queryArrivalConnectionBillConditionDto 页面传递的查询条件参数（交接单号，部门信息，时间等）
	 ****/
	public Long queryArrivalConnectionBillListConut(
			QueryArrivalConnectionBillConditionDto dto);

	/**
	 *保存接驳交接单
	 *@author 205109-foss-zenghaibin
	 * @date 2015-05-05
	 * @param connectionBillEntity 接驳交接单实体
	 * @param unSavedWaybillStockList 交接单明细
	 * @param unsavedSerialNoStockList 交接单流水明细
	 ****/
	public void saveConnectionBill(ConnectionBillEntity connectionBillEntity,List<ConnectionBillDetailEntity> unSavedWaybillStockList,List<HandOverBillSerialNoDetailEntity> unsavedSerialNoStockList);
	

	/**
	 *用于查询库存运单
	 *@author 205109-foss-zenghaibin
	 * @date 2015-04-09
	 * @param connectionBillVo 页面传递的查询条件参数（交接单号，部门信息，时间等）
	 * @return List<ConnectionBillDetailEntity> 交接单明细list
	 ****/
	public   List<ConnectionBillDetailEntity> queryWaybillStockList(QueryWayBillForConnectionBillDto wayBillDto,int limit,int start);

	
	/**
	 * 查询运单流水
	 * @author 205109-foss-shiwei
	 * @date 2015-05-08 下午3:36:13
	 * @param waybillNo运单号，storageDept库存部门
	 */
	public List<SerialNoStockEntity> querySerialNoStockList(QuerySerialNoListForWaybillConditionDto querySerialNoListForWaybillConditionDto) ;
		
	/**查询库存运单数量 分页用
	 *@author 205109-foss-zenghaibin
	 * @date 2015-04-09
	 * @param connectionBillVo （运单号，入库时间，运输性质等）
	 * @return Long
	 ***/
	public  Long queryWaybillStockCount(QueryWayBillForConnectionBillDto wayBillDto);
	
	/**
	 * 通过交接单号查询交接单实体
	 * @author 205109-foss-zenghaibin
	 * @date 2015-04-09 10:09:30
	 * @param connctionBillNo 接驳交接单号
	 ***/
	public ConnectionBillEntity queryConnectionBillByNo(String connctionBillNo);
	
	/**
	 * 通过交接单号查询交接单实体
	 * @author 218427-foss-hongwy
	 * @date 2015-11-01 15:15:09
	 * @param arrivalConnctionBillNo 接驳交接单号
	 ***/
	public ConnectionBillEntity queryArrivalConnectionBillByNo(String arrivalConnectionBillNo);
	/**
	 *
	 * @author 205109-foss-zenghaibin
	 * @date 2015-04-09 10:09:30
	 * @param connctionBillNo 接驳交接单号
	 ***/
	public List<ConnectionBillDetailEntity>  queryConnectionBillDetailByNo(String connctionBillNo,String wayBillNo);
	
	/**
	 * 通过交接单号查询交接单实体
	 * @author 218427-foss-hongwy
	 * @date 2015-10-31 15:46:30
	 * @param arrivalConnctionBillNo 接驳交接单号
	 ***/
	public List<ConnectionBillDetailEntity> queryArrivalConnectionBillDetailByNo(
			String arrivalConnectionBillNo);
	/**
	 *通过交接单号,运单号查流水明细
	 * @author 205109-foss-zenghaibin
	 * @date 2015-04-09 10:09:30
	 ***/
	public List<HandOverBillSerialNoDetailEntity>  queryWaybillDetailByNos(String connctionBillNo,String waybillNo);
	
	/**
	 *通过交接单号,运单号查流水明细
	 * @author 218427-foss-hongwy
	 * @date 2015-10-31 17:12:30
	 ***/
	public List<HandOverBillSerialNoDetailEntity> queryArrivalWaybillDetailByNos(
			String connectionBillNo, String waybillNo);
	/**
	 * 修改交接单后保存数据方法
	 * @author 205109-foss-zenghaibin
	 * @date 2014-05-12 下午:6:25
	 * @param connectionBillEntity 修改后的交接单基本信息实体
	 				   deletedWaybillList 被删除的交接单内运单list
	 				   updatedWaybillList 更新的交接单内运单list
	 				   deletedSerialNoList 被删除的流水号list
	 */
	boolean updateConnectionBill(ConnectionBillEntity connectionBillEntity,
			List<ConnectionBillDetailEntity> deletedWaybillList,
			List<ConnectionBillDetailEntity> updatedWaybillList,
			List<HandOverBillSerialNoDetailEntity> deletedSerialNoList
			);
		
	/**
	 * 修改交接单后保存数据方法
	 * @author 205109-foss-zenghaibin
	 * @date 2014-05-12 下午:6:25
	 * @param connectionBillNo
	 * @param targtState
	 */
	public void updateConnectionBillStateByNo(String connectionBillNo,int targtState);
	
	/**
	 * 根据接驳交接单号查询出打印清单中需要的数据 
	 * @author zenghaibin-foss-205109
	 * @param connectionBillNo
	 * @return List<connectionBillNo>
	 * @exception 无
	 * @date 2015-05-29 上午9:33:17
	 */
	public List<HandOverBillDetailDto> queryPrintConnectionBillDataByNo(String connectionBillNo);
	
	/**
	 * 根据接驳交接单号查询出打印清单中需要的数据 
	 * @author zenghaibin-foss-205109
	 * @param connectionBillNo
	 * @return String
	 * @exception 无
	 * @date 2015-05-29 上午9:33:17
	 */
	public List<String> queryConnectionBillNoForUnloadTaskLackGoods(QueryHandOverBillNoForUnloadTaskLackGoodsDto queryDto);



	
    
	
		
}