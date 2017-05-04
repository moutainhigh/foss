/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.crm._interface.crmservice.CrmService;
import com.deppon.esb.header.ESBHeader;
import com.deppon.esb.inteface.domain.waybillservice.OaQueryDetailRequest;
import com.deppon.esb.inteface.domain.waybillservice.OaQueryDetailResponse;
import com.deppon.esb.inteface.domain.waybillservice.QueryDetailForOfficialRequest;
import com.deppon.esb.inteface.domain.waybillservice.QueryDetailForOfficialResponse;
import com.deppon.esb.inteface.domain.waybillservice.QueryDetailRequest;
import com.deppon.esb.inteface.domain.waybillservice.QueryDetailResponse;
import com.deppon.esb.inteface.domain.waybillservice.QueryOneYearDetailRequest;
import com.deppon.esb.inteface.domain.waybillservice.QueryOneYearDetailResponse;
import com.deppon.esb.inteface.domain.waybillservice.WayBillDetail;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.server.service.impl.CrmOrderService;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmWaybillServiceDto;
import com.deppon.foss.waybillservice.CommonException;
import com.deppon.foss.waybillservice.WaybillService;

/**
 * 
 * CRM WAYBILL SERVICE 
 * 
 * @author 026113-foss-linwensheng
 * @date 2012-10-22 下午6:43:03
 */
public class CrmWaybillService implements WaybillService {
	/**
	 * 日志
	 */
	protected static final Logger LOG = LoggerFactory
			.getLogger(CrmWaybillService.class.getName());
	
	private static final String VERSION = "0.1";
	
	private static final String BUSINESS_ID = "ORDER";

	private Holder<ESBHeader> esbHeader;

	private IWaybillManagerService waybillManagerService;

	private CrmOrderService crmService;
	
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * 创建远程服务
	 * @author 043260-foss-suyujun
	 * @date 2012-12-18
	 * @return void
	 * @see
	 */
	protected void createService() {
		if (this.crmService == null) {
			CrmService port = new CrmService();
			WaybillService service = (WaybillService) port.getCrmService();
			if (service != null) {
				this.crmService = (CrmOrderService) service;
			}
		}
	}
	


	/**
	 * 创建头文件
	 * @author 043260-foss-suyujun
	 * @date 2012-12-18
	 * @return void
	 */
	protected void createHeader() {
		if (this.esbHeader == null) {
			esbHeader = new Holder<ESBHeader>();
			ESBHeader header = new ESBHeader();
			header.setVersion(VERSION);
			header.setBusinessId(BUSINESS_ID);
			header.setSourceSystem("FOSS");
			header.setTargetSystem("CRM");
			header.setMessageFormat("SOAP");
			header.setExchangePattern(1);
			esbHeader.value = header;
		}
	}
	
	/**
	 * queryWaybillInfo
	 * @param waybillNo
	 * @return
	 */
	public List<WayBillDetail> queryWaybillInfo(List<String> waybillNo) {
		return null;
	}



	/**
	 * 接口查询方法
	 * @author 043260-foss-suyujun
	 * @date 2012-12-18
	 * @param esbHeader
	 * @param payload
	 * @throws CommonException
	 * @see com.deppon.foss.waybillservice.WaybillService#queryDetail(javax.xml.ws.Holder, com.deppon.esb.inteface.domain.waybillservice.QueryDetailRequest)
	 */
	@Override
	public QueryDetailResponse queryDetail(Holder<ESBHeader> esbHeader,
			QueryDetailRequest payload) throws CommonException {
		List<String> waybillNoList = payload.getWaybillNo();
		List<CrmWaybillServiceDto> crmServiceDto = waybillManagerService.queryWaybillDetail(waybillNoList);
		return convertResultDto2WaybillDetail(crmServiceDto);
	}



