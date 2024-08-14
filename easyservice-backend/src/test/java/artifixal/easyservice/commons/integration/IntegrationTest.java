package artifixal.easyservice.commons.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

/**
 * Class containing methods to test Controllers.
 * 
 * @author ArtiFixal
 */
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {
    
    protected TestRestTemplate restTemplate;
    
    @LocalServerPort
    protected int port;

    @Autowired(required=true)
    public IntegrationTest(){
        restTemplate=new TestRestTemplate();
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }
    
    public String getURL(){
        return "http://localhost:"+port;
    }
    
    /**
     * Creates GET request object with given path to request and status to test.
     * 
     * @param mappingPath Path at which anotated controller method is available.
     * @param expectedStatus Status to test for.
     * 
     * @return REST GET request.
     */
    protected RestMethodRequest get(String mappingPath,HttpStatus expectedStatus){
        return get(mappingPath,expectedStatus,Object.class);
    }
    
    /**
     * Creates GET request object with given path to request and status to test.
     * 
     * @param mappingPath Path at which anotated controller method is available.
     * @param expectedStatus Status to test for.
     * @param responseType Response body type.
     * 
     * @return REST GET request.
     */
    protected RestMethodRequest get(String mappingPath,HttpStatus expectedStatus,Class responseType){
        return new RestMethodRequest(mappingPath,expectedStatus,responseType){
            @Override
            protected ResponseEntity sendRequest(String mappingPath,Object request,Class responseType){
                return restTemplate.exchange(getURL()+mappingPath,
                        HttpMethod.GET,new HttpEntity<>(request),responseType);
            }
        };
    }
    
    /**
     * Creates POST request object with given path to request and status to test.
     * 
     * @param mappingPath Path at which anotated controller method is available.
     * @param expectedStatus Status to test for.
     * 
     * @return REST POST request.
     */
    protected RestMethodRequest post(String mappingPath,HttpStatus expectedStatus){
        return post(mappingPath,expectedStatus,Object.class);
    }
    
    /**
     * Creates POST request object with given path to request and status to test.
     * 
     * @param mappingPath Path at which anotated controller method is available.
     * @param expectedStatus Status to test for.
     * @param responseType Response body type.
     * 
     * @return REST POST request.
     */
    protected RestMethodRequest post(String mappingPath,HttpStatus expectedStatus,Class responseType){
        return new RestMethodRequest(mappingPath,expectedStatus,responseType){
            @Override
            protected ResponseEntity sendRequest(String url,Object request,Class responseType){
                return restTemplate.exchange(getURL()+mappingPath,
                        HttpMethod.POST,new HttpEntity<>(request),responseType);
            }
        };
    }
    
    /**
     * Creates PUT request object with given path to request and status to test.
     * 
     * @param mappingPath Path at which anotated controller method is available.
     * @param expectedStatus Status to test for.
     * 
     * @return REST PUT request.
     */
    protected RestMethodRequest put(String mappingPath,HttpStatus expectedStatus){
        return put(mappingPath,expectedStatus,Object.class);
    }
    
    /**
     * Creates PUT request object with given path to request and status to test.
     * 
     * @param mappingPath Path at which anotated controller method is available.
     * @param expectedStatus Status to test for.
     * @param responseType Response body type.
     * 
     * @return REST PUT request.
     */
    protected RestMethodRequest put(String mappingPath,HttpStatus expectedStatus,Class responseType){
        return new RestMethodRequest(mappingPath,expectedStatus,responseType){
            @Override
            protected ResponseEntity sendRequest(String mappingPath,Object request,Class responseType){
                return restTemplate.exchange(getURL()+mappingPath,
                        HttpMethod.PUT,new HttpEntity<>(request),responseType);
            }
        };
    }
    
    /**
     * Creates PATCH request object with given path to request and status to test.
     * 
     * @param mappingPath Path at which anotated controller method is available.
     * @param expectedStatus Status to test for.
     * 
     * @return REST PATCH request.
     */
    protected RestMethodRequest patch(String mappingPath,HttpStatus expectedStatus){
        return patch(mappingPath,expectedStatus,Object.class);
    }
    
    /**
     * Creates PATCH request object with given path to request and status to test.
     * 
     * @param mappingPath Path at which anotated controller method is available.
     * @param expectedStatus Status to test for.
     * @param responseType Response body type.
     * 
     * @return REST PATCH request.
     */
    protected RestMethodRequest patch(String mappingPath,HttpStatus expectedStatus,Class responseType){
        return new RestMethodRequest(mappingPath,expectedStatus,responseType){
            @Override
            protected ResponseEntity sendRequest(String url,Object request,Class responseType){
                HttpHeaders headers=new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return restTemplate.exchange(getURL()+mappingPath,HttpMethod.PATCH,
                        new HttpEntity<>(request,headers),responseType);
                
            }
        };
    }
}
