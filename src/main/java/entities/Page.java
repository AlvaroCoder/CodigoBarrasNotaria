package entities;

public class Page {
    private Integer id;
    private String serialNumber;
    private Integer sectionId;
    private String path;

    public Page(Integer id, String serialNumber, Integer sectionId, String path){
        this.id=id;
        this.serialNumber=serialNumber;
        this.sectionId=sectionId;
        this.path=path;
    }

    public Page(String serialNumber, Integer sectionId, String path){
        this(null,serialNumber,sectionId,path);
    }

    @Override
    public String toString(){
        return String.format("Page(id=%s,serialNumber=%s,sectionId=%s,path=%s)",
                getId(),getSerialNumber(),getSectionId(),getPath());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
