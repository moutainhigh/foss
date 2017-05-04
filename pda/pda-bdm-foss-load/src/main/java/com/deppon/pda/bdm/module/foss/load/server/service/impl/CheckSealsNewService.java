package com.deppon.pda.bdm.module.foss.load.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPdaVehicleSealService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.SealDestDetailEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.shared.domain.CheckLoadSeals;
import com.deppon.pda.bdm.module.foss.load.shared.domain.CheckSealsNewEntity;
import com.deppon.pda.bdm.module.foss.load.shared.domain.SealDestDetail;

/**
 * 
 * TODO(检查封签)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2012-12-3 上午10:26:21,content:TODO </p>
 * @author Administrator
 * @date 2012-12-3 上午10:26:21
 * @since
 * @version
 */
public class CheckSealsNewService implements IBusinessService<CheckSealsNewEntity,CheckLoadSeals>{
	

	private IPdaVehicleSealService pdaVehicleSealService;
	private Logger log = Logger.getLogger(getClass());
	@Override
	public CheckLoadSeals parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		CheckLoadSeals model = JsonUtil.parseJsonToObject(CheckLoadSeals.class, asyncMsg.getContent());
		model.setDeptCode(asyncMsg.getDeptCode());
		model.setPdaCode(asyncMsg.getPdaCode());
		model.setScanUser(asyncMsg.getUserCode());
		model.setScanType(asyncMsg.getOperType());
		model.setUploadTime(asyncMsg.getUploadTime());
		return model;
	}
	/**
	 * 
	 * <p>TODO(检查封签)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午5:59:46
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public CheckSealsNewEntity service(AsyncMsg asyncMsg, CheckLoadSeals param)
			throws PdaBusiException {
	    CheckSealsNewEntity result= new CheckSealsNewEntity();
	    //添加封签明细
	    List<SealDestDetailEntity> sealDestDetails =  new ArrayList<SealDestDetailEntity>();
	  
	    if(param.getSealDestDetails()!=null){
	        SealDestDetailEntity sealDE = null;
	        for(SealDestDetail sd:param.getSealDestDetails()){
	            sealDE =  new SealDestDetailEntity();
	            sealDE.setSealNo(sd.getSealNo());
	            sealDE.setCheckType(sd.getCheckType());
	            sealDestDetails.add(sealDE);
	        }
	    }	   	  	 
		log.debug("检查封签开始");
		this.validate(param);
		param.setSyncStatus(Constant.SYNC_STATUS_INIT);		
		try {
			//调用foss接口	
			String str =  pdaVehicleSealService.insertSealDestNew (param.getStatus(),  param.getTruckCode(), 
		              asyncMsg.getDeptCode(),sealDestDetails, param.getCheckOrgMemo(), 
		              asyncMsg.getUserCode(), asyncMsg.getPdaCode(),
		              sealDestDetails.size()==0?null:sealDestDetails.get(0).getCheckType());
			result.setPlatformNum(str);
		
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		log.debug("检查封签结束");
		
		return result;
	}
	/**
	 * 
	 * <p>TODO(检验数据合法性)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:06:06
	 * @param param
	 * @see
	 */
	private void validate(CheckLoadSeals param) {
		
		Argument.notNull(param, "CheckLoadSeals");
		//封签状态不能为空
		Argument.hasText(param.getStatus(), "CheckLoadSeals.SealState");
		//车牌号不能为空
		Argument.hasText(param.getTruckCode(), "CheckLoadSeals.TruckCode");
		//交接单号不能为空
		//Argument.notEmpty(param.getReceiptCodes(), "CheckLoadSeals.ReceiptCodes");
		//Argument.notEmpty(param.getBackSealNos(), "CheckLoadSeals.BackSealNos");
		//Argument.notEmpty(param.getSideSealNos(), "CheckLoadSeals.SideSealNos");
	}

	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLD_SEALS_NEW_CHK.VERSION;
	}
	//PDA20140327版本同步改异步	
	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaVehicleSealService(
			IPdaVehicleSealService pdaVehicleSealService) {
		this.pdaVehicleSealService = pdaVehicleSealService;
	}
	

}
