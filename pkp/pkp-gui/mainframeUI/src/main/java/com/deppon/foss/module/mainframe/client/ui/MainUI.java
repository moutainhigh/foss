/**
 *  initial comments.
 */
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.mainframe.client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java.plugin.Plugin;
import org.java.plugin.PluginLifecycleException;
import org.java.plugin.registry.Extension;
import org.java.plugin.registry.Extension.Parameter;
import org.java.plugin.registry.ExtensionPoint;
import org.jdesktop.swingx.JXTree;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.I18nTemplate;
import com.deppon.foss.framework.client.commons.util.ImageUtil;
import com.deppon.foss.framework.client.core.jpf.utils.JpfUtils;
import com.deppon.foss.framework.client.core.workbench.IInitializer;
import com.deppon.foss.framework.client.core.workbench.IPluginAware;
import com.deppon.foss.framework.client.core.workbench.IWorkbench;
import com.deppon.foss.framework.client.core.workbench.IWorkbenchAware;
import com.deppon.foss.module.mainframe.client.MenuNodeInfo;
import com.deppon.foss.module.mainframe.client.action.AbstractOpenUIAction;
import com.deppon.foss.module.mainframe.client.action.IMainFrame;
import com.deppon.foss.module.mainframe.client.service.MenuServiceFactory;
import com.deppon.foss.module.mainframe.client.ui.JOutlookBar.BarInfo;
import com.deppon.foss.module.mainframe.client.utills.MenuNodeUtil;



/**
 * 
 * <p style="display:none">主框架UI</p>
 * @author dp-yangtong
 * @date 2012-10-12 上午11:48:34
 * @since
 * @version
 */
public class MainUI extends JPanel implements IWorkbenchAware, IPluginAware, IInitializer, ActionListener, MouseListener {

	/**
	 * 14 font size
	 */
	private static final int FONTSIZE14 = 14;
	/**
	 * 194 color z
	 */
	private static final int COLORZ = 194;
	/**
	 * 190 color y
	 */
	private static final int COLORY = 190;
	/**
	 * color x
	 */
	private static final int COLORX = 185;
	/**
	 * 20
	 */
	private static final int TWENTY = 20;
	/**
	 * 10 
	 */
	private static final int TEN = 10;
	/**
	 * 0.7 FOR LOCATION
	 */
	private static final double LOCATION07 = 0.7;
	/**
	 * 3 FOR SIZE
	 */
//	private static final int SIZE3 = 3;
	/**
	 * 
	 * 序列
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	private static final long serialVersionUID = 2008133579761322340L;
	/**
	 * log
	 */
	private static final Log LOG = LogFactory.getLog(MainUI.class);
	
	/**
	 * plugin
	 */
	private Plugin plugin;
	
	/**
	 * 工作台对象
	 */
	private IWorkbench workbench;
	
	/**
	 * 通知列表
	 */
	private JPanel leftPanel = null;

