/**
 * 打印运单
 * @author 100847-foss-GaoPeng
 */
querying.integrativeQuery.printGoodsWayBill = function (_wayBillNumber, _autoCheck) {
	var _print = {
		printWayBill : function () {
			var me = this;
			if (!Ext.isEmpty(me.config.wayBillNumber)) {
				var _gotoPrint = function () {
					if (me.config.loadWayBill) {
						var targetObject = {
							goodsWayBillNumber : me.config.wayBillNumber,
							printerWithDate : FossUserContext.getCurrentUserEmp().empName
						};
						do_printpreview('goodswaybill', targetObject, ContextPath.BSE_QUERYING);
					} else {
						var _message = querying.integrativeQuery.i18n('foss.querying.waybillBeNull');  //运单号（信息）不存在或者为空！
						me.messageWayBill(_message);
					}
					return me;
				};
				if (me.config.autoCheck) {
					me.checkWayBill(_gotoPrint);
				} else {
					_gotoPrint();
				}
			} else {
				var _message = querying.integrativeQuery.i18n('foss.querying.selectOneWaybill');  //运单号为空，请先选择一个运单！
				this.messageWayBill(_message);
				return me;
			}
		},
		messageWayBill : function (_message) {
			Ext.MessageBox.show({
				title : querying.integrativeQuery.i18n('foss.querying.failureInformation'),  //信息（失败）提示
				msg : querying.integrativeQuery.i18n('foss.querying.Warning') + _message,  //警告：
				width : 300,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.WARNING
			});
		},
		config : {
			loadWayBill : false,
			wayBillNumber : null,
			autoCheck : false
		}
	};
	if (_print) {
		_print.config.autoCheck = _autoCheck;
		_print.config.wayBillNumber = _wayBillNumber;
		_print.config.loadWayBill = _autoCheck ? true : _autoCheck ? false : true;
		_print.checkWayBill = function (_backFn) {
			var _params = {
				'waybillVo' : {
					'condition' : {
						'waybillNo' : _print.config.wayBillNumber
					}
				}
			};
			if (querying) {
				_print.config.loadWayBill = false;
				querying.requestJsonAjax(querying.realPath('searchWaybillInfoByWaybillNo.action'), _params,
					function (json) {
					if (!Ext.isEmpty(json.waybillVo)) {
						_print.config.loadWayBill = true;
					}
					_backFn();
				},
					function (json) {
					_backFn();
				});
			}
			_params = null;
		}
	} else {
		return null;
	}
	return _print;
}
