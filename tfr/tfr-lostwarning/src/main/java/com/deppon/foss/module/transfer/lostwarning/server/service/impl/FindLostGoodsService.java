package com.deppon.foss.module.transfer.lostwarning.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.lostwarning.api.server.dao.IFindLostGoodsDao;
import com.deppon.foss.module.transfer.lostwarning.api.server.define.LostWarningConstant;
import com.deppon.foss.module.transfer.lostwarning.api.server.domain.FindLostGoodsEntity;
import com.deppon.foss.module.transfer.lostwarning.api.server.domain.FindLostGoodsIDEntity;
import com.deppon.foss.module.transfer.lostwarning.api.server.domain.FindLostGoodsSerialEntity;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.LostGoodsFindOrgDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.LostWarningLogDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.utils.Utils;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;

/**
 * 丢货数据找到处理
 * 
 * 项目名称：tfr-lostwarning
 * 
 * 类名称：FindLostGoodsService
 * 
 * 创建人：lyz 263072
 * 
 * 创建时间：2015-8-7 上午9:01:40
 * 
 * 版权所有：上海德邦物流有限公司
 */
public class FindLostGoodsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FindLostGoodsService.class);
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final SimpleDateFormat sdfHms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//线程休眠时间（毫秒）
//	private static final long sleepMillis = 5000L;
	
	//丢货找到处理下限时间
	public Date paramTime = new Date();

	private IFindLostGoodsDao findLostGoodsDao;

	public void setFindLostGoodsDao(IFindLostGoodsDao findLostGoodsDao) {
		this.findLostGoodsDao = findLostGoodsDao;
	}
	/**
	 * @Description: 入库表记录同步到新建的表中
	 * @date 2017-02-28 下午2:45:37
	 * @author 336785
	 */
    public void dealInstockSynchron(){
    	//通过for循环将入库表记录同步到新建的表中去
    	for(int i=0;i<LostWarningConstant.INSTOCK_TO_NEWRECORD;i++){
    		findLostGoodsDao.inserNewInstockData(i);
    	}
    	LOGGER.info("同步入库表到临时表数据同步成功");
    	//保存新建入库信息表最新十五天的数据，超过该时间段的属于进行删除操作
    	   findLostGoodsDao.deleteInstockNewInVaildData();
    } 
	/**
	 * @Description: 丢货找到处理主线程
	 * @date 2015-8-7 下午4:05:37
	 * @author 263072
	 */
	public void findLostGoods() {

		/**
		 * 丢货找到处理规则：
		 * 1.每月10号之前（包括10号），统计上月1号到当前时间的数据
		 * 2.每月10号之后（不包括10号），统计当月1号到现在时间的数据
		 */
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			
			// 判断当前时间是否在10号之后
			if (calendar.get(Calendar.DAY_OF_MONTH) >= LostWarningConstant.SONAR_NUMBER_10) {
				//当月1号
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				paramTime = calendar.getTime();
			}else{
				//上月1号
				calendar.add(Calendar.MONTH, -1);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				paramTime = calendar.getTime();
			}
			
			
//-------------------------------代码添加区begin-------------------------------
			
			/***处理签收且上报存在的数据（此处注掉，下面另有签收）***/
			/*findLostGoodsDao.insertBillAndSigned(sdf.format(paramTime));
			List<LostWarningLogDto> wayBillListSigned = findLostGoodsDao.queryLostGoodsWayBillAndSigned();
			if(wayBillListSigned.size()>0){
			for(LostWarningLogDto dto3:wayBillListSigned){
				String wayBillNo3 = dto3.getWayBillNo();
				if(wayBillNo3 != null){
				List<LostWarningLogDto> list3= findLostGoodsDao.queryLostGoodsNotFound(wayBillNo3, sdf.format(paramTime));
				if(list3.size()==0){
					continue;
				}
				findGoodsByWayBillSign(wayBillNo3,list3);
				findLostGoodsDao.deleteSignedData(wayBillNo3);
				}
			}
			}*/
			
			/**
			 *  1----失效
			 *	2----装车扫描找到
			 *	3----清仓扫描找到
			 *	4----叉车司机扫描
			 *	5----分拣扫描
			 *	6----签收找到
			 *	7----卸车扫描找到  单票入库找到
			 */
			
			
			/***处理失效且上报存在的数据***/
			findLostGoodsDao.insertBillAndEfficacy(sdf.format(paramTime));
			while(true){
				List<LostWarningLogDto> wayBillListEfficacy = findLostGoodsDao.queryLostGoodsWayBillAndEfficacy("1");
				if(wayBillListEfficacy.size()<=0){
					break;
				}
					for(LostWarningLogDto dto1:wayBillListEfficacy){
						String wayBillNo1 = dto1.getWayBillNo();
						if(wayBillNo1 != null){
							List<LostWarningLogDto> list1 = findLostGoodsDao.queryLostGoodsNotFound(wayBillNo1, sdf.format(paramTime));
							if(list1.size()==0){
								continue;
							}
							findGoodsByInvaildWayBill(wayBillNo1,list1);
							findLostGoodsDao.deleteEfficacyData(wayBillNo1);
						}
					}
//				}else{
//					break;
//				}
			}
		
			
			
			
			/***处理作废且上报存在的数据***/
			
			findLostGoodsDao.insertBillAndVoided(sdf.format(paramTime));
			List<LostWarningLogDto> wayBillListVoided = findLostGoodsDao.queryLostGoodsWayBillAndVoided();
			if(wayBillListVoided.size()>0){
				for(LostWarningLogDto dto2:wayBillListVoided){
					String wayBillNo2 = dto2.getWayBillNo();
					if(wayBillNo2 != null){
					List<LostWarningLogDto> list2= findLostGoodsDao.queryLostGoodsNotFound(wayBillNo2, sdf.format(paramTime));
					if(list2.size()==0){
						continue;
					}
					findGoodsByInvaildWayBill(wayBillNo2,list2);
					findLostGoodsDao.deleteVoidedData(wayBillNo2);}
				}
			}
			

//-------------------------------代码添加区end---------------------------------
			
			
//------------------------------所有环节去线程，按模块处理------------------------------------------
	
			/***装车扫描找到***/
			//找到插入
			LOGGER.info("################装车扫描开始处理#################");
			findLostGoodsDao.insertLoadData(sdf.format(paramTime));
			while(true){
				List<LostWarningLogDto> wayBillListLoad = findLostGoodsDao.queryLostGoodsWayBillAndEfficacy("2");
				//sonar-352203
				if(wayBillListLoad.size()<=0){
					break;
				}
					for(LostWarningLogDto dto:wayBillListLoad){
						String wayBillNo = dto.getWayBillNo();
						LOGGER.info("#####################"+wayBillNo+"###############");
						//sonar-352203
						if(wayBillNo==null){
							continue;
						}
							List<LostWarningLogDto> list = findLostGoodsDao.queryLostGoodsNotFound(wayBillNo, sdf.format(paramTime));
							if(list.size()==0){
								findLostGoodsDao.deleteEfficacyData(wayBillNo);
								continue;
							}
							//上报QMS的集合
							List<FindLostGoodsEntity> requestList = new ArrayList<FindLostGoodsEntity>();
							//丢货系统上报记录的ID集合，用于更新记录状态
							List<String> idList = new ArrayList<String>();
							List<LostGoodsFindOrgDto> findGoodsList = findLostGoodsDao.queryLoadGoodsInfo(wayBillNo);
							
							if(findGoodsList.size()>0){
								FindLostGoodsIDEntity entity = findGoodsComm(idList,list,findGoodsList);
								if(entity!=null){
									FindLostGoodsEntity bean = new FindLostGoodsEntity();
									bean = copyBeanByFindLostGoodsIDEntity(bean,entity);
									bean.setFindScene("1");
									requestList.add(bean);
									idList.addAll(entity.getIdList());
								}
								if(requestList.size()>0){
									findGoodsHandle(wayBillNo, idList, requestList);
								}	
							}
							findLostGoodsDao.deleteEfficacyData(wayBillNo);
//						}
					}
					LOGGER.info("################装车扫描j结束处理#################");
//				}else{
//					break;
//				}
			}
			
			/********清仓扫描找到 ********/
			findLostGoodsDao.insertInventoryData(sdf.format(paramTime));
			while(true){
				List<LostWarningLogDto> wayBillListInventory = findLostGoodsDao.queryLostGoodsWayBillAndEfficacy("3");
				//sonar-352203
				if(wayBillListInventory.size()<=0){
					break;
				}
					for(LostWarningLogDto dto:wayBillListInventory){
						String wayBillNo = dto.getWayBillNo();
						LOGGER.info("###################"+wayBillNo+"#############");
						//sonar-352203
						if(wayBillNo==null){
							continue;
						}
							List<LostWarningLogDto> list = findLostGoodsDao.queryLostGoodsNotFound(wayBillNo, sdf.format(paramTime));
							if(list.size()==0){
								findLostGoodsDao.deleteEfficacyData(wayBillNo);
								continue;
							}
							List<FindLostGoodsEntity> requestList = new ArrayList<FindLostGoodsEntity>();
							//丢货系统上报记录的ID集合，用于更新记录状态
							List<String> idList = new ArrayList<String>();
							List<LostGoodsFindOrgDto>	findGoodsList = findLostGoodsDao.queryCheckStockInfo(wayBillNo);
							if(findGoodsList.size()>0){
								FindLostGoodsIDEntity entity = findGoodsComm(idList,list,findGoodsList);
								if(entity!=null){
									FindLostGoodsEntity bean = new FindLostGoodsEntity();
									bean = copyBeanByFindLostGoodsIDEntity(bean,entity);
									bean.setFindScene("4");
									requestList.add(bean);
									idList.addAll(entity.getIdList());
								}
								if(requestList.size()>0){
									findGoodsHandle(wayBillNo, idList, requestList);
								}
							}
							findLostGoodsDao.deleteEfficacyData(wayBillNo);
//						}
					}
//				}else{
//					break;
//				}
			}
			
			/********.叉车司机扫描********/
			findLostGoodsDao.insertDriverData(sdf.format(paramTime));
			while(true){
				List<LostWarningLogDto> wayBillListDriver = findLostGoodsDao.queryLostGoodsWayBillAndEfficacy("4");
				//sonar-352203
				if(wayBillListDriver.size()<=0){
					break;
				}
					for(LostWarningLogDto dto:wayBillListDriver){
						String wayBillNo = dto.getWayBillNo();
						LOGGER.info("###################"+wayBillNo+"#############");
						//sonar-352203
						if(wayBillNo==null){
							continue;
						}
							List<LostWarningLogDto> list = findLostGoodsDao.queryLostGoodsNotFound(wayBillNo, sdf.format(paramTime));
							if(list.size()==0){
								findLostGoodsDao.deleteEfficacyData(wayBillNo);
								continue;
							}
							List<FindLostGoodsEntity> requestList = new ArrayList<FindLostGoodsEntity>();
							//丢货系统上报记录的ID集合，用于更新记录状态
							List<String> idList = new ArrayList<String>();
							List<LostGoodsFindOrgDto> findGoodsList = findLostGoodsDao.queryTrayScanGoodsInfo(wayBillNo);
							if(findGoodsList.size()>0){
								FindLostGoodsIDEntity entity = findGoodsComm(idList,list,findGoodsList);
								if(entity!=null){
									FindLostGoodsEntity bean = new FindLostGoodsEntity();
									bean = copyBeanByFindLostGoodsIDEntity(bean,entity);
									bean.setFindScene("10");
									requestList.add(bean);
									idList.addAll(entity.getIdList());
						
								}
								if(requestList.size()>0){
									findGoodsHandle(wayBillNo, idList, requestList);
								}
							}
							findLostGoodsDao.deleteEfficacyData(wayBillNo);
//						}
					}
//				}else{
//					break;
//				}
			}
						
			/********.分拣扫描********/	
			findLostGoodsDao.insertSortingData(sdf.format(paramTime));
			LOGGER.info("分拣扫描开始正常执行收集数据");
			while(true){	
				List<LostWarningLogDto> wayBillListSorting = findLostGoodsDao.queryLostGoodsWayBillAndEfficacy("5");
				//352203-sonar
				if(wayBillListSorting.size()<=0){
					break;
				}
					for(LostWarningLogDto dto:wayBillListSorting){
						String wayBillNo = dto.getWayBillNo();
						LOGGER.info("###################"+wayBillNo+"#############");
						//sonar-352203
						if(wayBillNo==null){
							continue;
						}
							List<LostWarningLogDto> list = findLostGoodsDao.queryLostGoodsNotFound(wayBillNo, sdf.format(paramTime));
							if(list.size()==0){
								findLostGoodsDao.deleteEfficacyData(wayBillNo);
								continue;
							}
							List<FindLostGoodsEntity> requestList = new ArrayList<FindLostGoodsEntity>();
							//丢货系统上报记录的ID集合，用于更新记录状态
							List<String> idList = new ArrayList<String>();
							List<LostGoodsFindOrgDto>	findGoodsList = findLostGoodsDao.querySortingScanGoodsInfo(wayBillNo);
							if(findGoodsList.size()>0){
								FindLostGoodsIDEntity entity = findGoodsComm(idList,list,findGoodsList);
								if(entity!=null){
									FindLostGoodsEntity bean = new FindLostGoodsEntity();
									bean = copyBeanByFindLostGoodsIDEntity(bean,entity);
									bean.setFindScene("11");
									requestList.add(bean);
									idList.addAll(entity.getIdList());
								}
								/**** 若全部找到则结束后续操作，直接进行上报 ****/
								if(idList.size()>0){
									findGoodsHandle(wayBillNo, idList, requestList);
								}
							}
							findLostGoodsDao.deleteEfficacyData(wayBillNo);
//						}
					}
//				}else{
//					break;
//				}
			}
				
			/********.签收找到********/
			//通过for循环插入数据到临时表中
			 int days= daysBetween(paramTime,new Date());
			 LOGGER.info("签收找到开始正常执行收集数据");
			 LOGGER.info("签收找到开始正常执行收集数据"+days+"当前时间"+paramTime);
			for(int day=0;day<=days;day++){
				LOGGER.info("签收找到开始正常执行收集数据");
				LOGGER.info("签收找到循环开始正常执行收集数据"+days);
				findLostGoodsDao.insertSSignedData(sdf.format(paramTime),day);
			} 
			while(true){	
				List<LostWarningLogDto> wayBillListSSigned = findLostGoodsDao.queryLostGoodsWayBillAndEfficacy("6");
				//sonar-352203
				if(wayBillListSSigned.size()<=0){
					break;
				}
					for(LostWarningLogDto dto:wayBillListSSigned){
						String wayBillNo = dto.getWayBillNo();
						LOGGER.info("###################"+wayBillNo+"#############");
						//sonar-352203
						if(wayBillNo==null){
							continue;
						}
							List<LostWarningLogDto> list = findLostGoodsDao.queryLostGoodsNotFound(wayBillNo, sdf.format(paramTime));
							if(list.size()==0){
								findLostGoodsDao.deleteEfficacyData(wayBillNo);
								continue;
							}
							List<FindLostGoodsEntity> requestList = new ArrayList<FindLostGoodsEntity>();
							//丢货系统上报记录的ID集合，用于更新记录状态
							List<String> idList = new ArrayList<String>();
							List<LostGoodsFindOrgDto> findGoodsList = findLostGoodsDao.querySignScanGoodsInfo(wayBillNo);
							if(findGoodsList.size()>0){
								FindLostGoodsIDEntity entity = findGoodsComm(idList,list,findGoodsList);
								if(entity!=null){
									FindLostGoodsEntity bean = new FindLostGoodsEntity();
									bean = copyBeanByFindLostGoodsIDEntity(bean,entity);
									bean.setFindScene("12");
									requestList.add(bean);
									idList.addAll(entity.getIdList());
								}
								if(idList.size()>0){
									findGoodsHandle(wayBillNo, idList, requestList);
								}
							}
							findLostGoodsDao.deleteEfficacyData(wayBillNo);
//						}
					}
//				}else{
//					break;
//				}
			}
			
		/****卸车扫描找到  单票入库找到**/
			findLostGoodsDao.insertUnloadData(sdf.format(paramTime));
			while(true){	
				List<LostWarningLogDto> wayBillListUnloaded = findLostGoodsDao.queryLostGoodsWayBillAndEfficacy("7");
				//352203-sonar
				if(wayBillListUnloaded.size()<=0){
					break;
				}
					for(LostWarningLogDto dtoo:wayBillListUnloaded){
						String wayBillNo = dtoo.getWayBillNo();
						LOGGER.info("###################"+wayBillNo+"#############");
						//sonar-352203
						if(wayBillNo==null){
							continue;
						}
							List<LostWarningLogDto> list = findLostGoodsDao.queryLostGoodsNotFound(wayBillNo, sdf.format(paramTime));
							if(list.size()==0){
								findLostGoodsDao.deleteEfficacyData(wayBillNo);
								continue;
							}
							List<FindLostGoodsEntity> requestList = new ArrayList<FindLostGoodsEntity>();
							//丢货系统上报记录的ID集合，用于更新记录状态
							List<String> idList = new ArrayList<String>();
							List<LostGoodsFindOrgDto> findGoodsList = findLostGoodsDao.queryInStockGoodsInfo(wayBillNo);
							if(findGoodsList.size()>0){
								//sonar-352203提出为方法
								findGoodsListSizeThanZero(wayBillNo, list,
										requestList, idList, findGoodsList);
							}
							findLostGoodsDao.deleteEfficacyData(wayBillNo);
//						}
					}
//				}else{
//					break;
//				}
			}
		} catch (Exception e) {
			LOGGER.error("丢货找到处理异常： "+ExceptionUtils.getFullStackTrace(e));
		}
	}

	/**
	 * @param wayBillNo
	 * @param list
	 * @param requestList
	 * @param idList
	 * @param findGoodsList
	 */
	private void findGoodsListSizeThanZero(String wayBillNo,
			List<LostWarningLogDto> list,
			List<FindLostGoodsEntity> requestList, List<String> idList,
			List<LostGoodsFindOrgDto> findGoodsList) {
		//卸车类型-入库数据
		List<LostGoodsFindOrgDto> unLoadFindList = new ArrayList<LostGoodsFindOrgDto>();
		//单票入库类型
		List<LostGoodsFindOrgDto> singleInStockList = new ArrayList<LostGoodsFindOrgDto>();
		
		for(LostGoodsFindOrgDto dto:findGoodsList){
			if(StringUtils.equals(StockConstants.UNLOAD_GOODS_IN_STOCK_TYPE, dto.getInStockType())){
				unLoadFindList.add(dto);
			}else{
				singleInStockList.add(dto);
			}
		}
		
		//筛选卸车扫描是否有丢货数据
		if(unLoadFindList.size()>0){
			FindLostGoodsIDEntity entity = findGoodsComm(idList,list,unLoadFindList);
			if(entity!=null){
				FindLostGoodsEntity bean = new FindLostGoodsEntity();
				bean = copyBeanByFindLostGoodsIDEntity(bean,entity);
				bean.setFindScene("2");
				requestList.add(bean);
				idList.addAll(entity.getIdList());
			}
			
			/**** 若全部找到则结束后续操作，直接进行上报 ****/
			if(idList.size()>0){
				findGoodsHandle(wayBillNo, idList, requestList);
			}
		}
		
		//筛选单票入库扫描是否有丢货数据
		if(singleInStockList.size()>0){
			FindLostGoodsIDEntity entity = findGoodsComm(idList,list,singleInStockList);
			if(entity!=null){
				FindLostGoodsEntity bean = new FindLostGoodsEntity();
				bean = copyBeanByFindLostGoodsIDEntity(bean,entity);
				bean.setFindScene("6");
				requestList.add(bean);
				idList.addAll(entity.getIdList());
			}
			
			/**** 若全部找到则结束后续操作，直接进行上报 ****/
			if(idList.size()>0){
				findGoodsHandle(wayBillNo, idList, requestList);
				
			}
		}
	}
