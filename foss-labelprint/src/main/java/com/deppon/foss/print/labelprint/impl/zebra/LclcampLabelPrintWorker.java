package com.deppon.foss.print.labelprint.impl.zebra;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import com.deppon.foss.print.labelprint.impl.LabelPrintForm;

public class LclcampLabelPrintWorker extends LabelPrintWorker{
	@Override
	public void prepareLabelPrintForm(LabelPrintContext lblPrintContext)
			throws Exception {
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
	
	public LabelPrintForm getLabelPrintForm(LabelPrintContext context)
			throws Exception {
		// 渠道单号
		String channelNumber = (String)context.get("channelNumber");
		if (isNull(channelNumber)) {
			throw new Exception("该渠道单号无约车，无法进行标签打印");
		}
		// 标签打印信息对象
		LabelPrintForm form = new LabelPrintForm();
		// 上边距
		form.setTop(context.getPrtTop());
		// 左边距
		form.setLeft(context.getPrtLeft());
		// 渠道单号
		form.setChannelNumber(channelNumber);
		
		return form;
	}
	//斑马打印机进行打印操作
	public List<byte[]> printByZebra(LabelPrintContext lblPrintContext) throws Exception 
		{
			LabelPrintForm labelForm =  getLabelPrintForm(lblPrintContext);
			List<byte[]> outputlist = new ArrayList<byte[]>();
			ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
			//打印指令    
			StringBuffer printStrTemplate = new StringBuffer("");
			printStrTemplate.append("N\r\n");
			printStrTemplate.append("Q720,24\r\n");//设置标签纸高度及标签之间的间隙740
			printStrTemplate.append("q440\r\n");//设置标签纸宽度
			printStrTemplate.append("S6\n");//设置打印速度
			printStrTemplate.append("D7\n");//设置打印头温度
			printStrTemplate.append("JB\n");//取消打印回转功能
			printStrTemplate.append("ZB\n");//从标签右下角开始打印
			printStrTemplate.append("R" + labelForm.getLeft() + "," + labelForm.getTop() + "\n");//设置打印左边距和上边距
			printStrTemplate.append("A370,230,1,8,4,3,N,\"拼箱营\"\r\n");//打印英文
			printStrTemplate.append("A220,300,1,4,1,1,N,\"CONSOLWIN\"\r\n");//打印英文
			printStrTemplate.append("B170,255,1,1,2,12,75,B,\""+labelForm.getChannelNumber()+"\"\r\n");//条码
			baos.write(printStrTemplate.toString().getBytes("GBK"));
			baos.write("\r\n".getBytes());
			StringBuffer end = new StringBuffer();
			end.append("P1\r\n");
			baos.write(end.toString().getBytes());
			baos.close();
			outputlist.add(baos.toByteArray());
			return outputlist;
		}
}
