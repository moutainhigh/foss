package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.transfer.platform.api.server.dao.IReturnRateDao;
import com.deppon.foss.module.transfer.platform.api.server.service.IReturnRateService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ReturnRateEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;

public class ReturnRateServiceImpl implements IReturnRateService {
	
	/**
	 * 记录日志
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(ReturnRateServiceImpl.class);
	
	
	/**
	 * 退单率的dao
	* @fields returnRateDao
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午4:37:40
	* @version V1.0
	*/
	private IReturnRateDao returnRateDao;
	
	/**
	* @description 查询退单率
	* @param returnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:44:10
	*/
	@Override
	public List<ReturnRateEntity> queryReturnRateList(
			ReturnRateEntity returnRateEntity, int start, int limit) {
		if(returnRateEntity!=null){
			Map<String,String> map = new HashMap<String,String>();
			map.put("orgCode", returnRateEntity.getOrgCode());
			String queryDdTime = DateUtils.convert(new Date(), DateUtils.DATE_TIME_FORMAT);
			List<ReturnRateEntity> dbBackList = returnRateDao.queryReturnRateList(map, start, limit);
			Date time = new Date();
			
			for (ReturnRateEntity dbEntity : dbBackList) {
				checkReturnRateEntity(dbEntity,0);
				//理论统计时间
				dbEntity.setStatisticsTimeTheory(time);
				//系统查询时间
				dbEntity.setDataTimeQuery(queryDdTime);
			}
			//重新计算退单率
			calculateReturnRate(dbBackList);
			return dbBackList;
		}
		else{
			return null;
		}
		
	}

	/**
	* @description 查询退单率总记录
	* @param returnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午3:44:54
	*/
	@Override
	public long queryReturnRateListCount(ReturnRateEntity returnRateEntity) {
		if(returnRateEntity!=null){
			Map<String,String> map = new HashMap<String,String>();
			map.put("orgCode", returnRateEntity.getOrgCode());
			return returnRateDao.queryReturnRateListCount(map);
		}
		else{
			return 0;
		}
	}
	
	/**
	* @description 累计退单率查询分页
	* @param ReturnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月18日 上午8:48:41
	*/
	@Override
	public List<ReturnRateEntity> queryReturnRateLogList(
			ReturnRateEntity returnRateEntity, int start, int limit) {
		if(returnRateEntity!=null){
			
			Date queryDate= DateUtils.convert(returnRateEntity.getBeginDate(),DateUtils.DATE_FORMAT);
			Date endDate= DateUtils.convert(returnRateEntity.getEndDate(),DateUtils.DATE_FORMAT);
			Map<String,String> map = new HashMap<String,String>();
			map.put("orgCode", returnRateEntity.getOrgCode());
			map.put("beginDate", DateUtils.convert(queryDate, DateUtils.DATE_FORMAT));
			map.put("endDate", DateUtils.convert(endDate, DateUtils.DATE_FORMAT));
			List<ReturnRateEntity> dbList = null;
			int queryStatus = 0;//针对外场查询的数据returnRateAll 代表的是月累计退单率
			if(StringUtils.isNotBlank(returnRateEntity.getOrgCode())){
				dbList = returnRateDao.queryReturnRateLogList(map, start, limit);
			}else{
				queryStatus = 1;//针对全国查询的数据returnRateAll 代表的是日退单率
				dbList = returnRateDao.queryReturnRateAllLogList(map, start, limit);
			}
			
			if(dbList != null){
				for (ReturnRateEntity dbEntity : dbList) {
					checkReturnRateEntity(dbEntity, queryStatus);
					if(queryStatus==1){
						//理论统计时间statisticsTimeTheory
						dbEntity.setStatisticsTimeTheory(endDate);
					}
				}
			}
			
			return dbList;
		}
		else{
			return null;
		}
	}
	
