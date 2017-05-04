package com.deppon.foss.module.login.server.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.define.FunctionType;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IResourceService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserMenuService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TreeNode;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserMenuEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ResourceException;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.login.shared.define.MessageType;
import com.deppon.foss.module.login.shared.exception.LoginException;
import com.deppon.foss.module.login.shared.vo.ResourceTreeNode;
import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 
 ******************************************* 
 * <b style="font-family:微软雅黑"><small>Description:功能菜单WEB接入层</small></b> </br>
 * <b style="font-family:微软雅黑"><small>HISTORY</small></b></br> <b
 * style="font-family:微软雅黑"><small> ID DATE PERSON REASON</small></b><br>
 ******************************************** 
 * <div style="font-family:微软雅黑,font-size:70%"> 1 2012-08-30 钟庭杰 新增 </div>
 ******************************************** 
 */
public class MenuAction extends AbstractAction {

	private static final long serialVersionUID = 1851855354424501485L;

	private IResourceService resourceService;

	public void setResourceService(IResourceService resourceService) {
		this.resourceService = resourceService;
	}

	private IUserMenuService userMenuService;

	public void setUserMenuService(IUserMenuService userMenuService) {
		this.userMenuService = userMenuService;
	}

	// 功能信息集合
	private List<ResourceEntity> resources;

	// 父功能编码
	private String node;

	// 判断是否点击了用户常用菜单
	private boolean checkUserMenu;

	// 功能树节点集合
	@SuppressWarnings("rawtypes")
	private List<ResourceTreeNode> nodes;

	// 常用菜单树节点集合
	@SuppressWarnings("rawtypes")
	private List<TreeNode<ResourceEntity, TreeNode>> menuNodes;

	// 用户已经设置的常用菜单ID集合
	private List<String> userRes;

	// 用于菜单树展开路径设置
	private String path = "";

	// 菜单树展开路径集合
	private Set<String> pathList;

	// 转换菜单对象为树节点对象
	private ResourceTreeNode<ResourceEntity> changeResToTreeNode(
			ResourceEntity res) {
		ResourceTreeNode<ResourceEntity> treeNode = new ResourceTreeNode<ResourceEntity>();
		treeNode.setId(res.getFunctionCode());
		treeNode.setText(res.getName());
		treeNode.setExpandable(!FossConstants.YES.equalsIgnoreCase(res
				.getLeafFlag()));
		treeNode.setCls(res.getCls());
		treeNode.setIconCls(res.getIconCls());
		treeNode.setDisplayOrder(res.getDisplayOrder());
		if (res.getResType().equalsIgnoreCase(FunctionType.MENU)) {
			treeNode.setUri(res.getUri());
			treeNode.setLeaf(true);
		} else {
			treeNode.setLeaf(false);
		}
		if (res.getParentRes() != null) {
			treeNode.setParentId(res.getParentRes().getFunctionCode());
		} else {
			treeNode.setParentId(null);
		}
		// treeNode.setEntity(res);
		return treeNode;
	}

	// 查询功能方法
	private List<ResourceEntity> findResources(String parentCode) {
		// 获得当前用户
		UserEntity user = FossUserContext.getCurrentUser();
		// 当前用户当前部门功能编码集合
		Set<String> resCodes = user.getOrgResCodes();
		// 当前部门未配置角色
		if (resCodes == null) {
			throw new LoginException(LoginException.CURRENT_DEPT_NO_ROLE);
		}
		// 菜单对象集合
		List<ResourceEntity> resNodes = new ArrayList<ResourceEntity>();
		List<ResourceEntity> childResources = resourceService
				.queryResourcesByParentCode(parentCode);
		for (ResourceEntity res : childResources) {
			// 判断权限是否为空
			if (res == null) {
				continue;
			}
			// 判断权限的类型是否为按钮
			if (StringUtil.equalsIgnoreCase(FunctionType.BUTTON,
					res.getResType())) {
				continue;
			}
			// 判断是否是WEB菜单
			if (StringUtil
					.equalsIgnoreCase(
							DictionaryValueConstants.BSE_RESOURCE_BELONG_SYSTEM_TYPE_GUI,
							res.getBelongSystemType())) {
				continue;
			}
			// 判断权限为非检查的权限时，直接增加到权限列表中
			if (StringUtil.equalsIgnoreCase(FossConstants.NO, res.getChecked())) {
				resNodes.add(res);
				continue;
			}
			if (resCodes.contains(res.getCode())) {
				resNodes.add(res);
			}
		}
		return resNodes;
	}

