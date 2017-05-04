/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-partialline
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 偏线外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/server/action/PartiallineAction.java
 * 
 *  FILE NAME     :PartiallineAction.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.partialline.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.transfer.partialline.api.server.service.IExternalBillService;
import com.deppon.foss.module.transfer.partialline.api.server.service.IUninputPartiallineService;
import com.deppon.foss.module.transfer.partialline.api.shared.define.PartiallineConstants;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillWayBillInfoDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.HandoverBillDetailDto;
import com.deppon.foss.module.transfer.partialline.api.shared.exception.ExternalBillException;
import com.deppon.foss.module.transfer.partialline.api.shared.vo.ExternalBillVo;
import com.deppon.foss.module.transfer.partialline.server.service.impl.UninputPartiallineService;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;

/**
 * 
 * 偏线相关操作
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-10-12 上午8:59:03
 */
public class PartiallineAction extends AbstractAction {

	private static final long serialVersionUID = 113587600296731819L;

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(UninputPartiallineService.class);

	/**
	 * 偏线录入service注入
	 */
	private IExternalBillService externalBillService;
	/**
	 * 未录入查询service注入
	 */
	private IUninputPartiallineService uninputPartiallineService;
	/**
	 * 人员信息Service
	 */
	private IEmployeeService employeeService;
	
	/**
	 * 部门Service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * 前端数据Vo
	 */
	private ExternalBillVo vo = new ExternalBillVo();
	/**
	 * 导出Excel 文件流
	 */
	transient InputStream excelStream;
	/**
	 * 导出Excel 文件名
	 */
	private String fileName;
	/**
	 * 业务锁
	 */
	private IBusinessLockService businessLockService;

	/**
	 * 根据前端传入查询参数查询已经录入的偏线外发单列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-20 上午8:56:24
	 */
	@JSON
	public String queryPartialline() {
		try {
			LOGGER.info("根据前端传入查询参数查询已经录入的偏线外发单列表");
			ExternalBillDto dto = null;
			if (vo != null) {
				dto = vo.getDto();
				// 查询偏线外发单列表
				// 用户信息
				CurrentInfo user = FossUserContext.getCurrentInfo();
				
				//如果输入运单号,则忽略其他条件直接使用运单查询
				if(StringUtils.isNotBlank(dto.getWaybillNo())){
					ExternalBillDto dtoTemp =new ExternalBillDto();
					dtoTemp.setWaybillNo(dto.getWaybillNo());
					dto=dtoTemp;
				}else if(StringUtils.isNotBlank(dto.getExternalBillNo())){
					ExternalBillDto dtoTemp =new ExternalBillDto();
					dtoTemp.setExternalBillNo(dto.getExternalBillNo());
					dto=dtoTemp;
				}
				
				// 查询
				List<ExternalBillDto> externalBillList = externalBillService.selectByParams(dto, this.getLimit(),
						this.getStart(), user);
				if (externalBillList != null) {
					vo.setExternalBillList(externalBillList);
				}
				// 查询总条数
				this.setTotalCount(externalBillService.queryCount(dto, user));
			}
			return super.returnSuccess();
		} catch (ExternalBillException e) {
			return super.returnError(e);
		}

	}

