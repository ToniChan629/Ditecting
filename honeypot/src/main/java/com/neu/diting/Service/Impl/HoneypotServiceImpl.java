package com.neu.diting.Service.Impl;

import com.neu.diting.Entity.HoneypotEntity;
import com.neu.diting.Repository.HoneypotRepository;
import com.neu.diting.Service.HoneypotService;
import com.neu.diting.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.neu.diting.Util.Util.infors;
import static com.neu.diting.Util.Util.Check;

/**
 * Created by Toni Chan on 2018/8/11
 */
@Service
@Transactional
public class HoneypotServiceImpl implements HoneypotService {
    @Autowired
    private HoneypotRepository honeypotRepository;
    SimpleDateFormat dateFormat =  new SimpleDateFormat( "yyyy-MM-dd" );
    String[] potName = new String[]{"s7","modbus","DNP3","Ethernet/IP","atg","Fins","CodeSys","Fox"};
    String[] honeyPotIp= new String[]{"10.20.2.205","192.168.247.128","10.20.2.203","192.168.247.130","10.20.2.206","10.20.2.202","10.20.2.207","10.20.2.208"};
    int[] honeyPotPort= new int[]{102,8848,20000,8848,1001,9600,2455,1911};

    //查询蜜罐的所有消息，honeypot表
    public List findAll(){
        List<HoneypotEntity> dataList = honeypotRepository.findAll();
        List list = new ArrayList();
        for(HoneypotEntity honeypot : dataList){
            Map arrays = new HashMap<>();
            arrays.put("hostname", honeypot.getHostname());
            arrays.put("ip", honeypot.getIp());
            arrays.put("protocol",honeypot.getProtocol());
            String datetime = dateFormat.format(honeypot.getCreatTime());
            arrays.put("creat_time",datetime);
            list.add(arrays);
        }
        return list;
    }

    //显示国家排名，pot_data
    public List<Map> ranking(){
        List<Object[]> dataList = honeypotRepository.Ranking();
        //类型转换
        List list = new ArrayList();
        for(Object[] objs : dataList){
            Map map = new HashMap<>();
            map.put("Country", objs[0]);
            map.put("WarningTimeCount", objs[1]);
            list.add(map);
        }
        return list;
    }

    //饼图，内圈是各协议；外圈每个协议对应的IP、数量
    public List pieChart() {
        List<String> protocol = honeypotRepository.findAllProtocols();
        List list = new ArrayList();
        for(String str:protocol) {
            Map map = new HashMap<>();
            map.put("Protocol",str);
            List<Map> arraysList = new ArrayList();
            List<Object[]> dataList = honeypotRepository.findByProtocol(str);
            for(Object[] objs : dataList){
                Map arrays = new HashMap<>();
                arrays.put("IP", objs[0]);
                arrays.put("Count", objs[1]);
                arraysList.add(arrays);
            }
            map.put("Arrays",arraysList);
            list.add(map);
        }
        return list;
    }

    //热力图
    public List heatMap(String protocol) {
        List list = new ArrayList();//总的返回数组

        List<String> cities = honeypotRepository.findAllCities();//城市数组
        for(int j = 0; j < cities.size(); j++){//剔除北京自身
            if(cities.get(j).contentEquals("Beijing")){
                cities.remove(j);
            }else{
                System.out.println(cities.get(j));
            }
        }

        List<String> months;
        if(protocol.isEmpty()) {
            months = honeypotRepository.findAllMonths();//月份数组
            for (int i = 0; i < months.size(); i++) {
                String month =months.get(i);
                Map map = new HashMap();
                map.put("time",month);
                List lineData  =new ArrayList();
                List heatData = new ArrayList();
                for (int j = 0; j < cities.size(); j++) {
                    String city = cities.get(j);
                    String dataList = honeypotRepository.getCountByMonthCity(city, month);
                    if (dataList == null) {
                        continue;
                    } else {
                        List eachLinedata  =new ArrayList();
                        Map data1 = new HashMap();
                        data1.put("fromName",city);
                        String IP = dataList.substring(dataList.indexOf(",")+1,dataList.lastIndexOf(","));
                        data1.put("IP",IP);
                        eachLinedata.add(data1);

                        Map data2 = new HashMap();
                        data2.put("toName","Beijing");
                        String count = dataList.substring(dataList.lastIndexOf(",")+1);
                        data2.put("value",Integer.valueOf(count));
                        eachLinedata.add(data2);
                        lineData.add(eachLinedata);

                        Map heatdata = new HashMap();
                        heatdata.put("name",city);
                        heatdata.put("value",Integer.valueOf(count));
                        heatData.add(heatdata);
                    }
                }
                map.put("LineDate",lineData);
                map.put("heatData",heatData);
                list.add(map);
            }
        }
        else{
            //热力图查询协议对应的经纬度;
            months = honeypotRepository.findMonthsByProtocol(protocol);//所查协议月份数组
            for (int i = 0; i < months.size(); i++) {
                String month =months.get(i);
                Map map = new HashMap();
                map.put("time",month);
                List lineData  =new ArrayList();
                List heatData  =new ArrayList();
                for (int j = 0; j < cities.size(); j++) {
                    String city = cities.get(j);
                    String dataList = honeypotRepository.getCountByProtocol(protocol,city, month);
                    if (dataList == null) {
                        continue;
                    } else {
                        List eachLineData  =new ArrayList();
                        Map data1 = new HashMap();
                        data1.put("fromName",city);
                        String IP = dataList.substring(dataList.indexOf(",")+1,dataList.lastIndexOf(","));
                        data1.put("IP",IP);
                        eachLineData.add(data1);

                        Map data2 = new HashMap();
                        data2.put("toName","Beijing");
                        String count = dataList.substring(dataList.lastIndexOf(",")+1);
                        data2.put("value",Integer.valueOf(count));
                        eachLineData.add(data2);
                        lineData.add(eachLineData);

                        Map heatdata = new HashMap();
                        heatdata.put("name",city);
                        heatdata.put("value",Integer.valueOf(count));
                        heatData.add(heatdata);
                    }
                }
                map.put("LineDate",lineData);
                map.put("heatData",heatData);
                list.add(map);
            }
        }
        return list;
    }

