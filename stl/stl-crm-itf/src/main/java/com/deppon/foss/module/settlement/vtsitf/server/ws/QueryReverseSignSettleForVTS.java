package com.deppon.foss.module.settlement.vtsitf.server.ws;

import java.io.PrintWriter;
import java.io.StringWriter;
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
import com.deppon.foss.module.settlement.common.api.shared.domain.InterfaceLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSResverSettleRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSReverseSettlRequest;
import com.deppon.foss.module.settlement.common.api.shared.dto.VTSReverseSettlResponse;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.vtsitf.server.inter.IQueryReverseSignSettleForVTS;
import com.deppon.foss.util.UUIDUtils;

/**
 * @author 218392 张永雪
 * @date 2016-06-17 06:33:20
 * VTS查询反结清
 */
public class QueryReverseSignSettleForVTS implements IQueryReverseSignSettleForVTS{

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryReverseSignSettleForVTS.class);
	
	/**
	 * 注入反签收反结清的Service
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
	
	//查询反结清：查询到达付款信息
	@Override
	public String queryReverseSettle(String checkReverseSignSettle) {
		//转换请求
		JSONObject object = (JSONObject) JSONObject.parseObject(checkReverseSignSettle).get("requestEntity");
		VTSReverseSettlRequest request = JSONObject.toJavaObject(object, VTSReverseSettlRequest.class);
		//响应
		VTSReverseSettlResponse response = new VTSReverseSettlResponse();
		
		/**
		 * 定义插入log日志插入结算VTS专门的日志表
		 * stl.t_stl_bill_interfacelog
		 */
		InterfaceLogEntity interfaceEntity = new InterfaceLogEntity();
		
		try{
			if(request == null || StringUtils.isEmpty(request.getWaybillNo())){
				throw new SettlementException("反签收查询参数为空！");
			}
			String waybillNo = request.getWaybillNo();
			LOGGER.info("vts反签收查询开始单号为：" + waybillNo);
			interfaceEntity.setId(UUIDUtils.getUUID());
			interfaceEntity.setWaybillNo(waybillNo);
			interfaceEntity.setEsbCode("FOSS_ESB2FOSS_VTS_TO_FOSS_AUTO_QUERY_UNSETTLE");//服务端编码:反签收查询
			interfaceEntity.setSystemType("VTS");
			interfaceEntity.setSendContent(JSONObject.toJSONString(request));
			interfaceEntity.setCreateUser("218392");
			interfaceEntity.setModifyUser("自动反签查询rest接口");
			interfaceEntity.setCreateTime(new Date());

			/**
			 * 查询要做的步骤
			 * （1）首先校验运单是否存在；
			 * （2）校验登陆部门，是否等于运单表中的customer_pickup_org_code 最终到达部门
			 * （3） 被资金复核组锁定，如需操作，请联系资金复核组进行解锁
			 */
			//校验+查询反结清数据业务处理
			List<VTSResverSettleRepaymentEntity> vTSResverSettleRepaymentEntity = reverseSignSettleService.handleReverseSign(request);
			response.setIfSuccess(true);
			response.setErrorMsg("成功");
			response.setResverSettleRepaymentEntity(vTSResverSettleRepaymentEntity);
			LOGGER.info("vts反签收查询结束单号为：" + waybillNo);
			interfaceEntity.setIsSuccess("Y");
			interfaceEntity.setCorrectLog("VTS查询成功!");
			resp.setHeader("ESB-ResultCode", "1");
		}catch(SettlementException se){
			se.printStackTrace();
			LOGGER.info("SettlementException层："+se.getMessage());
			response.setIfSuccess(false);
			resp.setHeader("ESB-ResultCode", "1");
			response.setErrorMsg("查询失败:"+se.getErrorCode());
			
			StringWriter writer = new StringWriter();
			se.printStackTrace(new PrintWriter(writer));
			interfaceEntity.setErrorLog("反签收查询失败，失败原因是：" + writer.toString());
			interfaceEntity.setIsSuccess("N");
		}catch (BusinessException ex) {
			ex.printStackTrace();
			LOGGER.info("BusinessException层："+ex.getMessage());
			response.setIfSuccess(false);
			resp.setHeader("ESB-ResultCode", "1");
			response.setErrorMsg("查询失败:"+ex.getErrorCode());
			
			StringWriter writer = new StringWriter();
			ex.printStackTrace(new PrintWriter(writer));
			interfaceEntity.setErrorLog("反签收查询失败，失败原因是：" + writer.toString());
			interfaceEntity.setIsSuccess("N");
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.info("Exception层："+e.getMessage());
			response.setIfSuccess(false);
			resp.setHeader("ESB-ResultCode", "1");
			response.setErrorMsg("查询失败:"+e);
			
			StringWriter writer = new StringWriter();
			e.printStackTrace(new PrintWriter(writer));
			interfaceEntity.setErrorLog("反签收查询失败，失败原因是：" + writer.toString());
			interfaceEntity.setIsSuccess("N");
		}finally{
			//插入接口日志表
			this.esbInterfaceLogService.addInterfaceLog(interfaceEntity);
		}
		
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
