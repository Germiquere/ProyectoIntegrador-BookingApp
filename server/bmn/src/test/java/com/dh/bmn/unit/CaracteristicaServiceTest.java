package com.dh.bmn.unit;

import com.dh.bmn.dtos.requests.CaracteristicaBicicletaRequestDto;
import com.dh.bmn.dtos.responses.CaracteristicaBicicletaResponseDto;
import com.dh.bmn.entity.*;
import com.dh.bmn.exceptions.ResourceAlreadyExistsException;
import com.dh.bmn.exceptions.ResourceNotFoundException;
import com.dh.bmn.repositories.IBicicletaRepository;
import com.dh.bmn.repositories.ICaracteristicaBicicletaRepository;
import com.dh.bmn.security.user.Rol;
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
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CaracteristicaServiceTest.class)
@ExtendWith(MockitoExtension.class)
public class CaracteristicaServiceTest {

    @Mock
    private ICaracteristicaBicicletaRepository caracteristicaBicicletaRepository;

    @Mock
    private IBicicletaRepository bicicletaRepository;
    @InjectMocks
    private CaracteristicaBicicletaService caracteristicaBicicletaService;

    @BeforeEach
    public void setup() {
        caracteristicaBicicletaRepository = mock(ICaracteristicaBicicletaRepository.class);
        bicicletaRepository = mock(IBicicletaRepository.class);
        caracteristicaBicicletaService = new CaracteristicaBicicletaService(caracteristicaBicicletaRepository, bicicletaRepository);
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
    public void eliminarCaracteristicaBicicleta() throws MalformedURLException {
        //Arrange
        CaracteristicaBicicleta caracteristicaBicicleta = new CaracteristicaBicicleta(1L, "Precio", "icono");
        List<CaracteristicaBicicleta> caracteristicaList = new ArrayList<>(List.of(caracteristicaBicicleta));
        Imagen imagen = new Imagen("imagenesS3/1698617780205_34039.jpg", new URL("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698617780205_34039.jpg"));
        List<Imagen> imagenes = List.of(imagen);
        CategoriaBicicleta categoriaBicicleta = new CategoriaBicicleta(1L, "Montaña", "Bicicleta de montaña", imagen);
        List<CategoriaBicicleta> categoriaList = List.of(categoriaBicicleta);
        Politica politica = new Politica(1L, "Politica", "Descripcion politica");
        List<Politica> politicaBicicletaList = List.of(politica);
        Usuario usuario = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password", Rol.USER);
        Valoracion valoracion = new Valoracion(1L, 5,usuario, "Muy bueno", LocalDate.of(2023, 12, 2));
        List<Valoracion> valoracionList = List.of(valoracion);
        Bicicleta bicicleta = new Bicicleta(1L, "Bike", "Ideal para montaña", 34567, categoriaList, imagenes, caracteristicaList, politicaBicicletaList,valoracionList,4.5,5L);
        List<Bicicleta> bicicletaList = List.of(bicicleta);

        //Act
        Mockito.when(caracteristicaBicicletaRepository.findById(1L)).thenReturn(Optional.of(caracteristicaBicicleta));
        Mockito.when(bicicletaRepository.findAll()).thenReturn(bicicletaList);
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
