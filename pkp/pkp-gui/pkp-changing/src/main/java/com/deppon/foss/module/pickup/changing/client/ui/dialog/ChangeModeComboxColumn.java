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
 * PROJECT NAME	: pkp-changing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/ui/dialog/ChangeModeComboxColumn.java
 * 
 * FILE NAME        	: ChangeModeComboxColumn.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 */
package com.deppon.foss.module.pickup.changing.client.ui.dialog;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.changing.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.common.client.service.BaseDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodChangeHistoryDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author 026123-foss-lifengteng
 * 
 */
public class ChangeModeComboxColumn extends AbstractCellEditor implements
		TableCellEditor, TableCellRenderer {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	// log
	private static Log LOG = LogFactory.getLog(ChangeModeComboxColumn.class);

	private  BaseDataService vBaseDataService ;
	
	private static final int THREE = 3;
	
	/**
	 * ui
	 */
	private ChangeGoodsQtyDialog ui;

	/**
	 * 删除 check box 列表
	 */
	private List<JComboBox> renderButtonList = new ArrayList<JComboBox>();

	/**
	 * LinkTableMode
	 */
	private ChangeGoodQtyMode dtm;

	/**
	 * 导入的时候的WaybillInfo对象 
	 */
	private WaybillInfoVo originalWaybillInfoVo;

	/**
	 * 行
	 */
	private int row;
	
	/**
	 * 更改单Service
	 */
	
	/**
	 * 走货路劲上所有的org的List
	 */
	private List<DataDictionaryValueVo> lineOrgList =new ArrayList<DataDictionaryValueVo> ();
		
	/**
	 * 构造方法
	 * 
	 * @param tc
	 * @param ui
	 */
	public ChangeModeComboxColumn(TableColumn tc, ChangeGoodsQtyDialog ui,
			 WaybillInfoVo originalWaybillInfoVo) {
		vBaseDataService  = BaseDataServiceFactory.getBaseDataService();
		tc.setCellEditor(this);
		tc.setCellRenderer(this);
		this.ui = ui;
		this.originalWaybillInfoVo = originalWaybillInfoVo;
		if (LOG.isDebugEnabled()) {
			LOG.debug(this.ui);
		}
		
		//初始化数据
		initData();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		String lineOrgCode = "";
		String lineName = "";
		if(originalWaybillInfoVo.getOrgInfoDto()!=null){
			lineOrgCode = originalWaybillInfoVo.getOrgInfoDto().getRouteLineCode();
			lineName =  originalWaybillInfoVo.getOrgInfoDto().getRouteLineName();
		}
		 
		if(StringUtils.isEmpty(lineOrgCode)){
			
			
			
			lineOrgCode = "";
			lineName = "";
			
			
			
		}
		
		String[] orgArray =  StringUtils.split(lineOrgCode, '-');
		String[] orgName = StringUtils.split(lineName,'-');
		String[] emptyArray= new String[]{""};
		if(orgArray==null || orgArray.length==0){
			//整车
			if(originalWaybillInfoVo.getIsWholeVehicle()!= null && originalWaybillInfoVo.getIsWholeVehicle()){
				orgArray = emptyArray;
			}else{
			
				List<String> orgCodeList = new ArrayList<String>();
				List<String> orgNameList = new ArrayList<String>();
				
				if(StringUtils.isNotEmpty(originalWaybillInfoVo.getReceiveOrgCode())){
					orgCodeList.add(originalWaybillInfoVo.getReceiveOrgCode());
					orgNameList.add(originalWaybillInfoVo.getReceiveOrgName());
				}
				
				if(StringUtils.isNotEmpty(originalWaybillInfoVo.getLoadOrgCode())){
					orgCodeList.add(originalWaybillInfoVo.getLoadOrgCode());
					orgNameList.add(originalWaybillInfoVo.getLoadOrgName());
				}
				
				if(StringUtils.isNotEmpty(originalWaybillInfoVo.getLastLoadOrgCode())){
					orgCodeList.add(originalWaybillInfoVo.getLastLoadOrgCode());
					orgNameList.add(originalWaybillInfoVo.getLastLoadOrgName());
				}
				
				orgArray = orgCodeList.toArray(emptyArray);
				orgName =  orgNameList.toArray(emptyArray);
			}
			
		}
		
		if(orgName==null || orgName.length==0){
			orgName = new String[]{""};
		}
		
		//查询所有org code对应的name 
		for(int i =0 ;i <orgArray.length; i++){
			String deptId= orgArray[i];
		
			String deptName = "";
			//万一没有名字抛exception 不处理
			try{
				deptName=orgName[i];
				SaleDepartmentEntity s = vBaseDataService.querySaleDepartmentByCode(orgArray[i]);
				if(s!=null && s.checkStation()){
					continue;
				}
			}catch(Exception e){
				LOG.debug(e);
			}
			

		
			
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			
			vo.setValueName(deptName);
			vo.setValueCode(deptId);
			lineOrgList.add(vo);
		}
		
		
		
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @param row
	 *            the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * 输入panel
	 */
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		// 当单元格处于编辑状态时
		if (dtm == null) {
			dtm = (ChangeGoodQtyMode) table.getModel();
		}
		this.row = row;
		if (renderButtonList.size() < row + 1) {
			
			LabeledGoodChangeHistoryDto dto = (LabeledGoodChangeHistoryDto)dtm.getValueAt(row, THREE);
			
			JComboBox renderButton = new JComboBox();
			
			DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
			for (DataDictionaryValueVo vo : lineOrgList) {
				comboModel.addElement(vo);
			}
			renderButton.setModel(comboModel);
			SelectedListener l = new SelectedListener();
			renderButton.addItemListener(l);

			renderButtonList.add(renderButton);
			
			if(!FossConstants.YES.equals(dto.getIsFromService())){
			
				return renderButton;
			}else{
				JLabel j = new JLabel();
				return j;
			}
			
		} else {
			JComboBox renderButton = renderButtonList.get(row);
			LabeledGoodChangeHistoryDto dto = (LabeledGoodChangeHistoryDto)dtm.getValueAt(row, THREE);
			
			if(!FossConstants.YES.equals(dto.getIsFromService())){
				return renderButton;
			}else{
				JLabel j = new JLabel();
				return j;
			}
		}
	}

	/**
	 * 结果列表
	 */
	@Override
	public Object getCellEditorValue() {
		// 当单元格的编辑状态结束后调用此方法内的代码
		return null;
	}

	/**
	 * is selected
	 */
	@Override
	public boolean shouldSelectCell(EventObject arg0) {
		LOG.debug(arg0);
		return super.shouldSelectCell(arg0);
	}

	/**
	 * 结果列表 Component
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (dtm == null) {
			dtm = (ChangeGoodQtyMode) table.getModel();
		}

		// 当单元格处于展示状态时
		if (renderButtonList.size() < row + 1) {
			
			LabeledGoodChangeHistoryDto dto =
				(LabeledGoodChangeHistoryDto)dtm.getValueAt(row, THREE);
			
			JComboBox renderButton = new JComboBox();

			DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
			for (DataDictionaryValueVo vo : lineOrgList) {
				comboModel.addElement(vo);
			}
			renderButton.setModel(comboModel);

			
			SelectedListener l = new SelectedListener();
			renderButton.addItemListener(l);

			renderButtonList.add(renderButton);
			
			if(!FossConstants.YES.equals(dto.getIsFromService())){
				
				if(dto.getReceiveOrgCode()!=null){
					
					for (DataDictionaryValueVo vo : lineOrgList) {
						if(	dto.getReceiveOrgCode().equals(vo.getValueCode()) ){
							renderButton.setSelectedItem(vo);
						}
					}
				
				}
				
				
				
				return renderButton;
			}else{
				JLabel j = new JLabel();
				return j;
			}
			
		} else {
			JComboBox renderButton = renderButtonList.get(row);

			LabeledGoodChangeHistoryDto dto = 
				(LabeledGoodChangeHistoryDto)dtm.getValueAt(row, THREE);
			
			if(!FossConstants.YES.equals(dto.getIsFromService())){
				
				if(dto.getReceiveOrgCode()!=null){
					
					for (DataDictionaryValueVo vo : lineOrgList) {
						if(	dto.getReceiveOrgCode().equals(vo.getValueCode()) ){
							renderButton.setSelectedItem(vo);
						}
					}
				
				}
				
				return renderButton;
			}else{
				JLabel j = new JLabel();
				return j;
			}
		}
	}

	/**
	 * 选
	 * 
	 * @author shixw
	 * 
	 */
	class SelectedListener implements  ItemListener {

		/**
		 * 补录按钮事件处理
		 */
		public void itemStateChanged(ItemEvent e) {
			JComboBox renderButton = renderButtonList.get(row);
			Object obj = e.getSource();

			if (obj != null && obj.equals(renderButton)) {
				// 从0开始 最后一个是对象本身
				LabeledGoodChangeHistoryDto dto = (LabeledGoodChangeHistoryDto) dtm
						.getValueAt(row, THREE);

				// 删除checkbox框被选中
				DataDictionaryValueVo selectItem =(DataDictionaryValueVo) renderButton.getSelectedItem();
				
				dto.setReceiveOrgCode(selectItem.getValueCode());
				dto.setReceiveOrgName(selectItem.getValueName());
				
				
				
			}
		}
	}

	/**
	 * @return the renderButtonList
	 */
	public List<JComboBox> getRenderButtonList() {
		return renderButtonList;
	}

}