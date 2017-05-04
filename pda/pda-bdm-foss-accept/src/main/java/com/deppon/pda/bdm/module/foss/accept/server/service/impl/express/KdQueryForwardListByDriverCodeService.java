package com.deppon.pda.bdm.module.foss.accept.server.service.impl.express;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.foss.accept.server.IPDAToOMSService;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.PdaForwardDiverDto;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.PdaForwardDiverDtoList;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.PdaForwardDiverEntity;

/**
 * 快递获取转发人员集合
 * @author 245955
 *
 */
public class KdQueryForwardListByDriverCodeService implements IBusinessService<List<PdaForwardDiverEntity>, ScanMsgEntity> {

	private  IPDAToOMSService pdaToOMSService;
	
	public void setPdaToOMSService(IPDAToOMSService pdaToOMSService) {
		this.pdaToOMSService = pdaToOMSService;
	}

	@Override
	public ScanMsgEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<PdaForwardDiverEntity> service(AsyncMsg asyncMsg,
			ScanMsgEntity param) throws PdaBusiException {
		List<PdaForwardDiverDto>  pdaForwardDiverDtos=new ArrayList<PdaForwardDiverDto>();
		List<PdaForwardDiverEntity> pdaForwardDiverEntitys = new ArrayList<PdaForwardDiverEntity>();
		try {
			 String  responseStr=pdaToOMSService.pdaKdQueryForwardListByDriverCodeOMS(asyncMsg.getUserCode());
			 Map<String, Class> classMap = new HashMap<String, Class>();
				classMap.put("pdadriverlist", PdaForwardDiverDto.class);
			 PdaForwardDiverDtoList response = 
		  				(PdaForwardDiverDtoList)JSONObject.toBean(JSONObject.fromObject(responseStr), PdaForwardDiverDtoList.class,classMap);
	          if(null != response.getPdadriverlist()){
	        	  pdaForwardDiverDtos = response.getPdadriverlist();
	          }
			if(pdaForwardDiverDtos!=null){
				PdaForwardDiverDto pdaForwardDiverDto = null;
				for (int i = 0; i < pdaForwardDiverDtos.size(); i++) {
					pdaForwardDiverDto = pdaForwardDiverDtos.get(i);
					PdaForwardDiverEntity pdaForwardDiverEntity = new PdaForwardDiverEntity();
					pdaForwardDiverEntity.setDriverCode(pdaForwardDiverDto.getDriverCode());
					pdaForwardDiverEntity.setDriverName(pdaForwardDiverDto.getDriverName());
					pdaForwardDiverEntitys.add(pdaForwardDiverEntity);
				}			
			}
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		return pdaForwardDiverEntitys;
	}

	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_KD_QUERYFORWARDLIST_ACTIVITY.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

}
