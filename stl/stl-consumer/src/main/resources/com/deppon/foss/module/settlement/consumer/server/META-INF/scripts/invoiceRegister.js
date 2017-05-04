/**
 * 定额发票登记
 * @page
 */

/**
 * ddw
 * 运单号关联小票号
 * @param form
 * @param 被监听组件 小票
 */
consumer.invoiceRegister.waybillNosToOtherRevenueNos = function(form,me){
	var invoiceForm = form.getForm();
	var waybillNos = invoiceForm.findField('vo.waybillNOs');
	var otherRevenueNos = invoiceForm.findField('vo.otherRevenueNos');
	if (!waybillNos.isValid()) {
		//小票输入验证不通过，结束方法
		consumer.invoiceRegister.resetAllAmount(f);
		me.enable(true);
		return false;
	}
	var paramsV = invoiceForm.getValues();

	var waybillNoArr;
	var waybillStr = paramsV['vo.waybillNOs'];
	if(!Ext.isEmpty(waybillStr)){
		waybillNoArr = waybillStr.match(/\d+/g);
	}
	var otherRevenueNoArr;
	var otherRevenueStr = paramsV['vo.otherRevenueNos'];
	if(!Ext.isEmpty(otherRevenueStr)){
		otherRevenueNoArr = otherRevenueStr.match(/\d+/g);
	}
	var paramsdata = {
		'vo': {
			'waybillNOs': waybillNoArr
		}
	};
	Ext.Ajax.request({
		url:consumer.realPath('queryOtherRevenueNosByWaybillNos.action'),
		async:false,
		method : "POST",
		jsonData:paramsdata,
		success:function(response){
			var result = Ext.decode(response.responseText);
			var otherNos = result.vo.otherRevenueNos;
			if(!Ext.isEmpty(otherNos)){
				otherRevenueNos.setValue(null);
				if(!Ext.isEmpty(otherRevenueNoArr) && otherRevenueNoArr.length > 0){
					var otherNosLength = otherRevenueNoArr.length;
					for (var i=0; i < otherNos.length; i++){
						otherRevenueNoArr[otherNosLength + i] = otherNos[i];
					}
					var otherNoArr = Ext.Array.unique(otherRevenueNoArr);
					otherRevenueNos.setValue(otherNoArr);
				}else{
					otherRevenueNos.setValue(otherNos);
				}
			}
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.ux.Toast.msg(consumer.invoiceRegister.i18n('foss.stl.consumer.common.warmTips'), json.message,'error',3000);
			consumer.invoiceRegister.resetAllAmount(invoiceForm);
			me.enable(true);
		},
		failure:function(form,action){
			consumer.invoiceRegister.resetAllAmount(invoiceForm);
			me.enable(true);
		},
		unknownException:function(form,action){
			consumer.invoiceRegister.resetAllAmount(invoiceForm);
			me.enable(true);
		}
	});
}


/**
 * 监听小票输入，查询总运费可可开票金额并设置
 * @param form
 * @param 被监听组件 小票
 */
