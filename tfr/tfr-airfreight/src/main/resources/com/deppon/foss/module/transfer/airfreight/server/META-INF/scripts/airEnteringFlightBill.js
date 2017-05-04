//航班号选择器
Ext.define('Foss.commonSelector.FlightSelector', {
    extend: 'Foss.commonSelector.CommonCombSelector',
    alias: 'widget.commonflightselector', //别名
    displayField: 'flightNo',// 显示名称
    valueField: 'flightNo',// 显示值
    active: 'Y',//有效状态
    flightSort: null,//航班类别
    airLines: null,//航空公司
    origCode: null,//始发站代码
    targetCode: null,//目的站代码 
    queryParam: 'flightVo.flightDto.flightNo',// 查询参数(航班号)
    showContent: '{flightNo}',// 显示表格列
    //构造器
    constructor: function (config) {
        var me = this, cfg = Ext.apply({}, config);
        me.store = Ext.create('Foss.baseinfo.commonSelector.FlightStore'); //航班store
        me.active = config.active; //有效
        //加载前事件
        me.store.addListener('beforeload', function (store, operation, eOpts) {
            var searchParams = operation.params;
            //如果查询参数为空
            if (Ext.isEmpty(searchParams)) {
                searchParams = {};
                Ext.apply(operation, {
                    params: searchParams
                });
            }
            //航班类别
            if (!Ext.isEmpty(me.flightSort)) {
                searchParams['flightVo.flightDto.flightSort'] = me.flightSort;
            }
            //航空公司
            if (!Ext.isEmpty(me.airLines)) {
                searchParams['flightVo.flightDto.airlines'] = me.airLines;
            }
            //始发站代码
            if (!Ext.isEmpty(me.origCode)) {
                searchParams['flightVo.flightDto.origCode'] = me.origCode;
            }
            //目的站代码 
//			if (!Ext.isEmpty(me.targetCode)) {
//				searchParams['flightVo.flightDto.targetCode'] = me.targetCode;
//			} 
            //始发站代码列表
            if (!Ext.isEmpty(me.origCodes)) {
                var origCodes = me.origCodes.split(',');
                searchParams['flightVo.flightDto.origCodes'] = origCodes;
            }
            searchParams['flightVo.flightDto.active'] = me.active;
        })
        me.callParent([cfg]);
    }
});
//绑定运单明细
airfreight.airEnteringFlightBill.SubmitJointTicket = function (record, type) {
    //待提交运单明细列表
    var waybillNostr = '';
    var goodsNameStr = '';
    var goodsPackageStr = '';
    //遍历待选定的记录
    for (var i = 0; i < record.length; i++) {
        //获取record中的运单号
        var waybillNo = record[i].data.waybillNo;
        //提交
        if (type == 'submit') {
            //选定区
            var gridStore = airfreight.airEnteringFlightBill.showGrid.store.data.items;
            if (gridStore.length > 0) {
                //遍历选定区
                for (var j = 0; j < gridStore.length; j++) {
                    //如果已经存在加入总串中，进行提示
                    if (waybillNo == gridStore[j].data.waybillNo) {
                        waybillNostr = waybillNostr + '[' + waybillNo + ']';
                    }
                }
                if(gridStore[0].data.transportType!=record[0].data.transportType){
                	Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                			,airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.tranportDifferType'));
                        return false;
                }
            }
            if (waybillNostr.length > 0) {
                //提示信息,运单号+waybillNostr+已存在分单合票列表中
                Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                    , airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.waybillNo') + waybillNostr + airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.yczfdhplbz'));
                return false;
            }
            //增加一条记录
            airfreight.airEnteringFlightBill.showGrid.store.add(record[i].data);
            //关闭窗口
            airfreight.airEnteringFlightBill.jointTicketInformationWindow.close();
        }
    }
    //选定区长度
    var recordLength = airfreight.airEnteringFlightBill.showGrid.store.data.items;
    var volumeTotal = 0; //总体积
    var goodsQtyTotal = 0; //总件数
    var grossWeightTotal = 0; //总毛重
    var goodsNameMap = new Ext.util.HashMap();
    var goodsName = '';
    //遍历，计算总计信息
    for (var j = 0; j < recordLength.length; j++) {
        goodsName = recordLength[j].data.goodsName;
        //如果第一行
        if (j == 0) {
            goodsNameStr = goodsNameStr + goodsName; //拼接货物名称
            goodsNameMap.add(goodsName, goodsName);
            goodsPackageStr = goodsPackageStr + recordLength[j].data.goodsPackage; //拼接货物包装
        } else {
            if (!goodsNameMap.get(goodsName)) {
                goodsNameStr = goodsNameStr + ' ' + goodsName;  //拼接' '+货物名称
                goodsNameMap.add(goodsName, goodsName);
            }
            goodsPackageStr = goodsPackageStr + ',' + recordLength[j].data.goodsPackage; //拼接','+货物包装
        }
        //总体积，保留2位
        volumeTotal = airfreight.airEnteringFlightBill.fomatFloat(volumeTotal + recordLength[j].data.volume, 2);
        //总件数
        goodsQtyTotal = goodsQtyTotal + recordLength[j].data.goodsQty;
        //总毛重
        grossWeightTotal = grossWeightTotal + recordLength[j].data.grossWeight;
    }
    var setFilghtValues = airfreight.airEnteringFlightBill.flightinformationForm.getForm();
    var freightMethod = setFilghtValues.findField('airAssembleType').value;
    //当组装类型为单独开单或者是单独开单外发时只能选择一条运单信息
    if (freightMethod == 'DDKD' || freightMethod == 'DDWFD') {
        if (record.length > 0) {
            setFilghtValues.findField('receiverName').setValue(record[0].data.receiverName);
            setFilghtValues.findField('receiverContactPhone').setValue(record[0].data.receiverContactPhone);
        }
    }
    setFilghtValues.findField('goodsName').setValue(goodsNameStr);
    setFilghtValues.findField('packageStruction').setValue(goodsPackageStr);
    setFilghtValues.findField('goodsQty').setValue(goodsQtyTotal);
    setFilghtValues.findField('volume').setValue(volumeTotal);
    setFilghtValues.findField('grossWeight').setValue(grossWeightTotal);
    if ((volumeTotal * 1000) / 6 > grossWeightTotal) {
        var billingWeight = airfreight.airEnteringFlightBill.fomatFloat((volumeTotal * 1000) / 6, 1);
        setFilghtValues.findField('billingWeight').setValue(airfreight.airEnteringFlightBill.fomatFloat(billingWeight, 1));
    } else {
        var billingWeight = airfreight.airEnteringFlightBill.fomatFloat(grossWeightTotal, 1);
        setFilghtValues.findField('billingWeight').setValue(airfreight.airEnteringFlightBill.fomatFloat(billingWeight, 1));
    }
    airfreight.airEnteringFlightBill.calculate(airfreight.airEnteringFlightBill.record);
}
//处理新增删除流水号
airfreight.airEnteringFlightBill.addSerialDetialNo = function (src, id) {
    var waybillNo = airfreight.airEnteringFlightBill.serialnoDetialWind.waybillNo; //获取某个运单号
    var modifyWeightVolumnMap = airfreight.airEnteringFlightBill.modifyWeightVolumnMap; //修改重量体积map
    //如果src与id都为空
    if (!src && !id) {
        return false;
    }
    //当链接显示为蓝色时可以点击
    var operateFlag = src.substring(0, 3);
    var serNo = src.substring(3, src.length)
    var srcDoc = document.getElementById(operateFlag + id + serNo);
    var srcStyle = srcDoc.style.color;
    if (srcStyle != 'blue') {
        return false;
    }
    //不包含add操作
    if (!src.indexOf('add')) {
        //获取跟在add之后的流水号
        serialNo = src.substring(src.indexOf('add') + 3, src.length)
        var map = airfreight.airEnteringFlightBill.map;
        var mapStyle = airfreight.airEnteringFlightBill.sytleMap;
        var delMap = airfreight.airEnteringFlightBill.delMap;
        var totalCountMap = airfreight.airEnteringFlightBill.totalCountMap;
        if (totalCountMap.get(waybillNo)) {
            var count = totalCountMap.get(waybillNo);
            totalCountMap.add(waybillNo, count + 1);
            airfreight.airEnteringFlightBill.totalCountMap = totalCountMap;
        }
        var serialNoList = map.get(waybillNo + serialNo);
        if (typeof(serialNoList) !== 'undefined' && serialNoList.length > 0) {
            airfreight.airEnteringFlightBill.map.removeAtKey(waybillNo + serialNo);
            modifyWeightVolumnMap.removeAtKey(waybillNo + serialNo);
            if (typeof(delMap.get(waybillNo)) !== 'undefined') {
                var array = delMap.get(waybillNo);
                Ext.Array.remove(array, serialNo);
                delMap.removeAtKey(waybillNo);
                delMap.add(waybillNo, array);
                airfreight.airEnteringFlightBill.delMap = delMap;
            }
        }
        srcb = 'del' + id + serialNo;
        srcg = 'add' + id + serialNo;
        mapStyle.add(id + serialNo + 'add', serialNo);
        mapStyle.removeAtKey(id + serialNo + 'del');
        airfreight.airEnteringFlightBill.map = map;
        airfreight.airEnteringFlightBill.sytleMap = mapStyle;

    } else {
        var serialNo = src.substring(src.indexOf('del') + 3, src.length)
        var waybillNo = airfreight.airEnteringFlightBill.serialnoDetialWind.waybillNo;
        var total = airfreight.airEnteringFlightBill.serialNoNumbers;
        var map = airfreight.airEnteringFlightBill.map;
        var delMap = airfreight.airEnteringFlightBill.delMap;
        var mapStyle = airfreight.airEnteringFlightBill.sytleMap;
        //根据当前操作运单号流水号获取map中是否存在改记录
        var serialNoList = map.get(waybillNo + serialNo);
        var delSerialNoList = delMap.get(waybillNo);
        if (!serialNoList) {
            serialNoList = new Array();
        }
        if (!delSerialNoList) {
            delSerialNoList = new Array();
        }
        delSerialNoList.push(serialNo);
        delMap.add(waybillNo, delSerialNoList);
        if (typeof(delMap.get(waybillNo)) !== 'undefined') {
            if (delMap.get(waybillNo).length == total) {
                var array = delMap.get(waybillNo);
                Ext.Array.remove(array, serialNo);
                delMap.removeAtKey(waybillNo);
                delMap.add(waybillNo, array);
                airfreight.airEnteringFlightBill.delMap = delMap;
                Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
                    airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.leastOneSerial'));
                return false;
            }
        }
        var totalCountMap = airfreight.airEnteringFlightBill.totalCountMap;
        if (totalCountMap.get(waybillNo)) {
            var count = totalCountMap.get(waybillNo);
            totalCountMap.add(waybillNo, count - 1);
            airfreight.airEnteringFlightBill.totalCountMap = totalCountMap;
        }
        var booleanSerialNo = false
        for (var i = 0; i < serialNoList.length; i++) {
            if (serialNo == serialNoList[i]) {
                booleanSerialNo = true;
            }
        }
        if (!booleanSerialNo) {
            serialNoList.push(serialNo);
        }
        //删除时候将运单号合流水号作为KEY存放在map中 map的value是一条或多条流水号
        if (!map.get(waybillNo + serialNo)) {
            map.add(waybillNo + serialNo, serialNoList);
            //根据运单号保存流水号明细list方便动态计算毛重合体积
            modifyWeightVolumnMap.add(waybillNo + serialNo, serialNoList);
        }
        //将不需要保存的流水号存放在数组中
        srcb = 'add' + id + serialNo;
        srcg = 'del' + id + serialNo;
        mapStyle.add(id + serialNo + 'del', serialNo);
        mapStyle.removeAtKey(id + serialNo + 'add');
        airfreight.airEnteringFlightBill.map = map;
        airfreight.airEnteringFlightBill.delMap = delMap;
        airfreight.airEnteringFlightBill.sytleMap = mapStyle;
        airfreight.airEnteringFlightBill.modifyWeightVolumnMap = modifyWeightVolumnMap;
    }
    var docb = document.getElementById(srcb);
    var docg = document.getElementById(srcg);
    var docmentAdd = document.getElementsByName(srcb);
    var docmentDel = document.getElementsByName(srcg);
    for (var i = 0; i < docmentAdd.length; i++) {
        var add = docmentAdd[i];
        var operatorType = src.substring(0, 3);
        for (var j = 0; j < docmentDel.length; j++) {
            var del = docmentDel[j];
            del.style.color = "gray";
        }
        add.style.color = "blue";
    }
    //保存当前所操作的id对象的流水号样式
    airfreight.airEnteringFlightBill.modifyGoodsWeightVolume();
}

//调用方法修改毛重,体积
airfreight.airEnteringFlightBill.modifyGoodsWeightVolume = function () {
    var waybillNo = airfreight.airEnteringFlightBill.serialnoDetialWind.waybillNo;
    var goodsWeightTotal = airfreight.airEnteringFlightBill.originalWeightMap.get(waybillNo);
    var goodsVolumeTotal = airfreight.airEnteringFlightBill.originalVolumeMap.get(waybillNo);
    var total = airfreight.airEnteringFlightBill.serialNoNumbers;
    var serial = airfreight.airEnteringFlightBill.totalCountMap.get(waybillNo);
    //总重量
    var weight = (serial / total) * goodsWeightTotal;
    //总体积
    var volume = (serial / total) * goodsVolumeTotal;
    var recordForm = airfreight.airEnteringFlightBill.serialnoDetialWind.createForm().getForm();
    recordForm.findField('goodsWeightTotal').setValue(airfreight.airEnteringFlightBill.fomatFloat(weight, 1));
    recordForm.findField('goodsVolumeTotal').setValue(airfreight.airEnteringFlightBill.fomatFloat(volume, 2));
    if ((volume * 1000) / 6 > weight) {
        var billingWeight = airfreight.airEnteringFlightBill.fomatFloat((volume * 1000) / 6, 1);
        recordForm.findField('goodsBillingWeightTotal').setValue(airfreight.airEnteringFlightBill.fomatFloat(billingWeight, 1));
    } else {
        var billingWeight = airfreight.airEnteringFlightBill.fomatFloat(weight, 1);
        recordForm.findField('goodsBillingWeightTotal').setValue(airfreight.airEnteringFlightBill.fomatFloat(billingWeight, 1));
    }
    airfreight.airEnteringFlightBill.serialnoDetialWind.createForm().loadRecord(recordForm);
    return recordForm;
}
//数字运算后保留小数点后一位
airfreight.airEnteringFlightBill.fomatFloat = function (src, pos) {
    return Math.round(src * Math.pow(10, pos)) / Math.pow(10, pos);
}
/**
 *  //处理所有费用相关
 *  1、  航空运费：计费重量*运价，当航空运费小于航空公司费率基础资料的最低收费时，航空运费等于基础资料最低收费；当航空运费大于且等于航空公司费率基础资料的最低收费时，航空运费保持不变；小数点后保留2位；
 *  2、    地面运费：计费重量*地面运输费率，当地面运费小于基础资料里最低搬运费时，地面运费等于基础资料最低搬运费；当地面运费大于等于基础资料里最低搬运费时，地面运费保持不变；小数点后保留2位；
 *  3、    燃油附加税：计费重量*燃油附加费率，当燃油附加税小于基础资料最低燃油费时，燃油附加税等于基础资料最低燃油费；当燃油附加税大于且等于基础资料最低燃油费时，燃油附加税保持不变；
 *  4、    保险费：保险费计算方式为：保险费=运输保险*保险费率
 *  5、    附加费：手动输入；
 *  6、    运输保险：手动输入；
 *  7、    总金额：航空运费+地面运费+保险费+燃油附加税+制单费，总金额小于航空公司费率基础资料的最低总金额时，总金额等于基础资料最低总金额，大于并且等于航空公司费率基础资料的最低总金额时，总金额保持不变；小数点后保留2位；
 *  8、    费用说明来源基础资料，打开录入航空正单界面时，根据当前总调部门，得到相应的费用说明信息；
 *  9、    总金额和航空运费,地面运费、燃油附加税、保险费不可以编辑，附加费可以手动修改；
 *  10、    当配载类型为单独开单外发和合大票外发时，制单费变为可编辑状态，否则不可编辑且为空；
 */
airfreight.airEnteringFlightBill.calculate = function (record, flightNo) {
    var recordCalcuLate = record.data;
    var flightinformationFormGetValue = airfreight.airEnteringFlightBill.flightinformationForm.getForm();
    var airLineTwoletter = flightinformationFormGetValue.findField('airLineTwoletter').getValue();
    var curFlightNo = flightinformationFormGetValue.findField('flightNo').getValue();
    if (airLineTwoletter != '' && curFlightNo != '') {
        var airFee = 0;		//航空运费
        var inseranceFee = 0;//保险费
        var groundFee = 0;  //地面运费
        var fuelSurcharge = 0;//燃油附加税
        var feeTotal = 0.00;
        //运输保险
        var transportInsurance = Ext.Number.from(flightinformationFormGetValue.findField('transportInsurance').getValue(), 0);
        //设置制单费
        var billingFee = Ext.Number.from(flightinformationFormGetValue.findField('billingFee').getValue(), 0);
        //设置附加费
        var extraFee = Ext.Number.from(flightinformationFormGetValue.findField('extraFee').getValue(), 0);
        //计费重量
        var billingWeight = Ext.Number.from(flightinformationFormGetValue.findField('billingWeight').getValue(), 0);
        //运价
        airfreight.airEnteringFlightBill.priceRate = airfreight.airEnteringFlightBill.requestRate(record);
        if (!Ext.isEmpty(airfreight.airEnteringFlightBill.priceRate) && airfreight.airEnteringFlightBill.priceRate != 0) {
            airFee = airfreight.airEnteringFlightBill.fomatFloat(billingWeight * airfreight.airEnteringFlightBill.priceRate, 0);
            if (airfreight.airEnteringFlightBill.minPriceRate != null && airfreight.airEnteringFlightBill.minPriceRate != '') {
                if (billingWeight * airfreight.airEnteringFlightBill.priceRate < airfreight.airEnteringFlightBill.minPriceRate) {
                    airFee = airfreight.airEnteringFlightBill.fomatFloat(airfreight.airEnteringFlightBill.minPriceRate, 0);
                }
            }
            feeTotal = airFee;
        } else {
            Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
                airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.flightNoFeeInfo'));
        }
        var airlinesValueAddEntity = airfreight.airEnteringFlightBill.airlinesValueAddEntity;


        //判断是否外发，如果是外发则总金额等于航空运费+制单费用
        //2013-07-22 
        //wqh
        var airAssembleType = flightinformationFormGetValue.findField('airAssembleType').getValue();
        if (airAssembleType == 'DDWFD' || airAssembleType == 'HDPWF') {
            feeTotal = airFee + airfreight.airEnteringFlightBill.fomatFloat(billingFee, 2);
            flightinformationFormGetValue.findField('inseranceFee').setValue(0);
            flightinformationFormGetValue.findField('transportInsurance').setValue(0);
            flightinformationFormGetValue.findField('groundFee').setValue(0);
            flightinformationFormGetValue.findField('fuelSurcharge').setValue(0);
            flightinformationFormGetValue.findField('extraFee').setValue(0);
        } else {

            //地面运费、燃油附加税、保险费   的费率信息

            if (airlinesValueAddEntity != null && airlinesValueAddEntity.id != null) {
                var dmysfl = airlinesValueAddEntity.groundTrsFee;//地面运输费率
                var ryfjfl = airlinesValueAddEntity.oilAddFee;//燃油附加费率
                var bxfl = airlinesValueAddEntity.insuranceFee;//保险费率

                //计算地面运费2表示地面费率
                groundFee = billingWeight * dmysfl;
                //当地面运输费小于基础资料中提供的最低地面运输费时默认为基础资料中的最低地面运输费
                if (groundFee < airlinesValueAddEntity.minGroundTrsFee) {
                    groundFee = airlinesValueAddEntity.minGroundTrsFee;
                }
                //计算燃油附加税3表示燃油附加费率
                fuelSurcharge = billingWeight * ryfjfl;
                //当燃油附加费小于基础资料中的最低燃油费时默认为基础资料中的最低燃油附加费
                if (fuelSurcharge < airlinesValueAddEntity.minOilAddFee) {
                    fuelSurcharge = airlinesValueAddEntity.minOilAddFee;
                }

                //计算保险费
                if (transportInsurance * bxfl < airlinesValueAddEntity.minInsuranceFee) {
                    inseranceFee = airlinesValueAddEntity.minInsuranceFee;
                } else {
                    inseranceFee = transportInsurance * bxfl;
                }

                //总金额=航空运费+地面运费+保险费+燃油附加税+制单费+附加费
                /*feeTotal = Ext.Number.from(airFee,0) + Ext.Number.from(groundFe
                 * e,0) + Ext.Number.from(inseranceFee,0)
                 + Ext.Number.from(fuelSurcharge,0)+ Ext.Number.from(billingFee,0)+value;*/
                feeTotal = airFee + groundFee + inseranceFee + fuelSurcharge + extraFee
                    + airfreight.airEnteringFlightBill.fomatFloat(billingFee, 2);

            }


        }

        //总金额小于航空公司费率基础资料的最低总金额时，总金额等于基础资料最低总金额，
        //大于并且等于航空公司费率基础资料的最低总金额时，总金额保持不变；小数点后保留2位
        if (airlinesValueAddEntity != null && airlinesValueAddEntity.id != null) {
            if (feeTotal < airlinesValueAddEntity.minTotalFee) {
                feeTotal = airlinesValueAddEntity.minTotalFee;
            }

        }
        //给record赋值
        recordCalcuLate.airFee = airFee;
        recordCalcuLate.groundFee = groundFee;
        recordCalcuLate.fuelSurcharge = fuelSurcharge;
        recordCalcuLate.inseranceFee = inseranceFee;
        recordCalcuLate.extraFee = extraFee;
        recordCalcuLate.billingFee = billingFee;
        recordCalcuLate.feeTotal = feeTotal;

        flightinformationFormGetValue.findField('fee').setValue(Ext.Number.from(airfreight.airEnteringFlightBill.priceRate, 0));
        flightinformationFormGetValue.findField('airFee').setValue(airFee);
        flightinformationFormGetValue.findField('groundFee').setValue(groundFee.toFixed(2));
        flightinformationFormGetValue.findField('fuelSurcharge').setValue(fuelSurcharge.toFixed(2));
        flightinformationFormGetValue.findField('inseranceFee').setValue(inseranceFee.toFixed(2));
        flightinformationFormGetValue.findField('feeTotal').setValue(Ext.Number.from(feeTotal, 0).toFixed(2));
    } else {
        Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
            airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.pleaseSelectFlight'));
    }
    return record;
}

