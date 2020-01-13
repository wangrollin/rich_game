package com.wangrollin.rich.element;

public class House {

    public static final String HOUSE_TYPE_NONE_IN_UP = "HOUSE_TYPE_NONE_IN_UP";
    public static final String HOUSE_TYPE_NONE_IN_DOWN = "HOUSE_TYPE_NONE_IN_DOWN";
    public static final String HOUSE_TYPE_NONE_IN_RIGHT = "HOUSE_TYPE_NONE_IN_RIGHT";
    public static final String HOUSE_TYPE_NORMAL_IN_UP = "HOUSE_TYPE_NORMAL_IN_UP";
    public static final String HOUSE_TYPE_NORMAL_IN_DOWN = "HOUSE_TYPE_NORMAL_IN_DOWN";
    public static final String HOUSE_TYPE_NORMAL_IN_RIGHT = "HOUSE_TYPE_NORMAL_IN_RIGHT";
    public static final String HOUSE_TYPE_GOOD_IN_UP = "HOUSE_TYPE_GOOD_IN_UP";
    public static final String HOUSE_TYPE_GOOD_IN_DOWN = "HOUSE_TYPE_GOOD_IN_DOWN";
    public static final String HOUSE_TYPE_GOOD_IN_RIGHT = "HOUSE_TYPE_GOOD_IN_RIGHT";
    public static final String HOUSE_TYPE_BEST_IN_UP = "HOUSE_TYPE_BEST_IN_UP";
    public static final String HOUSE_TYPE_BEST_IN_DOWN = "HOUSE_TYPE_BEST_IN_DOWN";
    public static final String HOUSE_TYPE_BEST_IN_RIGHT = "HOUSE_TYPE_BEST_IN_RIGHT";

    public static final int PAY_FOR_NONE_IN_UP = 100;
    public static final int PAY_FOR_NONE_IN_DOWN = 150;
    public static final int PAY_FOR_NONE_IN_RIGHT = 250;
    public static final int PAY_FOR_NORMAL_IN_UP = 200;
    public static final int PAY_FOR_NORMAL_IN_DOWN = 300;
    public static final int PAY_FOR_NORMAL_IN_RIGHT = 500;
    public static final int PAY_FOR_GOOD_IN_UP = 400;
    public static final int PAY_FOR_GOOD_IN_DOWN = 600;
    public static final int PAY_FOR_GOOD_IN_RIGHT = 1000;
    public static final int PAY_FOR_BEST_IN_UP = 800;
    public static final int PAY_FOR_BEST_IN_DOWN = 1200;
    public static final int PAY_FOR_BEST_IN_RIGHT = 2000;

    public static final int SELL_FOR_NONE_IN_UP = 400;
    public static final int SELL_FOR_NONE_IN_DOWN = 600;
    public static final int SELL_FOR_NONE_IN_RIGHT = 1000;
    public static final int SELL_FOR_NORMAL_IN_UP = 800;
    public static final int SELL_FOR_NORMAL_IN_DOWN = 1200;
    public static final int SELL_FOR_NORMAL_IN_RIGHT = 2000;
    public static final int SELL_FOR_GOOD_IN_UP = 1200;
    public static final int SELL_FOR_GOOD_IN_DOWN = 1800;
    public static final int SELL_FOR_GOOD_IN_RIGHT = 3000;
    public static final int SELL_FOR_BEST_IN_UP = 1600;
    public static final int SELL_FOR_BEST_IN_DOWN = 2400;
    public static final int SELL_FOR_BEST_IN_RIGHT = 4000;

    public static final int UPGRADE_FOR_HOUSE_IN_UP = 200;
    public static final int UPGRADE_FOR_HOUSE_IN_DOWN = 300;
    public static final int UPGRADE_FOR_HOUSE_IN_RIGHT = 500;

    public static final int BUY_FOR_PLACE_IN_UP = 200;
    public static final int BUY_FOR_PLACE_IN_DOWN = 300;
    public static final int BUY_FOR_PLACE_IN_RIGHT = 500;

    private String houseType;
    private String owner;

    private String picture;


    public House(String playerNum, String type) {
        houseType = type;
        owner = playerNum;
        calPicture();
    }

    public String getPicture() {
        calPicture();
        return picture;
    }


