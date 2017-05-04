package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class CockpitVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 外场编码
	 */
	private String tfrCtrCode;

	/**
	 * 外场名称
	 */
	private String tfrCtrName;

	/**
	 * 当前时间
	 */
	private Date currentTime;
	
	
	 
	/**
	* @fields dutyCount
	* @author 14022-foss-songjie
	* @update 2015年9月2日 下午2:50:19
	* @version V1.0
	*/
	private String dutyCount;// duty_tb.duty_count --上班人数统计
     
    /**
    * @fields absenteeCount
    * @author 14022-foss-songjie
    * @update 2015年9月2日 下午2:50:21
    * @version V1.0
    */
    private String absenteeCount;//absentee_tb.absentee_count --上班异常人数
     
    /**
    * @fields zxlhCount
    * @author 14022-foss-songjie
    * @update 2015年9月2日 下午2:50:25
    * @version V1.0
    */
    private String zxlhCount;// zxlh_tb.zxlh_count --在线理货员 -快递理货员,理货员
     
    /**
    * @fields zxdccCount
    * @author 14022-foss-songjie
    * @update 2015年9月2日 下午2:50:28
    * @version V1.0
    */
    private String zxdccCount;// zxdcc_tb.zxdcc_count --在线电叉车司机统计
     
    /**
    * @fields platformRate
    * @author 14022-foss-songjie
    * @update 2015年9月2日 下午2:50:31
    * @version V1.0
    */
    private String platformRate;//   platform_rate_tb.platform_rate --月台使用比率
     
    /**
    * @fields stockVolume
    * @author 14022-foss-songjie
    * @update 2015年9月2日 下午2:50:34
    * @version V1.0
    */
    private String stockVolume;// stock_volume_tb.stock_volume --零担派送库存库区方数
     
    /**
    * @fields return_rate
    * @author 14022-foss-songjie
    * @update 2015年9月2日 下午2:50:36
    * @version V1.0
    */
    private String returnRate;//return_rate_tb.return_rate --派送退单率
     
    /**
    * @fields saturationDay
    * @author 14022-foss-songjie
    * @update 2015年9月2日 下午2:50:39
    * @version V1.0
    */
    private String saturationDay;//saturation_tb.Saturation_Day --仓库饱和度
    
    /**
	 * 货台库存货量(吨)
	 */
    private String stockWeight;

	/**
	 * 待卸货量(吨)
	 */
	private String waitUnloadWeight;

	/**
	 * (到达本外场)长途在途车辆数
	 */
	private String lngDisOnTheWayNums;

	/**
	 * (到达本外场)短途在途车辆数
	 */
	private String shtDisOnTheWayNums;

	/**
	 * (到达本外场)长途到达未卸车辆数
	 */
	private String lngDisArrivedNums;

	/**
	 * (到达本外场)段途到达未卸车辆数
	 */
	private String shtDisArrivedNums;

	/**
	 * 装卸车进度异常数
	 */
	private String loadUnloadProgressAbnormalNums;
	

	/**
	 * 返回结果
	 */
	private List<CockpitVo> cockpits;

	public String getTfrCtrCode() {
		return tfrCtrCode;
	}

	public void setTfrCtrCode(String tfrCtrCode) {
		this.tfrCtrCode = tfrCtrCode;
	}

	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	public List<CockpitVo> getCockpits() {
		return cockpits;
	}

	public void setCockpits(List<CockpitVo> cockpits) {
		this.cockpits = cockpits;
	}

	public String getTfrCtrName() {
		return tfrCtrName;
	}

	public void setTfrCtrName(String tfrCtrName) {
		this.tfrCtrName = tfrCtrName;
	}

	public String getDutyCount() {
		return dutyCount;
	}

	public void setDutyCount(String dutyCount) {
		this.dutyCount = dutyCount;
	}

	public String getAbsenteeCount() {
		return absenteeCount;
	}

	public void setAbsenteeCount(String absenteeCount) {
		this.absenteeCount = absenteeCount;
	}

	public String getZxlhCount() {
		return zxlhCount;
	}

	public void setZxlhCount(String zxlhCount) {
		this.zxlhCount = zxlhCount;
	}

	public String getZxdccCount() {
		return zxdccCount;
	}

	public void setZxdccCount(String zxdccCount) {
		this.zxdccCount = zxdccCount;
	}

	public String getPlatformRate() {
		return platformRate;
	}

	public void setPlatformRate(String platformRate) {
		this.platformRate = platformRate;
	}

	public String getStockVolume() {
		return stockVolume;
	}

	public void setStockVolume(String stockVolume) {
		this.stockVolume = stockVolume;
	}
	
	public String getReturnRate() {
		return returnRate;
	}

	public void setReturnRate(String returnRate) {
		this.returnRate = returnRate;
	}

	public String getSaturationDay() {
		return saturationDay;
	}

	public void setSaturationDay(String saturationDay) {
		this.saturationDay = saturationDay;
	}

	public String getStockWeight() {
		return stockWeight;
	}

	public void setStockWeight(String stockWeight) {
		this.stockWeight = stockWeight;
	}

	public String getWaitUnloadWeight() {
		return waitUnloadWeight;
	}

	public void setWaitUnloadWeight(String waitUnloadWeight) {
		this.waitUnloadWeight = waitUnloadWeight;
	}

	public String getLngDisOnTheWayNums() {
		return lngDisOnTheWayNums;
	}

	public void setLngDisOnTheWayNums(String lngDisOnTheWayNums) {
		this.lngDisOnTheWayNums = lngDisOnTheWayNums;
	}

	public String getShtDisOnTheWayNums() {
		return shtDisOnTheWayNums;
	}

	public void setShtDisOnTheWayNums(String shtDisOnTheWayNums) {
		this.shtDisOnTheWayNums = shtDisOnTheWayNums;
	}

	public String getLngDisArrivedNums() {
		return lngDisArrivedNums;
	}

	public void setLngDisArrivedNums(String lngDisArrivedNums) {
		this.lngDisArrivedNums = lngDisArrivedNums;
	}

	public String getShtDisArrivedNums() {
		return shtDisArrivedNums;
	}

	public void setShtDisArrivedNums(String shtDisArrivedNums) {
		this.shtDisArrivedNums = shtDisArrivedNums;
	}

	public String getLoadUnloadProgressAbnormalNums() {
		return loadUnloadProgressAbnormalNums;
	}

	public void setLoadUnloadProgressAbnormalNums(
			String loadUnloadProgressAbnormalNums) {
		this.loadUnloadProgressAbnormalNums = loadUnloadProgressAbnormalNums;
	}
	
	

}
