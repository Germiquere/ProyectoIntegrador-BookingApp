package com.dh.bmn.unit;

import com.dh.bmn.dtos.requests.CaracteristicaBicicletaRequestDto;
import com.dh.bmn.dtos.responses.CaracteristicaBicicletaResponseDto;
import com.dh.bmn.entity.CaracteristicaBicicleta;
import com.dh.bmn.exceptions.ResourceAlreadyExistsException;
import com.dh.bmn.exceptions.ResourceNotFoundException;
import com.dh.bmn.repositories.ICaracteristicaBicicletaRepository;
import com.dh.bmn.services.impl.CaracteristicaBicicletaService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CaracteristicaServiceTest {

    @Mock
    private ICaracteristicaBicicletaRepository caracteristicaBicicletaRepository;
    @InjectMocks
    private CaracteristicaBicicletaService caracteristicaBicicletaService;

    @BeforeEach
    public void setup() {
        caracteristicaBicicletaRepository = mock(ICaracteristicaBicicletaRepository.class);
        caracteristicaBicicletaService = new CaracteristicaBicicletaService(caracteristicaBicicletaRepository);
    }

    @Test
    public void crearCaracteristicaBicicleta() {
        //Arrange
        CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto = new CaracteristicaBicicletaRequestDto(1L, "Precio", "icono");

        //Act
        caracteristicaBicicletaService.crear(caracteristicaBicicletaRequestDto);

        // Asserts
        verify(caracteristicaBicicletaRepository, times(1)).save(any(CaracteristicaBicicleta.class));
    }

    @Test
    public void crearCaracteristicaBicicletaThrowsResourceAlreadyExistsException(){
        //Arrange
        CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto = new CaracteristicaBicicletaRequestDto(1L, "Precio", "icono");
        CaracteristicaBicicleta caracteristicaBicicleta = new CaracteristicaBicicleta(1L, "Precio", "icono");

        //Act
        when(caracteristicaBicicletaRepository.findByNombre(caracteristicaBicicletaRequestDto.getNombre())).thenReturn(Optional.of(caracteristicaBicicleta));

        // Asserts
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> {
            caracteristicaBicicletaService.crear(caracteristicaBicicletaRequestDto);
        });

    }

    @Test
    public void obtenerCaracteristicaBicicletaPorId() {
        //Arrange
        CaracteristicaBicicleta caracteristicaBicicleta = new CaracteristicaBicicleta(1L, "Precio", "icono");
        CaracteristicaBicicletaResponseDto caracteristicaBicicletaEsperada = new CaracteristicaBicicletaResponseDto(1L, "Precio", "icono");

        //Act
        Mockito.when(caracteristicaBicicletaRepository.findById(1L)).thenReturn(Optional.of(caracteristicaBicicleta));
        CaracteristicaBicicletaResponseDto caracteristicaBicicletaResponseDto = caracteristicaBicicletaService.buscarPorId(1L);

        // Asserts
        Assertions.assertEquals(caracteristicaBicicletaEsperada.getCaracteristicaId(), caracteristicaBicicletaResponseDto.getCaracteristicaId());
    }

    @Test
    public void obtenerCaracteristicaPorIdThrowsResourceNotFoundException() {
        Mockito.when(caracteristicaBicicletaRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            caracteristicaBicicletaService.buscarPorId(1L);
        });
    }

    @Test
    public void obtenerTodasLasCaracteristicasBicicleta() {
        //Arrange

        CaracteristicaBicicleta caracteristicaBicicleta = new CaracteristicaBicicleta(1L, "Precio", "icono");
        List<CaracteristicaBicicleta> caracteristicaBicicletas = new ArrayList<>();
        caracteristicaBicicletas.add(caracteristicaBicicleta);
        CaracteristicaBicicletaResponseDto caracteristicaBicicletaEsperada = new CaracteristicaBicicletaResponseDto(1L, "Precio", "icono");
        List<CaracteristicaBicicletaResponseDto> caracteristicaBicicletaResponseLista = new ArrayList<>();
        caracteristicaBicicletaResponseLista.add(caracteristicaBicicletaEsperada);

        //Act
        Mockito.when(caracteristicaBicicletaRepository.findAll()).thenReturn(caracteristicaBicicletas);
        List<CaracteristicaBicicletaResponseDto> caracteristicaBicicletaLista = caracteristicaBicicletaService.listarTodos();

        // Asserts
        Assertions.assertEquals(caracteristicaBicicletaLista.size(), caracteristicaBicicletas.size());
    }

    @Test
    public void listarTodasCaracteristicasBicicletaThrowsResourceNotFoundException() {

        Mockito.when(caracteristicaBicicletaRepository.findAll()).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            caracteristicaBicicletaService.listarTodos();
        });
    }

    @Test
    public void eliminarCaracteristicaBicicleta() {
        //Arrange
        CaracteristicaBicicleta caracteristicaBicicleta = new CaracteristicaBicicleta(1L, "Precio", "icono");

        //Act
        Mockito.when(caracteristicaBicicletaRepository.findById(1L)).thenReturn(Optional.of(caracteristicaBicicleta));
        doNothing().when(caracteristicaBicicletaRepository).delete(caracteristicaBicicleta);
        caracteristicaBicicletaService.borrarPorId(1L);

        // Asserts
        verify(caracteristicaBicicletaRepository, times(1)).delete(caracteristicaBicicleta);
    }

    @Test
    public void eliminarCaracteristicaBicicletaThrowsResourceNotFoundException() {

        Mockito.when(caracteristicaBicicletaRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            caracteristicaBicicletaService.borrarPorId(1L);
        });
    }

    @Test
    public void actualizarCaracteristicaBicicleta() {
        //Arrange
        CaracteristicaBicicleta caracteristicaBicicleta = new CaracteristicaBicicleta(1L, "Precio", "icono");
        CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto = new CaracteristicaBicicletaRequestDto(1L, "Precio", "icono");

        //Act
        Mockito.when(caracteristicaBicicletaRepository.findById(1L)).thenReturn(Optional.of(caracteristicaBicicleta));
        caracteristicaBicicletaService.actualizar(caracteristicaBicicletaRequestDto);

        // Asserts
        verify(caracteristicaBicicletaRepository, times(1)).save(any(CaracteristicaBicicleta.class));
    }

    @Test
    public void actualizarCaracteristicaBicicletaThrowsResourceNotFoundException() throws MalformedURLException {

        CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto = new CaracteristicaBicicletaRequestDto(1L, "Precio", "icono");

        Mockito.when(caracteristicaBicicletaRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            caracteristicaBicicletaService.actualizar(caracteristicaBicicletaRequestDto);
        });
    }
}
