/**
 * 查询日期限制
 */
pay.queryDisableRepayment.STLBILLREPAYMENT_MAX = 31; // 时间间隔最大不超过90天
pay.queryDisableRepayment.STLBILLREPAYMENT_ONEMONTH = 1; // 时间相差一月
pay.queryDisableRepayment.STLREVERSEWRITEOFF_ONEPAGESIZE = 50; // 每页显示的条数

// 按日期、按还款单
pay.queryDisableRepayment.queryRepayment_TD = 'TD';
pay.queryDisableRepayment.queryRepayment_RT = 'RT';

pay.queryDisableRepayment.SETTLEMENT__PAYMENT_TYPE__CASH='CH';
pay.queryDisableRepayment.SETTLEMENT__PAYMENT_TYPE__CARD='CD';
pay.queryDisableRepayment.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER='TT';
pay.queryDisableRepayment.SETTLEMENT__PAYMENT_TYPE__NOTE='NT';
pay.queryDisableRepayment.SETTLEMENT__PAYMENT_TYPE__ONLINE='OL';

pay.queryDisableRepayment.dept = FossUserContext.getCurrentUserDept();// 当前登录部门
pay.queryDisableRepayment.user = FossUserContext.getCurrentUser();// 当前登录用户
pay.queryDisableRepayment.roles = FossUserContext.getCurrentUserRoleCodes();

// 格式化金额
pay.queryDisableRepayment.convertAmount = function setMoneyFormat(val) {
	return "" + Ext.util.Format.currency(val, ' ', 2, false) + "";
}

/**
 * 提示信息
 * 
 * @param {}
 *            message
 * @param {}
 *            yesFn
 * @param {}
 *            noFn
 */
pay.billRepaymentConfirmAlert = function(message, yesFn, noFn) {
	Ext.Msg.confirm(pay.queryDisableRepayment.i18n('foss.stl.pay.common.alert'), message, function(o) {
		if (o == 'yes') {
			yesFn();
		} else {
			noFn();
		}
	});
};

// 还款单查询Form重置
pay.queryDisableRepayment.reset = function() {
	this.up('form').getForm().reset();
	var form = this.up('form').getForm().reset();
	var orgNameSeletor = form.findField('orgName');
	if (!Ext.isEmpty(stl.currentDept.code)) {

		var displayText = stl.currentDept.name;
		var valueText = stl.currentDept.code;

		var store = orgNameSeletor.store;
		var key = orgNameSeletor.displayField + '';
		var value = orgNameSeletor.valueField + '';

		var m = Ext.create(store.model.modelName);
		m.set(key, displayText);
		m.set(value, valueText);

		store.loadRecords([ m ]);
		orgNameSeletor.setValue(valueText);
	}
	
	Ext.getCmp('pay.queryDisableRepayment.commoncustomerselector').show();
	Ext.getCmp('pay.queryDisableRepayment.commonairagencycompanyselector').hide();
	Ext.getCmp('pay.queryDisableRepayment.commonvehagencycompselector').hide();
	Ext.getCmp('pay.queryDisableRepayment.commonairlinesselector').hide();
	Ext.getCmp('pay.queryDisableRepayment.landStowage').hide();
}

// 查询方法
pay.queryDisableRepayment.queryByRepayment = function(me, btn, queryType) {

	var form = me.getForm();
	if (form.isValid()) {
		var gridRepaymentGrid = Ext.getCmp('T_pay-queryDisableRepayment_content').getQueryGrid();
		var params = pay.queryDisableRepayment.queryByRepaymentSetParam(form, queryType);

		if (null == params || params == false) {
			return;
		}
		// 设置查询参数
		gridRepaymentGrid.store.setSubmitParams(params);
		// 设置该按钮灰掉
		btn.disable(false);
		// 30秒后自动解除灰掉效果
		setTimeout(function() {
			btn.enable(true);
		}, 30000);
		// 查询后台
		gridRepaymentGrid.store.loadPage(1, {
			callback : function(records, operation, success) {
				var rawData = gridRepaymentGrid.store.proxy.reader.rawData;
				// 当success:false ,isException:false --业务异常
				if (!success && !rawData.isException) {
					Ext.Msg.alert(pay.queryDisableRepayment.i18n('foss.stl.pay.common.alert'), rawData.message);
					btn.enable(true);
					return false;
				}
				if (success) {
					var result = Ext.decode(operation.response.responseText);
					toolBar = gridRepaymentGrid.getDockedItems('toolbar[dock="bottom"]')[0];
					// toolBar = gridRepaymentGrid.down('toolbar');
					var resultDto = result.billRepaymentDisableResultVo.billRepaymentDisableResultDto;
					var totalRepayAmount = pay.queryDisableRepayment.convertAmount(resultDto.totalRepayAmount);
					var totalApplyAmount = pay.queryDisableRepayment.convertAmount(resultDto.totalApplyAmount);

					toolBar.getComponent(1).setValue(resultDto.totalCount);
					toolBar.getComponent(2).setValue(totalRepayAmount);
					toolBar.getComponent(3).setValue(totalApplyAmount);

					var billRepaymentLength = 0;
					if (!Ext.isEmpty(resultDto.applys)) {
						billRepaymentLength = resultDto.applys;
					}
					// 判断是否有数据提示
					if (billRepaymentLength == 0) {
						Ext.Msg.alert(pay.queryDisableRepayment.i18n('foss.stl.pay.common.alert'), pay.queryDisableRepayment
								.i18n('foss.stl.pay.queryDisableBillRepayment.queryByRepaymentIsNullRegexText'), function() {
							btn.enable(true);
							return false;
						});
					} else {
						gridRepaymentGrid.show();
					}
					btn.enable(true);
				}
			}
		});
	} else {
		Ext.Msg.alert(pay.queryDisableRepayment.i18n('foss.stl.pay.common.alert'), pay.queryDisableRepayment
				.i18n('foss.stl.pay.common.validateFailAlert'));
	}
}