	/**
	 * 构造方法
	 */
	public MainUI() {
	}
	/**
	 * 
	 * 加载主框架，设置布局
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	@Override
	public void init() {
	    	/**
	    	 * 遍历菜单树
	    	 */
		rootTree();
		/**
	    	 * 设置布局
	    	 */
		setLayout(new BorderLayout());
		/**
	    	 * 设置背景
	    	 */
		setBackground(Color.white);// 设置背景颜色
		/**
	    	 * 创建JSplitPane对象
	    	 */
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				false);
		//enabled
		/**
	    	 * 设置是否启用
	    	 */
		splitPane.setEnabled(false);
		//size
		/**
	    	 * 设置Divider尺寸
	    	 */
		splitPane.setDividerSize(NumberConstants.NUMBER_3);
		//center
		/**
	    	 * 在当前对象添加面板
	    	 */
		add(splitPane, BorderLayout.CENTER);
		//background color
		/**
	    	 * 设置splitPane对象的背景
	    	 */
		splitPane.setBackground(Color.white);
		//location
		/**
	    	 * 设置splitPane对象的Divider路径
	    	 */
		splitPane.setDividerLocation(LOCATION07);
		//left
		/**
	    	 * 设置splitPane对象的Divider路径
	    	 */
		splitPane.setTopComponent(leftPanel);
		//right
		/**
	    	 * 设置splitPane对象的右侧组件
	    	 */
		splitPane.setRightComponent(getInfoPanel());
		/**
	    	 * 设置splitPane对象的边框为空
	    	 */
		splitPane.setBorder(null);
		
		//size
		/**
	    	 * 设置splitPane对象的头部组件大小
	    	 */
		splitPane.getTopComponent().setSize(TEN, TWENTY);
		/**
	    	 * 更新splitPane对象的界面
	    	 */
		splitPane.updateUI();
	}
	/**
	 * 
	 * 遍历菜单树
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	private void rootTree() {
		//differ extesion point
		MenuNodeUtil.plugin = plugin;
		
	    	/**
	    	 * 创建ExtensionPoint对象
	    	 */
		ExtensionPoint extensionPoint = plugin.getDescriptor()
				.getExtensionPoint("menu-item");
		//extension list
		/**
	    	 * 创建ExtensionPoint类型的List集合
	    	 */
		List<Extension> extensions = new ArrayList<Extension>(
				extensionPoint.getAvailableExtensions());
		//menu info map
		/**
	    	 * 创建Key为String类型，Value为MenuNodeInfo类型的Map集合menuNodeInfos
	    	 */
		Map<String, MenuNodeInfo> menuNodeInfos = new LinkedHashMap<String, MenuNodeInfo>();
		//iterator over jpf extensions
		/**
	    	 * 循环遍历extensions集合
	    	 */
		for (Extension extension : extensions) {
		    	//获取菜单节点信息
			MenuNodeInfo menuNodeInfo = readMenuNodeInfo(extension);
			//menuinf is null object is no need to work
			/**
		    	 * 判断menuNodeInfo是否为空，如果为空，则继续循环下一条记录
		    	 */
			if (menuNodeInfo == null) {
				continue;
			}
			//menu id
			/**
		    	 * 向menuNodeInfos集合中添加元素
		    	 */
			menuNodeInfos.put(menuNodeInfo.getId(), menuNodeInfo);
			//action class nul menu is no need to work
			/**
		    	 * 判断menuNodeInfo.getActionClass()是否为空，如果为空，则循环下一条记录
		    	 */
			if (menuNodeInfo.getActionClass() == null) {
				continue;
			}
			//object o to load usually pulgin
			/**
		    	 * 判断menuNodeInfo.getAction()是否为空
		    	 */
			if(menuNodeInfo.getAction()==null){
			Object obj = null;
			try {
				// get plugin class loader
			    	/**
			    	 * 根据插件信息获取Object对象obj
			    	 */
				obj = JpfUtils.createInstance(plugin.getManager().getPlugin(extension
						.getDeclaringPluginDescriptor().getId()), menuNodeInfo.getActionClass());
			} catch (PluginLifecycleException e) {
				//exception log
				LOG.error(e.getMessage(),e);
			}
			//action need open time
			/**
		    	 * 判断obj是否是Action中的一个实例
		    	 */
			if (!(obj instanceof Action)) {
			    	/**
			    	 * 抛出异常
			    	 */
				throw new RuntimeException((obj != null && obj.getClass() != null ? obj.getClass().getName() :"")
						+ "must be a javax.swing.Action");
			}

			//action need open time
			/**
		    	 * 判断obj对象是否是AbstractOpenUIAction的一个实例
		    	 */
			if (obj instanceof AbstractOpenUIAction) {
			    	/**
			    	 * 设置打开时间
			    	 */
				((AbstractOpenUIAction<?>) obj).setOpentime(menuNodeInfo
						.getOpentime());
			}
			//main frame need open time
			/**
		    	 * 判断obj对象是否是IMainFrame的一个实例
		    	 */
			if (obj instanceof IMainFrame) {
			    	/**
			    	 * 设置打开时间
			    	 */
				((IMainFrame) obj).setOpentime(menuNodeInfo.getOpentime());
			}
			//set menu action
			/**
		    	 * 设置Action
		    	 */
			menuNodeInfo.setAction((Action) obj);
			
			
			//short cut registeration
			/**
		    	 * 判断menuNodeInfo.getShortcut()是否为空，如果不为空，则调用registerKeyboardAction()方法
		    	 */
			if (StringUtils.isNotEmpty(menuNodeInfo.getShortcut())) {
					workbench.registerKeyboardAction((Action) obj,
							menuNodeInfo.getShortcut());
			}
			}
			
			
			
			
		}
		/**
	    	 * 调用服务工厂获取Map集合menuNodeInfosWeb
	    	 */
		Map<String, MenuNodeInfo>	menuNodeInfosWeb=MenuServiceFactory.getMenuService().getMenusData(plugin);
		/**
	    	 * 判断menuNodeInfosWeb是否为空，如果不为空，则将menuNodeInfosWeb的所有元素添加至menuNodeInfos
	    	 */
		if(menuNodeInfosWeb!=null){
		menuNodeInfos.putAll(menuNodeInfosWeb);
		}
		
		//menu list
		/**
	    	 * 过滤菜单并获取
	    	 */
		List<MenuNodeInfo> rootMenus = sortMenuItems(menuNodeInfos);
		//add menu to tree
		/**
		 * 打开动作触发事件
		 */
		addTreeNodeExtension(rootMenus);
		/**
		 * 创建一个实例
		 */
		MenuNodeUtil util = MenuNodeUtil.getInstance();
		/**
		 * 重设菜单
		 */
		util.resetMenus(rootMenus);
	}
	
	
	/**
	 * 
	 * 过滤菜单
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	private List<MenuNodeInfo> sortMenuItems(Map<String, MenuNodeInfo> menuMap) {
		// create menu list
	    	/**
		 * 创建一个MenuNodeInfo类型的List集合rootMenus
		 */
		List<MenuNodeInfo> rootMenus = new ArrayList<MenuNodeInfo>();
		/**
		 *  性能 - keySet迭代是低效的，使用entrySet代替   xiaowei 
		 */
		for (Entry<String, MenuNodeInfo> entry :menuMap.entrySet() ) {
			MenuNodeInfo menu = entry.getValue();
			//parent id is not null
			/**
			 *  判断父ID是否为空
			 */
			if (StringUtils.isNotEmpty(menu.getParentId())) {
			    	//获取父节点
				MenuNodeInfo parent = menuMap.get(menu.getParentId());
				/**
				 *  判断父节点是否为空
				 */
				if (parent != null) {
					//add sub menu
				    	//向父节点中的子节点添加菜单
					parent.getChildMenus().add(menu);
				} else {
					//exception log
				    	//记录日志
					LOG.warn(String.format("Menu %s has no parent menus!",
							menu.getId()));
				}
			} else {
				//add menu
			    	//向rootMenus集合添加菜单
				rootMenus.add(menu);
			}
		}
		//sort collection
		/**
		 * 对集合rootMenus进行排序
		 */
		Collections.sort(rootMenus, menuComparator);
		/**
		 * 循环遍历rootMenus
		 */
		for (MenuNodeInfo menu : rootMenus) {
			//sort menu
		    	/**
			 * 对menu进行排序
			 */
			sortChildMenus(menu, menuComparator);
		}
		return rootMenus;
	}
	/**
	 * 
	 * 过滤子菜单按钮
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	private void sortChildMenus(MenuNodeInfo menu,
			Comparator<MenuNodeInfo> menuComparator) {
		//menu list sub list has items
	    	/**
		 * 判断参数menu的子节点菜单大小是否大于0
		 */
		if (menu.getChildMenus().size() > 0) {
			//sort items
		    	//对菜单的子节点进行排序
			Collections.sort(menu.getChildMenus(), menuComparator);
			//iterator items
			/**
			 * 循环遍历菜单子节点
			 */
			for (MenuNodeInfo item : menu.getChildMenus()) {
			    	/**
				 * 对每一项进行排序
				 */
				sortChildMenus(item, menuComparator);
			}
		}
	}
	/**
	 * 
	 * 读菜单节点，并装载
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	private MenuNodeInfo readMenuNodeInfo(Extension extension) {
		// get menu
	    	/**
		 * 创建MenuNodeInfo对象
		 */
		MenuNodeInfo menuNodeInfo = new MenuNodeInfo();
		//parse id
		//获取ID
		String id = extension.getParameter("id").valueAsString();
		//id is not allow empty
		/**
		 * 判断id是否为空
		 */
		if (StringUtils.isEmpty(id)) {
			//log erro
		    	//记录日志
			LOG.error(String
					.format("MenuNodeInfo in plugin %s ,which id is %s mask have a id ",
							 plugin.getDescriptor().getId(),extension.getId() ));

			return null;
		} else {
			//set id
		    	//设置菜单节点ID
			menuNodeInfo.setId(id);
		}
		//parent id for menu
		/**
		 * 设置父ID
		 */
		menuNodeInfo.setParentId(getParameterAsString(extension
				.getParameter("parentId")));
		//code for menu
		/**
		 * 设置菜单节点Code
		 */
		menuNodeInfo.setCode(getParameterAsString(extension
				.getParameter("code")));
		//action class for menu
		/**
		 * 设置菜单节点ActionClass
		 */
		menuNodeInfo.setActionClass(getParameterAsString(extension
				.getParameter("menuItem-class")));
		//shortcut for menu
		/**
		 * 设置菜单节点Shortcut
		 */
		menuNodeInfo.setShortcut(getParameterAsString(extension
				.getParameter("shortcut")));
		//
		/**
		 * 判断父节点是否为空
		 */
		if(menuNodeInfo.getParentId() == null){
		    	/**
			 * 设置ResLevel为"2"，BgClass为"ye1-node-lvl1"
			 */
			menuNodeInfo.setResLevel("2");
			menuNodeInfo.setBgClass("ye1-node-lvl1");
		}else{
		    	/**
			 * 设置ResLevel为"3"，BgClass为"ye1-node-lvl2"
			 */
			menuNodeInfo.setResLevel("3");
			menuNodeInfo.setBgClass("ye1-node-lvl2");
		}
		//parse text
		/**
		 * 获取参数文本
		 */
		String text = getParameterAsString(extension.getParameter("text"));

		//get international text
		/**
		 * 判断text是否为空并且是否以"#{"结尾
		 */
		if (text != null && text.startsWith("#{")) {
			//get international text
		    	/**
			 * 获取text文本信息
			 */
			text = I18nTemplate.merge(I18nManager.getI18n(plugin.getManager()
					.getPluginClassLoader(
							extension.getDeclaringPluginDescriptor())), text);
		}
		//parse index parameter
		/**
		 * 从参数中获取索引
		 */
		String index = getParameterAsString(extension.getParameter("index"));
		// index is not null, then set index
		/**
		 * 判断index是否为空
		 */
		if (index != null) {
			try {
				//set menu node index
			    	/**
				 * 设置菜单节点信息的索引
				 */
				menuNodeInfo.setIndex(Integer.parseInt(index));
			} catch (Exception ex) {
				// exception log 
			    	/**
				 * 记录日志
				 */
				LOG.error(String
						.format("menuButtonInfo in plugin %s ,which index is %s mast integer ",
								plugin.getDescriptor().getId(),
										extension.getId() ), ex);
			}
		}
		//set menu text
		/**
		 * 设置菜单节点信息的文本
		 */
		menuNodeInfo.setText(text);
		// get icon
		/**
		 * 获取图标
		 */
		String icon = getParameterAsString(extension.getParameter("icon"));
		//icon string is not empty
		/**
		 * 判断icon是否为空
		 */
		if (StringUtils.isNotEmpty(icon)) {
			//set menu icon
		    	/**
			 * 设置icon图标
			 */
			menuNodeInfo.setIcon(ImageUtil.getImageIcon(
					plugin.getManager().getPluginClassLoader(
							extension.getDeclaringPluginDescriptor()), icon));
		}

		//parse open time
		/**
		 * 获取打开时间
		 */
		String opentime = getParameterAsString(extension
				.getParameter("opentime"));
		//opentime is not empty
		/**
		 * 判断打开时间是否为空
		 */
		if (StringUtils.isNotEmpty(opentime)) {
			//set open time
		    	/**
			 * 设置菜单节点的打开时间
			 */
			menuNodeInfo.setOpentime(Integer.parseInt(opentime));

		} else {
		    	/**
			 * 设置菜单节点的打开时间
			 */
			//open time default is 1
			menuNodeInfo.setOpentime(1);

		}
		
		// parse to do message remind flag
		/**
		 * 获取字符串todomsgremindbiztype
		 */
		String todomsgremindbiztype = getParameterAsString(extension.getParameter("todomsgremindbiztype"));
		/**
		 * 设置ToDoMsgRemindBizType
		 */
		menuNodeInfo.setToDoMsgRemindBizType(todomsgremindbiztype);
		/**
		 * 判断todomsgremindbiztype是否为空
		 */
		if(todomsgremindbiztype!=null){
		    	/**
			 * 获取菜单节点实例，并调用registerToDoMsgMenuNodeMap()
			 */
			MenuNodeUtil util = MenuNodeUtil.getInstance();
			util.registerToDoMsgMenuNodeMap(todomsgremindbiztype, menuNodeInfo);
		}
		return menuNodeInfo;
	}

	/**
	 * convert parametr to string
	 * @param parameter
	 * @return
	 */
	private String getParameterAsString(Parameter parameter) {
	    	/**
		 * 判断parameter是否为空或是否为空字符串，如果是，则返回null
		 */
		if (parameter == null || "".equals(parameter.valueAsString())) {
			return null;
		}
		//transfer parameter to string
		return parameter.valueAsString();
	}
	/**
	 * 
	 * 打开动作触发事件
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	private void addTreeNodeExtension(List<MenuNodeInfo> rootMenus) {
		//message bar panel
	    	/**
		 * 创建JOutlookBar对象
		 */
		JOutlookBar root = new JOutlookBar();
		//add left root bar 打开动作触发事件
		addTreeNodeExtension(rootMenus, null, root);
		/**
		 * 创建JPanel对象并设置属性信息
		 */
		leftPanel = new JPanel();
		//设置透明
		leftPanel.setOpaque(false);
		//设置布局
		leftPanel.setLayout(new BorderLayout());
		/**
		 * 为当前对象leftPanel添加属性信息
		 */
		leftPanel.add(createStandLabel(), BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(root);
		scrollPane.setBorder(null);
		scrollPane.setPreferredSize(new Dimension(NumberConstants.NUMBER_180, 0));
		
		leftPanel.add(scrollPane, BorderLayout.CENTER);
	}
	
	/**
	 * 
	 * 占位间距
	 * @author 102246-foss-shaohongliang
	 * @date 2013-3-1 下午2:22:26
	 */
	private Component createStandLabel() {
		//create empty label
	    	/**
		 * 创建JLabel对象，并设置Preferred大小
		 */
		JLabel button = new JLabel();
		button.setPreferredSize(new Dimension(0, NumberConstants.NUMBER_15));
		return button;
	}
	/**
	 * 
	 * 打开动作触发事件
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	private void addTreeNodeExtension(List<MenuNodeInfo> rootMenus,MenuNodeInfo parentMenu, JOutlookBar parent) {
		//add Listener
	    	/**
		 * 添加监听器
		 */
		parent.addMenuActionListener(this);
		// container.seto
		/**
		 * 循环遍历rootMenus
		 */
		for (MenuNodeInfo menu : rootMenus) {
			//set level
			if(parentMenu == null){
				menu.setResLevel("2");
			}else{
				//set level
				menu.setResLevel(String.valueOf(Integer.valueOf(parentMenu.getResLevel())+1));
			}
			//create menu bar
		    	/**
			 * 创建JOutlookBar对象
			 */
			JOutlookBar ol = new JOutlookBar();
			//add menu bar to parent
			/**
			 * 获取barInfo信息
			 */
			BarInfo barInfo = parent.addBar(menu.getText(), menu.getIcon(), ol);
			/**
			 * 设置barInfo的object
			 */
			barInfo.setObject(menu);
			//mouse over to hand cursor
			/**
			 * 设置barInfo的button
			 */
			barInfo.getButton().addMouseListener(this);
			//get menu background
			//set backgroud by menu css
			/**
			 * 设置按钮样式
			 */
			setMenubarStyle(barInfo, menu);
			//get child nodes
			/**
			 * 获取List集合childMenus
			 */
			List<MenuNodeInfo> childMenus = menu.getChildMenus();
			//create childs bar
			/**
			 * 判断childMenus的size是否大于0，如果是，则打开动作触发事件
			 */
			if(childMenus.size()>0){
				addTreeNodeExtension(childMenus, menu, ol);
			}
		}
	}
	
	/**
	 * 
	 * 设置按钮样式
	 * @author 102246-foss-shaohongliang
	 * @date 2013-2-28 下午2:42:01
	 */
	private void setMenubarStyle(BarInfo barInfo, MenuNodeInfo menuNodeInfo) {
		//web端设置的按钮样式
		String bgClass = menuNodeInfo.getBgClass();
		//未选中时背景
		ImageIcon unSelectedImage = ImageUtil.getImageIcon(this.getClass(),bgClass + ".jpg");
		//选中后背景
		ImageIcon selectedImage = ImageUtil.getImageIcon(this.getClass(), "selected.jpg");
		//给barInfo设置背景样式
		barInfo.setBackground(unSelectedImage, selectedImage);
		//非叶子节点
		if(menuNodeInfo.getChildMenus().size()>0){
			//展开样式
			ImageIcon expandImage = ImageUtil.getImageIcon(this.getClass(), menuNodeInfo.getResLevel() + "-expand.png");
			//收缩样式
			ImageIcon shrinkImage = ImageUtil.getImageIcon(this.getClass(), menuNodeInfo.getResLevel() + "-shrink.png");
			//给barInfo设置收展样式
			barInfo.setIcon(expandImage, shrinkImage);
		}
	}

	
	
	/**
	 * inner class for compare
	 */
	private static Comparator<MenuNodeInfo> menuComparator = new Comparator<MenuNodeInfo>() {

		/**
		 * comparaor inner class for comparing object
		 * @param o1
		 * @param o2
		 * @return
		 */
		public int compare(MenuNodeInfo o1, MenuNodeInfo o2) {
			return o1.getIndex() - o2.getIndex();
		}
	};
	/**
	 * 
	 * 渲染器
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	private static class ExplorerCellRenderer extends DefaultTreeCellRenderer {
		/**
		 * 100
		 */
		private static final int HUNDRED = 100;
		/**
		 * 60
		 */
		private static final int SIXTY = 60;
		/**
		 * 55
		 */
		private static final int FIFTYFIVE = 55;
		/**
		 * 序列化版本号
		 */
		private static final long serialVersionUID = -6243020217618952484L;

		/**
		 * get tree on theleft side of main ui
		 */
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {
		    	/**
			 * 调用父类的getTreeCellRendererComponent方法
			 */
			super.getTreeCellRendererComponent(tree, value, sel, expanded,
					leaf, row, hasFocus);
			/**
			 * 设置字体样式
			 */
			this.setFont(new Font("宋体", Font.BOLD, FONTSIZE14));
			/**
			 * 设置前景色
			 */
			this.setForeground(new Color(FIFTYFIVE, SIXTY, HUNDRED));
			/**
			 * 设置关闭图标
			 */
			setClosedIcon(null);
			/**
			 * 设置打开图标
			 */
			setOpenIcon(null);
			/**
			 * 获取用户对象
			 */
			Object obj = ((DefaultMutableTreeNode) value).getUserObject();
			/**
			 * 判断对昂obj是否是MenuNodeInfo的实例
			 */
			if (obj instanceof MenuNodeInfo) {
			    	//定义一个MenuNodeInfo类型的常量
				final MenuNodeInfo item = (MenuNodeInfo) obj;
				//设置当前对象的图标为空
				this.setIcon(null);
				/**
				 * 判断选项图标是否为空，如果不为空，则设置到当前图标
				 */
				if (item.getIcon() != null) {
					this.setIcon(item.getIcon());
				}
				/**
				 * 判断选项文本是否为空，如果不为空，则设置到当前文本
				 */
				if (item.getText() != null) {
					this.setText(item.getText());
				}
			}
			return this;
		}

		/**
		 * 
		 * 设置背景节点
		 * @author 026113-foss-linwensheng
		 * @date 2012-12-18 上午10:03:50
		 */
		public void setBackgroundNonSelectionColor(Color newColor) {
			super.setBackgroundNonSelectionColor(new Color(COLORX, COLORY, COLORZ));
		}

		/**
		 * 
		 * RENDER COMPONENT
		 * @author 026113-foss-linwensheng
		 * @date 2012-12-18 上午10:03:50
		 */
		protected void paintComponent(Graphics g) {
			//opague is not allow
		    	/**
			 * 设置透明
			 */
			setOpaque(false);
			/**
			 * 创建Dimension对象d，该对象封装了一个构件的高度和宽度
			 */
			Dimension d = getSize();
			//picture
			/**
			 * 创建ImageIcon对象
			 */
			ImageIcon ic = ImageUtil.getImageIcon(this.getClass(),"background.jpg");
			//drwa picture
			/**
			 * 循环遍历对象d的宽度
			 */
			for (int x = 0; x < d.width; x += ic.getIconWidth()){
			    	/**
				 * 循环遍历对象d的高度
				 */
				for (int y = 0; y < d.height; y += ic.getIconHeight()){
				    	/**
					 * 调用drawImage()方法，在指定位置并且按指定大小绘制指定的 Image
					 */
					g.drawImage(ic.getImage(), x, y, null, null);
				}
			}
			//调用父类的paintComponent方法进行界面重绘
			super.paintComponent(g);
		}

	}
	
	/**
	 * 
	 *菜单点击事件
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
	    	/**
		 * 创建BarInfo对象barInfo
		 */
		BarInfo barInfo = JOutlookBar.selectedBarInfo;
		/**
		 * 定义MenuNodeInfo类型的常量item
		 */
		final MenuNodeInfo item = (MenuNodeInfo)barInfo.getObject();
		/**
		 * 判断item.getAction()是否为空，如果不为空，调用actionPerformed()方法
		 */
		if (item.getAction() != null) {
//			//异步打开菜单，防止阻塞按钮状态
//			SwingUtilities.invokeLater(new Runnable() {
//				@Override
//				public void run() {
					item.getAction().actionPerformed(e);
					LOG.info(item.getAction().getClass().getName());
//				}
//			});
		}
	}
	
	/**
	 * 
	 *菜单点击事件
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	private static class MenuClickListener extends MouseAdapter {
		/**
		 * tree on the menu
		 */
		private JXTree lastTree;
		/**
		 * 添加事件
		 */
		public void mouseClicked(MouseEvent e) {
		    	/**
			 * 获取JXTree对象tree
			 */
			JXTree tree = (JXTree) e.getSource();
			/**
			 * 判断lastTree是否为空
			 */
			if (lastTree == null) {
			    	//如果为空，则将tree赋给lastTree
				lastTree = tree;
			} else if (lastTree.equals(tree) ) {
			    	//如果lastTree等于tree，则记录日志
				LOG.debug("do nothing");
			} else {
			    	/**
				 * 调用lastTree对象的clearSelection()方法，并将tree赋给lastTree
				 */
				lastTree.clearSelection();
				lastTree = tree;
			}
			/**
			 * 创建DefaultMutableTreeNode对象
			 */
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
					.getLastSelectedPathComponent();
			/**
			 * 如果node为空，则返回
			 */
			if (node == null) {
				return;
			}
			//获取用户对象
			Object obj = node.getUserObject();
			/**
			 * 判断对象obj是否是MenuNodeInfo的实例
			 */
			if (obj instanceof MenuNodeInfo) {
			    	//将obj赋给item
				MenuNodeInfo item = (MenuNodeInfo) obj;
				/**
				 * 判断item.getAction()是否为空，如果不为空，则调用actionPerformed()方法
				 */
				if (item.getAction() != null) {
					item.getAction().actionPerformed(null);
				}
			}
		}
	}
	/**
	 * 
	 * 增加子节点
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	private void addChildNode(DefaultMutableTreeNode node, MenuNodeInfo root) {
		//iterator over sub menu
	    	/**
		 * 循环遍历root.getChildMenus()
		 */
		for (MenuNodeInfo menu : root.getChildMenus()) {
			//create menu
		    	/**
			 * 创建DefaultMutableTreeNode对象currentNode
			 */
			DefaultMutableTreeNode currentNode = menu2Node(menu);
			//add sub menu to parent
			//向currentNode对象添加元素currentNode
			node.add(currentNode);
			//add all
			//增加子节点
			addChildNode(currentNode, menu);
		}
	}
	/**
	 * 
	 * 默认的树节点
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	private DefaultMutableTreeNode menu2Node(MenuNodeInfo item) {
		//返回之前不用的本地变量  
		return new DefaultMutableTreeNode(item);
	}
	/**
	 * 
	 * 获取框信息
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	private JPanel getInfoPanel() {
		//infopanel
	    	/**
		 * 定义JPanel对象
		 */
		JPanel infoPanel = null;
		///get plugin extension points
		/**
		 * 创建扩展点对象extensionPoint
		 */
		ExtensionPoint extensionPoint = plugin.getDescriptor()
				.getExtensionPoint("info-message");
		//get extensions
		/**
		 * 创建Extension类型的集合extensions
		 */
		List<Extension> extensions = new ArrayList<Extension>(
				extensionPoint.getConnectedExtensions());
		//iterator over extension
		/**
		 * 循环遍历extensions
		 */
		for (Extension extension : extensions) {
			//get class name
		    	/**
			 * 获取参数"info-class"
			 */
			String cls = extension.getParameter("info-class").valueAsString();
			//class name is null no work
			/**
			 * 判断cls是否为空，如果为空，则继续循环下一条
			 */
			if (StringUtils.isEmpty(cls)) {
				continue;
			}
			try {
				//get plugin class loader to load class
			    	/**
				 * 创建JpfUtils的实例并赋给obj
				 */
				Object obj = JpfUtils.createInstance(
						plugin.getManager().getPlugin(
								extension.getDeclaringPluginDescriptor()
										.getId()), cls);
				/**
				 * 创建Class<?>对象
				 */
				Class<?> clazz  =  obj.getClass();
				//get extension method 
				/**
				 * 获取Method对象infoMethod
				 */
				Method infoMethod = clazz.getMethod(extension.getParameter("info-method").valueAsString());
				try {
				    	/**
					 * 获取Object对象object，并将object赋给infoPanel
					 */
					Object object  = infoMethod.invoke(obj);
					infoPanel = (JPanel)object;
				} catch (IllegalArgumentException e1) {
					//exception log 记录异常日志
					LOG.error("IllegalArgumentException", e1);
					//exception log 记录异常日志
				} catch (IllegalAccessException e2) {
					//exception log 记录异常日志
					LOG.error("IllegalAccessException", e2);
				} catch (InvocationTargetException e3) {
					//exception log 记录异常日志
					LOG.error("InvocationTargetException", e3);
				}
			}catch(NoSuchMethodException n1){
				//exception log 记录异常日志
				LOG.error("NoSuchMethodException", n1);
			} catch (PluginLifecycleException n2) {
				//exception log 记录异常日志
				LOG.error("PluginLifecycleException", n2);
			}
		}
		return infoPanel;
	}

	/**
	 * 
	 * 设置插件
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	@Override
	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}
	/**
	 * 
	 * 注入Workbench
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-18 上午10:03:50
	 */
	@Override
	public void setWorkbench(IWorkbench workbench) {
		this.workbench = workbench;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	@Override
	public void mouseExited(MouseEvent e) {
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		
	}
}