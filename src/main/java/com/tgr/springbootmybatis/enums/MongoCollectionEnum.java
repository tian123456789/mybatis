package com.tgr.springbootmybatis.enums;

/**
 * @author lee
 * @date 2019/9/26.
 */
public enum MongoCollectionEnum {

    /**
     * mongo_collection
     */
    MONGO_COLLECTION_NOTICE("notice", 1),
    MONGO_COLLECTION_MIEN("mien", 2),
    MONGO_COLLECTION_NEWS("news", 3),
    MONGO_COLLECTION_ORG_ACTIVITY("activity", 4),
    MONGO_COLLECTION_POSITIVE_THEME("theme", 5),
    MONGO_COLLECTION_PREFER("prefer", 6),
    MONGO_COLLECTION_STUDY("study", 7),
    MONGO_COLLECTION_MENU("menu", 8),
    MONGO_COLLECTION_HEALTH("health", 9),
    MONGO_COLLECTION_HELP("help", 10),
	MONGO_COLLECTION_COMMENT("comment", 11),
	MONGO_COLLECTION_REFLECTION("reflection", 12);

    private String name;
    private String value;

    private int intValue;

    MongoCollectionEnum(String name, int intValue) {
        this.name = name;
        this.intValue = intValue;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public int getIntValue() {
        return intValue;
    }
}
