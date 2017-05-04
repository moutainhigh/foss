package com.deppon.foss.module.pickup.order.server.service.impl;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.deppon.gis.inteface.domain.CollectAddressRequest;
import com.deppon.deppon.gis.inteface.domain.CollectAddressResponse;
import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.framework.server.sso.util.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.service.IGisAddressCollectionService;
import com.deppon.foss.module.pickup.order.api.shared.define.AddressCollectionConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.AddressCollectionEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.gis.gisservice.CommonException;
import com.deppon.gis.gisservice.GisService;

public class GisAddressCollectionService implements
		IGisAddressCollectionService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GisAddressCollectionService.class);
	
	/**
	 * 省市区服务
	 * */
	private IAdministrativeRegionsService administrativeRegionsService;	
	
	/**
	 * 运单服务
	 * */
	private IWaybillManagerService waybillManagerService;
	
	/**
	 * 订单Dao
	 * */
	private IDispatchOrderEntityDao dispatchOrderEntityDao;
	

	/**
	 * GIS搜集地址服务
	 * */
	private GisService gisService;	

	/**
	 * 
	 * 将Date 类型转换为XML日期格式
	 * @author 043258-foss-zhaobin
	 * @date 2013-1-7 上午11:01:35
	 */
	private XMLGregorianCalendar convertToXMLGregorianCalendar(Date date)
	{
		if(date == null)
		{
			return null;
		}
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		XMLGregorianCalendar gc = null;
		try {
		    gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		} catch (Exception e) {
			LOGGER.info("XML日期类型转换错误：", e.getMessage());
		}
		return gc;
	    }

	@Override
	public CollectAddressResponse fossToGisAddressInfo(
			AddressCollectionEntity entity) throws CommonException {

		CollectAddressRequest  request = new CollectAddressRequest();
		//设置搜集时间	
		 XMLGregorianCalendar collectionTime = convertToXMLGregorianCalendar(entity.getCollectionTime());
		request.setCollectTime(collectionTime);
		//设置经度纬度
		request.setGpsLAT(entity.getGpsLatitude());
		request.setGpsLNG(entity.getGpsLongitude());
		
		request.setIsActive(Boolean.TRUE);
		request.setAddressType(entity.getAddressType());
		//如果为运单则需要转化，如果为订单则直接获取
		if(AddressCollectionConstants.LTL_PICKUP.equals(entity.getAddressType())
				||AddressCollectionConstants.PACKAGE_PICKUP.equals(entity.getAddressType())){
			//获取订单信息
			DispatchOrderEntity orderEntity = dispatchOrderEntityDao.queryAllInfoByOrderNo(entity.getBillNo());
			//设置省市区详细地址
			if(orderEntity != null){
			//判断省市区是否为空
			if(StringUtil.notNull(orderEntity.getPickupProvince())					
					&&StringUtil.notNull(orderEntity.getPickupCity())
			       &&StringUtil.notNull(orderEntity.getPickupCounty())
			       &&StringUtil.notNull(orderEntity.getPickupElseAddress())){
			request.setProvince(orderEntity.getPickupProvince());
			request.setCity(orderEntity.getPickupCity());
			request.setCountry(orderEntity.getPickupCounty());
			request.setAddress(orderEntity.getPickupElseAddress());
			 }else {
					throw new CommonException("传入的订单省市区为空,订单号："+entity.getBillNo());
			 }
			}else {
				throw new CommonException("传入的订单不存在,订单号："+entity.getBillNo());
			}
		}else if (AddressCollectionConstants.LTL_DELIVER.equals(entity.getAddressType())
				||AddressCollectionConstants.PACAKGE_DELIVER.equals(entity.getAddressType())){
			 WaybillEntity  waybillEntity = waybillManagerService.queryWaybillBasicByNo(entity.getBillNo());
			 //获得省市区名称
			 AdministrativeRegionsEntity regionEntity= null;
			 if(waybillEntity != null){
				 //城市判断
				 regionEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(
					 waybillEntity.getReceiveCustomerCityCode());
				 if(regionEntity != null){
					 request.setCity(regionEntity.getName());
				 }else{
					 throw new CommonException("运单的市不存在,运单号："+entity.getBillNo());
				 }
				 //省份判断
				 regionEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(
					 waybillEntity.getReceiveCustomerProvCode());
				 if(regionEntity != null){
					 request.setProvince(regionEntity.getName());
				 }else{
					 throw new CommonException("运的省份不存在,运单号："+entity.getBillNo());
				 }
				 //区域判断
				 regionEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(
					 waybillEntity.getReceiveCustomerDistCode());
				 if(regionEntity != null){
					 request.setCountry(regionEntity.getName());
				 }else{
					 throw new CommonException("运单的区不存在,运单号："+entity.getBillNo());
				 }
				 if(waybillEntity.getReceiveCustomerAddress() != null&&
						 !"".equals(waybillEntity.getReceiveCustomerAddress())){
				 request.setAddress(waybillEntity.getReceiveCustomerAddress());
				 }else {
					 throw new CommonException("运单的地址不存在,运单号："+entity.getBillNo());
				 }

			 }else{
					throw new CommonException("传入的运单不存在,运单号："+entity.getBillNo());
			 }			 
			 
		}
		//封装消息头
		ESBHeader header  = new ESBHeader();
		header.setEsbServiceCode(AddressCollectionConstants.ESB_FOSS2ESB_COLLECTADDRESS);
		//业务相关
		header.setBusinessId(entity.getBillNo());
		header.setExchangePattern(AddressCollectionConstants.ESB_HEADER__EXCHANGE_PATTERN);
		header.setVersion(AddressCollectionConstants.ESB_HEADER__VERSION);
		//消息格式
		header.setMessageFormat(AddressCollectionConstants.ESB_HEADER__MESSAGE_FORMAT);
		header.setRequestId(UUID.randomUUID().toString());
		header.setSourceSystem(AddressCollectionConstants.ESB_HEADER_SOURCESYSTEMP);
		
		Holder<ESBHeader> holder = new Holder<ESBHeader>(header);
		//消息推入ESB
		CollectAddressResponse  response = gisService.collectAddress(request, holder);
		return response;
	
	}
	/**
	 * 删除GIS固定库GPS坐标  14.7.23 gcl AUTO-195
	 * @return
	 * @throws CommonException
	 */
	@Override
	public CollectAddressResponse delGisAddressInfo(DispatchOrderEntity entity) 
			throws CommonException {
		//封装消息头
		ESBHeader header  = new ESBHeader();
		header.setEsbServiceCode(AddressCollectionConstants.ESB_FOSS2ESB_COLLECTADDRESS);
		//业务相关
		header.setBusinessId(entity.getOrderNo());
		header.setExchangePattern(AddressCollectionConstants.ESB_HEADER__EXCHANGE_PATTERN);
		header.setVersion(AddressCollectionConstants.ESB_HEADER__VERSION);
		//消息格式
		header.setMessageFormat(AddressCollectionConstants.ESB_HEADER__MESSAGE_FORMAT);
		header.setRequestId(UUID.randomUUID().toString());
		header.setSourceSystem(AddressCollectionConstants.ESB_HEADER_SOURCESYSTEMP);
				
		Holder<ESBHeader> holder = new Holder<ESBHeader>(header);
		CollectAddressRequest  request = new CollectAddressRequest();
		request.setProvince(entity.getPickupProvince());
		request.setCity(entity.getPickupCity());
		request.setCountry(entity.getPickupCounty());
		request.setAddress(entity.getPickupElseAddress());
		request.setAddressType("KD_PK");
		request.setCollectTime(convertToXMLGregorianCalendar(new Date()));
		request.setGpsLAT("30");
		request.setGpsLNG("30");
		request.setIsActive(Boolean.FALSE);
		//消息推入ESB
		CollectAddressResponse  response = gisService.collectAddress(request, holder);
		return response;
	}

	public IAdministrativeRegionsService getAdministrativeRegionsService() {
		return administrativeRegionsService;
	}

	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	public IWaybillManagerService getWaybillManagerService() {
		return waybillManagerService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public IDispatchOrderEntityDao getDispatchOrderEntityDao() {
		return dispatchOrderEntityDao;
	}

	public void setDispatchOrderEntityDao(
			IDispatchOrderEntityDao dispatchOrderEntityDao) {
		this.dispatchOrderEntityDao = dispatchOrderEntityDao;
	}

	public GisService getGisService() {
		return gisService;
	}

	public void setGisService(GisService gisService) {
		this.gisService = gisService;
	}

}
