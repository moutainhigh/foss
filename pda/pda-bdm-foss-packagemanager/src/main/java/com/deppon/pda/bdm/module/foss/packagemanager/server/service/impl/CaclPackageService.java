package com.deppon.pda.bdm.module.foss.packagemanager.server.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPackageService;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressPackageScanDetailDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.PackageManagerConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.packagemanager.server.dao.IPackageDao;
import com.deppon.pda.bdm.module.foss.packagemanager.shared.domain.PackageCancelScan;

/**
 * 
  * @ClassName CaclPackageService 
  * @Description TODO 撤销扫描建包
  * @author mt 
  * @date 2013年7月30日9:59:55
 */
public class CaclPackageService implements IBusinessService<Void, PackageCancelScan> {
	private IPDAExpressPackageService pdaExpressPackageService;
	private IPackageDao packageDao;
	
	/**
	 * 
	 * @author mt
	 * @date 2013年7月30日9:59:52
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 */
	@Override
	public PackageCancelScan parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		PackageCancelScan model = JsonUtil.parseJsonToObject(PackageCancelScan.class,asyncMsg.getContent());
		// PDA编号
		model.setDeviceNo(asyncMsg.getPdaCode());
		// 部门
		model.setDeptCode(asyncMsg.getDeptCode());
		// PDA编号
		model.setPdaCode(asyncMsg.getPdaCode());   
		// 操作类型
		model.setScanType(asyncMsg.getOperType());
		//操作用户
		model.setScanUser(asyncMsg.getUserCode());
		// ID
		model.setId(asyncMsg.getId());			  
		// 上传时间
		model.setUploadTime(asyncMsg.getUploadTime()); 
		// 同步状态
		model.setSyncStatus(asyncMsg.getAsyncStatus()); 
		// 同步次数
		model.setCount(asyncMsg.getSyncCount());
		return model;
	}
	/**
	 * 
	 * @author Administrator
	 * @date 2013年7月30日9:59:49
	 * @param asyncMsg 请求消息
	 * @param param 撤销扫描建包信息
	 * @return
	 * @throws PdaBusiException 
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, PackageCancelScan param)
			throws PdaBusiException {
		//验证字段非空
		this.validate(param);
		this.packageDao.saveCaclPackageScan(param);
		try{
			ExpressPackageScanDetailDto exDetailDto = wrapPdaPackageDto(param);
			pdaExpressPackageService.scan(exDetailDto);
		}
		catch(BusinessException e){
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		return null;
	}
	
	/**
	 * 
	* @Description: TODO 封装实体
	* @param param
	* @return
	* @return ExpressPackageScanDetailDto    
	* @author mt
	* @date 2013年7月30日9:59:38
	 */
	private ExpressPackageScanDetailDto wrapPdaPackageDto(PackageCancelScan param){
		ExpressPackageScanDetailDto exDetailDto = new ExpressPackageScanDetailDto();
		//包号
		exDetailDto.setPack(param.getPack());
		exDetailDto.setTaskNo(param.getTaskCode());
		//运单号
		exDetailDto.setWayBillNo(param.getWblCode());
		//标签号
		exDetailDto.setSerialNo(param.getLabelCode());
		//设备号
		exDetailDto.setDeviceNo(param.getDeviceNo());
		//扫描时间
		exDetailDto.setScanTime(param.getScanTime());
		//类型
		exDetailDto.setType(TransferPDADictConstants.EXPRESS_PACKAGE_GOODS_STATE_CANCELED);
		//扫描状态
		exDetailDto.setScanState(param.getScanState());
		//开单体积
		exDetailDto.setVolume(param.getVolum());
		//开单重量
		exDetailDto.setWeight(param.getWeight());
		//商品名称
		exDetailDto.setGoodsName(param.getGoodsName());
		//库存件数
		exDetailDto.setGoodsQty(param.getGoodsQty());
		//运输性质名称
		exDetailDto.setTransportType(param.getTransportType());
		//运输性质编码
		exDetailDto.setTransportTypeCode(param.getTransportTypeCode());
		//收货部门名称
		exDetailDto.setReceiveOrgName(param.getReceiveOrgName());
		//到达部门名称
		exDetailDto.setReachOrgName(param.getReachOrgName());
		return exDetailDto;
	}
	
	/**
	 * 
	 * TODO 字段非空验证
	 * @author mt
	 * @date 2013年7月30日9:59:43
	 * @param createLoadTask
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate(PackageCancelScan packageCancelScan)
			throws ArgumentInvalidException {
		Argument.notNull(packageCancelScan, "packageCancelScan");
		//运单号
		Argument.hasText(packageCancelScan.getWblCode(),"packageCancelScan.wblCode");
		//标签号
		Argument.hasText(packageCancelScan.getLabelCode(),"packageCancelScan.labelCode");
		//设备号
		Argument.hasText(packageCancelScan.getDeviceNo(),"packageCancelScan.deviceNo");
		//类型
		Argument.hasText(packageCancelScan.getType(),"packageCancelScan.type");
		//扫描状态
		Argument.hasText(packageCancelScan.getScanState(),"packageCancelScan.scanState");
		//UUID
		Argument.hasText(packageCancelScan.getId(),"packageCancelScan.id");
		//扫描时间
		Argument.notNull(packageCancelScan.getScanTime(),"packageCancelScan.scanTime");
	}
	
	@Override
	public String getOperType() {
		return PackageManagerConstant.OPER_TYPE_PACKAGE_SCAN_CANCEL.VERSION;
	}

	@Override
	public boolean isAsync() {
		return true;
	}
	
	public void setPdaExpressPackageService(
			IPDAExpressPackageService pdaExpressPackageService) {
		this.pdaExpressPackageService = pdaExpressPackageService;
	}
	public void setPackageDao(IPackageDao packageDao) {
		this.packageDao = packageDao;
	}
}
