package com.example.achint.ecommerce.Model;
import com.google.gson.annotations.SerializedName;


public class Users{

	@SerializedName("userid")
	private Long userId;

	@SerializedName("firstname")
	private String firstname;

	@SerializedName("password")
	private String password;

	@SerializedName("address")
	private String address;

	@SerializedName("contact")
	private String contact;

	@SerializedName("email")
	private String email;

	@SerializedName("lastname")
	private String lastname;

    public Users(String firstname, String password, String address, String contact, String email, String lastname) {
        this.firstname = firstname;
        this.password = password;
        this.address = address;
        this.contact = contact;
        this.email = email;
        this.lastname = lastname;
    }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Users(String email, String password){
        this.email = email;
        this.password = password;
    }

    public Users(){}

    public void setFirstname(String firstname){
		this.firstname = firstname;
	}

	public String getFirstname(){
		return firstname;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setContact(String contact){
		this.contact = contact;
	}

	public String getContact(){
		return contact;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setLastname(String lastname){
		this.lastname = lastname;
	}

	public String getLastname(){
		return lastname;
	}

	@Override
 	public String toString(){
		return 
			"Users{" + 
			"firstname = '" + firstname + '\'' + 
			",password = '" + password + '\'' + 
			",address = '" + address + '\'' + 
			",contact = '" + contact + '\'' + 
			",email = '" + email + '\'' + 
			",lastname = '" + lastname + '\'' + 
			"}";
		}
}