package com.deppon.foss.module.transfer.airfreight.server.service.impl;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirWaybillDao;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IPushAirPickUpInfoDao;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IPushAirPickUpInfoService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickUpDetialInfoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickUpDetialRequest;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickUpInfoRequest;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickUpSerialInfoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickUpSerialRequest;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillDetialRequest;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillSerialNoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillSerialRequest;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillTempEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.OPPNeedAirWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.OPPNeedAirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.OppNeedAirWaybillSerialNoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirWayBillDto;
import com.deppon.foss.module.transfer.common.server.service.impl.FossToOppService;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
/**
 * 
* @description 推送合大票信息至OPP系统
* @version 1.0
* @author 269701-foss-lln
* @update 2016年4月27日 上午11:11:41
 */
public class PushAirPickUpInfoService implements IPushAirPickUpInfoService{
	//日志logger
	private static final Logger LOGGER = LoggerFactory.getLogger(PushAirPickUpInfoService.class);
	
	//推送合大票信息 Dao
	private IPushAirPickUpInfoDao pushAirPickUpInfoDao;
	/****查询航空正单相关表****/
	@Autowired
	private IAirWaybillDao airWaybillDao ;
	/**
	 * @Description: JOB执行 推送合大票清单数据至OPP系统
	 * @date 2016-04-05 下午3:06:04   
	 * @author 269701 
	 * @throws IOException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Override
	public void doPushAirPickUpInfo() throws Exception {
		LOGGER.info("---调用自动推送doPushAirPickUpInfo()方法开始---");	
		//排除掉 合大票 insert delete推送状态都是N 的数据
		pushAirPickUpInfoDao.updateAirPickUpTemById();
		//获取需要推送的数据
		List<AirWaybillTempEntity> tempList =new ArrayList<AirWaybillTempEntity>();
		//查询临时表
		tempList=pushAirPickUpInfoDao.queryAirPickUpTemInfo();
		//推送数据
		if(tempList!=null && tempList.size()>0){
			//限制循环的条数 如果5分钟内查询到需要推送的数据超过1500条 只推送前1500条
			for(AirWaybillTempEntity tempEntity:tempList){
				if(StringUtils.equals("10",tempEntity.getBillType())){
					//同步正单号
					LOGGER.info("---航空正单id："+tempEntity.getAirWaybillId()+"航空正单号："
							+tempEntity.getAirWaybillNo()+"处理开始时间："+new Date());
					long startTime=	System.currentTimeMillis();
					syncAirWaybillData(tempEntity);
					long endTime=	System.currentTimeMillis();
					LOGGER.info("---航空正单id："+tempEntity.getAirWaybillId()+"航空正单号："
							+tempEntity.getAirWaybillNo()+"处理结束时间："+new Date()+" 处理时长："+(endTime-startTime));
					
				}else{
					
					LOGGER.info("---合大票id："+tempEntity.getAirPickUpId()+"合大票："
							+tempEntity.getAirPickNo()+"处理开始时间："+new Date());
					//同步清单号
					long startTime=	System.currentTimeMillis();
					syncAirPickUpData(tempEntity);
					long endTime=	System.currentTimeMillis();
					LOGGER.info("---合大票id："+tempEntity.getAirPickUpId()+"合大票："
							+tempEntity.getAirPickNo()+"处理结束时间："+new Date()+" 处理时长："+(endTime-startTime));
				}	
			}
		}
		LOGGER.info("---调用自动推送doPushAirPickUpInfo()方法结束---");
	}
	
	/**
	 * 
	* @description 同步合大票清单信息至OPP
	* @version 1.0
	* @author 269701-foss-lln
	 * @throws IOException 
	* @update 2016年5月17日 下午8:23:23
	 */
	@Transactional
	private void syncAirPickUpData(AirWaybillTempEntity tempEntity) throws Exception{
		//清单号
		String airPickUpNo=null;
		//合票清单主表ID
		String airPickId=null;
			//根据合大票清单id，合大票清单表
			AirPickUpInfoRequest airPickUpInfo=new AirPickUpInfoRequest();
			
			airPickUpInfo=pushAirPickUpInfoDao.queryAirPickUpInfo(tempEntity.getAirPickUpId(),"");
		//try{
			if(null != airPickUpInfo && !StringUtils.equals("DELETE", tempEntity.getOperateStatus())){
				airPickUpNo=airPickUpInfo.getAirWayBillNo();
				airPickId=airPickUpInfo.getAirPickUpID();
				
				//调用接口，推送数据给OPP
				/**
				 *1. 推送主表信息
				* 清单主表的信息
				**/
				//推送中
				airPickUpInfo.setPushStatus("N");
				//设置操作类型
				airPickUpInfo.setOperStatus(tempEntity.getOperateStatus());
				LOGGER.error("***************调用接口，同步合大票清单信息至OPP 开始***清单号："+airPickUpNo+"**************");
				FossToOppService.getInstatce().syncAirPickUpToOPP(airPickUpInfo);
				LOGGER.error("***************调用接口，同步合大票清单信息至OPP 成功结束*****清单号："+airPickUpNo+"************");
					//根据清单号，查询合大票清单明细表
					List<AirPickUpDetialInfoEntity>  airPickUpDetialInfoList=new ArrayList<AirPickUpDetialInfoEntity>();
					//推送至OPP数据
					List<AirPickUpDetialInfoEntity>  airPickUpDetialList=new ArrayList<AirPickUpDetialInfoEntity>();

					//根据合大票ID 查询合大票明细表
					airPickUpDetialInfoList=pushAirPickUpInfoDao.queryAirPickUpDetialInfo(airPickUpNo);
					
					if(airPickUpDetialInfoList.size()>0){
						for(int i=0;i<airPickUpDetialInfoList.size();i++){
							//当前明细数据
							AirPickUpDetialInfoEntity detialEntity=airPickUpDetialInfoList.get(i);
							//创建时间
							detialEntity.setCreateTime(null);
							airPickUpDetialList.add(detialEntity);
							//根据合大票明细运单号,查询清单流水号表
							List<AirPickUpSerialInfoEntity> airPickUpSerialInfoList=new ArrayList<AirPickUpSerialInfoEntity>();
							airPickUpSerialInfoList=pushAirPickUpInfoDao.queryAirPickUpSerialInfo(airPickUpDetialInfoList.get(i).getDetialId());
							if(airPickUpSerialInfoList.size()>0){
								/**
								 * 3.推送合大票清单流水号信息给OPP;
								 * 推送当前循环合大票清单明细id对应合大票清单流水信息至OPP
								 * 流水信息
								 */
								AirPickUpSerialRequest requestSerial=new AirPickUpSerialRequest();
								//流水信息時間搓轉換
								for(AirPickUpSerialInfoEntity entity:airPickUpSerialInfoList){
									entity.setCreateTime(null);	
								}
								//清单号
								requestSerial.setAirWayBillNo(airPickUpNo);
								//操作类型
								requestSerial.setOperStatus(tempEntity.getOperateStatus());
								//运单号
								requestSerial.setWayBillNo(detialEntity.getWayBillNo());
								//需要推送流水信息
								requestSerial.setAirPickUpSerialList(airPickUpSerialInfoList);
								LOGGER.error("***************调用接口，同步合大票清单流水信息至OPP 开始**"+detialEntity.getWayBillNo());
								FossToOppService.getInstatce().syncAirPickUpSerialToOPP(requestSerial);
								LOGGER.error("***************调用接口，同步合大票清单流水信息至OPP 成功结束**"+detialEntity.getWayBillNo());
							}
						}
						/**
						 * 2.推送明细表信息
						 * 推送当循环明细信息至OPP
						 * 清单明细表的信息
						**/
						//String requestPickUpDetialStr = JSONObject.fromObject(airPickUpDetialInfoList).toString();
						AirPickUpDetialRequest detialRequest=new AirPickUpDetialRequest();
						//清单号
						detialRequest.setAirPickNo(airPickUpNo);
						//操作类型
						detialRequest.setOperStatus(tempEntity.getOperateStatus());
						//明细list
						detialRequest.setAirPickUpDetialList(airPickUpDetialList);
						LOGGER.error("***************调用接口，同步合大票清单明细信息至OPP 开始***清单号："+airPickUpNo);
						FossToOppService.getInstatce().syncAirPickUpDetialToOPP(detialRequest);
						LOGGER.error("***************调用接口，同步合大票清单明细信息至OPP 成功结束**");
					}
					//4.推送完成后 更新临时表状态--将改数据变更为已推送--Y
					Map<String,String> params=new HashMap<String, String>();
					params.put("ID",tempEntity.getID());
					params.put("airPickId",airPickId);
					pushAirPickUpInfoDao.updateAirPickUpTemStatus(params);
					//5.通知OPP推送成功
					//OPP修改合大票清单表--推送完成
					//该条数据数据推送完成
					airPickUpInfo.setPushStatus("Y");
					airPickUpInfo.setOperStatus("UPDATE");
					LOGGER.error("***************调用接口，同步合大票清单状态至OPP 开始***清单号："+airPickUpNo+"**推送状态:"+airPickUpInfo.getPushStatus()+"***");
					FossToOppService.getInstatce().syncAirPickUpToOPP(airPickUpInfo);
					LOGGER.error("***************调用接口，同步合大票清单状态至OPP 成功结束***清单号："+airPickUpNo+"**推送状态:"+airPickUpInfo.getPushStatus()+"***");


			}else if(null == airPickUpInfo && StringUtils.equals("DELETE", tempEntity.getOperateStatus())){
				//合大票清单进行删除操作 数据不存在
				//同步合大票清单主表id 以及 合大票清单号 以及推送状态至OPP
				airPickUpInfo=new AirPickUpInfoRequest();
				airPickUpInfo.setPushStatus("Y");
				airPickUpInfo.setOperStatus("DELETE");
				airPickUpInfo.setAirPickUpID(tempEntity.getAirPickUpId());
				airPickUpInfo.setAirWayBillNo(tempEntity.getAirPickNo());
				LOGGER.error("***************调用接口，同步合大票清单删除操作至OPP 开始**********************");
				FossToOppService.getInstatce().syncAirPickUpToOPP(airPickUpInfo);
				LOGGER.error("***************调用接口，同步合大票清单删除操作至OPP 结束**********************");
				
				//4.推送完成后 更新临时表状态--将改数据变更为已推送--Y
				Map<String,String> params=new HashMap<String, String>();
				params.put("ID",tempEntity.getID());
				params.put("airPickId",tempEntity.getAirPickUpId());
				pushAirPickUpInfoDao.updateAirPickUpTemStatus(params);
			}
			
	}
	/**
	 * 
	* @description 同步航空正单信息至OPP
	* @param tempEntity
	* @version 1.0
	* @author 269701-foss-lln
	 * @throws IOException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	* @update 2016年5月17日 下午8:26:09
	 */
	@Transactional
	private void syncAirWaybillData(AirWaybillTempEntity tempEntity) throws Exception{
		//正单号
		String airWaybillNo=null;
		//航空正单主表ID
		String airWaybillId=null;
		//根据航空正单主表id 查询航空正单信息
		AirWaybillEntity airWaybillNoInfo=new AirWaybillEntity();
		AirWayBillDto airWayBillDto=new AirWayBillDto();
		//航空正单id
		airWayBillDto.setAirwaybillId(tempEntity.getAirWaybillId());
		//根据航空正单id 得到航空正单信息
		airWaybillNoInfo=pushAirPickUpInfoDao.queryAirWaybillInfoById(airWayBillDto);
		//需要推送给OPP的航空正单实体
		OPPNeedAirWaybillEntity oppAirWaybillNoInfo=new OPPNeedAirWaybillEntity();
		//try{
			if(null != airWaybillNoInfo &&!StringUtils.equals("DELETE",tempEntity.getOperateStatus())){
					airWaybillNo=airWaybillNoInfo.getAirWaybillNo();
					airWaybillId=airWaybillNoInfo.getId();
					
					//调用接口，推送数据给OPP
					/**
					 *1. 推送主表信息
					* 航空正单主表的信息
					**/
					//给推送OPP数据赋值
					oppAirWaybillNoInfo=convertOppAirPick(airWaybillNoInfo);
					//推送中
					oppAirWaybillNoInfo.setPushStatus("N");
					//设置操作类型
					oppAirWaybillNoInfo.setOperStatus(tempEntity.getOperateStatus());

					LOGGER.error("***************调用接口，同步航空正单单信息至OPP 开始*****正单号："+airWaybillNo+"*****************");
					FossToOppService.getInstatce().syncAirWaybillToOPP(oppAirWaybillNoInfo);
					LOGGER.error("***************调用接口，同步合大票清航空正单信息至OPP 成功结束*****航空正单号："+airWaybillNo+"*****************");

					//根据航空正单id以及正单号，查询航空正单明细
					List<AirWaybillDetailEntity>  airWaybillDetialList=new ArrayList<AirWaybillDetailEntity>();
					List<OPPNeedAirWaybillDetailEntity>  oppNeedDetialList=new ArrayList<OPPNeedAirWaybillDetailEntity>();
	
					//根据航空正单id 查询航空正单明细信息
						airWaybillDetialList=airWaybillDao.queryAirWaybillDetialList(airWaybillId);
						if(airWaybillDetialList.size()>0){

							//循环航空正单明细表
							for(AirWaybillDetailEntity oldDetial:airWaybillDetialList){
								//获取当前循环航空正单明细信息
								OPPNeedAirWaybillDetailEntity  detialEntity=new OPPNeedAirWaybillDetailEntity();
								//给推送至OPP的数据赋值
								PropertyUtils.copyProperties(detialEntity, oldDetial);
								detialEntity.setCreateTime(null);
								oppNeedDetialList.add(detialEntity);
								//根据正单明细id以及运单号,查询正单流水号表
								List<AirWaybillSerialNoEntity> airWaybillSerialList=new ArrayList<AirWaybillSerialNoEntity>();
								airWaybillSerialList=airWaybillDao.queryWaybillSerialNoListToOpp(detialEntity.getId());
								if(airWaybillSerialList.size()>0){

									/**
									 * 3.推送航空正单流水号信息给OPP;
									 * 推送当前循环明细对应的所有流水信息
									 * 航空正单流水信息
									 */
									AirWaybillSerialRequest request=new AirWaybillSerialRequest();
									//需要推送给OPP的航空正单流水数据
									List<OppNeedAirWaybillSerialNoEntity> oppAirWaybillSerialList=new ArrayList<OppNeedAirWaybillSerialNoEntity>();
									//時間格式轉換成時間搓
									for(AirWaybillSerialNoEntity serialEntity:airWaybillSerialList){
										OppNeedAirWaybillSerialNoEntity entity=new OppNeedAirWaybillSerialNoEntity();
										//给推送至OPP的数据赋值
										PropertyUtils.copyProperties(entity, serialEntity);
										entity.setCreateTime(null);
										oppAirWaybillSerialList.add(entity);
									}
									//航空正单号
									request.setAirWayBillNo(airWaybillNo);
									//操作类型
									request.setOperStatus(tempEntity.getOperateStatus());
									//运单号
									request.setWayBillNo(detialEntity.getWaybillNo());
									//流水list
									request.setAirWaybillSerialList(oppAirWaybillSerialList);
									LOGGER.error("***************调用接口，同步航空正单流水信息至OPP 开始***运单号："+request.getAirWayBillNo()+"*******************");
									FossToOppService.getInstatce().syncAirWaybillSerialToOPP(request);
									LOGGER.error("***************调用接口，同步航空正单流水信息至OPP 成功结束**运单号："+request.getAirWayBillNo()+"********************");

								}
							}
							
							
							/**
							 * 2.推送明细表信息
							 * 推送当前循环航空正单明细信息 一条数据
							 * 航空正单明细表的信息
							**/
							//String requestPickUpDetialStr = JSONObject.fromObject(airPickUpDetialInfoList).toString();
							//推送至OPP参数
							AirWaybillDetialRequest detialRequest=new AirWaybillDetialRequest();
							//设置操作类型
							detialRequest.setOperStatus(tempEntity.getOperateStatus());
							//正单号
							detialRequest.setAirWaybillNo(airWaybillNo);
							//明细list
							detialRequest.setAirWaybillDetialList(oppNeedDetialList);
							LOGGER.error("***************调用接口，同步航空正单明细信息至OPP 开始*****航空正单号："+airWaybillNo+"**操作类型："+detialRequest.getOperStatus()+"***************");
							FossToOppService.getInstatce().syncAirWaybillDetialToOPP(detialRequest);
							LOGGER.error("***************调用接口，同步航空正单明细信息至OPP 成功结束***航空正单号："+airWaybillNo+"**操作类型："+detialRequest.getOperStatus()+"*******************");
							
						}
						//4.推送完成后 更新临时表状态--将改数据变更为已推送--Y
						Map<String,String> params=new HashMap<String, String>();
						params.put("ID",tempEntity.getID());
						params.put("airWaybillId",airWaybillId);
						
						pushAirPickUpInfoDao.updateAirPickUpTemStatus(params);
						//5.通知OPP推送成功
						//OPP修改航空正单表--推送完成
						//该条数据数据推送完成
						oppAirWaybillNoInfo.setPushStatus("Y");
						oppAirWaybillNoInfo.setOperStatus("UPDATE");
						LOGGER.error("***************调用接口，同步航空正单主表推送完成至OPP 开始***正单号："+oppAirWaybillNoInfo.getAirWaybillNo()+"***推送状态："+oppAirWaybillNoInfo.getPushStatus()+"****************");
						FossToOppService.getInstatce().syncAirWaybillToOPP(oppAirWaybillNoInfo);
						LOGGER.error("***************调用接口，同步航空正单主表推送完成至OPP 结束**正单号："+oppAirWaybillNoInfo.getAirWaybillNo()+"***推送状态："+oppAirWaybillNoInfo.getPushStatus()+"*************************");

						
				}else if(airWaybillNoInfo ==null && StringUtils.equals("DELETE",tempEntity.getOperateStatus())){
					//航空正单进行删除操作
					oppAirWaybillNoInfo=new OPPNeedAirWaybillEntity();
					oppAirWaybillNoInfo.setPushStatus("Y");
					oppAirWaybillNoInfo.setOperStatus("DELETE");
					oppAirWaybillNoInfo.setAirWaybillNo(tempEntity.getAirWaybillNo());
					oppAirWaybillNoInfo.setId(tempEntity.getAirWaybillId());
					LOGGER.error("***************调用接口，同步航空正单删除操作数据给OPP 开始**********************");
					FossToOppService.getInstatce().syncAirWaybillToOPP(oppAirWaybillNoInfo);
					LOGGER.error("***************调用接口，同步航空正单删除操作数据给OPP 结束**********************");
					
					//4.推送完成后 更新临时表状态--将改数据变更为已推送--Y
					Map<String,String> params=new HashMap<String, String>();
					params.put("ID",tempEntity.getID());
					params.put("airWaybillId",tempEntity.getAirWaybillId());
					
					pushAirPickUpInfoDao.updateAirPickUpTemStatus(params);
				}
			
	}
	
