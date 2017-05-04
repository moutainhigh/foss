/**----------------------------------------------时分秒时间选择Combobox----------------------------------**/
Ext.define('Deppon.form.field.MinutesSecondsField', {
    alias: 'widget.minutesSecondsField',
    extend:'Ext.form.field.ComboBox',
    increment:60*30,//分*秒
    displayField:'value',
    valueField:'key',
    regex:/^([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$/,
    minValue:'00:00:00',
    maxValue:'23:59:59',
    
    formatToLong:function(str){
    	var arr = str.split(':');
    	return arr[0]*60*60+arr[1]*60+arr[2]*1;
    },
    formatToStr:function(num){
    	var str =''+(num/3600<10?('0'+parseInt(num/3600)):parseInt(num/3600))+':'+
    	(num%3600/60<10?('0'+parseInt(num%3600/60)):parseInt(num%3600/60))+':'+
    	(parseInt(num%60)<10?('0'+parseInt(num%60)):parseInt(num%60));
    	return str;
    },
     listeners:{
      blur:function(me){
      	if(me.getDateValue()<me.formatToLong(me.minValue)||me.getDateValue()>me.formatToLong(me.maxValue)){
      	  me.markInvalid("时间超出了"+me.minValue+"-"+me.maxValue+"的范围");
      	}
      	
      }
    },
    getDateValue:function(){
    	var me =this;
      if(me.isValid()){
        return me.formatToLong(me.getRawValue());
      }
    },
    initComponent:function(){
    	var me = this;
    	var data  = [];
    	var minDateLong = me.formatToLong(me.minValue);
    	var maxDateLong = me.formatToLong(me.maxValue);
    	data.push({key:minDateLong,value:me.minValue});
    	var temp=parseInt((maxDateLong-minDateLong)/me.increment)+1;
    	for(var i =1;i<temp;i++){
    		var minDateLong = minDateLong+me.increment;
    		data.push({key:minDateLong,value:me.formatToStr(minDateLong)});
    	}
    	data.push({key:maxDateLong,value:me.maxValue});
    	me.store = Ext.create('Ext.data.Store',{
    		fields:["key","value"],
    		data:{
    			items:data
    		},
    		proxy: {
		        type: 'memory',
		        reader: {
		            type: 'json',
		            root: 'items'
		        }
		    }
    	});
    	me.callParent();
    }
});