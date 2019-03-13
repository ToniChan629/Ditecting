package com.neu.diting;

import com.neu.diting.Util.Util;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Component
@Order(1)
public class OrderRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("The OrderRunner start to initialize ...");

        InetAddress address=InetAddress.getByName("202.199.6.55");
        byte[] buf = new byte[1024];
        //服务端在8849端口监听接收到的数据
        DatagramSocket ds = new DatagramSocket(8849,address);

        //接收从客户端发送过来的数据
        DatagramPacket dp_receive = new DatagramPacket(buf, 1024);
        System.out.println("server is on，waiting for client to send data......");
        boolean controll = true;
        while(controll){
            //服务器端接收来自客户端的数据
            ds.receive(dp_receive);
//            System.out.println("server received data from client：");
            String str_receive = new String(dp_receive.getData(),0,dp_receive.getLength());
            System.out.println(str_receive);
            Util.storeInfors(str_receive);

            dp_receive.setLength(1024);
        }
        ds.close();
    }
}