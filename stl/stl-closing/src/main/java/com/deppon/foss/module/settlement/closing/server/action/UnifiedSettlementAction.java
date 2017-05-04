package com.deppon.foss.module.settlement.closing.server.action;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.settlement.closing.api.shared.domain.UnifiedSettlementEntity;
import com.deppon.foss.module.settlement.closing.api.shared.vo.UnifiedSettlementVo;
import com.deppon.foss.module.settlement.closing.server.service.impl.UnifiedSettlementService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by 073615 on 2014/11/17.
 */
public class UnifiedSettlementAction extends AbstractAction{
    private static final Logger LOGGER = LogManager.getLogger(UnifiedSettlementAction.class);
    /**
     * 统一结算service
     */
    private  UnifiedSettlementService unifiedSettlementService;
    /**
     *统一结算查询dto
     */
    private UnifiedSettlementVo vo;

    private String excelName ;

    private InputStream inputStream;

    /**
     * 根据条件查询统一结算数据
     * @return
     */
    @JSON
    public String queryByConditions(){

        if(StringUtils.isBlank(vo.getDto().getPeriodId())){
            throw new SettlementException("查询期间为必选项");
        }
        int rowCount = unifiedSettlementService.queryByConditionCount(vo.getDto());
        if(rowCount>0){
        List<UnifiedSettlementEntity> list = unifiedSettlementService.queryBycondition(vo.getDto());
        if(list!= null && list.size()>0){
            UnifiedSettlementEntity sumEntity = unifiedSettlementService.queryByConditionSum(vo.getDto());
            sumEntity.setPeriod("汇总");
            list.add(sumEntity);
        }
        vo.getDto().setList(list);
        }else{
            vo.getDto().setList(null);
        }
        totalCount = (long)rowCount;
        return returnSuccess();
    }

    /**
     * 导出统一结算数据
     * @return
     */
    public String exportDataExcel(){
        LOGGER.info("统一结算导出开始");
        //设置文件名称
        String excelName = "统一结算"+vo.getDto().getPeriodId();
        try {
            this.setExcelName(new String(excelName.getBytes(SettlementConstants.UNICODE_GBK),
                    SettlementConstants.UNICODE_ISO));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e);
            return returnError("EXCEL 导出失败"+e.getMessage());
        }
        //导出流
        try{
        inputStream = unifiedSettlementService.exportUnifiedSettlementExcel(vo.getDto());
        LOGGER.info("统一结算导出完成");
        }catch (BusinessException e ){
            return returnError(e.getErrorCode());
        }
        return returnSuccess();
    }

    public void setUnifiedSettlementService(UnifiedSettlementService unifiedSettlementService) {
        this.unifiedSettlementService = unifiedSettlementService;
    }

    public UnifiedSettlementVo getVo() {
        return vo;
    }

    public void setVo(UnifiedSettlementVo vo) {
        this.vo = vo;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }
}