//外发托运打印书窗口
Ext.define('Foss.airfreight.printoutgoingcheck', {
    extend: 'Ext.window.Window',
    id: 'printoutgoingcheckId',
    title: airfreight.airEnteringFlightBill.i18n('foss.airfreight.agencyPrintBook'),
    modal: true,
    closeAction: 'hide',
    width: 650,
    height: 450,
    layout: 'auto',
    resultEntity: null,
    items: [
        {
            xtype: 'form',
            id: 'PrintOutgoingFormId',
            layout: 'column',
            defaults: {
                margin: '10 10 10 10',
                columnWidth: .5,
                labelWidth: 101
            },
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airWaybillNo'),
                    name: 'airWaybillNo'
                },
                {
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'bottom',
                            items: [
                                {
                                    text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.print'),
                                    handler: function () {
                                        if (airfreight.airEnteringFlightBill.isNotHomeQuery) {
                                            if (airfreight.airEnteringFlightBill.resultHomeEntity == null) {
                                                Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                                                    , airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.airWaybillDetailMessageNotPrint'));
                                                return false;
                                            }
                                        }
                                        var fieldElements = this.up('form').getForm()._fields;
                                        for (var i = 0; i < fieldElements.length; i++) {
                                            if (fieldElements.items[i].value == null || fieldElements.items[i].value == '') {
                                                Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                                                    , airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.printFieldCantBlank'));
                                                return false;
                                            }
                                        }
                                        if (!this.up('form').getForm().isValid()) {
                                            Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                                                , airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.validError'));
                                            return false;
                                        }
                                        var newForm = this.up('form').getForm();
                                        var newRecord = newForm.getRecord();
                                        newForm.updateRecord(newRecord);
                                        var str = Ext.encode(newRecord.data);
                                        do_printpreview('agencyConsignBook', {'record': str}, ContextPath.TFR_PARTIALLINE);
                                    }
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'textfield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.deptRegionName'),
                    name: 'deptRegionName',
                    allowBlank: true
                },
                {
                    xtype: 'textfield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.arrvRegionName'),
                    name: 'arrvRegionName',
                    allowBlank: true
                },
                {
                    xtype: 'textfield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.shipperName'),
                    name: 'shipperName',
                    allowBlank: true
                },
                {
                    xtype: 'textfield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.shipperContactPhone'),
                    name: 'shipperContactPhone',
                    allowBlank: true
                },
                {
                    xtype: 'textfield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.receiverName'),
                    name: 'receiverName',
                    allowBlank: true
                },
                {
                    xtype: 'textfield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.receiverContactPhone'),
                    name: 'receiverContactPhone',
                    allowBlank: true
                },
                {
                    xtype: 'textfield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.receiverAddress'),
                    name: 'receiverAddress',
                    allowBlank: true,
                    maxLength: 500,
                    maxLengthText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.dzcdycgzdxz')
                },
                {
                    xtype: 'textfield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.storageItem'),
                    name: 'storageItem',
                    allowBlank: true
                },
                {
                    xtype: 'textfield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.pickupType'),
                    name: 'pickupType',
                    allowBlank: true
                },
                {
                    xtype: 'textfield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.goodsName'),
                    name: 'goodsName',
                    allowBlank: true
                },
                {
                    xtype: 'numberfield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.fee'),
                    name: 'fee',
                    minValue: 0.001,
                    hideTrigger: true,
                    keyNavEnabled: true,
                    mouseWheelEnabled: true,
                    decimalPrecision: 2,
                    maxLength: 9,
                    maxLengthText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.cdcgzdxz')
                },
                {
                    xtype: 'numberfield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.feeTotal'),
                    name: 'feeTotal',
                    minValue: 0.001,
                    hideTrigger: true,
                    keyNavEnabled: true,
                    mouseWheelEnabled: true,
                    decimalPrecision: 2,
                    maxLength: 9,
                    maxLengthText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.cdcgzdxz')
                },
                {
                    xtype: 'numberfield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.goodsQty'),
                    name: 'goodsQty',
                    minValue: 1,
                    allowDecimals: false,
                    hideTrigger: true,
                    keyNavEnabled: true,
                    mouseWheelEnabled: true,
                    maxLength: 9,
                    maxLengthText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.cdcgzdxz')
                },
                {
                    xtype: 'numberfield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.grossWeight'),
                    name: 'grossWeight',
                    minValue: 0.001,
                    hideTrigger: true,
                    keyNavEnabled: true,
                    mouseWheelEnabled: true,
                    maxLength: 9,
                    maxLengthText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.cdcgzdxz')
                },
                {
                    xtype: 'textfield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.agencyName'),
                    name: 'agencyName'
                },
                {
                    xtype: 'datetimefield_date97',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.createTime'),
                    name: 'createTime',
//					time : true,
//					editable:false,
                    columnWidth: .5,
                    id: 'Foss_airfreight_createTime_ID3',
                    dateConfig: {
                        el: 'Foss_airfreight_createTime_ID3-inputEl'
                    }
                },
                {
                    xtype: 'textfield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.createUserName'),
                    name: 'createUserName'
                }
            ]
        }
    ],
    constructor: function (config) {
        var me = this,
        cfg = Ext.apply({}, config);
        me.callParent([cfg]);
        }
});

//选择打印方式窗口
Ext.define('Foss.airfreight.Windoption', {
    extend: 'Ext.window.Window',
    title: airfreight.airEnteringFlightBill.i18n('foss.airfreight.optionPrint'),
    modal: true,
    width: 200,
    height: 170,
    layout: 'auto',
    closeAction: 'hide',
    //打印方法
    airPrint: function (printType, aiyWayBillId) {
        Ext.MessageBox.buttonText.yes = airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.yes');
        Ext.MessageBox.buttonText.no = airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.no');
        if (printType == 'HKZD') {
            Ext.Msg.confirm(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'), '是否打印相关金额？', function (btn, text) {
                if (btn == 'yes') {
                    do_printpreview('enteringAryWaybill',
                        {
                            'aiyWayBillId': aiyWayBillId,
                            'isnotMoney': 'Y'
                        }, ContextPath.TFR_PARTIALLINE
                    );
                } else {
                    do_printpreview('enteringAryWaybill',
                        {
                            'aiyWayBillId': aiyWayBillId,
                            'isnotMoney': 'N'
                        }, ContextPath.TFR_PARTIALLINE
                    );
                }
            })
        } else if (printType == 'WFQD') {
            Ext.Msg.confirm(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                , airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.sfdyxgshbm'), function (btn, text) {
                    if (btn == 'yes') {
                        do_printpreview('enteringAgency',
                            {
                                'aiyWayBillId': aiyWayBillId,
                                'isNotreceiverName': 'Y'
                            }, ContextPath.TFR_PARTIALLINE
                        );
                    } else {
                        do_printpreview('enteringAgency',
                            {
                                'aiyWayBillId': aiyWayBillId,
                                'isNotreceiverName': 'N'
                            }, ContextPath.TFR_PARTIALLINE
                        );
                    }
                })
        } else {
            var printoutgoingcheckWindow = Ext.getCmp('printoutgoingcheckId');
            if (printoutgoingcheckWindow == null) {
                printoutgoingcheckWindow = Ext.create('Foss.airfreight.printoutgoingcheck');
            }
            printoutgoingcheckWindow.show();
            Ext.getCmp('PrintOutgoingFormId').getForm().loadRecord(airfreight.airEnteringFlightBill.record);
        }

    },
    items: [
        Ext.create('Ext.form.Panel', {
            items: [
                {
                    xtype: 'radiogroup',
                    columns: 1,
                    margin: '10 10 10 10',
                    items: [
                        {
                            boxLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.aviationWaybill'),
                            name: 'checkstatus',
                            checked: true,
                            inputValue: 'HKZD'
                        },
                        {

                            boxLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.airfreightAgenBill'),
                            name: 'checkstatus',
                            inputValue: 'WFQD'
                        },
                        {
                            boxLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.agenBook'),
                            name: 'checkstatus',
                            inputValue: 'WFTYS'
                        }
                    ]
                },
                {
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'bottom',
                            items: [
                                {
                                    xtype: 'container',
                                    margin: '0 0 0 70'
                                },
                                {
                                    text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.confirm'),
                                    handler: function () {
                                        var aiyWayBillId = airfreight.airEnteringFlightBill.aiyWayBillId;
                                        var radioValue = this.up('form').getForm().getValues()['checkstatus']
                                        if (radioValue == null || radioValue == '') {
                                            Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                                                , airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.qxzdybb'));
                                            return false;
                                        }
                                        if (radioValue == 'WFTYS') {
                                            airfreight.airEnteringFlightBill.windoption.airPrint('WFTYS', aiyWayBillId);
                                        } else if (radioValue == 'WFQD') {
                                            airfreight.airEnteringFlightBill.windoption.airPrint('WFQD', aiyWayBillId);
                                        } else {//打印行航空正单
                                            airfreight.airEnteringFlightBill.windoption.airPrint('HKZD', aiyWayBillId);
                                        }
                                    }
                                },
                                {
                                    xtype: 'container',
                                    margin: '0 0 0 10'
                                },
                                {
                                    text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.cancel'),
                                    handler: function () {
                                        this.up('window').hide();
                                    }
                                }
                            ]
                        }
                    ]
                }
            ]
        })
    ]
});
//运价查询model
Ext.define('Foss.airfreight.airRateQueryModel', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'targetCode', type: 'string'}
    ]
});
//正单基本信息model
Ext.define('Foss.airfreight.Model', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'id', type: 'string'},
        {name: 'airLineTwoletter', type: 'string'},  //航空公司二字码
        {name: 'airWaybillNo', type: 'string'},	//正单号
        {name: 'deptRegionCode', type: 'string'}, //出发站编码
        {name: 'deptRegionName', type: 'string'}, //出发站
        {name: 'arrvRegionCode', type: 'string'}, //目的站编码
        {name: 'arrvRegionName', type: 'string'}, //目的站
        {name: 'destOrgCode', type: 'string'},	//到达网点编码
        {name: 'dedtOrgName', type: 'string'},	//到达网点
        {name: 'receiverCode', type: 'string'},	//收货人编码
        {name: 'receiverName', type: 'string'},	//收货人名称
        {name: 'receiverContactPhone', type: 'string'},//收货联系电话
        {name: 'accountItem', type: 'string'},	//结算事项
        {name: 'billingAgency', type: 'string'},	//填开代理
        {name: 'receiverAddress', type: 'string'},//收货地址
        {name: 'storageItem', type: 'string'},	//储运事项
        {name: 'flightNo', type: 'string'},		//航班号
        {name: 'flightDate', type: 'date', defaultValue: new Date()},//航班日期
        {name: 'takeOffTime', type: 'date',		//起飞时间
            convert: function (value) {
                if (!value) return '';  //显示为空
                var date = new Date(value);
                var formatStr = 'Y-m-d H:i:s';
                return Ext.Date.format(date, formatStr);
            }},
        {name: 'arriveTime', type: 'date',
            convert: function (value) {
                if (!value) return '';
                var date = new Date(value);
                var formatStr = 'Y-m-d H:i:s';
                return Ext.Date.format(date, formatStr);
            }
        },
        {name: 'rateClass', type: 'string'},		//运价种类
        {name: 'paymentType', type: 'string'},	//付款方式
        {name: 'grossWeight', type: 'number', defaultValue: null, convert: null}, //毛重
        {name: 'billingWeight', type: 'number', defaultValue: null, convert: null},//计费重量
        {name: 'fee', type: 'number'},			//运价
        {name: 'agenctCode', type: 'string'},		//承运人外发代理编号
        {name: 'agencyName', type: 'string'},		//承运人外发代理名称
        {name: 'declareValue', type: 'string', defaultValue: 'NVD'},// 声明价值
        {name: 'itemCode', type: 'string', defaultValue: '9000'},	// 商品代号
        {name: 'goodsQty', type: 'number', defaultValue: null, convert: null},//货物件数
        {name: 'volume', type: 'number', defaultValue: null, convert: null}, //货物体积
        {name: 'airFee', type: 'number'},			//  航空运费
        {name: 'extraFee', type: 'number', defaultValue: 0, convert: null},		// 附加费
        {name: 'goodsName', type: 'string'},		// 货物名称
        {name: 'packageStruction', type: 'string'},// 包装说明
        {name: 'groundFee', type: 'number'},		//地面运费
        {name: 'fuelSurcharge', type: 'number'},	// 燃油附加税
        {name: 'transportInsurance', type: 'number', defaultValue: 0, convert: null}, //运输保险
        {name: 'inseranceFee', type: 'number', defaultValue: 0}, //保险费
        {name: 'feeTotal', type: 'number'},	//总金额
        {name: 'feePlain', type: 'string'},	//费用说明
        {name: 'billingFee', type: 'number', defaultValue: null, convert: null}, //制单费
        {name: 'shipperCode', type: 'string'},	//托运人编码
        {name: 'shipperName', type: 'string', defaultValue: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.consignorName')}, //托运人姓名，默认值：德邦物流
        {name: 'shipperAddress', type: 'string'},	//托运人地址
        {name: 'shipperContactPhone', type: 'string'}, //托运人电话
        {name: 'pickupType', type: 'string', defaultValue: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.jcpickupType')}, //提货方式，默认:机场自提
        {name: 'createOrgCode', type: 'string'},	//制单部门编号
        {name: 'createUserCode', type: 'string'},	//制单人编号
        {name: 'createUserName', type: 'string'},	//制单人姓名
        {name: 'createTime', type: 'date', defaultValue: Ext.Date.format(new Date(), 'Y-m-d H:i:s'),	//提单时间
            convert: function (value) {
                if (!value) return '';
                var date = new Date(value);
                if (date == 'Invalid Date') {
                    return value;
                }
                return Ext.Date.format(new Date(), 'Y-m-d H:i:s');
            }
        },
        {name: 'modifyUserCode', type: 'string'},	//修改人编号
        {name: 'modifyUserName', type: 'string'}, //修改人姓名
        {name: 'modifyTime', type: 'date',		//修改时间
            convert: function (value) {
                if (!value) return '';
                var date = new Date(value);
                var formatStr = 'Y-m-d H:i:s';
                return Ext.Date.format(date, formatStr);
            }
        },
        {name: 'currencyCode', type: 'string'},	//货币
        {name: 'airAssembleType', type: 'string'},//配载类型
        {name: 'handoverState', type: 'string'},	//交接状态
        {name: 'isNotPayment', type: 'string'},	//是否付款
        {name: 'jointTicketNo', type: 'string'},	//合票号
        {name: 'createOrgName', type: 'string'},	//创建部门
        {name: 'airportCode', type: 'string'}		//机场编码
    ]
});

