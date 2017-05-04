package com.deppon.foss.print.labelprint.impl.godex;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

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
 * Zebra888TT Air Way Bill Label Print Program
 * 
 * @author niujian
 */
public class AirWayBillLabelPrintWorker extends LabelPrintWorker {

	@Override
	public void prepareLabelPrintForm(LabelPrintContext lblPrintContext)
			throws Exception {
		// String cnpyCd = "Guoji";
		// String cnpyNm = "中国国际航空公司";
		// String waybillNo = "30087643";
		// String pieces = "10";
		// String goodsweight = "254";
		// String pcsweight = "26";
		// String from = "上海";
		// String to = "呼和浩特";
		// String command = getELPCommand(cnpyCd, cnpyNm, waybillNo, pieces,
		// goodsweight, pcsweight, from, to);
		// lblPrintContext.setPrintForm(command);
	}

	@Override
	public void prepareLabelPrintService(LabelPrintContext lblPrintContext)
			throws Exception {
	}

	@Override
	public void beforeExecuteLabelPrint(LabelPrintContext lblPrintContext)
			throws Exception {
	}

	/**
	 * @param context
	 * param for label print 斑马打印机进行打印操作
	 * lblprtworker:AirWayBillLabelPrintWorker 航空公司
	 * 编码cnpycode:HH,SD,SZ,XM,DF,GH,NH
	 * 航空公司名称cnpyname:海南航空,山东航空,深圳航空,厦门航空,中国东方航空公司,中国国际航空公司,中国南方航空公司
	 * 货运单号 awbno
	 * 件数 pieces 
	 * 货物重量 goodsweight 
	 * 本件重量 pcsweight 
	 * 始发站 from
	 * 目的站 to
	 * 打印份数size
	 */
	public void printByZebra(LabelPrintContext lblPrintContext)
			throws Exception {
	}

	@Override
	public void executePrintProcess(LabelPrintContext lblPrintContext)
			throws Exception {

		String cnpyCd = (String)lblPrintContext.get("cnpycode");//"Dongfang";
		if(cnpyCd==null || "".equals(cnpyCd) || "null".equals(cnpyCd)){
			throw new Exception("[航空公司选择不正确]");
		}
		String cnpyNm = (String)lblPrintContext.get("cnpyname");//"中国东方航空公司";
		if(cnpyNm==null || "".equals(cnpyNm) || "null".equals(cnpyNm)){
			throw new Exception("[航空公司名称没有输入]");
		}
		
		String waybillNo = (String)lblPrintContext.get("awbno");//"90387643";
		if(waybillNo==null || "".equals(waybillNo) || "null".equals(waybillNo)){
			throw new Exception("[货运单号没有输入]");
		}
		
		String pieces = (String)lblPrintContext.get("pieces");//"21";
		try{
			Integer.parseInt(pieces);
		}catch(Exception e){ throw new Exception("[打印份数输入不正确，请输入数字]");}
		
		
		String goodsweight = (String)lblPrintContext.get("goodsweight");//"423";
		if(goodsweight==null || "".equals(goodsweight) || "null".equals(goodsweight)){
			throw new Exception("[货物重量没有输入]");
		}
		
		String pcsweight = (String)lblPrintContext.get("pcsweight");//"17";
		if(pcsweight==null || "".equals(pcsweight) || "null".equals(pcsweight)){
			throw new Exception("[本件重量没有输入]");
		}
		
		String from = (String)lblPrintContext.get("from");//"上海";
		if(from==null || "".equals(from) || "null".equals(from)){
			throw new Exception("[始发站没有输入]");
		}
		
		String to = (String)lblPrintContext.get("to");//"重庆";
		if(to==null || "".equals(to) || "null".equals(to)){
			throw new Exception("[目的站没有输入]");
		}
		
		int size = 1;
		try{
			size = Integer.parseInt((String)lblPrintContext.get("size"));
		}catch(Exception e){
			size = 1;
		}
		
		byte[] cmdBytes = getELPCommand(cnpyCd, cnpyNm, waybillNo, pieces,
				goodsweight, pcsweight, from, to, size);
		PrintService _prtservice = findFirstPrintService(lblPrintContext);
		lblPrintContext.setmPrintService(_prtservice);
		
		PrintService _printService = lblPrintContext.getmPrintService();

		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		DocPrintJob job = _printService.createPrintJob();
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		ByteArrayInputStream str = new ByteArrayInputStream( cmdBytes);
		Doc doc = new SimpleDoc(str, flavor, null);
		job.print(doc, pras);
	}

	public static void main(String[] args) throws Exception {
//		String cnpyCd = "Guoji";
//		String cnpyNm = "中国国际航空公司";
//		String waybillNo = "30087643";
//		String pieces = "10";
//		String goodsweight = "254";
//		String pcsweight = "26";
//		String from = "上海";
//		String to = "呼和浩特";
//		byte[] cmdBytes = getELPCommand(cnpyCd, cnpyNm, waybillNo, pieces,
//				goodsweight, pcsweight, from, to);

		// System.out.println(command);
	}

