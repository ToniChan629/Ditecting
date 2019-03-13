package com.neu.diting.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Toni Chan on 2018/8/11
 */
public interface HoneypotService {
    List findAll();

    List ranking();

    List pieChart();

    List heatMap(String protocol);

//    List pointChart();

    List threeDChart();

    List sankey();

    List log();

    int deployTime(String protocol);

    List percentAge(String protocol);

    List flow(String protocol);

    Map logInformation(int id);

    List logPointMap(int id);

    Map ipAttackTime(int id);

    List ipCircleAttack(int id);

    List ipLineAttack(int id);

    List protocolAttack();

    Map honeyGrow();

    Map deviceCPU(String protocol);

    Map deviceNIC(String protocol);

    int setNetwork(String[] infor) throws IOException;

    Map selectSet(String protocol);
}
