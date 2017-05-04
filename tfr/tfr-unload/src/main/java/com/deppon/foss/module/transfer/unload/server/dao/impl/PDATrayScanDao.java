package com.deppon.foss.module.transfer.unload.server.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.shared.domain.TrayScanDetaiEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.TrayScanDto;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDATrayScanDetailEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDATrayScanTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryTrayScanTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDATrayOfflineScanDto;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.unload.api.server.dao.IBatchSaveProcessDao;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPDATrayScanDao;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.TrayOfflineScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PcakageWayBillDto;

/**
 * pda托盘扫描dao
 * @author 105869-foss-heyongdong
 * @date 2013-8-7 11:46:24
 */
@SuppressWarnings("unchecked")
public class PDATrayScanDao extends iBatis3DaoImpl implements IPDATrayScanDao {
	private static final String NAMESPACE="tfr-nuload-trayscan.";
	//批量插入接口
	private IBatchSaveProcessDao batchSaveProcessDao;
	/** 
	 * 设置批量插入接口*/
	public void setBatchSaveProcessDao(IBatchSaveProcessDao batchSaveProcessDao) {
		this.batchSaveProcessDao = batchSaveProcessDao;
	}
	/** 
	 * 根据部门编号查询部门名称
	 * 
	 * 
	 * @author foss-heyongdong
	 * @date 2013-8-7 11:50:46
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDATrayScanDao#getDeptInfo(java.lang.String)
	 */
	@Override
	public String getDeptInfo(String deptcode) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/** 
	 * 创建托盘任务
	 * 
	 * 
	 * @author foss-heyongdong
	 * @date 2013-8-7 12:21:59
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#createTrayScanTask(com.deppon.foss.module.transfer.load.api.shared.dto.TrayScanDto)
	 */
	@Override
	public int createTrayScanTask(TrayScanDto trayScanDto) {
		return this.getSqlSession().insert(NAMESPACE+"createTrayScanTask",trayScanDto );
		
	}
	
	/** 
	 * 创建托盘任务明细
	 * 
	 * 
	 * @author foss-heyongdong
	 * @date 2013-8-7 12:22:03
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#createTrayScanDetail(java.util.List)
	 */
	@Override
	public int createTrayScanDetail(List<TrayScanDetaiEntity> trayScanDetail) {
		return  batchSaveProcessDao.batchSaveProcess(UnloadConstants.BATCH_SAVE_TYPE_INSERT,NAMESPACE+"createTrayScanDetail", trayScanDetail);
	}
	
	/** 
	 * 查询托盘任务明细
	 * 
	 * 
	 * @author foss-heyongdong
	 * @date 2013-8-7 12:22:03
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryTrayScanDetail(com.deppon.foss.module.transfer.load.api.shared.domain.TrayScanDetaiEntity)
	 */
	@Override
	public List<PDATrayScanDetailEntity> queryTrayScanDetail(
			QueryTrayScanTaskEntity queryCondtion) {
		
		return this.getSqlSession().selectList(NAMESPACE+"queryTrayScanDetail",queryCondtion);
	}
	
	/**
	 * 查询托盘任务明细,对于有包的
	 * @param queryCondtion
	 * @return List<PDATrayScanDetailEntity>
	 * @author 205109-foss-zenghaibin
	 * @date 2014-12-01 17:31:17
	 * */
	@Override
	public List<PDATrayScanDetailEntity> queryTrayScanPackageDetail (QueryTrayScanTaskEntity queryCondtion){
		return this.getSqlSession().selectList(NAMESPACE+"queryTrayScanPackageDetail",queryCondtion);

		
	}
	
	/** 
	 * 查询托盘任务
	 * 
	 * 
	 * @author foss-heyongdong
	 * @date 2013-8-7 12:22:03
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryTrayScanNO(com.deppon.foss.module.transfer.load.api.shared.domain.TrayScanDetaiEntity)
	 */
	@Override
	public List<PDATrayScanTaskEntity> queryTrayScanNO(
			QueryTrayScanTaskEntity queryCondtion) {
		
		return this.getSqlSession().selectList(NAMESPACE+"queryTrayScanNO",queryCondtion);
	}
	
