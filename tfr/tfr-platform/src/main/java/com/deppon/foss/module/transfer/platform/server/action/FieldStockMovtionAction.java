/**
 * 
 */
package com.deppon.foss.module.transfer.platform.server.action;


import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.server.service.IFieldStockMovtionService;
import com.deppon.foss.module.transfer.platform.api.shared.define.FieldStockMovtionConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.FieldStockMovtionDetailEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.FieldStockMovtionEntity;
import com.deppon.foss.module.transfer.platform.api.shared.vo.FieldStockMovtionVo;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;

/**
 * @author 105795
 *
 */
public class FieldStockMovtionAction extends AbstractAction{

	private static final long serialVersionUID = -2111188823580294797L;

    /**
     * service
     * */
	private IFieldStockMovtionService fieldStockMovtionService;
	
	/**
	 * vo
	 * */
    private FieldStockMovtionVo fieldStockMovtionVo=new FieldStockMovtionVo();
	  

    /** 库存service*/
	private IStockService stockService;

    /**
	 * 导出Excel 文件流
	 */
	private InputStream excelStream;

	/**
	 * 导出Excel 文件名
	 */
	private String downloadFileName;
    
	/**
	 *com.deppon.foss.module.transfer.platform.server.action  
	 *@desc 查询场内库存流动所有情况
	 *@return String
	 *@author 105795
	 *@date 2015年3月3日下午5:23:07 
	 */
	@JSON
	public String queryFieldStockMovtion()
	{
		
		try {
			String origOrgCode=fieldStockMovtionVo.getQueryConditionStockMovtionDto().getOrigOrgCode();
			FieldStockMovtionEntity fieldStockMovtionEntity=fieldStockMovtionService.queryFieldStockMovtion(origOrgCode);
			if(fieldStockMovtionEntity!=null){
				fieldStockMovtionVo.setFieldStockMovtionEntity(fieldStockMovtionEntity);
			}else{
				throw new TfrBusinessException("查询结果为空!");
			}
			
		} catch (TfrBusinessException e) {
			
			return this.returnError(e);
		}
		
		return this.returnSuccess();
		
	}


	/**
	 *com.deppon.foss.module.transfer.platform.server.action  
	 *@desc 根据外场编码找外场名称
	 *@param 
	 *@return String
	 *@author 105795
	 *@date 2015年2月3日下午3:02:20 
	 */
	@JSON
	public String queryTfrNameByCode()
	{
		try {
			
			//外场
			String transferCenterCode=fieldStockMovtionVo.getQueryConditionStockMovtionDto().getOrigOrgCode(); 
			
			Map<String,String> tfrMap=fieldStockMovtionService.queryParentTfrCtrCode(transferCenterCode);
			if(tfrMap==null){
				throw new TfrBusinessException("没有找到顶级外场!");
				 
			}
			
			fieldStockMovtionVo.getQueryConditionStockMovtionDto().setOrigOrgName(tfrMap.get("name"));
			fieldStockMovtionVo.getQueryConditionStockMovtionDto().setOrigOrgCode(tfrMap.get("code"));

		} catch (TfrBusinessException e) {

			return returnError(e);
			
		}
		return returnSuccess();
		
		
	}
	
