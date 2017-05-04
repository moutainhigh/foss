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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/prt/deliverbill/DeliverbillDataSource.java
 * 
 * FILE NAME        	: DeliverbillDataSource.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.prt.todoAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.ApplicationContext;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.waybill.api.server.service.ITodoActionService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodTodoDto;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;


/**
 * 
 * 更改明细
 * 
 * @author ibm-wangfei
 * @date Nov 16, 2012 1:39:56 PM
 */
public class TodoActionDataSource implements JasperDataSource {

	static String split = " ";

	@Override
	public Map<String, Object> createParameterDataSource(JasperContext jasperContext) throws Exception {
		// 接受打印map
		Map<String, Object> parameter = new HashMap<String, Object>();
		String waybillNo = (String) jasperContext.get("waybillNo");
		parameter.put("waybillNo", waybillNo + split);
		return parameter;
	}

	@Override
	public List<Map<String, Object>> createDetailDataSource(JasperContext jasperContext) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> parameter;
		String waybillRfcId = (String) jasperContext.get("waybillRfcId");
		String currentUserDepCode = (String) jasperContext.get("currentUserDepCode");
		ApplicationContext applicationContext = jasperContext.getApplicationContext();
		ITodoActionService todoActionService = (ITodoActionService) applicationContext.getBean("todoActionService");
		LabeledGoodTodoDto labeledGoodTodoDto = todoActionService.queryTodoAction(waybillRfcId, currentUserDepCode);
		if (labeledGoodTodoDto != null && CollectionUtils.isNotEmpty(labeledGoodTodoDto.getWaybillRfcChangeDetailEntityList())) {
			List<WaybillRfcChangeDetailEntity> waybillRfcChangeDetailEntitys = labeledGoodTodoDto.getWaybillRfcChangeDetailEntityList();
			for (WaybillRfcChangeDetailEntity item : waybillRfcChangeDetailEntitys) {
				parameter = new HashMap<String, Object>();
				parameter.put("rfcItem", defaultIfNull(item.getRfcItems()) + split);// 变更项
				parameter.put("beforeRfcInfo", defaultIfNull(item.getBeforeRfcInfo()) + split);// 变更前信息
				parameter.put("afterRfcInfo", defaultIfNull(item.getAfterRfcInfo()) + split);// 变更后信息
				list.add(parameter);
			}
		} else {
			// 对于没有明细记录的信息，添加空map
			parameter = new HashMap<String, Object>();
			list.add(parameter);
		}
		return list;
	}

	private String defaultIfNull(String str) {
		return StringUtil.defaultIfNull(str);
	}
}