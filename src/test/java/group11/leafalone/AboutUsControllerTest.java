package group11.leafalone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
class AboutUsControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(new AboutUsController()).build();
    }

    //index

    @Test
    void Index_shouldRenderIndexView() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(view().name("index.html"))
                .andExpect(status().is2xxSuccessful());
    }

    //about

    @Test
    void About_shouldRenderAboutView() throws Exception {
        mockMvc.perform(get("/about"))
                .andExpect(view().name("aboutUs.html"))
                .andExpect(status().is2xxSuccessful());
    }
}