package com.deppon.foss.module.pickup.creatingexp.client.ui;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class ExpReturnedGoodsCheckBoxColumn  extends AbstractCellEditor implements  TableCellEditor, TableCellRenderer  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JCheckBox jb;
	
	public ExpReturnedGoodsCheckBoxColumn(TableColumn tc){
		tc.setCellEditor(this);  
        tc.setCellRenderer(this);
	}
	@Override
	public Object getCellEditorValue() {
		return null;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		jb = new JCheckBox();
		jb.setHorizontalAlignment(JLabel.CENTER);
		jb.setFocusable(false);
		return jb;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		JCheckBox jcb = new JCheckBox();
		jcb.setHorizontalAlignment(JLabel.CENTER);
		return jcb;
	}  

}
