package com.deppon.foss.module.settlement.closing.server.action;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrPlrQueryService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPlrEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPlrDto;
import com.deppon.foss.module.settlement.closing.api.shared.vo.MvrPlrVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 偏线月报Action.
 *
 * @author 095793-foss-LiQin
 * @date 2013-3-12 下午5:24:44
 */
public class MvrPlrAction extends AbstractAction {

	/** 偏线月报序列化. */
	private static final long serialVersionUID = -6617569005207702466L;

	/** 日志. */
	private static final Logger LOGGER = LogManager.getLogger(MvrAfrAction.class);
	
	/** 报表合计用于显示在前端. */
	private static final String TOTALNAME="合计";

	/** 偏线月报Vo. */
	private MvrPlrVo vo;
	
	/** 声明service. */
	private IMvrPlrQueryService mvrPlrQueryService;
	
	/**
	 * 导出的文件名
	 */
	private  String excelName;
	
	/** excel导出流. */
	private InputStream inputStream;

	/**
	 * 查询偏线月报.
	 *
	 * @return the string
	 * @author 095793-foss-LiQin
	 * @date 2013-3-12 下午5:24:41
	 */
	@JSON
	public String queryMvrPlrReport() {

		try{
			LOGGER.info(" MvrPlrAction into ..........");
			/***
			 * 校验查询月报，输入条件
			 */
			cheackParm(vo);
			/**
			 * 获取界面输入dto
			 */
			MvrPlrDto dto = vo.getMvrplrDto();
			
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置用户数据查询权限
			dto.setUserCode(currentInfo.getEmpCode());
			
			/***
			 * 当条数大于零的时候，执行查询列表
			 */
			MvrPlrDto resultDto=mvrPlrQueryService.queryMvrPlrByParamTotal(dto);
			List<MvrPlrEntity> mvrPlrList=null;
			if(resultDto.getCount()>0){
				/***
				 * 执行查询列表语句
				 */
				mvrPlrList= mvrPlrQueryService.queryMvrPlrByParam(dto,this.getStart(), this.getLimit());
				
				LOGGER.info(" MvrPlrAction query return List size:" + mvrPlrList.size());
				
				/**
				 *  附加统计总数信息到查询列表
				 */
				MvrPlrEntity totalPlrDto = new MvrPlrEntity();
				// 拷贝dto
				BeanUtils.copyProperties(resultDto, totalPlrDto);
				// 设置统计字段
				totalPlrDto.setPeriod(TOTALNAME);
				// 添加统计dto
				mvrPlrList.add(totalPlrDto);
				LOGGER.info(" MvrPlrAction return List size:" + mvrPlrList.size());
			}
			
			/**
			 * 偏线月报，将结果返回到前端
			 */
			vo.setQueryList(mvrPlrList);
			// 设置总条数
			this.setTotalCount((long) resultDto.getCount());
			
			LOGGER.info(" MvrPlrAction exit ..........");
			return returnSuccess();
		}catch(BusinessException e){
			return returnError(e);
		}
	}

	/**
	 * 检查偏线月发报表输入查询条件.
	 *
	 * @param vo the vo
	 * @author 095793-foss-LiQin
	 * @date 2013-3-12 下午6:31:48
	 */
	void cheackParm(MvrPlrVo vo) {
		LOGGER.debug(" MvrPlrAction cheackParm into ..........");
		if (null == vo.getMvrplrDto()) {
			// 查询偏线月报的实体为空
			throw new SettlementException("查询偏线月报的实体为空!");
		}

		if (StringUtils.isEmpty(vo.getMvrplrDto().getPeriod())) {
			// 查询报表期间为空
			throw new SettlementException("查询报表期间为空!");
		}
		LOGGER.debug(" MvrPlrAction cheackParm exit ..........");
	}

	
	
	
	
