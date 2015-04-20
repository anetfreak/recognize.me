package com.glassify.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.glassify.domain.MyCredential;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;

@Component
public class CredentialDaoImpl implements CredentialDao {
	
	@Autowired
	private DataSource dataSource;
	
	public void saveCredential(MyCredential myCredential) {
		String query = "insert into credential (user_id, access_token, refresh_token) values (?,?,?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		Object[] args = new Object[] { myCredential.getUserId(), myCredential.getCredential().getAccessToken(), myCredential.getCredential().getRefreshToken() };

		int out = jdbcTemplate.update(query, args);

		if (out != 0) {
			System.out.println("Credential added for user =" + myCredential.getUserId());
		} else {
			System.out.println("Add credential failed for user =" + myCredential.getUserId());
		}
	}

	public List<MyCredential> getCredentialForUser(final String userId) {
		String query = "select user_id, access_token, refresh_token from credential where user_id = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		return jdbcTemplate.query(query, new Object[] { userId }, new RowMapper<MyCredential>() {

					public MyCredential mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						MyCredential myCredential = new MyCredential();
						myCredential.setUserId(userId);
						
						Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod());
						credential.setAccessToken(rs.getString("access_token"));
						//credential.setRefreshToken(rs.getString("refresh_token"));
						myCredential.setCredential(credential);
						
						return myCredential;
					}
				});
		}

//	public void deleteAdById(int id) {
//
//		String query = "delete from advertisement where id=?";
//		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//
//		int out = jdbcTemplate.update(query, id);
//		if (out != 0) {
//			System.out.println("Ad deleted with id=" + id);
//		} else {
//			System.out.println("No Ad found with id=" + id);
//		}
//	}

}
