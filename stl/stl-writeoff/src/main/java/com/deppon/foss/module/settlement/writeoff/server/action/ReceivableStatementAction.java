/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.server.action;

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
import com.deppon.foss.module.settlement.writeoff.api.server.dao.BillRepaymentManageDto;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IReceivableStatementService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.StatementRecivableDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.StatementRecivableEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementRecivableDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.vo.StatementRecivaleVo;
import com.deppon.foss.util.DateUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.util.ReflectionUtils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 收款对账单管理
 * @author foss-youkun
 * @date 2016/1/19
 */
public class ReceivableStatementAction  extends AbstractAction {
    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    /*
     * 对账单导出文件名字
     */
    private static final String EXCELNAME = "收款对账单";

    private static final  String EXCELNAMED="收款对账单明细";
    /*
	 * excel名字
	 */
    private String excelName;
    /*
     * 输出流
     */
    private InputStream inputStream;

    /**
     * Logger
     */
    private static final Logger logger = LogManager.getLogger(ReceivableStatementAction.class);

    /**
     * 前台查询参数
     */
    private StatementRecivaleVo statementRecivaleVo;

    /**
     * 对账单services
     */
    private IReceivableStatementService receivableStatementService;



    /**
     *查询月结的始发提成应收单和委托派费应收单
     * @return
     */
    @JSON
    public String queryReceivableStatement(){
        // 获取当前登录信息
        CurrentInfo info = FossUserContext.getCurrentInfo();
        if(statementRecivaleVo==null){
            return returnError("查询参数不能为空！");
        }
        StatementRecivableDto statementRecivableDto ;
        try{
            statementRecivableDto = receivableStatementService.queryBillRecivableByList(statementRecivaleVo.getStatementRecivableDto(), info, getStart(), getLimit());
        }catch (SettlementException e){
            logger.error(e.getMessage());
            return returnError(e.getErrorCode());
        }
        if(statementRecivableDto!=null){
            if(statementRecivableDto.getBillReceivableEntityList().size()>0){
                setTotalCount(statementRecivableDto.getTotalCount());
            }
            statementRecivaleVo.setStatementRecivableDto(statementRecivableDto);
        }
        return returnSuccess();
    }

    /**
     * 根据月结的始发提成应收单和委托派费应收单
     * 生成对账单
     * @return
     */
    @JSON
    public String addReceivableStatement(){
        if(statementRecivaleVo==null){
            return returnError("参数不能为空！");
        }
        // 获取当前登录信息
        CurrentInfo info = FossUserContext.getCurrentInfo();

        String statementBillNo;
        try {
            statementBillNo = receivableStatementService.addReceivableStatement(statementRecivaleVo.getStatementRecivableDto(), info);
        }catch(SettlementException e){
            logger.error(e.getMessage());
            return returnError(e.getErrorCode());
        }
        statementRecivaleVo.getStatementRecivableDto().setStatementBillNo(statementBillNo);
        return returnSuccess();
    }


    /**
     * 收款对账单查询
     * @autor 尤坤
     * @return
     */
    @JSON
    public String queryPartnerReceivalbeStatement(){
        if(statementRecivaleVo==null){
            return returnError("查询条件不能为空！");
        }
        if(statementRecivaleVo.getStatementRecivableDto()==null){
            return returnError("查询条件不能为空！");
        }
        List<StatementRecivableEntity> list = new ArrayList<StatementRecivableEntity>();
        Long total ;
        try{
            list = receivableStatementService.queryPartnerReceivalbeStatement(statementRecivaleVo.getStatementRecivableDto(),getStart(),getLimit());
        }catch (SettlementException e){
            logger.error(e.getErrorCode());
            return returnError(e.getErrorCode());
        }
        try{
            total = receivableStatementService.queryPartnerReceivalbeStatementCount(statementRecivaleVo.getStatementRecivableDto());
        }catch(SettlementException e){
            logger.error(e.getErrorCode());
            return returnError(e.getErrorCode());
        }
        statementRecivaleVo.getStatementRecivableDto().setStatementRecivableEntityList(list);
        setTotalCount(total);
        return returnSuccess();
    }

