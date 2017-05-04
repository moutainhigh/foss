package com.deppon.pda.bdm.module.foss.unload.server.service.impl.express;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDASortingService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.SortingScanDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.kuaidi.SortingScanReqModel;



/**
 * 
 * 分拣扫描接口
 * @author wenwuneng
 * @version 1.0
 * @created 2013年7月23日14:51:12
 */
public class KdSortingScanService implements IBusinessService<List<SortingScanDto>, SortingScanReqModel> {
	private static final Log LOG = LogFactory.getLog(KdSortingScanService.class);
	private IPDASortingService pdaSortingService;
	@Override
	public SortingScanReqModel parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		SortingScanReqModel sortingScanReq = JsonUtil.parseJsonToObject(SortingScanReqModel.class, asyncMsg.getContent());
		return sortingScanReq;
	}

	@Transactional
	@Override
	public List<SortingScanDto> service(AsyncMsg asyncMsg, SortingScanReqModel param)
			throws PdaBusiException {
		LOG.debug("=============分拣扫描 start====================");
		//校验请求参数
		this.validateBusinessData(param);
		List<SortingScanDto> list=new ArrayList<SortingScanDto>();
		try {
			//封装FOSS请求参数
			SortingScanDto fossScanReq=this.fossSortingScanReq(param);
			// 调用FOSS接口
			if(param.getIfPackage()!=null && "Y".equals(param.getIfPackage() )){
				list=pdaSortingService.scanPackage(fossScanReq);
			}else{
				list=pdaSortingService.scan(fossScanReq);
			}
			
		} catch (BusinessException e) {
			LOG.error("分拣扫描异常"+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		LOG.debug("=============分拣扫描 start====================");
		return list;
	}

	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLD_SORTING_SCAN.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}
	/**
	 * 封装返回参数
	 */
	/**
	 * 参数有效新校验
	 */
	private void validateBusinessData(SortingScanReqModel param) {
		Argument.notNull(param, "SortingScanReq");
		Argument.hasText(param.getWblCode(), "SortingScanReq.wblCode");
		Argument.hasText(param.getUserCode(), "SortingScanReq.userCode");
		Argument.hasText(param.getDeptCode(), "SortingScanReq.deptCode");
		Argument.notNull(param.getSignTime(), "SortingScanReq.signTimeStr");
		Argument.hasText(param.getScanType(), "SortingScanReq.scanType");
		Argument.hasText(param.getLabelCode(), "SortingScanReq.labelCode");
		Argument.hasText(param.getPdaCode(), "SortingScanReq.pdaCode");
	}
	
	private SortingScanDto fossSortingScanReq(SortingScanReqModel param){
		SortingScanDto scan=new SortingScanDto();
		scan.setWayBillNo(param.getWblCode());//运单号
		scan.setOperatorCode(param.getUserCode());//员工号
		scan.setOrgCode(param.getDeptCode());//部门编码
		scan.setScanTime(param.getSignTime());//操作时间
		scan.setScanType(param.getScanType());//扫描类型
		scan.setSerialNo(param.getLabelCode());//标签号
		scan.setDeviceNo(param.getPdaCode());//PDA 设备号
		return scan;
	}
	
	
	
	public void setPdaSortingService(IPDASortingService pdaSortingService) {
		this.pdaSortingService = pdaSortingService;
	}


}
