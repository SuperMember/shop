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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import com.goods.manager.mapper.TbItemDescMapper;
import com.goods.manager.mapper.TbItemMapper;
import com.goods.manager.pojo.TbItem;
import com.goods.manager.pojo.TbItemDesc;
import com.goods.tools.common.util.HttpClientUtil;
import com.goods.tools.common.util.IDUtils;

public class addGoods {
	private TbItemMapper tbItemMapper;
	private TbItemDescMapper tbItemDescMapper;
	//private String[] mTitle={"投影机","投影配件","多功能一体机","打印机","传真设备","验钞/点钞机","扫描设备","复合机","碎纸机","考勤机","墨粉","收款/POS机","会议音频视频","保险柜","装订/封装机","安防监控","办公家具","白板"};
	//private int[] index={211,212,213,214,215,216,217,218,219,220,221,222,223,224,225,226,227,228};
	//private String[] mTitle={"硒鼓/墨粉","墨盒","色带","纸类","办公文具","学生文具","文件管理","财会用品","本册/便签","计算器","激光笔","笔类","画具画材","刻录碟片/附件"}
	//private int[] index={230,231,232,233,234,235,236,237,238,239,240,241,242,243}
	//private String[] mTitle={"MP3/MP4","智能设备","耳机/耳麦","音箱","高清播放器","MP3/MP4配件","麦克风","专业音频","数码相框","苹果配件"};
	//private int[] index={615,616,617,618,619,620,621,622,623,624};
	//private String[] mTitle={"男装衬衫","男装T恤","男装POLO衫","男装针织衫","男装羊绒衫","男装卫衣","男装马甲/背心","男装夹克","男装风衣","男装毛呢大衣","男装仿皮皮衣","男装西服","男装棉服","男装羽绒服","男装牛仔裤","男装休闲裤","男装西裤","男装西服套装","男装大码男装","男装中老年男装","男装唐装/中山装","男装工装","男装真皮皮衣","男装加绒裤","男装卫裤/运动裤","男装短裤","男装设计师/潮牌","男装羊毛衫"};
	//private int[] index={785,786,787,788,789,790,791,792,793,794,795,796,797,798,799,780,781,782,783,784,785,786,787,788,789,790,791,792,793,794,795,796,797,798,799,800,801,802,803,804,805,806,807,808,809,810,811,812};
	
	@Test
	public void add() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-Dao.xml");
		tbItemDescMapper = (TbItemDescMapper) applicationContext.getBean("tbItemDescMapper");
		tbItemMapper = (TbItemMapper) applicationContext.getBean("tbItemMapper");
		for (int i = 0; i < 1; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("keyword", "手机");
			map.put("enc", "utf-8");
			map.put("page", 0+"");
			String result = HttpClientUtil.doGet("https://search.jd.com/Search", map);
			if (result != null) {
				Document document = Jsoup.parse(result);
				Elements elements = document.select("ul li.gl-item");
				for (int j = 10; j < 11; j++) {
					StringBuilder imageurl = new StringBuilder();
					Element element = elements.get(j);
					TbItem tEntity = new TbItem();
					Document lis = Jsoup.parse(element.html());
					//String title = lis.select("div.p-img a").attr("title");// 商品标题
					//String title=lis.select("div.p-name-type-2 a i.promo-words").html();
					String title=lis.select("div.p-name-type-2 a").attr("title");
					tEntity.setTitle(title);
					String price = lis.select("div.p-price strong").attr("data-price");
					// 商品价格
					String goodsprice;
					if (price != null && !price.equals("")) {
						int index = price.lastIndexOf(".");
						if (index == -1) {
							continue;
						}
						goodsprice = price.substring(0, index);
					} else {
						continue;
					}
					// 处理价格
					tEntity.setId(IDUtils.genItemId());
					tEntity.setPrice(Long.parseLong(goodsprice));
					tEntity.setSellPoint(title);
					tEntity.setNum(100);
					tEntity.setStatus((byte) 1);
					tEntity.setCreated(new Date());
					tEntity.setUpdated(new Date());
					// 设置类别
					tEntity.setCid((long) 560);
					// 设置
					tEntity.setMuserId((long) 1);

					String url = "https:" + lis.select("div.p-img a").attr("href");

					Process p;
					try {
						Runtime rt = Runtime.getRuntime();
						String exec = "E:/web/phantomjs.exe E:/web/code.js " + url;
						p = rt.exec(exec);
						InputStream is = p.getInputStream();
						Document details = Jsoup.parse(is, "UTF-8", url);
						// 商品描述
						TbItemDesc tbItemDesc = new TbItemDesc();
						Element table = details.select("div.detail-content-wrap").first();
						tbItemDesc.setItemId(tEntity.getId());
						tbItemDesc.setItemDesc(table.html());
						tbItemDesc.setCreated(new Date());
						tbItemDesc.setUpdated(new Date());
						// 插入商品描述
						tbItemDescMapper.insert(tbItemDesc);

						// 详情页

						Elements pic = details.select("div#spec-list ul li");
						for (int c = 0; c < pic.size(); c++) {
							Element li = pic.get(c);
							Document images = Jsoup.parse(li.html());
							Elements img = images.select("img");
							String string = "https:" + img.attr("src");
							// 处理图片
							String newstring = null;
							if (string.contains("s54x54_g5")) {

								newstring = string.replace("s54x54_g5", "s400x400_g5");
							} else if (string.contains("s54x54_jfs")) {
								newstring = string.replace("s54x54_jfs", "s400x400_jfs");
							} else if (string.contains("/n5/")) {
								newstring = string.replace("/n5/", "/n1/");
							}
							imageurl.append(newstring + ",");
						}
						tEntity.setImage(new String(imageurl));// 设置商品图片
						// 插入商品
						tbItemMapper.insert(tEntity);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}
}
