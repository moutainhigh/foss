
package com.deppon.foss.module.transfer.load.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillService;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillDto;
import com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDetailDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillQueryService;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillInfoByWaybillNoReultDto;
import com.deppon.foss.module.transfer.load.api.server.service.ICalWaybillQtyService;
import com.deppon.foss.module.transfer.load.api.shared.define.CalWaybillQtyConstants;
import com.deppon.foss.module.transfer.load.api.shared.dto.CalWaybillQtyDto;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.transfer.stock.api.server.dao.IMatchTaskOrgDao;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.util.define.FossConstants;

/** 
 * @className: CalWaybillQtyService
 * @author: zwd 200968 
 * @description: 装车通用service
 * @date: 2015-07-22 下午14:15:11
 * 
 */
public class CalWaybillQtyService implements ICalWaybillQtyService{
	

	
	private static final Logger LOGGER = LogManager.getLogger(CalWaybillQtyService.class);
	// 零担丢货管理组
	private static final String LOSEORGCODE = "W01000301050203";

	private ILdpExternalBillService ldpExternalBillService;
	
	public void setLdpExternalBillService(
			ILdpExternalBillService ldpExternalBillService) {
		this.ldpExternalBillService = ldpExternalBillService;
	}
	/** 
	 * 货件库存DAO
	 * */
	private IStockDao stockDao;
    
	public void setStockDao(IStockDao stockDao) {
		this.stockDao = stockDao;
	}
	/**运单查询service   根据运单号，查询运单详细信息*/
	private IWaybillQueryService waybillQueryService;
	/**库存service     根据运单号，查询库存信息*/
	private IStockService stockService;

	/**匹配任务部门 Dao*/
	private IMatchTaskOrgDao matchTaskOrgDao;
	//结算 服务 接口
	/*private ISignDataOutService signDataOutService;
	
	public void setSignDataOutService(ISignDataOutService signDataOutService) {
		this.signDataOutService = signDataOutService;
	}*/
	/**
	 * 运单签收结果service
	 */
	private IWaybillSignResultService waybillSignResultService;
	
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}
	/** 
	 * 派送单明细DAO接口
	 */
	private IDeliverbillDetailDao deliverbillDetailDao;
	
	public void setDeliverbillDetailDao(IDeliverbillDetailDao deliverbillDetailDao) {
		this.deliverbillDetailDao = deliverbillDetailDao;
	}	
	public void setWaybillQueryService(IWaybillQueryService waybillQueryService) {
		this.waybillQueryService = waybillQueryService;
	}
	
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	public void setMatchTaskOrgDao(IMatchTaskOrgDao matchTaskOrgDao) {
		this.matchTaskOrgDao = matchTaskOrgDao;
	}
