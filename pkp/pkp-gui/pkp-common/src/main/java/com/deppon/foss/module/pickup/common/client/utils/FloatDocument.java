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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/utils/FloatDocument.java
 * 
 * FILE NAME        	: FloatDocument.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * 
 * 限制文本框只允许输入浮点类型
 * 
 * @author 025000-FOSS-helong
 * @date 2012-10-31 下午02:41:20
 */
public class FloatDocument extends PlainDocument {
	// 序列号
	private static final long serialVersionUID = 1300304776696337273L;

	// 最大长度
	private int limit;
	
	//保留小数位
	private int floatLength;

	public FloatDocument(int limit,int floatLength) {
		super();
		this.limit = limit;
		this.floatLength = floatLength;
	}

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null) {
			return;
		} else {
			// 移去空格
			str = str.replaceAll(" ", "");
			str = str.replaceAll("\n", "");
		}

		if(str.length() == 1)
		{
			if ((getLength() + str.length()) <= limit) 
			{
				char[] upper = str.toCharArray();
				int length = 0;
				boolean bool = true;

				String text = getText(0, getLength());
				String two = "";
				//判断文本框中是否已经存在“.”号
				if (text.indexOf(".") >= 0) {
					two  = text.substring(text.indexOf(".")+1, getLength());
					bool = false;
				}
				//保留两位小数点
				if(two.length() < floatLength)
				{
					for (int i = 0; i < upper.length; i++) {
						//判断第一次输入的数据是否为“.”号，是则不允许输入
						if (getLength() == 0 && upper[i] == '.') {
							bool = false;
						}

						if ((upper[i] >= '0' && upper[i] <= '9') || (upper[i] == '.' && bool)) {
							upper[length++] = upper[i];
						}

					}
					super.insertString(offset, new String(upper, 0, length), attr);
				}
			}
		}else
		{
			super.insertString(offset, str, attr);
		}
	}
}