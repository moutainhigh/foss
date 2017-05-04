package com.deppon.foss.print.labelprint.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.deppon.foss.print.labelprint.util.ClassPathResourceUtil;
import com.deppon.foss.print.labelprint.util.W3CXmlParser;

public class PrintProgramUtil {

	private static PrintProgramUtil instance;
	private List<Map> lstPrintProgram;
	private PrintProgramUtil() throws Exception{
		initPrintProgram();
	}
	
	private void initPrintProgram() throws Exception {
		lstPrintProgram = new ArrayList();
		ClassPathResourceUtil _ru = new ClassPathResourceUtil();
		InputStream in = _ru.getInputStream("printprogram.xml");
		W3CXmlParser parser = new W3CXmlParser(in);
		lstPrintProgram.addAll(parser.getChildMapAsList("fosslblprt.printprograms.program"));
	}
	
	public static PrintProgramUtil getInstance() throws Exception{
		if(instance ==null ){
			instance = new PrintProgramUtil();
		}
		return instance;
	}
	
	public Map findPrintProgramByIndex(int index ){
		if(lstPrintProgram!=null){
			return lstPrintProgram.get(index);
		}
		else {
			return null;
		}
	}
	
	public Map findPrintProgramBycode(String pCode ){
		if(lstPrintProgram!=null){
			for(Map m : lstPrintProgram){
				if(m.get("code").equals(pCode)){
					return m;
				}
			}
		}
		return null;
	}
	
	public String findPrintProgramClzByCode(String pCode){
		Map m = findPrintProgramBycode(pCode );
		if(m!=null){
			return (String)m.get("javaclz");
		}
		else {
			return null;
		}
	}
	
	public List<Map> getAllList(){
		return lstPrintProgram;
	}
}
