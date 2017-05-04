package com.deppon.pda.bdm.module.foss.accept.server.service.impl.electronicbill;


import com.deppon.foss.framework.shared.util.Argument;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.server.service.IPDAToVASService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ExternalInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill.ScanInfoEntity;
/**         
 * 接受扫描清关条码信息
 * @author 268974 wangzhili      
 * @created 2015-12-25     
 */
public class BigCustmerScanInfoService implements IBusinessService<String, ScanInfoEntity>{

	//private Logger log = Logger.getLogger(getClass());
	private IPDAToVASService pdaToVASService;
	

	public void setPdaToVASService(IPDAToVASService pdaToVASService) {
		this.pdaToVASService = pdaToVASService;
	}

	@Override
	public ScanInfoEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		ScanInfoEntity scanInfoEntity = JsonUtil.parseJsonToObject(ScanInfoEntity.class, asyncMsg.getContent());
		return scanInfoEntity;
	}

	@Override
	public String service(AsyncMsg asyncMsg, ScanInfoEntity param)
			throws PdaBusiException {
		//校验参数
		this.validate(asyncMsg, param);
		String responseString;
		try{
		 responseString = pdaToVASService.pdaSendDataToVAS(param.getCustomsCode());
		 if("清关条码未传入".equals(responseString))	{
				throw new ArgumentInvalidException("清关条码未传入", null);
			}
		} catch (ExternalInterfaceException e){
			throw new ExternalInterfaceException(e.getCause(),e.getErrCode());
		} catch (Exception e){
			throw new ExternalInterfaceException(null,"清关条码未传入");
		}
		return responseString;
	}

	private void validate(AsyncMsg asyncMsg, ScanInfoEntity entity) throws ArgumentInvalidException {
		Argument.notNull(entity, "entity");
		Argument.notNull(entity.getCustomsCode(), "customsCode不能为null");
	}

	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_BIG_CUSTMER_SCAN_INFO.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

}
