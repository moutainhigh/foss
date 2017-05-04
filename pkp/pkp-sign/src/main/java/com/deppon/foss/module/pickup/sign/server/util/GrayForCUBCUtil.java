
package com.deppon.foss.module.pickup.sign.server.util;

import java.util.List;

import com.deppon.foss.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayRequestEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayResponseEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.VestBatchResult;
import com.deppon.foss.module.settlement.common.api.shared.exception.CUBCGrayException;
import com.deppon.foss.module.settlement.common.server.util.HttpClientUtils;
/**
 * CUBC灰度分流
 * @author 243921 zhangtingting
 */
public class GrayForCUBCUtil {
	
	/**
	 * 记录日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(GrayForCUBCUtil.class);
	
	/**
	 * @param waybillNos 运单号集合
	 * @param grayUrl 灰度地址
	 * @return 返回FOSS/CUBC
	 */
	public static String getGray(List<String> waybillNos,String grayUrl){
		String vestSystemCode = null;
        try {
        	StackTraceElement elements = new Exception().getStackTrace()[1];
			String methodName = elements.getClassName()+"."+elements.getMethodName();
			LOGGER.info("获取调用灰度分流的方法名："+methodName);
        	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(waybillNos,
        			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,methodName,
        			SettlementConstants.TYPE_FOSS);
        	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayUrl);
        	List<VestBatchResult> list = response.getVestBatchResult();
			if(CollectionUtils.isEmpty(list)){
				LOGGER.info("灰度分流失败,"+"运单号："+waybillNos);
				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			}
        	vestSystemCode = list.get(0).getVestSystemCode();		
		} catch (Exception e) {
			LOGGER.error("调灰度分流方法异常：" , e);
			throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
		}
        return vestSystemCode;
	}
}