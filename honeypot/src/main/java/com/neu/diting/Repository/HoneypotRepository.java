package com.neu.diting.Repository;

import com.neu.diting.Entity.HoneypotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Toni Chan on 2018/8/11
 */
@Repository
public interface HoneypotRepository extends JpaRepository<HoneypotEntity,Integer>,PagingAndSortingRepository<HoneypotEntity,Integer> {

    //查询蜜罐的所有消息
    List<HoneypotEntity> findAll();
    //查询所有基础量


    //显示国家排名
    @Query(value = "SELECT pot_data1201.country,COUNT(*) AS Ranking FROM pot_data1201 GROUP BY pot_data1201.country ORDER BY Ranking DESC LIMIT 7",nativeQuery = true)
    List<Object[]> Ranking();

    //饼图，内圈是各协议；外圈每个协议对应的IP、数量
    @Query(value = "SELECT DISTINCT honeypot.protocol FROM honeypot ORDER BY honeypot.protocol ASC",nativeQuery = true)
    List<String> findAllProtocols();
    @Query(value = "SELECT honeypot.ip,COUNT(*) AS Number FROM honeypot WHERE honeypot.protocol = :protocol GROUP BY honeypot.ip",nativeQuery = true)
    List<Object[]> findByProtocol(@Param("protocol") String str);

    //热力图
    @Query(value = "SELECT DISTINCT pot_data.city FROM pot_data WHERE pot_data.city NOT LIKE 'NULL' AND pot_data.city NOT LIKE 'Unknow' ORDER BY pot_data.city ASC",nativeQuery = true)
    List<String> findAllCities();
    @Query(value = "SELECT DISTINCT DATE_FORMAT(pot_data.time,'%Y-%m') FROM pot_data",nativeQuery = true)
    List<String> findAllMonths();

    @Query(value = "SELECT DISTINCT pot_data1201.city FROM pot_data1201 WHERE pot_data1201.city NOT LIKE 'NULL' AND pot_data1201.city NOT LIKE 'Unknow' ORDER BY pot_data1201.city ASC",nativeQuery = true)
    List<String> findAllCitiesNew();

    @Query(value = "SELECT DATE_FORMAT(pot_data.time,'%Y-%m') AS datetime,pot_data.addr,COUNT(*) FROM pot_data WHERE pot_data.city=:city GROUP BY dateTime HAVING dateTime =:months",nativeQuery = true)
    String getCountByMonthCity(@Param("city") String city,@Param("months") String month);

    @Query(value = "SELECT DISTINCT DATE_FORMAT(pot_data.time,'%Y-%m') FROM pot_data WHERE pot_data.protocol =:protocol",nativeQuery = true)
    List<String> findMonthsByProtocol(@Param("protocol") String protocol);
    @Query(value = "SELECT DATE_FORMAT(pot_data.time,'%Y-%m') AS datetime,pot_data.addr,COUNT(*) FROM pot_data WHERE pot_data.city=:city AND pot_data.protocol =:protocol GROUP BY dateTime HAVING dateTime =:months",nativeQuery = true)
    String getCountByProtocol(@Param("protocol") String protocol,@Param("city") String city,@Param("months") String month);

    //点线图
    @Query(value = "SELECT DATE(pot_data.time) FROM pot_data WHERE pot_data.id =(SELECT MAX(pot_data.id) FROM pot_data)",nativeQuery = true)
    Date findMaxDate();
    @Query(value = "SELECT DISTINCT DATE(time) FROM pot_data WHERE DATE_SUB(:maxDate,INTERVAL :intervalDay DAY) = DATE(time)",nativeQuery = true)
    Date findIntervalDate(@Param("maxDate") Date maxDate,@Param("intervalDay") int intervalDay);
    @Query(value = " SELECT EXTRACT(HOUR FROM pot_data.time) AS Hours,COUNT(*) AS Count FROM pot_data WHERE DATE(pot_data.time)= :dateDay GROUP BY Hours",nativeQuery = true)
    List<Object[]> findHourCount(@Param("dateDay")String dateDay);

    //3D图：[协议，国家，供给量]
    @Query(value = "SELECT DISTINCT pot_data.country FROM pot_data WHERE pot_data.country NOT LIKE 'Unknow' AND pot_data.country NOT LIKE 'NULL' ORDER BY pot_data.country ASC",nativeQuery = true)
    List<String> findAllCountries();

