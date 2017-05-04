package com.deppon.foss.module.pickup.creatingexp.client.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillBatchChangeWeightUI;

/**
 * 
 * 模板下载
 * @author 136334-foss-guohao
 * @date 2015-1-29 下午1:54:16
 */
public class ExpWaybillBatchDownLoadWeightAction implements IButtonActionListener<ExpWaybillBatchChangeWeightUI> {
	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(ExpWaybillBatchDownLoadWeightAction.class);
	
	private static final Logger LOG = Logger.getLogger(ExpWaybillBatchDownLoadWeightAction.class);

	private ExpWaybillBatchChangeWeightUI ui;
	
	/**
	 * 
	 * 执行更改
	 * @author 136334-foss-guohao
	 * @date 2015-5-5 下午5:27:34
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// 创建一个Workbook，对应一个Excel文件  
		XSSFWorkbook wb = new XSSFWorkbook();  
        //在Workbook中添加一个sheet  
        XSSFSheet sheet = wb.createSheet();  
        //在sheet中添加表头第0行
        XSSFRow row = sheet.createRow((int) 0);  
        //创建单元格，并设置值表头 设置表头居中  
        XSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
  
        XSSFCell cell = row.createCell(0, 1);   
        cell.setCellValue("运单号");  
        cell.setCellStyle(style);  
        cell = row.createCell(1, 1); 
        cell.setCellValue("重量");  
        cell.setCellStyle(style);  
        cell = row.createCell(2, 1); 
        cell.setCellValue("体积");  
        cell.setCellStyle(style); 
      //start  装卸费
        /*cell = row.createCell(3, 1); 
        cell.setCellValue("装卸费");  
        cell.setCellStyle(style); */
        //end
        //保存路径
        String path  = selectSavePath();
        //保存到指定位置
		if (null != path) {
		    FileOutputStream fout = null;
			try {
				fout = new FileOutputStream(path);

			} catch (IOException e1) {
				LOG.info("异常信息:"+e1.getMessage());
				e1.printStackTrace();

			} finally {
				try {
					wb.write(fout);
					if(fout != null){
						fout.close();
					}
				} catch (IOException e1) {
					LOG.info("异常信息:"+e1.getMessage());
					e1.printStackTrace();
				}

			}
		}
       
	}
	public String selectSavePath(){
		
      //构造文件保存对话框
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setMultiSelectionEnabled(false);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setDialogTitle("模板保存");
		
		//文件过滤器
		chooser.addChoosableFileFilter(new FileFilter(){
			public boolean accept(File f) {
				if (f.getName().endsWith("xlsx") || f.isDirectory()) {
					 return true; 
				}else{
					return false; 
				}
			}
			public String getDescription() {
				return "Excel文件(*.xlsx)";
			}
		});
		
		//打开对话框
		int index = chooser.showSaveDialog(null);
		//文件确定
		if(index == JFileChooser.APPROVE_OPTION) {
			String outPath = chooser.getSelectedFile().getAbsolutePath()+".xlsx";
			if(new File(outPath).exists()){
				if(1==JOptionPane.showConfirmDialog(null, "文件已经存在,是否要覆盖该文件?", "模板保存", JOptionPane.YES_NO_OPTION)){
					return null;
				}
			}
			return outPath;
		}
        return null;
	}
	
	@Override
	public void setInjectUI(ExpWaybillBatchChangeWeightUI ui) {
		this.ui = ui;
	}
	
}