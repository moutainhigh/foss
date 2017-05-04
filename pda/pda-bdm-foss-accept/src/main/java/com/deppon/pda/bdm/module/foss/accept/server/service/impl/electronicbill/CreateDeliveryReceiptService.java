package com.deppon.pda.bdm.module.foss.accept.server.service.impl.electronicbill;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.Argument;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPickService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.CreateDeliveryReceiptEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.WaybillInfoEntity;
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
 * @ClassName: CreateDeliveryReceiptService 
 * @Description: TODO(生成交接单service) 
 * @author &268974  wangzhili
 * @date 2016-5-17 下午2:54:37 
 *
 */
public class CreateDeliveryReceiptService implements IBusinessService<Void, ScanDataUploadEntity>{

	private Logger log = Logger.getLogger(getClass());
	private IAcctDao acctDao;
	private IPDAExpressPickService pdaExpressPickService;
	
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
			//封装请求参数
			CreateDeliveryReceiptEntity entity = wropCreateDeliveryReceiptEntity(param);
			pdaExpressPickService.createLTLPackHandoverbill(entity);
			long endTime = System.currentTimeMillis();
			log.info("调用foss接口耗时:" + (endTime - startTime));
			//保存扫描数据
			this.saveEWaybillScanData(param);
			log.info("保存扫描数据结束");
		}catch(BusinessException e){
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		} 
		return null;
	}

	//操作类型
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ERP_CREATE_EIR.VERSION;
	}

	//异步
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
	 * 封装请求参数
	 */
	private CreateDeliveryReceiptEntity wropCreateDeliveryReceiptEntity(ScanDataUploadEntity entity){
		CreateDeliveryReceiptEntity result = new CreateDeliveryReceiptEntity();
		//司机工号
		result.setDriverCode(entity.getDriverCode());
		//车牌号
		result.setTruckCode(entity.getTruckCode());
		//任务号
		result.setTaskCode(entity.getTaskCode());
		//总件数
		result.setTotalPieces(entity.getTotalPieces());
		//总体积
		result.setTotalVolume(entity.getTotalVolume());
		//总重量
		result.setTotalWeight(entity.getTotalWeight());
		//总票数
		result.setTotalVotes(entity.getTotalVotes());
		//收货部门编码
		result.setConsigneeDeptCode(entity.getConsigneeDeptCode());
		//创建时间
		result.setCreationTime(entity.getCreationTime());
		//提交时间
		result.setSubmissionTime(entity.getSubmissionTime());
		List<WaybillInfoEntity> list = new ArrayList<WaybillInfoEntity>();
		for(int i=0;i<entity.getWaybillInfoEntity().size();i++){
			WaybillInfoEntity waybill = new WaybillInfoEntity();
			//件数
			waybill.setPieces(entity.getWaybillInfoEntity().get(i).getPieces());
			//体积
			waybill.setVolume(entity.getWaybillInfoEntity().get(i).getVolume());
			//重量
			waybill.setWeight(entity.getWaybillInfoEntity().get(i).getWeight());
			//运单号
			waybill.setWblCode(entity.getWaybillInfoEntity().get(i).getWblCode());
			//大件上楼
			waybill.setLargeUpstrairs(entity.getWaybillInfoEntity().get(i).getLargeUpstairs());
			//包装类型    为了在重量体积补录的时候跳转界面
			waybill.setPackageRemark("M;");
			list.add(waybill);
		}
		//运单集合
		result.setWaybillInfoEntity(list);
		return result;
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

	public void setAcctDao(IAcctDao acctDao) {
		this.acctDao = acctDao;
	}

	public void setPdaExpressPickService(
			IPDAExpressPickService pdaExpressPickService) {
		this.pdaExpressPickService = pdaExpressPickService;
	}

	
	
}
