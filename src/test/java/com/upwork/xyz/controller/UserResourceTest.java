package com.upwork.xyz.controller;

import com.upwork.xyz.model.Store;
import com.upwork.xyz.model.User;
import com.upwork.xyz.repository.UserRepository;
import com.upwork.xyz.security.AuthoritiesConstants;
import com.upwork.xyz.service.UserService;
import com.upwork.xyz.service.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;


import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.HashSet;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
public class UserResourceTest {

    private static final String ENTITY_API_URL = "/api/user";

    private static final String DEFAULT_USERNAME = "testuser";

    private static final String DEFAULT_EMAIL = "testuser@test.com";

    private static final String DEFAULT_PASSWORD = "testtesttest";

    private static boolean DEFAULT_ENABLED = true;


    @Autowired
    private MockMvc restUserMockMvc;

    @Autowired
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

    UserDTO userDTO;
    User user;

    public static UserDTO createEntity(EntityManager em) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(DEFAULT_USERNAME);
        userDTO.setEmail(DEFAULT_EMAIL);
        userDTO.setPassword(DEFAULT_PASSWORD);
        userDTO.setEnabled(DEFAULT_ENABLED);

        return userDTO;
    }

    @BeforeEach
    public void initTest() {
        userDTO = createEntity(em);
    }

    @Test
    @Transactional
    void createUser() throws Exception {

        Store store = new Store();
        store.setStoreName("store name");
        store.setAddress("store address");
        store.setUsers(new HashSet<>());
        store.setProducts(new HashSet<>());
        em.persist(store);
        em.flush();

        userDTO.setStore_id(store.getId());

        int databaseSizeBeforeCreate = userRepository.findAll().size();
        // Create the User
        restUserMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userDTO)))
                .andExpect(status().isOk());

        // Validate the User in the database
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeCreate + 1);
        User testUser = userList.get(userList.size() - 1);
        assertThat(testUser.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUser.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testUser.getStore().getId()).isEqualTo(store.getId());
    }

    @Test
    @Transactional
    void createUserWithExistingEmail() throws Exception {

        Store store = new Store();
        store.setStoreName("store name");
        store.setAddress("store address");
        store.setUsers(new HashSet<>());
        store.setProducts(new HashSet<>());
        em.persist(store);
        em.flush();

        userDTO.setStore_id(store.getId());

        User user1 = new User();
        user1.setEmail(DEFAULT_EMAIL);
        user1.setUsername(DEFAULT_USERNAME);
        user1.setEnabled(DEFAULT_ENABLED);
        user1.setPassword("password10password10password10password10password10password10");
        user1.setStore(store);
        em.persist(user1);
        em.flush();

        // Create the User
        restUserMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userDTO)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void testGetUser()  throws Exception{

        restUserMockMvc
                .perform(get(ENTITY_API_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(jsonPath("$").value("SSSSSSSSSSSSSSSSSSSS"));

    }

}
