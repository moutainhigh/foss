package com.deppon.foss.module.transfer.stock.api.server.service;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deppon.foss.module.transfer.stock.api.shared.domain.InnerPickupCurrDeptEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.QmsYchEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.QmsYchExceptionReportEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.RequestParammPojo;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillArrideptList;

@Scope("prototype")
@Controller
@RequestMapping("/fossToYchService")
public interface IServletQmsYchService {

	
	
	/**
	* @description 运单等于90的货物
	* @return
	* @throws IOException
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月22日 上午9:28:11
	*/
	@RequestMapping(value ="/queryBigNinetyDay.tfr",method=RequestMethod.POST)
	public @ResponseBody List<QmsYchEntity> queryBillTimeBigNinetyDay() throws IOException;
	
	/**
	* @description 是否在丢货小组 
	* @param pojo
	* @return
	* @throws IOException
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月23日 上午11:15:19
	*/
	@RequestMapping(value ="/isLoseGroup.tfr",method=RequestMethod.POST)
	public @ResponseBody QmsYchExceptionReportEntity 
		isLoseGroup(RequestParammPojo pojo) throws IOException;
	
	/**
	* @description 内部带货同步所处部门
	* @return
	* @throws IOException
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月25日 下午3:27:57
	*/
	@RequestMapping(value ="/innerPickupCurrDept.tfr",method=RequestMethod.POST)
	public @ResponseBody List<InnerPickupCurrDeptEntity> 
		innerPickupCurrDept() throws IOException;
	
	
	/**
	* @description 异常货同步未处理的单号到达部门
	* @param list
	* @return
	* @throws IOException
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年11月9日 上午9:31:12
	*/
	@RequestMapping(value ="/waybillArridept.tfr",method=RequestMethod.POST)
	public @ResponseBody WaybillArrideptList 
		queryWaybillArridept(List<String> list) throws IOException;
	
}