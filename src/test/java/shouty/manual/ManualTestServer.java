package shouty.manual;

import shouty.core.Network;
import shouty.core.Person;
import shouty.web.ShoutyServer;

import java.util.HashMap;
import java.util.Map;

public class ManualTestServer {

    public static void main(String[] args) throws Exception {
        Network network = new Network(100);
        Map<String, Person> people = new HashMap<String, Person>() {{
            Person Sean = new Person(network, 0);
            Sean.setCredits(15);
            Person Lucy = new Person(network, 100);
            Lucy.setCredits(100);

            put("Sean", Sean);
            put("Lucy",Lucy);
            put("Larry", new Person(network, 150));
        }};
        new ShoutyServer().start(people, 3000);
    }

}
