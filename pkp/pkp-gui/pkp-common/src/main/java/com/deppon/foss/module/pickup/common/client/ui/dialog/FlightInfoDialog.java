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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/dialog/FlightInfoDialog.java
 * 
 * FILE NAME        	: FlightInfoDialog.java
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
package com.deppon.foss.module.pickup.common.client.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FlightDto;
import com.deppon.foss.module.pickup.common.client.utils.DateUtils;
import com.deppon.foss.util.define.FossConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;


/**
 * 
 * 航班信息话框界面
 * @author 025000-FOSS-helong
 * @date 2012-12-31 上午09:43:15
 */
public class FlightInfoDialog extends JDialog {

	/**
	 * 国际化
	 */
	private II18n i18n = I18nManager.getI18n(FlightInfoDialog.class);
	
	private static final String OK = "OK";

	private static final int THREE = 3;

	private static final int FOUR = 4;

	private static final int FIVE = 5;

	private static final int SIX = 6;

	private static final int SEVEN = 7;

	private static final int EIGHT = 8;

//	private static final int NINE = 9;
	
	private static final int HUNDRED = 100;

	private static final long serialVersionUID = -5960720224792355823L;

	private static final int NUM_793 = 793;

	private static final int NUM_404 = 404;

	private final JPanel contentPanel = new JPanel();
	
	private JXTable table;
	
	private FlightDto flightDto;

	/**
	 * Create the dialog.
	 */
	public FlightInfoDialog(List<FlightDto> flightDto) {
		
		initTable();
		
		init();
		
		//初始化表格数据
		setData(flightDto);
		
	}
	