//------------------------------所有环节去线程，按模块处理------------------------------------------
			
	
			/*ThreadPoolFactory poolFactory = ThreadPoolFactory.getInstance();

			//*** 获取已上报但未处理的运单信息 ***//*
			List<LostWarningLogDto> wayBillList = findLostGoodsDao.queryLostGoodsWayBill(sdf.format(paramTime));
			for(LostWarningLogDto dto:wayBillList){
				String wayBillNo = dto.getWayBillNo();
				poolFactory.addTask(new dealFindGoodsTask(wayBillNo));
				try{
					Thread.sleep(sleepMillis);
				}catch (Exception e) {
					LOGGER.error(ExceptionUtils.getFullStackTrace(e));
				}
			}
			
			//关闭线程池
			poolFactory.closePool();*/
		

	
	/**
	 * 找到丢货处理任务
	 */
	class dealFindGoodsTask implements Runnable {
		private String wayBillNo;  
		  
		dealFindGoodsTask(String wayBillNo)  
        {  
            this.wayBillNo = wayBillNo;  
        }  
		@Override
		public void run() {
			try{
				/**
				 * 查询以下场景进行是否有丢货扫描数据，并按照场景进行封装数据
				 *  1:装车扫描找到
					2:卸车扫描找到
					3:PDA找货找到
					4:清仓扫描找到
					6:单票入库找到
					9:流水号失效
					10.叉车司机扫描
					11.分拣扫描
					12. 签收找到
				 */
				
			
//=========================================代码注释区======================
				/********9.流水号失效（单独处理，当运单失效则所有流水号作废，不需要进行其他场景的处理）********/
				//判断运单是否失效或作废
			/*	if(findLostGoodsDao.queryWayBillCount(wayBillNo)==0){
					findGoodsByInvaildWayBill(wayBillNo,list);
					return;
				}
				else if (findLostGoodsDao.queryInvalidWayBill(wayBillNo)>0){
					findGoodsByInvaildWayBill(wayBillNo,list);
					return;
				}
				
				//****判断运单是否签收  2015-11-24 16:40:08 *****//*
				if(findLostGoodsDao.queryWayBillIsSign(wayBillNo)>0){
					findGoodsByWayBillSign(wayBillNo,list);
					return;
				}*/
//========================================代码注释区============================
				//根据运单获取丢货详细信息
				List<LostWarningLogDto> list = findLostGoodsDao.queryLostGoodsNotFound(wayBillNo, sdf.format(paramTime));
				if(list.size()==0){
					return;
				}
				
				//上报QMS的集合
				List<FindLostGoodsEntity> requestList = new ArrayList<FindLostGoodsEntity>();
				//丢货系统上报记录的ID集合，用于更新记录状态
				List<String> idList = new ArrayList<String>();
				
				/********1:装车扫描找到********/
				List<LostGoodsFindOrgDto> findGoodsList = findLostGoodsDao.queryLoadGoodsInfo(wayBillNo);
				if(findGoodsList.size()>0){
					FindLostGoodsIDEntity entity = findGoodsComm(idList,list,findGoodsList);
					if(entity!=null){
						FindLostGoodsEntity bean = new FindLostGoodsEntity();
						bean = copyBeanByFindLostGoodsIDEntity(bean,entity);
						bean.setFindScene("1");
						requestList.add(bean);
						idList.addAll(entity.getIdList());
					}
				}
				
				/**** 若全部找到则结束后续操作，直接进行上报 ****/
				if(idList.size()==list.size()){
					findGoodsHandle(wayBillNo, idList, requestList);
					return;
				}
				
				/********2:卸车扫描找到  6:单票入库找到 （该两种场景在入库表中即可获取） ********/
				findGoodsList = findLostGoodsDao.queryInStockGoodsInfo(wayBillNo);
				if(findGoodsList.size()>0){
					//卸车类型-入库数据
					List<LostGoodsFindOrgDto> unLoadFindList = new ArrayList<LostGoodsFindOrgDto>();
					//单票入库类型
					List<LostGoodsFindOrgDto> singleInStockList = new ArrayList<LostGoodsFindOrgDto>();
					
					for(LostGoodsFindOrgDto dto:findGoodsList){
						if(StringUtils.equals(StockConstants.UNLOAD_GOODS_IN_STOCK_TYPE, dto.getInStockType())){
							unLoadFindList.add(dto);
						}else{
							singleInStockList.add(dto);
						}
					}
					
					//筛选卸车扫描是否有丢货数据
					if(unLoadFindList.size()>0){
						FindLostGoodsIDEntity entity = findGoodsComm(idList,list,findGoodsList);
						if(entity!=null){
							FindLostGoodsEntity bean = new FindLostGoodsEntity();
							bean = copyBeanByFindLostGoodsIDEntity(bean,entity);
							bean.setFindScene("2");
							requestList.add(bean);
							idList.addAll(entity.getIdList());
						}
						
						/**** 若全部找到则结束后续操作，直接进行上报 ****/
						if(idList.size()==list.size()){
							findGoodsHandle(wayBillNo, idList, requestList);
							return;
						}
					}
					
					//筛选单票入库扫描是否有丢货数据
					if(singleInStockList.size()>0){
						FindLostGoodsIDEntity entity = findGoodsComm(idList,list,findGoodsList);
						if(entity!=null){
							FindLostGoodsEntity bean = new FindLostGoodsEntity();
							bean = copyBeanByFindLostGoodsIDEntity(bean,entity);
							bean.setFindScene("6");
							requestList.add(bean);
							idList.addAll(entity.getIdList());
						}
						
						/**** 若全部找到则结束后续操作，直接进行上报 ****/
						if(idList.size()==list.size()){
							findGoodsHandle(wayBillNo, idList, requestList);
							return;
						}
					}
				}
				
				/********4:清仓扫描找到********/
				findGoodsList = findLostGoodsDao.queryCheckStockInfo(wayBillNo);
				if(findGoodsList.size()>0){
					FindLostGoodsIDEntity entity = findGoodsComm(idList,list,findGoodsList);
					if(entity!=null){
						FindLostGoodsEntity bean = new FindLostGoodsEntity();
						bean = copyBeanByFindLostGoodsIDEntity(bean,entity);
						bean.setFindScene("4");
						requestList.add(bean);
						idList.addAll(entity.getIdList());
					}
					
					/**** 若全部找到则结束后续操作，直接进行上报 ****/
					if(idList.size()==list.size()){
						findGoodsHandle(wayBillNo, idList, requestList);
						return;
					}
				}
				
				/********10.叉车司机扫描********/
				findGoodsList = findLostGoodsDao.queryTrayScanGoodsInfo(wayBillNo);
				if(findGoodsList.size()>0){
					FindLostGoodsIDEntity entity = findGoodsComm(idList,list,findGoodsList);
					if(entity!=null){
						FindLostGoodsEntity bean = new FindLostGoodsEntity();
						bean = copyBeanByFindLostGoodsIDEntity(bean,entity);
						bean.setFindScene("10");
						requestList.add(bean);
						idList.addAll(entity.getIdList());
					}
					
					/**** 若全部找到则结束后续操作，直接进行上报 ****/
					if(idList.size()==list.size()){
						findGoodsHandle(wayBillNo, idList, requestList);
						return;
					}
				}
				
				/********11.分拣扫描********/
				findGoodsList = findLostGoodsDao.querySortingScanGoodsInfo(wayBillNo);
				if(findGoodsList.size()>0){
					FindLostGoodsIDEntity entity = findGoodsComm(idList,list,findGoodsList);
					if(entity!=null){
						FindLostGoodsEntity bean = new FindLostGoodsEntity();
						bean = copyBeanByFindLostGoodsIDEntity(bean,entity);
						bean.setFindScene("11");
						requestList.add(bean);
						idList.addAll(entity.getIdList());
					}
					
					/**** 若全部找到则结束后续操作，直接进行上报 ****/
					if(idList.size()==list.size()){
						findGoodsHandle(wayBillNo, idList, requestList);
						return;
					}
				}
				
				/********12.签收找到********/
				findGoodsList = findLostGoodsDao.querySignScanGoodsInfo(wayBillNo);
				if(findGoodsList.size()>0){
					FindLostGoodsIDEntity entity = findGoodsComm(idList,list,findGoodsList);
					if(entity!=null){
						FindLostGoodsEntity bean = new FindLostGoodsEntity();
						bean = copyBeanByFindLostGoodsIDEntity(bean,entity);
						bean.setFindScene("12");
						requestList.add(bean);
						idList.addAll(entity.getIdList());
					}
				}
				
				/*** 上报QMS并更新数据 ****/
				if(requestList.size()>0){
					findGoodsHandle(wayBillNo, idList, requestList);
				}
			}
			catch (Exception e) {
				LOGGER.error("丢货找到处理异常： "+ExceptionUtils.getFullStackTrace(e));
			}
		}  
	}
	
	
	/**
	 * @Description: 复制对象属性
	 * @date 2015-8-13 上午9:50:20   
	 * @author 263072 
	 */
	public FindLostGoodsEntity copyBeanByFindLostGoodsIDEntity(FindLostGoodsEntity bean,FindLostGoodsIDEntity sourceBean){
		bean.setLostRepCode(sourceBean.getLostRepCode());
		bean.setLoseStatus(sourceBean.getLoseStatus());
		bean.setDealTime(sourceBean.getDealTime());
		bean.setSysAutoTrackId(sourceBean.getSysAutoTrackId());
		bean.setFlowcodeList(sourceBean.getSerialList());
		bean.setDealContent(sourceBean.getDealContent());
		return bean;
	}
	
	/**
	 * @Description: 扫描找到丢货处理公共方法
	 * @date 2015-8-12 下午2:47:00   
	 * @author 263072 
	 * @param list
	 * @return
	 */
	public FindLostGoodsIDEntity findGoodsComm(List<String> idList,List<LostWarningLogDto> lostList,List<LostGoodsFindOrgDto> findGoodsList){
		FindLostGoodsIDEntity entity = new FindLostGoodsIDEntity(); 
		try{
			//找到丢货对应上报信息的ID集合
			List<String> uploadIDList = new ArrayList<String>();
			//找到的流水信息
			List<FindLostGoodsSerialEntity> serialList = new ArrayList<FindLostGoodsSerialEntity>();
			String rcone=lostList.get(0).getLostRepCode();
			//当找到的时间大于丢货时间，则判断为找到
			for(LostWarningLogDto lostBean:lostList){
				//sonar-352203
				if(idList.contains(lostBean.getID()) || !StringUtils.equals(lostBean.getLostRepCode(),rcone)){
					continue;
				}
					for(LostGoodsFindOrgDto findBean:findGoodsList){
						//sonar-352203
						if(!StringUtils.equals(lostBean.getSerialNo(), findBean.getSerialNo())){
							continue;
						}
							LOGGER.info("======");
							if(lostBean.getCreateTime().getTime()<findBean.getCreateTime().getTime()){
								FindLostGoodsSerialEntity serialEntity = new FindLostGoodsSerialEntity();
								serialEntity.setFlowCode(findBean.getSerialNo());
								serialEntity.setFindStatus("2");//2:已找到
								serialEntity.setIsEffective("2");//1:无效 2:有效
								serialList.add(serialEntity);
								
								uploadIDList.add(lostBean.getID());
								break;
							}
//						}
					}
//				}
			}
			
			if(serialList.size()==0){
				return null;
			}
			entity.setIdList(uploadIDList);
			entity.setLostRepCode(lostList.get(0).getLostRepCode());
			entity.setLoseStatus("2");//2:已找到
			entity.setDealTime(sdfHms.format(new Date()));
			entity.setSysAutoTrackId(findLostGoodsDao.getSeqNextVal()+"");
			entity.setDealContent(LostWarningConstant.dealFindContentStr);
			entity.setSerialList(serialList);
		}catch (Exception e) {
			entity = null;
			LOGGER.error("装车扫描找到丢货处理异常： "+ExceptionUtils.getFullStackTrace(e));
		}
		return entity;
	}
	
	/**
	 * @Description: 运单失效
	 * @date 2015-8-12 上午8:53:50   
	 * @author 263072
	 */
	public void findGoodsByInvaildWayBill(String wayBillNo,List<LostWarningLogDto> list){
		List<FindLostGoodsEntity> requestList = new ArrayList<FindLostGoodsEntity>();
		FindLostGoodsEntity bean = new FindLostGoodsEntity();
		try{
			String rcone=list.get(0).getLostRepCode();
			bean.setLostRepCode(rcone);
			bean.setLoseStatus("2");//2:已找到
			bean.setDealTime(sdfHms.format(new Date()));
			bean.setSysAutoTrackId(findLostGoodsDao.getSeqNextVal()+"");
			bean.setFindScene("9");//9:流水号失效
			
			List<FindLostGoodsSerialEntity> serialList = new ArrayList<FindLostGoodsSerialEntity>();
			List<String> idList=new ArrayList<String>();
			for(LostWarningLogDto dto:list){
				if(StringUtils.equals(dto.getLostRepCode(),rcone)){
				idList.add(dto.getID());
				FindLostGoodsSerialEntity serailBean = new FindLostGoodsSerialEntity();
				serailBean.setFlowCode(dto.getSerialNo());
				serailBean.setFindStatus("2");//2:已找到
				serailBean.setIsEffective("1");//1:无效
				serialList.add(serailBean);
				}
			}
			bean.setDealContent(LostWarningConstant.dealFindContentStr);
			bean.setFlowcodeList(serialList);
			requestList.add(bean);
			//上报QMS
			findGoodsHandle(wayBillNo,idList,requestList);
			
		}catch (Exception e) {
			bean = null;
			LOGGER.error("丢货找到处理异常： "+ExceptionUtils.getFullStackTrace(e));
		}
	}
	
	
	/**
	 * @Description: 运单签收
	 * @date 2015-11-24 16:41:12 
	 * @author 263072
	 */
	public void findGoodsByWayBillSign(String wayBillNo,List<LostWarningLogDto> list){
		List<FindLostGoodsEntity> requestList = new ArrayList<FindLostGoodsEntity>();
		FindLostGoodsEntity bean = new FindLostGoodsEntity();
		try{
			String reone=list.get(0).getLostRepCode();
			bean.setLostRepCode(reone);
			bean.setLoseStatus("2");//2:已找到
			bean.setDealTime(sdfHms.format(new Date()));
			bean.setSysAutoTrackId(findLostGoodsDao.getSeqNextVal()+"");
			bean.setFindScene("12");//12. 签收找到
			List<String> idList=new ArrayList<String>();
			List<FindLostGoodsSerialEntity> serialList = new ArrayList<FindLostGoodsSerialEntity>();
			for(LostWarningLogDto dto:list){
				if(StringUtils.equals(dto.getLostRepCode(),reone)){
				idList.add(dto.getID());
				FindLostGoodsSerialEntity serailBean = new FindLostGoodsSerialEntity();
				serailBean.setFlowCode(dto.getSerialNo());
				serailBean.setFindStatus("2");//2:已找到
				serailBean.setIsEffective("2");//2:有效
				serialList.add(serailBean);
				}
				}
			bean.setDealContent(LostWarningConstant.dealFindContentStr);
			bean.setFlowcodeList(serialList);
			requestList.add(bean);
			//上报QMS
			findGoodsHandle(wayBillNo,idList,requestList);
			
		}catch (Exception e) {
			bean = null;
			LOGGER.error("丢货找到处理异常： "+ExceptionUtils.getFullStackTrace(e));
		}
	}
	
	
	/**
	 * @Description: 找到处理（上报QMS、更新DB数据）
	 * @date 2015-8-8 下午2:23:24   
	 * @author 263072 
	 * @param wayBillNo 运单号
	 * @param idList 需要更新的ID列表（可为空）
	 * @param list 上报找到丢货的集合
	 */
	public void findGoodsHandle(String wayBillNo,List<String> idList,List<FindLostGoodsEntity> list){
		try{
			String requestStr = JSONArray.fromObject(list).toString();
			boolean flag = false;
			//上报QMS，并解析返回的报文信息
			String respStr = FossToMcewService.getInstatce().reportFindGoodsData(requestStr);
			try{
				JSONObject obj = JSONObject.fromObject(respStr); 
				//上报状态标示符   0：失败 1：成功
	 			String retStatus = obj.get("retStatus").toString();
				//更新记录
	 			if("1".equals(retStatus)){
	 				flag = true;
	 			}
			}
			catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
			
			if(flag){
				findLostGoodsDao.updateLostGoodsStatus(wayBillNo,"1","",requestStr,idList);
			}else{
				//防止字符过长，导致插入失败
 				if(!Utils.isStrNull(respStr)){
 					if(respStr.length()>LostWarningConstant.SONAR_NUMBER_500){
 						respStr=respStr.substring(0, LostWarningConstant.SONAR_NUMBER_500);
 					}
 				}
				findLostGoodsDao.updateLostGoodsStatus(wayBillNo,"0",respStr,requestStr,idList);
			}
			
		}catch (Exception e) {
			LOGGER.error("丢货找到数据上报更新异常： "+ExceptionUtils.getFullStackTrace(e));
		}
	}
	/**
	 * 获取丢货上报未找到而且运单失效的运单号
	 * */
	
	    /**   
     * 计算两个日期之间相差的天数   
     * @param date1   
     * @param date2   
     * @return   
     */    
    public static int daysBetween(Date date1,Date date2)     
    {     
        Calendar cal = Calendar.getInstance();     
        cal.setTime(date1);     
        long time1 = cal.getTimeInMillis();                  
        cal.setTime(date2);     
        long time2 = cal.getTimeInMillis();          
        long between_days=(time2-time1)/(1000*3600*24);     
             
       return Integer.parseInt(String.valueOf(between_days));            
    } 

}
