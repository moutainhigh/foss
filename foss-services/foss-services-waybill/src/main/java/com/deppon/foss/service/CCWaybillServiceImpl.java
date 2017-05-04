/**
 * Project Name:foss-services-waybill
 * File Name:CCWaybillService.java
 * Package Name:com.deppon.foss.service
 * Date:2014-6-19上午8:55:28
 * Copyright (c) 2014, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.deppon.foss.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.header.ESBHeader;
import com.deppon.esb.inteface.domain.ccwaybillservice.PhoneNum;
import com.deppon.esb.inteface.domain.ccwaybillservice.QueryWaybillByPhoneRequest;
import com.deppon.esb.inteface.domain.ccwaybillservice.QueryWaybillByPhoneResponse;
import com.deppon.esb.inteface.domain.ccwaybillservice.WaybillCountDto;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillQueryService;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.ccwaybillservice.CCWaybillService;
import com.deppon.foss.ccwaybillservice.CommonException;
/**
 * ClassName:CCWaybillService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2014-6-19 上午8:55:28 <br/>
 * @author   157229-zxy
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class CCWaybillServiceImpl implements CCWaybillService{

	/**
     * 日志
     */
	private static final Logger LOGGER = LoggerFactory.getLogger(WaybillServiceImpl.class.getName());
	/**
	 * 默认值1
	 */
	private static final int ONE = 1;
	/**
	 * 运单查询服务
	 */
	private IWaybillQueryService waybillQueryService;
	
	public IWaybillQueryService getWaybillQueryService()
	{
		return waybillQueryService;
	}

	public void setWaybillQueryService(IWaybillQueryService waybillQueryService)
	{
		this.waybillQueryService = waybillQueryService;
	}

	// 注册BigDecimal转换器，否则Bigdecimal转换报错
	static {
		BigDecimalConverter bigDecimalConverter = new BigDecimalConverter(null);
		DateConverter dateConverter = new DateConverter(null);
		ConvertUtils.register(bigDecimalConverter, BigDecimal.class);
		ConvertUtils.register(dateConverter, Date.class);
	}
	
	/**
	 * 
	 * 将Date 类型转换为XML日期格式
	 * @author 043258-foss-zhaobin
	 * @date 2013-1-7 上午11:01:35
	 */
	private XMLGregorianCalendar convertToXMLGregorianCalendar(Date date){
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

	/**
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param cal xml格式日期
	 * @return Date Java类型日期
	 * @see
	 */
	private Date converToJavaDate(XMLGregorianCalendar cal){
		if (cal != null) {
			GregorianCalendar ca = cal.toGregorianCalendar();
			return ca.getTime();
		} else {
			return null;
		}
	}
	
	/**
	 * 判断时间是否为空，如果为空判断异常
	 * @param argsMap
	 * @throws CommonException
	 */
	private void validationData(Map<String,Object> argsMap) throws CommonException
	{
		if(argsMap!=null)
		{
				
			if(argsMap.get("startDate")==null||argsMap.get("endDate")==null)
			{
				throw new CommonException("时间不能为空！");
			}else{
			 Date startDate=(Date)argsMap.get("startDate");
			 Date endDate=(Date)argsMap.get("endDate");
			 Long diffDate= DateUtils.getTimeDiff(startDate, endDate);
			 if(diffDate>3)
			 {
				 throw new CommonException("查询时间范围超过3天，请调整时间！");
			 }
			
			}
			
		}
	}
	
	
	@Override
	public QueryWaybillByPhoneResponse queryWaybillByPhone(
			Holder<ESBHeader> esbHeader, QueryWaybillByPhoneRequest request)
			throws CommonException {
		// TODO Auto-generated method stub
			esbHeader.value.setResponseId(UUID.randomUUID().toString());
			QueryWaybillByPhoneResponse response = new QueryWaybillByPhoneResponse();
			List<WaybillCountDto> result = new ArrayList<WaybillCountDto>();
			List<PhoneNum> list = request.getPhoneList();
			//封装查询参数Map集合
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("startDate", converToJavaDate(request.getStartDate()));
			condition.put("endDate", converToJavaDate(request.getEndDate()));
			/**
			 * 校验时间
			 */
			validationData(condition);
			
			//是否关联收货人，默认关联，所以没有指明为“N”，则默认是“Y”
			if(!FossConstants.NO.equals(request.getIsAssociatedConsignee())){
				condition.put("isAssociatedConsignee", FossConstants.YES);
			}else{
				condition.put("isAssociatedConsignee", FossConstants.NO);
			}
			if(list!=null && list.size()>0){
				//由于Oracle in后面只能跟最多1000个参数，因此，每次查询处理1000个list
				int mod = list.size()/1000;
				for(int i =0;i<mod+1;i++){
					
					int maxValue = 0;
					if(1000*(i+1)>list.size()){
						maxValue = list.size();
					}else{
						maxValue = 1000*(i+1);
					}
					StringBuilder phones =new StringBuilder();
					StringBuilder mobilePhones =new StringBuilder();
					for(int j = 1000*i;j<maxValue;j++){
						PhoneNum object = list.get(j);
						//首先判断客户手机号是否存在，存在则不往下判断，否则往下继续判断
						if(object.getMobilePhone()!=null && !"".equals(object.getMobilePhone())){
							if(!"".equals(mobilePhones.toString())){
								mobilePhones.append(",'"+object.getMobilePhone()+"'");
							}else{
								mobilePhones.append("'"+object.getMobilePhone()+"'");
							}
						}else{
							if(object.getPhone()!=null && !"".equals(object.getPhone())){
								if(!"".equals(phones.toString())){
									phones.append(",'"+object.getPhone()+"'");
								}else{
									phones.append("'"+object.getPhone()+"'");
								}
							}
						}
					}
					if("".equals(phones.toString()))
						phones = new StringBuilder("''");
					if("".equals(mobilePhones.toString()))
						mobilePhones =  new StringBuilder("''");
					condition.put("phones", phones.toString());
					condition.put("mobilePhones", mobilePhones.toString());
					List<WaybillCountDto> resultList = waybillQueryService.queryWaybillByPhone(condition);
					result.addAll(resultList);
				}
			}
		response.getWaybillCountList().addAll(result);
		esbHeader.value.setResultCode(1);
		return response;
	}
	

}

