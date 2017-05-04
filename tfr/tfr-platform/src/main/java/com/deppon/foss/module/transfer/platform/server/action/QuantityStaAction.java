/**   
* @Title: QuantityStaAction.java 
* @Package com.deppon.foss.module.transfer.platform.server.action 
* @Description: 货量统计action
* @author shiwei-045923-shiwei@outlook.com
* @date 2014年8月28日 下午2:57:34 
* @version V1.0   
*/
package com.deppon.foss.module.transfer.platform.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.platform.api.server.service.IQuantityStaService;
import com.deppon.foss.module.transfer.platform.api.shared.define.QuantityStaConstants;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaQcDto;
import com.deppon.foss.module.transfer.platform.api.shared.vo.QuantityStaVo;
import com.deppon.foss.util.define.FossConstants;

/** 
 * @ClassName: QuantityStaAction 
 * @Description: 货量统计（新）action
 * @author shiwei-045923-shiwei@outlook.com
 * @date 2014年8月28日 下午2:57:34 
 *  
 */
public class QuantityStaAction extends AbstractAction {

	/** 
	* @Fields serialVersionUID : serialVersionUID
	*/ 
	private static final long serialVersionUID = 7560014128161708501L;
	
	/**
	 * service
	 */
	private IQuantityStaService quantityStaService;
	
	/**
	 * Vo
	 */
	private QuantityStaVo quantityStaVo = new QuantityStaVo();
	
	/**
	 * 导出Excel 文件流
	 */
	private InputStream excelStream;

	/**
	 * 导出Excel 文件名
	 */
	private String downloadFileName;
	
	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	/** 
	 * @return quantityStaVo 
	 */
	public QuantityStaVo getQuantityStaVo() {
		return quantityStaVo;
	}

	/** 
	 * @param quantityStaVo 要设置的 quantityStaVo 
	 */
	public void setQuantityStaVo(QuantityStaVo quantityStaVo) {
		this.quantityStaVo = quantityStaVo;
	}

	/** 
	 * @param quantityStaService 要设置的 quantityStaService 
	 */
	public void setQuantityStaService(IQuantityStaService quantityStaService) {
		this.quantityStaService = quantityStaService;
	}

	/**
	* @Title: quantityStaIndex 
	* @Description: 加载页面
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年8月29日 上午10:41:48 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String quantityStaIndex(){
		String[] transferCenter = quantityStaService.queryTransferCenter();
		if(transferCenter == null){
			return ERROR;
		}
		quantityStaVo.setTransferCenterCode(transferCenter[0]);
		quantityStaVo.setTransferCenterName(transferCenter[1]);
		return SUCCESS;
	}
	
	/**
	* @Title: queryQuantity 
	* @Description: 查询货量统计信息
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年8月31日 下午3:02:56 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String queryQuantity(){
		//获取外场code和查询日期
		String transferCenterCode = quantityStaVo.getQuantityStaQcDto().getTransferCenterCode();
		String startAndEndTime = quantityStaService.queryStaStartAndEndTime(transferCenterCode);
		//返回货量统计开始、结束时间
		quantityStaVo.setStaStartTime(startAndEndTime);
		/**
		 * 返回查询结果集
		 */
		//获取查询条件
		QuantityStaQcDto quantityStaQcDto = quantityStaVo.getQuantityStaQcDto();
		List<QuantityStaDto> quantityStaDtoList = null;
		//分别调用出发货量、到达货量service
		if(StringUtils.equals(quantityStaQcDto.getType(), QuantityStaConstants.STA_TYPE_DEPARTURE)){
			quantityStaDtoList = quantityStaService.queryDepart(quantityStaQcDto);
		}else{
			quantityStaDtoList = quantityStaService.queryArrive(quantityStaQcDto);
		}
		quantityStaVo.setQuantityStaDtoList(quantityStaDtoList);
		return SUCCESS;
	}
	
	/**
	* @Title: exportQuantityExcel 
	* @Description: 导出货量信息
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年9月2日 上午11:15:05 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@JSON
	public String exportQuantity(){
		try {
			downloadFileName = encodeFileName("货量信息.xls");

			ExportResource exportResource = quantityStaService
					.expertQuantity(quantityStaVo.getQuantityStaQcDto());
			ExportSetting exportSetting = new ExportSetting();
			// 设置名称
			exportSetting.setSheetName("货量信息");
			ExporterExecutor objExporterExecutor = new ExporterExecutor();
			// 导出成文件
			excelStream = objExporterExecutor.exportSync(exportResource,
					exportSetting);

			return SUCCESS;
		} catch (BusinessException e) {
			if(e.getMessage().equals(FossConstants.NO)){
				quantityStaVo.setExportError("没有符合条件的结果！");
			}
			return returnError(e);
		} catch (UnsupportedEncodingException e) {
			return returnError("UnsupportedEncodingException", "");
		}
	}
	
	/**
	* @Title: encodeFileName 
	* @Description: 处理文件名乱码问题
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年9月4日 下午3:52:39 
	* @param @param name
	* @param @return
	* @param @throws UnsupportedEncodingException    设定文件 
	* @return String    返回类型 
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

}
