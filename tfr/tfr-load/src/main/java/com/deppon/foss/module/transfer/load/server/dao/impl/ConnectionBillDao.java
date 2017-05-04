package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.IConnectionBillDao;
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
public class ConnectionBillDao extends iBatis3DaoImpl implements IConnectionBillDao{
	
	
	/**命名空间常量*/
	private static final String NAMESPACE = "foss.load.connectionbill.";
	/**记录日志*/
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	
	/**
	 *用于查询接驳交接单信息
	 *@author 205109-foss-zenghaibin
	 * @date 2015-04-09
	 * @param queryConnectionBillConditionDto 页面传递的查询条件参数（交接单号，部门信息，时间等）
	 * @param limit 分页参数
	 * @param start 分页参数
	 * @return List<ConnectionBillEntity>  返回交接单基本信息实体List
	 ****/
	@Override
	public List<ConnectionBillEntity> queryConnectionBillList(QueryConnectionBillConditionDto queryConnectionBillConditionDto,int limit,int start){
		
		//分页
		RowBounds rowBounds = new RowBounds(start, limit);
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryConnectionBillList",queryConnectionBillConditionDto,rowBounds);
	}
	
	/**
	 *用于查询接驳交接单信息
	 * @author 218427-foss-hongwy
	 * @date 2015-10-029
	 * @param arrivalConnectionBillVo 页面传递的查询条件参数（交接单号，部门信息，时间等）
	 * @param limit 分页参数
	 * @param start 分页参数
	 * @return List<ConnectionBillEntity>  返回交接单基本信息实体List
	 ****/
	@Override
	public List<ConnectionBillEntity> queryArrivalConnectionBillList(
			QueryArrivalConnectionBillConditionDto queryArrivalConnectionBillConditionDto, int limit, int start) {
		//分页
		RowBounds rowBounds = new RowBounds(start, limit);
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryArrivalConnectionBillList",queryArrivalConnectionBillConditionDto,rowBounds);
	}
	/**
	 *用于查询接驳交接单信息总条数
	 *@author 205109-foss-zenghaibin
	 * @date 2015-04-09
	 * @param queryConnectionBillConditionDto 页面传递的查询条件参数（交接单号，部门信息，时间等）
	 ****/
	@Override
	public Long queryConnectionBillListCount(QueryConnectionBillConditionDto queryConnectionBillConditionDto){
		
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "queryConnectionBillListCount",queryConnectionBillConditionDto);

	}
    
	/**
	 *用于查询接驳交接单信息总条数
	 *@author 218427-foss-hongwy
	 * @date 2015-10-31
	 * @param queryArrivalConnectionBillConditionDto 页面传递的查询条件参数（交接单号，部门信息，时间等）
	 ****/
	@Override
	public Long queryArrivalConnectionBillListConut(
			QueryArrivalConnectionBillConditionDto queryArrivalConnectionBillConditionDto) {
		
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"queryArrivalConnectionBillListCount",queryArrivalConnectionBillConditionDto);
	}
		

	/**
	 *用于查询库存运单
	 *@author 205109-foss-zenghaibin
	 * @date 2015-04-09
	 * @param connectionBillVo 页面传递的查询条件参数（交接单号，部门信息，时间等）
	 * @return List<ConnectionBillDetailEntity> 交接单明细list
	 ****/
	public   List<ConnectionBillDetailEntity> queryWaybillStockList(QueryWayBillForConnectionBillDto wayBillDto,int limit,int start){

		//分页
		RowBounds rowBounds = new RowBounds(start, limit);
		
	return this.getSqlSession().selectList(NAMESPACE+"queryWaybillStockList", wayBillDto, rowBounds);
	}

	

	/**
	 * 查询运单流水
	 * @author 205109-foss-shiwei
	 * @date 2015-05-08 下午3:36:13
	 * @param waybillNo运单号，storageDept库存部门
	 */
	@Override
	public List<SerialNoStockEntity> querySerialNoStockList(QuerySerialNoListForWaybillConditionDto querySerialNoListForWaybillConditionDto) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "querySerialNoStockList",querySerialNoListForWaybillConditionDto);
	}
	
	/**查询库存运单数量 分页用
	 *@author 205109-foss-zenghaibin
	 * @date 2015-04-09
	 * @param connectionBillVo （运单号，入库时间，运输性质等）
	 * @return Long
	 ***/
	public  Long queryWaybillStockCount(QueryWayBillForConnectionBillDto wayBillDto){
		
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryWaybillStockCount", wayBillDto);
	}
	
	
	/**
	 *保存接驳交接单
	 *@author 205109-foss-zenghaibin
	 * @date 2015-05-05
	 * @param connectionBillEntity 接驳交接单实体
	 * @param unSavedWaybillStockList 交接单明细
	 * @param unsavedSerialNoStockList 交接单流水明细
	 ****/
	@Override
	public void saveConnectionBill(ConnectionBillEntity connectionBillEntity,
			List<ConnectionBillDetailEntity> unSavedWaybillStockList,List<HandOverBillSerialNoDetailEntity> unsavedSerialNoStockList){
		
		this.saveBill(connectionBillEntity);
		this.saveBillDetail(unSavedWaybillStockList);
		this.saveBillSerialNo(unsavedSerialNoStockList);
	}
	
	
	/**
	 *通过交接单号查询交接单实体
	 * @author 205109-foss-zenghaibin
	 * @date 2015-04-09 10:09:30
	 * @param connctionBillNo 接驳交接单号
	 ***/
	@Override
	public ConnectionBillEntity queryConnectionBillByNo(String connctionBillNo){
		
		List<ConnectionBillEntity> list=this.getSqlSession().selectList(NAMESPACE+"queryConnectionBillByNo", connctionBillNo);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 *通过交接单号查询交接单实体
	 * @author 218427-foss-hongwy
	 * @date 2015-11-03 11:11:30
	 * @param arrivalConnctionBillNo 接驳交接单号
	 ***/	
	@Override
	public ConnectionBillEntity queryArrivalConnectionBillByNo(String arrivalConnectionBillNo){
		@SuppressWarnings("unchecked")
		List<ConnectionBillEntity> list = this.getSqlSession().selectList(NAMESPACE+"queryArrivalConnectionBillByNo",arrivalConnectionBillNo);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 *保存接驳交接单
	 *@author 205109-foss-zenghaibin
	 * @date 2015-05-05
	 * @param connectionBillEntity 接驳交接单实体
	**/
	
   private void saveBill(ConnectionBillEntity connectionBillEntity){
	  
	   this.getSqlSession().insert(NAMESPACE+"saveBill", connectionBillEntity);
   }	
   /**
	 *保存接驳交接单明细
	 *@author 205109-foss-zenghaibin
	 * @date 2015-05-05
	 * @param unSavedWaybillStockList 接驳交接单明细
	**/
  private void saveBillDetail(List<ConnectionBillDetailEntity> unSavedWaybillStockList){
	  
	   this.getSqlSession().insert(NAMESPACE+"saveBillDetail", unSavedWaybillStockList);

  }	
  /**
	 *保存接驳交接单流水
	 *@author 205109-foss-zenghaibin
	 * @date 2015-05-05
	 * @param unsavedSerialNoStockList 接驳交接单流水明细
	**/
 private void saveBillSerialNo(List<HandOverBillSerialNoDetailEntity> unsavedSerialNoStockList){
	   
	   this.getSqlSession().insert(NAMESPACE+"saveBillSerialNo", unsavedSerialNoStockList);

 }	
   
	
	/**
	 *通过交接单号查询交接单实体
	 * @author 205109-foss-zenghaibin
	 * @date 2015-04-09 10:09:30
	 * @param connctionBillNo 接驳交接单号
	 ***/
 	@Override
	public List<ConnectionBillDetailEntity>  queryConnectionBillDetailByNo(String connctionBillNo,String wayBillNo){
 		Map<String,String> hs=new HashMap<String,String>();
 		hs.put("connctionBillNo", connctionBillNo);
 		if(wayBillNo!=null){
 			hs.put("wayBillNo",wayBillNo);
 		}
		return this.getSqlSession().selectList(NAMESPACE+"queryConnectionBillDetailByNo", hs);
	}
 	
 	/**
	 *通过交接单号查询交接单实体
	 * @author 218427-foss-hongwy
	 * @date 2015-11-01 14:09:30
	 * @param connctionBillNo 接驳交接单号
	 ***/
 	@Override
 	public List<ConnectionBillDetailEntity> queryArrivalConnectionBillDetailByNo(String arrivalConnectionBillNo){
	    Map<String, String> hs = new HashMap<String, String>();
	    hs.put("arrivalConnectionBillNo", arrivalConnectionBillNo);
//	    if(waybillNo!=null){
//	    	hs.put("waybillNo", waybillNo);
//	    }
 		return this.getSqlSession().selectList(NAMESPACE+"queryArrivalConnectionBillDetailByNo",hs);
 		
 	}
 	/**
	 *通过交接单号,运单号查流水明细
	 * @author 205109-foss-zenghaibin
	 * @date 2015-04-09 10:09:30
	 ***/
	public List<HandOverBillSerialNoDetailEntity>   queryWaybillDetailByNos(String connctionBillNo,String waybillNo){
		Map<String,String> hs=new HashMap<String,String>();
 		hs.put("connctionBillNo", connctionBillNo);
 		hs.put("waybillNo",waybillNo);
		return this.getSqlSession().selectList(NAMESPACE+"queryWaybillDetailByNos", hs);
	}
		
	/**
	 *通过交接(到达)单号,运单号查流水明细
	 * @author 218427-foss-hongwy
	 * @date 2015-10-31 17:13:30
	 ***/
	public List<HandOverBillSerialNoDetailEntity> queryArrivalWaybillDetailByNos(String connctionBillNo,String waybillNo){
		Map<String,String> hs=new HashMap<String,String>();
		hs.put("connctionBillNo", connctionBillNo);
		hs.put("waybillNo", waybillNo);
		return this.getSqlSession().selectList(NAMESPACE+"queryArrivalWaybillDetailByNos",hs);
		
	}
	
	/**
	 * 修改交接单后保存数据方法
	 * @author 205109-foss-zenghaibin
	 * @date 2014-05-12 下午:6:25
	 * @param connectionBillEntity 修改后的交接单基本信息实体
	 				   deletedWaybillList 被删除的交接单内运单list
	 				   updatedWaybillList 更新的交接单内运单list
	 				   deletedSerialNoList 被删除的流水号list
	 */
	public boolean updateConnectionBill(ConnectionBillEntity connectionBillEntity,
			List<ConnectionBillDetailEntity> deleteWaybillList,
			List<ConnectionBillDetailEntity> updateWaybillList,
			List<HandOverBillSerialNoDetailEntity> deleteSerialNoList
			){
		this.getSqlSession().update(NAMESPACE+"updateConnectionBillBasicInfo", connectionBillEntity);
		if(deleteWaybillList != null && deleteWaybillList.size() > 0){
			this.getSqlSession().delete(NAMESPACE+"deleteWaybillList", deleteWaybillList);
		}
		if(updateWaybillList != null && updateWaybillList.size() > 0){
			this.getSqlSession().update(NAMESPACE+"updateWaybillList", updateWaybillList);
		}
		if(deleteSerialNoList != null && deleteSerialNoList.size() > 0){
			this.getSqlSession().delete(NAMESPACE+"deleteSerialNoList", deleteSerialNoList);
		}
		return true;
	}
	
	/**
	 * 修改交接单后保存数据方法
	 * @author 205109-foss-zenghaibin
	 * @date 2014-05-12 下午:6:25
	 * @param connectionBillNo
	 * @param targtState
	 */
	public void updateConnectionBillStateByNo(String connectionBillNo,int statu){
		Map<String,Object> hs=new HashMap<String,Object>();
		hs.put("connectionBillNo",connectionBillNo);
		hs.put("statu",statu);
		this.getSqlSession().update(NAMESPACE+"updateConnectionBillStateByNo",hs);
		
	}
	/**
	 * 根据接驳交接单号查询出打印清单中需要的数据 
	 * @author zenghaibin-foss-205109
	 * @param connectionBillNo
	 * @return List<connectionBillNo>
	 * @exception 无
	 * @date 2015-05-29 上午9:33:17
	 */
	public List<HandOverBillDetailDto> queryPrintConnectionBillDataByNo(String connectionBillNo){
		
		return this.getSqlSession().selectList(NAMESPACE+"queryPrintConnectionBillDataByNo",connectionBillNo);
	}
	
	/**
	 * 根据接驳交接单号查询出打印清单中需要的数据 
	 * @author zenghaibin-foss-205109
	 * @param connectionBillNo
	 * @return String
	 * @exception 无
	 * @date 2015-05-29 上午9:33:17
	 */
	@Override
	public List<String> queryConnectionBillNoForUnloadTaskLackGoods(QueryHandOverBillNoForUnloadTaskLackGoodsDto queryDto){
		
		return this.getSqlSession().selectList(NAMESPACE + "queryConnectionBillNoForUnloadTaskLackGoods", queryDto);

	}

	
}