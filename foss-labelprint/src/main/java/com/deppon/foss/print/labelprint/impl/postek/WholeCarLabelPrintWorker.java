package com.deppon.foss.print.labelprint.impl.postek;

import com.deppon.foss.print.labelprint.LabelPrintContext;
import com.deppon.foss.print.labelprint.LabelPrintWorker;

/**
 * Zebra888TT Whole Car Label Print Program
 * @author niujian
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
		printStrTemplate.append("N\r\n");
		printStrTemplate.append("Q720,24\r\n");
		printStrTemplate.append("q440\r\n");
		printStrTemplate.append("S1\r\n");
		printStrTemplate.append("D7\r\n");
		printStrTemplate.append("JB\r\n");
		printStrTemplate.append("ZB\r\n");
		printStrTemplate.append("R"+left+","+top+"\r\n");
		printStrTemplate.append("X53,8,2,424,688\r\n");
		printStrTemplate.append("LO323,8,3,680\r\n");
		printStrTemplate.append("LO253,8,3,680\r\n");
		printStrTemplate.append("LO183,8,3,680\r\n");
		printStrTemplate.append("LO113,8,3,680\r\n");
		printStrTemplate.append("LO48,220,380,3\r\n");
		printStrTemplate.append("A403,250,1,8,3,3,N,\"签收单\"\r\n");
		printStrTemplate.append("A318,35,1,8,2,2,N,\"目的站\"\r\n");
		printStrTemplate.append("A318,230,1,8,2,2,N,\""+(String)lblPrintContext.get("destination")+"\"\r\n");
		printStrTemplate.append("A248,60,1,8,2,2,N,\"单号\"\r\n");
		printStrTemplate.append("A239,250,1,4,2,1,N,\""+(String)lblPrintContext.get("number")+"\"\r\n");
		printStrTemplate.append("A178,13,1,8,2,2,N,\"始发部门\"\r\n");
		printStrTemplate.append("A178,230,1,8,2,2,N,\""+(String)lblPrintContext.get("departure")+"\"\r\n");
		printStrTemplate.append("A113,13,1,8,2,2,N,\"到达部门\"\r\n");
		printStrTemplate.append("A113,230,1,8,2,2,N,\""+(String)lblPrintContext.get("arrival")+"\"\r\n");
		printStrTemplate.append("A56,10,1,8,2,1,N,\""+(String)lblPrintContext.get("departure")+(String)lblPrintContext.get("username")+"\"\r\n");
		printStrTemplate.append("A43,450,1,4,1,1,N,\""+(String)lblPrintContext.get("datetime")+"\"\r\n");
		int prtpiece = Integer.parseInt((String)lblPrintContext.get("prtpiece"));
		printStrTemplate.append("P"+prtpiece+"\r\n");
		
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
