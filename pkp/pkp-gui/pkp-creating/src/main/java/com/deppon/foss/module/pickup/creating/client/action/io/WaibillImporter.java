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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/io/WaibillImporter.java
 * 
 * FILE NAME        	: WaibillImporter.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.action.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;

/**
 * 运单导入工具类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105089-foss-yangtong,date:2012-10-12 上午10:56:23, </p>
 * @author 105089-foss-yangtong
 * @date 2012-10-12 上午10:56:23
 * @since
 * @version
 */
public class WaibillImporter {

	//log object
	private static Log log = LogFactory.getLog(WaibillImporter.class);
	
	/**
	 * 国际化
	 */
	private static II18n i18n = I18nManager.getI18n(WaibillImporter.class);

    // 文件选取器
    private JFileChooser fileChooser;

    public List<?> importFile(JComponent parent,String deptId) {
	if (fileChooser == null) {
	    FileSystemView fsv = FileSystemView.getFileSystemView();
	    fileChooser = new JFileChooser(fsv.getHomeDirectory());
	    fileChooser.setMultiSelectionEnabled(false);
	    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	    fileChooser.setFileFilter(new WaybillFileFilter());
	}
	int shouldOpen = fileChooser.showOpenDialog(parent); // 打开
	File file = fileChooser.getSelectedFile();
	List<?> entityList = null;
	if (file != null && shouldOpen == JOptionPane.YES_OPTION) {
	    file = new File(file.getAbsolutePath());
	    if (file.exists()) {
			try {
			    entityList = importFromFile(deptId, file);
			    return entityList;
			} catch (Exception t) {
				log.error("exception", t);
			    JOptionPane.showMessageDialog(parent,
				    i18n.get("foss.gui.creating.waibillImporter.messageDialog.importExcelFailed"+"：")
				    + t.getMessage() + i18n.get("foss.gui.creating.waibillImporter.messageDialog.file"+"：")
					+ file.getAbsolutePath(), i18n.get("foss.gui.creating.waibillImporter.messageDialog.failed"),
				    JOptionPane.WARNING_MESSAGE);
			}
	    } else {
	    	JOptionPane.showMessageDialog(parent,
	    			i18n.get("foss.gui.creating.waibillImporter.messageDialog.fileNotExist"+"：") 
	    			+ file.getAbsolutePath(),
	    			i18n.get("foss.gui.creating.waibillImporter.messageDialog.failed"),
			JOptionPane.WARNING_MESSAGE);
	    }
	}
		return entityList;
    }
    
    /**
     * 
     * <p>验证签名导入文件</p> 
     * @author 105089-foss-yangtong
     * @date 2012-10-11 下午5:26:51
     * @see
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private List<?> importFromFile(String deptId, File file) throws Exception {
 
    	String content = SignatureDelegate.verify(file);
    	List<WaybillPendingEntity> resultList = new ArrayList();
    	if(!content.equals("")){
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				WaybillPendingEntity[] arr = objectMapper.readValue(content, WaybillPendingEntity[].class);
				resultList = Arrays.asList(arr);
			} catch (JsonParseException e) {
				log.error("JsonParseException", e);
			} catch (JsonMappingException e) {
				log.error("JsonMappingException", e);
			} catch (IOException e) {
				log.error("IOException", e);
			}
			return resultList;
		}else{
		    throw new IllegalStateException(i18n.get("foss.gui.creating.waibillImporter.exception.fileModified"));
		}
    }
    
}