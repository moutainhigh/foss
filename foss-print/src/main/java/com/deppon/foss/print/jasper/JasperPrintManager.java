package com.deppon.foss.print.jasper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRImage;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.fill.JRTemplatePrintImage;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.print.util.ClassPathResourceUtil;
import com.deppon.foss.print.util.W3CXmlParser;

/**
 * 调用jasperreport 作为打印功能的核心实现
 * @author niujian
 */
public class JasperPrintManager {
	
	private static final Log log = LogFactory.getLog(JasperPrintManager.class);
	
	public static final String FOSSPRT_XML_KEY_ROOT = "fossprt";
	public static final String FOSSPRT_XML_KEY_ID = "fossprt.id";
	public static final String FOSSPRT_XML_KEY_NAME = "fossprt.name";
	public static final String FOSSPRT_XML_KEY_DESCRIPTION = "fossprt.description";
	public static final String FOSSPRT_XML_KEY_FIX_PAGE_SIZE = "fossprt.fixpagesize";
	public static final String FOSSPRT_XML_KEY_HTML_EXP_WRAP_BREAK_WORD = "fossprt.wrapbreakwrd";
	public static final String FOSSPRT_XML_KEY_FILE_JRXML = "fossprt.jrxml";
	public static final String FOSSPRT_XML_KEY_FILE_JASPER = "fossprt.jasper";
	public static final String FOSSPRT_XML_KEY_JAVADS = "fossprt.javads";
	public static final String FOSSPRT_XML_KEY_PARAMS_PARAM = "fossprt.parameters.parameter";
	
	public static final String KEY_BACKGROUND_IMG = "BG_IMG";
	
	JasperContext mJasperContext = null;
	
	public JasperPrintManager(JasperContext jasperContext) {
		mJasperContext = jasperContext;
	}
	
	private void processLoadPrtXml() throws Exception {
		buildJasperContext(mJasperContext); 
	}
	
	/**
	 * 执行数据源java程序，生成P,map数据源和detail list数据源
	 * @throws Exception
	 */
	private void processBuildDataSource() throws Exception {
		
		try{
			Object datasource = JasperDataSourceFactory.createReportDataSource(mJasperContext);
			if(datasource instanceof JasperDataSource ){
				JasperDataSource reportDataSource = (JasperDataSource)datasource;
				
				Map<String,Object> parameters = reportDataSource.createParameterDataSource(mJasperContext);
				if(parameters==null){
					parameters = new HashMap<String, Object>();
				}
				mJasperContext.setJasperParameters(parameters);
				
				List<Map<String,Object>> detaillist = reportDataSource.createDetailDataSource(mJasperContext);
				mJasperContext.setJasperDetailList(detaillist);
			}
			else if(datasource instanceof MultiJasperDataSource ){
				mJasperContext.setMultiDataSource((MultiJasperDataSource)datasource);
			}
		}catch(Exception e){
			log.error("[ processBuildDataSource error ]",e);
			throw e;
		}
		
	}
	
	/**
	 * 直接读取.jasper文件获得jasperreport 对象
	 * @throws Exception
	 */
	private void processComplieJasperFile() throws Exception {
		try{
			String jasperfile = mJasperContext.getJasperfile();
			String jrxmlfile = mJasperContext.getJrxmlfile();
			
			if(jasperfile!=null && jrxmlfile!=null){
				
				ClassPathResourceUtil resourceUtil = new ClassPathResourceUtil(mJasperContext.getClassLoader());
				JasperReport jasperReport = (JasperReport)JRLoader.loadObject(resourceUtil.getInputStream(jasperfile));
				mJasperContext.setJasperReport(jasperReport);
				
				JasperDesign jasperDesign = JRXmlLoader.load(resourceUtil.getInputStream(jrxmlfile));
				mJasperContext.setJasperDesign(jasperDesign);
				
				boolean hasImage = false;
				JRBand[] bands = jasperDesign.getAllBands();
				for(JRBand band : bands){
					JRElement[] elements = band.getElements();
					for(JRElement element : elements){
						if(element instanceof JRImage){
							if( KEY_BACKGROUND_IMG.equals(((JRImage)element).getKey()) ){
								hasImage = true;
								break;
							}
						}
					}
				}
				mJasperContext.setHasImage(hasImage);
			}
			else {
				throw new Exception("打印模板定义文件不正确，请编译打印模板文件");
			}
			
		}catch(Exception e){
			log.error("[ processComplieJasperFile error ]" , e);
			throw e;
		}
		
	}
	
