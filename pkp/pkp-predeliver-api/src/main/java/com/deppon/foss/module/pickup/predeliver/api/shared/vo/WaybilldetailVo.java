package com.deppon.foss.module.pickup.predeliver.api.shared.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.mongodb.util.Hash;

/**
 * 运单明细值对象(VO)
 * @author 中软国际-李顺翔
 *
 */
public class WaybilldetailVo implements Serializable{
	private Map<String,Object> map=new HashMap<String, Object>(0);
	private boolean flag;			//是否默认查询
	private String waybillNos;		//用户输入的运单集合
	private String dept;			//部门编码
	private String createCode;		//创建人编码
	private String deliverCode;		//司机编码
	private String deliverNo;		//派送单
	private String vehicle;			//车牌号
	private String deliverType;		//派送类型
	public String getCreateCode() {
		return createCode;
	}
	public String getDeliverCode() {
		return deliverCode;
	}
	public String getDeliverNo() {
		return deliverNo;
	}
	public String getDeliverType() {
		return deliverType;
	}
	public String getDept() {
		return dept;
	}
	public boolean getFlag() {
		return flag;
	}
	public Map<String, Object> getMap() {
		map.put("deliverType", this.deliverType);
		map.put("deliverNo", this.deliverNo);
		map.put("deliverCode", this.deliverCode);
		map.put("createCode", this.createCode);
		map.put("dept", this.dept);
		map.put("flag", this.flag);
		map.put("vehicle", this.vehicle);
		if(waybillNos.indexOf(",")<0){
			String str[]={waybillNos};
			map.put("waybillNos", str);
		}else{
			map.put("waybillNos", this.waybillNos.split(","));
		}
		return map;
	}
	public String getWaybillNos() {
		return waybillNos;
	}
	public String getVehicle() {
		return vehicle;
	}
	
	public void setCreateCode(String createCode) {
		this.createCode = createCode;
	}
	public void setDeliverCode(String deliverCode) {
		this.deliverCode = deliverCode;
		
	}
	public void setDeliverNo(String deliverNo) {
		this.deliverNo = deliverNo;
		
	}
	public void setDeliverType(String deliverType) {
		this.deliverType = deliverType;
		
	}
	public void setDept(String dept) {
		this.dept = dept;
		
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
		
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
		
	}
	public void setWaybillNos(String waybillNos) {
		this.waybillNos = waybillNos;
		
	}
}
