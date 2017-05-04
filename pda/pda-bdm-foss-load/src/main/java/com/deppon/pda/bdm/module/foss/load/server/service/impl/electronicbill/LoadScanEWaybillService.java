package com.deppon.pda.bdm.module.foss.load.server.service.impl.electronicbill;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPickService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadScanDetailDto;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.server.dao.ILoadDao;
import com.deppon.pda.bdm.module.foss.load.shared.domain.LoadCancelScanEntity;




/**
 * @ClassName LoadScanEWaybillService.java 
 * @Description 电子运单二期 装车扫描接口
 * @author 201638
 * @date 2015-1-30
 */
public class LoadScanEWaybillService implements IBusinessService<Void,LoadCancelScanEntity>{
	
	private Logger log = Logger.getLogger(getClass());
	private ILoadDao loadDao;
	private IPDAExpressPickService pdaExpressPickService;
	@Override
	public LoadCancelScanEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		LoadCancelScanEntity entity = JsonUtil.parseJsonToObject(LoadCancelScanEntity.class, asyncMsg.getContent());
		//扫描部门编码
		entity.setDeptCode(asyncMsg.getDeptCode());
		//PDA编码
		entity.setPdaCode(asyncMsg.getPdaCode());
		//扫描人
		entity.setScanUser(asyncMsg.getUserCode());
		//扫描类型  UNLD_05,LOAD_04...
		entity.setScanType(asyncMsg.getOperType());
		//上传时间
		entity.setUploadTime(asyncMsg.getUploadTime());
		return entity;
	}
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, LoadCancelScanEntity loadCancelScanEntity)
			throws PdaBusiException {
		//检验数据合法性
		this.validate(asyncMsg, loadCancelScanEntity);
		//SYNCSTATUS 异步状态不能为空
		loadCancelScanEntity.setSyncStatus(Constant.SYNC_STATUS_INIT);
		//SCANFLAG 扫描标识不能为空
		loadCancelScanEntity.setScanFlag(loadCancelScanEntity.getScanStatus());
		//保存撤销装车扫描数据
		loadDao.saveLoadCaclScan(loadCancelScanEntity);
		
		LoadScanDetailDto dto = new LoadScanDetailDto();
		//任务号
		dto.setTaskNo(loadCancelScanEntity.getTaskCode());
		//运单号
		dto.setWayBillNo(loadCancelScanEntity.getWblCode());
		//流水号
		dto.setSerialNo(loadCancelScanEntity.getLabelCode());
		//重量
		dto.setWeight(loadCancelScanEntity.getWeight());
		//扫描类型:SCAN、CANCEL
		dto.setType(loadCancelScanEntity.getScanStatus());
		//扫描时间
		dto.setScanTime(loadCancelScanEntity.getScanTime());
		try {
			long startTime = System.currentTimeMillis();
			//调用FOSS接口
			pdaExpressPickService.scan(dto);
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]撤销装车扫描接口消耗时间:"+(endTime-startTime)+"ms");
			return null;
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
	}
	private void validate(AsyncMsg asyncMsg, LoadCancelScanEntity entity) throws ArgumentInvalidException{
		//撤销装车扫描非空
		Argument.notNull(entity,"loadCancelScanEntity");
		//任务号非空
		Argument.hasText(entity.getTaskCode(), "loadCancelScanEntity.taskCode");
		//运单号
		Argument.hasText(entity.getWblCode(), "loadCancelScanEntity.getWblCode");
		//流水号
		Argument.hasText(entity.getLabelCode(), "loadCancelScanEntity.getLabelCode");
	}
	
	/**
	 * @description 电子运单二期 接货装车 接货装车扫描
	 * @return ACCT_44
	 * @author 201638
	 * @date 2015-1-30 
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_EXP_RCV_LOAD_SCAN.VERSION;
	}

	/**
	 * @description 异步接口 
	 * @return true
	 * @author 201638
	 * @date 2015-1-30 
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
