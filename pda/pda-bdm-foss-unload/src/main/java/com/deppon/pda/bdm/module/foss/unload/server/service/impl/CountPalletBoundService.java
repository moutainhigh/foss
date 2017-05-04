package com.deppon.pda.bdm.module.foss.unload.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDATrayScanService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDATrayScanTaskEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.CountPalletBoundResult;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.QryCountPalletBoundInfo;
/**
 * 
 * TODO(统计叉车票数)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2014-1-13 上午9:25:57,content:TODO </p>
 * @author Administrator
 * @date 2014-1-13 上午9:25:57
 * @since
 * @version
 */
public class CountPalletBoundService implements IBusinessService<List<CountPalletBoundResult>, QryCountPalletBoundInfo>{
	private static final Logger log = Logger.getLogger(CountPalletBoundService.class);
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
	  
	    //如果为空 则默认等于12小时
	    if(model.getTimeSpan()==0){
	        model.setTimeSpan(12);
	    }  
	    
	    List<PDATrayScanTaskEntity> trayTasks = null;
		try {
			long startTime = System.currentTimeMillis();
			trayTasks = pdaTrayScanService.queryTraybindforkLiftTicks(model.getUserCode(), model.getDeptCode(), new Date(),model.getTimeSpan());
			log.info("[asyncinfo]统计叉车扫描接口消耗时间:" + (System.currentTimeMillis() - startTime) + "ms");
		} catch (BusinessException  e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}
		List<CountPalletBoundResult> result = new ArrayList<CountPalletBoundResult>();
		if(trayTasks!=null&&!trayTasks.isEmpty()){
			for (PDATrayScanTaskEntity trayTask : trayTasks) {
				CountPalletBoundResult countPalletBound = new CountPalletBoundResult();
				countPalletBound.setnVote(trayTask.getForkliftVotes());
				countPalletBound.setTaskCode(trayTask.getTaskNo());
				countPalletBound.setScanTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(trayTask.getTrayscanTime()));
				result.add(countPalletBound);
			}
		}
		return result;
	}

	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLD_PALLET_BOUND_COUNT.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaTrayScanService(IPDATrayScanService pdaTrayScanService) {
		this.pdaTrayScanService = pdaTrayScanService;
	}
	
}
