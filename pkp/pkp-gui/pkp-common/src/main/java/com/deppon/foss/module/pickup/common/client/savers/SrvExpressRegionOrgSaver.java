/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.savers;

import java.util.Date;
import java.util.List;

import org.mybatis.guice.transactional.Transactional;

import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.component.sync.AbstractSyncDataSaver;
import com.deppon.foss.module.pickup.common.client.service.impl.PriceRegioExpressOrgService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnExpressEntity;
import com.google.inject.Injector;

/**
 * @author ibm-foss-sxw
 *
 */
public class SrvExpressRegionOrgSaver  extends AbstractSyncDataSaver {
	
	/**
	 * 
	 * 功能：映射实体类
	 * 该方法主要用于返回实体类型  用于给客户端判断
	 * 下载的是何种类型的数据实体
	 * 和对应实体的是那一张票
	 * 该实体的类名会保存在水位表的的类信息中
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	public Class<?> getSaveType() {
		return PriceRegioOrgnExpressEntity.class;
	}
	
	/**
	 * 
	 * 功能：保存数据
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public Date saveData(List<?> data) {
		Date date = null;
		List<PriceRegioOrgnExpressEntity> list = (List<PriceRegioOrgnExpressEntity>)data;
		long mxVersion = 0;
		if(list != null && !list.isEmpty()){
//			PriceRegioOrgService priceRegioOrgService = DownLoadDataServiceFactory
//					.getPriceRegioOrgService();
			Injector injector = GuiceContextFactroy.getInjector();
			PriceRegioExpressOrgService priceRegioExpressOrgService =  injector.getInstance(PriceRegioExpressOrgService.class);
			
			
			for(PriceRegioOrgnExpressEntity priceRegioOrgn : list){
				priceRegioExpressOrgService.saveOrUpdate(priceRegioOrgn);
				Long version = priceRegioOrgn.getVersionNo();
				//获得最大版本号
				if(version != null && version>mxVersion){
					mxVersion = version;
				}
			}
			
		} 
		//创建水位日期
		date = new Date();
		//设置水位时间戳
		//该时间戳标记了上次下载的位置
		//返回以后保存于水位表中
		//下次下载会从该时间戳起进行增量下载
		date.setTime(mxVersion);
		//返回水位信息
		//由于hqlsql 和oracle精度不同， 
		//因此我们在下次下载的时候 可能会对该时间戳做1秒钟的提前量
		//但是这些多余的数据会进行update操作  
		//所以不会有很多影像
		return date;
	}
	
	/**
	 * 
	 * 功能：取区域ID
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	public String getRegionID() {
		return null;
	}

}
