//通过词编码、值编码查询对应的值名称
baseinfo.queryValueName = function(termsCode,valueCode){
	var data=FossDataDictionary.getDataByTermsCode(termsCode);
	var map = new Ext.util.HashMap();
	for(var i=0;i<data.length;i++){
		map.add(data[i].valueCode,data[i].valueName);
	}
	return map.get(valueCode);
}
/**
 * 保存修改表单信息操作方法
 */
baseinfo.bigcusDeliveryAddress.save =function(updateForm){
	if(!updateForm.getForm().isValid()){
		return;
	}
	var msgTip=baseinfo.bigcusDeliveryAddress.i18n('foss.baseinfo.updateSuccessful');
	// 当前表单中的数据：
	var bigcusDeliveryAddressObj = updateForm.getForm().getValues(); 
	var bigcusDeliveryAddressindex = Ext.getCmp("T_baseinfo-bigcusDeliveryAddress_content");
	var bigcusDeliveryAddressTree = bigcusDeliveryAddressindex.getRegionsLeftPannel().getBigcusDeliveryAddressTree();
    var bigcusDeliveryAddressTreeStore = bigcusDeliveryAddressTree.getStore();
    //把值封装给model
	var model =new Foss.baseinfo.bigcusDeliveryAddress.DistrictModel(bigcusDeliveryAddressObj);
	var params={'vo':{'bigcusDeliveryAddressEntity':model.data}};
	Ext.Ajax.request({
		jsonData:params,
		url:baseinfo.realPath('updateBigcusDeliveryAddress.action'),
		success:function(response){
			var json =Ext.decode(response.responseText);
			Ext.Msg.alert(baseinfo.bigcusDeliveryAddress.i18n('foss.baseinfo.tips'), msgTip);
			//若是点击查询详细修改的话，要更新详细信息
			if(updateForm.clickFromDetail){
				baseinfo.bigcusDeliveryAddress.treeNodeOperator(null,model.data.code);
			}else{
				baseinfo.bigcusDeliveryAddress.pagingBar.moveFirst();
			}
			bigcusDeliveryAddressindex.getWindow().hide();
		},
		exception:function(response){
			var json =Ext.decode(response.responseText);
			Ext.Msg.alert(baseinfo.bigcusDeliveryAddress.i18n('foss.baseinfo.tips'), json.message);
		}
	});
};

//树节点单击操作
baseinfo.bigcusDeliveryAddress.treeNodeOperator=function(me,a_code){
	// 如果是根结点，点击时不响应
	if(a_code =='01'){
		return ;
	}
	var params = {
			"vo.bigcusDeliveryAddressEntity.code":a_code 
		};
	Ext.Ajax.request({
		url : baseinfo.realPath('queryBigcusDeliveryAddressByCode.action'),
		params:params,
		async: true,   //ASYNC 是否异步( TRUE 异步 , FALSE 同步)
		success : function(response) {    								 
			json = Ext.decode(response.responseText);
			var entity = json.vo.bigcusDeliveryAddressEntity;
			var bigcusDeliveryAddressindex = Ext.getCmp("T_baseinfo-bigcusDeliveryAddress_content");
			var bigcusDeliveryAddressRightPanel = bigcusDeliveryAddressindex.getRegionsRightPannel();
			var bigcusDeliveryAddressRightQueryResultPannel = bigcusDeliveryAddressRightPanel.getBigcusDeliveryAddressQueryResultPanel();
			var detailForm = bigcusDeliveryAddressRightPanel.getBigcusDeliveryAddressDetailForm(); 
			//详情窗口可见，列表隐藏
			detailForm.setVisible(true);
			bigcusDeliveryAddressRightQueryResultPannel.setVisible(false);
			var districtModel = new Foss.baseinfo.bigcusDeliveryAddress.DistrictModel(entity);
			//把值设置给详情表单中的regionsModel
			detailForm.regionsModel = districtModel;
			detailForm.getForm().loadRecord(districtModel);
			detailForm.getForm().findField('deliverySalesDeptCode').setCombValue(entity.deliverySalesDeptName,entity.deliverySalesDeptCode);
		}
	});
};

