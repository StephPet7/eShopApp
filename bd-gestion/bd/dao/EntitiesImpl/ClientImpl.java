/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd.dao.EntitiesImpl;

import bd.SingletonConnection.SingletonConnection;
import bd.dao.IClient;
import bd.entity.Client;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author TontonLaForce
 */
public class ClientImpl implements IClient {
    EntityManager em = SingletonConnection.getConnection();
    

    @Override
    public void createClient(String name, String tel, String address) {
        try{
            Query query = em.createQuery("select c from Client c where c.nom= :name and c.tel= :tel and c.adresse= :address ");
            query.setParameter("name" , name);
            query.setParameter("tel" , tel);
            query.setParameter("address" , address);
            List<Client> clients = (List<Client>) query.getResultList();
            if(clients.isEmpty() ){
                SingletonConnection.getEntityTransaction().begin();
                em.persist(new Client(name , tel , address , 200 , false ));
                SingletonConnection.getEntityTransaction().commit();
                System.out.println("Client Ajoutée");
            }
            else{
                System.out.println("Acces refusee");
            }
        }
        catch(IllegalStateException e){
                SingletonConnection.getEntityTransaction().rollback();
        }
    }

    @Override
    public void updateClient(long clientCode, Client newInfo) {
        try{
            Client c = em.find(Client.class, (int)clientCode);
            if( c != null ){
                SingletonConnection.getEntityTransaction().begin();
                c.setNom(newInfo.getNom());
                c.setTel(newInfo.getTel());
                c.setAdresse(newInfo.getAdresse());
                c.setBonus(newInfo.getBonus());
                c.setActif(newInfo.getActif());
                SingletonConnection.getEntityTransaction().commit();
                System.out.println("Client updated");
                
            }else{
                System.out.println("Client introuvable");
            }
            
        }catch(IllegalStateException e){
            SingletonConnection.getEntityTransaction().rollback();
        }
    }

    @Override
    public void deleteClient(long clientCode) {
        try{
            Client c = em.find(Client.class, (int)clientCode);
            if( c!= null ){
                SingletonConnection.getEntityTransaction().begin();
                em.remove(c);
                SingletonConnection.getEntityTransaction().commit();
                System.out.println("Client deleted");
            }else{
                System.out.println("Client Introuvable");
            }
        }catch(IllegalStateException e){
            SingletonConnection.getEntityTransaction().rollback();
        }
    }

    @Override
    public List<Client> readClient() {
        return em.createQuery("select c from Client c").getResultList();
        
    }
    
}
