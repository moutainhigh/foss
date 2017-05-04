load.complementLabel.enterPress = function(event){
	if (event.keyCode == 13) {
		//运单控件
		var waybillCmp = document.getElementById("Foss_load_complementLabel_mainForm_items_waybill_id-inputEl"),
			// 提货网点名称控件
			ladingStationNameCmp = document.getElementById('Foss_load_complementLabel_mainForm_items_ladingStationName_id-inputEl'),
			// 补码简称控件
			complementAbbrCmp = document.getElementById('Foss_load_complementLabel_mainForm_items_complementAbbr_id-inputEl'),
			newValue = waybillCmp.value;
		if (Ext.isEmpty(newValue) || (newValue.length >= 0 && newValue.length < 16) || newValue.length > 18) {
			waybillCmp.value = null;
			ladingStationNameCmp.value = null;
			complementAbbrCmp.value = null;
		} else if(newValue.length === 16 || newValue.length === 17){
			var waybill = newValue.length === 16 ? newValue.substr(0, 8) : newValue.substr(0, 9);
			var message = "运单[" + waybill + "]不是快递货!"
			
			waybillCmp.value = null;
			ladingStationNameCmp.value = null;
			complementAbbrCmp.value = null;
			
			document.getElementById("audio_id").play();
			
			Ext.MessageBox.show({
				title : '提示',
				icon : Ext.Msg.WARNING,
				minWidth : 1000,
				minHeight : 1000,
				msg : '<p style="font-size:50px">' + message + '</p>',
				fn : function() {
					waybillCmp.focus();
				}
			});
		} else if (newValue.length === 18) {
			var waybill = newValue.substr(0, 10),
				params = {
					'complementVo' : {
						'waybillNo' : waybill
					}
				};
			waybillCmp.value = waybill;
			
			Ext.Ajax.request({
				url : load.realPath('printComplementLabel.action'),
				jsonData : params,
				async: false,
				success : function(response) {
					var result = Ext.decode(response.responseText),
						complementVo = result.complementVo,
						// 提货网点
						ladingStationName = complementVo.pkpOrgName,
						// 补码简称
						complementAbbr = complementVo.complementAbbr,
						// 补码标签打印时间
						complementLabelPrintDate = complementVo.complementLabelPrintDate,
						// 补码标签打印操作人
						userCode = FossUserContext.getCurrentUserEmp().empCode;

					ladingStationNameCmp.value = ladingStationName;
					complementAbbrCmp.value = complementAbbr;
					
					Ext.data.JsonP.request({
						url : "http://localhost:8077/print",
						callbackKey : 'callback',
						async: false,
						params : {
							lblprtworker : "SupplementLabelPrintWorker",
							optusernum : userCode,
							printdate : Ext.Date.format(
									new Date(complementLabelPrintDate),
									'Y-m-d'),
							number : waybill,
							destination : complementAbbr
						},
						success : function(result, request) {
							var ret_code = result.data.code;
							if (ret_code === 1) {
							} else {
								document.getElementById("audio_id").play();
								alert(result.data.msg);
							}
						},
						failure : function(result, request) {
							document.getElementById("audio_id").play();
						}
					});
				},
				exception : function(response) {
					waybillCmp.value = null;
					complementAbbrCmp.value = null;
					ladingStationNameCmp.value = null;
					document.getElementById("audio_id").play();

					var result = Ext.decode(response.responseText);
					Ext.MessageBox.show({
								title : '提示',
								icon : Ext.Msg.WARNING,
								minWidth : 1000,
								minHeight : 1000,
								msg : '<p style="font-size:50px">'
										+ result.message + '</p>',
								fn : function() {
										waybillCmp.focus();
									}
							});
				}
			});
			waybillCmp.select();
		}
	}
};

load.complementLabel.html = 
'<form id = "Foss_load_complementLabel_mainForm_id">' + 
'运 单 号&nbsp：' +
'<input id = "Foss_load_complementLabel_mainForm_items_waybill_id-inputEl" type="text" name="waybillNo" onkeypress="load.complementLabel.enterPress(event)" style="width:700px" />' +
'<br />' +
'提货网点：' +
'<input id = "Foss_load_complementLabel_mainForm_items_ladingStationName_id-inputEl" type="text" name="ladingStationName" style="width:700px" />' +
'<br />' +
'补码简称：' +
'<input id = "Foss_load_complementLabel_mainForm_items_complementAbbr_id-inputEl" type="text" name="complementAbbr" style="width:700px" />' +
'</form>' +
'<audio id = "audio_id" src="' + ContextPath.TFR_EXECUTION + '/images/load/msg.mp3"/>';

Ext.onReady(function() {
			Ext.create('Ext.panel.Panel', {
						id : 'T_load-complementLabelPrintIndex_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContent-body',
						layout : 'auto',
						html : load.complementLabel.html,
						renderTo : 'T_load-complementLabelPrintIndex-body'
					});
			document.getElementById("Foss_load_complementLabel_mainForm_items_waybill_id-inputEl").focus();
		});