/**
 * 
***************************派送区域界面数据模型*******************************************
*/
Ext.define('Foss.baseinfo.bigcusDeliveryAddress.DistrictModel',{
	extend:'Ext.data.Model',
	fields:[
		//ID
		{name:'id',type:'string'},
		//行政区域编码
		{name:'code',type:'string'},
		//区域名称
		{name:'name',type:'string'},
		//上级行政区域编码
		{name:'parentDistrictCode',type:'string'},
		//上级行政区域名称
		{name:'parentDistrictName',type:'string'},
		//虚拟行政区域
		{name:'virtualDistrictId',type:'string'},
		//行政区域级别
		{name:'degree',type:'string'},
		//派送加时
		{name:'deliveryAddTime',type:'string'},
		//派送网点类型
		{name:'deliveryType',type:'string'},
		//超派费用
		{name:'muchHigherDelivery',type:'string'},
		//派送营业部编码
		{name:'deliverySalesDeptCode',type:'string'},
		//派送营业部名称
		{name:'deliverySalesDeptName',type:'string'},
		//派送备注
		{name:'deliveryRemark',type:'string'},
		//是否启用
		{name:'active',type:'string'},
		//创建时间
		{name:'createTime',type:'date'},
		//修改时间
		{name:'modifyTime',type:'date'},
		//创建人
		{name:'createUserCode',type:'string'},
		//更新人
		{name:'modifyUserCode',type:'string'},
		//是否叶子结点（用于派送属性是否可改）
		{name:'isLeaf',type:'string'}
		]
});
/**
 * *******************************左侧面板-定义派送行政区域TreeStore*********************
 */
Ext.define('Foss.baseinfo.bigcusDeliveryAddress.BigcusDeliveryAddressTreeStore',{
  	extend: 'Ext.data.TreeStore', 
  	root: {
		//根的文本信息
		text:'行政区域',
		//设置根节点的ID
		id : '01',
		//根节点是否展开
		expanded: true
	},
	//代理用来实现tree的展开事件
  	proxy: {
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath("queryBigcusDeliveryAddressByParentDistrictCode.action"),
        reader: {
            type: 'json',
            root: 'nodes'
        }
  	},
  	//发送参数名，值为节点ID
  	nodeParam: 'vo.bigcusDeliveryAddressEntity.parentDistrictCode'
});    
/**
 * 左侧面板-定义行政区域Tree
 */
Ext.define('Foss.baseinfo.bigcusDeliveryAddress.BigcusDeliveryAddressTree',{
    extend:'Ext.tree.Panel',
    title: '零担行政区域',
    margin: false,
    cls: 'autoHeight',
	bodyCls: 'autoHeight',
    autoScroll: true,
    animate: false,
    useArrows: true,
    frame: false,
    rootVisible: true,
    // 树要给高度才有滚动条
    height: 650,
    columnWidth: 1,
    store: Ext.create('Foss.baseinfo.bigcusDeliveryAddress.BigcusDeliveryAddressTreeStore'),
    defaults: {
		margin:'5 5 0 5'
	},
  	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
	    //监听鼠标事件
	    me.listeners = {
	      //左键单击
			itemclick:function(node,record,item,index,e){
				//阻止浏览器默认行为处理事件
				e.preventDefault();
				var a_code =record.data.id;
				baseinfo.bigcusDeliveryAddress.treeNodeOperator(me,a_code); 
			},
	      //右键单击
	      itemcontextmenu:function(node,record,item,index,e){
	        e.preventDefault();
	      }
	    };
		me.callParent([cfg]);
	}
});  
/**
 * 左侧面板--封装树面板
 */
Ext.define('Foss.baseinfo.bigcusDeliveryAddress.bigcusDeliveryAddressLeftPannel',{
	extend:'Ext.panel.Panel',
	height: 680,
	frame: true,
	layout:'column',
	columnWidth: 0.25,
	defaultType : 'textfield',
	//定义组织树方法
	bigcusDeliveryAddressTree: null,
	getBigcusDeliveryAddressTree: function(){
		if(this.bigcusDeliveryAddressTree == null){
			this.bigcusDeliveryAddressTree = Ext.create('Foss.baseinfo.bigcusDeliveryAddress.BigcusDeliveryAddressTree');
		}
		return this.bigcusDeliveryAddressTree;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [me.getBigcusDeliveryAddressTree()];  
		me.callParent([cfg]);
	}
});
/**
 * 右边模块-查询条件面板
 */
