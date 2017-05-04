package com.deppon.foss.module.transfer.lostwarning.server.service.impl;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.jgroups.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.inteface.domain.lostwarning.LostGoodsStock;
import com.deppon.esb.inteface.domain.lostwarning.SyncLostGoodsStockRequest;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.lostwarning.api.server.dao.IFindLostGoodsDao;
import com.deppon.foss.module.transfer.lostwarning.api.server.dao.ILostWarningDataDao;
import com.deppon.foss.module.transfer.lostwarning.api.server.define.LostWarningConstant;
import com.deppon.foss.module.transfer.lostwarning.api.server.domain.LostWarningDataEntity;
import com.deppon.foss.module.transfer.lostwarning.api.server.domain.WayBillSerialInfoEntity;
import com.deppon.foss.module.transfer.lostwarning.api.server.service.ILostWarningDataService;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.LostWarningTempDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.TransferMotorcadeDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.utils.ThreadPoolFactory;
import com.deppon.foss.module.transfer.lostwarning.api.shared.utils.Utils;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.util.define.FossConstants;

/**
 * 上报丢货数据
 * 
 * 项目名称：tfr-lostwarning
 * 
 * 类名称：UploadWarningDataService
 * 
 * 创建人：lyz 263072
 * 
 * 创建时间：2015-6-12 下午6:36:19
 * 
 * 版权所有：上海德邦物流有限公司
 */
