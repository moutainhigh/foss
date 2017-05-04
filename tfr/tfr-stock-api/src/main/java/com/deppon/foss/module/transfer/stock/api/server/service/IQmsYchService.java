package com.deppon.foss.module.transfer.stock.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InnerPickupCurrDeptEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.QmsYchEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.QmsYchExceptionReportEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillArrideptList;


/**
* @description 异常货接口
* @version 1.0
* @author 218381-foss-lijie
* @update 2015年6月1日 上午9:43:25
*/
public interface IQmsYchService extends IService {
	
	
	
	/**
	* @description FOSS系统库存中开单>=90天的运单信息
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年6月1日 上午9:28:57
	*/
	List<QmsYchEntity> queryBillTimeBigNinetyDay();
	
	
	/**
	* @description 查询是否在零担丢货小组或者快递丢货小组
	* @param waybillNo
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年6月1日 上午11:13:16
	*/
	QmsYchExceptionReportEntity isLoseGroup(String waybillNo);
	
	
	/**
	* @description 内部带货同步所处部门
	* @param waybillNo
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年6月1日 下午3:45:32
	*/
	List<InnerPickupCurrDeptEntity> innerPickupCurrDept();
	
	
	
	/**
	* @description 异常货同步未处理的单号到达部门
	* @param list
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年11月9日 上午9:34:57
	*/
	WaybillArrideptList queryWaybillArridept(List<String> list);
}
