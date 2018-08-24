package spittr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import spittr.data.SpitterRepository;
import spittr.security.SpitterUserInterface;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	SpitterRepository spitterRepository;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(new SpitterUserInterface(spitterRepository));
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.formLogin()
		.and()
		.authorizeRequests()
			.antMatchers("/spitters/me").hasAuthority("ROLE_SPITTER")
			.antMatchers(HttpMethod.GET, "/spittles").hasAuthority("ROLE_SPITTER")
			.anyRequest().permitAll()
		.and()
		.requiresChannel()
			.antMatchers("/").requiresInsecure()
			/*to enable HTTPS -> needs update in Tomcat also
			.antMatchers("/spitter/register").requiresSecure()*/
		.and()
			.csrf()
			.disable();
	}
}
