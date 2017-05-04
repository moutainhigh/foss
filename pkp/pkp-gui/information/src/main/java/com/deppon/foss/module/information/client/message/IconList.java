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
 * PROJECT NAME	: pkp-gui
 * 
 * FILE PATH        	: information/src/main/java/com/deppon/foss/module/information/client/message/IconList.java
 * 
 * FILE NAME        	: IconList.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.information.client.message;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import com.deppon.foss.base.util.define.NumberConstants;

/**
 * 带图标的list控件封装类
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:55:38,
 * </p>
 * 
 * @author dp-yangtong
 * @date 2012-10-12 上午9:55:38
 * @since
 * @version
 */

public class IconList extends JList {
	private static final long serialVersionUID = 2439644961676200742L;

	public IconList(DefaultListModel model)// 使用列表模板创建列表
	{	super(model);// 调用父类构造方方
		setCellRenderer(new MyCellRenderer());// 设置单元格设置
			
	}
	/**
	 * 
	 * 树格渲染器
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	class MyCellRenderer extends JLabel implements ListCellRenderer {

		private static final long serialVersionUID = -238788462923678406L;

		public Component getListCellRendererComponent(JList list, Object value, // value
				int index, // cell index
				boolean isSelected, // is the cell selected
				boolean cellHasFocus) // the list and the cell have the focus
		{

			Object[] cell = (Object[]) value;
			setIcon((Icon) cell[0]);// 设置图片
			setText((cell[1].toString()));// 设置文本

			setBorder(BorderFactory.createEmptyBorder(NumberConstants.NUMBER_5, NumberConstants.NUMBER_5, NumberConstants.NUMBER_5, NumberConstants.NUMBER_5));// 加入宽度为5的空白边框
			index = index + 1;
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(Color.WHITE);

			} else {
				if (index % 2 == 0) {
					setBackground(Color.WHITE);
				} else {
					setBackground(new Color(NumberConstants.NUMBER_238, NumberConstants.NUMBER_238, NumberConstants.NUMBER_238));
				}
			}

			setForeground(new Color(NumberConstants.NUMBER_90, NumberConstants.NUMBER_90, NumberConstants.NUMBER_90));
			setEnabled(list.isEnabled());
			setFont(new Font("宋体", Font.BOLD, NumberConstants.NUMBER_6));

			setSize(NumberConstants.NUMBER_100, NumberConstants.NUMBER_200);
			setOpaque(true);

			return this;
		}
	}
}