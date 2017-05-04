/**********************************************************************/
/* Author: Yu Luo*/
/* 德邦物流FOSS系统框架*/
/**/
/*所有ID以及class名请勿随意更换，需更换请同事更换CSS文件内响应代码*/
/*所有ID以及class名请勿随意更换，需更换请同事更换CSS文件内相应代码*/
/*对应CSS文件：*/
/*			rsc/css/frame-all-4_1-customized.css (所有自定义ID及class的相关定义)*/
/*			rsc/css/frame-all-4_1.css	(所有extjs原有class(x-开头)的相关定义)*/
/*自定义CSS class请勿使用x-开头*/
/*暂定页面转跳与tabID绑定，即：
/*			主菜单(mainNav)内 href为'#!'开头,URL内容可包括字母数字下划线以及'/',不可用'-'。如果需要使用全屏请在结尾附加'_fullScreen字段' */
/*			打开该页时handler会剔除'#!'*,用'-'复写'/',并在开头加'T_'字段生成 Tab ID, 如末尾字段为'_fullScreen'则改动isFullScreen变量。 */
/*			Loader会在根目录下寻找[$href(剔除#!)].html*/
/**********************************************************************/

/*-----------------------------全局属性与方法定义--------------------------------*/
//当前页面是否使用全屏（菜单上收）
var isFullScreen=false;
var prevIsFullScreen=false;
//通知区域当前状态flag,展开为true
var announcePaneltFlag=false;
//复写Ext.tree.Panel的默认图标用
var noIcon="../images/login/main/emp.gif";
//root专用 CSS class,在展开状态下height=0，请谨慎使用。
var lvl0Style="ye1-node-lvl0";
//第一层 CSS class
var lvl1Style="ye1-node-lvl1";
//第二层 CSS class
var lvl2Style="ye1-node-lvl2";
//第三层 CSS class
var lvl3Style="ye1-node-lvl3";
//第四层 CSS class
var lvl4Style="ye1-node-lvl4";
//第五层备用 CSS class 名称, 无效果。实际第五层继承menu的CSS风格
var lvl5Style="ye1-node-lvl5";
//下端最后一行用的CSS Class
var endSpecStyle="ye1-node-end-all";

//handler function, 展开页首通知版面。
function openAnnounce(){
	var wStripe = Ext.getCmp('w_stripe'),
		announcePanel_Cont = Ext.getCmp('announce_panel_Cont'),
		announceNotice = Ext.getCmp('announceNotice');
	scroll(0,0);
	wStripe.update('<img src="../images/login/main/wStripe_nav_top_announce_expand.gif">');
	announcePanel_Cont.animate({
		to: {
			height:230
		}
	});
	announcePaneltFlag=true;
	if(announceNotice!=null){
		announceNotice.close();
	}
}

//handler function, 关闭页首通知版面。
function closeAnnounce(){
	var wStripe = Ext.getCmp('w_stripe'),
		announcePanel_Cont = Ext.getCmp('announce_panel_Cont');
	scroll(0,0);
	announcePanel_Cont.animate({
		to: {
			height:0
		}
	});
	wStripe.update('<img src="../images/login/main/wStripe_nav_top.gif">');
	announcePaneltFlag=false;
}

//生成当前日期
function constructDateTime(){
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0!
	var yyyy = today.getFullYear();
	var hh = today.getHours();
	var minutes = today.getMinutes();
	var ss=today.getSeconds();
	if(dd<10){dd='0'+dd;} 
	if(mm<10){mm='0'+mm;} 
	if(hh<10){hh='0'+hh;}
	if(minutes<10){minutes='0'+minutes;}
	if(ss<10){ss='0'+ss;}
	var today = yyyy+'-'+mm+'-'+dd+' '+hh+':'+minutes+':'+ss;
	return today;
}

//页面滚轮事件handler
function onScroll(){
	//确认主菜单是否算在宽度内
	var hasNav=1;
	if(isFullScreen==true){
		hasNav=0;
	}else{
		hasNav=1;
	}
	//当前标签页
	var curTab=Ext.getCmp('mainAreaPanel').getActiveTab();
	//header高度
	var frameTopHeight=Ext.getCmp('FrameTop').getHeight();
	//通知版面高度
	var announceHeight=Ext.getCmp('announce_panel_Cont').getHeight();
	//通知右上悬浮
	var aN=Ext.getCmp('announceNotice');
	//console.log('FTHEIGHT='+frameTopHeight);
	//标签页的悬浮工具条
	var curToolbarID=curTab.id;
	var curToolbar=Ext.getCmp(curToolbarID+'_toolbar');
	//黄色主工具条以及下面的白条，原定同标签页工作条一起悬浮，但因IE z-index问题暂时弃用。
	//var fToolbar=Ext.getCmp('FrameToolbar');
	//var fStripe=Ext.getCmp('w_stripe');
	//console.log(curToolbar);
	//scrollTop值
	var sTop=window.pageYOffset|document.documentElement.scrollTop;
	//右上通知悬浮位置控制：
	var hasToolbar=0;
	if(curToolbar){
		hasToolbar=1;
	}
	if(aN){
		switch(true){
			case ((sTop>frameTopHeight+48)&&(sTop<frameTopHeight+48+65)):
				aN.el.dom.style.top='0px';	
				//console.log('case1');
				//console.log(sTop);
			break;
			case (sTop<frameTopHeight+48):
				aN.el.dom.style.top=(frameTopHeight+48-sTop)+'px';	
				//console.log('case2');
			break;
			case(sTop>=frameTopHeight+48+64):
				aN.el.dom.style.top=(42*hasToolbar)+'px';	
				//console.log('case3');
			break;
			default:
			break;
		}
	}
	//标签页悬浮工具条位置控制：
	if(sTop>(frameTopHeight+announceHeight+113)){
		if(curToolbar){
			curToolbar.el.dom.style.position='fixed';
			curToolbar.el.dom.style.top='0px';
			curToolbar.el.dom.style.left=((186*(hasNav))+6)+'px';
			curToolbar.el.dom.style.zIndex=30;
		}
	}else{	
		if(curToolbar){
			curToolbar.el.dom.style.position='absolute';
			curToolbar.el.dom.style.top='18px';
			curToolbar.el.dom.style.left='0px';
			curToolbar.el.dom.style.zIndex=5;
		}
		/*if(sTop>=(frameTopHeight)){
			fToolbar.el.dom.style.position='fixed';
			fToolbar.el.dom.style.top='0px';
			fToolbar.el.dom.style.left='0px';
			fToolbar.el.dom.style.zIndex=110;
			logoSpace.addCls('tabPageLogo');
								
			fStripe.el.dom.style.position='fixed';
			fStripe.el.dom.style.top='36px';
			fStripe.el.dom.style.left='0px';
			fStripe.el.dom.style.zIndex=30;
			fStripe.el.dom.style.backgroundImage="none";
		}else{
			fToolbar.el.dom.style.position='relative';
			fToolbar.el.dom.style.top='0px';
			fToolbar.el.dom.style.left='0px';
			fToolbar.el.dom.style.zIndex=100;
			logoSpace.removeCls('tabPageLogo');
							
			fStripe.el.dom.style.position='relative';
			fStripe.el.dom.style.top='0px';
			fStripe.el.dom.style.left='0px';
			fStripe.el.dom.style.zIndex=5;
			fStripe.el.dom.style.backgroundImage="url('../images/login/main/wStripe_bg.gif')";
				
		}*/
	}
}

