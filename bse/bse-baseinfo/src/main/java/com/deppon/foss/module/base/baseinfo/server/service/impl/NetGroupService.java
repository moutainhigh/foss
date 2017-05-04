package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.INetGroupMixDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.INetGroupService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendNetGroupMixInfoToWDGHService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupMixEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.NetGroupDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.NetGroupException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 网点组服务类
 * @author foss-zhujunyong
 * @date Nov 2, 2012 1:37:20 PM
 * @version 1.0
 */
public class NetGroupService implements INetGroupService {

    /**
     * 
     * 日志
     */
   // private static final Logger log = Logger.getLogger(NetGroupService.class);

    /**
     * 
     * netGroupDao
     */
    @Inject
    private INetGroupMixDao netGroupMixDao;

    /**
     * 
     * orgAdministrativeInfoService
     */
    @Inject
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    
    /**
     * sendNetGroupMixInfoToWDGHService
     */
     
    private ISendNetGroupMixInfoToWDGHService sendNetGroupMixInfoToWDGHService;
    
    /**
     * 
     * @author foss-zhujunyong
     * @date Mar 11, 2013 3:16:01 PM
     * @param netGroupDao
     * @see
     */
    public void setNetGroupMixDao(INetGroupMixDao netGroupMixDao) {
        this.netGroupMixDao = netGroupMixDao;
    }

    /**
     * 
     * @author foss-zhujunyong
     * @date Mar 6, 2013 10:18:28 AM
     * @param orgAdministrativeInfoService
     * @see
     */
    public void setOrgAdministrativeInfoService(
    	IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }


    /**
     * 
     * @author foss-qirongsheng
     * @date Mar 23, 2016 2:47:28 PM
     * @param sendNetGroupMixInfoToWDGHService
     * @see
     */
	public void setSendNetGroupMixInfoToWDGHService(
			ISendNetGroupMixInfoToWDGHService sendNetGroupMixInfoToWDGHService) {
		this.sendNetGroupMixInfoToWDGHService = sendNetGroupMixInfoToWDGHService;
	}
    /** 
     * <p>添加网点组</p> 
     * @author foss-zhujunyong
     * @date Nov 4, 2012 11:52:18 AM
     * @param netGroup
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.INetGroupService#addNetGroup(com.deppon.foss.module.base.baseinfo.api.shared.dto.NetGroupDto)
     */
    @Override
    @Transactional
    public NetGroupDto addNetGroup(NetGroupDto netGroup) {
	if (netGroup == null) {
	    return null;
	}
	netGroup.trim();
	// 取一个唯一的网点组名
	String netGroupCode = UUID.randomUUID().toString();
	List<String> sourceOrganizationCodeList = netGroup.getSourceOrganizationCodeList();
	List<String> targetOrganizationCodeList = netGroup.getTargetOrganizationCodeList();
	if (CollectionUtils.isEmpty(sourceOrganizationCodeList) || CollectionUtils.isEmpty(targetOrganizationCodeList)) {
	    throw new NetGroupException(NetGroupException.ILLEGAL_NET_GROUP);
	}
	List<NetGroupMixEntity> nmLists = new ArrayList<NetGroupMixEntity>();
	// 添加出发网点组
	for (String orgCode : sourceOrganizationCodeList) {
	    if (StringUtils.isBlank(orgCode)) {
		continue;
	    }
	    NetGroupMixEntity entity = new NetGroupMixEntity();
	    entity.setOrgCode(orgCode);
	    entity.setOrgType(ComnConst.ORG_TYPE_SOURCE);
	    entity.setNetGroupCode(netGroupCode);
	    entity.setCreateUser(netGroup.getCreateUser());
	    entity.setFreightRouteVirtualCode(netGroup.getFreightRouteVirtualCode());
	    //添加是否快递网点    
	    entity.setExpNetworkGroup(netGroup.getExpNetworkGroup());
	    netGroupMixDao.addNetGroupMix(entity);
	    nmLists.add(entity);
	}
    //同步添加网点组到WDGH
    syncNetworkGroupMixInfoToWdgh(nmLists,NumberConstants.ONE);
    
	List<NetGroupMixEntity> nmList = new ArrayList<NetGroupMixEntity>();
	// 添加到达网点组
	for (String orgCode : targetOrganizationCodeList) {
	    if (StringUtils.isBlank(orgCode)) {
		continue;
	    }
	    NetGroupMixEntity entity = new NetGroupMixEntity();
	    entity.setOrgCode(orgCode);
	    entity.setOrgType(ComnConst.ORG_TYPE_TARGET);
	    entity.setNetGroupCode(netGroupCode);
	    entity.setCreateUser(netGroup.getCreateUser());
	    entity.setFreightRouteVirtualCode(netGroup.getFreightRouteVirtualCode());
	    //添加是否快递网点组
	    entity.setExpNetworkGroup(netGroup.getExpNetworkGroup());
	    netGroupMixDao.addNetGroupMix(entity);
	    nmList.add(entity);
	}
    //同步添加网点组到WDGH
    syncNetworkGroupMixInfoToWdgh(nmList,NumberConstants.ONE);
    
	netGroup.setNetGroupName(netGroupCode);
	// 未检查同一出发和到达存在于多个相同网点组的情况
	return enhanceNetGroupDto(netGroup);
    }


