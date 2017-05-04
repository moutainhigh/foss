package com.deppon.pda.bdm.module.foss.accept.server.service.impl;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.dto.PDAGoodsAttrDto;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.server.dao.IAcctDao;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.WblWghtAndVoleEntity;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.server.service.impl 
 * @file UploadWghtAndVoleService.java 
 * @description 上传货物重量体积接口
 * @author ChenLiang
 * @created 2012-12-26 上午10:07:30    
 * @version 1.0
 */
public class UploadWghtAndVoleService implements IBusinessService<Void, WblWghtAndVoleEntity> {

	private Logger log = Logger.getLogger(getClass());
	
	private IAcctDao acctDao;
	
	private IPdaWaybillService pdaWaybillService;
	
	public void setPdaWaybillService(IPdaWaybillService pdaWaybillService) {
		this.pdaWaybillService = pdaWaybillService;
	}

	public void setAcctDao(IAcctDao acctDao) {
		this.acctDao = acctDao;
	}

	/**
	 * 
	 * @description 解析包体
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public WblWghtAndVoleEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		//解析包体
		WblWghtAndVoleEntity wblWghtAndVoleVo = JsonUtil.parseJsonToObject(WblWghtAndVoleEntity.class, asyncMsg.getContent());
		//pda编号
		wblWghtAndVoleVo.setPdaCode(asyncMsg.getPdaCode());
		//部门编号
		wblWghtAndVoleVo.setDeptCode(asyncMsg.getDeptCode());
		//扫描类型
		wblWghtAndVoleVo.setScanType(asyncMsg.getOperType());
		//用户编号
		wblWghtAndVoleVo.setScanUser(asyncMsg.getUserCode());
		
		wblWghtAndVoleVo.setUploadTime(asyncMsg.getUploadTime());
		return wblWghtAndVoleVo;
	}

	/**
	 * 
	 * @description 校验数据合法性 描述
	 * @param asyncMsg
	 * @param wblWghtAndVole 
	 * @created 2012-12-31 下午2:25:16
	 */
	private void validate(AsyncMsg asyncMsg, WblWghtAndVoleEntity wblWghtAndVole)  throws PdaBusiException {
		Argument.notNull(asyncMsg, "AsyncMsg");
		//验证pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//验证用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//验证部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		
		Argument.notNull(wblWghtAndVole, "WblWghtAndVoleEntity");
		Argument.hasText(wblWghtAndVole.getId(), "WblWghtAndVoleEntity.id");
		//验证运单号
		Argument.hasText(wblWghtAndVole.getWblCode(), "WblWghtAndVoleEntity.wblCode");
		//验证扫描标识
		Argument.hasText(wblWghtAndVole.getScanFlag(), "WblWghtAndVoleEntity.scanFlag");
		//验证扫描时间
		Argument.notNull(wblWghtAndVole.getScanTime(), "WblWghtAndVoleEntity.scanTime");
		//验证体积
		//Argument.isPositiveNum(wblWghtAndVole.getWeight(), "WblWghtAndVoleEntity.weight");
		//Argument.isPositiveNum(wblWghtAndVole.getVolume(), "WblWghtAndVoleEntity.volume");
	}
	
	/**
	 * 
	 * @description 
	 * @param asyncMsg
	 * @param wblWghtAndVole
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Override
	@Transactional
	public Void service(AsyncMsg asyncMsg, WblWghtAndVoleEntity wblWghtAndVole) throws PdaBusiException {
		this.validate(asyncMsg, wblWghtAndVole);
		wblWghtAndVole.setSyncStatus(Constant.SYNC_STATUS_INIT);
		log.debug("---保存货物重量体积开始---");
		acctDao.saveWghtAndVole(wblWghtAndVole);
		log.debug("---保存货物重量体积结束---");
		
		PDAGoodsAttrDto goodAttr = new PDAGoodsAttrDto();
		// 运单号
		goodAttr.setWaybillNo(wblWghtAndVole.getWblCode());
		// 重量
		goodAttr.setWeight(BigDecimal.valueOf(wblWghtAndVole.getWeight()));
		// 体积
		goodAttr.setVolume(BigDecimal.valueOf(wblWghtAndVole.getVolume()));
		// 操作人工号
		goodAttr.setOperator(wblWghtAndVole.getScanUser());
		// 操作人所在部门编号
		goodAttr.setOperaterOrg(wblWghtAndVole.getDeptCode());
		// 操作时间
		goodAttr.setOperTime(wblWghtAndVole.getScanTime());
		try {
			log.debug("---调用FOSS上传货物重量体积接口开始---");
			long startTime = System.currentTimeMillis();
			pdaWaybillService.submitGoodsAttr(goodAttr);
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]补录体积重量接口消耗时间:"+(endTime-startTime)+"ms");
			//log.debug("---调用FOSS上传货物重量体积接口返回结果："+resultDto.getCode()+","+resultDto.getMsg()+"---");
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		log.debug("---调用FOSS上传货物重量体积接口结束---");
		return null;
	}

	/**
	 * 服务类型
	 */
	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLD_VOL_WGT_MKP.VERSION;
	}

	/**
	 * 是否同步
	 */
	@Override
	public boolean isAsync() {
		return true;
	}

}