consumer.invoiceRegister.amountChangeLisn = function(form,me){
	var f = form.getForm();
	var otherRevenueEl = f.findField('vo.otherRevenueNos');
	var waybillEl = f.findField('vo.waybillNOs');
	if(Ext.isEmpty(otherRevenueEl.value) && Ext.isEmpty(waybillEl.value)){
		Ext.ux.Toast.msg(consumer.invoiceRegister.i18n('foss.stl.consumer.common.warmTips'), consumer.invoiceRegister.i18n('foss.stl.consumer.invoiceRegister.WaybillSmallMustSelectOne'),'error',3000);
		//输入为空
		consumer.invoiceRegister.resetAllAmount(f);
		me.enable(true);
		return false;
	}
	if (!otherRevenueEl.isValid()) {
		//小票输入验证不通过，结束方法
		consumer.invoiceRegister.resetAllAmount(f);
		me.enable(true);
		return false;
	}
	if (!waybillEl.isValid()) {
		//小票输入验证不通过，结束方法
		consumer.invoiceRegister.resetAllAmount(f);
		me.enable(true);
		return false;
	}

	var paramsV = f.getValues();

	var waybillNoArr;
	var waybillStr = paramsV['vo.waybillNOs'];
	if(!Ext.isEmpty(waybillStr)){
		waybillNoArr = waybillStr.match(/\d+/g);
	}

	var noteNoArr;
	var noteNoStr = paramsV['vo.otherRevenueNos'];
	if(!Ext.isEmpty(noteNoStr)){
		noteNoArr = noteNoStr.match(/\d+/g);
	}

	var paramsdata = {
		'vo': {
			'waybillNOs': waybillNoArr,
			'otherRevenueNos': noteNoArr,
			'curPayTotalAmount':paramsV['vo.curPayTotalAmount'],
			'curPayOpenAmount':paramsV['vo.curPayOpenAmount'],
			'destPayTotalAmount':paramsV['vo.destPayTotalAmount'],
			'destPayOpenAmount':paramsV['vo.destPayOpenAmount'],
			'noteTotalAmount':paramsV['vo.noteTotalAmount'],
			'noteOpenAmount':paramsV['vo.noteOpenAmount'],
			'thisAmount':paramsV['vo.thisAmount']
		}
	};
	waybillEl.listeners={};
	consumer.invoiceRegister.waybillNosToOtherRevenueNos(form,me);
	Ext.Ajax.request({
		url:consumer.realPath('validateAndQueryInvoiceAmounts.action'),
		method : "POST",
		jsonData:paramsdata,
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(!result.vo.success){
				//如果查询返回总金额=0，则运单或者小票有无效的。
				var unOtherRevenueNoArr = result.vo.unOtherRevenueNos;
				var unWaybillNOArr = result.vo.unWaybillNOs;
				var unDeptOtherRevenueNoArr = result.vo.unDeptOtherRevenueNos;
				var unDeptWaybillNOArr = result.vo.unDeptWaybillNOs;

				//无效运单、有效运单、无效小票和有效小票都不为空
				if(!Ext.isEmpty(unWaybillNOArr)&&!Ext.isEmpty(waybillNoArr)&&!Ext.isEmpty(unOtherRevenueNoArr)&&!Ext.isEmpty(noteNoArr)){
					var mark = false;
					//找出无效的运单号
					for (var i=0; i < waybillNoArr.length; i++){
						for (var j=0; j < unWaybillNOArr.length; j++){
							if(waybillNoArr[i] == unWaybillNOArr[j]){
								waybillNoArr[i] = '['+waybillNoArr[i]+']';
								mark = true;
							}
						}
					}
					//找出无效的小票号
					for (var i=0; i < noteNoArr.length; i++){
						for (var j=0; j < unOtherRevenueNoArr.length; j++){
							if(noteNoArr[i] == unOtherRevenueNoArr[j]){
								noteNoArr[i] = '['+noteNoArr[i]+']';
								mark = true;
							}
						}
					}
					if(mark){
						waybillEl.setValue(waybillNoArr);
						otherRevenueEl.setValue(noteNoArr);
						Ext.ux.Toast.msg(consumer.invoiceRegister.i18n('foss.stl.consumer.common.warmTips'), consumer.invoiceRegister.i18n('foss.stl.consumer.invoiceRegister.SquareBracketsInvalidWaybillSmall'),'error',3000);
						consumer.invoiceRegister.resetAllAmount(f);
						me.enable(true);
						return false;
					}
				}//无效运单为空，全是有效运单，无效小票和有效小票都不为空，则只需找出无效小票即可
				else if(Ext.isEmpty(unWaybillNOArr)&&!Ext.isEmpty(waybillNoArr)&&!Ext.isEmpty(unOtherRevenueNoArr)&&!Ext.isEmpty(noteNoArr)){
					var mark = false;
					for (var i=0; i < noteNoArr.length; i++){
						for (var j=0; j < unOtherRevenueNoArr.length; j++){
							if(noteNoArr[i] == unOtherRevenueNoArr[j]){
								noteNoArr[i] = '['+noteNoArr[i]+']';
								mark = true;
							}
						}
					}
					if(mark){
						waybillEl.setValue(waybillNoArr);
						otherRevenueEl.setValue(noteNoArr);
						Ext.ux.Toast.msg(consumer.invoiceRegister.i18n('foss.stl.consumer.common.warmTips'), consumer.invoiceRegister.i18n('foss.stl.consumer.invoiceRegister.SquareBracketsInvalidWaybillSmall'),'error',3000);
						consumer.invoiceRegister.resetAllAmount(f);
						me.enable(true);
						return false;
					}
				}//无效运单有效运单都不为空，无效小票为空，只需找出无效运单号即可
				else if(!Ext.isEmpty(unWaybillNOArr)&&!Ext.isEmpty(waybillNoArr)&&Ext.isEmpty(unOtherRevenueNoArr)&&!Ext.isEmpty(noteNoArr)){
					var mark = false;
					for (var i=0; i < waybillNoArr.length; i++){
						for (var j=0; j < unWaybillNOArr.length; j++){
							if(waybillNoArr[i] == unWaybillNOArr[j]){
								waybillNoArr[i] = '['+waybillNoArr[i]+']';
								mark = true;
							}
						}
					}
					if(mark){
						waybillEl.setValue(waybillNoArr);
						otherRevenueEl.setValue(noteNoArr);
						Ext.ux.Toast.msg(consumer.invoiceRegister.i18n('foss.stl.consumer.common.warmTips'), consumer.invoiceRegister.i18n('foss.stl.consumer.invoiceRegister.SquareBracketsInvalidWaybillSmall'),'error',3000);
						consumer.invoiceRegister.resetAllAmount(f);
						me.enable(true);
						return false;
					}
				}//一同样的方法判断是不是的出发/到达部门跟登录部门一致
				else if(!Ext.isEmpty(unDeptWaybillNOArr)&&!Ext.isEmpty(waybillNoArr)&&!Ext.isEmpty(unDeptOtherRevenueNoArr)&&!Ext.isEmpty(noteNoArr)){
					var mark = false;
					for (var i=0; i < waybillNoArr.length; i++){
						for (var j=0; j < unDeptWaybillNOArr.length; j++){
							if(waybillNoArr[i] == unDeptWaybillNOArr[j]){
								waybillNoArr[i] = '['+waybillNoArr[i]+']';
								mark = true;
							}
						}
					}
					for (var i=0; i < noteNoArr.length; i++){
						for (var j=0; j < unDeptOtherRevenueNoArr.length; j++){
							if(noteNoArr[i] == unDeptOtherRevenueNoArr[j]){
								noteNoArr[i] = '['+noteNoArr[i]+']';
								mark = true;
							}
						}
					}
					if(mark){
						waybillEl.setValue(waybillNoArr);
						otherRevenueEl.setValue(noteNoArr);
						Ext.ux.Toast.msg(consumer.invoiceRegister.i18n('foss.stl.consumer.common.warmTips'), consumer.invoiceRegister.i18n('foss.stl.consumer.invoiceRegister.SquareBracketsInvalidDeptSmall'),'error',3000);
						consumer.invoiceRegister.resetAllAmount(f);
						me.enable(true);
						return false;
					}
				}else if(Ext.isEmpty(unDeptWaybillNOArr)&&!Ext.isEmpty(waybillNoArr)&&!Ext.isEmpty(unDeptOtherRevenueNoArr)&&!Ext.isEmpty(noteNoArr)){
					var mark = false;
					for (var i=0; i < noteNoArr.length; i++){
						for (var j=0; j < unDeptOtherRevenueNoArr.length; j++){
							if(noteNoArr[i] == unDeptOtherRevenueNoArr[j]){
								noteNoArr[i] = '['+noteNoArr[i]+']';
								mark = true;
							}
						}
					}
					if(mark){
						waybillEl.setValue(waybillNoArr);
						otherRevenueEl.setValue(noteNoArr);
						Ext.ux.Toast.msg(consumer.invoiceRegister.i18n('foss.stl.consumer.common.warmTips'), consumer.invoiceRegister.i18n('foss.stl.consumer.invoiceRegister.SquareBracketsInvalidDeptSmall'),'error',3000);
						consumer.invoiceRegister.resetAllAmount(f);
						me.enable(true);
						return false;
					}
				}else if(!Ext.isEmpty(unDeptWaybillNOArr)&&!Ext.isEmpty(waybillNoArr)&&Ext.isEmpty(unDeptOtherRevenueNoArr)&&!Ext.isEmpty(noteNoArr)){
					var mark = false;
					for (var i=0; i < waybillNoArr.length; i++){
						for (var j=0; j < unDeptWaybillNOArr.length; j++){
							if(waybillNoArr[i] == unDeptWaybillNOArr[j]){
								waybillNoArr[i] = '['+waybillNoArr[i]+']';
								mark = true;
							}
						}
					}
					if(mark){
						waybillEl.setValue(waybillNoArr);
						otherRevenueEl.setValue(noteNoArr);
						Ext.ux.Toast.msg(consumer.invoiceRegister.i18n('foss.stl.consumer.common.warmTips'), consumer.invoiceRegister.i18n('foss.stl.consumer.invoiceRegister.SquareBracketsInvalidDeptSmall'),'error',3000);
						consumer.invoiceRegister.resetAllAmount(f);
						me.enable(true);
						return false;
					}
				}
			}else {
				var curPayTotalAmountEl = f.findField('vo.curPayTotalAmount');
				if(curPayTotalAmountEl.value != result.vo.curPayTotalAmount){
					curPayTotalAmountEl.setValue(result.vo.curPayTotalAmount);
					Ext.get(curPayTotalAmountEl.getInputId()).highlight("339933", {attr : 'color',duration : 2000});
				}
				var curPayOpenAmountEl = f.findField('vo.curPayOpenAmount');
				if(curPayOpenAmountEl.value != result.vo.curPayOpenAmount){
					curPayOpenAmountEl.setValue( result.vo.curPayOpenAmount);
					Ext.get(curPayOpenAmountEl.getInputId()).highlight("FF0000", {attr : 'color',duration : 2000});
				}
				var destPayTotalAmountEl = f.findField('vo.destPayTotalAmount');
				if(destPayTotalAmountEl.value != result.vo.destPayTotalAmount){
					destPayTotalAmountEl.setValue(result.vo.destPayTotalAmount);
					Ext.get(destPayTotalAmountEl.getInputId()).highlight("339933", {attr : 'color',duration : 2000});
				}
				var destPayOpenAmountEl = f.findField('vo.destPayOpenAmount');
				if(destPayOpenAmountEl.value != result.vo.destPayOpenAmount){
					destPayOpenAmountEl.setValue( result.vo.destPayOpenAmount);
					Ext.get(destPayOpenAmountEl.getInputId()).highlight("FF0000", {attr : 'color',duration : 2000});
				}
				var noteTotalAmountEl = f.findField('vo.noteTotalAmount');
				if(noteTotalAmountEl.value != result.vo.noteTotalAmount){
					noteTotalAmountEl.setValue(result.vo.noteTotalAmount);
					Ext.get(noteTotalAmountEl.getInputId()).highlight("339933", {attr : 'color',duration : 2000});
				}
				var noteOpenAmountEl = f.findField('vo.noteOpenAmount');
				if(noteOpenAmountEl.value != result.vo.noteOpenAmount){
					noteOpenAmountEl.setValue( result.vo.noteOpenAmount);
					Ext.get(noteOpenAmountEl.getInputId()).highlight("FF0000", {attr : 'color',duration : 2000});
				}

			}
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.ux.Toast.msg(consumer.invoiceRegister.i18n('foss.stl.consumer.common.warmTips'), json.message,'error',3000);
			consumer.invoiceRegister.resetAllAmount(f);
			me.enable(true);
		},
		failure:function(form,action){
			consumer.invoiceRegister.resetAllAmount(f);
			me.enable(true);
		},
		unknownException:function(form,action){
			consumer.invoiceRegister.resetAllAmount(f);
			me.enable(true);
		}
	});

}