    /** 
     * <p>作废网点组</p> 
     * @author foss-zhujunyong
     * @date Nov 4, 2012 11:52:18 AM
     * @param netGroup
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.INetGroupService#deleteNetGroup(com.deppon.foss.module.base.baseinfo.api.shared.dto.NetGroupDto)
     */
    @Override
    //@Transactional
    public int deleteNetGroup(NetGroupDto netGroup) {
	if (netGroup == null) {
	    return FossConstants.FAILURE;
	}
	netGroup.trim();

	NetGroupMixEntity entity = new NetGroupMixEntity();
	entity.setNetGroupCode(netGroup.getNetGroupName());
	entity.setModifyUser(netGroup.getModifyUser());
	List<NetGroupMixEntity> list = netGroupMixDao.queryNetGroupMixByCode(netGroup.getNetGroupName());
	// 如果没有这个名字的网点组，就不用作废了
	if (CollectionUtils.isEmpty(list)) {
	    return FossConstants.FAILURE;
	}

	// 清除缓存
//	List<String> sourceList = new ArrayList<String>();
//	List<String> targetList = new ArrayList<String>();
//	for (NetGroupMixEntity c : list) {
//	    if (c != null && c.checkSource()) {
//		sourceList.add(c.getOrgCode());
//	    }
//	    if (c != null && c.checkTarget()) {
//		targetList.add(c.getOrgCode());
//	    }
//	}
//	for (String source : sourceList) {
//	    for (String target : targetList) {
//		String key = StringUtils.join(new String[] { source, target }, SymbolConstants.EN_COLON);
//		invalidList(key);
//	    }
//	}
//	}	
	//List<NetGroupMixEntity> outlist = netGroupMixDao.queryNetGroupMixByCode(entity.getNetGroupCode());
	//根据netgroupcode批量删除网点组
	int result = netGroupMixDao.deleteNetGroupMixByCode(entity);
	if(CollectionUtils.isNotEmpty(list) && result >0){
		List<NetGroupMixEntity> nmList = new ArrayList<NetGroupMixEntity>();
		Date date = new Date();
		for(NetGroupMixEntity ngmEntity:list){
			ngmEntity.setActive(FossConstants.INACTIVE);
			ngmEntity.setModifyDate(date);
			ngmEntity.setVersion(date.getTime());
			ngmEntity.setModifyUser(entity.getModifyUser());			
			nmList.add(ngmEntity);
    }
		//同步作废网点组到WDGH
		syncNetworkGroupMixInfoToWdgh(nmList, NumberConstants.THREE);	
	}
	return result;
    }


