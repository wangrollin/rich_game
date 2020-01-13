package com.wangrollin.rich.controller;

import com.wangrollin.rich.element.Player;
import com.wangrollin.rich.map.GameMap;

import java.util.*;

public class CommandExecutor {

    private static final String COMMAND_QUIT = "quit";
    private static final String COMMAND_HELP = "help";

    /**
     * 查询
     */
    private static final String COMMAND_QUERY = "query";

    /**
     * 使用道具
     */
    private static final String COMMAND_BLOCK = "block";
    private static final String COMMAND_ROBOT = "robot";
    private static final String COMMAND_BOMB = "bomb";

    /**
     * 卖掉房产
     */

    private static final String COMMAND_SELL = "sell";

    /**
     * 走动，执行完就跳到下一个玩家
     */
    private static final String COMMAND_STEP = "step";
    private static final String COMMAND_ROLL = "roll";

    /**
     * 测试使用
     */
    private static final String COMMAND_TOOLMAP = "toolmap";
    private static final String COMMAND_PLAYERMAP = "playermap";
    private static final String COMMAND_MAP = "map";
    private static final String COMMAND_HOUSEMAP = "housemap";

    private static int currentPlayerIndex = 0;

    private static int initialMoney = 10000;
    private static List<Player> playerList = new LinkedList<Player>();

//    static {
//        playerList.add(new Player(Player.PLAYER_NUMBER1));
//        playerList.add(new Player(Player.PLAYER_NUMBER2));
//        playerList.add(new Player(Player.PLAYER_NUMBER3));
//        playerList.add(new Player(Player.PLAYER_NUMBER4));
//    }

    public static void executeQuit() {
        System.exit(0);
    }

    public static void executeHelp() {
        System.out.println("【游戏帮助】");
        System.out.println("query     查询资产");
        System.out.println("quit      退出游戏");
        System.out.println("sell n    卖出指定位置的自己的房产");
        System.out.println("upgrade n 升级指定位置的自己的房产");
        System.out.println("roll      掷骰子");
        System.out.println("step n    测试用，向前走指定步数");
        System.out.println("map       测试用，显示叠加地图");
        System.out.println("toolmap   测试用，显示道具地图");
        System.out.println("housemap  测试用，显示房产地图");
        System.out.println("playermap 测试用，显示玩家地图");
    }

    public static Player getCurrentPlayer() {
        return playerList.get(currentPlayerIndex);
    }

    public static void executeQuery() {
        //todo
        Player player = getCurrentPlayer();
        System.out.println("【玩家信息】");
        System.out.println("玩家名称： " + player.getPlayerNum());
        System.out.println("玩家符号： " + player.getPicture());
        System.out.println("玩家当前位置： " + player.getLocation());
        System.out.println("玩家剩余金币数： " + player.getMoney());
        System.out.println("玩家剩余点数： " + player.getPoint());
        System.out.println("剩余财神轮数： " + player.getFreePassCount());
        System.out.println("炸弹数量： " + player.getBombToolCount());
        System.out.println("障碍数量： " + player.getBarrierToolCount());
        System.out.println("机器娃娃数量： " + player.getRobotToolCount());
        System.out.println("留在医院的轮数：" + player.getStayInHospitalCount());
        System.out.println("留在监狱的轮数： " + player.getStayInPrisonCount());
        System.out.println("拥有的空地地址： " + player.getNoneStr());
        System.out.println("拥有的茅屋地址： " + player.getNormalStr());
        System.out.println("拥有的洋房地址： " + player.getGoodStr());
        System.out.println("拥有的摩天楼地址： " + player.getBestStr());
    }

    public static void executeBlock(int offset) {
        if (offset > 10 || offset < -10) {
            System.out.println("只能将道具放置在前后十步的距离！");
            return;
        }

        if (getCurrentPlayer().hasBarrier()) {
            int targetLocation = GameMap.moveTo(getCurrentPlayer().getLocation(), offset);

            if (GameMap.canSetToolHere(targetLocation)) {

                getCurrentPlayer().useBarrier(offset);
                System.out.println();
                GameMap.printMap();
                System.out.println();
            } else {
                System.out.println("【" + targetLocation + "】位置有特殊建筑、道具或者玩家，无法在此处放置路障!");
            }
        } else {
            System.out.println("你没有路障道具，无法放置路障！");
        }
    }

