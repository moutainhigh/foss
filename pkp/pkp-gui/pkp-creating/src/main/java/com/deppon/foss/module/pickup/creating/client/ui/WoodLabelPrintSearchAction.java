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
package com.deppon.foss.module.pickup.creating.client.ui;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.SwiftNumberInfoDto;
import com.google.inject.Inject;

/**
 * 查询表
 * @author Foss-280747-Zhuxue
 *
 */
@SuppressWarnings("rawtypes")
public class WoodLabelPrintSearchAction extends AbstractButtonActionListener<PrintSignUI> {

	// 日志
	public static final Logger LOGGER = LoggerFactory.getLogger(WoodLabelPrintSearchAction.class);

	// 国际化
	private static final II18n i18n = I18nManager.getI18n(WoodLabelPrintSearchAction.class);

	@Inject
	private IWaybillService wayBillService;

	private PrintSignUI ui;

	//存储查询数据
	private BarcodePrintLabelDto printLabelBean = new BarcodePrintLabelDto();
	
	// 全选
	private JCheckBox allSelectCheckBox;

	
	public JCheckBox getAllSelectCheckBox() {
		return allSelectCheckBox;
	}



	public void setAllSelectCheckBox(JCheckBox allSelectCheckBox) {
		this.allSelectCheckBox = allSelectCheckBox;
	}



	@Override
	public void setIInjectUI(PrintSignUI ui) {
		this.ui = ui;
	}