Ext.define('Foss.baseinfo.bigcusDeliveryAddress.BigcusDeliveryAddressQueryForm',{
	extend:'Ext.form.Panel',  
	layout: {
		type : 'table',
		columns : 3
	},
	frame : true,
	title: baseinfo.bigcusDeliveryAddress.i18n('foss.baseinfo.queryCondition'),
	defaults: {
		readOnly : false,
		margin:'5 5 5 5',
		anchor: '100%',
		labelWidth : 100,
		width : 100
	},
	height : 140,
	defaultType : 'textfield',
	layout : 'column',
	items : [{
			name:'name',
			fieldLabel:'区域名称',//区域名称
			columnWidth : 0.45,
			labelWidth : 70
		},{
			name:'deliveryType',
			columnWidth : 0.55,
			labelWidth : 100,
			fieldLabel:'派送网点类型', //派送网点类型
			margin:'5 5 5 40',
			xtype:'combobox',
			valueField:'valueCode',
			displayField:'valueName',
			queryMode:'local',
			value:'',
			store:FossDataDictionary.getDataDictionaryStore('DELIVERY_TYPE',null,{
				'valueCode':'',
				'valueName':baseinfo.bigcusDeliveryAddress.i18n('foss.baseinfo.all')
			})
		},{
			xtype:'container',
			columnWidth:1,
			colspan:3,
			margin:'15 5 5 5',
			defaultType:'button',
			layout:'column',
			items:[{
				  text: baseinfo.bigcusDeliveryAddress.i18n('foss.baseinfo.reset'),//重置   
				  disabled:!baseinfo.bigcusDeliveryAddress.isPermission('bigcusDeliveryAddress/queryButton'),
				  hidden:!baseinfo.bigcusDeliveryAddress.isPermission('bigcusDeliveryAddress/queryButton'),
				  columnWidth:.25,
				  handler: function() {
						this.up('form').getForm().reset();
					}
			  	},{
					xtype:'container',
					html:'&nbsp;',
					columnWidth:.5
				},{
				  text: baseinfo.bigcusDeliveryAddress.i18n('foss.baseinfo.query'),//查询
				  disabled:!baseinfo.bigcusDeliveryAddress.isPermission('bigcusDeliveryAddress/queryButton'),
				  hidden:!baseinfo.bigcusDeliveryAddress.isPermission('bigcusDeliveryAddress/queryButton'),
				  columnWidth:.25,
				  cls:'yellow_button',
				  //查询按钮的响应事件：
				  handler: function() { 
					  	var bigcusDeliveryAddressindex = Ext.getCmp("T_baseinfo-bigcusDeliveryAddress_content");
						var bigcusDeliveryAddressRightPanel = bigcusDeliveryAddressindex.getRegionsRightPannel();
						var bigcusDeliveryAddressDetailFormPannel = bigcusDeliveryAddressRightPanel.getBigcusDeliveryAddressDetailForm();
						var queryResultPannel = bigcusDeliveryAddressRightPanel.getBigcusDeliveryAddressQueryResultPanel();
						bigcusDeliveryAddressDetailFormPannel.setVisible(false);
						queryResultPannel.setVisible(true);
						baseinfo.bigcusDeliveryAddress.pagingBar.moveFirst();
					}
			  	}]
		}]
});
/**
 * 右边模块-查询结果查询的显示表格Grid 
 */