// 设置还款单的查询参数
pay.queryDisableRepayment.queryByRepaymentSetParam = function(form, queryType) {
	// 定义查询参数
	var params = {};

	// 按照日期查询
	if (pay.queryDisableRepayment.queryRepayment_TD == queryType) {

		// 部门名称、大区名称、小区名称

		var collectionOrgCode = form.findField('orgName').getValue();
		var largeArea = form.findField('largeAreaName').getValue();
		var smallArea = form.findField('smallAreaName').getValue();
		var paymentType = form.findField('paymentType').getValue();
		var approveStatus = form.findField('approveStatus').getValue();
		// 校验日期是否正确
		var applyDateStart = form.findField('startDate').getValue();
		var applyDateEnd = form.findField('endDate').getValue();
		// 比较起始日期和结束日期
		var compareTwoDate = stl.compareTwoDate(applyDateStart, applyDateEnd);
		if (compareTwoDate > pay.queryDisableRepayment.STLBILLREPAYMENT_MAX) {
			Ext.Msg.alert(pay.queryDisableRepayment.i18n('foss.stl.pay.common.alert'), pay.queryDisableRepayment
					.i18n('foss.stl.pay.queryDisableBillRepayment.queryDateMaxLimit'));
			return false;
		} else if (compareTwoDate < 1) {
			Ext.Msg.alert(pay.queryDisableRepayment.i18n('foss.stl.pay.common.alert'), pay.queryDisableRepayment
					.i18n('foss.stl.pay.common.dateEndBeforeStatrtWarining'));
			return false;
		}

		var customerCode;
		switch (form.findField('customerType').getValue()) {
		case '01':
			customerCode = form.findField('customerName01').value;
			break;
		case '02':
			customerCode = form.findField('customerName02').value;
			break;
		case '03':
			customerCode = form.findField('customerName03').value;
			break;
		case '04':
			customerCode = form.findField('customerName04').value;
			break;
		case '05':
			customerCode = form.findField('customerName05').value;
			break;
		default:
			customerCode = null;
			break;
		}

		// 按日期查询时，提交参数
		Ext.apply(params, {
			'billRepaymentDisableVo.billRepaymentDisableDto.applyDateStart' : applyDateStart,
			'billRepaymentDisableVo.billRepaymentDisableDto.applyDateEnd' : applyDateEnd,
			'billRepaymentDisableVo.billRepaymentDisableDto.approveStatus':approveStatus,
			'billRepaymentDisableVo.billRepaymentDisableDto.applyOrgCode' : collectionOrgCode,
			'billRepaymentDisableVo.billRepaymentDisableDto.applyAreaCode' : largeArea,
			'billRepaymentDisableVo.billRepaymentDisableDto.applyParentOrgCode' : smallArea,
			'billRepaymentDisableVo.billRepaymentDisableDto.customerCode' : customerCode,
			'billRepaymentDisableVo.billRepaymentDisableDto.paymentType' : paymentType,
			'billRepaymentDisableVo.billRepaymentDisableDto.queryType' : queryType
		});

		// 按照还款单查询
	} else if (pay.queryDisableRepayment.queryRepayment_RT == queryType) {

		// 还款单号
		var repaymentNos = form.findField('repaymentNos').getValue();
		if (repaymentNos == null || repaymentNos == '') {
			Ext.Msg.alert(pay.queryDisableRepayment.i18n('foss.stl.pay.common.alert'), pay.queryDisableRepayment
					.i18n('foss.stl.pay.queryDisableBillRepayment.queryRepaymentNoNotNull'));
			return false;
		}
		var billRepayNoArray = [];
		if (Ext.String.trim(repaymentNos) != null && Ext.String.trim(repaymentNos) != '') {
			array = stl.splitToArray(repaymentNos);
			for ( var i = 0; i < array.length; i++) {
				if (Ext.String.trim(array[i]) != '') {
					billRepayNoArray.push(array[i]);
				}
			}
			if (billRepayNoArray.length > 10) {
				Ext.Msg.alert(pay.queryDisableRepayment.i18n('foss.stl.pay.common.alert'), pay.queryDisableRepayment
						.i18n('foss.stl.pay.common.queryNosLimit'));
				return false;
			}
		} 
		Ext.apply(params, {
			'billRepaymentDisableVo.billRepaymentDisableDto.repaymentNos' : billRepayNoArray,
			'billRepaymentDisableVo.billRepaymentDisableDto.queryType' : queryType
		});
	}
	return params;
}

