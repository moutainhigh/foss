package com.deppon.foss.module.pickup.predeliver.server.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeleteGisAddressService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverHandoverbillService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IVisibleOrderService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IWaybilldetailNewService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverHandoverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDeliverNewCountDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDetailBillArrageDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.DeliverbillException;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.HandoverBillVo;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.WaybilldetailNewVo;
import com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadGapReportService;
import com.deppon.foss.module.transfer.load.api.shared.domain.DiffReportReturnNumEntity;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

public class WaybilldetailAction extends AbstractAction{
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PartitionedViewAction.class);
	
	/** 
	 * 序列化
	 */
	private static final long serialVersionUID = 7512460180364008438L;	
	/** 
	 * 部门查询起始. 
	 */
	private static final int BEGIN_NUM = 0;
	private WaybilldetailNewVo vo=new WaybilldetailNewVo();
    private IDeliverHandoverbillService deliverHandoverbillService;
    private IDeliverbillService deliverbillService;
    private IDeliverLoadGapReportService deliverLoadGapReportService;
	
    private List<HandoverBillVo> handoverBillVos;
    private HandoverBillVo handoverBillVo;
	private IWaybilldetailNewService waybilldetailNewService;
	private IBusinessLockService businessLockService;
	private IVisibleOrderService visibleOrderService;
	private ITruckSchedulingTaskService truckSchedulingTaskService;
	private IRegionalVehicleService regionalVehicleService;
	private IDeleteGisAddressService deleteGisAddressService;
	// 运单号
	private String waybillNo;
	
	/**
	 * 设置deleteGisAddressService  
	 * @param deleteGisAddressService deleteGisAddressService 
	 */
	public void setDeleteGisAddressService(
			IDeleteGisAddressService deleteGisAddressService) {
		this.deleteGisAddressService = deleteGisAddressService;
	}
	/**
	 * 运单明细------查询待排单运单
	 * @return
	 */
	@JSON
	public String queryWaybilldetails(){
		try {
			// 查询符合条件的运单总记录数
			WaybillDeliverNewCountDto dtoCount= this.waybilldetailNewService.queryWaybilldetailNewCount(vo.getWaybillDetailNewQueryDto());
			// 根据运单总记录数
			if (dtoCount != null  && dtoCount.getTotalGoodsQty()!= null &&dtoCount.getTotalGoodsQty()>0) {
				this.setTotalCount(dtoCount.getTotalGoodsQty());
				vo.setWaybillDeliverNewCountDto(dtoCount);
				// 查询符合条件的记录列表
				//vo.setWaybillDetailDtos(waybilldetailNewService.queryWaybillDetailNewList(vo.getWaybillDetailNewQueryDto() ,this.getStart(), this.getLimit()));
				// modify by foss-sunyanfei 2015-8-4
				LOGGER.info("运单明细 ---- 查询符合条件的记录列表" +ReflectionToStringBuilder.toString(vo.getWaybillDetailNewQueryDto()));
				vo.setWaybillDetailDtos(waybilldetailNewService.queryWaybillDetailNewListByCondition(vo.getWaybillDetailNewQueryDto() ,this.getStart(), this.getLimit()));
				//查询理货员退单次数
				for(int i = 0, len = vo.getWaybillDetailDtos().size(); i < len ; i++) {
					WaybillDetailDto waybillDetailDto = vo.getWaybillDetailDtos().get(i);
					String waybillNo = waybillDetailDto.getWaybillNo();
					//查询交单时间
					Date surrenderTime = waybilldetailNewService.queryBilltimeByWaybillNo(waybillDetailDto.getWaybillNo());
					//调用中转的接口查询出少货和退回次数
					DiffReportReturnNumEntity queryDiffReportRN = deliverLoadGapReportService.queryDiffReportReturnNum(waybillNo,surrenderTime);
					int returnNum = queryDiffReportRN.getReturnNum();
					int loseNum = queryDiffReportRN.getLoseNum();
					waybillDetailDto.setReturnNumber(returnNum+loseNum);
				}
			} else {
				vo.setWaybillDetailDtos(null);
				WaybillDeliverNewCountDto dtoC = new WaybillDeliverNewCountDto();
				dtoC.setTotalGoodsQty(Long.valueOf(0));
				dtoC.setTotalGoodsWeight(BigDecimal.ZERO);
				dtoC.setTotalGoodsVolume(BigDecimal.ZERO);
				vo.setWaybillDeliverNewCountDto(dtoC);
				// 设置页面显示的记录总数
				this.setTotalCount(Long.valueOf(0));
			}
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}
	/**
	 * 修改预处理建议
	 * @return
	 */
	@JSON
	public String updateSuggestionTreatment(){
		try {
			if(vo.getDeliverHandoverbillEntity()!= null ){
				DeliverHandoverbillEntity entity = vo.getDeliverHandoverbillEntity();
				entity.setOldActive(FossConstants.YES);
				if(StringUtils.isNotBlank(entity.getActualVehicleNo())&& entity.getPreDeliverDate()!=null){
					SimpleTruckScheduleDto simpleTruckScheduleDto=new SimpleTruckScheduleDto();
					simpleTruckScheduleDto.setVehicleNo(entity.getActualVehicleNo());
					simpleTruckScheduleDto.setSchedulingStatus(FossConstants.YES);
					simpleTruckScheduleDto.setYmd(DateUtils.convert(entity.getPreDeliverDate(),DateUtils.DATE_FORMAT));
					List<SimpleTruckScheduleDto> dtos=truckSchedulingTaskService.queryTaskByVehicleNoAndDate(simpleTruckScheduleDto);
					if(CollectionUtils.isNotEmpty(dtos)&& dtos.size()>0){
						entity.setIsVehicleScheduling(FossConstants.YES);
					}else{
						entity.setIsVehicleScheduling(FossConstants.NO);
					}
				}else if(StringUtils.isBlank(entity.getActualVehicleNo())){
					entity.setActualVehicleNo("N/A");
					entity.setIsVehicleScheduling(FossConstants.NO);
					
				}
				entity.setPreDeliverDate(null);
				deliverHandoverbillService.updateByWaybillNoSelective(entity);
			}
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}
	
	/**
	 * 批量修改预处理建议
	 * @return
	 */
	@JSON
	public String updateBatchSuggestionTreatment(){
		try {
			List<DeliverHandoverbillEntity> list = vo.getDeliverHandoverbillEntityList();
			if(CollectionUtils.isNotEmpty(list)){
				for(DeliverHandoverbillEntity entity : list){
					entity.setOldActive(FossConstants.YES);
					if(StringUtils.isNotBlank(entity.getActualVehicleNo())&& entity.getPreDeliverDate()!=null){
						SimpleTruckScheduleDto simpleTruckScheduleDto=new SimpleTruckScheduleDto();
						simpleTruckScheduleDto.setVehicleNo(entity.getActualVehicleNo());
						simpleTruckScheduleDto.setSchedulingStatus(FossConstants.YES);
						simpleTruckScheduleDto.setYmd(DateUtils.convert(entity.getPreDeliverDate(),DateUtils.DATE_FORMAT));
						List<SimpleTruckScheduleDto> dtos=truckSchedulingTaskService.queryTaskByVehicleNoAndDate(simpleTruckScheduleDto);
						if(CollectionUtils.isNotEmpty(dtos)&& dtos.size()>0){
							entity.setIsVehicleScheduling(FossConstants.YES);
						}else{
							entity.setIsVehicleScheduling(FossConstants.NO);
						}
					}else if(StringUtils.isBlank(entity.getActualVehicleNo())){
						entity.setActualVehicleNo("N/A");
						entity.setIsVehicleScheduling(FossConstants.NO);
						
					}
					entity.setPreDeliverDate(null);
					deliverHandoverbillService.updateByWaybillNoSelective(entity);
				}
			}
			
		} catch (BusinessException e) {
			// 记录日志
			LOGGER.error(e.getMessage(), e);
			// 返回异常
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}
	
	/**
	 * 2. 保存新增(更新)预派送单
	 * @return
	 */
	@JSON
	public String waybillDetailSaveDeliverbill() {
		try {
			//外请司机不能修改
			if(StringUtils.isNotBlank(this.vo.getDeliverbill().getDelStatus())) {
				if(this.vo.getDeliverbill().getDelStatus().equals("LOADED")|| this.vo.getDeliverbill().getDelStatus().equals("CONFIRMED")){
					int driverCode=this.vo.getDeliverbill().getDriverCode().length();
					if(driverCode == NumberConstants.NUMBER_15 || driverCode == NumberConstants.NUMBER_18) {
						return super.returnError("外请司机暂时不能更改，请选择其他类型司机");
					}
				}
			}
			
			//拖动排序保存--239284
			String dragStr = this.vo.getDragStr();
			if (StringUtils.isNotEmpty(dragStr)) {
				JSONArray arr = new JSONArray().fromObject(dragStr);
		        Iterator iters = arr.iterator();
		        List<String> deliverDetailIds = new ArrayList<String>();
		        while (iters.hasNext()) {
		        	Map map = (Map)iters.next();
		        	deliverDetailIds.add((String)map.get("id"));
		        }
		       if (deliverDetailIds.size() > 0) {
		    	   this.deliverbillService.updateDeliverDetailSeriNoByDrag(deliverDetailIds);
		       }
			}
			
			//保存(新增/更新)派送单
			DeliverbillEntity deliverbill = this.waybilldetailNewService.saveDeliverbill(this.vo.getDeliverbill());
			
			//若派送单为空
			if (deliverbill == null) {
				//返回异常
				return super.returnError("保存失败");
			}
			//设置派送单
			this.vo.setDeliverbill(deliverbill);
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	/**
	 * 4. 点击"添加到派送单->"按钮
	 * @return
	 */
	@JSON
	public String  waybillDetailAddWaybillToArrange() {
		MutexElement mutexElement = new MutexElement(vo.getDeliverbill().getDeliverbillNo(), "运单明细派送单编号", MutexElementType.DELIVERBILL_NO);
		try {
			//互斥锁定
			boolean isLocked = businessLockService.lock(mutexElement, BEGIN_NUM);
			//若未上锁
			if(!isLocked){
				//抛出派送单异常
				throw new DeliverbillException(DeliverbillException.DELIVERBILL_LOCKED);
			}
			if(vo.getWaybillToArrangeDtoList()!=null){
				// 获取当前用户
				CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
				//添加运单至派送单
				this.vo.setWaybillToArrangeDtoList(this.waybilldetailNewService.addWaybillToArrange(vo.getDeliverbill(),this.vo.getWaybillToArrangeDtoList(),currentInfo));
				//解锁
				businessLockService.unlock(mutexElement);
				//返回成功
				return super.returnSuccess("排单成功");
			}else{
				throw new DeliverbillException("请选择待排运单!");
			}
		
		} catch (BusinessException e) {
			//解锁
			businessLockService.unlock(mutexElement);
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	/**
	 * 输入运单号右移
	 * @author 159231 meiying
	 * 2015-6-9  上午8:28:50
	 * @return
	 */
	@JSON
	public String  waybillDetailAddToArrangeByWaybillNo() {
		try {
			if(vo.getDeliverbill()!=null && StringUtils.isNotBlank(vo.getWaybillNo())){
				
				DeliverbillEntity deliverbill =waybilldetailNewService.waybillDetailAddToArrangeByWaybillNo(vo.getDeliverbill(),vo.getWaybillNo(),vo.getDeliverDate());
				//设置派送单
				this.vo.setDeliverbill(deliverbill);
				return super.returnSuccess("排单成功");
			}else{
				return super.returnError("请输入运单号!");
			}
			//返回成功
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	/**
	 * 5-1.查询派送单下的已排单信息，
	 * @param 、车牌号、预计送货时间(vo.getVehilceNo(), vo.getDeliverDate())
	 * @return the string
	 */
	@JSON
	public String waybillDetailQueryDetailList() {
		try {
			if(StringUtils.isNotBlank(vo.getDeliverbill().getId())){
				//已排单明细
				List<WaybillDetailBillArrageDto> waybillDetailArrageDtos = this.waybilldetailNewService.queryDeliverbillDetailList(vo.getDeliverbill().getId());
				if(waybillDetailArrageDtos!=null && waybillDetailArrageDtos.size()>0){
					WaybillDeliverNewCountDto waybillDeliverNewCountDto=new WaybillDeliverNewCountDto();
					//总票数
					waybillDeliverNewCountDto.setTotalTicket(waybillDetailArrageDtos.size());
					int totalCount=0;
					/**
					 * 已排单运单明细页面下方.总重量
					 */
					 BigDecimal totalWeight=BigDecimal.ZERO;
					/**
					 * 已排单运单明细页面下方.总体积
					 */
					 BigDecimal totalVolumn=BigDecimal.ZERO;
                     BigDecimal totalPayAmount=BigDecimal.ZERO;
					for (WaybillDetailBillArrageDto waybillDetailBillArrageDto : waybillDetailArrageDtos) {
						//已排件数
						if(waybillDetailBillArrageDto.getPreArrangeGoodsQty()!=null&& waybillDetailBillArrageDto.getPreArrangeGoodsQty()>0){
							totalCount+=waybillDetailBillArrageDto.getPreArrangeGoodsQty();
						}
						//已排体积
						if(waybillDetailBillArrageDto.getGoodsVolumeTotal()!=null&& waybillDetailBillArrageDto.getGoodsVolumeTotal().compareTo(BigDecimal.ZERO) > 0){
							totalVolumn=totalVolumn.add(waybillDetailBillArrageDto.getGoodsVolumeTotal());
						}
						//已排重量
						if(waybillDetailBillArrageDto.getWeight()!=null&& waybillDetailBillArrageDto.getWeight().compareTo(BigDecimal.ZERO) > 0){
							totalWeight=totalWeight.add(waybillDetailBillArrageDto.getWeight());
						}
                        //到付金额
                        if(waybillDetailBillArrageDto.getPayAmount()!=null&& waybillDetailBillArrageDto.getPayAmount().compareTo(BigDecimal.ZERO) > 0){
                        	totalPayAmount=totalPayAmount.add(waybillDetailBillArrageDto.getPayAmount());
                        }
						
					}
					waybillDeliverNewCountDto.setTotalCount(totalCount);
					waybillDeliverNewCountDto.setTotalWeight(totalWeight);
					waybillDeliverNewCountDto.setTotalVolumn(totalVolumn);
					waybillDeliverNewCountDto.setTotalPayAmount(totalPayAmount);
					
					
					if(vo.getWaybillDetailNewQueryDto()!=null && StringUtils.isNotBlank(vo.getWaybillDetailNewQueryDto().getVehicleNo())&& StringUtils.isNotBlank(vo.getWaybillDetailNewQueryDto().getDeliverDate())){
						WaybillDeliverNewCountDto dto=waybilldetailNewService.queryRightCount(waybillDeliverNewCountDto, vo.getWaybillDetailNewQueryDto(), vo.getDeliverbill().getId());
						//重新设置派送单里的排程相关信息(预计出车时间，出车任务，带货部门，转货部门，带货方数，班次)
						initDeliverInfoByStorage(dto, vo.getDeliverbill().getId() );
						vo.setWaybillDeliverNewCountDto(dto);
					}else{
						//重新设置派送单里的排程相关信息(预计出车时间，出车任务，带货部门，转货部门，带货方数，班次)
						initDeliverInfoByStorage(waybillDeliverNewCountDto, vo.getDeliverbill().getId() );
						this.vo.setWaybillDeliverNewCountDto(waybillDeliverNewCountDto);
					}
					this.vo.setWaybillDetailArrageDtos(waybillDetailArrageDtos);
				}else{
					if(vo.getWaybillDetailNewQueryDto()!=null && StringUtils.isNotBlank(vo.getWaybillDetailNewQueryDto().getVehicleNo())&& StringUtils.isNotBlank(vo.getWaybillDetailNewQueryDto().getDeliverDate())){
						WaybillDeliverNewCountDto waybillDeliverNewCountDto=new WaybillDeliverNewCountDto();
						waybillDeliverNewCountDto.setTotalWeight(BigDecimal.ZERO);
						waybillDeliverNewCountDto.setTotalVolumn(BigDecimal.ZERO);
						vo.setWaybillDeliverNewCountDto(waybillDeliverNewCountDto);
						WaybillDeliverNewCountDto dto=waybilldetailNewService.queryRightCount(vo.getWaybillDeliverNewCountDto(), vo.getWaybillDetailNewQueryDto(), vo.getDeliverbill().getId());
						//重新设置派送单里的排程相关信息(预计出车时间，出车任务，带货部门，转货部门，带货方数，班次)
						initDeliverInfoByStorage(dto, vo.getDeliverbill().getId() );
						vo.setWaybillDeliverNewCountDto(dto);
					} else {
						//重新设置派送单里的排程相关信息(预计出车时间，出车任务，带货部门，转货部门，带货方数，班次)
						WaybillDeliverNewCountDto waybillDeliverNewCountDto=new WaybillDeliverNewCountDto();
						initDeliverInfoByStorage(waybillDeliverNewCountDto, vo.getDeliverbill().getId() );
						vo.setWaybillDeliverNewCountDto(waybillDeliverNewCountDto);
					}
				}
			} else {
				this.vo.setWaybillDetailArrageDtos(null);
			}
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	/**
	 * 查询已排运单里的( 装载率(容积/体积): 额定净空(方)/额定载重(吨)) 
	 * @author 159231 meiying
	 * 2015-6-12  上午10:57:32
	 * @return
	 */
	@JSON
	public String queryWaybillDetailRightCount(){
		try {
			if(vo.getWaybillDetailNewQueryDto()!=null && StringUtils.isNotBlank(vo.getWaybillDetailNewQueryDto().getVehicleNo())&& StringUtils.isNotBlank(vo.getWaybillDetailNewQueryDto().getDeliverDate())){
				Long count = this.visibleOrderService.visibleVehilceNoExistDeliverEntity(vo.getWaybillDetailNewQueryDto().getDeliverDate(), 
						vo.getWaybillDetailNewQueryDto().getVehicleNo(),  this.vo.getDeliverbill().getId(), DeliverbillConstants.STATUS_SAVED, null);
				if (null != count && count.intValue() > 0) {
					return super.returnError("此车"+vo.getWaybillDetailNewQueryDto().getDeliverDate()+"已有已保存状态的派送单，不能再选择!");
				}else{
					WaybillDeliverNewCountDto dto=waybilldetailNewService.queryRightCount(vo.getWaybillDeliverNewCountDto(), vo.getWaybillDetailNewQueryDto(), this.vo.getDeliverbill().getId());
					vo.setWaybillDeliverNewCountDto(dto);	
				}
				
			}else{
				this.vo.setWaybillDeliverNewCountDto(null);
			}
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	/**
	 * 编辑/新增预派送单入口.
	 * @author 159231 meiying
	 * 2015-6-17  下午6:35:05
	 * @return
	 */
	@JSON
	public String editDeliverbillNewIndex() {
		try {
			//若派送单id为空
			if (this.vo.getDeliverbill().getId() == null|| "".equals(this.vo.getDeliverbill().getId())) {
				this.vo.getDeliverbill().setId("");

				// SUC-447 –创建预派送单 SR6 预派送单号生成规则为：p+序列号（数据库生成，保证唯一）。如p00000001。
				// 生成P+9位编号
				this.vo.getDeliverbill().setDeliverbillNo("P" + deliverbillService.querySequence());
			}
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}	

	/**
	 * 根据小区查询车牌号
	 * @author 159231 meiying
	 * 2015-6-17  下午6:34:59
	 * @return
	 */
	@JSON
	public String queryVehicleNoByRegionCode() {
		try {
			//若派送单id为空
			if (this.vo.getDeliverHandoverbillEntity()!= null && StringUtils.isNotBlank(vo.getDeliverHandoverbillEntity().getActualSmallzoneCode()) ) {
				vo.setActualVehicleNo(regionalVehicleService.getRegionVehicleNoBySmallZoneCode(vo.getDeliverHandoverbillEntity().getActualSmallzoneCode()));
			}
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	/**
	 * 删除地址坐标
	 * @author 159231 meiying
	 * 2015-7-3  下午5:26:52
	 * @return
	 */
	
	@JSON
	public String waybilldetailDeleteAddress() {
		try {
			//若派送单id为空
			if (this.handoverBillVo!= null && this.handoverBillVo.getWaybillNo()!=null) {
				deleteGisAddressService.deleteAddress(handoverBillVo);
			}
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	/**
	 * 批量删除地址坐标
	 */
	@JSON
	public String waybilldetailBatchDeleteAddress() {
		try {
			if(!CollectionUtils.isEmpty(this.handoverBillVos)){
				for(HandoverBillVo vo : this.handoverBillVos){
					if(vo != null && StringUtils.isNotBlank(vo.getWaybillNo())){
						deleteGisAddressService.deleteAddress(vo);
					}
				}
			}
			
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	
	/**
	 * 根据派送单信息初始化车辆相关信息
	 * @param waybillDeliverNewCountDto
	 */
	private void initDeliverInfoByStorage(WaybillDeliverNewCountDto waybillDeliverNewCountDto, String deliverbillId) {
		//获取派送单里的排程相关信息(预计出车时间，出车任务，带货部门，转货部门，带货方数，班次)
		DeliverbillEntity deliverbillEntity = waybilldetailNewService.queryDeliverbillById(deliverbillId);
		if (null != deliverbillEntity) {
			if (StringUtils.isNotBlank(deliverbillEntity.getCarTaskinfo())) {
				waybillDeliverNewCountDto.setCarTaskinfo(deliverbillEntity.getCarTaskinfo());
			}
			if (null != deliverbillEntity.getFrequencyno()) {
				waybillDeliverNewCountDto.setFrequencyNo(deliverbillEntity.getFrequencyno().toString());
			}
			if (StringUtils.isNotBlank(deliverbillEntity.getPreCartaskTime())) {
				waybillDeliverNewCountDto.setPreCartaskTime(deliverbillEntity.getPreCartaskTime());
			}
			if (null != deliverbillEntity.getExpectedbringvolume() && deliverbillEntity.getExpectedbringvolume().intValue() != 0) {
				waybillDeliverNewCountDto.setExpectedBringVolume(deliverbillEntity.getExpectedbringvolume());
			}
			if (StringUtils.isNotBlank(deliverbillEntity.getTakeGoodsDeptcode())) {
				waybillDeliverNewCountDto.setTakeGoodsDeptcode(deliverbillEntity.getTakeGoodsDeptcode());
				waybillDeliverNewCountDto.setTakeGoodsDeptname(deliverbillEntity.getTakeGoodsDeptname());
			}
			if (StringUtils.isNotBlank(deliverbillEntity.getTransferDeptcode())) {
				waybillDeliverNewCountDto.setTransferDeptcode(deliverbillEntity.getTransferDeptcode());
				waybillDeliverNewCountDto.setTransferDeptname(deliverbillEntity.getTransferDeptname());
			}
		}
	}
	
	/**
	 * 查询收货联系人
	 */
	@JSON
	public String queryReceiveCustomerContact(){
		try {
			if(waybillNo != null){
				//根据运单号查询收货联系人
				vo.setReceiveCustomerContact(waybilldetailNewService.queryReceiveCustomerContactByWaybillNo(waybillNo));
			}
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	/**
	 * 设置waybilldetailNewService  
	 * @param waybilldetailNewService waybilldetailNewService 
	 */
	public void setWaybilldetailNewService(
			IWaybilldetailNewService waybilldetailNewService) {
		this.waybilldetailNewService = waybilldetailNewService;
	}

	/**
	 * 获取vo  
	 * @return vo vo
	 */
	public WaybilldetailNewVo getVo() {
		return vo;
	}

	/**
	 * 设置vo  
	 * @param vo vo 
	 */
	public void setVo(WaybilldetailNewVo vo) {
		this.vo = vo;
	}
	/**
	 * 设置deliverHandoverbillService  
	 * @param deliverHandoverbillService deliverHandoverbillService 
	 */
	public void setDeliverHandoverbillService(
			IDeliverHandoverbillService deliverHandoverbillService) {
		this.deliverHandoverbillService = deliverHandoverbillService;
	}
	/**
	 * 设置deliverbillService  
	 * @param deliverbillService deliverbillService 
	 */
	public void setDeliverbillService(IDeliverbillService deliverbillService) {
		this.deliverbillService = deliverbillService;
	}
	/**
	 * 设置businessLockService  
	 * @param businessLockService businessLockService 
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	/**
	 * 设置visibleOrderService  
	 * @param visibleOrderService visibleOrderService 
	 */
	public void setVisibleOrderService(IVisibleOrderService visibleOrderService) {
		this.visibleOrderService = visibleOrderService;
	}
	/**
	 * 设置truckSchedulingTaskService  
	 * @param truckSchedulingTaskService truckSchedulingTaskService 
	 */
	public void setTruckSchedulingTaskService(
			ITruckSchedulingTaskService truckSchedulingTaskService) {
		this.truckSchedulingTaskService = truckSchedulingTaskService;
	}
	/**
	 * 设置regionalVehicleService  
	 * @param regionalVehicleService regionalVehicleService 
	 */
	public void setRegionalVehicleService(
			IRegionalVehicleService regionalVehicleService) {
		this.regionalVehicleService = regionalVehicleService;
	}
	/**
	 * 获取handoverBillVo  
	 * @return handoverBillVo handoverBillVo
	 */
	public HandoverBillVo getHandoverBillVo() {
		return handoverBillVo;
	}
	/**
	 * 设置handoverBillVo  
	 * @param handoverBillVo handoverBillVo 
	 */
	public void setHandoverBillVo(HandoverBillVo handoverBillVo) {
		this.handoverBillVo = handoverBillVo;
	}
	
	/**
	 * 获取handoverBillVos
	 */
	public List<HandoverBillVo> getHandoverBillVos() {
		return handoverBillVos;
	}
	/**
	 * 设置handoverBillVos
	 * @param handoverBillVos
	 */
	public void setHandoverBillVos(List<HandoverBillVo> handoverBillVos) {
		this.handoverBillVos = handoverBillVos;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public void setDeliverLoadGapReportService(
			IDeliverLoadGapReportService deliverLoadGapReportService) {
		this.deliverLoadGapReportService = deliverLoadGapReportService;
	}

	
	
}
