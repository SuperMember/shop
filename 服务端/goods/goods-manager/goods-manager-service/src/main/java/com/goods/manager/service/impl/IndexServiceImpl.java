package com.goods.manager.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goods.manager.mapper.TbCommentsMapper;
import com.goods.manager.mapper.TbGoodsRankMapper;
import com.goods.manager.mapper.TbItemCatMapper;
import com.goods.manager.mapper.TbItemMapper;
import com.goods.manager.mapper.TbOrderFinishMapper;
import com.goods.manager.mapper.TbOrderMapper;
import com.goods.manager.pojo.IndexDataEntity;
import com.goods.manager.pojo.TbCommentsExample;
import com.goods.manager.pojo.TbCommentsResult;
import com.goods.manager.pojo.TbGoodsRank;
import com.goods.manager.pojo.TbGoodsRankExample;
import com.goods.manager.pojo.TbItem;
import com.goods.manager.pojo.TbItemCat;
import com.goods.manager.pojo.TbOrder;
import com.goods.manager.pojo.TbOrderExample;
import com.goods.manager.pojo.TbUser;
import com.goods.manager.pojo.TbOrderExample.Criteria;
import com.goods.manager.pojo.TbOrderFinish;
import com.goods.manager.service.IndexService;
import com.goods.tools.commom.pojo.OrderEntity;
import com.goods.tools.common.util.TaotaoResult;

@Service
public class IndexServiceImpl implements IndexService {
	@Autowired
	private TbOrderFinishMapper tbOrderFinishMapper;
	@Autowired
	private TbOrderMapper tbOrderMapper;
	@Autowired
	private TbCommentsMapper tbCommentsMapper;
	@Autowired
	private TbGoodsRankMapper tbGoodsRankMapper;// 排行榜

	// 查找相关数据
	public IndexDataEntity showDatas(HttpServletRequest request) {
		try {
			// 存放数据实体类
			IndexDataEntity indexDataEntity = new IndexDataEntity();
			// 从request中获取用户信息
			TbUser user = (TbUser) request.getAttribute("user");
			Long itemId = user.getId();
			// 查找订单相关数据
			TbOrderExample tbOrderExample = new TbOrderExample();
			Criteria criteria = tbOrderExample.createCriteria();
			criteria.andMuserIdEqualTo(itemId);
			int ordernum = tbOrderMapper.countByExample(tbOrderExample);
			indexDataEntity.setOrdernum(ordernum);// 订单总数量

			// 近一个月的数据

			// 订单关闭数量
			criteria.andStatusEqualTo(6);
			int colsenum = tbOrderMapper.countByExample(tbOrderExample);
			indexDataEntity.setClose(colsenum);

			// 计算总收入
			long count = 0;
			List<TbOrderFinish> item = tbOrderFinishMapper.getAllItem(itemId);

			// 订单完成数量
			indexDataEntity.setFinish(item.size());

			for (TbOrderFinish tbOrderFinish : item) {
				// 计算总收入
				count = count + tbOrderFinish.getPrice() * tbOrderFinish.getNum();
			}
			indexDataEntity.setIncome(count);// 计算总收入

			// 查找最新评论
			TbCommentsExample tbCommentsExample = new TbCommentsExample();
			tbCommentsExample.setOrderByClause("time desc");
			com.goods.manager.pojo.TbCommentsExample.Criteria comments = tbCommentsExample.createCriteria();
			comments.andSoldidEqualTo(itemId);// 根据卖家id进行查找
			List<TbCommentsResult> list = tbCommentsMapper.selectByExample(tbCommentsExample);
			if (list != null) {
				if (list.size() >= 6)
					indexDataEntity.setList(list.subList(0, 5));
				else
					indexDataEntity.setList(list);
			} else {
				indexDataEntity.setList(new ArrayList<TbCommentsResult>());
			}

			// 查找畅销榜(前十名)
			TbGoodsRankExample tbGoodsRankExample = new TbGoodsRankExample();
			tbGoodsRankExample.setOrderByClause("income desc");// 根据收入额进行排序
			com.goods.manager.pojo.TbGoodsRankExample.Criteria criteria2 = tbGoodsRankExample.createCriteria();
			criteria2.andUseridEqualTo(itemId);
			List<TbGoodsRank> ranks = tbGoodsRankMapper.selectByExample(tbGoodsRankExample);
			if (ranks != null && ranks.size() != 0) {
				if (ranks.size() >= 10)
					indexDataEntity.setRanks(ranks.subList(0, 9));
				else
					indexDataEntity.setRanks(ranks);
			} else {
				indexDataEntity.setRanks(new ArrayList<TbGoodsRank>());
			}
			return indexDataEntity;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 根据日期获取不同的订单情况
	public OrderEntity showOrderDatas(HttpServletRequest request, String current) {
		try {
			// 获取用户id

			TbUser tbUser = (TbUser) request.getAttribute("user");
			Long itemId = tbUser.getId();

			// 得出每个月中某一天的订单数量，形式 为[1,12]
			OrderEntity orderEntity = new OrderEntity();// 返回实体
			Map<Integer, Integer> datemap = new TreeMap<Integer, Integer>();// 存放某一天的订单数量
			// 按时间顺序获取订单情况
			List<TbOrder> list = tbOrderMapper.selectLastByExample(current, itemId);
			String result = null;
			int max = 0;
			if (list != null && list.size() != 0) {
				// 获取时间中的日
				for (TbOrder tbOrder : list) {
					Date createTime = tbOrder.getCreateTime();
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
					String day = simpleDateFormat.format(createTime);
					int intday = Integer.parseInt(day);// 日期的int形式
					if (datemap.get(intday) != null) {
						Integer integer = datemap.get(intday);
						int i = integer.intValue();
						++i;
						datemap.put(intday, i);
					} else {
						datemap.put(intday, 1);
					}
				}
				// 拼接成[1,12]形式
				// 得到key

				StringBuilder stringBuilder = new StringBuilder();
				Set<Integer> keyset = datemap.keySet();
				Iterator<Integer> iterator = keyset.iterator();
				int firstkey = 0;// 保存第一项
				boolean isFirst = true;
				while (iterator.hasNext()) {
					Integer key = iterator.next();// key值
					if (isFirst) {
						firstkey = key;
						isFirst = false;
					}
					// 获取value值
					Integer value = datemap.get(key);
					// 获取最大值
					if (value > max) {
						max = value;
					}
					// 拼接
					stringBuilder.append("[" + key + "," + value + "],");
				}

				result = new String(stringBuilder).substring(0, stringBuilder.length() - 1);
				if (datemap.size() > 0 && datemap.size() <= 7) {
					// 拼够7项
					String string = "";
					for (int i = 1; i <= 7 - datemap.size(); i++) {
						string = "[" + (firstkey - i) + ",0]," + string;
					}
					result = string + result;
				}
				result = "[" + result + "]";// 最终显示的结果

			} else {
				String string = "";
				for (int i = 1; i <= 7; i++) {
					string = string + "[" + i + ",0],";
				}
				result = string.substring(0, string.length() - 1);
				result = "[" + result + "]";

			}
			orderEntity.setStatus(200);
			orderEntity.setData(result);
			orderEntity.setMax(max == 0 ? 10 : max);
			return orderEntity;
		} catch (Exception e) {
			e.printStackTrace();
		}
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setStatus(400);
		return orderEntity;
	}
}
