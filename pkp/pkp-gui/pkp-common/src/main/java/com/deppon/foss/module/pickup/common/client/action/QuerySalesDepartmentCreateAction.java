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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/action/QuerySalesDepartmentCreateAction.java
 * 
 * FILE NAME        	: QuerySalesDepartmentCreateAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-creating
 * PACKAGE NAME: com.deppon.foss.module.pickup.creating.client.action
 * FILE    NAME: WaybillSaveAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.common.client.action;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;

import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.binding.IBinder;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService;
import com.deppon.foss.module.pickup.common.client.ui.QueryPublishPriceUI;
import com.deppon.foss.module.pickup.common.client.ui.dialog.SalesDepartmentDialog;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.QueryPublishPriceVo;
import com.deppon.foss.module.pickup.common.client.vo.ValueCopy;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;

/**
 * （查询收货部门）
 * 
 * @author 025000-foss-helong
 * @date 2012-11-1 下午7:59:52
 */
public class QuerySalesDepartmentCreateAction implements IButtonActionListener<QueryPublishPriceUI> {

	// 主界面
	private QueryPublishPriceUI ui;

	private IBaseDataService baseDataService = 
			GuiceContextFactroy.getInjector().getInstance(BaseDataService.class);
	
	@Override
	public void actionPerformed(ActionEvent e) {

		Map<String, IBinder<QueryPublishPriceVo>> map = ui.getBindersMap();
		IBinder<QueryPublishPriceVo> waybillBinder = map.get("queryPublishPriceBinder");
		QueryPublishPriceVo bean = waybillBinder.getBean();

		try {
			SalesDepartmentDialog dialog = new SalesDepartmentDialog(false, null);
			// 剧中显示弹出窗口
			WindowUtil.centerAndShow(dialog);

			SaleDepartmentEntity entity = dialog.getSaleDepartmentEntity();
			if (entity != null) {
				//收货部门编号
				bean.setCreateOrgCode(entity.getCode());
				//收货部门名称
				bean.setCreateOrgName(entity.getName());
				
				DefaultComboBoxModel productTypeModel = new DefaultComboBoxModel();
				
				List<ProductEntity> list =baseDataService.queryTransType(entity.getCode());
				
				for (ProductEntity product : list) {
					if(!ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(product.getCode()))
					{
						ProductEntityVo vo = new ProductEntityVo();
						//将数据库查询出的产品对象进行转换，转成VO使用的对象
						ValueCopy.entityValueCopy(product, vo);
						vo.setDestNetType(product.getDestNetType());
						productTypeModel.addElement(vo);
						
					}
				}
				
			}
		} catch (WaybillValidateException w) {
			MsgBox.showInfo(w.getMessage());
		}
	}
	
	

	@Override
	public void setInjectUI(QueryPublishPriceUI ui) {
		this.ui = ui;
	}

}