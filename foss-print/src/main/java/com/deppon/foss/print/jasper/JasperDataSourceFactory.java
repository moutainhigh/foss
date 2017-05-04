package com.deppon.foss.print.jasper;

import com.deppon.foss.print.util.ObjectCreator;

/**
 * 
 * @author niujian
 *
 */
public class JasperDataSourceFactory {

	public static Object createReportDataSource(JasperContext jasperContext) throws Exception {
		String dsclass = jasperContext.getDatasourceclz();
		if(jasperContext.getClassLoader()!=null){
			return ObjectCreator.createObect(dsclass,jasperContext.getClassLoader());
		}
		else {
			return ObjectCreator.createObject(dsclass);
		}
	}
	
}
