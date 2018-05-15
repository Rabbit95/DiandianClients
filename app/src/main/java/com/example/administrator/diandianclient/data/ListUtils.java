package com.example.administrator.diandianclient.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/7.
 */

public class ListUtils {
    public static List<Cart> carts = new ArrayList<>();
    public static List<Order> orders = new ArrayList<>();
    public static List<User> users = new ArrayList<>();
    public static String name;
    public static String phone;

    public void add(Cart cart){
        carts.add(cart);
    }
    public void remove(Cart cart){
        carts.remove(cart);
    }
    public static int size(){
        return carts.size();
    }
    public static int getID(int i){
        return carts.get(i).getId();
    }

    public void oadd(Order order){
        orders.add(order);
    }
    public static int osize(){
        return orders.size();
    }
    public static String getONumber(int i){
        return orders.get(i).getONumber();
    }

    public static void uadd(User user){
        users.add(user);
    }
    public static void uc(){
        users.clear();
    }


}