/*	//接送货 服务  
	private IWaybillSignService wayBillSignService ;
	
	public void setWayBillSignService(IWaybillSignService wayBillSignService) {
		this.wayBillSignService = wayBillSignService;
	}*/
	/***
	 * 运输中票数:	查询时间范围内，货物状态为运输中票数之和。
	 * 根据接送货传过来的运单号集合,计算出所有状态为运输中的票数 ,分批配载的只要有运单在途,就算在途
	 * 2015-07-22 zwd 200968 
	 * 参数:运单号集合
	 * 返回值:运输中票数之和
	 */
	public CalWaybillQtyDto calWaybillOnQtys(List<String> waybillList){
		int onTheWayQTYTotal = 0 , loseGoodsQTYTotal = 0 ;
			
		//<运单号,运单状态>
    	Map<String, String> map = new HashMap<String, String>();
		for (String waybillNo : waybillList) {
		//获得运单信息
		WaybillInfoByWaybillNoReultDto waybill = waybillQueryService.queryWaybillInfoByWaybillNo(waybillNo);
	    if(waybill == null){
	    	LOGGER.error("运单"+waybillNo+"信息不存在！");
	    }else{
	    	//运单状态
	    	String status = null;	
	    	
	    	List<String> strList = new ArrayList<String>();
	    	strList.add(waybillNo);
	    	//int canlelNum = wayBillSignService.queryWaybillInvalid(strList);//作废票数
	    	/*if( status == null  && canlelNum>0 ){
	    		//作废
	    		status = CalWaybillQtyConstants.CANCELD; 
	    	}*/
	    	/* 
	    	int returnNum = wayBillSignService.queryWaybillBack(strList);
	    	if(status == null && returnNum>0){
	    		//退回
	    		status = CalWaybillQtyConstants.RETURNBACK; 
	    	}*/
	    	
			WaybillSignResultEntity signResultEntity=new WaybillSignResultEntity();
			signResultEntity.setWaybillNo(waybillNo);
			signResultEntity.setActive(FossConstants.ACTIVE);
			// 根据运单号查询运单签结果里的运单信息
			WaybillSignResultEntity newEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(signResultEntity);
			// 根据运单号查询派送单明细(新)
			List<DeliverbillDetailEntity> deliverDetailList = deliverbillDetailDao.queryByWaybillNo(waybillNo);
			if( CollectionUtils.isNotEmpty(deliverDetailList) && deliverDetailList.size()>0 ){
				//派送中-派送单详情有记录的前提下：1、签收结果表为空；2、签收结果表有部分签收记录  
				if(status == null && newEntity==null || !StringUtil.equals("SIGN_STATUS_ALL", newEntity.getSignStatus())){
					//派送中
					status = CalWaybillQtyConstants.DElIVERY_IN;	
				}
			}
	    
			WaybillStockEntity waybillStockEntity = new WaybillStockEntity();
			waybillStockEntity.setWaybillNO(waybillNo);
			//根据运单号查询库存查询当前运单的 库存 信息 
			List<StockEntity> stockList = stockService.queryStockByWaybillNo(waybillStockEntity);
			boolean isSigned = false;
			//根据运单号查询运单是否 全部签收
			isSigned = matchTaskOrgDao.checkWaybillIsSigned(waybillNo);
			//全部签收
			if(status == null  && isSigned){
				//签收
				status = CalWaybillQtyConstants.SIGN;
			}
			
			List<StockEntity> stockEntityList = new ArrayList<StockEntity>();
			stockEntityList = stockDao.queryStockByWO(waybillNo,waybill.getReceiveOrgCode());

			//开单件数等于收货部门的库存件数 = 营业部库存
			if(status == null  && stockEntityList.size() == waybill.getGoodsQtyTotal()){
				//营业部库存
				status = CalWaybillQtyConstants.SALES_INVENTORY;
			}	
			
			//部分签收
			if(!isSigned){
				//没有库存
				 if(CollectionUtils.isNotEmpty(stockList)){
			     for (StockEntity stock : stockList) {
			    	 if(stock.getOrgCode().equals(LOSEORGCODE)){
			    		 loseGoodsQTYTotal+=1;
			    		 //遗失
			    		 if(status == null){
				    		 status = CalWaybillQtyConstants.lOSING; 
			    		 }
			    		 break;
			    	 }
				}			
			  }
			}	
			
			if(status == null){
				//运输中
				status = CalWaybillQtyConstants.TRANSPORTATION; 
				 onTheWayQTYTotal+=1;
			}
			
			map.put(waybillNo, status);
			
		} 
	}
		
		
		
		
		CalWaybillQtyDto  calWaybillQtyDto = new CalWaybillQtyDto();
		//在途件数
		calWaybillQtyDto.setOnTheWayQTYTotal(onTheWayQTYTotal);
		//丢货件数
		calWaybillQtyDto.setLoseGoodsQTYTotal(loseGoodsQTYTotal);
		//运单状态
		calWaybillQtyDto.setMap(map);
		
        return calWaybillQtyDto;
  }
	
	/**
	 * 按照运单号查找外发单号和外发公司  zwd 200968 2016-2-24
	 * @param waybillList
	 * @return
	 */
	public List<LdpExternalBillDto> queryLdpExternalBillNoListByWayBillNos(List<String> waybillList){
		return ldpExternalBillService.queryLdpExternalBillNoList(waybillList);
	}
	
	
}
