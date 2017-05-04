package com.deppon.foss.module.transfer.platform.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.platform.api.server.service.IPlatformCommonService;
import com.deppon.foss.module.transfer.platform.api.server.service.IPlatformOpeEffiService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.PlatformOpeEffiEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.PlatformOpeEffiCondiDto;

public class PlatformOpeEffiAction extends AbstractAction{

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 1L;
	//常量，表示查询类型为月台操作效率
	private static final String PLATFORM = "platform";
	//常量，表示查询类型为月台操作效率明细
	private static final String PLATFORMDETAIL = "platformDetail";
	//月台操作效率service
	private IPlatformOpeEffiService platformOpeEffiService;
	//查询条件
	private PlatformOpeEffiCondiDto queryCondition = new PlatformOpeEffiCondiDto();
	//查询结果列表
	private List<PlatformOpeEffiEntity> platformOpeEffiList;
	//月台编号列表
	private List<String> platformCodeList;
	
	private IPlatformCommonService platformCommonService;
	
	public void setPlatformCommonService(
			IPlatformCommonService platformCommonService) {
		this.platformCommonService = platformCommonService;
	}
	public List<String> getPlatformCodeList() {
		return platformCodeList;
	}
	public void setPlatformCodeList(List<String> platformCodeList) {
		this.platformCodeList = platformCodeList;
	}
	/**
	 * 导出Excel 文件流
	 */
	private InputStream inputStream;

	/**
	 * 导出Excel 文件名
	 */
	private String downloadFileName;
	
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getDownloadFileName() {
		return downloadFileName;
	}
	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
	
	/**
	 * 
	* @Title: queryPlatformOpeEffi 
	* @Description: 查询月台操作效率信息
	* @author 105944
	* @date 2015-3-24 下午2:54:31  
	* @param @return    
	* @return String    
	* @throws
	 */
	@SuppressWarnings("unchecked")
	@JSON
	public String queryPlatformOpeEffi(){
		Map<String,Object> result = new HashMap<String,Object>();
		//判断查询类型
		if(PLATFORM.equals(queryCondition.getQueryType())){
			result = platformOpeEffiService.queryPlatformOpeEffi(queryCondition, this.getStart(), this.getLimit());
		}else if(PLATFORMDETAIL.equals(queryCondition.getQueryType())){
			result = platformOpeEffiService.queryPlatformOpeEffiDetail(queryCondition, this.getStart(), this.getLimit());
		}
		platformOpeEffiList = (List<PlatformOpeEffiEntity>) result.get("platformOpeEffiList");
		totalCount =  Long.valueOf(result.get("totalCount").toString());
		return returnSuccess();
	}
	
	/**
	 * 
	* @Title: encodeFileName 
	* @Description: 编码格式判断与转化
	* @author 105944
	* @date 2015-3-24 下午3:34:07  
	* @param @param name
	* @param @return
	* @param @throws UnsupportedEncodingException    
	* @return String    
	* @throws
	 */
	private String encodeFileName(String name)
			throws UnsupportedEncodingException {
		String result;
		String agent = (String) ServletActionContext.getRequest().getHeader(
				"USER-AGENT");
		if (agent != null && agent.indexOf("MSIE") == -1) {
			result = new String(name.getBytes("UTF-8"), "iso-8859-1");
		} else {
			result = URLEncoder.encode(name, "UTF-8");
		}
		return result;
	}
	
	/**
	 * 
	* @Title: exportPlatformOpeEffiData 
	* @Description: 导出月台操作效率数据
	* @author 105944
	* @date 2015-3-24 下午2:55:52  
	* @param @return    
	* @return String    
	* @throws
	 */
	public String exportPlatformOpeEffiData(){
		ExportResource exportResource = platformOpeEffiService.exportPlatformOpeEffiData(queryCondition);

		// 导出文件名
		try {
			downloadFileName = encodeFileName("月台操作效率.xls");
		} catch (UnsupportedEncodingException e) {
			downloadFileName = "platformOpeEffiData.xls";
		}

		ExportSetting exportSetting = new ExportSetting();
		// sheet名
		exportSetting.setSheetName("月台操作效率");

		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		// 导出文件
		inputStream = objExporterExecutor.exportSync(exportResource,
				exportSetting);

		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: queryOutfieldInfoByDeptCode 
	* @Description: 根据当前部门信息查询该部门所属外场及经营本部信息
	* @author 105944
	* @date 2015-3-24 下午4:15:26  
	* @param @param currentDeptCode
	* @param @return    
	* @return OrgAdministrativeInfoEntity    
	* @throws
	 */
	public String queryOrganizationInfoByDeptCode(){
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity outfieldEntity = platformCommonService.querySupTfrCtr(currentDeptCode);
		//如果查询到的外场信息不为空，则进行相应赋值
		if (null != outfieldEntity) {
			queryCondition.setOutfieldCode(outfieldEntity.getCode());
			queryCondition.setOutfieldName(outfieldEntity.getName());
		}
		//如果查询到的经营本部信息不为空，则进行相应赋值
		Map<String,String> businessDeptInfo = platformCommonService.findSupHq(currentDeptCode);
		if(null != businessDeptInfo){
			queryCondition.setBusinessDeptCode(businessDeptInfo.get("HQ_CODE"));
			queryCondition.setBusinessDeptName(businessDeptInfo.get("HQ_NAME"));
		}
		return SUCCESS;
	}
	public List<PlatformOpeEffiEntity> getPlatformOpeEffiList() {
		return platformOpeEffiList;
	}
	public void setPlatformOpeEffiList(
			List<PlatformOpeEffiEntity> platformOpeEffiList) {
		this.platformOpeEffiList = platformOpeEffiList;
	}
	public void setPlatformOpeEffiService(
			IPlatformOpeEffiService platformOpeEffiService) {
		this.platformOpeEffiService = platformOpeEffiService;
	}
	public PlatformOpeEffiCondiDto getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(PlatformOpeEffiCondiDto queryCondition) {
		this.queryCondition = queryCondition;
	}
}
