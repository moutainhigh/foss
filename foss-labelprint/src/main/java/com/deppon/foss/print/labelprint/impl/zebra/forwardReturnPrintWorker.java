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

public class forwardReturnPrintWorker extends LabelPrintWorker{

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
	
	/**
	 * 转寄退回件需要打印的字段
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public LabelPrintForm getLabelPrintForm(LabelPrintContext context)
			throws Exception {

		// 单号
		String number = (String) context.get("number");
		//打印件的流水号
		String serialnos = (String)context.get("serialnos");
		if(isNull(serialnos)){
			throw new Exception("输入的流水号不正确，无法进行标签打印");
		}
			
		//第二城市外场补码简称
		String secondLoadOrgName = (String)context.get("secLoadOrgName");
		
		if(isNull(secondLoadOrgName)){
			throw new Exception("第二外场为空");
		}
		
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
		
		if(secondLoadOrgName.length()>=12){
			secondLoadOrgName=secondLoadOrgName.substring(0,12);
		}
//		String lastTransCenterCity = (String) context.get("lastTransCenterCity");
//		if(isNull(lastTransCenterCity)){
//			throw new Exception("最终外场为空");
//		}
//		if(null!=lastTransCenterCity){
//			String [] destinationArrays = lastTransCenterCity.split("");
//			StringBuilder bf = new StringBuilder();
//			for (String value : destinationArrays) {
//				if(value.equals("浉")){
//					value = "氵"+"师";
//				}else if(value.equals("洺")){
//					value = "氵"+"名";
//				}
//				bf.append(value);
//			}
//			lastTransCenterCity = bf.toString();
//		}
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

		
		//联系人名字
		String receiveCustomerContact=(String)context.get("receiveCustomerContact");
		if(receiveCustomerContact==null){
			receiveCustomerContact="";
		}
		//联系方式  电话
		String receiveCustomerphone=(String)context.get("receiveCustomerphone");
		if(receiveCustomerphone==null){
			receiveCustomerphone="";
		}
		//地址
		String receiveCustomerProvName=(String)context.get("receiveCustomerProvName");
		if(receiveCustomerProvName==null){
			receiveCustomerProvName="";
		}
		
		String receiveCustomerCityName=(String)context.get("receiveCustomerCityName");
		if(receiveCustomerCityName==null){
			receiveCustomerCityName="";
		}
		String receiveCustomerDistName=(String)context.get("receiveCustomerDistName");
		if(receiveCustomerDistName==null){
			receiveCustomerDistName="";
		}
		String receiveCustomerAddress=(String)context.get("receiveCustomerAddress");
		if(receiveCustomerAddress==null){
			receiveCustomerAddress="";
		}
		String lastTransCenterCity=(String)context.get("lastTransCenterCity");
		if(lastTransCenterCity.length()>=10){
			lastTransCenterCity = lastTransCenterCity.substring(0,10);
		}
		//保价金额
		String insuranceFee=context.get("insuranceFee").toString();
		//代收货款
		String codAmount=context.get("codAmount").toString();
		//签回单
		String returnBillType=(String)context.get("returnBillType");
		//包装费
		String packageFee=context.get("packageFee").toString();
		//运费(公布价)
		String transportFee=context.get("transportFee").toString();
		//到付款
		String toPayAmount=context.get("toPayAmount").toString();
		//总件数
		String totalpieces=(String)context.get("totalpieces");
		//省市区+详细地址
		String receiveMessage=receiveCustomerphone+","+receiveCustomerContact+","+receiveCustomerProvName+receiveCustomerCityName+receiveCustomerDistName+receiveCustomerAddress;
		//转寄退回类型
		String forwardRerturn=(String)context.get("forwardRerturn");
		// 标签打印信息对象
		LabelPrintForm form = new LabelPrintForm();
		// 上边距
		form.setTop(context.getPrtTop());
		// 左边距
		form.setLeft(context.getPrtLeft());	
		// 单号
		form.setWaybillNum1(number);
		// 到达(送后面的字段)
		form.setDestination(lastTransCenterCity);
		//目的站编号
		form.setDestionalCode(stationNumber);
		// 第二外场补码简称
		form.setSecLoadOrgName(secondLoadOrgName);
		//保价金额
		form.setInsuranceFee(Integer.parseInt(insuranceFee));
		//代收货款
		form.setCodAmount(Integer.parseInt(codAmount));
		//签收单
		form.setReturnBillType(returnBillType);
		//包装费
		form.setPackageFee(Integer.parseInt(packageFee));
		//运费
		form.setTransportFee(Integer.parseInt(transportFee));
		//到付款合计
		form.setToPayAmount(Integer.parseInt(toPayAmount));
		// 件数
		form.setTotalPieces(Integer.parseInt(totalpieces));
		//专辑退回类型
		form.setForwardRerturn(forwardRerturn);
		
		form.setReceiveCustomerContact(receiveCustomerContact);
		
		form.setReceiveCustomerphone(receiveCustomerphone);
		
		form.setReceiveCustomerProvName(receiveCustomerProvName);
		
		form.setReceiveCustomerCityName(receiveCustomerCityName);
		
		form.setReceiveCustomerDistName(receiveCustomerDistName);
		
		form.setReceiveCustomerAddress(receiveCustomerAddress);
		
		form.setReceiveMessage(receiveMessage);
		//转寄退回件类型
		form.setForwardRerturn(forwardRerturn);
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
				//流水号
				String printPiece = printPiecesList.get(i - 1);
				//总件数
				int totalpieces = labelForm.getTotalPieces();
				String outPieces = "";
				if(totalpieces<10000){ // <10000
					outPieces = printPiece + "/" + totalpieces ;
				}
				else { // >=10000
					outPieces = printPiece + "/";
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
				//件数/总件数
				if(labelForm.getTotalPieces() > 0
						&& labelForm.getTotalPieces() < 1000) {
					printStrTemplate.append("A590,530,1,8,2,1,N,\""+outPieces+"\"\r\n");
				}else {
					printStrTemplate.append("A590,530,1,8,2,1,N,\""+outPieces+"\"\r\n");
				}
				
				printStrTemplate.append("X200,15,4,620,730\r\n");//画矩形框
				printStrTemplate.append("LO500,505,120,4\r\n");//转右边那条竖线
				printStrTemplate.append("LO495,15,4,715\r\n");//上海上的那条横线
				printStrTemplate.append("LO415,15,4,715\r\n");//北京上的那条横线
				printStrTemplate.append("LO340,15,4,434\r\n");//收件人信息上的那条横线
				printStrTemplate.append("LO240,453,4,280\r\n");//到款合计上的那条横线
				printStrTemplate.append("LO200,450,295,4\r\n");//右边那条竖线
				
				//收货人信息
				if(labelForm.getReceiveMessage().length()>51){
					printStrTemplate.append("A337,18,1,8,1,1,N,\""+"收件人信息:"+labelForm.getReceiveMessage().substring(0,12)+"\"\r\n");
					printStrTemplate.append("A307,18,1,8,1,1,N,\""+labelForm.getReceiveMessage().substring(12,25)+"\"\r\n");
					printStrTemplate.append("A273,18,1,8,1,1,N,\""+labelForm.getReceiveMessage().substring(25, 38)+"\"\r\n");
					printStrTemplate.append("A238,18,1,8,1,1,N,\""+labelForm.getReceiveMessage().substring(38,51)+"\"\r\n");
				}else if(labelForm.getReceiveMessage().length()<=51&&labelForm.getReceiveMessage().length()>38){
					printStrTemplate.append("A337,18,1,8,1,1,N,\""+"收件人信息:"+labelForm.getReceiveMessage().substring(0,12)+"\"\r\n");
					printStrTemplate.append("A307,18,1,8,1,1,N,\""+labelForm.getReceiveMessage().substring(12,25)+"\"\r\n");
					printStrTemplate.append("A273,18,1,8,1,1,N,\""+labelForm.getReceiveMessage().substring(25, 38)+"\"\r\n");
					printStrTemplate.append("A238,18,1,8,1,1,N,\""+labelForm.getReceiveMessage().substring(38)+"\"\r\n");
				}else if(labelForm.getReceiveMessage().length()<=38&&labelForm.getReceiveMessage().length()>25){
					printStrTemplate.append("A337,18,1,8,1,1,N,\""+"收件人信息:"+labelForm.getReceiveMessage().substring(0,12)+"\"\r\n");
					printStrTemplate.append("A307,18,1,8,1,1,N,\""+labelForm.getReceiveMessage().substring(12,25)+"\"\r\n");
					printStrTemplate.append("A273,18,1,8,1,1,N,\""+labelForm.getReceiveMessage().substring(25)+"\"\r\n");
				}else if(labelForm.getReceiveMessage().length()<=25&&labelForm.getReceiveMessage().length()>12){
					printStrTemplate.append("A337,18,1,8,1,1,N,\""+"收件人信息:"+labelForm.getReceiveMessage().substring(0,12)+"\"\r\n");
					printStrTemplate.append("A307,18,1,8,1,1,N,\""+labelForm.getReceiveMessage().substring(12)+"\"\r\n");
				}else{
					printStrTemplate.append("A337,18,1,8,1,1,N,\""+"收件人信息:"+"\"\r\n");
					printStrTemplate.append("A307,18,1,8,1,1,N,\""+labelForm.getReceiveMessage()+"\"\r\n");
				}
				
				//保价金额
				printStrTemplate.append("A415,460,1,8,1,1,N,\""+""+"保价金额:"+labelForm.getInsuranceFee()+"\"\r\n");
				//代收货款
				printStrTemplate.append("A380,460,1,8,1,1,N,\""+""+"代收货款:"+labelForm.getCodAmount()+"\"\r\n");
				//签回单
				printStrTemplate.append("A345,460,1,8,1,1,N,\""+""+"签收单:"+labelForm.getReturnBillType()+"\"\r\n");
				//包装费
				printStrTemplate.append("A310,460,1,8,1,1,N,\""+""+"包装费:"+labelForm.getPackageFee()+"\"\r\n");
				//运费
				printStrTemplate.append("A275,460,1,8,1,1,N,\""+""+"运费:"+labelForm.getTransportFee()+"\"\r\n");
				
				//到付款合计
				printStrTemplate.append("A240,460,1,8,1,1,N,\""+""+"到付款:"+labelForm.getToPayAmount()+"\"\r\n");
				//第二外场
				if(labelForm.getSecLoadOrgName().length()>7){
					printStrTemplate.append("A410,40,1,8,2,1,N,\""+labelForm.getSecLoadOrgName()+"\"\r\n");
				}else if(labelForm.getSecLoadOrgName().length()==7){
					printStrTemplate.append("A410,100,1,8,2,1,N,\""+labelForm.getSecLoadOrgName()+"\"\r\n");
				}else if(labelForm.getSecLoadOrgName().length()==6){
					printStrTemplate.append("A410,40,1,8,2,2,N,\""+labelForm.getSecLoadOrgName()+"\"\r\n");
				}else if(labelForm.getSecLoadOrgName().length()==5){
					printStrTemplate.append("A410,80,1,8,2,2,N,\""+labelForm.getSecLoadOrgName()+"\"\r\n");
				}else if(labelForm.getSecLoadOrgName().length()==4){
					printStrTemplate.append("A410,100,1,8,2,2,N,\""+labelForm.getSecLoadOrgName()+"\"\r\n");
				}else if(labelForm.getSecLoadOrgName().length()==3){
					printStrTemplate.append("A410,120,1,8,2,2,N,\""+labelForm.getSecLoadOrgName()+"\"\r\n");
				}else if(labelForm.getSecLoadOrgName().length()<3){
					printStrTemplate.append("A410,150,1,8,2,2,N,\""+labelForm.getSecLoadOrgName()+"\"\r\n");
				}
				 
				
				//送
				printStrTemplate.append("A470,25,1,8,1,1,N,\"【送】\"\r\n");
				//到达
				if(labelForm.getDestination().length()==2){
					printStrTemplate.append("A490,180,1,8,2,2,R,\""+labelForm.getDestination()+"\"\r\n");
				}else if(labelForm.getDestination().length()>=3&&labelForm.getDestination().length()<=4){
					printStrTemplate.append("A490,150,1,8,2,2,R,\""+labelForm.getDestination()+"\"\r\n");
				
				}else if(labelForm.getDestination().length()==5){
					printStrTemplate.append("A490,130,1,8,2,2,R,\""+labelForm.getDestination()+"\"\r\n");
				}else{ 
					printStrTemplate.append("A490,130,1,8,2,1,R,\""+labelForm.getDestination()+"\"\r\n");
				} 
//				
				printStrTemplate.append("A485,460,1,8,2,1,N,\"K\"\r\n");//打印K字
				printStrTemplate.append("A485,490,1,8,2,1,N,\""+labelForm.getWaybillNum1()+"\"\r\n");
				printStrTemplate.append("B610,210,1,1,2,12,80,B,\""+barcode+"\"\r\n");//条码
				printStrTemplate.append("A590,18,1,8,1,1,N,\"【转/退】\"\r\n");//是否送
				

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