Ext.define('Foss.baseinfo.bigcusDeliveryAddress.QueryResultGrid',{
	extend: 'Ext.grid.Panel', 
	cls:'autoHeight',
	bodyCls:'autoHeight',
	columnWidth:1, 
	// 设置表格不可排序
	sortableColumns:false,
	// 去掉排序的倒三角
	enableColumnHide:false,
	// 设置“如果查询的结果为空，则提示”
	emptyText: baseinfo.bigcusDeliveryAddress.i18n('foss.baseinfo.queryResultIsNull'),
	//增加滚动条
	autoScroll : true,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	store : null,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	//表格行可展开的插件
	columns : [{
          xtype:'actioncolumn',
          flex: 1,
          text: baseinfo.bigcusDeliveryAddress.i18n('foss.baseinfo.operate'),//操作
          align: 'center',
          items: [{
	          	  iconCls:'deppon_icons_edit',
	              tooltip: baseinfo.bigcusDeliveryAddress.i18n('foss.baseinfo.update'),//修改
	              disabled:!baseinfo.bigcusDeliveryAddress.isPermission('bigcusDeliveryAddress/updateButton'),
	              handler: function(grid, rowIndex, colIndex) {
	              	//获取得到Model
	            	var rowInfo=grid.getStore().getAt(rowIndex);
	            	var districtModel =new Foss.baseinfo.bigcusDeliveryAddress.DistrictModel(rowInfo.data);
	            	//获取修改窗口表单
	            	var window=Ext.getCmp("T_baseinfo-bigcusDeliveryAddress_content").getWindow();
	            	var updateForm =window.getDictMidifyUpdateForm();
	    			//给表格赋值
	            	updateForm.getForm().loadRecord(districtModel);
	            	updateForm.getForm().findField('deliverySalesDeptCode').setRawValue(districtModel.get('deliverySalesDeptName'));
	            	//把model传给表格，用于重置
	            	updateForm.dictModifyModel =districtModel;
	            	updateForm.clickFromDetail =false;
	            	//将4个派送字段设为可修改
	            	updateForm.getForm().findField('deliveryAddTime').setReadOnly(false);
	            	updateForm.getForm().findField('deliverySalesDeptCode').setReadOnly(false);
	            	updateForm.getForm().findField('deliveryType').setReadOnly(false);
	            	updateForm.getForm().findField('muchHigherDelivery').setReadOnly(false);
	    			updateForm.getForm().findField('deliveryRemark').setReadOnly(false);
	            	if(districtModel.data.isLeaf=='N'){//如果不是叶子结点，则不可以改派送类型
	            		updateForm.getForm().findField('deliveryType').setReadOnly(true);
	            		updateForm.getForm().findField('muchHigherDelivery').setReadOnly(true);
	            	}
	    			//将修改按钮（取消、重置、保存）设为可见，将查看按钮（关闭）设为隐藏
		    		var buttons=updateForm.items.get(9).items;
		            buttons.get(0).setVisible(true);
		            buttons.get(1).setVisible(true);
		            buttons.get(2).setVisible(true);
		            buttons.get(3).setVisible(false);
	            	//显示窗口
	            	window.show();
	              }
              	},{
	          	  iconCls:'deppon_icons_showdetail',
	              tooltip:'查看',//查看
	              disabled:!baseinfo.bigcusDeliveryAddress.isPermission('bigcusDeliveryAddress/queryButton'),
	              handler: function(grid, rowIndex, colIndex) {
	              	//获取得到Model
	            	var rowInfo=grid.getStore().getAt(rowIndex);
	            	var districtModel =new Foss.baseinfo.bigcusDeliveryAddress.DistrictModel(rowInfo.data);
	            	//获取修改窗口表单
	            	var window=Ext.getCmp("T_baseinfo-bigcusDeliveryAddress_content").getWindow();
	            	var updateForm =window.getDictMidifyUpdateForm();
	    			//给表格赋值
	            	updateForm.getForm().loadRecord(districtModel);
	            	updateForm.getForm().findField('deliverySalesDeptCode').setCombValue(districtModel.get('deliverySalesDeptName'),districtModel.get('deliverySalesDeptCode')); //营业部选择器
	            	//将4个派送字段设为只读
	            	updateForm.getForm().findField('deliveryAddTime').setReadOnly(true);
	            	updateForm.getForm().findField('deliverySalesDeptCode').setReadOnly(true);
	            	updateForm.getForm().findField('deliveryType').setReadOnly(true);
	            	updateForm.getForm().findField('muchHigherDelivery').setReadOnly(true);
	    			updateForm.getForm().findField('deliveryRemark').setReadOnly(true);
	    			//将修改按钮（取消、重置、保存）设为隐藏，将查看按钮（关闭）设为可见
	    			var buttons=updateForm.items.get(9).items;
		            buttons.get(0).setVisible(false);
		            buttons.get(1).setVisible(false);
		            buttons.get(2).setVisible(false);
		            buttons.get(3).setVisible(true);
	            	//显示窗口
	            	window.show();
	              }
              	}]
		},{
			text : '编码',//编码
			align: 'center',
			flex: 1,
			dataIndex :'code'  
		},{
			text: '上级行政区域',//上级行政区域
			align: 'center',
			flex: 1,
			xtype: 'ellipsiscolumn',
			dataIndex: 'parentDistrictName'
		},{
			text : '区域名称',//区域名称
			align: 'center',
			flex: 1,
			xtype: 'ellipsiscolumn',
			dataIndex : 'name'  
		},{
			text: '行政区域级别', //行政区域级别
			align: 'center',
			flex: 1,
			xtype: 'ellipsiscolumn',
			dataIndex: 'degree',
			renderer: function(value) {
				return baseinfo.queryValueName('DISTRICT_DEGREE',value);
			}
		},{
			text:'派送加时',//派送加时
			align:'center',
			flex:1,
			xtype:'ellipsiscolumn',
			dataIndex:'deliveryAddTime'
		},{
			text:'派送营业部',//派送营业部
			align:'center',
			flex:1,
			xtype:'ellipsiscolumn',
			dataIndex:'deliverySalesDeptName'
		},{
			text:'派送网点类型',//派送网点类型
			align:'center',
			flex:1,
			xtype:'ellipsiscolumn',
			dataIndex:'deliveryType',
			renderer: function(value) {
				return baseinfo.queryValueName('DELIVERY_TYPE',value);
			}
		},{
			text:'超派费用',//派送营业部
			align:'center',
			flex:1,
			xtype:'ellipsiscolumn',
			dataIndex:'muchHigherDelivery'
		},{
			text:'派送备注',//派送备注
			align:'center',
			flex:1.5,
			xtype:'ellipsiscolumn',
			dataIndex:'deliveryRemark'
		}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		var r_url = "queryBigcusDeliveryAddressList.action";
		r_url=	baseinfo.realPath(r_url) ;
		me.store = Ext.create('Ext.data.Store',{
			model: 'Foss.baseinfo.bigcusDeliveryAddress.DistrictModel',
			pageSize:10,
			autoLoad: false,
			proxy: {
				type: 'ajax',
				actionMethods: 'POST',
				url : r_url,
				//定义一个读取器
				reader: {
					type: 'json',
					root: 'vo.bigcusDeliveryAddressList',
					totalProperty : 'totalCount'
				}
			},
			constructor: function(config){
				var me = this,
					cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			},
			listeners: {
				beforeload : function(store, operation, eOpts) {
					var bigcusDeliveryAddressindex = Ext.getCmp("T_baseinfo-bigcusDeliveryAddress_content");
					var bigcusDeliveryAddressRightPanel = bigcusDeliveryAddressindex.getRegionsRightPannel();
					var queryForm = bigcusDeliveryAddressRightPanel.getBigcusDeliveryAddressQueryForm();
					if (queryForm != null) {
						var queryParams = queryForm.getValues();
						Ext.apply(operation, {
							params : {
								'vo.bigcusDeliveryAddressEntity.name':queryParams.name,
								'vo.bigcusDeliveryAddressEntity.deliveryType':queryParams.deliveryType
							}
						});
					}
				}
			}
		});
		me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store
			});
		baseinfo.bigcusDeliveryAddress.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});
