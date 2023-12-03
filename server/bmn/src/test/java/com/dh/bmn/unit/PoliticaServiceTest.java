package com.dh.bmn.unit;
import com.dh.bmn.dtos.requests.PoliticaRequestDto;
import com.dh.bmn.dtos.responses.PoliticaResponseDto;
import com.dh.bmn.entity.Politica;
import com.dh.bmn.exceptions.ResourceAlreadyExistsException;
import com.dh.bmn.exceptions.ResourceNotFoundException;
import com.dh.bmn.repositories.IBicicletaRepository;
import com.dh.bmn.repositories.IPoliticaRepository;
import com.dh.bmn.services.impl.PoliticaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import java.net.MalformedURLException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = PoliticaServiceTest.class)
@ExtendWith(MockitoExtension.class)
public class PoliticaServiceTest {

    @Mock
    private IPoliticaRepository politicaRepository;

    @Mock
    private IBicicletaRepository bicicletaRepository;

    @InjectMocks
    private PoliticaService politicaService;

    @BeforeEach
    public void setup(){
        politicaRepository = mock(IPoliticaRepository.class);
        bicicletaRepository = mock (IBicicletaRepository.class);
        politicaService = new PoliticaService(politicaRepository, bicicletaRepository);
    }

    @Test
    public void crearPolitica() throws MalformedURLException{
        //Arrange
        PoliticaRequestDto politicaRequestDto =
                new PoliticaRequestDto(1L, "Terminos y usos","Terminos y usos del sitio web");

        //Act
        politicaService.crear(politicaRequestDto);

        // Asserts
        verify(politicaRepository, times(1)).save(any(Politica.class));
    }

    @Test
    public void crearPoliticaThrowsResourceAlreadyExistsException() throws MalformedURLException {
        //Arrange
        PoliticaRequestDto politicaRequestDto = new PoliticaRequestDto(1L, "Terminos y usos","Terminos y usos del sitio web");
        Politica politicaEntity = new Politica(1L, "Terminos y usos","Terminos y usos del sitio web");

        //Act
        when(politicaRepository.findByTitulo("Terminos y usos")).thenReturn(Optional.of(politicaEntity));
        // Asserts
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> {
            politicaService.crear(politicaRequestDto);
        });
    }

    @Test
    public void actualizarPolitica() throws MalformedURLException {
        //Arrange
        Politica politicaEntity = new Politica(1L, "Terminos y usos", "Terminos y usos del sitio web");
        PoliticaRequestDto  politicaRequestDto = new PoliticaRequestDto(1L, "Terminos y usos", "Terminos y usos del sitio web");

        //Act
        Mockito.when(politicaRepository.findById(1L)).thenReturn(Optional.of(politicaEntity));

        politicaService.actualizar(politicaRequestDto);

        //Asserts
        verify(politicaRepository, times(1)).save(any(Politica.class));
    }

    @Test
    public void actualizarPoliticaThrowsResourceNotFoundException() throws MalformedURLException {
        PoliticaRequestDto politicaRequestDto = new PoliticaRequestDto(1L, "Terminos y usos","Terminos y usos del sitio web");
        Mockito.when(politicaRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            politicaService.actualizar(politicaRequestDto);
        });
    }

    @Test
    public void buscarPoliticaPorId() throws MalformedURLException{
        //Arrange
        Politica politica = new Politica(1L, "Terminos y usos","Terminos y usos del sitio web");
        PoliticaResponseDto politicaEsperada = new PoliticaResponseDto(1L, "Terminos y usos", "Terminos y usos del sitio web");

        //Act
        Mockito.when(politicaRepository.findById(1L)).thenReturn(Optional.of(politica));
        PoliticaResponseDto politicaResponseDto = politicaService.buscarPorId(1L);

        // Asserts
        Assertions.assertEquals(politicaEsperada.getPoliticaId(), politicaResponseDto.getPoliticaId());
    }

    @Test
    public void buscarPoliticaPorIdThrowsResourceNotFoundException() {
        Mockito.when(politicaRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            politicaService.buscarPorId(1L);
        });
    }


    @Test
    public void eliminarPolitica()throws MalformedURLException {
        //Arrange
        Politica politicaEntity = new Politica(1L, "Terminos y usos","Terminos y usos del sitio web");

        //Act
        Mockito.when(politicaRepository.findById(1L)).thenReturn(Optional.of(politicaEntity));
        doNothing().when(politicaRepository).delete(politicaEntity);
        politicaService.borrarPorId(1L);

        // Asserts
        verify(politicaRepository, times(1)).delete(politicaEntity);
    }

    @Test
    public void eliminarPoliticaThrowsResourceNotFoundException() {
        Mockito.when(politicaRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            politicaService.borrarPorId(1L);
        });
    }

    @Test
    public void listarPoliticasThrowsResourceNotFoundException() throws MalformedURLException{
        Mockito.when(politicaRepository.findAll()).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            politicaService.listarTodos();
        });
    }
}