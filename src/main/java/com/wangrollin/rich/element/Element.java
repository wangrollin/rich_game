package com.wangrollin.rich.element;

import java.util.LinkedList;
import java.util.List;

public class Element {

    private Place place;

    private House house = null;

    private Tool tool = null;

    private List<Player> playerList = new LinkedList<Player>();

    private String picture = null;

    public Element(String placeType) {
        place = new Place(placeType);
    }

    public String calcuatePicture() {
        if (!playerList.isEmpty()) {
            Player player = playerList.get(playerList.size() - 1);
            picture = player.getPicture();
        } else if (tool != null) {
            picture = tool.getPicture();
        } else if (house != null) {
            picture = house.getPicture();
        } else {
            if (Place.NORMAL_PLACE_TYPE_IN_UP.equals(place.getPlaceType())
                    || Place.NORMAL_TYPE_IN_DOWN.equals(place.getPlaceType())
                    || Place.NORMAL_TYPE_IN_RIGHT.equals(place.getPlaceType())) {
                picture = "0";
            } else if (Place.SPECIAL_PLACE_TYPE_HOSPITAL.equals(place.getPlaceType())) {
                picture = "H";
            } else if (Place.SPECIAL_PLACE_TYPE_PRISON.equals(place.getPlaceType())) {
                picture = "P";
            } else if (Place.SPECIAL_PLACE_TYPE_MAGIC.equals(place.getPlaceType())) {
                picture = "M";
            } else if (Place.SPECIAL_PLACE_TYPE_TOOL.equals(place.getPlaceType())) {
                picture = "T";
            } else if (Place.SPECIAL_PLACE_TYPE_START.equals(place.getPlaceType())) {
                picture = "S";
            } else if (Place.SPECIAL_PLACE_TYPE_GIFT.equals(place.getPlaceType())) {
                picture = "G";
            } else if (Place.SPECIAL_PLACE_TYPE_MINE.equals(place.getPlaceType())) {
                picture = "$";
            } else if (Place.PLACE_FOR_NOTHING.equals(place.getPlaceType())) {
                picture = " ";
            }
        }
        return null;
    }

    public String getPictureForNow() {
        calcuatePicture();
        return picture;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public void setPlayerList(List<Player> playerList) {
        for (Player player : playerList) {
            this.playerList.add(player.createSamePlayer());
        }
    }

    public House getHouse() {
        return house;
    }

    public Place getPlace() {
        return place;
    }

    public Tool getTool() {
        return tool;
    }


}
