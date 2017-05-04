package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressVehicleDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyDetailsDao;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyDetailsService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyDetailsConditonDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyDetailsDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * @功能 查询营业部派送、自提通知记录;支持导出功能
 * @author Foss-105888-Zhangxingwang
 * @date 2013-12-27 19:26:51
 *
 */
public class NotifyDetailsService implements INotifyDetailsService {
	private static final int NUMBER_20000 = 20000;

	/**
	 * 外场相关共通接口
	 */
	private IHandleQueryOutfieldService handleQueryOutfieldService;
	
	/**
	 * 注入通知客户DAO
	 */
	private INotifyDetailsDao notifyDetailsDao;
	
	/**
	 * 快递车牌查询公共选择器Service
	 */
	private ICommonExpressVehicleService commonExpressVehicleService;
	
	/** 
	 * “公司司机”的数据库对应数据访问Service接口
	 */
	private IOwnDriverService ownDriverService;
	
	/** 
	 * “外请车司机”的数据库对应数据访问
	 */
	private ILeasedDriverService leasedDriverService;
	
	
	public void setCommonExpressVehicleService(ICommonExpressVehicleService commonExpressVehicleService) {
		this.commonExpressVehicleService = commonExpressVehicleService;
	}

	public void setOwnDriverService(IOwnDriverService ownDriverService) {
		this.ownDriverService = ownDriverService;
	}

	public void setLeasedDriverService(ILeasedDriverService leasedDriverService) {
		this.leasedDriverService = leasedDriverService;
	}

	public void setHandleQueryOutfieldService(
			IHandleQueryOutfieldService handleQueryOutfieldService) {
		this.handleQueryOutfieldService = handleQueryOutfieldService;
	}
	
	public void setNotifyDetailsDao(INotifyDetailsDao notifyDetailsDao) {
		this.notifyDetailsDao = notifyDetailsDao;
	}
	/**
	 * @功能 查询数量
	 * @date 2013-12-27 19:28:37
	 * @author Foss-105888-Zhangxingwang
	 * @param vo
	 * @return
	 */
	@Override
	public long queryNoticeDetailCount(NotifyDetailsConditonDto notifyDetailsConditonDto) {
		return notifyDetailsDao.queryNoticeDetailCount(notifyDetailsConditonDto);
	}

