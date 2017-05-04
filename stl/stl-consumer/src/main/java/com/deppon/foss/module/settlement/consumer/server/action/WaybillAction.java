package com.deppon.foss.module.settlement.consumer.server.action;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybill4FIMSService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.WaybillDetailEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.WaybillVo;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 322906 on 2016/6/15.
 * 查询运单,合并运单 和  查询合并运单的action
 */
public class WaybillAction extends AbstractAction {

    private static final long serialVersionUID = -3295250685053948950L;

    /** 声明日志对象. */
    private static final Logger LOGGER = LogManager.getLogger(WaybillAction.class);

    private WaybillVo vo;

    private IWaybill4FIMSService waybill4FIMSService;


    /**
     * 根据客户编码获取客户信息
     */
    @JSON
    public String queryCustomerInfoFromCrm() {
        try {
            String[] customerCodes = vo.getWaybillQueryDto().getCustomerCodes();//客户编码
            List<String> customerCodeList = new ArrayList<String>();
            for(int i=0;i<customerCodes.length;i++){
                customerCodeList.add(customerCodes[i]);
            }

            Map<String,String> map = waybill4FIMSService.getTaxPayerInfo(customerCodeList);
            vo.setTaxPayer(map);
            return returnSuccess();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage(), e);
            return returnError(e);
        }
    }

    /**
     * 根据条件运单查询
     */
    @JSON
    public String queryWaybillByCondition(){
        CurrentInfo currentInfo = FossUserContext.getCurrentInfo();

        if(vo==null || vo.getWaybillQueryDto()==null){
            returnError("查询参数为空");
        }
        WaybillQueryDto waybillQueryDto = vo.getWaybillQueryDto();
        if(waybillQueryDto.getPaidMethods()[0].equals("")){
            waybillQueryDto.setPaidMethods(null);
        }
        waybillQueryDto.setEmpCode(currentInfo.getEmpCode());

        try{
            //查询出数据的总条数
            int count = waybill4FIMSService.queryWaybillByConditionsCount(waybillQueryDto);
            //分页查询
            List<WaybillDetailEntity> waybillDetailEntityList = waybill4FIMSService.queryWaybillByConditions(waybillQueryDto,currentInfo,start, limit);

            vo.setWaybillDetailEntityList(waybillDetailEntityList);//返回给页面的运单集合
            this.setTotalCount((long)count);//设置总条数

            return returnSuccess();
        }catch (BusinessException e){
            LOGGER.error("发票合并新增查询运单异常" + e.getMessage(), e);
            return returnError(e);
        }
    }
    @JSON
    public String mergeWaybill(){
        if(null==vo||null==vo.getWaybillQueryDto()||null==vo.getWaybillQueryDto().getWaybillNos()){
        	returnError("查询参数为空");
        }
        WaybillQueryDto waybillQueryDto = vo.getWaybillQueryDto();
        List<String> waybillNoList = new ArrayList<String>();
        String[] waybillNos = waybillQueryDto.getWaybillNos();
        for(int i=0;i<waybillNos.length;i++){
            waybillNoList.add(waybillNos[i]);
        }
        if(waybillNoList!=null){
			if (waybillNoList.size() < SettlementReportNumber.ONE_HUNDRED_AND_FIFTY
					|| waybillNoList.size() > SettlementReportNumber.ONE_THOUSAND) {
				returnError("运单数量必须超过150个且小于1000");
			}
        }

        try {
            //合并操作
            waybill4FIMSService.mergeWaybill(waybillQueryDto);
            return returnSuccess();
        }catch (BusinessException e){
            LOGGER.error(e.getMessage(),e);
            return returnError(e);
        }
    }


    public void setWaybill4FIMSService(IWaybill4FIMSService waybill4FIMSService) {
        this.waybill4FIMSService = waybill4FIMSService;
    }

    public void setVo(WaybillVo vo) {
        this.vo = vo;
    }

    public WaybillVo getVo() {
        return vo;
    }
}
