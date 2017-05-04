package com.deppon.foss.print.labelprint.impl.tsc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import com.deppon.foss.print.labelprint.LabelPrintContext;
import com.deppon.foss.print.labelprint.LabelPrintWorker;

/**
 * Zebra888TT Vehicle Label Print Program
 * @author niujian
 */
public class VehicleLabelPrintWorker extends LabelPrintWorker {
 
	@Override
	public void prepareLabelPrintForm(LabelPrintContext lblPrintContext)
			throws Exception {
		
		printByZebra(lblPrintContext);
	}

	@Override
	public void prepareLabelPrintService(LabelPrintContext lblPrintContext)
			throws Exception {
	}

	@Override
	public void beforeExecuteLabelPrint(LabelPrintContext lblPrintContext)
			throws Exception {
	}
	
	@Override
	public void executePrintProcess(LabelPrintContext lblPrintContext)
			throws Exception {
		
		byte[] cmdbytes = printByZebra(lblPrintContext);
		
		PrintService _prtservice = findFirstPrintService(lblPrintContext);
		lblPrintContext.setmPrintService(_prtservice);
		
		PrintService _printService = lblPrintContext.getmPrintService();

		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		DocPrintJob job = _printService.createPrintJob();
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		ByteArrayInputStream str = new ByteArrayInputStream( cmdbytes);
		Doc doc = new SimpleDoc(str, flavor, null);
		job.print(doc, pras);
	}

	//斑马打印机进行打印操作
	public byte[] printByZebra(LabelPrintContext lblPrintContext) throws Exception 
	{
		String optusernum = (String)lblPrintContext.get("optusernum");
		if(optusernum==null || "".equals(optusernum) || "null".equals(optusernum)){
			throw new Exception("没有输入员工工号，不能打印标签");
		}
		String printDateStr = (String)lblPrintContext.get("printdate");
		if(printDateStr==null || "".equals(printDateStr) || "null".equals(printDateStr)){
			throw new Exception("没有输入打印日期，不能打印标签");
		}
		
		String serialnos = (String)lblPrintContext.get("serialnos");
		if(serialnos==null || "".equals(serialnos) || "null".equals(serialnos)){
			throw new Exception("没有输入条码流水号，不能打印标签");
		}
		
		String carnos = (String)lblPrintContext.get("carnos");
		if(carnos==null || "".equals(carnos) || "null".equals(carnos)){
			throw new Exception("没有输入车辆牌照号，不能打印标签");
		}
		
		String teams = (String)lblPrintContext.get("teams");
		if(teams==null || "".equals(teams) || "null".equals(teams)){
			throw new Exception("没有输入所属车队，不能打印标签");
		}
		
		String groups = (String)lblPrintContext.get("groups");
		if(groups==null || "".equals(groups) || "null".equals(groups)){
			throw new Exception("没有输入组别，不能打印标签");
		}
		
		List<String> snlist = new ArrayList<String>(Arrays.asList(serialnos.split(",")));
		List<String> carnolist = new ArrayList<String>(Arrays.asList(carnos.split(",")));
		List<String> teamlist = new ArrayList<String>(Arrays.asList(teams.split(",")));
		List<String> grouplist = new ArrayList<String>(Arrays.asList(groups.split(",")));
		
		if(snlist.size()!=carnolist.size() ){
			throw new Exception("输入的条码流水个数与输入的车辆牌照号个数不一致，不能打印标签");
		}
		
		if(snlist.size()!=teamlist.size() ){
			throw new Exception("输入的条码流水个数与输入的所属车队个数不一致，不能打印标签");
		}
		
		if(snlist.size()!=grouplist.size()){
			throw new Exception("输入的条码流水个数与输入的组别个数不一致，不能打印标签");
		}
		
		//读取德邦logo图片
		ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
		ClassLoader cl = AirWayBillLabelPrintWorker.class.getClassLoader();
//		InputStream in = null;
		byte[] pcxBytes = new byte[4096];
//		int len = 0;
//		try {
//			in = cl.getResourceAsStream("logo/dplogo.pcx");
//			len = in.read(pcxBytes);
//		} finally {
//			if(in!=null){
//				in.close();
//			}
//			else {
//				
//			}
//		}
		
		StringBuffer printStrTemplate = new StringBuffer("");
		for (int i = 0; i < snlist.size(); i++){
			
			String barcode = snlist.get(i);
			String carno = carnolist.get(i);
			String team = teamlist.get(i);
			String group = grouplist.get(i);
			
			//打印指令
			printStrTemplate.append("N\r\n");
			printStrTemplate.append("Q720,24\r\n");//设置标签纸高度及标签之间的间隙
			printStrTemplate.append("q440\r\n");//设置标签纸宽度
			printStrTemplate.append("S6\n");//设置打印速度
			printStrTemplate.append("D7\n");//设置打印头温度
			printStrTemplate.append("JB\n");//取消打印回转功能
			printStrTemplate.append("ZB\n");//从标签右下角开始打印
			printStrTemplate.append("R" + lblPrintContext.getPrtLeft() + "," + lblPrintContext.getPrtTop() + "\n");//设置打印左边距和上边距
			
			printStrTemplate.append("LO180,1,3,710\r\n");//竖线
			
			printStrTemplate.append("A160,30,1,8,1,1,N,\"德邦物流\"\r\n");//打印公司LOGO
			printStrTemplate.append("LO120,16,3,160\r\n");//LOGO下面的竖线
			printStrTemplate.append("A100,30,1,8,1,1,N,\""+optusernum+"\"\r\n");//工号
			printStrTemplate.append("A60,20,1,4,1,1,N,\""+printDateStr+"\"\r\n");//打印日期
			printStrTemplate.append("B170,200,1,1,2,12,130,N,\""+barcode+"\"\r\n");//条码
			
			printStrTemplate.append("A430,30,1,8,2,2,N,\"证件包专用卡\"\r\n");
			printStrTemplate.append("LO350,16,3,500\r\n");//竖线
			
			printStrTemplate.append("A325,30,1,8,1,1,N,\"所属车队：\"\r\n");
			printStrTemplate.append("A325,170,1,8,1,1,N,\"" + team + "\"\r\n");
			
			printStrTemplate.append("A285,60,1,8,1,1,N,\"车牌号：\"\r\n");
			printStrTemplate.append("A285,170,1,8,1,1,N,\"" + carno + "\"\r\n");
			
			printStrTemplate.append("A245,90,1,8,1,1,N,\"组别：\"\r\n");
			printStrTemplate.append("A245,170,1,8,1,1,N,\"" + group + "\"\r\n");		

			// 打印德邦logo
			//printStrTemplate.append("GM\"DPLOGO\"").append(len).append("\r\n");
			baos.write(printStrTemplate.toString().getBytes("GBK"));
			baos.write(pcxBytes);
			baos.write("\r\n".getBytes());
			StringBuffer end = new StringBuffer();
			//end.append("GG110,20,\"DPLOGO\"").append("\r\n");
			end.append("P1\r\n");
			baos.write(end.toString().getBytes());
		}
		
		baos.close();
		return baos.toByteArray();
	}
}
