package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.pickup.waybill.api.server.service.IEcsQueryService;
import com.deppon.foss.module.pickup.waybill.server.utils.HttpClientListUtil;
import com.deppon.foss.module.pickup.waybill.shared.define.EcsConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.CustomerAddressDto;
import com.deppon.foss.module.pickup.waybill.shared.request.NumberRequest;

/**
 * 远程调用ECS
 * @dete 2017-03-21 22:11
 * @author 321993
 * {@value}
 */
public class EcsQueryService implements IEcsQueryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EcsQueryService.class);
	
	@Override
	public List<CustomerAddressDto> queryConsignee(String mobilePhone) {
		// TODO Auto-generated method stub
		List<CustomerAddressDto> resultData=null;
		try {
			NumberRequest bean=new NumberRequest();
			bean.setCustomerMobilePhone(mobilePhone);
			String str=JSONObject.fromObject(bean).toString();
			resultData = (List<CustomerAddressDto>) HttpClientListUtil.postRequestList(EcsConstants.ECS_CONSIGNEEINFO,str,CustomerAddressDto.class);
//			if(null!=responses){
//				if(CollectionUtils.isNotEmpty(responses.getResponses())){
//					resultData=responses.getResponses();
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("EcsQueryService repost Exception");
		}
		return resultData;
	}

}
