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
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-exceptiongoods
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/exceptiongoods/server/action/ContrabandGoodsAction.java
 *  
 *  FILE NAME          :ContrabandGoodsAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.exceptiongoods.server.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.exceptiongoods.api.define.ExceptionGoodsConstants;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IContrabandGoodsService;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.ContrabandGoodsEntity;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.vo.ContrabandGoodsVo;
/**
 * 违禁品界面
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 下午12:16:15
 */
public class ContrabandGoodsAction extends AbstractAction{

	/**
	* @fields serialVersionUID
	*/
	private static final long serialVersionUID = -7892992968232527644L;
	
	/**
	 * 违禁品Service
	 */
	IContrabandGoodsService contrabandGoodsService;
	
	/**
	 * 违禁品VO
	 */
	ContrabandGoodsVo contrabandGoodsVo = new ContrabandGoodsVo();
	
	/**
	* @param contrabandGoodsService
	* @description 设置违禁品Service
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:29:41
	*/
	public void setContrabandGoodsService(
			IContrabandGoodsService contrabandGoodsService) {
		this.contrabandGoodsService = contrabandGoodsService;
	}

	/**
	* @return
	* @description 获取违禁品VO
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:30:04
	*/
	public ContrabandGoodsVo getContrabandGoodsVo() {
		return contrabandGoodsVo;
	}

	/**
	* @param contrabandGoodsVo
	* @description 设置违禁品VO
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:30:14
	*/
	public void setContrabandGoodsVo(ContrabandGoodsVo contrabandGoodsVo) {
		this.contrabandGoodsVo = contrabandGoodsVo;
	}

	/**
	 * 查询违禁品
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-5 下午4:41:07
	 */
	@JSON
	public String queryContrabandGoods(){
		List<ContrabandGoodsEntity> list = contrabandGoodsService.queryContrabandGoods(contrabandGoodsVo.getContrabandGoods(), FossUserContext.getCurrentDeptCode(), this.getLimit(), this.getStart());
		contrabandGoodsVo.setContrabandGoodsList(list);
		this.setTotalCount(contrabandGoodsService.queryContrabandGoodsCount(contrabandGoodsVo.getContrabandGoods(), FossUserContext.getCurrentDeptCode()));
		return returnSuccess();
	}
	/**
	 * 移交违禁品
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-17 下午6:36:45
	 */
	@JSON
	public String handoverContrabandGoods(){
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		String userName = FossUserContext.getCurrentUser().getEmployee().getEmpName();
		try{
			contrabandGoodsService.handoverContrabandGoods(contrabandGoodsVo.getContrabandGoodsList(), userCode, userName);
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 判断部门类型是否是营业部
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-18 上午11:19:09
	 */
	@JSON
	public String queryCurrentOrgType(){
		//部门类型
		String orgType = contrabandGoodsService.queryCurrentOrgType(FossUserContext.getCurrentDept());
		contrabandGoodsVo.setOrgType(orgType);
		//非营业部
		if(!StringUtils.equals(ExceptionGoodsConstants.SALE_ORG, orgType)){
			try{
				//查询当前用户所属外场
				OrgAdministrativeInfoEntity transferCenterOrg = contrabandGoodsService.queryTransferCenterCode(FossUserContext.getCurrentDept());
				contrabandGoodsVo.setTransferCenterCode(transferCenterOrg.getCode());
				contrabandGoodsVo.setTransferCenterName(transferCenterOrg.getName());
			}catch(BusinessException e){
				return returnError(e);
			}
		}
		return returnSuccess();
	}
}