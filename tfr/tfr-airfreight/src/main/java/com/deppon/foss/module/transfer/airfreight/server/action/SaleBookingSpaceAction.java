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
 *  PROJECT NAME  : tfr-airfreight
 *  
 * package_name  : 
 *  
 *  FILE PATH          :/SaleBookingSpaceAction.java
 * 
 *  FILE NAME          :SaleBookingSpaceAction.java
 *  
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.server.action;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.service.ISaleBookingSpaceService;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.SaleBookingSpaceDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.SaleBookingSpaceVo;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 营业部订舱管理
 * @author 038300-foss-pengzhen
 * @date 2012-11-2 上午8:17:32
 */
public class SaleBookingSpaceAction extends AbstractAction {
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -6173057800764673548L;
	

	/**
	 * 营业部舱位管理service注入
	 */
	private ISaleBookingSpaceService saleBookingSpaceService;
	/**
	 * 营业部舱位管理vo注入
	 */
	private SaleBookingSpaceVo vo = new SaleBookingSpaceVo();
	
	/**
	 * 查询营业部订舱信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-2 下午8:25:55
	 */
	public String queryBookingSpace(){
		SaleBookingSpaceDto dto = new SaleBookingSpaceDto();
		try {
			//注册一个日期转换器,复制日期类型
			ConvertUtils.register(new org.apache.commons.beanutils.converters.SqlDateConverter(null), java.util.Date.class);  
			//属性复制
			BeanUtils.copyProperties(dto, vo);
			//设置dto中的当前用户信息
			fillCurrentUserInfo(dto);
			//查询营业部订舱信息
			vo.setBookingList(saleBookingSpaceService.queryBookingSpace(dto, this.getLimit(), this.getStart()));
			this.setTotalCount(saleBookingSpaceService.queryCount(dto));
			return super.returnSuccess();
			//捕获并抛出异常
		} catch (IllegalAccessException e) {
			return super.returnError(e.getMessage());
		} catch (InvocationTargetException e) {
			return super.returnError(e.getMessage());
		} catch(BusinessException e){
			return super.returnError(e);
		} 
	}
	
	/**
	 * 通过id查询营业部订舱信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-12 上午11:25:40
	 */
	public String queryBookingSpaceById(){
		try{
			//通过id查询营业部订舱信息
			vo.setDto(saleBookingSpaceService.queryBookingSpaceById(vo.getId()));
			return super.returnSuccess();
		}catch(BusinessException e){
			return super.returnError(e);
		}
	}
	
	/**
	 * 新增营业部订舱信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-3 下午5:00:02
	 */
	public String addBookingSpace(){
		try{
			//新增营业部订舱信息
			saleBookingSpaceService.addBookingSpace(vo.getDto());
			return super.returnSuccess();
		}catch(BusinessException e){
			return super.returnError(e);
		}
	}
	
	/**
	 * 更新、修改营业部订舱信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-12 上午11:26:10
	 */
	public String updateBookingSpace(){
		try{
			//更新、修改营业部订舱信息
			saleBookingSpaceService.updateBookingSpace(vo.getDto());
			return super.returnSuccess();
		}catch(BusinessException e){
			return super.returnError(e);
		}
	}
	
	/**
	 * 受理营业部订舱信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-5 上午11:33:08
	 */
	public String acceptBookingSpace(){
		try{
			//受理营业部订舱信息
			saleBookingSpaceService.acceptBookingSpace(vo.isAcceptType(), vo.getAcceptNotes(), vo.getAcceptIds());
			return super.returnSuccess();
		}catch(BusinessException e){
			return super.returnError(e);
		}
		
	}
	
	/**
	 * 初始化界面字段
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-9 下午6:13:12
	 */
	public String initBookingSpace(){
		try{
			//初始化界面字段
			SaleBookingSpaceDto dto = saleBookingSpaceService.initBookingSpace();
			vo.setDto(dto);
			return super.returnSuccess();
		}catch(BusinessException e){
			return super.returnError(e);
		}
	}
	
