package com.deppon.foss.deploy;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.CollectionUtils;

/**
 * 基于maven插件org.apache.servicemix.tooling:depends-maven-plugin产生的dependencies.properties，
 * 分析foss的web项目对于其他jar的依赖关系，以辅助决策在代码改动后，哪些web包应该进行升级。
 * 分析的结果以excel作为输出格式 
 * 
 * @author zhengwl
 *
 */
public class FossDependencyAnayzer {
	private static Log log = LogFactory.getLog(FossDependencyAnayzer.class);
	
	/**
	 * 依赖分析时，groupId所应有的前缀。
	 * 默认只分析对于com.deppon的artifact，可能包含foss的，也可能包含dpap的；
	 */
	private String filteredPrefix = "com.deppon";
	
	/**
	 * 搜索dependencies.properties的表达式；
	 */
	private String resourcesPattern;
	
	/**
	 * 所有web应用对于jar包的依赖。
	 * key为web应用的artifactId，value为artifact的列表；
	 */
	private Map<String, List<Artifact>> allDependencies = new HashMap<String, List<Artifact>>();
	
	/**
	 * 所有的foss项目用到的artifact列表；
	 */
	private Set<Artifact> allArtifacts = new TreeSet<Artifact>();
		
	public String getResourcesPattern() {
		return resourcesPattern;
	}
	public void setResourcesPattern(String resourcesPattern) {
		this.resourcesPattern = resourcesPattern;
	}
	public Map<String, List<Artifact>> getAllDependencies() {
		return allDependencies;
	}
	
	/**
	 * 搜索所有的dependencies.properties，依次分析项目依赖
	 * @throws Exception
	 */
	public void analyzeAll() throws Exception {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resolver.getResources(resourcesPattern);
		for(Resource resource: resources) {
			log.info("分析文件： " + resource.getFile().getAbsolutePath());
			analyze(resource);
		}
	}
	
	/**
	 * 对于单个dependencies.properties，分析依赖
	 * @param resource
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void analyze(Resource resource) throws Exception {
		Properties properties = new Properties();
		properties.load(resource.getInputStream());
		
		// 获取web项目的artifactId，作为key放入allDependencies这个map中；
		String artifactId = (String)properties.get("artifactId");
		
		List<Artifact> dependencies = new ArrayList<Artifact>();
		
		for(Map.Entry entry: properties.entrySet()) {
			// 对于dependencies.properties中的每个条目，解析为artifact；
			Artifact dependency = parseAsArtifact(entry);

			// 填充artifact的每个属性值
			if ( dependency != null && 
					!StringUtils.equals(dependency.getArtifactId(), artifactId) ) {
				if ( StringUtils.startsWith(dependency.getGroupId(), filteredPrefix) ) 
					mergeDependency(dependency, dependencies);
			}
		}
		
		if ( !allDependencies.containsKey(artifactId) ) 
			allDependencies.put(artifactId, dependencies);
		
	}
	
	/**
	 * 对于同一个artifact，在properties文件中有不同的条目对应；
	 * 所以需要在List中找到Artifact，把缺少的属性值填充过去；
	 * 
	 * @param dependency
	 * @param dependencies
	 */
	private void mergeDependency(Artifact dependency, List<Artifact> dependencies) {
		if ( !CollectionUtils.isEmpty(dependencies) ) {
			boolean exists = false;

			for(Artifact dep: dependencies) {
				if ( ObjectUtils.equals(dep, dependency)) {
					if ( StringUtils.isNotEmpty(dependency.getScope()) ) 
						dep.setScope(dependency.getScope());
					if ( StringUtils.isNotEmpty(dependency.getVersionId()) )
						dep.setVersionId(dependency.getVersionId());
					if ( StringUtils.isNotEmpty(dependency.getType()))
						dep.setType(dependency.getType());
					
					exists = true;
					break;
				}
			}
			if ( !exists ) {
				dependencies.add(dependency);
			}
		} else {
			dependencies.add(dependency);
		}
		
		allArtifacts.add(dependency);
	}
	
	/**
	 * 对于单个properties条目，解析为artifact
	 * @param entry
	 * @return
	 */
	private Artifact parseAsArtifact(Map.Entry entry) {
		String key = (String)entry.getKey();
		if ( !StringUtils.contains(key, "/") )
			return null;
		
		String[] dimensions = StringUtils.split(key, "/");
		
		if ( ArrayUtils.isEmpty(dimensions))
			return null;
		
		Artifact artifact = new Artifact();
		
		artifact.setGroupId(dimensions[0]);
		artifact.setArtifactId(dimensions[1]);
		if ( StringUtils.equals("version", dimensions[2])) {
			artifact.setVersionId((String)entry.getValue());
		}
		if ( StringUtils.equals("type", dimensions[2])) {
			artifact.setType((String)entry.getValue());
		}
		if ( StringUtils.equals("scope", dimensions[2])) {
			artifact.setScope((String)entry.getValue());
		}
		
		return artifact;
	}
	
	/**
	 * 主应用入口。
	 * 解析依赖，并生成excel文件
	 * @throws Exception
	 */
	private void generateDependencies(String outputFilename) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		FileOutputStream output = new FileOutputStream(outputFilename);
		
		HSSFSheet sheet = workbook.createSheet("依赖关系");
		for(int i=0; i<=allDependencies.size(); i++) {
			sheet.autoSizeColumn(0);
		}
		
		// 生成第一行的单元格。列出所有的web应用
		createHeader(workbook, sheet);
		
		int rowPosition = 1;
		for(Artifact artifact: allArtifacts) {
			Row row = sheet.createRow(rowPosition);
			
			int cellPosition = 0;
			Cell cell = row.createCell(cellPosition++);
			cell.setCellValue(artifact.getArtifactId());
			
			for(int i=0; i<allDependencies.size(); i++) {
				// 依次取到所有的web应用名称
				HSSFRow firstRow = HSSFCellUtil.getRow(0, sheet);
				HSSFCell c = HSSFCellUtil.getCell(firstRow, cellPosition+i);
				
				// 取得应用所依赖的artifact列表
				List<Artifact> dependencies = allDependencies.get(c.getStringCellValue());
				if ( dependencies.contains(artifact) ) {
					Cell c1 = row.createCell(cellPosition+i);
					c1.setCellValue("Y");
				}
			}

			rowPosition++;
		}
		
		workbook.write(output);
		output.close();
	}
	
	private void createHeader(HSSFWorkbook workbook, HSSFSheet sheet) {
		Row row = sheet.createRow(0);
		
		int cellPosition = 1;
		CellStyle style = workbook.createCellStyle();
		/*
		style.setFillForegroundColor(HSSFColor.GREEN.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		HSSFFont font = workbook.createFont();
		font.setFontName("华文细黑");
		font.setColor(HSSFColor.WHITE.index);
		font.setFontHeightInPoints((short)12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);
		*/
		for(String webapp: allDependencies.keySet()) {
			
			Cell cell = row.createCell(cellPosition);
			row.setHeightInPoints(14);
			
			cell.setCellStyle(style);
			cell.setCellValue(webapp);
			
			cellPosition++;
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String file = args[0];
		log.info("Output file: " + file);
		ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
		FossDependencyAnayzer analyzer = context.getBean("dependencyAnalyzer", FossDependencyAnayzer.class);
		log.info("开始分析。。。");
		
		analyzer.analyzeAll();
		analyzer.generateDependencies(file);
		
		log.info("结束分析。。。");
	}

}
