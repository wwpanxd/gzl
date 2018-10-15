$().ready(function() {
	validateRule();

	laydate({
        elem : '#renderdate'
    });
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/system/labourreportmain/save",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			renderdepart : {
				required : true
			},
			renderdate : {
				required : true
			}
		},
		messages : {
			renderdepart : {
				required : icon + "请输入填报单位"
			},
			renderdate : {
				required : icon + "请输入汇报年月"
			}
		}
	})
}