	/**
	 * 获取总调未受理订舱总条数
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-12 下午5:27:21
	 */
	public String queryNoAcceptCount(){
		try{
			SaleBookingSpaceDto dto = new SaleBookingSpaceDto();
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = saleBookingSpaceService.getOrgAdministrative();
			//判断是否空运总调
			if(FossConstants.YES.equals(orgAdministrativeInfoEntity.getDoAirDispatch())){
				//受理部门为当前部门
				dto.setAcceptOrgCode(orgAdministrativeInfoEntity.getCode());
			}else{
				//申请部门为当前部门
				dto.setApplyOrgCode(orgAdministrativeInfoEntity.getCode());
			}
			//获取总调未受理订舱总条数
			vo.setNoAcceptCount(saleBookingSpaceService.queryNoAcceptCount(dto));
			return super.returnSuccess();
		}catch(BusinessException e){
			return super.returnError(e);
		}
	}

	/**
	 * 设置dto中的当前用户信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-7 上午11:46:23
	 */
	public void fillCurrentUserInfo(SaleBookingSpaceDto dto){
		//从context中获取用户信息
		CurrentInfo userInfo  = FossUserContext.getCurrentInfo();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = saleBookingSpaceService.getOrgAdministrative();
		//如果用户信息不为空则进行填充至dto,否则抛出异常
		if(userInfo != null && userInfo.getDept() != null){
			//创建人编号
			dto.setCreateUserCode(userInfo.getEmpCode());
			//创建人姓名
			dto.setCreateUserName(userInfo.getEmpName());
			//修改人编号
			dto.setModifyUserCode(userInfo.getEmpCode());
			//修改人姓名
			dto.setModifyUserName(userInfo.getEmpName());
			//判断是否空运总调
			if(FossConstants.YES.equals(orgAdministrativeInfoEntity.getDoAirDispatch())){
				if(StringUtil.isBlank(dto.getAcceptOrgCode())){
					//受理部门
					dto.setAcceptOrgCode(dto.getAcceptOrgCode());
				}
			}else{
				if(StringUtil.isBlank(dto.getApplyOrgCode())){
					//申请部门
					dto.setApplyOrgCode(dto.getApplyOrgCode());
				}
			}
		}else{
			//抛出异常
			throw new TfrBusinessException(TfrBusinessException.GET_CURRENTUSERINFO_FAILURE);
		}
	}
	
	/**
	 * 初始化订舱查询表单信息 
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-9 下午2:53:33
	 */
	public String initBookingQueryForm(){
		vo.setCreateTimeFrom(DateUtils.getEndDatetime(new Date(), -1));
		vo.setCreateTimeTo(DateUtils.getEndDatetime(new Date()));
		//默认界面为未受理
		vo.setAcceptState(AirfreightConstants.UN_ACCEPT);
		return super.returnSuccess();
	}
	
	/**
	 * 设置 营业部舱位管理service注入.
	 *
	 * @param saleBookingSpaceService the new 营业部舱位管理service注入
	 */
	public void setSaleBookingSpaceService(
			ISaleBookingSpaceService saleBookingSpaceService) {
		this.saleBookingSpaceService = saleBookingSpaceService;
	}

	/**
	 * 获取 营业部舱位管理vo注入.
	 *
	 * @return the 营业部舱位管理vo注入
	 */
	public SaleBookingSpaceVo getVo() {
		return vo;
	}

	/**
	 * 设置 营业部舱位管理vo注入.
	 *
	 * @param vo the new 营业部舱位管理vo注入
	 */
	public void setVo(SaleBookingSpaceVo vo) {
		this.vo = vo;
	}
	
	/**
	 * 根据空运总调调出始发站
	 * @author foss-liuxue(for IBM)
	 * @date 2013-1-24 下午8:45:25
	 */
	@JSON
	public String queryDeptRegion(){
		try{
			//根据空运总调调出始发站
			vo.setDto(saleBookingSpaceService.queryDeptRegion(vo.getAcceptOrgCode()));
			return super.returnSuccess();
		}catch(BusinessException e){
			return super.returnError(e);
		}
	}
	
	/**
	 * 根据运单号查询需要订舱的信息
	 * @author foss-liuxue(for IBM)
	 * @date 2013-5-21 下午4:35:38
	 */
	@JSON
	public String queryWaybillInfo(){
		try{
			vo.setDto(saleBookingSpaceService.queryWaybillInfo(vo.getWaybillNo()));
			return super.returnSuccess();
		}catch(BusinessException e){
			return super.returnError(e);
		}
	}
	
}