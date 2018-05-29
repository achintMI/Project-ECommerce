package com.ecommerce.ECommerce.Repository;

import com.ecommerce.ECommerce.DTO.UserDto;
import com.ecommerce.ECommerce.Model.UserModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserModel,String>{
    boolean existsByEmail(String email);
    UserModel findByEmail(String email);
    UserModel findByVerificationToken(String verificationToken);
}