	// 用户已经设置的常用菜单与资源列表
	private Map<String, UserMenuEntity> createUserMenus() {
		// 获得当前用户
		UserEntity user = FossUserContext.getCurrentUser();
		// 获得当前用户当前所在部门的所有权限信息集合
		Set<String> userAccessResCodes = user.getOrgResCodes();
		// 用户菜单CODE与菜单MAP
		Map<String, UserMenuEntity> userMenusMap = new HashMap<String, UserMenuEntity>();
		// 用户已经设置的常用菜单对象
		List<UserMenuEntity> userMenus = userMenuService
				.queryUserMenuByUserCode(user.getEmployee().getEmpCode());
		String[] resCodes = new String[userMenus.size()];
		for (int i = 0; i < userMenus.size(); i++) {
			if(userAccessResCodes.contains(userMenus.get(i).getResourceCode())){
				resCodes[i] = userMenus.get(i).getResourceCode();
				userMenusMap.put(resCodes[i], userMenus.get(i));				
			}
		}
		return userMenusMap;
	}

	// 得到常用菜单的功能方法
	@SuppressWarnings("rawtypes")
	private List<ResourceTreeNode> findUserMenu() {
		Map<String, UserMenuEntity> userMenusMap = this.createUserMenus();
		List<ResourceTreeNode> userMenusTreeNode = new ArrayList<ResourceTreeNode>();
		// 通过用户常用菜单中的权限集合，得到用户常用菜单对象
		List<ResourceEntity> resList = resourceService
				.queryResourceBatchByCode(userMenusMap.keySet().toArray(
						new String[userMenusMap.size()]));
		if (resList != null && resList.size() > 0) {
			for (ResourceEntity res : resList) {
				UserMenuEntity userMenu = userMenusMap.get(res.getCode());
				res.setDisplayOrder(userMenu.getDisplayOrder().toString());
				ResourceTreeNode<ResourceEntity> treeNode = changeResToTreeNode(res);
				treeNode.setId(res.getCode() + "_usermenu");
				String cls = treeNode.getCls();
				treeNode.setCls(cls.substring(0, cls.length() - 1) + "3");
				userMenusTreeNode.add(treeNode);
			}
			// 对常用菜单进行排序
			Collections.sort(userMenusTreeNode,
					new Comparator<ResourceTreeNode>() {

						public int compare(ResourceTreeNode o1,
								ResourceTreeNode o2) {
							Integer r1 = Integer.parseInt(o1.getDisplayOrder());
							Integer r2 = Integer.parseInt(o2.getDisplayOrder());
							return r1.compareTo(r2);
						}
					});
		}
		return userMenusTreeNode;
	}

	/**
	 * 加载菜单树 loadTree
	 * 
	 * @return
	 * @return String
	 * @since:
	 */
	@SuppressWarnings("rawtypes")
	@JSON
	public String loadTree() {
		nodes = new ArrayList<ResourceTreeNode>();
		try {
			// 得到用户常用菜单
			if (checkUserMenu) {
				// 得到用户常用菜单节点列表
				try {
					nodes = findUserMenu();
				} catch (ResourceException e) {
					return returnSuccess(e.getErrorCode());
				}
				return returnSuccess();
			}
			// 得到点击的节点下的子节点菜单集合
			List<ResourceEntity> resources = findResources(node);
			for (ResourceEntity res : resources) {
				// 转换菜单对象为节点对象
				ResourceTreeNode<ResourceEntity> treeNode = changeResToTreeNode(res);
				// 判断节点是否叶子节点，如果为叶子节点，在判断节点是否有子节点，如果有子节点，那么就要把子节点加到该节点下，传到前台
				/*
				 * if (FossConstants.YES.equalsIgnoreCase(res.getLeafFlag())) {
				 * List<ResourceEntity> childResources = findResources(res
				 * .getCode()); List<ResourceTreeNode> childNodes = new
				 * ArrayList<ResourceTreeNode>(); for (ResourceEntity childRes :
				 * childResources) { ResourceTreeNode<ResourceEntity>
				 * childTreeNode = changeResToTreeNode(childRes);
				 * childNodes.add(childTreeNode); }
				 * treeNode.setChildren(childNodes); }
				 */
				nodes.add(treeNode);
			}
			return returnSuccess();
		} catch (BusinessException e) {
			return returnSuccess(e.getErrorCode());
		}
	}

