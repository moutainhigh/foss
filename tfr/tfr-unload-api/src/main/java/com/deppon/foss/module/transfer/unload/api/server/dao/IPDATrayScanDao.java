package com.deppon.foss.module.transfer.unload.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.TrayScanDetaiEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.TrayScanDto;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDATrayScanDetailEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDATrayScanTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryTrayScanTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDATrayOfflineScanDto;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.TrayOfflineScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PcakageWayBillDto;

/**
 * PDA接口：托盘扫描任务dao
 * @author 105869-foss-heyongdong
 * @date 2013-8-6 17:24:10
 */
public interface IPDATrayScanDao {
	
	/**
	 * 获取组织信息
	 * @param deptcode
	 * @return String
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-6 20:37:27
	 * */
	public String getDeptInfo(String deptcode);
	
	/**
	 * 创建托盘任务
	 * @param trayScanDto
	 * @return 
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-6 20:37:27
	 * */
	public int createTrayScanTask(TrayScanDto trayScanDto);
	
	/**
	 * 创建托盘任务明细
	 * @param addnewTaskDetails
	 * @return 
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-6 20:37:27
	 * */
	public int createTrayScanDetail(List<TrayScanDetaiEntity> addnewTaskDetails);
	
	
	/**
	 * 查询托盘任务明细
	 * @param queryCondtion
	 * @return List<PDATrayScanDetailEntity>
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-7 8:14:17
	 * */
	public List<PDATrayScanDetailEntity> queryTrayScanDetail(
			QueryTrayScanTaskEntity queryCondtion);
	/****/
	
	/**
	 * 查询托盘任务明细,对于有包的
	 * @param queryCondtion
	 * @return List<PDATrayScanDetailEntity>
	 * @author 205109-foss-zenghaibin
	 * @date 2014-12-01 17:31:17
	 * */
	public List<PDATrayScanDetailEntity> queryTrayScanPackageDetail(QueryTrayScanTaskEntity queryCondtion);
	/**
	 * 查询托盘任务
	 * @param queryCondtion
	 * @return PDATrayScanTaskEntity
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-7 8:14:17
	 * */
	public List<PDATrayScanTaskEntity> queryTrayScanNO(
			QueryTrayScanTaskEntity queryCondtion);

	/**
	 * 更新托盘任务
	 * @param trayScanDto
	 * @return 
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-7 10:15:34
	 * */
	public int updateTrayScanTask(TrayScanDto trayScanDto);
	
	/**
	 * 查询运单是否代打木架
	 * @param queryIsWooded
	 * @return 
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-7 19:56:19
	 * */
	public long queryIsWooded(QueryTrayScanTaskEntity queryIsWooded);
	
	/**
	 * 删除托盘任务
	 * @param padScanTask
	 * @return 
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-7 21:15:52
	 * */
	public int deleteTrayScanTask(PDATrayScanTaskEntity padScanTask);
	
	/**
	 * 删除托盘任务明细
	 * @param padScanTask
	 * @return 
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-7 21:15:52
	 * */
	public int deleteTrayScanTaskDetail(PDATrayScanTaskEntity padScanTask);
	
	/**
	 * 取消托盘任务
	 * @param padScanTask
	 * @return 
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-7 21:15:52
	 * */
	public int cancelTrayScanTask(String taskNo);
	
	/**
	 * 查询是否贵重物品
	 * @param waybillNo
	 * @return 
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-11 21:09:07
	 * */
	public String queryIsPreciousGoods(String waybillNo);
	
	
	/**
	 * 查询库存根据运单号,流水号,当前部门
	 * @param waybillNo
	 * @return 
	 * @author 105869-foss-heyongdong
	 * @param outfieldCode,serialNo
	 * @date 2013-8-11 21:09:07
	 * */
	public List<StockEntity> queryWaybillStock(String waybillNo,String serialNo,String outfieldCode);
	
	/**
	 * 查询运输name性质根据运输性质code
	 * @param property
	 * @return 
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-16 18:31:21
	 * */
	public String queryWaybillProperty(String property);
	
	/**
	 * 查询未生成叉车工作量的任务
	 * @param property
	 * @return 
	 * @author 105869-foss-heyongdong
	 * @date 2013-8-16 18:31:21
	 * */
	public List<PDATrayScanTaskEntity> queryNotForkliftWork(
			Date bizJobStartTime, Date bizJobEndTime, int threadNo,
			int threadCount);
	
	/**
	 * 查询运单的中提货网点对应的目的站简称
	 * @param customerPickupOrgCode
	 * @author 105869-foss-heyongdong
	 * @return   String
	 * @date 2013-8-23 15:08:25
	 * */
	public String queryOrgSimaleName(String customerPickupOrgCode);
	
	/**
	 * 在入库记录中查询库存根据运单号,流水号,当前部门
	 * @param waybillNo
	 * @return 
	 * @author 105869-foss-heyongdong
	 * @param outfieldCode,serialNo,waybillNo
	 * @date 2013年8月24日15:01:17
	 * */
	public List<StockEntity> queryWaybillInStock(String waybillNo,
			String serialNo, String outfieldcode);

	/**
	 * 插入离线扫描信息
	 * @author foss-heyongdong
	 * @param trayOfflineScanList
	 * @return int
	 */
	public int  createTrayOfflineScanTask(List<TrayOfflineScanEntity> trayOfflineScanList);
	
	/**
	 * 根据操作人工号、操作人部门及当前时间，返回该时间往前12个小时内的叉车票数统计值给PDA
	 * @param createrCode 创建人code
	 * @param createrOrgCode 创建人部门code
	 * @param currentTime 当前查询时间
	 * @return PDATrayScanTaskEntity
	 * @author 105795-foss-wqh
	 * @date 2014-01-11
	 * */
   public List<PDATrayScanTaskEntity> queryTraybindforkLiftTicks(String ceaterCode,
		   String createrOrgCode, Date currentTime,Date beginTime);
	/**
	* 根据操作人工号、操作人部门及当前时间，返回该时间往前 hours 个小时内的运单票数统计值给PDA
	 * @param createrCode 创建人code
	 * @param createrOrgCode 创建人部门code
	 * @param currentTime 当前查询时间
	 * @param hours 查询递归小时
	 * @return PDATrayOfflineScanDto
	 * @author 105869-foss-heyondong
	 * @date 2014年5月5日 14:26:44
	 */
	public List<PDATrayOfflineScanDto> queryTrayOfflineScanWaybillQty(
			String ceaterCode, String createrOrgCode, Date currentTime,
			Date beginTime);
	
	public int updateTrayScanForkliftCount(TrayScanDto trayScanDto);
	/**
	 * 下拉包的运单明细
	 * @author 205109 zenghaibin
	 * @date 2014-11-06 09：17
	 * @param Strng packageNo
	 * @return List<PcakageWayBillDto> (运单和流水号)
	 * **/
	public List<PcakageWayBillDto> queryPackageDetail(String packageNo);
	
}
