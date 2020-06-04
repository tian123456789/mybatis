layui.use(['layer', 'form', 'table', 'upload'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var upload = layui.upload;

    var columns = [];
    var nginxContext = $('#nginxContextPath').val();
    form.on('select(tableName)', function (data) {
        var tableName = data.value;
        var schema = $("#schema").val();
        var access = $("#access").val();
        if (!tableName) {
            return;
        }
        if (!schema) {
            layer.alert("数据库不能为空");
            return;
        }
        columns = [];
        $.ajax({
            url: nginxContext + '/export/tableColumn?tableName=' + tableName + '&schema=' + schema + '&access=' + access,
            success: function (res) {
                // 创建table表头元素
                if (res.code === 200) {
                    for (var i = 0; i < res.data.length; i++) {
                        var data = {};
                        data.field = res.data[i];
                        data.title = res.data[i];
                        data.width = 200;
                        if (res.data[i] != 'id') {
                            data.edit = 'text';
                        }
                        columns.push(data);
                    }
                } else {
                    layer.alert(res.message);
                }
            },
            failure: function (error) {
                alert("查询表元数据失败");
            }
        });
    });


    $('#queryBtn').on('click', function () {
        queryData();
    });

    function queryData() {
        var params = addQueryCondition();
        if (!params.tableName) {
            layer.alert('请选择查询的表');
            return;
        }

        table.render({
            elem: '#example',
            url: nginxContext + '/export/queryDb',
            page: true,
            where: params,
            cols: [columns],
            height: 'full-200',
            request: {
                pageName: 'page',
                limitName: 'size'
            },
            parseData: function (res) {
                return {
                    "code": res.code === 200 ? 0 : -1, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.data.totalElements, //解析数据长度
                    "data": res.data.content //解析数据列表
                };
            }
        });
    }

    //监听单元格编辑
    table.on('edit(test)', function (obj) {
        var tableName = $('#tableName').val();
        var schema = $("#schema").val();
        var access = $("#access").val();
        var value = obj.value; //得到修改后的值
        var data = obj.data; //得到所在行所有键值
        var field = obj.field; //得到字段
        layer.confirm('确认按如下信息进行修改？<pre class="layui-code">table = ' + tableName + ',<br/>id = ' + data.id + ',<br/>column = ' + field + ',<br/>value = ' + value + '</pre>', {
            icon: 3,
            title: '提示'
        }, function (index) {

            layer.prompt({
                formType: 1,
                title: '请输口令'
            }, function (token, index, elem) {
                $.ajax({
                    url: nginxContext + '/export/modifyDataById?schema=' + schema + '&access=' + access,
                    data: {id: data.id, field: field, value: value, tableName: tableName, token: token},
                    success: function (res) {
                        if (res.code === 200) {
                            layer.alert(res.message, function (index) {
                                layer.close(index);
                                queryData();
                            });
                        } else {
                            layer.msg(res.message, {icon: 5});
                        }
                    },
                    failure: function (error) {
                        layer.alert("操作失败");
                    }
                });
                layer.close(index);
            });

        }, function (index, layero) {
            queryData();
        });
    });

    $('#exportBtn').on('click', function () {
        var tableName = $("#tableName").val();
        var schema = $("#schema").val();
        var access = $("#access").val();
        var condition = $("#condition").val();
        window.open(nginxContext + '/export/exportExcel?tableName=' + tableName + '&schema=' + schema + '&access=' + access + '&condition=' + condition);
    });

    $('#importBtn').on('click', function () {
        layer.open({
            type: 1,
            title: '初始化抽奖企业',
            area: ['500px', '400px'],
            content: $('#importDiv') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
        });
    });

    //执行实例
    var uploadInst = upload.render({
        elem: '#uploadBtn',
        url: nginxContext + '/export/initExcel',
        accept: 'file',
        acceptMime: 'application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
        field: 'filename',
        data: {access: $("#access").val()},
        done: function (res) {
            $('#importResult').html('');
            //上传完毕回调
            if (res.code === 200) {
                layer.msg(res.message);
            } else {
                layer.alert(res.message, {icon: 5});
                var data = res.data;
                if (data) {
                    $('#importResult').append('<li style="color: #009f95">未找到的企业列表如下：</li>');
                    $('#importResult').append('<li>&nbsp;</li>');
                }
                for (var i = 0; i < data.length; i++) {
                    $('#importResult').append('<li style="display:inline; padding: 5px;">' + data[i] + '</li>');
                }
            }

        },
        error: function (err) {
            //请求异常回调
            layer.alert(err, {icon: 5});
        }
    })

    $.fn.serializeObject = function () {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function () {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };
});