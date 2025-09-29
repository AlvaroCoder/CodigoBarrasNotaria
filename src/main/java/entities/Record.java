package entities;

import java.time.LocalDateTime;

public class Record {
    private Integer id;
    private String name;
    private String description;
    private Integer usbId;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
    private String path;

    public Record(Integer id, String name, String description,
                  Integer usbId, LocalDateTime creationDate, LocalDateTime lastModifiedDate,
                  String path){
        this.id = id;
        this.name = name;
        this.description = description;
        this.usbId = usbId;
        this.creationDate = creationDate;
        this.lastModifiedDate = lastModifiedDate;
        this.path=path;
    }

    public Record(String name, String description, Integer usbId, LocalDateTime creationDate, LocalDateTime lastModifiedDate,String path){
        this(null, name, description,usbId,creationDate,lastModifiedDate,path);
    }

    @Override
    public String toString(){
        return String.format("Record(id=%s,name=%s,description=%s,usbId=%d,creationDate=%s,lastModifiedDate=%s,path=%s)",
                getId(),
                getName(),
                getDescription(),
                getUsbId(),
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUsbId() {
        return usbId;
    }

    public void setUsbId(Integer usbId) {
        this.usbId = usbId;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
