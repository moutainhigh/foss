/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-common-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/shared/define/TfrSerialNumberRuleEnum.java
 *  
 *  FILE NAME          :TfrSerialNumberRuleEnum.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.common.api.shared.define;

/**
 * 中转编号规则定义
 * <pre>
 * 枚举定义：
 * 
 * 
 * 编号中文名称
 * 是否需要输入参数
 * 是否需要日期prefix
 * 日期prefix
 * 是否需要分隔符 
 * 分隔符 
 * 是否需要字母前缀
 * 字母prefix
 * 是否需要数字
 * 是否需要Seq
 * Seq命名
 * 是否固定位数FIXED
 * 数字位数 
 * 是否需要后缀suffix
 * 后缀值
 * </pre>
 * 
 * @author foss-wuyingjie
 * @date 2012-11-2 下午4:46:44
 * 
 * 
 */
public enum TfrSerialNumberRuleEnum {
//	中文名称                                                 编码                 参数         是否日期前缀                   是否分隔符     是否字母前缀        是否累计      是否需要sequence           是否固定位数        后缀
	DC("订舱编号",               	"DC",      false, true, "yyyyMMdd", true, "-", false, "",      true,  false, "",                   true,  6, false, ""),
	ZDJJD("正单交接单号",       	"ZDJJD",   false, false, "",        false, "", false, "",      true,  true,  "TFR.SEQ_SN_ZDJJD",   true,  8, false, ""),
	ZZ("中转单号",               	"ZZ",      false, false, "",        false, "", true,  "zz",    true,  true,  "TFR.SEQ_SN_ZZ",      true,  7, false, ""),
	HP("合票号",                 "HP",      false, false, "",        false, "", true,  "DP",    true,  true,  "TFR.SEQ_SN_HP",      true,  7, false, ""),
	CLRW("（车辆）任务唯一标识号", 	"CLRW",    false, false, "",        false, "", true,  "f",     true,  true,  "TFR.SEQ_SN_CLRW",    true,  8, false, ""),
	FX("放行流水号",             	"FX",      false, false, "",        false, "", true,  "c", 	   true,  true,  "TFR.SEQ_SN_FX",      true,  8, false, ""),
	WBQ("无标签运单号",          	"WBQ",     false, false, "",        false, "", true,  "w", 	   true,  true,  "TFR.SEQ_SN_WBQ",     true,  8, false, ""),
	QC("清仓任务编号",           	"QC",      true,  true, "yyyyMMdd", false, "", false, "03:04", true,  false, "",                   true,  5, false, ""),
	CY("清仓差异报告编号",        	"CY", 	   true,  false, "",        false, "", true,  "D", 	   false, false, "",                   false, 0, false, ""),
	YC("约车申请编号",           	"YC", 	   false, false, "",        false, "", true,  "Y",     true,  true,  "TFR.SEQ_SN_YC",      false, 8, false, ""),
	JC("借车申请编号",           	"JC", 	   false, false, "", 		false, "", true,  "J",     true,  true,  "TFR.SEQ_SN_JC",      false, 8, false, ""),
	WQCYC("外请车申请编号",      	"WQCYC",   false, false, "", 		false, "", true,  "W",     true,  true,  "TFR.SEQ_SN_WQCYC",   false, 8, false, ""),
	JJD("交接单号",             	"JJD", 	   false, false, "", 		false, "", false, "",      true,  true,  "TFR.SEQ_SN_JJD",     true,  8, false, ""),
	JJD_PDA("PDA生成交接单号",  	"JJD_PDA", false, false, "", 		false, "", false, "",      true,  true,  "TFR.SEQ_SN_JJD_PDA", true,  8, true, "p"),
	PSZC("派送装车任务编号",    	"PSZC",    true,  true, "yyyyMMdd", false, "", true,  "07",    true,  false, "", 				   true,  5, false, ""),
	ZC("装车任务编号",          	"ZC",      true,  true, "yyyyMMdd", false, "", false, "01:05", true,  false, "", 				   true,  5, false, ""),
	XC("卸车任务编号",          	"XC",      true,  true, "yyyyMMdd", false, "", false, "02:06", true,  false, "", 				   true,  5, false, ""),
	PSZCCY("派送装车差异报告编号", "PSZCCY",    true,  false, "", 		true, "_", false, "",      false, false, "", 				   false, 0, true, "d"),
	XCCY("卸车差异报告编号", 		"XCCY",    true,  false, "", 		true, "_", false, "",      false, false, "", 				   false, 0, true, "c"),
	PZCC("配载车次号", 			"PZCC",    true,  false, "yyMMdd", 	false, "", false, "",      false, false, "", 				   false, 2, false, ""),
	WBQYDH("无标签运单号", 	    "WBQYDH",  false, false, "", 	    false, "", true,  "w",     true,  true,  "TFR.SEQ_SN_WBQYDH",  true,  8, false, ""),
	ZCBJ("租车标记",           "ZCBJ",       true,  true,"yyyyMMdd",  false, "", false, "",      true,  false, "",                   true,  6, false, ""),
	BH("包号",                "BH",         false,  true,"yyyyMMdd",  false, "", false, "",      true,  false, "",                   true,  5, false, ""),
	WBQBH("无标签多货编号",		 "WBQBH",	   false, true,"yyyyMMdd", false, "", false,  "",     true,   false,  "",					true, 6, false, ""),
	FGTASK("找货任务编号",     "FGTASK",        false,true,"yyyyMMdd",false, "",false, "",       true,true,"TFR.FGOODS_TASKNO",true,  4,false, ""),
	SCZC("二程接驳任务编号",    	"SCZC",    true,  true, "yyyyMMdd", false, "", true,  "08",    true,  false, "", 				   true,  5, false, ""),
	JBJJD("接驳交接单",       "JBJJD",   false,  false,"",  false, "", true, "C",      true,  true, "TFR.SEQ_SN_JBJJD",  true,  8, true, ""),
	KSD("空驶单",       "KSD",   false,  false,"",  false, "", true, "K",      true,  true, "TFR.SEQ_SN_KSD",  true,  8, true, ""),
	ODRPT_NO("点单差异报告编号",       "ODRPT_NO",   false,  false,"",  false, "", true, "H",      true,  true, "TFR.SEQ_RN_ODREPT_NO",  true,  9, true, ""),
	OC("点单任务编号",       "OC",   false,  false,"",  false, "", true, "H",      true,  true, "TFR.SEQ_SN_OC",  true,  8, true, "");
	private String name;			  //中文名称
	private String code;			  //编码
	private boolean needParams;		  //是否需要输入参数
	private boolean needTime;		  //是否需要日期prefix
	private String timeFormat;		  //日期prefix
	private boolean needDelimiter;	  //是否需要分隔符
	private String delimiter;		  //分隔符
	private boolean needLetterPrefix; //是否需要字母前缀
	private String letterPrefix;	  //字母prefix
	private boolean needNumber;		  //是否需要数字累加
	private boolean needSequence;	  //是否需要sequence
	private String sequence;		  //sequence命名
	private boolean fixedNumLen;	  //是否固定位数FIXED
	private int numLen;				  //数字位数
	private boolean needLetterSuffix; //是否需要后缀suffix
	private String letterSuffix;	  //后缀值
	
