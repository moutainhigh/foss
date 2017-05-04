package com.deppon.foss.print.labelprint.impl.godex;

import com.deppon.foss.print.labelprint.LabelPrintContext;
import com.deppon.foss.print.labelprint.LabelPrintWorker;

/**
 * Zebra888TT Whole Car Label Print Program
 * @author lifanghong
 */
public class WholeCarLabelPrintWorker extends LabelPrintWorker {
 
	
	/**
	 *	 lblprtworker:"WholeCarLabelPrintWorker",
	  	 prtpiece:"1",
	  	 destination:"上海青浦",
	  	 number:"01234567",
	  	 departure:"广州东平营业部",
	  	 arrival:"上海派送部",
	  	 username:"张三",
	  	 datetime:"2012-10-29" 
	 */
	@Override
	public void prepareLabelPrintForm(LabelPrintContext lblPrintContext)
			throws Exception {
		
		int left = lblPrintContext.getPrtLeft();
		int top = lblPrintContext.getPrtTop();
		
		StringBuffer printStrTemplate = new StringBuffer();
		printStrTemplate.append("~MDEL\r\n");
		printStrTemplate.append("^L\r\n");
		printStrTemplate.append("^E10\r\n");
		printStrTemplate.append("^S3\n");//设置打印速度
		printStrTemplate.append("^R230\r\n");
		printStrTemplate.append("H27,6,1,1,380,700,2\r\n");//画矩形框
		
		
		printStrTemplate.append("Lo,302,7,305,700\r\n");//第二条横线
		printStrTemplate.append("Lo,232,7,237,700\r\n");//第二条横线
		printStrTemplate.append("Lo,162,7,167,700\r\n");//第三条横线
		printStrTemplate.append("Lo,92,7,94,700\r\n");//第四条横线
		printStrTemplate.append("Lo,27,196,400,198\r\n");//第四条横线
		
		
		printStrTemplate.append("AZ,380,250,2,3,2,1,签收单\r\n");
		printStrTemplate.append("AZ,285,13,1,2,1,1,目的站\r\n");
		printStrTemplate.append("AZ,285,230,1,2,2,1,"+(String)lblPrintContext.get("destination")+"\r\n");
		printStrTemplate.append("AZ,220,13,1,2,1,1,单号\r\n");
		printStrTemplate.append("AE,225,230,1,1,1,1,"+(String)lblPrintContext.get("number")+"\r\n");
		printStrTemplate.append("AZ,145,13,1,2,2,1,始发部门\r\n");
		printStrTemplate.append("AZ,145,230,1,2,1,1,"+(String)lblPrintContext.get("departure")+"\r\n");
		
		printStrTemplate.append("AZ,73,13,1,2,2,1,到达部门\r\n");
		
		printStrTemplate.append("AZ,73,230,1,2,2,1,"+(String)lblPrintContext.get("arrival")+"\r\n");
		
		printStrTemplate.append("AZ,10,10,1,1,1,1,"+(String)lblPrintContext.get("departure")+(String)lblPrintContext.get("username")+"\r\n");
		printStrTemplate.append("AE,30,450,1,1,1,1,"+(String)lblPrintContext.get("datetime")+"\r\n");
		int prtpiece = Integer.parseInt((String)lblPrintContext.get("prtpiece"));
		printStrTemplate.append("P"+prtpiece+"\r\n");
		
		printStrTemplate.append("E\n");//设置打印头温度
		printStrTemplate.append("~MDEL\n");//取消打印回转功能
		
		lblPrintContext.setPrintForm(printStrTemplate.toString());
		lblPrintContext.setPrintFormParam(null);
	}

	@Override
	public void prepareLabelPrintService(LabelPrintContext lblPrintContext)
			throws Exception {
	}

	@Override
	public void beforeExecuteLabelPrint(LabelPrintContext lblPrintContext)
			throws Exception {
		
	}
}
