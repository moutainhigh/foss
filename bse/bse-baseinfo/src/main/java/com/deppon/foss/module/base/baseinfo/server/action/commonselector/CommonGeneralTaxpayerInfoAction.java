package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonGeneralTaxpayerInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.TaxpayerInfoVo;

/**
 * 
 * 公共选择器   一般纳税人信息 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:308861,date:2016-2-28 下午4:58:59,content:TODO </p>
 * @author 308861 
 * @date 2016-2-28 下午4:58:59
 * @since
 * @version
 */
public class CommonGeneralTaxpayerInfoAction extends AbstractAction implements IQueryAction{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 124521838425190907L;
	
	/**
	 * 注入ICommonGeneralTaxpayerInfoService
	 */
	private ICommonGeneralTaxpayerInfoService generalTaxpayerInfoService;
	
	private TaxpayerInfoVo taxpayerVo=new TaxpayerInfoVo();
	
	/**
	 * 
	 * 查询一般纳税人信息 
	 * @author 308861 
	 * @date 2016-2-28 下午5:03:10
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		//查询一般纳税人信息
		taxpayerVo.setTaxpayerEntitys(generalTaxpayerInfoService
				.queryTaxpayerInfoList(
						taxpayerVo.getTaxpayerEntity(), start, limit));
		//统计一般纳税人信息个数
		this.setTotalCount(generalTaxpayerInfoService
				.queryGeneralTaxpayerInfoCount(taxpayerVo.getTaxpayerEntity()));
		return returnSuccess();
	}
	
	/**
	 * 
	 * setter 
	 * @author 308861 
	 * @date 2016-2-29 上午10:32:28
	 * @param generalTaxpayerInfoService
	 * @see
	 */
	public void setGeneralTaxpayerInfoService(
			ICommonGeneralTaxpayerInfoService generalTaxpayerInfoService) {
		this.generalTaxpayerInfoService = generalTaxpayerInfoService;
	}

	
	public TaxpayerInfoVo getTaxpayerVo() {
		return taxpayerVo;
	}

	public void setTaxpayerVo(TaxpayerInfoVo taxpayerVo) {
		this.taxpayerVo = taxpayerVo;
	}
}
