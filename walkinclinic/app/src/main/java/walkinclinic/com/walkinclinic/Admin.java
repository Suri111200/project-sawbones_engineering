package walkinclinic.com.walkinclinic;

import java.io.Serializable;

public class Admin extends Person implements Serializable {

    public Admin (String id, String email, String password, String name)
    {
        super(id, email, password, name);
    }
}
