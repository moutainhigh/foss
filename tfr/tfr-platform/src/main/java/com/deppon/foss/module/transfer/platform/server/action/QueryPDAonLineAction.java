/**
 * 
 */
package com.deppon.foss.module.transfer.platform.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.server.service.IQueryPDAonLineService;
import com.deppon.foss.module.transfer.platform.api.server.service.ITfrCtrAbsenteeInfoService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.PDAOnlineUsingEntity;
import com.deppon.foss.module.transfer.platform.api.shared.vo.QueryPDAOnlineUsingVo;

/**
 * @author 105795
 *
 */
@SuppressWarnings("serial")
public class QueryPDAonLineAction extends AbstractAction {
	
	
	//注入pda在线查询vo
	private QueryPDAOnlineUsingVo queryPDAOnlineUsingVo=new QueryPDAOnlineUsingVo();

	
	private IQueryPDAonLineService queryPDAonLineService;
	
	private ITfrCtrAbsenteeInfoService tfrCtrAbsenteeInfoService;

	/**
	 * 导出Excel 文件流
	 */
	private InputStream excelStream;

	/**
	 * 导出Excel 文件名
	 */
	private String downloadFileName;
	//
	 /**
	 *com.deppon.foss.module.transfer.platform.server.action  
	 *@desc 根据当前员工工号查询对应的外场及经营本部
	 *@param 
	 *@return String
	 *@author 105795
	 *@date 2015年1月30日上午8:56:10 
	 */
	@JSON
	public String  queryTransferCenterCode()
	 {
		 try {
			 
			 PDAOnlineUsingEntity pdaOnlineUsingEntity=queryPDAonLineService.queryHqAndTransferCenter();
			 if(pdaOnlineUsingEntity!=null)
			 {
				  if(pdaOnlineUsingEntity.getHqCode()!=null)
				  {
					  queryPDAOnlineUsingVo.setHqCode(pdaOnlineUsingEntity.getHqCode());
				  }

				  if(pdaOnlineUsingEntity.getHqName()!=null)
				  {
					  queryPDAOnlineUsingVo.setHqName(pdaOnlineUsingEntity.getHqName());
				  }
				  if(pdaOnlineUsingEntity.getTransferCenterCode()!=null)
				  {
					  queryPDAOnlineUsingVo.setOrgCode(pdaOnlineUsingEntity.getTransferCenterCode());
				  }
				  if(pdaOnlineUsingEntity.getTransferCenterName()!=null)
				  {
					  queryPDAOnlineUsingVo.setOrgName(pdaOnlineUsingEntity.getTransferCenterName());
				  }

				 
			 }
		} catch (TfrBusinessException e) {
			returnError(e);
		}
		 return returnSuccess();
		 
		 
	 }
	
	//
	/**
	 *com.deppon.foss.module.transfer.platform.server.action  
	 *@desc 根据查询日期查询当天外场使用pda的情况
	 *@return String
	 *@author 105795
	 *@date 2015年2月3日上午10:12:20 
	 */
	@JSON
	public String queryOnLinePDAUsing()
	{
 
		try {
			//queryPDAonLineService.calculatePDAonline();
			//获取查询日期
			Date queryDate=queryPDAOnlineUsingVo.getQueryDate();
			//经营本部
			String hqCode=queryPDAOnlineUsingVo.getHqCode();
			//外场
			String transferCenterCode=queryPDAOnlineUsingVo.getOrgCode(); 
			
			PDAOnlineUsingEntity pdaOnlineUsingEntity=new PDAOnlineUsingEntity();
			pdaOnlineUsingEntity.setStaDate(queryDate);
			
			if(StringUtil.isNotEmpty(hqCode))
			{
				pdaOnlineUsingEntity.setHqCode(hqCode);	
			}
			if(StringUtil.isNotEmpty(transferCenterCode))
			{
				pdaOnlineUsingEntity.setTransferCenterCode(transferCenterCode);
			}
			Long totalCount = (long) queryPDAonLineService.queryAllCategoryPDAUsing(pdaOnlineUsingEntity).size();
			queryPDAOnlineUsingVo.setPdaOnlineUsingDtoList(queryPDAonLineService.queryAllCategoryPDAUsing(pdaOnlineUsingEntity,this.start,this.limit));
			
			super.setTotalCount(totalCount);
			
			
		} catch (TfrBusinessException e) {
			
			return returnError(e);
			
		}
		
		
		 return returnSuccess();
	}
	