//导出
pay.queryDisableRepayment.repaymentForBillExport = function(){
	var me = this;
	var	columns,
		searchParams,
		arrayColumns,
		arrayColumnNames,
		grid;
	//获取表格
	grid = Ext.getCmp('T_pay-queryDisableRepayment_content').getQueryGrid();
	var tab=Ext.getCmp('T_pay-queryDisableRepayment_content').getQueryTab();
	//获取当前页签的表单
	var queryType = tab.getActiveTab().down('form').getQueryType(); 
	//获取当前选择的的form
	var form=tab.getActiveTab().down('form').getForm();

	//给客户默认值
	var customerCode = null;
	
	//如果没有查询到数据，则不能进行导出操作
	if(grid.store.data.length==0){
		Ext.Msg.alert(pay.queryDisableRepayment.i18n('foss.stl.pay.common.alert'),pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.noDataToExport'));
		return false;
	}
	
	//进行导出提示
	Ext.MessageBox.show({
        title: pay.queryDisableRepayment.i18n('foss.stl.pay.common.alert'),
        msg:pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.isExport'),
        buttons: Ext.MessageBox.YESNOCANCEL,
        buttonText:{ 
            yes:pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.defaultColumnExport'),
            no: pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.customColumnExport'),
            cancel:pay.queryDisableRepayment.i18n('foss.stl.pay.common.cancel')
        },
        fn: function(e){
        	if(e=='yes'){
        		//默认显示列
				arrayColumns = ['repaymentNo','customerCode','customerName',
				                'applyParentOrgCode','applyParentOrgName','applyAreaCode','applyAreaName','applyOrgCode',
				            	'applyOrgName','applyUserCode','applyUserName','applyTime','amount','paymentType',
				            	'disableReason','approveUserCode','approveUserName','approveOrgCode','approveOrgName',
				            	'approveTime','createTime','modifyTime','approveStatus','isAllDisable','disableNote',
				            	'repaymentAmount','disableNum','trueAmount','oaPaymentNo'];
				//默认显示列名称
				arrayColumnNames = [
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.repaymentNo'),
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.customerCode'),
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.customerName'),
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.applyParentOrgCode'),
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.applyParentOrgName'),
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.applyAreaCode'),
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.applyAreaName'),
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.applyOrgCode'),
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.applyOrgName'),
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.applyUserCode'),
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.applyUserName'), 
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.applyTime'), 
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.amount'), 
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.paymentType'), 
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.disableReason'), 
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.approveUserCode'),
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.approveUserName'),
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.approveOrgCode'), 
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.approveOrgName'),
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.approveTime'),
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.createTime'),
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.modifyTime'),
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.approveStatus'),
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.isAllDisable'),
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.disableNote'),
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.repaymentAmount'),
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.disableNum'),
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.trueAmount'),
				                    pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.oaPaymentNo')
				                   ];									
        	}else if(e=='no'){
        		//转化列头和列明
				columns = grid.columns;
				arrayColumns = [];
				arrayColumnNames = [];
				//将前台对应列头传入到后台去
				for(var i=1;i<columns.length;i++){
					if(columns[i].isHidden()==false){
						var hederName = columns[i].text;
						var dataIndex = columns[i].dataIndex;
						if(columns[i].text!=pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.actionColumn')){
							arrayColumns.push(dataIndex);
							arrayColumnNames.push(hederName);
						}
					}
				}
        	}else{
        		return false;
        	}
   			
        	if(!Ext.fly('downloadAttachFileForm')){
			    var frm = document.createElement('form');
			    frm.id = 'downloadAttachFileForm';
			    frm.style.display = 'none';
			    document.body.appendChild(frm);
			}
			
			//按日期查询
			if(pay.queryDisableRepayment.queryRepayment_TD==queryType){
				//客户名称、部门名称、大区名称、小区名称、审核状态、还款方式
				var custType = form.findField('customerType').getValue();
				//业务日期、账户日期
				var applyDateStart = form.findField('startDate').getValue();;
				var applyDateEnd = form.findField('endDate').getValue();;
				
				
			
				//客户类型
				if(custType=='01'){
					customerCode=Ext.getCmp('pay.queryDisableRepayment.commoncustomerselector').getValue();
				}else if(custType=='02'){
					customerCode=Ext.getCmp('pay.queryDisableRepayment.commonairagencycompanyselector').getValue();
				}else if(custType=='03'){
					customerCode=Ext.getCmp('pay.queryDisableRepayment.commonvehagencycompselector').getValue();
				}else if(custType=='04'){ 
					customerCode=Ext.getCmp('pay.queryDisableRepayment.commonairlinesselector').getValue();
				}else if(custType=='05'){
					customerCode=Ext.getCmp('pay.queryDisableRepayment.landStowage').getValue();
				}
				
				//部门名称
				var collectionOrgCode=form.findField('orgName').getValue();
				//大区名称
				var largeArea =form.findField('largeAreaName').getValue();
				//小区名称
				var smallArea=form.findField('smallAreaName').getValue();
				//审核状态
				var approveStatus=form.findField('approveStatus').getValue();
				var paymentType=form.findField('paymentType').getValue();
	
				searchParams = {
						'billRepaymentDisableVo.billRepaymentDisableDto.applyDateStart' : applyDateStart,
			'billRepaymentDisableVo.billRepaymentDisableDto.applyDateEnd' : applyDateEnd,
			'billRepaymentDisableVo.billRepaymentDisableDto.approveStatus':approveStatus,
			'billRepaymentDisableVo.billRepaymentDisableDto.applyOrgCode' : collectionOrgCode,
			'billRepaymentDisableVo.billRepaymentDisableDto.applyAreaCode' : largeArea,
			'billRepaymentDisableVo.billRepaymentDisableDto.applyParentOrgCode' : smallArea,
			'billRepaymentDisableVo.billRepaymentDisableDto.customerCode' : customerCode,
			'billRepaymentDisableVo.billRepaymentDisableDto.paymentType' : paymentType,
			'billRepaymentDisableVo.billRepaymentDisableDto.queryType' : queryType,
			'billRepaymentDisableVo.billRepaymentDisableDto.arrayColumns':arrayColumns,
					'billRepaymentDisableVo.billRepaymentDisableDto.arrayColumnNames':arrayColumnNames
				
				};
			}else if(pay.queryDisableRepayment.queryRepayment_RT==queryType){
				//还款单号
				var repaymentNos=form.findField('repaymentNos').getValue();
				if(repaymentNos==null || repaymentNos==''){
					Ext.Msg.alert(pay.queryDisableRepayment.i18n('foss.stl.pay.common.alert'),pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.queryRepaymentNoNotNull'));
					return false;
				}
				var billRepayNoArray =[];
				if(Ext.String.trim(repaymentNos)!=null && Ext.String.trim(repaymentNos)!=''){
					 array = stl.splitToArray(repaymentNos);
					 for(var i=0;i<array.length;i++){
					 	if(Ext.String.trim(array[i])!=''){
					 		billRepayNoArray.push(array[i]);	
					 	}
					 }
					 if(billRepayNoArray.length>10){
					 	Ext.Msg.alert(pay.queryDisableRepayment.i18n('foss.stl.pay.common.alert'),pay.queryDisableRepayment.i18n('foss.stl.pay.common.queryNosLimit'));
					 	return false;
					 }
				}
				searchParams = {
					'billRepaymentDisableVo.billRepaymentDisableDto.repaymentNos':billRepayNoArray,
					'billRepaymentDisableVo.billRepaymentDisableDto.queryType':queryType,
					'billRepaymentDisableVo.billRepaymentDisableDto.arrayColumns':arrayColumns,
					'billRepaymentDisableVo.billRepaymentDisableDto.arrayColumnNames':arrayColumnNames
				}
			}
			
			
			me.disable(false);
			
			//10秒后自动解除灰掉效果
			setTimeout(function() {
			me.enable(true);
			}, 10000);
			
			//拼接vo，注入到后台
			Ext.Ajax.request({
			    url:pay.realPath('exportBillRepayPaymentApply.action'),
			    form: Ext.fly('downloadAttachFileForm'),
			    method : 'post',
			    params :searchParams,
			    isUpload: true,
			    success : function(response){
			    	var result = Ext.decode(response.responseText);
			    	
			    	if(!result.success && !result.isException){
			    		Ext.Msg.alert(pay.queryDisableRepayment.i18n('foss.stl.pay.common.alert'),result.message);
			    		return false;
			    	}
					Ext.ux.Toast.msg(pay.queryDisableRepayment.i18n('foss.stl.pay.common.alert'),pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.exportSuccess'), 'success', 1000);
			    },
				exception:function(response){
					var result = Ext.decode(response.responseText);
					Ext.ux.Toast.msg(pay.queryDisableRepayment.i18n('foss.stl.pay.common.alert'),pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.exportFailed'), 'error', 1000);
				}
			});
        }
	});
}

//明细
pay.queryDisableRepayment.detailRepaymentApply = function(grid, rowIndex, colIndex) {
	var yesFn = function() {
		// 获取选中的还款单数据
		var selection = grid.getStore().getAt(rowIndex);

		// 提交后台生产对账单回执，并返回回执数据
		Ext.Ajax.request({
			url : pay.realPath('applyDetailQuery.action'),
			params : {
				'billRepaymentDisableVo.billRepaymentDisableDto.applyId' : selection.data.id
			},
			method : 'post',
			success : function(response) {
				var result = Ext.decode(response.responseText);

				// 获取弹出窗口
				win = Ext.getCmp('Foss_DisableRepaymentbillQuery_WriteoffDetailWindow_ID');
				if (win == null) {
					win = Ext.create('Foss.DisableRepaymentbill.WriteoffDetailWindow');
				}
				var resultDto = result.billRepaymentDisableResultVo.billRepaymentDisableResultDto;
				var resultDto = result.billRepaymentDisableResultVo.billRepaymentDisableResultDto;
				//加载数据
				win.items.items[0].store.loadData(resultDto.billRepaymentDisableDto.details);
				
				toolBar = win.getWriteoffDetailGrid().getDockedItems('toolbar[dock="bottom"]')[0];
				//核销明细总条数
				toolBar.getComponent(0).setValue(resultDto.totalCount);
				//核销明细总金额
				toolBar.getComponent(2).setValue(pay.queryDisableRepayment.convertAmount(resultDto.totalVerifyAmount));
				win.show();
				
				win.show();
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.Msg.alert(pay.repayment.i18n('foss.stl.pay.common.alert'), result.message);
			}
		});
	};
	var noFn = function() {
		return false;
	};
	pay.billRepaymentConfirmAlert(pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.isConfimToDetails'),
			yesFn, noFn);
};

// grid的model
Ext.define('Foss.repaymentApplyQuery.repaymentApplyModel', {
	extend : 'Ext.data.Model',
	idgen : 'uuid',// EXT在前台为每个模型额外以UUID方式生成主键
	idProperty : 'extid',// 以上生成的主键用在名叫“extid”的列
	fields : [ {
		// 额外的用于生成的EXT使用的列
		name : 'extid'
	}, {
		name : 'id'
	}, {
		name : 'repaymentNo'
	}, {
		name : 'customerCode'
	}, {
		name : 'customerName'
	}, {
		name : 'applyParentOrgCode'
	}, {
		name : 'applyParentOrgName'
	}, {
		name : 'applyAreaCode'
	}, {
		name : 'applyAreaName'
	}, {
		name : 'applyOrgCode'
	}, {
		name : 'applyOrgName'
	}, {
		name : 'applyUserCode'
	}, {
		name : 'applyUserName'
	}, {
		name : 'applyTime',
		type : 'date',
		convert : stl.longToDateConvert
	}, {
		name : 'amount',
		type : 'long'
	}, {
		name : 'paymentType'
	}, {
		name : 'disableReason'
	}, {
		name : 'approveUserCode'
	}, {
		name : 'approveUserName'
	}, {
		name : 'approveOrgCode'
	}, {
		name : 'approveOrgName'
	}, {
		name : 'approveTime',
		type : 'date',
		convert : stl.longToDateConvert
	}, {
		name : 'createTime',
		type : 'date',
		convert : stl.longToDateConvert
	}, {
		name : 'modifyTime',
		type : 'date',
		convert : stl.longToDateConvert
	}, {
		name : 'approveStatus'
	}, {
		name : 'isAllDisable'
	}, {
		name : 'disableNote'
	}, {
		name : 'repaymentAmount',
		type : 'long'
	}, {
		name : 'disableNum'
	}, {
		name : 'trueAmount',
		type : 'long'
	}, {
		name : 'oaPaymentNo'
	}

	]
});

// 查询store
Ext.define('Foss.repaymentApplyQuery.repaymentApplyStore', {
	extend : 'Ext.data.Store',
	pageSize : pay.queryDisableRepayment.STLREVERSEWRITEOFF_ONEPAGESIZE,
	model : 'Foss.repaymentApplyQuery.repaymentApplyModel',
	sorters : [ {
		property : 'applyTime',
		direction : 'ASC'
	} ],
	proxy : {
		type : 'ajax',
		url : pay.realPath('queryBillRepaymentApply.action'),
		actionMethods : 'post',
		reader : {
			type : 'json',
			root : 'billRepaymentDisableResultVo.billRepaymentDisableResultDto.applys',
			totalProperty : 'totalCount'
		}
	},
	submitParams : {},
	setSubmitParams : function(submitParams) {
		this.submitParams = submitParams;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.listeners = {
			'beforeload' : function(store, operation, eOpts) {
				Ext.apply(me.submitParams, {
					"limit" : operation.limit,
					"page" : operation.page,
					"start" : operation.start
				});
				Ext.apply(operation, {
					params : me.submitParams
				});
			}
		};
		me.callParent([ cfg ]);
	}
});

// 客户类型：Model
Ext.define('Foss.pay.stlqueryDisableRepayment.CustomerTypeModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'customerTypeCode'
	}, {
		name : 'customerTypeName'
	} ]

});

// 客户类型：store
Ext.define('Foss.pay.stlqueryDisableRepayment.CustomerTypeStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pay.stlqueryDisableRepayment.CustomerTypeModel',
	data : {
		'items' : [ {
			customerTypeCode : '01',
			customerTypeName : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.customer')
		}, {
			customerTypeCode : '02',
			customerTypeName : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.airAgeny')
		}, {
			customerTypeCode : '03',
			customerTypeName : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.vehAgency')
		}, {
			customerTypeCode : '04',
			customerTypeName : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.airline')
		}, {
			customerTypeCode : '05',
			customerTypeName : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.landStowage')
		} ]
	},
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items'
		}
	}
});