	/**
	 * 执行数据源合并输出的打印结果的打印模块，把数据源填充到打印模板，
	 * 调用分多数据源和单数据源两种情况处理
	 * @throws Exception
	 */
	private JasperPrint processGeneratJasperResult() throws Exception {
		try{
			JasperPrint jasperPrint = null;
			if(mJasperContext.getMultiDataSource()!=null){
				processMultiDSJasperResult();
			}
			else {
				processSingleDSJasperResult();
			}
			
			jasperPrint = mJasperContext.getFullJasperPrint();
			return jasperPrint;
		}catch(Exception e){
			log.error("[ processGeneratJasperResult error ]",e);
			throw e;
		}
		
	}
	
	/**
	 * 执行单数据源合并输出的打印结果的打印模块，把单数据源填充到打印模板，
	 * @throws Exception
	 */
	private void processSingleDSJasperResult() throws Exception {
		try{
			JasperReport jasperReport = mJasperContext.getJasperReport();
			
			Map<String,Object> parameters = mJasperContext.getJasperParameters();
			Map<String, Object> noimgparameters = new HashMap<String, Object>(parameters);
			// clone for inputstream values
			Set<String> keys = parameters.keySet();
			for (String key : keys) {
				Object value = parameters.get(key);
				if (value instanceof InputStream) {
					ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
					InputStream is = (InputStream)value;
					int ch;
					while ((ch = is.read()) != -1) {
						bytestream.write(ch);
					}
					byte bytearr[] = bytestream.toByteArray();
					bytestream.close();
					noimgparameters.put(key, new ByteArrayInputStream(bytearr.clone()));
					parameters.put(key, new ByteArrayInputStream(bytearr.clone()));
				}
			}
			
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(mJasperContext.getJasperDetailList());
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,ds);
			mJasperContext.setFullJasperPrint(jasperPrint);
			
			if(mJasperContext.isHasImage()){
				JRBeanCollectionDataSource jrBeanColDs = new JRBeanCollectionDataSource(mJasperContext.getJasperDetailList());
				JasperPrint vJasperPrint = JasperFillManager.fillReport(jasperReport,noimgparameters,jrBeanColDs);
				mJasperContext.setNoImgJasperPrint(vJasperPrint);
			}
		}catch(Exception e){
			log.error("[ processSingleDSJasperResult error ]",e);
			throw e;
		}
		
	}
	
	/**
	 * 执行多数据源合并输出的打印结果的打印模块，把多数据源填充到打印模板，
	 * @throws Exception
	 */
	private void processMultiDSJasperResult() throws Exception {
		try{
			JasperReport jasperReport = mJasperContext.getJasperReport();
			
			MultiJasperDataSource multids = mJasperContext.getMultiDataSource();
			JasperDataSource[] jds = multids.createJasperDataSources(mJasperContext);
			
			List<Map<String,Object>> paramMapList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> noimgParamMapList = new ArrayList<Map<String, Object>>();
			
			List<List<Map<String,Object>>> detailList = new ArrayList<List<Map<String,Object>>>();
			List<JasperPrint> jplist = new ArrayList<JasperPrint>();
			for(int i=0;i<jds.length;i++){
				Map<String,Object> parameters = jds[i].createParameterDataSource(mJasperContext);
				if(parameters==null){
					parameters = new HashMap<String, Object>();
				}
				paramMapList.add(parameters);
				Map<String, Object> noimgparameters = new HashMap<String, Object>(parameters);
				// clone for inputstream values
				Set<String> keys = parameters.keySet();
				for (String key : keys) {
					Object value = parameters.get(key);
					if (value instanceof InputStream) {
						ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
						InputStream is = (InputStream)value;
						int ch;
						while ((ch = is.read()) != -1) {
							bytestream.write(ch);
						}
						byte bytearr[] = bytestream.toByteArray();
						bytestream.close();
						noimgparameters.put(key, new ByteArrayInputStream(bytearr.clone()));
						parameters.put(key, new ByteArrayInputStream(bytearr.clone()));
					}
				}
				noimgParamMapList.add(noimgparameters);
				
				List<Map<String,Object>> details = jds[i].createDetailDataSource(mJasperContext);
				detailList.add(details);
				JRBeanCollectionDataSource jbeancollds = new JRBeanCollectionDataSource(details);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,jbeancollds);
				jplist.add(jasperPrint);
			}
			
			JasperPrint fulljp = jplist.get(0);
			for(int i=1;i<jplist.size();i++){
				List<JRPrintPage> pages = jplist.get(i).getPages();
				for(JRPrintPage page : pages){
					fulljp.addPage(page);
				}
			}
			mJasperContext.setFullJasperPrint(fulljp);
			
			if(mJasperContext.isHasImage()){
				jplist = new ArrayList<JasperPrint>();
				for(int i=0;i<noimgParamMapList.size();i++){
					Map<String,Object> parameters = noimgParamMapList.get(i);
					List<Map<String,Object>> details = detailList.get(i);
					JRBeanCollectionDataSource jbeancollds = new JRBeanCollectionDataSource(details);
					JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,jbeancollds);
					jplist.add(jasperPrint);
				}
				
				JasperPrint noimgjp = jplist.get(0);
				for(int i=1;i<jplist.size();i++){
					List<JRPrintPage> pages = jplist.get(i).getPages();
					for(JRPrintPage page : pages){
						noimgjp.addPage(page);
					}
				}
				mJasperContext.setNoImgJasperPrint(noimgjp);
			}
		}catch(Exception e){
			log.error("[ processMultiDSJasperResult ]" ,e);
			throw e;
		}
	}
	
	/**
	 * 对于有背景图片的打印模板，修正打印输出结果删除一个jasperrpint对象上的image元素，
	 * 生成一个没有背景图的打印输出结果
	 */
	private void processNoImageJasperPrintUpdate(){
		JasperPrint noimgjp = mJasperContext.getNoImgJasperPrint();
		if(noimgjp!=null){
			List<JRPrintPage> pages = noimgjp.getPages();
			for(JRPrintPage page : pages){
				List<JRPrintElement> removelist = new ArrayList<JRPrintElement>();
				
				List<JRPrintElement> elements = page.getElements();
				for(JRPrintElement element : elements){
					if (element instanceof JRTemplatePrintImage) {
						if (KEY_BACKGROUND_IMG.equals(((JRTemplatePrintImage) element).getKey())) {
							removelist.add(element);
						}
					}
				}
				
				for(Object o : removelist){
					elements.remove(o);
				}
			}
			mJasperContext.setNoImgJasperPrint(noimgjp);
		}
	}
	
	/**
	 * 打印过程的调用入口类
	 * @param jasperContext
	 * @return
	 * @throws Exception
	 */
	public JasperPrint processPrintResult(JasperContext jasperContext) throws Exception {
		processLoadPrtXml();
		
		processBuildDataSource();
		
		processComplieJasperFile();
		
		JasperPrint jasperPrint = processGeneratJasperResult();
		
		processNoImageJasperPrintUpdate();
		
		return jasperPrint;
	}
	
	public void processPrintResultInPreviewer(JasperContext jasperContext) throws Exception {
		processPrintResult(jasperContext);
		JasperPrint fullJp = jasperContext.getFullJasperPrint();
		JasperPrint noImgJp = jasperContext.getNoImgJasperPrint();
		new PreformatViewer(fullJp, noImgJp, false);
	}
	
	public void processPrintResultDirectWithImage(JasperContext jasperContext) throws Exception {
		try{
			processPrintResult(jasperContext);
			JasperPrint fulljp = jasperContext.getFullJasperPrint();
			net.sf.jasperreports.engine.JasperPrintManager.printReport(fulljp, false);
		}catch (Exception e) {
			log.info("[ JasperPrintManager.processPrintResultDirectWithImage error ]", e);
			throw new Exception("打印失败");
		}
	}
	
	public void processPrintResultDirectWithoutImage(JasperContext jasperContext) throws Exception {
		try{
			processPrintResult(jasperContext);
			JasperPrint noImgJp = jasperContext.getNoImgJasperPrint();
			//new PreformatViewer(noImgJp, null , false);
			net.sf.jasperreports.engine.JasperPrintManager.printReport(noImgJp, true);
		}catch (Exception e) {
			log.info("[ JasperPrintManager.processPrintResultDirectWithoutImage error ]", e);
			throw new Exception("打印失败");
		}
	}
	
	private void buildJasperContext(JasperContext jasperContext) throws Exception {
		try{
			String prtkey = jasperContext.getPrtkey();
			log.info("[ buildJasperContext for print: "+prtkey+" ]");
			String prtxml = "com/deppon/foss/prt/"+prtkey+"/"+prtkey+".xml";
			ClassPathResourceUtil resourceUtil = new ClassPathResourceUtil(mJasperContext.getClassLoader());
			InputStream in = resourceUtil.getInputStream(prtxml);
			
			W3CXmlParser _w3cxmlparser = new W3CXmlParser(in);
			 
			jasperContext.setPrtname(_w3cxmlparser.getValueOfConfigItem(FOSSPRT_XML_KEY_NAME));
			jasperContext.setDescription(_w3cxmlparser.getValueOfConfigItem(FOSSPRT_XML_KEY_DESCRIPTION));
			jasperContext.setJasperfile(_w3cxmlparser.getValueOfConfigItem(FOSSPRT_XML_KEY_FILE_JASPER));
			jasperContext.setJrxmlfile(_w3cxmlparser.getValueOfConfigItem(FOSSPRT_XML_KEY_FILE_JRXML));
			jasperContext.setDatasourceclz(_w3cxmlparser.getValueOfConfigItem(FOSSPRT_XML_KEY_JAVADS));
			try{
				jasperContext.setFixpagesize(Boolean.valueOf(_w3cxmlparser.getValueOfConfigItem(FOSSPRT_XML_KEY_FIX_PAGE_SIZE)).booleanValue());	
			}catch(Exception e){
				jasperContext.setFixpagesize(false);
			}
			
			try{
				jasperContext.setWrapbreakword(Boolean.valueOf(_w3cxmlparser.getValueOfConfigItem(FOSSPRT_XML_KEY_HTML_EXP_WRAP_BREAK_WORD)).booleanValue());	
			}catch(Exception e){
				jasperContext.setWrapbreakword(false);
			}
			
			String[] params = _w3cxmlparser.getValuesOfConfigItem(FOSSPRT_XML_KEY_PARAMS_PARAM);
			if(params!=null){
				for(int i=0;i<params.length;i++){
					if(jasperContext.getRequest()!=null){
						jasperContext.put(params[i], jasperContext.getRequest().getParameter(params[i]));
						String[] values = jasperContext.getRequest().getParameterValues(params[i]);
						if(values!=null && values.length>1  ){
							jasperContext.put(params[i], jasperContext.getRequest().getParameterValues(params[i]));	
						}
					}
					else {
						//jasperContext.put(params[i], null);	
					}
					jasperContext.addParamKey(params[i]);
				}
			}
		}catch(Exception e){
			log.error("[ buildJasperContext error ]", e);
			throw e;
		}
		
	}
	
	/**
	 * 新增对更改单打印模板的方法，主要实现功能：提供对对象背景不打印
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-5-5 20:58:54
	 * @param jasperContext
	 * @throws Exception
	 */
	public void processModifyPrintResultInPreviewer(JasperContext jasperContext) throws Exception {
		processPrintResult(jasperContext);
		JasperPrint fullJp = jasperContext.getFullJasperPrint();
		JasperPrint noImgJp = jasperContext.getNoImgJasperPrint();
		if(noImgJp == null){
			//对象的复制，实属无奈之举，必须是对象实现序列化了
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream(); 
			ObjectOutputStream out = new ObjectOutputStream(byteOut); 
			out.writeObject(fullJp); 

			ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray()); 
			ObjectInputStream in = new ObjectInputStream(byteIn); 
			noImgJp = (JasperPrint)in.readObject();
			//对象复制结束
			if(noImgJp!=null){
				List<JRPrintPage> pages = noImgJp.getPages();
				for(JRPrintPage page : pages){
					List<JRPrintElement> removelist = new ArrayList<JRPrintElement>();
					
					List<JRPrintElement> elements = page.getElements();
					for(JRPrintElement element : elements){
						if (element instanceof JRTemplatePrintImage) {
							if (KEY_BACKGROUND_IMG.equals(((JRTemplatePrintImage) element).getKey())) {
								removelist.add(element);
							}
						}
					}
					
					for(Object o : removelist){
						elements.remove(o);
					}
				}
				mJasperContext.setNoImgJasperPrint(noImgJp);
			}
		}
		String printKey = jasperContext.getPrtkey();
		boolean isPrintHalf = false;
		if(printKey.indexOf("half")>=0){
			isPrintHalf = true;
		}
		new ModifyFormatViewer(fullJp, noImgJp, false, isPrintHalf);
	}
}
