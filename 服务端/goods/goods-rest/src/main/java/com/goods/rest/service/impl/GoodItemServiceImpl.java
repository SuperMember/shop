package com.goods.rest.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.goods.manager.mapper.TbCommentsMapper;
import com.goods.manager.mapper.TbItemCatMapper;
import com.goods.manager.mapper.TbItemDescMapper;
import com.goods.manager.mapper.TbItemMapper;
import com.goods.manager.pojo.TbComments;
import com.goods.manager.pojo.TbCommentsExample;
import com.goods.manager.pojo.TbCommentsExample.Criteria;
import com.goods.manager.pojo.TbCommentsResult;
import com.goods.manager.pojo.TbItem;
import com.goods.manager.pojo.TbItemCat;
import com.goods.manager.pojo.TbItemCatExample;
import com.goods.manager.pojo.TbItemDesc;
import com.goods.manager.pojo.TbItemExample;
import com.goods.manager.pojo.TbItemResult;
import com.goods.rest.mapper.GoodDetailMapper;
import com.goods.rest.mapper.IndexGoodsMapper;
import com.goods.rest.pojo.AdData;
import com.goods.rest.pojo.AdItem;
import com.goods.rest.pojo.ItemDetail;
import com.goods.rest.pojo.TbItemCatResult;
import com.goods.rest.service.GoodItemService;
import com.goods.tools.common.util.JsonUtils;
import com.goods.tools.common.util.TaotaoResult;

@Service
public class GoodItemServiceImpl implements GoodItemService {

	@Autowired
	private TbCommentsMapper tbCommentsMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;

	@Autowired
	private TbItemCatMapper tbItemCatMapper;

	@Autowired
	private TbItemMapper tbItemMapper;

	@Autowired
	private GoodDetailMapper goodDetailMapper;

	@Autowired
	private IndexGoodsMapper indexGoodsMapper;

	// 根据商品id获取相关信息
	public ItemDetail getDetail(long itemId) {
		ItemDetail itemDetail = new ItemDetail();
		try {
			// 获取评论
			TbCommentsExample tbCommentsExample = new TbCommentsExample();
			Criteria criteria = tbCommentsExample.createCriteria();
			criteria.andItemidEqualTo(itemId);
			List<TbCommentsResult> list = tbCommentsMapper.selectByExample(tbCommentsExample);
			if (list != null && list.size() != 0) {
				itemDetail.setTbComment(list.get(0));
			} else {
				itemDetail.setTbComment(null);
			}
			// 获取详情
			TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
			itemDetail.setContent(itemDesc.getItemDesc());
			return itemDetail;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemDetail;
	}

	// 获取商品类别
	public TaotaoResult getClassify(long parentId) {
		try {
			List<TbItemCatResult> results = new ArrayList<TbItemCatResult>();
			TbItemCatExample tbItemCatExample = new TbItemCatExample();
			com.goods.manager.pojo.TbItemCatExample.Criteria criteria = tbItemCatExample.createCriteria();
			criteria.andParentIdEqualTo(parentId);
			List<TbItemCat> list = tbItemCatMapper.selectByExample(tbItemCatExample);
			for (TbItemCat tbItemCat : list) {
				TbItemCatResult tbItemCatResult = new TbItemCatResult();
				tbItemCatResult.setTbItemCat(tbItemCat);
				// 找子孩子
				tbItemCatExample = new TbItemCatExample();
				com.goods.manager.pojo.TbItemCatExample.Criteria child = tbItemCatExample.createCriteria();
				child.andParentIdEqualTo(tbItemCat.getId());
				List<TbItemCat> childs = tbItemCatMapper.selectByExample(tbItemCatExample);
				tbItemCatResult.setList(childs);
				results.add(tbItemCatResult);
			}
			return TaotaoResult.ok(JsonUtils.objectToJson(results));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.build(400, "获取失败，请稍后重试");
	}

	// 根据类别获取商品
	public TaotaoResult getGoods(long parentId, long page, long rows, Long cid) {
		try {
			List<TbItemResult> list = null;
			if (cid == null) {
				list = goodDetailMapper.getGoods(parentId, (page - 1) * rows, rows);
			} else {
				list = goodDetailMapper.getGoodsById(parentId, (page - 1) * rows, rows, cid);
			}
			if (list != null && list.size() != 0) {
				String json = JsonUtils.objectToJson(list);
				return TaotaoResult.ok(json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.build(400, "暂无该商品");
	}

	// 获取首页商品数据
	public TaotaoResult getIndex() {
		try {
			AdData adData = new AdData();
			// 广告位
			List<AdItem> list = indexGoodsMapper.getAds();
			for (int i = 0; i < list.size(); i++) {
				String desc = list.get(i).getDc();
				Document document = Jsoup.parse(desc);
				String img = null;
				if (document.select("img").size() > 1) {
					Element element = document.select("img").get(1);
					img = "http:" + element.attr("data-lazyload");
				} else {
					Element element = document.select("img").first();
					if (element != null)
						img = "http:" + element.attr("data-lazyload");
				}
				list.get(i).setDc(img);// 修改图片
			}
			adData.setAds(list);
			// 新品专区
			// 根据时间进行筛选
			TbItemExample tbItemExample = new TbItemExample();
			PageHelper.startPage(1, 15);
			tbItemExample.setOrderByClause("created desc");
			List<TbItemResult> news = tbItemMapper.selectByExample(tbItemExample);
			// 手机专区
			List<TbItem> phones = indexGoodsMapper.getGoods((long) 560);
			// 电脑专区
			List<TbItem> cps = indexGoodsMapper.getGoods((long) 163);
			// 家用专区
			List<TbItem> homeTool = indexGoodsMapper.getHomeTool();// 家用
			adData.setNews(news.subList(0, 5));// 最新
			adData.setPhones(phones);
			adData.setCps(cps);
			adData.setHometool(homeTool);
			adData.setOthers(news.subList(6, 15));// 其他商品
			String json = JsonUtils.objectToJson(adData);
			return TaotaoResult.ok(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.build(400, "获取失败，请稍后重试");
	}

	// 首页加载更多
	public TaotaoResult getIndexOther(int page) {
		try {
			TbItemExample tbItemExample = new TbItemExample();
			PageHelper.startPage(page <= 1 ? 2 : page, 10);
			tbItemExample.setOrderByClause("created desc");
			List<TbItemResult> news = tbItemMapper.selectByExample(tbItemExample);
			String json = JsonUtils.objectToJson(news);
			return TaotaoResult.ok(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.build(400, "获取失败，请重试");
	}

}