public class UploadWarningDataService implements InitializingBean {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadWarningDataService.class);
	
	private ILostWarningDataService lostWarningDataService;

	private ILostWarningDataDao lostWarningDataDao;
	
	private LostWarningAnalyData lostWarningAnalyData;
	
	private IDataDictionaryValueService dataDictionaryValueService;
	
	private IStockService stockService;
	
	private ITfrCommonService tfrCommonService;
	
	private IFindLostGoodsDao findLostGoodsDao;

	public void setFindLostGoodsDao(IFindLostGoodsDao findLostGoodsDao) {
		this.findLostGoodsDao = findLostGoodsDao;
	}
	
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	
	public void setLostWarningDataService(
			ILostWarningDataService lostWarningDataService) {
		this.lostWarningDataService = lostWarningDataService;
	}

	public void setLostWarningDataDao(ILostWarningDataDao lostWarningDataDao) {
		this.lostWarningDataDao = lostWarningDataDao;
	}
	
	public void setLostWarningAnalyData(LostWarningAnalyData lostWarningAnalyData) {
		this.lostWarningAnalyData = lostWarningAnalyData;
	}
	
	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}

	/**
	 * @Description: 丢货数据上报处理主流程
	 * @date 2015-6-12 下午8:06:14   
	 * @author 263072
	 */
	public void dealWarningData(IStockService stockService,ITfrCommonService tfrCommonService){
		
		setStockService(stockService);
		setTfrCommonService(tfrCommonService);
		
		//加载静态基础数据 
		this.initBaseInfo();
		
		/** 每次运行JOB时，全部删除上一个JOB未执行完的中间表数据 ，重新开始运算  2015-11-29 13:39:29**/
		lostWarningDataDao.delALLWarningTempData();
		
		
		/**获取上个月一号的时间**/
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date paramTime  = calendar.getTime();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		LostWarningConstant.SQL_QUERY_STARTTIME=sdf.format(paramTime)+" 00:00:00";
		
		/****   1.将筛选的丢货数据同步到临时表 （10个线程处理）   ******/
		
		ThreadPoolFactory synPoolFactory = ThreadPoolFactory.getInstance();
		
		synPoolFactory.addTask(new analyWarningInfoTask("synStartDptLostWarningData"));
		synPoolFactory.addTask(new analyWarningInfoTask("synJZReceiveLostData"));
		synPoolFactory.addTask(new analyWarningInfoTask("synTransferLostData"));
		synPoolFactory.addTask(new analyWarningInfoTask("synAlreadyArriveLostData"));
		synPoolFactory.addTask(new analyWarningInfoTask("synTransferStoreLostData"));
		synPoolFactory.addTask(new analyWarningInfoTask("synHandoverLostData"));
		synPoolFactory.addTask(new analyWarningInfoTask("synDifferStockLostData"));
		// 注释原因是因为测试方便
		/*if(new Date().getHours()<=9 && new Date().getHours()>=7)
		{*/
			synPoolFactory.addTask(new analyWarningInfoTask("synDeliverLostData"));
		/*}*/
		/*根据业务需要暂时注释掉*/
		/*synPoolFactory.addTask(new analyWarningInfoTask("synAirTransferLostData"));*/
		synPoolFactory.addTask(new analyWarningInfoTask("synExpressExternalLostData"));
		
		//关闭线程池
		synPoolFactory.closePool();
		
		/****   2.多线程分发数据：一条数据分配一个线程，线程池不够用时，进行排队等待     ******/
		ThreadPoolFactory poolFactory = ThreadPoolFactory.getInstance();
		
		/*
		 * 循环读取数据，直到所有数据处理完毕
		 */
		while(true){
			/*** 每次读取数据前，先更新数据JOBID，再根据JOBID查询数据进行处理  ***/
			String jobID = lostWarningDataService.upateWarningDataForJob();
			List<LostWarningTempDto> list = lostWarningDataService.searchLostWarnningData(jobID);
			//当取不到数据时且分析数据线程池线程全部执行完毕，则结束该次JOB操作
			if(list==null||list.size()==0){
				if(synPoolFactory.getTerminatedStatus()){
					//关闭线程池
					poolFactory.closePool();
					break;
				}
			}
			
			//判断线程池状态，若已关闭则进行重启
			if(poolFactory.getPool().isShutdown()){
				poolFactory.openPool();
			}
			
			/*** 3.分发给多线程进行数据组合处理 ***/
			for(LostWarningTempDto dto:list){
				poolFactory.addTask(new UploadWarningInfoTask(dto));
			}
			/**** 4.下次读取DB进行等待处理   *****/
			try {
				Thread.currentThread().sleep(LostWarningConstant.SONAR_NUMBER_1000*2);
			} catch (InterruptedException e) {
				LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			}  
			
		}
		
	}
	
	
	/**
	 * 线程任务处理类，用于初步筛选丢货数据
	 */
	class analyWarningInfoTask implements Runnable {  
		//业务处理方法名
		private String methodName;  
		  
		analyWarningInfoTask(String methodName)  
        {  
            this.methodName = methodName;  
        }  
        public void run()  
        {  
        	try{
        		Method handleMethod = lostWarningDataService.getClass().getDeclaredMethod(methodName);
        		handleMethod.invoke(lostWarningDataService);
        	}catch (Exception e) {
        		LOGGER.error("分析丢货预警信息异常:"+ExceptionUtils.getFullStackTrace(e));
			}
        }
	}
	
	/**
	 * 线程任务处理类，用于解析预警类型，根据不同类型找到对应方法，分析处理后将数据上报
	 */
	class UploadWarningInfoTask implements Runnable {  
        private LostWarningTempDto taskData;  
  
        UploadWarningInfoTask(LostWarningTempDto task)  
        {  
            this.taskData = task;  
        }  
        public void run()  
        {  
            try{
            	if(taskData==null){
            		return;
            	}
            	/**
            	 * 判断是否被签收，若被签收则不作处理
            	 * 263072 2015-11-25 19:49:22
            	 */
            	/*long searchResult = findLostGoodsDao.queryWayBillIsSign(taskData.getWayBillNo());
            	if(searchResult>0){
            		lostWarningDataDao.delLostTempDataByBill(taskData.getWayBillNo(), taskData.getRepType());
            		return;
            	}*/
            	
            	/**
            	 * 判断运单是否无效，若无效则不作处理
            	 * modify by wwb at 2016年7月12日11:18:03 入库已做过滤
            	 * 263072 2015-11-27 09:23:02
            	 */
            /*	long searchResult = findLostGoodsDao.queryInvalidWayBill(taskData.getWayBillNo());
            	if(searchResult>0){
            		lostWarningDataDao.delLostTempDataByBill(taskData.getWayBillNo(), taskData.getRepType());
            		return;
            	}
            	*/
            	
            	/**** 1. 获取对应的方法名  ****/
            	String methodName = LostWarningConstant.warningTypeMethodNameMap.get(taskData.getRepType());
            	Method handleMethod = lostWarningAnalyData.getClass().getDeclaredMethod(methodName,
            			new Class[] { LostWarningTempDto.class });
            	/**** 2. 执行方法 ****/
            	LostWarningDataEntity bean = (LostWarningDataEntity)handleMethod.invoke(lostWarningAnalyData, taskData);
            	
            	//若返回实体信息为空或流水信息为空时，本次信息不做上报处理，同时删除中间表数据
            	if(bean==null){
            		lostWarningDataDao.delLostTempDataByBill(taskData.getWayBillNo(), taskData.getRepType());
            		return;
            	}else if(bean.getFlowCodeList()==null||bean.getFlowCodeList().size()==0){
            		lostWarningDataDao.delLostTempDataByBill(taskData.getWayBillNo(), taskData.getRepType());
            		return;
            	}
            	
            	
//            	List<WayBillSerialInfoEntity> serialList = bean.getFlowCodeList();
            	/******* 接口查询QMS是否重复上报（包含人工处理部分）  *******/
            	/*String alreadyReportGoods = FossToMcewService.getInstatce().queryReportGoodsByBill(taskData.getWayBillNo());
            	if(!"".equals(alreadyReportGoods)){
            		try{
	            		JSONObject jsonObj = JSONObject.fromObject(alreadyReportGoods); 
	            		String alreadyReportSerial = jsonObj.get("flowCode").toString();
	            		for(int i=0;i<serialList.size();i++){
	                		WayBillSerialInfoEntity serialEntity = serialList.get(i);
	                		for(String serialNo:alreadyReportSerial.split(",")){
	                			if(StringUtils.equals(serialEntity.getFlowCode(),serialNo)){
	            					//已存在上报记录，排除该流水信息
	                				serialList.remove(i);
	                				i--;
	                				break;
	                			}
	                		}
	                	}
            		}
            		catch(Exception e){
            			//上报失败
                		//lostWarningDataService.saveUploadFalseInfo(bean, alreadyReportGoods,taskData.getRepType(),"");
                		return;
            		}
            		
            	}*/
            	
            	//判断丢货列表是否为空
//            	if(serialList.size()==0){
//            		lostWarningDataDao.delLostTempDataByBill(taskData.getWayBillNo(), taskData.getRepType());
//            		return;
//            	}
            	
//            	if(serialList.size() < bean.getFlowCodeList().size()){
//            		bean.setFlowCodeList(serialList);
//            		bean.setIsWholeTicket("1");//由于已经删除部分流水信息，所以这里“是否整件丢失”字段直接定义 1否 
//            		bean.setLoseNum(serialList.size()+"");
//            	}
            	
            	List<LostWarningDataEntity> list = new ArrayList<LostWarningDataEntity>();
            	
        		list.add(bean);
            	/**** 3. 上报数据并解析返回报文信息 ****/
        		String uploadMsg = JSONArray.fromObject(list).toString();
            	String respStr = FossToMcewService.getInstatce().reportWarningData(uploadMsg);
            	
            	if(!"".equals(respStr)){
            		try{
            		    JSONObject obj = JSONObject.fromObject(respStr); 
                    	//上报状态标示符   0：失败 1：成功
            			String retStatus = obj.get("retStatus").toString();
            			//丢货编号
            			String lostRepCode = obj.get("lostRepCode").toString();
            			
            			/*** 4.记录日志并删除中间表记录 ****/
                    	if("1".equals(retStatus)){
                    		//上报成功
                    		lostWarningDataService.saveUploadSuccInfo(bean, lostRepCode,taskData.getRepType(),uploadMsg);
                    	//	inLoseDeptStock(list);
                    	}else{
                    		//上报失败
                    		//lostWarningDataService.saveUploadFalseInfo(bean, respStr,taskData.getRepType(),uploadMsg);
                    	}
            		}catch(Exception e){
            			//上报失败
                		//lostWarningDataService.saveUploadFalseInfo(bean, respStr,taskData.getRepType(),uploadMsg);
            		}
            		
            	}else{
            		//上报失败
            		//lostWarningDataService.saveUploadFalseInfo(bean, "上报异常",taskData.getRepType(),uploadMsg);
            	}
            } catch(Exception e) {  
            	LOGGER.error("上报丢货预警信息异常："+ExceptionUtils.getFullStackTrace(e));
            }  
        }  
  
    } 
	
	/**
	 * @Description: 将疑似丢货转移至特殊组织库存中（零担丢货管理组）
	 * @date 2015-8-28 下午2:11:55   
	 * @author 263072 
	 * @param list
	 */
	private void inLoseDeptStock(List<LostWarningDataEntity> list){
		if(list.size()==0){
			return;
		}
		
		LostWarningDataEntity bean = list.get(0);
		String operatorName = "";
		//获取操作名称
		if("1".equals(bean.getRepType())){
			//零担
			operatorName = LostWarningConstant.LOSTTYPE_MAP_LD.get(bean.getRepType());
		}else{
			//快递
			operatorName = LostWarningConstant.LOSTTYPE_MAP_KD.get(bean.getRepType());
		}
		try {
			//封装JMS请求对象
			SyncLostGoodsStockRequest request = new SyncLostGoodsStockRequest();
			List<LostGoodsStock> reqBeanList = request.getLostGoodsStock();
			for (WayBillSerialInfoEntity serialBean : bean.getFlowCodeList()) {
				LostGoodsStock reqBean = new LostGoodsStock();
				reqBean.setWayBillNum(bean.getWaybillNum());
				reqBean.setSerialNum(serialBean.getFlowCode());
				reqBean.setOrgCode(FossConstants.ROOT_ORG_CODE);
				reqBean.setOperateorName(operatorName);
				reqBean.setOperateorCode("Lose_Starting");
				reqBean.setInStockType(StockConstants.LOSE_GOODS_IN_STOCK_TYPE);
				reqBean.setInStockBillNum("");
				reqBeanList.add(reqBean);
			}
			// 创建服务头信息 上报QMS
			AccessHeader accessHeader = new AccessHeader();
			accessHeader.setEsbServiceCode(LostWarningConstant.ESB_JMS_SERVICE_CODE);
			accessHeader.setBusinessId(UUID.randomUUID().toString());
			accessHeader.setBusinessDesc1(LostWarningConstant.ESB_JMS_SERVICE_DESC);
			accessHeader.setVersion(LostWarningConstant.ESB_ACCESSHEADER_VERSION);
			// 推送数据
			ESBJMSAccessor.asynReqeust(accessHeader, request);
		} catch (ESBException e) {
			e.printStackTrace();
			LOGGER.error("调用异常  同步到库存虚拟组织");
		}
	}
	
	/**
	 * @Description: 查询车队所服务外场信息
	 * @date 2015-7-18 上午10:40:57   
	 * @author 263072 
	 * @return
	 */
	void getMotorCadeMap(){
		try{
			List<TransferMotorcadeDto> list = lostWarningDataDao.queryTransferMotorCadeList();
			for(TransferMotorcadeDto dto:list){
				LostWarningConstant.motorCadeMap.put(dto.getOrgCode(), dto);
			}
		}
		catch (Exception e) {
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
		}
	}
	
	/**
	 * @Description: 查询业务时间限制参数数据字典信息
	 * @date 2015-7-24 下午7:07:22   
	 * @author 263072
	 */
	void initConstantInfo(){
		List<DataDictionaryValueEntity>  list = new ArrayList<DataDictionaryValueEntity>();
		try {
			list = dataDictionaryValueService.queryDataDictionaryValueByTermsCode(DictionaryConstants.TFR_LOSTGOODS_TIMELIMIT_TYPE);
			for(DataDictionaryValueEntity bean:list){
				String value = bean.getExtAttribute1();
				try{
					if(!Utils.isStrNull(value)){
						if(LostWarningConstant.timeLimitMap.containsKey(bean.getValueCode())){
							LostWarningConstant.timeLimitMap.put(bean.getValueCode(), Double.parseDouble(value));
						}else if(LostWarningConstant.LOADDATA_COUNTLIMIT.equals(bean.getValueCode())){
							LostWarningConstant.dataLimitCount = Integer.parseInt(value);
						}
					}
				}catch(Exception e) {
					
				}
			}
		} catch(Exception e) {
			LOGGER.error("获取配置参数失败", ExceptionUtils.getFullStackTrace(e));
		}
	}
	
	/**
	 * @Description: 加载基础信息
	 * @date 2015-7-24 下午6:59:55   
	 * @author 263072
	 */
	void initBaseInfo(){
		getMotorCadeMap();
		initConstantInfo();
	}
	
	/**
	 * @Description: 初始化BEAN的回调方法,用于还原服务中断的异常数据
	 * @date 2015-7-21 上午10:35:10   
	 * @author 263072 
	 * @throws Exception
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		try{
			LOGGER.info("InitializingBean...");
			lostWarningDataDao.delALLWarningTempData();
		}catch (Exception e) {
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
		}
		
	}
	

}