    /** 
     * <p>更新网点组</p> 
     * @author foss-zhujunyong
     * @date Nov 4, 2012 11:52:18 AM
     * @param netGroup
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.INetGroupService#updateNetGroup(com.deppon.foss.module.base.baseinfo.api.shared.dto.NetGroupDto)
     */
    @Override
    @Transactional
    public NetGroupDto updateNetGroup(NetGroupDto netGroup) {
	Date now = new Date();
    NetGroupMixEntity netGroupMix = new NetGroupMixEntity();
	if (netGroup == null) {
	    return null;
	}
	netGroup.trim();
	// 把即将更新的dto拆解成entity
	List<String> sourceList = netGroup.getSourceOrganizationCodeList();
	List<String> targetList = netGroup.getTargetOrganizationCodeList();
	
	if (CollectionUtils.isEmpty(sourceList) || CollectionUtils.isEmpty(targetList)) {
	    throw new NetGroupException(NetGroupException.ILLEGAL_NET_GROUP);
	}
	
	// 把数据库中的值按类型分配到两个列表中
	List<NetGroupMixEntity> list = netGroupMixDao.queryNetGroupMixByCode(netGroup.getNetGroupName());
	List<String> dbSourceList = new ArrayList<String>();
	List<String> dbTargetList = new ArrayList<String>();
	//313353 sonar
	this.sonarSplitOne(list, dbSourceList, dbTargetList);

	List<NetGroupMixEntity> nmListsss = new ArrayList<NetGroupMixEntity>();
	// 如果数据库中没有，则新增
	for (String orgCode : sourceList) {
	    if (!dbSourceList.contains(orgCode)) {
		NetGroupMixEntity entity = new NetGroupMixEntity();
		entity.setOrgCode(orgCode);
		entity.setOrgType(ComnConst.ORG_TYPE_SOURCE);
		entity.setNetGroupCode(netGroup.getNetGroupName());
		entity.setCreateUser(netGroup.getModifyUser());
		entity.setFreightRouteVirtualCode(netGroup.getFreightRouteVirtualCode());
		//添加是否快递网点组
	    entity.setExpNetworkGroup(netGroup.getExpNetworkGroup());
		netGroupMixDao.addNetGroupMix(entity);
	    netGroupMix = netGroupMixDao.addNetGroupMix(entity);
	    nmListsss.add(entity);
	    }
	}
	//同步新增快递网点组到WDGH
	syncNetworkGroupMixInfoToWdgh(nmListsss, NumberConstants.ONE);
	
	List<NetGroupMixEntity> nmListss = new ArrayList<NetGroupMixEntity>();
	// 如果数据库中有，而即将更新的数据中没有，说明要删除
	for (String orgCode : dbSourceList) {
	    if (!sourceList.contains(orgCode)) {
		NetGroupMixEntity entity = new NetGroupMixEntity();
		entity.setOrgCode(orgCode);
		entity.setOrgType(ComnConst.ORG_TYPE_SOURCE);
		entity.setNetGroupCode(netGroup.getNetGroupName());
		entity.setModifyUser(netGroup.getModifyUser());
		entity.setFreightRouteVirtualCode(netGroup.getFreightRouteVirtualCode());
		//entity.setFreightRouteVirtualCode(netGroup.getFreightRouteVirtualCode())
		entity.setActive(FossConstants.INACTIVE);
		entity.setModifyDate(now);
		entity.setVersion(now.getTime());
		//根据3个字段获取网点组
		List<NetGroupMixEntity> outlist = netGroupMixDao.queryNetGroupMix(entity);
		netGroupMixDao.deleteNetGroupMix(entity);
		for(NetGroupMixEntity ngmEntity:outlist){
			
			ngmEntity.setActive(FossConstants.INACTIVE);
			ngmEntity.setModifyDate(entity.getModifyDate());
			ngmEntity.setModifyUser(entity.getModifyUser());
			ngmEntity.setVersion(entity.getVersion());	

			nmListss.add(ngmEntity);
	    }
	}
	}
	//同步删除快递网点组到WDGH,
	syncNetworkGroupMixInfoToWdgh(nmListss, NumberConstants.THREE);	
	
	List<NetGroupMixEntity> nmList = new ArrayList<NetGroupMixEntity>();
	// 如果数据库中没有，则新增
	for (String orgCode : targetList) {
	    if (!dbTargetList.contains(orgCode)) {
		NetGroupMixEntity entity = new NetGroupMixEntity();
		entity.setOrgCode(orgCode);
		entity.setOrgType(ComnConst.ORG_TYPE_TARGET);
		entity.setNetGroupCode(netGroup.getNetGroupName());
		entity.setCreateUser(netGroup.getModifyUser());
		entity.setFreightRouteVirtualCode(netGroup.getFreightRouteVirtualCode());
		//添加是否快递网点组
	    entity.setExpNetworkGroup(netGroup.getExpNetworkGroup());
	    netGroupMix = netGroupMixDao.addNetGroupMix(entity);
	    nmList.add(netGroupMix);
	    }
	}
	//同步新增快递网点组到WDGH
	syncNetworkGroupMixInfoToWdgh(nmList, NumberConstants.ONE);
	
	List<NetGroupMixEntity> nmLists = new ArrayList<NetGroupMixEntity>();
	// 如果数据库中有，而即将更新的数据中没有，说明要删除
	for (String orgCode : dbTargetList) {
	    if (!targetList.contains(orgCode)) {
		NetGroupMixEntity entity = new NetGroupMixEntity();
		entity.setOrgCode(orgCode);
		entity.setOrgType(ComnConst.ORG_TYPE_TARGET);
		entity.setNetGroupCode(netGroup.getNetGroupName());
		entity.setModifyUser(netGroup.getModifyUser());
		entity.setFreightRouteVirtualCode(netGroup.getFreightRouteVirtualCode());
		//entity.setFreightRouteVirtualCode(netGroup.getFreightRouteVirtualCode());		
		entity.setActive(FossConstants.INACTIVE);
		entity.setModifyDate(now);
		entity.setVersion(now.getTime());

		//根据3个字段获取网点组
		List<NetGroupMixEntity> outlist = netGroupMixDao.queryNetGroupMix(entity);
		//根据3个字段删除网点组
		netGroupMixDao.deleteNetGroupMix(entity);
		for(NetGroupMixEntity ngmEntity:outlist){
			
			ngmEntity.setActive(FossConstants.INACTIVE);
			ngmEntity.setModifyDate(entity.getModifyDate());
			ngmEntity.setModifyUser(entity.getModifyUser());
			ngmEntity.setVersion(entity.getVersion());	
			//同步删除快递网点组到WDGH,
			nmLists.add(ngmEntity);
	    }
	}
	}
	syncNetworkGroupMixInfoToWdgh(nmLists, NumberConstants.THREE);	
	return enhanceNetGroupDto(netGroup);
    }
    
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitOne(List<NetGroupMixEntity> list, List<String> dbSourceList,
			List<String> dbTargetList) {
		for (NetGroupMixEntity entity : list) {
		    if (entity == null) {
			continue;
		    }
		    if (StringUtils.equals(entity.getOrgType(), ComnConst.ORG_TYPE_SOURCE) && !dbSourceList.contains(entity.getOrgCode())) {
			dbSourceList.add(entity.getOrgCode());
		    }
		    if (StringUtils.equals(entity.getOrgType(), ComnConst.ORG_TYPE_TARGET) && !dbTargetList.contains(entity.getOrgCode())) {
			dbTargetList.add(entity.getOrgCode());
		    }
		}
	}

