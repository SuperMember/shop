package com.goods.sso.mapper;

import com.goods.sso.pojo.UserEntity;

public interface UserMapper {
	UserEntity getLogin(String username);
}
