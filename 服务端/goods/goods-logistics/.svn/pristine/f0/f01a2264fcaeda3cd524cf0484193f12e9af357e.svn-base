package com.test;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.goods.manager.mapper.TbItemDescMapper;
import com.goods.manager.mapper.TbItemMapper;
import com.goods.manager.pojo.TbItem;
import com.goods.manager.pojo.TbItemDesc;
import com.goods.tools.common.util.HttpClientUtil;
import com.goods.tools.common.util.IDUtils;

public class addGoods {
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;

	@Test
	public void add() {

		for (int i = 1; i <= 1; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("keyword", "数码相机");
			map.put("enc", "utf-8");
			map.put("page", i + "");
			String result = HttpClientUtil.doGet("https://search.jd.com/Search", map);
			if (result != null) {
				Document document = Jsoup.parse(result);
				Elements elements = document.select("ul li.gl-item");
				for (int j = 0; j < 1; j++) {
					Element element = elements.get(j);
					TbItem tEntity = new TbItem();
					Document lis = Jsoup.parse(element.html());
					String title = lis.select("div.p-img a").attr("title");// 标题
					tEntity.setTitle(title);

					String price = lis.select("div.p-price strong").attr("data-price");
					// 处理价格
					String goodsprice = price.substring(0, price.lastIndexOf("."));
					// 价格
					tEntity.setId(IDUtils.genItemId());
					tEntity.setPrice(Long.parseLong(goodsprice));
					tEntity.setSellPoint(title);
					tEntity.setNum(100);
					tEntity.setStatus((byte) 1);
					tEntity.setCreated(new Date());
					tEntity.setUpdated(new Date());
					tEntity.setCid((long) 582);
					// 设置卖家
					tEntity.setMuserId((long) 1);
					// 进入详情页,设置商品详情

					String url = "https:" + lis.select("div.p-img a").attr("href");

					Process p;
					try {
						Runtime rt = Runtime.getRuntime();
						String exec = "E:/web/phantomjs.exe E:/web/code.js " + url;
						p = rt.exec(exec);
						InputStream is = p.getInputStream();
						Document details = Jsoup.parse(is, "UTF-8", url);
						// 设置商品描述
						TbItemDesc tbItemDesc = new TbItemDesc();
						StringBuilder stringBuilder = new StringBuilder();
						Elements table = details.select("div#J-detail-content table tbody tr td img");
						for (Element element2 : table) {
							stringBuilder.append(
									"<div><img src=\"https:" + element2.attr("data-lazyload") + "\"</img><br></div>\n");
						}
						// 设置商品id
						tbItemDesc.setItemId(tEntity.getId());
						tbItemDesc.setItemDesc(new String(stringBuilder));
						tbItemDesc.setCreated(new Date());
						tbItemDesc.setUpdated(new Date());
						// 保存
						tbItemDescMapper.insert(tbItemDesc);

						// 设置商品图片
						StringBuilder imageurl = new StringBuilder();
						Elements pic = details.select("div#spec-list ul li img");// 图片
						for (Element image : pic) {
							String string = "https:" + image.attr("src");
							// 获取大张图片
							string.replace("s400x400_g5", "s54x54_g5");
							imageurl.append(image + ",");
						}
						tEntity.setImage(new String(imageurl));// 设置多张图片

						// 保存
						tbItemMapper.insert(tEntity);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}
}
