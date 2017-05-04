package com.deppon.foss.print.jasper;

/**
 * 
 * @author niujian
 *
 */
public interface MultiJasperDataSource {

	JasperDataSource[] createJasperDataSources(JasperContext jasperContext);
}
