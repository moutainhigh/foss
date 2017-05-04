/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/SalesDeptExportAction.java
 * 
 * FILE NAME        	: SalesDeptExportAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creatingexp.client.action;

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
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.module.pickup.common.client.action.AbstractButtonActionListener;
import com.deppon.foss.module.pickup.creatingexp.client.common.ExcelUtil;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpEWaybillTableMode;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpSalesDepartEWaybillUI;

/**
 * 电子面单导出按钮
 * 
 * @author Foss-105888-Zhangxingwang
 * @date 2014-12-31 15:08:08
 */
public class ExpEWaybillSalesDeptExportAction extends AbstractButtonActionListener<ExpSalesDepartEWaybillUI> {
	public static final Logger LOGGER = LoggerFactory.getLogger(ExpEWaybillSalesDeptExportAction.class);
	// 国际化
	private static final II18n i18n = I18nManager.getI18n(ExpEWaybillSalesDeptExportAction.class);

	private ExpSalesDepartEWaybillUI ui;

	@Override
	public void setIInjectUI(ExpSalesDepartEWaybillUI ui) {
		this.ui = ui;
	}

	/**
	 * 导出按钮事件（准备废掉）
	 * 
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-12-31 15:08:29
	 */
	/*public void actionPerformed(ActionEvent e) {
		int count = ui.getTable().getModel().getColumnCount();
		if (count <= 0) {
			MsgBox.showInfo(i18n.get("foss.gui.creating.ExpWaybillBatchChangeWeightUI.BatchChangeEWaybilInfoDialog.notDataToImport"));
			return;
		}
		// 判定Table表是否存在数据，如果无数据，无法进行导出

		JFileChooser fc = new JFileChooser();
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setFileFilter(new FileNameExtensionFilter("*.xls;*.xlsx", "xls", "xlsx"));// 添加文本文件过滤
		//快递与菜鸟对接项目 CN-263--begin
		fc.setSelectedFile(new File("异常数据信息")); //设置默认文件名
		//快递与菜鸟对接项目 CN-263--end
		int result = fc.showOpenDialog(ui);
		while(result == JFileChooser.APPROVE_OPTION){
			String path = fc.getSelectedFile().getAbsolutePath();
			File file = fc.getSelectedFile();
			LOGGER.info("文件路径为:" + file.getAbsolutePath());
			//如果不是Excel格式的，则无法保存
			if(!file.getName().endsWith(".xls") && !file.getName().endsWith(".xlsx")){
				path = path + ".xls";
			}
			try{
//				FileWriter out = new FileWriter(path);//新建输出
			    OutputStreamWriter out=new OutputStreamWriter(new FileOutputStream(path),"GBK");
		        for (int i = 2; i < ui.getTable().getColumnCount(); i++) {
		        	out.write(ui.getTable().getColumnName(i) + "\t");
		        }
		        out.write("\n");
		        for (int i = 0; i < ui.getTable().getRowCount(); i++) {
		            for (int j = 2; j < ui.getTable().getColumnCount(); j++) {
		            	Object object = ui.getTable().getValueAt(i, j);
		               if(object != null){
		            	   out.write(ui.getTable().getValueAt(i, j).toString() + "\t");
		               }else{
		            	   out.write("" + "\t");
		               }
		            }
		            out.write("\n");
		        }
		        out.close();
		        JOptionPane.showMessageDialog(null, "Excel文件导出成功");
		        return;
		        //如果需要保存这个Excel然后打开可以使用下面的这个命令
		        //Runtime.getRuntime().exec("cmd /c start "+ "" + path);
			}catch (Exception ee) {
				LOGGER.info("异常信息:"+ee.getMessage());
				ee.printStackTrace();   
			}
		}
	}*/
	
	/**
	 * 导出按钮事件
	 * 
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-12-31 15:08:29
	 * 修改 270293-foss-zhangfeng
	 * @date 2014-07-09 10:30
	 */
	public void actionPerformed(ActionEvent e) {
		//定义文件名
		String filePathName="大客户电子订单基本信息";
		//得到Jtablemodel对象
		ExpEWaybillTableMode model=(ExpEWaybillTableMode) ui.getTable().getModel();
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
			// 判定Table表是否存在数据，如果无数据，无法进行导出
			JFileChooser fc = new JFileChooser();
			fc.setDialogType(JFileChooser.SAVE_DIALOG);
			fc.setFileFilter(new FileNameExtensionFilter("*.xls;*.xlsx", "xls", "xlsx"));// 添加文本文件过滤
			//快递与菜鸟对接项目 CN-263--begin
			fc.setSelectedFile(new File(filePathName)); //设置文件名
			int result = fc.showOpenDialog(ui);
			//快递与菜鸟对接项目 CN-263--end
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