	/**
	 * @功能 查询通知记录详情
	 * @date 2013-12-27 19:28:37
	 * @author Foss-105888-Zhangxingwang
	 * @param vo yuting
	 * @return
	 */
	public List<NotifyDetailsDto> queryNoticeDetail(NotifyDetailsConditonDto notifyDetailsConditonDto, int start, int limit) {
		List<NotifyDetailsDto> list = notifyDetailsDao.queryNoticeDetail(notifyDetailsConditonDto, start, limit);
		if(CollectionUtils.isNotEmpty(list)){
			NotifyDetailsDto dto = null;
			for(int i=0;i<list.size();i++){
				dto = list.get(i);
				dto.setDelivercustomerAddr(handleQueryOutfieldService.getCompleteAddressAttachAddrNote(dto.getReceiveCustomerProvCode(), dto.getReceiveCustomerCityCode(), dto.getReceiveCustomerDistCode(), dto.getReceiveCustomerAddress(), dto.getReceiveCustomerAddressNote()));
				//新增根据司机工号、车牌号查询司机信息 DMANA-3694
				this.queryDriverInfo(dto);
			}
		}
		return list;
	}
	
	
	/**
	 * 根据司机工号、车牌号查询司机信息
	 * @Title: queryDriverInfo 
	 * @author 200664-yangjinheng
	 * @date 2014年9月19日 上午8:37:38
	 * @throws
	 */
	private void queryDriverInfo(NotifyDetailsDto dto) {
		if (StringUtils.equals(dto.getIsExpress(), FossConstants.YES) && (StringUtils.isNotEmpty(dto.getDriverCode()) || StringUtils.isNotEmpty(dto.getVehicleNo()))) {
			// 如果是小件派送单,并且司机工号或者司机车牌号不为空
			ExpressVehicleDto expressVeh = new ExpressVehicleDto();
			// 司机工号
			expressVeh.setEmpCode(dto.getDriverCode());
			// 车牌号
			expressVeh.setVehicleNo(dto.getVehicleNo());
			List<ExpressVehicleDto> expressVehicleDtos = commonExpressVehicleService.queryExpressVehicleNoListBySelectiveCondition(expressVeh, NumberConstants.NUMBER_10,
					NumberConstants.ZERO);
			if (CollectionUtils.isNotEmpty(expressVehicleDtos)) {
				// 司机电话
				dto.setDriverPhone(expressVehicleDtos.get(0).getMobilePhone());
			}
		} else {
			// 零担订单
			// 若司机工号不为空
			if (StringUtil.isNotEmpty(dto.getDriverCode())) {
				// 内部司机根据工号查询相关信息
				DriverAssociationDto driverAssociationDto = ownDriverService.queryOwnDriverAssociationDtoByDriverCode(dto.getDriverCode());
				// 用来交互“司机（公司、外请）”的数据实体相关联信息的DTO不为空
				if (driverAssociationDto != null) {
					// 司机电话
					dto.setDriverPhone(driverAssociationDto.getDriverPhone());
				} else {
					if (StringUtil.isNotEmpty(dto.getVehicleNo())) {

						// 外请司机根据车牌号进行查询
						List<DriverAssociationDto> driverAssociationDtos = leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(dto.getVehicleNo());

						if (CollectionUtils.isNotEmpty(driverAssociationDtos)) {
							// 司机电话
							dto.setDriverPhone(driverAssociationDtos.get(0).getDriverPhone());
						}
					}
				}
				// 如果车牌号不为空
			} else if (StringUtil.isNotEmpty(dto.getVehicleNo())) {
				// 外请司机根据车牌号进行查询
				List<DriverAssociationDto> driverAssociationDtos = leasedDriverService.queryLesasedDriverAssociationDtoByTruckVehicleNo(dto.getVehicleNo());

				if (CollectionUtils.isNotEmpty(driverAssociationDtos)) {
					// 司机电话
					dto.setDriverPhone(driverAssociationDtos.get(0).getDriverPhone());
				}
			}
		}
	}
	
