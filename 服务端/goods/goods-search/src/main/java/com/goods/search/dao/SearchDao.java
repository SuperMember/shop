package com.goods.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import com.goods.search.pojo.SearchResult;



public interface SearchDao {

	SearchResult search(SolrQuery query) throws Exception;
}
