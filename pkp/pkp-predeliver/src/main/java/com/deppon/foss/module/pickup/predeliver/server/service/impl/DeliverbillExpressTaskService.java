package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillExpressTaskDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillExpressTaskService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDataDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillExpressTaskDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.DeliverbillException;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.transfer.departure.api.server.service.IWebDepartureService;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.departure.api.shared.dto.AutoDepartDTO;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 快递派件交接 提交任务后更新派送单数据
 * @author 243921-foss-zhangtingting
 * @date 2015-04-20 上午11:01:05
 */
public class DeliverbillExpressTaskService implements
		IDeliverbillExpressTaskService {
	/**
	 * 记录
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DeliverbillExpressTaskService.class);

	private IDeliverbillExpressTaskDao deliverbillExpressTaskDao;

	private IDeliverbillDao deliverbillDao;
	
	private IDeliverbillService deliverbillService;

	private IArriveSheetManngerService arriveSheetManngerService;

	private ISaleDepartmentService saleDepartmentService;

	private IWebDepartureService webDepartureService;
	
	/**
	 * 验证传入的参数是否为空
	 * 如果为空抛出异常信息
	 * @author
	 * 		 foss-zhangtingting
	 * @date 
	 * 		2015-6-11下午3:50:19
	 * @param dto
	 * @see
	 */
	private void validateParams(DeliverbillExpressTaskDto dto) {
		/**
		 * 接口传入的数据为空
		 */
		if (dto == null) {
			LOGGER.error("接口传入的数据为空");
			throw new DeliverbillException("接口传入的数据为空!");
		}else if (CollectionUtils.isEmpty(dto.getList())){
			LOGGER.error("传入的运单信息列表为空");
			throw new DeliverbillException("传入的运单信息列表为空!");
		}else if(StringUtils.isBlank(dto.getVehicleNo())){
			LOGGER.error("传入的车牌号为空");
			throw new DeliverbillException("传入的车牌号为空!");
		}else if(StringUtils.isBlank(dto.getCreateOrgCode())) {
			LOGGER.error("创建部门编码为空");
			throw new DeliverbillException("创建部门编码为空!");
		}else if(StringUtils.isBlank(dto.getCreateOrgName())) {
			LOGGER.error("创建部门名称为空");
			throw new DeliverbillException("创建部门名称为空!");
		}else if (StringUtils.isBlank(dto.getCreateUserCode())){
			LOGGER.error("创建人工号为空");
			throw new DeliverbillException("创建人工号为空!");
		}else if(StringUtils.isBlank(dto.getCreateUserName())) {
			LOGGER.error("创建人姓名为空");
			throw new DeliverbillException("创建人姓名为空!");
		}
	}

	/**
	 * 根据派送单号、运单号更新派送单、派送单明细数据及生成新的派送单
	 * @author 243921-foss-zhangtingting
	 * @date 2015-05-11 下午4:15:01
	 * @param deliverbillExpressTaskDto 提交任务后生成派送单dto
	 */
	@Override
	public DeliverbillExpressTaskDto createDeliverbillExpressTask(DeliverbillExpressTaskDto deliverbillExpressTaskDto) {
		
		this.validateParams(deliverbillExpressTaskDto);//验证传的值

		List<DeliverbillExpressTaskDto> list = deliverbillExpressTaskDto.getList();
		DeliverbillEntity deliverbillEntityNew = new DeliverbillEntity();
		//派送单明细ID列表
		List<String> deliverbillDetailIds = new ArrayList<String>();
		//已交接派送单列表
		List<DeliverbillDataDto> deliverbillDataDtoList = new ArrayList<DeliverbillDataDto>();

		int waybillQtyNew = 0;//总票数 新生成的派送单
		if(null != list && list.size() > 0){
			waybillQtyNew = list.size();
			for (DeliverbillExpressTaskDto dto : list){

				int goodTotal = 0;//总件数
				int waybillQty = 0;//总票数
				BigDecimal payAmountTotal = BigDecimal.ZERO;//总到付金额
				BigDecimal weightTotal = BigDecimal.ZERO;//总重量
				BigDecimal volumeTotal = BigDecimal.ZERO;//总体积

				DeliverbillDto deliverbillDto = new DeliverbillDto();
				deliverbillDto.setDeliverbillNo(dto.getDeliverbillNo());
				String deliverbillId = ""; //派送单ID
				//已派件交接列表中无数据
				if(CollectionUtils.isEmpty(deliverbillDataDtoList)){
					//根据派送单号查询派送单
					DeliverbillEntity deliverbillEntity = deliverbillDao.queryByDeliverbillDto(deliverbillDto);
					if(null != deliverbillEntity){
						deliverbillId = deliverbillEntity.getId();
						//根据派送单ID和运单号查询派送单明细
						DeliverbillDetailEntity deliverbillDetailEntity = deliverbillExpressTaskDao.getDeliverbillDetail(deliverbillId,dto.getWaybillNo());
						if(null != deliverbillDetailEntity){
							//查询该运单是否已签收--到达联表中查询
							if(this.isDelivereByWaybilNo(dto.getWaybillNo())){
								//将派送单明细ID存入到派送单明细ID列表中，供更新派送单明细表使用
	 							deliverbillDetailIds.add(deliverbillDetailEntity.getId());

								goodTotal = dto.getGoodQtyTotal().intValue();
								waybillQty = 1;
								payAmountTotal = deliverbillDetailEntity.getPayAmount();
								weightTotal = deliverbillDetailEntity.getWeight();
								volumeTotal = deliverbillDetailEntity.getGoodsVolumeTotal();
							}else{
								LOGGER.error("该运单状态不是派送中，运单号："+dto.getWaybillNo());
								throw new DeliverbillException("该运单状态不是派送中，运单号："+dto.getWaybillNo());
							}
						}else{
							LOGGER.error("无此派送单明细信息，运单号："+dto.getWaybillNo());
							throw new DeliverbillException("无此派送单明细信息，运单号："+dto.getWaybillNo());
						}
					}else{
						LOGGER.error("无此派送单信息，派送单号："+dto.getDeliverbillNo());
						throw new DeliverbillException("无此派送单信息，派送单号："+dto.getDeliverbillNo());
					}
				}else{
					if(null != this.getDeliverbillDataDto(deliverbillDataDtoList, dto.getDeliverbillNo())){//存在派送单
						DeliverbillDataDto dataDto = this.getDeliverbillDataDto(deliverbillDataDtoList, dto.getDeliverbillNo());
						deliverbillId = dataDto.getDeliverbillId();
						//根据派送单ID和运单号查询派送单明细
						DeliverbillDetailEntity deliverbillDetailEntity = deliverbillExpressTaskDao.getDeliverbillDetail(deliverbillId,dto.getWaybillNo());
						if(null != deliverbillDetailEntity){
							//查询该运单是否已签收--到达联表中查询
							if(this.isDelivereByWaybilNo(dto.getWaybillNo())){
								//将派送单明细ID存入到派送单明细ID列表中，供更新派送单明细表使用
								deliverbillDetailIds.add(deliverbillDetailEntity.getId());

								goodTotal = dataDto.getGoodsQtyTotal().intValue() + dto.getGoodQtyTotal().intValue();
								waybillQty = dataDto.getWaybillQtyTotal().intValue() + 1;
								payAmountTotal = dataDto.getPayAmountTotal().add(deliverbillDetailEntity.getPayAmount());
								weightTotal = dataDto.getWeightTotal().add(deliverbillDetailEntity.getWeight());
								volumeTotal = dataDto.getVolumeTotal().add(deliverbillDetailEntity.getGoodsVolumeTotal());

								//删除之前存在的派送单数据
								deliverbillDataDtoList.remove(dataDto);
							}else{
								LOGGER.error("该运单状态不是派送中，运单号："+dto.getWaybillNo());
								throw new DeliverbillException("该运单状态不是派送中，运单号："+dto.getWaybillNo());
							}
						}else{
							LOGGER.error("无此派送单明细信息，运单号："+dto.getWaybillNo());
							throw new DeliverbillException("无此派送单明细信息，运单号："+dto.getWaybillNo());
						}
					}else{//不存在派送单
						//根据派送单号查询派送单
						DeliverbillEntity deliverbillEntity = deliverbillDao.queryByDeliverbillDto(deliverbillDto);
						if(null != deliverbillEntity){
							deliverbillId = deliverbillEntity.getId();
							//根据派送单ID和运单号查询派送单明细
							DeliverbillDetailEntity deliverbillDetailEntity = deliverbillExpressTaskDao.getDeliverbillDetail(deliverbillId,dto.getWaybillNo());
							if(null != deliverbillDetailEntity){
								//查询该运单是否已签收--到达联表中查询
								if(this.isDelivereByWaybilNo(dto.getWaybillNo())){
									//将派送单明细ID存入到派送单明细ID列表中，供更新派送单明细表使用
									deliverbillDetailIds.add(deliverbillDetailEntity.getId());

									goodTotal = dto.getGoodQtyTotal().intValue();
									waybillQty = 1;
									payAmountTotal = deliverbillDetailEntity.getPayAmount();
									weightTotal = deliverbillDetailEntity.getWeight();
									volumeTotal = deliverbillDetailEntity.getGoodsVolumeTotal();
								}else{
									LOGGER.error("该运单状态不是派送中，运单号："+dto.getWaybillNo());
									throw new DeliverbillException("该运单状态不是派送中，运单号："+dto.getWaybillNo());
								}
							}else{
								LOGGER.error("无此派送单明细信息，运单号："+dto.getWaybillNo());
								throw new DeliverbillException("无此派送单明细信息，运单号："+dto.getWaybillNo());
							}
						}else{
							LOGGER.error("无此派送单信息，派送单号："+dto.getDeliverbillNo());
							throw new DeliverbillException("无此派送单信息，派送单号："+dto.getDeliverbillNo());
						}
					}
				}
				//把派送单的数据存入到已交接派送单列表中
				DeliverbillDataDto deliverbillDataDto = new DeliverbillDataDto();
				deliverbillDataDto.setDeliverbillId(deliverbillId);
				deliverbillDataDto.setDeliverbillNo(dto.getDeliverbillNo());
				deliverbillDataDto.setGoodsQtyTotal(goodTotal);
				deliverbillDataDto.setWaybillQtyTotal(waybillQty);
				deliverbillDataDto.setPayAmountTotal(payAmountTotal);
				deliverbillDataDto.setWeightTotal(weightTotal);
				deliverbillDataDto.setVolumeTotal(volumeTotal);
				deliverbillDataDtoList.add(deliverbillDataDto);
			}
		}
		int goodTotalNew = 0;//总件数  新生成的派送单
		BigDecimal payAmountTotalNew = BigDecimal.ZERO;//总到付金额  新生成的派送单
		BigDecimal weightTotalNew = BigDecimal.ZERO;//总重量  新生成的派送单
		BigDecimal volumeTotalNew = BigDecimal.ZERO;//总体积  新生成的派送单

		//更新已交接的派送单数据
		if(null != deliverbillDataDtoList && deliverbillDataDtoList.size() > 0){
			for(DeliverbillDataDto dto : deliverbillDataDtoList){
				//新生成的派送单的数据
				goodTotalNew = goodTotalNew + dto.getGoodsQtyTotal().intValue();
				payAmountTotalNew = payAmountTotalNew.add(dto.getPayAmountTotal());
				weightTotalNew = weightTotalNew.add(dto.getWeightTotal());
				volumeTotalNew = volumeTotalNew.add(dto.getVolumeTotal());
				//根据派送单号查询派送单
				DeliverbillEntity deliverbillEntity = deliverbillDao.queryById(dto.getDeliverbillId());
				//更新派送单的数据
				deliverbillEntity.setWaybillQtyTotal(deliverbillEntity.getWaybillQtyTotal().intValue() - dto.getWaybillQtyTotal().intValue());
				deliverbillEntity.setGoodsQtyTotal(deliverbillEntity.getGoodsQtyTotal().intValue() - dto.getGoodsQtyTotal().intValue());
				deliverbillEntity.setPayAmountTotal(deliverbillEntity.getPayAmountTotal().subtract(dto.getPayAmountTotal()));
				deliverbillEntity.setWeightTotal(deliverbillEntity.getWeightTotal().subtract(dto.getWeightTotal()));
				deliverbillEntity.setVolumeTotal(deliverbillEntity.getVolumeTotal().subtract(dto.getVolumeTotal()));
				deliverbillEntity.setOperator(deliverbillExpressTaskDto.getCreateUserName());
				deliverbillEntity.setOperatorCode(deliverbillExpressTaskDto.getCreateUserCode());
				deliverbillEntity.setOperateOrgName(deliverbillExpressTaskDto.getCreateOrgName());
				deliverbillEntity.setOperateOrgCode(deliverbillExpressTaskDto.getCreateOrgCode());
				deliverbillEntity.setOperateTime(new Date());
				//如果总件数为0，则派送单状态修改为已取消
				int goodTotal = deliverbillEntity.getGoodsQtyTotal().intValue();
				if(goodTotal == 0){
					deliverbillEntity.setStatus(DeliverbillConstants.STATUS_CANCELED);
				}

				deliverbillExpressTaskDao.updateDeliverbillByDeliverbillNo(deliverbillEntity);
			}
		}

		//交接后创建新的派送单
		deliverbillEntityNew.setDeliverbillNo("P" + deliverbillService.querySequence());
		//返回给中转的派送单号
		deliverbillExpressTaskDto.setDeliveryNo(deliverbillEntityNew.getDeliverbillNo());

		deliverbillEntityNew.setVehicleNo(deliverbillExpressTaskDto.getVehicleNo());
		deliverbillEntityNew.setDriverCode(deliverbillExpressTaskDto.getCreateUserCode());
		deliverbillEntityNew.setDriverName(deliverbillExpressTaskDto.getCreateUserName());
		deliverbillEntityNew.setWaybillQtyTotal(waybillQtyNew);
		deliverbillEntityNew.setGoodsQtyTotal(goodTotalNew);
		deliverbillEntityNew.setPayAmountTotal(payAmountTotalNew);
		deliverbillEntityNew.setWeightTotal(weightTotalNew);
		deliverbillEntityNew.setVolumeTotal(volumeTotalNew);
		deliverbillEntityNew.setCreateUserCode(deliverbillExpressTaskDto.getCreateUserCode());
		deliverbillEntityNew.setCreateUserName(deliverbillExpressTaskDto.getCreateUserName());
		deliverbillEntityNew.setCreateOrgCode(deliverbillExpressTaskDto.getCreateOrgCode());
		deliverbillEntityNew.setCreateOrgName(deliverbillExpressTaskDto.getCreateOrgName());
		deliverbillEntityNew.setSubmitTime(new Date());
		deliverbillEntityNew.setIsExpress(FossConstants.YES);

		AutoDepartDTO autoDto = this.getAutoDepartDto(deliverbillExpressTaskDto);

		SaleDepartmentEntity saleDepartment = saleDepartmentService.querySaleDepartmentByCode(deliverbillExpressTaskDto.getCreateOrgCode());
		if (null != saleDepartment){
			//如果是营业部 并且 是驻地部门 才调用
			if(saleDepartment.checkStation()) {
				//调用中转接口 获得车辆放行ID
				String tOptTruckDepartId = webDepartureService.autoDepart(autoDto);
				deliverbillEntityNew.settOptTruckDepartId(tOptTruckDepartId);
			}
		}else {	//非营业部 也进行调用
			//调用中转接口 获得车辆放行ID
			String tOptTruckDepartId = webDepartureService.autoDepart(autoDto);
			deliverbillEntityNew.settOptTruckDepartId(tOptTruckDepartId);
		}
		deliverbillEntityNew.setStatus(DeliverbillConstants.STATUS_CONFIRMED);//已确认
		deliverbillEntityNew.setOperator(deliverbillExpressTaskDto.getCreateUserName());
		deliverbillEntityNew.setOperatorCode(deliverbillExpressTaskDto.getCreateUserCode());
		deliverbillEntityNew.setOperateOrgName(deliverbillExpressTaskDto.getCreateOrgName());
		deliverbillEntityNew.setOperateOrgCode(deliverbillExpressTaskDto.getCreateOrgCode());
		deliverbillEntityNew.setOperateTime(new Date());
		deliverbillEntityNew.setFastWaybillQty(Integer.valueOf(0));
		deliverbillEntityNew.setTransferCenter(deliverbillExpressTaskDto.getCreateOrgCode());
		deliverbillEntityNew.setCurrencyCode(WaybillConstants.RMB);
		deliverbillEntityNew.setDeliverType(DeliverbillConstants.NOMAL);

		deliverbillEntityNew = deliverbillDao.add(deliverbillEntityNew);

		//更新已交接的派送单明细中的派送单ID
		if(null != deliverbillDetailIds && deliverbillDetailIds.size() > 0){
			deliverbillExpressTaskDao.updateDeliverbillDetailByCondition(deliverbillEntityNew.getId(),deliverbillDetailIds);
		}		

		return deliverbillExpressTaskDto;
	}

	/**
	 * 判断已交接的派送单列表中存在某个派送单号 返回Dto
	 * @author 243921-foss-zhangtingting
	 * @date 2015-05-12 下午2:43:25
	 * @param list 已交接的派送单列表
	 * @param deliverbillNo 派送单号
	 * @return
	 */
	private DeliverbillDataDto getDeliverbillDataDto(List<DeliverbillDataDto> list,String deliverbillNo){
		for(DeliverbillDataDto dto : list){
			if(deliverbillNo.equals(dto.getDeliverbillNo())){
				return dto;
			}
		}
		return null;
	}

	/**
	 * 根据运单编号判断该运单在到达联中的状态是否是派送中
	 * @author 243921-foss-zhangtingting
	 * @date 2015-05-12 下午2:43:25
	 * @param waybillNo 运单号
	 * @return
	 */
	private boolean isDelivereByWaybilNo(String waybillNo){
		ArriveSheetEntity arriveSheetEntity = new ArriveSheetEntity();
		arriveSheetEntity.setWaybillNo(waybillNo);
		arriveSheetEntity.setActive(FossConstants.YES);
		arriveSheetEntity.setStatus(ArriveSheetConstants.STATUS_DELIVER);
		arriveSheetEntity.setDestroyed(FossConstants.NO);
		List<ArriveSheetEntity> arriveSheets = arriveSheetManngerService.queryArriveSheetByWaybillNo(arriveSheetEntity);
		if(null !=arriveSheets && arriveSheets.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 获取自动放行数据信息
	 * @author 243921-foss-zhangtingting
	 * @date 2015-05-15 下午2:21:06
	 * @param deliverbillExpressTaskDto
	 * @return
	 */
	private AutoDepartDTO getAutoDepartDto(DeliverbillExpressTaskDto deliverbillExpressTaskDto){
		if(null != deliverbillExpressTaskDto){
			AutoDepartDTO autoDto = new AutoDepartDTO();
			autoDto.setVehicleNo(deliverbillExpressTaskDto.getVehicleNo());
			autoDto.setApplyUserName(deliverbillExpressTaskDto.getCreateUserName());
			autoDto.setApplyUserCode(deliverbillExpressTaskDto.getCreateUserCode());
			autoDto.setApplyOrgCode(deliverbillExpressTaskDto.getCreateOrgCode());
			autoDto.setAutoDepartType(DepartureConstant.AUTO_DEPART_TYPE_DELIVERBILL);
			autoDto.setDepartItems(DepartureConstant.DEPART_ITEM_TYPE_PKP);
			autoDto.setDriverCode(deliverbillExpressTaskDto.getCreateUserCode());
			autoDto.setDriverName(deliverbillExpressTaskDto.getCreateUserName());
			return autoDto;
		}
		return null;
	}
	public void setDeliverbillExpressTaskDao(
			IDeliverbillExpressTaskDao deliverbillExpressTaskDao) {
		this.deliverbillExpressTaskDao = deliverbillExpressTaskDao;
	}

	public void setDeliverbillDao(IDeliverbillDao deliverbillDao) {
		this.deliverbillDao = deliverbillDao;
	}

	public void setDeliverbillService(IDeliverbillService deliverbillService) {
		this.deliverbillService = deliverbillService;
	}

	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setWebDepartureService(IWebDepartureService webDepartureService) {
		this.webDepartureService = webDepartureService;
	}

	public void setArriveSheetManngerService(IArriveSheetManngerService arriveSheetManngerService) {
		this.arriveSheetManngerService = arriveSheetManngerService;
	}
}
