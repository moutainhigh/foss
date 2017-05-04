package com.deppon.pda.bdm.module.foss.delivery.server.service.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaPullbackGoodsService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaPullbackgoodsDto;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.cache.DeptCache;
import com.deppon.pda.bdm.module.core.server.cache.UserCache;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.DeliveryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.domain.DeptEntity;
import com.deppon.pda.bdm.module.core.shared.domain.UserEntity;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.delivery.server.dao.IDeliveryDao;
import com.deppon.pda.bdm.module.foss.delivery.server.dao.IDeliveryPdaDao;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.DeryExcpScanEntity;

/**
 * 
  * @ClassName DeryExcepService 
  * @Description 派送异常 
  * @author xujun cometzb@126.com 
  * @date 2012-12-26
 */
/** 
 * 派送异常
  * @ClassName DeryExcepService 
  * @Description TODO 
  * @author xujun cometzb@126.com 
  * @date 2012-12-28  
*/ 
public class DeryExcepService implements IBusinessService<Void, DeryExcpScanEntity> {
	
	private static final Log LOG = LogFactory.getLog(DeryExcepService.class);
	
	private IPdaPullbackGoodsService pdaPullbackGoodsService;
	
	private IDeliveryDao deliveryDao;
	
	private IDeliveryPdaDao deliveryPdaDao;
    private UserCache userCache;
	
	private DeptCache deptCache;
	/**
	 * 解析包体
	 */
	@Override
	public DeryExcpScanEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		DeryExcpScanEntity deryExcpScan = JsonUtil.parseJsonToObject(DeryExcpScanEntity.class, asyncMsg.getContent());
		// 部门编号
		deryExcpScan.setDeptCode(asyncMsg.getDeptCode());
		// pda编号
		deryExcpScan.setPdaCode(asyncMsg.getPdaCode());
		//用户编号
		deryExcpScan.setScanUser(asyncMsg.getUserCode());
		 // 操作类型
		deryExcpScan.setScanType(asyncMsg.getOperType());
		
