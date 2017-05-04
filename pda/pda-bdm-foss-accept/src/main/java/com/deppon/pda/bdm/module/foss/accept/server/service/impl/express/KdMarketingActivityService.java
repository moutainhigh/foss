package com.deppon.pda.bdm.module.foss.accept.server.service.impl.express;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaPriceService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.CityMarketPlanDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.CountFreightEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.MarketingActivityEntity;


/** 
  * @ClassName MarketingActivityService 
  * @Description TODO 
  * @author 092038 
  * @date 2014-4-14 上午8:36:01 
*/ 
public class KdMarketingActivityService implements IBusinessService<List<MarketingActivityEntity>, CountFreightEntity> {
	
    private Logger log = Logger.getLogger(getClass());

    //开单价格接口
    private IPdaPriceService pdaPriceService;
   
    
    public void setPdaPriceService(IPdaPriceService pdaPriceService) {
        this.pdaPriceService = pdaPriceService;
    }



    /**
     * @description 解析包体
     * @param asyncMsg
     * @return
     * @throws PdaBusiException     
     * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
     */
    @Override
    public CountFreightEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
        //解析内容
        CountFreightEntity countFreightEntity = JsonUtil.parseJsonToObject(CountFreightEntity.class, asyncMsg.getContent());
        
        return countFreightEntity;
    }
    
    private void validate(AsyncMsg asyncMsg, CountFreightEntity countFreight) throws PdaBusiException {
        Argument.notNull(asyncMsg, "AsyncMsg");
        //PDA编号
        Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
        //用户编号
        Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
        //部门编号
        Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
        Argument.notNull(countFreight, "CountFreightEntity");
         // 出发部门编码
        Argument.hasText(countFreight.getOriginalOrgCode(), "CountFreightEntity.originalOrgCode");
             // 开单日期
        Argument.notNull(countFreight.getReceiveDate(), "CountFreightEntity.receiveDate");

        
    }

    /**
     * @description 服务方法
     * @param asyncMsg
     * @param qryActOrder
     * @return
     * @throws PdaBusiException     
     * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
     */
    @Override
    public List<MarketingActivityEntity> service(AsyncMsg asyncMsg, CountFreightEntity countFreightEntity) throws PdaBusiException {
       
        this.validate(asyncMsg,countFreightEntity);
        
        log.debug("---调用FOSS获取的营销活动接口开始---");
        List<MarketingActivityEntity> marketingActivitys = new ArrayList<MarketingActivityEntity>();
       
        List<CityMarketPlanDto> cityMarketPlans = null;
        try {
            //根据用户编号,和车牌号进行查询数据
          cityMarketPlans = pdaPriceService.searchCityMarketPlanEntityList(
          countFreightEntity.getOriginalOrgCode(),countFreightEntity.getReceiveDate());

        } catch (BusinessException e) {
            throw new FossInterfaceException(e.getCause(),e.getErrorCode());
        }
        if(cityMarketPlans != null){
            for(CityMarketPlanDto cmpDto:cityMarketPlans){
                MarketingActivityEntity marketingActivity = new MarketingActivityEntity();
                marketingActivity.setMarketingCode(cmpDto.getCode());
                marketingActivity.setMarketingName(cmpDto.getName());
                marketingActivitys.add(marketingActivity);
            }
        }
           log.debug("----返回FOSS获取的营销活动接口开始成功---");
        
        return marketingActivitys;
    }

    /**
     * 业务类型
     */
    @Override
    public String getOperType() {
        return AcceptConstant.OPER_TYPE_ACCT_MARKETING_ACTIVITY.VERSION;
    }

    /**
     * 是否异步
     */
    @Override
    public boolean isAsync() {
        return false;
    }
}
