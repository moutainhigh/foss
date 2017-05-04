package com.deppon.foss.module.settlement.pay.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.util.ReflectionUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.pay.api.server.service.IRentCarReportDetailService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.RentCarReportDetailDto;
import com.deppon.foss.module.settlement.pay.api.shared.vo.RentCarReportDetailVo;
import com.deppon.foss.util.DateUtils;


public class RentCarReportDetailAction extends AbstractAction {

	/**
	 * 注入组织服务
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	/**
	 * Logger 查询临时租车付款报表明细界面数据logger
	 */
	private static final Logger logger = LogManager
			.getLogger(RentCarReportDetailAction.class);

	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 注入service
	 */
	private IRentCarReportDetailService rentCarReportDetailService;
    
	/**
	 * vo
	 */
	private RentCarReportDetailVo vo;
    /**
	 * 导出excel的文件名称
	 */
	private  String fileName;

	/**
	 * 导出excel的输入流
	 */
	private InputStream stream;

    /**
     * 根据条件查询临时租车界面
     * @return
     */
	@JSON
	public String queryRentCarReportDetail(){
		try{
			//声明目标部门集合
			 List<String> targetLsit = new ArrayList<String>();
			//如果部门存在，则获取当前部门
			 if (StringUtils.isNotBlank(vo.getRentCarReportDetailDto().getRentCarDeptCode())){
				   targetLsit.add(vo.getRentCarReportDetailDto().getRentCarDeptCode());
			//如果部门不存在，小区存在，则获取小区地下所有部门与该用户所管辖部门交集	
			} else if(StringUtils.isNotBlank(vo.getRentCarReportDetailDto().getSmallArea())){
				//调用综合方法查询
				List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(vo.getRentCarReportDetailDto().getSmallArea());
				//循环部门，获取用户所管辖部门与查询到部门的交集
				for(OrgAdministrativeInfoEntity en: orgList){
					targetLsit.add(en.getCode());
				}
			//如果部门、小区都不存在，而大区存在，	则获取大区底下所有部门与该用户所管辖部门交集	
			}else if(StringUtils.isNotBlank(vo.getRentCarReportDetailDto().getBigArea())){
				//调用综合方法查询
				List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(vo.getRentCarReportDetailDto().getBigArea());
				//循环部门，获取用户所管辖部门与查询到部门的交集
				for(OrgAdministrativeInfoEntity en: orgList){
					targetLsit.add(en.getCode());
				}
			//如果部门、小区、大区都不存在，而事业部存在，则获取默认用户所管辖部门集合	
			}else if(StringUtils.isNotBlank(vo.getRentCarReportDetailDto().getDivision())){
				//调用综合方法查询
				List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(vo.getRentCarReportDetailDto().getDivision());
				//循环部门，获取用户所管辖部门与查询到部门的交集
				for(OrgAdministrativeInfoEntity en: orgList){
					targetLsit.add(en.getCode());
				}
			//如果都不存在，则获取默认用户所管辖部门集合	
			}
		    // 设置可查询部门结果集
			 vo.getRentCarReportDetailDto().setRentCarDeptCodeList(targetLsit);
			 List<RentCarReportDetailDto> list = rentCarReportDetailService.queryRentCarReportDetailDto(vo.getRentCarReportDetailDto(), start, limit);
			 vo.getRentCarReportDetailDto().setTotalCount(rentCarReportDetailService.queryRentCarReportCount(vo.getRentCarReportDetailDto()));
			 vo.setRentCarReportDetailDtoList(list);   
			 // 设置查询出数据的总条数
			 this.setTotalCount((long) vo.getRentCarReportDetailDto().getTotalCount());
			 // 将查询出的单据类型强制转换为前台的显示
			 if(list.size()>0){
				 for(int i=0; i<list.size();i++){
					 if("handoverbill".equalsIgnoreCase(list.get(i).getBillType())){
						 list.get(i).setBillType("交接单");
				     }else if("waybill".equalsIgnoreCase(list.get(i).getBillType())){
						 list.get(i).setBillType("运单");
					 }else if("deliverbill".equalsIgnoreCase(list.get(i).getBillType())){
						 list.get(i).setBillType("派送单");
					 }else if("stowagebill".equalsIgnoreCase(list.get(i).getBillType())){
						 list.get(i).setBillType("配载单");
					 }
				 }
			 }
			 return returnSuccess();
		}catch(BusinessException e){
			return returnError(e);
		}
	}
	/**
	 * 将查询出的数据用excel导出
	 * @String
	 */
    public String exportQueryRentCarReportDetail() {
    	
		try {
			// 查询参数不能为空
			if (vo == null) {
				return returnError("导出参数不能为空!");
			}
			// 设置导出excel名称
			try {
				this.setFileName(new String(("临时租车付款报表明细界面数据的导出").getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
				return returnError("导出Excel失败");
			}
			// 获取类头名称
			String[] header = vo.getArrayColumns();
			// 获取列头中文名
			String[] headerName = vo.getArrayColumnNames();
            //查询出的数据
			List<RentCarReportDetailDto> list = rentCarReportDetailService.exportQueryRentCarReportDetailDto(vo.getRentCarReportDetailDto());
			// 导出格式数据
			ExportResource exportResource = packExportResourceXLS(list,header,headerName);
			// 创建导出表头对象
			ExportSetting exportSetting = new ExportSetting();
			// 设置名称
			exportSetting.setSheetName(SettlementConstants.EXCEL_SHEET_NAME);
			// 创建导出工具类
			ExporterExecutor executor = new ExporterExecutor();
			// 输出到导出流中
			stream = executor.exportSync(exportResource, exportSetting);

		} catch (BusinessException e) {
			// 记录日志并返回失败
			logger.error(e.getMessage(),e);
			return returnError("导出对账单异常:" + e.getMessage());

		}

		return returnSuccess();
	}
     
	private ExportResource packExportResourceXLS(
			List<RentCarReportDetailDto> list, String[] header,
			String[] headerName) {
		// 导出excel数据
		ExportResource data = new ExportResource();
		// 声明sheetList
		List<List<String>> sheetList = new ArrayList<List<String>>();
		//声明字典集合
		List<String> termCodes = new ArrayList<String>();
		termCodes.add(DictionaryConstants.RENTCAR_USE_TYPE);
		
		//后台获取数据字典对应的数据
		Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(termCodes);
		// 循环进行封装
		for (RentCarReportDetailDto rentCarReportDetailDto : list) {
			// 声明一行的rowList
			List<String> rowList = new ArrayList<String>();
			for (String columnName : header) {
				// 通过名称获取field
				Field field = ReflectionUtils.findField(RentCarReportDetailDto.class, columnName);
				if (field != null) {
					// 通过传入字段获取值
					ReflectionUtils.makeAccessible(field);
					Object fieldValue = ReflectionUtils.getField(field, rentCarReportDetailDto);
					// 如果为日期，需要进行格式化
					if (Date.class.equals(field.getType())&& fieldValue != null) {
						//日期转化
						fieldValue = DateUtils.convert((Date) fieldValue,DateUtils.DATE_TIME_FORMAT);
					}else if(columnName.equals("billType")){
						 if("handoverbill".equals(fieldValue)){
							 fieldValue = "交接单";
					     }else if("waybill".equals(fieldValue)){
							 fieldValue = "运单";
						 }else if("deliverbill".equals(fieldValue)){
							 fieldValue ="派送单";
						 }else if("stowagebill".equals(fieldValue)){
							 fieldValue ="配载单";
						 }
					}else if(columnName.equals("rentCarUseType") && fieldValue != null){
						fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
								DictionaryConstants.RENTCAR_USE_TYPE,
								fieldValue.toString());
					}
					if (fieldValue != null) {
						rowList.add(fieldValue.toString());
					} else {
						rowList.add(null);
					}
				}
			}
			sheetList.add(rowList);
		}
		//封装数据
		data.setRowList(sheetList);
		data.setHeads(headerName);
		return data;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setRentCarReportDetailService(
			IRentCarReportDetailService rentCarReportDetailService) {
		this.rentCarReportDetailService = rentCarReportDetailService;
	}

	public RentCarReportDetailVo getVo() {
		return vo;
	}

	public void setVo(RentCarReportDetailVo vo) {
		this.vo = vo;
	}


	public InputStream getStream() {
		return stream;
	}


	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}


}
