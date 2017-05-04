package com.deppon.foss.module.transfer.partialline.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IWayBillNoLocusService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WayBillNoLocusDTO;
import com.deppon.foss.module.pickup.sign.api.server.service.IAirAgencySignService;
import com.deppon.foss.module.pickup.sign.api.server.service.IExpSignToTfrService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignChangeService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.trackings.api.server.service.IPushTrackForCaiNiaoService;
import com.deppon.foss.module.trackings.api.shared.domain.SynTrackingEntity;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToAgentWaybillService;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.dto.AgentWaybillNoRequestDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.AgentWaybillResponseDto;
import com.deppon.foss.module.transfer.common.api.shared.vo.AgentWaybillExceptionDto;
import com.deppon.foss.module.transfer.partialline.api.server.dao.IAgentWaybillTrackDao;
import com.deppon.foss.module.transfer.partialline.api.server.service.IAgentWaybillTrackService;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillService;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillTrackService;
import com.deppon.foss.module.transfer.partialline.api.server.service.IPrintAgentWaybillService;
import com.deppon.foss.module.transfer.partialline.api.shared.define.PartiallineConstants;
import com.deppon.foss.module.transfer.partialline.api.shared.define.TrackingEventTypeEnum;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.AgentWaybillNoEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.AgentWaybillTrackEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.InternationalTrackingEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintAgentWaybillRecordEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.AgentWaybillTrackDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.ExpressPushInfoDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillTrackDto;
import com.deppon.foss.module.transfer.partialline.api.shared.exception.ExternalBillException;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * @author niuly
 * @function 统计需要推送给快递100的运单轨迹类
 * @date 2015年1月31日下午2:42:19
 * 
 */
