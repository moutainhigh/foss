package com.deppon.foss.module.pickup.waybill.server.utils.cubc.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.module.pickup.waybill.server.utils.HttpClientUtil;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.RequestDO;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.ResultList;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.TradeDO;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.VestLogDo;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.VestResponse;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.service.IGrayScaleService;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.service.IVestLogServicePkp;

/**
 * 
    * @ClassName: GrayScaleService
    * @Description: 调用灰度接口的service服务
    * @author 323098
    * @date 2017年1月1日
    *
 */
public class GrayScaleService implements IGrayScaleService{
	//灰度接口地址
	private String grayScaleUrlVestAscription;
	private String grayScaleUrlVestSourceBillNo;
	private String grayScaleUrlVestCustomer;
	private String grayQueryOrderByList;
	private String grayQueryOrderByNo;
	@Autowired
	private IVestLogServicePkp vestLogServicePkp;
	private Logger logger = LoggerFactory.getLogger(GrayScaleService.class);
	//根据客户分类方法    对应原本你们本来调的jar方法 vestByCustomer
	@Override
	public VestResponse vestByCustomer(RequestDO requestDO) throws Exception {
		VestResponse result = null;
		//1.封装参数
		String requestJson = JSON.toJSONString(requestDO);
		logger.info("cubc灰度请求内容"+requestJson);
		try {
		    //2.远程调用服务 含异常处理情况
		    result = HttpClientUtil.postRequest(grayScaleUrlVestCustomer, requestJson,VestResponse.class);
		    //4.返回调用信息
		    return result;
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    throw e;
		}finally{
		    //3.记录日志信息
    		logger.info("cubc灰度返回的内容"+JSON.toJSONString(result));
    		VestLogDo vestLog = new VestLogDo();
    		vestLog.setRequestTime(new Date());
    		vestLog.setRequestId(requestDO.getRequestId());
    		vestLog.setOrigin(requestDO.getOrigin());
    		vestLog.setCustomerCode(Arrays.toString(requestDO.getCustomerCodes()));
    		vestLog.setCustomerType(requestDO.getCustomerType());
    		vestLog.setServiceCode(requestDO.getServiceCode());
    		vestLog.setSourceBillNo(requestDO.getSourceBillNos() == null || requestDO.getSourceBillNos().length == 0 ? null :requestDO.getSourceBillNos().toString());
    		//vestLog.setSystemCode(result.getVestBatchResult().get(0).getVestSystemCode());
    		vestLog.setResponseContent(JSON.toJSONString(result));
    		vestLog.setResponseTime(new Date());
    		vestLogServicePkp.log(vestLog);
		}
	}
	//根据来源单号分流 对应原本你们本来调的jar方法 vestBySourceBillNo
	@Override
	public VestResponse vestBySourceBillNo(RequestDO requestDO) throws Exception {
		VestResponse result = null;
		//1.封装参数
		String requestJson = JSON.toJSONString(requestDO);
		logger.info("cubc灰度请求内容"+requestJson);
		try {
		    //2.远程调用服务 含异常处理情况
		    result = HttpClientUtil.postRequest(grayScaleUrlVestSourceBillNo, requestJson,VestResponse.class);
		    //4.返回调用信息
		    return result;
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    throw e;
		}finally{
		    logger.info("cubc灰度返回的内容"+JSON.toJSONString(result));
		    //3.记录日志信息
		    VestLogDo vestLog = new VestLogDo();
		    vestLog.setRequestTime(new Date());
		    vestLog.setRequestId(requestDO.getRequestId());
		    vestLog.setOrigin(requestDO.getOrigin());
		    vestLog.setSourceBillNo(Arrays.toString(requestDO.getSourceBillNos()));
		    vestLog.setSourceBillType(requestDO.getSourceBillType());
		    vestLog.setServiceCode(requestDO.getServiceCode());
		    //	vestLog.setSystemCode(result.getVestBatchResult().get(0).getVestSystemCode());
		    vestLog.setResponseContent(JSON.toJSONString(result));
		    vestLog.setResponseTime(new Date());
		    vestLogServicePkp.log(vestLog);
		}
		
	}
	//归属规则  对应你们本来调用的vestAscription
	@Override
	public VestResponse vestAscription(RequestDO requestDO) throws Exception {
		
		//String serviceCode, String origin, String sourceBillType,String... sourceBillNos
		VestResponse result = null;
		//1.封装参数
		String requestJson = JSON.toJSONString(requestDO);
		logger.info("cubc灰度请求内容"+requestJson);
		try {
		    //2.远程调用服务 含异常处理情况
		    result = HttpClientUtil.postRequest(grayScaleUrlVestAscription, requestJson,VestResponse.class);
		    //4.返回调用信息
		    return result;
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    throw e;
		}finally{
		    logger.info("cubc灰度返回的内容"+JSON.toJSONString(result));
		    //3.记录日志信息
		    VestLogDo vestLog = new VestLogDo();
		    vestLog.setRequestTime(new Date());
		    vestLog.setRequestId(requestDO.getRequestId());//請求id
		    vestLog.setOrigin(requestDO.getOrigin());//请求的系统
		    vestLog.setSourceBillNo(Arrays.toString(requestDO.getSourceBillNos()));//请求的单号
		    vestLog.setSourceBillType(requestDO.getSourceBillType());//请求的
		    vestLog.setServiceCode(requestDO.getServiceCode());//请求的
		    //vestLog.setSystemCode(result.getVestBatchResult().get(0).getVestSystemCode());
		    vestLog.setResponseContent(JSON.toJSONString(result));
		    vestLog.setResponseTime(new Date());
		    vestLogServicePkp.log(vestLog);
		}
		//return HttpClientUtil.postRequest(grayScaleUrl, JSON.toJSONString(grayScaleRequest), GrayScaleResponse.class);
	}
	
