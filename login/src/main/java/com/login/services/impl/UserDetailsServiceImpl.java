package com.login.services.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.login.entities.UserModel;

import org.springframework.security.core.userdetails.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final List<UserModel> users = getAllUsers();

		for (UserModel user : users) {
			if (user.getUsername().equals(username)) {
				List<GrantedAuthority> grantedAuthorities = AuthorityUtils
						.commaSeparatedStringToAuthorityList("ROLE_" + user.getRole());

				return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
			}
		}

		throw new UsernameNotFoundException("Username: " + username + " not found");
	}

	private List<UserModel> getAllUsers() {
		List<UserModel> users = new ArrayList<>();
		users.add(new UserModel(1, "adminone", encoder.encode("adminone"), "ADMIN"));
		users.add(new UserModel(2, "admintwo", encoder.encode("admintwo"), "ADMIN"));
		users.add(new UserModel(3, "customerone", encoder.encode("customerone"), "CUSTOMER"));
		users.add(new UserModel(4, "customertwo", encoder.encode("customertwo"), "CUSTOMER"));
		users.add(new UserModel(4, "deliveryone", encoder.encode("deliveryone"), "DELIVERY"));
		users.add(new UserModel(4, "deliverytwo", encoder.encode("deliverytwo"), "DELIVERY"));

		return users;
	}

}
