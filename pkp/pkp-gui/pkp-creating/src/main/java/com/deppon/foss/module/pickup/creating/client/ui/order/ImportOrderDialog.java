package com.deppon.foss.module.pickup.creating.client.ui.order;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class ImportOrderDialog extends JDialog{

	private static final long serialVersionUID = 1L;
	//日志
	public static final Logger LOGGER = LoggerFactory.getLogger(PendingCompleteDialog.class);

	/**
	 * 表格默认扩展
	 */
	private static final String DEFAULTGROW = "default:grow";
	
	private static final int NUM_600 = 600;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(PendingCompleteDialog.class); 
	
	private WaybillEditUI ui;

	public ImportOrderDialog(WaybillEditUI ui){
		this.ui = ui;
		init();
	}
	
	/**
	 * 初始化界面
	 * 
	 */
	private void init() {
		// 创建一个面板
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(NUM_600, NumberConstants.NUMBER_100));
		panel.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode(DEFAULTGROW), FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode(DEFAULTGROW), FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode(DEFAULTGROW), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode(DEFAULTGROW), }, new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, RowSpec.decode("10dlu"),
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		//是否导入订单
		JLabel label1 = new JLabel(i18n.get("foss.gui.creating.webOrderDialog.msgbox.orderRepeat2"));
		label1.setFont(new Font("Dialog", 1, NumberConstants.NUMBER_15));
		panel.add(label1, "1, 1, 7, 1 ,center, center");

		// 确认按钮
		JButton btnConfirm = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.confirm"));
		panel.add(btnConfirm, "3, 5");

		// 取消按钮
		JButton btnCancel = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.cancel"));
		panel.add(btnCancel, "7, 5");

		// Dialog监听ESC事件
		panel.registerKeyboardAction(new EscHandler(ui), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		// 添加“确定”按钮监听事件
		btnConfirm.addActionListener(new ConfirmHandler(ui));
		// 添加“取消”按钮监听事件
		btnCancel.addActionListener(new CancelHandler(ui));

		// 设置模态
		setModal(true);
		// 将panel加入到容器中
		getContentPane().add(panel);
		// 自动撑展弹出框
		pack();
	}
	
	/**
	 * 定义一个一般内部类：设置dialog监听esc按键的处理事件
	 */
	private class EscHandler implements ActionListener {
		WaybillEditUI ui;
		public EscHandler(WaybillEditUI ui){
			this.ui=ui;
		}
		/**
		 * 关闭当前打开的dialog
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			ui.setIsImportOrder(false);
			dispose();
		}
	}

	/**
	 * 定义一般内部类：处理“取消”按钮事件
	 */
	private class CancelHandler implements ActionListener {
		WaybillEditUI ui;
		public CancelHandler(WaybillEditUI ui){
			this.ui=ui;
		}
		/**
		 * 关闭当前打开的dialog
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			ui.setIsImportOrder(false);
			dispose();
		}
	}
	/**
	 * 定义一般内部类：处理“确定”按钮事件
	 */
	private class ConfirmHandler implements ActionListener {
		WaybillEditUI ui;
		public ConfirmHandler(WaybillEditUI ui){
			this.ui=ui;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			ui.setIsImportOrder(true);
			dispose();
		}
	}

}
