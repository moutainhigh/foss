package com.deppon.foss.module.pickup.deliver.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaPickupService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PadPickupDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PadPickupResultDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupResultDto;
import com.deppon.foss.module.pickup.sign.api.server.dao.IPickupDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IPickupService;
import com.deppon.foss.module.pickup.sign.api.shared.define.PickupConstants;
import com.deppon.foss.module.pickup.sign.api.shared.exception.PickupException;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;

/**
 * pda提货清单的service
 * 
 * @author foss-yuting
 * @date 2014-11-27 上午11:51:55
 * @since
 * @version
 */
public class PdaPickupService implements IPdaPickupService {

	/**
	 * 提货清单Service
	 */
	private IPickupService pickupService;

	/**
	 * 营业部 Service实现
	 */
	private ISaleDepartmentService saleDepartmentService;
	
	/**
	 * 部门复杂service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	/**
	 * 业务互斥锁服务
	 */
	private IBusinessLockService businessLockService;
	
	/**
	 * 运单Dao
	 */
	private IWaybillDao waybillDao;
	
	/**
	 * 提货清单dao层
	 */
	private IPickupDao pickupDao;
	
	/**
	 * 记录 
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PdaPickupService.class);
	/**
	 * 提供Pad提货清单列表数据
	 * 
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return
	 * @see com.deppon.foss.module.pickup.deliver.server.service.impl.PdaPickupService#getPadPickupList(com.deppon.foss.module.pickup.pda.api.shared.dto.PadPickupDto)
	 */
	public List<PadPickupResultDto> getPadPickupList(PadPickupDto pickupDto) {
		String searchOrgCode = StringUtils.EMPTY; 
		if (pickupDto != null) {
			String orgCode = pickupDto.getCurrentDeptCode();
			if (StringUtils.isNotEmpty(orgCode)) {
				SaleDepartmentEntity saleDepartment = saleDepartmentService.querySaleDepartmentByCode(orgCode);
				if (saleDepartment == null ) {
						//非营业部找到它上级所属外场的编码
						List<String> bizTypes = new ArrayList<String>();
						bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
						OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(pickupDto.getCurrentDeptCode(), bizTypes);
						if(orgAdministrativeInfoEntity != null){
							searchOrgCode=orgAdministrativeInfoEntity.getCode();
							pickupDto.setCurrentDeptCode(null);
						}else{
							return null;
						}
					
				} else {//如果不为空，为营业部
					// 是否驻地部门
					if (saleDepartment != null && saleDepartment.checkStation()) {
						searchOrgCode = saleDepartment.getTransferCenter();
					} else {
						searchOrgCode =orgCode;// 如果不是驻地部门，那么最终库存部门是最终配载部门
					}
				}
				pickupDto = getSearchCollection(pickupDto, searchOrgCode);
				List<PadPickupResultDto> queryPdaPickupList = pickupService.queryPdaPickupList(pickupDto);//返回pad数据
				return queryPdaPickupList;
			}
		}
		return null;
	}



	/**
	 * pad撤销运单的状态
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return
	 * @see com.deppon.foss.module.pickup.deliver.server.service.impl.PdaPickupService#pdaChangeWaybillState(java.util.List)
	 */
	@Transactional
	public void pdaChangeWaybillState(List<String> waybillNos){
		if(CollectionUtils.isNotEmpty(waybillNos)){
			for (String waybillNo : waybillNos) {
				PickupResultDto rstPickupResult = pickupDao.isExistByWaybill(waybillNo);
				if(rstPickupResult!=null){
					WaybillEntity rstWaybill = waybillDao.queryWaybillByNo(waybillNo);
					PickupResultDto pickupDto=new PickupResultDto();
					pickupDto.setWaybillNo(waybillNo);
					if(rstWaybill!=null){
						LOGGER.info("##########该单号存在#############");
						updateDataInit(pickupDto, rstWaybill);
						MutexElement mutexElement=new MutexElement(waybillNo, "运单编号", MutexElementType.GOODS_INADVANCE);
						boolean isLocked=businessLockService.lock(mutexElement, NumberConstants.ZERO);
						if(!isLocked){
							LOGGER.error("当前运单操作中，请稍后再试");//记录错误日志
							throw new PickupException(PickupException.WAYBILL_LOCKED);
						}
						businessLockService.unlock(mutexElement);//解锁	
						pickupService.updatePickupStateByPickupResultDto(pickupDto);
					}else{
						LOGGER.info("##########该单号不存在#############");
					}
				}
			}
		}
	}
	
	
	/**
	 * 初始化符合条件的插入数据
	 * @date 2014-11-24 下午6:45:19
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.deliver.server.service.impl.PdaPickupService#updateDataInit(com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupResultDto)
	 */
	private PickupResultDto updateDataInit(PickupResultDto pickupDto,WaybillEntity rstWaybill) {
		if(rstWaybill!=null){
			pickupDto.setBillingGoodsQty(rstWaybill.getGoodsQtyTotal());
			pickupDto.setGoodPackage(rstWaybill.getGoodsPackage());
			pickupDto.setGoodSize(rstWaybill.getGoodsSize());
			pickupDto.setGoodVolume(rstWaybill.getGoodsVolumeTotal());
			pickupDto.setGoodWeight(rstWaybill.getGoodsWeightTotal());
			pickupDto.setState(PickupConstants.GOOD_STATE_REVOCATION);
			pickupDto.setOperTime(new Date());
		}
		return pickupDto;
	}
	/**
	 * 获取pda查询条件
	 * 
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return
	 * @see com.deppon.foss.module.pickup.deliver.server.service.impl.PdaPickupService#getSearchCollection(com.deppon.foss.module.pickup.pda.api.shared.dto.PadPickupDto)
	 */
	private PadPickupDto getSearchCollection(PadPickupDto pickupDto,String searchOrgCode) {
		pickupDto.setState(PickupConstants.GOOD_STATE_HASINFORM);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date start = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.SECOND, -1);
		Date end = calendar.getTime();

		pickupDto.setOperTimeStart(start);
		pickupDto.setOperTimeEnd(end);
		//操作 按照 部门编码+时间+已告知状态查询
		pickupDto.setEndStockOrgCode(searchOrgCode);
		pickupDto.setCurrentDeptCode(null);
		return pickupDto;
	}

	public void setPickupService(IPickupService pickupService) {
		this.pickupService = pickupService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}

	public void setPickupDao(IPickupDao pickupDao) {
		this.pickupDao = pickupDao;
	}
	
}
