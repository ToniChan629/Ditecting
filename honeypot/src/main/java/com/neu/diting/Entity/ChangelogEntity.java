package com.neu.diting.Entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created by user on 2019/1/24 19:54
 */
@Entity
@Table(name = "changelog", schema = "honeypot", catalog = "")
public class ChangelogEntity {
    private int id;
    private String protocol;
    private String inetAddr;
    private String onetAddr;
    private String mask;
    private String port;
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
    @Column(name = "protocol")
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Basic
    @Column(name = "inet_addr")
    public String getInetAddr() {
        return inetAddr;
    }

    public void setInetAddr(String inetAddr) {
        this.inetAddr = inetAddr;
    }

    @Basic
    @Column(name = "onet_addr")
    public String getOnetAddr() {
        return onetAddr;
    }

    public void setOnetAddr(String onetAddr) {
        this.onetAddr = onetAddr;
    }

    @Basic
    @Column(name = "mask")
    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    @Basic
    @Column(name = "port")
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
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
        ChangelogEntity that = (ChangelogEntity) o;
        return id == that.id &&
                Objects.equals(protocol, that.protocol) &&
                Objects.equals(inetAddr, that.inetAddr) &&
                Objects.equals(onetAddr, that.onetAddr) &&
                Objects.equals(mask, that.mask) &&
                Objects.equals(port, that.port) &&
                Objects.equals(creatTime, that.creatTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, protocol, inetAddr, onetAddr, mask, port, creatTime);
    }
}