    /** 
     * <p>根据走货路径取符合条件的网点组dto列表</p> 
     * @author foss-zhujunyong
     * @date Nov 4, 2012 11:52:20 AM
     * @param freightRouteVirtualCode
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.INetGroupService#queryNetGroupListByFreightRoute(java.lang.String)
     */
    @Override
    public List<NetGroupDto> queryNetGroupListByFreightRoute(
	    String freightRouteVirtualCode) {
	List<NetGroupDto> resultList = new ArrayList<NetGroupDto> ();
	if (StringUtils.isBlank(freightRouteVirtualCode)) {
	    return resultList;
	}
	
	List<NetGroupMixEntity> list = netGroupMixDao.queryNetGroupMixByFreightRoute(freightRouteVirtualCode);
	if (CollectionUtils.isEmpty(list)) {
	    return resultList;
	}
	// 把符合走货路径条件的实体按网点组名称分组放在map里
	Map<String, List<NetGroupMixEntity>> netGroupCodeMap = new HashMap<String, List<NetGroupMixEntity>> ();
	for (NetGroupMixEntity entity : list) {
	    if (netGroupCodeMap.get(entity.getNetGroupCode()) == null) {
		netGroupCodeMap.put(entity.getNetGroupCode(), new ArrayList<NetGroupMixEntity>());
	    }
	    netGroupCodeMap.get(entity.getNetGroupCode()).add(entity);
	}
	
	for (Entry<String, List<NetGroupMixEntity>> netGroupEntry : netGroupCodeMap.entrySet()) {
	    List<String> sourceList = new ArrayList<String>();
	    for (NetGroupMixEntity entity : netGroupEntry.getValue()) {
		if (StringUtils.equals(entity.getOrgType(), ComnConst.ORG_TYPE_SOURCE) && !sourceList.contains(entity.getOrgCode())) {
		    sourceList.add(entity.getOrgCode());
		}
	    }
	    List<String> targetList = new ArrayList<String>();
	    for (NetGroupMixEntity entity : netGroupEntry.getValue()) {
		if (StringUtils.equals(entity.getOrgType(), ComnConst.ORG_TYPE_TARGET) && !targetList.contains(entity.getOrgCode())) {
		    targetList.add(entity.getOrgCode());
		}
	    }
	    // 组装成需要的dto
	    NetGroupDto dto = new NetGroupDto();
	    dto.setNetGroupName(netGroupEntry.getKey());
	    dto.setSourceOrganizationCodeList(sourceList);
	    dto.setTargetOrganizationCodeList(targetList);
	    dto.setFreightRouteVirtualCode(freightRouteVirtualCode);
	    resultList.add(dto);
	}
	return enhanceNetGroupDtoList(resultList);
    }


