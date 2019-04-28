
package mail_list_manager.domain;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MailerGroupTest {

    ArrayList<MailerGroup> groups = new ArrayList<>();

    public MailerGroupTest() {

    }

    @Before
    public void setUp() {
        MailerGroup g1 = new MailerGroup("1a2", "group1");
        MailerGroup g2 = new MailerGroup("4h6", "group2");
        groups.add(g1);
        groups.add(g2);
    }

    @Test
    public void correctId() {
        assertEquals("1a2", groups.get(0).getId());
        assertEquals("4h6", groups.get(1).getId());
    }

    @Test
    public void correctName() {
        assertEquals("group1", groups.get(0).getName());
        assertEquals("group2", groups.get(1).getName());
    }
}
