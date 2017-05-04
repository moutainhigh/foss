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
 * PROJECT NAME	: pkp-sign
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/action/SignAction.java
 * 
 * FILE NAME        	: SignAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.ArriveSheetVo;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.sign.api.shared.vo.SignDetailVo;
import com.deppon.foss.module.pickup.sign.api.shared.vo.SignVo;
import com.deppon.foss.module.pickup.sign.api.shared.vo.StockVo;

/**
 * 签收出库Action
 * @author foss-meiying
 * @date 2012-10-17 上午10:36:08
 * @since
 * @version
 */
public class SignAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 签收出库service
	 */
	private ISignService signService;
	/**
	 * 签收出库vo
	 */
	private SignVo signVo = new SignVo();
	/**
	 * 库存vo
	 */
	private StockVo stockVo = new StockVo();
	/**
	 * 签收明细vo
	 */
	private SignDetailVo signDetailVo = new SignDetailVo();

	/**
	 * 定义查询签收出库界面的库存到达联
	 */

	private ArriveSheetVo arriveSheetVo = new ArriveSheetVo();

	/**
	 * 返回状态提醒
	 */
	private String stateMsg;

	/**
	 * 
	 * 签收出库--查询到达联
	 * @author foss-meiying
	 * @date 2012-10-17 上午10:41:13
	 * @return
	 * @see
	 */
	@JSON
	public String queryArriveSheetInfo() {
		try {
			// 当前登录人的部门编码
			signVo.getSignDto().setLastLoadOrgCode(FossUserContext.getCurrentDeptCode());
			arriveSheetVo=signService.queryArriveSheetByParams(signVo.getSignDto(),this.getStart(), this.getLimit());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<SignArriveSheetDto> signArriveSheetDtoList=arriveSheetVo.getSignArriveSheetDtoList();
			if(signArriveSheetDtoList!=null){
				for(int i=0;i<signArriveSheetDtoList.size();i++){
					SignArriveSheetDto tmp=signArriveSheetDtoList.get(i);
					tmp.setServiceTime(sdf.format(new Date()));//将服务器现在时间传到页面显示
				}
			}
			this.totalCount=arriveSheetVo.getTotalCount();
		} catch (BusinessException e) {
			// 返回错误信息
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 
	 * 提交录入的签收信息
	 * @author foss-meiying
	 * @date 2012-10-17 上午10:43:00
	 * @return
	 * @see
	 */
	@JSON
	public String addSign() {
		try {
			// 获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 录入签收信息到签收明细里
			String resultMsg = signService.addSign(signDetailVo.getSignDetailList(),arriveSheetVo.getArriveSheet(), signVo.getLineSignDto(), currentInfo,signVo.getOrderNo());
			if (StringUtils.isNotBlank(resultMsg)) {
				return returnSuccess(resultMsg);
			}
		} catch (BusinessException e) {
			// 处理异常
			return returnError(e);
		}
		// 返回成功
		return returnSuccess(SignException.SIGN_SUCCESS);//签收出库成功
	}

	/**
	 * 查询运单的货物流水号
	 * @author foss-meiying
	 * @date 2012-10-17 上午10:42:43
	 * @return
	 * @see
	 */
	@JSON
	public String querySerialNo() {
		try {
			// 获取当前用户登录的部门编码
			signVo.getSignDto().setLastLoadOrgCode(FossUserContext.getCurrentDeptCode());
			// 根据运单编号
			stockVo.setStockDtoList(signService.queryStock(signVo.getSignDto()));
		} catch (SignException e) {
			// 返回错误信息
			return returnError(e);
		}
		// 返回成功
		return returnSuccess();
	}

	/**
	 * 提前找货
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.action.SignAction#updateInadvanceGoodsBySign()
	 */
	@JSON
	public String updateInadvanceGoodsBySign(){
		try {
			 stateMsg=signService.updateInadvanceGoodsBySign(signVo.getSignDto());
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 根据到达联号和流水号查询SignDetailEntity
	 * @date 2014-12-27 下午3:58:10
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.action.SignAction#signSituationByArrivesheetNoSerialNo()
	 */
	@JSON
	public String signSituationByArrivesheetNoSerialNo(){
		try {
			 List<SignDetailEntity> detailEntities= signDetailVo.getSignDetailList();
			 List<String> list=new ArrayList<String>();
			 if(detailEntities!=null){
				 Iterator<SignDetailEntity> it=detailEntities.iterator();
				 while(it.hasNext()){
					 List<SignDetailEntity> tmps=signService.signSituationByArrivesheetNoSerialNo(it.next());
					 if(tmps !=null && tmps.size()>0){
						 if(tmps.get(0).getSituation()==null){
							 list.add("");
						 }else{
							 list.add(tmps.get(0).getSituation());
						 }
					 }
				 }
			 }
			 signDetailVo.setSituationList(list);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	
	
	
	
	
	
	/**
	 * Sets the 签收出库service.
	 *
	 * @param signService the new 签收出库service
	 */
	public void setSignService(ISignService signService) {
		this.signService = signService;
	}
	
	/**
	 * Gets the 签收出库vo.
	 *
	 * @return the 签收出库vo
	 */
	public SignVo getSignVo() {
		return signVo;
	}
	
	/**
	 * Sets the 签收出库vo.
	 *
	 * @param signVo the new 签收出库vo
	 */
	public void setSignVo(SignVo signVo) {
		this.signVo = signVo;
	}
	
	/**
	 * Gets the 定义查询签收出库界面的库存到达联.
	 *
	 * @return the 定义查询签收出库界面的库存到达联
	 */
	public ArriveSheetVo getArriveSheetVo() {
		return arriveSheetVo;
	}
	
	/**
	 * Sets the 定义查询签收出库界面的库存到达联.
	 *
	 * @param arriveSheetVo the new 定义查询签收出库界面的库存到达联
	 */
	public void setArriveSheetVo(ArriveSheetVo arriveSheetVo) {
		this.arriveSheetVo = arriveSheetVo;
	}
	
	/**
	 * Gets the 库存vo.
	 *
	 * @return the 库存vo
	 */
	public StockVo getStockVo() {
		return stockVo;
	}
	
	/**
	 * Sets the 库存vo.
	 *
	 * @param stockVo the new 库存vo
	 */
	public void setStockVo(StockVo stockVo) {
		this.stockVo = stockVo;
	}
	
	/**
	 * Gets the 签收明细vo.
	 *
	 * @return the 签收明细vo
	 */
	public SignDetailVo getSignDetailVo() {
		return signDetailVo;
	}
	
	/**
	 * Sets the 签收明细vo.
	 *
	 * @param signDetailVo the new 签收明细vo
	 */
	public void setSignDetailVo(SignDetailVo signDetailVo) {
		this.signDetailVo = signDetailVo;
	}

	public String getStateMsg() {
		return stateMsg;
	}

	public void setStateMsg(String stateMsg) {
		this.stateMsg = stateMsg;
	}
	
}