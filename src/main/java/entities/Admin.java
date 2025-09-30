package entities;

public class Admin {
    private Integer id;
    private String username;
    private String password;
    private String email;

    public Admin(Integer id, String username, String password, String email){
        this.id = id;
        this.username=username;
        this.password=password;
        this.email = email;
    }

    public Admin(Integer id, String username,String email){
        this(id,username,null,email);
    }

    public Admin(String username, String password, String email){
        this(null,username,password,email);
    }

    @Override
    public String toString(){
        return String.format("Admin(id=%d,username=%s,email=%s)",getId(),getUsername(),getEmail());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
