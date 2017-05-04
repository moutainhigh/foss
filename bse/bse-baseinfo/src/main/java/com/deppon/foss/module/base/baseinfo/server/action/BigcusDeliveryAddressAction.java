package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBigcusDeliveryAddressService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigcusDeliveryAddressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TreeNode;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.BigcusDeliveryAddressVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.CollectionUtils;

/**
 * 零担大客户派送地址库 Action
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:232607,date:2016-3-14 下午2:45:09,content:TODO </p>
 * @author 232607 
 * @date 2016-3-14 下午2:45:09
 * @since
 * @version
 */
public class BigcusDeliveryAddressAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	/**
	 * 日志监控
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BigcusDeliveryAddressAction.class);
	/**
	 * 声明定义
	 */
	private IBigcusDeliveryAddressService bigcusDeliveryAddressService;

	private BigcusDeliveryAddressVo vo;

	@SuppressWarnings("rawtypes")
	private List<TreeNode> nodes;

	public BigcusDeliveryAddressVo getVo() {
		return vo;
	}

	public void setVo(BigcusDeliveryAddressVo vo) {
		this.vo = vo;
	}

	@SuppressWarnings("rawtypes")
	public List<TreeNode> getNodes() {
		return nodes;
	}

	public void setBigcusDeliveryAddressService(
			IBigcusDeliveryAddressService bigcusDeliveryAddressService) {
		this.bigcusDeliveryAddressService = bigcusDeliveryAddressService;
	}

	/**
	 * <p>根据上级编码查询下级区域，用于界面中tree的展开事件</p> 
	 * @author 232607 
	 * @date 2016-3-14 下午5:00:18
	 * @return
	 * @see
	 */
	@SuppressWarnings("rawtypes")
	@JSON
	public String queryBigcusDeliveryAddressByParentDistrictCode() {
		try {
			// 若父级编码为空
			if (null == vo
					|| null == vo.getBigcusDeliveryAddressEntity()
					|| StringUtils.isBlank(vo.getBigcusDeliveryAddressEntity()
							.getParentDistrictCode())) {
				return returnSuccess();
			}
			String parentCode = vo.getBigcusDeliveryAddressEntity()
					.getParentDistrictCode();
			// 定义树集合对象
			nodes = new ArrayList<TreeNode>();
			// 根据父级编码查询节点
			List<BigcusDeliveryAddressEntity> list = bigcusDeliveryAddressService
					.queryByParentDistrictCode(parentCode);
			if (CollectionUtils.isNotEmpty(list)) {
				// 循环行政区域信息
				for (BigcusDeliveryAddressEntity pojo : list) {
					// 定义树节点对象
					TreeNode<BigcusDeliveryAddressEntity, TreeNode> treeNode = new TreeNode<BigcusDeliveryAddressEntity, TreeNode>();
					// 设置节点信息
					treeNode.setId(pojo.getCode());
					treeNode.setText(pojo.getName());
					treeNode.setLeaf(pojo.getIsLeaf().equals("Y")?true:false);
					treeNode.setParentId(parentCode);
					treeNode.setEntity(pojo);
					// 讲节点信息添加到树种
					nodes.add(treeNode);
				}
			}
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
	/**
	 * <p>根据编码查询区域信息，用于界面中tree的单击事件</p>
	 * @author 232607 
	 * @date 2016-3-14 下午4:59:24
	 * @return
	 * @see
	 */
	@JSON
	public String queryBigcusDeliveryAddressByCode() {
		try {
			String code = vo.getBigcusDeliveryAddressEntity().getCode();
			//查询实体
			BigcusDeliveryAddressEntity entity = bigcusDeliveryAddressService.queryBigcusDeliveryAddressByCode(code);
			// 设置给前台
			vo.setBigcusDeliveryAddressEntity(entity);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
	/**
	 * <p>分页查询</p> 
	 * @author 232607 
	 * @date 2016-3-14 下午4:59:09
	 * @return
	 * @see
	 */
	@JSON
	public String queryBigcusDeliveryAddressList() {
		try {
			//分页查询
			List<BigcusDeliveryAddressEntity> list = bigcusDeliveryAddressService
					.queryBigcusDeliveryAddressEntities(vo.getBigcusDeliveryAddressEntity()
							, this.start,this.limit);
			vo.setBigcusDeliveryAddressList(list);
			//查询count 
			totalCount =bigcusDeliveryAddressService.queryCount(vo.getBigcusDeliveryAddressEntity());
			this.setTotalCount(totalCount);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(),e);
			return returnError(e.getMessage());
		}
	}
	/**
	 * <p>修改区域</p> 
	 * @author 232607 
	 * @date 2016-3-14 下午4:58:22
	 * @return
	 * @see
	 */
	@JSON
	public String updateBigcusDeliveryAddress(){
		try {
			BigcusDeliveryAddressEntity entity =vo.getBigcusDeliveryAddressEntity();
			//获取当前登陆人
			CurrentInfo info =FossUserContext.getCurrentInfo();
			entity.setModifyUser(info.getEmpCode());
			//修改区域操作
			bigcusDeliveryAddressService.updateBigcusDeliveryAddress(entity);
			return returnSuccess(SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(),e);
			return returnError(e.getMessage());
		}
	}
}
