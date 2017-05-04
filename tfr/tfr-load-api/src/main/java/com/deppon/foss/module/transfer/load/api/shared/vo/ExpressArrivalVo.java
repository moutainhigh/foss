package com.deppon.foss.module.transfer.load.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressArrivalDisplayEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressArrivalQueryDto;


public class ExpressArrivalVo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	
	/**
	* @fields 快递到达查询DTO
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午11:03:02
	* @version V1.0
	*/
	private ExpressArrivalQueryDto expressArrivalQueryDto;

	/**
	* @description 获取快递到达查询DTO
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午11:12:57
	*/
	public ExpressArrivalQueryDto getExpressArrivalQueryDto() {
		return expressArrivalQueryDto;
	}
	
	/**
	* @description 设置快递到达查询DTO
	* @param expressArrivalQueryDto
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午11:13:09
	*/
	public void setExpressArrivalQueryDto(
			ExpressArrivalQueryDto expressArrivalQueryDto) {
		this.expressArrivalQueryDto = expressArrivalQueryDto;
	}

	
	/**
	* @fields 快递到达查询结果集
	* @author 218381-foss-lijie
	* @update 2015年5月12日 下午3:54:17
	* @version V1.0
	*/
	private List<ExpressArrivalDisplayEntity> expressArrivalDisplayEntityList;

	
	/**
	* @description 获取快递到达查询结果集
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 下午3:54:53
	*/
	public List<ExpressArrivalDisplayEntity> getExpressArrivalDisplayEntityList() {
		return expressArrivalDisplayEntityList;
	}

	
	/**
	* @description 设置快递到达查询结果集
	* @param expressArrivalEntityList
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 下午3:55:16
	*/
	public void setExpressArrivalDisplayEntityList(
			List<ExpressArrivalDisplayEntity> expressArrivalDisplayEntityList) {
		this.expressArrivalDisplayEntityList = expressArrivalDisplayEntityList;
	}
	
	
	/**
	* @fields id
	* @author 218381-foss-lijie
	* @update 2015年5月14日 下午6:40:01
	* @version V1.0
	*/
	private String expressArrivaId;
	
	
	/**
	* @fields waybillNo
	* @author 218381-foss-lijie
	* @update 2015年5月15日 上午10:49:21
	* @version V1.0
	*/
	private String waybillNo;
	
	
	/**
	* @description 获取运单号
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 上午10:49:29
	*/
	public String getWaybillNo() {
		return waybillNo;
	}

	
	/**
	* @description 设置运单号
	* @param waybillNo
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 上午10:49:37
	*/
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	* @description 获取id
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月14日 下午6:40:20
	*/
	public String getExpressArrivaId() {
		return expressArrivaId;
	}

	
	/**
	* @description 设置id
	* @param expressArrivaId
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月14日 下午6:40:27
	*/
	public void setExpressArrivaId(String expressArrivaId) {
		this.expressArrivaId = expressArrivaId;
	}
	
}
