import com.example.stationski.entities.Moniteur;
import com.example.stationski.entities.MoniteurDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@Slf4j
@ExtendWith(MockitoExtension.class)
@WebMvcTest(Moniteur.class)
public class MoniteurEntityTest {

    private MockMvc mockMvc;

    @Mock
    private Moniteur moniteur;

    @Mock
    private MoniteurDTO moniteurDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(moniteur).build();

    }

    @Test
    public void moniteurSetterTest() {
        Moniteur moniteur = new Moniteur();
        moniteur.setNomM("yassine");
        Assertions.assertEquals("yassine" , moniteur.getNomM());
    }

}