//空运代理网点
Ext.define('Foss.commonSelector.AirAgencyDeptSelector', {
    extend: 'Foss.commonSelector.CommonCombSelector',
    alias: 'widget.commonairagencydeptselector',
    displayField: 'agentDeptName',// 显示名称
    valueField: 'agentDeptCode',// 值
    active: 'Y',//有效状态
    destCityCode: null,//目的站城市代码
    agentDeptCode : null,
    queryParam: 'vo.entity.agentDeptName',// 查询参数
    showContent: '{agentDeptName}&nbsp;&nbsp;&nbsp;{agentDeptCode}',// 显示表格列
    constructor: function (config) {
        var me = this, cfg = Ext.apply({}, config);
        me.store = Ext
            .create('Foss.baseinfo.commonSelector.AirAgentAndDeptStore');
        //加载前事件
        me.agentDeptCode = config.agentDeptCode;
        me.store.addListener('beforeload', function (store, operation, eOpts) {
            var searchParams = operation.params;
            //如果查询参数为空
            if (Ext.isEmpty(searchParams)) {
                searchParams = {};
                Ext.apply(operation, {
                    params: searchParams
                });
            }
            //目的站城市代码
//            if (!Ext.isEmpty(me.destCityCode)) {
                searchParams['vo.entity.cityCode'] = me.destCityCode;
//            }
            searchParams['vo.entity.agentDeptCode'] = me.agentDeptCode;
        })
        me.callParent([cfg]);
    }
});
//TODO待确认是否有效
Ext.define('Foss.commonSelector.DestFreightRouteLineSelector', {
    extend: 'Foss.commonSelector.CommonCombSelector',
    alias: 'widget.commondestfreightroutelineselector',
//	fieldLabel : '到达站点',
    active: 'Y',
    displayField: 'name',// 显示名称
    valueField: 'name',// 值
    queryParam: 'freightRouteLine.destName',// 查询参数
    showContent: '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
    constructor: function (config) {
        var me = this, cfg = Ext.apply({}, config);
        me.store = Ext
            .create('Foss.baseinfo.commonSelector.OgirFreightRouteLineStore');
        me.active = config.active;
        me.store.addListener('beforeload', function (store, operation, eOpts) {
            var searchParams = operation.params;
            if (Ext.isEmpty(searchParams)) {
                searchParams = {};
                Ext.apply(operation, {
                    params: searchParams
                });
            }
            searchParams['freightRouteLine.active'] = me.active;
        });
        me.callParent([cfg]);
    }
});
//航空正单基本信息表单
Ext.define('Foss.airfreight.flightinformationForm', {
    extend: 'Ext.form.Panel',
    layout: 'column',
    frame: false,
    //查询航空公司费率
    queryAirlineRate: function (airLineTwoletter, origCode) {
        Ext.Ajax.request({
            async: false,
            url: airfreight.realPath('queryRate.action'),
            params: {
                'pointsSingleJointTicketVO.airLineTwoletter': airLineTwoletter,
                'pointsSingleJointTicketVO.airportCode': origCode
            },
            success: function (response) {
                var result = Ext.decode(response.responseText);
                airfreight.airEnteringFlightBill.airlinesValueAddEntity = null;
                airfreight.airEnteringFlightBill.airlinesValueAddEntity = result.pointsSingleJointTicketVO.airlinesValueAddEntity;
                var accountItem = airfreight.airEnteringFlightBill.airlinesValueAddEntity.priceNo;
                airfreight.airEnteringFlightBill.flightinformationForm.getForm().findField('accountItem').setValue(accountItem);
                airfreight.airEnteringFlightBill.flightinformationForm.getForm().findField('feePlain').setValue(
                        airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.fuelSurcharge')
                        + ':' + Ext.Number.from(airfreight.airEnteringFlightBill.airlinesValueAddEntity.oilAddFee, 0) + '/Kg');
            },
            exception: function (response) {
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.errorMessage'), result.message);
                return false;
            }
        });
        return airfreight.airEnteringFlightBill.airlinesValueAddEntity;
    },
    queryAirlinesAgentCode: function (airLineTwoletter) {
        /**
         * @desc 获得配载类型，将配载类型传入后台
         * @author wqh
         * date 2013-08-08
         * */
        var flightinformationForm = airfreight.airEnteringFlightBill.flightinformationForm.getForm();
        var airAssembleType = flightinformationForm.findField('airAssembleType').getValue();
        if (airAssembleType == null || airAssembleType == "") {
            Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.errorMessage'), '请选择配载类型');
            return;
        }
        Ext.Ajax.request({
            async: false,
            url: airfreight.realPath('queryAirlinesAgentCode.action'),
            params: {
                'pointsSingleJointTicketVO.airLineTwoletter': airLineTwoletter,
                'pointsSingleJointTicketVO.airAssembleType': airAssembleType
            },
            success: function (response) {
                var result = Ext.decode(response.responseText);
                var airlinesAgentEntity = result.pointsSingleJointTicketVO.airlinesAgent;
                if (airlinesAgentEntity != null) {
                    airfreight.airEnteringFlightBill.flightinformationForm.getForm().findField('billingAgency').setValue(airlinesAgentEntity.agentCode);
                } else {
                    airfreight.airEnteringFlightBill.flightinformationForm.getForm().findField('billingAgency').setValue('');
                }
            },
            exception: function (response) {
                var result = Ext.decode(response.responseText);
                Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.errorMessage'), result.message);
                return false;
            }
        });
        return airfreight.airEnteringFlightBill.airlinesValueAddEntity;
    },
    items: [
        Ext.create('Ext.toolbar.Toolbar', {
            xtype: 'toolbar',
            dock: 'right',
            layout: 'column',
            defaultType: 'button',
            width: 1024,
            items: [
                {
                    xtype: 'container',
                    html: '&nbsp;',
                    columnWidth: .80
                },
                {
                    text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.add'),
                    columnWidth: .06,
                    handler: function () {
                        if(airfreight.airEnteringFlightBill.flightinformationForm){

                            var destOrg =airfreight.airEnteringFlightBill.flightinformationForm.getForm().findField('dedtOrgName');
                            if(destOrg){
                                destOrg.destCityCode = null;
                                destOrg.setValue('');
                                destOrg.store.load();
                            }
                            airfreight.airEnteringFlightBill.flightinformationForm.getForm().reset();
                        }
                        if (airfreight.airEnteringFlightBill.jointTicketInformation) {
                            airfreight.airEnteringFlightBill.findForm.getForm().reset();
                            airfreight.airEnteringFlightBill.queryForm.getForm().reset();
                            airfreight.airEnteringFlightBill.findForm.getForm().findField('beginInTime').setValue(Ext.Date.format(new Date(), 'Y-m-d') + ' ' + '00:00:00');
                            airfreight.airEnteringFlightBill.findForm.getForm().findField('endInTime').setValue(Ext.Date.format(new Date(), 'Y-m-d') + ' ' + '23:59:59');
                            airfreight.airEnteringFlightBill.jointTicketInformation.store.loadData([]);
                            airfreight.airEnteringFlightBill.submenuGtasks.store.loadData([]);
                        }
                        if (airfreight.airEnteringFlightBill.aiyWayBillId) {
                            airfreight.airEnteringFlightBill.aiyWayBillId = null;
                        }
                        //var tabPanel1 = Ext.getCmp('mainAreaPanel');
                        //var activeTab = tabPanel1.getActiveTab();
                        //tabPanel1.remove(activeTab);
                        removeTab('T_airfreight-airEnteringFlightBill');
                        addTab('T_airfreight-airEnteringFlightBill', airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.enteringWaybillDetail'), airfreight.realPath('airEnteringFlightBill.action'));
                    }
                },
                {
                    xtype: 'container',
                    html: '&nbsp;',
                    columnWidth: .04
                },
                {
                    text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.print'),
                    disabled: !airfreight.airEnteringFlightBill.isPermission('airfreight/printAirEnteringFlightBillButton'),
                    hidden: !airfreight.airEnteringFlightBill.isPermission('airfreight/printAirEnteringFlightBillButton'),
                    columnWidth: .06,
                    handler: function () {
                        var aiyWayBillId = airfreight.airEnteringFlightBill.aiyWayBillId;
                        if (aiyWayBillId == null || aiyWayBillId == '') {
                            Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
                                airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.notSaveCantPrint'));
                            return false;
                        }
                        airfreight.airEnteringFlightBill.windoption = Ext.create('Foss.airfreight.Windoption').show();
                    }
                }
            ]
        }),
        airfreight.airEnteringFlightBill.BasicInfo = Ext.create('Ext.form.FieldSet', {
            title: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.basicInfo'),
            height: 105,
            width: 1025,
            layout: 'column',
            defaults: {
                allowBlank: false,
                margin: '5 5 5 5',
                xtype: 'textfield'
            },
            items: [
                {
                    xtype: 'commonairlinesselector',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.airLineTwoletter'),
                    displayField: 'code',// 显示名称
                    valueField: 'code',// 值 
                    name: 'airLineTwoletter',
                    columnWidth: .25,
                    listeners: {
                        select: function (combo, record, index) {
                            var setComboxValues = airfreight.airEnteringFlightBill.flightinformationForm.getForm();
                            var airLineTwoletterCode = record[0].data.code;
                            airfreight.airEnteringFlightBill.record.data.agencyName = record[0].data.name;
                            var deptRegionCode = setComboxValues.findField('deptRegionName').getValue();
                            var arrvRegionCode = setComboxValues.findField('arrvRegionName').getValue();
                            var airAssembleType = setComboxValues.findField('airAssembleType').getValue();
                            if (airAssembleType != null) {
                                if (airAssembleType == 'DDKD' || airAssembleType == 'HDP') {
                                    setComboxValues.findField('agenctCode').setValue(airLineTwoletterCode);
                                    airfreight.airEnteringFlightBill.record.data.agencyName = record[0].data.name;
                                }
                            }

                            var findField = function (name) {
                                return form.findField(name);
                            };
                            setComboxValues.findField('flightNo').airLines = airLineTwoletterCode;
                            setComboxValues.findField('flightNo').setValue('');
                            setComboxValues.findField('flightNo').store.load();
                            //过滤航空公司代理人信息
                            setComboxValues.findField('agencyName_wf').assemblyDeptId = airfreight.airEnteringFlightBill.record.data.createOrgCode;
                            setComboxValues.findField('agencyName_wf').airlinesCode = airLineTwoletterCode;
                            setComboxValues.findField('agencyName_wf').setValue('');
                            setComboxValues.findField('agencyName_wf').store.load();
                            setComboxValues.findField('storageItem').setValue('仅限' + record[0].data.name + '承运');
                            airfreight.airEnteringFlightBill.flightinformationForm.queryAirlinesAgentCode(airLineTwoletterCode);
                            setComboxValues.findField('billingAgency').setReadOnly(true);
                        },
                        change: function (ths, newValue, oldValue, eOpts) {
                            var setComboxValues = airfreight.airEnteringFlightBill.flightinformationForm.getForm();
                            if (Ext.isEmpty(ths.getRawValue())) {
                                var findField = function (name) {
                                    return setComboxValues.findField(name);
                                };
                                ths.setValue(null);
                                findField('flightNo').airLines = null;
                                findField('flightNo').store.load();
                            }
                        }
                    }
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.airWaybillNo'),
                    xtype: 'textfield',
                    name: 'airWaybillNo',
                    columnWidth: .25,
                    maxLength: 25,
                    maxLengthText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.zdhcdycgzdxz'),
                    regex: /^\w+$/,
                    regexText:"正单号只能由字母和数字组成！"
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.deptRegionName'),
                    xtype: 'textfield',
                    name: 'deptRegionName',
                    readOnly: true,
                    columnWidth: .25
                },
                {
                    xtype: 'commonairporwithcitynametselector',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.public.arrvRegionName'),
                    name: 'arrvRegionName',
                    displayField: 'cityName',
                    valueField: 'cityName',
                    columnWidth: .25,
                    listeners: {
                        select: function (me, records, eOpts) {
                            if (Ext.isEmpty(me.getValue())) {
                                return;
                            } else {
                                var tempForm = me.up('form').getForm();
                            }
                            var record = records[0].data;
                            var destAirPortCode = record.airportCode;
                            var flightinformationForm = airfreight.airEnteringFlightBill.flightinformationForm.getForm();
                            //2013-05-31去除根据目的站过滤航班（有中转情况）
                            //flightinformationForm.findField('flightNo').targetCode = destAirPortCode;
                            //flightinformationForm.findField('flightNo').setValue('');
                            //flightinformationForm.findField('flightNo').store.load();
                            var destCityCode = record.cityCode;
                            flightinformationForm.findField('dedtOrgName').destCityCode = destCityCode;
                            flightinformationForm.findField('dedtOrgName').setValue('');
                            flightinformationForm.findField('dedtOrgName').store.load();
                            }
                        }
                },
                {
                    xtype: 'combobox',
                    editable: false,
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.airAssembleType'),
                    name: 'airAssembleType',
                    hiddenName: 'airAssembleType',
                    store: FossDataDictionary.getDataDictionaryStore('AIR_ASSEMBLE_TYPE', 'Foss_login_language_store_Id'),
                    queryMode: 'local',
                    displayField: 'valueName',
                    valueField: 'valueCode',
                    //设置默认值为'合大票'
                    value: 'HDP',
                    columnWidth: .25,
                    listeners: {
                        select: function (combo, record, index) {
                            var code = record[0].data.valueCode;
                            var flightinformationForm = airfreight.airEnteringFlightBill.flightinformationForm.getForm();
                            if (code == 'DDWFD' || code == 'HDPWF') {
                                //获取合票号
                                flightinformationForm.findField('billingFee').setReadOnly(false);
                                flightinformationForm.findField('billingFee').setValue(0);
                                flightinformationForm.findField('airWaybillNo').setValue(flightinformationForm.findField('jointTicketNo').getValue());
                                flightinformationForm.findField('agenctCode').setValue('');
                                document.getElementById(flightinformationForm.findField('agencyName_wf').getId()).style.display = 'inline';
                                document.getElementById(flightinformationForm.findField('agenctCode').getId()).style.display = 'none';
                                //重新计算费用
                                //wqh
                                var airFee = flightinformationForm.findField('airFee').getValue();
                                flightinformationForm.findField('groundFee').setValue(0);
                                flightinformationForm.findField('groundFee').setReadOnly(true);
                                flightinformationForm.findField('fuelSurcharge').setValue(0);
                                flightinformationForm.findField('fuelSurcharge').setReadOnly(true);
                                flightinformationForm.findField('transportInsurance').setValue(0);
                                flightinformationForm.findField('transportInsurance').setReadOnly(true);
                                flightinformationForm.findField('inseranceFee').setValue(0);
                                flightinformationForm.findField('inseranceFee').setReadOnly(true);
                                flightinformationForm.findField('extraFee').setValue(0);
                                flightinformationForm.findField('extraFee').setReadOnly(true);
                                flightinformationForm.findField('feeTotal').setValue(Ext.Number.from(airFee, 0));

                            }
                            //重新计算航空费用总金额
                            if (code == 'HDP' || code == 'DDKD') {
                                var airFee = Ext.Number.from(flightinformationForm.findField('airFee').getValue(), 0);
                                flightinformationForm.findField('billingFee').setValue(0);
                                flightinformationForm.findField('groundFee').setValue(0);
                                flightinformationForm.findField('fuelSurcharge').setValue(0);
                                flightinformationForm.findField('inseranceFee').setValue(0);
                                //ISSUE-3324  航空正单明细录入界面，保险费支持编辑，可为0
                                flightinformationForm.findField('inseranceFee').setReadOnly(false);
                                flightinformationForm.findField('extraFee').setValue(0);
                                flightinformationForm.findField('feeTotal').setValue(airFee);
                                flightinformationForm.findField('transportInsurance').setReadOnly(false);
                                flightinformationForm.findField('extraFee').setReadOnly(false);
                            }
                            if (code == 'HDP' || code == 'HDPWF') {
                                var dedtOrgName = flightinformationForm.findField('dedtOrgName').getValue();
                                flightinformationForm.findField('receiverName').setValue(dedtOrgName);
                                var receiverContactPhone = '';
                                if (flightinformationForm.findField('dedtOrgName').store.findRecord('agentDeptName', dedtOrgName, 0, false, true, true)) {
                                    receiverContactPhone = flightinformationForm.findField('dedtOrgName').store.findRecord('agentDeptName', dedtOrgName, 0, false, true, true).get('contactPhone');
                                }
                                flightinformationForm.findField('receiverContactPhone').setValue(receiverContactPhone);

                            }
                            //设置承运人
                            if (code == 'HDP' || code == 'DDKD') {
                                var airLineTwoletter = flightinformationForm.findField('airLineTwoletter').getValue();
                                flightinformationForm.findField('billingFee').setValue(0);
                                flightinformationForm.findField('billingFee').setReadOnly(true);
                                flightinformationForm.findField('agenctCode').setValue(airLineTwoletter);
//                                flightinformationForm.findField('agenctCode').setReadOnly(true);
                                document.getElementById(flightinformationForm.findField('agencyName_wf').getId()).style.display = 'none';
                                document.getElementById(flightinformationForm.findField('agenctCode').getId()).style.display = 'inline';
                            }
                            if (code == 'DDWFD' || code == 'DDKD') {
                                var showRecord = airfreight.airEnteringFlightBill.showGrid.store.data.items;
                                if (showRecord.length > 1) {
                                    Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                                        , airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.ddkdhddkwfdznbcytyd'));
                                    return false;
                                }
                                if (showRecord.length != 0) {
                                    flightinformationForm.findField('receiverName').setValue(showRecord[0].data.receiverName);
                                    flightinformationForm.findField('receiverContactPhone').setValue(showRecord[0].data.receiverContactPhone);
                                }

                            }
                            if (code == 'DDKD' || code == 'HDP') {
                                flightinformationForm.findField('airWaybillNo').setValue('');
                            }
                            var waybillDatas = airfreight.airEnteringFlightBill.showGrid.store.data.items;
                            if (waybillDatas.length > 0) {
                                for (var i = 0; i < waybillDatas.length; i++) {
                                    var makeWaybillWay = waybillDatas[i].data.makeWaybillWay;
                                    if (code == 'DDKD' || code == 'DDWFD') {
                                        if (makeWaybillWay == 'HDP' || makeWaybillWay == 'HDPWF') {
                                            Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                                                , airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.hdpOnddkdIfContinue'));
                                            return false;
                                        }
                                    } else if (code == 'HDP' || code == 'HDPWF') {
                                        if (makeWaybillWay == 'DDKD' || makeWaybillWay == 'DDWFD') {
                                            Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                                                , airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.ddkdOnhdpIfContinue'));
                                            return false;
                                        }
                                    }
                                }
                            }

                        }
                    }
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.jointTicketNo'),
                    xtype: 'textfield',
                    name: 'jointTicketNo',
                    disabled: true,
                    columnWidth: .25,
                    regex: /[^\d|chun]/g,
                    regexText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.znsrzmhsz'),
                    maxLength: 9,
                    maxLengthText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.hpcdycgzdxz')
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.dedtOrgName'),
                    xtype: 'commonairagencydeptselector',
                    name: 'dedtOrgName',
                    columnWidth: .25,
                    listeners: {
                        select: function (combo, record, index) {
                            var recordAgent = record[0].data;
                            var record = airfreight.airEnteringFlightBill.flightinformationForm.getRecord();
                            var flightinformationForm = airfreight.airEnteringFlightBill.flightinformationForm.getForm();
                            flightinformationForm.findField('arrvRegionName').orgCode = recordAgent.agentDeptCode;
                            var airAssembleType = flightinformationForm.findField('airAssembleType').getValue();
                            if (airAssembleType == 'HDP' || airAssembleType == 'HDPWF') {
                                flightinformationForm.findField('receiverName').setValue(recordAgent.agentDeptName);
                                flightinformationForm.findField('receiverContactPhone').setValue(recordAgent.contactPhone);
                            }
                            record.data.destOrgCode = recordAgent.agentDeptCode;
                            flightinformationForm.findField('arrvRegionName').setValue(recordAgent.cityName);
                            record.data.arrvRegionCode = recordAgent.cityCode;
                            combo.destCityCode = recordAgent.cityCode;
                        }
                    }
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.public.arrvRegionName'),
                    xtype: 'textfield',
                    name: 'airportLocationName',
                    columnWidth: .25,
                    allowBlank: true,
                    maxLength: 20,
                    maxLengthText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.cdcgzdxz')
                }
            ]
        }),
        airfreight.airEnteringFlightBill.Consignee = Ext.create('Ext.form.FieldSet', {
            title: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.consigneeMessage'),
            height: 105,
            width: 1025,
            layout: 'column',
            defaults: {
                margin: '5 5 5 5',
                xtype: 'textfield',
                allowBlank: false
            },
            items: [
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.receiverName'),
                    name: 'receiverName',
                    columnWidth: .25,
                    maxLength: 100,
                    maxLengthText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.shrcdycgzdxz')
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.receiverContactPhone'),
                    name: 'receiverContactPhone',
                    columnWidth: .25,
                    maxLength: 30,
                    maxLengthText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.dhcdycgzdxz')
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.accountItem'),
                    name: 'accountItem',
                    allowBlank: true,
                    columnWidth: .25,
                    maxLength: 100,
                    maxLengthText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.jssxcdycgzdxz')
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.billingAgency'),
                    allowBlank: true,
                    disabled: true,
                    name: 'billingAgency',
                    columnWidth: .25
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.receiverAddress'),
                    name: 'receiverAddress',
                    allowBlank: true,
                    columnWidth: .25,
                    maxLength: 500,
                    maxLengthText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.dzcdycgzdxz')
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.storageItem'),
                    name: 'storageItem',
                    allowBlank: true,
                    columnWidth: .25
                }
            ]
        }),
        airfreight.airEnteringFlightBill.Freightinformation = Ext.create('Ext.form.FieldSet', {
            title: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.flightpricingMessage'),
            height: 140,
            width: 1025,
            layout: 'column',
            defaults: {
                margin: '5 5 5 5',
                xtype: 'textfield',
                allowBlank: false
            },
            items: [
                {
                    xtype: 'commonflightselector',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.flightNo'),
                    name: 'flightNo',
                    columnWidth: .25,
                    listeners: {
                        select: function (combo, record, index) {
                            var record = record[0].data;
                            airfreight.airEnteringFlightBill.filghtInfo = record;
                            // 日期格式化
                            var formatDate = function (value, formatStr) {
                                if (!formatStr) formatStr = 'Y-m-d';
                                return Ext.Date.format(new Date(value), formatStr);
                            };
                            var startTime = Ext.Date.format(new Date(record.planLeaveTime), 'H:i');
                            var endTime = Ext.Date.format(new Date(record.planArriveTime), 'H:i');
                            airfreight.airEnteringFlightBill.flightinformationForm.getForm().findField('takeOffTime').setValue(startTime);
                            airfreight.airEnteringFlightBill.flightinformationForm.getForm().findField('arriveTime').setValue(endTime);
                            var airLineTwoletter = airfreight.airEnteringFlightBill.flightinformationForm.getForm().findField('airLineTwoletter').getValue();
                            if (airLineTwoletter == '') {
                                return false;
                            }
                            airfreight.airEnteringFlightBill.flightinformationForm.getForm().findField('flightNo').setValue(record.flightNo);
                            var origCode = record.origCode;
                            airfreight.airEnteringFlightBill.flightinformationForm.getRecord().data.airportCode = origCode;
                            //查询费率
                            airfreight.airEnteringFlightBill.flightinformationForm.queryAirlineRate(airLineTwoletter, origCode);
                            //计算相关费用
                            airfreight.airEnteringFlightBill.calculate(airfreight.airEnteringFlightBill.flightinformationForm.getRecord(), 'query', record.flightNo);
                        }
                    }
                },
                {
                    xtype: 'datefield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.flightDate'),
                    name: 'flightDate',
                    format: 'Y-m-d',
                    columnWidth: .25
                },
                {
                    xtype: 'timefield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.takeOffTime'),
                    name: 'takeOffTime',
                    altFormats: 'H:i|g:i A',
                    format: 'H:i',
                    increment: 1,
                    columnWidth: .25
                },
                {
                    xtype: 'timefield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.arriveTime'),
                    altFormats: 'H:i|g:i A',
                    format: 'H:i',
                    increment: 1,
                    name: 'arriveTime',
                    columnWidth: .25
                },
                {
                    xtype: 'combobox',
                    editable: false,
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.rateClass'),
                    name: 'rateClass',
                    hiddenName: 'rateClassr',
                    store: FossDataDictionary.getDataDictionaryStore('RATE_CLASS', 'Foss_login_language_store_Id'),
                    queryMode: 'local',
                    displayField: 'valueName',
                    valueField: 'valueCode',
                    columnWidth: .25
                },
                {
                    xtype: 'combobox',
                    editable: false,
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.paymentType'),
                    name: 'paymentType',
                    hiddenName: 'paymentType',
                    store: FossDataDictionary.getDataDictionaryStore('PAYMENT_TYPE', 'Foss_login_language_store_Id'),
                    queryMode: 'local',
                    displayField: 'valueName',
                    valueField: 'valueCode',
                    columnWidth: .25
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.grossWeight'),
                    name: 'grossWeight',
                    columnWidth: .25,
                    maxLength: 8,
                    maxValue: 99999999,
                    vtype: 'grossWeigth',
                    maxLengthText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.zlcgzdxz')
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.billingWeight'),
                    name: 'billingWeight',
                    columnWidth: .25,
                    maxValue: 9999999,
                    maxLength: 7,
                    maxLengthText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.jfzlcgzdxz'),
                    vtype: 'billingWeight',
                    listeners: {
                        blur: function (v, event, eOpts) {
                            var value = Ext.Number.from(v.value, 0);
                            if (value >= 0) {
                                var record = airfreight.airEnteringFlightBill.flightinformationForm.getRecord();
                                airfreight.airEnteringFlightBill.calculate(record, '');
                            }

                        }
                    }
                },
                {
                    xtype: 'numberfield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.fee'),
                    name: 'fee',
                    maxLength: 9,
                    columnWidth: .25,
                    minValue: 0,
                    hideTrigger: true,
                    keyNavEnabled: true,
                    mouseWheelEnabled: true,
                    decimalPrecision: 2,
                    maxLengthText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.yjcgzdxz'),
                    listeners: {
                        blur: function (v, event, eOpts) {
                            var fee = Ext.Number.from(v.value, 0);
                            if (fee >= 0) {
                                var airFee = 0;
                                var currentForm = airfreight.airEnteringFlightBill.flightinformationForm.getForm();
                                var billingWeight = Ext.Number.from(currentForm.findField('billingWeight').getValue(), 0);
                                //计算航空运费
                                if (airfreight.airEnteringFlightBill.minPriceRate != null || airfreight.airEnteringFlightBill.minPriceRate != '') {
                                    if (billingWeight * fee < airfreight.airEnteringFlightBill.minPriceRate) {
                                        airFee = airfreight.airEnteringFlightBill.fomatFloat(airfreight.airEnteringFlightBill.minPriceRate, 0);
                                    } else {
                                        airFee = airfreight.airEnteringFlightBill.fomatFloat(billingWeight * fee, 0);
                                    }
                                }
                                airfreight.airEnteringFlightBill.flightinformationForm.getRecord().data.airFee = airFee;
                                currentForm.findField('airFee').setValue(airFee);

                                var billingFee = currentForm.findField('billingFee').getValue();
                                var groundFee = currentForm.findField('groundFee').getValue();
                                var inseranceFee = currentForm.findField('inseranceFee').getValue();
                                var fuelSurcharge = currentForm.findField('fuelSurcharge').getValue();
                                var extraFee = currentForm.findField('extraFee').getValue();
                                //判断是否外发 ,如果属于外发 总金额等于航空运费+制单费用
                                //2013-07-22 wqh
                                var feeTotal = 0;//总金额
                                var airAssembleType = currentForm.findField('airAssembleType').getValue();
                                if (airAssembleType == 'DDWFD' || airAssembleType == 'HDPWF') {
                                    feeTotal = Ext.Number.from(airFee, 0) + Ext.Number.from(billingFee, 0);
                                    //初始化其他附加费用，默认为0 
                                    currentForm.findField('groundFee').setValue(0);
                                    currentForm.findField('fuelSurcharge').setValue(0);
                                    currentForm.findField('extraFee').setValue(0);
                                    currentForm.findField('transportInsurance').setValue(0);
                                } else {

                                    //总金额=航空运费+地面运费+保险费+燃油附加税+制单费+附加费
                                    feeTotal = Ext.Number.from(airFee, 0) + Ext.Number.from(groundFee, 0) + Ext.Number.from(inseranceFee, 0)
                                        + Ext.Number.from(fuelSurcharge, 0) + Ext.Number.from(billingFee, 0) + Ext.Number.from(extraFee, 0);
                                }
                                //总金额小于航空公司费率基础资料的最低总金额时，总金额等于基础资料最低总金额，
                                //大于并且等于航空公司费率基础资料的最低总金额时，总金额保持不变；小数点后保留2位
                                if (airfreight.airEnteringFlightBill.airlinesValueAddEntity != null
                                    && airfreight.airEnteringFlightBill.airlinesValueAddEntity.id != null) {
                                    if (feeTotal < airfreight.airEnteringFlightBill.airlinesValueAddEntity.minTotalFee) {
                                        feeTotal = airfreight.airEnteringFlightBill.airlinesValueAddEntity.minTotalFee;
                                    }
                                }
                                airfreight.airEnteringFlightBill.flightinformationForm.getRecord().data.feeTotal = feeTotal;
                                currentForm.findField('feeTotal').setValue(airfreight.airEnteringFlightBill.fomatFloat(feeTotal, 2));
                            }

                        }
                    }
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.agenctCode'),
                    name: 'agenctCode',
                    allowBlank: true,
                    columnWidth: .25,
                    hidden: false
                },
                {
                    xtype: 'commonagentselector',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.agenctCode'),
                    name: 'agencyName_wf',
                    columnWidth: .25,
                    hidden: true,
                    listeners: {
                        select: function (combo, record, index) {
                            var record = record[0].data;
                            var flightinformationForm = airfreight.airEnteringFlightBill.flightinformationForm.getForm();
                            var flightinformationFormRecord = airfreight.airEnteringFlightBill.flightinformationForm.getRecord();
                            flightinformationForm.findField('agenctCode').setValue(record.agentCode);
                            flightinformationFormRecord.data.agencyName = record.agentName;
                        }
                    }
                }
            ]
        }),
        airfreight.airEnteringFlightBill.GoodsInformation = Ext.create('Ext.form.FieldSet', {
            title: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.goodsMessage'),
            height: 210,
            width: 1025,
            layout: 'column',
            defaults: {
                margin: '5 5 5 5',
                xtype: 'textfield',
                allowBlank: false
            },
            items: [
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.declareValue'),
                    name: 'declareValue',
                    disabled: true,
                    columnWidth: .25
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.itemCode'),
                    disabled: false,
                    allowBlank: true,
                    name: 'itemCode',
                    readOnly: true,
                    columnWidth: .25,
                    maxLength: 25
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.goodsQty'),
                    name: 'goodsQty',
                    columnWidth: .25,
                    maxLength: 9,
                    maxValue: 99999999,
                    regexp : /^\d+$/,
                    maxLengthText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.jscgzdxz'),
                    vtype: 'goodsQty'
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.volume'),
                    name: 'volume',
                    columnWidth: .25,
                    maxLength: 8,
                    maxValue: 55555,
                    maxLengthText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.tjcgzdxz'),
                    vtype: 'volume'
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.airFee'),
                    name: 'airFee',
                    disabled: true,
                    columnWidth: .25
                },
                {
                    xtype: 'numberfield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.extraFee'),
                    name: 'extraFee',
                    columnWidth: .25,
                    minValue: 0,
                    maxValue: 999999999,
                    allowBlank: true,
                    hideTrigger: true,
                    keyNavEnabled: true,
                    mouseWheelEnabled: true,
                    decimalPrecision: 2,
                    listeners: {
                        blur: function (v, event, eOpts) {
                            var flightinformationForm = airfreight.airEnteringFlightBill.flightinformationForm.getForm();
                            var value = Ext.Number.from(v.value, 0);
                            if (v.value == null) {
                                flightinformationForm.findField('extraFee').setValue(0);
                            }
                            if (value >= 0) {
                                var airFee = flightinformationForm.findField('airFee').getValue();
                                var groundFee = flightinformationForm.findField('groundFee').getValue();
                                var inseranceFee = flightinformationForm.findField('inseranceFee').getValue();
                                var fuelSurcharge = flightinformationForm.findField('fuelSurcharge').getValue();
                                var billingFee = flightinformationForm.findField('billingFee').getValue();
                                var feeTotal = 0.00;

                                //判断是否外发，如果是外发则总金额等于航空运费加上制单费用
                                //2013-07-22 
                                //wqh
                                var airAssembleType = flightinformationForm.findField('airAssembleType').getValue();

                                if (airAssembleType == 'DDWFD' || airAssembleType == 'HDPWF') {

                                    return false;
                                } else {
                                    //总金额=航空运费+地面运费+保险费+燃油附加税+制单费+附加费
                                    feeTotal = Ext.Number.from(airFee, 0) + Ext.Number.from(groundFee, 0) + Ext.Number.from(inseranceFee, 0)
                                        + Ext.Number.from(fuelSurcharge, 0) + Ext.Number.from(billingFee, 0) + value;
                                }
                                //总金额小于航空公司费率基础资料的最低总金额时，总金额等于基础资料最低总金额，
                                if (airfreight.airEnteringFlightBill.airlinesValueAddEntity != null
                                    && airfreight.airEnteringFlightBill.airlinesValueAddEntity.id != null) {
                                    if (feeTotal < airfreight.airEnteringFlightBill.airlinesValueAddEntity.minTotalFee) {
                                        feeTotal = airfreight.airEnteringFlightBill.airlinesValueAddEntity.minTotalFee;
                                    }
                                }
                                airfreight.airEnteringFlightBill.flightinformationForm.getRecord().data.feeTotal = feeTotal;
                                flightinformationForm.findField('feeTotal').setValue(airfreight.airEnteringFlightBill.fomatFloat(feeTotal, 2));
                            }
                        }
                    }
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.groundFee'),
                    name: 'groundFee',
                    disabled: true,
                    columnWidth: .25
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.fuelSurcharge'),
                    name: 'fuelSurcharge',
                    disabled: true,
                    columnWidth: .25
                },
                {
                    xtype: 'numberfield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.transportInsurance'),
                    name: 'transportInsurance',
                    columnWidth: .25,
                    minValue: 0,
                    maxValue: 9999999999,
                    allowBlank: true,
                    hideTrigger: true,
                    keyNavEnabled: true,
                    mouseWheelEnabled: true,
                    decimalPrecision: 2,
                    maxLength: 10,
                    maxLengthText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.ysbxcgzdxz'),
                    listeners: {
                        blur: function (v, event, eOpts) {
                            var flightinformationForm = airfreight.airEnteringFlightBill.flightinformationForm.getForm();
                            if (v.value == null) {
                                flightinformationForm.findField('transportInsurance').setValue(0);
                            }
                            var value = Ext.Number.from(v.value, 0);
                            if (value >= 0) {

                                //判断是否外发，如果外发则总金额等于航空运费+制单费用
                                //wqh
                                //2013-07-22
                                var airAssembleType = flightinformationForm.findField('airAssembleType').getValue();
                                if (airAssembleType == 'DDWFD' || airAssembleType == 'HDPWF') {

                                    return false;
                                }
                                var airFee = flightinformationForm.findField('airFee').getValue();
                                var groundFee = flightinformationForm.findField('groundFee').getValue();
                                var fuelSurcharge = flightinformationForm.findField('fuelSurcharge').getValue();
                                var billingFee = flightinformationForm.findField('billingFee').getValue();
                                var extraFee = flightinformationForm.findField('extraFee').getValue();
                                var inseranceFee = 0;


                                //总金额=航空运费+地面运费+保险费+燃油附加税+制单费
                                var feeTotal = Ext.Number.from(airFee, 0) + Ext.Number.from(groundFee, 0) + Ext.Number.from(fuelSurcharge, 0)
                                    + Ext.Number.from(billingFee, 0) + Ext.Number.from(extraFee, 0);
                                //地面运费、燃油附加税、保险费   的费率信息
                                var airlinesValueAddEntity = airfreight.airEnteringFlightBill.airlinesValueAddEntity;
                                if (airlinesValueAddEntity != null && airlinesValueAddEntity.id != null) {
                                    var transportInsurance = flightinformationForm.findField('transportInsurance').getValue();
                                    var bxfl = airlinesValueAddEntity.insuranceFee;//保险费率 
                                    //计算保险费
                                    if (transportInsurance * bxfl < airlinesValueAddEntity.minInsuranceFee) {
                                        inseranceFee = airlinesValueAddEntity.minInsuranceFee;
                                    } else {
                                        inseranceFee = transportInsurance * bxfl;
                                    }
                                    //总金额=航空运费+地面运费+保险费+燃油附加税+制单费
                                    feeTotal = feeTotal + Ext.Number.from(inseranceFee, 0);
                                    //总金额小于航空公司费率基础资料的最低总金额时，总金额等于基础资料最低总金额，
                                    if (feeTotal < airfreight.airEnteringFlightBill.airlinesValueAddEntity.minTotalFee) {
                                        feeTotal = airfreight.airEnteringFlightBill.airlinesValueAddEntity.minTotalFee;
                                    }
                                }
                                airfreight.airEnteringFlightBill.flightinformationForm.getRecord().data.inseranceFee = inseranceFee;
                                airfreight.airEnteringFlightBill.flightinformationForm.getRecord().data.feeTotal = feeTotal;
                                flightinformationForm.findField('inseranceFee').setValue(airfreight.airEnteringFlightBill.fomatFloat(inseranceFee, 2));
                                flightinformationForm.findField('feeTotal').setValue(airfreight.airEnteringFlightBill.fomatFloat(feeTotal, 2));
                            }
                        }
                    }
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.inseranceFee'),
                    xtype: 'numberfield',
                    disabled: false,
                    minValue: 0,
                    maxValue: 9999999999,
                    maxLength: 10,
                    name: 'inseranceFee',
                    columnWidth: .25,
                    allowBlank: true,
                    hideTrigger: true,
                    keyNavEnabled: true,
                    mouseWheelEnabled: true,
                    //保险费改变时重新计算
                    //BUG-54800 新增和修改航空正单，录入保险费后，保存，总运费没有加上保险费
                    //wqh 2013-09-13
                    listeners: {
                        blur: function (v, event, eOpts) {
                            var flightinformationForm = airfreight.airEnteringFlightBill.flightinformationForm.getForm();
                            if (v.value == null) {
                                flightinformationForm.findField('inseranceFee').setValue(0);
                            }
                            var value = Ext.Number.from(v.value, 0);
                            if (value >= 0) {

                                //判断是否外发，如果外发则总金额等于航空运费+制单费用
                                //wqh
                                //2013-07-22
                                var airAssembleType = flightinformationForm.findField('airAssembleType').getValue();
                                if (airAssembleType == 'DDWFD' || airAssembleType == 'HDPWF') {
                                    return false;
                                }
                                var airFee = flightinformationForm.findField('airFee').getValue();
                                var groundFee = flightinformationForm.findField('groundFee').getValue();
                                var fuelSurcharge = flightinformationForm.findField('fuelSurcharge').getValue();
                                var billingFee = flightinformationForm.findField('billingFee').getValue();
                                var extraFee = flightinformationForm.findField('extraFee').getValue();
                                var inseranceFee = Ext.Number.from(flightinformationForm.findField('inseranceFee').getValue(), 0);
                                //总金额=航空运费+地面运费+保险费+燃油附加税+制单费
                                var feeTotal = Ext.Number.from(airFee, 0) + Ext.Number.from(groundFee, 0) + Ext.Number.from(fuelSurcharge, 0)
                                    + Ext.Number.from(billingFee, 0) + Ext.Number.from(extraFee, 0) + inseranceFee;

                                airfreight.airEnteringFlightBill.flightinformationForm.getRecord().data.inseranceFee = inseranceFee;
                                airfreight.airEnteringFlightBill.flightinformationForm.getRecord().data.feeTotal = feeTotal;
                                flightinformationForm.findField('inseranceFee').setValue(airfreight.airEnteringFlightBill.fomatFloat(inseranceFee, 2));
                                flightinformationForm.findField('feeTotal').setValue(airfreight.airEnteringFlightBill.fomatFloat(feeTotal, 2));
                            }
                        }
                    }
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.feeTotal'),
                    name: 'feeTotal',
                    disabled: true,
                    columnWidth: .25
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.feePlain'),
                    name: 'feePlain',
                    columnWidth: .25,
                    maxLength: 100,
                    maxLengthText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.fysmcgzdxz')
                },
                {
                    xtype: 'numberfield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.billingFee'),
                    name: 'billingFee',
                    allowBlank: true,
                    columnWidth: .25,
                    minValue: 0,
                    maxValue: 999999999,
                    hideTrigger: true,
                    keyNavEnabled: true,
                    mouseWheelEnabled: true,
                    decimalPrecision: 2,
                    listeners: {
                        blur: function (v, event, eOpts) {
                            var value = Ext.Number.from(v.value, 0);
                            if (value >= 0) {
                                var flightinformationForm = airfreight.airEnteringFlightBill.flightinformationForm.getForm();
                                var airFee = flightinformationForm.findField('airFee').getValue();
                                var groundFee = flightinformationForm.findField('groundFee').getValue();
                                var inseranceFee = flightinformationForm.findField('inseranceFee').getValue();
                                var fuelSurcharge = flightinformationForm.findField('fuelSurcharge').getValue();
                                var extraFee = flightinformationForm.findField('extraFee').getValue();
                                var billingFee = flightinformationForm.findField('billingFee').getValue();
                                var feeTotal = 0.00;

                                //判断是否外发，如果是外发则总金额等于航空运费+制单费用
                                //2013-07-22 
                                //wqh
                                var airAssembleType = flightinformationForm.findField('airAssembleType').getValue();

                                if (airAssembleType == 'DDWFD' || airAssembleType == 'HDPWF') {
                                    feeTotal = Ext.Number.from(airFee, 0) + Ext.Number.from(billingFee, 0);
                                    flightinformationForm.findField('groundFee').setValue(0);

                                    flightinformationForm.findField('fuelSurcharge').setValue(0);
                                    flightinformationForm.findField('inseranceFee').setValue(0);
                                    flightinformationForm.findField('transportInsurance').setValue(0);
                                } else {
                                    //总金额=航空运费+地面运费+保险费+燃油附加税+制单费+附加费
                                    feeTotal = Ext.Number.from(airFee, 0) + Ext.Number.from(groundFee, 0) + Ext.Number.from(inseranceFee, 0)
                                        + Ext.Number.from(fuelSurcharge, 0) + Ext.Number.from(extraFee, 0) + value;
                                }
                                /*//总金额=航空运费+地面运费+保险费+燃油附加税+制单费+附加费
                                 feeTotal = Ext.Number.from(airFee,0) + Ext.Number.from(groundFee,0) + Ext.Number.from(inseranceFee,0)
                                 + Ext.Number.from(fuelSurcharge,0)+value + extraFee;*/
                                //总金额小于航空公司费率基础资料的最低总金额时，总金额等于基础资料最低总金额，
                                if (airfreight.airEnteringFlightBill.airlinesValueAddEntity != null
                                    && airfreight.airEnteringFlightBill.airlinesValueAddEntity.id != null) {
                                    if (feeTotal < airfreight.airEnteringFlightBill.airlinesValueAddEntity.minTotalFee) {
                                        feeTotal = airfreight.airEnteringFlightBill.airlinesValueAddEntity.minTotalFee;
                                    }
                                }
                                airfreight.airEnteringFlightBill.flightinformationForm.getRecord().data.feeTotal = feeTotal;
                                flightinformationForm.findField('feeTotal').setValue(airfreight.airEnteringFlightBill.fomatFloat(feeTotal, 2));
                            }
                        }
                    }
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.goodsName'),
                    xtype: 'textareafield',
                    allowBlank: true,
                    name: 'goodsName',
                    columnWidth: .25,
                    maxLength: 100,
                    maxLengthText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.hwmccdycgzdxz')
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.packageStruction'),
                    xtype: 'textareafield',
                    name: 'packageStruction',
                    allowBlank: true,
                    columnWidth: .25,
                    maxLength: 100,
                    maxLengthText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.bzsmcdycgzdxz')
                }
            ]
        }),
        airfreight.airEnteringFlightBill.OtherInfo = Ext.create('Ext.form.FieldSet', {
            title: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.OthersMessage'),
            height: 105,
            width: 1025,
            layout: 'column',
            defaults: {
                margin: '5 5 5 5',
                xtype: 'textfield',
                allowBlank: false
            },
            items: [
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.shipperName'),
                    name: 'shipperName',
                    disabled: true,
                    columnWidth: .25
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.shipperAddress'),
                    name: 'shipperAddress',
                    allowBlank: true,
                    columnWidth: .25,
                    maxLength: 500,
                    maxLengthText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.dzcdycgzdxz')
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.shipperContactPhone'),
                    name: 'shipperContactPhone',
                    allowBlank: true,
                    columnWidth: .25,
                    maxLength: 50,
                    maxLengthText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.dhcdycgzdxz')
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.pickupType'),
                    name: 'pickupType',
                    disabled: true,
                    columnWidth: .25
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.createOrgName'),
                    name: 'createOrgName',
                    disabled: true,
                    columnWidth: .25
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.createUserName'),
                    name: 'createUserName',
                    disabled: true,
                    columnWidth: .25
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.createTime'),
                    name: 'createTime',
                    xtype: 'datetimefield_date97',
                    editable: false,
                    time: true,
                    disabled: true,
                    id: 'Foss_airfreight_createTime_ID',
                    allowBlank: false,
                    dateConfig: {
                        el: 'Foss_airfreight_createTime_ID-inputEl'
                    },
                    columnWidth: .25
                }
            ]
        })
    ],
    constructor: function (config) {
        var me = this,
            cfg = Ext.apply({}, config);
        me.callParent([cfg]);
        airfreight.airEnteringFlightBill.vtypes = Ext.apply(Ext.form.field.VTypes, {
            phone: function (val, field) {
                if (!isNaN(val)) {
                    return true;
                } else {
                    return false;
                }
            },
            grossWeigth: function (val, field) {
                var str = val + '';
                if (str.substring(0, 1) == '-')
                    return false;
                if (str.substring(0, 2) == '00')
                    return false;
                if (str.indexOf("..") == 1)
                    return false;
                var isNotNumber = str.substring(str.length - 1, str.length);
                if (!/^[0-9]+$/.test(isNotNumber))
                    return false;
                return /^-?\d+\.?\d{0,1}$/.test(Ext.Number.from(val, 0));

            },
            billingWeight: function (val, field) {
                var str = val + '';
                if (str.substring(0, 1) == '-')
                    return false;
                if (str.substring(0, 2) == '00')
                    return false;
                if (str.indexOf("..") == 1)
                    return false;
                var isNotNumber = str.substring(str.length - 1, str.length);
                if (!/^[0-9]+$/.test(isNotNumber))
                    return false;
                return /^-?\d+\.?\d{0,1}$/.test(Ext.Number.from(val, 0));
            },
            goodsQty: function (val, field) {
                var str = val + '';
                if (str.indexOf(".") == 1)
                    return false;
                if (str.substring(0, 2) == '00')
                    return false;
                var isNotNumber = str.substring(str.length - 1, str.length);
                if (!/^[0-9]+$/.test(isNotNumber))
                    return false;
                return /^\d+$/.test(Ext.Number.from(val, 0));
            },
            volume: function (val, field) {
                var str = val + '';
                if (str.substring(0, 1) == '-')
                    return false;
                if (str.substring(0, 2) == '00')
                    return false;
                if (str.indexOf("..") == 1)
                    return false;
                var isNotNumber = str.substring(str.length - 1, str.length);
                if (!/^[0-9]+$/.test(isNotNumber))
                    return false;
                return /^-?\d+\.?\d{0,2}$/.test(Ext.Number.from(val, 0));
            },
            extraFee: function (val, field) {
                var str = val + '';
                if (str.substring(0, 1) == '-')
                    return false;
                if (str.substring(0, 2) == '00')
                    return false;
                if (str.indexOf("..") == 1)
                    return false;
                var isNotNumber = str.substring(str.length - 1, str.length);
                if (!/^[0-9]+$/.test(isNotNumber))
                    return false;
                return /^-?\d+\.?\d{0,2}$/.test(Ext.Number.from(val, 0));
            },
            transportInsurance: function (val, field) {
                var str = val + '';
                if (str.indexOf(".") == 1)
                    return false;
//		    	if(str.substring(0,1)=='0')
//		    		return false;
                var isNotNumber = str.substring(str.length - 1, str.length);
                if (!/^[0-9]+$/.test(isNotNumber))
                    return false;
                return /^[0-9]*[1-9][0-9]*$/.test(Ext.Number.from(val, 0));
            },
            volumeText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.tsgsyw'),
            goodsQtyText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.number'),
            billingWeightText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.billingWeight'),
            grossWeigthText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.weight'),
            transportInsuranceText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.transportationInsurance'),
            billingFeeText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.billingFee'),
            phoneText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.phone')
        });
        me.basicInfo = airfreight.airEnteringFlightBill.BasicInfo;
        me.consignee = airfreight.airEnteringFlightBill.Consignee;
        me.freightinformation = airfreight.airEnteringFlightBill.Freightinformation;
        me.goodsInformation = airfreight.airEnteringFlightBill.GoodsInformation;
        me.otherInfo = airfreight.airEnteringFlightBill.OtherInfo;
    }
});

