package com.deppon.foss.module.pickup.order.server.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.pickup.order.api.server.service.IAutoExpressMakeupService;
import com.deppon.foss.module.pickup.order.api.shared.tools.OrderTheadPool;
import com.deppon.foss.module.pickup.order.api.shared.util.SettlementReportNumber;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IEamDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IEamService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IExpressAutoMakeupService;
import com.deppon.foss.module.pickup.waybill.shared.domain.EamDto;

/**
 *  快递集中录单
 * @author Foss-065340-liutao
 * @date 2015-09-30 
 */
public class AutoExpressMakeupService extends OrderTheadPool implements IAutoExpressMakeupService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AutoExpressMakeupService.class);
	
	/**
	 * 快递集中录单处理服务类
	 */
	private IExpressAutoMakeupService expressAutoMakeupService;
	private IEamService eamService;
	private IEamDao eamDao;
	@Override
	public void businessExecutor(Object obj) {
		//进行数据转换
		String jobId = (String) obj;
		List<EamDto> List = eamService.queryGenerateUnActiveEamByJobID(jobId);
		if (List != null && List.size() > 0) {
			for (int i = 0; i < List.size(); i++) {
				// 执行补录
				try {
					expressAutoMakeupService.expressWaybillAutoMakeup(List.get(i));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void outOfOrderPool(Object obj) {
		LOGGER.info("AutoExpressMakeupService【线程池满异常处理开始。。。。。。】");
		//进行数据转换
		String jobId = (String) obj;
		if(StringUtils.isEmpty(jobId)){
			return;
		}
		List<EamDto> List = eamService.queryGenerateUnActiveEamByJobID(jobId);
		//判定运单号集合是否为空
		if(CollectionUtils.isEmpty(List)){
			return;
		}
		//进行数据插入
		for(EamDto eamDto : List){
				//设置ID等数据
				eamDto.setBillNumberState("N");	
				eamDto.setRemarks("快递集中录单线程池满异常");
				eamDao.eamLogInsert(eamDto);
		}
		LOGGER.info("AutoExpressMakeupService【线程池满异常处理结束。。。。。。】");
	}

	@Override
	public int getActiveThreads() {
		return SettlementReportNumber.NINE;
	}
	@Override
	public void process(String jobId) {
		pushThreadsPool(jobId);
	}

	public void setEamService(IEamService eamService) {
		this.eamService = eamService;
	}
	public void setExpressAutoMakeupService(
			IExpressAutoMakeupService expressAutoMakeupService) {
		this.expressAutoMakeupService = expressAutoMakeupService;
	}

	public void setEamDao(IEamDao eamDao) {
		this.eamDao = eamDao;
	}
	
}
