package com.wangrollin.rich.main;

import com.wangrollin.rich.controller.CommandExecutor;
import com.wangrollin.rich.map.GameMap;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        runTheGame();
    }

    public static void runTheGame() {

        /**
         * 游戏初始化
         */
        CommandExecutor.askForInitialMoney();
        CommandExecutor.askForPlayerNum();

        /**
         * 展示地图
         */
        GameMap.printMap();

        while (true) {

            /**
             * 某个玩家操作一轮
             */

            System.out.println();
            System.out.println("-----------------------------");
            System.out.println();

            CommandExecutor.executeACommand();
        }

    }


}
