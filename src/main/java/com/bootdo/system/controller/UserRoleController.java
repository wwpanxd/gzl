package com.bootdo.system.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.system.domain.UserRoleDO;
import com.bootdo.system.service.UserRoleService;

@RequestMapping("/sys/roleuser")
@Controller
public class UserRoleController extends BaseController{
	String prefix = "system/role";
	@Autowired
	UserRoleService userroleservice;

//	@RequiresPermissions("sys:role:userlist")
	@GetMapping()
	String role() {
		return prefix + "/userlist";
	}

//	@RequiresPermissions("sys:role:userlist")
	@GetMapping("/list")
	@ResponseBody()
	PageUtils list(@RequestParam Map<String, Object> params) {
		 Query query = new Query(params);
		List<UserRoleDO> roleusers = userroleservice.list(query);
		int total =0;
		if(roleusers!=null) total=roleusers.size();
		PageUtils pageUtils = new PageUtils(roleusers, total);
		return pageUtils;
	}
}