	/**
	 *com.deppon.foss.module.transfer.platform.server.action  
	 *@desc 根据当前位置查询库存流动明细
	 *@param void
	 *@return String
	 *@author 105795
	 *@date 2015年3月5日上午9:07:25 
	 */
	@JSON
	public String queryFieldStockMovtionDetail()
	{
		
		try {
		  String collIndex=fieldStockMovtionVo.getQueryConditionStockMovtionDto().getColIndex();	
		  int location=0;
		  String locationName="";
		  if(StringUtil.isNotEmpty(collIndex))
		  {
			 location=Integer.parseInt(collIndex);
		  }
		  if("All".equalsIgnoreCase(fieldStockMovtionVo.getQueryConditionStockMovtionDto().getProductCode()))
		  {
			  fieldStockMovtionVo.getQueryConditionStockMovtionDto().setProductCode(null);
		  }
		  if(location<0)
		  {
			  throw new TfrBusinessException("请选择当前所处的位置");
		  }	
		  List<FieldStockMovtionDetailEntity> resultList=null;
		  Long totalCount=0l;
		 // String isAll="N";
		  //根据当前位置找对应的service
		  switch (location) {
			 case FieldStockMovtionConstants.MOVTION_STOCK_ARRIVED_UNLOAD://
				 resultList=fieldStockMovtionService.queryArrivedUnload(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
				 totalCount=(long) (fieldStockMovtionService.queryArrivedUnload(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), 0, 0).size());
				 locationName="到达未卸";
				 if(com.deppon.foss.util.CollectionUtils.isNotEmpty(resultList)&&resultList.size()>0){
					 for(FieldStockMovtionDetailEntity detail:resultList){
						 detail.setCurrentLocaton(locationName);
					 }
				 }
				 break;
	
			 case FieldStockMovtionConstants.MOVTION_STOCK_UNLOADING: 
				 resultList=fieldStockMovtionService.queryUnLoading(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
				 totalCount=(long) (fieldStockMovtionService.queryUnLoading(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), 0, 0).size());
				 locationName="卸车中";
				 if(com.deppon.foss.util.CollectionUtils.isNotEmpty(resultList)&&resultList.size()>0){
					 for(FieldStockMovtionDetailEntity detail:resultList){
						 detail.setCurrentLocaton(locationName);
					 }
				 }
				 break;				 
				 
			 case FieldStockMovtionConstants.MOVTION_STOCK_TRAY:
				 resultList=fieldStockMovtionService.queryTray(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
				 totalCount=(long) (fieldStockMovtionService.queryTray(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), 0, 0).size());
				 locationName="待叉区库存";
				 if(com.deppon.foss.util.CollectionUtils.isNotEmpty(resultList)&&resultList.size()>0){
					 for(FieldStockMovtionDetailEntity detail:resultList){
						 detail.setCurrentLocaton(locationName);
					 }
				 }
				 break;	
			 case FieldStockMovtionConstants.MOVTION_STOCK_PACKAGING:
				 resultList=fieldStockMovtionService.queryPackaging(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
				 totalCount=(long) (fieldStockMovtionService.queryPackaging(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), 0, 0).size());
				 locationName="包装库区库存";
				 if(com.deppon.foss.util.CollectionUtils.isNotEmpty(resultList)&&resultList.size()>0){
					 for(FieldStockMovtionDetailEntity detail:resultList){
						 detail.setCurrentLocaton(locationName);
					 }
				 }
				 break;	
			 case FieldStockMovtionConstants.MOVTION_STOCK_TFR:
				 resultList=fieldStockMovtionService.queryTfrStock(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
				 totalCount=(long) (fieldStockMovtionService.queryTfrStock(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), 0, 0).size());
				 locationName="零担中转库区库存";
				 if(com.deppon.foss.util.CollectionUtils.isNotEmpty(resultList)&&resultList.size()>0){
					 for(FieldStockMovtionDetailEntity detail:resultList){
						 detail.setCurrentLocaton(locationName);
					 }
				 }
				 break;	
			 case FieldStockMovtionConstants.MOVTION_STOCK_DELIVER:
				 resultList=fieldStockMovtionService.queryTfrPickupStock(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
				 totalCount=(long) (fieldStockMovtionService.queryTfrPickupStock(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), 0, 0).size());
				 locationName="零担派送库区库存";
				 if(com.deppon.foss.util.CollectionUtils.isNotEmpty(resultList)&&resultList.size()>0){
					 for(FieldStockMovtionDetailEntity detail:resultList){
						 detail.setCurrentLocaton(locationName);
					 }
				 }
				 break;	
			 case FieldStockMovtionConstants.MOVTION_STOCK_TFR_EXPRESS:
				 resultList=fieldStockMovtionService.queryRcpStock(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
				 totalCount=(long) (fieldStockMovtionService.queryRcpStock(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), 0, 0).size());
				 locationName="快递中转库区库存";
				 if(com.deppon.foss.util.CollectionUtils.isNotEmpty(resultList)&&resultList.size()>0){
					 for(FieldStockMovtionDetailEntity detail:resultList){
						 detail.setCurrentLocaton(locationName);
					 }
				 }
				 break;	
			 case FieldStockMovtionConstants.MOVTION_STOCK_EXPRESS_DELIVER:
				 resultList=fieldStockMovtionService.queryRcpPickupStock(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
				 totalCount=(long) (fieldStockMovtionService.queryRcpPickupStock(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), 0, 0).size());
				 locationName="快递派送库区库存";
				 if(com.deppon.foss.util.CollectionUtils.isNotEmpty(resultList)&&resultList.size()>0){
					 for(FieldStockMovtionDetailEntity detail:resultList){
						 detail.setCurrentLocaton(locationName);
					 }
				 }
				 break;	
			 case FieldStockMovtionConstants.MOVTION_STOCK_LOADED:
				 resultList=fieldStockMovtionService.queryLoading(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
				 totalCount=(long) (fieldStockMovtionService.queryLoading(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), 0, 0).size());
				 locationName="已装车";
				 if(com.deppon.foss.util.CollectionUtils.isNotEmpty(resultList)&&resultList.size()>0){
					 for(FieldStockMovtionDetailEntity detail:resultList){
						 detail.setCurrentLocaton(locationName);
					 }
				 }
				 break;	 
			default:
				/**查询全部*/
				// isAll="Y";
				//先判断运单号是否为空，如果运单号为空，不让查询全部的库存位置
				 String waybillNo= fieldStockMovtionVo.getQueryConditionStockMovtionDto().getWaybillNo();
				 if(StringUtil.isEmpty(waybillNo)){
					 throw new TfrBusinessException("运单为空，不能查询场内库存的全部位置！"); 
				 }
				 resultList=new ArrayList<FieldStockMovtionDetailEntity>();
				//到达未卸
				 List<FieldStockMovtionDetailEntity> resultListA=fieldStockMovtionService.queryArrivedUnload(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
				 if(!CollectionUtils.isEmpty(resultListA)&&resultListA.size()>0){
					 for(FieldStockMovtionDetailEntity detail:resultListA){
						 detail.setCurrentLocaton("到达未卸");
					 }
					 resultList.addAll(resultListA);
				 }
				 
				 //卸车中
				 List<FieldStockMovtionDetailEntity> resultListB=fieldStockMovtionService.queryUnLoading(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
				 if(!CollectionUtils.isEmpty(resultListB)&&resultListB.size()>0){
					 for(FieldStockMovtionDetailEntity detail:resultListB){
						 detail.setCurrentLocaton("卸车中");
					 }
					 resultList.addAll(resultListB);
				 }
				 //待叉区库存
				 List<FieldStockMovtionDetailEntity> resultListC=fieldStockMovtionService.queryTray(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
				 if(!CollectionUtils.isEmpty(resultListC)&&resultListC.size()>0){
					 for(FieldStockMovtionDetailEntity detail:resultListC){
						 detail.setCurrentLocaton("待叉区库存");
					 }
					 resultList.addAll(resultListC);
				 }
				 //包装库区库存
				 List<FieldStockMovtionDetailEntity> resultListD=fieldStockMovtionService.queryPackaging(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
				 if(!CollectionUtils.isEmpty(resultListD)&&resultListD.size()>0){
					 for(FieldStockMovtionDetailEntity detail:resultListD){
						 detail.setCurrentLocaton("包装库区库存");
					 }
					 resultList.addAll(resultListD);
				 }
				 //零担中转库区库存
				 List<FieldStockMovtionDetailEntity> resultListE=fieldStockMovtionService.queryTfrStock(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
				 if(!CollectionUtils.isEmpty(resultListE)&&resultListE.size()>0){
					 for(FieldStockMovtionDetailEntity detail:resultListE){
						 detail.setCurrentLocaton("零担中转库区库存");
					 }
					 resultList.addAll(resultListE);
				 }
				 //零担派送库区库存
				 List<FieldStockMovtionDetailEntity> resultListF=fieldStockMovtionService.queryTfrPickupStock(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
				 if(!CollectionUtils.isEmpty(resultListF)&&resultListF.size()>0){
					 for(FieldStockMovtionDetailEntity detail:resultListF){
						 detail.setCurrentLocaton("零担派送库区库存");
					 }
					 resultList.addAll(resultListF);
				 }
				 //快递中转库区库存
				 List<FieldStockMovtionDetailEntity> resultListG=fieldStockMovtionService.queryRcpStock(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
				 if(!CollectionUtils.isEmpty(resultListG)&&resultListG.size()>0){
					 for(FieldStockMovtionDetailEntity detail:resultListG){
						 detail.setCurrentLocaton("快递中转库区库存");
					 }
					 resultList.addAll(resultListG);
				 }
				 //快递派送库区库存
				 List<FieldStockMovtionDetailEntity> resultListH=fieldStockMovtionService.queryRcpPickupStock(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
				 if(!CollectionUtils.isEmpty(resultListH)&&resultListH.size()>0){
					 for(FieldStockMovtionDetailEntity detail:resultListH){
						 detail.setCurrentLocaton("快递派送库区库存");
					 }
					 resultList.addAll(resultListH);
				 }
				 //已装车
				 List<FieldStockMovtionDetailEntity> resultListI=fieldStockMovtionService.queryLoading(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
				 if(!CollectionUtils.isEmpty(resultListI)&&resultListI.size()>0){
					 for(FieldStockMovtionDetailEntity detail:resultListI){
						 detail.setCurrentLocaton("已装车");
					 }
					 resultList.addAll(resultListH);
					 resultList.addAll(resultListI);
				 }
				 totalCount=(long)resultList.size();
				 break;	
			}
		  
		  //处理放回结果
		  if(!CollectionUtils.isEmpty(resultList)&&resultList.size()>0)
		  {
			  //去拿外场
			  for(FieldStockMovtionDetailEntity fieldStockMovtionDetailEntity:resultList)
			  {
				  fieldStockMovtionDetailEntity.setTransferCenterName(fieldStockMovtionVo.getQueryConditionStockMovtionDto().getOrigOrgName());
				  /*if("N".equals(isAll)){
					  fieldStockMovtionDetailEntity.setCurrentLocaton(locationName);
				  }*/
			  }
			  fieldStockMovtionVo.setFieldStockMovtionDetailList(resultList);
			  super.setTotalCount(totalCount);

			  
		  }else{
			  throw new TfrBusinessException("查询结果为空");
		  }
		  
			
			
			
		} catch (TfrBusinessException e) {

			return returnError(e);
			
		}
	
		return returnSuccess();
	}

	/**
	 *com.deppon.foss.module.transfer.platform.server.action  
	 *@desc 获取产品list
	 *@return String
	 *@author 105795
	 *@date 2015年3月5日下午4:30:14 
	 */
	@JSON
	public String queryProductList(){
		//设置产品List
		fieldStockMovtionVo.setProductList(stockService.queryProductList());
		return returnSuccess();
	}
	
	/**
	 *com.deppon.foss.module.transfer.platform.server.action  
	 *@desc 导出库存流动明细
	 *@param 
	 *@return String
	 *@author 105795
	 *@date 2015年3月5日下午5:03:29 
	 */
	public String exportFieldStockMovtionDetail(){
		
    	  try {
    		  String collIndex=fieldStockMovtionVo.getQueryConditionStockMovtionDto().getColIndex();	
    		  int location=0;
    		  String locationName="";
    		  if(StringUtil.isNotEmpty(collIndex))
    		  {
    			 location=Integer.parseInt(collIndex);
    		  }
    		  if("All".equalsIgnoreCase(fieldStockMovtionVo.getQueryConditionStockMovtionDto().getProductCode()))
    		  {
    			  fieldStockMovtionVo.getQueryConditionStockMovtionDto().setProductCode(null);
    		  }
    		  if(location<0)
    		  {
    			  throw new TfrBusinessException("请选择当前所处的位置");
    		  }	
    		  List<FieldStockMovtionDetailEntity> resultList=null;
    		 // Long totalCount=0l;
    		 // String isAll="N";

    		  //根据当前位置找对应的service
    		  switch (location) {
    			 case FieldStockMovtionConstants.MOVTION_STOCK_ARRIVED_UNLOAD://
    				 resultList=fieldStockMovtionService.queryArrivedUnload(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), 0,0);	 
    				 locationName="到达未卸";
    				 if(com.deppon.foss.util.CollectionUtils.isNotEmpty(resultList)&&resultList.size()>0){
    					 for(FieldStockMovtionDetailEntity detail:resultList){
    						 detail.setCurrentLocaton(locationName);
    					 }
    				 }
    				 break;
    	
    			 case FieldStockMovtionConstants.MOVTION_STOCK_UNLOADING: 
    				 resultList=fieldStockMovtionService.queryUnLoading(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), 0,0);	 
    				 locationName="卸车中";
    				 if(com.deppon.foss.util.CollectionUtils.isNotEmpty(resultList)&&resultList.size()>0){
    					 for(FieldStockMovtionDetailEntity detail:resultList){
    						 detail.setCurrentLocaton(locationName);
    					 }
    				 }
    				 break;				 
    				 
    			 case FieldStockMovtionConstants.MOVTION_STOCK_TRAY:
    				 resultList=fieldStockMovtionService.queryTray(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), 0,0);	 
    				 locationName="待叉区库存";
    				 if(com.deppon.foss.util.CollectionUtils.isNotEmpty(resultList)&&resultList.size()>0){
    					 for(FieldStockMovtionDetailEntity detail:resultList){
    						 detail.setCurrentLocaton(locationName);
    					 }
    				 }
    				 break;	
    			 case FieldStockMovtionConstants.MOVTION_STOCK_PACKAGING:
    				 resultList=fieldStockMovtionService.queryPackaging(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), 0,0);	 
    				 locationName="包装库区库存";
    				 if(com.deppon.foss.util.CollectionUtils.isNotEmpty(resultList)&&resultList.size()>0){
    					 for(FieldStockMovtionDetailEntity detail:resultList){
    						 detail.setCurrentLocaton(locationName);
    					 }
    				 }
    				 break;	
    			 case FieldStockMovtionConstants.MOVTION_STOCK_TFR:
    				 resultList=fieldStockMovtionService.queryTfrStock(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), 0,0);	 
    				 locationName="零担中转库区库存";
    				 if(com.deppon.foss.util.CollectionUtils.isNotEmpty(resultList)&&resultList.size()>0){
    					 for(FieldStockMovtionDetailEntity detail:resultList){
    						 detail.setCurrentLocaton(locationName);
    					 }
    				 }
    				 break;	
    			 case FieldStockMovtionConstants.MOVTION_STOCK_DELIVER:
    				 resultList=fieldStockMovtionService.queryTfrPickupStock(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), 0,0);	 
    				 locationName="零担派送库区库存";
    				 if(com.deppon.foss.util.CollectionUtils.isNotEmpty(resultList)&&resultList.size()>0){
    					 for(FieldStockMovtionDetailEntity detail:resultList){
    						 detail.setCurrentLocaton(locationName);
    					 }
    				 }
    				 break;	
    			 case FieldStockMovtionConstants.MOVTION_STOCK_TFR_EXPRESS:
    				 resultList=fieldStockMovtionService.queryRcpStock(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), 0,0);	 
    				 locationName="快递中转库区库存";
    				 if(com.deppon.foss.util.CollectionUtils.isNotEmpty(resultList)&&resultList.size()>0){
    					 for(FieldStockMovtionDetailEntity detail:resultList){
    						 detail.setCurrentLocaton(locationName);
    					 }
    				 }
    				 break;	
    			 case FieldStockMovtionConstants.MOVTION_STOCK_EXPRESS_DELIVER:
    				 resultList=fieldStockMovtionService.queryRcpPickupStock(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), 0, 0);	 
    				 locationName="快递派送库区库存";
    				 if(com.deppon.foss.util.CollectionUtils.isNotEmpty(resultList)&&resultList.size()>0){
    					 for(FieldStockMovtionDetailEntity detail:resultList){
    						 detail.setCurrentLocaton(locationName);
    					 }
    				 }
    				 break;	
    			 case FieldStockMovtionConstants.MOVTION_STOCK_LOADED:
    				 resultList=fieldStockMovtionService.queryLoading(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), 0, 0);	 
    				 totalCount=(long) (fieldStockMovtionService.queryLoading(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), 0, 0).size());
    				 locationName="已装车";
    				 if(com.deppon.foss.util.CollectionUtils.isNotEmpty(resultList)&&resultList.size()>0){
    					 for(FieldStockMovtionDetailEntity detail:resultList){
    						 detail.setCurrentLocaton(locationName);
    					 }
    				 }
    				 break;	 
    			default:				
    				/**查询全部*/
   				 //isAll="Y";
   				//先判断运单号是否为空，如果运单号为空，不让查询全部的库存位置
   				 String waybillNo= fieldStockMovtionVo.getQueryConditionStockMovtionDto().getWaybillNo();
   				 if(StringUtil.isEmpty(waybillNo)){
   					 throw new TfrBusinessException("运单为空，不能查询场内库存的全部位置！"); 
   				 }
   				 resultList=new ArrayList<FieldStockMovtionDetailEntity>();
   				//到达未卸
   				 List<FieldStockMovtionDetailEntity> resultListA=fieldStockMovtionService.queryArrivedUnload(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
   				 if(!CollectionUtils.isEmpty(resultListA)&&resultListA.size()>0){
   					 for(FieldStockMovtionDetailEntity detail:resultListA){
   						 detail.setCurrentLocaton("到达未卸");
   					 }
   					 resultList.addAll(resultListA);
   				 }
   				 
   				 //卸车中
   				 List<FieldStockMovtionDetailEntity> resultListB=fieldStockMovtionService.queryUnLoading(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
   				 if(!CollectionUtils.isEmpty(resultListB)&&resultListB.size()>0){
   					 for(FieldStockMovtionDetailEntity detail:resultListB){
   						 detail.setCurrentLocaton("卸车中");
   					 }
   					 resultList.addAll(resultListB);
   				 }
   				 //待叉区库存
   				 List<FieldStockMovtionDetailEntity> resultListC=fieldStockMovtionService.queryTray(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
   				 if(!CollectionUtils.isEmpty(resultListC)&&resultListC.size()>0){
   					 for(FieldStockMovtionDetailEntity detail:resultListC){
   						 detail.setCurrentLocaton("待叉区库存");
   					 }
   					 resultList.addAll(resultListC);
   				 }
   				 //包装库区库存
   				 List<FieldStockMovtionDetailEntity> resultListD=fieldStockMovtionService.queryPackaging(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
   				 if(!CollectionUtils.isEmpty(resultListD)&&resultListD.size()>0){
   					 for(FieldStockMovtionDetailEntity detail:resultListD){
   						 detail.setCurrentLocaton("包装库区库存");
   					 }
   					 resultList.addAll(resultListD);
   				 }
   				 //零担中转库区库存
   				 List<FieldStockMovtionDetailEntity> resultListE=fieldStockMovtionService.queryTfrStock(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
   				 if(!CollectionUtils.isEmpty(resultListE)&&resultListE.size()>0){
   					 for(FieldStockMovtionDetailEntity detail:resultListE){
   						 detail.setCurrentLocaton("零担中转库区库存");
   					 }
   					 resultList.addAll(resultListE);
   				 }
   				 //零担派送库区库存
   				 List<FieldStockMovtionDetailEntity> resultListF=fieldStockMovtionService.queryTfrPickupStock(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
   				 if(!CollectionUtils.isEmpty(resultListF)&&resultListF.size()>0){
   					 for(FieldStockMovtionDetailEntity detail:resultListF){
   						 detail.setCurrentLocaton("零担派送库区库存");
   					 }
   					 resultList.addAll(resultListF);
   				 }
   				 //快递中转库区库存
   				 List<FieldStockMovtionDetailEntity> resultListG=fieldStockMovtionService.queryRcpStock(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
   				 if(!CollectionUtils.isEmpty(resultListG)&&resultListG.size()>0){
   					 for(FieldStockMovtionDetailEntity detail:resultListG){
   						 detail.setCurrentLocaton("快递中转库区库存");
   					 }
   					 resultList.addAll(resultListG);
   				 }
   				 //快递派送库区库存
   				 List<FieldStockMovtionDetailEntity> resultListH=fieldStockMovtionService.queryRcpPickupStock(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
   				 if(!CollectionUtils.isEmpty(resultListH)&&resultListH.size()>0){
   					 for(FieldStockMovtionDetailEntity detail:resultListH){
   						 detail.setCurrentLocaton("快递派送库区库存");
   					 }
   					 resultList.addAll(resultListH);
   				 }
   				 //已装车
   				 List<FieldStockMovtionDetailEntity> resultListI=fieldStockMovtionService.queryLoading(fieldStockMovtionVo.getQueryConditionStockMovtionDto(), this.start, this.limit);	 
   				 if(!CollectionUtils.isEmpty(resultListI)&&resultListI.size()>0){
   					 for(FieldStockMovtionDetailEntity detail:resultListI){
   						 detail.setCurrentLocaton("已装车");
   					 }
   					 resultList.addAll(resultListH);
   					 resultList.addAll(resultListI);
   				 }
    				 break;	
    			}
    		  
			
			  //处理放回结果
			  if(!CollectionUtils.isEmpty(resultList)&&resultList.size()>0)
			  {
				  //去拿外场
				  for(FieldStockMovtionDetailEntity fieldStockMovtionDetailEntity:resultList)
				  {
					  fieldStockMovtionDetailEntity.setTransferCenterName(fieldStockMovtionVo.getQueryConditionStockMovtionDto().getOrigOrgName());
					  /*if("N".equals(isAll)){
						  fieldStockMovtionDetailEntity.setCurrentLocaton(locationName);
					  }			*/	 
				   }
				  fieldStockMovtionVo.setFieldStockMovtionDetailList(resultList);

				  
			  }else{
				  throw new TfrBusinessException("查询结果为空");
			  }
			  try {
				  downloadFileName = encodeFileName(FieldStockMovtionConstants.MOVTION_STOCK_DETAIL_EXCEL_NAME);
			  } catch (UnsupportedEncodingException e) {
				  throw new TfrBusinessException("编码转换格式异常");
			  }

		    ExportResource exportResource =handlerExport(resultList);
			ExportSetting exportSetting = new ExportSetting();
			// 设置名称
			exportSetting.setSheetName(FieldStockMovtionConstants.MOVTION_STOCK_DETAIL_EXCEL_NAME);
			ExporterExecutor objExporterExecutor = new ExporterExecutor();
			// 导出成文件
			excelStream = objExporterExecutor.exportSync(exportResource,exportSetting);
			
			
			
		} catch (TfrBusinessException e) {
			
			return this.returnError(e);
	  }
      return this.returnSuccess();
	}	
	
	
	//导出文件头编码
	private String encodeFileName(String name)
			throws UnsupportedEncodingException {
		String result;
		String agent = (String) ServletActionContext.getRequest().getHeader(
				"USER-AGENT");
		if (agent != null && agent.indexOf("MSIE") == -1) {
			result = new String(name.getBytes("UTF-8"), "iso-8859-1");
		} else {
			result = URLEncoder.encode(name, "UTF-8");
		}
		return result;
	}
	
	
	/**
	 * 处理导出文件
	 * */
	ExportResource handlerExport(List<FieldStockMovtionDetailEntity> fieldStockMovtionDetailList){

		List<List<String>> rowList = new ArrayList<List<String>>();
		List<String> result = null;

		for (FieldStockMovtionDetailEntity entity : fieldStockMovtionDetailList) {
			result = new ArrayList<String>();

			// 转运场
			result.add(entity.getTransferCenterName() != null ? entity.getTransferCenterName() : null);
			// 运单号
			result.add(entity.getWaybillNo()!= null ? entity.getWaybillNo() : null);
			// 当前位置
			result.add(entity.getCurrentLocaton()!=null?entity.getCurrentLocaton():null);
			// 运输性质
			result.add(entity.getTransportType() != null ? entity.getTransportType() : null);
			// 提货方式
			result.add(entity.getPickupMethod() !=null?entity.getPickupMethod():null);
			// 车牌号
			result.add(entity.getVehicleNo() != null ? entity.getVehicleNo() : null);
			// 上一线路
			result.add(entity.getLastOrgName()!=null?entity.getLastOrgName():null);
			// 下一线路
			result.add(entity.getNextOrgName() != null ? entity.getNextOrgName() : null);

			// 开单件数
			result.add(entity.getGoodsQtyTotal() + "");
			// 开单重量(KG)
			result.add(entity.getGoodsWeightTotal()+"");
			// 当前件数
			result.add(entity.getCurrentGoodsQty() + "");
			// 当前重量(KG)
			result.add(entity.getCurrentWeight()+"");
			// 当前体积(立方)
			result.add(entity.getCurrentVolume() + "");
			
			rowList.add(result);
		}
		ExportResource sheet = new ExportResource();
		sheet.setHeads(FieldStockMovtionConstants.MOVTION_STOCK_DETAIL_EXCEL_TITLE);
		sheet.setRowList(rowList);
		return sheet;

	}
	
	
	
	
	/**
	 * @param fieldStockMovtionService the fieldStockMovtionService to set
	 */
	public void setFieldStockMovtionService(
			IFieldStockMovtionService fieldStockMovtionService) {
		this.fieldStockMovtionService = fieldStockMovtionService;
	}



	/**
	 * @return the fieldStockMovtionVo
	 */
	public FieldStockMovtionVo getFieldStockMovtionVo() {
		return fieldStockMovtionVo;
	}



	/**
	 * @param fieldStockMovtionVo the fieldStockMovtionVo to set
	 */
	public void setFieldStockMovtionVo(FieldStockMovtionVo fieldStockMovtionVo) {
		this.fieldStockMovtionVo = fieldStockMovtionVo;
	}


	/**
	 * @return the excelStream
	 */
	public InputStream getExcelStream() {
		return excelStream;
	}


	/**
	 * @param excelStream the excelStream to set
	 */
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}


	/**
	 * @return the downloadFileName
	 */
	public String getDownloadFileName() {
		return downloadFileName;
	}


	/**
	 * @param downloadFileName the downloadFileName to set
	 */
	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}


	/**
	 * @param stockService the stockService to set
	 */
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}


	
	
	
}
