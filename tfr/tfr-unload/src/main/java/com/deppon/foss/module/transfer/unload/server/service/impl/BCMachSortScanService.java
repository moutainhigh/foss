package com.deppon.foss.module.transfer.unload.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrToQmsErrorService;
import com.deppon.foss.module.transfer.common.api.server.service.IWaybillFromMachineService;
import com.deppon.foss.module.transfer.common.api.shared.domain.AutoOverWeightToQMSMainEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.WaybillFromMachineEntity;
import com.deppon.foss.module.transfer.unload.api.server.dao.IBCMachSortScanDao;
import com.deppon.foss.module.transfer.unload.api.server.service.IBCMachSortScanService;
import com.deppon.foss.module.transfer.common.api.shared.domain.AutoOverWeightToQMSEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.RequestParamEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.BCMachSortScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.BCMachWaybillSortScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.vo.BCMachSortScanVo;




/**
* @description 计泡机服务类
* @version 1.0
* @author 105869-foss-heyongdong
* @update 2015年5月7日 下午2:43:05
*/
public class BCMachSortScanService implements IBCMachSortScanService {
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private IBCMachSortScanDao bCMachSortScanDao;
	
	
	public void setbCMachSortScanDao(IBCMachSortScanDao bCMachSortScanDao) {
		this.bCMachSortScanDao = bCMachSortScanDao;
	}
	//获取待处理的数据
	private IWaybillFromMachineService waybillFromMachineService;
	public void setWaybillFromMachineService(
			IWaybillFromMachineService waybillFromMachineService) {
		this.waybillFromMachineService = waybillFromMachineService;
	}
	//获取运单的所有信息
	private IWaybillDao waybillDao;
	
	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}
	
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	//掉接送货的接口获取运单的信息
	public IWaybillManagerService waybillManagerService;
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	//接口执行自动上报QMS的接口
	private ITfrToQmsErrorService tfrToQmsErrorService;
	public void setTfrToQmsErrorService(ITfrToQmsErrorService tfrToQmsErrorService) {
		this.tfrToQmsErrorService = tfrToQmsErrorService;
	}
	//获取配置参数
	private IDataDictionaryValueService dataDictionaryValueService;
	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}



	/**
	* @description 计泡机查询称重量方
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.unload.api.server.service.IBCMachSortScanService#queryBCMachSortScan(com.deppon.foss.module.transfer.unload.api.shared.vo.BCMachSortScanVo)
	* @author 105869-foss-heyongdong
	* @update 2015年5月7日 下午2:42:47
	* @version V1.0
	*/
	@Override
	public List<BCMachSortScanEntity> queryBCMachSortScan(BCMachSortScanVo vo,int i, int j) {
		
		return bCMachSortScanDao.queryBCMachSortScan(vo,i,j);
	}


	
	/**
	* @description 计泡机查询称重量方数据总条数
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.unload.api.server.service.IBCMachSortScanService#queryBCMachSortScanCount(com.deppon.foss.module.transfer.unload.api.shared.vo.BCMachSortScanVo)
	* @author 105869-foss-heyongdong
	* @update 2015年5月7日 下午3:53:36
	* @version V1.0
	*/
	@Override
	public Long queryBCMachSortScanCount(BCMachSortScanVo vo) {
	
		return bCMachSortScanDao.queryBCMachSortScanCount(vo);
	}


	@Override
	public int saveScanMsg(BCMachSortScanEntity entity) {
		
		return bCMachSortScanDao.saveScanMsg(entity);
	}


	
	/**
	* @description 查询计泡机分拣信息
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.unload.api.server.service.IBCMachSortScanService#queryBCMachSortScanBySeriaNo(java.lang.String, java.lang.String)
	* @author 105869-foss-heyongdong
	* @update 2015年5月19日 下午4:41:18
	* @version V1.0
	*/
	@Override
	public BCMachSortScanEntity queryBCMachSortScanBySeriaNo(String waybillNo,
			String serialNo) {
		
		return bCMachSortScanDao.queryBCMachSortScanBySeriaNo(waybillNo,serialNo);
	}


	
	/**
	* @description 删除运单计泡机信息
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.unload.api.server.service.IBCMachSortScanService#deleteScanMsg(com.deppon.foss.module.transfer.unload.api.shared.domain.BCMachSortScanEntity)
	* @author 105869-foss-heyongdong
	* @update 2015年5月19日 下午4:41:22
	* @version V1.0
	*/
	@Override
	public long deleteScanMsg(BCMachSortScanEntity sortEntity) {
		return bCMachSortScanDao.deleteScanMsg(sortEntity);
	}


	
	/**
	* @description 保存上计泡机日志
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.unload.api.server.service.IBCMachSortScanService#saveScanMsgLog(com.deppon.foss.module.transfer.unload.api.shared.domain.BCMachSortScanEntity)
	* @author 105869-foss-heyongdong
	* @update 2015年5月25日 下午8:22:09
	* @version V1.0
	*/
	@Override
	public int saveScanMsgLog(BCMachSortScanEntity entity) {
		
		return bCMachSortScanDao.saveScanMsgLog(entity);
	}


	
	/**
	* @description 更新或者插入 上计泡机运单信息综合表
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.unload.api.server.service.IBCMachSortScanService#updateOrInsertWaybillMsg(java.lang.String)
	* @author 105869-foss-heyongdong
	* @update 2015年5月25日 下午8:22:12
	* @version V1.0
	*/
	@Override
	public int updateOrInsertWaybillMsg(String waybillNo) {
		return bCMachSortScanDao.updateOrInsertWaybillMsg(waybillNo);
	}
	
	/**
	* @description 将计泡机传过来的数据获取超方超重的自动上报到QMS
	* @param 开始时间，结束时间，线程号，线程数
	*
	* @version 1.0
	* @author 268084-foss-renchao
	* @update 2015年12月12日 下午7:42:25
	*/
	@SuppressWarnings("null")
	@Override
	public void executeoverWeightToQMS(Date jobStartTime,Date jobEndTime){
		try {
			LOGGER.info("查询数据字典中的配置信息开始....");
			List<DataDictionaryValueEntity> paramList = dataDictionaryValueService.queryDataDictionaryValueByTermsCode(DictionaryConstants.TFR_PARAM_OVERVOLUMNANDWEIGHT_TOQMS_CONDITION);
			if(paramList==null){
				throw new Exception("超方超重参数为空:TFR_PARAM_OVERVOLUMNANDWEIGHT_TOQMS_CONDITION");
			}
			if(paramList.size()<2){
				throw new Exception("未配置超方超重参数:TFR_PARAM_OVERVOLUMNANDWEIGHT_TOQMS_CONDITION");
			}
			LOGGER.info("查询数据字典中的配置信息结束....");
			String overVolumn = "";
			String overWeight = "";
			for(DataDictionaryValueEntity ddv:paramList){
				if(ddv.getValueCode().equals("OVERVOLUMN")){
					overVolumn=ddv.getValueName();
				}
				if(ddv.getValueCode().equals("OVERWEIGHT")){
					overWeight=ddv.getValueName();
				}
			}
			//将配置参数中的符合超方超重的条件取出来
			double fixedOverVolumn=Double.parseDouble(overVolumn);
			double fixedOverWeight=Double.parseDouble(overWeight);
			LOGGER.info("数据字典配置的参数为:"+"重量体积比:"+fixedOverVolumn+";超重参数为:"+fixedOverWeight);
			//从WaybillFromMachine表中获取需要处理的数据
			LOGGER.info("从WaybillFromMachine表中获取需要处理的数据开始....");
			Map<String, String> map = new HashMap<String, String>();
			map.put("status", "DOING");
			List<WaybillFromMachineEntity> jobToDoList=waybillFromMachineService.findByCondition(map);
			LOGGER.info("从WaybillFromMachine表中获取需要处理的数据结束....");
			if(jobToDoList!=null&&jobToDoList.size()>0){
				for(WaybillFromMachineEntity toDoEntity:jobToDoList){
					LOGGER.info("查询运单的实际信息开始(包括发更改之后的).....");
					WaybillEntity waybillEntity = waybillDao.queryWaybillByNo(toDoEntity.getWaybillNo());//获取运单信息
					LOGGER.info("查询运单的实际信息结束(包括发更改之后的).....");
					//如果运单信息存在重量和体积则执行
					if(waybillEntity!=null&&waybillEntity.getGoodsVolumeTotal()!=null&&waybillEntity.getGoodsWeightTotal()!=null){
						double billVollumn = waybillEntity.getGoodsVolumeTotal().doubleValue();
						double billWeight = waybillEntity.getGoodsWeightTotal().doubleValue();
						if(Double.valueOf(0).compareTo(billVollumn) == 0|| Double.valueOf(0).compareTo(billWeight) == 0){
							//如果体积(除数)为零则在WaybillFromMachine表中删除此条记录，进行下次循环
							LOGGER.info("删除体积或重量为空的运单信息开始....");
							waybillFromMachineService.deleteByWaybillNo(toDoEntity.getWaybillNo());
							LOGGER.info("删除体积或重量为空的运单信息结束....");
							continue;
						}
						if(billWeight/billVollumn>=fixedOverVolumn){//开单重货
							//从计泡机运单总信息中查询一条数据
							LOGGER.info("从计泡机测重的数据表中查询一条信息开始...");
							BCMachWaybillSortScanEntity entity=bCMachSortScanDao.queryOneBillFromBCMachine(toDoEntity.getWaybillNo());
							LOGGER.info("从计泡机测重的数据表中查询一条信息结束...");
							if(entity!=null&&entity.getWeight()!=null){
								if((entity.getWeight().doubleValue()-billWeight)>fixedOverWeight){//如果计泡机重量减去开单重量大于固定值
									//推送信息给qms(掉接送货的几口将数据传输给QMS)
									pushOverWeightToQMS(waybillEntity, entity);
								}else{
									//如果不符合则更新信息
									WaybillFromMachineEntity wfmEntity = new WaybillFromMachineEntity();
									wfmEntity.setFinishTime(new Date());
									wfmEntity.setIsOverWeight("N");
									wfmEntity.setStatus("DONE");
									wfmEntity.setWaybillNo(toDoEntity.getWaybillNo());
									waybillFromMachineService.updateByWayBillNo(wfmEntity);
								}
							}
						}else{//开单泡货
							//从计泡机运单总信息中查询一条数据
							BCMachWaybillSortScanEntity entity=bCMachSortScanDao.queryOneBillFromBCMachine(toDoEntity.getWaybillNo());
							if(entity!=null&&entity.getWeight()!=null){
								if((entity.getWeight().doubleValue()-billVollumn*fixedOverVolumn)>fixedOverWeight){//若计泡机测重-计费重量(开单体积*1000/6)>1kg
									//推送信息给qms(掉接送货的几口将数据传输给QMS)
									pushOverWeightToQMS(waybillEntity, entity);
								}else{
									//如果不符合则更新信息
									WaybillFromMachineEntity wfmEntity = new WaybillFromMachineEntity();
									wfmEntity.setFinishTime(new Date());
									wfmEntity.setIsOverWeight("N");
									wfmEntity.setStatus("DONE");
									wfmEntity.setWaybillNo(toDoEntity.getWaybillNo());
									waybillFromMachineService.updateByWayBillNo(wfmEntity);
								}
							}
						}
					}else{
						//如果运单表信息中不存在重量和体积，在WaybillFromMachine表中将此信息删掉
						waybillFromMachineService.deleteByWaybillNo(toDoEntity.getWaybillNo());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 
	 * 推送超方超重的信息给qms
	 * @param waybillEntity
	 * @param entity
	 */
	private void pushOverWeightToQMS(WaybillEntity waybillEntity,BCMachWaybillSortScanEntity entity) {
		AutoOverWeightToQMSEntity atQMS = new AutoOverWeightToQMSEntity();
		atQMS.setConsignor(waybillEntity.getDeliveryCustomerName());// 托运人
		atQMS.setConsignee(waybillEntity.getReceiveCustomerName());// 收货人
		atQMS.setReceiveDeptName(waybillEntity.getReceiveOrgName());// 收货部门
		atQMS.setArriveDeptName(waybillEntity.getCustomerPickupOrgName());// 到达部门
		atQMS.setRespEmpCode(waybillEntity.getModifyUserCode());// 责任人编号
		atQMS.setRespEmpName(waybillEntity.getModifyUser());// 责任人名字
		try {
			if (waybillEntity.getModifyOrgCode() != null
					&& StringUtils.isNotBlank(waybillEntity.getModifyOrgCode())) {
				LOGGER.info("根据责任人部门编号查找责任人部门名称开始...");
				OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCodeClean(waybillEntity
								.getModifyOrgCode());
				if (org != null) {
					atQMS.setRespDeptCode(org.getUnifiedCode());//责任部门标杆
					atQMS.setRespDeptName(org.getName());// 责任部门
					LOGGER.info("根据责任人部门编号查找责任人部门名称结束...");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 查询责任事业部
		List<String> orgTypeLst = new ArrayList<String>();
		orgTypeLst.add(BizTypeConstants.ORG_DIVISION); // 事业部类型
		try {
			if (waybillEntity.getModifyOrgCode() != null
					&& StringUtils.isNotBlank(waybillEntity.getModifyOrgCode())) {
				LOGGER.info("根据责任部门向上查找责事业部信息开始....");
				OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoComplexService
						.queryOrgAdministrativeInfoByCode(
								waybillEntity.getModifyOrgCode(), orgTypeLst);
				if (orgEntity != null) {
					atQMS.setRespDivisionCode(orgEntity.getUnifiedCode());// 事业部标杆编码
					atQMS.setRespDivisionName(orgEntity.getName());// 事业部名称
					LOGGER.info("根据责任部门向上查找责事业部信息结束....");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//获取开单部门
		try {
			if (waybillEntity.getCreateOrgCode() != null
					&& StringUtils.isNotBlank(waybillEntity.getCreateOrgCode())) {
					LOGGER.info("根据开单人部门编号查找开单人部门名称开始...");
				OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCodeClean(waybillEntity
								.getCreateOrgCode());
				if (org != null) {
					atQMS.setBillingDeptCode(org.getUnifiedCode());//开单部门标杆编码
					atQMS.setBillingDeptName(org.getName());// 开单部门名称
					LOGGER.info("根据开单人部门编号查找开单人部门名称结束...");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		atQMS.setGoodsName(waybillEntity.getGoodsName());// 货物名称
		atQMS.setWeight(waybillEntity.getGoodsWeightTotal());// 货物重量
		atQMS.setVolume(waybillEntity.getGoodsVolumeTotal());// 货物体积
		atQMS.setActualWeight(entity.getWeight());
		//封装实体传输给QMS
		AutoOverWeightToQMSMainEntity mainInfo = new AutoOverWeightToQMSMainEntity();
		mainInfo.setWayBillNum(waybillEntity.getWaybillNo());//运单号在主表里面
		mainInfo.setErrorCategory("K");
		mainInfo.setErrorTypeId("K201503250014");
		mainInfo.setDocStandarId("3464");
		mainInfo.setRepTime(new Date());
		try {//设置收货人部门的标杆编码
			if (waybillEntity.getReceiveOrgCode() != null
					&& StringUtils.isNotBlank(waybillEntity.getReceiveOrgCode())) {
				LOGGER.info("根据收货部门编号查找责事业部信息开始....");
				OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCodeClean(waybillEntity
								.getReceiveOrgCode());
				if (org != null) {
					mainInfo.setReceiveDeptCode(org.getUnifiedCode());// 收货部门的标杆编码
					LOGGER.info("根据责任部门查找责事业部信息结束....");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		RequestParamEntity rpEntity=new RequestParamEntity();
		rpEntity.setErrCategoty("K");
		rpEntity.setErrorTypeId("K201503250014");
		rpEntity.setReturnResult(true);
		rpEntity.setMainInfo(mainInfo);
		rpEntity.setKdsubHasInfo(atQMS);
		LOGGER.info("开始执行上报QMS.......");
		String responseStr = tfrToQmsErrorService.reportQmsOverWeight(rpEntity);//上报到QMS
		LOGGER.info("上报QMS后返回的数据是:"+responseStr);
		if(responseStr!=null&&StringUtils.isNotBlank(responseStr)){
			//如果成功推送将更新这条信息
			WaybillFromMachineEntity wfmEntity = new WaybillFromMachineEntity();
			wfmEntity.setFinishTime(new Date());
			wfmEntity.setIsOverWeight("Y");
			wfmEntity.setStatus("DONE");
			wfmEntity.setWaybillNo(waybillEntity.getWaybillNo());
			LOGGER.info("开始更新上报完成后的数据.....");
			waybillFromMachineService.updateByWayBillNo(wfmEntity);
			LOGGER.info("结束更新上报完成后的数据.....");
		}
	}
}
