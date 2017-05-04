package com.deppon.foss.prt.waybilltest;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.print.util.ClassPathResourceUtil;

public class WayBillPrtDataSource implements JasperDataSource {

	public Map<String,Object> createParameterDataSource(JasperContext jasperContext) {
		Map<String,Object> parameter = new HashMap<String,Object>();
		
		parameter.put("waybillNo","8674321");
	 
		ClassPathResourceUtil util = new ClassPathResourceUtil();
		InputStream imgin = util.getInputStream("com/deppon/foss/prt/waybilltest/images/waybill.jpg");
		parameter.put("waybillbkg",imgin);
		
		return parameter;
	}

	public List<Map<String,Object>> createDetailDataSource(JasperContext jasperContext) {

		List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("waybillNo", "12346685");
		m.put("consignor", "222");
		m.put("field1", "13564216008");
		m.put("field2", "13564216007");

		m.put("consignorContactWay", "~~~~~~~~~~~~~~");
		m.put("consignorAddr", "上海张江高科技园区郭守敬路515号");
		m.put("consignorNo", "33650");
		m.put("onlineOrderNo", "WL33605");
		m.put("commodityName", "重货");
		m.put("packaging", "12346685");
		m.put("stroeTransport", 1);

		m.put("product", "精准");
		m.put("startStop", "上海");
		m.put("endStop", "北京");
		m.put("count", 100);
		m.put("volume", 12);
		m.put("weight", 31);
		m.put("isDoor", "否");
		m.put("chargeWeight", 12);

		m.put("rate", "精准");
		m.put("freight", 13);
		m.put("insuranceCharge", 250);
		m.put("noPayAmount", 122);
		m.put("toPayAmount", 221);
		m.put("makeBillInfo", 1223);
		m.put("signature", 2121);
		m.put("consigorNetInfo", 3123);

		m.put("deliveryInfo", 1212);
		m.put("deliveryType", "上门交货");
		m.put("payType", "电汇");
		m.put("signBill", "原件");

		m.put("payAccount", 3332);
		m.put("openAccountBank", 12203);
		m.put("accountName", "上海浦发银行");

		m.put("proxyAmount", 323);
		m.put("insurance", "622600223556666255");
		m.put("consigneeContactWay", "上海333发银行");
		m.put("addr", "上海张江高科技园区郭守敬路515号02555555555");
		m.put("consignee", "上11行");
		lst.add(m);
		return lst;
	}

}
