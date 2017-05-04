
package com.deppon.foss.module.transfer.load.api.server.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;


import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.load.api.shared.domain.ConnectionBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ConnectionBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.HandOverBillDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.NewConnectionBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillNoForUnloadTaskLackGoodsDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryWayBillForConnectionBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateConnectionBillDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.ArrivalConnectionBillVo;
import com.deppon.foss.module.transfer.load.api.shared.vo.ConnectionBillVo;

/** 
 * @className: IConnectionBillService
 * @author:zenghaibin-foos-205109
 * @description: 接驳交接单模块service接口
 * @date: 2015-04-09 上午10:21:31
 * 
 */
public interface IConnectionBillService extends IService {
	
	/**
	 *查询当前部门编码
	 * 
	 ***/
	public String [] queryOrgCode();
		
	/**
	 *用于查询接驳交接单信息
	 *@author 205109-foss-zenghaibin
	 * @date 2015-04-09
	 * @param connectionBillVo 页面传递的查询条件参数（交接单号，部门信息，时间等）
	 * @param limit 分页参数
	 * @param start 分页参数
	 * @return List<ConnectionBillEntity>  返回交接单基本信息实体List
	 ****/
	public List<ConnectionBillEntity> queryConnectionBillList(ConnectionBillVo connectionBillVo,int limit,int start);
	
	/**
	 *用于查询接驳交接单信息
	 * @author 218427-foss-hongwy
	 * @date 2015-10-29
	 * @param ArrivalConnectionBillVo 页面传递的查询条件参数（交接单号，部门信息，时间等）
	 * @param limit 分页参数
	 * @param start 分页参数
	 * @return List<ConnectionBillEntity>  返回交接单基本信息实体List
	 ****/
	public List<ConnectionBillEntity> queryArrivalConnectionBillList(ArrivalConnectionBillVo arrivalConnectionBillVo,int limit,int start);
	
	/**
	 *用于查询接驳交接单信息总条数
	 *@author 205109-foss-zenghaibin
	 * @date 2015-04-09
	 * @param connectionBillVo 页面传递的查询条件参数（交接单号，部门信息，时间等）
	 ****/
	public Long queryConnectionBillListCount(ConnectionBillVo connectionBillVo);
	
	/**
	 * 用于查询接驳交接单信息总条数
	 * @author 218427-foss-hongwy
	 * @date 2015-10-30
	 * @param arrivalConnectionBillVo 页面传递的查询条件参数（交接单号，部门信息，时间等）
	 ****/
	public Long queryArrivalConnectionBillListCount(ArrivalConnectionBillVo arrivalConnectionBillVo);
	
	/**
	 *用于查询库存运单
	 *@author 205109-foss-zenghaibin
	 * @date 2015-04-09
	 * @param connectionBillVo 页面传递的查询条件参数（交接单号，部门信息，时间等）
	 * @return List<ConnectionBillDetailEntity> 交接单明细list
	 ****/
	public   Map<String,Object>  queryWaybillStockList(ConnectionBillVo connectionBillVo,int limit,int start);
	
	/**
	 *保存接驳交接单，并返回交接单号
	 *@author 205109-foss-zenghaibin
	 * @date 2015-05-04
	 * @param newConnectionBillDto （运单号，入库时间，运输性质等）
	 * @return String 
	 ***/
	
	public String saveConnectionBill(NewConnectionBillDto newConnectionBillDto);
	
	
	/**查询库存运单数量 分页用
	 *@author 205109-foss-zenghaibin
	 * @date 2015-04-09
	 * @param connectionBillVo （运单号，入库时间，运输性质等）
	 * @return Long
	 ***/
	public  Long queryWaybillStockCount(QueryWayBillForConnectionBillDto wayBillDto);

	

	/**
	 *通过交接单号查询交接单实体
	 * @author 205109-foss-zenghaibin
	 * @date 2015-04-09 10:09:30
	 * @param connctionBillNo 接驳交接单号
	 ***/
	public ConnectionBillEntity queryConnectionBillByNo(String connctionBillNo);
	
