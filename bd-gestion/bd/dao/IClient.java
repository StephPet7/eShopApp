/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd.dao;


import bd.entity.Client;

import java.util.List;

/**
 *
 * @author TontonLaForce
 */
public interface IClient {
    public void createClient(String name, String tel, String address);
    public void updateClient(long clientCode, Client newInfo) ;
    public void deleteClient(long clientCode);
    public List<Client> readClient() ;
}
