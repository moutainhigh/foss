package com.deppon.foss.module.pickup.common.client.ui.commonUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;

import com.deppon.foss.module.pickup.common.client.utils.ExcelUtils;



public class FossExportExcelButton extends JButton {
	private static final long serialVersionUID = 549038207134295751L;
	
	private JFileChooser fileChooser;
	
	
	public FossExportExcelButton(final JTable table, String text,String fileName ,final Integer... notExportCol) {
		if (table == null){
			throw new IllegalArgumentException("target component table can not be null");
		}
		super.setText(text);
		fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(new ExcelFileFilter());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		fileChooser.setSelectedFile(new File(fileName+"-"+sdf.format(new Date())+".xls"));
		this.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				fileChooser.setVisible(true);
				fileChooser.showSaveDialog(FossExportExcelButton.this); // 淇濆瓨
				String info = "提示信息";
				File file = fileChooser.getSelectedFile();
				if (file != null) {
					file = new File(file.getAbsolutePath());
					if (file.exists()) {
						final int confirm = JOptionPane.showConfirmDialog(FossExportExcelButton.this, "Excel文件已经存在", info, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (confirm != JOptionPane.YES_OPTION) {
							return;
						}
					}
					try {
						ExcelUtils.exportExcel(table, file,notExportCol);
						JOptionPane.showMessageDialog(FossExportExcelButton.this, "导出excel成功: " + file.getAbsolutePath(), info, JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception t) {
						JOptionPane.showMessageDialog(FossExportExcelButton.this, "导出excel失败: " + t.getMessage(), info, JOptionPane.WARNING_MESSAGE);
					}
				}
				fileChooser.setVisible(false);
			}
		});
		
	}

	private class ExcelFileFilter extends FileFilter{

		public String getDescription() {
			return "*.xls";
		}
		public boolean accept(final File file) {
			return file.getName().endsWith(".xls");
		}
		
	}
}