	public List<TradeDO> grayQueryOrderByList(String  parmaters) throws Exception {
		
		List<TradeDO> result = null;
		logger.info("cubc灰度请求内容"+parmaters);
		try {
			//2.远程调用服务 含异常处理情况
			result = HttpClientUtil.postRequest(grayQueryOrderByList, parmaters,ResultList.class).getDatalist();
			//4.返回调用信息
			return result;
		} catch (Exception e) {
			
			throw e;
		}finally{
			logger.info("cubc灰度返回的内容"+JSON.toJSONString(result));
			//3.记录日志信息
			VestLogDo vestLog = new VestLogDo();
			vestLog.setRequestTime(new Date());
			vestLog.setRequestId(new Date().getTime());//請求id
			vestLog.setResponseContent(JSON.toJSONString(result));
			vestLog.setResponseTime(new Date());
			vestLogServicePkp.log(vestLog);
		}
		
	}
	public List<TradeDO> grayQueryOrderByNo(String parmaters) throws Exception {
		
		List<TradeDO> result = null;
		//1.封装参数
		logger.info("cubc灰度请求内容"+parmaters);
		try {
			//2.远程调用服务 含异常处理情况
			result = HttpClientUtil.postRequest(grayQueryOrderByNo, parmaters,ResultList.class).getDatalist();
			//4.返回调用信息
			return result;
		} catch (Exception e) {
			throw e;
		}finally{
			logger.info("cubc灰度返回的内容"+JSON.toJSONString(result));
			//3.记录日志信息
			VestLogDo vestLog = new VestLogDo();
			vestLog.setRequestTime(new Date());
			vestLog.setRequestId(new Date().getTime());//請求id
			vestLog.setResponseContent(JSON.toJSONString(result));
			vestLog.setResponseTime(new Date());
			vestLogServicePkp.log(vestLog);
		}
		
	}
	public void setGrayScaleUrlVestAscription(String grayScaleUrlVestAscription) {
	    this.grayScaleUrlVestAscription = grayScaleUrlVestAscription;
	}
	public void setGrayScaleUrlVestSourceBillNo(String grayScaleUrlVestSourceBillNo) {
	    this.grayScaleUrlVestSourceBillNo = grayScaleUrlVestSourceBillNo;
	}
	public void setGrayScaleUrlVestCustomer(String grayScaleUrlVestCustomer) {
	    this.grayScaleUrlVestCustomer = grayScaleUrlVestCustomer;
	}
	
	public void setVestLogServicePkp(IVestLogServicePkp vestLogServicePkp) {
		this.vestLogServicePkp = vestLogServicePkp;
	}
	public void setLogger(Logger logger) {
	    this.logger = logger;
	}
	public void setGrayQueryOrderByList(String grayQueryOrderByList) {
		this.grayQueryOrderByList = grayQueryOrderByList;
	}
	public void setGrayQueryOrderByNo(String grayQueryOrderByNo) {
		this.grayQueryOrderByNo = grayQueryOrderByNo;
	}
	
}
