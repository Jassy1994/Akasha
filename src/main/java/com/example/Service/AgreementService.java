package com.example.Service;

import com.example.util.JedisAdapter;
import com.example.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Date;

/**
 * Created by Jassy on 2017/2/28.
 * description:
 */
@Service
public class AgreementService {

    @Autowired
    JedisAdapter jedisAdapter;

    public long agreement(int userId, int entityType, int entityId) {
        String agreementKey = RedisKeyUtil.getAgreementKey(entityType, entityId);
        Date date = new Date();
        jedisAdapter.zadd(agreementKey, date.getTime(), String.valueOf(userId));
        return jedisAdapter.zcard(agreementKey);
    }

    public long cancelAgreement(int userId, int entityType, int entityId) {
        String agreementKey = RedisKeyUtil.getAgreementKey(entityType, entityId);
        return jedisAdapter.zrem(agreementKey, String.valueOf(userId));
    }

    public long getAgreementNum(int entityType, int entityId) {
        String agreementKey = RedisKeyUtil.getAgreementKey(entityType, entityId);
        return jedisAdapter.zcard(agreementKey);
    }

    public int getAgreementStatus(int userId, int entityType, int entityId) {
        String agreementKey = RedisKeyUtil.getAgreementKey(entityType, entityId);
        if (jedisAdapter.sismember(agreementKey, String.valueOf(userId))) {
            return 1;
        } else {
            return 0;
        }
    }
}
