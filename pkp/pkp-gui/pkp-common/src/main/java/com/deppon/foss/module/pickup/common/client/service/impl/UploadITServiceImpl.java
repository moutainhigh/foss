package com.deppon.foss.module.pickup.common.client.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.widget.itservice.common.ITServiceResultDto;
import com.deppon.foss.framework.client.widget.itservice.common.ITServiceVo;
import com.deppon.foss.framework.client.widget.itservice.common.UploadPictureDto;
import com.deppon.foss.framework.client.widget.service.IUploadITService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.util.define.FossConstants;

/**
 * 一键上报实现类
 * @author WangQianJin
 *
 */
public class UploadITServiceImpl implements IUploadITService {
	
	// 获得远程HessianRemoting接口
	private IWaybillHessianRemoting waybillRemotingService = DefaultRemoteServiceFactory.getService(IWaybillHessianRemoting.class);

	/**
	 * 一键上报至IT服务台
	 * @author WangQianJin
	 * @param itList
	 * @return
	 */
	@Override
	public ITServiceResultDto uploadItServiceForGui(List<ITServiceVo> itList) {
		List<com.deppon.foss.module.pickup.waybill.shared.vo.ITServiceVo> list=null;
		//将一键上报传递参数进行转换封装
		if(itList!=null && itList.size()>0){
			list=new ArrayList<com.deppon.foss.module.pickup.waybill.shared.vo.ITServiceVo>();
			for(int i=0;i<itList.size();i++){
				ITServiceVo vo1=itList.get(i);
				if(vo1!=null){
					//将上传图片列表进行转换
					List<com.deppon.foss.module.pickup.waybill.shared.dto.UploadPictureDto> picList=null;					
					if(vo1.getUploadVoucherList()!=null && vo1.getUploadVoucherList().size()>0){
						picList=new ArrayList<com.deppon.foss.module.pickup.waybill.shared.dto.UploadPictureDto>();
						for(int j=0;j<vo1.getUploadVoucherList().size();j++){
							UploadPictureDto pic1=vo1.getUploadVoucherList().get(j);
							com.deppon.foss.module.pickup.waybill.shared.dto.UploadPictureDto pic2=new com.deppon.foss.module.pickup.waybill.shared.dto.UploadPictureDto();
							try{
								if(pic1!=null){
									org.apache.commons.beanutils.PropertyUtils.copyProperties(pic2, pic1);	
								}								
							}catch(Exception e){
								System.out.print("uploadItServiceForGui> UploadPictureDto >copyProperties  is  error!");
							}
							picList.add(pic2);
						}						
					}
					com.deppon.foss.module.pickup.waybill.shared.vo.ITServiceVo vo2=new com.deppon.foss.module.pickup.waybill.shared.vo.ITServiceVo();
					try{
						org.apache.commons.beanutils.PropertyUtils.copyProperties(vo2, vo1);
					}catch(Exception e){
						System.out.print("uploadItServiceForGui> ITServiceVo >copyProperties  is  error!");
					}
					//重新设置上传列表
					vo2.setUploadVoucherList(picList);
					list.add(vo2);
				}				
			}
		}
		//将返回的结果进行转换
		com.deppon.foss.module.pickup.waybill.shared.dto.ITServiceResultDto result1=waybillRemotingService.uploadItService(list);
		ITServiceResultDto result2=new ITServiceResultDto();
		if(result1!=null){
			try{
				org.apache.commons.beanutils.PropertyUtils.copyProperties(result2, result1);
			}catch(Exception e){
				System.out.print("uploadItServiceForGui> ITServiceResultDto >copyProperties  is  error!");
			}
		}		
		return result2;
	}

	/**
	 * 是否启用一键上报
	 * @author WangQianJin
	 * @return
	 */
	@Override
	public boolean isStartItServiceUpload() {
		boolean start=false;
		//获取配置参数
		ConfigurationParamsEntity config=waybillRemotingService.queryConfigurationParamsByEntity(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, WaybillConstants.ITSERVICE_UPLOAD_START,FossConstants.ROOT_ORG_CODE);
		if(config!=null){			
			if(FossConstants.YES.equals(config.getConfValue())){
				start=true;
			}
		}
		return start;
	}
	
}