	/**
	 * 录入偏线外发单 验证点：在新增偏线外发单前需要验证运单号及外发单号是否已经录入过，录入过则不允许再录入
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-12 上午10:32:28
	 */
	@JSON
	public String addExternalBill() {
		MutexElement mutex=null;
		try {
			LOGGER.info("录入偏线外发单 验证点：在新增偏线外发单前需要验证运单号及外发单号是否已经录入过，录入过则不允许再录入");
			if (vo.getDto() != null) {
				ExternalBillDto tempDto = vo.getDto();
				// 获取当前录入用户，部门，时间信息
				userInfo(tempDto, PartiallineConstants.PARTIALLINE_ACTION_INSERT);
				// 发员工号查询外发员信息、及部门
				queryEmployee(tempDto);
				// 获取当前用户
				CurrentInfo user = FossUserContext.getCurrentInfo();
				if (user == null) {
					throw new ExternalBillException("获取当前用户失败 ", "");
				}
				// 业务锁
				mutex = new MutexElement("ExternalBill" + tempDto.getWaybillNo(),
						PartiallineConstants.TFR_PARTITIALLINE_LOCK_DESC, MutexElementType.WAYBILL_NO);
				boolean b1 = businessLockService.lock(mutex, 0);
				if (b1) {
					// 验证并新增
					externalBillService.addExternalBill(tempDto, user);
					// 解锁
					businessLockService.unlock(mutex);
				} else {
					throw new ExternalBillException(tempDto.getWaybillNo() + "正在录入中，请稍后再试！", "");
				}
			}
			return super.returnSuccess();
		} catch (ExternalBillException e) {
			if(mutex!=null){
				//解锁
				businessLockService.unlock(mutex);
			}
			return super.returnError(e);
		} catch (SettlementException e) {
			if(mutex!=null){
				//解锁
				businessLockService.unlock(mutex);
			}
			return super.returnError(e);
		}
	}

	/**
	 * 更新偏线外发单 验证点：在更新偏线外发单前需要验证运单号及外发单号是否已经录入过，录入过则不允许再录入 ；
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-15 上午8:36:23
	 */
	@JSON
	public String updateExternalBill() {
		MutexElement mutex =null;
		try {
			LOGGER.info("更新偏线外发单");
			ExternalBillDto tempDto = vo.getDto();
			if (tempDto != null) {
				// 获取当前录入用户，部门，时间信息
				userInfo(tempDto, null);
				// 发员工号查询外发员信息、及部门
				queryEmployee(tempDto);
				// 获取当前用户
				CurrentInfo user = FossUserContext.getCurrentInfo();
				// 业务锁
				 mutex = new MutexElement("ExternalBill"+tempDto.getWaybillNo(),
						PartiallineConstants.TFR_PARTITIALLINE_LOCK_DESC, MutexElementType.WAYBILL_NO);
				boolean b1 = businessLockService.lock(mutex, 0);
				if (b1) {
					// 验证并更新
					externalBillService.updateExternalBill(tempDto, user);
					//解锁
					businessLockService.unlock(mutex);
				} else {
					throw new ExternalBillException(tempDto.getWaybillNo() + "正在修改中，请稍后再试！", "");
				}
			}
			return super.returnSuccess();
		} catch (ExternalBillException e) {
			if(mutex!=null){
				//解锁
				businessLockService.unlock(mutex);
			}
			return super.returnError(e);
		} catch (SettlementException e) {
			if(mutex!=null){
				//解锁
				businessLockService.unlock(mutex);
			}
			return super.returnError(e);
		}
	}

	/**
	 * 根据运单号查询运单信息及代理信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-20 上午8:55:59
	 */
	@JSON
	public String queryWaybillInfo() {
		try {
			LOGGER.info("根据运单号查询运单信息及代理信息");
			ExternalBillDto tempDto = vo.getDto();
			if (tempDto != null && StringUtils.isNotBlank(tempDto.getWaybillNo())) {
				// 获取当前用户
				CurrentInfo user = FossUserContext.getCurrentInfo();
				// 验证并查询运单信息
				ExternalBillWayBillInfoDto tempWaybillInfo = externalBillService.queryWaybillInfo(tempDto,
						vo.getValidateWaybillNo(), user);
				if (tempWaybillInfo != null) {
					vo.setBillInfo(tempWaybillInfo);
				}
			}
			return super.returnSuccess();
		} catch (ExternalBillException e) {
			return super.returnError(e);
		}
	}

	/**
	 * 审核偏线外发单
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-20 上午11:35:28
	 */
	@JSON
	public String auditPartialline() {
		try {
			LOGGER.info("审核偏线外发单");
			// 待审核偏线外发ID
			List<String> auditIds = vo.getAuditIds();
			if (auditIds != null) {
				// 获取当前用户
				CurrentInfo user = FossUserContext.getCurrentInfo();
				externalBillService.auditPartialline(auditIds, user);
			}
			return super.returnSuccess();
		} catch (ExternalBillException e) {
			return super.returnError(e);
		} catch (SettlementException e) {
			return super.returnError(e);
		} catch (IllegalAccessException e) {
			return super.returnError("IllegalAccessException");
		} catch (InvocationTargetException e) {
			return super.returnError("InvocationTargetException");
		}
	}