    //点线图，从今天起往前一周之内每一天每个小时攻击量数
    //数据库时间2018-03-23至2018-08-05
//    public List pointChart() {
//        List list = new ArrayList();//总的返回数组
//        Date maxDate = honeypotRepository.findMaxDate();//查找最新的日期
//        ArrayList dates = new ArrayList();//前七天日期起由远及近
//
//        for(int i =0,j =7; i<7 ;i++,j--){
//            Date date = honeypotRepository.findIntervalDate(maxDate,j);
//            dates.add(date);
//            System.out.println(date);
//        }
//        for(int i =0; i<dates.size() ;i++){
//            Map map = new HashMap<>();
//            map.put("Day",dates.get(i));//日期存入
//            //count数组用来存放每天24小时每个小时的访问次数
//            int count[][] = new int[2][24];
//            for(int k=0; k<24 ;k++){
//                count[0][k] = k;
//                count[1][k] = 0;
//            }
//
//            List<Object[]> counts= honeypotRepository.findHourCount(dates.get(i).toString());
//            for(Object[] objs : counts){
//                String index=objs[0].toString();
//                int hour = Integer.parseInt(index);
//                String number =objs[1].toString();
//                int num = Integer.parseInt(number);
//                count[1][hour] = num;
//            }
//            map.put("HoursCount",count);//当天每小时对应的时间和个数
//            list.add(map);
//        }
//        return list;
//    }

    //3D图：[协议，国家，供给量]
    public List threeDChart() {
        List list = new ArrayList();//总的返回数组
        List<String> countries = honeypotRepository.findAllCountriesNew();//国家数组
        List<String> protocols = honeypotRepository.findAllProtocols();//协议数组
        for(int i = 0;i<countries.size();i++)
        {
            for(int j =0;j<protocols.size();j++){
                Map map = new HashMap();
                String country = countries.get(i);
                map.put("Country",country);
                String protocol = protocols.get(j);
                map.put("Protocol",protocol);
                int count = honeypotRepository.getCountByCountyProtocol(country,protocol);
                map.put("Count",count);
                list.add(map);
            }
        }
        return list;
    }

