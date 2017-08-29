package com.goods.manager.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goods.manager.mapper.TbItemCatMapper;
import com.goods.manager.mapper.TbItemDescMapper;
import com.goods.manager.mapper.TbItemMapper;
import com.goods.manager.pojo.TbItem;
import com.goods.manager.pojo.TbItemCat;
import com.goods.manager.pojo.TbItemDesc;
import com.goods.manager.pojo.TbItemExample;
import com.goods.manager.pojo.TbItemResult;
import com.goods.manager.pojo.TbUser;
import com.goods.manager.pojo.TbItemExample.Criteria;
import com.goods.manager.service.GoodsItemService;
import com.goods.manager.service.UserService;
import com.goods.tools.commom.pojo.GoodsListItem;
import com.goods.tools.common.util.CookieUtils;
import com.goods.tools.common.util.ExceptionUtil;
import com.goods.tools.common.util.HttpClientUtil;
import com.goods.tools.common.util.IDUtils;
import com.goods.tools.common.util.JsonUtils;
import com.goods.tools.common.util.TaotaoResult;

@Service
public class GoodsItemServiceImpl implements GoodsItemService {

	@Autowired
	private TbItemMapper tbItemMapper;

	@Autowired
	private TbItemCatMapper tbItemCatMapper;// 商品类别

	@Autowired
	private TbItemDescMapper tbItemDescMapper;

	@Autowired
	private JmsTemplate jmsTemplate;// activemq

	// 获取与该卖家有关的所有商品
	// 有可能携带指定字段进行查询，例如状态
	public GoodsListItem getGoodsItemList(HttpServletRequest request, long page, long rows, Integer status) {
		// 从cookies中获取卖家id
		TbUser user = (TbUser) request.getAttribute("user");
		long item = user.getId();// 获取卖家id
		// 查找
		GoodsListItem goodsListItem = new GoodsListItem();
		try {
			TbItemExample tbItemExample = new TbItemExample();
			tbItemExample.setOrderByClause("updated desc");// 按更新时间进行排序查询
			Criteria criteria = tbItemExample.createCriteria();
			criteria.andMuserIdEqualTo(item);
			if (status != null && status.intValue() != 0) {
				// 增加查询条件
				criteria.andStatusEqualTo(status.byteValue());
			}
			PageHelper.startPage((int) page, (int) rows);
			List<TbItemResult> list = tbItemMapper.selectByExample(tbItemExample);
			// 修改商品的类别为文字形式
			for (TbItemResult tbItemResult : list) {
				TbItemCat itemCat = tbItemCatMapper.selectByPrimaryKey(tbItemResult.getCid());
				// 赋值为文字形式
				tbItemResult.setCidname(itemCat.getName());
			}
			goodsListItem.setRows(list);
			goodsListItem.setTotal((int) new PageInfo<TbItemResult>(list).getTotal());

			return goodsListItem;

		} catch (Exception e) {
			e.printStackTrace();
			return goodsListItem;
		}

	}

	// 增加商品
	@RequestMapping("/item/save")
	@ResponseBody
	public TaotaoResult addGoods(HttpServletRequest request, TbItem tbItem, String desc) {
		try {
			// 从cookies中获取卖家的id
			TbUser user = (TbUser) request.getAttribute("user");
			long muserId = user.getId();
			// 补全
			// 生成id
			tbItem.setId(IDUtils.genItemId());
			tbItem.setStatus((byte) 1);
			tbItem.setCreated(new Date());
			tbItem.setUpdated(new Date());
			// 设置商品的卖家
			tbItem.setMuserId(muserId);
			// 插入
			tbItemMapper.insert(tbItem);

			// 插入商品描述
			TbItemDesc tbItemDesc = new TbItemDesc();
			tbItemDesc.setItemId(tbItem.getId());
			tbItemDesc.setItemDesc(desc);
			tbItemDesc.setCreated(new Date());
			tbItemDesc.setUpdated(new Date());
			tbItemDescMapper.insert(tbItemDesc);
			return TaotaoResult.ok();

		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	// 修改商品
	// 根据id进行修改
	// 通知搜索服务根据状态上架或下架商品(即移出搜索或加入搜索)
	public TaotaoResult editGoods(TbItem tbItem) {
		try {
			tbItem.setUpdated(new Date());
			// 更新
			tbItemMapper.updateByPrimaryKey(tbItem);

			final long itemId = tbItem.getId();
			final String type;
			if (tbItem.getStatus() == 1) {
				// 上架
				// 通知搜索服务将该商品加入到搜索中
				type = "up";
			} else if (tbItem.getStatus() == 2) {
				// 下架
				// 将改商品移出搜索中
				type = "down";
			} else {
				type = null;
			}
			// 创建成功之后利用activemq发送信息给manager管理后台，以实现实时通知
			jmsTemplate.send(new MessageCreator() {

				public Message createMessage(Session session) throws JMSException {
					return session.createTextMessage(type + ":" + itemId);
				}
			});
			return TaotaoResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.build(400, "修改失败，请稍后重新重试");
	}

	// 根据id获取某个商品
	public TbItem getTbItem(long itemId) {
		try {
			TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
			return tbItem;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 删除商品
	// 有可能有多个
	public TaotaoResult deleteGoods(String[] itemIds) {
		try {
			// 批量删除
			for (String itemId : itemIds) {
				tbItemMapper.deleteByPrimaryKey(Long.parseLong(itemId));
			}
			return TaotaoResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.build(400, "修改失败，请稍后重试");
	}

	

}