    public static void executeBomb(int offset) {
        if (offset > 10 || offset < -10) {
            System.out.println("只能将道具放置在前后十步的距离！");
            return;
        }
        if (getCurrentPlayer().hasBomb()) {
            int targetLocation = GameMap.moveTo(getCurrentPlayer().getLocation(), offset);
            if (GameMap.canSetToolHere(targetLocation)) {
                getCurrentPlayer().useBomb(offset);
                System.out.println();
                GameMap.printMap();
                System.out.println();
            } else {
                System.out.println("【" + targetLocation + "】位置有特殊建筑、道具或者玩家，无法在此处放置炸弹!");
            }
        } else {
            System.out.println("你没有炸弹道具，无法放置炸弹！");
        }
    }

    public static void executeSell(int location) {
        if (getCurrentPlayer().hasHouseInHere(location)) {
            if(GameMap.getElement(location).getHouse().isNone())
            {
                System.out.println("此处为你的空地,没有房产,无法售卖!");
            }
            else
            {
                getCurrentPlayer().sell(location);
                System.out.println();
                GameMap.printMap();
                System.out.println();
            }
        } else {
            System.out.println("你不拥有【" + location + "】位置，无法执行卖房产操作。");
        }
    }

    public static void payForPass() {
        Player player = getCurrentPlayer();
        if (GameMap.getElement(getCurrentPlayer().getLocation()).getHouse() != null
                && GameMap.getElement(getCurrentPlayer().getLocation()).getHouse().getOwner() != getCurrentPlayer().getPlayerNum()) {
            if (GameMap.isInHospital(GameMap.getElement(getCurrentPlayer().getLocation()).getHouse().getOwner())) {
                System.out.println("房产主人在医院，免去过路费");
            } else if (GameMap.isInPrison(GameMap.getElement(getCurrentPlayer().getLocation()).getHouse().getOwner())) {
                System.out.println("房产主人在监狱，免去过路费");
            } else {
                getCurrentPlayer().passHouseByFreeOrMoney();
            }

        }
    }

    public static void getPointInMineifCan() {
        if (GameMap.isInMine(getCurrentPlayer().getLocation())) {
            int point = GameMap.getPointInMine(getCurrentPlayer().getLocation());
            getCurrentPlayer().earnPoint(point);
            System.out.println("在矿地获取到" + point + "点数");
        }
    }

    public static void executeToolmap()
    {
        System.out.println("[道具地图]");
        GameMap.printMapForTool();
    }

    public static void executeMap()
    {
        System.out.println("[叠加地图]");
        GameMap.printMap();
    }

    public static void executeHousemap()
    {
        System.out.println("[房产地图]");
        GameMap.printMapForHouse();
    }

    public static void executePlayermap()
    {
        System.out.println("[玩家地图]");
        GameMap.printMapForPlayer();
    }

