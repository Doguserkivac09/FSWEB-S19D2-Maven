package com.workintech.s18d4.controller;

import com.workintech.s18d4.entity.Address;
import com.workintech.s18d4.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public List<Address> getAll() {
        return addressService.findAll();
    }

    @GetMapping("/{id}")
    public Address get(@PathVariable long id) {
        return addressService.find(id);
    }

    @PostMapping
    public Address save(@RequestBody Address address) {
        return addressService.save(address);
    }

    @PutMapping("/{id}")
    public Address update(@PathVariable long id, @RequestBody Address address) {
        Address existing = addressService.find(id);
        if (existing == null) {
            return null;
        }
        address.setId(id);
        return addressService.save(address);
    }

    @DeleteMapping("/{id}")
    public Address delete(@PathVariable long id) {
        return addressService.delete(id);
    }
}

