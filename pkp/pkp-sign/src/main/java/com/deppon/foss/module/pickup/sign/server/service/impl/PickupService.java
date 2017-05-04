package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PadPickupDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PadPickupResultDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupResultDto;
import com.deppon.foss.module.pickup.sign.api.server.dao.IPickupDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IPickupService;
import com.deppon.foss.module.pickup.sign.api.shared.define.PickupConstants;
import com.deppon.foss.module.pickup.sign.api.shared.exception.PickupException;
import com.deppon.foss.module.pickup.sign.api.shared.vo.PickupResultVo;
import com.deppon.foss.module.pickup.sign.api.shared.vo.PickupVo;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 提货清单service
 * @author  foss-yuting
 * @date   2014-11-21 下午16:09:11
 * @since
 * @version
 */
public class PickupService implements IPickupService {

	/**
	 * 提货清单dao层
	 */
	private IPickupDao pickupDao;
	
	/**
	 * 业务互斥锁服务
	 */
	private IBusinessLockService businessLockService;
	
	/**
	 * 运单Dao
	 */
	private IWaybillDao waybillDao;
	
	/**
	 * 记录 
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PickupService.class);
	
	/**
	 * 分页查询清单列表
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.PickupService#queryPickupListByParams(com.deppon.foss.module.pickup.sign.api.shared.vo.PickupVo, int start,int limit)
	 */
	@Override
	public PickupResultVo queryPickupListByParams(PickupVo pickupVo, int start,int limit) {
		PickupResultVo pickupResultVo=new PickupResultVo();
		PickupDto pickupDto = pickupVo.getPickupDto();
		if(pickupDto!=null){
			pickupDto=initSearchCollection(pickupDto);
			String state = pickupDto.getState();
			if(StringUtils.isNotEmpty(state)&&state.equals(PickupConstants.GOOD_STATE_ALL)){
				pickupDto.setState(null);
			}
			List<PickupResultDto> pickupResultDtoList = pickupDao.queryPickupListByParams(pickupDto, start,limit);
			if(CollectionUtils.isNotEmpty(pickupResultDtoList)){
				for (int i=0;i<pickupResultDtoList.size();i++) {
					pickupResultDtoList.get(i).setSerialNumber(start+i+1);
					pickupResultDtoList.get(i).setState(DictUtil.rendererSubmitToDisplay(pickupResultDtoList.get(i).getState(), DictionaryConstants.PKP_INADVANCE_GOODS_STATE));
				}
			}
			pickupResultVo.setPickupResultDtoList(pickupResultDtoList);
			pickupResultVo.setTotalCount(pickupDao.queryPickupInfoCountByParams(pickupDto));
		}
		return pickupResultVo;
	}
	
	/**
	 * 批量更新
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.PickupService#updatePickupState(com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupDto)
	 */
	@Override
	@Transactional
	public void updatePickupState(List<PickupDto> pickupList) {
		PickupResultDto pickupResultDto=null;
		if(CollectionUtils.isNotEmpty(pickupList)){
			for (PickupDto pickupDto : pickupList) {
				String waybillNo = pickupDto.getWaybillNo();
				WaybillEntity rstWaybill = waybillDao.queryWaybillByNo(waybillNo);
				pickupResultDto=new PickupResultDto();
				pickupResultDto.setWaybillNo(waybillNo);
				if(rstWaybill==null){
					pickupResultDto.setState(PickupConstants.GOOD_STATE_REVOCATION);
				}else{
					pickupResultDto=updateDataInit(pickupResultDto, rstWaybill);
				}
				MutexElement mutexElement=new MutexElement(waybillNo, "运单编号", MutexElementType.GOODS_INADVANCE);
				boolean isLocked=businessLockService.lock(mutexElement, NumberConstants.ZERO);
				if(!isLocked){
					LOGGER.error("当前运单操作中，请稍后再试");//记录错误日志
					throw new PickupException(PickupException.WAYBILL_LOCKED);
				}
				pickupDao.updatePickupStateByEntity(pickupResultDto);
				businessLockService.unlock(mutexElement);//解锁	
			}
		}
	}
	