//选择航空公司三字码
Ext.define('Foss.airfreight.airLineTwoletterFreeCode', {
    extend: 'Ext.window.Window',
    width: 400,
    height: 240,
    title: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.optionAirportCode'),
    closeable: true,
    modal: true,
    closeAction: 'hide',
    createForm: function () {
        if (airfreight.airEnteringFlightBill.showCodeForm) {
            return airfreight.airEnteringFlightBill.showCodeForm;
        }
        airfreight.airEnteringFlightBill.showCodeForm = Ext.create('Ext.form.Panel', {
            frame: false,
            border: false,
            width: 360,
            layout: 'column',
            defaults: {
                margin: '5 5 5 5'
            },
            //获取始发站三字码、目的站三字码
            queryAirportCode: function (url, cityCode, paramsTypes) {
                var records = null;
                var params = null;
                if (paramsTypes == 'deptRegion') {
                    params = {
                        'pointsSingleJointTicketVO.deptRegionCode': cityCode
                    };
                } else {
                    params = {
                        'pointsSingleJointTicketVO.arrvRegionCode': cityCode
                    };
                }
                Ext.Ajax.request({
                    //设置后台返回的合票号
                    async: false,
                    url: airfreight.realPath(url),
                    params: params,
                    success: function (response) {
                        var result = Ext.decode(response.responseText);
                        if (paramsTypes == 'deptRegion') {
                            records = result.pointsSingleJointTicketVO.deptRegionAirportList;
                            if (!records) {
                                records = [];
                            }
                            var json = {
                                fields: ['airportCode', 'airportName'],
                                data: records
                            };
                            var store = Ext.create('Ext.data.Store', json);
                            var deptRegionCode = airfreight.airEnteringFlightBill.showCodeForm.getForm().findField('deptRegionCode');
                            deptRegionCode.store = store;
                        } else {
                            records = result.pointsSingleJointTicketVO.arrvRegionAirportList;
                            if (!records) {
                                records = [];
                            }
                            var json = {
                                fields: ['airportCode', 'airportName'],
                                data: records
                            };
                            var store = Ext.create('Ext.data.Store', json);
                            var arrvRegionCode = airfreight.airEnteringFlightBill.showCodeForm.getForm().findField('arrvRegionCode');
                            arrvRegionCode.store = store;
                        }
                    },
                    exception: function (response) {
                        var result = Ext.decode(response.responseText);
                        Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'), result.message);
                        return false;
                    }
                });
                return records;
            },
            //制作唐翼制单
            saveTangYiAwb: function (url, deptRegionCode, arrvRegionCode, airWaybillNo) {
                params = {
                    'pointsSingleJointTicketVO.deptRegionCode': deptRegionCode,
                    'pointsSingleJointTicketVO.arrvRegionCode': arrvRegionCode,
                    'pointsSingleJointTicketVO.airWaybillNo': airWaybillNo
                };
                Ext.Ajax.request({
                    //设置后台返回的合票号
                    async: false,
                    url: airfreight.realPath(url),
                    params: params,
                    success: function (response) {
                        var result = Ext.decode(response.responseText);
                        Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                            , airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.makesuccess'));
                    },
                    exception: function (response) {
                        var result = Ext.decode(response.responseText);
                        Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'), result.message);
                        return false;
                    }
                });
            },
            items: [
                {
                    xtype: 'combobox',
                    mode: 'local',
                    queryMode: 'local',
                    triggerAction: 'all',
                    forceSelection: true,
                    editable: false,
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.deptRegionCode'),
                    name: 'deptRegionCode',
                    displayField: 'airportCode',
                    valueField: 'airportCode',
                    store: null,
                    labelWidth: 100,
                    width: 300,
                    listeners: {
                        focus: function (e, t, s) {
                            var record = airfreight.airEnteringFlightBill.flightinformationForm.getRecord();
                            var deptRegionCode = record.data.deptRegionCode;
                            var url = 'byDeptRegionCodeQueryAirportCode.action';
                            var cityCode = deptRegionCode;
                            var paramsTypes = 'deptRegion';
                            airfreight.airEnteringFlightBill.showCodeForm.queryAirportCode(url, cityCode, paramsTypes);
                        }
                        //beforequery事件
                    }
                },
                {
                    xtype: 'combobox',
                    mode: 'local',
                    queryMode: 'local',
                    triggerAction: 'all',
                    forceSelection: true,
                    editable: false,
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.arrvRegionCode'),
                    name: 'arrvRegionCode',
                    displayField: 'airportCode',
                    valueField: 'airportCode',
                    store: null,
                    labelWidth: 100,
                    width: 300,
                    listeners: {
                        focus: function (e, t, s) {
                            var record = airfreight.airEnteringFlightBill.flightinformationForm.getRecord();
                            var arrvRegionCode = record.data.arrvRegionCode;
                            var url = 'byArrvRegionCodeQueryAirportCode.action';
                            var cityCode = arrvRegionCode;
                            var paramsTypes = 'arrvRegion';
                            airfreight.airEnteringFlightBill.showCodeForm.queryAirportCode(url, cityCode, paramsTypes);
                        }
                    }
                },
                {
                    xtype: 'container',
                    margin: '10 5 5 60'
                },
                {
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            layout: 'column',
                            defaultType: 'button',
                            width: 300,
                            items: [
                                {
                                    xtype: 'container',
                                    html: '&nbsp;',
                                    columnWidth: .65
                                },
                                {
                                    text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.ensure'),
                                    columnWidth: .15,
                                    handler: function () {
                                        var deptRegionCode = airfreight.airEnteringFlightBill.showCodeForm.getForm().findField('deptRegionCode').getValue();
                                        var arrvRegionCode = airfreight.airEnteringFlightBill.showCodeForm.getForm().findField('arrvRegionCode').getValue();
                                        var record = airfreight.airEnteringFlightBill.flightinformationForm.getRecord();
                                        var airWaybillNo = record.data.airWaybillNo;
                                        var url = 'saveTangYiAwb.action';
                                        airfreight.airEnteringFlightBill.showCodeForm.saveTangYiAwb(url, deptRegionCode, arrvRegionCode, airWaybillNo);
                                    }
                                },
                                {
                                    xtype: 'container',
                                    html: '&nbsp;',
                                    columnWidth: .05
                                },
                                {
                                    text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.cancel'),
                                    columnWidth: .15,
                                    handler: function () {
                                        airfreight.airEnteringFlightBill.airLineTwoletterFreeCode.close();
                                    }
                                }
                            ]
                        }
                    ]
                }
            ]
        });
        return airfreight.airEnteringFlightBill.showCodeForm;
    },
    constructor: function (config) {
        var me = this,
            cfg = Ext.apply({}, config);
        me.callParent([cfg]);
        me.createForm();
        me.add([airfreight.airEnteringFlightBill.showCodeForm]);
    }
});

