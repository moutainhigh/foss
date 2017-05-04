package com.deppon.foss.module.pickup.common.client.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
/**
 * 
 * @author DP-FOSS-YANGKANG
 * @Description:实现限制文本框长度，输入数值必须为负数或者0，且最多不超过两位小数
 */
public class NegativeNumberDocument extends PlainDocument {
		//序列化
		private static final long serialVersionUID = 1L;

		//最大长度
		private int limit;
		//输入小数位数
		private int floatLength;

		public NegativeNumberDocument(int limit,int floatLength) {
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
					int floatIndex = 0;//小数点的位置
					int zeroIndex =0;//第一个0的位置
					String text = getText(0, getLength());
					String floatBackLength = "";//小数点后字符
					floatIndex = text.indexOf(".");//获取小数点的位置,当floatIndex 不等于0的时候，则已经录入过小数点，不允许重复录入
					zeroIndex = text.indexOf("0");//获取第一个0 的位置
					
					//如果第一次输入的是0，则后续不允许输入
					if(zeroIndex==0 && getLength()!=0){
						bool = false;
					}
					//判断文本框中是否已经存在“.”号
					if (text.indexOf(".") >= 0) {
						floatBackLength  = text.substring(text.indexOf(".")+1, getLength());
					}
					boolean isContinueWrite = false;//是否可以继续录入
					//如果小数位数符合标准
					if(floatBackLength.length() < floatLength
							|| (floatBackLength.length() == floatLength && offset<=floatIndex)//如果小数位数等于设定的位数，则进行判断，若是录入的数字在小数点后，则不允许录入，若录入在小数点前，则允许
					){
						isContinueWrite = true;
					}
					if(isContinueWrite){
						for (int i = 0; i < upper.length; i++) {
							//判断第一次输入的数据是否为“.”号，是则不允许输入
							if (getLength() == 1 && upper[i] == '.') {
								bool = false;
							}
							//判断第一次输入的数据是否为'-'或者'0',如果不是则不允许输入
							if(getLength() == 0 && upper[i] != '-'&&upper[i]!='0'){
								bool = false;
							}
							//不是第一次输入'-',则不允许输入
							if(getLength() != 0 && upper[i] == '-'){
								bool = false;
							}

							if ((upper[i] >= '0' && upper[i] <= '9'&&bool) || (upper[i] == '.' &&floatIndex<2 && bool)||(upper[i]=='-'&&bool)) {
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
