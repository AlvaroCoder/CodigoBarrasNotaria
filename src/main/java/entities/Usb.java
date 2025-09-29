package entities;

import java.time.LocalDateTime;

public class Usb {
    private Integer id;
    private String description;
    private Integer clientId;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
    private String pdfPassword;
    private String path;

    public Usb(Integer id, String description,
               Integer clientId, LocalDateTime creationDate,
               LocalDateTime lastModifiedDate, String pdfPassword,
               String path){
        this.id=id;
        this.description=description;
        this.clientId=clientId;
        this.creationDate=creationDate;
        this.lastModifiedDate=lastModifiedDate;
        this.pdfPassword=pdfPassword;
        this.path=path;
    }

    public Usb(Integer id, String description, LocalDateTime creationDate, LocalDateTime lastModifiedDate,String path){
        this(id,description,null,creationDate,lastModifiedDate,null,path);
    }

    public Usb(Integer id, String description,Integer clientId, LocalDateTime creationDate, LocalDateTime lastModifiedDate,String path){
        this(id,description,clientId,creationDate,lastModifiedDate,null,path);
    }

    public Usb(String description,Integer clientId,LocalDateTime creationDate, LocalDateTime lastModifiedDate, String pdfPassword, String path){
        this(null,description,clientId,creationDate,lastModifiedDate,pdfPassword,path);
    }

    @Override
    public String toString(){
        return String.format("Usb(id=%s,description=%s,clientId=%s,creationDate=%s,lastModifiedDate=%s,path=%s)",
                getId(),
                getDescription(),
                getClientId(),
                getCreationDate(),
                getLastModifiedDate(),
                getPath());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
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

    public String getPdfPassword() {
        return pdfPassword;
    }

    public void setPdfPassword(String pdfPassword) {
        this.pdfPassword = pdfPassword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}