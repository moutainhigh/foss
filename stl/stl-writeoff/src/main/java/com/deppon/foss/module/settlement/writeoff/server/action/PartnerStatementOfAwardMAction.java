package com.deppon.foss.module.settlement.writeoff.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IPartnerStatementOfAwardDao;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IPartnerStatementOfAwardMService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerStatementOfAwardDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerStatementOfAwardEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerStatementOfAwardDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.vo.PartnerStatementOfAwardVo;
import com.deppon.foss.util.DateUtils;

/** 
 * 对账单管理
 * @author foss结算-306579-guoxinru 
 * @date 2016-2-17 上午11:21:41    
 */
public class PartnerStatementOfAwardMAction extends AbstractAction{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 声明日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(PartnerStatementOfAwardMAction.class);

	/**
	 * 合伙人奖罚对账单Vo 
	 */
	private PartnerStatementOfAwardVo partnerStatementOfAwardVo;
	
	/**
	 * 注入奖罚对账单管理Service
	 */
	private IPartnerStatementOfAwardMService partnerStatementOfAwardMService;
	
	/**
	 * 注入奖罚对账单新增DAO
	 */
	private IPartnerStatementOfAwardDao partnerStatementOfAwardDao;
	
    /**
     * 对账单导出文件名字
     */
    private static final String EXCELNAME = "合伙人奖罚对账单";

    private static final  String EXCELNAMED="合伙人奖罚对账单明细";
    /**
	 * excel名字
	 */
    private String excelName;
    /**
     * 输出流
     */
    private InputStream inputStream;
	
