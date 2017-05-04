package com.deppon.foss.print.labelprint.impl.godex;

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
 * @author 218392 zhangyongxue
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
		//始发地( 客户手动填写的)
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
//		printStrTemplate.append("^Y96,N,8,1\r\n");
		printStrTemplate.append("^L\r\n");//标签起始符号设定
		printStrTemplate.append("^S3\n");//设置打印速度
		printStrTemplate.append("^R" + labelForm.getLeft()+ "\r\n");//设置打印左边距
		printStrTemplate.append("~Q+" + labelForm.getTop() + "\r\n");//设置打印上边距
		
		printStrTemplate.append("H55,116,1,1,390,660,18\r\n");//画矩形框
		printStrTemplate.append("Lo,55,466,480,478\r\n");//框内横线
		printStrTemplate.append("Lo,323,116,335,466\r\n");//框内竖线
		printStrTemplate.append("Lo,188,116,200,774\r\n");//框内竖线····
		
		
		printStrTemplate.append("AZ,435,145,3,3,0,1,德邦快递\r\n");//打印公司"德邦物流"LOGO四个字
		printStrTemplate.append("AZ,320,136,2,2,0,1,始发地：\r\n");//始发地
		printStrTemplate.append("AZ,180,136,2,2,0,1,目的地：\r\n");//目的地
		printStrTemplate.append("AZ,180,486,2,2,0,1,件数：\r\n");//件数
		printStrTemplate.append("BQ,430,505,2,2,120,1,1,"+labelForm.getBarCode()+"\r\n");//条码
		
		printStrTemplate.append("E\n");//设置打印头温度
		printStrTemplate.append("~MDEL\n");//取消打印回转功能
		
		baos.write(printStrTemplate.toString().getBytes("GBK"));
		baos.write("\r\n".getBytes());
		
		//打印德邦LOGO图片
//		printStrTemplate.append("GM\"DPLOGO\"").append(len).append("\r\n");
		StringBuffer end = new StringBuffer();
//		end.append("GG380,100,\"DPLOGO\"").append("\r\n");
		end.append("P1\r\n");//打印一份
		baos.write(end.toString().getBytes());
		baos.close();
		outputlist.add(baos.toByteArray());
		
		return outputlist;
	}
}

