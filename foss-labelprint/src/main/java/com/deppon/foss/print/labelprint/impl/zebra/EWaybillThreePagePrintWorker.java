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

import org.mortbay.util.StringUtil;

import com.deppon.foss.print.labelprint.LabelPrintContext;
import com.deppon.foss.print.labelprint.LabelPrintWorker;
import com.deppon.foss.print.labelprint.impl.EWaybillStubCopyForm;
/**
 * 电子运单3联打印模版
 * @author foss-218438
 */
public class EWaybillThreePagePrintWorker extends LabelPrintWorker {

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
		//获取字节码
		List<byte[]> cmdByteList = printEWaybillByZebra(lblPrintContext);
		
		PrintService _prtservice = findFirstPrintService(lblPrintContext);
		lblPrintContext.setmPrintService(_prtservice);
		PrintService _printService = lblPrintContext.getmPrintService();
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		for(byte[] bytes : cmdByteList){
			DocPrintJob job = _printService.createPrintJob();
			PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
			ByteArrayInputStream str = new ByteArrayInputStream( bytes);
			Doc doc = new SimpleDoc(str, flavor, null);
			job.print(doc, pras);
		}
	}

	/**
	 * 获取打印所需数据以及打印指令
	 * @author foss-218438
	 * @param lblPrintContext
	 * @return
	 */
	private List<byte[]> printEWaybillByZebra(LabelPrintContext context) throws Exception {
		List<byte[]> outputlist = new ArrayList<byte[]>();
		
		EWaybillStubCopyForm labelForm =  getLabelPrintForm(context);
		List<String> barcodeList = labelForm.getBarcode();
		for (int i = 1; i <= barcodeList.size(); i++){
			String barcode = barcodeList.get(i-1);
			String serialNo = labelForm.getSerialNoList().get(i-1);
			ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
			
			//出发城市
			String leaveCity = revertUnow(labelForm.getLeavecity());
			//进行相关数据的转化
			if(leaveCity != null && leaveCity.length() > 0){
				if (leaveCity.length()<3){
					leaveCity +="—";
				}else if(leaveCity.length()<=4){
					leaveCity = leaveCity.substring(2 ,leaveCity.length());
				}else{
					leaveCity = leaveCity.substring(2 ,4);
				}
			}
			//发货人联系方式
			String deliverPhone = "";
			if(!isBlank(labelForm.getDeliveryCustomerPhone())){
				deliverPhone = isBlank(labelForm.getDeliveryCustomerPhone()) ? "" : labelForm.getDeliveryCustomerPhone();
				if(!isBlank(labelForm.getDeliveryCustomerMobilephone())){
					deliverPhone = deliverPhone + "/" + labelForm.getDeliveryCustomerMobilephone();
				}
			}else{
				deliverPhone = labelForm.getDeliveryCustomerMobilephone();
			}
			//收货人联系方式
			String receiverPhone = "";
			if(!isBlank(labelForm.getReceiveCustomerPhone())){
				receiverPhone =  isBlank(labelForm.getReceiveCustomerPhone()) ? "" : labelForm.getReceiveCustomerPhone();
				if(!isBlank(labelForm.getReceiveCustomerMobilephone())){
					receiverPhone = receiverPhone + "/" + labelForm.getReceiveCustomerMobilephone();
				}
			}else{
				receiverPhone = labelForm.getReceiveCustomerMobilephone();
			}
			String insuranceFee = isBlank(labelForm.getInsuranceFee()) ? "" : labelForm.getInsuranceFee();
			String insuranceAmount = isBlank(labelForm.getInsuranceAmount()) ? "" : labelForm.getInsuranceAmount();
			String codAmount = isBlank(labelForm.getCodAmount()) ? "" : labelForm.getCodAmount();
			String custOrderLine = isBlank(labelForm.getCustOrderLine()) ? "" : labelForm.getCustOrderLine();
			//电子运单模板打印指令
			StringBuffer printStrTemplate = new StringBuffer("");
			
			
			printStrTemplate.append("^XA\r\n");
			printStrTemplate.append("^SEE:GB18030.DAT^CWZ,E:CLJGBK.TTF^CI26\r\n");
			//printStrTemplate.append("^^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR2,2~SD20^JUS^LRN^CI0^XZ\r\n");
			printStrTemplate.append("^MMT\r\n");
			printStrTemplate.append("^PW799\r\n");
			printStrTemplate.append("^LL1199\r\n");
			printStrTemplate.append("^LS0\r\n");
			// 设置左边距和右边距
			printStrTemplate.append("^LH" + labelForm.getLeft() + ","
					+ labelForm.getTop() + "\n");
			
			//画框线
			printStrTemplate.append("^FO16,9^GB770,1422,2^FS\r\n");
			printStrTemplate.append("^FO16,128^GB770,0,2^FS\r\n");
			printStrTemplate.append("^FO16,301^GB770,0,2^FS\r\n");
			printStrTemplate.append("^FO16,423^GB770,0,2^FS\r\n");
			printStrTemplate.append("^FO16,461^GB770,0,2^FS\r\n");
			printStrTemplate.append("^FO16,1217^GB770,0,2^FS\r\n");
			printStrTemplate.append("^FO16,1257^GB770,0,2^FS\r\n");
			printStrTemplate.append("^FO16,1176^GB770,0,2^FS\r\n");
			printStrTemplate.append("^FO16,657^GB770,0,2^FS\r\n");
			printStrTemplate.append("^FO16,697^GB770,0,2^FS\r\n");
			printStrTemplate.append("^FO16,816^GB770,0,2^FS\r\n");
			printStrTemplate.append("^FO16,896^GB770,0,2^FS\r\n");
			printStrTemplate.append("^FO16,979^GB580,0,2^FS\r\n");
			printStrTemplate.append("^FO16,1134^GB770,0,2^FS\r\n");
			printStrTemplate.append("^FO16,1384^GB770,0,2^FS\r\n");
			
			printStrTemplate.append("^FO593,896^GB0,238,2^FS\r\n");
			printStrTemplate.append("^FO490,128^GB0,176,2^FS\r\n");
			printStrTemplate.append("^FO490,462^GB0,193,2^FS\r\n");
			printStrTemplate.append("^FO490,547^GB295,0,2^FS\r\n");
			printStrTemplate.append("^FO25,216^GB466,0,2^FS\r\n");
			printStrTemplate.append("^FO60,302^GB0,122,1^FS\r\n");
			
			//填充数据
			//条形码
			printStrTemplate.append("^BY3,3,55^FT370,84^BCN,,Y,N\r\n");
			printStrTemplate.append("^FD>;"+barcode+"^FS\r\n");
			printStrTemplate.append("^BY2,3,66^FT403,774^BCN,,Y,N\r\n");
			printStrTemplate.append("^FD>;"+labelForm.getWaybillNo()+"^FS\r\n");
			
			//第1联收件信息
			//收件联系人和联系方式
			printStrTemplate.append("^FO20,135^AZN,20,20^FD收件:"+labelForm.getReceiveCustomerContact()+"^FS\r\n");
			printStrTemplate.append("^FO20,155^AZN,20,20^FD"+receiverPhone+"^FS\r\n");
			if(labelForm.getReceiveCustomerAddress().length() <= 23){
				printStrTemplate.append("^FO20,175^AZN,20,20^FD"+labelForm.getReceiveCustomerAddress()+"^FS\r\n");
			}else {
				printStrTemplate.append("^FO20,175^AZN,20,20^FD"+labelForm.getReceiveCustomerAddress().substring(0, 23)+"^FS\r\n");   
				printStrTemplate.append("^FO20,195^AZN,20,20^FD"+labelForm.getReceiveCustomerAddress().substring(23, labelForm.getReceiveCustomerAddress().length())+"^FS\r\n");
			}
			
			//第1联寄件信息
			//关于寄件人信息
			if("N".equals(labelForm.getIsHideDeliveryCustomerInfo())){
				printStrTemplate.append("^FO20,220^AZN,20,20^FD寄件:"+labelForm.getDeliveryCustomerContact()+"^FS\r\n");
				printStrTemplate.append("^FO20,240^AZN,20,20^FD"+deliverPhone+"^FS\r\n");
				if(labelForm.getDeliveryCustomerAddress().length() <= 23){
					printStrTemplate.append("^FO20,260^AZN,20,20^FD"+labelForm.getDeliveryCustomerAddress()+"^FS\r\n");
				}else {
					printStrTemplate.append("^FO20,260^AZN,20,20^FD"+labelForm.getDeliveryCustomerAddress().substring(0, 23)+"^FS\r\n");   
					printStrTemplate.append("^FO20,280^AZN,20,20^FD"+labelForm.getDeliveryCustomerAddress().substring(23, labelForm.getDeliveryCustomerAddress().length())+"^FS\r\n");
				}
			}
			
			
			printStrTemplate.append("^FO510,140^AZN,24,24^FD运输方式:^FS\r\n");
			printStrTemplate.append("^FO540,170^AZN,40,40^FD"+labelForm.getProductName()+"^FS\r\n");
			printStrTemplate.append("^FO510,210^AZN,24,24^FD收件员:^FS\r\n");
			printStrTemplate.append("^FO510,240^AZN,24,24^FD派件员:^FS\r\n");
			printStrTemplate.append("^FO680,270^AZN,28,28^FD已验视^FS\r\n");
			
			//分拣信息
			printStrTemplate.append("^FO25,310^FPV,0^AZN,28,28^FD分拣信息^FS\r\n");
			printStrTemplate.append("^FO70,310^AZN,35,35^FD"+labelForm.getLastLoadOrgName()+"^FS\r\n");
			printStrTemplate.append("^FO100,360^AZN,50,50^FD"+labelForm.getSecondOutfieldName()+"-"+labelForm.getLastLoadOrgName()+"^FS\r\n");
			printStrTemplate.append("^FO560,310^AZN,25,25^FD原寄地:"+labelForm.getLeavecity()+"^FS\r\n");
			//始发营业部和到达营业部
			printStrTemplate.append("^FO25,430^AZN,23,23^FD始发:"+labelForm.getCreateOrgName()+"^FS\r\n");
			printStrTemplate.append("^FO380,430^AZN,23,23^FD到达:"+labelForm.getCustomerPickupOrgName()+"^FS\r\n");
			printStrTemplate.append("^FO25,470^AZN,23,23^FD订单号:"+custOrderLine+"^FS\r\n");
			printStrTemplate.append("^FO25,500^AZN,23,23^FD保价金额:"+insuranceAmount+"^FS\r\n");
			printStrTemplate.append("^FO25,540^AZN,23,23^FD付款方式:"+labelForm.getPaidMethod()+"^FS\r\n");
			printStrTemplate.append("^FO25,580^AZN,23,23^FD代收货款:"+codAmount+"^FS\r\n");
			printStrTemplate.append("^FO250,540^AZN,23,23^FD保价费用:"+insuranceFee+"^FS\r\n");
			if(labelForm.getReturnBillType().length()<=6){
				printStrTemplate.append("^FO250,580^AZN,23,23^FD签回单:"+labelForm.getReturnBillType()+"^FS\r\n");
			}else{
				printStrTemplate.append("^FO250,580^AZN,23,23^FD签回单:"+labelForm.getReturnBillType().substring(0, 6)+"^FS\r\n");
				printStrTemplate.append("^FO250,605^AZN,23,23^FD"+labelForm.getReturnBillType().substring(6, labelForm.getReturnBillType().length())+"^FS\r\n");
			}
			if(serialNo != null && Integer.parseInt(serialNo) == 1){
				printStrTemplate.append("^FO25,625^AZN,23,23^FD件数:"+labelForm.getGoodsQtyTotal()+"^FS\r\n");
			}else{
				String s=String.valueOf(Integer.parseInt(serialNo));
				printStrTemplate.append("^FO25,625^AZN,23,23^FD件数:" + labelForm.getGoodsQtyTotal()+ "-" +s+"^FS\r\n");
			}
			//关于计费重量
			if("N".equals(labelForm.getIsHideBillWeight())){
				printStrTemplate.append("^FO250,500^AZN,23,23^FD计费重量:"+labelForm.getBillWeight()+"^FS\r\n");
			}else{
				printStrTemplate.append("^FO250,500^AZN,23,23^FD计费重量:^FS\r\n");
			}
			//关于运费
			if("N".equals(labelForm.getIsHideTransportFee())){
				printStrTemplate.append("^FO500,470^AZN,23,23^FD运费:"+labelForm.getTransportFee()+"^FS\r\n");
			}else{
				printStrTemplate.append("^FO500,470^AZN,23,23^FD运费:^FS\r\n");
			}
			//关于费用合计	
			if("N".equals(labelForm.getIsHideTotalFee())){
				printStrTemplate.append("^FO500,520^AZN,23,23^FD费用合计:"+labelForm.getTotalFee()+"^FS\r\n");
			}else{
				printStrTemplate.append("^FO500,520^AZN,23,23^FD费用合计:^FS\r\n");
			}
			printStrTemplate.append("^FO500,560^AZN,23,23^FD收方签名:^FS\r\n");
			printStrTemplate.append("^FO670,635^AZN,23,23^FD年    月    日^FS\r\n");
			//1，2联分割
			printStrTemplate.append("^FO25,665^AZN,23,23^FD上联为德邦快递存根联     下联为收件客户存根联   第三联发件客户存根联^FS\r\n");
			
			//第2联
			if(serialNo != null && Integer.parseInt(serialNo) == 1){
				printStrTemplate.append("^FO650,750^AZN,23,23^FD件数:"+labelForm.getGoodsQtyTotal()+"^FS\r\n");
			}else{
				String s=String.valueOf(Integer.parseInt(serialNo));
				printStrTemplate.append("^FO650,750^AZN,23,23^FD件数:" + labelForm.getGoodsQtyTotal()+ "-" +s+"^FS\r\n");
			}
			
			//第2联收件信息
			//收件联系人和联系方式
			printStrTemplate.append("^FO20,820^AZN,20,20^FD收件:"+labelForm.getReceiveCustomerContact()+"   "+receiverPhone+"^FS\r\n");
			if(labelForm.getReceiveCustomerAddress().length() <= 28){
				printStrTemplate.append("^FO20,845^AZN,20,20^FD"+labelForm.getReceiveCustomerAddress()+"^FS\r\n");
			}else {
				printStrTemplate.append("^FO20,845^AZN,20,20^FD"+labelForm.getReceiveCustomerAddress().substring(0, 28)+"^FS\r\n");   
				printStrTemplate.append("^FO20,870^AZN,20,20^FD"+labelForm.getReceiveCustomerAddress().substring(28, labelForm.getReceiveCustomerAddress().length())+"^FS\r\n");
			}
			//第2联寄件信息
			//关于寄件人信息
			if("N".equals(labelForm.getIsHideDeliveryCustomerInfo())){
				printStrTemplate.append("^FO20,905^AZN,20,20^FD寄件:"+labelForm.getDeliveryCustomerContact()+"   "+deliverPhone+"^FS\r\n");
				if(labelForm.getDeliveryCustomerAddress().length() <= 28){
					printStrTemplate.append("^FO20,930^AZN,20,20^FD"+labelForm.getDeliveryCustomerAddress()+"^FS\r\n");
				}else {
					printStrTemplate.append("^FO20,930^AZN,20,20^FD"+labelForm.getDeliveryCustomerAddress().substring(0, 28)+"^FS\r\n");   
					printStrTemplate.append("^FO20,955^AZN,20,20^FD"+labelForm.getDeliveryCustomerAddress().substring(28, labelForm.getDeliveryCustomerAddress().length())+"^FS\r\n");
				}
			}
			
			//货物名称
			if(labelForm.getGoodsName().length()<=24){
				printStrTemplate.append("^FO20,990^AZN,20,20^FD货物名称:"+labelForm.getGoodsName()+"^FS\r\n");
			}else if(labelForm.getGoodsName().length()>24 &&
					labelForm.getGoodsName().length()<=52){
				printStrTemplate.append("^FO20,990^AZN,20,20^FD货物名称:"+labelForm.getGoodsName().substring(0, 24)+"^FS\r\n");
				printStrTemplate.append("^FO20,1010^AZN,20,20^FD"+labelForm.getGoodsName().substring(24, labelForm.getGoodsName().length())+"^FS\r\n");
			}else if(labelForm.getGoodsName().length()>52 &&
					labelForm.getGoodsName().length()<=80){
				printStrTemplate.append("^FO20,990^AZN,20,20^FD货物名称:"+labelForm.getGoodsName().substring(0, 24)+"^FS\r\n");
				printStrTemplate.append("^FO20,1010^AZN,20,20^FD"+labelForm.getGoodsName().substring(24, 52)+"^FS\r\n");
				printStrTemplate.append("^FO20,1030^AZN,20,20^FD"+labelForm.getGoodsName().substring(52, labelForm.getGoodsName().length())+"^FS\r\n");
			}else if(labelForm.getGoodsName().length()>80 &&
					labelForm.getGoodsName().length()<=108){
						printStrTemplate.append("^FO20,990^AZN,20,20^FD货物名称:"+labelForm.getGoodsName().substring(0, 24)+"^FS\r\n");
						printStrTemplate.append("^FO20,1010^AZN,20,20^FD"+labelForm.getGoodsName().substring(24, 52)+"^FS\r\n");
						printStrTemplate.append("^FO20,1030^AZN,20,20^FD"+labelForm.getGoodsName().substring(52, 80)+"^FS\r\n");
						printStrTemplate.append("^FO20,1050^AZN,20,20^FD"+labelForm.getGoodsName().substring(80, labelForm.getGoodsName().length())+"^FS\r\n");
			}else if(labelForm.getGoodsName().length()>108 &&
					labelForm.getGoodsName().length()<=136){
				printStrTemplate.append("^FO20,990^AZN,20,20^FD货物名称:"+labelForm.getGoodsName().substring(0, 24)+"^FS\r\n");
				printStrTemplate.append("^FO20,1010^AZN,20,20^FD"+labelForm.getGoodsName().substring(24, 52)+"^FS\r\n");
				printStrTemplate.append("^FO20,1030^AZN,20,20^FD"+labelForm.getGoodsName().substring(52, 80)+"^FS\r\n");
				printStrTemplate.append("^FO20,1050^AZN,20,20^FD"+labelForm.getGoodsName().substring(80, 108)+"^FS\r\n");
				printStrTemplate.append("^FO20,1070^AZN,20,20^FD"+labelForm.getGoodsName().substring(108, labelForm.getGoodsName().length())+"^FS\r\n");
			}else if(labelForm.getGoodsName().length()>136 &&
					labelForm.getGoodsName().length()<=150){
				printStrTemplate.append("^FO20,990^AZN,20,20^FD货物名称:"+labelForm.getGoodsName().substring(0, 24)+"^FS\r\n");
				printStrTemplate.append("^FO20,1010^AZN,20,20^FD"+labelForm.getGoodsName().substring(24, 52)+"^FS\r\n");
				printStrTemplate.append("^FO20,1030^AZN,20,20^FD"+labelForm.getGoodsName().substring(52, 80)+"^FS\r\n");
				printStrTemplate.append("^FO20,1050^AZN,20,20^FD"+labelForm.getGoodsName().substring(80, 108)+"^FS\r\n");
				printStrTemplate.append("^FO20,1070^AZN,20,20^FD"+labelForm.getGoodsName().substring(108, 136)+"^FS\r\n");
				printStrTemplate.append("^FO20,1090^AZN,20,20^FD"+labelForm.getGoodsName().substring(136, labelForm.getGoodsName().length())+"^FS\r\n");
			}else{
				printStrTemplate.append("^FO20,990^AZN,20,20^FD货物名称:"+labelForm.getGoodsName().substring(0, 24)+"^FS\r\n");
				printStrTemplate.append("^FO20,1010^AZN,20,20^FD"+labelForm.getGoodsName().substring(24, 52)+"^FS\r\n");
				printStrTemplate.append("^FO20,1030^AZN,20,20^FD"+labelForm.getGoodsName().substring(52, 80)+"^FS\r\n");
				printStrTemplate.append("^FO20,1050^AZN,20,20^FD"+labelForm.getGoodsName().substring(80, 108)+"^FS\r\n");
				printStrTemplate.append("^FO20,1070^AZN,20,20^FD"+labelForm.getGoodsName().substring(108, 136)+"^FS\r\n");
				printStrTemplate.append("^FO20,1090^AZN,20,20^FD"+labelForm.getGoodsName().substring(136, 150)+"^FS\r\n");
			}
			//二维码
			printStrTemplate.append("^FO605,840^BQ,2,5^FDLAhttp://a.app.qq.com/o/simple.jsp?pkgname=com.deppon.dpapp^FS\r\n");
			//备注
			if(labelForm.getOrderNotes().length()<=30){
				printStrTemplate.append("^FO20,1145^AZN,23,23^FD备注:"+labelForm.getOrderNotes()+"^FS\r\n");
			}else{
				printStrTemplate.append("^FO20,1145^AZN,23,23^FD备注:"+labelForm.getOrderNotes().substring(0, 30)+"^FS\r\n");
			}
			if(serialNo != null && Integer.parseInt(serialNo) == 1){
				printStrTemplate.append("^FO20,1185^AZN,20,20^FD件数:"+labelForm.getGoodsQtyTotal()+"^FS\r\n");
			}else{
				String s=String.valueOf(Integer.parseInt(serialNo));
				printStrTemplate.append("^FO20,1185^AZN,20,20^FD件数:" + labelForm.getGoodsQtyTotal()+ "-" +s+"^FS\r\n");
			}
			if("N".equals(labelForm.getIsHideBillWeight())){
				printStrTemplate.append("^FO130,1185^AZN,20,20^FD计费重量:"+labelForm.getBillWeight()+"                    付款方式:"+labelForm.getPaidMethod()+"                         费用合计:"+labelForm.getTotalFee()+"^FS\r\n");
			}else{
				printStrTemplate.append("^FO130,1185^AZN,20,20^FD计费重量:                                             付款方式:"+labelForm.getPaidMethod()+"                         费用合计:"+labelForm.getTotalFee()+"^FS\r\n");
			}
			printStrTemplate.append("^FO20,1225^AZN,20,20^FD订单号:"+custOrderLine+"                                                运单号:"+labelForm.getWaybillNo()+"^FS\r\n");
			//货物名称
			if(labelForm.getGoodsName().length()<=26){
				printStrTemplate.append("^FO20,1270^AZN,25,25^FD货物名称:"+labelForm.getGoodsName()+"^FS\r\n");
			}else if(labelForm.getGoodsName().length()>26 &&
					labelForm.getGoodsName().length()<=56){
				printStrTemplate.append("^FO20,1270^AZN,25,25^FD货物名称:"+labelForm.getGoodsName().substring(0, 26)+"^FS\r\n");
				printStrTemplate.append("^FO20,1300^AZN,25,25^FD"+labelForm.getGoodsName().substring(26, labelForm.getGoodsName().length())+"^FS\r\n");
			}else if(labelForm.getGoodsName().length()>56 &&
					labelForm.getGoodsName().length()<=80){
				printStrTemplate.append("^FO20,1270^AZN,25,25^FD货物名称:"+labelForm.getGoodsName().substring(0, 26)+"^FS\r\n");
				printStrTemplate.append("^FO20,1300^AZN,25,25^FD"+labelForm.getGoodsName().substring(26, 56)+"^FS\r\n");
				printStrTemplate.append("^FO20,1330^AZN,25,25^FD"+labelForm.getGoodsName().substring(56, labelForm.getGoodsName().length())+"^FS\r\n");
			}else{
				printStrTemplate.append("^FO20,1270^AZN,25,25^FD货物名称:"+labelForm.getGoodsName().substring(0, 26)+"^FS\r\n");
				printStrTemplate.append("^FO20,1300^AZN,25,25^FD"+labelForm.getGoodsName().substring(26, 56)+"^FS\r\n");
				printStrTemplate.append("^FO20,1330^AZN,25,25^FD"+labelForm.getGoodsName().substring(56,80)+"^FS\r\n");
			}
			
			//^XZ
			printStrTemplate.append("^XZ\r\n");
			baos.write(printStrTemplate.toString().getBytes("GBK"));
			baos.write("\r\n".getBytes());
			StringBuffer end = new StringBuffer();
			baos.write(end.toString().getBytes());
			baos.close();
			outputlist.add(baos.toByteArray());
		}		
		return outputlist;
	}
	private EWaybillStubCopyForm getLabelPrintForm(LabelPrintContext context){
		EWaybillStubCopyForm printForm = new EWaybillStubCopyForm();
		// 运单号
		String waybillNo = (String) context.get("waybillNo");
		printForm.setWaybillNo( (String) context.get("waybillNo"));
		// 订单号
		printForm.setOrderNo((String) context.get("orderNo"));
		// 客户订单号
		printForm.setCustOrderLine((String) context.get("custOrderLine"));
		//订单备注
		printForm.setOrderNotes((String) context.get("orderNotes"));
		//开单部门
		printForm.setCreateOrgName((String) context.get("createOrgName"));
		//到达部门
		printForm.setCustomerPickupOrgName((String) context.get("customerPickupOrgName"));
		// 发货客户名称
		printForm.setDeliveryCustomerContact((String) context.get("deliveryCustomerContact"));
		// 发货客户手机
		printForm.setDeliveryCustomerMobilephone((String) context.get("deliveryCustomerMobilephone"));
		// 发货客户电话
		printForm.setDeliveryCustomerPhone((String) context.get("deliveryCustomerPhone"));
		// 发货具体地址
		printForm.setDeliveryCustomerAddress((String) context.get("deliveryCustomerAddress"));
		//收货客户名称
		printForm.setReceiveCustomerContact((String) context.get("receiveCustomerContact"));
		//收货客户电话
		printForm.setReceiveCustomerPhone((String) context.get("receiveCustomerPhone"));
		//收货客户手机
		printForm.setReceiveCustomerMobilephone((String) context.get("receiveCustomerMobilephone"));
		//收货具体地址
		printForm.setReceiveCustomerAddress((String) context.get("receiveCustomerAddress"));
		//货物总件数
		int goodTotal = Integer.parseInt(context.get("goodsQtyTotal").toString());
		printForm.setGoodsQtyTotal(goodTotal);
		//货物总体积
		printForm.setGoodsVolumeTotal((String) context.get("goodsVolumeTotal"));
		//货物名称
		printForm.setGoodsName((String) context.get("goodsName"));
		//计费重量
		printForm.setBillWeight((String) context.get("billWeight"));
		//运输性质名称
		printForm.setProductName((String) context.get("productName"));
		//开单付款方式
		printForm.setPaidMethod((String) context.get("paidMethod"));
		//总费用
		printForm.setTotalFee((String) context.get("totalFee"));
		//公布价运费
		printForm.setTransportFee((String) context.get("transportFee"));
		//保价声明价值
		printForm.setInsuranceAmount((String) context.get("insuranceAmount"));
		//保价费用
		printForm.setInsuranceFee((String) context.get("insuranceFee"));
		//返单类别
		printForm.setReturnBillType((String) context.get("returnBillType"));
		//代收金额
		printForm.setCodAmount( (String) context.get("codAmount"));
		//最终配载部门名称
		printForm.setLastLoadOrgName((String) context.get("lastLoadOrgName"));
		//第二外场
		printForm.setSecondOutfieldName((String) context.get("secondOutfieldName"));
		//出发城市
		printForm.setLeavecity((String) context.get("leavecity"));
		// 上边距
		printForm.setTop(context.getPrtTop());
		// 左边距
		printForm.setLeft(context.getPrtLeft());
		//是否隐藏计费重量
		printForm.setIsHideBillWeight((String) context.get("isHideBillWeight"));
		//是否隐藏发货人信息
		printForm.setIsHideDeliveryCustomerInfo((String) context.get("isHideDeliveryCustomerInfo"));
		//是否隐藏费用合计
		printForm.setIsHideTotalFee((String) context.get("isHideTotalFee"));
		//是否隐藏运费
		printForm.setIsHideTransportFee((String) context.get("isHideTransportFee"));
		//第一外场名称
		printForm.setOuterField1((String) context.get("outerField1"));
		//目的站获取
		String destination=(String)context.get("destination");
		printForm.setDestination(destination);
		//当前打印人
		printForm.setOptusernum((String) context.get("optusernum"));
		//条码部分目的站编码
		String stationnumber = (String) context.get("stationnumber");
		String[] printSerialNos = ((String) context.get("printSerialNos")).split(",");
		List<String> printPiecesList = new ArrayList<String>(goodTotal);
		List<String> barcodeList = new ArrayList<String>(goodTotal);
		for (int i = 0; i <printSerialNos.length; i++) {
			//运单号9位 + 流水号4位 + 目的站编码4位
			barcodeList.add(waybillNo + printSerialNos[i] + stationnumber); //运单号9位 + 流水号4位 + 目的站编码4位
			printPiecesList.add(printSerialNos[i]);
		}
		printForm.setBarcode(barcodeList);
		printForm.setSerialNoList(printPiecesList);	
		return printForm;
	}

	/**
	 * 转换生僻字
	 * @param destination
	 * @return
	 */
	public String revertUnow(String destination){
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
		return destination;
	}
	
	/**
	 * 判定是否为空的情况
	 * @author Foss-220125-yangxiaolong
	 * @date 2015-2-2 22:22:22
	 * @param cs
	 * @return
	 */
	private boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
}
