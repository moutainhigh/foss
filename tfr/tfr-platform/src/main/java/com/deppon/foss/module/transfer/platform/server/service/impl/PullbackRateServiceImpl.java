package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.platform.api.server.dao.IPullbackRateDao;
import com.deppon.foss.module.transfer.platform.api.server.service.IPullbackRateService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.PullbackRateEntity;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;


/**
* @description 拉回率Service
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年3月3日 下午4:31:45
*/
public class PullbackRateServiceImpl implements IPullbackRateService {
	
	/**
	 * 记录日志
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(PullbackRateServiceImpl.class);
	
	
	/**
	 * 拉回率的Dao
	* @fields pullbackRateDao
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午4:34:33
	* @version V1.0
	*/
	private IPullbackRateDao pullbackRateDao; 
	
	
	/**
	* @description 查询拉回率
	* @param pullbackRateEntity
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:48:29
	*/
	@Override
	public List<PullbackRateEntity> queryPullbackRateList(
			PullbackRateEntity pullbackRateEntity, int start, int limit) {
		if(pullbackRateEntity!=null){
			Map<String,String> map = new HashMap<String,String>();
			map.put("orgCode", pullbackRateEntity.getOrgCode());
			String queryDdTime = DateUtils.convert(new Date(), DateUtils.DATE_TIME_FORMAT);
			 List<PullbackRateEntity> dbBackList = pullbackRateDao.queryPullbackRateList(map, start, limit);
			 Date time = new Date();
			 for (PullbackRateEntity dbEntity : dbBackList) {
				 checkPullbackRateEntity(dbEntity,0);
				 //理论统计时间
				 dbEntity.setStatisticsTimeTheory(time);
				 //系统时间
				 dbEntity.setDataTimeQuery(queryDdTime);
			}
			 return dbBackList;
		}else{
			return null;
		}
	}
	
	/**
	* @description 查询拉回率总记录
	* @param pullbackRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:49:02
	*/
	@Override
	public long queryPullbackRateListCount(PullbackRateEntity pullbackRateEntity) {
		if(pullbackRateEntity!=null){
			Map<String,String> map = new HashMap<String,String>();
			map.put("orgCode", pullbackRateEntity.getOrgCode());
			return pullbackRateDao.queryPullbackRateListCount(map);
		}else{
			return 0;
		}
	}
	
