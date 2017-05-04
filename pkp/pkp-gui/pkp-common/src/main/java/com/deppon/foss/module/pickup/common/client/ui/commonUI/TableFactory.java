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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/commonUI/TableFactory.java
 * 
 * FILE NAME        	: TableFactory.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.common.client.ui.commonUI;

import javax.swing.JTable;


/**
 * 
 * 表格样式工厂类
 * @author 102246-foss-shaohongliang
 * @date 2012-12-27 上午10:58:08
 */
public class TableFactory {
	
	/**
	 * 
	 * 给表格第一列加上行号显示
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-27 上午10:58:38
	 */
	public static TableRowColumn createRowColumn(JTable table){
		return new TableRowColumn(table);
	}
	
	/**
	 * 
	 * 设置表格指定列为可编辑按钮状态，按钮文本为TableModel返回内容
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-27 上午10:59:02
	 */
	public static ButtonColumn createButtonColumn(JTable table, int column){
		return new ButtonColumn(table, column);
	}
	
	/**
	 * 
	 * 设置表格指定列为可编辑按钮状态，按钮文本为TableModel返回内容，指定固定宽度
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-27 上午10:59:02
	 */
	public static ButtonColumn createButtonColumn(JTable table, int column, int width){
		return new ButtonColumn(table, column, width);
	}
	
	/**
	 * 
	 * 设置表格指定列为可编辑带下划线链接按钮状态，按钮文本为TableModel返回内容
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-27 上午10:59:02
	 */
	public static LinkedButtonColumn createLinkedButtonColumn(JTable table, int column){
		return new LinkedButtonColumn(table, column);
	}

	/**
	 * 
	 * 设置表格指定列为可编辑带下划线链接按钮状态，按钮文本为TableModel返回内容，指定固定宽度
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-27 上午10:59:02
	 */
	public static LinkedButtonColumn createLinkedButtonColumn(JTable table, int column, int width){
		return new LinkedButtonColumn(table, column, width);
	}
}