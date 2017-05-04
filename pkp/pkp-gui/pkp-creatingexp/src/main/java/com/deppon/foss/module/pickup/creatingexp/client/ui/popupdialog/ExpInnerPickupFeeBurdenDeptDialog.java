/*
 * 费用承担部门选择弹出框
 */
package com.deppon.foss.module.pickup.creatingexp.client.ui.popupdialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTable;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.common.client.service.IOrgInfoService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;


public class ExpInnerPickupFeeBurdenDeptDialog extends JDialog {

	
	private static final long serialVersionUID = 1L;

	private static final int FIVE = 5;

	private static final int FOURZEROSEVEN = 407;

	private static final int FIVETHREEONE = 531;

	private static final int HUNDRED = 100;

	private static final int TEN = 10;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(ExpInnerPickupFeeBurdenDeptDialog.class); 

	
	// 组织服务类
	private IOrgInfoService orgInfoService = WaybillServiceFactory.getOrgInfoService();

	private final JPanel contentPanel = new JPanel();

	/**
	 * 费用承担部门名称输入框
	 */
	private JTextField txtFeeBurdenDeptName;

	/**
	 * 部门数据表格
	 */
	private JXTable table;
	
	//查询
	private JButton btnQueryButton;
 
	/**
	 * 费用承担部门实体
	 */
	private OrgAdministrativeInfoEntity orgAdministrativeInfoEntity;

	/**
	 * Create the dialog.
	 */
	public ExpInnerPickupFeeBurdenDeptDialog() {

		initTable();
		init();
		
		//Enter键监听事件
		ExpEnterKeyQueryFeeBurdenDept enterDepartment=new ExpEnterKeyQueryFeeBurdenDept(btnQueryButton);
		txtFeeBurdenDeptName.addKeyListener(enterDepartment);
		ExpEnterKeyQueryFeeBurdenDept enterTable=new ExpEnterKeyQueryFeeBurdenDept(this);
		table.addKeyListener(enterTable);
	}

