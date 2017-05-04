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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.spi.IIORegistry;
import javax.swing.AbstractCellEditor;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.WindowUtil;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.PictureViewComp;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.creating.client.ui.popupdialog.WaybillPictureReturnRecordViewDialog;
import com.luciad.imageio.webp.WebPImageReaderSpi;
import com.luciad.imageio.webp.WebPImageWriterSpi;

/**
 * 图片查询退回记录查看图片查看列
 * 
 * @author hehaisu
 * 
 */
public class PictureViewReturnButtonColumn extends AbstractCellEditor
		implements TableCellEditor, TableCellRenderer, ActionListener {
	
	private static final Log LOG = LogFactory.getLog(PictureViewReturnButtonColumn.class);
	
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	private static final int NUM_835 = 835;

	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager
			.getI18n(PictureViewReturnButtonColumn.class);

	private JButton lookRenderButton;
	private JButton lookEditorButton;
	private SearchPictureReturnTableModel spt;
	WaybillPictureReturnRecordViewDialog ui;
	
	static{
		
		try {
			
			IIORegistry r = javax.imageio.spi.IIORegistry.getDefaultInstance();
			WebPImageReaderSpi s = new WebPImageReaderSpi();
			WebPImageWriterSpi s2 = new WebPImageWriterSpi();
			r.registerServiceProvider(s);
			r.registerServiceProvider(s2);
			//inilibWebp();
		}
		catch (NoClassDefFoundError e) {
			if(LOG.isErrorEnabled()){
				LOG.error(e);
			}
		}
		
	}

	/**
	 * 构造方法
	 * 
	 * @param tc
	 * @param ui2
	 */
	public PictureViewReturnButtonColumn(TableColumn tc,
			WaybillPictureReturnRecordViewDialog ui, SearchPictureReturnTableModel tableModel) {
		this.spt = tableModel;
		lookRenderButton = new JButton();
		lookEditorButton = new JButton();
		lookEditorButton.setFocusable(false);
		Dimension preferredSize = new Dimension(NumberConstants.NUMBER_30,NumberConstants.NUMBER_18);
		lookEditorButton.setPreferredSize(preferredSize);
		lookRenderButton.setPreferredSize(preferredSize);
		
		lookEditorButton.addActionListener(this);	
		
		tc.setCellEditor(this);
		tc.setCellRenderer(this);
		this.ui = ui;
	}

	/**
	 * 当单元格处于编辑状态时
	 */
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		// 当单元格处于编辑状态时
		lookEditorButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.look"));
		spt = (SearchPictureReturnTableModel) table.getModel();
		return lookEditorButton;
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
		
		lookRenderButton.setText(i18n.get("foss.gui.creating.PictureWaybillSearchButtonColumn.button.look"));
		
		return lookRenderButton;
	}
	
	/**
	 * 获得下拉框中全部值的编码
	 */
	private List<String> getComboxCodes(JComboBox comboBox) {
		ComboBoxModel comb = comboBox.getModel();
		// 定义接收集合
		List<String> list = new ArrayList<String>();
		// 下拉框的值
		int count = comb.getSize();
		// 遍历下拉框
		for (int i = 0; i < count; i++) {
			// 获得下拉选项的code
			String code = ((DataDictionaryValueVo) comb.getElementAt(i)).getValueCode();
			// 过滤掉ALL
			if (!"all".equals(StringUtil.defaultIfNull(code))) {
				list.add(code);
			}
		}
		return list;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 将UI上的行转成model中的行
		int index = ui.getTable().getSelectedRow();
		if (index < 0) {
			return;
		}

		int row = ui.getTable().convertRowIndexToModel(index);
		
		String waybillId = (String)spt.getValueAt(row,2);
		
		if (StringUtils.isNotBlank(waybillId)) {
			//只能查看到2个月以内的图片运单
			JDialog dialog = new JDialog();
			dialog.setTitle("图片查看");
			dialog.setSize(NUM_835, NumberConstants.NUMBER_600);
			PictureViewComp pictureViewComp = new PictureViewComp(NumberConstants.NUMBER_800,NumberConstants.NUMBER_500);
			try {
				pictureViewComp.loadImageByWaybillId(waybillId);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			dialog.add(pictureViewComp);
			dialog.setModal(true);
			WindowUtil.centerAndShow(dialog);
		}
	}
	
}
