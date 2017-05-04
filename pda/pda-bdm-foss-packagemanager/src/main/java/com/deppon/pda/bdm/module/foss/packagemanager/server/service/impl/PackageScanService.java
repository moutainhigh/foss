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
import com.deppon.pda.bdm.module.foss.packagemanager.shared.domain.PackageScan;

/**
 * 
  * @ClassName CaclPackageService 
  * @Description TODO 扫描建包
  * @author mt 
  * @date 2013年7月30日9:59:55
 */
public class PackageScanService implements IBusinessService<Void, PackageScan> {
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
	public PackageScan parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		PackageScan model = JsonUtil.parseJsonToObject(PackageScan.class,asyncMsg.getContent());
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
	 * @param param 扫描建包信息
	 * @return
	 * @throws PdaBusiException 
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, PackageScan param)
			throws PdaBusiException {
		//验证字段非空
		this.validate(param);
		this.packageDao.savePackageScan(param);
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
	private ExpressPackageScanDetailDto wrapPdaPackageDto(PackageScan param){
		ExpressPackageScanDetailDto exDetailDto = new ExpressPackageScanDetailDto();
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
		exDetailDto.setType(TransferPDADictConstants.EXPRESS_PACKAGE_GOODS_STATE_NORMAL);
		//扫描状态
		exDetailDto.setScanState(TransferPDADictConstants.SCAN_STATE_SCANED);
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
	private void validate(PackageScan packageScan)
			throws ArgumentInvalidException {
		Argument.notNull(packageScan, "packageScan");
		//运单号
		Argument.hasText(packageScan.getWblCode(),"packageScan.wblCode");
		//标签号
		Argument.hasText(packageScan.getLabelCode(),"packageScan.labelCode");
		//设备号
		Argument.hasText(packageScan.getDeviceNo(),"packageScan.deviceNo");
		//类型
		Argument.hasText(packageScan.getType(),"packageScan.type");
		//扫描状态
		Argument.hasText(packageScan.getScanState(),"packageScan.scanState");
		//扫描时间
		Argument.notNull(packageScan.getScanTime(),"packageScan.scanTime");
	}
	
	@Override
	public String getOperType() {
		return PackageManagerConstant.OPER_TYPE_PACKAGE_SCAN.VERSION;
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