	/**
	 * 
	 * 初始化界面信息
	 * 
	 */
	private void init() {
		setBounds(HUNDRED, HUNDRED, FIVETHREEONE, FOURZEROSEVEN);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(FIVE, FIVE, FIVE, FIVE));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"), }));
		{
			//部门名称
			JLabel txtFeeBurdenDeptName = new JLabel(i18n.get(
					"foss.gui.creatingexp.expInnerPickupFeeBurdenDeptDialog.expInnerPickupFeeBurdenDept.label")+"：");
			contentPanel.add(txtFeeBurdenDeptName, "2, 2, right, default");
		}
		{
			txtFeeBurdenDeptName = new JTextField();
			contentPanel.add(txtFeeBurdenDeptName,
					"4, 2, 12, 1, fill, default");
			txtFeeBurdenDeptName.setColumns(TEN);
		}
		{
			btnQueryButton = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.query"));
			btnQueryButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					String name = txtFeeBurdenDeptName.getText();
					if (name == null || "".equals(name)) {
						MsgBox.showInfo(i18n.get("foss.gui.creatingexp.expInnerPickupFeeBurdenDeptDialog.msgbox.query"));
					} else {
						setDataForOrgInfo(orgInfoService.queryOrgInfoByName(name));
					}
					//默认选中查询结果的第一行
					if(table!=null && table.getRowCount()>0){
						table.requestFocus();
						table.setRowSelectionAllowed(true);
						table.setRowSelectionInterval(0,0);
					}
				}
			});
			contentPanel.add(btnQueryButton, "16, 2");
		}
		{
			JScrollPane scrollPane = new JScrollPane(table);
			contentPanel.add(scrollPane, "2, 4, 19, 1, fill, fill");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton confirmButton = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.confirm"));
				confirmButton.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						setOrgAdministrativeInfo();
					}
				});
				confirmButton.setActionCommand("OK");
				buttonPane.add(confirmButton);
				//getRootPane().setDefaultButton(confirmButton);
			}
			{
				JButton cancelButton = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.cancel"));
				cancelButton.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

		this.setTitle(i18n.get("foss.gui.creatingexp.expInnerPickupFeeBurdenDeptDialog.expInnerPickupFeeBurdenDept.title"));
		
		// 设置模态
		setModal(true);
		
		// Dialog监听ESC事件
		contentPanel.registerKeyboardAction(new EscHandler(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		getContentPane().add(contentPanel);
	}

	
	/**
	 * 定义一个一般内部类：设置dialog监听esc按键的处理事件
	 * 
	 */
	private class EscHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
	
	/**
	 * 
	 * 初始化表格
	 * 
	 */
	private void initTable() {
		// 初始化表格
		table = new JXTable();
		// 设置表格数据模型
		table.setModel(new OrgAdministrativeInfoEntityModel());
		// 设置表格可以有滚动条
		table.setAutoscrolls(true);
		// 禁止表格排序
		table.setSortable(false);
		// 表格设置为不可编辑
		table.setEditable(false);
		// 设置列可见状态
		table.setColumnControlVisible(true);

		// 居中显示
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, render);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);
		table.addMouseListener(new ClickTableHandler());
	}

	/**
	 * 
	 * 将数据添加到表格
	 * 
	 */
	public void setData(List<OrgAdministrativeInfoEntity> OrgAdministrativeInfo) {
		FeeBurdenDeptModel model = ((FeeBurdenDeptModel) table.getModel());
		model.setData(OrgAdministrativeInfo);
		// 刷新表格数据
		model.fireTableDataChanged();
	}
	
	/**
	 * 
	 * 将数据添加到表格
	 * 
	 */
	public void setDataForOrgInfo(List<OrgAdministrativeInfoEntity> orgAdministrativeInfoEntity) {
		OrgAdministrativeInfoEntityModel model = ((OrgAdministrativeInfoEntityModel) table.getModel());
		model.setData(orgAdministrativeInfoEntity);
		// 刷新表格数据
		model.fireTableDataChanged();
	}

	/**
	 * 一般内部类：表格的单、双击处理事件
	 * 
	 */
	private class ClickTableHandler extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			// 双击
			if (e.getClickCount() == 2) {
				setOrgAdministrativeInfo();
			}
		}
	}
	
	/**
	 * table表格监听事件
	 * @author WangQianJin
	 * @date 2013-5-20 下午3:46:21
	 */
	public void tableListenter(){
		setOrgAdministrativeInfo();
	}

	/**
	 * 
	 * 设置部门信息
	 * 
	 */
	private void setOrgAdministrativeInfo() {
		int row = table.getSelectedRow();
		if (row < 0) {
			MsgBox.showInfo(i18n.get("foss.gui.creatingexp.expInnerPickupFeeBurdenDeptDialog.msgbox.select"));
		} else {
			OrgAdministrativeInfoEntityModel model = ((OrgAdministrativeInfoEntityModel) table.getModel());
			List<OrgAdministrativeInfoEntity> list = model.getData();
			if (list != null) {
				if (!list.isEmpty()) {
					orgAdministrativeInfoEntity = list.get(row);
					// 关闭界面，释放资源
					dispose();
				}
			}
		}

	}

	/**
	 * 
	 * 封装表格的数据模型，设置成以对象进行操作
	 * 
	 */
	public class FeeBurdenDeptModel extends DefaultTableModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * 表格数据集合
		 */
		private List<OrgAdministrativeInfoEntity> data;

		/**
		 * 表格数据集合
		 */
		public List<OrgAdministrativeInfoEntity> getData() {
			return data;
		}

		/**
		 * 表格数据集合
		 */
		public void setData(List<OrgAdministrativeInfoEntity> data) {
			this.data = data;
		}

		/**
		 * 
		 * 重写表格获取列名称方法
		 * 
		 */
		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return i18n.get("foss.gui.creatingexp.expInnerPickupFeeBurdenDeptDialog.column.one");
			case 1:
				return i18n.get("foss.gui.creatingexp.expInnerPickupFeeBurdenDeptDialog.column.two");
			default:
				return "";
			}
		}

		/**
		 * 
		 * 重写表格获取列总数方法
		 * 
		 */
		@Override
		public int getColumnCount() {
			return 2;
		}

		/**
		 * 
		 * 重写表格获取行总数方法
		 * 
		 */
		@Override
		public int getRowCount() {
			return data == null ? 0 : data.size();
		}

		/**
		 * 
		 * 重写获取数据的方法
		 * 
		 */
		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
			case 0:
				return data.get(row).getName();
			case 1:
				return data.get(row).getCode();
			default:
				return super.getValueAt(row, column);
			}
		}
	}
	
	/**
	 * 
	 * 封装表格的数据模型，设置成以对象进行操作
	 * 
	 */
	public class OrgAdministrativeInfoEntityModel extends DefaultTableModel {


		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * 表格数据集合
		 */
		private List<OrgAdministrativeInfoEntity> data;

		/**
		 * 表格数据集合
		 */
		public List<OrgAdministrativeInfoEntity> getData() {
			return data;
		}

		/**
		 * 表格数据集合
		 */
		public void setData(List<OrgAdministrativeInfoEntity> data) {
			this.data = data;
		}

		/**
		 * 
		 * 重写表格获取列名称方法
		 * 
		 * @author 025000-FOSS-helong
		 * @date 2012-12-24 下午09:03:18
		 * @param column
		 * @return
		 */
		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return i18n.get("foss.gui.creatingexp.expInnerPickupFeeBurdenDeptDialog.column.one");
			case 1:
				return i18n.get("foss.gui.creatingexp.expInnerPickupFeeBurdenDeptDialog.column.two");
			default:
				return "";
			}
		}

		/**
		 * 
		 * 重写表格获取列总数方法
		 * 
		 * @author 025000-FOSS-helong
		 * @date 2012-12-24 下午09:03:18
		 * @param column
		 * @return
		 */
		@Override
		public int getColumnCount() {
			return 2;
		}

		/**
		 * 
		 * 重写表格获取行总数方法
		 * 
		 * @author 025000-FOSS-helong
		 * @date 2012-12-24 下午09:03:18
		 * @param column
		 * @return
		 */
		@Override
		public int getRowCount() {
			return data == null ? 0 : data.size();
		}

		/**
		 * 
		 * 重写获取数据的方法
		 * 
		 * @author 025000-FOSS-helong
		 * @date 2012-12-24 下午09:08:19
		 * @param row
		 * @param column
		 * @return
		 */
		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
			case 0:
				return data.get(row).getName();
			case 1:
				return data.get(row).getCode();
			default:
				return super.getValueAt(row, column);
			}
		}
	}

	public OrgAdministrativeInfoEntity getOrgAdministrativeInfoEntity() {
		return orgAdministrativeInfoEntity;
	}

	public void setOrgAdministrativeInfoEntity(
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity) {
		this.orgAdministrativeInfoEntity = orgAdministrativeInfoEntity;
	}
}