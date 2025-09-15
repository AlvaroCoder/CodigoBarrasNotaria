package entities;

public class Page {
    private String id;
    private String serialNumber;
    private String recordId;
    private String path;

    public Page(String id, String serialNumber, String recordId, String path){
        this.id=id;
        this.serialNumber=serialNumber;
        this.recordId=recordId;
        this.path=path;
    }

    public String toString(){
        return String.format("Page(id=%s,serialNumber=%s,recordId=%s,path=%s)",
                getId(),getSerialNumber(),getRecordId(),getPath());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
