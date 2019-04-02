package mail_list_manager.domain;

public class MailerGroup {

    private String id;
    private String name;

    public MailerGroup(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
    
    

}
