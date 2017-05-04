package com.deppon.foss.module.pickup.common.client.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.guice.transactional.Transactional;

import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.module.pickup.common.client.dao.IDataConsistencyCheckDao;
import com.deppon.foss.module.pickup.common.client.define.DataConsistencyConstants;
import com.deppon.foss.module.pickup.common.client.service.IDataConsistencyCheckService;
import com.deppon.foss.module.pickup.common.client.vo.DataConsistencyCheckVo;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IDataConsistencyCheckHessianRemoting;
import com.deppon.foss.module.pickup.waybill.shared.vo.DataConsistencyVo;
import com.google.inject.Inject;



public class DataConsistencyCheckService implements
		IDataConsistencyCheckService {

	//log
//	private static final Log LOG = LogFactory.getLog(DataConsistencyCheckService.class);

	private static final int THREE = 3;

	private static final int FOUR = 4;
	
	@Inject
	private IDataConsistencyCheckDao dataConsistencyCheckDao;
	
	private IDataConsistencyCheckHessianRemoting dataConsistencyCheckHessianRemoting;
	
	
	public DataConsistencyCheckService(){
		dataConsistencyCheckHessianRemoting=DefaultRemoteServiceFactory.getService(IDataConsistencyCheckHessianRemoting.class);
	}
	
	/**
	 * 本地数据下载表名查询
	 * @author foss-dengyao
	 * @date 2013-04-20 上午9:32:15
	 * @param querylocalTableDate
	 * @see
	 */
	@Transactional
	@Override
	public List<DataConsistencyCheckVo> querylocalTableDate(){
		List<DataConsistencyCheckVo> dataConsistencyCheck = dataConsistencyCheckDao.queryDownlodeTableDate();
		List<DataConsistencyCheckVo> deldata=new ArrayList<DataConsistencyCheckVo>();
		//List<String> tableName = new  ArrayList<String>();
		for (DataConsistencyCheckVo data:dataConsistencyCheck){
			if(data.getRegionID()!=null && !data.getRegionID().equals("")){
				data.setRegionID("'"+data.getRegionID()+"'");
			}
			if(queryLocalPriceTableDate(data)
				|| queryLocalBasServiceTableDate(data)
				|| queryLocalBasInfoTableDate(data)
				|| queryLocalPKPServiceTableDate(data)
				|| data.getEntityClsName().equals("com.deppon.foss.module.pickup.pricing.api.shared.domain.DiscountProgramEntity")
				|| data.getEntityClsName().equals("com.deppon.foss.module.pickup.pricing.api.shared.domain.MakEventInOrgEntity")
				|| data.getEntityClsName().equals("com.deppon.foss.module.pickup.pricing.api.shared.domain.MakEventExOrgEntity")){
				continue ;
			} 
		}
		for (DataConsistencyCheckVo datas:dataConsistencyCheck){
			if(datas.getTabelName()==null){
				deldata.add(datas);
			}
		}
		dataConsistencyCheck.removeAll(deldata);
		
		List<DataConsistencyCheckVo> tableCounts=countLocalTableCounts(dataConsistencyCheck);
		
		return tableCounts;
	}
	
	private boolean queryLocalPKPServiceTableDate(DataConsistencyCheckVo data) {
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_SRV_DISCOUNT_ORG)){
			data.setTabelName("PKP.T_SRV_DISCOUNT_ORG");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_SRV_EFFECTIVE_PLAN)){
			data.setTabelName("PKP.T_SRV_EFFECTIVE_PLAN");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_SRV_EFFECTIVE_PLAN_DETAIL)){
			data.setTabelName("PKP.T_SRV_EFFECTIVE_PLAN_DETAIL");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_SRV_EFFECTIVE_REGION)){
			data.setTabelName("PKP.T_SRV_EFFECTIVE_REGION");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_SRV_EFFECTIVE_REGION_ORG)){
			data.setTabelName("PKP.T_SRV_EFFECTIVE_REGION_ORG");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_SRV_GOODSTYPE)){
			data.setTabelName("PKP.T_SRV_GOODSTYPE");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_SRV_MARKETING_EVENT)){
			data.setTabelName("PKP.T_SRV_MARKETING_EVENT");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_SRV_MARKETING_EVENT_CHANEL)){
			data.setTabelName("PKP.T_SRV_MARKETING_EVENT_CHANEL");
			return true;
		}
		
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_SRV_PRODUCT)){
			data.setTabelName("PKP.T_SRV_PRODUCT");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_SRV_PRODUCT_ITEM)){
			data.setTabelName("PKP.T_SRV_PRODUCT_ITEM");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_BAS_SALESDEPT_BILLINGGROUP)){
			data.setTabelName("BSE.T_BAS_SALESDEPT_BILLINGGROUP");
			return true;
		}
		return false;
	}

	private boolean queryLocalBasInfoTableDate(DataConsistencyCheckVo data) {
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_BAS_ROLE)){
			data.setTabelName("BSE.T_BAS_ROLE");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_BAS_ROLE_RESOURCES)){
			data.setTabelName("BSE.T_BAS_ROLE_RESOURCES");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_BAS_SALES_DEPARTMENT)){
			data.setTabelName("BSE.T_BAS_SALES_DEPARTMENT");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_BAS_SYS_CONFIG)){
			data.setTabelName("BSE.T_BAS_SYS_CONFIG");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_BAS_TRANSFER_CENTER)){
			data.setTabelName("BSE.T_BAS_TRANSFER_CENTER");
			return true;
		}
		//if (data.getEntityClsName().equals(DataConsistencyConstants.T_BAS_SALESDEPT_ASTERISK)){
		//	data.setTabelName("BSE.T_BAS_SALESDEPT_ASTERISK");return true;
		//}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_BAS_USER)){
			data.setTabelName("BSE.T_BAS_USER");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_BAS_USER_ORG_ROLE)){
			data.setTabelName("BSE.T_BAS_USER_ORG_ROLE");
			return true;
		}
		return false;
	}

	private boolean queryLocalBasServiceTableDate(DataConsistencyCheckVo data) {
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_BAS_BUSINESS_PARTNER)){
			data.setTabelName("BSE.T_BAS_BUSINESS_PARTNER");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_BAS_DATA_DICTIONARY_VALUE)){
			data.setTabelName("BSE.T_BAS_DATA_DICTIONARY_VALUE");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_BAS_DEPARTURE_STD)){
			data.setTabelName("BSE.T_BAS_DEPARTURE_STD");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_BAS_DISTRICT)){
			data.setTabelName("BSE.T_BAS_DISTRICT");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_BAS_FREIGHT_ROUTE)){
			data.setTabelName("BSE.T_BAS_FREIGHT_ROUTE");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_BAS_FREIGHT_ROUTE_LINE)){
			data.setTabelName("BSE.T_BAS_FREIGHT_ROUTE_LINE");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_BAS_GOODS_AREA)){
			data.setTabelName("BSE.T_BAS_GOODS_AREA");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_BAS_INSUR_GOODS)){
			data.setTabelName("BSE.T_BAS_INSUR_GOODS");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_BAS_LINE)){
			data.setTabelName("BSE.T_BAS_LINE");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_BAS_LINE_ITEM)){
			data.setTabelName("BSE.T_BAS_LINE_ITEM");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_BAS_NET_GROUP)){
			data.setTabelName("BSE.T_BAS_NET_GROUP");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_BAS_ORG)){
			data.setTabelName("BSE.T_BAS_ORG");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_BAS_OUTER_BRANCH)){
			data.setTabelName("BSE.T_BAS_OUTER_BRANCH");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_BAS_PRO_SALESDEPT)){
			data.setTabelName("BSE.T_BAS_PRO_SALESDEPT");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_BAS_PROHIBIT_GOODS)){
			data.setTabelName("BSE.T_BAS_PROHIBIT_GOODS");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_BAS_RESOURCES)){
			data.setTabelName("BSE.T_BAS_RESOURCES");
			return true;
		}
		return false;
	}

	private boolean queryLocalPriceTableDate(DataConsistencyCheckVo data){
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_SRV_PRICING_VALUATION[0])){
			data.setTabelName("PKP.T_SRV_PRICING_VALUATION");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_SRV_PRICING_VALUATION[1])){
			data.setTabelName("PKP.T_SRV_PRICING_VALUATION");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_SRV_PRICING_VALUATION[2])){
			data.setTabelName("PKP.T_SRV_PRICING_VALUATION");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_SRV_PRICING_VALUATION[THREE])){
			data.setTabelName("PKP.T_SRV_PRICING_VALUATION");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_SRV_PRICING_VALUATION[FOUR])){
			data.setTabelName("PKP.T_SRV_PRICING_VALUATION");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_SRV_PRICE_PLAN[0])){
			data.setTabelName("PKP.T_SRV_PRICE_PLAN");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_SRV_PRICE_PLAN[1])){
			data.setTabelName("PKP.T_SRV_PRICE_PLAN");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_SRV_PRICE_REGION)){
			data.setTabelName("PKP.T_SRV_PRICE_REGION");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_SRV_PRICE_REGION_AIR)){
			data.setTabelName("PKP.T_SRV_PRICE_REGION_AIR");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_SRV_PRICE_REGION_ORG)){
			data.setTabelName("PKP.T_SRV_PRICE_REGION_ORG");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_SRV_PRICE_REGION_ORG_AIR)){
			data.setTabelName("PKP.T_SRV_PRICE_REGION_ORG_AIR");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_SRV_PRICE_RULE)){
			data.setTabelName("PKP.T_SRV_PRICE_RULE");
			return true;
		}
		if (data.getEntityClsName().equals(DataConsistencyConstants.T_SRV_PRICING_ENTRY)){
			data.setTabelName("PKP.T_SRV_PRICING_ENTRY");
			return true;
		}
		return false;
	}
	
	
	/**
	 * 服务器对应数据表查询
	 * @author foss-dengyao
	 * @date 2013-05-02 上午9:32:15
	 * @param countServiceTableDate
	 * @see
	 */
	@Override
	public List<DataConsistencyVo> countServiceTableDate(List<DataConsistencyVo> selectedData,String userCode){
		return dataConsistencyCheckHessianRemoting.countQueryTableDate(selectedData,userCode);
	}
	
	/**
	 * 本地数据下载表条目查询
	 * @author foss-dengyao
	 * @date 2013-05-02 上午9:32:15
	 * @param countServiceTableDate
	 * @see
	 */
	@Override
	public List<DataConsistencyCheckVo> countLocalTableCounts(List<DataConsistencyCheckVo> dataConsistencyCheck){
		List<DataConsistencyCheckVo> tableCounts=dataConsistencyCheckDao.countQuerylocalTable(dataConsistencyCheck);
		tableCounts.remove(0);
		for (int i=0;i<dataConsistencyCheck.size();i++){
			dataConsistencyCheck.get(i).setLocalCounts(tableCounts.get(i).getLocalCounts());
		}		
		return dataConsistencyCheck;
	}

}
