package com.deppon.foss.module.pickup.creating.client.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.creating.client.common.ExcelUtil;
import com.deppon.foss.module.pickup.creating.client.ui.LTLEWaybillManageTableModel;
import com.deppon.foss.module.pickup.creating.client.ui.LTLEWaybillManageUI;

/**
 * 零担电子面单批量导入重量体积的信息导出按钮
 * TODO 此类暂时没有使用，以后如果需要，再做使用
 * @author 305082
 *
 */
public class LTLEWaybillSalesDeptExportAction extends AbstractButtonActionListener<LTLEWaybillManageUI> {
	public static final Logger LOGGER = LoggerFactory.getLogger(LTLEWaybillSalesDeptExportAction.class);
	// 国际化
	private static final II18n i18n = I18nManager.getI18n(LTLEWaybillSalesDeptExportAction.class);

	private LTLEWaybillManageUI ui;
	
	@Override
	public void setIInjectUI(LTLEWaybillManageUI ui) {
		this.ui = ui;
	}

	/**
	 * 导出按钮事件
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		//定义文件名
		String filePathName="零担电子运单基本信息";
		//得到Jtablemodel对象
		LTLEWaybillManageTableModel model=(LTLEWaybillManageTableModel) ui.getTable().getModel();
		int count = model.getRowCount();
		if (count <= 0) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.ExpWaybillBatchChangeWeightUI.BatchChangeEWaybilInfoDialog.notDataToImport"));
			return;
		}
		List<Integer> rowIndexs=new Vector<Integer>();
 		//获取选中数据行数索引
		for(int i=0;i<count;i++){
			//判断是否选择
			   if(ui.getAllSelectCheckBox().isSelected()){
				   rowIndexs.add(i);
		   }else{
			   Object isCheckboxStr=model.getValueAt(i, 0);
			   if(isCheckboxStr==null){
				   rowIndexs.add(i);
			   }
		   }		
       	
		}
		//根据rowIndexs来判断是否有数据
		if(rowIndexs.size()>0){
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			if (null != user&&!user.getTitle().equals("营业部经理")) {
				MsgBox.showInfo(i18n.get("foss.gui.creating.LTLEWaybillManageUI.common.employeeManage"));
				return;
			}
			// 判定Table表是否存在数据，如果无数据，无法进行导出
			JFileChooser fc = new JFileChooser();
			fc.setDialogType(JFileChooser.SAVE_DIALOG);
			fc.setFileFilter(new FileNameExtensionFilter("*.xls;*.xlsx", "xls", "xlsx"));// 添加文本文件过滤
			//begin
			fc.setSelectedFile(new File(filePathName)); //设置文件名
			int result = fc.showOpenDialog(ui);
			//end
			if(result==JFileChooser.APPROVE_OPTION){
				String path=fc.getSelectedFile().getAbsolutePath();
				File file=fc.getSelectedFile();
				LOGGER.info("文件路径为:" + file.getAbsolutePath());
				//如果不是Excel格式的，则无法保存
				if(!file.getName().endsWith(".xls") && !file.getName().endsWith(".xlsx")){
					path = path + ".xls";
				}
				FileOutputStream out=null;
				try {
					//创建输出流
					 out = new FileOutputStream(new File(path));
					//输出内容
					ExcelUtil.create(null, getRowData(model,rowIndexs), getColumnNames(model), filePathName, out);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
					LOGGER.info("异常信息:"+e1.getMessage());
				} catch (IOException e2) {
					e2.printStackTrace();
					LOGGER.info("异常信息:"+e2.getMessage());
				}finally{
					if(out!=null){
						try {
							out.close();
						} catch (IOException e1) {
							e1.printStackTrace();
							LOGGER.info("异常信息:"+e1.getMessage());
						}
					}
				}
				
			}
		}else{
			MsgBox.showInfo(i18n.get("foss.gui.creating.ExpWaybillBatchChangeWeightUI.BatchChangeEWaybilInfoDialog.notSelect"));
			return;
		}
		
	}
	
	//获取table列名称
	public String[] getColumnNames(TableModel model){
		String[] columnNames=new String[model.getColumnCount()]; 
		for(int i=1;i<model.getColumnCount();i++){
			columnNames[i-1]=model.getColumnName(i).toString();
		}
		return columnNames;
	}
	
	//获取jtble数据
	public List<Object> getRowData(TableModel model,List<Integer> rowIndexs){
		List<Object> rowList=new ArrayList<Object>();
	    for(int i:rowIndexs) {
	    	String[] rowData=new String[model.getColumnCount()];
	    	for(int j=1;j<model.getColumnCount();j++){
	    	rowData[j-1]=model.getValueAt(i, j)+"";
	    	}
	    	rowList.add(rowData);
	    }
	    return rowList;
	}

	
	
}
