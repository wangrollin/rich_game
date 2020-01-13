package com.wangrollin.rich.map;

import com.wangrollin.rich.element.Element;
import com.wangrollin.rich.element.House;
import com.wangrollin.rich.element.Place;
import com.wangrollin.rich.element.Player;

import java.util.List;

public class GameMap {

    public static final int LOCATION_MAX = 70;

    public static final int LOCATION_START = 0;
    public static final int LOCATION_HOSPITAL = 14;
    public static final int LOCATION_TOOL = 28;
    public static final int LOCATION_GIFT = 35;
    public static final int LOCATION_PRISON = 49;
    public static final int LOCATION_MAGIC = 63;

    public static final int LOCATION_MINE_START = 64;
    public static final int LOCATION_MINE_END = 69;

    public static final int INITIAL_PRICE_FOR_UP = 200;
    public static final int INITIAL_PRICE_FOR_RIGHT = 500;
    public static final int INITIAL_PRICE_FOR_DOWN = 300;

//    public static final int LOCATION_MINE_END = 69;
//    public static final int LOCATION_MINE_END = 69;
//
//    public static final int LOCATION_MINE_END = 69;
//    public static final int LOCATION_MINE_END = 69;
//
//    public static final int LOCATION_MINE_END = 69;
//    public static final int LOCATION_MINE_END = 69;


    private static Element[][] MAP = new Element[8][29];

    static {
        for (int j = 0; j < 29; j++) {
            MAP[0][j] = new Element(Place.NORMAL_PLACE_TYPE_IN_UP);
            MAP[7][j] = new Element(Place.NORMAL_TYPE_IN_DOWN);
        }

        for (int i = 1; i < 7; ++i) {
            for (int j = 1; j < 28; j++) {
                MAP[i][j] = new Element(Place.PLACE_FOR_NOTHING);
            }
            MAP[i][0] = new Element(Place.SPECIAL_PLACE_TYPE_MINE);
            MAP[i][28] = new Element(Place.NORMAL_TYPE_IN_RIGHT);
        }

        MAP[0][0] = new Element(Place.SPECIAL_PLACE_TYPE_START);
        MAP[0][14] = new Element(Place.SPECIAL_PLACE_TYPE_HOSPITAL);
        MAP[0][28] = new Element(Place.SPECIAL_PLACE_TYPE_TOOL);
        MAP[7][0] = new Element(Place.SPECIAL_PLACE_TYPE_MAGIC);
        MAP[7][14] = new Element(Place.SPECIAL_PLACE_TYPE_PRISON);
        MAP[7][28] = new Element(Place.SPECIAL_PLACE_TYPE_GIFT);

    }

//    public static void removeTool(String toolType);
//    public static void addTool(String toolType);
//
//    public static void addHouse(int location);
//    public static void upgradeHouse(int location);
//    public static void removeHouse(int location);
//
//    public static void removePlayer(int playerNum);
//    public static void addPlayer(int playerNum);

