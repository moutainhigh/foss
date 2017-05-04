package com.deppon.pda.bdm.module.foss.clear.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPdaDifferReportService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PdaDifferDetailEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.ClearConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.DifferDetailEntity;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.QryClearExceReportEntity;


/** 
  * @ClassName ClearExceReportDetailService 
  * @Description 获取清仓差异报告明细 
  * @author 092038 
  * @date 2014-6-9 上午8:44:53 
*/ 
public class ClearExceReportDetailService implements IBusinessService<List<DifferDetailEntity>, QryClearExceReportEntity>{
	private static final Log LOG = LogFactory.getLog(ClearExceReportDetailService.class);
	
	   private IPdaDifferReportService pdaDifferReportService;
	
	@Override
	public QryClearExceReportEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
	    QryClearExceReportEntity qryClearReport = JsonUtil.parseJsonToObject(QryClearExceReportEntity.class, asyncMsg.getContent());
		return qryClearReport;
	}

	@Transactional
	@Override
	public List<DifferDetailEntity> service(AsyncMsg asyncMsg, QryClearExceReportEntity param)
			throws PdaBusiException {
		LOG.info("start refresh cleartask ...");
		List<PdaDifferDetailEntity> pdadiffers = null;
		
		List<DifferDetailEntity> differs = new ArrayList<DifferDetailEntity>();
		
		try {
		    pdadiffers =  pdaDifferReportService.queryDifferDetailByReportNo(
		            param.getReportCode(),
		            asyncMsg.getDeptCode(),
		            asyncMsg.getUserCode(),
		            asyncMsg.getPdaCode(),
		            param.getHandInputFlg()
		            );
		} catch (BusinessException e) {
			LOG.error("刷新异常："+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		
		//将明细转换成PDA明细
		if(pdadiffers!=null && pdadiffers.size()>0){
			DifferDetailEntity detail = null;
			for(PdaDifferDetailEntity entity:pdadiffers){
				detail = new DifferDetailEntity();
				//包号
				detail.setPackageNo(entity.getPackageNo());
				//运单号
				detail.setWaybillNo(entity.getWaybillNo());
				//流水号
				detail.setSerialNo(entity.getSerialNo());
				//处理状态
				detail.setHandleStatus(entity.getHandleStatus());
				//目的站
				detail.setDestStation(entity.getDestStation());		
				
				differs.add(detail);
			}
			
			
		}
		
		
		
		LOG.info("refresh cleartask end...");
		return differs;
	}

	

	@Override
	public String getOperType() {
		return ClearConstant.OPER_TYPE_CLEAR_EXCE__DETAIL.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

    public void setPdaDifferReportService(IPdaDifferReportService pdaDifferReportService) {
        this.pdaDifferReportService = pdaDifferReportService;
    }



}
