package com.deppon.pda.bdm.module.foss.clear.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPdaStockcheckingService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PdaPackageStTastDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.ClearConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.PackageStTastDto;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.RfshClearTask;


/**   
 * @ClassName RefreshPackageService  
 * @Description 刷新包列表   
 * @author  092038 张贞献  
 * @date 2015-4-13    
 */ 
public class RefreshPackageService implements IBusinessService<List<PackageStTastDto>, RfshClearTask>{
	private static final Log LOG = LogFactory.getLog(RefreshPackageService.class);
	
	private IPdaStockcheckingService pdaStockcheckingService;
	
	@Override
	public RfshClearTask parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		RfshClearTask clearTask = JsonUtil.parseJsonToObject(RfshClearTask.class, asyncMsg.getContent());
		return clearTask;
	}

	@Transactional
	@Override
	public List<PackageStTastDto> service(AsyncMsg asyncMsg, RfshClearTask param)
			throws PdaBusiException {
		LOG.info("start refresh Package List ...");
		List<PdaPackageStTastDto> dtos = null;
		try {
			dtos = pdaStockcheckingService.queryPdaPackageStTastDtoList(param.getTaskCode());
		} catch (BusinessException e) {
			LOG.error("刷新异常："+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		List<PackageStTastDto> clearCrgDetails = null;
		if(dtos != null && dtos.size() > 0){
		 clearCrgDetails = this.wrapPdaStTaskDtos(dtos);
		}
		
		LOG.info("refresh Package List end...");
		return clearCrgDetails;
	}

	private List<PackageStTastDto> wrapPdaStTaskDtos(List<PdaPackageStTastDto> dtos) {
		List<PackageStTastDto> st = new ArrayList<PackageStTastDto>();
		PackageStTastDto  detail = null;
		for(PdaPackageStTastDto entity:dtos){			
			detail = new PackageStTastDto();
			/**目的站*/
			detail.setArriveOrgName(entity.getArriveOrgName());
			/**入库时间*/
			detail.setInStockTime(entity.getInStockTime());
			/**包号*/
			detail.setPackageNo(entity.getPackageNo());
			/**包总体积*/
			detail.setPackageVolume(entity.getPackageVolume());
			/**包总重量*/
			detail.setPackageWeight(entity.getPackageWeight());
			/**扫描状态*/
			detail.setScanStatus(entity.getScanStatus());
			/**清仓任务ID*/
			detail.setTaskId(entity.getStTaskId());
			/**清仓任务编号*/
			detail.setTaskCode(entity.getStTaskNo());
			/**类(快)*/
			detail.setType(entity.getType());
			/**运单件数*/
			detail.setPiece(entity.getWayBillQiy());
			
			
			st.add(detail);		
		}
		
		
		
		
		return st;
		
		
	}

	@Override
	public String getOperType() {
		return ClearConstant.OPER_TYPE_CLEAR_REFRESH_PACKAGE.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaStockcheckingService(
			IPdaStockcheckingService pdaStockcheckingService) {
		this.pdaStockcheckingService = pdaStockcheckingService;
	}

}
