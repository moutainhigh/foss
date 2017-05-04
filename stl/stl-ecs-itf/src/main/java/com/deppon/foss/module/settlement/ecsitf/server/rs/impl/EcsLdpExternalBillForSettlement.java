package com.deppon.foss.module.settlement.ecsitf.server.rs.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import com.deppon.foss.framework.server.context.WebApplicationContextHolder;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.EcsResponseDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.settlement.agency.api.server.service.IVehicleAgencyExternalLdpService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.EcsSettlementLdpExternalBillRequestDto;
import com.deppon.foss.module.settlement.agency.api.shared.dto.SettlementLdpExternalBillDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.util.ECSCurrentInfoUtil;
import com.deppon.foss.module.settlement.ecsitf.server.rs.IEcsLdpExternalBillForSettlement;
import com.deppon.foss.util.CollectionUtils;

/**
 * 快递 外发单业务调用相关结算接口
 * @author 243921-zhangtingting
 * @date 2016-04-21 上午9:44:08
 */
public class EcsLdpExternalBillForSettlement implements
		IEcsLdpExternalBillForSettlement {
	//日志
	private final Logger logger = LogManager.getLogger(EcsSignForSettlement.class);
	
	//偏线快递代理外发单录入、修改、审核、反审核、作废服务
	private IVehicleAgencyExternalLdpService vehicleAgencyExternalLdpService;
	
	@Context
	HttpServletResponse res;

	//快递 外发单业务调用相关结算接口
	private IEcsLdpExternalBillForSettlement ecsLdpExternalBillForSettlement;
	
	private IWaybillManagerService waybillManagerService;

	/**
	 * 新增外发单服务 结算接口
	 * @param jsonStr
	 * @return
	 */
	@Override
	public @ResponseBody String addExternalBill(String jsonStr) {

		res.setHeader("ESB-ResultCode", "1");
		
		//生成返回实体
		String response = "";
		
		if(StringUtils.isBlank(jsonStr)){
			// 记录日志
			logger.info("新增外发单，调用结算接口失败：请求参数为空！");
			
			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "新增外发单，调用结算接口失败：请求参数为空！");

			return response;
		}
		//请求的参数转换为DTO
		EcsSettlementLdpExternalBillRequestDto request = JSONObject.parseObject(jsonStr, EcsSettlementLdpExternalBillRequestDto.class);
		try {
			logger.info("调用结算接口开始"+"运单号："+request.getWaybillNo()); 
			
			//外发成本总额【应付费用】
			//add by 329757 FOSS结算放开对外发运费为0的校验
			ecsLdpExternalBillForSettlement.addExternalBill(request);
			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_SUCESS, "新增外发单，调用结算接口成功！");
			
//			if (request.getCostAmount() != null
//					&& request.getCostAmount().compareTo(BigDecimal.ZERO) > 0) {
//				
//				ecsLdpExternalBillForSettlement.addExternalBill(request);
//				
//				response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_SUCESS, "新增外发单，调用结算接口成功！");
//			}else{
//				response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "新增外发单，调用结算接口失败:外发成本总额不能为0！");
//			}

			logger.info("调用结算接口结束。"); 
		} catch (SettlementException se){
			// 记录日志
			logger.info("新增外发单，调用结算接口失败：" + se.getErrorCode());
			// 日志处理
			logger.error(se.getErrorCode(), se);

			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "新增外发单，调用结算接口失败："+se.getErrorCode());
		} catch (Exception e) {
			// 记录日志
			logger.info("新增外发单，调用结算接口失败：" + e.getMessage());
			// 日志处理
			logger.error(e.getMessage(), e);

			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "新增外发单，调用结算接口失败："+e.getMessage());
		}
		
		return response;
	}
	
	/**
	 * 修改外发单服务 结算接口
	 * @param jsonStr
	 * @return
	 */
	@Override
	public @ResponseBody String modifyExternalBill(String jsonStr) {
		
		res.setHeader("ESB-ResultCode", "1");
		
		//生成返回实体
		String response = "";
		
		if(StringUtils.isBlank(jsonStr)){
			// 记录日志
			logger.info("修改外发单，调用结算接口失败：请求参数为空！");
			
			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "修改外发单，调用结算接口失败：请求参数为空！");

			return response;
		}
		//请求的参数转换为DTO
		EcsSettlementLdpExternalBillRequestDto request = JSONObject.parseObject(jsonStr, EcsSettlementLdpExternalBillRequestDto.class);
		try {
			logger.info("调用结算接口开始"+"运单号："+request.getWaybillNo());
			
			ecsLdpExternalBillForSettlement.modifyExternalBill(request);
			
			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_SUCESS, "修改外发单，调用结算接口成功！");

			logger.info("调用结算接口结束。"); 
		} catch (SettlementException se){
			// 记录日志
			logger.info("修改外发单，调用结算接口失败：" + se.getErrorCode());
			// 日志处理
			logger.error(se.getErrorCode(), se);

			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "修改外发单，调用结算接口失败："+se.getErrorCode());
		} catch (Exception e) {
			// 记录日志
			logger.info("修改外发单，调用结算接口失败：" + e.getMessage());
			// 日志处理
			logger.error(e.getMessage(), e);

			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "修改外发单，调用结算接口失败："+e.getMessage());
		}
		
		return response;
	}

	/**
	 * 审核外发单服务 结算接口
	 * @param jsonStr
	 * @return
	 */
	@Override
	public @ResponseBody String auditExternalBill(String jsonStr) {

		res.setHeader("ESB-ResultCode", "1");
		
		//生成返回实体
		String response = "";
		
		if(StringUtils.isBlank(jsonStr)){
			// 记录日志
			logger.info("审核外发单，调用结算接口失败：请求参数为空！");
			
			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "审核外发单，调用结算接口失败：请求参数为空！");

			return response;
		}
		
		//请求的参数转换为DTO
		List<EcsSettlementLdpExternalBillRequestDto> request = JSONObject.parseArray(jsonStr, EcsSettlementLdpExternalBillRequestDto.class);
		try {
			logger.info("调用结算接口开始........");
			// add by 329757 添加悟空外发单校验
			for (EcsSettlementLdpExternalBillRequestDto ecsSettlementLdpExternalBillRequestDto : request) {
				ecsSettlementLdpExternalBillRequestDto.setWukong(true);
			}

			ecsLdpExternalBillForSettlement.auditExternalBill(request);
			
			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_SUCESS, "审核外发单，调用结算接口成功！");

			logger.info("调用结算接口结束。"); 
		} catch (SettlementException se){
			// 记录日志
			logger.info("审核外发单，调用结算接口失败：" + se.getErrorCode());
			// 日志处理
			logger.error(se.getErrorCode(), se);

			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "审核外发单，调用结算接口失败："+se.getErrorCode());
		} catch (Exception e) {
			// 记录日志
			logger.info("审核外发单，调用结算接口失败：" + e.getMessage());
			// 日志处理
			logger.error(e.getMessage(), e);

			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "审核外发单，调用结算接口失败："+e.getMessage());
		}
		
		return response;
	}

	/**
	 * 作废外发单服务 结算接口
	 * @param jsonStr
	 * @return
	 */
	@Override
	public @ResponseBody String disableExternalBill(String jsonStr) {
		
		res.setHeader("ESB-ResultCode", "1");
		
		//生成返回实体
		String response = "";
		
		if(StringUtils.isBlank(jsonStr)){
			// 记录日志
			logger.info("作废外发单，调用结算接口失败：请求参数为空！");
			
			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "作废外发单，调用结算接口失败：请求参数为空！");

			return response;
		}
		
		//请求的参数转换为DTO
		List<EcsSettlementLdpExternalBillRequestDto> request = JSONObject.parseArray(jsonStr, EcsSettlementLdpExternalBillRequestDto.class);
		try {
			logger.info("调用结算接口开始.....");

			ecsLdpExternalBillForSettlement.disableExternalBill(request);
			
			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_SUCESS, "作废外发单，调用结算接口成功！");

			logger.info("调用结算接口结束。"); 
		} catch (SettlementException se){
			// 记录日志
			logger.info("作废外发单，调用结算接口失败：" + se.getErrorCode());
			// 日志处理
			logger.error(se.getErrorCode(), se);

			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "作废外发单，调用结算接口失败："+se.getErrorCode());
		} catch (Exception e) {
			// 记录日志
			logger.info("作废外发单，调用结算接口失败：" + e.getMessage());
			// 日志处理
			logger.error(e.getMessage(), e);

			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "作废外发单，调用结算接口失败："+e.getMessage());
		}
		
		return response;
	}

	/**
	 * 反审核外发单服务 结算接口
	 * @param jsonStr
	 * @return
	 */
	@Override
	public @ResponseBody String reverseAuditExternalBill(String jsonStr) {
		
		res.setHeader("ESB-ResultCode", "1");
		
		//生成返回实体
		String response = "";
		
		if(StringUtils.isBlank(jsonStr)){
			// 记录日志
			logger.info("反审核外发单，调用结算接口失败：请求参数为空！");
			
			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "反审核外发单，调用结算接口失败：请求参数为空！");

			return response;
		}
		
		//请求的参数转换为DTO
		List<EcsSettlementLdpExternalBillRequestDto> request = JSONObject.parseArray(jsonStr, EcsSettlementLdpExternalBillRequestDto.class);
		try {
			logger.info("调用结算接口开始....");
			//添加悟空外发单检验
			for (EcsSettlementLdpExternalBillRequestDto ecsSettlementLdpExternalBillRequestDto : request) {
				ecsSettlementLdpExternalBillRequestDto.setWukong(true);
			}

			ecsLdpExternalBillForSettlement.reverseAuditExternalBill(request);
			
			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_SUCESS, "反审核外发单，调用结算接口成功！");

			logger.info("调用结算接口结束。"); 
		} catch (SettlementException se){
			// 记录日志
			logger.info("反审核外发单，调用结算接口失败：" + se.getErrorCode());
			// 日志处理
			logger.error(se.getErrorCode(), se);

			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "反审核外发单，调用结算接口失败："+se.getErrorCode());
		} catch (Exception e) {
			// 记录日志
			logger.info("反审核外发单，调用结算接口失败：" + e.getMessage());
			// 日志处理
			logger.error(e.getMessage(), e);

			response = EcsResponseDto.getEcsResponseDto(SettlementConstants.RETURN_FAILURE, "反审核外发单，调用结算接口失败："+e.getMessage());
		}
		
		return response;
	}

	/**
	 * 调用结算接口，添加事务控制
	 */

	//新增外发单服务
	@Override
	@Transactional
	public void addExternalBill(EcsSettlementLdpExternalBillRequestDto dto) {

		vehicleAgencyExternalLdpService.addExternalBill(this.getSettlementLdpExternalBillDto(dto),
				ECSCurrentInfoUtil.getCurrentInfo(dto.getEmpCode(),dto.getEmpName(),
						dto.getCurrentDeptCode(),dto.getCurrentDeptName()));
	}

	//修改外发单服务
	@Override
	@Transactional
	public void modifyExternalBill(EcsSettlementLdpExternalBillRequestDto dto) {

		vehicleAgencyExternalLdpService.modifyExternalBill(this.getSettlementLdpExternalBillDto(dto),
				ECSCurrentInfoUtil.getCurrentInfo(dto.getEmpCode(),dto.getEmpName(),
						dto.getCurrentDeptCode(),dto.getCurrentDeptName()));
	}

	//审核外发单服务
	@Override
	@Transactional
	public void auditExternalBill(List<EcsSettlementLdpExternalBillRequestDto> list) {

		vehicleAgencyExternalLdpService.auditExternalBill(this.getList(list),
				ECSCurrentInfoUtil.getCurrentInfo(list.get(0).getEmpCode(),list.get(0).getEmpName(),
						list.get(0).getCurrentDeptCode(),list.get(0).getCurrentDeptName()));
	}

	//作废外发单服务
	@Override
	@Transactional
	public void disableExternalBill(List<EcsSettlementLdpExternalBillRequestDto> list) {

		vehicleAgencyExternalLdpService.disableExternalBill(this.getList(list),
				ECSCurrentInfoUtil.getCurrentInfo(list.get(0).getEmpCode(),list.get(0).getEmpName(),
						list.get(0).getCurrentDeptCode(),list.get(0).getCurrentDeptName()));
	}

	//反审核外发单服务
	@Override
	@Transactional
	public void reverseAuditExternalBill(List<EcsSettlementLdpExternalBillRequestDto> list) {

		vehicleAgencyExternalLdpService.reverseAuditExternalBill(this.getList(list),
				ECSCurrentInfoUtil.getCurrentInfo(list.get(0).getEmpCode(),list.get(0).getEmpName(),
						list.get(0).getCurrentDeptCode(),list.get(0).getCurrentDeptName()));
	}

	//外发单 根据请求的参数，转换成对应接口中的dto
	private SettlementLdpExternalBillDto getSettlementLdpExternalBillDto(EcsSettlementLdpExternalBillRequestDto request){
		SettlementLdpExternalBillDto dto = new SettlementLdpExternalBillDto();
		//add by 329757
		dto.setWukong(true);
		//运单号
		dto.setWaybillNo(request.getWaybillNo());
		//付款方式    
		dto.setPaidMethod(request.getPaidMethod());
		//外发部门//制单人所在的部门
		dto.setWaifabumen(request.getWaifabumen());
		//外发部门//制单人所在的部门名称
		dto.setWaifabumenName(request.getWaifabumenName());
		//外发单号
		dto.setExternalBillNo(request.getExternalBillNo());
		//代收货款手续费
		dto.setCodAgencyFee(request.getCodAgencyFee());
		//保价费
		dto.setExternalInsuranceFee(request.getExternalInsuranceFee());
		//外发运费
		dto.setExternalAgencyFee(request.getExternalAgencyFee());
		//外发成本总额【应付费用】
		dto.setCostAmount(request.getCostAmount());
		//审核状态
		dto.setAuditStatus(request.getAuditStatus());
		//偏线代理编码
		dto.setAgentCompanyCode(request.getAgentCompanyCode());
		//偏线代理名称
		dto.setAgentCompanyName(request.getAgentCompanyName());
		//是否中转外发
		dto.setTransferExternal(request.getTransferExternal());
		//币种
		dto.setCurrencyCode(request.getCurrencyCode());
		//业务日期(当前业务操作时间)
		dto.setBusinessDate(request.getBusinessDate());
		//外发单创建时间(录入日期)
		dto.setCreateTime(request.getCreateTime());
		//出发部门编码
		dto.setReceiveOrgCode(request.getReceiveOrgCode());
		//到达部门编码
		dto.setLastLoadOrgCode(request.getLastLoadOrgCode());
		//总费用
		dto.setTotalFee(request.getTotalFee());
		//代收货款费用
		dto.setCodAmount(request.getCodAmount());
		//代理网点
		dto.setAgentOrgCode(request.getAgentOrgCode());
		//查询运单
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(request.getWaybillNo());
		if(null == waybillEntity){
			throw new SettlementException("运单信息未同步至FOSS，无法获取运单id");
		}
		//运单Id
		dto.setWaybillId(waybillEntity.getId());
		//核销方式
		dto.setVerificationMethods(request.getVerificationMethods());
		return dto;
	}
	//外发单 根据请求的参数，转换成对应接口中的dtos
	private List<SettlementLdpExternalBillDto> getList(List<EcsSettlementLdpExternalBillRequestDto> list){
		List<SettlementLdpExternalBillDto> dtos = new ArrayList<SettlementLdpExternalBillDto>();
		if(CollectionUtils.isNotEmpty(list)){
			for(EcsSettlementLdpExternalBillRequestDto dto : list){
				dtos.add(this.getSettlementLdpExternalBillDto(dto));
			}
		}
		return dtos;
	}
	
	public void setVehicleAgencyExternalLdpService(
			IVehicleAgencyExternalLdpService vehicleAgencyExternalLdpService) {
		this.vehicleAgencyExternalLdpService = vehicleAgencyExternalLdpService;
	}

	public void setEcsLdpExternalBillForSettlement(IEcsLdpExternalBillForSettlement ecsLdpExternalBillForSettlement) {
		this.ecsLdpExternalBillForSettlement = WebApplicationContextHolder.getWebApplicationContext().
				getBean("ecsLdpExternalBillForSettlement",IEcsLdpExternalBillForSettlement.class);
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
}