//详情表格
Ext.define('Foss.baseinfo.bigcusDeliveryAddress.DetailForm',{
	extend: 'Ext.form.Panel',
	title: '区域详情',
	frame: true,
	defaultType : 'textfield',
	columnWidth: 1, 
	regionsModel:null, //定义一个model
	layout:{
		type : 'column'
	},	
	defaults: {
		readOnly : true, 
		margin:'5 20 5 10',
		labelWidth: 120,
		columnWidth: 0.5
	}, 
	items:[{
	   		name: 'code',
	   		
	   		fieldLabel: baseinfo.bigcusDeliveryAddress.i18n('foss.baseinfo.code')//编码
	     },{
	   		name: 'name',

	   		fieldLabel:'区域名称' //区域名称
	     },{
	    	 name:'deliveryAddTime',
	    	 fieldLabel:'派送加时',//派送加时
	    	 xtype:'combo',
	    	 queryMode: 'local',
		     valueField: 'value',
	    	 displayField: 'name',
		     editable:false,//禁止手动修改
		     value:'0',
	    	 store : Ext.create('Ext.data.Store', {
			    fields:['value','name'],
			    data:[{'value':'0','name':'0'},
			      		{'value':'0.5','name':'0.5'},
			      		{'value':'1','name':'1'},
			      		{'value':'1.5','name':'1.5'},
			      		{'value':'2','name':'2'},
			      		{'value':'2.5','name':'2.5'},
			      		{'value':'3','name':'3'},
			      		{'value':'3.5','name':'3.5'},
			      		{'value':'4','name':'4'},
			      		{'value':'4.5','name':'4.5'},
			      		{'value':'5','name':'5'}
		      		   ]})
	     },{
	   		name: 'parentDistrictName',
	   		fieldLabel: baseinfo.bigcusDeliveryAddress.i18n('foss.baseinfo.upDistrict')  //上级行政区域
	     },{
	   		name: 'deliverySalesDeptCode',
	   		fieldLabel: '派送营业部', //派送营业部
	   		xtype:'dynamicorgcombselector',
	   		salesDepartment:'Y'
	     },{
			name:'deliveryType',
			fieldLabel:'派送网点类型',//派送网点类型
			xtype:'combo',
			queryMode:'local',
			valueField: 'valueCode',
			displayField: 'valueName',
			editable:false,
			store:FossDataDictionary.getDataDictionaryStore('DELIVERY_TYPE',null,null,null)
		},{
	   		name: 'degree',
	   	    fieldLabel: baseinfo.bigcusDeliveryAddress.i18n('foss.baseinfo.districtLevel'), //行政区域级别
			xtype:'combo',
			queryMode:'local',
			valueField: 'valueCode',
			displayField: 'valueName',
			editable:false,
			store:FossDataDictionary.getDataDictionaryStore('DISTRICT_DEGREE')
	   	},{
			name:'muchHigherDelivery',
			fieldLabel:'超派费用',//超派费用
			queryMode:'local',
			valueField: 'valueCode',
			displayField: 'valueName',
			editable:false,
			store:FossDataDictionary.getDataDictionaryStore('MUCHHIGHER_DELIVERY')
		},{
	   		border: 1,
	   		name:'deliveryRemark',
	   		fieldLabel:'派送备注',
	   		xtype:'textarea',
	   		//width:500,
	   		columnWidth: 1,
	   		listeners:{
					blur:function(_this,theEvent,eOpts){
						if(!Ext.isEmpty(_this.value)){
							var value =_this.value.trim();
							if(value.length>666){
								Ext.Msg.alert(baseinfo.bigcusDeliveryAddress.i18n('foss.baseinfo.tips'), '字数已超过666字，已自动帮你截取');
								_this.value =value.substring(0,666);
							}
						}
						_this.setValue(_this.value);
					}
				}
	   	},{
			border: 1,
			xtype:'container',
			colspan:2,
			columnWidth:1, 
			defaultType:'button',
			layout:'column',
			items:[{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.75
				},{ 
					text: baseinfo.bigcusDeliveryAddress.i18n('foss.baseinfo.update'), //修改
					columnWidth:.25,
					name: 'detailUpdateButton',
					disabled:!baseinfo.bigcusDeliveryAddress.isPermission('bigcusDeliveryAddress/updateButton'),
					hidden:!baseinfo.bigcusDeliveryAddress.isPermission('bigcusDeliveryAddress/updateButton'),
					cls:'yellow_button',
					handler: function(){
						//详情表格
						var detailForm=Ext.getCmp("T_baseinfo-bigcusDeliveryAddress_content").getRegionsRightPannel().getBigcusDeliveryAddressDetailForm();
						if(Ext.isEmpty(detailForm.getForm().findField('code').getValue())){
							Ext.Msg.alert(baseinfo.bigcusDeliveryAddress.i18n('foss.baseinfo.tips'), 
									baseinfo.bigcusDeliveryAddress.i18n('foss.baseinfo.choseDistrict'));
							return;
						}
						//窗口表格
						var window=Ext.getCmp("T_baseinfo-bigcusDeliveryAddress_content").getWindow();
						var updateForm =window.getDictMidifyUpdateForm();
		    			//给表格赋值
		            	updateForm.getForm().loadRecord(detailForm.regionsModel);
		          		updateForm.getForm().findField('deliverySalesDeptCode').setRawValue(detailForm.regionsModel.get('deliverySalesDeptName'));
		            	//把model传给表格，用于重置
		            	updateForm.dictModifyModel =detailForm.regionsModel;
		            	//是否从详情表格点击，用于判断修改成功后的刷新对象
		            	updateForm.clickFromDetail =true;
		            	//将4个派送字段设为可修改
		            	updateForm.getForm().findField('deliveryAddTime').setReadOnly(false);
		            	updateForm.getForm().findField('deliverySalesDeptCode').setReadOnly(false);
		            	updateForm.getForm().findField('deliveryType').setReadOnly(false);
		            	updateForm.getForm().findField('muchHigherDelivery').setReadOnly(false);
		    			updateForm.getForm().findField('deliveryRemark').setReadOnly(false);
		            	if(detailForm.regionsModel.data.isLeaf=='N'){//如果不是叶子结点，则不可以改派送类型
		            		updateForm.getForm().findField('deliveryType').setReadOnly(true);
		            		updateForm.getForm().findField('muchHigherDelivery').setReadOnly(true);
		            	}
		    			//将修改按钮（取消、重置、保存）设为可见，将查看按钮（关闭）设为隐藏
		    			var buttons=updateForm.items.get(9).items;
		            	buttons.get(0).setVisible(true);
		            	buttons.get(1).setVisible(true);
		            	buttons.get(2).setVisible(true);
		            	buttons.get(3).setVisible(false);
		            	//显示窗口
		            	window.show();
					}
			  	}]
		}],
	   initComponent: function(config){
	   		var me = this,
			cfg = Ext.apply({}, config);
	   		me.callParent([cfg]);
	   }
});






