/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFocusRecordManagementService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesBillingGroupEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MapDto;
import com.deppon.foss.module.pickup.common.client.dao.ISalesBillingGroupDao;
import com.deppon.foss.module.pickup.common.client.service.ISalesBillingGroupService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.google.inject.Inject;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class SalesBillingGroupService  implements ISalesBillingGroupService {
	@Inject
	ISalesBillingGroupDao  salesBillingGroupDao;
	
	/**
	 * 获取集中开单组
	 */
	private IFocusRecordManagementService focusRecordManagementService;
	
	public void setFocusRecordManagementService(
			IFocusRecordManagementService focusRecordManagementService) {
		this.focusRecordManagementService = focusRecordManagementService;
	}
	
	// 获得远程HessianRemoting接口
	private IWaybillHessianRemoting waybillRemotingService;
	
	//zxy 20131029 KDTE-4268 start 新增：初始化服务
	public SalesBillingGroupService(){
		//获得远程HessianRemoting接口
		waybillRemotingService = DefaultRemoteServiceFactory.getService(IWaybillHessianRemoting.class);
	}
	//zxy 20131029 KDTE-4268 end 新增：初始化服务
	
	/**
     * 插条记录
     */
	@Override
	public boolean addSalesBillingGroup(SalesBillingGroupEntity salesBillingGroup) {
		return salesBillingGroupDao.addSalesBillingGroup(salesBillingGroup);
		
	}
	/**
	 * 更新条记录
	 */
	@Override
	public void updateSalesBillingGroup(
			SalesBillingGroupEntity salesBillingGroup) {
		salesBillingGroupDao.updateSalesBillingGroup(salesBillingGroup);
	}
	/**
	 * 新增或更新记录
	 */
	@Override
	public void saveOrUpdate(SalesBillingGroupEntity salesBillingGroup) {
		
		if(!salesBillingGroupDao.addSalesBillingGroup(salesBillingGroup)){
			salesBillingGroupDao.updateSalesBillingGroup(salesBillingGroup);
		}
		
	}

	/**
	 * @param delete
	 */
	public void delete(SalesBillingGroupEntity salesBillingGroup) {
		salesBillingGroupDao.delete(salesBillingGroup);
	}
	
	/**
	 * 根据集中开单部门编码查询旗下所有营业部
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-6 下午6:33:35
	 */
	@Override
	public List<SalesBillingGroupEntity> querySalesListByBillingGroupCode(String code){

		List<String> lists = new ArrayList<String>();
		lists.add(code);
		return salesBillingGroupDao.querySalesListByBillingGroupCode(lists);
	}
	
	/**
	 * 根据营业部编码查询所属集中开单组
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-6 下午6:33:35
	 */
	@Override
	public List<SalesBillingGroupEntity> queryBillingGroupListBySalesCode(String code){
		//zxy 20131029 KDTE-4268 start 修改：之前只有离线查询，现增加在线查询功能
		// 判断在线还是离线
		if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString())) {
			List<MapDto> billGroupLst = waybillRemotingService.queryBillingGroupListBySalesCode(code);
			if(billGroupLst != null && billGroupLst.size() > 0){
				List<SalesBillingGroupEntity> salesBillingGroupEntityLst = new ArrayList<SalesBillingGroupEntity>();
				for(MapDto dto:billGroupLst){
					SalesBillingGroupEntity salesBillingGroupEntity = new SalesBillingGroupEntity();
					salesBillingGroupEntity.setBillingGroupCode(dto.getCode());
					salesBillingGroupEntity.setBillingGroupName(dto.getName());
					salesBillingGroupEntityLst.add(salesBillingGroupEntity);
				}
				return salesBillingGroupEntityLst;
			}else
				return null;
		}else
			return salesBillingGroupDao.queryBillingGroupListBySalesCode(code);
		//zxy 20131029 KDTE-4268 end 修改：之前只有离线查询，现增加在线查询功能
	}

}