    public static boolean isNeedStayInHospital() {
        if (getCurrentPlayer().getStayInHospitalCount() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNeedStayInPrison() {
        if (getCurrentPlayer().getStayInPrisonCount() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isStopInPrison() {
        if (getCurrentPlayer().getLocation() == GameMap.LOCATION_PRISON) {
            return true;
        } else {
            return false;
        }
    }

    public static void setPlayerForPrison() {
        getCurrentPlayer().beReadyForPrison();
    }

    public static void executeStayInHospital() {
        getCurrentPlayer().stayInHospitalOnce();
    }

    public static void executeStayInPrison() {
        getCurrentPlayer().stayInPrisonOnce();
    }


    private static void turnToNextPlayer() {
        if (currentPlayerIndex == playerList.size() - 1) {
            currentPlayerIndex = 0;
        } else {
            currentPlayerIndex++;
        }
        System.out.println("轮到玩家： " + getCurrentPlayer().getPlayerNum());
    }

    public static void askForInitialMoney() {
        while(true)
        {
            System.out.print("请输入玩家初始的金币数量： ");
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();

            if (COMMAND_QUIT.equals(str.toLowerCase())) {
                executeQuit();
            } else if (COMMAND_HELP.equals(str.toLowerCase())) {
                executeHelp();
                continue;
            }

            if ("".equals(str)) {
                System.out.println("玩家的初始金币数量为10000！");
                return;
            }
            int money = Integer.parseInt(str);
            if(money<1000 || money>50000)
            {
                System.out.println("玩家的初始资金范围为1000~50000！");
                continue;
            }
            initialMoney = money;

            return;
        }

    }

    public static void askForPlayerNum() {
        while (true) {
            System.out.print("请选择2~4位不重复玩家，输入编号即可。（1.钱夫人；2.阿土伯；3.孙小美；4.金贝贝）： ");
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();

            if (COMMAND_QUIT.equals(str.toLowerCase())) {
                executeQuit();
            } else if (COMMAND_HELP.equals(str.toLowerCase())) {
                executeHelp();
                continue;
            }

            if (str.length() > 4 || str.length() < 2) {
                System.out.println("玩家数量只能为2、3或者4！");
            } else {
                //todo 去重
                for (int i = 0; i < str.length(); i++) {
                    if (str.charAt(i) == '1') {
                        playerList.add(new Player(Player.PLAYER_NUMBER1));
                    } else if (str.charAt(i) == '2') {
                        playerList.add(new Player(Player.PLAYER_NUMBER2));
                    } else if (str.charAt(i) == '3') {
                        playerList.add(new Player(Player.PLAYER_NUMBER3));
                    } else if (str.charAt(i) == '4') {
                        playerList.add(new Player(Player.PLAYER_NUMBER4));
                    }
                }
                GameMap.getElement(0).setPlayerList(playerList);
                break;
            }
        }

        for (Player player : playerList) {
            player.initeMoney(initialMoney);
        }

        System.out.println("游戏过程中可以输入help命令来获取游戏帮助，地图如下，游戏愉快！");
        System.out.println();
    }

    public static void executeStep(int step) {
        Player player = getCurrentPlayer();
        System.out.println(player.getPlayerNum() + "计划前进" + step + "步!");
        player.goForword(step);
    }

    public static void executeRoll() {
        Random random = new Random();
        int step = random.nextInt(5) + 1;
        Player player = getCurrentPlayer();
        System.out.println(player.getPlayerNum() + "计划前进" + step + "步!");
        player.goForword(step);
    }

    public static List<Player> getPlayerList() {
        return playerList;
    }

    public static boolean isInToolShop() {
        Player player = getCurrentPlayer();
        if (player.getLocation() == GameMap.LOCATION_TOOL) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean canUpgradeHouse() {
        return getCurrentPlayer().canUpgradeHouseInHere();
    }

    public static void executeUpgrade() {
        System.out.print(getCurrentPlayer().getPlayerNum() + " > ");
        System.out.println("【可以升级房产】");
        Scanner scanner = new Scanner(System.in);
        String commandStr;

        while (true) {
            System.out.println("是否升级该处地产，" + GameMap.getElement(getCurrentPlayer().getLocation()).getHouse().getPriceForUpgrade() + "元（Y/N）？");
            commandStr = scanner.nextLine();
            if ("Y".equals(commandStr) || "y".equals(commandStr)) {
                getCurrentPlayer().upgradeHouseInHere();
                System.out.println();
                GameMap.printMap();
                System.out.println();
                break;
            } else if ("N".equals(commandStr) || "n".equals(commandStr)) {
                break;
            } else {
                System.out.println("命令【" + commandStr + "】不存在！");
            }
        }
    }

    public static void executeToolShop() {
        if (!getCurrentPlayer().hasSpaceForNewTool()) {
            System.out.println("提示：你已经拥有了十个道具，无法进入道具店购买道具。");
            return;
        }
        if (getCurrentPlayer().getPoint()<30) {
            System.out.println("提示：你的点数不足买点数最少的道具，无法进入道具店购买道具。");
            return;
        }
        System.out.print(getCurrentPlayer().getPlayerNum() + " > ");
        System.out.println("欢迎光临道具屋，请选择您所需要的道具：");
        System.out.println("1. 路障 50点数");
        System.out.println("2. 机器娃娃 30点数");
        System.out.println("3. 炸弹 50点数");
        Scanner scanner = new Scanner(System.in);
        String commandStr;

        while (true) {
            System.out.println("输入对应道具编号购买，或输入命令F退出商店！");
            commandStr = scanner.nextLine();
            if ("1".equals(commandStr)) {
                if (getCurrentPlayer().hasPointForBarrier()) {
                    getCurrentPlayer().buyBarrier();
                    System.out.println("你成功购买了一个路障！");
                } else {
                    System.out.println("您当前剩余点数为" + getCurrentPlayer().getPoint() + "，不足以购买路障道具！");
                }
            } else if ("2".equals(commandStr)) {
                if (getCurrentPlayer().hasPointForRobot()) {
                    getCurrentPlayer().buyRobot();
                    System.out.println("你成功购买了一个机器娃娃！");
                } else {
                    System.out.println("您当前剩余点数为" + getCurrentPlayer().getPoint() + "，不足以购买机器娃娃道具！");
                }
            } else if ("3".equals(commandStr)) {
                if (getCurrentPlayer().hasPointForBomb()) {
                    getCurrentPlayer().buyBomb();
                    System.out.println("你成功购买了一个炸弹！");
                } else {
                    System.out.println("您当前剩余点数为" + getCurrentPlayer().getPoint() + "，不足以购买炸弹道具！");
                }
            } else if ("F".equals(commandStr) || "f".equals(commandStr)) {
                break;
            } else {
                System.out.println("命令【" + commandStr + "】不存在！");
            }
        }
    }

    public static boolean isInGiftHouse() {
        Player player = getCurrentPlayer();
        if (player.getLocation() == GameMap.LOCATION_GIFT) {
            return true;
        } else {
            return false;
        }
    }

    public static void executeGiftHouse() {
        System.out.print(getCurrentPlayer().getPlayerNum() + " > ");
        System.out.println("欢迎光临礼品屋，请选择一件您喜欢的礼品：");
        System.out.println("1. 奖金：2000");
        System.out.println("2. 点数卡：200");
        System.out.println("3. 财神：免过路费5次");
        Scanner scanner = new Scanner(System.in);
        String commandStr;

        while (true) {
            System.out.println("输入对应礼品编号获取！");
            commandStr = scanner.nextLine();
            if ("1".equals(commandStr)) {
                getCurrentPlayer().obtainMoneyInGiftHouse();
                System.out.println("你获得礼品：【奖金：2000】");
                break;
            } else if ("2".equals(commandStr)) {
                getCurrentPlayer().obtainPointInGiftHouse();
                System.out.println("你获得礼品：【点数卡：200】");
                break;
            } else if ("3".equals(commandStr)) {
                getCurrentPlayer().obtainGodInGiftHouse();
                System.out.println("你获得礼品：【财神：免过路费5次】");
                break;
            } else {
                System.out.println("礼品编号【" + commandStr + "】不存在，视为放弃此次选择，退出礼品屋！");
                break;
            }
        }
    }

    public static boolean canBuyland() {
        return getCurrentPlayer().canBuyPlace();
    }

    public static void executeBuyLand() {
        System.out.print(getCurrentPlayer().getPlayerNum() + " > ");
        System.out.println("【此地可买】");
        Scanner scanner = new Scanner(System.in);
        String commandStr;

        while (true) {
            System.out.println("是否购买该处空地，" + GameMap.getInitialHousePriceHere(getCurrentPlayer().getLocation()) + "元（Y/N）？");
            commandStr = scanner.nextLine();
            if ("Y".equals(commandStr) || "y".equals(commandStr)) {
                getCurrentPlayer().buyPlace();
                System.out.println("当前剩余金币: " + getCurrentPlayer().getMoney());
                break;
            } else if ("N".equals(commandStr) || "n".equals(commandStr)) {
                break;
            } else {
                System.out.println("命令【" + commandStr + "】不存在！");
            }
        }
    }

    public static void executeRobot() {
        if (getCurrentPlayer().hasRobot()) {
            getCurrentPlayer().useRobot();
            System.out.println();
            GameMap.printMap();
            System.out.println();
        } else {
            System.out.println("你没有机器娃娃道具，无法执行操作！");
        }
    }

    public static void checkPlayerMoneyAndGameOverIfCan() {
        if (getCurrentPlayer().getMoney() < 0) {
            System.out.println("【" + getCurrentPlayer().getPlayerNum() + "】因破产淘汰出局！");
            playerList.remove(currentPlayerIndex);
            if (playerList.size() == 1) {
                System.out.println("恭喜玩家【" + playerList.get(0).getPlayerNum() + "】获取了本次大富翁游戏的胜利！");
                System.exit(0);
            }

            if (currentPlayerIndex == 0 || currentPlayerIndex >= playerList.size()) {
                currentPlayerIndex = playerList.size() - 1;
            } else {
                currentPlayerIndex--;
            }

        }

    }

    public static void executeACommand() {
        Player player = getCurrentPlayer();
        System.out.println(player.getPlayerNum() + "的位置为: " + player.getLocation());

        /**
         * 阶段1 财神,住院,蹲监狱
         */
        player.spendAFreePass();

        if (isNeedStayInPrison()) {
            executeStayInPrison();
            System.out.println(player.getPlayerNum() + "被关在监狱，无法进行本轮操作，后续还需被关" + player.getStayInPrisonCount() + "轮！");
            turnToNextPlayer();
            return;
        }

        if (isNeedStayInHospital()) {
            executeStayInHospital();
            System.out.println(player.getPlayerNum() + "住院治疗，无法进行本轮操作，后续还需治疗" + player.getStayInHospitalCount() + "轮！");
            turnToNextPlayer();
            return;
        }


        /**
         * 阶段2 命令
         */

        while (true) {
            System.out.print(getCurrentPlayer().getPlayerNum() + " > ");
            Scanner scanner = new Scanner(System.in);
            String commandStr = scanner.nextLine();
            commandStr = commandStr.toLowerCase();

            if (COMMAND_QUIT.equals(commandStr)) {
                executeQuit();
            } else if (COMMAND_HELP.equals(commandStr)) {
                executeHelp();
            } else if (COMMAND_QUERY.equals(commandStr)) {
                executeQuery();
            } else if (COMMAND_TOOLMAP.equals(commandStr)) {
                executeToolmap();
            } else if (COMMAND_MAP.equals(commandStr)) {
                executeMap();
            } else if (COMMAND_HOUSEMAP.equals(commandStr)) {
                executeHousemap();
            } else if (COMMAND_PLAYERMAP.equals(commandStr)) {
                executePlayermap();
            }


            else if (COMMAND_SELL.equals(commandStr.split(" ")[0])) {

                try {
                    executeSell(Integer.parseInt(commandStr.split(" ")[1]));
                }
                catch (Exception e)
                {
                    System.out.println("命令["+commandStr+"]不合法!");
                }
                break;



            } else if (COMMAND_BLOCK.equals(commandStr.split(" ")[0])) {


                try {
                    executeBlock(Integer.parseInt(commandStr.split(" ")[1]));
                }
                catch (Exception e)
                {
                    System.out.println("命令["+commandStr+"]不合法!");
                }
                break;


            } else if (COMMAND_BOMB.equals(commandStr.split(" ")[0])) {
                try {
                    executeBomb(Integer.parseInt(commandStr.split(" ")[1]));
                }
                catch (Exception e)
                {
                    System.out.println("命令["+commandStr+"]不合法!");
                }
                break;




            }

            else if (COMMAND_STEP.equals(commandStr.split(" ")[0])) {
                try {
                    executeStep(Integer.parseInt(commandStr.split(" ")[1]));
                }
                catch (Exception e)
                {
                    System.out.println("命令["+commandStr+"]不合法!");
                }
                break;
            }



            else if (COMMAND_ROBOT.equals(commandStr)) {
                executeRobot();
            }

            else if (COMMAND_ROLL.equals(commandStr)) {
                executeRoll();
                break;
            } else {
                System.out.println("没有【" + commandStr + "】命令！");
            }
        }

        if (isStopInPrison()) {
            setPlayerForPrison();
            System.out.println("运气不好，走进了监狱！");
        }

        /**
         * 阶段3 显示地图
         */

        System.out.println();
        GameMap.printMap();
        System.out.println();

        /**
         * 阶段4 后续处理
         */
        if (isInToolShop()) {
            executeToolShop();
        } else if (isInGiftHouse()) {
            executeGiftHouse();
        } else if (canBuyland()) {
            executeBuyLand();
        } else if (canUpgradeHouse()) {
            executeUpgrade();
        }

        payForPass();
        checkPlayerMoneyAndGameOverIfCan();
        getPointInMineifCan();

        /**
         * 切换玩家
         */
        turnToNextPlayer();
    }

    public static void executeACommandOld() {
        Player player = getCurrentPlayer();
        System.out.println(player.getPlayerNum() + "的位置为: " + player.getLocation());

        player.spendAFreePass();

        if (isNeedStayInPrison()) {
            executeStayInPrison();
            System.out.println(player.getPlayerNum() + "被关在监狱，无法进行本轮操作，后续还需被关" + player.getStayInPrisonCount() + "轮！");
            turnToNextPlayer();
            return;
        }

        if (isNeedStayInHospital()) {
            executeStayInHospital();
            System.out.println(player.getPlayerNum() + "住院治疗，无法进行本轮操作，后续还需治疗" + player.getStayInHospitalCount() + "轮！");
            turnToNextPlayer();
            return;
        }

        if (isInToolShop()) {
            executeToolShop();
        } else if (isInGiftHouse()) {
            executeGiftHouse();
        } else if (canBuyland()) {
            executeBuyLand();
        } else if (canUpgradeHouse()) {
            executeUpgrade();
        }

        payForPass();
        checkPlayerMoneyAndGameOverIfCan();
        getPointInMineifCan();

        while (true) {
            System.out.print(getCurrentPlayer().getPlayerNum() + " > ");
            Scanner scanner = new Scanner(System.in);
            String commandStr = scanner.nextLine();

            if (COMMAND_QUIT.equals(commandStr)) {
                executeQuit();
            } else if (COMMAND_HELP.equals(commandStr)) {
                executeHelp();
            } else if (COMMAND_QUERY.equals(commandStr)) {
                executeQuery();
            } else if (COMMAND_TOOLMAP.equals(commandStr)) {
                executeToolmap();
            } else if (COMMAND_MAP.equals(commandStr)) {
                executeMap();
            } else if (COMMAND_HOUSEMAP.equals(commandStr)) {
                executeHousemap();
            } else if (COMMAND_PLAYERMAP.equals(commandStr)) {
                executePlayermap();
            } else if (COMMAND_SELL.equals(commandStr.split(" ")[0])) {
                executeSell(Integer.parseInt(commandStr.split(" ")[1]));
            } else if (COMMAND_BLOCK.equals(commandStr.split(" ")[0])) {
                System.out.println("executeBlock()");
                executeBlock(Integer.parseInt(commandStr.split(" ")[1]));
            } else if (COMMAND_ROBOT.equals(commandStr)) {
                executeRobot();
            } else if (COMMAND_BOMB.equals(commandStr.split(" ")[0])) {
                System.out.println("executeBomb()");
                executeBomb(Integer.parseInt(commandStr.split(" ")[1]));
            } else if (COMMAND_STEP.equals(commandStr.split(" ")[0])) {
                executeStep(Integer.parseInt(commandStr.split(" ")[1]));
                break;
            } else if (COMMAND_ROLL.equals(commandStr)) {
                executeRoll();
                break;
            } else {
                System.out.println("没有【" + commandStr + "】命令！");
            }
        }

        if (isStopInPrison()) {
            setPlayerForPrison();
            System.out.println("运气不好，走进了监狱！");
        }

        /**
         * 展示地图
         */

        System.out.println();
        GameMap.printMap();
        System.out.println();
        turnToNextPlayer();

    }


}
