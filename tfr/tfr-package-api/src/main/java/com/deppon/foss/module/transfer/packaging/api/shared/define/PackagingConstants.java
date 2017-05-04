/**
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
 * 
 * 
 * 
 * 
 * 
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
 *  PROJECT NAME  : tfr-package-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/api/shared/define/PackagingConstants.java
 *  
 *  FILE NAME          :PackagingConstants.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-package-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.packaging.api.shared.define
 * FILE    NAME: PackagingConstants.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.packaging.api.shared.define;

/**
 *  包装模块的常量类
 * @author 046130-foss-xuduowei
 * @date 2012-10-22 上午8:30:21
 */
public class PackagingConstants {
	//Y
	public final static String PACKAGING_Y = "Y";

	public final static String PACKAGING_N = "N";

	/**
	 * 定义流水号长度
	 */
	public final static int SERIAL_LENGTH = 4;
	/**
	 * 定义ibatis的命名空间
	 */
	public final static String PACKAGING_IBATIS_NAMESAPCE = "foss.packaging.pack.";
	/**
	 * 定义ibatis的命名空间
	 */
	public final static String PACKAGING_OUT_IBATIS_NAMESAPCE = "foss.packaging.outpack.";
	//数据字典的激活状态
	public final static String PACKAING_DICT_ACTIVE_Y = "Y";
	//流水号激活状态
	public final static String SERIALNO_ACTIVE = "1";
	//流水号激活状态
	public final static String SERIALNO_DISABLE = "0";
	//失败标志
	public final static String PACKAGING_FAILURE = "0";
	//在木架区库存中
	public final static String PACKAGING_IS_IN_YOKE = "是";
	//不在木架区库存
	public final static String PACKAGING_NOT_IN_YOKE = "否";
	//代包装明细状态字段
	/**
	 * 已开单
	 */
	public final static String PACKAGING_DETAILS_GOODS_CREATE = "已开单"; 
	/**
	 * 已交接
	 */
	public final static String PACKAGING_DETAILS_GOODS_HANDOVER = "已交接"; 
	/**
	 * 库存中
	 */
	public final static String PACKAGING_DETAILS_GOODS_STOCK = "库存中";
	/**
	 * 出发
	 */
	public final static String PACKAGING_DETAILS_GOODS_DEPART = "出发";
	/**
	 * 到达
	 */
	public final static String PACKAGING_DETAILS_GOODS_ARRIVE = "到达";
	/**
	 * 未入库
	 */
	public final static String PACKAGING_DETAILS_GOODS_NOTINSTORE = "未入库";
	/**
	 * 签收
	 */
	public final static String PACKAGING_DETAILS_GOODS_SIGNIN = "签收";
	/**
	 * 全部
	 */
	public final static String PACKAGING_DETAILS_GOODS_ALL = "全部";
	/**
	 * 代包装信息excel表头信息
	 */
	public final static String[] PACKAGING_EXPORT_UNPACK_HEADER = {"运单号","开单件数","开单体积","需要包装体积","代包装货区件数","需要包装件数","代包装要求",
			"已包装件数","货物状态","运输类型","货物名称","开单部门","目的站","货物开单时间","预计到达时间","预计出发时间"};
	/**
	 * 已包装信息excel表头信息
	 */
	//public final static String[] PACKAGING_EXPORT_PACKED_HEADER = {"运单号","包装时间","开单部门","是否在包装区","包装部门","货物名称",
	//		"开单件数","包装件数","加托个数","包装材料","包装体积","包装人","创建人","修改人","登入包装货区时间","登出包装货区时间","包装需求"};
	/**
	 * 已包装信息excel表头信息
	 */
	public final static String[] PACKAGING_EXPORT_PACKED_HEADER = {"运单号","包装时间","开单部门","是否在包装区","包装部门","货物名称",
			"开单件数","包装件数","加托个数","包装材料","包装体积","包装人","创建人","修改人","登入包装货区时间","登出包装货区时间","包装需求",
		"备注","原流水号","新流水号","运输性质","开单体积","需要包装体积"};
	/**
	 * .
	 */
	public final static String PACKAGING_EXPORT_DOT = ".";
	/**
	 * 新增标签，需要增加变动事项
	 */
	public final static String PACKAGING_LABEL_SLASH = "/";
	public final static String PACKAGING_CHANGE_ITEMS_BEFORE = "打木架：第";
	public final static String PACKAGING_CHANGE_ITEMS_BACK = "件合并";	
	
	/**
	 * 包装导出文件名称
	 */
	//代包装货物信息
	public final static String PACKAGING_EXPORT_FILE_NAME_UNPACK = "代包装货物信息";
	//已包装货物信息
	public final static String PACKAGING_EXPORT_FILE_NAME_PACKED = "已包装货物信息";
	//
	public final static Integer PACKAGING_EXPORT_FILE_SIZE = 5000;
	