	/**
	 * 
	* @description FOSS推送数据转换成OPP需要的
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年6月1日 上午9:43:09
	 */
	private OPPNeedAirWaybillEntity convertOppAirPick(AirWaybillEntity requestParam){
		
		OPPNeedAirWaybillEntity entity =new OPPNeedAirWaybillEntity();
		//航空公司二字码
		entity.setAirLineTwoletter(requestParam.getAirLineTwoletter());
		//配载类型
		entity.setAirAssembleType(requestParam.getAirAssembleType());
		//正单号
		entity.setAirWaybillNo(requestParam.getAirWaybillNo());
		//外发承运人
		entity.setAgenctCode(requestParam.getAgenctCode());
		//外发承运人那么
		entity.setAgencyName(requestParam.getAgencyName());
		//目的站编码
		entity.setArrvRegionCode(requestParam.getArrvRegionCode());
		//目的站name
		entity.setArrvRegionName(requestParam.getArrvRegionName());
		//计费重量
		entity.setBillingWeight(requestParam.getBillingWeight());
		//制单部门编号
		entity.setCreateOrgCode(requestParam.getCreateOrgCode());
		//制单部门name
		entity.setCreateOrgName(requestParam.getCreateOrgName());
		//制单时间
		entity.setCreateTime(DateUtils.convert(requestParam.getCreateTime(), DateUtils.DATE_TIME_FORMAT));
		//制单人code
		entity.setCreateUserCode(requestParam.getCreateUserCode());
		//制单人name
		entity.setCreateUserName(requestParam.getCreateUserName());
		//币种
		entity.setCurrencyCode(requestParam.getCurrencyCode());
		//目的站
		entity.setDestOrgName(requestParam.getDedtOrgName());
		//目的站name
		entity.setDestOrgCode(requestParam.getDestOrgCode());
		//总费用
		entity.setFeeTotal(requestParam.getFeeTotal());
		//航班时间
		entity.setFlightDate(DateUtils.convert(requestParam.getFlightDate(), DateUtils.DATE_TIME_FORMAT));
		//航班号
		entity.setFlightNo(requestParam.getFlightNo());
		//商品名
		entity.setGoodsName(requestParam.getGoodsName());
		//件数
		entity.setGoodsQty(requestParam.getGoodsQty());
		//毛总
		entity.setGrossWeight(requestParam.getGrossWeight());
		//正单id
		entity.setId(requestParam.getId());
		//合票号
		entity.setJointTicketNo(requestParam.getJointTicketNo());
		//修改时间
		entity.setModifyTime(DateUtils.convert(requestParam.getModifyTime(), DateUtils.DATE_TIME_FORMAT));
		//修改人code
		entity.setModifyUserCode(requestParam.getModifyUserCode());
		//修改人name
		entity.setModifyUserName(requestParam.getModifyUserName());
		//付款方式
		entity.setPaymentType(requestParam.getPaymentType());
		//提货方式
		entity.setPickupType(requestParam.getPickupType());
		//运输性质
		entity.setProductCode(requestParam.getProductCode());
		//运输性质
		entity.setProductName(requestParam.getProductName());
		//收货人地址
		entity.setReceiverAddress(requestParam.getReceiverAddress());
		//收货人编码
		entity.setReceiverCode(requestParam.getReceiverCode());
		//收货人电话
		entity.setReceiverContactPhone(requestParam.getReceiverContactPhone());
		//收货人姓名
		entity.setReceiverName(requestParam.getReceiverName());
		//体积
		entity.setVolume(requestParam.getVolume());
		
		return entity;
	}
	
