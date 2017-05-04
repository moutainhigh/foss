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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/paginationtable/DefaultNavigator.java
 * 
 * FILE NAME        	: DefaultNavigator.java
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
package com.deppon.foss.module.pickup.common.client.ui.paginationtable;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;

/**
 * 分页控件。
 * 
 * <p>
 * 该控件包含分页所需显示的信息（如总记录数、当前页面、页面容量等），以及分页控制（第一页、上一页、下一页、最后页、页面跳转等）。
 * <p>
 * 一般可加入到面板的下方，对实现<code>Navigatable</code>接口的列表进行排序。
 * <p>
 * 
 * @author shixiaowei
 * 
 */
public class DefaultNavigator extends JPanel {
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -3686142720195694938L;
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(DefaultNavigator.class);

	/**
	 * 排序对象
	 */
	private Navigatable target = null; 

	/**
	 * 总记录数
	 */
	private int totalCount = 0; 

	/**
	 * 页面总数
	 */
	private int pageCount = 1; // 

	/**
	 * 当前页面
	 */
	private int currentPage = 1; // 

	/**
	 * 页面容量
	 */
	private int pageSize = 0; // 

	/**
	 * 第一页
	 */
	private JButton buttonFirst = new JButton(); // 

	/**
	 * 上一页
	 */
	private JButton buttonPrev = new JButton(); // 

	/**
	 * 下一页
	 */
	private JButton buttonNext = new JButton(); // 

	/**
	 * 最后页
	 */
	private JButton buttonLast = new JButton(); // 

	/**
	 * 跳转
	 */
	private JButton buttonGo = new JButton(); // 

	/**
	 * 每页容量
	 */
	private JComboBox comboBoxPageSize = new JComboBox(); // 

	/**
	 * 跳转到
	 */
	private JTextField textFieldJumpTo = new TextFieldJumpTo(); // 

	/**
	 * count中文
	 */
	private JLabel labelBeforeTotalCount = new JLabel();

	private JLabel labelTotalCount = new JLabel();

	/**
	 * 条记录
	 */
	private JLabel labelAfterTotalCount = new JLabel();

	/**
	 * 共有
	 */
	private JLabel labelBeforePageCount = new JLabel();

	private JLabel labelPageCount = new JLabel();

	/**
	 * 页
	 */
	private JLabel labelAfterPageCount = new JLabel();

	/**
	 * 当前是第
	 */
	private JLabel labelBeforeCurrentPage = new JLabel();

	private JLabel labelCurrentPage = new JLabel();

	/**
	 * 页
	 */
	private JLabel labelAfterCurrentPage = new JLabel();

	/**
	 * 每页
	 */
	private JLabel labelBeforePageSize = new JLabel();

	/**
	 * 条
	 */
	private JLabel labelAfterPageSize = new JLabel();

	/**
	 * 转到第
	 */
	private JLabel labelBeforeJumpTo = new JLabel();

	/**
	 * 转到第
	 */
	private JLabel labelAfterJumpTo = new JLabel();

	/**
	 * layout布局
	 */
	private FlowLayout layout = new FlowLayout();
	
	/**
	 * 构造函数
	 */
	public DefaultNavigator() {
		init();
	}

	/**
	 * 构造函数，需传入待控制的target
	 * 
	 * @param target
	 */
	public DefaultNavigator(Navigatable target) {
		this.target = target;
		init();
	}

	
	/**
	 * 初始化
	 */
	private void init() {
		initUI();
		initBehavior();
	}

