$(function () {
    $.getJSON("dashboard/search", {}, function (rs) {
        if (rs && rs.code == 200 && rs.content) {
            $("#channelSize").text(rs.content.channelSize);
            $("#gatewaySize").text(rs.content.gatewaySize);
            $("#msgSize").text(rs.content.msgSize);
        }
    });

    var rangesConf = {};
    rangesConf['今日'] = [moment().startOf('day'), moment().endOf('day')];
    rangesConf['昨日'] = [moment().subtract(1, 'days').startOf('day'), moment().subtract(1, 'days').endOf('day')];
    rangesConf['本月'] = [moment().startOf('month'), moment().endOf('month')];
    rangesConf['上个月'] = [moment().subtract(1, 'months').startOf('month'), moment().subtract(1, 'months').endOf('month')];
    rangesConf['最近一周'] = [moment().subtract(1, 'weeks').startOf('day'), moment().endOf('day')];
    rangesConf['最近一月'] = [moment().subtract(1, 'months').startOf('day'), moment().endOf('day')];

    $('#filterTime').daterangepicker({
        autoApply: false,
        singleDatePicker: false,
        showDropdowns: false,        // 是否显示年月选择条件
        timePicker: true, 			// 是否显示小时和分钟选择条件
        timePickerIncrement: 10, 	// 时间的增量，单位为分钟
        timePicker24Hour: true,
        opens: 'left', //日期选择框的弹出位置
        ranges: rangesConf,
        locale: {
            format: 'YYYY-MM-DD HH:mm:ss',
            separator: ' - ',
            customRangeLabel: '自定义',
            applyLabel: '确定',
            cancelLabel: '关闭',
            fromLabel: '起始时间',
            toLabel: '结束时间',
            daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
            monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
            firstDay: 1
        },
        startDate: rangesConf['最近一周'][0],
        endDate: rangesConf['最近一周'][1]
    }, function (start, end, label) {
        freshChartDate(start, end);
    });
    freshChartDate(rangesConf['最近一月'][0], rangesConf['最近一月'][1]);

    function freshChartDate(startDate, endDate) {
        $.getJSON("dashboard/chart", {
                'startDate': startDate.format('YYYY-MM-DD HH:mm:ss'),
                'endDate': endDate.format('YYYY-MM-DD HH:mm:ss')
            },
            function (rs) {
                if (rs && rs.code == 200 && rs.content) {
                    lineChartInit(rs)
                    pieChartInit(rs);
                }
            });
    }

    /**
     * line Chart Init
     */
    function lineChartInit(data) {
        var option = {
            title: {
                text: '日期分布图'
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    label: {
                        backgroundColor: '#6a7985'
                    }
                }
            },
            legend: {
                data: ['梦网', '快加易', '聚信']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    boundaryGap: false,
                    data: data.content.dayList
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: '梦网',
                    type: 'line',
                    stack: 'Total',
                    areaStyle: {normal: {}},
                    data: data.content.mwList
                },
                {
                    name: '快加易',
                    type: 'line',
                    stack: 'Total',
                    label: {
                        normal: {
                            show: true,
                            position: 'top'
                        }
                    },
                    areaStyle: {normal: {}},
                    data: data.content.kjyList
                },
                {
                    name: '聚信',
                    type: 'line',
                    stack: 'Total',
                    areaStyle: {normal: {}},
                    data: data.content.jxList
                }
            ]
        };

        var lineChart = echarts.init(document.getElementById('lineChart'));
        lineChart.setOption(option);
    }

    /**
     * pie Chart Init
     */
    function pieChartInit(data) {
        var option = {
            title: {
                text: '信息分布图',
                x: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: ['梦网', '快加易', '聚信']
            },
            series: [
                {
                    type: 'pie',
                    radius: '55%',
                    data: [
                        {
                            name: '梦网',
                            value: data.content.mwCount
                        },
                        {
                            name: '快加易',
                            value: data.content.kjyCount
                        },
                        {
                            name: '聚信',
                            value: data.content.jxCount
                        }
                    ]
                }
            ]
        };
        var pieChart = echarts.init(document.getElementById('pieChart'));
        pieChart.setOption(option);
    }
});

