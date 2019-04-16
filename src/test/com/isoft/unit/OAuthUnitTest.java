package test.com.isoft.unit;

import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;

import main.com.isoft.Main;
import main.com.isoft.rest.controller.ConfigController;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes=Main.class)
public class OAuthUnitTest {
    
    @Autowired
    private WebApplicationContext wac;
 
    @Autowired
    private FilterChainProxy springSecurityFilterChain;
    
    private static final String CLIENT_ID = "dragonflareful";
    private static final String CLIENT_SECRET = "secret";

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    private static final int TEMP = 0;
    private static final int FEEL_TEMP = 0;
    private static final String STATUS = "default";
    private static final int WIND_SPD = 2;
    private static final String WIND_STATUS = "default";
    private static final String WIND_DIRECTION = "default";
    private static final int HUMIDITY = 0;
    private static final double VISIBILITY = 0;
    private static final int PRESSURE = 0;
    private static final String CITY = "default";
    private static final String COUNTRY = "default";
    private static final String SITE = "default";
    private static final String QUERY_DATE = "2018-04-01";
 
    private MockMvc mockMvc;
 
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
          .addFilter(springSecurityFilterChain).build();
    }
    
    private String obtainAccessToken(String username, String password) throws Exception {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", CLIENT_ID);
        params.add("username", username);
        params.add("password", password);

        // @formatter:off

        ResultActions result = mockMvc.perform(post("/oauth/token")
                               .params(params)
                               .with(httpBasic(CLIENT_ID, CLIENT_SECRET))
                               .accept(CONTENT_TYPE))
                               .andExpect(status().isOk())
                               .andExpect(content().contentType(CONTENT_TYPE));
        
        // @formatter:on

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }
    
    @Test
    public void givenNoToken_whenGetSecureRequest_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/weather").param("status", STATUS)).andExpect(status().isUnauthorized());
    }
    
    @Test
    public void givenInvalidRole_whenGetSecureRequest_thenForbidden() throws Exception {
        final String accessToken = obtainAccessToken("user1", "pass");
        System.out.println("token:" + accessToken);
        mockMvc.perform(get("/weather").header("Authorization", "Bearer " + accessToken).param("status", STATUS)).andExpect(status().isForbidden());
    }
    
    @Test
    public void givenToken_whenPostGetSecureRequest_thenOk() throws Exception {
        final String accessToken = obtainAccessToken("dragonflareful@gmail.com", "Juventus_96");

        String weatherString = "{\"temp\":\"" + TEMP + "\",\"feel_temp\":\"" + FEEL_TEMP + "\",\"status\":\"" + STATUS + "}";

        // @formatter:off
        
        mockMvc.perform(post("/weather")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(CONTENT_TYPE)
                .content(weatherString)
                .accept(CONTENT_TYPE))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/weather_list")
                .param("status", STATUS)
                .header("Authorization", "Bearer " + accessToken)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$.status", is(STATUS)));
        
        // @formatter:on
    }
    
}
