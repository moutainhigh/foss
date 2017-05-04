package com.deppon.foss.module.pickup.waybill.server.fetcher;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.entity.SyncDataRequest;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.sync.AbstractSyncDataFetcher;
import com.deppon.foss.framework.server.components.sync.ISyncDataDao;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 
**
* <b style="font-family:微软雅黑"><small>Description:数据抓取抽象类</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
*  1  2011-4-27  steven.cheng 新增
* </div>  
 */
public abstract class AbstractSyncDataFetcherWaybill<T> extends AbstractSyncDataFetcher<T>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSyncDataFetcherWaybill.class);

	private Class<T> realClazz ;
	private static Object THREAD_COUNT_LOCK = new Object(); //线程对象锁
	/**
	 * 系统常量service
	 */
	private IConfigurationParamsService configurationParamsService;
	
	private static final int NUMBER_100000 = 100000;
	
	@Resource(name="configurationParamsService")
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AbstractSyncDataFetcherWaybill(){
//		realClazz =(Class<T>) ((ParameterizedType) (getClass().getGenericSuperclass())).getActualTypeArguments()[0];
		//获取泛型类型
		Type t = getClass().getGenericSuperclass();                                                           
		Type arg;                                                                                             
		if(t instanceof  ParameterizedType){                                                                  
		        arg = ((ParameterizedType)t).getActualTypeArguments()[0];                                     
		}else if(t instanceof Class){                                                                         
		        arg = ((ParameterizedType)((Class)t).getGenericSuperclass()).getActualTypeArguments()[0];     
		                                                                                                      
		}else{                                                                                                
		        throw new BusinessException("Can not handle type construction for '"+getClass()+"'!");         
		}                                                                                                     
		                                                                                                      
		if(arg instanceof Class){                                                                             
		        this.realClazz = (Class<T>)arg;                                                         
		}else if(arg instanceof ParameterizedType){                                                           
		        this.realClazz = (Class<T>)((ParameterizedType)arg).getRawType();                       
		}else{                                                                                                
		        throw new BusinessException("Problem dtermining generic class for '"+getClass()+"'! ");        
		} 
	}
	
	/**
	 * 
	 * @see com.deppon.foss.framework.server.components.sync.ISyncDataFetcher#getFetcherKey()
	 * getFetcherKey
	 * @return
	 * @since:0.7
	 */
	@Override
	public Class<T> getFetcherKey() {
		return realClazz;
	}
	
	public abstract ISyncDataDao<T> getSyncDataDao();
	
	/**
	 * 
	 * @see com.deppon.foss.framework.server.components.sync.ISyncDataFetcher#getLastTime()
	 * getLastTime
	 * @return
	 * @since:0.7
	 */
	@Override
	public Date getLastTime() {
		return getSyncDataDao().getLastModifyTime();
		//return request.getFromDate();
	}
	
	/**
	 * 
	 * @see com.deppon.foss.framework.server.components.sync.ISyncDataFetcher#getSyncData(com.deppon.foss.framework.entity.SyncDataRequest)
	 * getSyncData
	 * @param request
	 * @return
	 * @since:0.7
	 */
	/*@Override
	public List<T> getSyncData(SyncDataRequest request) {
		return getSyncDataDao().getSyncData(request.getFromDate(), request.getMaxDate(), request.getFromPage(),request.getUserID(), getPageSize());
	}*/
	public List<T> getSyncData(SyncDataRequest request) {
		//zxy 20140424 MANA-2018 start 新增:实时判断下载开关
		boolean downSwitch = true;
		downSwitch = isOnSwitchBySwtOrgCode(FossUserContext.getCurrentDeptCode(),WaybillConstants.FOSS_DOWNLOAD_SWITCH);
		if(!downSwitch){
			throw new BusinessException("下载开关已关闭[downSwitch is closed.]");
		}
		//zxy 20140424 MANA-2018 end 新增:实时判断下载开关
		
		//zxy 20140313 MANA-2019 start 修改:完善此开关
		ConfigurationParamsEntity entity = null;
		try{
			entity = configurationParamsService.queryConfigurationParamsByOrgCode
					(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
						WaybillConstants.DOWLOAD_MAX_THREAD,
							FossConstants.ROOT_ORG_CODE);
		}catch(Exception e){
			LOGGER.info(e.getMessage());
		}
		String maxThread =  null;
		int threadMax=0;
		if (entity != null) {
			maxThread = entity.getConfValue();
			//参数
			try {
				threadMax = Integer.parseInt(maxThread);
				//天数如果弄错了不能解析为INTEGER
			} catch (Exception e) {
				LOGGER.info(e.getMessage());
			}
		}else{
			threadMax=NUMBER_100000;
		}
		//zxy 20140313 MANA-2019 end 修改:完善此开关
		
		//count
		if(threadMax>-2){
			if(ExpWaybillConstants.count>threadMax){
				return new ArrayList();
			}else{
				try{
					//zxy 20140415 MANA-2018 增加锁
					synchronized(THREAD_COUNT_LOCK){
						ExpWaybillConstants.count++;
					}
					if(request.getList()==null || request.getList().isEmpty()){
						return getSyncDataDao().getSyncData(request.getFromDate(),request.getUserID(), request.getRegionID(), request.getPagination(), request.getSyncPage());
					}else{
						return getSyncDataDao().getSyncData(request);
					}
				}finally{
					synchronized(THREAD_COUNT_LOCK){
						ExpWaybillConstants.count--;
					}
				}
			}
			
		}else{
			if(request.getList()==null || request.getList().isEmpty()){
				return getSyncDataDao().getSyncData(request.getFromDate(),request.getUserID(), request.getRegionID(), request.getPagination(), request.getSyncPage());
			}else{
				return getSyncDataDao().getSyncData(request);
			}
			
		}
		
		
	}
	
	public boolean isOffSwitchBySwtOrgCode(String orgCode,String swtichCode){
		ConfigurationParamsEntity entity = new ConfigurationParamsEntity();
		entity.setActive(FossConstants.YES);
		entity.setOrgCode(orgCode);
		entity.setCode(swtichCode);
		entity.setConfValue(FossConstants.NO);
		List lst = configurationParamsService.queryConfigurationParamsByOrgCode(entity);
		if(lst != null && lst.size() > 0)
			return true;
		else
			return false;
	}
	
	/**
     * 判断开关是否处于开启状态： 
	  * Description: 离线下载功能的开关配置 BUG-55198
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:37
	  * @param orgCode	机构
	  * @param swtichCode 开关类型
	  * @return
	 */
	public boolean isOnSwitchBySwtOrgCode(String orgCode,String swtichCode){
		//如果已关闭则直接返回false，如果未关闭再查是否有开启开关，如果无表示未开启
		if(!isOffSwitchBySwtOrgCode(orgCode,swtichCode)){
			ConfigurationParamsEntity entity = new ConfigurationParamsEntity();
			entity.setActive(FossConstants.YES);
			entity.setOrgCode(orgCode);
			entity.setCode(swtichCode);
			entity.setConfValue(FossConstants.YES);
			List lst = configurationParamsService.queryConfigurationParamsByOrgCode(entity);
			if(lst != null && lst.size() > 0)
				return true;
			else
				return false;
		}else
			return false;
	}
}
