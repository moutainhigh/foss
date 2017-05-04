package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillHomeImpDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillHomeImpPushService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillHomeImpService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.InstallDetail;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.WaybillHomeInfoDto;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.WaybillHomeInfoResponseDto;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.InstallationEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillHomeImpEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.vo.WaybillHomeImpPushVo;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 2015-09-15
 * @author lizhonglin 德邦家装运单信息推送
 */
public class WaybillHomeImpService implements IWaybillHomeImpService {

	protected final static Logger LOGGER = LoggerFactory.getLogger(WaybillHomeImpService.class.getName());

	public static final String queryJobId = WaybillConstants.UNKNOWN;
	//运单推送次数
	public static final int PUSH_TIMES = 5;
	public static final String NULL_STRING = "";
	/**
	 * 家装运单推送信息持久层接口
	 */
	private IWaybillHomeImpDao waybillHomeImpDao;
	/**
	 * 组织机构信息 提供与组织机构相关的服务接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 家装运单推送业务逻辑处理接口
	 */
	private IWaybillHomeImpPushService waybillHomeImpPushService;
	/**
	 * 地址信息服务接口
	 */
	private IAdministrativeRegionsService administrativeRegionsService;
	
	/**
	 * 运单管理信息服务接口
	 */
	private IWaybillManagerService waybillManagerService;
	
	/**
	 * 运单信息更改持久层接口
	 * @return
	 */
	private IWaybillRfcDao waybillRfcDao;
	
	/**
	 * 实际承运信息服务接口
	 */
	
	private IActualFreightService actualFreightService;
	
	/**
	 * 运单信息相关服务接口 查询安装费明细信息
	 */
	private IWaybillDao waybillDao;
	