	/**
	 * 反审核偏线外发单
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-20 上午11:35:28
	 */
	@JSON
	public String deAuditPartialline() {
		try {
			LOGGER.info("反审核偏线外发单");
			// 待审核偏线外发ID
			List<String> auditIds = vo.getAuditIds();
			if (auditIds != null) {
				// 获取当前用户
				CurrentInfo user = FossUserContext.getCurrentInfo();
				externalBillService.deAuditPartialline(auditIds, user);
			}
			return super.returnSuccess();
		} catch (ExternalBillException e) {
			return super.returnError(e);
		} catch (SettlementException e) {
			return super.returnError(e);
		} catch (IllegalAccessException e) {
			return super.returnError("IllegalAccessException");
		} catch (InvocationTargetException e) {
			return super.returnError("InvocationTargetException");
		}
	}

	/**
	 * 作废偏线外发单
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-20 上午11:35:28
	 */
	@JSON
	public String invalidePartialline() {
		try {
			LOGGER.info("作废偏线外发单");
			// 待审核偏线外发ID
			List<String> auditIds = vo.getAuditIds();
			if (auditIds != null) {
				// 获取当前用户
				CurrentInfo user = FossUserContext.getCurrentInfo();
				externalBillService.invalidePartialline(auditIds, user);
			}
			return super.returnSuccess();
		} catch (ExternalBillException e) {
			return super.returnError(e);
		} catch (SettlementException e) {
			return super.returnError(e);
		} catch (IllegalAccessException e) {
			return super.returnError("IllegalAccessException");
		} catch (InvocationTargetException e) {
			return super.returnError("InvocationTargetException");
		}
	}

	/**
	 * 查询未录入外发单列表(查询交接单类型为偏线业务，且未录入偏线外发单列表的交接单明细)
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-22 上午11:25:15
	 */
	@JSON
	public String queryUninputedpartial() {
		try {
			LOGGER.info("查询未录入外发单列表");
			HandoverBillDetailDto detailDto = null;
			if (vo != null) {
				detailDto = vo.getUninputDto();
				if (detailDto != null) {
					// 按运单号查询时不受其他条件的限制。若无此单号，弹框提示：请输入正确的单号
					if (StringUtils.isNotBlank(detailDto.getWaybillNo())) {
						// 查询条件设空
						detailDto.setDestOrgCode(null);
						// 交接单号
						detailDto.setHandoverNo(null);
						// 交接时间
						detailDto.setHandoverTimeFrom(null);
						detailDto.setHandoverTimeTo(null);

					}
					// 查询未录入偏线外发单列表（查询交接单明细）
					// TODO 需要根据部门查询（根据权限获取）
					// 用户信息
					CurrentInfo user = FossUserContext.getCurrentInfo();
					// 查询
					List<HandoverBillDetailDto> handoverDetailList = uninputPartiallineService.queryUninputedpartial(
							detailDto, this.getLimit(), this.getStart(), user);
					if (handoverDetailList != null) {
						vo.setUninputedpartials(handoverDetailList);
					}
					// 查询总条数
					this.setTotalCount(uninputPartiallineService.queryUninputedpartialCount(detailDto,user));
				}

			}
			return super.returnSuccess();
		} catch (ExternalBillException e) {
			return super.returnError(e);
		}
	}

