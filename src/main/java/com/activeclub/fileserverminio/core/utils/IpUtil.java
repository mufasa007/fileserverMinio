package com.activeclub.fileserverminio.core.utils;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;

public class IpUtil {

    public static String getIpv4(){
        return "";
    }

    public static String getIpv6(){
        try {
            String hostAddress = InetAddress.getLocalHost().getHostAddress().toString();
            System.out.println("hostAddress:" + hostAddress);
            String hostName = InetAddress.getLocalHost().getHostName();
            System.out.println("hostName:" + hostName);
            if (hostName.length() > 0) {
                InetAddress[] addrs = InetAddress.getAllByName(hostName);
                if (addrs.length > 0) {
                    for (int i = 0; i < addrs.length; i++) {
                        InetAddress address = addrs[i];
                        System.out.println("**********************");
                        System.out.println(address.getHostAddress());
                        if (address instanceof Inet6Address) {
                            System.out.println("true6");
                        } else if(address instanceof Inet4Address){
                            System.out.println("true4");
                        } else {
                            System.out.println("unknown");
                        }
                        System.out.println("**********************");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        getIpv6();
    }


}
