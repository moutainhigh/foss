package com.deppon.foss.print.labelprint.impl.godex;

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
 * @author lifanghong
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

	//godex打印机进行打印操作
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
//		ClassLoader cl = AirWayBillLabelPrintWorker.class.getClassLoader();
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
			
			printStrTemplate.append("^Y96,N,8,1\r\n");
			printStrTemplate.append("~MDEL\r\n");
			printStrTemplate.append("^L\r\n");
			printStrTemplate.append("^E10\r\n");
			printStrTemplate.append("^R150\r\n");
			
			printStrTemplate.append("^S3\n");//设置打印速度

			printStrTemplate.append("Le,230,1,232,701\r\n");//第一条竖线
			
			printStrTemplate.append("AZ,204,40,1,1,1,1,德邦物流\r\n");//打印公司LOGO
			printStrTemplate.append("Le,170,16,172,210\r\n");//LOGO下面的竖线
			printStrTemplate.append("AE,165,30,1,1,1,1,"+optusernum+"\r\n");//工号
			printStrTemplate.append("AE,133,10,1,1,1,1,"+printDateStr+"\r\n");//打印日期
			printStrTemplate.append("BQ,212,220,2,5,110,1,0,"+barcode+"\r\n");//条码
			
			printStrTemplate.append("AZ,454,30,2,2,2,1,证件包专用卡\r\n");
			printStrTemplate.append("Le,397,1,399,701\r\n");//第一条竖线
			
			printStrTemplate.append("AZ,372,30,1,1,1,1,所属车队：\r\n");
			printStrTemplate.append("AZ,372,170,1,1,1,1," + team + "\r\n");
			
			printStrTemplate.append("AZ,322,60,1,1,1,1,车牌号：\r\n");
			
			
//			printStrTemplate.append("AZ,322,170,1,1,1,1," + carno + "\r\n");
            char[] outPackingArray = carno.toCharArray();
			
			for (int j = 0; j < outPackingArray.length; j++) {
				int k = 175+j*20;
				// 由于中文的编码区间为：0x4e00--0x9fbb，故Java判断一个字符串是否有中文是利用Unicode编码来判断，
				if ((outPackingArray[j] >= 0x4e00) && (outPackingArray[j] <= 0x9fbb)) {
					printStrTemplate.append("AZ,322,"+k+",1,1,1,1,"+outPackingArray[j]+"\r\n");//总件数 包装
				} else {
					if(j==0){
						printStrTemplate.append("AE,332,175,1,1,1,1,"+outPackingArray[j]+"\r\n");//总件数 包装
					}else{
						printStrTemplate.append("AE,332,"+k+",1,1,1,1,"+outPackingArray[j]+"\r\n");//总件数 包装
					}
				}
			}
			
			
			
			
			printStrTemplate.append("AZ,272,90,1,1,1,1,组别：\r\n");
			printStrTemplate.append("AZ,272,170,1,1,1,1," +group+ "\r\n");	
		
			printStrTemplate.append("E\n");//设置打印头温度
			printStrTemplate.append("~MDEL\n");//取消打印回转功能
		
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
