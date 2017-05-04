package com.deppon.pda.bdm.module.core.shared.constants;
/**
 * @需求：Ocr二期
 * @功能：定义一些常用常量
 * @author:218371-foss-zhaoyanjun
 * @date:2016-11-03
 */
public class OcrContant {
	//最大等待OCR处理时间，用于发送给FOSS
	public static final int waitTime=30;
	
	//数据字典OCR词条
	public static final String OcrTerm="OCR";
	
	//数据字典ocr全国上线
	public static final String OcrAllArea="0";
	
	//数据字典ocr不允许上线
	public static final String OcrNoArea="-1";
	
	//数据字典ocr区域上线
	public static final String OcrPartArea="1";
}
