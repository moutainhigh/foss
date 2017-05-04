package com.deppon.foss.module.settlement.ecsitf.server.rs.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.WebApplicationContextHolder;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.agency.api.server.service.ITruckStowageService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.StlVehicleAssembleBillDto;
import com.deppon.foss.module.settlement.closing.api.shared.inteface.domain.EcsLongHandoverBillRequest;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.ecsitf.server.rs.IEcsTruckStowageService;

/**
 * 快递系统新增、修改、作废长途交接单时，相关财务单据处理接口
 * @author foss-231434-bieyexiong
 * @date 2016-4-14 20:08
 */
public class EcsTruckStowageService implements IEcsTruckStowageService{

	//成功返回1
	private static final String SUCCESS = "1";

	//失败返回0
	private static final String FAILURE = "0";

	@Context
	HttpServletResponse res;
	
	//快递系统新增、修改、作废长途交接单时，相关财务单据处理接口
	private IEcsTruckStowageService ecsTruckStowageService;
	
	//外请车配载服务（长途交接单服务）
	private ITruckStowageService truckStowageService;
	
	//应付单服务
	private IBillPayableService billPayableService;
	
	/**
	 * 生成快递长途交接单（类似零担配载单）
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-14 20:08
	 */
	@Override
	public @ResponseBody String addTruckStowage(@RequestBody String jsonReq) {
		try{
			res.setHeader("ESB-ResultCode" , "1");
			//将请求参数转换成实体对象
			EcsLongHandoverBillRequest req = JSONObject.parseObject(jsonReq,EcsLongHandoverBillRequest.class);

			if(req == null){
				return this.getResponse(FAILURE, "结算生成长途交接单失败：生成财务单据的参数为空！");
			}
			if(StringUtils.isBlank(req.getEmpCode())
					|| StringUtils.isBlank(req.getEmpName())
					|| StringUtils.isBlank(req.getCurrentDeptCode())
					|| StringUtils.isBlank(req.getCurrentDeptName())){
				return this.getResponse(FAILURE, "结算生成长途交接单失败：交接员信息为空！");
			}

			//设置参数
			StlVehicleAssembleBillDto dto = this.getStlVehicleDto(req);
			CurrentInfo currentInfo = SettlementUtil.getECSCurrentInfo(req.getEmpCode(), req.getEmpName(), req.getCurrentDeptCode(), req.getCurrentDeptName());

			//生成交接单财务单据
			ecsTruckStowageService.addTruckStowage(dto, currentInfo);
			return this.getResponse(SUCCESS, "结算生成长途交接单成功！");
		}catch(BusinessException e){
			return this.getResponse(FAILURE, "结算生成长途交接单失败：" + (e.getErrorCode() != null ? e.getErrorCode() : e.getMessage()));
		}catch(Exception e){
			return this.getResponse(FAILURE, "结算生成长途交接单失败：系统异常，请重新操作，以校验财务单据！："+e.getMessage());
		}
	}
	

	/**
	 * 修改、作废长途交接单 校验应付单接口
	 * @author foss-231434-bieyexiong
	 * @date 2016-5-03 11:26
	 */
	@Override
	public @ResponseBody String checkPayableIsWriteOff(@RequestBody String jsonReq) {
		try{
			res.setHeader("ESB-ResultCode" , "1");
			
			JSONObject obj = JSONObject.parseObject(jsonReq);
			
			if(obj == null){
				return this.getResponse(FAILURE,"结算校验应付单未通过:交接单号为空");
			}
			String sourceBillNo = obj.getString("vehicleAssembleNo");
			
			boolean result = billPayableService.queryBillPayableIsWriteOff(sourceBillNo,null);
			if(result){
				return this.getResponse(FAILURE, "结算校验应付单未通过:该交接单已做付款确认!");
			}else{
				return this.getResponse(SUCCESS, "结算校验应付单通过");
			}
			
		}catch(BusinessException e){
			return this.getResponse(FAILURE, "结算校验应付单未通过：" + (e.getErrorCode() != null ? e.getErrorCode() : e.getMessage()));
		}catch(Exception e){
			return this.getResponse(FAILURE, "结算校验应付单未通过：系统异常，请重新操作，以校验财务单据！："+e.getMessage());
		}
	}


	/**
	 * 修改快递长途交接单（类似零担配载单）
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-14 20:08
	 */
	@Override
	public @ResponseBody String modifyTruckStowage(@RequestBody String jsonReq) {
		try{
			res.setHeader("ESB-ResultCode" , "1");
			//将请求参数转换成实体对象
			EcsLongHandoverBillRequest req = JSONObject.parseObject(jsonReq,EcsLongHandoverBillRequest.class);

			if(req == null){
				return this.getResponse(FAILURE, "结算修改长途交接单失败：修改财务单据的参数为空！");
			}
			
			if(StringUtils.isBlank(req.getEmpCode())
					|| StringUtils.isBlank(req.getEmpName())
					|| StringUtils.isBlank(req.getCurrentDeptCode())
					|| StringUtils.isBlank(req.getCurrentDeptName())){
				return this.getResponse(FAILURE, "结算修改长途交接单失败：交接员信息为空！");
			}
			
			//设置参数
			StlVehicleAssembleBillDto dto = this.getStlVehicleDto(req);
			CurrentInfo currentInfo = SettlementUtil.getECSCurrentInfo(req.getEmpCode(), req.getEmpName(), req.getCurrentDeptCode(), req.getCurrentDeptName());
			
			//修改交接单财务单据
			ecsTruckStowageService.modifyTruckStowage(dto, currentInfo);
			return this.getResponse(SUCCESS, "结算修改长途交接单成功！");
		}catch(BusinessException e){
			return this.getResponse(FAILURE, "结算修改长途交接单失败：" + (e.getErrorCode() != null ? e.getErrorCode() : e.getMessage()));
		}catch(Exception e){
			return this.getResponse(FAILURE, "结算修改长途交接单失败：系统异常，请重新操作，以校验财务单据！："+e.getMessage());
		}
	}

