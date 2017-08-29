package com.goods.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import com.goods.manager.mapper.TbAddrMapper;
import com.goods.manager.mapper.TbUserMapper;
import com.goods.manager.pojo.Muser;
import com.goods.manager.pojo.TbAddr;
import com.goods.manager.pojo.TbAddrExample;
import com.goods.manager.pojo.TbUser;
import com.goods.manager.pojo.TbUserExample;
import com.goods.manager.pojo.TbUserExample.Criteria;
import com.goods.sso.dao.JedisClient;
import com.goods.sso.mapper.UserMapper;
import com.goods.sso.pojo.UserEntity;
import com.goods.sso.service.UserService;
import com.goods.tools.common.util.CookieUtils;
import com.goods.tools.common.util.ExceptionUtil;
import com.goods.tools.common.util.JsonUtils;
import com.goods.tools.common.util.TaotaoResult;

@Service
public class UserServiceImpl implements UserService {

	@Value("${REDIS_USER_SESSION_KEY}")
	private String REDIS_USER_SESSION_KEY;// redis
	@Value("${SSO_SESSION_EXPIRE}")
	private String SSO_SESSION_EXPIRE;// redis

	@Autowired
	private TbUserMapper tbUserMapper;
	@Autowired
	private JedisClient jedisClient;

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private TbAddrMapper tbAddrMapper;

	public TaotaoResult login(String username, String password, HttpServletRequest request,
			HttpServletResponse response, Integer index) {
		try {
			/*
			 * TbUserExample tbUserExample = new TbUserExample(); Criteria
			 * criteria = tbUserExample.createCriteria();
			 * criteria.andUsernameEqualTo(username); List<TbUser> list =
			 * tbUserMapper.selectByExample(tbUserExample);
			 */

			/*
			 * if (list == null || list.size() == 0) return
			 * TaotaoResult.build(400, "�û�������"); TbUser user = list.get(0);
			 * if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.
			 * getPassword())) { // ������� return TaotaoResult.build(400,
			 * "�������"); }
			 */
			UserEntity user = userMapper.getLogin(username);
			if (user == null) {
				return TaotaoResult.build(400, "用户不存在");
			}

			if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
				return TaotaoResult.build(400, "密码错误");
			}

			// 查找是否有设置邮寄地址
			TbAddrExample tbAddrExample = new TbAddrExample();
			com.goods.manager.pojo.TbAddrExample.Criteria criteria = tbAddrExample.createCriteria();
			criteria.andMuseridEqualTo(user.getId());
			List<TbAddr> list = tbAddrMapper.selectByExample(tbAddrExample);
			if (list != null && list.size() != 0) {
				user.setTbAddrs(list);
			}

			String token = UUID.randomUUID().toString();

			user.setPassword(null);
			String userJson = JsonUtils.objectToJson(user);

			jedisClient.set(REDIS_USER_SESSION_KEY + ":" + token, userJson);
			// 设置session过期时间
			jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, Integer.parseInt(SSO_SESSION_EXPIRE));

			// ���дcookie���߼���cookie����Ч���ǹر��������ʧЧ��
			// ��ҳ����Ҫ����cookies��,��������Ҫ����cookies��
			// ֻ�����ҽ��е�¼ʱ����Ҫ����cookies��
			// CookieUtils.setCookie(request, response, "TT_TOKEN", token);

			if (index != null) {
				return TaotaoResult.ok(userJson);
			}
			return TaotaoResult.ok(token);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}

	}

	// 注册
	public TaotaoResult register(TbUser tbUser) {
		try {
			tbUser.setCreated(new Date());
			tbUser.setUpdated(new Date());
			tbUser.setSeller(0);// 初始化角色为买家
			// 加密
			tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
			tbUserMapper.insert(tbUser);
			return TaotaoResult.ok();

		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(400, "注册失败");
		}
	}

	// type
	public TaotaoResult checkDatas(String datas, int type) {
		TbUserExample tbUserExample = new TbUserExample();
		Criteria criteria = tbUserExample.createCriteria();
		if (type == 1) {
			// 用户名
			criteria.andUsernameEqualTo(datas);
			List<TbUser> list = tbUserMapper.selectByExample(tbUserExample);
			if (list == null || list.size() == 0)
				return TaotaoResult.ok("成功");
			return TaotaoResult.build(400, "用户名已存在");
		} else if (type == 2) {
			// 邮箱
			criteria.andUsernameEqualTo(datas);
			List<TbUser> list = tbUserMapper.selectByExample(tbUserExample);
			if (list == null || list.size() == 0)
				return TaotaoResult.ok("成功");
			return TaotaoResult.build(400, "邮箱已存在");
		} else if (type == 3) {
			// 手机号
			criteria.andUsernameEqualTo(datas);
			List<TbUser> list = tbUserMapper.selectByExample(tbUserExample);
			if (list == null || list.size() == 0) {
				String code = null;
				return TaotaoResult.ok(code);
			}
			return TaotaoResult.build(400, "手机号已被注册");
		} else {
			return TaotaoResult.build(400, "失败");
		}

	}

	// 编辑
	public TaotaoResult editAddr(TbAddr tbAddr) {
		try {
			tbAddrMapper.updateByPrimaryKey(tbAddr);
			return TaotaoResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.build(400, "编辑失败，请稍后再试");
	}

	// 删除
	public TaotaoResult deleteAddr(Long id) {
		try {
			tbAddrMapper.deleteByPrimaryKey(id);
			return TaotaoResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.build(400, "删除失败，请稍后重试");
	}

	// 新增
	public TaotaoResult addAddr(TbAddr tbAddr) {
		try {
			tbAddrMapper.insert(tbAddr);
			return TaotaoResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.build(400, "新增失败，请稍后重试");
	}

}
