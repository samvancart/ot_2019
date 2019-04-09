/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail_list_manager.dao;

/**
 *
 * @author Sam
 */
    public interface ApiKeyDao {
    String create(String apiKey);
    String getKey();
}
