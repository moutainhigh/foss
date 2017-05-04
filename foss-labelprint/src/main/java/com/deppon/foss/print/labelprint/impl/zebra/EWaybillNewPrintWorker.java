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
import com.deppon.foss.print.labelprint.impl.EWaybillStubCopyForm;
import com.deppon.foss.print.labelprint.impl.LabelPrintForm;
/**
 * 德邦存根联打印模板
 * @author Foss-220125-yangxiaolon
 * @date 2015-2-2 22:22:22
 */
public class EWaybillNewPrintWorker extends LabelPrintWorker {

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
	 * @date 2015-2-2 22:22:22
	 * @author Foss-220125-yangxiaolong
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
			String accountNo = isBlank(labelForm.getAccountNo()) ? "" : labelForm.getAccountNo();
			String insuranceAmount = isBlank(labelForm.getInsuranceAmount()) ? "" : labelForm.getInsuranceAmount();
			String codeAmount = isBlank(labelForm.getCodAmount()) ? "" : labelForm.getCodAmount();
		
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
			printStrTemplate.append("^FO12,20^GB776,1175,3^FS\r\n");
			printStrTemplate.append("^FO15,715^GB768,0,3^FS\r\n");
			printStrTemplate.append("^FO14,160^GB773,0,3^FS\r\n");
			printStrTemplate.append("^FO414,23^GB0,141,3^FS\r\n");
			printStrTemplate.append("^FO14,519^GB774,0,3^FS\r\n");
			printStrTemplate.append("^FO15,674^GB773,0,3^FS\r\n");
			printStrTemplate.append("^FO397,520^GB0,157,3^FS\r\n");
			printStrTemplate.append("^FO401,631^GB384,0,3^FS\r\n");
			printStrTemplate.append("^FO12,841^GB773,0,3^FS\r\n");
			printStrTemplate.append("^FO15,914^GB773,0,3^FS\r\n");
			printStrTemplate.append("^FO15,1159^GB773,0,3^FS\r\n");
			printStrTemplate.append("^FO14,236^GB769,0,3^FS\r\n");
			printStrTemplate.append("^FO12,400^GB775,0,3^FS\r\n");
			printStrTemplate.append("^FO16,480^GB769,0,3^FS\r\n");
			printStrTemplate.append("^FO16,440^GB774,0,3^FS\r\n");
			printStrTemplate.append("^FO397,401^GB0,81,3^FS\r\n");
			printStrTemplate.append("^FO587,402^GB0,233,3^FS\r\n");
			printStrTemplate.append("^FO199,401^GB0,81,3^FS\r\n");
			printStrTemplate.append("^FO486,162^GB0,238,3^FS\r\n");
			printStrTemplate.append("^FO490,316^GB295,0,3^FS\r\n");
			printStrTemplate.append("^FO12,1055^GB578,0,3^FS\r\n");
			printStrTemplate.append("^FO587,915^GB0,248,3^FS\r\n");
			//printStrTemplate.append("^FO43,108^AZN,61,40^FD德邦快递^FS\r\n");
			//printStrTemplate.append("^FO227,79^AZN,28,28^FD95353^FS\r\n");
			//printStrTemplate.append("^FO205,141^AZN,28,19^FDwww.deppon.com^FS\r\n");
			//这三个值获取后直接塞进去即可
			printStrTemplate.append("^FO23,540^AZN,26,26^FD保价金额："+insuranceAmount+"^FS\r\n");
			printStrTemplate.append("^FO23,580^AZN,26,26^FD代收货款："+codeAmount+"^FS\r\n");
			printStrTemplate.append("^FO23,618^AZN,26,26^FD代收账号： "+accountNo+"^FS\r\n");
			//以下是一些写死的代码！！！
			printStrTemplate.append("^FO590,493^AZN,24,24^FD收件员：^FS\r\n");
			