    /** 
     * <p>根据出发营业部和到达营业部找出符合的走货路径</p> 
     * @author foss-zhujunyong
     * @date Nov 4, 2012 11:52:20 AM
     * @param sourceSaleCode
     * @param targetSaleCode
     * @param productCode
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.INetGroupService#queryFreightRouteCode(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<String> queryFreightRouteCode(String sourceSaleCode, String targetSaleCode) {
	List<String> resultList = new ArrayList<String>();
	// 参数判断
	if (StringUtils.isBlank(sourceSaleCode) || StringUtils.isBlank(targetSaleCode)) {
	    return resultList;
	}
	// 找缓存
	List<NetGroupMixEntity> list = null;
	list = netGroupMixDao.queryNetGroupMixBySourceTarget(sourceSaleCode, targetSaleCode);
	
	if (CollectionUtils.isEmpty(list)) {
	    return resultList;
	}
	for (NetGroupMixEntity entity : list) {
	    if (!resultList.contains(entity.getFreightRouteVirtualCode())) {
		resultList.add(entity.getFreightRouteVirtualCode());
	    }
	}
	return resultList;
    }


    /**
     * 
     * <p>根据走货路径批量作废网点组</p> 
     * @author foss-zhujunyong
     * @date Nov 13, 2012 10:18:37 AM
     * @param freightRouteVirtualCode
     * @param modifyUser
     * @return
     * @see
     */
    @Override
    @Transactional
    public int deleteNetGroupByFreightRoute(String freightRouteVirtualCode, String modifyUser) {
	if (StringUtils.isBlank(freightRouteVirtualCode) || StringUtils.isBlank(modifyUser)) {
	    return FossConstants.FAILURE;
	}
	List<NetGroupMixEntity> list = netGroupMixDao.queryNetGroupMixByFreightRoute(freightRouteVirtualCode);
	// 没数据就不用作废了
	if (CollectionUtils.isEmpty(list)) {
	    return FossConstants.FAILURE;
	}
	
	//根据走货路径code查询
	List<NetGroupMixEntity>  outlist = netGroupMixDao.queryNetGroupMixByFreightRoute(freightRouteVirtualCode);		
	int result = netGroupMixDao.deleteNetGroupMixByFreightRoute(freightRouteVirtualCode, modifyUser);
	if(CollectionUtils.isNotEmpty(outlist)){
		Date date = new Date();
		for(NetGroupMixEntity ngmEntity:outlist){
			ngmEntity.setActive(FossConstants.INACTIVE);
			ngmEntity.setModifyDate(date);
			ngmEntity.setModifyUser(modifyUser);
			ngmEntity.setVersion(date.getTime());
    }
	}
	return result;
    }

    
    /**
     * 
     * <p>根据出发营业部编码和出发外场编码作废网点组</p> 
     * @author foss-zhujunyong
     * @date Jun 27, 2013 11:22:19 AM
     * @param salesCode
     * @param transferCode
     * @param modifyUser
     * @see
     */
    @Override
    @Transactional
    public void deleteSourceNetGroup(String salesCode, String transferCode, 
    		String modifyUser,String transType,String expNetworkGroup) {    
	if (StringUtils.isBlank(salesCode) || StringUtils.isBlank(transferCode)) {
	    return;
	}
	// 先找出符合条件的网点组虚拟编码列表
	List<String> virtualCodeList = netGroupMixDao.queryVirtualCodeListBySourceSalesCode(salesCode, transferCode,expNetworkGroup);
	
	if (CollectionUtils.isEmpty(virtualCodeList)){
	    return;
	}
	
	//根据运输类型，空运对应产品为一级空运、二级空运、三级精准空运的，汽运则对应其他所有产品类型，去对应删除相应网点组信息,空运说明都是LD的
	String[] productArray=new String[NumberConstants.NUMBER_3];
	productArray[0]=PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20002;
	productArray[1]=PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20004;
	productArray[2]=PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT;
	if(StringUtils.equals(transType,DictionaryValueConstants.BSE_LINE_TRANSTYPE_KONGYUN)){
		//根据网点组virtualCode和产品列表返回需要删除的网点组virtualCode
		 virtualCodeList=queryVirCodeListByProCodeListIn(virtualCodeList,productArray);
	}
	else{
		//根据网点组virtualCode和产品列表返回需要删除的网点组virtualCode
		 virtualCodeList=queryVirCodeListByProCodeListNotIn(virtualCodeList,productArray);
	}
	List<NetGroupMixEntity> nmList = new ArrayList<NetGroupMixEntity>();
	// 然后依次作废，使用for循环来对mq队列发送消息速度较慢，改为list
	for (String virtualCode : virtualCodeList) {
	    netGroupMixDao.deleteNetGroupMixByVirtualCode(virtualCode, modifyUser);
	    //根据virtualcode获取网点组实体
	    NetGroupMixEntity ngmEntity = netGroupMixDao.queryNetGroupMixByVirtualCode(virtualCode);
	    if(null != ngmEntity)
	    	nmList.add(ngmEntity);
    }
	//同步作废网点组到WDGH，按照virtualCode作废
	syncNetworkGroupMixInfoToWdgh(nmList,NumberConstants.THREE);
    }
    
