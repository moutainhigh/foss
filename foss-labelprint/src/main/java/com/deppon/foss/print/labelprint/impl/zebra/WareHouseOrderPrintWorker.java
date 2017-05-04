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
/**
 * 仓库分拣单
 * @author foss-218438
 */
public class WareHouseOrderPrintWorker extends LabelPrintWorker {

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
		printStrTemplate.append("^FO6,10^GB786,1180,2^FS\r\n");
		printStrTemplate.append("^FO8,123^GB783,0,2^FS\r\n");
		printStrTemplate.append("^FO8,363^GB785,0,2^FS\r\n");
		printStrTemplate.append("^FO6,715^GB784,0,2^FS\r\n");
		printStrTemplate.append("^FO6,844^GB785,0,2^FS\r\n");
		printStrTemplate.append("^FO6,958^GB785,0,2^FS\r\n");
		printStrTemplate.append("^FO6,1143^GB785,0,2^FS\r\n");
		printStrTemplate.append("^FO377,11^GB0,113,2^FS\r\n");
		
		//内容
		printStrTemplate.append("^BY4,3,58^FT410,794^BCN,,Y,N\r\n");
		printStrTemplate.append("^FD>;"+labelForm.getWaybillNo()+"^FS\r\n");
		printStrTemplate.append("^BY4,3,58^FT402,75^BCN,,Y,N\r\n");
		printStrTemplate.append("^FD>;"+labelForm.getWaybillNo()+"^FS\r\n");
		printStrTemplate.append("^FO190,133^AZN,60,60^FD仓库分拣单^FS\r\n");
		printStrTemplate.append("^FO15,210^AZN,28,28^FD订单号:"+custOrderLine+"^FS\r\n");
		printStrTemplate.append("^FO400,210^AZN,28,28^FD订单创建时间:"+labelForm.getOrderTime()+"^FS\r\n");
		printStrTemplate.append("^FO15,250^AZN,28,28^FD运单号:"+labelForm.getWaybillNo()+"^FS\r\n");
		printStrTemplate.append("^FO15,290^AZN,28,28^FD收货人:"+labelForm.getReceiveCustomerContact()+"^FS\r\n");
		printStrTemplate.append("^FO15,330^AZN,28,28^FD发货人:"+labelForm.getDeliveryCustomerContact()+"^FS\r\n");
		printStrTemplate.append("^FO500,270^AZN,28,28^FD件数:"+labelForm.getGoodsQtyTotal()+"^FS\r\n");
		//关于计费重量
		printStrTemplate.append("^FO500,330^AZN,28,28^FD重量:"+labelForm.getBillWeight()+"^FS\r\n");
		if(labelForm.getGoodsName().length()<=23){
			printStrTemplate.append("^FO15,373^AZN,28,28^FD货物名称:"+labelForm.getGoodsName()+"^FS\r\n");
		}else if(labelForm.getGoodsName().length()>23 &&
						labelForm.getGoodsName().length()<=50){
			printStrTemplate.append("^FO15,373^AZN,28,28^FD货物名称:"+labelForm.getGoodsName().substring(0, 23)+"^FS\r\n");
			printStrTemplate.append("^FO15,403^AZN,28,28^FD"+labelForm.getGoodsName().substring(23, labelForm.getGoodsName().length())+"^FS\r\n");
		}else if(labelForm.getGoodsName().length()>50 &&
				labelForm.getGoodsName().length()<=77){
			printStrTemplate.append("^FO15,373^AZN,28,28^FD货物名称:"+labelForm.getGoodsName().substring(0, 23)+"^FS\r\n");
			printStrTemplate.append("^FO15,403^AZN,28,28^FD"+labelForm.getGoodsName().substring(23, 50)+"^FS\r\n");
			printStrTemplate.append("^FO15,433^AZN,28,28^FD"+labelForm.getGoodsName().substring(50, labelForm.getGoodsName().length())+"^FS\r\n");
		}else if(labelForm.getGoodsName().length()>77 &&
				labelForm.getGoodsName().length()<=104){
			printStrTemplate.append("^FO15,373^AZN,28,28^FD货物名称:"+labelForm.getGoodsName().substring(0, 23)+"^FS\r\n");
			printStrTemplate.append("^FO15,403^AZN,28,28^FD"+labelForm.getGoodsName().substring(23, 50)+"^FS\r\n");
			printStrTemplate.append("^FO15,433^AZN,28,28^FD"+labelForm.getGoodsName().substring(50, 77)+"^FS\r\n");
			printStrTemplate.append("^FO15,463^AZN,28,28^FD"+labelForm.getGoodsName().substring(77, labelForm.getGoodsName().length())+"^FS\r\n");
		}else if(labelForm.getGoodsName().length()>104 &&
				labelForm.getGoodsName().length()<=131){
			printStrTemplate.append("^FO15,373^AZN,28,28^FD货物名称:"+labelForm.getGoodsName().substring(0, 23)+"^FS\r\n");
			printStrTemplate.append("^FO15,403^AZN,28,28^FD"+labelForm.getGoodsName().substring(23, 50)+"^FS\r\n");
			printStrTemplate.append("^FO15,433^AZN,28,28^FD"+labelForm.getGoodsName().substring(50, 77)+"^FS\r\n");
			printStrTemplate.append("^FO15,463^AZN,28,28^FD"+labelForm.getGoodsName().substring(77 ,104)+"^FS\r\n");
			printStrTemplate.append("^FO15,493^AZN,28,28^FD"+labelForm.getGoodsName().substring(104, labelForm.getGoodsName().length())+"^FS\r\n");
		}else if(labelForm.getGoodsName().length()>131 &&
				labelForm.getGoodsName().length()<=150){
			printStrTemplate.append("^FO15,373^AZN,28,28^FD货物名称:"+labelForm.getGoodsName().substring(0, 23)+"^FS\r\n");
			printStrTemplate.append("^FO15,403^AZN,28,28^FD"+labelForm.getGoodsName().substring(23, 50)+"^FS\r\n");
			printStrTemplate.append("^FO15,433^AZN,28,28^FD"+labelForm.getGoodsName().substring(50, 77)+"^FS\r\n");
			printStrTemplate.append("^FO15,463^AZN,28,28^FD"+labelForm.getGoodsName().substring(77 ,104)+"^FS\r\n");
			printStrTemplate.append("^FO15,493^AZN,28,28^FD"+labelForm.getGoodsName().substring(104 ,131)+"^FS\r\n");
			printStrTemplate.append("^FO15,523^AZN,28,28^FD"+labelForm.getGoodsName().substring(131, labelForm.getGoodsName().length())+"^FS\r\n");
		}else{
			printStrTemplate.append("^FO15,373^AZN,28,28^FD货物名称:"+labelForm.getGoodsName().substring(0, 23)+"^FS\r\n");
			printStrTemplate.append("^FO15,403^AZN,28,28^FD"+labelForm.getGoodsName().substring(23, 50)+"^FS\r\n");
			printStrTemplate.append("^FO15,433^AZN,28,28^FD"+labelForm.getGoodsName().substring(50, 77)+"^FS\r\n");
			printStrTemplate.append("^FO15,463^AZN,28,28^FD"+labelForm.getGoodsName().substring(77 ,104)+"^FS\r\n");
			printStrTemplate.append("^FO15,493^AZN,28,28^FD"+labelForm.getGoodsName().substring(104 ,131)+"^FS\r\n");
			printStrTemplate.append("^FO15,523^AZN,28,28^FD"+labelForm.getGoodsName().substring(131, 150)+"^FS\r\n");
		}
		
		
		if(labelForm.getReceiveCustomerAddress().length()<=22){
			printStrTemplate.append("^FO15,854^AZN,28,28^FD收件人地址:"+labelForm.getReceiveCustomerAddress()+"^FS\r\n");
		}else if(labelForm.getReceiveCustomerAddress().length()>22 &&
				labelForm.getReceiveCustomerAddress().length()<=49){
			printStrTemplate.append("^FO15,854^AZN,28,28^FD收件人地址:"+labelForm.getReceiveCustomerAddress().substring(0, 22)+"^FS\r\n");
			printStrTemplate.append("^FO15,884^AZN,28,28^FD"+labelForm.getReceiveCustomerAddress().substring(22,labelForm.getReceiveCustomerAddress().length())+"^FS\r\n");
		}else if(labelForm.getReceiveCustomerAddress().length()>49){
			printStrTemplate.append("^FO15,854^AZN,28,28^FD收件人地址:"+labelForm.getReceiveCustomerAddress().substring(0, 22)+"^FS\r\n");
			printStrTemplate.append("^FO15,854^AZN,28,28^FD"+labelForm.getReceiveCustomerAddress().substring(22, 49)+"^FS\r\n");
			printStrTemplate.append("^FO15,884^AZN,28,28^FD"+labelForm.getReceiveCustomerAddress().substring(49,76)+"^FS\r\n");
		}
		
