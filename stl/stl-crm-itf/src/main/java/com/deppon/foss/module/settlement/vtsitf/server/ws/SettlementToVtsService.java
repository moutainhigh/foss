package com.deppon.foss.module.settlement.vtsitf.server.ws;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IEsbInterfaceLogService;
import com.deppon.foss.module.settlement.common.api.shared.domain.InterfaceLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IVtsValidateAndSettlementService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.SettlementPayToVtsDto;
import com.deppon.foss.module.settlement.vtsitf.server.inter.ISettlementToVtsService;
import com.deppon.foss.util.UUIDUtils;

/** 
 * 
 * @author foss结算-306579-guoxinru 
 * @date 2016-5-5 上午10:55:07    
 */
public class SettlementToVtsService implements ISettlementToVtsService{

	/**
	 * 日志
	 */
	private static final Logger logger = LogManager.getLogger(SettlementToVtsService.class);
	
	/**
	 * 注入接口日志service
	 */
	private IEsbInterfaceLogService esbInterfaceLogService;

	@Context
	HttpServletResponse response;
	@Context
	HttpServletRequest request;

	/**
	 * 注入结清货款Service
	 */
	private IVtsValidateAndSettlementService vtsValidateAndSettlementService; 
	
	/**
	 * VTS结清货款
	 */
	@SuppressWarnings("finally")
	@Override
	public String confirmToPayForVts(String settlementPayToVtsDto) {

		logger.info("VTS调用foss财务校验及结清货款开始...");
		
		SettlementPayToVtsDto resultDto = new SettlementPayToVtsDto();
		
		SettlementPayToVtsDto dto = new SettlementPayToVtsDto();
		
		/**
		 * 定义插入log日志插入结算VTS专门的日志表
		 * stl.t_stl_bill_interfacelog
		 */
		InterfaceLogEntity interfaceEntity = new InterfaceLogEntity();

		try{
			if(StringUtils.isEmpty(settlementPayToVtsDto)){
				throw new SettlementException("vts传入参数为空！");
			}
			logger.info("vts传入参数："+settlementPayToVtsDto);
			//解析传入json，封装实体类
			JSONObject object = JSONObject.parseObject(settlementPayToVtsDto);
		    dto = object.getObject("requestEntity", SettlementPayToVtsDto.class);
			
			//vts传入校验参数判空
			if(null==dto){
				throw new SettlementException("vts传入校验参数为空！");
			}
			
			//vts传入登陆用户参数判空
			if(StringUtils.isEmpty(dto.getEmpCode())){
				throw new SettlementException("vts传入用户编码为空！");
			}
			if(StringUtils.isEmpty(dto.getEmpName())){
				throw new SettlementException("vts传入用户名称为空！");
			}
			if(StringUtils.isEmpty(dto.getCurrentDeptCode())){
				throw new SettlementException("vts传入部门编码为空！");
			}
			if(StringUtils.isEmpty(dto.getCurrentDeptName())){
				throw new SettlementException("vts传入部门名称为空！");
			}
			if(StringUtils.isEmpty(dto.getBusinessId())){
				throw new SettlementException("VTS传入的BusinessId为空！");
			}
			
			interfaceEntity.setId(UUIDUtils.getUUID());
			interfaceEntity.setWaybillNo(dto.getWaybillNo());
			interfaceEntity.setEsbCode("FOSS_ESB2FOSS_CONFIRMTOPAYFORVTS");//服务端编码:自动反结清
			interfaceEntity.setSystemType("VTS");
			interfaceEntity.setSendContent(JSONObject.toJSONString(dto));
			interfaceEntity.setCreateUser("218392");
			interfaceEntity.setModifyUser("结清货款rest接口");
			interfaceEntity.setCreateTime(new Date());
			
			//从前台获取登录用户信息封装至CurrentInfo
			UserEntity user = new UserEntity();
			OrgAdministrativeInfoEntity deptInfo = new OrgAdministrativeInfoEntity();
			EmployeeEntity employeeEntity = new EmployeeEntity();
			user.setEmployee(employeeEntity);
			user.getEmployee().setEmpCode(dto.getEmpCode());
			user.getEmployee().setEmpName(dto.getEmpName());
			deptInfo.setCode(dto.getCurrentDeptCode());
			deptInfo.setName(dto.getCurrentDeptName());
			
			CurrentInfo currentInfo = new CurrentInfo(user, deptInfo);
			
			//运单号判空
			if(StringUtils.isEmpty(dto.getWaybillNo())){
				throw new SettlementException("vts传入运单号为空！");
			}
			
			//付款方式判空
			if(StringUtils.isEmpty(dto.getPaymentType())){
				throw new SettlementException("vts传入付款方式为空！");
			}
			
			//提货人姓名判空
			if(StringUtils.isEmpty(dto.getDeliverymanName())){
				throw new SettlementException("vts传入提货人姓名为空！");
			}
			
			//证件类型判空
			if(StringUtils.isEmpty(dto.getIdentifyType())){
				throw new SettlementException("vts传入证件类型为空！");
			}
			
			//证件号码判空
			if(StringUtils.isEmpty(dto.getIdentifyCode())){
				throw new SettlementException("vts传入证件号码为空！");
			}
				
			 this.vtsValidateAndSettlementService.ValidateAndSettlement(dto,currentInfo); 
			 
			 //20160811 354830 孙小雪  FOSS20160906 查询结清状态返回VTS
			 String settlementStatus = this.vtsValidateAndSettlementService.querySettlementStatus(dto.getWaybillNo());  
			 resultDto.setSettleStatus(settlementStatus);       
			
			 resultDto.setIsSuccess(true);
			 resultDto.setMsg("VTS调用foss财务校验及结清货款成功...运单号："+dto.getWaybillNo());
			 logger.info("VTS调用foss财务校验及结清货款成功...运单号："+dto.getWaybillNo());
			 interfaceEntity.setIsSuccess("Y");
			 interfaceEntity.setCorrectLog("结清货款成功(非现金结清)success!");
			
		} catch(SettlementException e1){
			resultDto.setIsSuccess(false);
			resultDto.setMsg(e1.getErrorCode());
			logger.info("vts调用foss财务校验/结清货款/快速结清异常,运单号："+dto.getWaybillNo());
			StringWriter writer = new StringWriter();
			e1.printStackTrace(new PrintWriter(writer));
			interfaceEntity.setErrorLog("结清货款失败，失败原因是：" + writer.toString());
			interfaceEntity.setIsSuccess("N");
		} catch (BusinessException e1) {
			resultDto.setIsSuccess(false);
			resultDto.setMsg(e1.getErrorCode());
			logger.info("vts调用foss财务校验/结清货款/快速结清异常,运单号："+dto.getWaybillNo());
			StringWriter writer = new StringWriter();
			e1.printStackTrace(new PrintWriter(writer));
			interfaceEntity.setErrorLog("结清货款失败，失败原因是：" + writer.toString());
			interfaceEntity.setIsSuccess("N");
		}catch (Exception e2) {
			resultDto.setIsSuccess(false);
			resultDto.setMsg(e2.getMessage());
			logger.info("vts调用foss财务校验/结清货款/快速结清异常,运单号："+dto.getWaybillNo());
			StringWriter writer = new StringWriter();
			e2.printStackTrace(new PrintWriter(writer));
			interfaceEntity.setErrorLog("结清货款失败，失败原因是：" + writer.toString());
			interfaceEntity.setIsSuccess("N");
		} finally {
			response.addHeader("ESB-ResultCode", "1");
			logger.info("VTS调用foss财务校验/结清货款/快速结清结束");
			//插入接口日志表
			this.esbInterfaceLogService.addInterfaceLog(interfaceEntity);
			return JSONObject.toJSONString(resultDto);
		}
	}
	
	public void setVtsValidateAndSettlementService(
			IVtsValidateAndSettlementService vtsValidateAndSettlementService) {
		this.vtsValidateAndSettlementService = vtsValidateAndSettlementService;
	}

	public void setEsbInterfaceLogService(
			IEsbInterfaceLogService esbInterfaceLogService) {
		this.esbInterfaceLogService = esbInterfaceLogService;
	}
	
	
}
