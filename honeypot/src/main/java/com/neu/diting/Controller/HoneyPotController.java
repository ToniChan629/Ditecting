package com.neu.diting.Controller;

import com.neu.diting.Service.HoneypotService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/information")
public class HoneyPotController {
    @Autowired
    HoneypotService honeypotService;

    //查询蜜罐的所有信息
    @RequestMapping("/findHoneypotInfor")
    public List findAllHoneypotInfor(){
        List dataList = honeypotService.findAll();
        return dataList;
    }

    //显示国家排名,前五位，降序
    @RequestMapping("/ranking")
    public List ranking(){
        List dataList = honeypotService.ranking();
        return dataList;
    }

    //饼图
    @RequestMapping("/pieChart")
    public List pieChart(){
        List dataList =  honeypotService.pieChart();
        return dataList;
    }

    //热力图
    @RequestMapping("/heatMap")
    public List heatMap(@RequestBody JSONObject json){
        String protocol = json.containsKey("protocol")?json.getString("protocol"):null;
        List dataList =  honeypotService.heatMap(protocol);
        return dataList;
    }

    //点线图
//    @RequestMapping("/pointChart")
//    public List pointChart(){
//        List dataList =  honeypotService.pointChart();
//        return dataList;
//    }

    //3D图
    @RequestMapping("/3DChart")
    public List threeDChart(){
        List dataList = honeypotService.threeDChart();
        return dataList;
    }

    //桑基图
    @RequestMapping("/sankey")
    public List sankey(){
        List dataList = honeypotService.sankey();
        return dataList;
    }

    //日志
    @RequestMapping("/log")
    public List log(){
        List dataList = honeypotService.log();
        return dataList;
    }

    //蜜罐部署时间
    @RequestMapping("/deployTime")
    public int deployTime(@RequestBody JSONObject json){
        String protocol = json.containsKey("protocol")?json.getString("protocol"):null;
        int dataList = honeypotService.deployTime(protocol);
        return dataList;
    }

    //国家攻击量占比
    @RequestMapping("/percent")
    public List percentAge(@RequestBody JSONObject json){
        String protocol = json.containsKey("protocol")?json.getString("protocol"):null;
        List dataList = honeypotService.percentAge(protocol);
        return dataList;
    }

    //近七天流量状态
    @RequestMapping("/flow")
    public List flowThisMonth(@RequestBody JSONObject json){
        String protocol = json.containsKey("protocol")?json.getString("protocol"):null;
        List dataList = honeypotService.flow(protocol);
        return dataList;
    }

    //日志详细信息
    @RequestMapping("/logInformation")
    public Map logInformation(@RequestBody JSONObject json){
        int id = json.containsKey("id")?json.getInt("id"):null;
        Map dataList = honeypotService.logInformation(id);
        return dataList;
    }

    //日志点线图
    @RequestMapping("/logPointMap")
    public List logPointMap(@RequestBody JSONObject json){
        int id = json.containsKey("id")?json.getInt("id"):null;
        List dataList = honeypotService.logPointMap(id);
        return dataList;
    }

    //IP持续攻击时间:计算某一条ip第一次出现到现在的时间差
    @RequestMapping("/ipAttackTime")
    public Map ipAttackTime(@RequestBody JSONObject json){
        int id = json.containsKey("id")?json.getInt("id"):null;
        Map dataList = honeypotService.ipAttackTime(id);
        return dataList;
    }

    //IP周期性攻击:计算某一条ip每个月的攻击量，取最近六个月
    @RequestMapping("/ipCircleAttack")
    public List ipCircleAttack(@RequestBody JSONObject json){
        int id = json.containsKey("id")?json.getInt("id"):null;
        List dataList = honeypotService.ipCircleAttack(id);
        return dataList;
    }

    //某个IP一年的攻击量:精确到天
    @RequestMapping("/ipLineAttack")
    public List ipLineAttack(@RequestBody JSONObject json){
        int id = json.containsKey("id")?json.getInt("id"):null;
        List dataList = honeypotService.ipLineAttack(id);
        return dataList;
    }

    //六种协议分月攻击量，协议顺序:'s7','modbus','DNP3','Ethernet/IP','atg','Fins'
    @RequestMapping("/protocolAttack")
    public List protocolAttack(){
        List dataList = honeypotService.protocolAttack();
        return dataList;
    }

    //从2018-01开始按照月份区分每个协议的流量值
    @RequestMapping("/honeyGrow")
    public Map honeyGrow(){
        Map dataList = honeypotService.honeyGrow();
        return dataList;
    }

    @RequestMapping("/deviceCPU")
    public Map deviceCPU(@RequestBody JSONObject json){
        String protocol = json.containsKey("protocol")?json.getString("protocol"):null;
        Map dataList = honeypotService.deviceCPU(protocol);
        return dataList;
    }

    @RequestMapping("/deviceNIC")
    public Map deviceNIC(@RequestBody JSONObject json){
        String protocol = json.containsKey("protocol")?json.getString("protocol"):null;
        Map dataList = honeypotService.deviceNIC(protocol);
        return dataList;
    }

    @RequestMapping("/selectSet")
    public Map selectSet(@RequestBody JSONObject json) throws IOException {
        String protocol = json.containsKey("protocol")?json.getString("protocol"):null;
        Map result = honeypotService.selectSet(protocol);
        return result;
    }

    @RequestMapping("/setNetwork")
    public int setNetwork(@RequestBody JSONObject json) throws IOException {
        String[] infor = new String[]{"","","",""};
        infor[0] = json.containsKey("protocol")?json.getString("protocol"):null;
        infor[1] = json.containsKey("ip")?json.getString("ip"):null;
        infor[2] = json.containsKey("mask")?json.getString("mask"):null;
        infor[3] = json.containsKey("gateway")?json.getString("gateway"):null;
        int result =  honeypotService.setNetwork(infor);
        return result;
    }
}
