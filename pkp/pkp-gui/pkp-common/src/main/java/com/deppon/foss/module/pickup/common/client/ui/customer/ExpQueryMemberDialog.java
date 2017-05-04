/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.ui.customer;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.pickup.common.client.service.ICustomerService;
import com.deppon.foss.module.pickup.common.client.service.IWaybillPriceExpressService;
import com.deppon.foss.module.pickup.common.client.service.impl.CustomerService;
import com.deppon.foss.module.pickup.common.client.service.impl.WaybillPriceExpressService;
import com.deppon.foss.module.pickup.common.client.ui.mergeTable.CombineData;
import com.deppon.foss.module.pickup.common.client.ui.mergeTable.CombineTable;
import com.deppon.foss.module.pickup.common.client.utils.ExpCommonUtils;
import com.deppon.foss.module.pickup.common.client.vo.QueryMemberDialogVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.google.inject.Injector;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * @author ibm-foss-sxw
 *
 */
public class ExpQueryMemberDialog extends JDialog {
	/**
	 * 定义日志静态类 
	 * 通过日志工厂类获得该类的日志对象
	 *  使用该日志类的静态方法记录日志
	 */
	protected final static Logger LOG = LoggerFactory.getLogger(QueryMemberDialog.class.getName());

	/**
	 * 国际化
	 */
	private II18n i18n = I18nManager.getI18n(QueryMemberDialog.class);
	
	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 6105513872122002267L;
	
	private static final int THREE = 3;

	private static final int FOUR = 4;

	private static final int FIVE = 5;

	private static final int SIX = 6;

	private static final int SEVEN = 7;

	private static final int EIGHT = 8;

	private static final int NINE = 9;

	private static final int TEN = 10;
	
	/**
	 * 身份证号码
	 */
	private JTextField txtIdCard;

	/**
	 * 联系人编码
	 */
	private JTextField txtLinkmanCode;

	/**
	 * 发货联系人
	 */
	private JTextField txtConsignor;

	// 电话
	private JTextField txtPhone;

	// 客户名称
	private JTextField txtCustomerName;

	// 手机
	private JTextField txtMobilePhone;

//	// 发货人地址
//	private JTextField txtAddress;

	// 越发越送审核编号
	private JTextField txtAuditNo;

	// 生效时间
	private JTextField txtValidTime;

	// 失效时间
	private JTextField txtInvalidTime;

	// 定义合并单元格的表格
	private CombineTable tblCombine;

	// 会员信息列表
	private List<Integer> combineColumns = new ArrayList<Integer>();

	// 客户服务接口
//	private ICustomerService customerService;

	
	private IWaybillPriceExpressService waybillPriceExpressService;

	
	// vo类
	public QueryMemberDialogVo memberVo;
	
	/**
	 * 客户编码
	 */
	private JTextField txtCustCode;

	/**
	 * 是否查询全公司
	 */
	private JCheckBox chkQueryAll;
	
	private JButton btnQuery;
	
	private WaybillPanelVo vo;

	/**
	 * 构造方法，用来调用初始化数据的方法
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-13 上午8:30:45
	 */
	public ExpQueryMemberDialog(WaybillPanelVo bean) {
		Injector injector = GuiceContextFactroy.getInjector();
//		customerService = injector.getInstance(CustomerService.class);
		waybillPriceExpressService= injector
				.getInstance(WaybillPriceExpressService.class);
		vo = bean;
		initTable();
		init();
		
		//监听Enter键
		EnterKeyForQueryConsignee enterCustomerName=new EnterKeyForQueryConsignee(btnQuery);		
		txtCustomerName.addKeyListener(enterCustomerName);
		EnterKeyForQueryConsignee enterCustCode=new EnterKeyForQueryConsignee(btnQuery);		
		txtCustCode.addKeyListener(enterCustCode);
		EnterKeyForQueryConsignee enterMobilePhone=new EnterKeyForQueryConsignee(btnQuery);		
		txtMobilePhone.addKeyListener(enterMobilePhone);
		EnterKeyForQueryConsignee enterLinkmanCode=new EnterKeyForQueryConsignee(btnQuery);		
		txtLinkmanCode.addKeyListener(enterLinkmanCode);
		EnterKeyForQueryConsignee enterIdCard=new EnterKeyForQueryConsignee(btnQuery);		
		txtIdCard.addKeyListener(enterIdCard);
		EnterKeyForQueryConsignee enterPhone=new EnterKeyForQueryConsignee(btnQuery);		
		txtPhone.addKeyListener(enterPhone);
		EnterKeyForQueryConsignee enterConsignor=new EnterKeyForQueryConsignee(btnQuery);		
		txtConsignor.addKeyListener(enterConsignor);
		EnterKeyForQueryConsignee enterQueryAll=new EnterKeyForQueryConsignee(btnQuery);		
		chkQueryAll.addKeyListener(enterQueryAll);
		ExpEnterKeyForQueryConsignee enterCombine=new ExpEnterKeyForQueryConsignee(this);		
		tblCombine.addKeyListener(enterCombine);		
	}