// 按日期查询 Form
Ext.define('Foss.disablerepayment.StlBillRepaymentQueryByDateFrom', {
	extend : 'Ext.form.Panel',
	frame : false,
	columnWidth : 1,
	height : 180,
	width : 800,
	defaults : {
		margin : '5 5 5 5',
		labelWidth : 90,
		labelAlign : 'left',
		labelSeparator : ':'
	},
	getQueryType : function() {
		return pay.queryDisableRepayment.queryRepayment_TD = 'TD';
	},
	defaultType : 'textfield',
	layout : 'column',
	items : [ {
		xtype : 'container',
		height : 10,
		border : false,
		html : '&nbsp;',
		columnWidth : 1
	}, {
		xtype : 'datefield',
		columnWidth : 0.33,
		name : 'startDate',
		fieldLabel : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.startDate'),
		value : stl.getTargetDate(new Date(), -7),
		format : 'Y-m-d',
		allowBlank : false
	}, {
		xtype : 'datefield',
		name : 'endDate',
		columnWidth : 0.33,
		fieldLabel : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.endDate'),
		format : 'Y-m-d',
		value : new Date(),
		allowBlank : false,
		maxValue : new Date()
	}, {
    	xtype: 'combobox',
		name:'approveStatus',
        fieldLabel:pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.approveStatus'),
        columnWidth:.33,
        editable:false,
    	store:Ext.create('Ext.data.Store', {
    	    fields: ['code', 'name'],
    	    data : [
    	        {"code":'', "name":pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.all')},
    	        {"code":'NA', "name":pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.approveStatusNA')},
    	        {"code":'AG', "name":pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.approveStatusAG')},
    	        {"code":'AA', "name":pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.approveStatusAA')},
    	        {"code":'AD', "name":pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.approveStatusAD')}
    	    ]
    	}),
		queryModel:'local',
		displayField:'name',
		valueField:'code',
		value:''
    },{
		xtype : 'linkagecomboselector',
		eventType : [ 'focus' ],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
		itemId : 'Foss_baseinfo_BigRegion_ID',
		store : Ext.create('Foss.baseinfo.commonSelector.BigRegionStore'),// 从外面传入
		columnWidth : .33,
		fieldLabel : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.largeRegionSearch'),
		name : 'largeAreaName',
		isPaging : true,
		allowBlank : true,
		value : '',
		minChars : 0,
		displayField : 'name',// 显示名称
		valueField : 'code',
		minChars : 0,
		queryParam : 'commonOrgVo.name'
	}, {
		xtype : 'linkagecomboselector',
		itemId : 'Foss_baseinfo_SmallRegion_ID',
		eventType : [ 'callparent' ],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
		store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
		columnWidth : .33,
		fieldLabel : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.smallReginSearch'),
		name : 'smallAreaName',
		allowBlank : true,
		isPaging : true,
		parentParamsAndItemIds : {
			'commonOrgVo.code' : 'Foss_baseinfo_BigRegion_ID'
		},// 此处城市不需要传入
		minChars : 0,
		displayField : 'name',// 显示名称
		valueField : 'code',
		minChars : 0,
		queryParam : 'commonOrgVo.name'
	}, {
		xtype : 'linkagecomboselector',
		eventType : [ 'callparent' ],
		store : Ext.create('Foss.baseinfo.commonSelector.SmallRegionStore'),// 从外面传入
		columnWidth : .33,
		fieldLabel : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.repayOrgSearch'),
		name : 'orgName',
		allowBlank : true,
		isPaging : true,
		parentParamsAndItemIds : {
			'commonOrgVo.code' : 'Foss_baseinfo_SmallRegion_ID'
		},// 此处区域不需要传入
		minChars : 0,
		displayField : 'name',// 显示名称
		valueField : 'code',
		minChars : 0,
		queryParam : 'commonOrgVo.name'
	}, {
		xtype : 'combobox',
		name : 'customerType',
		columnWidth : .33,
		fieldLabel : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.customerType'),
		store : Ext.create('Foss.pay.stlqueryDisableRepayment.CustomerTypeStore'),
		queryModel : 'local',
		value : '01',
		editable : false,
		displayField : 'customerTypeName',
		valueField : 'customerTypeCode',
		listeners : {
			"select" : {
				fn : function(_combo, _r) {
					var cusValue = _combo.ownerCt.getForm().findField('customerType').getValue();
					// 客户信息
					Ext.getCmp('pay.queryDisableRepayment.commoncustomerselector').hide();
					// 空运代理公司
					Ext.getCmp('pay.queryDisableRepayment.commonairagencycompanyselector').hide();
					// 偏线代理公司
					Ext.getCmp('pay.queryDisableRepayment.commonvehagencycompselector').hide();
					// 航空公司
					Ext.getCmp('pay.queryDisableRepayment.commonairlinesselector').hide();
					// 快递代理
					Ext.getCmp('pay.queryDisableRepayment.landStowage').hide();

					switch (cusValue) {
					case '01':
						Ext.getCmp('pay.queryDisableRepayment.commoncustomerselector').show();
						break;
					case '02':
						Ext.getCmp('pay.queryDisableRepayment.commonairagencycompanyselector').show();
						break;
					case '03':
						Ext.getCmp('pay.queryDisableRepayment.commonvehagencycompselector').show();
						break;
					case '04':
						Ext.getCmp('pay.queryDisableRepayment.commonairlinesselector').show();
						break;
					case '05':
						Ext.getCmp('pay.queryDisableRepayment.landStowage').show();
						break;
					}
				}
			}
		}
	}, {
		xtype : 'commoncustomerselector',
		listWidth : 300,
		all : 'true',
		fieldLabel : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.customerInfo'),
		name : 'customerName01',
		singlePeopleFlag : 'Y',
		id : 'pay.queryDisableRepayment.commoncustomerselector',
		columnWidth : .33,// 分页
		isPaging : true
	}, {
		xtype : 'commonairagentselector',
		all : 'true',
		fieldLabel : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.airAgenyCompany'),
		columnWidth : .33,
		name : 'customerName02',
		id : 'pay.queryDisableRepayment.commonairagencycompanyselector',
		isPaging : true,// 分页
		hidden : true
	}, {
		xtype : 'commonvehagencycompselector',
		all : 'true',
		fieldLabel : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.vehAgencyCompany'),
		columnWidth : .33,
		name : 'customerName03',
		id : 'pay.queryDisableRepayment.commonvehagencycompselector',
		isPaging : true,// 分页
		hidden : true
	}, {
		xtype : 'commonairlinesselector',
		all : 'true',
		fieldLabel : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.airlineCompany'),
		columnWidth : .33,
		name : 'customerName04',
		id : 'pay.queryDisableRepayment.commonairlinesselector',
		isPaging : true,// 分页
		hidden : true
	}, {
		xtype : 'commonLdpAgencyCompanySelector',
		fieldLabel : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.landStowage'),
		name : 'customerName05',
		id : 'pay.queryDisableRepayment.landStowage',
		allowBlank : true,
		columnWidth : .33,
		listWidth : 300,// 设置下拉框宽度
		isPaging : true, // 分页
		hidden : true
	},
	{
    	xtype: 'combobox',
		name:'paymentType',
        fieldLabel:pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.paymentType'),
        columnWidth:.33,
        editable:false,
    	store:FossDataDictionary.getDataDictionaryStore(settlementDict.SETTLEMENT__PAYMENT_TYPE,null,{
			 'valueCode': '',
     		 'valueName': pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.all')
		},[
			pay.queryDisableRepayment.SETTLEMENT__PAYMENT_TYPE__CASH , // 现金
			pay.queryDisableRepayment.SETTLEMENT__PAYMENT_TYPE__CARD,// 银行卡
			pay.queryDisableRepayment.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER,// 电汇
			pay.queryDisableRepayment.SETTLEMENT__PAYMENT_TYPE__NOTE,// 支票
			pay.queryDisableRepayment.SETTLEMENT__PAYMENT_TYPE__ONLINE
		]),
		queryModel:'local',
		displayField:'valueName',
		valueField:'valueCode',
		value:''
    },
	{
		border : 1,
		xtype : 'container',
		columnWidth : .99,
		defaultType : 'button',
		layout : 'column',
		items : [ {
			text : pay.queryDisableRepayment.i18n('foss.stl.pay.common.reset'),
			columnWidth : .12,
			handler : pay.queryDisableRepayment.reset
		}, {
			xtype : 'container',
			border : false,
			columnWidth : .43,
			html : '&nbsp;'
		}, {
			text : pay.queryDisableRepayment.i18n('foss.stl.pay.common.query'),
			cls : 'yellow_button',
			columnWidth : .12,
			handler : function() {
				var form = this.up('form');
				var btn = this;
				pay.queryDisableRepayment.queryByRepayment(form, btn, pay.queryDisableRepayment.queryRepayment_TD);
			}
		} ]
	} ]

});

// 按还款单号查询 Form
Ext.define('Foss.disablerepayment.StlBillRepaymentQueryByRepayNosFrom', {
	extend : 'Ext.form.Panel',
	frame : false,
	columnWidth : 700,
	height : 180,
	tabConfig : {
		width : 120
	},
	layout : 'fit',
	getQueryType : function() {
		return pay.queryDisableRepayment.queryRepayment_RT = 'RT';
	},
	items : [ {
		xtype : 'form',
		defaults : {
			margin : '5 5 5 5',
			labelWidth : 70
		},
		layout : 'column',
		items : [ {
			xtype : 'container',
			border : false,
			columnWidth : 1,
			html : '&nbsp;'
		}, {
			xtype : 'textarea',
			autoScroll : true,
			regex : /^((HK)?[0-9]{7,10}[;,])*((HK)?[0-9]{7,10}[;,]?)$/i,
			regexText : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.inputHKErrorWarning'),
			emptyText : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.billNoEmptyText'),
			fieldLabel : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.repaymentBillNo'),
			name : 'repaymentNos',
			height : 80,
			allowBlank : false,
			columnWidth : .45
		}, {
			border : 1,
			xtype : 'container',
			columnWidth : .99,
			defaultType : 'button',
			layout : 'column',
			items : [ {
				text : pay.queryDisableRepayment.i18n('foss.stl.pay.common.reset'),
				columnWidth : .09,
				handler : pay.queryDisableRepayment.reset
			}, {
				xtype : 'container',
				border : false,
				columnWidth : .33,
				html : '&nbsp;'
			}, {
				text : pay.queryDisableRepayment.i18n('foss.stl.pay.common.query'),
				cls : 'yellow_button',
				columnWidth : .09,
				handler : function() {
					var form = this.up('form');
					var btn = this;
					pay.queryDisableRepayment.queryByRepayment(form, btn, pay.queryDisableRepayment.queryRepayment_RT);
				}
			} ]
		} ]
	} ]
});

// 查询tab
Ext.define('Foss.stlqueryDisableRepayment.StlDisableRepaymentQueryInfoTab', {
	extend : 'Ext.tab.Panel',
	frame : false,
	bodyCls : 'autoHeight',
	cls : 'innerTabPanel',
	activeTab : 0,
	items : [ {
		title : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.queryByRepaymentDate'),// 按日期查询
		tabConfig : {
			width : 120
		},
		width : '200',
		items : [ Ext.create('Foss.disablerepayment.StlBillRepaymentQueryByDateFrom') ]
	}, {
		title : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.queryByRepaymentBillNo'),// 按还款单号查询
		tabConfig : {
			width : 120
		},
		layout : 'fit',
		items : [ Ext.create('Foss.disablerepayment.StlBillRepaymentQueryByRepayNosFrom') ]
	} ]
});

// 还款单Grid
Ext.define('Foss.stlqueryDisableRepayment.StlDisableRepaymentQueryInfoGrid', {
	extend : 'Ext.grid.Panel',
	title : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.repaymentInfo'),
	emptyText : pay.queryDisableRepayment.i18n('foss.stl.pay.common.noResult'),
	frame : true,
	height : 510,
	// width : 1060,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	hidMeParams : null,
	setHidMeParams : function(params) {
		this.hidMeParams = params;
	},
	getHidMeParams : function() {
		return this.hidMeParams;
	},
	totalRepayAmount : null,// 还款单总金额
	getTotalRepayAmount : function() {
		var me = this;
		if (Ext.isEmpty(me.totalRepayAmount)) {
			me.totalRepayAmount = 0;
		}
		return me.totalRepayAmount;
	},
	totalApplyAmount : null,// 申请总金额
	getTotalApplyAmount : function() {
		var me = this;
		if (Ext.isEmpty(me.totalApplyAmount)) {
			me.totalApplyAmount = 0;
		}
		return me.totalAmount;
	},
	totalCount : null,// 总条数
	getTotalCount : function() {
		var me = this;
		if (Ext.isEmpty(me.totalCount)) {
			me.totalCount = 0;
		}
		return me.totalCount;
	},
	columns : [ {
		xtype : 'actioncolumn',
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.actionColumn'),
		width : 73,
		align : 'center',
		items : [{
			iconCls : 'deppon_icons_showdetail',
			tooltip : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.entry'),
			handler : function(grid, rowIndex, colIndex) {
				pay.queryDisableRepayment.detailRepaymentApply(grid, rowIndex, colIndex)
			}
		} ]
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.id'),
		dataIndex : 'id',
		hidden : true
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.repaymentNo'),
		dataIndex : 'repaymentNo',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.isAllDisable'),
		dataIndex : 'isAllDisable',
		hidden : false,
				renderer:function(value){
    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.FOSS_BOOLEAN);
    		return displayField;
    	}
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.customerCode'),
		dataIndex : 'customerCode',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.customerName'),
		dataIndex : 'customerName',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.applyParentOrgCode'),
		dataIndex : 'applyParentOrgCode',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.applyParentOrgName'),
		dataIndex : 'applyParentOrgName',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.applyAreaCode'),
		dataIndex : 'applyAreaCode',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.applyAreaName'),
		dataIndex : 'applyAreaName',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.applyOrgCode'),
		dataIndex : 'applyOrgCode',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.applyOrgName'),
		dataIndex : 'applyOrgName',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.applyUserCode'),
		dataIndex : 'applyUserCode',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.applyUserName'),
		dataIndex : 'applyUserName',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.applyTime'),
		dataIndex : 'applyTime',
		hidden : false,
				renderer:function(value){
    				return stl.dateFormat(value,'Y-m-d H:i:s');
    			}
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.amount'),
		dataIndex : 'amount',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.paymentType'),
		dataIndex : 'paymentType',
		hidden : false,
		renderer:function(value){
		    		var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.SETTLEMENT__PAYMENT_TYPE);
		    		return displayField;
    			}
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.disableReason'),
		dataIndex : 'disableReason',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.approveUserCode'),
		dataIndex : 'approveUserCode',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.approveUserName'),
		dataIndex : 'approveUserName',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.approveOrgCode'),
		dataIndex : 'approveOrgCode',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.approveOrgName'),
		dataIndex : 'approveOrgName',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.approveTime'),
		dataIndex : 'approveTime',
		hidden : false,
				renderer:function(value){
    				return stl.dateFormat(value,'Y-m-d H:i:s');
    			}
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.createTime'),
		dataIndex : 'createTime',
		hidden : false,
				renderer:function(value){
    				return stl.dateFormat(value,'Y-m-d H:i:s');
    			}
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.modifyTime'),
		dataIndex : 'modifyTime',
		hidden : false,
				renderer:function(value){
    				return stl.dateFormat(value,'Y-m-d H:i:s');
    			}
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.approveStatus'),
		dataIndex : 'approveStatus',
		hidden : false,
		renderer:function(value) {
			if(value=='NA') {
				return pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.approveStatusNA')
			}
			if(value=='AG') {
				return pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.approveStatusAG')
			}
			if(value=='AA') {
				return pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.approveStatusAA')
			}
			if(value=='AD') {
				return pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.approveStatusAD')
			}
		}
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.disableNote'),
		dataIndex : 'disableNote',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.repaymentAmount'),
		dataIndex : 'repaymentAmount',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.disableNum'),
		dataIndex : 'disableNum',
		hidden : false
	} ,{
				header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.trueAmount'),
				dataIndex : 'trueAmount',
				hidden : false
			}, {
				header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.oaPaymentNo'),
				dataIndex : 'oaPaymentNo',
				hidden : false
			}],
	viewConfig : {
		enableTextSelection : true
	},
	constructor : function(config) {
		var me = this;
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.repaymentApplyQuery.repaymentApplyStore');
		me.dockedItems = [ {
			xtype : 'toolbar',
			dock : 'top',
			layout : 'column',
			items : [ {
				xtype : 'container',
				border : false,
				html : '&nbsp;',
				columnWidth : .9
			}, {
				xtype : 'button',
				columnWidth : .1,
				text : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.export'),
				handler :pay.queryDisableRepayment.repaymentForBillExport
			} ]
		}, {
			xtype : 'toolbar',
			dock : 'bottom',
			layout : 'column',
			defaults : {
				margin : '0 0 5 3'
			},
			items : [ {
				height : 5,
				columnWidth : 1,
				layout : 'hbox'
			}, {
				xtype : 'displayfield',
				allowBlank : true,
				columnWidth : .25,
				name : 'totalCount',
				labelWidth : 80,
				width : 185,
				fieldLabel : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.totalCountRecord'),
				value : me.getTotalCount()
			}, {
				xtype : 'displayfield',
				allowBlank : true,
				columnWidth : .25,
				name : 'totalRepayAmount',
				labelWidth : 90,
				fieldLabel : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.totalRepayAmount'),
				value : me.getTotalRepayAmount()
			}, {
				xtype : 'displayfield',
				allowBlank : true,
				columnWidth : .25,
				labelWidth : 120,
				fieldLabel : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.totalApplyAmount'),
				name : 'totalApplyAmount',
				value : me.getTotalApplyAmount()
			}, {
				xtype : 'standardpaging',
				store : me.store,
				pageSize : 10,
				columnWidth : 1,
				plugins : Ext.create('Deppon.ux.PageSizePlugin', {
					// 设置分页记录最大值，防止输入过大的数值
					maximumSize : 200,
					sizeList : [ [ '20', 10 ], [ '50', 50 ], [ '100', 100 ], [ '200', 200 ] ]
				})
			} ]
		} ];
		me.callParent([ cfg ]);
	}
});

// 核销单model
Ext.define('Foss.DisableRepaymentbill.WriteoffDetailModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id'
	}, {
		name : 'repaymentApplicationId'
	}, {
		name : 'writeOffBillNo'
	}, {
		name : 'repaymentNo'
	}, {
		name : 'receviceNo'
	}, {
		name : 'receviceWaybillNo'
	}, {
		name : 'statementNo'
	}, {
		name : 'customerCode'
	}, {
		name : 'customerName'
	}, {
		name : 'amount',
		type : 'long'
	}, {
		name : 'writeoffType'
	}, {
		name : 'writeoffTime',
		type : 'date',
		convert : stl.longToDateConvert
	}, {
		name : 'orgCode'
	}, {
		name : 'orgName'
	}, {
		name : 'createTime',
		type : 'date',
		convert : stl.longToDateConvert
	}, {
		name : 'modifyTime',
		type : 'date',
		convert : stl.longToDateConvert
	}, {
		name : 'active'
	}, {
		name : 'isInit'
	} ]
});

