package com.deppon.pda.bdm.module.foss.packagemanager.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPackageService;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressPackageDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressPackageScanDetailDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.PackageManagerConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.packagemanager.shared.domain.ForcePackageInfo;
import com.deppon.pda.bdm.module.foss.packagemanager.shared.domain.PackageGoodsDetail;
import com.deppon.pda.bdm.module.foss.packagemanager.shared.domain.SerialNo;

/**
 * 
  * @ClassName ForcePackageService 
  * @Description TODO 强扫接口
  * @author mt 
  * @date 2013年7月30日9:58:59
 */
public class ForcePackageService implements IBusinessService<PackageGoodsDetail, ForcePackageInfo> {
	private IPDAExpressPackageService pdaExpressPackageService;
	
	/**
	 * 
	 * <p>TODO 强扫接口</p> 
	 * @author Administrator
	 * @date 2013年7月30日9:58:55
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 */
	@Override
	public ForcePackageInfo parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		ForcePackageInfo model = JsonUtil.parseJsonToObject(ForcePackageInfo.class,
				asyncMsg.getContent());
		model.setDeviceNo(asyncMsg.getPdaCode());
		return model;
	}
	
	/**
	 * 
	 * <p>TODO强扫接口</p> 
	 * @author Administrator
	 * @date 2013年7月30日9:59:02
	 * @param asyncMsg 请求消息
	 * @param param 强扫接口信息
	 * @return
	 * @throws PdaBusiException 
	 */
	@Transactional
	@Override
	public PackageGoodsDetail service(AsyncMsg asyncMsg, ForcePackageInfo param)
			throws PdaBusiException {
		this.validate(param);
		ExpressPackageDetailDto detailDto = null;
		ExpressPackageScanDetailDto scanDetail = null;
		PackageGoodsDetail result = null;
		try {
			detailDto = pdaExpressPackageService.moreGoodsVerify(param.getPackageCode(), 
					param.getWayBillNo(), param.getSerialNo());
			scanDetail = wrapPdaScanPackageDto(detailDto,param);
			//需要调用一下扫描接口
			pdaExpressPackageService.scan(scanDetail);
			//封装实体
			result = wrapPdaPackageDto(detailDto);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		return result;
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
	private ExpressPackageScanDetailDto wrapPdaScanPackageDto(ExpressPackageDetailDto param,ForcePackageInfo p){
		ExpressPackageScanDetailDto exDetailDto = new ExpressPackageScanDetailDto();
		//包号
		exDetailDto.setTaskNo(p.getPackageCode());
		//运单号
		exDetailDto.setWayBillNo(param.getWayBillNo());
		//标签号
		List<String> list = param.getSerialNos();
		String serialNo = "";
		if(list != null){
			serialNo = list.get(0);
		}
		exDetailDto.setSerialNo(serialNo);
		//设备号
		exDetailDto.setDeviceNo(p.getDeviceNo());
		//扫描时间
		exDetailDto.setScanTime(p.getScanTime());
		//类型
		exDetailDto.setType(TransferPDADictConstants.EXPRESS_PACKAGE_GOODS_STATE_NORMAL);
		//扫描状态
		exDetailDto.setScanState(TransferPDADictConstants.SCAN_STATE_SCANED);
		//开单体积
		exDetailDto.setVolume(param.getVolume());
		//开单重量
		exDetailDto.setWeight(param.getWeight());
		//商品名称
		exDetailDto.setGoodsName(param.getGoodsName());
		//库存件数
		exDetailDto.setGoodsQty(param.getWayBillQty());
		//运输性质名称
		exDetailDto.setTransportType(param.getTransportTypeName());
		//运输性质编码
		exDetailDto.setTransportTypeCode(param.getTransportTypeCode());
		//收货部门名称
		exDetailDto.setReceiveOrgName(param.getReceiveOrgName());
		//到达部门名称
		exDetailDto.setReachOrgName(param.getReachOrgName());
		//货物包装
		exDetailDto.setPack(param.getPack());
		return exDetailDto;
	}
	
	/**
	 * 
	* @Description: TODO 封装实体
	* @param list
	* @return
	* @return List<GetPackageTask>    
	* @author mt
	* @date 2013年7月30日9:58:46
	 */
	private PackageGoodsDetail wrapPdaPackageDto(ExpressPackageDetailDto detailDto){
		PackageGoodsDetail goodsDetail = new PackageGoodsDetail();
		//货物名称
		goodsDetail.setGoodsName(detailDto.getGoodsName());
//		detailDto.getNotes();
		//已扫数量
		goodsDetail.setOperateQty(detailDto.getOperateQty());
//		detailDto.getPack();
//		detailDto.getPackageNo();
		//到达部门编码
		goodsDetail.setReachOrgCode(detailDto.getReachOrgCode());
		//到达部门名称
		goodsDetail.setReachOrgName(detailDto.getReachOrgName());
		//收货部门编号
		goodsDetail.setReceiveOrgCode(detailDto.getReceiveOrgCode());
		//收货部门名称
		goodsDetail.setReceiveOrgName(detailDto.getReceiveOrgName());
		List<SerialNo> serialNos = new ArrayList<SerialNo>();
		for (int i = 0; i < detailDto.getSerialNos().size(); i++) {
			SerialNo serialNo = new SerialNo();
			serialNo.setSerialNo(detailDto.getSerialNos().get(i));
			serialNos.add(serialNo);
		}
		//流水号列表
		goodsDetail.setSerialNos(serialNos);
		
		//库存数量
		goodsDetail.setStockQty(detailDto.getStockQty());
		//运输性质Code
		goodsDetail.setTransportTypeCode(detailDto.getTransportTypeCode());
		//运输性质名称
		goodsDetail.setTransportTypeName(detailDto.getTransportTypeName());
		//体积
		goodsDetail.setVolum(detailDto.getVolume());
		//运单号
		goodsDetail.setWayBillNo(detailDto.getWayBillNo());
		//开单数量
		goodsDetail.setWayBillQty(detailDto.getWayBillQty());
		//重量
		goodsDetail.setWeight(detailDto.getWeight());
		return goodsDetail;
	}
	
	/**
	 * 
	 * <p>TODO字段非空验证</p> 
	 * @author Administrator
	 * @date 2013年7月30日9:59:06
	 * @param createLoadTask
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate(ForcePackageInfo forcePackageInfo)
			throws ArgumentInvalidException {
		Argument.notNull(forcePackageInfo, "forcePackageInfo");
		//检验包号非空
		Argument.hasText(forcePackageInfo.getPackageCode(), "forcePackageInfo.packageCode");
		//检验运单号非空
		Argument.hasText(forcePackageInfo.getWayBillNo(), "forcePackageInfo.wayBillNo");
		//检验流水号非空
		Argument.hasText(forcePackageInfo.getSerialNo(), "forcePackageInfo.serialNo");
		//检验设备号非空
		Argument.hasText(forcePackageInfo.getDeviceNo(), "forcePackageInfo.deviceNo");
		//检验扫描时间非空
		Argument.notNull(forcePackageInfo.getScanTime(), "forcePackageInfo.scanTime");
	}
	
	@Override
	public String getOperType() {
		return PackageManagerConstant.OPER_TYPE_PACKAGE_CHECK_SCAN.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}
	
	public void setPdaExpressPackageService(
			IPDAExpressPackageService pdaExpressPackageService) {
		this.pdaExpressPackageService = pdaExpressPackageService;
	}
}