//常用功能设置窗口
Ext.define('Deppon.main.NavConfigWindow', {
	extend: 'Ext.window.Window',
	title: login.i18n('dpap.login.NavConfigWindowTitle'),
	height: 400,
	width: 600,
	modal:true,
	closeAction: 'hide',
	layout: {
		type:'vbox',
		padding:'5',
		align:'stretch'
	},
	//功能搜索框
	searchNavModule : null,
	getSearchNavModule : function(){
		if(this.searchNavModule==null){
			this.searchNavModule=Ext.create('Ext.form.Panel', {
								labelWidth: 75, // label settings here cascade unless overridden
								url: 'url_here',
								id: 'searchNavModule',
								frame: false,
								height:30,
								//flex:1,
								layout:'anchor',
								items: [{
									xtype: 'textfield',
									name: 'NC_search',
									id: 'NC_search',
									allowBlank: true,
									emptyText: login.i18n('dpap.login.searchNavModule'),
									anchor: '-250 100%'// requires a non-empty value
    							}]
							});	
		}
		return this.searchNavModule;
	},
	//功能列表
	navConfigTabPanel : null,
	getNavConfigTabPanel : function(){
		if(this.navConfigTabPanel==null){
			this.navConfigTabPanel = Ext.create('Ext.tab.Panel', {
										id:"NavConfigPanel",
										flex:1,
										plain:true,
										items:[{
												title: '接送货', tabConfig:{width:100}, closable:false,
												id:'NC_deliv', layout:'fit'
											},
											{
												title: '中转', tabConfig:{width:100}, closable:false,
												id:'NC_trans', layout:'fit'
											},
											{
												title: '结算', tabConfig:{width:100}, closable:false,
												id:'NC_account', layout:'fit'
											},
											{
												title: '综合管理', tabConfig:{width:100}, closable:false,
												id:'NC_manage', layout:'fit'
											}]
									});
		}
		return this.navConfigTabPanel;
	},
	listeners:{
		//恢复overflow:auto
		beforeclose:function(){
			document.documentElement.style.overflow='auto';
			document.body.style.overflow='auto';
		}			
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [me.getSearchNavModule(),me.getNavConfigTabPanel()];
		me.callParent([cfg]);
	}
});