	/**
	 * 查询对账单
	 * 
	 * @author foss结算-306579-guoxinru
	 * @date 2012-10-20 下午3:03:38
	 */
	@JSON
	public String queryPAwardStatement(){
		if(null == partnerStatementOfAwardVo){
			return returnError("参数不能为空");
		}
		if(null == partnerStatementOfAwardVo.getPartnerStatementOfAwardDto()){
			return returnError("参数不能为空");
		}
		
		//获取前台参数
		PartnerStatementOfAwardDto partnerStatementOfAwardDto = partnerStatementOfAwardVo.getPartnerStatementOfAwardDto();
		
		try {
			//判断查询页签，如果为按客户查询，给第结束日期加1天
			if(SettlementConstants.TAB_QUERY_BY_DATE.equals(partnerStatementOfAwardDto.getQueryTabType())){
				//如果结束日期不为空，则需要对其进行+1操作
				if(partnerStatementOfAwardDto.getPeriodEndDate()!=null){
					//结束日期加1天
					partnerStatementOfAwardDto.setPeriodEndDate(DateUtils.convert(DateUtils.addDay(partnerStatementOfAwardDto.getPeriodEndDate(), 1)));
				}
			}
			
			// 获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			
			partnerStatementOfAwardDto.setEmpCode(currentInfo.getEmpCode());
			
			//调用servie查询对账单
			PartnerStatementOfAwardDto dto=this.partnerStatementOfAwardMService.queryPAwardStatement(partnerStatementOfAwardDto,this.getStart(),this.getLimit());
			
			//如果partnerStatementOfAwardDto查询为空，则new一个dto，防止前台报错
			if(null==dto){
				dto = new PartnerStatementOfAwardDto();
			}
			
			//封装VO并返回
			this.partnerStatementOfAwardVo.setPartnerStatementOfAwardDto(dto);
			this.setTotalCount(dto.getCount());
			
			//正常返回
			return returnSuccess();
			
			//异常处理
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(),e);
			//返回异常
			return returnError(e);
		}
	}
	
	/**
	 * 根据对账单号查询对账单明细
	 * 
	 * @author foss结算-306579-guoxinru
	 * @date 2016-02-22 下午3:03:38
	 */
	@JSON
	public String queryPAwardStatementDetail(){
		if(null == partnerStatementOfAwardVo){
			return returnError("参数不能为空");
		}
		if(null == partnerStatementOfAwardVo.getPartnerStatementOfAwardDto()){
			return returnError("参数不能为空");
		}
		if(null==partnerStatementOfAwardVo.getPartnerStatementOfAwardDto().getStatementBillNo()){
			return returnError("参数不能为空");
		}
		
		try {
			//获取封装参数dto
			PartnerStatementOfAwardDto queryDto = this.partnerStatementOfAwardVo.getPartnerStatementOfAwardDto();
			
			//根据对账单号调用service查询对账单明细
			PartnerStatementOfAwardDto dto = this.partnerStatementOfAwardMService.queryPAwardStatementDetail(queryDto);
			
			//如果partnerStatementOfAwardDto查询为空，则new一个dto，防止前台报错
			if(null==dto){
				dto = new PartnerStatementOfAwardDto();
			}
			
			//封装VO并返回
			this.partnerStatementOfAwardVo.setPartnerStatementOfAwardDto(dto);
			
			//正常返回
			return returnSuccess();
			
			//异常处理
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(),e);
			//返回异常
			return returnError(e);
		}
	}
	
	/**
	 * 对账单确认/反确认
	 * @author foss结算-306579-guoxinru
	 * @date 2016-02-26 
	 */
	@JSON
	public String updateStatusByStatementNo(){
		if(null == partnerStatementOfAwardVo){
			return returnError("参数不能为空");
		}
		if(null == partnerStatementOfAwardVo.getPartnerStatementOfAwardDto()){
			return returnError("参数不能为空");
		}
		if(null==partnerStatementOfAwardVo.getPartnerStatementOfAwardDto().getConfirmStatus()){
			return returnError("参数不能为空");
		}
		if(null==partnerStatementOfAwardVo.getPartnerStatementOfAwardDto().getStatementBillNo()){
			return returnError("参数不能为空");
		}
		try {
			// 获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			
			//获取封装参数dto
			PartnerStatementOfAwardDto queryDto = this.partnerStatementOfAwardVo.getPartnerStatementOfAwardDto();
			
			//调用service更改对账单状态
			this.partnerStatementOfAwardMService.updateStatusByStatementNo(queryDto,currentInfo);

			//正常返回
			return returnSuccess();
			//异常处理
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(),e);
			//返回异常
			return returnError(e);
		}
		
	}

	/**
	 * 合伙人奖罚对账单删除明细
	 * @author guoxinru-306579
	 * @date 2016-03-04
	 */
	@JSON
	public String delPAwardStatementD(){
		//非空空判断
		if(null == partnerStatementOfAwardVo){
			return returnError("参数不能为空");
		}
		if(null == partnerStatementOfAwardVo.getPartnerStatementOfAwardDto()){
			return returnError("参数不能为空");
		}
		if(null==partnerStatementOfAwardVo.getPartnerStatementOfAwardDto().getStatementBillNo()){
			return returnError("参数对账单号不能为空");
		}
		if(null==partnerStatementOfAwardVo.getPartnerStatementOfAwardDto().getNumbers()){
			return returnError("参数单号不能为空");
		}
		//获取前台参数
		PartnerStatementOfAwardDto queryDto = this.partnerStatementOfAwardVo.getPartnerStatementOfAwardDto();
		try {
			//调用删除明细接口
			PartnerStatementOfAwardDto dto = this.partnerStatementOfAwardMService.delPAwardStatementD(queryDto);
			//返回金额、对账单单据类型等数据
			partnerStatementOfAwardVo.setPartnerStatementOfAwardDto(dto);
			
			return returnSuccess();
			
		} catch (BusinessException e) {
			
			logger.error(e.getMessage(), e);
			
			return returnError(e);
		}
	}
	
	/**
	 * 合伙人奖罚对账单明细添加
	 * @author guoxinru-306579
	 * @date 2016-03-10
	 */
	@JSON
	public String addPAwardStatementD(){
		//非空空判断
		if(null == partnerStatementOfAwardVo){
			return returnError("参数不能为空");
		}
		if(null == partnerStatementOfAwardVo.getPartnerStatementOfAwardDto()){
			return returnError("参数不能为空");
		}
		if(null == partnerStatementOfAwardVo.getPartnerStatementOfAwardDto().getStatementBillNo()){
			return returnError("参数对账单号不能为空");
		}
		if(null == partnerStatementOfAwardVo.getPartnerStatementOfAwardDto().getSourceBillNo()){
			return returnError("参数应收应付单号不能为空");
		}
		//获取前台参数
		PartnerStatementOfAwardDto queryDto = this.partnerStatementOfAwardVo.getPartnerStatementOfAwardDto();
		try {
			//调用添加明细接口
			PartnerStatementOfAwardDto dto = this.partnerStatementOfAwardMService.addPAwardStatementD(queryDto);
			//返回金额、对账单单据类型等数据
			partnerStatementOfAwardVo.setPartnerStatementOfAwardDto(dto);
			
			return returnSuccess();
			
		} catch (BusinessException e) {
			
			logger.error(e.getMessage(), e);
			
			return returnError(e);
		}
	}
	
	/**
	 * 合伙人奖罚对账单导出
	 * @author guoxinru-306579
	 * @date 2016-03-10
	 */
	@JSON
	public String pAwardStatementToExcel(){
		if(null==partnerStatementOfAwardVo){
            return returnError("导出参数不能为空!");
        }
        if(null==partnerStatementOfAwardVo.getPartnerStatementOfAwardDto()){
            return returnError("导出参数不能为空!");
        }
        if(null==partnerStatementOfAwardVo.getPartnerStatementOfAwardDto().getStatementBillNos()){
            return returnError("导出参数不能为空!");
        }
        // 获取类头名称
        String[] header = partnerStatementOfAwardVo.getPartnerStatementOfAwardDto().getArrayColumns();
        // 获取列头中文名
        String[] headerName = partnerStatementOfAwardVo.getPartnerStatementOfAwardDto().getArrayColumnNames();
        try {
            try {
                // 设置导出excel名称
                this.setExcelName(new String((EXCELNAME).getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(), e);
                return returnError("导出Excel失败");
            }
            //查询对账单信息
            List<PartnerStatementOfAwardEntity> pAwardList;
            try{
            	pAwardList = this.partnerStatementOfAwardMService.queryPStatementsByStaNoList(partnerStatementOfAwardVo.getPartnerStatementOfAwardDto().getStatementBillNos());
            }catch (SettlementException e){
                logger.error(e.getMessage(), e);
                return returnError(e.getMessage());
            }
            // 导出格式数据
            ExportResource exportResource = packExportResourceXLS(pAwardList,header,headerName);
            // 创建导出表头对象
            ExportSetting exportSetting = new ExportSetting();
            // 设置名称
            exportSetting.setSheetName(SettlementConstants.EXCEL_SHEET_NAME);
            // 创建导出工具类
            ExporterExecutor executor = new ExporterExecutor();
            // 输出到导出流中
            inputStream = executor.exportSync(exportResource, exportSetting);
        } catch (SettlementException e) {
            // 记录日志并返回失败
            logger.error(e.getMessage(),e);
            return returnError("导出对账单异常:" + e.getMessage());
        }
        return returnSuccess();
	}
	
	/**
	 * 合伙人奖罚对账单明细导出
	 * @author guoxinru-306579
	 * @date 2016-03-10
	 */
	@JSON
	public String pAwardStatementDetailToExcel(){
		 if(null==partnerStatementOfAwardVo){
	            return returnError("导出参数不能为空!");
	        }
	        if(null==partnerStatementOfAwardVo.getPartnerStatementOfAwardDto()){
	            return returnError("导出参数不能为空!");
	        }
	        if(null==partnerStatementOfAwardVo.getPartnerStatementOfAwardDto().getStatementBillNos()){
	            return returnError("导出参数不能为空!");
	        }
	        // 获取类头名称
	        String[] header = partnerStatementOfAwardVo.getPartnerStatementOfAwardDto().getArrayColumns();
	        // 获取列头中文名
	        String[] headerName = partnerStatementOfAwardVo.getPartnerStatementOfAwardDto().getArrayColumnNames();
	        try {
	            try {
	                // 设置导出excel名称
	                this.setExcelName(new String((EXCELNAMED).getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
	            } catch (UnsupportedEncodingException e) {
	                logger.error(e.getMessage(), e);
	                return returnError("导出Excel失败");
	            }
	            //查询对账单信息
	            List<PartnerStatementOfAwardDEntity> pAwardDetailList;
	            PartnerStatementOfAwardDto dto  = partnerStatementOfAwardVo.getPartnerStatementOfAwardDto();
	            dto.setStatementBillNo(partnerStatementOfAwardVo.getPartnerStatementOfAwardDto().getStatementBillNos().get(0).toString());
	            try{
	            	pAwardDetailList = partnerStatementOfAwardDao.querypartnerDByStatementBillNo(dto);
	            }catch (SettlementException e){
	                logger.error(e.getMessage(), e);
	                return returnError(e.getMessage());
	            }
	            // 导出格式数据
	            ExportResource exportResource = packExportResourceDetailXLS(pAwardDetailList,header,headerName);
	            // 创建导出表头对象
	            ExportSetting exportSetting = new ExportSetting();
	            // 设置名称
	            exportSetting.setSheetName(SettlementConstants.EXCEL_SHEET_NAME);
	            // 创建导出工具类
	            ExporterExecutor executor = new ExporterExecutor();
	            // 输出到导出流中
	            inputStream = executor.exportSync(exportResource, exportSetting);
	        } catch (SettlementException e) {
	            // 记录日志并返回失败
	            logger.error(e.getMessage(),e);
	            return returnError("导出对账单异常:" + e.getMessage());
	        }
	        return returnSuccess();
	}
	//导出对账单的方法
    private ExportResource packExportResourceXLS(List<PartnerStatementOfAwardEntity> list, String[] header, String[] headerName){
        // 导出excel数据
        ExportResource data = new ExportResource();
        // 声明sheetList
        List<List<String>> sheetList = new ArrayList<List<String>>();
        // 循环进行封装
        for (PartnerStatementOfAwardEntity entity : list) {
            // 声明一行的rowList
            List<String> rowList = new ArrayList<String>();
            for (String columnName : header) {
                // 通过名称获取field
                Field field = ReflectionUtils.findField(PartnerStatementOfAwardEntity.class, columnName);
                if (field != null) {
                    // 通过传入字段获取值
                    ReflectionUtils.makeAccessible(field);
                    Object fieldValue = ReflectionUtils.getField(field, entity);
                    validaRowList(entity, rowList, columnName, field,
							fieldValue);
                }
            }
            sheetList.add(rowList);
        }
        //封装数据
        data.setRowList(sheetList);
        data.setHeads(headerName);
        return data;
    }

	private void validaRowList(PartnerStatementOfAwardEntity entity,
			List<String> rowList, String columnName, Field field,
			Object fieldValue) {
		if (fieldValue != null) {
			 // 如果为日期，需要进行格式化
		    fieldValue = validaStatement(entity, columnName, field,
					fieldValue);
		    if(columnName.equals("billType")){
		        //提货方式
		        if(StringUtils.isNotEmpty(fieldValue.toString())){
		            if("PBPA".equals(entity.getBillType())){
		                fieldValue = "合伙人奖罚对账单";
		            }else{
		                fieldValue = entity.getBillType();
		            }
		        }
		    }
		    rowList.add(fieldValue.toString());
		} else {
		    rowList.add(null);
		}
	}

	private Object validaStatement(PartnerStatementOfAwardEntity entity,
			String columnName, Field field, Object fieldValue) {
		if (Date.class.equals(field.getType())) {
		    //日期转化
		    fieldValue = DateUtils.convert((Date) fieldValue,DateUtils.DATE_TIME_FORMAT);
		}
		if(columnName.equals("confirmStatus")){
		    //如果数据产品类型编码不为空
		    if(StringUtils.isNotEmpty(fieldValue.toString())){
		        if("Y".equals(entity.getConfirmStatus())){
		            fieldValue = "已确认";
		        }else if("N".equals(entity.getConfirmStatus())){
		            fieldValue = "未确认";
		        }else{
		            fieldValue = entity.getConfirmStatus();
		        }
		    }
		}
		return fieldValue;
	}

    //导出对账单明细的的方法
    private ExportResource packExportResourceDetailXLS(List<PartnerStatementOfAwardDEntity> list, String[] header, String[] headerName){
        // 导出excel数据
        ExportResource data = new ExportResource();
        // 声明sheetList
        List<List<String>> sheetList = new ArrayList<List<String>>();
        // 循环进行封装
        for (PartnerStatementOfAwardDEntity entity : list) {
            // 声明一行的rowList
            List<String> rowList = new ArrayList<String>();
            for (String columnName : header) {
                // 通过名称获取field
                Field field = ReflectionUtils.findField(PartnerStatementOfAwardDEntity.class, columnName);
                if (field != null) {
                    // 通过传入字段获取值
                    ReflectionUtils.makeAccessible(field);
                    Object fieldValue = ReflectionUtils.getField(field, entity);
                    if (fieldValue != null) {
                    	// 如果为日期，需要进行格式化
                        if (Date.class.equals(field.getType())) {
                            //日期转化
                            fieldValue = DateUtils.convert((Date) fieldValue,DateUtils.DATE_TIME_FORMAT);
                        }
                        fieldValue = validaStatementOf(entity, columnName,
								fieldValue);
                        
                        if(columnName.equals("billType")){
                            //提货方式
                            fieldValue = validaAwardDEntity(entity, fieldValue);
                        }
                        
                        fieldValue = validaPartnerStatement(entity, columnName,
								fieldValue);
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

	private Object validaStatementOf(PartnerStatementOfAwardDEntity entity,
			String columnName, Object fieldValue) {
		if(columnName.equals("billParentType")){
		    //付款方式
		    if(StringUtils.isNotEmpty(fieldValue.toString())){
		        if("10.YS".equals(entity.getBillParentType())){
		            fieldValue = "应收单";
		        }else if("20.YF".equals(entity.getBillParentType())){
		            fieldValue = "应付单";
		        }else{
		            fieldValue = entity.getPaymentType();
		        }
		    }
		}
		return fieldValue;
	}

	private Object validaAwardDEntity(PartnerStatementOfAwardDEntity entity,
			Object fieldValue) {
		if(StringUtils.isNotEmpty(fieldValue.toString())){
		    if("PB".equals(entity.getBillType())){
		        fieldValue = "合伙人业务奖励应付";
		    }else if("PLE".equals(entity.getBillType())){
		        fieldValue = "合伙人快递差错应付";
		    }else if("PP".equals(entity.getBillType())){
		        fieldValue = "合伙人罚款应收";
		    }else if("PER".equals(entity.getBillType())){
		        fieldValue = "合伙人差错应收";
		    }else if("PTF".equals(entity.getBillType())){
		        fieldValue = "合伙人培训会务应收";
		    }else{
		        fieldValue = entity.getReceiveMethod();
		    }
		}
		return fieldValue;
	}

	private Object validaPartnerStatement(
			PartnerStatementOfAwardDEntity entity, String columnName,
			Object fieldValue) {
		if(columnName.equals("auditStatus")){
		    //单据子类型
		    if(StringUtils.isNotEmpty(fieldValue.toString())){
		        if("NA".equals(entity.getAuditStatus())){
		            fieldValue = "未审核";
		        }else if("AA".equals(entity.getAuditStatus())){
		            fieldValue = "已审核";
		        }else{
		            fieldValue = entity.getBillType();
		        }
		    }
		}
		return fieldValue;
	}
	
	public PartnerStatementOfAwardVo getPartnerStatementOfAwardVo() {
		return partnerStatementOfAwardVo;
	}

	public void setPartnerStatementOfAwardVo(
			PartnerStatementOfAwardVo partnerStatementOfAwardVo) {
		this.partnerStatementOfAwardVo = partnerStatementOfAwardVo;
	}

	public void setPartnerStatementOfAwardMService(
			IPartnerStatementOfAwardMService partnerStatementOfAwardMService) {
		this.partnerStatementOfAwardMService = partnerStatementOfAwardMService;
	}

	public String getExcelName() {
		return excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setPartnerStatementOfAwardDao(
			IPartnerStatementOfAwardDao partnerStatementOfAwardDao) {
		this.partnerStatementOfAwardDao = partnerStatementOfAwardDao;
	}
	
	
	
}