	/**
	 * 基础数据处理-获取用户信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-20 上午10:40:01
	 */
	private void userInfo(ExternalBillDto tempDto, String insertOrUpdate) {
		LOGGER.info("获取用户信息");
		// 用户信息
		CurrentInfo user = FossUserContext.getCurrentInfo();
		// 设置用户相关信息
		Date currentTime = new Date();
		// 录入时间
		tempDto.setRegisterTime(currentTime);
		// 新增时才些修改时间
		if (PartiallineConstants.PARTIALLINE_ACTION_INSERT.equals(insertOrUpdate)) {
			// 修改时间
			tempDto.setModifyDate(currentTime);
		}
		if (StringUtils.isNotBlank(user.getEmpCode())) {
			LOGGER.info("员工号" + user.getEmpCode());
			// 录入人员工号
			tempDto.setRegisterUserCode(user.getEmpCode());
			// 修改人
			tempDto.setModifyUser(user.getEmpCode());
		}
		if (StringUtils.isNotBlank(user.getCurrentDeptName())) {
			// 外发部门
			LOGGER.info("外发部门:" + user.getCurrentDeptName());
			tempDto.setExternalOrgName(user.getCurrentDeptName());
		}
		if (StringUtils.isNotBlank(user.getCurrentDeptCode())) {
			// 外发部门编号
			LOGGER.info("外发部门编号:" + user.getCurrentDeptCode());
			tempDto.setExternalOrgCode(user.getCurrentDeptCode());
			// 录入部门
			tempDto.setRegisterOrgCode(user.getCurrentDeptCode());
		}

		// 待审核
		tempDto.setAuditStatus(PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT);
	}

	/**
	 * 查询人员信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-30 上午11:34:22
	 */
	private void queryEmployee(ExternalBillDto externalBillDto) {
		if (externalBillDto != null && StringUtils.isNotBlank(externalBillDto.getExternalUserCode())) {
			EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(externalBillDto.getExternalUserCode());
			if (emp != null) {
				// 外发员姓名
				externalBillDto.setExternalUserName(emp.getEmpName());
				if (emp.getDepartment() != null) {
					//判定外发人员是否有外场
					List<String> list = new ArrayList<String>();
					list.add(BizTypeConstants.ORG_TRANSFER_CENTER);
					OrgAdministrativeInfoEntity  orgAdministrativeInfoEntity=orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(emp.getDepartment().getCode(),list);
					if(null != orgAdministrativeInfoEntity){
						// 外发员部门编号
						externalBillDto.setExternalOrgCode(orgAdministrativeInfoEntity.getCode());
						// 外发员部门名称
						externalBillDto.setExternalOrgName(orgAdministrativeInfoEntity.getName());
					}else{						
						LOGGER.error("外发员无外场，请配置，再操作");
						throw new ExternalBillException("外发员无外场，请配置，再操作", "");
					}
				} else {
					LOGGER.error("外发员无部门，请配置，再操作");
					throw new ExternalBillException("外发员无部门，请配置，再操作", "");
				}
			} else {
				LOGGER.error("未查询到相应的外发员");
				throw new ExternalBillException("未查询到相应的外发员", "");
			}
		} else {
			LOGGER.error("外发员未录入");
			throw new ExternalBillException("外发员未录入", "");
		}

	}

	/**
	 * 查询外发人员姓名
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-30 上午11:34:22
	 */
	public String queryExternalOrgName() {
		try {
			if (vo.getDto() != null) {
				queryEmployee(vo.getDto());
				if (StringUtils.isBlank(vo.getDto().getExternalOrgName())) {
					throw new ExternalBillException("未查询到外发员对应的部门信息", "");
				}
			}
			return super.returnSuccess();
		} catch (ExternalBillException e) {
			return super.returnError(e);
		}

	}

	/**
	 * 运单号查询是否存在未录入的外发交接单
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-30 上午11:34:22
	 */
	public String queryHandedUninputWaybill() {
		try {
			if (vo.getDto() != null && StringUtils.isNotBlank(vo.getDto().getWaybillNo())) {
				// 运单号不为空
				uninputPartiallineService.queryHandedUninputWaybill(vo.getDto());
			}
			return super.returnSuccess();
		} catch (ExternalBillException e) {
			return super.returnError(e);
		}

	}

