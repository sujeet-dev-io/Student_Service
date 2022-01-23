package com.studentapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user")
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id=?")
@Where(clause = "deleted=false OR deleted is null")
public class UserEntity extends BaseEntity {
  
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
	
    @Column
    private String username;
    
    @Column
    @JsonIgnore
    private String password;
    
    @Column
    private String firstName;
    
    @Column
    private String lastName;
    
    @Column
    private String mobileNumber;
    
    @Column
    private String emailId;
    
	@Column(name="deleted")
	private Boolean deleted = Boolean.FALSE;
}
