package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.transfer.platform.api.server.dao.ISendRateDao;
import com.deppon.foss.module.transfer.platform.api.server.dao.IStockPairDao;
import com.deppon.foss.module.transfer.platform.api.server.service.ISendRateService;
import com.deppon.foss.module.transfer.platform.api.shared.define.DeliverConstants;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.SendRateEntity;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;

public class SendRateServiceImpl implements ISendRateService {
	/**
	 * 记录日志
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(SendRateServiceImpl.class);
	
	
	/**
	 * 派送率Dao
	* @fields sendRateDao
	* @author 14022-foss-songjie
	* @update 2014年3月4日 上午11:12:35
	* @version V1.0
	*/
	private ISendRateDao sendRateDao;
	
	
	/**
	 * 库存副表Dao
	* @fields stockPairDao
	* @author 14022-foss-songjie
	* @update 2014年3月8日 下午4:44:29
	* @version V1.0
	*/
	private IStockPairDao stockPairDao;
	
	
	/**
	* @description 查询日派送率
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.service.ISendRateService#querySendRateList(com.deppon.foss.module.transfer.platform.api.shared.domain.SendRateEntity, int, int)
	* @author 14022-foss-songjie
	* @update 2014年3月4日 上午11:19:47
	* @version V1.0
	*/
	@Override
	public List<SendRateEntity> querySendRateList(
			SendRateEntity sendRateEntity, int start, int limit) {
		if(sendRateEntity!=null){
			Map<String,String> map = new HashMap<String,String>();
			String queryDate = DateUtils.convert(new Date(), DateUtils.DATE_TIME_FORMAT);
			map.put("orgCode", sendRateEntity.getOrgCode());
			Date time = new Date();
			List<SendRateEntity> dbList = sendRateDao.querySendRateList(map, start, limit);
			for (SendRateEntity dbEntity : dbList) {
				checkSendRateEntity(dbEntity);
				//理论统计时间
				dbEntity.setStatisticsTimeTheory(time);
				//系统查询时间
				dbEntity.setDataTimeQuery(queryDate);
			}
			return dbList;
		}else{
			return null;
		}
	}

	
	/**
	* @description 查询派送率的总结记录数
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.service.ISendRateService#querySendRateListCount(com.deppon.foss.module.transfer.platform.api.shared.domain.SendRateEntity)
	* @author 14022-foss-songjie
	* @update 2014年3月4日 上午11:19:43
	* @version V1.0
	*/
	@Override
	public long querySendRateListCount(SendRateEntity sendRateEntity) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("orgCode", sendRateEntity.getOrgCode());
		return sendRateDao.querySendRateListCount(map);
	}

	/**
	* @description 累计日派送率查询
	* @param map
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月7日 下午4:01:20
	*/
	@Override
	public List<SendRateEntity> querySendRateLogList(
			SendRateEntity sendRateEntity, int start, int limit) {
		if(sendRateEntity!=null){
			Map<String,String> map = new HashMap<String,String>();
			Date queryDate= DateUtils.convert(sendRateEntity.getBeginDate(),DateUtils.DATE_FORMAT);
			Date endDate= DateUtils.convert(sendRateEntity.getEndDate(),DateUtils.DATE_FORMAT);
			map.put("orgCode", sendRateEntity.getOrgCode());
			map.put("beginDate", DateUtils.convert(queryDate, DateUtils.DATE_FORMAT));
			map.put("endDate", DateUtils.convert(endDate, DateUtils.DATE_FORMAT));
			List<SendRateEntity> dbList = null;
			int queryStatus = 0;//针对外场查询的数据sendRateAll 代表的是月累计派送率
			if(StringUtils.isNotBlank(sendRateEntity.getOrgCode())){
				dbList = sendRateDao.querySendRateLogList(map, start, limit);
			}else{
				queryStatus = 1;//针对全国查询的数据sendRateAll 代表的是日派送率
				dbList = sendRateDao.querySendRateLogListAll(map, start, limit);
			}
			if(dbList!=null){
				for (SendRateEntity dbEntity : dbList) {
					checkSendRateEntity(dbEntity);
					if(queryStatus==1){
						//理论统计时间statisticsTimeTheory
						dbEntity.setStatisticsTimeTheory(endDate);
					}
				}
			}
			
			return dbList;
		}else{
			return null;
		}
	}

	/**
	* @description 累计日派送率查询总数
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月7日 下午3:38:36
	*/
	@Override
	public long querySendRateLogListCount(SendRateEntity sendRateEntity) {
		Map<String,String> map = new HashMap<String,String>();
		Date queryDate= DateUtils.convert(sendRateEntity.getBeginDate(),DateUtils.DATE_FORMAT);
		Date endDate= DateUtils.convert(sendRateEntity.getEndDate(),DateUtils.DATE_FORMAT);
		map.put("orgCode", sendRateEntity.getOrgCode());
		map.put("beginDate", DateUtils.convert(queryDate, DateUtils.DATE_FORMAT));
		map.put("endDate", DateUtils.convert(endDate, DateUtils.DATE_FORMAT));
		if(StringUtils.isNotBlank(sendRateEntity.getOrgCode())){
			return sendRateDao.querySendRateLogListCount(map);
		}else{
			return sendRateDao.querySendRateLogAllListCount(map);
		}
		
	}
	

	
	/**
	* @description 派送率定时任务查询 Service
	* @param jobDate
	* @param threadNo
	* @param threadCount
	* @throws Exception
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月8日 上午11:18:37
	*/
	@Override
	public void doSendRateJobList(Date jobDate, int threadNo, int threadCount)
			throws Exception {
		//查询结果 
		Map<String,String> map = new HashMap<String, String>();
		
		String queryDate = DateUtils.convert(jobDate, DateUtils.DATE_FORMAT);
		String queryYesterday = DateUtils.addDay(jobDate, -1);
		map.put("queryYesterday", queryYesterday);
		map.put("queryDate", queryDate);
		map.put("threadCount", threadCount+"");
		map.put("threadNo", threadNo+"");
		List<SendRateEntity> jobList = sendRateDao.querySendRateJobList(map);
		//insert 集合
		for (SendRateEntity sendRateEntity : jobList) {
			//计算派送率
			//理论统计时间
			sendRateEntity.setStatisticsTimeTheory(DateUtils.convert(queryDate, DateUtils.DATE_FORMAT));
			sendRateEntity.setSendRateId(UUIDUtils.getUUID());
			sendRateDao.insertSendRatePojo(sendRateEntity);
		}
		/**sendRateDao.batchInsertSendRatePojo(jobList);*/
		//更新库存副表
		Map<String,String> stockPairMap = new HashMap<String, String>();
		stockPairMap.put("isSendRate", DeliverConstants.YES);
		stockPairMap.put("day", queryDate);
		stockPairDao.updateStockPair(stockPairMap);
		
	}


	/**
	* @description 获取派送率Dao
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月4日 上午11:13:04
	*/
	public ISendRateDao getSendRateDao() {
		return sendRateDao;
	}

	
	/**
	* @description 设置派送率Dao
	* @param sendRateDao
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月4日 上午11:13:07
	*/
	public void setSendRateDao(ISendRateDao sendRateDao) {
		this.sendRateDao = sendRateDao;
	}


	
	/**
	* @description 获取 库存副表Dao
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月8日 下午4:45:03
	*/
	public IStockPairDao getStockPairDao() {
		return stockPairDao;
	}


	
	/**
	* @description 设置 库存副表Dao
	* @param stockPairDao
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月8日 下午4:45:16
	*/
	public void setStockPairDao(IStockPairDao stockPairDao) {
		this.stockPairDao = stockPairDao;
	}
	
	
	
	/**
	* @description 派送率的数据验证
	* @param dbEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月12日 下午4:07:30
	*/
	private SendRateEntity checkSendRateEntity(SendRateEntity dbEntity){
		/**
		if(dbEntity!=null){
			List<TransCenterOrgEntity> transCenterList = stockPairDao.queryAllTransOrg();
			TransCenterOrgEntity tco = queryNameOrgUp(dbEntity.getOrgCode(), transCenterList);
			if(tco!=null){
				//本部
				dbEntity.setBigdept(tco.getBigdept());
				//事业部dbEntity
				dbEntity.setDivision(tco.getDivision());
			}
			
		}*/
		
		//已派送量 begin 
		//票数
		if(dbEntity.getDaySendWaybill() == null){
			dbEntity.setDaySendWaybill(0L);
		}
		//重量
		if(dbEntity.getDaySendWeight() == null){
			dbEntity.setDaySendWeight(new BigDecimal(0));
		}
		//体积
		if(dbEntity.getDaySendVolume() == null){
			dbEntity.setDaySendVolume(new BigDecimal(0));
		}
	//已派送量  end
	
	//前一日剩余派送量 begin 
		//票数
		if(dbEntity.getYesterdayStockWaybill() == null){
			dbEntity.setYesterdayStockWaybill(0L);
		}
		//重量
		if(dbEntity.getYesterdayStockWeight() == null){
			dbEntity.setYesterdayStockWeight(new BigDecimal(0));
		}
		//体积
		if(dbEntity.getYesterdayStockVolume() == null){
			dbEntity.setYesterdayStockVolume(new BigDecimal(0));
		}
	//前一日剩余派送量  end
	
	
	//当日入库货量 begin 
		//票数
		if(dbEntity.getDayStockWaybill() == null){
			dbEntity.setDayStockWaybill(0L);
		}
		//重量
		if(dbEntity.getDayStockWeight() == null){
			dbEntity.setDayStockWeight(new BigDecimal(0));
		}
		//体积
		if(dbEntity.getDayStockVolume() == null){
			dbEntity.setDayStockVolume(new BigDecimal(0));
		}
	//当日入库货量  end
		
	//派送率 begin
		BigDecimal f= dbEntity.getYesterdayStockVolume().add(dbEntity.getDayStockVolume());
		BigDecimal fa = new BigDecimal(0);
	if(f.compareTo(fa)==0){
		//分母不能为零
		//针对外场查询的数据sendRate 代表的是日派送率
			dbEntity.setSendRate(new BigDecimal(0));
	}else{
		BigDecimal a = f;
		BigDecimal b = dbEntity.getDaySendVolume();
		BigDecimal c = b.divide(a, 2, BigDecimal.ROUND_HALF_UP);//四舍五入
		//针对外场查询的数据sendRateAll 代表的是日派送率
			dbEntity.setSendRate(c.multiply(new BigDecimal(PlatformConstants.SONAR_NUMBER_100)));
	}
	//派送率 end
	
	
	//月累计派送 begin
	if(dbEntity.getYesterdayStockVolumeAll()==null){
		dbEntity.setYesterdayStockVolumeAll(new BigDecimal(0));
	}
	if(dbEntity.getDayStockVolumeAll()==null){
		dbEntity.setDayStockVolumeAll(new BigDecimal(0));
	}
	if(dbEntity.getDaySendVolumeAll()==null){
		dbEntity.setDaySendVolumeAll(new BigDecimal(0));
	}
	BigDecimal f1= dbEntity.getYesterdayStockVolumeAll().add(dbEntity.getDayStockVolumeAll());
	BigDecimal f1a = new BigDecimal(0);
	if(f1.compareTo(f1a)==0){
		//分母不能为零
		//针对外场查询的数据sendRateAll 代表的是月累计派送率
			dbEntity.setSendRateAll(new BigDecimal(0));
	}else{
		BigDecimal a = f1;
		BigDecimal b = dbEntity.getDaySendVolumeAll();
		BigDecimal c = b.divide(a, 2, BigDecimal.ROUND_HALF_UP);//四舍五入
		//针对外场查询的数据sendRateAll 代表的是月累计派送率
		dbEntity.setSendRateAll(c.multiply(new BigDecimal(PlatformConstants.SONAR_NUMBER_100)));
	}
	//月累计派送 end
	return dbEntity;
	}
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.service  
	 *@desc 根据部门and日期 查询当日的预计派送到达货量
	 *@param orgCode,queryDate
	 *@return List<SendRateEntity>
	 *@author 105795
	 *@date 2015年4月10日下午4:14:50 
	 */
	public List<SendRateEntity> queryForeCastDeliverGoodsByDate(String orgCode,String queryDate){
		if(StringUtil.isEmpty(orgCode)||StringUtil.isEmpty(queryDate)){
		    throw new BusinessException("查询参数不能为空！");	
		}
		
		return sendRateDao.queryForeCastDeliverGoodsByDate(orgCode, queryDate);
		
	}	
}