    /**
     * 查询对账单明细
     * @return
     */
    @JSON
    public String queryReceivalbeStatementByBillNo(){
        if(null==statementRecivaleVo){
            return returnError("参数不能为空！");
        }
        if(null == statementRecivaleVo.getStatementRecivableDto()){
            return returnError("参数不能为空！");
        }
        if(null==statementRecivaleVo.getStatementRecivableDto().getStatementBillNo()){
            return returnError("对账单号不能为空！");
        }
        List<StatementRecivableDEntity> list = new ArrayList<StatementRecivableDEntity>();
        try{
            list = receivableStatementService.queryReceivalbeStatementByBillNo(statementRecivaleVo.getStatementRecivableDto().getStatementBillNo());
        }catch (SettlementException e){
            logger.error(e.getMessage());
           return returnError(e.getMessage());
        }
        statementRecivaleVo.getStatementRecivableDto().setStatementRecivableDEntityList(list);
        if(null!=list){
            Long total =Long.parseLong(String.valueOf(list.size()));
            setTotalCount(total);
        }else{
            setTotalCount(0L);
        }
        return returnSuccess();
    }

    /**
     * 更新对账的账单状态
     */
    @JSON
    public String updateStatementByStatementStatus(){
        if(null==statementRecivaleVo){
            return returnError("参数不能空");
        }
        if(null==statementRecivaleVo.getStatementRecivableDto()){
            return returnError("参数不能空");
        }
        try{
            receivableStatementService.updateStatementByStatementStatus(statementRecivaleVo.getStatementRecivableDto().getStatementStatus(), statementRecivaleVo.getStatementRecivableDto().getStatementBillNoList());
        }catch(SettlementException e){
            logger.error(e.getMessage());
            return returnError(e.getErrorCode());
        }
        return returnSuccess();
    }

    /**
     * 删除收款对账单明细
     * @return
     */
    @JSON
    public String deleteReceivableStatementById(){
        if(null==statementRecivaleVo){
            return returnError("参数不能空");
        }
        if(null==statementRecivaleVo.getStatementRecivableDto()){
            return returnError("参数不能空");
        }
        try{
            receivableStatementService.deleteReceivableStatementById(statementRecivaleVo.getStatementRecivableDto().getStatementBillNoList());
        }catch(BusinessException e){
            logger.error(e.getErrorCode(),e);
            return returnError(e.getErrorCode());
        }
        return returnSuccess();
    }

    /**
     * 合伙人还款
     * @return
     */
    @JSON
    public String partnerRepayment(){
        try {
            logger.debug("还款/批量还款开始...");
            //获取当前登录用户的信息
            // 获取当前登录用户信息
            CurrentInfo cInfo = FossUserContext.getCurrentInfo();
            //获取批量作废还款单的 当前用户
            if(null==cInfo.getEmpName()){
                //抛出异常
                throw new SettlementException("还款/批量还款获取用户名为空", "");
            }
            logger.debug("还款/批量还款登录用户:"+cInfo.getUserName());
            // 还款
            String repaymentNo = receivableStatementService.partnerRepayment(statementRecivaleVo.getBillStatementToPaymentQueryDto(),cInfo);
            //获取还款单号
            BillRepaymentManageDto billRepaymentManageDto = new BillRepaymentManageDto();
            billRepaymentManageDto.setRepaymentNo(repaymentNo);
            //设置返回还款单号
            statementRecivaleVo.setBillRepaymentManageDto(billRepaymentManageDto);
            //还款结束
            logger.debug("还款success...");
            return returnSuccess();
        } catch (SettlementException e) {
            //批量作废还款单logger
            logger.error("还款、批量还款异常"+e.getMessage(), e);
            return returnError(e);
        }

    }

    /**
     *根据对账单号查询对账单需要的数据
     * 和校验是否满足还款
     * @return
     */
    @JSON
    public String repaymentBillofStatementNo(){
        //创建一个对象
        StatementRecivableEntity entity;

        if(null==statementRecivaleVo||null==statementRecivaleVo.getStatementRecivableDto()){
            returnError("传入的参数不全");
        }
        if(null==statementRecivaleVo.getStatementRecivableDto().getStatementBillNoList()||
                statementRecivaleVo.getStatementRecivableDto().getStatementBillNoList().size()<=0 ){
            returnError("传入的参数不全");
        }
        try{
            entity =  receivableStatementService.repaymentBillofStatementNo(statementRecivaleVo.getStatementRecivableDto().getStatementBillNoList());
        }catch (SettlementException e){
            logger.error("查询失败："+e.getMessage(),e);
            return returnError(e.getMessage());
        }
        if(null!=entity){
            int i = entity.getPeriodNpayAmount().compareTo(BigDecimal.ZERO);
            //f(i==0) 等于 if(i==1) 大于if(i==-1) 小于
            if(i==0||i==-1){
                return  returnError("剩余未还金额不能小于等于零");
            }
            statementRecivaleVo.getStatementRecivableDto().setStatementRecivableEntity(entity);
        }
        return  returnSuccess();
    }