	/**
	 * 初始化表格
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-13 上午9:35:05
	 */
	public void initTable() {
		// 默认表格模式
		DefaultTableModel tblModel = new CustomerDetailTableModel();
		// 创建表格
		tblCombine = new CombineTable(tblModel);
		// 设置表格可编辑状态
		tblCombine.setEditable(false);
		// 设置滚动条
		tblCombine.setAutoscrolls(true);
		// 设置手动调整滚动条
		tblCombine.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// 设置水平滚动
		tblCombine.setHorizontalScrollEnabled(true);
		// 设置
		tblCombine.setColumnControlVisible(true);
		// 设置表格排序
		tblCombine.setSortable(false);
		// 设置单行选择模式
		tblCombine.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// 监听处理事件
		tblCombine.addMouseListener(new ClickTableHandler());
		
		// 设置表格的合并列
		combineColumns.add(0);
		combineColumns.add(1);
		combineColumns.add(2);
		tblCombine.setCombineData(new CombineData(null, combineColumns));
		

	}

	/**
	 * 获得表格选择模式
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-23 上午11:25:53
	 */
	private class SelectTableHandler implements ListSelectionListener {
		// 给表格选择模式增加行选择监听事件
		@Override
		public void valueChanged(ListSelectionEvent e) {
			// 返回此事件是否是仍然在更改的多个不同事件之一
			if (e.getValueIsAdjusting()) {
				// 忽略其它消息
				return;
			}

			ListSelectionModel lsm = (ListSelectionModel) e.getSource();
			if (lsm.isSelectionEmpty()) {
				// 不做操作
			} else {
				int selectRow = lsm.getMinSelectionIndex();
				CustomerDetailTableModel model = (CustomerDetailTableModel) tblCombine.getModel();
				List<QueryMemberDialogVo> data = model.getData();
				if (CollectionUtils.isNotEmpty(data)) {
					QueryMemberDialogVo vo = data.get(selectRow);
					// 审核编码
					txtAuditNo.setText(StringUtil.defaultIfNull(vo.getAuditNo()));
					
					// 格式化日期对象
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH：mm：ss");
					// 生效日期
					if(vo.getValidTime() != null){
						txtValidTime.setText(sdf.format(vo.getValidTime()));
					}
					
					// 失效日期
					if(vo.getInvalidTime() != null){
						txtInvalidTime.setText(sdf.format(vo.getInvalidTime()));
					}
				}
			}
		}
	}

	/**
	 * 获取查询条件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-29 上午10:35:13
	 */
	private CustomerQueryConditionDto gainQueryCondition() {
		//定义查询条件对象 
		CustomerQueryConditionDto condition = new CustomerQueryConditionDto();
		//定义字符串对象
		StringBuffer sb = new StringBuffer();
		// 客户编码
		StringBuffer custCode = new StringBuffer(txtCustCode.getText().trim());
		// 联系人编码
		StringBuffer linkmanCode = new StringBuffer(txtLinkmanCode.getText().trim());
		// 身份证号码
		StringBuffer idCard = new StringBuffer(txtIdCard.getText().trim());
		// 手机号码
		StringBuffer mobile = new StringBuffer(txtMobilePhone.getText().trim());
		// 电话号码
		StringBuffer phone = new StringBuffer(txtPhone.getText().trim());
		// 客户名称
		StringBuffer custName = new StringBuffer(txtCustomerName.getText().trim());
		// 联系人名称
		StringBuffer linkmanName = new StringBuffer(txtConsignor.getText().trim());
		
		//客户编码、联系人编码、电话、手机、身份证号为精确查询，并忽略模糊查询条件
		String str = sb.append(custCode).append(linkmanCode).append(idCard).append(mobile).append(phone).toString();
		//判断精确查询条件是否为空
		if(StringUtil.isNotEmpty(str)){
			// 客户编码
			condition.setCustCode(custCode.toString());
			// 联系人编码
			condition.setLinkmanCode(linkmanCode.toString());
			// 身份证号码
			condition.setIdCard(idCard.toString());
			// 手机号码
			condition.setMobilePhone(mobile.toString());
			// 电话号码
			condition.setContactPhone(phone.toString());
			// 精确查询
			condition.setExactQuery(true);
		}
		// 客户名称和联系人名称为模糊查询
		else{
			// 客户名称 
			condition.setCustName(custName.toString());
			// 联系人名称
			condition.setContactName(linkmanName.toString());
			// 模糊查询
			condition.setExactQuery(false);
			
			// 判断是否查询全公司
			if (!chkQueryAll.isSelected()) {
				// 设置查询条件，查询本部门(出发部门，非制单部门)
				condition.setDeptCode(vo.getReceiveOrgCode());
			}
		}
		return condition;
	}

