package com.deppon.foss.module.pickup.common.client.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java.plugin.Plugin;
import org.jdesktop.swingx.JXTable;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.factory.ButtonActionFactory;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.component.sync.ISyncDataSaver;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.entity.SyncDataRequest;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.service.ISyncDataRemoting;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.common.client.service.IDataConsistencyCheckService;
import com.deppon.foss.module.pickup.common.client.service.impl.DataConsistencyCheckService;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.LinkedButtonColumn;
import com.deppon.foss.module.pickup.common.client.ui.commonUI.TableFactory;
import com.deppon.foss.module.pickup.common.client.utils.MessageI18nUtil;
import com.deppon.foss.module.pickup.common.client.utils.SaverUtils;
import com.deppon.foss.module.pickup.common.client.vo.DataConsistencyCheckVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.DataConsistencyVo;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;



public class DataConsistencyCheckUI extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 133456457561L;

	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager
			.getI18n(DataConsistencyCheckUI.class);
	//log
	private static final Log LOG = LogFactory.getLog(DataConsistencyCheckUI.class);

	private static final int TEN = 10;

	private static final int NUM_440 = 440;
	
	private static final int THREE = 3;

	private static final int FOUR = 4;

	private static final int FIVE = 5;

	private static final int SIX = 6;

	private static final int SEVEN = 7;

	private static final int EIGHT = 8;

	private static final int NINE = 9;
	
	/**
	 * 用户名
	 */
	private JTextField userNameText;
	/**
	 * 部门
	 */
	private JTextField departmentText;
	/**
	 * 检查所选表
	 */
	private JButton checkSelectedTable;
	/**
	 * 全量更新
	 */
	private JButton updateAllTable;
	/**
	 * 增量更新
	 */
	private JButton addSelectedTable;
	/**
	 * 数据表格
	 */
	private JXTable table;
	
	/**
	 * 全选
	 */
	private JCheckBox allSelectCheckBox;
	
	/**
	 * 用户ID
	 */
	private String userCode;
	/**
	 * 部门ID
	 */
	private String userOrgcode;

	/**
	 * 检查
	 */
	private LinkedButtonColumn checkColum;
	/**
	 * 全量
	 */
	private LinkedButtonColumn allColum;
	
	/**
	 * 增量
	 */
