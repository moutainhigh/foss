package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.inteface.domain.dop.SignMessageRequest;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto;
import com.deppon.foss.module.pickup.sign.api.server.dao.IDopSignDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IDopSignService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.DopSignEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;

public class DopSignService implements IDopSignService {

	/**
	 * 记录日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DopSignService.class);
	
	private IDopSignDao dopSignDao;	
	
	/**
	 *  运单签收结果service
	 */
	private IWaybillSignResultService waybillSignResultService;

	
	public void setDopSignDao(IDopSignDao dopSignDao) {
		this.dopSignDao = dopSignDao;
	}
	
	/**
	 * 查询保存dop传来的签收信息表
	 * foss-zhuliangzhi 2015-10-22
	 */
	@Override
	public DopSignEntity queryDopCacheByParams(SignDto dto) {
		if(dto!=null){
			String waybillNo = dto.getWaybillNo();
			if(StringUtils.isNotEmpty(waybillNo)){
				List<DopSignEntity> dtos=dopSignDao.queryDopListByWaybillNo(dto);
				if(!dtos.isEmpty()){
					return dtos.get(0);
				}
			}
		}
		return null;
	}

	/**
	 * 查询反签收记录
	 */
	public boolean queryDopSignRfc(SignDto dto) {
		// TODO Auto-generated method stub
		boolean flag=false;
		if(dto!=null){
			String waybillNo = dto.getWaybillNo();
			long num=dopSignDao.queryDopSignRfc(waybillNo);
			if(num!=0){
				flag=true;
			}
		}
		return flag;
	}

	/**
	 * FOSS中的实际签收信息返回给DOP add zhuliangzhi 2015-12-15
	 */
	@Override
	public void sendDopSignMessage(String waybillNo,String situation) {
		if(situation==null||situation==""){
			throw new SignException("运单签收状态为空异常");
		}
		LOGGER.info("签收信息返回给DOP，  运单号： "+waybillNo+" FOSS系统内的签收状态： "+situation);
		WaybillSignResultEntity waybillSignResultEntity=new WaybillSignResultEntity();
		waybillSignResultEntity.setActive("Y");
		waybillSignResultEntity.setWaybillNo(waybillNo);
		try {
			waybillSignResultEntity=waybillSignResultService.queryWaybillSignResultByWaybillNo(waybillSignResultEntity);
			//准备消息头信息
			AccessHeader header = createAccessHeader(waybillNo);
			//创建具体消息
			SignMessageRequest request=new SignMessageRequest();
				String signSituation="";
				if("NORMAL_SIGN".equals(situation)){
					signSituation="01";
				}else if("UNNORMAL_BREAK".equals(situation)){
					signSituation="02";
				}else if("UNNORMAL_DAMP".equals(situation)){
					signSituation="03";
				}else if("UNNORMAL_POLLUTION".equals(situation)){
					signSituation="04";
				}else if("UNNORMAL_GOODSHORT".equals(situation)){
					signSituation="05";
				}else if("UNNORMAL_ELSE".equals(situation)){
					signSituation="06";
				}else if("UNNORMAL_SAMEVOTEODD".equals(situation)){
					signSituation="07";
				}else if("RFC".equals(situation)){
					signSituation="10";
				}else{
					signSituation="01";
				}
				LOGGER.info("家装运单返回给DOP的签收状态 ： "+signSituation);
				request.setMailNo(waybillSignResultEntity.getWaybillNo());
				request.setFossSignStatus(signSituation);
				request.setFossSignMemo(waybillSignResultEntity.getSignNote());
				request.setFossSignTime(waybillSignResultEntity.getSignTime());
				// 发送请求
				ESBJMSAccessor.asynReqeust(header, request);
				LOGGER.info("家装运单： "+waybillNo+" 返回给DOP的签收信息完成"+request);
		} catch (Exception e) {
			LOGGER.error("FOSS发送DOP签收信息 通过ESB发送请求--报错:"+waybillNo+"----"+e.getMessage());
		}
		LOGGER.info("FOSS发送DOP签收信息  完成   "+waybillNo);


	}
	
	/**
	 * FOSS中的实际签收信息返回给DOP add zhuliangzhi 2015-12-15
	 */
	@Override
	public void sendDopRfcSignMessage(String waybillNo){
		try {
			LOGGER.info("家装运单反签收信息返回给DOP，  运单号： "+waybillNo+" 开始---");
			//准备消息头信息
			AccessHeader header = createAccessHeader(waybillNo);
			//创建具体消息
			SignMessageRequest request=new SignMessageRequest();
			request.setMailNo(waybillNo);
			request.setFossSignMemo("家装运单反签收 ，运单： "+waybillNo);
			request.setFossSignStatus("10");//回传反签收标记
			request.setFossSignTime(new Date());
			// 发送请求
			ESBJMSAccessor.asynReqeust(header, request);
			LOGGER.info("家装运单反签收信息返回给DOP，  运单号： "+waybillNo+" 完成---");
		} catch (Exception e) {
			LOGGER.error("FOSS发送DOP签收信息 通过ESB发送请求报错:"+waybillNo+e.getMessage());
		}
		LOGGER.info("FOSS发送DOP签收信息  完成   "+waybillNo);


	}
	
	private AccessHeader createAccessHeader(String waybillNo) {
		AccessHeader header = new AccessHeader();
		//business code 根据esb提供
		header.setEsbServiceCode("ESB_FOSS2ESB_OUTFIT_SYSTEM_RECEIVES");
		//版本随意
		header.setVersion("V151220");
		//business id 随意
		header.setBusinessId(waybillNo);
		//运单号放在消息头中传给 waybillNo
		header.setBusinessDesc1(waybillNo);
		return header;
	}

	/**
	 * Sets the 运单签收结果service.
	 *
	 * @param waybillSignResultService the new 运单签收结果service
	 */
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	/**
	 * 更新反签收标记
	 */
	@Override
	public int updateRfc(String waybillNo) {
		// TODO Auto-generated method stub
		return dopSignDao.updateRfc(waybillNo);
	}	
	
	
	
}
