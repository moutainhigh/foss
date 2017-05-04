package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.Map;

import com.deppon.foss.module.transfer.platform.api.shared.vo.CockpitVo;

public interface ICockpitDao {
	/**
	* @description 根据外场查询驾驶舱vo
	* @param orgCode
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年9月2日 下午3:08:24
	*/
	CockpitVo queryCockpitByOrgCode(String orgCode);

	
	String onDutyNums(Map<String,Object> map);
	
	String absenteeNums(Map<String,Object> map);
	
	String tallyNums(Map<String,Object> map);
	
	String eForkNums(Map<String,Object> map);
	
	String stockWeight(Map<String,Object> map);
	
	String waitUnloadWeight(Map<String,Object> map);
	
	String lngDisOnTheWayNums(Map<String,Object> map);
	
	String shtDisOnTheWayNums(Map<String,Object> map);
	
	String lngDisArrivedNums(Map<String,Object> map);
	
	String shtDisArrivedNums(Map<String,Object> map);
	
	String loadUnloadProgressAbnormalNums(Map<String,Object> map);
	
	String platformUsageRate(Map<String,Object> map);
	
	String dispatchStockVolume(Map<String,Object> map);
	
	String sendBackPct(Map<String,Object> map);
	
	String stockSaturation(Map<String,Object> map);
}
