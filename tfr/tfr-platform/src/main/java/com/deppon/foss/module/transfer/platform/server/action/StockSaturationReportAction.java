package com.deppon.foss.module.transfer.platform.server.action;


import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.platform.api.server.service.IStockSaturationReportService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.define.StockSaturationConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockSaturationReportEntity;
import com.deppon.foss.module.transfer.platform.api.shared.vo.StockSaturationReportVo;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;

/**
* @description 仓库饱和度报表Action
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年5月10日 上午9:10:32
*/
public class StockSaturationReportAction extends AbstractAction  {

	/**
	* @fields serialVersionUID
	* @author 14022-foss-songjie
	* @update 2014年5月10日 上午10:01:39
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;
	/**
	 * 仓库饱和度数据监控报表VO
	* @fields stockSaturationVo
	* @author 14022-foss-songjie
	* @update 2014年3月28日 下午3:47:43
	* @version V1.0
	*/
	private StockSaturationReportVo stockSaturationReportVo = new StockSaturationReportVo();
	
	
	/**
	 * 库存饱和度数据监控报表Service
	* @fields stockSaturationReportService
	* @author 14022-foss-songjie
	* @update 2014年5月13日 下午5:02:27
	* @version V1.0
	*/
	private IStockSaturationReportService stockSaturationReportService;
	
	
	/**
	 * 导出Excel 文件流
	* @fields excelStream
	* @author 14022-foss-songjie
	* @update 2014年5月14日 上午8:43:39
	* @version V1.0
	*/
	transient InputStream excelStream;
	
	/**
	 * 导出Excel 文件名
	* @fields fileName
	* @author 14022-foss-songjie
	* @update 2014年5月14日 上午8:43:39
	* @version V1.0
	*/
	private String fileName;
	
	private Logger LOGGER = LoggerFactory.getLogger(StockSaturationReportAction.class);
	/**
	* @description 库存饱和度数据监控报表Service
	* @param stockSaturationReportService
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月13日 下午5:02:49
	*/
	public void setStockSaturationReportService(
			IStockSaturationReportService stockSaturationReportService) {
		this.stockSaturationReportService = stockSaturationReportService;
	}


	/**
	* @description 仓库饱和度数据监控报表VO
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月13日 下午5:00:22
	*/
	public StockSaturationReportVo getStockSaturationReportVo() {
		return stockSaturationReportVo;
	}

	
	/**
	* @description 仓库饱和度数据监控报表VO
	* @param stockSaturationReportVo
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月13日 下午5:01:06
	*/
	public void setStockSaturationReportVo(
			StockSaturationReportVo stockSaturationReportVo) {
		this.stockSaturationReportVo = stockSaturationReportVo;
	}


	/**
	* @description 导出Excel 文件流
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月14日 上午8:44:44
	*/
	public InputStream getExcelStream() {
		return excelStream;
	}


	
	/**
	* @description 导出Excel 文件名
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月14日 上午8:44:58
	*/
	public String getFileName() {
		return fileName;
	}


	
	/**
	* @description 导出Excel 文件名
	* @param fileName
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月14日 上午8:45:02
	*/
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	/**
	* @description 页面加载
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月10日 上午9:20:46
	*/
	public String stockSaturationReportIndex(){
		return SUCCESS;
	}
	
	
	/**
	* @description 查询
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月14日 上午8:42:06
	*/
	@JSON
	public String queryStockSaturationReport(){
		StockSaturationReportEntity dto = stockSaturationReportVo.getStockSaturationReportDto();
		if(dto==null){
			dto = new StockSaturationReportEntity(); 
		}
		if(StringUtils.isEmpty(dto.getQueryDateB())){
			return returnError("请输入查询日期");
		}
		dto.setQueryDateA(beginTimeByEndtime(dto.getQueryDateB()));
		stockSaturationReportVo.setStockSaturationReportList(stockSaturationReportService.queryStockSaturationReport(dto, start, limit)); 
		this.setTotalCount(stockSaturationReportService.queryStockSaturationReportCount());
		return SUCCESS;
	}
	
	
	/**
	* @description 导出
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月14日 上午8:42:47
	*/
	public String exportStockSaturationReport(){
		try{
			StockSaturationReportEntity dto = stockSaturationReportVo.getStockSaturationReportDto();
			if(dto==null){
				dto = new StockSaturationReportEntity(); 
			}
			if(StringUtils.isEmpty(dto.getQueryDateB())){
				return returnError("请输入查询日期");
			}
			dto.setQueryDateA(beginTimeByEndtime(dto.getQueryDateB()));
				try {
					fileName = this.encodeFileName(StockSaturationConstants.STOCK_SATURATION_REPORT_SHEET_NAME);
				} catch (UnsupportedEncodingException e) {
					LOGGER.info("StockSaturationReportAction.exportStockSaturationReport 报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
				}
			excelStream = stockSaturationReportService.exportStockSaturationReport(dto);
		}catch(Exception e){
			return returnError(e.getMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 转换导出文件的文件名
	 * 
	 * @param name
	 * @return
	 * @throws UnsupportedEncodingException
	 * @description 转换导出文件的文件名
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2014年5月14日 上午8:46:47
	 */
	public String encodeFileName(String name)
			throws UnsupportedEncodingException {
		String returnStr;
		String agent = (String) ServletActionContext.getRequest().getHeader(
				"USER-AGENT");
		if (agent != null && agent.indexOf("MSIE") == -1) {
			returnStr = new String(name.getBytes("UTF-8"), "iso-8859-1");
		} else {
			returnStr = URLEncoder.encode(name, "UTF-8");
		}
		return returnStr;
	}
	
	/**
	* @description 根据截至时间获取开始时间
	* @param endTime
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月14日 上午8:46:47
	*/
	public String beginTimeByEndtime(String endTime){
		if(endTime!=null && endTime.length()>=PlatformConstants.SONAR_NUMBER_10){
			String endTimeSub = endTime.substring(PlatformConstants.SONAR_NUMBER_0,PlatformConstants.SONAR_NUMBER_8); 
			String beginTime = endTimeSub+"01";
			return beginTime;
		}else{
			return "";
		}
	}

}