	/**
	 * 初始化UI
	 */
	private void initUI() {
		buttonFirst.setText(i18n.get("foss.gui.common.defaultNavigator.buttonFirst.label"));
		buttonFirst.setMnemonic('F');

		buttonPrev.setText(i18n.get("foss.gui.common.defaultNavigator.buttonPrev.label"));
		buttonPrev.setMnemonic('P');

		buttonNext.setText(i18n.get("foss.gui.common.defaultNavigator.buttonNext.label"));
		buttonNext.setMnemonic('N');
		buttonLast.setText(i18n.get("foss.gui.common.defaultNavigator.buttonLast.label"));
		buttonLast.setMnemonic('L');

		buttonGo.setText("Go");

		labelBeforeTotalCount.setText(i18n.get("foss.gui.common.defaultNavigator.beforeTotalCount.label"));
		labelAfterTotalCount.setText(i18n.get("foss.gui.common.defaultNavigator.afterTotalCount.label"));
		labelBeforePageCount.setText(i18n.get("foss.gui.common.defaultNavigator.beforeTotalCount.label"));
		labelAfterPageCount.setText(i18n.get("foss.gui.common.defaultNavigator.afterPageCount.label"));
		labelBeforeCurrentPage.setText(i18n.get("foss.gui.common.defaultNavigator.currentPage.label"));
		labelAfterCurrentPage.setText(i18n.get("foss.gui.common.defaultNavigator.afterPageCount.label"));
		labelBeforePageSize.setText(i18n.get("foss.gui.common.defaultNavigator.beforePageSize.label"));
		labelAfterPageSize.setText(i18n.get("foss.gui.common.defaultNavigator.afterPageSize.label"));
		labelBeforeJumpTo.setText(i18n.get("foss.gui.common.defaultNavigator.beforeJumpTo.label"));
		labelAfterJumpTo.setText(i18n.get("foss.gui.common.defaultNavigator.afterPageCount.label"));

		comboBoxPageSize.setModel(new DefaultComboBoxModel(new String[] {  "50", "100" }));
		textFieldJumpTo.setPreferredSize(new Dimension(NumberConstants.NUMBER_40, NumberConstants.NUMBER_25));

		layout.setAlignment(FlowLayout.CENTER);
		this.setLayout(layout);
		this.setPreferredSize(new Dimension(NumberConstants.NUMBER_10, NumberConstants.NUMBER_40));

		// 将各元素加入界面
		this.add(labelBeforeTotalCount);
		this.add(labelTotalCount);
		this.add(labelAfterTotalCount);
		this.add(labelBeforePageCount);
		this.add(labelPageCount);
		this.add(labelAfterPageCount);
		this.add(labelBeforeCurrentPage);
		this.add(labelCurrentPage);
		this.add(labelAfterCurrentPage);
		this.add(buttonFirst);
		this.add(buttonPrev);
		this.add(buttonNext);
		this.add(buttonLast);
		this.add(labelBeforePageSize);
		this.add(comboBoxPageSize);
		this.add(labelAfterPageSize);
		this.add(labelBeforeJumpTo);
		this.add(textFieldJumpTo);
		this.add(labelAfterJumpTo);
		this.add(buttonGo);
	}

	/**
	 * 初始化各部件的行为
	 */
	private void initBehavior() {
		
		/**
		 *  第一页监听
		 */
		buttonFirst.addActionListener(new java.awt.event.ActionListener() {
			/**
			 *  添加事件
			 */
			public void actionPerformed(ActionEvent e) {
				buttonFirstActionPerformed(e);
			}
		});
		
		/**
		 * 上页监听
		 */
		buttonPrev.addActionListener(new java.awt.event.ActionListener() {
			/**
			 *  添加事件
			 */
			public void actionPerformed(ActionEvent e) {
				buttonPrevActionPerformed(e);
			}
		});

		/**
		 *下页监听
		 */
		buttonNext.addActionListener(new java.awt.event.ActionListener() {
			/**
			 *  添加事件
			 */
			public void actionPerformed(ActionEvent e) {
				buttonNextActionPerformed(e);
			}
		});

		/**
		 *  最后页监听
		 */
		buttonLast.addActionListener(new java.awt.event.ActionListener() {
			/**
			 *  添加事件
			 */
			public void actionPerformed(ActionEvent e) {
				buttonLastActionPerformed(e);
			}
		});

		/**
		 * GO x 页监听
		 */
		buttonGo.addActionListener(new java.awt.event.ActionListener() {
			/**
			 *  添加事件
			 */
			public void actionPerformed(ActionEvent e) {
				buttonGoActionPerformed(e);
			}
		});

		/**
		 * 添加跳页的键盘监听，如果输入的页数<=0或>总页数，则清空输入框
		 */
		textFieldJumpTo.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				validate();
			}

			public void keyTyped(KeyEvent e) {
				validate();
			}