/**
 * 清空金额
 * @param form
 */
consumer.invoiceRegister.resetAllAmount = function(form){
	form.findField('vo.curPayTotalAmount').setValue(0);
	form.findField('vo.curPayOpenAmount').setValue(0);
	form.findField('vo.destPayTotalAmount').setValue(0);
	form.findField('vo.destPayOpenAmount').setValue(0);
	form.findField('vo.noteTotalAmount').setValue(0);
	form.findField('vo.noteOpenAmount').setValue(0);
}

/**
 * 总金额,可开票金额变化后，金额变化效果
 * @param totalEl 总金额El/可开票El
 * @param totalAmountEl 总金额El/可开票El --ext el
 * @param resultTotalAmount Ajax请求后返回的总金额/可开票金额
 */
consumer.invoiceRegister.changeAmountColor = function(totalEl,totalAmountEl,resultTotalAmount){
	if(resultTotalAmount>totalAmountEl.value){
		totalAmountEl.setValue(totalAmountEl.value+"+"+(stl.amountSub(resultTotalAmount,totalAmountEl.value)));
		totalEl.slideIn('r',{ easing: 'easeOut',duration: 500});
		totalEl.addCls('label-class-green');

		setTimeout(function(){
			totalEl.removeCls('label-class-green');
			totalAmountEl.setValue(resultTotalAmount);
			totalEl.highlight("339933", {attr : 'color',duration : 500});
		},500);

	}else{
		totalAmountEl.setValue(totalAmountEl.value+"-"+(stl.amountSub(totalAmountEl.value,resultTotalAmount)));
		totalEl.slideOut('r',{ easing: 'easeIn',duration: 500});
		totalEl.addCls('label-class-red');
		setTimeout(function(){
			totalEl.removeCls('label-class-red');
			totalAmountEl.setValue(resultTotalAmount);
			totalEl.highlight("FF0000", {attr : 'color',duration : 500});
		},500);
	}
}