	// ========================================================================================
	/**
	 * 保存家装运单提交时需要向家装模块推送信息 ,将带有特殊增值服务标识的运单信息保存
	 * 
	 * @param waybillEntity
	 */
	@Override
	@Transactional
	public void saveWaybillHomeImpInfo(WaybillDto waybillDto) {
		WaybillHomeImpEntity homeImpEntity = new WaybillHomeImpEntity();
		homeImpEntity.setId(UUID.randomUUID().toString());
		homeImpEntity.setMailNo(waybillDto.getWaybillEntity().getWaybillNo());
		homeImpEntity.setLogisticID(waybillDto.getWaybillEntity().getOrderNo());
		Date currentTime = new Date();
		
		//获取安装费用明细
		List<InstallationEntity> installList =waybillDao.queryWaybillInstallation(waybillDto.getWaybillEntity().getWaybillNo());
		//获取人工安装费 和 超标楼层数
		if(installList.size()>0){
			int key = 0;
			boolean isContainInstallationInfo =false; 
			InstallationEntity installationEntity  = null;
			for(int i = 0 ; i < installList.size() ; i++){
				installationEntity = installList.get(i);
				boolean tmp = installationEntity.getInstallationCode().startsWith(WaybillConstants.INSTALLATION_CODE);
				if(tmp){
					key = i;
					isContainInstallationInfo =true;
					break;
				}
			}
			if(isContainInstallationInfo){
				//人工搬楼费用信息
				InstallationEntity installationManualEntity = installList.get(key);
				if(installationManualEntity.getInstallPackages()!=null){
				homeImpEntity.setInstallNumber(Integer.parseInt(installationManualEntity.getInstallPackages()));
				}
				if(installationManualEntity.getExceedNumberFloors()!=null){
					homeImpEntity.setOverflowNumber(Integer.parseInt(installationManualEntity.getExceedNumberFloors()));
					}
				if(installationManualEntity.getAmount()!=null){
			    homeImpEntity.setMoveCost(installationManualEntity.getAmount());
				}
			}else{
				homeImpEntity.setInstallNumber(0);
			    homeImpEntity.setOverflowNumber(0);
				homeImpEntity.setMoveCost(new BigDecimal(0));
			}
		}else{
			homeImpEntity.setInstallNumber(0);
		    homeImpEntity.setOverflowNumber(0);
			homeImpEntity.setMoveCost(new BigDecimal(0));
		}
		
		//订单来源
		homeImpEntity.setOrderSource(waybillDto.getWaybillEntity().getOrderChannel());
		// 提货方式
		homeImpEntity.setPickUpType(waybillDto.getWaybillEntity().getReceiveMethod());
		// 设置收货人联系信息
		homeImpEntity.setReceiveName(waybillDto.getWaybillEntity().getReceiveCustomerContact());
		if(waybillDto.getWaybillEntity().getReceiveCustomerPhone()!=null){
		homeImpEntity.setReceivePhone(waybillDto.getWaybillEntity().getReceiveCustomerPhone());
		}
		homeImpEntity.setReceiveMobile(waybillDto.getWaybillEntity().getReceiveCustomerMobilephone());
		// 设置收货地址信息
		AdministrativeRegionsEntity receiveProvince= administrativeRegionsService.queryAdministrativeRegionsByCode(waybillDto.getWaybillEntity().getReceiveCustomerProvCode());
		if(receiveProvince!=null){
			homeImpEntity.setReceiveProvince(receiveProvince.getName());
		}else{
			homeImpEntity.setReceiveProvince(NULL_STRING);
		}
		AdministrativeRegionsEntity receiveCity= administrativeRegionsService.queryAdministrativeRegionsByCode(waybillDto.getWaybillEntity().getReceiveCustomerCityCode());
		if(receiveCity!=null){
			homeImpEntity.setReceiveCity(receiveCity.getName());
		}else{
			homeImpEntity.setReceiveCity(NULL_STRING);
		}
		AdministrativeRegionsEntity receiveCounty= administrativeRegionsService.queryAdministrativeRegionsByCode(waybillDto.getWaybillEntity().getReceiveCustomerDistCode());
		if(receiveCounty!=null){
			homeImpEntity.setReceiveCounty(receiveCounty.getName());
		}else{
			homeImpEntity.setReceiveCounty(NULL_STRING);
		}
		homeImpEntity.setReceiveAddress(waybillDto.getWaybillEntity().getReceiveCustomerAddress());
		// 设置货物名称
		homeImpEntity.setTransCargoName(waybillDto.getWaybillEntity().getGoodsName());
		// 设置货物重量体积数量
		homeImpEntity.setTotalWeight(waybillDto.getWaybillEntity().getGoodsWeightTotal());
		homeImpEntity.setTotalVolume(waybillDto.getWaybillEntity().getGoodsVolumeTotal());
		homeImpEntity.setTotalNumber(waybillDto.getWaybillEntity().getGoodsQtyTotal());
		// 设置提货网点信息
		String currentCode = waybillDto.getWaybillEntity().getCustomerPickupOrgCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(currentCode);
		if (orgAdministrativeInfoEntity != null) {
			homeImpEntity.setPickNetWorkName(orgAdministrativeInfoEntity.getName());
			homeImpEntity.setPickNetWorkCode(orgAdministrativeInfoEntity.getCode());
			homeImpEntity.setPickNetWorkAddress(orgAdministrativeInfoEntity.getAddress());
			homeImpEntity.setPickNetWorkMobile(orgAdministrativeInfoEntity.getDepTelephone());
		} else {
			homeImpEntity.setPickNetWorkName(NULL_STRING);
			homeImpEntity.setPickNetWorkCode(NULL_STRING);
			homeImpEntity.setPickNetWorkAddress(NULL_STRING);
			homeImpEntity.setPickNetWorkMobile(NULL_STRING);
			LOGGER.info("没有找到编码为：" + currentCode + " 的提货网点信息");
		}
		// 设置对内备注
		String innerNotes=null;
		if(StringUtils.isBlank(waybillDto.getWaybillEntity().getInnerNotes())){
			innerNotes =NULL_STRING;
		}else{
			innerNotes = waybillDto.getWaybillEntity().getInnerNotes();
		}
		homeImpEntity.setMemo(innerNotes);
		// 设置收货部门名称，编码
		homeImpEntity.setDepartureDept(waybillDto.getWaybillEntity().getReceiveOrgName());
		homeImpEntity.setDepartureCode(waybillDto.getWaybillEntity().getReceiveOrgCode());
		// 设置操作类型为：新增
		homeImpEntity.setOperType(0);
		// 设置运单推送表的状态信息
		homeImpEntity.setJobId(WaybillConstants.UNKNOWN);
		homeImpEntity.setPushTimes(0);
		homeImpEntity.setPushStatus(WaybillConstants.NO);
		homeImpEntity.setOperatorName(waybillDto.getWaybillEntity().getCreateUserName());
		homeImpEntity.setCreateTime(currentTime);
		homeImpEntity.setModifyTime(currentTime);
		//开单时间
		homeImpEntity.setCreateWayBillTime(waybillDto.getWaybillEntity().getCreateTime());
		//出发城市
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity1 = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillDto.getWaybillEntity().getReceiveOrgCode());
		if(orgAdministrativeInfoEntity1!=null){
			homeImpEntity.setStartCity(orgAdministrativeInfoEntity1.getCityName());
		}else{
			homeImpEntity.setStartCity(NULL_STRING);
		}
		//承诺到达时间
		homeImpEntity.setCommitmentArriveTime(waybillDto.getWaybillEntity().getPreArriveTime());
		//收取客户的特殊增值服务费总和
		BigDecimal totalSpecialServiceCharge=new BigDecimal(0);
		List<InstallationEntity> installationEntities=waybillDto.getSpecialValueAddedServiceEntity();
		if(installationEntities!=null){
		for (InstallationEntity installationEntity : installationEntities) {
			BigDecimal installPackages = StringUtils.isEmpty(installationEntity.getInstallPackages()) ? new BigDecimal(1): new BigDecimal(installationEntity.getInstallPackages());				
			if(installationEntity.getLowerLimit().compareTo(installPackages.multiply(installationEntity.getAmount()))<0){
				totalSpecialServiceCharge = totalSpecialServiceCharge
						.add(installPackages.multiply(installationEntity.getAmount()));
			    }else{
			    	totalSpecialServiceCharge = totalSpecialServiceCharge
							.add(installationEntity.getLowerLimit());
			    }
		      }
		}
		homeImpEntity.setTotalSpecialServiceCharge(String.valueOf(totalSpecialServiceCharge));
		// 保存运单推送信息
		if(homeImpEntity!=null){
			waybillHomeImpDao.insertWaybillHomeImpInfo(homeImpEntity);
		}
	}

	/**
	 * 保存家装运单更改提交时需要向家装模块推送信息 ,将带有特殊增值服务标识的运单更改信息保存
	 * @param waybillRfcEntity
	 */
	@Override
	@Transactional
	public void saveWaybillHomeImpInfoRfc(WaybillRfcEntity waybillRfcEntity) {
		
		//获取更改前后运单信息
		
		LOGGER.info("更改前运单编号："+waybillRfcEntity.getOldVersionWaybillId()  + "，更改后运单编号:" + waybillRfcEntity.getNewVersionWaybillId());
		
		WaybillDto oldWaybillDto = waybillManagerService.getWaybillDtoById(waybillRfcEntity.getOldVersionWaybillId());
		WaybillDto newWaybillDto = waybillManagerService.getWaybillDtoById(waybillRfcEntity.getNewVersionWaybillId());
		
		ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(newWaybillDto.getWaybillEntity().getWaybillNo());
		
		oldWaybillDto.setActualFreightEntity(actualFreightEntity);
		newWaybillDto.setActualFreightEntity(actualFreightEntity);
		Date currentTime = new Date();
		//获取安装费用明细
		List<InstallationEntity> installList =waybillDao.queryWaybillInstallation(oldWaybillDto.getWaybillEntity().getWaybillNo());
			//作废运单信息
			if(WaybillRfcConstants.INVALID.equals(waybillRfcEntity.getRfcType())||
					WaybillRfcConstants.ABORT.equals(waybillRfcEntity.getRfcType())){
				WaybillHomeImpEntity homeImpEntity = new WaybillHomeImpEntity();
				homeImpEntity.setId(UUID.randomUUID().toString());
				homeImpEntity.setMailNo(oldWaybillDto.getWaybillEntity().getWaybillNo());
				homeImpEntity.setLogisticID(oldWaybillDto.getWaybillEntity().getOrderNo());
				/**
				 * 安装品名  安装件数  超标楼层数  上楼安装费  待定
				 */
				
				//获取人工安装费 和 超标楼层数
				if(installList.size()>0){
					int key = 0;
					boolean isContainInstallationInfo = false;
					InstallationEntity installationEntity  = null;
					for(int i = 0 ; i < installList.size() ; i++){
						installationEntity = installList.get(i);
						boolean tmp = installationEntity.getInstallationCode().startsWith(WaybillConstants.INSTALLATION_CODE);
						if(tmp){
							key = i;
							isContainInstallationInfo = true;
							break;
						}
					}
					if(isContainInstallationInfo){
						//人工搬楼费用信息
						InstallationEntity installationManualEntity = installList.get(key);
						if(installationManualEntity.getInstallPackages()!=null){
							homeImpEntity.setInstallNumber(Integer.parseInt(installationManualEntity.getInstallPackages()));
						}
						if(installationManualEntity.getExceedNumberFloors()!=null){
							homeImpEntity.setOverflowNumber(Integer.parseInt(installationManualEntity.getExceedNumberFloors()));
							}
					    if(installationManualEntity.getAmount()!=null){
						    homeImpEntity.setMoveCost(installationManualEntity.getAmount());
							}
					}else{
						homeImpEntity.setInstallNumber(0);
					    homeImpEntity.setOverflowNumber(0);
						homeImpEntity.setMoveCost(new BigDecimal(0));
					}
				}else{
					homeImpEntity.setInstallNumber(0);
				    homeImpEntity.setOverflowNumber(0);
					homeImpEntity.setMoveCost(new BigDecimal(0));
				}
				//订单来源
				homeImpEntity.setOrderSource(oldWaybillDto.getWaybillEntity().getOrderChannel());
				// 提货方式
				homeImpEntity.setPickUpType(oldWaybillDto.getWaybillEntity().getReceiveMethod());
				// 设置收货人联系信息
				homeImpEntity.setReceiveName(oldWaybillDto.getWaybillEntity().getReceiveCustomerContact());
				homeImpEntity.setReceivePhone(oldWaybillDto.getWaybillEntity().getReceiveCustomerPhone());
				homeImpEntity.setReceiveMobile(oldWaybillDto.getWaybillEntity().getReceiveCustomerMobilephone());
				// 设置收货地址信息
				AdministrativeRegionsEntity receiveProvince= administrativeRegionsService.queryAdministrativeRegionsByCode(oldWaybillDto.getWaybillEntity().getReceiveCustomerProvCode());
				if(receiveProvince!=null){
					homeImpEntity.setReceiveProvince(receiveProvince.getName());
				}else{
					homeImpEntity.setReceiveProvince(NULL_STRING);
				}
				AdministrativeRegionsEntity receiveCity= administrativeRegionsService.queryAdministrativeRegionsByCode(oldWaybillDto.getWaybillEntity().getReceiveCustomerCityCode());
				if(receiveCity!=null){
					homeImpEntity.setReceiveCity(receiveCity.getName());
				}else{
					homeImpEntity.setReceiveCity(NULL_STRING);
				}
				AdministrativeRegionsEntity receiveCounty= administrativeRegionsService.queryAdministrativeRegionsByCode(oldWaybillDto.getWaybillEntity().getReceiveCustomerDistCode());
				if(receiveCounty!=null){
					homeImpEntity.setReceiveCounty(receiveCounty.getName());
				}else{
					homeImpEntity.setReceiveCounty(NULL_STRING);
				}
				homeImpEntity.setReceiveAddress(oldWaybillDto.getWaybillEntity().getReceiveCustomerAddress());
				// 设置货物名称
				homeImpEntity.setTransCargoName(oldWaybillDto.getWaybillEntity().getGoodsName());
				// 设置货物重量体积数量
				homeImpEntity.setTotalWeight(oldWaybillDto.getWaybillEntity().getGoodsWeightTotal());
				homeImpEntity.setTotalVolume(oldWaybillDto.getWaybillEntity().getGoodsVolumeTotal());
				homeImpEntity.setTotalNumber(oldWaybillDto.getWaybillEntity().getGoodsQtyTotal());
				// 设置提货网点信息
				String currentCode = oldWaybillDto.getWaybillEntity().getCustomerPickupOrgCode();
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(currentCode);
				if (orgAdministrativeInfoEntity != null) {
					homeImpEntity.setPickNetWorkName(orgAdministrativeInfoEntity.getName());
					homeImpEntity.setPickNetWorkCode(orgAdministrativeInfoEntity.getCode());
					homeImpEntity.setPickNetWorkAddress(orgAdministrativeInfoEntity.getAddress());
					homeImpEntity.setPickNetWorkMobile(orgAdministrativeInfoEntity.getDepTelephone());
				} else {
					homeImpEntity.setPickNetWorkName(NULL_STRING);
					homeImpEntity.setPickNetWorkCode(NULL_STRING);
					homeImpEntity.setPickNetWorkAddress(NULL_STRING);
					homeImpEntity.setPickNetWorkMobile(NULL_STRING);
					LOGGER.info("没有找到编码为：" + currentCode + " 的提货网点信息");
				}
				// 设置对内备注
				String innerNotes=null;
				if(StringUtils.isBlank(oldWaybillDto.getWaybillEntity().getInnerNotes())){
					innerNotes =NULL_STRING;
				}else{
					innerNotes =oldWaybillDto.getWaybillEntity().getInnerNotes();
				}
				homeImpEntity.setMemo(innerNotes);
				// 设置收货部门名称，编码
				homeImpEntity.setDepartureDept(oldWaybillDto.getWaybillEntity().getReceiveOrgName());
				homeImpEntity.setDepartureCode(oldWaybillDto.getWaybillEntity().getReceiveOrgCode());
				// 设置操作类型为：取消
				homeImpEntity.setOperType(2);
				// 设置运单推送表的状态信息
				homeImpEntity.setJobId(WaybillConstants.UNKNOWN);
				homeImpEntity.setPushTimes(0);
				homeImpEntity.setPushStatus(WaybillConstants.NO);
				homeImpEntity.setOperatorName(newWaybillDto.getWaybillEntity().getCreateUserName());
				homeImpEntity.setCreateTime(currentTime);
				homeImpEntity.setModifyTime(currentTime);
				//开单时间
				homeImpEntity.setCreateWayBillTime(oldWaybillDto.getWaybillEntity().getCreateTime());
				//出发城市
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity1 = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(oldWaybillDto.getWaybillEntity().getReceiveOrgCode());
				if(orgAdministrativeInfoEntity1!=null){
					homeImpEntity.setStartCity(orgAdministrativeInfoEntity1.getCityName());
				}else{
					homeImpEntity.setStartCity(NULL_STRING);
				}
				//承诺到达时间
				homeImpEntity.setCommitmentArriveTime(oldWaybillDto.getWaybillEntity().getPreArriveTime());
				//收取客户的特殊增值服务费总和
				BigDecimal totalSpecialServiceCharge=new BigDecimal(0);
				List<InstallationEntity> installationEntities=oldWaybillDto.getSpecialValueAddedServiceEntity();
				if(installationEntities!=null){
					for (InstallationEntity installationEntity : installationEntities) {
						BigDecimal installPackages = StringUtils.isEmpty(installationEntity.getInstallPackages()) ? new BigDecimal(1): new BigDecimal(installationEntity.getInstallPackages());		
						if(installationEntity.getLowerLimit().compareTo(installPackages.multiply(installationEntity.getAmount()))<0){
							totalSpecialServiceCharge = totalSpecialServiceCharge
									.add(installPackages.multiply(installationEntity.getAmount()));
						    }else{
						    	totalSpecialServiceCharge = totalSpecialServiceCharge
										.add(installationEntity.getLowerLimit());
						    }
					      }
					}
				homeImpEntity.setTotalSpecialServiceCharge(String.valueOf(totalSpecialServiceCharge));
				// 保存运单推送信息
				if(homeImpEntity!=null){
					waybillHomeImpDao.insertWaybillHomeImpInfo(homeImpEntity);
				}
			}else{		
			//内部更改运单信息
				//获取更改明细信息
//				List<WaybillRfcChangeDetailEntity> changeList = waybillRfcDao.queryRfcChangeDetail(waybillRfcEntity.getId());
				//检测是否有目的站变更
				/*boolean isChangeCustomerPickupOrgName = false;
				if(changeList!=null){
					WaybillRfcChangeDetailEntity changeDetailEntity =null;		
					for(int i =0 ;i < changeList.size();i++){
						changeDetailEntity = changeList.get(i);
						if(WaybillRfcConstants.WaybillRfcChangeItemsConstants.ITEM_CUSTOMER_PICKUP_ORG_NAME.equals(changeDetailEntity.getRfcItems())){
							isChangeCustomerPickupOrgName=true;
							break;
						}
					}
				}*/
					//变更信息修改为:更改 ==========开始
					WaybillHomeImpEntity newHomeImpEntity = new WaybillHomeImpEntity();
					newHomeImpEntity.setId(UUID.randomUUID().toString());
					newHomeImpEntity.setMailNo(newWaybillDto.getWaybillEntity().getWaybillNo());
					newHomeImpEntity.setLogisticID(newWaybillDto.getWaybillEntity().getOrderNo());
					/**
					 * 安装品名  安装件数  超标楼层数  上楼安装费  待定
					 */
					//获取人工安装费 和 超标楼层数
					if(installList.size()>0){
						int key = 0;
						boolean isContainInstallationInfo = false;
						InstallationEntity installationEntity  = null;
						for(int i = 0 ; i < installList.size() ; i++){
							installationEntity = installList.get(i);
							boolean tmp = installationEntity.getInstallationCode().startsWith(WaybillConstants.INSTALLATION_CODE);
							if(tmp){
								key = i;
								isContainInstallationInfo =true;
								break;
							}
						}
						if(isContainInstallationInfo){
							//人工搬楼费用信息
							InstallationEntity installationManualEntity = installList.get(key);
							if(installationManualEntity.getExceedNumberFloors()!=null){
                            newHomeImpEntity.setOverflowNumber(Integer.parseInt(installationManualEntity.getExceedNumberFloors()));
							}				
							if(installationManualEntity.getInstallPackages()!=null){
								newHomeImpEntity.setInstallNumber(Integer.parseInt(installationManualEntity.getInstallPackages()));
							}
							if(installationManualEntity.getAmount()!=null){
							   newHomeImpEntity.setMoveCost(installationManualEntity.getAmount());
							}
						}else{
							newHomeImpEntity.setInstallNumber(0);
							newHomeImpEntity.setOverflowNumber(0);
							newHomeImpEntity.setMoveCost(new BigDecimal(0));
						}
					}else{
						newHomeImpEntity.setInstallNumber(0);
						newHomeImpEntity.setOverflowNumber(0);
						newHomeImpEntity.setMoveCost(new BigDecimal(0));
					}
					//订单来源
					newHomeImpEntity.setOrderSource(newWaybillDto.getWaybillEntity().getOrderChannel());
					// 提货方式
					newHomeImpEntity.setPickUpType(newWaybillDto.getWaybillEntity().getReceiveMethod());
					// 设置收货人联系信息
					newHomeImpEntity.setReceiveName(newWaybillDto.getWaybillEntity().getReceiveCustomerContact());
					newHomeImpEntity.setReceivePhone(newWaybillDto.getWaybillEntity().getReceiveCustomerPhone());
					newHomeImpEntity.setReceiveMobile(newWaybillDto.getWaybillEntity().getReceiveCustomerMobilephone());
					// 设置收货地址信息
					AdministrativeRegionsEntity newReceiveProvince= administrativeRegionsService.queryAdministrativeRegionsByCode(newWaybillDto.getWaybillEntity().getReceiveCustomerProvCode());
					if(null!=newReceiveProvince){
						newHomeImpEntity.setReceiveProvince(newReceiveProvince.getName());
					}else{
						newHomeImpEntity.setReceiveProvince(NULL_STRING);
					}
					AdministrativeRegionsEntity newReceiveCity= administrativeRegionsService.queryAdministrativeRegionsByCode(newWaybillDto.getWaybillEntity().getReceiveCustomerCityCode());
					if(null!=newReceiveCity){
						newHomeImpEntity.setReceiveCity(newReceiveCity.getName());
					}else{
						newHomeImpEntity.setReceiveCity(NULL_STRING);
					}
					AdministrativeRegionsEntity newReceiveCounty= administrativeRegionsService.queryAdministrativeRegionsByCode(newWaybillDto.getWaybillEntity().getReceiveCustomerDistCode());
					if(null!=newReceiveCounty){
						newHomeImpEntity.setReceiveCounty(newReceiveCounty.getName());
					}else{
						newHomeImpEntity.setReceiveCounty(NULL_STRING);
					}
					//
					newHomeImpEntity.setReceiveAddress(newWaybillDto.getWaybillEntity().getReceiveCustomerAddress());
					// 设置货物名称
					newHomeImpEntity.setTransCargoName(newWaybillDto.getWaybillEntity().getGoodsName());
					// 设置货物重量体积数量
					newHomeImpEntity.setTotalWeight(newWaybillDto.getWaybillEntity().getGoodsWeightTotal());
					newHomeImpEntity.setTotalVolume(newWaybillDto.getWaybillEntity().getGoodsVolumeTotal());
					newHomeImpEntity.setTotalNumber(newWaybillDto.getWaybillEntity().getGoodsQtyTotal());
					// 设置提货网点信息
					String newCurrentCode = newWaybillDto.getWaybillEntity().getCustomerPickupOrgCode();
					OrgAdministrativeInfoEntity newOrgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(newCurrentCode);
					if (newOrgAdministrativeInfoEntity != null) {
						newHomeImpEntity.setPickNetWorkName(newOrgAdministrativeInfoEntity.getName());
						newHomeImpEntity.setPickNetWorkCode(newOrgAdministrativeInfoEntity.getCode());
						newHomeImpEntity.setPickNetWorkAddress(newOrgAdministrativeInfoEntity.getAddress());
						newHomeImpEntity.setPickNetWorkMobile(newOrgAdministrativeInfoEntity.getDepTelephone());
					} else {
						newHomeImpEntity.setPickNetWorkName(NULL_STRING);
						newHomeImpEntity.setPickNetWorkCode(NULL_STRING);
						newHomeImpEntity.setPickNetWorkAddress(NULL_STRING);
						newHomeImpEntity.setPickNetWorkMobile(NULL_STRING);
						LOGGER.info("没有找到编码为：" + newCurrentCode + " 的提货网点信息");
					}
					// 设置对内备注
					String newInnerNotes=null;
					if(StringUtils.isBlank(newWaybillDto.getWaybillEntity().getInnerNotes())){
						newInnerNotes =NULL_STRING;
					}else{
						newInnerNotes = newWaybillDto.getWaybillEntity().getInnerNotes();
					}
					newHomeImpEntity.setMemo(newInnerNotes);
					// 设置收货部门名称，编码
					newHomeImpEntity.setDepartureDept(newWaybillDto.getWaybillEntity().getReceiveOrgName());
					newHomeImpEntity.setDepartureCode(newWaybillDto.getWaybillEntity().getReceiveOrgCode());
					// 设置操作类型为：更改
					if(WaybillConstants.SEND_UPSTAIRS_EQUIP.equals(newWaybillDto.getWaybillEntity().getReceiveMethod()) ||
									 WaybillConstants.SEND_AND_EQUIP.equals(newWaybillDto.getWaybillEntity().getReceiveMethod()) ||
									 WaybillConstants.SEND_NO_UPSTAIRS.equals(newWaybillDto.getWaybillEntity().getReceiveMethod())){
					    newHomeImpEntity.setOperType(1); 
					}else{
						//特殊运单改成普通运单
						newHomeImpEntity.setOperType(2);
					}
					// 设置运单推送表的状态信息
					newHomeImpEntity.setJobId(WaybillConstants.UNKNOWN);
					newHomeImpEntity.setPushTimes(0);
					newHomeImpEntity.setPushStatus(WaybillConstants.NO);
					newHomeImpEntity.setOperatorName(newWaybillDto.getWaybillEntity().getCreateUserName());
					newHomeImpEntity.setCreateTime(currentTime);
					newHomeImpEntity.setModifyTime(currentTime);
					//开单时间
					newHomeImpEntity.setCreateWayBillTime(oldWaybillDto.getWaybillEntity().getCreateTime());
					//出发城市
					OrgAdministrativeInfoEntity orgAdministrativeInfoEntity1 = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(oldWaybillDto.getWaybillEntity().getReceiveOrgCode());
					if(orgAdministrativeInfoEntity1!=null){
						newHomeImpEntity.setStartCity(orgAdministrativeInfoEntity1.getCityName());
					}else{
						newHomeImpEntity.setStartCity(NULL_STRING);
					}
					//承诺到达时间
					newHomeImpEntity.setCommitmentArriveTime(oldWaybillDto.getWaybillEntity().getPreArriveTime());
					//收取客户的特殊增值服务费总和
					BigDecimal totalSpecialServiceCharge=new BigDecimal(0);
					List<InstallationEntity> installationEntities=oldWaybillDto.getSpecialValueAddedServiceEntity();
					if(installationEntities!=null){
						for (InstallationEntity installationEntity : installationEntities) {
							BigDecimal installPackages = StringUtils.isEmpty(installationEntity.getInstallPackages()) ? new BigDecimal(1): new BigDecimal(installationEntity.getInstallPackages());		
							if(installationEntity.getLowerLimit().compareTo(installPackages.multiply(installationEntity.getAmount()))<0){
								totalSpecialServiceCharge = totalSpecialServiceCharge
										.add(installPackages.multiply(installationEntity.getAmount()));
							    }else{
							    	totalSpecialServiceCharge = totalSpecialServiceCharge
											.add(installationEntity.getLowerLimit());
							    }
						      }
						}
					newHomeImpEntity.setTotalSpecialServiceCharge(String.valueOf(totalSpecialServiceCharge));
					// 保存运单推送信息
					if(newHomeImpEntity!=null){
						waybillHomeImpDao.insertWaybillHomeImpInfo(newHomeImpEntity);
					}
					//变更信息修改为:更改  ==========结束				
		}
	}

	/**
	 * FOSS向DOP推送带有特殊增值服务标识的家装运单信息
	 */
	@Override
	@Transactional
	public void pushWaybillHomeInfo() {
		// 每次要查询的条数
		String queryNum = "500";
		WaybillHomeImpPushVo vo = new WaybillHomeImpPushVo();
		vo.setResultNum(Integer.parseInt(queryNum));
		String jobId = UUIDUtils.getUUID();
		// 更新一定数量的JobId
		vo = updateWaybillInfoPushForJobTopNum(jobId, queryNum);
		// 根据JobId查询待处理信息
		List<WaybillHomeImpEntity> waybillList = this.queryWaybillHomeInfoPushMessageByJobId(jobId);
		if (CollectionUtils.isNotEmpty(waybillList)) {
			//执行批量推送
			this.pushInfo(waybillList);
		}

	}

	@Override
	@Transactional
	public WaybillHomeImpPushVo updateWaybillInfoPushForJobTopNum(String jobId,
			String queryNum) {
		WaybillHomeImpPushVo vo = new WaybillHomeImpPushVo();
		vo.setJobId(jobId);
		vo.setQueryNum(queryNum);
		vo.setQueryJobId(queryJobId);
		int result = waybillHomeImpDao.updateHomeImpPushMessageByRowNum(vo);
		vo.setResultNum(result);
		return vo;
	}

	@Override
	@Transactional
	public List<WaybillHomeImpEntity> queryWaybillHomeInfoPushMessageByJobId(
			String jobId) {
		WaybillHomeImpEntity entity = new WaybillHomeImpEntity();
		entity.setJobId(jobId);
		List<WaybillHomeImpEntity> list = waybillHomeImpDao
				.quaryWaybillHomeInfoByJobId(entity);
		if (list != null) {
			return list;
		}
		return null;
	}

	@Transactional
	public void pushInfo(List<WaybillHomeImpEntity> waybillList) {
		LOGGER.error("推送家装运单信息开始......");
		// 订阅表中需全量推送的运单
		LOGGER.error("推送家装运单信息List : " + waybillList);

		WaybillHomeInfoDto requestDto = new WaybillHomeInfoDto();
		for (WaybillHomeImpEntity entity : waybillList) {
			try {
				
				List<InstallationEntity> installList =waybillDao.queryWaybillInstallation(entity.getMailNo());
				List<InstallDetail> detailList = new ArrayList<InstallDetail>();
				
				if(installList.size()>0){
					InstallDetail detail = null;
					InstallationEntity installationEntity =null;
					for(int i =0 ; i < installList.size() ;i++){
						installationEntity = installList.get(i);
						detail = new InstallDetail();
						detail.setUniqueId("");
						detail.setMailNo(installationEntity.getWaybillNo());
						detail.setInstallNumber(Integer.parseInt(installationEntity.getInstallPackages()==null?"0":installationEntity.getInstallPackages()));
						detail.setInstallCargoName(installationEntity.getInstallationName());
						if(installationEntity.getInstallPackages()!=null && !"0".equals(installationEntity.getInstallPackages())){
							detail.setSpecialFlag(FossConstants.YES);
							detailList.add(detail);
						}
						
					}
					requestDto.setDetailList(detailList);
				}
				
				requestDto.setId("");
				requestDto.setMailNo(entity.getMailNo());
				requestDto.setLogisticID(entity.getLogisticID());
				requestDto.setOrderSource(entity.getOrderSource());
				requestDto.setPickUpType(entity.getPickUpType());
				requestDto.setOverflowNumber(entity.getOverflowNumber());
				if(StringUtils.isBlank(entity.getMoveCost().toString())){
					entity.setMoveCost(BigDecimal.ZERO);
				}
				requestDto.setMoveCost(Double.valueOf(entity.getMoveCost().toString()));
				// 设置收货人联系信息
				requestDto.setReceiveName(entity.getReceiveName());
				requestDto.setReceivePhone(entity.getReceivePhone());
				requestDto.setReceiveMobile(entity.getReceiveMobile());
				// 设置收货地址信息
				requestDto.setReceiveProvince(entity.getReceiveProvince());
				requestDto.setReceiveCity(entity.getReceiveCity());
				requestDto.setReceiveCounty(entity.getReceiveCounty());
				requestDto.setReceiveAddress(entity.getReceiveAddress());
				// 设置货物名称
				requestDto.setTransCargoName(entity.getTransCargoName());
				// 设置货物重量体积数量
				if(StringUtils.isBlank(entity.getTotalWeight().toString())){
					entity.setTotalWeight(BigDecimal.ZERO);
				}
				if(StringUtils.isBlank(entity.getTotalVolume().toString())){
					entity.setTotalVolume(BigDecimal.ZERO);
				}
				requestDto.setTotalWeight(Double.valueOf(entity.getTotalWeight().toString()));
				requestDto.setTotalVolume(Double.valueOf(entity.getTotalVolume().toString()));
				requestDto.setTotalNumber(entity.getTotalNumber());
				requestDto.setPickNetWorkName(entity.getPickNetWorkName());
				requestDto.setPickNetWorkCode(entity.getPickNetWorkCode());
				requestDto.setPickNetWorkAddress(entity.getPickNetWorkAddress());
				requestDto.setPickNetWorkPhone(entity.getPickNetWorkMobile());
				requestDto.setMemo(entity.getMemo());
				// 设置收货部门名称，编码
				requestDto.setDepartureDept(entity.getDepartureDept());
				requestDto.setDepartureCode(entity.getDepartureCode());
				// 设置操作类型为
				requestDto.setOperType(entity.getOperType());
				//二期新加的四个字段 254615
				requestDto.setCreateWayBillTime(entity.getCreateWayBillTime());
				requestDto.setStartCity(entity.getStartCity());
				requestDto.setCommitmentArriveTime(entity.getCommitmentArriveTime());
				requestDto.setTotalSpecialServiceCharge(entity.getTotalSpecialServiceCharge());

				WaybillHomeInfoResponseDto responseDto = waybillHomeImpPushService.pushWaybillHomeInfo(requestDto);
				
				WaybillHomeImpEntity resonseWaybillHomeEntity = new WaybillHomeImpEntity();
				resonseWaybillHomeEntity.setModifyTime(new Date());
				if (null == responseDto) {
					// 保存异常信息，标注ESB异常，便于查找问题原因
					// 更新数据库中运单信息的推送数据次数
					resonseWaybillHomeEntity.setId(entity.getId());
					resonseWaybillHomeEntity.setPushTimes(entity.getPushTimes() + 1);
					if (resonseWaybillHomeEntity.getPushTimes() < WaybillHomeImpService.PUSH_TIMES) {
						resonseWaybillHomeEntity.setJobId(WaybillConstants.UNKNOWN);
					}else{
						resonseWaybillHomeEntity.setFailReason("连接ESB出现异常");
					}
					this.updateWaybillHomeImpInfo(resonseWaybillHomeEntity);
					// 推送失败时，异常信息处理
					LOGGER.error("FOSS向DOP推送家装运单信息ESB返回异常，运单号 ："+ requestDto.getMailNo());
					continue;
				}
				if (StringUtils.equals("1", responseDto.getResultCode())) {
					// 更新数据库中运单信息的推送状态为Y
					String id = entity.getId();
					resonseWaybillHomeEntity.setId(id);
					resonseWaybillHomeEntity.setPushTimes(entity.getPushTimes() + 1);
					resonseWaybillHomeEntity.setPushStatus(WaybillConstants.YES);
					this.updateWaybillHomeImpInfo(resonseWaybillHomeEntity);
					LOGGER.error("FOSS向DOP推送家装运单信息返回成功，运单号 : "+ responseDto.getMailNo());
				} else {
					// 更新数据库中运单信息的推送数据次数
					String id = entity.getId();
					resonseWaybillHomeEntity.setId(id);
					resonseWaybillHomeEntity.setPushTimes(entity.getPushTimes() + 1);
					if (resonseWaybillHomeEntity.getPushTimes() < WaybillHomeImpService.PUSH_TIMES) {
						resonseWaybillHomeEntity.setJobId(WaybillConstants.UNKNOWN);
					}else{
						String failReason = responseDto.getErrorMessage();
						resonseWaybillHomeEntity.setFailReason(failReason.length() > NumberConstants.NUMBER_100 ? failReason.substring(0, NumberConstants.NUMBER_100):failReason);
					}
					this.updateWaybillHomeImpInfo(resonseWaybillHomeEntity);
					// 推送失败时，异常信息处理
					LOGGER.error("FOSS向DOP推送家装运单信息返回失败，运单号 : "+ responseDto.getMailNo());
					LOGGER.error("错误原因 : "+ responseDto.getErrorMessage());
				}
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error("推送家装运单信息异常，运单号 : " + entity.getMailNo());
			}
		}
		LOGGER.error("推送家装运单信息结束......");
	}

	/**
	 * 更新家装运单推送信息
	 * 
	 * @param waybillHomeImpEntity
	 */
	@Override
	@Transactional
	public void updateWaybillHomeImpInfo(WaybillHomeImpEntity waybillHomeImpEntity) {
		waybillHomeImpDao.updateWaybillHomeImpInfo(waybillHomeImpEntity);
	}

	/**
	 * 查询所有德邦家装运单推送信息
	 * 
	 * @return
	 */
	@Override
	public List<WaybillHomeImpEntity> quaryAllWaybillHomeInfo() {
		return waybillHomeImpDao.quaryAllWaybillHomeInfo();
	}

	/**
	 * 根据ID查询德邦家装运单推送信息
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public WaybillHomeImpEntity quaryWaybillHomeInfoById(String id) {
		return waybillHomeImpDao.quaryWaybillHomeInfoById(id);
	}

	/**
	 * 根据运单号查询德邦家装运单推送信息
	 * 
	 * @param mailno
	 * @return
	 */
	@Override
	public List<WaybillHomeImpEntity> quaryWaybillHomeInfoByMailNo(String mailNo) {
		return waybillHomeImpDao.quaryWaybillHomeInfoByMailNo(mailNo);
	}

	/**
	 * 根据我司对应的运单的订单号查询德邦家装运单推送信息
	 * 
	 * @param logisticId
	 * @return
	 */
	@Override
	public List<WaybillHomeImpEntity> quaryWaybillHomeInfoByLogisticId(
			String logisticId) {
		return waybillHomeImpDao.quaryWaybillHomeInfoByLogisticId(logisticId);
	}

	/**
	 * 查询德邦家装运单中未推送的运单信息
	 */
	@Override
	public List<WaybillHomeImpEntity> quaryUnPushedWaybillHomeInfo() {
		WaybillHomeImpEntity waybillHomeImpEntity = new WaybillHomeImpEntity();
		waybillHomeImpEntity.setPushStatus(WaybillConstants.NO);
		waybillHomeImpEntity.setPushTimes(NumberConstants.NUMBER_5);
		List<WaybillHomeImpEntity> list = waybillHomeImpDao
				.quaryUnPushedWaybillHomeInfo(waybillHomeImpEntity);
		return list;
	}
	/**
	 * 判断更改单是否能推送运单信息
	 */
	@Override
	public boolean isAbledPush(String wayBillNo) {
		Long count = waybillHomeImpDao.quaryWaybillHomeInfoCountByWaybillNo(wayBillNo);
		if(count!=null && count > 0){
			return true;
		}
		return false;
	}

	public IWaybillHomeImpDao getWaybillHomeImpDao() {
		return waybillHomeImpDao;
	}

	public void setWaybillHomeImpDao(IWaybillHomeImpDao waybillHomeImpDao) {
		this.waybillHomeImpDao = waybillHomeImpDao;
	}

	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public IWaybillHomeImpPushService getWaybillHomeImpPushService() {
		return waybillHomeImpPushService;
	}

	public void setWaybillHomeImpPushService(
			IWaybillHomeImpPushService waybillHomeImpPushService) {
		this.waybillHomeImpPushService = waybillHomeImpPushService;
	}

	public IAdministrativeRegionsService getAdministrativeRegionsService() {
		return administrativeRegionsService;
	}

	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}
	
	public IWaybillManagerService getWaybillManagerService() {
		return waybillManagerService;
	}
	
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public IWaybillRfcDao getWaybillRfcDao() {
		return waybillRfcDao;
	}

	public void setWaybillRfcDao(IWaybillRfcDao waybillRfcDao) {
		this.waybillRfcDao = waybillRfcDao;
	}

	public IActualFreightService getActualFreightService() {
		return actualFreightService;
	}

	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}
	
	public IWaybillDao getWaybillDao() {
		return waybillDao;
	}
	
	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}


}
