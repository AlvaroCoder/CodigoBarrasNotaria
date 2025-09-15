package entities;

public class Record {
    private String id;
    private String name;
    private String description;
    private Integer clientId;
    private String usbId;

    public Record(String id, String name, String description, Integer clientId,String usbId){
        this.id=id;
        this.name=name;
        this.description=description;
        this.clientId=clientId;
        this.usbId=usbId;
    }

    @Override
    public String toString(){
        return String.format("Record(id=%s,name=%s,description=%s,clientId=%d,usbId=%s)",
                getId(), getName(),getDescription(),getClientId(),getUsbId());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getUsbId() {
        return usbId;
    }

    public void setUsbId(String usbId) {
        this.usbId = usbId;
    }
}