/**
 * 监听本次开票金额输入
 * @param form
 * @param 被监听组件 本次开票金额框
 */
consumer.invoiceRegister.thisAmountChangeLisn = function(form,el){
	var curPayOpenAmountEl = form.getForm().findField('vo.curPayOpenAmount');
	var curPayTotalAmountEl = form.getForm().findField('vo.curPayTotalAmount')
	var destPayOpenAmountEl = form.getForm().findField('vo.destPayOpenAmount');
	var destPayTotalAmountEl = form.getForm().findField('vo.destPayTotalAmount');
	var noteOpenAmountEl = form.getForm().findField('vo.noteOpenAmount');
	var noteTotalAmountEl = form.getForm().findField('vo.noteTotalAmount');
	var totalAmount =  stl.amountAdd(stl.amountAdd(curPayTotalAmountEl.value,destPayTotalAmountEl.value),noteTotalAmountEl.value);
	var openAmount = stl.amountAdd(stl.amountAdd(curPayOpenAmountEl.value,destPayOpenAmountEl.value),noteOpenAmountEl.value);


	if(el.value <= 0){
		Ext.ux.Toast.msg(consumer.invoiceRegister.i18n('foss.stl.consumer.common.warmTips'), consumer.invoiceRegister.i18n('foss.stl.consumer.invoiceRegister.thisAmountgreaterThanZero'),'error',3000);
		return false;
	}
	if(totalAmount <= 0){
		Ext.ux.Toast.msg(consumer.invoiceRegister.i18n('foss.stl.consumer.common.warmTips'), consumer.invoiceRegister.i18n('foss.stl.consumer.invoiceRegister.totalAmountgreaterThanZero'),'error',3000);
		return false;
	}
	
	if(openAmount <= 0){
		if(!Ext.isEmpty(form.getForm().findField('vo.waybillNOs').value) || !Ext.isEmpty(form.getForm().findField('vo.otherRevenueNos').value)){
		 
				Ext.Msg.alert(consumer.invoiceRegister.i18n('foss.stl.consumer.common.warmTips'),consumer.invoiceRegister.i18n('foss.stl.consumer.invoiceRegister.openAmountgreaterThanZero'));
		 
				Ext.getCmp('invoiceErrID').setText('<span style="color:red;font-weight:bold">&nbsp;*&nbsp;'
						+ consumer.invoiceRegister.i18n('foss.stl.consumer.invoiceRegister.CannotReopenBill'));
				
				Ext.Function.defer(function(){
					Ext.getCmp('invoiceErrID').setText('');
				}, 5000);
				
		}else{
			Ext.getCmp('invoiceErrID').setText('<span style="color:red;font-weight:bold">&nbsp;*&nbsp;'
					+ consumer.invoiceRegister.i18n('foss.stl.consumer.invoiceRegister.CannotReopenBill'));
			
			Ext.Function.defer(function(){
				Ext.getCmp('invoiceErrID').setText('');
			}, 5000);
		}
		
		return false;
	}
	
	
	
	if(!( stl.amountSub(el.value,openAmount) <= 100)){
		Ext.ux.Toast.msg(consumer.invoiceRegister.i18n('foss.stl.consumer.common.warmTips'), consumer.invoiceRegister.i18n('foss.stl.consumer.invoiceRegister.CannotOutAmount'),'error',3000);
		return false;
	}

	return true;
}

