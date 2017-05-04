package com.deppon.foss.prt.demo2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.print.jasper.MultiJasperDataSource;

public class Demo2DataSource implements MultiJasperDataSource {

	@Override
	public JasperDataSource[] createJasperDataSources(
			JasperContext jasperContext) {
		
		String ids = (String)jasperContext.get("number");
		String[] idarr = ids.split(",");
		
		List<JasperDataSource> lst = new ArrayList<JasperDataSource>();
		for(String id : idarr ){
			lst.add(new MyJasperDataSource(id));
		}
		
		return lst.toArray(new JasperDataSource[0]);
	}

	
	class MyJasperDataSource implements JasperDataSource {
		
		private String id=null;
		public MyJasperDataSource(String pId){
			id = pId;
		}
		
		@Override
		public List<Map<String,Object>> createDetailDataSource(JasperContext jasperContext) {
			List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
			int loopmax = 10;
			for (int i = 0; i < loopmax; i++) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("id", i + "__" + id);

				lst.add(m);
			}

			return lst;
		}
		
		@Override
		public Map<String,Object> createParameterDataSource(JasperContext jasperContext) {
			Map<String,Object> parameter = new HashMap<String,Object>();
			
			parameter.put("name","niujian"+id);
			return parameter;
		}
	}
}
