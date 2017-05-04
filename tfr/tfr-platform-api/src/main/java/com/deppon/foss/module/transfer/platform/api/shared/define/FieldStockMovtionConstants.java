/**
 * 
 */
package com.deppon.foss.module.transfer.platform.api.shared.define;

/**
 * @author 105795
 * @desc 场内库存状态
 */
public final class FieldStockMovtionConstants {


	//到达未卸
	public static final int MOVTION_STOCK_ARRIVED_UNLOAD=1;//"ARRIVED_UNLOAD";
	
	//卸车中
	public static final int MOVTION_STOCK_UNLOADING=2;//"UNLOADING";
	
	//待叉区库存
	public static final int MOVTION_STOCK_TRAY=3;//"TRAY";
	
	//包装库区库存
	public static final int MOVTION_STOCK_PACKAGING=4;//"PACKAGING";
	
	//零担中转库区库存
	public static final int MOVTION_STOCK_TFR=5;//"STOCK";

	//零担派送库区库存
	public static final int MOVTION_STOCK_DELIVER=6;//"DELIVER";

	//快递中转库区库存
	public static final int MOVTION_STOCK_TFR_EXPRESS=7;//"EXPRESS";

	//快递派送库区库存
	public static final int MOVTION_STOCK_EXPRESS_DELIVER=8;//"EXPRESS_DELIVER";

	//已装车
	public static final int MOVTION_STOCK_LOADED=9;//"LOADED";

	/**
	 * 场内库存流动明细
	 * */
	public static final String MOVTION_STOCK_DETAIL_EXCEL_NAME = "场内库存流动明细信息.xls";

	/**
	 * 场内库存流动明细Excel表头
	 * */
	public static final String[] MOVTION_STOCK_DETAIL_EXCEL_TITLE = { "转运场", "运单号",
			"当前位置", "运输性质", "提货方式", "车牌号", "上一线路", "下一线路","开单件数","开单重量(KG)","当前件数","当前重量(KG)","当前体积(立方)"};
	
	
	
	
}
