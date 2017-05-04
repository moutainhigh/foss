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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/OfflineButtonColumn.java
 * 
 * FILE NAME        	: OfflineButtonColumn.java
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
package com.deppon.foss.module.pickup.creating.client.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.ImageUtil;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.common.client.utils.MessageI18nUtil;
import com.deppon.foss.module.pickup.creating.client.action.ImportWaybillEditUIAction;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.module.pickup.waybill.shared.hessian.ILabeledGoodHessianRemoting;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillPendingHessianRemoting;

public class BatchWaybillButtonColumn extends AbstractCellEditor
		implements TableCellEditor, TableCellRenderer, ActionListener {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager
			.getI18n(BatchWaybillButtonColumn.class);

	/**
	 * 图片开单远程接口
	 */
	IWaybillHessianRemoting waybillHessianRemoting; 
	
	IWaybillPendingHessianRemoting waybillPendingHessianRemoting;
	
	ILabeledGoodHessianRemoting labeledGoodHessianRemoting;
	private JButton modifyEditorButton;
	private JButton deleteEditorButton;
	private JButton modifyRenderButton;
	private JButton deleteRenderButton;
	private JPanel panel ;
	private BatchCreateWaybillTableModel spt;
	private List<Object> list;
	//private boolean IS_PKP_400_CS_REP  = false;
	/**
	 * ui
	 */
	OpenBatchCreateWaybillUI ui;

	/**
	 * 构造方法
	 * 
	 * @param tc
	 * @param ui2
	 */
	public BatchWaybillButtonColumn(TableColumn tc,
			OpenBatchCreateWaybillUI ui, BatchCreateWaybillTableModel tableModel,List<Object> list) {
		this.spt = tableModel;
		modifyRenderButton = new JButton(ImageUtil.getImageIcon(
				this.getClass(), "supplywaybill.png"));
		modifyEditorButton = new JButton(ImageUtil.getImageIcon(
				this.getClass(), "supplywaybill.png"));
		modifyEditorButton.setFocusable(false);
		modifyRenderButton.setPreferredSize(new Dimension(NumberConstants.NUMBER_60,NumberConstants.NUMBER_18));
		modifyEditorButton.setPreferredSize(new Dimension(NumberConstants.NUMBER_60,NumberConstants.NUMBER_18));
		
		deleteRenderButton = new JButton(ImageUtil.getImageIcon(
				this.getClass(), "delete.png"));
		deleteEditorButton = new JButton(ImageUtil.getImageIcon(
				this.getClass(), "delete.png"));
		deleteEditorButton.setFocusable(false);
		deleteRenderButton.setPreferredSize(new Dimension(NumberConstants.NUMBER_60,NumberConstants.NUMBER_18));
		deleteEditorButton.setPreferredSize(new Dimension(NumberConstants.NUMBER_60,NumberConstants.NUMBER_18));
	
		this.list=list;
		initListener(ui);
		
		waybillHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillHessianRemoting.class);
		waybillPendingHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillPendingHessianRemoting.class);
		
		labeledGoodHessianRemoting= DefaultRemoteServiceFactory.getService(ILabeledGoodHessianRemoting.class);
		
		tc.setCellEditor(this);
		tc.setCellRenderer(this);
		this.ui = ui;
		/*UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		if(!CollectionUtils.isEmpty(user.getRoleids())){
			for(String roleId:user.getRoleids()){
				if(WaybillConstants.PKP_400_CS_REP.equals(roleId)){
					//则是400客服角色
					IS_PKP_400_CS_REP = true;
					break;
				}
			}
		}*/
	}
	private void initListener(final OpenBatchCreateWaybillUI ui){
		final JXTable table = ui.getTable();
		modifyEditorButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// 将UI上的行转成model中的行
				int index = table.getSelectedRow();
				if (index < 0) {
					return;
				}
				try {
					int row = table.convertRowIndexToModel(index);
					String waybillNo = (String)spt.getValueAt(row, NumberConstants.NUMBER_4);
					String waybillType = (String)spt.getValueAt(row, NumberConstants.NUMBER_25);
					if(WaybillConstants.WAYBILL_STATUS_PC_PENDING.equals(waybillType)
						 ||
					   WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION.equals(waybillType)){
						//获得修改运单基本信息对象 
						WaybillPendingEntity waybillPending = waybillPendingHessianRemoting.queryWaybillByWaybillNo(waybillNo);
						ImportWaybillEditUIAction uiAction = new ImportWaybillEditUIAction();
						uiAction.importWaybillEditUI(waybillPending);
						if(null!=waybillPending && waybillPending.getExceptionNote()!=null){
							MsgBox.showInfo(waybillPending.getExceptionNote());
						}
					}else if(WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(waybillType)){
						//获得修改运单基本信息对象 
						WaybillDto waybillDto = waybillHessianRemoting.queryWaybillByNo(waybillNo);
						ImportWaybillEditUIAction uiAction = new ImportWaybillEditUIAction();
						uiAction.importWaybillEditUI(waybillDto);
					}
					
				} catch (BusinessException ex) {
					//MsgBox.showError(i18n.get("foss.gui.creating.pendingButtonColumn.exception.label")+"："+ex.getMessage());
					if (StringUtils.isNotEmpty(ex.getMessage())) {
						MsgBox.showInfo(i18n.get("foss.gui.creating.pendingButtonColumn.exception.label")+MessageI18nUtil.getMessage(ex, i18n));
					}else if(StringUtils.isNotEmpty(ex.getErrorCode())){
					    MsgBox.showInfo(i18n.get("foss.gui.creating.pendingButtonColumn.exception.label")+ex.getErrorCode());
					}
				}
			}
			
		});
		
		deleteEditorButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// 将UI上的行转成model中的行
				int index = table.getSelectedRow();
				if (index < 0) {
					return;
				}
				int row = table.convertRowIndexToModel(index);
				List<Object> newList = new ArrayList<Object>();
				for (Object obj : list) {
					if (obj instanceof WaybillPendingDto) {
						newList.add(((WaybillPendingDto) obj)
								.getWaybillPending());
					} else {
						newList = list;
						break;
					}
				}
				// 刷新表格
				newList.remove(index);
				Object[][] datas = ui.getArray(newList);
				BatchCreateWaybillTableModel tableModel = new BatchCreateWaybillTableModel(datas);
				table.setModel(tableModel);
				BatchCheckBoxColumn checkColumn = new BatchCheckBoxColumn(table.getColumn(i18n.get("foss.gui.creating.BatchCreateWaybillQueryAction.BatchCheckBoxColumn.choice")), ui);
				List<JCheckBox> lit = checkColumn.getRenderButtonList();
				ui.setList(lit);
				ui.setCheckBoxColumn(checkColumn);
				new BatchWaybillButtonColumn(table.getColumn(i18n.get("foss.gui.creating.BatchWaybillButtonColumn.column")), ui, tableModel,newList);
				ui.refreshTable(table);
				String waybillNo = (String)spt.getValueAt(row, NumberConstants.NUMBER_4);
				if(null!=waybillNo){
					List<String> waybillNos = new ArrayList<String>();
					waybillNos.add(waybillNo);
					waybillPendingHessianRemoting.deleteByWaybillNo(waybillNo);
					labeledGoodHessianRemoting.deleteLabByWaybillNo(waybillNo);
					waybillHessianRemoting.deleteWoodByWaybillNo(waybillNo);
				}
				
			}
			
		});
		
	}
	/**
	 * 当单元格处于编辑状态时
	 */
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		// 当单元格处于编辑状态时
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0,0 ));
		modifyEditorButton.setEnabled(true);
		deleteEditorButton.setEnabled(true);
		String active = (String)spt.getValueAt(row,NumberConstants.NUMBER_25);
		//已录入 或者 生成运单异常
		if (WaybillConstants.WAYBILL_STATUS_PC_PENDING.equals(active) 
				|| WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION.equals(active)) {
			modifyEditorButton.setText("修改");
			deleteEditorButton.setText("删除");			
			panel.add(modifyEditorButton);
			panel.add(deleteEditorButton);
			ui.getSubmitButton().setEnabled(true);
			ui.getCleanUpButton().setEnabled(true);
		} 
		//已开单
		else if (WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(active)) {
			modifyEditorButton.setText("查看");
			deleteEditorButton.setText("删除");
			modifyEditorButton.setEnabled(true);
			deleteEditorButton.setEnabled(false);
			panel.add(modifyEditorButton);
			panel.add(deleteEditorButton);
			ui.getSubmitButton().setEnabled(false);
			ui.getCleanUpButton().setEnabled(false);
		}
		
		spt = (BatchCreateWaybillTableModel) table.getModel();
		return panel;
	}

	/**
	 * 当单元格的编辑状态结束后调用此方法内的代码
	 */
	@Override
	public Object getCellEditorValue() {
		// 当单元格的编辑状态结束后调用此方法内的代码
		return null;
	}

	/**
	 * getTableCellRendererComponent
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// 当单元格处于展示状态时
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0,0 ));
		modifyRenderButton.setEnabled(true);
		deleteRenderButton.setEnabled(true);
		
		
		String active = (String)spt.getValueAt(row, NumberConstants.NUMBER_25);
		
		
		//已录入 或者生成运单异常
		if (WaybillConstants.WAYBILL_STATUS_PC_PENDING.equals(active) 
				|| WaybillConstants.WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION.equals(active) ) {
			modifyRenderButton.setText("修改");
			deleteRenderButton.setText("删除");						
			panel.add(modifyRenderButton);
			panel.add(deleteRenderButton);
			ui.getSubmitButton().setEnabled(true);
			ui.getCleanUpButton().setEnabled(true);
		} 
		//已开单
		else if (WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(active)) {
			modifyRenderButton.setText("查看");
			deleteRenderButton.setText("删除");
			modifyRenderButton.setEnabled(true);
			deleteRenderButton.setEnabled(false);
			panel.add(modifyRenderButton);
			panel.add(deleteRenderButton);
			ui.getSubmitButton().setEnabled(false);
			ui.getCleanUpButton().setEnabled(false);
		}
		return panel;
	}		
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
