package com.goods.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goods.search.dao.SearchDao;
import com.goods.search.pojo.SearchResult;
import com.goods.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDao searchDao;

	// 根据关键字进行查询
	public SearchResult search(String queryString, long page, long rows) {

		try {

			// 创建查询对象
			SolrQuery query = new SolrQuery();
			// 设置查询条件
			query.setQuery(queryString);
			// 设置分页
			query.setStart((int) ((page - 1) * rows));
			query.setRows((int) rows);
			// 设置默认搜素域
			query.set("df", "item_keywords");
			// 设置高亮显示
			/*query.setHighlight(true);
			query.addHighlightField("item_title");
			query.setHighlightSimplePre("<em style=\"color:red\">");
			query.setHighlightSimplePost("</em>");*/
			// 执行查询
			SearchResult searchResult;

			searchResult = searchDao.search(query);

			// 计算查询结果总页数
			long recordCount = searchResult.getTotal();
			long pageCount = recordCount / rows;
			if (recordCount % rows > 0) {
				pageCount++;
			}
			searchResult.setPageCount(pageCount);
			searchResult.setCurPage(page);

			return searchResult;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