	/**
	 * 转换Dto到接口需要的类型
	 * @author 043260-foss-suyujun
	 * @date 2012-12-18
	 * @param crmServiceDto
	 * @return QueryDetailResponse
	 * @see
	 */
	private QueryDetailResponse convertResultDto2WaybillDetail(List<CrmWaybillServiceDto> crmServiceDto) {
		QueryDetailResponse response = new QueryDetailResponse();
		List<WayBillDetail> list = new ArrayList<WayBillDetail>();
		WayBillDetail detail = null;
		for(CrmWaybillServiceDto dto : crmServiceDto){
			detail = new WayBillDetail();
			detail.setArriveCharge(dto.getArriveCharge());
			detail.setConsignCharge(dto.getConsignCharge());
			detail.setConsignee(dto.getConsignee());
			detail.setConsigneeAddress(dto.getConsigneeAddress());
			detail.setConsigneeCityCode(dto.getConsigneeCityCode());
			detail.setConsigneeCityName(dto.getConsigneeCityName());
			detail.setConsigneeMobile(dto.getConsigneeMobile());
			detail.setConsigneePhone(dto.getConsigneePhone());
			detail.setConsigneeProvinceCode(dto.getConsigneeProvinceCode());
			detail.setConsigneeProvinceName(dto.getConsigneeProvinceName());
			detail.setCubage(dto.getCubage());
			detail.setDeliveryCharge(dto.getDeliveryCharge());
			detail.setDeliveryType(dto.getDeliveryType());
			detail.setDeparture(dto.getDeparture());
			detail.setDepartureDeptAddr(dto.getDepartureDeptAddr());
			detail.setDepartureDeptFax(dto.getDepartureDeptFax());
			detail.setDepartureDeptName(dto.getDepartureDeptName());
			detail.setDepartureDeptNumber(dto.getDepartureDeptNumber());
			detail.setDepartureDeptPhone(dto.getDepartureDeptPhone());
			detail.setDestination(dto.getDestination());
			detail.setGoodName(dto.getGoodName());
			detail.setInsurance(dto.getInsurance());
			detail.setInsuranceValue(dto.getInsuranceValue());
			detail.setIsClear(dto.getIsClear());
//			detail.setIsDoorToDoorPick(dto.getIsDoorToDoorPick());
//			detail.setIsNormalSigned(dto.getIsNormalSigned());
//			detail.setIsSigned(dto.getIsSigned());
//			detail.setIsSmsNotice(dto.getIsSmsNotice());
			detail.setLaborRebate(dto.getLaborRebate());
			detail.setLadingStationAddr(dto.getLadingStationAddr());
			detail.setLadingStationFax(dto.getLadingStationFax());
			detail.setLadingStationName(dto.getLadingStationName());
			detail.setLadingStationNumber(dto.getLadingStationNumber());
			detail.setLadingStationPhone(dto.getLadingStationPhone());
			detail.setNumber(dto.getWaybillNo());
			detail.setOrderNumber(dto.getOrderNumber());
			detail.setOrderState(dto.getOrderState());
			detail.setOtherPayment(dto.getOtherPayment());
			detail.setPacking(dto.getPacking());
			detail.setPayment(dto.getPayment());
			detail.setPickCharge(dto.getPickCharge());
			detail.setPieces(dto.getPieces());
			detail.setPreCharge(dto.getPreCharge());
			detail.setPublishCharge(dto.getPublishCharge());
			detail.setReceiveDeptName(dto.getReceiveDeptName());
			detail.setReceiveDeptNumber(dto.getReceiveDeptName());
			detail.setRefund(dto.getRefund());
			detail.setRefundFee(dto.getRefundFee());
			detail.setRefundType(dto.getRefundType());
			detail.setSender(dto.getSender());
			detail.setSenderAddress(dto.getSenderAddress());
			detail.setSenderCityCode(dto.getSenderCityCode());
			detail.setSenderCityName(dto.getSenderCityName());
			detail.setSenderMobile(dto.getSenderMobile());
			detail.setSenderNumber(dto.getSenderNumber());
			detail.setSenderPhone(dto.getSenderPhone());
			detail.setSenderProvinceCode(dto.getSenderProvinceCode());
			detail.setSenderProvinceName(dto.getSenderProvinceName());
			detail.setSendTime(convertToXMLGregorianCalendar(dto.getSendTime()));
			detail.setSignBackCharge(dto.getSignBackCharge());
			detail.setSignBackType(dto.getSignBackType());
			detail.setSignBillBackWay(dto.getSignBillBackWay());
			detail.setSignedDesc(dto.getSignedDesc());
			detail.setSignedDate(convertToXMLGregorianCalendar(dto.getSignedDate()));
			detail.setSignRecorderId(dto.getSignRecorderId());
			detail.setStowageDept(dto.getStowageDept());
			//detail.setStowageType(dto.getStowageType());
			detail.setTotalCharge(dto.getTotalCharge());
			detail.setTranDesc(dto.getTranDesc());
			detail.setTransNotice(dto.getTransNotice());
			detail.setTranType(dto.getTranType());
			detail.setWeight(dto.getWeight());
			list.add(detail);
		}
		response.getWayBillDetailList().addAll(list);
		return response;
	}
	
	/**
	 * 
	 * <p>
	 * 把java日期转换为XML格式日期
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-11-13 下午4:55:50
	 * @param date
	 * @return
	 * @see
	 */
	private XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {
		if (date != null) {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			XMLGregorianCalendar gc = null;
			try {
				gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			} catch (Exception e) {
				LOG.info("XML日期类型转换错误：", e);
			}
			return gc;
		} else {
			return null;
		}
	}

	@Override
	public QueryOneYearDetailResponse queryOneYearDetail(
			Holder<ESBHeader> esbHeader,
			QueryOneYearDetailRequest queryOneYearDetailRequest)
			throws CommonException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OaQueryDetailResponse queryOADetail(Holder<ESBHeader> esbHeader,
			OaQueryDetailRequest oAQueryDetailRequest) throws CommonException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QueryDetailForOfficialResponse queryDetailForOfficial(
			Holder<ESBHeader> arg0, QueryDetailForOfficialRequest arg1)
			throws CommonException {
		// TODO Auto-generated method stub
		return null;
	}

}
