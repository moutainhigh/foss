package com.deppon.foss.module.transfer.common.server.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestBatchResult;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.GrayParameterDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.service.impl.VestClientServiceImpl;

public class CUBCGrayUtil {
	public static final String SYSTEM_FOSS = "FOSS";

	public enum SBType{
		ZC("ZC", "临时租车编号"), W("W", "运单号"), PZ("PZ", "配载单号"), SJ("SJ", "司机编码"), WQC("WQC", "外请车司机"), HK("HK", "航空公司编码");
		private SBType(String name, String desc){
			this.setName(name);
			this.setDesc(desc);
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		private String name;
		private String desc;
		
	}
	
	/**
	 * 日志初始化
	 */
	private static final Log LOG = LogFactory.getLog(CUBCGrayUtil.class);
	private VestClientServiceImpl vestClientService = new VestClientServiceImpl();
	
	/**
	 * 灰度系统CODE返回
	 * 调用灰度返回具体调用FOSS STL 或者 UCBC
	 * @param resource
	 * @param key
	 * @return String
	 */
	public VestResponse getUcbcGrayData(GrayParameterDto grayParameterDto, Throwable className){
		
		LOG.error("进入灰度工具类, 参数：" + getClassMethodName(className) 
		+ "  " + grayParameterDto.getSourceBillType()
		+ " " + concatSB(grayParameterDto.getSourceBillNos()));
		LOG.error("调用灰度方法开始...");
		
		VestResponse vestResponse = null;
		try {
			vestResponse = vestClientService.vestAscription(getClassMethodName(className), "FOSS-ZZ", grayParameterDto.getSourceBillType(), grayParameterDto.getSourceBillNos());
		} catch(Throwable throwable) {
			LOG.error("调用cubc灰度  vestAscription 方法出错",throwable);
//			LOG.error("发生了异常需要调用FOSS STL 模块...");
//			vestResponse = new VestResponse();
//			List<VestBatchResult> resList = new ArrayList<VestBatchResult>();
//			VestBatchResult result = new VestBatchResult();
//			result.setVestSystemCode(SYSTEM_FOSS);
//			result.setVestObject(Arrays.asList(grayParameterDto.getSourceBillNos()));
//			resList.add(result);
//			vestResponse.setVestBatchResult(resList);
			throw new TfrBusinessException("灰度异常", throwable);
		}
		LOG.error("调用灰度方法结束...");
		if (vestResponse != null && vestResponse.getVestBatchResult().size() > 0) {
			if (vestResponse.getVestBatchResult().size() >= 2) {
				LOG.error("灰度返回值 SYS CODE [0]..." + vestResponse.getVestBatchResult().get(0).getVestSystemCode());
				LOG.error("灰度返回值 SYS CODE [1]..." + vestResponse.getVestBatchResult().get(1).getVestSystemCode());
			} else {
				LOG.error("灰度返回值 SYS CODE [0]..." + vestResponse.getVestBatchResult().get(0).getVestSystemCode());;
			}
			return vestResponse;
		} else {
			vestResponse = new VestResponse();
			LOG.error("访问灰度返回为空，需要调用FOSS STL 模块...");
			List<VestBatchResult> resList = new ArrayList<VestBatchResult>();
			VestBatchResult result = new VestBatchResult();
			result.setVestSystemCode(SYSTEM_FOSS);
			result.setVestObject(Arrays.asList(grayParameterDto.getSourceBillNos()));
			resList.add(result);
			vestResponse.setVestBatchResult(resList);
			
			return vestResponse;
		}
	}
	
	
	private String concatSB(String[] sourceBillNos) {
		StringBuilder sb = new StringBuilder("-");
		for (String string : sourceBillNos) {
			sb.append(string).append("-");
		}
		return sb.toString();
	}


