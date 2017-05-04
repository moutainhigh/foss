package com.deppon.foss.print.labelprint.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.mortbay.jetty.handler.AbstractHandler;

import com.deppon.foss.print.labelprint.LabelPrintContext;
import com.deppon.foss.print.labelprint.LabelPrintManager;
import com.deppon.foss.print.labelprint.util.IdCardReader;
import com.deppon.foss.print.labelprint.util.Log;
import com.idCard.info.IdCardInfo;

public class DefaultRequestHandler extends AbstractHandler {

	private static ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, int arg3) throws IOException,
			ServletException {

		Log.info(" request Label Print serivce to do :" + target);
		StringBuffer result = new StringBuffer();
		try {
			if ("/print".equals(target)) {

				LabelPrintContext ctx = new LabelPrintContext();
				Enumeration<String> enumeration = request.getParameterNames();
				for (; enumeration.hasMoreElements();) {
					String pname = enumeration.nextElement();
					String str = request.getParameter(pname);
					if (str != null) {
						// System.out.println(pname+"="+new
						// String(str.getBytes(),"UTF-8"));
						// System.out.println(pname+"="+new
						// String(str.getBytes("GBK"),"GBK"));
						ctx.put(pname, str);
						Log.debug("Print param/value:" + pname + "=" + str);
					}
				}

				/**
				 * key_ CommLabelPrintWorker 
				 * key_ WholeCarLabelPrintWorker 
				 * key_	AirWayBillLabelPrintWorker 
				 * key_ NoLabelGoodsLabelPrintWorker
				 */
				String lblprtworker = (String)ctx.get("lblprtworker");
				LabelPrintManager.doLabelPrintAction(lblprtworker, ctx);
				result.append("{data:{code:1,msg:'打印成功'}}");
			}
		} catch (Exception e) {
			Log.info("Call Label Print Server Error:" + e.getMessage());
			e.printStackTrace();
			String errmsg = "打印失败,"+e.getMessage();
			if(e instanceof javax.print.PrintException){
				errmsg = "打印提交失败,连接打印机异常,请检查打印机和电脑连接并测试打印.";
			}
			result.append("{data:{code:0,msg:'" + errmsg + "'}}");
		}
		// 读取身份证信息
		try {
			if ("/idcard".equals(target)) {
				IdCardReader reader = new IdCardReader();
				IdCardInfo idCardInfo = reader.getIdCardInfo();
				String json = objectMapper.writeValueAsString(idCardInfo);
				result.append(json);
			}
		} catch (Exception e) {
			Log.info("Read idCard error:" + e);
			e.printStackTrace();
			result.append("{}");
		}
		// response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		//((Request) request).setHandled(true);

		boolean jsonP = false;
		String cb = request.getParameter("callback");
		if (cb != null) {
			jsonP = true;
			response.setContentType("text/javascript;charset=utf-8");
		} else {
			response.setContentType("application/x-json;charset=utf-8");
		}

		PrintWriter out = response.getWriter();
		if (jsonP) {
			out.print(cb + "(");
		}
		out.print(result);
		if (jsonP) {
			out.print(");");
		}
		out.close();
	}

}
