package com.deppon.pda.bdm.module.foss.load.server.service.impl.tallyload;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadGoodsDetailDto;
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
 * 接驳装车扫描实现类
 * @ClassName TranLoadScanService.java 
 * @Description 
 * @author 245955
 * @date 2015-4-11
 */
public class LoadScanService implements IBusinessService<Void, LoadScanEntity>{
	private ILoadDao loadDao;
	//FOSS接驳装车任务接口
	private IPDATransferLoadService pdaTransferLoadService;
	
	private Logger log = Logger.getLogger(getClass());
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 245955
	 * @date 2015-4-11 上午
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
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
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 245955
	 * @date 2015-4-11 上午
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, LoadScanEntity param)
			throws PdaBusiException {
		// 处理强装（运单不在下拉列表中情况）,补齐调用FOSS接口需要的参数
		// 只有一种异常，就是任务已经提交，因此，抓到异常之后，不用调用数据同步接口
		//校验数据合法性
		this.validate(param);
		param.setSyncStatus(Constant.SYNC_STATUS_INIT);
		//保存装车扫描信息
		loadDao.saveDriverLoadScan(param);
		LoadScanDetailDto entity = new LoadScanDetailDto();
		entity.setTaskNo(param.getTaskCode());//任务编号
		entity.setWayBillNo(param.getWblCode());//运单号
		entity.setSerialNo(param.getLabelCode());//流水号
		entity.setDeviceNo(asyncMsg.getPdaCode());//设备号
		entity.setScanTime(param.getScanTime());//扫描时间
		entity.setType(param.getScanStatus());//类型
		entity.setScanState(param.getScanFlag());//扫描状态
		entity.setVolume(param.getVolume());//体积
		entity.setWeight(param.getWeight());//重量
		entity.setGoodsName(param.getCargoName());//货名
		entity.setBeJoinCar(param.getBeJoinCar());//是否合车
		entity.setStockQty(param.getStockQty());//库存件数
		entity.setPack(param.getIsWrap());//包装
		entity.setTransportType(param.getTransType());//运输性质
		entity.setReceiveOrgName(param.getAcctDeptName());//收货部门名称
		entity.setReachOrgName(param.getArrDeptName());//到达部门名称
		entity.setIsPackageScan(param.getIfPackage());//是否直达包
		if(param.getIfPackage()==null || !"Y".equals(param.getIfPackage())){
			Argument.hasText(param.getLabelCode(), "loadScanEntity.labelCode");
		}
		try {
			//调用FOSS接口
			long startTime = System.currentTimeMillis();
			pdaTransferLoadService.loadScan(entity);
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]接驳装车扫描接口消耗时间:"+(endTime-startTime)+"ms");
			return null;
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		
	}
	
	// 处理强装（运单不在下拉列表中情况）
	private void handleNoWaybill(LoadScanEntity param) {
		// 是否多货
		boolean isMore = "MORE".equals(param.getScanStatus());
		// 货物名是否为空
		boolean cargoNameIsNull = "".equals(param.getCargoName())
				|| param.getCargoName() == null;
		if (isMore && cargoNameIsNull) {
			LoadScanDetailDto entity = new LoadScanDetailDto();
			entity.setTaskNo(param.getTaskCode());
			entity.setWayBillNo(param.getWblCode());
			entity.setSerialNo(param.getLabelCode());
			LoadGoodsDetailDto dto = pdaTransferLoadService
					.moreGoodsLoadScan(entity);
			// 设置重量
			double weightPerPiece = (dto.getWayBillQty() == 0) ? 0 : dto
					.getWeight() / dto.getWayBillQty();
			param.setWeight(new BigDecimal(weightPerPiece).setScale(2,
					BigDecimal.ROUND_HALF_UP).doubleValue());
			// 体积
			double volumePerPiece = (dto.getWayBillQty() == 0) ? 0 : dto
					.getVolume() / dto.getWayBillQty();
			param.setVolume(new BigDecimal(volumePerPiece).setScale(2,
					BigDecimal.ROUND_HALF_UP).doubleValue());
			param.setCargoName(dto.getGoodsName()); //
			param.setBeJoinCar(dto.getBeJoinCar());
			param.setStockQty(dto.getStockQty());
			param.setIsWrap(dto.getPacking());
			param.setTransType(dto.getTransportType());
			param.setAcctDeptName(dto.getReachOrgName());
			param.setArrDeptName(dto.getReachOrgName());
		}
	}
	
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 245955
	 * @date 2015-4-11 上午
	 * @param entity
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate( LoadScanEntity entity)
			throws ArgumentInvalidException {
		//检验扫描非空
		Argument.notNull(entity, "loadScanEntity");
		//任务号非空
		Argument.hasText(entity.getTaskCode(), "loadScanEntity.taskCode");
		//扫描时间非空
		Argument.notNull(entity.getScanTime(), "loadScanEntity.scanTime");
	}
	/**
	 * 操作类型
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-11
	 */
	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_LOAD_SCAN_TRAN.VERSION;
	}
   
	/**
	 * 是否异步
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-11
	 */
	@Override
	public boolean isAsync() {
		return true;
	}

	public void setLoadDao(ILoadDao loadDao) {
		this.loadDao = loadDao;
	}
	public void setPdaTransferLoadService(
			IPDATransferLoadService pdaTransferLoadService) {
		this.pdaTransferLoadService = pdaTransferLoadService;
	}
}