// 明细Store
Ext.define('Foss.DisableRepaymentbill.WriteoffDetailStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.DisableRepaymentbill.WriteoffDetailModel'
});

// 查询作废明细
Ext.define('Foss.DisableRepaymentbill.WriteoffDetailGrid', {
	extend : 'Ext.grid.Panel',
	title : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.repaymentDetail'),
	frame : true,
	height : 500,
	emptyText : pay.queryDisableRepayment.i18n('foss.stl.pay.common.noResult'),
	store : Ext.create('Foss.DisableRepaymentbill.WriteoffDetailStore'),
	columns : [ {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.id'),
		dataIndex : 'id',
		hidden : true
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.repaymentApplicationId'),
		dataIndex : 'repaymentApplicationId',
		hidden : true
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.writeOffBillNo'),
		dataIndex : 'writeOffBillNo',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.repaymentNo'),
		dataIndex : 'repaymentNo',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.receviceNo'),
		dataIndex : 'receviceNo',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.receviceWaybillNo'),
		dataIndex : 'receviceWaybillNo',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.statementNo'),
		dataIndex : 'statementNo',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.customerCode'),
		dataIndex : 'customerCode',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.customerName'),
		dataIndex : 'customerName',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.amount'),
		dataIndex : 'amount',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.writeoffType'),
		dataIndex : 'writeoffType',
		hidden : false,
		renderer:function(value){
    		var displayField = FossDataDictionary.rendererSubmitToDisplay(value,settlementDict.BILL_WRITEOFF__WRITEOFF_TYPE);
    		return displayField;
    	}
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.writeoffTime'),
		dataIndex : 'writeoffTime',
		hidden : false,
				renderer:function(value){
    				return stl.dateFormat(value,'Y-m-d H:i:s');
    			}
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.orgCode'),
		dataIndex : 'orgCode',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.orgName'),
		dataIndex : 'orgName',
		hidden : false
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.createTime'),
		dataIndex : 'createTime',
		hidden : false,
				renderer:function(value){
    				return stl.dateFormat(value,'Y-m-d H:i:s');
    			}
	}, {
		header : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.modifyTime'),
		dataIndex : 'modifyTime',
		hidden : false,
				renderer:function(value){
    				return stl.dateFormat(value,'Y-m-d H:i:s');
    			}
	}],
	viewConfig : {
		enableTextSelection : true
	},
	dockedItems : [ {
		xtype : 'toolbar',
		dock : 'bottom',
		layout : 'column',
		defaults : {
			margin : '0 0 5 3'
		},
		items : [ {
			xtype : 'displayfield',
			allowBlank : true,
			columnWidth : .2,
			labelWidth : 90,
			fieldLabel : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.totalCountRecord')
		}, {
			xtype : 'container',
			border : false,
			html : '&nbsp;',
			columnWidth : .05
		}, {
			xtype : 'displayfield',
			allowBlank : true,
			columnWidth : .2,
			labelWidth : 90,
			fieldLabel : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.writeoffAmount')
		} ]
	} ]
});

