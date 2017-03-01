package com.example.util;

/**
 * Created by Jassy on 2017/3/1.
 * description:
 */
public class RedisKeyUtil {
    private static String SPLIT=":";
    private static String BIZ_AGREEMENT="AGREEMENT";
    private static String BIZ_EVENTQUEUE="EVENT_QUEUE";

    private static String BIZ_FOLLOWER="FOLLOWER";
    private static String BIZ_FOLLOWEE="FOLLOWEE";
    private static String BIZ_TIMELINE="TIMELINE";

    public static String getAgreementKey(int entityType,int entityId){
        return BIZ_AGREEMENT+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
    }

    public static String getEventQueueKey(){
        return BIZ_EVENTQUEUE;
    }

    /**
     * 关注了某个实体的人的key;
     * @param entityType
     * @param entityId
     * @return
     */
    public static String getFollowerKey(int entityType,int entityId){
        return BIZ_FOLLOWER+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
    }

    /**
     * 不是很理解;
     * @param userId
     * @param entityType
     * @return
     */
    public static String getFolloweeKey(int userId,int entityType){
        return BIZ_FOLLOWEE+SPLIT+String.valueOf(userId)+SPLIT+String.valueOf(entityType);
    }

    public static String getTimelineKey(int userId){
        return BIZ_TIMELINE+SPLIT+String.valueOf(userId);
    }

}
