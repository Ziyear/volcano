package com.ziyear.volcano.service.impl;

import com.ziyear.volcano.domain.User;
import com.ziyear.volcano.service.UserCacheService;
import com.ziyear.volcano.util.Constants;
import com.ziyear.volcano.util.CryptoUtil;
import com.ziyear.volcano.util.TotpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述 : TODO
 *
 * @author zhaorui 2021-10-28 17:30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserCacheServiceImpl implements UserCacheService {

    private final RedissonClient redissonClient;
    private final TotpUtil totpUtil;
    private final CryptoUtil cryptoUtil;


    @Override
    public String cacheUser(User user) {
        String mfaId = cryptoUtil.randomAlphanumeric(12);
        RMapCache<String, User> cache = redissonClient.getMapCache(Constants.CACHE_MFA);
        if (!cache.containsKey(mfaId)) {
            cache.put(mfaId, user, totpUtil.getTimeStepInLong(), TimeUnit.SECONDS);
        }
        return mfaId;
    }

    @Override
    public Optional<User> retrieveUser(String mfaId) {
        RMapCache<String, User> cache = redissonClient.getMapCache(Constants.CACHE_MFA);
        if (cache.containsKey(mfaId)) {
            return Optional.of(cache.get(mfaId));
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> verifyTotp(String mfaId, String code) {
        RMapCache<String, User> cache = redissonClient.getMapCache(Constants.CACHE_MFA);
        if (!cache.containsKey(mfaId) || cache.get(mfaId) == null) {
            return Optional.empty();
        }
        val cachedUser = cache.get(mfaId);
        log.debug("找到用户 {}", cachedUser);
        try {
            val isValid = totpUtil.validateTotp(totpUtil.decodeKeyFromString(cachedUser.getMfaKey()), code);
            log.debug("code {} 的验证结果为 {}", code, isValid);
            if (!isValid) {
                return Optional.empty();
            }
            cache.remove(mfaId);
            log.debug("移除 mfaId: {}", mfaId);
            return Optional.of(cachedUser);
        } catch (InvalidKeyException e) {
            log.error("Key is invalid {}", e.getLocalizedMessage());
        }
        return Optional.empty();
    }
}
