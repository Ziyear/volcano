package com.ziyear.volcano.service;

import com.ziyear.volcano.domain.User;

import java.util.Optional;

/**
 * 功能描述 : TODO
 *
 * @author zhaorui 2021-10-28 17:30
 */
public interface UserCacheService {

    String cacheUser(User user);

    Optional<User> retrieveUser(String mfaId);

    Optional<User> verifyTotp(String mfaId, String code);
}
