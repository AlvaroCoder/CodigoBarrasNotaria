package dto;

import java.time.LocalDateTime;

public class UsbClientDto {
    private Integer usbId;
    private String description;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
    private Integer clientId;
    private String dni;
    private String email;
    private String firstName;
    private String lastName;
    private String path;

    public UsbClientDto(Integer usbId, String description,
                        LocalDateTime creationDate, LocalDateTime lastModifiedDate,
                        Integer clientId,String dni, String email,
                        String firstName, String lastName, String path){
        this.usbId=usbId;
        this.description=description;
        this.creationDate=creationDate;
        this.lastModifiedDate=lastModifiedDate;
        this.clientId=clientId;
        this.dni=dni;
        this.email=email;
        this.firstName=firstName;
        this.lastName=lastName;
        this.path = path;
    }

    public UsbClientDto(Integer usbId,String dni, LocalDateTime creationDate, LocalDateTime lastModifiedDate){
        this(usbId,null,creationDate,lastModifiedDate,null,dni,null,null,null,null);
    }

    @Override
    public String toString(){
        return String.format("UsbClientDto(usbId=%d,description=%s,creationDate=%s,lastModifiedDate=%s,path=%s," +
                "clientId=%d,dni=%s,email=%s,firstName=%s,lastName=%s)",
                getUsbId(),getDescription(),getCreationDate(),getLastModifiedDate(),getPath(),
                getClientId(),getDni(),getEmail(),getFirstName(),getLastName());
    }

    public Integer getUsbId() {
        return usbId;
    }

    public void setUsbId(Integer usbId) {
        this.usbId = usbId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
