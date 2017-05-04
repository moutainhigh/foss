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
 * PROJECT NAME	: pkp-changingexp
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/ui/dialog/ChangeGoodsQtyDialog.java
 * 
 * FILE NAME        	: ChangeGoodsQtyDialog.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-changingexp
 * PACKAGE NAME: com.deppon.foss.module.pickup.changingexp.client.ui.dialog
 * FILE    NAME: ChangeGoodsQtyDialog.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.changingexp.client.ui.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.changingexp.client.action.ChangeGoodsQtyConfimrAction;
import com.deppon.foss.module.pickup.changingexp.client.ui.WaybillRFCUI;
import com.deppon.foss.module.pickup.changingexp.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.waybill.shared.define.LabeledGoodChangeHistoryConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodChangeHistoryDto;
import com.deppon.foss.util.define.FossConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 
 * 修改件数信息弹出框
 * @author 026123-foss-lifengteng
 *
 */
public class ChangeGoodsQtyDialog extends JDialog {
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(ChangeGoodsQtyDialog.class);
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * log
	 */
	private static Log LOG = LogFactory.getLog(ChangeGoodsQtyDialog.class);
		
	/**
	 * gif picture
	 */
	protected static final String SEARCH16GIF = "Search16.gif";
	
	private static final int SEVEN = 7;
	
	private static final int ELEVEN = 11;

	private static final int THREE = 3;
	
	private static final int FIFTEEN = 15;
	
	private static final int FOUR = 4;
	
	/**
	 * 界面上最新的WaybillInfo对象 和界面修改是同步的
	 */
	private WaybillInfoVo currentWaybillInfoVo;
	
	/**
	 * 导入的时候的WaybillInfo对象 
	 */
	private WaybillInfoVo originalWaybillInfoVo;
	
	/**
	 * 更改单UI
	 */
	private WaybillRFCUI ui;
	
	/**
	 * table panel
	 */
	private JPanel tablePanel;
	
	/**
	 * 表外滚动条
	 */
	private JScrollPane scrollPane;
	 
	
	/**
	 * 结果表
	 */
	private JXTable table = new JXTable();
	
	/**
	 * 下方panel
	 */
	private JPanel panel;
	
	/**
	 * 确定按钮
	 */
	private JButton button;
	
	/**
	 * 选择删除的jchecobox列表
	 */
	private List<JCheckBox> list;
	
	/**
	 * 构造方法
	 * @param currentWaybillInfoVo
	 * @param ui
	 */
	public ChangeGoodsQtyDialog
		(WaybillInfoVo currentWaybillInfoVo, WaybillRFCUI ui){
		this.currentWaybillInfoVo= currentWaybillInfoVo;
		this.ui=ui;
		this.originalWaybillInfoVo = ui.getOriginWaybill();
		EnterQtyListener clistener = new EnterQtyListener(this);
		this.addWindowListener(clistener);
		init();
	}
	
	class EnterQtyListener implements WindowListener{
		/**
		 * 更改单主UI
		 */
		private ChangeGoodsQtyDialog ui;
		
		public EnterQtyListener(ChangeGoodsQtyDialog dialog){
			this.ui = dialog;
		}

		
		public void windowOpened(WindowEvent e) {
			
		}

	
		public void windowClosing(WindowEvent e) {
			
			
			int totalQty = ui.getCurrentWaybillInfoVo().getGoodsQtyTotal();
			
			List<LabeledGoodChangeHistoryDto> list 
				= ui.getCurrentWaybillInfoVo().getLabeledGoodChangeHistoryDtoList();
			
			int totalQtyInList = 0;
			
			for(int i =0 ;i <list.size();i++){
				LabeledGoodChangeHistoryDto dtp = list.get(i);
				if(LabeledGoodChangeHistoryConstants.CHANGE_TYPE_DELETE.equals(dtp.getChangeType())){
					continue;
				}
				totalQtyInList ++;
			}
			
			if(totalQtyInList!=totalQty){
				MsgBox.showError(i18n.get("foss.gui.changingexp.changeGoodsQtyConfimrAction.msgBox.inconsistentQtyInList"));
			}
		}

	
		public void windowClosed(WindowEvent e) {
			
		}

		
		public void windowIconified(WindowEvent e) {
			
		}

	
		public void windowDeiconified(WindowEvent e) {
			
		}

		
		public void windowActivated(WindowEvent e) {
			
		}

		public void windowDeactivated(WindowEvent e) {
			
		}
		
	}

