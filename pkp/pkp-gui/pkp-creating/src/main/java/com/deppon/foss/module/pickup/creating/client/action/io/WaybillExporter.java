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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/io/WaybillExporter.java
 * 
 * FILE NAME        	: WaybillExporter.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.action.io;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;

/**
 * 运单导出工具类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105089-foss-yangtong,date:2012-10-12 上午10:56:23, </p>
 * @author 105089-foss-yangtong
 * @date 2012-10-12 上午10:56:23
 * @since
 * @version
 */
public class WaybillExporter {
	
	//log object
	private static Log log = LogFactory.getLog(WaybillExporter.class);
	/**
	 * 国际化
	 */
	private static II18n i18n = I18nManager.getI18n(WaybillExporter.class);

    //文件选取器
    private JFileChooser fileChooser;

    /**
     * 
     * <p>导出文件功能</p> 
     * @author 105089-foss-yangtong
     * @date 2012-10-11 下午5:26:51
     * @see
     */
	public boolean exportFile(JComponent parent, List<?> entitys,
			String operateId, String deptId) {
		if (entitys == null) {
			throw new IllegalArgumentException(
					"waybill entitys does not allow null");
		}
		if (operateId == null) {
			throw new IllegalArgumentException("operateId does not allow null");
		}
		if (deptId == null) {
			throw new IllegalArgumentException("deptId does not allow null");
		}
		if (fileChooser == null) {
			FileSystemView fsv = FileSystemView.getFileSystemView();
			fileChooser = new JFileChooser(fsv.getHomeDirectory());
			fileChooser.setMultiSelectionEnabled(false);
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooser.setFileFilter(new WaybillFileFilter());
			fileChooser.setSelectedFile(new File(new SimpleDateFormat(
					"yyyyMMdd").format(new Date()) + operateId + ".dat"));
		}
		int shouldSave = fileChooser.showSaveDialog(parent); // 保存
		File file = fileChooser.getSelectedFile();
		if (file != null && shouldSave == JOptionPane.YES_OPTION) {
			file = new File(file.getAbsolutePath());
			if (file.exists()) {
				final int confirm = JOptionPane.showConfirmDialog(parent,
						i18n.get("foss.gui.creating.waybillExporter.messageDialog.isOverwrite"), 
						i18n.get("foss.gui.creating.waybillExporter.messageDialog.overwrite"), JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (confirm != JOptionPane.YES_OPTION) {
					return true;
				}
			}
			try {
				exportToFile(entitys, operateId, deptId, file);
				JOptionPane.showMessageDialog(parent, i18n.get("foss.gui.creating.waybillExporter.messageDialog.exportSuccess")+"："
						+ file.getAbsolutePath(), i18n.get("foss.gui.creating.waybillExporter.messageDialog.success"),
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			} catch (Exception t) {
				log.error("exception", t);
				JOptionPane.showMessageDialog(
						parent,
						i18n.get("foss.gui.creating.waybillExporter.messageDialog.exportDatFailed"+"：") + t.getMessage()
						+ i18n.get("foss.gui.creating.waybillExporter.messageDialog.file"+"：")
						+ file.getAbsolutePath(), i18n.get("foss.gui.creating.waybillExporter.messageDialog.failed"),
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		fileChooser.setVisible(false);
		return true;
	}
    
	/**
     * 
     * <p>导出json文件并签名</p> 
     * @author 105089-foss-yangtong
     * @date 2012-10-11 下午5:26:51
     * @see
     */
	private void exportToFile(List<?> entitys, String operateId,
			String deptId, File file) throws Exception {

		if (entitys == null) {
			throw new IllegalArgumentException("entitys does not allow  null");
		}
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(entitys);
		SignatureDelegate.writeFile(json, file);

	}

}