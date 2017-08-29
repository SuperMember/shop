package com.goods.logistics.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.druid.support.json.JSONUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goods.logistics.pojo.LogisticsJsonDetail;
import com.goods.logistics.pojo.LogisticsJsonResult;
import com.goods.logistics.service.LogisticsService;
import com.goods.manager.dao.JedisClient;
import com.goods.manager.mapper.TbAreaMapper;
import com.goods.manager.mapper.TbCityMapper;
import com.goods.manager.mapper.TbLogisticsMapper;
import com.goods.manager.mapper.TbProvinceMapper;
import com.goods.manager.pojo.TbArea;
import com.goods.manager.pojo.TbAreaExample;
import com.goods.manager.pojo.TbCity;
import com.goods.manager.pojo.TbCityExample;
import com.goods.manager.pojo.TbCityExample.Criteria;
import com.goods.manager.pojo.TbLogistics;
import com.goods.manager.pojo.TbLogisticsExample;
import com.goods.manager.pojo.TbProvince;
import com.goods.manager.pojo.TbProvinceExample;
import com.goods.manager.pojo.TbUser;
import com.goods.tools.commom.pojo.GoodsListItem;
import com.goods.tools.common.util.HttpClientUtil;
import com.goods.tools.common.util.JsonUtils;
import com.goods.tools.common.util.TaotaoResult;

@Service
public class LogisticsServiceImpl implements LogisticsService {

	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private TbAreaMapper tbAreaMapper;
	@Autowired
	private TbProvinceMapper tbProvinceMapper;
	@Autowired
	private TbCityMapper tbCityMapper;

	@Value("${LOGISTICS_GEN_KEY}")
	private String LOGISTICS_GEN_KEY;// 物流号key
	@Value("${LOGISTICS_INIT_VALUE}")
	private String LOGISTICS_INIT_VALUE;// 物流号初始值

	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	@Value("${ORDER_EDIT_URL}")
	private String ORDER_EDIT_URL;

	@Value("${LOGISTICS_NAME}")
	private String LOGISTICS_NAME;// 快递名称
	@Value("${LOGISTICS_DETAIL}")
	private String LOGISTICS_DETAIL;// 快递详情
	@Autowired
	private TbLogisticsMapper tbLogisticsMapper;

	// 获取地址
	public String getLocatioin(String type, Integer id) {
		try {
			if (type.equals("province")) {
				// 省
				TbProvinceExample tbProvinceExample = new TbProvinceExample();
				List<TbProvince> list = tbProvinceMapper.selectByExample(tbProvinceExample);
				if (list != null && list.size() != 0) {
					String result = JsonUtils.objectToJson(list);
					return result;
				}
			} else if (type.equals("city")) {
				// 城市
				TbCityExample tbCityExample = new TbCityExample();
				Criteria criteria = tbCityExample.createCriteria();
				criteria.andFatherEqualTo(id + "");
				List<TbCity> list = tbCityMapper.selectByExample(tbCityExample);
				if (list != null && list.size() != 0) {
					String json = JsonUtils.objectToJson(list);
					return json;
				}
			} else if (type.equals("area")) {
				// 区域
				TbAreaExample tbAreaExample = new TbAreaExample();
				com.goods.manager.pojo.TbAreaExample.Criteria criteria = tbAreaExample.createCriteria();
				criteria.andFatherEqualTo(id + "");
				List<TbArea> list = tbAreaMapper.selectByExample(tbAreaExample);
				if (list != null && list.size() != 0) {
					String json = JsonUtils.objectToJson(list);
					return json;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 物流
	public TaotaoResult sendgoods(TbLogistics tbLogistics) {
		try {
			// 利用redis的incr生成物流号
			String string = jedisClient.get(LOGISTICS_GEN_KEY);
			if (StringUtils.isEmpty(string)) {
				jedisClient.set(LOGISTICS_GEN_KEY, LOGISTICS_INIT_VALUE);
			}
			long orderId = jedisClient.incr(LOGISTICS_GEN_KEY);// 生成物流号
			tbLogistics.setId(orderId);
			tbLogistics.setCreated(new Date());
			tbLogistics.setUpdated(new Date());
			// 修改订单的信息
			try {
				//while (true) {
					Map<String, String> param = new HashMap<String, String>();
					param.put("itemId", tbLogistics.getOrderid() + "");
					param.put("status", 4 + "");// 已发货
					param.put("shippingname", tbLogistics.getCompany());// 物流名称
					param.put("shippingcode", orderId + "");// 物流单号
					String result = HttpClientUtil.doGet(ORDER_BASE_URL + ORDER_EDIT_URL, param);
					TaotaoResult taotaoResult = TaotaoResult.format(result);
					if (taotaoResult.getStatus() == 200) {
						//break;
					}
				//}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 保存物流信息
			tbLogisticsMapper.insert(tbLogistics);
			return TaotaoResult.ok(orderId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.build(400, "送货失败，请稍后重试");
	}

	// 显示与该卖家有关的物流信息
	public GoodsListItem showLogistics(long page, long rows, long userId) {
		try {
			// 获取卖家id
			TbLogisticsExample tbLogisticsExample = new TbLogisticsExample();
			com.goods.manager.pojo.TbLogisticsExample.Criteria criteria = tbLogisticsExample.createCriteria();
			criteria.andMuseridEqualTo(userId);
			PageHelper.startPage((int) page, (int) rows);
			List<TbLogistics> list = tbLogisticsMapper.selectByExample(tbLogisticsExample);
			if (list != null && list.size() != 0) {
				GoodsListItem goodsListItem = new GoodsListItem();
				goodsListItem.setTotal((int) new PageInfo<TbLogistics>(list).getTotal());
				goodsListItem.setRows(list);
				return goodsListItem;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 显示详细的物流信息
	public LogisticsJsonDetail showDetail(String logisticsId) {
		// 先获取快递名称
		Map<String, String> param = new HashMap<String, String>();
		param.put("text", logisticsId);
		String result = HttpClientUtil.doGet(LOGISTICS_NAME, param);
		LogisticsJsonResult logisticsJsonResult = JsonUtils.jsonToPojo(result, LogisticsJsonResult.class);
		// 截取快递公司名称
		String companyName = logisticsJsonResult.getAuto().get(0).getComCode();
		// 接着获取快递详情
		Map<String, String> params = new HashMap<String, String>();
		params.put("type", companyName);
		params.put("postid", logisticsId);
		String detail = HttpClientUtil.doGet(LOGISTICS_DETAIL, params);
		LogisticsJsonDetail logisticsJsonDetail = JsonUtils.jsonToPojo(detail, LogisticsJsonDetail.class);
		return logisticsJsonDetail;
	}

}