	/**
	 * 构造界面
	 */
	private void init() {
		
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("fill:max(75dlu;default):grow"),}));
		tablePanel = new JPanel();
		tablePanel.setBorder(new TitledBorder(null, i18n.get("foss.gui.changingexp.changeGoodsQtyDialog.init.changeGoodsDetailInfo.label"), 
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(tablePanel, "1, 2, fill, default");
		
		//初始化表格信息
		initTable();
		
		scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		tablePanel.add(scrollPane, "1, 3, 8, 1, fill, fill");
		
		panel = new JPanel();
		getContentPane().add(panel, "1, 3, fill, fill");
		
		FormLayout flpanel = new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:max(61dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(53dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(53dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(61dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(61dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(51dlu;default):grow"),},
			new RowSpec[] {
				RowSpec.decode("max(10dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(10dlu;default)"),});
		flpanel.setColumnGroups(new int[][]{new int[]{FIFTEEN, ELEVEN, SEVEN, THREE}});
		panel.setLayout(flpanel);
		
		button = new JButton(i18n.get("foss.gui.changingexp.waybillRFCUI.common.confirm"));
		panel.add(button, "13, 3");
		ChangeGoodsQtyConfimrAction action =new ChangeGoodsQtyConfimrAction();
		action.setIInjectUI(this);
		button.addActionListener(action);
		
		
		
		// Dialog监听ESC事件
		tablePanel.registerKeyboardAction(new EscHandler(), 
			KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
				
		// 设置为模态（当弹出窗口打开时，父窗口不能编辑）
		setModal(true);
		
		
		
		// 让窗口弹出时根据实际需要进行显示
		pack();
	}

	/**
	 * 初始化表格
	 */
	private void initTable() {
		
		//原来的件数  
		int originalGoodQty= originalWaybillInfoVo.getGoodsQtyTotal();
				
		//最新的件数最新的件数
		int newGoodsQty = currentWaybillInfoVo.getGoodsQtyTotal();
				
			//	currentWaybillInfoVo.getLabeledGoodChangeHistoryDtoList().size();
		
		//货签一共的list里面的件数
		int currentLisQty = currentWaybillInfoVo.getLabeledGoodChangeHistoryDtoList().size();
		
		String lineOrgCode = "";
		String lineName = ""; 
		if(originalWaybillInfoVo.getOrgInfoDto()!=null){
			lineOrgCode = originalWaybillInfoVo.getOrgInfoDto().getRouteLineCode();
			lineName = originalWaybillInfoVo.getOrgInfoDto().getRouteLineName();
		}
		
	
		if(StringUtils.isEmpty(lineOrgCode)){
			lineOrgCode = "";
			lineName = "";
		}
		
		String[] orgArray =  StringUtils.split(lineOrgCode, '-');
		String[] orgName = StringUtils.split(lineName,'-');
		
		if(orgArray==null || orgArray.length==0){
			//整车
			if(originalWaybillInfoVo.getIsWholeVehicle()!= null && originalWaybillInfoVo.getIsWholeVehicle()){
				orgArray = new String[]{""};
			}else{
				String defaultOrgCode = originalWaybillInfoVo.getReceiveOrgCode();
				String defaultOrgName =originalWaybillInfoVo.getReceiveOrgName();
				if(StringUtils.isEmpty(defaultOrgCode)){
					defaultOrgCode = originalWaybillInfoVo.getLoadOrgCode();
					defaultOrgName = originalWaybillInfoVo.getLoadOrgName();
				}
				if(StringUtils.isEmpty(defaultOrgCode)){
					defaultOrgCode = originalWaybillInfoVo.getLastLoadOrgCode();
					defaultOrgName = originalWaybillInfoVo.getLastLoadOrgName();
				}
				orgArray = new String[]{defaultOrgCode};
				orgName =  new String[]{defaultOrgName};
			}
			
			
		}
		
		
		if(orgName==null || orgName.length==0){
			orgName = new String[]{""};
		}
		
		
		if(newGoodsQty>currentLisQty){
			int addtionalQty = newGoodsQty - currentLisQty;
			
			for(int i=1; i<=addtionalQty; i++){
				List<LabeledGoodChangeHistoryDto> list = currentWaybillInfoVo.getLabeledGoodChangeHistoryDtoList();
				
				LabeledGoodChangeHistoryDto lastDto = list.get(list.size()-1);
				
				String serianlNo = lastDto.getSerialNo();
				
				int nextSerialNo = Integer.parseInt(serianlNo)+1;
				
				//合成下一个流水号
				String nextSerialNoStr = StringUtils.leftPad(Integer.toString(nextSerialNo), FOUR, '0');
				
				LabeledGoodChangeHistoryDto additionalChangeFromGUI = new LabeledGoodChangeHistoryDto();
				additionalChangeFromGUI.setSerialNo(nextSerialNoStr);
				additionalChangeFromGUI.setWaybillNo(originalWaybillInfoVo.getWaybillNo());
				//修改类型为新增
				additionalChangeFromGUI.setChangeType(
						LabeledGoodChangeHistoryConstants.CHANGE_TYPE_NEW);
				
				//不是从服务器端来的
				additionalChangeFromGUI.setIsFromService(FossConstants.NO);
				additionalChangeFromGUI.setChangeTimes(lastDto.getChangeTimes());
				
				//默认的网店
				additionalChangeFromGUI.setReceiveOrgCode(orgArray[0]);
				additionalChangeFromGUI.setReceiveOrgName(orgName[0]);
				
				//审批过程中
				additionalChangeFromGUI.setFlowStatus(LabeledGoodChangeHistoryConstants.FLOW_STATUS_IN_PROCESS);
				
				list.add(additionalChangeFromGUI);
			}
			
		//如果件数比导入得件数还要小
		}else if (newGoodsQty<=originalGoodQty){
			
			
			Iterator<LabeledGoodChangeHistoryDto> sListIterator =  currentWaybillInfoVo.getLabeledGoodChangeHistoryDtoList().iterator();  
			while(sListIterator.hasNext()){  
				LabeledGoodChangeHistoryDto dto = sListIterator.next(); 
				
				//去掉所有不是yes的件 （不是从服务器导入的件）
			    if(!FossConstants.YES.equals(dto.getIsFromService())){  
			    	sListIterator.remove();  
			    }  
			}
			
		//件数比一开始的时候导入得多， 但是又改小了
		}else if(newGoodsQty<currentLisQty){
			int deleteItemNo = currentLisQty - newGoodsQty;
			//去掉最后的item
			List<LabeledGoodChangeHistoryDto> list = currentWaybillInfoVo.getLabeledGoodChangeHistoryDtoList();
			
			
			for(int i = 1; i<=deleteItemNo; i++){
				
				list.remove(list.size()-1);//从末尾开始去掉
			}
			
			
		}
		
		ChangeGoodQtyMode tableModel = new ChangeGoodQtyMode(
				this.getArray(currentWaybillInfoVo.getLabeledGoodChangeHistoryDtoList()));
		table.setModel(tableModel);
		
		//选择
		ChangeModeCheckBoxColumn checkColumn 
			= new ChangeModeCheckBoxColumn(table.getColumn(i18n.get("foss.gui.changingexp.waybillRFCUI.common.delete")), this, currentWaybillInfoVo);
		
		List<JCheckBox> checList = checkColumn.getRenderButtonList();
		//send all check box to ui for select all
		setList(checList);
		
		if(LOG.isDebugEnabled()){
			LOG.debug(this.list);
		}
		
		
		new ChangeModeComboxColumn(table.getColumn(i18n.get("foss.gui.changingexp.changeGoodsQtyDialog.initTable.acceptDept.label")), this , originalWaybillInfoVo);
		
		
		refreshTable(table);
	}
	
	
	/**
	 * 将查询结果展现于jtable
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Object[][] getArray(List list) {
		
		if (list != null && !list.isEmpty()) {
			Object[][] objtemp = new Object[list.size()][]; // 转换为二维数组
			Object[] q;
			for (int i = 0; i < list.size(); i++) {
				LabeledGoodChangeHistoryDto dto = (LabeledGoodChangeHistoryDto) list
							.get(i);
				q = getRowValue(dto);
				for (int j = 0; j < objtemp.length; j++) {
					objtemp[i] = q;
				}
			}
			return objtemp;
		} else {
			return null;
		}
	}
	
	/**
	 * 刷新界面
	 * @param table
	 */
	public static void refreshTable(JXTable table){
		table.setAutoscrolls(true);
		table.setSortable(false);
		table.setColumnControlVisible(true);
		// 设置表头不伸缩模式：如果手工调整一个表头栏目，其他的不会跟随着变的   
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  
        table.setHorizontalScrollEnabled(true);
        // 设置表头的宽度   
        ChangeGoodQtyMode.adjustColumnPreferredWidths(table);
		
	}
	
	
	/**
	 * getRowValue结果列表
	 * @param object
	 * @return
	 */
	private Object[] getRowValue(LabeledGoodChangeHistoryDto dto) {
		
		
		//获取对象成员保存至一维数组
		Object[] obj = { "" ,dto.getSerialNo() ,"",dto};
		return obj;
	}
	

	/**
	 * @param list the list to set
	 */
	public void setList(List<JCheckBox> list) {
		this.list = list;
	}
	
	/**
	 * @return the currentWaybillInfoVo
	 */
	public WaybillInfoVo getCurrentWaybillInfoVo() {
		return currentWaybillInfoVo;
	}

	/**
	 * @param currentWaybillInfoVo the currentWaybillInfoVo to set
	 */
	public void setCurrentWaybillInfoVo(WaybillInfoVo currentWaybillInfoVo) {
		this.currentWaybillInfoVo = currentWaybillInfoVo;
	}

	/**
	 * @return the originalWaybillInfoVo
	 */
	public WaybillInfoVo getOriginalWaybillInfoVo() {
		return originalWaybillInfoVo;
	}

	/**
	 * @param originalWaybillInfoVo the originalWaybillInfoVo to set
	 */
	public void setOriginalWaybillInfoVo(WaybillInfoVo originalWaybillInfoVo) {
		this.originalWaybillInfoVo = originalWaybillInfoVo;
	}

	/**
	 * @return the ui
	 */
	public WaybillRFCUI getUi() {
		return ui;
	}

	/**
	 * @param ui the ui to set
	 */
	public void setUi(WaybillRFCUI ui) {
		this.ui = ui;
	}
	
	/**
	 * 定义一个一般内部类：设置dialog监听esc按键的处理事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-25 上午10:21:54
	 */
	private class EscHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}

	
}