			/**
			 *  检查输入的数字是否有效
			 */
			private void validate() {
				if (!textFieldJumpTo.getText().equals("")) {
					int page = Integer.parseInt(textFieldJumpTo.getText());
					if (page <= 0 || page > pageCount) {
						textFieldJumpTo.setText("");
					}
				}
			}
		});

		setPageSize(NumberConstants.NUMBER_50);// default pagesize == 50
	}

	/**
	 * 设置每页容量
	 * 
	 * @param newPageSize
	 */
	public void setPageSize(int newPageSize) {
		String newPageSizeStr = String.valueOf(newPageSize);
		for (int i = 0; i < comboBoxPageSize.getModel().getSize(); i++) {
			if (newPageSizeStr.equals(comboBoxPageSize.getModel().getElementAt(
					i))) {
				comboBoxPageSize.setSelectedIndex(i);
				pageSize = newPageSize;
				return;
			}
		}
	}

	/**
	 * 最后页
	 * @param e
	 */
	private void buttonLastActionPerformed(ActionEvent e) {
		currentPage = Integer.MAX_VALUE;
		go();
	}

	/**
	 * 下页
	 * @param e
	 */
	private void buttonNextActionPerformed(ActionEvent e) {
		currentPage++;
		go();
	}

	/**
	 * 上页
	 * @param e
	 */
	private void buttonPrevActionPerformed(ActionEvent e) {
		currentPage--;
		go();
	}

	/**
	 * 第一页
	 * @param e
	 */
	private void buttonFirstActionPerformed(ActionEvent e) {
		currentPage = 1;
		go();
	}

	/**
	 * GO x 页
	 * @param e
	 */
	private void buttonGoActionPerformed(ActionEvent e) {
		// 如果跳转输入框为空，则默认为1
		if (textFieldJumpTo.getText().equals("")) {
			pageSize = Integer.valueOf(
					(String) comboBoxPageSize.getSelectedItem()).intValue();
			currentPage = 1;
			go();
		} else {
			// 获取每页记录数
			pageSize = Integer.valueOf(
					(String) comboBoxPageSize.getSelectedItem()).intValue();

			currentPage = Integer.valueOf(textFieldJumpTo.getText()).intValue();

			int newPageCount = ((totalCount - 1) / pageSize) + 1;

			// 如果跳转输入框中的页数大于计算后的新的页总数，则默认为1
			if (currentPage > newPageCount) {
				currentPage = 1;
			}
			go();
		}
	}

	/**
	 * 跳转页面
	 * 
	 */
	private void go() {
		if (target == null) {
			return;
		}
		// 得到总记录数
		if(currentPage<=pageCount ){
			target.setCurrentPage(currentPage);
		}else{
			target.setCurrentPage(pageCount);
		}
		target.setPageSize(pageSize);
		totalCount = target.getTotalCount();
		if (totalCount <= 0) {
			totalCount = 0;
			pageCount = 0;
			currentPage = 0;
			// diable all controlers
			setAllControler(false);
			target.pageTo(0, 0);
		} else {
			// enable all controlers
			setAllControler(true);
			// 设置总页数
			pageCount = ((totalCount - 1) / pageSize) + 1; // 总页数
			if (currentPage < 1) {
				currentPage = 1;
			}
			if (currentPage > pageCount) {
				currentPage = pageCount;
			}
			// 设置按钮

			if (pageCount <= 1) {
				buttonFirst.setEnabled(false);
				buttonPrev.setEnabled(false);
				buttonNext.setEnabled(false);
				buttonLast.setEnabled(false);

			} else {
				if (currentPage == 1) {
					buttonFirst.setEnabled(false);
					buttonPrev.setEnabled(false);
					buttonNext.setEnabled(true);
					buttonLast.setEnabled(true);
				} else if (currentPage == pageCount) {
					buttonFirst.setEnabled(true);
					buttonPrev.setEnabled(true);
					buttonNext.setEnabled(false);
					buttonLast.setEnabled(false);
				} else {
					buttonFirst.setEnabled(true);
					buttonPrev.setEnabled(true);
					buttonNext.setEnabled(true);
					buttonLast.setEnabled(true);
				}
			}
			target.pageTo(currentPage, pageSize);
		}
		// 显示label
		labelTotalCount.setText(String.valueOf(totalCount));
		labelPageCount.setText(String.valueOf(pageCount));
		labelCurrentPage.setText(String.valueOf(currentPage));
		// 清空跳转输入框
		textFieldJumpTo.setText("");
		textFieldJumpTo.requestFocus();
	}

	/**
	 * 设置按钮是否为DISABLED
	 * @param b
	 */
	private void setAllControler(boolean b) {
		buttonFirst.setEnabled(b);
		buttonPrev.setEnabled(b);
		buttonNext.setEnabled(b);
		buttonLast.setEnabled(b);
		buttonGo.setEnabled(b);
		comboBoxPageSize.setEnabled(b);
		textFieldJumpTo.setEnabled(b);
	}

	/**
	 * 设置初始化页面状态
	 * 
	 */
	public void initPageStatus() {
		initPageStatus(1,
				Integer.valueOf((String) comboBoxPageSize.getSelectedItem())
						.intValue());
	}

	/**
	 * 设置初始化页面状态
	 * 
	 * @param currentPage
	 * @param pageSize
	 */
	public void initPageStatus(int currentPage, int pageSize) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		go();
	}

	/**
	 * 更新数据
	 * 
	 */
	public void refresh() {
		go();
	}

	/**
	 * @return the target
	 */
	public Navigatable getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(Navigatable target) {
		this.target = target;
	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the pageCount
	 */
	public int getPageCount() {
		return pageCount;
	}

	/**
	 * @param pageCount the pageCount to set
	 */
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return the buttonFirst
	 */
	public JButton getButtonFirst() {
		return buttonFirst;
	}

	/**
	 * @param buttonFirst the buttonFirst to set
	 */
	public void setButtonFirst(JButton buttonFirst) {
		this.buttonFirst = buttonFirst;
	}

	/**
	 * @return the buttonPrev
	 */
	public JButton getButtonPrev() {
		return buttonPrev;
	}

	/**
	 * @param buttonPrev the buttonPrev to set
	 */
	public void setButtonPrev(JButton buttonPrev) {
		this.buttonPrev = buttonPrev;
	}

	/**
	 * @return the buttonNext
	 */
	public JButton getButtonNext() {
		return buttonNext;
	}

	/**
	 * @param buttonNext the buttonNext to set
	 */
	public void setButtonNext(JButton buttonNext) {
		this.buttonNext = buttonNext;
	}

	/**
	 * @return the buttonLast
	 */
	public JButton getButtonLast() {
		return buttonLast;
	}

	/**
	 * @param buttonLast the buttonLast to set
	 */
	public void setButtonLast(JButton buttonLast) {
		this.buttonLast = buttonLast;
	}

	/**
	 * @return the buttonGo
	 */
	public JButton getButtonGo() {
		return buttonGo;
	}

	/**
	 * @param buttonGo the buttonGo to set
	 */
	public void setButtonGo(JButton buttonGo) {
		this.buttonGo = buttonGo;
	}

	/**
	 * @return the comboBoxPageSize
	 */
	public JComboBox getComboBoxPageSize() {
		return comboBoxPageSize;
	}

	/**
	 * @param comboBoxPageSize the comboBoxPageSize to set
	 */
	public void setComboBoxPageSize(JComboBox comboBoxPageSize) {
		this.comboBoxPageSize = comboBoxPageSize;
	}

	/**
	 * @return the textFieldJumpTo
	 */
	public JTextField getTextFieldJumpTo() {
		return textFieldJumpTo;
	}

	/**
	 * @param textFieldJumpTo the textFieldJumpTo to set
	 */
	public void setTextFieldJumpTo(JTextField textFieldJumpTo) {
		this.textFieldJumpTo = textFieldJumpTo;
	}

	/**
	 * @return the labelBeforeTotalCount
	 */
	public JLabel getLabelBeforeTotalCount() {
		return labelBeforeTotalCount;
	}

	/**
	 * @param labelBeforeTotalCount the labelBeforeTotalCount to set
	 */
	public void setLabelBeforeTotalCount(JLabel labelBeforeTotalCount) {
		this.labelBeforeTotalCount = labelBeforeTotalCount;
	}

	/**
	 * @return the labelTotalCount
	 */
	public JLabel getLabelTotalCount() {
		return labelTotalCount;
	}

	/**
	 * @param labelTotalCount the labelTotalCount to set
	 */
	public void setLabelTotalCount(JLabel labelTotalCount) {
		this.labelTotalCount = labelTotalCount;
	}

	/**
	 * @return the labelAfterTotalCount
	 */
	public JLabel getLabelAfterTotalCount() {
		return labelAfterTotalCount;
	}

	/**
	 * @param labelAfterTotalCount the labelAfterTotalCount to set
	 */
	public void setLabelAfterTotalCount(JLabel labelAfterTotalCount) {
		this.labelAfterTotalCount = labelAfterTotalCount;
	}

	/**
	 * @return the labelBeforePageCount
	 */
	public JLabel getLabelBeforePageCount() {
		return labelBeforePageCount;
	}

	/**
	 * @param labelBeforePageCount the labelBeforePageCount to set
	 */
	public void setLabelBeforePageCount(JLabel labelBeforePageCount) {
		this.labelBeforePageCount = labelBeforePageCount;
	}

	/**
	 * @return the labelPageCount
	 */
	public JLabel getLabelPageCount() {
		return labelPageCount;
	}

	/**
	 * @param labelPageCount the labelPageCount to set
	 */
	public void setLabelPageCount(JLabel labelPageCount) {
		this.labelPageCount = labelPageCount;
	}

	/**
	 * @return the labelAfterPageCount
	 */
	public JLabel getLabelAfterPageCount() {
		return labelAfterPageCount;
	}

	/**
	 * @param labelAfterPageCount the labelAfterPageCount to set
	 */
	public void setLabelAfterPageCount(JLabel labelAfterPageCount) {
		this.labelAfterPageCount = labelAfterPageCount;
	}

	/**
	 * @return the labelBeforeCurrentPage
	 */
	public JLabel getLabelBeforeCurrentPage() {
		return labelBeforeCurrentPage;
	}

	/**
	 * @param labelBeforeCurrentPage the labelBeforeCurrentPage to set
	 */
	public void setLabelBeforeCurrentPage(JLabel labelBeforeCurrentPage) {
		this.labelBeforeCurrentPage = labelBeforeCurrentPage;
	}

	/**
	 * @return the labelCurrentPage
	 */
	public JLabel getLabelCurrentPage() {
		return labelCurrentPage;
	}

	/**
	 * @param labelCurrentPage the labelCurrentPage to set
	 */
	public void setLabelCurrentPage(JLabel labelCurrentPage) {
		this.labelCurrentPage = labelCurrentPage;
	}

	/**
	 * @return the labelAfterCurrentPage
	 */
	public JLabel getLabelAfterCurrentPage() {
		return labelAfterCurrentPage;
	}

	/**
	 * @param labelAfterCurrentPage the labelAfterCurrentPage to set
	 */
	public void setLabelAfterCurrentPage(JLabel labelAfterCurrentPage) {
		this.labelAfterCurrentPage = labelAfterCurrentPage;
	}

	/**
	 * @return the labelBeforePageSize
	 */
	public JLabel getLabelBeforePageSize() {
		return labelBeforePageSize;
	}

	/**
	 * @param labelBeforePageSize the labelBeforePageSize to set
	 */
	public void setLabelBeforePageSize(JLabel labelBeforePageSize) {
		this.labelBeforePageSize = labelBeforePageSize;
	}

	/**
	 * @return the labelAfterPageSize
	 */
	public JLabel getLabelAfterPageSize() {
		return labelAfterPageSize;
	}

	/**
	 * @param labelAfterPageSize the labelAfterPageSize to set
	 */
	public void setLabelAfterPageSize(JLabel labelAfterPageSize) {
		this.labelAfterPageSize = labelAfterPageSize;
	}

	/**
	 * @return the labelBeforeJumpTo
	 */
	public JLabel getLabelBeforeJumpTo() {
		return labelBeforeJumpTo;
	}

	/**
	 * @param labelBeforeJumpTo the labelBeforeJumpTo to set
	 */
	public void setLabelBeforeJumpTo(JLabel labelBeforeJumpTo) {
		this.labelBeforeJumpTo = labelBeforeJumpTo;
	}

	/**
	 * @return the labelAfterJumpTo
	 */
	public JLabel getLabelAfterJumpTo() {
		return labelAfterJumpTo;
	}

	/**
	 * @param labelAfterJumpTo the labelAfterJumpTo to set
	 */
	public void setLabelAfterJumpTo(JLabel labelAfterJumpTo) {
		this.labelAfterJumpTo = labelAfterJumpTo;
	}

	/**
	 * @return the layout
	 */
	public FlowLayout getLayout() {
		return layout;
	}

	/**
	 * @param layout the layout to set
	 */
	public void setLayout(FlowLayout layout) {
		this.layout = layout;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	
}

/**
 * 只接受数字的跳转输入框
 * 
 * @author sundw
 * 
 */
class TextFieldJumpTo extends JTextField {
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 8077431012317479681L;

	protected Document createDefaultModel() {
		return new JumpToDocument();
	}

	protected class JumpToDocument extends PlainDocument {
		/**
		 * 序列化版本号
		 */
		private static final long serialVersionUID = -6185645753358502126L;

		public void insertString(int offset, String str, AttributeSet a)
				throws BadLocationException {
			char[] insertChars = str.toCharArray();
			for (int i = 0; i < insertChars.length; i++) {
				if (!(Character.isDigit(insertChars[i]))) {
					return;
				}
			}
			super.insertString(offset, str, a);
		}
	}
}