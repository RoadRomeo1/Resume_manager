package com.example.manager.service.address;

import com.example.manager.data.Address;
import com.example.manager.data.ResponseFormat;
import com.example.manager.exception.address.AddressNotFoundException;
import com.example.manager.repository.address.AddressRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

  @Autowired private ResponseFormat format;
  @Autowired private AddressRepository repo;

  @Override
  public List<Address> listAllAddresses() {
    List<Address> addressDataList = new ArrayList<>();
    repo.findAll().forEach(addressDataList::add);
    return addressDataList;
  }

  @Override
  public Address addressFromId(Long id) throws AddressNotFoundException {
    Optional<Address> addressData = repo.findById(id);
    if (addressData.isEmpty()) {
      throw new AddressNotFoundException("Address with " + id + " is not found");
    }
    return addressData.get();
  }

  @Override
  public ResponseFormat saveAddress(Address address) {
    repo.save(address);

    format.setStatus(HttpStatus.CREATED.value());
    format.setMessage("Record Created");
    format.setTimeStamp(System.currentTimeMillis());
    return format;
  }

  @Override
  public ResponseFormat updateAddress(Address address, Long id) {
    address.setId(id);
    repo.save(address);
    format.setStatus(HttpStatus.OK.value());
    format.setMessage("Record Updated");
    format.setTimeStamp(System.currentTimeMillis());

    return format;
  }

  @Override
  public ResponseFormat deleteAddress(Long id) {
    repo.deleteById(id);
    format.setStatus(HttpStatus.OK.value());
    format.setMessage("Record Deleted");
    format.setTimeStamp(System.currentTimeMillis());

    return format;
  }

  @Override
  public ResponseFormat deleteAllAddress() {
    repo.deleteAll();
    format.setStatus(HttpStatus.OK.value());
    format.setMessage("All records deleted");
    format.setTimeStamp(System.currentTimeMillis());
    return format;
  }
}
