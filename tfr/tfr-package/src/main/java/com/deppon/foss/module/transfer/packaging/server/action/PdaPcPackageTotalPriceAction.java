package com.deppon.foss.module.transfer.packaging.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.packaging.api.server.service.IPdaPcPackageTotalPriceService;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPdaPcPackConditionEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPdaPcPackResultEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.vo.QueryPackedPriceVo;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;

/** 
 * @author ZhangXu
 * @version 创建时间：2014-5-30 下午3:54:23 
 * 根据运单号 、包装部门、包装供应商、开单部门包装开始结束时间
 * 查询并汇总PDA与PC端包装金额
 */
public class PdaPcPackageTotalPriceAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(PdaPcPackageTotalPriceAction.class);
	//查询并汇总PDA与PC端包装金额 server
	private  IPdaPcPackageTotalPriceService pdaPcPackageTotalPriceService;
	
	/**
	 * vo对象
	 */
	private QueryPackedPriceVo queryPackedPriceVo=new QueryPackedPriceVo();	
	
	/**
	 * 导出Excel 文件流
	 */
	transient InputStream excelStream;
	/**
	 * 导出Excel 文件名
	 */
	private String fileName;
	
	/**
	 * 查询并汇总PDA与PC端包装金额
	* @author foss-189284-zx
	* @date 创建时间：2014-6-3 下午3:47:42 
	* @return String
	 */
	@JSON
	public String queryPdaPcTotalPrice(){
		try {
			QueryPdaPcPackConditionEntity queryPdaPcPackConditionEntity=queryPackedPriceVo.getQueryPdaPcPackConditionEntity();
			List<QueryPdaPcPackResultEntity> queryPdaPcPackResultList= pdaPcPackageTotalPriceService.queryPdaPcTotalPrice(queryPdaPcPackConditionEntity,this.getLimit(), this.getStart());
			//查询总条数	
			long totalCount=pdaPcPackageTotalPriceService.queryPdaPcTotalPriceCount(queryPdaPcPackConditionEntity);
			//设置总条数
			this.setTotalCount(totalCount);
			queryPackedPriceVo.setQueryPdaPcPackResultList(queryPdaPcPackResultList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
		
	}
	
	/**
	 * 查询并汇总PDA与PC端包装金额信息  导出
	* @author foss-189284-zx
	* @date 创建时间：2014-6-3 下午3:47:05 
	* @return String
	 */
	public String exportPdaPcExcel(){
		try {
			// 导出文件名
			fileName = this.encodeFileName("汇总PDA与PC端包装金额信息");
			// 导出文件流
			excelStream = pdaPcPackageTotalPriceService.exportExcelStream(queryPackedPriceVo.getQueryPdaPcPackConditionEntity());
		} catch (BusinessException e) {
			LOGGER.error(e.getErrorCode());
			return returnError(e);
		}catch (Exception e) {
			//LOGGER.error(ScheduleConstants.SCHEDULE_EXCEPTION, e);
			return super.returnError(e.toString(), "");
		}
		return returnSuccess();
	}
	
	
	
	
	/**
	 * 获取excelStream
	 * @return  excelStream
	 */
	public InputStream getExcelStream() {
		return excelStream;
	}

	/**
	 * 获取fileName
	 * @return  fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 获取queryPackedPriceVo
	 * @return  queryPackedPriceVo
	 */
	public QueryPackedPriceVo getQueryPackedPriceVo() {
		return queryPackedPriceVo;
	}


	/**
	 *设置queryPackedPriceVo
	 */
	public void setQueryPackedPriceVo(QueryPackedPriceVo queryPackedPriceVo) {
		this.queryPackedPriceVo = queryPackedPriceVo;
	}


	/**
	 *设置查询并汇总PDA与PC端包装金额 server pdaPcPackageTotalPriceService
	 */
	public void setPdaPcPackageTotalPriceService(
			IPdaPcPackageTotalPriceService pdaPcPackageTotalPriceService) {
		this.pdaPcPackageTotalPriceService = pdaPcPackageTotalPriceService;
	}

	
	/**
	 * 生成导出文件名称
	 * @author yuyongxiang
	 * @date 2013年10月11日 11:38:30
	 */
	public String encodeFileName(String fileName) throws BusinessException {
		try {
			String returnStr;
			String agent = (String) ServletActionContext.getRequest().getHeader("USER-AGENT");
			if (agent != null && agent.indexOf("MSIE") == -1) {
				returnStr = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			} else {
				returnStr = URLEncoder.encode(fileName, "UTF-8");
			}
			return returnStr;
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("转换文件名编码失败", e);
			throw new BusinessException(StockException.EXPORT_FILE_ERROR_CODE, "");
		}
	}
}