    public int getPriceForUpgrade() {
        if (HOUSE_TYPE_NONE_IN_DOWN.equals(houseType)
                || HOUSE_TYPE_NORMAL_IN_DOWN.equals(houseType)
                || HOUSE_TYPE_GOOD_IN_DOWN.equals(houseType)) {
            return UPGRADE_FOR_HOUSE_IN_DOWN;
        } else if (HOUSE_TYPE_NONE_IN_RIGHT.equals(houseType)
                || HOUSE_TYPE_NORMAL_IN_RIGHT.equals(houseType)
                || HOUSE_TYPE_GOOD_IN_RIGHT.equals(houseType)) {
            return UPGRADE_FOR_HOUSE_IN_RIGHT;
        } else if (HOUSE_TYPE_NONE_IN_UP.equals(houseType)
                || HOUSE_TYPE_NORMAL_IN_UP.equals(houseType)
                || HOUSE_TYPE_GOOD_IN_UP.equals(houseType)) {
            return UPGRADE_FOR_HOUSE_IN_UP;
        }
        return 0;
    }

    public void calPicture() {
        if (HOUSE_TYPE_NORMAL_IN_DOWN.equals(houseType)
                || HOUSE_TYPE_NORMAL_IN_UP.equals(houseType)
                || HOUSE_TYPE_NORMAL_IN_RIGHT.equals(houseType)) {
            picture = "1";
        } else if (HOUSE_TYPE_GOOD_IN_DOWN.equals(houseType)
                || HOUSE_TYPE_GOOD_IN_RIGHT.equals(houseType)
                || HOUSE_TYPE_GOOD_IN_UP.equals(houseType)) {
            picture = "2";
        } else if (HOUSE_TYPE_BEST_IN_UP.equals(houseType)
                || HOUSE_TYPE_BEST_IN_RIGHT.equals(houseType)
                || HOUSE_TYPE_BEST_IN_DOWN.equals(houseType)) {
            picture = "3";
        }
        else if (HOUSE_TYPE_NONE_IN_UP.equals(houseType)
                || HOUSE_TYPE_NONE_IN_RIGHT.equals(houseType)
                || HOUSE_TYPE_NONE_IN_DOWN.equals(houseType)) {
            picture = "0";
        }
    }