	/**
	 * 导出偏线月报表
	 * @author 095793-foss-liqin
	 * @date 2013-3-22 下午5:08:26
	 * @return
	 */
	public String exportMvrPlrReport(){
		try {
			// 查询参数不能为空
			if (vo.getMvrplrDto() == null) {
				return returnError("查询参数为空");
			}

			// 查询报表期间不能为空
			if (StringUtils.isEmpty(vo.getMvrplrDto().getPeriod())) {
				return returnError("查询偏线月报表期间为空");
			}
			
			//获取用户、部门信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 设置导出excel名称
			String exportXlsName = "偏线月报表_" + vo.getMvrplrDto().getPeriod();
			try {
				//this.setExcelName(URLEncoder.encode(exportXlsName,SettlementConstants.UNICODE_UTF));
				
				this.setExcelName(new String((exportXlsName)
						.getBytes(SettlementConstants.UNICODE_GBK),
						SettlementConstants.UNICODE_ISO));	
			} catch (UnsupportedEncodingException e) {
				LOGGER.error(e.getMessage(), e);
				return returnError("导出Excel失败");
			}
			//查询未确认收银的数据
			ExportResource exportResource = mvrPlrQueryService.exportMvrPlr(vo.getMvrplrDto(), currentInfo);
			//创建导出表头对象
		    ExportSetting exportSetting = new ExportSetting();
		    //设置名称
		    exportSetting.setSheetName(exportXlsName);
		    Map<Integer, String> map = new HashMap<Integer, String>(); 
		    map.put(SettlementReportNumber.NINE, "float"); 
		    map.put(SettlementReportNumber.TEN, "float"); 
		    map.put(SettlementReportNumber.ELEVEN, "float"); 
		    map.put(SettlementReportNumber.TWELVE, "float"); 
		    map.put(SettlementReportNumber.THIRTEEN, "float"); 
		    map.put(SettlementReportNumber.FOURTEEN, "float"); 
		    map.put(SettlementReportNumber.FIFTEEN, "float"); 
		    map.put(SettlementReportNumber.SIXTEEN, "float"); 
		    map.put(SettlementReportNumber.SEVENTEEN, "float"); 
		    map.put(SettlementReportNumber.EIGHTEEN, "float"); 
		    map.put(SettlementReportNumber.NINETEEN, "float"); 
		    map.put(SettlementReportNumber.TWENTY, "float"); 
		    map.put(SettlementReportNumber.TWENTY_ONE, "float"); 
		    map.put(SettlementReportNumber.TWENTY_TWO, "float"); 
		    map.put(SettlementReportNumber.TWENTY_THREE, "float"); 
		    exportSetting.setDataType(map);
		  
		    //创建导出工具类
		    ExporterExecutor objExporterExecutor = new ExporterExecutor();
		    // 导出成文件
		    inputStream =  objExporterExecutor.exportSync(exportResource, exportSetting);
			return SUCCESS;
		}  catch (BusinessException e) {
			LOGGER.error("导出偏线月报表："+e.getMessage(), e);
		    return returnError(e);
		}
	}
	
	
	
	/**
	 * service 注入.
	 *
	 * @param mvrPlrQueryService the new mvr plr query service
	 * @author 095793-foss-LiQin
	 * @date 2013-3-13 上午10:37:25
	 */
	public void setMvrPlrQueryService(IMvrPlrQueryService mvrPlrQueryService) {
		this.mvrPlrQueryService = mvrPlrQueryService;
	}

	/**
	 * Gets the vo.
	 *
	 * @return vo
	 */
	public MvrPlrVo getVo() {
		return vo;
	}

	/**
	 * Sets the vo.
	 *
	 * @param vo the new vo
	 */
	public void setVo(MvrPlrVo vo) {
		this.vo = vo;
	}

	/**
	 * @GET
	 * @return excelName
	 */
	public String getExcelName() {
		/*
		 *@get
		 *@ return excelName
		 */
		return excelName;
	}

	/**
	 * @SET
	 * @param excelName
	 */
	public void setExcelName(String excelName) {
		/*
		 *@set
		 *@this.excelName = excelName
		 */
		this.excelName = excelName;
	}

	/**
	 * @GET
	 * @return inputStream
	 */
	public InputStream getInputStream() {
		/*
		 *@get
		 *@ return inputStream
		 */
		return inputStream;
	}

	/**
	 * @SET
	 * @param inputStream
	 */
	public void setInputStream(InputStream inputStream) {
		/*
		 *@set
		 *@this.inputStream = inputStream
		 */
		this.inputStream = inputStream;
	}
}
