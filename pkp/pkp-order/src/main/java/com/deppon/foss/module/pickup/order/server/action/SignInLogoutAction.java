/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-order
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/server/action/SignInLogoutAction.java
 * 
 * FILE NAME        	: SignInLogoutAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity;
import com.deppon.foss.module.pickup.order.api.server.service.ISignInAndLogOutService;
import com.deppon.foss.module.pickup.order.api.shared.define.ActionMessageType;
import com.deppon.foss.module.pickup.order.api.shared.define.PdaSignStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.dto.PdaSignDto;
import com.deppon.foss.module.pickup.order.api.shared.exception.DispatchException;
import com.deppon.foss.module.pickup.order.api.shared.vo.SignInAndLogOutVo;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.util.define.FossConstants;

/**
 * 调度解除司机签到
 * @author 097972-foss-dengtingting
 * @date 2012-10-23 下午2:33:28
 */
public class SignInLogoutAction extends AbstractAction{
	// 序列id
	private static final long serialVersionUID = 1L;
	// 签到注销Vo
	private SignInAndLogOutVo vo;
	// 签到注销服务
	private ISignInAndLogOutService signInAndLogOutService;
	/**
	 * 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 部门 复杂查询 service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * 营业部车队对应
	 */
	private ISalesMotorcadeService salesMotorcadeService;
	/** 
	 * 部门查询起始. 
	 */
	private static final int BEGIN_NUM = 0;
	/** 
	 * 派送部查询页面大小. 
	 */
	private static final int PAGE_SIZE = 100;
	/**
	 * 车队编码
	 */
	private String fleetCode;

	
	/**
	 * 根据条件查询PDA签到信息.
	 * 
	 * @return the string
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-19 下午5:34:15
	 */
	@JSON
	public String querySignedInfo(){
		try {
			PdaSignDto dto =signInAndLogOutService.refreshPdaSignDto(vo.getDto());
			
			//add by 329757 添加外请车查询信息	
			List<PdaSignDto> pdasignList =  new ArrayList<PdaSignDto>();
				//查询自有车信息
				Long count = signInAndLogOutService.querySignedInfoCount(dto);
				if(count > 0){
					// 查询签到信息
					this.setTotalCount(count);
					pdasignList.addAll(signInAndLogOutService.querySignedInfoByPage(dto, this.getStart(), this.getLimit()));
				}
				//查询外请车
				count = signInAndLogOutService.querySignedInfoCountForLeased(dto);
				if(count > 0){
					// 查询签到信息
					this.setTotalCount(count);
					pdasignList.addAll(signInAndLogOutService.querySignedInfoByPageForLeased(dto, this.getStart(), this.getLimit()));
				}
				vo.setPdasignList(pdasignList);
			}catch (DispatchException e) {
			// 返回失败信息
			return returnError(e);
		}
		// 成功
		return returnSuccess();
	}
	
	/**
	 * 根据ID解除司机签到信息.
	 * 
	 * @return the string
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-19 下午7:01:17
	 */
	@JSON
	public String handResolveBind(){
		try {
			// 解除绑定状态
			vo.getPdaEntity().setStatus(PdaSignStatusConstants.UNBUNDLE);
			// 解除绑定
			signInAndLogOutService.handResolveBind(vo.getPdaEntity());
		} catch (DispatchException e) {
			// 返回失败信息
			return returnError(e);
		}
		// 成功
		return returnSuccess(ActionMessageType.UNBUNDLE_SUCCESS);
	}

	/**
	 * 获取组织所对应的车队.
	 *
	 * @return the string
	 * @author 043258-foss-zhaobin
	 * @date 2013-1-22 上午10:50:18
	 */
	@JSON
	public String querySuperOrg() {
		String orgCode = FossUserContextHelper.getOrgCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(orgCode);
		if(orgAdministrativeInfoEntity != null)
		{
			if(FossConstants.YES.equals(orgAdministrativeInfoEntity.getSalesDepartment()))
			{
				//如果是营业部派送部 则通过营业部-车队对应关系表获取车队code
				SalesMotorcadeEntity entity = new SalesMotorcadeEntity();
				entity.setSalesdeptCode(orgCode);
				List<SalesMotorcadeEntity> salesMotorcadeList = salesMotorcadeService.querySalesMotorcadeExactByEntity(entity, BEGIN_NUM, PAGE_SIZE);
				if (!CollectionUtils.isEmpty(salesMotorcadeList))
				{
					StringBuffer sb = new StringBuffer();
					for (SalesMotorcadeEntity salesMotorcadeEntity : salesMotorcadeList) 
					{
							sb.append(salesMotorcadeEntity.getMotorcadeCode()) ;
							sb.append(",");
					}
					fleetCode=StringUtil.isNotEmpty(sb.toString()) ? sb.substring(0, sb.length()-1) :null;
				}else
				{
					fleetCode = FossUserContextHelper.getOrgCode();
				}
			}else{
				// 调用综合组的服务获取当前组织所在的车队
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity1 = orgAdministrativeInfoComplexService.getTopFleetByCode(orgCode);
				if (orgAdministrativeInfoEntity1 != null) 
				{
					fleetCode = orgAdministrativeInfoEntity1.getCode();
				}else
				{
					fleetCode = FossUserContextHelper.getOrgCode();
				}
			}
		}
		//返回成功
		return returnSuccess();
	}
	
	/**
	 * Sets the signInAndLogOutService.
	 * 
	 * @param signInAndLogOutService the signInAndLogOutService to see
	 */
	public void setSignInAndLogOutService(
			ISignInAndLogOutService signInAndLogOutService) {
		this.signInAndLogOutService = signInAndLogOutService;
	}

	/**
	 * Gets the vo.
	 * 
	 * @return the vo
	 */
	public SignInAndLogOutVo getVo() {
		return vo;
	}

	/**
	 * Sets the vo.
	 * 
	 * @param vo the vo to see
	 */
	public void setVo(SignInAndLogOutVo vo) {
		this.vo = vo;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setSalesMotorcadeService(
			ISalesMotorcadeService salesMotorcadeService) {
		this.salesMotorcadeService = salesMotorcadeService;
	}

	public String getFleetCode() {
		return fleetCode;
	}

	public void setFleetCode(String fleetCode) {
		this.fleetCode = fleetCode;
	}
}