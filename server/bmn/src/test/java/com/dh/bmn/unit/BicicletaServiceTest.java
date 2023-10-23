package com.dh.bmn.unit;

import com.dh.bmn.dtos.requests.BicicletaRequestDto;
import com.dh.bmn.dtos.requests.CategoriaBicicletaRequestDto;
import com.dh.bmn.dtos.responses.BicicletaResponseDto;
import com.dh.bmn.dtos.responses.CategoriaBicicletaResponseDto;
import com.dh.bmn.entity.Bicicleta;
import com.dh.bmn.entity.CategoriaBicicleta;
import com.dh.bmn.exceptions.ResourceAlreadyExistsException;
import com.dh.bmn.exceptions.ResourceNotFoundException;
import com.dh.bmn.repositories.IBicicletaRepository;
import com.dh.bmn.services.impl.BicicletaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BicicletaServiceTest {


    @Mock
    private IBicicletaRepository bicicletaRepository;
    @InjectMocks
    private BicicletaService bicicletaService;

    @BeforeEach
    public void setup() {

        bicicletaRepository = mock(IBicicletaRepository.class);
        bicicletaService = new BicicletaService(bicicletaRepository);
    }

    @Test
    public void crearBicicleta() {
        //Arrange

        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
        BicicletaRequestDto bicicletaRequestDto =
                new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaRequestDto);

        //Act
        bicicletaService.crear(bicicletaRequestDto);

        // Asserts
        verify(bicicletaRepository, times(1)).save(any(Bicicleta.class));
    }

    @Test
    public void crearBicicletaThrowsResourceAlreadyExistsException() {
        //Arrange
        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
        BicicletaRequestDto bicicletaRequestDto = new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaRequestDto);

        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
        Bicicleta bicicletaEntity = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicleta);

        //Act
        when(bicicletaRepository.findByNombreAndDescripcion(bicicletaRequestDto.getNombre(), bicicletaRequestDto.getDescripcion())).thenReturn(Optional.of(bicicletaEntity));

        // Asserts
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> {
            bicicletaService.crear(bicicletaRequestDto);
        });

    }


    @Test
    public void obtenerBicicletaPorId() {
        //Arrange

        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
        Bicicleta bicicleta = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicleta);

        CategoriaBicicletaResponseDto categoriaBicicletaResponseDto = new CategoriaBicicletaResponseDto(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
        BicicletaResponseDto bicicletaEsperada = new BicicletaResponseDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaResponseDto);

        //Act
        Mockito.when(bicicletaRepository.findById(1L)).thenReturn(Optional.of(bicicleta));
        BicicletaResponseDto bicicletaResponseDto = bicicletaService.buscarPorId(1L);

        // Asserts
        Assertions.assertEquals(bicicletaEsperada.getBicicletaId(), bicicletaResponseDto.getBicicletaId());

    }

    @Test
    public void obtenerBicicletaPorIdThrowsResourceNotFoundException() {
        Mockito.when(bicicletaRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            bicicletaService.buscarPorId(1L);
        });
    }

    @Test
    public void obtenerTodasLasBicicletas() {
        //Arrange
        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
        Bicicleta bicicletaEntity = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicleta);

        List<Bicicleta> bicicletas = new ArrayList<>();
        bicicletas.add(bicicletaEntity);

        CategoriaBicicletaResponseDto categoriaBicicletaResponseDto = new CategoriaBicicletaResponseDto(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
        BicicletaResponseDto bicicletaEsperada = new BicicletaResponseDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaResponseDto);
        List<BicicletaResponseDto> bicicletaResponseLista = new ArrayList<>();
        bicicletaResponseLista.add(bicicletaEsperada);

        //Act
        Mockito.when(bicicletaRepository.findAll()).thenReturn(bicicletas);
        List<BicicletaResponseDto> bicicletaLista = bicicletaService.listarTodos();

        // Asserts
        Assertions.assertEquals(bicicletaResponseLista.size(), bicicletaLista.size());

    }

    @Test
    public void listarTodasBicicletasThrowsResourceNotFoundException() {

        Mockito.when(bicicletaRepository.findAll()).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            bicicletaService.listarTodos();
        });

    }

    @Test
    public void eliminarBicicleta() {
        //Arrange

        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
        Bicicleta bicicletaEntity = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicleta);

        //Act
        Mockito.when(bicicletaRepository.findById(1L)).thenReturn(Optional.of(bicicletaEntity));
        doNothing().when(bicicletaRepository).delete(bicicletaEntity);

        bicicletaService.borrarPorId(1L);

        // Asserts
        verify(bicicletaRepository, times(1)).delete(bicicletaEntity);
    }

    @Test
    public void eliminarBicicletaThrowsResourceNotFoundException() {

        Mockito.when(bicicletaRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            bicicletaService.borrarPorId(1L);
        });
    }

    @Test
    public void actualizarBicicleta() {
        //Arrange
        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
        Bicicleta bicicletaEntity = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicleta);

        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
        BicicletaRequestDto bicicletaRequestDto = new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaRequestDto);

        //Act
        Mockito.when(bicicletaRepository.findById(1L)).thenReturn(Optional.of(bicicletaEntity));

        bicicletaService.actualizar(bicicletaRequestDto);

        // Asserts
        verify(bicicletaRepository, times(1)).save(any(Bicicleta.class));
    }

    @Test
    public void actualizarBicicletaThrowsResourceNotFoundException() {

        CategoriaBicicletaRequestDto categoriaBicicletaRequestDto = new CategoriaBicicletaRequestDto(1L, "Montaña", "Bicicleta de montaña", "https://www.bicidemontaña.com");
        BicicletaRequestDto bicicletaRequestDto = new BicicletaRequestDto(1L, "Bike", "Ideal para montaña", 34567, categoriaBicicletaRequestDto);

        Mockito.when(bicicletaRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            bicicletaService.actualizar(bicicletaRequestDto);
        });
    }
}
