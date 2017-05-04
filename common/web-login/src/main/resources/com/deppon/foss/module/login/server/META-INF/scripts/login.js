/**
 * String对象的trim方法在某些版本浏览器下不兼容
 */
if(typeof String.prototype.trim !== 'function') {
	String.prototype.trim = function() {
		return this.replace(/^\s+|\s+$/g, ''); 
	}
}

//取得cookie
function getCookie(name) {
	var nameEQ = name + "=";
	var ca = document.cookie.split(';');    //把cookie分割成组
	for(var i=0;i < ca.length;i++) {
		var c = ca[i];                      //取得字符串
		while (c.charAt(0)==' ') {          //判断一下字符串有没有前导空格
			c = c.substring(1,c.length);      //有的话，从第二位开始取
		}
		if (c.indexOf(nameEQ) == 0) {       //如果含有我们要的name
			return unescape(c.substring(nameEQ.length,c.length));    //解码并截取我们要值
		}
	}
	return false;
}

//设置cookie
function setCookie(name, value, days) {
	days = days || 0;   //days有值就直接赋值，没有为0，这个根php不一样。
	var expires = "";
	if (days != 0 ) {      //设置cookie生存时间
		var date = new Date();
		date.setTime(date.getTime()+(days*24*60*60*1000));
		expires = "; expires="+date.toGMTString();
	}
	document.cookie = name+"="+escape(value)+expires+"; path=/";   //转码并赋值
}

function constructDate(){
	var today = new Date(),
		day = today.getDay(),
		dd = today.getDate(),
		mm = today.getMonth()+1, //January is 0!
		yyyy = today.getFullYear();
	if(dd<10){dd='0'+dd;} 
	if(mm<10){mm='0'+mm;} 
	var today = yyyy+'-'+mm+'-'+dd;
	var dateString = today+'  ';
	switch(day){
	  case 0: dateString = dateString + login.i18n('foss.login.Sunday');break;
	  case 1: dateString = dateString + login.i18n('foss.login.Monday');break;
	  case 2: dateString = dateString + login.i18n('foss.login.Tuesday');break;
	  case 3: dateString = dateString + login.i18n('foss.login.Wednesday');break;
	  case 4: dateString = dateString + login.i18n('foss.login.Thursday');break;
	  case 5: dateString = dateString + login.i18n('foss.login.Friday');break;
	  case 6: dateString = dateString + login.i18n('foss.login.Saturday');break;
	}
	return dateString;
}
function bodyReady(){
	 /*var odd=window.onload;//先得到已经注册的onload函数
	        odd&&odd();*/
	var dateTime = document.getElementById('dateTime'),
		error = document.getElementById('error'),
		loginName = document.getElementById('loginName'),
		loginNameValue = getCookie('loginName');
	if(loginNameValue){
		loginName.value = loginNameValue;		
	}
	writeErrorMessage(error.innerHTML);
	dateTime.innerHTML = constructDate();
	document.onkeydown = function(evt){
		if(window.event){
			evt = window.event;
		}
		if(evt.keyCode==13){
			loginHandler();
		}
	};
}

