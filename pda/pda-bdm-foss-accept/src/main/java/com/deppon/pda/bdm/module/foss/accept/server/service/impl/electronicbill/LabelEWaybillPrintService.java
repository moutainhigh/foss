package com.deppon.pda.bdm.module.foss.accept.server.service.impl.electronicbill;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodPDADto;
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
import com.deppon.pda.bdm.module.foss.accept.shared.domain.LabelPrintEntity;


/**   
 * @ClassName LabelEWaybillPrintService  
 * @Description 电动运单上传标签打印信息  
 * @author  092038 张贞献  
 * @date 2014-7-10    
 */ 
public class LabelEWaybillPrintService implements IBusinessService<Void, LabelPrintEntity> {
	
	private Logger log = Logger.getLogger(getClass());

	private IPdaWaybillService pdaWaybillService;

	private IAcctDao acctDao;
	
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
	public LabelPrintEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		//解析包体
		LabelPrintEntity labelPrint = JsonUtil.parseJsonToObject(LabelPrintEntity.class, asyncMsg.getContent());
		//pda编号
		labelPrint.setPdaCode(asyncMsg.getPdaCode());
		//部门编号
		labelPrint.setDeptCode(asyncMsg.getDeptCode());
		//扫描类型
		labelPrint.setScanType(asyncMsg.getOperType());
		//用户编号
		labelPrint.setScanUser(asyncMsg.getUserCode());
		
		labelPrint.setUploadTime(asyncMsg.getUploadTime());
		return labelPrint;
	}

	/**
	 * 
	 * @description 校验数据合法性
	 * @param asyncMsg
	 * @param labelPrint
	 * @throws PdaBusiException 
	 * @created 2012-12-29 下午8:11:21
	 */
	private void validate(AsyncMsg asyncMsg, LabelPrintEntity labelPrint) throws PdaBusiException {
		Argument.notNull(asyncMsg, "AsyncMsg");
		//验证pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//验证用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//验证部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		
		// 判断重打标签实体
		Argument.notNull(labelPrint, "LabelPrintEntity");
		Argument.hasText(labelPrint.getId(), "LabelPrintEntity.id");
		// 判断打印时间
		Argument.notNull(labelPrint.getScanTime(), "LabelPrintEntity.scanTime");
		// 判断运单号
		Argument.hasText(labelPrint.getWblCode(), "LabelPrintEntity.wblCode");
		// 判断标签号
		Argument.hasText(labelPrint.getLabelCode(), "LabelPrintEntity.labelCode");
		// 扫描标识
		Argument.hasText(labelPrint.getScanFlag(), "LabelPrintEntity.scanFlag");
		
	}

	/**
	 * 服务方法
	 */
	@Override
	@Transactional
	public Void service(AsyncMsg asyncMsg, LabelPrintEntity labelPrint) throws PdaBusiException {
		this.validate(asyncMsg, labelPrint);
		labelPrint.setSyncStatus(Constant.SYNC_STATUS_INIT);
		
		log.debug("---保存打印标签扫描数据开始---");
		acctDao.saveLabelPrint(labelPrint);
		log.debug("---保存打印标签扫描数据成功---");
		
		LabeledGoodPDADto dto = new LabeledGoodPDADto();
		
		//扫描时间
		dto.setPrintTime(labelPrint.getScanTime());
		//扫描用户编号
		dto.setPrintPerson(labelPrint.getScanUser());
		//运单号
		dto.setWaybillNo(labelPrint.getWblCode());
		//流水号
		dto.setSerialNo(labelPrint.getLabelCode());
		log.debug("---调用FOSS电子运单标签打印接口---");
//		ResultDto result = null;
		try {
			//result = 
			long startTime = System.currentTimeMillis();
			pdaWaybillService.uploadLabeledGood(dto);
			//log.debug("---调用FOSS标签打印接口返回结果："+result.getCode()+"---");
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]标签打印上传接口消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		log.debug("---调用FOSS电子运单标签打印接口---");
		return null;
	}

	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_KD_EWAYBILLLABELPRINT.VERSION;
	}

	/**
	 * 是否同步
	 */
	@Override
	public boolean isAsync() {
		return true;
	}

}
