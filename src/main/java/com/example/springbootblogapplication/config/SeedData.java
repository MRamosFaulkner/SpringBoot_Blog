package com.example.springbootblogapplication.config;

import com.example.springbootblogapplication.models.Account;
import com.example.springbootblogapplication.models.Authority;
import com.example.springbootblogapplication.models.Post;
import com.example.springbootblogapplication.repositories.AuthorityRepository;
import com.example.springbootblogapplication.services.AccountService;
import com.example.springbootblogapplication.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Post> posts = postService.getAll();

        if (posts.size() == 0) {

            Authority user = new Authority();
            user.setName("ROLE_USER");
            authorityRepository.save(user);

            Authority admin = new Authority();
            admin.setName("ROLE_ADMIN");
            authorityRepository.save(admin);

            Account account1 = new Account();
            Account account2 = new Account();

            account1.setFirstName("Jimmy");
            account1.setLastName("Jones");
            account1.setEmail("jimmyjones@mail.com");
            account1.setPassword("password");
            Set<Authority> authorities1 = new HashSet<>();
            authorityRepository.findById("ROLE_USER").ifPresent(authorities1::add);
            account1.setAuthorities(authorities1);


            account2.setFirstName("Lina");
            account2.setLastName("Smith");
            account2.setEmail("linasmith@mail.com");
            account2.setPassword("password2");

            Set<Authority> authorities2 = new HashSet<>();
            authorityRepository.findById("ROLE_ADMIN").ifPresent(authorities2::add);
            authorityRepository.findById("ROLE_USER").ifPresent(authorities2::add);
            account2.setAuthorities(authorities2);

            accountService.save(account1);
            accountService.save(account2);

            Post post1 = new Post();
            post1.setTitle("A fun trip?");
            post1.setBody("Trying to think of a fun trip to do. Where should I go? I am thinking of something tropical or some exotic island. Let me know your thoughts. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. ");
            post1.setAccount(account1);

            Post post2 = new Post();
            post2.setTitle("Doggy Daycare");
            post2.setBody("Trying to find a place that will take care of Spot. Know of a place? Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. ");
            post2.setAccount(account2);

            postService.save(post1);
            postService.save(post2);
        }
    }

}