			//官网代码优化   yangxiaolong
			if(null==labelForm.getOrderNotes()){
				printStrTemplate.append("^FO400,642^AZN,24,24^FD备注栏：^FS\r\n");}
			else if(null!=labelForm.getOrderNotes()&&labelForm.getOrderNotes().length()<=10){
			printStrTemplate.append("^FO400,642^AZN,24,24^FD备注栏："+labelForm.getOrderNotes()+"^FS\r\n");
			}else{
				printStrTemplate.append("^FO400,642^AZN,24,24^FD备注栏："+labelForm.getOrderNotes().substring(0, 10)+"^FS\r\n");
			}
			
			printStrTemplate.append("^FO400,528^AZN,24,24^FD收方签名：^FS\r\n");
			printStrTemplate.append("^FO468,600^AZN,24,24^FD月      日^FS\r\n");
			printStrTemplate.append("^FO590,528^AZN,24,24^FD派件员：^FS\r\n");
			printStrTemplate.append("^FO106,690^AZN,24,24^FD上联为德邦快递存根联     下联为收件客户存根联^FS\r\n");
			//printStrTemplate.append("^FO555,265^AZN,45,45^FD已验视^FS\r\n");
			printStrTemplate.append("^FO23,1169^AZN,24,24^FD此运单仅供德邦签约客户使用，相关责任业务以双方签约合同为主^FS\r\n");
			//条码打印
			printStrTemplate.append("^FO452,35^BY2,3,100^BCN,100,Y,N^FD>;"+barcode+"^FS\r\n");
             //以下为上联打印时的一些数据封装
			printStrTemplate.append("^FO23,173^AZN,26,26^FD寄件：^FS\r\n");
			printStrTemplate.append("^FO23,207^AZN,25,26^FD电话：^FS\r\n");
	/**
	 * 此段逻辑为复选框部分，注意传入值为“N”时才打印
	 * 
	 * */
			//关于寄件人信息
			if("N".equals(labelForm.getIsHideDeliveryCustomerInfo())){
				//printStrTemplate.append("^FO117,174^AZN,26,24^FD酒仙网有限责任公司^FS\r\n");
				String deliveryCustomerContact = labelForm.getDeliveryCustomerContact();
				if(deliveryCustomerContact.length() <= 16){
					printStrTemplate.append("^FO100,174^AZN,24,24^FD" + deliveryCustomerContact + "^FS\r\n");
				}else if(deliveryCustomerContact.length() >16){
					printStrTemplate.append("^FO100,174^AZN,24,24^FD" +deliveryCustomerContact.substring(0,16) + "^FS\r\n");
				}
				//printStrTemplate.append("^FO127,207^AZN,26,24^FD18720120214^FS\r\n");
				printStrTemplate.append("^FO94,207^AZN,24,24^FD"+deliverPhone+"^FS\r\n");
			}
			//关于计费重量
			if("N".equals(labelForm.getIsHideBillWeight())){
				printStrTemplate.append("^FO270,448^AZN,24,24^FD"+labelForm.getBillWeight()+"^FS\r\n");
			}
			//关于运费
			if("N".equals(labelForm.getIsHideTransportFee())){
				printStrTemplate.append("^FO480,448^AZN,24,24^FD"+labelForm.getTransportFee()+"^FS\r\n");
			}
			//关于费用合计	
			if("N".equals(labelForm.getIsHideTotalFee())){
				printStrTemplate.append("^FO670,448^AZN,24,24^FD"+labelForm.getTotalFee()+"^FS\r\n");
				}	
			//表格数据的封装
			printStrTemplate.append("^FO63,408^AZN,26,26^FD件数^FS\r\n");
			printStrTemplate.append("^FO250,408^AZN,26,26^FD计费重量^FS\r\n");
			printStrTemplate.append("^FO460,408^AZN,26,26^FD运费^FS\r\n");
			printStrTemplate.append("^FO640,408^AZN,26,26^FD费用合计^FS\r\n");
			printStrTemplate.append("^FO80,448^AZN,24,24^FD" +labelForm.getGoodsQtyTotal()+"^FS\r\n");
			//目的站                 
			printStrTemplate.append("^FO495,243^AZN,26,26^FD目的站：^FS\r\n");
			if(labelForm.getDestination()!=null && !labelForm.getDestination().equals("")){
			if(labelForm.getDestination().length() <=4){
				printStrTemplate.append("^FO590,250^AZN,43,43^FD"+labelForm.getDestination()+"^FS\r\n");
			}else if(labelForm.getDestination().length() >4){
				printStrTemplate.append("^FO590,250^AZN,43,43^FD"+labelForm.getDestination().substring(0, 4)+"^FS\r\n");
			}
			}
			//上联收件人信息设置
			printStrTemplate.append("^FO23,243^AZN,26,26^FD收件：^FS\r\n");
			//printStrTemplate.append("^FO128,273^AZN,26,24^FD张晓  12365487541^FS\r\n");
			if(labelForm.getReceiveCustomerContact().length() <= 16){
				printStrTemplate.append("^FO94,243^AZN,24,24^FD"+labelForm.getReceiveCustomerContact() + "^FS\r\n");
			}else if(labelForm.getReceiveCustomerContact().length() >16){
				printStrTemplate.append("^FO94,243^AZN,24,24^FD"+labelForm.getReceiveCustomerContact().substring(0,16) + "^FS\r\n");
			}
			printStrTemplate.append("^FO26,275^AZN,24,24^FD"+ receiverPhone+"^FS\r\n");
			//printStrTemplate.append("^FO123,320^AZN,26,24^FD北京大鲁店食堂中心3楼真的好^FS\r\n");
			if(labelForm.getReceiveCustomerAddress().length() <= 19){
				printStrTemplate.append("^FO26,310^AZN,24,24^FD"+labelForm.getReceiveCustomerAddress()+"^FS\r\n");
			}else if(labelForm.getReceiveCustomerAddress().length() >19&&labelForm.getReceiveCustomerAddress().length() <=38){
				printStrTemplate.append("^FO26,310^AZN,24,24^FD"+labelForm.getReceiveCustomerAddress().substring(0, 19)+"^FS\r\n");   
				printStrTemplate.append("^FO26,345^AZN,24,24^FD"+labelForm.getReceiveCustomerAddress().substring(19, labelForm.getReceiveCustomerAddress().length())+"^FS\r\n");
			}	else if(labelForm.getReceiveCustomerAddress().length() >38&&labelForm.getReceiveCustomerAddress().length() <=57){
				printStrTemplate.append("^FO26,305^AZN,24,24^FD"+labelForm.getReceiveCustomerAddress().substring(0, 19)+"^FS\r\n");  
				printStrTemplate.append("^FO26,340^AZN,24,24^FD"+labelForm.getReceiveCustomerAddress().substring(19, 38)+"^FS\r\n");  
				printStrTemplate.append("^FO26,375^AZN,24,24^FD"+labelForm.getReceiveCustomerAddress().substring(38, labelForm.getReceiveCustomerAddress().length())+"^FS\r\n");
			}	else if(labelForm.getReceiveCustomerAddress().length() >57){
				printStrTemplate.append("^FO26,305^AZN,24,24^FD"+labelForm.getReceiveCustomerAddress().substring(0, 19)+"^FS\r\n");  
				printStrTemplate.append("^FO26,340^AZN,24,24^FD"+labelForm.getReceiveCustomerAddress().substring(19, 38)+"^FS\r\n");  
				printStrTemplate.append("^FO26,375^AZN,24,24^FD"+labelForm.getReceiveCustomerAddress().substring(38, 57)+"^FS\r\n");
			}				

