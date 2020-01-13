package com.wangrollin.rich.element;

public class Place {
    public static final String NORMAL_PLACE_TYPE_IN_UP = "PLACE_TYPE_IN_UP";
    public static final String NORMAL_TYPE_IN_DOWN = "PLACE_TYPE_IN_DOWN";
    public static final String NORMAL_TYPE_IN_RIGHT = "PLACE_TYPE_IN_RIGHT";

    public static final String SPECIAL_PLACE_TYPE_HOSPITAL = "SPECIAL_PLACE_TYPE_HOSPITAL";
    public static final String SPECIAL_PLACE_TYPE_PRISON = "SPECIAL_PLACE_TYPE_PRISON";
    public static final String SPECIAL_PLACE_TYPE_MAGIC = "SPECIAL_PLACE_TYPE_MAGIC";
    public static final String SPECIAL_PLACE_TYPE_TOOL = "SPECIAL_PLACE_TYPE_TOOL";
    public static final String SPECIAL_PLACE_TYPE_START = "SPECIAL_PLACE_TYPE_START";
    public static final String SPECIAL_PLACE_TYPE_GIFT = "SPECIAL_PLACE_TYPE_GIFT";

    public static final String SPECIAL_PLACE_TYPE_MINE = "SPECIAL_PLACE_TYPE_MINE";

    public static final String PLACE_FOR_NOTHING = "PLACE_FOR_NOTHING";

    private String placeType;

    public Place(String type) {
        placeType = type;
    }

    public String getPlaceType() {
        return placeType;
    }
}