		deryExcpScan.setUploadTime(asyncMsg.getUploadTime());
		return deryExcpScan;
	}

	/**
	 * 服务方法
	 */
	@Override
	@Transactional
	public Void service(AsyncMsg asyncMsg, DeryExcpScanEntity deryExcpScan) throws PdaBusiException {
		LOG.info(""+deryExcpScan);
		PdaPullbackgoodsDto pullbackgoodsDto = null;
		try {
			//验证数据有效性
			this.validate(asyncMsg,deryExcpScan);
			//快递车牌号均是以“德”开头，若flag为“德”，则为快递
			String flag = deryExcpScan.getTruckCode().substring(0, 1);
			if("德".equals(flag)){
				LOG.info("保存快递派送拉回各时间节点");
				//保存快递派送拉回操作的各时间节点
				UserEntity userEntity = userCache.getUser(deryExcpScan.getScanUser());
				DeptEntity deptEntity = deptCache.getDept(userEntity.getDeptId());
				//set快递员的当前部门编码
				deryExcpScan.setDeptCode(deptEntity.getDeptCode());
//				try {
					deliveryPdaDao.saveDeryExcepTimeNode(deryExcpScan);
//				} catch (Exception e) {
//				}
			}
			//构造参数
			pullbackgoodsDto = wrapPdaPullbackgoodsDto(
					asyncMsg, deryExcpScan);
			long startTime = System.currentTimeMillis();
			//保存拉回货物信息
			String res = pdaPullbackGoodsService.addPullbackGoodsSign(pullbackgoodsDto);
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			LOG.info("[asyncinfo]派送拉回接口消耗时间:"+(endTime-startTime)+"ms");
			//保存扫描信息和派送异常信息
			saveScanMsgAndExceptionMsg(deryExcpScan);
			System.out.println(res);
		} catch (BusinessException e) {
//			LOG.error("派送拉回异常："+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		return null;
	}

	/**
	 * 
	* @Title: saveScanMsgAndExceptionMsg
	* @Description: 保存扫描信息和派送异常信息
	* @param deryExcpScan    
	* @return void  返回类型 
	* @throws
	 */
	@Transactional
	private void saveScanMsgAndExceptionMsg(DeryExcpScanEntity deryExcpScan){
		deliveryDao.saveDeryExcepScanMsg(deryExcpScan);
		deliveryDao.saveDeryExcepSign(deryExcpScan);
	}

	/**
	 * 封装数据
	 * @param asyncMsg
	 * @param deryExcpScan
	 * @return
	 */
	private PdaPullbackgoodsDto wrapPdaPullbackgoodsDto(AsyncMsg asyncMsg,
			DeryExcpScanEntity deryExcpScan){
		PdaPullbackgoodsDto pullbackgoodsDto = new PdaPullbackgoodsDto();
		//到达联编号
		pullbackgoodsDto.setArrivesheetNo(deryExcpScan.getArrInfoCode());
		//用户编号
		pullbackgoodsDto.setDriverCode(asyncMsg.getUserCode());
		//件数
		pullbackgoodsDto.setPullbackQty(deryExcpScan.getPieces());
		/*
		 * wwn 3013-05-31 16:17
		 * 2014 3.22 取消过滤
		 * */
		//异常原因
		pullbackgoodsDto.setPullbackReason(deryExcpScan.getExcpReason());
		//扫描时间
		pullbackgoodsDto.setPullbackTime(deryExcpScan.getScanTime());
		/*
		 * wwn 3013-05-31 16:17
		 * 
		 * */
		//备注
		pullbackgoodsDto.setSignNote(deryExcpScan.getRemark());
		//车牌号
		pullbackgoodsDto.setVehicleNo(deryExcpScan.getTruckCode());
		//运单号
		pullbackgoodsDto.setWaybillNo(deryExcpScan.getWblCode());
		//部门编号
		pullbackgoodsDto.setOperateOrgCode(asyncMsg.getDeptCode());
		//author:245960 Date:2015-06-05
		//再次派送时间
		pullbackgoodsDto.setNextDeliverTime(deryExcpScan.getNextDeliverTime());
		
		return pullbackgoodsDto;
	}

	/**
	 * 验证数据合法性
	 * @param asyncMsg
	 * @param deryExcpScan
	 */
	private void validate(AsyncMsg asyncMsg, DeryExcpScanEntity deryExcpScan){
		Argument.notNull(asyncMsg, "AsyncMsg");
		//pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		//用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//操作类型
		Argument.hasText(asyncMsg.getOperType(), "AsyncMsg.operType");
		
		Argument.notNull(deryExcpScan,"DeryExcpScanEntity");
		//扫描数据UUID
		Argument.hasText(deryExcpScan.getId(), "DeryExcpScanEntity.id");
		//运单号
		Argument.hasText(deryExcpScan.getWblCode(), "DeryExcpScanEntity.wblCode");
		//扫描标识
		Argument.hasText(deryExcpScan.getScanFlag(), "DeryExcpScanEntity.scanFlag");
		//扫描时间
		Argument.notNull(deryExcpScan.getScanTime(), "DeryExcpScanEntity.scanTime");
		//异常原因
		Argument.hasText(deryExcpScan.getExcpReason(), "DeryExcpScanEntity.excpReason");
		//车牌号
		Argument.hasText(deryExcpScan.getTruckCode(), "DeryExcpScanEntity.truckCode");
		//到达联编号
		Argument.hasText(deryExcpScan.getArrInfoCode(), "DeryExcpScanEntity.arrInfoCode");
		
	}

	/**
	 * 服务类型
	 */
	@Override
	public String getOperType() {
		return DeliveryConstant.OPER_TYPE_DERY_EXCP.VERSION;
	}

	/**
	 * 是否同步
	 */
	@Override
	public boolean isAsync() {
		return true;
	}

	public void setPdaPullbackGoodsService(IPdaPullbackGoodsService pdaPullbackGoodsService) {
		this.pdaPullbackGoodsService = pdaPullbackGoodsService;
	}

	public void setDeliveryDao(IDeliveryDao deliveryDao) {
		this.deliveryDao = deliveryDao;
	}

	public void setDeliveryPdaDao(IDeliveryPdaDao deliveryPdaDao) {
		this.deliveryPdaDao = deliveryPdaDao;
	}

	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}

	public void setDeptCache(DeptCache deptCache) {
		this.deptCache = deptCache;
	}
	
}
