package com.deppon.foss.module.transfer.oa.server.ws;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.querying.server.service.IIntegrativeQueryService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IWayBillNoLocusService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WayBillNoLocusDTO;
import com.deppon.foss.module.pickup.sign.api.server.dao.ISignDetailDao;
import com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignDetailService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StockDto;
import com.deppon.foss.module.pickup.sign.server.service.impl.WaybillQueryForBseService;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillQueryService;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillInfoByWaybillNoReultDto;
import com.deppon.foss.module.transfer.oa.server.domain.InLocusEntityResponse;
import com.deppon.foss.module.transfer.oa.server.domain.InternalLocusEntityResponse;
import com.deppon.foss.module.transfer.oa.server.domain.LostCodeEntityResponse;
import com.deppon.foss.module.transfer.oa.server.domain.LostReportEntityRequest;
import com.deppon.foss.module.transfer.oa.server.domain.LostReportEntityResponse;
import com.deppon.foss.util.define.FossConstants;
/**
 * crm传递给FOSS运单号丢货上报
 * @author 362917
 * @date 2016-11-11 14:31:11
 */
@Controller
public class CRMFossQMSService implements ICRMFossQMSService{
	private static final Logger LOGGER = LoggerFactory.getLogger(CRMFossQMSService.class);
	/**
	 *  运单签收结果Dao
	 */
	private IWaybillSignResultDao waybillSignResultDao;
	/**
	 * 运单计划走货路径
	 */
	private WaybillQueryForBseService waybillQueryForBseService;
	/**
	 * 查询运单信息
	 */
	private IWaybillQueryService waybillQueryService;
	/**
	 * 运单轨迹接口service. 
	 */
	private IWayBillNoLocusService wayBillNoLocusService;
	/**
	 * 运单服务类
	 */
	private IWaybillManagerService waybillManagerService;
	/**
	 * 签收明细service
	 */
	private ISignDetailService signDetailService;
	/**
	 * 综合查询接口
	 */
	private IIntegrativeQueryService integrativeQueryService;
	/**
	 * 运单Dao
	 */
	private IWaybillDao waybillDao;
	/**
	 * 运单签收结果service
	 */
	private IWaybillSignResultService waybillSignResultService;
	/**
	 * 签收明细dao
	 */
	private ISignDetailDao signDetailDao;
	/**
	 * 货签服务接口
	 */
	private ILabeledGoodService labeledGoodService;
	
	/**
	 * @author zhangdandan
	 * @date 2016年12月10日 上午9:16:18
	 * @function crm投诉上报传递给foss运单号
	 * @function --FOSS传递相关数据到QMS进行丢货上报
	 * @param 
	 * @return
	 */
	