    //桑基图，基础数据[协议、国家、蜜罐IP]，关联数据[source、target、value]
    public List sankey() {
        List<String> protocols = honeypotRepository.findAllProtocols();//协议数组
        List<String> countries = honeypotRepository.findAllCountriesNew();//国家数组
        List<Object[]> top3countries = honeypotRepository.top7Countries();//前7位国家
        List<String> top7 = new ArrayList();
        List<String> honeypotIPs = honeypotRepository.findAllhoneypotIP();//蜜罐IP数组

        for(Object[] objs : top3countries){
            String country =objs[0].toString();
            top7.add(country);
        }

        for(int i = 0;i <countries.size();i++)
        {
            for(int j = 0;j < top7.size();j++)
            {
                if(countries.get(i).equals(top7.get(j))){
                    countries.remove(i);
                }
            }
        }

        List list = new ArrayList();//总的返回数组
        List list1 = new ArrayList();//返回基础数据数组
        for(int i = 0;i<protocols.size();i++)
        {
            Map map = new HashMap();
            String protocol = protocols.get(i);
            map.put("name",protocol);
            int value = honeypotRepository.getCountByProtocol(protocol);
            map.put("value",value);
            list1.add(map);
        }
        for(int i = 0;i<top7.size();i++)
        {
            Map map = new HashMap();
            String country = top7.get(i);
            map.put("name",country);
            map.put("value",1);
            list1.add(map);
        }
        Map others = new HashMap();
        String other = "Other Countries";
        others.put("name",other);
        others.put("value",1);
        list1.add(others);

        for(int i = 0;i<honeypotIPs.size();i++)
        {
            Map map = new HashMap();
            String honeypotIP = honeypotIPs.get(i);
            map.put("name",honeypotIP);
            map.put("value",1);
            list1.add(map);
        }
        list.add(list1);

        //条带数据
        List list2 = new ArrayList();//返回条带数据数组
        for(int i = 0;i<protocols.size();i++)
        {
            for(int j =0;j<top7.size();j++){
                Map map = new HashMap();
                String protocol = protocols.get(i);
                String country = top7.get(j);
                int value = honeypotRepository.getCountByCountyProtocol(country,protocol);
                if(value>0) {
                    map.put("source",protocol);
                    map.put("target",country);
                    map.put("value",value);
                    list2.add(map);
                }else{ continue;}
            }
        }
        for(int i = 0;i<protocols.size();i++)
        {
            String protocol = protocols.get(i);
            int count = 0;
            for(int j =0;j<countries.size();j++){
                String country = countries.get(j);
                int value = honeypotRepository.getCountByCountyProtocol(country,protocol);
                if(value>0) {
                    count = count + value;
                }else{ continue;}
            }
            Map map = new HashMap();
            map.put("source",protocol);
            map.put("target",other);
            map.put("value",count);
            list2.add(map);
        }

        for(int j =0;j<honeypotIPs.size();j++)
        {
            for(int i = 0;i<top7.size();i++){
                Map map = new HashMap();
                String country = top7.get(i);
                String honeypotIP = honeypotIPs.get(j);
                int value = honeypotRepository.getCountByCountyHoneypotIP(country,honeypotIP);
                if(value>0) {
                    map.put("source", country);
                    map.put("target", honeypotIP);
                    map.put("value", value);
                    list2.add(map);
                }else{ continue;}
            }
        }

        for(int j =0;j<honeypotIPs.size();j++)
        {
            String honeypotIP = honeypotIPs.get(j);
            int count = 0;
            for(int i = 0;i<countries.size();i++){
                String country = countries.get(i);
                int value = honeypotRepository.getCountByCountyHoneypotIP(country,honeypotIP);
                if(value>0) {
                    count = count + value;
                }else{ continue;}
            }
            Map map = new HashMap();
            map.put("source",other);
            map.put("target",honeypotIP);
            map.put("value",count);
            list2.add(map);
        }

        list.add(list2);
        return list;
    }

    //日志
    public List log() {
        List list = new ArrayList();//返回的总数组

        List<Object[]> dataList = honeypotRepository.findLog();
        for(Object[] data : dataList){
            Map arrays = new HashMap<>();
            arrays.put("id",Integer.parseInt(data[0].toString()));
            String datetime = dateFormat.format(data[1]);
            arrays.put("Time",datetime);
            arrays.put("Ip",data[2]);
            arrays.put("Location",data[3]);
            String index=data[4].toString();
            int score = Integer.parseInt(index);
            arrays.put("Risk",score);
            String dns = data[5].toString().substring(2);
            arrays.put("DNS",dns);
            list.add(arrays);
        }

        for(int i=0,j=1;j<list.size();){
            Map first = (Map) list.get(i);
            String firstIp = (String) first.get("Ip");
            Map second = (Map) list.get(j);
            String secondIp = (String) second.get("Ip");
            if(firstIp.equals(secondIp))
            {
                list.remove(j);
            }else{
                i++;
                j++;
            }
        }
        return list;
    }

    //蜜罐部署时间
    public int deployTime(String protocol) {
        int value = honeypotRepository.getDeploytime(protocol);
        return value;
    }

    // 国家攻击量占比图
    public List percentAge(String protocol) {
        List list = new ArrayList();
        List<Object[]> dataList = honeypotRepository.top3(protocol);
        int value = honeypotRepository.allPercent(protocol);
        for(Object[] data : dataList){
            Map array = new HashMap<>();
            array.put("Country",data[0]);
            int precent = Integer.parseInt(data[1].toString());
            value = value - precent;
            array.put("Count",precent);
            list.add(array);
        }
        Map array = new HashMap<>();
        String country = "Other countries";
        array.put("Country",country);
        array.put("Count",value);
        list.add(array);
        return list;
    }

