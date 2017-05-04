package com.deppon.foss.module.settlement.closing.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrPliService;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPliDto;
import com.deppon.foss.module.settlement.closing.api.shared.vo.MvrPliVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;

/**
 * 始发偏线往返action.
 *
 * @author foss-pengzhen
 * @date 2013-3-11 上午10:32:47
 * @since
 * @version
 */
public class MvrPliAction extends AbstractAction {
	
	/** 序列号. */
	private static final long serialVersionUID = -996676797754903522L;

	/** 始发偏线往返vo. */
	private MvrPliVo mvrPliVo;
	
	/** 注入始发偏线service. */
	private IMvrPliService mvrPliService;
	
	/** excel名称. */
	private String excelName;

	/** excel导出流. */
	private InputStream inputStream;
	
	/** The Constant logger. */
	private static final Logger logger = LogManager
			.getLogger(MvrAfiAction.class);
	
	/**
	 * 根据多个参数查询始发偏线信息.
	 *
	 * @return the string
	 * @author foss-pengzhen
	 * @date 2013-3-6 上午11:12:49
	 * @see
	 */
	@JSON
	public String querymvrPliByConditions(){
		try {
			MvrPliDto mvrPliDto = mvrPliVo.getMvrPliDto();
			//到达部门类型需要转化一下
			if(StringUtils.isNotBlank(mvrPliDto.getOrgType()) && SettlementDictionaryConstants.VOUCHER_ORG_TYPE_DEST.equals(mvrPliDto.getOrgType())){
				mvrPliDto.setOrgType(SettlementDictionaryConstants.VOUCHER_ORG_TYPE_PL);
			}

			//根据多个参数查询始发偏线信息
			MvrPliDto dto=mvrPliService.queryMvrPliByConditions(mvrPliDto, getStart(), getLimit());
			if(dto!=null){
				mvrPliVo.setMvrPliDto(dto);	
				this.setTotalCount(dto.getSum());
			}
			
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return ERROR;
		}
	}
	
	/**
	 * 始发偏线往来月报表导出.
	 *
	 * @return the string
	 * @author foss-pengzhen
	 * @date 2013-3-12 上午9:13:29
	 * @see
	 */
	public String mvrPliExport(){
		try {
			// 查询参数不能为空
			if (mvrPliVo.getMvrPliDto() == null) {
				return returnError("查询参数为空");
			}

			// 查询报表期间不能为空
			if (StringUtils.isEmpty(mvrPliVo.getMvrPliDto().getPeriod())) {
				return returnError("查询偏线月报表期间为空");
			}
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			
			// 设置导出excel名称
			String exportXlsName = "始发偏线往来月报表_" + mvrPliVo.getMvrPliDto().getPeriod();
			try {
//				this.setExcelName(URLEncoder.encode(exportXlsName,
//						SettlementConstants.UNICODE_UTF));
				// 转化编码
				this.setExcelName(new String((exportXlsName)
						.getBytes(SettlementConstants.UNICODE_GBK),
						SettlementConstants.UNICODE_ISO));					
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
				return returnError("导出Excel失败");
			}
			
			//查询未确认收银的数据
			ExportResource exportResource = mvrPliService.exportMvrPlis(mvrPliVo.getMvrPliDto(), currentInfo);
			//创建导出表头对象
		    ExportSetting exportSetting = new ExportSetting();
		    //设置名称
		    exportSetting.setSheetName(exportXlsName);
		    
		    //设置格式
		    Map<Integer, String> map = new HashMap<Integer, String>();
		    for(int i=SettlementReportNumber.SIX;i<=SettlementReportNumber.THIRTEEN;i++){
		    	map.put(i, "float");
		    }
		    exportSetting.setDataType(map);
		    
		    //创建导出工具类
		    ExporterExecutor objExporterExecutor = new ExporterExecutor();
		    // 导出成文件
		    inputStream =  objExporterExecutor.exportSync(exportResource, exportSetting);


			return SUCCESS;
		} catch (Exception e) {
			// 记录日志并返回失败
			logger.error(e);
			return returnError("导出始发偏线往来月报表异常:" + e.getMessage());
		}
	}
	
	
	/**
	 * Gets the excel name.
	 *
	 * @return  the excelName
	 */
	public String getExcelName() {
		return excelName;
	}
	
	/**
	 * Sets the excel name.
	 *
	 * @param excelName the excelName to set
	 */
	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}
	
	/**
	 * Gets the input stream.
	 *
	 * @return  the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}
	
	/**
	 * Sets the input stream.
	 *
	 * @param inputStream the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	/**
	 * Gets the mvr pli vo.
	 *
	 * @return  the mvrPliVo
	 */
	public MvrPliVo getMvrPliVo() {
		return mvrPliVo;
	}
	
	/**
	 * Sets the mvr pli vo.
	 *
	 * @param mvrPliVo the mvrPliVo to set
	 */
	public void setMvrPliVo(MvrPliVo mvrPliVo) {
		this.mvrPliVo = mvrPliVo;
	}
	
	/**
	 * Sets the mvr pli service.
	 *
	 * @param mvrPliService the mvrPliService to set
	 */
	public void setMvrPliService(IMvrPliService mvrPliService) {
		this.mvrPliService = mvrPliService;
	}
	
	
}
