//主页面
Ext.onReady(function() {
	Ext.Ajax.request({
		url : platform.realPath('queryTransferCenterUnits.action'),
		jsonData : {
			'transferCenterLayoutVo' : {
				'orgCode' : platform.transferCenterLayoutShow.orgCode
			}
		},
		success : function(response){
			var result = Ext.decode(response.responseText);
			var vo = result.transferCenterLayoutVo;
			//获取查询结果
			var unitsList = vo.unitsList,
				unitsDiv = '',
				scale = 5,
				tbar = [];
			if(unitsList != null && unitsList != undefined && unitsList.length != 0){
				for(var i = 0;i < unitsList.length;i++){
					var entity = unitsList[i],
							color = 'black',
							fontSize = '12px',
							type = entity.type;
					if(type === 'GOODSAREA'){
						color = '#373C64';
					}else if(type === 'TRANSFERAREA'){
						color = '#FAAF19';
					}else if(type === 'PLATFORM'){
						color = '#50B946';
						fontSize = '1px';
					}
					var tempDiv = '<div id="' + entity.orgCode + '^' + entity.code + '^' + entity.type
							+ '" style="position : absolute;border : solid 1px '+ color + 
							';width : ' + entity.width*5 + 'px;' + 
							'height : ' + entity.length*5 + 'px;' + 
							'font-size : ' + fontSize + ';' + 
							'left : ' + (entity.x - entity.width/2)*5 + 'px;' + 
							'top : ' + (entity.y - entity.length/2)*5 + 'px">' + 
							entity.name + '</div>';
							unitsDiv += tempDiv;					
				}
				tbar = [{
					xtype : 'container',
					width : 23,
					height : 10,
					style : 'border : solid 1px #373C64'
				},{
					xtype : 'label',
					text : '库区'
				},{
					xtype : 'container',
					width : 23,
					height : 10,
					style : 'border : solid 1px  #FAAF19'
				},{
					xtype : 'label',
					text : '待叉区'
				},{
					xtype : 'container',
					width : 23,
					height : 10,
					style : 'border : solid 1px  #50B946'
				},{
					xtype : 'label',
					text : '月台'
				},{
					xtype : 'slider',
					width : 400,
				    value : 5,
				    increment : 1,
				    minValue : 1,
				    maxValue : 15,
					addThumb : function(value){
						var me = this;
						if(me.thumbs.length != 0){
							return;
						}
				        var thumb = new Ext.slider.Thumb({
			                ownerCt : me,
			                ownerLayout : me.getComponentLayout(),
			                value : value,
			                slider : me,
			                index : me.thumbs.length,
			                constrain : me.constrainThumbs
			            });
					    me.thumbs.push(thumb);
				        //render the thumb now if needed
				        if (me.rendered) {
				            thumb.render();
				        }
					    return thumb;
					},
					listeners : {
						'change' : function(me, newValue, thumb, eOpts){
							if(unitsList != null
									&& unitsList != undefined
									&& unitsList.length !=0){
								for(var i = 0;i < unitsList.length;i++){
									var entity = unitsList[i],
											divId = entity.orgCode + '^' + entity.code + '^' + entity.type,
											div = document.getElementById(divId),
											//此处截取掉px再运算
											heightValue = div.style.height.substr(0,div.style.height.length - 2),
											widthValue = div.style.width.substr(0,div.style.width.length - 2),
											topValue = div.style.top.substr(0,div.style.top.length - 2),
											leftValue = div.style.left.substr(0,div.style.left.length - 2);
									div.style.height = (heightValue*newValue/scale) + "px";
									div.style.width = (widthValue*newValue/scale) + "px";
									div.style.top = (topValue*newValue/scale) + "px";
									div.style.left = (leftValue*newValue/scale) + "px";
								}
								scale = newValue;
							}
						}
					}
				}]
			}
			if(platform.transferCenterLayoutShow.beComeFromQueryPage == 1){
				Ext.create('Ext.panel.Panel',{
					id :'T_platform-displayShowPage_content',
					autoScroll : true,
					height : 800,
					html : unitsDiv,
					tbar : tbar,
					renderTo: 'T_platform-displayShowPage-body'
				});
			}else{
				Ext.create('Ext.panel.Panel',{
					id :'T_platform-chooseDisplayPage_content',
					autoScroll : true,
					height : 800,
					html : unitsDiv,
					tbar : tbar,
					renderTo: 'T_platform-chooseDisplayPage-body'
				});
			}
		},
		exception : function(response){
			var result = Ext.decode(response.responseText);
			top.Ext.MessageBox.alert('提示',result.message);
		}
	});
});