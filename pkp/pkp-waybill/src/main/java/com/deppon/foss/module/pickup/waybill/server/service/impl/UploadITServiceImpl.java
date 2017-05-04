package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISysConfigService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IUploadITService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.ITServiceResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.UploadPictureDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.ITServiceException;
import com.deppon.foss.module.pickup.waybill.shared.vo.ITServiceVo;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.itsm.module.remote.foss.server.service.UpdateOrderItem2;
import com.deppon.itsm.module.remote.foss.shared.domain.QuestionAttachment;
import com.deppon.itsm.module.remote.foss.shared.domain.QuestionInfo;
import com.deppon.itsm.module.remote.foss.shared.domain.SendQuestionInfoRequest;
import com.google.gson.Gson;




/**
 * IT服务台一键上报服务类
 * @author WangQianJin
 *
 */
public class UploadITServiceImpl implements IUploadITService {  
	
	/**
	 * 定义日志静态类 
	 * 通过日志工厂类获得该类的日志对象
	 * 使用该日志类的静态方法记录日志
	 */
	protected final static Logger LOG = LoggerFactory.getLogger(WaybillManagerService.class.getName());
	
	/**
	 * 系统配置项服务
	 * 提供与系统配置项相关的服务接口
	 */
	private ISysConfigService pkpsysConfigService;
	
	public void setPkpsysConfigService(ISysConfigService pkpsysConfigService) {
		this.pkpsysConfigService = pkpsysConfigService;
	}
	
	/**
	 * IT服务台调用地址
	 */
	private String itserviceAddress;

	public void setItserviceAddress(String itserviceAddress) {
		this.itserviceAddress = itserviceAddress;
	}

	/**
	 * 调用IT服务台一键上报
	 */
	@Override
	public ITServiceResultDto uploadItService(List<ITServiceVo> itList) {		
		LOG.info("==================开始调用IT服务台进行一键上报==================");
		ITServiceResultDto resultDto=new ITServiceResultDto();		
		try{
			//是否启用一键上报
			if(isStartItServiceUpload()){
				//调用WebService
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				factory.setServiceClass(UpdateOrderItem2.class);
				factory.setAddress(itserviceAddress);
				UpdateOrderItem2 service = (UpdateOrderItem2) factory.create();
				//封装JSON对象
				String json=setterJsonObject(itList);	
				LOG.info("向IT服务传递JSON字符串："+json);
				//调用接口传送
				String result = service.automaticReportQuestion(json);
				//处理返回结果
				operReturnResult(result,resultDto);		
			}else{
				resultDto.setIsSuccess(false);
				resultDto.setFailReason("IT服务台一键上报未启用");
				LOG.error("IT服务台一键上报未启用");
			}			
		}catch(Exception e){			
			LOG.error("IT服务台一键上报失败："+e.getMessage());
			throw new ITServiceException(ITServiceException.IT_UPLOAD_SERVICE_FAIL,e.getMessage());
		}
		LOG.info("==================调用IT服务台进行一键上报结束==================");		
		return resultDto;
	}
	
	/**
	 * 封装JSON字符串
	 * @author WangQianJin
	 * @return
	 */
	private String setterJsonObject(List<ITServiceVo> itList){
		Gson gson = new Gson();
		//定义发送问题list
		SendQuestionInfoRequest req = new SendQuestionInfoRequest();
		List<QuestionInfo> infos = new ArrayList<QuestionInfo>();
		//封装上报信息
		if(itList!=null && itList.size()>0){
			for (int i=0;i<itList.size();i++){
				ITServiceVo itvo=itList.get(i);
				if(itvo!=null){
					//创建单个问题
					QuestionInfo info = new QuestionInfo();
					//上报人工号
					info.setQuestionEmpcode(itvo.getEmpCode());
					//所属系统
					info.setQuestionSystem(itvo.getSystemCode());
					//问题来源系统
					info.setQuestionComFrom(itvo.getSystemCode());
					//问题类别
					info.setQuestionType(itvo.getQuestionType());
					//问题描述
					info.setQuestionDetail(itvo.getShowMessage());
					//问题图片
					if(itvo.getUploadVoucherList()!=null && itvo.getUploadVoucherList().size()>0){
						//附件列表
						List<QuestionAttachment> atts = new ArrayList<QuestionAttachment>();
						//添加附件列表
						for(int j=0;j<itvo.getUploadVoucherList().size();j++){
							QuestionAttachment att = new QuestionAttachment();
							UploadPictureDto pic=itvo.getUploadVoucherList().get(j);
							if(pic!=null){
								boolean flagName=false;
								boolean flagUrl=false;
								if(pic.getProofName()!=null && !"".equals(pic.getProofName())){
									att.setAttachmentName(pic.getProofName());
									flagName=true;
								}
								if(pic.getProofBytes()!=null && !"".equals(pic.getProofBytes())){
									att.setAttachmentUrl(pic.getProofBytes());
									flagUrl=true;
								}
								//当附件名称与附件流字符串都不为空时才添加到附件列表中
								if(flagName && flagUrl){
									atts.add(att);	
								}								
							}								
						}
						info.setQuestionAttachments(atts);
					}						
					infos.add(info);
				}					
			}
			req.setQuestionInfo(infos);
		}			
		//将消息转换为json
		String json = gson.toJson(req);
		return json;
	}
	
	/**
	 * 处理返回结果
	 * @author WangQianJin
	 * @param jstr
	 */
	private void operReturnResult(String jstr,ITServiceResultDto resultDto){		
		//将json字符串转成json对象
		JSONObject jsonObj = JSONObject.parseObject(jstr);
        //获取成功与失败个数
        int successCount = jsonObj.getIntValue("successCount");
        int failedCount = jsonObj.getIntValue("failedCount");
        if(successCount>0 && failedCount==0){
        	resultDto.setIsSuccess(true);
        }else{
        	resultDto.setIsSuccess(false);
        }
        System.out.println("返回结果-successCount:=============================" + successCount);
        System.out.println("返回结果-failedCount:=============================" + failedCount);
        LOG.info("返回结果-successCount:=============================" + successCount);
        LOG.info("返回结果-failedCount:=============================" + failedCount);
        JSONArray resultList=jsonObj.getJSONArray("processResult");
        if(resultList!=null && resultList.size()>0){
        	//对json数组进行循环
            for (int i = 0; i < resultList.size(); i++) {
            	JSONObject jobj = resultList.getJSONObject(i);
            	boolean result=jobj.getBooleanValue("result");
            	String reason=jobj.getString("reason");	
            	if(!result){
            		resultDto.setFailReason(reason);
            	}        	
                System.out.println("返回结果-result:=============================" + result);             
                System.out.println("返回结果-reason:=============================" + reason);
                LOG.info("返回结果-result:=============================" + result);
                LOG.info("返回结果-reason:=============================" + reason);
                break;
            }
        }
	}

	/**
	 * 是否开启IT服务台一键上报
	 * @author WangQianJin
	 * @return
	 */
	@Override
	public boolean isStartItServiceUpload(){
		boolean start=false;
		//获取配置参数
		ConfigurationParamsEntity config=pkpsysConfigService.queryConfigurationParamsByEntity(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, WaybillConstants.ITSERVICE_UPLOAD_START,FossConstants.ROOT_ORG_CODE);
		if(config!=null){			
			if(FossConstants.YES.equals(config.getConfValue())){
				start=true;
			}
		}
		return start;
	}
}
