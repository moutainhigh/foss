package com.deppon.foss.module.settlement.vtsitf.server.ws;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.server.service.IValidateRfcAndCancelService;
import com.deppon.foss.module.pickup.sign.api.shared.dto.ValidateRfcAndCancelDto;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.vtsitf.server.inter.IValidateRfcAndCancelToVts;

/**
 * foss对接整车运单更改、作废财务规则校验
 * 
 * @author foss结算-306579-guoxinru
 * @date 2016-5-18 下午3:42:18
 */
public class ValidateRfcAndCancelToVts implements IValidateRfcAndCancelToVts {

	/**
	 * 日志
	 */
	private static final Logger logger = LogManager
			.getLogger(SettlementToVtsService.class);

	@Context
	HttpServletResponse response;
	@Context
	HttpServletRequest request;

	/**
	 * 注入foss对接vts运单更改、作废规则校验service
	 */
	private IValidateRfcAndCancelService validateRfcAndCancelService;

	/**
	 * 应付单通用Service
	 */
	private IBillPayableService billPayableService;



	@SuppressWarnings("finally")
	@Override
	public String validateRfcAndCancel(String validateRfcAndCancelDto) {

		logger.info("VTS调用foss运单更改、作废财务规则校验开始...");

		ValidateRfcAndCancelDto resultDto = new ValidateRfcAndCancelDto();
		ValidateRfcAndCancelDto validateDto = new ValidateRfcAndCancelDto();

		try {
			// 非空判断
			if (StringUtils.isEmpty(validateRfcAndCancelDto)) {
				logger.error("vts传入参数为空!");
				throw new SettlementException("vts传入参数为空!");
			}
			logger.info("vts传入参数：" + validateRfcAndCancelDto);

			// 解析传入json，封装实体类
			validateDto = JSONObject.parseObject(validateRfcAndCancelDto)
					.getObject("requestEntity", ValidateRfcAndCancelDto.class);

			// 非空判断
			if (null == validateDto) {
				logger.error("vts传入参数为空!");
				throw new SettlementException("vts传入参数为空!");
			}

			// 运单号判空
			if (StringUtils.isEmpty(validateDto.getWaybillNo())) {
				logger.error("vts传入运单号为空!");
				throw new SettlementException("vts传入运单号为空!");
			}
			logger.info("vts传入运单号：" + validateDto.getWaybillNo());

			// 操作标识判空
			if (StringUtils.isEmpty(validateDto.getOperate())) {
				logger.error("vts传入操作标识为空!");
				throw new SettlementException("vts传入操作标识为空!");
			}

			// vts传入登陆用户参数判空
			if (StringUtils.isEmpty(validateDto.getEmpCode())) {
				logger.error("vts传入用户编码为空！");
				throw new SettlementException("vts传入用户编码为空！");
			}
			if (StringUtils.isEmpty(validateDto.getEmpName())) {
				logger.error("vts传入用户名称为空!");
				throw new SettlementException("vts传入用户名称为空！");
			}
			if (StringUtils.isEmpty(validateDto.getCurrentDeptCode())) {
				logger.error("vts传入部门编码为空!");
				throw new SettlementException("vts传入部门编码为空！");
			}
			if (StringUtils.isEmpty(validateDto.getCurrentDeptName())) {
				logger.error("vts传入部门名称为空!");
				throw new SettlementException("vts传入部门名称为空！");
			}

			// 从前台获取登录用户信息封装至CurrentInfo
			UserEntity user = new UserEntity();
			OrgAdministrativeInfoEntity deptInfo = new OrgAdministrativeInfoEntity();
			EmployeeEntity employeeEntity = new EmployeeEntity();

			user.setEmployee(employeeEntity);
			user.getEmployee().setEmpCode(validateDto.getEmpCode());
			user.getEmployee().setEmpName(validateDto.getEmpName());
			deptInfo.setCode(validateDto.getCurrentDeptCode());
			deptInfo.setName(validateDto.getCurrentDeptName());

			CurrentInfo currentInfo = new CurrentInfo(user, deptInfo);

			// 调用运单更改、作废财务规则判断service
			logger.info("运单更改、作废财务单据校验调用foss接口开始；运单号："
					+ validateDto.getWaybillNo());
			this.validateRfcAndCancelService.validateRfcAndCancel(validateDto,
					currentInfo);

			/*
			 * 根据运单号查询该运单号是否存在整车首尾款应付单： 1.不存在，返回：0 2.存在且未支付：返回1 3.存在且已支付：返回2
			 */
			// 1.查询该运单号是否存在首尾款应付单
			BillPayableConditionDto billPayableConditionDto = new BillPayableConditionDto(
					validateDto.getWaybillNo());
			billPayableConditionDto
					.setBillTypes(new String[] {
							SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST,
							SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST });

			List<BillPayableEntity> billPayables = this.billPayableService
					.queryBillPayableByCondition(billPayableConditionDto);

			// 1.查询该运单号是否存在首尾款应付单
			if (CollectionUtils.isEmpty(billPayables)) {
				resultDto.setLastStatus("0");
				resultDto.setFirstStatus("0");
			}

			if (CollectionUtils.isNotEmpty(billPayables)) {
				for (BillPayableEntity entity : billPayables) {
					if (entity.getPayStatus().equals("N")
							&& entity
									.getBillType()
									.equals(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST)) {
						resultDto.setFirstStatus("1");
					}
					if (entity.getPayStatus().equals("N")
							&& entity
									.getBillType()
									.equals(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST)) {
						resultDto.setLastStatus("1");
					}
					if (entity.getPayStatus().equals("Y")
							&& entity
									.getBillType()
									.equals(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST)) {

						resultDto.setFirstStatus("2");
					}

					if (entity.getPayStatus().equals("Y")
							&& entity
									.getBillType()
									.equals(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST)) {
						resultDto.setLastStatus("2");
					}

				}
			}

			// foss财务单据校验成功，封装dto返回vts
			resultDto.setWaybillNo(validateDto.getWaybillNo());
			resultDto.setSuccess(true);
			resultDto.setMsg("vts调用foss运单更改、作废财务规则校验成功！");
		} catch (BusinessException e) {
			resultDto.setSuccess(false);
			resultDto.setMsg(e.getErrorCode());
			logger.error("vts调用foss运单更改、作废财务规则校验异常,运单号："
					+ validateDto.getWaybillNo());
		} catch (Exception e1) {
			resultDto.setSuccess(false);
			resultDto.setMsg(e1.getMessage());
			logger.error("vts调用foss运单更改、作废财务规则校验异常,运单号："
					+ validateDto.getWaybillNo());
		} finally {
			response.addHeader("ESB-ResultCode", "1");
			logger.info("VTS调用foss运单更改、作废财务单据校验结束；");
			return JSONObject.toJSONString(resultDto);
		}

	}

	public void setValidateRfcAndCancelService(
			IValidateRfcAndCancelService validateRfcAndCancelService) {
		this.validateRfcAndCancelService = validateRfcAndCancelService;
	}
	
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

}
