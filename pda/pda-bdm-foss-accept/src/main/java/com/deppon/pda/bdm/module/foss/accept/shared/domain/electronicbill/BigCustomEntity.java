package com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill;

import java.io.Serializable;

/**   
 * @ClassName BigCustomEntity  
 * @Description 大客户信息   
 * @author  092038 张贞献  
 * @date 2014-7-10    
 */ 
public class BigCustomEntity implements Serializable{
	/**  
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 联系人名称.
     */
    private String curstomerName;
    
    /**
     * 联系人编码.
     */
    private String curstomerCode;
    
    /**
     * 联系人手机.
     */
    private String bigCurstomTel;
      
    /**
     * 联系人地址.
     */
    private String bigCurstomAddr;
	
	/**
	 * 下单量
	 */
	private Integer countWayBills;
	
	
	private String isCollectGps;


	public String getCurstomerName() {
		return curstomerName;
	}


	public void setCurstomerName(String curstomerName) {
		this.curstomerName = curstomerName;
	}


	public String getCurstomerCode() {
		return curstomerCode;
	}


	public void setCurstomerCode(String curstomerCode) {
		this.curstomerCode = curstomerCode;
	}


	public String getBigCurstomTel() {
		return bigCurstomTel;
	}


	public void setBigCurstomTel(String bigCurstomTel) {
		this.bigCurstomTel = bigCurstomTel;
	}


	public String getBigCurstomAddr() {
		return bigCurstomAddr;
	}


	public void setBigCurstomAddr(String bigCurstomAddr) {
		this.bigCurstomAddr = bigCurstomAddr;
	}


	public Integer getCountWayBills() {
		return countWayBills;
	}


	public void setCountWayBills(Integer countWayBills) {
		this.countWayBills = countWayBills;
	}


	public String getIsCollectGps() {
		return isCollectGps;
	}


	public void setIsCollectGps(String isCollectGps) {
		this.isCollectGps = isCollectGps;
	}
	

    
    
}
