package com.deppon.foss.module.base.querying.server.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.querying.server.service.ISpecialValueAddedService;
import com.deppon.foss.module.base.querying.shared.dto.DeliveryInformationDto;
import com.deppon.foss.module.transfer.partialline.api.server.dao.ISalesDeptDeliveryProcDao;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.SalesdeptDeliveryEntity;
import com.deppon.foss.base.util.define.NumberConstants;
public class SpecialValueAddedService implements ISpecialValueAddedService {
    /**
     * 查询提货信息
     */
	//日志
	private static final Logger log = Logger
			.getLogger(SpecialValueAddedService.class);
	

	private ISalesDeptDeliveryProcDao salesDeptDeliveryProcDao;
	

	public void setSalesDeptDeliveryProcDao(
			ISalesDeptDeliveryProcDao salesDeptDeliveryProcDao) {
		this.salesDeptDeliveryProcDao = salesDeptDeliveryProcDao;
	}


	//dop提货人接口地址
	public String dopDeliveryInformationAddress;
	public SpecialValueAddedService(String dopDeliveryInformationAddress) {
		super();
		this.dopDeliveryInformationAddress = dopDeliveryInformationAddress;
	}
	public String getDopDeliveryInformationAddress() {
		return dopDeliveryInformationAddress;
	}
	public void setDopDeliveryInformationAddress(
			String dopDeliveryInformationAddress) {
		this.dopDeliveryInformationAddress = dopDeliveryInformationAddress;
	}
	
	@Override
	public List<DeliveryInformationDto> queryDeliveryInfo(String waybillNo) {
		// TODO Auto-generated method stub
		log.info("从dop获取提货人信息，安装信息");
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(this.getDopDeliveryInformationAddress());
		method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, NumberConstants.NUMBER_5000);
		client.getHttpConnectionManager().getParams().setConnectionTimeout(NumberConstants.NUMBER_5000);
		method.getParams().setContentCharset("UTF-8");
		method.getParams().setHttpElementCharset("UTF-8");
		String data = "{waybillNo:'"+waybillNo+"'}";
		RequestEntity entity = null;
			try {
				entity = new StringRequestEntity(data, "application/json", "UTF-8");
				method.setRequestEntity(entity);
				method.addRequestHeader("Content-Type",
						"application/json;charset=UTF-8");
				int statuCode = client.executeMethod(method);
				if (statuCode == HttpStatus.SC_OK){
					String responseData = method.getResponseBodyAsString();
					log.info("dop提货人信息/安装信息"+responseData);
					JSONObject obj = JSONObject.parseObject(responseData);
					String code  =(String) obj.get("resultCode");
					if(!StringUtil.isEmpty(code)&&code.equals("1")){
					JSONArray ja = (JSONArray) obj.get("resultList");
					//需求优化,将安装师傅提货时间  显示的数据改为 交货确认时间
					SalesdeptDeliveryEntity record = new SalesdeptDeliveryEntity();
					record.setWayBillNo(waybillNo);
					List<SalesdeptDeliveryEntity>  sds = salesDeptDeliveryProcDao.salesDeptDeliveryQuery(record );
					Date cofirmTime=null;
					if(null!=sds &&sds.size()>0){
						 cofirmTime = sds.get(0).getConfirmationTime();
					}
					//json转换为实体类
					List<DeliveryInformationDto> response = this.deliveryInformationDtoTransfer(ja,cofirmTime);
					
					return response;
					}
					if(!StringUtil.isEmpty(code)&&code.equals("0")){
						String errorMsg = (String)obj.get("errorMessage");
						log.info("dop查询数据异常"+errorMsg);
					}
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				log.info("调用dop接口异常"+e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}
	
	
	//将dop传过来的数据转换为DeliveryInformationDto实体
	public  List<DeliveryInformationDto> deliveryInformationDtoTransfer(JSONArray ja,Date confireTime){
		if(ja.isEmpty()) {
			return null;
		}
		List<DeliveryInformationDto> ls = new ArrayList<DeliveryInformationDto>();
		for (int i = 0; i < ja.size(); i++) {
			DeliveryInformationDto d = new DeliveryInformationDto();
			JSONObject jo =  (JSONObject) ja.get(i);
			d.setWaybillno((String)jo.get("mailNo"));
			d.setDeliveryName((String)jo.get("workerName"));
			d.setAcceptTime(jo.get("createTime")==null?null:new Date(Long.parseLong(jo.get("createTime").toString())));
			d.setInstallTime(jo.get("installTime")==null?null:new Date(Long.parseLong(jo.get("installTime").toString())));
			String  phone =jo.get("pickSuppPhone")==null?null:jo.get("pickSuppPhone").toString();
			d.setProviderPhone(phone);
			d.setValid(jo.get("flag")==null?0:Integer.parseInt(jo.get("flag").toString()));
			//d.setPickUpTime(jo.get("pickUpTime")==null?null:new Date(Long.parseLong(jo.get("pickUpTime").toString())));
			//将安装时间提货时间改为交货确认时间
			d.setPickUpTime(confireTime);
			d.setProviderOrderNo((String)jo.get("suppLogisticId"));
			d.setProviderName((String)jo.get("suppName"));
			d.setRemark((String)jo.get("memo"));
			ls.add(d);
		}
		return ls;
	}
	
}
