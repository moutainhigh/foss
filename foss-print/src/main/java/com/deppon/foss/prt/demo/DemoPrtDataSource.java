package com.deppon.foss.prt.demo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.print.util.BarCode128Util;

public class DemoPrtDataSource implements JasperDataSource {

	public Map<String,Object> createParameterDataSource(JasperContext jasperContext) {
		Map<String,Object> parameter = new HashMap<String,Object>();
		
		parameter.put("name","niujian");
		
        try {
        	InputStream is = BarCode128Util.newBarCode128("123456789123456789",true);
            parameter.put("bcodeimg",is);
            is.close();
        } catch (Exception e) {
        	e.printStackTrace();
		}finally {
			
        }
		return parameter;
	}

	public List<Map<String,Object>> createDetailDataSource(JasperContext jasperContext) {

		List<Map<String,Object>> lst = new ArrayList<Map<String,Object>>();
		
		try{

			for(int i=0;i<10;i++){
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("id",i+"00000000000000000000");
				
				lst.add(m);
			}
			
		}catch (Exception e) {
			
		}finally{
			
		}
		return lst;
	}

}