function loginHandler(){
	if("undefined" != typeof returnCitySN){
	  document.getElementById("publicIp").value=returnCitySN["cip"];
	}else{
		 document.getElementById("publicIp").value = " ";
	}
	var loginName = document.getElementById('loginName'),
		password = document.getElementById('password'),
		loginForm = document.getElementById('loginForm'),
		cashierValidator = document.getElementById('cashierValidator');
		//validator = document.getElementById('validator');
		var loginNameValue = loginName.value.trim(),
		passwordValue = password.value.trim();
	loginName.value=loginNameValue;
	password.value=passwordValue;
	message = check(loginNameValue,passwordValue);
	
	if(cashierValidatorModule.style.display==""){//验证码模块style显示
		//添加验证报登陆校验
		cashierValidatorValue=cashierValidator.value.trim();
		//validatorValue=validator.value;
		if(!message && (cashierValidatorValue==""||cashierValidatorValue==null)){
			message="请输入验证码！";
		}
		if(!message && (cashierValidatorValue!=""&&cashierValidatorValue!=null)
				&& (cashierValidatorValue.length<6||cashierValidatorValue.length>6)){
			message="验证码为6位数字！";
		}
	/*	if(cashierValidatorValue!=validatorValue){
			if(!message){
				if(validatorValue=="ERROR"&&(cashierValidatorValue!=""||cashierValidatorValue!=null)){
					message="验证码失效，请重新获取验证码！";
				}else{
					message="验证码错误，请重新输入正确的验证码！";
				}
			}
		}*/
	}else{
		checkUserName(document.getElementById('loginName'));
		if(cashierValidatorModule.style.display==""){
			message="请输入验证码！";
		}
	}
	
	setCookie('loginName',loginName.value,6);
	if (!message) {
		if(login.dev){
		//	loginForm.action = '../login/index.action';			
		}
		$.ajax({  
	        type: "POST",//http请求方式  
	        url: "../login/indexTest.action",//服务器端url地址  
	        data: $("#loginForm").serialize(),//发送给服务器端的数据  
	        dataType: "json",//告诉jQuery返回的数据格式 
	        async: true, //同步请求
	        success: function(data){
	        	if(data.success){
	        		window.location.href="../login/index.action"; 
	        		//window.open('../login/index.action');
	        	}else{
	        		writeErrorMessage(data.message);
	        	}
	        }//定义交互完成并且服务器正确返回数值时调用的回调函数  
	       });
		//loginForm.submit();
		
	}else{
		writeErrorMessage(message);
	}
}

function writeErrorMessage(message){
	 if(message==='验证码错误！'){
	   var cashierValidatorModule = document.getElementById("cashierValidatorModule");
		cashierValidatorModule.style.display=""; 
	 }
	 var errorLi = document.getElementById('errorLi'),
		error = document.getElementById('error');
	if(message!=''){
		errorLi.style.display='inline';
		error.innerHTML = message;
	}else{
		errorLi.style.display='none';
	}
}

function checkUser(){
	window.open('../login/oAPasswordAuthentication.action');
}

function checkNewPassword(){
	//获得界面元素
	var newPassword = document.getElementById('newPassword'),
	    confirmPassword = document.getElementById('confirmPassword'),
	    resetPasswordForm = document.getElementById('resetPasswordForm'),
	    //去空格操作
	    newPasswordValue = newPassword.value.trim(),
	    confirmPasswordValue = confirmPassword.value.trim();
		newPassword.value=newPasswordValue;
		confirmPassword.value=confirmPasswordValue;
		
		//控件为空校验，重置密码和确认密码是否一致
		var message = null;
		if (newPassword.value == "" || newPassword.value == null ) {
			message = '新输入密码为必填项';
			if (confirmPassword.value == "" || confirmPassword.value == null ) {
				message = message +','+'确认密码为必填项';
			}
		}else{
			if (confirmPassword.value == "" || confirmPassword.value == null ) {
				message = '确认密码为必填项';
			}else{
				
				if(newPassword.value != confirmPassword.value){
					message = '新输入密码和确认密码不一致！';
				}else if(newPasswordValue.length > 15 || newPasswordValue.length < 6){
						message = '重置密码位数必须在6-15位之间！';
					}
			}
		}
		
		if (message == null) {
			resetPasswordForm.action = '/bse-baseinfo-web/login/resetFossLoginPassword.action';
			resetPasswordForm.submit();
		}else{
			writeErrorMessage(message);
		}
}

function checkIsNull(){
	//获得界面元素
	var loginName = document.getElementById('emp_code'),
		password = document.getElementById('emp_password'),
		oaPassCheckForm = document.getElementById('oaPassCheckForm'),
	    //去空格操作
		loginNameValue = loginName.value.trim(),
		passwordValue = password.value.trim();
	    loginName.value = loginNameValue;
	    password.value = passwordValue;
		
		//控件为空校验，重置密码和确认密码是否一致
		var message = null;
		if (loginNameValue == "" || loginNameValue == null ) {
			message = '输入工号不能为空';
			
			if (passwordValue == "" || passwordValue == null ) {
				message = message +','+'输入密码不能为空';
			}
			
		}else{
			if (passwordValue == "" || passwordValue == null ) {
				message = '输入密码不能为空';
			}
		}
		
		
		if (message == null) {
			oaPassCheckForm.action = '../login/checkFossPasswordByOa.action';		
			oaPassCheckForm.submit();
		}else{
			writeErrorMessage(message);
		}
}

