package com.deppon.foss.module.pickup.creating.client.ui.print;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.module.pickup.creating.client.ui.CustomerCouponTableModel;
import com.deppon.foss.module.pickup.creating.client.ui.WaybillEditUI;
import com.deppon.foss.module.pickup.waybill.shared.domain.CustomerCouponEntity;
import com.deppon.foss.module.pickup.waybill.shared.hessian.ICustomerHessianRemoting;
import com.deppon.foss.util.define.FossConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 
* @Description: 客户优惠券对话框
* @author hbhk 
* @date 2015-6-15 上午10:08:54
 */
public class CustomerCouponDialog extends JDialog {

	// 日志
	private static final Log LOG = LogFactory.getLog(CustomerCouponDialog.class);

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -7959544878097277415L;

	private static final int NUM_550 = 550;
	
	/**
	 * 结果列表
	 */
	private JXTable table;
	
	private JPanel panel;
	
	private WaybillEditUI waybillEditUI;
	
	private ICustomerHessianRemoting customerHessianRemoting;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static List<String>  customerCoupons = new ArrayList<String>();
	/**
	 * 构造方法
	 * 
	 * @param waybillNo
	 */
	public CustomerCouponDialog(String customCode,String phone,WaybillEditUI waybillEditUI) {
		super();
		this.waybillEditUI = waybillEditUI;
		this.setTitle("客户优惠券");
		this.setSize(NUM_550, NumberConstants.NUMBER_300);
		this.initTable();
		this.loadTblData(customCode,phone);
		this.setModal(true);
		this.setResizable(false);
		this.setVisible(true);
	}

	public void loadTblData(String customCode,String phone) {
		CustomerCouponTableModel model = ((CustomerCouponTableModel) table.getModel());
		if(customerHessianRemoting == null){
			customerHessianRemoting = DefaultRemoteServiceFactory.getService(ICustomerHessianRemoting.class);
		}
		CustomerCouponEntity  entity = new CustomerCouponEntity();
		entity.setUsed(FossConstants.YES);
		entity.setCustomerCode(customCode);
		List<CustomerCouponEntity> list = customerHessianRemoting.queryCustomerCouponList(entity);
		customerCoupons.clear();
		model.setData(getArray(list));
		// 刷新表格数据
		model.fireTableDataChanged();
	}
	private  Object[][] getArray(List<CustomerCouponEntity> list) {
		if (list != null && !list.isEmpty()) {
			// 转换为二维数组
			Object[][] objtemp = new Object[list.size()][];
			Object[] q;
			for (int i = 0; i < list.size(); i++) {
				CustomerCouponEntity pending =  list.get(i);
				q = getRowValue(pending,i+1);
				for (int j = 0; j < objtemp.length; j++) {
					objtemp[i] = q;
				}
			}
			return objtemp;
		} else {
			return null;
		}
	}
	
	public Object[] getRowValue(CustomerCouponEntity cc,int num ) {
		// 获取对象成员保存至一维数组
		String activeDate =  sdf.format(cc.getActiveDate());
		customerCoupons.add(cc.getCouponCode());
		Object[] obj = {num,cc.getCouponCode(), cc.getAmount(),activeDate};
		return obj;
	}
	/**
	 * 
	 * 初始化表格
	 */
	private void initTable() {
		setLocationRelativeTo(null);
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(262dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.BUTTON_COLSPEC, },
				new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"), }));

		//初始化表格
		table = new JXTable(){
			private static final long serialVersionUID = 8987212521100316296L;
			public String getToolTipText(MouseEvent e) {
				int row=table.rowAtPoint(e.getPoint());
				int col=table.columnAtPoint(e.getPoint());
				String tiptextString=null;
				if(row>-1 && col>-1){
					Object value=table.getValueAt(row, col);
					if(null!=value && !"".equals(value))
						tiptextString=value.toString();//悬浮显示单元格内容
				}
				return tiptextString;
			}
		};
		//设置表格数据模型
		table.setModel(new CustomerCouponTableModel(null));
		//设置表格可以有滚动条
		table.setAutoscrolls(true);
		//禁止表格排序
		table.setSortable(false);
		//表格设置为可编辑
		//table.setEditable(true);
		//设置列可见状态
		table.setColumnControlVisible(true);
		table.setAutoResizeMode(JXTable.AUTO_RESIZE_OFF);
		table.setHorizontalScrollEnabled(true);
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, "2, 2, 3, 1, fill, default");
		scrollPane.setViewportView(table);
		
//		TableColumn column = table.getColumnModel().getColumn(0);
//		column.setCellRenderer(new TableRadioButtonCellRenderer());  
		/**
		 * 固定表格的宽度
		 */
		TableColumnModel tcm = table.getTableHeader().getColumnModel();
		for (int i = 0; i < tcm.getColumnCount(); i++) {
			TableColumn tc = tcm.getColumn(i);
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();
			render.setHorizontalAlignment(SwingConstants.CENTER);
			tc.setCellRenderer(render);
			if (i == NumberConstants.NUMBER_3) {
				tc.setPreferredWidth(NumberConstants.NUMBER_100);
			} else if (i == 2) {
				tc.setPreferredWidth(NumberConstants.NUMBER_70);
				tc.setMaxWidth(NumberConstants.NUMBER_70);
			}else if (i == 1) {
				tc.setPreferredWidth(NumberConstants.NUMBER_200);
			}else if (i == 0) {
				tc.setPreferredWidth(NumberConstants.NUMBER_30);
			}
		}
		// 居中显示
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, render);
		((DefaultTableCellRenderer) table.getTableHeader()
				.getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);
		addDListener();
	}
	
	private void addDListener() {
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {// 单击鼠标左键
					if (e.getClickCount() == 2) {
						int row = table.getSelectedRow();
						if(row == -1){
							return ;
						}
						Object obj = table.getValueAt(row,1);
						if(obj != null){
							String couponCode= obj.toString();
							LOG.info("客户优惠券:"+couponCode);
							JTextFieldValidate couponTxt =  waybillEditUI.getIncrementPanel().getTxtPromotionNumber();
							couponTxt.setText(couponCode);
							couponTxt.requestFocus();
							dispose();
						}
					
					}
				}
			}
		});
	}

}