	/**
	 * 导出偏线外发单
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-30 上午11:34:22
	 */
	public String exportExternalBill() {
		try {
			LOGGER.info("根据前端传入查询参数查询已经录入的偏线外发单列表");
			ExternalBillDto dto = null;
			if (vo != null) {
				dto = vo.getDto();
				// 查询偏线外发单列表
				// 用户信息
				CurrentInfo user = FossUserContext.getCurrentInfo();
				
				//如果输入运单号,则忽略其他条件直接使用运单查询
				if(StringUtils.isNotBlank(dto.getWaybillNo())){
					ExternalBillDto dtoTemp =new ExternalBillDto();
					dtoTemp.setWaybillNo(dto.getWaybillNo());
					dto=dtoTemp;
				}else if(StringUtils.isNotBlank(dto.getExternalBillNo())){
					ExternalBillDto dtoTemp =new ExternalBillDto();
					dtoTemp.setExternalBillNo(dto.getExternalBillNo());
					dto=dtoTemp;
				}
				
				// 总条数(将总条数作为限制)
				String limit = String.valueOf(externalBillService.queryCount(dto, user));
				// 是否为数字
				if (StringUtils.isNumeric(limit)) {
					// 设置限制
					this.setLimit(Integer.valueOf(limit));
				}
				
				// 查询
				List<ExternalBillDto> externalBillList = externalBillService.selectByParams(dto, this.getLimit(),
						this.getStart(), user);
				// 导出文件名
				fileName = this.encodeFileName(PartiallineConstants.EXCEPTION_WAYBILL_SHEET_NAME);
				// 导出文件流
				excelStream = externalBillService.exportExternalBill(externalBillList, vo);

			}
			return super.returnSuccess();
		} catch (ExternalBillException e) {
			return super.returnError(e);
		}

	}
	
	/**
	 * 生成导出文件名称
	 * @author huyue
	 * @date 2013-1-7 下午2:51:23
	 */
	private String encodeFileName(String fileName) throws ExternalBillException {
		try {
			String returnStr;
			String agent = (String) ServletActionContext.getRequest().getHeader("USER-AGENT");
			if (agent != null && agent.indexOf("MSIE") == -1) {
				returnStr = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			} else {
				returnStr = URLEncoder.encode(fileName, "UTF-8");
			}
			return returnStr;
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("转换文件名编码失败", e);
			throw new ExternalBillException(StockException.EXPORT_FILE_ERROR_CODE, "");
		}
	}
	
	
	/**
	 * 根据重新计算运单号的外发费用及外发送货费
	 * 
	 * @author 105795-foss-中转-wqh
	 * @date 2014-07-31 上午11:34:22
	 */
	
	public String calculateDelAndEtdFee()
	{
		
		try {
			
			ExternalBillDto tempDto = vo.getDto();
			String waybillNo=tempDto.getWaybillNo();
			
			// 验证并查询运单信息
			ExternalBillWayBillInfoDto tempWaybillInfo = externalBillService.calculateDeliverFeeAndEtdFee(waybillNo);
			if (tempWaybillInfo != null) {
				vo.setBillInfo(tempWaybillInfo);
			}
			
			return returnSuccess();
		} catch (ExternalBillException e) {
			LOGGER.error("重新计算运单号的外发费用及外发送货费 时  出错", e);
			return super.returnError(e);
			
		}
		
	}
	
	

	/**
	 * 设置 偏线录入service注入.
	 * 
	 * @param externalBillService
	 *            the new 偏线录入service注入
	 */
	public void setExternalBillService(IExternalBillService externalBillService) {
		this.externalBillService = externalBillService;
	}

	/**
	 * 设置 未录入查询service注入.
	 * 
	 * @param uninputPartiallineService
	 *            the new 未录入查询service注入
	 */
	public void setUninputPartiallineService(IUninputPartiallineService uninputPartiallineService) {
		this.uninputPartiallineService = uninputPartiallineService;
	}

	/**
	 * 设置 人员信息Service.
	 * 
	 * @param employeeService
	 *            the new 人员信息Service
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/**
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * 获取 前端数据Vo.
	 * 
	 * @return the 前端数据Vo
	 */
	public ExternalBillVo getVo() {
		return vo;
	}

	/**
	 * 设置 前端数据Vo.
	 * 
	 * @param vo
	 *            the new 前端数据Vo
	 */
	public void setVo(ExternalBillVo vo) {
		this.vo = vo;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	

}