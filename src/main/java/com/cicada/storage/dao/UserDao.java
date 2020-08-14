package com.cicada.storage.dao;

import com.cicada.Constants;
import com.cicada.storage.bean.UserInfo;
import com.dotin.cache.annotation.Cache;
import com.dotin.cache.annotation.CacheEvict;
import com.dotin.cache.annotation.CacheParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    int save(UserInfo info);

    @CacheEvict(keys = "user_{#item.loginId}")
    int update(@CacheParam("item") UserInfo item);

    @Cache(key = "user_{#loginId}", ttl = Constants.MINUTES * 10)
    UserInfo loadById(@Param("loginId") @CacheParam("loginId") String loginId);

    @CacheEvict(keys = "user_{#loginId}")
    int delete(@Param("loginId") @CacheParam("loginId") String loginId);
}
