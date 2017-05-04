package com.deppon.foss.module.pickup.common.client.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
/**
 * 文本框手机号码校验
 * @author Foss-105888-Zhangxingwang
 * @date 2015-5-13 10:08:49
 */
public class MobileDocument extends PlainDocument {
	// 序列号
	private static final long serialVersionUID = 1300304776696337273L;

	//最大长度
	private int limit;

	public MobileDocument(int limit) {
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
				if (upper[i] >= '0' && upper[i] <= '9') {
					upper[length++] = upper[i];
				}
			}
			super.insertString(offset, new String(upper, 0, length), attr);
		}
	}
}
