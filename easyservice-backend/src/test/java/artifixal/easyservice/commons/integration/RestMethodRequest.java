package artifixal.easyservice.commons.integration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Builder style base class for testing REST requests.
 * 
 * @author ArtiFixal
 * @param <T> Request response type.
 */
public abstract class RestMethodRequest<T>{
    
    private boolean tested;
    
    private final String url;
    
    /**
     * Repository to use in tests.
     */
    private JpaRepository testRepo;
    
    private ResponseEntity<T> testResponse;
    
    private final HttpStatus expectedStatus;
    
    private Object request;
    
    /**
     * Tests to execute after request.
     */
    protected ArrayList<Consumer<ResponseEntity<T>>> tests;
    
    private Supplier<ResponseEntity<T>> testSupplier;
    
    private final Class<T> responseType;
    
    public RestMethodRequest(String url,HttpStatus expectedStatus,Class responseType){
        this.tested=false;
        this.url=url;
        this.expectedStatus=expectedStatus;
        this.tests=new ArrayList<>();
        this.testSupplier=defaultTest();
        this.responseType=responseType;
    }
    
    /**
     * Sends REST request.
     * 
     * @param url Where to send request.
     * @param request What to send.
     * @param responseType Type of response.
     * 
     * @return Received response.
     */
    protected abstract ResponseEntity<T> sendRequest(String url,Object request,Class<T> responseType);
    
    /**
     * Tests if row count changed in DB repository on the request.
     * 
     * @param testRepo Repository used to test change.
     * @param expectedChange Count of rows affected. Positive if rows were 
     *        added, negative if removed, 0 if request shouldn't affect row count.
     */
    public RestMethodRequest testRowChange(JpaRepository testRepo,int expectedChange){
        this.testRepo=testRepo;
        testSupplier=rowCountTest(expectedChange);
        return this;
    }
    
    public RestMethodRequest addTest(Consumer<ResponseEntity<T>>... additionalTests){
        tests.addAll(Arrays.asList(additionalTests));
        return this;
    }
    
    /**
     * Tests if response has body.
     */
    public RestMethodRequest responseBodyNotNull(){
        tests.add((response)->
                assertNotNull(response));
        return this;
    }
    
    /**
     * Tests if response body is a given instance.
     * 
     * @param instance Instance to test for.
     */
    public RestMethodRequest instanceOf(Class instance){
        tests.add((response)->
            assertTrue(response.getBody().getClass().isInstance(instance))
        );
        return this;
    }
    
    /**
     * Fires request without body.
     * 
     * @return Request response.
     */
    public ResponseEntity<T> test(){
        return test(null);
    }
    
    /**
     * Fires request with given body.
     * 
     * @param request Body to send.
     * 
     * @return Request response.
     */
    public ResponseEntity<T> test(Object request){
        if(tested)
            throw new IllegalStateException("Single request instance can be tested only once");
        tested=true;
        this.request=request;
        testResponse=testSupplier.get();
        for(Consumer c:tests)
            c.accept(testResponse);
        return testResponse;
    }
    
    private Supplier<ResponseEntity<T>> defaultTest(){
        return ()->{
            ResponseEntity response=sendRequest(url,request,responseType);
            assertEquals(expectedStatus,response.getStatusCode());
            return response;
        };
    }
    
    private Supplier<ResponseEntity<T>> rowCountTest(long exceptedChange){
        return ()->{
            long beforeRequest=testRepo.count();
            // Execute request
            ResponseEntity response=defaultTest().get();
            assertEquals(beforeRequest+exceptedChange,testRepo.count());
            return response;
        };
    }
}
