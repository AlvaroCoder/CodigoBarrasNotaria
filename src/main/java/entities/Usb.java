package entities;

import java.time.LocalDateTime;

public class Usb {
    private String id;
    private Integer clientId;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
    private String pdfPassword;

    public Usb(String id, Integer clientId, LocalDateTime creationDate, LocalDateTime lastModifiedDate, String pdfPassword){
        this.id=id;
        this.clientId=clientId;
        this.creationDate=creationDate;
        this.lastModifiedDate=lastModifiedDate;
        this.pdfPassword=pdfPassword;
    }

    public Usb(String id, Integer clientId, LocalDateTime creationDate, LocalDateTime lastModifiedDate){
        this(id,clientId,creationDate,lastModifiedDate,null);
    }

    @Override
    public String toString(){
        return String.format("Usb(id=%s,clientId=%d,creationDate=%s,lastModifiedDate=%s)",
                getId(),
                getClientId(),
                getCreationDate(),
                getLastModifiedDate());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