	/**
	 * 初始化符合条件的插入数据
	 * @date 2014-11-24 下午6:45:19
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.SignService#updateDataInit(com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupResultDto)
	 */
	private PickupResultDto updateDataInit(PickupResultDto rstPickupResultDto,WaybillEntity rstWaybill) {
		if(rstWaybill!=null){
			rstPickupResultDto.setBillingGoodsQty(rstWaybill.getGoodsQtyTotal());
			rstPickupResultDto.setGoodPackage(rstWaybill.getGoodsPackage());
			rstPickupResultDto.setGoodSize(rstWaybill.getGoodsSize());
			rstPickupResultDto.setGoodVolume(rstWaybill.getGoodsVolumeTotal());
			rstPickupResultDto.setGoodWeight(rstWaybill.getGoodsWeightTotal());
			rstPickupResultDto.setOperTime(new Date());
			rstPickupResultDto.setState(PickupConstants.GOOD_STATE_REVOCATION);
		}
		return rstPickupResultDto;
	}
	/**
	 * 单个实体更新
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.PickupService#updatePickupStateByPickupResultDto(com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupResultDto)
	 */
	@Override
	@Transactional
	public void updatePickupStateByPickupResultDto(PickupResultDto dto) {
		MutexElement mutexElement=new MutexElement(dto.getWaybillNo(), "运单编号", MutexElementType.GOODS_INADVANCE);
		boolean isLocked=businessLockService.lock(mutexElement, NumberConstants.ZERO);
		if(!isLocked){
			LOGGER.error("当前运单操作中，请稍后再试");//记录错误日志
			throw new PickupException(PickupException.WAYBILL_LOCKED);
		}
		if(dto!=null){
			pickupDao.updatePickupStateByEntity(dto);
		}
		businessLockService.unlock(mutexElement);//解锁
	}
	
	/**
	 * 保存数据
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.PickupService#insertEntity(com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupResultDto.PickupResultDto)
	 */
	@Override
	@Transactional
	public void insertEntity(PickupResultDto rstPickupResultDto) {
		MutexElement mutexElement=new MutexElement(rstPickupResultDto.getWaybillNo(), "运单编号", MutexElementType.GOODS_INADVANCE);
		boolean isLocked=businessLockService.lock(mutexElement, NumberConstants.ZERO);
		if(!isLocked){
			LOGGER.error("当前运单操作中，请稍后再试");//记录错误日志
			throw new PickupException(PickupException.WAYBILL_LOCKED);
		}
		if(rstPickupResultDto!=null){
			pickupDao.insertEntity(rstPickupResultDto);
		}
		businessLockService.unlock(mutexElement);//解锁
	}
	/**
	 * 初始化查询条件
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.PickupService#initSearchCollection(com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupDto)
	 */
	private PickupDto initSearchCollection(PickupDto pickupDto) {
		String waybillNo = pickupDto.getWaybillNo();
		if(StringUtils.isNotEmpty(waybillNo)){
			pickupDto.setOperTimeEnd(null);
			pickupDto.setOperTimeStart(null);
			pickupDto.setState(null);
		}
		return pickupDto;
	}

	
	/**
	 * 返回Pda需要的提货清单列表数据
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.PickupService#queryPdaPickupList(com.deppon.foss.module.pickup.pda.api.shared.dto.PadPickupDto)
	 */
	@Override
	public List<PadPickupResultDto> queryPdaPickupList(PadPickupDto pickupDto) {
		return pickupDao.queryPdaPickupList(pickupDto);
	}
	
	/**
	 * 判断记录是否存在
	 * @date 2014-11-27 下午3:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.PickupService#isExistByWaybill(java.lang.String waybillNo)
	 */
	@Override
	public PickupResultDto isExistByWaybill(String waybillNo) {
		return pickupDao.isExistByWaybill(waybillNo);
	}

	public void setPickupDao(IPickupDao pickupDao) {
		this.pickupDao = pickupDao;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}
	
	

}