//窗口表格
Ext.define('Foss.baseinfo.bigcusDeliveryAddress.DictModifyForm',{
	extend: 'Ext.form.Panel',
	title: '行政区域',
	frame: true,
	hidden : false,
	defaultType : 'textfield',
	layout:'column',
	columnWidth: 0.99,
	defaults: {
		readOnly : true,
		margin:'5 5 5 5',
		anchor: '90%',
		columnWidth: 0.5,
		labelWidth: 90
	},
	operatorType:null,
	initFormFlag:null,
	dictModifyModel:null,
	//若是直接点击详细信息上面的修改的话，为true
	clickFromDetail:false,
	initComponent: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [{
				 name: 'code',
				 fieldLabel: baseinfo.bigcusDeliveryAddress.i18n('foss.baseinfo.code')//编码
			},{
				 name: 'name',
				 fieldLabel: '区域名称'//区域名称
			},{
		    	 name:'deliveryAddTime',
		    	 fieldLabel:'派送加时',//派送加时
		    	 xtype:'combo',
		    	 queryMode: 'local',
			     valueField: 'value',
		    	 displayField: 'name',
			     editable:false,//禁止手动修改
			     value:'0',
		    	 store : Ext.create('Ext.data.Store', {
				    fields:['value','name'],
				    data:[{'value':'0','name':'0'},
				      		{'value':'0.5','name':'0.5'},
				      		{'value':'1','name':'1'},
				      		{'value':'1.5','name':'1.5'},
				      		{'value':'2','name':'2'},
				      		{'value':'2.5','name':'2.5'},
				      		{'value':'3','name':'3'},
				      		{'value':'3.5','name':'3.5'},
				      		{'value':'4','name':'4'},
				      		{'value':'4.5','name':'4.5'},
				      		{'value':'5','name':'5'}
			      		   ]})
			},{
				name:'parentDistrictName',
				fieldLabel : baseinfo.bigcusDeliveryAddress.i18n('foss.baseinfo.upDistrict') //上级行政区域
			},{
		   		name: 'deliverySalesDeptCode',
		   		fieldLabel: '派送营业部', //派送营业部
		   		xtype:'dynamicorgcombselector',
		   		salesDepartment:'Y'
			},{
				name:'deliveryType',
				fieldLabel:'派送网点类型',//派送网点类型
				xtype:'combo',
				queryMode:'local',
				valueField: 'valueCode',
				displayField: 'valueName',
				editable:false,
				store:FossDataDictionary.getDataDictionaryStore('DELIVERY_TYPE',null,null,['own_whole_delivery','px_whole_delivery','no_delivery','muchHigher_delivery'])
			},{
		   		name: 'degree',
		   	    fieldLabel: baseinfo.bigcusDeliveryAddress.i18n('foss.baseinfo.districtLevel'), //行政区域级别
				xtype:'combo',
				queryMode:'local',
				valueField: 'valueCode',
				displayField: 'valueName',
				editable:false,
				store:FossDataDictionary.getDataDictionaryStore('DISTRICT_DEGREE')
				
		   	},{
		   		name:'muchHigherDelivery',
		   		fieldLabel: "超派费用",
		   		readOnly:false,
		   		decimalPrecision: 2, // 精确地小数点后两位
                allowDecimals: true,
                regex:/^([1-9]\d*|0)(\.\d{1,2})?$/,//验证来规则
                nanText: "请输入有效的小数,例:99.99",
                maxLength:7,//能够输入的内容的最大长度
                maxLengthText:"超过最大长度!"
			},{
				name:'deliveryRemark',
				xtype:'textarea',
				fieldLabel:'派送备注',//派送备注
				columnWidth:1,
				listeners:{
					blur:function(_this,theEvent,eOpts){
						if(!Ext.isEmpty(_this.value)){
							var value =_this.value.trim();
							if(value.length>666){
								Ext.Msg.alert(baseinfo.bigcusDeliveryAddress.i18n('foss.baseinfo.tips'), '字数已超过666字，已自动帮你截取');
								_this.value =value.substring(0,666);
							}
						}
						_this.setValue(_this.value);
					}
				}
			},{
				xtype:'container', 
				defaultType:'button',
				height : 60,
				columnWidth : 1, 
				layout:'column',
				items:[{
					text: baseinfo.bigcusDeliveryAddress.i18n('foss.baseinfo.cancel'),//取消
					width:120,
					margin:'20 5 5 70',
					handler: function(){ 
						me.up().hide();
					}
				},{
					text:baseinfo.bigcusDeliveryAddress.i18n('foss.baseinfo.reset'),//重置
					margin:'20 5 5 70',
					width:120,
					handler: function(){
						me.getForm().reset();
						var model =me.dictModifyModel;
						me.getForm().loadRecord(model);
						me.getForm().findField('deliverySalesDeptCode').setCombValue(model.get('deliverySalesDeptName'),model.get('deliverySalesDeptCode')); //营业部选择器
					}
				},{
					text:baseinfo.bigcusDeliveryAddress.i18n('foss.baseinfo.save'),//保存
					cls:'yellow_button',
					margin:'20 5 5 70',
					width:120,
					handler: function(){
						 baseinfo.bigcusDeliveryAddress.save(me);
					}
				},{
					text: '关闭',//关闭
					margin:'0 5 5 265',
					width:120,
					handler: function(){
						me.up().hide();
					}
				}]
			}];
		me.callParent([cfg]);
	}
});
/**
 * 修改窗口
 */