//设置运单明细收缩展开样式
Ext.define('Foss.airfreight.submitForm', {
    extend: 'Ext.form.Panel',
    frame: false,
    defaults: {
        margin: '0 5 5 5'
    },
    items: [
        {
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'bottom',
                    items: [
                        {
                            xtype: 'button',
                            text: '+',
                            collapsible: true,
                            animCollapse: false,
                            handler: function () {
                                if (this.text == '+') {
                                    this.setText('-'),
                                        airfreight.airEnteringFlightBill.showGrid.setVisible(true);
                                } else {
                                    this.setText('+'),
                                        airfreight.airEnteringFlightBill.showGrid.setVisible(false);
                                }
                            }
                        },
                        {
                            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.airWaybillDetail')
                        },
                        {
                            xtype: 'container',
                            html: '&nbsp;',
                            width: 710
                        },
                        {

                            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.tanyizhidan'),
                            disabled: !airfreight.airEnteringFlightBill.isPermission('airfreight/saveTangYiAwbButton'),
                            hidden: !airfreight.airEnteringFlightBill.isPermission('airfreight/saveTangYiAwbButton'),
                            handler: function () {
                                var aiyWayBillId = airfreight.airEnteringFlightBill.aiyWayBillId;
                                if (aiyWayBillId == null || aiyWayBillId == '') {
                                    Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                                        , airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.noSaveUnableCreatetangyi'));
                                    return false;
                                }
                                var airLineTwoletter = airfreight.airEnteringFlightBill.flightinformationForm.getForm().findField('airLineTwoletter').getValue();
                                if (airLineTwoletter != 'CZ') {
                                    Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                                        , airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.onlyNHuse'));
                                    return false;
                                }
                                airfreight.airEnteringFlightBill.airLineTwoletterFreeCode = Ext.create('Foss.airfreight.airLineTwoletterFreeCode').show();
                            }
                        },
                        {
                            xtype: 'container',
                            html: '&nbsp;',
                            width: 35
                        },
                        {
                            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.save'),
                            disabled: !airfreight.airEnteringFlightBill.isPermission('airfreight/airEnteringFlightBillButton'),
                            hidden: !airfreight.airEnteringFlightBill.isPermission('airfreight/airEnteringFlightBillButton'),
                            handler: function () {
                                var flightinformationForm = airfreight.airEnteringFlightBill.flightinformationForm.getForm();
                                var hiddenButton2 = airfreight.airEnteringFlightBill.submitForm.down('toolbar').query('button');
                                hiddenButton2[3].disable(true);
                                var airAssembleType = flightinformationForm.findField('airAssembleType').getValue();
                                //判断配载类型如为单独开单或者合大票时承运人外发代理和制单费不是必输项
                                if (airAssembleType == 'DDKD' || airAssembleType == 'HDP') {
                                    flightinformationForm.findField('agencyName_wf').allowBlank = true;
                                }
                                //20130319修改在保存时提示必输项没有输入的具体名称。
                                var fieldElements = airfreight.airEnteringFlightBill.flightinformationForm.getForm()._fields;
                                for (var i = 0; i < fieldElements.length; i++) {
                                    if (fieldElements.items[i].allowBlank == false && (fieldElements.items[i].value == null || fieldElements.items[i].value == '')) {
                                        Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                                            , fieldElements.items[i].fieldLabel + airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.notInput'));
                                        hiddenButton2[3].setDisabled(false);
                                        return false;
                                    }
                                }
                                if (!airfreight.airEnteringFlightBill.flightinformationForm.getForm().isValid()) {
                                    Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                                        , airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.validError'));
                                    hiddenButton2[3].setDisabled(false);
                                    return false;
                                }
                                var formatDate = function (value, formatStr) {
                                    if (!formatStr) formatStr = 'Y-m-d';
                                    return Ext.Date.format(new Date(value), formatStr);
                                };
                                var record = airfreight.airEnteringFlightBill.flightinformationForm.getRecord();
                                flightinformationForm.updateRecord(record);
                                var flightDate = formatDate(record.data.flightDate);
                                var takeOffTime = flightinformationForm.findField('takeOffTime').getValue();
                                var arriveTime = flightinformationForm.findField('arriveTime').getValue();
                                var billingAgency = flightinformationForm.findField('billingAgency').getValue();
                                var arriveDate = flightDate + ' ' + formatDate(arriveTime, 'H:i') + ':00';
                                var takeOffDate = flightDate + ' ' + formatDate(takeOffTime, 'H:i') + ':00';
                                record.data.arriveTime = arriveDate;
                                record.data.takeOffTime = takeOffDate;
                                record.data.billingAgency = billingAgency;
                                record.data.transportType = airfreight.airEnteringFlightBill.airWaybillTranportType;
                                var records = airfreight.airEnteringFlightBill.showGrid.store.data.items;
                                //2013-07-10 ISSUE-3269 允许不包含运单的航空正单保存，并且不包含运单的航空正单中重量体积和件数允许为0
//            	if(records.length==0 || records.length==''){
//            		Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
//            				,airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.pleaseOptionSubmitWaybillDetail'));
//            		hiddenButton2[3].setDisabled(false);
//            		return false;
//            	}
                                var jsonData = new Array();
                                //遍历航空正单list集合
                                for (var i = 0; i < records.length; i++) {
                                    jsonData.push(records[i].data);
                                }
                                //判断配载类型如为单独开单或者单独开单外发时不允许提交多个运单明细
                                if (airAssembleType == 'DDKD' || airAssembleType == 'DDWFD') {
                                    if (records.length > 1) {
                                        Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
                                            airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.CanAlsoChooseDifferentType'));
                                        hiddenButton2[3].setDisabled(false);
                                        return false;
                                    }
                                }
                                //保存名称和编码
                                record.data.destOrgCode = flightinformationForm.findField('dedtOrgName').getValue();
                                record.data.dedtOrgName = flightinformationForm.findField('dedtOrgName').rawValue;
                                //add  2013-06-08如果手工输入目的站文本框 不为空，则保存手工输入的目的站
                                var airportLocationName = flightinformationForm.findField('airportLocationName').getValue();
                                if (!Ext.isEmpty(airportLocationName)) {
                                    record.data.arrvRegionName = airportLocationName;
                                }
                                record.data.airWaybillNo = Ext.String.trim(record.data.airWaybillNo);
                                //收集航空正单、明细、流水
                                var airWayBilEntityList = {pointsSingleJointTicketVO: {billDetailList: jsonData, billEntity: record.data,
                                    delParameterMap: airfreight.airEnteringFlightBill.delMap.map,
                                    parameter: airfreight.airEnteringFlightBill.serialNoListMap.map}};
                                Ext.Ajax.request({
                                    url: airfreight.realPath('addAirwayBill.action'),
                                    jsonData: airWayBilEntityList,
                                    success: function (response) {
                                        var result = Ext.decode(response.responseText);
                                        //保存航空正单id
                                        var aiyWayBillId = result.pointsSingleJointTicketVO.ticketDto.aiyWayBillId;
                                        airfreight.airEnteringFlightBill.aiyWayBillId = aiyWayBillId;
                                        Ext.ux.Toast.msg(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
                                            airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.saveSuccess'));
                                        //保存成功后移除合票信息列表中对应的元素
                                        airfreight.airEnteringFlightBill.jointTicketInformation.store.remove(records);
                                        //保存成功后将form表单变灰
                                        setFormEditAble(airfreight.airEnteringFlightBill.flightinformationForm);
                                        //保存成功后隐藏保存按钮、移除按钮、分单合票按钮
                                        var hiddenButton1 = airfreight.airEnteringFlightBill.showGrid.down('toolbar').query('button');
                                        hiddenButton1[0].disable(true);
                                        hiddenButton1[1].disable(true);
                                    },
                                    exception: function (response) {
                                        var result = Ext.decode(response.responseText);
                                        Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'), '[' + ascii2native(result.message) + ']'
                                            + airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.saveFailure'));
                                        var hiddenButton2 = airfreight.airEnteringFlightBill.submitForm.down('toolbar').query('button');
                                        hiddenButton2[3].setDisabled(false);
                                        return false;
                                    }
                                });
                            }
                        }
                    ]
                }
            ]
        }
    ],
    constructor: function (config) {
        var me = this,
            cfg = Ext.apply({}, config);
        me.callParent([cfg]);
    }
});
//ascii转native
function ascii2native(strAscii) {
    var output = "";
    var posFrom = 0;
    var posTo = strAscii.indexOf("\\u", posFrom);
    while (posTo >= 0) {
        output += strAscii.substring(posFrom, posTo);
        output += toChar(strAscii.substr(posTo, 6));
        posFrom = posTo + 6;
        posTo = strAscii.indexOf("\\u", posFrom);
    }
    output += strAscii.substr(posFrom);
    return output;
}
function toChar(str) {
    if (str.substr(0, 2) != "\\u") return str;

    var code = 0;
    for (var i=2; i<str.length; i++) {
        var cc = str.charCodeAt(i);
        if (cc >= 0x30 && cc <= 0x39)
            cc = cc - 0x30;
        else if (cc >= 0x41 && cc <= 0x5A)
            cc = cc - 0x41 + 10;
        else if (cc >= 0x61 && cc <= 0x7A)
            cc = cc - 0x61 + 10;

        code <<= 4;
        code += cc;
    }

    if (code < 0xff) return str;

    return String.fromCharCode(code);
}
//运单明细列表
Ext.define('Foss.airfreight.showGrid', {
    extend: 'Ext.grid.Panel',
    frame: true,
    border: false,
    autoScroll: true,
    width: 1025,
    //layout: 'column',
    height: 250,
    emptyText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.searchResultInexistence'),
    defaults: {
        sortable: true,
        flex: 1
    },
    columns: [
        {
            xtype: 'actioncolumn',
            width: 40,
            text: '操作',
            align: 'center',
            items: [
                {
                    tooltip: '查看尺寸及储运事项',
                    iconCls: 'deppon_icons_showdetail',
                    handler: function (grid, rowIndex, colIndex, item, e) {
                        var waybillNo = grid.store.getAt(rowIndex).get('waybillNo');
                        airfreight.airEnteringFlightBill.detailWaybillNo = waybillNo;
                        var x = e.getX();
                        var y = e.getY();
                        var transportType = grid.store.getAt(rowIndex).get('transportType');
                        if(transportType != 'PACKAGE_AIR'){
	                        if (airfreight.airEnteringFlightBill.waybillDetailWindow == null) {
	                            airfreight.airEnteringFlightBill.waybillDetailWindow = Ext.create('Foss.airfreight.airEnteringFlightBill.waybillDetailWindow');
	                        }
	                        airfreight.airEnteringFlightBill.waybillDetailWindow.showAt(x + 10, y + 10);
                        }
                    }
                }
            ]
        },
        {
            xtype: 'ellipsiscolumn',
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.waybillNo'),
            dataIndex: 'waybillNo',
            flex: 0.9
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.flightNumberType'),
            dataIndex: 'planFlightNo',
            flex: 0.8,
            renderer:function(value){
				//航班类型
				if(value.trim() == ''){
					return null;
				}else{
					return FossDataDictionary.rendererSubmitToDisplay(value,'AIR_FLIGHT_TYPE');
				}
			}
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.makeWaybillWay'),
            dataIndex: 'makeWaybillWay',
            flex: 0.8,
            renderer: function (v) {
                if (v == 'HDP') {
                    return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.hdp');
                }else if(v == ' '){
                	return null;
                }else {
                    return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.ddkd');
                }
            }
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.arrvRegionName'),
            dataIndex: 'arrvRegionName',
            flex: 0.7
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.weight'),
            flex: 0.8,
            dataIndex: 'grossWeight'
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.billingWeight'),
            flex: 0.8,
            dataIndex: 'billingWeight'
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.volume'),
            flex: 0.7,
            dataIndex: 'volume'
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.goodsQty'),
            flex: 0.7,
            dataIndex: 'goodsQty'
        },
        {
            xtype: 'ellipsiscolumn',
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.goodsName'),
            flex: 1.2,
            dataIndex: 'goodsName'
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.stockStatus'),
            dataIndex: 'stockStatus',
            flex: 0.8,
            renderer: function (value) {
                if (value == 'NOTSTORE') {//未入库
                    return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.storage');
                } else if (value == 'INSTORE') { //库存中
                    return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.instore');
                }
            }
        },
        {
            xtype: 'ellipsiscolumn',
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.receiveOrgName'),
            dataIndex: 'receiveOrgName',
            flex: 1.5
        }
    ],
    dockedItems: [
        {
            xtype: 'toolbar',
            dock: 'bottom',
            layout: 'column',
            defaultType: 'button',
            items: [
                {
                    xtype: 'container',
                    html: '&nbsp;',
                    columnWidth: .79
                },
                {
                    text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.remove'),
                    columnWidth: .06,
                    handler: function () {
                        record = airfreight.airEnteringFlightBill.showGrid.getSelectionModel().getSelection();
                        if (record.length == 0) {
                            Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
                                airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.UnselectedRemoveItem'));
                            return false;
                        }
                        airfreight.airEnteringFlightBill.showGrid.store.remove(record);
                        var serialNoListMap = airfreight.airEnteringFlightBill.serialNoListMap;
                        for (var i = 0; i < record.length; i++) {
                            var wayBillNo = record[i].data.waybillNo;
                            //如果运单号对应的流水明细列表为空，则从数据库查询出来赋值给全局变量，保存时提交到后台用于判断
                            if (serialNoListMap.get(wayBillNo)) {
                                serialNoListMap.removeAtKey(wayBillNo);
                            }
                        }
                        airfreight.airEnteringFlightBill.serialNoListMap = serialNoListMap;
                        var updateRecord = airfreight.airEnteringFlightBill.showGrid.store.data.items;
                        var showGridStore = airfreight.airEnteringFlightBill.showGrid.store;
                        var showGridRecord = updateRecord.length;
                        var getRecordValues = airfreight.airEnteringFlightBill.flightinformationForm.getForm();
                        if (showGridRecord != 0) {
                            airfreight.airEnteringFlightBill.SubmitJointTicket(updateRecord, 'update');
                        } else {
                            getRecordValues.findField('grossWeight').reset();
                            getRecordValues.findField('billingWeight').reset();
                            getRecordValues.findField('goodsQty').reset();
                            getRecordValues.findField('volume').reset();
                            getRecordValues.findField('goodsName').reset();
                            getRecordValues.findField('packageStruction').reset();
                        }
                    }
                },
                {
                    xtype: 'container',
                    html: '&nbsp;', 
                    columnWidth: .04
                },
                {
                    text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket'),
                    disabled: !airfreight.airEnteringFlightBill.isPermission('airfreight/jointticketButton'),
                    hidden: !airfreight.airEnteringFlightBill.isPermission('airfreight/jointticketButton'),
                    columnWidth: .08,
                    handler: function () {
                        var freightMethod = airfreight.airEnteringFlightBill.flightinformationForm.getForm().findField('airAssembleType').value;
                        var existRecord = airfreight.airEnteringFlightBill.showGrid.store.data.items;
                        //当组装类型为单独开单或者是单独开单外发时只能选择一条运单信息
                        if (freightMethod == 'DDKD' || freightMethod == 'DDWFD') {
                            if (existRecord.length > 0) {
                                Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                                    , airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.hasExistRecord'));
                                return false;
                            }
                        }
                        if (!airfreight.airEnteringFlightBill.jointTicketInformationWindow) {
                            airfreight.airEnteringFlightBill.jointTicketInformationWindow = Ext.create('Ext.window.Window', {
                                title: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket'),
                                modal: true,
                                closable: true,
                                closeAction: 'hide',
                                width: 1200,
                                height: 900,
                                layout: 'auto',
                                items: [
                                    airfreight.airEnteringFlightBill.findForm = Ext.create('Foss.airfreight.findForm'),
                                    airfreight.airEnteringFlightBill.submenuGtasks = Ext.create('Foss.airfreight.submenuGtasks'),
                                    airfreight.airEnteringFlightBill.queryForm = Ext.create('Foss.airfreight.QueryForm'),
                                    airfreight.airEnteringFlightBill.jointTicketInformation = Ext.create('Foss.airfreight.JointTicketInformation')
                                ]
                            });
                        }
                        airfreight.airEnteringFlightBill.jointTicketInformationWindow.show()
                    }
                }
            ]
        }
    ],
    constructor: function (config) {
        var me = this,
            cfg = Ext.apply({}, config);
        me.selModel = Ext.create('Ext.selection.CheckboxModel'),
            me.store = Ext.create('Foss.airfreight.beassembleStore'),
            me.callParent([cfg]);
    }
});

/**
 *************************************** 合票信息 *********************************
 */