/**
 * 提交定额发票
 * @param form
 */
consumer.invoiceRegister.registerAmountSaveFun = function(form,me){
	var f = form.getForm();
	var waybillEl = f.findField('vo.waybillNOs');
	var otherRevenueEl = f.findField('vo.otherRevenueNos');

	var thisAmountEl = f.findField('vo.thisAmount');

	if(Ext.isEmpty(waybillEl.value ) && Ext.isEmpty(otherRevenueEl.value )){
		Ext.ux.Toast.msg(consumer.invoiceRegister.i18n('foss.stl.consumer.common.warmTips'), consumer.invoiceRegister.i18n('foss.stl.consumer.invoiceRegister.WaybillSmallMustSelectOne'),'error',3000);
		me.enable(true);
		return false;
	}

	if(!consumer.invoiceRegister.thisAmountChangeLisn(form,thisAmountEl)){
		me.enable(true);
		return false;
	}
	
	var paramsV = f.getValues();

	var waybillNoArr;
	var waybillStr = paramsV['vo.waybillNOs'];
	if(!Ext.isEmpty(waybillStr)){
		waybillNoArr = waybillStr.match(/\d+/g);
	}

	var noteNoArr;
	var noteNoStr = paramsV['vo.otherRevenueNos'];
	if(!Ext.isEmpty(noteNoStr)){
		noteNoArr = noteNoStr.match(/\d+/g);
	}

	var paramsdata = {
		'vo': {
			'waybillNOs': waybillNoArr,
			'otherRevenueNos': noteNoArr,
			'curPayTotalAmount':paramsV['vo.curPayTotalAmount'],
			'curPayOpenAmount':paramsV['vo.curPayOpenAmount'],
			'destPayTotalAmount':paramsV['vo.destPayTotalAmount'],
			'destPayOpenAmount':paramsV['vo.destPayOpenAmount'],
			'noteTotalAmount':paramsV['vo.noteTotalAmount'],
			'noteOpenAmount':paramsV['vo.noteOpenAmount'],
			'thisAmount':paramsV['vo.thisAmount']
		}
	};

	Ext.Ajax.request({
		url:consumer.realPath('registerDoInvoice.action'),
		method : "POST",
		jsonData:paramsdata,
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(!result.vo.success){
				//如果查询返回总金额=0，则运单或者小票有无效的。
				var unWaybillArr = result.vo.unWaybillNOs;
				var unOtherRevenueNoArr = result.vo.unOtherRevenueNos;
				//无效运单号和无效小票号都存在
				if(!Ext.isEmpty(unWaybillArr) && !Ext.isEmpty(waybillNoArr)&&!Ext.isEmpty(unOtherRevenueNoArr)&&!Ext.isEmpty(noteNoArr)){
					var mark = false;
					for (var i=0; i < waybillNoArr.length; i++){
						for (var j=0; j < unWaybillArr.length; j++){
							if(waybillNoArr[i] == unWaybillArr[j]){
								waybillNoArr[i] = '['+waybillNoArr[i]+']';
								mark = true;
							}
						}
					}
					for (var i=0; i < noteNoArr.length; i++){
						for (var j=0; j < unOtherRevenueNoArr.length; j++){
							if(noteNoArr[i] == unOtherRevenueNoArr[j]){
								noteNoArr[i] = '['+noteNoArr[i]+']';
								mark = true;
							}
						}
					}
					if(mark){
						waybillEl.setValue(waybillNoArr);
						otherRevenueEl.setValue(noteNoArr);
						Ext.ux.Toast.msg(consumer.invoiceRegister.i18n('foss.stl.consumer.common.warmTips'), consumer.invoiceRegister.i18n('foss.stl.consumer.invoiceRegister.SquareBracketsInvalidWaybillSmall'),'error',3000);
						consumer.invoiceRegister.resetAllAmount(f);
						me.enable(true);
						return false;
					}
				}//无效运单号不存在，只存在无效小票号
				else if(Ext.isEmpty(unWaybillArr) && !Ext.isEmpty(waybillNoArr)&&!Ext.isEmpty(unOtherRevenueNoArr)&&!Ext.isEmpty(noteNoArr)){
					var mark = false;
					for (var i=0; i < noteNoArr.length; i++){
						for (var j=0; j < unOtherRevenueNoArr.length; j++){
							if(noteNoArr[i] == unOtherRevenueNoArr[j]){
								noteNoArr[i] = '['+noteNoArr[i]+']';
								mark = true;
							}
						}
					}
					if(mark){
						waybillEl.setValue(waybillNoArr);
						otherRevenueEl.setValue(noteNoArr);
						Ext.ux.Toast.msg(consumer.invoiceRegister.i18n('foss.stl.consumer.common.warmTips'), consumer.invoiceRegister.i18n('foss.stl.consumer.invoiceRegister.SquareBracketsInvalidWaybillSmall'),'error',3000);
						consumer.invoiceRegister.resetAllAmount(f);
						me.enable(true);
						return false;
					}
				}//无效小票号不存在，只存在无效运单号
				else if(Ext.isEmpty(unWaybillArr) && !Ext.isEmpty(waybillNoArr)&&!Ext.isEmpty(unOtherRevenueNoArr)&&!Ext.isEmpty(noteNoArr)){
					var mark = false;
					for (var i=0; i < waybillNoArr.length; i++){
						for (var j=0; j < unWaybillArr.length; j++){
							if(waybillNoArr[i] == unWaybillArr[j]){
								waybillNoArr[i] = '['+waybillNoArr[i]+']';
								mark = true;
							}
						}
					}
					if(mark){
						waybillEl.setValue(waybillNoArr);
						otherRevenueEl.setValue(noteNoArr);
						Ext.ux.Toast.msg(consumer.invoiceRegister.i18n('foss.stl.consumer.common.warmTips'), consumer.invoiceRegister.i18n('foss.stl.consumer.invoiceRegister.SquareBracketsInvalidWaybillSmall'),'error',3000);
						consumer.invoiceRegister.resetAllAmount(f);
						me.enable(true);
						return false;
					}
				}

			}

			if(result.vo.success){
				consumer.invoiceRegister.resetAllAmount(f);
				Ext.ux.Toast.msg(consumer.invoiceRegister.i18n('foss.stl.consumer.common.warmTips'), consumer.invoiceRegister.i18n('foss.stl.consumer.invoiceRegister.SuccessInvoice'),'ok',3000);
			}
			me.enable(true);
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.ux.Toast.msg(consumer.invoiceRegister.i18n('foss.stl.consumer.common.warmTips'), json.message,'error',3000);
			me.enable(true);
		},
		failure:function(form,action){
			me.enable(true);
		},
		unknownException:function(form,action){
			me.enable(true);
		}
	});
}