	/**
	 * 一般内部类：“查询”按钮处理事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-23 上午11:49:00
	 */
	private class QueryHandler implements ActionListener {

		/**
		 * 点击查询按钮事件
		 * 
		 * @author 026123-foss-lifengteng
		 * @date 2012-12-25 上午10:44:35
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
    			//检验查询条件是否正确
    			validateData();
    			// 查询客户综合信息
    			List<CustomerQueryConditionDto> dtoList = 
    					//customerService
    					waybillPriceExpressService.queryCustomerByCondition(gainQueryCondition());
    			// 往表格中设值
    			if (CollectionUtils.isNotEmpty(dtoList)) {
    				List<QueryMemberDialogVo> list = ExpCommonUtils.convertToMemberVo(dtoList);
    				setTableData(list);
    				
    				if(dtoList.size()>WaybillConstants.CUSTOMER_COUNT){
    					MsgBox.showError(i18n.get("foss.gui.common.queryMemberDialog.tooMuch")+WaybillConstants.CUSTOMER_COUNT 
    							+i18n.get("foss.gui.common.queryMemberDialog.onlyAlow")+WaybillConstants.CUSTOMER_COUNT );
    				}
    			} else {
    				MsgBox.showError(i18n.get("foss.gui.common.queryMemberDialog.nullCustomerInfo"));
    			}
    			//获取光标
				if(tblCombine!=null && tblCombine.getRowCount()>0){
					tblCombine.requestFocus();
					tblCombine.setRowSelectionAllowed(true);
					tblCombine.setRowSelectionInterval(0,0);
				}
    		}catch (BusinessException ee) {
    			MsgBox.showError(i18n.get("foss.gui.common.queryMemberDialog.msgBox.errorQuery")+ ee.getMessage());
			}
		}
	}
	
	/**
	 * 判断查询条件是否正确
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-20 下午3:01:00
	 */
	private void validateData() {
		// 身份证号码
		StringBuffer idCard = new StringBuffer(txtIdCard.getText().trim());
		// 联系人编码
		StringBuffer linkmanCode = new StringBuffer(txtLinkmanCode.getText().trim());
		// 联系人
		StringBuffer consignor = new StringBuffer(txtConsignor.getText().trim());
		// 电话
		StringBuffer phone = new StringBuffer(txtPhone.getText().trim());
		// 客户名称
		StringBuffer customerName = new StringBuffer(txtCustomerName.getText().trim());
		// 手机
		StringBuffer mobilePhone = new StringBuffer(txtMobilePhone.getText().trim());
//		// 发货人地址
//		StringBuffer address = new StringBuffer(txtAddress.getText().trim());
		// 客户编码
		StringBuffer custCode = new StringBuffer(txtCustCode.getText().trim());
		
		String text = idCard.append(linkmanCode).append(consignor).append(phone).append(customerName).append(mobilePhone).append(custCode).toString();
		if("".equals(text)){
			throw new WaybillValidateException(i18n.get("foss.gui.common.queryMemberDialog.exception.nullQueryCondition"));
		}
	}

	/**
	 * 扩展表格数据模型
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-26 下午7:42:45
	 */
	private class CustomerDetailTableModel extends DefaultTableModel {

		/**
		 * 序列化标识
		 */
		private static final long serialVersionUID = 9071126508541610144L;

		// 联系人列表
		private List<QueryMemberDialogVo> contactList;

		/**
		 * 获得数据集合
		 * 
		 * @author 026123-foss-lifengteng
		 * @date 2012-11-26 下午7:49:32
		 */
		public List<QueryMemberDialogVo> getData() {
			return contactList;
		}

