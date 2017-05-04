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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/utils/NumberDocument.java
 * 
 * FILE NAME        	: NumberDocument.java
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
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:实现限制文本框长度，并且只能输入数字字符</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
*  2012-4-1 linws    新增
* </div>  
********************************************
 */
public class NumberDocumentUtils extends PlainDocument {
	// 序列号
	private static final long serialVersionUID = 1300304776696337273L;

	//最大长度
	private int limit;

	public NumberDocumentUtils(int limit) {
		super();
		this.limit = limit;
	}

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null) {
			return;
		} else {
			// 移去空格
			str = str.replaceAll(" ", "");
			str = str.replaceAll("\n", "");
		}
		if ((getLength() + str.length()) <= limit) {
			char[] upper = str.toCharArray();
			int length = 0;
			for (int i = 0; i < upper.length; i++) {
				if (upper[i] >= '0' && upper[i] <= '9' || upper[i] == '-' || upper[i] == '.') {
					upper[length++] = upper[i];
				}
			}
			super.insertString(offset, new String(upper, 0, length), attr);
		}
	}
}