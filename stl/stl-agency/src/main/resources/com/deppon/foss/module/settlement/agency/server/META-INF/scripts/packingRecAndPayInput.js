// 破损率参数 读取系统配置
agency.packingRecAndPayInput.STANDARD_DAMAGE_RATE = -1;
agency.packingRecAndPayInput.STL_PACKING_DAMAGE_MAX_AMOUNT = -1;
agency.packingRecAndPayInput.form = {};
agency.packingRecAndPayInput.global_deptCode = stl.currentDept.code;

function forI18n() {
    agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.label.damageRate');
    agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.label.damageClaim');
    agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.label.forkliftTicket');
    agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.label.aging');
    agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.label.mixing');
    agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.label.losing');
    agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.label.complaint');
    agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.label.battenCheck');
    agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.label.board');
    agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.label.woodenStand');
    agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.label.tray');
    agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.label.packingTray');
    agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.label.recOther');
    agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.label.inconsistentWaybill');
}

agency.packingRecAndPayInput.buildParams = function (params) {
    if (params == null) {
        Ext.Msg.alert(agency.packingRecAndPayInput.i18n('foss.stl.agency.queryagencysystemreport.warmlyReminder'), agency.packingRecAndPayInput
            .i18n('foss.stl.agency.packingRecAndPayInput.validate.paramsIsNull'));
        return false;
    }

    if (params['packingRecAndPayInputVo.packingRecAndPayInputDto.customerCode'] == null
        || params['packingRecAndPayInputVo.packingRecAndPayInputDto.customerCode'] == '') {
        Ext.Msg.alert(agency.packingRecAndPayInput.i18n('foss.stl.agency.queryagencysystemreport.warmlyReminder'), agency.packingRecAndPayInput
            .i18n('foss.stl.agency.packingRecAndPayInput.validate.custCodeIsNull'));
        return false;
    }
    if (params['packingRecAndPayInputVo.packingRecAndPayInputDto.deptCode'] == null
        || params['packingRecAndPayInputVo.packingRecAndPayInputDto.deptCode'] == '') {
        Ext.Msg.alert(agency.packingRecAndPayInput.i18n('foss.stl.agency.queryagencysystemreport.warmlyReminder'), agency.packingRecAndPayInput
            .i18n('foss.stl.agency.packingRecAndPayInput.validate.deptCodeIsNull'));
        return false;
    }

    // 提交时 设置当前部门编码
    if (params['packingRecAndPayInputVo.packingRecAndPayInputDto.deptCode'] == stl.currentDept.name) {
        Ext.apply(params, {
            'packingRecAndPayInputVo.packingRecAndPayInputDto.deptCode': stl.currentDept.code
        });
    }
    if (params['packingRecAndPayInputVo.packingRecAndPayInputDto.amount'] == null
        || params['packingRecAndPayInputVo.packingRecAndPayInputDto.amount'] == '') {
        Ext.Msg.alert(agency.packingRecAndPayInput.i18n('foss.stl.agency.queryagencysystemreport.warmlyReminder'), agency.packingRecAndPayInput
            .i18n('foss.stl.agency.packingRecAndPayInput.validate.amountIsNull'));
        return false;
    }
    if(!params['packingRecAndPayInputVo.packingRecAndPayInputDto.inputType'] == 'damageRate'){
        if( params['inputAmount'] == null){
            Ext.Msg.alert(agency.packingRecAndPayInput.i18n('foss.stl.agency.queryagencysystemreport.warmlyReminder'), agency.packingRecAndPayInput
                .i18n('foss.stl.agency.packingRecAndPayInput.validate.amountIsNull'));
        }
    }
    if (params['packingRecAndPayInputVo.packingRecAndPayInputDto.amount'] == 0) {
        Ext.Msg.alert(agency.packingRecAndPayInput.i18n('foss.stl.agency.queryagencysystemreport.warmlyReminder'), agency.packingRecAndPayInput
            .i18n('foss.stl.agency.packingRecAndPayInput.validate.amountIsZero'));
        return false;
    }
    if (params['packingRecAndPayInputVo.packingRecAndPayInputDto.billTime'] == null
        || params['packingRecAndPayInputVo.packingRecAndPayInputDto.billTime'] == '') {
        Ext.Msg.alert(agency.packingRecAndPayInput.i18n('foss.stl.agency.queryagencysystemreport.warmlyReminder'), agency.packingRecAndPayInput
            .i18n('foss.stl.agency.packingRecAndPayInput.validate.billTimeIsNull'));
        return false;
    }

    Ext.apply(params, {
        'packingRecAndPayInputVo.packingRecAndPayInputDto.damageRate': params['packingRecAndPayInputVo.packingRecAndPayInputDto.damageRate']
            * 0.01
    });
    return params;
};

