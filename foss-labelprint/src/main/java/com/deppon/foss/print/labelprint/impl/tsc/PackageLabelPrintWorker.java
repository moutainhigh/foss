package com.deppon.foss.print.labelprint.impl.tsc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
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
import com.deppon.foss.print.labelprint.impl.PackageLabelPrintForm;

/**
 * 
 * @author 218392
 *
 */
public class PackageLabelPrintWorker extends LabelPrintWorker {

	@Override
	public void prepareLabelPrintForm(LabelPrintContext lblPrintContext)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepareLabelPrintService(LabelPrintContext lblPrintContext)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeExecuteLabelPrint(LabelPrintContext lblPrintContext)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void executePrintProcess(LabelPrintContext lblPrintContext)
			throws Exception {
		
		List<byte[]> cmdbytelst = printByZebra(lblPrintContext);
		
		PrintService _prtservice = findFirstPrintService(lblPrintContext);
		lblPrintContext.setmPrintService(_prtservice);
		PrintService _printService = lblPrintContext.getmPrintService();
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		for(byte[] bytes : cmdbytelst){
			DocPrintJob job = _printService.createPrintJob();
			PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
			ByteArrayInputStream str = new ByteArrayInputStream( bytes);
			Doc doc = new SimpleDoc(str, flavor, null);
			job.print(doc, pras);
		}
	}
	
	public PackageLabelPrintForm getPackageLabelPrintForm(LabelPrintContext context)
			throws Exception {
		PackageLabelPrintForm packageForm = new PackageLabelPrintForm();
		//始发地 客户手动填写的
//		String leaveCity  = (String) context.get("leaveCity");
//		packageForm.setLeaveCity(leaveCity);
//		//目的地
//		String arriveCity = (String) context.get("arriveCity");
//		packageForm.setArriveCity(arriveCity);
//		//件数
//		int goodQty = Integer.parseInt(context.get("goodQty").toString());
//		packageForm.setGoodQty(goodQty);
		//条形码
		String barCode = (String) context.get("barCode");
		packageForm.setBarCode(barCode);
		return packageForm;
	}

	//斑马打印机进行打印操作
	public List<byte[]> printByZebra(LabelPrintContext lblPrintContext) throws Exception {
		PackageLabelPrintForm labelForm =  getPackageLabelPrintForm(lblPrintContext);
		// 上边距
		labelForm.setTop(lblPrintContext.getPrtTop());
		// 左边距
		labelForm.setLeft(lblPrintContext.getPrtLeft());
		
		//读取德邦logo图片
		ClassLoader cl = AirWayBillLabelPrintWorker.class.getClassLoader();
		InputStream in = null;
		byte[] pcxBytes = new byte[4096];
		int len = 0;
		try {
			in = cl.getResourceAsStream("logo/dplogo.pcx");
			len = in.read(pcxBytes);
		} finally {
			if(in!=null){
				in.close();
			}
			else {
				
			}
		}
		
		List<byte[]> outputlist = new ArrayList<byte[]>();
		ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
		//打印指令
		StringBuffer printStrTemplate = new StringBuffer("");
		
		printStrTemplate.append("N\r\n");
		printStrTemplate.append("Q720,24\r\n");//设置标签纸高度及标签之间的间隙
		printStrTemplate.append("q440\r\n");//设置标签纸宽度
		printStrTemplate.append("S6\n");//设置打印速度
		printStrTemplate.append("D7\n");//设置打印头温度
		printStrTemplate.append("JB\n");//取消打印回转功能
		printStrTemplate.append("ZB\n");//从标签右下角开始打印
		
//		printStrTemplate.append("R200,25\n");//设置打印左边距和上边距
		printStrTemplate.append("R" + labelForm.getLeft() + "," + labelForm.getTop() + "\n");//设置打印左边距和上边距
		printStrTemplate.append("X035,1,16,455,700\r\n");//画矩形框
		printStrTemplate.append("LO35,350,420,9\r\n");//框内横线
		printStrTemplate.append("LO175,1,9,700\r\n");//第一条竖线
		printStrTemplate.append("LO315,1,9,350\r\n");//第一条竖线
//		printStrTemplate.append("A415,40,1,8,2,2,N,\"德邦快递\"\r\n");//打印"德邦快递"四个字
		//读取德邦LOGO图片(TSC打印机打不出图片)
//		printStrTemplate.append("GM\"dplogo\"").append(len).append("\r\n");
		printStrTemplate.append("A430,56,1,8,3,2,N,\"德邦快递\"\r\n");//在图片下打印上"快递"两个字
		
		printStrTemplate.append("A298,20,1,8,1,1,N,\"始发地：\"\r\n");//始发地
		printStrTemplate.append("A158,20,1,8,1,1,N,\"目的地：\"\r\n");//目的地
		printStrTemplate.append("A158,370,1,8,1,1,N,\"件数：\"\r\n");//件数
		printStrTemplate.append("B380,395,1,1,2,24,90,N,\""+labelForm.getBarCode()+"\"\r\n");//条形码
		//打印包编码
		printStrTemplate.append("A260,395,1,2,2,2,N,\""+labelForm.getBarCode()+"\"\r\n");//条形码
		
		baos.write(printStrTemplate.toString().getBytes("GBK"));
		baos.write("\r\n".getBytes());
		
		//打印德邦LOGO图片(TSC打印不出图片)
		StringBuffer end = new StringBuffer();
//		end.append("GG350,60,\"DPLOGO\"").append("\r\n");
		end.append("P1\r\n");//打印一份
		baos.write(end.toString().getBytes());
		baos.close();
		outputlist.add(baos.toByteArray());
		
		return outputlist;
	}
}