public class AgentWaybillTrackService implements IAgentWaybillTrackService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AgentWaybillTrackService.class);
	
	private IAgentWaybillTrackDao agentWaybillTrackDao;
	private IWayBillNoLocusService wayBillNoLocusService;
	private IWaybillManagerService waybillManagerService;
	private IWaybillSignResultService waybillSignResultService;
	private ILdpExternalBillService ldpExternalBillService;
	private ILdpExternalBillTrackService ldpExternalBillTrackService;
	private IPrintAgentWaybillService printAgentWaybillService;
	private IFOSSToAgentWaybillService  fossToAgentWaybillService;
	private IAirAgencySignService airAgencySignService;
	private IConfigurationParamsService configurationParamsService;
	private IAdministrativeRegionsService administrativeRegionsService;
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private IExpSignToTfrService expSignToTfrService;
	private ISignChangeService signChangeService;
	private IDataDictionaryValueService dataDictionaryValueService;
	private IPushTrackForCaiNiaoService pushTrackForCaiNiaoService;
	
	//快递100推送轨迹至FOSS
	/**
	 * @author nly
	 * @date 2015年2月3日 上午10:32:52
	 * @function 推送代理单号给快递100
	 */
	@Override
	public void pushAgentWaybillNos() {
		Date startDate = DateUtils.strToDate("2015-03-22 00:00:00");
		try{
			ConfigurationParamsEntity defaultBizHourSlice = configurationParamsService.queryConfigurationParamsByOrgCode(
					DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , 
					ConfigurationParamsConstants.TFR_PARM_PUSH_AGENTWAYBILLNO_BEGIN_TIME, 
					FossConstants.ROOT_ORG_CODE);
			if(defaultBizHourSlice != null && StringUtils.isNotEmpty(defaultBizHourSlice.getConfValue())){
				startDate = DateUtils.strToDate(defaultBizHourSlice.getConfValue());
			}
			LOGGER.error("配置参数值"+startDate);
		} catch(Exception e){
			LOGGER.error("获取配置参数失败");
		}
		//需推送的单号
		List<AgentWaybillNoRequestDto> agentWabillList = agentWaybillTrackDao.queryAgentWaybillNoList(startDate);
		LOGGER.error("agentWabillList===" + agentWabillList);
		Map<String,String> cityMap = new HashMap<String,String>();
		//获取目的站-提货网点所在城市
		for(AgentWaybillNoRequestDto dto : agentWabillList) {
			if(cityMap.containsKey(dto.getTo())) {
				dto.setTo(cityMap.get(dto.getTo()));
			} else {
				String cityName = administrativeRegionsService.queryAdministrativeRegionsNameByCode(dto.getTo());
				cityMap.put(dto.getTo(), cityName);
				dto.setTo(cityName);
			}
		}
		//推送成功的代理单号
		List<String> successList = new ArrayList<String>();
		//推送失败的代理单号
		List<AgentWaybillExceptionDto> exceptionList = new ArrayList<AgentWaybillExceptionDto>();
		for(AgentWaybillNoRequestDto dto : agentWabillList) {
			LOGGER.error("FOSS推送快递单号" + dto.getNumber() + "给100开始...");
			try{
				AgentWaybillResponseDto  responseDto = fossToAgentWaybillService.pushAgentWaybillNo(dto);
				LOGGER.error("responseDto===" + responseDto);
				//ESB异常
				if(null == responseDto) {
					//保存异常信息，标注ESB异常，便于查找问题原因
					//推送失败时，异常信息处理
					AgentWaybillExceptionDto exceptionDto = new AgentWaybillExceptionDto();
					exceptionDto.setId(UUIDUtils.getUUID());
					exceptionDto.setWaybillNo(dto.getNumber());
					exceptionDto.setCode("500-ESB");
					exceptionDto.setMessage("FOSS推送单号给100时ESB发生异常，exceptionCode-S000099");
					exceptionDto.setType(TransferConstants.EXP_FOSS2100_AGENTWAYBILLNO);
					exceptionList.add(exceptionDto);
					continue;
				}
				if(StringUtils.equals("true", responseDto.getResult())) {
					successList.add(dto.getNumber());
				} else {
					//推送失败时，异常信息处理
					AgentWaybillExceptionDto exceptionDto = new AgentWaybillExceptionDto();
					exceptionDto.setId(UUIDUtils.getUUID());
					exceptionDto.setWaybillNo(dto.getNumber());
					exceptionDto.setCode(responseDto.getReturnCode());
					exceptionDto.setMessage(responseDto.getMessage());
					exceptionDto.setType(TransferConstants.EXP_FOSS2100_AGENTWAYBILLNO);
					exceptionList.add(exceptionDto);
					//重复订阅时，不再继续推送，此处必须要求绑定时，限制绑定的代理单号唯一
					if(StringUtils.equals("501", responseDto.getReturnCode()) || StringUtils.equals("700", responseDto.getReturnCode())) {
						successList.add(dto.getNumber());
					}
					//sonar-重复-352203，将条件提到一起
					/* else if(StringUtils.equals("700", responseDto.getReturnCode())) {
						successList.add(dto.getNumber());
					}*/
				}
			} catch(Exception e) {
				//推送失败
			}
			LOGGER.error("FOSS推送快递单号" + dto.getNumber() + "给100结束...");
		}
		
		try{
			//推送成功的不再推送
			for(String agentWaybillNo : successList) {
				agentWaybillTrackDao.updatePrintWaybillInfo(agentWaybillNo);
				LOGGER.error("FOSS推送快递单号给100-更新成功单号"+agentWaybillNo+"推送状态成功");
			}
		}catch(Exception e){
			LOGGER.error("FOSS推送代理单号给100-更新成功单号状态失败");
		}
		
		try{
			//保存推送失败记录
			for(AgentWaybillExceptionDto dto:exceptionList) {
				agentWaybillTrackDao.addExceptionInfo(dto);
			}
		}catch(Exception e) {
			LOGGER.error("FOSS推送代理单号给100-保存异常信息失败");
		}
	}

	/**
	 * 手动推送代理单号
	 * @param agentWabillList
	 * @author 257220
	 * @date 2015-7-28上午11:10:51
	 */
	public List<AgentWaybillExceptionDto> pushAgentWaybillNos(List<AgentWaybillNoRequestDto> agentWabillList) {
		if(agentWabillList == null || agentWabillList.size() == 0){
			return null;
		}
		//推送成功的代理单号
		List<AgentWaybillNoRequestDto> successList = new ArrayList<AgentWaybillNoRequestDto>();
		//推送失败的代理单号
		List<AgentWaybillExceptionDto> exceptionList = new ArrayList<AgentWaybillExceptionDto>();
		//推送失败的代理单号，不包括重复订阅的代理单号，用于返回前台页面
		List<AgentWaybillExceptionDto> exceptionWithoutReorderList = new ArrayList<AgentWaybillExceptionDto>();
		//循环推送
		for(AgentWaybillNoRequestDto dto : agentWabillList) {
			LOGGER.error("FOSS推送快递单号" + dto.getNumber() + "给100开始...");
			try{
				AgentWaybillResponseDto  responseDto = fossToAgentWaybillService.pushAgentWaybillNo(dto);
				LOGGER.error("responseDto===" + responseDto);
				//ESB异常
				if(null == responseDto) {
					//保存异常信息，标注ESB异常，便于查找问题原因
					//推送失败时，异常信息处理
					AgentWaybillExceptionDto exceptionDto = new AgentWaybillExceptionDto();
					exceptionDto.setId(UUIDUtils.getUUID());
					exceptionDto.setWaybillNo(dto.getNumber());
					exceptionDto.setCode("500-ESB");
					exceptionDto.setMessage("FOSS推送单号给100时ESB发生异常，exceptionCode-S000099");
					exceptionDto.setType(TransferConstants.EXP_FOSS2100_AGENTWAYBILLNO);
					exceptionList.add(exceptionDto);
					exceptionWithoutReorderList.add(exceptionDto);
					continue;
				}
				if(StringUtils.equals("true", responseDto.getResult())) {
					successList.add(dto);
				} else {
					//推送失败时，异常信息处理
					AgentWaybillExceptionDto exceptionDto = new AgentWaybillExceptionDto();
					exceptionDto.setId(UUIDUtils.getUUID());
					exceptionDto.setWaybillNo(dto.getNumber());
					exceptionDto.setCode(responseDto.getReturnCode());
					exceptionDto.setMessage(responseDto.getMessage());
					exceptionDto.setType(TransferConstants.EXP_FOSS2100_AGENTWAYBILLNO);
					exceptionList.add(exceptionDto);
					//重复订阅时，不再继续推送，此处必须要求绑定时，限制绑定的代理单号唯一
					if(StringUtils.equals("501", responseDto.getReturnCode()) || StringUtils.equals("700", responseDto.getReturnCode())) {
						successList.add(dto);
					}
					//sonar-352203-将条件提到一个if中
					/*else if(StringUtils.equals("700", responseDto.getReturnCode())) {
						successList.add(dto);
					} */else{
						exceptionWithoutReorderList.add(exceptionDto);
					}
				}
			} catch(Exception e) {
				//推送失败
				throw new ExternalBillException("推送轨迹失败"+e);
			}
			LOGGER.error("FOSS推送快递单号" + dto.getNumber() + "给100结束...");
		}
		
		try{
			//推送成功的不再推送
			for( AgentWaybillNoRequestDto agentWaybillNoRequestDto : successList) {
				String agentNo = agentWaybillNoRequestDto.getNumber();
				updatePrintWaybillInfoByAgentInfo(agentNo,agentWaybillNoRequestDto.getCompany());
				LOGGER.error("FOSS推送快递单号给-更新成功单号"+agentNo+"推送状态成功");
			}
		}catch(Exception e){
			LOGGER.error("FOSS推送代理单号给100-更新成功单号状态失败"+e);
		}
		
		try{
			//保存推送失败记录
			for(AgentWaybillExceptionDto dto:exceptionList) {
				agentWaybillTrackDao.addExceptionInfo(dto);
			}
		}catch(Exception e) {
			LOGGER.error("FOSS推送代理单号给100-保存异常信息失败");
		}
		return exceptionWithoutReorderList;
	}
	
	/**
	 * @author nly
	 * @date 2015年2月4日 上午11:19:23
	 * @function 新增快递代理运单轨迹
	 * @param status
	 * @param state
	 * @param isSign
	 * @param agentWaybillNo
	 * @param trackList
	 * @throws Exception 
	 */