//分单合票查询条件
Ext.define('Foss.airfreight.findForm', {
    extend: 'Ext.form.Panel',
    layout: 'column',
    frame: true,
    title: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.searchCondition'), //查询条件
    height: 170,
    defaults: {
        xtype: 'textfield',
        margin: '5 5 5 5',
        allowBlank: false
    },
    items: [
        {
            xtype: 'combobox',
            allowBlank: true,
            fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.makeWaybillWay'), //开单方式
            emptyText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.all'),
            name: 'makeWaybillWay',
            value: 'ALL',
            hiddenName: 'makeWaybillWay',
            store: Ext.create('Ext.data.Store', {
                fields: ['valueName', 'valueCode'],
                data: [
                    {'valueName': airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.all'), 'valueCode': ''},
                    {'valueName': airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.hdp'), 'valueCode': 'HDP'},
                    {'valueName': airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.ddkd'), 'valueCode': 'DDKD'}
                ]
            }),
            queryMode: 'local',
            displayField: 'valueName',
            valueField: 'valueCode',
            editable: false,
            forceSelection: true,
            columnWidth: .25

        },
        {
            name: 'stockStatus',
            fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.stockStatus'),
            allowBlank: true,
            xtype: 'combobox',
            value: 'ALL',
            emptyText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.all'), //全部
            store: Ext.create('Ext.data.Store', {
                fields: ['valueName', 'valueCode'],
                data: [
                    {'valueName': airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.all'), 'valueCode': 'ALL'},
                    {'valueName': airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.instore'), 'valueCode': 'INSTORE'},
                    {'valueName': airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.storage'), 'valueCode': 'NOTSTORE'}
                ]
            }),
            /*listeners: {
                //设置当选择库存状态为未入库必须选择精准空运
                select: function (me) {
                    var form = me.up('form').getForm();
                    var cmb = form.findField('transportType');
                    if (me.value == 'NOTSTORE') {
                        cmb.setValue('PRECISION_AIR');
                        cmb.setReadOnly(true);
                    } else {
                        cmb.setReadOnly(false);
                    }
                }
            },*/
            queryMode: 'local',
            displayField: 'valueName',
            valueField: 'valueCode',
            editable: false,
            forceSelection: true,
            columnWidth: .25
        },
        {
            fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.arrvRegionName'),
            name: 'arrvRegionName',
            allowBlank: true,
            columnWidth: .25
        },
        {
            xtype: 'combobox',
            allowBlank: false,
            name: 'transportType',
            fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.tranportType'),
            emptTxt: airfreight.airEnteringFlightBill.i18n('foss.airfreight.all'),
            store: Ext.create('Ext.data.Store', {
                fields: ['valueName', 'valueCode'],
                data: [
                    {'valueName': airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.precisionAir'), 'valueCode': 'PRECISION_AIR'},
                    {'valueName': airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.packageAir'), 'valueCode': 'PACKAGE_AIR'}
                ]
            }),
            queryMode: 'local',
            value: 'PRECISION_AIR',
            displayField: 'valueName',
            valueField: 'valueCode',
            editable: false,
            columnWidth: .25,
            listeners: {
	    	'change' : function(field,newValue,oldValue,eOpts){
	    		var form = this.up('form').getForm();
	    		if(newValue=='PRECISION_AIR'){
	    			for(var i = 0;i<airfreight.airEnteringFlightBill.findForm.items.length;i++){
	    				if("foss_airfreight_storageTime_panel" == airfreight.airEnteringFlightBill.findForm.items.items[i].name){
	    					airfreight.airEnteringFlightBill.findForm.items.items[i].hide();
	    				}
	    			}
	    			form.findField('arrvRegionName').setReadOnly(false);
	    			form.findField('nextTfrOrg').setValue(null);
	    			form.findField('nextTfrOrg').setReadOnly(true);
	    			
	    			form.findField('stockStatus').setReadOnly(false);
	    			form.findField('stockStatus').setValue('ALL');
	    		}else{
	    			form.findField('arrvRegionName').setReadOnly(true);
	    			form.findField('nextTfrOrg').setReadOnly(false);
	    			for(var i = 0;i<airfreight.airEnteringFlightBill.findForm.items.length;i++){
	    				if("foss_airfreight_storageTime_panel" == airfreight.airEnteringFlightBill.findForm.items.items[i].name){
	    					airfreight.airEnteringFlightBill.findForm.items.items[i].show();
	    				}
	    			}
	    			form.findField('stockStatus').setValue('INSTORE');
	    			form.findField('stockStatus').setReadOnly(true);

	    		}
	    	}
	     }
        },
        {
            xtype: 'rangeDateField',
            fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.createWaybillTime'),
            fieldId: 'Foss_airfreight_singlejointticket_Id' + (Ext.Date.format(new Date(), 'Y-m-d H:i:s')),
            dateType: 'datetimefield_date97',
            fromName: 'beginInTime',
            toName: 'endInTime',
            dateRange: 10, //时间跨度不能大于10天
            editable: false,
            fromValue: Ext.Date.format(new Date(), 'Y-m-d') + ' ' + '00:00:00',
            toValue: Ext.Date.format(new Date(), 'Y-m-d') + ' ' + '23:59:59',
            columnWidth: .4
        },{
    		xtype:'dynamicorgcombselector',
    		type : 'ORG',
    		transferCenter : 'Y',
    		fieldLabel:airfreight.airEnteringFlightBill.i18n('foss.airfreight.airhandovebill.label.nextTfrOrg'),  //下一外场
    		allowBlank:true,
    		name:'nextTfrOrg',
    		columnWidth:.25,
    		readOnly:true
    	},
      /*  {
            xtype: 'container',
            html: '&nbsp;',
            columnWidth: .17
        },*/
        {
            xtype: 'button',
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.search'),
            disabled: !airfreight.airEnteringFlightBill.isPermission('airfreight/queryWayBillInfoButton'),
            hidden: !airfreight.airEnteringFlightBill.isPermission('airfreight/queryWayBillInfoButton'),
            columnWidth: .08,
            cls: 'yellow_button',
            handler: function () {
                var n = airfreight.airEnteringFlightBill.findForm.getValues();
                //获取下一到达外场
                var form = this.up('form').getForm();
              //  var dest_dept = airfreight.airEnteringFlightBill.flightinformationForm.getForm().findField('dedtOrgName').getValue();
               
                var dest_dept=form.findField('nextTfrOrg').getValue();
                if (!airfreight.airEnteringFlightBill.findForm.getForm().isValid()) {
                    Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
                        airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.includeNotInput'));
                    return false;
                }
                //如果选择快递空运，则到达网点不能为空
           /*    if (n.transportType == 'PACKAGE_AIR' &&
                    (dest_dept == null || dest_dept == '')) {
                    Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
                        airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.nextTfrOrgIsNull'));
                    return false;
                }*/
                var params = {
                    'pointsSingleJointTicketVO.ticketDto.makeWaybillWay': n.makeWaybillWay,
                    'pointsSingleJointTicketVO.ticketDto.stockStatus': n.stockStatus,
                    'pointsSingleJointTicketVO.ticketDto.arrvRegionName': n.arrvRegionName,
                    'pointsSingleJointTicketVO.ticketDto.beginInTime': n.beginInTime,
                    'pointsSingleJointTicketVO.ticketDto.endInTime': n.endInTime,
                    'pointsSingleJointTicketVO.ticketDto.storageBeginInTime': n.storageBeginInTime,
                    'pointsSingleJointTicketVO.ticketDto.storageEndInTime': n.storageEndInTime,
                    'pointsSingleJointTicketVO.ticketDto.nextDestOrg': dest_dept,
                    'pointsSingleJointTicketVO.transportType': n.transportType
                };
                Ext.Ajax.request({
                    url: airfreight.realPath('queryWayBillInfo.action'),
                    timeout: 600000,
                    params: params,
                    success: function (response) {
                        var result = Ext.decode(response.responseText);
                        var arryvolumeResult = result.pointsSingleJointTicketVO.billDetailList;
                        var newArray = [];
                        //清除gird所有记录
                        airfreight.airEnteringFlightBill.submenuGtasks.store.removeAll();
                        //获取合票信息所有行数据
                        var jointTicketInformationStore = airfreight.airEnteringFlightBill.jointTicketInformation.store;
                        //合票信息数组长度
                        var gridLength = jointTicketInformationStore.data.items.length
                        var showGridDisable = [];
                        var showGridview = [];
                        if (arryvolumeResult == null) {
                            var count = 0;
                            var toolbarArray = airfreight.airEnteringFlightBill.submenuGtasks.down('toolbar').query('textfield');
                            for (var j = 0; j < toolbarArray.length; j++) {
                                toolbarArray[j].setValue(0);
                                count++;
                            }
                            return false;
                        }
                        airfreight.airEnteringFlightBill.jointTicketInformation.store.removeAll();
                        if (showGridview.length > 0) {
                            airfreight.airEnteringFlightBill.submenuGtasks.store.add(showGridview);
                        } else {
                            airfreight.airEnteringFlightBill.submenuGtasks.store.add(arryvolumeResult);
                        }
                    },
                    exception: function (response) {
                        var result = Ext.decode(response.responseText);
                        Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.errorMessage'), result.message);
                    }
                });
            }
        },
       /* {
            xtype: 'container',
            html: '&nbsp;',
            columnWidth: .15
        },*/
        {
            xtype: 'panel',
            border: false,
            height: 18,
            width: 25,
            columnWidth: .05,
            cls: 'x-grid-record-yellow'
        },
        {
            html: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.ddkd'),
            xtype: 'panel',
            columnWidth: .06,
            border: false
        },
        {
            xtype: 'panel',
            border: false,
            height: 18,
            width: 25,
            columnWidth: .05,
            cls: 'x-grid-record-gray'
        },
        {
            html: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.storage'),
            xtype: 'panel',
            columnWidth: .06,
            border: false
        },{ 
		  xtype: 'panel',
		  name:'foss_airfreight_storageTime_panel',
		  hidden:true,
		  height: 40,
		  columnWidth: .4 ,
		  items: [{
	        xtype: 'rangeDateField',
	        fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.storageTime'),
	        fieldId:'Foss_airfreight_storageTime_Id'+(Ext.Date.format(new Date(),'Y-m-d H:i:s')),  
	        dateType: 'datetimefield_date97',
	        fromName: 'storageBeginInTime',
	        toName: 'storageEndInTime',
	        dateRange: 7, //时间跨度不能大于7天
	        editable: false,
	        fromValue: Ext.Date.format(new Date(), 'Y-m-d') + ' ' + '00:00:00',
	        toValue: Ext.Date.format(new Date(), 'Y-m-d') + ' ' + '23:59:59',
	        columnWidth: .9 
		 }]
	   }
    ],
    constructor: function (config) {
        var me = this,
            cfg = Ext.apply({}, config);
        me.callParent([cfg]);
    }
});

//待选信息、合票信息model
Ext.define('Foss.airfreight.singlejointticketModel', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'id', type: 'string'},
        {name: 'waybillNo', type: 'string'},
        {name: 'goodsPackage', type: 'string'},
        {name: 'freightMethod', type: 'string'},//开单方式
        {name: 'makeWaybillWay', type: 'string'},
        {name: 'arrvRegionName', type: 'string'},
        {name: 'grossWeight', type: 'number'},
        {name: 'billingWeight', type: 'number'},
        {name: 'planFlightNo', type: 'string'},
        {name: 'volume', type: 'number'},
        {name: 'goodsQty', type: 'number'},
        {name: 'goodsName', type: 'string'},
        {name: 'collectionFee', type: 'number'},
        {name: 'arrivalFee', type: 'number'},
        {name: 'pickupType', type: 'string'},
        {name: 'deliverFee', type: 'number'},
        {name: 'receiverAddress', type: 'string'},
        {name: 'receiverContactPhone', type: 'string'},
        {name: 'receiverName', type: 'string'},
        {name: 'stockStatus', type: 'string'},
        {name: 'receiveOrgName', type: 'string'},
        {name: 'departTime', type: 'date', convert: dateConvert},//交接单到达对应空运总调外场的出发时间
        {name: 'unitPrice', type: 'string'},//运单费率
        {name: 'lockRemark', type: 'string'},//锁票备注
        {name: 'lockStatus', type: 'string'},//锁票状态
        {name: 'goodsQtyTotal', type: 'string'},//开单总件数
        {name: 'stockQty', type: 'string'},//库存总件数
        {name: 'transportType', type: 'string'},
        {name: 'productCode', type: 'string'}

    ]
});

Ext.define('Foss.airfreight.singlejointticketStore', {
    extend: 'Ext.data.Store',
    model: 'Foss.airfreight.singlejointticketModel',
    proxy: {
        type: 'ajax',
        actionMethods: 'POST',
        url: airfreight.realPath('wayBillFind.action'),
        reader: {
            type: 'json',
            root: 'pointsSingleJointTicketVO.volumeResult',
            successProperty: 'success'
        }
    },
    listeners: {
        beforeload: function (store, operation, eOpts) {
            var n = airfreight.airEnteringFlightBill.findForm.getValues();
            Ext.apply(operation, {
                params: {
                    'pointsSingleJointTicketVO.ticketDto': n.arrvRegionName
                }
            });
        },
        datachanged: function (store, operation, eOpts) {
            var totalArray = store.data.items;
            //总件数
            var goodsQtyTotal = 0;
            //毛重合
            var weightTotal = 0;
            //计费重量
            var billingWeightTotal = 0;
            //体积
            var volumeTotal = 0;
            //总票数
            var billNoTotal = totalArray.length;

            for (var i = 0; i < totalArray.length; i++) {
                goodsQtyTotal = goodsQtyTotal + Ext.Number.from(totalArray[i].data.goodsQty, 0);
                weightTotal = weightTotal + Ext.Number.from(totalArray[i].data.grossWeight, 0);
                billingWeightTotal = billingWeightTotal + Ext.Number.from(totalArray[i].data.billingWeight, 0);
                volumeTotal = volumeTotal + Ext.Number.from(totalArray[i].data.volume, 0);
            }
            var count = 0;
            var toolbarArray = airfreight.airEnteringFlightBill.submenuGtasks.down('toolbar').query('textfield');
            for (var j = 0; j < toolbarArray.length; j++) {
                if (count == 0) {
                    toolbarArray[j].setValue(billNoTotal);
                } else if (count == 1) {
                    toolbarArray[j].setValue(goodsQtyTotal);
                } else if (count == 2) {
                    toolbarArray[j].setValue(airfreight.airEnteringFlightBill.fomatFloat(weightTotal, 1) + '  ' + airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.kilo'));
                } else if (count == 3) {
                    toolbarArray[j].setValue(airfreight.airEnteringFlightBill.fomatFloat(billingWeightTotal, 1) + '  ' + airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.kilo'));
                } else {
                    toolbarArray[j].setValue(airfreight.airEnteringFlightBill.fomatFloat(volumeTotal, 2) + '  ' + airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.cube'));
                }
                count++;
            }
        }
    }
});

//合票信息stroe
Ext.define('Foss.airfreight.beassembleStore', {
    extend: 'Ext.data.Store',
    model: 'Foss.airfreight.singlejointticketModel',
    proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            root: 'items'
        }
    },
    listeners: {
        datachanged: function (store, operation, eOpts) {
            var totalArray = store.data.items;
            //总件数
            var goodsQtyTotal = 0;
            //毛重合
            var weightTotal = 0;
            //计费重量
            var billingWeightTotal = 0;
            //体积
            var volumeTotal = 0;
            //总票数
            var billNoTotal = totalArray.length;

            for (var i = 0; i < totalArray.length; i++) {
                goodsQtyTotal = goodsQtyTotal + Ext.Number.from(totalArray[i].data.goodsQty, 0);
                weightTotal = weightTotal + Ext.Number.from(totalArray[i].data.grossWeight, 0);
                billingWeightTotal = billingWeightTotal + Ext.Number.from(totalArray[i].data.billingWeight, 0);
                volumeTotal = volumeTotal + Ext.Number.from(totalArray[i].data.volume, 0);
            }
            var count = 0;
            var toolbarArray = airfreight.airEnteringFlightBill.jointTicketInformation.down('toolbar').query('textfield');
            for (var j = 0; j < toolbarArray.length; j++) {
                if (count == 0) {
                    toolbarArray[j].setValue(billNoTotal);
                } else if (count == 1) {
                    toolbarArray[j].setValue(goodsQtyTotal);
                } else if (count == 2) {
                    toolbarArray[j].setValue(airfreight.airEnteringFlightBill.fomatFloat(weightTotal, 1) + '  ' + airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.kilo'));
                    toolbarArray[j].hideValue = airfreight.airEnteringFlightBill.fomatFloat(weightTotal, 1);
                } else if (count == 3) {
                    toolbarArray[j].setValue(airfreight.airEnteringFlightBill.fomatFloat(billingWeightTotal, 1) + '  ' + airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.kilo'));
                    toolbarArray[j].hideValue = airfreight.airEnteringFlightBill.fomatFloat(billingWeightTotal, 1);
                } else {
                    toolbarArray[j].setValue(airfreight.airEnteringFlightBill.fomatFloat(volumeTotal, 2) + '  ' + airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.cube'));
                    toolbarArray[j].hideValue = airfreight.airEnteringFlightBill.fomatFloat(volumeTotal, 2);
                }
                count++;
            }
        }
    }
});


//分单待选信息列表
Ext.define('Foss.airfreight.submenuGtasks', {
    extend: 'Ext.grid.Panel',
    title: '待选信息',
    //layout: 'column',
    frame: true,
    border: true,
    autoScroll: true,
    height: 300,
    emptyText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.searchResultInexistence'),
    defaults: {
        sortable: true,
        flex: 1
    },
    viewConfig: {
        stripeRows: false,
        getRowClass: function (record, rowIndex, rp, ds) {
            var makeWaybillWay = record.get('makeWaybillWay');
            if (makeWaybillWay == 'DDKD') {
                return 'x-grid-record-yellow';
            }
            var stockStatus = record.get('stockStatus');
            if (stockStatus == 'NOTSTORE') {
                return 'x-grid-record-gray';
            }
        }
    },
    columns: [
        {
            xtype: 'actioncolumn',
            width: 60,
            text: '操作',
            align: 'center',
            items: [
                {
                    tooltip: '查看尺寸及储运事项',
                    iconCls: 'deppon_icons_showdetail',
                    handler: function (grid, rowIndex, colIndex, item, e) {
                        var waybillNo = grid.store.getAt(rowIndex).get('waybillNo');
                        airfreight.airEnteringFlightBill.detailWaybillNo = waybillNo;
                        var x = e.getX();
                        var y = e.getY();
                        var transportType = grid.store.getAt(rowIndex).get('transportType');
                        if(transportType != 'PACKAGE_AIR'){
	                        if (airfreight.airEnteringFlightBill.waybillDetailWindow == null ) {
	                            airfreight.airEnteringFlightBill.waybillDetailWindow = Ext.create('Foss.airfreight.airEnteringFlightBill.waybillDetailWindow');
	                        }
	                        airfreight.airEnteringFlightBill.waybillDetailWindow.showAt(x + 10, y + 10);
                        }
                    }
                },
                {
                    iconCls: 'foss_icons_stl_noFreeze',
                    handler: function (grid, rowIndex, colIndex, item, e) {

                        var waybillNo = grid.store.getAt(rowIndex).get('waybillNo');
                        var lockStatus = grid.store.getAt(rowIndex).get('lockStatus');
                        airfreight.airEnteringFlightBill.lockWaybillNo = waybillNo;
                        airfreight.airEnteringFlightBill.rowIndex = rowIndex;
                        airfreight.airEnteringFlightBill.grid = grid;
                        if (lockStatus == 'Y') {
                            Ext.MessageBox.confirm(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
                                '确认要解锁当前运单吗？',
                                function (btn) {
                                    if (btn == 'yes') {
                                        Ext.Ajax.request({
                                            url: airfreight.realPath('unlockAirWaybill.action'),
                                            params: {'pointsSingleJointTicketVO.airLockWaybillDetailEntity.waybillNo': airfreight.airEnteringFlightBill.lockWaybillNo
                                            },
                                            success: function (response) {
                                                grid.store.getAt(rowIndex).set('lockStatus', 'N');
                                                grid.store.getAt(rowIndex).set('lockRemark', '');
                                                airfreight.airEnteringFlightBill.lockWaybillWindow.hide();
                                            },
                                            exception: function (response) {
                                                var result = Ext.decode(response.responseText);
                                                top.Ext.MessageBox.alert('提示',
                                                    result.message);
                                            }
                                        });
                                    }
                                }
                            );
                            return;
                        }
                        var x = e.getX();
                        var y = e.getY();
                        var transportType = grid.store.getAt(rowIndex).get('transportType');
                        if(transportType != 'PACKAGE_AIR'){
                            if (airfreight.airEnteringFlightBill.lockWaybillWindow == null){
                            	airfreight.airEnteringFlightBill.lockWaybillWindow = Ext.create('Foss.airfreight.airEnteringFlightBill.lockWaybillWindow');
                            } 
			    airfreight.airEnteringFlightBill.lockWaybillWindow.showAt(x + 10, y + 10);
			}
                    }

                }
            ],
            renderer: function (value, metadata, record) {
                lockStatus = record.get('lockStatus');
                if (lockStatus == 'Y') {
                    this.items[1].iconCls = 'foss_icons_stl_freeze';
                } else {
                    this.items[1].iconCls = 'foss_icons_stl_noFreeze';
                }
            }
        },
        {
            xtype: 'ellipsiscolumn',
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.waybillNo'),
            dataIndex: 'waybillNo',
            flex: 1.0
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.flightNumberType'),
            dataIndex: 'planFlightNo',
            flex: 0.8,
         	renderer:function(value){
				//航班类型
				if(value.trim() == ''){
					return null;
				}else{
					return FossDataDictionary.rendererSubmitToDisplay(value,'AIR_FLIGHT_TYPE');
				}
			}
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.makeWaybillWay'),
            dataIndex: 'makeWaybillWay',
            flex: 0.8,
            renderer: function (v) {
                if (v == 'HDP') {
                    return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.hdp');
                }else if(v == ' '){
                	return null;
                } else {
                    return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.ddkd');
                }
            }
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.arrvRegionName'),
            dataIndex: 'arrvRegionName',
            flex: 0.7
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.weight'),
            dataIndex: 'grossWeight',
            flex: 0.5
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.billingWeight'),
            dataIndex: 'billingWeight',
            flex: 0.8
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.volume'),
            dataIndex: 'volume',
            flex: 0.5
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.goodsQty'),
            dataIndex: 'goodsQty',
            flex: 0.5
        },
        {
            xtype: 'ellipsiscolumn',
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.goodsName'),
            dataIndex: 'goodsName',
            flex: 0.9
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.stockStatus'),
            dataIndex: 'stockStatus',
            flex: 0.8,
            renderer: function (value) {
                if (value == 'NOTSTORE') {//未入库
                    return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.storage');
                } else if (value == 'INSTORE') { //库存中
                    return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.instore');
                }
            }
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.departTime'),
            flex: 1.5,
            dataIndex: 'departTime',
            xtype: 'datecolumn',//外场交单出发时间
            format: 'Y-m-d H:i:s'
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.unit_price'),
            flex: 0.5,
            dataIndex: 'unitPrice'//运单费率
        },
        {
            xtype: 'ellipsiscolumn',
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.receiveOrgName'),
            dataIndex: 'receiveOrgName',
            flex: 1.5
        },
        {
            xtype: 'ellipsiscolumn',
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.lockRemark'),//锁票备注
            dataIndex: 'lockRemark',
            flex: 1.5
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.tranportType'),
            dataIndex: 'transportType',
            renderer: function (value) {
                if (value == 'PACKAGE_AIR') {
                  return  airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.packageAir');
                } else {
                   return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.precisionAir');
                }
            },
            sortable:false
        },
        
        {
            xtype: 'ellipsiscolumn',
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airCargoVolume.label.pickupType'),//提货方式
            dataIndex: 'pickupType',
            flex: 1.5,
            renderer: function (value) {
                if (value == 'DELIVER_INGA') {
                  return  airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.pickup.DELIVER_INGA');
                } else if(value=='DELIVER_NOUP'){
                   return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.pickup.DELIVER_NOUP');
                }else if(value=='DELIVER'){
                   return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.pickup.DELIVER');
                }else if(value=='DELIVER_UP'){
                   return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.pickup.DELIVER_UP');
                }else if(value=='INNER_PICKUP'){
                   return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.pickup.INNER_PICKUP');
                }else if(value=='SELF_PICKUP'){
                   return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.pickup.SELF_PICKUP');
                }else if(value=='LARGE_DELIVER_UP'){
                   return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.pickup.LARGE_DELIVER_UP');
                }else if(value=='SELF_PICKUP_FREE_AIR'){
                   return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.pickup.SELF_PICKUP_FREE_AIR');
                }else if(value=='DELIVER_INGA_AIR'){
                   return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.pickup.DELIVER_INGA_AIR');
                }else if(value=='DELIVER_UP_AIR'){
                   return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.pickup.DELIVER_UP_AIR');
                }else if(value=='DELIVER_AIR'){
                   return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.pickup.DELIVER_AIR');
                }else if(value=='AIRPORT_PICKUP'){
                   return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.pickup.AIRPORT_PICKUP');
                }else if(value=='DELIVER_NOUP_AIR'){
                   return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.pickup.DELIVER_NOUP_AIR');
                }else if(value=='SELF_PICKUP_AIR'){
                   return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.pickup.SELF_PICKUP_AIR');
                }
            }

            
        }
        
    ],
    dockedItems: [
        {
            xtype: 'toolbar',
            dock: 'bottom',
            layout: 'column',
            defaults: {
                xtype: 'textfield',
                value: '0',
                readOnly: true,
                labelWidth: 50,
                width: 30
            },
            items: [
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.poll'),
                    columnWidth: .10,
                    dataIndex: 'billNoTotal'
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.goodsQty'),
                    columnWidth: .10,
                    dataIndex: 'goodsQtyTotal'
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.grossWeight'),
                    columnWidth: .14,
                    dataIndex: 'weightTotal'
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.billingWeight'),
                    labelWidth: 70,
                    columnWidth: .15,
                    dataIndex: 'billingWeightTotal'
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.volume'),
                    columnWidth: .15,
                    dataIndex: 'volumeTotal'
                }
            ]
        }
    ],
    constructor: function (config) {
        var me = this,
            cfg = Ext.apply({}, config);
        me.store = Ext.create('Foss.airfreight.singlejointticketStore');
        me.selModel = Ext.create('Ext.selection.CheckboxModel'),
            airfreight.airEnteringFlightBill.pagingBar = me.store;
        me.callParent([cfg]);
        me.addListener({ 'itemdblclick': me.itemdblclick, scope: this});
    },
    itemdblclick: function (view, record, item, index, e, eOpts) {

        var lockStatus = record.get('lockStatus');
        var waybillNo = record.get('waybillNo');
        //判断是否已锁定
        if (lockStatus == 'Y') {
            Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
                    '运单' + waybillNo + '未解锁，不允许下移，请先解锁');
            return;
        }
        var goodsQty = record.get('stockQty');
        var goodsQtyTotal = record.get('goodsQtyTotal');
        var isDown = true;
        var transportType=record.get('transportType');
        var items=airfreight.airEnteringFlightBill.jointTicketInformation.store.data.items;
    	for(var i=0;i<items.length;i++){
    		var productCode=items[i].data.transportType;
    		if(productCode!=transportType){
    			
              Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
                 '运单【'+waybillNo+'】与【'+items[i].data.waybillNo+"】 运输类型不同！");
                 return;
    
    		}
    		
    	}
        //判断当前配载件数是否等于开单件数
        if (goodsQty != goodsQtyTotal && transportType != 'PACKAGE_AIR') {
            Ext.MessageBox.confirm(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
                    '运单' + waybillNo + '开单件数与空运总调库存件数不一致，是否确定快速下移?',
                function (btn) {
                    if (btn == 'yes') {
                    	//判断是否全都是快递空运的类型的运单
                    	
                    	
                        airfreight.airEnteringFlightBill.jointTicketInformation.store.add(record);
                        airfreight.airEnteringFlightBill.submenuGtasks.store.remove(record);
                        return;
                    } else {
                        return;
                    }
                })

        } else {
            airfreight.airEnteringFlightBill.jointTicketInformation.store.add(record);
            airfreight.airEnteringFlightBill.submenuGtasks.store.remove(record);
        }


    }
});

//定义货物详情窗口
Ext.define('Foss.airfreight.airEnteringFlightBill.waybillDetailWindow', {
    extend: 'Ext.window.Window',
    closeAction: 'hide',
    resizable: false,
    width: 300,
    draggable: false,//拖动
    modal: true,
    title: '详情',
    items: [
        {
            fieldLabel: '货物尺寸',
            xtype: 'textarea',
            readOnly: true
        },
        {
            fieldLabel: '储运事项',
            xtype: 'textarea',
            readOnly: true
        }
    ],
    listeners: {
        'beforeshow': function (cmp, obj) {
            Ext.Ajax.request({
                url: airfreight.realPath('queryWaybillDetailByWaybillNo.action'),
                params: {'pointsSingleJointTicketVO.waybillDetailDto.waybillNo': airfreight.airEnteringFlightBill.detailWaybillNo},
                success: function (response) {
                    var result = Ext.decode(response.responseText);
                    obj = result.pointsSingleJointTicketVO.waybillDetailDto,
                        size = obj.goodSize,
                        remark = obj.transportationRemark;
                    cmp.items.items[0].setValue(size);
                    cmp.items.items[1].setValue(remark);
                },
                exception: function (response) {
                    var result = Ext.decode(response.responseText);
                    top.Ext.MessageBox.alert('提示',
                        result.message);
                }
            });
        }
    }
});

//定义空运锁票备注窗口
Ext.define('Foss.airfreight.airEnteringFlightBill.lockWaybillWindow', {
    extend: 'Ext.window.Window',
    closeAction: 'hide',
    resizable: false,
    width: 400,
    height: 200,
    draggable: false,//拖动
    modal: true,
    title: '空运锁票',
    items: [
        {
            fieldLabel: '锁票备注',
            id: 'airfreight.airEnteringFlightBill.lockRemark',
            name: 'airfreight.airEnteringFlightBill.lockRemark',
            xtype: 'textarea',
            maxLength: 1000,
            width: 300,
            allowBlank: false,
            readOnly: false
        }
    ],
    buttons: [
        { text: '确认',
            handler: function (cmp, obj) {
                var grid = airfreight.airEnteringFlightBill.grid;
                var rowIndex = airfreight.airEnteringFlightBill.rowIndex;
                var lockRemark = Ext.query('[name=airfreight.airEnteringFlightBill.lockRemark]')[0].value
                if (lockRemark == null || lockRemark == '') {
                    Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
                        '请输入锁票备注');
                    return;
                }
                Ext.Ajax.request({
                    url: airfreight.realPath('addAirLockWaybillDetail.action'),
                    params: {'pointsSingleJointTicketVO.airLockWaybillDetailEntity.waybillNo': airfreight.airEnteringFlightBill.lockWaybillNo,
                        'pointsSingleJointTicketVO.airLockWaybillDetailEntity.lockRemark': lockRemark
                    },
                    success: function (response) {
                        grid.store.getAt(rowIndex).set('lockStatus', 'Y');
                        grid.store.getAt(rowIndex).set('lockRemark', lockRemark);
                        airfreight.airEnteringFlightBill.lockWaybillWindow.hide();
                        Ext.query('[name=airfreight.airEnteringFlightBill.lockRemark]')[0].value = '';
                    },
                    exception: function (response) {
                        var result = Ext.decode(response.responseText);
                        top.Ext.MessageBox.alert('提示',
                            result.message);
                    }
                });

            }
        }
    ]

});

Ext.define('Foss.airfreight.QueryForm', {
    extend: 'Ext.form.Panel',
    layout: 'column',
    height: 50,
    frame: false,
    //遍历待选信息是否已在合票信息、运单明细列表中
    vtype: function (record) {
        var recordMap = new Ext.util.HashMap();
        for (var i = 0; i < record.length; i++) {
            recordMap.add(record[i].data.id, record)
        }
    },
    defaults: {
        xtype: 'textfield'
    },
    items: [
        {
            fieldLabel: '',
            readOnly: true,
            columnWidth: .10
        },
        {
            xtype: 'button',
            iconCls: 'up-airfreight',
            tooltip: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.upmove'),
            width: 50,
            handler: function () {
                records = airfreight.airEnteringFlightBill.jointTicketInformation.getSelectionModel().getSelection();
                if (records.length != 0) {
                    var submenuGtasks = airfreight.airEnteringFlightBill.submenuGtasks.store;
                    var submenuRecord = submenuGtasks.data.items.length;
                    var jointTicketInformation = airfreight.airEnteringFlightBill.jointTicketInformation.store;
                    var jointTicketRecord = jointTicketInformation.data.items.length;
                    airfreight.airEnteringFlightBill.submenuGtasks.store.add(records);
                    airfreight.airEnteringFlightBill.jointTicketInformation.store.remove(records);

                } else {
                    Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
                        airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.optionJointticket'));
                }
            }
        },
        {
            fieldLabel: '',
            readOnly: true,
            columnWidth: .10
        },
        {
            xtype: 'button',
            iconCls: 'down-airfreight',
            tooltip: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.nextmove'),
            width: 50,
            handler: function () {
                records = airfreight.airEnteringFlightBill.submenuGtasks.getSelectionModel().getSelection();
                var waybillNoList = '';
                var isQtyEqual = true;
                if (records.length != 0) {
                    for (var i = 0; i < records.length; i++) {
                        var lockStatus = records[i].get('lockStatus');
                        var waybillNo = records[i].get('waybillNo');
                        var productCode=records[i].get('transportType');
                        //判断是否锁票
                        if (lockStatus == 'Y') {
                            Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                                , '运单' + waybillNo + '未解锁，不允许下移，请先解锁');
                            return;
                        }
                        var goodsQty = records[i].get('stockQty');
                        var goodsQtyTotal = records[i].get('goodsQtyTotal');
                        //判断当前配载件数是否等于开单件数
                        if (goodsQty != goodsQtyTotal && productCode != 'PACKAGE_AIR') {
                            waybillNoList += waybillNo + ','
                            isQtyEqual = false;
                        }
	                    //判断提交合票信息中是否运输性质全都一致
		                var items=airfreight.airEnteringFlightBill.jointTicketInformation.store.data.items;
		                for(var j=0;j<items.length;j++){
		                
		                	if(items[j].data.transportType!=productCode){
		                		 Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
		                                 '运单【'+waybillNo+'】与【'+items[j].data.waybillNo+"】 运输类型不同！");
		                                 return;
		                	}
		                }

                    }
                    //处理waybillNoList显示格式
                    var len = waybillNoList.length;
                    waybillNoList = waybillNoList.substring(0, len - 1);

                
                    if (isQtyEqual) {
                        //绑定合票信息
                        airfreight.airEnteringFlightBill.jointTicketInformation.store.add(records);
                        airfreight.airEnteringFlightBill.queryForm.vtype(records);
                        //移除待选信息
                        airfreight.airEnteringFlightBill.submenuGtasks.store.remove(records);

                    } else {
                        Ext.Msg.confirm(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
                                '运单{ ' + waybillNoList + ' }开单件数与空运总调库存件数不一致，是否确定下移?',
                            function (btn) {
                                if (btn == 'yes') {
                                    //绑定合票信息
                                    airfreight.airEnteringFlightBill.jointTicketInformation.store.add(records);
                                    airfreight.airEnteringFlightBill.queryForm.vtype(records);
                                    //移除待选信息
                                    airfreight.airEnteringFlightBill.submenuGtasks.store.remove(records);
                                } else {
                                    return;
                                }
                            })
                    }


                } else {
                    Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
                        airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.optionWaitJointticket'));
                }
            }
        },
        {
            fieldLabel: '',
            readOnly: true,
            columnWidth: .15
        },
        {
            fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.waybillNo'),
            name: 'waybillNo',
            columnWidth: .25
        },
        {
            xtype: 'button',
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.fastpagedown'),
            disabled: !airfreight.airEnteringFlightBill.isPermission('airfreight/querySpeedinessButton'),
            hidden: !airfreight.airEnteringFlightBill.isPermission('airfreight/querySpeedinessButton'),
            handler: function () {
                var waybillNo = this.up('form').getForm().findField('waybillNo').getValue();
                var getSubmenuGtasks = airfreight.airEnteringFlightBill.submenuGtasks.store.data.items;
                var jointTicketInformation = airfreight.airEnteringFlightBill.jointTicketInformation.store.data.items;
                var inexistenced = false;
                var inexistenceh = false;
                var gtyIsEqual = true;//默认当前配载件数与开单总件数相等
                var index = 0;//记住快速移动的下标
                if (waybillNo.length == 0) {
                    Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                        , airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.ydhbnwk'));
                    return false;
                }
                if (getSubmenuGtasks.length <= 0) {
                    Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                        , airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.dxlbbczsj'));
                    return false;
                }
                if (jointTicketInformation.length != 0) {
                    for (var j = 0; j < jointTicketInformation.length; j++) {
                        if (waybillNo == jointTicketInformation[j].data.waybillNo) {
                            inexistenceh = true;
                        }
                    }
                }
                if (inexistenceh == true) {
                    Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                        , airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.hdxxlbbcz') + waybillNo + airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.ydhbnxy'));
                    return false;
                }
                for (var i = 0; i < getSubmenuGtasks.length; i++) {
                    //如果当前待选列表中存在此数据则遍历出相等的进行绑定
                    if (waybillNo == getSubmenuGtasks[i].data.waybillNo) {
                        inexistenced = true;
                        index = i;
                        var lockStatus = getSubmenuGtasks[i].data.lockStatus;
                        if (lockStatus == 'Y') {
                            Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                                , '该运单已经被锁定，无法快速下移，请先解锁');
                            return;
                        }
                        var goodsQty = getSubmenuGtasks[i].data.stockQty;
                        var goodsQtyTotal = getSubmenuGtasks[i].data.goodsQtyTotal;
                        var transportType = getSubmenuGtasks[i].data.transportType;
                        //判断当前配载件数是否等于开单件数
                        if (goodsQty != goodsQtyTotal && transportType != 'PACKAGE_AIR') {
                            gtyIsEqual = false;
                        }
                    }
                }
                if (inexistenced == false) {
                    Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                        , airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.dxlbbcz') + waybillNo + airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.bczdydh'));
                    return false;
                }
                //判断提交合票信息中是否运输性质全都一致
                var items=airfreight.airEnteringFlightBill.jointTicketInformation.store.data.items;
                for(var i=0;i<items.length;i++){
                
                	if(items[i].data.transportType!=getSubmenuGtasks[index].data.transportType){
                	
                		 Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
                                 '运单【'+getSubmenuGtasks[index].data.waybillNo+'】与【'+items[i].data.waybillNo+"】 运输类型不同！");
                                 return;
                	}
                }
                
                if (!gtyIsEqual) {
                    Ext.Msg.confirm(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
                            '运单' + waybillNo + '开单件数与空运总调库存件数不一致，是否确定快速下移?',
                        function (btn) {
                            if (btn == 'yes') {
                                airfreight.airEnteringFlightBill.jointTicketInformation.store.add(getSubmenuGtasks[index]);
                                airfreight.airEnteringFlightBill.submenuGtasks.store.remove(getSubmenuGtasks[index]);
                            } else {
                                return;
                            }
                        })

                } else {
                    airfreight.airEnteringFlightBill.jointTicketInformation.store.add(getSubmenuGtasks[index]);
                    airfreight.airEnteringFlightBill.submenuGtasks.store.remove(getSubmenuGtasks[index]);
                }

            }
        }
    ],
    constructor: function (config) {
        var me = this,
            cfg = Ext.apply({}, config);
        me.selModel = Ext.create('Ext.selection.CheckboxModel'),
            me.callParent([cfg]);
    }
});


