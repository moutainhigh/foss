package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILateCouponService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LateCouponEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TreeNode;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.AdministrativeRegionsVo;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.LateCouponVo;
import com.deppon.foss.module.base.baseinfo.server.service.impl.AdministrativeRegionsService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.RegionVo;

/**
 * 晚到补差价
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:232607,date:2015-7-28 下午7:28:28,content:TODO
 * </p>
 * 
 * @author 232607
 * @date 2015-7-28 下午7:28:28
 * @since
 * @version
 */
@SuppressWarnings("unused")
public class LateCouponAction extends AbstractAction {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 2773644272419312426L;
	/**
	 * 日志处理
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(LateCouponAction.class);
	/**
	 * 降价发券服务
	 */
	private ILateCouponService lateCouponService;
	/**
	 * 降价发券方案VO
	 */
	private LateCouponVo lateCouponVo = new LateCouponVo();
	/**
	 * 导入文件
	 */
	private String uploadFile;
	/**
	 * 导入文件名
	 */
	private String uploadFileFileName;
	/**
	 * 区域管理服务
	 */
	// private IRegionService regionService;
	/**
	 * 区域Vo页面传参对象
	 */
	private RegionVo regionVo = new RegionVo();

	/**
	 * 注入降价发券服务
	 */
	public void setLateCouponService(ILateCouponService lateCouponService) {
		this.lateCouponService = lateCouponService;
	}

	/**
	 * 注入区域管理service
	 */
	/*
	 * public void setRegionService(IRegionService regionService) {
	 * this.regionService = regionService; }
	 */
	/**
	 * lateCouponVo的get、set方法
	 */
	public LateCouponVo getLateCouponVo() {
		return lateCouponVo;
	}

	public void setLateCouponVo(LateCouponVo lateCouponVo) {
		this.lateCouponVo = lateCouponVo;
	}

	/**
	 * 获得区域Vo页面传参对象
	 */
	public RegionVo getRegionVo() {
		return regionVo;
	}

	/**
	 * 设置区域Vo页面传参对象
	 */
	public void setRegionVo(RegionVo regionVo) {
		this.regionVo = regionVo;
	}

	/**
	 * @return the uploadFile
	 */
	public String getUploadFile() {
		return uploadFile;
	}

	/**
	 * @param uploadFile
	 *            the uploadFile to set
	 */
	public void setUploadFile(String uploadFile) {
		this.uploadFile = uploadFile;
	}

	/**
	 * @return the uploadFileFileName
	 */
	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	/**
	 * @param uploadFileFileName
	 *            the uploadFileFileName to set
	 */
	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	// 用于注入行政区域业务服务实现类
	private IAdministrativeRegionsService administrativeRegionsService;

	private AdministrativeRegionsVo administrativeRegionsVo;

	@SuppressWarnings({ "rawtypes" })
	private List<TreeNode> nodes;

	/*
	 * =================================================================
	 * 下面是get,set方法：
	 */

	@SuppressWarnings("rawtypes")
	public List<TreeNode> getNodes() {
		return nodes;
	}

	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	public AdministrativeRegionsVo getAdministrativeRegionsVo() {
		return administrativeRegionsVo;
	}

	public void setAdministrativeRegionsVo(
			AdministrativeRegionsVo administrativeRegionsVo) {
		this.administrativeRegionsVo = administrativeRegionsVo;
	}

	/**
	 * 分页查询晚到补差价方案
	 * 
	 * @author 232607
	 * @date 2015-7-28 下午7:47:04
	 * @return
	 * @see
	 */
	@JSON
	public String queryLateCouponByCodition() {
		try {
			List<LateCouponEntity> lateCouponEntitys = lateCouponService
					.queryLateCouponByCodition(
							lateCouponVo.getLateCouponEntity(), start, limit);
			lateCouponVo.setLateCouponEntitys(lateCouponEntitys);
			this.setTotalCount(lateCouponService
					.countLateCouponByCodition(lateCouponVo
							.getLateCouponEntity()));

			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error("根据条件查询降价发券方案明细: " + e.getMessage());
			return returnError(e.getMessage());
		}
	}

