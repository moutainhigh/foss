package com.deppon.foss.module.settlement.closing.server.action;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.components.export.excel.HeaderRows;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.classes.ReflectionUtils;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrDcdService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrDcdEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDcdQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDcdResultDto;
import com.deppon.foss.module.settlement.closing.api.shared.vo.MvrDcdVo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 折扣单到达报表
 *
 * @author 105762
 * @version 1.0
 * @date 2015-03-31
 * @since 1.6
 */
public class MvrDcdAction extends AbstractAction {

    private static final long serialVersionUID = 5165914761001870850L;

    /**
     * Logger
     */
    private static Logger logger = LogManager.getLogger(MvrDcdAction.class);

    /**
     * Vo
     */
    private MvrDcdVo vo;

    /**
     * service
     */
    private IMvrDcdService mvrDcdService;

    /**
     * 导出输出流
     */
    private InputStream inputStream;

    /**
     * 导出excel名称
     */
    private String execlName;

    /**
     * 根据产品CODE找对应的名称
     */
    IProductService productService;

    /**
     * @param productService
     * @SET
     */
    public void setProductService(IProductService productService) {
        /*
         * @set
		 * 
		 * @this.productService = productService
		 */
        this.productService = productService;
    }

    /**
     * 查询
     */
    @JSON
    public String query() {

        try {
            MvrDcdQueryDto dto = vo.getQueryDto();
            CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
            // 设置用户数据查询权限
            dto.setUserCode(currentInfo.getEmpCode());

            // 设置分页数据
            dto.setStart(getStart());
            dto.setLimit(getLimit());

            // 查询
            MvrDcdResultDto mvrDiscountResultDto = mvrDcdService.query(dto);

            // 设置返回值
            vo.setResultDto(mvrDiscountResultDto);
            this.setTotalCount(mvrDiscountResultDto.getCount());

            // 正常返回
            return returnSuccess();
            // 异常处理
        } catch (BusinessException be) {
            // 记录日志
            logger.error(be.getMessage(), be);
            // 异常返回
            return returnError(be);
        }

    }

