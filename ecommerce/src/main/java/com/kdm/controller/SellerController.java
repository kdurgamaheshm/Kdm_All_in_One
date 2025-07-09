package com.kdm.controller;

import com.kdm.config.JwtProvider;
import com.kdm.domain.AccountStatus;
import com.kdm.model.Seller;
import com.kdm.model.SellerReport;
import com.kdm.model.VerificationCode;
import com.kdm.repository.VerificationCodeRepository;
import com.kdm.request.LoginRequest;
import com.kdm.response.ApiResponse;
import com.kdm.response.AuthResponse;
import com.kdm.service.AuthService;
import com.kdm.service.EmailService;
import com.kdm.service.SellerService;
import com.kdm.utils.OtpUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController {
    private final SellerService sellerService;
    private final VerificationCodeRepository verificationCodeRepository;
    private final AuthService authService;
    private final EmailService emailService;
    private final JwtProvider jwtProvider;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> LoginSeller(
            @RequestBody LoginRequest req) throws Exception {

        String otp=req.getOtp();
        String email=req.getEmail();

//        VerificationCode verificationCode=verificationCodeRepository.findByEmail(email);
//        if(verificationCode==null && !verificationCode.getOtp().equals(req.getOtp())) {
//            throw new Exception("Wrong Otp...");
//        }
        req.setEmail("seller_"+email);
        System.out.println(otp+" - "+email);
        AuthResponse authResponse=authService.signing(req);
        return ResponseEntity.ok(authResponse);
    }
    @PatchMapping("/verify/{otp}")
    public ResponseEntity<Seller> VerifySellerEmail(@PathVariable String otp)
            throws Exception{
        VerificationCode verificationCode=verificationCodeRepository.findByOtp((otp));
        if(verificationCode==null ||!verificationCode.getOtp().equals(otp)){
            throw new Exception("Wrong otp...");
        }
        Seller seller =sellerService.verifyEmail(verificationCode.getEmail(),otp);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller)
            throws Exception, MessagingException {
        Seller savedSeller=sellerService.createSeller(seller);
        String otp = OtpUtil.generateOtp();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(seller.getEmail());
        verificationCodeRepository.save(verificationCode);

        String subject="KDM all in one Email verification code";
        String text="Welcome to KDM all in one ,Verify your account using this link ";
        String frontend_url="http://localhost:5252/verify-seller/";
        emailService.sendVerificationOtpEmail(seller.getEmail(),verificationCode.getOtp(),subject,text+frontend_url);
        return new ResponseEntity<>(savedSeller, HttpStatus.CREATED);
    }
    @PostMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id) throws Exception{
        Seller seller=sellerService.getSellerById(id);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }
    @GetMapping("/profile")
    public ResponseEntity<Seller> getSellerByJwt(
            @RequestHeader("Authorization") String jwt) throws Exception{
        Seller seller=sellerService.getSellerProfile(jwt);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }
//    @GetMapping("/report")
//    public ResponseEntity<SellerReport> getSellerReport(
//            @RequestHeader("Authorization") String jwt) throws Exception{
//        String email=jwtProvider.getEmailFromJwtToken(jwt);
//        Seller seller=sellerService.getSellerByEmail(email);
//        SellerReport sellerReport= SellerReportService.getSellerReport(Seller);
//        return new ResponseEntity<>(sellerReport, HttpStatus.OK);
//    }
    @GetMapping
    public ResponseEntity<List<Seller>> getAllSellers(
            @RequestParam(required = false)AccountStatus status){
        List<Seller> sellers=sellerService.getAllSellers(status);
        return ResponseEntity.ok(sellers);
    }
    @PatchMapping()
    public ResponseEntity<Seller> updateSeller(
            @RequestHeader("Authorization") String jwt,
            @RequestBody Seller seller) throws Exception{
        Seller profile= sellerService.getSellerProfile(jwt);
        Seller updatedSeller =sellerService.updateSeller(profile.getId(), seller);
        return ResponseEntity.ok(updatedSeller);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Seller> deleteSeller(@PathVariable Long id) throws Exception{
        sellerService.deleteSeller(id);
        return ResponseEntity.noContent().build();

    }


}
