package dao;

import entities.Usb;

import java.util.List;

public interface UsbDao {
    void insertOne(Usb usb);
    Usb findOne(String id);
    List<Usb> findMany();
    void deleteOne(int id);
    List<Usb> findByIdClient(int idClient);
}
