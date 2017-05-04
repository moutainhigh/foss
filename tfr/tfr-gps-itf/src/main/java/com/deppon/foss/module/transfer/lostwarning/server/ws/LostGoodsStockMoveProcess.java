package com.deppon.foss.module.transfer.lostwarning.server.ws;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.lostwarning.LostGoodsStock;
import com.deppon.esb.inteface.domain.lostwarning.SyncLostGoodsStockRequest;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;

/**
 * 处理丢货预警项目推送丢货货物库存异动
 * 
 * 项目名称：tfr-exceptiongoods-itf
 * 
 * 类名称：LostGoodsStockMoveProcess
 * 
 * 创建人：lyz 263072
 * 
 * 创建时间：2015-10-31 下午4:03:51
 * 
 * 版权所有：上海德邦物流有限公司
 */
public class LostGoodsStockMoveProcess implements IProcess {
	
	private static Logger LOGGER = LogManager.getLogger(LostGoodsStockMoveProcess.class);
	
	private IStockService stockService;
	
	private ITfrCommonService tfrCommonService;
	
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}

	@Override
	public Object process(Object req) throws ESBBusinessException {
		SyncLostGoodsStockRequest request = (SyncLostGoodsStockRequest) req;
		List<LostGoodsStock> list = request.getLostGoodsStock();
		for (LostGoodsStock bean : list) {
			try{
				InOutStockEntity stockDto = new InOutStockEntity();
				stockDto.setWaybillNO(bean.getWayBillNum());
				stockDto.setSerialNO(bean.getSerialNum());
				stockDto.setOrgCode(bean.getOrgCode());
				stockDto.setOperatorName(bean.getOperateorName());
				stockDto.setOperatorCode(bean.getOperateorCode());
				stockDto.setInOutStockType(bean.getInStockType());
				stockDto.setInOutStockBillNO(bean.getInStockBillNum());
				stockService.inStockRequiresNewTransactional(stockDto);
			}catch(Exception e){
				LOGGER.error(ExceptionUtils.getFullStackTrace(e));
				TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
				jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.PUSH_LOSTWARNING_QMS_JOB.getBizName());
				jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.PUSH_LOSTWARNING_QMS_JOB.getBizCode());
				jobProcessLogEntity.setRemark("丢货入库特殊组织出错！   waybillNo:" + bean.getWayBillNum() + 
						", serialNo:" + bean.getSerialNum());
				jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
				jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
				tfrCommonService.addJobProcessLog(jobProcessLogEntity);
			}
		}
		return null;
	}

	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		// TODO Auto-generated method stub
		LOGGER.error("丢货预警推送信息---库存转移异常："+ req);
		return null;
	}

}
