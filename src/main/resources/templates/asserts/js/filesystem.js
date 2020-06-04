layui.use(['layer', 'table', 'form'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;

    var currentPath = '';
    var access = $("#access").val();
    var params = {parentPath: "/", access: access};
    var nginxContext = $('#nginxContextPath').val();
    var tableObj = table.render({
        elem: '#filesystem',
        url: nginxContext+'/export/filesystem/getFileList',
        page: false,
        where: params,
        height: 'full-200',
        parseData: function (res) {
            return {
                "code": res.code === 200 ? 0 : -1, //解析接口状态
                "msg": res.message, //解析提示文本
                "data": res.data //解析数据列表
            };
        },
        cols: [[
            {
                field: 'path', title: '文件目录', templet: function (data) {
                    if (data.fileType == '0') {
                        return "<a href=\"javascript:action.reload('" + data.path + "');\" style='color:#01AAED'>" + data.path + "</a>";
                    } else if (data.fileType == '1') {
                        return "<a href=\"javascript:action.download('" + data.path + "');\" style='color:#2F4056'>" + data.path + "</a>";
                    }
                }
            }, {
                field: 'fileType', title: '文件类型', width: 250, templet: function (data) {
                    return data.fileType == 0 ? "目录" : "文件&nbsp;" +
                        "<a href='javascript:void(0)' style='color:#01AAED' onclick=\"action.download('" + data.path + "')\">点击下载</a>&nbsp;" +
                        "<a href='javascript:void(0)' style='color:#01AAED' onclick=\"action.preview('" + data.path + "')\">即时预览</a>";
                },
            },
            {field: 'fileSize', title: '文件大小(字节)', width: 250},
            {field: 'updateTime', title: '最后修改日期', width: 250},
            {
                field: 'isHidden', title: '隐藏文件', width: 250, templet: function (data) {
                    return data.isHidden ? '是' : '否';
                }
            }
        ]]
    });

    $('#backPath').on('click', function () {
        action.back(currentPath);
    });

    $('#intoPath').on('click', function (e) {
        action.reload($('#currentPath').val());
    });

    action = {
        reload: function (path) {
            currentPath = path;
            $('#currentPath').val(currentPath);
            tableObj.reload({
                where: {
                    parentPath: path,
                    access: access
                }
            });
        },
        back: function () {
            var path = currentPath;
            path = path.split('/');
            if (!path) {
                path = '/';
            } else {
                path.pop();
                path = path.join('/');
                if (!path) {
                    path = '/';
                }
            }
            currentPath = path;
            action.reload(path);
        },
        download: function (path) {

            layer.prompt({
                formType: 1,
                title: '请输口令',
                // area: ['800px', '350px'] //自定义文本域宽高
            }, function (value, index, elem) {
                window.open(nginxContext+'/export/filesystem/download?currentFile=' + path + '&access=' + access + '&token=' + value);
                layer.close(index);
            });

            // window.open('/export/filesystem/download?currentFile='+path+"&access="+

            // $.ajax({
            //     url: '/export/filesystem/download',
            //     type: 'post',
            //     data: {'currentFile': path, access: access},
            //     success: function (data, status, xhr) {
            //         console.log("Download file DONE!");
            //         console.log(data); // ajax方式请求的数据只能存放在javascipt内存空间，可以通过javascript访问，但是无法保存到硬盘
            //         console.log(status);
            //         console.log(xhr);
            //         console.log("=====================");
            //     }
            // });
        },
        preview:function(path){
            layer.alert('晢未实现');
            // window.open(nginxContext+'/export/filesystem/syslog?currentFile=' + path + '&access=' + access);
        }
    };
});

var action = {};