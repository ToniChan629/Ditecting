package com.neu.diting.Entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created by user on 2019/1/23 19:20
 */
@Entity
@Table(name = "honeypot", schema = "honeypot", catalog = "")
public class HoneypotEntity {
    private int id;
    private String hostname;
    private String ip;
    private String protocol;
    private Timestamp creatTime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "hostname")
    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Basic
    @Column(name = "ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "protocol")
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Basic
    @Column(name = "creat_time")
    public Timestamp getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Timestamp creatTime) {
        this.creatTime = creatTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HoneypotEntity that = (HoneypotEntity) o;
        return id == that.id &&
                Objects.equals(hostname, that.hostname) &&
                Objects.equals(ip, that.ip) &&
                Objects.equals(protocol, that.protocol) &&
                Objects.equals(creatTime, that.creatTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hostname, ip, protocol, creatTime);
    }

    /**
     * Created by user on 2019/1/23 19:20
     */
    @Entity
    @Table(name = "pot_data1201", schema = "honeypot", catalog = "")
    public static class PotData1201Entity {
        private int id;
        private String addr;
        private String protocol;
        private Timestamp time;
        private String city;
        private String country;
        private String continent;
        private String latitude;
        private String longitude;

        @Id
        @Column(name = "id")
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Basic
        @Column(name = "addr")
        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        @Basic
        @Column(name = "protocol")
        public String getProtocol() {
            return protocol;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }

        @Basic
        @Column(name = "time")
        public Timestamp getTime() {
            return time;
        }

        public void setTime(Timestamp time) {
            this.time = time;
        }

        @Basic
        @Column(name = "city")
        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        @Basic
        @Column(name = "country")
        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        @Basic
        @Column(name = "continent")
        public String getContinent() {
            return continent;
        }

        public void setContinent(String continent) {
            this.continent = continent;
        }

        @Basic
        @Column(name = "latitude")
        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        @Basic
        @Column(name = "longitude")
        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PotData1201Entity that = (PotData1201Entity) o;
            return id == that.id &&
                    Objects.equals(addr, that.addr) &&
                    Objects.equals(protocol, that.protocol) &&
                    Objects.equals(time, that.time) &&
                    Objects.equals(city, that.city) &&
                    Objects.equals(country, that.country) &&
                    Objects.equals(continent, that.continent) &&
                    Objects.equals(latitude, that.latitude) &&
                    Objects.equals(longitude, that.longitude);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, addr, protocol, time, city, country, continent, latitude, longitude);
        }
    }

    /**
     * Created by user on 2019/1/23 19:20
     */
    @Entity
    @Table(name = "pot_data", schema = "honeypot", catalog = "")
    public static class PotDataEntity {
        private int id;
        private String addr;
        private String protocol;
        private Timestamp time;
        private String city;
        private String country;
        private String continent;
        private String latitude;
        private String longitude;

        @Id
        @Column(name = "id")
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Basic
        @Column(name = "addr")
        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        @Basic
        @Column(name = "protocol")
        public String getProtocol() {
            return protocol;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }

        @Basic
        @Column(name = "time")
        public Timestamp getTime() {
            return time;
        }

        public void setTime(Timestamp time) {
            this.time = time;
        }

        @Basic
        @Column(name = "city")
        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        @Basic
        @Column(name = "country")
        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        @Basic
        @Column(name = "continent")
        public String getContinent() {
            return continent;
        }

        public void setContinent(String continent) {
            this.continent = continent;
        }

        @Basic
        @Column(name = "latitude")
        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        @Basic
        @Column(name = "longitude")
        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PotDataEntity that = (PotDataEntity) o;
            return id == that.id &&
                    Objects.equals(addr, that.addr) &&
                    Objects.equals(protocol, that.protocol) &&
                    Objects.equals(time, that.time) &&
                    Objects.equals(city, that.city) &&
                    Objects.equals(country, that.country) &&
                    Objects.equals(continent, that.continent) &&
                    Objects.equals(latitude, that.latitude) &&
                    Objects.equals(longitude, that.longitude);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, addr, protocol, time, city, country, continent, latitude, longitude);
        }
    }

    /**
     * Created by user on 2019/1/23 19:20
     */
    @Entity
    @Table(name = "whois", schema = "honeypot", catalog = "")
    public static class WhoisEntity {
        private int id;
        private String ip;
        private String whoisOrganization;
        private String whoisContactEmail;
        private String whoisCountry;
        private String whoisRegistrarName;
        private String detailCategories;
        private String subnet;
        private String detailCountryOfIp;
        private String detailAsn;
        private String dns;
        private String score;
        private String detailHosted;
        private String detailActions;
        private String detailRisks;
        private String whoisUpdate;

        @Id
        @Column(name = "id")
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Basic
        @Column(name = "ip")
        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        @Basic
        @Column(name = "whois_organization")
        public String getWhoisOrganization() {
            return whoisOrganization;
        }

        public void setWhoisOrganization(String whoisOrganization) {
            this.whoisOrganization = whoisOrganization;
        }

        @Basic
        @Column(name = "whois_contactEmail")
        public String getWhoisContactEmail() {
            return whoisContactEmail;
        }

        public void setWhoisContactEmail(String whoisContactEmail) {
            this.whoisContactEmail = whoisContactEmail;
        }

        @Basic
        @Column(name = "whois_country")
        public String getWhoisCountry() {
            return whoisCountry;
        }

        public void setWhoisCountry(String whoisCountry) {
            this.whoisCountry = whoisCountry;
        }

        @Basic
        @Column(name = "whois_registrarName")
        public String getWhoisRegistrarName() {
            return whoisRegistrarName;
        }

        public void setWhoisRegistrarName(String whoisRegistrarName) {
            this.whoisRegistrarName = whoisRegistrarName;
        }

        @Basic
        @Column(name = "detail_categories")
        public String getDetailCategories() {
            return detailCategories;
        }

        public void setDetailCategories(String detailCategories) {
            this.detailCategories = detailCategories;
        }

        @Basic
        @Column(name = "subnet")
        public String getSubnet() {
            return subnet;
        }

        public void setSubnet(String subnet) {
            this.subnet = subnet;
        }

        @Basic
        @Column(name = "detail_country_of_ip")
        public String getDetailCountryOfIp() {
            return detailCountryOfIp;
        }

        public void setDetailCountryOfIp(String detailCountryOfIp) {
            this.detailCountryOfIp = detailCountryOfIp;
        }

        @Basic
        @Column(name = "detail_asn")
        public String getDetailAsn() {
            return detailAsn;
        }

        public void setDetailAsn(String detailAsn) {
            this.detailAsn = detailAsn;
        }

        @Basic
        @Column(name = "dns")
        public String getDns() {
            return dns;
        }

        public void setDns(String dns) {
            this.dns = dns;
        }

        @Basic
        @Column(name = "score")
        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        @Basic
        @Column(name = "detail_hosted")
        public String getDetailHosted() {
            return detailHosted;
        }

        public void setDetailHosted(String detailHosted) {
            this.detailHosted = detailHosted;
        }

        @Basic
        @Column(name = "detail_actions")
        public String getDetailActions() {
            return detailActions;
        }

        public void setDetailActions(String detailActions) {
            this.detailActions = detailActions;
        }

        @Basic
        @Column(name = "detail_risks")
        public String getDetailRisks() {
            return detailRisks;
        }

        public void setDetailRisks(String detailRisks) {
            this.detailRisks = detailRisks;
        }

        @Basic
        @Column(name = "whois_update")
        public String getWhoisUpdate() {
            return whoisUpdate;
        }

        public void setWhoisUpdate(String whoisUpdate) {
            this.whoisUpdate = whoisUpdate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WhoisEntity that = (WhoisEntity) o;
            return id == that.id &&
                    Objects.equals(ip, that.ip) &&
                    Objects.equals(whoisOrganization, that.whoisOrganization) &&
                    Objects.equals(whoisContactEmail, that.whoisContactEmail) &&
                    Objects.equals(whoisCountry, that.whoisCountry) &&
                    Objects.equals(whoisRegistrarName, that.whoisRegistrarName) &&
                    Objects.equals(detailCategories, that.detailCategories) &&
                    Objects.equals(subnet, that.subnet) &&
                    Objects.equals(detailCountryOfIp, that.detailCountryOfIp) &&
                    Objects.equals(detailAsn, that.detailAsn) &&
                    Objects.equals(dns, that.dns) &&
                    Objects.equals(score, that.score) &&
                    Objects.equals(detailHosted, that.detailHosted) &&
                    Objects.equals(detailActions, that.detailActions) &&
                    Objects.equals(detailRisks, that.detailRisks) &&
                    Objects.equals(whoisUpdate, that.whoisUpdate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, ip, whoisOrganization, whoisContactEmail, whoisCountry, whoisRegistrarName, detailCategories, subnet, detailCountryOfIp, detailAsn, dns, score, detailHosted, detailActions, detailRisks, whoisUpdate);
        }
    }
}
