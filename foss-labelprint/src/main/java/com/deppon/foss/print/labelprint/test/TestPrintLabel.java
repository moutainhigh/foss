package com.deppon.foss.print.labelprint.test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import com.deppon.foss.print.labelprint.LabelPrintContext;
import com.deppon.foss.print.labelprint.LabelPrintManager;
import com.deppon.foss.print.labelprint.gui.LblPrtSetupWindow;
import com.deppon.foss.print.labelprint.service.LblPrtServiceConst;
import com.deppon.foss.print.labelprint.util.PropertiesUtil;

public class TestPrintLabel extends JFrame {
	
	private static final long serialVersionUID = 2333518218405723156L;
	
	JSplitPane mainpanel = null;
	JScrollPane leftpanel = null;
	JPanel rightpanel = null;
	JTextArea parameterTextArea = null;
	JScrollPane parameterpanel = null;
	JPanel btnpanel = null;
	
	public static void main(String[] args){
		try{
			PropertiesUtil.initProperties();
			TestPrintLabel t = new TestPrintLabel();
			t.showWindow();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	class MyWindowListener implements WindowListener {
		@Override
		public void windowOpened(WindowEvent e) { }
		
		@Override
		public void windowIconified(WindowEvent e) { }
		
		@Override
		public void windowDeiconified(WindowEvent e) { }
		
		@Override
		public void windowDeactivated(WindowEvent e) { }
		
		@Override
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
		
		@Override
		public void windowClosed(WindowEvent e) { }
		
		@Override
		public void windowActivated(WindowEvent e) { }
	}
	
	class MenuNode {
		
		public MenuNode(String code,String name){
			this.code = code;
			this.name = name;
		}
		
		private String code = null;
		private String name = null;
				
		public String getCode(){
			return code;
		}
		
		public String getName(){
			return name;
		}
		
		public String toString(){
			return getName();
		}
	}
	
	private DefaultMutableTreeNode makeMenuNodes(){
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("标签打印测试");
		//新增电子运单模板测试！！！
		root.add(new DefaultMutableTreeNode(new MenuNode(LblPrtServiceConst.key_lblprt_program_EWaybillNewPrintWorker,"电子运单打印模板变更")));

		
		root.add(new DefaultMutableTreeNode(new MenuNode(LblPrtServiceConst.key_lblprt_program_ExpLabelPrintWorker,"测试新增快递标签")));

		
		root.add(new DefaultMutableTreeNode(new MenuNode(LblPrtServiceConst.key_lblprt_program_CommLabelPrintWorker,"运单标签")));
		
		root.add(new DefaultMutableTreeNode(new MenuNode(LblPrtServiceConst.key_lblprt_program_WholeCarLabelPrintWorker,"整车标签")));

		root.add(new DefaultMutableTreeNode(new MenuNode(LblPrtServiceConst.key_lblprt_program_AirWayBillLabelPrintWorker,"航空正单标签")));
		
		root.add(new DefaultMutableTreeNode(new MenuNode(LblPrtServiceConst.key_lblprt_program_NoLabelGoodsLabelPrintWorker,"无标签货物标签")));
		
		root.add(new DefaultMutableTreeNode(new MenuNode(LblPrtServiceConst.key_lblprt_program_VehicleLabelPrintWorker,"车辆标签")));
		
		root.add(new DefaultMutableTreeNode(new MenuNode(LblPrtServiceConst.key_lblprt_program_ExpCommLabelPrintWorker,"快递标签")));
		root.add(new DefaultMutableTreeNode(new MenuNode(LblPrtServiceConst.key_lblprt_program_ExpCommLabelPrintWorkerWeb,"快递标签(web)")));
		root.add(new DefaultMutableTreeNode(new MenuNode(LblPrtServiceConst.key_lblprt_program_SupplementLabelPrintWorker,"补码标签")));
		root.add(new DefaultMutableTreeNode(new MenuNode(LblPrtServiceConst.key_lblprt_program_EWaybillDeliverStubCopyPrintWorker, "发货人电子运单打印")));
		root.add(new DefaultMutableTreeNode(new MenuNode(LblPrtServiceConst.key_lblprt_program_EWaybillOwnStubCopyPrintWorker, "公司自留电子运单打印")));
		root.add(new DefaultMutableTreeNode(new MenuNode(LblPrtServiceConst.key_lblprt_program_EWaybillReceiveStubCopyPrintWorker, "收货人电子运单打印")));
		root.add(new DefaultMutableTreeNode(new MenuNode(LblPrtServiceConst.key_lblprt_program_PackageLabelPrintWorker,"包标签")));
		root.add(new DefaultMutableTreeNode(new MenuNode(LblPrtServiceConst.key_lblprt_program_PackageLabelPrintWorker,"包标签")));
		root.add(new DefaultMutableTreeNode(new MenuNode(LblPrtServiceConst.key_lblprt_program_EWaybillInsidePrintWorker,"电子运单2联标签")));
		root.add(new DefaultMutableTreeNode(new MenuNode(LblPrtServiceConst.key_lblprt_program_EWaybillThreePagePrintWorker,"电子运单3联标签")));
		root.add(new DefaultMutableTreeNode(new MenuNode(LblPrtServiceConst.key_lblprt_program_WareHouseOrderPrintWorker,"仓库分拣单")));
		root.add(new DefaultMutableTreeNode(new MenuNode(LblPrtServiceConst.key_lblprt_program_forwardReturnPrintWorker,"转寄退回")));
		root.add(new DefaultMutableTreeNode(new MenuNode(LblPrtServiceConst.key_lblprt_program_PinxiangyingLabelPrintWorker,"拼箱营")));
		root.add(new DefaultMutableTreeNode(new MenuNode(LblPrtServiceConst.key_lblprt_program_woodelReturnPrintWorker,"打木包装")));
		return root;
	}

	class MenuSelectionListener implements TreeSelectionListener{

		@Override
		public void valueChanged(TreeSelectionEvent e) {
			JTree _menutree = (JTree)e.getSource();
			DefaultMutableTreeNode selectednode = (DefaultMutableTreeNode)_menutree.getLastSelectedPathComponent();
			if(selectednode.getUserObject() instanceof MenuNode){
				MenuNode _node = (MenuNode)selectednode.getUserObject();
				selectOneWorkerFromTree(_node);
			}
		}
	}

	private MenuNode currSelectMenuNode = null;
	private void selectOneWorkerFromTree(MenuNode pMenuNode){
		
		currSelectMenuNode = pMenuNode;
		
		if(parameterTextArea!=null){
			parameterTextArea.setText(loadLabelWorkerPrintParameter(pMenuNode));
		}
	}
	private String loadLabelWorkerPrintParameter(MenuNode pMenuNode){
		if(LblPrtServiceConst.key_lblprt_program_CommLabelPrintWorker.equals(pMenuNode.getCode())){
			//运单标签打印
			StringBuffer out = new StringBuffer();
			out.append("addr1=D01\n");
			out.append("addr2=D02\n");
		    out.append("addr3=D03\n");
		    out.append("addr4=D04\n");
		    out.append("location1=10\n");
		    out.append("location2=101\n");
		    out.append("location3=111\n");
		    out.append("location4=102\n");
		    out.append("optusernum=101057\n");
		    out.append("number=789432156\n");
		    out.append("serialnos=0001\n");
		    out.append("leavecity=广州\n");
		    out.append("destination=上海\n");
		    out.append("isagent=true\n");
		    out.append("stationnumber=1023\n");
		    out.append("deptno=D102\n");
		    out.append("finaloutfieldid=1023\n");
		    out.append("finaloutname=上海青浦\n");
		    out.append("weight=10.2\n");
		    out.append("totalpieces=100\n");
		    out.append("packing=1木1纸\n");
		    out.append("unusual=异\n");
		    out.append("transtype=汽运偏线\n");
		    out.append("printdate=2012-12-17\n");
		    out.append("deliver=送\n");
		    out.append("goodstype=A\n");
		    out.append("preassembly=早班\n");
		    out.append("isExhibitCargo=Y\r\n");
		    out.append("deliveryBigCustomer=Y\r\n");
		    out.append("partnerBillingLogo=徐泾营业部徐泾\n");
			return out.toString();
		}
		else if(LblPrtServiceConst.key_lblprt_program_ExpLabelPrintWorker.equals(pMenuNode.getCode())){
			//新增快递标签测试
			StringBuffer out = new StringBuffer();
			out.append("optusernum=101057\n");    //当前用户工号
		    out.append("number=7894321560\n");   //单号
		    out.append("serialnos=0002\n");      //打印件的流水号
		    out.append("leavecity=上海市区哦好的\n");            //始发城市
		    out.append("deliver=送\n");           //是否送货
		    out.append("secLoadOrgName=上海转运场我就凯尼\n");            //第二外场   看其是否能只截取“兰州”
		    out.append("finaloutname=兰州市转运哇哈哈\n");         //最终外场名称看其是否能只截取“北京”
		    out.append("stationnumber=1023\n");      //目的站编码      
			out.append("totalpieces=800\n");            //件数
		    out.append("printdate=2015-01-29\n");   //打印日期
		    out.append("isNoStop=N\r\n");  //是否是快递部门为出发快递营业部
			return out.toString();
		}
		else if(LblPrtServiceConst.key_lblprt_program_WholeCarLabelPrintWorker.equals(pMenuNode.getCode())){
			//整车标签打印
			StringBuffer out = new StringBuffer();
			out.append("prtpiece=1\n");
			out.append("destination=上海青浦\n");
		    out.append("number=01234567\n");
		    out.append("departure=广州东平营业部\n");
		    out.append("arrival=上海派送部\n");
		    out.append("username=张三\n");
		    out.append("datetime=2012-10-29\n");
			return out.toString();
		}
		else if(LblPrtServiceConst.key_lblprt_program_AirWayBillLabelPrintWorker.equals(pMenuNode.getCode())){
			//航空正单标签打印
			StringBuffer out = new StringBuffer();
			out.append("cnpycode=SD\n");
			out.append("cnpyname=山东航空\n");
		    out.append("awbno=30087643\n");
		    out.append("pieces=10\n");
		    out.append("goodsweight=254\n");
		    out.append("pcsweight=26\n");
		    out.append("from=上海\n");
		    out.append("to=呼和浩特\n");
		    out.append("size=1\n");
			return out.toString();
		}
		else if(LblPrtServiceConst.key_lblprt_program_NoLabelGoodsLabelPrintWorker.equals(pMenuNode.getCode())){
			//无标签货物的标签打印
			StringBuffer out = new StringBuffer();
			out.append("optusernum=010010\n");
			out.append("printdate=2012-11-29\n");
		    out.append("number=123456789\n");
		    out.append("serialnos=0001\n");
		    out.append("totalpieces=10\n");
		    out.append("packing=木\n");
		    out.append("currentOrgName=上海外场\n");
		    out.append("goodsAreaName=D01\n");
		    out.append("handoverNo=JJD0101010\n");
		    out.append("goodsName=冰箱\n");
		    out.append("weight=10.3\n");
		    out.append("volume=20.3\n");
			return out.toString();
		}
		else if(LblPrtServiceConst.key_lblprt_program_VehicleLabelPrintWorker.equals(pMenuNode.getCode())){
			//车辆标签打印
			StringBuffer out = new StringBuffer();
			out.append("optusernum=101057\n");
			out.append("printdate=2012-11-29\n");
		    out.append("serialnos=ha00001,se88888\n");
		    out.append("carnos=沪A00001,苏E88888\n");
		    out.append("teams=上海车队,苏州车队\n");
		    out.append("groups=上海车队嘉定东部接送货组,苏州车队东部接送货组\n");
			return out.toString();
		}
		else if(LblPrtServiceConst.key_lblprt_program_ExpCommLabelPrintWorker.equals(pMenuNode.getCode())){
				//运单标签打印
				StringBuffer out = new StringBuffer();
				out.append("addr1=D01\n");
				out.append("addr2=D02\n");
			    out.append("addr3=D03\n");
			    out.append("addr4=D04\n");
			    out.append("location1=10\n");
			    out.append("location2=101\n");
			    out.append("location3=111\n");
			    out.append("location4=102\n");
			    out.append("optusernum=101057\n");
			    out.append("number=789432156\n");
			    out.append("serialnos=0001\n");
			    out.append("leavecity=上海\n");
			    out.append("destination=北京\n");
			    out.append("isagent=true\n");
			    out.append("stationnumber=1023\n");
			    out.append("deptno=D102\n");
			    out.append("finaloutfieldid=1023\n");
			    out.append("finaloutname=北京转运场\n");
			    out.append("weight=10.2\n");
			    out.append("totalpieces=100\n");
			    out.append("packing=1木1纸\n");
			    out.append("unusual=异\n");
			    out.append("transtype=经济快递\n");
			    out.append("printdate=2012-12-17\n");
			    out.append("deliver=送\n");
			    out.append("goodstype=A\n");
			    out.append("preassembly=早班\n");
			    out.append("secLoadOrgName=南京外场\n");
			    
				return out.toString();
			}
		else if(LblPrtServiceConst.key_lblprt_program_ExpCommLabelPrintWorkerWeb.equals(pMenuNode.getCode())){
			//运单标签打印
			StringBuffer out = new StringBuffer();
			out.append("addr1=D01\n");
			out.append("addr2=D02\n");
		    out.append("addr3=D03\n");
		    out.append("addr4=D04\n");
		    out.append("location1=10\n");
		    out.append("location2=101\n");
		    out.append("location3=111\n");
		    out.append("location4=102\n");
		    out.append("optusernum=101057\n");
		    out.append("number=789432156\n");
		    out.append("serialnos=0001\n");
		    out.append("leavecity=上海\n");
		    out.append("destination=北京\n");
		    out.append("isagent=true\n");
		    out.append("stationnumber=1023\n");
		    out.append("deptno=D102\n");
		    out.append("finaloutfieldid=1023\n");
		    out.append("finaloutname=北京转运场\n");
		    out.append("weight=10.2\n");
		    out.append("totalpieces=100\n");
		    out.append("packing=1木1纸\n");
		    out.append("unusual=异\n");
		    out.append("transtype=经济快递\n");
		    out.append("printdate=2012-12-17\n");
		    out.append("deliver=送\n");
		    out.append("goodstype=A\n");
		    out.append("preassembly=早班\n");
		    out.append("secLoadOrgName=南京外场\n");
		    
			return out.toString();
		}
		else if(LblPrtServiceConst.key_lblprt_program_SupplementLabelPrintWorker.equals(pMenuNode.getCode())){
			//运单标签打印
			StringBuffer out = new StringBuffer();
		    out.append("optusernum=101057\n");
		    out.append("number=789432156\n");
		    out.append("destination=北京\n");
		    out.append("printdate=2012-12-17\n");
			return out.toString();
		}
		/**
		 * 
		 *测试！！！！！！         该测试类为电子运单二期新增模板
		 * 
		 * */
		else if(LblPrtServiceConst.key_lblprt_program_EWaybillNewPrintWorker.equals(pMenuNode.getCode())){
			StringBuffer sb = new StringBuffer();
			sb.append("waybillNo=8232322363\n");
			sb.append("destination=我靠你们事儿真多\n");
			sb.append("printSerialNos=0001\n");
			sb.append("orderNo=Y00211525\n");
			sb.append("orderChannel=淘宝\n");
			sb.append("orderPaidMethod=网上支付\n");
			sb.append("deliveryCustomerId=232323\n");
			sb.append("deliveryCustomerCode=2323\n");
			sb.append("deliveryCustomerName=北京德邦货运代理有限公司\n");
			sb.append("deliveryCustomerMobilephone=18217471056\n");
			sb.append("deliveryCustomerPhone=021-31350606\n");
			sb.append("deliveryCustomerContact=上海精准德邦物流有限公司\n");
			sb.append("deliveryCustomerAddress=上海市青浦区徐泾镇明珠路1018号德邦物流C区3楼FOSS接送货开发组\n");
			sb.append("receiveCustomerId=1111\n");
			sb.append("receiveCustomerCode=111\n");
			sb.append("receiveCustomerName=北京精准德邦物流有限公司\n");
			sb.append("receiveCustomerMobilephone=18717456705\n");
			sb.append("receiveCustomerPhone=020-31350606\n");
			sb.append("receiveCustomerContact=何永东\n");
			sb.append("receiveCustomerAddress=上海市青浦区徐泾镇明珠路1018号德邦物流C区3楼FOSS中转开发组\r\n");
			sb.append("receiveOrgCode=111\n");
			sb.append("receiveOrgName=上海派送中心\n");
			sb.append("createOrgCode=23434\n");
			sb.append("createOrgName=大幅度撒\n");
			sb.append("customerPickupOrgCode=3\n");
			sb.append("customerPickupOrgName=萨顶顶\n");
			sb.append("goodsName=一箱红酒一架飞机一辆坦克一箱红酒一架飞机一辆坦克一箱红酒一架飞机一辆坦克一箱红酒一架飞机一辆坦克一箱红酒一架飞机一辆坦克\n");
			sb.append("goodsQtyTotal=2\n");
			sb.append("goodsVolumeTotal=3.2\n");
			sb.append("goodsWeightTotal=3.5\n");
			sb.append("billWeight=3.3\n");
			sb.append("productCode=RCP\n");
			sb.append("productName=360特惠件\n");
			sb.append("receiveMethod=111\n");
			sb.append("paidMethod=到付\n");
			sb.append("totalFee=1\n");
			sb.append("transportFee=1\n");
			sb.append("goodsSize=11\n");
			sb.append("goodsTypeCode=A\n");
			sb.append("goodsTypeName=到达\n");
			sb.append("preciousGoods=低调点\n");
			sb.append("specialShapedGoods=低调点\n");
			sb.append("goodsPackage=低调点\n");
			sb.append("insuranceAmount=22\n");
			sb.append("packageFee=222\n");
			sb.append("returnBillType=无需返单\n");
			sb.append("deliverMan=低调点\n");
			sb.append("receiveDate=低调点\n");         //codAmount
			sb.append("receiverMan=低调点\n");
			sb.append("deliverDate=2014-9-17 09:42:29\n");
			sb.append("secondOutfieldCode=W33333\n");
			sb.append("secondOutfieldName=上海枢纽中心\n");
			sb.append("lastLoadOrgCode=W111\n");
			sb.append("weixinAddr=https://dpapp.deppon.com/app/qr/index.html\n");
			sb.append("lastLoadOrgName=廊坊枢纽中心\n");
			sb.append("stationnumber=0002\n");
			sb.append("leavecity=上海市\r\n");
			sb.append("receiveBigCustomer=Y\r\n");
			sb.append("accountNo=622*4524\r\n");
			sb.append("codAmount=1000.000\r\n");
			return sb.toString();
		}
//此为中转场打印快递标签
		else if(LblPrtServiceConst.key_lblprt_program_ExpLabelPrintWorker.equals(pMenuNode.getCode())){
			//新增快递标签测试
			StringBuffer out = new StringBuffer();
			out.append("optusernum=101057\n");    //当前用户工号
		    out.append("number=7894321560\n");   //单号
		    out.append("serialnos=0002\n");      //打印件的流水号
		    out.append("leavecity=上海市区哦好的\n");            //始发城市
		    out.append("deliver=送\n");           //是否送货
		    out.append("secLoadOrgName=上海转运场我就凯尼\n");            //第二外场   看其是否能只截取“兰州”
		    out.append("finaloutname=兰州市转运哇哈哈\n");         //最终外场名称看其是否能只截取“北京”
		    out.append("stationnumber=1023\n");      //目的站编码      
			out.append("totalpieces=800\n");            //件数
		    out.append("printdate=2015-01-29\n");   //打印日期
		  out.append("isNoStop=Y\r\n");  //是否是快递部门为出发快递营业部
			return out.toString();
		}
		else if(LblPrtServiceConst.key_lblprt_program_EWaybillOwnStubCopyPrintWorker.equals(pMenuNode.getCode())){
			StringBuffer sb = new StringBuffer();
			sb.append("waybillNo=5203516859\n");
			sb.append("printSerialNos=0001,0002\n");
			sb.append("orderNo=Y00211525\n");
			sb.append("orderChannel=淘宝\n");
			sb.append("orderPaidMethod=网上支付\n");
			sb.append("deliveryCustomerId=232323\n");
			sb.append("deliveryCustomerCode=2323\n");
			sb.append("deliveryCustomerName=北京德邦货运代理有限公司\n");
			sb.append("deliveryCustomerMobilephone=18217471056\n");
			sb.append("deliveryCustomerPhone=021-31350606\n");
			sb.append("deliveryCustomerContact=上海精准德邦物流有限公司\n");
			sb.append("deliveryCustomerAddress=上海市青浦区徐泾镇明珠路1018号德邦物流C区3楼FOSS接送货开发组\n");
			sb.append("receiveCustomerId=1111\n");
			sb.append("receiveCustomerCode=111\n");
			sb.append("receiveCustomerName=北京精准德邦物流有限公司\n");
			sb.append("receiveCustomerMobilephone=18717456705\n");
			sb.append("receiveCustomerPhone=020-31350606\n");
			sb.append("receiveCustomerContact=何永东\n");
			sb.append("receiveCustomerAddress=上海市青浦区徐泾镇明珠路1018号德邦物流C区3楼FOSS中转开发组\r\n");
			sb.append("receiveOrgCode=111\n");
			sb.append("receiveOrgName=上海派送中心\n");
			sb.append("createOrgCode=23434\n");
			sb.append("createOrgName=大幅度撒\n");
			sb.append("customerPickupOrgCode=3\n");
			sb.append("customerPickupOrgName=萨顶顶\n");
			sb.append("goodsName=红酒\n");
			sb.append("goodsQtyTotal=2\n");
			sb.append("goodsVolumeTotal=3.2\n");
			sb.append("goodsWeightTotal=3.5\n");
			sb.append("billWeight=3.3\n");
			sb.append("productCode=RCP\n");
			sb.append("productName=360特惠件\n");
			sb.append("receiveMethod=111\n");
			sb.append("paidMethod=到付\n");
			sb.append("totalFee=1\n");
			sb.append("transportFee=1\n");
			sb.append("goodsSize=11\n");
			sb.append("goodsTypeCode=A\n");
			sb.append("goodsTypeName=到达\n");
			sb.append("preciousGoods=低调点\n");
			sb.append("specialShapedGoods=低调点\n");
			sb.append("goodsPackage=低调点\n");
			sb.append("insuranceAmount=22\n");
			sb.append("packageFee=222\n");
			sb.append("returnBillType=无需返单\n");
			sb.append("deliverMan=低调点\n");
			sb.append("receiveDate=低调点\n");
			sb.append("receiverMan=低调点\n");
			sb.append("deliverDate=2014-9-17 09:42:29\n");
			sb.append("secondOutfieldCode=W33333\n");
			sb.append("secondOutfieldName=上海枢纽中心\n");
			sb.append("lastLoadOrgCode=W111\n");
			sb.append("isDeliver=Y\n");
			sb.append("lastLoadOrgName=廊坊枢纽中心\n");
			sb.append("stationnumber=0000\n");
			sb.append("isPrintStar=Y\n");
			sb.append("isPrintAt=Y\n");
			sb.append("leavecity=上海市\r\n");
			sb.append("receiveBigCustomer=Y\r\n");
			sb.append("accountNo=622*4524\r\n");
			sb.append("deliveryBigCustomer=Y\r\n");
			return sb.toString();
		}
		else if(LblPrtServiceConst.key_lblprt_program_EWaybillReceiveStubCopyPrintWorker.equals(pMenuNode.getCode())){
			StringBuffer sb = new StringBuffer();
			sb.append("waybillNo=5203516859\n");
			sb.append("printSerialNos=0001,0002\n");
			sb.append("orderNo=Y00211525\n");
			sb.append("orderChannel=淘宝\n");
			sb.append("orderPaidMethod=网上支付\n");
			sb.append("deliveryCustomerId=232323\n");
			sb.append("deliveryCustomerCode=2323\n");
			sb.append("deliveryCustomerName=北京德邦货运代理有限公司\n");
			sb.append("deliveryCustomerMobilephone=18217471056\n");
			sb.append("deliveryCustomerPhone=021-31350606\n");
			sb.append("deliveryCustomerContact=上海精准德邦物流有限公司\n");
			sb.append("deliveryCustomerAddress=上海市青浦区徐泾镇明珠路1018号德邦物流C区3楼FOSS接送货开发组\n");
			sb.append("receiveCustomerId=1111\n");
			sb.append("receiveCustomerCode=111\n");
			sb.append("receiveCustomerName=北京精准德邦物流有限公司\n");
			sb.append("receiveCustomerMobilephone=18717456705\n");
			sb.append("receiveCustomerPhone=020-31350606\n");
			sb.append("receiveCustomerContact=何永东\n");
			sb.append("receiveCustomerAddress=上海市青浦区徐泾镇明珠路1018号德邦物流C区3楼FOSS中转开发组\r\n");
			sb.append("receiveOrgCode=111\n");
			sb.append("receiveOrgName=上海派送中心\n");
			sb.append("createOrgCode=23434\n");
			sb.append("createOrgName=大幅度撒\n");
			sb.append("customerPickupOrgCode=3\n");
			sb.append("customerPickupOrgName=萨顶顶\n");
			sb.append("goodsName=我其实很想吃西瓜和甜瓜你造么\n");
			sb.append("goodsQtyTotal=2\n");
			sb.append("goodsVolumeTotal=3.2\n");
			sb.append("goodsWeightTotal=3.5\n");
			sb.append("billWeight=3.3\n");
			sb.append("productCode=RCP\n");
			sb.append("productName=360特惠件\n");
			sb.append("receiveMethod=111\n");
			sb.append("paidMethod=到付\n");
			sb.append("totalFee=1\n");
			sb.append("transportFee=1\n");
			sb.append("goodsSize=11\n");
			sb.append("goodsTypeCode=A\n");
			sb.append("goodsTypeName=到达\n");
			sb.append("preciousGoods=低调点\n");
			sb.append("specialShapedGoods=低调点\n");
			sb.append("goodsPackage=低调点\n");
			sb.append("insuranceAmount=22\n");
			sb.append("packageFee=222\n");
			sb.append("returnBillType=无需返单\n");
			sb.append("deliverMan=低调点\n");
			sb.append("receiveDate=低调点\n");
			sb.append("receiverMan=低调点\n");
			sb.append("deliverDate=2014-9-17 09:42:29\n");
			sb.append("secondOutfieldCode=W33333\n");
			sb.append("secondOutfieldName=上海枢纽中心\n");
			sb.append("lastLoadOrgCode=W111\n");
			sb.append("isDeliver=Y\n");
			sb.append("lastLoadOrgName=廊坊枢纽中心\n");
			sb.append("stationnumber=0000\n");
			sb.append("isPrintStar=Y\n");
			sb.append("isPrintAt=Y\n");
			sb.append("leavecity=上海市\r\n");
			sb.append("receiveBigCustomer=Y\r\n");
			sb.append("accountNo=622*4524\r\n");
			sb.append("deliveryBigCustomer=Y\r\n");
			return sb.toString();
		}else if(LblPrtServiceConst.key_lblprt_program_EWaybillDeliverStubCopyPrintWorker.equals(pMenuNode.getCode())){
			StringBuffer sb = new StringBuffer();
			sb.append("waybillNo=5203516859\n");
			sb.append("printSerialNos=0001,0002\n");
			sb.append("orderNo=Y00211525\n");
			sb.append("orderChannel=淘宝\n");
			sb.append("orderPaidMethod=网上支付\n");
			sb.append("deliveryCustomerId=232323\n");
			sb.append("deliveryCustomerCode=2323\n");
			sb.append("deliveryCustomerName=北京德邦货运代理有限公司\n");
			sb.append("deliveryCustomerMobilephone=18217471056\n");
			sb.append("deliveryCustomerPhone=021-31350606\n");
			sb.append("deliveryCustomerContact=上海精准德邦物流有限公司\n");
			sb.append("deliveryCustomerAddress=上海市青浦区徐泾镇明珠路1018号德邦物流C区3楼FOSS接送货开发组\n");
			sb.append("receiveCustomerId=1111\n");
			sb.append("receiveCustomerCode=111\n");
			sb.append("receiveCustomerName=北京精准德邦物流有限公司\n");
			sb.append("receiveCustomerMobilephone=18717456705\n");
			sb.append("receiveCustomerPhone=020-31350606\n");
			sb.append("receiveCustomerContact=何永东\n");
			sb.append("receiveCustomerAddress=上海市青浦区徐泾镇明珠路1018号德邦物流C区3楼FOSS中转开发组\r\n");
			sb.append("receiveOrgCode=111\n");
			sb.append("receiveOrgName=上海派送中心\n");
			sb.append("createOrgCode=23434\n");
			sb.append("createOrgName=大幅度撒\n");
			sb.append("customerPickupOrgCode=3\n");
			sb.append("customerPickupOrgName=萨顶顶\n");
			sb.append("goodsName=我其实很想吃西瓜和甜瓜你造么\n");
			sb.append("goodsQtyTotal=2\n");
			sb.append("goodsVolumeTotal=3.2\n");
			sb.append("goodsWeightTotal=3.5\n");
			sb.append("billWeight=3.3\n");
			sb.append("productCode=RCP\n");
			sb.append("productName=360特惠件\n");
			sb.append("receiveMethod=111\n");
			sb.append("paidMethod=到付\n");
			sb.append("totalFee=1\n");
			sb.append("transportFee=1\n");
			sb.append("goodsSize=11\n");
			sb.append("goodsTypeCode=A\n");
			sb.append("goodsTypeName=到达\n");
			sb.append("preciousGoods=低调点\n");
			sb.append("specialShapedGoods=低调点\n");
			sb.append("goodsPackage=低调点\n");
			sb.append("insuranceAmount=22\n");
			sb.append("packageFee=222\n");
			sb.append("returnBillType=无需返单\n");
			sb.append("deliverMan=低调点\n");
			sb.append("receiveDate=低调点\n");
			sb.append("receiverMan=低调点\n");
			sb.append("deliverDate=2014-9-17 09:42:29\n");
			sb.append("secondOutfieldCode=W33333\n");
			sb.append("secondOutfieldName=上海枢纽中心\n");
			sb.append("lastLoadOrgCode=W111\n");
			sb.append("isDeliver=Y\n");
			sb.append("lastLoadOrgName=廊坊枢纽中心\n");
			sb.append("stationnumber=0000\n");
			sb.append("isPrintStar=Y\n");
			sb.append("isPrintAt=Y\n");
			sb.append("leavecity=上海市\r\n");
			sb.append("receiveBigCustomer=Y\r\n");
			sb.append("accountNo=622*4524\r\n");
			sb.append("deliveryBigCustomer=Y\r\n");
			return sb.toString();
		}else if(LblPrtServiceConst.key_lblprt_program_PackageLabelPrintWorker.equals(pMenuNode.getCode())){
			StringBuffer sb = new StringBuffer();
			sb.append("barCode=B12345678901\r\n");
			return sb.toString();
		}else if(LblPrtServiceConst.key_lblprt_program_EWaybillInsidePrintWorker.equals(pMenuNode.getCode())){
			//电子运单2联打印
			StringBuffer sb = new StringBuffer();
			sb.append("waybillNo=8232322363\n");
			sb.append("destination=我靠你们事儿真多\n");
			sb.append("printSerialNos=0002\n");
			sb.append("orderNo=Y00211525\n");
			sb.append("orderChannel=淘宝\n");
			sb.append("orderPaidMethod=网上支付\n");
			sb.append("deliveryCustomerId=232323\n");
			sb.append("deliveryCustomerCode=2323\n");
			sb.append("deliveryCustomerName=北京德邦货运代理有限公司\n");
			sb.append("deliveryCustomerMobilephone=18217471056\n");
			sb.append("deliveryCustomerPhone=021-31350606\n");
			sb.append("deliveryCustomerContact=上海精准德邦物流有限公司\n");
			sb.append("deliveryCustomerAddress=上海市青浦区徐泾镇明珠路号德邦物流区楼接送货开发组恐惧哦爱和发放阿斯发生的\n");
			sb.append("receiveCustomerId=1111\n");
			sb.append("receiveCustomerCode=111\n");
			sb.append("receiveCustomerName=北京精准德邦物流有限公司\n");
			sb.append("receiveCustomerMobilephone=18717456705\n");
			sb.append("receiveCustomerPhone=020-31350606\n");
			sb.append("receiveCustomerContact=何永东\n");
			sb.append("receiveCustomerAddress=上海市青浦区徐泾镇明珠路1018号德邦物流C区3楼FOSS中转开发组\r\n");
			sb.append("receiveOrgCode=111\n");
			sb.append("receiveOrgName=上海派送中心\n");
			sb.append("createOrgCode=23434\n");
			sb.append("createOrgName=大幅度撒\n");
			sb.append("customerPickupOrgCode=3\n");
			sb.append("customerPickupOrgName=萨顶顶\n");
			sb.append("goodsName=一箱红酒一架飞机一辆坦克一箱红酒一架飞机一辆坦克一箱红酒一架飞机一辆坦克一箱红酒一架飞机一辆坦克一箱红酒一架飞机一辆坦克\n");
			sb.append("goodsQtyTotal=2\n");
			sb.append("goodsVolumeTotal=3.2\n");
			sb.append("goodsWeightTotal=3.5\n");
			sb.append("billWeight=3.3\n");
			sb.append("productCode=RCP\n");
			sb.append("productName=360特惠件\n");
			sb.append("receiveMethod=111\n");
			sb.append("paidMethod=到付\n");
			sb.append("totalFee=1\n");
			sb.append("transportFee=1\n");
			sb.append("goodsSize=11\n");
			sb.append("goodsTypeCode=A\n");
			sb.append("goodsTypeName=到达\n");
			sb.append("preciousGoods=低调点\n");
			sb.append("specialShapedGoods=低调点\n");
			sb.append("goodsPackage=低调点\n");
			sb.append("insuranceAmount=22\n");
			sb.append("packageFee=222\n");
			sb.append("returnBillType=无需返单\n");
			sb.append("deliverMan=低调点\n");
			sb.append("receiveDate=低调点\n");         //codAmount
			sb.append("receiverMan=低调点\n");
			sb.append("deliverDate=2014-9-17 09:42:29\n");
			sb.append("secondOutfieldCode=W33333\n");
			sb.append("secondOutfieldName=上海枢纽中心\n");
			sb.append("lastLoadOrgCode=W111\n");
			sb.append("weixinAddr=https://dpapp.deppon.com/app/qr/index.html\n");
			sb.append("lastLoadOrgName=廊坊枢纽中心\n");
			sb.append("stationnumber=0002\n");
			sb.append("leavecity=上海市\r\n");
			sb.append("receiveBigCustomer=Y\r\n");
			sb.append("accountNo=622*4524\r\n");
			sb.append("orderNotes=我看你英俊潇洒、玉树临风、风流倜傥，人见人爱，花见花开，想必你一定是人渣中的极品，禽兽中的禽兽。我看你一定是从小缺钙、长大缺爱、姥姥不疼、舅舅不爱、左脸欠抽、右脸欠踹、驴见驴踢、猪见猪踩。你长得真有创意，活得真有勇气，丑不是你的本意，而是上帝在发脾气。把你丢到毛屎坑里，毛屎坑都能吐了；把你丢到时空裂缝里，时空裂缝也能自我爆炸了。你说叫本少爷教你练功夫，我教你练刀，你偏要练剑，你还上剑不练练下剑，金剑不练练银剑，最后练成了最淫剑。赐你剑神你不做，给你剑仙你不当，你偏偏哭着喊着死皮赖脸要做剑人。你真是活着浪费空气，死了浪费土地，半死不活浪费金币。还不如撒泡尿把自己淹死算了\n");
			sb.append("outerField1=上海枢纽中心\r\n");
			return sb.toString();
		}else if(LblPrtServiceConst.key_lblprt_program_EWaybillThreePagePrintWorker.equals(pMenuNode.getCode())){
			//电子运单3联打印
			StringBuffer sb = new StringBuffer();
			sb.append("waybillNo=8232322363\n");
			sb.append("destination=我靠你们事儿真多\n");
			sb.append("printSerialNos=0001\n");
			sb.append("orderNo=Y00211525\n");
			sb.append("orderChannel=淘宝\n");
			sb.append("orderPaidMethod=网上支付\n");
			sb.append("deliveryCustomerId=232323\n");
			sb.append("deliveryCustomerCode=2323\n");
			sb.append("deliveryCustomerName=北京德邦货运代理有限公司\n");
			sb.append("deliveryCustomerMobilephone=18217471056\n");
			sb.append("deliveryCustomerPhone=021-31350606\n");
			sb.append("deliveryCustomerContact=上海精准德邦物流有限公司\n");
			sb.append("deliveryCustomerAddress=上海市青浦区徐泾镇明珠路号德邦物流区楼接送货开发组开机速度和佛山汾\n");
			sb.append("receiveCustomerId=1111\n");
			sb.append("receiveCustomerCode=111\n");
			sb.append("receiveCustomerName=北京精准德邦物流有限公司\n");
			sb.append("receiveCustomerMobilephone=18717456705\n");
			sb.append("receiveCustomerPhone=020-31350606\n");
			sb.append("receiveCustomerContact=何永东\n");
			sb.append("receiveCustomerAddress=上海市青浦区徐泾镇明珠路号德邦物流区楼中转开发组啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊\r\n");
			sb.append("receiveOrgCode=111\n");
			sb.append("receiveOrgName=上海派送中心\n");
			sb.append("createOrgCode=23434\n");
			sb.append("createOrgName=大幅度撒\n");
			sb.append("customerPickupOrgCode=3\n");
			sb.append("customerPickupOrgName=萨顶顶\n");
			sb.append("goodsName=一箱红酒一架飞机一辆坦克一箱红酒一架飞机一辆坦克一箱红酒一架飞机一辆坦克一箱红酒一架飞机一辆坦克一箱红酒一架飞机一辆坦克\n");
			sb.append("goodsQtyTotal=2\n");
			sb.append("goodsVolumeTotal=3.2\n");
			sb.append("goodsWeightTotal=3.5\n");
			sb.append("billWeight=3.3\n");
			sb.append("productCode=RCP\n");
			sb.append("productName=360特惠件\n");
			sb.append("receiveMethod=111\n");
			sb.append("paidMethod=到付\n");
			sb.append("totalFee=1\n");
			sb.append("transportFee=1\n");
			sb.append("goodsSize=11\n");
			sb.append("goodsTypeCode=A\n");
			sb.append("goodsTypeName=到达\n");
			sb.append("preciousGoods=低调点\n");
			sb.append("specialShapedGoods=低调点\n");
			sb.append("goodsPackage=低调点\n");
			sb.append("insuranceAmount=2200\n");
			sb.append("insuranceFee=22\n");
			sb.append("packageFee=222\n");
			sb.append("returnBillType=无需返单\n");
			sb.append("deliverMan=低调点\n");
			sb.append("receiveDate=低调点\n");         //codAmount
			sb.append("receiverMan=低调点\n");
			sb.append("deliverDate=2014-9-17 09:42:29\n");
			sb.append("secondOutfieldCode=W33333\n");
			sb.append("secondOutfieldName=上海枢纽中心\n");
			sb.append("lastLoadOrgCode=W111\n");
			sb.append("weixinAddr=https://dpapp.deppon.com/app/qr/index.html\n");
			sb.append("lastLoadOrgName=廊坊枢纽中心\n");
			sb.append("stationnumber=0002\n");
			sb.append("leavecity=上海市\r\n");
			sb.append("receiveBigCustomer=Y\r\n");
			sb.append("accountNo=622*4524\r\n");
			sb.append("orderNotes=我看你英俊潇洒、玉树临风、风流倜傥，人见人爱，花见花开，想必你一定是人渣中的极品，禽兽中的禽兽。我看你一定是从小缺钙、长大缺爱、姥姥不疼、舅舅不爱、左脸欠抽、右脸欠踹、驴见驴踢、猪见猪踩。你长得真有创意，活得真有勇气，丑不是你的本意，而是上帝在发脾气。把你丢到毛屎坑里，毛屎坑都能吐了；把你丢到时空裂缝里，时空裂缝也能自我爆炸了。你说叫本少爷教你练功夫，我教你练刀，你偏要练剑，你还上剑不练练下剑，金剑不练练银剑，最后练成了最淫剑。赐你剑神你不做，给你剑仙你不当，你偏偏哭着喊着死皮赖脸要做剑人。你真是活着浪费空气，死了浪费土地，半死不活浪费金币。还不如撒泡尿把自己淹死算了\n");
			sb.append("outerField1=上海枢纽中心\r\n");
			sb.append("createOrgName=上海青浦区徐泾营业部\r\n");
			sb.append("destination=嘉兴平湖市长胜路营业部\r\n");
			return sb.toString();
		}else if(LblPrtServiceConst.key_lblprt_program_WareHouseOrderPrintWorker.equals(pMenuNode.getCode())){
			//仓库分拣单
			StringBuffer sb = new StringBuffer();
			sb.append("waybillNo=8232322363\n");
			sb.append("printSerialNos=0001\n");
			sb.append("orderNo=Y00211525\n");
			sb.append("deliveryCustomerContact=上海精准德邦物流有限公司\n");
			sb.append("receiveCustomerContact=何永东\n");
			sb.append("goodsQtyTotal=2\n");
			sb.append("billWeight=3.3\n");
			sb.append("goodsName=一箱红酒一架飞机一辆坦克一箱红酒一架飞机一辆坦克一箱红酒一架飞机一辆坦克一箱红酒一架飞机一辆坦克一箱红酒一架飞机一辆坦克\n");
			sb.append("receiveCustomerAddress=上海市青浦区徐泾镇明珠路1018号德邦物流C区3楼FOSS中转开发组\r\n");
			sb.append("orderTime=2015-01-29\n");
			sb.append("orderNotes=我看你英俊潇洒、玉树临风、风流倜傥，人见人爱，花见花开，想必你一定是人渣中的极品，禽兽中的禽兽。我看你一定是从小缺钙、长大缺爱、姥姥不疼、舅舅不爱、左脸欠抽、右脸欠踹、驴见驴踢、猪见猪踩。你长得真有创意，活得真有勇气，丑不是你的本意，而是上帝在发脾气。把你丢到毛屎坑里，毛屎坑都能吐了；把你丢到时空裂缝里，时空裂缝也能自我爆炸了。你说叫本少爷教你练功夫，我教你练刀，你偏要练剑，你还上剑不练练下剑，金剑不练练银剑，最后练成了最淫剑。赐你剑神你不做，给你剑仙你不当，你偏偏哭着喊着死皮赖脸要做剑人。你真是活着浪费空气，死了浪费土地，半死不活浪费金币。还不如撒泡尿把自己淹死算了\n");
			sb.append("printSerialNos=0001\n");
			return sb.toString();
		}else if(LblPrtServiceConst.key_lblprt_program_forwardReturnPrintWorker.equals(pMenuNode.getCode())){
			//运单标签打印
			StringBuffer out = new StringBuffer();
		    out.append("number=7894321568\n");
		    out.append("secLoadOrgName=上海\n");
		    out.append("lastTransCenterCity=北京市是水电费\n");
		    out.append("serialnos=0001\n");
		    out.append("totalpieces=99\n");
		    out.append("stationnumber=1234\n");
		    out.append("insuranceFee=1000\n");
		    out.append("codAmount=2000\n");
		    out.append("returnBillType=有\n");
		    out.append("packageFee=3000\n");
		    out.append("transportFee=4000\n");
		    out.append("totalFee=5000\n");

		   // out.append("receive=廖青松,18717777084,上海市青浦区徐泾镇西藏自治区拉萨市堆龙德庆县乃琼镇广东省东莞市东莞市沙田镇斜西村\n");
		    out.append("receiveCustomerContact=廖青松\n");
		    out.append("receiveCustomerphone=18717777084\n");
		    out.append("receiveCustomerProvName=西藏自治区\n");
		    out.append("receiveCustomerCityName=拉萨市\n");
		    out.append("receiveCustomerDistName=堆龙德庆县乃琼镇\n");
		    out.append("receiveCustomerAddress=方式\n");
		    return out.toString();
		}else if(LblPrtServiceConst.key_lblprt_program_PinxiangyingLabelPrintWorker.equals(pMenuNode.getCode())){
			//拼箱营
			StringBuffer sb = new StringBuffer();
			sb.append("channelNumber=5435435495\n");
			return sb.toString();
		}else if(LblPrtServiceConst.key_lblprt_program_woodelReturnPrintWorker.equals(pMenuNode.getCode())){
			//打木标签测试信息
			StringBuffer out = new StringBuffer();
			out.append("waybillNo=88888888\n");//运单号
			out.append("transtype=精准汽运\n");//运输性质
			out.append("weight=4\n");//总重量
			out.append("serialnos=0001\n");//流水号
			out.append("printdate=2016-04-28\n");//打印日期
			out.append("optusernum=44944\n");//用户工号
			out.append("packing=打木架+托\n");//包装类型
			out.append("standGoodsSize=0.05*0.05*0.05\n");//打木架包装尺寸
			out.append("standGoodsNum=4\n");//打木架货物件数
			out.append("standGoodsVolume=3.36\n");//打木架货物体积
			out.append("boxGoodsNum=1\n");//打木箱货物件数
			out.append("boxGoodsSize=60*10*40\n");//打木箱货物尺寸
			out.append("boxGoodsVolume=3.36\n");//打木箱货物体积
			out.append("salverGoodsNum=1\n");//打木托件数
			out.append("standRequirement=打不打打不打打不打打不打打不打打打不打打不打打不打打不打打不打打打不打打\n");//打木架要求
			out.append("boxRequirement=就不打就不打就不打就不打就不打就不打就不打就不打就不打就不打就不打就不打就不打就不打\n");//打木箱要求
			out.append("salverRequirement=随便打随便打\n");//打木托要求
			out.append("IsPrintLogo=Y");
			return out.toString();
		}
		return null;
	}
	
	public TestPrintLabel() {
		
		DefaultMutableTreeNode root = makeMenuNodes();
		JTree menutree = new JTree(root);
		leftpanel = new JScrollPane(menutree);
		leftpanel.setPreferredSize(new Dimension(200,500));
		menutree.addTreeSelectionListener(new MenuSelectionListener());
		
		btnpanel = new JPanel();
		btnpanel.setLayout(new FlowLayout());
		JButton btnsetup = new JButton("setup");
		btnsetup.addActionListener(new SetupActionListener());
		JButton btnprt = new JButton("Test Print");
		btnprt.addActionListener(new PrintActionListener());
		btnpanel.add(btnsetup);
		btnpanel.add(btnprt);
		
		parameterTextArea = new JTextArea();
		parameterpanel = new JScrollPane(parameterTextArea);
		parameterpanel.setPreferredSize(new Dimension(500,800));
		
		rightpanel = new JPanel();
		rightpanel.setLayout(new BorderLayout());
		rightpanel.add(btnpanel,BorderLayout.SOUTH);
		rightpanel.add(parameterpanel,BorderLayout.CENTER);
		
		mainpanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT); 
		mainpanel.setLeftComponent(leftpanel);
		mainpanel.setRightComponent(rightpanel);
		this.getContentPane().add(mainpanel);
		this.addWindowListener(new MyWindowListener());
	}
	
	public void showWindow(){
		this.setSize(new Dimension(800, 600));
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);
		show();
	}
	
	class SetupActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			LblPrtSetupWindow setupwindow = LblPrtSetupWindow.getInstance();
			setupwindow.openWindow();
		}
	}
	
	class PrintActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try{
				
				if(currSelectMenuNode!=null){
					LabelPrintContext ctx = new LabelPrintContext();
					
					// parse label print parameter
					Scanner scanner = new Scanner(parameterTextArea.getText());
					while(scanner.hasNextLine()){
						String line = scanner.nextLine();
						if(line!=null && line.indexOf("=")!=-1){
							String key = line.split("=")[0];
							String value = line.split("=")[1];
							ctx.put(key, value);
						}
					}
					LabelPrintManager.doLabelPrintAction(currSelectMenuNode.getCode(), ctx);
				}
			}catch (Exception exp) {
				exp.printStackTrace();
			}
		}
	}
	
}
