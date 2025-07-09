package com.kdm.service.impl;

import com.kdm.config.JwtProvider;
import com.kdm.domain.AccountStatus;
import com.kdm.domain.USER_ROLE;
import com.kdm.model.Address;
import com.kdm.model.Seller;
import com.kdm.repository.AddressRepository;
import com.kdm.repository.SellerRepository;
import com.kdm.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private  final AddressRepository addressRepository;

    @Override
    public Seller getSellerProfile(String jwt) throws Exception {
        String email=jwtProvider.getEmailFromJwtToken(jwt);

        return this.getSellerByEmail(email);
    }

    @Override
    public Seller createSeller(Seller seller) throws Exception {
        Seller sellerExist= (Seller) sellerRepository.findByEmail(seller.getEmail());
        if(sellerExist!=null){
            throw new Exception("Seller already exist, use different email");
        }
        Address savedAddress=addressRepository.save(seller.getPickUpAddress());
        Seller newSeller=new Seller();
        newSeller.setPassword(passwordEncoder.encode(seller.getPassword()));
        newSeller.setEmail(seller.getEmail());
        newSeller.setSellerName(seller.getSellerName());
        newSeller.setPickUpAddress( savedAddress);
        newSeller.setGSTIN(seller.getGSTIN());
        newSeller.setRole(USER_ROLE.ROLE_SELLER);
        newSeller.setMobile(seller.getMobile());
        newSeller.setBankDetails(seller.getBankDetails());
        newSeller.setBusinessDetails(seller.getBusinessDetails());


        return sellerRepository.save(newSeller);
    }

    @Override
    public Seller getSellerById(Long id) throws Exception {

        return sellerRepository.findById(id)
                .orElseThrow(()-> new Exception("Seller not found with id "+id));

    }

    @Override
    public Seller getSellerByEmail(String email) throws Exception {
        Seller seller = (Seller) sellerRepository.findByEmail(email);
        if(seller == null) {
            throw new Exception("Seller not found...");
        }
        return seller;
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus status) {
        return sellerRepository.findAllByAccountStatus(status);
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) throws Exception {
        Seller existingSeller=this.getSellerById(id);
        if(seller.getSellerName()!=null){
            existingSeller.setSellerName(seller.getSellerName());
        }
        if(seller.getMobile()!=null){
            existingSeller.setMobile(seller.getMobile());
        }
        if(seller.getEmail()!=null){
            existingSeller.setEmail(seller.getEmail());
        }
        if(seller.getBusinessDetails()!=null
                && seller.getBusinessDetails().getBusinessName()!=null){
            existingSeller.getBusinessDetails().setBusinessName(
                    seller.getBusinessDetails().getBusinessName());
        }
        if(seller.getBankDetails()!=null
                && seller.getBankDetails().getAccountHolderName()!=null
                && seller.getBankDetails().getIfscCode()!=null
                && seller.getBankDetails().getAccountNumber()!=null){
            existingSeller.getBankDetails().setAccountHolderName(
                    seller.getBankDetails().getAccountHolderName());
            existingSeller.getBankDetails().setAccountNumber(
                    seller.getBankDetails().getAccountNumber());
            existingSeller.getBankDetails().setIfscCode(
                    seller.getBankDetails().getIfscCode());
        }
        if(seller.getPickUpAddress()!=null
                && seller.getPickUpAddress().getCity()!=null
                && seller.getPickUpAddress().getState()!=null
                && seller.getPickUpAddress().getMobile()!=null
                && seller.getPickUpAddress().getAddress()!=null){
            existingSeller.getPickUpAddress().setCity(seller.getPickUpAddress().getCity());
            existingSeller.getPickUpAddress().setState(seller.getPickUpAddress().getState());
            existingSeller.getPickUpAddress().setMobile(seller.getPickUpAddress().getMobile());
            existingSeller.getPickUpAddress().setAddress(seller.getPickUpAddress().getAddress());
        }
        if(seller.getGSTIN()!=null){
            existingSeller.setGSTIN(seller.getGSTIN());
        }
        return sellerRepository.save(existingSeller);
    }

    @Override
    public void deleteSeller(Long id) throws Exception {
        Seller seller=getSellerById(id);
        sellerRepository.delete(seller);
    }

    @Override
    public Seller verifyEmail(String email, String otp) throws Exception {
        Seller seller=getSellerByEmail(email);
        seller.setEmailVerified(true);

        return sellerRepository.save(seller);
    }

    @Override
    public Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) throws Exception {
        Seller seller=getSellerById(sellerId);
        seller.setAccountStatus(status);

        return sellerRepository.save(seller);
    }
}
