package com.upwork.xyz.controller;

import com.upwork.xyz.model.Authority;
import com.upwork.xyz.model.Product;
import com.upwork.xyz.model.Store;
import com.upwork.xyz.model.User;
import com.upwork.xyz.repository.ProductRepository;
import com.upwork.xyz.repository.StoreRpository;
import com.upwork.xyz.repository.UserRepository;
import com.upwork.xyz.security.AuthoritiesConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;


import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.EMPLOYEE , username = "testUser")
public class ProductResourceTest {

    private static final String ENTITY_API_URL = "/api/product";
    private static final String ENTITY_API_URL_ID = "/api/product/{id}";


    private static final String DEFAULT_Product_Name = "AAAAAAAAAA";
    private static final String UPDATED_Product_Name = "BBBBBBBBBB";

    private static final String USER_NAME = "testUser";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRpository storeRpository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc restProductMockMvc;

    @Autowired
    private EntityManager em;

    Product product = new Product();

    public static Product createEntity(EntityManager em) {
        Product product = new Product();
        product.setProductName(DEFAULT_Product_Name);

        return product;
    }

    @BeforeEach
    public void initTest() {
        product = createEntity(em);
    }

    @Test
    @Transactional
    void createProduct() throws Exception {

        Store store = new Store();
        store.setStoreName("store name");
        store.setAddress("store address");
        store.setUsers(new HashSet<>());
        store.setProducts(new HashSet<>());
        em.persist(store);
        em.flush();

        User user = new User();
        user.setUsername(USER_NAME);
        user.setEmail("test@test.com");
        user.setPassword("password10password10password10password10password10password10");
        user.setEnabled(true);
        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.EMPLOYEE);
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);
        user.setAuthorities(authorities);
        System.out.println(user);
        user.setStore(store);
        em.persist(user);
        em.flush();


        int databaseSizeBeforeCreate = productRepository.findAll().size();
        // Create the Product
        restProductMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(product)))
                .andExpect(status().isCreated());

        // Validate the Match in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getProductName()).isEqualTo(DEFAULT_Product_Name);
        assertThat(testProduct.getStoreProducts().stream().collect(Collectors.toList()).get(0).getId()).isEqualTo(store.getId());

    }


    @Test
    @Transactional
    void updateProduct() throws Exception {

        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findById(product.getId()).get();
        // Disconnect from session so that the updates on updatedFile are not directly saved in db
        em.detach(updatedProduct);


        updatedProduct.setProductName(UPDATED_Product_Name);

        restProductMockMvc
                .perform(
                        put(ENTITY_API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(updatedProduct))
                )
                .andExpect(status().isOk());

        // Validate the File in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getProductName()).isEqualTo(UPDATED_Product_Name);

    }

    @Test
    @Transactional
    void updateProductWithZeroId() throws Exception {

        // Initialize the database
        productRepository.saveAndFlush(product);

        // Update the product
        Product updatedProduct = productRepository.findById(product.getId()).get();
        // Disconnect from session so that the updates on updatedFile are not directly saved in db
        em.detach(updatedProduct);

        updatedProduct.setProductName(UPDATED_Product_Name);
        updatedProduct.setId(0l);

        restProductMockMvc
                .perform(
                        put(ENTITY_API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(updatedProduct))
                )
                .andExpect(status().isNotFound());

    }

    @Test
    @Transactional
    void updateProductWithNullProductDto() throws Exception {

        // Initialize the database
        productRepository.saveAndFlush(product);

        restProductMockMvc
                .perform(
                        put(ENTITY_API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(null))
                )
                .andExpect(status().isBadRequest());

    }

    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.EMPLOYEE , username = "testUser" )
    void searchWithEmployee() throws Exception {

        Store store = new Store();
        store.setStoreName("store name");
        store.setAddress("store address");
        store.setUsers(new HashSet<>());
        store.setProducts(new HashSet<>());
        em.persist(store);
        em.flush();

        Set<Store> stores = new HashSet<>();
        stores.add(store);

        product.setStoreProducts(stores);

        // Initialize the database
        productRepository.saveAndFlush(product);

        User user = new User();
        user.setUsername(USER_NAME);
        user.setEmail("test@test.com");
        user.setPassword("password10password10password10password10password10password10");
        user.setEnabled(true);
        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.EMPLOYEE);
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);
        user.setAuthorities(authorities);
        System.out.println(user);
        user.setStore(store);
        em.persist(user);
        em.flush();

        Store store2 = new Store();
        store2.setStoreName("store name 2");
        store2.setAddress("store address 2");
        store2.setUsers(new HashSet<>());
        store2.setProducts(new HashSet<>());
        em.persist(store2);
        em.flush();

        Set<Store> stores2 = new HashSet<>();
        stores.add(store2);

        Product product2 = new Product();
        product2.setProductName(UPDATED_Product_Name);

        product2.setStoreProducts(stores2);
        em.persist(product2);
        em.flush();

        // Get all the productList
        restProductMockMvc
                .perform(get("/api/search"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
                .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_Product_Name)))
                .andExpect(jsonPath("$.[*].id").value(not(hasItem(product2.getId().intValue()))))
                .andExpect(jsonPath("$.[*].productName").value(not(hasItem(UPDATED_Product_Name))));
    }

    @Test
    @Transactional
    @WithMockUser(authorities = AuthoritiesConstants.ADMIN , username = "testUserAdmin" )
    void searchWithAdmin() throws Exception {

        Store store = new Store();
        store.setStoreName("store name");
        store.setAddress("store address");
        store.setUsers(new HashSet<>());
        store.setProducts(new HashSet<>());
        em.persist(store);
        em.flush();

        Set<Store> stores = new HashSet<>();
        stores.add(store);

        product.setStoreProducts(stores);

        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc
                .perform(get("/api/search"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
                .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_Product_Name)));
    }

    @Test
    @Transactional
    void deleteExpense() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Delete the product
        restProductMockMvc
                .perform(delete(ENTITY_API_URL_ID, product.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Product> expenseList = productRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeDelete - 1);
    }



}
