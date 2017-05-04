package com.deppon.pda.bdm.module.foss.unload.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockPositionNumberEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.server.dao.IUnloadDao;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.StockPositionList;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.UploadStockPositionEntity;

public class UploadStockPositionService implements IBusinessService<Void, UploadStockPositionEntity>{
	private Logger log = Logger.getLogger(getClass());
	private IPDAStockService pdaStockService;
	private IUnloadDao unloadDao;
	/** (非 Javadoc)  
	 * <p>Title: parseBody</p> 
	 * <p>Description: </p> 
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException  
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg) 
	 */
	@Override
	public UploadStockPositionEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		UploadStockPositionEntity entity= new UploadStockPositionEntity();
		entity = JsonUtil.parseJsonToObject(UploadStockPositionEntity.class,
				asyncMsg.getContent());
		if(null!=entity.getStockList()){
			int size =entity.getStockList().size();
			for(int i=0;i<size;i++){
				entity.getStockList().get(i).setDeptCode(asyncMsg.getDeptCode());
				entity.getStockList().get(i).setPdaCode(asyncMsg.getPdaCode());
				entity.getStockList().get(i).setScanUser(asyncMsg.getUserCode());
				entity.getStockList().get(i).setScanType(asyncMsg.getOperType());		
				entity.getStockList().get(i).setUploadTime(asyncMsg.getUploadTime());
				entity.getStockList().get(i).setOrgCode(asyncMsg.getDeptCode());				
			}
		}
		
		return entity;
	}

	/** (非 Javadoc)  
	 * <p>Title: service</p> 
	 * <p>Description: </p> 
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException  
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object) 
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, UploadStockPositionEntity param)
			throws PdaBusiException {
	
		log.debug("---调用FOSS保存货区编号到扫描表---");
	
		List<StockPositionNumberEntity> scp = new ArrayList<StockPositionNumberEntity>();
		StockPositionNumberEntity spentity = null;
		for(StockPositionList entity:param.getStockList()){
			spentity = new StockPositionNumberEntity();
			spentity.setOrgCode(entity.getOrgCode());
			spentity.setSerialNO(entity.getLabelCode());
			spentity.setStockPositionNumber(entity.getStockPositionNumber());
			spentity.setWaybillNO(entity.getWblCode());
			spentity.setScanTime(param.getScanTime());
			scp.add(spentity);
			entity.setScanTime(param.getScanTime());
			entity.setId(UUIDUtils.getUUID());
			unloadDao.saveStockPosition(entity);
		}
		
		
		try {
			
			
			long startTime = System.currentTimeMillis();
			pdaStockService.saveStockPositionNumber(scp);		
			
			log.debug("---调用FOSS保存货区编号：成功---");
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]调用FOSS货区编号保存耗时:"+(endTime-startTime)+"ms");
			
			
		
			return null;
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:34:49
	 * @return 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#getOperType()
	 */
	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLD_SSTOCKPOSITION_NUMBER.VERSION;
	}

	@Override
	public boolean isAsync() {
		return true;
	}

	public void setPdaStockService(IPDAStockService pdaStockService) {
		this.pdaStockService = pdaStockService;
	}

	public void setUnloadDao(IUnloadDao unloadDao) {
		this.unloadDao = unloadDao;
	}

	
}
