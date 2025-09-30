package entities;

public class Client {
    private Integer id;
    private String dni;
    private String password;
    private String firstName;
    private String lastName;
    private String email;

    public Client(Integer id, String dni,String password, String lastName, String firstName,String email){
        if (dni.length()!=8){
            throw new IllegalArgumentException("El dni debe ser de ocho dígitos");
        }
        this.id=id;
        this.dni=dni;
        this.password=password;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
    }

    public Client(Integer id, String dni,String firstName, String lastName, String email){
        this(id,dni,null,firstName,lastName,email);
        if (dni.length()!=8){
            throw new IllegalArgumentException("El dni debe ser de ocho dígitos");
        }
    }

    public Client(String dni, String password,String firstName,String lastName, String email){
        this(null,dni,password,firstName,lastName,email);
        if (dni.length()!=8){
            throw new IllegalArgumentException("El dni debe ser de ocho dígitos");
        }
    }

    @Override
    public String toString(){
        return String.format("Client(id=%d,dni=%s,firstName=%s,lastName=%s,email=%s)",
                getId(),getDni(),getFirstName(),getLastName(),getEmail());
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
