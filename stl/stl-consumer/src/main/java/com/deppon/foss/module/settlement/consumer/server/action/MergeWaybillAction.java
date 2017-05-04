package com.deppon.foss.module.settlement.consumer.server.action;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IMergeWaybillService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.WaybillDetailEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.MergeWaybillDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.MergeWaybillQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.MergeWaybillVo;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by 322906 on 2016/6/22.
 */
public class MergeWaybillAction extends AbstractAction {
    private static final long serialVersionUID = -3295250685053948950L;

    /** 声明日志对象. */
    private static final Logger LOGGER = LogManager.getLogger(WaybillAction.class);

    private MergeWaybillVo vo;

    private IMergeWaybillService mergeWaybillService;


    /**
     * 发票合并运单管理
     * 查询合并运单
     * @return
     */
    @JSON
    public String queryMergeWaybillByConditions() {
        if(vo==null || vo.getMergeWaybillQueryDto()==null){
            returnError("查询参数为空");
        }
        MergeWaybillQueryDto mergeWaybillQueryDto = vo.getMergeWaybillQueryDto();
		if (mergeWaybillQueryDto.getMergeWaybillNos() != null
				&& mergeWaybillQueryDto.getMergeWaybillNos().length > SettlementReportNumber.THIRTY) {
			return returnError("合并运单号不能超过30个");
		}
		if (mergeWaybillQueryDto.getWaybillNos() != null
				&& mergeWaybillQueryDto.getWaybillNos().length > SettlementReportNumber.ONE_THOUSAND) {
			return returnError("运单号不能超过1000个");
		}
        try{
            List<MergeWaybillDto> mergeWaybillDtoList = mergeWaybillService.queryMergeWaybillByConditions(mergeWaybillQueryDto, start, limit);
            long counts = mergeWaybillService.queryMergeWaybillByConditionsCounts(mergeWaybillQueryDto);

            this.setTotalCount(counts);
            vo.setMergeWaybillDtoList(mergeWaybillDtoList);

            return returnSuccess();
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return returnError(e.getMessage(),e);
        }

    }

    /**
     * 根据合并运单号查询运单明细
     * @return
     */
    @JSON
    public String queryWaybillDetailByMergeWaybillNo(){
        if(vo==null || vo.getMergeWaybillNo()==null){
            returnError("查询参数为空");
        }
        String mergeWaybillNo = vo.getMergeWaybillNo();
        try{
            List<WaybillDetailEntity> waybillDetailEntityList = mergeWaybillService.queryWaybillDetailByMergeWaybillNo(mergeWaybillNo);
            vo.setWaybillDetailEntityList(waybillDetailEntityList);
            return returnSuccess();
        }catch (Exception e){
            return returnError(e.getMessage(),e);
        }

    }

    /**
     * 作废合并运单
     * 运单详细表中将删除
     * 而合并运单中修改状态为无效
     * @return
     */
    @JSON
    public String cancelMergeWaybill(){
        if(vo==null || vo.getMergeWaybillNo()==null){
            returnError("查询参数为空");
        }
        String mergeWaybillNo = vo.getMergeWaybillNo();
        try{
            mergeWaybillService.cancelMergeWaybill(mergeWaybillNo);
            return returnSuccess("作废成功");
        }catch (SettlementException e){
            LOGGER.error(e.getMessage());
            return returnError(e);
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return returnError("作废失败");
        }
    }


    public MergeWaybillVo getVo() {
        return vo;
    }

    public void setVo(MergeWaybillVo vo) {
        this.vo = vo;
    }

    public void setMergeWaybillService(IMergeWaybillService mergeWaybillService) {
        this.mergeWaybillService = mergeWaybillService;
    }
}