			printStrTemplate.append("^FO25,488^AZN,26,26^FD付款方式：^FS\r\n");
			//printStrTemplate.append("^FO165,488^AZN,26,24^FD到付^FS\r\n");
			printStrTemplate.append("^FO162,488^AZN,24,24^FD"+labelForm.getPaidMethod()+"^FS\r\n");
			printStrTemplate.append("^FO495,173^AZN,26,26^FD原寄地：^FS\r\n");
			//printStrTemplate.append("^FO495,205^AZN,26,24^FD上海市徐家汇区大鲁店中心^FS\r\n");//最多12个字符串
			if(labelForm.getLeavecity().length() <=8){
				printStrTemplate.append("^FO590,190^AZN,24,24^FD"+labelForm.getLeavecity()+"^FS\r\n");
			}else if(labelForm.getLeavecity().length() >8){
				printStrTemplate.append("^FO590,190^AZN,24,24^FD"+labelForm.getLeavecity().substring(0, 8)+"^FS\r\n");
			}
			printStrTemplate.append("^FO495,330^AZN,26,26^FD运输方式：^FS\r\n");
			printStrTemplate.append("^FO620,330^AZN,35,35^FD已验视^FS\r\n");
			//printStrTemplate.append("^FO495,365^AZN,26,24^FD360特惠价^FS\r\n");
			printStrTemplate.append("^FO600,365^AZN,24,24^FD"+labelForm.getProductName()+"^FS\r\n");
			//打印联下联数据设计
			//条形码（此处要求只打印运单）
			printStrTemplate.append("^FO415,733^BY2,3,75^BCN,80,Y,N^FD>;"+labelForm.getWaybillNo()+"^FS\r\n");
			printStrTemplate.append("^FO23,852^AZN,26,26^FD收件：^FS\r\n");
			//printStrTemplate.append("^FO108,852^AZN,26,24^FD张二^FS\r\n");
			//printStrTemplate.append("^FO218,852^AZN,26,2                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               004^FD18721080858^FS\r\n");
			if(labelForm.getReceiveCustomerContact().length() <=15){
				printStrTemplate.append("^FO100,852^AZN,24,24^^FD"+labelForm.getReceiveCustomerContact()+" "+ receiverPhone+"^FS\r\n");
			}else if(labelForm.getReceiveCustomerContact().length() >15){
				printStrTemplate.append("^FO100,852^AZN,24,24^^FD"+labelForm.getReceiveCustomerContact().substring(0, 15)+" "+ receiverPhone+"^FS\r\n");
			}
			//printStrTemplate.append("^FO105,890^AZN,26,24^FD北京市朝阳区大鲁店食堂饭店CEO张大婶^FS\r\n");
				if(labelForm.getReceiveCustomerAddress().length() <= 31){
					printStrTemplate.append("^FO26,890^AZN,24,24^FD"+labelForm.getReceiveCustomerAddress()+"^FS\r\n");
				}else if(labelForm.getReceiveCustomerAddress().length() >31){
					printStrTemplate.append("^FO26,890^AZN,24,24^FD"+labelForm.getReceiveCustomerAddress().substring(0, 31)+"^FS\r\n");
				}
			//printStrTemplate.append("^FO611,852^AZN,30,30^FD件数：^FS\r\n");
			//printStrTemplate.append("^FO692,852^AZN,26,24^FD5-2^FS\r\n");
			
				
			/*	if(serialNo != null && Integer.parseInt(serialNo) == 1){
					printStrTemplate.append("^FO05,420^AZN,24,24^FD件数:" +labelForm.getGoodsQtyTotal()+"^FS\r\n");
				}else{
					printStrTemplate.append("^FO05,420^AZN,24,24^FD件数:" +serialNo + "/" + labelForm.getGoodsQtyTotal()+"^FS\r\n");
				}
				*/
			if(serialNo != null && Integer.parseInt(serialNo) == 1){
				printStrTemplate.append("^FO620,758^AZN,26,26^FD件数:" +labelForm.getGoodsQtyTotal()+"^FS\r\n");	
			}else{
				String s=String.valueOf(Integer.parseInt(serialNo));
				printStrTemplate.append("^FO620,758^AZN,26,26^FD件数:" + labelForm.getGoodsQtyTotal()+ "-" +s+"^FS\r\n");
			}
			//此处需要重新获取二维码扫描地址      后期由於打印紙變更，故不需再添加二維碼
			//printStrTemplate.append("^FO597,1005^AZN,169,43^FD二维码^FS\r\n");
			//printStrTemplate.append("^FO602,860^BQ,2,6^FDLAhttp://a.app.qq.com/o/simple.jsp?pkgname=com.deppon.dpapp^FS\r\n");
			