		/**
		 * 填充数据集合
		 * 
		 * @author 026123-foss-lifengteng
		 * @date 2012-11-26 下午7:49:14
		 */
		public void setData(List<QueryMemberDialogVo> contactList) {
			this.contactList = contactList;
		}

		/**
		 * 获得行数
		 * 
		 * @author 026123-foss-lifengteng
		 * @date 2012-11-26 下午7:47:00
		 * @see javax.swing.table.DefaultTableModel#getRowCount()
		 */
		@Override
		public int getRowCount() {
			return contactList == null ? 0 : contactList.size();
		}

		/**
		 * 重写方法：获得表格行数
		 * 
		 * @author 026123-foss-lifengteng
		 * @date 2012-11-26 下午7:46:34
		 * @see javax.swing.table.DefaultTableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return TEN;
		}

		/**
		 * 重写方法：获得列名
		 * 
		 * @author 026123-foss-lifengteng
		 * @date 2012-11-26 下午7:46:18
		 * @see javax.swing.table.DefaultTableModel#getColumnName(int)
		 */
		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return i18n.get("foss.gui.common.queryMemberDialog.columnName.customerCode");
			case 1:
				return i18n.get("foss.gui.common.queryMemberDialog.columnName.customerName");
			case 2:
				return i18n.get("foss.gui.common.queryMemberDialog.columnName.deptName");
			case THREE:
				return i18n.get("foss.gui.common.queryMemberDialog.columnName.monthlyAudit");
			case FOUR:
				return i18n.get("foss.gui.common.queryMemberDialog.columnName.contactCode");
			case FIVE:
				return i18n.get("foss.gui.common.queryMemberDialog.columnName.contact");
			default:
				return getColumnNameExp(column);
			}
		}
		private String getColumnNameExp(int column) {
			switch (column) {
			case SIX:
				return i18n.get("foss.gui.common.queryMemberDialog.columnName.phone");
			case SEVEN:
				return i18n.get("foss.gui.common.queryMemberDialog.columnName.tel");
			case EIGHT:
				return i18n.get("foss.gui.common.queryMemberDialog.columnName.identityId");
			case NINE:
				return i18n.get("foss.gui.common.queryMemberDialog.columnName.creditLimit");
			case TEN:
				return i18n.get("foss.gui.common.queryMemberDialog.columnName.address");
			default:
				return "";
			}
		}

		/**
		 * 重写model方法，获得行数据对象
		 * 
		 * @author 026123-foss-lifengteng
		 * @date 2012-11-26 下午7:45:44
		 * @see javax.swing.table.DefaultTableModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
			case 0:
				// 客户编码
				return contactList.get(row).getCustomerCode();
			case 1:
				// 客户名称
				return contactList.get(row).getCustomerName();
			case 2:
				// 所属部门
				return contactList.get(row).getDeptName();
			case THREE:
				// 月结审核
				return contactList.get(row).getChargeMode();
			case FOUR:
				// 联系人编码
				return contactList.get(row).getConsignorCode();
			case FIVE:
				// 联系人
				return contactList.get(row).getLinkman();
			default:
				return getValueAtExp(row, column);
			}
		}
		private Object getValueAtExp(int row, int column) {
			switch (column) {
			case SIX:
				// 手机
				return contactList.get(row).getMobilePhone();
			case SEVEN:
				// 电话
				return contactList.get(row).getPhone();
			case EIGHT:
				// 身份证
				return contactList.get(row).getIdentityCard();
			case NINE:
				// 信用额度
				return contactList.get(row).getCreditAmount();
			case TEN:
				// 地址
				return contactList.get(row).getAddress();
			default:
				return super.getValueAt(row, column);
			}
		}
	}

	/**
	 * 设置表格数据
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-13 上午10:14:33
	 */
	public void setTableData(List<QueryMemberDialogVo> contactList) {
		if (contactList == null || contactList.size() == 0) {
			return;
		}
		// 获得表格模型
		CustomerDetailTableModel tableModel = (CustomerDetailTableModel) tblCombine.getModel();
		tableModel.setData(contactList);
		// 刷新表格数据
		tableModel.fireTableDataChanged();
	}

