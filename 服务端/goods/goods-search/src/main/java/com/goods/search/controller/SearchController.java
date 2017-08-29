package com.goods.search.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goods.search.pojo.SearchResult;
import com.goods.search.service.SearchService;
import com.goods.tools.common.util.TaotaoResult;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public Object search(@RequestParam(value = "callback", required = false) String callback,
			@RequestParam("q") String q, @RequestParam(value = "page", defaultValue = "1") long page,
			@RequestParam(value = "rows", defaultValue = "15") long rows) {
		// 解决get乱码
		try {
			q = new String(q.getBytes("iso-8859-1"), "utf-8");
			SearchResult searchResult = searchService.search(q, page, rows);
			TaotaoResult ok = TaotaoResult.ok(searchResult);
			if (callback != null && !StringUtils.isEmpty(callback)) {
				MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(ok);
				mappingJacksonValue.setJsonpFunction(callback);
				return mappingJacksonValue;
			}

			return searchResult;

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return TaotaoResult.build(400, "搜索出错，请稍后进行重试");
	}
}