			printStrTemplate.append("^FO23,930^AZN,26,26^FD寄件：^FS\r\n");
			
			/**
			 * 此段逻辑为复选框部分，注意传入值为“N”时才打印
			 * 
			 */
					//关于寄件人信息
		if("N".equals(labelForm.getIsHideDeliveryCustomerInfo())){
			//printStrTemplate.append("^FO142,930^AZN,26,24^FD阿里巴巴淘宝网公司  6666666666666^FS\r\n");
			
			String deliveryCustomerContact = labelForm.getDeliveryCustomerContact();
			if(deliveryCustomerContact.length() <= 20){
				printStrTemplate.append("^FO94,930^AZN,24,24^FD"  + deliveryCustomerContact + "^FS\r\n");
			}else if(deliveryCustomerContact.length() >20){
				printStrTemplate.append("^FO94,930^AZN,24,24^FD"  +deliveryCustomerContact.substring(0,20) + "^FS\r\n");
			}
			//printStrTemplate.append("^FO127,207^AZN,26,24^FD18720120214^FS\r\n");
			printStrTemplate.append("^FO26,960^AZN,24,24^FD" + deliverPhone + "^FS\r\n");
			//deliveryCustomerAddress
			//printStrTemplate.append("^FO30,968^AZN,26,24^FD上海市青浦区徐泾镇1018号德邦公寓CTO赵大伯^FS\r\n");
			   if(labelForm.getDeliveryCustomerAddress().length() <= 23){
			printStrTemplate.append("^FO26,990^AZN,24,24^FD"+labelForm.getDeliveryCustomerAddress()+"^FS\r\n");
				}else if(labelForm.getDeliveryCustomerAddress().length() >23&&labelForm.getDeliveryCustomerAddress().length() <=46){
			printStrTemplate.append("^FO26,990^AZN,24,24^FD"+labelForm.getDeliveryCustomerAddress().substring(0, 23)+"^FS\r\n");
			printStrTemplate.append("^FO26,1020^AZN,24,24^FD"+labelForm.getDeliveryCustomerAddress().substring(23,labelForm.getDeliveryCustomerAddress().length() )+"^FS\r\n");
				}else if(labelForm.getDeliveryCustomerAddress().length() >46){
					printStrTemplate.append("^FO26,990^AZN,24,24^FD"+labelForm.getDeliveryCustomerAddress().substring(0, 23)+"^FS\r\n");
					printStrTemplate.append("^FO26,1020^AZN,24,24^FD"+labelForm.getDeliveryCustomerAddress().substring(23,46 )+"^FS\r\n");
						}
				}
			printStrTemplate.append("^FO23,1062^AZN,26,26^FD货物名称：^FS\r\n");
			//printStrTemplate.append("^FO35,1082^AZN,26,24^FD红酒       腊肉      火锅^FS\r\n");
			//printStrTemplate.append("^FO35,1118^AZN,24,24^FD"+labelForm.getGoodsName()+"^FS\r\n"); 

