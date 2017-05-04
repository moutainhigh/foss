package com.deppon.pda.bdm.module.foss.load.server.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDADeliverLoadService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadScanDetailDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.server.dao.ILoadDao;
import com.deppon.pda.bdm.module.foss.load.shared.domain.NoLoadRemarkScanEntity;
import com.deppon.pda.bdm.module.foss.load.shared.domain.NoLoadScanInfo;

/**
 * 
 * TODO(未装车备注)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2012-12-3 上午10:27:13,content:TODO </p>
 * @author Administrator
 * @date 2012-12-3 上午10:27:13
 * @since
 * @version
 */
public class NoLoadRemarkService implements IBusinessService<Void, NoLoadScanInfo>{
	private ILoadDao loadDao;
	private IPDADeliverLoadService pdaDeliverLoadService;
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:20:31
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public NoLoadScanInfo parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		NoLoadScanInfo model = JsonUtil.parseJsonToObject(NoLoadScanInfo.class, asyncMsg.getContent());
		for (NoLoadRemarkScanEntity entity : model.getNoLoadRemarks()) {
			entity.setDeptCode(asyncMsg.getDeptCode());
			entity.setPdaCode(asyncMsg.getPdaCode());
			entity.setScanUser(asyncMsg.getUserCode());
			entity.setScanType(asyncMsg.getOperType());
		}
		return model;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:20:38
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, NoLoadScanInfo param)
			throws PdaBusiException {
		if(loadDao.queryNoSyncScanMsgCount(param.getNoLoadRemarks().get(0).getTaskCode())){
			throw new FossInterfaceException(null,"服务器数据正在同步，请耐心等待");
		}
		LoadScanDetailDto req = new LoadScanDetailDto();
		Map<String,Object> map = new HashMap<String, Object>();
		for (NoLoadRemarkScanEntity entity : param.getNoLoadRemarks()) {
			this.validate(entity);
			entity.setSyncStatus(Constant.SYNC_STATUS_INIT);
			//loadDao.saveNoLoadScan(entity);
			req.setWayBillNo(entity.getWblCode());
			req.setSerialNo(entity.getLabelCode());
			req.setTaskNo(entity.getTaskCode());
			req.setDeviceNo(asyncMsg.getPdaCode());
			req.setScanTime(entity.getScanTime());
			req.setNotes(entity.getNoLoadReson());
			try {
				if(!map.containsKey(entity.getWblCode())){
					pdaDeliverLoadService.deliverLoadNotes(req);
					map.put(entity.getWblCode(),entity);
				}
				continue;
			} catch (BusinessException e) {
				throw new FossInterfaceException(e.getCause(),e.getErrorCode());
			}
		}
		return null;
	}                                                          
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:20:44
	 * @param param
	 * @see
	 */
	private void validate(NoLoadRemarkScanEntity param) {
		Argument.notNull(param, "NoLoadRemarkScanEntity");
		//Argument.hasText(param.getLabelCode(), "NoLoadRemarkScanEntity.labelCode");
		Argument.hasText(param.getWblCode(),  "NoLoadRemarkScanEntity.wblCode");
		Argument.notNull(param.getScanTime(), "NoLoadRemarkScanEntity.scanTime");
		Argument.hasText(param.getNoLoadReson(), "NoLoadRemarkScanEntity.NoLoadReson");
	}

	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_LOAD_REMARK.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public void setLoadDao(ILoadDao loadDao) {
		this.loadDao = loadDao;
	}

	public void setPdaDeliverLoadService(
			IPDADeliverLoadService pdaDeliverLoadService) {
		this.pdaDeliverLoadService = pdaDeliverLoadService;
	}

}
