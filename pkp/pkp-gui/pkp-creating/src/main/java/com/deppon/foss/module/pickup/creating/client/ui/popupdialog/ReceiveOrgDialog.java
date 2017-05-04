package com.deppon.foss.module.pickup.creating.client.ui.popupdialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.common.client.dto.QueryPickupPointDto;
import com.deppon.foss.module.pickup.common.client.service.DownLoadDataServiceFactory;
import com.deppon.foss.util.define.FossConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 查询收货部门Dialog
 * 
 * @author WangQianJin
 * @date 2013-07-17
 */
public class ReceiveOrgDialog extends JDialog {
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -5960720224792355823L;

	private static final int FIVE = 5;

	private static final int NUM_407 = 407;

	private static final int NUM_531 = 531;

	private static final int TEN = 10;
	
	/**
	 * 国际化对象
	 */
	private II18n i18n = I18nManager.getI18n(ReceiveOrgDialog.class); 

	/**
	 * panel
	 */
	private final JPanel contentPanel = new JPanel();
	
	/**
	 * 输入panel
	 */
	private JTextField txtSalesDepartmentName;
	
	/**
	 * 结果列表
	 */
	private JXTable table;
	
	/**
	 * 数据实体
	 */
	private SaleDepartmentEntity saleDepartmentEntity;
	
	//查询
	private JButton btnNewButton;
	
	/**
	 * Create the dialog.
	 */
	public ReceiveOrgDialog() {
		initTable();
		init();
		EnterKeyQuerySalesDepartment enter=new EnterKeyQuerySalesDepartment(btnNewButton);
		txtSalesDepartmentName.addKeyListener(enter);
		EnterKeyQuerySalesDepartment enterTable=new EnterKeyQuerySalesDepartment(this);
		table.addKeyListener(enterTable);
	}
	
	/**
	 * 初始化界面信息
	 */
	private void init()
	{
		setBounds(NumberConstants.NUMBER_100, NumberConstants.NUMBER_100, NUM_531, NUM_407);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(FIVE, FIVE, FIVE, FIVE));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
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
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		{
			JLabel lblNewLabel = new JLabel(
					i18n.get("foss.gui.creating.salesDepartmentDialog.salesDepartmentName.label")
							+ "：");
			contentPanel.add(lblNewLabel, "2, 2, right, default");
		}
		{
			/**
			 * 部门名称
			 */
			txtSalesDepartmentName = new JTextField();
			contentPanel.add(txtSalesDepartmentName, "4, 2, 12, 1, fill, default");
			txtSalesDepartmentName.setColumns(TEN);
		}
		{
			/**
			 * 根据输入部门名称查询收货部门
			 */
			btnNewButton = new JButton(i18n.get("foss.gui.creating.waybillEditUI.common.query"));
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String name = txtSalesDepartmentName.getText();
					if(name == null || "".equals(name)){
						MsgBox.showInfo(i18n.get("foss.gui.creating.salesDepartmentDialog.msgbox.query"));
					}else{
						// 查询条件						
						QueryPickupPointDto dto=new QueryPickupPointDto();
						// 名称
						dto.setPickUpPoint(name.trim());
						// 当前时间(当前日期要大于等于开业日期)
						dto.setCurDate(new java.util.Date());
						// 是否有效
						dto.setActive(FossConstants.ACTIVE);
						// 设置营业部的出发属性
						dto.setLeave(FossConstants.YES);
						// 根据名称查询自有网点
						setData(DownLoadDataServiceFactory.getSalesDepartmentService().querySalesDepartmentInfo(dto));						
					}
					//默认选中查询结果的第一行
					if(table!=null && table.getRowCount()>0){
						table.requestFocus();
						table.setRowSelectionAllowed(true);
						table.setRowSelectionInterval(0,0);
					}
				}
			});
			contentPanel.add(btnNewButton, "16, 2");
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
						setSaleDepartmentInfo();
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
		table.setModel(new SaleDepartmentModel());
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
	public void setData(List<SaleDepartmentEntity> saleDepartment) {
		SaleDepartmentModel model = ((SaleDepartmentModel) table.getModel());
		model.setData(saleDepartment);
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
				setSaleDepartmentInfo();
			}
		}
	}
	
	/**
	 * table表格监听事件
	 * @author WangQianJin
	 * @date 2013-5-22 下午4:54:18
	 */
	public void tableListenter(){
		setSaleDepartmentInfo();
	}	
	
	/**
	 * 
	 * 设置银行信息
	 * @author 025000-FOSS-helong
	 * @date 2012-12-6 下午07:40:27
	 */
	private void setSaleDepartmentInfo(){
		//获取表格点击行数
		int row = table.getSelectedRow();
		if(row < 0)
		{
			MsgBox.showInfo(i18n.get("foss.gui.creating.salesDepartmentDialog.msgbox.select"));
		}else
		{
			//获取表格数据模型
			SaleDepartmentModel model = ((SaleDepartmentModel) table.getModel());
			//获取表格所有行记录
			List<SaleDepartmentEntity> list = model.getData();
			if(list != null)
			{
				if(!list.isEmpty())
				{
					//获取选择的行记录
					saleDepartmentEntity = list.get(row);
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
	public class SaleDepartmentModel extends DefaultTableModel {

		private static final long serialVersionUID = 5883365603131625962L;

		/**
		 * 表格数据集合
		 */
		private List<SaleDepartmentEntity> data;
		
		/**
		 * 表格数据集合
		 */
		public List<SaleDepartmentEntity> getData() {
			return data;
		}

		/**
		 * 表格数据集合
		 */
		public void setData(List<SaleDepartmentEntity> data) {
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
				return i18n.get("foss.gui.creating.salesDepartmentDialog.column.one");
			case 1:
				return i18n.get("foss.gui.creating.salesDepartmentDialog.column.two");
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
			return 2;
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
				return data.get(row).getName();
			case 1:
				return data.get(row).getCode();
			default:
				return super.getValueAt(row, column);
			}
		}
	}
	
	/**
	 * getSaleDepartmentEntity
	 * @return SaleDepartmentEntity
	 */ 
	public SaleDepartmentEntity getSaleDepartmentEntity() {
		return saleDepartmentEntity;
	}
	
	/**
	 * setSaleDepartmentEntity
	 * @param saleDepartmentEntity SaleDepartmentEntity
	 */
	public void setSaleDepartmentEntity(SaleDepartmentEntity saleDepartmentEntity) {
		this.saleDepartmentEntity = saleDepartmentEntity;
	}
}