Ext.define('Foss.baseinfo.bigcusDeliveryAddress.DistrictModifyWindow',{
	extend: 'Ext.window.Window', 
    width: 800,
	layout : 'column', 
	dictMidifyForm:null,
	closeAction:'hide',
	autoDestroy:true,
	getDictMidifyUpdateForm:function(){
		if(Ext.isEmpty(this.dictMidifyForm)){
			this.dictMidifyForm=Ext.create('Foss.baseinfo.bigcusDeliveryAddress.DictModifyForm');
		}
		return this.dictMidifyForm;
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [me.getDictMidifyUpdateForm()];
		me.listeners={
			beforehide:function(me,eOpts){
				//清空数据
				me.getDictMidifyUpdateForm().getForm().reset();
			}
		};
		me.callParent([cfg]);
	}
});
//右边模块-中间Grid区域Panel
Ext.define('Foss.baseinfo.bigcusDeliveryAddress.BigcusDeliveryAddressQueryResultPanel',{
	extend:'Ext.panel.Panel', 
	layout:'column',
	frame: true,
	hidden:true,
	columnWidth: 1,
	cls:'autoHeight',
	bodyCls:'autoHeight',
	title: baseinfo.bigcusDeliveryAddress.i18n('foss.baseinfo.districtList'),//行政区域列表
	defaults: {
		readOnly : false,
		margin:'5 5 5 10',
		anchor: '90%'
	},
	QueryResultGrid:null,
	getQueryResultGrid:function(){
		if(Ext.isEmpty(this.QueryResultGrid)){ 
			this.QueryResultGrid = Ext.create('Foss.baseinfo.bigcusDeliveryAddress.QueryResultGrid');
		}
		return this.QueryResultGrid;
	},
	initComponent: function(config){
	    var me = this,
				cfg = Ext.apply({}, config);
	    me.items = [
			Ext.create('Ext.container.Container',{
				columnWidth:1,
				layout : 'column',
				items:[
						me.getQueryResultGrid()
					]
			})	
		];
	    me.callParent([cfg]);
	}
});
/**
 * 右边模块的实现的主面板：
 */
