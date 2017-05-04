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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/action/AuditSignRfcAction.java
 * 
 * FILE NAME        	: AuditSignRfcAction.java
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
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignChangeService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcEntity;
import com.deppon.foss.module.pickup.sign.api.shared.vo.ChangeSignResultVo;

/**
 * <p>
 * 审核变更签收结果<br />
 * </p>
 * 
 * @title AuditSignRfcAction.java
 * @package com.deppon.foss.module.pickup.sign.server.action
 * @author ibm-lizhiguo
 * @version 0.1 2012-12-6
 */
public class AuditSignRfcAction extends AbstractAction {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	// Logger
	private static final Logger LOGGER = LoggerFactory.getLogger(AuditSignRfcAction.class);
	//申请签收变更service
	private ISignChangeService signChangeService;

	//vo
	private ChangeSignResultVo vo;

	/**
	 *  set对象
	 */
	public void setSignChangeService(ISignChangeService signChangeService) {
		this.signChangeService = signChangeService;
	}

	/**
	 *  get对象
	 */
	public ChangeSignResultVo getVo() {
		return vo;
	}

	/**
	 *  set对象
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
	public String queryAuditSignRfcList() {
		LOGGER.info("queryAuditSignRfcList() begin.....");
		try {
			//获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//获得查询条件
			SignRfcEntity entity = vo.getSignResultDto().getSignRfcEntity();
			//设置查询条件
			entity.setStatus(SignConstants.SIGN_RFC_SIGN_APPROVALIN);
			//获得查询数据
			List<SignRfcEntity> signRfcList = signChangeService.searchSignRfcList(vo.getSignResultDto().getSignRfcEntity(), this.getStart(), this.getLimit(),currentInfo);
			//把查询的数据设置到返回结果中
			vo.setSignRfcList(signRfcList);
			//获得查询的总条数
			Long totalCount = signChangeService.getSignRfcCount(vo.getSignResultDto().getSignRfcEntity(),currentInfo);
			//设置总条数到界面
			this.setTotalCount(totalCount);
			//异常
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
		LOGGER.info("queryAuditSignRfcList() end.....");
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
	public String queryAuditSignRfcDetail() {
		LOGGER.info("queryAuditSignRfcDetail() begin.....");
		try {
			//获得审核数据详细
			vo.setSignResultDto(signChangeService.getSignDetail(vo.getSignResultDto()));
			//异常
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
		LOGGER.info("queryAuditSignRfcDetail() end.....");
		// 返回success
		return returnSuccess();
	}

	/**
	 * 
	 * <p>
	 * 同意申请<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-6
	 * @return String
	 */
	@JSON
	public String agree() {
		LOGGER.info("agree() begin.....");
		try {
			//获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//同意操作
			signChangeService.agree(vo.getSignResultDto().getSignRfcEntity().getId(),
					vo.getSignResultDto().getSignRfcEntity().getNotes(),
					currentInfo);
			//异常
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
		LOGGER.info("agree() end.....");
		// 返回success
		return returnSuccess();
	}

	/**
	 * 
	 * <p>
	 * 拒绝申请<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-6
	 * @return String
	 */
	@JSON
	public String refuse() {
		LOGGER.info("refuse() begin.....");
		try {
			//获取当前用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//拒绝操作
			signChangeService.refuse(vo.getSignResultDto().getSignRfcEntity().getId(),
					vo.getSignResultDto().getSignRfcEntity().getNotes(),
					currentInfo);
			//异常
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
		LOGGER.info("refuse() end.....");
		// 返回success
		return returnSuccess();
	}

}