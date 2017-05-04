package com.deppon.foss.print.labelprint.impl.zebra;

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
		cmdBuilder.append("N\r\n");
		cmdBuilder.append("Q560,20").append("\r\n");
		cmdBuilder.append("q800").append("\r\n");
		cmdBuilder.append("S6\r\n");//设置打印速度
		cmdBuilder.append("LO25,20,750,3").append("\r\n");
		cmdBuilder.append("LO25,150,750,3").append("\r\n");
		cmdBuilder.append("LO25,280,750,3").append("\r\n");
		cmdBuilder.append("LO25,410,750,3").append("\r\n");
		cmdBuilder.append("LO25,540,750,3").append("\r\n");
		cmdBuilder.append("LO25,20,3,520").append("\r\n");
		cmdBuilder.append("LO775,20,3,520").append("\r\n");
		cmdBuilder.append("LO205,280,3,130").append("\r\n");
		cmdBuilder.append("LO520,280,3,130").append("\r\n");
		cmdBuilder.append("LO400,410,3,130").append("\r\n");
		cmdBuilder.append("A145,50,0,8,2,2,N,").append("\"").append(cnpyNm)
				.append("\"").append("\r\n");
		cmdBuilder.append("A45,155,0,8,1,1,N,\"货运单号/AWB.NO\"").append("\r\n");
		cmdBuilder.append("A215,205,0,5,1,1,N,").append("\"").append(waybillNo)
				.append("\"").append("\r\n");
		cmdBuilder.append("A45,285,0,8,1,1,N,\"件数/PCS\"").append("\r\n");
		cmdBuilder.append("A75,335,0,5,1,1,N,").append("\"").append(pieces)
				.append("\"").append("\r\n");
		cmdBuilder.append("A210,285,0,8,1,1,N,\"货物重量/WTS.(kg)\"").append("\r\n");
		cmdBuilder.append("A305,335,0,5,1,1,N,").append("\"")
				.append(goodsweight).append("\"").append("\r\n");
		cmdBuilder.append("A530,285,0,8,1,1,N,\"本件重量/WTHS\"").append("\r\n");
		cmdBuilder.append("A600,335,0,5,1,1,N,").append("\"").append(pcsweight)
				.append("\"").append("\r\n");
		cmdBuilder.append("A45,415,0,8,1,1,N,\"始发站/FROM\"").append("\r\n");
		cmdBuilder.append("A75,455,0,8,2,2,N,").append("\"").append(from)
				.append("\"").append("\r\n");
		cmdBuilder.append("A425,415,0,8,1,1,N,\"目的站/TO\"").append("\r\n");
		cmdBuilder.append("A455,455,0,8,2,2,N,").append("\"").append(to)
				.append("\"").append("\r\n");
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
