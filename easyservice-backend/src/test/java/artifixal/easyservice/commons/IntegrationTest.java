package artifixal.easyservice.commons;

import java.util.function.Consumer;
import java.util.function.Supplier;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Class containing methods to test Controllers.
 * 
 * @author ArtiFixal
 */
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {
    
    @Autowired
    protected TestRestTemplate restTemplate;
    
    @LocalServerPort
    protected int port;
    
    public String getURL(){
        return "http://localhost:"+port;
    }
    
    /**
     * GETs object from given path amd tests for {@link HttpStatus#OK}.
     * 
     * @param <T> Response type.
     * @param mappingPath Path at which anotated controller method is available.
     * @param responseType Response class.
     * @param expectedStatus Status to test for.
     * 
     * @return Response received from controller.
     */
    protected<T> ResponseEntity<T> getAndTest(String mappingPath,Class<T> responseType,HttpStatus expectedStatus){
        return sendRequestAndTest(mappingPath,HttpMethod.GET,null,responseType,expectedStatus);
    }
    
    /**
     * POSTs object as a request and tests for given {@code HttpStatus}.
     * 
     * @param mappingPath Path at which anotated controller method is available.
     * @param request Object to send.
     * @param expectedStatus Status to test for.
     * 
     * @return Response received from controller.
     */
    protected ResponseEntity postAndTest(String mappingPath,Object request,HttpStatus expectedStatus){
        // Object.class so we can test if body is returned on error.
        return postAndTest(mappingPath,request,Object.class,expectedStatus);
    }
    
    /**
     * POSTs object as a request and tests for {@link HttpStatus#OK}.
     * 
     * @param <T> Response type
     * @param mappingPath Path at which anotated controller method is available.
     * @param request Object to send.
     * @param responseType Response class.
     * @param expectedStatus Status to test for.
     * 
     * @return Response received from controller.
     */
    protected<T> ResponseEntity<T> postAndTest(String mappingPath,Object request,Class<T> responseType,HttpStatus expectedStatus){
        return sendRequestAndTest(mappingPath,HttpMethod.POST,request,responseType,expectedStatus);
    }
    
    /**
     * Sends PUT request and tests for {@link HttpStatus#OK}.
     * 
     * @param mappingPath Path at which anotated controller method is available.
     * @param request Object to send.
     * @param expectedStatus Status to test for.
     * 
     * @return Response received from controller.
     */
    protected ResponseEntity putAndTest(String mappingPath,Object request,HttpStatus expectedStatus){
        return sendRequestAndTest(mappingPath,HttpMethod.PUT,request,Object.class,expectedStatus);
    }

    /**
     * Sends request and tests if row count changed after it in the given 
     * repository.
     * 
     * @param <T> Response Type
     * @param requestTest Request to send
     * @param repo Repository used to test.
     * @param expectedChange Count of rows affected. Positive if rows were 
     *        added, negative if removed, 0 if shouldn't affect row count.
     * @param additionalTests
     * 
     * @return Response received from controller.
     */
    protected<T> ResponseEntity<T> testRowCountAfterRequest(Supplier<ResponseEntity<T>> requestTest,JpaRepository repo,int expectedChange,Consumer... additionalTests){
        long beforeRequest=repo.count();
        ResponseEntity response=requestTest.get();
        assertEquals(beforeRequest+expectedChange,repo.count());
        for(Consumer c:additionalTests)
            c.accept(response);
        return response;
    }
    
    /**
     * @return Consumer to test if response body is not null.
     */
    protected Consumer<ResponseEntity> testForNotNullResponseBody(){
        return (ResponseEntity response)->
                assertNotNull(response.getBody());
    }
    
    /**
     * Sends object request to the controller and tests for 
     * {@link HttpStatus#OK}.
     * 
     * @param <T> Response type.
     * @param mappingPath Path at which anotated controller method is available.
     * @param method Method used to send request.
     * @param request Object to send.
     * @param responseType Response class.
     * 
     * @return Response received from controller.
     */
    private<T> ResponseEntity<T> sendRequestAndTest(String mappingPath,HttpMethod method,Object request,Class<T> responseType,HttpStatus expectedStatus){
        ResponseEntity response=restTemplate.exchange(getURL()+mappingPath,
                method,new HttpEntity<>(request),responseType);
        assertEquals(expectedStatus,response.getStatusCode());
        return response;
    }
}