	/** 
	 * 更新托盘任务
	 * 
	 * 
	 * @author foss-heyongdong
	 * @date 2013-8-7 12:22:03
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#updateTrayScanTask(com.deppon.foss.module.transfer.load.api.shared.dto.TrayScanDto)
	 */
	@Override
	public int updateTrayScanTask(TrayScanDto trayScanDto) {
		return this.getSqlSession().update(NAMESPACE+"updateTrayScanTask", trayScanDto);
		
	}
	/** 
	 * 查询是否待打木架
	 * 
	 * 
	 * @author foss-heyongdong
	 * @date 2013-8-7 12:22:03
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryIsWooded(com.deppon.foss.module.transfer.load.api.shared.domain.TrayScanDetaiEntity)
	 */
	@Override
	public long queryIsWooded(QueryTrayScanTaskEntity queryIsWooded) {
		
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"queryIsWooded", queryIsWooded);
	}
	/** 
	 * 删除托盘任务
	 * 
	 * 
	 * @author foss-heyongdong
	 * @date 2013-8-7 21:20:00
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#deleteTrayScanTask(com.deppon.foss.module.transfer.pda.api.shared.domain.PDATrayScanTaskEntity)
	 */
	@Override
	public int deleteTrayScanTask(PDATrayScanTaskEntity padScanTask) {
		return this.getSqlSession().delete(NAMESPACE+"deleteTrayScanTask", padScanTask);
		
	}
	/** 
	 * 删除托盘任务明细
	 * 
	 * 
	 * @author foss-heyongdong
	 * @date 2013-8-7 21:20:00
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#deleteTrayScanTaskDetail(com.deppon.foss.module.transfer.pda.api.shared.domain.PDATrayScanTaskEntity)
	 */
	@Override
	public int deleteTrayScanTaskDetail(PDATrayScanTaskEntity padScanTask) {
		return this.getSqlSession().delete(NAMESPACE+"deleteTrayScanTaskDetail", padScanTask);
		
	}
	
	/** 
	 * 取消托盘任务
	 * 
	 * 
	 * @author foss-heyongdong
	 * @date 2013-8-8 17:55:59
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#cancelTrayScanTask(java.lang.String)
	 */
	@Override
	public int cancelTrayScanTask(String taskNo) {
		return this.getSqlSession().update(NAMESPACE+"cancelTrayScanTask", taskNo);
	}
	
	/** 
	 * 查询是否贵重物品
	 * 
	 * 
	 * @author foss-heyongdong
	 * @date 2013-8-8 17:55:59
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryIsPreciousGoods(java.lang.String)
	 */
	@Override
	public String queryIsPreciousGoods(String waybillNo) {
		
		return (String) this.getSqlSession().selectOne(NAMESPACE+"queryIsPreciousGoods", waybillNo);
	}
	
	/** 
	 * 通过运单号、外场code、查询
	 * 
	 * 
	 * @author foss-heyongdong
	 * @date 2013-8-8 17:55:59
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryWaybillStock(java.lang.String,java.lang.String,java.lang.String)
	 */
	@Override
	public List<StockEntity> queryWaybillStock(String waybillNo, String serialNo,String outfieldCode) {
       Map<String, String> map =new HashMap<String, String>();
       map.put("waybillNo", waybillNo);
       map.put("serialNo", serialNo);
       map.put("outfieldCode", outfieldCode);
       return  this.getSqlSession().selectList(NAMESPACE+"queryWaybillStock",map);
	}
	
	/** 
	 * 通过运输性质code查询运输性质name
	 * 
	 * 
	 * @author foss-heyongdong
	 * @date 2013-8-16 18:32:45
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryWaybillProperty(java.lang.String)
	 */
	@Override
	public String queryWaybillProperty(String property) {
		return (String) this.getSqlSession().selectOne(NAMESPACE+"queryWaybillProperty", property);
	}
	
	/** 
	 * 查询
	 * 
	 * 
	 * @author foss-heyongdong
	 * @date 2013-8-20 12:17:38
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryNotForkliftWork(Date,Date,int,int)
	 */
	@Override
	public List<PDATrayScanTaskEntity> queryNotForkliftWork(
			Date bizJobStartTime, Date bizJobEndTime, int threadNo,
			int threadCount) {
		 Map<String, Object> map =new HashMap<String, Object>();
		 map.put("bizJobStartTime", bizJobStartTime);
		 map.put("bizJobEndTime", bizJobEndTime);
		 map.put("threadNo", threadNo);
		 map.put("threadCount", threadCount);
		 
		return this.getSqlSession().selectList(NAMESPACE+"queryNotForkliftWork",map);
	}
	
	/** 
	 * 查询提货网点简称（标签上的目的站）
	 * 
	 * 
	 * @author foss-heyongdong
	 * @date 2013-8-20 12:17:38
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryNotForkliftWork(Date,Date,int,int)
	 */
	@Override
	public String queryOrgSimaleName(String customerPickupOrgCode) {
		// TODO Auto-generated method stub
		return  (String) this.getSqlSession().selectOne(NAMESPACE+"queryOrgSimaleName", customerPickupOrgCode);
	}
	
	/** 
	 * 在入库日志中查询入库记录
	 * 
	 * 
	 * @author foss-heyongdong
	 * @date 2013-8-20 12:17:38
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao#queryWaybillInStock(java.lang.String,java.lang.String,java.lang.String)
	 */
	@Override
	public List<StockEntity> queryWaybillInStock(String waybillNo,
			String serialNo, String outfieldCode) {
		 Map<String, String> map =new HashMap<String, String>();
	       map.put("waybillNo", waybillNo);
	       map.put("serialNo", serialNo);
	       map.put("outfieldCode", outfieldCode);
		return this.getSqlSession().selectList(NAMESPACE+"queryWaybillInStock",map);
	}
	/**
	 * 插入叉车离线扫描信息
	 * @author foss-heyongdong
	 * @Date 2014年1月8日 15:34:29
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDATrayScanDao#createTrayOfflineScanTask(com.deppon.foss.module.transfer.unload.api.shared.domain.TrayOfflineScanEntity)
	 */
	@Override
	public int createTrayOfflineScanTask(List<TrayOfflineScanEntity> trayOfflineScanList) {
		
		return this.batchSaveProcessDao.batchSaveProcess(UnloadConstants.BATCH_SAVE_TYPE_INSERT,NAMESPACE+"createTrayOfflineScanTask", trayOfflineScanList);
	}

	/**
	 * 根据操作人工号、操作人部门及当前时间，返回该时间往前12个小时内的叉车票数统计值给PDA
	 * @param createrCode 创建人code
	 * @param createrOrgCode 创建人部门code
	 * @param currentTime 当前查询时间
	 * @return PDATrayScanTaskEntity
	 * @author 105795-foss-wqh
	 * @date 2014-01-11
	 * */
	public List<PDATrayScanTaskEntity> queryTraybindforkLiftTicks(String ceaterCode,String createrOrgCode, Date currentTime,Date beginTime)
	{
		 Map<String, String> map =new HashMap<String, String>();
	       map.put("ceaterCode", ceaterCode);
	       map.put("createrOrgCode", createrOrgCode);
	       SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	       map.put("currentTime", df.format(currentTime));
	       map.put("beginTime", df.format(beginTime));
		return this.getSqlSession().selectList(NAMESPACE+"queryTraybindforkLiftTicks",map);
	}
	/**
	 * 更新叉车票数
	 * @date 2014年3月27日 08:48:39
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDATrayScanDao#updateTrayScanForkliftCount(com.deppon.foss.module.transfer.load.api.shared.dto.TrayScanDto)
	 */
	@Override
	public int updateTrayScanForkliftCount(TrayScanDto trayScanDto) {
		
		return this.getSqlSession().update(NAMESPACE+"updateTrayScanForkliftCount", trayScanDto);
	}
	/**
	 * 根据操作人工号、操作人部门及当前时间，返回该时间往前 hours 个小时内的运单票数统计值给PDA
	 * @param createrCode 创建人code
	 * @param createrOrgCode 创建人部门code
	 * @param currentTime 当前查询时间
	 * @param hours 查询递归小时
	 * @return PDATrayOfflineScanDto
	 * @author 105869-foss-heyondong
	 * @date 2014年5月5日 14:39:59
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IPDATrayScanDao#queryTrayOfflineScanWaybillQty(java.lang.String, java.lang.String, java.util.Date, java.util.Date)
	 */
	@Override
	public List<PDATrayOfflineScanDto> queryTrayOfflineScanWaybillQty(
			String ceaterCode, String createrOrgCode, Date currentTime,Date beginTime) {
		 Map<String, String> map =new HashMap<String, String>();
	       map.put("ceaterCode", ceaterCode);
	       map.put("createrOrgCode", createrOrgCode);
	       SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	       map.put("currentTime", df.format(currentTime));
	       map.put("beginTime", df.format(beginTime));
		return this.getSqlSession().selectList(NAMESPACE+"queryTrayOfflineScanWaybillQty", map);
	}
	/**
	 * 下拉包的运单明细
	 * @author 205109 zenghaibin
	 * @date 2014-11-06 09：17
	 * @param Strng packageNo
	 * @return List<PcakageWayBillDto> (运单和流水号)
	 * **/
	@Override
	public List<PcakageWayBillDto> queryPackageDetail(String packageNo){
		return this.getSqlSession().selectList(NAMESPACE+"queryPackageDetail", packageNo);
	}
}
