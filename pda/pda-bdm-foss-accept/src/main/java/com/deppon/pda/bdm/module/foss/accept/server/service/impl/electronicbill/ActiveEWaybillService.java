package com.deppon.pda.bdm.module.foss.accept.server.service.impl.electronicbill;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillConditionDto;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.server.dao.IAcctDao;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill.ElecBillingScanEntity;

/**   
 * @ClassName ActiveEWaybillService  
 * @Description 激活大客户电子运单（扫描上传）   
 * @author  092038 张贞献  
 * @date 2014-7-10    
 */ 
public class ActiveEWaybillService implements IBusinessService<Void, ElecBillingScanEntity> {
	
	private Logger log = Logger.getLogger(getClass());

	  private IAcctDao acctDao;

	    private IPdaWaybillService pdaWaybillService;
	    
	    public void setAcctDao(IAcctDao acctDao) {
	        this.acctDao = acctDao;
	    }

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
	public ElecBillingScanEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		//解析包体
		ElecBillingScanEntity billingScan = JsonUtil.parseJsonToObject(ElecBillingScanEntity.class, asyncMsg.getContent());
		//pda编号
		billingScan.setPdaCode(asyncMsg.getPdaCode());
		//部门编号
		billingScan.setDeptCode(asyncMsg.getDeptCode());
		//扫描类型
		billingScan.setScanType(asyncMsg.getOperType());
		//用户编号
		billingScan.setScanUser(asyncMsg.getUserCode());
		
		billingScan.setUploadTime(asyncMsg.getUploadTime());
		//设备类型
		billingScan.setPdaType(asyncMsg.getPdaType());
		
		return billingScan;
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
		//验证用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
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
		// 扫描标识
		//Argument.hasText(billingScan.getScanFlag(), "ElecBillingScanEntity.scanFlag");
	}

	/**
	 * 
	 * @description 服务方法
	 * @param asyncMsg
	 * @param billingScan
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Override
	@Transactional
	public Void service(AsyncMsg asyncMsg, ElecBillingScanEntity billingScan) throws PdaBusiException {
		this.validate(asyncMsg, billingScan);
		billingScan.setWaybillType(Constant.WAYBILLTYPE.E_BIGCUSTOMER_WAYBILL);
		// 保存开单扫描数据
		log.debug("---保存开单扫描数据开始---");
		acctDao.saveBigEScanBilling(billingScan);
		log.debug("---保存开单扫描数据开始---");
		
		// 保存开单信息
		log.debug("---保存开单数据开始---");
		acctDao.saveBigEBilling(billingScan);
		log.debug("---保存开单数据开始---");
		
	
		
		
		EWaybillConditionDto eWaybillConditionDto = new EWaybillConditionDto();
		
		//激活的运单号
		List<String> eWaybillNoList = new ArrayList<String>();			
		eWaybillNoList.add(billingScan.getWblCode());		
 		eWaybillConditionDto.seteWaybillNoList(eWaybillNoList);
 		
 		eWaybillConditionDto.setVehicleNo(billingScan.getTruckCode());
 		eWaybillConditionDto.setCustContactCode(billingScan.getTaskCode());
 		 		 		
		log.debug("---调用FOSS激活大客户订单开始---");
		try {
		long startTime = System.currentTimeMillis();
		pdaWaybillService.batchGenerateActiveEWaybillByPda(eWaybillConditionDto);
		long endTime = System.currentTimeMillis();
		QueueMonitorInfo.addTotalFossTime(endTime-startTime);
		log.info("[asyncinfo]提交快递订单接口消耗时间:"+(endTime-startTime)+"ms");				
		} catch (BusinessException e) {
		throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		log.debug("---调用FOSS提交快递订单接口结束---");
		return null;
	}

	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_KD_EWAYBILLSCAN.VERSION;
	}

	/**
	 * 同步还是异步
	 */
	@Override
	public boolean isAsync() {
		return true;
	}
}