agency.packingRecAndPayInput.queryStandardDamageRate = function () {
    var form = agency.packingRecAndPayInput.form.getForm();
    var deptCode = form.findField('packingRecAndPayInputVo.packingRecAndPayInputDto.deptCode').getValue();
    deptCode = deptCode == stl.currentDept.name ? stl.currentDept.code : deptCode;
    var customerCode = form.findField('packingRecAndPayInputVo.packingRecAndPayInputDto.customerCode').getValue();
    if (!!deptCode && !!customerCode) {
        Ext.Ajax.request({
            url: agency.realPath('standardDamageRate.action'),
            params: {
                'packingRecAndPayInputVo.deptCode': deptCode,
                'packingRecAndPayInputVo.customerCode': customerCode
            },
            callback: function (options, success, response) {
                if (success) {
                    var data = Ext.decode(response.responseText);
                    if (data.success) {
                        agency.packingRecAndPayInput.STANDARD_DAMAGE_RATE = data.packingRecAndPayInputVo.standardDamageRate;
                    } else {
                        Ext.Msg.alert(agency.packingRecAndPayInput.i18n('foss.stl.agency.queryagencysystemreport.warmlyReminder'),
                            data.message);
                        return false;
                    }
                } else {
                    Ext.Msg.alert(agency.packingRecAndPayInput.i18n('foss.stl.agency.queryagencysystemreport.warmlyReminder'), Ext
                        .decode(response.responseText).message);
                    return false;
                }
            }
        });
    }
};

