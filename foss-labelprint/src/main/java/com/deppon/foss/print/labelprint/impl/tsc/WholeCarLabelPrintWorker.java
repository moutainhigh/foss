package com.deppon.foss.print.labelprint.impl.tsc;

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
		
		printStrTemplate.append("FK\"DEPPON\"\n");
		printStrTemplate.append("FS\"DEPPON\"\n");
		printStrTemplate.append("V00,20,N,\"\"\n");
		printStrTemplate.append("V01,20,N,\"\"\n");
		printStrTemplate.append("V02,20,N,\"\"\n");
		printStrTemplate.append("V03,20,N,\"\"\n");
		printStrTemplate.append("V04,20,N,\"\"\n");
		printStrTemplate.append("V05,20,N,\"\"\n");
		printStrTemplate.append("Q720,24\n");
		printStrTemplate.append("q440\n");
		printStrTemplate.append("S6\n");
		printStrTemplate.append("D7\n");
		printStrTemplate.append("JB\n");
		printStrTemplate.append("ZB\n");
		printStrTemplate.append("R"+left+","+top+"\n");
		printStrTemplate.append("X55,8,2,424,691\n");
		printStrTemplate.append("LO325,8,3,684\n");
		printStrTemplate.append("LO255,8,3,684\n");
		printStrTemplate.append("LO185,8,3,684\n");
		printStrTemplate.append("LO115,8,3,684\n");
		printStrTemplate.append("LO53,190,373,3\n");
		printStrTemplate.append("A405,250,1,8,2,2,N,\"签收单\"\n");
		printStrTemplate.append("A305,35,1,8,1,1,N,\"目的站\"\n");
		printStrTemplate.append("A305,200,1,8,1,1,N,V00\n");
		printStrTemplate.append("A230,35,1,8,1,1,N,\"单号\"\n");
		printStrTemplate.append("A230,200,1,8,1,1,N,V01\n");
		printStrTemplate.append("A160,35,1,8,1,1,N,\"始发部门\"\n");
		printStrTemplate.append("A160,200,1,8,1,1,N,V02\n");
		printStrTemplate.append("A100,35,1,8,1,1,N,\"到达部门\"\n");
		printStrTemplate.append("A100,200,1,8,1,1,N,V03\n");
		printStrTemplate.append("A45,10,1,8,1,1,N,V04\n");
		printStrTemplate.append("A45,500,1,8,1,1,N,V05\n");
		printStrTemplate.append("FE\n");
		
		StringBuffer printStrParam = new StringBuffer();
		printStrParam.append("N\n");
		printStrParam.append("FR\"DEPPON\"\n");
		//设置双缓存模式
		printStrParam.append("rY" + "\n");
		
		int prtpiece = Integer.parseInt((String)lblPrintContext.get("prtpiece"));
		printStrParam.append("?\n");
		printStrParam.append((String)lblPrintContext.get("destination")+"\n");
		printStrParam.append((String)lblPrintContext.get("number")+"\n");
		printStrParam.append((String)lblPrintContext.get("departure")+"\n");
		printStrParam.append((String)lblPrintContext.get("arrival")+"\n");
		printStrParam.append((String)lblPrintContext.get("departure")+(String)lblPrintContext.get("username")+"\n");
		printStrParam.append((String)lblPrintContext.get("datetime")+"\n");
		printStrParam.append("P"+prtpiece+"\n");
		
		lblPrintContext.setPrintForm(printStrTemplate.toString());
		lblPrintContext.setPrintFormParam(printStrParam.toString());
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