	/**
	 * 新增晚到补差价方案
	 * 
	 * @author 232607
	 * @date 2015-7-28 下午7:49:31
	 * @return
	 * @see
	 */
	@JSON
	public String addLateCoupon() {
		try {
			lateCouponService.addLateCoupon(lateCouponVo.getLateCouponEntity());
			this.nodes=null;
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("新增降价发券方案: " + e.getMessage());
			return returnError(e.getMessage());
		}
	}

	/**
	 * 作废晚到补差价方案
	 * 
	 * @author 232607
	 * @date 2015-7-28 下午7:50:25
	 * @return
	 * @see
	 */
	@JSON
	public String deleteLateCoupon() {
		try {
			lateCouponService.deleteLateCoupon(lateCouponVo.getLateCouponIds());
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("作废降价发券方案: " + e.getMessage());
			return returnError(e.getMessage());
		}
	}

	/**
	 * 修改晚到补差价方案
	 * 
	 * @author 232607
	 * @date 2015-7-28 下午7:56:00
	 * @return
	 * @see
	 */
	@JSON
	public String updateLateCoupon() {
		try {
			lateCouponService.updateLateCoupon(lateCouponVo
					.getLateCouponEntity());
			this.nodes=null;
			return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("修改降价发券方案: " + e.getMessage());
			return returnError(e.getMessage());
		}
	}

