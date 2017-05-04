package com.deppon.foss.print.jasper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.type.OrientationEnum;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 调用jasperreport 打印结果输出的servlet
 * @author niujian
 */
public class ExportHtmlServlet extends HttpServlet {
	
	private static final Log log = LogFactory.getLog(ExportHtmlServlet.class);
	private static final long serialVersionUID = -3407098521007650586L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		exportHtml(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		exportHtml(req, resp);
	}

	public ApplicationContext getApplicationContext() {
		return WebApplicationContextUtils.getWebApplicationContext(this
				.getServletContext());
	}

	private static final double FORMAT_DOUBLE = 2.833;
	private static final int TEN_INT = 10;
	private void exportHtml(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		String prtkey = request
				.getParameter(FossPrintConst.KEY_JAPSER_PRINT_KEY);

		//JasperPrintCacher jpcacher = JasperPrintCacher.getInstance();
		String cahcerid = request.getSession(true).getId();
		
		int orientation = 1;
		int pagewidth = 0;
		int pageheight = 0;
		JasperContext jctx = new JasperContext();
		
		String ctxpath = request.getContextPath();
		if(ctxpath!=null){
			jctx.setCtxPath(ctxpath);
		}
		try {
			jctx.setApplicationContext(getApplicationContext());
			jctx.setRequest(request);
			jctx.setPrtkey(prtkey);
			JasperPrintManager jasperPrintManager = new JasperPrintManager(jctx);
			JasperPrint fulljapserprint = jasperPrintManager.processPrintResult(jctx);
			JasperPrint onimgjasperprint = jctx.getNoImgJasperPrint();
			
			if (fulljapserprint != null) {
				if (fulljapserprint.getOrientationValue().equals(
						OrientationEnum.PORTRAIT)) {
					orientation = 0;
				} else if (fulljapserprint.getOrientationValue().equals(
						OrientationEnum.LANDSCAPE)) {
					orientation = 1;
				}
				pagewidth = fulljapserprint.getPageWidth();
				pagewidth = (new Double(pagewidth / FORMAT_DOUBLE).intValue()) * TEN_INT;
				pageheight = fulljapserprint.getPageHeight();
				pageheight = (new Double(pageheight / FORMAT_DOUBLE).intValue()) * TEN_INT;
				
				out.println("<div id='fulljpdiv' style='padding:0px;border:2px solid #000000;width:1px;height:1px;overflow:auto;' >");
				StringWriter fullstringwriter = new StringWriter();
				FossJRXhtmlExporter fulljasperprintexporter = new FossJRXhtmlExporter();
				fulljasperprintexporter.setParameter(
						JRHtmlExporterParameter.IMAGES_DIR_NAME,
						request.getSession().getServletContext()
								.getRealPath("/image")
								+ "\\");
				
				fulljasperprintexporter.setParameter(
						JRHtmlExporterParameter.IMAGES_URI,
						ctxpath+"/servlets/printimage?"
								+ FossPrintConst.KEY_JASPER_PRINT_CACHE_ID
								+ "=" + cahcerid + "&"
								+ FossPrintConst.KEY_JAPSER_PRINT_KEY + "="
								+ prtkey+"$full" + "&image=");
				fulljasperprintexporter.setParameter(
						JRHtmlExporterParameter.HTML_HEADER, "<span style=\"padding:0px;margin:0px;\">");
				fulljasperprintexporter.setParameter(
						JRHtmlExporterParameter.HTML_FOOTER, "</span>");
				fulljasperprintexporter.setParameter(
						JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
						Boolean.TRUE);
				fulljasperprintexporter.setParameter(
						JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,
						Boolean.TRUE);
				fulljasperprintexporter.setParameter(
						JRHtmlExporterParameter.SIZE_UNIT, "pt");
				fulljasperprintexporter.setParameter(
						JRExporterParameter.JASPER_PRINT, fulljapserprint);
				fulljasperprintexporter.setParameter(
						JRExporterParameter.OUTPUT_WRITER, fullstringwriter);
				fulljasperprintexporter.setParameter(
						JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
				if(jctx.isWrapbreakword()){
					fulljasperprintexporter.setParameter(JRHtmlExporterParameter.IS_WRAP_BREAK_WORD, Boolean.TRUE);
				}
				fulljasperprintexporter.exportReport();
				out.println(fullstringwriter.toString());
				out.println("</div>");
				
				//fulljasperprintexporter.
				/*
				Map<String,byte[]> mapdata = fulljasperprintexporter.getImageNameToImageDataMap();
				Map<String,String > maptype = fulljasperprintexporter.getImageNameToImageTypeMap();
				Set<String> keys = mapdata.keySet();
				for(String imgname : keys ){
					jpcacher.put(cahcerid, prtkey+"$full$"+imgname+"$imageMimeType", maptype.get(imgname));
					jpcacher.put(cahcerid, prtkey+"$full$"+imgname+"$imageData", mapdata.get(imgname));
				}
				*/
			}

			if (onimgjasperprint != null) {
				out.println("<div id='noimgjpdiv' style='padding:0px;border:2px solid #000000;width:1px;height:1px;overflow:auto;' >");
				StringWriter noimgstringwriter = new StringWriter();
				FossJRXhtmlExporter noimgjasperprintexporter = new FossJRXhtmlExporter();
				noimgjasperprintexporter.setParameter(
						JRHtmlExporterParameter.IMAGES_DIR_NAME,
						request.getSession().getServletContext()
								.getRealPath("/image")
								+ "\\");
				noimgjasperprintexporter.setParameter(
						JRHtmlExporterParameter.IMAGES_URI,
						ctxpath+"/servlets/printimage?"
								+ FossPrintConst.KEY_JASPER_PRINT_CACHE_ID
								+ "=" + cahcerid + "&"
								+ FossPrintConst.KEY_JAPSER_PRINT_KEY + "="
								+ prtkey+"$noimg" + "&image=");
				noimgjasperprintexporter.setParameter(
						JRHtmlExporterParameter.HTML_HEADER, "<span style=\"padding:0px;margin:0px;\">");
				noimgjasperprintexporter.setParameter(
						JRHtmlExporterParameter.HTML_FOOTER, "</span>");
				noimgjasperprintexporter.setParameter(
						JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
						Boolean.TRUE);
				noimgjasperprintexporter.setParameter(
						JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,
						Boolean.TRUE);
				noimgjasperprintexporter.setParameter(
						JRHtmlExporterParameter.SIZE_UNIT, "pt");
				noimgjasperprintexporter.setParameter(
						JRExporterParameter.JASPER_PRINT, onimgjasperprint);
				noimgjasperprintexporter.setParameter(
						JRExporterParameter.OUTPUT_WRITER, noimgstringwriter);
				noimgjasperprintexporter.setParameter(
						JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
				if(jctx.isWrapbreakword()){
					noimgjasperprintexporter.setParameter(JRHtmlExporterParameter.IS_WRAP_BREAK_WORD, Boolean.TRUE);
				}
				noimgjasperprintexporter.exportReport();
				out.println(noimgstringwriter.toString());
				out.println("</div>");
				
				/*
				Map<String,byte[]> mapdata = noimgjasperprintexporter.getImageNameToImageDataMap();
				Map<String,String > maptype = noimgjasperprintexporter.getImageNameToImageTypeMap();
				Set<String> keys = mapdata.keySet();
				for(String imgname : keys ){
					jpcacher.put(cahcerid, prtkey+"$noimg$"+imgname+"$imageMimeType", maptype.get(imgname));
					jpcacher.put(cahcerid, prtkey+"$noimg$"+imgname+"$imageData", mapdata.get(imgname));
				}*/
			}
			
		} catch (Exception e) {
			StringWriter sw = new StringWriter();  
            e.printStackTrace(new PrintWriter(sw, true));  
			out.println("<div id='fulljpdiv' style='border:2px solid #000000;width:1px;height:1px;overflow:auto;' ><div id='page1' class='jrPage'>"+sw.toString()+"</div></div>");
			
			log.error("[ ExportHtmlServlet error ]",e);
		}
		
		out.println("<input type='hidden' id='pagewidth' value='"+(pagewidth)+"' >");
		out.println("<input type='hidden' id='pageheight' value='"+(pageheight)+"' >");
		out.println("<input type='hidden' id='orientation' value='"+orientation+"' >");
		out.println("<input type='hidden' id='hasimg' value='"+Boolean.valueOf(jctx.isHasImage()).toString()+"' >");
		if(jctx.isFixpagesize()){
			out.println("<input type='hidden' id='fixpagesize' value='true' >");
		}
		else {
			out.println("<input type='hidden' id='fixpagesize' value='false' >");
		}
	}
}
