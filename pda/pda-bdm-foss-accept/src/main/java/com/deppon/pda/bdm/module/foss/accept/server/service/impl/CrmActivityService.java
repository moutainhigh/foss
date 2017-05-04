package com.deppon.pda.bdm.module.foss.accept.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmActiveInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.vo.CrmActiveParamVo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.MarketingActivityEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.QryActivityEntity;


/** 
  * @ClassName MarketingActivityService 
  * @Description TODO 
  * @author 092038 
  * @date 2014-4-14 上午8:36:01 
*/ 
public class CrmActivityService implements IBusinessService<List<MarketingActivityEntity>, QryActivityEntity> {
	
    private Logger log = Logger.getLogger(getClass());




	private IPdaWaybillService pdaWaybillService;
	


	public void setPdaWaybillService(IPdaWaybillService pdaWaybillService) {
		this.pdaWaybillService = pdaWaybillService;
	}
  




	/**
     * @description 解析包体
     * @param asyncMsg
     * @return
     * @throws PdaBusiException     
     * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
     */
    @Override
    public QryActivityEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
        //解析内容
    	QryActivityEntity qryActivityEntity = JsonUtil.parseJsonToObject(QryActivityEntity.class, asyncMsg.getContent());
        
        return qryActivityEntity;
    }
    
    private void validate(AsyncMsg asyncMsg, QryActivityEntity qryActivityEntity) throws PdaBusiException {
        Argument.notNull(asyncMsg, "AsyncMsg");
        //PDA编号
        Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
        //用户编号
        Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
        //部门编号
        Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
        //实体类不能为空
        Argument.notNull(qryActivityEntity, "QryActivityEntity");
        
    	Argument.hasText(qryActivityEntity.getIsExpress(), "QryActivityEntity.isExpress");
	
		Argument.notNull(qryActivityEntity.getCurrentDate(), "QryActivityEntity.currentDate");
	
                        
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
    public List<MarketingActivityEntity> service(AsyncMsg asyncMsg, QryActivityEntity qryActivityEntity) throws PdaBusiException {
       
        this.validate(asyncMsg,qryActivityEntity);
        
        log.debug("---调用FOSS获取的CRM营销活动接口开始---");
        CrmActiveInfoDto  crmActiveInfoDto = new CrmActiveInfoDto();
        CrmActiveParamVo cvo = new CrmActiveParamVo();
        cvo.setCurrentDate(qryActivityEntity.getCurrentDate());  
        cvo.setCurrentOrgCode(qryActivityEntity.getDepartmentCode());
        
          //判断是零担还是快递
        try {          
        	if(null!=qryActivityEntity.getIsExpress() && "Y".equals(qryActivityEntity.getIsExpress())){
        		
         		crmActiveInfoDto =	pdaWaybillService.getActiveInfoListExpress(cvo);
        		
        	}else{
        		crmActiveInfoDto =	pdaWaybillService.getActiveInfoList(cvo);
        	}
        	
        } catch (BusinessException e) {
            throw new FossInterfaceException(e.getCause(),e.getErrorCode());
        }
        if(crmActiveInfoDto==null){
        	return null;
        }
        //返回值转换
        List<MarkActivitiesQueryConditionDto> markActivitys = new  ArrayList<MarkActivitiesQueryConditionDto>();
        markActivitys = crmActiveInfoDto.getActiveList();  
        List<MarketingActivityEntity> markets = new ArrayList<MarketingActivityEntity>();
        MarketingActivityEntity activityEntity = null;
        //获取的名称和编码返回给PDA
        for(MarkActivitiesQueryConditionDto queryConditionDto: markActivitys){
        	activityEntity = new MarketingActivityEntity();
        	activityEntity.setMarketingCode(queryConditionDto.getCode());
        	activityEntity.setMarketingName(queryConditionDto.getName());
        	List<String> goodsName=queryConditionDto.getGoodNameList();
        	String goods="";
        	if(goodsName!=null){
        		goods=goodsName.toString();       		
        		goods=goods.replace("[", "").replace("]", "");
        	}
        	activityEntity.setGoodsName(goods);
        	markets.add(activityEntity);
        }
               
           log.debug("----返回FOSS获取的CRM营销活动接口开始成功---");
        
        return markets;
    }

    /**
     * 业务类型
     */
    @Override
    public String getOperType() {
        return AcceptConstant.OPER_TYPE_ACCT_CRMMARKETING_ACTIVITY.VERSION;
    }

    /**
     * 是否异步
     */
    @Override
    public boolean isAsync() {
        return false;
    }
}