	private static byte[] getELPCommand(String cnpyCd, String cnpyNm,
			String waybillNo, String pieces, String goodsweight,
			String pcsweight, String from, String to,int size) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
		StringBuilder cmdBuilder = new StringBuilder();
		cmdBuilder.append("~MDEL\r\n");
		cmdBuilder.append("^L\r\n");
		cmdBuilder.append("^E10\r\n");
		cmdBuilder.append("^S3\n");//设置打印速度
		cmdBuilder.append("^R70\r\n");

		

	

		cmdBuilder.append("Lo,00,25,740,27\r\n");//第一条横线
		cmdBuilder.append("Lo,00,155,740,157\r\n");//第二条横线
		cmdBuilder.append("Lo,00,285,740,287\r\n");//第三条横线
		cmdBuilder.append("Lo,00,415,740,417\r\n");//第四条横线
		cmdBuilder.append("Lo,00,545,740,547\r\n");//第五条横线
		
		cmdBuilder.append("Lo,00,25,2,545\r\n");//第一条竖线
		cmdBuilder.append("Lo,197,285,199,415\r\n");//第二条竖线
		cmdBuilder.append("Lo,495,285,497,415\r\n");//第三条竖线
		cmdBuilder.append("Lo,385,415,387,547\r\n");//第四条竖线
		cmdBuilder.append("Lo,740,25,742,545\r\n");//第五条竖线
		
		if(cnpyCd != null && "CZ".equals(cnpyCd)){
			cmdBuilder.append("AZ,110,58,2,2,1,0,").append(cnpyNm).append("\r\n");
			cmdBuilder.append("AZ,110,88,2,2,1,0,").append(cnpyNm).append("\r\n");
		}else{
			cmdBuilder.append("AZ,40,78,2,2,1,0,").append(cnpyNm).append("\r\n");
		}
		cmdBuilder.append("AZ,10,160,1,1,1,0,货运单号").append("\r\n");
		cmdBuilder.append("AE,100,150,1,1,1,0,/AWB.NO").append("\r\n");
		cmdBuilder.append("AE,120,170,2,2,1,0,").append(waybillNo)
				.append("\r\n");
		
		cmdBuilder.append("AZ,10,295,1,1,1,0,件数").append("\r\n");
		cmdBuilder.append("AE,60,285,1,1,1,0,/PCS").append("\r\n");
		cmdBuilder.append("AE,80,310,2,2,1,0,").append(pieces)
				.append("\r\n");
		
		cmdBuilder.append("AZ,207,295,1,1,1,0,货物重量").append("\r\n");
		cmdBuilder.append("AE,302,285,1,1,1,0,/WTS.(KG)").append("\r\n");
		cmdBuilder.append("AE,280,310,2,2,1,0,")
				.append(goodsweight).append("\r\n");
		cmdBuilder.append("AZ,525,295,1,1,1,0,本件重量").append("\r\n");
		cmdBuilder.append("AE,620,285,1,1,1,0,/WTHS").append("\r\n");
		cmdBuilder.append("AE,550,330,2,2,1,0,").append(pcsweight)
				.append("\r\n");
		
		cmdBuilder.append("AZ,10,420,1,1,1,0,始发站").append("\r\n");
		cmdBuilder.append("AE,85,415,1,1,1,0,/FROM").append("\r\n");
		cmdBuilder.append("AZ,20,450,2,2,2,0,").append(from)
				.append("\r\n");
		
		cmdBuilder.append("AZ,400,420,1,1,1,0,目的站").append("\r\n");
		cmdBuilder.append("AE,475,415,1,1,1,0,/TO").append("\r\n");
		cmdBuilder.append("AZ,420,450,2,2,2,0,").append(to)
				.append("\r\n");
		
		cmdBuilder.append("GK\"AALOGO\"").append("\r\n").append("GK\"AALOGO\"")
				.append("\r\n");

		ClassLoader cl = AirWayBillLabelPrintWorker.class.getClassLoader();
		InputStream in = null;
		byte[] pcxBytes = new byte[4096];
		int len = 0;
		try {
			in = cl.getResourceAsStream("logo/" + cnpyCd + ".pcx");
			if(in!=null){
				len = in.read(pcxBytes);
			}
		} finally {
			if(in!=null){
				in.close();
			}
			else {
				//throw new Exception("["+cnpyCd+"所选择的航空公司没有找到对应的logo图片]");
			}
		}
		
		StringBuffer end = new StringBuffer();
		
		cmdBuilder.append("E\n");//设置打印头温度
		cmdBuilder.append("~MDEL\n");//取消打印回转功能
		
		if(len>0){
			cmdBuilder.append("GM\"AALOGO\"").append(len).append("\r\n");
			baos.write(cmdBuilder.toString().getBytes("GBK"));
			baos.write(pcxBytes);
			baos.write("\r\n".getBytes());
			end.append("GG30,25,\"AALOGO\"").append("\r\n");
		}
		else {
			baos.write(cmdBuilder.toString().getBytes("GBK"));
		}
		
		end.append("P"+size);
		end.append("\r\n");
		baos.write(end.toString().getBytes());
		baos.close();
		return baos.toByteArray();
	}
}
