package com.deppon.foss.module.transfer.unload.server.action;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.unload.api.server.service.IBCMachSortScanService;
import com.deppon.foss.module.transfer.unload.api.shared.domain.BCMachSortScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.vo.BCMachSortScanVo;


/**
* @description 称重量方查询界面Action
* @version 1.0
* @author 105869-foss-heyongdong
* @update 2015年5月7日 下午2:02:12
*/
public class BCMachSortScanQueryAction extends AbstractAction {
	
	private BCMachSortScanVo vo;
	
	
	
	public BCMachSortScanVo getVo() {
		return vo;
	}

	public void setVo(BCMachSortScanVo vo) {
		this.vo = vo;
	}

	private IBCMachSortScanService bCMachSortScanService; 
	
	public void setbCMachSortScanService(
			IBCMachSortScanService bCMachSortScanService) {
		this.bCMachSortScanService = bCMachSortScanService;
	}

	/**
	* @fields serialVersionUID
	* @author 105869-foss-heyongdong
	* @update 2015年5月7日 下午2:04:07
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;
	
	
	/**
	* @description 查询计泡机称重量方请求
	* @return
	* @version 1.0
	* @author 105869-foss-heyongdong
	* @update 2015年5月7日 下午3:48:36
	*/
	@JSON
	public String queryBCMachSortScan(){
		try{
			List<BCMachSortScanEntity> sortScanList = bCMachSortScanService.queryBCMachSortScan(vo,this.getLimit(),this.getStart());
			vo.setSortScanList(sortScanList);
			Long totalCount = bCMachSortScanService.queryBCMachSortScanCount(vo);
			this.setTotalCount(totalCount);
			return this.returnSuccess();
		}catch(BusinessException e){
			return this.returnError(e);
		}
	}

	
}
