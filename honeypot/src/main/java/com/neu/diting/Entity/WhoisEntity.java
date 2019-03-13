package com.neu.diting.Entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by user on 2019/1/24 20:46
 */
@Entity
@Table(name = "whois", schema = "honeypot", catalog = "")
public class WhoisEntity {
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
