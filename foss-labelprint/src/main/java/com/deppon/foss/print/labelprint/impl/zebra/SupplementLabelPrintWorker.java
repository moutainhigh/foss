package com.deppon.foss.print.labelprint.impl.zebra;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
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

 
/**
 * Zebra888TT Comm Label Print Program
 * @author niujiang
 */
public class SupplementLabelPrintWorker extends LabelPrintWorker {
 
	@Override
	public void prepareLabelPrintForm(LabelPrintContext lblPrintContext)
			throws Exception {
		//printByZebra(lblPrintContext);
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
	
	/*
	 * param for label print 
	 * 地区1 addr1
	 * 地区2 addr2
	 * 地区3 addr3
	 * 地区4 addr4
	 * 编号1 location1
	 * 编号2 location2
	 * 编号3 location3
	 * 编号4 location4
	 * 当前用户 工号 optusernum
	 * 单号 number
	 * 打印流水号 serialnos[0001,0002]
	 * 始发站(出发城市) leavecity
	 * 目的站 destination
	 * 是否偏线 isagent[true]
	 * 目的站编码  stationnumber
	 * 最终外场编码 deptno
	 * 最终外场ID finaloutfieldid
	 * 最终外场城市名称 finaloutname
	 * 重量 weight
	 * 件数 totalpieces
	 * 包装 packing
	 * 异常货 unusual[异]
	 * 运输类型/运输性质 transtype[中文]
	 * 打印日期 printdate
	 * 是否送货 deliver[送]
	 * 货物类型 goodstype[A/B/ ]
	 * 航班早班 preassembly[早班]
	 */
	public LabelPrintForm getLabelPrintForm(LabelPrintContext context)
			throws Exception {


		// 当前用户 工号
		String userName = (String) context.get("optusernum");
		// 单号
		String number1 = (String) context.get("number");
		// 目的站
		String destination = (String) context.get("destination");
		if(null!=destination){
			String [] destinationArrays = destination.split("");
			StringBuilder bf = new StringBuilder();
			for (String value : destinationArrays) {
				if(value.equals("浉")){
					value = "氵"+"师";
				}else if(value.equals("洺")){
					value = "氵"+"名";
				}
				bf.append(value);
			}
			destination = bf.toString();
		}
		if (isNull(number1)) {
			throw new Exception("运单号不正确，无法进行标签打印");
		}
		// 标签打印信息对象
		LabelPrintForm form = new LabelPrintForm();
		// 上边距
		form.setTop(context.getPrtTop());
		// 左边距
		form.setLeft(context.getPrtLeft());
		// 目的站
		form.setDestination(destination);
//		// 第二城市外场
		
		// 单号
		int length = number1.length();
		if (length == 8) {
			form.setWaybillNum1(number1.substring(0, length - 4));
			form.setWaybillNum2(number1.substring(length - 4));
		} else if (length == 9) {
			form.setWaybillNum1(number1.substring(0, length - 4));
			form.setWaybillNum2(number1.substring(length - 4));
		} else if (length == 10) {
			form.setWaybillNum1(number1.substring(0, length - 5));
			form.setWaybillNum2(number1.substring(length - 5));
		}
		// 用户工号
		form.setOptNum(userName);
		// 打印日期
		String printDateStr = (String) context.get("printdate");
		form.setPrintDate(printDateStr);
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
			printStrTemplate.append("R" + labelForm.getLeft() + "," + labelForm.getTop() + "\n");//设置打印左边距和上边距
			
//			printStrTemplate.append("A13,195,0,4,1,1,N,\""+labelForm.getOptNum()+"\"\r\n");//工号
//			printStrTemplate.append("A133,195,0,4,1,1,N,\""+labelForm.getPrintDate()+"\"\r\n");//打印日期
//			printStrTemplate.append("A75,160,0,4,1,1,N,\""+labelForm.getWaybillNum1()+"\"\r\n");//单号1
//			printStrTemplate.append("A155,160,0,4,1,1,N,\""+labelForm.getWaybillNum2()+"\"\r\n");//单号2
//			String destination = labelForm.getDestination();
//			if(destination.length()==2){
//			    printStrTemplate.append("A00,00,0,8,5,5,N,\""+labelForm.getDestination()+"\"\r\n");//180
//			}else if (destination.length()==3){
//			    printStrTemplate.append("A05,50,0,8,3,3,N,\""+labelForm.getDestination()+"\"\r\n");//180
//			}else if(destination.length()>3){
//				printStrTemplate.append("A15,20,0,8,2,3,N,\""+labelForm.getDestination()+"\"\r\n");//180
//			}
			
			
			
			printStrTemplate.append("A0,200,0,4,1,1,N,\""+labelForm.getOptNum()+"\"\r\n");//工号
			printStrTemplate.append("A100,200,0,4,1,1,N,\""+labelForm.getPrintDate()+"\"\r\n");//打印日期
			printStrTemplate.append("A260,195,0,8,1,1,N,\"德邦快递\"\r\n");//打印日期
			printStrTemplate.append("A105,170,0,4,1,1,N,\""+labelForm.getWaybillNum1()+"\"\r\n");//单号1
			printStrTemplate.append("A190,170,0,4,1,1,N,\""+labelForm.getWaybillNum2()+"\"\r\n");//单号2
			String destination = labelForm.getDestination();
			if(destination.length()==1){
			    printStrTemplate.append("A40,05,0,8,5,5,N,\""+labelForm.getDestination()+"\"\r\n");//180
			}else if(destination.length()==2){
			    printStrTemplate.append("A30,05,0,8,5,5,N,\""+labelForm.getDestination()+"\"\r\n");//180
			}else if(destination.length()==3){
			    printStrTemplate.append("A00,05,0,8,4,4,N,\""+labelForm.getDestination()+"\"\r\n");//180
			}else if(destination.length()==3){
				printStrTemplate.append("A85,20,0,8,2,3,N,\""+labelForm.getDestination()+"\"\r\n");//180
			}else if(destination.length()==4){
				printStrTemplate.append("A70,20,0,8,2,3,N,\""+labelForm.getDestination()+"\"\r\n");//180
			}else if(destination.length()>4){
				printStrTemplate.append("A40,20,0,8,2,3,N,\""+labelForm.getDestination().substring(0, 5)+"\"\r\n");//180
			}
			baos.write(printStrTemplate.toString().getBytes("GBK"));
			baos.write("\r\n".getBytes());
			StringBuffer end = new StringBuffer();
			//end.append("GG67,10,\"DPLOGO\"").append("\r\n");
			end.append("P1\r\n");
			baos.write(end.toString().getBytes());
			baos.close();
			outputlist.add(baos.toByteArray());
		
		return outputlist;
	}
}
