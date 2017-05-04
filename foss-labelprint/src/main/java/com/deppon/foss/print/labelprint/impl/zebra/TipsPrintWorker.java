package com.deppon.foss.print.labelprint.impl.zebra;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.print.labelprint.LabelPrintContext;
import com.deppon.foss.print.labelprint.LabelPrintWorker;
import com.deppon.foss.print.labelprint.util.Log;

 
/**
 * Zebra888TT Comm Label Print Program
 * @author niujian
 */
public class TipsPrintWorker extends LabelPrintWorker {
 
	@Override
	public void prepareLabelPrintForm(LabelPrintContext lblPrintContext)
			throws Exception {
		String number = (String)lblPrintContext.get("number"); //件数
		String agentWaybillNos = (String)lblPrintContext.get("agentWaybillNos");//代理单号
		String[] arrayAgentWaybillNos = agentWaybillNos.split(",");
		String fontSizeX = "";
		String fontSizeY = "";
		int length = arrayAgentWaybillNos.length;
		int cols = 1;//每行的单数
		int interval = 0;//每行间隔
		int marginLeft = 0;//左边距
		if(length <= 2){
			fontSizeX = "2";
			fontSizeY = "2";
			cols = 1;
			interval = 111;//行间隔111
			marginLeft = 150;
		}else if(length <=6 ){
			fontSizeX = "2";
			fontSizeY = "1";
			cols = 2;
			interval = 70;//行间隔70
			marginLeft = 130;
		}else if(length <=12 ){//6个以上每三个一行
			fontSizeX = "1";
			fontSizeY = "1";
			cols = 3;
			interval = 55;//行间隔55
			marginLeft = 35;
		}else {
			throw new Exception("数量过多不能打印！");
		}
		List<String> printAgentWaybills = new ArrayList<String>();
		String temp = "";
		for(int i=0;i<length;i++){
			temp = temp + arrayAgentWaybillNos[i] + ",";
			if((i+1)%cols==0){
				temp = temp.substring(0,temp.lastIndexOf(","));
				printAgentWaybills.add(temp);
				temp = "";
			}else if((i+1)%cols!=0 && i == length-1){
				temp = temp.substring(0,temp.lastIndexOf(","));
				printAgentWaybills.add(temp);
				temp = "";
			}
		}
		//打印指令
		StringBuffer printStrTemplate = new StringBuffer("");
		printStrTemplate.append("N\r\n");
		printStrTemplate.append("Q740,24\r\n");//设置标签纸高度及标签之间的间隙
		printStrTemplate.append("q450\r\n");//设置标签纸宽度
		printStrTemplate.append("S6\n");//设置打印速度
		printStrTemplate.append("D7\n");//设置打印头温度
		printStrTemplate.append("JB\n");//取消打印回转功能
		printStrTemplate.append("ZB\n");//从标签右下角开始打印
		int left = lblPrintContext.getPrtLeft();
		int top = lblPrintContext.getPrtTop();
		printStrTemplate.append("R" + left + "," + top + "\n");//设置打印左边距和上边距
		printStrTemplate.append("X30,10,4,460,720\r\n");//画矩形框

		printStrTemplate.append("A130,60,1,8,1,1,N,\"共"+number+"件为同一批货物，收货人为同一人，\"\r\n");

		printStrTemplate.append("A80,60,1,8,1,1,N,\"烦请同时派送，谢谢！\"\r\n");
	//	printStrTemplate.append("A350,105,1,8,3,3,N,\"998899885\"\r\n");
		int intervalNum = 350;
		for (int i = 0; i < printAgentWaybills.size(); i++) {
			intervalNum = 350 - interval * i;
			printStrTemplate.append("A"+intervalNum+","+marginLeft+",1,8,"+fontSizeX+","+fontSizeY+",N,\""+printAgentWaybills.get(i)+"\"\r\n");
		}
	//	printStrTemplate.append("A350,105,1,8,3,3,N,\""+agentWaybillNos+"\"\r\n");
		printStrTemplate.append("A435,60,1,8,1,1,N,\"快递小哥您好：该货与\"\r\n");
		printStrTemplate.append("P1\r\n");//打印一份
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