//	private LinkedButtonColumn addColum;
	/**
	 * 插件
	 */
	private Plugin plugin;
	
	/**
	 * 数据下载请求
	 */
	private List<SyncDataRequest> requests=new ArrayList<SyncDataRequest>();
	/**
	 * 选中的checkbox
	 */
	private List<DataConsistencyCheckVo> selectedDatas=new ArrayList<DataConsistencyCheckVo>();
	
	private IDataConsistencyCheckService dataConsistencyCheckService= GuiceContextFactroy.getInjector().getInstance(DataConsistencyCheckService.class);

	/**
	 * 
	 * DataConsistencyCheckUI
	 * 
	 * @author 102246-foss-dengyao
	 * @date 2013-04-18 上午10:30:02
	 */
	public DataConsistencyCheckUI() {
		init();
		initTable();
		addListener();
		bind();
	}
	/**
	 * 
	 * 页面绑定
	 * 
	 * @author 102246-foss-dengyao
	 * @date 2013-04-18 上午10:30:02
	 */
	private void bind() {
		ButtonActionFactory.getIntsance().bindButtonAction(this);
	}
	/**
	 * Create the panel.
	 */
	private void init() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(475dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(22dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(203dlu;default):grow"),
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("max(32dlu;default)"),
				RowSpec.decode("6dlu"),}));
		
		JPanel panel1 = new JPanel();
		add(panel1, "2, 2, fill, fill");
		FormLayout flpanel1 = new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(50dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(39dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(57dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(23dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(45dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(122dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(97dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,});
		panel1.setLayout(flpanel1);
		
		JLabel lblNewLabel = new JLabel("用户名：");
		panel1.add(lblNewLabel, "4, 2, right, default");
		
		userNameText = new JTextField();
		userNameText.setEnabled(false);
		panel1.add(userNameText, "6, 2, fill, center");
		userNameText.setColumns(TEN);
		
		JLabel lblNewLabel1 = new JLabel("所属部门：");
		panel1.add(lblNewLabel1, "10, 2, right, center");
		
		departmentText = new JTextField();
		departmentText.setEnabled(false);
		panel1.add(departmentText, "12, 2, fill, center");
		departmentText.setColumns(TEN);
		
		JPanel panel = new JPanel();
		panel.setBorder(new CompoundBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "\u540C\u6B65\u6570\u636E\u5217\u8868", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), new EmptyBorder(1, 1, 2, 1)));
		
		add(panel, "2, 4, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		allSelectCheckBox = new JCheckBox("全选");
		panel.add(allSelectCheckBox, "2, 2");
		AllListener allListener = new AllListener();
		allSelectCheckBox.addItemListener(allListener);
				
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, "2, 4, fill, fill");
		
		table = new JXTable(new DataCheckVoModel());
		table.setHorizontalScrollEnabled(true);
		
		scrollPane.setViewportView(table);
		table.setAutoscrolls(true);
		table.setColumnControlVisible(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		
		UserEntity user = (UserEntity) SessionContext.getCurrentUser();
		//String userOrgCode=user.getEmployee().getDepartment().getCode();
		//List<String> userOrgCodes = new ArrayList<String> ();
		//userOrgCodes.add(userOrgCode);		
		
		userCode=user.getEmployee().getEmpCode();
		userOrgcode=user.getEmployee().getDepartment().getCode();
		String orgCurrentName = user.getEmployee().getDepartment().getName();

		userNameText.setText(userCode);
		departmentText.setText(orgCurrentName);
		
		JPanel panel2 = new JPanel();
		add(panel2, "2, 6, fill, fill");
		panel2.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(58dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(49dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(47dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(74dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(76dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(24dlu;default)"),
				RowSpec.decode("max(5dlu;default)"),}));
		
		checkSelectedTable = new JButton("检查所选表");
		panel2.add(checkSelectedTable, "4, 2, fill, fill");
		
		updateAllTable = new JButton("全量更新所选表");
		panel2.add(updateAllTable, "8, 2, fill, fill");
		
		addSelectedTable = new JButton("增量更新所选表");
		panel2.add(addSelectedTable, "12, 2, fill, fill");

		// 添加Button样式
		checkColum = TableFactory.createLinkedButtonColumn(table, NumberConstants.NUMBER_8, NumberConstants.NUMBER_80);
		/*	allColum=TableFactory.createLinkedButtonColumn(table, 8, 80);
			addColum=TableFactory.createLinkedButtonColumn(table, 8, 80);*/
		TableFactory.createRowColumn(table);
		scrollPane.setViewportView(table);	
		
		/*List<DataConsistencyCheckVo> dataConsistencyCheck = dataConsistencyCheckService.parseLocalDataSaver(userOrgCodes, true);
	
		DataCheckVoModel tableModel =(DataCheckVoModel)table.getModel();
		tableModel.setRowCount(0);// 清除原有行
		tableModel.setData(dataConsistencyCheck);
		tableModel.fireTableDataChanged();*/
		//LinkTableMode tableModel = new LinkTableMode(getArray(dataList, 2));
		//table.setModel(tableModel);
	}
	private void initTable() {
			
		List<DataConsistencyCheckVo> dataConsistencyCheck = dataConsistencyCheckService.querylocalTableDate();
		DataCheckVoModel tableModel =(DataCheckVoModel)table.getModel();
		tableModel.setRowCount(0);// 清除原有行
		tableModel.setData(dataConsistencyCheck);
		/**
		 * 固定表格的宽度
		 */
		TableColumnModel tcm = table.getTableHeader().getColumnModel();
		for (int i = 0; i < tcm.getColumnCount(); i++) {
			 TableColumn tc = tcm.getColumn(i);
			switch (i) {
			case 0:					  
			case 1:					  
			   tc.setPreferredWidth(NumberConstants.NUMBER_50);
			   tc.setMaxWidth(NumberConstants.NUMBER_50);
			   break;
			case 2:
				tc.setPreferredWidth(NUM_440);
				tc.setMaxWidth(NumberConstants.NUMBER_800); 
				break;
			case THREE:
				tc.setPreferredWidth(NumberConstants.NUMBER_250);
				tc.setMaxWidth(NumberConstants.NUMBER_500); 
				break;
			case FIVE:
			case SIX:
				tc.setPreferredWidth(NumberConstants.NUMBER_150);
				tc.setMaxWidth(NumberConstants.NUMBER_500); 
				break;
			case SEVEN:
			case EIGHT:
				tc.setPreferredWidth(NumberConstants.NUMBER_70);
				tc.setMaxWidth(NumberConstants.NUMBER_500); 
				break;
			default:
				tc.setPreferredWidth(NumberConstants.NUMBER_100);
				tc.setMaxWidth(NumberConstants.NUMBER_250); 
				break;
			}
		}
		tableModel.fireTableDataChanged();
		
		
		
		table.setModel(tableModel);
		table.repaint();
		//table.setSortable(true);
	}
	
	
	private void addListener() {
		
		ButtonActionFactory.getIntsance().bindButtonAction(this);
		
		checkColum.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DataConsistencyCheckVo rowVo = getSelectedVo();
					List<DataConsistencyCheckVo> selectedRows=new ArrayList<DataConsistencyCheckVo>();
					selectedRows.add(rowVo);
					List<DataConsistencyVo> selectedData=convertServiceDate(selectedRows);
					selectedData=dataConsistencyCheckService.countServiceTableDate(selectedData,userCode);
					selectedRows.get(0).setCounts(selectedData.get(0).getCounts());
					selectedRows.get(0).setSyncDate(selectedData.get(0).getSyncDate());
					
					setSelectedVo(selectedRows);
					table.repaint();
					

				} catch (BusinessException e1) {
					MsgBox.showError(MessageI18nUtil.getMessage(e1, i18n));
				}
			}
		});
		
		checkSelectedTable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					 List<DataConsistencyVo> selectedData=convertServiceDate(selectedDatas);
					 selectedData=dataConsistencyCheckService.countServiceTableDate(selectedData,userCode);
					 List<DataConsistencyCheckVo> selectedRows=selectedDatas;
					 int i=0;
					 for(DataConsistencyVo data:selectedData){
						 selectedRows.get(i).setCounts(data.getCounts());
						 selectedRows.get(i).setSyncDate(data.getSyncDate());
						 i++;
					 }
					 setSelectedVo(selectedRows);
					 table.repaint();
					 
				}catch (BusinessException e1) {
					MsgBox.showError(MessageI18nUtil.getMessage(e1, i18n));
				}
			}
		});
		
		addSelectedTable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i=JOptionPane.showConfirmDialog(null, "确定进行增量更新么？", "更新确认", JOptionPane.YES_NO_OPTION);
				if(i==JOptionPane.YES_OPTION){
				try {
					 for (DataConsistencyCheckVo download:selectedDatas){
						 SyncDataRequest request=new SyncDataRequest();
						 request.setFromDate(download.getLocalSyncDate());
						 request.setOrgCode(userOrgcode);
						 request.setRegionID(download.getRegionID());
						 try {
							    request.setSyncKey(Class.forName(download.getEntityClsName()));
								requests.add(request);
								//hasMadeRequest = true;
							} catch (ClassNotFoundException exception) {
								LOG.error("parseSyncDataConfigs exception", exception);
							}
						
					 }
					 ISyncDataRemoting service= DefaultRemoteServiceFactory.getService(ISyncDataRemoting.class);
					 
					 Map<Class<?>, ISyncDataSaver> syncDataSaver =SaverUtils.getDataSaver(plugin);
					 SaverUtils.syncData(service, syncDataSaver, requests);
					 
					 List<DataConsistencyCheckVo> updateCount=dataConsistencyCheckService.countLocalTableCounts(selectedDatas);
					 setSelectedVo(updateCount);
					 MsgBox.showInfo("更新成功！");
					 table.repaint();
					 
				}catch (BusinessException e1) {
					MsgBox.showError(MessageI18nUtil.getMessage(e1, i18n));
				}
				}
			}
		});
		
		updateAllTable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i=JOptionPane.showConfirmDialog(null, "确定进行全量更新么？\n若选择表较多则更新时间较长，请耐心等待", "更新确认", JOptionPane.YES_NO_OPTION);
				if(i==JOptionPane.YES_OPTION){
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String ndate="1970-01-01 08:00:00";
					Date date=new Date();
					try {
						date = sdf.parse(ndate);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					 for (DataConsistencyCheckVo download:selectedDatas){
						 SyncDataRequest request=new SyncDataRequest();
						 request.setFromDate(date);
						 request.setOrgCode(userOrgcode);
						 request.setRegionID(download.getRegionID());
						 try {
							    request.setSyncKey(Class.forName(download.getEntityClsName()));
								requests.add(request);
							} catch (ClassNotFoundException exception) {
								LOG.error("parseSyncDataConfigs exception", exception);
							}
						
					 }
					 ISyncDataRemoting service= DefaultRemoteServiceFactory.getService(ISyncDataRemoting.class);
					 
					 Map<Class<?>, ISyncDataSaver> syncDataSaver =SaverUtils.getDataSaver(plugin);
					 SaverUtils.syncData(service, syncDataSaver, requests);
					 
					 List<DataConsistencyCheckVo> updateCount=dataConsistencyCheckService.countLocalTableCounts(selectedDatas);
					 setSelectedVo(updateCount);
					 MsgBox.showInfo("更新成功！");
					 table.repaint();
					 
				}catch (BusinessException e1) {
					MsgBox.showError(MessageI18nUtil.getMessage(e1, i18n));
				}
		    	}
			}
		});
		
	}
	
	
	/**
	 * DataConsistencyVo到DataConsistencyCheckVo转换
	 * 
	 * @author foss-dengyao
	 * @date 2013-04-20 上午9:32:15
	 * @param selectedRowNo
	 * @see
	 */
	private List<DataConsistencyVo> convertServiceDate(List<DataConsistencyCheckVo> rowVos){
		List<DataConsistencyVo> tableDates = new ArrayList<DataConsistencyVo>();
		for(DataConsistencyCheckVo data:rowVos){
			DataConsistencyVo tableDate=new DataConsistencyVo();
			tableDate.setEntityClsName(data.getEntityClsName());
			tableDate.setRegionID(data.getRegionID());
			tableDate.setTabelName(data.getTabelName());
			tableDates.add(tableDate);
		}
		return tableDates;
	}
	
	/**
	 * 刷新 dataConsistencyCheckUI的表格
	 * 
	 * @author foss-dengyao
	 * @date 2013-04-20 上午9:32:15
	 * @param selectedRowNo
	 * @see
	 */
	public void dataConsistencyCheckUITableRefresh(int selectedRowNo) {
		checkSelectedTable.doClick();
	}	
	
	/**
	 * 
	 * 获取多行表格数据
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午10:31:56
	 */
	public List<DataConsistencyCheckVo> getTableData() {
		DataCheckVoModel dataCheckVoModel = ((DataCheckVoModel) table
				.getModel());
		return dataCheckVoModel.getInitDataList();
	}
	
	/**
	 * 
	 * 获取当前选中行对象
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2013-04-25 上午10:31:38
	 */
	protected DataConsistencyCheckVo getSelectedVo() {
		int row = table.getSelectedRow();
		return getTableData().get(row);
	}
	
	/**
	 * 
	 * 设定当前选中行对象
	 * 
	 * @author foss-dengyao
	 * @date 2013-04-25 上午10:31:38
	 */
	protected void setSelectedVo(List<DataConsistencyCheckVo> selectedRows) {
		DataCheckVoModel tableModel =(DataCheckVoModel)table.getModel();
		tableModel.setSelectedData(selectedRows);
	}
	
	
	/**
	 * 
	 * 设置表格数据
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-25 上午10:31:56
	 */
	
	
	public LinkedButtonColumn getCheckColum() {
		return checkColum;
	}
	public LinkedButtonColumn getAllColum() {
		return allColum;
	}
	/*public LinkedButtonColumn getAddColum() {
		return addColum;
	}*/

	public JXTable getTable() {
		return table;
	}
	
	/**
	* 全选
	* @author dengyao
	*
	*/
	class AllListener  implements ItemListener  { 
			public void itemStateChanged(ItemEvent e) { 
				Object obj = e.getSource();
				if(obj.equals(allSelectCheckBox) && table != null) {
					DataCheckVoModel tableModel =(DataCheckVoModel)table.getModel();
					List<DataConsistencyCheckVo> tableData=tableModel.getInitDataList();
					if(allSelectCheckBox.isSelected()) {
						
						///全选						
						if(tableData!=null){
							for(int i=0;i<tableData.size();i++){
								tableModel.setValueAt(true, i, 0);
							}
						}
					} else {               
						//全不选
						if(tableData!=null){
							for(int i=0;i<tableData.size();i++){
								tableModel.setValueAt(false, i, 0);
							}
						}
					}  
						table.repaint();
				}           
			}
	}
	
	/**
	 * 
	 * 封装表格的数据模型， 设置成以对象进行操
	 * 
	 */
	public class DataCheckVoModel extends DefaultTableModel{
		private static final long serialVersionUID = -6543839237123152L;
		
		public DataCheckVoModel(){
			
		}
		
		/**
		 * 表格数据
		 */		
		private List<DataConsistencyCheckVo> initDataList;
		
		public List<DataConsistencyCheckVo> getInitDataList() {
			return initDataList;
		}

		public void setInitDataList(List<DataConsistencyCheckVo> initDataList) {
			this.initDataList = initDataList;
			selectedDatas.clear();
			fireSelectedChanged();
		}
		
		
		/**
		 * 改变的列表
		 */
		private List<ChangeListener> changeListeners = new Vector<ChangeListener>();
		
		
		/**
		 * 
		 * 添加事件
		 * @author FOSS-dengyao
		 * @date 2013-4-19 下午08:18:08
		 * @param changeListener
		 */
		public void addChangeListener(ChangeListener changeListener){
			changeListeners.add(changeListener);
		}
		
		public void setData(List<DataConsistencyCheckVo> initDataList) {
			this.initDataList = initDataList;
		}
		
		/**
		 * 更新所选的表格数据
		 */	
		public void setSelectedData(List<DataConsistencyCheckVo> selectedData) {
			for(DataConsistencyCheckVo selected:selectedData){
				for(DataConsistencyCheckVo initData:initDataList){
					if (selected.getEntityClsName().equals(initData.getEntityClsName())){
						initData.setCounts(selected.getCounts());
						initData.setSyncDate(selected.getSyncDate());
						if(selected.getLocalCounts()!=0){
							initData.setLocalCounts(selected.getLocalCounts());
						}
						break;
					}
				}
			}
		}
		
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return "选择";
			case 1:
				return "实体名";
			case 2:
				return "表名";
			case THREE:
				return "区域";
			case FOUR:
				return "本地版本";
			case FIVE:
				return "服务器版本";
			case SIX:
				return "本地记录数";
			case SEVEN:
				return "服务器记录数";
			case EIGHT:
				return "操作";

			default:
				return "";
			}
		}
		
		public int getColumnCount() {
			return NINE;
		}
		
		public int getRowCount() {
			return initDataList == null ? 0 : initDataList.size();
		}
		
		public boolean isCellEditable(int row, int column) {
			return column == 0 || column == EIGHT;
		}
		
		public Object getValueAt(int row, int column) {
			switch (column) {
			case 0:
				// 选择
				return selectedDatas.contains(initDataList.get(row));
			case 1:
				// 实体名
				return initDataList.get(row).getEntityClsName();
			case 2:
				// 表名
				return initDataList.get(row).getTabelName();
			case THREE:
				//区域
				return initDataList.get(row).getRegionID();
			case FOUR:
				// 本地版本
				return timeTransfer(initDataList.get(row).getLocalSyncDate());
			case FIVE:
				// 服务器版本
				return timeTransfer(initDataList.get(row).getSyncDate());	
			case SIX:
				// 本地记录数
				return initDataList.get(row).getLocalCounts();
			case SEVEN:
				// 服务器记录数
				return initDataList.get(row).getCounts();
			case EIGHT:
				// 操作
				return "检查";			
			default:
				return super.getValueAt(row, column);
			}
		}
		
		/**
		 * 设置表格中CheckBox
		 * 
		 * @author foss-dengyao
		 * @date 2013-4-23 上午9:50:52
		 * @see javax.swing.JTable#getColumnClass(int)
		 */
		public Class<?> getColumnClass(int column) {
			switch (column) {
			case 0:
				return Boolean.class;
			default:
				return super.getColumnClass(column);
			}		
		}
		
		/**
		 * 获得选中的VALUE列表
		 * @return
		 */
		//public List<DataConsistencyCheckVo> getSelectedValues() {
			//return selectedValues;
		//}
		
		/**
		 * 获得列 VALUE，复选框选择时的数据添加
		 * @param aValue
		 * @param row
		 * @param column
		 */
		public void setValueAt(Object aValue, int row, int column) {
			switch (column) {
			case 0:
				Boolean repeat=false;
				Boolean isSelected = (Boolean)aValue;
				if(isSelected){
					for(DataConsistencyCheckVo data:selectedDatas){
						if(data.getEntityClsName().equals(initDataList.get(row).getEntityClsName())){
							repeat=true;
							break;
						}
					}
					if (repeat){
						break;
					}
					selectedDatas.add(initDataList.get(row));
				}else{
					selectedDatas.remove(initDataList.get(row));
				}
			
			default:
				 break;
			}
			//fireSelectedChanged();
		}
		
		/**
		 * 
		 * 去掉添加事件
		 * @author FOSS-dengyao
		 * @date 2013-4-19 下午08:18:22
		 * @param changeListener
		 */
		public void removeChangeListener(ChangeListener changeListener){
			changeListeners.remove(changeListener);
		}
		
		/**
		 * 
		 * 去掉添加事件
		 * @author FOSS-dengyao
		 * @date 2013-4-19 下午08:18:30
		 */
		public void fireSelectedChanged(){
			Iterator<ChangeListener> iterator = changeListeners.iterator();
			ChangeEvent changeEvent = new ChangeEvent(this);
			while(iterator.hasNext()){
				ChangeListener changeListener =	iterator.next();
				changeListener.stateChanged(changeEvent);
			}
		}
		
	}
	
	/**
	 * @日期格式转换
	 */
	private String timeTransfer(Date date){
		if (date==null){
			return null;
		}
		SimpleDateFormat sdf= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss",Locale.SIMPLIFIED_CHINESE);
		return sdf.format(date);
	}
	
	public JCheckBox getAllSelectCheckBox() {
		return allSelectCheckBox;
	}
	public void setAllSelectCheckBox(JCheckBox allSelectCheckBox) {
		this.allSelectCheckBox = allSelectCheckBox;
	}
	public List<DataConsistencyCheckVo> getSelectedDatas() {
		return selectedDatas;
	}
	public void setSelectedDatas(List<DataConsistencyCheckVo> selectedDatas) {
		this.selectedDatas = selectedDatas;
	}
	public List<SyncDataRequest> getRequests() {
		return requests;
	}
	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}

}