	/**
	 *通过交接单号查询交接单实体
	 * @author 218427-foss-hongwy
	 * @date 2015-11-01 14:43:30
	 * @param arrivalConnectionBillNo 接驳交接单号
	 ***/
	public ConnectionBillEntity queryArrivalConnectionBillByNo(String arrivalConnectionBillNo);
	/**
	 *通过交接单号查询交接单实体
	 * @author 205109-foss-zenghaibin
	 * @date 2015-04-09 10:09:30
	 * @param connctionBillNo 接驳交接单号
	 ***/
	public List<ConnectionBillDetailEntity>  queryConnectionBillDetailByNo(String connctionBillNo);
	
	/**
	 *通过交接单号(到达)查询交接单实体
	 * @author 218427-foss-hongwy
	 * @date 2015-10-31 15:38:30
	 * @param connctionBillNo 接驳交接单号
	 ***/
	public List<ConnectionBillDetailEntity>  queryArrivalConnectionBillDetailByNo(
			String arrivalConnectionBillNo);
	
	/**
	 *通过交接单号,运单号查流水明细
	 * @author 205109-foss-zenghaibin
	 * @date 2015-04-09 10:09:30
	 ***/
	public List<HandOverBillSerialNoDetailEntity>  queryWaybillDetailByNos(String connctionBillNo,String waybillNo);
	
	/**
	 *通过（到达）交接单号,运单号查流水明细
	 * @author 218427-foss-hongwy
	 * @date 2015-10-31 16:52:39
	 ***/
	public List<HandOverBillSerialNoDetailEntity> queryArrivalWaybillDetailByNos(
			String connectionBillNo, String waybillNo);


	/**
	 *通过交接单号,运单号查流水明细
	 * @author 205109-foss-zenghaibin
	 * @date 2015-04-09 10:09:30
	 ***/
  public void updateConnectionBill(UpdateConnectionBillDto updateConnectionBillDto);
  
  /**
	 *通过交接单号,查询交接单
	 * @author 205109-foss-zenghaibin
	 * @date 2015-04-09 10:09:30
	 ***/
  public void cancelConnectionBillbyNo(String connectionBillNo);
  
  /**
	 * 将文件名转成UTF-8编码以防止乱码
	 * @param 文件名
	 * @author 205109-foss-zenghaibin
	 * @date 2015-05-14 上午8:59:05
	 */
	public String encodeFileName(String fileName) ;
	
	/**
	 * 接驳交接单导出
	 * @param connectionBillVo
	 * @author 205109-foss-zenghaibin
	 * @date 2015-05-14 上午8:59:05
	 */
	public InputStream exportConnectionBillExcel(ConnectionBillVo connectionBillVo);
	

	/**
	 * 接驳交接单导出
	 * @param 
	 * @author 205109-foss-zenghaibin
	 * @date 2015-05-14 上午8:59:05
	 */
	public InputStream exportConnectionDetailExcel(String  connectionBillNo);
	
	/**
	 * 根据接驳交接单号查询出打印清单中需要的数据 
	 * @author zenghaibin-foss-205109
	 * @param connectionBillNo
	 * @return List<connectionBillNo>
	 * @exception 无
	 * @date 2015-05-29 上午9:33:17
	 */
	public List<HandOverBillDetailDto> queryPrintConnectionBillDataByNo(String connectionBillNo) ;
	
	/**
	 * 根据接驳交接单号查询出打印清单中需要的数据 
	 * @author zenghaibin-foss-205109
	 * @param queryDto
	 * @return List<String>
	 * @exception 无
	 * @date 2015-05-29 上午9:33:17
	 */
	public List<String> queryConnectionBillNoForUnloadTaskLackGoods(QueryHandOverBillNoForUnloadTaskLackGoodsDto queryDto);

	

	
	


	


	

  
}