package com.deppon.foss.module.pickup.creating.client.ui.customer;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
/**
 * 
* @Description: 表格单选按钮
* @author hbhk 
* @date 2015-6-16 上午9:39:55
 */
public class TableRadioButtonCellRenderer extends JRadioButton implements
		TableCellRenderer {
	private static final long serialVersionUID = 2740159163003041567L;
	
	private static final int TEN = 10;

	public TableRadioButtonCellRenderer() {
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (hasFocus) {
			this.setSelected(true);
		} else {
			this.setSelected(false);
		}
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setSize(new Dimension(TEN, TEN));
		return this;
	}
}
