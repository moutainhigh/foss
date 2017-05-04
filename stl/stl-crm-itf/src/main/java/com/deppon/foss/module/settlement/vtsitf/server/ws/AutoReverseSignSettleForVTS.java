package com.deppon.foss.module.settlement.vtsitf.server.ws;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.common.api.server.service.IEsbInterfaceLogService;
import com.deppon.foss.module.settlement.common.api.server.service.IReverseSignSettleService;
import com.deppon.foss.module.settlement.common.api.shared.domain.AutoReverseSignSettleRequestEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.InterfaceLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.RequestAutoReverseSignSettle;
import com.deppon.foss.module.settlement.common.api.shared.domain.ResponseAutoReverseSignSettle;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSResverSettleRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.vtsitf.server.inter.IAutoReverseSignSettleForVTS;
import com.deppon.foss.util.UUIDUtils;
/**
 * 
 * @author 218392 zhangyongxue
 * @date 2016-06-12 08:34:20
 * vts自动反签收反结清restful接口
 *
 */
public class AutoReverseSignSettleForVTS implements IAutoReverseSignSettleForVTS{
	
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AutoReverseSignSettleForVTS.class);
	
	/**
	 * 注入vts自动反签收、反结清reverseSignSettleService
	 */
	private IReverseSignSettleService reverseSignSettleService;
	
	/**
	 * 注入接口日志service
	 */
	private IEsbInterfaceLogService esbInterfaceLogService;
	
	@Context
    protected HttpServletRequest req;
	@Context
	protected HttpServletResponse resp;

	//反签收的校验
	@Override
	public String checkReverseSignSettle(String autoReverseSignEntity) {
		
		LOGGER.info("vts反签收反结清开始！" );
		
		//转换请求
		JSONObject object = (JSONObject) JSONObject.parseObject(autoReverseSignEntity).get("requestEntity");
		RequestAutoReverseSignSettle requestVTS = JSONObject.toJavaObject(object, RequestAutoReverseSignSettle.class);
		//响应
		ResponseAutoReverseSignSettle response = new ResponseAutoReverseSignSettle();
		
		//判空
		if(requestVTS == null){
			response.setIfSuccess(false);
			response.setErrorMsg("vts传给FOSS的参数解析为null！");
			throw new SettlementException("传入参数为空！");
		}
		
		String waybillNo = requestVTS.getWaybillNo();//运单号
		String reverseType = requestVTS.getAutoReverseType();//自动反操作类型：unsign-反签收   unsettle-反结清
		String currentDeptCode = requestVTS.getCurrentDeptCode();
		String currentDeptName = requestVTS.getCurrentDeptName();
		String empCode = requestVTS.getEmpCode();
		String empName = requestVTS.getEmpName();
		String unifiedCodeFromVTS = requestVTS.getUnifiedCodeFromVTS();
		
		List<String> ids = new ArrayList<String>();
		//定义VTS请求过来的实体中的List集合，主要是为了获取到达付款表实体中的ID
		List<VTSResverSettleRepaymentEntity> resverSettleRepaymentEntity = requestVTS.getSettleEntityList();
		//定义FOSS中的实体,和VTS传过来的请求对应
		AutoReverseSignSettleRequestEntity request = new AutoReverseSignSettleRequestEntity();
		if(resverSettleRepaymentEntity != null){//如果反签收就为空，反结清的时候不为空！
			for(VTSResverSettleRepaymentEntity entityList : resverSettleRepaymentEntity){
				ids.add(entityList.getId());
			}
			request.setRepaymentIds(ids);//设置IDS集合
		}
		request.setAutoReverseType(reverseType);//设置VTS前台反申请的类型
		request.setCurrentDeptCode(currentDeptCode);//设置登录部门
		request.setCurrentDeptName(currentDeptName);//设置登录部门名称
		request.setEmpCode(empCode);//设置员工工号
		request.setEmpName(empName);//设置员工姓名
		request.setUnifiedCodeFromVTS(unifiedCodeFromVTS);//设置部门标杆编码
		request.setWaybillNo(waybillNo);//设置运单号:反签收成功之后只要工号和
		
		/**
		 * 定义插入log日志插入结算VTS专门的日志表
		 * stl.t_stl_bill_interfacelog
		 */
		InterfaceLogEntity interfaceEntity = new InterfaceLogEntity();
		interfaceEntity.setId(UUIDUtils.getUUID());
		interfaceEntity.setWaybillNo(waybillNo);
		interfaceEntity.setEsbCode("FOSS_ESB2FOSS_VTS_TO_FOSS_AUTO_ACCEPT_UNSIGNSETTLE");//服务端编码:自动反结清
		interfaceEntity.setSystemType("VTS");
		interfaceEntity.setSendContent(JSONObject.toJSONString(requestVTS));
		interfaceEntity.setCreateUser("218392");
		interfaceEntity.setModifyUser("自动反签收反结清rest接口");
		interfaceEntity.setCreateTime(new Date());

		try{
			resp.setHeader("ESB-ResultCode", "1");
			String msg = "";
			
			//反签收校验+处理
			if("unsign".equals(reverseType)){
			  if(StringUtils.isEmpty(waybillNo)){
				  throw new SettlementException("反签收传输单号为空！");
			  }
			  LOGGER.info("vts反签收校验开始！单号为：" + waybillNo);
			  msg = reverseSignSettleService.checkReverseSign(request);
				if("Y".equals(msg)){
					response.setIfSuccess(true);
					response.setErrorMsg("自动反签收成功success!");
					
					interfaceEntity.setIsSuccess("Y");
					interfaceEntity.setCorrectLog("自动反签收成功success!");
				}
			}
			
			//反结清校验+处理
			if("unsettle".equals(reverseType)){
			  LOGGER.info("vts反结清校验开始！");
			  msg = reverseSignSettleService.checkReverseSettle(request);
				if("Y".equals(msg)){
					response.setIfSuccess(true);
					response.setErrorMsg("自动反结清成功success!");
					
					interfaceEntity.setIsSuccess("Y");
					interfaceEntity.setCorrectLog("自动反结清成功success!");
				}
			}
			
		}catch(SettlementException se){
			se.printStackTrace();
			LOGGER.info("SettlementException层："+se.getMessage()+"单号为："+waybillNo);
			response.setIfSuccess(false);
			resp.setHeader("ESB-ResultCode", "1");
			response.setErrorMsg("反签收反结清处理异常:"+se.getErrorCode());
			
			StringWriter writer = new StringWriter();
			se.printStackTrace(new PrintWriter(writer));
			interfaceEntity.setErrorLog("反签收反结清失败，失败原因是：" + writer.toString());
			interfaceEntity.setIsSuccess("N");
		}catch (BusinessException ex) {
			ex.printStackTrace();
			LOGGER.info("BusinessException层："+ex.getMessage()+"单号为："+waybillNo);
			response.setIfSuccess(false);
			resp.setHeader("ESB-ResultCode", "1");
			response.setErrorMsg("反签收反结清处理异常:"+ex.getErrorCode());
			
			StringWriter writer = new StringWriter();
			ex.printStackTrace(new PrintWriter(writer));
			interfaceEntity.setErrorLog("反签收反结清失败，失败原因是：" + writer.toString());
			interfaceEntity.setIsSuccess("N");
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.info("Exception层："+e.getMessage()+"单号为："+waybillNo);
			response.setIfSuccess(false);
			resp.setHeader("ESB-ResultCode", "1");
			response.setErrorMsg("反签收反结清处理异常:"+e);
			
			StringWriter writer = new StringWriter();
			e.printStackTrace(new PrintWriter(writer));
			interfaceEntity.setErrorLog("反签收反结清失败，失败原因是：" + writer.toString());
		}finally{
			//插入接口日志表
			this.esbInterfaceLogService.addInterfaceLog(interfaceEntity);
		}
		LOGGER.info("VTS自动反签收反结清FOSS响应成功!" + "单号为：" + waybillNo);
		
		return JSON.toJSONString(response);
		
	}

	public HttpServletRequest getReq() {
		return req;
	}

	public void setReq(HttpServletRequest req) {
		this.req = req;
	}

	public HttpServletResponse getResp() {
		return resp;
	}

	public void setResp(HttpServletResponse resp) {
		this.resp = resp;
	}

	public void setReverseSignSettleService(
			IReverseSignSettleService reverseSignSettleService) {
		this.reverseSignSettleService = reverseSignSettleService;
	}

	public void setEsbInterfaceLogService(
			IEsbInterfaceLogService esbInterfaceLogService) {
		this.esbInterfaceLogService = esbInterfaceLogService;
	}

}