		if(labelForm.getOrderNotes().length()<=19){
			printStrTemplate.append("^FO15,970^AZN,28,28^FD备注:"+labelForm.getOrderNotes()+"^FS\r\n");
		}else if(labelForm.getOrderNotes().length()>19 &&
				labelForm.getOrderNotes().length()<=40){
			printStrTemplate.append("^FO15,970^AZN,28,28^FD备注:"+labelForm.getOrderNotes().substring(0, 19)+"^FS\r\n");
			printStrTemplate.append("^FO15,1000^AZN,28,28^FD"+labelForm.getOrderNotes().substring(19, labelForm.getOrderNotes().length())+"^FS\r\n");
		}else if(labelForm.getOrderNotes().length()>40 &&
				labelForm.getOrderNotes().length()<=61){
				printStrTemplate.append("^FO15,970^AZN,28,28^FD备注:"+labelForm.getOrderNotes().substring(0, 19)+"^FS\r\n");
				printStrTemplate.append("^FO15,1000^AZN,28,28^FD"+labelForm.getOrderNotes().substring(19, 40)+"^FS\r\n");
				printStrTemplate.append("^FO15,1030^AZN,28,28^FD"+labelForm.getOrderNotes().substring(40, labelForm.getOrderNotes().length())+"^FS\r\n");
		}else if(labelForm.getOrderNotes().length()>61 &&
				labelForm.getOrderNotes().length()<=82){
				printStrTemplate.append("^FO15,970^AZN,28,28^FD备注:"+labelForm.getOrderNotes().substring(0, 19)+"^FS\r\n");
				printStrTemplate.append("^FO15,1000^AZN,28,28^FD"+labelForm.getOrderNotes().substring(19, 40)+"^FS\r\n");
				printStrTemplate.append("^FO15,1030^AZN,28,28^FD"+labelForm.getOrderNotes().substring(40, 61)+"^FS\r\n");
				printStrTemplate.append("^FO15,1060^AZN,28,28^FD"+labelForm.getOrderNotes().substring(61, labelForm.getOrderNotes().length())+"^FS\r\n");
		}else if(labelForm.getOrderNotes().length()>82 &&
				labelForm.getOrderNotes().length()<=100){
				printStrTemplate.append("^FO15,970^AZN,28,28^FD备注:"+labelForm.getOrderNotes().substring(0, 19)+"^FS\r\n");
				printStrTemplate.append("^FO15,1000^AZN,28,28^FD"+labelForm.getOrderNotes().substring(19, 40)+"^FS\r\n");
				printStrTemplate.append("^FO15,1030^AZN,28,28^FD"+labelForm.getOrderNotes().substring(40, 61)+"^FS\r\n");
				printStrTemplate.append("^FO15,1060^AZN,28,28^FD"+labelForm.getOrderNotes().substring(61, 82)+"^FS\r\n");
				printStrTemplate.append("^FO15,1090^AZN,28,28^FD"+labelForm.getOrderNotes().substring(82, labelForm.getOrderNotes().length())+"^FS\r\n");
		}else{
			printStrTemplate.append("^FO15,970^AZN,28,28^FD备注:"+labelForm.getOrderNotes().substring(0, 19)+"^FS\r\n");
			printStrTemplate.append("^FO15,1000^AZN,28,28^FD"+labelForm.getOrderNotes().substring(19, 40)+"^FS\r\n");
			printStrTemplate.append("^FO15,1030^AZN,28,28^FD"+labelForm.getOrderNotes().substring(40, 61)+"^FS\r\n");
			printStrTemplate.append("^FO15,1060^AZN,28,28^FD"+labelForm.getOrderNotes().substring(61, 82)+"^FS\r\n");
			printStrTemplate.append("^FO15,1090^AZN,28,28^FD"+labelForm.getOrderNotes().substring(82, 100)+"^FS\r\n");
		}
		//二维码
		printStrTemplate.append("^FO620,905^BQ,2,5^FDLAhttp://a.app.qq.com/o/simple.jsp?pkgname=com.deppon.dpapp^FS\r\n");
		
