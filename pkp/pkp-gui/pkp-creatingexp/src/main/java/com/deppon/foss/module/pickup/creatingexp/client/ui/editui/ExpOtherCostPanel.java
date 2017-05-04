/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.ui.editui;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.module.pickup.common.client.vo.DeliverChargeEntity;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpOtherCostPanel  extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(ExpOtherCostPanel.class);

	JXTable tblOther;

	/**
	 * 构造方法
	 */
	public ExpOtherCostPanel() {

		initTable();

		init();

	}

	/**
	 * 初始化界面信息
	 */
	private void init() {

		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), }));

		JScrollPane scrollPane = new JScrollPane(tblOther);
		add(scrollPane, "1, 2, 22, 5, fill, fill");
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
		tblOther = new JXTable();
		//设置表格数据模型
		tblOther.setModel(new WaybillOtherChargeCanvas());
		//设置表格可以有滚动条
		tblOther.setAutoscrolls(true);
		//禁止表格排序
		tblOther.setSortable(false);
		//表格设置为不可编辑
		tblOther.setEditable(false);
		//设置列可见状态
		tblOther.setColumnControlVisible(true);

		// 居中显示
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		tblOther.setDefaultRenderer(Object.class, render);
		((DefaultTableCellRenderer) tblOther.getTableHeader()
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
	public void setChangeDetail(List<DeliverChargeEntity> changeDetailList) {
		WaybillOtherChargeCanvas changeInfoDetailTableModel = ((WaybillOtherChargeCanvas) tblOther
				.getModel());
		changeInfoDetailTableModel.setData(changeDetailList);
		// 刷新表格数据
		changeInfoDetailTableModel.fireTableDataChanged();
	}

	/**
	 * 
	 * 封装表格的数据模型，设置成以对象进行操作
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:30:03
	 */
	public class WaybillOtherChargeCanvas extends DefaultTableModel {

		private static final long serialVersionUID = 5883365603131625962L;

		private List<DeliverChargeEntity> changeDetailList;

		public List<DeliverChargeEntity> getData() {
			return changeDetailList;
		}

		public void setData(List<DeliverChargeEntity> changeDetailList) {
			this.changeDetailList = changeDetailList;
		}

		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return i18n.get("foss.gui.creating.incrementPanel.gridName.label");
			case 1:
				return i18n.get("foss.gui.creating.incrementPanel.gridValue.label");
			default:
				return "";
			}
		}

		@Override
		public int getColumnCount() {
			return 2;
		}

		@Override
		public int getRowCount() {
			return changeDetailList == null ? 0 : changeDetailList.size();
		}

		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
			case 0:
				return changeDetailList.get(row).getName();
			case 1:
				return changeDetailList.get(row).getAmount();
			default:
				return super.getValueAt(row, column);
			}

		}
	}

	public JXTable getTblOther() {
		return tblOther;
	}

}
