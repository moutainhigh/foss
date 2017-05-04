function constructDate(){
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0!
	var yyyy = today.getFullYear();
	if(dd<10){dd='0'+dd;} 
	if(mm<10){mm='0'+mm;} 
	var today = yyyy+'-'+mm+'-'+dd;
	var i=0;
	var dateString='';
	for(i=0; i<10; i++){
		dateString=dateString+'<img src=../images/login/login/numbers/'+today[i]+'.png>';
	}
	return dateString;
}
var dateHTML=constructDate();
var date=Ext.create('Ext.panel.Panel' ,{
	id:"datePanel",
	bodyBorder:false,
	closeAction:'hide',
	height:50,
	html:dateHTML
});
var loginForm=Ext.create('Ext.form.Panel', {
	id:'loginForm',
	border: true,
    //指定表单中的所有字段类型
    defaultType: 'textfield',
    defaults: {
    	labelAlign: 'left',
    	labelSeparator: '',
		height: 28,
    	//labelWidth: 65,
        //msgTarget: 'side',
        anchor: '98%'
    },
    //表单项
    items: [{
        fieldLabel: '<img src=../images/login/login/username.png>',
        name: 'loginName',
    },{
        fieldLabel: '<img src=../images/login/login/password.png>',
        name: 'password',
		inputType: 'password'
    }],
    //指定表单容器的按钮
    buttons: [{
        text: '<b>登录</b>',
		width: 100,
		height: 25,
		handler: loginHandler
    }]
});

function loginHandler(){
	var loginInfo = loginForm.getValues(),
		loginName = loginInfo.loginName,
		password = loginInfo.password,
		message = null;
	message = check(loginName,password);
	if (!message) {
		Ext.Ajax.request({
			url : 'login.action',
			params : {
				'loginName' : loginName,
				'password' : password
			},
			//登录成功
			success : function(response, opts) {
				var json = Ext.decode(response.responseText);
	            if (json.success && !json.isException) {
	                window.location = 'index.action';
	            } else if (!json.success && !json.isException) {
	            	errorMsg.update(json.message);
	            }
			},
			//登录失败
			failure : function(response, opts) {
				errorMsg.update(login.i18n('dpap.login.connectError'));
			}
		});
	}else{
		//验证失败
		errorMsg.update(message);
	}
}

var errorMsg=Ext.create('Ext.panel.Panel',{
	id:'loginError',
	autoScroll:false						
});
/*var announcement=Ext.create('Ext.panel.Panel',{
	id:'loginAnnounce',
	height: 145,
	autoScroll:true,
	title:'<img src=../images/login/login/anTitle.png>',
	html: '<p>2012-03-24  最新通知：本月月末将举行运动活动希望各个部门踊跃参加。本次运动活动，设立多种奖励啊，排名前十均有丰厚奖品。请每个部门经理协调好工作时间，并安排员工积极参与运动活动。</p><ul><li>本次活动报名截止日期即日起至2012年4月3日止。</li></ul><img src=../images/login/login/sep.gif>'
});
var imgPanel=Ext.create('Ext.panel.Panel', {
	id:'announceImg',
	height:90,
	autoScroll:false,
	html:'<img src=../images/login/login/img1.jpg>'
});*/
var logoPanel=Ext.create('Ext.panel.Panel', {
	id:'logoImg',
	height:65,
	width:500,
	autoScroll:false,
	html:'<img src=../images/login/login/logo.png>'
});

/**
 * 验证方法
 * @param logName 登录名
 * @param logPwd 登录密码
 * @returns message 验证信息字符串
 */
function check(logName,logPwd) {
	var message = null;
	if (logName == "" || logName == null || logName == undefined) {
		message = login.i18n('dpap.login.UserNameIsNullException');
	}
	if (logPwd == '' || logPwd == null || logPwd == undefined) {
		if (message != null) {
			message = message + ','
					+ login.i18n('dpap.login.LoginPasswordIsNullException');
		} else {
			message = login.i18n('dpap.login.LoginPasswordIsNullException');
		}
	}
	return message;
}

/*----------框架整合-------------------------*/
Ext.onReady(function() {
    Ext.create('Ext.container.Viewport', {
		layout: 'auto',
		autoScroll: false,
		items: [{id:"login_logo_area",
				xtype: 'panel',
				width:1280,
				layout: 'auto',
				items:[logoPanel]
			},{	id:"login_center_area",
				xtype: 'panel',
				width:340,
				height:1,
				layout: 'auto',
				items:[date, errorMsg, loginForm/*, announcement,imgPanel*/]
			}
		]
	});
});