		printStrTemplate.append("^FO45,1150^AZN,25,25^FD此运单仅供德邦签约客户使用，相关责任业务以双方合同为主。^FS\r\n");

		//^XZ
		printStrTemplate.append("^XZ\r\n");
		baos.write(printStrTemplate.toString().getBytes("GBK"));
		baos.write("\r\n".getBytes());
		StringBuffer end = new StringBuffer();
		baos.write(end.toString().getBytes());
		baos.close();
		outputlist.add(baos.toByteArray());
		return outputlist;
	}
	private EWaybillStubCopyForm getLabelPrintForm(LabelPrintContext context){
		EWaybillStubCopyForm printForm = new EWaybillStubCopyForm();
		// 运单号
		printForm.setWaybillNo( (String) context.get("waybillNo"));
		// 订单号
		printForm.setOrderNo((String) context.get("orderNo"));
		// 客户订单号
		printForm.setCustOrderLine((String) context.get("custOrderLine"));
		//订单创建时间
		printForm.setOrderTime((String) context.get("orderTime"));
		//订单备注
		printForm.setOrderNotes((String) context.get("orderNotes"));
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
		//计费重量
		printForm.setBillWeight((String) context.get("billWeight"));
		//货物名称
		printForm.setGoodsName((String) context.get("goodsName"));
		//运输性质名称
		printForm.setProductName((String) context.get("productName"));
		//开单付款方式
		printForm.setPaidMethod((String) context.get("paidMethod"));
		//总费用
		printForm.setTotalFee((String) context.get("totalFee"));
		//保价声明价值
		printForm.setInsuranceAmount((String) context.get("insuranceAmount"));
		//返单类别
		printForm.setReturnBillType((String) context.get("returnBillType"));
		//代收金额
		printForm.setCodAmount((String) context.get("codAmount"));
		//最终配载部门名称
		printForm.setLastLoadOrgName((String) context.get("lastLoadOrgName"));
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
