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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/SalesDeptSearchAction.java
 * 
 * FILE NAME        	: SalesDeptSearchAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JCheckBox;

import org.apache.commons.collections.CollectionUtils;
import org.jdesktop.swingx.JXTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.WaybillValidateException;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.creating.client.service.ISalesDeptWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillOffLinePendingService;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.creating.client.service.impl.WaybillOffLinePendingService;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpLinkTableModePrint;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpPrintSignCheckBoxColumn;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpPrintSignUI;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.google.inject.Inject;

/**
 * 查询pending表
 * @author Foss-280747-Zhuxue
 *
 */
@SuppressWarnings("rawtypes")
public class ExpLabelPrintSearchAction extends AbstractButtonActionListener<ExpPrintSignUI> {

	// 日志
	public static final Logger LOGGER = LoggerFactory.getLogger(ExpLabelPrintSearchAction.class);

	// 国际化
	private static final II18n i18n = I18nManager.getI18n(ExpLabelPrintSearchAction.class);
	
	@Inject
	private ISalesDeptWaybillService salesDeptWaybillService;

	
	@Inject
	IWaybillOffLinePendingService offLinePendingService;

	@Inject
	private IWaybillService wayBillService;

	private ExpPrintSignUI ui;

	private List list;

	private List pendingList;

	private DataDictionaryValueVo vo;
	
	// 全选
	private JCheckBox allSelectCheckBox;

	private Object btnPrint5;
	
	
	
	public JCheckBox getAllSelectCheckBox() {
		return allSelectCheckBox;
	}



	public void setAllSelectCheckBox(JCheckBox allSelectCheckBox) {
		this.allSelectCheckBox = allSelectCheckBox;
	}



	@Override
	public void setIInjectUI(ExpPrintSignUI ui) {
		this.ui = ui;
	}



	@SuppressWarnings({ "static-access", "unchecked" })
	public void actionPerformed(ActionEvent e) {
		try {
			boolean all = false;// 是否已全部查询完毕

			// 制单时间
			if (true) {
				// 制单时间天数
				long zdiff = ui.getZdEndDate().getDate().getTime() - ui.getZdStartDate().getDate().getTime();
				// 查询天数
				double days = zdiff / (1000.0 * 60.0 * 60.0 * 24.0);
				// 查询天数不能超过1天
				if (days > 1) {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.salesDeptSearchAction.exception.createTime")
							+ i18n.get("foss.gui.creating.salesDeptSearchAction.exception.overTimeSpanln"));
				}
				salesDeptWaybillService = WaybillServiceFactory.getSalesDeptWaybillService();
				offLinePendingService = GuiceContextFactroy.getInjector().getInstance(WaybillOffLinePendingService.class);
				wayBillService = WaybillServiceFactory.getWaybillService();
				final JXTable table = ui.getTable();
				ExpLinkTableModePrint tableModel;
				list = queryWaybillPending(ui, false);
	            Object[][] datas = ui.getArray(list, 1);
				// 刷新表格
				tableModel = new ExpLinkTableModePrint(datas);
				table.setModel(tableModel);
				ExpPrintSignCheckBoxColumn checkColumn = new ExpPrintSignCheckBoxColumn(table.getColumn(i18n.get("foss.gui.creating.linkTableMode.column.zero")), ui);
				List<JCheckBox> list1 = checkColumn.getRenderButtonList();
				ui.setList(list1);
				ui.setCheckBoxColumn(checkColumn);
//				ui.getAllSelectCheckBox().setSelected(true);
				//设置发货客户的大客户标记
				ui.refreshTable(table);
			} 
		} catch (BusinessException ee) {
			LOGGER.error("sale deptsearch BusinessException", ee);
			MsgBox.showError(ee.getMessage());
		}
	}


	/**
	 * 查询WaybillPending表
	 */
	@SuppressWarnings("unchecked")
	private List queryWaybillPending(ExpPrintSignUI ui, boolean flag) {
		List list = null;
		// 待补录运单
		//WaybillPendingDto pendingDto = new WaybillPendingDto();
		// 待补录运单基本信息
		//WaybillPendingEntity pendingEntity = new WaybillPendingEntity();
	//	WaybillInfoDto waybillInfoDto = new WaybillInfoDto();

		// 运单、订单号
		String createUserCode = ui.getCreateUserCode().getText();
		/*pendingEntity.setCreateUserCode(createUserCode);
		if (StringUtil.isNotEmpty(createUserCode) ) {

			// 制单时间
			pendingDto.setBillStartTime(ui.getZdStartDate().getDate());
			pendingDto.setBillEndTime(ui.getZdEndDate().getDate());
		}else{
			//当开单人不输入的时候，直接返回
			return null;
		}


		pendingDto.setWaybillPending(pendingEntity);
		if (flag) {// 是否离线运单
			//离线查询
			pendingList = offLinePendingService.queryPendingExpress(pendingDto);
		} else {
			//在线查询
		    pendingList = salesDeptWaybillService.queryPendingExpress(pendingDto);
		}

		// 判断集合是否为空
		if (CollectionUtils.isNotEmpty(list)) {
			//list.addAll(pendingList);
		} else {
			//list = pendingList;
		}
		return list;
	}
	*/
	
	WaybillDto waybillDto =new WaybillDto();
	WaybillEntity waybillEntity = new WaybillEntity();
	// 运单、订单号
	waybillEntity.setCreateUserCode(createUserCode);
	waybillEntity.setActive("Y");
	if (StringUtil.isNotEmpty(createUserCode) ) {
		// 制单时间
		waybillDto.setBillStartTime(ui.getZdStartDate().getDate());
		waybillDto.setBillEndTime(ui.getZdEndDate().getDate());
	}else{
		//当开单人不输入的时候，直接返回
		return null;
	}
	waybillDto.setWaybillEntity(waybillEntity);
	//如果待补录没有查询到数据就查询运单表
	List<WaybillEntity> waybilllist = wayBillService.queryWaybillForPrint(waybillDto);
	if (CollectionUtils.isNotEmpty(list)) {
		list.addAll(waybilllist);
	} else {
		list=waybilllist;
	}
	return list;
	
	}
	
	
}
   