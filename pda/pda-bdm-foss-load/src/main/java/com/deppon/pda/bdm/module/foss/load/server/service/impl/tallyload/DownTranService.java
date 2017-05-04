package com.deppon.pda.bdm.module.foss.load.server.service.impl.tallyload;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressConnectionService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.AccessPointDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
//import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
//import com.deppon.pda.bdm.module.foss.load.shared.domain.tallyload.GetPotEntity;
import com.deppon.pda.bdm.module.foss.load.shared.domain.tallyload.GetPotEntity;
import com.deppon.pda.bdm.module.foss.load.shared.domain.tallyload.PotlModel;

/**
 * 下拉接驳点
 * @ClassName DownTranService.java 
 * @Description 
 * @author 245955
 * @date 2015-4-10
 */
public  class DownTranService implements IBusinessService<List<PotlModel>, GetPotEntity> {
	 private IPDAExpressConnectionService pdaExpressConnectionService;
	 
	 private Logger log = Logger.getLogger(getClass());
	 
	 /**
	 * @description 解析包体
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public GetPotEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		GetPotEntity entity=JsonUtil.parseJsonToObject(GetPotEntity.class, asyncMsg.getContent());
		return entity;
	}
	
	/**
	 * 服务方法
	 * @description 
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-10
	 */
	@Override
	public List<PotlModel> service(AsyncMsg asyncMsg, GetPotEntity param) throws PdaBusiException {
        this.validate(asyncMsg);   
        log.debug("---调用FOSS查询接驳点接口开始---");
        List<PotlModel> modeList=new ArrayList<PotlModel>(); 
        List<AccessPointDto> pot = null;
        try {
         //调用FOSS方法,根据用户编号和大部门查询数据
         pot = pdaExpressConnectionService.queryAccessPointsByTransferCode(param.getDepartMent());       
        } catch (BusinessException e) {
            throw new FossInterfaceException(e.getCause(),e.getErrorCode());
        }
        log.debug("---调用FOSS查询接驳点接口结束---");
        if(pot!=null && pot.size()>0){
        	PotlModel model= null;
        	for (AccessPointDto accessPointDto : pot) {
             model = new PotlModel();
             model.setOrgCode(accessPointDto.getCode());
             model.setOrgName(accessPointDto.getName());
             modeList.add(model);     
              }
          }		
        log.debug("---返回接驳点信息成功---"); 
        log.debug(modeList.size());
	    return modeList;
    }
	
	/**
	 * 校验数据的合法性
	 * @description 
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-10
	 */
	private void validate(AsyncMsg asyncMsg) throws PdaBusiException {
        // pdaInfo信息校验
        Argument.notNull(asyncMsg, "AsyncMsg");
        //验证PDA编号
        //Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
        //验证部门编号
         Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.getDeptCode");    
        // 包体信息校验
        // Argument.notNull(entity, "GetPotEntity");
    }

	/**
	 * 操作类型
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-11
	 */
	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_LOAD_DOWN_TRAN.VERSION;
	}

	/**
	 * 是否异步
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-11
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaExpressConnectionService(IPDAExpressConnectionService pdaExpressConnectionService) {
		this.pdaExpressConnectionService = pdaExpressConnectionService;
	}
}