	//
	/**
	 *com.deppon.foss.module.transfer.platform.server.action  
	 *@desc 根据查询日期查询当天外场本月初到查询当天的pda的使用情况
	 *@return String
	 *@author 105795
	 *@date 2015年2月3日上午10:13:03 
	 */
	@JSON
	public String queryPDAUsingDetail()
	{
	
		try {
			
			//月初日期
			Date startStaDate=null;
			//获取查询日期
			Date endStaDate=queryPDAOnlineUsingVo.getQueryDate();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//将时间格式化后获到月份
			String strDate=sdf.format(endStaDate).substring(PlatformConstants.SONAR_NUMBER_0, PlatformConstants.SONAR_NUMBER_7);
			//从1号开始
			strDate=strDate+"-01";
			
			try {
				startStaDate=sdf.parse(strDate);
			} catch (ParseException e) {
				throw new TfrBusinessException("转换日期格式异常");
				
			}
			//外场
			String transferCenterCode=queryPDAOnlineUsingVo.getOrgCode(); 


			Long totalCount = (long) queryPDAonLineService.queryAllCategoryPDAUsingDetail(transferCenterCode,startStaDate,endStaDate,0,0).size();
			queryPDAOnlineUsingVo.setPdaOnlineUsingDtoList(queryPDAonLineService.queryAllCategoryPDAUsingDetail(transferCenterCode,startStaDate,endStaDate,this.start,this.limit));
			
			super.setTotalCount(totalCount);
			
			
		} catch (TfrBusinessException e) {
			
			return returnError(e);
			
		}
		return returnSuccess();
		
	}
	
	//
	
	/**
	 *com.deppon.foss.module.transfer.platform.server.action  
	 *@desc 根据外场编码找外场名称
	 *@param 
	 *@return String
	 *@author 105795
	 *@date 2015年2月3日下午3:02:20 
	 */
	@JSON
	public String queryTransferCenterNameByCode()
	{
	
		try {
			
			//外场
			String transferCenterCode=queryPDAOnlineUsingVo.getOrgCode(); 
			
			Map<String,String> tfrMap=tfrCtrAbsenteeInfoService.queryParentTfrCtrCode(transferCenterCode);
			queryPDAOnlineUsingVo.setOrgName(tfrMap.get("name"));
			queryPDAOnlineUsingVo.setOrgCode(tfrMap.get("code"));

		} catch (TfrBusinessException e) {

			return returnError(e);
			
		}
		return returnSuccess();
		
		
	}
	
	/**
	 *com.deppon.foss.module.transfer.platform.server.action  
	 *@desc 导出pda使用明细
	 *@param 
	 *@return String
	 *@author 105795
	 *@date 2015年2月6日上午9:15:28 
	 */
	public String exportPDAUsingDetail(){
		
		try {
			
			Date startStaDate=null;
			//获取查询日期
			Date endStaDate=queryPDAOnlineUsingVo.getQueryDate();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//将时间格式化后获到月份
			String strDate=sdf.format(endStaDate).substring(PlatformConstants.SONAR_NUMBER_0, PlatformConstants.SONAR_NUMBER_7);
			//从1号开始
			strDate=strDate+"-01";
			
			try {
				startStaDate=sdf.parse(strDate);
			} catch (ParseException e) {
				throw new TfrBusinessException("转换日期格式异常");
				
			}
			//外场
			String transferCenterCode=queryPDAOnlineUsingVo.getOrgCode(); 
			
			try {
				downloadFileName = encodeFileName(PlatformConstants.TFRCTRPDAUSINGINFO_EXCEL_NAME);
			} catch (UnsupportedEncodingException e) {
				throw new TfrBusinessException("编码转换格式异常");
			}

			ExportResource exportResource =queryPDAonLineService.exportPDAUsingDetail(transferCenterCode, startStaDate, endStaDate);
			ExportSetting exportSetting = new ExportSetting();
			// 设置名称
			exportSetting
					.setSheetName(PlatformConstants.TFRCTRPDAUSINGINFO_EXCEL_NAME);
			ExporterExecutor objExporterExecutor = new ExporterExecutor();
			// 导出成文件
			excelStream = objExporterExecutor.exportSync(exportResource,
					exportSetting);
			
			
			
		} catch (TfrBusinessException e) {
			
			return this.returnError(e);
			
		}
		return this.returnSuccess();
		
	}
	
	
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
	 * @return the queryPDAOnlineUsingVo
	 */
	public QueryPDAOnlineUsingVo getQueryPDAOnlineUsingVo() {
		return queryPDAOnlineUsingVo;
	}

	/**
	 * @param queryPDAOnlineUsingVo the queryPDAOnlineUsingVo to set
	 */
	public void setQueryPDAOnlineUsingVo(QueryPDAOnlineUsingVo queryPDAOnlineUsingVo) {
		this.queryPDAOnlineUsingVo = queryPDAOnlineUsingVo;
	}

	/**
	 * @param queryPDAonLineService the queryPDAonLineService to set
	 */
	public void setQueryPDAonLineService(
			IQueryPDAonLineService queryPDAonLineService) {
		this.queryPDAonLineService = queryPDAonLineService;
	}

	/**
	 * @param tfrCtrAbsenteeInfoService the tfrCtrAbsenteeInfoService to set
	 */
	public void setTfrCtrAbsenteeInfoService(
			ITfrCtrAbsenteeInfoService tfrCtrAbsenteeInfoService) {
		this.tfrCtrAbsenteeInfoService = tfrCtrAbsenteeInfoService;
	}

	/**
	 * @return the excelStream
	 */
	public InputStream getExcelStream() {
		return excelStream;
	}

	/**
	 * @param excelStream the excelStream to set
	 */
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	/**
	 * @return the downloadFileName
	 */
	public String getDownloadFileName() {
		return downloadFileName;
	}

	/**
	 * @param downloadFileName the downloadFileName to set
	 */
	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
	
	
	
}
