/**
 * 日期时间控件，可单独显示日期，或日期时间。格式可自己设置。和myDate97设置一样
 * @class Deppon.form.field.DateTimeField
 * @extends Ext.form.field.Trigger
 * @xtype datetimefield
 * 日期时间控件的定义
 * 使用说明：
 * 在要定义日期时间类型为当前类型的字段中，定义字段的类型为"datetimefield"类型
 *{
 *	//字段标题
 *	fieldlable: "arrivedDateFrom",
 *	//定义字段的类型为扩展的datetimefield类型
 *	xtype: 'datetimefield',
 *}
 */
Ext.define('Deppon.form.field.DateTimeField', {
	extend: 'Ext.form.field.Trigger',
	alias: 'widget.datetimefield_date97',
	/**
	 * @param {Array} date97配置列表
	 * @type 
	 */
	dateConfig:null,
	/**
	 * @param {Boolean} 是否显示时间,默认为不显示
	 */
	time:false,
	triggerCls : Ext.baseCSSPrefix + 'form-date-trigger',
	defaultAutoCreate : {tag : "input",type : "text",size : "10",autocomplete : "off"},
	initComponent : function() {
		Ext.form.field.Date.superclass.initComponent.call(this);
		this.initDate97js();
		this.initDateConfig();
	},
	onTriggerClick : function(e) {// 点击查找按钮时
		if (this.disabled||this.readOnly) {
			return;
		}
		this.onFocus({});
		var bodyWidth = document.body.clientWidth;
		var xC = document.body.clientWidth - e.getX() - this.width;
		var yC = document.body.clientHeight - e.getY() - this.height; 
		var x=0;
		var y=0;
		if (xC > 0)
			x = e.getX();
		else
			x = document.body.clientWidth - this.width - 4;

		if (yC > 0)
			y = e.getY();
		else
			y= document.body.clientHeight - this.height - 4;
//		this.dateConfig['position']={left:e.xy[0],top:e.xy[1]};
		WdatePicker(this.dateConfig);
	},
	initDate97js:function(){
		var obj=this;
		if(!document.getElementById("$date97js")){
			 var  script  =  document.createElement("script");   
			  script.setAttribute("type",   "text/javascript");   
			  script.setAttribute("src",   "extend/date97/WdatePicker.js");
			  script.setAttribute("id","$date97js");
			  try   
			  {   
				  document.getElementsByTagName("head")[0].appendChild(script);
				  script.onload = script.onreadystatechange = function() {
				 	  if (script.readyState && script.readyState != 'loaded' && script.readyState != 'complete') 
			            return; 
			         script.onreadystatechange = script.onload = null; 
 			         WdatePicker(1);
				 }
			  }catch(e){}
		}
	},
	initDateConfig:function()
	{
		if(!this.dateConfig)
			this.dateConfig=new Array();
		if(!this.dateConfig['el'])
			this.dateConfig['el']=this.id;
		if(this.time)
			this.addDateConfig("dateFmt",'yyyy-MM-dd HH:mm:ss');
		else
			if(!this.dateConfig["dateFmt"])
				this.dateConfig["dateFmt"]='yyyy-MM-dd';
		if(!this.dateConfig["skin"])
			this.dateConfig["skin"]='ext';
	},
	addDateConfig:function(name,value)
	{
		this.removeDateConfig(name);
		this.dateConfig[name]=value;
	},
	removeDateConfig:function(name){
		for (var i = 0; i < this.dateConfig.length; i++) {
			var temp = this.dateConfig[i];
			if (temp && temp.split(':')[0] == name) {
				this.dateConfig.pop(i);
				return;
			}
		}
	},
	setDateConfig:function(config)
	{
		this.dateConfig=config;
		this.initDateConfig();
	}
	

});
 