/*-----------------------------页面的头部中的工具栏--------------------------------*/
Ext.define('Deppon.main.Toptoolbar', {
	extend: 'Ext.container.Container',
	height:36,
	id:"FrameToolbar",
	bodyBorder:false,
	layout:'hbox',
	//左右德邦标识预留空间
	logoSpace: null,
	getLogoSpace : function(){
		if(this.logoSpace==null){
			this.logoSpace = Ext.create('Ext.panel.Panel',{
								width:240,
								height:36,
								id:'tabPageLogo',
								autoDestroy:false
							});
		}
		return this.logoSpace;
	},
	//通知按钮
	announceLink: null,
	getAnnounceLink : function(){
		//var me = this;
		if(this.announceLink==null){
			this.announceLink = Ext.create('Ext.Button', {
									width:120,
									height:36,
									text: '<span style="font-size: 22px; font-weight: bold;">3 </span><b>项未读通知</b>', 
									id:'M_announce',
									cls:'announceLinkButton',
									handler: this.toggleAnnounce,
									autoScroll: false	
								});
		}
		return this.announceLink;
	},
	//展开\关闭通知面板
	toggleAnnounce : function(){
		scroll(0,0);
		if(announcePaneltFlag==false){
			openAnnounce();
		}else{
			closeAnnounce();
		}
	},
	//返回主页按钮
	homeLink : null,
	getHomeLink : function(){
		var me = this;
		if(this.homeLink==null){
			this.homeLink = Ext.create('Ext.Button', {
								width:80,
								height:36,
								text: login.i18n('dpap.login.homeLink'), 
								id:'M_home',
								cls:'homeButton',
								handler: me.onClick,
								autoScroll: false						
							});
		}
		return this.homeLink;
	},
	//主页按钮Handler
	onClick : function(arg){
		var tabPanel = Ext.getCmp('mainAreaPanel');
		scroll(0,0);
		var TabID='#T_';
		for (var j=2; j<arg['id'].length; j++){
			TabID=TabID+arg['id'][j];
		}
		var newActiveTab=tabPanel.child(TabID);
		tabPanel.setActiveTab(newActiveTab);
	},
	//设置常用功能按钮
	navConfigLink : null,
	getNavConfigLink : function(){
		var me = this;
		if(this.navConfigLink==null){
			this.navConfigLink = Ext.create('Ext.Button', {
									width:120,
									height:36,
									text: login.i18n('dpap.login.NavConfigWindowTitle'), 
									id:'M_navConfig',
									cls:'navLinkButton',
									handler: me.openNavConfig,
									autoScroll: false	
								});
		
		}
		return this.navConfigLink;
	},
	//常用功能设置窗口
	navConfigWindow : null,
	//常用功能设置Handler
	openNavConfig : function(){
		scroll(0,0);
		if(this.navConfigWindow == null){
			this.navConfigWindow = Ext.create('Deppon.main.NavConfigWindow');
		}
		this.navConfigWindow.show();
		//console.log(window);
		//console.log(document.getElementsByTagName('HTML'));
		//console.log(Ext.getBody());
		//锁定背景overflow(跨浏览器：FF IE Chrome)
		document.body.style.overflow='hidden';
		document.documentElement.style.overflow='hidden';
		//document.getElementsByTagName('HTML').style.overflow='hidden';
	},
	//搜索框
	searchSpace : null,
	getSearchSpace : function(){
		if(this.searchSpace == null){
			this.searchSpance = Ext.create('Ext.form.Panel', {
								width: 270,
								//labelWidth: 75, // label settings here cascade unless overridden
								url: 'url_here',
								id: 'searchSpace',
								frame: false,
								columnWidth:1,
								height:36,
								layout:'column',
								items: [{
									xtype: 'textfield',
									name: 'comp_search',
									id: 'comp_search',
									allowBlank: true,
									emptyText: login.i18n('dpap.login.searchSpace'),
									width: 185// requires a non-empty value
    							},{
									xtype: 'button',
									name: 'comp_search_button',
									id: 'comp_search_button',
									width: 35,
									height:36// requires a non-empty value
    							}]
							});
		}
		return this.searchSpance;
	},
	//用户名空间
	userInfoSpace : null,
	getUserInfoSpace : function(){
		if(this.userInfoSpace==null){
			this.userInfoSpace = Ext.create('Ext.panel.Panel',{
							 		id: 'userInfoSpace',
						 			width:120,
									height:36,
									html:'',//'用户名',
									cls:'frametoolbar_content'
						 		});
		}
		return this.userInfoSpace;
	},
	//用户所属部门空间
	userDepartSpace : null,
	getUserDepartSpace : function(){
		if(this.userDepartSpace==null){
			this.userDepartSpace = Ext.create('Ext.panel.Panel',{
							   		id: 'userDepartSpace',
						 			width:110,
									height:36,
									html:'',//'用户归属部门',
									cls:'frametoolbar_content'
						 		});
		}
		return this.userDepartSpace;
	},
	//当前时间
	dateTimeSpace : null,
	getDateTimeSpace : function(){
		if(this.dateTimeSpace==null){
			this.dateTimeSpace =Ext.create('Ext.panel.Panel',{
							 		id: 'dateTimeSpace',
						 			width:130,
									height:36,
									html:constructDateTime,
									cls:'frametoolbar_content'
						 		});
		}
		return this.dateTimeSpace;
	},
	//登出按钮
	logout : null,
	getLogOut : function(){
		var me = this;
		if(this.logout==null){
			this.logout = Ext.create('Ext.Button',{
							width:55,
							height:36,
							text:'',
							id:'M_logout',
							autoScroll: false,
							handler: me.logoutHandler
						});
		}
		return this.logout;
	},
	logoutHandler : function(){
		Ext.MessageBox.confirm(login.i18n('dpap.login.logout'), login.i18n('dpap.login.sureLogout'),function(btn){
			console.log(btn);
			if(btn=='yes'){
				//Ajax请求logout
				Ext.Ajax.request({
					url : '../login/logout.action',
					//退出成功
					success : function(response, opts) {
						window.location = '../login/index.action';
					},
					//退出失败
					exception : function(response, opts) {
						var result = Ext.decode(response.responseText);
						Ext.MessageBox.show({
			                title: login.i18n('dpap.login.messageTitle'),
			                msg: result.message,
			                buttons: Ext.MessageBox.OK,
			                icon: Ext.MessageBox.ERROR
			            });
					}
				});
			}
		});
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [me.getLogoSpace(),/*me.getAnnounceLink(),*/me.getHomeLink(),{ xtype: 'panel' ,flex: 1, html: '&nbsp;' },/*me.getNavConfigLink(),me.getSearchSpace(),*/me.getUserInfoSpace(),me.getUserDepartSpace(),me.getDateTimeSpace(),me.getLogOut()];
		me.callParent([cfg]);
		//Ajax请求当前登录用户信息
		Ext.Ajax.request({
			url : '../login/currentLoginUserInfo.action',
			//退出成功
			success : function(response, opts) {
				var result = Ext.decode(response.responseText);
				me.getUserInfoSpace().update(result.currentUserName);
				me.getUserDepartSpace().update(result.currentUserDeptName);
			}
		});
	}
});

