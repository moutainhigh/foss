package com.deppon.foss.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;




import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.server.service.impl.ProductService;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillQueryService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.CommWaybillCheckStatus;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WayBillDetailForQms;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillCheckStatus;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillCheckStatusListforQms;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDetaillListEntityforQms;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillProInfo;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillProInfoListforQms;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.flowcodeAreaForQms;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillInfoDto;
import com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.util.CollectionUtils;


/**
 * rest风格接口（供QMS调用）
 * @author 065340-foss-liutao
 * @date 2015-07-08 
 */

//请求数据格式为json
@Consumes({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN })
//响应数据格式为json
@Produces("application/json;charset=UTF-8") 
public class QmsWaybillServiceImpl{
	/**
     * 日志
     */
	private static final Logger LOGGER = LoggerFactory.getLogger(QmsWaybillServiceImpl.class.getName());
	/**
	 *查询运单详情
	 */
	private IWaybillQueryService waybillQueryService;
	public void setWaybillQueryService(IWaybillQueryService waybillQueryService) {
		this.waybillQueryService = waybillQueryService;
	}
	/**
	 * 查询运单
	 */
	private IWaybillManagerService waybillManagerService;
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	private IWaybillExpressService waybillExpressService;
	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}
	/**
	 * 查询运单实际承运信息
	 * */
    private IActualFreightService  actualFreightService;
	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}
	/**
	 * 产品服务类
	 * */
	private ProductService productService;
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	/**
	 * 数据字典
	 */
	private IDataDictionaryValueService dataDictionaryValueService;
	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}
	/**
	 * 货签信息
	 * */
	private ILabeledGoodDao labeledGoodDao;
	public void setLabeledGoodDao(ILabeledGoodDao labeledGoodDao) {
		this.labeledGoodDao = labeledGoodDao;
	}
	/**
	 * 库区信息
	 * */
	private IStockDao stockDao;
	public void setStockDao(IStockDao stockDao) {
		this.stockDao = stockDao;
	}
	/**
	 * 更改单
	 * */
	private IWaybillRfcDao waybillRfcDao;
	public void setWaybillRfcDao(IWaybillRfcDao waybillRfcDao) {
		this.waybillRfcDao = waybillRfcDao;
	}
	/**
	 * 组织
	 * */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 * @author foss-LiuTao
	 * @date 2015-07-01下午7:44:25
	 * MCEW-丢货预警接口需求-丢货上报，输入运单号，调用FOSS接口，并返回结果
	 * */
	@GET
	@Path("/waybilldetail")
	public WaybillDetaillListEntityforQms waybilldetail(
			@QueryParam("waybillNo") String waybillNo,
			@Context HttpServletResponse response){
	    //运单信息集合
		List<WayBillDetailForQms> wayBillDetailForQmsList = new ArrayList<WayBillDetailForQms> ();
	   //ESB运单信息
		WayBillDetailForQms wayBillDetailForQms = new WayBillDetailForQms();
		//运单详情
		WaybillDetaillListEntityforQms waybillDetailInfo=new WaybillDetaillListEntityforQms();
		SimpleDateFormat pdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//判断传入运单号是否为空
		if(StringUtils.isNotEmpty(waybillNo)){
			//判断运单号是否已开单
			if(waybillManagerService.isWayBillExsits(waybillNo.trim())){
				//判断运单号是否存在承运信息
				ActualFreightEntity actualFreightEntity =actualFreightService.queryByWaybillNo(waybillNo.trim());
				if(actualFreightEntity!=null){
			    //将运单号封装成运单集合
				List<String> waybillList =new  ArrayList<String>();
				waybillList.add(waybillNo);
				//查询运单详情
				List<WaybillInfoDto> waybillInfoDtoList = waybillQueryService.queryWaybillInfo(waybillList);
				if(waybillInfoDtoList!=null && waybillInfoDtoList.size()!=0){
			   //遍历运单详情
				for(WaybillInfoDto waybillInfo : waybillInfoDtoList){
					if(waybillInfo!=null){
						//设置运单号
						wayBillDetailForQms.setWaybillNum(waybillInfo.getWaybillNo());
						//收货部门编码
						wayBillDetailForQms.setTakeOverDeptCode(waybillInfo.getReceiveOrgCode());
						//收货部门名称
						wayBillDetailForQms.setTakeOverDeptName(waybillInfo.getReceiveOrgName());
						//到达部门编码
						wayBillDetailForQms.setArriveDeptCode(waybillInfo.getCustomerPickupOrgCode());
						//到达部门名称
						wayBillDetailForQms.setArriveDeptName(waybillInfo.getLadingStationName());
						//运输性质
						ProductEntity productEntity =productService.getProductByCache(waybillInfo.getProductCode(),waybillInfo.getBillTime());
						if(null!=productEntity){
							wayBillDetailForQms.setTransNature(productEntity.getName());
						}else{
							wayBillDetailForQms.setTransNature("");
						}
						//发货客户名称
						wayBillDetailForQms.setSendClientName(waybillInfo.getDeliveryCustomerName());
						//发货客户编码
						wayBillDetailForQms.setSendClientCode(waybillInfo.getDeliveryCustomerCode());
						//收货客户名称
						wayBillDetailForQms.setTakeOverClientName(waybillInfo.getReceiveCustomerName());
						//收货客户编码
						wayBillDetailForQms.setTakeOverClientCode(waybillInfo.getReceiveCustomerCode());
						//业务性质  1  零担 2快递
						wayBillDetailForQms.setRepScene("1");
						//提货方式名称
						if(null!=waybillInfo.getReceiveMethod()){
							if (StringUtils.equals(waybillInfo.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT)
									|| StringUtils.equals(waybillInfo.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_C2_C20004)) {
								String termsCodeAir="PICKUPGOODSAIR";
									DataDictionaryValueEntity entityAir=dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(termsCodeAir,waybillInfo.getReceiveMethod());
		                            if(null!=entityAir){
		                            	wayBillDetailForQms.setPickUpType(entityAir.getValueName());
		                            }else{
		                            	wayBillDetailForQms.setPickUpType("");
		                            }
							}else if(StringUtils.equals(waybillInfo.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE)
									|| StringUtils.equals(waybillInfo.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE)
									|| StringUtils.equals(waybillInfo.getProductCode(), ProductEntityConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE)
									|| StringUtils.equals(waybillInfo.getProductCode(), ProductEntityConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT)){
								wayBillDetailForQms.setRepScene("2");
								String termsCodeKd="KD_PICKUPGOODSHIGHWAYS";
									DataDictionaryValueEntity entityKd=dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(termsCodeKd,waybillInfo.getReceiveMethod());
		                            if(null!=entityKd){
		                            	wayBillDetailForQms.setPickUpType(entityKd.getValueName());
		                            }else{
		                            	wayBillDetailForQms.setPickUpType("");
		                            }
							}else{
								String termsCodeHw="PICKUPGOODSHIGHWAYS";
								DataDictionaryValueEntity entityHw=dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(termsCodeHw,waybillInfo.getReceiveMethod());
							    if(null!=entityHw){
	                            	wayBillDetailForQms.setPickUpType(entityHw.getValueName());
	                            }else{
	                            	wayBillDetailForQms.setPickUpType("");
	                            }
							}
						}else{
							wayBillDetailForQms.setPickUpType("");
						}					
						
						//付款方式名称
						String termsCode="SETTLEMENT__PAYMENT_TYPE";
						if(null!=waybillInfo.getPaidMethod()){
							DataDictionaryValueEntity entity=dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(termsCode,waybillInfo.getPaidMethod());
                            if(null!=entity){
                            	wayBillDetailForQms.setPayType(entity.getValueName());
                            }else{
                            	wayBillDetailForQms.setPayType("");
                            }
						}else{
							wayBillDetailForQms.setPayType("");
						}
						//发货客户手机
						wayBillDetailForQms.setSendClientMobile(waybillInfo.getDeliveryCustomerMobilephone());
						//开单部门编码
						wayBillDetailForQms.setBillingDeptCode(waybillInfo.getCreateOrgCode());
						if(null!=waybillInfo.getCreateOrgCode()){
							OrgAdministrativeInfoEntity orgAdministrativeInfoEntity=orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(waybillInfo.getCreateOrgCode());
						    if(null!=orgAdministrativeInfoEntity){
						    	//开单部门名称
								wayBillDetailForQms.setBillingDeptName(orgAdministrativeInfoEntity.getName());
						    }else{
						    	wayBillDetailForQms.setBillingDeptName("");
						    }
						}else{
							wayBillDetailForQms.setBillingDeptName("");
						}
						
						//货物名称
						wayBillDetailForQms.setGoodsName(waybillInfo.getGoodsName());
						//货物重量
						wayBillDetailForQms.setWeight(waybillInfo.getGoodsWeightTotal().toString());
						//货物体积
						wayBillDetailForQms.setVolume(waybillInfo.getGoodsVolumeTotal().toString());
						//货物包装
						wayBillDetailForQms.setGoodsPackage(waybillInfo.getGoodsPackage());
						//货物件数
						wayBillDetailForQms.setGoodsNum(waybillInfo.getGoodsQtyTotal().toString());
						//开单时间
						wayBillDetailForQms.setBillingTime(pdf.format(waybillInfo.getBillTime()));
						//开单金额
						wayBillDetailForQms.setBillingFee((waybillInfo.getTotalFee().subtract(waybillInfo.getCodAmount()).subtract(waybillInfo.getServiceFee())).toString());
						//保价声明价值
						wayBillDetailForQms.setSafeMoney(waybillInfo.getInsuranceAmount().toString());
						//代收货款金额
						if(null!=waybillInfo.getCodAmount()&& waybillInfo.getCodAmount().compareTo(new BigDecimal("0"))>=0){
							wayBillDetailForQms.setAgentFee(waybillInfo.getCodAmount().toString());
						}else{
							wayBillDetailForQms.setAgentFee("");
						}
						wayBillDetailForQms.setAgentFee(waybillInfo.getCodAmount().toString());
						//返单类型  
						if(StringUtils.equals(waybillInfo.getReturnBillType(), "FAX")){
							wayBillDetailForQms.setReturnBillType("客户签收单传真返回 ");
						}else if(StringUtils.equals(waybillInfo.getReturnBillType(), "ORIGINAL")){
							wayBillDetailForQms.setReturnBillType("客户签收单原件返回 ");
						}else if(StringUtils.equals(waybillInfo.getReturnBillType(), "ARRIVESHEET_FAX")){
							wayBillDetailForQms.setReturnBillType("运单到达联传真返回");
						}else if(StringUtils.equals(waybillInfo.getReturnBillType(), "NONE")){
							wayBillDetailForQms.setReturnBillType("无需返单");
						}else{
							wayBillDetailForQms.setReturnBillType(waybillInfo.getReturnBillType());
						}
						//储运事项
						if(null!=waybillInfo.getTransportationRemark()){
							wayBillDetailForQms.setStorageTransport(waybillInfo.getTransportationRemark());
						}else{
							wayBillDetailForQms.setStorageTransport("");
						}
						
						//流水号
						List<LabeledGoodEntity> labeledGoodEntitys =labeledGoodDao.queryAllSerialByWaybillNo(waybillInfo.getWaybillNo());
						String serialNos="";
						if(null!=labeledGoodEntitys && labeledGoodEntitys.size()>0){
							for(int i=0;i<labeledGoodEntitys.size();i++){
								if(i==labeledGoodEntitys.size()-1){
								   serialNos=serialNos+labeledGoodEntitys.get(i).getSerialNo();
								}else{
								   serialNos=serialNos+labeledGoodEntitys.get(i).getSerialNo()+",";
								}
							}
						}
						wayBillDetailForQms.setLoseFlowcode(serialNos);
						//丢失货物包装
						String GoodsQtyTotal=waybillInfo.getGoodsQtyTotal().toString();
						String pack=toFowwingString(waybillInfo.getGoodsPackage());
						if(!pack.equals("")){
						 if(StringUtils.equals(GoodsQtyTotal, pack)){
								wayBillDetailForQms.setLoseGoodsPackage(waybillInfo.getGoodsPackage());
							}else{
								wayBillDetailForQms.setLoseGoodsPackage("");
							}
						}else{
							wayBillDetailForQms.setLoseGoodsPackage(waybillInfo.getGoodsPackage());
						}
						
						//流水号所在货区
						List<flowcodeAreaForQms> flowcodeAreas=new ArrayList<flowcodeAreaForQms>();
						if(null!=labeledGoodEntitys && labeledGoodEntitys.size()>0){
							for(LabeledGoodEntity labeledGoodEntity:labeledGoodEntitys){
								flowcodeAreaForQms flowcodeArea=new flowcodeAreaForQms();
								List<StockEntity> stockEntity=stockDao.queryStockAndGoodsArea(waybillInfo.getWaybillNo(),labeledGoodEntity.getSerialNo());
								if(null!=stockEntity&& stockEntity.size()==1){
									flowcodeArea.setLoseFlowcode(labeledGoodEntity.getSerialNo());
									if(null!=stockEntity.get(0).getGoodsAreaCode()){
									  flowcodeArea.setGoodsAreaCode(stockEntity.get(0).getGoodsAreaCode());
									}else{
										flowcodeArea.setGoodsAreaCode("");
									}
									if(null!=stockEntity.get(0).getGoodsAreaName()){
										flowcodeArea.setGoodsAreaName(stockEntity.get(0).getGoodsAreaName());
									}else{
										flowcodeArea.setGoodsAreaName("");
									}
									flowcodeAreas.add(flowcodeArea);
									flowcodeAreas.add(new flowcodeAreaForQms());
								}
								else if(null!=stockEntity&& stockEntity.size()>1){
									flowcodeArea.setLoseFlowcode(labeledGoodEntity.getSerialNo());
									if(null!=stockEntity.get(0).getGoodsAreaCode()){
									  flowcodeArea.setGoodsAreaCode(stockEntity.get(0).getGoodsAreaCode());
									}else{
										flowcodeArea.setGoodsAreaCode("");
									}
									if(null!=stockEntity.get(0).getGoodsAreaName()){
										flowcodeArea.setGoodsAreaName(stockEntity.get(0).getGoodsAreaName());
									}else{
										flowcodeArea.setGoodsAreaName("");
									}
									flowcodeAreas.add(flowcodeArea);
								}else{
									flowcodeArea.setLoseFlowcode(labeledGoodEntity.getSerialNo());
									flowcodeArea.setGoodsAreaCode("");
									flowcodeArea.setGoodsAreaName("");
									flowcodeAreas.add(flowcodeArea);
									flowcodeAreas.add(new flowcodeAreaForQms());
								}
							}
						}
						wayBillDetailForQms.setFlowcodeAreasForQms(flowcodeAreas);
						wayBillDetailForQmsList.add(wayBillDetailForQms);
						LOGGER.info("-- 运单查询---- "+ReflectionToStringBuilder.toString(wayBillDetailForQms));
					}	
				}
				  //消息代码成功为“00000”  获取运单信息成功
				  waybillDetailInfo.setMessage_code("00000");
				  //消息代码说明
				  waybillDetailInfo.setMessage_detail("Gets the waybill information is success");
				  //返回记录的条数
				  waybillDetailInfo.setCount(waybillInfoDtoList.size());
				  //处理明细
				  waybillDetailInfo.setDetail(wayBillDetailForQmsList);
				  response.setHeader("ESB-ResultCode","1");
				  return waybillDetailInfo;
				}else{
					//消息代码失败为“10000”  获取运单信息失败，运单信息为空
					waybillDetailInfo.setMessage_code("10000");
					//消息代码说明
					waybillDetailInfo.setMessage_detail("Gets the waybill information is failure,information is null");
					//返回记录的条数
					waybillDetailInfo.setCount(0);
					//处理明细
					waybillDetailInfo.setDetail(null);
					response.setHeader("ESB-ResultCode","1");
					return waybillDetailInfo;
				}	
			}else{
				//消息代码失败为“10000” 运单承运信息为空
				waybillDetailInfo.setMessage_code("20000");
				//消息代码说明
				waybillDetailInfo.setMessage_detail("Waybill ActualFreight information is null");
				//返回记录的条数
				waybillDetailInfo.setCount(0);
				//处理明细
				waybillDetailInfo.setDetail(null);
				response.setHeader("ESB-ResultCode","1");
				return waybillDetailInfo;
			}
		}else{
           //消息代码失败为“20000”   运单未开单
           waybillDetailInfo.setMessage_code("30000");
           //消息代码说明
           waybillDetailInfo.setMessage_detail("Waybill without billing");
          //返回记录的条数
          waybillDetailInfo.setCount(0);
          //处理明细
          waybillDetailInfo.setDetail(null);
          response.setHeader("ESB-ResultCode","1");
          return waybillDetailInfo;
		}
	}else{
		//消息代码失败为“30000”   运单号为空或无法从QMS获取运单号
		waybillDetailInfo.setMessage_code("40000");
		//消息代码说明
		waybillDetailInfo.setMessage_detail("waybill number is null or FOSS can't get the waybill number from APP");
		//返回记录的条数
		waybillDetailInfo.setCount(0);
		//处理明细
		waybillDetailInfo.setDetail(null);	
		response.setHeader("ESB-ResultCode","1");
		return waybillDetailInfo;
	}
  }
	/**
	 * @author foss-LiuTao
	 * @date 2015-07-01下午7:44:25
	 * MCEW-丢货预警接口需求-检查运单号、流水号是否处于可上报状态
	 * */
	@GET
	@Path("/checkWaybillStatus")
	public WaybillCheckStatusListforQms checkWaybillStatus(
			@QueryParam("waybillNo") String waybillNo,
			@QueryParam("srialNo") String srialNos,
			@Context HttpServletResponse response){
	    //返回信息集合
		WaybillCheckStatusListforQms waybillCheckStatusListforQms=new WaybillCheckStatusListforQms();
	    //运单是否有效集合
		List<WaybillCheckStatus> waybillCheckStatusList = new ArrayList<WaybillCheckStatus> ();
		//返回实体
		WaybillCheckStatus waybillCheckstatus=null;
		//判断传入运单号是否为空
			if(StringUtils.isNotEmpty(waybillNo) && StringUtils.isNotEmpty(srialNos) && srialNos.length()>0){
					//判断运单号是否已开单
				if(waybillManagerService.isWayBillExsits(waybillNo.trim())){
					//判断运单号是否存在承运信息
					ActualFreightEntity actualFreightEntity =actualFreightService.queryByWaybillNo(waybillNo.trim());
					if(null!=actualFreightEntity){
						String [] srialNo=srialNos.split("-");
						for(int i=0;i<srialNo.length;i++){
						String srial =srialNo[i];
						WaybillCheckStatus waybillCheckstatusavail=new WaybillCheckStatus();
						//流水号
						List<StockEntity> stockEntity=stockDao.queryStockAndGoodsArea(waybillNo,srial);
						if(null!=stockEntity && stockEntity.size()>0){
							waybillCheckstatusavail.setWaybillNo(waybillNo);
							waybillCheckstatusavail.setSrialNo(srial);
							waybillCheckstatusavail.setWaybillStatusCode("Y");
							waybillCheckstatusavail.setSrialNoStatusCode("Y");
							if(stockEntity.get(0).getOrgCode().equals("W01000301050203") || stockEntity.get(0).getOrgCode().equals("W0000002728")){
								waybillCheckstatusavail.setSrialNoIsspecialStock("Y");
							}else{
								waybillCheckstatusavail.setSrialNoIsspecialStock("N");
							}
							waybillCheckStatusList.add(waybillCheckstatusavail);
						}else{
							waybillCheckstatusavail.setWaybillNo(waybillNo);
							waybillCheckstatusavail.setSrialNo(srial);
							waybillCheckstatusavail.setWaybillStatusCode("Y");
							waybillCheckstatusavail.setSrialNoStatusCode("N");
							waybillCheckstatusavail.setSrialNoIsspecialStock("N");
							waybillCheckStatusList.add(waybillCheckstatusavail);
						}
					  }
						//消息代码失败为“30000”   运单号有效
						waybillCheckStatusListforQms.setMessage_code("30000");
						//消息代码说明
						waybillCheckStatusListforQms.setMessage_detail("waybillNo is avail");
						//处理明细
						if(null!=waybillCheckStatusList&&waybillCheckStatusList.size()==1){
							waybillCheckStatusList.add(new WaybillCheckStatus());
//						   waybillCheckStatus=(WaybillCheckStatus[]) waybillCheckStatusList.toArray(new WaybillCheckStatus[waybillCheckStatusList.size()]);
						   waybillCheckStatusListforQms.setDetail(waybillCheckStatusList);
					    }else if(null!=waybillCheckStatusList&&waybillCheckStatusList.size()>1){
//					       waybillCheckStatus=(WaybillCheckStatus[]) waybillCheckStatusList.toArray(new WaybillCheckStatus[waybillCheckStatusList.size()]);
						   waybillCheckStatusListforQms.setDetail(waybillCheckStatusList);
					    }
						else{
					    	waybillCheckStatusListforQms.setDetail(null);
					    }
						response.setHeader("ESB-ResultCode","1");
						return waybillCheckStatusListforQms;
					}else{
						waybillCheckstatus=new WaybillCheckStatus();
						waybillCheckstatus.setWaybillNo(waybillNo);
						waybillCheckstatus.setSrialNo(srialNos);
						waybillCheckstatus.setWaybillStatusCode("N");
						waybillCheckstatus.setSrialNoStatusCode("N");
						waybillCheckstatus.setSrialNoIsspecialStock("N");
						waybillCheckStatusList.add(waybillCheckstatus);
						//消息代码失败为“20000”   运单号无效
						waybillCheckStatusListforQms.setMessage_code("20000");
						//消息代码说明
						waybillCheckStatusListforQms.setMessage_detail("waybillNo  is noavail");
						//处理明细
						if(null!=waybillCheckStatusList&&waybillCheckStatusList.size()==1){
							waybillCheckStatusList.add(new WaybillCheckStatus());
//						   waybillCheckStatus=(WaybillCheckStatus[]) waybillCheckStatusList.toArray(new WaybillCheckStatus[waybillCheckStatusList.size()]);
						   waybillCheckStatusListforQms.setDetail(waybillCheckStatusList);
					    }else if(null!=waybillCheckStatusList&&waybillCheckStatusList.size()>1){
//					       waybillCheckStatus=(WaybillCheckStatus[]) waybillCheckStatusList.toArray(new WaybillCheckStatus[waybillCheckStatusList.size()]);
						   waybillCheckStatusListforQms.setDetail(waybillCheckStatusList);
					    }
						else{
					    	waybillCheckStatusListforQms.setDetail(null);
					    }
						response.setHeader("ESB-ResultCode","1");
						return waybillCheckStatusListforQms;
					}
			  }else{
					waybillCheckstatus=new WaybillCheckStatus();
					waybillCheckstatus.setWaybillNo(waybillNo);
					waybillCheckstatus.setSrialNo(srialNos);
					waybillCheckstatus.setWaybillStatusCode("N");
					waybillCheckstatus.setSrialNoStatusCode("N");
					waybillCheckstatus.setSrialNoIsspecialStock("N");
					waybillCheckStatusList.add(waybillCheckstatus);
					//消息代码失败为“20000”   运单号无效
					waybillCheckStatusListforQms.setMessage_code("20000");
					//消息代码说明
					waybillCheckStatusListforQms.setMessage_detail("waybillNo  is noavail");
					//处理明细
					if(null!=waybillCheckStatusList&&waybillCheckStatusList.size()==1){
					   waybillCheckStatusList.add(new WaybillCheckStatus());
//					   waybillCheckStatus=(WaybillCheckStatus[]) waybillCheckStatusList.toArray(new WaybillCheckStatus[waybillCheckStatusList.size()]);
					   waybillCheckStatusListforQms.setDetail(waybillCheckStatusList);
				    }else if(null!=waybillCheckStatusList&&waybillCheckStatusList.size()>1){
//				       waybillCheckStatus=(WaybillCheckStatus[]) waybillCheckStatusList.toArray(new WaybillCheckStatus[waybillCheckStatusList.size()]);
					   waybillCheckStatusListforQms.setDetail(waybillCheckStatusList);
				    }
					else{
				    	waybillCheckStatusListforQms.setDetail(null);
				    }
					response.setHeader("ESB-ResultCode","1");
					return waybillCheckStatusListforQms;
			}
		}else{
		//消息代码失败为“10000”   运单号或流水号为空
		waybillCheckStatusListforQms.setMessage_code("10000");
		//消息代码说明
		waybillCheckStatusListforQms.setMessage_detail("waybillNo or SrialNo is null or FOSS can't get the waybill number from QMS");
		//处理明细
		waybillCheckStatusListforQms.setDetail(null);	
		response.setHeader("ESB-ResultCode","1");
		return waybillCheckStatusListforQms;
	}
  }
	/**
	 * @author foss-LiuTao
	 * @date 2015-07-01下午7:44:25
	 * MCEW-丢货预警接口需求-获取运单号的操作状态信息
	 * */
	@GET
	@Path("/findWaybillProInfo")
	public WaybillProInfoListforQms findWaybillProInfo(
			@QueryParam("waybillNo") String waybillNo,
			@QueryParam("srialNo") String srialNos,
			@Context HttpServletResponse response){
	    //返回信息集合
		WaybillProInfoListforQms waybillProInfoListforQms=new WaybillProInfoListforQms();
		List<WaybillProInfo> waybillProInfoList = new ArrayList<WaybillProInfo> ();
		//返回实体
		WaybillProInfo waybillProInfo=null;
		SimpleDateFormat pdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//判断传入运单号是否为空
			if(StringUtils.isNotEmpty(waybillNo) && StringUtils.isNotEmpty(srialNos) && srialNos.length()>0){
					//判断运单号是否已开单
//				if(waybillManagerService.isWayBillExsits(waybillNo.trim())){
					//判断运单号是否存在承运信息
					ActualFreightEntity actualFreightEntity =actualFreightService.queryByWaybillNo(waybillNo.trim());
					//将运单号封装成运单集合
					List<String> waybillList =new  ArrayList<String>();
					waybillList.add(waybillNo);
					if(null!=actualFreightEntity){
						//查询出发更改单详情
						List<WaybillRfcEntity> waybillRfcEntitys=waybillRfcDao.queryWaybillRfcAcceptByWaybillNo(waybillNo,WaybillRfcConstants.ACCECPT);
						if(null!=waybillRfcEntitys){
							for(WaybillRfcEntity waybillRfcEntity:waybillRfcEntitys){
								if(waybillRfcEntity.getRfcType().equals(WaybillRfcConstants.INVALID)){
									//作废
									waybillProInfo=new WaybillProInfo();
									srialNos=srialNos.replace("-", ",");
									waybillProInfo.setSrialNo(srialNos);
									waybillProInfo.setOperateUserCode(waybillRfcEntity.getOperatorCode());
									waybillProInfo.setOperateUserName(waybillRfcEntity.getOperator());
									waybillProInfo.setOperateOrgName(waybillRfcEntity.getOperateOrgName());
									waybillProInfo.setOperateOrgCode(waybillRfcEntity.getOperateOrgCode());
									waybillProInfo.setOperateReason(waybillRfcEntity.getRfcReason());
									waybillProInfo.setOperateTime(pdf.format(waybillRfcEntity.getOperateTime()));
									waybillProInfoList.add(waybillProInfo);
									//消息代码失败为“40000”   作废
									waybillProInfoListforQms.setMessage_code("40000");
									//消息代码说明
									waybillProInfoListforQms.setMessage_detail("waybillNo  is invalid ");
									//处理明细
									if(null!=waybillProInfoList&&waybillProInfoList.size()==1){
										waybillProInfoList.add(new WaybillProInfo());
										waybillProInfoListforQms.setDetail(waybillProInfoList);
									    }else if(null!=waybillProInfoList&&waybillProInfoList.size()>1){
									    	waybillProInfoListforQms.setDetail(waybillProInfoList);
									    }
										else{
											waybillProInfoListforQms.setDetail(null);
									    }
									response.setHeader("ESB-ResultCode","1");
									return waybillProInfoListforQms;
								}else if(waybillRfcEntity.getRfcType().equals(WaybillRfcConstants.ABORT)){
									//终止
									waybillProInfo=new WaybillProInfo();
									srialNos=srialNos.replace("-", ",");
									waybillProInfo.setSrialNo(srialNos);
									waybillProInfo.setOperateUserCode(waybillRfcEntity.getOperatorCode());
									waybillProInfo.setOperateUserName(waybillRfcEntity.getOperator());
									waybillProInfo.setOperateOrgName(waybillRfcEntity.getOperateOrgName());
									waybillProInfo.setOperateOrgCode(waybillRfcEntity.getOperateOrgCode());
									waybillProInfo.setOperateReason(waybillRfcEntity.getRfcReason());
									waybillProInfo.setOperateTime(pdf.format(waybillRfcEntity.getOperateTime()));
									waybillProInfoList.add(waybillProInfo);
									//消息代码失败为“50000”   终止
									waybillProInfoListforQms.setMessage_code("50000");
									//消息代码说明
									waybillProInfoListforQms.setMessage_detail("waybillNo  is abort ");
									//处理明细
									if(null!=waybillProInfoList&&waybillProInfoList.size()==1){
										waybillProInfoList.add(new WaybillProInfo());
										waybillProInfoListforQms.setDetail(waybillProInfoList);
									    }else if(null!=waybillProInfoList&&waybillProInfoList.size()>1){
									    	waybillProInfoListforQms.setDetail(waybillProInfoList);
									    }
										else{
											waybillProInfoListforQms.setDetail(null);
									    }
									response.setHeader("ESB-ResultCode","1");
									return waybillProInfoListforQms;
								}
							}
							
							if(waybillManagerService.isWayBillExsits(waybillNo.trim())){
									//消息代码失败为“60000”   其他情况不做处理
									waybillProInfoListforQms.setMessage_code("60000");
									//消息代码说明
									waybillProInfoListforQms.setMessage_detail("waybil  is no abort and no invalid");
									//处理明细
									waybillProInfoListforQms.setDetail(null);
									response.setHeader("ESB-ResultCode","1");
									return waybillProInfoListforQms;
							}else{
								//消息代码失败为“20000”   运单号无效
							    waybillProInfoListforQms.setMessage_code("20000");
								//消息代码说明
							    waybillProInfoListforQms.setMessage_detail("waybillNo  is noavail");
								//处理明细
							    waybillProInfoListforQms.setDetail(null);
								response.setHeader("ESB-ResultCode","1");
								return waybillProInfoListforQms;
							}
						}else{
							//消息代码失败为“30000”   无更改记录
							waybillProInfoListforQms.setMessage_code("30000");
							//消息代码说明
							waybillProInfoListforQms.setMessage_detail("waybil  is norfc");
							//处理明细
							waybillProInfoListforQms.setDetail(null);
							response.setHeader("ESB-ResultCode","1");
							return waybillProInfoListforQms;
						}
					}else{
						//消息代码失败为“20000”   运单号无效
						waybillProInfoListforQms.setMessage_code("20000");
						//消息代码说明
						waybillProInfoListforQms.setMessage_detail("waybillNo  is noavail");
						//处理明细
						waybillProInfoListforQms.setDetail(null);
						response.setHeader("ESB-ResultCode","1");
						return waybillProInfoListforQms;
					}
//			  }
//				else{
//					//消息代码失败为“20000”   运单号无效
//				    waybillProInfoListforQms.setMessage_code("20000");
//					//消息代码说明
//				    waybillProInfoListforQms.setMessage_detail("waybillNo  is noavail");
//					//处理明细
//				    waybillProInfoListforQms.setDetail(null);
//					response.setHeader("ESB-ResultCode","1");
//					return waybillProInfoListforQms;
//			}
		}else{
		//消息代码失败为“10000”   运单号或流水号为空
			waybillProInfoListforQms.setMessage_code("10000");
		//消息代码说明
			waybillProInfoListforQms.setMessage_detail("waybillNo or SrialNo is null or FOSS can't get the waybill number from QMS");
		//处理明细
			waybillProInfoListforQms.setDetail(null);	
		response.setHeader("ESB-ResultCode","1");
		return waybillProInfoListforQms;
	}
  }
	
	/**
	 * 此接口专门用于判断官网、触屏官网、微信、APP的原单号关联单号的类型
	 * 
	 * @author 280747 - zhuxue
	 * @date 2016-01-20
	 */
	/**
	 * @param waybillNo
	 * @param response
	 * @return
	 */
	@POST
	@Path("/waybillReturnType")
	public Object waybillReturnType(
			@RequestBody String requestBody,@Context HttpServletResponse response) {
		JSONObject jsonObject = JSONObject.parseObject(requestBody);
		String waybillNo = jsonObject.getString("waybillNo");
		// 返回信息集合
		CommWaybillCheckStatus commWaybillCheckStatus = new CommWaybillCheckStatus();
		// 查询快递运单表实体类
		WaybillExpressEntity Waybill = new WaybillExpressEntity();

		// ESB提供运单集合
		List<String> waybillList = new ArrayList();

		// 判断是否是原单号查询ni
		String hasReturnType = null;
        //如果单号不为空就进行查询
		if (!"".equals(waybillNo) && StringUtils.isNotEmpty(waybillNo)) {
			// 定义单号的类型
			String Status = null;

			waybillList.add(waybillNo);

			List<WaybillInfoDto> waybillInfoDtoList = waybillQueryService
					.queryWaybillInfo(waybillList);

			if (CollectionUtils.isNotEmpty(waybillInfoDtoList)) {
				for (WaybillInfoDto infoDto : waybillInfoDtoList) {
					if (StringUtils.isNotEmpty(infoDto.getWaybillNo())) {
						// 如果原单号为空就根据返货单号查询
						if (StringUtils.isEmpty(infoDto.getOriginalWaybillNo())) {
							WaybillExpressEntity entity = waybillExpressService
									.queryWaybillExpressByNo(infoDto
											.getWaybillNo());
							if (entity != null
									&& StringUtils.isNotEmpty(entity
											.getOriginalWaybillNo())) {
								hasReturnType = "true";
								if ("RETURN_WAYBILL".equals(entity
										.getReturnType())
										&& StringUtils.isNotEmpty(entity
												.getReturnType())) {
									Status = "签收单返回单号";
									commWaybillCheckStatus
											.setReturnType(Status);
									commWaybillCheckStatus.setWaybillNo(entity
											.getWaybillNo());
									commWaybillCheckStatus
											.setOriginalWaybillNo(entity
													.getOriginalWaybillNo());
									commWaybillCheckStatus
											.setHasReturnType(hasReturnType);
								} else if (("RETURN_CARGO".equals(entity
										.getReturnType()) || ("RETURN_PIECE"
										.equals(entity.getReturnType())))
										&& StringUtils.isNotEmpty(Waybill
												.getReturnType())) {
									Status = "返货单号";
									commWaybillCheckStatus
											.setReturnType(Status);
									commWaybillCheckStatus.setWaybillNo(entity
											.getWaybillNo());
									commWaybillCheckStatus
											.setOriginalWaybillNo(entity
													.getOriginalWaybillNo());
									commWaybillCheckStatus
											.setHasReturnType(hasReturnType);
								}
							} else if (entity != null
									&& StringUtils.isEmpty(entity
											.getOriginalWaybillNo())) {
								Waybill = waybillExpressService
										.queryExpressWaybillByOriginalWaybillNo(infoDto
												.getWaybillNo());
								if (Waybill != null
										&& StringUtils.isNotEmpty(Waybill
												.getOriginalWaybillNo())) {
									hasReturnType = "false";
									if ("RETURN_WAYBILL".equals(Waybill
											.getReturnType())
											&& StringUtils.isNotEmpty(Waybill
													.getReturnType())) {
										Status = "签收单返回单号";
										commWaybillCheckStatus
												.setReturnType(Status);
										commWaybillCheckStatus
												.setWaybillNo(Waybill
														.getOriginalWaybillNo());
										commWaybillCheckStatus
												.setOriginalWaybillNo(Waybill
														.getWaybillNo());
										commWaybillCheckStatus
												.setHasReturnType(hasReturnType);
									} else if (("RETURN_CARGO".equals(Waybill
											.getReturnType()) || ("RETURN_PIECE"
											.equals(Waybill.getReturnType())))
											&& StringUtils.isNotEmpty(Waybill
													.getReturnType())) {
										Status = "返货单号";
										commWaybillCheckStatus
												.setReturnType(Status);
										commWaybillCheckStatus
												.setWaybillNo(Waybill
														.getOriginalWaybillNo());
										commWaybillCheckStatus
												.setOriginalWaybillNo(Waybill
														.getWaybillNo());
										commWaybillCheckStatus
												.setHasReturnType(hasReturnType);
									}
								}
							}
						} else {
							Waybill = waybillExpressService
									.queryExpressWaybillByOriginalWaybillNo(infoDto
											.getOriginalWaybillNo());
							if (Waybill != null
									&& StringUtils.isNotEmpty(Waybill
											.getOriginalWaybillNo())) {
								hasReturnType = "false";
								if ("RETURN_WAYBILL".equals(Waybill
										.getReturnType())
										&& StringUtils.isNotEmpty(Waybill
												.getReturnType())) {
									Status = "签收单返回单号";
									commWaybillCheckStatus
											.setReturnType(Status);
									commWaybillCheckStatus.setWaybillNo(Waybill
											.getWaybillNo());
									commWaybillCheckStatus
											.setOriginalWaybillNo(Waybill
													.getOriginalWaybillNo());
									commWaybillCheckStatus
											.setHasReturnType(hasReturnType);
								} else if (("RETURN_CARGO".equals(Waybill
										.getReturnType()) || ("RETURN_PIECE"
										.equals(Waybill.getReturnType())))
										&& StringUtils.isNotEmpty(Waybill
												.getReturnType())) {
									Status = "返货单号";
									commWaybillCheckStatus
											.setReturnType(Status);
									commWaybillCheckStatus.setWaybillNo(Waybill
											.getWaybillNo());
									commWaybillCheckStatus
											.setOriginalWaybillNo(Waybill
													.getOriginalWaybillNo());
									commWaybillCheckStatus
											.setHasReturnType(hasReturnType);
								}
							}
						}
					}

				}
			}
		} else {
			commWaybillCheckStatus.setInformation("必须输入单号");
		}
		response.setHeader("ESB-ResultCode","1");
		return commWaybillCheckStatus;
	}
	private String  toFowwingString(String loseGoodsPackage){
		String loseGoodsPackagenum="";
		for(int i=0;i<loseGoodsPackage.length();i++){
			String temp=loseGoodsPackage.substring(i, i+1);
			try {
				Double.valueOf(temp);
				loseGoodsPackagenum=loseGoodsPackagenum+temp;
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
		return loseGoodsPackagenum;
		
	}

}
