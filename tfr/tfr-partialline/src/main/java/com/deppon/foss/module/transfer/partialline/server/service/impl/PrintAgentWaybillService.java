package com.deppon.foss.module.transfer.partialline.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.trackings.api.server.service.IPushTrackForCaiNiaoService;
import com.deppon.foss.module.trackings.api.server.service.IWaybillTrackingsService;
import com.deppon.foss.module.trackings.api.shared.define.WaybillTrackingsConstants;
import com.deppon.foss.module.trackings.api.shared.domain.SynTrackingEntity;
import com.deppon.foss.module.trackings.api.shared.dto.WaybillTrackingsDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.AgentWaybillNoRequestDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadService;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity;
import com.deppon.foss.module.transfer.partialline.api.server.dao.IPrintAgentWaybillDao;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillService;
import com.deppon.foss.module.transfer.partialline.api.server.service.IPrintAgentWaybillService;
import com.deppon.foss.module.transfer.partialline.api.shared.define.PartiallineConstants;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintAgentWaybillEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintAgentWaybillRecordEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.PrintAgentWaybillDto;
import com.deppon.foss.module.transfer.partialline.api.shared.exception.ExternalBillException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * @author niuly
 * @function 打印代理面单service-STO
 * @date 2014年9月5日下午2:55:52
 */
public class PrintAgentWaybillService implements IPrintAgentWaybillService {

	private IPrintAgentWaybillDao printAgentWaybillDao;
	
	private IWaybillManagerService waybillManagerService;
	
	private IAdministrativeRegionsService administrativeRegionsService;
	
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	private ISaleDepartmentService saleDepartmentService ;
	
	private IWaybillRfcService waybillRfcService ;
	
	private IWaybillTrackingsService waybillTrackingsService;
	
	private ILoadService loadService;
	
	private ILdpExternalBillService ldpExternalBillService;
	
	private ILdpAgencyDeptService ldpAgencyDeptService;
	
	
	private IDataDictionaryValueService dataDictionaryValueService;
	
