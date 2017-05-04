package com.deppon.foss.prt;

import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperPrintManager;

public class DemoRpt {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			JasperContext ctx = new JasperContext();
			ctx.setPrtkey("demo");
			ctx.put("number", "1,2,3,3,4,5,5");
			JasperPrintManager manager = new JasperPrintManager(ctx);
			manager.processPrintResultInPreviewer(ctx);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

}