Ext.define('Foss.DisableRepaymentbill.WriteoffDetailWindow', {
	extend : 'Ext.window.Window',
	title : pay.queryDisableRepayment.i18n('foss.stl.pay.queryDisableBillRepayment.applyDisableTitle'),
	id : 'Foss_DisableRepaymentbillQuery_WriteoffDetailWindow_ID',
	titleAlign : 'left',
	width : stl.SCREENWIDTH * 0.68,
	modal : true,
	constrainHeader : true,
	closeAction : 'destory',
	writeoffDetailGrid : null,
	getWriteoffDetailGrid : function() {
		var me = this;
		return this.writeoffDetailGrid;
	},

	constructor : function(config) {
		var me = this;
		cfg = Ext.apply({}, config);

		me.writeoffDetailGrid = Ext.create('Foss.DisableRepaymentbill.WriteoffDetailGrid');

		me.items = [ me.writeoffDetailGrid ];
		me.callParent([ cfg ]);
	}
});

// 初始化界面
Ext.onReady(function() {
	Ext.Ajax.timeout = 180000;
	Ext.QuickTips.init();

	if (Ext.getCmp('T_pay-queryDisableRepayment_content')) {
		return;
	}
	// 创建还款单4个tab
	var StlDisableRepaymentQueryInfoTab = Ext.create('Foss.stlqueryDisableRepayment.StlDisableRepaymentQueryInfoTab');

	// 查询grid创建
	var StlDisableRepaymentQueryInfoGrid = Ext.getCmp('Foss_stlqueryDisableRepayment_StlDisableRepaymentQueryInfoGrid_Id');
	if (Ext.isEmpty(StlDisableRepaymentQueryInfoGrid)) {
		StlDisableRepaymentQueryInfoGrid = Ext.create('Foss.stlqueryDisableRepayment.StlDisableRepaymentQueryInfoGrid', {
			id : 'Foss_stlqueryDisableRepayment_StlDisableRepaymentQueryInfoGrid_Id',
			hidden : true
		});
	}

	// 主页面创建
	Ext.create('Ext.panel.Panel', {
		id : 'T_pay-queryDisableRepayment_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		// 获得查询FORM
		getQueryTab : function() {
			return StlDisableRepaymentQueryInfoTab;
		},
		// 获得查询结果GRID
		getQueryGrid : function() {
			return StlDisableRepaymentQueryInfoGrid;
		},
		items : [ StlDisableRepaymentQueryInfoTab, StlDisableRepaymentQueryInfoGrid ],
		renderTo : 'T_pay-queryDisableRepayment-body',
		// 角色权限问题
		listeners : {
			'boxready' : function(th) {
				var roles = pay.queryDisableRepayment.roles;
				var queryByDateTab = th.getQueryTab().items.items[0].items.items[0];
				var orgName = queryByDateTab.getForm().findField('orgName');
				// 设置部门为默认当前登录部门
				if (!Ext.isEmpty(stl.currentDept.code)) {
					var displayText = stl.currentDept.name
					var valueText = stl.currentDept.code;
					var store = orgName.store;
					var key = orgName.displayField + '';
					var value = orgName.valueField + '';
					var m = Ext.create(store.model.modelName);
					m.set(key, displayText);
					m.set(value, valueText);
					store.loadRecords([ m ]);
					orgName.setValue(valueText);
				}
			}
		}
	});
});