    /**
     * 导出
     */
    @SuppressWarnings("unchecked")
    public String export() {

        try {

            if (vo == null) {
                throw new SettlementException("查询参数不能为空");
            }

            MvrDcdQueryDto dto = vo.getQueryDto();

            // 导出期间不能为空
            if (StringUtils.isEmpty(dto.getPeriod())) {
                throw new SettlementException("导出折扣调整到达月报表期间不能为空");
            }

            CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
            // 设置用户数据查询权限
            dto.setUserCode(currentInfo.getEmpCode());

            // 导出文件名称：
            try {
                String exportExcelName = "折扣调整到达月报表" + dto.getPeriod() + ".xls";
                /*this.setExeclName(URLEncoder.encode(exportExcelName,
						SettlementConstants.UNICODE_UTF));*/

                // 转化编码
                this.setExeclName(new String((exportExcelName)
                        .getBytes(SettlementConstants.UNICODE_GBK),
                        SettlementConstants.UNICODE_ISO));
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(), e);
                return returnError("导出Excel失败");
            }

            // 查询导出数据
            dto.setStart(0);
            dto.setLimit(Integer.MAX_VALUE);
            List<MvrDcdEntity> queryList = mvrDcdService.query(dto).getEntityList();

            // 查询结果不能为空
            if (CollectionUtils.isEmpty(queryList)) {
                // 提示导出
                throw new SettlementException("导出月报表查询数据为空");
            }

            // 生成导出数据源类
            ExportResource sheet = new ExportResource();
            // 设置表单表头
            sheet.setHeads(this.getExcelHeads());

            Object[] dataAndType = this.exportDataAndType(queryList);
            // 处理返回的结果集

            List<List<String>> dataList = (List<List<String>>) dataAndType[0];
            Map<Integer, String> type = (Map<Integer, String>) dataAndType[1];

            // 设置表单数据
            sheet.setRowList(dataList);

            sheet.setHeaderHeight(SettlementReportNumber.THREE);

            HeaderRows[] headerCfg = {
                    new HeaderRows(SettlementReportNumber.ZERO,	SettlementReportNumber.ZERO,	SettlementReportNumber.ZERO,	SettlementReportNumber.THIRTEEN,	"数据统计维度"),
                    new HeaderRows(SettlementReportNumber.ZERO,	SettlementReportNumber.ZERO,	SettlementReportNumber.FOURTEEN,	SettlementReportNumber.FIFTEEN,	"事后折扣【02】-特殊业务调整"),
                    new HeaderRows(SettlementReportNumber.ZERO,	SettlementReportNumber.ZERO,	SettlementReportNumber.SEVENTEEN,	SettlementReportNumber.SEVENTEEN,	"事后折扣【01】-特殊业务调整"),
                    new HeaderRows(SettlementReportNumber.ONE,	SettlementReportNumber.ONE,	SettlementReportNumber.FOURTEEN,	SettlementReportNumber.FIFTEEN,	"02理赔-到达部门申请"),
                    new HeaderRows(SettlementReportNumber.ONE,	SettlementReportNumber.ONE,	SettlementReportNumber.SIXTEEN,	SettlementReportNumber.SEVENTEEN,	"01理赔-到达部门申请"),
                    new HeaderRows(SettlementReportNumber.TWO,	SettlementReportNumber.TWO,	SettlementReportNumber.ZERO,	SettlementReportNumber.ZERO,	"期间"),
                    new HeaderRows(SettlementReportNumber.TWO,	SettlementReportNumber.TWO,	SettlementReportNumber.ONE,	SettlementReportNumber.ONE,	"运输性质"),
                    new HeaderRows(SettlementReportNumber.TWO,	SettlementReportNumber.TWO,	SettlementReportNumber.TWO,	SettlementReportNumber.TWO,	"发票标记"),
                    new HeaderRows(SettlementReportNumber.TWO,	SettlementReportNumber.TWO,	SettlementReportNumber.THREE,	SettlementReportNumber.THREE,	"客户编码"),
                    new HeaderRows(SettlementReportNumber.TWO,	SettlementReportNumber.TWO,	SettlementReportNumber.FOUR,	SettlementReportNumber.FOUR,	"客户名称"),
                    new HeaderRows(SettlementReportNumber.TWO,	SettlementReportNumber.TWO,	SettlementReportNumber.FIVE,	SettlementReportNumber.FIVE,	"始发部门编码"),
                    new HeaderRows(SettlementReportNumber.TWO,	SettlementReportNumber.TWO,	SettlementReportNumber.SIX,	SettlementReportNumber.SIX,	"始发部门名称"),
                    new HeaderRows(SettlementReportNumber.TWO,	SettlementReportNumber.TWO,	SettlementReportNumber.SEVEN,	SettlementReportNumber.SEVEN,	"始发部门标杆编码"),
                    new HeaderRows(SettlementReportNumber.TWO,	SettlementReportNumber.TWO,	SettlementReportNumber.EIGHT,	SettlementReportNumber.EIGHT,	"到达部门编码"),
                    new HeaderRows(SettlementReportNumber.TWO,	SettlementReportNumber.TWO,	SettlementReportNumber.NINE,	SettlementReportNumber.NINE,	"到达部门名称"),
                    new HeaderRows(SettlementReportNumber.TWO,	SettlementReportNumber.TWO,	SettlementReportNumber.TEN,	SettlementReportNumber.TEN,	"到达部门标杆编码"),
                    new HeaderRows(SettlementReportNumber.TWO,	SettlementReportNumber.TWO,	SettlementReportNumber.ELEVEN,	SettlementReportNumber.ELEVEN,	"统一结算类型"),
                    new HeaderRows(SettlementReportNumber.TWO,	SettlementReportNumber.TWO,	SettlementReportNumber.TWELVE,	SettlementReportNumber.TWELVE,	"合同部门编码"),
                    new HeaderRows(SettlementReportNumber.TWO,	SettlementReportNumber.TWO,	SettlementReportNumber.THIRTEEN,	SettlementReportNumber.THIRTEEN,	"合同部门名称"),
                    new HeaderRows(SettlementReportNumber.TWO,	SettlementReportNumber.TWO,	SettlementReportNumber.FOURTEEN,	SettlementReportNumber.FOURTEEN,	"02理赔冲收入调整"),
                    new HeaderRows(SettlementReportNumber.TWO,	SettlementReportNumber.TWO,	SettlementReportNumber.FIFTEEN,	SettlementReportNumber.FIFTEEN,	"02理赔入成本调整"),
                    new HeaderRows(SettlementReportNumber.TWO,	SettlementReportNumber.TWO,	SettlementReportNumber.SIXTEEN,	SettlementReportNumber.SIXTEEN,	"01理赔冲收入调整"),
                    new HeaderRows(SettlementReportNumber.TWO,	SettlementReportNumber.TWO,	SettlementReportNumber.SEVENTEEN,	SettlementReportNumber.SEVENTEEN,	"01理赔入成本调整")
            };
            sheet.setHeaderList(Arrays.asList(headerCfg));

            // 创建导出表头对象
            ExportSetting exportSetting = new ExportSetting();

            // 设置sheet名称
            String exprotSheetName = vo.getQueryDto()
                    .getPeriod().toString();
            exportSetting.setSheetName(exprotSheetName);
            exportSetting.setDataType(type);

            // 创建导出工具类
            ExporterExecutor objExporterExecutor = new ExporterExecutor();

            // 导出成文件
            inputStream = objExporterExecutor.exportSync(sheet, exportSetting);

            // 正常返回
            return returnSuccess();
            // 异常处理
        } catch (BusinessException e1) {
            // 记录日志
            logger.error(e1.getMessage(), e1);
            // 异常返回
            return returnError(e1);
            // 异常处理
        }
    }

    /**
     * 设置Excel 表头
     *
     * @return
     */
    private String[] getExcelHeads() {

        String[] header = {
                // 数据统计维度
                "期间", "运输性质","发票标记", "客户编码", "客户名称", "始发部门编码", "始发部门名称", "始发部门标杆编码",
                "到达部门编码", "到达部门名称", "到达部门标杆编码", "统一结算类型",  "合同部门编码", "合同部门名称",
                "02理赔冲收入调整","02理赔入成本调整","01理赔冲收入调整","01理赔入成本调整"

        };

        // 返回excel表头
        return header;
    }

    /**
     * 生成导出始发报表数据
     *
     * @param queryList
     * @return
     * @author ddw
     * @date 2013-11-08
     */
    private Object[] exportDataAndType(List<MvrDcdEntity> queryList) {

        String[] properties = new String[]{
                "period", "productCode","invoiceMark",
                "customerCode", "customerName", "origOrgCode", "origOrgName",
                "origUnifiedCode", "destOrgCode", "destOrgName",
                "destUnifiedCode", "unifiedSettlementType", "contractOrgCode", "contractOrgName",
                "dcdDestApplyWoIncomet","dcdDestApplyWoCostt",
                "ldcDestCosto","ldcDestIncomet"
                };

        List<String> strProperties = Arrays.asList(
                "period", "productCode","invoiceMark",
                "customerCode", "customerName", "origOrgCode", "origOrgName",
                "origUnifiedCode", "destOrgCode", "destOrgName",
                "destUnifiedCode", "unifiedSettlementType", "contractOrgCode", "contractOrgName");

        // 生成返回数据集合
        List<List<String>> resultList = new ArrayList<List<String>>();

        // 获取全部有效的第三级别产品类型
        List<ProductEntity> productList = productService
                .queryLevel3ProductInfo();
        // 生成存储产品类型的map
        Map<String, String> productMap = new HashMap<String, String>();
        // 如果产品类型不为空，循环加入到map中
        if (CollectionUtils.isNotEmpty(productList)) {
            for (ProductEntity entity : productList) {
                productMap.put(entity.getCode(), entity.getName());
            }
        }

        List<String> data;
        Object fieldValue;
        String cellValue;

        // 循环处理
        for (MvrDcdEntity entity : queryList) {

            data = new ArrayList<String>();
            for (String property : properties) {
                // 获取对象的值，如果为空，则设置其为空字符串
                fieldValue = ReflectionUtils.getFieldValue(entity, property);
                cellValue = (fieldValue == null ? "" : fieldValue.toString());

                // 产品类型需要转换
                if ("productCode".equalsIgnoreCase(property)) {

                    // 如果数据产品类型编码不为空
                    if (StringUtils.isNotEmpty(cellValue)) {
                        // 将产品类型转换编码为名称
                        cellValue = productMap.get(cellValue);
                    }
                }
                // 统一结算类型
                if ("unifiedSettlementType".equalsIgnoreCase(property)) {
                    // 如果统一结算类型不为空
                    if (StringUtils.isNotEmpty(cellValue)) {
                        if (SettlementDictionaryConstants.CLAIM_APPLY_ORG_TYPE_ORIG.equals(cellValue)) {
                            cellValue = "始发统一结算";
                        } else if (SettlementDictionaryConstants.CLAIM_APPLY_ORG_TYPE_DEST.equals(cellValue)) {
                            cellValue = "到达统一结算";
                        } else {
                            cellValue = "非统一结算";
                        }
                    }
                }
                data.add(cellValue);
            }

            resultList.add(data);
        }

        Map<Integer, String> typeMap = new HashMap<Integer, String>();
        for (int i = 0; i < properties.length; i++) {
            String type = strProperties.contains(properties[i]) ? "string"
                    : "float";
            typeMap.put(i, type);
        }

        // 返回
        return new Object[]{resultList, typeMap};
    }

    public MvrDcdVo getVo() {
        return vo;
    }

    public void setVo(MvrDcdVo vo) {
        this.vo = vo;
    }

    /**
     * @return inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param inputStream
     */
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * @return execlName
     */
    public String getExeclName() {
        return execlName;
    }

    /**
     * @param execlName
     */
    public void setExeclName(String execlName) {
        this.execlName = execlName;
    }

    /**
     * @return serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void setMvrDcdService(IMvrDcdService mvrDcdService) {
        this.mvrDcdService = mvrDcdService;
    }

}