	//包装录入业务锁
	public final static String PACKAGING_ADD_LOCK_DESC = "包装录入业务锁";
	
	//登入
	public final static String PACKAGING_LOGING_IN = "in";
	//登出
	public final static String PACKAGING_LOGING_OUT = "out";
	
	//打木托打木架类型
	public enum packing {  
		WOODEN_FRAME("木架", "WOODEN_FRAME"), MAKE_WOODEN_STOCK("木托", "MAKE_WOODEN_STOCK");
	    // 成员变量  
	    private String name;  
	    private String value;  
	    // 构造方法  
	    private packing(String name, String value) {  
	        this.name = name;  
	        this.value = value;  
	    }  
	    // 普通方法  
	    public static String getName(String value) {  
	        for (packing c : packing.values()) {  
	            if (c.getValue().equals(value)) {  
	                return c.name;  
	            }  
	        }  
	        return null;  
	    }  
	    // get set 方法  
	    public String getName() {  
	        return name;  
	    }  
	    public void setName(String name) {  
	        this.name = name;  
	    }  
	    // get set 方法  
	    public String getValue() {  
	    	return value;  
	    }  
	    public void setValue(String value) {  
	    	this.value = value;  
	    }  
	 
	}  
	//包装金额中计算打木架体积与打木箱体积中理论与实际的关系常量
	public final static double 	PACKAGE_W_F_THEORY_ACTUAL_RELATION_CONSTANT = 1.01;
	
	//包装金额中计算打木架、打木箱时的比较常量
	public final static double 	PACKAGE_W_F_COMPARE_CONSTANT = 0.01;


   //打木架生成代办件数定义最大开单件数
	public final static int PACKAGE_BILL_GOODSQTY=30;

	/**
	 * 包装应付单审核状态
	 * **/
	//未审核
	public final static String PACKAGE_PAYBILL_AUDIT_STATUS_WAITINGAUDIT="WAITINGAUDIT";
	//已审核
	public final static String PACKAGE_PAYBILL_AUDIT_STATUS_HASAUDITED="HASAUDITED";


	//包装金额支持最多导出记录
	public final static int PACKAGE_PAYBILL_EXPORT_MAX_RECORD_NUM=50000;
    /*
	 * 定义导入辅助包装金额用到的字段
	 * 
	 * **/
	//定义导入辅助包装金额信息的最大列数
	public final static int PACKAGE_ASSIST_IMPORT_MAXCOL_NUM=9;
	
	//定义导入辅助包装金额信息的运单列
	public final static int PACKAGE_ASSIST_IMPORT_WAYBILLNO=0;
	//定义导入辅助包装金额信息的包装供应商编码列
	public final static int PACKAGE_ASSIST_IMPORT_SUPPLIERCODE=1;
	//定义导入辅助包装金额信息实际打木架体积
	public final static int PACKAGE_ASSIST_IMPORT_FRAME_VOLUME=2;
	//定义导入辅助包装金额信息的实际打木箱体积
	public final static int PACKAGE_ASSIST_IMPORT_BOX_VOLUME=3;
	//定义导入辅助包装金额信息的打木托个数
	public final static int PACKAGE_ASSIST_IMPORT_MASK_NUM=4;
	//定义导入辅助包装金额信息的木条长度
	public final static int PACKAGE_ASSIST_IMPORT_WOODBAR_NUM=5;
	//定义导入辅助包装金额信息的气泡膜体积
	public final static int PACKAGE_ASSIST_IMPORT_BUBBVELAMEN_VOLUME=6;
	//定义导入辅助包装金额信息的缠绕膜体积
	public final static int PACKAGE_ASSIST_IMPORT_BINDVELAMEN_NUM=7;
	//定义导入辅助包装金额信息的包带根数
	public final static int PACKAGE_ASSIST_IMPORT_BAGBEL_NUM=8;
	
	//定义导入辅助包装的最大条数
	public final static int PACKAGE_ASSIST_IMPORT_COUNT=1000;

	/**sonar_constants_number*/
	public final static int SONAR_NUMBER_3 = 3;
	public final static int SONAR_NUMBER_4 = 4;
	public final static int SONAR_NUMBER_5 = 5;
	public final static int SONAR_NUMBER_6 = 6;
	public final static int SONAR_NUMBER_7 = 7;
	public final static int SONAR_NUMBER_8 = 8;
	public final static int SONAR_NUMBER_9 = 9;
	public final static int SONAR_NUMBER_10 = 10;
	public final static int SONAR_NUMBER_11 = 11;
	public final static int SONAR_NUMBER_12 = 12;
	public final static int SONAR_NUMBER_3000 = 3000;
	
}