	/**
	 * 获取运单信息
	 */
	private IWaybillDao waybillDao;
	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}
	
	
	/**轨迹service***/
	private IPushTrackForCaiNiaoService pushTrackForCaiNiaoService;
	
	public void setPushTrackForCaiNiaoService(
			IPushTrackForCaiNiaoService pushTrackForCaiNiaoService) {
		this.pushTrackForCaiNiaoService = pushTrackForCaiNiaoService;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(PrintAgentWaybillService.class);
	public void setLdpExternalBillService(
			ILdpExternalBillService ldpExternalBillService) {
		this.ldpExternalBillService = ldpExternalBillService;
	}

	public void setPrintAgentWaybillDao(IPrintAgentWaybillDao printAgentWaybillDao) {
		this.printAgentWaybillDao = printAgentWaybillDao;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}

	public void setLoadService(ILoadService loadService) {
		this.loadService = loadService;
	}
	
	public void setWaybillTrackingsService(
			IWaybillTrackingsService waybillTrackingsService) {
		this.waybillTrackingsService = waybillTrackingsService;
	}


	public void setLdpAgencyDeptService(ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}

	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}


	/**
	 * @author nly
	 * @date 2014年9月5日 下午2:56:25
	 * @function 查询代理面单的运单
	 * @return 
	 */
	@Override
	public List<PrintAgentWaybillEntity> queryWaybills(PrintAgentWaybillDto dto,int start,int limit) {
		String deptCode = "";
		if((dto.getBeginInStockTime() != null && dto.getEndInStockTime() != null ) 
				|| (dto.getBeginHandOverTime() != null && dto.getEndHandOverTime() != null)
				|| CollectionUtils.isNotEmpty(dto.getWaybillNoList())) {
			CurrentInfo user = FossUserContext.getCurrentInfo();
			if(user != null) {
				String currentDeptCode = user.getCurrentDeptCode();
				OrgAdministrativeInfoEntity entity = this.getBigOrg(currentDeptCode);
				if(entity != null ) {
					deptCode = entity.getCode();
					dto.setDeptCode(deptCode);
				}
			}
		}
		
		List<PrintAgentWaybillEntity> list = printAgentWaybillDao.queryWaybills(dto,start,limit);
		
		list = this.packageList(list, deptCode);
		
		return list;
	}

	/**
	 * @author nly
	 * @date 2015年4月27日 下午2:33:21
	 * @function 获取最晚装车扫描时间
	 * @param list
	 * @param deptCode
	 * @return
	 */
	private List<PrintAgentWaybillEntity> packageList(List<PrintAgentWaybillEntity> list,String deptCode) {
		Date loadTime = null;
		if(CollectionUtils.isEmpty(list) || StringUtils.isEmpty(deptCode)) {
			return list;
		}
		for(PrintAgentWaybillEntity entity : list) {
			
			List<LoadSerialNoEntity> loadList = loadService.queryLdpLoadScanInfo(entity.getWaybillNo(), entity.getSerialNo(), deptCode);
			if(CollectionUtils.isNotEmpty(loadList)) {
				if(null != loadList.get(0)) {
					loadTime = loadList.get(0).getLoadTime();
					entity.setLoadScanTime(loadTime);
				}
			}
		}
		return list;
	}

	/**
	 * @author nly
	 * @date 2014年9月22日 下午5:18:27
	 * @function 获取上级外场
	 * @param currentOrg
	 * @return
	 */
	private OrgAdministrativeInfoEntity getBigOrg(String currentOrgCode){
			//设置查询参数
			List<String> bizTypesList = new ArrayList<String>();
			//外场类型
			bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
			OrgAdministrativeInfoEntity bigOrg = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(currentOrgCode, bizTypesList);
			if(bigOrg == null){
				throw new TfrBusinessException("查询当前部门所属外场失败","");
			}
			return bigOrg;
	}
	/**
	 * @author nly
	 * @date 2014年9月9日 下午2:39:17
	 * @function 查询运单总数
	 * @param dto
	 * @return
	 */
	@Override
	public Long queryWaybillCount(PrintAgentWaybillDto dto) {
		if((dto.getBeginInStockTime() != null && dto.getEndInStockTime() != null ) 
				|| (dto.getBeginHandOverTime() != null && dto.getEndHandOverTime() != null)
				|| CollectionUtils.isNotEmpty(dto.getWaybillNoList())) {
			CurrentInfo user = FossUserContext.getCurrentInfo();
			if(user != null) {
				String currentDeptCode = user.getCurrentDeptCode();
				OrgAdministrativeInfoEntity entity = this.getBigOrg(currentDeptCode);
				if(entity != null ) {
					String deptCode = entity.getCode();
					dto.setDeptCode(deptCode);
				}
			}
		}
		return printAgentWaybillDao.queryWaybillCount(dto);
	}
	
	/**
	 * @author nly
	 * @date 2014年9月13日 下午12:38:27
	 * @function 根据运单号查询运单信息
	 * @param waybillNo
	 * @return
	 */
	@Override
	public PrintAgentWaybillEntity queryWaybillByNo(String waybillNo) {
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		return this.getPrintWaybillInfo(waybillEntity);
	}

	/**
	 * @author nly
	 * @date 2014年9月13日 下午1:27:59
	 * @function 封装打印实体
	 * @param waybillEntity
	 * @return
	 */
	private PrintAgentWaybillEntity getPrintWaybillInfo(WaybillEntity waybillEntity) {
		PrintAgentWaybillEntity entity = new PrintAgentWaybillEntity();
		
		if(waybillEntity == null) {
			return entity;
		} else {
			List<String> codeList = this.getCodes(waybillEntity);
			Map<String, AdministrativeRegionsEntity> map = null;
			if(CollectionUtils.isNotEmpty(codeList)) {
				map =  administrativeRegionsService.queryAdministrativeRegionsBatchByCodeToMap(codeList);
			}
			
//			String deliverProv = this.getRegionsName(map, waybillEntity.getDeliveryCustomerProvCode());
//			String deliverCity = this.getRegionsName(map, waybillEntity.getDeliveryCustomerCityCode());
//			String deliverDist = this.getRegionsName(map, waybillEntity.getDeliveryCustomerDistCode());
//			String deliverAddr = deliverProv + deliverCity + deliverDist + waybillEntity.getDeliveryCustomerAddress();
			
			String receiverProv = this.getRegionsName(map, waybillEntity.getReceiveCustomerProvCode());
			String receiverCity = this.getRegionsName(map, waybillEntity.getReceiveCustomerCityCode());
			String receiverDist = this.getRegionsName(map, waybillEntity.getReceiveCustomerDistCode());
			String receiverAddr = waybillEntity.getReceiveCustomerAddress();
			
			entity.setWaybillNo(waybillEntity.getWaybillNo());
			entity.setDeliverName(waybillEntity.getDeliveryCustomerContact());
			entity.setDeliverTel(waybillEntity.getDeliveryCustomerMobilephone());
			entity.setDeliverPhone(waybillEntity.getDeliveryCustomerPhone());
			
//			entity.setDeliverProv(deliverProv);
//			entity.setDeliverCity(deliverCity);
//			entity.setDeliverDist(deliverDist);
//			entity.setDeliverAddr(deliverAddr);
			entity.setReceiverName(waybillEntity.getReceiveCustomerContact());
			entity.setReceiverTel(waybillEntity.getReceiveCustomerMobilephone());
			entity.setReceiverPhone(waybillEntity.getReceiveCustomerPhone());
			entity.setReceiverProv(receiverProv);
			entity.setReceiverCity(receiverCity);
			entity.setReceiverDist(receiverDist);
			entity.setReceiverAddr(receiverAddr);
			entity.setGoodsQty(waybillEntity.getGoodsQtyTotal());//chigo
			entity.setGoodsName(waybillEntity.getGoodsName());
			entity.setGoodsWeight(waybillEntity.getGoodsWeightTotal());
			entity.setGoodsVolume(waybillEntity.getGoodsVolumeTotal());
			entity.setInsurance(waybillEntity.getInsuranceAmount());
			
			return entity;
		}
	}
	/**
	 * @author nly
	 * @date 2014年9月18日 上午9:32:27
	 * @function 获取省市区名称
	 * @param map
	 * @param code
	 * @return
	 */
	private String getRegionsName(Map<String,AdministrativeRegionsEntity>map, String code) {
		if(map == null) {
			return "";
		}
		if(StringUtils.isEmpty(code)) {
			return "";
		} 
		AdministrativeRegionsEntity entity = map.get(code);
		if(entity == null) {
			return "";
		}
		if(StringUtils.isEmpty(entity.getName())) {
			return "";
		}
		return entity.getName();
	}
	/**
	 * @author nly
	 * @date 2014年9月18日 上午9:22:56
	 * @function 获取收发货人省市区
	 * @param waybillEntity
	 * @return
	 */
	private List<String> getCodes(WaybillEntity waybillEntity) {
		List<String> codeList = new ArrayList<String>();
		if(waybillEntity == null) {
			return codeList;
		} else {
			if(StringUtils.isNotEmpty(waybillEntity.getDeliveryCustomerProvCode())) {
				codeList.add(waybillEntity.getDeliveryCustomerProvCode());
			}
			if(StringUtils.isNotEmpty(waybillEntity.getDeliveryCustomerCityCode())) {
				codeList.add(waybillEntity.getDeliveryCustomerCityCode());
			}
			if(StringUtils.isNotEmpty(waybillEntity.getDeliveryCustomerDistCode())){
				codeList.add(waybillEntity.getDeliveryCustomerDistCode());
			}
			
			if(StringUtils.isNotEmpty(waybillEntity.getReceiveCustomerProvCode())){
				codeList.add(waybillEntity.getReceiveCustomerProvCode());
			}
			if(StringUtils.isNotEmpty(waybillEntity.getReceiveCustomerCityCode())) {
				codeList.add(waybillEntity.getReceiveCustomerCityCode());
			}
			if(StringUtils.isNotEmpty(waybillEntity.getReceiveCustomerDistCode())) {
				codeList.add(waybillEntity.getReceiveCustomerDistCode());
			}
			
		}
		return codeList;
	}

	/**
	 * @author nly
	 * @date 2014年9月16日 下午2:15:45
	 * @function 转换值
	 * @param entity
	 */
	/*private void convertData(PrintAgentWaybillEntity entity) {
		String waybillNo = StringUtils.isEmpty(entity.getWaybillNo()) ? "" :entity.getWaybillNo();
		String deliverName = StringUtils.isEmpty(entity.getDeliverName()) ? "" : entity.getDeliverName();
		String deliverTel = StringUtils.isEmpty(entity.getDeliverTel()) ? "" : entity.getDeliverTel();
		String receiverName = StringUtils.isEmpty(entity.getDeliverName()) ? "" : entity.getDeliverName();
		String receiverTel = StringUtils.isEmpty(entity.getDeliverTel()) ? "" : entity.getDeliverTel();
		String receiverAddr = StringUtils.isEmpty(entity.getReceiverAddr()) ? "" : entity.getReceiverAddr();
		String goodsName = StringUtils.isEmpty(entity.getGoodsName()) ? "" : entity.getGoodsName();
		int goodsQty = entity.getGoodsQty() == 0 ? 0 : entity.getGoodsQty();
		BigDecimal insurance = entity.getInsurance() == null ? BigDecimal.valueOf(0) : entity.getInsurance();
		
		entity.setWaybillNo(waybillNo);
		entity.setDeliverName(deliverName);
		entity.setDeliverTel(deliverTel);
		entity.setReceiverName(receiverName);
		entity.setReceiverTel(receiverTel);
		entity.setReceiverAddr(receiverAddr);
		entity.setGoodsName(goodsName);
		entity.setGoodsQty(goodsQty);
		entity.setInsurance(insurance);
	}*/
	/**
	 * @author nly
	 * @date 2014年9月15日 下午2:06:30
	 * @function 新增打印代理面单记录
	 * @param printRecord
	 * @return
	 */
	@Override
	public int addPrintrecord(PrintAgentWaybillRecordEntity printRecord) {
		//处理新增时代理单号中的空格
		if(printRecord.getAgentWaybillNo()!= null) {
			printRecord.setAgentWaybillNo(printRecord.getAgentWaybillNo().trim());
		}
		//新增本次打印记录
		printAgentWaybillDao.addPrintrecord(printRecord);
		//作废之前有效的打印记录
		printAgentWaybillDao.updateActive(printRecord);
		
		return 0;
	}

	/**
	 * @author chigo
	 * @date 2014-10-15上午8:45:28
	 * @function 综合查询代理信息
	 * @param agentWaybillRecordEntity
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public List<PrintAgentWaybillRecordEntity> queryWaybillsRecord(
			PrintAgentWaybillRecordEntity agentWaybillRecordEntity, int start,
			int limit) {
		return printAgentWaybillDao.queryWaybillsRecord(agentWaybillRecordEntity, start, limit);
	}
	/**
	 * @author nly
	 * @date 2015年2月2日 下午5:15:58
	 * @function 根据代理单号查询打印记录
	 * @param agentWaybillNo
	 * @return
	 */
	@Override
	public List<PrintAgentWaybillRecordEntity> queryRecordByAgentWaybillNo(String agentWaybillNo) {
		return printAgentWaybillDao.queryRecordByAgentWaybillNo(agentWaybillNo);
	}
	/**
	 * 根据代理单号与代理公司查询绑定记录
	 * @param agentWaybillNo
	 * @return List<PrintAgentWaybillRecordEntity>
	 * @author 257220
	 * @date 2015-8-10下午6:56:36
	 */
	public List<PrintAgentWaybillRecordEntity> queryRecordByAgentWaybillNoAndCompany(String agentWaybillNo,String agentCompanyCode) {
		return printAgentWaybillDao.queryRecordByAgentWaybillNo(agentWaybillNo,agentCompanyCode);
	}
	
	/**
	 * @author nly
	 * @date 2015年2月4日 下午2:33:08
	 * @function 查询运单绑定信息
	 * @param waybillNo
	 * @param type
	 * @return
	 */
	@Override
	public List<PrintAgentWaybillRecordEntity> queryRecordByWaybillNo(String waybillNo, String type) {
		return printAgentWaybillDao.queryRecordByWaybillNo(waybillNo,type);
	}
	
	/**
	 * @author chigo
	 * @date 2015年2月3日 下午4:06:37
	 * @function 查询营业部外发绑定运单信息
	 * @param dto
	 * @return
	 */
	@Override
	public Long queryBundleWaybillsCount(PrintAgentWaybillDto dto) {

		CurrentInfo user = FossUserContext.getCurrentInfo();
		if(user != null) {
			String currentDeptCode = user.getCurrentDeptCode();
			
			if(dto.getWaybillNo() != null) {
				String stockOrgCode = this.getStockOrg(currentDeptCode);//快件对应的库存部门
				dto.setDeptCode(stockOrgCode);
			}
			dto.setCurrentDeptCode(currentDeptCode);
		}
		
		
		return printAgentWaybillDao.queryBundleWaybillsCount(dto);
		
	}
	/**
	 * @author chigo
	 * @date 2015年2月3日 上午10:23:56
	 * @function 查询营业部外发绑定运单信息
	 * @return 
	 */
	@Override
	public List<PrintAgentWaybillRecordEntity> queryBundleWaybills(PrintAgentWaybillDto dto,int start,int limit) {
		CurrentInfo user = FossUserContext.getCurrentInfo();
		if(user != null) {
			String currentDeptCode = user.getCurrentDeptCode();
			
			if(dto.getWaybillNo() != null) {
				String stockOrgCode = this.getStockOrg(currentDeptCode);//快件对应的库存部门
				dto.setDeptCode(stockOrgCode);
			}
			dto.setCurrentDeptCode(currentDeptCode);
		}
		
		List<PrintAgentWaybillRecordEntity> list = printAgentWaybillDao.queryBundleWaybills(dto,start,limit);

		return list;
	}
	
	/**
	 * @author chigo
	 * @date 2015年2月3日 上午10:55:36
	 * @function 查对应库存的部门编号
	 * @return 
	 */
	private String getStockOrg(String currentDeptCode) {
		String orgCode = "";
		SaleDepartmentEntity entity = saleDepartmentService.querySimpleSaleDepartmentByCode(currentDeptCode);
		if(entity != null){
			if(entity.getStation().equals("Y")){
				orgCode = entity.getTransferCenter();
				return orgCode;
			}
		}
		return currentDeptCode;
		
	}

	/**
	 * @author chigo
	 * @date 2015-2-4下午8:53:36
	 * @function 绑定营业部运单和代理单号
	 * @param dto
	 * @return
	 */
	@Override
	public int bundleSdExternalBill(PrintAgentWaybillDto dto) {
		
		List<String> waybillNoList = dto.getWaybillNoList();
		List<String> agentWaybillNoList = dto.getAgentWaybillNoList();
		List<String> agentCompanyList = dto.getAgentCompanyList();
		List<String> frightFeeList = dto.getFrightFeeList();
		CurrentInfo user = FossUserContext.getCurrentInfo();
		
		for(int i = 0 ; i < waybillNoList.size() ; i++){
			PrintAgentWaybillRecordEntity entity = new PrintAgentWaybillRecordEntity();
			entity.setId(UUIDUtils.getUUID());
			entity.setWaybillNo(waybillNoList.get(i));
			entity.setAgentWaybillNo(agentWaybillNoList.get(i));
			entity.setAgentCompanyCode(agentCompanyList.get(i));
			entity.setAgentCompanyName(PartiallineConstants.agentWaybillMap.get(agentCompanyList.get(i)));
			entity.setFrightFee(new BigDecimal(frightFeeList.get(i)));
			entity.setActive("Y");
			entity.setCreateDate(new Date());
			entity.setOperatTime(new Date());
			entity.setOperatorCode(user.getEmpCode());
			entity.setOperatorName(user.getEmpName());
			entity.setOrgCode(user.getCurrentDeptCode());
			entity.setOrgName(user.getCurrentDeptName());
			entity.setPrintType("SD");
			//验证运单更改的受理状态，为待受理或者待审核状态就提示不能进行营业外发单号绑定
			 if(waybillRfcService.isExsitsWayBillRfc(entity.getWaybillNo())){
				 throw new ExternalBillException("运单号："+entity.getWaybillNo()+"存在更改未审核或未受理，无法绑定");
			 }
			 //绑定前验证当前查询的运单信息是否改变，若改变后不符合绑定条件则给出提示，不能绑定
			 PrintAgentWaybillDto printAgentWaybillDto = new PrintAgentWaybillDto();
			 printAgentWaybillDto.setWaybillNo(entity.getWaybillNo());
			 if (this.queryBundleWaybillsCount(printAgentWaybillDto) == 0){
				 throw new ExternalBillException("运单号："+entity.getWaybillNo()+"信息已发生变化，不符合营业部外发绑定条件，请重新确认运单信息");
			 }
			Long validateBundle = (Long)this.printAgentWaybillDao.validateBundle(entity);
				if(validateBundle==0){
					this.printAgentWaybillDao.bundleSdExternalBill(entity);
					entity.setId(UUIDUtils.getUUID());
					this.printAgentWaybillDao.bundleFrightFee(entity);
				}
				else if(validateBundle > 0 ){
					throw new ExternalBillException("运单已绑定，运单号："+entity.getWaybillNo());
				}
			//插入轨迹表
			try{
				WaybillTrackingsDto trackDto = new WaybillTrackingsDto();
				trackDto.setWaybillNo(entity.getWaybillNo());
				trackDto.setOperateType(WaybillTrackingsConstants.OPERATE_TYPE_SD_TRACK_TRANSFER);
				trackDto.setOperateDeptCode(entity.getOrgCode()); 
				trackDto.setOperateDeptName(entity.getOrgName()); 
				trackDto.setOperateTime(new Date());
				trackDto.setOperatorName(entity.getOperatorName());

				waybillTrackingsService.addOneWaybillTrack(trackDto);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		return 0;
	}
	/**
	 * @author chigo
	 * @date 2015-2-5上午10:48:19
	 * @function 作废绑定营业部运单和代理单号
	 * @param dto
	 * @return
	 */
	@Override
	public int unBundleSdExternalBill(PrintAgentWaybillDto dto) {
		List<String> waybillNoList = dto.getWaybillNoList();
		List<String> agentWaybillNoList = dto.getAgentWaybillNoList();
		CurrentInfo user = FossUserContext.getCurrentInfo();
		
		for(int i = 0 ; i < waybillNoList.size() ; i++){
			PrintAgentWaybillRecordEntity entity = new PrintAgentWaybillRecordEntity();
			entity.setWaybillNo(waybillNoList.get(i));
			entity.setAgentWaybillNo(agentWaybillNoList.get(i));
			entity.setActive("N");
			entity.setOperatTime(new Date());
			entity.setOperatorCode(user.getEmpCode());
			entity.setOperatorName(user.getEmpName());
			entity.setOrgCode(user.getCurrentDeptCode());
			entity.setOrgName(user.getCurrentDeptName());
			entity.setPrintType("SD");
			this.printAgentWaybillDao.unBundleSdExternalBill(entity);
			entity.setId(UUIDUtils.getUUID());
			this.printAgentWaybillDao.unBundleFrightFee(entity);

			try{
				WaybillTrackingsDto trackDto = new WaybillTrackingsDto();
				trackDto.setWaybillNo(entity.getWaybillNo());
				trackDto.setOperateType(WaybillTrackingsConstants.OPERATE_TYPE_SD_TRACK_TRANSFER_CANCEL);
				trackDto.setOperateDeptCode(entity.getOrgCode()); 
				trackDto.setOperateDeptName(entity.getOrgName()); 
				trackDto.setOperateTime(new Date());
				trackDto.setOperatorName(entity.getOperatorName());

				waybillTrackingsService.addOneWaybillTrack(trackDto);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * @author nly
	 * @date 2015年4月28日 下午12:41:49
	 * @function 保存快递代理绑定数据
	 * @param entity
	 * @return
	 */
	@Override
	public int addLdpBindRecord(PrintAgentWaybillRecordEntity entity) {
		entity = this.completeRecord(entity); 
		return printAgentWaybillDao.addPrintrecord(entity);
	}
	
	/**
	 * @author alfred
	 * @function 插入轨迹信息
	 * @param entity
	 * @param user
	 */
	public void addTrackSyn(PrintAgentWaybillRecordEntity entity,CurrentInfo user){
		SynTrackingEntity synTrackEntity = new SynTrackingEntity();
		synTrackEntity.setId(UUIDUtils.getUUID());
		synTrackEntity.setWayBillNo(entity.getWaybillNo());
		synTrackEntity.setNextMailNos(entity.getAgentWaybillNo());//外发单号
		if(entity.getAgentCompanyName().equals("天天快递")){
			synTrackEntity.setNextTpCode("TTKDEX");//外发公司编码
		}else if(entity.getAgentCompanyName().equals("全峰快递")){
			synTrackEntity.setNextTpCode("QFKD");//外发公司编码
		}else if(entity.getAgentCompanyName().equals("百世汇通")){
			synTrackEntity.setNextTpCode("HTKY");//外发公司编码
		}else if(entity.getAgentCompanyName().equals("全一快递")){
			synTrackEntity.setNextTpCode("UAPEX");//外发公司编码
		}else if(entity.getAgentCompanyName().equals("速尔快递")){
			synTrackEntity.setNextTpCode("SURE");//外发公司编码
		}else{
			synTrackEntity.setNextTpCode(entity.getAgentCompanyCode());//外发公司编码
		}
		synTrackEntity.setCreateDate(new Date());
		synTrackEntity.setOperateTime(new Date());
		synTrackEntity.setOperatorCode(user.getEmpCode());
		synTrackEntity.setOperatorName(user.getEmpName());
		synTrackEntity.setOrgCode(user.getCurrentDeptCode());
		synTrackEntity.setOrgName(user.getCurrentDeptName());
		synTrackEntity.setEventType("LDP");
		synTrackEntity.setTrackInfo(user.getCurrentDeptName()+" "+user.getEmpName()+"已绑定外发单！");
		synTrackEntity.setOperateCity(user.getDept().getProvName()+" "+user.getDept().getCityName()+" "+user.getDept().getCountyName());
		synTrackEntity.setOrgType("2");
		//2016年8月19日10:41:29 311396增加代理公司名称 DN201611150016 增加编码
		WaybillEntity waybillEntity = waybillDao.queryWaybillByNo(entity.getWaybillNo());//获取运单信息
		//获取代理公司信息
		OuterBranchExpressEntity outer = getLdpAgentCompanyName(waybillEntity);
		synTrackEntity.setNextTpCode(outer.getAgentCompany());//编码
		synTrackEntity.setAgentCompanyName(outer.getAgentCompanyName());//名称
		pushTrackForCaiNiaoService.addSynTrackToWQS(synTrackEntity);
	}
	
	

	/**
	 * 查询快递代理公司
	 * 
	 * @author wwb
	 * @date 2016年8月19日10:36:51
	 */
	private OuterBranchExpressEntity getLdpAgentCompanyName(WaybillEntity waybillEntity) {
		OuterBranchExpressEntity companyDto = null;
		if (waybillEntity != null) {
			// 根据运单号查询落地配代理信息
			// 提货网点CODE
			String agencyBranchCode = waybillEntity.getCustomerPickupOrgCode();
			// 落地配公司
			if(StringUtils.isNotBlank(agencyBranchCode)) {
				LOGGER.info("通过网点查询代理编码查询代理公司:" + agencyBranchCode);
				// 代理信息Dto // 代理网点编码查询代理公司
				companyDto = ldpAgencyDeptService.queryLdpAgencyDeptByCode(agencyBranchCode, FossConstants.ACTIVE);
			}
		}
		return companyDto == null ? new OuterBranchExpressEntity() : companyDto;
	}
	
	
	private PrintAgentWaybillRecordEntity completeRecord(PrintAgentWaybillRecordEntity entity) {
		entity.setId(UUIDUtils.getUUID());
		entity.setActive("Y");
		entity.setOperatTime(new Date());
		//modified by 257220 :根据提货网点获取代理公司简称
		//添加代理公司
		//entity.setAgentCompanyName(PartiallineConstants.agentWaybillMap.get(entity.getAgentCompanyCode()));
		setAgentCompanybyPickupNetWork(entity);
		return entity;
	}

	/**
	 * @author nly
	 * @date 2015年4月28日 下午3:36:41
	 * @function 作废快递代理绑定
	 * @param entity
	 * @return
	 */
	@Override
	public int invalidLdpBindRecord(PrintAgentWaybillRecordEntity entity) {
		entity.setOperatTime(new Date());
		return printAgentWaybillDao.invalidLdpBindRecord(entity);
	}

	/**
	 * @author nly
	 * @date 2015年4月29日 上午11:05:12
	 * @function 是否存在有效的快递代理外发单
	 * @param waybillNos
	 * @return
	 * 作废原因：前台界面已经注释，对应的action和service
	 */
	@Deprecated
	@Override
	public String queryLdpExternalBill(List<String> list) {
		String finalWaybillNo = "";
		for(String waybillNo : list ) {
			
			LdpExternalBillDto dto = new LdpExternalBillDto();
			dto.setWaybillNo(waybillNo);
			List<String> statusList = new ArrayList<String>();
			statusList.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT);
			statusList.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
			statusList.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT);
			dto.setList(statusList);
			List<LdpExternalBillDto> resultList = ldpExternalBillService.queryOrigLdpExternalBillList(dto);
			if(CollectionUtils.isNotEmpty(resultList)) {
				finalWaybillNo = waybillNo;
				break;
			}
		}
		return finalWaybillNo;
	}
	
	/**
	 * @author nly
	 * @date 2015年4月29日 下午12:24:15
	 * @function 校验单号和流水号是否已存在
	 * @param entity
	 */
	@Override
	public void checkWaybillRecord(PrintAgentWaybillRecordEntity entity) {
		List<PrintAgentWaybillRecordEntity> list = printAgentWaybillDao.queryRecordByWaybillNoAndSerialNo(entity.getWaybillNo(), entity.getSerialNo());
		if(CollectionUtils.isNotEmpty(list)) {
			throw new ExternalBillException("运单号" + entity.getWaybillNo() +",流水号"+ entity.getSerialNo() +"已绑定，不能重复绑定");
		}
	}
	/**
	 * @author nly
	 * @date 2015年4月29日 下午12:54:11
	 * @function 校验代理单号是否存在
	 * @param entity
	 */
	@Override
	public void checkAgentWaybillRecord(PrintAgentWaybillRecordEntity entity) {
		List<PrintAgentWaybillRecordEntity>  list1 = printAgentWaybillDao.queryRecordByAgentWaybillNo(entity.getAgentWaybillNo());
		if(CollectionUtils.isNotEmpty(list1)) {
			throw new ExternalBillException("代理单号" + entity.getAgentWaybillNo() +"已被绑定，不能重复使用");
		}	
	}
	
	/**
	 * @author nly
	 * @date 2015年4月29日 下午12:58:46
	 * @function 校验运单号、流水号是否在本部门落地配交接单或库存中
	 * @param entity
	 */
	@Override
	public void checkStockAndHandOverbill(PrintAgentWaybillRecordEntity entity) {
		String deptCode = "";
		CurrentInfo user = FossUserContext.getCurrentInfo();
		if(user != null) {
			String currentDeptCode = user.getCurrentDeptCode();
			OrgAdministrativeInfoEntity orgEntity = this.getBigOrg(currentDeptCode);
			if(orgEntity != null ) {
				deptCode = orgEntity.getCode();
			}
		}
		if(StringUtils.isEmpty(deptCode)) {
			throw new ExternalBillException("运单号" + entity.getWaybillNo() +",流水号"+ entity.getSerialNo() +"未做快递代理交接单且不在库存，不能绑定");
		}
		int count = printAgentWaybillDao.queryStockAndHandOverBillCount(entity.getWaybillNo(),entity.getSerialNo(),deptCode);
		if(count <= 0) {
			throw new ExternalBillException("运单号" + entity.getWaybillNo() +",流水号"+ entity.getSerialNo() +"未做快递代理交接单且不在库存，不能绑定");
		}
	}
	/**
	 * @author gongjp
	 * @date 205-04-29
	 * @function  批量绑定
	 * @param excelDtos
	 * @return int
	 */
	@Override
	@Transactional
	public int batchImportPrintAgentWaybillList(List<PrintAgentWaybillRecordEntity> excelDtos) {
		//校验数据是否重复
		valiedate(excelDtos);
		CurrentInfo user = FossUserContext.getCurrentInfo();
		int total = 0;
		for(PrintAgentWaybillRecordEntity entity : excelDtos) {
			int count = this.addLdpBindRecord(entity);
			//增加货物轨迹推送给WQS
			this.addTrackSyn(entity, user);
			if(count!=0){
				total=total+1;
			}
		}
		return total;
	}
	/**
	 * @author gongjp
	 * @date 2015-05-12
	 * @function 校验excel数据是否满足标准
	 * @param excelDtos
	 */
	private void valiedate(List<PrintAgentWaybillRecordEntity> excelDtos) {
		//存放重复的运单号
		 List<String> waybillList=new ArrayList<String>();
		//存放重复的代理单号
		 List<String>agentList=new ArrayList<String>();
		for(int i=0;i<excelDtos.size();i++){
			 for(int j=i+1;j<excelDtos.size();j++){
				 //将重复的运单号放入集合中
				 if((excelDtos.get(i).getWaybillNo()+excelDtos.get(i).getSerialNo()).equals((excelDtos.get(j).getWaybillNo()+excelDtos.get(j).getSerialNo()))){
					 waybillList.add("运单号:"+excelDtos.get(i).getWaybillNo()+",流水号:"+excelDtos.get(i).getSerialNo()+"组合重复");
				 }//将重复的代理单号放入集合中
				 if(excelDtos.get(i).getAgentWaybillNo().equals(excelDtos.get(j).getAgentWaybillNo())){
					 agentList.add(excelDtos.get(i).getAgentWaybillNo());
				 }
			 }
		}
		//运单号重复数据集合
		List<String> data = removeRepeatingData(waybillList);
		//代理单号重复数据集合
		List<String> data1 = removeRepeatingData(agentList);
		if(data!=null&&data.size()>0){
			throw new BusinessException(changeList2String(data),changeList2String(data));
		}
		if(data1!=null&&data1.size()>0){
			throw new BusinessException("代理单号"+changeList2String(data1)+"重复","代理单号"+changeList2String(data1)+"重复");
		}
	}
	
	
	/***@date 2015-05-13
	 * @author gongjp
	 * 删除集合集合中重复数据
	 * @return List<String>
	 * @param list
	 */
	public static List<String>  removeRepeatingData(List<String> list){
		 HashSet<String> set  = new LinkedHashSet<String>(list);
	     list.clear();
	     list.addAll(set);
	     return list;
	}
	
	/***
	 * @author gongjp
	 * @date 2015-05-13
	 * 将集合转换成字符串
	 * @param list
	 * return String
	 */
	public  String changeList2String(List<String> list){
		StringBuffer sb=new StringBuffer();
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				sb.append(list.get(i)+",");
			}
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
	/**
	 * 判断运单是否外发
	 * @param excelDto
	 * @author 257220
	 * @date 2015-6-2上午11:07:05
	 */
	public void checkWaybillIsOuter(PrintAgentWaybillRecordEntity entity){
		String waybillNo = entity.getWaybillNo();
		int num = printAgentWaybillDao.queryOuterNetWorkNumByWaybillNo(waybillNo);
		if(num == 0){
			String message = "请确认运单号为："+waybillNo+"的运单是否外发！";
			LOGGER.error(message);
			throw new ExternalBillException(message);
		}
	}
	/**
	 * 根据运单对应的提货网点查询代理公司
	 * @param entity
	 * @author 257220
	 * @date 2015-5-27下午5:16:52
	 */
	public void setAgentCompanybyPickupNetWork(PrintAgentWaybillRecordEntity entity){
		String waybillNo = entity.getWaybillNo();
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		String message = "";
		if(waybillEntity == null){
			message = "未找到运单号为："+waybillNo+"的运单，请检查运单号是否输入正确！";
			LOGGER.error(message);
			throw new ExternalBillException(message);
		}
		String network = waybillEntity.getCustomerPickupOrgCode();//提货网点
		if(StringUtils.isEmpty(network)){
			message = "未找到运单号为："+waybillNo+"的运单的提货网点！";
			LOGGER.error(message);
			throw new ExternalBillException(message);
		}
		//调用接口获取代理公司
		String agentCompanyCode = "";
		String agentCompanyName = "";
		OuterBranchExpressEntity obe = ldpAgencyDeptService.queryLdpAgencyDeptByDeptCode(network);
		if(obe != null){
			agentCompanyCode = obe.getAgentCompanyAbbreviationCode();
			agentCompanyName = obe.getAgentCompanyAbbreviation();
		}
		if(StringUtils.isEmpty(agentCompanyCode)){
			message = "未找到运单号为："+waybillNo+"的运单的提货网点对应的代理公司！";
			LOGGER.error(message);
			throw new ExternalBillException(message);
		}
		entity.setAgentCompanyCode(agentCompanyCode);
		entity.setAgentCompanyName(agentCompanyName);
	}

	/**
	 * 构建与快递一百交互dto
	 * @param entity
	 * @author 257220
	 * @date 2015-07-29下午5:16:52
	 */
	public List<AgentWaybillNoRequestDto> buildAgentWaybillNoRequestDto(
			List<PrintAgentWaybillRecordEntity> recordList) {
		String message = "";
		List<AgentWaybillNoRequestDto> agentWaybillNoRequestDtos = new ArrayList<AgentWaybillNoRequestDto>();
		for (PrintAgentWaybillRecordEntity printAgentWaybillRecordEntity : recordList) {
			String agentWaybillNo = printAgentWaybillRecordEntity.getAgentWaybillNo();
			String waybillNo = printAgentWaybillRecordEntity.getWaybillNo();
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
			AgentWaybillNoRequestDto agentWaybillNoRequestDto = new AgentWaybillNoRequestDto();
			if(waybillEntity == null){
				message = "未找到运单号为："+waybillNo+"的运单,请确认该运单是否存在！";
				LOGGER.error(message);
				throw new ExternalBillException(message);
			}
			//提货网点
			String pickupOrgCode = waybillEntity.getCustomerPickupOrgCode();
			OuterBranchEntity outerBranch = waybillManagerService.queryAgencyBranchInfo(pickupOrgCode);
			if(outerBranch == null){
				message = "未找到运单号为："+waybillNo+"的运单的对应的外部网点！";
				LOGGER.error(message);
				throw new ExternalBillException(message);
			}
			String pickupCityCode = outerBranch.getCityCode();
			String cityName = administrativeRegionsService.queryAdministrativeRegionsNameByCode(pickupCityCode);
			if(pickupCityCode == null){
				message = "未找到运单号为："+waybillNo+"的运单的对应的外部网点所在城市！";
				LOGGER.error(message);
				throw new ExternalBillException(message);
			}
			DataDictionaryValueEntity dataDictionaryValueEntity = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.ABBREVIATION_OF_AGENCY_COMPANY, printAgentWaybillRecordEntity.getAgentCompanyCode());
			String company = null;
			if(dataDictionaryValueEntity == null){
				message = "未找到运单号为："+waybillNo+"的运单的对应的外部代理公司，请确认数据字典中有无配置！";
				LOGGER.error(message);
				throw new ExternalBillException(message);
			}
			//快递一百对应的代理公司编码
			company = dataDictionaryValueEntity.getExtAttribute1();
			if(company == null || "".equals(company)){
				message = "未找到运单号为："+waybillNo+"的运单的对应的外部代理公司，请确认数据字典中有无配置！";
				LOGGER.error(message);
				throw new ExternalBillException(message);
			}
			agentWaybillNoRequestDto.setNumber(agentWaybillNo);
			agentWaybillNoRequestDto.setTo(cityName);
			agentWaybillNoRequestDto.setCompany(company);
			agentWaybillNoRequestDtos.add(agentWaybillNoRequestDto);
		}
		return agentWaybillNoRequestDtos;
	}
	/**
	 * 检查代理单号、运单号是否重复订阅
	 * @param entity
	 * @author 257220
	 * @date 2015-8-3下午2:19:17
	 */
	public void checkAgentWaybillCanDoOrder(PrintAgentWaybillRecordEntity entity) {
		List<PrintAgentWaybillRecordEntity>  list1 = printAgentWaybillDao.queryOrderedRecord(entity);
		if(list1 == null){
			return;
		}
		String waybillNo = entity.getWaybillNo();
		String serialNo = entity.getSerialNo();
		String agentWayBillNo = entity.getAgentWaybillNo();//代理单号
		String agentCompanyCode = entity.getAgentCompanyCode();//代理公司简称
		for (PrintAgentWaybillRecordEntity printAgentWaybillRecordEntity : list1) {
			String agentWayBillNo1 = printAgentWaybillRecordEntity.getAgentWaybillNo();
			String agentCompanyCode1 = printAgentWaybillRecordEntity.getAgentCompanyCode();
			String waybillNo1 = printAgentWaybillRecordEntity.getWaybillNo();
			String serialNo1 = printAgentWaybillRecordEntity.getSerialNo();
			//先判断当前代理公司下的代理单号是否已有订阅
			if(StringUtils.equals(agentWayBillNo, agentWayBillNo1)&&StringUtils.equals(agentCompanyCode, agentCompanyCode1)){
				throw new ExternalBillException("代理单号："+agentWayBillNo+"已经订阅，不能重复订阅！");
			}else if(StringUtils.equals(waybillNo, waybillNo1)&&StringUtils.equals(serialNo, serialNo1)){//当前运单号流水号是否已有订阅
				throw new ExternalBillException("运单号："+waybillNo+"流水号："+serialNo+"已经订阅，不能重复订阅！");
			}
		}
	}

	/**
	 * 停止订阅快递100轨迹
	 * @param printAgentWaybillRecordEntity
	 * @author 257220
	 * @date 2015-8-5下午2:19:17
	 */
	public int stopTrackOrder(PrintAgentWaybillRecordEntity printAgentWaybillRecordEntity) {
		printAgentWaybillRecordEntity.setIspush(PartiallineConstants.ISPUSH_BACK);
		printAgentWaybillRecordEntity.setOperatTime(new Date());
		//更新绑定代理推送状态为已退订
		return printAgentWaybillDao.updatePrintAgentPushState(printAgentWaybillRecordEntity);
	}
}