    /**
     * 添加对账单明细
     * @return
     */
    public String addReceivableStatementDetail(){
        if(null==statementRecivaleVo||null==statementRecivaleVo.getStatementRecivableDto()){
            return returnError("参数不能为空！");
        }
        try{
            receivableStatementService.addReceivableStatementDetail(statementRecivaleVo.getStatementRecivableDto());
        }catch(SettlementException e){
            return  returnError(e.getErrorCode());
        }
        return returnSuccess();
    }

    /**
     * 导出对账单信息
     * @return
     */
    @JSON
    public String receivableStatemenExl(){
        if(null==statementRecivaleVo){
            return returnError("导出参数不能为空!");
        }
        if(null==statementRecivaleVo.getStatementRecivableDto()){
            return returnError("导出参数不能为空!");
        }
        if(null==statementRecivaleVo.getStatementRecivableDto().getWaybillNoList()){
            return returnError("导出参数不能为空!");
        }
        // 获取类头名称
        String[] header = statementRecivaleVo.getStatementRecivableDto().getArrayColumns();
        // 获取列头中文名
        String[] headerName = statementRecivaleVo.getStatementRecivableDto().getArrayColumnNames();
        try {
            try {
                // 设置导出excel名称
                this.setExcelName(new String((EXCELNAME).getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(), e);
                return returnError("导出Excel失败");
            }
            //查询对账单信息
            List<StatementRecivableEntity> list;
            try{
                list = this.receivableStatementService.queryPartnerStatementList(statementRecivaleVo.getStatementRecivableDto().getWaybillNoList());
            }catch (SettlementException e){
                logger.error(e.getMessage(), e);
                return returnError(e.getMessage());
            }
            // 导出格式数据
            ExportResource exportResource = packExportResourceXLS(list,header,headerName);
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
     * 导出收款对账单明细
     * @return
     */
    @JSON
    public String  receivableStatemenDetailExl(){
        if(null==statementRecivaleVo){
            return returnError("导出参数不能为空!");
        }
        if(null==statementRecivaleVo.getStatementRecivableDto()){
            return returnError("导出参数不能为空!");
        }
        if(null==statementRecivaleVo.getStatementRecivableDto().getWaybillNoList()){
            return returnError("导出参数不能为空!");
        }
        // 获取类头名称
        String[] header = statementRecivaleVo.getStatementRecivableDto().getArrayColumns();
        // 获取列头中文名
        String[] headerName = statementRecivaleVo.getStatementRecivableDto().getArrayColumnNames();
        try {
            try {
                // 设置导出excel名称
                this.setExcelName(new String((EXCELNAMED).getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(), e);
                return returnError("导出Excel失败");
            }
            //查询对账单信息
            List<StatementRecivableDEntity> list;
            try{
                list = this.receivableStatementService.queryReceivalbeStatementByBillNo(statementRecivaleVo.getStatementRecivableDto().getWaybillNoList().get(0).toString());
            }catch (SettlementException e){
                logger.error(e.getMessage(), e);
                return returnError(e.getMessage());
            }
            // 导出格式数据
            ExportResource exportResource = packExportResourceDetailXLS(list,header,headerName);
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
    private ExportResource packExportResourceXLS(List<StatementRecivableEntity> list, String[] header, String[] headerName){
        // 导出excel数据
        ExportResource data = new ExportResource();
        // 声明sheetList
        List<List<String>> sheetList = new ArrayList<List<String>>();
        // 循环进行封装
        for (StatementRecivableEntity entity : list) {
            // 声明一行的rowList
            List<String> rowList = new ArrayList<String>();
            for (String columnName : header) {
                // 通过名称获取field
                Field field = ReflectionUtils.findField(StatementRecivableEntity.class, columnName);
                if (field != null) {
                    // 通过传入字段获取值
                    ReflectionUtils.makeAccessible(field);
                    Object fieldValue = ReflectionUtils.getField(field, entity);
                    // 如果为日期，需要进行格式化
                    fieldValue = validaField(entity, columnName, field,
							fieldValue);
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

	private Object validaField(StatementRecivableEntity entity,
			String columnName, Field field, Object fieldValue) {
		if (Date.class.equals(field.getType())&& fieldValue != null) {
		    //日期转化
		    fieldValue = DateUtils.convert((Date) fieldValue,DateUtils.DATE_TIME_FORMAT);
		}
		if(columnName.equals("statementStatus")){
		    //如果数据产品类型编码不为空
		    if(StringUtils.isNotEmpty(fieldValue.toString())){
		        if("Y".equals(entity.getStatementStatus())){
		            fieldValue = "已确认";
		        }else if("N".equals(entity.getStatementStatus())){
		            fieldValue = "未确认";
		        }else{
		            fieldValue = entity.getStatementStatus();
		        }
		    }
		}
		return fieldValue;
	}

    //导出对账单明细的的方法
    private ExportResource packExportResourceDetailXLS(List<StatementRecivableDEntity> list, String[] header, String[] headerName){
        // 导出excel数据
        ExportResource data = new ExportResource();
        // 声明sheetList
        List<List<String>> sheetList = new ArrayList<List<String>>();
        // 循环进行封装
        for (StatementRecivableDEntity entity : list) {
            // 声明一行的rowList
            List<String> rowList = new ArrayList<String>();
            for (String columnName : header) {
                // 通过名称获取field
                Field field = ReflectionUtils.findField(StatementRecivableDEntity.class, columnName);
                if (field != null) {
                    // 通过传入字段获取值
                    ReflectionUtils.makeAccessible(field);
                    Object fieldValue = ReflectionUtils.getField(field, entity);
                    // 如果为日期，需要进行格式化
                    if (Date.class.equals(field.getType())&& fieldValue != null) {
                        //日期转化
                        fieldValue = DateUtils.convert((Date) fieldValue,DateUtils.DATE_TIME_FORMAT);
                    }
                    fieldValue = validaEntity(entity, columnName, fieldValue);
                    fieldValue = validaFieldValue(entity, columnName,
							fieldValue);
                    if(columnName.equals("receiveMethod")){
                        //提货方式
                        fieldValue = validaStatement(entity, fieldValue);
                    }
                    if(columnName.equals("billType")){
                        //单据子类型
                        fieldValue = validaStatementRecivable(entity,
								fieldValue);
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

	private Object validaEntity(StatementRecivableDEntity entity,
			String columnName, Object fieldValue) {
		if(columnName.equals("proType")){
		    //如果数据产品类型编码不为空
		    if(StringUtils.isNotEmpty(fieldValue.toString())){
		        if("AF".equals(entity.getProType())){
		            fieldValue = "精准空运";
		        }else if("FLF".equals(entity.getProType())){
		            fieldValue = "精准卡航";
		        }else if("FSF".equals(entity.getProType())){
		            fieldValue = "精准城运";
		        }else if("LRF".equals(entity.getProType())){
		            fieldValue = "精准汽运(长途)";
		        }else if("PLF".equals(entity.getProType())){
		            fieldValue = "汽运偏线";
		        }else if("SRF".equals(entity.getProType())){
		            fieldValue = "精准汽运(短途)";
		        }else if("WVH".equals(entity.getProType())){
		            fieldValue = "整车（三级）";
		        }else if("PACKAGE".equals(entity.getProType())){
		            fieldValue = "标准快递";
		        }else if("RCP".equals(entity.getProType())){
		            fieldValue = "3.60特惠件";
		        }else if("BGFLF".equals(entity.getProType())){
		            fieldValue = "精准大票卡航";
		        }else if("BGLRF".equals(entity.getProType())){
		            fieldValue = "精准大票汽运(长)";
		        }else if("BGFSF".equals(entity.getProType())){
		            fieldValue = "精准大票城运";
		        }else if("BGSRF".equals(entity.getProType())){
		            fieldValue = "精准大票汽运(短)";
		        }else if("DTD".equals(entity.getProType())){
		            fieldValue = "精准大票.经济件";
		        }else if("YTY".equals(entity.getProType())){
		            fieldValue = "精准大票.标准件";
		        }else if("EPEP".equals(entity.getProType())){
		            fieldValue = "电商尊享";
		        }else if("DEAP".equals(entity.getProType())){
		            fieldValue = "商务专递";
		        }else{
		            fieldValue = entity.getProType();
		        }
		    }
		}
		return fieldValue;
	}

	private Object validaFieldValue(StatementRecivableDEntity entity,
			String columnName, Object fieldValue) {
		if(columnName.equals("paymentType")){
		    //付款方式
		    if(StringUtils.isNotEmpty(fieldValue.toString())){
		        if("CT".equals(entity.getPaymentType())){
		            fieldValue = "月结";
		        }else if("CH".equals(entity.getPaymentType())){
		            fieldValue = "现金";
		        }else if("CD".equals(entity.getPaymentType())){
		            fieldValue = "银行卡";
		        }else if("TT".equals(entity.getPaymentType())){
		            fieldValue = "电汇";
		        }else if("NT".equals(entity.getPaymentType())){
		            fieldValue = "支票";
		        }else if("OL".equals(entity.getPaymentType())){
		            fieldValue = "网上支付";
		        }else if("DT".equals(entity.getPaymentType())){
		            fieldValue = "临时欠款";
		        }else if("FC".equals(entity.getPaymentType())){
		            fieldValue = "到付";
		        }else{
		            fieldValue = entity.getPaymentType();
		        }
		    }
		}
		return fieldValue;
	}

	private Object validaStatement(StatementRecivableDEntity entity,
			Object fieldValue) {
		if(StringUtils.isNotEmpty(fieldValue.toString())){
		    if("SELF_PICKUP".equals(entity.getReceiveMethod())){
		        fieldValue = "自提";
		    }else if("DELIVER_INGA".equals(entity.getReceiveMethod())){
		        fieldValue = "送货进仓";
		    }else if("DELIVER_NOUP".equals(entity.getReceiveMethod())){
		        fieldValue = "送货(不含上楼)";
		    }else if("DELIVER_FLOOR".equals(entity.getReceiveMethod())){
		        fieldValue = "送货上楼安装（家居)";
		    }else if("LARGE_DELIVER_UP".equals(entity.getReceiveMethod())){
		        fieldValue = "大件上楼";
		    }else if("DELIVER".equals(entity.getReceiveMethod())){
		        fieldValue = "免费送货";
		    }else if("DELIVER_UP".equals(entity.getReceiveMethod())){
		        fieldValue = "送货上楼";
		    }else if("INNER_PICKUP".equals(entity.getReceiveMethod())){
		        fieldValue = "内部带货自提";
		    }else{
		        fieldValue = entity.getReceiveMethod();
		    }
		}
		return fieldValue;
	}

	private Object validaStatementRecivable(StatementRecivableDEntity entity,
			Object fieldValue) {
		if(StringUtils.isNotEmpty(fieldValue.toString())){
		    if("CR".equals(entity.getBillType())){
		        fieldValue = "代收货款应收";
		    }else if("POR".equals(entity.getBillType())){
		        fieldValue = "合伙人始发提成应收";
		    }else if("PFCR".equals(entity.getBillType())){
		        fieldValue = "合伙人到付运费应收";
		    }else if("PODR".equals(entity.getBillType())){
		        fieldValue = "合伙人始发提成和委托派费应收";
		    }else if("PDFR".equals(entity.getBillType())){
		        fieldValue = "合伙人委托派费应收";
		    }else if("PFCCR".equals(entity.getBillType())){
		        fieldValue = "合伙人部分到付部分现付运费应收";
		    }else if("PP".equals(entity.getBillType())){
		        fieldValue = "合伙人罚款应收";
		    }else if("PTF".equals(entity.getBillType())){
		        fieldValue = "合伙人培训会务应收";
		    }else if("PER".equals(entity.getBillType())){
		        fieldValue = "合伙人差错应收";
		    }else{
		        fieldValue = entity.getBillType();
		    }
		}
		return fieldValue;
	}


    public StatementRecivaleVo getStatementRecivaleVo() {
        return statementRecivaleVo;
    }

    public void setStatementRecivaleVo(StatementRecivaleVo statementRecivaleVo) {
        this.statementRecivaleVo = statementRecivaleVo;
    }

    public void setReceivableStatementService(IReceivableStatementService receivableStatementService) {
        this.receivableStatementService = receivableStatementService;
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
}
