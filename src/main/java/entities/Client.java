package entities;

public class Client {
    private Integer id;
    private String dni;
    private String password;
    private String firstName;
    private String lastName;

    public Client(Integer id, String dni,String password, String lastName, String firstName){
        if (dni.length()!=8){
            throw new IllegalArgumentException("El dni debe ser de ocho dígitos");
        }
        this.id=id;
        this.dni=dni;
        this.password=password;
        this.firstName=firstName;
        this.lastName=lastName;
    }

    public Client(Integer id, String dni,String firstName, String lastName){
        this(id,dni,null,firstName,lastName);
        if (dni.length()!=8){
            throw new IllegalArgumentException("El dni debe ser de ocho dígitos");
        }
    }

    public Client(String dni, String password,String firstName,String lastName){
        this(null,dni,password,firstName,lastName);
        if (dni.length()!=8){
            throw new IllegalArgumentException("El dni debe ser de ocho dígitos");
        }
    }

    @Override
    public String toString(){
        return String.format("Client(id=%d,dni=%s)",getId(),getDni());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