	/**
	 * 升级版本（复制晚到补差价方案）
	 * 
	 * @author 232607
	 * @date 2015-7-28 下午7:54:42
	 * @return
	 * @see
	 */
	@JSON
	public String copyLateCoupon() {
		try {
			lateCouponService.copyLateCoupon(lateCouponVo.getLateCouponEntity()
					.getId());
			return returnSuccess(MessageType.COPY_PRICECOUPON_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("升级版本: " + e.getMessage());
			return returnError(e.getMessage());
		}
	}

	/**
	 * 激活晚到补差价方案
	 * 
	 * @author 232607
	 * @date 2015-7-28 下午7:54:14
	 * @return
	 * @see
	 */
	@JSON
	public String activateLateCoupon() {
		try {
			lateCouponService.activateLateCoupon(lateCouponVo
					.getLateCouponEntity().getId());
			return returnSuccess(MessageType.ACTIVE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("立即修改降价发券方案状态: " + e.getMessage());
			return returnError(e.getMessage());
		}
	}

	/**
	 * 中止晚到补差价方案
	 * 
	 * @author 232607
	 * @date 2015-7-28 下午7:54:04
	 * @return
	 * @see
	 */
	@JSON
	public String stopLateCoupon() {
		try {
			lateCouponService.stopLateCoupon(lateCouponVo.getLateCouponEntity()
					.getId());
			return returnSuccess(MessageType.STOP_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.error("立即中止降价发券方案状态: " + e.getMessage());
			return returnError(e.getMessage());
		}
	}

	/**
	 * 晚到补差价查询行政区域
	 * 
	 * @author 273296
	 * @date 2016-12-21 下午7:54:04
	 * @return
	 * @see
	 */	
	@SuppressWarnings("rawtypes")
	@JSON
	public String queryStartDisctrict() {
		LateCouponEntity entity = null;
		String[] startCodes = null;
		if (lateCouponVo.getLateCouponEntity()!=null&&lateCouponVo.getLateCouponEntity().getId() != null) {
			entity = lateCouponService.findById(lateCouponVo.getLateCouponEntity().getId());
		}
		if (entity != null && entity.getStartBigzone() != null) {
			startCodes = entity.getStartBigzone().split(",");
		}

		String parentCode = administrativeRegionsVo
				.getAdministrativeRegionsDetail().getParentDistrictCode();
		if (StringUtils.isEmpty(parentCode) || parentCode.equals("0")) {
			parentCode = "100000";
		}

		// 根据行政区域编码查询行政区域集合信息
		List<AdministrativeRegionsEntity> administrativeRegionsList = administrativeRegionsService
				.queryAdministrativeRegionsByParentDistrictCode(parentCode);
		nodes = new ArrayList<TreeNode>();
		// 循环获取行政区域信息
		for (AdministrativeRegionsEntity pojo : administrativeRegionsList) {
			TreeNode<AdministrativeRegionsEntity, TreeNode> treeNode = new TreeNode<AdministrativeRegionsEntity, TreeNode>();
			treeNode.setId(pojo.getCode());
			treeNode.setText(pojo.getName());
			// 如果行政区域级别是TOWN_STREET_AGENCY（乡镇/街道），则为叶子结点--187862-dujunhui
			treeNode.setLeaf(StringUtils.equals(
					DictionaryValueConstants.DISTRICT_CITY, pojo.getDegree()));
			treeNode.setChecked(false);
			if(startCodes!=null){
				for(int i=0;i<startCodes.length;i++){
					if (startCodes[i].equals(pojo.getCode())) {
						treeNode.setChecked(true);
						break;
				}
			}
			}

			// 如果上级行政区域编码不为空
			if (pojo.getParentDistrictCode() != null
					&& !"".equals(pojo.getParentDistrictCode())) {
				treeNode.setParentId(pojo.getParentDistrictCode());
			} else {
				treeNode.setParentId("100000");
			}
			// 将获取的行政区域信息添加到节点中
			treeNode.setEntity(pojo);
			nodes.add(treeNode);
		}
		return returnSuccess();
	}

	/**
	 * 晚到补差价查询行政区域
	 * 
	 * @author 273296
	 * @date 2016-12-21 下午7:54:04
	 * @return
	 * @see
	 */
	@SuppressWarnings("rawtypes")
	@JSON
	public String queryArriveDisctrict() {
		LateCouponEntity entity = new LateCouponEntity();
		String[] arriveCodes = null;
		if (lateCouponVo.getLateCouponEntity()!=null&&lateCouponVo.getLateCouponEntity().getId() != null) {
			entity = lateCouponService.findById(lateCouponVo.getLateCouponEntity().getId());
		}
		if (entity != null && entity.getArriveBigzone() != null) {
			arriveCodes = entity.getArriveBigzone().split(",");
		}
		String parentCode = administrativeRegionsVo
				.getAdministrativeRegionsDetail().getParentDistrictCode();
		if (StringUtils.isEmpty(parentCode) || parentCode.equals("0")) {
			parentCode = "100000";
		}

		// 根据行政区域编码查询行政区域集合信息
		List<AdministrativeRegionsEntity> administrativeRegionsList = administrativeRegionsService
				.queryAdministrativeRegionsByParentDistrictCode(parentCode);
		nodes = new ArrayList<TreeNode>();
		// 循环获取行政区域信息
		for (AdministrativeRegionsEntity pojo : administrativeRegionsList) {
			TreeNode<AdministrativeRegionsEntity, TreeNode> treeNode = new TreeNode<AdministrativeRegionsEntity, TreeNode>();
			treeNode.setId(pojo.getCode());
			treeNode.setText(pojo.getName());
			// 如果行政区域级别是TOWN_STREET_AGENCY（乡镇/街道），则为叶子结点--187862-dujunhui
			treeNode.setLeaf(StringUtils.equals(
					DictionaryValueConstants.DISTRICT_CITY, pojo.getDegree()));
			treeNode.setChecked(false);
			if(arriveCodes!=null){
				for(int i=0;i<arriveCodes.length;i++){
					if (arriveCodes[i].equals(pojo.getCode())) {
						treeNode.setChecked(true);
						break;
					}
				}
			}
			
			

			// 如果上级行政区域编码不为空
			if (pojo.getParentDistrictCode() != null
					&& !"".equals(pojo.getParentDistrictCode())) {
				treeNode.setParentId(pojo.getParentDistrictCode());
			} else {
				treeNode.setParentId("100000");
			}
			// 将获取的行政区域信息添加到节点中
			treeNode.setEntity(pojo);
			nodes.add(treeNode);
		}
		return returnSuccess();
	}

}