
package mail_list_manager.domain;

import mail_list_manager.dao.ApiKeyDao;

public class ApiKeyService {

    private ApiKeyDao keyDao;

    public ApiKeyService(ApiKeyDao keyDao) {
        this.keyDao = keyDao;
    }

    public boolean createKey(String key) {
        try {
            keyDao.create(key);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public String getKey() {
        String key="";
        try {
            key = keyDao.getKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }
}