//	@Transactional
	@Override
	public void addWaybillTrack(String agentWaybillNo,String agentCompany,List<AgentWaybillTrackDto> trackList)  {

		//代理单号是否存在有效的绑定关系，且对应运单是否有效，是否存至有效的非中转外发落地配外发单
		//代理单号对应的运单号
		//List<PrintAgentWaybillRecordEntity> recordList = printAgentWaybillService.queryRecordByAgentWaybillNo(agentWaybillNo);
		List<PrintAgentWaybillRecordEntity> recordList = printAgentWaybillService.queryRecordByAgentWaybillNoAndCompany(agentWaybillNo,agentCompany);
		LOGGER.error("recordList===" + recordList);
		if(recordList.size()<=0){
			//代理单号是否存在有效的绑定关系
			ExpressPushInfoDto expressPushInfoDto=new ExpressPushInfoDto(UUIDUtils.getUUID(), null, agentWaybillNo, agentCompany, PartiallineConstants.IS_EXIST_VALID_BIND, PartiallineConstants.IS_EXIST_VALID_BIND_REASON, convertOperateType1(trackList.get(trackList.size()-1).getStatus()), null, PartiallineConstants.IS_EXIST_VALID_BIND_REASON+",代理单号为"+agentWaybillNo);
			//保存对应消息到指定数据库
			this.savePushExpressInfo(expressPushInfoDto);
		}
		for(PrintAgentWaybillRecordEntity record : recordList) {
			if(null == record) {
				continue;
			}
			String waybillNo = record.getWaybillNo();
			//add by 257220 未订阅,不入库
			if(StringUtils.isEmpty(record.getIspush())||StringUtils.equals(record.getIspush(),PartiallineConstants.ISPUSH_NO)){
				this.saveExceptionInfo(waybillNo,"699","拒收轨迹信息:未曾订阅轨迹信息");
				continue;
			}
			//人工停止接收轨迹信息后，不再保存入库
			if(PartiallineConstants.ISPUSH_BACK.equals(record.getIspush())){
				this.saveExceptionInfo(waybillNo,"699","拒收轨迹信息:人工停止接收轨迹");
				continue;
			}
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
			LOGGER.error("waybillEntity===" + waybillEntity);
			if(null == waybillEntity) {
				//对应运单是否有效时
				ExpressPushInfoDto expressPushInfoDto=new ExpressPushInfoDto(UUIDUtils.getUUID(), waybillNo, agentWaybillNo, agentCompany, PartiallineConstants.WAYBILL_IS_VALID, PartiallineConstants.WAYBILL_IS_VALID_REASON, convertOperateType1(trackList.get(trackList.size()-1).getStatus()), record.getPrintType(), PartiallineConstants.WAYBILL_IS_VALID_REASON+",运单号为"+waybillNo);
				//保存对应消息到指定数据库
				this.savePushExpressInfo(expressPushInfoDto);
				continue;
			}
			LOGGER.error("printType===" + record.getPrintType());
			//快递代理外发，保存轨迹和签收结果
			if(StringUtils.equals("LDP", record.getPrintType())) {
				List<LdpExternalBillDto> ldpExternalBillDtoList = ldpExternalBillService.queryExternalBillListForLdpSign(waybillNo);
				if(null == ldpExternalBillDtoList || ldpExternalBillDtoList.size() <= 0) {
					continue;
				}
				//有效非中转外发快递代理外发单
				LdpExternalBillDto billDto = ldpExternalBillDtoList.get(0);
				//反签收表中的记录集合
			    if(queryReversSign(waybillNo)){
			    	this.saveExceptionInfo(waybillNo,"699","拒收轨迹信息：" +"快递代理人工反签收后，FOSS不再接收快递100推送的货物状态信息");
			    	continue;
			    }
				//快递100为全量推送，故先删除原有轨迹
				ldpExternalBillTrackService.deleteLDPTrack(waybillNo);
				
				for(AgentWaybillTrackDto dto:trackList) {
					//签收
					if(StringUtils.equals("签收", dto.getStatus())) {
						if(!isContainsSensitive(dto.getContext())){
							LOGGER.error("运单号："+waybillNo + "同步快递中转外发签收记录开始");
							try{
								WaybillSignResultEntity expressSignEntity = new WaybillSignResultEntity();
								
								expressSignEntity.setWaybillNo(waybillNo);
								//流水号 added by 257220 一票多件外发
								expressSignEntity.setSerialNo(record.getSerialNo());
								//操作人姓名
								expressSignEntity.setCreator(billDto.getExternalUserName());
								//操作人编号
								expressSignEntity.setCreatorCode(billDto.getExternalUserCode());
								//操作网点名称
								expressSignEntity.setCreateOrgName(billDto.getExternalOrgName());
								//操作网点编码
								expressSignEntity.setCreateOrgCode(billDto.getExternalOrgCode());
								//签收情况
								expressSignEntity.setSignSituation(SignConstants.NORMAL_SIGN);
								//签收状态
								expressSignEntity.setSignStatus(SignConstants.SIGN_STATUS_ALL);
								//代理网点编码
								expressSignEntity.setAgentCode(billDto.getAgentOrgCode());
								//到达代理网点的时间  无该时间
								//expressSignEntity.setArriveTime(billDto.getBillTime());
								//送货时间  无送货时间
								//expressSignEntity.setDeliverDate(billDto.getDeliveryTime());
								//快递员名称 = 送货人(代理的员工)  无送货员  可为空
								//expressSignEntity.setExpressEmpName(billDto.getDeliveryUserName());
								//签收时间
								expressSignEntity.setSignTime(DateUtils.convert(dto.getFtime()));
								//签收人(客户) 为开单收货联系人
								//expressSignEntity.setDeliverymanName(billDto.getSignUpUserName());
								expressSignEntity.setDeliverymanName(waybillEntity.getReceiveCustomerContact());
								//签收件数   安件签收 件数为1
								//expressSignEntity.setSignGoodsQty(waybillEntity.getGoodsQtyTotal());
								expressSignEntity.setSignGoodsQty(1);
								//签收备注 = 异常描述  可为空
								expressSignEntity.setSignNote(TransferConstants.AUTO_SIGN);
								//调用落地配外发签收接口
								airAgencySignService.addExpressAgentSignResultForTfr(expressSignEntity);
								LOGGER.error("运单号："+waybillNo + "同步快递中转外发签收记录结束");
							} catch(Exception e) {
								e.printStackTrace();
								LOGGER.info("快递代理外发调用签收接口异常！");
								this.saveExceptionInfo(waybillNo,"600","快递代理外发调用签收接口异常" + e.getMessage());
								//throw new TfrBusinessException("快递代理外发调用签收接口异常！");
							}
						}
						else{
							//敏感词汇处理信息保存异常信息
							ExpressPushInfoDto expressPushInfoDto = setExpressEntityInfo(agentWaybillNo, agentCompany, record,
									waybillNo, dto, PartiallineConstants.EXIST_SENSITIVE_WORD_EXPRESS_REASON,PartiallineConstants.EXIST_SENSITIVE_WORD);
							//保存对应消息到指定数据库
							this.savePushExpressInfo(expressPushInfoDto);
						}
					} else if(StringUtils.equals("在途", dto.getStatus())
								|| StringUtils.equals("揽收", dto.getStatus())
								|| StringUtils.equals("疑难", dto.getStatus())
								|| StringUtils.equals("派件", dto.getStatus())
								|| StringUtils.equals("退签", dto.getStatus())
								|| StringUtils.equals("退回", dto.getStatus())){
						LOGGER.error("运单号："+waybillNo + "同步快递中转外发轨迹开始");
						try{
							String cityName = "";
							
							LdpExternalBillTrackDto trackDto = new LdpExternalBillTrackDto();
							String id = UUIDUtils.getUUID();
							trackDto.setId(id);
							trackDto.setTraceId(id);
							trackDto.setWaybillNo(waybillNo);
							trackDto.setAgentCompanyCode(billDto.getAgentCompanyCode());
							trackDto.setAgentCompanyName(billDto.getAgentCompanyName());
							trackDto.setAgentOrgCode(billDto.getAgentOrgCode());
							trackDto.setAgentOrgName(billDto.getAgentOrgName());
							//可能空，为空时默认为外发部门所在城市
							if(StringUtils.isNotEmpty(dto.getAreaName())) {
								trackDto.setOperationcity(dto.getAreaName());
							} else {
								OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(billDto.getExternalOrgCode());
								if(null != orgEntity) {
									cityName = administrativeRegionsService.queryAdministrativeRegionsNameByCode(orgEntity.getCityCode());
								}
								if(StringUtils.isEmpty(cityName)) {
									cityName = "外发部门";
								}
								trackDto.setOperationcity(cityName);
							}
							trackDto.setOperationtype(convertOperateType(dto.getStatus()));
							//trackDto.setDispatchname(dto.getDeliverName());//可为空
							//trackDto.setDispatchphoneno(dto.getDeliverPhone());//可为空
							
							//DN201607270034 菜鸟轨迹外发派送及时率优化——FOSS 派送时间改为接受到推送轨迹时间
							//2016-8-5 17:02:11 wwb
							if(StringUtils.equals("派件", dto.getStatus())){
								trackDto.setOperationTime(new Date());
							}else{
								trackDto.setOperationTime(DateUtils.convert(dto.getFtime()));
							}
							//操作人 为代理单号绑定人
							trackDto.setOperationUserName(record.getOperatorName());//必填
							
							if(StringUtils.equals("揽收", dto.getStatus())) {
								if(StringUtils.isEmpty(cityName) || StringUtils.equals("外发部门", cityName)) {
									//外发部门
									OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(billDto.getExternalOrgCode());
									if(null != orgEntity) {
										cityName = administrativeRegionsService.queryAdministrativeRegionsNameByCode(orgEntity.getCityCode());
									}
								}
								trackDto.setOperationDescribe("到达"+ cityName +"分拣站");
							} else {
								//屏蔽轨迹中敏感词汇
								   String str=dto.getContext();
								   String result=shieldingSensitive(str);
								   dto.setContext(result);
							       trackDto.setOperationDescribe(dto.getContext());
							}
						
							ldpExternalBillTrackService.addLdpExternalBillTrack(trackDto);
							LOGGER.error("运单号："+waybillNo + "同步快递中转外发轨迹结束");
						}catch(Exception e) {
							e.printStackTrace();
							LOGGER.error(waybillNo +":快递代理外发同步轨迹失败！");
							this.saveExceptionInfo(waybillNo,"600","快递代理外发同步轨迹失败" + e.getMessage());
							//throw new TfrBusinessException("快递代理外发同步轨迹失败！");
						}
					}
				}	
			} else if(StringUtils.equals("SD", record.getPrintType())) {//营业部外发，仅保存签收结果
				//反签收表中的记录集合
			    if(queryReversSign(waybillNo)){
			    	this.saveExceptionInfo(waybillNo,"699","拒收轨迹信息：" +"快递代理人工反签收后，FOSS不再接收快递100推送的货物状态信息");
			    	continue;
			    }
				for(AgentWaybillTrackDto dto:trackList) {
					if(StringUtils.equals("签收", dto.getStatus())) {
						if(!isContainsSensitive(dto.getContext())){
							LOGGER.error("运单号："+waybillNo + "同步营业部外发签收记录开始");
							//调用营业部签收接口
							//必须字段waybillNo  situation signGoodsQty signTime operator operatorCode operateOrgName operateOrgCode
							ArriveSheetEntity entity = new ArriveSheetEntity();
							entity.setWaybillNo(waybillNo); //运单号
							//流水号 added by 257220 一票多件外发
							entity.setSerialNo(record.getSerialNo());
							entity.setSituation(SignConstants.NORMAL_SIGN);//正常签收
							//entity.setSignGoodsQty(waybillEntity.getGoodsQtyTotal()); //开单件数
							//签收件数   安件签收 件数为1
							entity.setSignGoodsQty(1); 
							entity.setSignTime(DateUtils.convert(dto.getFtime())); //100传过来的时间
							//entity.setOperator(record.getOperatorName()); //营业部外发绑定人名称 签收接口中赋值
							entity.setOperatorCode(record.getOperatorCode()); //绑定人工号
							entity.setOperateOrgCode(record.getOrgCode()); //绑定部门编码
							entity.setOperateOrgName(record.getOrgName()); //绑定部门名称
							entity.setSignNote(TransferConstants.AUTO_SIGN);
							
							try{
								LOGGER.error("运单号："+waybillNo + "调用营业部签收接口开始");
								expSignToTfrService.addExpressArrivesheetForTfr(entity);
								LOGGER.error("运单号："+waybillNo + "调用营业部签收接口结束");
							} catch(Exception e) {
								e.printStackTrace();
								LOGGER.error(waybillNo + ":营业部外发调用签收接口异常");
								this.saveExceptionInfo(waybillNo,"600","营业部外发调用签收接口异常" + e.getMessage());
								//throw new TfrBusinessException(waybillNo + ":营业部外发调用签收接口异常");
							}
						}else{
							//营业部外发签收包含敏感词汇信息处理
							ExpressPushInfoDto expressPushInfoDto = setExpressEntityInfo(agentWaybillNo, agentCompany, record,
									waybillNo, dto, PartiallineConstants.EXIST_SENSITIVE_WORD_SALES_REASON,PartiallineConstants.EXIST_SENSITIVE_WORD);
							//保存对应消息到指定数据库
							this.savePushExpressInfo(expressPushInfoDto);
						}
						LOGGER.error("运单号："+waybillNo + "同步营业部外发签收记录结束");
					}
				}
			}
		}
	}
	
	
	/**
	 * @author alfred
	 * @date 2016-04-20 21:23:55
	 * @function 韩国国际件出口轨迹
	 * @param
	 * @return
	 */
	@Override
	public void addWaybillTrackForInternal(String waybillNo, List<AgentWaybillTrackDto> trackList) {
		InternationalTrackingEntity entity;
		if(null != waybillNo){
			for(AgentWaybillTrackDto dto:trackList) {
				try {
					
					entity = new InternationalTrackingEntity();
					String id = UUIDUtils.getUUID();
					entity.setId(id);
					entity.setOperateType(dto.getStatus());
					entity.setOperateTime(DateUtils.convert(dto.getFtime()));
					entity.setWaybillNo(waybillNo);
					if("22".equals(dto.getStatus())){
						entity.setNote("地址更改");
					}else if("23".equals(dto.getStatus())){
						entity.setNote("派送未完成 ");
					}else if("24".equals(dto.getStatus())){
						entity.setNote("地址错误");
					}
					agentWaybillTrackDao.addInternationTrackings(entity);
					
					//插入记录到给WQS轨迹表
					try{
						//查询运单信息
						WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
						
						SynTrackingEntity track = new SynTrackingEntity();
						track.setId(UUIDUtils.getUUID());
						// 运单
						track.setWayBillNo(waybillNo);
						// 发生时间
						track.setOperateTime(DateUtils.convert(dto.getFtime()));
						track.setCreateDate(new Date());
						track.setModifyDate(new Date());
						// 跟踪信息描述
						//track.setTrackInfo("已进行返货开单，返货单号【"+waybill.getOrderNo()+"】");
						//韩国件的操作类型
						TrackingEventTypeEnum trackEnum = getTrackingEventTypeEnum(dto.getStatus());
						// 跟踪信息描述
						track.setTrackInfo(trackEnum == null ? "" : trackEnum.getBizName());
						// 开单部门所在城市
						OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
								.queryOrgAdministrativeInfoByCode(waybillEntity.getCreateOrgCode());
						if (org != null) {
							track.setOperateCity(org.getCityName());
						}
						// 站点类型
						/*track.setOrgType(String.valueOf(1));
						// 操作部门编码
						track.setOrgCode(stock.getOrgCode());
						// 操作部门名称
						track.setOrgName(org.getName());
						//当前操作人工号
						track.setOperatorCode(stock.getOperatorCode());
						//当前操作人姓名
						track.setOperatorName(stock.getOperatorName());*/
						String eventType = (trackEnum == null ? "" : trackEnum.getWqsTrackCode());
						// 事件
						track.setEventType(eventType);
		
						//2016-8-11 16:08:23 311396 加上目的部门名称
						track.setDestinationDeptName(waybillEntity.getCustomerPickupOrgName());
						pushTrackForCaiNiaoService.addSynTrack(track);
					}catch(Exception e){
						LOGGER.error("插入中转轨迹推送表 (类型：韩国件)失败，原因：" + e.getMessage());	
					}
				} catch (Exception e) {
					e.printStackTrace();
					e.printStackTrace();
					LOGGER.error(waybillNo +":韩国国际件同步轨迹失败！");
					this.saveExceptionInfo(waybillNo,"600","韩国国际件同步轨迹失败" + e.getMessage());
				}
			}
		}
		
	}

	
	/**
	 * 韩国件操作类型转换
	 * @author 311396 wwb
	 * 2016-6-29  下午5:38:33
	 *
	 */
	private TrackingEventTypeEnum getTrackingEventTypeEnum(String status) {
		/**
		 * 清关到达			ARRIVAL_CLEARANCE	 		10
		 * 转运场到达			ARRIVETRANFIELD			 	11
		 * 到达目的站			ARRIVE_DESTINATION			12
		 * 派送				LDP_TRACK_DELIVER		 	13
		 * 签收				SIGNED						14
		 * 派送异常			LDPDELIVERERROR 			20	
		 * 已退回				RETURN						21
		 * 派送拉回-地址更改		LDPDELIVERERROR				22	
		 * 派送拉回-派送未完成	LDPDELIVERERROR				23
		 * 派送拉回-地址错误		LDPDELIVERERROR				24
	 	 */
		
		return PartiallineConstants.enumMap.get(PartiallineConstants.TRACK_PREFIX + status);
	}
	
	
	/**
	 * @author nly
	 * @date 2015年2月2日 下午6:31:19
	 * @function 转换快递100的操作类型
	 * @param operateType
	 * @return
	 */
	private String convertOperateType(String status) {
		String operateType = "";
		//快递100状态：在途		揽收		疑难		签收		退签		派件		退回
		//FOSS外发轨迹状态：1：到达网点（到达）、2：派送出仓（外发）、3：派件成功（派送）、4：派件失败（派送失败）、5：货物拉回德邦、99：拒签。
		if(StringUtils.equals("在途", status)) {
			operateType = "2";
		} else if(StringUtils.equals("揽收", status)){
			operateType = "1";
		} else if(StringUtils.equals("疑难", status)){
			operateType = "4";
		} else if(StringUtils.equals("派件", status)){
			operateType = "3";
		} else if(StringUtils.equals("退签", status) || StringUtils.equals("退回", status)){
			operateType = "5";
		} 
		
		return operateType;   
	}
	/**
	 * @author gongjp
	 * @date 2015年11月10日 下午15:52:19
	 * @function 转换快递100的操作类型
	 * @param operateType
	 * @return int 
	 */
	private int convertOperateType1(String status) {
		//0在途中、1已揽收、2疑难、3派件，4已签收、5退签、退回
		int operateType =0;
		if(StringUtils.equals("在途", status)) {
			operateType = 0;
		} else if(StringUtils.equals("揽收", status)){
			operateType = 1;
		} else if(StringUtils.equals("疑难", status)){
			operateType = 2;
		} else if(StringUtils.equals("派件", status)){
			operateType = ConstantsNumberSonar.SONAR_NUMBER_3;
		} else if(StringUtils.equals("签收", status)){
			operateType = ConstantsNumberSonar.SONAR_NUMBER_4;
		} else if(StringUtils.equals("退签", status) || StringUtils.equals("退回", status)){
			operateType = ConstantsNumberSonar.SONAR_NUMBER_5;
		} 
		return operateType;   
	}
	
	/**
	 * @author nly
	 * @date 2015年2月10日 上午10:41:34
	 * @function 保存异常信息
	 * @param waybillNo
	 * @param code
	 * @param msg
	 */
	private void saveExceptionInfo(String waybillNo,String code,String msg) {
		AgentWaybillExceptionDto exceptionDto = new AgentWaybillExceptionDto();
		try{
			exceptionDto.setId(UUIDUtils.getUUID());
			exceptionDto.setWaybillNo(waybillNo);
			exceptionDto.setCode(code);
			if(msg.length() > ConstantsNumberSonar.SONAR_NUMBER_900) {
				exceptionDto.setMessage(msg.substring(0, ConstantsNumberSonar.SONAR_NUMBER_900));
			} else {
				exceptionDto.setMessage(msg);
			}
			exceptionDto.setType(TransferConstants.EXP_1002FOSS_AGENTWAYBILLTRACK);
			agentWaybillTrackDao.addExceptionInfo(exceptionDto);
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.info("保存异常信息失败");
		}
	}
	
	//TODO FOSS推轨迹给100部分
	/**
	 * @author nly
	 * @date 2015年1月31日 下午2:43:04
	 * @function 统计运单轨迹信息
	 */
	@Override
	public void saveWaybillTrack() {
		//每次查询定量的运单轨迹
		int count = ConstantsNumberSonar.SONAR_NUMBER_3;
		//查询快递100需请求的运单轨迹
		List<AgentWaybillNoEntity> waybillList = agentWaybillTrackDao.queryWaybillNo(count);
		for(AgentWaybillNoEntity waybill:waybillList) {
			this.addTrack(waybill);
		}
	}
	/**
	 * @author nly
	 * @date 2015年1月31日 下午6:30:25
	 * @function 保存一个运单的轨迹及更新运单信息
	 * @param waybill
	 */
	@Transactional
	public void addTrack(AgentWaybillNoEntity waybill) {
		//需保存的轨迹列表
		List<AgentWaybillTrackEntity> trackList = new ArrayList<AgentWaybillTrackEntity>();		
		
		String waybillNo = waybill.getWaybillNo();
		Date trackTime = waybill.getTrackTime();
		List<WayBillNoLocusDTO> list = wayBillNoLocusService.getWayBillNoLocus(waybillNo);
		
		//轨迹处理
		for (WayBillNoLocusDTO wayBillDTO : list)
		{
			Date operateTime = wayBillDTO.getOperateTime();
			//当操作时间小于等于轨迹时间时，不再保存至轨迹表
			if(operateTime.compareTo(trackTime) <= 0) {
				continue;
			}
			//更新轨迹最新时间
			if(operateTime.compareTo(waybill.getTrackTime()) > 0) {
				waybill.setTrackTime(operateTime);
			}
			
			AgentWaybillTrackEntity trackInfo = new AgentWaybillTrackEntity();
			trackInfo.setId(UUIDUtils.getUUID());
			trackInfo.setWaybillNo(wayBillDTO.getWaybillNo());
			trackInfo.setOperateDeptCode(wayBillDTO.getOperateOrgCode());
			trackInfo.setOperateDeptName(wayBillDTO.getOperateOrgName());
			trackInfo.setOperateCityCode(wayBillDTO.getOperateCityCode());
			trackInfo.setOperateCityName(wayBillDTO.getOperateCityName());
			trackInfo.setOperateTime(wayBillDTO.getOperateTime());
			trackInfo.setOperatorName(wayBillDTO.getOperateName());
			trackInfo.setOperateType(wayBillDTO.getOperateType());
			trackInfo.setOperateContent(wayBillDTO.getOperateContent());
			trackInfo.setDeliverName(wayBillDTO.getDeliveryName());
			trackInfo.setDeliverPhone(wayBillDTO.getDeliveryPhone());
			trackInfo.setRemark(wayBillDTO.getNotes());
			
			trackList.add(trackInfo);
		}
		
		// 此业务规则待定！运单无效或运单已签收或推送时间超过N天，则需更新isEnd为Y
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		if(waybillEntity == null){
			waybill.setIsEnd("Y");
		} else {
			//查询签收结果
			WaybillSignResultEntity entity = new WaybillSignResultEntity();
			//设置查询参数
			entity.setWaybillNo(waybillNo);
			entity.setActive(FossConstants.YES);
			WaybillSignResultEntity waybillSignResultEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(entity);
			if(waybillSignResultEntity != null) {
				waybill.setIsEnd("Y");
			} else {
				//TODO
			}
		}
		
		//保存轨迹
		for(AgentWaybillTrackEntity entity : trackList) {
			agentWaybillTrackDao.addOneTrack(entity);
		}
		//更新运单号信息
		agentWaybillTrackDao.updateWaybillNoInfo(waybill);
	}
	/**
	 * @author nly
	 * @date 2015年2月2日 上午9:55:14
	 * @function 保存快递100推送的运单号
	 * @param waybillNoList
	 */
	@Override
	public void addWaybillNos(List<String> waybillNoList) {
		//TODO 因不确定快递100每次传递的运单号数量，故此处最好使用批量插入。因紧迫，有时间再优化
		for(String waybillNo:waybillNoList) {
			AgentWaybillNoEntity entity = new AgentWaybillNoEntity();
			entity.setId(UUIDUtils.getUUID());
			entity.setWaybillNo(waybillNo);
			agentWaybillTrackDao.addWaybillNo(entity);
		}
	}
	/**
	 * 根据代理单号及代理公司code更新 
	 * @author 257220
	 * @date 2015-7-29下午2:17:29
	 */
	public void updatePrintWaybillInfoByAgentInfo(String agentWaybillNo,String kd100CompanyCode){
		if(StringUtils.isEmpty(agentWaybillNo)||StringUtils.isEmpty(kd100CompanyCode)){
			return;
		}
		agentWaybillTrackDao.updatePrintWaybillInfoByAgentInfo(agentWaybillNo,kd100CompanyCode);
	}
	
	public List<ExpressPushInfoDto>queryPushExpressListByWaybillNo(String waybillNo,String reasonCode){
		return this.agentWaybillTrackDao.queryPushExpressListByWaybillNo(waybillNo, reasonCode);
	}

	/**
	 * 屏蔽备注信息中数据字典中所包含的敏感词汇，电话电话，联系方式，代理公司名称，数字连续六位以上电话号码
	 * @return result
	 * @author 271297
	 * @date 2015-7-14下午8:10:45
	 */
	    private String shieldingSensitive(String str){
			if(str == null){
				return str;
			}
			List<DataDictionaryValueEntity> list = dataDictionaryValueService.queryDataDictionaryValueByTermsCode(DictionaryConstants.SHIELDING_SENSITIVE_KD100);
			if(list != null&&list.size() != 0){
				for(DataDictionaryValueEntity dataDictionaryValueEntity : list){
				    String value = dataDictionaryValueEntity.getValueName();
				    str=str.replaceAll(value,""); 
					}
				}
			str=str.replaceAll("[0-9]{6,}","");
			return str;
		}
		
	/**
	 * 是否包含数据字典配置的敏感字符
	 * @return true:包含敏感字段；false:不包含敏感字段
	 * @author 257220
	 * @date 2015-7-10下午3:10:45
	 */
	private boolean isContainsSensitive(String str){
		if(str == null){
			return false;
		}
		List<DataDictionaryValueEntity> list = dataDictionaryValueService.queryDataDictionaryValueByTermsCode(DictionaryConstants.NONEED_AUTOSIGN_FROM_KD100);
			
		if(list != null&&list.size() != 0){
			for(DataDictionaryValueEntity dataDictionaryValueEntity : list){
				String value = dataDictionaryValueEntity.getValueName();
				if(str.contains(value)){
					return true;
				}
			}
		}
		return false;
	}
	public void setAgentWaybillTrackDao(IAgentWaybillTrackDao agentWaybillTrackDao) {
		this.agentWaybillTrackDao = agentWaybillTrackDao;
	}

	public void setWayBillNoLocusService(
			IWayBillNoLocusService wayBillNoLocusService) {
		this.wayBillNoLocusService = wayBillNoLocusService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}
	public void setLdpExternalBillService(
			ILdpExternalBillService ldpExternalBillService) {
		this.ldpExternalBillService = ldpExternalBillService;
	}
	public void setLdpExternalBillTrackService(
			ILdpExternalBillTrackService ldpExternalBillTrackService) {
		this.ldpExternalBillTrackService = ldpExternalBillTrackService;
	}
	public void setPrintAgentWaybillService(
			IPrintAgentWaybillService printAgentWaybillService) {
		this.printAgentWaybillService = printAgentWaybillService;
	}
	public void setFossToAgentWaybillService(
			IFOSSToAgentWaybillService fossToAgentWaybillService) {
		this.fossToAgentWaybillService = fossToAgentWaybillService;
	}
	public void setAirAgencySignService(IAirAgencySignService airAgencySignService) {
		this.airAgencySignService = airAgencySignService;
	}
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}
	public void setExpSignToTfrService(IExpSignToTfrService expSignToTfrService) {
		this.expSignToTfrService = expSignToTfrService;
	}
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public IDataDictionaryValueService getDataDictionaryValueService() {
		return dataDictionaryValueService;
	}

	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	public ISignChangeService getSignChangeService() {
		return signChangeService;
	}

	public void setSignChangeService(ISignChangeService signChangeService) {
		this.signChangeService = signChangeService;
	}
	
	public void setPushTrackForCaiNiaoService(
			IPushTrackForCaiNiaoService pushTrackForCaiNiaoService) {
		this.pushTrackForCaiNiaoService = pushTrackForCaiNiaoService;
	}

	/**
	 * @author gongjp
	 * @Date 2015-08-05
	 * 根据运单号和反签收的状态 查询反签收表
	 * 人工反签收后，FOSS不再接收快递100推送的货物状态信息
	 * @param waybillNo
	 * @param status
	 * @return boolean
	 */
	public boolean  queryReversSign(String waybillNo){
		//反签收状态 (已受理)
	    String status = "CHANGE_SIGN_PASSED";
		List<SignRfcEntity> list = signChangeService.queryWayBillRfcListBy(waybillNo, status);
		   boolean rSign = false;
		    for(SignRfcEntity rfcEntity:list){
		      //反签收；RfcType包含REVERSESIGN时是反签收，其他为变更
		      if(rfcEntity.getRfcType().contains("REVERSESIGN")){
		    	  rSign = true;   
		      }
		    }
			return rSign;
	}
	
	/**
	 *@author gongjp
	 * @date 2015年10月21日 下午20:24:34
	 * @function 保存异常信息
	 * @param agentWaybillNo
	 * @param agentCompany
	 * @param record
	 * @param waybillNo
	 * @param dto
	 * @param tips
	 * @return
	 */
	private ExpressPushInfoDto setExpressEntityInfo(String agentWaybillNo,
			String agentCompany, PrintAgentWaybillRecordEntity record,
			String waybillNo, AgentWaybillTrackDto dto, String tips,String reasonCode) {
		ExpressPushInfoDto expressPushInfoDto=new ExpressPushInfoDto(UUIDUtils.getUUID(), waybillNo, agentWaybillNo, agentCompany, reasonCode, tips, convertOperateType1(dto.getStatus()), record.getPrintType(), tips+dto.getContext());
		return expressPushInfoDto;
	}
	/**
	 * @author gongjp
	 * @date 2015年10月19日 上午10:24:34
	 * @function 保存快递100推送异常信息
	 * @param expressPushInfoDto
	 */
	private void savePushExpressInfo(ExpressPushInfoDto expressPushInfoDto) {
		agentWaybillTrackDao.savePushExpressInfo(expressPushInfoDto);
		
	}

	/**
	 * @author alfred
	 * @date 2016-04-22 21:22:54
	 * @function 查询国际件轨迹
	 * @param waybillNo
	 */
	@Override
	public List<InternationalTrackingEntity> queryInterTrackingEntity(
			String waybillNo) {
		return agentWaybillTrackDao.queryInterTrackingsEntity(waybillNo);
	}

	
}