var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
	clicksToEdit: 1
});
/**
 * 定额发票计算器
 */
Ext.define('Foss.invoiceRegister.invoiceRegisterCalculatorGrid', {
	alias:'widget.Foss.invoiceRegister.invoiceRegisterCalculatorGrid',
	id:'Foss_invoiceRegister_invoiceRegisterCalculatorGrid_ID',
	extend : 'Ext.grid.Panel',
	title : "定额发票计算器",
	collapsible: true,collapsed:true,
	bodyCls : 'autoHeight',
	bodyStyle : {
		padding : '0px'
	},
	frame : true,
	store:FossDataDictionary.getDataDictionaryStore('INVOICE__INVOICE_VALUE_TYPE',null,null,null),
	columns :{
		defaults : {
			sortable : false,
			draggable : false
		},items:[ {
			header : '发票金额',
			dataIndex : 'valueCode',
			hidden:true,
			menuDisabled:true,
			sortable: false,
			renderer : function(value, meta, record){
				return parseInt(value);
			}
		}, {
			header : '发票面值',
			dataIndex : 'valueName',
			width:90,
			align: 'right',
			menuDisabled:true,
			sortable: false
		}, {
			header : '张数',
			dataIndex : 'extAttribute1',
			width:120,
			align: 'right',
			editor: {
				xtype: 'numberfield',
				allowBlank: false,
				allowDecimals: false,
				minValue: 0,
				value:0,
				listeners:{
					blur:function(_this, _The, eOpts ){
						var grid = this.up('grid');
						var totalAmount = 0;
						setTimeout(function(){
							grid.getStore().each(function(record){
								if(!Ext.isEmpty(record.get('extAttribute1'))&&parseInt(record.get('extAttribute1'))>0){
									var oneAmount = parseInt(record.get('extAttribute1'))*record.get('valueCode');
									totalAmount = totalAmount + oneAmount;
								}
							});

							Ext.getCmp('invoiceTotalID').setText(totalAmount);
							Ext.get('invoiceTotalID').highlight("FF0000", {attr : 'color',duration : 1000});
						},1000);

					}
				}
			},
			menuDisabled:true,
			sortable: false
		} , {
			header : '金额',
			width:140,
			align: 'right',
			dataIndex : 'extAttribute2',
			renderer : function(value, meta, record){
				var incoiceAmount = record.get('valueCode');//incoiceAmount列的值
				var number = record.get('extAttribute1');//incoiceAmount列的值
				return incoiceAmount*number;
			},
			menuDisabled:true,
			sortable: false
		}
		]
	},
	dockedItems: [{
		xtype: 'toolbar',
		dock: 'top',
		layout:'column',

		items: [{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			columnWidth:.8
		},{
			xtype:'button',
			text:'重置',
			columnWidth:.2,
			handler:function(){
				var grid = this.up('grid');
				grid.getStore().each(function(record){
					record.set('extAttribute1',0);
					record.commit();
				});
				Ext.getCmp('invoiceTotalID').setText('0');
			}
		}]
	},{
		xtype: 'toolbar',
		dock: 'bottom',
		layout:'column',
		defaults:{
			margin:'0 0 5 0'
		},
		items: [{
			height:5,
			columnWidth:1
		},{
			xtype:'tbtext',
			readOnly:true,
			text:'定额发票累计金额：',
			columnWidth:0.35
		},{
			xtype:'tbtext',
			readOnly:true,
			id:'invoiceTotalID',
			name:'totalRows',
			text:'0',
			columnWidth:0.35
		}]
	}
	],
	plugins: [cellEditing]
});

