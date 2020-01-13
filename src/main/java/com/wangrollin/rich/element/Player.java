package com.wangrollin.rich.element;

import com.wangrollin.rich.map.GameMap;

import java.util.List;

public class Player {
//    public static final String PLAYER_NUMBER1 = "PLAYER_NUMBER1";
//    public static final String PLAYER_NUMBER2 = "PLAYER_NUMBER2";
//    public static final String PLAYER_NUMBER3 = "PLAYER_NUMBER3";
//    public static final String PLAYER_NUMBER4 = "PLAYER_NUMBER4";

    public static final String PLAYER_NUMBER1 = "钱夫人";
    public static final String PLAYER_NUMBER2 = "阿土伯";
    public static final String PLAYER_NUMBER3 = "孙小美";
    public static final String PLAYER_NUMBER4 = "金贝贝";

    public static final String PLAYER_STATE_NORMAL = "PLAYER_STATE_NORMAL";
    public static final String PLAYER_STATE_IN_HOSPITAL = "PLAYER_STATE_IN_HOSPITAL";
    public static final String PLAYER_STATE_IN_PRISON = "PLAYER_STATE_IN_PRISON";
    public static final String PLAYER_STATE_IN_GAMEOVER = "PLAYER_STATE_IN_GAMEOVER";

    private static final int MAX_TOOL_NUMBER = 10;

//    private static final Player player1 = new Player(PLAYER_NUMBER1);
//    private static final Player player2 = new Player(PLAYER_NUMBER2);
//    private static final Player player3 = new Player(PLAYER_NUMBER3);
//    private static final Player player4 = new Player(PLAYER_NUMBER4);


    private String playerNum;
    private String picture;

    private int location = 0;

    private String playerState = PLAYER_STATE_NORMAL;

    private int money = 10000;
    private int point = 0;
    private int freePassCount = 0;

    private int bombToolCount = 0;
    private int robotToolCount = 0;
    private int barrierToolCount = 0;

    private int stayInHospitalCount = 0;
    private int stayInPrisonCount = 0;

    public Player createSamePlayer() {
        Player copy = new Player(playerNum);
        copy.location = location;
        copy.playerState = playerState;
        copy.money = money;
        copy.point = point;
        copy.freePassCount = freePassCount;
        copy.bombToolCount = bombToolCount;
        copy.robotToolCount = robotToolCount;
        copy.barrierToolCount = barrierToolCount;
        copy.stayInHospitalCount = stayInHospitalCount;
        copy.stayInPrisonCount = stayInPrisonCount;

        return copy;
    }

    public void beReadyForPrison() {
        stayInPrisonCount = 2;
        playerState = PLAYER_STATE_IN_PRISON;
    }

    public boolean hasBomb() {
        return bombToolCount != 0;
    }

    public boolean hasRobot() {
        return robotToolCount != 0;
    }

    public boolean hasBarrier() {
        return barrierToolCount != 0;
    }

    public boolean hasHouseInHere(int landLocation) {
        if (GameMap.getElement(landLocation).getHouse() != null && GameMap.getElement(landLocation).getHouse().getOwner() == playerNum) {
            return true;
        } else {
            return false;
        }
    }

    public boolean canUpgradeHouseInHere() {
        int landLocation = location;
        if (hasHouseInHere(landLocation) && GameMap.getElement(landLocation).getHouse().canUpgrade()
                && money >= GameMap.getElement(landLocation).getHouse().getPriceForUpgrade()) {
            return true;
        } else {
            return false;
        }
    }

    public void upgradeHouseInHere() {
        int landLocation = location;
        int payment = GameMap.getElement(landLocation).getHouse().getPriceForUpgrade();
        money -= payment;
        GameMap.getElement(landLocation).getHouse().upgrade();
    }

    public void useBomb(int offset) {
        int targetLocation = GameMap.moveTo(location, offset);
        GameMap.getElement(targetLocation).setTool(new Tool(Tool.TOOL_TYPE_BOMB));
        bombToolCount--;
    }

    public void useBarrier(int offset) {
        int targetLocation = GameMap.moveTo(location, offset);
        GameMap.getElement(targetLocation).setTool(new Tool(Tool.TOOL_TYPE_BARRIER));
        barrierToolCount--;
    }


    public void useRobot() {
        for (int i = 1; i < 11; i++) {
            int targetLocation = GameMap.moveTo(location, i);
            GameMap.getElement(targetLocation).setTool(null);
        }
        robotToolCount--;
        System.out.println("已经清除了[" + (location + 1) + ", " + (location + 10) + "]的炸弹和路障！");
    }

    public void sell(int houseLocation) {
        int houseMoney = GameMap.getElement(houseLocation).getHouse().getPriceForSell();
        money += houseMoney;
        GameMap.getElement(houseLocation).setHouse(null);
        System.out.println("卖房产获得" + houseMoney + "金币");
    }

