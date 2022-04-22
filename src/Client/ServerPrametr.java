/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

/**
 *
 * @author jakub
 */
public class ServerPrametr {

    private final String name;
    private final String ip;
    private final int port;

    public ServerPrametr(String name, String ip, int port) {
        this.name = name;
        this.ip = ip;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    @Override
    public boolean equals(Object obj) {
        ServerPrametr par = (ServerPrametr) obj;
        return ip.equals(par.getIp()) && port == par.getPort() && name.equals(par.getName());
    }

}
