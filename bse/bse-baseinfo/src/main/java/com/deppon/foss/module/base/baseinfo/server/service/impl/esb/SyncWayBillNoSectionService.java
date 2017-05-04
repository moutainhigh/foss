package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import com.deppon.foss.module.base.baseinfo.api.server.service.IWayBillNoSectionService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncWayBillNoSectionService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WayBillNoSectionEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WayBillNoSectionRequestEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.WayBillNoSectionDto;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.util.define.ESBHeaderConstant;
import com.deppon.foss.base.util.define.NumberConstants;
/**
 * 同步FOSS分配的运单号段给CRM的接口
 * @author 262036 HuangWei
 *
 * @date 2015-6-25 下午3:19:48
 */
public class SyncWayBillNoSectionService implements ISyncWayBillNoSectionService{

	/**
	 * 运单号段接口
	 */
    private IWayBillNoSectionService wayBillNoSectionService;
    /**
     * 数据字典接口
     */
    private IDataDictionaryValueService dataDictionaryValueService;
    
    @Context
    protected HttpServletRequest req;
	@Context
	protected HttpServletResponse resp;
	
	public void setWayBillNoSectionService(
			IWayBillNoSectionService wayBillNoSectionService) {
		this.wayBillNoSectionService = wayBillNoSectionService;
	}
	
	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	@Override
	public WayBillNoSectionDto addWayBillNoSection(String rertunString) {
		resp.setHeader(ESBHeaderConstant.VERSION, req.getHeader(ESBHeaderConstant.VERSION));
		resp.setHeader(ESBHeaderConstant.ESBSERVICECODE, "FOSS_ESB2FOSS_SYNC_WAYBILLTHEM_INFO2FOSS");
		resp.setHeader(ESBHeaderConstant.REQUESTID, req.getHeader(ESBHeaderConstant.REQUESTID));
		resp.setHeader(ESBHeaderConstant.BUSINESSID, req.getHeader(ESBHeaderConstant.BUSINESSID));
		resp.setHeader(ESBHeaderConstant.MESSAGEFORMAT, req.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
		resp.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, req.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
		resp.setHeader(ESBHeaderConstant.BACKSERVICECODE, req.getHeader(ESBHeaderConstant.BACKSERVICECODE));
		resp.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
		resp.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
		WayBillNoSectionDto dto = new WayBillNoSectionDto();
		JSONObject obj = JSONObject.fromObject(rertunString);
		WayBillNoSectionRequestEntity request = (WayBillNoSectionRequestEntity) JSONObject.toBean(obj,WayBillNoSectionRequestEntity.class);
		try{
			if(null != request){
				Long applyCountL = request.getApplyCount();
				//313353 sonar
				WayBillNoSectionDto result = this.sonarSplitOne(dto, request, applyCountL);
				if(null != result){
					return result;
				}
				
				 WayBillNoSectionEntity entity = new WayBillNoSectionEntity();
				 entity.setSystemName(request.getSystemName() == null ? null : request.getSystemName().trim().toUpperCase());
				 entity.setChannelSourceCode(request.getChannelSourceCode() == null ? null : request.getChannelSourceCode().trim());
				 entity.setCustomerCode(request.getCustomerCode() == null ? null : request.getCustomerCode().trim());
				 entity.setApplyCount(applyCountL);
				 entity.setState(NumberConstants.NUMBER_THE_8);//表示接口调用service中的方法，不保存操作用户
				 entity = wayBillNoSectionService.addWayBillNoSection(entity);
				
				 if(entity != null){
					 if(entity.getState() == 1){//成功
						dto.setStartWayBillNo(entity.getWayBillNoStart());
						dto.setEndWayBillNo(entity.getWayBillNoEnd());
						dto.setSuccess(true);
						dto.setMessage("已返回运单号段");
						resp.setHeader(ESBHeaderConstant.RESULTCODE, "1");
						return dto;
					 }else if(entity.getState() == -1){//申请数量的运单号已超过截止号段
						dto.setMessage("申请数量的运单号已超过截止号段，请联系相关人员修改后台配置参数!");
						dto.setSuccess(false);
						resp.setHeader(ESBHeaderConstant.RESULTCODE, "1");
						return dto;
					 }else if(entity.getState() == NumberConstants.NUMBER_THE_3){//申请数量的运单号已超过截止号段
						dto.setMessage("非电子运单大客户不允许分配号段!");
						dto.setSuccess(false);
						resp.setHeader(ESBHeaderConstant.RESULTCODE, "1");
						return dto;
					 }else if(entity.getState() == NumberConstants.NUMBER_THE_10){//加锁失败
						dto.setMessage("加锁失败...");
						dto.setSuccess(false);
						resp.setHeader(ESBHeaderConstant.RESULTCODE, "1");
						return dto;
					 }
				 }else{
					 dto.setSuccess(false);
					 dto.setMessage("请求异常....");
					 resp.setHeader(ESBHeaderConstant.RESULTCODE, "0"); 
				 }
			}else{
				dto.setSuccess(false);
				dto.setMessage("请求参数为空");
				resp.setHeader(ESBHeaderConstant.RESULTCODE, "1");
			}
		}catch(Exception e){
			dto.setSuccess(false);
			dto.setMessage("数据异常....");
			resp.setHeader(ESBHeaderConstant.RESULTCODE, "0");
		}
		return dto;
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private WayBillNoSectionDto sonarSplitOne(WayBillNoSectionDto dto,
			WayBillNoSectionRequestEntity request, Long applyCountL) {
		if(StringUtils.isBlank(request.getSystemName()) && StringUtils.isBlank(request.getChannelSourceCode()) && StringUtils.isBlank(request.getCustomerCode())){
			dto.setMessage("系统、渠道编码、客户编码三者必填其一");
			dto.setSuccess(false);
			resp.setHeader(ESBHeaderConstant.RESULTCODE, "1");
			return dto;
		 }
		 if(applyCountL == null || applyCountL < NumberConstants.NUMBER_100000 || applyCountL % NumberConstants.NUMBER_100000 != 0 || applyCountL > NumberConstants.NUMBER_1000000){
			dto.setMessage("申请数量必须为10万或者10万的整数倍，最多为100万");
			dto.setSuccess(false);
			resp.setHeader(ESBHeaderConstant.RESULTCODE, "1");
			return dto;
		 }
		 
		 if(StringUtils.isNotBlank(request.getChannelSourceCode())){
			 if(dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.CRM_ORDER_TYPE, request.getChannelSourceCode() == null ? null : request.getChannelSourceCode().trim()) == null){
				dto.setMessage("渠道编码在系统中不存在!");
				dto.setSuccess(false);
				resp.setHeader(ESBHeaderConstant.RESULTCODE, "1");
				return dto;
			 }
		 }
		 return null;
	}

}
