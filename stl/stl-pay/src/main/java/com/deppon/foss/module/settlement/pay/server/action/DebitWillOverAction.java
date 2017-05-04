package com.deppon.foss.module.settlement.pay.server.action;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DebitWillOverEntity;
import com.deppon.foss.module.settlement.pay.api.shared.vo.DebitWillOverVo;
import com.deppon.foss.module.settlement.pay.server.service.impl.DebitWillOverService;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 073615 on 2014/12/26.
 */
public class DebitWillOverAction extends AbstractAction {

    private static final Logger LOGGER = Logger.getLogger(DebitWillOverAction.class);
    /**
     * 处理数据服务
     */
    private DebitWillOverService debitWillOverService;
    /**
     * 参数及返回结果
     */
    private DebitWillOverVo vo;
    /**
     * Excel 名称
     */
    private String excelName;

    private InputStream inputStream;

    /**
     * 通过条件查询
     */
    @JSON
    public String queryDebitOverByCondition(){
        List<DebitWillOverEntity> list = debitWillOverService.queryByDebitInfobyPages(vo.getDto(),this.getStart(),this.getLimit());
        long rowCount = debitWillOverService.queryDebitCount(vo.getDto());
        this.totalCount = rowCount;
        vo.setList(list);
        return returnSuccess();
    }

    /**
     * 根据条件导出数据
     */
    @JSON
    public String exportExcel(){
        Date currentDate = new Date();
        String date = new SimpleDateFormat("yyyy-MM-dd ").format(currentDate);

        String excelName = "结算天数少于10天"+date;
        try {
            this.setExcelName(new String(excelName.getBytes(SettlementConstants.UNICODE_GBK),
                    SettlementConstants.UNICODE_ISO));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e);
            return returnError("EXCEL 导出失败" + e.getMessage());
        }
        //导出流
        inputStream = debitWillOverService.exportDebitInfo (vo.getDto());
        LOGGER.info("结算天数少于10天导出完成");
        return returnSuccess();

    }

    public void setDebitWillOverService(DebitWillOverService debitWillOverService) {
        this.debitWillOverService = debitWillOverService;
    }

    public void setVo(DebitWillOverVo vo) {
        this.vo = vo;
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

    public DebitWillOverVo getVo() {
        return vo;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

}
