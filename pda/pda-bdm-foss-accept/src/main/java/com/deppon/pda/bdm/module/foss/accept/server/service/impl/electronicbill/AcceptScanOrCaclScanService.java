package com.deppon.pda.bdm.module.foss.accept.server.service.impl.electronicbill;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.Argument;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.dto.PdaScanQueryDto;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.server.dao.IAcctDao;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill.ElecBillingScanEntity;

/**
 * 接货收件扫描或撤销扫描service实现类
 * 
 * @author gaojia
 * @date Sep 7,2012 9:48:30 AM
 * @version 1.0
 * @since
 */
public class AcceptScanOrCaclScanService implements IBusinessService<Void, ElecBillingScanEntity> {
	private IPdaWaybillService pdaWaybillService;
	private IAcctDao acctDao;

	public void setAcctDao(IAcctDao acctDao) {
		this.acctDao = acctDao;
	}

	private Logger log = Logger.getLogger(getClass());

	/**
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
	public ElecBillingScanEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		// 解析body
		ElecBillingScanEntity billingScan = JsonUtil.parseJsonToObject(ElecBillingScanEntity.class, asyncMsg.getContent());
		// pda编号
		billingScan.setPdaCode(asyncMsg.getPdaCode());
		// 部门编号
		billingScan.setDeptCode(asyncMsg.getDeptCode());
		// 扫描类型
		billingScan.setScanType(asyncMsg.getOperType());
		// 用户编号
		billingScan.setScanUser(asyncMsg.getUserCode());
		//上传时间
		billingScan.setUploadTime(asyncMsg.getUploadTime());
		// 设备类型
		billingScan.setPdaType(asyncMsg.getPdaType());
		return billingScan;
	}

	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, ElecBillingScanEntity billingScan) throws PdaBusiException {
		//校验参数
		this.validate(asyncMsg, billingScan);
		billingScan.setWaybillType(Constant.WAYBILLTYPE.E_WAYBILL);
		billingScan.setTruckCode("德"+asyncMsg.getUserCode());
		// 保存开单扫描数据
		log.info("---保存开单扫描数据开始---");
		//保存电子运单二期扫描数据
		acctDao.saveEWaybillScan(billingScan);
		log.info("---保存开单扫描数据结束---");
		
		// 保存开单信息
		log.info("---保存开单数据开始---");
		//保存电子运单二期开单信息
		acctDao.saveEWaybillBilling(billingScan);
		log.info("---保存开单数据结束---");
		//PDA扫描查询参数dto
		PdaScanQueryDto dto = new PdaScanQueryDto();
		//运单号
		dto.setWaybillNo(billingScan.getWblCode());
		//流水号
		dto.setSerialNo(billingScan.getLabelCode());
		//任务号
		dto.setTaskId(billingScan.getTaskCode());
		//司机工号
		dto.setDriverCode(billingScan.getScanUser());
		//扫描状态 SCAN、CANCEL
		if(billingScan.getScanStatus() != null && "SCANED".equals(billingScan.getScanStatus())){
			dto.setScanType("SCAN");
		}else if(billingScan.getScanStatus() != null && "CANCELED".equals(billingScan.getScanStatus())){
			dto.setScanType("CANCEL");
		}
		//扫描时间
		dto.setScanTime(billingScan.getScanTime());
		//收入部门
		dto.setReceiveOrgCode(billingScan.getRevenueCode());
		//重量
		try {
			dto.setGoodsWeight(new BigDecimal(billingScan.getWeight()));
		} catch (Exception e) {
			log.info(e);
			dto.setGoodsWeight(null);
		}
		//调用FOSS接口
		try {
			long startTime = System.currentTimeMillis();
			pdaWaybillService.savePdaScanInfo(dto);
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime - startTime);
			log.info("电子运单正反扫扫描接口消耗时间:" + (endTime - startTime) + "ms");
			return null;
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}

	}

	/**
	 * 
	 * @description 校验数据合法性 描述
	 * @param billingScan
	 * @throws PdaBusiException 
	 * @created 2012-12-29 下午2:04:03
	 */
	private void validate(AsyncMsg asyncMsg, ElecBillingScanEntity billingScan) throws PdaBusiException {
		Argument.notNull(asyncMsg, "AsyncMsg");
		//验证pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//验证部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		
		// 判断开单实体
		Argument.notNull(billingScan, "ElecBillingScanEntity");
		// 判断操作类型
		Argument.hasText(billingScan.getScanType(), "ElecBillingScanEntity.scanType");
		// 判断运单号
		Argument.hasText(billingScan.getWblCode(), "ElecBillingScanEntity.wblCode");	
		// 司机编号
		Argument.hasText(billingScan.getScanUser(), "ElecBillingScanEntity.scanUser");
	}

	
	/**
	 * @description 电子运单二期 接货收件 扫描接口 <br>
	 * @return ACCT_39
	 * @author 201638
	 * @date 2015-3-2 
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_EXP_RCV_SCAN.VERSION;
	}

	/**
	 * @description 异步接口
	 * @return true
	 * @author 201638
	 * @date 2015-2-4 
	 */
	@Override
	public boolean isAsync() {
		return true;
	}
	
	public void setPdaWaybillService(IPdaWaybillService pdaWaybillService) {
		this.pdaWaybillService = pdaWaybillService;
	}

}
