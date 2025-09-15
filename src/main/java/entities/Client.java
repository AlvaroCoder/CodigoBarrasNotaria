package entities;

public class Client {
    private Integer id;
    private String username;
    private String password;

    public Client(Integer id, String username,String password){
        this.id=id;
        this.username=username;
        this.password=password;
    }

    public Client(Integer id, String username){
        this(id,username,null);
    }

    @Override
    public String toString(){
        return String.format("Client(id=%d,username=%s)",getId(),getUsername());
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
}
