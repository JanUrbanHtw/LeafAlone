package group11.leafalone.ControllerTests;

import group11.leafalone.LeafaloneApplication;
import group11.leafalone.Plant.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeafaloneApplication.class)
class PlantControllerTest {

    @Autowired
    private PlantController plantController;

    @Autowired
    private PlantCareRepository plantCareRepository;
    @Autowired
    private PlantRepository plantRepository;

    @AfterEach
    void removeAllEntries() {
        plantRepository.deleteAll();
        plantCareRepository.deleteAll();
    }
    // addPlant

    @Test
    void AddPlant_GET_shouldRenderAdd() {
        Model model = Mockito.mock(Model.class);
        String view = plantController.addPlantForm(model);
        assertEquals("plants/add", view);
    }

    //TODO should test that the submitted form is filled correctly
    // will update when this feature is present
    //TODO should test that object is saved to the database
    // will update when this feature is present
    @Test
    void AddPlant_POST_RedirectToAbout() {
//        BindingResult mockResult = Mockito.mock(BindingResult.class);
//        when(mockResult.hasErrors()).thenReturn(false);
//        Model mockmodel = Mockito.mock(Model.class);
//        String redirect = plantController.addPlantSubmit(new Plant("Dummy", new PlantCare("Dummy", "Duminitus Testitus",
//                SunSituation.SUNNY, 5, 5, "soil", "Test", "Paul"),
//                SunSituation.DARK, new Date(), new Date(), null, null), mockResult, mockmodel);
//        assertEquals("redirect:../about", redirect);
    }

    //contributePlant

    @Test
    void ContributePlant_GET_shouldRenderContribute() {
        Model model = Mockito.mock(Model.class);
        String view = plantController.contributePlantForm(model);
        assertEquals("plants/contribute", view);
    }


    //TODO should test that the submitted form is filled correctly
    // will update when this feature is present
    //TODO should test that object is saved to the database
    // will update when this feature is present
    @Test
    void ContributePlant_POST_shouldRedirectToAbout() {
//        BindingResult mockResult = Mockito.mock(BindingResult.class);
//        when(mockResult.hasErrors()).thenReturn(false);
//        Model mockModel = Mockito.mock(Model.class);
//        PlantCare plantCare = new PlantCare("Dummy", "Duminitus Testitus",
//                SunSituation.SUNNY, 5, 5, "soil", "Test", "Paul");
//        String redirect = plantController.contributePlantSubmit(plantCare, mockResult, mockModel);
//        assertEquals("redirect:../about", redirect);
    }
}