// 定义定额发票登记form
Ext
	.define(
	'Foss.invoiceRegister.invoiceRegisterForm',
	{
		extend : 'Ext.form.Panel',
		frame:true,
		title:consumer.invoiceRegister.i18n('foss.stl.consumer.invoiceRegister.invoiceRegister'),
		layout : {
			type : 'column'
		},
		defaults : {
			msgTarget : 'qtip',
			allowBlank : true,
			margin : '10 0 10 0',
			columnWidth : .25
		},
		items : [
			{
				//运单视图
				border : 1,
				xtype : 'container',
				columnWidth : .6,
				defaultType : 'textfield',
				layout : 'column',
				items : [
					{
						xtype : 'textarea',
						fieldLabel :consumer.invoiceRegister.i18n('foss.stl.consumer.invoiceRegister.waybillNo'),
						name : 'vo.waybillNOs',
						columnWidth : .9,
						height : 80,
						emptyText : consumer.invoiceRegister.i18n('foss.stl.consumer.invoiceRegister.wayBillNosMustInputNum'),
						regex : /^(\s*[0-9]{8,10}\s*)([, \n \r (\r\n) (\u0085) (\u2028) (\u2029]{1}\s*[0-9]{8,10}\s*){0,149}[, \n \r (\r\n) (\u0085) (\u2028) (\u2029]{0,1}?$/i,
						regexText : consumer.invoiceRegister.i18n('foss.stl.consumer.invoiceRegister.billMustNumberWayBillNosMustInputNum'),
						//ddw
						listeners:{
							blur:function(_this, the,eOpts ){
								var form = this.up('form');
								consumer.invoiceRegister.waybillNosToOtherRevenueNos(form,this);
							}
						}
					},{
						xtype:'container',
						html:'&nbsp;',
						height : 20,
						columnWidth:.1
					},{
						xtype:'container',
						html:'&nbsp;',
						height : 20,
						columnWidth:.7
					},{
						xtype : 'button',
						text : '更新可开票余额',
						cls:'specialbtn',
						columnWidth : .2,
						handler : function() {
							var form = this.up('form');
							var me = this;
							//设置该按钮灰掉
							me.disable(false);
							//10秒后自动解除灰掉效果
							setTimeout(function() {
								me.enable(true);
							}, 3000);

							consumer.invoiceRegister.amountChangeLisn(form,me);
						}
					},{
						xtype:'container',
						html:'&nbsp;',
						height : 20,
						columnWidth:.1
					},{
						xtype:'container',
						html:'&nbsp;',
						height : 10,
						columnWidth:1
					},
					// 现付金额
					{
						xtype : 'textfield',
						name : 'vo.curPayTotalAmount',
						columnWidth : .4,
						readOnly : true,
						value:0,
						fieldLabel : '现付总金额'
					},{
						xtype:'container',
						html:'&nbsp;',
						height : 20,
						columnWidth:.1
					},
					// 可开票金额
					{
						xtype : 'textfield',
						name : 'vo.curPayOpenAmount',
						columnWidth : .4,
						readOnly : true,
						value:0,
						fieldLabel : '现付可开票余额'
					},{
						xtype:'container',
						html:'&nbsp;',
						height : 20,
						columnWidth:.1
					},{
						xtype:'container',
						html:'&nbsp;',
						height : 10,
						columnWidth:1
					},
					// 到付金额
					{
						xtype : 'textfield',
						name : 'vo.destPayTotalAmount',
						columnWidth : .4,
						readOnly : true,
						value:0,
						fieldLabel : '到付总金额'
					},{
						xtype:'container',
						html:'&nbsp;',
						height : 20,
						columnWidth:.1
					},
					// 可开票金额
					{
						xtype : 'textfield',
						name : 'vo.destPayOpenAmount',
						columnWidth : .4,
						readOnly : true,
						value:0,
						fieldLabel : '到付可开票余额'
					},{
						xtype:'container',
						html:'&nbsp;',
						height : 20,
						columnWidth:.1
					},{
						xtype:'container',
						html:'&nbsp;',
						height : 10,
						columnWidth:1
					},
					// 小票金额
					{
						xtype : 'textfield',
						name : 'vo.noteTotalAmount',
						columnWidth : .4,
						readOnly : true,
						value:0,
						fieldLabel : '小票总金额'
					},{
						xtype:'container',
						html:'&nbsp;',
						height : 20,
						columnWidth:.1
					},
					// 可开票金额
					{
						xtype : 'textfield',
						name : 'vo.noteOpenAmount',
						columnWidth : .4,
						readOnly : true,
						value:0,
						fieldLabel : '小票可开票余额'
					},{
						xtype:'container',
						html:'&nbsp;',
						height : 20,
						columnWidth:.1
					},{
						xtype:'container',
						html:'&nbsp;',
						height : 20,
						columnWidth:1
					},
					// 本次开票金额
					{
						xtype : 'numberfield',
						name : 'vo.thisAmount',
						columnWidth : .60,
						value: 0,
						minValue: 0,
						decimalPrecision : 2,
						allowBlank : false,
						fieldLabel : consumer.invoiceRegister.i18n('foss.stl.consumer.invoiceRegister.thisAmount'),
						listeners:{
							change:function(_this,newValue,oldValue,eOpts){
								var form = this.up('form');
								consumer.invoiceRegister.thisAmountChangeLisn(form,_this);
							},
							blur:function(_this, the,eOpts ){
								var form = this.up('form');
								consumer.invoiceRegister.thisAmountChangeLisn(form,_this);
							}
						}
					},{						
						xtype:'tbtext',
						readOnly:true,
						id:'invoiceErrID',
						text:'',
						height : 50,
						columnWidth:1
					},
					{
						border : 1,
						xtype : 'container',
						columnWidth : 1,
						defaultType : 'button',
						layout : 'column',
						items : [
							{
								text : consumer.invoiceRegister
									.i18n('foss.stl.consumer.common.reset'),
								columnWidth : .1,
								handler : function() {
									// 重置
									this.up('form').getForm().reset();
									consumer.invoiceRegister.noteInputValue = null;
									consumer.invoiceRegister.waybillInputValue = null;
								}
							},
							{
								xtype : 'container',
								border : false,
								html : '&nbsp;',
								columnWidth : .7
							},
							{
								text : consumer.invoiceRegister
									.i18n('foss.stl.consumer.common.commit'),
								cls : 'yellow_button',
								columnWidth : .1,
								handler : function() {
									var form = this.up('form');
									var me = this;
									//设置该按钮灰掉
									me.disable(false);
									//10秒后自动解除灰掉效果
									setTimeout(function() {
										me.enable(true);
									}, 10000);

									setTimeout(function(){
										consumer.invoiceRegister.registerAmountSaveFun(form,me);
									},3000);

								}
							} ]
					}
				]
			},{
				//小票视图包括计算器
				border : 1,
				xtype : 'container',
				columnWidth : .4,
				defaultType : 'textfield',
				layout : 'column',
				items : [
					//小票单号
					{
						xtype : 'textarea',
						fieldLabel : consumer.invoiceRegister.i18n('foss.stl.consumer.otherRevenue.otherRevenueNo'),
						name : 'vo.otherRevenueNos',
						columnWidth : 1,
						height : 80,
						emptyText : consumer.invoiceRegister.i18n('foss.stl.consumer.invoiceRegister.smallsMustInputNum'),
						//allowBlank : false,
						regex : /^(\s*[0-9]{8,10}\s*)([, \n \r (\r\n) (\u0085) (\u2028) (\u2029]{1}\s*[0-9]{8,10}\s*){0,149}[, \n \r (\r\n) (\u0085) (\u2028) (\u2029]{0,1}?$/i,
						regexText :consumer.invoiceRegister.i18n('foss.stl.consumer.invoiceRegister.billMustNumberSmallsMustInputNum')
					},{
						xtype:'container',
						html:'&nbsp;',
						height : 15,
						columnWidth:1
					},{
						xtype : 'Foss.invoiceRegister.invoiceRegisterCalculatorGrid',
						columnWidth : 1

					}
				]
			}],
		loadMask:null

	});

// 显示
Ext.onReady(function() {
	Ext.QuickTips.init();

	// 创建面板
	var invoiceRegisterFormVar = Ext.create(
		'Foss.invoiceRegister.invoiceRegisterForm', {
			id : 'Foss_invoiceRegister_invoiceRegisterForm_ID'
		});


	// 显示到JSP页面
	Ext.create('Ext.panel.Panel', {
		id : 'T_consumer-invoiceRegister_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [ invoiceRegisterFormVar ],
		renderTo : 'T_consumer-invoiceRegister-body'
	});
});