/*--------------------------------页面的头部---------------------------------------*/
Ext.define('Deppon.main.topPanel', {
	extend: 'Ext.container.Container',
	cls:'autoHeight',
	bodyCls:'autoHeight',
	//页首德邦标识部分。
	topLogo: null,
	getTopLogo : function(){
		if(this.topLogo==null){
			this.topLogo = Ext.create('Ext.panel.Panel' ,{
				id:"FrameTop",
				bodyBorder:false,
				height:102
			});
		}
		return this.topLogo;
	},
	//整合页首黄色工具栏
	toptoolbar: null,
	getToptoolbar: function(){
		if(this.toptoolbar==null){
			this.toptoolbar = Ext.create('Deppon.main.Toptoolbar');
		}
		return this.toptoolbar;
	},
	//header与body之间白条部分。
	whiteStripe: null,
	getWhiteStripe : function(){
		if(this.whiteStripe==null){
			this.whiteStripe = Ext.create('Ext.panel.Panel' ,{
								id: 'w_stripe',
								height:12,
								html: '<img src="../images/login/main/wStripe_nav_top.gif">'
							});
		}
		return this.whiteStripe;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [me.getTopLogo(),me.getToptoolbar(),me.getWhiteStripe()];
		me.callParent([cfg]);
	}
});

