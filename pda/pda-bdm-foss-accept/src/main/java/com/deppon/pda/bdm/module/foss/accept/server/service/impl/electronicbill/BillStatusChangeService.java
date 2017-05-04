package com.deppon.pda.bdm.module.foss.accept.server.service.impl.electronicbill;


import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillConditionDto;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill.BillStatusChangeEntity;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.server.service.impl 
 * @file BackOrderService.java 
 * @description 退回订单服务类
 * @author ChenLiang
 * @created 2012-12-29 下午1:42:05    
 * @version 1.0
 */
public class BillStatusChangeService implements IBusinessService<Void, BillStatusChangeEntity> {

	private Logger log = Logger.getLogger(getClass());
	
	  // private IAcctDao acctDao;

	    private IPdaWaybillService pdaWaybillService;
	    
//	    public void setAcctDao(IAcctDao acctDao) {
//	        this.acctDao = acctDao;
//	    }

	    public void setPdaWaybillService(IPdaWaybillService pdaWaybillService) {
	        this.pdaWaybillService = pdaWaybillService;
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
	public BillStatusChangeEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		//解析包体
		BillStatusChangeEntity backOrderScan = JsonUtil.parseJsonToObject(BillStatusChangeEntity.class, asyncMsg.getContent());
		//pda编号
		backOrderScan.setPdaCode(asyncMsg.getPdaCode());
		//部门编号
		backOrderScan.setDeptCode(asyncMsg.getDeptCode());
		//扫描类型
		backOrderScan.setScanType(asyncMsg.getOperType());
		//用户编号
		backOrderScan.setScanUser(asyncMsg.getUserCode());
		//上传时间
		//backOrderScan.setUploadTime(asyncMsg.getUploadTime());
       return backOrderScan;
	}
	
	/**
	 * 
	 * @description 校验数据合法性
	 * @param backOrderScan
	 * @throws PdaBusiException 
	 * @created 2012-12-29 上午10:47:20
	 */
	private void validate(AsyncMsg asyncMsg, BillStatusChangeEntity backOrderScan) throws PdaBusiException {
		Argument.notNull(asyncMsg, "AsyncMsg");
		//验证pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//验证用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//验证部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		// 判断订单返回实体
		Argument.notNull(backOrderScan, "BackOrderScanEntity");
	
	}

	/**
	 * 
	 * @description 服务方法
	 * @param asyncMsg
	 * @param backOrderScan
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Override
	@Transactional
	public Void service(AsyncMsg asyncMsg, BillStatusChangeEntity backOrderScan) throws PdaBusiException {
		this.validate(asyncMsg, backOrderScan);
		
		//BackOrderScanEntity backOrder=null;
		
	/*	List<String> ewaybills = backOrderScan.getOrderCodes();
		log.debug("---保存订单已退回扫描数据开始---");
		if(ewaybills!=null){
		  int count = 0;
			for(String waybill:ewaybills){
			backOrder = new BackOrderScanEntity();
			
			//id
			backOrder.setId(backOrderScan.getId()+"-"+count++);
			//pda编号
			backOrder.setPdaCode(backOrderScan.getPdaCode());
			//扫描时间（无，用上传时间替换）
			backOrder.setScanTime(backOrderScan.getUploadTime());			
			//扫描时间操作类型
			backOrder.setScanType(asyncMsg.getOperType());
			//部门编号
			backOrder.setDeptCode(backOrderScan.getDeptCode());
			//用户工号
			backOrder.setScanUser(backOrderScan.getScanUser());
			//上传时间
			backOrder.setUploadTime(backOrderScan.getUploadTime());
			//订单单号
			backOrder.setOrderCode(waybill);
			//退回原因
			backOrder.setBackReason(backOrderScan.getRetreatReason());
			//车牌号
			backOrder.setTruckCode(backOrderScan.getTruckCode());
			//备注
			backOrder.setRemark(backOrderScan.getRetreatRemark()==null?"":backOrderScan.getRetreatRemark());
			
			acctDao.saveBackEWaybills(backOrder);
		  		
			}
			
			
			
			
			
			
		}
		
		log.debug("---保存订单已退回扫描数据成功---");
		
		
	*/
		
		EWaybillConditionDto eWaybillConditionDto=null;
		
		eWaybillConditionDto = new EWaybillConditionDto();
		//退回订单集合
		eWaybillConditionDto.setOrderNoList(backOrderScan.getOrderCodes());
		//车牌号
		eWaybillConditionDto.setVehicleNo(backOrderScan.getTruckCode());
		//备注
		eWaybillConditionDto.setRemark(backOrderScan.getRetreatRemark());
		// 快递员工号
		eWaybillConditionDto.setDriverCode(asyncMsg.getUserCode());
		// 退回原因
		eWaybillConditionDto.setReturnReason(backOrderScan.getRetreatReason());
		try {
			long startTime = System.currentTimeMillis();
			log.info("---调用FOSS退回订单接口开始---");
			//调用foss接口	
			pdaWaybillService.returnEWaybillByPda(eWaybillConditionDto);
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]退回订单接口消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		log.info("---调用FOSS退回订单接口结束---");
		return null;
	}

	
	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_KD_EWAYBILLSTATE.VERSION;
	}

	/**
	 * 同步还是异步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}
}