	/**
	 * 
	* @description 保存数据至临时表
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年5月11日 上午9:50:35
	 */
	public void addAirPickToTemp(AirWaybillTempEntity temEntity){
		
		//保存数据至临时表
		if(null!=temEntity){
			//清单同步
			if(StringUtils.equals(temEntity.getBillType(), "20")){
				//添加新增 修改 删除的合大票单号以及id 保存至临时表
				addAirPickDataToTemp(temEntity);
			}else if(StringUtils.equals(temEntity.getBillType(), "10")){
				//正单同步数据
				//添加新增 修改 删除的航空正单号以及id 保存至临时表
				addAirWaybillDataToTemp(temEntity);
			}else{
				//不做操作
			}

		}
		
	}
	/**
	 * 
	* @description 添加清单信息至临时表
	* @param temEntity
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年5月17日 下午7:38:55
	 */
	private void addAirPickDataToTemp(AirWaybillTempEntity temEntity){
		//如果操作类型是更新或者删除
			//校验该数据是否存在于临时表-true 存在 false 不存在
			boolean existTemp=pushAirPickUpInfoDao.checkDataIsExist(temEntity);
			if(existTemp){
				LOGGER.error("临时表存在该合大票信息 将合大票更新或者删除数据保存至临时表 开始：合大票清单号："+temEntity.getAirPickNo()+"操作类型："+temEntity.getOperateStatus());
				//临时表新增一条操作类型为更新或者删除的数据
				temEntity.setID(UUIDUtils.getUUID());
				pushAirPickUpInfoDao.addAirPickToTemp(temEntity);
				LOGGER.error("临时表存在该合大票信息 将合大票更新或者删除数据保存至临时表 结束：合大票清单号："+temEntity.getAirPickNo()+"操作类型："+temEntity.getOperateStatus());
			}else{
				//临时表先新增一条操作类型为新增的数据
				temEntity.setID(UUIDUtils.getUUID());
				LOGGER.error("临时表不存在该合大票信息 首先保存数据至临时表开始：合大票清单号："+temEntity.getAirPickNo()+"操作类型："+temEntity.getOperateStatus());
				pushAirPickUpInfoDao.addAirPickToTemp(temEntity);
				LOGGER.error("临时表不存在该合大票信息 首先保存数据至临时表结束：合大票清单号："+temEntity.getAirPickNo()+"操作类型："+temEntity.getOperateStatus());
				
			}
		
	}
	