    public static void printMap() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 29; j++) {
                System.out.print(MAP[i][j].getPictureForNow());
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static void printMapForPlayer() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 29; j++) {
                List<Player> playerList = MAP[i][j].getPlayerList();
                if(playerList.size() == 0)
                {
                    System.out.print(". ");
                }
                else
                {
                    System.out.print(playerList.get(playerList.size()-1).getPicture()+ " ");
                }
//                System.out.print(MAP[i][j].getPictureForNow());
//                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static void printMapForTool() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 29; j++) {
                System.out.print(MAP[i][j].getTool()==null ? ". " : MAP[i][j].getTool().getPicture()+ " ");
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static void printMapForHouse() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 29; j++) {
                System.out.print(MAP[i][j].getHouse()==null ? ". " : MAP[i][j].getHouse().getPicture()+" ");
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static int moveTo(int currentLocation, int step) {
        currentLocation = currentLocation + step;
        if (currentLocation >= LOCATION_MAX) {
            currentLocation = currentLocation % LOCATION_MAX;
            return currentLocation;
        } else if (currentLocation < 0) {
            int temp = currentLocation * -1;
            currentLocation = LOCATION_MAX - temp % LOCATION_MAX;
            return currentLocation;
        } else {
            return currentLocation;
        }
    }

    public static Element[][] getMAP() {
        return MAP;
    }

    public static Element getElement(int location) {
        if (LOCATION_START <= location && location <= LOCATION_TOOL) {
            return MAP[0][location];
        } else if (LOCATION_GIFT <= location && location <= LOCATION_MAGIC) {
            return MAP[7][28 - (location - 35)];
        } else if (LOCATION_MAGIC + 1 <= location && location <= LOCATION_MAX - 1) {
            return MAP[7 - (location - 63)][0];
        } else {
            return MAP[location - 28][28];
        }
    }

    public static boolean canBuyThis(int location) {
        Place place = getElement(location).getPlace();
        if (place.getPlaceType() == Place.NORMAL_PLACE_TYPE_IN_UP
                || place.getPlaceType() == Place.NORMAL_TYPE_IN_DOWN
                || place.getPlaceType() == Place.NORMAL_TYPE_IN_RIGHT) {
            if (getElement(location).getHouse() == null) {
                return true;
            }
        }
        return false;
    }

    public static String getInitialHouseTypeHere(int location) {
        Place place = getElement(location).getPlace();
        if (place.getPlaceType() == Place.NORMAL_PLACE_TYPE_IN_UP) {
            return House.HOUSE_TYPE_NONE_IN_UP;
        } else if (place.getPlaceType() == Place.NORMAL_TYPE_IN_DOWN) {
            return House.HOUSE_TYPE_NONE_IN_DOWN;
        } else if (place.getPlaceType() == Place.NORMAL_TYPE_IN_RIGHT) {
            return House.HOUSE_TYPE_NONE_IN_RIGHT;
        }
        return null;
    }

    public static int getInitialHousePriceHere(int location) {
        Place place = getElement(location).getPlace();
        if (place.getPlaceType() == Place.NORMAL_PLACE_TYPE_IN_UP) {
            return INITIAL_PRICE_FOR_UP;
        } else if (place.getPlaceType() == Place.NORMAL_TYPE_IN_DOWN) {
            return INITIAL_PRICE_FOR_DOWN;
        } else if (place.getPlaceType() == Place.NORMAL_TYPE_IN_RIGHT) {
            return INITIAL_PRICE_FOR_RIGHT;
        }

        return 0;
    }

    public static boolean canSetToolHere(int location) {
        if (getElement(location).getTool() != null) {
            return false;
        }
        if(hasPlayerHere(location))
        {
            return false;
        }
        if (location == LOCATION_HOSPITAL || location == LOCATION_TOOL
                || location == LOCATION_GIFT || location == LOCATION_PRISON
                || location == LOCATION_MAGIC) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isInMine(int location) {
        if (location >= LOCATION_MINE_START && location <= LOCATION_MINE_END) {
            return true;
        } else {
            return false;
        }
    }

    public static int getPointInMine(int location) {
        if (location == 64) {
            return 20;
        } else if (location == 65) {
            return 80;
        } else if (location == 66) {
            return 100;
        } else if (location == 67) {
            return 40;
        } else if (location == 68) {
            return 80;
        } else {
            return 60;
        }
    }

    public static boolean isInHospital(String playerNum) {
        for (Player player : getElement(LOCATION_HOSPITAL).getPlayerList()) {
            if (player.getPlayerNum() == playerNum) {
                return true;
            }
        }

        return false;
    }

    public static boolean isInPrison(String playerNum) {
        for (Player player : getElement(LOCATION_PRISON).getPlayerList()) {
            if (player.getPlayerNum() == playerNum) {
                return true;
            }
        }

        return false;
    }

    public static void removeHouseForPlayer(String playerNum)
    {
        for(int i = GameMap.LOCATION_START; i< GameMap.LOCATION_MAX ; ++i)
        {
            if(GameMap.getElement(i).getHouse()!=null
                    && GameMap.getElement(i).getHouse().getOwner() == playerNum)
            {
                GameMap.getElement(i).setHouse(null);
            }
        }
    }

    public static boolean hasPlayerHere(int location)
    {
        if(GameMap.getElement(location).getPlayerList().size() == 0)
        {
            return false;
        }
        else {
            return true;
        }

    }

}