    public boolean canBuyPlace() {
        if (GameMap.canBuyThis(location) && GameMap.getElement(location).getHouse() == null && money >= 200) {
            return true;
        } else {
            return false;
        }
    }

    public void obtainMoneyInGiftHouse() {
        money += 2000;
    }

    public void obtainPointInGiftHouse() {
        point += 200;
    }

    public void obtainGodInGiftHouse() {
        freePassCount += 5;
    }

    public boolean hasSpaceForNewTool() {
        return (barrierToolCount + robotToolCount + bombToolCount) < MAX_TOOL_NUMBER;
    }

    public boolean hasPointForBarrier() {
        return point >= Tool.PRICE_FOR_BARRIER;
    }

    public boolean hasPointForRobot() {
        return point >= Tool.PRICE_FOR_ROBOT;
    }

    public boolean hasPointForBomb() {
        return point >= Tool.PRICE_FOR_BOMB;
    }

    public void buyBarrier() {
        point -= Tool.PRICE_FOR_BARRIER;
        barrierToolCount++;
    }

    public void buyRobot() {
        point -= Tool.PRICE_FOR_ROBOT;
        robotToolCount++;
    }

    public void buyBomb() {
        point -= Tool.PRICE_FOR_BOMB;
        bombToolCount++;
    }

    public void buyPlace() {
        money -= GameMap.getInitialHousePriceHere(location);
        GameMap.getElement(location).setHouse(new House(playerNum, GameMap.getInitialHouseTypeHere(location)));
    }

    public void stayInPrisonOnce() {
        stayInPrisonCount--;
        if (stayInPrisonCount == 0) {
            playerState = PLAYER_STATE_NORMAL;
        }
    }

    public void stayInHospitalOnce() {
        stayInHospitalCount--;
        if (stayInPrisonCount == 0) {
            playerState = PLAYER_STATE_NORMAL;
        }
    }

    public Player(String num) {
        playerNum = num;
        if (PLAYER_NUMBER1.equals(num)) {
            picture = "Q";
        } else if (PLAYER_NUMBER2.equals(num)) {
            picture = "A";
        } else if (PLAYER_NUMBER3.equals(num)) {
            picture = "S";
        } else if (PLAYER_NUMBER4.equals(num)) {
            picture = "J";
        }
    }

    public String getPlayerNum() {
        return playerNum;
    }

    public void moveToHospital() {
        location = GameMap.LOCATION_HOSPITAL;
        playerState = PLAYER_STATE_IN_HOSPITAL;
        stayInHospitalCount = 3;
    }

