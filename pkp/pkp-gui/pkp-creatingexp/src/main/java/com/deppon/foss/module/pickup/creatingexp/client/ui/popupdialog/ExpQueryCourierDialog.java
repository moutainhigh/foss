package com.deppon.foss.module.pickup.creatingexp.client.ui.popupdialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 查询快递员信息Dialog
 * @author 218438-foss-zhulei
 */
public class ExpQueryCourierDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int NUM_531 = 531;

	private static final int NUM_407 = 407;

	private static final int FIVE = 5;

	private static final int TEN = 10;

	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(ExpQueryCourierDialog.class); 
	
	/**
	 * service
	 */
	private IWaybillService waybillService = WaybillServiceFactory.getWaybillService();
	
	/**
	 * panel
	 */
	private final JPanel contentPanel = new JPanel();
	/**
	 * 输入panel
	 */
	private JTextField txtCourier;
	
	/**
	 * 结果列表
	 */
	private JXTable table;
	
	/**
	 * 数据实体
	 */
	private EmployeeEntity employeeEntity;
	
	/**
	 * 查询按钮
	 */
	private JButton btnNewButton;
	
	/**
	 * 分页查询参数
	 */
	private int start;
	private int limit = Integer.MAX_VALUE;
	
	
	
	public EmployeeEntity getEmployeeEntity() {
		return employeeEntity;
	}

	public void setEmployeeEntity(EmployeeEntity employeeEntity) {
		this.employeeEntity = employeeEntity;
	}
	
	public ExpQueryCourierDialog(String empCode) {
		initTable();
		init(empCode);
		//监听Enter
		ExpEnterKeyQueryCourier enter = new ExpEnterKeyQueryCourier(btnNewButton);
		txtCourier.addKeyListener(enter);
		ExpEnterKeyQueryCourier enterTable = new ExpEnterKeyQueryCourier(this);
		table.addKeyListener(enterTable);
	}
	
	/**
	 * 初始化界面信息
	 */
	private void init(String empCode)
	{
		setBounds(NumberConstants.NUMBER_100, NumberConstants.NUMBER_100, NUM_531, NUM_407);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(FIVE, FIVE, FIVE, FIVE));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(59dlu;default):grow"),
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(43dlu;default):grow"),
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(18dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		{
			JLabel lblCourierName = new JLabel(
					"快递员：");
			contentPanel.add(lblCourierName, "5, 2, right, default");
		}
		{
			/**
			 * 快递员信息
			 */
			txtCourier = new JTextField();
			contentPanel.add(txtCourier, "9, 2, 6, 1, fill, default");
			txtCourier.setColumns(TEN);
		}
		{
			/**
			 * 根据输入快递员查询
			 */
			btnNewButton = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.query"));
			
			contentPanel.add(btnNewButton, "18, 2");
			if(StringUtils.isNotEmpty(empCode) ){
				employeeEntity = new EmployeeEntity();
				employeeEntity.setQueryParam(empCode);
				setData(waybillService.queryEmployeeExactByEntity4Selector(employeeEntity,start,limit));
		    }
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String courier = txtCourier.getText();
					if(courier==null || "".equals(courier))
					{
						MsgBox.showInfo(i18n.get("foss.gui.creatingexp.ExpQueryCourierDialog.msgbox.query"));
					}else{
						employeeEntity = new EmployeeEntity();
						employeeEntity.setQueryParam(courier);
						setData(waybillService.queryEmployeeExactByEntity4Selector(employeeEntity,start,limit));
				    }
					//默认选中查询结果的第一行
					if(table!=null && table.getRowCount()>0){
						table.requestFocus();
						table.setRowSelectionAllowed(true);
						table.setRowSelectionInterval(0,0);
					}
				}
			});
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
				JButton okButton = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.confirm"));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setCourierInfo();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
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
		// 设置模态
		setModal(true);
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
		table = new JXTable();
		//设置表格数据模型
		table.setModel(new CourierModel());
		//设置表格可以有滚动条
		table.setAutoscrolls(true);
		//禁止表格排序
		table.setSortable(false);
		//表格设置为不可编辑
		table.setEditable(false);
		//设置列可见状态
		table.setColumnControlVisible(true);

		// 居中显示
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, render);
		((DefaultTableCellRenderer) table.getTableHeader()
				.getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);
		table.addMouseListener(new ClickTableHandler());
	}
	
	/**
	 * 
	 * 将数据添加到表格
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:29:46
	 */
	public void setData(List<EmployeeEntity> data) {
		CourierModel model = new CourierModel();
		table.setModel(model);
		List<EmployeeEntity> empList = new ArrayList<EmployeeEntity>();
		for(int i=0 ;i<data.size();i++){
			if(StringUtils.isNotEmpty(data.get(i).getTitleName()) 
					&& ("快递员".equals(data.get(i).getTitleName().trim())
							||"快递员组长".equals(data.get(i).getTitleName().trim()))){
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = waybillService.queryOrgByUnifiedCode(data.get(i).getUnifieldCode());
				data.get(i).setOrgName(orgAdministrativeInfoEntity.getName());
				empList.add(data.get(i));
			}
		}
		model.setData(empList);
		// 刷新表格数据
		model.fireTableDataChanged();
	}
	
	/**
	 * 一般内部类：表格的单、双击处理事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-23 上午10:16:42
	 */
	private class ClickTableHandler extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			// 双击
			if (e.getClickCount() == 2) {
				setCourierInfo();
			}
		}
	}
	
	/**
	 * table表格监听事件
	 * @author WangQianJin
	 * @date 2013-5-22 下午4:54:18
	 */
	public void tableListenter(){
		setCourierInfo();
	}	
	
	/**
	 * 
	 * 设置快递员信息
	 * @author 025000-FOSS-helong
	 * @date 2012-12-6 下午07:40:27
	 */
	private void setCourierInfo()
	{
		//获取表格点击行数
		int row = table.getSelectedRow();
		if(row < 0)
		{
			MsgBox.showInfo(i18n.get("foss.gui.creating.salesDepartmentDialog.msgbox.select"));
		}else
		{
			//获取表格数据模型
			CourierModel model = ((CourierModel) table.getModel());
			//获取表格所有行记录
			List<EmployeeEntity> list = model.getData();
			if(list != null)
			{
				if(!list.isEmpty())
				{
					//获取选择的行记录
					employeeEntity = list.get(row);
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
	 * @author 025000-FOSS-helong
	 * @date 2012-11-3 上午11:30:03
	 */
	public class CourierModel extends DefaultTableModel {

		private static final long serialVersionUID = 5883365603131625962L;

		/**
		 * 表格数据集合
		 */
		private List<EmployeeEntity> data;
		
		/**
		 * 表格数据集合
		 */
		public List<EmployeeEntity> getData() {
			return data;
		}

		/**
		 * 表格数据集合
		 */
		public void setData(List<EmployeeEntity> data) {
			this.data = data;
		}

		/**
		 * 
		 * 重写表格获取列名称方法
		 * @author 025000-FOSS-helong
		 * @date 2012-12-24 下午09:03:18
		 * @param column
		 * @return
		 */
		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return i18n.get("foss.gui.creatingexp.ExpQueryCourierDialog.column.one");
			case 1:
				return i18n.get("foss.gui.creatingexp.ExpQueryCourierDialog.column.two");
			case 2:
				return i18n.get("foss.gui.creatingexp.ExpQueryCourierDialog.column.three");	
			default:
				return "";
			}
		}

		/**
		 * 
		 * 重写表格获取列总数方法
		 * @author 025000-FOSS-helong
		 * @date 2012-12-24 下午09:03:18
		 * @param column
		 * @return
		 */
		@Override
		public int getColumnCount() {
			return NumberConstants.NUMBER_3;
		}

		
		/**
		 * 
		 * 重写表格获取行总数方法
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
				return data.get(row).getEmpName();
			case 1:
				return data.get(row).getEmpCode();
			case 2:
				return data.get(row).getOrgName();
			default:
				return super.getValueAt(row, column);
			}
			
		}
	}
}
