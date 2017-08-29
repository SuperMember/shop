package com.goods.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.goods.search.dao.SearchDao;
import com.goods.search.pojo.Item;
import com.goods.search.pojo.SearchResult;



/**
 * 商品搜索Dao

 */
@Repository("searchDao")
public class SearchDaoImpl implements SearchDao {
	
	@Autowired
	private SolrServer solrServer;
	public SearchResult search(SolrQuery query) throws Exception {
		//返回值对象
		SearchResult result = new SearchResult();
		//根据查询条件查询索引库
		QueryResponse queryResponse = solrServer.query(query);
		//取查询结果
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		//取查询结果总数量
		result.setTotal(solrDocumentList.getNumFound());
		//商品列表
		List<Item> itemList = new ArrayList();
		//取高亮显示
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		//取商品列表
		for (SolrDocument solrDocument : solrDocumentList) {
			//创建一商品对象
			Item item = new Item();
			item.setId((String) solrDocument.get("id"));
			//取高亮显示的结果
		/*	List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if (list != null && list.size()>0) {
				title = list.get(0);
			} else {
				title = (String) solrDocument.get("item_title");
			}*/
			
			item.setTitle((String) solrDocument.get("item_title"));
			item.setImage((String) solrDocument.get("item_image"));
			item.setPrice((Long)solrDocument.get("item_price"));
			item.setSellPoint((String) solrDocument.get("item_sellPoint"));
			item.setCidname((String) solrDocument.get("item_cid"));
			item.setStatus(((Integer)solrDocument.get("item_status")).byteValue());
			item.setNum((Integer)solrDocument.get("item_num"));
			item.setMuser_id((Long) solrDocument.get("item_muserid"));
			//添加的商品列表
			itemList.add(item);
		}
		result.setItemList(itemList);
		return result;
	}

}
