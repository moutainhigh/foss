package com.deppon.pda.bdm.module.foss.accept.server.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaPriceService;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PublicPriceDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.ProductInfoEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.QryProductInfoEntity;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.server.service.impl 
 * @file ProductInfoService.java 
 * @description 查询产品信息
 * @author ChenLiang
 * @created 2012-12-26 下午2:10:12    
 * @version 1.0
 */
public class ProductInfoService implements IBusinessService<QryProductInfoEntity, ProductInfoEntity> {
	
	private Logger log = Logger.getLogger(getClass());
	
	private IPdaPriceService pdaPriceService;

	public void setPdaPriceService(IPdaPriceService pdaPriceService) {
		this.pdaPriceService = pdaPriceService;
	}

	/**
	 * 
	 * @description 解析包体
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public ProductInfoEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		//解析包体
		ProductInfoEntity productInfo = JsonUtil.parseJsonToObject(ProductInfoEntity.class, asyncMsg.getContent());
		return productInfo;
	}
	
	/**
	 * 验证数据
	 * @param asyncMsg
	 * @param productInfo
	 */
	private void validate(AsyncMsg asyncMsg, ProductInfoEntity productInfo){
		Argument.notNull(asyncMsg, "AsyncMsg");
		//验证pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//验证用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//验证部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		Argument.notNull(productInfo, "ProductInfoEntity");
		//验证出发城市
		Argument.hasText(productInfo.getDepartDeptCity(), "ProductInfoEntity.departDeptCity");
		//验证到达城市
		Argument.hasText(productInfo.getAssemblyCity(), "ProductInfoEntity.assemblyCity");
	}

	/**
	 * 
	 * @description 查询产品信息服务
	 * @param asyncMsg
	 * @param productInfo
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Override
	public QryProductInfoEntity service(AsyncMsg asyncMsg, ProductInfoEntity productInfo) throws PdaBusiException {
		this.validate(asyncMsg, productInfo);
		QryProductInfoEntity qryProductInfo = new QryProductInfoEntity();
		log.debug("---调用FOSS查询产品信息接口开始---");
		List<PublicPriceDto> lists = null;
		try {
			lists = pdaPriceService.queryPublishPriceDetailByCity(productInfo.getDepartDeptCity(), productInfo.getAssemblyCity(), productInfo.getDate());
			log.debug("---调用FOSS查询产品信息接口返回大小:"+lists.size()+"---");
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		log.debug("---调用FOSS查询产品信息接口结束---");
		return qryProductInfo;
	}

	
	/**
	 * 服务类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_PRODUCT_INFO.VERSION;
	}

	/**
	 * 是否同步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

}
