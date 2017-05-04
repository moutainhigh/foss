package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISendDistrictMapService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SendDistrictItemAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SendDistrictMapEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SendDistrictMapException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.SendDistrictMapVo;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.define.FossConstants;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
/**
 * 派送货区行政区域映射关系Action
 * @author WeiXing
 *
 */
public class SendDistrictMapAction extends AbstractAction {
	
	private static final long serialVersionUID = 9033163481315747560L;
	private static final Logger LOGGER =LoggerFactory.getLogger(SendDistrictMapAction.class);
	
	private SendDistrictMapVo sendDistrictMapVo;
	private ISendDistrictMapService sendDistrictMapService;
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private IGoodsAreaService goodsAreaService;
	private IAdministrativeRegionsService administrativeRegionsService;
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * <P>
	 * 根据条件分页查询
	 * <P>
	 * @author :WeiXing
	 * @date : 2014-10-28上午11:23:49
	 * @return
	 */
	@JSON
	public String querySendDistrictMapList() {
		try {
			SendDistrictMapEntity entity=sendDistrictMapVo.getSendDistrictMapEntity();
			entity.setActive(FossConstants.ACTIVE);
			
			List<SendDistrictMapEntity> sendDistrictMapEntityGroupList=sendDistrictMapService.querySendDistrictMapGroupList(entity, limit, start);
			for(int i=0;i<sendDistrictMapEntityGroupList.size();i++){
				SendDistrictMapEntity tmp=sendDistrictMapEntityGroupList.get(i);
				SendDistrictMapEntity tmpDistrict=new SendDistrictMapEntity();
				tmpDistrict.setTransferCenterCode(tmp.getTransferCenterCode());
				tmpDistrict.setGoodsAreaCode(tmp.getGoodsAreaCode());
				tmpDistrict.setZoneCode(tmp.getZoneCode());
				/**
				 * 根据zoneCode分区 查询件区信息
				 */
				List<SendDistrictItemAreaEntity> itemAreaEntitys=sendDistrictMapService.queryItemAreaByIdOrZoneCode(null,tmp.getZoneCode());
				tmp.setSendDistrictItemAreaEntitys(itemAreaEntitys);
				List<SendDistrictMapEntity> tmpDistrictEntities= sendDistrictMapService.querySendDistrictMapListbyEntity(tmpDistrict);
				//String districtNameStr="";  //行政区域名称
				//String districtCodeStr="";   //行政区域代码
				//String idStr=""; //ID
				StringBuffer districtNameStr = new StringBuffer();  //行政区域名称
				StringBuffer districtCodeStr = new StringBuffer();  //行政区域代码
				StringBuffer idStr=new StringBuffer(); //ID
				if(tmpDistrictEntities!=null){
					for(int x=0;x<tmpDistrictEntities.size();x++){//将行政区域名称、代码、ID分别拼接成一个字符串,中间以逗号隔开
						//districtCodeStr=districtCodeStr+tmpDistrictEntities.get(x).getDistrictCode();
						//idStr=idStr+tmpDistrictEntities.get(x).getId();
						districtCodeStr.append(tmpDistrictEntities.get(x).getDistrictCode());
						idStr.append(tmpDistrictEntities.get(x).getId());
						//根据行政区域CODE查询行政区域NAME
						AdministrativeRegionsEntity   administrativeRegionsEntity=administrativeRegionsService.queryAdministrativeRegionsByCode(tmpDistrictEntities.get(x).getDistrictCode());
						if(administrativeRegionsEntity!=null){
							//districtNameStr=districtNameStr+administrativeRegionsEntity.getName();
							districtNameStr.append(administrativeRegionsEntity.getName());
						}
						if(x!=(tmpDistrictEntities.size()-1)){
							districtNameStr.append(",");
							districtCodeStr.append(",");
							idStr.append(",");
						}
					}
				}
				tmp.setDistrictName(districtNameStr.toString());
				tmp.setDistrictCode(districtCodeStr.toString());
				tmp.setId(idStr.toString());
				//根据外场CODE查询外场NAME
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntityTmp=orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(tmp.getTransferCenterCode());
				if(orgAdministrativeInfoEntityTmp!=null){
					tmp.setTransferCenterName(orgAdministrativeInfoEntityTmp.getName());
				}
				//根据库区GOODS_AREA_CODE查询库区GOODS_AREA_NAME
				List<GoodsAreaEntity> goodsAreaEntityList=goodsAreaService.queryGoodsAreaListByType(tmp.getTransferCenterCode(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
				if(goodsAreaEntityList!=null&&goodsAreaEntityList.size()!=0){
					tmp.setGoodsAreaName(goodsAreaEntityList.get(0).getGoodsAreaName());
				}
				//设置分区NAME
				if(null != tmpDistrictEntities 
						&& 0 < tmpDistrictEntities.size() 
						&& null != tmpDistrictEntities.get(0)){
					tmp.setZoneName(tmpDistrictEntities.get(0).getZoneName());
				}
			}
			sendDistrictMapVo.setSendDistrictMapEntities(sendDistrictMapEntityGroupList);
			long totalCount = sendDistrictMapService.querySendDistrictMapGroupCount(entity);
			this.setTotalCount(totalCount);
			return returnSuccess();
		} catch (SendDistrictMapException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	 *<P>根据id批量作废<P>
	 * @author :WeiXing
	 * @date : 2014-11-04下午5:59:17
	 * @return
	 */
	@JSON
	public String updateSendDistrictMap(){
		try {
			sendDistrictMapService.deleteAddSendDistrictMapList(sendDistrictMapVo);
			return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (SendDistrictMapException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
	
	/**
	 * 
	* @Title: getTransferCenter
	* @Description:根据 code  查询顶级外场
	* @author 189284--zhang xu 
	* @date 2014-10-28 上午11:42:56 
	* @param @param currentOrgCode
	* @param @return
	* @return OrgAdministrativeInfoEntity    返回类型
	 */
	public OrgAdministrativeInfoEntity getTransferCenter(String currentOrgCode){
		//设置查询参数
				List<String> bizTypesList = new ArrayList<String>();
				//外场类型
				bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.
						queryOrgAdministrativeInfoByCode(currentOrgCode, bizTypesList);
					//返回部门
				return orgAdministrativeInfoEntity;
	}
	/**
	 * 
	* @Title: qureyTransferCenter
	* @Description:查询地外场 和对应的 驻地派送货区 
	* @author 189284--zhang xu 
	* @date 2014-10-28 上午11:43:21 
	* @param @return
	* @return String    返回类型
	 */
	@JSON 
	public String qureyTransferCenter(){
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity=getTransferCenter(FossUserContext.getCurrentDeptCode());
		if(orgAdministrativeInfoEntity==null){
			return returnError("无对应顶级外场信息");
		}
		if(StringUtils.equals(orgAdministrativeInfoEntity.getTransferCenter(), FossConstants.YES)){
			SendDistrictMapEntity sendDistrictMap=new SendDistrictMapEntity();
			sendDistrictMap.setTransferCenterCode(orgAdministrativeInfoEntity.getCode());
			sendDistrictMap.setTransferCenterName(orgAdministrativeInfoEntity.getName());
			// DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION   驻地派送货区  
			/**
			 * 根据业务要求  找对应外场的  驻地派送货区 
			 * */
			List<GoodsAreaEntity> goodsAreaList=goodsAreaService.queryGoodsAreaListByType(orgAdministrativeInfoEntity.getCode(), DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
			if(goodsAreaList!=null&&goodsAreaList.size()>0){
				if(StringUtils.isEmpty(goodsAreaList.get(0).getGoodsAreaCode())||StringUtils.isEmpty(goodsAreaList.get(0).getGoodsAreaName())){
					return returnError("无对应驻地派送货区信息");
				}
				sendDistrictMap.setGoodsAreaCode(goodsAreaList.get(0).getGoodsAreaCode());
				sendDistrictMap.setGoodsAreaName(goodsAreaList.get(0).getGoodsAreaName());
			}else{
				return returnError("无对应驻地派送货区信息");	
			}			
			sendDistrictMapVo.setSendDistrictMapEntity(sendDistrictMap);
			return returnSuccess();
		}
		return returnSuccess();
	}
	/**
	 * 
	* @Title: querySendDistrictMapByAreaCodeOrZoneName
	* @Description:根据分区名称 或者分区Code查询 派送货区行政区域映射基础资料 信息
	* sendDistrictMapVo.sendDistrictMap实体
	*zoneName分区名称
	*areaCode分区Code
	* @author 189284--zhang xu 
	* @date 2014-10-30 上午10:54:25 
	* @param @return
	* @return String    返回类型
	 */
	@JSON
	public String  querySendDistrictMapBydistrictCodeOrdistrictName(){
		try {
			List<SendDistrictMapEntity> sendDistrictMap=sendDistrictMapService.querySendDistrictMapBydistrictCodeOrdistrictName(sendDistrictMapVo.getSendDistrictMapEntity());
			if(sendDistrictMap!=null&&sendDistrictMap.size()>0){
				sendDistrictMapVo.setSendDistrictMapEntity(sendDistrictMap.get(0));
			}else{
				sendDistrictMapVo.setSendDistrictMapEntity(new SendDistrictMapEntity());
			}
			return  returnSuccess();
		} catch (SendDistrictMapException e) {
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: addsendDistrictMap
	* @Description: 新增 派送货区行政区域映射基础资料 
	* @author 189284--zhang xu 
	* @date 2014-10-30 下午3:13:29 
	* @param @return
	* @return String    返回类型
	 */
	@JSON
	public String addsendDistrictMap(){
		try {
			sendDistrictMapService.addSendDistrictMap(sendDistrictMapVo.getSendDistrictMapEntity(),
					sendDistrictMapVo.getSendDistrictMapEntities(),
					sendDistrictMapVo.getSendDistrictItemAreaEntitys()
					);
			return returnSuccess();
		} catch (SendDistrictMapException e) {
			return returnError(e.getMessage());
		}
		
	}
	/**
	 * 
	* @Title: deleteByZoneName
	* @Description: 根据条件作废  派送货区行政区域映射基础资料
	* @author 189284--zhang xu 
	* @date 2014-11-3 下午6:18:25 
	* @param @return
	* @return String    返回类型
	 */
	public String deleteByZoneName(){
		try {
			sendDistrictMapService.deleteByZoneName(sendDistrictMapVo.getSendDistrictMapEntity());
			return returnSuccess();
		} catch (SendDistrictMapException e) {
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	* @Title: deleteSendDistrictMapList
	* @Description:作废多条  派送货区行政区域映射基础资料
	* @author 189284--zhang xu 
	* @date 2014-11-4 上午10:00:29 
	* @param @return
	* @return String    返回类型
	 */
	@JSON
	public String deleteSendDistrictMapList(){
		try {
			sendDistrictMapService.deleteSendDistrictMapByIds(sendDistrictMapVo.getDeleteids(),FossUserContext.getCurrentInfo().getEmpCode());
			return returnSuccess();
		} catch (SendDistrictMapException e) {
			return returnError(e.getMessage());
		}
	}
	
	/**  
	 * 部门复杂查询service
	 * 设置orgAdministrativeInfoComplexService  
	 * @param orgAdministrativeInfoComplexService orgAdministrativeInfoComplexService  
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	public SendDistrictMapVo getSendDistrictMapVo() {
		return sendDistrictMapVo;
	}
	public void setSendDistrictMapVo(SendDistrictMapVo sendDistrictMapVo) {
		this.sendDistrictMapVo = sendDistrictMapVo;
	}
	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}
	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	public void setSendDistrictMapService(
			ISendDistrictMapService sendDistrictMapService) {
		this.sendDistrictMapService = sendDistrictMapService;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
