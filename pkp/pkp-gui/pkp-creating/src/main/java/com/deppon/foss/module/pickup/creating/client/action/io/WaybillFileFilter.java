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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/action/io/WaybillFileFilter.java
 * 
 * FILE NAME        	: WaybillFileFilter.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.action.io;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * 文件选择过滤类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105089-foss-yangtong,date:2012-10-12 上午10:56:23, </p>
 * @author 105089-foss-yangtong
 * @date 2012-10-12 上午10:56:23
 * @since
 * @version
 */
public class WaybillFileFilter extends FileFilter{

	public String getDescription() {
		return "*.dat";
	}
	public boolean accept(final File file) {
		return file.getName().endsWith(".dat");
	}

}