	private void init()
	{
		setBounds(HUNDRED, HUNDRED, NUM_793, NUM_404);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(FIVE, FIVE, FIVE, FIVE));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		{
			JScrollPane scrollPane = new JScrollPane(table);
			contentPanel.add(scrollPane, "2, 2, 19, 3, fill, fill");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton(OK);
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						setFlightInfo();
					}
				});
				okButton.setActionCommand(OK);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		// 设置模态
		setModal(true);
	}
	
	
	/**
	 * 
	 * 初始化表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午10:47:03
	 */
	private void initTable() {
		table = new JXTable();
		table.setModel(new FlightModel());
		table.setAutoscrolls(true);
		table.setSortable(false);
		table.setEditable(false);
		table.setColumnControlVisible(true);

		// 居中显示
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, render);
		((DefaultTableCellRenderer) table.getTableHeader()
				.getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);
		table.addMouseListener(new ClickTableHandler());
	}
	
	/**
	 * 
	 * 将数据添加到表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:29:46
	 */
	public void setData(List<FlightDto> flightDto) {
		FlightModel model = ((FlightModel) table.getModel());
		model.setData(flightDto);
		// 刷新表格数据
		model.fireTableDataChanged();
	}
	
	/**
	 * 一般内部类：表格的单、双击处理事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-23 上午10:16:42
	 */
	private class ClickTableHandler extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			// 双击
			if (e.getClickCount() == 2) {
				setFlightInfo();
				// 关闭界面，释放资源
				dispose();
			}
		}
	}
	
	
	/**
	 * 
	 * 设置银行信息
	 * @author 025000-FOSS-helong
	 * @date 2012-12-6 下午07:40:27
	 */
	private void setFlightInfo()
	{
		int row = table.getSelectedRow();
		FlightModel model = ((FlightModel) table.getModel());
		List<FlightDto> list = model.getData();
		if(list != null)
		{
			if(!list.isEmpty())
			{
				flightDto = list.get(row);
				// 关闭界面，释放资源
				dispose();
			}
		}
	}

	/**
	 * 
	 * 封装表格的数据模型，设置成以对象进行操作
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:30:03
	 */
	public class FlightModel extends DefaultTableModel {

		private static final long serialVersionUID = 5883365603131625962L;

		/**
		 * 航班信息
		 */
		private List<FlightDto> data;
		
		public List<FlightDto> getData() {
			return data;
		}

		public void setData(List<FlightDto> data) {
			this.data = data;
		}

		
		/**
		 * 
		 * 重写获取表格列名的方法
		 * @author 025000-FOSS-helong
		 * @date 2012-12-31 上午09:24:06
		 * @param column
		 * @return
		 */
		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return i18n.get("foss.gui.common.flightInfoDialog.columnName.airlineName");
			case 1:
				return i18n.get("foss.gui.common.flightInfoDialog.columnName.deliveryOrgName");
			case 2:
				return i18n.get("foss.gui.common.flightInfoDialog.columnName.targetOrgName");
			case THREE:
				return i18n.get("foss.gui.common.flightInfoDialog.columnName.flightNo");
			case FOUR:
				return i18n.get("foss.gui.common.flightInfoDialog.columnName.preDepartureTime");
			case FIVE:
				return i18n.get("foss.gui.common.flightInfoDialog.columnName.preArriveTime");
			case SIX:
				return i18n.get("foss.gui.common.flightInfoDialog.columnName.aircraftType");
			case SEVEN:
				return i18n.get("foss.gui.common.flightInfoDialog.columnName.journeyTime");
			case EIGHT:
				return i18n.get("foss.gui.common.flightInfoDialog.columnName.flightDate");
			default:
				return "";
			}
		}

		/**
		 * 
		 * 重写获取表格列总数的方法
		 * @author 025000-FOSS-helong
		 * @date 2012-12-31 上午09:24:31
		 * @return
		 */
		@Override
		public int getColumnCount() {
			return NumberConstants.NUMBER_9;
		}

		/**
		 * 
		 * 重写获取行总数的方法
		 * @author 025000-FOSS-helong
		 * @date 2012-12-31 上午09:24:56
		 * @return
		 */
		@Override
		public int getRowCount() {
			return data == null ? 0 : data.size();
		}

		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
			case 0:
				return data.get(row).getAirlinesName();
			case 1:
				return data.get(row).getOriginatingStationName();
			case 2:
				return data.get(row).getDestinationStationName();
			case THREE:
				return data.get(row).getFlightNo();
			case FOUR:
				if(data.get(row).getPlanLeaveTime()!=null){
					return DateUtils.getHourMinute(data.get(row).getPlanLeaveTime());
				}else{
					return null;
				}
			case FIVE:
				if(data.get(row).getPlanArriveTime()!=null){
					return DateUtils.getHourMinute(data.get(row).getPlanArriveTime());
				}else{
					return null;
				}
			default:
				return getValueAtExp(row, column);
			}
		}
		
		private Object getValueAtExp(int row, int column){
			switch (column) {
			case SIX:
				return data.get(row).getAircraftType();
			case SEVEN:
				return data.get(row).getFlyTime().toString();
			case EIGHT:
				return getFlyDate(data.get(row));
			default:
				return super.getValueAt(row, column);
			}
			
		}
		
		
		/**
		 * 
		 * 获取飞行日期
		 * @author 025000-FOSS-helong
		 * @date 2012-12-31 上午09:27:36
		 * @return
		 */
		private String getFlyDate(FlightDto rowDto)
		{
			String flyDate = "";
			if(FossConstants.YES.equals(rowDto.getFlyOnMonday()))
			{
				flyDate = flyDate + "1";
			}
			
			if(FossConstants.YES.equals(rowDto.getFlyOnTuesday()))
			{
				flyDate = flyDate + "2";
			}
			
			if(FossConstants.YES.equals(rowDto.getFlyOnWednesday()))
			{
				flyDate = flyDate + "3";
			}
			
			if(FossConstants.YES.equals(rowDto.getFlyOnThursday()))
			{
				flyDate = flyDate + "4";
			}
			
			if(FossConstants.YES.equals(rowDto.getFlyOnFriday()))
			{
				flyDate = flyDate + "5";
			}
			
			if(FossConstants.YES.equals(rowDto.getFlyOnSaturday()))
			{
				flyDate = flyDate + "6";
			}
			
			if(FossConstants.YES.equals(rowDto.getFlyOnSunday()))
			{
				flyDate = flyDate + "7";
			}
			return flyDate;
		}
		
	}


	public FlightDto getFlightDto() {
		return flightDto;
	}

	public void setFlightDto(FlightDto flightDto) {
		this.flightDto = flightDto;
	}
	
}