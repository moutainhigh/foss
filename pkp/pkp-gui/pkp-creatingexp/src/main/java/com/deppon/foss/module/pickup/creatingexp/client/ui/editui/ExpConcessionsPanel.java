/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.ui.editui;

import java.util.Enumeration;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.module.pickup.common.client.vo.WaybillDiscountVo;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpConcessionsPanel  extends JPanel {
	
	private static final int THREE = 3;

	private static final int FOUR = 4;

	private static final long serialVersionUID = 1L;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(ExpConcessionsPanel.class);

	JXTable tblConcessions;

	public ExpConcessionsPanel() {
		init();
	}

	private void init() {
		
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), }));
		
		initTable();
		
		JScrollPane scrollPane = new JScrollPane(tblConcessions);
		add(scrollPane, "2, 2, fill, fill");
	}



	/**
	 * 
	 * 初始化表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午10:47:03
	 */
	private void initTable() {
		//初始化表格
		tblConcessions = new JXTable();
		//设置表格数据模型
		tblConcessions.setModel(new WaybillDiscountCanvas());
		//设置表格可以有滚动条
		tblConcessions.setAutoscrolls(true);
		//禁止表格排序
		tblConcessions.setSortable(false);
		//表格设置为不可编辑
		tblConcessions.setEditable(false);
		//设置列可见状态
		tblConcessions.setColumnControlVisible(true);
		//根据内容设置列宽
		fitTableColumnsWidth(tblConcessions, NumberConstants.NUMBER_5);
		
		// 居中显示
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		tblConcessions.setDefaultRenderer(Object.class, render);
		((DefaultTableCellRenderer) tblConcessions.getTableHeader()
				.getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);
	}

	/**
	 * 
	 * 将数据添加到表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:29:46
	 */
	public void setChangeDetail(List<WaybillDiscountVo> data) {
		WaybillDiscountCanvas discountTableModel = ((WaybillDiscountCanvas) tblConcessions
				.getModel());
		discountTableModel.setData(data);
		// 刷新表格数据
		discountTableModel.fireTableDataChanged();
	}

	/**
	 * 
	 * 封装表格的数据模型，设置成以对象进行操作
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:30:03
	 */
	public class WaybillDiscountCanvas extends DefaultTableModel {

		private static final long serialVersionUID = 5883365603131625962L;

		/**
		 * 折扣优惠数据VO
		 */
		private List<WaybillDiscountVo> data;


		public List<WaybillDiscountVo> getData() {
			return data;
		}

		public void setData(List<WaybillDiscountVo> data) {
			this.data = data;
		}
		

		/**
		 * 
		 * 重写表格数据模型获取列名称的方法
		 * @author 025000-FOSS-helong
		 * @date 2012-12-27 下午02:18:23
		 * @param column
		 * @return
		 */
		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return i18n.get("foss.gui.creating.concessionsPanel.item.label");
			case 1:
				return i18n.get("foss.gui.creating.concessionsPanel.type.label");
			case 2:
				return i18n.get("foss.gui.creating.concessionsPanel.discount.label1");
			case THREE:
				return i18n.get("foss.gui.creating.concessionsPanel.continueDiscount.label");
			case FOUR:
				return i18n.get("foss.gui.creating.concessionsPanel.amount.label");
			default:
				return "";
			}
		}

		/**
		 * 
		 * 重写数据获取列总数的方法
		 * @author 025000-FOSS-helong
		 * @date 2012-12-27 下午02:22:47
		 * @return
		 */
		@Override
		public int getColumnCount() {
			return 5;
		}

		/**
		 * 
		 * 重写获取行记录总数的方法
		 * @author 025000-FOSS-helong
		 * @date 2012-12-27 下午02:23:41
		 * @return
		 */
		@Override
		public int getRowCount() {
			return data == null ? 0 : data.size();
		}

		/**
		 * 
		 * 重写获取列值的方法
		 * @author 025000-FOSS-helong
		 * @date 2012-12-27 下午02:24:01
		 * @param row
		 * @param column
		 * @return
		 */
		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
			case 0:
				return data.get(row).getFavorableItemName();
			case 1:
				return data.get(row).getFavorableTypeName();
			case 2:
				return data.get(row).getFavorableDiscount();
			case THREE:
				return data.get(row).getContinueFavorableDiscount();
			case FOUR:
				return data.get(row).getFavorableAmount();
			default:
				return super.getValueAt(row, column);
			}

		}
	}

	public JXTable getTblConcessions() {
		return tblConcessions;
	}
	/**
	 * dongjialing
	 * 自适应列宽
	 * @param table
	 * @param addtionalSpace
	 */
	public static void fitTableColumnsWidth(JXTable table, int addtionalSpace) {
		table.setAutoResizeMode(JXTable.AUTO_RESIZE_OFF);
        JTableHeader tableHeader = table.getTableHeader();
        int rowCount = table.getRowCount();
        Enumeration<TableColumn> columns = table.getColumnModel().getColumns();
        while (columns.hasMoreElements()) {
            TableColumn column = (TableColumn) columns.nextElement();
            int col = tableHeader.getColumnModel().getColumnIndex(column.getIdentifier());
            int width = (int) table.getTableHeader().getDefaultRenderer()
                    .getTableCellRendererComponent(table, column.getIdentifier()
                            , false, false, -1, col).getPreferredSize().getWidth();
            for (int row = 0; row < rowCount; row++) {
                int preferedWidth = (int) table.getCellRenderer(row, col).getTableCellRendererComponent(table,
                        table.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
                width = Math.max(width, preferedWidth);
            }
            tableHeader.setResizingColumn(column);
            column.setWidth(width + table.getIntercellSpacing().width + addtionalSpace);
        }
        
    }

}