Ext.define('Foss.agency.packingRecAndPayInput.form', {
    extend: 'Ext.form.Panel',
    bodyPadding: 5,
    cls: 'panelContentNToolbar',
    header: false,
    defaultType: 'numberfield',
    layout: 'column',
    defaults: {
        columnWidth: .3,
        padding: 5,
        labelWidth: 120
    },
    initComponent: function () {
        var me = this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'dynamicorgcombselector',
                    name: 'packingRecAndPayInputVo.packingRecAndPayInputDto.deptCode',
                    fieldLabel: agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.label.department'),
                    allowBlank: false,
                    listWidth: 300,// 设置下拉框宽度
                    isPaging: true,
                    value: stl.currentDept.name,
                    listeners: {
                        select: function (me, newValue, oldValue, eOpts) {
                            var form = me.up('form').getForm();
                            form.findField('packingRecAndPayInputVo.packingRecAndPayInputDto.damageRate').reset();
                            form.findField('packingRecAndPayInputVo.packingRecAndPayInputDto.customerCode').reset();
                            agency.packingRecAndPayInput.global_deptCode = me.getValue() == stl.currentDept.name ? stl.currentDept.code : me.getValue();
                            var customerSelector = form.findField('packingRecAndPayInputVo.packingRecAndPayInputDto.customerCode');
                            var store = customerSelector.getStore();
                            store.load({
                                scope: this,
                                callback: function (records, operation, success) {
                                    if (records.length == 1) {
                                        customerSelector.setValue(store.getAt(0).data.packagingSupplierCode);
                                        agency.packingRecAndPayInput.queryStandardDamageRate();
                                    } else {
                                        customerSelector.expand();
                                    }
                                }
                            });
                        }
                    }
                },
                {
                    xtype: 'dynamicPackagingSupplierSelector',
                    listWidth: 300,
                    name: 'packingRecAndPayInputVo.packingRecAndPayInputDto.customerCode',
                    active: 'Y',
                    orgCodeCode: stl.currentDept.code,
                    fieldLabel: agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.label.customer'),
                    allowBlank: false,
                    listeners: {
                        'boxready': function (th) {
                            var store = th.store;
                            store.removeListener('beforeload');
                            store.addListener('beforeload', function (store, operation, eOpts) {
                                var searchParams = {
                                    'objectVo.packagingSupplierEntity.packagingSupplier': '',
                                    'objectVo.packagingSupplierEntity.active': 'Y',
                                    'objectVo.packagingSupplierEntity.orgCodeCode': agency.packingRecAndPayInput.global_deptCode
                                };

                                Ext.apply(operation, {
                                    params: searchParams
                                });
                            });
                            store.load({
                                scope: this,
                                callback: function (records, operation, success) {
                                    if (records.length == 1) {
                                        th.setValue(store.getAt(0).data.packagingSupplierCode);
                                        agency.packingRecAndPayInput.queryStandardDamageRate();
                                    } else {
                                        th.expand();
                                    }
                                }
                            });
                        },
                        select: function (me, newValue, oldValue, eOpts) {
                            var form = me.up('form').getForm();
                            form.findField('packingRecAndPayInputVo.packingRecAndPayInputDto.damageRate').reset();
                            agency.packingRecAndPayInput.queryStandardDamageRate();
                        }
                    }
                },

                {
                    xtype: 'combo',
                    name: 'packingRecAndPayInputVo.packingRecAndPayInputDto.billTime.year',
                    fieldLabel: agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.label.billTime'),
                    padding: '5px 0 5px 5px',
                    columnWidth: .20,
                    editable: false,
                    submitValue: false,
                    store: Ext.create('Ext.data.Store', {
                        fields: ['year'],
                        data: [
                            {
                                'year': 2014
                            },
                            {
                                'year': 2015
                            },
                            {
                                'year': 2016
                            },
                            {
                                'year': 2017
                            },
                            {
                                'year': 2018
                            },
                            {
                                'year': 2019
                            },
                            {
                                'year': 2020
                            },
                            {
                                'year': 2021
                            },
                            {
                                'year': 2022
                            },
                            {
                                'year': 2023
                            },
                            {
                                'year': 2024
                            },
                            {
                                'year': 2025
                            }
                        ]
                    }),
                    valueField: 'year',
                    displayField: 'year',
                    value: (new Date()).getFullYear(),
                    allowBlank: false,
                    listeners: {
                        change: function (me, newValue, oldValue, eOpts) {
                            var form = this.up('form').getForm();
                            var billTimeField = form.findField('amount');
                            var monthField = form.findField('amount');
                            billTimeField.setValue(newValue + '-' + monthField.value);
                        }
                    }
                },
                {
                    xtype: 'label',
                    html: agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.label.year'),
                    margin: '10px 0 0 0',
                    padding: '0 0 0 2px',
                    columnWidth: .03
                },
                {
                    xtype: 'combo',
                    columnWidth: .06,
                    hideLabel: true,
                    itemId: 'month',
                    editable: false,
                    submitValue: false,
                    name: 'packingRecAndPayInputVo.packingRecAndPayInputDto.billTime.month',
                    store: Ext.create('Ext.data.Store', {
                        fields: ['code', 'name'],
                        data: [
                            {
                                'code': 0,
                                'name': '1'
                            },
                            {
                                'code': 1,
                                'name': '2'
                            },
                            {
                                'code': 2,
                                'name': '3'
                            },
                            {
                                'code': 3,
                                'name': '4'
                            },
                            {
                                'code': 4,
                                'name': '5'
                            },
                            {
                                'code': 5,
                                'name': '6'
                            },
                            {
                                'code': 6,
                                'name': '7'
                            },
                            {
                                'code': 7,
                                'name': '8'
                            },
                            {
                                'code': 8,
                                'name': '9'
                            },
                            {
                                'code': 9,
                                'name': '10'
                            },
                            {
                                'code': 10,
                                'name': '11'
                            },
                            {
                                'code': 11,
                                'name': '12'
                            }
                        ]
                    }),
                    valueField: 'code',
                    displayField: 'name',
                    value: (new Date()).getMonth(),
                    allowBlank: false,
                    listeners: {
                        change: function (me, newValue, oldValue, eOpts) {
                            var form = this.up('form').getForm();
                            var billTimeField = form.findField('billTime');
                            var yearField = form.findField('year');
                            billTimeField.setValue(yearField.value + '-' + newValue);
                        }
                    }
                },
                {
                    xtype: 'label',
                    html: agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.label.month'),
                    margin: '10px 0 0 0',
                    padding: 0,
                    columnWidth: .06
                },
                {
                    name: 'packingRecAndPayInputVo.packingRecAndPayInputDto.inputType',
                    xtype: 'combo',
                    fieldLabel: agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.label.type'),
                    queryMode: 'local',
                    displayField: 'name',
                    valueField: 'code',
                    editable: false,
                    value: 'damageRate',
                    store: Ext.create('Ext.data.Store', {
                        fields: ['code', 'name'],
                        data: [
                            {
                                'code': 'damageRate',
                                'name': agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.combox.damageRate')
                            },
                            {
                                'code': 'damageClaim',
                                'name': agency.packingRecAndPayInput
                                    .i18n('foss.stl.agency.packingRecAndPayInput.combox.damageClaim')
                            },
                            {
                                'code': 'forkliftTicket',
                                'name': agency.packingRecAndPayInput
                                    .i18n('foss.stl.agency.packingRecAndPayInput.combox.forkliftTicket')
                            },
                            {
                                'code': 'aging',
                                'name': agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.combox.aging')
                            },
                            {
                                'code': 'mixing',
                                'name': agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.combox.mixing')
                            },
                            {
                                'code': 'losing',
                                'name': agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.combox.losing')
                            },
                            {
                                'code': 'complaint',
                                'name': agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.combox.complaint')
                            },
                            {
                                'code': 'battenCheck',
                                'name': agency.packingRecAndPayInput
                                    .i18n('foss.stl.agency.packingRecAndPayInput.combox.battenCheck')
                            },
                            {
                                'code': 'board',
                                'name': agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.combox.board')
                            },
                            {
                                'code': 'woodenStand',
                                'name': agency.packingRecAndPayInput
                                    .i18n('foss.stl.agency.packingRecAndPayInput.combox.woodenStand')
                            },
                            {
                                'code': 'tray',
                                'name': agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.combox.tray')
                            },
                            {
                                'code': 'packingTray',
                                'name': agency.packingRecAndPayInput
                                    .i18n('foss.stl.agency.packingRecAndPayInput.combox.packingTray')
                            },
                            {
                                'code': 'recOther',
                                'name': agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.combox.recOther')
                            },
                            {
                                'code': 'inconsistentWaybill',
                                'name': agency.packingRecAndPayInput
                                    .i18n('foss.stl.agency.packingRecAndPayInput.combox.inconsistentWaybill')
                            }
                        ]
                    }),
                    listeners: {
                        change: function (me, newValue, oldValue, eOpts) {
                            var formPanel = me.up('form');
                            var inputAmountCmp = formPanel.getComponent('inputAmount');
                            inputAmountCmp.setFieldLabel(agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.label.'
                                + newValue));
                            inputAmountCmp.setValue(null);

                            switch (newValue) {
                                case 'damageRate' :
                                    Ext.apply(inputAmountCmp, {
                                        minValue: -10000000000,
                                        maxValue: 10000000000
                                    });
                                    inputAmountCmp.setReadOnly(true);
                                    inputAmountCmp.setEditable(false);
                                    break;
                                case 'damageClaim' :
                                case 'forkliftTicket' :
                                case 'aging' :
                                case 'mixing' :
                                case 'losing' :
                                case 'complaint' :
                                case 'battenCheck' :
                                case 'recOther' :
                                    Ext.apply(inputAmountCmp, {
                                        minValue: 0,
                                        maxValue: 10000000000
                                    });
                                    inputAmountCmp.setReadOnly(false);
                                    inputAmountCmp.setEditable(true);
                                    break;
                                case 'board' :
                                case 'woodenStand' :
                                case 'tray' :
                                case 'packingTray' :
                                    Ext.apply(inputAmountCmp, {
                                        minValue: -10000000000,
                                        maxValue: 0
                                    });
                                    inputAmountCmp.setReadOnly(false);
                                    inputAmountCmp.setEditable(true);
                                    break;
                                case 'inconsistentWaybill' :
                                    Ext.apply(inputAmountCmp, {
                                        minValue: -10000000000,
                                        maxValue: 10000000000
                                    });
                                    inputAmountCmp.setReadOnly(false);
                                    inputAmountCmp.setEditable(true);
                                    break;
                            }

                            inputAmountCmp.doComponentLayout();

                            if (oldValue == 'damageRate') {
                                formPanel.getComponent('damageRate').hide();
                                formPanel.getComponent('damageRateLabel').hide();
                            } else if (oldValue == 'damageClaim' || oldValue == 'inconsistentWaybill') {
                                formPanel.getComponent('waybillNo').setValue(null);
                                formPanel.getComponent('waybillNo').hide();
                            }
                            if (newValue == 'damageRate') {
                                formPanel.getComponent('damageRate').show();
                                formPanel.getComponent('damageRate').setValue(null);
                                formPanel.getComponent('damageRateLabel').show();
                                formPanel.getComponent('waybillNo').hide();
                            } else if (newValue == 'damageClaim' || newValue == 'inconsistentWaybill') {
                                formPanel.getComponent('waybillNo').show();
                            }
                        }
                    }
                },
                {
                    name: 'packingRecAndPayInputVo.packingRecAndPayInputDto.damageRate',
                    itemId: 'damageRate',
                    fieldLabel: agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.label.damageRateLabel'),
                    maxValue: 100,
                    minValue: 0,
                    decimalPrecision: 6,
                    checkChangeBuffer: 300,
                    columnWidth: .28,
                    padding: '5px 0 5px 5px',
                    hideTrigger: true,
                    keyNavEnabled: false,
                    mouseWheelEnabled: false,
                    hideTrigger: true,
                    listeners: {
                        change: function (me, newValue, oldValue, eOpts) {
                            //公式为 ((x-破损率参数)/0.01%)*1000 = 10000000 * x-10000000 * 破损率参数
                            var amount = newValue *100000 - agency.packingRecAndPayInput.STANDARD_DAMAGE_RATE*10000000;
                            if (amount > agency.packingRecAndPayInput.STL_PACKING_DAMAGE_MAX_AMOUNT) {
                                amount = agency.packingRecAndPayInput.STL_PACKING_DAMAGE_MAX_AMOUNT;
                            } else if (amount < -agency.packingRecAndPayInput.STL_PACKING_DAMAGE_MAX_AMOUNT) {
                                amount = -agency.packingRecAndPayInput.STL_PACKING_DAMAGE_MAX_AMOUNT;
                            }
                            me.up('form').getComponent('inputAmount').setValue(amount);
                        }
                    }
                },
                {
                    itemId: 'damageRateLabel',
                    xtype: 'label',
                    html: '%',
                    margin: '5px 0 0 0',
                    padding: '6px 5px 5px 1px',
                    columnWidth: .02
                },
                {
                    name: 'inputAmount',
                    itemId: 'inputAmount',
                    fieldLabel: agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.label.damageRate'),
                    readOnly: true,
                    hideTrigger: true,
                    keyNavEnabled: false,
                    mouseWheelEnabled: false,
                    checkChangeBuffer: 300,
                    editable: false,
                    listeners: {
                        change: function (me, newValue, oldValue, eOpts) {
                            me.up('form').getComponent('amount').setValue(newValue);
                        }
                    }
                },
                {
                    name: 'packingRecAndPayInputVo.packingRecAndPayInputDto.waybillNo',
                    itemId: 'waybillNo',
                    xtype: 'textfield',
                    fieldLabel: '<span style="color:red">*</span>'
                        + agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.label.waybillNo'),
                    hidden: true,
                    regex: /\d{8,}/
                },
                {
                    xtype: 'hiddenfield',
                    name: 'packingRecAndPayInputVo.packingRecAndPayInputDto.amount',
                    itemId: 'amount',
                    value: '',
                    listeners: {
                        change: function (me, newValue, oldValue, eOpts) {
                            var amountTipCom = me.up('form').getComponent('amountTip');
                            if (newValue > 0) {
                                amountTipCom.setText(agency.packingRecAndPayInput
                                    .i18n('foss.stl.agency.packingRecAndPayInput.label.toCollect')
                                    + newValue);
                            } else if (newValue < 0) {
                                amountTipCom.setText(agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.label.toPay')
                                    + (-newValue));
                            } else {
                                amountTipCom.setText(agency.packingRecAndPayInput
                                    .i18n('foss.stl.agency.packingRecAndPayInput.label.amountIsZero'));
                            }
                            amountTipCom.getEl().highlight("eeee00", {
                                attr: "color",
                                easing: 'easeIn',
                                duration: 1000
                            });
                        }
                    }
                },
                {
                    xtype: 'hiddenfield',
                    name: 'packingRecAndPayInputVo.packingRecAndPayInputDto.billTime',
                    itemId: 'billTime',
                    value: '' + (new Date()).getFullYear() + Ext.String.leftPad(((new Date()).getMonth() + 1), 2, '0')
                },
                {
                    xtype: 'label',
                    itemId: 'amountTip',
                    columnWidth: 1,
                    style: {
                        'color': 'red',
                        'font-size': '16px',
                        'font-style': 'bold',
                        'text-align': 'center'
                    },
                    height: 24,
                    text: ''
                },
                {
                    text: agency.packingRecAndPayInput.i18n('foss.stl.agency.common.reset'),
                    xtype: 'button',
                    columnWidth: .1,
                    handler: function () {
                        this.up('form').getForm().reset();
                    }
                },
                {
                    xtype: 'component',
                    columnWidth: .7,
                    height: 24
                },
                {
                    text: agency.packingRecAndPayInput.i18n('foss.stl.agency.common.commit'),
                    columnWidth: .1,
                    xtype: 'button',
                    cls: 'yellow_button',
                    plugins: 'buttonlimitingplugin',
                    handler: function () {
                        var me = this;
                        me.disable();
                        var form = this.up('form').getForm();
                        if(form.findField('packingRecAndPayInputVo.packingRecAndPayInputDto.inputType').getValue() == 'damageRate'){
                            if(form.findField('packingRecAndPayInputVo.packingRecAndPayInputDto.damageRate').getValue() == null){
                                Ext.Msg.alert(agency.packingRecAndPayInput.i18n('foss.stl.agency.queryagencysystemreport.warmlyReminder'), agency.packingRecAndPayInput
                                    .i18n('foss.stl.agency.packingRecAndPayInput.validate.damageRateIsNull'));
                                return false;
                            }
                        }

                        var param = agency.packingRecAndPayInput.buildParams(form.getValues());
                        if (!param) {
                            return false;
                        }
                        Ext.apply(param, {
                            'packingRecAndPayInputVo.packingRecAndPayInputDto.customerName': form
                                .findField('packingRecAndPayInputVo.packingRecAndPayInputDto.customerCode').getRawValue()
                        });
                        if (!param) {
                            Ext.Msg.alert(agency.packingRecAndPayInput.i18n('foss.stl.agency.queryagencysystemreport.warmlyReminder'),
                                agency.packingRecAndPayInput.i18n('foss.stl.agency.packingRecAndPayInput.formInvalide'));
                        }
                        Ext.Ajax.request({
                            url: agency.realPath('packingRecAndPayInput.action'),
                            params: param,
                            callback: function (options, success, response) {
                                me.enable();
                                agency.packingRecAndPayInput.global_deptCode = stl.currentDept.code;
                                var rawData = Ext.decode(response.responseText);
                                if (rawData.success) {
                                    Ext.Msg.alert(agency.packingRecAndPayInput
                                            .i18n('foss.stl.agency.queryagencysystemreport.warmlyReminder'),
                                        agency.packingRecAndPayInput.i18n('foss.stl.agency.common.success'));
                                    agency.packingRecAndPayInput.form.getForm().reset();
                                    return false;
                                } else {
                                    Ext.Msg.alert(agency.packingRecAndPayInput
                                        .i18n('foss.stl.agency.queryagencysystemreport.warmlyReminder'), rawData.message);
                                    return false;
                                }
                            }
                        });
                    }
                }
            ]
        });
        me.callParent(arguments);
    }
});