			if(labelForm.getGoodsName().length() <= 18){
				printStrTemplate.append("^FO140,1062^AZN,24,24^FD"+labelForm.getGoodsName()+"^FS\r\n");
			}else if(labelForm.getGoodsName().length() >18&&labelForm.getGoodsName().length()<=40){
				printStrTemplate.append("^FO140,1062^AZN,24,24^FD"+labelForm.getGoodsName().substring(0, 18)+"^FS\r\n");
				printStrTemplate.append("^FO26,1092^AZN,24,24^FD"+labelForm.getGoodsName().substring(18,labelForm.getGoodsName().length() )+"^FS\r\n");
			}else if(labelForm.getGoodsName().length() >40&&labelForm.getGoodsName().length()<=62){
				printStrTemplate.append("^FO140,1062^AZN,24,24^FD"+labelForm.getGoodsName().substring(0, 18)+"^FS\r\n");
				printStrTemplate.append("^FO26,1092^AZN,24,24^FD"+labelForm.getGoodsName().substring(18, 40)+"^FS\r\n");
				printStrTemplate.append("^FO26,1122^AZN,24,24^FD"+labelForm.getGoodsName().substring(40,labelForm.getGoodsName().length() )+"^FS\r\n");
			}else if(labelForm.getGoodsName().length() >62){
				printStrTemplate.append("^FO140,1062^AZN,24,24^FD"+labelForm.getGoodsName().substring(0, 18)+"^FS\r\n");
				printStrTemplate.append("^FO26,1092^AZN,24,24^FD"+labelForm.getGoodsName().substring(18, 40)+"^FS\r\n");
				printStrTemplate.append("^FO26,1122^AZN,24,24^FD"+labelForm.getGoodsName().substring(40,62)+"^FS\r\n");
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
		printForm.setWaybillNo(waybillNo);
		// 订单号
		printForm.setOrderNo((String) context.get("orderNo"));
		// 付款方式
		printForm.setOrderPaidMethod((String) context.get("orderPaidMethod"));
		
		// 发货客户名称
		printForm.setDeliveryCustomerName((String) context.get("deliveryCustomerContact"));
		// 发货客户手机
		printForm.setDeliveryCustomerMobilephone((String) context.get("deliveryCustomerMobilephone"));
		// 发货客户电话
		printForm.setDeliveryCustomerPhone((String) context.get("deliveryCustomerPhone"));
		// 发货客户联系人
		printForm.setDeliveryCustomerContact((String) context.get("deliveryCustomerContact"));
		// 发货具体地址
		printForm.setDeliveryCustomerAddress((String) context.get("deliveryCustomerAddress"));
		//收货客户名称
		printForm.setReceiveCustomerName((String) context.get("receiveCustomerContact"));
		//收货客户手机
		printForm.setReceiveCustomerPhone((String) context.get("receiveCustomerPhone"));
		//收货客户手机/联系方式
		printForm.setReceiveCustomerMobilephone((String) context.get("receiveCustomerMobilephone"));
		printForm.setReceiveCustomerContact((String) context.get("receiveCustomerContact"));
		//收获具体地址
		printForm.setReceiveCustomerAddress((String) context.get("receiveCustomerAddress"));
		
		//收货部门
		printForm.setReceiveOrgName((String) context.get("receiveOrgName"));
		//开单部门
		printForm.setCreateOrgName((String) context.get("createOrgName"));
		//收货部门
		printForm.setCustomerPickupOrgName((String) context.get("customerPickupOrgName"));
		//货物名称
		printForm.setGoodsName((String) context.get("goodsName"));
		//货物总件数
		int goodTotal = Integer.parseInt(context.get("goodsQtyTotal").toString());
		printForm.setGoodsQtyTotal(goodTotal);
		//货物总体积
		printForm.setGoodsVolumeTotal((String) context.get("goodsVolumeTotal"));
		//货物总重量
		printForm.setGoodsWeightTotal((String) context.get("goodsWeightTotal"));
		//计费重量
		printForm.setBillWeight((String) context.get("billWeight"));
		//运输性质名称
		printForm.setProductName((String) context.get("productName"));
		//提货方式
		printForm.setPaidMethod((String) context.get("paidMethod"));
		//开单付款方式
		printForm.setTotalFee((String) context.get("totalFee"));
		//总运费
		printForm.setTransportFee((String) context.get("transportFee"));
		
		//货物类型名称
		printForm.setGoodsTypeName((String) context.get("goodsTypeName"));
		//是否贵重物品
		printForm.setPreciousGoods((String) context.get("preciousGoods"));
		//货物包装
		printForm.setGoodsPackage((String) context.get("goodsPackage"));
		//保价声明价值
		printForm.setInsuranceAmount((String) context.get("insuranceAmount"));
		//包装费
		printForm.setPackageFee((String) context.get("packageFee"));
		//返单类别
		printForm.setReturnBillType((String) context.get("returnBillType"));
		//代收金额
		printForm.setCodAmount((String) context.get("codAmount"));
		//代收货款账号
		printForm.setAccountNo((String) context.get("accountNo"));
		//快递员(收)
		printForm.setDeliverMan((String) context.get("deliverMan"));
		//收获具体地址
		printForm.setReceiveDate((String) context.get("receiveDate"));
		//收获具体地址
		printForm.setReceiverMan((String) context.get("receiverMan"));
		//派送日期
		printForm.setDeliverDate((String) context.get("deliverDate"));
		//第二城市外场名称
		printForm.setSecondOutfieldName((String) context.get("secondOutfieldName"));
		//最终配载部门名称
		printForm.setLastLoadOrgName((String) context.get("lastLoadOrgName"));
		//出发城市
		printForm.setLeavecity((String) context.get("leavecity"));
		//派送日期
		printForm.setDeliverDate((String) context.get("deliverDate"));
		// 上边距
		printForm.setTop(context.getPrtTop());
		// 左边距
		printForm.setLeft(context.getPrtLeft());
		//六大外场名称
		printForm.setOuterField1((String) context.get("outerField1"));
		printForm.setOuterField2((String) context.get("outerField2"));
		printForm.setOuterField3((String) context.get("outerField3"));
		printForm.setOuterField4((String) context.get("outerField4"));
		printForm.setOuterField5((String) context.get("outerField5"));
		printForm.setOuterField6((String) context.get("outerField6"));
		//订单备注
	    printForm.setOrderNotes((String) context.get("orderNotes"));
		//关于复选框的子段说明
		//是否隐藏计费重量
		printForm.setIsHideBillWeight((String) context.get("isHideBillWeight"));
		//是否隐藏发货人信息
		printForm.setIsHideDeliveryCustomerInfo((String) context.get("isHideDeliveryCustomerInfo"));
		//是否隐藏费用合计
		printForm.setIsHideTotalFee((String) context.get("isHideTotalFee"));
		//是否隐藏运费
		printForm.setIsHideTransportFee((String) context.get("isHideTransportFee"));
		//六大库位
		printForm.setLocation1((String) context.get("location1"));
		printForm.setLocation2((String) context.get("location2"));
		printForm.setLocation3((String) context.get("location3"));
		printForm.setLocation4((String) context.get("location4"));
		printForm.setLocation5((String) context.get("location5"));
		printForm.setLocation6((String) context.get("location6"));
		//是否打印送字
		String isDeliver = (String) context.get("isDeliver");
		printForm.setIsDeliver(isDeliver);
		//货物总件数destination
		String isPrintStar = (String) context.get("isPrintStar");
		printForm.setIsPrintStar(isPrintStar);
		//目的站获取
		String destination=(String)context.get("destination");
		printForm.setDestination(destination);
		//公司微信地址获取
		//String weixinAddr=(String)context.get("weixinAddr");
		//printForm.setWeixinAddr(weixinAddr);
		//当前打印人
		printForm.setOptusernum((String) context.get("optusernum"));
		//是否打印@
		String isPrintAt = (String) context.get("isPrintAt");
		printForm.setIsPrintAt(isPrintAt);
		//发货大客户
		printForm.setDeliveryBigCustomer((String) context.get("deliveryBigCustomer"));
		//收获大客户
		printForm.setReceiveBigCustomer((String) context.get("receiveBigCustomer"));
		
		String stationnumber = (String) context.get("stationnumber");
		String[] printSerialNos = ((String) context.get("printSerialNos")).split(",");
		List<String> printPiecesList = new ArrayList(goodTotal);
		List<String> barcodeList = new ArrayList(goodTotal);
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
