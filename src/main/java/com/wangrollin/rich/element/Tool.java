package com.wangrollin.rich.element;

public class Tool {

    public static final String TOOL_TYPE_ROBOT = "TOOL_TYPE_ROBOT";
    public static final String TOOL_TYPE_BOMB = "TOOL_TYPE_BOMB";
    public static final String TOOL_TYPE_BARRIER = "TOOL_TYPE_BARRIER";

    public static final int PRICE_FOR_BARRIER = 50;
    public static final int PRICE_FOR_ROBOT = 30;
    public static final int PRICE_FOR_BOMB = 50;

    private String toolType;

    private String picture;

    public Tool(String type) {
        toolType = type;
        if (TOOL_TYPE_ROBOT.equals(type)) {
            picture = "R";
        } else if (TOOL_TYPE_BOMB.equals(type)) {
            picture = "@";
        } else if (TOOL_TYPE_BARRIER.equals(type)) {
            picture = "#";
        }
    }

    public String getToolType() {
        return toolType;
    }

    public String getPicture() {
        return picture;
    }
}