// 初始化界面
Ext.onReady(function () {
    Ext.QuickTips.init();
    Ext.create('Ext.panel.Panel', {
        id: 'T_agency-packingRecAndPayInputPage_content',
        cls: "panelContentNToolbar",
        bodyCls: 'panelContentNToolbar-body',
        layout: 'auto',
        items: [agency.packingRecAndPayInput.form = Ext.create('Foss.agency.packingRecAndPayInput.form')],
        renderTo: 'T_agency-packingRecAndPayInputPage-body'
    });

    // 设置破损率最大金额
    Ext.Ajax.request({
        url: agency.realPath('stlPackingDamageMaxAmount.action'),
        callback: function (options, success, response) {
            if (success) {
                var data = Ext.decode(response.responseText);
                if (data.success) {
                    agency.packingRecAndPayInput.STL_PACKING_DAMAGE_MAX_AMOUNT =
                        data.packingRecAndPayInputVo.stlPackingDamageMaxAmount;
                } else {
                    Ext.Msg.alert(agency.packingRecAndPayInput.i18n('foss.stl.agency.queryagencysystemreport.warmlyReminder'),
                        data.message);
                    return false;
                }
            } else {
                Ext.Msg.alert(agency.packingRecAndPayInput.i18n('foss.stl.agency.queryagencysystemreport.warmlyReminder'), Ext
                    .decode(response.responseText).message);
                return false;
            }
        }
    })
});