    /**
     *<p>同步给网点规划</p>
     *@author 269231 -qirongsheng
     *@date 2016-3-23 下午2:48:41
     *@param netGroupMixEntity
     *@param type
     */
    private void syncNetworkGroupMixInfoToWdgh(List<NetGroupMixEntity> nmlist,
			Integer type) {	
    	if(null != nmlist && nmlist.size() >= 1){
        	//同步接口
        	sendNetGroupMixInfoToWDGHService.syncNetGroupMixInfo(nmlist, type.toString());
    	}
	}
    
    /**
     * 
     * <p>根据到达营业部编码和到达外场编码作废网点组</p> 
     * @author foss-zhujunyong
     * @date Jun 27, 2013 11:22:19 AM
     * @param salesCode
     * @param transferCode
     * @param modifyUser
     * @see
     */
    @Override
    //@Transactional
    public void deleteTargetNetGroup(String salesCode, String transferCode, String modifyUser,String expNetworkGroup) {
    	
//    NetGroupMixEntity netGroupMixEntity = new NetGroupMixEntity();
    
	if (StringUtils.isBlank(salesCode) || StringUtils.isBlank(transferCode)) {
	    return;
	}
	// 先找出符合条件的网点组虚拟编码列表
	List<String> virtualCodeList = netGroupMixDao.queryVirtualCodeListByTargetSalesCode(salesCode, transferCode,expNetworkGroup);
	if (CollectionUtils.isEmpty(virtualCodeList)){
	    return;
	}
	List<NetGroupMixEntity> nmList = new ArrayList<NetGroupMixEntity>();
	// 然后依次作废，使用for循环来对mq队列发送消息速度较慢，改为list
	for (String virtualCode : virtualCodeList) {
	    netGroupMixDao.deleteNetGroupMixByVirtualCode(virtualCode, modifyUser);    
	    //根据virtualcode获取网点组实体
	    NetGroupMixEntity ngmEntity = netGroupMixDao.queryNetGroupMixByVirtualCode(virtualCode);
	    if(null != ngmEntity)
	    	nmList.add(ngmEntity);	   
    }
  	//同步作废网点组到WDGH，按照virtualCode作废
	syncNetworkGroupMixInfoToWdgh(nmList,NumberConstants.THREE);
    }

    
    /**
     * 
     * <p>批量填充出发营业部和到达营业部列表</p> 
     * @author foss-zhujunyong
     * @date Mar 6, 2013 10:19:22 AM
     * @param list
     * @return
     * @see
     */
    private List<NetGroupDto> enhanceNetGroupDtoList(List<NetGroupDto> list) {
	if (CollectionUtils.isEmpty(list)) {
	    return list;
	}
	for (NetGroupDto dto : list) {
	    enhanceNetGroupDto(dto);
	}
	return list;
    }
    