    public void goForword(int step) {
        List<Player> playerList = GameMap.getElement(location).getPlayerList();

        for (int i = 1; i <= step; i++) {
            int targetLocation = GameMap.moveTo(location, i);
            if (GameMap.getElement(targetLocation).getTool() != null) {
                if (GameMap.getElement(targetLocation).getTool().getToolType() == Tool.TOOL_TYPE_BOMB) {
                    moveToHospital();

                    //原来的位置删除
                    for (int j = 0; j < playerList.size(); j++) {
                        if (playerList.get(j).getPlayerNum() == playerNum) {
                            playerList.remove(j);
                            break;
                        }
                    }

                    List<Player> playerList2 = GameMap.getElement(location).getPlayerList();
                    playerList2.add(this);

                    GameMap.getElement(targetLocation).setTool(null);
                    System.out.println("不幸被炸弹炸伤送往医院治疗！");
                    return;
                } else if (GameMap.getElement(targetLocation).getTool().getToolType() == Tool.TOOL_TYPE_BARRIER) {
                    //原来的位置删除
                    for (int j = 0; j < playerList.size(); j++) {
                        if (playerList.get(j).getPlayerNum() == playerNum) {
                            playerList.remove(j);
                            break;
                        }
                    }
                    location = GameMap.moveTo(location, i);
                    //新的地方加上
                    List<Player> playerList2 = GameMap.getElement(location).getPlayerList();
                    playerList2.add(this);

                    GameMap.getElement(targetLocation).setTool(null);
                    System.out.println("被路障挡在了【" + location + "】位置！");
                    return;
                }
            }
        }

        //原来的位置删除
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).getPlayerNum() == playerNum) {
                playerList.remove(i);
                break;
            }
        }
        location = GameMap.moveTo(location, step);
        //新的地方加上
        List<Player> playerList2 = GameMap.getElement(location).getPlayerList();
        playerList2.add(this);
    }

    public String getPicture() {
        return picture;
    }

    public void initeMoney(int initeMoney) {
        money = initeMoney;
    }

    public void earnMoney(int theMoney) {
        money += theMoney;
    }

    public void spendMoneyAndOverIfCan(int theMoney) {
        money -= theMoney;
        if (money <= 0) {
            playerState = PLAYER_STATE_IN_GAMEOVER;
        }
    }

    public void earnPoint(int thePoint) {
        point += thePoint;
    }

    public void spendPoint(int thePoint) {
        point -= thePoint;
    }

    public void getTheGodOfWealth() {
        freePassCount += 5;
    }

    public void passHouseByFreeOrMoney() {
        House house = GameMap.getElement(location).getHouse();
        if (canPassForFree()) {
            System.out.println("财神附身，可免过路费!");
        } else {
            int payment = house.getPriceForPass();
            spendMoneyAndOverIfCan(payment);
            System.out.println("缴纳过路费" + payment + "金币给"+house.getOwner()+", 剩余"+money+"金币!");
        }
    }

    public String getNoneStr()
    {
        String result = "";
        for(int i = GameMap.LOCATION_START; i< GameMap.LOCATION_MAX ; ++i)
        {
            if(GameMap.getElement(i).getHouse()!=null
                    && GameMap.getElement(i).getHouse().getOwner() == playerNum
                    &&(GameMap.getElement(i).getHouse().getHouseType() == House.HOUSE_TYPE_NONE_IN_UP
                            ||GameMap.getElement(i).getHouse().getHouseType() == House.HOUSE_TYPE_NONE_IN_RIGHT
                            ||GameMap.getElement(i).getHouse().getHouseType() == House.HOUSE_TYPE_NONE_IN_DOWN))
            {
                result = result + i + ", ";
            }
        }

        if(result.length()>=2)
        {
            result = result.substring(0,result.length()-2);
        }
        else
        {
            result = "无";
        }
        return result;
    }

    public String getNormalStr()
    {
        String result = "";
        for(int i = GameMap.LOCATION_START; i< GameMap.LOCATION_MAX ; ++i)
        {
            if(GameMap.getElement(i).getHouse()!=null
                    && GameMap.getElement(i).getHouse().getOwner() == playerNum
                    &&(GameMap.getElement(i).getHouse().getHouseType() == House.HOUSE_TYPE_NORMAL_IN_UP
                    ||GameMap.getElement(i).getHouse().getHouseType() == House.HOUSE_TYPE_NORMAL_IN_RIGHT
                    ||GameMap.getElement(i).getHouse().getHouseType() == House.HOUSE_TYPE_NORMAL_IN_DOWN))
            {
                result = result + i + ", ";
            }
        }

        if(result.length()>=2)
        {
            result = result.substring(0,result.length()-2);
        }
        else
        {
            result = "无";
        }
        return result;
    }

    public String getGoodStr()
    {
        String result = "";
        for(int i = GameMap.LOCATION_START; i< GameMap.LOCATION_MAX ; ++i)
        {
            if(GameMap.getElement(i).getHouse()!=null
                    && GameMap.getElement(i).getHouse().getOwner() == playerNum
                    &&(GameMap.getElement(i).getHouse().getHouseType() == House.HOUSE_TYPE_GOOD_IN_UP
                    ||GameMap.getElement(i).getHouse().getHouseType() == House.HOUSE_TYPE_GOOD_IN_RIGHT
                    ||GameMap.getElement(i).getHouse().getHouseType() == House.HOUSE_TYPE_GOOD_IN_DOWN))
            {
                result = result + i + ", ";
            }
        }

        if(result.length()>=2)
        {
            result = result.substring(0,result.length()-2);
        }
        else
        {
            result = "无";
        }
        return result;
    }

    public String getBestStr()
    {
        String result = "";
        for(int i = GameMap.LOCATION_START; i< GameMap.LOCATION_MAX ; ++i)
        {
            if(GameMap.getElement(i).getHouse()!=null
                    && GameMap.getElement(i).getHouse().getOwner() == playerNum
                    &&(GameMap.getElement(i).getHouse().getHouseType() == House.HOUSE_TYPE_BEST_IN_UP
                    ||GameMap.getElement(i).getHouse().getHouseType() == House.HOUSE_TYPE_BEST_IN_RIGHT
                    ||GameMap.getElement(i).getHouse().getHouseType() == House.HOUSE_TYPE_BEST_IN_DOWN))
            {
                result = result + i + ", ";
            }
        }

        if(result.length()>=2)
        {
            result = result.substring(0,result.length()-2);
        }
        else
        {
            result = "无";
        }
        return result;
    }

    public int getMoney() {
        return money;
    }

    public int getPoint() {
        return point;
    }

    public int getFreePassCount() {
        return freePassCount;
    }

    public int getBombToolCount() {
        return bombToolCount;
    }

    public int getRobotToolCount() {
        return robotToolCount;
    }

    public int getBarrierToolCount() {
        return barrierToolCount;
    }

    public int getStayInHospitalCount() {
        return stayInHospitalCount;
    }

    public int getStayInPrisonCount() {
        return stayInPrisonCount;
    }

    private boolean canPassForFree() {
        return !(freePassCount == 0);
    }

    public int getLocation() {
        return location;
    }

    public String getPlayerState() {
        return playerState;
    }

    public void spendAFreePass()
    {
        if(freePassCount>0)
        {
            freePassCount--;
        }
    }
}
