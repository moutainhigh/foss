package com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill;

import java.io.Serializable;

/**   
 * @ClassName CustomEWaybillEntity  
 * @Description 下拉客户订单明细 
 * @author  092038 张贞献  
 * @date 2014-8-24    
 */ 
public class BigCustomEWaybillEntity  implements Serializable{
	/**  
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 1L;
	//订单号
	private String orc;
	//件数
    private int pie;
    //运单号
  	private String wabi;	
	//返单类别
    private String reBT;
    
    
	public String getOrc() {
		return orc;
	}
	public void setOrc(String orc) {
		this.orc = orc;
	}
	public int getPie() {
		return pie;
	}
	public void setPie(int pie) {
		this.pie = pie;
	}
	public String getWabi() {
		return wabi;
	}
	public void setWabi(String wabi) {
		this.wabi = wabi;
	}
	public String getReBT() {
		return reBT;
	}
	public void setReBT(String reBT) {
		this.reBT = reBT;
	}

    
	
	
	/*
	//快递收入部门编码
    private String revenueCode;
	//快递收入部门名称
     private String evenueName;

   //目的站
    private String destDeptName;
    //目的站code
	private String destDeptCode;	
   //第二城市
    private String secCity;
   //第二外场
    private String secField;
   //最终外场
    private String finalField;
   //路由明细
    private String routeDetail;

	//收货客户姓名
    private String addresseeName;
	//收货人地址
    private String addresseeAddr;
	//收货客户手机
    private String addresseeTel;
	//运输方式
    private String transType;
	//提货方式
    private String takeType;
	//付款方式
    private String payType;
	//货物名称(品名) 
    private String goodsName;

	//重量
    private String weight;
	//体积
    private String volume;
	//保险声明价值
    private String insuredAmount;
	//代收货款类型
    private String reciveLoanType;
	//代收货款金额
    private String reviceMoneyAmount;
	//代收货款账号
    private String reviceLoadAccount;
	
	//下单时间
    private Date orderTime;
    
    
    //是否打印五角星
  	private String isStarFlag;
    //是否外发	
  	private String isPrintAt;
   
  	*/
  	
	
	
	
}