	/**
	 * 
	* @description 添加航空正单信息至临时表
	* @param temEntity
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年5月17日 下午7:38:55
	 */
	private void addAirWaybillDataToTemp(AirWaybillTempEntity temEntity){
			//校验该数据是否存在于临时表-true 存在 false 不存在
			boolean existTemp=pushAirPickUpInfoDao.checkDataIsExist(temEntity);
			if(existTemp){
				LOGGER.error("临时表存在该航空正单信息 将航空正单更新或者删除数据保存至临时表 开始：航空正单号："+temEntity.getAirWaybillNo()+"操作类型："+temEntity.getOperateStatus());
				//临时表新增一条操作类型为更新或者删除的数据
				temEntity.setID(UUIDUtils.getUUID());
				pushAirPickUpInfoDao.addAirPickToTemp(temEntity);
				LOGGER.error("临时表存在该航空正单信息 将航空正单更新或者删除数据保存至临时表 结束：航空正单号："+temEntity.getAirWaybillNo()+"操作类型："+temEntity.getOperateStatus());
			}else{
				//临时表先新增一条操作类型为新增的数据
				temEntity.setID(UUIDUtils.getUUID());
				LOGGER.error("临时表不存在该航空正单信息 首先保存数据至临时表开始：航空正单号："+temEntity.getAirWaybillNo()+"操作类型："+temEntity.getOperateStatus());
				pushAirPickUpInfoDao.addAirPickToTemp(temEntity);
				LOGGER.error("临时表不存在该航空正单信息 首先保存数据至临时表结束：航空正单号："+temEntity.getAirWaybillNo()+"操作类型："+temEntity.getOperateStatus());
				
		}
	}

	public void setPushAirPickUpInfoDao(IPushAirPickUpInfoDao pushAirPickUpInfoDao) {
		this.pushAirPickUpInfoDao = pushAirPickUpInfoDao;
	}
	/*public void setAirWaybillDao(IAirWaybillDao airWaybillDao) {
		this.airWaybillDao = airWaybillDao;
	}*/
	
}
