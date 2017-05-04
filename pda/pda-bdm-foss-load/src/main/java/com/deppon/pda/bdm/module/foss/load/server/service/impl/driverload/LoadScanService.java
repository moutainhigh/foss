package com.deppon.pda.bdm.module.foss.load.server.service.impl.driverload;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPickService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadScanDetailDto;
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
import com.deppon.pda.bdm.module.foss.load.shared.domain.LoadScanEntity;
/**
 * 司机装车扫描类
 * @ClassName CalLoadScanService.java 
 * @Description 
 * @author 245955
 * @date 2015-4-23
 */
public class LoadScanService implements IBusinessService<Void, LoadScanEntity> {
	private Logger log = Logger.getLogger(getClass());
	private ILoadDao loadDao;
	private IPDAExpressPickService pdaExpressPickService;
	/**
	 * 解析包体
	 * @description 
	 * @param asyncMsg
	 * @return
	 * @throws P
	 * daBusiException
	 * @author 245955
	 * @date 2015-4-23
	 */
	@Transactional
	@Override
	public LoadScanEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		LoadScanEntity entity = JsonUtil.parseJsonToObject(LoadScanEntity.class, asyncMsg.getContent());
		entity.setDeptCode(asyncMsg.getDeptCode());
		entity.setPdaCode(asyncMsg.getPdaCode());
		entity.setScanUser(asyncMsg.getUserCode());
		entity.setScanType(asyncMsg.getOperType());
		entity.setUploadTime(asyncMsg.getUploadTime());
		return entity;
	}
	
	/**
	 * 服务方法
	 * @description 
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-23
	 */
	@Override
	public Void service(AsyncMsg asyncMsg, LoadScanEntity loadScanEntity) throws PdaBusiException {
		//检验数据合法性
		this.validate(asyncMsg, loadScanEntity);
		loadScanEntity.setSyncStatus(Constant.SYNC_STATUS_INIT);
		//保存装车扫描数据
		loadDao.saveDriverLoadScan(loadScanEntity);
		LoadScanDetailDto entity = new LoadScanDetailDto();
		entity.setTaskNo(loadScanEntity.getTaskCode());
		entity.setWayBillNo(loadScanEntity.getWblCode());
		entity.setSerialNo(loadScanEntity.getLabelCode());
		entity.setDeviceNo(asyncMsg.getPdaCode());
		entity.setScanTime(loadScanEntity.getScanTime());
		entity.setType(loadScanEntity.getScanStatus());
		entity.setScanState(loadScanEntity.getScanFlag());
		entity.setVolume(loadScanEntity.getVolume());
		entity.setWeight(loadScanEntity.getWeight());
		entity.setGoodsName(loadScanEntity.getCargoName());
		entity.setBeJoinCar(loadScanEntity.getBeJoinCar());
		entity.setStockQty(loadScanEntity.getStockQty());
		entity.setPack(loadScanEntity.getIsWrap());
		entity.setTransportType(loadScanEntity.getTransType());
		entity.setReceiveOrgName(loadScanEntity.getAcctDeptName());
		entity.setReachOrgName(loadScanEntity.getArrDeptName());
		entity.setIsPackageScan(loadScanEntity.getIfPackage());//是否包扫描
		if(loadScanEntity.getIfPackage()==null || !"Y".equals(loadScanEntity.getIfPackage())){
			Argument.hasText(loadScanEntity.getLabelCode(), "loadScanEntity.labelCode");
		}
	   try {
			long startTime = System.currentTimeMillis();
			//调用FOSS接口
			pdaExpressPickService.scan(entity);
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]装车扫描接口消耗时间:"+(endTime-startTime)+"ms");
			return null;
			} catch (BusinessException e) {
				throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		  }
	}
	
	/**
	 * 参数验证
	 * @description 
	 * @param asyncMsg
	 * @param entity
	 * @throws ArgumentInvalidException
	 * @author 245955
	 * @date 2015-4-23
	 */
	private void validate(AsyncMsg asyncMsg, LoadScanEntity entity) throws ArgumentInvalidException{
		//撤销装车扫描非空
		Argument.notNull(entity,"LoadScanEntity");
		//标签号非空
	    //Argument.hasText(entity.getLabelCode(), "loadCancelScanEntity.labelCode");
		//任务号非空
		Argument.hasText(entity.getTaskCode(), "LoadScanEntity.taskCode");
		//撤销时间非空
		Argument.notNull(entity.getScanTime(), "LoadScanEntity.scanTime");
	}
	
	
	/**
	 * 操作类型
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-23
	 */
	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_DRIVER_TASK_SCAN.VERSION;
	}
	/**
	 * 是否异步
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-23
	 */
	@Override
	public boolean isAsync() {
		return true;
	}
    
	public void setPdaExpressPickService(IPDAExpressPickService pdaExpressPickService) {
		this.pdaExpressPickService = pdaExpressPickService;
	}
	public void setLoadDao(ILoadDao loadDao) {
		this.loadDao = loadDao;
	}
}
