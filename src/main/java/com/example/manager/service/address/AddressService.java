package com.example.manager.service.address;

import com.example.manager.data.Address;
import com.example.manager.data.ResponseFormat;
import com.example.manager.exception.address.AddressNotFoundException;
import java.util.List;

public interface AddressService {

  public List<Address> listAllAddresses();

  public Address addressFromId(Long id) throws AddressNotFoundException;

  public ResponseFormat saveAddress(Address address);

  public ResponseFormat updateAddress(Address address, Long id);

  public ResponseFormat deleteAddress(Long id);

  public ResponseFormat deleteAllAddress();
}
