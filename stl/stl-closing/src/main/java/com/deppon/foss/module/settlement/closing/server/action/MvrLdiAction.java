package com.deppon.foss.module.settlement.closing.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrLdiService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrLdiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrLdiDto;
import com.deppon.foss.module.settlement.closing.api.shared.vo.MvrLdiVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;

/**
 * 快递代理往来Action.
 * @author 045738-foss-maojianqiang
 * @date 2013-8-2 下午4:19:51
 */
public class MvrLdiAction extends AbstractAction {

	/** 序列号. */
	private static final long serialVersionUID = -625898101064703956L;

	/** 始发、空运Vo. */
	private MvrLdiVo vo;

	/** 注入始发、空运往来service. */
	private IMvrLdiService mvrLdiService;
	
	/** excel名称. */
	private String excelName;

	/** excel导出流. */
	private InputStream inputStream;
	
	/** The Constant logger. */
	private static final Logger logger = LogManager
			.getLogger(MvrLdiAction.class);
	
	/**
	 * 根据多个参数查询快递代理往来
	 *
	 * @return the string
	 * @author foss-pengzhen
	 * @date 2013-3-6 上午11:12:49
	 * @see
	 */
	@JSON
	public String queryMvrLdiByConditions(){
		try {
			MvrLdiDto mvrLdiDto = vo.getDto();
			//到达部门类型需要转化一下
			if(StringUtils.isNotBlank(mvrLdiDto.getOrgType()) && SettlementDictionaryConstants.VOUCHER_ORG_TYPE_DEST.equals(mvrLdiDto.getOrgType())){
				mvrLdiDto.setOrgType(SettlementDictionaryConstants.VOUCHER_ORG_TYPE_PKG);
			}
			//根据多个参数查
			MvrLdiDto dto = mvrLdiService.queryTotalCounts(mvrLdiDto);	
			dto.setPeriod("合计");
			if(dto!=null && dto.getCount() >0){
				// 查询列表
				List<MvrLdiEntity> queryList = mvrLdiService.queryMvrLdiByConditions(mvrLdiDto, this.getStart(), this.getLimit());

				// 附加统计信息
				MvrLdiEntity total = new MvrLdiEntity();
				BeanUtils.copyProperties(dto, total);
				queryList.add(total);
				// 设置查询结果列表
				dto.setQueryList(queryList);
				vo.setDto(dto);
				this.setTotalCount((long) dto.getCount());	
			}
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return ERROR;
		}
	}
	
	/**
	 * 快递代理往来月报表导出.
	 *
	 * @return the string
	 * @author foss-pengzhen
	 * @date 2013-3-12 上午9:13:29
	 * @see
	 */
	public String mvrLdiExport(){
		try {
			// 查询参数不能为空
			if (vo.getDto() == null) {
				return returnError("查询参数为空");
			}

			// 查询报表期间不能为空
			if (StringUtils.isEmpty(vo.getDto().getPeriod())) {
				return returnError("查询快递代理往来月报表期间为空");
			}
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置导出excel名称
			String exportXlsName = "始发快递代理往来月报表_" + vo.getDto().getPeriod();
			try {
				this.setExcelName(URLEncoder.encode(exportXlsName,
						SettlementConstants.UNICODE_UTF));
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
				return returnError("导出Excel失败");
			}
			//查询未确认收银的数据
			ExportResource exportResource = mvrLdiService.exportMvrLdis(vo.getDto(), currentInfo);
			//创建导出表头对象
		    ExportSetting exportSetting = new ExportSetting();
		    //设置名称
		    exportSetting.setSheetName(exportXlsName);
		    
		    //设置格式
		    Map<Integer, String> map = new HashMap<Integer, String>();
		    for(int i=SettlementReportNumber.SIX;i<=SettlementReportNumber.TWENTY_FOUR;i++){
		    	map.put(i, "float");
		    }
		    exportSetting.setDataType(map);
		    
		    //创建导出工具类
		    ExporterExecutor objExporterExecutor = new ExporterExecutor();
		    // 导出成文件
		    inputStream =  objExporterExecutor.exportSync(exportResource, exportSetting);

			return SUCCESS;
		}  catch (BusinessException e) {
			logger.error(e.getMessage(), e);
		    return returnError(e);
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
	 * @GET
	 * @return vo
	 */
	public MvrLdiVo getVo() {
		/*
		 *@get
		 *@ return vo
		 */
		return vo;
	}

	/**
	 * @SET
	 * @param vo
	 */
	public void setVo(MvrLdiVo vo) {
		/*
		 *@set
		 *@this.vo = vo
		 */
		this.vo = vo;
	}

	/**
	 * @SET
	 * @param mvrLdiService
	 */
	public void setMvrLdiService(IMvrLdiService mvrLdiService) {
		/*
		 *@set
		 *@this.mvrLdiService = mvrLdiService
		 */
		this.mvrLdiService = mvrLdiService;
	}
	
	
	
}