	/**
	* @description 累计退单率查询分页总记录数
	* @param ReturnRateEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月18日 上午8:48:41
	*/
	@Override
	public long queryReturnRateLogListCount(ReturnRateEntity returnRateEntity) {
		if(returnRateEntity!=null){
			Date queryDate= DateUtils.convert(returnRateEntity.getBeginDate(),DateUtils.DATE_FORMAT);
			Date endDate= DateUtils.convert(returnRateEntity.getEndDate(),DateUtils.DATE_FORMAT);
			Map<String,String> map = new HashMap<String,String>();
			map.put("orgCode", returnRateEntity.getOrgCode());
			map.put("beginDate", DateUtils.convert(queryDate, DateUtils.DATE_FORMAT));
			map.put("endDate", DateUtils.convert(endDate, DateUtils.DATE_FORMAT));
			if(StringUtils.isNotBlank(returnRateEntity.getOrgCode())){
				return returnRateDao.queryReturnRateLogListCount(map);
			}else{
				return returnRateDao.queryReturnRateAllLogListCount(map);
			}
		}
		else{
			return 0;
		}
	}
	

	/**
	* @description 退单率定时执行Service
	* @param jobDate
	* @param threadNo
	* @param threadCount
	* @return
	* @throws Exception
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月8日 上午11:34:21
	*/
	@Override
	public void doReturnRateJobList(Date jobDate,
			int threadNo, int threadCount) throws Exception {
		Map<String,String> map = new HashMap<String,String>();
		String queryDate = DateUtils.convert(jobDate, DateUtils.DATE_FORMAT);
		map.put("queyDate", queryDate);
		map.put("threadCount", threadCount+"");
		map.put("threadNo", threadNo+"");
		List<ReturnRateEntity> jobList = returnRateDao.queryReturnRateJobList(map);
		//重新计算退单率
		calculateReturnRate(jobList);
		if(jobList!=null){
			for (ReturnRateEntity returnRateEntity : jobList) {
				returnRateEntity.setStatisticsTimeTheory(DateUtils.convert(queryDate, DateUtils.DATE_FORMAT));
				returnRateEntity.setReturnRateId(UUIDUtils.getUUID());
				returnRateDao.insertReturnRatePojo(returnRateEntity);
			}
		}
		
	}

	/**
	* @description 获取退单率的dao
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月3日 下午4:38:03
	*/
	public IReturnRateDao getReturnRateDao() {
		return returnRateDao;
	}

	
	/**
	* @description 设置退单率的dao
	* @param returnRateDao
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月6日 下午2:15:01
	*/
	public void setReturnRateDao(IReturnRateDao returnRateDao) {
		this.returnRateDao = returnRateDao;
	}
	
	
	
