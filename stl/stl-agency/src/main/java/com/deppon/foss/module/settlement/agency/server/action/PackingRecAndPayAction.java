package com.deppon.foss.module.settlement.agency.server.action;


import com.deppon.foss.framework.server.components.export.excel.*;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.settlement.agency.api.server.service.IPackingRecAndPayService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayResultDto;
import com.deppon.foss.module.settlement.agency.api.shared.vo.PackingRecAndPayVo;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;

import java.io.*;
import java.util.*;

/**
 * 查询_审核_作废包装其它应收应付
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-5-16 上午11:14:40,content:TODO </p>
 * @author 105762
 * @date 2014-5-16 上午11:14:40
 * @since 1.6
 * @version 1.0
 */
public class PackingRecAndPayAction extends AbstractAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * LOGGER
	 */
	private Logger LOGGER = Logger.getLogger(PackingRecAndPayAction.class);

	private IPackingRecAndPayService packingRecAndPayService;

	/**
	 * vo
	 */
	private PackingRecAndPayVo packingRecAndPayVo;

    /** 导出excel的输入流. */
    private InputStream inputStream;

    /** 导出excel名称. */
    private String exportExcelName;

    /** 导出的列名 */
    private static final String[] COLUMN_HEADER_NAMES = {"单据类别" ,"单号","运单号", "产生年月", "收款/应付类别", "部门编码", "部门名称", "包装供应商编码", "包装供应商", "对账单号", "审核状态", "总金额", "已核销金额", "未核销金额", "业务日期", "记账日期", "是否有效", "是否红单", "备注"};

    /** 列名对应列 */
    private static final String[] COLUMN_PROPERTY_NAMES = {"billNo", "waybillNo", "billTime", "collectionOrPayableType", "orgCode", "orgName", "customerCode", "customerName", "statementNo", "auditStatus", "amount", "verifyAmount", "unverifyAmount", "businessDate", "accountDate", "active", "isRedBack", "notes"};

    @JSON
	public String query() {
		LOGGER.info("start querying...");
		try {
			SettlementUtil.valideIsNull(packingRecAndPayVo, "packingRecAndPayVo 为空");
			SettlementUtil.valideIsNull(packingRecAndPayVo.getPackingRecAndPayQueryDto(), "packingRecAndPayVo中PackingRecAndPayResultDto为空");

			packingRecAndPayVo.getPackingRecAndPayQueryDto().setStart(start);
			packingRecAndPayVo.getPackingRecAndPayQueryDto().setLimit(limit);

			PackingRecAndPayResultDto result = packingRecAndPayService.query(packingRecAndPayVo.getPackingRecAndPayQueryDto(),
					FossUserContext.getCurrentInfo());
			SettlementUtil.valideIsNull(result, "未查询到数据");
			SettlementUtil.valideIsNull(result.getPackingRecAndPayDtos(), "未查询到数据");
			packingRecAndPayVo.setPackingRecAndPayResultDto(result);
		} catch (SettlementException e) {
			LOGGER.info(" A SettlementException has occured:" + e.getErrorCode());
			return returnError(e);
		}
		LOGGER.info("querying successful.");
		return returnSuccess();
	}

	/**
	 * <p>作废/红冲</p> 
	 * @author 105762
	 * @date 2014-6-10 下午6:11:09
	 * @return result
	 * @see
	 */
	@JSON
	public String invalid() {
		LOGGER.info("start querying...");
		try {
			SettlementUtil.valideIsNull(packingRecAndPayVo, "packingRecAndPayVo 为空");
			SettlementUtil.valideIsNull(packingRecAndPayVo.getPackingRecAndPayQueryDto(), "packingRecAndPayVo中PackingRecAndPayResultDto为空");
			packingRecAndPayService.invalid(packingRecAndPayVo.getPackingRecAndPayQueryDto(), FossUserContext.getCurrentInfo());
		} catch (SettlementException e) {
			LOGGER.info(" A SettlementException has occured:" + e.getErrorCode());
			return returnError(e);
		}
		LOGGER.info("query successfully.");
		return returnSuccess();
	}

	/**
	 * <p>审核</p> 
	 * @author 105762
	 * @date 2014-6-10 下午6:11:23
	 * @return result
	 * @see
	 */
	@JSON
	public String approve() {
		LOGGER.info("start approveing...");
		try {
			SettlementUtil.valideIsNull(packingRecAndPayVo, "packingRecAndPayVo 为空");
			SettlementUtil.valideIsNull(packingRecAndPayVo.getPackingRecAndPayQueryDto(), "packingRecAndPayVo中PackingRecAndPayResultDto为空");
			packingRecAndPayService.approve(packingRecAndPayVo.getPackingRecAndPayQueryDto(), FossUserContext.getCurrentInfo());
		} catch (SettlementException e) {
			LOGGER.info(" A SettlementException has occured:" + e.getErrorCode());
			return returnError(e);
		}
		LOGGER.info("approve successfully.");
		return returnSuccess();
	}

	/**
	 * <p>反审核</p> 
	 * @author 105762
	 * @date 2014-6-10 下午6:11:31
	 * @return result
	 * @see
	 */
	@JSON
	public String reverseApprove() {
		LOGGER.info("start reverseApproveing...");
		try {
			SettlementUtil.valideIsNull(packingRecAndPayVo, "packingRecAndPayVo 为空");
			SettlementUtil.valideIsNull(packingRecAndPayVo.getPackingRecAndPayQueryDto(), "packingRecAndPayVo中PackingRecAndPayResultDto为空");
			packingRecAndPayService.reverseApprove(packingRecAndPayVo.getPackingRecAndPayQueryDto(), FossUserContext.getCurrentInfo());
		} catch (SettlementException e) {
			LOGGER.info(" A SettlementException has occured:" + e.getErrorCode());
			return returnError(e);
		}
		LOGGER.info("reverseApprove successfully.");
		return returnSuccess();
	}

    /**
     * 导出
     * @return result
     */
    public String export(){
        LOGGER.info("导出包装其它应收应付单 开始...");

        try {
        /* 1.查询 */
            SettlementUtil.valideIsNull(packingRecAndPayVo, "packingRecAndPayVo 为空");
            SettlementUtil.valideIsNull(packingRecAndPayVo.getPackingRecAndPayQueryDto(), "packingRecAndPayVo中PackingRecAndPayResultDto为空");

            //去掉分页
            packingRecAndPayVo.getPackingRecAndPayQueryDto().setStart(0);
            packingRecAndPayVo.getPackingRecAndPayQueryDto().setLimit(Integer.MAX_VALUE);

            //查询
            PackingRecAndPayResultDto result = packingRecAndPayService.query(packingRecAndPayVo.getPackingRecAndPayQueryDto(),
                    FossUserContext.getCurrentInfo());
            SettlementUtil.valideIsNull(result, "未查询到数据");
            SettlementUtil.valideIsNull(result.getTotalCount(), "未查询到数据");
            SettlementUtil.valideIsNull(result.getPackingRecAndPayDtos(), "未查询到数据");
            if (result.getTotalCount() > SettlementConstants.EXPORT_EXCEL_MAX_COUNTS) {
                throw new SettlementException("查询结果总数超过十万，无法导出。");
            }

        /* 2.生成Excel */


            //excel 名字
            this.exportExcelName = SettlementUtil.convertExcelName("包装其他应收应付");

            //查询未确认收银的数据
            ExportResource exportResource = createExportResoutce(result);
            //创建导出表头对象
            ExportSetting exportSetting = new ExportSetting();
            //设置名称
            exportSetting.setSheetName("包装其他应收应付");
            //设置size 防止生成两个sheet
            exportSetting.setSize(Integer.MAX_VALUE);

            //创建导出工具类
            ExporterExecutor objExporterExecutor = new ExporterExecutor();
            // 导出成文件
            inputStream =  objExporterExecutor.exportSync(exportResource, exportSetting);


        } catch(SettlementException e) {
            LOGGER.info("导出包装其他应收应付单报错" + e.getMessage());
            returnError(e);
        }
        LOGGER.info("导出包装其它应收应付单 结束");
        return returnSuccess();
    }

    /**
     * 创建导出数据表
     * @param dto
     * @return 导出数据
     */
    private ExportResource createExportResoutce(PackingRecAndPayResultDto dto){
        ExportResource sheet = new ExportResource();
        sheet.setHeads(COLUMN_HEADER_NAMES);

        //获取相关数据字典
        List<String> dictTermsCodeList = new ArrayList<String>();
        dictTermsCodeList.add(DictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE);
        dictTermsCodeList.add(DictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE);
        dictTermsCodeList.add(DictionaryConstants.BILL_PAYABLE__APPROVE_STATUS);
        dictTermsCodeList.add(DictionaryConstants.FOSS_BOOLEAN);
        //后台获取数据字典对应的数据
        Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(dictTermsCodeList);

        //处理数据
        List<List<String>> rowList = new ArrayList<List<String>>();
        for(PackingRecAndPayDto oneDto :dto.getPackingRecAndPayDtos()){
            List<String> oneRowList = new ArrayList<String>();
            for(String propertyName : COLUMN_PROPERTY_NAMES){
                Object property = SettlementUtil.gainPropertyByName(oneDto,propertyName);
                if(property == null){
                    oneRowList.add("");
                }
                else if(property.getClass().equals(Date.class)){
                    oneRowList.add(DateUtils.formatDate2String((Date)property,"yyyy-MM-dd HH:mm:ss"));
                }
                else if(propertyName.equals("billNo")){
                    if(StringUtils.startsWith((String) property, "YS")){
                        oneRowList.add("应收");
                        oneRowList.add(property.toString());
                    }
                    else if(StringUtils.startsWith((String) property, "YF")){
                        oneRowList.add("应付");
                        oneRowList.add(property.toString());
                    }
                    else {
                        oneRowList.add("");
                        oneRowList.add(property.toString());
                    }
                }
                else if(propertyName.equals("collectionOrPayableType")){
                    if(StringUtils.startsWith(oneDto.getBillNo(), "YS")){
                        oneRowList.add(map.get(DictionaryConstants.BILL_RECEIVABLE__COLLECTION_TYPE).get(property.toString()).toString());
                    }
                    else if(StringUtils.startsWith(oneDto.getBillNo(), "YF")){
                        oneRowList.add(map.get(DictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE).get(property.toString()).toString());
                    }
                    else {
                        oneRowList.add(property.toString());
                    }
                }
                else if(propertyName.equals("auditStatus")){
                    oneRowList.add(map.get(DictionaryConstants.BILL_PAYABLE__APPROVE_STATUS).get(property.toString()).toString());
                }
                else if(propertyName.equals("isRedBack")||propertyName.equals("active")){
                    oneRowList.add(map.get(DictionaryConstants.FOSS_BOOLEAN).get(property.toString()).toString());
                }
                else {
                    oneRowList.add(property.toString());
                }
            }
            rowList.add(oneRowList);
        }

        sheet.setRowList(rowList);
        return sheet;
    }

	/**
	 * @param packingRecAndPayService the packingRecAndPayService to set
	 */
	public void setPackingRecAndPayService(IPackingRecAndPayService packingRecAndPayService) {
		this.packingRecAndPayService = packingRecAndPayService;
	}

	/**
	  * @return  the packingRecAndPayVo
	 */
	public PackingRecAndPayVo getPackingRecAndPayVo() {
		return packingRecAndPayVo;
	}

	/**
	 * @param packingRecAndPayVo the packingRecAndPayVo to set
	 */
	public void setPackingRecAndPayVo(PackingRecAndPayVo packingRecAndPayVo) {
		this.packingRecAndPayVo = packingRecAndPayVo;
	}

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getExportExcelName() {
        return exportExcelName;
    }

    public void setExportExcelName(String exportExcelName) {
        this.exportExcelName = exportExcelName;
    }
}
