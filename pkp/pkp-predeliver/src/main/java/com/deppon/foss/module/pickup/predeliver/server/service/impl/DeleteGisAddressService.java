package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.Holder;

import com.deppon.deppon.gis.inteface.domain.CollectAddressRequest;
import com.deppon.deppon.gis.inteface.domain.CollectAddressResponse;
import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.module.pickup.order.api.shared.define.AddressCollectionConstants;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeleteGisAddressService;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.HandoverBillVo;
import com.deppon.gis.gisservice.CommonException;
import com.deppon.gis.gisservice.GisService;

/**
 * @ClassName: DeleteGisAddressService
 * @Description: 作废Gis地址库Service 实现
 * @author 237982-foss-fangwenjun
 * @date 2015-6-3 下午4:38:23
 * 
 */
public class DeleteGisAddressService implements IDeleteGisAddressService {

	/**
	 * Gis服务
	 */
	private GisService gisService;

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IDeleteGisAddressService#deleteAddress(com.deppon.foss.module.pickup.predeliver.api.shared.vo.HandoverBillVo)
	 */
	@Override
	public CollectAddressResponse deleteAddress(HandoverBillVo handoverBillVo) {
		if (handoverBillVo == null) {
			return null;
		}
		//封装消息头
		ESBHeader header  = new ESBHeader();
		header.setEsbServiceCode(AddressCollectionConstants.ESB_FOSS2ESB_COLLECTADDRESS);
		// 业务相关
		header.setBusinessId(handoverBillVo.getWaybillNo());
		header.setExchangePattern(AddressCollectionConstants.ESB_HEADER__EXCHANGE_PATTERN);
		header.setVersion(AddressCollectionConstants.ESB_HEADER__VERSION);
		// 消息格式
		header.setMessageFormat(AddressCollectionConstants.ESB_HEADER__MESSAGE_FORMAT);
		header.setRequestId(UUID.randomUUID().toString());
		header.setSourceSystem(AddressCollectionConstants.ESB_HEADER_SOURCESYSTEMP);
		CollectAddressRequest request = new CollectAddressRequest();
		request.setIsActive(false);
		request.setProvince(handoverBillVo.getReceiveCustomerProvName());
		request.setCity(handoverBillVo.getReceiveCustomerCityName());
		request.setCountry(handoverBillVo.getReceiveCustomerDistName());
		request.setAddress(handoverBillVo.getReceiveCustomerAddress());
		request.setGpsLNG(handoverBillVo.getLongitude());
		request.setGpsLAT(handoverBillVo.getLatitude());
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		try {
			request.setCollectTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		Holder<ESBHeader> esbHeader = new Holder<ESBHeader>(header);
		CollectAddressResponse response = null;
		try {
			response = gisService.collectAddress(request, esbHeader);
		} catch (CommonException e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 获取gisService
	 * 
	 * @return the gisService
	 */
	public GisService getGisService() {
		return gisService;
	}

	/**
	 * 设置gisService
	 * 
	 * @param gisService
	 *            要设置的gisService
	 */
	public void setGisService(GisService gisService) {
		this.gisService = gisService;
	}

}