	/**
	 * 灰度系统CODE返回
	 * 调用灰度返回具体调用FOSS STL 或者 UCBC
	 * @param resource
	 * @param key
	 * @return String
	 */
	public VestResponse getUcbcGrayDataBySourceBillNo(GrayParameterDto grayParameterDto, Throwable className){
		
		LOG.error("进入单据分流工具类, 参数：" + getClassMethodName(className) 
		+ "  " + grayParameterDto.getSourceBillType()
		+ " " + grayParameterDto.getSourceBillNos());
		LOG.error("调用单据分流方法开始...");
		
		VestResponse vestResponse = null;
		try {
			vestResponse = vestClientService.vestBySourceBillNo(getClassMethodName(className), "FOSS-ZZ", grayParameterDto.getSourceBillType(), grayParameterDto.getSourceBillNos());
		} catch(Throwable throwable) {
			LOG.error("调用cubc灰度  vestBySourceBillNo 方法出错",throwable);
//			LOG.error("发生了异常需要调用FOSS STL 模块...");
//			vestResponse = new VestResponse();
//			List<VestBatchResult> resList = new ArrayList<VestBatchResult>();
//			VestBatchResult result = new VestBatchResult();
//			result.setVestSystemCode(SYSTEM_FOSS);
//			result.setVestObject(Arrays.asList(grayParameterDto.getSourceBillNos()));
//			resList.add(result);
//			vestResponse.setVestBatchResult(resList);
//			return vestResponse;
			throw new TfrBusinessException("灰度异常", throwable);
		}
		LOG.error("调用单据分流方法结束...");
		if (vestResponse != null && vestResponse.getVestBatchResult().size() > 0) {
			if (vestResponse.getVestBatchResult().size() >= 2) {
				LOG.error("getUcbcGrayDataBySourceBillNo灰度返回值 SYS CODE [0]..." + vestResponse.getVestBatchResult().get(0).getVestSystemCode());
				LOG.error("getUcbcGrayDataBySourceBillNo灰度返回值 SYS CODE [1]..." + vestResponse.getVestBatchResult().get(1).getVestSystemCode());
			} else {
				LOG.error("getUcbcGrayDataBySourceBillNo灰度返回值 SYS CODE [0]..." + vestResponse.getVestBatchResult().get(0).getVestSystemCode());;
			}
			return vestResponse;
		} else {
			vestResponse = new VestResponse();
			LOG.error("访问单据分流返回为空，需要调用FOSS STL 模块...");
			List<VestBatchResult> resList = new ArrayList<VestBatchResult>();
			VestBatchResult result = new VestBatchResult();
			result.setVestSystemCode(SYSTEM_FOSS);
			result.setVestObject(Arrays.asList(grayParameterDto.getSourceBillNos()));
			resList.add(result);
			vestResponse.setVestBatchResult(resList);
			
			return vestResponse;
		}
	}
	
	/**
	 * VestClientService.vestByCustomer根据客户分流
	 * @param grayParameterDto
	 * @param className new一个
	 * @return 
	 */
	public VestResponse getCubcGrayDataByCustomer(GrayParameterDto grayParameterDto, Throwable className){
		
		LOG.error("进入客户分流工具类, 参数：" + getClassMethodName(className) 
		+ "  " + grayParameterDto.getSourceBillType()
		+ " " + grayParameterDto.getSourceBillNos());
		LOG.error("调用客户分流方法开始...");
		
		VestResponse vestResponse = null;
		try {
			vestResponse = vestClientService.vestByCustomer(getClassMethodName(className), grayParameterDto.getSourceBillType(), "FOSS-ZZ", grayParameterDto.getSourceBillNos());
		} catch(Throwable throwable) {
			LOG.error("调用cubc灰度  vestByCustomer 方法出错",throwable);
//			LOG.error("发生了异常需要调用FOSS STL 模块...");
//			vestResponse = new VestResponse();
//			List<VestBatchResult> resList = new ArrayList<VestBatchResult>();
//			VestBatchResult result = new VestBatchResult();
//			result.setVestSystemCode(SYSTEM_FOSS);
//			result.setVestObject(Arrays.asList(grayParameterDto.getSourceBillNos()));
//			resList.add(result);
//			vestResponse.setVestBatchResult(resList);
//			return vestResponse;
			throw new TfrBusinessException("灰度异常", throwable);
		}
		LOG.error("调用客户分流方法结束...");
		if (vestResponse != null && vestResponse.getVestBatchResult().size() > 0) {
			if (vestResponse.getVestBatchResult().size() >= 2) {
				LOG.error("getCubcGrayDataByCustomer灰度返回值 SYS CODE [0]..." + vestResponse.getVestBatchResult().get(0).getVestSystemCode());
				LOG.error("getCubcGrayDataByCustomer灰度返回值 SYS CODE [1]..." + vestResponse.getVestBatchResult().get(1).getVestSystemCode());
			} else {
				LOG.error("getCubcGrayDataByCustomer灰度返回值 SYS CODE [0]..." + vestResponse.getVestBatchResult().get(0).getVestSystemCode());;
			}
			return vestResponse;
		} else {
			LOG.error("访问客户分流返回为空，需要调用FOSS STL 模块...");
			vestResponse = new VestResponse();
			List<VestBatchResult> resList = new ArrayList<VestBatchResult>();
			VestBatchResult result = new VestBatchResult();
			result.setVestSystemCode(SYSTEM_FOSS);
			result.setVestObject(Arrays.asList(grayParameterDto.getSourceBillNos()));
			resList.add(result);
			vestResponse.setVestBatchResult(resList);
			
			return vestResponse;
		}
	}
	
	private String getClassMethodName(Throwable throwable) {
		return throwable.getStackTrace()[0].getClassName() + "." + throwable.getStackTrace()[0].getMethodName();
	}
}
