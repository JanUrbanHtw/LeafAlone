package group11.leafalone.Plant;

import group11.leafalone.Auth.LeafAloneUser;
import group11.leafalone.Auth.LeafAloneUserDetailsService;
import group11.leafalone.Auth.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class PlantControllerTest {
    private MockMvc mockMVC;

    private PlantRepository plantRepository;
    private PlantCareRepository plantCareRepository;
    private UserRepository userRepository;

    private PlantService plantService;
    private PlantCareService plantCareService;
    private LeafAloneUserDetailsService userService;

    @BeforeEach
    void init() {
        plantRepository = Mockito.mock(PlantRepository.class);
        plantCareRepository = Mockito.mock(PlantCareRepository.class);
        userRepository = Mockito.mock(UserRepository.class);

        userService = new LeafAloneUserDetailsService(userRepository, new BCryptPasswordEncoder());
        plantService = new PlantService(plantRepository, userService);
        plantCareService = new PlantCareService(plantCareRepository, userService);

        PlantController plantController = new PlantController(plantService, plantCareService, userService);
        mockMVC = MockMvcBuilders.standaloneSetup(plantController).build();
    }

    // addPlant

    @Test
    void AddPlant_GET_shouldRenderAdd() throws Exception {
        PlantCare[] plantCares = {new PlantCare(), new PlantCare()};
        ArrayList<PlantCare> plantCareList = new ArrayList<>(Arrays.asList(plantCares));

        when(plantCareService.findAll()).thenReturn(plantCareList);
        mockMVC.perform(get("/plants/add"))
                .andExpect(view().name("plants/add"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("sunSituations", SunSituation.values()))
                .andExpect(model().attribute("plantCares", plantCareList));
        verify(plantCareRepository).findAll();
    }

    @Test
    @WithMockUser
    void AddPlant_POST_correctShouldSaveToDB() throws Exception {
        mockMVC.perform(post("/plants/add")
                .param("name", "plantName"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:list"));
        verify(plantRepository, times(1)).save(any(Plant.class));
    }

    @Test
    @WithMockUser
    void AddPlant_POST_missingUserName() throws Exception {
        mockMVC.perform(post("/plants/add")
                .param("name", ""))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrors("plant", "name"))
                .andExpect(view().name("plants/add"));
        verify(plantRepository, times(0)).save(any(Plant.class));
    }

    @Test
    @WithMockUser
    void AddPlant_POST_acquisitionInTheFuture() throws Exception {
        String date = (Calendar.getInstance().get(Calendar.YEAR) + 1) + "-01-01T00:00:00.000Z";
        System.out.println(date);
        mockMVC.perform(post("/plants/add")
                .param("name", "plantName")
                .param("acquisition", date))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("plants/add"))
                .andExpect(model().attributeHasFieldErrors("plant", "acquisition"));
        verify(plantRepository, times(0)).save(any(Plant.class));
    }

    @Test
    @WithMockUser
    void AddPlant_POST_wateredInTheFuture() throws Exception {
        String date = (Calendar.getInstance().get(Calendar.YEAR) + 1) + "-01-01T00:00:00.000Z";
        System.out.println(date);
        mockMVC.perform(post("/plants/add")
                .param("name", "plantName")
                .param("watered", date))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("plants/add"))
                .andExpect(model().attributeHasFieldErrors("plant", "watered"));
        verify(plantRepository, times(0)).save(any(Plant.class));
    }

    @Test
    @WithMockUser()
    void AddPlant_POST_PlantNameAlreadyInUse() throws Exception {
        List<String> names = new ArrayList<>();
        names.add("plantName");
        when(plantRepository.findNamesByLeafAloneUser(userService.getCurrentUser())).thenReturn(names);
        mockMVC.perform(post("/plants/add")
                .param("name", "plantName"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("plants/add"))
                .andExpect(model().attributeHasFieldErrors("plant", "name"));
        verify(plantRepository, times(0)).save(any(Plant.class));
    }

    //contributePlant

    @Test
    void ContributePlant_GET_shouldRenderContribute() throws Exception {
        mockMVC.perform(get("/plants/contribute"))
                .andExpect(view().name("plants/contribute"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("sunSituations", SunSituation.values()));
    }

    @Test
    @WithMockUser
    void ContributePlant_POST_correctShouldForwardToConfirm() throws Exception {
        mockMVC.perform(post("/plants/contribute")
                .param("colloquial", "colloquial")
                .param("scientific", "scientific")
                .param("waterCycle", "1")
                .param("waterAmount", "1")
                .param("soilAdvice", "soilAdvice")
                .param("description", "description"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("forward:/plants/confirm"))
                .andExpect(model().attributeExists("plantCare"));
    }

    @Test
    void ContributePlant_POST_noColloquial() throws Exception {
        mockMVC.perform(post("/plants/contribute")
                .param("scientific", "scientific")
                .param("waterCycle", "1")
                .param("waterAmount", "1")
                .param("soilAdvice", "soilAdvice")
                .param("description", "description"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("plants/contribute"))
                .andExpect(model().attributeHasFieldErrors("plantCare", "colloquial"));
        verify(plantCareRepository, times(0)).save(any(PlantCare.class));
    }

    @Test
    void ContributePlant_POST_noScientific() throws Exception {
        mockMVC.perform(post("/plants/contribute")
                .param("colloquial", "colloquial")
                .param("waterCycle", "1")
                .param("waterAmount", "1")
                .param("soilAdvice", "soilAdvice")
                .param("description", "description"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("plants/contribute"))
                .andExpect(model().attributeHasFieldErrors("plantCare", "scientific"));
        verify(plantCareRepository, times(0)).save(any(PlantCare.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "0"})
    void ContributePlant_POST_invalidWaterCycle(String cycle) throws Exception {
        mockMVC.perform(post("/plants/contribute")
                .param("colloquial", "colloquial")
                .param("scientific", "scientific")
                .param("waterCycle", cycle)
                .param("waterAmount", "1")
                .param("soilAdvice", "soilAdvice")
                .param("description", "description"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("plants/contribute"))
                .andExpect(model().attributeHasFieldErrors("plantCare", "waterCycle"));
        verify(plantCareRepository, times(0)).save(any(PlantCare.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "0"})
    void ContributePlant_POST_invalidWaterAmount(String amount) throws Exception {
        mockMVC.perform(post("/plants/contribute")
                .param("colloquial", "colloquial")
                .param("scientific", "scientific")
                .param("waterCycle", "1")
                .param("waterAmount", amount)
                .param("soilAdvice", "soilAdvice")
                .param("description", "description"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("plants/contribute"))
                .andExpect(model().attributeHasFieldErrors("plantCare", "waterAmount"));
        verify(plantCareRepository, times(0)).save(any(PlantCare.class));
    }

    @Test
    void ContributePlant_POST_noSoilAdvice() throws Exception {
        mockMVC.perform(post("/plants/contribute")
                .param("colloquial", "colloquial")
                .param("scientific", "scientific")
                .param("waterCycle", "1")
                .param("waterAmount", "1")
                .param("description", "description"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("plants/contribute"))
                .andExpect(model().attributeHasFieldErrors("plantCare", "soilAdvice"));
        verify(plantCareRepository, times(0)).save(any(PlantCare.class));
    }

    @Test
    void ContributePlant_POST_noDescription() throws Exception {
        mockMVC.perform(post("/plants/contribute")
                .param("colloquial", "colloquial")
                .param("scientific", "scientific")
                .param("waterCycle", "1")
                .param("waterAmount", "1")
                .param("soilAdvice", "soilAdvice"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("plants/contribute"))
                .andExpect(model().attributeHasFieldErrors("plantCare", "description"));
        verify(plantCareRepository, times(0)).save(any(PlantCare.class));
    }

    //list

    @Test
    @WithMockUser(username = "name")
    void ListPlant_GET_ShouldRenderList() throws Exception {
        LeafAloneUser user = new LeafAloneUser.Builder().build();
        when(userService.findByUsername("name")).thenReturn(user);

        ArrayList<Plant> list = new ArrayList<>();
        list.add(new Plant.Builder().withName("Plant1").build());
        list.add(new Plant.Builder().withName("Plant2").build());
        when(plantService.findByLeafAloneUser(user))
                .thenReturn(list);

        mockMVC.perform(get("/plants/list"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("plants/list"))
                .andExpect(model().attribute("plants", list));
        verify(plantRepository).findByLeafAloneUser(user);
    }

    @Test
    void confirmPostShouldRenderConfirmPage() throws Exception {
        mockMVC.perform(post("/plants/confirm")
                .param("colloquial", "colloquial")
                .param("scientific", "scientific")
                .param("waterCycle", "1")
                .param("waterAmount", "1")
                .param("soilAdvice", "soilAdvice")
                .param("description", "description"))
                .andExpect(status().isOk())
                .andExpect(view().name("plants/confirm"))
                .andExpect(model().attributeExists("plantCare"));
    }

    @Test
    @WithMockUser(username = "user")
    void confirmOkGetShouldRenderIndexPage() throws Exception {
        when(userService.findByUsername("user")).thenReturn(new LeafAloneUser("user", "password", "ROLE_CONTRIBUTOR", "password"));

        mockMVC.perform(post("/plants/confirm")
                .param("colloquial", "colloquial")
                .param("scientific", "scientific")
                .param("waterCycle", "1")
                .param("waterAmount", "1")
                .param("soilAdvice", "soilAdvice")
                .param("description", "description"));

        mockMVC.perform(get("/plants/confirm/ok"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/?message=Thank you for your contribution."));

        ArgumentCaptor<PlantCare> savedPlantCare= ArgumentCaptor.forClass(PlantCare.class);
        Mockito.verify(plantCareRepository).save(savedPlantCare.capture());
        assertThat(savedPlantCare.getValue().getScientific()).isEqualTo("scientific");
        assertThat(savedPlantCare.getValue().getColloquial()).isEqualTo("colloquial");
    }
}

