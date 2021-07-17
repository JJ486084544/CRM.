<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + 	request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>Title</title>
</head>
<body>
    <script>
        $.ajax({
            url : "clue/getUserList.do",
            type : "post",
            dataType : "json",
            success : function (data){
                if (data.success){

                }
                else {
                    $("#msg").html(data.message);
                }
            }
        })
        $(".time").datetimepicker({
            minView: "month",
            language: 'zh-CN',
            format: 'yyyy-mm-dd',
            autoclose: true,
            todayBtn: true,
            pickerPosition: "top-left"
        });
        var $xz = $("input[name=xz]:checked");
    </script>
    activity.setCreateTime(DateTimeUtil.getSysTime());
    activity.setCreateBy(((User)request.getSession().getAttribute("user")).getName());
</body>
</html>