	// 转换常用菜单对象为树节点对象
	@SuppressWarnings("rawtypes")
	private TreeNode<ResourceEntity, TreeNode> menuToTreeNode(ResourceEntity res) {
		TreeNode<ResourceEntity, TreeNode> treeNode = new TreeNode<ResourceEntity, TreeNode>();
		if (res == null) {
			return treeNode;
		}

		treeNode.setId(res.getFunctionCode());
		treeNode.setText(res.getName());

		// 087584-foss-lijun 解决此处空指针异常的问题
		treeNode.setLeaf(StringUtils.equals(res.getLeafFlag(),
				FossConstants.YES));

		if (res.getParentRes() != null) {
			treeNode.setParentId(res.getParentRes().getFunctionCode());
		} else {
			treeNode.setParentId(null);
		}
		/*
		 * 如果节点是菜单功能，那么就把该节点变成叶子节点
		 */
		if (FunctionType.MENU.equalsIgnoreCase(res.getResType())) {
			treeNode.setLeaf(true);
		}

		treeNode.setEntity(res);
		return treeNode;
	}

	/**
	 * 功能菜单树的节点查询与展开路径加载
	 * 
	 * @return
	 * @return String
	 * @since:
	 */
	@JSON
	public String queryTreePathForName() {
		try {
			ResourceEntity queryEntity = new ResourceEntity();
			queryEntity.setName(node);
			queryEntity
					.setBelongSystemType(DictionaryValueConstants.BSE_RESOURCE_BELONG_SYSTEM_TYPE_WEB);
			List<ResourceEntity> resList = resourceService
					.queryResourceByEntity(queryEntity);
			Set<String> resCodes = FossUserContext.getCurrentUser().getOrgResCodes();
			pathList = new HashSet<String>();
			for (ResourceEntity res : resList) {
				if (FunctionType.BUTTON.equalsIgnoreCase(res.getResType())) {
					continue;

				}
				if (CollectionUtils.isNotEmpty(resCodes)) {
					if (resCodes.contains(res.getCode())) {
						node = res.getCode();
						this.expendTreePath();
						pathList.add(new String(path));
						path = "";
					}
				}
			}
			return returnSuccess();
		} catch (ResourceException e) {
			return returnError(e);
		}
	}

	/**
	 * 常用菜单设置中菜单树的加载 loadMenuTree
	 * 
	 * @return
	 * @return String
	 * @since:
	 */
	@JSON
	@SuppressWarnings("rawtypes")
	public String loadMenuTree() {
		try {
			menuNodes = new ArrayList<TreeNode<ResourceEntity, TreeNode>>();
			List<ResourceEntity> ress = findResources(node);
			// 通过用户常用菜单中的权限集合，得到用户常用菜单对象
			Map<String, UserMenuEntity> userMenusMap = this.createUserMenus();
			for (ResourceEntity res : ress) {
				// 判断菜单是否已经被设置到常用菜单中
				if (userMenusMap != null && userMenusMap.size() > 0) {
					if (userMenusMap.containsKey(res.getCode())) {
						continue;
					}
				}
				// 转换菜单对象为节点对象
				TreeNode<ResourceEntity, TreeNode> treeNode = menuToTreeNode(res);
				menuNodes.add(treeNode);
			}
			// 第一次请求删除用户常用菜单与最后一个空白节点
			ResourceEntity rootRes = resourceService
					.queryResourceRoot(DictionaryValueConstants.BSE_RESOURCE_BELONG_SYSTEM_TYPE_WEB);
			if (rootRes.getCode().equals(node) && menuNodes.size() >= 2) {
				// 用户常用菜单节点
				menuNodes.remove(menuNodes.size() - 1);
				// 空白节点
				menuNodes.remove(0);
			}
			return returnSuccess();
		} catch (BusinessException e) {
			return returnSuccess(e.getErrorCode());
		}
	}

