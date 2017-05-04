package com.deppon.foss.module.pickup.common.client.ui.commonUI;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ListModel;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.pickup.common.client.utils.NumberDocument;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 打木托列表区间勾选组建
 * zxy 20131118 ISSUE-4391
 * @author 157229-zxy
 */
public class EnterYokeSearchPanel extends JPanel{
	private JTextFieldValidate tfStart;
	private JTextFieldValidate tfEnd;
	private JButton btnSelect;
	private JButton btnUnSelect;
	private JList list;
	
	private static final int FOUR = 4;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(EnterYokeSearchPanel.class); 
	
	public EnterYokeSearchPanel() {
		setLayout(
				new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("1dlu"),
				ColumnSpec.decode("max(19dlu;min)"),
				ColumnSpec.decode("1dlu"),
				ColumnSpec.decode("max(19dlu;min)"),
				ColumnSpec.decode("1dlu"),
				ColumnSpec.decode("max(7dlu;min)"),
				ColumnSpec.decode("1dlu"),
				ColumnSpec.decode("max(7dlu;min)"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));
		setAlignmentX(Component.LEFT_ALIGNMENT);
		
		tfStart = new JTextFieldValidate();
		add(tfStart, "2, 2, 1, 1, left, default");
		tfStart.setColumns(FOUR);
		NumberDocument numberStartDoc = new NumberDocument(FOUR);
		tfStart.setDocument(numberStartDoc);
		
		tfEnd = new JTextFieldValidate();
		add(tfEnd,"4, 2, 1, 1, left, default");
		tfEnd.setColumns(FOUR);
		NumberDocument numberEndDoc = new NumberDocument(FOUR);
		tfEnd.setDocument(numberEndDoc);
		
		JButton btnSelect = new JButton(i18n.get("foss.gui.creating.EnterYokeSearchPanel.select.label"));
		add(btnSelect, "6, 2, 1, 1, left, default");
		btnSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultSelectAction(true);
			}
		});
		
		JButton btnUnSelect = new JButton(i18n.get("foss.gui.creating.EnterYokeSearchPanel.unselect.label"));
		add(btnUnSelect, "6, 2, 1, 1, left, default");
		btnUnSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultSelectAction(false);
			}
		});
		add(btnUnSelect, "8, 2, 1, 1, left, default");
		
	}
	
	/**
	 * 默认选择事件
	 */
	public void defaultSelectAction(boolean isSelect){
		if(list == null){
			MsgBox.showInfo(i18n.get("foss.gui.common.EnterYokeSearchPanel.nullList"));
			return;
		}
		
		//zxy 20131224 MANA-285 start 新增:当区间未输入的情况下，开始区间默认1,结束区间默认最大
		ListModel lm = list.getModel();
		Integer size = lm.getSize();
		if(StringUtils.isBlank(tfStart.getText()))
			tfStart.setText("1");
		if(StringUtils.isBlank(tfEnd.getText()))
			tfEnd.setText(size.toString());
		//zxy 20131224 MANA-285 end 新增:当区间未输入的情况下，开始区间默认1,结束区间默认最大
		if(StringUtils.isNotBlank(tfStart.getText()) && StringUtils.isNotBlank(tfEnd.getText())){
			//开始,结束均可为0，此时会取消全部勾选
			Integer startInt = Integer.parseInt(tfStart.getText());
			Integer endInt = Integer.parseInt(tfEnd.getText());
			//判断是否超过列表区间
			
			if(startInt < 0 || endInt > size || startInt > endInt){
				MsgBox.showInfo(i18n.get("foss.gui.common.EnterYokeSearchPanel.validParam"));
				tfStart.requestFocus();
				return;
			}
			//勾选 
			//重置
//			list.removeSelectionInterval(0, size);
			if(endInt > 0){
				if(startInt == 0)
					startInt = 1;
				if(isSelect)
					list.addSelectionInterval(startInt - 1, endInt - 1);
				else
					list.removeSelectionInterval(startInt - 1, endInt - 1);
			}
		}else{
			MsgBox.showInfo(i18n.get("foss.gui.common.EnterYokeSearchPanel.nullStartEnd"));
			tfStart.requestFocus();
		}
	}

	/**
	 * 事件接口，可用来扩展
	 * @param searchAction
	 */
	public void addSelectActionListener(ActionListener selectAction) {
		removeAllActionListener();
		btnSelect.addActionListener(selectAction);
	}
	
	/**
	 * 事件接口，可用来扩展
	 * @param unSelectAction
	 */
	public void addUnSelectActionListener(ActionListener unSelectAction) {
		removeAllActionListener();
		btnUnSelect.addActionListener(unSelectAction);
	}
	
	public void removeAllActionListener(){
		ActionListener[] als = btnSelect.getActionListeners();
		for(ActionListener al : als){
			btnSelect.removeActionListener(al);
		}
	}

	public JTextFieldValidate getTfStart() {
		return tfStart;
	}

	public JTextFieldValidate getTfEnd() {
		return tfEnd;
	}

	public JButton getBtnSelect() {
		return btnSelect;
	}

	public JList getList() {
		return list;
	}

	public void setList(JList list) {
		this.list = list;
	}
	
	/**
	 * 清空区间输入框
	  * Description: 
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-12-24 下午4:06:26
	  * @param record
	  * @return
	 */
	public void clearText(){
		tfStart.setText("");
		tfEnd.setText("");
	}

}