    /**
     * 
     * <p>填充出发营业部和到达营业部列表</p> 
     * @author foss-zhujunyong
     * @date Mar 6, 2013 10:19:34 AM
     * @param dto
     * @return
     * @see
     */
    private NetGroupDto enhanceNetGroupDto(NetGroupDto dto) {
	if (dto == null) {
	    return dto;
	}
	if (CollectionUtils.isEmpty(dto.getSourceOrganizationNameList()) && CollectionUtils.isNotEmpty(dto.getSourceOrganizationCodeList())) {
	    List<String> nameList = new ArrayList<String>();
	    for (String code : dto.getSourceOrganizationCodeList()) {
		nameList.add(orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(code));
	    }
	    dto.setSourceOrganizationNameList(nameList);
	}
	if (CollectionUtils.isEmpty(dto.getTargetOrganizationNameList()) && CollectionUtils.isNotEmpty(dto.getTargetOrganizationCodeList())) {
	    List<String> nameList = new ArrayList<String>();
	    for (String code : dto.getTargetOrganizationCodeList()) {
		nameList.add(orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(code));
	    }
	    dto.setTargetOrganizationNameList(nameList);
	}
	return dto;
    }

    /**
     * 
     * <p>清空指定key的缓存列表</p> 
     * 
     * 按现有表结构编辑网点组清一次缓存要先查一遍数据库，目前瓶颈在编辑网点组，而非查询
     * 先不走缓存
     * 
     * 
     * @author foss-zhujunyong
     * @date Mar 6, 2013 10:19:54 AM
     * @param key
     * @see
     */
   /* @SuppressWarnings({ "unchecked", "unused" })
    private void invalidList(String key) {
	((ICache<String, List<NetGroupEntity>>)CacheManager.getInstance().getCache(FossTTLCache.NETGROUP_CACHE_UUID)).invalid(key);
    }*/

    /**
     * 
     * <p>取缓存中的数据</p> 
     * 
     * 按现有表结构编辑网点组清一次缓存要先查一遍数据库，目前瓶颈在编辑网点组，而非查询
     * 先不走缓存
     * 
     * @author foss-zhujunyong
     * @date Mar 6, 2013 10:20:08 AM
     * @param key
     * @return
     * @see
     */
    /*@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
    private List<NetGroupEntity> queryListCache(String key) {
	List<NetGroupEntity> resultList = new ArrayList<NetGroupEntity>();
	try {
	    CacheManager cacheManager = CacheManager.getInstance();
	    if (cacheManager == null) {
		return resultList;
	    }
	    ICache cache = cacheManager.getCache(FossTTLCache.NETGROUP_CACHE_UUID);
	    if (cache == null) {
		return resultList;
	    }
	    resultList = (List<NetGroupEntity>) cache.get(key);
	} catch (Exception t) {
	    log.error("cache找不到", t);
	}
	return resultList;
    }*/
    
    /**
     * 
     * <p>根据网点组虚拟网点编码和产品CODE，获取相对应的网点组虚拟编码</p> 
     * 
     * 
     * @author foss-zhangjiheng
     * @date 2月19, 2014 10:20:08 AM
     * @param key
     * @return
     * @see
     */
	private List<String> queryVirCodeListByProCodeListIn(List<String> virtualCodeList,String[] productArray){
    	return netGroupMixDao.queryVirtualCodeListByProductCodeIn(virtualCodeList,productArray);
    }
    
    /**
     * 
     * <p>根据网点组虚拟网点编码和产品CODE，获取相对应的网点组虚拟编码</p> 
     * 
     * 
     * @author foss-zhangjiheng
     * @date 2月19, 2014 10:20:08 AM
     * @param key
     * @return
     * @see
     */
	private List<String> queryVirCodeListByProCodeListNotIn(List<String> virtualCodeList,String[] productArray){
    	return netGroupMixDao.queryVirtualCodeListByProductCodeNotIn(virtualCodeList,productArray);
    }
}
