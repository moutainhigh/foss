package com.deppon.foss.module.settlement.ecsitf.server.rs.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.settlement.common.api.server.service.ICreditCustomerService;
import com.deppon.foss.module.settlement.common.api.shared.dto.CreditCustomerDto;
import com.deppon.foss.module.settlement.ecsitf.server.rs.IEcsGrayCustomer;
import com.deppon.foss.util.DateUtils;

public class EcsGrayCustomer implements IEcsGrayCustomer{
	
	/**
	 * 日志
	 */
	private final Logger logger = LogManager.getLogger(getClass());
	
	@Context
	HttpServletResponse res;
	
	//客户编码
	private String CUSTOMER_CODE = "customerCode";
	
	/**
	 * 客户合同信息，获取月结客户的信用额度
	 */
	private ICusBargainService cusBargainService;
	
	/**
	 * 客户信用额度服务,获取最早欠款数据
	 */
	private ICreditCustomerService creditCustomerService;

	/**
	 * 校验客户是否可以开月结
	 * 1.传入参数为空，无法校验是否可开月结！
	 * 2.客户编码为空，不能开月结！
	 * 3.FOSS没有客户月结合同，不能开月结！
	 * 4.存在超期应收单，不能开月结！
	 * 5.出现业务异常，不能开月结！
	 * 6.出现系统异常，不能开月结！
	 * author foss-231434-bieyexiong
	 * @date 2016-12-15 17:09
	 */
	@Override
	public @ResponseBody String checkGrayCustomer(String jsonReq) {
		logger.error(new Date() +"--月结校验请求参数：" + jsonReq);
		//响应参数
		String response = "";
		
		try{
			//设置esb处理结果状态
			res.setHeader("ESB-ResultCode" , "1");
			JSONObject jsonObj = JSONObject.parseObject(jsonReq);
			
			if(jsonObj == null){
				response = this.getResponse(false,"传入参数为空，无法校验是否可开月结！");
				logger.error(new Date() +"--月结校验响应参数：" + response);
				return response;
			}
			
			//获取客户编码
			String customerCode = jsonObj.getString(CUSTOMER_CODE);
			
			if(StringUtils.isBlank(customerCode)){
				response = this.getResponse(false,"客户编码为空，不能开月结！");
				logger.error(new Date() +"--月结校验响应参数：" + response);
				return response;
			}
			
			//查询客户月结合同信息
			CusBargainEntity  cubaEntity=cusBargainService.queryCusBargainByCustCode(customerCode);
			//没有月结合同信息时，无法开月结
			if(cubaEntity == null){
				response = this.getResponse(false,"FOSS没有客户"+customerCode+"的月结合同，不能开月结！");
				logger.error(new Date() +"--月结校验响应参数：" + response);
				return response;
			}
			
			//查询客户月结应收单最早欠款日期
			CreditCustomerDto customerDebitVo =creditCustomerService.queryDebitCustomerInfo(customerCode);
			//没有未还款应收单，可以开月结
			if(customerDebitVo == null){
				response = this.getResponse(true,"客户"+ customerCode +"没有未还款应收单，可以开月结！");
				logger.error(new Date() +"--月结校验响应参数：" + response);
				return response;
			}
			//获取当前时间
			Date currentDate = new Date();
			if(customerDebitVo.getMinDebitDate() == null){
				customerDebitVo.setMinDebitDate(currentDate);
			}
			
			//判断应收单最早欠款日期与当前时间相差天数
            long differDays = DateUtils.getTimeDiff( customerDebitVo.getMinDebitDate(),currentDate);
            //如果月结合同天数小于 最早欠款日期相差天数，无法开月结
            if(cubaEntity.getDebtDays()<differDays){
            	String minDate = new SimpleDateFormat("yyyy-MM-dd").format(customerDebitVo.getMinDebitDate());
            	String msg = "该客户存在超期应收单,最早欠款日期:" +minDate+"最大欠款天数为："+cubaEntity.getDebtDays();
            	response = this.getResponse(false,msg);
            	logger.error(new Date() +"--月结校验响应参数：" + response);
            	return response;
            }
            
            //所有校验通过，可以开月结
            response = this.getResponse(true,"客户"+ customerCode +"没有超期应收单，可以开月结！");
            logger.error(new Date() +"--月结校验响应参数：" + response);
            return response;
		}catch(BusinessException e){
			response = this.getResponse(false,"系统异常：" + e.getErrorCode() != null ? e.getErrorCode() : e.getMessage());
        	logger.error(new Date() +"--月结校验响应参数：" + response);
        	return response;
		}catch(Exception e){
			response = this.getResponse(false,"系统异常：" + e);
        	logger.error(new Date() +"--月结校验响应参数：" + response);
        	return response;
		}
	}
	
	private String getResponse(boolean isSuccess,String msg){
		Map<String,Object> map = new HashMap<String,Object>();
		
		//不在灰名单，返回ture；在灰名单，返回false
		map.put("isSuccess",isSuccess);
		//与悟空沟通，可以不传此参数
		map.put("errorCode", "");
		map.put("msg", msg);
		
		return JSONObject.toJSONString(map);
	}

	public void setCusBargainService(ICusBargainService cusBargainService) {
		this.cusBargainService = cusBargainService;
	}

	public void setCreditCustomerService(
			ICreditCustomerService creditCustomerService) {
		this.creditCustomerService = creditCustomerService;
	}

}
