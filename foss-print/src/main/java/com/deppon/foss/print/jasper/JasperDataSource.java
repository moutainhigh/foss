package com.deppon.foss.print.jasper;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author niujian
 *
 */
public interface JasperDataSource {

	Map<String,Object> createParameterDataSource(JasperContext jasperContext) throws Exception;
	List<Map<String,Object>> createDetailDataSource(JasperContext jasperContext) throws Exception;
}
