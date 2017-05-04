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
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrDcoService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrDcoEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDcoQueryDto;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrDcoResultDto;
import com.deppon.foss.module.settlement.closing.api.shared.vo.MvrDcoVo;
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
 * 折扣调整始发月报表
 *
 * @author 105762
 * @version 1.0
 * @date 2015-03-31
 * @since 1.6
 */
public class MvrDcoAction extends AbstractAction {

    private static final long serialVersionUID = 3119189941722445989L;

    /**
     * Logger
     */
    private static Logger logger = LogManager.getLogger(MvrDcoAction.class);

    /**
     * Vo
     */
    private MvrDcoVo vo;

    /**
     * service
     */
    private IMvrDcoService mvrDcoService;

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
            MvrDcoQueryDto dto = vo.getQueryDto();
            CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
            // 设置用户数据查询权限
            dto.setUserCode(currentInfo.getEmpCode());

            // 设置分页数据
            dto.setStart(getStart());
            dto.setLimit(getLimit());

            // 查询
            MvrDcoResultDto mvrDiscountResultDto = mvrDcoService.query(dto);

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
    @SuppressWarnings({"unchecked"})
    public String export() {

        try {

            if (vo == null) {
                throw new SettlementException("查询参数不能为空");
            }

            MvrDcoQueryDto dto = vo.getQueryDto();

            // 导出期间不能为空
            if (StringUtils.isEmpty(dto.getPeriod())) {
                throw new SettlementException("导出始发折扣调整月报表期间不能为空");
            }

            CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
            // 设置用户数据查询权限
            dto.setUserCode(currentInfo.getEmpCode());

            // 导出文件名称：
            try {
                String exportExcelName = "始发折扣调整报表" + dto.getPeriod() + ".xls";
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
            List<MvrDcoEntity> queryList = mvrDcoService.query(dto).getEntityList();

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
                    new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ONE, SettlementReportNumber.ZERO, SettlementReportNumber.TEN,  "数据统计维度"),
                    new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.ELEVEN,  SettlementReportNumber.SIXTY_SIX,  "事后折扣【02】-收入调整（签收单）"),
                    new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.SIXTY_SEVEN,  SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_TWO, "事后折扣【02】-收入调整（反签收单）"),
                    new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_THREE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_NINE, "事后折扣【02】-特殊业务调整"),
                    new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_THREE, "事后折扣【01】-收入调整（签收单）"),
                    new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_FOUR, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY_SEVEN, "事后折扣【01】-收入调整（反签收单）"),
                    new HeaderRows(SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY_EIGHT, SettlementReportNumber.ONE_HUNDRED_AND_SIXTY_THREE, "事后折扣【01】-特殊业务调整"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ELEVEN,  SettlementReportNumber.SEVENTEEN,  "签收时始发应收未核销预先折调整"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.EIGHTEEN,  SettlementReportNumber.TWENTY_FOUR,  "签收时到达应收未核销预先折调整"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.TWENTY_FIVE,  SettlementReportNumber.THIRTY_ONE,  "签收时始发应收未核销预先折与实际折差额调整"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.THIRTY_TWO,  SettlementReportNumber.THIRTY_EIGHT,  "签收时到达应收未核销预先折与实际折差额调整"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.THIRTY_NINE,  SettlementReportNumber.FORTY_FIVE,  "签收时始发应收未核销实际折调整"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.FORTY_SIX,  SettlementReportNumber.FIFTY_TWO,  "签收时到达应收未核销实际折调整"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.FIFTY_THREE,  SettlementReportNumber.FIFTY_NINE,  "签收时始发应收已核销实际折调整"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.SIXTY,  SettlementReportNumber.SIXTY_SIX,  "签收时到达应收已核销实际折调整"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.SIXTY_SEVEN,  SettlementReportNumber.SEVENTY_THREE,  "反签收时始发应收未核销预先折调整"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.SEVENTY_FOUR,  SettlementReportNumber.EIGHTY,  "反签收时到达应收未核销预先折调整"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.EIGHTY_ONE,  SettlementReportNumber.EIGHTY_SEVEN,  "反签收时始发应收未核销预先折与实际折差额调整"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.EIGHTY_EIGHT,  SettlementReportNumber.NINETY_FOUR,  "反签收时到达应收未核销预先折与实际折差额调整"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.NINETY_FIVE,  SettlementReportNumber.ONE_HUNDRED_AND_ONE, "反签收时始发应收未核销实际折调整"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_TWO, SettlementReportNumber.ONE_HUNDRED_AND_EIGHT, "反签收时到达应收未核销实际折调整"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_NINE, SettlementReportNumber.ONE_HUNDRED_AND_FIFTEEN, "反签收时始发应收已核销实际折调整"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_SIXTEEN, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_TWO, "反签收时到达应收已核销实际折调整"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_THREE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_FOUR, "理赔-出发部门申请"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_FIVE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_FIVE, "理赔-到达部门申请"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_SIX, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_SEVEN, "异常冲收入"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_EIGHT, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_NINE, "弃货、违禁品、全票丢货"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY_SIX, "签收时始发应收未核销实际折调整"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY_SEVEN, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_THREE, "签收时始发应收已核销实际折调整"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_FOUR, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY, "反签收时始发应收未核销实际折调整"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY_ONE, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY_SEVEN, "反签收时始发应收已核销实际折调整"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY_EIGHT, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY_NINE, "理赔-出发部门申请"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_SIXTY, SettlementReportNumber.ONE_HUNDRED_AND_SIXTY, "理赔-到达部门申请"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_SIXTY_ONE, SettlementReportNumber.ONE_HUNDRED_AND_SIXTY_ONE, "异常冲收入"),
                    new HeaderRows(SettlementReportNumber.ONE, SettlementReportNumber.ONE, SettlementReportNumber.ONE_HUNDRED_AND_SIXTY_TWO, SettlementReportNumber.ONE_HUNDRED_AND_SIXTY_THREE, "弃货、违禁品、全票丢货"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ZERO, SettlementReportNumber.ZERO, "业务类型"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE, SettlementReportNumber.ONE, "发票标记"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.TWO, SettlementReportNumber.TWO, "客户名称"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.THREE, SettlementReportNumber.THREE, "客户编码"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.FOUR, SettlementReportNumber.FOUR, "统一结算类型"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.FIVE, SettlementReportNumber.FIVE, "部门名称"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.SIX, SettlementReportNumber.SIX, "部门编码"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.SEVEN, SettlementReportNumber.SEVEN, "部门名称"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.EIGHT, SettlementReportNumber.EIGHT, "部门编码"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.NINE, SettlementReportNumber.NINE, "合同部门名称"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.TEN,  SettlementReportNumber.TEN,  "合同部门编码"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ELEVEN,  SettlementReportNumber.ELEVEN,  "公布价运费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.TWELVE,  SettlementReportNumber.TWELVE,  "接货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.THIRTEEN,  SettlementReportNumber.THIRTEEN,  "送货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.FOURTEEN,  SettlementReportNumber.FOURTEEN,  "包装费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.FIFTEEN,  SettlementReportNumber.FIFTEEN,  "保价费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.SIXTEEN,  SettlementReportNumber.SIXTEEN,  "代收货款手续费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.SEVENTEEN,  SettlementReportNumber.SEVENTEEN,  "其他费用"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.EIGHTEEN,  SettlementReportNumber.EIGHTEEN,  "公布价运费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.NINETEEN,  SettlementReportNumber.NINETEEN,  "接货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.TWENTY,  SettlementReportNumber.TWENTY,  "送货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.TWENTY_ONE,  SettlementReportNumber.TWENTY_ONE,  "包装费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.TWENTY_TWO,  SettlementReportNumber.TWENTY_TWO,  "保价费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.TWENTY_THREE,  SettlementReportNumber.TWENTY_THREE,  "代收货款手续费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.TWENTY_FOUR,  SettlementReportNumber.TWENTY_FOUR,  "其他费用"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.TWENTY_FIVE,  SettlementReportNumber.TWENTY_FIVE,  "公布价运费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.TWENTY_SIX,  SettlementReportNumber.TWENTY_SIX,  "接货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.TWENTY_SEVEN,  SettlementReportNumber.TWENTY_SEVEN,  "送货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.TWENTY_EIGHT,  SettlementReportNumber.TWENTY_EIGHT,  "包装费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.TWENTY_NINE,  SettlementReportNumber.TWENTY_NINE,  "保价费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.THIRTY,  SettlementReportNumber.THIRTY,  "代收货款手续费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.THIRTY_ONE,  SettlementReportNumber.THIRTY_ONE,  "其他费用"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.THIRTY_TWO,  SettlementReportNumber.THIRTY_TWO,  "公布价运费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.THIRTY_THREE,  SettlementReportNumber.THIRTY_THREE,  "接货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.THIRTY_FOUR,  SettlementReportNumber.THIRTY_FOUR,  "送货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.THIRTY_FIVE,  SettlementReportNumber.THIRTY_FIVE,  "包装费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.THIRTY_SIX,  SettlementReportNumber.THIRTY_SIX,  "保价费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.THIRTY_SEVEN,  SettlementReportNumber.THIRTY_SEVEN,  "代收货款手续费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.THIRTY_EIGHT,  SettlementReportNumber.THIRTY_EIGHT,  "其他费用"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.THIRTY_NINE,  SettlementReportNumber.THIRTY_NINE,  "公布价运费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.FORTY,  SettlementReportNumber.FORTY,  "接货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.FORTY_ONE,  SettlementReportNumber.FORTY_ONE,  "送货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.FORTY_TWO,  SettlementReportNumber.FORTY_TWO,  "包装费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.FORTY_THREE,  SettlementReportNumber.FORTY_THREE,  "保价费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.FORTY_FOUR,  SettlementReportNumber.FORTY_FOUR,  "代收货款手续费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.FORTY_FIVE,  SettlementReportNumber.FORTY_FIVE,  "其他费用"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.FORTY_SIX,  SettlementReportNumber.FORTY_SIX,  "公布价运费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.FORTY_SEVEN,  SettlementReportNumber.FORTY_SEVEN,  "接货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.FORTY_EIGHT,  SettlementReportNumber.FORTY_EIGHT,  "送货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.FORTY_NINE,  SettlementReportNumber.FORTY_NINE,  "包装费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.FIFTY,  SettlementReportNumber.FIFTY,  "保价费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.FIFTY_ONE,  SettlementReportNumber.FIFTY_ONE,  "代收货款手续费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.FIFTY_TWO,  SettlementReportNumber.FIFTY_TWO,  "其他费用"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.FIFTY_THREE,  SettlementReportNumber.FIFTY_THREE,  "公布价运费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.FIFTY_FOUR,  SettlementReportNumber.FIFTY_FOUR,  "接货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.FIFTY_FIVE,  SettlementReportNumber.FIFTY_FIVE,  "送货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.FIFTY_SIX,  SettlementReportNumber.FIFTY_SIX,  "包装费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.FIFTY_SEVEN,  SettlementReportNumber.FIFTY_SEVEN,  "保价费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.FIFTY_EIGHT,  SettlementReportNumber.FIFTY_EIGHT,  "代收货款手续费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.FIFTY_NINE,  SettlementReportNumber.FIFTY_NINE,  "其他费用"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.SIXTY,  SettlementReportNumber.SIXTY,  "公布价运费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.SIXTY_ONE,  SettlementReportNumber.SIXTY_ONE,  "接货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.SIXTY_TWO,  SettlementReportNumber.SIXTY_TWO,  "送货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.SIXTY_THREE,  SettlementReportNumber.SIXTY_THREE,  "包装费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.SIXTY_FOUR,  SettlementReportNumber.SIXTY_FOUR,  "保价费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.SIXTY_FIVE,  SettlementReportNumber.SIXTY_FIVE,  "代收货款手续费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.SIXTY_SIX,  SettlementReportNumber.SIXTY_SIX,  "其他费用"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.SIXTY_SEVEN,  SettlementReportNumber.SIXTY_SEVEN,  "公布价运费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.SIXTY_EIGHT,  SettlementReportNumber.SIXTY_EIGHT,  "接货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.SIXTY_NINE,  SettlementReportNumber.SIXTY_NINE,  "送货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.SEVENTY,  SettlementReportNumber.SEVENTY,  "包装费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.SEVENTY_ONE,  SettlementReportNumber.SEVENTY_ONE,  "保价费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.SEVENTY_TWO,  SettlementReportNumber.SEVENTY_TWO,  "代收货款手续费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.SEVENTY_THREE,  SettlementReportNumber.SEVENTY_THREE,  "其他费用"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.SEVENTY_FOUR,  SettlementReportNumber.SEVENTY_FOUR,  "公布价运费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.SEVENTY_FIVE,  SettlementReportNumber.SEVENTY_FIVE,  "接货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.SEVENTY_SIX,  SettlementReportNumber.SEVENTY_SIX,  "送货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.SEVENTY_SEVEN,  SettlementReportNumber.SEVENTY_SEVEN,  "包装费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.SEVENTY_EIGHT,  SettlementReportNumber.SEVENTY_EIGHT,  "保价费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.SEVENTY_NINE,  SettlementReportNumber.SEVENTY_NINE,  "代收货款手续费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.EIGHTY,  SettlementReportNumber.EIGHTY,  "其他费用"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.EIGHTY_ONE,  SettlementReportNumber.EIGHTY_ONE,  "公布价运费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.EIGHTY_TWO,  SettlementReportNumber.EIGHTY_TWO,  "接货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.EIGHTY_THREE,  SettlementReportNumber.EIGHTY_THREE,  "送货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.EIGHTY_FOUR,  SettlementReportNumber.EIGHTY_FOUR,  "包装费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.EIGHTY_FIVE,  SettlementReportNumber.EIGHTY_FIVE,  "保价费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.EIGHTY_SIX,  SettlementReportNumber.EIGHTY_SIX,  "代收货款手续费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.EIGHTY_SEVEN,  SettlementReportNumber.EIGHTY_SEVEN,  "其他费用"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.EIGHTY_EIGHT,  SettlementReportNumber.EIGHTY_EIGHT,  "公布价运费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.EIGHTY_NINE,  SettlementReportNumber.EIGHTY_NINE,  "接货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.NINETY,  SettlementReportNumber.NINETY,  "送货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.NINETY_ONE,  SettlementReportNumber.NINETY_ONE,  "包装费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.NINETY_TWO,  SettlementReportNumber.NINETY_TWO,  "保价费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.NINETY_THREE,  SettlementReportNumber.NINETY_THREE,  "代收货款手续费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.NINETY_FOUR,  SettlementReportNumber.NINETY_FOUR,  "其他费用"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.NINETY_FIVE,  SettlementReportNumber.NINETY_FIVE,  "公布价运费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.NINETY_SIX,  SettlementReportNumber.NINETY_SIX,  "接货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.NINETY_SEVEN,  SettlementReportNumber.NINETY_SEVEN,  "送货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.NINETY_EIGHT,  SettlementReportNumber.NINETY_EIGHT,  "包装费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.NINETY_NINE,  SettlementReportNumber.NINETY_NINE,  "保价费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED, SettlementReportNumber.ONE_HUNDRED, "代收货款手续费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_ONE, SettlementReportNumber.ONE_HUNDRED_AND_ONE, "其他费用"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_TWO, SettlementReportNumber.ONE_HUNDRED_AND_TWO, "公布价运费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_THREE, SettlementReportNumber.ONE_HUNDRED_AND_THREE, "接货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_FOUR, SettlementReportNumber.ONE_HUNDRED_AND_FOUR, "送货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_FIVE, SettlementReportNumber.ONE_HUNDRED_AND_FIVE, "包装费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_SIX, SettlementReportNumber.ONE_HUNDRED_AND_SIX, "保价费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_SEVEN, SettlementReportNumber.ONE_HUNDRED_AND_SEVEN, "代收货款手续费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_EIGHT, SettlementReportNumber.ONE_HUNDRED_AND_EIGHT, "其他费用"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_NINE, SettlementReportNumber.ONE_HUNDRED_AND_NINE, "公布价运费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_TEN, SettlementReportNumber.ONE_HUNDRED_AND_TEN, "接货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_ELEVEN, SettlementReportNumber.ONE_HUNDRED_AND_ELEVEN, "送货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_TWELVE, SettlementReportNumber.ONE_HUNDRED_AND_TWELVE, "包装费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_THIRTEEN, SettlementReportNumber.ONE_HUNDRED_AND_THIRTEEN, "保价费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_FOURTEEN, SettlementReportNumber.ONE_HUNDRED_AND_FOURTEEN, "代收货款手续费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_FIFTEEN, SettlementReportNumber.ONE_HUNDRED_AND_FIFTEEN, "其他费用"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_SIXTEEN, SettlementReportNumber.ONE_HUNDRED_AND_SIXTEEN, "公布价运费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_SEVENTEEN, SettlementReportNumber.ONE_HUNDRED_AND_SEVENTEEN, "接货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_EIGHTEEN, SettlementReportNumber.ONE_HUNDRED_AND_EIGHTEEN, "送货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_NINETEEN, SettlementReportNumber.ONE_HUNDRED_AND_NINETEEN, "包装费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY, "保价费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_ONE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_ONE, "代收货款手续费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_TWO, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_TWO, "其他费用"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_THREE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_THREE, "02理赔冲收入调整"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_FOUR, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_FOUR, "02理赔入成本调整"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_FIVE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_FIVE, "02理赔冲收入调整"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_SIX, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_SIX, "02应收始发运费已签收调整"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_SEVEN, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_SEVEN, "02应收到付运费已签收调整"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_EIGHT, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_EIGHT, "开单且为月结临时欠款网上支付未核销"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_NINE, SettlementReportNumber.ONE_HUNDRED_AND_TWENTY_NINE, "开单且为月结临时欠款网上支付已核销"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY, "公布价运费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY_ONE, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY_ONE, "接货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY_TWO, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY_TWO, "送货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY_THREE, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY_THREE, "包装费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY_FOUR, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY_FOUR, "保价费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY_FIVE, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY_FIVE, "代收货款手续费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY_SIX, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY_SIX, "其他费用"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY_SEVEN, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY_SEVEN, "公布价运费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY_EIGHT, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY_EIGHT, "接货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY_NINE, SettlementReportNumber.ONE_HUNDRED_AND_THIRTY_NINE, "送货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_FORTY, SettlementReportNumber.ONE_HUNDRED_AND_FORTY, "包装费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_ONE, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_ONE, "保价费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_TWO, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_TWO, "代收货款手续费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_THREE, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_THREE, "其他费用"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_FOUR, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_FOUR, "公布价运费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_FIVE, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_FIVE, "接货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_SIX, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_SIX, "送货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_SEVEN, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_SEVEN, "包装费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_EIGHT, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_EIGHT, "保价费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_NINE, SettlementReportNumber.ONE_HUNDRED_AND_FORTY_NINE, "代收货款手续费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY, "其他费用"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY_ONE, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY_ONE, "公布价运费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY_TWO, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY_TWO, "接货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY_THREE, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY_THREE, "送货费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY_FOUR, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY_FOUR, "包装费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY_FIVE, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY_FIVE, "保价费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY_SIX, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY_SIX, "代收货款手续费"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY_SEVEN, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY_SEVEN, "其他费用"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY_EIGHT, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY_EIGHT, "01理赔冲收入调整"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY_NINE, SettlementReportNumber.ONE_HUNDRED_AND_FIFTY_NINE, "01理赔入成本调整"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_SIXTY, SettlementReportNumber.ONE_HUNDRED_AND_SIXTY, "01理赔冲收入调整"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_SIXTY_ONE, SettlementReportNumber.ONE_HUNDRED_AND_SIXTY_ONE, "02应收始发运费已签收调整"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_SIXTY_TWO, SettlementReportNumber.ONE_HUNDRED_AND_SIXTY_TWO, "开单且为月结临时欠款网上支付未核销"),
                    new HeaderRows(SettlementReportNumber.TWO, SettlementReportNumber.THREE, SettlementReportNumber.ONE_HUNDRED_AND_SIXTY_THREE, SettlementReportNumber.ONE_HUNDRED_AND_SIXTY_THREE, "开单且为月结临时欠款网上支付已核销")
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
                "期间","发票标记", "运输性质", "客户编码", "客户名称", "始发部门编码", "始发部门名称", "始发部门标杆编码",
                "到达部门编码", "到达部门名称", "到达部门标杆编码", "统一结算类型",  "合同部门编码", "合同部门名称",
                "02签收时始发应收未核销预先折调整_公布价运费",
                "02签收时始发应收未核销预先折调整_接货费",
                "02签收时始发应收未核销预先折调整_送货费",
                "02签收时始发应收未核销预先折调整_包装费",
                "02签收时始发应收未核销预先折调整_保价费",
                "02签收时始发应收未核销预先折调整_代收货款手续费",
                "02签收时始发应收未核销预先折调整_其他费用",
                "02签收时到达应收未核销预先折调整_公布价运费",
                "02签收时到达应收未核销预先折调整_接货费",
                "02签收时到达应收未核销预先折调整_送货费",
                "02签收时到达应收未核销预先折调整_包装费",
                "02签收时到达应收未核销预先折调整_保价费",
                "02签收时到达应收未核销预先折调整_代收货款手续费",
                "02签收时到达应收未核销预先折调整_其他费用",
                "02签收时始发应收未核销预先折与实际折差额调整_公布价运费",
                "02签收时始发应收未核销预先折与实际折差额调整_接货费",
                "02签收时始发应收未核销预先折与实际折差额调整_送货费",
                "02签收时始发应收未核销预先折与实际折差额调整_包装费",
                "02签收时始发应收未核销预先折与实际折差额调整_保价费",
                "02签收时始发应收未核销预先折与实际折差额调整_代收货款手续费",
                "02签收时始发应收未核销预先折与实际折差额调整_其他费用",
                "02签收时到达应收未核销预先折与实际折差额调整_公布价运费",
                "02签收时到达应收未核销预先折与实际折差额调整_接货费",
                "02签收时到达应收未核销预先折与实际折差额调整_送货费",
                "02签收时到达应收未核销预先折与实际折差额调整_包装费",
                "02签收时到达应收未核销预先折与实际折差额调整_保价费",
                "02签收时到达应收未核销预先折与实际折差额调整_代收货款手续费",
                "02签收时到达应收未核销预先折与实际折差额调整_其他费用",
                "02签收时始发应收未核销实际折调整_公布价运费",
                "02签收时始发应收未核销实际折调整_接货费",
                "02签收时始发应收未核销实际折调整_送货费",
                "02签收时始发应收未核销实际折调整_包装费",
                "02签收时始发应收未核销实际折调整_保价费",
                "02签收时始发应收未核销实际折调整_代收货款手续费",
                "02签收时始发应收未核销实际折调整_其他费用",
                "02签收时到达应收未核销实际折调整_公布价运费",
                "02签收时到达应收未核销实际折调整_接货费",
                "02签收时到达应收未核销实际折调整_送货费",
                "02签收时到达应收未核销实际折调整_包装费",
                "02签收时到达应收未核销实际折调整_保价费",
                "02签收时到达应收未核销实际折调整_代收货款手续费",
                "02签收时到达应收未核销实际折调整_其他费用",
                "02签收时始发应收已核销实际折调整_公布价运费",
                "02签收时始发应收已核销实际折调整_接货费",
                "02签收时始发应收已核销实际折调整_送货费",
                "02签收时始发应收已核销实际折调整_包装费",
                "02签收时始发应收已核销实际折调整_保价费",
                "02签收时始发应收已核销实际折调整_代收货款手续费",
                "02签收时始发应收已核销实际折调整_其他费用",
                "02签收时到达应收已核销实际折调整_公布价运费",
                "02签收时到达应收已核销实际折调整_接货费",
                "02签收时到达应收已核销实际折调整_送货费",
                "02签收时到达应收已核销实际折调整_包装费",
                "02签收时到达应收已核销实际折调整_保价费",
                "02签收时到达应收已核销实际折调整_代收货款手续费",
                "02签收时到达应收已核销实际折调整_其他费用",
                "02反签收时始发应收未核销预先折调整_公布价运费",
                "02反签收时始发应收未核销预先折调整_接货费",
                "02反签收时始发应收未核销预先折调整_送货费",
                "02反签收时始发应收未核销预先折调整_包装费",
                "02反签收时始发应收未核销预先折调整_保价费",
                "02反签收时始发应收未核销预先折调整_代收货款手续费",
                "02反签收时始发应收未核销预先折调整_其他费用",
                "02反签收时到达应收未核销预先折调整_公布价运费",
                "02反签收时到达应收未核销预先折调整_接货费",
                "02反签收时到达应收未核销预先折调整_送货费",
                "02反签收时到达应收未核销预先折调整_包装费",
                "02反签收时到达应收未核销预先折调整_保价费",
                "02反签收时到达应收未核销预先折调整_代收货款手续费",
                "02反签收时到达应收未核销预先折调整_其他费用",
                "02反签收时始发应收未核销预先折与实际折差额调整_公布价运费",
                "02反签收时始发应收未核销预先折与实际折差额调整_接货费",
                "02反签收时始发应收未核销预先折与实际折差额调整_送货费",
                "02反签收时始发应收未核销预先折与实际折差额调整_包装费",
                "02反签收时始发应收未核销预先折与实际折差额调整_保价费",
                "02反签收时始发应收未核销预先折与实际折差额调整_代收货款手续费",
                "02反签收时始发应收未核销预先折与实际折差额调整_其他费用",
                "02反签收时到达应收未核销预先折与实际折差额调整_公布价运费",
                "02反签收时到达应收未核销预先折与实际折差额调整_接货费",
                "02反签收时到达应收未核销预先折与实际折差额调整_送货费",
                "02反签收时到达应收未核销预先折与实际折差额调整_包装费",
                "02反签收时到达应收未核销预先折与实际折差额调整_保价费",
                "02反签收时到达应收未核销预先折与实际折差额调整_代收货款手续费",
                "02反签收时到达应收未核销预先折与实际折差额调整_其他费用",
                "02反签收时始发应收未核销实际折调整_公布价运费",
                "02反签收时始发应收未核销实际折调整_接货费",
                "02反签收时始发应收未核销实际折调整_送货费",
                "02反签收时始发应收未核销实际折调整_包装费",
                "02反签收时始发应收未核销实际折调整_保价费",
                "02反签收时始发应收未核销实际折调整_代收货款手续费",
                "02反签收时始发应收未核销实际折调整_其他费用",
                "02反签收时到达应收未核销实际折调整_公布价运费",
                "02反签收时到达应收未核销实际折调整_接货费",
                "02反签收时到达应收未核销实际折调整_送货费",
                "02反签收时到达应收未核销实际折调整_包装费",
                "02反签收时到达应收未核销实际折调整_保价费",
                "02反签收时到达应收未核销实际折调整_代收货款手续费",
                "02反签收时到达应收未核销实际折调整_其他费用",
                "02反签收时始发应收已核销实际折调整_公布价运费",
                "02反签收时始发应收已核销实际折调整_接货费",
                "02反签收时始发应收已核销实际折调整_送货费",
                "02反签收时始发应收已核销实际折调整_包装费",
                "02反签收时始发应收已核销实际折调整_保价费",
                "02反签收时始发应收已核销实际折调整_代收货款手续费",
                "02反签收时始发应收已核销实际折调整_其他费用",
                "02反签收时到达应收已核销实际折调整_公布价运费",
                "02反签收时到达应收已核销实际折调整_接货费",
                "02反签收时到达应收已核销实际折调整_送货费",
                "02反签收时到达应收已核销实际折调整_包装费",
                "02反签收时到达应收已核销实际折调整_保价费",
                "02反签收时到达应收已核销实际折调整_代收货款手续费",
                "02反签收时到达应收已核销实际折调整_其他费用",
                "02理赔-出发部门申请-02理赔冲收入调整",
                "02理赔-出发部门申请-02理赔入成本调整",
                "02理赔-到达部门申请",
                "02异常冲收入-02应收始发运费已签收调整",
                "02异常冲收入-02应收到付运费已签收调整",
                "02特殊业务调整-弃货、违禁品、全票丢货-开单且为月结临时欠款网上支付未核销",
                "02特殊业务调整-弃货、违禁品、全票丢货-开单且为月结临时欠款网上支付已核销",
                "01签收时始发应收未核销实际折调整-公布价运费",
                "01签收时始发应收未核销实际折调整-接货费",
                "01签收时始发应收未核销实际折调整-送货费",
                "01签收时始发应收未核销实际折调整-包装费",
                "01签收时始发应收未核销实际折调整-保价费",
                "01签收时始发应收未核销实际折调整-代收货款手续费",
                "01签收时始发应收未核销实际折调整-其他费用",
                "01签收时始发应收已核销实际折调整-公布价运费",
                "01签收时始发应收已核销实际折调整-接货费",
                "01签收时始发应收已核销实际折调整-送货费",
                "01签收时始发应收已核销实际折调整-包装费",
                "01签收时始发应收已核销实际折调整-保价费",
                "01签收时始发应收已核销实际折调整-代收货款手续费",
                "01签收时始发应收已核销实际折调整-其他费用",
                "01反签收时始发应收未核销实际折调整-公布价运费",
                "01反签收时始发应收未核销实际折调整-接货费",
                "01反签收时始发应收未核销实际折调整-送货费",
                "01反签收时始发应收未核销实际折调整-包装费",
                "01反签收时始发应收未核销实际折调整-保价费",
                "01反签收时始发应收未核销实际折调整-代收货款手续费",
                "01反签收时始发应收未核销实际折调整-其他费用",
                "01反签收时始发应收已核销实际折调整-公布价运费",
                "01反签收时始发应收已核销实际折调整-接货费",
                "01反签收时始发应收已核销实际折调整-送货费",
                "01反签收时始发应收已核销实际折调整-包装费",
                "01反签收时始发应收已核销实际折调整-保价费",
                "01反签收时始发应收已核销实际折调整-代收货款手续费",
                "01反签收时始发应收已核销实际折调整-其他费用",
                "01特殊业务调整-理赔-出发部门申请-01理赔冲收入调整",
                "01特殊业务调整-理赔-出发部门申请-01理赔入成本调整",
                "01特殊业务调整-理赔-到达部门申请-01理赔冲收入调整",
                "01特殊业务调整-异常冲收入-01应收始发运费已签收调整",
                "01特殊业务调整-弃货、违禁品、全票丢货-开单且为月结临时欠款网上支付未核销",
                "01特殊业务调整-弃货、违禁品、全票丢货-开单且为月结临时欠款网上支付已核销"

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
    private Object[] exportDataAndType(List<MvrDcoEntity> queryList) {

        String[] properties = new String[]{
                "period",
                "invoiceMark", "productCode",
                "customerCode", "customerName", "origOrgCode", "origOrgName",
                "origUnifiedCode", "destOrgCode", "destOrgName",
                "destUnifiedCode", "unifiedSettlementType", "contractOrgCode", "contractOrgName",
                "dcOrigPredctPodTransport",
                "dcOrigPredctPodPickup",
                "dcOrigPredctPodDelivery",
                "dcOrigPredctPodPackaging",
                "dcOrigPredctPodCod",
                "dcOrigPredctPodInsurance",
                "dcOrigPredctPodOther",
                "dcDestPredctPodTransport",
                "dcDestPredctPodPickup",
                "dcDestPredctPodDelivery",
                "dcDestPredctPodPackaging",
                "dcDestPredctPodCod",
                "dcDestPredctPodInsurance",
                "dcDestPredctPodOther",
                "dcOrigDctDifPodTransport",
                "dcOrigDctDifPodPickup",
                "dcOrigDctDifPodDelivery",
                "dcOrigDctDifPodPackaging",
                "dcOrigDctDifPodCod",
                "dcOrigDctDifPodInsurance",
                "dcOrigDctDifPodOther",
                "dcDestDctDifPodTransport",
                "dcDestDctDifPodPickup",
                "dcDestDctDifPodDelivery",
                "dcDestDctDifPodPackaging",
                "dcDestDctDifPodCod",
                "dcDestDctDifPodInsurance",
                "dcDestDctDifPodOther",
                "dcOrigActdctPodTransport",
                "dcOrigActdctPodPickup",
                "dcOrigActdctPodDelivery",
                "dcOrigActdctPodPackaging",
                "dcOrigActdctPodCod",
                "dcOrigActdctPodInsurance",
                "dcOrigActdctPodOther",
                "dcDestActdctPodTransport",
                "dcDestActdctPodPickup",
                "dcDestActdctPodDelivery",
                "dcDestActdctPodPackaging",
                "dcDestActdctPodCod",
                "dcDestActdctPodInsurance",
                "dcDestActdctPodOther",
                "dcOrigActdctNpodTransport",
                "dcOrigActdctNpodPickup",
                "dcOrigActdctNpodDelivery",
                "dcOrigActdctNpodPackaging",
                "dcOrigActdctNpodCod",
                "dcOrigActdctNpodInsurance",
                "dcOrigActdctNpodOther",
                "dcDestActdctNpodTransport",
                "dcDestActdctNpodPickup",
                "dcDestActdctNpodDelivery",
                "dcDestActdctNpodPackaging",
                "dcDestActdctNpodCod",
                "dcDestActdctNpodInsurance",
                "dcDestActdctNpodOther",
                "dcOrigPredctRpodTransport",
                "dcOrigPredctRpodPickup",
                "dcOrigPredctRpodDelivery",
                "dcOrigPredctRpodPackaging",
                "dcOrigPredctRpodCod",
                "dcOrigPredctRpodInsurance",
                "dcOrigPredctRpodOther",
                "dcDestPredctRpodTransport",
                "dcDestPredctRpodPickup",
                "dcDestPredctRpodDelivery",
                "dcDestPredctRpodPackaging",
                "dcDestPredctRpodCod",
                "dcDestPredctRpodInsurance",
                "dcDestPredctRpodOther",
                "dcOrigDctDifRpodTransport",
                "dcOrigDctDifRpodPickup",
                "dcOrigDctDifRpodDelivery",
                "dcOrigDctDifRpodPackaging",
                "dcOrigDctDifRpodCod",
                "dcOrigDctDifRpodInsurance",
                "dcOrigDctDifRpodOther",
                "dcDestDctDifRpodTransport",
                "dcDestDctDifRpodPickup",
                "dcDestDctDifRpodDelivery",
                "dcDestDctDifRpodPackaging",
                "dcDestDctDifRpodCod",
                "dcDestDctDifRpodInsurance",
                "dcDestDctDifRpodOther",
                "dcOrigActdctRpodTransport",
                "dcOrigActdctRpodPickup",
                "dcOrigActdctRpodDelivery",
                "dcOrigActdctRpodPackaging",
                "dcOrigActdctRpodCod",
                "dcOrigActdctRpodInsurance",
                "dcOrigActdctRpodOther",
                "dcDestActdctRpodTransport",
                "dcDestActdctRpodPickup",
                "dcDestActdctRpodDelivery",
                "dcDestActdctRpodPackaging",
                "dcDestActdctRpodCod",
                "dcDestActdctRpodInsurance",
                "dcDestActdctRpodOther",
                "dcOrigActdctRnpodTransport",
                "dcOrigActdctRnpodPickup",
                "dcOrigActdctRnpodDelivery",
                "dcOrigActdctRnpodPackaging",
                "dcOrigActdctRnpodCod",
                "dcOrigActdctRnpodInsurance",
                "dcOrigActdctRnpodOther",
                "dcDestActdctRnpodTransport",
                "dcDestActdctRnpodPickup",
                "dcDestActdctRnpodDelivery",
                "dcDestActdctRnpodPackaging",
                "dcDestActdctRnpodCod",
                "dcDestActdctRnpodInsurance",
                "dcDestActdctRnpodOther",
                "dcOrigIncomet",
                "dcOrigCostt",
                "dcDestApplyT",
                "dcExOrigRcvtPod",
                "dcExDestRcvtPod",
                "ldcExtOrNwo",
                "ldcExtOrWo",
                "ldcOroNwoActPodTransport",
                "ldcOroNwoActPodPickup",
                "ldcOroNwoActPodDelivery",
                "ldcOroNwoActPodPackaging",
                "ldcOroNwoActPodInsurance",
                "ldcOroNwoActPodCod",
                "ldcOroNwoActPodOther",
                "ldcOroWoActPodTransport",
                "ldcOroWoActPodPickup",
                "ldcOroWoActPodDelivery",
                "ldcOroWoActPodPackaging",
                "ldcOroWoActPodInsurance",
                "ldcOroWoActPodCod",
                "ldcOroWoActPodOther",
                "ldcOroNwoActRpodTransport",
                "ldcOroNwoActRpodPickup",
                "ldcOroNwoActRpodDelivery",
                "ldcOroNwoActRpodPackaging",
                "ldcOroNwoActRpodInsurance",
                "ldcOroNwoActRpodCod",
                "ldcOroNwoActRpodOther",
                "ldcOroWoActRpodTransport",
                "ldcOroWoActRpodPickup",
                "ldcOroWoActRpodDelivery",
                "ldcOroWoActRpodPackaging",
                "ldcOroWoActRpodInsurance",
                "ldcOroWoActRpodCod",
                "ldcOroWoActRpodOther",
                "ldcOrigIncomeo",
                "ldcOrigCosto",
                "ldcDestIncomeo",
                "ldcExoOrPod",
                "ldcExoOrNwo",
                "ldcExoOrWo"};

        List<String> strProperties = Arrays.asList(
                "period","invoiceMark", "productCode",
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
        for (MvrDcoEntity entity : queryList) {

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

    public MvrDcoVo getVo() {
        return vo;
    }

    public void setVo(MvrDcoVo vo) {
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

    public void setMvrDcoService(IMvrDcoService mvrDcoService) {
        this.mvrDcoService = mvrDcoService;
    }

}
