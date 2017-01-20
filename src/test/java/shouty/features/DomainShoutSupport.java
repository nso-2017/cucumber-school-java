package shouty.features;

import java.util.List;

public class DomainShoutSupport extends ShoutSupport {

    @Override
    public void seanShout(String message) {
        if( getPeople().get("Sean").canShout(message)) {
            getPeople().get("Sean").shout(message);
            String personName = "Sean";

            rememberMessageShoutedBy(message, personName);
        }

    }

    @Override
    public List<String> getMessagesHeardBy(String name) {
        return getPeople().get(name).getMessagesHeard();
    }

    @Override
    public int getCreditsOf(String name) {
        return getPeople().get(name).getCredits();
    }
}
