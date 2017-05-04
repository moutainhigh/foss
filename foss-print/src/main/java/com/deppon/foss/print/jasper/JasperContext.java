package com.deppon.foss.print.jasper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.ApplicationContext;

import com.deppon.foss.print.dao.IPrintDao;

/**
 * 
 * @author niujian
 *
 */
public class JasperContext {
	
	IPrintDao iPrintDao;
	SqlSession sqlsession;
	
	List<String> paramkeys = new ArrayList<String>();
	Map<String,Object> paramMap = new HashMap<String,Object>();
	
	//打印模块的key
	String prtkey;
	String prtname;
	String description;
	
	//打印模块的模板文件 .jasper文件
	String jasperfile;
	//打印模块的模板文件 .jrxml文件
	String jrxmlfile;
	
	//打印模块的java 数据源对象类
	String datasourceclz;
	
	// 缓冲参数用于运行时缓存编译好的jasperreport打印模板对象
	JasperDesign jasperDesign;
	JasperReport jasperReport;
	
	// 参数用于标记此打印模板是否包含背景图片
	boolean hasImage;
	
	// 参数用于标记打印模板是否要求打印输出效果在 lodop控件上是锁定页面大小
	boolean fixpagesize;
	
	// 参数要求打印输出结果中文字支持自动换行
	boolean wrapbreakword;
	
	//单数据源模式下的单数据源集合
	Map<String,Object> jasperParameters = null;
	List<Map<String,Object>> jasperDetailList = null;
	
	//多数据源模式下的数据源java ds 对象
	MultiJasperDataSource multiDataSource = null;
	
	ClassLoader classLoader = null;
	HttpServletRequest request = null;
	String ctxPath = null;
	
	//包含全部元素的打印输出对象
	JasperPrint fullJasperPrint = null;
	//去除了背景图片的打印输出对象
	JasperPrint noImgJasperPrint = null; 
	//spring context 全局对象
	ApplicationContext applicationContext = null;
	
	public String getPrtkey() {
		return prtkey;
	}
	public void setPrtkey(String prtkey) {
		this.prtkey = prtkey;
	}
	public String getJasperfile() {
		return jasperfile;
	}
	public void setJasperfile(String jasperfile) {
		this.jasperfile = jasperfile;
	}
	public String getJrxmlfile() {
		return jrxmlfile;
	}
	public void setJrxmlfile(String jrxmlfile) {
		this.jrxmlfile = jrxmlfile;
	}	
	public String getDatasourceclz() {
		return datasourceclz;
	}
	public void setDatasourceclz(String datasourceclz) {
		this.datasourceclz = datasourceclz;
	}
	public IPrintDao getiPrintDao() {
		return iPrintDao;
	}
	public void setiPrintDao(IPrintDao iPrintDao) {
		this.iPrintDao = iPrintDao;
	}
	public SqlSession getSqlsession() {
		return sqlsession;
	}
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}
	
	public String getPrtname() {
		return prtname;
	}
	public void setPrtname(String prtname) {
		this.prtname = prtname;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void addParamKey(String key){
		paramkeys.add(key);	
	}
	public List<String> getParamkeys() {
		return paramkeys;
	}
	public void put(String key,Object value){
		paramMap.put(key, value);
	}
	
	public Object get(String key){
		return paramMap.get(key);
	}
	
	public Map<String,Object> getParamMap(){
		return paramMap;
	}
	
	public Map<String,Object> getJasperParameters() {
		return jasperParameters;
	}
	public void setJasperParameters(Map<String,Object> jasperParameters) {
		this.jasperParameters = jasperParameters;
	}
	public List<Map<String,Object>> getJasperDetailList() {
		return jasperDetailList;
	}
	public void setJasperDetailList(List<Map<String,Object>> jasperDetailList) {
		this.jasperDetailList = jasperDetailList;
	}
	public ClassLoader getClassLoader() {
		return classLoader;
	}
	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}
	public JasperPrint getFullJasperPrint() {
		return fullJasperPrint;
	}
	public void setFullJasperPrint(JasperPrint fullJasperPrint) {
		this.fullJasperPrint = fullJasperPrint;
	}
	public JasperPrint getNoImgJasperPrint() {
		return noImgJasperPrint;
	}
	public void setNoImgJasperPrint(JasperPrint noImgJasperPrint) {
		this.noImgJasperPrint = noImgJasperPrint;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	public JasperDesign getJasperDesign() {
		return jasperDesign;
	}
	public void setJasperDesign(JasperDesign jasperDesign) {
		this.jasperDesign = jasperDesign;
	}
	public JasperReport getJasperReport() {
		return jasperReport;
	}
	public void setJasperReport(JasperReport jasperReport) {
		this.jasperReport = jasperReport;
	}
	public MultiJasperDataSource getMultiDataSource() {
		return multiDataSource;
	}
	public void setMultiDataSource(MultiJasperDataSource multiDataSource) {
		this.multiDataSource = multiDataSource;
	}
	public boolean isHasImage() {
		return hasImage;
	}
	public void setHasImage(boolean hasImage) {
		this.hasImage = hasImage;
	}
	public boolean isFixpagesize() {
		return fixpagesize;
	}
	public void setFixpagesize(boolean fixpagesize) {
		this.fixpagesize = fixpagesize;
	}
	public boolean isWrapbreakword() {
		return wrapbreakword;
	}
	public void setWrapbreakword(boolean wrapbreakword) {
		this.wrapbreakword = wrapbreakword;
	}
	public String getCtxPath() {
		return ctxPath;
	}
	public void setCtxPath(String ctxPath) {
		this.ctxPath = ctxPath;
	}
	
}