    @Query(value = "SELECT DISTINCT pot_data1201.country FROM pot_data1201 WHERE pot_data1201.country NOT LIKE 'Unknow' AND pot_data1201.country NOT LIKE 'NULL' ORDER BY pot_data1201.country ASC",nativeQuery = true)
    List<String> findAllCountriesNew();
    @Query(value = "SELECT COUNT(*) FROM pot_data1201 WHERE pot_data1201.country = :country AND pot_data1201.protocol = :protocol",nativeQuery = true)
    int getCountByCountyProtocol(@Param("country") String country,@Param("protocol") String protocol);

    //桑基图
    @Query(value = "SELECT DISTINCT pot_data1201.country,COUNT(pot_data1201.id) AS NUM FROM pot_data1201 WHERE pot_data1201.country NOT LIKE 'Unknow' GROUP BY pot_data1201.country ORDER BY NUM DESC LIMIT 7",nativeQuery = true)
    List<Object[]> top7Countries();
    @Query(value = "SELECT DISTINCT honeypot.ip FROM honeypot",nativeQuery = true)
    List<String> findAllhoneypotIP();
    @Query(value = "SELECT COUNT(*) FROM pot_data1201 WHERE pot_data1201.protocol = :protocol",nativeQuery = true)
    int getCountByProtocol(@Param("protocol") String protocol);

    @Query(value = "SELECT COUNT(*) FROM pot_data1201,honeypot WHERE pot_data1201.protocol = honeypot.protocol AND pot_data1201.country = :country AND honeypot.ip = :honeypotIP",nativeQuery = true)
    int getCountByCountyHoneypotIP(@Param("country")String country,@Param("honeypotIP") String honeypotIP);

    //日志
    @Query(value = "SELECT DISTINCT pot_data.id,pot_data.time,pot_data.addr,pot_data.country,whois.score,whois.dns FROM pot_data ,whois WHERE pot_data.addr = whois.ip AND pot_data.country NOT LIKE 'Unknow' AND whois.dns NOT LIKE '' AND whois.score IS NOT NULL",nativeQuery = true)
    List<Object[]> findLog();

    //蜜罐部署时间
    @Query(value = "SELECT TIMESTAMPDIFF(DAY,Min(pot_data1201.time),Max(pot_data1201.time)) FROM honeypot,pot_data1201 WHERE honeypot.protocol = pot_data1201.protocol AND honeypot.protocol = :protocol",nativeQuery = true)
    int getDeploytime(@Param("protocol") String protocol);

    //攻击量百分比
    @Query(value = "SELECT pot_data1201.country,COUNT(*) AS num FROM pot_data1201 WHERE pot_data1201.protocol = :protocol GROUP BY pot_data1201.country ORDER BY num DESC LIMIT 3",nativeQuery = true)
    List<Object[]> top3(@Param("protocol") String protocol);
    @Query(value = "SELECT COUNT(*) FROM pot_data1201 WHERE pot_data1201.protocol =:protocol",nativeQuery = true)
    int allPercent(@Param("protocol") String protocol);

    //近七天流量状态
    @Query(value = "SELECT DISTINCT Date(pot_data1201.time) AS dateTime FROM pot_data1201 WHERE pot_data1201.protocol =:protocol ORDER BY dateTime DESC LIMIT 7",nativeQuery = true)
    List<Date> recentTime(@Param("protocol") String protocol);
    @Query(value = "SELECT Date(pot_data1201.time) AS dateTime,COUNT(*) FROM pot_data1201 WHERE pot_data1201.protocol =:protocol AND DATE(pot_data1201.time)=:datetime",nativeQuery = true)
    String flow(@Param("protocol")String protocol,@Param("datetime") Date time);
//    @Query(value = "SELECT Date(pot_data.time) AS dateTime,COUNT(*) FROM pot_data WHERE pot_data.protocol =:protocol AND pot_data.time > (SELECT DISTINCT DATE_SUB(Max(pot_data.time),INTERVAL 1 WEEK) FROM pot_data WHERE pot_data.protocol = :protocol) GROUP BY dateTime",nativeQuery = true)
//    List<Object[]> flowThisWeek(@Param("protocol") String protocol);

