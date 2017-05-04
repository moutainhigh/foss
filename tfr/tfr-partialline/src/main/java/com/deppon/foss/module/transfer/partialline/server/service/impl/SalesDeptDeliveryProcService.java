package com.deppon.foss.module.transfer.partialline.server.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.InstallationEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillMessageEntity;
import com.deppon.foss.module.transfer.partialline.api.server.dao.ISalesDeptDeliveryProcDao;
import com.deppon.foss.module.transfer.partialline.api.server.service.ISalesdeptDeliveryprocService;
import com.deppon.foss.module.transfer.partialline.api.shared.define.RestFullHeaderFactory;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.SalesdeptDeliveryEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.SupplierAndOrderNo;
import com.deppon.foss.module.transfer.partialline.api.shared.exception.SalesdeptDeliveryprocException;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.WaybillInfoList;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 
 * @author 130134
 *
 */
public class SalesDeptDeliveryProcService implements
		ISalesdeptDeliveryprocService {
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LogManager
			.getLogger(SalesDeptDeliveryProcService.class);
	/**
	 * 
	 */                               
	private ISalesDeptDeliveryProcDao salesDeptDeliveryProcDao;

	/**
	 * 结算签收情况验证
	 */
	private IWaybillSignResultService waybillSignResultService;
	/**
	 * 接送货接口
	 */
	private IWaybillManagerService waybillManagerService; 
	
	/**
	 * 
	 */
	private  IDataDictionaryService dataDictionaryService;
	
	@Value(value="${dop.querySuppliers.address}")
	private String address;
	
	/**
	* @Title: setDataDictionaryService 
	* @Description: 
	* @param @param dataDictionaryService    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void setDataDictionaryService(
			IDataDictionaryService dataDictionaryService) {
		this.dataDictionaryService = dataDictionaryService;
	}
	
	/**
	 * 
	 * @param waybillManagerService
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	/**
	 * 
	 * @param waybillSignResultService
	 */
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}
	
	/**
	 * 
	 * @param salesDeptDeliveryProcDao
	 */
	public void setSalesDeptDeliveryProcDao(
			ISalesDeptDeliveryProcDao salesDeptDeliveryProcDao) {
		this.salesDeptDeliveryProcDao = salesDeptDeliveryProcDao;
	}
 
	/**
	 * 根据运单号查询
	 */
	@Override
	public List<SalesdeptDeliveryEntity> salesDeptDeliveryQuery(
			String waynoStr, String status) {
		if(StringUtils.isEmpty(waynoStr)){
			throw new SalesdeptDeliveryprocException("输入的单号不能为空！","输入的单号不能为空！");
		}
		String[] codes=waynoStr.split("\n");
		
		List<String> codeList1=Arrays.asList(codes);
		Set<String> codeSet=new HashSet<String>();
		codeSet.addAll(codeList1);
		List<String> codeList=new ArrayList<String>();
		codeList.addAll(codeSet);
		if(codeList.size()>ConstantsNumberSonar.SONAR_NUMBER_5||codeList.size()==0){
			throw new SalesdeptDeliveryprocException("最多只能输入5个单号！","最多只能输入5个单号！");
		}
		 
		SalesdeptDeliveryEntity salesdeptDeliveryEntity=new SalesdeptDeliveryEntity();
		salesdeptDeliveryEntity.setWayNos(codes);
//		salesdeptDeliveryEntity.setWaystaus(status);
		salesdeptDeliveryEntity.setOrgCode(FossUserContext.getCurrentDeptCode());
		List<SalesdeptDeliveryEntity> salesDeptDeliveryProcs = 
				salesDeptDeliveryProcDao.salesDeptDeliveryQuery(salesdeptDeliveryEntity);
		//当状态为已确认是仅仅只查询本地已交货的数据
		if(StringUtils.equals(status, "DE_CONFIRM")||salesDeptDeliveryProcs.size()==codes.length){
			return salesDeptDeliveryProcs;
		}
		//
		
		if(StringUtils.isEmpty(status)){
		     //当状态为空分为两步：本地有已交货的数据和未交货的数据
		     this.convert(codeList,salesDeptDeliveryProcs,true);	
		}
		//未确认状态查询时
		if(StringUtils.equals(status, "NON_DE_CONFIRM")){
			 this.convert(codeList,salesDeptDeliveryProcs,false);
		}
		return salesDeptDeliveryProcs;
	}

	/**
	 * 
	* @Title: convert 
	* @Description: 根据运单号调用周边系统进行运单信息封装
	* @param @param codeList
	* @param @param salesDeptDeliveryProcs
	* @param @param flag    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void convert(List<String> codes,List<SalesdeptDeliveryEntity> salesDeptDeliveryProcs,boolean flag){
		
		
		List<String> codeList=new ArrayList<String>();
		for(String code:codes){
			codeList.add(code);
		}
		if(!CollectionUtils.isEmpty(salesDeptDeliveryProcs)){
			for(SalesdeptDeliveryEntity entity:salesDeptDeliveryProcs){
				codeList.remove(entity.getWayBillNo());
			}
		} 
		//
		if(CollectionUtils.isEmpty(codeList)){
			return;
		}
		//数组转换
		String[] codeArray=(String[]) codeList.toArray(new String[codeList.size()]);
		
		//接送货
		Map<String, WaybillMessageEntity> actualFreightMap=
				waybillManagerService.getWaybillEntityMessage(codeArray);
		// 当接送货无返回值时候无需进行下面操作
		if(null==actualFreightMap||actualFreightMap.isEmpty()){
			return;
		}
		/*//中转
		Map<String,Integer> stocks= stockService.
				querySumStockGoodsQtyByWaybillsOrgCode(codeArray, FossUserContext.getCurrentDeptCode());*/
		//当状态为未确定运单时
		if(!flag){
			salesDeptDeliveryProcs.clear();
		}
		//调用DOP接口
		Map<String, SupplierAndOrderNo> supplierAndOrderNoMap =null;
		
		try {	
			supplierAndOrderNoMap=this.getSupplierInfoByWayBillNo(codeList);
		} catch (HttpException e) {
			LOGGER.info(e.getMessage());
			LOGGER.info("DOP接口调用失败!-------------->HttpException");
			// throw new SalesdeptDeliveryprocException("DOP接口调用失败!","DOP接口调用失败!");
			} catch (IOException e) {
			LOGGER.info(e.getMessage());
			LOGGER.info("DOP接口调用失败!-------------->IOException");
			// throw new SalesdeptDeliveryprocException("DOP接口调用失败!","DOP接口调用失败!");
			}catch(Exception e){
			LOGGER.info(e.getMessage());
			LOGGER.info("DOP接口调用失败!-------------->Exception");
		}
		//DOP接口查询
		for(String code:codeList){
			SalesdeptDeliveryEntity convertEntity=new SalesdeptDeliveryEntity();
			WaybillMessageEntity waybillMessageEntity=actualFreightMap.get(code);
			if(null==waybillMessageEntity){
				continue;
			}
			
			//如果不是当前登录人部门不是到达部门的运单无需处理
			if(!StringUtils.equals(waybillMessageEntity.getReceiveOrgCode(),
					FossUserContext.getCurrentDeptCode())){
				continue;
			}
			
			//如果没有特殊增值服务的运单不需要进行交货确认
			if(StringUtils.isEmpty(waybillMessageEntity.getSpecialValueAddedServiceType())){
				continue;
			}
			if(StringUtils.isEmpty(code)){
				continue;
			}
			//运单号
			convertEntity.setWayBillNo(code);
			//交货类型
 			convertEntity.setWaystaus("NON_DE_CONFIRM");
			//总件数
			Integer goodsQtyTotal=waybillMessageEntity.getGoodsQtyTotal();
			if(null!=goodsQtyTotal){
				convertEntity.setBilling(Long.valueOf(goodsQtyTotal));
			}
			//提货方式
			convertEntity.setDeliveryMethod(waybillMessageEntity.getReceiveMethod());
			//货物名称
			convertEntity.setGoodsName(waybillMessageEntity.getGoodsName());
			//总量
			BigDecimal weight=waybillMessageEntity.getGoodsWeightTotal();
			if(null!=weight){
				convertEntity.setWeight(weight.toString());
			}
			//体积
			BigDecimal volume=waybillMessageEntity.getGoodsVolumeTotal();
			if(null!=volume){
				convertEntity.setVolume(volume.toString());
			}
			//安装明细
			StringBuffer installationInfo=new StringBuffer();
			List<InstallationEntity> installations=waybillMessageEntity.getInstallList();
			if(CollectionUtils.isNotEmpty(installations)){
				for(InstallationEntity entity:installations){
					if(StringUtils.isNotEmpty(entity.getInstallationName())&&
							StringUtils.isNotEmpty(entity.getInstallPackages())){
						installationInfo.append(entity.getInstallationName()+"*"+entity.getInstallPackages());
					}
				}
			}
			//安装明细
			convertEntity.setGoodsDetails(installationInfo.toString());
			//收货部门
			convertEntity.setOrgCode(waybillMessageEntity.getReceiveOrgCode());
//			convertEntity.setConfirmationTime("交货确认时间");
//			convertEntity.setIsbroken("是否有损坏");
//			convertEntity.setBrokennote("损坏备注");
//			convertEntity.setGoodsDetails("货物明细");
//			convertEntity.setPicPersoncard("提货人身份证号");
			//中转set
			/*if(!stocks.isEmpty()){
				Integer stock= stocks.get(code);
				if(null!=stock){
					convertEntity.setStock(Long.valueOf(stock));
				}
			}*/
			//DOP set
			if(MapUtils.isNotEmpty(supplierAndOrderNoMap)){
				SupplierAndOrderNo supplierAndOrderNo=supplierAndOrderNoMap.get(code);
				if(null!=supplierAndOrderNo){
					convertEntity.setSupplierOrder(supplierAndOrderNo.getOrderNo());
					convertEntity.setSuppliers(supplierAndOrderNo.getSupplierName());
				}
			}
			salesDeptDeliveryProcs.add(convertEntity);
		}
	}
	
	/**
	 * @throws IOException 
	 * @throws HttpException 
	 * 
	* @Title: getSupplierInfoByWayBillNo 
	* @Description: 根据运单号获取供应商信息
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String, SupplierAndOrderNo> getSupplierInfoByWayBillNo(List<String> codeList) throws HttpException, IOException{
		HttpClient httpClient=new HttpClient();
		httpClient.getParams().setContentCharset("UTF-8");	
		PostMethod postMethod =null;
		RestFullHeaderFactory factory=new RestFullHeaderFactory();
	    postMethod =factory.getPostMethod(address, "ESB_FOSS2ESB_CHECK_INFO_ORDERNUMBER2DOP");
	    
	    WaybillInfoList list =new WaybillInfoList();
		list.setWaybillInfos(codeList);
	    
	    String codeListString=JSON.toJSONString(list);
	    RequestEntity entity = new StringRequestEntity(codeListString,"application/json","UTF-8");
		postMethod.setRequestEntity(entity);
		int statusCode = httpClient.executeMethod(postMethod);
		if (statusCode != ConstantsNumberSonar.SONAR_NUMBER_200) {
			return null;
		}
		String responseBody = postMethod.getResponseBodyAsString();
		 if(StringUtils.isEmpty(responseBody)){
			 LOGGER.info("responseBody is null--------->");
			 return null;
		 }
		LOGGER.info(responseBody+"--------->");
		 JSONObject response=JSON.parseObject(responseBody);
		 if(null==response){
			 return null;
		 }
		 if(response.get("success").equals("true")){
			 String mapResults=response.get("mapResults").toString();
			 if(StringUtils.isEmpty(mapResults)){
				 LOGGER.info("mapResults is null--------->");
				 return null;
			 }
			 Map<String,SupplierAndOrderNo> mapResultsMap=
					 (Map<String, SupplierAndOrderNo>) JSON.parseObject(mapResults, new TypeReference<Map<String,SupplierAndOrderNo>>(){});
			 return mapResultsMap;
		 }
		 LOGGER.info(response.get("remark"));
		 LOGGER.info(response.get("remark"));
		 return null;
	}
	
	
	/**
	 * 运单货物批量交接
	* <p>Title: addSalesDeptDeliveryList</p> 
	* <p>Description: </p> 
	* @param salesdeptDeliveryProcs 
	* @see com.deppon.foss.module.base.querying.server.service.ISalesdeptDeliveryprocService#addSalesDeptDeliveryList(java.util.List)
	 */
	@Transactional
	@Override
	public void addSalesDeptDeliveryList(
			List<SalesdeptDeliveryEntity> salesdeptDeliveryProcs) {
		 if(CollectionUtils.isEmpty(salesdeptDeliveryProcs)){
			 throw new SalesdeptDeliveryprocException("交接运单不能为空!","交接运单不能为空!"); 
		 }
		 for(SalesdeptDeliveryEntity entity:salesdeptDeliveryProcs){
			 if(StringUtils.isEmpty(entity.getWayBillNo())){
				 throw new SalesdeptDeliveryprocException("运单异常请刷新页面重试！","运单异常请刷新页面重试！");
			 }
			 entity.setConfirmationTime(new Date());
			 List<SalesdeptDeliveryEntity> entitys= salesDeptDeliveryProcDao.salesDeptDeliveryQuery(entity);
			 entity.setWaystaus("DE_CONFIRM");
			 if(CollectionUtils.isEmpty(entitys)){
				 salesDeptDeliveryProcDao.insertSelective(entity); 
			 }else{
				 salesDeptDeliveryProcDao.updateByPrimaryKey(entity); 
			 }
		 }
	}
	
	 
	/**
	 * 运单交接取消
	* <p>Title: updateSalesDeptDeliveryList</p> 
	* <p>Description: </p> 
	* @param salesdeptDeliveryProcs 
	* @see com.deppon.foss.module.base.querying.server.service.ISalesdeptDeliveryprocService#updateSalesDeptDeliveryList(java.util.List)
	 */
	@Transactional
	@Override
	public void updateSalesDeptDeliveryList(
			List<SalesdeptDeliveryEntity> salesdeptDeliveryProcs) {
		if(CollectionUtils.isEmpty(salesdeptDeliveryProcs)){
			throw new SalesdeptDeliveryprocException("请选择你需要取消的运单信息!","请选择你需要取消的运单信息!"); 
		}
		List<String> codes=new ArrayList<String>();
		WaybillSignResultEntity entity=new WaybillSignResultEntity();
		entity.setActive(FossConstants.ACTIVE);
		for(SalesdeptDeliveryEntity entityParam:salesdeptDeliveryProcs){
			
			if(StringUtils.isEmpty(entityParam.getWaystaus())||
					StringUtils.equals("NON_DE_CONFIRM", entityParam.getWaystaus())){
				throw new SalesdeptDeliveryprocException("只能对已确认交货的运单进行取消运单交货操作!","只能对已确认交货的运单进行取消运单交货操作!"); 
			}
			
			entity.setWaybillNo(entityParam.getWayBillNo());
			//调用结算接口判断(异常签收或者正常签收不能进行交接取消)
			WaybillSignResultEntity waybillEntity=waybillSignResultService.
					queryWaybillSignResultByWaybillNo(entity);
			//如果结算存在签收信息那就不能进行取消
			if(null!=waybillEntity){
				throw new SalesdeptDeliveryprocException("运单号"+entityParam.getWayBillNo()+"已签收不能进行交接取消操作!",
						"运单号"+entityParam.getWayBillNo()+"已签收不能进行交接取消操作!"); 
			}
			codes.add(entityParam.getWayBillNo());
			entityParam.setBrokennote(null);
			entityParam.setPicPersoncard(null);
			entityParam.setIsbroken(null);
			entityParam.setId(UUIDUtils.getUUID());
			entityParam.setActive(FossConstants.ACTIVE);
			entityParam.setCreatetime(new Date());
			entityParam.setModifytime(new Date(NumberConstants.ENDTIME));
			entityParam.setCreateuser(FossUserContext.getCurrentInfo().getEmpCode());
		}
		
		if(CollectionUtils.isEmpty(codes)){
			throw new SalesdeptDeliveryprocException("运单异常请刷新页面重试！","运单异常请刷新页面重试！");
		}
		
		//删除历史数据
		SalesdeptDeliveryEntity updateEntity=new SalesdeptDeliveryEntity();
		updateEntity.setWayBillNos(codes);
		////清除货物破损，提货人身份证号、破损备注信息。(作废新增一条)
		salesDeptDeliveryProcDao.deleteByNoOrId(updateEntity);
		//取消交货相当于直接作废原以确认数据
		/*for(SalesdeptDeliveryEntity entityParam:salesdeptDeliveryProcs){
			entityParam.setWaystaus("NON_DE_CONFIRM");
			salesDeptDeliveryProcDao.salesdeptDeProcInsert(entityParam);
		}*/
	}
	 
	/**
	 *导出
	* <p>Title: exprotSalesDeptDeliveryQuery</p> 
	* <p>Description: </p> 
	* @param waynoStr
	* @param status
	* @return 
	* @see com.deppon.foss.module.base.querying.server.service.ISalesdeptDeliveryprocService#exprotSalesDeptDeliveryQuery(java.lang.String, java.lang.String)
	 */
	@Override
	public ExportResource exprotSalesDeptDeliveryQuery(
			SalesdeptDeliveryEntity entity) {
		
		/*if(StringUtils.isEmpty(status)||!StringUtils.equals(status, "DE_CONFIRM")){
			 throw new SalesdeptDeliveryprocException("只能导出已确认交货的运单!","只能导出已确认交货的运单!"); 
		}
		if(StringUtils.isEmpty(waynoStr)){
	    	 throw new SalesdeptDeliveryprocException("请输入你输入需要导出的运单号!","请输入你输入需要导出的运单号!");  
	     }*/
//		String[] codes=waynoStr.split("\n");
		String[] codes=entity.getWayNos();
		if(codes.length<=0||codes.length>ConstantsNumberSonar.SONAR_NUMBER_5){
			throw new SalesdeptDeliveryprocException("最多选择5个单号！","最多选择5个单号！");
		}
		
		 List<SalesdeptDeliveryEntity> entitys= salesDeptDeliveryProcDao.salesDeptDeliveryQuery(entity);
		/* if(CollectionUtils.isEmpty(entitys)){
			 throw new SalesdeptDeliveryprocException("交接运单为空!","交接运单为空!"); 
		 }*/
		//定义集合
		List<List<String>> resultList = new ArrayList<List<String>>();

		DataDictionaryEntity dataDictionaryEntity=dataDictionaryService.queryDataDictionaryByTermsCode(DictionaryConstants.SPECIAL_DELIVERY_TYPE);
		//迭代循环
		for(SalesdeptDeliveryEntity salesdeptDeliveryEntity : entitys){
		    //实体信息封装到集合中
		    List<String> result = exportInfoList(salesdeptDeliveryEntity,dataDictionaryEntity);
		    //存放到集合
		    resultList.add(result);
		}
		//导出对象创建
		ExportResource sheet = new ExportResource();
		//设置Excel表头
		sheet.setHeads(ComnConst.WAYBILL_INFO_TITLE);
		//设置导出数据
		sheet.setRowList(resultList);
		return sheet;
	}
	 
	/**
	 * 
	* @Title: exportInfoList 
	* @Description: 表格封装
	* @param @param entity
	* @param @return    设定文件 
	* @return List<String>    返回类型 
	* @throws
	 */
	
	
	private List<String> exportInfoList(SalesdeptDeliveryEntity entity,DataDictionaryEntity dataDictionaryEntity){
		 
		List<String> result = new ArrayList<String>();
		result.add(entity.getWayBillNo());
		/*Long stock = entity.getStock();
		if(null!=stock){
			result.add(stock.toString());
		}*/
		Long billing=entity.getBilling();
		if(null!=billing){
			result.add(billing.toString());
		}
		result.add(entity.getSuppliers());
		result.add(entity.getSupplierOrder());
		boolean flag=false;
		if(null!=dataDictionaryEntity){
			List<DataDictionaryValueEntity> dataDictionaryValueEntityList=dataDictionaryEntity.getDataDictionaryValueEntityList();
			if(CollectionUtils.isEmpty(dataDictionaryValueEntityList)){
				result.add(null);
			} else{
				for(DataDictionaryValueEntity dicValue:dataDictionaryValueEntityList){
					 if(StringUtils.equals(dicValue.getValueCode(), entity.getDeliveryMethod())){
						 result.add(dicValue.getValueName());
						 flag=true;
					 }
				 }
				 if(!flag){
					 result.add(null); 
				 }
			}
		}else{
			result.add(null);
		}
		result.add(entity.getGoodsName());
		result.add(entity.getWeight());
		result.add(entity.getVolume());
		Date createTime=entity.getCreatetime();
		if(null!=createTime){
			result.add(DateUtils.convert(createTime));
		}
		result.add(entity.getIsbroken()==null?null:StringUtils.equals(FossConstants.ACTIVE, entity.getIsbroken())?"是":"否");
		result.add(entity.getBrokennote());
		result.add(entity.getGoodsDetails());
		result.add(entity.getPicPersoncard());
		return result;
	    }
 
	/**
	 * 提供给中转，通过运单号获取当前运单号的交货状态 
	* <p>Title: querySalesDeptDeliverysToTfr</p> 
	* <p>Description: </p> 
	* @param wayno
	* @return 
	* @see com.deppon.foss.module.base.querying.server.service.ISalesdeptDeliveryprocService#querySalesDeptDeliverysToTfr(java.lang.String)
	 */
	@Override
	public SalesdeptDeliveryEntity querySalesDeptDeliverysToTfr(
			String wayno,String orgCode) {
		
		if(StringUtils.isEmpty(wayno)||StringUtils.isEmpty(orgCode)){
			LOGGER.error("运单号:"+wayno+",操作部门:"+orgCode+"为空！");
			return null;
		}
		SalesdeptDeliveryEntity entity=new SalesdeptDeliveryEntity();
		entity.setWayBillNo(wayno);
		entity.setOrgCode(orgCode);
		List<SalesdeptDeliveryEntity> salesdeptDeliveryEntitys=
				salesDeptDeliveryProcDao.salesDeptDeliveryQuery(entity);
		return CollectionUtils.isEmpty(salesdeptDeliveryEntitys)?null:salesdeptDeliveryEntitys.get(0);
	}
	/**
	 * 取消交货确认
	* <p>Title: canleSalesDeptDeliverys</p> 
	* <p>Description: </p> 
	* @param salesdeptDeliveryProcs
	* @return 
	* @see com.deppon.foss.module.base.querying.server.service.ISalesdeptDeliveryprocService#canleSalesDeptDeliverys(java.util.List)
	 */
	@Override
	public boolean canleSalesDeptDeliverys(
			List<SalesdeptDeliveryEntity> salesdeptDeliveryProcs) {
		 
		return false;
	}
	/**提供给DOP进行运单信息查询
	* <p>Title: salesDeptDeliveryQueryByWayBillNo</p> 
	* <p>Description: </p> 
	* @param wayBillNo
	* @return 
	* @see com.deppon.foss.module.base.querying.server.service.ISalesdeptDeliveryprocService#salesDeptDeliveryQueryByWayBillNo(java.lang.String)
	 */
	@Override
	public SalesdeptDeliveryEntity salesDeptDeliveryQueryByWayBillNo(
			String wayBillNo) {
		if(StringUtils.isEmpty(wayBillNo)){
			return null;
		}
		SalesdeptDeliveryEntity entity=new SalesdeptDeliveryEntity();
		entity.setWayBillNo(wayBillNo);
		List<SalesdeptDeliveryEntity> salesdeptDeliveryEntitys=
				salesDeptDeliveryProcDao.salesDeptDeliveryQuery(entity);
		if(!CollectionUtils.isEmpty(salesdeptDeliveryEntitys)){
			return salesdeptDeliveryEntitys.get(0);
		}
		return null;
	}

}