    //近七天流量状态
    public List flow(String protocol) {
        List list = new ArrayList();
        List<Date> dates = honeypotRepository.recentTime(protocol);
        Collections.sort(dates);
        for(Date time : dates){
            Map array = new HashMap<>();
            String dataList = honeypotRepository.flow(protocol,time);
            String date = dataList.substring(0,10);
            array.put("Date",date);
            String num  = dataList.substring(11);
            int count = Integer.parseInt(num);
            array.put("Count",count);
            list.add(array);
        }
        return list;
    }

    //日志详细信息
    public Map logInformation(int id) {
        Map array = new HashMap<>();
        List<Object[]> dataList = honeypotRepository.logInformation(id);
        String ip;
        for(Object[] data : dataList){
            String time = dateFormat.format(data[0]);
            array.put("Time",time);
            ip = data[1].toString();
            array.put("IP",data[1]);
            array.put("Country",data[2]);
            array.put("City",data[3]);
            array.put("Protocol",data[4]);
            int risk = Integer.parseInt(data[5].toString());
            array.put("Risk",risk);
            String dns = data[6].toString().substring(2);
            array.put("DNS",dns);
            array.put("whois_updatedDate",data[7]);
            array.put("whois_organization",data[8]);
            array.put("whois_contactEmail",data[9]);
        }
        //查询这个IP的攻击次数
        int trackTime = honeypotRepository.trackTimeOfOneIp(id);
        array.put("trackTime",trackTime);
        return array;
    }

    @Override
    public List logPointMap(int id) {
        List list = new ArrayList();//总的返回数组
        String country = honeypotRepository.findCountry(id);//查找id对应的协议
        Date maxDate = honeypotRepository.findRecentDate(country);//查找国家对应的最后日期
        System.out.println(maxDate);
        ArrayList dates = new ArrayList();//前七天日期起由远及近
        for(int i =0,j =7; i<7 ;i++,j--){
            Date date = honeypotRepository.findIntervalDate(maxDate,j);
            dates.add(date);
            System.out.println(date);
        }
        for(int i =0; i<dates.size() ;i++){
            Map map = new HashMap<>();
            map.put("Day",dates.get(i));//周几存入
            //count数组用来存放每天24小时每个小时的访问次数
            int count[][] = new int[2][24];
            for(int k=0; k<24 ;k++){
                count[0][k] = k;
                count[1][k] = 0;
            }

            List<Object[]> counts= honeypotRepository.findHourCount(dates.get(i).toString());
            for(Object[] objs : counts){
                String index=objs[0].toString();
                int hour = Integer.parseInt(index);
                String number =objs[1].toString();
                int num = Integer.parseInt(number);
                count[1][hour] = num;
            }
            map.put("HoursCount",count);//当天每小时对应的时间和个数
            list.add(map);
        }
        return list;
    }

    public Map ipAttackTime(int id){
        String addr = honeypotRepository.idProtocol(id);
        String time = honeypotRepository.ipAttackTime(addr);
        long[] data = Util.getStrOfSeconds(time);
        Map map = new HashMap<>();
        map.put("logDay",data[0]);//天
        map.put("logHour",data[1]);//小时
        map.put("logMinute",data[2]);//分
        map.put("logSecond",data[3]);//秒
        return map;
    }

    public List ipCircleAttack(int id) {
        List list = new ArrayList();//总的返回数组
        String addr = honeypotRepository.idProtocol(id);
        List<Object[]> dataList =honeypotRepository.ipCircleAttack(addr) ;
        for(Object[] data :dataList){
            Map map = new HashMap<>();
            map.put("name",data[0]);
            map.put("value",data[1]);
            list.add(map);
        }
        return list;
    }

    public List ipLineAttack(int id) {
        List list = new ArrayList();//总的返回数组
        String addr = honeypotRepository.idProtocol(id);
        List<Object[]> dataList =honeypotRepository.ipLineAttack(addr) ;
        for(Object[] data :dataList){
            Map map = new HashMap<>();
            map.put("time",data[0]);
            map.put("value",data[1]);
            list.add(map);
        }
        return list;
    }

