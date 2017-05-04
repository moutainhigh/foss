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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/action/ViewSignRfcAction.java
 * 
 * FILE NAME        	: ViewSignRfcAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignChangeService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcEntity;
import com.deppon.foss.module.pickup.sign.api.shared.vo.ChangeSignResultVo;

/**
 * 变更签收ACTION
 * 
 * @author ibm-lizhiguo
 * @date 2012-11-21 上午10:04:35
 */
public class ViewSignRfcAction extends AbstractAction {
	/**
	 */
	private static final long serialVersionUID = 1L;
	// Logger
	private static final Logger LOGGER = LoggerFactory.getLogger(ViewSignRfcAction.class);
	//申请签收变更service
	private ISignChangeService signChangeService;
	//vo
	private ChangeSignResultVo vo;

	/**
	 * set对象
	 */
	public void setSignChangeService(ISignChangeService signChangeService) {
		this.signChangeService = signChangeService;
	}

	/**
	 * get对象
	 */
	public ChangeSignResultVo getVo() {
		return vo;
	}

	/**
	 * set对象
	 */
	public void setVo(ChangeSignResultVo vo) {
		this.vo = vo;
	}

	/**
	 * 
	 * <p>
	 * 查询需要审批的数据List<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-6
	 * @return String
	 */
	public String queryViewSignRfcList() {
		LOGGER.info("queryViewSignRfcList() begin.....");
		try {
			//获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//总数
			Long totalCount = signChangeService.getSignRfcCountView(vo.getSignResultDto().getSignRfcEntity(), currentInfo);
			//设置总行数
			this.setTotalCount(totalCount);
			if (totalCount > 0) {
				//获取返回界面数据
				List<SignRfcEntity> signRfcList = signChangeService.searchSignRfcListForView(vo.getSignResultDto().getSignRfcEntity(), this.getStart(), this.getLimit(), currentInfo);
				//查询结果
				vo.setSignRfcList(signRfcList);
			}
			//异常
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
		LOGGER.info("queryViewSignRfcList() end.....");
		// 返回success
		return returnSuccess();
	}

	/**
	 * 
	 * <p>
	 * 查看需要审核数据详细<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-6
	 * @return String
	 */
	public String queryViewSignRfcDetail() {
		LOGGER.info("queryViewSignRfcDetail() begin.....");
		try {
			//设置查看详细数据
			vo.setSignResultDto(signChangeService.getSignDetail(vo.getSignResultDto()));
			//异常
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
		LOGGER.info("queryViewSignRfcDetail() end.....");
		// 返回success
		return returnSuccess();
	}
}