package entities;

import java.time.LocalDateTime;

public class Section {
    private Integer id;
    private String name;
    private Integer recordId;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
    private String path;

    public Section(Integer id, String name, Integer recordId, LocalDateTime creationDate, LocalDateTime lastModifiedDate,String path){
        this.id = id;
        this.name= name;
        this.recordId= recordId;
        this.creationDate=creationDate;
        this.lastModifiedDate=lastModifiedDate;
        this.path=path;
    }

    public Section(String name, Integer recordId, LocalDateTime creationDate, LocalDateTime lastModifiedDate,String path){
        this(null,name,recordId,creationDate,lastModifiedDate,path);
    }

    @Override
    public String toString(){
        return String.format("Section(id=%d,name=%s,recordId=%d,creationDate=%s,lastModifiedDate=%s,path=%s)",
                getId(),getName(),getRecordId(),getCreationDate(),getLastModifiedDate(),getPath());
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

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
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
