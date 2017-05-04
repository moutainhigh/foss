package com.deppon.pda.bdm.module.foss.unload.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDATrayScanService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDATrayOfflineScanDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.CountPalletBoundResult;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.QryCountPalletBoundInfo;

/** 
  * @ClassName ExcCountPalletBoundService 
  * @Description 《统计叉车异常扫描数据》
  * @author 092038 
  * @date 2014-5-7 下午7:13:06 
*/ 
public class ExcCountPalletBoundService implements IBusinessService<List<CountPalletBoundResult>, QryCountPalletBoundInfo>{
	private static final Logger log = Logger.getLogger(ExcCountPalletBoundService.class);
	private IPDATrayScanService pdaTrayScanService;
	@Override
	public QryCountPalletBoundInfo parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
	    QryCountPalletBoundInfo model = JsonUtil.parseJsonToObject(QryCountPalletBoundInfo.class,asyncMsg.getContent());
	    model.setDeptCode(asyncMsg.getDeptCode());
	    model.setUserCode(asyncMsg.getUserCode());
	    return model;
	}

	@Override
	@Transactional
	public List<CountPalletBoundResult> service(AsyncMsg asyncMsg, QryCountPalletBoundInfo model) throws PdaBusiException {
	    List<PDATrayOfflineScanDto> trayTasks = null;
		try {
			long startTime = System.currentTimeMillis();
			trayTasks = pdaTrayScanService.queryTrayOfflineScanWaybillQty(model.getUserCode(), model.getDeptCode(), new Date(),model.getTimeSpan());
			log.info("[asyncinfo]统计叉车异常扫描接口消耗时间:" + (System.currentTimeMillis() - startTime) + "ms");
		} catch (BusinessException  e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}
		List<CountPalletBoundResult> result = new ArrayList<CountPalletBoundResult>();
		if(trayTasks!=null&&!trayTasks.isEmpty()){
			for (PDATrayOfflineScanDto trayTask : trayTasks) {
				CountPalletBoundResult countPalletBound = new CountPalletBoundResult();
				countPalletBound.setnVote(trayTask.getWaybillQty());
				countPalletBound.setTaskCode(trayTask.getOfflineTaskNo());
				countPalletBound.setScanTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(trayTask.getTrayOfflineScanTime()));
				result.add(countPalletBound);
			}
		}
		return result;
	}

	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLD_EXCE_BOUND_COUNT.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaTrayScanService(IPDATrayScanService pdaTrayScanService) {
		this.pdaTrayScanService = pdaTrayScanService;
	}
	
}