Ext.define('Foss.airfreight.serialModel', {
    extend: 'Ext.data.Model',
    fields: [
        {
            name: 'serialNo',
            type: 'string'
        },
        {
            name: 'operatingStatus',
            type: 'string'
        },
        {
            name: 'goodsWeightTotal',
            type: 'number'
        },
        {
            name: 'goodsVolumeTotal',
            type: 'number'
        },
        {
            name: 'id',
            type: 'string'
        },
        {
            name: 'stockStatus',
            type: 'string'
        }
    ]
})


Ext.define('Foss.airfreight.serialStore', {
    extend: 'Ext.data.Store',
    model: 'Foss.airfreight.serialModel',
    proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            root: 'items'
        }
    }
});

//航空货量流水明细列表
Ext.define('Foss.airfreight.serialWindow', {
    extend: 'Ext.window.Window',
    width: 600,
    height: 380,
    title: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.airWaybillDetail'),
    showGrid: null,
    showForm: null,
    closeable: true,
    waybillNo: null,
    re: null,
    modal: true,
    closeAction: 'hide',
    layout: 'hbox',
    createGrid: function () {
        if (this.showGrid) {
            return this.showGrid;
        }
        var serialStore = Ext.create('Foss.airfreight.serialStore');
        this.showGrid = Ext.create('Ext.grid.Panel', {
            frame: true,
            border: true,
            autoScroll: true,
            width: 300,
            height: 300,
            store: serialStore,
            emptyText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.searchResultInexistence'),
            columns: [
                {
                    text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.serialNumber'),
                    dataIndex: 'serialNo',
                    flex: 1.5
                },
                {
                    text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.stockStatus'),
                    dataIndex: 'stockStatus',
                    flex: 1.5,
                    renderer: function (v, metadata, record, rowIndex, columnIndex, store) {
                        if (v == 'NOTSTORE') {
                            return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.storage');
                        } else {
                            return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.instore');
                        }
                    }
                },
                {
                    text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.operate'),
                    dataIndex: 'operatingStatus',
                    flex: 1.5,
                    renderer: function (v, metadata, record, rowIndex, columnIndex, store) {
                        if (rowIndex == 0) {
                            airfreight.airEnteringFlightBill.serialNoNumbers = store.data.items.length;
                        }
                        var id = record.data.id;
                        //获取改流水号上一次的操作
                        var addSytle = '';
                        var delSytle = '';
                        var add = airfreight.airEnteringFlightBill.sytleMap.get(id + record.data.serialNo + 'add');
                        var del = airfreight.airEnteringFlightBill.sytleMap.get(id + record.data.serialNo + 'del');
                        if (add != null) {
                            if (record.data.serialNo == add) {
                                addSytle = 'gray';
                                delSytle = 'blue';
                            }
                        } else {
                            if (record.data.serialNo == del) {
                                addSytle = 'blue';
                                delSytle = 'gray';
                            }
                        }
                        if (add != null || del != null) {
                            var formatStrAdd = '<a href="javascript:airfreight.airEnteringFlightBill.addSerialDetialNo(\'' + 'add' + record.data.serialNo + '\',\'' + record.data.id + '\');" style="color:' + addSytle + ';" id=add' + record.data.id + record.data.serialNo + ' name=add' + record.data.id + record.data.serialNo + '>' + airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.addTwo') + '</a>';
                            var formatStrDelete = '<a href="javascript:airfreight.airEnteringFlightBill.addSerialDetialNo(\'' + 'del' + record.data.serialNo + '\',\'' + record.data.id + '\');" style="color:' + delSytle + ';" id=del' + record.data.id + record.data.serialNo + ' name=del' + record.data.id + record.data.serialNo + '>' + airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.delete') + '</a>';
                        } else {

                            var formatStrAdd = '<a href="javascript:airfreight.airEnteringFlightBill.addSerialDetialNo(\'' + 'add' + record.data.serialNo + '\',\'' + record.data.id + '\');" style="color:gray;" id=add' + record.data.id + record.data.serialNo + ' name=add' + record.data.id + record.data.serialNo + '>' + airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.addTwo') + '</a>';
                            var formatStrDelete = '<a href="javascript:airfreight.airEnteringFlightBill.addSerialDetialNo(\'' + 'del' + record.data.serialNo + '\',\'' + record.data.id + '\');" style="color:blue;" id=del' + record.data.id + record.data.serialNo + ' name=del' + record.data.id + record.data.serialNo + '>' + airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.delete') + '</a>';
                        }
                        return formatStrAdd + formatStrDelete;
                    }
                }
            ]
        });
        return this.showGrid;
    },
    createForm: function () {
        if (this.showForm) {
            return this.showForm;
        }
        this.showForm = Ext.create('Ext.form.Panel', {
            frame: false,
            border: false,
            width: 300,
            layout: 'column',
            columns: 1,
            defaults: {
                margin: '10 5 5 50'
            },
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.grossWeight'),
                    name: 'goodsWeightTotal',
                    vtype: 'goodsWeightTotal',
                    labelWidth: 60,
                    width: 200
                },
                {
                    xtype: 'textfield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.volume'),
                    name: 'goodsVolumeTotal',
                    vtype: 'goodsVolumeTotal',
                    labelWidth: 60,
                    width: 200
                },
                {
                    xtype: 'textfield',
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.billingWeight'),
                    name: 'goodsBillingWeightTotal',
                    vtype: 'goodsWeightTotal',
                    hidden: true,
                    labelWidth: 60,
                    width: 200
                },
                {
                    xtype: 'container',
                    margin: '10 5 5 60'
                },
                {
                    xtype: 'button',
                    text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.ensure'),
                    labelWidth: 20,
                    width: 50,
                    handler: function () {
                        var formValue = this.up('form').getForm();
                        var validateWeight = function (value) {
                            var str = value + '';
                            if (str == 0)
                                return false;
                            if (str.substring(0, 1) == '-')
                                return false;
                            if (str.substring(0, 2) == '00')
                                return false;
                            if (str.indexOf("..") == 1)
                                return false;
                            var isNotNumber = str.substring(str.length - 1, str.length);
                            if (!/^[0-9]+$/.test(isNotNumber))
                                return false;
                            return /^-?\d+\.?\d{0,1}$/.test(Ext.Number.from(value, 0));
                        }
                        var validateVolume = function (value) {
                            var str = value + '';
                            if (str == 0)
                                return false;
                            if (str.substring(0, 1) == '-')
                                return false;
                            if (str.substring(0, 2) == '00')
                                return false;
                            if (str.indexOf("..") == 1)
                                return false;
                            var isNotNumber = str.substring(str.length - 1, str.length);
                            if (!/^[0-9]+$/.test(isNotNumber))
                                return false;
                            return /^-?\d+\.?\d{0,2}$/.test(Ext.Number.from(value, 0));
                        }
                        var delMap = airfreight.airEnteringFlightBill.delMap;
                        var arry = airfreight.airEnteringFlightBill.updateWeightVolumeMap.get(formValue.findField('id').getValue());
                        arry.data.grossWeight = formValue.findField('goodsWeightTotal').getValue();
                        arry.data.volume = formValue.findField('goodsVolumeTotal').getValue();
                        arry.data.billingWeight = formValue.findField('goodsBillingWeightTotal').getValue();
                        var totalNumbers = 0;
                        if (delMap.get(arry.data.waybillNo)) {
                            var map = delMap.get(arry.data.waybillNo);
                            totalNumbers = map.length;
                        }
                        var n = airfreight.airEnteringFlightBill.findForm.getValues();
                        if(n.transportType != 'PACKAGE_AIR'){
                        	arry.data.goodsQty = airfreight.airEnteringFlightBill.serialNoNumbers - totalNumbers;
                        }
                        if (!validateWeight(arry.data.grossWeight)) {
                            Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                                , airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.grossWeight'));
                            return false;
                        }
                        if (!validateVolume(arry.data.volume)) {
                            Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                                , airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.volum'));
                            return false;
                        }
                        airfreight.airEnteringFlightBill.jointTicketInformation.store.update(arry);

                        Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                            , airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.updateSuccess'));
                        airfreight.airEnteringFlightBill.serialWindow.close();
                    }
                },
                {
                    xtype: 'textfield',
                    hideLabel: true,
                    name: 'id',
                    hidden: true
                }
            ]
        });
        return this.showForm;
    },
    constructor: function (config) {
        var me = this,
            cfg = Ext.apply({}, config);
        me.callParent([cfg]);
        airfreight.airEnteringFlightBill.modifyVtypesTotal = Ext.apply(Ext.form.field.VTypes, {
            goodsWeightTotal: function (val, field) {
                var str = val + '';
                if (str == 0)
                    return false;
                if (str.substring(0, 1) == '-')
                    return false;
                if (str.substring(0, 2) == '00')
                    return false;
                if (str.indexOf("..") == 1)
                    return false;
                var isNotNumber = str.substring(str.length - 1, str.length);
                if (!/^[0-9]+$/.test(isNotNumber))
                    return false;
                return /^-?\d+\.?\d{0,1}$/.test(Ext.Number.from(val, 0));

            },
            goodsVolumeTotal: function (val, field) {
                var str = val + '';
                if (str == 0)
                    return false;
                if (str.substring(0, 1) == '-')
                    return false;
                if (str.substring(0, 2) == '00')
                    return false;
                if (str.indexOf("..") == 1)
                    return false;
                var isNotNumber = str.substring(str.length - 1, str.length);
                if (!/^[0-9]+$/.test(isNotNumber))
                    return false;
                return /^-?\d+\.?\d{0,2}$/.test(Ext.Number.from(val, 0));
            },
            goodsWeightTotalText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.weight'),
            goodsVolumeTotalText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.checkoutMessage.volum')
        });
        me.createGrid();
        me.createForm();
        me.add([me.showGrid, me.showForm]);

    }
});
//合票信息
Ext.define('Foss.airfreight.JointTicketInformation', {
    extend: 'Ext.grid.Panel',
    title: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.houseMessage'),
    layout: 'column',
    frame: true,
    border: true,
    autoScroll: true,
    height: 280,
    emptyText: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.searchResultInexistence'),
    defaults: {
        sortable: true,
        flex: 1
    },
    viewConfig: {
        stripeRows: false,
        getRowClass: function (record, rowIndex, rp, ds) {
            var makeWaybillWay = record.get('makeWaybillWay');
            if (makeWaybillWay == 'DDKD') {
                return 'x-grid-record-yellow';
            }
            var stockStatus = record.get('stockStatus');
            if (stockStatus == 'NOTSTORE') {
                return 'x-grid-record-gray';
            }
        }
    },
    columns: [
        {
            xtype: 'actioncolumn',
            width: 60,
            items: [
                {
                    iconCls: 'deppon_icons_edit',
                    handler: function (grid, rowIndex, colIndex) {
                        var record = grid.getStore().getAt(rowIndex);
                        var waybillNo = record.data.waybillNo;
                        var stockStatus = record.data.stockStatus;
                        var params = {
                            'pointsSingleJointTicketVO.ticketDto.stockStatus': stockStatus,
                            'pointsSingleJointTicketVO.ticketDto.waybillNo': record.data.waybillNo
                        };
                        Ext.Ajax.request({
                            url: airfreight.realPath('queryWaybillNo.action'),
                            params: params,
                            success: function (response) {
                                var result = Ext.decode(response.responseText);
                                //绑定运单明细数据
                                if (!airfreight.airEnteringFlightBill.createForm) {
                                    var serialWindow = Ext.create('Foss.airfreight.serialWindow', {'waybillNo': record.data.waybillNo});
                                    airfreight.airEnteringFlightBill.serialWindow = serialWindow;
                                }
                                airfreight.airEnteringFlightBill.serialWindow.show();
                                var modifyForm = airfreight.airEnteringFlightBill.serialWindow.createForm().getForm();
                                airfreight.airEnteringFlightBill.serialnoDetialWind = airfreight.airEnteringFlightBill.serialWindow;
                                //运单流水明细list
                                var serailNoRecordList = result.pointsSingleJointTicketVO.airSerialNoDetailList;
                                //根据该运单号查询出来的流水号=页面显示流水号
                                var serailNoList = new Array();
                                for (var j = 0; j < serailNoRecordList.length; j++) {
                                    serailNoList.push(serailNoRecordList[j].serialNo);
                                }
                                airfreight.airEnteringFlightBill.serialNoListMap.add(waybillNo, serailNoList);
                                //运单流水明细总件数
                                var serailNoNumber = serailNoRecordList.length;
                                var totalCountMap = airfreight.airEnteringFlightBill.totalCountMap;
                                if (!totalCountMap.get(waybillNo)) {
                                    totalCountMap.add(waybillNo, serailNoNumber);
                                    airfreight.airEnteringFlightBill.totalCountMap = totalCountMap;
                                    ;
                                }

                                //将原始毛重和提交保存在全部变量中方便计算毛重合体积
                                var originalWeightMap = airfreight.airEnteringFlightBill.originalWeightMap;
                                var originalVolumeMap = airfreight.airEnteringFlightBill.originalVolumeMap;
                                if (!originalWeightMap.get(waybillNo) && !originalVolumeMap.get(waybillNo)) {
                                    originalWeightMap.add(waybillNo, record.data.grossWeight);
                                    originalVolumeMap.add(waybillNo, record.data.volume);
                                    airfreight.airEnteringFlightBill.originalWeightMap = originalWeightMap;
                                    airfreight.airEnteringFlightBill.originalVolumeMap = originalVolumeMap;
                                }
                                //运单总毛重
                                var goodsWeightTotal = originalWeightMap.get(waybillNo);
                                //运单总体积
                                var goodsVolumeTotal = originalVolumeMap.get(waybillNo);
                                var total = serailNoNumber;
                                var serial = airfreight.airEnteringFlightBill.totalCountMap.get(waybillNo);
                                
                                //判断是否快递空运
                                if(record.data.transportType != 'PACKAGE_AIR'){
                                	//重量
                                    var goodsWeight = (serial / total) * goodsWeightTotal;
                                    //体积
                                    var goodsVolume = (serial / total) * goodsVolumeTotal;
                                }else{
                                	//重量
                                    var goodsWeight =  goodsWeightTotal;
                                    //体积
                                    var goodsVolume =  goodsVolumeTotal;
                                }
                                
                                //运单总件数
                                var goodsQtyTotal = Ext.Number.from(record.data.goodsQtyTotal, 0);
                                var serialNoList = airfreight.airEnteringFlightBill.delMap.get(record.data.waybillNo);
                                modifyForm.findField('goodsWeightTotal').setValue(airfreight.airEnteringFlightBill.fomatFloat(goodsWeight, 1));
                                modifyForm.findField('goodsVolumeTotal').setValue(airfreight.airEnteringFlightBill.fomatFloat(goodsVolume, 2));
                                modifyForm.findField('goodsBillingWeightTotal').setValue(airfreight.airEnteringFlightBill.fomatFloat(record.data.billingWeight, 1));
                                airfreight.airEnteringFlightBill.serialWindow.createForm().loadRecord(modifyForm);
                                if (airfreight.airEnteringFlightBill.map.get(waybillNo) != null) {
                                    var currentForm = airfreight.airEnteringFlightBill.modifyGoodsWeightVolume();
                                    airfreight.airEnteringFlightBill.serialWindow.createForm().loadRecord(currentForm);
                                }
                                airfreight.airEnteringFlightBill.serialWindow.showGrid.store.loadData(serailNoRecordList);
                                //将运单明细中的 毛重体积绑定到临时变量上方便动态计算
                                airfreight.airEnteringFlightBill.serialWindow.showGrid.goodsWeightTotal = goodsWeightTotal;
                                airfreight.airEnteringFlightBill.serialWindow.showGrid.goodsVolumeTotal = goodsVolumeTotal;
                                airfreight.airEnteringFlightBill.serialWindow.showGrid.goodsQtyTotal = goodsQtyTotal;
                                //全局变量(运单明细总毛重合体积件数)
                                airfreight.airEnteringFlightBill.goodsWeightTotal = goodsWeightTotal;
                                airfreight.airEnteringFlightBill.goodsVolumeTotal = goodsVolumeTotal;
                                airfreight.airEnteringFlightBill.serialWindow.createForm().getForm().findField('id').setValue(record.data.id);
                                //保存当前record对象
                                var updateWeightVolumeMap = airfreight.airEnteringFlightBill.updateWeightVolumeMap;
                                updateWeightVolumeMap.add(record.data.id, record);
                                airfreight.airEnteringFlightBill.updateWeightVolumeMap = updateWeightVolumeMap;
                            },
                            exception: function (response) {
                                var result = Ext.decode(response.responseText);
                            }
                        });
                    }
                },
                {
                    tooltip: '查看尺寸及储运事项',
                    iconCls: 'deppon_icons_showdetail',
                    handler: function (grid, rowIndex, colIndex, item, e) {
                        var waybillNo = grid.store.getAt(rowIndex).get('waybillNo');
                        airfreight.airEnteringFlightBill.detailWaybillNo = waybillNo;
                        var x = e.getX();
                        var y = e.getY();
                        var n = airfreight.airEnteringFlightBill.findForm.getValues();
                        if(n.transportType != 'PACKAGE_AIR'){
	                        if (airfreight.airEnteringFlightBill.waybillDetailWindow == null) {
	                            airfreight.airEnteringFlightBill.waybillDetailWindow = Ext.create('Foss.airfreight.airEnteringFlightBill.waybillDetailWindow');
	                        }
	                        airfreight.airEnteringFlightBill.waybillDetailWindow.showAt(x + 10, y + 10);
                        }
                    }
                }
            ],
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.operate'),
            width: 60,
            dataIndex: 'id'
        },
        {
            xtype: 'ellipsiscolumn',
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.waybillNo'),
            dataIndex: 'waybillNo',
            flex: 0.9
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.flightNumberType'),
            dataIndex: 'planFlightNo',
            flex: 0.8,
             renderer:function(value){
				//航班类型
				if(value.trim() == ''){
					return null;
				}else{
					return FossDataDictionary.rendererSubmitToDisplay(value,'AIR_FLIGHT_TYPE');
				}
			}
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.makeWaybillWay'),
            dataIndex: 'makeWaybillWay',
            flex: 0.8,
            renderer: function (v) {
                if (v == 'HDP') {
                    return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.hdp');
                }else if(v == ' '){
                	return null;
                } else {
                    return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.ddkd');
                }
            }
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.arrvRegionName'),
            dataIndex: 'arrvRegionName',
            flex: 0.7
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.weight'),
            dataIndex: 'grossWeight',
            flex: 0.8
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.billingWeight'),
            dataIndex: 'billingWeight',
            flex: 0.8
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.volume'),
            dataIndex: 'volume',
            flex: 0.7
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.goodsQty'),
            dataIndex: 'goodsQty',
            flex: 0.7
        },
        {
            xtype: 'ellipsiscolumn',
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.goodsName'),
            dataIndex: 'goodsName',
            flex: 1.2
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.stockStatus'),
            dataIndex: 'stockStatus',
            flex: 0.8,
            renderer: function (value) {
                if (value == 'NOTSTORE') {//未入库
                    return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.storage');
                } else if (value == 'INSTORE') { //库存中
                    return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.instore');
                }
            }
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.departTime'),
            flex: 1.5,
            format: 'Y-m-d H:i:s',
            xtype: 'datecolumn',
            dataIndex: 'departTime'//外场交单出发时间
        },
        {
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.unit_price'),
            flex: 0.7,
            dataIndex: 'unitPrice'//运单费率
        },
        {
            xtype: 'ellipsiscolumn',
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.receiveOrgName'),
            dataIndex: 'receiveOrgName',
            flex: 1.5
        },
        { //运输性质
            text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.tranportType'),
            dataIndex: 'transportType',
            renderer: function (value) {
                if (value == 'PACKAGE_AIR') {
                  return  airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.packageAir');
                } else {
                   return airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.precisionAir');
                }
            },
            sortable: false
        }
    ],
    dockedItems: [
        {
            xtype: 'toolbar',
            dock: 'bottom',
            layout: 'column',
            defaults: {
                readOnly: true,
                labelWidth: 50,
                xtype: 'textfield',
                width: 30
            },
            items: [
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.poll'),
                    dataIndex: 'billNoTotal',
                    columnWidth: .10
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.goodsQty'),
                    dataIndex: 'goodsQtyTotal',
                    columnWidth: .10
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.grossWeight'),
                    dataIndex: 'weightTotal',
                    hideValue: '',
                    columnWidth: .12
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.billingWeight'),
                    labelWidth: 70,
                    dataIndex: 'billingWeightTotal',
                    hideValue: '',
                    columnWidth: .15
                },
                {
                    fieldLabel: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.volume'),
                    dataIndex: 'volumeTotal',
                    hideValue: '',
                    columnWidth: .15
                },
                {
                    xtype: 'container',
                    html: '&nbsp;',
                    columnWidth: .30
                },
                {
                    xtype: 'button',
                    text: airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.submitJointticket'),
                    columnWidth: .08,
                    handler: function () {
                        record = airfreight.airEnteringFlightBill.jointTicketInformation.getSelectionModel().getSelection();
                        if (record.length == 0) {
                            Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                                , airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.optionJointticket'));
                            return false;
                        }
                       
                        var count = 1;
                        var arrvRegionName = ''; //目的站
                        var isConsistent = false;
                        var isConsistentMethod = false;
                        var freightMethod = airfreight.airEnteringFlightBill.flightinformationForm.getForm().findField('airAssembleType').value;
                        //当组装类型为单独开单或者是单独开单外发时只能选择一条运单信息
                        if (freightMethod == 'DDKD' || freightMethod == 'DDWFD') {
                            if (record.length > 1) {
                                Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                                    , airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.selectOneRecord'));
                                return false;
                            }
                        }
                        var serialNoListMap = airfreight.airEnteringFlightBill.serialNoListMap;
                        for (var i = 0; i < record.length; i++) {
                            var wayBillNo = record[i].data.waybillNo;
                            //如果运单号对应的流水明细列表为空，则从数据库查询出来赋值给全局变量，保存时提交到后台用于判断
                            if (serialNoListMap.get(wayBillNo) == null) {
                                var stockStatus = record[i].data.stockStatus;
                                var params = {
                                    'pointsSingleJointTicketVO.ticketDto.stockStatus': stockStatus,
                                    'pointsSingleJointTicketVO.ticketDto.waybillNo': wayBillNo
                                };
                                Ext.Ajax.request({
                                    url: airfreight.realPath('queryWaybillNo.action'),
                                    params: params,
                                    success: function (response) {
                                        var result = Ext.decode(response.responseText);
                                        //运单流水明细list
                                        var serailNoRecordList = result.pointsSingleJointTicketVO.airSerialNoDetailList;
                                        var serailNoList = new Array();
                                        for (var j = 0; j < serailNoRecordList.length; j++) {
                                            serailNoList.push(serailNoRecordList[j].serialNo);
                                        }
                                        //根据该运单号查询出来的流水号=页面显示流水号
                                        serialNoListMap.add(wayBillNo, serailNoList);
                                    },
                                    exception: function (response) {
                                        var result = Ext.decode(response.responseText);
                                    }
                                });
                            }

                            if (freightMethod == 'HDP' || freightMethod == 'HDPWF') {
                                if (record[i].data.freightMethod == 'DDKD') {
                                    isConsistentMethod = true;
                                }
                            }
                            var gridStore = airfreight.airEnteringFlightBill.showGrid.store.data.items;
                            if (count == 1) {
                                arrvRegionName = record[i].data.arrvRegionName;
                            } else {
                                if (arrvRegionName != record[i].data.arrvRegionName) {
                                    isConsistent = true;
                                }
                            }
                            count++;
                            if(gridStore.length > 0 && gridStore[0].data.arrvRegionName != record[i].data.arrvRegionName){
                            	isConsistent = true;
                            }
                        }
                        airfreight.airEnteringFlightBill.serialNoListMap = serialNoListMap;
                        var message1 = '';
                        var message2 = '';
                        if (isConsistentMethod) {
                            message1 = airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.includeDdkd');
                        }
                        if (isConsistent) {
                            if (isConsistentMethod) {


                                message2 = airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.differentDestinationStation');
                            } else {
                                message2 = airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.differentDestWaybillNo');
                            }
                        }

                        if (isConsistentMethod || isConsistent) {
                            Ext.MessageBox.buttonText.yes = airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.yes');
                            Ext.MessageBox.buttonText.no = airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.no');
                            Ext.Msg.confirm(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'), message1 + message2 + airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.isNotHdp'), function (btn, text) {
                                if (btn == 'yes') {
                                    airfreight.airEnteringFlightBill.SubmitJointTicket(record, 'submit');
                                } else {
                                    return false;
                                }
                            });
                        } else {
                            airfreight.airEnteringFlightBill.SubmitJointTicket(record, 'submit');
                        }
                        airfreight.airEnteringFlightBill.airWaybillTranportType = record[0].get('transportType');
                    }
                }
            ]
        }
    ],
    constructor: function (config) {
        var me = this,
            cfg = Ext.apply({}, config);
        me.selModel = Ext.create('Ext.selection.CheckboxModel'),
            me.store = Ext.create('Foss.airfreight.beassembleStore'),
            me.callParent([cfg]);
        me.addListener({ 'itemdblclick': me.itemdblclick, scope: this});
    },
    itemdblclick: function (view, record, item, index, e, eOpts) {
        airfreight.airEnteringFlightBill.jointTicketInformation.store.remove(record);
        airfreight.airEnteringFlightBill.submenuGtasks.store.add(record);
    }
});

