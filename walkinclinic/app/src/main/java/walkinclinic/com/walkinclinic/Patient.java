package walkinclinic.com.walkinclinic;

import java.io.Serializable;

public class Patient extends Person implements Serializable {

  public Patient (String id, String email, String password, String name)
  {
      super(id, email, password, name);
  }
}
