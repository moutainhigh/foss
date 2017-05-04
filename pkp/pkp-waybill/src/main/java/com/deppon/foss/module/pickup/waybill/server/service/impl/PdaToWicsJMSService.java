package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.inteface.domain.wics.PdaInfo;
import com.deppon.esb.inteface.domain.wics.PdaInfoRequest;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFreightRouteService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FreightRouteLineDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IEamDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPdaToWicsJMSService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISysConfigService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPendingService;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.ToWicsDto;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.TimeLookDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.vo.ExpressAutoMakeupVo;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class PdaToWicsJMSService implements IPdaToWicsJMSService {
	protected final static Logger LOG = LoggerFactory.getLogger(PdaToWicsJMSService.class);
	private IEamDao eamDao;
	public void setEamDao(IEamDao eamDao) {
		this.eamDao = eamDao;
	}
	private IWaybillPendingService waybillPendingService;
	public void setWaybillPendingService(
			IWaybillPendingService waybillPendingService) {
		this.waybillPendingService = waybillPendingService;
	}

	/**
	 * 获取相关配置参数
	 */
	private ISysConfigService pkpsysConfigService;

	public void setPkpsysConfigService(ISysConfigService pkpsysConfigService) {
		this.pkpsysConfigService = pkpsysConfigService;
	}
	private IFreightRouteService freightRouteService ;
	public void setFreightRouteService(IFreightRouteService freightRouteService) {
		this.freightRouteService = freightRouteService;
	}

	/**
	 * 定时任务推送PDA开单后的运单时效
	 * @author YangxiaoLong
	 * @date
	 */
	// 要查询的JobId
	public static final String queryJobId = WaybillConstants.UNKNOWN;

	@Override
	public void batchTimeSetExpressJobs(){
		// 初始化时首先设置批处理条数为100条
		String queryNum = "1000";
		ExpressAutoMakeupVo vo = new ExpressAutoMakeupVo();
		// 后台设置当前每个周期要处理的订单数，后期通过数据字典配置
		ConfigurationParamsEntity configQueryNum = pkpsysConfigService.queryConfigurationParamsByEntity
				(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,WaybillConstants.PKP_EXPRESS_TIMESET_CYCLE,
						FossConstants.ROOT_ORG_CODE);
		if(configQueryNum != null && StringUtils.isNotBlank(configQueryNum.getConfValue())){
			queryNum = configQueryNum.getConfValue();
		}
		vo.setResultNum(Integer.parseInt(queryNum));
		while (vo.getResultNum() == Integer.parseInt(queryNum)) {
			String jobId = UUIDUtils.getUUID();
			// 更新一定数量的JobId
			vo = updateGenearateUnActiveEWaybillForJobTopNum(jobId, queryNum);
			// 根据JobId查询待处理信息
			// 此处注意更新一定数量的jobId然后根据Jobid查询待处理信息,如何将Jobid与待处理信息联系到一起的
			List<TimeLookDto> list = eamDao.querytimeLookActiveEamByJobID(vo.getJobId());
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					// 执行推送任务
					sendInFomationToWics(list.get(i));
				}
			}
		}
	}

	/**
	 * 每次更新一定数量待推送的运单的任务编号 JOBID
	 * @author YangxiaoLong
	 * @date 2015-09-08
	 */
	@Transactional
	public ExpressAutoMakeupVo updateGenearateUnActiveEWaybillForJobTopNum(
			String jobId, String queryNum) {
		ExpressAutoMakeupVo vo = new ExpressAutoMakeupVo();
		vo.setJobId(jobId);
		vo.setQueryNum(queryNum);
		vo.setQueryJobId(queryJobId);
		eamDao.updateJobIDtimeLookByRowNum(vo);
		return vo;
	}
	
	/**
	 * 
	 * @param waybillNo
	 * @author 220125  YangXiaolong
	 */
	@Override
	 public void sendInFomationToWics(List<TimeLookDto> list){	
		 PdaInfoRequest request=new PdaInfoRequest();
		 for(int i = 0; i < list.size(); i++){
			// PdaInfo  pdaInfo=new PdaInfo();
			 ToWicsDto toWicsDto=queryPendingExpress(list.get(i).getWaybillNo());
				PdaInfo  pdaInfo=new PdaInfo();
				if(toWicsDto.getAgingOne()==null){
					//虽然不发请求，扫描表的数据因为要删除
					eamDao.timeLookDelete(toWicsDto.getWaybillNo());
					LOG.info("====查询时效为空，默认次日补录=====");
				}else{
					if(toWicsDto.getWaybillNo()!=null){
					 pdaInfo.setWaybillNo(toWicsDto.getWaybillNo());
						}
					//原则上不会发生null的情况，还是进行判空，以免esb请求报错，发生数据滞留
					pdaInfo.setFirstFinallyAging(toWicsDto.getAgingTwo()==null?0:toWicsDto.getAgingTwo());
					pdaInfo.setFirstSecondAging(toWicsDto.getAgingOne()==null?0:toWicsDto.getAgingOne());	
					request.getPdaInfos().add(pdaInfo);	
					eamDao.timeLookDelete(toWicsDto.getWaybillNo());
					LOG.info("====查询到时效，删除扫描表中记录=====");
		 }
		}
		 //默认集合中第一个单的单号为businessId
		 if(null!=request.getPdaInfos() && request.getPdaInfos().size()>0){
			AccessHeader header=createAccessHeader(list.get(0));
			try {
				ESBJMSAccessor.asynReqeust(header, request);
				LOG.info("================消息推送成功!");
			} catch (Exception e) {
				// 对异常进行处理
				LOG.error("PDA开单推送消息至WICS系统失败，错误详情：", e);
			}
		 }
	}
	
	/**
	 * 创建消息头
	 * @param waybillNo
	 * @return
	 * @author 220125
	 */
	private AccessHeader createAccessHeader(TimeLookDto timeLookDto) {
		AccessHeader header = new AccessHeader();
		//business code 根据esb提供
		//ESB_FOSS2ESB_RECEIVE_ORDERSTATUS
		LOG.info("PDA开单时推送到WICS系统编码为：ESB_FOSS2ESB_PDAINFO_ERC版本号:0.1");
		header.setEsbServiceCode("ESB_FOSS2ESB_PDAINFO_ERC");
		//版本随意  1.0
		header.setVersion("1.0");
		//business id 随意
		header.setBusinessId(timeLookDto.getWaybillNo());
		//运单号放在消息头中传给oa waybillNo
		header.setBusinessDesc1(timeLookDto.getWaybillNo());
		return header;
	}
	/**
	 * 查询时效方法
	 * @param waybillNo
	 * @return
	 * @author 220125
	 * */
	private ToWicsDto queryPendingExpress(String waybillNo) {
		WaybillPendingEntity waybillPendingEntity=waybillPendingService.queryPendingByNo(waybillNo);
		ToWicsDto toWicsDto=new ToWicsDto();
		toWicsDto.setWaybillNo(waybillNo);
		if(null!=waybillPendingEntity){
			    toWicsDto.setWaybillNo(waybillPendingEntity.getWaybillNo());
				//出发部门
				String rcode =waybillPendingEntity.getReceiveOrgCode();
				//收货部门
				String ccode =waybillPendingEntity.getCustomerPickupOrgCode();
				// 产品
				String pcode = waybillPendingEntity.getProductCode();
				//开单时间
				Date bdate = waybillPendingEntity.getBillTime();
				//查找走货路径				
				List<FreightRouteLineDto> lines=null;
				try{
					lines=freightRouteService.queryEnhanceFreightRouteBySourceTarget(rcode,ccode,pcode,bdate);	
				}catch(Exception e){
					LOG.info("获取走货路径失败！");
				}
				if(CollectionUtils.isNotEmpty(lines)){
					//出发外场
				    String  oneTargetCode =null;
				    //下个外场
				    String  twoTargetCode =null;
				    //最终外场
				    String lastSourceCode=null;
					if(lines.size()>=2){
						for(int i=0;i<=lines.size();i++){
						  if(i==0){
							  oneTargetCode=  lines.get(i).getTargetCode();
						  }
						 if(i==1){
							  twoTargetCode =  lines.get(i).getTargetCode();
							  FreightRouteEntity freightRoute = new FreightRouteEntity();
							  freightRoute.setOrginalOrganizationCode(oneTargetCode);
							  freightRoute.setDestinationOrganizationCode(twoTargetCode);
							  freightRoute.setTransType(pcode);
							  freightRoute.setActive("Y");
							  //查询时效
							  List<FreightRouteEntity> fys= freightRouteService.querySimpleFreightRouteListByCondition(freightRoute,0,NumberConstants.NUMBER_100);
							  if(CollectionUtils.isNotEmpty(fys)){
								  FreightRouteEntity route = fys.get(0);
								  Long aging=route.getAging()/ NumberConstants.NUMBER_1000;
								  if(aging!=null){
									  toWicsDto.setAgingOne(aging);
								  }
							  }
						  }
						  if(i==lines.size()-1){
							  lastSourceCode= lines.get(i).getSourceCode();
							  FreightRouteEntity freightRoute = new FreightRouteEntity();
							  freightRoute.setOrginalOrganizationCode(oneTargetCode);
							  freightRoute.setDestinationOrganizationCode(lastSourceCode);
							  freightRoute.setTransType(pcode);
							  freightRoute.setActive("Y");
							  //查询时效
							  List<FreightRouteEntity> fys= freightRouteService.querySimpleFreightRouteListByCondition(freightRoute,0,NumberConstants.NUMBER_100);
							  if(CollectionUtils.isNotEmpty(fys)){
								  FreightRouteEntity route = fys.get(0);
								  Long aging=route.getAging()/ NumberConstants.NUMBER_1000;
								  if(aging!=null){
									  toWicsDto.setAgingTwo(aging);
								  }
							  }
						  }
						 
						}
				 }
			 }
		}
		return toWicsDto ;
	}

	@Override
	public void sendInFomationToWics(TimeLookDto timeLookDto){
		// TODO Auto-generated method stub
		
	}
}
