package com.tentochu.tenphammem.demo.controller;

import java.util.List;
import java.util.Optional;


import com.tentochu.tenphammem.demo.dto.BrandDTO;
import com.tentochu.tenphammem.demo.dto.ResponseObject;
import com.tentochu.tenphammem.demo.entities.Brands;
import com.tentochu.tenphammem.demo.mapper.BrandMapper;
import com.tentochu.tenphammem.demo.model.AuthenticationRequest;
import com.tentochu.tenphammem.demo.model.AuthenticationResponse;
import com.tentochu.tenphammem.demo.service.BrandsService;
import com.tentochu.tenphammem.demo.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
//@RequestMapping("/brands")
public class BrandController {


    private final BrandsService brandsService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;


    private final JwtUtil jwtUtil;

    @GetMapping("/{id}")
    // public ResponseEntity<BrandDTO> findById(@PathVariable Long id) {
    // Optional<Brands> brand = brandsService.findById(id);
    // BrandDTO brandDTO = BrandMapper.INSTANCE.toBrandDTO(brand.get());
    // return ResponseEntity.ok(brandDTO);
    // }
    public ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Brands> brand = brandsService.findById(id);

        if (brand.isPresent()) {
            BrandDTO brandDTO = BrandMapper.INSTANCE.toBrandDTO(brand.get());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ok", "truy van brand thanh cong", brandDTO));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("false", "ko the tim thay id = " + id, ""));
        }
    }

    @GetMapping
    public ResponseEntity<List<BrandDTO>> findAll() {
        List<Brands> brands = brandsService.findAll();
        List<BrandDTO> brandList = BrandMapper.INSTANCE.toBrandDTOs(brands);
        return ResponseEntity.ok(brandList);

    }

    @PostMapping
    public ResponseEntity<ResponseObject> create(@RequestBody BrandDTO brandDTO) {
        List<Brands> listBrands = brandsService.findByBrandName(brandDTO.getBrandName().trim());

        if (listBrands.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(new ResponseObject("failed", "da trung ten", ""));
        } else {

            Brands brand = BrandMapper.INSTANCE.toBrands(brandDTO);
            brandsService.save(brand);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseObject("ok", "insert thanh cong", brandDTO));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        boolean exists = brandsService.existsById(id);

        if (exists) {
            brandsService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "xoa thanh cong", ""));
        }

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ResponseObject("failed", "ko tim thay sp de xoa", ""));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable Long id, @RequestBody BrandDTO brandDTO) {
        Brands brand = BrandMapper.INSTANCE.toBrands(brandDTO);
        brand.setId(id);
        brandsService.save(brand);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new ResponseObject("ok", "cap nhat thanh cong", brandDTO));
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("incorrect name or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