    //日志详细信息
    @Query(value = "SELECT pot_data.time,pot_data.addr,pot_data.country,pot_data.city,pot_data.protocol,whois.score,whois.dns,whois.whois_update,whois.whois_organization,whois.whois_contactEmail FROM pot_data,whois WHERE pot_data.addr = whois.ip AND pot_data.id = :id",nativeQuery = true)
    List<Object[]> logInformation(@Param("id") int id);
    @Query(value = "SELECT Count(*) FROM pot_data WHERE pot_data.addr = ( SELECT pot_data.addr FROM pot_data WHERE pot_data.id = :id)",nativeQuery = true)
    int trackTimeOfOneIp(@Param("id") int id);

    //日志点线图
    @Query(value = "SELECT pot_data.country FROM pot_data WHERE pot_data.id = :id",nativeQuery = true)
    String findCountry(@Param("id") int id);
    @Query(value = "SELECT DATE(pot_data.time) FROM pot_data WHERE pot_data.id =(SELECT MAX(pot_data.id) FROM pot_data WHERE pot_data.country = :country)",nativeQuery = true)
    Date findRecentDate(@Param("country") String country);

    //IP持续攻击时间
    @Query(value = "SELECT pot_data.addr FROM pot_data WHERE pot_data.id = :id",nativeQuery = true)
    String idProtocol(@Param("id") int id);
    @Query(value = "SELECT TIMESTAMPDIFF(SECOND,MIN(pot_data.time),NOW()) AS time FROM pot_data WHERE pot_data.addr = :addr", nativeQuery = true)
    String ipAttackTime(@Param("addr") String addr);

    //IP周期性攻击
    @Query(value = "SELECT DATE_FORMAT(pot_data.time,'%Y/%m') AS yearmonth,COUNT(*) FROM pot_data WHERE pot_data.addr =:addr GROUP BY yearmonth LIMIT 6", nativeQuery = true)
    List<Object[]> ipCircleAttack(@Param("addr") String addr);

    //某个IP一年的攻击量
    @Query(value = "SELECT DATE_FORMAT(pot_data.time,'%Y/%m/%d') AS everyday,COUNT(*) FROM pot_data WHERE pot_data.addr =:addr GROUP BY everyday LIMIT 365", nativeQuery = true)
    List<Object[]> ipLineAttack(@Param("addr") String addr);

    //六种协议分月攻击量
    @Query(value = "SELECT DISTINCT DATE_FORMAT(pot_data1201.time,'%Y-%m') FROM pot_data1201",nativeQuery = true)
    List<String> findAllMonthsNew();
    @Query(value = "SELECT DATE_FORMAT(pot_data1201.time,'%Y-%m') AS yearmonth,COUNT(*) FROM pot_data1201 WHERE pot_data1201.protocol = :protocol GROUP BY yearmonth HAVING yearmonth= :yearmonth", nativeQuery = true)
    String  protocolAttack(@Param("protocol") String protocol, @Param("yearmonth") String yearmonth);

    //从2018-01开始按照月份区分每个协议的流量值
    @Query(value = "SELECT DATE(pot_data1201.time) FROM pot_data1201 WHERE pot_data1201.id =(SELECT MAX(pot_data1201.id) FROM pot_data1201)",nativeQuery = true)
    Date findMaxDateNew();
    @Query(value = "SELECT pot_data1201.protocol,Sum(pot_data1201.id) FROM pot_data1201 WHERE pot_data1201.time BETWEEN '2018-01-01' AND :lastDay AND pot_data1201.protocol = :protocol GROUP BY pot_data1201.protocol", nativeQuery = true)
    String honeyGrow(@Param("lastDay") String time,@Param("protocol") String protocol);

    @Query(value = "INSERT INTO changelog(protocol,onet_addr,mask,gateway,creat_time) VALUES (:prot,:ip,:msk,:gty,NOW())",nativeQuery = true)
    @Modifying
    void setNetwork(@Param("prot") String protocol,@Param("ip") String ip, @Param("msk") String mask, @Param("gty") String gateway);

    @Query(value = "SELECT changelog.onet_addr,changelog.mask,changelog.gateway FROM changelog WHERE changelog.protocol = :prot ORDER BY changelog.creat_time DESC LIMIT 1",nativeQuery = true)
    List<Object[]> selectSet(@Param("prot") String protocol);
}
