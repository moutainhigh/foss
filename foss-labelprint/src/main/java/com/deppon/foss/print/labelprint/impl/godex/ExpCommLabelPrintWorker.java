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
import com.deppon.foss.print.labelprint.impl.LabelPrintForm;
import com.deppon.foss.print.labelprint.impl.PackageLabelPrintForm;

/**
 * Godex Comm Label Print Program
 * @author lifanghong
 * 快递打印
 */
public class ExpCommLabelPrintWorker extends LabelPrintWorker {

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
	 * 当前用户 工号 optusernum
	 * 单号 number
	 * 打印流水号 serialnos[0001,0002]
	 * 始发站(出发城市) leavecity
	 * 目的站编码 stationNumber
	 * 最终外场补码简称 finaloutname
	 * 第二城市外场补码简称 secLoadOrgName
	 * 件数 totalpieces
	 * 打印日期 printdate
	 */
	public LabelPrintForm getLabelPrintForm(LabelPrintContext context)
			throws Exception {

		// 当前用户 工号
		String userName = (String) context.get("optusernum");
		// 单号
		String number = (String) context.get("number");
		//打印件的流水号
		String serialnos = (String)context.get("serialnos");
		if(isNull(serialnos)){
			throw new Exception("输入的流水号不正确，无法进行标签打印");
		}
		
		// 始发站
		String leaveCity = (String) context.get("leavecity");
		
		// 最终外场补码简称
		String finalOutName = "";
		if(isNull((String)context.get("finaloutname"))){
			//throw new Exception("最终外场城市名称不存在，无法进行标签打印");
		}
		else {
			finalOutName = (String)context.get("finaloutname");
			if(null!=finalOutName){
				String [] finalOutNameArrays = finalOutName.split("");
				StringBuilder bf = new StringBuilder();
				for (String value : finalOutNameArrays) {
					if(value.equals("浉")){
						value = "氵"+"师";
					}else if(value.equals("洺")){
						value = "氵"+"名";
					}
					bf.append(value);
				}
				finalOutName = bf.toString();
			}
		}
		if(finalOutName.length()>5){
			finalOutName = finalOutName.substring(0, 5);
		}
		//第二城市外场补码简称
		String secondLoadOrgName = (String)context.get("secLoadOrgName");
		if(null!=secondLoadOrgName){
			String [] destinationArrays = secondLoadOrgName.split("");
			StringBuilder bf = new StringBuilder();
			for (String value : destinationArrays) {
				if(value.equals("浉")){
					value = "氵"+"师";
				}else if(value.equals("洺")){
					value = "氵"+"名";
				}
				bf.append(value);
			}
			secondLoadOrgName = bf.toString();
		}
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
		// 目的站编码 
		String stationNumber = "0000";
		if(isNull((String)context.get("stationnumber"))){
			throw new Exception("目的站编码不存在，无法进行标签打印");
		}
		else {
			stationNumber = (String)context.get("stationnumber");
		}

		if (isNull(number)) {
			throw new Exception("运单号不正确，无法进行标签打印");
		}
		
		if ("".equals(leaveCity) || leaveCity==null ) {
			leaveCity = " ";
		}

		if ("".equals(destination) || destination==null ) {
			destination = " ";
		}
		
		if ("".equals(secondLoadOrgName) || secondLoadOrgName==null ) {
			secondLoadOrgName = " ";
		}

		// 标签打印信息对象
		LabelPrintForm form = new LabelPrintForm();
		// 上边距
		form.setTop(context.getPrtTop());
		// 左边距
		form.setLeft(context.getPrtLeft());
		

		// 出发城市
		form.setLeaveCity(leaveCity);
		// 目的站
		form.setDestination(destination);
		// 目的站编码
		form.setDestionalCode(stationNumber);
		
		//新加空运需求关于打印运输性质
		String productCode=(String)context.get("productCode");
		form.setTransportProperty(productCode);
		
		// 最终外场补码简称
		form.setFinalOutfield(finalOutName == null ? " " : finalOutName);
		// 第二外场补码简称
		form.setSecLoadOrgName(secondLoadOrgName == null ? " " : secondLoadOrgName);
		// 单号
		int length = number.length();
		if (length == 9) {
			form.setWaybillNum1(number.substring(0, length - 4));
			form.setWaybillNum2(number.substring(length - 4));
		} else if (length == 10) {
			form.setWaybillNum1(number.substring(0, length - 5));
			form.setWaybillNum2(number.substring(length - 5));
		}
		
		// 件数
		form.setTotalPieces(Integer.parseInt((String) context.get("totalpieces")));
		// 用户工号
		form.setOptNum(userName);
		// 打印日期
		String printDateStr = (String) context.get("printdate");
		form.setPrintDate(printDateStr);
		
		String[] serialnoarr = serialnos.split(",");
		//要打印的件数
		int pieces = serialnoarr.length;
		
		// 设置条码内容
		try{
			List<String> printPiecesList = new ArrayList<String>(pieces);
			List<String> barcodeList = new ArrayList<String>(pieces);
			for (int i = 0; i <pieces; i++) {
				String serial_no = serialnoarr[i];
				barcodeList.add(number + serial_no + form.getDestionalCode()); //运单号9位 + 流水号4位 + 目的站编码4位
				printPiecesList.add( serialnoarr[i]);
			}
			form.setBarcode(barcodeList);
			form.setPrintPiecesList(printPiecesList);		
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("打印流水号转换成件数序号错误，无法进行标签打印"+e.toString());
		}
		return form;
	}