	/**
	 * @功能 初始化查询条件
	 * @date 2013-12-27 19:28:37
	 * @author Foss-105888-Zhangxingwang
	 * @param vo
	 * @return
	 */
	public  void initNotifyDetailsConditonDto(
			NotifyDetailsConditonDto notifyDetailsConditonDto) {
		//运单不为空，则进行对应的拆分
		if(StringUtils.isNotBlank(notifyDetailsConditonDto.getWaybillNo())){
			notifyDetailsConditonDto.setWaybillNoList(notifyDetailsConditonDto.getWaybillNo().split("\\n"));
		}
		notifyDetailsConditonDto.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY);
		String currOrgCode = FossUserContextHelper.getOrgCode();
		notifyDetailsConditonDto.setLastLoadOrgCode(currOrgCode);
		List<String> list = handleQueryOutfieldService.getEndStockCodeAndExpressAreaCode(currOrgCode);
		if (CollectionUtils.isNotEmpty(list)) {
			List<String> ld=new ArrayList<String>();
			ld.add(list.get(1));
			ld.add(list.get(2));
			notifyDetailsConditonDto.setEndStockOrgCode(list.get(0));
			notifyDetailsConditonDto.setGoodsAreaCodes(ld);
		}
		
	}
	
	/**
	 * @功能 导出通知记录详情
	 * @date 2013-12-27 19:28:37
	 * @author Foss-105888-Zhangxingwang
	 * @param vo
	 * @return
	 */
	@Override
	public InputStream exportNoticeDetail(NotifyDetailsConditonDto dto) {
		List<NotifyDetailsDto> resultList = new ArrayList<NotifyDetailsDto> ();
		//update
		dto=getNotifyDetailsConditonDto(dto);
		if (dto != null) {
			// 如果行政区域未null，直接返回空
			resultList = this.queryNoticeDetail(dto, 0, 0);//dispatchVehicleRecordEntityDao.queryExpressVehicleRecordBy(dto, NumberConstants.ZERO, NUMBER);
		}
		// 获取所有需查询部门
		List<List<String>> rowList = new ArrayList<List<String>>();
		if(CollectionUtils.isNotEmpty(resultList)){
			// 列头 运单号、通知状态、开单提货方式、库存状态、入库时间、预计送货日期、收货人、收货人地址、收货人电话、收货人手机、件数、重量、体积、到付金额、提前通知、创建人、创建时间
			for(NotifyDetailsDto row : resultList){
				List<String> columnList = new ArrayList<String>();
				// 运单号
				columnList.add(row.getWaybillNo());
				// 通知状态
				if("SUCCESS".equals(row.getNoticeStatus())){
					columnList.add("通知成功");
				}else{
					columnList.add("通知失败");
				}
				// 开单提货方式
				columnList.add(DictUtil.rendererSubmitToDisplay(row.getDeliverMethod(), DictionaryConstants.PICKUP_GOODS));
				// 库存状态
				if(FossConstants.YES.equals(row.getStockStatus())){
					columnList.add("库存中");
				}else{
					columnList.add("非本部门库存");
				}
				// 入库时间
				columnList.add(DateUtils.convert(row.getInStockTime(), DateUtils.DATE_TIME_FORMAT));
				// 预计送货时间
				columnList.add(DateUtils.convert(row.getPlanToSendTime(), DateUtils.DATE_FORMAT));
				// 收货人
				columnList.add(row.getReceivecustomerName());
				// 收货人地址
				columnList.add(row.getDelivercustomerAddr());
				// 收货人电话
				columnList.add(row.getCustomerTelePhone());
				// 收货人手机
				columnList.add(row.getCustomerMobile());
				// 总件数
				columnList.add(row.getGoodsTotal().toString());
//				// 重量
				columnList.add(row.getWeight().toString());
				// 体积
				columnList.add(row.getVolume().toString());
				// 到付金额
				columnList.add(row.getArriveFee().toString());
				//提前通知
				columnList.add(row.getDeliverRequire());
				
				// DMANA-3694 增加6个字段：到达时间、送货司机、司机手机、签收时间、出发部门、到达部门
				// 到达时间
				columnList.add(DateUtils.convert(row.getArriveTime(), DateUtils.DATE_TIME_FORMAT));
				// 送货司机
				columnList.add(row.getDriverName());
				// 司机手机
				columnList.add(row.getDriverPhone());
				// 签收时间
				columnList.add(DateUtils.convert(row.getSignTime(), DateUtils.DATE_TIME_FORMAT));
				// 出发部门
				columnList.add(row.getReceiveOrgName());
				// 到达部门
				columnList.add(row.getCustomerPickupOrgName());
				
				// 创建人
				columnList.add(row.getCreatorName().toString());
				
				// 创建时间
				columnList.add(DateUtils.convert(row.getCreateTime(), DateUtils.DATE_TIME_FORMAT));
				
				
				rowList.add(columnList);
			}
		}
		
		// 列头运单号、通知状态、开单提货方式、库存状态、入库时间、预计送货日期、收货人、收货人地址、收货人电话、收货人手机、件数、重量、体积、到付金额、提前通知、到达时间、送货司机、司机手机、签收时间、出发部门、到达部门、创建人、创建时间
		String[] rowHeads = {"运单号","通知状态","开单提货方式","库存状态","入库时间","预计送货日期","收货人","收货人地址","收货人电话","收货人手机","件数","重量","体积","到付金额","提前通知","到达时间","送货司机","司机手机","签收时间","出发部门","到达部门","创建人","创建时间"};
		
		ExportResource exportResource = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("查询通知明细");
		exportSetting.setSize(NUMBER_20000);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		return objExporterExecutor.exportSync(exportResource, exportSetting);
	}

	/***
	 * 去除参数空格  完成数据查询
	 * @author yuting
	 * @time 2014-07-29
	 * @param conditionDto NotifyDetailsConditonDto
	 * @return NotifyDetailsConditonDto
	 */
	private NotifyDetailsConditonDto getNotifyDetailsConditonDto(
			NotifyDetailsConditonDto dto) {
		String[] waybillNoList = dto.getWaybillNoList();
		if(ArrayUtils.isNotEmpty(waybillNoList)){
			String[] rstArray=new String[waybillNoList.length];
			for (int i = 0; i < waybillNoList.length; i++) {
				String waybillNo=waybillNoList[i].trim();
				rstArray[i]=waybillNo;
			}
			dto.setWaybillNoList(rstArray);
		}
		return dto;
	}
}
