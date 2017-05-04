package com.deppon.foss.module.settlement.pay.server.action;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.pay.api.server.service.IDiscountAddService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.ReceivableBillDto;
import com.deppon.foss.module.settlement.pay.api.shared.vo.DiscountAddVo;
import com.deppon.foss.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;


/**
 * Created by 073615 on 2014/12/26.
 */
public class DiscuontAddAction extends AbstractAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(DiscuontAddAction.class);
    /**
     * 折扣单Vo
     */
    DiscountAddVo vo;
    /**
     * 折扣单服务
     */
    IDiscountAddService discountAddService;

    /**
     * 查询应收单
     * @return
     */
    @JSON
   public String queryReceivableByConditon(){
    try{
       if(StringUtils.isBlank(vo.getDto().getCustomerCode())){
           throw new SettlementException("客户编码不能为空");
       }
       Date firstMonthDay  = SettlementUtil.getFirstDayOfMonth(vo.getDto().getStartDate());
       Date endMonthDay = SettlementUtil.addMonthToDate(firstMonthDay,1);
        if(!firstMonthDay.equals(vo.getDto().getStartDate())){
            throw new SettlementException("开始日期不是自然月的第一天");
        }else if(!vo.getDto().getEndDate().equals(DateUtils.addDayToDate(endMonthDay, -1))){
            throw new SettlementException("结束日期不是开始日期自然月的最后一天");
        }else if(SettlementUtil.getFirstDayOfMonth(new Date()).equals(firstMonthDay)){
             throw new SettlementException("当月无法生成折扣单");
        }else{
            vo.getDto().setEndDate(endMonthDay);
        }

       ReceivableBillDto receivDto = discountAddService.queryReceiableAmountByCondition(vo.getDto());
       vo.setCodAmount(receivDto.getCodAmount());
       vo.setInsuranceAmount(receivDto.getInsuranceAmount());
       vo.setTransportAmount(receivDto.getTransportAmount());
       //根据条件查询应收单
       List<BillReceivableEntity> list = discountAddService.queryReceiableByCondition(vo.getDto(),start,limit);
       vo.setReceivableList(list);
       this.totalCount = (long)receivDto.getTotalRecordsInDB();
        return returnSuccess();
    }catch(BusinessException e ){
        LOGGER.error(e.getErrorCode(),e);
       return returnError(e);
    }
   }


    /**
     *生成折扣单
     * @return
     */
    @JSON
   public String createDiscount(){
       try{
       if(StringUtils.isBlank(vo.getDto().getCustomerCode())){
           throw new SettlementException("客户编码不能为空");
       }
       Date firstMonthDay  = SettlementUtil.getFirstDayOfMonth(vo.getDto().getStartDate());
           Date endMonthDay = SettlementUtil.addMonthToDate(firstMonthDay, 1);
           if(!firstMonthDay.equals(vo.getDto().getStartDate())){
               throw new SettlementException("开始日期不是自然月的第一天");
           }else if(!vo.getDto().getEndDate().equals(DateUtils.addDayToDate(endMonthDay, -1))){
               throw new SettlementException("结束日期不是开始日期自然月的最后一天");
               //     }else if(SettlementUtil.getFirstDayOfMonth(new Date()).equals(firstMonthDay)){
               // 两时间毫秒数不同，Date类equals方法，判断为不同时间
               //getFirstDayOfMonth产生的时间，不建议直接用equals比较是否为同一天，除非使用此方法生成时间的输入时间相同 modified by 302307
           }else if(SettlementUtil.isInCurrentMonth(firstMonthDay) || !SettlementUtil.isInTimePeriod(8,12)){
               throw new SettlementException("请于每天8:00-12:00进行“生成折扣单”操作，且当月的折扣单只允许次月生成");
           }else{
               vo.getDto().setEndDate(endMonthDay);
           }
           LOGGER.debug("开始生成"+vo.getDto().getCustomerCode()+"折扣单");
           discountAddService.createDiscount(vo.getDto());
           return returnSuccess();
       }catch(BusinessException e ){
           LOGGER.error(e.getErrorCode(),e);
           return returnError(e);
       }
    }

    /**
     *生成折扣单-职能
     * @return
     */
    @JSON
   public String createDiscountFD(){
        try{
           if(StringUtils.isBlank(vo.getDto().getCustomerCode())){
               throw new SettlementException("客户编码不能为空");
           }
           Date firstMonthDay  = SettlementUtil.getFirstDayOfMonth(vo.getDto().getStartDate());
           Date endMonthDay = SettlementUtil.addMonthToDate(firstMonthDay,1);
           if(!firstMonthDay.equals(vo.getDto().getStartDate())){
               throw new SettlementException("开始日期不是自然月的第一天");
           }else if(!vo.getDto().getEndDate().equals(DateUtils.addDayToDate(endMonthDay, -1))){
               throw new SettlementException("结束日期不是开始日期自然月的最后一天");
           }else{
               vo.getDto().setEndDate(endMonthDay);
           }
           LOGGER.debug("开始生成"+vo.getDto().getCustomerCode()+"折扣单");
           discountAddService.createDiscount(vo.getDto());
           return returnSuccess();
        }catch(BusinessException e ){
            LOGGER.error(e.getErrorCode(),e);
            return returnError(e);
        }

   }

    public void setVo(DiscountAddVo vo) {
        this.vo = vo;
    }

    public DiscountAddVo getVo() {
        return vo;
    }

    public void setDiscountAddService(IDiscountAddService discountAddService) {
        this.discountAddService = discountAddService;
    }


}