    @Override
    public List protocolAttack() {
        List list = new ArrayList();//总的返回数组
        String[] protocols = new String[]{"","modbus","DNP3","Ethernet/IP","atg","Fins"};
        List<String> months = honeypotRepository.findAllMonthsNew();//月份数组
        list.add(months);
        int count;
        for(int i = 0;i<protocols.length;i++){
            List preProtocol = new ArrayList();
            for(String month:months){
                String dataList = honeypotRepository.protocolAttack(protocols[i],month);
                if(dataList==null){
                    count = 0;
                }else{
                    String number =dataList.substring(8);
                    count = Integer.parseInt(number);
                }
                preProtocol.add(count);
            }
            list.add(preProtocol);
        }
        return list;
    }

    @Override
    public  Map honeyGrow() {
        Map map = new HashMap<>();
        Date maxDate = honeypotRepository.findMaxDateNew();
        String[] protocols = new String[]{"s7","modbus","DNP3","Ethernet/IP","atg","Fins"};
        int maxMonth = maxDate.getMonth();
        int month = 0;//月份从0开始[0...11]

        List time = new ArrayList();
        while(month<=maxMonth) {
            time.add("2018-" + (month + 1));
            month++;
        }
        map.put("time",time);

        Map dataMap  = new HashMap<>();
        for(String protocol:protocols){
            month = 0;
            List data = new ArrayList();
            while(month<=maxMonth) {
            String lastDay = Util.getLastDayOfMonth(2018, month);
            String dataList = honeypotRepository.honeyGrow(lastDay, protocol);
            if(dataList==null){
                data.add(0);
            }else{
                String result[] = dataList.split(",");
                int count = Integer.parseInt(result[1]);
                data.add(count);
            }
            month++;
            }
            dataMap.put(protocol,data);
        }
        map.put("data",dataMap);
        return map;
    }


    @Override
    public Map deviceCPU(String protocol) {
        Map deviceCPU;
        int index = -1 ;
        for(int i=0;i<potName.length;i++){
            System.out.println(potName[i]);
            if(potName[i].equals(protocol)){
                index = i;
                break;
            }
        }
        deviceCPU=Util.dealReceive(infors[index]);
        deviceCPU.remove("NIC");
        return deviceCPU;
    }

    @Override
    public Map deviceNIC(String protocol) {
        Map deviceNIC;
        int index = -1 ;
        for(int i=0;i<potName.length;i++){
            System.out.println(potName[i]);
            if(potName[i].equals(protocol)){
                index = i;
                break;
            }
        }
        deviceNIC=Util.dealReceive(infors[index]);
        deviceNIC.remove("Memory");
        deviceNIC.remove("CPU");
        return deviceNIC;
    }

    @Override
    public int setNetwork(String[] infor) throws IOException {

        //定位哪个蜜罐
        int index = -1 ;
        for(int i=0;i<potName.length;i++){
//            System.out.println(potName[i]);
            if(potName[i].equals(infor[0])){
                index = i;
                break;
            }
        }

        if(index==-1){
            System.out.println("输入蜜罐不匹配");
            return 0;
        }

        //检查输入的IP、子网掩码、网关是否符合相关格式
        boolean result = Check(infor);
        if(result){
            //1.创建一个Server Socket
            Socket socket = new Socket();

            // 2.连接到指定的 server socket,指定IP 和端口号
            InetSocketAddress address = new InetSocketAddress(honeyPotIp[index],honeyPotPort[index]);
            socket.connect(address);

            // 3.连接成功后，获取相应的输入输出流，进行数据交互
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);


            //数据发送给服务器端
            String  test = infor[1]+"#"+infor[2]+"#"+infor[3];
            pw.println(test);
//            honeyPotIp[index]=infor[1];
            honeypotRepository.setNetwork(infor[0],infor[1],infor[2],infor[3]);
            System.out.println("Client:"+test);

            while(true)
            {
                // 捕捉来自服务器端发来的消息  服务器端没有发消息过来时，br.ready() 为false， 循环检测是否有数据，有测打印出来
                if(br.ready())
                {
                    String info = br.readLine();
                    System.out.println("Server:"+info);
                    break;
                }
            }

            br.close();
            pw.close();
            socket.close();
            System.out.println("客户端将关闭连接");
            return 1;

        }else{
            return 0;
        }
    }

    @Override
    public Map selectSet(String protocol) {
        Map resultSet = new HashMap<>();
        String[] infor = new String[]{"","","",};
        List<Object[]> result  = honeypotRepository.selectSet(protocol);
        if(result.isEmpty()){
            resultSet.put("ip",infor[0]);
            resultSet.put("mask",infor[1]);
            resultSet.put("gateway",infor[2]);
        }else{
            resultSet.put("ip",result.get(0)[0]);
            resultSet.put("mask",result.get(0)[1]);
            resultSet.put("gateway",result.get(0)[2]);
        }
        return resultSet;
    }
}
