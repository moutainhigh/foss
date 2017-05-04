package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.platform.api.server.dao.IStockPairDao;
import com.deppon.foss.module.transfer.platform.api.server.service.ISendRateService;
import com.deppon.foss.module.transfer.platform.api.server.service.IStockPairService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockPairEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TransCenterOrgEntity;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;

/**
* @description 库存副表的Service
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年3月4日 上午9:07:21
*/
public class StockPairServiceImpl implements IStockPairService {

	/**
	 * 记录日志
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(StockPairServiceImpl.class);
	
	
	/**
	 * 库存副表的Dao
	* @fields stockPairDao
	* @author 14022-foss-songjie
	* @update 2014年3月4日 上午9:07:01
	* @version V1.0
	*/
	private IStockPairDao stockPairDao;
	
	
	/**
	 * 派送率job
	* @fields sendRateService
	* @author 14022-foss-songjie
	* @update 2014年3月8日 下午5:21:43
	* @version V1.0
	*/
	private ISendRateService sendRateService;
	
	
	/**
	* @description 添加库存副表
	* @param stockPairEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月28日 上午9:35:42
	*/
	@Override
	public int addStockPair(StockPairEntity stockPairEntity) {
		return stockPairDao.addStockPair(stockPairEntity);
	}

	
	/**
	* @description  删除库存副表
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.service.IStockPairService#deleteStockPair(com.deppon.foss.module.transfer.platform.api.shared.domain.StockPairEntity)
	* @author 14022-foss-songjie
	* @update 2014年3月4日 上午9:10:51
	* @version V1.0
	*/
	@Override
	public int deleteStockPair(Map<String,String> map) {
		return stockPairDao.deleteStockPair(map);
	}

	
	/**
	* @description 查询库存副表
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.service.IStockPairService#queryStockPairEntity(com.deppon.foss.module.transfer.platform.api.shared.domain.StockPairEntity)
	* @author 14022-foss-songjie
	* @update 2014年3月4日 上午9:10:55
	* @version V1.0
	*/
	@Override
	public List<StockPairEntity> queryStockPairEntity(
			StockPairEntity stockPairEntity) {
		return stockPairDao.queryStockPairEntity(stockPairEntity);
	}

	
	/**
	* @description 更新库存副表
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.service.IStockPairService#updateStockPair(com.deppon.foss.module.transfer.platform.api.shared.domain.StockPairEntity)
	* @author 14022-foss-songjie
	* @update 2014年3月4日 上午9:10:57
	* @version V1.0
	*/
	@Override
	public int updateStockPair(Map<String,String> map) {
		return stockPairDao.updateStockPair(map);
	}
	
	/**
	* @description 定时任务
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.service.IStockPairService#doStockPairJob(int, int)
	* @author 14022-foss-songjie
	* @update 2014年3月4日 上午9:26:03
	* @version V1.0
	*/
	@Override
	public void doStockPairJob(Date queryDate,int threadNo, int threadCount) throws Exception {
		//24点执行任务
		//所有外场code以及对应的派送货区集合
		Map<String,String> map = new HashMap<String, String>();
		/**
		 * 不支持多线程
		map.put("threadCount", threadCount+"");
		map.put("threadNo", threadNo+"");
		 * 
		 */
		map.put("queryDate", DateUtils.convert(queryDate,DateUtils.DATE_FORMAT));
		try{
		
		List<TransCenterOrgEntity> orgList = stockPairDao.queryAllTransOrg();
		if(orgList!=null){
			for (TransCenterOrgEntity transCenterOrgEntity : orgList) {
				String tmpOrgCode = transCenterOrgEntity.getOrgCode();
				map.put("orgCode", tmpOrgCode);
				List<StockPairEntity> stockList = stockPairDao.queryStockPairJob(map);
				//把此刻的库存表(tfr.t_opt_stock) 全部拷贝到 tfr.t_opt_stock_pair表
				if(stockList!=null && stockList.size()>0){
					for (StockPairEntity dbEntity : stockList) {
						dbEntity.setId(UUIDUtils.getUUID());
						dbEntity.setInStockTime(queryDate);
						this.addStockPair(dbEntity);
					}
				}
			}
		}	
		/**
		if(stockList!=null && stockList.size()>0){
			stockPairDao.batchAddStockPair(stockList);
		}
		*/
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	

	/**
	* @description 定时任务执行完毕之后
	* @throws Exception
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月20日 上午9:19:35
	*/
	@Override
	public void doStockPairJobContinue(Date queryDate) throws Exception {
		//日派送率的统计数据
		/**
		 * 插入日派送
		*/
		sendRateService.doSendRateJobList(queryDate, 0, 0);
		
		/**
		 
		//退单率的统计数量
		//排单票数为预排单票数
		//退单票数=排单票数 - 排单件数与装车件数一致的运单
		//退单率=退单票数/排单票数*100%
		
		//插入退单
		
		//拉回率的统计数量
		//拉回票数：6点到第二天6点 ; 拉回票数=装车票数 –装车件数与签收件数一致的运单
		//拉回率=拉回票数/装车票数*100%
		
		//插入拉回
		 */
		//删除掉tfr.t_opt_stock_pair表里IS_SEND_RATE='Y' 
		Map<String,String> deleteMap = new HashMap<String, String>();
		//延迟三天 删除库存副表
		Date deleteTime = DateUtils.addDayToDate(queryDate, -PlatformConstants.SONAR_NUMBER_3);
		 deleteMap.put("queryDate", DateUtils.convert(deleteTime,DateUtils.DATE_FORMAT));
		stockPairDao.deleteStockPair(deleteMap);
	}


	/**
	* @description 所有外场(包含有派送部的外场)对应的 事业部 本部
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月13日 上午11:39:50
	*/
	@Override
	public List<TransCenterOrgEntity> queryAllTransOrg() {
		return stockPairDao.queryAllTransOrg();
	}
	
	

	/**
	* @description 所有外场对应的 事业部 本部
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月2日 上午11:00:44
	*/
	@Override
	public List<TransCenterOrgEntity> queryAllOutTransOrg() {
		return stockPairDao.queryAllOutTransOrg();
	}


	/**
	* @description 获取库存副表的Dao
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月4日 上午9:07:35
	*/
	public IStockPairDao getStockPairDao() {
		return stockPairDao;
	}

	
	/**
	* @description 设置库存副表的Dao
	* @param stockPairDao
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月4日 上午9:07:38
	*/
	public void setStockPairDao(IStockPairDao stockPairDao) {
		this.stockPairDao = stockPairDao;
	}


	
	/**
	* @description 获取派送率job
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月8日 下午5:22:19
	*/
	public ISendRateService getSendRateService() {
		return sendRateService;
	}


	
	/**
	* @description 设置 派送率job
	* @param sendRateService
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月8日 下午5:22:22
	*/
	public void setSendRateService(ISendRateService sendRateService) {
		this.sendRateService = sendRateService;
	}
	
	
	
	

}
