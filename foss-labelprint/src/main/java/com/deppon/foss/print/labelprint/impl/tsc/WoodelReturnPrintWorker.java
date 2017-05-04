package com.deppon.foss.print.labelprint.impl.tsc;

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

public class WoodelReturnPrintWorker extends LabelPrintWorker {
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
	
	public LabelPrintForm getLabelPrintForm(LabelPrintContext context)
			throws Exception {
		// 当前用户 工号
		String userName = (String) context.get("optusernum");
		// 单号
		String number = (String) context.get("waybillNo");
		//运输性质
		String transtype=(String) context.get("transtype");
		//流水号
		String serialnos=(String) context.get("serialnos");
		if(isNull(serialnos)){
			throw new Exception("无流水号，无法进行标签打印");
		}
		// 打印日期
		String printDateStr = (String) context.get("printdate");
		//包装类型
		String packing=(String) context.get("packing");
		//总件数
		String numTotal=(String)context.get("NumTotal");
		//包装件数
		String Num="";
		/**
		 * 包装件数，根据包装类型确定
		 */
		//如果包装类型是打木架或者打木架加托包装件数取打木架件数
		if("打木架".equals(packing) || "打木架+托".equals(packing)){
			Num=(String) context.get("standGoodsNum");
		}else if("打木箱".equals(packing) || "打木箱+托".equals(packing)){
			Num=(String) context.get("boxGoodsNum");
		}else if("打木托".equals(packing)){
			Num=(String) context.get("salverGoodsNum");
		}
		/**
		 * 打包装要求与包装件数一样
		 */
		 //包装要求
		String Quirement="";
		if("打木架+托".equals(packing)){
			Quirement=(String) context.get("standRequirement")+"/"+(String) context.get("salverRequirement");
		}else if("打木箱+托".equals(packing)){
			Quirement=(String) context.get("boxRequirement")+"/"+(String) context.get("salverRequirement");
		}else if("打木托".equals(packing)){
			Quirement=(String) context.get("salverRequirement");
		}else if("打木架".equals(packing)){
			Quirement=(String) context.get("standRequirement");
		}else if("打木箱".equals(packing)){
			Quirement=(String) context.get("boxRequirement");
		}
		//是否显示“德邦物流”
		String isPrintLogo=" ";
		isPrintLogo=(String)context.get("isPrintLogo");
		//总重量
		String weight=(String)context.get("weight");
		/**
		 * 包装尺寸
		 */
		String GoodsSize="";
		if("打木架".equals(packing) || "打木架+托".equals(packing)){
			GoodsSize=(String) context.get("standGoodsSize");
		}else if("打木箱".equals(packing) || "打木箱+托".equals(packing)){
			GoodsSize=(String) context.get("boxGoodsSize");
		}else if("打木托".equals(packing)){
			GoodsSize="";
		}
		/**
		 * 包装体积
		 */
		String GoodsVolume="";
		if("打木架".equals(packing) || "打木架+托".equals(packing)){
			GoodsVolume=(String) context.get("standGoodsVolume");
		}else if("打木箱".equals(packing) || "打木箱+托".equals(packing)){
			GoodsVolume=(String) context.get("boxGoodsVolume");
		}else if("打木托".equals(packing)){
			GoodsVolume="";
		}
					
		// 标签打印信息对象
		LabelPrintForm form = new LabelPrintForm();
		// 上边距
		form.setTop(context.getPrtTop());
		// 左边距
		form.setLeft(context.getPrtLeft());
		//显示“德邦物流”		
		form.setIsPrintLogo(isPrintLogo);
		// 单号
		form.setWaybillNo(number);
		//流水号
		form.setSerialnos(serialnos);
		//包装类型
		form.setPacking(packing);
		//包装件数
		form.setNum(Num);
		//包装要求
		form.setQuirement(Quirement);
		//总重量
		form.setWeight(weight);
		//包装尺寸
		form.setGoodsSize(GoodsSize);
		//包装体积
		form.setGoodsVolume(GoodsVolume);
		// 运输性质
		form.setTranstype(transtype);
		// 用户工号
		form.setOptNum(userName);
		//打印日期
		form.setPrintDate(printDateStr);
		//总件数
		form.setNumTotal(numTotal);
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
		printStrTemplate.append("Q720,24\r\n");//设置标签纸高度及标签之间的间隙
		printStrTemplate.append("q440\r\n");//设置标签纸宽度
		printStrTemplate.append("S6\n");//设置打印速度
		printStrTemplate.append("D7\n");//设置打印头温度
		printStrTemplate.append("JB\n");//取消打印回转功能
		printStrTemplate.append("ZB\n");//从标签右下角开始打印
		printStrTemplate.append("R" + labelForm.getLeft() + "," + labelForm.getTop() + "\n");//设置打印左边距和上边距
		
		printStrTemplate.append("X50,1,2,460,700\r\n");//画矩形框
//		printStrTemplate.append("X200,15,4,620,710\r\n");//画矩形框
		printStrTemplate.append("LO50,130,410,4\r\n");//第一条竖线
		printStrTemplate.append("LO50,300,130,4\r\n");//第二条竖线
		printStrTemplate.append("LO50,440,130,4\r\n");//第三条竖线
		printStrTemplate.append("LO50,550,130,4\r\n");//第四条竖线
		printStrTemplate.append("LO380,390,80,4\r\n");//第五条竖线
		printStrTemplate.append("LO380,5,4,695\r\n");//第一条横线
		printStrTemplate.append("LO180,5,4,695\r\n");//第二条横线
		printStrTemplate.append("LO110,5,4,695\r\n");//第三条横线
		//关于满足客户个性需求，标签打印不显示“德邦物流”
		if("N".equals(labelForm.getIsPrintLogo()) || "".equals(labelForm.getIsPrintLogo())){
			//什么逻辑都不执行！！
		}else{
			printStrTemplate.append("A165,560,1,8,1,1,N,\"德邦物流\"\r\n");//打印公司LOGO
		}
		printStrTemplate.append("A110,575,1,8,1,1,N,\""+labelForm.getOptNum()+"\"\r\n");//打印打印人
		printStrTemplate.append("A70,565,1,2,1,1,N,\""+labelForm.getPrintDate()+"\"\r\n");//打印日期
		printStrTemplate.append("A165,445,1,8,1,1,N,\""+"总重量"+"\"\r\n");//总重量
		printStrTemplate.append("A90,480,1,4,1,1,N,\""+labelForm.getWeight()+"\"\r\n");//总重量
		printStrTemplate.append("A165,310,1,8,1,1,N,\""+"包装体积"+"\"\r\n");//包装体积
		printStrTemplate.append("A90,335,1,4,1,1,N,\""+labelForm.getGoodsVolume()+"\"\r\n");//包装体积
		printStrTemplate.append("A165,160,1,8,1,1,N,\""+"包装尺寸"+"\"\r\n");//包装尺寸
		printStrTemplate.append("A90,135,1,2,1,1,N,\""+labelForm.getGoodsSize()+"\"\r\n");//包装尺寸
		printStrTemplate.append("A165,5,1,8,1,1,N,\""+"包装件数"+"\"\r\n");//包装件数
		printStrTemplate.append("A90,50,1,8,1,1,N,\""+labelForm.getNum()+"件"+"\"\r\n");//包装件数
		printStrTemplate.append("A430,5,1,8,1,1,N,\""+"打木包装"+"\"\r\n");//打木包装
		printStrTemplate.append("A430,200,1,4,1,1,N,\""+labelForm.getWaybillNo()+"\"\r\n");//运单号 
		printStrTemplate.append("A430,400,1,4,1,1,N,\""+labelForm.getSerialnos()+"/"+"\"\r\n");//流水号
		printStrTemplate.append("A430,485,1,4,1,1,N,\""+labelForm.getNum()+"\"\r\n");//总件数
		printStrTemplate.append("A430,535,1,8,1,1,N,\""+labelForm.getTranstype()+"\"\r\n");//运输性质
		/**
		 * 包装类型
		 */
		if(labelForm.getPacking().length()==5){
			printStrTemplate.append("A300,15,1,8,1,1,N,\""+labelForm.getPacking().substring(0,3)+"\"\r\n");
			printStrTemplate.append("A265,15,1,8,1,1,N,\""+labelForm.getPacking().substring(3,5)+"\"\r\n");
		}else{
			printStrTemplate.append("A300,15,1,8,1,1,N,\""+labelForm.getPacking()+"\"\r\n");
		}
		
		/**
		 * 打木包装要求
		 */
		if(labelForm.getQuirement() !=null){
			if(labelForm.getQuirement().length()>67){
				printStrTemplate.append("A345,155,1,8,1,1,N,\""+labelForm.getQuirement().substring(0, 17)+"\"\r\n");
				printStrTemplate.append("A310,155,1,8,1,1,N,\""+labelForm.getQuirement().substring(17, 33)+"\"\r\n");
				printStrTemplate.append("A275,155,1,8,1,1,N,\""+labelForm.getQuirement().substring(33, 50)+"\"\r\n");
				printStrTemplate.append("A240,155,1,8,1,1,N,\""+labelForm.getQuirement().substring(50, 67)+"\"\r\n");
			}
				else if(labelForm.getQuirement().length()>51 && labelForm.getQuirement().length()<=67){
				printStrTemplate.append("A345,155,1,8,1,1,N,\""+labelForm.getQuirement().substring(0, 18)+"\"\r\n");
				printStrTemplate.append("A310,155,1,8,1,1,N,\""+labelForm.getQuirement().substring(18, 33)+"\"\r\n");
				printStrTemplate.append("A275,155,1,8,1,1,N,\""+labelForm.getQuirement().substring(34, 50)+"\"\r\n");
				printStrTemplate.append("A240,155,1,8,1,1,N,\""+labelForm.getQuirement().substring(50, labelForm.getQuirement().length())+"\"\r\n");
			}
				else if(labelForm.getQuirement().length()>34 && labelForm.getQuirement().length()<=50){
				printStrTemplate.append("A345,155,1,8,1,1,N,\""+labelForm.getQuirement().substring(0, 17)+"\"\r\n");
				printStrTemplate.append("A310,155,1,8,1,1,N,\""+labelForm.getQuirement().substring(17, 33)+"\"\r\n");
				printStrTemplate.append("A275,155,1,8,1,1,N,\""+labelForm.getQuirement().substring(33, labelForm.getQuirement().length())+"\"\r\n");
			}
			   else if(labelForm.getQuirement().length()>17 && labelForm.getQuirement().length()<=34){
				printStrTemplate.append("A310,155,1,8,1,1,N,\""+labelForm.getQuirement().substring(0, 17)+"\"\r\n");
				printStrTemplate.append("A275,155,1,8,1,1,N,\""+labelForm.getQuirement().substring(17, labelForm.getQuirement().length())+"\"\r\n");
			}  else {
				printStrTemplate.append("A310,155,1,8,1,1,N,\""+labelForm.getQuirement()+"\"\r\n");
			}
		}
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