//渲染录入航空正单界面
Ext.onReady(function () {
    Ext.QuickTips.init();
    //创建航空正单基本信息表单
    var flightinformationForm = Ext.create('Foss.airfreight.flightinformationForm');
    //运单明细收缩展开样式表单
    var submitForm = Ext.create('Foss.airfreight.submitForm');
    //运单明细列表，初始隐藏
    var showGrid = Ext.create('Foss.airfreight.showGrid', {hidden: true});
    //
    airfreight.airEnteringFlightBill.record = Ext.create('Foss.airfreight.Model');
    //原始重量map
    airfreight.airEnteringFlightBill.originalWeightMap = new Ext.util.HashMap();
    //原始体积map
    airfreight.airEnteringFlightBill.originalVolumeMap = new Ext.util.HashMap();
    //更新后重量map
    var updateWeightVolumeMap = new Ext.util.HashMap();
    //更新后体积map
    var modifyWeightVolumnMap = new Ext.util.HashMap();
    //总数量map
    airfreight.airEnteringFlightBill.totalCountMap = new Ext.util.HashMap();
    //流水号map
    var serialNoListMap = new Ext.util.HashMap();
    var sytleMap = new Ext.util.HashMap();
    var delMap = new Ext.util.HashMap();
    var map = new Ext.util.HashMap();
    airfreight.airEnteringFlightBill.airWaybillTranportType = null;
    //设置全局变量
    airfreight.airEnteringFlightBill.updateWeightVolumeMap = updateWeightVolumeMap;
    airfreight.airEnteringFlightBill.modifyWeightVolumnMap = modifyWeightVolumnMap;
    airfreight.airEnteringFlightBill.airlinesValueAddEntity = null;
    airfreight.airEnteringFlightBill.serialNoListMap = serialNoListMap;
    airfreight.airEnteringFlightBill.sytleMap = sytleMap;
    airfreight.airEnteringFlightBill.delMap = delMap;
    airfreight.airEnteringFlightBill.map = map;
    //设置最低默认运价
    airfreight.airEnteringFlightBill.priceRate = 0;
    //设置全局变量
    airfreight.airEnteringFlightBill.flightinformationForm = flightinformationForm;
    airfreight.airEnteringFlightBill.submitForm = submitForm;
    airfreight.airEnteringFlightBill.showGrid = showGrid;
    //主页面框架
    Ext.create('Ext.panel.Panel', {
        id: 'T_airfreight-airEnteringFlightBill_content',
        cls: "panelContentNToolbar",
        bodyCls: 'panelContent-body',
        items: [flightinformationForm, submitForm, showGrid],
        renderTo: 'T_airfreight-airEnteringFlightBill-body'
    });
    Ext.Ajax.request({
        //设置后台返回的合票号
        async: false,
        url: airfreight.realPath('queryBaseData.action'),
        success: function (response) {
            var result = Ext.decode(response.responseText);
            //获取费用说明 2013-05-23 费用说明修改为根据基础费率表配置的燃油附加费 实时拼接出，例如：燃油附加费：0.2/Kg
//			if(result.pointsSingleJointTicketVO.configurationParamsList.length!=0){
//				var confName = result.pointsSingleJointTicketVO.configurationParamsList[0].confName;
//				//设置费用说明
//				airfreight.airEnteringFlightBill.record.data.feePlain = confName;
//			}
            var deptRegionAirportList = result.pointsSingleJointTicketVO.deptRegionAirportList;
            var airportListStr = '';
            for (var i = 0; i < deptRegionAirportList.length; i++) {
                if (i == 0) {
                    airportListStr = airportListStr + deptRegionAirportList[i].airportCode;
                } else {
                    airportListStr = airportListStr + ',' + deptRegionAirportList[i].airportCode
                }
            }
            flightinformationForm.getForm().findField('flightNo').airLines = 'CA';	//航班号，CA
            flightinformationForm.getForm().findField('flightNo').origCodes = airportListStr;
            flightinformationForm.getForm().findField('flightNo').store.load();		//航班号加载一次
            var initData = result.pointsSingleJointTicketVO.ticketDto;
            //设置正单号
            airfreight.airEnteringFlightBill.record.data.jointTicketNo = initData.jointTicketNo;
            //设置制单人
            airfreight.airEnteringFlightBill.record.data.createUserName = initData.createUserName;
            //设置制单部门
            airfreight.airEnteringFlightBill.record.data.createOrgCode = initData.orgCode;
            airfreight.airEnteringFlightBill.record.data.createOrgName = initData.orgName;
			//设置制单部门电话zwd
			airfreight.airEnteringFlightBill.record.data.shipperContactPhone = initData.shipperContactPhone;
        },
        exception: function (response) {
            var result = Ext.decode(response.responseText);
            //提示信息
            Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'), result.message);
        }
    });
    //给表单设置初始化值
    flightinformationForm.loadRecord(airfreight.airEnteringFlightBill.record);
    var currentDept = FossUserContext.getCurrentDept(); //当前部门
    var cityCode = currentDept.cityCode;  //城市编码
    var cityName = currentDept.cityName;  //城市名称
    var record = airfreight.airEnteringFlightBill.flightinformationForm.getRecord();
    //重置
    var flightinformationFormReset = flightinformationForm.getForm();
    flightinformationFormReset.findField('airLineTwoletter').reset();
    flightinformationFormReset.findField('arrvRegionName').reset();
    flightinformationFormReset.findField('airAssembleType').reset();
    flightinformationFormReset.findField('dedtOrgName').reset();
    flightinformationFormReset.findField('flightNo').reset();
    flightinformationFormReset.findField('rateClass').reset();
    flightinformationFormReset.findField('paymentType').reset();
    flightinformationFormReset.findField('arriveTime').reset();
    flightinformationFormReset.findField('takeOffTime').reset();
    record.data.deptRegionCode = cityCode;
    flightinformationFormReset.findField('deptRegionName').setValue(cityName);
    flightinformationFormReset.findField('storageItem').setValue(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.onlyczFilght')); //仅限中国国际航空公司承运
    flightinformationFormReset.findField('arrvRegionName').orgCode = cityCode;
    //设置航空公司为CA
    flightinformationFormReset.findField('airLineTwoletter').setCombValue('CA', 'CA');
    airfreight.airEnteringFlightBill.record.data.agencyName = airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.company'); //中国国际航空公司
    flightinformationFormReset.findField('rateClass').setValue('C_TYPE');  //C类
    flightinformationFormReset.findField('paymentType').setValue('CHECK'); //支票
    airfreight.airEnteringFlightBill.flightinformationForm.queryAirlinesAgentCode('CA');		  //查询航空公司二字码
    flightinformationFormReset.findField('flightNo').airLines = 'CA';	//航班号，CA
    flightinformationFormReset.findField('flightNo').store.load();		//航班号加载一次
    //过滤航空公司代理人信息
    flightinformationFormReset.findField('agencyName_wf').assemblyDeptId = airfreight.airEnteringFlightBill.record.data.createOrgCode;
    flightinformationFormReset.findField('agencyName_wf').airlinesCode = 'CA';
    flightinformationFormReset.findField('agencyName_wf').store.load();

});

//获取最低运价
airfreight.airEnteringFlightBill.requestRate = function (record) {
    var filghtInfo = Ext.create('Foss.airfreight.airRateQueryModel', record.data);  //创建时添加record.data参数
    filghtInfo.data.targetCode = airfreight.airEnteringFlightBill.flightinformationForm.getForm().findField('dedtOrgName').destCityCode;  //目的地code
    var flightinformationFormGetValue = airfreight.airEnteringFlightBill.flightinformationForm.getForm(); //获取正单的表单
    var airLineTwoletter = flightinformationFormGetValue.findField('airLineTwoletter').getValue();//航空公司
    var billingWeight = Ext.Number.from(flightinformationFormGetValue.findField('billingWeight').getValue(), 0);//计费重量
    var flightNo = flightinformationFormGetValue.findField('flightNo').getValue(); //航班号
    var queryRate = {pointsSingleJointTicketVO: {flightNo: flightNo, flightEntity: filghtInfo.data,
        airLineTwoletter: airLineTwoletter, weight: billingWeight}};  //查询参数
    Ext.Ajax.request({
        async: false,
        url: airfreight.realPath('queryFlightMinPriceRate.action'),
        jsonData: queryRate,
        success: function (response) {
            var result = Ext.decode(response.responseText);
            //基础运价
            airfreight.airEnteringFlightBill.priceRate = result.pointsSingleJointTicketVO.priceRate;
            //最低运价
            airfreight.airEnteringFlightBill.minPriceRate = result.pointsSingleJointTicketVO.minPriceRate;
        },
        exception: function (response) {
            var result = Ext.decode(response.responseText);
            Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'), result.message);
            return false;

        }
    });
    return airfreight.airEnteringFlightBill.priceRate;
}