	/**
	* @description 退单数据的检验
	* @param dbEntity
	* @param queryStatus
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月18日 上午9:54:55
	*/
	public ReturnRateEntity checkReturnRateEntity(ReturnRateEntity dbEntity,int queryStatus){
		//排单量
		if(dbEntity.getForecastWaybill()==null){
			dbEntity.setForecastWaybill(0L);
		}
		//实际装车量
		if(dbEntity.getQuantityCarReality()==null){
			dbEntity.setQuantityCarReality(0L);
		}
		//退单量
		dbEntity.setQuantityReturn(dbEntity.getForecastWaybill()-dbEntity.getQuantityCarReality());
		
		
		//全国排单量
		if(dbEntity.getForecastWaybillAll()==null){
			dbEntity.setForecastWaybillAll(0L);
		}
		//全国实际装车量
		if(dbEntity.getQuantityCarRealityAll()==null){
			dbEntity.setQuantityCarRealityAll(0L);
		}
		//全国退单量
		dbEntity.setQuantityReturnAll(dbEntity.getForecastWaybillAll()-dbEntity.getQuantityCarRealityAll());

		//排单率
		if(dbEntity.getForecastWaybill()<=0){
			if(queryStatus==0){
				dbEntity.setReturnRate(new Float(0));
			}else{
				dbEntity.setReturnRateAll(new Float(0));
			}
		}else{
			BigDecimal a = new BigDecimal(dbEntity.getForecastWaybill()+"");
			BigDecimal b = new BigDecimal(dbEntity.getQuantityReturn()+"");
			BigDecimal c = b.divide(a, 2, BigDecimal.ROUND_HALF_UP);//四舍五入
			c=  c.multiply(new BigDecimal(PlatformConstants.SONAR_NUMBER_100));
			if(queryStatus==0){
				dbEntity.setReturnRate(c.floatValue());
			}else{
				dbEntity.setReturnRateAll(c.floatValue());
			}
			
		}
		
		//全国的数据查询
		//排单率
		if(dbEntity.getForecastWaybillAll()<=0){
			if(queryStatus==0){
				dbEntity.setReturnRateAll(new Float(0));
			}else{
				dbEntity.setReturnRate(new Float(0));
			}
		}else{
			BigDecimal a = new BigDecimal(dbEntity.getForecastWaybillAll()+"");
			BigDecimal b = new BigDecimal(dbEntity.getQuantityReturnAll()+"");
			BigDecimal c = b.divide(a, 2, BigDecimal.ROUND_HALF_UP);//四舍五入
			c = c.multiply(new BigDecimal(PlatformConstants.SONAR_NUMBER_100));
			if(queryStatus==0){
				dbEntity.setReturnRateAll(c.floatValue());
			}else{
				dbEntity.setReturnRate(c.floatValue());
			}
			
		}
		return dbEntity;
	}
	/** 
	 *@desc 查询外场提交派送装车任务票数
	 *@param orgCodeList 外场编码
	 *@return List<ReturnRateEntity>
	 *@author 105795
	 *@date 2015年3月16日下午4:33:33 
	 */
	@Override
	public List<ReturnRateEntity> queryDeliverLoadWaybillQty(
			List<String> orgCodeList) {

		if(CollectionUtils.isEmpty(orgCodeList)||orgCodeList.size()<1){
			
			throw new BusinessException("外场编码为空");
		}
		return returnRateDao.queryDeliverLoadWaybillQty(orgCodeList);
	}
	
	/**
	 * 计算退单率  退单率=退单票数/提交派送装车任务时票数*100%
	 * 
	 * */
	private void calculateReturnRate(List<ReturnRateEntity> returnRateList){
		
	    //拿到外场
		List<String> orgCodeList=new ArrayList<String>();
		for(ReturnRateEntity returnRateEntity:returnRateList)
		{
			if(StringUtil.isNotEmpty(returnRateEntity.getOrgCode()))
			{
				orgCodeList.add(returnRateEntity.getOrgCode());
			}
		}
		if(orgCodeList.size()>0)
		{
			
			List<ReturnRateEntity> resultList=queryDeliverLoadWaybillQty(orgCodeList);
			if(!CollectionUtils.isEmpty(resultList)&&resultList.size()>0)
			{
				for(ReturnRateEntity entity1:returnRateList)
				{
					
					for(ReturnRateEntity entity2:resultList)
					{
						
						{
							BigDecimal returnQty = BigDecimal.ZERO;
							if(entity1.getQuantityReturn()!=null){
								 returnQty=new BigDecimal(entity1.getQuantityReturn());
							}
						
							BigDecimal loadQty = new BigDecimal(entity2.getLoadWaybillQty());
							if(returnQty.compareTo(BigDecimal.ZERO) != 0 &&loadQty.compareTo(BigDecimal.ZERO) != 0)
							{
								BigDecimal c = returnQty.divide(loadQty, 2, BigDecimal.ROUND_HALF_UP);//四舍五入
								c = c.multiply(new BigDecimal(PlatformConstants.SONAR_NUMBER_100));
								entity1.setReturnRate(c.floatValue());
							}else{
								
								entity1.setReturnRate(BigDecimal.ZERO.floatValue());
							}
							
						 }
						
					}
					
				}
				
			}
		}
		
	}
	
}
