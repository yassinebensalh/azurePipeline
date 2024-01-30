import com.example.stationski.entities.Moniteur;
import com.example.stationski.entities.MoniteurDTO;
import com.example.stationski.repositories.MoniteurRepository;
import com.example.stationski.services.MoniteurServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@ExtendWith(MockitoExtension.class)
@WebMvcTest(MoniteurServiceImpl.class)
public class MoniteurServiceTest {

    private MockMvc mockMvc;

    @InjectMocks
    private MoniteurServiceImpl moniteurServiceImpl;

    @Mock
    private MoniteurRepository moniteurRepository;

    Moniteur m = Moniteur.builder().idMoniteur(1).numMoniteur(1L).nomM("yassine").prenomM("ben Salha").dateRecru(LocalDate.of(2023,12,12)).prime(15f).build();
    MoniteurDTO moniteurDTO = MoniteurDTO.builder().numMoniteur(1L).nomM("yassine").prenomM("ben Salha").dateRecru(LocalDate.of(2023,12,12)).prime(15f).build();
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
        this.mockMvc = MockMvcBuilders.standaloneSetup(moniteurServiceImpl).build();

    }


    @Test
    public void GetAllMonitorsServiceTest() {
        Mockito.when(moniteurRepository.findAll()).thenReturn(records);
        List<Moniteur> moniteurList = moniteurServiceImpl.retrieveAllMoniteurs();
        Assertions.assertEquals("yassine", moniteurList.get(0).getNomM());
    }

    @Test
    public void GetOneMonitorServiceTest() {
        Mockito.when(moniteurRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(m));
        Moniteur moniteur = moniteurServiceImpl.retrieveMoniteur(1);
        Assert.assertNotNull(moniteur);
        log.info("get ===> " + moniteur.toString());
        Mockito.verify(moniteurRepository).findById(Mockito.anyInt());
    }

    @Test
    public void AddMonitorsServiceTest() {
        log.info("first : " + m.getNomM());
        Mockito.when(moniteurRepository.save(Mockito.any())).thenReturn(m);
        Moniteur ResM = moniteurServiceImpl.addMoniteur(moniteurDTO);
        log.info("second : " + ResM.toString());
        Assert.assertNotNull(ResM);
    }

    @Test
    public void updateMoniteurServiceTest() {
        Moniteur updatedMonitor = Moniteur.builder().idMoniteur(1).numMoniteur(1L).nomM("mohamed yassine").prenomM("ben Salha").dateRecru(LocalDate.of(2023,12,12)).prime(15f).build();
        log.info("first : " + moniteurDTO );
        Mockito.when(moniteurRepository.save(updatedMonitor)).thenReturn(updatedMonitor);
        Mockito.when(moniteurRepository.findMoniteurByIdMoniteur(Mockito.anyInt())).thenReturn(m);
        Mockito.when(moniteurServiceImpl.updateMoniteur(updatedMonitor.getIdMoniteur(),moniteurDTO)).thenReturn(updatedMonitor);
        Moniteur resultUpdatedMoniteur = moniteurServiceImpl.updateMoniteur(updatedMonitor.getIdMoniteur(),moniteurDTO);
        log.info("second : " + resultUpdatedMoniteur);
        Assertions.assertEquals("mohamed yassine" , resultUpdatedMoniteur.getNomM());
    }

    @Test
    public void deleteMoniteurServiceTest() {
        moniteurServiceImpl.deleteMoniteur(m.getIdMoniteur());
        Mockito.verify(moniteurRepository).deleteById(m.getIdMoniteur());
    }

}