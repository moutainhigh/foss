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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/outsideremark/OutsideRemark.java
 * 
 * FILE NAME        	: OutsideRemark.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.ui.outsideremark;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.service.DownLoadDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.IDataDictionaryValueService;
import com.deppon.foss.module.pickup.common.client.ui.layout.VFlowLayout;
import javax.swing.JCheckBox;


public class OutsideRemark extends JPanel {

	/**
	* 序列化
	*/
    private static final long serialVersionUID = 1L;
    
    /**
     * 数据字典service
     */
    private transient IDataDictionaryValueService dictionaryService = DownLoadDataServiceFactory.getDataDictionaryValueService();
    
    /**
     * 内容列表
     */
    private List<JCheckBox> checkList = new ArrayList<JCheckBox>();

    /**
     * Create the panel.
     */
    public OutsideRemark() {
    	init();
    }
    
    public List<JCheckBox> getCheckList() {
        return checkList;
    }

    /**
     * 
     *初始化控件数据
     * @author 025000-FOSS-helong
     * @date 2012-10-31 上午10:08:14
     */
    private void init(){
    	setLayout(new VFlowLayout());//垂直流布局
    	List<DataDictionaryValueEntity> list = dictionaryService.queryOuterRemark();
    	for(DataDictionaryValueEntity dataDictionary:list){
    	    JCheckBox chckbxNewCheckBox = new JCheckBox(dataDictionary.getValueName());
    	    add(chckbxNewCheckBox);
    	    checkList.add(chckbxNewCheckBox);
    	}
    }
}