/*---------------------------------通知面板----------------------------------------*/
Ext.define('Deppon.main.AnnouncePanel_Cont', {
	extend: 'Ext.panel.Panel',
	id:'announce_panel_Cont',
	height: 0,
	//autoRender:true,
	//autoDestroy:false,
	layout:'column',
	//设置通知面板滚动
	scrolling:null,
	//prevSL:记录上次的scrollLeft值用
	prevSL : 100,
	//counter
	aItemCount : 0,
	//通知总数
	aItemTotal : 8,
	//小版面文字
	simpleStrPanel : [],
	//大版面文字
	expandedStrPanel : [],
	//“处理”按钮
	announceItemCloseButton : [],
	//右上关闭按钮。注：因为只是缩小版面，因而不可用EXTJS的collapse()或close()
	announceItemClose : [],
	//通知事项版面
	announceItem : [],
	//通知区域关闭按钮
	announceCloseB : null,
	getAnnounceCloseB : function(){
		if(this.announceCloseB==null){
			this.announceCloseB = Ext.create('Ext.Button',{
								  id:"announceCloseB",
								  text:'',
								  height:25,
								  width:25,
								  handler:closeAnnounce
							  });
		}
		return this.announceCloseB;
	},
	//通知面板左滚动用按钮
	announcePanelScroll_L : null,
	getAnnouncePanelScroll_L : function(){
		var me = this;
		if(this.announcePanelScroll_L==null){
			this.announcePanelScroll_L = Ext.create('Ext.Button',{
											id:'announceScroll_L',
											width:60,
											height:60,
											cls:'scroll',
											text: '',
											autoScroll: false,
											listeners:{
												mouseover: function() {
													me.scrolling=setInterval(function(){me.announceScrollLeft();},100);
												},
												mouseout:function() {
													clearInterval(me.scrolling);
												}
											}
										});
		}
		return this.announcePanelScroll_L;
	},
	//左滚动
	announceScrollLeft : function(){
		this.getAnnouncePanel().body.scroll('right',10);
		this.getAnnouncePanelScroll_R().removeCls('scroll_disabled');
		this.getAnnouncePanelScroll_R().addClass('scroll');
		//console.log(this.getAnnouncePanel().body.dom.scrollLeft);
		//如等prevSL则滚动到头，更换CSS class.
		if(this.getAnnouncePanel().body.dom.scrollLeft==this.prevSL){
			this.getAnnouncePanelScroll_L().removeCls('scroll');
			this.getAnnouncePanelScroll_L().addClass('scroll_disabled');
		}
		//记录scrollLeft值。
		this.prevSL=this.getAnnouncePanel().body.dom.scrollLeft;
	},
	//通知面板右滚动用按钮
	announcePanelScroll_R : null,
	getAnnouncePanelScroll_R : function(){
		var me = this;
		if(this.announcePanelScroll_R==null){
			this.announcePanelScroll_R = Ext.create('Ext.Button',{
											id:'announceScroll_R',
											width:60,
											height:60,
											cls:'scroll',
											text: '',
											autoScroll: false,
											listeners:{
												mouseover: function() {
													if (!this.mousedOver) {
														this.mousedOver = true;
													}
													me.scrolling=setInterval(function(){me.announceScrollRight();},100);
													
												},
												mouseout:function() {
													if (this.mousedOver) {
														this.mousedOver = false;
													}
													clearInterval(me.scrolling);
												}
											}
										});
		}
		return this.announcePanelScroll_R;
	},
	//右滚动
	announceScrollRight : function(){
		this.getAnnouncePanel().body.scroll('left',10);
		this.getAnnouncePanelScroll_L().removeCls('scroll_disabled');
		this.getAnnouncePanelScroll_L().addClass('scroll');
		//console.log(this.getAnnouncePanel().body.dom.scrollLeft);
		if(this.getAnnouncePanel().body.dom.scrollLeft==this.prevSL){
			this.getAnnouncePanelScroll_R().removeCls('scroll');
			this.getAnnouncePanelScroll_R().addClass('scroll_disabled');
		}
		this.prevSL=this.getAnnouncePanel().body.dom.scrollLeft;
	},
	//中间主通知内容区域
	announcePanel : null,
	getAnnouncePanel : function(){
		var me = this;
		if(this.announcePanel==null){
			this.announcePanel = Ext.create('Ext.panel.Panel',{
									id:'announce_panel',
									height: 230,
									columnWidth:1,
									autoRender:true,
									autoDestroy:false,
									layout:'column',
									items:me.createAnnounceItem()
								});
		}
		return this.announcePanel;
	},
	//创建通知版面的内容
	createAnnounceItem : function(){
		var me = this;
		var expandedStr='<span class="aTitle">未处理运单</span><span class="aLabel">运单号：</span><b>A12345678</b></br><span class="aLabel">类别：</span>长途</br><span class="aLabel">线路：</span>天津-上海</br><span class="aLabel">重量(公斤)：</span>100</br><span class="aLabel">体积(方)：</span>300';
		//为所有通知事项生成以上所有数据：
		for(me.aItemCount=0; me.aItemCount<me.aItemTotal; me.aItemCount++){
			var simpleCont='<span class="aTitle">未读通知'+(me.aItemCount+1)+'</span></br>发送人：李欣</br>主题：关于XXXXXXXX';
			me.simpleStrPanel[me.aItemCount]=Ext.create('Ext.panel.Panel',{
											html:simpleCont
										});
			me.expandedStrPanel[me.aItemCount]=Ext.create('Ext.panel.Panel',{
											width:355,
											html:expandedStr
										});
			me.announceItemCloseButton[me.aItemCount]=Ext.create('Ext.Button',{
													id:'announce_do_'+me.aItemCount,
													width:120,
													height:30,
													cls:'yellow_button',
													text: '<b>处理</b>',
													autoScroll: false	
												});
			me.announceItemClose[me.aItemCount]=Ext.create('Ext.Button',{
													id:'announceClose'+me.aItemCount,
													width:25,
													height:25,
													cls:'grey_close',
													text: '',
													autoScroll: false,
													handler:function(o){me.collapseAnnounceItem_handler(o);}
												});
			//根据事项顺序生成该事项左起位置(margin-left)
			var lMargin=5+me.aItemCount*225;
			me.announceItem[me.aItemCount]=Ext.create('Ext.panel.Panel',{
									id:'announceItem'+me.aItemCount,
									height: 120,
									width: 215,
									cls:'announce_item',
									margin:'80 0 0 '+lMargin,
									listeners: {
										render: function(thisPanel) {thisPanel.body.on('click', function(c, panel,o){me.toggleAnnounceItem(c,this);});}
									},
									autoRender:true,
									autoDestroy:false,
									items:[me.simpleStrPanel[me.aItemCount]]
								});
		}
		return me.announceItem;
	},
	//缩小通知事项（按钮用o为Ext.component）
	collapseAnnounceItem_handler : function(o){
		var me = this;
		//console.log(o);
		var AnnounceIdNum=o.id.charAt(13);
		var AnnouncePanel_I=Ext.getCmp('announceItem'+AnnounceIdNum);
		AnnouncePanel_I.removeCls('announceItemExpanded');
		AnnouncePanel_I.addClass('announce_item');
		AnnouncePanel_I.animate({
					duration:0,
					to: {
						height:120
					}
				}).animate({
					to:{
						width:215
					}
				});
		AnnouncePanel_I.removeAll();
		AnnouncePanel_I.add(me.simpleStrPanel[AnnounceIdNum]);
	},
	//放大\缩小通知事项
	toggleAnnounceItem : function(c, panel){
		var hasMainNav=1;
		if(isFullScreen==true){
			hasMainNav=0;
		}
		else{
			hasMainNav=1;
		}
		//鼠标点击点X值
		var mouseX=c.browserEvent.clientX;
		//主区域宽度
		var areaWidth=Ext.getCmp('announce_panel').getWidth();
		//删除ID内'-body'字段从而得到通知事项componentID.
		var AnnounceId=panel.id.replace("-body", '');
		//第N?个通知
		var AnnounceIdNum=AnnounceId.charAt(12);
		var AnnouncePanel=Ext.getCmp(AnnounceId);
		var curHeight=AnnouncePanel.height;
		//如果高度小于150则展开，大于则缩小。
		if(curHeight<=150){		
			for(this.aItemCount=0; this.aItemCount<this.aItemTotal; this.aItemCount++){
				if (this.aItemCount==AnnounceIdNum){
					this.expandAnnounceItem(AnnounceId);
				}else{
					this.collapseAnnounceItem('announceItem'+this.aItemCount);
				}
			}
		}
		//defer0.5秒 等待展开/收缩动画结束,
		//判定当前事项位置，如在左右1/3区域内则向反方向滚动主版面。
		Ext.defer(function(){
			switch(true){
				case ((mouseX-(186*hasMainNav))<areaWidth*0.3):
					//console.log('case1');
					Ext.getCmp('announce_panel').body.scroll('right',((areaWidth/2)-(mouseX-(186*hasMainNav))-50));
				break;
				case ((mouseX-(186*hasMainNav))>areaWidth*0.7):
					//console.log('case2');
					Ext.getCmp('announce_panel').body.scroll('left',((areaWidth/2)-(areaWidth-(mouseX-(186*hasMainNav)))));
				break;
				default:
				break;
			}
		},500);
	},
	//缩小通知事项(aID为事项ID)
	collapseAnnounceItem : function(aID){
		var me = this;
		var AnnouncePanel_I=Ext.getCmp(aID);
		var AnnounceIdNum=aID.charAt(12);
		AnnouncePanel_I.removeCls('announceItemExpanded');
		AnnouncePanel_I.addClass('announce_item');
		AnnouncePanel_I.animate({
					duration:0,
					to: {
						height:120
					}
				}).animate({
					to:{
						width:215
					}
				});
		AnnouncePanel_I.removeAll();
		AnnouncePanel_I.add(me.simpleStrPanel[AnnounceIdNum]);
	},
	//展开事项(aID为事项ID)
	expandAnnounceItem : function(aID){
		var me = this;
		var AnnouncePanel_I=Ext.getCmp(aID);
		var AnnounceIdNum=aID.charAt(12);
		AnnouncePanel_I.removeCls('announce_item');
		AnnouncePanel_I.addClass('announceItemExpanded');
		AnnouncePanel_I.syncFx();
		AnnouncePanel_I.animate({
					duration:0,
					to: {
						height:194				
					}
				}).animate({
					to:{
						width:425	
					}
				});
		//等待动画结束再更换内容，防止因动画动作中造成的layout错乱。
		Ext.defer(function () {
					AnnouncePanel_I.removeAll();
					AnnouncePanel_I.add({
						xtype:'panel',
						layout:'column',
						items:[
							me.expandedStrPanel[AnnounceIdNum],
							me.announceItemClose[AnnounceIdNum]
						]},
						me.announceItemCloseButton[AnnounceIdNum]
					);
			},650);
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [me.getAnnounceCloseB(),me.getAnnouncePanelScroll_L(),me.getAnnouncePanel(),me.getAnnouncePanelScroll_R()];
		me.callParent([cfg]);
	}
});