	/**
	 * 常用菜单设置中菜单树的加载 currentUserMeun
	 * 
	 * @return
	 * @return String
	 * @since:
	 */
	@JSON
	public String currentUserMeun() {
		final Map<String, UserMenuEntity> userMenusMap = this.createUserMenus();
		// 得到用户设置的常用菜单
		try {
			resources = resourceService.queryResourceBatchByCode(userMenusMap
					.keySet().toArray(new String[userMenusMap.size()]));
		} catch (ResourceException e) {
			return returnSuccess(e.getErrorCode());
		}
		if (resources == null || resources.size() <= 0) {
			return returnSuccess();
		}
		// 对系统功能进行排序
		Collections.sort(resources, new Comparator<ResourceEntity>() {

			public int compare(ResourceEntity o1, ResourceEntity o2) {
				UserMenuEntity menu1 = userMenusMap.get(o1.getCode());
				UserMenuEntity menu2 = userMenusMap.get(o2.getCode());
				return menu1.getDisplayOrder().compareTo(
						menu2.getDisplayOrder());
			}
		});
		return returnSuccess();
	}

	/**
	 * 保存用户常用菜单 saveUserMenus
	 * 
	 * @return
	 * @return String
	 * @since:
	 */
	@JSON
	public String saveUserMenus() {
		UserEntity user = (UserEntity) (UserContext.getCurrentUser());
		try {
			userMenuService.saveUserMenu(user.getEmployee().getEmpCode(),
					this.userRes);
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 常用菜单树的展开路径加载
	 * 
	 * @return
	 * @return String
	 * @since:
	 */
	@JSON
	public String expendTreePath() {
		try {
			ResourceEntity rootRes = resourceService
					.queryResourceRoot(DictionaryValueConstants.BSE_RESOURCE_BELONG_SYSTEM_TYPE_WEB);
			ResourceEntity parentRes = resourceService
					.queryResourceByCode(node);
			String parentNode = node;
			while (!rootRes.getCode().equals(parentNode)) {
				path = "/" + parentNode + path;
				parentRes = resourceService.queryResourceByCode(parentNode)
						.getParentRes();
				if (parentRes == null) {
					path = "";
					break;
				}
				parentNode = parentRes.getCode();
			}
			path = "/" + rootRes.getCode() + path;
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (ResourceException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 获取快递请求的IP
	 * @author 310854
	 * @date 2016-7-5
	 */
	@JSON
	public String toDoItemIp(){
		
		try {
			toDoItemIp = PropertiesUtil.getKeyValue("foss.login.app.toDoItem.ip");
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (ResourceException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 快递开关
	 * @author 310854
	 * @date 2016-7-25
	 */
	@JSON
	public String queryBseSwitch4Ecs(){
		try {
			if("Y".equals(resourceService.queryIsECSByWayBillNo(waybillNo))){
				ecsSwitch = "Y";
			}
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (ResourceException e) {
			return returnError(e);
		}
	}
	
	public void setNode(String node) {
		this.node = node;
	}

	@SuppressWarnings("rawtypes")
	public List<ResourceTreeNode> getNodes() {
		return nodes;
	}

	public List<ResourceEntity> getResources() {
		return resources;
	}

	@SuppressWarnings("rawtypes")
	public List<TreeNode<ResourceEntity, TreeNode>> getMenuNodes() {
		return menuNodes;
	}

	public void setUserRes(List<String> userRes) {
		this.userRes = userRes;
	}

	public void setCheckUserMenu(boolean checkUserMenu) {
		this.checkUserMenu = checkUserMenu;
	}

	public String getPath() {
		return path;
	}

	public Set<String> getPathList() {
		return pathList;
	}

	public String getToDoItemIp() {
		return toDoItemIp;
	}

	public void setToDoItemIp(String toDoItemIp) {
		this.toDoItemIp = toDoItemIp;
	}

	private String toDoItemIp;
	
	/**
     * 系统配置参数 Service接口
     */
    private IConfigurationParamsService configurationParamsService;
    
    /**
     * @param configurationParamsService the configurationParamsService to set
     */
    public void setConfigurationParamsService(
    	IConfigurationParamsService configurationParamsService) {
        this.configurationParamsService = configurationParamsService;
    }
    
    private String ecsSwitch = "N";
    
    private String waybillNo;

	public void setEcsSwitch(String ecsSwitch) {
		this.ecsSwitch = ecsSwitch;
	}

	public String getEcsSwitch() {
		return ecsSwitch;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
}