	private TfrSerialNumberRuleEnum(String name, String code, boolean needParams,
			boolean needTime, String timeFormat, boolean needDelimiter, String delimiter, 
			boolean needLetterPrefix, String letterPrefix, boolean needNumber, boolean needSequence, String sequence,
			boolean fixedNumLen, int numLen, boolean needLetterSuffix, String letterSuffix) {
		this.name = name;
		this.code = code;
		this.needParams = needParams;
		this.needTime = needTime;
		this.timeFormat = timeFormat;
		this.needDelimiter = needDelimiter;
		this.delimiter = delimiter;
		this.needLetterPrefix = needLetterPrefix;
		this.letterPrefix = letterPrefix;
		this.needNumber = needNumber;
		this.needSequence = needSequence;
		this.sequence = sequence;
		this.fixedNumLen = fixedNumLen;
		this.numLen = numLen;
		this.needLetterSuffix = needLetterSuffix;
		this.letterSuffix = letterSuffix;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isNeedParams() {
		return needParams;
	}

	public void setNeedParams(boolean needParams) {
		this.needParams = needParams;
	}

	public boolean isNeedTime() {
		return needTime;
	}

	public void setNeedTime(boolean needTime) {
		this.needTime = needTime;
	}

	public String getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	public boolean isNeedDelimiter() {
		return needDelimiter;
	}

	public void setNeedDelimiter(boolean needDelimiter) {
		this.needDelimiter = needDelimiter;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public boolean isNeedNumber() {
		return needNumber;
	}

	public void setNeedNumber(boolean needNumber) {
		this.needNumber = needNumber;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public boolean isFixedNumLen() {
		return fixedNumLen;
	}

	public void setFixedNumLen(boolean fixedNumLen) {
		this.fixedNumLen = fixedNumLen;
	}

	public int getNumLen() {
		return numLen;
	}

	public void setNumLen(int numLen) {
		this.numLen = numLen;
	}

	public boolean isNeedLetterSuffix() {
		return needLetterSuffix;
	}

	public void setNeedLetterSuffix(boolean needLetterSuffix) {
		this.needLetterSuffix = needLetterSuffix;
	}

	public String getLetterSuffix() {
		return letterSuffix;
	}

	public void setLetterSuffix(String letterSuffix) {
		this.letterSuffix = letterSuffix;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLetterPrefix() {
		return letterPrefix;
	}

	public void setLetterPrefix(String letterPrefix) {
		this.letterPrefix = letterPrefix;
	}

	public boolean isNeedLetterPrefix() {
		return needLetterPrefix;
	}

	public void setNeedLetterPrefix(boolean needLetterPrefix) {
		this.needLetterPrefix = needLetterPrefix;
	}

	public boolean isNeedSequence() {
		return needSequence;
	}

	public void setNeedSequence(boolean needSequence) {
		this.needSequence = needSequence;
	}
}