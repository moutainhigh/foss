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

 
/**
 * Zebra888TT Comm Label Print Program
 * @author niujian
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
		//新加空运需求关于打印运输性质
		String productCode=(String)context.get("productCode");
		form.setTransportProperty(productCode);
		
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
	public List<byte[]> printByZebra(LabelPrintContext lblPrintContext) throws Exception 
	{
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
			printStrTemplate.append("N\r\n");
			printStrTemplate.append("Q740,24\r\n");//设置标签纸高度及标签之间的间隙
			printStrTemplate.append("q450\r\n");//设置标签纸宽度
			printStrTemplate.append("S6\n");//设置打印速度
			printStrTemplate.append("D7\n");//设置打印头温度
			printStrTemplate.append("JB\n");//取消打印回转功能
			printStrTemplate.append("ZB\n");//从标签右下角开始打印
			printStrTemplate.append("R" + labelForm.getLeft() + "," + labelForm.getTop() + "\n");//设置打印左边距和上边距
			printStrTemplate.append("X30,10,4,460,720\r\n");//画矩形框
			//出发城市右边的那条竖线
			printStrTemplate.append("LO140,95,315,4\r\n");
			//第二条竖线
			printStrTemplate.append("LO140,550,315,4\r\n");
			//德邦快递下的那条线
			printStrTemplate.append("LO95,10,2,165\r\n");
			//德邦快递上的那条线
			printStrTemplate.append("LO140,10,4,706\r\n");
			//最终外场下的线
			printStrTemplate.append("LO281,95,4,460\r\n");
			//单号上的线
			printStrTemplate.append("LO375,550,4,170\r\n");
			
			
			
			//字题定位     A p1, p2, p3, p4 ,p5，p6
			//最终外场
			if(labelForm.getFinalOutfield().length()==5){
				printStrTemplate.append("A395,170,1,8,3,2,R,\""+labelForm.getFinalOutfield()+"\"\r\n");
			}else if(labelForm.getFinalOutfield().length()== 4 ){
				printStrTemplate.append("A395,195,1,8,3,2,R,\""+labelForm.getFinalOutfield()+"\"\r\n");
			}else if(labelForm.getFinalOutfield().length()==3){
				printStrTemplate.append("A395,180,1,8,3,3,R,\""+labelForm.getFinalOutfield()+"\"\r\n");
			}else if(labelForm.getFinalOutfield().length()==2){
				printStrTemplate.append("A395,230,1,8,3,3,R,\""+labelForm.getFinalOutfield()+"\"\r\n");
			}
			//第二外场
			if(labelForm.getSecLoadOrgName().length()>7){
				printStrTemplate.append("A270,130,1,8,2,1,N,\""+labelForm.getSecLoadOrgName()+"\"\r\n");
			}else if(labelForm.getSecLoadOrgName().length()==7){
				printStrTemplate.append("A270,100,1,8,2,2,N,\""+labelForm.getSecLoadOrgName()+"\"\r\n");
			}else if(labelForm.getSecLoadOrgName().length()==6){
				printStrTemplate.append("A270,135,1,8,2,2,N,\""+labelForm.getSecLoadOrgName()+"\"\r\n");
			}else if(labelForm.getSecLoadOrgName().length()==5){
				printStrTemplate.append("A270,170,1,8,3,2,N,\""+labelForm.getSecLoadOrgName()+"\"\r\n");
			}else if(labelForm.getSecLoadOrgName().length()== 4){
				printStrTemplate.append("A270,195,1,8,3,2,N,\""+labelForm.getSecLoadOrgName()+"\"\r\n");
			}else if(labelForm.getSecLoadOrgName().length()==3){
				printStrTemplate.append("A270,180,1,8,3,3,N,\""+labelForm.getSecLoadOrgName()+"\"\r\n");
			}else if(labelForm.getSecLoadOrgName().length()==2){
				printStrTemplate.append("A270,230,1,8,3,3,N,\""+labelForm.getSecLoadOrgName()+"\"\r\n");
			}
			
			//始发站
			if(labelForm.getLeaveCity().length()==3){
				printStrTemplate.append("A390,20,1,8,2,2,N,\""+labelForm.getLeaveCity().charAt(0)+"\"\r\n");
				printStrTemplate.append("A325,20,1,8,2,2,N,\""+labelForm.getLeaveCity().charAt(1)+"\"\r\n");
				printStrTemplate.append("A265,20,1,8,2,2,N,\""+labelForm.getLeaveCity().charAt(2)+"\"\r\n");
			
			}else if(labelForm.getLeaveCity().length()==4){
				printStrTemplate.append("A420,20,1,8,2,2,N,\""+labelForm.getLeaveCity().charAt(0)+"\"\r\n");
				printStrTemplate.append("A355,20,1,8,2,2,N,\""+labelForm.getLeaveCity().charAt(1)+"\"\r\n");
				printStrTemplate.append("A295,20,1,8,2,2,N,\""+labelForm.getLeaveCity().charAt(2)+"\"\r\n");
				printStrTemplate.append("A235,20,1,8,2,2,N,\""+labelForm.getLeaveCity().charAt(3)+"\"\r\n");
			}else if(labelForm.getLeaveCity().length()>=5){
				printStrTemplate.append("A455,20,1,8,2,2,N,\""+labelForm.getLeaveCity().charAt(0)+"\"\r\n");
				printStrTemplate.append("A390,20,1,8,2,2,N,\""+labelForm.getLeaveCity().charAt(1)+"\"\r\n");
				printStrTemplate.append("A330,20,1,8,2,2,N,\""+labelForm.getLeaveCity().charAt(2)+"\"\r\n");
				printStrTemplate.append("A270,20,1,8,2,2,N,\""+labelForm.getLeaveCity().charAt(3)+"\"\r\n");
				printStrTemplate.append("A210,20,1,8,2,2,N,\""+labelForm.getLeaveCity().charAt(4)+"\"\r\n");
			}
			
			//件数/总件数
			if(labelForm.getTotalPieces() > 0
					&& labelForm.getTotalPieces() < 10) {
				printStrTemplate.append("A445,585,1,8,2,1,N,\""+outPieces+"\"\r\n");
			}else if(labelForm.getTotalPieces() >= 10
					&& labelForm.getTotalPieces() < 100){
				printStrTemplate.append("A445,565,1,8,2,1,N,\""+outPieces+"\"\r\n");
			}else if(labelForm.getTotalPieces() >= 100
					&& labelForm.getTotalPieces() < 1000) {
				printStrTemplate.append("A445,553,1,8,2,1,N,\""+outPieces+"\"\r\n");
			}else {
				printStrTemplate.append("A445,580,1,8,2,1,N,\""+outPieces+"\"\r\n");
			}
//			printStrTemplate.append("A425,580,1,8,1,1,N,\"001/5\"\r\n");
			
			
			printStrTemplate.append("A317,561,1,4,4,2,N,\"K\"\r\n");//打印K字
			printStrTemplate.append("A325,593,1,8,2,1,N,\""+labelForm.getWaybillNum1()+"\"\r\n");
			printStrTemplate.append("A275,593,1,8,2,1,N,\""+labelForm.getWaybillNum2()+"\"\r\n");
			
			//11月26号版本
			if (null!=labelForm.getTransportProperty() && "DEAP".equals(labelForm.getTransportProperty())){
			printStrTemplate.append("A110,615,1,8,2,2,R,\""+"空"+"\"\r\n");//空
			}
			
			printStrTemplate.append("A90,45,1,4,1,1,N,\""+labelForm.getOptNum()+"\"\r\n");//工号
			printStrTemplate.append("A65,20,1,4,1,1,N,\""+labelForm.getPrintDate()+"\"\r\n");//打印日期
			printStrTemplate.append("A130,30,1,8,1,1,N,\"德邦快递\"\r\n");
			
			printStrTemplate.append("B138,300,1,1,2,12,75,B,\""+barcode+"\"\r\n");//条码
			
			printStrTemplate.append("A445,105,1,8,1,1,N,\"【送】\"\r\n");//是否送货

			baos.write(printStrTemplate.toString().getBytes("GBK"));
			//baos.write(pcxBytes);
			baos.write("\r\n".getBytes());
			StringBuffer end = new StringBuffer();
			end.append("P1\r\n");
			baos.write(end.toString().getBytes());
			baos.close();
			outputlist.add(baos.toByteArray());
		}
		
		return outputlist;
	}
}