/**
 * 验证方法
 * @param logName 登录名
 * @param logPwd 登录密码
 * @returns message 验证信息字符串
 */
function check(logName,logPwd) {
	var message = null;
	if (logName == "" || logName == null || logName == undefined) {
		message = login.i18n('foss.login.UserNameIsNullException');
	}
	if (logPwd == '' || logPwd == null || logPwd == undefined) {
		if (message != null) {
			message = message + ','
					+ login.i18n('foss.login.LoginPasswordIsNullException');
		} else {
			message = login.i18n('foss.login.LoginPasswordIsNullException');
		}
	}
	return message;
};

/**
 * 登陆验证该用户是否需要短信验证码
 * @param obj
 */
function checkUserName(obj){
	var logName=obj.value;
	if (logName == "" || logName == null || logName == undefined) {
		return;
	}
	
	loginName = logName.trim();
	
	$.ajax({  
        type: "POST",//http请求方式  
        url: "../loginVal/checkUserName.action",//服务器端url地址  
        data: "loginName="+loginName,//发送给服务器端的数据  
        dataType: "xml",//告诉jQuery返回的数据格式 
        async: false, //同步请求
        success: callback//定义交互完成并且服务器正确返回数值时调用的回调函数  
       });
    
    //回调函数  
    function callback(returnedData){  
	    //接受服务器返回的数据  
	    //需要将dom的对象转成jQuery的对象  
	    var isContainCashierRole = $(returnedData).find("isContainCashierRole").text();
		var errorMsg = $(returnedData).find("errorMsg").text();
		var cashierValidatorModule = document.getElementById("cashierValidatorModule"),
			errorField = document.getElementById("errorField"),
			error=document.getElementById("error"),
			phoneNum = document.getElementById("phoneNum"),
			password = document.getElementById("password");
		cashierValidatorModule.style.display="none";
		error.innerHTML="";
		if (isContainCashierRole != "" 
			&& isContainCashierRole != null 
			&& isContainCashierRole != undefined) {
			cashierValidatorModule.style.display=""; 
			phoneNum.value=isContainCashierRole;
		}
		if(errorMsg != ""
			&& errorMsg != null
			&& errorMsg != undefined
			){
			cashierValidatorModule.style.display=""; 
			writeErrorMessage(errorMsg);
		}
    }
};

/**
 * 登陆用户发送短信验证
 * @param obj
 */
function sendCode(obj){  
	var loginName = document.getElementById("loginName");
	var phoneNum = document.getElementById("phoneNum");
	$.post("../loginVal/sendCode.action",
			{   loginName:loginName.value,
				phoneNum: phoneNum.value
			}, function(returnedData, status){
				if("success" == status){
					var returnResult = $(returnedData).find("returnResult").text();
					var validatorText = $(returnedData).find("validator").text();
					var validator = document.getElementById("validator");
					if ( returnResult!="200") {
						writeErrorMessage("验证码发送异常，请联系管理员");
					}
				}else{
				  writeErrorMessage("服务端连接异常 ！");
				}
				
			});
        // 1分钟内禁止点击  
        for (var i = 1; i <= 120; i++) {  
            // 1秒后显示  
            window.setTimeout("updateTime(" + (120 - i) + ")", i * 1000);  
        }  
};

function updateTime(i){  
    // setTimeout传多个参数到function有点麻烦，只能重新获取对象  
    var obj = document.getElementById("validationCode");
    var validator = document.getElementById("validator");
    if(i > 0){  
        obj.innerHTML  = "距下次获取还需" + i + "秒";  
        obj.disabled = true;  
    }else{  
        obj.innerHTML = "获取验证码";
        validator.value="ERROR";
        obj.disabled = false;  
    }  
}  