	/**
	 * 作废快递长途交接单（类似零担配载单）
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-14 20:08
	 */
	@Override
	public @ResponseBody String disableTruckStowage(@RequestBody String jsonReq) {
		try{
			res.setHeader("ESB-ResultCode" , "1");
			//将请求参数转换成实体对象
			EcsLongHandoverBillRequest req = JSONObject.parseObject(jsonReq,EcsLongHandoverBillRequest.class);
			
			if(req == null){
				return this.getResponse(FAILURE, "结算作废长途交接单失败：作废财务单据的参数为空！");
			}
			
			if(StringUtils.isBlank(req.getEmpCode())
					|| StringUtils.isBlank(req.getEmpName())
					|| StringUtils.isBlank(req.getCurrentDeptCode())
					|| StringUtils.isBlank(req.getCurrentDeptName())){
				return this.getResponse(FAILURE, "结算作废长途交接单失败：交接员信息为空！");
			}
			
			//设置参数
			StlVehicleAssembleBillDto dto = this.getStlVehicleDto(req);
			CurrentInfo currentInfo = SettlementUtil.getECSCurrentInfo(req.getEmpCode(), req.getEmpName(), req.getCurrentDeptCode(), req.getCurrentDeptName());
			
			//作废长途交接单
			ecsTruckStowageService.disableTruckStowage(dto, currentInfo);
			
			return this.getResponse(SUCCESS, "结算作废长途交接单成功！");
		}catch(BusinessException e){
			return this.getResponse(FAILURE, "结算作废长途交接单失败：" + (e.getErrorCode() != null ? e.getErrorCode() : e.getMessage()));
		}catch(Exception e){
			return this.getResponse(FAILURE, "结算作废长途交接单失败：系统异常，请重新操作，以校验财务单据！："+e.getMessage());
		}
	}

	/**
	 * 获取响应信息
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-20 17:27
	 */
	private String getResponse(String result,String message){
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("result", result);
		map.put("message", replaceMsg(message));
		String response = JSONObject.toJSONString(map);
		return response;
	}
	
	/**
	 * 替换提示信息(将 ‘配载’ 替换成 ‘交接’)
	 * @author foss-231434-bieyexiong
	 * @date 2016-5-16 15:04
	 */
	private String replaceMsg(String message){
		message = message.replaceAll("配载", "交接");
		return message;
	}
	
	/**
	 * 设置请求信息
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-20 17:50
	 */
	private StlVehicleAssembleBillDto getStlVehicleDto(EcsLongHandoverBillRequest req){
		StlVehicleAssembleBillDto dto = new StlVehicleAssembleBillDto();
		
		//交接单号
		dto.setVehicleAssembleNo(req.getVehicleAssembleNo());
		//车辆所有权类别 ，如果是公司车不允许调用此接口
		dto.setVehicleOwnerShip(req.getVehicleOwnerShip());
		//车牌号
		dto.setVehicleNo(req.getVehicleNo());
		//预付运费总额
		dto.setPrePaidFeeTotal(req.getPrePaidFeeTotal());
		//付款方式
		dto.setPaymentType(req.getPaymentType());
		//出发部门编码
		dto.setOrigOrgCode(req.getOrigOrgCode());
		//出发部门名称
		dto.setOrigOrgName(req.getOrigOrgName());
		//车辆出发日期
		dto.setLeaveTime(req.getLeaveTime());
		//总运费
		dto.setFeeTotal(req.getFeeTotal());
		//司机编码
		dto.setDriverCode(req.getDriverCode());
		//司机姓名
		dto.setDriverName(req.getDriverName());
		//到达部门编码
		dto.setDestOrgCode(req.getDestOrgCode());
		//到达部门名称
		dto.setDestOrgName(req.getDestOrgName());
		//币种
		dto.setCurrencyCode(req.getCurrencyCode());
		//是否押回单（否）
		dto.setBeReturnReceipt(req.getBeReturnReceipt());
		//到付运费总额
		dto.setArriveFeeTotal(req.getArriveFeeTotal());
		//配载类型（专线）
		dto.setAssembleType(req.getAssembleType());
		
		return dto;
	}

	
	@Transactional
	@Override
	public void addTruckStowage(StlVehicleAssembleBillDto dto,CurrentInfo currentInfo) {
		truckStowageService.addTruckStowage(dto, currentInfo);
	}
	
	@Transactional
	@Override
	public void modifyTruckStowage(StlVehicleAssembleBillDto dto,CurrentInfo currentInfo) {
		truckStowageService.modifyTruckStowage(dto, currentInfo);
	}
	
	@Transactional
	@Override
	public void disableTruckStowage(StlVehicleAssembleBillDto dto,CurrentInfo currentInfo) {
		truckStowageService.disableTruckStowage(dto, currentInfo);
	}

	public void setEcsTruckStowageService(
			IEcsTruckStowageService ecsTruckStowageService) {
		this.ecsTruckStowageService = WebApplicationContextHolder.getWebApplicationContext().
				getBean("ecsTruckStowageService",IEcsTruckStowageService.class);
	}

	public void setTruckStowageService(ITruckStowageService truckStowageService) {
		this.truckStowageService = truckStowageService;
	}


	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

}
