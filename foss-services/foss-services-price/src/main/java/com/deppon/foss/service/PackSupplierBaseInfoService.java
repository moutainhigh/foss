package com.deppon.foss.service;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deppon.foss.module.base.baseinfo.api.server.service.IPackagingSupplierService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity;
import com.deppon.foss.shared.request.FOSSFactorFlagRequest;
import com.deppon.foss.shared.response.FOSSFactorFlagResponse;
import com.deppon.foss.util.define.ESBHeaderConstant;
import com.deppon.foss.util.define.FossConstants;

/**
 * 贷款系统同步保理标记信息
 * @author 269231
 * 
 */
@RequestMapping("/packSupplierBaseInfo")
public class PackSupplierBaseInfoService {
	
	Log logger = LogFactory.getLog(PackSupplierBaseInfoService.class);

	//包装供应商服务
	@Resource
	private IPackagingSupplierService packagingSupplierService ;
	
	//贷款系统同步包装服务商保理标记信息 ------ 增删改
		@RequestMapping("/editPackSupplierBaseInfo")
		public @ResponseBody FOSSFactorFlagResponse editPackSupplierBaseInfo(@RequestBody FOSSFactorFlagRequest payload,HttpServletResponse response) {
			
			logger.info("进入请求 editPackSupplierBaseInfo ");
			
			FOSSFactorFlagResponse packSupplierBaseInfoResponse = new FOSSFactorFlagResponse();
			try {				
				if (payload != null) {
					//根据4个字段查询包装商数据是否存在
					PackagingSupplierEntity entity = packagingSupplierService.queryPackagingSupplierByCodes(payload.getOutfieldCode(), payload.getOutfieldName(), 
							payload.getSupplierCode(), payload.getSupplierName());					
					//如果存在,更新数据
					if(entity != null){
						//新加四个字段
						entity.setAccount(payload.getAccount());
						entity.setCusCode(payload.getCusCode());
						entity.setFactorBeginTime(new SimpleDateFormat("yyyy-MM-dd").parse(payload.getFactorBeginTime()));
						entity.setFactorEndTime(new SimpleDateFormat("yyyy-MM-dd").parse(payload.getFactorEndTime()));
						
						//包装供应商
						entity.setPackagingSupplier(payload.getSupplierName());
						entity.setPackagingSupplierCode(payload.getSupplierCode());
						//包装部门
						entity.setOrgCode(payload.getOutfieldName());
						entity.setOrgCodeCode(payload.getOutfieldCode());
						
						//是否保理
						entity.setFactoring("Y");					
						packagingSupplierService.updatePackagingSupplier(entity);
						
						response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
						return packSupplierBaseInfoResponse;
					}
					//如果不存在，返回异常
					else{
						logger.warn("找不到对应的包装商");
						response.setHeader(ESBHeaderConstant.RESULTCODE, "0");
						packSupplierBaseInfoResponse.setIsSuccess(FossConstants.NO);
						packSupplierBaseInfoResponse.setErrorMsg("找不到对应的包装商");
						return packSupplierBaseInfoResponse;						
						}			
					}
				else{
					logger.warn("传入的参数为空");
					response.setHeader(ESBHeaderConstant.RESULTCODE, "0");
					packSupplierBaseInfoResponse.setIsSuccess(FossConstants.NO);
					packSupplierBaseInfoResponse.setErrorMsg("查询参数不能为空");
					return packSupplierBaseInfoResponse;
					}
				}
			
			catch (Exception e) {
				logger.error(e.getMessage(), e);
				packSupplierBaseInfoResponse.setIsSuccess(FossConstants.NO);
				packSupplierBaseInfoResponse.setErrorMsg(e.getMessage());
				response.setHeader(ESBHeaderConstant.RESULTCODE, "0");
				return packSupplierBaseInfoResponse;
			}
		}
}
