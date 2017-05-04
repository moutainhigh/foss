package com.deppon.foss.prt.pdapayinreport;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.print.jasper.MultiJasperDataSource;

/**
 * 打印数据绑定多条数据
 * @author 045738-foss-maojianqiang
 * @date 2012-11-3 下午12:51:55
 */
public class PDAPayinReportPrtDataSource implements MultiJasperDataSource {

	/** 
	 * 绑定多个打印数据源
	 * @author 045738-foss-maojianqiang
	 * @param 
	 * @date 2012-12-24 下午7:19:19
	 * @return 
	 * @see com.deppon.foss.print.jasper.MultiJasperDataSource#createJasperDataSources(com.deppon.foss.print.jasper.JasperContext)
	 */
	@Override
	public JasperDataSource[] createJasperDataSources(
			JasperContext jasperContext) {
		//获取前台传入缴款报表集合
		String reportNoStr = (String)jasperContext.get("reportNos");
		String[]reportNos = reportNoStr.split(",");
		//判断报表编号集合
		if(ArrayUtils.isEmpty(reportNos)){
			throw new SettlementException("传入的报表单号集合为空，不能进行打印操作！");
		}
		//设置多个数据源集合
		List<JasperDataSource> lst = new ArrayList<JasperDataSource>();
		//循环数据，调用单个数据源来，获取单个数据源
		for(String reportNo : reportNos ){
			//将单个数据源放到数据源集合中 
			lst.add(new PDAPayinReportSimplePrtDataSource(reportNo));
		}
		//判断空
		if(lst.size()==0){
			throw new SettlementException("传入单号没有要打印的数据源!");
		}
		//声明dataArray
		JasperDataSource[] dataArray = {};
		dataArray = lst.toArray(dataArray);
		//返回数据源集合
		return dataArray;
	}
	
}
