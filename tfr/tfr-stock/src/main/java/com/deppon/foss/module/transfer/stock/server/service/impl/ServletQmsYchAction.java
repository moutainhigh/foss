package com.deppon.foss.module.transfer.stock.server.service.impl;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deppon.foss.module.transfer.stock.api.server.service.IQmsYchService;
import com.deppon.foss.module.transfer.stock.api.server.service.IServletQmsYchService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InnerPickupCurrDeptEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.QmsYchEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.QmsYchExceptionReportEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.RequestParammPojo;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillArrideptList;


/**
 * @author 218381
 *
 */
public class ServletQmsYchAction implements IServletQmsYchService{
	

	
	private IQmsYchService qmsYchService;

	public void setQmsYchService(IQmsYchService qmsYchService) {
		this.qmsYchService = qmsYchService;
	}

	/**
	 * 日志实例
	* @fields logger
	* @author 218381
	* @update 2015年4月22日 上午9:28:11
	* @version V1.0
	*/
	private static final Logger logger = LoggerFactory.getLogger(ServletQmsYchAction.class);

	//运单大于90的货物
	@Override
	public @ResponseBody List<QmsYchEntity> queryBillTimeBigNinetyDay() throws IOException {
		List<QmsYchEntity> list = null;
		list= qmsYchService.queryBillTimeBigNinetyDay();
		return list;
	}

	//是否在丢货小组 
	@Override
	public @ResponseBody QmsYchExceptionReportEntity isLoseGroup(@RequestBody RequestParammPojo pojo)
			throws IOException {
			logger.info("获取的参数id:"+pojo.getWaybillNOs());
			RequestParammPojo p = new RequestParammPojo();
			p.setWaybillNOs(pojo.getWaybillNOs());
			QmsYchExceptionReportEntity entity= qmsYchService.isLoseGroup(p.getWaybillNOs());
			return entity;
	}

	//内部带货同步所处部门
	@Override
	public @ResponseBody List<InnerPickupCurrDeptEntity> innerPickupCurrDept()
			throws IOException {
		List<InnerPickupCurrDeptEntity> list = null;
		list= qmsYchService.innerPickupCurrDept();
		return list;
	}
	
	//异常货同步未处理的单号到达部门
	@Override
	public @ResponseBody WaybillArrideptList queryWaybillArridept(@RequestBody List<String> list)
			throws IOException {
		logger.error("queryWaybillArridept方法获取的运单数量为: "+list.size());
		WaybillArrideptList waybillArrideptList = null;
		waybillArrideptList = qmsYchService.queryWaybillArridept(list);
		return waybillArrideptList;
	}
	


}
