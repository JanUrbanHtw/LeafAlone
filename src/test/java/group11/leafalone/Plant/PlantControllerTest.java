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

import java.util.*;

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
    @WithMockUser("name")
    void AddPlant_POST_correctShouldSaveToDB() throws Exception {
        LeafAloneUser user = new LeafAloneUser("name", "password", "ROLE_USER", "leafalone@mail.de", "password");
        when(userRepository.findByUsername("name")).thenReturn(user);

        mockMVC.perform(post("/plants/add")
                .param("name", "plantName"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/plants/list"));
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
        mockMVC.perform(get("/plant_types/add"))
                .andExpect(view().name("plant_types/add"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("sunSituations", SunSituation.values()));
    }

    @Test
    @WithMockUser
    void ContributePlant_POST_correctShouldForwardToConfirm() throws Exception {
        mockMVC.perform(post("/plant_types/add")
                .param("colloquial", "colloquial")
                .param("scientific", "scientific")
                .param("waterCycle", "1")
                .param("waterAmount", "1")
                .param("soilAdvice", "soilAdvice")
                .param("description", "description"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("forward:/plant_types/confirm"))
                .andExpect(model().attributeExists("plantCare"));
    }

    @Test
    void ContributePlant_POST_noColloquial() throws Exception {
        mockMVC.perform(post("/plant_types/add")
                .param("scientific", "scientific")
                .param("waterCycle", "1")
                .param("waterAmount", "1")
                .param("soilAdvice", "soilAdvice")
                .param("description", "description"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("plant_types/add"))
                .andExpect(model().attributeHasFieldErrors("plantCare", "colloquial"));
        verify(plantCareRepository, times(0)).save(any(PlantCare.class));
    }

    @Test
    void ContributePlant_POST_noScientific() throws Exception {
        mockMVC.perform(post("/plant_types/add")
                .param("colloquial", "colloquial")
                .param("waterCycle", "1")
                .param("waterAmount", "1")
                .param("soilAdvice", "soilAdvice")
                .param("description", "description"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("plant_types/add"))
                .andExpect(model().attributeHasFieldErrors("plantCare", "scientific"));
        verify(plantCareRepository, times(0)).save(any(PlantCare.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "0"})
    void ContributePlant_POST_invalidWaterCycle(String cycle) throws Exception {
        mockMVC.perform(post("/plant_types/add")
                .param("colloquial", "colloquial")
                .param("scientific", "scientific")
                .param("waterCycle", cycle)
                .param("waterAmount", "1")
                .param("soilAdvice", "soilAdvice")
                .param("description", "description"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("plant_types/add"))
                .andExpect(model().attributeHasFieldErrors("plantCare", "waterCycle"));
        verify(plantCareRepository, times(0)).save(any(PlantCare.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "0"})
    void ContributePlant_POST_invalidWaterAmount(String amount) throws Exception {
        mockMVC.perform(post("/plant_types/add")
                .param("colloquial", "colloquial")
                .param("scientific", "scientific")
                .param("waterCycle", "1")
                .param("waterAmount", amount)
                .param("soilAdvice", "soilAdvice")
                .param("description", "description"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("plant_types/add"))
                .andExpect(model().attributeHasFieldErrors("plantCare", "waterAmount"));
        verify(plantCareRepository, times(0)).save(any(PlantCare.class));
    }

    @Test
    void ContributePlant_POST_noSoilAdvice() throws Exception {
        mockMVC.perform(post("/plant_types/add")
                .param("colloquial", "colloquial")
                .param("scientific", "scientific")
                .param("waterCycle", "1")
                .param("waterAmount", "1")
                .param("description", "description"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("plant_types/add"))
                .andExpect(model().attributeHasFieldErrors("plantCare", "soilAdvice"));
        verify(plantCareRepository, times(0)).save(any(PlantCare.class));
    }

    @Test
    void ContributePlant_POST_noDescription() throws Exception {
        mockMVC.perform(post("/plant_types/add")
                .param("colloquial", "colloquial")
                .param("scientific", "scientific")
                .param("waterCycle", "1")
                .param("waterAmount", "1")
                .param("soilAdvice", "soilAdvice"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("plant_types/add"))
                .andExpect(model().attributeHasFieldErrors("plantCare", "description"));
        verify(plantCareRepository, times(0)).save(any(PlantCare.class));
    }

    @Test
    @WithMockUser()
    void ContributePlant_POST_PlantCareNameAlreadyInUse() throws Exception {
        when(plantCareRepository.findByScientific("scientific")).thenReturn(new PlantCare());
        mockMVC.perform(post("/plant_types/add")
                .param("colloquial", "colloquial")
                .param("scientific", "scientific")
                .param("waterCycle", "1")
                .param("waterAmount", "1")
                .param("soilAdvice", "soilAdvice")
                .param("description", "description"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("plant_types/add"))
                .andExpect(model().attributeHasFieldErrors("plantCare", "scientific"));
        verify(plantRepository, times(0)).save(any(Plant.class));
    }

    //list

    @Test
    @WithMockUser(username = "name")
    void ListPlant_GET_ShouldRenderList() throws Exception {
        LeafAloneUser user = new LeafAloneUser.Builder().build();
        when(userService.findByUsername("name")).thenReturn(user);

        ArrayList<Plant> list = new ArrayList<>();
        PlantCare care = new PlantCare.Builder().withColloquial("Dummy").build();
        list.add(new Plant.Builder().withName("Plant1").withPlantCare(care).withWatered(new Date()).withNextWatering(new Date()).build());
        list.add(new Plant.Builder().withName("Plant2").withPlantCare(care).withWatered(new Date()).withNextWatering(new Date()).build());
        when(plantService.findByLeafAloneUserOrdered(user))
                .thenReturn(list);

        mockMVC.perform(get("/plants/list"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("plants/list"))
                .andExpect(model().attributeExists("plants"));
        verify(plantRepository).findByLeafAloneUserOrdered(user.getId());
    }

    @Test
    @WithMockUser(username = "name")
    void WateredPlant_GET_ShouldRenderListAndUpdatePlant() throws Exception {
        PlantCare plantCare = new PlantCare.Builder().withColloquial("TestDummy").build();
        Plant plant = new Plant.Builder().withName("Dummy").withPlantCare(plantCare).build();
        Optional<Plant> optionalPlant = Optional.of(plant);

        ArrayList<Plant> list = new ArrayList<>();
        list.add(plant);

        LeafAloneUser user = new LeafAloneUser("name", "password", "ROLE_USER", "leafalone@mail.de", "password");

        when(userRepository.findByUsername("name")).thenReturn(user);
        when(plantRepository.findByName(userService.getCurrentUser().getId(), "Dummy")).thenReturn(optionalPlant);
        when(plantRepository.findByLeafAloneUserOrdered(userService.getCurrentUser().getId())).thenReturn(list);

        mockMVC.perform(get("/plants/watered/Dummy"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/plants/list?message=Dummy got watered"))
                .andExpect(model().attributeExists("plants"));
        verify(plantRepository, times(1)).save(plant);
        verify(plantRepository).findByLeafAloneUserOrdered(userService.getCurrentUser().getId());
    }

    //confirm

    @Test
    void confirmPostShouldRenderConfirmPage() throws Exception {
        mockMVC.perform(post("/plant_types/confirm")
                .param("colloquial", "colloquial")
                .param("scientific", "scientific")
                .param("waterCycle", "1")
                .param("waterAmount", "1")
                .param("soilAdvice", "soilAdvice")
                .param("description", "description"))
                .andExpect(status().isOk())
                .andExpect(view().name("plant_types/confirm"))
                .andExpect(model().attributeExists("plantCare"));
    }

    @Test
    @WithMockUser(username = "user")
    void confirmOkGetShouldRenderIndexPage() throws Exception {
        when(userService.findByUsername("user")).thenReturn(new LeafAloneUser("user", "password", "ROLE_CONTRIBUTOR", "leafalone@mail.de", "password"));

        mockMVC.perform(post("/plant_types/confirm")
                .param("colloquial", "colloquial")
                .param("scientific", "scientific")
                .param("waterCycle", "1")
                .param("waterAmount", "1")
                .param("soilAdvice", "soilAdvice")
                .param("description", "description"));

        mockMVC.perform(get("/plant_types/confirm/ok"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/?message=Thank you for your contribution."));

        ArgumentCaptor<PlantCare> savedPlantCare = ArgumentCaptor.forClass(PlantCare.class);
        Mockito.verify(plantCareRepository).save(savedPlantCare.capture());
        assertThat(savedPlantCare.getValue().getScientific()).isEqualTo("scientific");
        assertThat(savedPlantCare.getValue().getColloquial()).isEqualTo("colloquial");
    }

    @Test
    @WithMockUser(username = "name")
    void DeletePlant_GET_ShouldRenderListAndDeletePlant() throws Exception {
        PlantCare plantCare = new PlantCare.Builder().withColloquial("TestDummy").withWaterCycle(2).build();
        Plant plant =
                new Plant.Builder().withName("Dummy").withPlantCare(plantCare).withId(123456789).withWatered(new Date()).withNextWatering(new Date()).build();
        Optional<Plant> optionalPlant = Optional.of(plant);

        ArrayList<Plant> list = new ArrayList<>();
        list.add(plant);

        LeafAloneUser user = new LeafAloneUser("name", "password", "ROLE_USER", "leafalone@mail.de", "password");

        when(userRepository.findByUsername("name")).thenReturn(user);
        when(plantRepository.findByName(userService.getCurrentUser().getId(), "Dummy")).thenReturn(optionalPlant);
        when(plantRepository.findByLeafAloneUserOrdered(userService.getCurrentUser().getId())).thenReturn(list);

        mockMVC.perform(get("/plants/delete/Dummy"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/plants/list?message=Dummy got deleted"))
                .andExpect(model().attributeExists("plants"));
        verify(plantRepository, times(1)).deleteById(plant.getId());
        verify(plantRepository).findByLeafAloneUserOrdered(userService.getCurrentUser().getId());
    }

    //edit

    @Test
    @WithMockUser("name")
    void EditPlant_GET_shouldRenderEdit() throws Exception {
        PlantCare[] plantCares = {new PlantCare(), new PlantCare()};
        ArrayList<PlantCare> plantCareList = new ArrayList<>(Arrays.asList(plantCares));
        Plant plant = new Plant.Builder().withName("dummy").build();
        Optional<Plant> optional = Optional.of(plant);
        LeafAloneUser user = new LeafAloneUser("name", "password", "ROLE_USER", "leafalone@mail.de", "password");

        when(userRepository.findByUsername("name")).thenReturn(user);
        when(plantRepository.findByName(userService.getCurrentUser().getId(), "dummy")).thenReturn(optional);
        when(plantCareService.findAll()).thenReturn(plantCareList);

        mockMVC.perform(get("/plants/edit/dummy"))
                .andExpect(view().name("plants/edit"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("plant", plant))
                .andExpect(model().attribute("sunSituations", SunSituation.values()))
                .andExpect(model().attribute("plantCares", plantCareList));
        verify(plantCareRepository).findAll();
        verify(plantRepository).findByName(userService.getCurrentUser().getId(), "dummy");
    }

    //TODO make it pass; see AddPlant_POST_correctShouldSaveToDB()
//    @Test
//    @WithMockUser("name")
//    void EditPlant_POST_correctShouldSaveToDB() throws Exception {
//        Plant plant = new Plant.Builder().withName("dummy").build();
//        Optional<Plant> optional = Optional.of(plant);
//
//        LeafAloneUser user = new LeafAloneUser("name", "password", "ROLE_USER", "leafalone@mail.de", "password");
//
//        when(userRepository.findByUsername("name")).thenReturn(user);
//        when(plantRepository.findByName(userService.getCurrentUser().getId(), "dummy")).thenReturn(optional);
//
//        mockMVC.perform(post("/plants/edit/dummy")
//                .param("name", "dummy"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/plants/list"));
//        verify(plantRepository, times(1)).save(any(Plant.class));
//    }

    @Test
    @WithMockUser
    void EditPlant_POST_acquisitionInTheFuture() throws Exception {
        String date = (Calendar.getInstance().get(Calendar.YEAR) + 1) + "-01-01T00:00:00.000Z";
        mockMVC.perform(post("/plants/edit/dummy")
                .param("name", "dummy")
                .param("acquisition", date))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("plants/edit"))
                .andExpect(model().attributeHasFieldErrors("plant", "acquisition"));
        verify(plantRepository, times(0)).save(any(Plant.class));
    }

    @Test
    @WithMockUser("name")
    void EditPlant_POST_wateredInTheFuture() throws Exception {
        String date = (Calendar.getInstance().get(Calendar.YEAR) + 1) + "-01-01T00:00:00.000Z";
        Plant plant = new Plant.Builder().withName("dummy").build();
        Optional<Plant> optional = Optional.of(plant);
        LeafAloneUser user = new LeafAloneUser("name", "password", "ROLE_USER", "leafalone@mail.de", "password");

        when(userRepository.findByUsername("name")).thenReturn(user);
        when(plantRepository.findByName(userService.getCurrentUser().getId(), "dummy")).thenReturn(optional);

        mockMVC.perform(post("/plants/edit/dummy")
                .param("name", "dummy")
                .param("watered", date))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("plants/edit"))
                .andExpect(model().attributeHasFieldErrors("plant", "watered"));
        verify(plantRepository, times(0)).save(any(Plant.class));
    }

    // types
    @Test
    @WithMockUser(username = "name")
    void TypesPlantCare_GET_ShouldRenderTypes() throws Exception {
        LeafAloneUser user = new LeafAloneUser.Builder().build();
        when(userService.findByUsername("name")).thenReturn(user);

        ArrayList<PlantCare> list = new ArrayList<>();
        list.add(new PlantCare.Builder().withColloquial("colloquial1").withScientific("scientific1").build());
        list.add(new PlantCare.Builder().withColloquial("colloquial2").withScientific("scientific2").build());
        when(plantCareService.findByLeafAloneUserOrdered(user))
                .thenReturn(list);

        mockMVC.perform(get("/plant_types/list"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("plant_types/list"))
                .andExpect(model().attribute("plantCares", list));
        verify(plantCareRepository).findByLeafAloneUserOrdered(user.getId());
    }

    @Test
    @WithMockUser(username = "name")
    void DetailsPlantCare_GET_shouldRenderDetails() throws Exception {
        LeafAloneUser user = new LeafAloneUser.Builder().build();
        when(userService.findByUsername("name")).thenReturn(user);

        PlantCare plantCare = new PlantCare.Builder().withScientific("dummy").build();
        when(plantCareRepository.findByScientific("dummy")).thenReturn(plantCare);

        mockMVC.perform(get("/plant_types/details/dummy"))
                .andExpect(view().name("plant_types/details"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("plantCare", plantCare));
        verify(plantCareRepository).findByScientific("dummy");
    }
}

