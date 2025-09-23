package com.example.ProductService.Service;

import com.example.ProductService.DOA.ProductRepo;
import com.example.ProductService.DTO.ProductQuantityRequest;
import com.example.ProductService.DTO.ProductRequest;
import com.example.ProductService.DTO.ProductResponse;
import com.example.ProductService.GlobalExceptionHandler.ResourceNotFoundException;
import com.example.ProductService.GlobalExceptionHandler.UnauthorizedException;
import com.example.ProductService.Model.Product;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepo productRepo;

    @Mock
    StreamBridge streamBridge;

    @InjectMocks
    ProductService productService;


    Product sampleProduct;

    @BeforeEach
    public void setUpProduct(){
        sampleProduct = new Product();
        sampleProduct.setId(1);
        sampleProduct.setName("sampleProduct1");
        sampleProduct.setActive(true);
        sampleProduct.setSeller("sampleSeller1");
        sampleProduct.setStockQuantity(10);
        sampleProduct.setPrice(new BigDecimal("400"));
        sampleProduct.setCategory("sampleCategory");
        sampleProduct.setDescription("sampleDescription");
        sampleProduct.setImageUrl("sampleUrl");
    }


    @Test
    void getAllProductsTest(){
        when(productRepo.findByActiveTrue()).thenReturn(List.of(sampleProduct));
        List<ProductResponse> sampleResponses = productService.getAllProducts();

        assertNotNull(sampleResponses);
        assertEquals(1,sampleResponses.size());
        assertEquals(sampleProduct.getName(),sampleResponses.getFirst().getName());
    }

    @Test
    void getAllProductsBySellerTest(){
        Product product = new Product();
        product.setSeller("sampleSeller2");
        when(productRepo.findByActiveTrue()).thenReturn(List.of(sampleProduct, product));

        List<ProductResponse> productResponses = productService.getAllProductsBySeller("sampleSeller1");

        assertEquals(1,productResponses.size());
        assertEquals("sampleSeller1",productResponses.getFirst().getSeller());

    }

    @Test
    void createProductTest() {

        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("sampleProduct2");
        productRequest.setSeller("sampleSeller1");
        productRequest.setStockQuantity(5);
        productRequest.setPrice(new BigDecimal(10));

        Product savedProduct = new Product();
        savedProduct.setId(1);
        savedProduct.setName("sampleProduct2");
        savedProduct.setActive(true);

        when(productRepo.save(any(Product.class))).thenReturn(savedProduct);
        when(streamBridge.send(anyString(),any())).thenReturn(true);

        ProductResponse productResponse = productService.createProduct(productRequest,"sampleseller1@gmail.com");

        assertNotNull(productResponse);
        assertEquals(savedProduct.getId(),productResponse.getId());

        verify(productRepo, times(1)).save(any(Product.class));
        verify(streamBridge,times(1)).send(eq("createProduct-out-0"), any());

    }

    @Test
    public void getProductByIdTest_Found(){
        when(productRepo.findByIdAndActiveTrue(1)).thenReturn(Optional.of(sampleProduct));

        ProductResponse response = productService.getProductById(1);

        assertNotNull(response);
        assertEquals(sampleProduct.getName(), response.getName());
    }

    @Test
    public void getProductByIdTest_NotFound(){
        when(productRepo.findByIdAndActiveTrue(2)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(2));
        assertEquals("Product with ID 2 not found.", exception.getMessage());
    }

    @Test
    public void getProductByIdOfSellerTest_SellerName(){
        when(productRepo.findById(1)).thenReturn(Optional.of(sampleProduct));

        ProductResponse response = productService.getProductByIdOfSeller(1, "sampleSeller1");

        assertNotNull(response);
        assertEquals("sampleSeller1", response.getSeller());
    }




    @Test
    public void getProductByIdOfSellerTest_SellerNameMismatch(){
        when(productRepo.findById(1)).thenReturn(Optional.of(sampleProduct));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                productService.getProductByIdOfSeller(1, "misMatchSellerName"));


        assertEquals("Product not found or seller mismatch.", exception.getMessage());
    }


    @Test
    public void updateProductBySellerTest_WithAdminRole(){
        ProductRequest request = new ProductRequest();
        request.setName("Updated Product Name");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getAuthorities()).thenAnswer(invocation ->
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        when(productRepo.findById(1)).thenReturn(Optional.of(sampleProduct));
        when(productRepo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ProductResponse productResponse = productService.updateProductBySeller(authentication,1,"sampleSeller1",request);

        assertNotNull(productResponse);
        assertEquals("Updated Product Name",productResponse.getName());
    }

    @Test
    public void updateProductBySellerTest_WithUserRole(){
        ProductRequest request = new ProductRequest();
        request.setName("Updated Product Name");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getAuthorities()).thenAnswer(invocation ->
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        when(productRepo.findById(1)).thenReturn(Optional.of(sampleProduct));

        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () ->
                productService.updateProductBySeller(authentication,1,"sampleSeller1",request));


        assertEquals("You don't have permission to update this product.",exception.getMessage());
    }


    @Test
    void updateProductBySeller_withSellerRoleAndMismatch_throws() {
        ProductRequest request = new ProductRequest();
        request.setName("Updated Name");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getAuthorities()).thenAnswer(invocation ->
                List.of(new SimpleGrantedAuthority("ROLE_SELLER")));
        when(productRepo.findById(1)).thenReturn(Optional.of(sampleProduct));

        UnauthorizedException ex = assertThrows(UnauthorizedException.class, () ->
            productService.updateProductBySeller(authentication, 1, "wrongSeller", request));
        assertEquals("This is product is not created by You.", ex.getMessage());
    }

    @Test
    void deleteProductTest(){
        when(productRepo.findById(1)).thenReturn(Optional.of(sampleProduct));
        when(productRepo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        boolean result = productService.deleteProduct(1);

        assertTrue(result);
        assertFalse(sampleProduct.getActive());
        verify(productRepo).save(sampleProduct);
    }


    @Test
    void deleteProductBySellerTest_WithMatchingSellerName(){
        when(productRepo.findById(1)).thenReturn(Optional.of(sampleProduct));
        when(productRepo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        boolean result = productService.deleteProductBySeller(1, "sampleSeller1");

        assertTrue(result);
        assertFalse(sampleProduct.getActive());
    }


    @Test
    void deleteProductBySellerTest_WithoutMatchingSellerName(){
        when(productRepo.findById(1)).thenReturn(Optional.of(sampleProduct));

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
            productService.deleteProductBySeller(1, "wrongSeller"));

        assertEquals("Product not found or seller mismatch.", ex.getMessage());
    }


    @Test
    void searchProductTest() {
        when(productRepo.findByNameContainingOrDescriptionContaining("keyword", "keyword"))
                .thenReturn(List.of(sampleProduct));

        List<ProductResponse> results = productService.searchProduct("keyword");

        assertEquals(1, results.size());
        assertEquals(sampleProduct.getName(), results.getFirst().getName());
    }


    @Test
    void searchProductBySellerTest(){
        Product otherProduct = new Product();
        otherProduct.setSeller("sampleSeller2");

        when(productRepo.findByNameContainingOrDescriptionContaining("keyword", "keyword"))
                .thenReturn(List.of(sampleProduct, otherProduct));

        List<ProductResponse> results = productService.searchProductBySeller("keyword", "sampleSeller1");

        assertEquals(1, results.size());
        assertEquals("sampleSeller1", results.getFirst().getSeller());
    }

    @Test
    void updateProductQuantityBySellerTest_Success(){
        ProductQuantityRequest pqr = new ProductQuantityRequest();
        pqr.setProductId(1);
        pqr.setStockQuantity(5);

        when(productRepo.findById(1)).thenReturn(Optional.of(sampleProduct));
        when(productRepo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        String result = productService.updateProductQuantityBySeller("sampleSeller1", List.of(pqr));

        assertEquals("Updated", result);
        assertEquals(5, sampleProduct.getStockQuantity());
        assertTrue(sampleProduct.getActive());
    }


    @Test
    void updateProductQuantityBySellerTest_Failed(){
        ProductQuantityRequest productQuantityRequest = new ProductQuantityRequest();
        productQuantityRequest.setProductId(1);
        productQuantityRequest.setStockQuantity(5);

        when(productRepo.findById(1)).thenReturn(Optional.of(sampleProduct));

        UnauthorizedException ex = assertThrows(UnauthorizedException.class, () ->
            productService.updateProductQuantityBySeller("otherSeller", List.of(productQuantityRequest)));

        assertTrue(ex.getMessage().contains("You can't update quantity"));

    }

    @Test
    void getProductByIdSellerTest(){
        when(productRepo.findById(1)).thenReturn(Optional.of(sampleProduct));

        Product result = productService.getProductByIdSeller(1);

        assertEquals(sampleProduct,result);
    }


}