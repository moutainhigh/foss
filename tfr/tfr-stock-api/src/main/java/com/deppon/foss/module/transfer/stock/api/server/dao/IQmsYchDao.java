package com.deppon.foss.module.transfer.stock.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.stock.api.shared.domain.InnerPickupCurrDeptEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.IsLoseGroupEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.QmsYchEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillArridept;



public interface IQmsYchDao {
	
	/**
	* @description FOSS系统库存中开单>=90天的运单信息
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年6月1日 上午9:47:14
	*/
	List<QmsYchEntity> queryBillTimeBigNinetyDay(Date beginDate,Date endDate);
	
	
	/**
	* @description 查询是否在零担丢货小组或者快递丢货小组
	* @param waybillNo
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年6月1日 上午11:24:28
	*/
	IsLoseGroupEntity isLoseGroup(String waybillNo);
	
	
	/**
	* @description 内部带货同步所处部门
	* @param waybillNo
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年6月1日 下午3:49:09
	*/
	List<InnerPickupCurrDeptEntity> innerPickupCurrDept();
	
	/**
	* @description 根据部门code判断部门是否是驻地营业部
	* @param orgCode
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月17日 下午2:18:26
	*/
	String isStation(String orgCode);
	
	
	/**
	* @description 根据驻地营业部code查询对应外场code
	* @param orgCode
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月17日 下午2:19:21
	*/
	String selectTransferByOrgCode(String orgCode);
	
	
	/**
	* @description 异常货同步未处理的单号到达部门
	* @param list
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年11月9日 上午9:45:06
	*/
	List<WaybillArridept> queryWaybillArridept(List<String> list);
}
