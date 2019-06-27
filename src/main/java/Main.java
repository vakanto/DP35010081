package main.java;

import ha07_ha08.Shop.ShopServer;
import ha07_ha08.Warehouse.WarehouseServer;

import java.io.IOException;

public class Main {
    public static void main (String args[]) throws IOException {
        System.out.println(args[0]);
        WarehouseServer.main(null);
        if(args[0].contains("warehouse")){
            WarehouseServer.main(null);
        }
        if(args[0].contains("shop")){
            ShopServer.main(null);
        }
    }
}