Ext.define('Foss.baseinfo.bigcusDeliveryAddress.bigcusDeliveryAddressRightPannel',{
	extend:'Ext.panel.Panel',  
	cls:'autoHeight',
	bodyCls:'autoHeight',
	defaultType : 'textfield',
	layout:'column',
	frame: true,
	columnWidth: 0.72,
	defaults: {
		readOnly : false,
		margin:'5 5 5 10',
		anchor: '90%',
		columnWidth: 1,
		labelWidth: 120
	},
	//查询条件面板
	bigcusDeliveryAddressQueryForm:null,
	getBigcusDeliveryAddressQueryForm : function(){
		if(Ext.isEmpty(this.bigcusDeliveryAddressQueryForm)){
			this.bigcusDeliveryAddressQueryForm= Ext.create('Foss.baseinfo.bigcusDeliveryAddress.BigcusDeliveryAddressQueryForm');
		}
		return this.bigcusDeliveryAddressQueryForm;
	}, 
	//查询结果列表
	bigcusDeliveryAddressQueryResultPanel:null,
	getBigcusDeliveryAddressQueryResultPanel : function(){
		if(Ext.isEmpty(this.bigcusDeliveryAddressQueryResultPanel)){
			this.bigcusDeliveryAddressQueryResultPanel= Ext.create('Foss.baseinfo.bigcusDeliveryAddress.BigcusDeliveryAddressQueryResultPanel');
		}
		return this.bigcusDeliveryAddressQueryResultPanel;
	}, 
	//详情面板
	bigcusDeliveryAddressDetailForm:null,
	getBigcusDeliveryAddressDetailForm : function(){
		if(Ext.isEmpty(this.bigcusDeliveryAddressDetailForm)){
			this.bigcusDeliveryAddressDetailForm = Ext.create('Foss.baseinfo.bigcusDeliveryAddress.DetailForm');
		}
		return this.bigcusDeliveryAddressDetailForm;
	}, 
	initComponent: function(config){
		var me = this,
				cfg = Ext.apply({}, config);
		me.items = [
		            me.getBigcusDeliveryAddressQueryForm(),
		            me.getBigcusDeliveryAddressQueryResultPanel(),
		            me.getBigcusDeliveryAddressDetailForm()
		];
		me.callParent([cfg]);
	}
});
/**
 * 程序入口方法
 */
baseinfo.bigcusDeliveryAddress.window=null;
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-bigcusDeliveryAddress_content')) {
		return;
	}
	var bigcusDeliveryAddressLeftPannel = Ext.create('Foss.baseinfo.bigcusDeliveryAddress.bigcusDeliveryAddressLeftPannel');
	var bigcusDeliveryAddressRightPannel = Ext.create('Foss.baseinfo.bigcusDeliveryAddress.bigcusDeliveryAddressRightPannel');

	Ext.getCmp('T_baseinfo-bigcusDeliveryAddress').add(Ext.create('Ext.panel.Panel',{
		id: 'T_baseinfo-bigcusDeliveryAddress_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'column',
		getRegionsLeftPannel : function() {
			return bigcusDeliveryAddressLeftPannel;
		},
		getRegionsRightPannel : function() {
			return bigcusDeliveryAddressRightPannel;
		},
		getWindow:function(){
			if(Ext.isEmpty(baseinfo.bigcusDeliveryAddress.window)){
				baseinfo.bigcusDeliveryAddress.window=Ext.create('Foss.baseinfo.bigcusDeliveryAddress.DistrictModifyWindow');
			}
			return baseinfo.bigcusDeliveryAddress.window;
		},
		items: [bigcusDeliveryAddressLeftPannel,bigcusDeliveryAddressRightPannel]
	}));
});