package group11.leafalone.ControllerTests;

import group11.leafalone.Plant.PlantCare;
import group11.leafalone.Plant.PlantController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlantControllerTest {

    private static PlantController plantController;

    @BeforeAll
    static void init() {
        plantController = new PlantController();
    }

    // addPlant

    @Test
    void AddPlant_GET_shouldRenderAdd() {
        ModelAndView view = plantController.addPlantForm();
        assertEquals("plants/add.html", view.getViewName());
    }

    //TODO should test that the submitted form is filled correctly
    // will update when this feature is present
    //TODO should test that object is saved to the database
    // will update when this feature is present
    @Test
    void AddPlant_POST_RedirectToAbout() {
        String redirect = plantController.addPlantSubmit();
        assertEquals("redirect:../about", redirect);
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
        PlantCare plantCare = Mockito.mock(PlantCare.class);
        String redirect = plantController.contributePlantSubmit(plantCare);
        assertEquals("redirect:../about", redirect);
    }

    //listPlants

    @Test
    void ListPlant_Get_shouldRenderList() {
        ModelAndView view = plantController.getPlantList();
        assertEquals("plants/list.html", view.getViewName());
    }
}
