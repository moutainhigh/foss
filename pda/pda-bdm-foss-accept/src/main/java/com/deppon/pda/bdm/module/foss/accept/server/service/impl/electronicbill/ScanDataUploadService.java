package com.deppon.pda.bdm.module.foss.accept.server.service.impl.electronicbill;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.Argument;
import com.deppon.foss.module.pickup.pda.api.server.service.ILTLEWaybillPdaScanService;
import com.deppon.foss.module.pickup.waybill.shared.dto.PdaAppInfoDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.server.dao.IAcctDao;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill.ScanDataUploadEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill.WaybillInfoEntitys;

/**
 * 
 * @ClassName: ScanDataUploadService 
 * @Description: TODO(扫描数据上传service) 
 * @author &268974  wangzhili
 * @date 2016-5-17 下午2:55:02 
 *
 */
public class ScanDataUploadService implements IBusinessService<Void, ScanDataUploadEntity>{

	private Logger log = Logger.getLogger(getClass());
	private IAcctDao acctDao;
	private ILTLEWaybillPdaScanService lTLEWaybillPdaScanService;
	//解析包体
	@Override
	public ScanDataUploadEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		ScanDataUploadEntity entity  = JsonUtil.parseJsonToObject(ScanDataUploadEntity.class, asyncMsg.getContent());
		//PDA设备号
		entity.setPdaCode(asyncMsg.getPdaCode());
		//扫描类型
		entity.setScanType(asyncMsg.getOperType());
		//上传时间
		entity.setUploadTime(asyncMsg.getUploadTime());
		//扫描部门
		entity.setDeptCode(asyncMsg.getDeptCode());
		return entity;
	}

	//业务方法入口
	@Override
	public Void service(AsyncMsg asyncMsg, ScanDataUploadEntity param)
			throws PdaBusiException {
		//校验参数
		this.validate(asyncMsg, param);
		//调用foss接口
		try{	
			log.info("----调用foss接口开始----任务号："+param.getTaskCode());
			long startTime = System.currentTimeMillis();
			//封装参数
			List<PdaAppInfoDto> pdaAppInfo = wropAppOverTask(param,asyncMsg);
			lTLEWaybillPdaScanService.appOverTask(pdaAppInfo);
			long endTime = System.currentTimeMillis();
			log.info("调用foss接口耗时:" + (endTime - startTime));
			//保存扫描数据
			this.saveEWaybillScanData(param);
			log.info("----扫描数据保存结束");
		}catch(BusinessException e){
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		} 
		return null;
	}

	//操作类型
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ERP_SCAN_UPL.VERSION;
	}

	//同步
	@Override
	public boolean isAsync() {
		return true;
	}
	/**
	 * 
	 * @Title: validate 
	 * @Description: TODO(校验参数)  
	 * @return void    返回类型 
	 * @param asyncMsg
	 * @param entity
	 * @author： 268974  wangzhili
	 */
	private void validate(AsyncMsg asyncMsg,ScanDataUploadEntity entity){
		Argument.notNull(asyncMsg, "AsyncMsg");
		Argument.notNull(entity, "entity");
		//校验PDA设备号
		Argument.hasText(asyncMsg.getPdaCode(),"pdaCode");
		//校验部门编码
		Argument.hasText(asyncMsg.getDeptCode(),"deptCode");
		//校验操作类型
		Argument.hasText(asyncMsg.getOperType(),"operType");
		//校验司机工号
		Argument.hasText(entity.getDriverCode(), "driverCode");
		//校验任务号
		Argument.hasText(entity.getTaskCode(),"taskCode");
		//校验运单实体
		Argument.notNull(entity.getWaybillInfoEntity(),"WaybillInfoEntity");
	}

	/**
	 * 保存扫描数据
	 */
	private void saveEWaybillScanData(ScanDataUploadEntity entity){
		List<WaybillInfoEntitys> waybillInfo = entity.getWaybillInfoEntity();
		int len = waybillInfo.size();
		for(int i=0;i<len;i++){
			entity.setWblCode(waybillInfo.get(i).getWblCode());
			entity.setScanTime(waybillInfo.get(i).getScanTime());
			acctDao.saveEWaybillScanData(entity);
		}
	}
	/**
	 * 
	 * @Title: setAcctDao 
	 * @Description: TODO(封装参数)  
	 * @return void    返回类型 
	 * @param acctDao
	 * @author： 268974  wangzhili
	 */
	private List<PdaAppInfoDto> wropAppOverTask(ScanDataUploadEntity param,AsyncMsg asyncMsg){
		List<PdaAppInfoDto> list = new ArrayList<PdaAppInfoDto>();
		int len = param.getWaybillInfoEntity().size();
		for(int i=0;i<len;i++){
			PdaAppInfoDto dto = new PdaAppInfoDto();
			WaybillInfoEntitys entity = param.getWaybillInfoEntity().get(i);
			//单号
			String wblCode = entity.getWblCode();
			//件数
			int pieces = entity.getPieces();
			//重量
			BigDecimal weight = entity.getWeight();
			//体积
			BigDecimal volume = entity.getVolume();
			//是否大件上楼
			String largeUpstairs = entity.getLargeUpstairs();
			//运单号
			dto.setWaybillNo(wblCode);
			//总体积
			dto.setGoodsVolumeTotal(volume);
			//总重量
			dto.setGoodsWeightTotal(weight);
			//总件数
			dto.setGoodsQtyTotal(pieces);
			//完成问题提交时间
			dto.setSubmissionTime(param.getSubmissionTime());
			//是工号
			dto.setDriverCode(param.getDriverCode());
			//任务号
			dto.setTaskCode(param.getTaskCode());
			//车牌号
			dto.setTruckCode(param.getTruckCode());
			//是否大件上楼
			dto.setIsBigUp(largeUpstairs);
			//包装信息
			dto.setPackInfo(entity.getPackInfo());
			list.add(dto);
		}
		return list;
	}

	public void setAcctDao(IAcctDao acctDao) {
		this.acctDao = acctDao;
	}

	public void setlTLEWaybillPdaScanService(
			ILTLEWaybillPdaScanService lTLEWaybillPdaScanService) {
		this.lTLEWaybillPdaScanService = lTLEWaybillPdaScanService;
	}

	
	

}
