package com.bootdo.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.common.controller.BaseController;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.StringUtils;
import com.bootdo.system.domain.LabourreportmainDO;
import com.bootdo.system.service.LabourreportmainService;

/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-10-11 13:33:34
 */

@Controller
@RequestMapping("/system/labourreportmain")
public class LabourreportmainController extends BaseController {
	@Autowired
	private LabourreportmainService labourreportmainService;

	@GetMapping()
	@RequiresPermissions("system:labourreportmain:labourreportmain")
	String Labourreportmain(String Code, Model model) {
		model.addAttribute("Code", Code);
		return "system/labourreportmain/labourreportmain";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:labourreportmain:labourreportmain")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<LabourreportmainDO> labourreportmainList = labourreportmainService
				.list(query);
		int total = labourreportmainService.count(query);
		PageUtils pageUtils = new PageUtils(labourreportmainList, total);
		return pageUtils;
	}

	@GetMapping("/add")
	@RequiresPermissions("system:labourreportmain:add")
	String add(String Code, Model model) {
		model.addAttribute("Code", Code);
		return "system/labourreportmain/add";
	}

	@GetMapping("/edit/{oid}")
	@RequiresPermissions("system:labourreportmain:edit")
	String edit(@PathVariable("oid") String oid, Model model) {
		LabourreportmainDO labourreportmain = labourreportmainService.get(oid);
		model.addAttribute("labourreportmain", labourreportmain);
		return "system/labourreportmain/edit";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:labourreportmain:add")
	public R save(LabourreportmainDO labourreportmain) {
		Long uid = getUserId();
		if (!StringUtils.isEmpty(labourreportmain.getRenderdate()))
			labourreportmain.setRenderdate(labourreportmain.getRenderdate()
					+ "-01");
		labourreportmain.setUptuser(uid.toString());
		String pkey = labourreportmainService.getPkey(labourreportmain);
		if (!StringUtils.isEmpty(pkey)) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:labourreportmain:edit")
	public R update(LabourreportmainDO labourreportmain) {
		if (!StringUtils.isEmpty(labourreportmain.getRenderdate()))
			labourreportmain.setRenderdate(labourreportmain.getRenderdate()
					+ "-01");
		labourreportmainService.update(labourreportmain);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	@RequiresPermissions("system:labourreportmain:remove")
	public R remove(String oid, String Code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("oid", oid);
		map.put("code", Code);
		String result = labourreportmainService.delPkey(map);
		if (!StringUtils.isEmpty(result))
			return R.ok();
		// if (labourreportmainService.remove(oid) > 0) {
		// return R.ok();
		// }
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:labourreportmain:batchRemove")
	public R remove(@RequestParam("ids[]") String[] oids) {
		labourreportmainService.batchRemove(oids);
		return R.ok();
	}

}