	/**
	 * 一般内部类：“重置”按钮处理事件，设置查询条件为空
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-23 上午11:41:36
	 */
	private class ResetHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// 客户编码
			txtCustCode.setText("");
			// 客户名称
			txtCustomerName.setText("");
			// 联系人编码
			txtLinkmanCode.setText("");
			// 联系人名称
			txtConsignor.setText("");
			// 手机
			txtMobilePhone.setText("");
			// 电话
			txtPhone.setText("");
			// 身份证号码
			txtIdCard.setText("");
//			// 地址
//			txtAddress.setText("");
		}
	}

	/**
	 * 定义一个一般内部类：设置dialog监听esc按键的处理事件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-25 上午10:21:54
	 */
	private class EscHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
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
				obtainCustomer();
				// 关闭界面，释放资源
				dispose();
				// 单击
			} else{
				// 无操作
			}
		}

	}
	
	/**
	 * 一般内部类：表格的单、双击处理事件
	 * 
	 * @author WangQianJin
	 * @date 2013-05-16
	 */
	public void tableEnter() {	
		obtainCustomer();
		// 关闭界面，释放资源
		dispose();
	}

	/**
	 * 获得选中行记录对象
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-29 上午11:10:09
	 */
	private void obtainCustomer() {
		// 获得选中行
		int row = tblCombine.getSelectedRow();
		if (row < 0) {
			//将异常信息抛出到前台
			throw new WaybillValidateException(i18n.get("foss.gui.common.queryConsigneeDialog.msgBox.nullSelectedRow"));
		} else {
			memberVo = new QueryMemberDialogVo();
			CustomerDetailTableModel model = (CustomerDetailTableModel) tblCombine.getModel();
			List<QueryMemberDialogVo> data = model.getData();
			memberVo = data.get(row);
			// 关闭界面，释放资源
			dispose();
		}
	}

	/**
	 * 初始化界面控件
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-20 下午5:17:00
	 */
	public void init() {
		JPanel panel = new JPanel();
		panel.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("9px"), ColumnSpec.decode("35dlu:grow"), ColumnSpec.decode("4dlu:grow"),
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(60dlu;default):grow"),
				ColumnSpec.decode("20dlu:grow"), FormFactory.DEFAULT_COLSPEC, ColumnSpec.decode("8dlu"), ColumnSpec.decode("31dlu:grow"),
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(60dlu;default):grow"), ColumnSpec.decode("20dlu:grow"),
				FormFactory.DEFAULT_COLSPEC, ColumnSpec.decode("8dlu"), ColumnSpec.decode("15dlu:grow"), FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("60dlu:grow"), ColumnSpec.decode("9dlu:grow"), FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC, RowSpec.decode("15px"), FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("fill:100dlu"),
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(85dlu;default)"), }));

		// 查询客户信息
		JLabel lblTitle = new JLabel(i18n.get("foss.gui.common.queryMemberDialog.queryCustomerInfo.label"));
		lblTitle.setFont(new Font(i18n.get("foss.gui.common.QueryMemberDialog.font.songti"), Font.BOLD, NumberConstants.NUMBER_12));
		panel.add(lblTitle, "2, 2, 5, 1");

		// 客户名称
		JLabel lblCustomerName = new JLabel(i18n.get("foss.gui.common.queryMemberDialog.customerName.label"));
		panel.add(lblCustomerName, "2, 4, 3, 1, left, default");

		// 客户名称
		txtCustomerName = new JTextField();
		panel.add(txtCustomerName, "6, 4, fill, default");
		txtCustomerName.setColumns(TEN);

		// 客户编码
		JLabel lblCustomerCode = new JLabel(i18n.get("foss.gui.common.queryMemberDialog.customerCode.label"));
		panel.add(lblCustomerCode, "8, 4, 3, 1");

		// 客户编码
		txtCustCode = new JTextField();
		panel.add(txtCustCode, "12, 4, fill, default");
		txtCustCode.setColumns(TEN);

		// 手机号码
		JLabel lblMobilePhone = new JLabel(i18n.get("foss.gui.common.queryMemberDialog.phone.label"));
		panel.add(lblMobilePhone, "14, 4, 3, 1, left, default");

		// 手机号码
		txtMobilePhone = new JTextField();
		panel.add(txtMobilePhone, "18, 4, fill, default");
		txtMobilePhone.setColumns(TEN);

		// 联系人编码
		JLabel lblLinkmanCode = new JLabel(i18n.get("foss.gui.common.queryMemberDialog.contactCode.label"));
		panel.add(lblLinkmanCode, "2, 6, 3, 1, left, default");

		// 联系人编码
		txtLinkmanCode = new JTextField();
		panel.add(txtLinkmanCode, "6, 6, fill, default");
		txtLinkmanCode.setColumns(TEN);

		// 身份证号码
		JLabel lblIdCard = new JLabel(i18n.get("foss.gui.common.queryMemberDialog.identityId.label"));
		panel.add(lblIdCard, "8, 6, 3, 1");

		// 身份证号码
		txtIdCard = new JTextField();
		panel.add(txtIdCard, "12, 6, fill, default");
		txtIdCard.setColumns(TEN);

		// 电话
		JLabel lblPhone = new JLabel(i18n.get("foss.gui.common.queryMemberDialog.tel.label"));
		panel.add(lblPhone, "14, 6, 3, 1, left, default");

		// 电话
		txtPhone = new JTextField();
		panel.add(txtPhone, "18, 6, fill, default");
		txtPhone.setColumns(TEN);

		// 联系人名称
		JLabel lblConsignor = new JLabel(i18n.get("foss.gui.common.queryMemberDialog.contactName.label"));
		panel.add(lblConsignor, "2, 8, 3, 1, left, default");

		// 联系人名称
		txtConsignor = new JTextField();
		panel.add(txtConsignor, "6, 8, fill, default");
		txtConsignor.setColumns(TEN);

		JButton btnReset = new JButton(i18n.get("foss.gui.common.queryMemberDialog.reset.label"));
		// 添加事件监听
		btnReset.addActionListener(new ResetHandler());
		panel.add(btnReset, "2, 10, 5, 1, left, default");

//		// 发货人地址
//		JLabel lblAddress = new JLabel("客户地址：");
//		panel.add(lblAddress, "8, 8, 3, 1, left, default");
//		txtAddress = new JTextField();
//		panel.add(txtAddress, "12, 8, 7, 1, fill, default");
//		txtAddress.setColumns(10);

		btnQuery = new JButton(i18n.get("foss.gui.common.waybillEditUI.common.query"));
		panel.add(btnQuery, "18, 10, right, default");
		// 添加事件监听
		btnQuery.addActionListener(new QueryHandler());

		// 是否查询全公司
		chkQueryAll = new JCheckBox(i18n.get("foss.gui.common.queryMemberDialog.queryFullCompany.checkBox"));
		panel.add(chkQueryAll, "12, 10, 6, 1, right, default");

		// 月发越送审核编号
		JLabel lblAuditNo = new JLabel(i18n.get("foss.gui.common.queryMemberDialog.sendMonthlyAuditNo.label"));
		panel.add(lblAuditNo, "2, 16, 2, 1, left, default");

		txtAuditNo = new JTextField();
		txtAuditNo.setEditable(false);
		panel.add(txtAuditNo, "4, 16, 3, 1, right, default");
		txtAuditNo.setColumns(TEN);

		JLabel lblValidTime = new JLabel(i18n.get("foss.gui.common.queryMemberDialog.effectiveTime.label"));
		panel.add(lblValidTime, "7, 16, 3, 1, right, default");

		txtValidTime = new JTextField();
		txtValidTime.setEditable(false);
		panel.add(txtValidTime, "10, 16, 3, 1, fill, default");
		txtValidTime.setColumns(TEN);

		JLabel lblInvalidTime = new JLabel(i18n.get("foss.gui.common.queryMemberDialog.failureTime.label"));
		panel.add(lblInvalidTime, "13, 16, 3, 1, right, default");

		txtInvalidTime = new JTextField();
		txtInvalidTime.setEditable(false);
		panel.add(txtInvalidTime, "16, 16, 3, 1, fill, default");
		txtInvalidTime.setColumns(TEN);

		JScrollPane scrollPane = new JScrollPane(tblCombine);
		panel.add(scrollPane, "2, 12, 17, 1, default, fill");

		tblCombine.addMouseListener(new ClickTableHandler());
		ListSelectionModel rowSM = tblCombine.getSelectionModel();
		rowSM.addListSelectionListener(new SelectTableHandler());
		
		// Dialog监听ESC事件
		panel.registerKeyboardAction(new EscHandler(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		getContentPane().add(panel);
		
		// 设置为模态（当弹出窗口打开时，父窗口不能编辑）
		setModal(true);
		// 让窗口弹出时根据实际需要进行显示
		pack();
	}
	
	/**
	 * @return the memberVo .
	 */
	public QueryMemberDialogVo getMemberVo() {
		return memberVo;
	}

	/**
	 * @param memberVo
	 *            the memberVo to set.
	 */
	public void setMemberVo(QueryMemberDialogVo memberVo) {
		this.memberVo = memberVo;
	}
}
