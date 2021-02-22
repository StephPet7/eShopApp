/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd.dao;


import bd.entity.Facture;

import java.util.Date;
import java.util.List;

/**
 *
 * @author TontonLaForce
 */
public interface IBill {
    public List<Facture> readBillPerDate(Date date) ;
    
}