	 @Override
		public @ResponseBody void getWayBillToReport(@RequestBody  LostReportEntityRequest lostReportEntityRequest){
		 try {
				//获取实体运单号（CRM上报的运单号）
				String waybillNo=lostReportEntityRequest.getWayBillNo();
				LOGGER.info("接收CRM传递的投诉上报运单号：" + waybillNo);
				//判断运单号是否为空
				if(StringUtils.isNotEmpty(waybillNo)){
					//根据运单号查询运单信息
					WaybillEntity waybillEntity =new WaybillEntity();
					waybillEntity=waybillDao.queryWaybillNo(waybillNo);
					//查询crm上报的运单号在FOSS表中的存在(根据运单号判断是否在运单表中存在，若存在则说明该运单号有效)
					if(waybillEntity!=null){
						WaybillInfoByWaybillNoReultDto waybillInfoByWaybillNoReultDto = waybillQueryService.queryWaybillInfoByWaybillNo(waybillNo);
						LostReportEntityResponse lostReportEntityResponse=new LostReportEntityResponse();
						lostReportEntityResponse.setWaybillNum(waybillNo);//运单号
					  	lostReportEntityResponse.setTakeOverDeptCode(waybillEntity.getReceiveOrgCode()==null?null:waybillEntity.getReceiveOrgCode());//收货部门编码
						lostReportEntityResponse.setTakeOverDeptName(waybillEntity.getReceiveOrgName()==null?null:waybillEntity.getReceiveOrgName());//收货部门名称
						lostReportEntityResponse.setArriveDeptName(waybillEntity.getCustomerPickupOrgName()==null?null:waybillEntity.getCustomerPickupOrgName()); //到达部门名称
						lostReportEntityResponse.setArriveDeptCode(waybillEntity.getCustomerPickupOrgCode()==null?null:waybillEntity.getCustomerPickupOrgCode());//到达部门编码
						lostReportEntityResponse.setTransNature(waybillInfoByWaybillNoReultDto.getProductName()==null?null:waybillInfoByWaybillNoReultDto.getProductName());//运输性质
						lostReportEntityResponse.setSendClientName(waybillEntity.getDeliveryCustomerName()==null?null:waybillEntity.getDeliveryCustomerName());//发货客户名称
						lostReportEntityResponse.setSendClientCode(waybillEntity.getDeliveryCustomerCode()==null?null:waybillEntity.getDeliveryCustomerCode());//发货客户编码
						lostReportEntityResponse.setTakeOverClientName(waybillEntity.getReceiveCustomerName()==null?null:waybillEntity.getReceiveCustomerName());//收货客户名称
						lostReportEntityResponse.setTakeOverClientCode(waybillEntity.getReceiveCustomerCode()==null?null:waybillEntity.getReceiveCustomerCode());//收货客户编码
						lostReportEntityResponse.setPickUpType(waybillInfoByWaybillNoReultDto.getReceiveMethod()==null?null:waybillInfoByWaybillNoReultDto.getReceiveMethod());//提货方式
						lostReportEntityResponse.setPayType(waybillInfoByWaybillNoReultDto.getPaidMethod()==null?null:waybillInfoByWaybillNoReultDto.getPaidMethod());//付款方式
						lostReportEntityResponse.setSendClientMobile(waybillEntity.getDeliveryCustomerMobilephone()==null?null:waybillEntity.getDeliveryCustomerMobilephone());//发货客户手机
						lostReportEntityResponse.setBillingDeptCode(waybillEntity.getReceiveOrgCode()==null?null:waybillEntity.getReceiveOrgCode()); //开单部门编码
						lostReportEntityResponse.setGoodsName(waybillEntity.getGoodsName()==null?null:waybillEntity.getGoodsName());//货物名称
						BigDecimal goodsWeightTotal= waybillEntity.getGoodsWeightTotal();
						lostReportEntityResponse.setWeight(goodsWeightTotal==null?null:goodsWeightTotal.toString());//总重量
						BigDecimal GoodsVolumeTotal= waybillEntity.getGoodsVolumeTotal();
						lostReportEntityResponse.setVolume(GoodsVolumeTotal==null?null:GoodsVolumeTotal.toString());//总体积
						lostReportEntityResponse.setGoodsPackage(waybillEntity.getGoodsPackage()==null?null:waybillEntity.getGoodsPackage());//货物包装
						lostReportEntityResponse.setGoodsNum(waybillEntity.getGoodsQtyTotal()==null?null:Integer.toString(waybillEntity.getGoodsQtyTotal()));//货物总数量（件数）
						lostReportEntityResponse.setBillingTime(waybillEntity.getBillTime()==null?null:new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(waybillEntity.getBillTime()));//开单时间
						BigDecimal PrePayAmount= waybillEntity.getPrePayAmount();
						lostReportEntityResponse.setBillingFee(PrePayAmount==null?null:PrePayAmount.toString());//开单金额
						BigDecimal insuranceAmount= waybillEntity.getInsuranceAmount();
						lostReportEntityResponse.setSafeMoney(insuranceAmount==null?null:insuranceAmount.toString());//保价金额
						BigDecimal codAmount= waybillEntity.getCodAmount();
						lostReportEntityResponse.setAgentFee(codAmount==null?null:codAmount.toString());//代收货款
						lostReportEntityResponse.setReturnBillType(waybillInfoByWaybillNoReultDto.getReturnBillType()==null?null:waybillInfoByWaybillNoReultDto.getReturnBillType());//返单类型

						lostReportEntityResponse.setStorageTransport(waybillEntity.getTransportationRemark()==null?null:waybillEntity.getTransportationRemark());//储运方式
						lostReportEntityResponse.setLoseGoodsPackage(waybillEntity.getGoodsPackage()==null?null:waybillEntity.getGoodsPackage());//丢失货物包装
						lostReportEntityResponse.setRepScene(1);//上报场景
						lostReportEntityResponse.setRepType(11);//上报方式
						Date date =new Date();
						String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
						lostReportEntityResponse.setRepTime(dateStr);//上报时间
						//运单信息(获取开单(部门)组织名称)
						WaybillEntity waybill= waybillManagerService.queryWaybillBasicByNo(waybillNo);
						lostReportEntityResponse.setBillingDeptName(waybill.getCreateUserDeptName()==null?null:waybill.getCreateUserDeptName());//开单部门名称
						WaybillSignResultEntity wayEntity=new WaybillSignResultEntity();
						wayEntity.setActive(FossConstants.ACTIVE);//运单是否生效
						wayEntity.setWaybillNo(waybillNo);
						WaybillSignResultEntity wayEntityResult = waybillSignResultDao.queryWaybillSignResult(wayEntity);
						List<WayBillNoLocusDTO> wayBIllNoLocusList = new ArrayList<WayBillNoLocusDTO>();
						//运单轨迹（主要轨迹）--内部轨迹
						List<WayBillNoLocusDTO> wayBIllNoLocus = new ArrayList<WayBillNoLocusDTO>();
						wayBIllNoLocus = wayBillNoLocusService.getWayBillNoLocusForBse(waybillNo);
						//流水号（用于按件查询，PKP）
						List<LabeledGoodEntity> labeledGoodList = new ArrayList<LabeledGoodEntity>();
						labeledGoodList = labeledGoodService.queryAllSerialByWaybillNo(waybillNo);
						
						List<String> LoseFlowcode=new ArrayList<String>();//丢失流水号集合
						List<String> serialNoList=new ArrayList<String>();//流水号集合
						for(LabeledGoodEntity wayBillList:labeledGoodList){
							serialNoList.add(wayBillList.getSerialNo());
						} 
						List<LostReportEntityResponse> lostReportEntity=new ArrayList<LostReportEntityResponse>();
						
						if(wayEntityResult!=null && (SignConstants.SIGN_STATUS_ALL.equals(wayEntityResult.getSignStatus())
						  ||wayEntityResult!=null && SignConstants.SIGN_STATUS_PARTIAL.equals(wayEntityResult.getSignStatus()))){
							//1.1 全部签收记录
							if(SignConstants.SIGN_STATUS_ALL.equals(wayEntityResult.getSignStatus())){
								try{
									lostReportEntity.add(lostReportEntityResponse);
									String requestStr = JSONArray.fromObject(lostReportEntity).toString();
									//上报QMS，并解析返回的报文信息
									QMSToFOSSResponseService.getInstatce().reportWarningData(requestStr);
									}catch(Exception e){
										LOGGER.info("卸车找到丢货上报QMS失败");
									}
							//1.2 部分签收记录	
							}else if(SignConstants.SIGN_STATUS_PARTIAL.equals(wayEntityResult.getSignStatus())){
								String serialNoNum=null;
								Integer in=0;
								List<LostCodeEntityResponse> flowCodeList=new ArrayList<LostCodeEntityResponse>();
								List<InternalLocusEntityResponse> inLocusEntityList=new ArrayList<InternalLocusEntityResponse>();
								String serialNo=null;
								for(String serialNos:serialNoList){
									StockDto dto = new StockDto();
									dto.setWaybillNo(waybillNo);
									dto.setSerialNo(serialNos);
									dto.setActive(FossConstants.YES);
									dto.setDestroyed(FossConstants.NO);
									dto.setStatus(ArriveSheetConstants.STATUS_SIGN);
								//判断运单号的流水号是否签收
									String signTotalNo=signDetailDao.querySerialNoIsSign(dto);
									//如果返回值为NO 说明该流水号未签收
									if(signTotalNo.equals("N")){
										serialNo=serialNos;
										LoseFlowcode.add(serialNo);
										in+=1;
										if(in!=null){
											serialNoNum=String.valueOf(in);
										}
											LostCodeEntityResponse lostResponse =new LostCodeEntityResponse();
											lostResponse.setFlowCode(serialNo);
											flowCodeList.add(lostResponse);
											lostReportEntityResponse.setFlowCodeList(flowCodeList);//丢货流水号（未签收流水号）
											lostReportEntityResponse.setLoseNum(serialNoNum==null?null:serialNoNum);//丢货流水号数量
											wayBIllNoLocusList = wayBillNoLocusService.getWayBillNoLocusBySerialNo(waybillNo,serialNo);
											//根据运单号和流水号查询内部轨迹--按件查询
											for(WayBillNoLocusDTO ByWayBIllNoLocusLists:wayBIllNoLocusList){
												InternalLocusEntityResponse LocusResponse=new InternalLocusEntityResponse();
												LocusResponse.setByCurrentStatus(ByWayBIllNoLocusLists.getCurrentStatus()==null?null:ByWayBIllNoLocusLists.getCurrentStatus());
												LocusResponse.setByOperateOrgName(ByWayBIllNoLocusLists.getOperateOrgName()==null?null:ByWayBIllNoLocusLists.getOperateOrgName());
												LocusResponse.setByOperateName(ByWayBIllNoLocusLists.getOperateName()==null?null:ByWayBIllNoLocusLists.getOperateName());
												LocusResponse.setByOperateTypeName(ByWayBIllNoLocusLists.getOperateTypeName()==null?null:ByWayBIllNoLocusLists.getOperateTypeName());
												LocusResponse.setByOperateTime(ByWayBIllNoLocusLists.getOperateTime()==null?null:new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(ByWayBIllNoLocusLists.getOperateTime()));
												inLocusEntityList.add(LocusResponse);
											}
											lostReportEntityResponse.setInLocusEntityList(inLocusEntityList);
									}else{
										lostReportEntityResponse.setInLocusEntityList(null);
									}
							}
								try{
									lostReportEntity.add(lostReportEntityResponse);
									String requestStr = JSONArray.fromObject(lostReportEntity).toString();
									//上报QMS，并解析返回的报文信息
									QMSToFOSSResponseService.getInstatce().reportWarningData(requestStr);
								}catch(Exception e){
									LOGGER.info("卸车找到丢货上报QMS失败");
								}					
								}
								
							//1.1 未签收签收记录（如果未签收则签收结果为空）
						}else if(wayEntityResult==null){
							List<InLocusEntityResponse> LocusEntityLists=new ArrayList<InLocusEntityResponse>();
							//运单轨迹（主要轨迹）--内部轨迹
							for(WayBillNoLocusDTO wayBIllNoLocusLists:wayBIllNoLocus){
								InLocusEntityResponse inResponse =new InLocusEntityResponse(); 
								inResponse.setCurrentStatus(wayBIllNoLocusLists.getCurrentStatus()==null?null:wayBIllNoLocusLists.getCurrentStatus());//货物状态
								inResponse.setOperateOrgName(wayBIllNoLocusLists.getOperateOrgName()==null?null:wayBIllNoLocusLists.getOperateOrgName());//操作部门名称
								inResponse.setOperateName(wayBIllNoLocusLists.getOperateName()==null?null:wayBIllNoLocusLists.getOperateName());//操作人姓名 
								inResponse.setOperateTypeName(wayBIllNoLocusLists.getOperateTypeName()==null?null:wayBIllNoLocusLists.getOperateTypeName());//操作类型
								inResponse.setOperateTime(wayBIllNoLocusLists.getOperateTime()==null?null:new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(wayBIllNoLocusLists.getOperateTime()));//操作时间
								LocusEntityLists.add(inResponse);
							}
							lostReportEntityResponse.setLocusEntityList(LocusEntityLists);
							String serialNoNum=null;
							Integer in=0;
							List<LostCodeEntityResponse> flowCodeList=new ArrayList<LostCodeEntityResponse>();
							List<InternalLocusEntityResponse> inLocusEntityList=new ArrayList<InternalLocusEntityResponse>();
							String serialNo=null;
							for(String serialNos:serialNoList){
								StockDto dto = new StockDto();
								dto.setWaybillNo(waybillNo);
								dto.setSerialNo(serialNos);
								dto.setActive(FossConstants.YES);
								dto.setDestroyed(FossConstants.NO);//是否作废NO (未作废)
								dto.setStatus(ArriveSheetConstants.STATUS_SIGN);
								//判断运单号的流水号是否签收
								String signTotalNo=signDetailDao.querySerialNoIsSign(dto);
								//如果返回值为NO 说明该流水号未签收
								if(signTotalNo.equals("N")){
									serialNo=serialNos;
									LoseFlowcode.add(serialNo);
									in+=1;
									if(in!=null){
										serialNoNum=String.valueOf(in);
									}
									LostCodeEntityResponse lostResponse =new LostCodeEntityResponse();
									lostResponse.setFlowCode(serialNo);
									flowCodeList.add(lostResponse);
									lostReportEntityResponse.setFlowCodeList(flowCodeList);//丢货流水号（未签收流水号）
									lostReportEntityResponse.setLoseNum(serialNoNum==null?null:serialNoNum);//丢货流水号数量
								
									wayBIllNoLocusList = wayBillNoLocusService.getWayBillNoLocusBySerialNo(waybillNo,serialNo);
									for(WayBillNoLocusDTO ByWayBIllNoLocusLists:wayBIllNoLocusList){
										InternalLocusEntityResponse LocusResponse=new InternalLocusEntityResponse();

										LocusResponse.setByCurrentStatus(ByWayBIllNoLocusLists.getCurrentStatus()==null?null:ByWayBIllNoLocusLists.getCurrentStatus());
										LocusResponse.setByOperateOrgName(ByWayBIllNoLocusLists.getOperateOrgName()==null?null:ByWayBIllNoLocusLists.getOperateOrgName());
										LocusResponse.setByOperateName(ByWayBIllNoLocusLists.getOperateName()==null?null:ByWayBIllNoLocusLists.getOperateName());
										LocusResponse.setByOperateTypeName(ByWayBIllNoLocusLists.getOperateTypeName()==null?null:ByWayBIllNoLocusLists.getOperateTypeName());
										LocusResponse.setByOperateTime(ByWayBIllNoLocusLists.getOperateTime()==null?null:new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(ByWayBIllNoLocusLists.getOperateTime()));
										inLocusEntityList.add(LocusResponse);
									}
									lostReportEntityResponse.setInLocusEntityList(inLocusEntityList);
								}else{
									lostReportEntityResponse.setInLocusEntityList(null);
								}
								
							}
							try{
								lostReportEntity.add(lostReportEntityResponse);
								String requestStr = JSONArray.fromObject(lostReportEntity).toString();
								//上报QMS，并解析返回的报文信息
								QMSToFOSSResponseService.getInstatce().reportWarningData(requestStr);
								}catch(Exception e){
									LOGGER.info("卸车找到丢货上报QMS失败");
								}						
							}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new BusinessException(e.getMessage());
			}
		}

	public IWaybillSignResultDao getWaybillSignResultDao() {
		return waybillSignResultDao;
	}

	public void setWaybillSignResultDao(IWaybillSignResultDao waybillSignResultDao) {
		this.waybillSignResultDao = waybillSignResultDao;
	}

	public WaybillQueryForBseService getWaybillQueryForBseService() {
		return waybillQueryForBseService;
	}

	public void setWaybillQueryForBseService(
			WaybillQueryForBseService waybillQueryForBseService) {
		this.waybillQueryForBseService = waybillQueryForBseService;
	}

	public IWaybillQueryService getWaybillQueryService() {
		return waybillQueryService;
	}

	public void setWaybillQueryService(IWaybillQueryService waybillQueryService) {
		this.waybillQueryService = waybillQueryService;
	}

	public IWayBillNoLocusService getWayBillNoLocusService() {
		return wayBillNoLocusService;
	}

	public void setWayBillNoLocusService(
			IWayBillNoLocusService wayBillNoLocusService) {
		this.wayBillNoLocusService = wayBillNoLocusService;
	}

	public IWaybillManagerService getWaybillManagerService() {
		return waybillManagerService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public ISignDetailService getSignDetailService() {
		return signDetailService;
	}

	public void setSignDetailService(ISignDetailService signDetailService) {
		this.signDetailService = signDetailService;
	}

	public IIntegrativeQueryService getIntegrativeQueryService() {
		return integrativeQueryService;
	}

	public void setIntegrativeQueryService(
			IIntegrativeQueryService integrativeQueryService) {
		this.integrativeQueryService = integrativeQueryService;
	}

	public IWaybillDao getWaybillDao() {
		return waybillDao;
	}

	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}

	public IWaybillSignResultService getWaybillSignResultService() {
		return waybillSignResultService;
	}

	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	public ISignDetailDao getSignDetailDao() {
		return signDetailDao;
	}

	public void setSignDetailDao(ISignDetailDao signDetailDao) {
		this.signDetailDao = signDetailDao;
	}

	public ILabeledGoodService getLabeledGoodService() {
		return labeledGoodService;
	}

	public void setLabeledGoodService(ILabeledGoodService labeledGoodService) {
		this.labeledGoodService = labeledGoodService;
	}
}
































