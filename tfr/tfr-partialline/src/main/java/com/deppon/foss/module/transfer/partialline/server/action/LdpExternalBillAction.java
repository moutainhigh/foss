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
 *  DESCRIPTION   : 落地配外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/server/action/LdpExternalBillAction.java
 * 
 *  FILE NAME     :LdpExternalBillAction.java
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserDeptAuthorityService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRelateDetailEntityService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.transfer.partialline.api.server.service.IInputWeightVolumnService;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillService;
import com.deppon.foss.module.transfer.partialline.api.server.service.IUninputLdpExternalBillService;
import com.deppon.foss.module.transfer.partialline.api.shared.define.PartiallineConstants;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.InputWeightVolumnEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillWayBillInfoDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpHandoverBillDetailDto;
import com.deppon.foss.module.transfer.partialline.api.shared.exception.ExternalBillException;
import com.deppon.foss.module.transfer.partialline.api.shared.vo.LdpExternalBillVo;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 落地配相关操作
 * 
 * @author ibm-liuzhaowei
 * @date 2013-07-16 上午9:18:36
 */
public class LdpExternalBillAction extends AbstractAction {

	private static final long serialVersionUID = 113587610296731819L;

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LdpExternalBillAction.class);

	/**
	 * 落地配录入service注入 
	 */
	private ILdpExternalBillService ldpExternalBillService;
	/**
	 * 未录入查询service注入
	 */
	private IUninputLdpExternalBillService uninputLdpExternalBillService;
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
	private LdpExternalBillVo vo = new LdpExternalBillVo();
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
	
	private IUserDeptAuthorityService userDeptAuthorityService;
	
	private IWaybillRelateDetailEntityService waybillRelateDetailEntityService;
	
	private IInputWeightVolumnService inputWeightVolumnService;
	
	private IWaybillManagerService waybillManagerService;

	/**
	 * 根据前端传入查询参数查询已经录入的落地配外发单列表
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午8:56:24
	 */
	@JSON
	public String queryLdpExternalBill() {
		try {
			LOGGER.info("根据前端传入查询参数查询已经录入的快递代理外发单列表");
			LdpExternalBillDto dto = null;
			if (vo != null) {
				dto = vo.getDto();
				CurrentInfo user = FossUserContext.getCurrentInfo();
//				获取财务人员数据权限
				List<OrgAdministrativeInfoEntity> deptCodeLists = userDeptAuthorityService.searchUserDeptsByUserCode(user.getEmpCode());
				
				List<String> externalOrgCodeList = new ArrayList<String>();
//				找到外场
				for(OrgAdministrativeInfoEntity orgAdministrativeInfoEntity : deptCodeLists){
					if(StringUtils.equals(orgAdministrativeInfoEntity.getTransferCenter(), FossConstants.YES)){
						externalOrgCodeList.add(orgAdministrativeInfoEntity.getCode());
					}
				}
				
				if (dto != null && user != null) {
					OrgAdministrativeInfoEntity transCenter = ldpExternalBillService.querySuperiorOrgByOrgCode(user.getCurrentDeptCode());
					if (transCenter != null && StringUtils.isNotBlank(transCenter.getCode())) {
						externalOrgCodeList.add(transCenter.getCode());
					}
				}
//				如果没有配置数据权限，找此登陆人员上级的外场
				if(externalOrgCodeList.size() == 0){
//					此处是为了防止数据权限以及查找上级外场都为空的情况，防止SQL出错
					//sonar-352203
					if(dto != null){
						dto.setExternalOrgCode(user.getCurrentDeptCode());
						dto.setExternalOrgCodeList(null);
					}
				}else{
					if(dto != null){
					dto.setExternalOrgCode(null);
					dto.setExternalOrgCodeList(externalOrgCodeList);
					}
				}
				
				// 查询
				List<LdpExternalBillDto> externalBillList = ldpExternalBillService.selectByParams(dto, this.getLimit(),	this.getStart(), user);
				if (externalBillList != null) {
					vo.setLdpExternalBillList(externalBillList);
				}
				// 查询总条数
				this.setTotalCount(ldpExternalBillService.queryCount(dto, user));
			}
			return super.returnSuccess();
		} catch (ExternalBillException e) {
			return super.returnError(e);
		}

	}
	
	/**
	 * 录入落地配外发单 验证点：在新增落地配外发单前需要验证运单号及外发单号是否已经录入过，录入过则不允许再录入
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午10:32:28
	 * 
	 * 生成外发时，一票多件，按照运单和流水号生成外发；并且可部分生成外发单
	 * @update-author foss--269701--lln
	 * @update-date 2015-10-22 下午14:37:20
	 */
	@JSON
	public String addLdpExternalBill() {
		try {
			// 获取当前用户
			CurrentInfo user = FossUserContext.getCurrentInfo();
			//落地配外发运单号列表
			//List<String> waybillNoList = vo.getWaybillNoList();
			//String failedWaybillNos = uninputLdpExternalBillService.addLdpExternalBill(waybillNoList, user);
			//vo.setWaybillNos(failedWaybillNos);
			
			//落地配外发运单号和流水号列表
			List<LdpHandoverBillDetailDto> wayBillSerialNos=vo.getUninputLdpExternalBill();
			String failedserialNos = uninputLdpExternalBillService.addLdpExternalBill(wayBillSerialNos, user);
			//批量生成落地配外发单失败的流水号集合
			vo.setWaybillNos(failedserialNos);
			
			return super.returnSuccess();
		} catch (ExternalBillException e) {
			return super.returnError(e);
		}
	}
	
	/**
	 * 一票多件、母件是否重新录入重量体积
	 * @return
	 */
	public String validateLdpExternalBill(){
		try {
			//落地配外发运单号列表
			List<LdpHandoverBillDetailDto> uninputLdpExternalBill=vo.getUninputLdpExternalBill();
			String unValidateWaybillSerialNos = "";
			//检查未通过的运单
			for (int i = uninputLdpExternalBill.size() - 1;i >=0;i--) {
				LdpHandoverBillDetailDto ldpHandoverBillDetailDto = uninputLdpExternalBill.get(i);
				boolean isCheck = false; //是否需要检查重量是否录入过,默认无需检查
				String waybillNo = ldpHandoverBillDetailDto.getWaybillNo();
				String serialNo = ldpHandoverBillDetailDto.getSerialNo();
				WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
				if(hook(waybillEntity)){
					if(waybillEntity != null && waybillEntity.getGoodsQtyTotal() > 1){
						isCheck = true;
					}
				}else{
					Map<String,Object> paramMap = new HashMap<String,Object>();
					paramMap.put("waybillNo", waybillNo);
					paramMap.put("active", FossConstants.ACTIVE);
					TwoInOneWaybillDto twoInOneWaybillDto = waybillRelateDetailEntityService.queryWaybillRelateByWaybillOrOrderNo(paramMap);
					if(twoInOneWaybillDto != null){
						//是子母件，则判断是否重新录入过重量体积
						String twoInOne = twoInOneWaybillDto.getIsTwoInOne();
						if(StringUtils.equals(twoInOne, FossConstants.ACTIVE)){
							isCheck = true;
						}
					}
				}
				//如果需要检查是否录入子母件
				if(isCheck){
					InputWeightVolumnEntity inputWeightVolumnEntity = inputWeightVolumnService.getInputWeightVolumnByWaybillNo(waybillNo,serialNo);
					if(inputWeightVolumnEntity == null){
						//将检查未通过的数据添加返回给页面
						unValidateWaybillSerialNos = unValidateWaybillSerialNos + "("+waybillNo + ","+serialNo + ") ";
						uninputLdpExternalBill.remove(i);
					}
				}
			}
			vo.setWaybillNos(unValidateWaybillSerialNos);
			return super.returnSuccess();
		}catch (ExternalBillException e) {
			return super.returnError(e);
		}
	}
	/**
	 * 钩子函数
	 */
	private boolean hook(WaybillEntity waybillEntity){
		boolean hook = true;
		String customerPickUpOrg = waybillEntity.getCustomerPickupOrgCode();
		if(customerPickUpOrg.startsWith("LDP00174")||customerPickUpOrg.startsWith("LDP00175")||customerPickUpOrg.startsWith("LDP00183")){
			hook = false;
		}
		return hook;
	}
	/**
	 * 更新落地配外发单 验证点：在更新落地配外发单前需要验证运单号及外发单号是否已经录入过，录入过则不允许再录入 ；
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午8:36:23
	 */
	@JSON
	public String updateLdpExternalBill() {
		MutexElement mutex = null;
		try {
			LOGGER.info("更新快递代理外发单");
			LdpExternalBillDto ldpExternalBillDto = vo.getDto();
			if (ldpExternalBillDto != null) {
				// 获取当前用户
				CurrentInfo user = FossUserContext.getCurrentInfo();
				// 业务锁
				 mutex = new MutexElement("ExternalBill" + ldpExternalBillDto.getWaybillNo(),
						PartiallineConstants.TFR_PARTITIALLINE_LOCK_DESC, MutexElementType.WAYBILL_NO);
				boolean b1 = businessLockService.lock(mutex, 0);
				if (b1) {
					// 验证并更新
					ldpExternalBillDto = ldpExternalBillService.updateLdpExternalBill(ldpExternalBillDto, user);
					vo.setDto(ldpExternalBillDto);
					//解锁
					businessLockService.unlock(mutex);
				} else {
					throw new ExternalBillException(ldpExternalBillDto.getWaybillNo() + "正在修改中，请稍后再试！", "");
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
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午8:55:59
	 */
	@JSON
	public String queryWaybillInfoByWaybillNo() {
		try {
			LOGGER.info("根据运单号查询运单信息及代理信息");
			LdpExternalBillDto tempDto = vo.getDto();
			if (tempDto != null && StringUtils.isNotBlank(tempDto.getWaybillNo())) {
				// 获取当前用户
				CurrentInfo user = FossUserContext.getCurrentInfo();
				// 验证并查询运单信息
				LdpExternalBillWayBillInfoDto tempWaybillInfo = ldpExternalBillService.queryWaybillInfo(tempDto,
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
	 * 审核落地配外发单
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午11:35:28
	 */
	@JSON
	public String auditLdpExternalBill() {
		try {
			LOGGER.info("审核快递代理外发单");
			// 待审核落地配外发ID
			List<String> auditIds = vo.getAuditIds();
			if (auditIds != null) {
				// 获取当前用户
				CurrentInfo user = FossUserContext.getCurrentInfo();
				ldpExternalBillService.auditLdpExternalBill(auditIds, user);
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
	 * 反审核落地配外发单
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午11:35:28
	 */
	@JSON
	public String deAuditLdpExternalBill() {
		try {
			LOGGER.info("反审核快递代理外发单");
			// 待审核落地配外发ID
			List<String> auditIds = vo.getAuditIds();
			if (auditIds != null) {
				// 获取当前用户
				CurrentInfo user = FossUserContext.getCurrentInfo();
				ldpExternalBillService.deAuditLdpExternalBill(auditIds, user);
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
	 * 作废落地配外发单
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午11:35:28
	 */
	@JSON
	public String invalidLdpExternalBill() {
		try {
			LOGGER.info("作废快递代理外发单");
			// 待审核落地配外发ID
			List<String> auditIds = vo.getAuditIds();
			if (auditIds != null) {
				// 获取当前用户
				CurrentInfo user = FossUserContext.getCurrentInfo();
				ldpExternalBillService.invalideLdpExternalBill(auditIds, user);
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
	 * 查询未录入落地配外发单列表(查询交接单类型为落地配业务，且未录入落地配外发单列表的交接单明细或已录入但标志为中转的)
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午11:25:15
	 */
	@JSON
	public String queryUninputLdpExternalBills() {
		try {
			LOGGER.info("查询未录快递代理外发单列表");
			LdpHandoverBillDetailDto detailDto = null;
			//sonar-352203
			if (vo == null) {
				return super.returnSuccess();
			}
				detailDto = vo.getUninputLdpExternalBillDto();
				//sonar-352203
				if (detailDto == null) {
					return super.returnSuccess();
				}
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
					// 查询未录入落地配外发单列表（查询交接单明细）
					// 需要根据部门查询（根据权限获取）
					// 用户信息
					CurrentInfo user = FossUserContext.getCurrentInfo();
//					
					if (detailDto != null && user != null) {
						LOGGER.info("查询筛选的当前部门名称:" + user.getCurrentDeptName() + ",部门编码:" + user.getCurrentDeptCode());
						List<String> list = uninputLdpExternalBillService.queryTransCenterChildrenCodes(user.getCurrentDeptCode());
						if(CollectionUtils.isEmpty(list)){
							List<String> l = new ArrayList<String>();
							l.add(user.getCurrentDeptCode());
							detailDto.setOrgCodes(l);
						}else{
							detailDto.setOrgCodes(list);
						}
						
					}
					// 查询
					List<LdpHandoverBillDetailDto> handoverDetailList = uninputLdpExternalBillService.queryUninputLdpExternalBill(detailDto, this.getLimit(), this.getStart());
					if (handoverDetailList != null) {
						//如果是子母件或一票多件，则优先从录入的重量体积里获取数据
						for (LdpHandoverBillDetailDto ldpHandoverBillDetailDto : handoverDetailList) {
							InputWeightVolumnEntity inputWeightVolumnEntity = inputWeightVolumnService.queryInputWeightVolumnByWaybillNo(ldpHandoverBillDetailDto.getWaybillNo(),ldpHandoverBillDetailDto.getSerialNo());
							if(inputWeightVolumnEntity != null){
								ldpHandoverBillDetailDto.setHandoverWeight(inputWeightVolumnEntity.getWeight());
								ldpHandoverBillDetailDto.setHandoverVolume(inputWeightVolumnEntity.getVolumn());
							}
						}
						vo.setUninputLdpExternalBill(handoverDetailList);
					}
					// 查询总条数
					this.setTotalCount(uninputLdpExternalBillService.queryUninputLdpExternalBillCount(detailDto));
//				}

//			}
			return super.returnSuccess();
		} catch (ExternalBillException e) {
			return super.returnError(e);
		}
	}

	/**
	 * 查询人员信息
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午11:34:22
	 */
	private void queryEmployee(LdpExternalBillDto ldpExternalBillDto) {
		if (ldpExternalBillDto != null && StringUtils.isNotBlank(ldpExternalBillDto.getExternalUserCode())) {
			EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(ldpExternalBillDto.getExternalUserCode());
			if (emp != null) {
				// 外发员姓名
				ldpExternalBillDto.setExternalUserName(emp.getEmpName());
				if (emp.getDepartment() != null) {
					//判定外发人员是否有外场
					List<String> list = new ArrayList<String>();
					list.add(BizTypeConstants.ORG_TRANSFER_CENTER);
					OrgAdministrativeInfoEntity  orgAdministrativeInfoEntity=orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(emp.getDepartment().getCode(),list);
					if(null != orgAdministrativeInfoEntity){
						// 外发员部门编号
						ldpExternalBillDto.setExternalOrgCode(orgAdministrativeInfoEntity.getCode());
						// 外发员部门名称
						ldpExternalBillDto.setExternalOrgName(orgAdministrativeInfoEntity.getName());
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
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午11:34:22
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
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午11:34:22
	 */
	public String queryHandedUninputWaybill() {
		try {
			if (vo.getDto() != null && StringUtils.isNotBlank(vo.getDto().getWaybillNo())) {
				// 运单号不为空
				uninputLdpExternalBillService.queryHandedUninputWaybill(vo.getDto());
			}
			return super.returnSuccess();
		} catch (ExternalBillException e) {
			return super.returnError(e);
		}

	}

	/**
	 * 导出落地配外发单
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午11:34:22
	 */
	public String exportLdpExternalBill() {
		try {
			LOGGER.info("根据前端传入查询参数查询已经录入的快递代理外发单列表");
			LdpExternalBillDto dto = null;
			if (vo != null) {
				dto = vo.getDto();
				CurrentInfo user = FossUserContext.getCurrentInfo();
//				获取财务人员数据权限
				List<OrgAdministrativeInfoEntity> deptCodeLists = userDeptAuthorityService.searchUserDeptsByUserCode(user.getEmpCode());
				
				List<String> externalOrgCodeList = new ArrayList<String>();
//				找到外场
				for(OrgAdministrativeInfoEntity orgAdministrativeInfoEntity : deptCodeLists){
					if(StringUtils.equals(orgAdministrativeInfoEntity.getTransferCenter(), FossConstants.YES)){
						externalOrgCodeList.add(orgAdministrativeInfoEntity.getCode());
					}
				}
				
				if (dto != null && user != null) {
					OrgAdministrativeInfoEntity transCenter = ldpExternalBillService.querySuperiorOrgByOrgCode(user.getCurrentDeptCode());
					if (transCenter != null && StringUtils.isNotBlank(transCenter.getCode())) {
						externalOrgCodeList.add(transCenter.getCode());
					}
				}
//				如果没有配置数据权限，找此登陆人员上级的外场
				if(externalOrgCodeList.size() == 0){
//					此处是为了防止数据权限以及查找上级外场都为空的情况，防止SQL出错
					if(dto!=null){//sonar-352203
					dto.setExternalOrgCode(user.getCurrentDeptCode());
					dto.setExternalOrgCodeList(null);
					}
				}else{
					if(dto!=null){
					dto.setExternalOrgCode(null);
					dto.setExternalOrgCodeList(externalOrgCodeList);
					}
				}
				// 查询外发单信息列表 + 运单信息
				List<LdpExternalBillDto> externalBillList = ldpExternalBillService.queryExternalBillInfoList(dto, true);
				// 导出文件名
				fileName = URLEncoder.encode(PartiallineConstants.EXPORT_LDP_EXTERNALBILL_SHEET_NAME, "UTF-8");
				// 导出文件流
				excelStream = ldpExternalBillService.exportLdpExternalBill(externalBillList, vo);

			}
			return super.returnSuccess();
		} catch (ExternalBillException e) {
			return super.returnError(e);
		} catch (UnsupportedEncodingException e) {
			return super.returnSuccess("导出文件名出错");
		}

	}
	
	public String queryLdpExternalBillDetail(){
		try {
			LOGGER.info("根据前端传入查询参数查询已经录入的快递代理外发单列表");
			LdpExternalBillDto ldpExternalBillDto = ldpExternalBillService.queryLdpExternalBillDetail(vo.getDto().getId());
			vo.setDto(ldpExternalBillDto);
			
			return super.returnSuccess();
		} catch (BusinessException e) {
			return super.returnError(e);
		}
	}

	/**
	 * 设置 落地配录入service注入.
	 * @author ibm-liuzhaowei
	 * @param ldpExternalBillService
	 *            the new 落地配录入service注入
	 */
	public void setLdpExternalBillService(ILdpExternalBillService ldpExternalBillService) {
		this.ldpExternalBillService = ldpExternalBillService;
	}

	/**
	 * 设置 未录入查询service注入.
	 * 
	 * @param uninputLdpExternalBillService
	 *            the new 未录入查询service注入
	 */
	public void setUninputLdpExternalBillService(IUninputLdpExternalBillService uninputLdpExternalBillService) {
		this.uninputLdpExternalBillService = uninputLdpExternalBillService;
	}

	/**
	 * 设置 人员信息Service.
	 * @author ibm-liuzhaowei
	 * @param employeeService
	 *            the new 人员信息Service
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/**
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
	 */
	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * 获取 前端数据Vo.
	 * 
	 * @return the 前端数据Vo
	 */
	public LdpExternalBillVo getVo() {
		return vo;
	}

	/**
	 * 设置 前端数据Vo.
	 * 
	 * @param vo
	 *            the new 前端数据Vo
	 */
	public void setVo(LdpExternalBillVo vo) {
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
	
	public void setUserDeptAuthorityService(IUserDeptAuthorityService userDeptAuthorityService) {
		this.userDeptAuthorityService = userDeptAuthorityService;
	}

	public void setWaybillRelateDetailEntityService(
			IWaybillRelateDetailEntityService waybillRelateDetailEntityService) {
		this.waybillRelateDetailEntityService = waybillRelateDetailEntityService;
	}

	public void setInputWeightVolumnService(
			IInputWeightVolumnService inputWeightVolumnService) {
		this.inputWeightVolumnService = inputWeightVolumnService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
}