    public boolean isNone() {
        if (HOUSE_TYPE_NONE_IN_DOWN.equals(houseType)
                || HOUSE_TYPE_NONE_IN_UP.equals(houseType)
                || HOUSE_TYPE_NONE_IN_RIGHT.equals(houseType)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isNormal() {
        if (HOUSE_TYPE_NORMAL_IN_DOWN.equals(houseType)
                || HOUSE_TYPE_NORMAL_IN_UP.equals(houseType)
                || HOUSE_TYPE_NORMAL_IN_RIGHT.equals(houseType)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isGood() {
        if (HOUSE_TYPE_GOOD_IN_DOWN.equals(houseType)
                || HOUSE_TYPE_GOOD_IN_RIGHT.equals(houseType)
                || HOUSE_TYPE_GOOD_IN_UP.equals(houseType)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isBest() {
        if (HOUSE_TYPE_BEST_IN_UP.equals(houseType)
                || HOUSE_TYPE_BEST_IN_RIGHT.equals(houseType)
                || HOUSE_TYPE_BEST_IN_DOWN.equals(houseType)) {
            return true;
        } else {
            return false;
        }
    }

    public String getOwner() {
        return owner;
    }

    public boolean canUpgrade() {
        if (HOUSE_TYPE_BEST_IN_UP.equals(houseType) || HOUSE_TYPE_BEST_IN_DOWN.equals(houseType)
                || HOUSE_TYPE_BEST_IN_RIGHT.equals(houseType)) {
            return false;
        } else {
            return true;
        }
    }

    public String getHouseType() {
        return houseType;
    }

    public void upgrade() {
        if (HOUSE_TYPE_NORMAL_IN_UP.equals(houseType))
        {
            houseType = HOUSE_TYPE_GOOD_IN_UP;
        } else if (HOUSE_TYPE_GOOD_IN_UP.equals(houseType))
        {
            houseType = HOUSE_TYPE_BEST_IN_UP;
        } else if (HOUSE_TYPE_NORMAL_IN_DOWN.equals(houseType))
        {
            houseType = HOUSE_TYPE_GOOD_IN_DOWN;
        } else if (HOUSE_TYPE_GOOD_IN_DOWN.equals(houseType))
        {
            houseType = HOUSE_TYPE_BEST_IN_DOWN;
        } else if (HOUSE_TYPE_NORMAL_IN_RIGHT.equals(houseType))
        {
            houseType = HOUSE_TYPE_GOOD_IN_RIGHT;
        } else if (HOUSE_TYPE_GOOD_IN_RIGHT.equals(houseType))
        {
            houseType = HOUSE_TYPE_BEST_IN_RIGHT;
        }

        else if (HOUSE_TYPE_NONE_IN_RIGHT.equals(houseType))
        {
            houseType = HOUSE_TYPE_NORMAL_IN_RIGHT;
        }

        else if (HOUSE_TYPE_NONE_IN_UP.equals(houseType))
        {
            houseType = HOUSE_TYPE_NORMAL_IN_UP;
        }
        else if (HOUSE_TYPE_NONE_IN_DOWN.equals(houseType))
        {
            houseType = HOUSE_TYPE_NORMAL_IN_DOWN;
        }
    }

    public int getPriceForPass() {

        if (HOUSE_TYPE_NORMAL_IN_UP.equals(houseType)) {
            return PAY_FOR_NORMAL_IN_UP;
        } else if (HOUSE_TYPE_NORMAL_IN_DOWN.equals(houseType)) {
            return PAY_FOR_NORMAL_IN_DOWN;
        } else if (HOUSE_TYPE_NORMAL_IN_RIGHT.equals(houseType)) {
            return PAY_FOR_NORMAL_IN_RIGHT;
        } else if (HOUSE_TYPE_GOOD_IN_UP.equals(houseType)) {
            return PAY_FOR_GOOD_IN_UP;
        } else if (HOUSE_TYPE_GOOD_IN_DOWN.equals(houseType)) {
            return PAY_FOR_GOOD_IN_DOWN;
        } else if (HOUSE_TYPE_GOOD_IN_RIGHT.equals(houseType)) {
            return PAY_FOR_GOOD_IN_RIGHT;
        } else if (HOUSE_TYPE_BEST_IN_UP.equals(houseType)) {
            return PAY_FOR_BEST_IN_UP;
        } else if (HOUSE_TYPE_BEST_IN_DOWN.equals(houseType)) {
            return PAY_FOR_BEST_IN_DOWN;
        } else if (HOUSE_TYPE_BEST_IN_RIGHT.equals(houseType)) {
            return PAY_FOR_BEST_IN_RIGHT;
        } else if (HOUSE_TYPE_NONE_IN_UP.equals(houseType)) {
            return PAY_FOR_NONE_IN_UP;
        } else if (HOUSE_TYPE_NONE_IN_DOWN.equals(houseType)) {
            return PAY_FOR_NONE_IN_DOWN;
        } else if (HOUSE_TYPE_NONE_IN_RIGHT.equals(houseType)) {
            return PAY_FOR_NONE_IN_RIGHT;
        }

        return -1;
    }

    public int getPriceForSell() {

        if (HOUSE_TYPE_NORMAL_IN_UP.equals(houseType)) {
            return SELL_FOR_NORMAL_IN_UP;
        } else if (HOUSE_TYPE_NORMAL_IN_DOWN.equals(houseType)) {
            return SELL_FOR_NORMAL_IN_DOWN;
        } else if (HOUSE_TYPE_NORMAL_IN_RIGHT.equals(houseType)) {
            return SELL_FOR_NORMAL_IN_RIGHT;
        } else if (HOUSE_TYPE_GOOD_IN_UP.equals(houseType)) {
            return SELL_FOR_GOOD_IN_UP;
        } else if (HOUSE_TYPE_GOOD_IN_DOWN.equals(houseType)) {
            return SELL_FOR_GOOD_IN_DOWN;
        } else if (HOUSE_TYPE_GOOD_IN_RIGHT.equals(houseType)) {
            return SELL_FOR_GOOD_IN_RIGHT;
        } else if (HOUSE_TYPE_BEST_IN_UP.equals(houseType)) {
            return SELL_FOR_BEST_IN_UP;
        } else if (HOUSE_TYPE_BEST_IN_DOWN.equals(houseType)) {
            return SELL_FOR_BEST_IN_DOWN;
        } else if (HOUSE_TYPE_BEST_IN_RIGHT.equals(houseType)) {
            return SELL_FOR_BEST_IN_RIGHT;
        } else if (HOUSE_TYPE_NONE_IN_UP.equals(houseType)) {
            return SELL_FOR_NONE_IN_UP;
        } else if (HOUSE_TYPE_NONE_IN_DOWN.equals(houseType)) {
            return SELL_FOR_NONE_IN_DOWN;
        } else if (HOUSE_TYPE_NONE_IN_RIGHT.equals(houseType)) {
            return SELL_FOR_NONE_IN_RIGHT;
        }

        return -1;
    }

}
