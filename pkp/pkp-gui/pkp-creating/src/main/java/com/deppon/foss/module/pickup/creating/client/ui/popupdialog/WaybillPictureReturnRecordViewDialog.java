package com.deppon.foss.module.pickup.creating.client.ui.popupdialog;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.pickup.creating.client.ui.PictureViewReturnButtonColumn;
import com.deppon.foss.module.pickup.creating.client.ui.SearchPictureReturnTableModel;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 查看图片开单退回记录Dialog
 * 
 * @author WangQianJin
 * @date 2013-07-17
 */
public class WaybillPictureReturnRecordViewDialog extends JDialog {
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -5960720224792355823L;
	
	private static final Log log = LogFactory.getLog(WaybillPictureReturnRecordViewDialog.class);

	private static final int FIVE = 5;

	private static final int NUM_407 = 407;

	private static final int NUM_531 = 531;

	private static final int NUM_425 = 425;
	
	/**
	 * panel
	 */
	private final JPanel contentPanel = new JPanel();
	
	/**
	 * 结果列表
	 */
	private JXTable table;
	
	public JXTable getTable() {
		return table;
	}

	public void setTable(JXTable table) {
		this.table = table;
	}

	/**
	 * Create the dialog.
	 */
	public WaybillPictureReturnRecordViewDialog(List<WaybillPictureEntity> waybillPictureEntitys) {
		initTable();
		setData(waybillPictureEntitys);
		init();
		new PictureViewReturnButtonColumn(table.getColumn(1), this, (SearchPictureReturnTableModel) table.getModel());
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
				FormFactory.DEFAULT_ROWSPEC
				}));
		{
			JScrollPane scrollPane = new JScrollPane(table);
			contentPanel.add(scrollPane, "2, 2, 19, 1, fill, fill");
		}
		// 设置模态
		setModal(true);
	}
	
	
	/**
	 * 
	 * 初始化表格
	 */
	private void initTable() {
		//初始化表格
		table = new JXTable(){
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
		table.setModel(new SearchPictureReturnTableModel(null));
		//设置表格可以有滚动条
		table.setAutoscrolls(true);
		//禁止表格排序
		table.setSortable(false);
		//表格设置为可编辑
		table.setEditable(true);
		//设置列可见状态
		table.setColumnControlVisible(true);
		table.setAutoResizeMode(JXTable.AUTO_RESIZE_OFF);
		table.setHorizontalScrollEnabled(true);
		
		/**
		 * 固定表格的宽度
		 */
		TableColumnModel tcm = table.getTableHeader().getColumnModel();
		for (int i = 0; i < tcm.getColumnCount(); i++) {
			TableColumn tc = tcm.getColumn(i);
			if (i == 1) {
				tc.setPreferredWidth(NumberConstants.NUMBER_50);
				tc.setMaxWidth(NumberConstants.NUMBER_50);
			} else if (i == 0) {
				tc.setPreferredWidth(NUM_425);
			}
		}
		
		// 居中显示
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, render);
		((DefaultTableCellRenderer) table.getTableHeader()
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
	public void setData(List<WaybillPictureEntity> waybillPictureEntitys) {
		SearchPictureReturnTableModel model = ((SearchPictureReturnTableModel) table.getModel());
		model.setData(getArray(waybillPictureEntitys));
		// 刷新表格数据
		model.fireTableDataChanged();
	}
	
	@SuppressWarnings({ "rawtypes" })
	public Object[][] getArray(List list) {
		if (list != null && !list.isEmpty()) {
			// 转换为二维数组
			Object[][] objtemp = new Object[list.size()][];
			Object[] q;
			// 根据集合中的工号查询对应的姓名
			// List<EmployeeEntity> empList = queryEmployeeByCode(list);
			for (int i = 0; i < list.size(); i++) {
				Object pending = (Object) list.get(i);
				
				q = getRowValue(pending);

				for (int j = 0; j < objtemp.length; j++) {
					objtemp[i] = q;
				}
			}
			return objtemp;
		} else {
			return null;
		}
	}
	
	/**
	 * getRowValue结果列表
	 * @param object
	 * @return
	 */
	public Object[] getRowValue(Object object) {

		String reMark = "";
		
		String waybillId = "";
		
		if (object instanceof WaybillPictureEntity) {
			
			WaybillPictureEntity vo = (WaybillPictureEntity) object;
			
			reMark = vo.getRemark();
			
			try {
				JSONObject  jsonObject  = JSON.parseObject(reMark);
				//防止老版本非json格式
				//BackPictureWaybillDialog 中ConfirmHandler  的waybillBackMessage键值
				String waybillBackMessage = jsonObject.get(WaybillConstants.WAYBILL_BACK_MESSAGE).toString();
				reMark = waybillBackMessage;
				
			} catch (Exception e) {
				if(log.isErrorEnabled()){
					log.error(e);
				}
			}
			
			waybillId = vo.getId();
			
		}

		
		// 获取对象成员保存至一维数组
		Object[] obj = { reMark, "", waybillId};
		return obj;
	}
	

}