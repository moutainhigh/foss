package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPlatformService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IWaitForkAreaService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WaitForkAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.WaitForkAreaException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.WaitForkAreaVo;

/**
 * @author 092020-lipengfei
 * @version V1.0
 * @Description 待叉区Action
 * @Time 2014-4-25
 */
public class WaitForkAreaInfoAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3660407747331687726L;
	/**
	 * 待叉区vo
	 */
	private WaitForkAreaVo waitForkAreaVo=new WaitForkAreaVo();
	/**
	 * 待叉区service
	 */
	private IWaitForkAreaService waitForkAreaService;
	/**
	 * 库区service
	 */
	private IGoodsAreaService goodsAreaService;
	/**
	 * 月台service
	 */
	private IPlatformService platformService;
	private static final Logger LOGGER = LoggerFactory.getLogger(WaitForkAreaInfoAction.class);
	public void setPlatformService(IPlatformService platformService) {
		this.platformService = platformService;
	}
	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}
	public void setWaitForkAreaService(IWaitForkAreaService waitForkAreaService) {
		this.waitForkAreaService = waitForkAreaService;
	}
	public WaitForkAreaVo getWaitForkAreaVo() {
		return waitForkAreaVo;
	}
	public void setWaitForkAreaVo(WaitForkAreaVo waitForkAreaVo) {
		this.waitForkAreaVo = waitForkAreaVo;
	}
	/**
	 * @author 092020-lipengfei
	 * @Description 新增待叉区
	 * @Time 2014-4-25
	 * @return
	 */
	@JSON
	public String addWaitForkArea(){
		try {
			waitForkAreaService.addWaitForkArea(waitForkAreaVo.getWaitForkAreaEntity());
			
			return returnSuccess("新增成功！");
		} catch (WaitForkAreaException e) {
			 LOGGER.debug(e.getMessage(), e);
			 return returnError(e);
		}
	}
	/**
	 * @author 092020-lipengfei
	 * @Description 修改待叉区
	 * @Time 2014-4-25
	 * @return
	 */
	@JSON
	public String updateForkArea(){
	 
		WaitForkAreaEntity entity=waitForkAreaVo.getWaitForkAreaEntity();
		try {
			waitForkAreaService.updateWaitForkArea(entity);
			return returnSuccess("修改成功！");
		} catch (WaitForkAreaException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
	/**
	 * @author 092020-lipengfei
	 * @Description 删除待叉区
	 * @Time 2014-4-25
	 * @return
	 */
	@JSON
	public String deleteWaitForkArea(){
		WaitForkAreaEntity entity=waitForkAreaVo.getWaitForkAreaEntity();
		waitForkAreaService.deleteWaitForkAreaByVirtualCode(entity);
		return returnSuccess("作废成功！");
	}
	/**
	 * @author 130346-lifanghong
	 * @Description 批量删除待叉区
	 * @Time 2014-4-25
	 * @return
	 */
	@JSON
	public String deleteDistanceBetweenPlatform(){
		List<String> waitForkAreaVirtualCodes = waitForkAreaVo.getWaitForkAreaVirtualCodes();
		 for(String virtualCode:waitForkAreaVirtualCodes){
			WaitForkAreaEntity waitForkAreaEntity  = waitForkAreaService.queryWaitForkAreaByVirtualCode(virtualCode);
			waitForkAreaService.deleteWaitForkAreaByVirtualCode(waitForkAreaEntity);
		 }
		return returnSuccess("作废成功！");
	}
	/**
	 * @author 130346-lifanghong
	 * @Description 批量删除待叉区于月台距离
	 * @Time 2014-4-25
	 * @return
	 */
	@JSON
	public String deleteWaitForkAreas(){
		List<String> waitForkAreaVirtualCodes = waitForkAreaVo.getWaitForkAreaVirtualCodes();
		 for(String virtualCode:waitForkAreaVirtualCodes){
			WaitForkAreaEntity waitForkAreaEntity  = waitForkAreaService.queryWaitForkAreaByVirtualCode(virtualCode);
			waitForkAreaService.deleteWaitForkAreaByVirtualCode(waitForkAreaEntity);
		 }
		return returnSuccess("作废成功！");
	}
	/**
	 * @author 092020-lipengfei
	 * @Description 查询待叉区
	 * @Time 2014-4-25
	 * @return
	 */
	@JSON
	public String queryWaitForkAreaByParams(){
		WaitForkAreaEntity entity=waitForkAreaVo.getWaitForkAreaEntity();
		waitForkAreaVo.setWaitForkAreaEntityList(waitForkAreaService.queryWaitForkAreaByParams(entity,limit,start));
		totalCount=waitForkAreaService.queryWaitForkAreaCount(entity);
		return returnSuccess();
	}
	/**
	 * @author 李鹏飞
	 * @Description 查询当前外场所有库区
	 * @Time 2014-4-25
	 * @return
	 */
	@JSON
	public String queryGoodsArea(){
//		WaitForkAreaEntity waitForAreaEntity = new WaitForkAreaEntity();
//		waitForAreaEntity = waitForkAreaVo.getWaitForkAreaEntity();
		String deptCode=waitForkAreaVo.getWaitForkAreaEntity().getTransferCode();//TODO 此处是根据外场来获取库区，需要根据deptCode来获取外场Code
		List<GoodsAreaEntity> goodsAreaEntity = goodsAreaService.querySimpleGoodsAreaListByOrganizationCode(deptCode);
		List<GoodsAreaEntity> goodsAreaEntitys = new ArrayList<GoodsAreaEntity>();
		//给库区加上显示的名称（供待叉区显示使用）
		if(CollectionUtils.isNotEmpty(goodsAreaEntity)){
			for(GoodsAreaEntity entity : goodsAreaEntity){
				if(StringUtils.isNotBlank(entity.getGoodsAreaCode())&&StringUtils.isNotBlank(entity.getGoodsAreaName())){
					entity.setNameAndCode(entity.getGoodsAreaName()+entity.getGoodsAreaCode());
				}
				goodsAreaEntitys.add(entity);
			}
		}else{
			goodsAreaEntitys =goodsAreaEntity ;
		}
		
		
		if(CollectionUtils.isEmpty(goodsAreaEntity)){
			return returnError("该部门没有查到所属库区！");
		}else{
			waitForkAreaVo.setGoodsAreaEntityList(goodsAreaEntitys);
			return returnSuccess();
		}
		
	}
	/**
	 * @author 李鹏飞
	 * @Description 查询当前外场所有月台
	 * @Time 2014-4-25
	 * @return
	 */
	@JSON
	public String queryPlatform(){
		String transferCode=waitForkAreaVo.getWaitForkAreaEntity().getTransferCode();
		waitForkAreaVo.setPlatformEntityList(platformService.queryPlatformListByOrganizationCode(transferCode));
		return returnSuccess();
	}
	/**
	 * @author 李鹏飞
	 * @Description 根据外场编码、待叉区编码查询待叉区
	 * @Time 2014-4-28
	 * @return
	 */
	@JSON
	public String queryWaitForkAreaByCode(){
		String waitForkAreaCode=waitForkAreaVo.getWaitForkAreaEntity().getWaitForkAreaCode();
		String virtuaCode=waitForkAreaVo.getWaitForkAreaEntity().getVirtualCode(); 
		waitForkAreaVo.setWaitForkAreaEntity(waitForkAreaService.queryWaitForkAreaByCode(waitForkAreaCode,virtuaCode));
		return returnSuccess();
	}

}