	/**
	* @description 累计拉回率查询分页
	* @param pullbackRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 上午10:51:13
	*/
	@Override
	public List<PullbackRateEntity> queryPullbackRateLogList(
			PullbackRateEntity pullbackRateEntity, int start, int limit) {
		if(pullbackRateEntity!=null){
			Map<String,String> map = new HashMap<String,String>();
			Date queryDate= DateUtils.convert(pullbackRateEntity.getBeginDate(),DateUtils.DATE_FORMAT);
			Date endDate= DateUtils.convert(pullbackRateEntity.getEndDate(),DateUtils.DATE_FORMAT);
			map.put("orgCode", pullbackRateEntity.getOrgCode());
			map.put("beginDate", DateUtils.convert(queryDate, DateUtils.DATE_FORMAT));
			map.put("endDate", DateUtils.convert(endDate, DateUtils.DATE_FORMAT));
			List<PullbackRateEntity> dbList = null;
			int queryStatus = 0;//针对外场查询的数据pullbackRateAll 代表的是月累计拉回率
			if(StringUtils.isNotBlank(pullbackRateEntity.getOrgCode())){
				dbList = pullbackRateDao.queryPullbackRateLogList(map, start, limit);
			}else{
				queryStatus = 1;//针对全国查询的数据pullbackRateAll 代表的是日拉回率
				dbList = pullbackRateDao.queryPullbackRateAllLogList(map, start, limit);
			}
			if(dbList!=null){
				for (PullbackRateEntity dbEntity : dbList) {
					checkPullbackRateEntity(dbEntity,queryStatus);
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
	* @description 累计拉拉回率查询分页总记录数
	* @param pullbackRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 上午10:51:13
	*/
	@Override
	public long queryPullbackRateLogListCount(
			PullbackRateEntity pullbackRateEntity) {
		Map<String,String> map = new HashMap<String,String>();
		Date queryDate= DateUtils.convert(pullbackRateEntity.getBeginDate(),DateUtils.DATE_FORMAT);
		Date endDate= DateUtils.convert(pullbackRateEntity.getEndDate(),DateUtils.DATE_FORMAT);
		map.put("orgCode", pullbackRateEntity.getOrgCode());
		map.put("beginDate", DateUtils.convert(queryDate, DateUtils.DATE_FORMAT));
		map.put("endDate", DateUtils.convert(endDate, DateUtils.DATE_FORMAT));
		if(StringUtils.isNotBlank(pullbackRateEntity.getOrgCode())){
			return pullbackRateDao.queryPullbackRateLogListCount(map);
		}else{
			return pullbackRateDao.queryPullbackRateAllLogListCount(map);
		}
	}

	/**
	* @description  拉回率定时执行Service
	* @param jobDate
	* @param threadNo
	* @param threadCount
	* @return
	* @throws Exception
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月8日 上午11:37:01
	*/
	@Override
	public void doPullbackRateJobList(Date jobDate,
			int threadNo, int threadCount) throws Exception {
		Map<String,String> map = new HashMap<String, String>();
		String queryDate = DateUtils.convert(jobDate, DateUtils.DATE_FORMAT);
		String queyDateBefore = DateUtils.addDay(jobDate, -1);
		map.put("queyDate", queryDate);
		map.put("queyDateBefore", queyDateBefore);
		map.put("threadCount", threadCount+"");
		map.put("threadNo", threadNo+"");
		List<PullbackRateEntity> queryList = pullbackRateDao.queryPullbackRateJobList(map);
		if(queryList!=null){
			for (PullbackRateEntity pullbackRateEntity : queryList) {
				pullbackRateEntity.setStatisticsTimeTheory(DateUtils.convert(queryDate, DateUtils.DATE_FORMAT));
				pullbackRateEntity.setPullbackRateId(UUIDUtils.getUUID());
				pullbackRateDao.insertPullbackRatePojo(pullbackRateEntity);
			}
		}
		
/*
 * 		if(queryList!=null && queryList.size()>0){
			pullbackRateDao.batchInsertPullbackRatePojo(queryList);
		}
*/	}

	/**
	* @description 获取 拉回率的Dao
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午4:34:58
	*/
	public IPullbackRateDao getPullbackRateDao() {
		return pullbackRateDao;
	}

	
	/**
	* @description  设置 拉回率的Dao
	* @param pullbackRateDao
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月6日 下午4:44:18
	*/
	public void setPullbackRateDao(IPullbackRateDao pullbackRateDao) {
		this.pullbackRateDao = pullbackRateDao;
	}
	
	
	
	/**
	* @description 拉回率的数据检验
	* @param dbEntity
	* @param queryStatus
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月14日 下午4:10:05
	*/
	public PullbackRateEntity checkPullbackRateEntity(PullbackRateEntity dbEntity,int queryStatus){
		//装车量
		 if(dbEntity.getQuantityCar()==null){
			 dbEntity.setQuantityCar(0L);
		 }
		 //拉回量
		 if(dbEntity.getQuantityPullback()==null){
			 dbEntity.setQuantityPullback(0L);
		 }
		 
		 //累计装车/日装车量
		 if(dbEntity.getQuantityCarAll()==null){
			 dbEntity.setQuantityCarAll(0L);
		 }
		 
		//累计拉回/日拉回量
		 if(dbEntity.getQuantityPullbackAll()==null){
			 dbEntity.setQuantityPullbackAll(0L);
		 }
		 
		//拉回率
		 if(dbEntity.getQuantityCar()<=0){
			 if(queryStatus==0){
				 dbEntity.setPullbackRate(new Float(0));
			 }else{
				 dbEntity.setPullbackRateAll(new Float(0));
			 }
			 
		 }else{
			 if(queryStatus==0){
				 BigDecimal a = new BigDecimal(dbEntity.getQuantityCar()+"");
					BigDecimal b = new BigDecimal(dbEntity.getQuantityPullback()+"");
					BigDecimal c = b.divide(a, 2, BigDecimal.ROUND_HALF_UP);//四舍五入
					c= c.multiply(new BigDecimal(PlatformConstants.SONAR_NUMBER_100));
					dbEntity.setPullbackRate(c.floatValue());
			 }else{
				 BigDecimal a = new BigDecimal(dbEntity.getQuantityCar()+"");
					BigDecimal b = new BigDecimal(dbEntity.getQuantityPullback()+"");
					BigDecimal c = b.divide(a, 2, BigDecimal.ROUND_HALF_UP);//四舍五入
					c= c.multiply(new BigDecimal(PlatformConstants.SONAR_NUMBER_100));
					dbEntity.setPullbackRateAll(c.floatValue());
			 }
			
		 }
		 
		 //累计拉回率
		 if(dbEntity.getQuantityCarAll()<=0){
			 if(queryStatus==0){
				 dbEntity.setPullbackRateAll(new Float(0));
			 }else{
				 dbEntity.setPullbackRate(new Float(0));
			 }
		 }else{
			 if(queryStatus==0){
				 BigDecimal a = new BigDecimal(dbEntity.getQuantityCarAll()+"");
					BigDecimal b = new BigDecimal(dbEntity.getQuantityPullbackAll()+"");
					BigDecimal c = b.divide(a, 2, BigDecimal.ROUND_HALF_UP);//四舍五入
					c= c.multiply(new BigDecimal(PlatformConstants.SONAR_NUMBER_100));
					dbEntity.setPullbackRateAll(c.floatValue());
			 }else{
				 BigDecimal a = new BigDecimal(dbEntity.getQuantityCarAll()+"");
					BigDecimal b = new BigDecimal(dbEntity.getQuantityPullbackAll()+"");
					BigDecimal c = b.divide(a, 2, BigDecimal.ROUND_HALF_UP);//四舍五入
					c=c.multiply(new BigDecimal(PlatformConstants.SONAR_NUMBER_100));
					dbEntity.setPullbackRate(c.floatValue());
			 }
		 }
		return dbEntity;
	}
	
}
