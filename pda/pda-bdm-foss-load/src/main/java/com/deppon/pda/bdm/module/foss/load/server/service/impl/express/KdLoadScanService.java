package com.deppon.pda.bdm.module.foss.load.server.service.impl.express;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressDeliverLoadService;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressDeliverScanDto;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.server.dao.ILoadDao;
import com.deppon.pda.bdm.module.foss.load.shared.domain.KdLoadScanEntity;
import com.deppon.pda.bdm.module.foss.load.shared.domain.KdScanBusinessErrorLog;

/** 
  * @ClassName KdLoadScanService 
  * @Description TODO 快递装车扫描实现类
  * @author cbb 
  * @date 2013-7-26 下午5:01:20 
*/ 
public class KdLoadScanService implements IBusinessService<Void, KdLoadScanEntity>{
	private ILoadDao loadDao;
	private IPDAExpressDeliverLoadService pdaExpressDeliverLoadService;
	private KdScanBusinessErrorLogComponent kdScanBusinessErrorLogComponent;
	private Logger log = Logger.getLogger(getClass());
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:19:33
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public KdLoadScanEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		KdLoadScanEntity entity = JsonUtil.parseJsonToObject(KdLoadScanEntity.class, asyncMsg.getContent());
		entity.setDeptCode(asyncMsg.getDeptCode());
		entity.setPdaCode(asyncMsg.getPdaCode());
		entity.setScanUser(asyncMsg.getUserCode());
		entity.setScanType(asyncMsg.getOperType());
		entity.setUploadTime(asyncMsg.getUploadTime());
		return entity;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:19:38
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, KdLoadScanEntity param)
			throws PdaBusiException {
		//校验数据合法性
		this.validate(param);
		param.setSyncStatus(Constant.SYNC_STATUS_INIT);
		ExpressDeliverScanDto  entity = new ExpressDeliverScanDto ();
		entity.setTaskNo(param.getTaskCode());//任务号
		entity.setWayBillNo(param.getWblCode());//运单号
		entity.setSerialNo(param.getLabelCode());//流水号
		entity.setDeviceNo(asyncMsg.getPdaCode());//PDA设备号
		entity.setScanTime(param.getScanTime());//扫描时间
		entity.setType(TransferPDADictConstants.LOAD_GOODS_STATE_NORMAL);//类型  快递装车扫描状态只有  NORMAL  bug编号 BUGKD-680
		entity.setScanState(param.getScanFlag());//扫描状态
		try {
			//调用FOSS接口
			long startTime = System.currentTimeMillis();
			pdaExpressDeliverLoadService.scan(entity);
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]装车扫描接口消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			KdScanBusinessErrorLog kdScanBusinessErrorLog = new KdScanBusinessErrorLog();
			kdScanBusinessErrorLog.setId(param.getId());
			kdScanBusinessErrorLog.setTaskCode(param.getTaskCode());
			kdScanBusinessErrorLog.setWaybillCode(param.getWblCode());
			kdScanBusinessErrorLog.setLabelCode(param.getLabelCode());
			kdScanBusinessErrorLog.setReason(e.getErrorCode());
			kdScanBusinessErrorLog.setScanStatus(TransferPDADictConstants.LOAD_GOODS_STATE_NORMAL);
			kdScanBusinessErrorLogComponent.saveKdScanBusinessErrorLog(kdScanBusinessErrorLog);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		//保存装车扫描信息
		loadDao.saveKdLoadScan(param);
		return null;
		
	}
	
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:20:12
	 * @param entity
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate( KdLoadScanEntity entity)
			throws ArgumentInvalidException {
		//检验扫描非空
		Argument.notNull(entity, "kdLoadScanEntity");
		//标签号非空
		Argument.hasText(entity.getLabelCode(), "kdLoadScanEntity.labelCode");
		//任务号非空
		Argument.hasText(entity.getTaskCode(), "kdLoadScanEntity.taskCode");
		//扫描时间非空
		Argument.notNull(entity.getScanTime(), "kdLoadScanEntity.scanTime");
	}
	@Override
	public String getOperType() {
		// TODO Auto-generated method stub
		return LoadConstant.OPER_TYPE_KD_LOAD_SCAN.VERSION;
	}

	@Override
	public boolean isAsync() {
		// TODO Auto-generated method stub
		return true;
	}

	public void setLoadDao(ILoadDao loadDao) {
		this.loadDao = loadDao;
	}

	public void setPdaExpressDeliverLoadService(
			IPDAExpressDeliverLoadService pdaExpressDeliverLoadService) {
		this.pdaExpressDeliverLoadService = pdaExpressDeliverLoadService;
	}
	
	public void setKdScanBusinessErrorLogComponent(
			KdScanBusinessErrorLogComponent kdScanBusinessErrorLogComponent) {
		this.kdScanBusinessErrorLogComponent = kdScanBusinessErrorLogComponent;
	}

}