/*----------------------------------主导航-----------------------------------------*/
//定义一个功能菜单store
Ext.define('Deppon.main.NavStore', {
	extend: 'Ext.data.TreeStore',
	root: {
		id: '0',
		text: login.i18n('dpap.login.system'),
		cls:lvl0Style,
		expanded: true,
		icon:noIcon
	},
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : '../login/loadTree.action',
		reader : {
			type : 'json',
			root : 'functions'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//定义一个导航树
Ext.define('Deppon.main.MainNav', {
	extend: 'Ext.tree.Panel',
	id:'mainNav',//菜单CSS Class以此id为准
	componentCls:'ye1',
	cls:'ye1',
	bodyCls:'ye1-body',
	width:186,
	store:Ext.create('Deppon.main.NavStore'),
	rootVisible:true,
	titleCollapse:true,
	//animate:true,
	useArrows:true,
	autoScroll:false,
	//添加标签页用
	addTab : function(Panel, tID, tText, tLoc){
		Panel.add({
			title: tText,
			tabConfig:{width:150},
			id: tID,
			layout:'fit',
			cls:'autoHeight',
			bodyCls:'autoHeight',
			// html:tabContent,
			closable:true,
			loader:{
				autoLoad:true,
				scripts:true,
				url:'../'+tLoc
			}
		});
		var newTab=Panel.child('#'+tID);
		Panel.setActiveTab(newTab);
	},
	//主导航展开菜单的handler,与主导航本身的handler类似。
	onMClick : function(arg){	
		//console.log('BBBBBBBB'+arg['id']);
		var TabID='T_';
		var tLoc='';
		for (var j=2; j<arg['id'].length; j++){
			TabID=TabID+arg['id'].charAt(j);
			if(arg['id'].charAt(j)=='-'){
				tLoc=tLoc+'-';
			}else{
				tLoc=tLoc+arg['id'].charAt(j);
			}
		}
		var Panel=Ext.getCmp('mainAreaPanel');
		var fTab=Panel.child('#'+TabID);
		if(fTab==null){
			this.addTab(Panel, TabID, arg['text'], tLoc);
		}else{
			Panel.show(fTab);
			Panel.setActiveTab(fTab);
		}		
	},
	initListeners : function(){
		var mainNav = this;
		mainNav.listeners = {
			//点击主菜单节点：
			itemclick: function(view, node, c, o, e) {
				//console.log(node.data.cls);
				//console.log(node.data.expandable);
				//console.log(node);
				//console.log(view);
				//console.log(c);
				//console.log(o);
				//console.log(e);
				var tabPanel = Ext.getCmp('mainAreaPanel');
				if(node.isLeaf()) {
					var hrefLength=node.data.href.length;
					var href=node.data.href;
					//确认是否使用全屏
					/*var strFlag=href.substring(hrefLength-11,hrefLength);
					//console.log(strFlag);
					if(isFullScreen=true){
						prevIsFullScreen=true;
					}else{
						prevIsFullScreen=false;
					}
					if(strFlag=='_fullScreen'){
						isFullScreen=true;
						//href=href.replace('_fullScreen','_fullScreen');
					}else{
						isFullScreen=false;
					}*/
					//console.log(node.data);
					
					//生成tab相关数据：
					var tID='T_';//tab ID
					var tLoc='';//html文件位置（不加'.html'）
					var h=0;//counter
					//生成tID和tLoc
					for (h=0; h<hrefLength; h++){
						if(href.charAt(h)!='#' && href.charAt(h)!='!'){
							if(href.charAt(h)!='/'){
								tID=tID+href.charAt(h);
							}else{
								tID=tID+'-';
							}
							tLoc=tLoc+href.charAt(h);
						}
					}
					tID = tID.substring(0, tID.lastIndexOf('.'));
					//console.log(tID);
					//确认该tab是否已经打开：
					var fTab=tabPanel.child('#'+tID);
					//var tabContent=null;
					//如果未打开则添加：
					if(fTab==null){
							mainNav.addTab(tabPanel, tID, node.data.text, tLoc);
					}else{
							tabPanel.show(fTab);
							tabPanel.setActiveTab(fTab);
					}
				//} else if(node.isExpanded()) {
				//	node.collapse();
				//如果expandable==false且不是leaf,则生成右侧展开菜单
				} else if(node.data.expandable==false){
					var me=node;
					//确认header高度，用来定位菜单Y轴位置。
					var frameTopHeight=Ext.getCmp('FrameTop').getHeight();
					//console.log(me.navMenu);
					var ic=0;//counter
					//如果没有可用的菜单则生成菜单：
					if (!me.navMenu) {
						me.navMenu = Ext.create('Ext.menu.Menu',{width: 180});
						for(ic==0; ic<node.data.children.length; ic++){
							//console.log('AAAAAAA'+node.data.children[ic].href);
							var mID='B_';//菜单项ID
							var h=0;
							for (h=0; h<node.data.children[ic].href.length; h++){
								if(node.data.children[ic].href.charAt(h)!='#' && node.data.children[ic].href.charAt(h)!='!'){
									if(node.data.children[ic].href.charAt(h)!='/'){
										mID=mID+node.data.children[ic].href.charAt(h);
									}else{
										mID=mID+'_';
									}
								}
							}
							me.navMenu.add({
											text:node.data.children[ic].text,
											id: mID,
											handler:mainNav.onMClick
											});
						}
					}
					//console.log(o);
					//console.log(30*o);
					
					//left为180, 根据index(o)和header高度计算top position
					me.navMenu.setPosition(180, 30*(o+1)+frameTopHeight+48, false );
					me.navMenu.show();
					//console.log(node);
					//console.log(me.navMenu);
				}else{
					//node.expand();
					view.toggle(node);
				}
			}
		};
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.initListeners();
		me.callParent([cfg]);
	}
});

/*---------------------------------工作区------------------------------------------*/	
//定义一个标签页
Ext.define('Deppon.main.TabPanel', {
	extend: 'Ext.tab.Panel',
	id:"mainAreaPanel",
	height:1500,
	columnWidth:1,
	plain:true,
	cls:'autoWidth',
	bodyCls:"tabFrame",
	componentCls:"mainArea",
	//右手overflow下拉插件
	plugins: [{
		ptype: 'tabscrollermenu',
		id:'OFB_menu',
		maxText  : 40,
		pageSize : 100
	},{
		ptype: 'tabclosemenu',
		closeTabText: login.i18n('dpap.login.closeTabText'),
		closeOthersTabsText: login.i18n('dpap.login.closeOthersTabsText'),
		closeAllTabsText: login.i18n('dpap.login.closeAllTabsText')
	}],
	items:[{
		title: login.i18n('dpap.login.homeLink'), 
		tabConfig:{width:150}, 
		closable:false,
		id:'T_home', 
		layout:'fit', 
		items:[
			//dummy, 工作区浮动工具条
			Ext.create('Ext.panel.Panel',{					 
				id:"T_home_toolbar",
				height:42,
				cls:'floatToolbar'
			})
		]
	}],
	//全屏模式下主导航关闭控制：
	fullScreenNav : function(c){
		//记录点击点
		var mouseX=c.browserEvent.clientX;
		var mouseY=c.browserEvent.clientY;
		//记录页面srollTop
		var sTop=window.pageYOffset|document.documentElement.scrollTop;
		//横向186未固定导航区域
		var areaX=186;
		//计算纵向区域,32为root本身的高度,勿做改动。
		var areaY=Ext.getCmp('mainNav').el.getY()-sTop+Ext.getCmp('mainNav').getHeight()+32;
		//console.log(mouseX+','+mouseY); 
		//console.log(Ext.getCmp('mainNav').getWidth());
		//console.log(Ext.getCmp('mainNav').getHeight());
		//console.log(Ext.getCmp('mainNav').el.getXY());
		//console.log('y relative to window: '+(Ext.getCmp('mainNav').el.getY()-sTop));
		//console.log('area in window (x*y): 186*'+(Ext.getCmp('mainNav').el.getY()-sTop+Ext.getCmp('mainNav').getHeight()));
		//如果超出区域则上收菜单
		if(mouseX>areaX||mouseY>areaY){
			Ext.getCmp('mainNav').getRootNode().collapse();
		}
	},
	initListeners : function(){
		var me = this;
		me.listeners = {
			//关闭前清内存里相应项目
			beforeremove: function(panel, tab){
				
				var conId = tab.id+'_content',
					toolbarId = tab.id+'_toolbar';
				//清空几个root element events
				Ext.EventManager.removeAll(conId);
				Ext.EventManager.removeAll(toolbarId);
				//判断是否存在tab的主页面，如果不存在就无需清理
				if(Ext.getCmp(conId)==null){
					return;
				}
				
				//回收模块下变量和方法
				var moduleName = conId.substring(2, conId.indexOf("-", 2));
				var childmoduleName = conId.substring(conId.lastIndexOf("-")+1, conId.length-8);
				var module = eval(moduleName);
				if(childmoduleName == "index") {
					if(module != null) {
						 for(var prop in module) {
							 if(!Ext.isObject(prop)) {
								module[prop] = null;
							 }
						 }
						 module = null;
					}
				} else {
					var childmodule = module[childmoduleName.slice(0, -5)];
					 for(var prop in childmodule) {
						 if(!Ext.isObject(prop)) {
							 childmodule[prop] = null;
						 }
					 }
					 childmodule = null;
				}
				
				//清空ComponentManager内的注册项
				var cmpArray = Ext.getCmp(conId).removeAll();
				//清空store
				for(var i=0;i<cmpArray.length;i++){
					if(cmpArray[i].store){
						cmpArray[i].store.destroyStore();
						Ext.data.StoreManager.unregister(cmpArray[i].store);
					}
				}
				cmpArray = null;
				//清空几个root element
				Ext.ComponentManager.unregister(Ext.getCmp(conId));
				if(Ext.getCmp(toolbarId)!=null){
					Ext.getCmp(toolbarId).removeAll()==null;
					Ext.ComponentManager.unregister(Ext.getCmp(toolbarId));
				}
				
			},
			//切换及激活标签页前进行的调整：
			beforetabchange: function(panel, tab){
				var logoSpace = Ext.getCmp('tabPageLogo');
				scroll(0,0);
				//console.log(tab.id);
				//确认是否使用全屏排版
				var strFlag=tab.id.substring(tab.id.length-11,tab.id.length);
				//console.log(strFlag);
				if(isFullScreen==true){
					prevIsFullScreen=true;
				}else{
					prevIsFullScreen=false;
				}
				if(strFlag=='_fullScreen'){
					isFullScreen=true;
				}else{
					isFullScreen=false;
				}
				//找取通知版面右上悬浮备用
				var aN=Ext.getCmp('announceNotice');
				//找取header备用
				var tP=Ext.getCmp('FrameTop');
				//console.log('activating tab '+tab.title);
				//如果切换到主页
				if (tab.id == 'T_home'){
					//悬浮框下移。
					if(aN!=null){
						aN.animate({
							   to:{
								   y:150
								   }
							   });
					}
					//header展开
					tP.animate({
							   to:{
								   height:102
								   }
							   });
					//删除左上小logo;
					logoSpace.removeCls('tabPageLogo');
					//toptoolbar.doLayout();
				//一般页面：
				}else{
					//悬浮框上移
					if(aN!=null){
						aN.animate({
							   to:{
								   y:48
								   }
							   });
						//aN.el.dom.style.top='48px';
					}
					//console.log('hidding frameTop');
					//header隐藏：
					tP.animate({
							   to:{
								   height:0
								   }
							   });
					//添加左上小logo:
					logoSpace.addCls('tabPageLogo');
					//toptoolbar.doLayout();
				}
				//找取通知版面备用
				var aNC=Ext.getCmp('announce_panel_Cont');
				var mN=Ext.getCmp('mainNav');
				//全屏模式下：
				if(isFullScreen==true){
					//主导航宽度归0，使tabPanel左移动（主导航依然显示，因为底层element overflow=visible）
					mN.el.dom.style.width=0;
					//上收到root
					mN.getRootNode().collapse();
					//通知版面拓宽,tabPanel拓宽
					//console.log('cur'+isFullScreen);
					//console.log('prev'+prevIsFullScreen);
					if(prevIsFullScreen==false){
						aNC.setWidth(aNC.getWidth()+186);												
						me.setWidth(me.getWidth()+186);
					}
					aNC.doLayout();
					me.doLayout();
					//tabBar向右移动
					me.tabBar.addClass('tabbarDisplace');
					//主工作区container float定义清空，用浏览器默认。
					Ext.getCmp('container').addClass('clearFloat');
					//添加fullScreenNav handler，在全屏模式下点击主导航外区域会自动关闭主导航。
					Ext.getBody().on('click', me.fullScreenNav);
				//一般模式
				}else{
					//移除fullScreenNav handler以及相关listener
					Ext.getBody().removeListener('click', me.fullScreenNav);
					//重设主导航宽度
					mN.setWidth(186);
					mN.doLayout;
					//主导航展开
					mN.getRootNode().expand();
					//重设tabPanel宽度（因ExtJS自身的宽度计算机制，这里不做减法。）
					me.setWidth(me.getWidth());
					me.doLayout();
					//重设通知版面宽度
					if(prevIsFullScreen==true){
						aNC.setWidth(aNC.getWidth()-186);
						aNC.doLayout();
					}
					//tabBar归位
					me.tabBar.removeCls('tabbarDisplace');
					//主工作区归位
					Ext.getCmp('container').removeCls('clearFloat');
				}
			}
		};
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.initListeners();
		me.callParent([cfg]);
	}
});

/*--------------------------------框架整合-----------------------------------------*/
Ext.application({
    name: 'DpFoss',
    appFolder: '..',
    launch: function() {
		Ext.QuickTips.init();
		//页面的头部
		var topPanel = Ext.create('Deppon.main.topPanel'),
		//导航树
		mainNav = Ext.create('Deppon.main.MainNav'),
		//通知面板
		announcePanel_Cont = Ext.create('Deppon.main.AnnouncePanel_Cont'),
		//工作区标签页
		tabPanel = Ext.create('Deppon.main.TabPanel'),
		//页脚
		footer=Ext.create('Ext.panel.Panel',{
			id:'footer',
			columnWidth:1,
			html:'copyright 2004-2012, All Right Reservered',
			height:30
		 }),
		//右上“您有一条新未读通知”悬浮框，在非即时必读通知时使用。
		announceNotice=Ext.create('Ext.panel.Panel',{
			id:'announceNotice',
			width:170,
			height:62,
			//隐藏
			hidden: true, 
			closable:true,
			html:'您有一条新的<a onclick="javascript:openAnnounce(); "><b>未读通知</b></a>'
		});
        Ext.create('Ext.container.Viewport', {
			id:'dpViewport',
			listeners:{
				//渲染结束后开始右上时间更新。每一秒更新一次。
				afterrender : function(){
					setInterval(
						function(){
							Ext.getCmp('dateTimeSpace').update(constructDateTime());
						}
						,1000
					);
				}
			},
			minWidth:1220,
			autoScroll: false,
            layout: 'auto',
			items: [topPanel,
				{	xtype:'panel',
					id:'col_cont',
					cls:'autoHeight',
					bodyCls:'autoHeight',
					layout: 'column',
					
					items:[
						   		mainNav,
						   		{	xtype:'panel',
									id:'container',
									cls:'autoHeight',
									bodyCls:'autoDim',
									columnWidth:1,
									items: [announcePanel_Cont,tabPanel,footer,announceNotice]
								}
							]
				
				}
			 ]
        });
		//添加滚动事件监控(因浏览器兼容缘故，window和document上都要添加)
		document.onscroll=onScroll;
		window.onscroll=onScroll;
    }
});
