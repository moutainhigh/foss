package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGradientDiscountService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncGradientDiscountService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GradientDiscItemEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GradientDiscountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGradientDiscountInfoEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.util.define.ESBHeaderConstant;


/**
 * 零担梯度折扣需求 服务端 restful
 * @author 218392
 * @date 2015-06-03 15:14:20
 *
 */
public class SyncGradientDiscountService implements ISyncGradientDiscountService{
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SyncGradientDiscountService.class);
	
	/**
	 * 注入梯度折扣接口Service   IGradientDiscountService 
	 */
	private IGradientDiscountService gradientDiscountService;
	
	public void setGradientDiscountService(
			IGradientDiscountService gradientDiscountService) {
		this.gradientDiscountService = gradientDiscountService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	/**
     * 业务互斥锁
     */
    private IBusinessLockService businessLockService;
    
    @Context
    protected HttpServletRequest req;
	@Context
	protected HttpServletResponse resp;
	
	@Override
	public ReturnGradientDiscountInfoEntity addGradientDiscount(String gradientDiscount) {
		resp.setHeader(ESBHeaderConstant.VERSION, req.getHeader(ESBHeaderConstant.VERSION));
		resp.setHeader(ESBHeaderConstant.ESBSERVICECODE, "FOSS_ESB2FOSS_LCL_GRADIENT_DISCOUNT");
		resp.setHeader(ESBHeaderConstant.REQUESTID, req.getHeader(ESBHeaderConstant.REQUESTID));
		resp.setHeader(ESBHeaderConstant.BUSINESSID, req.getHeader(ESBHeaderConstant.BUSINESSID));
		resp.setHeader(ESBHeaderConstant.MESSAGEFORMAT, req.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
		resp.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, req.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
		resp.setHeader(ESBHeaderConstant.BACKSERVICECODE, req.getHeader(ESBHeaderConstant.BACKSERVICECODE));
		resp.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
		resp.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
	
		ReturnGradientDiscountInfoEntity response = new ReturnGradientDiscountInfoEntity();
		//将CRM请求过来的String gradientDiscount 转换成JSONObject对象,后面括号里gradientDiscountEntity是CRM传过来的外面实体的key
		JSONObject object = JSONObject.fromObject(gradientDiscount).getJSONObject("gradientDiscountEntity");
		/**
		 * @author 218392 张永雪  
		 * @date 2015-06-16 我在调试的时候出现这样一个问题：从CRM传过来的实体entity中又包含一个List<entity>,在我这里进行转换的时候就
		 * 报了net.sf.ezmorph.bean.MorphDynaBean cannot be cast to 错误；
		 * 在json字符串转java bean时，一般的对象，可以直接转;但是如果属性中含有复杂的类型，类似List , Map ,ArrayList、自定义的类型
		 * 就会出新net.sf.ezmorph.bean.MorphDynaBean cannot be cast to 错误
		 * 解决办法如下:定义一个Map<String, Class> classMap = new HashMap<String, Class>();
		 * 然后：	classMap.put("dealItems", GradientDiscItemEntity.class);最后再转就OK了
		 */
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("dealItems", GradientDiscItemEntity.class);
		//将JSONObject转换成对应的实体类--梯度折基础资料实体GradientDiscountEntity
		GradientDiscountEntity gradientDiscountEntity = (GradientDiscountEntity) JSONObject.toBean(object,GradientDiscountEntity.class,classMap);
//		//获取梯度折基础资料实体中梯度折基础资料详情实体GradientDiscItemEntity(CRM那边是把详情放在梯度折基础资料实体里面然后传过来的)
//		List<GradientDiscItemEntity> gradientDiscItemList = gradientDiscountEntity.getDealItems();
		try {
			if(gradientDiscountEntity != null){
				MutexElement mutex = new MutexElement(gradientDiscountEntity.getFid(), "CRM_GRADIENT_DISCOUNT_ID", MutexElementType.CRM_GRADIENT_DISCOUNT_ID);
				LOGGER.info("开始加锁：" + mutex.getBusinessNo());
				boolean result = businessLockService.lock(mutex, NumberConstants.ZERO);
				if(result){
					LOGGER.info("成功加锁：" + mutex.getBusinessNo());
					resp.setHeader("ESB-ResultCode", "1");
					response.setIfSuccess(true);
					
					//开始往FOSS表中同步数据
					gradientDiscountService.updateGradientDiscountInfo(gradientDiscountEntity);
					
					LOGGER.info("开始解锁：" + mutex.getBusinessNo());
					// 解业务锁
					businessLockService.unlock(mutex);
					LOGGER.info("完成解锁：" + mutex.getBusinessNo());
				}else{
					LOGGER.info("失败加锁：" + mutex.getBusinessNo());
					 response.setIfSuccess(false);
					 resp.setHeader("ESB-ResultCode", "1");
					 response.setErrorMsg("加锁失败");
				}
			}else{
				response.setIfSuccess(false);
				resp.setHeader("ESB-ResultCode", "1");
				response.setErrorMsg("实体类不能为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setIfSuccess(false);
			resp.setHeader("ESB-ResultCode", "1");
			response.setErrorMsg("数据异常...");
		}
		return response;
	}
	

}
