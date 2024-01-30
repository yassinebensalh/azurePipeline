import com.example.stationski.controllers.MoniteurRestController;
import com.example.stationski.entities.Moniteur;
import com.example.stationski.entities.MoniteurDTO;
import com.example.stationski.repositories.MoniteurRepository;
import com.example.stationski.services.IMoniteurService;
import com.example.stationski.services.MoniteurServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.datatype.jsr310.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
@WebMvcTest(MoniteurRestController.class)
public class MoniteurControllerTest {



    private MockMvc mockMvc;

    static ObjectMapper objectMapper = new ObjectMapper();
    static ObjectWriter objectWriter = objectMapper.writer();
    public static String asJsonString(final Object obj) {
        try {
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Mock
    private IMoniteurService moniteurService;

    @Mock
    private MoniteurRepository moniteurRepository;

    @InjectMocks
    private MoniteurServiceImpl moniteurServiceImpl;

    @InjectMocks
    private MoniteurRestController moniteurRestController;

    Moniteur m = Moniteur.builder().idMoniteur(1).numMoniteur(1L).nomM("yassine").prenomM("ben Salha").dateRecru(LocalDate.of(2023,12,12)).prime(15f).build();

    MoniteurDTO moniteurDTO = MoniteurDTO.builder().numMoniteur(5L).nomM("yassine").prenomM("ben Salha").dateRecru(LocalDate.of(2023,12,12)).prime(15f).build();

    List<Moniteur> records = new ArrayList<Moniteur>() {
        {
            add(Moniteur.builder().idMoniteur(1).numMoniteur(1L).nomM("yassine").prenomM("ben Salha").dateRecru(LocalDate.of(2023,12,12)).prime(15f).build());
            add(Moniteur.builder().idMoniteur(2).numMoniteur(2L).nomM("aziz").prenomM("nahdi").dateRecru(LocalDate.of(2022,12,12)).prime(10f).build());
            add(Moniteur.builder().idMoniteur(3).numMoniteur(3L).nomM("amine").prenomM("thabet").dateRecru(LocalDate.of(2021,12,12)).prime(5f).build());
        }
    };
    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(moniteurRestController).build();

    }

    @Test
    public void retrieveMonitorsTest() throws Exception {

        Mockito.when(moniteurService.retrieveAllMoniteurs()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/moniteur/retrieve-all-moniteurs")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$" , hasSize(3)))
                .andExpect(jsonPath("$[2].nomM" , is("amine")));
    }

    @Test
    public void createMonitorTest() throws Exception
    {

        Mockito.when(moniteurServiceImpl.addMoniteur(moniteurDTO)).thenReturn(m);
        mockMvc.perform(MockMvcRequestBuilders.post("/moniteur/add-moniteur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(asJsonString(moniteurDTO)))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test

    public void updateMonitorTest() throws Exception
    {
        Moniteur updatedMonitor = Moniteur.builder().idMoniteur(1).numMoniteur(1L).nomM("mohamed yassine").prenomM("ben Salha").dateRecru(LocalDate.of(2023,12,12)).prime(15f).build();
        Mockito.when(moniteurService.updateMoniteur(updatedMonitor.getIdMoniteur(),moniteurDTO)).thenReturn(updatedMonitor);
        mockMvc.perform(MockMvcRequestBuilders.put("/moniteur/update-moniteur/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(asJsonString(moniteurDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.nomM" , is("mohamed yassine")))
                .andReturn();
        Mockito.verify(moniteurService).updateMoniteur(updatedMonitor.getIdMoniteur(),moniteurDTO);
    }

    @Test
    public void deleteMonitorTest() throws Exception
    {
        Mockito.when(moniteurRepository.findById(m.getIdMoniteur())).thenReturn(Optional.ofNullable(m));

        mockMvc.perform(MockMvcRequestBuilders.delete("/moniteur/remove-moniteur/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void JustTest() {
        Mockito.when(moniteurRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(m));
        Moniteur moniteur = moniteurServiceImpl.retrieveMoniteur(1);
        Assert.assertNotNull(moniteur);
        log.info("get ===> " + moniteur.toString());
    }

    @Test
    public void retrieveMonitorTest() throws Exception
    {
        Mockito.when(moniteurService.retrieveMoniteur(Mockito.anyInt())).thenReturn(m);
        mockMvc.perform(MockMvcRequestBuilders.get("/moniteur/retrieve-moniteur/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.nomM" , is("yassine")))
                .andReturn();
    }


}
