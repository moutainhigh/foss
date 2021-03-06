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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/impl/LabeledGoodService.java
 * 
 * FILE NAME        	: LabeledGoodService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.common.client.service.impl;

import java.util.List;

import org.mybatis.guice.transactional.Transactional;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.module.pickup.common.client.dao.ILabeledGoodDao;
import com.deppon.foss.module.pickup.common.client.service.ILabeledGoodService;
import com.deppon.foss.module.pickup.common.client.utils.initUIDialog;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.google.inject.Inject;

/**
 * 
 * 
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-10-30 下午3:46:21,
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-10-30 下午3:46:21
 * @since
 * @version
 */
public class LabeledGoodService implements ILabeledGoodService {

	/**
	 * 货签服务 dao
	 */
	private ILabeledGoodDao labeledGoodDao;
	
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(initUIDialog.class);

	/**
	 * 注入货签服务 dao
	 * @param labeledGoodDao
	 */
	@Inject
	public void setLabeledGoodDao(ILabeledGoodDao labeledGoodDao) {
		this.labeledGoodDao = labeledGoodDao;
	}

	/**
	 * 
	 * <p>
	 * 插入货签信息
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午3:46:50
	 * @param waybillNo
	 * @param serialNoList
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodService#insertSerialNo(java.lang.String,
	 *      java.util.List)
	 */
	@Transactional
	public ResultDto insertSerialNo(LabeledGoodEntity labeledGoods,
			List<String> serialNoList) {
	
		String code = "1";
		String msg = "";
		if (labeledGoods != null) {
			for (String serialNo : serialNoList) {
				labeledGoods.setSerialNo(serialNo);
				labeledGoodDao.insertSelective(labeledGoods);
			}
		} else {
			code = "0";
			msg = i18n.get("foss.gui.common.LabeledGoodService.msgBox.numNotExist");
		}
		ResultDto res = new ResultDto();
		res.setCode(code);
		res.setMsg(msg);
		return res;
	}



	/**
	 * 
	 * <p>
	 * 通过运单号查询最大的序列号
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-30 下午4:55:34
	 * @param waybillNo
	 * @return
	 * @see
	 */
	public LabeledGoodEntity queryLastSerialByWaybillNo(String waybillNo) {
		return labeledGoodDao.queryLastSerialByWaybillNo(waybillNo);
	}

}