	//斑马打印机进行打印操作
	public List<byte[]> printByZebra(LabelPrintContext lblPrintContext) throws Exception {
		LabelPrintForm labelForm =  getLabelPrintForm(lblPrintContext);
		List<byte[]> outputlist = new ArrayList<byte[]>();
		List<String> printPiecesList = labelForm.getPrintPiecesList();
		List<String> barcodeList = labelForm.getBarcode();
		for (int i = 1; i <= barcodeList.size(); i++){
			ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
			
			String barcode = barcodeList.get(i - 1);
			String printPiece = printPiecesList.get(i - 1);
			int totalpieces = labelForm.getTotalPieces();
			String outPieces = "";
			if(totalpieces<1000){ // <1000
				outPieces = printPiece + "/" + totalpieces ;
			}
			else { // >=1000
				outPieces = printPiece + "/";
			}
			
			String outleaveCity = labelForm.getLeaveCity();
			if (outleaveCity.length()>=3)
			{
				outleaveCity = outleaveCity.substring(2,outleaveCity.length());
			}
			//打印指令
			StringBuffer printStrTemplate = new StringBuffer("");
			printStrTemplate.append("~MDEL\r\n");
			printStrTemplate.append("^L\r\n");
			printStrTemplate.append("^S3\n");//设置打印速度
	
			
			printStrTemplate.append("^R" + labelForm.getLeft()+ "\r\n");//设置打印左边距和上边距
			printStrTemplate.append("~Q+" + labelForm.getTop() + "\r\n");
			
			printStrTemplate.append("H3,112,1,1,415,702,2\r\n");//画矩形框
			printStrTemplate.append("Lo,120,200,418,202\r\n");//出发城市右边的那条竖线
			printStrTemplate.append("Lo,120,630,418,632\r\n");//第二条竖线
			printStrTemplate.append("Lo,118,112,120,812\r\n");//德邦快递上的那条线
			printStrTemplate.append("Lo,66,112,67,325\r\n");//德邦快递下的那条线
			printStrTemplate.append("Lo,258,200,260,632\r\n");//最终外场下的线
			printStrTemplate.append("Lo,328,632,330,812\r\n");//单号上的线
			
			//内容定位
			//始发站
			if(labelForm.getLeaveCity().length()==3){
				printStrTemplate.append("AZ,350,135,2,2,0,1,"+labelForm.getLeaveCity().charAt(0)+"\r\n");
				printStrTemplate.append("AZ,290,135,2,2,0,1,"+labelForm.getLeaveCity().charAt(1)+"\r\n");
				printStrTemplate.append("AZ,230,135,2,2,0,1,"+labelForm.getLeaveCity().charAt(2)+"\r\n");
			
			}else if(labelForm.getLeaveCity().length()==4){
				printStrTemplate.append("AZ,380,135,2,2,0,1,"+labelForm.getLeaveCity().charAt(0)+"\r\n");
				printStrTemplate.append("AZ,320,135,2,2,0,1,"+labelForm.getLeaveCity().charAt(1)+"\r\n");
				printStrTemplate.append("AZ,260,135,2,2,0,1,"+labelForm.getLeaveCity().charAt(2)+"\r\n");
				printStrTemplate.append("AZ,200,135,2,2,0,1,"+labelForm.getLeaveCity().charAt(3)+"\r\n");
			}else if(labelForm.getLeaveCity().length()>=5){
				printStrTemplate.append("AZ,400,135,2,2,0,1,"+labelForm.getLeaveCity().charAt(0)+"\r\n");
				printStrTemplate.append("AZ,345,135,2,2,0,1,"+labelForm.getLeaveCity().charAt(1)+"\r\n");
				printStrTemplate.append("AZ,290,135,2,2,0,1,"+labelForm.getLeaveCity().charAt(2)+"\r\n");
				printStrTemplate.append("AZ,235,135,2,2,0,1,"+labelForm.getLeaveCity().charAt(3)+"\r\n");
				printStrTemplate.append("AZ,180,135,2,2,0,1,"+labelForm.getLeaveCity().charAt(4)+"\r\n");
			}
			//最终外场
			if(labelForm.getFinalOutfield().length()==5){
				printStrTemplate.append("AZ,340,240,3,3,0,1,"+labelForm.getFinalOutfield()+"\r\n");
			}else if(labelForm.getFinalOutfield().length()== 4 ){
				printStrTemplate.append("AZ,350,220,4,4,0,1,"+labelForm.getFinalOutfield()+"\r\n");
			}else if(labelForm.getFinalOutfield().length()==3){
				printStrTemplate.append("AZ,350,265,4,4,0,1,"+labelForm.getFinalOutfield()+"\r\n");
			}else if(labelForm.getFinalOutfield().length()==2){
				printStrTemplate.append("AZ,350,300,4,4,0,1,"+labelForm.getFinalOutfield()+"\r\n");
			}
			//第二外场
			if(labelForm.getSecLoadOrgName().length()>7){
				printStrTemplate.append("AZ,250,225,2,2,0,1,"+labelForm.getSecLoadOrgName()+"\r\n");
			}else if(labelForm.getSecLoadOrgName().length()==7){
				printStrTemplate.append("AZ,250,240,2,2,0,1,"+labelForm.getSecLoadOrgName()+"\r\n");
			}else if(labelForm.getSecLoadOrgName().length()==6){
				printStrTemplate.append("AZ,250,200,3,3,0,1,"+labelForm.getSecLoadOrgName()+"\r\n");
			}else if(labelForm.getSecLoadOrgName().length()==5){
				printStrTemplate.append("AZ,250,240,3,3,0,1,"+labelForm.getSecLoadOrgName()+"\r\n");
			}else if(labelForm.getSecLoadOrgName().length()== 4 ){
				printStrTemplate.append("AZ,250,220,4,4,0,1,"+labelForm.getSecLoadOrgName()+"\r\n");
			}else if(labelForm.getSecLoadOrgName().length()==3){
				printStrTemplate.append("AZ,250,265,4,4,0,1,"+labelForm.getSecLoadOrgName()+"\r\n");
			}else if(labelForm.getSecLoadOrgName().length()==2){
				printStrTemplate.append("AZ,250,300,4,4,0,1,"+labelForm.getSecLoadOrgName()+"\r\n");
			}
			
			
			
			printStrTemplate.append("AE,260,640,1,2,1,1,K\r\n");//打印K字
			printStrTemplate.append("AE,270,680,1,1,1,1,"+labelForm.getWaybillNum1()+"\r\n");
			printStrTemplate.append("AE,240,680,1,1,1,1,"+labelForm.getWaybillNum2()+"\r\n");
			
			
			
			//件数/总件数
			if(labelForm.getTotalPieces() > 0
					&& labelForm.getTotalPieces() < 10) {
				printStrTemplate.append("AE,395,660,1,1,0,1,"+outPieces+"\r\n");
			}else if(labelForm.getTotalPieces() >= 10
					&& labelForm.getTotalPieces() < 100){
				printStrTemplate.append("AE,395,650,1,1,0,1,"+outPieces+"\r\n");
			}else if(labelForm.getTotalPieces() >= 100
					&& labelForm.getTotalPieces() < 1000) {
				printStrTemplate.append("AE,395,640,1,1,0,1,"+outPieces+"\r\n");
			}else {
				printStrTemplate.append("AE,395,640,1,1,0,1,"+outPieces+"\r\n");
			}
			
			
			//11月26号版本
			if (null!=labelForm.getTransportProperty() && "DEAP".equals(labelForm.getTransportProperty())){
				printStrTemplate.append("AZ,55,700,2,2,0,1,空\r\n");//空
				printStrTemplate.append("R1,750,60,700,2,1\r\n");//画空矩形框
			}
			
			
			
			printStrTemplate.append("AZ,120,126,2,2,0,1,德邦快递\r\n");//打印公司"德邦快递"LOGO四个字
			printStrTemplate.append("AE,75,140,1,1,1,1,"+labelForm.getOptNum()+"\r\n");//工号
			printStrTemplate.append("AE,45,115,1,1,1,1,"+labelForm.getPrintDate()+"\r\n");//打印日期
			printStrTemplate.append("BQ,115,360,2,2,75,1,1,"+barcode+"\r\n");//条码
			printStrTemplate.append("AZ,395,200,1,1,0,1,【送】\r\n");//是否送货
			
			printStrTemplate.append("E\n");//设置打印头温度
			printStrTemplate.append("~MDEL\n");//取消打印回转功能
			
			baos.write(printStrTemplate.toString().getBytes("GBK"));
			baos.write("\r\n".getBytes());
			
			StringBuffer end = new StringBuffer();
			end.append("P1\r\n");//打印一份
			baos.write(end.toString().getBytes());
			baos.close();
			outputlist.add(baos.toByteArray());
		}
		return outputlist;
	}
}


