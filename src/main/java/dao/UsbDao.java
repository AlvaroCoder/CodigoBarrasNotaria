package dao;

import dto.UsbClientDto;
import entities.Usb;

import java.util.List;

public interface UsbDao {
    Integer insertOne(Usb usb);
    Usb findOne(int id);
    List<Usb> findMany();
    void deleteOne(int id);
    List<UsbClientDto> findByIdClient(int idClient);
}
