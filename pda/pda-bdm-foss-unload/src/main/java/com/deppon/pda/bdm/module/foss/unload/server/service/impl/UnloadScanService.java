package com.deppon.pda.bdm.module.foss.unload.server.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.Argument;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadTaskService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadGoodsDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadScanDetailDto;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.server.dao.IUnloadDao;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.UnldScanEntity;

/**
 * 卸车扫描service实现类
 * 
 * @author gaojia
 * @date Sep 7,2012 9:48:30 AM
 * @version 1.0
 * @since
 */
public class UnloadScanService implements
		IBusinessService<Void, UnldScanEntity> {
	private IPDAUnloadTaskService pdaUnloadTaskService;
	private IUnloadDao unloadDao;
	private Logger log = Logger.getLogger(getClass());

	/**
	 * 
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author Administrator
	 * @date 2013-3-20 下午6:36:21
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public UnldScanEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		UnldScanEntity entity = new UnldScanEntity();
		entity = JsonUtil.parseJsonToObject(UnldScanEntity.class,
				asyncMsg.getContent());
		entity.setDeptCode(asyncMsg.getDeptCode());
		entity.setPdaCode(asyncMsg.getPdaCode());
		entity.setScanUser(asyncMsg.getUserCode());
		entity.setScanType(asyncMsg.getOperType());
		entity.setUploadTime(asyncMsg.getUploadTime());
//		entity.setUploadTime(new Date());
		return entity;
	}

	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, UnldScanEntity param)
			throws PdaBusiException {
		
		// 处理强卸（运单不在下拉列表中情况）,补齐调用FOSS接口需要的参数
		// 只有一种异常，就是任务已经提交，因此，抓到异常之后，不用调用数据同步接口
		try {
			handleNoWaybill(param);
		} catch (BusinessException e1) {
			log.error("运单校验不通过：任务号【"+param.getTaskCode()+"】    标签号【"+param.getLabelCode());
			log.error(e1.getMessage());
			throw new FossInterfaceException(e1.getCause(), e1.getErrorCode());
		} 
		
		this.validate(param);
		unloadDao.saveUnldScan(param);
		UnloadScanDetailDto req = new UnloadScanDetailDto();
		req.setWayBillNo(param.getWblCode());//
		req.setSerialNo(param.getLabelCode());//
		req.setTaskNo(param.getTaskCode());//
		req.setType(param.getScanStatus());//
		req.setWeight(param.getWeight());//
		try {
			double volume = Double.parseDouble(param.getVolume());
			if (volume > 99999999 || volume < 0) {
				req.setVolume(0);
			} else {
				req.setVolume(volume);
			}
		} catch (Exception e) {
			req.setVolume(0);
		}
		req.setGoodsName(param.getCargoName());//
		req.setDeviceNo(asyncMsg.getPdaCode());//
		req.setScanState(param.getScanFlag());//
		req.setScanTime(param.getScanTime());//
		req.setBillNo(param.getBillNo());//
		req.setDestOrgCode(param.getDestOrgCode());//
		req.setOrigOrgCode(param.getOrigOrgCode());//
		req.setTransportType(param.getTransType());//
		req.setPack(param.getPacking());//
		req.setHandOverQty(param.getRcptPieces());
		req.setBePartial(param.getBePartial());
		req.setReachOrgCode(param.getReachOrgCode());//提货网点部门编码
		req.setIsPackageScan(param.getIfPackage());//是否包扫描
		//运单生效状态 - YES NO
		req.setWayBillStatus(param.getWayBillStatus());
		if(param.getIfPackage()==null || !"Y".equals(param.getIfPackage())){
			Argument.hasText(param.getLabelCode(),"unldScanEntity.labelCode");
		}
		try {
			long startTime = System.currentTimeMillis();
			pdaUnloadTaskService.unloadScan(req);
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime - startTime);
			log.info("[asyncinfo]卸车扫描接口消耗时间:" + (endTime - startTime) + "ms");
			return null;
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}

	}

	// 处理强卸（运单不在下拉列表中情况）
	private void handleNoWaybill(UnldScanEntity param) {
		// 是否多货
		boolean isMore = "MORE".equals(param.getScanStatus());
		// 货物名是否为空
		boolean cargoNameIsNull = "".equals(param.getCargoName())
				|| param.getCargoName() == null;
		if (isMore && cargoNameIsNull) {
			UnloadGoodsDetailDto dto = pdaUnloadTaskService
					.moreGoodsUnloadVerify(param.getTaskCode(),
							param.getWblCode(), param.getLabelCode());
			// 设置重量
			double weightPerPiece = (dto.getWayBillQty() == 0) ? 0 : dto
					.getWeight() / dto.getWayBillQty();
			param.setWeight(new BigDecimal(weightPerPiece).setScale(2,
					BigDecimal.ROUND_HALF_UP).doubleValue());
			// 体积
			double volumePerPiece = (dto.getWayBillQty() == 0) ? 0 : dto
					.getVolume() / dto.getWayBillQty();
			param.setVolume(String.valueOf(new BigDecimal(volumePerPiece)
					.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
			param.setCargoName(dto.getGoodsName()); //
			param.setDestOrgCode(dto.getDestOrgCode());
			param.setOrigOrgCode(dto.getOrigOrgCode());
			param.setTransType(dto.getTransportType());
			param.setPacking(dto.getPacking());
			param.setRcptPieces(0); //
			param.setBePartial("Y");//
		}
	}

	/**
	 * 
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author Administrator
	 * @date 2013-3-20 下午6:36:27
	 * @param unldScanEnity
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate(UnldScanEntity unldScanEnity)
			throws ArgumentInvalidException {
		// 检验卸车扫描非空
		Argument.notNull(unldScanEnity, "unldScanEnity");
		// 检验标签号非空
	//	Argument.hasText(unldScanEnity.getLabelCode(),
	//			"unldScanEntity.labelCode");
		// 检验任务号非空
		Argument.hasText(unldScanEnity.getTaskCode(), "unldScanEntity.taskCode");
		// 检验扫描时间非空
		Argument.notNull(unldScanEnity.getScanTime(), "unldScanEntity.scanTime");
	}

	/**
	 * 
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author Administrator
	 * @date 2013-3-20 下午6:36:31
	 * @return
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#getOperType()
	 */
	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLD_SCAN.VERSION;
	}

	/**
	 * @description 异步
	 * @return true
	 * @author 201638
	 * @date 2015-2-7 
	 */
	@Override
	public boolean isAsync() {
		return true;
	}

	public void setPdaUnloadTaskService(
			IPDAUnloadTaskService pdaUnloadTaskService) {
		this.pdaUnloadTaskService = pdaUnloadTaskService;
	}

	public void setUnloadDao(IUnloadDao unloadDao) {
		this.unloadDao = unloadDao;
	}

	public static void main(String[] args) {

		BigDecimal d = new BigDecimal(0);
		double d1 = d.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println(d1);
		System.out.println(Math.ceil(2 / 3));
	}
}
