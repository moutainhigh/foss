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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/ui/order/PendingCompleteDialog.java
 * 
 * FILE NAME        	: PendingCompleteDialog.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-creating
 * PACKAGE NAME: com.deppon.foss.module.pickup.creating.client.ui.order
 * FILE    NAME: PendingCompleteDialog.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.creating.client.ui.order;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.common.client.utils.MessageI18nUtil;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 运单待补录对话框
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-12-5 上午9:43:48
 */
public class PendingCompleteDialog extends JDialog {
	//日志
	public static final Logger LOGGER = LoggerFactory.getLogger(PendingCompleteDialog.class);

	/**
	 * 表格默认扩展
	 */
	private static final String DEFAULTGROW = "default:grow";

	/**
	 * 设置表格列的个数
	 */
	private static final int TEN = 10;

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = -7549754011546660024L;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(PendingCompleteDialog.class); 

	/**
	 * 通过工厂类获得运单服务类
	 */
	private IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	/**
	 * 待处理运单DTO
	 */
	private WaybillPendingDto waybillPendingDto = null;

	/**
	 * 运单号
	 * 
	 */
	private JTextField txtWaybillNo;

	/**
	 * 构造方法：整体布局
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-25 上午8:35:17
	 */
	public PendingCompleteDialog() {
		init();
	}
	
	/**
	 * 定义一般内部类：处理“确定”按钮事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-22 下午2:46:58
	 */
	private class ConfirmHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			pendingComplete();
			
		}
	}
	
	

	/**
	 * 定义一个一般内部类：设置dialog监听esc按键的处理事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-25 上午10:21:54
	 */
	private class EscHandler implements ActionListener {

		/**
		 * 关闭当前打开的dialog
		 * 
		 * @author 026123-foss-lifengteng
		 * @date 2012-12-25 上午8:37:13
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}

	/**
	 * 定义一般内部类：处理“取消”按钮事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-22 下午2:47:51
	 */
	private class CancelHandler implements ActionListener {

		/**
		 * 关闭当前打开的dialog
		 * 
		 * @author 026123-foss-lifengteng
		 * @date 2012-12-25 上午8:37:13
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
	/**
	 * 定义一个一般内部类：设置dialog监听enter按键的处理事件
	 * @author DP-FOSS-DONGJIALING
	 *
	 */
	private class enterHandler extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			int k=e.getKeyCode();
			if(k == KeyEvent.VK_ENTER) {
				pendingComplete();
			}
			
		}

		
		
	}
	//运单补录跳转
	public void pendingComplete() {
		try{
			String waybillNo = txtWaybillNo.getText().trim();
			waybillPendingDto = waybillService.queryPendingWaybill(waybillNo);
			//查询对象非空判断
			if (waybillPendingDto == null) {
				MsgBox.showError(i18n.get("foss.gui.creating.pendingCompleteDialog.msgbox.label"));
			} else {
				//判断是否为零担电子运单 - sangwenhao
				if(waybillPendingDto.getWaybillPending() != null 
						&& StringUtils.equals(waybillPendingDto.getWaybillPending().getWaybillType(), WaybillConstants.WAYBILL_STATUS_LTLEWAYBILL)){
					waybillPendingDto = null ;
					throw new WaybillValidateException(i18n.get("foss.pkp.waybill.waybillManagerService.exception.ltlewaybill"));
				}
				// 是否确认补录
				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(PendingCompleteDialog.this, 
						i18n.get("foss.gui.creating.pendingCompleteDialog.showdialog.label"), 
						i18n.get("foss.gui.creating.waybillEditUI.common.prompt"), JOptionPane.YES_NO_OPTION))
				{
					// 校验运单号是否已存在
					validateWaybillNo(waybillPendingDto.getWaybillPending().getPendingType(),waybillPendingDto.getWaybillPending().getWaybillNo());
					
					// 若确认补录关闭当前弹出框，进入运单开单界面，否则回到待补录运单录入框界面
					dispose();
				}else{
					waybillPendingDto = null;
				}
			}
		}catch (BusinessException w) {
			if (StringUtils.isNotEmpty(w.getMessage())) {
				MsgBox.showInfo(MessageI18nUtil.getMessage(w, i18n));
			}else if(StringUtils.isNotEmpty(w.getErrorCode())){
			    MsgBox.showInfo(w.getErrorCode());
			}
		} 
	}
	
	// 校验运单号是否已存在
		private void validateWaybillNo(String pendingType,String waybillNo){		
			if(WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(pendingType)){
				//PDA补录
				if (waybillService.checkWaybillNoExceptPDAPending(waybillNo) && waybillService.checkEWaybillNoExceptPDAPending(waybillNo)) {		    			
	    			throw new WaybillValidateException(i18n.get("foss.pkp.waybill.waybillManagerService.exception.wayBillExsits",new Object[]{waybillNo}));
	    		}
			}else{
				//非PDA补录
	    		if (waybillService.checkWaybillNo(waybillNo)) {		    			
	    			throw new WaybillValidateException(i18n.get("foss.pkp.waybill.waybillManagerService.exception.wayBillExsits",new Object[]{waybillNo}));
	    		}
			}
		}
	/**
	 * 初始化界面
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-5 上午9:58:56
	 */
	private void init() {
		// 创建一个面板
		JPanel panel = new JPanel();
		panel.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode(DEFAULTGROW), FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode(DEFAULTGROW), FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode(DEFAULTGROW), }, new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, RowSpec.decode("10dlu"),
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		// 待补录运单号
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.pendingCompleteDialog.waybillNo.label")+"：");
		panel.add(label1, "1, 1, 7, 1");

		// 运单号输入框
		txtWaybillNo = new JTextField();
		panel.add(txtWaybillNo, "2, 3, 7, 1, fill, default");
		txtWaybillNo.setColumns(TEN);

		// 确认按钮
		JButton btnConfirm = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.confirm"));
		panel.add(btnConfirm, "3, 5");

		// 取消按钮
		JButton btnCancel = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.cancel"));
		panel.add(btnCancel, "7, 5");

		// Dialog监听ESC事件
		panel.registerKeyboardAction(new EscHandler(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		//Dialog监听enter事件
		txtWaybillNo.addKeyListener(new enterHandler());
		// 添加“确定”按钮监听事件
		btnConfirm.addActionListener(new ConfirmHandler());
		// 添加“取消”按钮监听事件
		btnCancel.addActionListener(new CancelHandler());

		// 设置模态
		setModal(true);
		// 将panel加入到容器中
		getContentPane().add(panel);
		// 自动撑展弹出框
		pack();
	}

	/**
	 * @return the waybillPendingDto .
	 */
	public WaybillPendingDto getWaybillPendingDto() {
		return waybillPendingDto;
	}

	/**
	 * @param waybillPendingDto
	 *            the waybillPendingDto to set.
	 */
	public void setWaybillPendingDto(WaybillPendingDto waybillPendingDto) {
		this.waybillPendingDto = waybillPendingDto;
	}
}