	@SuppressWarnings({ "static-access"})
	public void actionPerformed(ActionEvent e) {
		try {
			wayBillService = WaybillServiceFactory.getWaybillService();

			/**
			 * 封装查询数据
			 */
			String woodWaybillNo =String.valueOf(ui.getWoodWaybillNo().getText());
			if (StringUtils.isEmpty(woodWaybillNo)) {
				JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			// 运单信息
			WaybillEntity waybillEntity = wayBillService.queryNewWaybillByNumber(woodWaybillNo);
			if(waybillEntity == null){
				JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.waybillNo"), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
				return;
		   }
			//总重量
			printLabelBean.setWeight(String.valueOf(waybillEntity.getGoodsWeightTotal()));
			//总件数
			printLabelBean.setTotalPieces(String.valueOf(waybillEntity.getGoodsQtyTotal()));
			//运单号
			printLabelBean.setWaybillNumber(waybillEntity.getWaybillNo());
			//打木实体信息
			WoodenRequirementsEntity woodenRequirementsEntity = new WoodenRequirementsEntity();
			//查询打木架需求表获取打木相关数据
			woodenRequirementsEntity = wayBillService.queryWoodenRequirement(woodWaybillNo); 
			//如果查询打木相关数据不为空就进行打印数据封装
			if(woodenRequirementsEntity ==null){
				JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.woodWaybillNo"), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
				return;
			}
			// 打木架货物件数
			printLabelBean.setStandGoodsNum(woodenRequirementsEntity.getStandGoodsNum());
			// 打木箱货物件数
			printLabelBean.setBoxGoodsNum(woodenRequirementsEntity.getBoxGoodsNum());
			// 打木托件数
			printLabelBean.setSalverGoodsNum(woodenRequirementsEntity.getSalverGoodsNum());
			//填充打木架，木箱，木托，数据
			ui.getTstandGoodsNum().setText(String.valueOf(printLabelBean.getStandGoodsNum()==null ? " " : printLabelBean.getStandGoodsNum()));
			ui.getTboxGoodsNum().setText(String.valueOf(printLabelBean.getBoxGoodsNum()==null ? "" : printLabelBean.getBoxGoodsNum()));
			StringBuilder sb = new StringBuilder("");
			//填充包装类型
			if(printLabelBean.getStandGoodsNum()>=1){
				sb.append(i18n.get("foss.gui.creating.woodYokeEnterAction.standGoods")).append(",");
			}
			if(printLabelBean.getBoxGoodsNum()>=1){
				sb.append(i18n.get("foss.gui.creating.woodYokeEnterAction.boxGoods")).append(",");
			}
			if(printLabelBean.getSalverGoodsNum()>=1){
				sb.append(i18n.get("foss.gui.creating.woodYokeEnterAction.salverGoods")).append(",");
			}
			ui.gettPackageType().setText(sb.toString().substring(0, sb.toString().length()-1));
			// 填充流水号
			//选择要填充的文本框
			final JXTable table = ui.getTable();
			//设置表头信息
			LinkWoodTableModePrint tableModel;
			//查询货签信息表
		    List<SwiftNumberInfoDto> list = wayBillService.queryUnpackDetailss(woodWaybillNo);
		    if(list==null){
		    	JOptionPane.showMessageDialog(null, i18n.get("foss.gui.creating.printSignUI.msgbox.error.one"), i18n.get("foss.gui.creating.printSignUI.msgbox.error"), JOptionPane.WARNING_MESSAGE);
		    	return;
		    }
			Collections.sort(list, new Comparator<SwiftNumberInfoDto>() {
				@Override
				public int compare(SwiftNumberInfoDto q1,SwiftNumberInfoDto q2) {
					int k =Integer.parseInt(q1.getSerialNo()) - Integer.parseInt(q2.getSerialNo());
					return k == 0 ? q2.getPackageType().compareTo(q1.getPackageType()) : k;
				}
			});
			//存贮不重复的流水号
			List<String> list2=new ArrayList<String>();
			for (SwiftNumberInfoDto entity : list) {
				if (!list2.contains(entity.getSerialNo())) {
					list2.add(entity.getSerialNo());
				}
			}
			Map<String ,String> totalPiecesMap = new LinkedHashMap<String ,String>();
			// 如果查询的流水号不为空就添加到totalPiecesList
			if (CollectionUtils.isNotEmpty(list)) {
				for (SwiftNumberInfoDto q : list) {
					if (StringUtils.isNotBlank(q.getSerialNo())) {
						totalPiecesMap.put(q.getSerialNo(), q.getPackageType());
					}
				}
			}
			
			Iterator<String> ite = totalPiecesMap.values().iterator();
			
			Map<String, Integer> countMap =new HashMap<String, Integer>();
			
			while(ite.hasNext()){
				String item = ite.next();
				
				if(countMap.containsKey(item)){
					countMap.put(item, Integer.valueOf(countMap.get(item).intValue() + 1));
				}else{
					countMap.put(item, new Integer(1));
				}
			}
			
			//print the count
			for (Iterator iter = countMap.keySet().iterator(); iter.hasNext();) {
				if(countMap.get("MAKE_WOODEN_STOCK")!=null){
					int l=countMap.get("MAKE_WOODEN_STOCK");
					// 打木托件数
					printLabelBean.setSalverGoodsNum(l);
				}else{
					// 打木托件数
					printLabelBean.setSalverGoodsNum(0);
				}
				break;
			}
			//打木托的个数
			ui.getTsalverGoodsNum().setText(String.valueOf(printLabelBean.getSalverGoodsNum()==null ? "" : printLabelBean.getSalverGoodsNum()));
			//将查询出的数据转换为数组
            Object[][] datas = ui.getArray(list2, 1);
			// 刷新表格
			tableModel = new LinkWoodTableModePrint(datas);
			//填充到文本框
			table.setModel(tableModel);
			//添加复选框
			PrintWoodSignCheckBoxColumn checkColumn = new PrintWoodSignCheckBoxColumn(table.getColumn(i18n.get("foss.gui.creating.printSignUI.msgbox.error.woodWaybillNoChoice")), ui);
			List<JCheckBox> list1 = checkColumn.getRenderButtonList();
			ui.setList(list1);
			ui.setCheckBoxColumn(checkColumn);
			//刷新显示数据
			ui.refreshTable(table);
		} catch (BusinessException ee) {
			LOGGER.error("sale deptsearch BusinessException", ee);
			MsgBox.showError(ee.getMessage());
		}
	}
}
   